package org.framework.java.controller.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.framework.java.BeanContainer;
import org.framework.java.bean.ActionHandler;
import org.framework.java.model.PageView;
import org.framework.java.model.ResponseData;
import org.framework.java.utils.ConfigUtils;
import org.framework.java.utils.ControllerUtils;
import org.framework.java.utils.FrameworkLoader;
import org.framework.java.utils.JsonUtils;
import org.framework.java.utils.ReflectionUtils;
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

	LOGGER.info(" prepare to init class... ");
	// 初始化工作
	FrameworkLoader.init();

	LOGGER.info(" load class finished.. ");
	ServletContext servletContext = config.getServletContext();
	// 注册处理jsp的servlet
	ServletRegistration jspServlet = servletContext
		.getServletRegistration("jsp");

	String appViewPath = ConfigUtils.getAppViewPath();
	jspServlet.addMapping(appViewPath + "*");
	LOGGER.info("registe jsp servlet to servlet context..");
	// 注册默认的servlet
	ServletRegistration defaultServlet = servletContext
		.getServletRegistration("default");

	String appWebResourcePath = ConfigUtils.getAppWebResourcePath();
	defaultServlet.addMapping(appWebResourcePath + "*");
	LOGGER.error("registe default servlet to servlet context..");
    }

    @Override
    protected void service(HttpServletRequest request,
	    HttpServletResponse response)
	    throws ServletException, IOException {
	String requestURl = request.getPathInfo();
	String method = request.getMethod().toLowerCase();
	LOGGER.info("request url :{},request method :{}..", requestURl, method);
	ActionHandler actionHandler = ControllerUtils.getActionHandler(requestURl, method);
	if (actionHandler != null) {
	    Method actionMethod = actionHandler.getActionMethod();
	    Class<?> controllerClass = actionHandler.getControllerClass();
	    Object controller = BeanContainer.getBean(controllerClass);
	    Map<String, String[]> parameterMap = request.getParameterMap();
	    List<Object> paramList = new ArrayList<Object>();
	    for (Entry<String, String[]> entry : parameterMap.entrySet()) {
		String[] value = entry.getValue();
		for (String parm : value) {
		    paramList.add(parm);
		}
	    }
	    try {
		Object result = ReflectionUtils.invokeMethod(controller,
			actionMethod, paramList.toArray());
		if (result instanceof PageView) {
		    // 返回视图
		    PageView view = (PageView) result;
		    String path = view.getLocation();
		    Map<String, Object> modelMap = view.getModelMap();
		    if (StringUtils.isNotEmpty(path)) {
			if (path.startsWith("/")) {
			    response.sendRedirect(request.getContextPath()
				    + path);
			} else {
			    for (Entry<String, Object> entry : modelMap
				    .entrySet()) {
				request.setAttribute(entry.getKey(),
					entry.getValue());
			    }
			    request.getRequestDispatcher(
				    ConfigUtils.getAppViewPath() + path)
				    .forward(request, response);
			}
		    }
		} else if (result instanceof ResponseData) {
		    // 返回json数据
		    ResponseData responseData = (ResponseData) result;
		    Object model = responseData.getModel();
		    if (model != null) {
			PrintWriter writer = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String jsonString = JsonUtils.toJsonString(model);
			writer.write(jsonString);
			writer.close();
		    }
		}
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
