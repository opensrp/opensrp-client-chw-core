package org.smartregister.chw.core.activity.impl;

import org.smartregister.chw.core.activity.CoreAboveFiveChildProfileActivity;
import org.smartregister.chw.core.activity.CoreAncMemberProfileActivity;
import org.smartregister.domain.Task;

import java.util.Set;

import timber.log.Timber;

public class CoreAboveFiveChildProfileActivityImpl extends CoreAboveFiveChildProfileActivity {
    @Override
    protected void onCreation() {
        super.onCreation();
    }

    @Override
    public void setParentName(String parentName) {
        super.setParentName(parentName);
    }

    @Override
    public void setLastVisitRowView(String days) {
        super.setLastVisitRowView(days);
    }

    @Override
    public void setServiceNameDue(String serviceName, String dueDate) {
        super.setServiceNameDue(serviceName, dueDate);
    }

    @Override
    public void setServiceNameOverDue(String serviceName, String dueDate) {
        super.setServiceNameOverDue(serviceName, dueDate);
    }

    @Override
    public void setServiceNameUpcoming(String serviceName, String dueDate) {
        super.setServiceNameUpcoming(serviceName, dueDate);
    }
}
