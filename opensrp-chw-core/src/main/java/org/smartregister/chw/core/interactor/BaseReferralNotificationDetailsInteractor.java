package org.smartregister.chw.core.interactor;

import android.app.Activity;
import android.content.Context;

import org.jetbrains.annotations.NotNull;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.dao.ReferralNotificationDao;
import org.smartregister.chw.core.domain.ReferralNotificationItem;
import org.smartregister.chw.core.domain.ReferralNotificationRecord;
import org.smartregister.family.util.Utils;
import org.smartregister.opd.utils.OpdUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseReferralNotificationDetailsInteractor implements BaseReferralNotificationDetailsContract.Interactor {

    private BaseReferralNotificationDetailsContract.Presenter presenter;
    private Context context;

    public BaseReferralNotificationDetailsInteractor(BaseReferralNotificationDetailsContract.Presenter presenter) {
        this.presenter = presenter;
        context = (Activity) presenter.getView();
    }

    @Override
    public void fetchReferralDetails(String referralTaskId, String referralType) {
        ReferralNotificationItem referralNotificationItem = null;

        if (referralType.contains("Successful")) {
            referralNotificationItem = getDetailsForSuccessfulReferral(referralTaskId);
        } else if (referralType.contains("PNC") || referralType.contains("ANC")) {
            referralNotificationItem = getDetailsForPNCAndANCReferrals(referralTaskId);
        } else if (referralType.contains("Malaria")) {
            referralNotificationItem = getDetailsForMalariaReferral(referralTaskId);
        } else if (referralType.contains("Family Planning")) {
            referralNotificationItem = getDetailsForFamilyPlanningReferral(referralTaskId);
        }
        presenter.getView().onReferralDetailsFetched(referralNotificationItem);
    }

    @NotNull
    private ReferralNotificationItem getDetailsForSuccessfulReferral(String referralTaskId) {
        ReferralNotificationRecord record = ReferralNotificationDao.getSuccessfulReferral(referralTaskId);

        String title = context.getString(R.string.successful_referral_notification_title,
                record.getClientName(), getClientAge(record.getClientDateOfBirth()));
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_phone, record.getPhone() != null ? record.getPhone() : context.getString(R.string.no_phone_provided)));
        details.add(context.getString(R.string.referral_notification_closure_date, record.getNotificationDate()));
        if (record.getVillage() != null) {
            details.add(context.getString(R.string.referral_notification_village, record.getVillage()));
        }
        details.add(context.getString(R.string.referral_notification_record_closed));
        return new ReferralNotificationItem(title, details);
    }

    @NotNull
    private ReferralNotificationItem getDetailsForPNCAndANCReferrals(String referralTaskId) {
        String title = context.getString(R.string.followup_referral_notification_title, "Victoria Rubadiri", "10 Jan 2019");
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_danger_sign, "Difficulty in breathing"));
        details.add(context.getString(R.string.referral_notification_action_taken, "Nothing"));
        details.add(context.getString(R.string.referral_notification_village, "Miami Beach"));
        return new ReferralNotificationItem(title, details);
    }

    @NotNull
    private ReferralNotificationItem getDetailsForFamilyPlanningReferral(String referralTaskId) {
        String title = context.getString(R.string.followup_referral_notification_title, "Victoria Rubadiri", "10 Jan 2019");
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_danger_sign, "Difficulty in breathing"));
        details.add(context.getString(R.string.referral_notification_action_taken, "Nothing"));
        details.add(context.getString(R.string.referral_notification_village, "Beketa Market"));
        return new ReferralNotificationItem(title, details);
    }

    @NotNull
    private ReferralNotificationItem getDetailsForMalariaReferral(String referralTaskId) {
        String title = context.getString(R.string.followup_referral_notification_title, "Victoria Rubadiri", "10 Jan 2019");
        List<String> details = new ArrayList<>();
        details.add(context.getString(R.string.referral_notification_selected_method, "Male condoms"));
        details.add(context.getString(R.string.referral_notification_village, "Somba Bay"));
        return new ReferralNotificationItem(title, details);
    }

    private String getClientAge(String dobString) {
        String translatedYearInitial = context.getResources().getString(R.string.abbrv_years);
        return OpdUtils.getClientAge(Utils.getDuration(dobString), translatedYearInitial);
    }
}
