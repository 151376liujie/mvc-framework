package org.framework.java.utils;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class TestClassUtils {

    @Test
    public void testGetClassLoader() {
	System.out.println(ClassUtils.getClassLoader());
    }

    @Test
    public void testLoadClass() {
	System.out.println(ClassUtils.loadClass("java.lang.String", true));
    }

    @Test
    public void testGetClassSet() {
	Set<Class<?>> classSet = ClassUtils.getClassSet("org.framework.java");
	System.out.println(classSet);
    }

    @Test
    public void testGetControllerClassSet() {
	Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
	Assert.assertFalse(controllerClassSet.isEmpty());
	System.out.println(controllerClassSet);
    }

    @Test
    public void testGetServiceClassSet() {
	Set<Class<?>> serviceClassSet = ClassUtils.getServiceClassSet();
	Assert.assertFalse(serviceClassSet.isEmpty());
	System.out.println(serviceClassSet);
    }

}
