package cn.zl.sm.upload.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MusicDaoFactory {
	
	private static Properties props;
	
	static {
		InputStream in=MusicDaoFactory.class.getClassLoader().getResourceAsStream("dao.properties");
		props=new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static MusicDao getMusicDao() {
		try {
			Class clazz=Class.forName(props.getProperty("cn.zl.sm.upload.dao.MusicDao"));
			return (MusicDao) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
