package com.panzhanpeng.mybatis.session;

import java.lang.reflect.Proxy;
import java.util.List;

import com.panzhanpeng.mybatis.blinding.MapperProxy;
import com.panzhanpeng.mybatis.config.Configuration;
import com.panzhanpeng.mybatis.config.MapperStatement;
import com.panzhanpeng.mybatis.excuter.DefaultExcuter;
import com.panzhanpeng.mybatis.excuter.Excuter;

public class DefaultSqlSession implements SqlSession {
	
	private final Configuration configuration;
	private Excuter excuter;
	
	public DefaultSqlSession(Configuration configuration) {
		super();
		this.configuration = configuration;
		excuter = new DefaultExcuter(configuration);
	}

	public <T> T selectOne(String statement, Object parameter) throws Exception {
		List<T> list = selectList(statement, parameter);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new Exception("too many results!");
		}
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		MapperStatement ms = configuration.getMapperStatements().get(statement);
		return excuter.query(ms, parameter);
	}

	public <T> T getMapper(Class<T> type) {
		MapperProxy mp = new MapperProxy(this);
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mp);
	}

}
