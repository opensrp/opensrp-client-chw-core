package org.smartregister.chw.core.presenter;

import android.util.Pair;

import org.smartregister.chw.core.contract.ChwNotificationDetailsContract;
import org.smartregister.chw.core.dao.ChwNotificationDao;
import org.smartregister.chw.core.domain.NotificationItem;
import org.smartregister.chw.core.interactor.BaseChwNotificationDetailsInteractor;

import java.lang.ref.WeakReference;

public class BaseNotificationDetailsPresenter implements ChwNotificationDetailsContract.Presenter {

    private WeakReference<ChwNotificationDetailsContract.View> view;
    private ChwNotificationDetailsContract.Interactor interactor;
    private String clientBaseEntityId;
    private Pair<String, String> notificationDates;

    public BaseNotificationDetailsPresenter(ChwNotificationDetailsContract.View view) {
        this.view = new WeakReference<>(view);
        interactor = new BaseChwNotificationDetailsInteractor(this);
    }

    @Override
    public String getClientBaseEntityId() {
        return clientBaseEntityId;
    }

    @Override
    public void getNotificationDetails(String baseEntityId, String notificationType) {
        interactor.fetchNotificationDetails(baseEntityId, notificationType);
    }

    @Override
    public ChwNotificationDetailsContract.View getView() {
        if (view != null) {
            return view.get();
        }
        return null;
    }

    @Override
    public void onNotificationDetailsFetched(NotificationItem notificationItem) {
        getView().setNotificationDetails(notificationItem);
    }

    @Override
    public void showMemberProfile() {
        //TODO implement functionality for navigating to member profile
    }

    @Override
    public void dismissNotification(String baseEntityId, String notificationType) {
        if (!ChwNotificationDao.isMarkedAsDone(notificationType)) {
            getView().disableMarkAsDoneAction(true);
            interactor.createReferralDismissalEvent(notificationType);
        }
    }

    public void setInteractor(ChwNotificationDetailsContract.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setClientBaseEntityId(String clientBaseEntityId) {
        this.clientBaseEntityId = clientBaseEntityId;
    }

    @Override
    public void setNotificationDates(Pair<String, String> notificationDates) {
        this.notificationDates = notificationDates;
    }

    @Override
    public Pair<String, String> getNotificationDates() {
        return notificationDates;
    }
}
