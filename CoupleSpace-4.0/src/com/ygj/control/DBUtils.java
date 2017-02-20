package com.ygj.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leeyn.util.uuid.Uuid;
import com.ygj.bo.DBUtil_BO;
import com.ygj.error.MyException;

public class DBUtils {
	 static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(DBUtils.class.getName());
	 private static void realseSource( Connection _conn, PreparedStatement _st,ResultSet _rs){		
			C3p0Utils.close(_conn,_st,_rs);
		}

		public static void realseSource(DBUtil_BO _vo){	
			if(_vo!=null){
				realseSource(_vo.conn, _vo.st, _vo.rs);
			}		
		}
		
		public static void executeQuery(DBUtil_BO vo)
		{		
			try{
				vo.rs = vo.st.executeQuery();
			}catch (SQLException e){			
				realseSource(vo);
				String uuid=Uuid.create().toString();
				logger.error("UUID:"+uuid+", SQL语法有误: ",e);
				throw new MyException("err.user.dao.jdbc",e,uuid);
			}	
		}
		

		public static  void executeUpdate(DBUtil_BO vo)
		{

			Connection conn = vo.conn;
			PreparedStatement st = vo.st;
			try {
				st.executeUpdate();
			} catch (SQLException e) {
				realseSource(conn, st, null);		
				String uuid=Uuid.create().toString();
				logger.error("UUID:"+uuid+", SQL语法有误: ",e);
				throw new MyException("err.user.dao.jdbc",e,uuid);
			}
			realseSource(conn, st,null );				

		}
}
