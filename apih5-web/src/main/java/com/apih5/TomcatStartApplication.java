package com.apih5;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableApolloConfig
public class TomcatStartApplication extends SpringBootServletInitializer {  
	  
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { 
        String path = Thread.currentThread().getContextClassLoader().getResource("")
                .getPath().replaceAll("/WEB-INF/classes/", "");
        System.setProperty("log.path", path);
        return builder.sources(Apih5Application.class);  
    }  
  
}  
