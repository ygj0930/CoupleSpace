package com.ygj.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sun.imageio.plugins.common.LZWStringTable;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ygj.bo.ArticleBO;
import com.ygj.error.MyError;
import com.ygj.error.MyException;

public class ArticleDAO {
	Connection con;
	PreparedStatement st;
	ResultSet rs;
	Properties properties;
	Logger logger=Logger.getLogger(getClass().getName());
	public ArticleDAO() {
		super();
		properties=new Properties();
		InputStream in=ArticleDAO.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(in);
		} catch (Exception e) {
			logger.error("Exception in ArticleDAO!", e);
			throw new MyError("加载配置文件出错!", e);
			
		}
	}
	
	public boolean doAdd(ArticleBO article){
		String id=article.getA_id();
		String title=article.getA_title();
		String summarize=article.getA_summarize();
		String content=article.getA_content();
		String time=article.getA_time();
		String photo=article.getA_photo();
		int uid=article.getA_uid();
		boolean flag=false;
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			String sql2="select * from articles where a_title=?";
			PreparedStatement st2=con.prepareStatement(sql2);
			st2.setString(1, title);
			ResultSet rs2=st2.executeQuery();
			if(rs2.next()){
				flag=false;
			}else{
				String sql = "insert into articles values(?,?,?,?,?,?,?)";
				st = con.prepareStatement(sql);
				st.setString(1, id);
				st.setString(2, title);
				st.setString(3, summarize);
				st.setString(4,content );
				st.setString(5, time);
				st.setString(6, photo);
				st.setInt(7, uid);			
			st.execute();
			st.close();
			con.close();
			
			flag=true;}
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in ArticleDAO!", e);
			throw new MyException("ClassNotFoundError in ArticleDAO!", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return flag;
	}
	
	public ArticleBO doChaxun(String id){
		String idString=id;
		ArticleBO article=new ArticleBO();
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			String sql = "select * from articles where a_id=?";
			st = con.prepareStatement(sql);
			st.setString(1, idString);
			rs=st.executeQuery();
			while(rs.next()){
				article.setA_id(idString);
				article.setA_title(rs.getString("a_title"));
				article.setA_summarize(rs.getString("a_summarize"));
				article.setA_content(rs.getString("a_content"));
				article.setA_time(rs.getString("a_time"));
				article.setA_photo(rs.getString("a_photo"));				
			}			
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in ArticleDAO!", e);
			throw new MyException("ClassNotFoundError in ArticleDAO!", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return article;
	}
	
	public Vector<ArticleBO> ChaFenye(int uid,int pages,int limit){
		Vector<ArticleBO> arts=new Vector<ArticleBO>();
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			String sql=null;
			if(uid==0){
				sql="select * from articles limit "+(pages-1)*limit+","+limit;
			}else{
			sql = "select * from articles where u_id="+uid+" limit "+(pages-1)*limit+","+limit;}
			st = con.prepareStatement(sql);
			rs=st.executeQuery();
			int i=0;
			while(rs.next())
			{
				ArticleBO a=new ArticleBO();
				a.a_id=rs.getString("a_id");
				a.a_title=rs.getString("a_title");
				a.a_summarize=rs.getString("a_summarize");
				a.a_content=rs.getString("a_content");
				a.a_time=rs.getString("a_time");
				a.a_photo=rs.getString("a_photo");				
				arts.add(a);		
			}
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in ArticleDAO!", e);
			throw new MyException("ClassNotFoundError in ArticleDAO!", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		
		return arts;
		
	}
	
	public boolean doUpdate(ArticleBO article){
		String id=article.getA_id();
		String title=article.getA_title();
		String summarize=article.getA_summarize();
		String content=article.getA_content();
		String time=article.getA_time();
		String photo=article.getA_photo();
		String sql=null;
		boolean flag=false;
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			
			if(photo==null){
				sql="Update articles set a_title=?,a_summarize=?,a_content=?,a_time=? where a_id=?";
				st = con.prepareStatement(sql);
				st.setString(1, title);
				st.setString(2, summarize);
				st.setString(3,content );
				st.setString(4, time);
				st.setString(5,id );
			}else{
				sql="Update articles set a_title=?,a_summarize=?,a_content=?,a_time=?,a_photo=? where a_id=?";
				st = con.prepareStatement(sql);
				st.setString(1, title);
				st.setString(2, summarize);
				st.setString(3,content );
				st.setString(4, time);
				st.setString(5,photo);
				st.setString(6,id);
			}
			st.executeUpdate();
			st.close();
			con.close();			
			flag=true;
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in ArticleDAO!", e);
			throw new MyException("ClassNotFoundError in ArticleDAO!", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return flag;
	}
	
	public boolean doDelete(String id){
		boolean flag=false;
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			String sql="Delete from articles where a_id=?";
			st = con.prepareStatement(sql);
			st.setString(1, id);
			st.execute();
			st.close();
			con.close();			
			flag=true;
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in ArticleDAO!", e);
			throw new MyException("ClassNotFoundError", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return flag;
	}

}
