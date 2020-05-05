package org.smartregister.chw.core.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.BaseReferralNotificationDetailsContract;
import org.smartregister.chw.core.dao.ReferralNotificationDao;
import org.smartregister.chw.core.domain.ReferralNotificationItem;
import org.smartregister.chw.core.presenter.BaseReferralNotificationDetailsPresenter;
import org.smartregister.util.Utils;
import org.smartregister.view.activity.MultiLanguageActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.List;

import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.NOTIFICATION_TYPE;
import static org.smartregister.chw.core.utils.CoreConstants.DB_CONSTANTS.REFERRAL_TASK_ID;

public abstract class BaseReferralNotificationDetailsActivity extends MultiLanguageActivity
        implements BaseReferralNotificationDetailsContract.View, View.OnClickListener {

    protected TextView referralNotificationTitle;
    protected LinearLayout referralNotificationDetails;
    protected TextView markAsDoneTextView;
    protected TextView viewProfileTextView;

    private BaseReferralNotificationDetailsContract.Presenter presenter;
    private String referralTaskId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_notification_details);
        inflateToolbar();
        setupViews();
        initPresenter();
        disableMarkAsDoneAction(ReferralNotificationDao.isMarkedAsDone(referralTaskId));
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

    protected void setupViews() {
        referralNotificationTitle = findViewById(R.id.referral_notification_title);
        referralNotificationDetails = findViewById(R.id.referral_notification_content);
        markAsDoneTextView = findViewById(R.id.mark_as_done);
        markAsDoneTextView.setOnClickListener(this);
        viewProfileTextView = findViewById(R.id.view_profile);
        viewProfileTextView.setOnClickListener(this);
    }

    @Override
    public void setReferralNotificationDetails(ReferralNotificationItem notificationItem) {
        referralNotificationTitle.setText(notificationItem.getTitle());
        addNotificationInnerContent(notificationItem.getDetails());
    }

    @Override
    public void initPresenter() {
        presenter = new BaseReferralNotificationDetailsPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            referralTaskId = getIntent().getExtras().getString(REFERRAL_TASK_ID);
            String notificationType = getIntent().getExtras().getString(NOTIFICATION_TYPE);
            presenter.getReferralDetails(referralTaskId, notificationType);
        }
    }

    @Override
    public void disableMarkAsDoneAction(boolean disable) {
        if (disable) {
            markAsDoneTextView.setEnabled(false);
            markAsDoneTextView.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.disabled_button_background));
            markAsDoneTextView.setTextColor(ContextCompat.getColor(this,
                    R.color.text_black));
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

    public BaseReferralNotificationDetailsContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_profile) {
            getPresenter().showMemberProfile();
        } else if (view.getId() == R.id.mark_as_done) {
            getPresenter().dismissReferralNotification(referralTaskId);
        } else {
            Utils.showShortToast(this, getString(R.string.perform_click_action));
        }
    }
}
