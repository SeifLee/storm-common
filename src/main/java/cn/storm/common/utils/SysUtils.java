package cn.storm.common.utils;

import java.io.File;
import java.util.Properties;

import org.springframework.boot.ApplicationHome;

/**
 * 系统工具类
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-07-13 09:51
 * @version 1.0
 */
public class SysUtils {

	/**
	 * 获取当前jar包所在系统中的目录
	 */
	public static String getBaseJarPath() {
		ApplicationHome home = new ApplicationHome(SysUtils.class);
		File jarFile = home.getSource();
		return jarFile.getParentFile().toString();
	}
	
	/**
	 * 判断系统是否是windows
	 */
	public static boolean isWin() {
		// 获取系统名称
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os.startsWith("win") || os.startsWith("Win")) {
			return true;
		}
		return false;
	}
}
