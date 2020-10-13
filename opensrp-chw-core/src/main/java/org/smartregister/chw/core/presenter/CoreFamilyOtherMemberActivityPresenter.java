package org.smartregister.chw.core.presenter;

import android.content.ContentValues;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.anc.util.Constants;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.contract.FamilyOtherMemberProfileExtendedContract;
import org.smartregister.chw.core.contract.FamilyProfileExtendedContract;
import org.smartregister.chw.core.dao.AncDao;
import org.smartregister.chw.core.interactor.CoreFamilyInteractor;
import org.smartregister.chw.core.interactor.CoreFamilyProfileInteractor;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.commonregistry.AllCommonsRepository;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.family.contract.FamilyOtherMemberContract;
import org.smartregister.family.contract.FamilyProfileContract;
import org.smartregister.family.domain.FamilyEventClient;
import org.smartregister.family.presenter.BaseFamilyOtherMemberProfileActivityPresenter;
import org.smartregister.family.util.DBConstants;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;

import java.lang.ref.WeakReference;
import java.text.MessageFormat;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static org.smartregister.util.Utils.getName;

public abstract class CoreFamilyOtherMemberActivityPresenter extends BaseFamilyOtherMemberProfileActivityPresenter implements FamilyOtherMemberProfileExtendedContract.Presenter, FamilyProfileContract.InteractorCallBack, FamilyProfileExtendedContract.PresenterCallBack {

    protected FamilyProfileContract.Interactor profileInteractor;
    protected FamilyProfileContract.Model profileModel;
    protected CoreFamilyInteractor familyInteractor;
    private WeakReference<FamilyOtherMemberProfileExtendedContract.View> viewReference;
    private String familyBaseEntityId;
    private String familyName;

    public CoreFamilyOtherMemberActivityPresenter(FamilyOtherMemberProfileExtendedContract.View view, FamilyOtherMemberContract.Model model,
                                                  String viewConfigurationIdentifier, String familyBaseEntityId, String baseEntityId,
                                                  String familyHead, String primaryCaregiver, String villageTown, String familyName) {
        super(view, model, viewConfigurationIdentifier, baseEntityId, familyHead, primaryCaregiver, villageTown);
        viewReference = new WeakReference<>(view);
        this.familyBaseEntityId = familyBaseEntityId;
        this.familyName = familyName;

        this.profileInteractor = getFamilyProfileInteractor();
        this.profileModel = getFamilyProfileModel(familyName);
        setProfileInteractor();
        verifyHasPhone();
        initializeServiceStatus();
    }

    protected abstract CoreFamilyProfileInteractor getFamilyProfileInteractor();

    protected abstract FamilyProfileContract.Model getFamilyProfileModel(String familyName);

    protected abstract void setProfileInteractor();

    @Override
    public void verifyHasPhone() {
        ((CoreFamilyProfileInteractor) profileInteractor).verifyHasPhone(familyBaseEntityId, this);
    }

    @Override
    public void notifyHasPhone(boolean hasPhone) {
        if (viewReference.get() != null) {
            viewReference.get().updateHasPhone(hasPhone);
        }
    }

