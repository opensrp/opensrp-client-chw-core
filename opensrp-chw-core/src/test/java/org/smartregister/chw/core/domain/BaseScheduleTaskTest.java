package org.smartregister.chw.core.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseScheduleTaskTest {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private final BaseScheduleTask baseScheduleTask = new BaseScheduleTask();
    private Date date;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        date = sdf.parse("2021-02-08");
    }
    @Test
    public void setIDAndGetIDTest () {
        baseScheduleTask.setID("qwert1234");
        Assert.assertEquals(baseScheduleTask.getID(), "qwert1234");
    }
    @Test
    public void setBaseEntityIDAndGetBaseEntityIDTest () {
        baseScheduleTask.setBaseEntityID("qwert1234");
        Assert.assertEquals(baseScheduleTask.getBaseEntityID(), "qwert1234");
    }
    @Test
    public void setScheduleGroupNameAndGetScheduleGroupNameTest () {
        baseScheduleTask.setScheduleGroupName("Jina Fulani");
        Assert.assertEquals(baseScheduleTask.getScheduleGroupName(), "Jina Fulani");
    }

    @Test
    public void setScheduleNameAndGetScheduleNameTest () {
        baseScheduleTask.setScheduleName("Another Name");
        Assert.assertEquals(baseScheduleTask.getScheduleName(), "Another Name");
    }

    @Test
    public void setScheduleDueDateAndGetScheduleDueDateTest () {
        baseScheduleTask.setScheduleDueDate(date);
        Assert.assertEquals(baseScheduleTask.getScheduleDueDate(), date);
    }
    @Test
    public void setScheduleOverDueDateAndGetScheduleOverDueDateTest () {
        baseScheduleTask.setScheduleOverDueDate(date);
        Assert.assertEquals(baseScheduleTask.getScheduleOverDueDate(), date);
    }
    @Test
    public void setScheduleCompletionDateAndGetScheduleCompletionDateTest () {
        baseScheduleTask.setScheduleCompletionDate(date);
        Assert.assertEquals(baseScheduleTask.getScheduleCompletionDate(), date);
    }
    @Test
    public void setUpdatedAtAndGetUpdatedAtTest () {
        baseScheduleTask.setUpdatedAt(date);
        Assert.assertEquals(baseScheduleTask.getUpdatedAt(), date);
    }
    @Test
    public void setCreatedAtAndGetCreatedAtTest () {
        baseScheduleTask.setCreatedAt(date);
        Assert.assertEquals(baseScheduleTask.getCreatedAt(), date);
    }
 }
