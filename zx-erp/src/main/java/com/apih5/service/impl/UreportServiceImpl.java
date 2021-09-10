package com.apih5.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSfPlanTargetMapper;
import com.apih5.mybatis.pojo.ZxSfPlanTarget;
import com.apih5.service.UreportService;
import com.apih5.service.ZxSfPlanTargetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 手动加载自定义配置文件
 */
/*@PropertySource(value = {
        "classpath:config/report-config.properties",
}, encoding = "utf-8")*/
@Service("ureportService")
public class UreportServiceImpl implements UreportService {

    @Autowired
    private Environment env;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Override
    public List ureportList(Map paramMap){

        //paramMap设置默认值，比如当前机构ID
        //todo 机构好了再设置

        String reportID = MapUtil.getStr(paramMap,"reportID");

        List<HashMap> mapList = new ArrayList<>();

        String reportConfig = this.env.getProperty(reportID);
        if(StrUtil.isEmpty(reportConfig)){
            reportConfig = "com.apih5.mybatis.dao.ureport."+reportID;
        }

        mapList = sqlSessionTemplate.getSqlSessionFactory().openSession().selectList(reportConfig,paramMap);

        return mapList;
    }
    @Override
    public Object ureportObject(Map paramMap){

        //paramMap设置默认值，比如当前机构ID
        //todo 机构好了再设置

        String reportID = MapUtil.getStr(paramMap,"reportID");

        String reportConfig = this.env.getProperty(reportID);
        if(StrUtil.isEmpty(reportConfig)){
            reportConfig = "com.apih5.mybatis.dao.ureport."+reportID;
        }

        Map map = sqlSessionTemplate.getSqlSessionFactory().openSession().selectOne(reportConfig,paramMap);

        return map;
    }
}
