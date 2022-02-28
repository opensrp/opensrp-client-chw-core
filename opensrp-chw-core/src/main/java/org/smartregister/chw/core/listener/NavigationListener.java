package org.smartregister.chw.core.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.NavigationAdapter;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.util.Utils;

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
                case CoreConstants.DrawerMenu.BIRTH_NOTIFICATION:
                    Class bNotification = getActivity(CoreConstants.REGISTERED_ACTIVITIES.BNOTIFICATION_REGISTER_ACTIVITY);
                    if (bNotification == null) {
                        Toast.makeText(activity.getApplicationContext(), CoreConstants.DrawerMenu.BIRTH_NOTIFICATION, Toast.LENGTH_SHORT).show();
                    } else {
                        startRegisterActivity(bNotification);
                    }
                    break;
                case CoreConstants.DrawerMenu.DEATH_NOTIFICATION:
                    Class deathNotification = getActivity(CoreConstants.REGISTERED_ACTIVITIES.DEATH_NOTIFICATION_REGISTER_ACTIVITY);
                    if (deathNotification != null) {
                        startRegisterActivity(deathNotification);
                    }
                    break;
                case CoreConstants.DrawerMenu.OUT_OF_AREA_CHILD:
                    Class outOfAreaChild = getActivity(CoreConstants.REGISTERED_ACTIVITIES.OUT_OF_AREA_REGISTER_ACTIVITY);
                    if (outOfAreaChild != null) {
                        startRegisterActivity(outOfAreaChild);
                    }
                    break;
                case CoreConstants.DrawerMenu.OUT_OF_AREA_DEATH:
                    Class outOfAreaDeath = getActivity(CoreConstants.REGISTERED_ACTIVITIES.OUT_OF_AREA_DEATH_ACTIVITY);
                    if (outOfAreaDeath != null) {
                        startRegisterActivity(outOfAreaDeath);
                    }
                    break;

                case CoreConstants.DrawerMenu.MALARIA:
                    Class malaria = getActivity(CoreConstants.REGISTERED_ACTIVITIES.MALARIA_REGISTER_ACTIVITY);
                    if (malaria != null) {
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
