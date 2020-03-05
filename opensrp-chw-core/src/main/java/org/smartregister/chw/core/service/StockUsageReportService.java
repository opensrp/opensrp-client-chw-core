package org.smartregister.chw.core.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.dao.StockUsageReportDao;
import org.smartregister.chw.core.domain.StockUsage;
import org.smartregister.chw.core.repository.StockUsageReportRepository;

import java.util.List;

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

            StockUsageReportRepository repo = CoreChwApplication.getInstance().getStockUsageRepository();

            // delete all objects
            repo.deleteAll();

            // add new results
            List<StockUsage> usages = StockUsageReportDao.getStockUsage();

            for (StockUsage usage : usages) {
                repo.addStockUsage(usage);
            }
        }
    }
}
