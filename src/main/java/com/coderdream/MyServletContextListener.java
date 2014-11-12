package com.coderdream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
	ServerThread st = null;// 声明服务器线程的引用

	public void contextInitialized(ServletContextEvent sce) {// 重写的contextInitialized方法
		sce.getServletContext().log("[com.coderdream] Context Initialized...");
		System.out.println("[com.coderdream] Context Initialized...");
		st = new ServerThread();// 创建服务线程
		st.start();// 启动服务线程
	}

	public void contextDestroyed(ServletContextEvent sce) {// 重写的contextDestroyed方法
		sce.getServletContext().log("[com.coderdream] Context Destroyed...");
		System.out.println("[com.coderdream] Context Destroyed...");
		st.setFlag(false);// 停止服务线程
	}
}
