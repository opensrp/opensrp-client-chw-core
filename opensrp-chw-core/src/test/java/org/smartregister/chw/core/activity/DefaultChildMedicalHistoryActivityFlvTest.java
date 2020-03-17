package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.immunization.domain.ServiceRecord;
import org.smartregister.immunization.domain.Vaccine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rkodev
 */
public class DefaultChildMedicalHistoryActivityFlvTest extends BaseUnitTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private DefaultChildMedicalHistoryActivityFlv childMedicalHistoryActivityFlv;

    private Activity activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        childMedicalHistoryActivityFlv = Mockito.mock(DefaultChildMedicalHistoryActivityFlv.class, Mockito.CALLS_REAL_METHODS);
        activity = Robolectric.buildActivity(Activity.class).create().start().get();
    }

    @Test
    public void testBindViewsReturnsAViewGroup() {
        View view = childMedicalHistoryActivityFlv.bindViews(activity);
        Assert.assertTrue(view instanceof ViewGroup);
    }

    @Test
    public void testProcessViewData() {
        List<Visit> visits = getSampleVisits();
        Map<String, List<Vaccine>> vaccineMap = getSampleVaccineMap();
        List<ServiceRecord> serviceTypeListMap = getSampleServiceRecords();
        Context context = RuntimeEnvironment.application;

        ViewGroup rootView = (ViewGroup) childMedicalHistoryActivityFlv.bindViews(activity);

        childMedicalHistoryActivityFlv.processViewData(visits, vaccineMap, serviceTypeListMap, context);

        // verify object has kids
        Assert.assertTrue(rootView.getChildCount() > 0);
    }

    private List<Visit> getSampleVisits() {
        List<Visit> visits = new ArrayList<>();

        Visit visit = new Visit();
        visits.add(visit);

        return visits;
    }

    private Map<String, List<Vaccine>> getSampleVaccineMap() {
        return new HashMap<>();
    }

    private List<ServiceRecord> getSampleServiceRecords() {
        return new ArrayList<>();
    }
}
