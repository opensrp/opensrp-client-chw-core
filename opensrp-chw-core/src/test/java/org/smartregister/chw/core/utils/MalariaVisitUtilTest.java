package org.smartregister.chw.core.utils;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.application.TestCoreChwApplication;
import org.smartregister.chw.core.rule.MalariaFollowUpRule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestCoreChwApplication.class)
public class MalariaVisitUtilTest {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private MalariaVisitUtil malariaVisitUtil = new MalariaVisitUtil();
    private Date malariaTestDate = new DateTime().minusDays(14).toDate();
    private Date followUpDate = new DateTime().minusDays(7).toDate();
    private MalariaFollowUpRule malariaFollowUpRule;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMalariaStatus() {
        CoreChwApplication.getInstance().getRulesEngineHelper().getMalariaRule(malariaFollowUpRule, CoreConstants.RULE_FILE.MALARIA_FOLLOW_UP_VISIT);
        //   CoreChwApplication.getInstance().getRulesEngineHelper().getMalariaRule(malariaFollowUpRule, CoreConstants.RULE_FILE.MALARIA_FOLLOW_UP_VISIT);
        malariaFollowUpRule = MalariaVisitUtil.getMalariaStatus(malariaTestDate, followUpDate);
        Assert.assertNotNull(malariaFollowUpRule);
    }
}