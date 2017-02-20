package com.ygj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.ygj.vo.User;

public class UserDAO {
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;

	public UserDAO() {
		super();
	}

	public int doQuery(User user) {
		int uid = -1;
		String name = user.getUsername();
		String pass = user.getPassword();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			st = con.createStatement();

			String sql = "select * from users where username='" + name
					+ "' and password='" + pass + "'";

			rs = st.executeQuery(sql);

			if (rs.next()) {
				uid = rs.getInt("userid");
			}
			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uid;
	}

	public void doAdd(User user) {
		String name = user.getUsername();
		String pass = user.getPassword();
		String email = user.getEmail();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/couplespace", "root", "root");
			st = con.createStatement();
			String sql = "insert into users(username,password,email) values('"
					+ name + "','" + pass + "','" + email + "')";
			st.executeUpdate(sql);
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
