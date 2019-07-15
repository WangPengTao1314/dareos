package com.centit.core.exception;

import java.rmi.ServerException;

/**
 * @ClassName: BizException 
 * @Description: 自定义异常--用于Service层抛出业务异常
 * @author: zhu_hj
 * @date: 2018年5月15日 上午9:22:21
 */
public class BizException extends ServerException {
	private static final long serialVersionUID = 1L;

	public BizException(String s) {
		super(s);
	}

	public BizException(String s, Exception ex) {
        super(s, ex);
    }
}
