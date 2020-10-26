package org.smartregister.chw.core.activity;

import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.shadows.ShadowReferralTaskViewActivity;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.Task;

import java.util.HashMap;
import java.util.Map;

public class BaseReferralTaskViewActivityTest extends BaseUnitTest {

    private BaseReferralTaskViewActivity referralTaskViewActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        Task task = new Task();
        task.setCode("192919code");
        task.setFocus("ANC Danger Signs");
        task.setLocation("some-location");
        task.setGroupIdentifier("some-group-id");
        intent.putExtra(CoreConstants.INTENT_KEY.USERS_TASKS, task);
        referralTaskViewActivity = Robolectric.buildActivity(ShadowReferralTaskViewActivity.class).newIntent(intent).create().visible().get();
        Map<String, String> details = new HashMap<>();
        CommonPersonObjectClient commonPersonObject = new CommonPersonObjectClient("case-id", details, "StoneField Mack");
        BaseReferralTaskViewActivity.setPersonObjectClient(commonPersonObject);
    }

    @Test
    public void getPersonObjectClient() {
        Assert.assertNotNull(referralTaskViewActivity.getPersonObjectClient());
    }

    public void tearDown() {
        try {
            referralTaskViewActivity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}