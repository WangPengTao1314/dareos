package com.centit.core.filter;


/**
 * @ClassName: RequestThreadLocal 
 * @Description: 将HttpServletRequest请求与本地线程绑定，方便在非Controller层获取HttpServletRequest实例
 * @author: zhu_hj
 * @date: 2018年5月16日 上午8:39:35
 */
public class RequestThreadLocal extends ThreadLocal<HttpThreadWrapper> {
    private static ThreadLocal<HttpThreadWrapper> threadLocal = new ThreadLocal<>();

    private RequestThreadLocal() {
        super();
    }

    public static void setHttpThreadWrapper(HttpThreadWrapper wrapper) {
        threadLocal.set(wrapper);
    }

    public static HttpThreadWrapper getHttpThreadWrapper() {
        return threadLocal.get();
    }
}