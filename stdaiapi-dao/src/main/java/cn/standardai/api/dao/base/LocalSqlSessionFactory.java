package cn.standardai.api.dao.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.standardai.api.core.bean.PropertyConfig.Mysql;
import cn.standardai.api.dao.DataDao;
import cn.standardai.api.dao.DatasetDao;
import cn.standardai.api.dao.ModelDao;
import cn.standardai.api.dao.ModelTemplateDao;
import cn.standardai.api.dao.TokenDao;
import cn.standardai.api.dao.UserDao;
import cn.standardai.api.dao.exception.DaoException;

public class LocalSqlSessionFactory {

	private static SqlSessionFactory ssf4Mysql;

	public static void init(Mysql mysql) throws DaoException {

		if (ssf4Mysql != null) return;

		String resource = "cn/standardai/api/dao/sql/map/mybatis.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new DaoException("mysql配置文件不存在");
		}

		Properties properties = new Properties();
		properties.setProperty("driver", mysql.getDriver());
		properties.setProperty("url", mysql.getUrl());
		properties.setProperty("name", mysql.getName());
		properties.setProperty("password", mysql.getPassword());

		ssf4Mysql = new SqlSessionFactoryBuilder().build(inputStream, properties);
		ssf4Mysql.getConfiguration().addMapper(DataDao.class);
		ssf4Mysql.getConfiguration().addMapper(DatasetDao.class);
		ssf4Mysql.getConfiguration().addMapper(TokenDao.class);
		ssf4Mysql.getConfiguration().addMapper(UserDao.class);
		ssf4Mysql.getConfiguration().addMapper(ModelDao.class);
		ssf4Mysql.getConfiguration().addMapper(ModelTemplateDao.class);
	}

	public static SqlSession getMySQLInstance() {
		return ssf4Mysql.openSession();
	}

	public static SqlSession getMySQLInstance(boolean autoCommit) {
		return ssf4Mysql.openSession(autoCommit);
	}
}
