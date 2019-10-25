package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.jeasy.rules.api.Rules;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.anc.activity.BaseAncMemberProfileActivity;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.util.Constants;
import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.contract.AncMemberProfileContract;
import org.smartregister.chw.core.interactor.CoreAncMemberProfileInteractor;
import org.smartregister.chw.core.interactor.CoreChildProfileInteractor;
import org.smartregister.chw.core.presenter.CoreAncMemberProfilePresenter;
import org.smartregister.chw.core.rule.AncVisitAlertRule;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.HomeVisitUtil;
import org.smartregister.chw.core.utils.VisitSummary;
import org.smartregister.domain.AlertStatus;
import org.smartregister.domain.Task;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;
import java.util.logging.SimpleFormatter;

public abstract class CoreAncMemberProfileActivity extends BaseAncMemberProfileActivity implements AncMemberProfileContract.View {

    protected boolean hasDueServices = false;
    LocalDate todayDate = new LocalDate();
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_anc_member_registration) {
            startFormForEdit(R.string.edit_member_form_title,
                    CoreConstants.JSON_FORM.getFamilyMemberRegister());
            return true;
        } else if (itemId == R.id.action_anc_registration) {
            startFormForEdit(R.string.edit_anc_registration_form_title,
                    CoreConstants.JSON_FORM.getAncRegistration());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anc_member_profile_menu, menu);
        return true;
    }

    @Override // to chw
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CoreConstants.ProfileActivityResults.CHANGE_COMPLETED && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(CoreAncMemberProfileActivity.this, CoreAncRegisterActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
            finish();
        } else if (requestCode == Constants.REQUEST_CODE_HOME_VISIT) {
            this.displayView();
        }
    }


    // to chw
    public void startFormForEdit(Integer title_resource, String formName) {
        //// TODO: 22/08/19
    }

    public CoreAncMemberProfilePresenter ancMemberProfilePresenter() {
        return (CoreAncMemberProfilePresenter) presenter;
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
    public void onClick(View view) {
        super.onClick(view);

    }

    @Override
    public void openVisitMonthView() {
        layoutNotRecordView.setVisibility(View.VISIBLE);
        layoutRecordButtonDone.setVisibility(View.GONE);
        layoutRecordView.setVisibility(View.GONE);

    }
    private void checkIfNOtVisited(){

        Visit lastNotDoneVisit = AncLibrary.getInstance().visitRepository().getLatestVisit(baseEntityID, org.smartregister.chw.anc.util.Constants.EVENT_TYPE.ANC_HOME_VISIT_NOT_DONE);
        if (lastNotDoneVisit != null) {
            Visit lastNotDoneVisitUndo = AncLibrary.getInstance().visitRepository().getLatestVisit(baseEntityID, org.smartregister.chw.anc.util.Constants.EVENT_TYPE.ANC_HOME_VISIT_NOT_DONE_UNDO);
            if (lastNotDoneVisitUndo != null
                    && lastNotDoneVisitUndo.getDate().after(lastNotDoneVisit.getDate())) {
                lastNotDoneVisit = null;
            }
        }
        if(lastNotDoneVisit!= null){
            String lastDt = new SimpleDateFormat("dd-MM-yyyy").format(lastNotDoneVisit.getDate());
            LocalDate firstMonthDayoflastVisitNotDoneDate = formatter.parseDateTime(lastDt).toLocalDate().withDayOfMonth(1);
            Boolean isWithinMonth = Months.monthsBetween(firstMonthDayoflastVisitNotDoneDate, todayDate).getMonths() < 1;

            if(isWithinMonth)
                openVisitMonthView();
            else
                textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_selector_overdue);
            layoutRecordView.setVisibility(View.VISIBLE);
            textViewAncVisitNot.setVisibility(View.VISIBLE);
            record_reccuringvisit_done_bar.setVisibility(View.GONE);
        }

        else {
            checkIfDueOrOverDue();
        }

    }

    private void checkIfDueOrOverDue() {
        Rules rules = CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.ANC_HOME_VISIT);
        VisitSummary visitSummary = HomeVisitUtil.getAncVisitStatus(this, rules, memberObject.getLastContactVisit(), null, new DateTime(memberObject.getDateCreated()).toLocalDate());
        String visitStatus = visitSummary.getVisitStatus();

        if (visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.OVERDUE)) {
                textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_selector_overdue);
                layoutRecordView.setVisibility(View.VISIBLE);
                textViewAncVisitNot.setVisibility(View.VISIBLE);
                record_reccuringvisit_done_bar.setVisibility(View.GONE);
            }
            if (visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE)) {
                textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_anc_selector);
                layoutRecordView.setVisibility(View.VISIBLE);
                textViewAncVisitNot.setVisibility(View.VISIBLE);
                record_reccuringvisit_done_bar.setVisibility(View.GONE);
            }

    }
    @Override
    public void setupViews() {
        super.setupViews();

        Visit lastVisit = getVisit(Constants.EVENT_TYPE.ANC_HOME_VISIT);
        if (lastVisit != null) {
            String lastDt = new SimpleDateFormat("dd-MM-yyyy").format(lastVisit.getDate());
            boolean within24Hours = VisitUtils.isVisitWithin24Hours(lastVisit);
            LocalDate firstMonthDayoflastVisitDate = formatter.parseDateTime(lastDt).toLocalDate().withDayOfMonth(1);
            Boolean isWithinMonth = Months.monthsBetween(firstMonthDayoflastVisitDate, todayDate).getMonths() < 1;
            if (!within24Hours && isWithinMonth)
                textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_selector_above_twentyfr);
            view_anc_record.setVisibility(View.GONE);
            textViewAncVisitNot.setVisibility(View.GONE);

            if (!isWithinMonth)
                checkIfNOtVisited();

        } else {
            checkIfNOtVisited();
        }
    }


/*
    @Override
    public void updateVisitNotDone(long value) {
       // super.updateVisitNotDone(0);
        layoutRecordView.setVisibility(View.GONE);
    }



       if (!visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE) &&
                !visitStatus.equalsIgnoreCase(CoreChildProfileInteractor.VisitType.OVERDUE.name())) {
            textview_record_anc_visit.setVisibility(View.GONE);
            view_anc_record.setVisibility(View.GONE);
            textViewAncVisitNot.setVisibility(View.GONE);
        }

        if (visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.OVERDUE) && !within24Hours) {
            textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_selector_overdue);
            layoutRecordView.setVisibility(View.VISIBLE);
            record_reccuringvisit_done_bar.setVisibility(View.GONE);
        }

        if(visitStatus.equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE) && !within24Hours){
            textview_record_anc_visit.setBackgroundResource(R.drawable.record_btn_anc_selector);
            layoutRecordView.setVisibility(View.VISIBLE);
            record_reccuringvisit_done_bar.setVisibility(View.GONE);
        }
*/



    @Override
    public abstract void setClientTasks(Set<Task> taskList);

}
