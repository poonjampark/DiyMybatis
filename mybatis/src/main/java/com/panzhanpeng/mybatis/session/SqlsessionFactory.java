package com.panzhanpeng.mybatis.session;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.panzhanpeng.mybatis.config.Configuration;
import com.panzhanpeng.mybatis.config.MapperStatement;

/**
 * 在初始化的时候加载配置信息到configuration对象中
 * 生产session
 * @author panzhanpeng
 *
 */
public class SqlsessionFactory {

	private final Configuration config = new Configuration();
	private static final String MAPPER_CONFIG_LOCATION = "mappers";
	private static final String DB_CONFIG_FILE = "/db.properties";
	
	public SqlsessionFactory() {
		loadDbInfo();
		loadMappersInfo();
	}
	
	/**
	 * 加载数据库信息
	 */
	private void loadDbInfo() {
		InputStream dbInfo = SqlsessionFactory.class.getResourceAsStream(DB_CONFIG_FILE);
		Properties p = new Properties();
		try {
			p.load(dbInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.setJdbcDriver(p.get("driver").toString());
		config.setJdbcName(p.get("name").toString());
		config.setJdbcUrl(p.get("url").toString());
		config.setJdbcPassword(p.get("password").toString());
	}
	
	/**
	 * 加载指定文件夹下所有的xml文件
	 */
	private void loadMappersInfo() {
		URL resource = null;
		resource = SqlsessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
		File mappers = new File(resource.getFile());
		if (mappers.isDirectory()) {
			File[] files = mappers.listFiles();
			for (File file : files) {
				loadMappersInfo(file);
			}
		}
	}
	
	/**
	 * 加载指定的mapper.xml文件
	 */
	private void loadMappersInfo(File file) {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		String nameSpace = root.attribute("namespace").getData().toString();
		List<Element> selects = root.elements("select");
		for (Element element : selects) {
			MapperStatement mapperStatement = new MapperStatement();
			String id = element.attribute("id").getData().toString();
			String relustType = element.attribute("resultType").getData().toString();
			String sql = element.getData().toString();
			String sourceId = nameSpace + "." + id;
			mapperStatement.setNamespace(nameSpace);
			mapperStatement.setResultType(relustType);
			mapperStatement.setSql(sql);
			mapperStatement.setSourceId(sourceId);
			config.getMapperStatements().put(sourceId, mapperStatement);
		}
	}
	
	public SqlSession openSession() {
		return new DefaultSqlSession(config);
	}
	
}
