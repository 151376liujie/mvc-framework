package org.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.framework.annotation.Controller;
import org.framework.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类操作工具类
 * 
 * @author liujie
 * 
 */
public final class ClassUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);
    
    /**
     * 获取类加载器
     * 
     * @return
     */
    public static ClassLoader getClassLoader() {
	return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载指定类
     * 
     * @param className
     * @return
     */
    public static Class<?> loadClass(String className) {
	return loadClass(className, false);
    }

    /**
     * 加载指定类，并指定是否执行初始化代码块
     * 
     * @param className
     * @param isInitialed
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialed) {
	Class<?> cls = null;
	try {
	    cls = (Class<?>) Class.forName(className, isInitialed,
		    getClassLoader());
	} catch (ClassNotFoundException e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return cls;
    }


    /**
     * 加载制定包名下的所有类
     * 
     * @param basePackage
     * @return
     */
    public static Set<Class<?>> getClassSet(String basePackage) {
	Set<Class<?>> classSet = new HashSet<Class<?>>();
	String baseUrl = basePackage.replace(".", "/");
	try {
	    Enumeration<URL> urls = getClassLoader().getResources(baseUrl);
	    while (urls.hasMoreElements()) {
		URL url = urls.nextElement();
		if (url.getProtocol().equals("file")) {
		    String path = url.getPath();
		    System.out.println(path);
		    addClass(classSet, path, basePackage);
		} else if (url.getProtocol().equals("jar")) {
		    JarURLConnection conn = (JarURLConnection) url
			    .openConnection();
		    if (conn != null) {
			JarFile jarFile = conn.getJarFile();
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
			    JarEntry jarEntry = entries.nextElement();
			    String name = jarEntry.getName();
			    if (name.endsWith(".class")) {
				String className = name.substring(0,
					name.lastIndexOf(".class"));
				className = className.replaceAll("/", ".");
				doAddClass(classSet, className);
			    }
			}
		    }
		}
	    }
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return classSet;
    }

    /**
     * 获取所有controller类
     * 
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
	return getClassSetByType(Controller.class);
    }

    /**
     * 获取所有的service类
     * 
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
	return getClassSetByType(Service.class);
    }

    /**
     * 根据指定类型获取类集合
     * 
     * @param clazz
     * @return
     */
    public static Set<Class<?>> getClassSetByType(
	    Class<? extends Annotation> clazz) {
	Set<Class<?>> set = new HashSet<Class<?>>();
	String basePackage = ConfigUtils.getAppBasePath();
	Set<Class<?>> classSet = getClassSet(basePackage);
	for (Class<?> cls : classSet) {
	    if (cls.isAnnotationPresent(clazz)) {
		set.add(cls);
	    }
	}
	return set;
    }

    public static Set<Class<?>> getBeanClassSet() {
	Set<Class<?>> classSet = new HashSet<Class<?>>();
	classSet.addAll(getControllerClassSet());
	classSet.addAll(getServiceClassSet());
	return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath,
	    String packageName) {
	File[] files = new File(packagePath).listFiles(new FileFilter() {
	    @Override
	    public boolean accept(File pathname) {
		return (pathname.isFile() && pathname.getName().endsWith(
			".class"))
			|| pathname.isDirectory();
		}
	});
	for (File file : files) {
	    String fileName = file.getName();
	    if (file.isFile()) {
		int lastIndexOf = fileName.lastIndexOf(".class");
		String classPath = packageName + "."
			+ fileName.substring(0, lastIndexOf);
		doAddClass(classSet, classPath);
	    } else {
		String subPackagePath = fileName;
		if (StringUtils.isNotEmpty(packagePath)) {
		    subPackagePath = packagePath + "/" + subPackagePath;
		}
		String subPackageName = fileName;
		if (StringUtils.isNotEmpty(packagePath)) {
		    subPackageName = packageName + "." + fileName;
		}
		addClass(classSet, subPackagePath, subPackageName);
	    }
	}

    }

    private static void doAddClass(Set<Class<?>> classSet, String classPath) {
	Class<?> classForLoad = loadClass(classPath.replace("/", "."), false);
	classSet.add(classForLoad);
    }

}
