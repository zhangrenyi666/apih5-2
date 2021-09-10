package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzDesignChangeMapper;
import com.apih5.mybatis.dao.ZjTzDesignChangeRecordMapper;
import com.apih5.mybatis.dao.ZjTzDesignChangeStatisticsMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzDesignChange;
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;
import com.apih5.mybatis.pojo.ZjTzDesignChangeStatistics;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.service.ZjTzDesignChangeService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDesignChangeService")
public class ZjTzDesignChangeServiceImpl implements ZjTzDesignChangeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzDesignChangeMapper zjTzDesignChangeMapper;

    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzDesignChangeRecordMapper zjTzDesignChangeRecordMapper;
    
    @Autowired(required = true)
    private ZjTzDesignChangeStatisticsMapper zjTzDesignChangeStatisticsMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzDesignChangeListByCondition(ZjTzDesignChange zjTzDesignChange) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzDesignChange == null) {
            zjTzDesignChange = new ZjTzDesignChange();
        }
        // 分页查询
        PageHelper.startPage(zjTzDesignChange.getPage(),zjTzDesignChange.getLimit());
     // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzDesignChange.getProjectId(), true)) {
            	zjTzDesignChange.setProjectId("");
            	zjTzDesignChange.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzDesignChange.getProjectId(), true)) {
            	zjTzDesignChange.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzDesignChange> zjTzDesignChangeList = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(zjTzDesignChange);
        for (ZjTzDesignChange zjTzDesignChange2 : zjTzDesignChangeList) {
            ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
            zjTzFileContractConditionSelect.setOtherId(zjTzDesignChange2.getDesignChangeId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
            zjTzDesignChange2.setZjTzFileList(zjTzFileList);
            // 主表中仅汇总子表中已上报的数据判断标识
            int changeNum = 0;
            BigDecimal changeAmount = new BigDecimal("0");
            
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            //先去查一下记录表
            ZjTzDesignChangeRecord record = new ZjTzDesignChangeRecord();
        	record.setDesignChangeId(zjTzDesignChange2.getDesignChangeId());
        	List<ZjTzDesignChangeRecord> records = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(record);
        	if(records != null &&records.size() > 0) {
        		for (ZjTzDesignChangeRecord zjTzDesignChangeRecord2 : records) {
        			if (StrUtil.equals(zjTzDesignChangeRecord2.getStatusId(), "1")) {
        				changeNum = changeNum + 1 ;
        				changeAmount = CalcUtils.calcAdd(changeAmount, zjTzDesignChangeRecord2.getChangeAmount());
					}
        			if (StrUtil.equals(zjTzDesignChangeRecord2.getRenew1(), "0")) {
						count1 = count1 + 1;
					}else if (StrUtil.equals(zjTzDesignChangeRecord2.getRenew2(), "0")) {
						count2 = count2 + 1;
					}else if (StrUtil.equals(zjTzDesignChangeRecord2.getRenew3(), "0")) {
						count3 = count3 + 1;
					}else if (StrUtil.equals(zjTzDesignChangeRecord2.getRenew4(), "0")) {
						count4 = count4 + 1;
					}
        			
    			}
        	}
        	if (count1 >= 1) {
        		zjTzDesignChange2.setRenew1("0");
            }else if (count2 >= 1) {
            	zjTzDesignChange2.setRenew2("0");
            }else if (count3 >= 1) {
            	zjTzDesignChange2.setRenew3("0");
            }else if (count4 >= 1) {
            	zjTzDesignChange2.setRenew4("0");
            }
        	zjTzDesignChange2.setChangeNum(changeNum);
        	zjTzDesignChange2.setChangeAmount(changeAmount);
		}
        // 得到分页信息
        PageInfo<ZjTzDesignChange> p = new PageInfo<>(zjTzDesignChangeList);

        return repEntity.okList(zjTzDesignChangeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignChangeDetails(ZjTzDesignChange zjTzDesignChange) {
        if (zjTzDesignChange == null) {
            zjTzDesignChange = new ZjTzDesignChange();
        }
        // 获取数据
        ZjTzDesignChange dbZjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzDesignChange.getDesignChangeId());
        // 数据存在
        if (dbZjTzDesignChange != null) {
        	ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
            zjTzFileContractConditionSelect.setOtherId(dbZjTzDesignChange.getDesignChangeId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
            dbZjTzDesignChange.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzDesignChange);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignChange(ZjTzDesignChange zjTzDesignChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
      //一、项目id和子项目id   共同判断
    	if(StrUtil.isNotEmpty(zjTzDesignChange.getSubprojectInfoId())) {
    		ZjTzDesignChange zjTzSizeControlSelect = new ZjTzDesignChange();
    		zjTzSizeControlSelect.setProjectId(zjTzDesignChange.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
    		List<ZjTzDesignChange> sizeControls = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
    		}
    		//子项目名称
    		ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignChange.getSubprojectInfoId());
    		if(info != null) {
    			zjTzDesignChange.setSubprojectName(info.getSubprojectName());
    		}
    	}else {
    		//二、不填子项目的  = 用项目id判断
    		ZjTzDesignChange zjTzSizeControlSelect = new ZjTzDesignChange();
    		zjTzSizeControlSelect.setProjectId(zjTzDesignChange.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId("无");
    		List<ZjTzDesignChange> sizeControls = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
    		}
    		zjTzDesignChange.setSubprojectInfoId("无");
    	}
        
        zjTzDesignChange.setDesignChangeId(UuidUtil.generate());
        //设计阶段
        if (StrUtil.isNotEmpty(zjTzDesignChange.getDesignStageId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignChange.getDesignStageId());
        	zjTzDesignChange.setDesignStageName(openBankName);
    	}
        //变更等级(1-6:A.B.C1.C2.D1.D2)
        if (StrUtil.isNotEmpty(zjTzDesignChange.getChangeLevelId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianGengDengJi", zjTzDesignChange.getChangeLevelId());
        	zjTzDesignChange.setChangeLevelName(openBankName);
        	
//        	if(zjTzDesignChange.getChangeAmount() != null) {
//        		if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "1")) {//500,+∞
//        			if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("500")) >=0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "变更增减金额超出范围，请按变更等级规定填写");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "2")) {//200,500
//            		if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("200")) >=0 && zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("500")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "变更增减金额超出范围，请按变更等级规定填写");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "3") || StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "4")) {//80,200
//            		if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("80")) >=0 && zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("200")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "变更增减金额超出范围，请按变更等级规定填写");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "5") || StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "6")) {//30,80
//            		if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("30")) >=0 && zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("80")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "变更增减金额超出范围，请按变更等级规定填写");
//            		}
//            	}	
//        	}
    	}
        //变更性质
        if (StrUtil.isNotEmpty(zjTzDesignChange.getChangeNatureId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianGengXingZhi", zjTzDesignChange.getChangeNatureId());
        	zjTzDesignChange.setChangeNatureName(openBankName);
    	}
        if (StrUtil.equals(zjTzDesignChange.getDynamicId(), "1")) {
        	zjTzDesignChange.setDynamicName("是");
    	}else {
    		zjTzDesignChange.setDynamicName("否");
    	}
        if (StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {
        	zjTzDesignChange.setInnerCheckName("是");
    	}else {
    		zjTzDesignChange.setInnerCheckName("否");
    	}
        if (StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {
        	zjTzDesignChange.setLegalName("是");
    	}else {
    		zjTzDesignChange.setLegalName("否");
    	}
        zjTzDesignChange.setChangeNum(1);
        zjTzDesignChange.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignChangeMapper.insert(zjTzDesignChange);
        List<ZjTzFile> zjTzFileList = zjTzDesignChange.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzDesignChange.getDesignChangeId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//add合同条件
    	ZjTzDesignChangeRecord dbzjTzDesignChangeRecord = new ZjTzDesignChangeRecord();
    	dbzjTzDesignChangeRecord.setDesignChangeRecordId(UuidUtil.generate());
    	 // 设计变更id
        dbzjTzDesignChangeRecord.setDesignChangeId(zjTzDesignChange.getDesignChangeId());
        // 项目id
        dbzjTzDesignChangeRecord.setProjectId(zjTzDesignChange.getProjectId());
        // 项目name
        dbzjTzDesignChangeRecord.setProjectName(zjTzDesignChange.getProjectName());
        // 子项目id
        dbzjTzDesignChangeRecord.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
        // 子项目name
        dbzjTzDesignChangeRecord.setSubprojectName(zjTzDesignChange.getSubprojectName());
        // 设计阶段id
        dbzjTzDesignChangeRecord.setDesignStageId(zjTzDesignChange.getDesignStageId());
        // 设计阶段name
        dbzjTzDesignChangeRecord.setDesignStageName(zjTzDesignChange.getDesignStageName());
        // 设计变更编号
        dbzjTzDesignChangeRecord.setDesignChangeNumber(zjTzDesignChange.getDesignChangeNumber());
        // 设计变更名称
        dbzjTzDesignChangeRecord.setDesignChangeName(zjTzDesignChange.getDesignChangeName());
        // 变更等级id
        dbzjTzDesignChangeRecord.setChangeLevelId(zjTzDesignChange.getChangeLevelId());
        // 变更等级name
        dbzjTzDesignChangeRecord.setChangeLevelName(zjTzDesignChange.getChangeLevelName());
        // 变更性质id
        dbzjTzDesignChangeRecord.setChangeNatureId(zjTzDesignChange.getChangeNatureId());
        // 变更性质name
        dbzjTzDesignChangeRecord.setChangeNatureName(zjTzDesignChange.getChangeNatureName());
        // 是否有动态设计机制id
        dbzjTzDesignChangeRecord.setDynamicId(zjTzDesignChange.getDynamicId());
        // 是否有动态设计机制name
        dbzjTzDesignChangeRecord.setDynamicName(zjTzDesignChange.getDynamicName());
        // 变更增减金额
        dbzjTzDesignChangeRecord.setChangeAmount(zjTzDesignChange.getChangeAmount());
        // 变更时间
        dbzjTzDesignChangeRecord.setChangeDate(zjTzDesignChange.getChangeDate());
        // 设计内容简要描述
        dbzjTzDesignChangeRecord.setContent(zjTzDesignChange.getContent());
        dbzjTzDesignChangeRecord.setStatusId("0");
        dbzjTzDesignChangeRecord.setStatusName("未上报");
        // 备注
        dbzjTzDesignChangeRecord.setRemarks(zjTzDesignChange.getRemarks());
        dbzjTzDesignChangeRecord.setCreateUserInfo(userKey, realName);
        flag = zjTzDesignChangeRecordMapper.insert(dbzjTzDesignChangeRecord);
        List<ZjTzFile> zjTzFileRecordList = zjTzDesignChange.getZjTzFileList();
    	if(zjTzFileRecordList != null && zjTzFileRecordList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileRecordList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(dbzjTzDesignChangeRecord.getDesignChangeRecordId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//add 统计======单条数据的金额超过30万才新增
    	if(zjTzDesignChange.getChangeAmount() != null && zjTzDesignChange.getChangeAmount().abs().compareTo(new BigDecimal("30")) >= 0) {
    		int extA1 = 0;
        	BigDecimal extA2 = new BigDecimal("0");
        	int extA3 = 0;
        	BigDecimal extA4 = new BigDecimal("0");
        	int extA5 = 0;
        	BigDecimal extA6 = new BigDecimal("0");
        	int extA7 = 0;
        	int extA8 = 0;
        	int extB1 = 0;
        	BigDecimal extB2 = new BigDecimal("0");
        	int extB3 = 0;
        	BigDecimal extB4 = new BigDecimal("0");
        	int extB5 = 0;
        	BigDecimal extB6 = new BigDecimal("0");
        	int extB7 = 0;
        	int extB8 = 0;
        	int extC11 = 0;
        	BigDecimal extC12 = new BigDecimal("0");
        	int extC13 = 0;
        	BigDecimal extC14 = new BigDecimal("0");
        	int extC15 = 0;
        	int extC16 = 0;
        	int extC21 = 0;
        	BigDecimal extC22 = new BigDecimal("0");
        	int extC23 = 0;
        	int extC24 = 0;
        	int extD11 = 0;
        	BigDecimal extD12 = new BigDecimal("0");
        	int extD13 = 0;
        	BigDecimal extD14 = new BigDecimal("0");
        	int extD15 = 0;
        	int extD16 = 0;
        	int extD21 = 0;
        	BigDecimal extD22 = new BigDecimal("0");
        	int extD23 = 0;
        	int extAll1 = 0;
        	BigDecimal extAll2 = new BigDecimal("0");
        	int extAll3 = 0;
        	BigDecimal extAll4 = new BigDecimal("0");
        	int extAll5 = 0;
        	BigDecimal extAll6 = new BigDecimal("0");
        	int extAll7 = 0;
        	BigDecimal extAll8 = new BigDecimal("0");
        	//
        	ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
        	 dbzjTzDesignChangeStatistics.setDesignChangeStatisticsId(UuidUtil.generate());
             dbzjTzDesignChangeStatistics.setDesignChangeId(zjTzDesignChange.getDesignChangeId());
             dbzjTzDesignChangeStatistics.setProjectId(zjTzDesignChange.getProjectId());
             dbzjTzDesignChangeStatistics.setProjectName(zjTzDesignChange.getProjectName());
             dbzjTzDesignChangeStatistics.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
             dbzjTzDesignChangeStatistics.setSubprojectName(zjTzDesignChange.getSubprojectName());
             if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "1")) {//=============a
            	 if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "1")) {//主动
            		 extA1 = extA1 + 1;
            		 extA2 = CalcUtils.calcAdd(extA2, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "2")) {//被动
            		 extA3 = extA3 + 1;
            		 extA4 = CalcUtils.calcAdd(extA4, zjTzDesignChange.getChangeAmount());
            	 }
            	 extA5 = extA1 + extA3;
            	 extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//完成内部审核
            		 extA7 = extA7 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//完成合法合规
            		 extA8 = extA8 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "2")) {//========b
            	 if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "1")) {//主动
            		 extB1 = extB1 + 1;
            		 extB2 = CalcUtils.calcAdd(extB2, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "2")) {//被动
            		 extB3 = extB3 + 1;
            		 extB4 = CalcUtils.calcAdd(extB4, zjTzDesignChange.getChangeAmount());
            	 }
            	 extB5 = extB5 + extB1 + extB3;
            	 extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//完成内部审核
            		 extB7 = extB7 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//完成合法合规
            		 extB8 = extB8 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "3")) {//=============c1
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//设计
            		 extC11 = extC11 + 1;
            		 extC12 = CalcUtils.calcAdd(extC12, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "1")) {//动态
            		 extC13 = extC13 + 1;
            		 extC14 = CalcUtils.calcAdd(extC14, zjTzDesignChange.getChangeAmount());
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//完成内部审核
            		 extC15 = extC15 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//完成合法合规
            		 extC16 = extC16 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "4")) {//=============c2
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//设计
            		 extC21 = extC21 + 1;
            		 extC22 = CalcUtils.calcAdd(extC22, zjTzDesignChange.getChangeAmount());
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//完成内部审核
            		 extC23 = extC23 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//完成合法合规
            		 extC24 = extC24 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "5")) {//=============d1
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//设计
            		 extD11 = extD11 + 1;
            		 extD12 = CalcUtils.calcAdd(extD12, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "1")) {//动态
            		 extD13 = extD13 + 1;
            		 extD14 = CalcUtils.calcAdd(extD14, zjTzDesignChange.getChangeAmount());
            	 } if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//完成内部审核
            		 extD15 = extD15 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//完成合法合规
            		 extD16 = extD16 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "6")) {//=============d2
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//设计
            		 extD21 = extD21 + 1;
            		 extD22 = CalcUtils.calcAdd(extD22, zjTzDesignChange.getChangeAmount());
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//完成合法合规
            		 extD23 = extD23 + 1;
            	 }
             }
             extAll1 = extAll1 + extA1+extB1+extC11+extD11;
             extAll2 = extAll2.add(extA2.add(extB2).add(extC12).add(extD12));
             extAll3 = extAll3 + extA3+extB3+extC21+extD21;
             extAll4 = extAll4.add(extA4.add(extB4).add(extC22).add(extD22));
             extAll5 = extAll5 + extC13+extD13;
             extAll6 = extAll6.add(extC14.add(extD14));
             extAll7 = extAll7 + extAll1+extAll3;
             extAll8 = extAll8.add(extAll2.add(extAll4));
             // a主动数量
             dbzjTzDesignChangeStatistics.setExtA1(extA1);
             // a主动金额
             dbzjTzDesignChangeStatistics.setExtA2(extA2);
             // a被动数量
             dbzjTzDesignChangeStatistics.setExtA3(extA3);
             // a被动金额
             dbzjTzDesignChangeStatistics.setExtA4(extA4);
             // a总数量
             dbzjTzDesignChangeStatistics.setExtA5(extA5);
             // a总金额
             dbzjTzDesignChangeStatistics.setExtA6(extA6);
             // a已完成内部审核数量
             dbzjTzDesignChangeStatistics.setExtA7(extA7);
             // a已完成合法合规数量
             dbzjTzDesignChangeStatistics.setExtA8(extA8);
             // b主动数量
             dbzjTzDesignChangeStatistics.setExtB1(extB1);
             // b主动金额
             dbzjTzDesignChangeStatistics.setExtB2(extB2);
             // b被动数量
             dbzjTzDesignChangeStatistics.setExtB3(extB3);
             // b被动金额
             dbzjTzDesignChangeStatistics.setExtB4(extB4);
             // b总数量
             dbzjTzDesignChangeStatistics.setExtB5(extB5);
             // b总金额
             dbzjTzDesignChangeStatistics.setExtB6(extB6);
             // b已完成内部审核数量
             dbzjTzDesignChangeStatistics.setExtB7(extB7);
             // b已完成合法合规数量
             dbzjTzDesignChangeStatistics.setExtB8(extB8);
             // c1设计数量
             dbzjTzDesignChangeStatistics.setExtC11(extC11);
             // c1设计金额
             dbzjTzDesignChangeStatistics.setExtC12(extC12);
             // c1动态管控数量
             dbzjTzDesignChangeStatistics.setExtC13(extC13);
             // c1动态管控金额
             dbzjTzDesignChangeStatistics.setExtC14(extC14);
             // c1已完成内部审核数量
             dbzjTzDesignChangeStatistics.setExtC15(extC15);
             // c1已完成合法合规数量
             dbzjTzDesignChangeStatistics.setExtC16(extC16);
             // c2设计数量
             dbzjTzDesignChangeStatistics.setExtC21(extC21);
             // c2设计金额
             dbzjTzDesignChangeStatistics.setExtC22(extC22);
             // c2已完成内部审核数量
             dbzjTzDesignChangeStatistics.setExtC23(extC23);
             // c2已完成合法合规数量
             dbzjTzDesignChangeStatistics.setExtC24(extC24);
             // d1设计数量
             dbzjTzDesignChangeStatistics.setExtD11(extD11);
             // d1设计金额
             dbzjTzDesignChangeStatistics.setExtD12(extD12);
             // d1动态管控数量
             dbzjTzDesignChangeStatistics.setExtD13(extD13);
             // d1动态管控金额
             dbzjTzDesignChangeStatistics.setExtD14(extD14);
             // d1已完成内部审核数量
             dbzjTzDesignChangeStatistics.setExtD15(extD15);
             // d1已完成合法合规数量
             dbzjTzDesignChangeStatistics.setExtD16(extD16);
             // d2设计数量
             dbzjTzDesignChangeStatistics.setExtD21(extD21);
             // d2设计金额
             dbzjTzDesignChangeStatistics.setExtD22(extD22);
             // d2已完成合法合规数量
             dbzjTzDesignChangeStatistics.setExtD23(extD23);
             // 总主动数量
             dbzjTzDesignChangeStatistics.setExtAll1(extAll1);
             // 总主动金额
             dbzjTzDesignChangeStatistics.setExtAll2(extAll2);
             // 总被动数量
             dbzjTzDesignChangeStatistics.setExtAll3(extAll3);
             // 总被动金额
             dbzjTzDesignChangeStatistics.setExtAll4(extAll4);
             // 总动态设计数量
             dbzjTzDesignChangeStatistics.setExtAll5(extAll5);
             // 总动态设计金额
             dbzjTzDesignChangeStatistics.setExtAll6(extAll6);
             // 总数量
             dbzjTzDesignChangeStatistics.setExtAll7(extAll7);
             // 总金额
             dbzjTzDesignChangeStatistics.setExtAll8(extAll8);
             // 备注
             dbzjTzDesignChangeStatistics.setRemarks(zjTzDesignChange.getRemarks());
             dbzjTzDesignChangeStatistics.setReleaseId("0");
             dbzjTzDesignChangeStatistics.setCreateUserInfo(userKey, realName);
             flag = zjTzDesignChangeStatisticsMapper.insert(dbzjTzDesignChangeStatistics);
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignChange);
        }
    }

    @Override
    public ResponseEntity updateZjTzDesignChange(ZjTzDesignChange zjTzDesignChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignChange dbzjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzDesignChange.getDesignChangeId());
        if (dbzjTzDesignChange != null && StrUtil.isNotEmpty(dbzjTzDesignChange.getDesignChangeId())) {
           // 项目id
           dbzjTzDesignChange.setProjectId(zjTzDesignChange.getProjectId());
           // 项目name
           dbzjTzDesignChange.setProjectName(zjTzDesignChange.getProjectName());
           // 子项目id
           dbzjTzDesignChange.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
           // 子项目name
           dbzjTzDesignChange.setSubprojectName(zjTzDesignChange.getSubprojectName());
           // 设计阶段id
           dbzjTzDesignChange.setDesignStageId(zjTzDesignChange.getDesignStageId());
           // 设计阶段name
           dbzjTzDesignChange.setDesignStageName(zjTzDesignChange.getDesignStageName());
           // 变更次数
           dbzjTzDesignChange.setChangeNum(zjTzDesignChange.getChangeNum());
           // 设计变更编号
           dbzjTzDesignChange.setDesignChangeNumber(zjTzDesignChange.getDesignChangeNumber());
           // 设计变更名称
           dbzjTzDesignChange.setDesignChangeName(zjTzDesignChange.getDesignChangeName());
           // 变更等级id
           dbzjTzDesignChange.setChangeLevelId(zjTzDesignChange.getChangeLevelId());
           // 变更等级name
           dbzjTzDesignChange.setChangeLevelName(zjTzDesignChange.getChangeLevelName());
           // 变更性质id
           dbzjTzDesignChange.setChangeNatureId(zjTzDesignChange.getChangeNatureId());
           // 变更性质name
           dbzjTzDesignChange.setChangeNatureName(zjTzDesignChange.getChangeNatureName());
           // 是否有动态设计机制id
           dbzjTzDesignChange.setDynamicId(zjTzDesignChange.getDynamicId());
           // 是否有动态设计机制name
           dbzjTzDesignChange.setDynamicName(zjTzDesignChange.getDynamicName());
           // 变更增减金额
           dbzjTzDesignChange.setChangeAmount(zjTzDesignChange.getChangeAmount());
           // 变更时间
           dbzjTzDesignChange.setChangeDate(zjTzDesignChange.getChangeDate());
           // 设计内容简要描述
           dbzjTzDesignChange.setContent(zjTzDesignChange.getContent());
           // 备注
           dbzjTzDesignChange.setRemarks(zjTzDesignChange.getRemarks());
           // 共通
           dbzjTzDesignChange.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignChangeMapper.updateByPrimaryKey(dbzjTzDesignChange);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignChange);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignChange(List<ZjTzDesignChange> zjTzDesignChangeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignChangeList != null && zjTzDesignChangeList.size() > 0) {
           ZjTzDesignChange zjTzDesignChange = new ZjTzDesignChange();
           zjTzDesignChange.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignChangeMapper.batchDeleteUpdateZjTzDesignChange(zjTzDesignChangeList, zjTzDesignChange);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignChangeList);
        }
    }
}
