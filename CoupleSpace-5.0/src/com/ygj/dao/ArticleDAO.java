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
import com.ygj.bo.DBUtil_BO;
import com.ygj.control.C3p0Utils;
import com.ygj.control.DBUtils;

import com.ygj.error.MyException;

public class ArticleDAO {
	
	private static ArticleDAO articleDAO=new ArticleDAO();
	public static ArticleDAO getArticleDAO() {
		return articleDAO;
	}
	private ArticleDAO() {
		super();
	}
	private  DBUtil_BO dbBo=new DBUtil_BO();
	private  DBUtils dbUtils=DBUtils.getDbUtils();
	Logger logger=Logger.getLogger(getClass().getName());
	
	
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
			dbBo.conn=C3p0Utils.getConnection();
			String sql2="select * from articles where a_title=?";
			dbBo.st=dbBo.conn.prepareStatement(sql2);
			dbBo.st.setString(1, title);
			dbUtils.executeQuery(dbBo);
			if(dbBo.rs.next()){
				flag=false;
				dbUtils.realseSource(dbBo);
			}else{
				String sql = "insert into articles values(?,?,?,?,?,?,?)";
				dbBo.st=dbBo.conn.prepareStatement(sql);
				dbBo.st.setString(1, id);
				dbBo.st.setString(2, title);
				dbBo.st.setString(3, summarize);
				dbBo.st.setString(4,content );
				dbBo.st.setString(5, time);
				dbBo.st.setString(6, photo);
				dbBo.st.setInt(7, uid);			
				dbUtils.executeUpdate(dbBo);			
			    flag=true;}
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
			dbBo.conn=C3p0Utils.getConnection();
			String sql = "select * from articles where a_id=?";
			dbBo.st = dbBo.conn.prepareStatement(sql);
			dbBo.st.setString(1, idString);
			dbUtils.executeQuery(dbBo);
			while(dbBo.rs.next()){
				article.setA_id(idString);
				article.setA_title(dbBo.rs.getString("a_title"));
				article.setA_summarize(dbBo.rs.getString("a_summarize"));
				article.setA_content(dbBo.rs.getString("a_content"));
				article.setA_time(dbBo.rs.getString("a_time"));
				article.setA_photo(dbBo.rs.getString("a_photo"));				
			}
			dbUtils.realseSource(dbBo);
		}catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return article;
	}
	
	public Vector<ArticleBO> ChaFenye(int uid,int pages,int limit){
		Vector<ArticleBO> arts=new Vector<ArticleBO>();
		try {
			dbBo.conn=C3p0Utils.getConnection();
			String sql=null;
			if(uid==0){
				sql="select * from articles limit "+(pages-1)*limit+","+limit;
			}else{
			sql = "select * from articles where u_id="+uid+" limit "+(pages-1)*limit+","+limit;}
			dbBo.st = dbBo.conn.prepareStatement(sql);
			dbUtils.executeQuery(dbBo);
			int i=0;
			while(dbBo.rs.next())
			{
				ArticleBO a=new ArticleBO();
				a.a_id=dbBo.rs.getString("a_id");
				a.a_title=dbBo.rs.getString("a_title");
				a.a_summarize=dbBo.rs.getString("a_summarize");
				a.a_content=dbBo.rs.getString("a_content");
				a.a_time=dbBo.rs.getString("a_time");
				a.a_photo=dbBo.rs.getString("a_photo");				
				arts.add(a);		
			}
			dbUtils.realseSource(dbBo);
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
			dbBo.conn=C3p0Utils.getConnection();
			if(photo==null){
				sql="Update articles set a_title=?,a_summarize=?,a_content=?,a_time=? where a_id=?";
				dbBo.st = dbBo.conn.prepareStatement(sql);
				dbBo.st.setString(1, title);
				dbBo.st.setString(2, summarize);
				dbBo.st.setString(3,content );
				dbBo.st.setString(4, time);
				dbBo.st.setString(5,id );
			}else{
				sql="Update articles set a_title=?,a_summarize=?,a_content=?,a_time=?,a_photo=? where a_id=?";
				dbBo.st = dbBo.conn.prepareStatement(sql);
				dbBo.st.setString(1, title);
				dbBo.st.setString(2, summarize);
				dbBo.st.setString(3,content );
				dbBo.st.setString(4, time);
				dbBo.st.setString(5,photo);
				dbBo.st.setString(6,id);
			}
			dbUtils.executeUpdate(dbBo);		
			flag=true;
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return flag;
	}
	
	public boolean doDelete(String id){
		boolean flag=false;
		try {
			dbBo.conn=C3p0Utils.getConnection();
			String sql="Delete from articles where a_id=?";
			dbBo.st = dbBo.conn.prepareStatement(sql);
			dbBo.st.setString(1, id);
			dbUtils.executeUpdate(dbBo);					
			flag=true;	
		} catch (SQLException e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);			
		}
		return flag;
	}

}
