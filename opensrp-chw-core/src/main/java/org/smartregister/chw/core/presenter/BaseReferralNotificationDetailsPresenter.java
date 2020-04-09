package org.smartregister.chw.core.presenter;

import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.interactor.BaseReferralNotificationDetailsInteractor;

import java.lang.ref.WeakReference;

public class BaseReferralNotificationDetailsPresenter implements BaseReferralNotificationDetailsContract.Presenter {

    private WeakReference<BaseReferralNotificationDetailsContract.View> view;
    private BaseReferralNotificationDetailsContract.Interactor interactor;

    public BaseReferralNotificationDetailsPresenter(BaseReferralNotificationDetailsContract.View view) {
        this.view = new WeakReference<>(view);
        interactor = new BaseReferralNotificationDetailsInteractor(this);
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
}
