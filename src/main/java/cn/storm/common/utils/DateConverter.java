package cn.storm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * springmvc日期转换类
 * 
 * @author lvxiaofei
 * @version 1.0，2017-06-16 11:32:11
 */
public class DateConverter implements Converter<String, Date> {
	
	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Date convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (ParseException e) {
			logger.debug("日期格式转换错误!");
		}
		return null;
	}

}
