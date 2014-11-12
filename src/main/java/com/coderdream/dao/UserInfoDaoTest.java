package com.coderdream.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coderdream.bean.UserInfo;

public class UserInfoDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetUserByUid() {
		String uid = "12";
		UserInfo userInfo = UserInfoDao.getUserByUid(uid);
		String name = "lisi";
		System.out.println(userInfo);
		assertEquals(name, userInfo.getU_name());
	}

	/**
	 * 增加用户
	 */
	@Test
	public void testInsertUser() {
		String u_name = "lisi";
		String u_qq = "123456";
		String u_pwd = "1234";
		String u_Email = "123456@qq.com";
		String u_dis = "我是美食家！";
		int uid = MstxDao.getMaxNumber("mstx_user");
		int result = UserInfoDao.insertUser(u_name, u_qq, u_pwd, u_Email, u_dis);
		assertEquals(uid, result);
	}

	@Test
	public void testCheckUser() {
		String name = "张三";
		String uid = "11";
		String u_pwd = "1234";
		String result = UserInfoDao.checkUser(uid, u_pwd);
		assertEquals(name, result);
	}

	/**
	 * 更新头像
	 */
	@Test
	public void testUpdateUserHead() {
		int uid = 11;
		int u_head = 32;
		int result = UserInfoDao.updateUserHead(uid, u_head);
		assertEquals(1, result);
	}

	/**
	 * 更新用戶信息
	 */
	@Test
	public void testUpdateUserInfo() {
		String uid = "12";
		String u_name = "wangwu";
		String u_qq = "654321";
		String u_Email = "654321@qq.com";
		String u_favourite = "天下美食";
		String u_dis = "天生的美食家！";
		int result = UserInfoDao.updateUserInfo(uid, u_name, u_qq, u_Email, u_favourite, u_dis);
		assertEquals(1, result);
	}

	/**
	 * 是否为管理员
	 */
	@Test
	public void testIsAdmin() {
		String uid = "0";
		boolean result = UserInfoDao.isAdmin(uid);
		assertEquals(true, result);
	}

	@Test
	public void testIsAdmin2() {
		String uid = "11";
		boolean result = UserInfoDao.isAdmin(uid);
		assertEquals(false, result);
	}

}
