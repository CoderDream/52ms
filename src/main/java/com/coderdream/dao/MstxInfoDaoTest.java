package com.coderdream.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.coderdream.bean.MstxInfo;

public class MstxInfoDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * 通过名字查找美食
	 */
	@Test
	public void testGetMstxInfoByName() {
		String name = "肉";
		int type = 1;
		int span = 1;
		int currentPageNo = 1;
		int where = 1;
		ArrayList<MstxInfo> mstxInfoList = MstxInfoDao.getMstxInfoByName(name, type, span, currentPageNo, where);
		assertEquals(1, mstxInfoList.size());
	}

	/**
	 * 获取每日推荐
	 */
	@Test
	public void testGetMstxRecommend() {
		ArrayList<MstxInfo> mstxInfoList = MstxInfoDao.getMstxRecommend();
		assertEquals(1, mstxInfoList.size());
	}

	/**
	 * 获取用户的收藏
	 */
	@Test
	public void testGetFavourite() {
		String uid = "11";
		ArrayList<MstxInfo> mstxInfoList = MstxInfoDao.getFavourite(uid);
		assertEquals(1, mstxInfoList.size());
	}

	@Test
	public void testInsertMstxInfo() {
		String info_title = "毛氏红烧肉";
		String info_dis = "三湘春的毛氏红烧肉";// 描述
		String info_lon = "37.566535";
		String info_lat = "126.977969";
		// 用户ID
		String uid = "11";
		String info_sort = "0";// 种类
		int info_price = 38;
		int where = 1;
		String info_hotel = "三湘春";
		int result = MstxInfoDao.insertMstxInfo(info_title, info_dis, info_lon, info_lat, uid, info_sort, info_price,
				where, info_hotel);
		assertEquals(1, result);
	}

	@Test
	public void testInsertMstxInfo2() {
		String info_title = "红烧肥肠";
		String info_dis = "三湘春的红烧肥肠";// 描述
		String info_lon = "37.566535";
		String info_lat = "126.977969";
		// 用户ID
		String uid = "11";
		String info_sort = "0";// 种类
		int info_price = 48;
		int where = 1;
		String info_hotel = "三湘春";
		int result = MstxInfoDao.insertMstxInfo(info_title, info_dis, info_lon, info_lat, uid, info_sort, info_price,
				where, info_hotel);
		assertEquals(3, result);
	}

	/**
	 * TODO
	 */
	@Test
	public void testGetMstxInfoForPhone() {
		fail("Not yet implemented");
	}

	/**
	 * TODO
	 */
	@Test
	public void testGetMstxInfoCountForPhone() {
		fail("Not yet implemented");
	}

	/**
	 * TODO
	 */
	@Test
	public void testGetMstxInfoCount() {
		int result = MstxInfoDao.getMstxInfoCount();
		assertEquals(2, result);
	}

}