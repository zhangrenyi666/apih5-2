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
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzComplianceDeal.getProjectId(), true)) {
            	zjTzComplianceDeal.setProjectId("");
            	zjTzComplianceDeal.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzComplianceDeal.getProjectId(), true)) {
            	zjTzComplianceDeal.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzComplianceDeal.getPage(),zjTzComplianceDeal.getLimit());
        // ???????????? 
        List<ZjTzComplianceDeal> zjTzComplianceDealList = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(zjTzComplianceDeal);
   //?????????????????????7?????????????????????????????????????????????==??????????????????????????????????????????????????????????????????
        //?????????????????????????????? 
        //????????????????????????????????????15??????
        //??????????????????????????????????????????????????????
        for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
        	ZjTzComplianceDetail detailSelect = new ZjTzComplianceDetail();
        	detailSelect.setComplianceDealId(zjTzComplianceDeal2.getComplianceDealId());
        	
        	detailSelect.setNum("001");
        	detailSelect.setComplianceBanseName("???????????????????????????");
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
        	detailSelect.setComplianceBanseName("???????????????????????????");
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
        	detailSelect.setComplianceBanseName("????????????");
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
        	detailSelect.setComplianceBanseName("?????????????????????");
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
        	detailSelect.setComplianceBanseName("????????????");
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
        	detailSelect.setComplianceBanseName("???????????????");
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
        	detailSelect.setComplianceBanseName("????????????");
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
        	detailSelect.setComplianceBanseName("????????????");
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
        
        // ??????????????????
        PageInfo<ZjTzComplianceDeal> p = new PageInfo<>(zjTzComplianceDealList);

        return repEntity.okList(zjTzComplianceDealList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzComplianceDealDetails(ZjTzComplianceDeal zjTzComplianceDeal) {
        if (zjTzComplianceDeal == null) {
            zjTzComplianceDeal = new ZjTzComplianceDeal();
        }
        // ????????????
        ZjTzComplianceDeal dbZjTzComplianceDeal = zjTzComplianceDealMapper.selectByPrimaryKey(zjTzComplianceDeal.getComplianceDealId());
        // ????????????
        if (dbZjTzComplianceDeal != null) {
            return repEntity.ok(dbZjTzComplianceDeal);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzComplianceDeal(ZjTzComplianceDeal zjTzComplianceDeal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
    	ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzComplianceDeal.getProjectId());
        //????????????id????????????id   ????????????
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
             	return repEntity.layerMessage("no", "?????????????????????????????????????????????");
             }
      	}else {
      		//????????????????????????  = ?????????id??????
      		ComplianceDeal.setSubprojectInfoId("???");
      		List<ZjTzComplianceDeal> sizeControls = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(ComplianceDeal);
      		if(sizeControls != null && sizeControls.size() >0) {
      			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
      		}
      		zjTzComplianceDeal.setSubprojectInfoId("???");
      	}
      	zjTzComplianceDeal.setProjectName(manage.getProjectName());
        zjTzComplianceDeal.setComplianceDealId(UuidUtil.generate());
        zjTzComplianceDeal.setCreateUserInfo(userKey, realName);
        flag = zjTzComplianceDealMapper.insert(zjTzComplianceDeal);
        //????????????????????????company2
        
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
//           // ??????id
//           dbzjTzComplianceDeal.setProjectId(zjTzComplianceDeal.getProjectId());
//           // ??????name
//           dbzjTzComplianceDeal.setProjectName(zjTzComplianceDeal.getProjectName());
//           // ?????????id
//           dbzjTzComplianceDeal.setSubprojectInfoId(zjTzComplianceDeal.getSubprojectInfoId());
//           // ?????????name
//           dbzjTzComplianceDeal.setSubprojectName(zjTzComplianceDeal.getSubprojectName());
           // ????????????????????????
           dbzjTzComplianceDeal.setEstablishDate(zjTzComplianceDeal.getEstablishDate());
           // ??????
           dbzjTzComplianceDeal.setRemarks(zjTzComplianceDeal.getRemarks());
           // ??????1
           dbzjTzComplianceDeal.setBase1(zjTzComplianceDeal.getBase1());
           // ??????2
           dbzjTzComplianceDeal.setBase2(zjTzComplianceDeal.getBase2());
           // ??????3
           dbzjTzComplianceDeal.setBase3(zjTzComplianceDeal.getBase3());
           // ??????4
           dbzjTzComplianceDeal.setBase4(zjTzComplianceDeal.getBase4());
           // ??????5
           dbzjTzComplianceDeal.setBase5(zjTzComplianceDeal.getBase5());
           // ??????6
           dbzjTzComplianceDeal.setBase6(zjTzComplianceDeal.getBase6());
           // ??????7
           dbzjTzComplianceDeal.setBase7(zjTzComplianceDeal.getBase7());
           // ??????
           dbzjTzComplianceDeal.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceDealMapper.updateByPrimaryKey(dbzjTzComplianceDeal);
        }
        // ??????
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
    	// ??????
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
    			//??????????????????
    			ZjTzComplianceDetail delDeduct = new ZjTzComplianceDetail();
    			delDeduct.setComplianceDealId(zjTzComplianceDeal.getComplianceDealId());
    			List<ZjTzComplianceDetail> delRelpyList = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(delDeduct);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				for (ZjTzComplianceDetail zjTzComplianceDetail2 : delRelpyList) {
    					// ?????????????????????
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
    			//?????????????????????????????????????????????????????????????????????
    			String dealFlagNum = "";
    			for (ZjTzComplianceDetail addDetail : zjTzComplianceDeal.getZjTzComplianceDetailList()) {
    				if(StrUtil.equals(addDetail.getDealFlag(), "1")) {
    					dealFlagNum = addDetail.getNum();
    					break;
    				}
				}
    			//??????
    			for (ZjTzComplianceDetail addDetail : zjTzComplianceDeal.getZjTzComplianceDetailList()) {
    				ZjTzComplianceDetail dbzjTzComplianceDetail = new ZjTzComplianceDetail();
    				// ??????id
    				dbzjTzComplianceDetail.setComplianceDetailId(UuidUtil.generate());
    				// id
    				dbzjTzComplianceDetail.setComplianceDealId(zjTzComplianceDeal.getComplianceDealId());
    				// ????????????
    				dbzjTzComplianceDetail.setNum(addDetail.getNum());
    				//????????????????????????????????????
    				dbzjTzComplianceDetail.setDealFlag(addDetail.getDealFlag());
    				// ??????????????????
    				dbzjTzComplianceDetail.setComplianceBanseName(addDetail.getComplianceBanseName());
    				
    				ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzComplianceDeal.getProjectId());
    				if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
    					//???????????????????????????????????????
        				//????????????
        				//????????????+2
        				//????????????+2+4
        				//????????????+2+4+4
        				//????????????+2+4+4+1
        				//????????????+2+4+4+1+1
        				//????????????+2+4+4+1+1+1
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
    				// ??????????????????
     				dbzjTzComplianceDetail.setApprovalDate(addDetail.getApprovalDate());
    				// ????????????
    				dbzjTzComplianceDetail.setDealSituation(addDetail.getDealSituation());
    				//?????????????????????????????????
    				dbzjTzComplianceDetail.setApprovalShouldFinishDate(addDetail.getApprovalShouldFinishDate());
    				//??????
    				dbzjTzComplianceDetail.setBz(addDetail.getBz());
    				//????????????
    				dbzjTzComplianceDetail.setLockFlag(addDetail.getLockFlag());
    				//????????????????????????
    				dbzjTzComplianceDetail.setAddFlag(addDetail.getAddFlag());
    				// ??????list
    				List<ZjTzFile> zjTzFileList = addDetail.getZjTzFileList();
    				if(zjTzFileList != null && zjTzFileList.size()>0) {
    					for(ZjTzFile zjTzFile:zjTzFileList) {
    						zjTzFile.setUid(UuidUtil.generate());
    						zjTzFile.setOtherId(dbzjTzComplianceDetail.getComplianceDetailId());
    						zjTzFile.setCreateUserInfo(userKey, realName);
    						zjTzFileMapper.insert(zjTzFile);
    					}
    				}
    				// ??????
    				dbzjTzComplianceDetail.setCreateUserInfo(userKey, realName);
    				flag = zjTzComplianceDetailMapper.insert(dbzjTzComplianceDetail);
    			}
    		}
    	}
    	// ??????
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
	 * ????????????????????????
	 * 
	 */
	private ZjTzComplianceDetail showComplianceDealBase(ZjTzComplianceDetail zjTzComplianceDetail) {
			//??????????????????
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
			//??????????????????
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