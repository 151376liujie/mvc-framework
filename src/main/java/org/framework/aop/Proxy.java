package org.framework.aop;

/**
 * 代理接口
 * Created by liujie on 2016/4/26 23:44.
 */
public interface Proxy {

    /**
     * 代理方法
     *
     * @param proxyChain
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
