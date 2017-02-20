package com.ygj.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.sun.net.httpserver.Filter.Chain;
import com.ygj.error.MyError;
import com.ygj.error.MyException;

public class DoExceptionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		String err="";
		try {
			arg2.doFilter(arg0, arg1);
		}catch(MyError e){
			err=e.getKey();
		}catch(MyException e){
			err=e.getKey();
		}catch(Error e){
			err="系统错误";
		}catch (Exception e) {
			err="系统异常";
		}
		PrintWriter out = arg1.getWriter();
		if(!err.equals("")){
		out.print("<script>alert('"+err+"');window.location='index.jsp';</script>");
		out.close();}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
