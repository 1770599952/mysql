package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import com.jdbc.utils.JDBCUtil;

public class TransactionStudy04 {

	// 设置事务回滚点。
	// 在开发中，有时候可能需要手动设置事务的回滚点，在JDBC中使用如下的语句设置事务回滚点：
	// Savepoint sp = conn.setSavepoint();
	// Conn.rollback(sp);
	// Conn.commit(); // 回滚后必须通知数据库提交事务	
	public static void main(String[] args) {
		
		  	Connection conn = null;
	        PreparedStatement st = null;
	        ResultSet rs = null;
	        Savepoint sp = null;

	        try {
	            conn = JDBCUtil.getConnection(); 
	            // MySQL默认的隔离级别——REPEATABLE-READ，并且是严格遵循数据库规范设计的，即支持4种隔离级别
	            // Oracle默认的隔离级别——Read committed，并且不支持这4种隔离级别，只支持这4种隔离级别中的2种，Read committed和Serializable
	            // conn.setTransactionIsolation(); // 相当于设置CMD窗口的隔离级别
	            conn.setAutoCommit(false); // 相当于start transaction，开启事务

	            // 不符合实际需求
	            String sql1 = "update account set money=money-100 where name='aaa'";
	            String sql2 = "update account set money=money+100 where name='bbb'";
	            String sql3 = "update account set money=money+100 where name='ccc'";

	            st = conn.prepareStatement(sql1);
	            st.executeUpdate();
	            /**
	             * 只希望回滚掉这一条sql语句，上面那条sql语句让其执行成功
	             * 这时可设置事务回滚点
	             */
	            sp = conn.setSavepoint();

	            st = conn.prepareStatement(sql2);
	            st.executeUpdate();

	            int x = 1/0; // 程序运行到这个地方抛异常，后面的代码就不执行，数据库没有收到commit命令

	            st = conn.prepareStatement(sql3);
	            st.executeUpdate();

	            conn.commit();
	        } catch (Exception e) {
	        	
	        	e.printStackTrace();
	            try {
	            	conn.rollback(sp); // 回滚到sp点，sp点上面的sql语句发给数据库执行，由于数据库没收到commit命令，数据库又会自动将这条sql语句的影响回滚掉，所以回滚完，一定要记得commit命令。
					conn.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} // 手动回滚后，一定要记得提交事务
	        } finally {
	        	JDBCUtil.release(conn, st, rs);
	        }

	}

}
