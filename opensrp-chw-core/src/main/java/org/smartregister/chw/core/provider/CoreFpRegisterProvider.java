package org.smartregister.chw.core.provider;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import org.smartregister.chw.fp.provider.BaseFpRegisterProvider;
import org.smartregister.view.contract.SmartRegisterClient;

import java.util.Set;

public class CoreFpRegisterProvider extends BaseFpRegisterProvider {

    private Context context;

    public CoreFpRegisterProvider(Context context, View.OnClickListener paginationClickListener,
                                  View.OnClickListener onClickListener, Set visibleColumns) {
        super(context, paginationClickListener, onClickListener, visibleColumns);
        this.context = context;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient client, RegisterViewHolder viewHolder) {
        super.getView(cursor, client, viewHolder);

        viewHolder.dueButton.setVisibility(View.GONE);
        viewHolder.dueButton.setOnClickListener(null);
    }


}
