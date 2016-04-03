package org.framework.java.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
     * 加载制定类
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

		}
	    }
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	}
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
