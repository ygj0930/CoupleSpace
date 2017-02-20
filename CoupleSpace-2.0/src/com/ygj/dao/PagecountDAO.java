package com.ygj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PagecountDAO {
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public int getCount(){
		int Count=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
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
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Count;
		
	}

}
