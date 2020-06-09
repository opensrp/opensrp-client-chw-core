package org.smartregister.chw.core.implementation;

import android.content.Context;

import org.mockito.Mockito;
import org.smartregister.chw.core.activity.CoreFamilyOtherMemberProfileActivity;
import org.smartregister.chw.core.activity.CoreFamilyProfileActivity;
import org.smartregister.chw.core.custom_views.CoreFamilyMemberFloatingMenu;
import org.smartregister.chw.core.presenter.CoreFamilyOtherMemberActivityPresenter;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.family.fragment.BaseFamilyOtherMemberProfileFragment;
import org.smartregister.view.contract.BaseProfileContract;

public class CoreFamilyOtherMemberProfileActivityImpl extends CoreFamilyOtherMemberProfileActivity {
    @Override
    public void startAncRegister() {

    }

    @Override
    public void startFpRegister() {

    }

    @Override
    public void startFpChangeMethod() {

    }

    @Override
    public void startMalariaRegister() {

    }

    @Override
    public void startMalariaFollowUpVisit() {

    }

    @Override
    public void setIndependentClient(boolean isIndependent) {

    }

    @Override
    public void removeIndividualProfile() {

    }

    @Override
    public void startEditMemberJsonForm(Integer title_resource, CommonPersonObjectClient client) {

    }

    @Override
    protected BaseProfileContract.Presenter getFamilyOtherMemberActivityPresenter(String familyBaseEntityId, String baseEntityId, String familyHead, String primaryCaregiver, String villageTown, String familyName) {
        return Mockito.mock(CoreFamilyOtherMemberActivityPresenter.class);
    }

    @Override
    protected CoreFamilyMemberFloatingMenu getFamilyMemberFloatingMenu() {
        return Mockito.mock(CoreFamilyMemberFloatingMenu.class);
    }

    @Override
    protected Context getFamilyOtherMemberProfileActivity() {
        return null;
    }

    @Override
    protected Class<? extends CoreFamilyProfileActivity> getFamilyProfileActivity() {
        return null;
    }

    @Override
    protected BaseFamilyOtherMemberProfileFragment getFamilyOtherMemberProfileFragment() {
        return null;
    }
}
