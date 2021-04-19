package com.panzhanpeng.mybatis.excuter;

import java.util.List;

import com.panzhanpeng.mybatis.config.MapperStatement;

public interface Excuter {
	
	<E> List<E> query(MapperStatement mapperStatement, Object parameter);
	
}