    private void initializeServiceStatus() {
        familyInteractor.updateFamilyDueStatus(viewReference.get().getContext(), "", familyBaseEntityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.v("initializeServiceStatus onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        updateFamilyMemberServiceDue(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("initializeServiceStatus %s", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Timber.v("initializeServiceStatus onComplete");
                    }
                });
    }

    @Override
    public FamilyOtherMemberProfileExtendedContract.View getView() {
        if (viewReference != null) {
            return viewReference.get();
        } else {
            return null;
        }
    }

    @Override
    public void refreshProfileTopSection(CommonPersonObjectClient client) {
        super.refreshProfileTopSection(client);
        if (client != null && client.getColumnmaps() != null) {
            String firstName = Utils.getValue(client.getColumnmaps(), DBConstants.KEY.FIRST_NAME, true);
            String middleName = Utils.getValue(client.getColumnmaps(), DBConstants.KEY.MIDDLE_NAME, true);
            String lastName = Utils.getValue(client.getColumnmaps(), DBConstants.KEY.LAST_NAME, true);

            String dob = Utils.getValue(client.getColumnmaps(), DBConstants.KEY.DOB, true);
            int age = StringUtils.isNotBlank(dob) ? Utils.getAgeFromDate(dob) : 0;

            this.getView().setProfileName(MessageFormat.format("{0}, {1}", getName(getName(firstName, middleName), lastName), age));
            String gestationAge = CoreChwApplication.ancRegisterRepository().getGaIfAncWoman(client.getCaseId());
            if (gestationAge != null) {
                this.getView().setProfileDetailOne(NCUtils.gestationAgeString(gestationAge, viewReference.get().getContext(), true));
            }
        }
    }

    public String getFamilyBaseEntityId() {
        return familyBaseEntityId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getFamilyHeadBaseEntityId() {
        return familyHead;
    }

    @Override
    public void updateFamilyMember(String jsonString, boolean isIndependent) {

        try {
            getView().showProgressDialog(org.smartregister.family.R.string.saving_dialog_title);

            FamilyEventClient familyEventClient = profileModel.processUpdateMemberRegistration(jsonString, familyBaseEntityId);
            if (familyEventClient == null) {
                return;
            }
            if (isIndependent) {
                familyEventClient.getEvent().setEntityType(CoreConstants.TABLE_NAME.INDEPENDENT_CLIENT);
            }
            updateLocalStorage(familyEventClient.getClient().getBaseEntityId(), CoreConstants.JsonAssets.FAMILY_MEMBER.EVER_SCHOOL, getValueForKey(CoreConstants.JsonAssets.FAMILY_MEMBER.EVER_SCHOOL, jsonString));
            updateLocalStorage(familyEventClient.getClient().getBaseEntityId(), CoreConstants.JsonAssets.FAMILY_MEMBER.SCHOOL_LEVEL, getValueForKey(CoreConstants.JsonAssets.FAMILY_MEMBER.SCHOOL_LEVEL, jsonString));
            profileInteractor.saveRegistration(familyEventClient, jsonString, true, this);

            AllSharedPreferences allSharedPreferences = org.smartregister.util.Utils.getAllSharedPreferences();
            Event baseEvent = org.smartregister.chw.anc.util.JsonFormUtils.processJsonForm(allSharedPreferences, jsonString, Constants.TABLES.FAMILY_MEMBER);
            NCUtils.processEvent(baseEvent.getBaseEntityId(), new JSONObject(org.smartregister.chw.anc.util.JsonFormUtils.gson.toJson(baseEvent)));
        } catch (Exception e) {
            getView().hideProgressDialog();
            Timber.e(e);
        }
    }

    public String getValueForKey(String key, String jsonString) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject step1 = jsonObject.getJSONObject("step1");
            JSONArray fields = step1.getJSONArray("fields");
            for (int i = 0; i < fields.length(); i++) {
                JSONObject field = fields.getJSONObject(i);
                if (field.getString("key").equalsIgnoreCase(key)) {
                    value = field.optString("value", "");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    private void updateLocalStorage(String baseEntityId, String fieldName, String fieldValue) {
        if (getChildAllCommonsRepository() != null) {
            ContentValues values = new ContentValues();
            values.put(fieldName, fieldValue);
            getChildAllCommonsRepository().update(Constants.TABLES.FAMILY_MEMBER, values, baseEntityId);
        }
    }

    private AllCommonsRepository getChildAllCommonsRepository() {
        return CoreChwApplication.getInstance().getAllCommonsRepository(Constants.TABLES.FAMILY_MEMBER);
    }


    @Override
    public void updateFamilyMemberServiceDue(String serviceDueStatus) {
        if (getView() != null) {
            getView().setFamilyServiceStatus(serviceDueStatus);
        }

    }

    public boolean isWomanAlreadyRegisteredOnAnc(CommonPersonObjectClient client) {
        return AncDao.isANCMember(client.entityId());
    }

    @Override
    public void startFormForEdit(CommonPersonObjectClient commonPersonObject) {
    }

    @Override
    public void onUniqueIdFetched(Triple<String, String, String> triple, String entityId) {
        //TODO Implement
        Timber.d("onUniqueIdFetched unimplemented");
    }

    @Override
    public void onNoUniqueId() {
        //TODO Implement
        Timber.d("onNoUniqueId unimplemented");
    }

    @Override
    public void onRegistrationSaved(boolean editMode, boolean isSaved, FamilyEventClient familyEventClient) {
        if (editMode) {
            getView().hideProgressDialog();

            refreshProfileView();

            getView().refreshList();
        }
    }
}
