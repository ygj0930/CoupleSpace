package com.ygj.control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		HttpSession session=request.getSession();
		
		String path=request.getRequestURI();
		
		Integer uid=(Integer)session.getAttribute("userid");
		
		if(path.indexOf("/login.jsp")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		if(path.indexOf("/register.jsp")>-1){
			arg2.doFilter(request, response);
			return;
		}
		
		if(uid!=null){
			arg2.doFilter(request, response);
			
		}else{
			response.sendRedirect("login.jsp");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
