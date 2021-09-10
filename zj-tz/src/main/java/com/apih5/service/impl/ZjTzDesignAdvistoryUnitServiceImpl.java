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
import com.apih5.mybatis.dao.ZjTzDesignAdvistoryUnitMapper;
import com.apih5.mybatis.dao.ZjTzDesignAdvistoryUnitRecordMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzQualityMapper;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnit;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitRecord;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzQuality;
import com.apih5.service.ZjTzDesignAdvistoryUnitService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDesignAdvistoryUnitService")
public class ZjTzDesignAdvistoryUnitServiceImpl implements ZjTzDesignAdvistoryUnitService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitMapper zjTzDesignAdvistoryUnitMapper;
    
    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitRecordMapper zjTzDesignAdvistoryUnitRecordMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzQualityMapper zjTzQualityMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitListByCondition(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzDesignAdvistoryUnit == null) {
            zjTzDesignAdvistoryUnit = new ZjTzDesignAdvistoryUnit();
        }
        // 分页查询
        PageHelper.startPage(zjTzDesignAdvistoryUnit.getPage(),zjTzDesignAdvistoryUnit.getLimit());
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzDesignAdvistoryUnit.getProjectId(), true)) {
            	zjTzDesignAdvistoryUnit.setProjectId("");
            	zjTzDesignAdvistoryUnit.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzDesignAdvistoryUnit.getProjectId(), true)) {
            	zjTzDesignAdvistoryUnit.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzDesignAdvistoryUnit> zjTzDesignAdvistoryUnitList = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(zjTzDesignAdvistoryUnit);
        for (ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit2 : zjTzDesignAdvistoryUnitList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnit2.getDesignAdvistoryUnitId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnit2.setZjTzFileList(zjTzFileList);
             //资质
             ZjTzQuality zjTzQuality = new ZjTzQuality();
             zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnit2.getDesignAdvistoryUnitStandardId());
             List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
             for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
            	 ZjTzFile zjTzFileQuality = new ZjTzFile();
            	 zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
            	 List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
             	zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
     		}
         	zjTzDesignAdvistoryUnit2.setZjTzQualityList(zjTzQualityList);
         	
         	int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
         	ZjTzDesignAdvistoryUnitRecord zjTzRules = new  ZjTzDesignAdvistoryUnitRecord();
         	zjTzRules.setDesignAdvistoryUnitId(zjTzDesignAdvistoryUnit2.getDesignAdvistoryUnitId());
         	List<ZjTzDesignAdvistoryUnitRecord> records = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzRules);
         	if (records != null && records.size() > 0) {
				for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : records) {
					if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord2.getRenew1(), "0")) {
						count1 = count1 + 1;
					}else if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord2.getRenew2(), "0")) {
						count2 = count2 + 1;
					}else if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord2.getRenew3(), "0")) {
						count3 = count3 + 1;
					}else if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord2.getRenew4(), "0")) {
						count4 = count4 + 1;
					}
				}
			}
         	if (count1 >= 1) {
         		zjTzDesignAdvistoryUnit2.setRenew1("0");
            }else if (count2 >= 1) {
            	zjTzDesignAdvistoryUnit2.setRenew2("0");
            }else if (count3 >= 1) {
            	zjTzDesignAdvistoryUnit2.setRenew3("0");
            }else if (count4 >= 1) {
            	zjTzDesignAdvistoryUnit2.setRenew4("0");
            }
         	// 主表中仅汇总子表中已上报的数据判断标识
         	BigDecimal total1Amount1 = new BigDecimal("0");
         	BigDecimal total1Amount2 = new BigDecimal("0");
         	BigDecimal total2Amount1 = new BigDecimal("0");
         	BigDecimal total2Amount2 = new BigDecimal("0");
         	BigDecimal total3Amount1 = new BigDecimal("0");
         	BigDecimal total3Amount2 = new BigDecimal("0");
         	ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
         	zjTzDesignAdvistoryUnitRecord.setDesignAdvistoryUnitId(zjTzDesignAdvistoryUnit2.getDesignAdvistoryUnitId());
         	List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
         	if (zjTzDesignAdvistoryUnitRecordList != null && zjTzDesignAdvistoryUnitRecordList.size() > 0) {
				for (ZjTzDesignAdvistoryUnitRecord dbzjTzDesignAdvistoryUnitRecord : zjTzDesignAdvistoryUnitRecordList) {
					if (StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getReleaseId(), "1")) {
			         	if(StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getDesignStageId(), "1")) {
			         		total1Amount1 = CalcUtils.calcAdd(total1Amount1, dbzjTzDesignAdvistoryUnitRecord.getAmount1());
			         		total1Amount2 = CalcUtils.calcAdd(total1Amount2,dbzjTzDesignAdvistoryUnitRecord.getAmount2());
			    		}else if(StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getDesignStageId(), "2")) {
			    			total2Amount1 = CalcUtils.calcAdd(total2Amount1,dbzjTzDesignAdvistoryUnitRecord.getAmount1());
			    			total2Amount2 = CalcUtils.calcAdd(total2Amount2,dbzjTzDesignAdvistoryUnitRecord.getAmount2());
			    		}else if(StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getDesignStageId(), "3")) {
			    			total3Amount1 = CalcUtils.calcAdd(total3Amount1,dbzjTzDesignAdvistoryUnitRecord.getAmount1());
			    			total3Amount2 = CalcUtils.calcAdd(total3Amount2,dbzjTzDesignAdvistoryUnitRecord.getAmount2());
			    		}
					}
				}
			}
         	zjTzDesignAdvistoryUnit2.setTotal1Amount1(total1Amount1);
         	zjTzDesignAdvistoryUnit2.setTotal1Amount2(total1Amount2);
         	zjTzDesignAdvistoryUnit2.setTotal2Amount1(total2Amount1);
         	zjTzDesignAdvistoryUnit2.setTotal2Amount2(total2Amount2);
         	zjTzDesignAdvistoryUnit2.setTotal3Amount1(total3Amount1);
         	zjTzDesignAdvistoryUnit2.setTotal3Amount2(total3Amount2);
        
        }
        // 得到分页信息
        PageInfo<ZjTzDesignAdvistoryUnit> p = new PageInfo<>(zjTzDesignAdvistoryUnitList);

        return repEntity.okList(zjTzDesignAdvistoryUnitList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitDetails(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        if (zjTzDesignAdvistoryUnit == null) {
            zjTzDesignAdvistoryUnit = new ZjTzDesignAdvistoryUnit();
        }
        // 获取数据
        ZjTzDesignAdvistoryUnit dbZjTzDesignAdvistoryUnit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
        // 数据存在
        if (dbZjTzDesignAdvistoryUnit != null) {
        	ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
            zjTzFileContractConditionSelect.setOtherId(dbZjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
            dbZjTzDesignAdvistoryUnit.setZjTzFileList(zjTzFileList);

            //资质
            ZjTzQuality zjTzQuality = new ZjTzQuality();
            zjTzQuality.setDesignAdvistoryUnitStandardId(dbZjTzDesignAdvistoryUnit.getDesignAdvistoryUnitStandardId());
            List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
            for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
            	ZjTzFile zjTzFileQuality = new ZjTzFile();
            	zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
            	List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
            	zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
            }
            dbZjTzDesignAdvistoryUnit.setZjTzQualityList(zjTzQualityList);

            return repEntity.ok(dbZjTzDesignAdvistoryUnit);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignAdvistoryUnit(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(StrUtil.isEmpty(zjTzDesignAdvistoryUnit.getTypeId())) {
        	return repEntity.layerMessage("no", "请确定新增的是设计还是咨询");
        }else {
        	if(StrUtil.equals(zjTzDesignAdvistoryUnit.getTypeId(), "1")) {
        		zjTzDesignAdvistoryUnit.setTypeName("设计");
        	}else if(StrUtil.equals(zjTzDesignAdvistoryUnit.getTypeId(), "2")) {
        		zjTzDesignAdvistoryUnit.setTypeName("单位");
        	}
        }
    	//一、项目id和子项目id   共同判断
    	if(StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getSubprojectInfoId())) {
    		ZjTzDesignAdvistoryUnit zjTzSizeControlSelect = new ZjTzDesignAdvistoryUnit();
    		zjTzSizeControlSelect.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
    		zjTzSizeControlSelect.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
    		List<ZjTzDesignAdvistoryUnit> sizeControls = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
    		}
    		//子项目名称
    		ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
    		if(info != null) {
    			zjTzDesignAdvistoryUnit.setSubprojectName(info.getSubprojectName());
    		}
    	}else {
    		//二、不填子项目的  = 用项目id判断
    		ZjTzDesignAdvistoryUnit zjTzSizeControlSelect = new ZjTzDesignAdvistoryUnit();
    		zjTzSizeControlSelect.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
    		zjTzSizeControlSelect.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId("无");
    		List<ZjTzDesignAdvistoryUnit> sizeControls = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
    		}
    		zjTzDesignAdvistoryUnit.setSubprojectInfoId("无");
    	}
        zjTzDesignAdvistoryUnit.setDesignAdvistoryUnitId(UuidUtil.generate());
        //设计阶段  1.工程可研   2.初步设计   3.施工图设计
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getDesignStageId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignAdvistoryUnit.getDesignStageId());
        	zjTzDesignAdvistoryUnit.setDesignStageName(openBankName);
        	if(StrUtil.equals(zjTzDesignAdvistoryUnit.getDesignStageId(), "1")) {
        		zjTzDesignAdvistoryUnit.setTotal1Amount1(zjTzDesignAdvistoryUnit.getAmount1());
        		zjTzDesignAdvistoryUnit.setTotal1Amount2(zjTzDesignAdvistoryUnit.getAmount2());
        	}else if(StrUtil.equals(zjTzDesignAdvistoryUnit.getDesignStageId(), "2")) {
        		zjTzDesignAdvistoryUnit.setTotal2Amount1(zjTzDesignAdvistoryUnit.getAmount1());
        		zjTzDesignAdvistoryUnit.setTotal2Amount2(zjTzDesignAdvistoryUnit.getAmount2());
        	}else if(StrUtil.equals(zjTzDesignAdvistoryUnit.getDesignStageId(), "3")) {
        		zjTzDesignAdvistoryUnit.setTotal3Amount1(zjTzDesignAdvistoryUnit.getAmount1());
        		zjTzDesignAdvistoryUnit.setTotal3Amount2(zjTzDesignAdvistoryUnit.getAmount2());
        	}
        }
        //选定方式
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getSelectModeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("xuanDiFangShi", zjTzDesignAdvistoryUnit.getSelectModeId());
        	zjTzDesignAdvistoryUnit.setSelectModeName(openBankName);
        }
        //评价等级
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getEvaluateOrderId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnit.getEvaluateOrderId());
        	zjTzDesignAdvistoryUnit.setEvaluateOrderName(openBankName);
        }
        
        zjTzDesignAdvistoryUnit.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignAdvistoryUnitMapper.insert(zjTzDesignAdvistoryUnit);
        // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzDesignAdvistoryUnit.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//add记录
        ZjTzDesignAdvistoryUnitRecord record = new ZjTzDesignAdvistoryUnitRecord();
        record.setDesignAdvistoryUnitRecordId(UuidUtil.generate());
        // 设计、咨询单位管理id
        record.setDesignAdvistoryUnitId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
        // 类型id
        record.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
        // 类型name
        record.setTypeName(zjTzDesignAdvistoryUnit.getTypeName());
        // 设计、咨询单位标准化id
        record.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitStandardId());
        // 项目id
        record.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
        // 项目name
        record.setProjectName(zjTzDesignAdvistoryUnit.getProjectName());
        // 子项目id
        record.setSubprojectInfoId(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
        // 子项目name
        record.setSubprojectName(zjTzDesignAdvistoryUnit.getSubprojectName());
        // 设计阶段id
        record.setDesignStageId(zjTzDesignAdvistoryUnit.getDesignStageId());
        // 设计阶段name
        record.setDesignStageName(zjTzDesignAdvistoryUnit.getDesignStageName());
        // 选定方式id
        record.setSelectModeId(zjTzDesignAdvistoryUnit.getSelectModeId());
        // 选定方式name
        record.setSelectModeName(zjTzDesignAdvistoryUnit.getSelectModeName());
        // 工作内容
        record.setContent(zjTzDesignAdvistoryUnit.getContent());
        // 金额1
        record.setAmount1(zjTzDesignAdvistoryUnit.getAmount1());
        // 金额2
        record.setAmount2(zjTzDesignAdvistoryUnit.getAmount2());
        // 项目负责人
        record.setProLeader(zjTzDesignAdvistoryUnit.getProLeader());
        // 负责人电话
        record.setProLeaderTel(zjTzDesignAdvistoryUnit.getProLeaderTel());
        // 评价等级id
        record.setEvaluateOrderId(zjTzDesignAdvistoryUnit.getEvaluateOrderId());
        // 评价等级name
        record.setEvaluateOrderName(zjTzDesignAdvistoryUnit.getEvaluateOrderName());
        record.setReleaseId("0");
        record.setReleaseName("未上报");
        // 备注
        record.setRemarks(zjTzDesignAdvistoryUnit.getRemarks());
        record.setCreateUserInfo(userKey, realName);
        zjTzDesignAdvistoryUnitRecordMapper.insert(record);
        // 附件list
    	List<ZjTzFile> zjTzFileRecordList = zjTzDesignAdvistoryUnit.getZjTzFileList();
    	if(zjTzFileRecordList != null && zjTzFileRecordList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileRecordList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(record.getDesignAdvistoryUnitRecordId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignAdvistoryUnit);
        }
    }

    @Override
    public ResponseEntity updateZjTzDesignAdvistoryUnit(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignAdvistoryUnit dbzjTzDesignAdvistoryUnit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
        if (dbzjTzDesignAdvistoryUnit != null && StrUtil.isNotEmpty(dbzjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId())) {
           // 类型id
           dbzjTzDesignAdvistoryUnit.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
           // 类型name
           dbzjTzDesignAdvistoryUnit.setTypeName(zjTzDesignAdvistoryUnit.getTypeName());
           // 设计、咨询单位标准化id
           dbzjTzDesignAdvistoryUnit.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitStandardId());
           // 项目id
           dbzjTzDesignAdvistoryUnit.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
           // 项目name
           dbzjTzDesignAdvistoryUnit.setProjectName(zjTzDesignAdvistoryUnit.getProjectName());
           // 子项目id
           dbzjTzDesignAdvistoryUnit.setSubprojectInfoId(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
           // 子项目name
           dbzjTzDesignAdvistoryUnit.setSubprojectName(zjTzDesignAdvistoryUnit.getSubprojectName());
           // 设计阶段id
           dbzjTzDesignAdvistoryUnit.setDesignStageId(zjTzDesignAdvistoryUnit.getDesignStageId());
           // 设计阶段name
           dbzjTzDesignAdvistoryUnit.setDesignStageName(zjTzDesignAdvistoryUnit.getDesignStageName());
           // 选定方式id
           dbzjTzDesignAdvistoryUnit.setSelectModeId(zjTzDesignAdvistoryUnit.getSelectModeId());
           // 选定方式name
           dbzjTzDesignAdvistoryUnit.setSelectModeName(zjTzDesignAdvistoryUnit.getSelectModeName());
           // 工作内容
           dbzjTzDesignAdvistoryUnit.setContent(zjTzDesignAdvistoryUnit.getContent());
           // 金额1
           dbzjTzDesignAdvistoryUnit.setAmount1(zjTzDesignAdvistoryUnit.getAmount1());
           // 金额2
           dbzjTzDesignAdvistoryUnit.setAmount2(zjTzDesignAdvistoryUnit.getAmount2());
           // 项目负责人
           dbzjTzDesignAdvistoryUnit.setProLeader(zjTzDesignAdvistoryUnit.getProLeader());
           // 负责人电话
           dbzjTzDesignAdvistoryUnit.setProLeaderTel(zjTzDesignAdvistoryUnit.getProLeaderTel());
           // 评价等级id
           dbzjTzDesignAdvistoryUnit.setEvaluateOrderId(zjTzDesignAdvistoryUnit.getEvaluateOrderId());
           // 评价等级name
           dbzjTzDesignAdvistoryUnit.setEvaluateOrderName(zjTzDesignAdvistoryUnit.getEvaluateOrderName());
           // 备注
           dbzjTzDesignAdvistoryUnit.setRemarks(zjTzDesignAdvistoryUnit.getRemarks());
           // 总额
           dbzjTzDesignAdvistoryUnit.setTotal1Amount1(zjTzDesignAdvistoryUnit.getTotal1Amount1());
           // 总额
           dbzjTzDesignAdvistoryUnit.setTotal1Amount2(zjTzDesignAdvistoryUnit.getTotal1Amount2());
           // 总额
           dbzjTzDesignAdvistoryUnit.setTotal2Amount1(zjTzDesignAdvistoryUnit.getTotal2Amount1());
           // 总额
           dbzjTzDesignAdvistoryUnit.setTotal2Amount2(zjTzDesignAdvistoryUnit.getTotal2Amount2());
           // 总额
           dbzjTzDesignAdvistoryUnit.setTotal3Amount1(zjTzDesignAdvistoryUnit.getTotal3Amount1());
           // 总额
           dbzjTzDesignAdvistoryUnit.setTotal3Amount2(zjTzDesignAdvistoryUnit.getTotal3Amount2());
           // 共通
           dbzjTzDesignAdvistoryUnit.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnit);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnit);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnit(List<ZjTzDesignAdvistoryUnit> zjTzDesignAdvistoryUnitList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignAdvistoryUnitList != null && zjTzDesignAdvistoryUnitList.size() > 0) {
           ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit = new ZjTzDesignAdvistoryUnit();
           zjTzDesignAdvistoryUnit.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignAdvistoryUnitMapper.batchDeleteUpdateZjTzDesignAdvistoryUnit(zjTzDesignAdvistoryUnitList, zjTzDesignAdvistoryUnit);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignAdvistoryUnitList);
        }
    }
}
