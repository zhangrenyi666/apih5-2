package com.apih5.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.health.ApplicationHealthIndicator;
import org.springframework.stereotype.Component;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.service.MenuService;

import cn.hutool.core.util.StrUtil;

/**
 * 启动容器以后，将数据库的sys_menu表中的系统管理、网站监控、服务可用性监控的host和port设置正确
 */
@Component
public class Apih5CommandLineRunner implements CommandLineRunner {

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(String... strings) throws Exception {
//        menuService.updateUrl("http://" + serverAddress + ":" + serverPort);
//    	if(Apih5Properties.getWebUrl().indexOf("weixin.fheb.cn") >= 0) {
//    		HttpUtil.sendPostJson(Apih5Properties.getWebUrl() + "getDpOperatingIndicatorsList","");
//    	}
    }
}
