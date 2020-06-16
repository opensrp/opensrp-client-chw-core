package org.smartregister.chw.core.implementation;

import android.content.Intent;
import android.view.MenuItem;

import org.mockito.Mockito;
import org.smartregister.chw.core.activity.CoreFamilyProfileActivity;
import org.smartregister.chw.core.activity.CorePncMemberProfileActivity;
import org.smartregister.chw.core.activity.CorePncRegisterActivity;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.interactor.CorePncMemberProfileInteractor;

public class CorePncMemberProfileActivityImpl extends CorePncMemberProfileActivity {
    @Override
    protected Class<? extends CoreFamilyProfileActivity> getFamilyProfileActivityClass() {
        return CoreFamilyProfileActivity.class;
    }

    @Override
    protected CorePncMemberProfileInteractor getPncMemberProfileInteractor() {
        return Mockito.mock(CorePncMemberProfileInteractor.class);
    }

    @Override
    protected Intent getPNCIntent() {
        return Mockito.mock(Intent.class);
    }

    @Override
    public void removePncMember() {

    }

    @Override
    protected Class<? extends CorePncRegisterActivity> getPncRegisterActivityClass() {
        return CorePncRegisterActivity.class;
    }

    @Override
    public void startMalariaRegister() {

    }

    @Override
    public void startFpRegister() {

    }

    @Override
    public void startFpChangeMethod() {

    }

    @Override
    public void startMalariaFollowUpVisit() {

    }

    @Override
    public void getRemoveBabyMenuItem(MenuItem item) {

    }
}
