package org.framework.java.utils;

import java.util.Set;

import org.framework.utils.ClassUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClassUtils {

    private final Logger LOGGER = LoggerFactory
	    .getLogger(TestClassUtils.class);

    @Test
    public void testGetClassLoader() {
	LOGGER.info(ClassUtils.getClassLoader().toString());
    }

    @Test
    public void testLoadClass() {
	LOGGER.info(ClassUtils.loadClass("java.lang.String", true).toString());
    }

    @Test
    public void testGetClassSet() {
	Set<Class<?>> classSet = ClassUtils.getClassSet("org.framework");
	LOGGER.info(classSet.toString());
    }

    @Test
    public void testGetControllerClassSet() {
	Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
	Assert.assertTrue(controllerClassSet.isEmpty());
    }

    @Test
    public void testGetServiceClassSet() {
	Set<Class<?>> serviceClassSet = ClassUtils.getServiceClassSet();
	Assert.assertTrue(serviceClassSet.isEmpty());
    }

}
