package com.umpay.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * 单例获取数据库连接
 * */
public class OnTestUmssfrnaDBConnect {

	private static final String jdbcurl="jdbc:db2://xx.xx.xx.xx:xxxxx/upontest";
	private static final String user="xxxxxxxx";
	private static final String pwd="xxxxxxxx";
	public static Connection getConnect(){
		Connection conn = null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			conn = DriverManager.getConnection(jdbcurl, user,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getDB2Connect(String jdbcurl,String user,String pwd){
		 Connection resconn =null;
		if(resconn == null){
			synchronized(OnTestUmssfrnaDBConnect.class){
				if(resconn == null){
					try {
						Class.forName("com.ibm.db2.jcc.DB2Driver");
						resconn = DriverManager.getConnection(jdbcurl, user,pwd);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return resconn;
	}
	
	public static void release(Connection conn, PreparedStatement pst, ResultSet rs){
		try {//首先尝试关闭结果集
			if (rs != null)
				rs.close();
		} catch (SQLException sqle) {//发生异常是输出异常不处理
			sqle.printStackTrace();
		} finally { //在方法结束前 尝试关闭stmt
			try {
				if (pst != null)
					pst.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} finally {//无论stmt是否关闭成功 都进行关闭数据库连接
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
	}
}
