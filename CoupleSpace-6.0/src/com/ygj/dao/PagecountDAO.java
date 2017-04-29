package com.ygj.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ygj.bo.DBUtil_BO;
import com.ygj.control.C3p0Utils;
import com.ygj.control.DBUtils;

import com.ygj.error.MyException;

public class PagecountDAO {
	private static PagecountDAO pageDAO=new PagecountDAO();
	public static PagecountDAO getPageDAO() {
		return pageDAO;
	}
	private PagecountDAO() {
		super();
	}
	
	private  DBUtil_BO dbBo=new DBUtil_BO();
	private  DBUtils dbUtils=DBUtils.getDbUtils();
	Logger logger=Logger.getLogger(getClass().getName());
	public int getCount(){
		int Count=0;
		try {
			dbBo.conn=C3p0Utils.getConnection();
			String sql = "select * from pagecount";
			dbBo.st=dbBo.conn.prepareStatement(sql);
			dbUtils.executeQuery(dbBo);
			if (dbBo.rs.next()) {
				Count = dbBo.rs.getInt("article_total");
			}
			dbUtils.realseSource(dbBo);
		} catch (SQLException e) {
			logger.error("SQLException in PagecountDAO!", e);
			throw new MyException("SQLError", e);			
		}
		return Count;		
	}

}
