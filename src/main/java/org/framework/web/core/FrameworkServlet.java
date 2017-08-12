package org.framework.web.core;

import org.apache.commons.lang3.StringUtils;
import org.framework.BeanContainer;
import org.framework.FrameworkLoader;
import org.framework.utils.*;
import org.framework.web.FormRequest;
import org.framework.web.WebRequestParameterBinder;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
        //初始化文件上传处理器
        FileUploadHandler.init(servletContext);
        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");

        String appViewPath = ConfigUtils.getAppViewPath();
        jspServlet.addMapping(appViewPath + "*");
        // 注册处理静态资源的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        //获取静态资源包路径
        String appWebResourcePath = ConfigUtils.getAppWebResourcePath();
        defaultServlet.addMapping(appWebResourcePath + "*");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("register jsp servlet to url: {} ", appViewPath);
            LOGGER.info("register default servlet to url: {}", appWebResourcePath);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        String requestURl = request.getPathInfo();

        String method = request.getMethod().toLowerCase();
        LOGGER.info("request url :{},request method :{}..", requestURl, method);
        //忽略/favicon.ico请求
        if (requestURl.equals("/favicon.ico")) {
            return;
        }
        ActionHandler actionHandler = ControllerUtils.getActionHandler(requestURl, method);
        if (actionHandler != null) {
            Method actionMethod = actionHandler.getActionMethod();
            Class<?> controllerClass = actionHandler.getControllerClass();
            Object controller = BeanContainer.getBean(controllerClass);
            WebRequestParameterBinder webRequestParameterBinder;
            if (FileUploadHandler.isMultiPartRequest(request)) {
                webRequestParameterBinder = FileUploadHandler.createWebRequestParameterBinder(request);
            } else {
                // 获取请求参数
                List<FormRequest> formRequests = getParameterMap(request);
                webRequestParameterBinder = new WebRequestParameterBinder(formRequests);
            }
            try {
                Object result;
                int parameterCount = actionMethod.getParameterCount();
                //用户没有传递参数
                if (parameterCount == 0) {
                    result = ReflectionUtils.invokeMethod(controller, actionMethod);
                } else if (parameterCount == 1) {
                    result = ReflectionUtils.invokeMethod(controller, actionMethod, webRequestParameterBinder);
                } else {
                    throw new RuntimeException("more than one argument is not supported so far.");
                }
                if (result instanceof PageView) {
                    resolvePageView(request, response, (PageView) result);
                } else if (result instanceof JsonResponseView) {
                    resolveJsonView(response, (JsonResponseView) result);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private void resolveJsonView(HttpServletResponse response, JsonResponseView result) throws IOException {
        // 返回json数据
        JsonResponseView responseData = result;
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonString = JsonUtils.toJsonString(responseData);
        writer.write(jsonString);
        writer.close();
    }

    private void resolvePageView(HttpServletRequest request, HttpServletResponse response, PageView result) throws IOException, ServletException {
        // 返回视图
        PageView view = result;
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
    }

    private List<FormRequest> getParameterMap(HttpServletRequest request) throws IOException {
        List<FormRequest> formRequests = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(parameterName);
            if (parameterValues != null && parameterValues.length > 0) {
                if (parameterValues.length == 1) {
                    String paramVal = new String(parameterValues[0].getBytes(Charset.forName("ISO-8859-1")),
                            Charset.forName("utf-8"));
                    formRequests.add(new FormRequest(parameterName, paramVal));
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String pVal : parameterValues) {
                        String paramVal = new String(pVal.getBytes(Charset.forName("ISO-8859-1")),
                                Charset.forName("utf-8"));
                        sb.append(paramVal).append("@");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    formRequests.add(new FormRequest(parameterName, sb.toString()));
                }
            }
        }

        String body = CodecUtils.decodeUrl(StreamUtils.getString(request.getInputStream()));
        if (StringUtils.isNotEmpty(body)) {
            String[] split = StringUtils.split(body, "&");
            if (split.length > 0) {
                for (String param : split) {
                    String[] arr = StringUtils.split(param, "=");
                    if (arr.length == 2) {
                        String paramName = arr[0];
                        String paramValue = arr[1];
                        formRequests.add(new FormRequest(paramName, paramValue));
                    }
                }
            }
        }
        return formRequests;
    }

    @Override
    public void destroy() {
        LOGGER.info("the web container is going to stop...");
    }

}
