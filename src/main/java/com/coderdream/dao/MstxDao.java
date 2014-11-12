package com.coderdream.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.coderdream.bean.MstxHeadImage;
import com.coderdream.util.DBUtil;

public class MstxDao {

	/**
	 * 得到最大编号，即某个表的主键自加时的最大
	 * 
	 * @param what
	 * @return
	 */
	public static int getMaxNumber(String what) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from mstx_max");
			rs.next();
			result = rs.getInt(what);
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
				if (rs != null) {
					rs.close();
					rs = null;
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
	 * @param gid
	 * @return
	 */
	public static int updateIntoAdsInfo(int gid) {
		int result = gid;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from mstx_ads where gid=?");
			pstmt.setInt(1, gid);
			rs = pstmt.executeQuery();// 查询
			if (!rs.next()) {
				result = getMaxNumber("mstx_ads");
				updateMaxNumber(5);// 将该字段值加1
				pstmt = con.prepareStatement("insert into mstx_ads(gid,gdis) values(?,?)");
				pstmt.setInt(1, result);
				pstmt.setString(2, "");
				pstmt.executeUpdate();
			}
		} catch (Exception e) {// 捕获异常
			e.printStackTrace();// 打印异常
		} finally {
			try {
				if (rs != null) {
					rs.close();// 关闭
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
	 * 插入每日推荐
	 * 
	 * @param mid
	 *            美食ID（MstxID）
	 */
	public static int insertMstxRecommend(int mid) {
		int result = 0;
		int id = getMaxNumber("mstx_recommend");
		updateMaxNumber(7);// 将该字段值加1
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("insert into mstx_recommend(id,mid) values(?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			result = pstmt.executeUpdate();
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
	 * @param tdis
	 *            描述
	 * @param fileName
	 *            文件名
	 * @param uid
	 *            用户ID
	 */
	public static boolean insertMstxHead(String tdis, File fileName, int uid) {
		boolean result = false;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null;// TODO
		int id = getMaxNumber("mstx_head");
		updateMaxNumber(1);// 将该字段值加1
		try {
			fis = new FileInputStream(fileName);
			pstmt = con.prepareStatement("insert into mstx_head(tid,tdis,tdata,uid) values(?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, tdis);
			pstmt.setBinaryStream(3, fis, (int) fileName.length());
			pstmt.setInt(4, uid);
			result = pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
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
	 * @param tdis
	 *            描述
	 * @param fileName
	 *            文件名
	 * @param uid
	 *            用户ID
	 */
	public static File getMstxHeadFile(int tid, String fileName) {
		File file = null;
		Connection con = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			String sql = "select tdata from mstx_head where tid='" + tid + "'";
			System.out.println("SQL: " + sql);
			rs = st.executeQuery(sql);

			InputStream is = null;
			while (rs.next()) {
				Blob blob = (Blob) rs.getBlob("tdata");
				if (null != blob) {
					System.out.println("读取成功");
				} else {
					System.out.println("读取失败");
				}
				is = new BufferedInputStream(blob.getBinaryStream());
			}

			byte[] buf = new byte[1024];
			int len = 0;
			file = new File(fileName);
			FileOutputStream outs = new FileOutputStream(file);
			BufferedOutputStream output = new BufferedOutputStream(outs);
			System.out.println("OutputStream is ini");
			while ((len = is.read(buf, 0, 1024)) != -1) {
				System.out.println("output...");
				output.write(buf, 0, len);
			}
			if (is != null) {
				is.close();
			}
			if (output != null) {
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	/**
	 * 更新头像的图片
	 * 
	 * @param tdis
	 *            描述
	 * @param fileName
	 *            文件名
	 * @param uid
	 *            用户ID
	 */
	public static int updateHeadFile(int tid, File fileName) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			pstmt = con.prepareStatement("update mstx_head set tdata=? where tid=?");
			pstmt.setBinaryStream(1, fis, (int) fileName.length());
			pstmt.setInt(2, tid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
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
	 * 插入广告
	 * 
	 * @param fileName
	 * @param gid
	 */
	public static int insertMstxAds(String gdis) {
		int gid = MstxDao.getMaxNumber("mstx_ads");
		MstxDao.updateMaxNumber(5);// 将该字段值加1
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into mstx_ads(gid,gdis) values(?,?)");
			pstmt.setInt(1, gid);
			pstmt.setString(2, gdis);
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
	 * 插入广告图片
	 * 
	 * @param fileName
	 * @param gid
	 */
	public static int insertMstxAdsImage(File fileName, int gid) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null;
		int id = getMaxNumber("mstx_ads_image");
		updateMaxNumber(6);// 将该字段值加1
		try {
			fis = new FileInputStream(fileName);
			pstmt = con.prepareStatement("insert into mstx_ads_image(gpid,gid,gdata) values(?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, gid);
			pstmt.setBinaryStream(3, fis, (int) fileName.length());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
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
	 * 插入新种类
	 * 
	 * @param sort
	 */
	public static int insertMstxSort(String sort) {
		int result = 0;
		int sid = getMaxNumber("mstx_sort");
		updateMaxNumber(8);// 将该字段值加1
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement("insert into mstx_sort(sid,info_sort) values(" + sid + ", '" + sort + "')");
			result = pstmt.executeUpdate();
		} catch (Exception e) {// 捕获异常
			e.printStackTrace();// 打印异常信息
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
	 * 得到指定id的头像图片
	 * 
	 * @param tid
	 *            头像ID
	 * @return
	 */
	public static ArrayList<MstxHeadImage> getHeadImage(int tid) {//
		ArrayList<MstxHeadImage> result = new ArrayList<MstxHeadImage>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from mstx_head where tid=?");
			pstmt.setInt(1, tid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String tdis = rs.getString("tdis");
				Blob b = rs.getBlob("tdata");
				int uid = rs.getInt("uid");
				MstxHeadImage mi = new MstxHeadImage(tid, tdis, b, uid);
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
	 * 删除每日推荐
	 * 
	 * @param mid
	 */
	public static int deleteMstxRecommend(int mid) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("delete from mstx_recommend where mid=?");
			pstmt.setInt(1, mid);
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

	public static List<String[]> getMstxSort() {// 得到美食的全部种类
		List<String[]> result = new ArrayList<String[]>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select sid,info_sort from mstx_sort;");
			rs = pstmt.executeQuery();// 查询
			while (rs.next()) {
				String sid = rs.getString(1);
				String info_sort = rs.getString(2);
				String[] strs = new String[2];
				strs[0] = sid;
				strs[1] = info_sort;
				result.add(strs);
			}
		} catch (Exception e) {// 捕获异常
			e.printStackTrace();// 打印异常信息
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
	 * 插入注释
	 * 
	 * @param mid
	 *            注释ID
	 * @param uid
	 *            用户ID
	 * @param comment
	 *            注释
	 * @return
	 */
	public static int insertMstxCol(String mid, String uid, String comment) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into mstx_col(mid,uid,comment) values(?,?,?)");
			pstmt.setString(1, mid);
			pstmt.setString(2, uid);
			pstmt.setString(3, comment);
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
	 * 插入美食对应的图片 TODO
	 * 
	 * @param mid
	 *            注释ID
	 * @param uid
	 *            用户ID
	 * @param comment
	 *            注释
	 * @return
	 */
	public static int insertMstxImage(int mid, File fileName, Date imageTime) {
		int result = 0;
		int id = MstxDao.getMaxNumber("mstx_image");
		MstxDao.updateMaxNumber(4);// 将该字段值加1
		FileInputStream fis = null;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			fis = new FileInputStream(fileName);
			pstmt = con.prepareStatement("insert into mstx_image(id,mid,image_data,image_time) values(?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			pstmt.setBinaryStream(3, fis, fis.available());
			pstmt.setDate(4, imageTime);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
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
	 * 删除推荐
	 * 
	 * @param mid
	 * @param uid
	 * @return
	 */
	public static int deleteMstxCol(String mid, String uid) {
		int result = 0;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("delete from mstx_col where mid=? and uid=?");
			pstmt.setString(1, mid);
			pstmt.setString(2, uid);
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
	 * 根据编号得到所有广告图片
	 * 
	 * @param id
	 * @return
	 */
	public static List<Integer> getMstxAdsGpid(int id) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select gpid from mstx_ads_image where gid=?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int gid = rs.getInt(1);
				result.add(gid);
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
	 * 得到所有广告位置的编号
	 * 
	 * @return
	 */
	public static List<Integer> getMSTXAdsID() {
		List<Integer> result = new ArrayList<Integer>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select gid from mstx_ads;");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer i = rs.getInt(1);
				result.add(i);
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
	 * 根据gpid得到广告图片
	 * 
	 * @param gpid
	 * @return
	 */
	public static Blob getMstxAdsImageByGpid(int gpid) {
		Blob result = null;
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select gdata from mstx_ads_image where gpid = ?;");
			pstmt.setInt(1, gpid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getBlob(1);
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
	 * 得到所有头像的id
	 * 
	 * @return
	 */
	public static ArrayList<Integer> getHeadNumber() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select tid from mstx_head");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer tid = rs.getInt("tid");
				result.add(tid);
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
	 * 更改mstx_max中相应列的值
	 * 
	 * @param what
	 */
	public static int updateMaxNumber(int what) {
		int result = 0;
		Connection con = DBUtil.getConnection();// 得到数据库连接
		Statement st = null;
		String sql = "";
		try {
			st = con.createStatement();
			switch (what) {
			case 1:
				sql = "update mstx_max set mstx_head = mstx_head + 1 ";
				break;
			case 2:
				sql = "update mstx_max set mstx_user  = mstx_user  + 1 ";
				break;
			case 3:
				sql = "update mstx_max set mstx_info   = mstx_info   + 1 ";
				break;
			case 4:
				sql = "update mstx_max set mstx_image  = mstx_image  + 1 ";
				break;
			case 5:
				sql = "update mstx_max set mstx_ads   = mstx_ads   + 1 ";
				break;
			case 6:
				sql = "update mstx_max set mstx_ads_image   = mstx_ads_image   + 1 ";
				break;
			case 7:
				sql = "update mstx_max set mstx_recommend   = mstx_recommend   + 1 ";
				break;
			case 8:
				sql = "update mstx_max set mstx_sort  = mstx_sort   + 1 ";
				break;
			}
			result = st.executeUpdate(sql);
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
		return result;
	}
}
