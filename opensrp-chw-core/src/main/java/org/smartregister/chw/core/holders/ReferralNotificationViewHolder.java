package org.smartregister.chw.core.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.smartregister.chw.core.R;

public class ReferralNotificationViewHolder extends RecyclerView.ViewHolder {

    private TextView personNameAndAge;
    private TextView referralTypeTextView;
    private TextView notificationEventDateTextView;

    public ReferralNotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        personNameAndAge = itemView.findViewById(R.id.persons_name_and_age);
        referralTypeTextView = itemView.findViewById(R.id.referral_type);
        notificationEventDateTextView = itemView.findViewById(R.id.notification_date);
    }

    public void setNameAndAge(String fullNameAndAge) {
        this.personNameAndAge.setText(fullNameAndAge);
    }

    public void setReferralTypeTextView(String referralType) {
        String formattedReferralType = referralTypeTextView.getContext()
                .getString(R.string.facility_visit, referralType);
        this.referralTypeTextView.setText(formattedReferralType);
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationEventDateTextView.setText(notificationDate);
    }
}
