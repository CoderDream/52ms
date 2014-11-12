package com.coderdream.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.coderdream.bean.MstxInfo;
import com.coderdream.util.DBUtil;

public class MstxInfoDao {
	public static ArrayList<MstxInfo> getMstxInfoByName(String name, int type, int span, int currentPageNo, int where) {// currentPageNo从1开始
		ArrayList<MstxInfo> result = new ArrayList<MstxInfo>();
		String info_name = name;

		String sql;
		if (currentPageNo == -1) {
			sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info;";
		} else {
			int start = span * (currentPageNo - 1);// 计算出起始记录编号
			if (name.trim().equals("")) {
				// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
				sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info limit " + start + ","
						+ span + ";";
			} else {
				switch (type) {
				case 1:
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info where info_title like '%"
							+ info_name + "%' limit " + start + "," + span + ";";
					break;
				case 2:// 按种类
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info where info_sort like '%"
							+ info_name + "%' limit " + start + "," + span + ";";
					break;
				case 3:// 按价格
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info where info_price = "
							+ info_name + " limit " + start + "," + span + ";";
					break;
				default:
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info limit " + start + ","
							+ span + ";";
				}
			}
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			// 由于MySQL默认创建的是可滚动的结果集合，因此要在创建语句时指定结果集不可滚动，只读
			st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			// 执行检索
			rs = st.executeQuery(sql);

			while (rs.next()) {
				String infoTitle = rs.getString("info_title");
				String infoDis = rs.getString("info_dis");
				String infoLon = rs.getString("info_lon");
				String infoLat = rs.getString("info_lat");
				String uid = rs.getString("uid");
				String mid = rs.getString("mid");
				Date infoTime = rs.getDate("info_time");

				MstxInfo mi = new MstxInfo(infoTitle, infoDis, infoLon, infoLat, infoTime, uid, mid);
				result.add(mi);
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
	 * 得到所有的每日推荐
	 * 
	 * @return
	 */
	public static ArrayList<MstxInfo> getMstxRecommend() {
		ArrayList<MstxInfo> result = new ArrayList<MstxInfo>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con
					.prepareStatement("select info_title,info_dis,info_lon,info_lat,a.info_time,a.uid,a.mid from mstx_info as a, mstx_recommend as b where a.mid = b.mid");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String info_title = rs.getString(1);
				String info_dis = rs.getString(2);
				String info_lon = rs.getString(3);
				String info_lat = rs.getString(4);
				Date info_time = rs.getDate(5);
				String uid = rs.getString(6);
				String mid = rs.getString(7);
				MstxInfo mi = new MstxInfo(info_title, info_dis, info_lon, info_lat, info_time, uid, mid);
				result.add(mi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return result;
	}

	/**
	 * 获取用户的收藏
	 * 
	 * @param uid
	 * @return
	 */
	public static ArrayList<MstxInfo> getFavourite(String uid) {
		ArrayList<MstxInfo> result = new ArrayList<MstxInfo>();
		String sql = "select info_title,info_dis,info_lon,info_lat,info_time,a.uid,a.mid "
				+ "from mstx_info as a,mstx_col as b where a.mid=b.mid and b.uid = " + uid + ";";
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();// 创建语句
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String info_title = rs.getString("info_title");
				String info_dis = rs.getString("info_dis");
				String info_lon = rs.getString("info_lon");
				String info_lat = rs.getString("info_lat");
				String mid = rs.getString("mid");
				Date info_time = rs.getDate("info_time");

				MstxInfo mi = new MstxInfo(info_title, info_dis, info_lon, info_lat, info_time, uid, mid);
				result.add(mi);
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
	 * 插入美食信息
	 * 
	 * @param info_title
	 * @param info_dis
	 * @param info_lon
	 * @param info_lat
	 * @param uid
	 * @param info_sort
	 * @param info_price
	 * @param where
	 * @param mshotelName
	 * @return
	 */
	public static int insertMstxInfo(String info_title, String info_dis, String info_lon, String info_lat, String uid,
			String info_sort, int info_price, int where, String mshotelName) {
		int mid = MstxDao.getMaxNumber("mstx_info");
		MstxDao.updateMaxNumber(3);// 将该字段值加1
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con
					.prepareStatement("insert into mstx_info(mid,info_title,info_dis,info_lon,info_lat,uid,info_sort,info_price,hotel_name) values(?,?,?,?,?,?,?,?,?)");
			if (where == 2) {// 手机来的
				pstmt.setInt(1, mid);
				pstmt.setString(2, info_title);
				pstmt.setString(3, info_dis);
				pstmt.setDouble(4, Double.parseDouble(info_lon));
				pstmt.setDouble(5, Double.parseDouble(info_lat));
				pstmt.setInt(6, Integer.parseInt(uid));
				pstmt.setInt(7, Integer.parseInt(info_sort));
				pstmt.setInt(8, info_price);
				pstmt.setString(9, mshotelName);
			} else {
				pstmt.setInt(1, mid);
				pstmt.setString(2, info_title);
				pstmt.setString(3, info_dis);
				pstmt.setDouble(4, Double.parseDouble(info_lon));
				pstmt.setDouble(5, Double.parseDouble(info_lat));
				pstmt.setInt(6, Integer.parseInt(uid));
				pstmt.setInt(7, Integer.parseInt(info_sort));
				pstmt.setInt(8, info_price);
				pstmt.setString(9, mshotelName);
			}

			pstmt.execute();
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
		return mid;
	}

	/**
	 * 通过手机查询美食信息
	 * 
	 * @param infoValues
	 * @param searchSort
	 * @param startPrice
	 * @param endPrice
	 * @param span
	 * @param currentPageNo
	 * @return
	 */
	public static List<String[]> getMstxInfoForPhone(String infoValues, int searchSort, String startPrice,
			String endPrice, int span, int currentPageNo) {
		List<String[]> result = new ArrayList<String[]>();
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql;
		int start = span * (currentPageNo - 1);// 计算出起始记录编号
		if (searchSort == -1) {
			if (startPrice.trim().equals("")) {// 当起始价格为空时
				if (endPrice.trim().equals("")) {// 截止价格也为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
							+ "mstx_info where info_title like '%" + infoValues + "%' limit " + start + "," + span + ";";
				} else {
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
							+ "mstx_info where info_price<" + endPrice + " and info_title like '%" + infoValues + "%' limit " + start
							+ "," + span + ";";
				}
			} else {// 起始价格不为空
				if (endPrice.trim().equals("")) {// 截止价格为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
							+ "mstx_info where info_price>" + startPrice + " and info_title like '%" + infoValues + "%' limit "
							+ start + "," + span + ";";
				} else {// 都不为空时
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
							+ "mstx_info where info_price>" + startPrice + " and info_price<" + endPrice + " and info_title like '%"
							+ infoValues + "%' limit " + start + "," + span + ";";
				}
			}
		} else {
			if (startPrice.trim().equals("")) {// 当起始价格为空时
				if (endPrice.trim().equals("")) {// 截止价格也为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info "
							+ "where info_sort=" + searchSort + " and info_title like '%" + infoValues + "%' limit " + start + ","
							+ span + ";";
				} else {// 起始为空，截止不为空
					// sql =
					// "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
					// +
					// "mstx_info where info_sort="+searchSort+" info_price<"+endPrice+" and "
					// +
					// "info_title like '%"+infoValues+"%' limit "+start+","+span+";";
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info "
							+ "where info_sort=" + searchSort + " and info_price>" + startPrice + " " + "and info_title like '%"
							+ infoValues + "%' limit " + start + "," + span + ";";
				}
			} else {// 起始价格不为空
				if (endPrice.trim().equals("")) {// 截止价格为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					// sql =
					// "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
					// +
					// "mstx_info where info_sort="+searchSort+" info_price>"+startPrice+" and info_title "
					// +
					// "like '%"+infoValues+"%' limit "+start+","+span+";";
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info "
							+ "where info_sort=" + searchSort + " and info_price>" + startPrice + " " + "and info_title like '%"
							+ infoValues + "%' limit " + start + "," + span + ";";
				} else {// 都不为空时
					// sql =
					// "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from "
					// +
					// "mstx_info where info_sort="+searchSort+" info_price>"+startPrice+" and info_price<"+endPrice+" and info_title like '%"+infoValues+"%' limit "+start+","+span+";";
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info "
							+ "where info_price<" + endPrice + " and info_sort=" + searchSort + " and info_price>" + startPrice + " "
							+ "and info_title like '%" + infoValues + "%' limit " + start + "," + span + ";";
				}
			}
		}
		try {
			con = DBUtil.getConnection();
			// 由于MySQL默认创建的是可滚动的结果集合，因此要在创建语句时指定结果集不可滚动，只读
			st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			// 执行检索
			rs = st.executeQuery(sql);

			while (rs.next()) {
				String[] str = new String[8];
				str[0] = rs.getString(1);// hotel_name
				str[1] = rs.getString(2);// info_title
				str[2] = rs.getString(3);// info_dis
				str[3] = rs.getString(4);// info_lon
				str[4] = rs.getString(5);// info_lat
				str[5] = rs.getDate(6).toString();// info_time
				str[6] = rs.getString(7);// uid
				str[7] = rs.getString(8);// mid

				result.add(str);
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

	public static int getMstxInfoCountForPhone(String infoValues, int searchSort, String startPrice, String endPrice) {// 返回满足搜索添加的总个数
		int result = 0;
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql;
		if (searchSort == -1) {
			if (startPrice.trim().equals("")) {// 当起始价格为空时
				if (endPrice.trim().equals("")) {// 截止价格也为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					sql = "select count(*) from " + "mstx_info where info_title like '%" + infoValues + "%';";
				} else {
					sql = "select count(*) from " + "mstx_info where info_price<" + endPrice + " and info_title like '%"
							+ infoValues + "%';";
				}
			} else {// 起始价格不为空
				if (endPrice.trim().equals("")) {// 截止价格为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					sql = "select count(*) from " + "mstx_info where info_price>" + startPrice + " and info_title like '%"
							+ infoValues + "%';";
				} else {// 都不为空时
					sql = "select count(*) from " + "mstx_info where info_price>" + startPrice + " and info_price<" + endPrice
							+ " and info_title like '%" + infoValues + "%';";
				}
			}
		} else {
			if (startPrice.trim().equals("")) {// 当起始价格为空时
				if (endPrice.trim().equals("")) {// 截止价格也为空时
					// LIMIT M,N M+1条记录开始（记录号从1开始） N要几条记录 例如要 5-8条记录 LIMIT 4,4
					sql = "select count(*) from mstx_info " + "where info_sort=" + searchSort + " and info_title like '%"
							+ infoValues + "%';";
				} else {// 起始为空，截止不为空
					sql = "select count(*) from mstx_info " + "where info_sort=" + searchSort + " and info_price>" + startPrice
							+ " " + "and info_title like '%" + infoValues + "%';";
				}
			} else {// 起始价格不为空
				if (endPrice.trim().equals("")) {// 截止价格为空时
					sql = "select count(*) from mstx_info where info_sort=" + searchSort + " and info_price>" + startPrice + " "
							+ "and info_title like '%" + infoValues + "%';";
				} else {// 都不为空时
					sql = "select count(*) from mstx_info " + "where info_price<" + endPrice + " and info_sort=" + searchSort
							+ " and info_price>" + startPrice + " " + "and info_title like '%" + infoValues + "%';";
				}
			}
		}
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();

			// 执行检索
			rs = st.executeQuery(sql);

			if (rs.next()) {
				result = rs.getInt(1);
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

	public static int getMstxInfoCount() {
		int result = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("select count(mid) from mstx_info");
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
