package org.smartregister.chw.hf.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import org.smartregister.chw.core.activity.CoreFamilyProfileActivity;
import org.smartregister.chw.core.activity.CoreMalariaProfileActivity;
import org.smartregister.chw.core.presenter.CoreFamilyOtherMemberActivityPresenter;
import org.smartregister.chw.hf.R;
import org.smartregister.chw.hf.presenter.FamilyOtherMemberActivityPresenter;
import org.smartregister.chw.malaria.dao.MalariaDao;
import org.smartregister.chw.malaria.domain.MemberObject;
import org.smartregister.chw.malaria.util.Constants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.family.model.BaseFamilyOtherMemberProfileActivityModel;

import static org.smartregister.chw.malaria.util.Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID;

public class MalariaProfileActivity extends CoreMalariaProfileActivity {

    private static final String CLIENT = "client";



    @Override
    public void setProfileImage(String s, String s1) {
        //Overridden from abstract class not yet implemented
    }

    @Override
    public void setProfileDetailThree(String s) {
        //Implemented from abstract class but not required right now for HF
    }

    @Override
    public void toggleFamilyHead(boolean b) {
        //Implemented from abstract class but not required right now for HF
    }

    @Override
    public void togglePrimaryCaregiver(boolean b) {
        //Implemented from abstract class but not required right now for HF
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_remove_member);
        if (item != null) {
            menu.removeItem(item.getItemId());
        }
        return true;
    }

    @Override
    protected Class<? extends CoreFamilyProfileActivity> getFamilyProfileActivityClass() {
        return FamilyProfileActivity.class;
    }

    @Override
    protected void removeMember() {
        //Implemented from abstract class but not required right now for HF
    }

    @NonNull
    @Override
    public CoreFamilyOtherMemberActivityPresenter presenter() {
        String baseEntityId = getIntent().getStringExtra(BASE_ENTITY_ID);
        memberObject = MalariaDao.getMember(baseEntityId);
        return new FamilyOtherMemberActivityPresenter(this, new BaseFamilyOtherMemberProfileActivityModel(),
                null, memberObject.getRelationalId(), memberObject.getBaseEntityId(),
                memberObject.getFamilyHead(), memberObject.getPrimaryCareGiver(), memberObject.getAddress(),
                memberObject.getLastName());
    }

    @Override
    public void refreshList() {
        //Overridden from abstract class not yet implemented
    }

    @Override
    public void updateHasPhone(boolean hasPhone) {
        //Overridden from abstract class not yet implemented
    }

    @Override
    public void setFamilyServiceStatus(String status) {
        //Implemented from abstract class but not required right now for HF
    }

    @Override
    public void verifyHasPhone() {
        //Implemented from abstract class but not required right now for HF
    }

    @Override
    public void notifyHasPhone(boolean hasPhone) {
        //Overridden from abstract class not yet implemented
    }
}
