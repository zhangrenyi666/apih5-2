package com.apih5.framework.components;

import javax.servlet.Servlet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.bstek.ureport.console.UReportServlet;

/**
 * 配置ureport
 */

@ImportResource("classpath:ureport-console-context.xml")
@EnableAutoConfiguration
@Configuration
//@ComponentScan(basePackages = "com.apih5")
public class UReportConfiguration {

    @Bean
    public ServletRegistrationBean ureportServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
        return servletRegistrationBean;
    }

    @Bean
	public ServletRegistrationBean<Servlet> buildUreportServlet(){
		return new ServletRegistrationBean<Servlet>(new UReportServlet(), "/ureport/*");
	}
}
