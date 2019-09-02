package cn.zl.sm.user.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.zl.mailUtil.Mail;
import cn.zl.mailUtil.MailUtil;
import cn.zl.sm.user.domain.User;
import cn.zl.sm.user.service.UserService;
import util.uuid.UUIDTool;

public class UserServlet extends HttpServlet {
	
	private UserService service=new UserService();
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String methodName=request.getParameter("method");
		if(methodName==null && methodName.trim().isEmpty())throw new RuntimeException("您没有传递方法参数");
		Method method=null;
		try {
			method=this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
		}catch(Exception e) {
			throw new RuntimeException("您没有定义该方法");
		}
		try {
			method.invoke(this,request, response);
		}catch(Exception e) {
			System.out.println("您调用的"+methodName+"方法内部抛出了异常");
			throw new RuntimeException(e);
		}
	}
	
	public void regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.得到表单数据封装到bean对象中
		User form=new User();
		BeanUtils.populate(form, request.getParameterMap());
		form.setUid(UUIDTool.getUUID());
		form.setCode(UUIDTool.getUUID()+UUIDTool.getUUID());
		form.setState(false);
		//2.进行输入校验
		Map<String,String> errors=new HashMap<String,String>();
		
		String username=form.getUsername();
		if(username==null||username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空");
		}else if(username.length()<3||username.length()>20){
			errors.put("username", "用户名长度必须在3到20之间");
		}
		
		int age=form.getAge();
		if(age<1||age>100) {
			errors.put("age", "年龄在1到100之间");
		}
		
		String psw=form.getPassword();
		if(psw==null||psw.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		}else if(psw.length()<3||psw.length()>20) {
			errors.put("password", "密码长度需要在3到20之间");
		}
		
		String phone=form.getPhone();
		if(phone==null||phone.trim().isEmpty()) {
			errors.put("phone", "手机号不能为空");
		}else if(phone.length()!=11) {
			errors.put("phone", "格式错误，手机号必须为11位数");
		}
		
		String email=form.getEmail();
		if(email==null||email.trim().isEmpty()) {
			errors.put("email", "邮箱不能为空");
		}else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "邮箱格式错误，请输入正确的邮箱");
		}
		
		String vcode=form.getVerifyCode();
		String session_vcode=(String) request.getSession().getAttribute("session_vcode");
		if(vcode==null||vcode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空");
		}else if(!vcode.equalsIgnoreCase(session_vcode)) {
			errors.put("verifyCode", "验证码错误");
		}
		
		if(errors.size()>0&&errors!=null) {
			request.setAttribute("errors",errors);
			request.setAttribute("form",form);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return;
		}
		//3.进行逻辑校验
		try {
			service.regist(form);
		}catch(Exception e) {
			request.setAttribute("form",form);
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return;
		}
		Properties props=new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("mail.properties"));
		String host=props.getProperty("host");
		String uname=props.getProperty("uname");
		String pwd=props.getProperty("pwd");
		String subject=props.getProperty("subject");
		String content=props.getProperty("content");
		String from=props.getProperty("from");
		String to=form.getEmail();
		content=MessageFormat.format(content, form.getCode());
		Session session=MailUtil.getSession(host, uname, pwd);
		Mail mail=new Mail(from,to,subject,content);
		MailUtil.sendMail(session, mail);
		
		request.setAttribute("msg","注册成功，请到邮箱激活");
		request.getRequestDispatcher("/user/msg.jsp").forward(request, response);
	}
	
	public void active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code=request.getParameter("code");
		try {
			service.updateState(code);
			request.setAttribute("msg","激活成功");
			request.getRequestDispatcher("/user/msg.jsp").forward(request, response);
			return;
		}catch(Exception e) {
			request.setAttribute("msg",e.getMessage());
			request.getRequestDispatcher("/user/msg.jsp").forward(request, response);
			return;
		}
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User form = new User();
		BeanUtils.populate(form, request.getParameterMap());
		
		//1.输入校验
		Map<String,String> errors=new HashMap<String,String>();
		
		String email=form.getEmail();
		if(email==null||email.trim().isEmpty()) {
			errors.put("email","邮箱不能为空");
		}else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "邮箱格式错误");
		}
		
		String password=form.getPassword();
		if(password==null||password.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		}else if(password.length()<3||password.length()>20) {
			errors.put("password","密码长度必须在3到20之间");
		}
		
		String vcode=form.getVerifyCode();
		String s_vcode=(String) request.getSession().getAttribute("session_vcode");
		if(vcode==null||vcode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空");
		}else if(!vcode.equalsIgnoreCase(s_vcode)) {
			errors.put("verifyCode", "验证码错误");
		}
		
		if(errors.size()>0 && errors!=null) {
			request.setAttribute("errors",errors);
			request.setAttribute("form",form);
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return;
		}
		
		try {
			User result=service.login(form);
			request.getSession().setAttribute("session_user", result);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}catch(Exception e) {
			request.setAttribute("msg",e.getMessage());
			request.setAttribute("form",form);
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return;
		}
	}
}
