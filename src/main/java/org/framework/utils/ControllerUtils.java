package org.framework.utils;

import org.framework.annotation.ActionMapping;
import org.framework.bean.ActionHandler;
import org.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器工具类
 *
 * @author liujie
 */
public final class ControllerUtils {

    // key-请求，value-处理器
    private static final Map<Request, ActionHandler> actionMap = new HashMap<Request, ActionHandler>();

    static {
        Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
        for (Class<?> controllerClass : controllerClassSet) {
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(ActionMapping.class)) {
                    ActionMapping annotation = method
                            .getAnnotation(ActionMapping.class);
                    // 构建request对象
                    Request request = buildRequest(annotation);
                    // 构建actionhandler对象
                    ActionHandler actionHandler = buildActionHandler(
                            controllerClass, method);
                    actionMap.put(request, actionHandler);
                }
            }
        }
    }

    /**
     * 根据指定的request获取actionhandler对象
     *
     * @param request
     * @return
     */
    public static ActionHandler getActionHandler(String requestUrl,
                                                 String method) {
        Request request = new Request(method, requestUrl);
        return actionMap.get(request);
    }

    private static Request buildRequest(ActionMapping annotation) {
        Request request = new Request(annotation.method(),
                annotation.requestUrl());
        return request;
    }

    private static ActionHandler buildActionHandler(Class<?> controllerClass,
                                                    Method method) {
        ActionHandler actionHandler = new ActionHandler();
        actionHandler.setControllerClass(controllerClass);
        actionHandler.setActionMethod(method);
        return actionHandler;
    }

}
