package com.ygj.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ygj.error.MyError;

public class C3p0Utils {
	static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(C3p0Utils.class.getName());
	static ComboPooledDataSource dataSource=new ComboPooledDataSource("mysql");
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
			
		} catch (Exception e) {
			logger.error("Exception in C3p0Utils!", e);
			throw new MyError("数据库连接出错!", e);			
		}
	}
	
	 public static void close(Connection conn,PreparedStatement pst,ResultSet rs){  
	        if(rs!=null){  
	            try {  
	                rs.close();  
	            } catch (SQLException e) {  
	            	logger.error("Exception in C3p0Utils!", e);
	    			throw new MyError("数据库连接关闭出错!", e);			
	            }  
	        }  
	        if(pst!=null){  
	            try {  
	                pst.close();  
	            } catch (SQLException e) {  
	            	logger.error("Exception in C3p0Utils!", e);
	    			throw new MyError("数据库连接关闭出错!", e);	
	            }  
	        }  
	  
	        if(conn!=null){  
	            try {  
	                conn.close();  
	            } catch (SQLException e) {  
	            	logger.error("Exception in C3p0Utils!", e);
	    			throw new MyError("数据库连接关闭出错!", e);	
	            }  
	        }  
	    }  
	 
	 

}
