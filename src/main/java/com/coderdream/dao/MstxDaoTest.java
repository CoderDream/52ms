package com.coderdream.dao;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.coderdream.bean.MstxHeadImage;

public class MstxDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMaxNumber() {
		String what = "mstx_head";
		int result = MstxDao.getMaxNumber(what);
		assertEquals(34, result);
	}

	/**
	 * TODO
	 */
	@Test
	public void testUpdateIntoAdsInfo() {
		int gid = 3;
		int result = MstxDao.updateIntoAdsInfo(gid);
		assertEquals(3, result);
	}

	/**
	 * 每日推荐
	 */
	@Test
	public void testInsertMstxRecommend() {
		int mid = 1;
		int result = MstxDao.insertMstxRecommend(mid);
		assertEquals(1, result);
	}

	/**
	 * 每日推荐
	 */
	@Test
	public void testInsertMstxRecommend2() {
		int mid = 3;
		int result = MstxDao.insertMstxRecommend(mid);
		assertEquals(1, result);
	}

	/**
	 * 删除每日推荐
	 */
	@Test
	public void testDeleteMstxRecommend() {
		int mid = 3;
		int result = MstxDao.deleteMstxRecommend(mid);
		assertEquals(1, result);
	}

	/**
	 * 插入广告信息
	 */
	@Test
	public void testInsertMstxAds() {
		String gdis = "广告信息1";
		int result = MstxDao.insertMstxAds(gdis);
		assertEquals(1, result);
	}

	/**
	 * 插入广告图片
	 */
	@Test
	public void testInsertMstxAdsImage() {
		String PATH = "D:\\image\\face\\";
		String fileNameStr = "";
		File fileName = null;
		int gid = 0;
		int result = 0;
		for (int i = 1; i < 4; i++) {
			fileNameStr = "image" + i + ".jpg";
			fileName = new File(PATH + fileNameStr);
			result = MstxDao.insertMstxAdsImage(fileName, gid);
			assertEquals(1, result);
		}

	}

	/**
	 * 添加分类
	 */
	@Test
	public void testInsertMstxSort() {
		String[] sortArray = new String[] { "肉类", "东北菜", "特色小吃", "清真", "粤菜", "火锅", "西餐", "酒类", "日本料理" };
		for (int i = 0; i < sortArray.length; i++) {
			int result = MstxDao.insertMstxSort(sortArray[i]);
			assertEquals(1, result);
		}
	}

	/**
	 * 插入头像
	 */
	@Test
	public void testInsertMstxHead() {
		String tdis = "头像";
		File fileName = null;
		int uid = 10;
		String PATH = "D:\\image\\face\\";
		String fileNameStr = "2-1.gif";
		boolean result = false;
		for (int i = 1; i < 7; i++) {
			fileNameStr = "2-" + i + ".gif";
			fileName = new File(PATH + fileNameStr);
			result = MstxDao.insertMstxHead(tdis, fileName, uid);
			System.out.println(result);
		}
	}

	/**
	 * 更新头像
	 */
	@Test
	public void testUpdateHeadFile() {
		File fileName = null;
		int tid = 0;
		String PATH = "D:\\image\\face\\";
		String fileNameStr = "2-1.gif";
		fileName = new File(PATH + fileNameStr);
		int result = MstxDao.updateHeadFile(tid, fileName);
		System.out.println(result);
	}

	/**
	 * 插入美食图片
	 */
	@Test
	public void testInsertMstxImage() {
		File fileName = null;
		String PATH = "D:\\image\\face\\";
		String fileNameStr = "";
		int result = 0;
		for (int i = 1; i < 3; i++) {
			fileNameStr = "food" + i + ".jpg";
			fileName = new File(PATH + fileNameStr);
			result = MstxDao.insertMstxImage(i, fileName, new Date(new java.util.Date().getTime()));
			System.out.println(result);
		}
	}

	@Test
	public void testGetMstxHeadFile() {
		String PATH = "D:\\image\\face\\";
		String fileName = "";

		boolean result = false;
		int tid = 26;
		File file = null;
		for (int x = 1; x < 7; x++) {
			fileName = PATH + "out_2-" + x + ".gif";
			tid++;
			file = MstxDao.getMstxHeadFile(tid, fileName);
			result = file.exists();
			assertEquals(true, result);
		}
	}

	@Test
	public void testGetMstxHeadFile2() {
		String PATH = "D:\\image\\face\\";
		String fileName = "";
		File file = null;
		boolean result = false;
		int tid = 29;
		fileName = PATH + "out_2-20.gif";
		file = MstxDao.getMstxHeadFile(tid, fileName);
		result = file.exists();
		assertEquals(true, result);
	}

	@Test
	public void testGetHeadImage() {
		int tid = 27;
		ArrayList<MstxHeadImage> mstxHeadImageList = MstxDao.getHeadImage(tid);
		assertEquals(1, mstxHeadImageList.size());
	}

	@Test
	public void testGetMstx_sort() {
		List<String[]> sortList = MstxDao.getMstxSort();
		for (int i = 0; i < sortList.size(); i++) {
			String[] sortArray = sortList.get(i);
			System.out.println(sortArray[0] + ":" + sortArray[1]);
		}
	}

	/**
	 * 增加推荐
	 */
	@Test
	public void testInsertMstxCol() {
		String mid = "1";
		String uid = "10";
		String comment = "好吃佬";

		int result = MstxDao.insertMstxCol(mid, uid, comment);
		assertEquals(1, result);
	}

	/**
	 * 删除推荐
	 */
	@Test
	public void testDeleteMstxCol() {
		String mid = "1";
		String uid = "10";

		int result = MstxDao.deleteMstxCol(mid, uid);
		assertEquals(1, result);
	}

	/**
	 * 
	 */
	@Test
	public void testGetMstxAdsGpid() {
		int gid = 0;
		List<Integer> gpidList = MstxDao.getMstxAdsGpid(gid);
		for (Integer integer : gpidList) {
			System.out.println(integer);
		}
	}

	/**
	 * 得到所有广告位置的编号
	 */
	@Test
	public void testGetMSTXAdsID() {
		List<Integer> gpidList = MstxDao.getMSTXAdsID();
		for (Integer integer : gpidList) {
			System.out.println(integer);
		}
	}

	/**
	 * 根据gpid得到广告图片
	 */
	@Test
	public void testGetMstxAdsImageByGpid() {
		int gpid = 0;
		Blob blob = MstxDao.getMstxAdsImageByGpid(gpid);

		InputStream is = null;

		try {
			is = new BufferedInputStream(blob.getBinaryStream());

			byte[] buf = new byte[1024];
			int len = 0;

			String PATH = "D:\\image\\face\\";
			String fileName = PATH + "output_Header.jpg";
			File file = new File(fileName);
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
		}
	}

	/**
	 * 得到所有头像的id
	 */
	@Test
	public void testGetHeadNumber() {
		List<Integer> gpidList = MstxDao.getHeadNumber();
		for (Integer integer : gpidList) {
			System.out.println(integer);
		}
	}

	/**
	 * 更新最大值
	 */
	@Test
	public void testUpdateMaxNumber() {
		int what = 3;
		int result = MstxDao.updateMaxNumber(what);
		assertEquals(1, result);
	}

}