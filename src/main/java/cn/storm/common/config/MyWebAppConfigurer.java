package cn.storm.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.storm.common.exception.ExceptionResolver;
import cn.storm.common.interceptor.RemoteInterceptor;

/**
 * spring boot的配置类
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-06-26 09:59
 * @version 1.0
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * 把我们的拦截器注入为bean
	 */
	@Bean
	public HandlerInterceptor getRemoteInterceptor() {
		return new RemoteInterceptor();
	}
	
	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 1.跨域访问拦截器
		registry.addInterceptor(getRemoteInterceptor()) // 注册
				.addPathPatterns("/**");// 添加拦截规则

		super.addInterceptors(registry);
	}

	/**
	 * 把我们的异常处理器注入为bean
	 */
	@Bean
	public HandlerExceptionResolver getExceptionResolver() {
		return new ExceptionResolver();
	}

	/**
	 * 注册异常处理器
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(getExceptionResolver());
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

}
