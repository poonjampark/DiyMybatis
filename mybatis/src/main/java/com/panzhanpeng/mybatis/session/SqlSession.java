package com.panzhanpeng.mybatis.session;

import java.util.List;

public interface SqlSession {

	<T> T selectOne(String statement, Object parameter) throws Exception;
	
	<E> List<E> selectList(String statement, Object parameter);
	
	<T> T getMapper(Class<T> type);
	
}
