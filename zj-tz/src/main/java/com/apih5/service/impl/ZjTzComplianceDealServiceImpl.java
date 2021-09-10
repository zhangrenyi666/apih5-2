package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzComplianceBaseMapper;
import com.apih5.mybatis.dao.ZjTzComplianceDealMapper;
import com.apih5.mybatis.dao.ZjTzComplianceDetailMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzComplianceBase;
import com.apih5.mybatis.pojo.ZjTzComplianceDeal;
import com.apih5.mybatis.pojo.ZjTzComplianceDetail;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.service.ZjTzComplianceDealService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzComplianceDealService")
public class ZjTzComplianceDealServiceImpl implements ZjTzComplianceDealService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzComplianceDealMapper zjTzComplianceDealMapper;
    
    @Autowired(required = true)
    private ZjTzComplianceDetailMapper zjTzComplianceDetailMapper;
    
    @Autowired(required = true)
    private ZjTzComplianceBaseMapper zjTzComplianceBaseMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzComplianceDealListByCondition(ZjTzComplianceDeal zjTzComplianceDeal) {
        if (zjTzComplianceDeal == null) {
            zjTzComplianceDeal = new ZjTzComplianceDeal();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzComplianceDeal.getProjectId(), true)) {
            	zjTzComplianceDeal.setProjectId("");
            	zjTzComplianceDeal.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzComplianceDeal.getProjectId(), true)) {
            	zjTzComplianceDeal.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzComplianceDeal.getPage(),zjTzComplianceDeal.getLimit());
        // 获取数据 
        List<ZjTzComplianceDeal> zjTzComplianceDealList = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(zjTzComplianceDeal);
   //列表页面列出前7个步骤进行预警，设置预警规则，==预警时间根据当前日期和应办理完成日期进行对比
        //绿色表示正常进行中， 
        //黄色表示距离办理时间还有15天，
        //红色表示应办理完成日期超出时间规定。
        for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
        	ZjTzComplianceDetail detailSelect = new ZjTzComplianceDetail();
        	detailSelect.setComplianceDealId(zjTzComplianceDeal2.getComplianceDealId());
        	
        	detailSelect.setNum("001");
        	detailSelect.setComplianceBanseName("工程可行性研究报告");
        	List<ZjTzComplianceDetail> detailSelectList1 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList1 != null && detailSelectList1.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList1.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase1("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase1("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase1("green");
        		}
			}
