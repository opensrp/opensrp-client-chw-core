package org.smartregister.chw.core.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.anc.util.JsonFormUtils;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.dao.StockUsageReportDao;
import org.smartregister.chw.core.domain.StockUsage;
import org.smartregister.chw.core.repository.StockUsageReportRepository;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.FormUtils;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.BaseRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.smartregister.chw.anc.util.NCUtils.getSyncHelper;
import static org.smartregister.util.Utils.getAllSharedPreferences;

public class StockUsageReportService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public StockUsageReportService() {
        super("StockUsageReportService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (!StockUsageReportDao.lastInteractedWithinDay()) {
            try {

                StockUsageReportRepository repo = CoreChwApplication.getInstance().getStockUsageRepository();

                JSONObject form = FormUtils.getFormUtils().getFormJson(CoreConstants.JSON_FORM.getStockUsageForm());
                JSONObject stepOne = form.getJSONObject(JsonFormUtils.STEP1);
                JSONArray jsonArray = stepOne.getJSONArray(JsonFormUtils.FIELDS);
                AllSharedPreferences allSharedPreferences = getAllSharedPreferences();


                repo.deleteAll();
                List<StockUsage> usages = StockUsageReportDao.getStockUsage();

                for (StockUsage usage : usages) {
                    if (StringUtils.isBlank(usage.getStockName()) || StringUtils.isBlank(usage.getYear()) && StringUtils.isBlank(usage.getMonth()) && StringUtils.isBlank(usage.getStockUsage())) {
                        return;
                    }

                    repo.addStockUsage(usage);
                    addEvent(form, jsonArray, usage, allSharedPreferences);
                }

                long lastSyncTimeStamp = getAllSharedPreferences().fetchLastUpdatedAtDate(0);
                Date lastSyncDate = new Date(lastSyncTimeStamp);
                NCUtils.getClientProcessorForJava().processClient(getSyncHelper().getEvents(lastSyncDate, BaseRepository.TYPE_Unprocessed));
                getAllSharedPreferences().saveLastUpdatedAtDate(lastSyncDate.getTime());

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addEvent(JSONObject form, JSONArray jsonArray, StockUsage usage, AllSharedPreferences allSharedPreferences) throws JSONException {
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_NAME, usage.getStockName());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_YEAR, usage.getYear());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_MONTH, usage.getMonth());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_USAGE, usage.getStockUsage());

        String baseEntityID = UUID.randomUUID().toString();
        String formSubmissionId = (usage.getProviderId() + "-" + usage.getYear() + "-" + usage.getMonth() + "-" + usage.getStockName()).replaceAll(" ", "-").toUpperCase();

        JSONObject jsonEventStr = CoreLibrary.getInstance().context().getEventClientRepository().getEventsByFormSubmissionId(formSubmissionId);
        if (jsonEventStr != null)
            baseEntityID = (new Gson().fromJson(jsonEventStr.toString(), Event.class)).getBaseEntityId();

        Event baseEvent = org.smartregister.chw.anc.util.JsonFormUtils.processJsonForm(allSharedPreferences, form.toString(), CoreConstants.TABLE_NAME.STOCK_USAGE_REPORT);
        baseEvent.setFormSubmissionId(formSubmissionId);

        CoreJsonFormUtils.tagSyncMetadata(org.smartregister.family.util.Utils.context().allSharedPreferences(), baseEvent);// tag docs
        JSONObject eventJson = new JSONObject(CoreJsonFormUtils.gson.toJson(baseEvent));
        getSyncHelper().addEvent(baseEntityID, eventJson);
    }

}
