package org.smartregister.chw.core.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.dao.PNCDao;
import org.smartregister.chw.core.model.ChildModel;
import org.smartregister.chw.core.utils.ChildDBConstants;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.Task;
import org.smartregister.family.util.DBConstants;
import org.smartregister.family.util.Utils;
import org.smartregister.view.activity.SecuredActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.List;

import timber.log.Timber;

import static org.smartregister.chw.core.utils.Utils.updateToolbarTitle;

public abstract class BaseReferralTaskViewActivity extends SecuredActivity {

    protected String name;
    protected String baseEntityId;
    protected Task task;
    protected CustomFontTextView womanGa;
    protected LinearLayout womanGaLayout;
    protected MemberObject memberObject;
    protected String familyHeadName;
    protected String familyHeadPhoneNumber;
    protected CustomFontTextView clientName;
    protected CustomFontTextView careGiverName;
    protected CustomFontTextView childName;
    protected CustomFontTextView careGiverPhone;
    protected CustomFontTextView clientReferralProblem;
    protected CustomFontTextView referralDate;
    protected CustomFontTextView chwDetailsNames;
    protected LinearLayout careGiverLayout;
    protected LinearLayout childNameLayout;
    protected AppBarLayout appBarLayout;
    protected String startingActivity;
    private CommonPersonObjectClient personObjectClient;
    private String clientAge;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    protected void extractPersonObjectClient() {
        setPersonObjectClient((CommonPersonObjectClient) getIntent().getSerializableExtra(CoreConstants.INTENT_KEY.CHILD_COMMON_PERSON));
        if (getPersonObjectClient() != null) {
            name = Utils.getValue(getPersonObjectClient().getColumnmaps(), DBConstants.KEY.FIRST_NAME, true) + " " + Utils.getValue(getPersonObjectClient().getColumnmaps(), DBConstants.KEY.MIDDLE_NAME, true) + " " + Utils.getValue(getPersonObjectClient().getColumnmaps(), DBConstants.KEY.LAST_NAME, true);
            setBaseEntityId(Utils.getValue(getPersonObjectClient().getColumnmaps(), DBConstants.KEY.BASE_ENTITY_ID, false));
        }

        if (getPersonObjectClient() == null) {
            Timber.e("ReferralTaskViewActivity --> The person object is null");
            finish();
        }
    }

    public CommonPersonObjectClient getPersonObjectClient() {
        return personObjectClient;
    }

    public void setPersonObjectClient(CommonPersonObjectClient personObjectClient) {
        this.personObjectClient = personObjectClient;
    }

    public void setBaseEntityId(String baseEntityId) {
        this.baseEntityId = baseEntityId;
    }

