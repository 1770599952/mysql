package com.jdbc.batchinsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdbc.utils.JDBCUtil;

public class BatchInsert01 {

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

            String sql = "INSERT INTO account(name,money) values";
            
            for (int i = 0; i < 10; i++) {
            	if(i < 9) {
            		sql += "("+String.valueOf(i)+","+i+"),";
            	}else {
            		sql += "("+String.valueOf(i)+","+i+")";
            	}
            }
            System.out.println(sql);
            st = conn.prepareStatement(sql);
            st.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	JDBCUtil.release(conn, st, rs);
        }		
		
		
	}
	
}
