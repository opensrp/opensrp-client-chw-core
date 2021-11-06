package org.smartregister.chw.core.provider;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;

import org.smartregister.chw.core.R;
import org.smartregister.chw.hiv.provider.BaseHivRegisterProvider;
import org.smartregister.chw.hiv.util.DBConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.util.Utils;
import org.smartregister.view.contract.SmartRegisterClient;

import java.util.Set;

public class CoreHivIndexContactsProvider extends BaseHivRegisterProvider {

    private Context context;
    private View.OnClickListener onClickListener;

    public CoreHivIndexContactsProvider(Context context, Set visibleColumns, View.OnClickListener onClickListener, View.OnClickListener paginationClickListener) {
        super(context, paginationClickListener, onClickListener, visibleColumns);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient client, RegisterViewHolder viewHolder) {
        super.getView(cursor, client, viewHolder);

        viewHolder.getDueButton().setVisibility(View.GONE);
        CommonPersonObjectClient pc = (CommonPersonObjectClient) client;
        viewHolder.getDueButton().setOnClickListener(null);
        updateDueColumn(context, viewHolder, pc);
    }

    private void updateDueColumn(Context context, RegisterViewHolder viewHolder, CommonPersonObjectClient commonPersonObjectClient) {

        boolean clientFollowedUpByChw = Utils.getValue(commonPersonObjectClient.getColumnmaps(), DBConstants.Key.FOLLOWED_UP_BY_CHW, false).equalsIgnoreCase("true");
        viewHolder.getDueButton().setVisibility(View.VISIBLE);
        if (clientFollowedUpByChw) {
            setVisitDone(context, viewHolder.getDueButton());
        } else {
            setVisitButtonDueStatus(context, "0", viewHolder.getDueButton());
        }

    }

    private void setVisitButtonDueStatus(Context context, String visitDue, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.alert_in_progress_blue));
        if (visitDue.equalsIgnoreCase("0")) {
            dueButton.setText(context.getString(R.string.hiv_visit_day_due_today));
        } else {
            dueButton.setText(context.getString(R.string.hiv_visit_day_due, visitDue));
        }
        dueButton.setBackgroundResource(R.drawable.blue_btn_selector);
        dueButton.setOnClickListener(onClickListener);
    }

    private void setVisitDone(Context context, Button dueButton) {
        dueButton.setTextColor(context.getResources().getColor(R.color.alert_complete_green));
        dueButton.setText(context.getString(R.string.visit_done));
        dueButton.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        dueButton.setOnClickListener(null);
    }

}