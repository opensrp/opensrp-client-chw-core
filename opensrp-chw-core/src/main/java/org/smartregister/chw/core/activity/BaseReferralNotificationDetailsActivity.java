package org.smartregister.chw.core.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.domain.ReferralNotificationItem;
import org.smartregister.chw.core.presenter.BaseReferralNotificationDetailsPresenter;
import org.smartregister.view.activity.MultiLanguageActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.List;

import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.REFERRAL_TASK_ID;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.NOTIFICATION_TYPE;

public abstract class BaseReferralNotificationDetailsActivity extends MultiLanguageActivity
        implements BaseReferralNotificationDetailsContract.View {

    private TextView referralNotificationTitle;
    private LinearLayout referralNotificationDetails;
    private BaseReferralNotificationDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_notification_details);
        inflateToolbar();
        setupViews();
        initPresenter();
    }

    private void inflateToolbar() {
        Toolbar toolbar = findViewById(R.id.back_to_updates_toolbar);
        CustomFontTextView toolBarTextView = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            upArrow.setColorFilter(getResources().getColor(R.color.text_blue), PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setElevation(0);
        }

        toolbar.setNavigationOnClickListener(v -> finish());
        toolBarTextView.setOnClickListener(v -> finish());
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setOutlineProvider(null);
        }
    }

    private void setupViews() {
        referralNotificationTitle = findViewById(R.id.referral_notification_title);
        referralNotificationDetails = findViewById(R.id.referral_notification_content);
    }

    @Override
    public void onReferralDetailsFetched(ReferralNotificationItem notificationItem) {
        referralNotificationTitle.setText(notificationItem.getTitle());
        addNotificationInnerContent(notificationItem.getDetails());
    }

    @Override
    public void initPresenter() {
        presenter = new BaseReferralNotificationDetailsPresenter(this);
        if (getIntent().getExtras() != null) {
            String referralTaskId = getIntent().getExtras().getString(REFERRAL_TASK_ID);
            String notificationType = getIntent().getExtras().getString(NOTIFICATION_TYPE);
            presenter.getReferralDetails(referralTaskId, notificationType);
        }
    }

    private void addNotificationInnerContent(List<String> details) {
        referralNotificationDetails.removeAllViews();
        for (String entry : details) {
            TextView textView = new TextView(this);
            textView.setTextSize(18f);
            textView.setText(entry);
            textView.setPadding(16, 32, 16, 32);
            textView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            textView.setSingleLine(false);
            textView.setTextColor(ContextCompat.getColor(this, R.color.text_black));
            referralNotificationDetails.addView(textView,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
}
