package org.smartregister.chw.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.domain.NotificationRecord;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationRowViewModel> {

    private List<NotificationRecord> notificationRecords = new ArrayList<>();

    @NonNull
    @Override
    public NotificationRowViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_notification_row, parent, false);
        return new NotificationRowViewModel(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRowViewModel holder, int position) {
        NotificationRecord notificationRecord = notificationRecords.get(position);
        holder.setNotificationForText(notificationRecord.getNotificationType());
    }

    @Override
    public int getItemCount() {
        return notificationRecords.size();
    }

    static class NotificationRowViewModel extends RecyclerView.ViewHolder {

        private TextView notificationForTextView;

        public NotificationRowViewModel(@NonNull View itemView) {
            super(itemView);
            notificationForTextView = itemView.findViewById(R.id.notification_for_textview);
        }

        public void setNotificationForText(String notificationFor) {
            notificationForTextView.setText(itemView.getContext().getString(R.string.notification_for, notificationFor));
        }
    }
}
