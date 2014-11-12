package com.coderdream.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	/**
	 * 得到数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mstx", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void main(String[] args) {

	}
}
