package org.smartregister.chw.core.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.domain.MonthlyTally;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SentMonthlyFragmentTest extends BaseUnitTest {

    private SentMonthlyFragment sentMonthlyFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        FragmentActivity activity = Robolectric
                .buildActivity(FragmentActivity.class).create().start()
                .resume().get();

        sentMonthlyFragment = SentMonthlyFragment.newInstance();
        HashMap<String, ArrayList<MonthlyTally>> monthlyTallies = new HashMap<String, ArrayList<MonthlyTally>>() {
            {
                ArrayList<MonthlyTally> juneTally = new ArrayList<>();
                MonthlyTally june = new MonthlyTally();
                june.setCreatedAt(new Date());
                juneTally.add(june);
                put("June", juneTally);
            }
        };
        sentMonthlyFragment.setSentMonthlyTallies(monthlyTallies);

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager()
                .findFragmentByTag("SentMonthlyFragment");
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.add(sentMonthlyFragment, "SentMonthlyFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Test
    public void testExpandableListNotNull() {
        Assert.assertNotNull(sentMonthlyFragment);
        Assert.assertNotNull(sentMonthlyFragment.getExpandableListView());
    }
}