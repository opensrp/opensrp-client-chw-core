package org.smartregister.chw.core.utils;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.Context;
import org.smartregister.chw.referral.domain.MemberObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 27)
public class UtilsTest {

    private Map<String, String> details;
    private Map<String, String> columnMap;
    private CommonPersonObjectClient client;

    @Before
    public void setUp() {
        details = new HashMap<>();
        columnMap = new HashMap<>();
    }

    @Test
    public void canConvertReferralToANCMemberObject() {
        client = new CommonPersonObjectClient("case1", details, "test user");
        client.setColumnmaps(columnMap);
        MemberObject referralMember = new MemberObject(client);
        referralMember.setChwReferralHf("facility 2");
        Assert.assertTrue(Utils.referralToAncMember(referralMember) instanceof org.smartregister.chw.anc.domain.MemberObject);
    }

    @Test
    public void trueReturnedIfWomanIsOfReproductiveAge() {
        columnMap.put("dob", "1995-12-12");
        columnMap.put("gender", "Female");
        client = new CommonPersonObjectClient("case1", details, "test user 2");
        client.setColumnmaps(columnMap);
        int toAge = 50;
        int fromAge = 18;
        Assert.assertTrue(Utils.isWomanOfReproductiveAge(client, fromAge, toAge));
    }

    @Test
    public void falseReturnedIfWomanIsNotOfReproductiveAge() {
        String dateFormat = "yyyy-MM-dd";
        String dateOfBirth = new SimpleDateFormat(dateFormat).format(new Date());
        columnMap.put("dob", dateOfBirth);
        columnMap.put("gender", "Female");
        client = new CommonPersonObjectClient("case1", details, "test user 2");
        client.setColumnmaps(columnMap);
        int toAge = 50;
        int fromAge = 18;
        Assert.assertFalse(Utils.isWomanOfReproductiveAge(client, fromAge, toAge));
    }

    @Test
    public void formatReferralDurationReturnsCorrectString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date oneDayBefore = cal.getTime();

        DateTime referralTime = new DateTime(oneDayBefore);
        Assert.assertEquals("Yesterday", Utils.formatReferralDuration(referralTime, RuntimeEnvironment.application));
    }

    @After
    public void tearDown() {
        columnMap = null;
        details = null;
        client = null;
    }
}
