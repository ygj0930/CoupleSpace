package com.ygj.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.code.kaptcha.*;
import com.ygj.bo.UserBO;
import com.ygj.dao.ArticleDAO;
import com.ygj.error.MyError;
import com.ygj.error.MyException;
import com.ygj.service.UserService;

public class UserServlet extends HttpServlet {
	Properties properties;
	Logger logger=Logger.getLogger(getClass().getName());
	public UserServlet() {
		super();
		properties=new Properties();
		InputStream in=ArticleDAO.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(in);
		} catch (Exception e) {
			logger.error("Exception in UserServlet!", e);
			throw new MyError("加载配置文件出错!", e);
			
		}
	}


	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op=request.getParameter("op");
		request.setCharacterEncoding(properties.getProperty("Encoding"));
		response.setContentType(properties.getProperty("contentType"));
		response.setCharacterEncoding(properties.getProperty("Encoding"));
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		
		if("login".equals(op)){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String checkCode=request.getParameter("checkCode");
			String codeString=(String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			
			if(!codeString.equals(checkCode)){
				out.println("<script>alert(\"Wrong CheckCode!\");window.location='login.jsp'</script>");
			}else{			
				UserBO user=new UserBO(username,password,email);
				int uid=new UserService().dologin(user);
				if(uid>0){
				session.setAttribute("userid",uid);
				response.sendRedirect("index.jsp");
				}else{
					out.println("<script>alert(\"Login Failed,please retry!\");window.location='login.jsp'</script>");
				}				
			}	
		}
		else if("register".equals(op)){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String checkCode=request.getParameter("checkCode");
			String codeString=(String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			
			if(!codeString.equals(checkCode)){
				out.println("<script>alert(\"Wrong CheckCode!\");window.location='register.jsp'</script>");
			}else{			
				UserBO user=new UserBO(username,password,email);
				new UserService().doregister(user);
				out.println("<script>alert(\"Register Successfully!\");window.location='login.jsp'</script>");
			}	
		}
		else if("exit".equals(op)){
			session.removeAttribute("userid");
			response.sendRedirect("index.jsp");			
		}
	}

	public void init() throws ServletException {
	}

}
