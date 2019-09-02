package cn.zl.sm.user.dao.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.zl.sm.user.dao.UserDao;

public class UserDaoFactory {
	private static Properties props;
	static {
		InputStream in=UserDaoFactory.class.getClassLoader().getResourceAsStream("dao.properties");
		props=new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static UserDao getUserDao() {
		try {
			Class clazz=Class.forName(props.getProperty("cn.zl.sm.user.dao.UserDao"));
			return (UserDao) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
