package org.smartregister.chw.core.custom_views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.listener.OnClickFloatingMenu;
import org.smartregister.chw.pmtct.custom_views.BasePmtctFloatingMenu;
import org.smartregister.chw.pmtct.domain.MemberObject;
import org.smartregister.chw.pmtct.fragment.BasePmtctCallDialogFragment;

import static org.smartregister.chw.core.utils.Utils.redrawWithOption;

public abstract class CorePmtctFloatingMenu extends BasePmtctFloatingMenu {
    public FloatingActionButton fab;
    private Animation fabOpen;
    private Animation fabClose;
    private Animation rotateForward;
    private Animation rotateBack;
    private View callLayout;
    private RelativeLayout activityMain;
    private boolean isFabMenuOpen = false;
    private LinearLayout menuBar;
    private OnClickFloatingMenu onClickFloatingMenu;
    private MemberObject MEMBER_OBJECT;


    public CorePmtctFloatingMenu(Context context, MemberObject MEMBER_OBJECT) {
        super(context, MEMBER_OBJECT);
        this.MEMBER_OBJECT = MEMBER_OBJECT;
    }

    public void setFloatMenuClickListener(OnClickFloatingMenu onClickFloatingMenu) {
        this.onClickFloatingMenu = onClickFloatingMenu;
    }

    @Override
    protected void initUi() {
        inflate(getContext(), R.layout.view_pmtct_floating_menu, this);

        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotateBack = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_back);

        activityMain = findViewById(R.id.activity_main);
        menuBar = findViewById(R.id.menu_bar);

        fab = findViewById(R.id.pmtct_fab);
        fab.setOnClickListener(this);

        callLayout = findViewById(R.id.call_layout);
        callLayout.setOnClickListener(this);
        callLayout.setClickable(false);

        menuBar.setVisibility(GONE);

    }

    @Override
    public void onClick(View view) {
        onClickFloatingMenu.onClickMenu(view.getId());
    }

    public void animateFAB() {
        menuBar.setVisibility(VISIBLE);
        fab.startAnimation(rotateForward);

        if (isFabMenuOpen) {
            activityMain.setBackgroundResource(R.color.transparent);
            fab.startAnimation(rotateBack);
            fab.setImageResource(R.drawable.ic_edit_white);

            callLayout.startAnimation(fabClose);
            callLayout.setClickable(false);

            isFabMenuOpen = false;
        } else {
            activityMain.setBackgroundResource(R.color.grey_tranparent_50);
            fab.startAnimation(rotateForward);
            fab.setImageResource(R.drawable.ic_input_add);

            callLayout.startAnimation(fabOpen);
            callLayout.setClickable(true);

            isFabMenuOpen = true;
        }
    }


    public void launchCallWidget() {
        BasePmtctCallDialogFragment.launchDialog((Activity) this.getContext(), MEMBER_OBJECT);
    }

    public void redraw(boolean hasPhoneNumber) {
        redrawWithOption(this, hasPhoneNumber);
    }

    public View getCallLayout() {
        return callLayout;
    }
}