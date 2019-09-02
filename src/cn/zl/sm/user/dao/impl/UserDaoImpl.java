package cn.zl.sm.user.dao.impl;

import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import cn.zl.jdbcutil.TxQueryRunner;
import cn.zl.sm.user.dao.UserDao;
import cn.zl.sm.user.domain.User;

public class UserDaoImpl implements UserDao {
	
	private QueryRunner qr=new TxQueryRunner();
	
	@Override
	public User findByEmail(String email) {
		String sql="select * from user where email=?";
		try {
			User result=qr.query(sql, new BeanHandler<User>(User.class),email);
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addUser(User user) {
		String sql="insert into `user` values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params= {user.getUid(),user.getUsername(),user.getPassword(),user.getAge(),user.getGender(),user.getBirthday(),user.getPhone(),user.getEmail(),user.getCode(),user.isState()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findByCode(String code) {
		String sql="select * from `user` where code=?";
		try {
			User user=qr.query(sql, new BeanHandler<User>(User.class),code);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateState(String code) {
		String sql="update `user` set state=true where code=?";
		try {
			qr.update(sql,code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
