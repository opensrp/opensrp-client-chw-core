package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.jeasy.rules.api.Rules;
import org.json.JSONObject;
import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.FamilyOtherMemberProfileExtendedContract;
import org.smartregister.chw.core.contract.FamilyProfileExtendedContract;
import org.smartregister.chw.core.interactor.CoreFamilyPlanningProfileInteractor;
import org.smartregister.chw.core.presenter.CoreFamilyOtherMemberActivityPresenter;
import org.smartregister.chw.core.rule.FpAlertRule;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.FpUtil;
import org.smartregister.chw.core.utils.HomeVisitUtil;
import org.smartregister.chw.fp.activity.BaseFpProfileActivity;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.presenter.BaseFpProfilePresenter;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;

import java.util.Date;

import timber.log.Timber;

public abstract class CoreFamilyPlanningMemberProfileActivity extends BaseFpProfileActivity implements
        FamilyOtherMemberProfileExtendedContract.View, FamilyProfileExtendedContract.PresenterCallBack {

    @Override
    protected void onCreation() {
        super.onCreation();
    }

    @Override
    public void setupViews() {
        super.setupViews();
      /*  if (hasFollowUp(fpMemberObject.getFpMethod())) {
            new UpdateFollowUpVisitButtonTask(fpMemberObject).execute();
        }*/
        new UpdateFollowUpVisitButtonTask(fpMemberObject).execute();
    }

    @Override
    protected void initializePresenter() {
        showProgressBar(true);
        fpProfilePresenter = new BaseFpProfilePresenter(this, new CoreFamilyPlanningProfileInteractor(), fpMemberObject);
        fetchProfileData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_registration) {
            startFormForEdit(R.string.registration_info,
                    CoreConstants.JSON_FORM.FAMILY_MEMBER_REGISTER);
            return true;
        } else if (itemId == R.id.action_remove_member) {
            removeMember();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.family_planning_member_profile_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CoreConstants.ProfileActivityResults.CHANGE_COMPLETED:
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent(this, getFamilyProfileActivityClass());
                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);
                    finish();
                }
                break;
            case JsonFormUtils.REQUEST_CODE_GET_JSON:
                if (resultCode == RESULT_OK) {
                    try {
                        String jsonString = data.getStringExtra(org.smartregister.family.util.Constants.JSON_FORM_EXTRA.JSON);
                        JSONObject form = new JSONObject(jsonString);
                        if (form.getString(JsonFormUtils.ENCOUNTER_TYPE).equals(Utils.metadata().familyMemberRegister.updateEventType)) {
                            presenter().updateFamilyMember(jsonString);
                        }
                    } catch (Exception e) {
                        Timber.e(e);
                    }
                }
                break;
            default:
                break;
        }
    }

    protected abstract Class<? extends CoreFamilyProfileActivity> getFamilyProfileActivityClass();

    protected abstract void removeMember();

    @NonNull
    @Override
    public abstract CoreFamilyOtherMemberActivityPresenter presenter();

    @Override
    public void setProfileName(@NonNull String s) {
        TextView textView = findViewById(org.smartregister.malaria.R.id.textview_name);
        textView.setText(s);
    }

    @Override
    public void setProfileDetailOne(@NonNull String s) {
        TextView textView = findViewById(org.smartregister.malaria.R.id.textview_gender);
        textView.setText(s);
    }

    @Override
    public void setProfileDetailTwo(@NonNull String s) {
        TextView textView = findViewById(org.smartregister.malaria.R.id.textview_address);
        textView.setText(s);
    }

    public void startFormForEdit(Integer title_resource, String formName) {

        JSONObject form = null;
        CommonPersonObjectClient client = org.smartregister.chw.core.utils.Utils.clientForEdit(fpMemberObject.getBaseEntityId());

        if (formName.equals(CoreConstants.JSON_FORM.getFamilyMemberRegister())) {
            form = CoreJsonFormUtils.getAutoPopulatedJsonEditMemberFormString(
                    (title_resource != null) ? getResources().getString(title_resource) : null,
                    CoreConstants.JSON_FORM.getFamilyMemberRegister(),
                    this, client,
                    Utils.metadata().familyMemberRegister.updateEventType, fpMemberObject.getLastName(), false);
        } else if (formName.equals(CoreConstants.JSON_FORM.getAncRegistration())) {
            form = CoreJsonFormUtils.getAutoJsonEditAncFormString(
                    fpMemberObject.getBaseEntityId(), this, formName, FamilyPlanningConstants.EventType.FAMILY_PLANNING_REGISTRATION, getResources().getString(title_resource));
        }

        try {
            assert form != null;
            startFormActivity(form, fpMemberObject);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void startFormActivity(JSONObject jsonForm, FpMemberObject fpMemberObject) {
        Intent intent = org.smartregister.chw.core.utils.Utils.formActivityIntent(this, jsonForm.toString());
        intent.putExtra(FamilyPlanningConstants.FamilyPlanningMemberObject.MEMBER_OBJECT, fpMemberObject);
        startActivityForResult(intent, JsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    private void updateFollowUpVisitButton(String buttonStatus) {
        switch (buttonStatus) {
            case CoreConstants.VISIT_STATE.DUE:
                setFollowUpButtonDue();
                break;
            case CoreConstants.VISIT_STATE.OVERDUE:
                setFollowUpButtonOverdue();
                break;
            default:
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    private class UpdateFollowUpVisitButtonTask extends AsyncTask<Void, Void, Void> {
        private final FpMemberObject fpMemberObject;
        private FpAlertRule fpAlertRule;

        public UpdateFollowUpVisitButtonTask(FpMemberObject fpMemberObject) {
            this.fpMemberObject = fpMemberObject;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Visit lastVisit = AncLibrary.getInstance().visitRepository().getLatestVisit(fpMemberObject.getBaseEntityId(), FamilyPlanningConstants.EventType.FP_HOME_VISIT);
            Date lastVisitDate = lastVisit != null ? lastVisit.getDate() : null;

            Rules rule = FpUtil.getFpRules(fpMemberObject.getFpMethod());
            fpAlertRule = HomeVisitUtil.getFpVisitStatus(rule, lastVisitDate, FpUtil.parseFpStartDate(fpMemberObject.getFpStartDate()), fpMemberObject.getPillCycles(), fpMemberObject.getFpMethod());
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            if (fpAlertRule != null && (fpAlertRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.OVERDUE) ||
                    fpAlertRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE))
            ) {
                updateFollowUpVisitButton(fpAlertRule.getButtonStatus());
            }
        }
    }

}
