package org.smartregister.chw.core.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

public class PermissionEventTest {

    PermissionEvent permissionEvent = new PermissionEvent();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAndSetPermissionType() {
        permissionEvent.setPermissionType(1);
        Assert.assertEquals(permissionEvent.getPermissionType(), 1);
    }

    @Test
    public void testSetGranted() {
        permissionEvent.setGranted(true);
        Assert.assertEquals(permissionEvent.isGranted(), true);
    }

    @Test
    public void testisGranted() {
        PermissionEvent permissionEvent1 = new PermissionEvent(1,true);
        Assert.assertEquals(permissionEvent1.isGranted(), true);
    }
}
