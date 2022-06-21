package org.smartregister.chw.core.activity.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.smartregister.chw.anc.util.Constants.ANC_MEMBER_OBJECTS.MEMBER_PROFILE_OBJECT;

import android.content.Context;

import org.powermock.reflect.Whitebox;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.util.AppExecutors;
import org.smartregister.chw.core.activity.CoreAncMemberProfileActivity;
import org.smartregister.chw.core.interactor.CoreAncMemberProfileInteractor;
import org.smartregister.chw.core.presenter.CoreAncMemberProfilePresenter;
import org.smartregister.domain.Task;

import java.util.Set;

import timber.log.Timber;

public class CoreAncMemberProfileActivityImpl extends CoreAncMemberProfileActivity {
    @Override
    public void openUpcomingService() {
        Timber.v("openUpcomingService");
    }

    @Override
    public void openFamilyDueServices() {
        Timber.v("openFamilyDueServices");
    }

    @Override
    public void setClientTasks(Set<Task> taskList) {
        Timber.v("setClientTasks");
    }

    @Override
    public void setupViews() {
        if (this.memberObject == null){
            this.memberObject = (MemberObject) getIntent().getSerializableExtra(MEMBER_PROFILE_OBJECT);
        }
        super.setupViews();
    }

    @Override
    protected void registerPresenter() {
        CoreAncMemberProfileInteractor interactor = spy(new CoreAncMemberProfileInteractor(mock(Context.class)));
        Whitebox.setInternalState(interactor, "appExecutors", new AppExecutors());
        doReturn(memberObject).when(interactor).getMemberClient(any());
        presenter = new CoreAncMemberProfilePresenter(this, interactor,this.memberObject);
    }

    @Override
    protected void fetchProfileData() {
//        super.fetchProfileData();
    }
}
