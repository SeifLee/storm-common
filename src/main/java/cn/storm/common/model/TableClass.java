package cn.storm.common.model;

/**
 * 数据库表对应的实体类,查询对象,dao接口的字节码对象
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-31 14:55
 * @version 1.0
 */
public class TableClass {

	/** 实体类字节码对象 */
	private Class<?> entityClass;

	/** 查询类字节码对象 */
	private Class<?> exampleClass;

	/** dao字节码对象 */
	private Class<?> daoClass;

	public TableClass() {
		super();
	}

	public TableClass(Class<?> entityClass, Class<?> exampleClass, Class<?> daoClass) {
		super();
		this.entityClass = entityClass;
		this.exampleClass = exampleClass;
		this.daoClass = daoClass;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<?> getExampleClass() {
		return exampleClass;
	}

	public void setExampleClass(Class<?> exampleClass) {
		this.exampleClass = exampleClass;
	}

	public Class<?> getDaoClass() {
		return daoClass;
	}

	public void setDaoClass(Class<?> daoClass) {
		this.daoClass = daoClass;
	}

}
