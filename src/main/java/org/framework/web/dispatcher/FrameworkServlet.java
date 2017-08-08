package org.framework.web.dispatcher;

import org.apache.commons.lang3.StringUtils;
import org.framework.BeanContainer;
import org.framework.FrameworkLoader;
import org.framework.bean.ActionHandler;
import org.framework.model.PageView;
import org.framework.model.RequestParameter;
import org.framework.model.ResponseData;
import org.framework.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 核心拦截器，拦截所有请求
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class FrameworkServlet extends HttpServlet {

    private static final long serialVersionUID = 5567022349711280068L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FrameworkServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {

        LOGGER.info(" prepare to init class... ");
        // 初始化工作
        FrameworkLoader.init();

        LOGGER.info(" load class finished.. ");
        ServletContext servletContext = config.getServletContext();
        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");

        String appViewPath = ConfigUtils.getAppViewPath();
        jspServlet.addMapping(appViewPath + "*");
        LOGGER.info("register jsp servlet to url: {} ", appViewPath);
        // 注册处理静态资源的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");

        String appWebResourcePath = ConfigUtils.getAppWebResourcePath();
        defaultServlet.addMapping(appWebResourcePath + "*");
        LOGGER.error("register default servlet to url: {}", appWebResourcePath);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String requestURl = request.getPathInfo();

        String method = request.getMethod().toLowerCase();
        LOGGER.info("request url :{},request method :{}..", requestURl, method);
        ActionHandler actionHandler = ControllerUtils.getActionHandler(requestURl, method);
        if (actionHandler != null) {
            Method actionMethod = actionHandler.getActionMethod();
            Class<?> controllerClass = actionHandler.getControllerClass();
            Object controller = BeanContainer.getBean(controllerClass);
            // 获取请求参数
            Map<String, Object> map = getParameterMap(request);
            String body = CodecUtils.decodeUrl(StreamUtils.getString(request.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] split = StringUtils.split(body, "&");
                if (split.length > 0) {
                    for (String param : split) {
                        String[] arr = StringUtils.split(param, "=");
                        if (arr.length == 2) {
                            String paramName = arr[0];
                            String paramValue = arr[1];
                            map.put(paramName, paramValue);
                        }
                    }
                }
            }
            try {
                RequestParameter requestParameter = new RequestParameter(map);
                Object result = null;
                if (requestParameter.isEmpty()) {
                    result = ReflectionUtils.invokeMethod(controller, actionMethod);
                } else {
                    result = ReflectionUtils.invokeMethod(controller, actionMethod, requestParameter);
                }
                if (result instanceof PageView) {
                    // 返回视图
                    PageView view = (PageView) result;
                    String path = view.getLocation();
                    Map<String, Object> modelMap = view.getModelMap();
                    if (StringUtils.isNotEmpty(path)) {
                        if (path.startsWith("/")) {
                            response.sendRedirect(request.getContextPath() + path);
                        } else {
                            for (Entry<String, Object> entry : modelMap.entrySet()) {
                                request.setAttribute(entry.getKey(), entry.getValue());
                            }
                            request.getRequestDispatcher(ConfigUtils.getAppViewPath() + path)
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

    private Map<String, Object> getParameterMap(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(parameterName);
            if (parameterValues != null && parameterValues.length > 0) {
                if (parameterValues.length == 1) {
                    String paramVal = new String(parameterValues[0].getBytes(Charset.forName("ISO-8859-1")),
                            Charset.forName("utf-8"));
                    map.put(parameterName, paramVal);
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String pVal : parameterValues) {
                        String paramVal = new String(pVal.getBytes(Charset.forName("ISO-8859-1")),
                                Charset.forName("utf-8"));
                        sb.append(paramVal).append("@");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    map.put(parameterName, sb.toString());
                }
            }
        }
        return map;
    }

    @Override
    public void destroy() {
        LOGGER.info("the web container is going to stop...");
    }

}
