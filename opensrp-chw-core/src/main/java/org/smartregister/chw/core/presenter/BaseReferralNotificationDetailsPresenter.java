package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.dao.ReferralNotificationDao;
import org.smartregister.chw.core.domain.ReferralNotificationItem;
import org.smartregister.chw.core.interactor.BaseReferralNotificationDetailsInteractor;

import java.lang.ref.WeakReference;

public class BaseReferralNotificationDetailsPresenter implements BaseReferralNotificationDetailsContract.Presenter {

    private WeakReference<BaseReferralNotificationDetailsContract.View> view;
    private BaseReferralNotificationDetailsContract.Interactor interactor;
    private String clientBaseEntityId;

    public BaseReferralNotificationDetailsPresenter(BaseReferralNotificationDetailsContract.View view) {
        this.view = new WeakReference<>(view);
        interactor = new BaseReferralNotificationDetailsInteractor(this);
    }

    @Override
    public String getClientBaseEntityId() {
        return clientBaseEntityId;
    }

    @Override
    public void getReferralDetails(String referralTaskId, String notificationType) {
        interactor.fetchReferralDetails(referralTaskId, notificationType);
    }

    @Override
    public BaseReferralNotificationDetailsContract.View getView() {
        if (view != null) {
            return view.get();
        }
        return null;
    }

    @Override
    public void onReferralDetailsFetched(ReferralNotificationItem referralNotificationItem) {
        getView().setReferralNotificationDetails(referralNotificationItem);
    }

    @Override
    public void showMemberProfile(String baseEntityId) {

    }

    @Override
    public void dismissReferralNotification(String referralTaskId) {
        if (!ReferralNotificationDao.isMarkedAsDone(referralTaskId)) {
            getView().disableMarkAsDoneAction(true);
            interactor.createReferralDismissalEvent(referralTaskId);
        }
    }

    public void setInteractor(BaseReferralNotificationDetailsContract.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setClientBaseEntityId(String clientBaseEntityId) {
        this.clientBaseEntityId = clientBaseEntityId;
    }
}
