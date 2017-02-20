package com.ygj.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ygj.service.UserService;
import com.ygj.vo.User;

public class UserServlet extends HttpServlet {


	public UserServlet() {
		super();
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		
		if("login".equals(op)){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String checkCode=request.getParameter("checkCode");
			String codeString=(String) session.getAttribute("randomCode");
			
			if(!codeString.equals(checkCode)){
				out.println("<script>alert(\"Wrong CheckCode!\");window.location='login.html'</script>");
			}else{			
				User user=new User(username,password,email);
				int uid=new UserService().dologin(user);
				if(uid>0){
				session.setAttribute("userid",uid);
				response.sendRedirect("index.jsp");
				}else{
					out.println("<script>alert(\"Login Failed,please retry!\");window.location='login.html'</script>");
				}				
			}	
		}
		else if("register".equals(op)){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String checkCode=request.getParameter("checkCode");
			String codeString=(String) session.getAttribute("randomCode");
			
			if(!codeString.equals(checkCode)){
				out.println("<script>alert(\"Wrong CheckCode!\");window.location='register.html'</script>");
			}else{			
				User user=new User(username,password,email);
				new UserService().doregister(user);
				out.println("<script>alert(\"Register Successfully!\");window.location='login.html'</script>");
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
