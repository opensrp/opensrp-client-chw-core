package org.smartregister.chw.core.activity;

import android.app.ProgressDialog;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.chw.core.contract.CoreChildRegisterContract;
import org.smartregister.chw.core.presenter.CoreChildRegisterPresenter;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class CoreBirthNotificationRegisterActivityTest {

    @Mock
    private CoreChildRegisterContract.Model model;
    @Mock
    private JSONObject jsonObject;
    @Mock
    private CoreChildRegisterContract.InteractorCallBack callBack;
    @Mock
    private CoreChildRegisterContract.Interactor interactor;
    private CoreChildRegisterPresenter presenter;
    @Mock
    private CoreChildRegisterContract.View view;
    private CoreBirthNotificationRegisterActivity coreBirthNotificationRegisterActivity;
    @Mock
    private ProgressDialog progressDialog;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        coreBirthNotificationRegisterActivity = Mockito.mock(CoreBirthNotificationRegisterActivity.class, Mockito.CALLS_REAL_METHODS);
        presenter = Mockito.mock(CoreChildRegisterPresenter.class, Mockito.CALLS_REAL_METHODS);
        ReflectionHelpers.setField(presenter, "viewReference", new WeakReference<>(view));
        ReflectionHelpers.setField(presenter, "interactor", interactor);
        ReflectionHelpers.setField(presenter, "model", model);
    }

    @Test
    public void testStartMeInitiatesActivity() {
        CoreBirthNotificationRegisterActivity.startMe(coreBirthNotificationRegisterActivity);
        Mockito.verify(coreBirthNotificationRegisterActivity).startActivity(Mockito.any());
    }

    @Test
    public void testActivityLoaded() {
        Assert.assertNotNull(coreBirthNotificationRegisterActivity);
    }

    @Test
    public void testInitializePresenter() {
        coreBirthNotificationRegisterActivity.initializePresenter();
        Assert.assertTrue(ReflectionHelpers.getField(coreBirthNotificationRegisterActivity, "presenter") instanceof CoreChildRegisterPresenter);
    }

    @Test
    public void testGetContext() {
        Assert.assertEquals(coreBirthNotificationRegisterActivity, coreBirthNotificationRegisterActivity.getContext());
    }

    @Test
    public void testHideProgressDialog() {
        Whitebox.setInternalState(coreBirthNotificationRegisterActivity, "progressDialog", progressDialog);
        coreBirthNotificationRegisterActivity.hideProgressDialog();
        Mockito.verify(progressDialog).dismiss();
    }

    @Test
    public void testStartForm() {
        try {
            String entityId = Mockito.anyString();
            String familyId = Mockito.anyString();
            presenter.startForm(Mockito.anyString(), entityId, Mockito.anyString(), Mockito.anyString(), familyId);
            if ("".equals(entityId)) {
                String anyString = Mockito.anyString();
                Triple<String, String, String> triple = Triple.of(anyString, anyString, anyString);
                Mockito.verify(interactor).getNextUniqueId(triple, callBack, Mockito.anyString());
            }
            if ("".equals(familyId)) {
                Mockito.verify(view).startFormActivity(jsonObject);
            } else {
                Mockito.verify(view).startFormActivity(jsonObject);
            }

        } catch (Exception e) {
            Timber.v(e.toString());
        }
    }

    @Test
    public void testSaveLanguage() {
        presenter.saveLanguage(Mockito.anyString());
        Mockito.verify(view).displayToast(Mockito.anyString());
    }

    @Test
    public void testOnDestroy() {
        presenter.onDestroy(true);
        Mockito.verify(interactor).onDestroy(true);
    }

    @Test
    public void testOnNoUniqueId() {
        presenter.onNoUniqueId();
        Mockito.verify(view).displayShortToast(Mockito.anyInt());
    }

    @After
    public void tearDown() {
        try {
            coreBirthNotificationRegisterActivity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //logout
        Context.getInstance().session().expire();
        System.gc();
    }

}
