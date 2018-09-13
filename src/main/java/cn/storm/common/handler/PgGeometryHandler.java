package cn.storm.common.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.postgis.Geometry;
import org.postgis.PGgeometry;

/**
 * 定义mybatis类型处理器, 用来处理postgis的空间信息类型
 * 
 * @Copyright (c) 2012,北京吉威时代软件股份有限公司.All Rights Reserved.
 * @link http://www.geoway.com.cn
 * @author 吕晓飞
 * @date 2018-07-10 18:12
 * @version 1.0
 */
@MappedTypes({ PGgeometry.class })
@MappedJdbcTypes({ JdbcType.OTHER })
public class PgGeometryHandler implements TypeHandler<PGgeometry> {

	@Override
	public void setParameter(PreparedStatement ps, int i, PGgeometry parameter, JdbcType jdbcType) throws SQLException {
		// 使用mybatis入库的时候会调用该方法设置参数
		ps.setObject(i, parameter);
	}

	@Override
	public PGgeometry getResult(ResultSet rs, String columnName) throws SQLException {
		Object object = rs.getObject(columnName);
		if (object != null) {
			PGgeometry pg = new PGgeometry(object.toString());
			return pg;
		}
		return null;
	}

	@Override
	public PGgeometry getResult(ResultSet rs, int columnIndex) throws SQLException {
		Geometry st = (Geometry) rs.getObject(columnIndex);
		if (st != null) {
			PGgeometry pg = new PGgeometry(st);
			return pg;
		}
		return null;
	}

	@Override
	public PGgeometry getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Geometry st = (Geometry) cs.getObject(columnIndex);
		if (st != null) {
			PGgeometry pg = new PGgeometry(st);
			return pg;
		}
		return null;
	}
}
