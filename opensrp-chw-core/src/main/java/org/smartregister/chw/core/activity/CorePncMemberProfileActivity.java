package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.dao.PNCDao;
import org.smartregister.chw.core.interactor.CoreChildProfileInteractor;
import org.smartregister.chw.core.interactor.CorePncMemberProfileInteractor;
import org.smartregister.chw.core.model.ChildModel;
import org.smartregister.chw.core.rule.MalariaFollowUpRule;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.MalariaVisitUtil;
import org.smartregister.chw.malaria.dao.MalariaDao;
import org.smartregister.chw.pnc.activity.BasePncMemberProfileActivity;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.AlertStatus;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.util.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public abstract class CorePncMemberProfileActivity extends BasePncMemberProfileActivity {

    protected ImageView imageViewCross;
    protected boolean hasDueServices = false;
    protected CorePncMemberProfileInteractor pncMemberProfileInteractor = getPncMemberProfileInteractor();
    protected HashMap<String, String> menuItemEditNames = new HashMap<>();
    protected HashMap<String, String> menuItemRemoveNames = new HashMap<>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_pnc_member_registration) {
            JSONObject form = CoreJsonFormUtils.getAncPncForm(R.string.edit_member_form_title, CoreConstants.JSON_FORM.getFamilyMemberRegister(), memberObject, this);
            startActivityForResult(CoreJsonFormUtils.getAncPncStartFormIntent(form, this), JsonFormUtils.REQUEST_CODE_GET_JSON);
            return true;
        } else if (itemId == R.id.action_pnc_registration) {
            getEditMenuItem(item);
        } else if (itemId == R.id.action_malaria_registration) {
            startMalariaRegister();
            return true;
        } else if (itemId == R.id.action_malaria_followup_visit) {
            startMalariaFollowUpVisit();
            return true;
        } else if (itemId == R.id.action_fp_initiation_pnc) {
            startFpRegister();
            return true;
        } else if (itemId == R.id.action_fp_change) {
            startFpChangeMethod();
            return true;
        } else if (itemId == R.id.action__pnc_remove_member) {
            removePncMember();
            return true;
        } else if (itemId == R.id.action_pnc_remove_baby) {
            getRemoveBabyMenuItem(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected List<CommonPersonObjectClient> getChildren(MemberObject memberObject) {
        return pncMemberProfileInteractor.pncChildrenUnder29Days(memberObject.getBaseEntityId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pnc_member_profile_menu, menu);
        List<ChildModel> childModels = PNCDao.childrenForPncWoman(memberObject.getBaseEntityId());
        for (int i = 0; i < childModels.size(); i++) {
            menu.add(0, R.id.action_pnc_registration, 100 + i, getString(R.string.edit_child_form_title, childModels.get(i).getFirstName()));
            menuItemEditNames.put(getString(R.string.edit_child_form_title, childModels.get(i).getFirstName()), childModels.get(i).getBaseEntityId());
            menu.add(0, R.id.action_pnc_remove_baby, 700 + i, getString(R.string.remove_child_form_title, childModels.get(i).getFirstName()));
            menuItemRemoveNames.put(getString(R.string.remove_child_form_title, childModels.get(i).getFirstName()), childModels.get(i).getBaseEntityId());
            if (MalariaDao.isRegisteredForMalaria(baseEntityID)) {
                Utils.startAsyncTask(new UpdateMalariaFollowUpStatusTask(menu, baseEntityID), null);
            } else {
                menu.findItem(R.id.action_malaria_registration).setVisible(true);
            }
        }
        return true;
    }

    private void setMalariaFollowUpMenuItems(String followStatus, Menu menu) {
        if (CoreConstants.VISIT_STATE.DUE.equalsIgnoreCase(followStatus) || CoreConstants.VISIT_STATE.OVERDUE.equalsIgnoreCase(followStatus)) {
            menu.findItem(R.id.action_malaria_followup_visit).setVisible(true);
        }
    }

    private boolean getEditMenuItem(MenuItem item) {
        if (getChildren(memberObject).size() > 0) {
            for (CommonPersonObjectClient child : getChildren(memberObject)) {
                for (Map.Entry<String, String> entry : menuItemEditNames.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase(item.getTitle().toString()) && entry.getValue().equalsIgnoreCase(child.entityId())) {
                        CoreChildProfileInteractor childProfileInteractor = new CoreChildProfileInteractor();
                        JSONObject childEnrollmentForm = childProfileInteractor.getAutoPopulatedJsonEditFormString(CoreConstants.JSON_FORM.getChildRegister(), entry.getKey(), this, child);
                        startFormForEdit(org.smartregister.chw.anc.util.JsonFormUtils.setRequiredFieldsToFalseForPncChild(childEnrollmentForm, memberObject.getFamilyBaseEntityId(),
                                memberObject.getBaseEntityId()));
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == CoreConstants.ProfileActivityResults.CHANGE_COMPLETED) {
            Intent intent = new Intent(this, getPncRegisterActivityClass());
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
            finish();
        }
    }

    public void startFormForEdit(JSONObject form) {
        try {
            startActivityForResult(CoreJsonFormUtils.getAncPncStartFormIntent(form, this), JsonFormUtils.REQUEST_CODE_GET_JSON);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void setupViews() {
        super.setupViews();
        imageViewCross = findViewById(R.id.tick_image);
        imageViewCross.setOnClickListener(this);
    }

    @Override
    public void openFamilyDueServices() {
        Intent intent = new Intent(this, getFamilyProfileActivityClass());

        intent.putExtra(org.smartregister.family.util.Constants.INTENT_KEY.FAMILY_BASE_ENTITY_ID, memberObject.getFamilyBaseEntityId());
        intent.putExtra(org.smartregister.family.util.Constants.INTENT_KEY.FAMILY_HEAD, memberObject.getFamilyHead());
        intent.putExtra(org.smartregister.family.util.Constants.INTENT_KEY.PRIMARY_CAREGIVER, memberObject.getPrimaryCareGiver());
        intent.putExtra(org.smartregister.family.util.Constants.INTENT_KEY.FAMILY_NAME, memberObject.getFamilyName());

        intent.putExtra(CoreConstants.INTENT_KEY.SERVICE_DUE, hasDueServices);
        startActivity(intent);
    }

    @Override
    public void updateVisitNotDone(long value) {
        //Overridden
    }

    @Override
    public void setFamilyStatus(AlertStatus status) {
        TextView tvFamilyStatus;
        tvFamilyStatus = findViewById(R.id.textview_family_has);

        view_family_row.setVisibility(View.VISIBLE);
        rlFamilyServicesDue.setVisibility(View.VISIBLE);

        if (status == AlertStatus.complete) {
            hasDueServices = false;
            tvFamilyStatus.setText(getString(R.string.family_has_nothing_due));
        } else if (status == AlertStatus.normal) {
            hasDueServices = true;
            tvFamilyStatus.setText(R.string.family_has_services_due);
        } else if (status == AlertStatus.urgent) {
            hasDueServices = true;
            tvFamilyStatus.setText(NCUtils.fromHtml(getString(R.string.family_has_service_overdue)));
        }
    }

    private class UpdateMalariaFollowUpStatusTask extends AsyncTask<Void, Void, Void> {
        private final Menu menu;
        private final String baseEntityId;
        private MalariaFollowUpRule malariaFollowUpRule;

        private UpdateMalariaFollowUpStatusTask(Menu menu, String baseEntityId) {
            this.menu = menu;
            this.baseEntityId = baseEntityId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Date malariaTestDate = MalariaDao.getMalariaTestDate(baseEntityId);
            Date followUpDate = MalariaDao.getMalariaFollowUpVisitDate(baseEntityId);
            malariaFollowUpRule = MalariaVisitUtil.getMalariaStatus(malariaTestDate, followUpDate);
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            if (malariaFollowUpRule != null) {
                setMalariaFollowUpMenuItems(malariaFollowUpRule.getButtonStatus(), menu);
            }
        }
    }

    protected abstract Class<? extends CoreFamilyProfileActivity> getFamilyProfileActivityClass();

    protected abstract CorePncMemberProfileInteractor getPncMemberProfileInteractor();

    protected abstract void removePncMember();

    protected abstract Class<? extends CorePncRegisterActivity> getPncRegisterActivityClass();

    protected abstract void startMalariaRegister();

    protected abstract void startFpRegister();

    protected abstract void startFpChangeMethod();

    protected abstract void startMalariaFollowUpVisit();

    protected abstract void getRemoveBabyMenuItem(MenuItem item);

}