package cn.storm.common.utils;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisUtils {

	private static Logger logger = LoggerFactory.getLogger(MybatisUtils.class);

	/**
	 * 查询实体类字段在数据库中映射的列名
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param modelClass
	 *            字段所在实体类的class
	 * @return 实体类字段在数据哭中映射的列名
	 */
	public static String getFieldMapper(String fieldName, Class<?> modelClass) {
		// 查询mybatis的mapper中的映射
		SqlSessionFactory sqlSessionFactory = SpringContextHolder.getBean(SqlSessionFactory.class);
		// 获取mybatis的配置
		Configuration configuration = sqlSessionFactory.getConfiguration();
		// 获取mybatis框架中的所有映射关系
		Collection<ResultMap> resultMaps = configuration.getResultMaps();
		// 定义目标映射对象map
		ResultMap srcResultMap = null;
		String modelClassName = modelClass.getName();
		String resultMapClassName = ResultMap.class.getName();
		// 遍历
		for (Object obj : resultMaps) {
			String objClassName = obj.getClass().getName();
			if (!resultMapClassName.equals(objClassName)) {
				continue;
			}
			ResultMap resultMap = (ResultMap) obj;
			// 获取resultMap对应的实体类class
			String clazz = resultMap.getType().getName();
			if (clazz.equals(modelClassName)) {
				srcResultMap = resultMap;
				break;
			}
		}
		// 非空校验
		if (srcResultMap != null) {
			// 获取映射关系
			List<ResultMapping> resultMappings = srcResultMap.getResultMappings();
			// 遍历
			for (ResultMapping resultMapping : resultMappings) {
				// 实体类中的名称
				String property = resultMapping.getProperty();
				// 数据库中的名称
				String column = resultMapping.getColumn();
				if (property.equals(fieldName)) {
					return column;
				}
			}
		} else {
			logger.info("resultmap获取失败");
		}

		return fieldName;
	}
}
