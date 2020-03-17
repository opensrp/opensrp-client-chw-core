package org.smartregister.chw.core.task;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.json.JSONArray;
import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.HIA2ReportsActivity;
import org.smartregister.chw.core.activity.ServiceJsonFormActivity;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.domain.Hia2Indicator;
import org.smartregister.chw.core.domain.MonthlyTally;
import org.smartregister.chw.core.repository.HIA2IndicatorsRepository;
import org.smartregister.chw.core.repository.MonthlyTalliesRepository;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.Utils;
import org.smartregister.util.FormUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

import static com.vijay.jsonwizard.constants.JsonFormConstants.REPORT_MONTH;

public class StartDraftMonthlyFormTask extends AsyncTask<Void, Void, Intent> {
    private final HIA2ReportsActivity baseActivity;
    private final Date date;
    private final String formName;
    private final List<String> readOnlyList;

    public StartDraftMonthlyFormTask(HIA2ReportsActivity baseActivity,
                                     Date date, String formName, List<String> readOnlyList) {
        this.baseActivity = baseActivity;
        this.date = date;
        this.formName = formName;
        this.readOnlyList = readOnlyList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        baseActivity.showProgressDialog();
    }

    @Override
    protected Intent doInBackground(Void... params) {
        try {
            MonthlyTalliesRepository monthlyTalliesRepository = CoreChwApplication.getInstance().monthlyTalliesRepository();
            List<MonthlyTally> monthlyTallies = monthlyTalliesRepository.findDrafts(MonthlyTalliesRepository.DF_YYYYMM.format(date));

            HIA2IndicatorsRepository hIA2IndicatorsRepository = CoreChwApplication.getInstance().hIA2IndicatorsRepository();
            List<Hia2Indicator> hia2Indicators = hIA2IndicatorsRepository.fetchAll();
            if (hia2Indicators == null || hia2Indicators.isEmpty()) {
                return null;
            }
            JSONObject form = new FormUtils(baseActivity).getFormJson(formName);
            JSONObject step1 = form.getJSONObject("step1");
            JSONObject step2 = form.getJSONObject("step2");
            JSONObject step3 = form.getJSONObject("step3");
            JSONObject step4 = form.getJSONObject("step4");
            JSONObject step5 = form.getJSONObject("step5");
            JSONObject step6 = form.getJSONObject("step6");
            JSONObject step7 = form.getJSONObject("step7");
            JSONObject step8 = form.getJSONObject("step8");
            JSONObject step9 = form.getJSONObject("step9");
            JSONObject step10 = form.getJSONObject("step10");
            JSONObject step11 = form.getJSONObject("step11");
            JSONObject step12 = form.getJSONObject("step12");

            JSONArray fieldsArray = step1.getJSONArray("fields");
            JSONArray fieldsArray2 = step2.getJSONArray("fields");
            JSONArray fieldsArray3 = step3.getJSONArray("fields");
            JSONArray fieldsArray4 = step4.getJSONArray("fields");
            JSONArray fieldsArray5 = step5.getJSONArray("fields");
            JSONArray fieldsArray6 = step6.getJSONArray("fields");
            JSONArray fieldsArray7 = step7.getJSONArray("fields");
            JSONArray fieldsArray8 = step8.getJSONArray("fields");
            JSONArray fieldsArray9 = step9.getJSONArray("fields");
            JSONArray fieldsArray10= step10.getJSONArray("fields");
            JSONArray fieldsArray11 = step11.getJSONArray("fields");
            JSONArray fieldsArray12 = step12.getJSONArray("fields");

            int i = 1;
            // This map holds each category as key and all the fields for that category as the
            // value (jsonarray)
            for (Hia2Indicator hia2Indicator : hia2Indicators) {
                JSONObject jsonObject = new JSONObject();
                if (hia2Indicator.getDescription() == null) {
                    hia2Indicator.setDescription("");
                }
                int resourceId = baseActivity.getResources().getIdentifier(hia2Indicator.getDescription(), "string", baseActivity.getPackageName());
                String label = baseActivity.getResources().getString(resourceId);

                JSONObject vRequired = new JSONObject();
                vRequired.put(JsonFormConstants.VALUE, "true");
                vRequired.put(JsonFormConstants.ERR, "Specify: " + hia2Indicator.getDescription());
                JSONObject vNumeric = new JSONObject();
                vNumeric.put(JsonFormConstants.VALUE, "true");
                vNumeric.put(JsonFormConstants.ERR, "Value should be numeric");

                jsonObject.put(JsonFormConstants.KEY, hia2Indicator.getIndicatorCode());
                jsonObject.put(JsonFormConstants.TYPE, "edit_text");
                //jsonObject.put(JsonFormConstants.READ_ONLY, HIA2ReportsActivity.getReadOnlyList().contains(hia2Indicator.getIndicatorCode()));
                jsonObject.put(JsonFormConstants.HINT, label);
                jsonObject.put(JsonFormConstants.VALUE, Utils.retrieveValue(monthlyTallies, hia2Indicator));
                jsonObject.put(JsonFormConstants.V_REQUIRED, vRequired);
                jsonObject.put(JsonFormConstants.V_NUMERIC, vNumeric);
                jsonObject.put(JsonFormConstants.OPENMRS_ENTITY_PARENT, "");
                jsonObject.put(JsonFormConstants.OPENMRS_ENTITY, "");
                jsonObject.put(JsonFormConstants.OPENMRS_ENTITY_ID, "");
                jsonObject.put(CoreConstants.KEY.HIA_2_INDICATOR, hia2Indicator.getIndicatorCode());

                if (i <= 5) {
                    fieldsArray.put(jsonObject);
                    i++;
                }else if (i <= 9) {
                    fieldsArray2.put(jsonObject);
                    i++;
                } else if (i <= 10) {
                    fieldsArray3.put(jsonObject);
                    i++;
                } else if (i <= 15) {
                    fieldsArray4.put(jsonObject);
                    i++;
                } else if (i <= 17) {
                    fieldsArray5.put(jsonObject);
                    i++;
                }
                else if (i <= 29) {
                    fieldsArray6.put(jsonObject);
                    i++;
                }else if (i <= 44) {
                    fieldsArray7.put(jsonObject);
                    i++;
                } else if (i <= 64) {
                    fieldsArray8.put(jsonObject);
                    i++;
                } else if (i <= 79) {
                    fieldsArray9.put(jsonObject);
                    i++;
                } else if (i <= 84) {
                    fieldsArray10.put(jsonObject);
                    i++;
                }
                else if (i <= 99) {
                    fieldsArray11.put(jsonObject);
                    i++;
                }else {
                    fieldsArray12.put(jsonObject);
                    i++;
                }
            }

            // Add the confirm button
            JSONObject buttonObject = new JSONObject();
            buttonObject.put(JsonFormConstants.KEY, HIA2ReportsActivity.FORM_KEY_CONFIRM);
            buttonObject.put(JsonFormConstants.VALUE, "false");
            buttonObject.put(JsonFormConstants.TYPE, "button");
            buttonObject.put(JsonFormConstants.HINT, baseActivity.getBaseContext().getString(R.string.confirm_button_label));
            buttonObject.put(JsonFormConstants.OPENMRS_ENTITY_PARENT, "");
            buttonObject.put(JsonFormConstants.OPENMRS_ENTITY, "");
            buttonObject.put(JsonFormConstants.OPENMRS_ENTITY_ID, "");
            JSONObject action = new JSONObject();
            action.put(JsonFormConstants.BEHAVIOUR, "finish_form");
            buttonObject.put(JsonFormConstants.ACTION, action);

            fieldsArray12.put(buttonObject);

            form.put(REPORT_MONTH, HIA2ReportsActivity.dfyymmdd.format(date));
            form.put("identifier", "HIA2ReportForm");

            Intent intent = new Intent(baseActivity, ServiceJsonFormActivity.class);
            intent.putExtra("json", form.toString());

            SimpleDateFormat DF_YYYYMM = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
            String title = DF_YYYYMM.format(date).concat(" " +
                    baseActivity.getBaseContext().getString(R.string.draft));

            Form paramForm = new Form();
            paramForm.setName(title);
            paramForm.setWizard(true);
            paramForm.setHideNextButton(true);
            paramForm.setHidePreviousButton(true);
            paramForm.setNavigationBackground(R.color.due_profile_blue);
            intent.putExtra("form", paramForm);

            intent.putExtra(JsonFormConstants.SKIP_VALIDATION, false);

            return intent;
        } catch (Exception e) {
            Timber.e(Log.getStackTraceString(e));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Intent intent) {
        super.onPostExecute(intent);
        baseActivity.hideProgressDialog();
        if (intent != null) {
            baseActivity.startActivityForResult(intent, HIA2ReportsActivity.REQUEST_CODE_GET_JSON);
        }
    }
}