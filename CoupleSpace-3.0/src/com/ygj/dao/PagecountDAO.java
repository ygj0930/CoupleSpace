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

import com.ygj.error.MyError;
import com.ygj.error.MyException;

public class PagecountDAO {
	Connection con;
	Statement st;
	ResultSet rs;
	Properties properties;
	Logger logger=Logger.getLogger(getClass().getName());
	public int getCount(){
		properties=new Properties();
		InputStream in=ArticleDAO.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(in);
		} catch (Exception e) {
			logger.error("Exception in PagecountDAO!", e);
			throw new MyError("加载配置文件出错!", e);
		}
		int Count=0;
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			st = con.createStatement();

			String sql = "select * from pagecount";

			rs = st.executeQuery(sql);

			if (rs.next()) {
				Count = rs.getInt("article_total");
			}
			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in PagecountDAO!", e);
			throw new MyException("ClassNotFoundError", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in PagecountDAO!", e);
			throw new MyException("SQLError", e);
			
		}
		return Count;
		
	}

}
