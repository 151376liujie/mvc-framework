package org.framework.utils;

import org.framework.annotation.HandlerMapping;
import org.framework.web.core.ActionHandler;
import org.framework.web.core.HandlerMappingParameter;

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
    private static final Map<HandlerMappingParameter, ActionHandler> actionMap = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
        for (Class<?> controllerClass : controllerClassSet) {
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(HandlerMapping.class)) {
                    HandlerMapping annotation = method
                            .getAnnotation(HandlerMapping.class);
                    // 构建request对象
                    HandlerMappingParameter handlerMappingParameter = buildRequest(annotation);
                    // 构建actionhandler对象
                    ActionHandler actionHandler = buildActionHandler(
                            controllerClass, method);
                    actionMap.put(handlerMappingParameter, actionHandler);
                }
            }
        }
    }

    /**
     * 根据指定的request获取actionhandler对象
     *
     * @param requestUrl 请求URL
     * @param method     http方法
     * @return
     */
    public static ActionHandler getActionHandler(String requestUrl,
                                                 String method) {
        HandlerMappingParameter handlerMappingParameter = new HandlerMappingParameter(method, requestUrl);
        return actionMap.get(handlerMappingParameter);
    }

    private static HandlerMappingParameter buildRequest(HandlerMapping annotation) {
        HandlerMappingParameter handlerMappingParameter = new HandlerMappingParameter(annotation.method(),
                annotation.requestUrl());
        return handlerMappingParameter;
    }

    private static ActionHandler buildActionHandler(Class<?> controllerClass,
                                                    Method method) {
        ActionHandler actionHandler = new ActionHandler(controllerClass, method);
        return actionHandler;
    }

}
