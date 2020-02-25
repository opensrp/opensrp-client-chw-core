package org.smartregister.chw.core.interactor;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.core.dao.VisitDao;
import org.smartregister.chw.pnc.contract.BasePncMedicalHistoryContract;
import org.smartregister.chw.anc.util.AppExecutors;
import org.smartregister.dao.AbstractDao;
import org.smartregister.repository.Repository;

import java.util.concurrent.Executor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({VisitDao.class, AbstractDao.class})
public class CorePncMedicalHistoryInteractorTest implements Executor {

    private CorePncMedicalHistoryActivityInteractor interactor;
    private AppExecutors appExecutors;

    @Mock
    private Context context;

    @Mock
    private Repository repository;

    @Mock
    private SQLiteDatabase sqLiteDatabase;

    @Mock
    private BasePncMedicalHistoryContract.InteractorCallBack callBack;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        appExecutors = new AppExecutors(this, this, this);
        interactor = Mockito.spy(CorePncMedicalHistoryActivityInteractor.class);
        Mockito.doReturn(sqLiteDatabase).when(repository).getReadableDatabase();
        Mockito.doReturn(sqLiteDatabase).when(repository).getWritableDatabase();
    }

    @Test
    public void getMemberHistoryCallBackIsExecutedWithVisitDetails() {
        Whitebox.setInternalState(interactor, "appExecutors", appExecutors);
        Whitebox.setInternalState(AbstractDao.class, "repository", repository);
        interactor.getMemberHistory("ID1234", context, callBack);
        Mockito.verify(callBack).onDataFetched(Mockito.anyList());
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
