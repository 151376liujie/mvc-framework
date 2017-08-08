package org.framework.controller;

import org.framework.BeanContainer;
import org.framework.FrameworkLoader;
import org.framework.bean.ActionHandler;
import org.framework.utils.ControllerUtils;
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
        HelloController helloController = BeanContainer.getBean(HelloController.class);
        Assert.assertNotNull(helloController);
        System.out.println(helloController.getServerTime());
    }
}
