package cn.storm.common.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.storm.common.bootstraptable.BootstrapTableModel;
import cn.storm.common.bootstraptable.BootstrapTableUtils;
import cn.storm.common.model.TableClass;
import cn.storm.common.utils.GenericsUtils;
import cn.storm.common.utils.JsonUtils;
import cn.storm.common.utils.SpringContextHolder;

/**
 * 抽象业务类
 * 
 * @param <Mapper>
 *            数据操作层接口
 * @param <Entity>
 *            单表实体类
 * @param <Example>
 *            单表实体类查询类
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-30 20:40
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<Mapper, Entity, Example> implements BaseService<Entity, Example> {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 单表泛型对应的数据操作接口的代理对象 ,protected修饰的字段只能被子类访问 */
	protected Mapper mapper;

	/** 实体类查询类字节码对象 */
	protected Class<? extends Object> entityClass;

	/** 实体类查询类字节码对象 */
	protected Class<? extends Object> exampleClass;

	/** dao字节码对象 */
	protected Class<? extends Object> daoClass;

	/**
	 * 无参构造: 抽象类有构造方法,但是不能实例化; 抽象类子类在实例化时会先调用父类的构造方法
	 */
	protected BaseServiceImpl() {
		// 获取父类类型(也就是抽象类的类型), 这里的this.getClass()获取的是抽象类子类的字节码对象
		Class<?> childClass = this.getClass();

		ParameterizedType genericSuperclass = (ParameterizedType) childClass.getGenericSuperclass();

		// 获取父类声明上的泛型数组(也就是<Mapper, Record, Example>)
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();

		// 获得第一个泛型(Mapper)的实体类型--这里的实体类型是指调用了BaseServiceImpl构造方法的子类上定义的泛型的类型
		Class<Mapper> clazz = (Class<Mapper>) actualTypeArguments[0];

		// 初始化mapper
		mapper = SpringContextHolder.getBean(clazz);

		// 初始化daoclass
		daoClass = clazz;

		// 初始化实体类Class
		entityClass = GenericsUtils.getSuperClassGenricType(childClass, 1);

		// 初始化查询对象Class
		exampleClass = GenericsUtils.getSuperClassGenricType(childClass, 2);
	}

	/**
	 * 根据模版对象查询总记录条数
	 */
	public long countByExample(Example example) {
		long result = 0;
		try {
			if (example == null) {
				example = (Example) exampleClass.newInstance();
			}
			result = (long) MethodUtils.invokeMethod(mapper, "countByExample", example);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据模版对象里的条件删除
	 */
	public int deleteByExample(Example example) {
		int result = 0;
		try {
			if (example == null) {
				example = (Example) exampleClass.newInstance();
			}
			result = (int) MethodUtils.invokeMethod(mapper, "deleteByExample", example);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据主键删除
	 */
	public int deleteByPrimaryKey(String id) {
		int result = 0;
		try {
			if (StringUtils.isBlank(id)) {
				return result;
			}
			result = (int) MethodUtils.invokeMethod(mapper, "deleteByPrimaryKey", id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入一条数据,不管字段是否为null
	 */
	public int insert(Entity entity) {
		int result = 0;
		try {
			result = (int) MethodUtils.invokeMethod(mapper, "insert", entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
	 */
	public int insertSelective(Entity entity) {
		int result = 0;
		try {
			result = (int) MethodUtils.invokeMethod(mapper, "insertSelective", entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据模版对象里的条件查询
	 */
	public List<Entity> selectByExample(Example example) {
		List<Entity> result = null;
		try {
			if (example == null) {
				example = (Example) exampleClass.newInstance();
			}
			result = (List<Entity>) MethodUtils.invokeMethod(mapper, "selectByExample", example);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据主键查询
	 */
	public Entity selectByPrimaryKey(String id) {
		Entity result = null;
		try {
			if (StringUtils.isBlank(id)) {
				return result;
			}
			result = (Entity) MethodUtils.invokeMethod(mapper, "selectByPrimaryKey", id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据模版对象里的条件,对当前要更新的对象中不为null的字段进行更新
	 */
	public int updateByExampleSelective(Entity entity, Example example) {
		int result = 0;
		try {
			if (example == null) {
				example = (Example) exampleClass.newInstance();
			}
			result = (int) MethodUtils.invokeMethod(entity, "updateByExampleSelective",
					new Object[] { entity, example });
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据模版对象里的条件,对当前要更新对象的全部字段更新,(不管是否为null)
	 */
	public int updateByExample(Entity entity, Example example) {
		int result = 0;
		try {
			if (example == null) {
				example = (Example) exampleClass.newInstance();
			}
			result = (int) MethodUtils.invokeMethod(entity, "updateByExample", new Object[] { entity, example });
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据主键,将对象中不为null的字段进行更新
	 */
	public int updateByPrimaryKeySelective(Entity entity) {
		int result = 0;
		try {
			result = (int) MethodUtils.invokeMethod(mapper, "updateByPrimaryKeySelective", entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据主键,将对象的字段全部更新(不管是否为null)
	 */
	public int updateByPrimaryKey(Entity entity) {
		int result = 0;
		try {
			result = (int) MethodUtils.invokeMethod(mapper, "updateByPrimaryKey", entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取分页查询数据
	 */
	public String getBootstrapTableData(HttpServletRequest request) {

		// 获取分页信息
		BootstrapTableModel bootstrapTableModel = BootstrapTableUtils.initQueryParam(request);

		// 封装数据库单表对应的class
		TableClass tableClass = new TableClass(entityClass, exampleClass, daoClass);

		// 封装查询条件--排序/模糊搜索
		Object example = BootstrapTableUtils.packagePageInfo(bootstrapTableModel, tableClass);

		// 封装业务查询条件需要自己手动添加...在子类里重写本方法

		// 分页查询
		HashMap<String,Object> query = BootstrapTableUtils.query(bootstrapTableModel, tableClass, example);
		String pageData = JsonUtils.toJson(query);
		return pageData;
	}

}
