package org.smartregister.chw.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.CoreCommunityRespondersRegisterActivity;
import org.smartregister.chw.core.dao.EventDao;
import org.smartregister.chw.core.model.CommunityResponderModel;
import org.smartregister.chw.core.utils.CoreConstants;

import java.util.ArrayList;

import timber.log.Timber;

public class CommunityResponderCustomAdapter extends ArrayAdapter<CommunityResponderModel> implements View.OnClickListener {

    Context mContext;
    private int lastPosition = -1;
    private CoreCommunityRespondersRegisterActivity activity;

    private static class ViewHolder {
        TextView txtName;
        TextView txtPhoneNumber;
        ImageView editDelete;
    }

    public CommunityResponderCustomAdapter(ArrayList<CommunityResponderModel> data, Context context, CoreCommunityRespondersRegisterActivity activity) {
        super(context, R.layout.row_item, data);
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        CommunityResponderModel communityResponderModel = (CommunityResponderModel) object;

        if (v.getId() == R.id.edit_delete) {

            // start the object
            try {
                String json = EventDao.getLatestJson(communityResponderModel.getId(), CoreConstants.EventType.COMMUNITY_RESPONDER_REGISTRATION);
                activity.startJsonActivity(json);
            }catch (Exception e){
                Timber.e(e);
            }

            Snackbar.make(v, "Base ID " + communityResponderModel.getId(), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommunityResponderModel communityResponderModel = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.responder_name);
            viewHolder.txtPhoneNumber = convertView.findViewById(R.id.responder_phone);
            viewHolder.editDelete = convertView.findViewById(R.id.edit_delete);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(communityResponderModel.getResponderName());
        viewHolder.txtPhoneNumber.setText(communityResponderModel.getResponderPhoneNumber());
        viewHolder.editDelete.setOnClickListener(this);
        viewHolder.editDelete.setTag(position);
        return convertView;
    }


}
