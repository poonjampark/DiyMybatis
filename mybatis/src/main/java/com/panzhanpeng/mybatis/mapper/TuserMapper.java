package com.panzhanpeng.mybatis.mapper;

import java.util.List;

import com.panzhanpeng.mybatis.object.Tuser;

public interface TuserMapper {

	Tuser selectByPrimaryKey(String id);
	
	List<Tuser> selectAll();
}
