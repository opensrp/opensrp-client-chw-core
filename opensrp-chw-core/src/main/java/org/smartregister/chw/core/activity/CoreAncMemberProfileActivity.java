package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jeasy.rules.api.Rules;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.anc.activity.BaseAncMemberProfileActivity;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.util.Constants;
import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.contract.AncMemberProfileContract;
import org.smartregister.chw.core.dao.AncDao;
import org.smartregister.chw.core.interactor.CoreAncMemberProfileInteractor;
import org.smartregister.chw.core.presenter.CoreAncMemberProfilePresenter;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.HomeVisitUtil;
import org.smartregister.chw.core.utils.VisitSummary;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.AlertStatus;
import org.smartregister.domain.Task;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static org.smartregister.chw.core.utils.Utils.getCommonPersonObjectClient;
import static org.smartregister.chw.core.utils.Utils.updateToolbarTitle;

public abstract class CoreAncMemberProfileActivity extends BaseAncMemberProfileActivity implements AncMemberProfileContract.View {

    protected RecyclerView notificationAndReferralRecyclerView;
    protected RelativeLayout notificationAndReferralLayout;
    protected boolean hasDueServices = false;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
    private LocalDate ancCreatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeNotificationReferralRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_anc_member_registration) {
            startFormForEdit(R.string.edit_member_form_title, CoreConstants.JSON_FORM.getFamilyMemberRegister());
            return true;
        } else if (itemId == R.id.action_anc_registration) {
            startFormForEdit(R.string.edit_anc_registration_form_title, CoreConstants.JSON_FORM.getAncRegistration());
            return true;
        } else if (itemId == R.id.anc_danger_signs_outcome) {
            ancMemberProfilePresenter().startAncDangerSignsOutcomeForm(memberObject);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anc_member_profile_menu, menu);
        return true;
    }

    @Override // to chw
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case CoreConstants.ProfileActivityResults.CHANGE_COMPLETED:
                Intent intent = new Intent(CoreAncMemberProfileActivity.this, CoreAncRegisterActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
                finish();
                break;
            case Constants.REQUEST_CODE_HOME_VISIT:
                this.displayView();
                break;
            default:
                break;
        }
    }

    public void startFormActivity(JSONObject formJson) {
        startActivityForResult(CoreJsonFormUtils.getJsonIntent(this, formJson,
                Utils.metadata().familyMemberFormActivity), JsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    // to chw
    public void startFormForEdit(Integer title_resource, String formName) {
        //// TODO: 22/08/19
    }

    public CoreAncMemberProfilePresenter ancMemberProfilePresenter() {
        return (CoreAncMemberProfilePresenter) presenter;
    }


    protected static CommonPersonObjectClient getClientDetailsByBaseEntityID(@NonNull String baseEntityId) {
        return getCommonPersonObjectClient(baseEntityId);
    }

    @Override
    protected void registerPresenter() {
        presenter = new CoreAncMemberProfilePresenter(this, new CoreAncMemberProfileInteractor(this), memberObject);
    }

    @Override
    public void openMedicalHistory() {
        CoreAncMedicalHistoryActivity.startMe(this, memberObject);
    }

    @Override
    public abstract void openUpcomingService();

    @Override
    public abstract void openFamilyDueServices();


    @Override
    public void setFamilyStatus(AlertStatus status) {
        super.setFamilyStatus(status);
        if (status == AlertStatus.complete) {
            hasDueServices = false;
        } else if (status == AlertStatus.normal || status == AlertStatus.urgent) {
            hasDueServices = true;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    private int getMonthsDifference(LocalDate date1, LocalDate date2) {
        return Months.monthsBetween( date1.withDayOfMonth(1), date2.withDayOfMonth(1)).getMonths();
    }

    protected boolean isVisitThisMonth(LocalDate lastVisitDate, LocalDate todayDate) {
        return getMonthsDifference(lastVisitDate, todayDate) < 1;

    }

    protected LocalDate getDateCreated() {
        try {
            Date dateCreated = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(memberObject.getDateCreated().substring(0, 10));
            String createdDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(dateCreated);
            ancCreatedDate = formatter.parseLocalDate(createdDate);
        } catch (Exception e) {

        }
        return ancCreatedDate;
    }

    protected void getLayoutVisibility() {
        layoutRecordView.setVisibility(View.VISIBLE);
        record_reccuringvisit_done_bar.setVisibility(View.GONE);
        textViewAncVisitNot.setVisibility(View.VISIBLE);
        layoutNotRecordView.setVisibility(View.GONE);
    }

    private void getButtonStatus() {
        openVisitMonthView();
        textViewUndo.setVisibility(View.GONE);

        Rules rules = CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.ANC_HOME_VISIT);
        Visit lastNotDoneVisit = AncLibrary.getInstance().visitRepository().getLatestVisit(baseEntityID, org.smartregister.chw.anc.util.Constants.EVENT_TYPE.ANC_HOME_VISIT_NOT_DONE);
        if (lastNotDoneVisit != null) {
            Visit lastNotDoneVisitUndo = AncLibrary.getInstance().visitRepository().getLatestVisit(baseEntityID, org.smartregister.chw.anc.util.Constants.EVENT_TYPE.ANC_HOME_VISIT_NOT_DONE_UNDO);
            if (lastNotDoneVisitUndo != null
                    && lastNotDoneVisitUndo.getDate().after(lastNotDoneVisit.getDate())) {
                lastNotDoneVisit = null;
            }
        }

        Visit lastVisit = AncLibrary.getInstance().visitRepository().getLatestVisit(baseEntityID, org.smartregister.chw.anc.util.Constants.EVENT_TYPE.ANC_HOME_VISIT);
        String visitDate = lastVisit != null ? new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(lastVisit.getDate()) : null;
        String lastVisitNotDone = lastNotDoneVisit != null ? new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(lastNotDoneVisit.getDate()) : null;

        VisitSummary visitSummary = HomeVisitUtil.getAncVisitStatus(this, rules, visitDate, lastVisitNotDone, getDateCreated());
        String visitStatus = visitSummary.getVisitStatus();

        if (visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.OVERDUE)) {
            textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_selector_overdue);
            getLayoutVisibility();

        } else if (visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE)) {
            textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_anc_selector);
            getLayoutVisibility();
        } else if (visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.NOT_VISIT_THIS_MONTH)) {
            textViewUndo.setVisibility(View.VISIBLE);
            textViewUndo.setText(getString(org.smartregister.chw.opensrp_chw_anc.R.string.undo));
            record_reccuringvisit_done_bar.setVisibility(View.GONE);
            openVisitMonthView();
        }
    }

    @Override
    public void setupViews() {
        super.setupViews();
        updateToolbarTitle(this, R.id.toolbar_title, memberObject.getFamilyName());
        Visit lastVisit = getVisit(Constants.EVENT_TYPE.ANC_HOME_VISIT);
        if (lastVisit != null) {
            boolean within24Hours = VisitUtils.isVisitWithin24Hours(lastVisit);
            String lastVisitDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(lastVisit.getDate());
            if (isVisitThisMonth(formatter.parseLocalDate(lastVisitDate), new LocalDate())) {
                if (within24Hours) {
                    Calendar cal = Calendar.getInstance();
                    int offset = cal.getTimeZone().getOffset(cal.getTimeInMillis());
                    Long longDate = lastVisit.getDate().getTime();
                    Date date = new Date(longDate - (long) offset);
                    String monthString = (String) DateFormat.format("MMMM", date);
                    layoutRecordView.setVisibility(View.GONE);
                    tvEdit.setVisibility(View.VISIBLE);
                    textViewNotVisitMonth.setText(getContext().getString(R.string.anc_visit_done, monthString));
                    imageViewCross.setImageResource(R.drawable.activityrow_visited);
                } else {
                    record_reccuringvisit_done_bar.setVisibility(View.VISIBLE);
                    layoutNotRecordView.setVisibility(View.GONE);
                }
                textViewUndo.setVisibility(View.GONE);
                textViewAncVisitNot.setVisibility(View.GONE);

            } else {
                getButtonStatus();
            }
        } else {
            getButtonStatus();
        }
    }

    protected void initializeNotificationReferralRecyclerView() {
        notificationAndReferralLayout = findViewById(R.id.notification_and_referral_row);
        notificationAndReferralRecyclerView = findViewById(R.id.notification_and_referral_recycler_view);
        notificationAndReferralRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void openFamilyLocation() {
        Intent intent = new Intent(this, CoreAncMemberMapActivity.class);
        intent.putExtra(CoreConstants.KujakuConstants.LAT_LNG, memberObject.getGps());
        intent.putExtra(CoreConstants.KujakuConstants.LAND_MARK, memberObject.getLandmark());
        intent.putExtra(CoreConstants.KujakuConstants.NAME, memberObject.getFullName());
        intent.putExtra(CoreConstants.KujakuConstants.FAMILY_NAME, memberObject.getFamilyName());
        intent.putExtra(CoreConstants.KujakuConstants.ANC_WOMAN_PHONE, memberObject.getPhoneNumber());
        intent.putExtra(CoreConstants.KujakuConstants.ANC_WOMAN_FAMILY_HEAD, memberObject.getFamilyHeadName());
        intent.putExtra(CoreConstants.KujakuConstants.ANC_WOMAN_FAMILY_HEAD_PHONE, memberObject.getFamilyHeadPhoneNumber());
        this.startActivity(intent);
    }

    @Override
    public abstract void setClientTasks(Set<Task> taskList);

    @Override
    public MemberObject getMemberObject(String baseEntityID) {
        return AncDao.getMember(baseEntityID);
    }

}
