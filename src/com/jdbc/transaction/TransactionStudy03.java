package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdbc.utils.JDBCUtil;

public class TransactionStudy03 {
	// 模拟aaa向bbb转账过程中出现异常导致有一部分SQL执行失败时手动通知数据库回滚事务
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false); // 相当于start transaction，开启多操作事务

			String sql1 = "update account set money=money-100 where name='aaa'";
			String sql2 = "update account set money=money+100 where name='bbb'";

			st = conn.prepareStatement(sql1);
			st.executeUpdate();

			int x = 1 / 0; // 程序运行到这个地方抛异常，后面的代码就不执行，数据库没有收到commit命令

			st = conn.prepareStatement(sql2);
			st.executeUpdate();

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} // 捕获到异常之后手动通知数据库执行回滚事务的操作
		} finally {
			JDBCUtil.release(conn, st, rs);
		}

	}
}
