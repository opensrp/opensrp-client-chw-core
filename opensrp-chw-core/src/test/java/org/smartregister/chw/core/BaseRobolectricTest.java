package org.smartregister.chw.core;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.smartregister.chw.core.application.TestApplication;
import org.smartregister.chw.core.shadows.AssetHandlerShadow;
import org.smartregister.chw.core.shadows.BaseJobShadow;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {27}, shadows = {BaseJobShadow.class, AssetHandlerShadow.class}, application = TestApplication.class)
public abstract class BaseRobolectricTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

}
