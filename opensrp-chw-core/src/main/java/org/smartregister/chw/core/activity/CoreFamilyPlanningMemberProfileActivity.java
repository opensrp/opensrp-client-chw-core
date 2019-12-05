package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.FamilyOtherMemberProfileExtendedContract;
import org.smartregister.chw.core.contract.FamilyProfileExtendedContract;
import org.smartregister.chw.core.presenter.CoreFamilyOtherMemberActivityPresenter;
import org.smartregister.chw.fp.activity.BaseFpProfileActivity;
import org.smartregister.chw.fp.interactor.BaseFpProfileInteractor;
import org.smartregister.chw.fp.presenter.BaseFpProfilePresenter;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;

import timber.log.Timber;

public abstract class CoreFamilyPlanningMemberProfileActivity extends BaseFpProfileActivity implements
        FamilyOtherMemberProfileExtendedContract.View, FamilyProfileExtendedContract.PresenterCallBack {

    @Override
    protected void onCreation() {
        super.onCreation();
    }

    @Override
    protected void initializePresenter() {
        fpProfilePresenter = new BaseFpProfilePresenter(this, new BaseFpProfileInteractor(), fpMemberObject);
        fpProfilePresenter.refreshProfileData();
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
}
