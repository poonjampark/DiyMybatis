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
 * �ڳ�ʼ����ʱ�����������Ϣ��configuration������
 * ����session
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
	 * �������ݿ���Ϣ
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
	 * ����ָ���ļ��������е�xml�ļ�
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
	 * ����ָ����mapper.xml�ļ�
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