    protected void extraClientTask() {
        setTask((Task) getIntent().getSerializableExtra(CoreConstants.INTENT_KEY.USERS_TASKS));

        if (getTask() == null) {
            Timber.e("ReferralTaskViewActivity --> The task object is null");
            finish();
        }
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public MemberObject getMemberObject() {
        return memberObject;
    }

    protected void addGaDisplay() {
        if (CoreConstants.TASKS_FOCUS.ANC_DANGER_SIGNS.equals(getTask().getFocus())) {
            womanGaLayout.setVisibility(View.VISIBLE);

            String gaWeeks = getMemberObject().getGestationAge() + " " + getString(R.string.weeks);
            womanGa.setText(gaWeeks);
        }
    }

    protected void extraDetails() {
        setMemberObject((MemberObject) getIntent().getSerializableExtra(CoreConstants.INTENT_KEY.MEMBER_OBJECT));
        setFamilyHeadName((String) getIntent().getSerializableExtra(CoreConstants.INTENT_KEY.FAMILY_HEAD_NAME));
        setFamilyHeadPhoneNumber((String) getIntent().getSerializableExtra(CoreConstants.INTENT_KEY.FAMILY_HEAD_PHONE_NUMBER));
    }

    public void setMemberObject(MemberObject memberObject) {
        this.memberObject = memberObject;
    }

    public void setFamilyHeadName(String familyHeadName) {
        this.familyHeadName = familyHeadName;
    }

    public void setFamilyHeadPhoneNumber(String familyHeadPhoneNumber) {
        this.familyHeadPhoneNumber = familyHeadPhoneNumber;
    }

    public String getFamilyHeadPhoneNumber() {
        return familyHeadPhoneNumber;
    }

    protected void getReferralDetails() {
        if (getPersonObjectClient() != null && getTask() != null) {
            updateProblemDisplay();
            clientAge = (Utils.getTranslatedDate(Utils.getDuration(Utils.getValue(getPersonObjectClient().getColumnmaps(), DBConstants.KEY.DOB, false)), getBaseContext()));
            clientName.setText(getString(R.string.client_name_age_suffix, name, clientAge));
            referralDate.setText(org.smartregister.chw.core.utils.Utils.dd_MMM_yyyy.format(getTask().getExecutionStartDate().toDate()));

            String parentFirstName = Utils.getValue(getPersonObjectClient().getColumnmaps(), ChildDBConstants.KEY.FAMILY_FIRST_NAME, true);
            String parentLastName = Utils.getValue(getPersonObjectClient().getColumnmaps(), ChildDBConstants.KEY.FAMILY_LAST_NAME, true);
            String parentMiddleName = Utils.getValue(getPersonObjectClient().getColumnmaps(), ChildDBConstants.KEY.FAMILY_MIDDLE_NAME, true);
            String parentName = getString(R.string.care_giver_prefix, org.smartregister.util.Utils.getName(parentFirstName, parentMiddleName + " " + parentLastName));

            //For PNC get children belonging to the woman
            String childrenForPncWoman = getChildrenForPncWoman(getPersonObjectClient().entityId());
            if (getTask().getFocus().equalsIgnoreCase(CoreConstants.TASKS_FOCUS.PNC_DANGER_SIGNS) &&
                    StringUtils.isNoneEmpty(childrenForPncWoman)) {
                childName.setText(childrenForPncWoman);
                childNameLayout.setVisibility(View.VISIBLE);
            }

            //Hide Care giver for ANC referral
            careGiverLayout.setVisibility(View.GONE);
            if (getTask().getFocus().equalsIgnoreCase(CoreConstants.TASKS_FOCUS.SICK_CHILD)) {
                // CG only shows for CHILD clients
                careGiverLayout.setVisibility(View.VISIBLE);
            }

            careGiverName.setText(parentName);
            careGiverPhone.setText(getFamilyMemberContacts().isEmpty() || getFamilyMemberContacts() == null ? getString(R.string.phone_not_provided) : getFamilyMemberContacts());

            chwDetailsNames.setText(getTask().getRequester());

            addGaDisplay();
        }
    }

    private String getChildrenForPncWoman(String baseEntityId) {
        List<ChildModel> childModels = PNCDao.childrenForPncWoman(baseEntityId);
        StringBuilder stringBuilder = new StringBuilder();
        for (ChildModel childModel : childModels) {
            String childAge = (Utils.getTranslatedDate(Utils.getDuration(childModel.getDateOfBirth()), getBaseContext()));
            stringBuilder.append(childModel.getChildFullName())
                    .append(", ")
                    .append(childAge)
                    .append("; ");
        }

        String children = stringBuilder.toString().replaceAll("; $", "").trim();

        return childModels.size() == 1 ? getString(R.string.child_prefix, children) :
                getString(R.string.children_prefix, children);
    }

    private void updateProblemDisplay() {
        if (CoreConstants.TASKS_FOCUS.ANC_DANGER_SIGNS.equals(getTask().getFocus())) {
            clientReferralProblem.setText(getString(R.string.anc_danger_sign_prefix, getTask().getDescription()));
        } else {
            clientReferralProblem.setText(getTask().getDescription());
        }
    }

    private String getFamilyMemberContacts() {
        String phoneNumber = "";
        String familyPhoneNumber = Utils.getValue(getPersonObjectClient().getColumnmaps(), ChildDBConstants.KEY.FAMILY_MEMBER_PHONENUMBER, true);
        String familyPhoneNumberOther = Utils.getValue(getPersonObjectClient().getColumnmaps(), ChildDBConstants.KEY.FAMILY_MEMBER_PHONENUMBER_OTHER, true);
        if (StringUtils.isNoneEmpty(familyPhoneNumber)) {
            phoneNumber = familyPhoneNumber;
        } else if (StringUtils.isEmpty(familyPhoneNumber) && StringUtils.isNoneEmpty(familyPhoneNumberOther)) {
            phoneNumber = familyPhoneNumberOther;
        } else if (StringUtils.isNoneEmpty(familyPhoneNumber) && StringUtils.isNoneEmpty(familyPhoneNumberOther)) {
            phoneNumber = familyPhoneNumber + ", " + familyPhoneNumberOther;
        } else if (StringUtils.isNoneEmpty(getFamilyHeadPhoneNumber())) {
            phoneNumber = getFamilyHeadPhoneNumber();
        }

        return phoneNumber;
    }

    public String getFamilyHeadName() {
        return familyHeadName;
    }

    protected void inflateToolbar() {
        Toolbar toolbar = findViewById(R.id.back_referrals_toolbar);
        CustomFontTextView toolBarTextView = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            upArrow.setColorFilter(getResources().getColor(R.color.text_blue), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setElevation(0);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        if (getStartingActivity().equals(CoreConstants.REGISTERED_ACTIVITIES.REFERRALS_REGISTER_ACTIVITY)) {
            toolBarTextView.setText(R.string.back_to_referrals);
        } else {
            if (TextUtils.isEmpty(name)) {
                toolBarTextView.setText(R.string.back_to_referrals);
            } else {
                toolBarTextView.setText(getString(R.string.return_to, name));
            }
        }
        toolBarTextView.setOnClickListener(v -> finish());
        appBarLayout = findViewById(R.id.app_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setOutlineProvider(null);
        }
        updateToolbarTitle(this, R.id.toolbar_title, "");
    }

    public String getStartingActivity() {
        return startingActivity;
    }
}
