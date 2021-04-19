package com.panzhanpeng.mybatis.excuter;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.panzhanpeng.mybatis.config.Configuration;
import com.panzhanpeng.mybatis.config.MapperStatement;
import com.panzhanpeng.mybatis.object.Tuser;

public class DefaultExcuter implements Excuter{
	
	private final Configuration configuration;
	
	public DefaultExcuter(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public <E> List<E> query(MapperStatement mapperStatement, Object parameter) {
		System.out.println(mapperStatement.getSql());
		return getResult(mapperStatement.getSql(), parameter);
	}
	
	private <E> List<E> getResult(String sql, Object parameter) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<E> list = new ArrayList<E>();
		try {
			Class.forName(configuration.getJdbcDriver());
			connection = (Connection) DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcName(), configuration.getJdbcPassword());
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			dealParam(preparedStatement, sql, parameter);
			result = preparedStatement.executeQuery();
			list = dealResult(result);
			connection.close();
			preparedStatement.close();
			result.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		return list;
	}
	
	/**
	 * 处理参数
	 * @param sql
	 * @param param
	 */
	private void dealParam(PreparedStatement preparedStatement, String sql, Object param) {
		try {
			if (sql.contains("?")) {
				String value = String.valueOf(param);
				preparedStatement.setString(1, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理结果
	 * @param <E>
	 * @return
	 */
	private <E> List<E> dealResult(ResultSet rs) {
		List<Tuser> list = new ArrayList();
		 try {
			while (rs.next()) {
				Tuser user = new Tuser();
				user.setId(rs.getString(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (List<E>) list;
	}

}
