package org.smartregister.chw.core.helper;

import androidx.appcompat.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.smartregister.chw.anc.domain.GroupedVisit;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.core.BaseUnitTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

public class BaMedicalHistoryActivityHelperTest extends BaseUnitTest {

    @Spy
    private BaMedicalHistoryActivityHelper baMedicalHistoryActivityHelper;

    private List<Visit> visits = Arrays.asList(new Visit(), new Visit());

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        baMedicalHistoryActivityHelper.bindViews(Robolectric.buildActivity(AppCompatActivity.class).get());
    }

    @Test
    public void shouldProcessViewData() {
        MemberObject memberObject = new MemberObject();
        String baseEntityId = "some-base-entity-id";
        memberObject.setBaseEntityId(baseEntityId);
        memberObject.setFirstName("Mom");
        memberObject.setMiddleName("Mid");
        memberObject.setLastName("Last");
        List<GroupedVisit> groupedVisits = Arrays.asList(
                new GroupedVisit(baseEntityId, "Group Visit One", visits),
                new GroupedVisit("another-base-entity-id", "Group Visit Two", visits),
                new GroupedVisit("baseEntityId", "Group Visit Three", visits)
        );
        baMedicalHistoryActivityHelper.processViewData(groupedVisits, RuntimeEnvironment.systemContext, memberObject);
        Mockito.verify(baMedicalHistoryActivityHelper, Mockito.atLeastOnce()).processMotherDetails(eq(visits), eq(memberObject));
        Mockito.verify(baMedicalHistoryActivityHelper, Mockito.atLeastOnce()).processChildDetails(eq(visits), Mockito.anyString());
    }
}