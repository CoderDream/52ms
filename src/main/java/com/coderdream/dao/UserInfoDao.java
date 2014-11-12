package com.coderdream.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.coderdream.bean.UserInfo;
import com.coderdream.util.DBUtil;

public class UserInfoDao {

	/**
	 * 根据用户ID得到用户所有信息
	 * 
	 * @param uid
	 *          用户ID
	 * @return 用户信息对象
	 */
	public static UserInfo getUserByUid(String uid) {
		UserInfo result = null;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from mstx_user where uid=?");
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String u_name = rs.getString("u_name");
				String u_qq = rs.getString("u_qq");
				String u_Email = rs.getString("u_Email");
				String u_dis = rs.getString("u_dis");
				int u_head = rs.getInt("u_head");
				boolean u_admin = rs.getBoolean("u_admin");
				String u_mood = rs.getString("u_mood") == null ? "" : rs.getString("u_mood");
				int u_integral = rs.getInt("u_integral");// 积分
				String u_hobby = rs.getString("u_hobby") == null ? "" : rs.getString("u_hobby");
				int u_level = rs.getInt("u_level");
				int u_number = rs.getInt("u_number");// 访问次数
				result = new UserInfo(Integer.parseInt(uid), u_name, u_qq, u_Email, u_dis, u_head, u_admin, u_mood, u_integral,
						u_hobby, u_level, u_number);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 添加用户
	 * 
	 * @param u_name
	 *          姓名
	 * @param u_qq
	 *          QQ
	 * @param u_pwd
	 *          密码
	 * @param u_Email
	 *          邮箱
	 * @param u_dis
	 *          简介
	 * @return
	 */
	public static int insertUser(String u_name, String u_qq, String u_pwd, String u_Email, String u_dis) {// 添加用户
		int uid = MstxDao.getMaxNumber("mstx_user");
		MstxDao.updateMaxNumber(2);// 将该字段值加1
		Connection con = DBUtil.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
			String sql = "insert into mstx_user" + "(u_admin,uid,u_name,u_qq,u_pwd,u_Email,u_dis) " + "values(false," + uid
					+ ",'" + u_name + "','" + ((u_qq.equals("")) ? 0 : u_qq) + "','" + u_pwd + "','" + u_Email + "','" + u_dis
					+ "');";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return uid;
	}

	/**
	 * 检查用户名是否存在
	 * 
	 * @param uid
	 *          用户ID
	 * @param u_pwd
	 *          密码
	 * @return
	 */
	public static String checkUser(String u_name, String u_pwd) {
		String result = null;
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();// 创建语句
			rs = st.executeQuery("select uid from mstx_user where u_name = '" + u_name + "' and u_pwd = '" + u_pwd + "';");
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 更新用户头像
	 * 
	 * @param uid
	 *          用户ID
	 * @param u_head
	 *          头像ID
	 */
	public static int updateUserHead(int uid, int u_head) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("update mstx_user set u_head=? where uid=?");
			pstmt.setInt(1, u_head);
			pstmt.setInt(2, uid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int updateUserInfo(String uid, String u_name, String u_qq, String u_Email, String u_favourite,
			String u_dis) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("update mstx_user set u_name=?, u_qq=?, u_Email=?, u_hobby=?, u_dis=? where uid=?;");
			pstmt.setString(1, u_name);
			pstmt.setString(2, u_qq);
			pstmt.setString(3, u_Email);
			pstmt.setString(4, u_favourite);
			pstmt.setString(5, u_dis);
			pstmt.setInt(6, Integer.parseInt(uid));
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 检查是否为管理员
	 * 
	 * @param uid
	 *          用户ID
	 * @return
	 * 
	 */
	public static boolean isAdmin(String uid) {
		boolean result = false;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select u_admin from mstx_user where uid=?");
			pstmt.setInt(1, Integer.parseInt(uid));

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getBoolean(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		// int i = DBUtil.getMstxInfoCountForPhone("a", -1, "", "743");
		// System.out.println(i);
		String uid = "0";
		UserInfo userInfo = getUserByUid(uid);
		System.out.println(userInfo);
	}
}
