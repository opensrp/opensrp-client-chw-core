package org.smartregister.chw.core.provider;

import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.activity.impl.CoreRegisterProviderImpl;
import org.smartregister.commonregistry.CommonRepository;

import java.util.Set;

public class CoreRegisterProviderTest extends BaseUnitTest {

    private CoreRegisterProvider coreRegisterProvider;

    @Mock
    private Context context;

    @Mock
    private CommonRepository commonRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Set visibleColumns = Mockito.mock(Set.class);
        View.OnClickListener onClickListener = Mockito.mock(View.OnClickListener.class);
        coreRegisterProvider = new CoreRegisterProviderImpl(context, commonRepository, visibleColumns,
                onClickListener, onClickListener);
    }



}
