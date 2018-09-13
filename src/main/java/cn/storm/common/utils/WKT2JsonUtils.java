package cn.storm.common.utils;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

/**
 * wkt和geojson相互转化的工具类
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-07-11 10:03
 * @version 1.0
 */
public class WKT2JsonUtils {
	
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(WKT2JsonUtils.class);
	
	/**
	 * wkt转geojson
	 * 
	 * @param wkt wkt字符串
	 * @param map 在geojson里附加的一些信息map,可以为null
	 * @return geojson字符串
	 */
	public static String wkt2Json(String wkt, Map<String, Object> map) {
		String geojson = null;
		if (map == null) {
			map = new HashMap<>();
		}
		try {
			// 创建wkt读取对象
			WKTReader reader = new WKTReader();
			// 将wkt字符串解析为geometry
			Geometry geometry = reader.read(wkt);

			// 创建geojson对象(15位精度)
			GeometryJSON g = new GeometryJSON(15);
			// 将geometry转换为geojson
			StringWriter writer = new StringWriter();
			g.write(geometry, writer);
			
			// 将geometry信息添加到map里
			map.put("geometry", writer);
			
			// 将map转为json
			geojson = JSONObject.toJSONString(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return geojson;
	}

	/**
	 * geojson转wkt字符串
	 * 
	 * @param geoJson geojson字符串
	 * @return wkt字符串
	 */
	public static String json2Wkt(String geoJson) {
		String wkt = null;
		GeometryJSON gjson = new GeometryJSON();
		Reader reader = new StringReader(geoJson);
		try {
			Geometry geometry = gjson.read(reader);
			wkt = geometry.toText();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return wkt;
	}
}
