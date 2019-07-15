package com.centit.core.filter;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: HttpThreadWrapper 
 * @Description: 在ThreadLocal中封装请求响应
 * @author: zhu_hj
 * @date: 2018年5月16日 上午8:39:24
 */
public class HttpThreadWrapper implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3434170518095254917L;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public HttpThreadWrapper(HttpServletRequest request, HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