//        	List<ZjTzComplianceDetail> detailSelectList1 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList1 != null && detailSelectList1.size() >0) {
//        	 	if(StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList1.get(0).getShouldFinishDate() != null) {
//            			if(DateUtil.between(detailSelectList1.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList1.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList1.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList1.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList1.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList1.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList1.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase1(detailSelectList1.get(0).getColourFlag());
//        	}
        	detailSelect.setNum("002");
        	detailSelect.setComplianceBanseName("工可批复或项目核准");
        	List<ZjTzComplianceDetail> detailSelectList2 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList2 != null && detailSelectList2.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList2.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase2("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase2("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase2("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList2 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList2 != null && detailSelectList2.size() >0) {
//        		if(StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList2.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList2.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList2.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList2.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList2.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList2.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList2.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList2.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase2(detailSelectList2.get(0).getColourFlag());
//        	}
        	
        	detailSelect.setNum("003");
        	detailSelect.setComplianceBanseName("初设批复");
        	List<ZjTzComplianceDetail> detailSelectList3 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList3 != null && detailSelectList3.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList3.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase3("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase3("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase3("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList3 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList3 != null && detailSelectList3.size() >0) {
//        		if(StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList3.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList3.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList3.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList3.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList3.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList3.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList3.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList3.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase3(detailSelectList3.get(0).getColourFlag());
//        	}
        	
        	detailSelect.setNum("004");
        	detailSelect.setComplianceBanseName("施工图设计批复");
        	List<ZjTzComplianceDetail> detailSelectList4 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList4 != null && detailSelectList4.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList4.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase4("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase4("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase4("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList4 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList4 != null && detailSelectList4.size() >0) {
//        		if(StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList4.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList4.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList4.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList4.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList4.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList4.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList4.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList4.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase4(detailSelectList4.get(0).getColourFlag());
//        	}
        	
        	detailSelect.setNum("005");
        	detailSelect.setComplianceBanseName("用地批复");
        	List<ZjTzComplianceDetail> detailSelectList5 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList5 != null && detailSelectList5.size() >0) {
        	ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList5.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase5("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase5("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase5("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList5 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList5 != null && detailSelectList5.size() >0) {
//        		if(StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList5.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList5.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList5.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList5.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList5.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList5.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList5.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList5.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase5(detailSelectList5.get(0).getColourFlag());
//        	}
        	
        	detailSelect.setNum("006");
        	detailSelect.setComplianceBanseName("施工许可证");
        	List<ZjTzComplianceDetail> detailSelectList6 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList6 != null && detailSelectList6.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList6.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase6("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase6("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase6("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList6 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList6 != null && detailSelectList6.size() >0) {
//        		if(StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList6.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList6.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList6.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList6.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList6.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList6.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList6.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList6.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase6(detailSelectList6.get(0).getColourFlag());
//        	}
        	
        	detailSelect.setNum("007");
        	detailSelect.setComplianceBanseName("融资协议");
        	List<ZjTzComplianceDetail> detailSelectList7 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList7 != null && detailSelectList7.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList7.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase7("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase7("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase7("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList7 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList7 != null && detailSelectList7.size() >0) {
//        		if(StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList7.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList7.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList7.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList7.get(0).getShouldFinishDate(),new Date()) <0) {
//                			detailSelectList7.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList7.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList7.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList7.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase7(detailSelectList7.get(0).getColourFlag());
//        	}
        	
        	detailSelect.setNum("008");
        	detailSelect.setComplianceBanseName("环评批复");
        	List<ZjTzComplianceDetail> detailSelectList8 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
        	if (detailSelectList8 != null && detailSelectList8.size() >0) {
        		ZjTzComplianceDetail dbZjTzComplianceDetail = showComplianceDealBase(detailSelectList8.get(0));
        		if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "red") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "red")) {
        			zjTzComplianceDeal2.setBase8("red");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "yellow") || StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "yellow")) {
        			zjTzComplianceDeal2.setBase8("yellow");
        		}else if (StrUtil.equals(dbZjTzComplianceDetail.getColourFlag(), "green") && StrUtil.equals(dbZjTzComplianceDetail.getColourFlag1(), "green")) {
        			zjTzComplianceDeal2.setBase8("green");
        		}
        	}
//        	List<ZjTzComplianceDetail> detailSelectList8 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
//        	if(detailSelectList8 != null && detailSelectList8.size() >0) {
//        		if(StrUtil.equals(detailSelectList8.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList8.get(0).getDealSituation(), "1")) {
//            		if(detailSelectList8.get(0).getShouldFinishDate() != null) {
//                		if(DateUtil.between(detailSelectList8.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//                			detailSelectList8.get(0).setColourFlag("yellow");
//                 		}else if(DateUtil.compare(detailSelectList8.get(0).getShouldFinishDate(),new Date()) <0) {
//                 			detailSelectList8.get(0).setColourFlag("red");
//                 		}else {
//                 			detailSelectList8.get(0).setColourFlag("green");
//                 		}
//                	}else {
//                		detailSelectList8.get(0).setColourFlag("green");
//                	}
//            	}else {
//            		detailSelectList8.get(0).setColourFlag("green");
//            	}
//        		zjTzComplianceDeal2.setBase8(detailSelectList8.get(0).getColourFlag());
//        	}
        	
        }
        
        for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
			if(StrUtil.equals(zjTzComplianceDeal2.getBase1(), "red") 
			|| StrUtil.equals(zjTzComplianceDeal2.getBase2(), "red")
			|| StrUtil.equals(zjTzComplianceDeal2.getBase3(), "red")
			|| StrUtil.equals(zjTzComplianceDeal2.getBase4(), "red")
			|| StrUtil.equals(zjTzComplianceDeal2.getBase5(), "red")
			|| StrUtil.equals(zjTzComplianceDeal2.getBase6(), "red")
			|| StrUtil.equals(zjTzComplianceDeal2.getBase7(), "red")
			|| StrUtil.equals(zjTzComplianceDeal2.getBase8(), "red")
					) {
				zjTzComplianceDeal2.setWarnFlag("red");
				
			}
		}
        
        // 得到分页信息
        PageInfo<ZjTzComplianceDeal> p = new PageInfo<>(zjTzComplianceDealList);

        return repEntity.okList(zjTzComplianceDealList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzComplianceDealDetails(ZjTzComplianceDeal zjTzComplianceDeal) {
        if (zjTzComplianceDeal == null) {
            zjTzComplianceDeal = new ZjTzComplianceDeal();
        }
        // 获取数据
        ZjTzComplianceDeal dbZjTzComplianceDeal = zjTzComplianceDealMapper.selectByPrimaryKey(zjTzComplianceDeal.getComplianceDealId());
        // 数据存在
        if (dbZjTzComplianceDeal != null) {
            return repEntity.ok(dbZjTzComplianceDeal);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzComplianceDeal(ZjTzComplianceDeal zjTzComplianceDeal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
    	ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzComplianceDeal.getProjectId());
        //一、项目id和子项目id   共同判断
        ZjTzComplianceDeal ComplianceDeal = new ZjTzComplianceDeal();
        ComplianceDeal.setProjectId(zjTzComplianceDeal.getProjectId());
      	if(StrUtil.isNotEmpty(zjTzComplianceDeal.getSubprojectInfoId())) {
             ComplianceDeal.setSubprojectInfoId(zjTzComplianceDeal.getSubprojectInfoId());
             ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzComplianceDeal.getSubprojectInfoId());
             if(info != null && StrUtil.isNotEmpty(info.getSubprojectInfoId())) {
            	 zjTzComplianceDeal.setSubprojectName(info.getSubprojectName());
             }
             List<ZjTzComplianceDeal> ComplianceDealList =  zjTzComplianceDealMapper.selectByZjTzComplianceDealList(ComplianceDeal);
             if(ComplianceDealList != null && ComplianceDealList.size() >0) {
             	return repEntity.layerMessage("no", "该项目已经添加，请重新选择！！");
             }
      	}else {
      		//二、不填子项目的  = 用项目id判断
      		ComplianceDeal.setSubprojectInfoId("无");
      		List<ZjTzComplianceDeal> sizeControls = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(ComplianceDeal);
      		if(sizeControls != null && sizeControls.size() >0) {
      			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
      		}
      		zjTzComplianceDeal.setSubprojectInfoId("无");
      	}
      	zjTzComplianceDeal.setProjectName(manage.getProjectName());
        zjTzComplianceDeal.setComplianceDealId(UuidUtil.generate());
        zjTzComplianceDeal.setCreateUserInfo(userKey, realName);
        flag = zjTzComplianceDealMapper.insert(zjTzComplianceDeal);
        //查项目的完成时间company2
        
        	if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        		List<ZjTzComplianceBase> baseList = zjTzComplianceBaseMapper.selectByZjTzComplianceBaseList(new ZjTzComplianceBase());
        		if(baseList != null && baseList.size() >0) {
        			for (ZjTzComplianceBase baseSelect : baseList) {
        				ZjTzComplianceDetail detail = new ZjTzComplianceDetail();
        				detail.setComplianceDetailId(UuidUtil.generate());
        				detail.setComplianceDealId(zjTzComplianceDeal.getComplianceDealId());
        				detail.setNum(baseSelect.getNum());
        				detail.setComplianceBanseName(baseSelect.getComplianceBanseName());
        				detail.setDealFlag("0");
        				detail.setCreateUserInfo(userKey, realName);
        				flag = zjTzComplianceDetailMapper.insert(detail);
        			}
        		}
        	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzComplianceDeal);
        }
    }

    @Override
    public ResponseEntity updateZjTzComplianceDeal(ZjTzComplianceDeal zjTzComplianceDeal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzComplianceDeal dbzjTzComplianceDeal = zjTzComplianceDealMapper.selectByPrimaryKey(zjTzComplianceDeal.getComplianceDealId());
        if (dbzjTzComplianceDeal != null && StrUtil.isNotEmpty(dbzjTzComplianceDeal.getComplianceDealId())) {
//           // 项目id
//           dbzjTzComplianceDeal.setProjectId(zjTzComplianceDeal.getProjectId());
//           // 项目name
//           dbzjTzComplianceDeal.setProjectName(zjTzComplianceDeal.getProjectName());
//           // 子项目id
//           dbzjTzComplianceDeal.setSubprojectInfoId(zjTzComplianceDeal.getSubprojectInfoId());
//           // 子项目name
//           dbzjTzComplianceDeal.setSubprojectName(zjTzComplianceDeal.getSubprojectName());
           // 项目公司成立日期
           dbzjTzComplianceDeal.setEstablishDate(zjTzComplianceDeal.getEstablishDate());
           // 备注
           dbzjTzComplianceDeal.setRemarks(zjTzComplianceDeal.getRemarks());
           // 环节1
           dbzjTzComplianceDeal.setBase1(zjTzComplianceDeal.getBase1());
           // 环节2
           dbzjTzComplianceDeal.setBase2(zjTzComplianceDeal.getBase2());
           // 环节3
           dbzjTzComplianceDeal.setBase3(zjTzComplianceDeal.getBase3());
           // 环节4
           dbzjTzComplianceDeal.setBase4(zjTzComplianceDeal.getBase4());
           // 环节5
           dbzjTzComplianceDeal.setBase5(zjTzComplianceDeal.getBase5());
           // 环节6
           dbzjTzComplianceDeal.setBase6(zjTzComplianceDeal.getBase6());
           // 环节7
           dbzjTzComplianceDeal.setBase7(zjTzComplianceDeal.getBase7());
           // 共通
           dbzjTzComplianceDeal.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceDealMapper.updateByPrimaryKey(dbzjTzComplianceDeal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzComplianceDeal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzComplianceDeal(List<ZjTzComplianceDeal> zjTzComplianceDealList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzComplianceDealList != null && zjTzComplianceDealList.size() > 0) {
    		for (ZjTzComplianceDeal zjTzComplianceDeal : zjTzComplianceDealList) {
    			ZjTzComplianceDetail delDetail = new ZjTzComplianceDetail();
    			delDetail.setComplianceDealId(zjTzComplianceDeal.getComplianceDealId());
    			List<ZjTzComplianceDetail> delDetails = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(delDetail);
    			if(delDetails != null && delDetails.size() >0) {
    				for (ZjTzComplianceDetail delDetailFile : delDetails) {
    					ZjTzFile delFile = new ZjTzFile();
    					delFile.setOtherId(delDetailFile.getComplianceDetailId());
    					List<ZjTzFile> delFileList = zjTzFileMapper.selectByZjTzFileList(delFile);
    					if(delFileList != null && delFileList.size() >0) {
    						delFile.setModifyUserInfo(userKey, realName);
    						flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(delFileList, delFile);
    					}
    				}
    				delDetail.setModifyUserInfo(userKey, realName);
    				flag = zjTzComplianceDetailMapper.batchDeleteUpdateZjTzComplianceDetail(delDetails, delDetail);
    			}
    		}
    		ZjTzComplianceDeal zjTzComplianceDeal = new ZjTzComplianceDeal();
    		zjTzComplianceDeal.setModifyUserInfo(userKey, realName);
    		flag = zjTzComplianceDealMapper.batchDeleteUpdateZjTzComplianceDeal(zjTzComplianceDealList, zjTzComplianceDeal);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzComplianceDealList);
    	}
    }

    @Override
    public ResponseEntity saveZjTzComplianceDealAllDetail(ZjTzComplianceDeal zjTzComplianceDeal) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzComplianceDeal != null && StrUtil.isNotEmpty(zjTzComplianceDeal.getComplianceDealId())) {
    		if (zjTzComplianceDeal.getZjTzComplianceDetailList() != null && zjTzComplianceDeal.getZjTzComplianceDetailList().size() >0) {
    			//先删除再新增
    			ZjTzComplianceDetail delDeduct = new ZjTzComplianceDetail();
    			delDeduct.setComplianceDealId(zjTzComplianceDeal.getComplianceDealId());
    			List<ZjTzComplianceDetail> delRelpyList = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(delDeduct);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				for (ZjTzComplianceDetail zjTzComplianceDetail2 : delRelpyList) {
    					// 删除附件再新增
    					ZjTzFile zjTzFileSelect = new ZjTzFile();
    					zjTzFileSelect.setOtherId(zjTzComplianceDetail2.getComplianceDetailId());
    					List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    					if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    						zjTzFileSelect.setModifyUserInfo(userKey, realName);
    						zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    					}
    				}
    				delDeduct.setModifyUserInfo(userKey, realName);
    				flag = zjTzComplianceDetailMapper.batchDeleteUpdateZjTzComplianceDetail(delRelpyList, delDeduct);
    			}
    			//新增之前先遍历出来是哪个环节选中了项目成立时间
    			String dealFlagNum = "";
    			for (ZjTzComplianceDetail addDetail : zjTzComplianceDeal.getZjTzComplianceDetailList()) {
    				if(StrUtil.equals(addDetail.getDealFlag(), "1")) {
    					dealFlagNum = addDetail.getNum();
    					break;
    				}
				}
    			//新增
    			for (ZjTzComplianceDetail addDetail : zjTzComplianceDeal.getZjTzComplianceDetailList()) {
    				ZjTzComplianceDetail dbzjTzComplianceDetail = new ZjTzComplianceDetail();
    				// 主键id
    				dbzjTzComplianceDetail.setComplianceDetailId(UuidUtil.generate());
    				// id
    				dbzjTzComplianceDetail.setComplianceDealId(zjTzComplianceDeal.getComplianceDealId());
    				// 环节编号
    				dbzjTzComplianceDetail.setNum(addDetail.getNum());
    				//项目公司成立时应办理环节
    				dbzjTzComplianceDetail.setDealFlag(addDetail.getDealFlag());
    				// 合规环节名称
    				dbzjTzComplianceDetail.setComplianceBanseName(addDetail.getComplianceBanseName());
    				
    				ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzComplianceDeal.getProjectId());
    				if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
    					//应办理完成日期的规则如下：
        				//项目时间
        				//项目时间+2
        				//项目时间+2+4
        				//项目时间+2+4+4
        				//项目时间+2+4+4+1
        				//项目时间+2+4+4+1+1
        				//项目时间+2+4+4+1+1+1
        				if(manage.getCompany2() != null) {
        					if(StrUtil.equals(dealFlagNum, "001") || StrUtil.equals(dealFlagNum, "002")) {
        						if(StrUtil.equals(addDetail.getNum(), "001")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "002")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 2));
        						}else if(StrUtil.equals(addDetail.getNum(), "003")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 6));
        						}else if(StrUtil.equals(addDetail.getNum(), "004")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 10));
        						}else if(StrUtil.equals(addDetail.getNum(), "005")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 11));
        						}else if(StrUtil.equals(addDetail.getNum(), "006")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 12));
        						}else if(StrUtil.equals(addDetail.getNum(), "007")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 13));
        						}
        					}else if(StrUtil.equals(dealFlagNum, "003")) {
        						if(StrUtil.equals(addDetail.getNum(), "001")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "002")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "003")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 4));
        						}else if(StrUtil.equals(addDetail.getNum(), "004")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 8));
        						}else if(StrUtil.equals(addDetail.getNum(), "005")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 9));
        						}else if(StrUtil.equals(addDetail.getNum(), "006")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 10));
        						}else if(StrUtil.equals(addDetail.getNum(), "007")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 11));
        						}
        					}else if(StrUtil.equals(dealFlagNum, "004")) {
        						if(StrUtil.equals(addDetail.getNum(), "001")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "002")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "003")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "004")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 4));
        						}else if(StrUtil.equals(addDetail.getNum(), "005")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 5));
        						}else if(StrUtil.equals(addDetail.getNum(), "006")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 6));
        						}else if(StrUtil.equals(addDetail.getNum(), "007")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 7));
        						}
        					}else if(StrUtil.equals(dealFlagNum, "005")) {
        						if(StrUtil.equals(addDetail.getNum(), "001")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "002")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "003")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "004")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "005")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 1));
        						}else if(StrUtil.equals(addDetail.getNum(), "006")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 2));
        						}else if(StrUtil.equals(addDetail.getNum(), "007")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 3));
        						}
        					}else if(StrUtil.equals(dealFlagNum, "006")) {
        						if(StrUtil.equals(addDetail.getNum(), "001")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "002")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "003")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "004")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "005")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "006")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 1));
        						}else if(StrUtil.equals(addDetail.getNum(), "007")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 2));
        						}
        					}else if(StrUtil.equals(dealFlagNum, "007")) {
        						if(StrUtil.equals(addDetail.getNum(), "001")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "002")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "003")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "004")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "005")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "006")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(null);
        						}else if(StrUtil.equals(addDetail.getNum(), "007")) {
        							dbzjTzComplianceDetail.setShouldFinishDate(DateUtil.offset(manage.getCompany2(), DateField.MONTH, 1));
        						}
        					}
        				}
    				}
    				// 文件批复日期
     				dbzjTzComplianceDetail.setApprovalDate(addDetail.getApprovalDate());
    				// 办理情况
    				dbzjTzComplianceDetail.setDealSituation(addDetail.getDealSituation());
    				//策划批复应办理完结日期
    				dbzjTzComplianceDetail.setApprovalShouldFinishDate(addDetail.getApprovalShouldFinishDate());
    				//备注
    				dbzjTzComplianceDetail.setBz(addDetail.getBz());
    				//是否锁定
    				dbzjTzComplianceDetail.setLockFlag(addDetail.getLockFlag());
    				//是否是新增的数据
    				dbzjTzComplianceDetail.setAddFlag(addDetail.getAddFlag());
    				// 附件list
    				List<ZjTzFile> zjTzFileList = addDetail.getZjTzFileList();
    				if(zjTzFileList != null && zjTzFileList.size()>0) {
    					for(ZjTzFile zjTzFile:zjTzFileList) {
    						zjTzFile.setUid(UuidUtil.generate());
    						zjTzFile.setOtherId(dbzjTzComplianceDetail.getComplianceDetailId());
    						zjTzFile.setCreateUserInfo(userKey, realName);
    						zjTzFileMapper.insert(zjTzFile);
    					}
    				}
    				// 共通
    				dbzjTzComplianceDetail.setCreateUserInfo(userKey, realName);
    				flag = zjTzComplianceDetailMapper.insert(dbzjTzComplianceDetail);
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzComplianceDeal);
    	}
    }

	@Override
	public List<ZjTzComplianceDeal> uReportZjTzComplianceDealList(ZjTzComplianceDeal zjTzComplianceDeal) {
		if (zjTzComplianceDeal == null) {
			zjTzComplianceDeal = new ZjTzComplianceDeal();
		}

		List<ZjTzComplianceDeal> zjTzComplianceDealList = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(zjTzComplianceDeal);
		return zjTzComplianceDealList;
	}
	
	/**
	 * 合规列表外层预警
	 * 
	 */
	private ZjTzComplianceDetail showComplianceDealBase(ZjTzComplianceDetail zjTzComplianceDetail) {
			//制度时限预警
			if (StrUtil.isNotEmpty(zjTzComplianceDetail.getDealSituation())) {
				if (StrUtil.equals(zjTzComplianceDetail.getDealSituation(), "0") || StrUtil.equals(zjTzComplianceDetail.getDealSituation(), "1")) {
					if (zjTzComplianceDetail.getShouldFinishDate() != null) {
						if (zjTzComplianceDetail.getShouldFinishDate().compareTo(new Date()) > 0 && DateUtil.between(new Date(), zjTzComplianceDetail.getShouldFinishDate(), DateUnit.DAY) >= 15) {
							zjTzComplianceDetail.setColourFlag("green");
						}else if (zjTzComplianceDetail.getShouldFinishDate().compareTo(new Date()) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail.getShouldFinishDate(), DateUnit.DAY) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail.getShouldFinishDate(), DateUnit.DAY) < 15) {
							zjTzComplianceDetail.setColourFlag("yellow");
						}else if (zjTzComplianceDetail.getShouldFinishDate().compareTo(new Date()) < 0) {
							zjTzComplianceDetail.setColourFlag("red");
						}
					}else {
						zjTzComplianceDetail.setColourFlag("red");
					}
				}else {
					zjTzComplianceDetail.setColourFlag("green");
				}
			}else {
				if (StrUtil.equals(zjTzComplianceDetail.getNum(), "008")) {
					zjTzComplianceDetail.setColourFlag("red");
				}else {
					if (zjTzComplianceDetail.getShouldFinishDate() != null) {
						zjTzComplianceDetail.setColourFlag("red");
					}else {
						zjTzComplianceDetail.setColourFlag("green");
					}
				}
			}
			//策划时限预警
			if (StrUtil.isNotEmpty(zjTzComplianceDetail.getDealSituation())) {
				if (StrUtil.equals(zjTzComplianceDetail.getDealSituation(), "0") || StrUtil.equals(zjTzComplianceDetail.getDealSituation(), "1")) {
					if (zjTzComplianceDetail.getApprovalShouldFinishDate() != null) {
						if (zjTzComplianceDetail.getApprovalShouldFinishDate().compareTo(new Date()) > 0 && DateUtil.between(new Date(), zjTzComplianceDetail.getApprovalShouldFinishDate(), DateUnit.DAY) >= 15) {
							zjTzComplianceDetail.setColourFlag1("green");
						}else if (zjTzComplianceDetail.getApprovalShouldFinishDate().compareTo(new Date()) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail.getApprovalShouldFinishDate(), DateUnit.DAY) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail.getApprovalShouldFinishDate(), DateUnit.DAY) < 15) {
							zjTzComplianceDetail.setColourFlag1("yellow");
						}else if (zjTzComplianceDetail.getApprovalShouldFinishDate().compareTo(new Date()) < 0) {
							zjTzComplianceDetail.setColourFlag1("red");
						}
					}else {
						zjTzComplianceDetail.setColourFlag1("red");
					}
				}else {
					zjTzComplianceDetail.setColourFlag1("green");
				}
			}else {
				if (StrUtil.equals(zjTzComplianceDetail.getNum(), "008")) {
					zjTzComplianceDetail.setColourFlag1("red");
				}else {
					if (zjTzComplianceDetail.getApprovalShouldFinishDate() != null) {
						zjTzComplianceDetail.setColourFlag1("red");
					}else {
						zjTzComplianceDetail.setColourFlag1("green");
					}
				}
			}
//		}
		
		return zjTzComplianceDetail;
	}
}