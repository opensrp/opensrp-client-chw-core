package org.smartregister.chw.core.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.NavigationAdapter;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.util.Utils;
import org.smartregister.view.activity.BaseRegisterActivity;

public class NavigationListener implements View.OnClickListener {

    private Activity activity;
    private NavigationAdapter navigationAdapter;

    public NavigationListener(Activity activity, NavigationAdapter adapter) {
        this.activity = activity;
        this.navigationAdapter = adapter;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof String) {
            String tag = (String) v.getTag();
            switch (tag) {
                case CoreConstants.DrawerMenu.CHILD_CLIENTS:
                case CoreConstants.DrawerMenu.ALL_CHILDREN:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.CHILD_REGISTER_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.ALL_FAMILIES:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.FAMILY_REGISTER_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.ANC:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.ANC_REGISTER_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.LD:
                    Toast.makeText(activity.getApplicationContext(), CoreConstants.DrawerMenu.LD, Toast.LENGTH_SHORT).show();
                    break;
                case CoreConstants.DrawerMenu.PNC:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.PNC_REGISTER_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.FAMILY_PLANNING:
                    Class fPlanning = getActivity(CoreConstants.REGISTERED_ACTIVITIES.FP_REGISTER_ACTIVITY);
                    if (fPlanning == null) {
                        Toast.makeText(activity.getApplicationContext(), CoreConstants.DrawerMenu.FAMILY_PLANNING, Toast.LENGTH_SHORT).show();
                    } else {
                        startRegisterActivity(fPlanning);
                    }
                    break;

                case CoreConstants.DrawerMenu.MALARIA:
                    Class malaria = getActivity(CoreConstants.REGISTERED_ACTIVITIES.MALARIA_REGISTER_ACTIVITY);
                    if (malaria == null) {
                        Toast.makeText(activity.getApplicationContext(), CoreConstants.DrawerMenu.MALARIA, Toast.LENGTH_SHORT).show();
                    } else {
                        startRegisterActivity(malaria);
                    }
                    break;
                case CoreConstants.DrawerMenu.REFERRALS:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.REFERRALS_REGISTER_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.ALL_CLIENTS:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.ALL_CLIENTS_REGISTERED_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.UPDATES:
                    startRegisterActivity(getActivity(CoreConstants.REGISTERED_ACTIVITIES.UPDATES_REGISTER_ACTIVITY));
                    break;
                case CoreConstants.DrawerMenu.REPORTS:
                    activity.startActivity(new Intent(activity, getActivity(CoreConstants.REGISTERED_ACTIVITIES.REPORTS_ACTIVITY)));
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    activity.finish();
                    break;
                case CoreConstants.DrawerMenu.ADD_NEW_FAMILY:
                    Class<?> newFamilyRegisterClass = getActivity(CoreConstants.REGISTERED_ACTIVITIES.ADD_NEW_FAMILY);
                    if (newFamilyRegisterClass.isInstance(activity)) {
                        BaseRegisterActivity baseRegisterActivity = (BaseRegisterActivity) activity;
                        baseRegisterActivity.startRegistration();
                    } else {
                        Intent intent = new Intent(activity, newFamilyRegisterClass);
                        intent.putExtra(CoreConstants.ACTIVITY_PAYLOAD.ACTION, CoreConstants.ACTION.START_REGISTRATION);
                        activity.startActivity(intent);
                    }
                    break;
                default:
                    Utils.showShortToast(activity.getApplicationContext(), "Unspecified navigation action");
                    break;
            }
            navigationAdapter.setSelectedView(tag);
        }
    }

    public void startRegisterActivity(Class registerClass) {
        if (registerClass != null) {
            Intent intent = new Intent(activity, registerClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            activity.finish();
        }
    }

    private Class getActivity(String key) {
        return navigationAdapter.getRegisteredActivities().get(key);
    }
}
