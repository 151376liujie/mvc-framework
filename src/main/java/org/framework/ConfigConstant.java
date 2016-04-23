package org.framework;

/**
 * 框架常量类
 * 
 * @author liujie
 * 
 */
public interface ConfigConstant {
    /**
     * 配置文件的文件名
     */
    String CONFIG_FILE = "mini-framework.properties";
    String JDBC_DRIVER = "mini.framework.jdbc.driver";
    String JDBC_URL = "mini.framework.jdbc.url";
    String JDBC_USERNAME = "mini.framework.jdbc.username";
    String JDBC_PASSWORD = "mini.framework.jdbc.password";
    String FRAMEWORK_BASE_PACKAGE="mini.framework.basepackage";
    String FRAMEWORK_VIEW_PATH = "mini.framework.view.path";
    /**
     * web项目的资源路径（css,js文件路径）
     */
    String FRAMEWORK_WEB_RESOURCE_PATH = "mini.framework.web.resource.path";

}
