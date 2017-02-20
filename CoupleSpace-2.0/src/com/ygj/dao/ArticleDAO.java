package com.ygj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.sun.imageio.plugins.common.LZWStringTable;
import com.ygj.vo.Article;

public class ArticleDAO {
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	public ArticleDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean doAdd(Article article){
		String id=article.getA_id();
		String title=article.getA_title();
		String summarize=article.getA_summarize();
		String content=article.getA_content();
		String time=article.getA_time();
		String photo=article.getA_photo();
		int uid=article.getA_uid();
		boolean flag=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			Statement st2=con.createStatement();
			String sql2="select * from articles where a_title='"+title+"'";
			ResultSet rs2=st2.executeQuery(sql2);
			if(rs2.next()){
				flag=false;
			}else{
			st = con.createStatement();
			String sql = "insert into articles values('"
					+ id + "','" + title + "','" + summarize+ "','" + content + "','"+ time + "','" + photo+"','"+uid + "')";
			st.executeUpdate(sql);
			st.close();
			con.close();
			
			flag=true;}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public Article doChaxun(String id){
		String idString=id;
		Article article=new Article();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			st = con.createStatement();
			String sql = "select * from articles where a_id='"+idString+"'";
			rs=st.executeQuery(sql);
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
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public Vector<Article> ChaFenye(int uid,int pages,int limit){
		Vector<Article> arts=new Vector<Article>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			st = con.createStatement();
			String sql=null;
			if(uid==0){
				sql="select * from articles limit "+(pages-1)*limit+","+limit;
			}else{
			sql = "select * from articles where u_id="+uid+" limit "+(pages-1)*limit+","+limit;}
			rs=st.executeQuery(sql);
			int i=0;
			while(rs.next())
			{
				Article a=new Article();
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
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arts;
		
	}
	
	public boolean doUpdate(Article article){
		String id=article.getA_id();
		String title=article.getA_title();
		String summarize=article.getA_summarize();
		String content=article.getA_content();
		String time=article.getA_time();
		String photo=article.getA_photo();
		String sql=null;
		boolean flag=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			st = con.createStatement();
			if(photo==null){
				sql="Update articles set a_title='"+title+"',a_summarize='"+summarize+"',a_content='"+content+"',a_time='"+time+"' where a_id='"+id+"'";
			}else{
				sql="Update articles set a_title='"+title+"',a_summarize='"+summarize+"',a_content='"+content+"',a_time='"+time+"',a_photo='"+photo+"' where a_id='"+id+"'";
			}
			st.executeUpdate(sql);
			st.close();
			con.close();			
			flag=true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean doDelete(String id){
		boolean flag=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			st = con.createStatement();
			String sql="Delete from articles where a_id='"+id+"'";
			st.executeUpdate(sql);
			st.close();
			con.close();			
			flag=true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
