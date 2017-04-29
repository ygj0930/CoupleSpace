package com.ygj.control;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class onLineCount implements HttpSessionListener {

	public int count=0;
	public void sessionCreated(HttpSessionEvent arg0) {
		count++;
		arg0.getSession().getServletContext().setAttribute("Count", count);

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		count--;
		arg0.getSession().getServletContext().setAttribute("Count", count);
	}

}
