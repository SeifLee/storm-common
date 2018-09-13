package cn.storm.common.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * controller详细日志信息记录(需要在配置文件中配置下才行,暂时不启用该功能)
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-30 14:44
 * @version 1.0
 */
public class ControllerMethodInterceptor implements MethodInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static ObjectMapper jsonMapper = new ObjectMapper();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		logger.info("Before: interceptor name: {}", invocation.getMethod().getName());

		logger.info("Arguments: {}", jsonMapper.writeValueAsString(invocation.getArguments()));

		Object result = invocation.proceed();

		logger.info("After: result: {}", jsonMapper.writeValueAsString(result));

		return result;
	}

}
