package org.smartregister.chw.core;


import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.AncLibraryShadowUtil;
import org.smartregister.chw.core.shadows.ContextShadow;
import org.smartregister.chw.core.shadows.CustomFontTextViewShadowHelper;
import org.smartregister.chw.core.shadows.FamilyLibraryShadowUtil;
import org.smartregister.chw.core.shadows.UtilsShadowUtil;

/**
 * @author rkodev
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, shadows = {ContextShadow.class, UtilsShadowUtil.class, FamilyLibraryShadowUtil.class, CustomFontTextViewShadowHelper.class, AncLibraryShadowUtil.class})
public abstract class BaseUnitTest {

}
