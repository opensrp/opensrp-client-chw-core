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
                List<StockUsage> usages = StockUsageReportDao.getStockUsage();

                for (StockUsage usage : usages) {
                    if (StringUtils.isBlank(usage.getStockName()) || StringUtils.isBlank(usage.getYear()) || StringUtils.isBlank(usage.getMonth()) || StringUtils.isBlank(usage.getStockUsage()))
                        return;

                    String formSubmissionId = getFormSubmissionID(usage);
                    usage.setId(formSubmissionId);
                    repo.addOrUpdateStockUsage(usage);
                    addEvent(form, jsonArray, usage, allSharedPreferences, formSubmissionId);
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

    private String getFormSubmissionID(StockUsage usage) {
        if (usage != null)
            return (usage.getProviderId() + "-" + usage.getYear() + "-" + usage.getMonth() + "-" + usage.getStockName()).replaceAll(" ", "-").toUpperCase();
        return null;
    }

    private void addEvent(JSONObject form, JSONArray jsonArray, StockUsage usage, AllSharedPreferences allSharedPreferences, String formSubmissionId) throws JSONException {
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_NAME, usage.getStockName());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_YEAR, usage.getYear());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_MONTH, usage.getMonth());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE, usage.getStockUsage());
        FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_PROVIDER, usage.getProviderId());

        String baseEntityID = UUID.randomUUID().toString();

        JSONObject jsonEventStr = CoreLibrary.getInstance().context().getEventClientRepository().getEventsByFormSubmissionId(formSubmissionId);
        if (jsonEventStr != null)
            baseEntityID = (new Gson().fromJson(jsonEventStr.toString(), Event.class)).getBaseEntityId();

        Event baseEvent = JsonFormUtils.processJsonForm(allSharedPreferences, form.toString(), CoreConstants.TABLE_NAME.STOCK_USAGE_REPORT);
        baseEvent.setFormSubmissionId(formSubmissionId);
        baseEvent.setBaseEntityId(baseEntityID);

        CoreJsonFormUtils.tagSyncMetadata(org.smartregister.family.util.Utils.context().allSharedPreferences(), baseEvent);
        JSONObject eventJson = new JSONObject(CoreJsonFormUtils.gson.toJson(baseEvent));
        getSyncHelper().addEvent(baseEntityID, eventJson);
    }

}
