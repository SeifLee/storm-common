package cn.storm.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.storm.common.model.RespObj;
import cn.storm.common.utils.JsonUtils;

/**
 * 统一异常处理
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-30 14:53
 * @version 1.0
 */
public class ExceptionResolver implements HandlerExceptionResolver {

	/** 日志对象 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		if (ex != null) {
			// 打印异常消息
			ex.printStackTrace();

			// 记录日志
			logger.error(ex.getMessage());

			// 设置状态码
			response.setStatus(HttpStatus.OK.value());
			// 设置ContentType
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			// 避免乱码
			response.setCharacterEncoding("UTF-8");
			// 设置相应头
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			try {
				String url = request.getRequestURL().toString();
				System.out.println("请求链接" + url);
				// 将错误消息转为json响应到请求端
				response.getWriter().write(JsonUtils.toJson(new RespObj(false, "服务器内部错误,请联系管理员", ex.getMessage())));
			} catch (IOException e) {
				logger.error("请求路径:" + request.getRequestURL().toString());
				logger.error("与客户端通讯异常:" + e.getMessage(), e);
			}
		}
		// 返回null,根据web.xml里的配置进行跳转404/500
		// 返回new ModelAndView(); 则将json返回到前端
		return new ModelAndView();
	}

}
