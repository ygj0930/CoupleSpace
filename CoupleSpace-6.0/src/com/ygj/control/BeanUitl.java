package com.ygj.control;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class BeanUitl {
	
	public BeanUitl() {
		super();
	}
	public static ApplicationContext appContext=new ClassPathXmlApplicationContext("applicationContext.xml");
	public static Object getBean(String name){		
		return appContext.getBean(name);
	}

}
