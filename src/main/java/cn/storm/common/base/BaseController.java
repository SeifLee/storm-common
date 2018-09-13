package cn.storm.common.base;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.storm.common.model.RespObj;
import cn.storm.common.utils.JsonUtils;

/**
 * controller基类
 * 
 * @param <Service>
 *            业务接口泛型
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-31 10:51
 * @version 1.0
 */
public abstract class BaseController {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** request对象,线程安全 */
	@Resource
	protected HttpServletRequest request;

	/**
	 * 
	 * render:向客户端输出文本.
	 *
	 * @param text
	 *            输出的文本
	 * @param contentType
	 *            响应的类型
	 * @param response
	 *            响应对象
	 */
	private void render(String text, String contentType, HttpServletResponse response) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * renderText:直接输出字符串.
	 *
	 * @param text
	 *            输出的文本
	 * @param response
	 *            响应对象
	 */
	protected void renderText(String text, HttpServletResponse response) {
		render(text, "text/plain;charset=UTF-8", response);
	}

	/**
	 * 
	 * renderHtml:直接输出HTML.
	 *
	 * @param html
	 *            输出的HTML
	 * @param response
	 *            响应对象
	 */
	protected void renderHtml(String html, HttpServletResponse response) {
		render(html, "text/html;charset=UTF-8", response);
	}

	/**
	 * 
	 * renderXML:直接输出XML.
	 *
	 * @param xml
	 *            输出的XML
	 * @param response
	 *            响应对象
	 */
	protected void renderXML(String xml, HttpServletResponse response) {
		render(xml, "text/xml;charset=UTF-8", response);
	}
	
	/**
	 * 
	 * renderJson:直接输出Json.
	 *
	 * @param json
	 *            输出的json
	 * @param response
	 *            响应对象
	 */
	protected void renderJson(String json, HttpServletResponse response) {
		render(json, "application/json;charset=UTF-8", response);
	}

	/**
	 * renderObject: 返回一个对象.
	 * 
	 * 操作结果
	 * 
	 * @param msg
	 *            返回信息
	 * @param obj
	 *            对象
	 * 
	 * @return 返回json反馈对象
	 */
	protected RespObj renderRespObj(boolean success, String msg, Object obj) {
		return new RespObj(success, msg, obj);
	}

	/**
	 * 输出响应信息
	 * 
	 * @param success
	 *            响应状态 true/fasle
	 * @param msg
	 *            响应消息
	 * @return
	 */
	protected RespObj renderRespObj(boolean success, String msg) {
		return new RespObj(success, msg);
	}

	/**
	 * 将对象转为json字符串后响应到请求端
	 * 
	 * @param obj
	 *            对象
	 * @param response
	 *            浏览器响应对象
	 */
	protected void renderObj(Object obj, HttpServletResponse response) {
		render(JsonUtils.toJson(obj), "application/json", response);
	}

	public RespObj insert() {
		return null;
	};

	public RespObj delete() {
		return null;
	};

	public RespObj update() {
		return null;
	};

	public RespObj getAll() {
		return null;
	};
	
	public String getBootstrapTableData() {
		return null;
	};
}