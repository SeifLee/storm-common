package cn.storm.common.utils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;

/**
 * json工具类
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-29 11:34
 * @version 1.0
 */
public class JsonUtils {

	/** 采取单例模式 */
	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * 私有构造,禁止new
	 */
	private JsonUtils() {
	}

	/**
	 * 将对象转为JSON串，此方法能够满足大部分需求
	 * 
	 * @param src
	 *            将要被转化的对象
	 * @return 转化后的JSON串
	 */
	public static String toJson(Object src) {
		if (src == null) {
			return gson.toJson(JsonNull.INSTANCE);
		}
		return gson.toJson(src);
	}
	
	/**
	 * 将对象转为JSON串，此方法能够满足大部分需求
	 * 
	 * @param src
	 *            将要被转化的对象
	 * @return 转化后的JSON串
	 */
	public static String toJsonExclude(Object src,String[] exclude) {
		
		if (src == null) {
			return gson.toJson(JsonNull.INSTANCE);
		}
		
		Gson _gson = null;
		if (exclude != null) {
			// 排除不想序列化为json的字段
			final List<String> asList = Arrays.asList(exclude);
			_gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
				@Override
				public boolean shouldSkipField(FieldAttributes arg0) {
					if (asList.contains(arg0.getName())) {
						return true;
					}
					return false;
				}

				@Override
				public boolean shouldSkipClass(Class<?> arg0) {
					return false;
				}
			}).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		} else {
			_gson = new Gson();
		}
		
		if (_gson!=null) {
			return _gson.toJson(src);
		}else {
			// 调用toJson方法
			return toJson(src);
		}		
	}
	
	/**
	 * 将对象转为JSON串，此方法能够满足大部分需求
	 * 
	 * @param src
	 *            将要被转化的对象
	 * @return 转化后的JSON串
	 */
	public static String toJsonInclude(Object src,String[] include) {
		
		if (src == null) {
			return gson.toJson(JsonNull.INSTANCE);
		}
		
		Gson _gson = null;
		if (include != null) {
			// 排除不想序列化为json的字段
			final List<String> asList = Arrays.asList(include);
			_gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
				@Override
				public boolean shouldSkipField(FieldAttributes arg0) {
					if (!asList.contains(arg0.getName())) {
						return true;
					}
					return false;
				}

				@Override
				public boolean shouldSkipClass(Class<?> arg0) {
					return false;
				}
			}).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		} else {
			_gson = new Gson();
		}
		
		if (_gson!=null) {
			return _gson.toJson(src);
		}else {
			// 调用toJson方法
			return toJson(src);
		}		
	}

	/**
	 * 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
	 * 
	 * @param json
	 *            JSON字符串
	 * @param classOfT
	 *            目标对象的class
	 * @return 目标对象
	 */
	public static <T> Object fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, (Type) classOfT);
	}

	/**
	 * 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为 new
	 * TypeToken<List<T>>(){}.getType()，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
	 * 
	 * @param json
	 *            JSON字符串
	 * @param typeOfT
	 *            泛型的具体class
	 * @return
	 */
	public static Object fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}
}
