package org.smartregister.chw.core.dao;

import android.content.Context;
import android.util.Pair;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.utils.Utils;
import org.smartregister.thinkmd.model.FHIRBundleModel;

import static org.smartregister.chw.core.dao.ChildDao.getChildProfileData;
import static org.smartregister.chw.core.utils.Utils.fetchMUACValues;
import static org.smartregister.chw.core.utils.Utils.getRandomGeneratedId;

@RunWith(PowerMockRunner.class)
public class FHIRBundleDaoTest {

    @Mock
    private Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        org.smartregister.Context context = org.smartregister.Context.getInstance();
        CoreLibrary.init(context);
    }

    @PrepareForTest({ChildDao.class, Utils.class})
    @Test
    public void getFHIRBundleTest() {
        FHIRBundleDao fhirBundleDao = Mockito.spy(FHIRBundleDao.class);
        String childBaseEntityId = "123456";
        PowerMockito.mockStatic(ChildDao.class);
        PowerMockito.mockStatic(Utils.class);
        PowerMockito.when(getChildProfileData(childBaseEntityId)).thenReturn(Triple.of("9416", "15-10-1994", "male"));
        PowerMockito.when(fetchMUACValues(childBaseEntityId)).thenReturn(Pair.create("green", "Green"));
        PowerMockito.when(getRandomGeneratedId()).thenReturn("123-456-789");
        PowerMockito.doReturn("111-222-333").when(fhirBundleDao).getLocationId();
        PowerMockito.doReturn("dummy").when(fhirBundleDao).getProviderId();
        FHIRBundleModel bundle = fhirBundleDao.fetchFHIRDateModel(context, childBaseEntityId);

        Assert.assertNotNull(bundle);
    }
}
