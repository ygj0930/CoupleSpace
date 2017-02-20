package com.ygj.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil_BO {
	
	public Connection conn = null;
	public PreparedStatement st = null;
	public ResultSet rs = null;
	public DBUtil_BO() {
		super();
	}	
}
