package com.apih5.components;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Apih5Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("----本服务器已正式授权----");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("----本服务关闭，正在释放资源----");
    }
}
