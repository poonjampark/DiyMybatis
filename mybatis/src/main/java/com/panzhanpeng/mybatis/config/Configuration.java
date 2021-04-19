package com.panzhanpeng.mybatis.config;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcName;
	private String jdbcPassword;
	
	private Map<String, MapperStatement> mapperStatements = new HashMap<String, MapperStatement>();

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcName() {
		return jdbcName;
	}

	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	public Map<String, MapperStatement> getMapperStatements() {
		return mapperStatements;
	}

	public void setMapperStatements(Map<String, MapperStatement> mapperStatements) {
		this.mapperStatements = mapperStatements;
	}
	
}
