package org.smartregister.chw.core.provider;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.rule.PmtctFollowUpRule;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.FpUtil;
import org.smartregister.chw.core.utils.HomeVisitUtil;
import org.smartregister.chw.pmtct.dao.PmtctDao;
import org.smartregister.provider.PmtctRegisterProvider;
import org.smartregister.util.Utils;
import org.smartregister.view.contract.SmartRegisterClient;

import java.util.Date;
import java.util.Set;

/**
 * Created by Billy on 22/10/2021.
 */
public class CorePmtctRegisterProvider extends PmtctRegisterProvider {

    private Context context;

    public CorePmtctRegisterProvider(Context context, View.OnClickListener paginationClickListener, View.OnClickListener onClickListener, Set visibleColumns) {
        super(context, paginationClickListener, onClickListener, visibleColumns);
        this.context = context;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient smartRegisterClient, RegisterViewHolder registerViewHolder) {
        super.getView(cursor, smartRegisterClient, registerViewHolder);
        registerViewHolder.dueButton.setVisibility(View.GONE);
        registerViewHolder.dueButton.setOnClickListener(null);
        String baseEntityId = smartRegisterClient.entityId();
        Utils.startAsyncTask(new UpdatePmtctDueButtonStatusTask(registerViewHolder, baseEntityId), null);
    }

    protected void updateDueColumn(Context context, RegisterViewHolder viewHolder, PmtctFollowUpRule pmtctFollowUpRule) {
        if (pmtctFollowUpRule.getDueDate() != null) {
            if (pmtctFollowUpRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.NOT_DUE_YET)) {
                setVisitButtonNextDueStatus(context, FpUtil.sdf.format(pmtctFollowUpRule.getDueDate()), viewHolder.dueButton);
                viewHolder.dueButton.setVisibility(View.GONE);
            }
            if (pmtctFollowUpRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE)) {
                viewHolder.dueButton.setVisibility(View.VISIBLE);
                setVisitButtonDueStatus(context, String.valueOf(Days.daysBetween(new DateTime(pmtctFollowUpRule.getDueDate()), new DateTime()).getDays()), viewHolder.dueButton);
            } else if (pmtctFollowUpRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.OVERDUE)) {
                viewHolder.dueButton.setVisibility(View.VISIBLE);
                setVisitButtonOverdueStatus(context, String.valueOf(Days.daysBetween(new DateTime(pmtctFollowUpRule.getOverDueDate()), new DateTime()).getDays()), viewHolder.dueButton);
            } else if (pmtctFollowUpRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.VISIT_DONE)) {
                setVisitDone(context, viewHolder.dueButton);
            }
        }
    }

    protected void setVisitButtonNextDueStatus(Context context, String visitDue, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.light_grey_text));
        dueButton.setText(context.getString(R.string.hiv_visit_day_next_due, visitDue));
        dueButton.setBackgroundResource(R.drawable.colorless_btn_selector);
        dueButton.setOnClickListener(null);
    }


    protected void setVisitButtonDueStatus(Context context, String visitDue, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.alert_in_progress_blue));
        if (visitDue.equalsIgnoreCase("0")) {
            dueButton.setText(context.getString(R.string.hiv_visit_day_due_today));
        } else {
            dueButton.setText(context.getString(R.string.hiv_visit_day_due, visitDue));
        }
        dueButton.setBackgroundResource(R.drawable.blue_btn_selector);
        dueButton.setOnClickListener(onClickListener);
    }


    protected void setVisitButtonOverdueStatus(Context context, String visitDue, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.white));
        if (visitDue.equalsIgnoreCase("0")) {
            dueButton.setText(context.getString(R.string.hiv_visit_day_overdue_today));

        } else {
            dueButton.setText(context.getString(R.string.hiv_visit_day_overdue, visitDue));
        }
        dueButton.setBackgroundResource(R.drawable.overdue_red_btn_selector);
        dueButton.setOnClickListener(onClickListener);
    }

    protected void setVisitDone(Context context, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.alert_complete_green));
        dueButton.setText(context.getString(R.string.visit_done));
        dueButton.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        dueButton.setOnClickListener(null);
    }

    private class UpdatePmtctDueButtonStatusTask extends AsyncTask<Void, Void, Void> {
        private final RegisterViewHolder viewHolder;
        private final String baseEntityId;
        private PmtctFollowUpRule pmtctFollowUpRule;

        private UpdatePmtctDueButtonStatusTask(RegisterViewHolder viewHolder, String baseEntityId) {
            this.viewHolder = viewHolder;
            this.baseEntityId = baseEntityId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Date pmtctRegisterDate = PmtctDao.getPmtctRegisterDate(baseEntityId);
            Date followUpDate = PmtctDao.getPmtctFollowUpVisitDate(baseEntityId);
            pmtctFollowUpRule = HomeVisitUtil.getPmtctVisitStatus(pmtctRegisterDate, followUpDate, baseEntityId);
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            if (pmtctFollowUpRule != null && StringUtils.isNotBlank(pmtctFollowUpRule.getButtonStatus()) && !CoreConstants.VISIT_STATE.EXPIRED.equalsIgnoreCase(pmtctFollowUpRule.getButtonStatus())) {
                updateDueColumn(context, viewHolder, pmtctFollowUpRule);
            }
        }
    }
}
