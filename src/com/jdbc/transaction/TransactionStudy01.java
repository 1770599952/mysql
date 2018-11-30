package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdbc.utils.JDBCUtil;

/**
 * 
 * 	当JDBC向数据库中获取一个Connect对象时，默认情况下这个connection对象就会自动向数据库提交它上面发送的SQL语句，
 * 	若想关闭这种提交方式，让多条SQL语句在一个事务中执行，可使用下列的JDBC事务控制语句。
 * 
 * 		Connection.setAutoCommit(false); // 相当于开启事务(start transaction)
		Connection.rollback(); 			 //回滚事务(rollback)
		Connection.commit(); 			 //提交事务(commit)
 * 
 * **/
public class TransactionStudy01 {
	
	// 在JDBC代码中演示银行转帐案例，使如下转帐操作在同一事务中执行：
	public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false); // 相当于start transaction，开启事务

            String sql1 = "update account set money=money-100 where name='aaa'";
            String sql2 = "update account set money=money+100 where name='bbb'";

            st = conn.prepareStatement(sql1);
            st.executeUpdate();

            st = conn.prepareStatement(sql2);
            st.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	JDBCUtil.release(conn, st, rs);
        }

	}

}
