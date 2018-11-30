package com.jdbc.batchinsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdbc.utils.JDBCUtil;

public class BatchInsert02 {

	public static void main(String[] args) {
		batchInsertTest01();
	}
	
	public static void batchInsertTest01() {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false); // 相当于start transaction，开启事务

			String sql = "INSERT INTO account(name,money) VALUES(?,?)";
			st = conn.prepareStatement(sql);

			for (int i = 0; i < 10; i++) {
				st.setString(1, String.valueOf(i));
				st.setFloat(2, i);
				st.addBatch();
			}

			System.out.println(sql);
			st.executeBatch();
			st.clearBatch();

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st, rs);
		}

	}
	
}
