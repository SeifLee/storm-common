package cn.storm.common.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用业务接口
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-05-30 20:18
 * @version 1.0
 */
public interface BaseService<Entity, Example> {

	/** 根据模版对象查询总记录条数 */
	long countByExample(Example example);

	/** 根据模版对象里的条件删除 */
	int deleteByExample(Example example);

	/** 根据主键删除 */
	int deleteByPrimaryKey(String id);

	/** 插入一条数据,不管字段是否为null */
	int insert(Entity entity);

	/** 插入一条数据,只插入不为null的字段,不会影响有默认值的字段 */
	int insertSelective(Entity entity);

	/** 根据模版对象里的条件查询 */
	List<Entity> selectByExample(Example example);

	/** 根据主键查询 */
	Entity selectByPrimaryKey(String id);

	/** 根据模版对象里的条件,对当前要更新的对象中不为null的字段进行更新 */
	int updateByExampleSelective(Entity entity, Example example);

	/** 根据模版对象里的条件,对当前要更新对象的全部字段更新,(不管是否为null) */
	int updateByExample(Entity entity, Example example);

	/** 根据主键,将对象中不为null的字段进行更新 */
	int updateByPrimaryKeySelective(Entity entity);

	/** 根据主键,将对象的字段全部更新(不管是否为null) */
	int updateByPrimaryKey(Entity entity);

	/** 获取分页查询数据 */
	public String getBootstrapTableData(HttpServletRequest request);

}
