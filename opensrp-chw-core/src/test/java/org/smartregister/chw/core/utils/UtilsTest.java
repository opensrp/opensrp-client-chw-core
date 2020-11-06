package org.smartregister.chw.core.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.referral.domain.MemberObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.tag.FormTag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, sdk = Build.VERSION_CODES.P)
public class UtilsTest {

    private Map<String, String> details;
    private Map<String, String> columnMap;
    private CommonPersonObjectClient client;
    private Context context;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        details = new HashMap<>();
        columnMap = new HashMap<>();
        context = RuntimeEnvironment.application.getApplicationContext();
        MockitoAnnotations.initMocks(this);
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
        Assert.assertTrue(Utils.isMemberOfReproductiveAge(client, fromAge, toAge));
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
        Assert.assertFalse(Utils.isMemberOfReproductiveAge(client, fromAge, toAge));
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


    @Test
    public void getFormTagShouldNotReturnNullValues() {
        FormTag formTag = Utils.getFormTag(org.smartregister.util.Utils.getAllSharedPreferences());
        Assert.assertNotNull(formTag);
    }

    @Test
    public void assertGetDurationTests() {
        Utils.getDuration("2020-10-09T18:17:07.830+05:00", "2020-05-15T18:17:07.830+05:00");
        Locale locale = RuntimeEnvironment.application.getApplicationContext().getResources().getConfiguration().locale;
        DateTime todayDateTime = new DateTime("2020-10-09T18:17:07.830+05:00");

        Assert.assertEquals("1d", Utils.getDuration(Long.parseLong("100000000"),
                new DateTime("2020-10-10T18:17:07.830+05:00"),
                todayDateTime,
                locale));

        Assert.assertEquals("2w 5d", Utils.getDuration(Long.parseLong("1641600000"),
                new DateTime("2020-11-12T18:17:07.830+05:00"),
                todayDateTime,
                locale));


        Assert.assertEquals("4m 3w", Utils.getDuration(Long.parseLong("12700800000"),
                new DateTime("2020-11-12T18:17:07.830+05:00"),
                todayDateTime,
                locale));

        Assert.assertEquals("4y 11m", Utils.getDuration(Long.parseLong("157334400000"),
                new DateTime("2015-10-10T05:00:00.000+05:00"),
                todayDateTime,
                locale));

        Assert.assertEquals("5y", Utils.getDuration(Long.parseLong("157334400000"),
                new DateTime("2015-10-09T05:00:00.000+05:00"),
                todayDateTime,
                locale));

        Assert.assertEquals("25y 11m", Utils.getDuration("2020-10-12T00:00:00.000+00:00",
                "1994-10-15T00:00:00.000+00:00"));
    }

    @Test
    public void getGenderLanguageSpecificReturnsCorrectString() {
        Assert.assertEquals("", Utils.getGenderLanguageSpecific(context, ""));
        Assert.assertEquals("Male", Utils.getGenderLanguageSpecific(context, "male"));
        Assert.assertEquals("Female", Utils.getGenderLanguageSpecific(context, "female"));
    }

    @Test
    public void getImmunizationHeaderLanguageSpecificReturnsCorrectString() {
        Assert.assertEquals("", Utils.getImmunizationHeaderLanguageSpecific(context, ""));
        Assert.assertEquals("at birth", Utils.getImmunizationHeaderLanguageSpecific(context, "at birth"));
        Assert.assertEquals("weeks", Utils.getImmunizationHeaderLanguageSpecific(context, "weeks"));
        Assert.assertEquals("months", Utils.getImmunizationHeaderLanguageSpecific(context, "months"));
    }

    @Test
    public void getFileNameReturnsFormIdentityIfFormExists() {
        Locale locale = CoreChwApplication.getCurrentLocale();
        AssetManager assetManager = CoreChwApplication.getInstance().getAssets();
        Assert.assertEquals("test_form", Utils.getFileName("test_form", locale, assetManager));
        Assert.assertEquals("child_enrollment", Utils.getFileName("child_enrollment", locale, assetManager));
    }

    @Test
    public void getDayOfMonthWithSuffixThrowsExceptionWhenIllegalDaySupplied() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("illegal day of month: 99");
        Utils.getDayOfMonthWithSuffix(99, context);
    }

    @Test
    public void getDayOfMonthWithSuffixReturnsCorrectSuffix() {
        Assert.assertEquals("1st", Utils.getDayOfMonthWithSuffix(1, context));
        Assert.assertEquals("2nd", Utils.getDayOfMonthWithSuffix(2, context));
        Assert.assertEquals("8th", Utils.getDayOfMonthWithSuffix(8, context));
        Assert.assertEquals("11th", Utils.getDayOfMonthWithSuffix(11, context));
        Assert.assertEquals("12th", Utils.getDayOfMonthWithSuffix(12, context));
        Assert.assertNull(Utils.getDayOfMonthWithSuffix(22, context));
    }

    @After
    public void tearDown() {
        columnMap = null;
        details = null;
        client = null;
    }

}
