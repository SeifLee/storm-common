package cn.storm.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 远程访问相关拦截器
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-03-20 11:55
 * @version 1.0
 */
public class RemoteInterceptor implements HandlerInterceptor {

	/**
	 * 进入controller之前做的处理
	 * 
	 * @return 返回false,取消当前请求;返回true,进入controller继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 允许跨域请求
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 设置UTF-8编码格式
		response.setCharacterEncoding("UTF-8");
		// 继续执行
		return true;
	}

	/**
	 * controller执行后,视图渲染之前,进行的处理
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在整个请求结束之后被调用,也就是在DispatcherServlet渲染了对应的视图之后执行,主要用于对资源清理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
