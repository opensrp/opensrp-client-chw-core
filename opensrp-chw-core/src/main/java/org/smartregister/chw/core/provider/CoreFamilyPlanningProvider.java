package org.smartregister.chw.core.provider;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import org.apache.commons.lang3.StringUtils;
import org.jeasy.rules.api.Rules;
import org.smartregister.chw.anc.AncLibrary;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.util.DBConstants;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.rule.FPAlertRule;
import org.smartregister.chw.core.rule.PncVisitAlertRule;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.HomeVisitUtil;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.util.Utils;
import org.smartregister.view.contract.SmartRegisterClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import provider.PncRegisterProvider;
import timber.log.Timber;

public class CoreFamilyPlanningProvider extends PncRegisterProvider {

    private Context context;
    private View.OnClickListener onClickListener;

    public CoreFamilyPlanningProvider(Context context, CommonRepository commonRepository, Set visibleColumns, View.OnClickListener onClickListener, View.OnClickListener paginationClickListener) {
        super(context, commonRepository, visibleColumns, onClickListener, paginationClickListener);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient client, RegisterViewHolder viewHolder) {
        super.getView(cursor, client, viewHolder);

        viewHolder.dueButton.setVisibility(View.GONE);
        CommonPersonObjectClient pc = (CommonPersonObjectClient) client;
        viewHolder.dueButton.setOnClickListener(null);
        Utils.startAsyncTask(new UpdateAsyncTask(context, viewHolder, pc), null);
    }

    private void updateDueColumn(Context context, RegisterViewHolder viewHolder, FPAlertRule fpAlertRule) {
        viewHolder.dueButton.setVisibility(View.VISIBLE);
        if (fpAlertRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.DUE)) {
            setVisitButtonDueStatus(context, fpAlertRule.getVisitId(), viewHolder.dueButton);
        } else if (fpAlertRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.OVERDUE)) {
            setVisitButtonOverdueStatus(context, fpAlertRule.getVisitId(), viewHolder.dueButton);
        } else if (fpAlertRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.VISIT_DONE)) {
            setVisitDone(context, viewHolder.dueButton);
        }
    }

    private void setVisitButtonDueStatus(Context context, String visitDue, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.alert_in_progress_blue));
        dueButton.setText(context.getString(R.string.pnc_visit_day_due, visitDue));
        dueButton.setBackgroundResource(R.drawable.blue_btn_selector);
        dueButton.setOnClickListener(onClickListener);
    }

    private void setVisitButtonOverdueStatus(Context context, String visitDue, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.white));
        dueButton.setText(context.getString(R.string.pnc_visit_day_overdue, visitDue));
        dueButton.setBackgroundResource(R.drawable.overdue_red_btn_selector);
        dueButton.setOnClickListener(onClickListener);
    }

    private void setVisitDone(Context context, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.alert_complete_green));
        dueButton.setText(context.getString(R.string.visit_done));
        dueButton.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        dueButton.setOnClickListener(null);
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        private final RegisterViewHolder viewHolder;
        private final CommonPersonObjectClient pc;
        private final Context context;
        private FPAlertRule fpAlertRule;
        private List<Rules> fpRules = new ArrayList<>();

        private UpdateAsyncTask(Context context, RegisterViewHolder viewHolder, CommonPersonObjectClient pc) {
            this.context = context;
            this.viewHolder = viewHolder;
            this.pc = pc;

            fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_COC_POP_REFILL));
            fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_CONDOM_REFILL));
            fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_INJECTION_DUE));
            fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_FEMALE_STERILIZATION));
            fpRules.add(CoreChwApplication.getInstance().getRulesEngineHelper().rules(CoreConstants.RULE_FILE.FP_IUCD));
        }

        @Override
        protected Void doInBackground(Void... params) {
            //map = getChildDetails(pc.getCaseId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String baseEntityID = Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.BASE_ENTITY_ID, false);
            String dayPnc = Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.DELIVERY_DATE, true);
            Date deliveryDate = null;
            Date lastVisitDate = null;
            try {
                deliveryDate = sdf.parse(dayPnc);
            } catch (ParseException e) {
                Timber.e(e);
            }

            Visit lastVisit = AncLibrary.getInstance().visitRepository().getLatestVisit(baseEntityID, org.smartregister.chw.anc.util.Constants.EVENT_TYPE.PNC_HOME_VISIT);
            if (lastVisit != null) {
                lastVisitDate = lastVisit.getDate();
            }
            for(Rules rule:fpRules){
                fpAlertRule = HomeVisitUtil.getFpVisitStatus(rule,lastVisitDate,deliveryDate);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            // Update status column
            if (fpAlertRule == null || StringUtils.isBlank(fpAlertRule.getVisitId())) {
                return;
            }

            if (fpAlertRule != null
                    && StringUtils.isNotBlank(fpAlertRule.getVisitId())
                    && !fpAlertRule.getButtonStatus().equalsIgnoreCase(CoreConstants.VISIT_STATE.EXPIRED)
            ) {
                updateDueColumn(context, viewHolder, fpAlertRule);
            }
        }
    }
}
