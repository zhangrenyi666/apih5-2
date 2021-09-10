package com.apih5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.apih5.framework.utils.LoggerUtils;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import cn.hutool.core.util.StrUtil;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.apih5.mybatis.dao")
// @EnableCaching // 开启缓存，需要显示的指定
@EnableApolloConfig
@ServletComponentScan
// 异步执行
@EnableAsync
public class Apih5Application {

	private static final Logger logger = LoggerFactory.getLogger(Apih5Application.class);

	public static void main(String[] args) {
		String version = SpringBootVersion.getVersion();
//		System.out.println(version);
		String implementationVersion = SpringApplication.class.getPackage().getImplementationVersion();

//		System.out.println(implementationVersion);

//		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		if (args != null && args.length > 0 && StrUtil.isNotEmpty(args[0])) {
			String path = System.getProperty("user.dir");
			String applicationTest = path + "/src/main/resources/application-test.yml";
			String dbconfig = path + "/src/main/resources/workflow-conf/properties/dbconfig.properties";
			String quartz = path + "/src/main/resources/quartz.properties";
			write(applicationTest, read(applicationTest, args[0]));
			write(dbconfig, read(dbconfig, args[0]));
			write(quartz, read(quartz, args[0]));

			String targetApplicationTest = path + "/target/classes/application-test.yml";
			String targetDbconfig = path + "/target/classes/workflow-conf/properties/dbconfig.properties";
			String targetQuartz = path + "/target/classes/quartz.properties";
			write(targetApplicationTest, read(applicationTest, args[0]));
			write(targetDbconfig, read(dbconfig, args[0]));
			write(targetQuartz, read(targetQuartz, args[0]));

			// appid
			if (args.length > 1) {
				String appid = path + "/src/main/resources/META-INF/app.properties";
				write(appid, readAppId(appid, args[1]));
			}
		}

		SpringApplication application = new SpringApplication(Apih5Application.class);
		application.setBannerMode(Banner.Mode.OFF);
		String path = Thread.currentThread().getContextClassLoader().getResource("")
                .getPath().replaceAll("/WEB-INF/classes/", "");
        System.setProperty("log.path", path);
		
//        // jar时，放开这个
//        System.setProperty("log.path", System.getProperty("user.dir"));

        application.run(args);
		LoggerUtils.printLogger(logger, "--------------APIH5 启动完成----------------");
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static String read(String filePath, String dbStr) {
		BufferedReader br = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((line = br.readLine()) != null) {
				// 此处根据实际需要修改某些行的内容
				if (line.indexOf("3306") > 0) {
					buf.append(line.replaceAll(StrUtil.subBetween(line, "3306/", "?"), dbStr));
				}
				// 如果不用修改, 则按原来的内容回写
				else {
					buf.append(line);
				}
				buf.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}
		return buf.toString();
	}

	public static String readAppId(String filePath, String dbStr) {
		BufferedReader br = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((line = br.readLine()) != null) {
				// 此处根据实际需要修改某些行的内容
				if (line.indexOf("app.id=") >= 0) {
					// buf.append(line.replaceAll(StrUtil.subBetween(line,
					// "3306/", "?"), dbStr));
					buf.append("app.id=" + dbStr);
				}
				// 如果不用修改, 则按原来的内容回写
				else {
					buf.append(line);
				}
				buf.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}
		return buf.toString();
	}

	/**
	 * 将内容回写到文件中
	 * 
	 * @param filePath
	 * @param content
	 */
	public static void write(String filePath, String content) {
		BufferedWriter bw = null;
		try {
			// 根据文件路径创建缓冲输出流
			bw = new BufferedWriter(new FileWriter(filePath));
			// 将内容写入文件中
			bw.write(content.replaceAll("\\\r\\\n", "\n"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}
}
