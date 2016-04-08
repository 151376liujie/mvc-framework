package org.framework.java.controller.dispatcher;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.framework.java.BeanContainer;
import org.framework.java.bean.ActionHandler;
import org.framework.java.utils.ConfigUtils;
import org.framework.java.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心请求转发器，拦截所有请求
 * 
 * @author liujie
 * 
 */

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class FrameworkServlet extends HttpServlet {

    private static final long serialVersionUID = 5567022349711280068L;

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(FrameworkServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
	ServletContext servletContext = config.getServletContext();
	// 注册处理jsp的servlet
	ServletRegistration jspServlet = servletContext
		.getServletRegistration("jsp");

	jspServlet.addMapping(ConfigUtils.getAppViewPath() + "*");
	// 注册默认的servlet
	ServletRegistration defaultServlet = servletContext
		.getServletRegistration("default");

	defaultServlet.addMapping(ConfigUtils.getAppBasePath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request,
	    HttpServletResponse response)
	    throws ServletException, IOException {
	String requestURl = request.getPathInfo();
	String method = request.getMethod();
	LOGGER.info("request url :{}", requestURl);
	ActionHandler actionHandler = ControllerUtils.getActionHandler(requestURl, method);
	if (actionHandler != null) {
	    Method actionMethod = actionHandler.getActionMethod();
	    Class<?> controllerClass = actionHandler.getControllerClass();
	    Object controller = BeanContainer.getBean(controllerClass);
	    Object[] params = new Object[10];
	    try {
		actionMethod.invoke(controller, params);
		// TODO
	    } catch (Exception e) {
		LOGGER.error(e.getMessage(), e);
	    }
	}
    }

    @Override
    public void destroy() {
	LOGGER.info("the web container is going to stop...");
    }

}
