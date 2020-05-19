package org.smartregister.chw.core.interactor;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.interactors.JsonFormInteractor;
import com.vijay.jsonwizard.interfaces.FormWidgetFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smartregister.chw.core.widget.ServiceEditTextFactory;

import static org.junit.Assert.*;

public class ServiceJsonFormInteractorTest {

    private JsonFormInteractor serviceJsonFormInteractor;

    @Before
    public void setUp(){
        serviceJsonFormInteractor =  ServiceJsonFormInteractor.getServiceInteractorInstance();
    }

    @Test
    public void testThatEditTextInstanceWasUpdated() {
        FormWidgetFactory formWidgetFactory = serviceJsonFormInteractor.map.get(JsonFormConstants.EDIT_TEXT);
        Assert.assertTrue(formWidgetFactory instanceof ServiceEditTextFactory);
    }
}