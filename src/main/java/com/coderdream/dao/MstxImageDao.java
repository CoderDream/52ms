package com.coderdream.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.coderdream.bean.MstxImage;
import com.coderdream.util.DBUtil;

public class MstxImageDao {
	/**
	 * @param image
	 * @param mid
	 */
	public static void insertMstxImage(byte[] image, int mid) {
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null;
		int id = MstxDao.getMaxNumber("mstx_image");
		MstxDao.updateMaxNumber(4);// 将该字段值加1
		try {
			pstmt = con.prepareStatement("insert into mstx_image(id,mid,image_data) values(?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			pstmt.setBytes(3, image);
			pstmt.execute();
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
	}

	/**
	 * 插入美食图片
	 * 
	 * @param fileName
	 * @param mid
	 */
	public static void insertMstxImage(File fileName, int mid) {
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null;
		int id = MstxDao.getMaxNumber("mstx_image");
		MstxDao.updateMaxNumber(4);// 将该字段值加1
		try {
			fis = new FileInputStream(fileName);
			pstmt = con.prepareStatement("insert into mstx_image(id,mid,image_data) values(?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			pstmt.setBinaryStream(3, fis, (int) fileName.length());
			pstmt.execute();
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
	}

	/**
	 * 得到美食图片
	 * 
	 * @param mid
	 * @return
	 */
	public static ArrayList<Blob> getMstxImage(int[] mid) {//
		ArrayList<Blob> result = new ArrayList<Blob>();
		for (int i = 0; i < mid.length; i++) {
			ArrayList<MstxImage> temp = getMstxImageList(mid[i]);
			if (temp.size() > 0) {
				MstxImage mi = temp.get(0);// 用于手机端显示，只取第一张图片即可
				Blob b = mi.getB();
				result.add(b);
			}
		}
		return result;
	}

	/**
	 * 得到美食图片
	 * 
	 * @param mid
	 * @return
	 */
	public static ArrayList<MstxImage> getMstxImageList(int mid) {
		ArrayList<MstxImage> result = new ArrayList<MstxImage>();
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from mstx_image where mid=?");
			pstmt.setInt(1, mid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				Blob b = rs.getBlob("image_data");
				Date image_time = rs.getDate("image_time");
				MstxImage mi = new MstxImage(id, mid, b, image_time);
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
}
