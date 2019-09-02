package cn.zl.sm.user.service;

import cn.zl.jdbcutil.JdbcUtil;
import cn.zl.sm.user.dao.UserDao;
import cn.zl.sm.user.dao.factory.UserDaoFactory;
import cn.zl.sm.user.domain.User;

public class UserService {
	
	private UserDao dao=UserDaoFactory.getUserDao();
	
	public void regist(User user) throws Exception {
		String email=user.getEmail();
		User result=dao.findByEmail(email);
		if(result!=null) throw new UserException("该邮箱已被注册");
		try {
			JdbcUtil.beginTransaction();
			dao.addUser(user);
			JdbcUtil.commitTransanction();
		}catch(Exception e) {
			JdbcUtil.rollbackTransanction();
		}
	}

	public void updateState(String code) throws UserException {
		User result=dao.findByCode(code);
		if(result==null) {
			throw new UserException("该激活链接无效");
		}else if(result.isState()) {
			throw new UserException("该账号已激活请勿重复操作");
		}
		dao.updateState(code);
	}

	public User login(User form) throws UserException {
		User result=dao.findByEmail(form.getEmail());
		if(result==null) {
			throw new UserException("该用户不存在，请先注册");
		}else if(!form.getPassword().equals(result.getPassword())) {
			throw new UserException("密码错误");
		}else if(!result.isState()) {
			throw new UserException("该用户还未激活，请先到邮箱激活");
		}
		return result;
	}
	
}
