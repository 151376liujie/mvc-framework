package org.framework.java.utils;

import java.util.Set;

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

}
