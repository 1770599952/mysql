package com.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	 	static String url = "jdbc:mysql://localhost:3306/transactionstest";  
	    static String username = "root";  
	    static String password = "root";  
	  
	    static {  
	        try {  
	            Class.forName("com.mysql.jdbc.Driver");  
	        } catch (Exception e) {  
	            throw new ExceptionInInitializerError(e);  
	        }  
	    }  
	  
	    public static Connection getConnection() {  
	        Connection conn = null;  
	        try {  
	            conn = DriverManager.getConnection(url, username, password);  
	        } catch (SQLException e) {  
	            throw new RuntimeException("获取连接Mysql数据库连接失败");  
	        }  
	        return conn;  
	    }  
	  
	    public static void release(Connection conn, Statement st, ResultSet rs) {  
	  
	        if (rs != null) {  
	            try {  
	                rs.close();  
	            } catch (Exception e) {  
	                throw new RuntimeException("ResultSet关闭异常");  
	            }  
	            rs = null;  
	        }  
	        if (st != null) {  
	            try {  
	                st.close();  
	            } catch (Exception e) {  
	                throw new RuntimeException("Statement关闭异常");  
	            }  
	            st = null;  
	        }  
	        if (conn != null) {  
	            try {  
	                conn.close();  
	            } catch (Exception e) {  
	                throw new RuntimeException("Connection关闭异常");  
	            }  
	            conn = null;  
	        }  
	  
	    }  
}
