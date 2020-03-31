package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.chw.anc.domain.GroupedVisit;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.CustomFontTextViewShadowHelper;
import org.smartregister.chw.core.shadows.FamilyLibraryShadowUtil;
import org.smartregister.chw.core.shadows.PncLibraryShadowHelper;
import org.smartregister.chw.core.shadows.PncMedicalHistoryViewBuilderShadow;
import org.smartregister.chw.pnc.PncLibrary;
import org.smartregister.chw.pnc.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rkodev
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, shadows = {ContextShadow.class, FamilyLibraryShadowUtil.class, CustomFontTextViewShadowHelper.class, PncMedicalHistoryViewBuilderShadow.class, PncLibraryShadowHelper.class})
public class DefaultPncMedicalHistoryActivityFlvTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private DefaultPncMedicalHistoryActivityFlv pncMedicalHistoryActivityFlv;

    private Activity activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pncMedicalHistoryActivityFlv = Mockito.mock(DefaultPncMedicalHistoryActivityFlv.class, Mockito.CALLS_REAL_METHODS);
        activity = Robolectric.buildActivity(Activity.class).create().start().get();
    }

    @Test
    public void testBindViews() {
        View view = pncMedicalHistoryActivityFlv.bindViews(activity);
        Assert.assertTrue(view instanceof ViewGroup);
    }

    @Test
    public void testProcessViewData() {
        List<GroupedVisit> groupedVisits = getSampleGroups();

        Context context = RuntimeEnvironment.application;
        MemberObject memberObject = new MemberObject();
        memberObject.setBaseEntityId("23456");
        memberObject.setFirstName("fname");
        memberObject.setMiddleName("mname");
        memberObject.setLastName("mname");

        LinearLayout parentView = Mockito.mock(LinearLayout.class);
        LayoutInflater inflater = LayoutInflater.from(context);


        PncLibrary instance = Mockito.mock(PncLibrary.class);
        ProfileRepository repository = Mockito.mock(ProfileRepository.class);
        Mockito.doReturn(repository).when(instance).profileRepository();
        Mockito.doReturn(new Date().getTime()).when(repository).getLastVisit(Mockito.anyString());

        PncLibraryShadowHelper.setInstance(instance);


        ReflectionHelpers.setField(pncMedicalHistoryActivityFlv, "parentView", parentView);
        ReflectionHelpers.setField(pncMedicalHistoryActivityFlv, "context", context);
        ReflectionHelpers.setField(pncMedicalHistoryActivityFlv, "inflater", inflater);
        pncMedicalHistoryActivityFlv.processViewData(groupedVisits, context, memberObject);
        Mockito.verify(parentView, Mockito.times(5)).addView(Mockito.any(View.class));
    }

    private List<GroupedVisit> getSampleGroups() {
        List<GroupedVisit> groupedVisits = new ArrayList<>();

        groupedVisits.add(new GroupedVisit("12345", "Mum", new ArrayList<>()));
        groupedVisits.add(new GroupedVisit("23456", "Baby", new ArrayList<>()));
        return groupedVisits;
    }
}
