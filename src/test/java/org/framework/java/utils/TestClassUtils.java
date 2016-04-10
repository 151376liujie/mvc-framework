package org.framework.java.utils;

import java.util.Set;

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
	Set<Class<?>> classSet = ClassUtils.getClassSet("org.framework.java");
	LOGGER.info(classSet.toString());
    }

    @Test
    public void testGetControllerClassSet() {
	Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
	Assert.assertFalse(controllerClassSet.isEmpty());
	LOGGER.info(controllerClassSet.toString());
    }

    @Test
    public void testGetServiceClassSet() {
	Set<Class<?>> serviceClassSet = ClassUtils.getServiceClassSet();
	Assert.assertFalse(serviceClassSet.isEmpty());
	LOGGER.info(serviceClassSet.toString());
    }

}
