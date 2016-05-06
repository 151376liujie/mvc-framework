package org.framework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器，负责生成代理类
 * Created by liujie on 2016/4/27 22:40.
 */
public class ProxyManager {

    /**
     * 根据指定目标类型和代理数组生成代理对象
     *
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
	@SuppressWarnings("unchecked")
    public static <T> T getProxy(final Class<T> targetClass,
                                 final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object target, Method method, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(proxyList, targetClass, target, method, methodProxy, methodParams).doProxyChain();
            }
        });
    }

}
