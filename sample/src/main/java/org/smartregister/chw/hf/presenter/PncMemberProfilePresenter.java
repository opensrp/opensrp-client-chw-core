package org.smartregister.chw.hf.presenter;


import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.anc.contract.BaseAncMemberProfileContract;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.presenter.BaseAncMemberProfilePresenter;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.hf.contract.PncMemberProfileContract;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.Task;
import org.smartregister.family.contract.FamilyProfileContract;

import java.util.Set;

import timber.log.Timber;

public class PncMemberProfilePresenter extends BaseAncMemberProfilePresenter implements
        PncMemberProfileContract.Presenter, PncMemberProfileContract.InteractorCallback, FamilyProfileContract.InteractorCallBack {

    private PncMemberProfileContract.Interactor interactor;
    private String entityId;

    public PncMemberProfilePresenter(BaseAncMemberProfileContract.View view, BaseAncMemberProfileContract.Interactor interactor,
                                     MemberObject memberObject) {
        super(view, interactor, memberObject);
        setEntityId(memberObject.getBaseEntityId());
        this.interactor = (PncMemberProfileContract.Interactor) interactor;
    }

    @Override
    public void fetchReferralTasks() {
        interactor.getReferralTasks(CoreConstants.REFERRAL_PLAN_ID, getEntityId(), this);
    }

    @Override
    public void updateReferralTasks(Set<Task> taskList) {
        ((PncMemberProfileContract.View) getView()).setReferralTasks(taskList);
    }

    public String getEntityId() {
        return entityId;
    }

    private void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void startFormForEdit(CommonPersonObjectClient commonPersonObject) {
        Timber.d("startFormForEdit unimplemented");
    }

    @Override
    public void refreshProfileTopSection(CommonPersonObjectClient client) {
        Timber.d("refreshProfileTopSection unimplemented");
    }

    @Override
    public void onUniqueIdFetched(Triple<String, String, String> triple, String entityId) {
        Timber.d("onUniqueIdFetched unimplemented");
    }

    @Override
    public void onNoUniqueId() {
        Timber.d("onNoUniqueId unimplemented");
    }

    @Override
    public void onRegistrationSaved(boolean isEditMode) {
        Timber.d("onRegistrationSaved unimplemented");
    }
}


