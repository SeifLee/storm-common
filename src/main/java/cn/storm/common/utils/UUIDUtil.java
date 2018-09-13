package cn.storm.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author lvxiaofei
 * @version 1.0 , 2017-04-12 17:15:30
 */
public class UUIDUtil {

	/**
	 * 获取32位的UUID
	 * 
	 * @return 返回值: 32位UUID
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	/**
	 * 产生4位随机数(0000-9999)
	 * 
	 * @return 4位随机数
	 */
	public static String getFourRandom() {
		Random random = new Random();
		String fourRandom = random.nextInt(10000) + "";
		int randLength = fourRandom.length();
		if (randLength < 4) {
			for (int i = 1; i <= 4 - randLength; i++)
				fourRandom = "0" + fourRandom;
		}
		return fourRandom;
	}
}
