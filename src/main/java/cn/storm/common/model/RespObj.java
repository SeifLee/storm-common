package cn.storm.common.model;

import java.io.Serializable;

/**
 * 封装了浏览器响应数据
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-30 14:07
 * @version 1.0
 */
public class RespObj implements Serializable {

	/** 响应状态:true/false */
	private boolean success = false;

	/** 响应短消息 */
	private String msg = "this's a json object";

	/** 响应数据 */
	private Object result = null;

	private static final long serialVersionUID = 1L;

	// public RespObj() {
	// super();
	// }

	/**
	 * 浏览器响应对象
	 * 
	 * @param success
	 * @param msg
	 */
	public RespObj(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	/**
	 * 浏览器响应对象
	 * 
	 * @param success
	 * @param msg
	 * @param result
	 */
	public RespObj(boolean success, String msg, Object result) {
		super();
		this.success = success;
		this.msg = msg;
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "RespObj [success=" + success + ", msg=" + msg + ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RespObj other = (RespObj) obj;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (success != other.success)
			return false;
		return true;
	}

}
