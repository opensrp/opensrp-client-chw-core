package org.smartregister.chw.core.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.anc.util.JsonFormUtils;
import org.smartregister.chw.anc.util.NCUtils;
import org.smartregister.chw.core.dao.StockUsageReportDao;
import org.smartregister.chw.core.domain.StockUsage;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.FormUtils;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.BaseRepository;

import java.util.Date;
import java.util.List;

import static org.smartregister.chw.anc.util.NCUtils.getSyncHelper;
import static org.smartregister.util.Utils.getAllSharedPreferences;

public class StockUsageReportService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public StockUsageReportService() {
        super("StockUsageReportService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (!StockUsageReportDao.lastInteractedWithinDay()) {
            JSONObject form = FormUtils.getFormUtils().getFormJson(CoreConstants.JSON_FORM.getStockUsageForm());
            try {
                JSONObject stepOne = form.getJSONObject(JsonFormUtils.STEP1);
                JSONArray jsonArray = stepOne.getJSONArray(JsonFormUtils.FIELDS);
                AllSharedPreferences allSharedPreferences = getAllSharedPreferences();

                List<StockUsage> usages = StockUsageReportDao.getStockUsage();

                for (StockUsage usage : usages) {
                    if (StringUtils.isBlank(usage.getStockName()) || StringUtils.isBlank(usage.getYear()) && StringUtils.isBlank(usage.getMonth()) && StringUtils.isBlank(usage.getStockUsage())) {
                        return;
                    }
                    FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_NAME, usage.getStockName());
                    FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_YEAR, usage.getYear());
                    FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_MONTH, usage.getMonth());
                    FormUtils.updateFormField(jsonArray, CoreConstants.JsonAssets.STOCK_USAGE_REPORT.STOCK_USAGE, usage.getStockUsage());
                    Event baseEvent = org.smartregister.chw.anc.util.JsonFormUtils.processJsonForm(allSharedPreferences, form.toString(), CoreConstants.TABLE_NAME.STOCK_USAGE_REPORT);
                    String baseEntityID = (usage.getProviderId() + "-" + usage.getYear() + "-" + usage.getMonth() + "-" + usage.getStockName()).replaceAll(" ", "-").toUpperCase();
                    CoreJsonFormUtils.tagSyncMetadata(org.smartregister.family.util.Utils.context().allSharedPreferences(), baseEvent);// tag docs
                    JSONObject eventJson = new JSONObject(CoreJsonFormUtils.gson.toJson(baseEvent));
                    getSyncHelper().addEvent(baseEntityID, eventJson, BaseRepository.TYPE_Unsynced);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
