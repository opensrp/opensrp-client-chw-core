package org.smartregister.chw.core.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.chw.core.BaseUnitTest;
import org.smartregister.chw.core.activity.impl.CoreAboveFiveChildProfileActivityImpl;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.helper.ImageRenderHelper;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class CoreAboveFiveChildProfileActivityTest extends BaseUnitTest {

    private CoreAboveFiveChildProfileActivity activity;

    private ActivityController<CoreAboveFiveChildProfileActivityImpl> controller;

    @Mock
    private ImageRenderHelper imageRenderHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Context context = Context.getInstance();
        CoreLibrary.init(context);

        //Auto login by default
        context.session().start(context.session().lengthInMilliseconds());

        controller = Robolectric.buildActivity(CoreAboveFiveChildProfileActivityImpl.class).create().start();
        activity = controller.get();
    }

    @After
    public void tearDown() {
        try {
            activity.finish();
            controller.pause().stop().destroy(); //destroy controller if we can
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Test
    public void registerPresenter() {
        activity.initializePresenter();
        Assert.assertNotNull(activity.presenter());
    }

    @Test
    public void setProfileImageBorderTest() {
        CircleImageView imageView = Mockito.spy(new CircleImageView(RuntimeEnvironment.application));
        ReflectionHelpers.setField(activity, "imageViewProfile", imageView);
        ReflectionHelpers.setField(activity, "imageRenderHelper", imageRenderHelper);
        activity.setProfileImage("1234");
        Assert.assertEquals(0, imageView.getBorderWidth());
    }

    @Test
    public void setParentNameViewGone() {
        TextView textView = Mockito.spy(new TextView(RuntimeEnvironment.application));
        ReflectionHelpers.setField(activity, "textViewParentName", textView);
        activity.setParentName("sdfs");
        Assert.assertEquals(View.GONE, textView.getVisibility());
    }

    @Test
    public void testOnOptionsItemSelected() {
        activity = Mockito.spy(activity);

        MenuItem menuItem = Mockito.mock(MenuItem.class);
        CoreConstants.JSON_FORM.setLocaleAndAssetManager(activity.getApplicationContext().getResources().getConfiguration().locale, activity.getApplicationContext().getAssets());

        // back pressed
        Mockito.doReturn(android.R.id.home).when(menuItem).getItemId();
        Mockito.doNothing().when(activity).onBackPressed();

        activity.onOptionsItemSelected(menuItem);
        Mockito.verify(activity).onBackPressed();
    }

    @Test
    public void testActivityLoaded() {
        Assert.assertNotNull(activity);
    }

}