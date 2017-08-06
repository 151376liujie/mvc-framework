package org.framework.controller;

import org.framework.bean.ActionHandler;
import org.framework.utils.ControllerUtils;
import org.framework.utils.FrameworkLoader;
import org.framework.utils.ReflectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Author: jonny
 * Time: 2017-08-06 11:07.
 */
public class ControllerTest {

    @Before
    public void setUp() throws Exception {
        FrameworkLoader.init();
    }

    @Test
    public void testLocateActionHandler() {

        ActionHandler actionHandler = ControllerUtils.getActionHandler("/hello", "get");
        Assert.assertNull(actionHandler);

        ActionHandler handler = ControllerUtils.getActionHandler("/getTime", "get");
        Assert.assertNotNull(handler);
        System.out.println(handler);

    }

    @Test
    public void testExecuteActionHandler() throws Exception {
        ActionHandler actionHandler = ControllerUtils.getActionHandler("/getTime", "get");
        Assert.assertNotNull(actionHandler);
        Object controllerInstance = ReflectionUtils.newInstance(actionHandler.getControllerClass());
        Object response = ReflectionUtils.invokeMethod(controllerInstance, actionHandler.getActionMethod(), null);
        Assert.assertNotNull(response);
        System.out.println(response.toString());
    }
}
