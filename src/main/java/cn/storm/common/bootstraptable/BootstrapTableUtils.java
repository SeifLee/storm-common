package cn.storm.common.bootstraptable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.storm.common.model.TableClass;
import cn.storm.common.utils.MybatisUtils;
import cn.storm.common.utils.SpringContextHolder;

/**
 * BootstrapTable工具类
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-30 21:12
 * @version 1.0
 */
@SuppressWarnings("resource")
public class BootstrapTableUtils {

	/**
	 * 日志对象
	 */
	public static Logger logger = LoggerFactory.getLogger(BootstrapTableUtils.class);

	/**
	 * 初始化查询条件
	 * 
	 * @param request
	 *            浏览器请求对象
	 * @return 封装了查询参数的对象
	 */
	public static BootstrapTableModel initQueryParam(HttpServletRequest request) {
		// 获取所有参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		BootstrapTableModel bootstrapTableModel = new BootstrapTableModel();
		// 非空校验
		if (parameterMap == null) {
			return bootstrapTableModel;
		}
		// 遍历参数
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			if (value == null || value.length == 0) {
				continue;
			}
			String value0 = value[0];
			if (StringUtils.isBlank(value0)) {
				continue;
			}
			// 参数过滤
			if ("limit".equals(key)) {
				bootstrapTableModel.setLimit(Long.valueOf(value0));
			} else if ("offset".equals(key)) {
				bootstrapTableModel.setOffset(Long.valueOf(value0));
			} else if ("sort".equals(key)) {
				bootstrapTableModel.setSort(value0);
			} else if ("sortOrder".equals(key)) {
				bootstrapTableModel.setSortOrder(value0);
			} else if ("search".equals(key)) {
				bootstrapTableModel.setSearch(value0);
			}
		}
		return bootstrapTableModel;
	}

	/**
	 * 封装分页信息到查询对象里
	 * 
	 * @param request
	 *            请求对象
	 * @param tableClass
	 *            封装了class的对象
	 * @return 封装了查询条件的example对象
	 */
	public static Object packagePageInfo(BootstrapTableModel bootstrapTableModel, TableClass tableClass) {

		// 模糊搜索字段
		String search = bootstrapTableModel.getSearch();
		// 不搜索的字段
		List<String> searchExclude = bootstrapTableModel.getSearchExclude();

		// 排序字段
		String sort = bootstrapTableModel.getSort();
		// 排序方式
		String sortOrder = bootstrapTableModel.getSortOrder();

		// 不排序的字段
		List<String> sortExclude = bootstrapTableModel.getSortExclude();

		// 获取字节码对象
		// 实体类
		Class<?> entityClass = tableClass.getEntityClass();
		// 查询对象
		Class<?> exampleClass = tableClass.getExampleClass();
		// dao
		// Class<?> daoClass = tableClass.getDaoClass();

		// 创建查询实例
		Object example = null;
		try {
			example = exampleClass.newInstance();

			// 封装排序语句
			if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(sortOrder)) {
				// 排序字段过滤
				if (!sortExclude.contains(sort)) {
					// 查询java实体类中的字段在数据库表中对应的column名称
					String orderField = MybatisUtils.getFieldMapper(sort, entityClass);
					// 使用内省封装参数
					BeanUtils.setProperty(example, "orderByClause", orderField + " " + sortOrder);
				}
			}

			// 封装模糊搜索语句
			if (StringUtils.isNotBlank(search)) {
				// 执行example中的createCriteria方法, 创建criteria条件查询对象
				Object criteria = MethodUtils.invokeMethod(example, "createCriteria", null);
				// 获取criteria的class
				Class<? extends Object> criteriaClass = criteria.getClass();
				// 反射获取criteria中的方法
				Method[] methods = criteriaClass.getMethods();
				// 获取所有的"and...Like"方法
				ArrayList<String> andLikeMethodNames = new ArrayList<>();
				// 遍历所有的方法
				for (Method method : methods) {
					// 方法名
					String methodName = method.getName();
					// 筛选and...Like方法
					if (methodName.startsWith("and") && methodName.endsWith("Like")
							&& !methodName.endsWith("NotLike")) {
						// 这里是要对模糊搜索字段进行屏蔽的地方
						if (!searchExclude.contains(methodName)) {
							andLikeMethodNames.add(methodName);
						}
					}
				}
				// 添加搜索条件
				for (int i = 0; i < andLikeMethodNames.size(); i++) {
					// 方法名
					String andLikeMethodName = andLikeMethodNames.get(i);
					// 执行example中的or方法, 获取criteria条件查询对象
					Object criteriaTmpi = MethodUtils.invokeMethod(example, "or", null);
					// 添加and..Like语句
					MethodUtils.invokeMethod(criteriaTmpi, andLikeMethodName, "%" + search + "%");
				}
			}
			return example;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 开始分页查询
	 * 
	 * @param bootstrapTableModel
	 *            封装了分页信息的对象
	 * @param tableClass
	 *            单表的借个相关对象class
	 * @param example
	 *            查询对象
	 * @return 返回bootstrapTable插件所需的数据
	 */
	public static HashMap<String, Object> query(BootstrapTableModel bootstrapTableModel, TableClass tableClass, Object example) {
		// 创建map
		HashMap<String, Object> hm = new HashMap<>();
		try {
			// 每页大小
			Long limit = bootstrapTableModel.getLimit();
			// 其实页码
			Long offset = bootstrapTableModel.getOffset();
			// 获取dao字节码对象
			Class<?> daoClass = tableClass.getDaoClass();
			Class<?> exampleClass = tableClass.getExampleClass();
			if (example==null) {
				example = exampleClass.newInstance();
			}
			// 获取dao代理实例
			Object daoProxy = SpringContextHolder.getBean(daoClass);
			// 封装分页信息
			PageHelper.offsetPage(offset.intValue(), limit.intValue());
			// 分页查询
			ArrayList<?> rows = (ArrayList<?>) MethodUtils.invokeMethod(daoProxy, "selectByExample", example);
			// 强转为Page对象
			Page<? extends Object> page = (Page<? extends Object>) rows;
			// 查询总记录数
			long total = page.getTotal();
			// 初始化map
			hm.put("total", total);
			hm.put("rows", rows);
			return hm;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {
			// 清理资源
			PageHelper.clearPage();
		}

		// 重新初始化数据
		hm.put("total", 0);
		hm.put("rows", new ArrayList<>());
		return hm;
	}
	
}
