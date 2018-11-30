package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdbc.utils.JDBCUtil;

public class TransactionStudy02 {
	// 模拟aaa向bbb转账过程中出现异常导致有一部分SQL执行失败后让数据库自动回滚事务
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

			int x = 1 / 0; // 程序运行到这个地方抛异常，后面的代码就不执行，数据库没有收到commit命令

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
