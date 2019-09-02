package cn.zl.sm.user.dao;

import cn.zl.sm.user.domain.User;

public interface UserDao {

	User findByEmail(String email);

	void addUser(User user);

	User findByCode(String code);

	void updateState(String code);
	
}
