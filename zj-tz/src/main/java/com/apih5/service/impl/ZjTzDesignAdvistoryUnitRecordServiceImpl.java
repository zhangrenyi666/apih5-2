package com.apih5.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitRecord;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnit;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzQuality;
import com.apih5.mybatis.pojo.ZjTzSizeControl;
import com.apih5.mybatis.pojo.ZjTzSizeControlRecord;
import com.apih5.service.ZjTzDesignAdvistoryUnitRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjTzDesignAdvistoryUnitRecordService")
public class ZjTzDesignAdvistoryUnitRecordServiceImpl implements ZjTzDesignAdvistoryUnitRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitRecordMapper zjTzDesignAdvistoryUnitRecordMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitMapper zjTzDesignAdvistoryUnitMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzQualityMapper zjTzQualityMapper;


    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordListByCondition(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzDesignAdvistoryUnitRecord == null) {
            zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
        }
    	// 不是集团的人员时
//        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//           
//        } else {
//            // 集团人员时
//        	zjTzDesignAdvistoryUnitRecord.setExt1SeeFlag("投资事业部只可见已上报和被退回的数据");
//        }
        // 直管项目用户不可见托管项目上报状态的数据
        if (StrUtil.equals("3", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
        	zjTzDesignAdvistoryUnitRecord.setExt1Flag1("直管项目用户不可见托管项目上报状态的数据");
        //	托管公司不可见未上报的数据
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzDesignAdvistoryUnitRecord.setExt1Flag2("托管公司不可见未上报的数据");;
        // 局用户不可见未上报和托管项目上报状态的数据	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzDesignAdvistoryUnitRecord.setExt1Flag3("局用户不可见未上报和托管项目上报状态的数据");
		}
        // 分页查询
        PageHelper.startPage(zjTzDesignAdvistoryUnitRecord.getPage(),zjTzDesignAdvistoryUnitRecord.getLimit());
        // 获取数据
        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);
             
             //资质
             ZjTzQuality zjTzQuality = new ZjTzQuality();
             zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitStandardId());
             List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
             for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
             	ZjTzFile zjTzFileQuality = new ZjTzFile();
             	zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
             	List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
             	zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
             }
             zjTzDesignAdvistoryUnitRecord2.setZjTzQualityList(zjTzQualityList);
		}
        // 得到分页信息
        PageInfo<ZjTzDesignAdvistoryUnitRecord> p = new PageInfo<>(zjTzDesignAdvistoryUnitRecordList);

        return repEntity.okList(zjTzDesignAdvistoryUnitRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordDetails(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        if (zjTzDesignAdvistoryUnitRecord == null) {
            zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
        }
        // 获取数据
        ZjTzDesignAdvistoryUnitRecord dbZjTzDesignAdvistoryUnitRecord = zjTzDesignAdvistoryUnitRecordMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        // 数据存在
        if (dbZjTzDesignAdvistoryUnitRecord != null) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(dbZjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             dbZjTzDesignAdvistoryUnitRecord.setZjTzFileList(zjTzFileList);
             
             //资质
             ZjTzQuality zjTzQuality = new ZjTzQuality();
             zjTzQuality.setDesignAdvistoryUnitStandardId(dbZjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitStandardId());
             List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
             for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
             	ZjTzFile zjTzFileQuality = new ZjTzFile();
             	zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
             	List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
             	zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
             }
             dbZjTzDesignAdvistoryUnitRecord.setZjTzQualityList(zjTzQualityList);
             
            return repEntity.ok(dbZjTzDesignAdvistoryUnitRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignAdvistoryUnitRecord(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(StrUtil.isEmpty(zjTzDesignAdvistoryUnitRecord.getTypeId())) {
        	return repEntity.layerMessage("no", "请确定新增的是设计还是咨询");
        }else {
        	if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "1")) {
        		zjTzDesignAdvistoryUnitRecord.setTypeName("设计");
        	}else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "2")) {
        		zjTzDesignAdvistoryUnitRecord.setTypeName("单位");
        	}
        }
        //子项目名称
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getSubprojectInfoId());
        if(info != null) {
        	zjTzDesignAdvistoryUnitRecord.setSubprojectName(info.getSubprojectName());
        }
        ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getProjectId());
        if (dbzjTzProManage != null) {
        	zjTzDesignAdvistoryUnitRecord.setProjectName(dbzjTzProManage.getProjectName());
		}
        zjTzDesignAdvistoryUnitRecord.setDesignAdvistoryUnitRecordId(UuidUtil.generate());
        //设计阶段  1.工程可研   2.初步设计   3.施工图设计
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getDesignStageId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignAdvistoryUnitRecord.getDesignStageId());
        	zjTzDesignAdvistoryUnitRecord.setDesignStageName(openBankName);
        	//给外层的金额进行累加计算
        	ZjTzDesignAdvistoryUnit unit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        	if(unit != null && StrUtil.isNotEmpty(unit.getDesignAdvistoryUnitId())) {
        		if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "1")) {
        			unit.setTotal1Amount1(CalcUtils.calcAdd(unit.getTotal1Amount1(), zjTzDesignAdvistoryUnitRecord.getAmount1()));
        			unit.setTotal1Amount2(CalcUtils.calcAdd(unit.getTotal1Amount2(),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        		}else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "2")) {
        			unit.setTotal2Amount1(CalcUtils.calcAdd(unit.getTotal2Amount1(),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        			unit.setTotal2Amount2(CalcUtils.calcAdd(unit.getTotal2Amount2(),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        		}else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "3")) {
        			unit.setTotal3Amount1(CalcUtils.calcAdd(unit.getTotal3Amount1(),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        			unit.setTotal3Amount2(CalcUtils.calcAdd(unit.getTotal3Amount2(),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        		}
        		unit.setModifyUserInfo(userKey, realName);
        		zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(unit);
        	}
        }
        //选定方式
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getSelectModeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("xuanDiFangShi", zjTzDesignAdvistoryUnitRecord.getSelectModeId());
        	zjTzDesignAdvistoryUnitRecord.setSelectModeName(openBankName);
        }
        //评价等级
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
        	zjTzDesignAdvistoryUnitRecord.setEvaluateOrderName(openBankName);
        }
        zjTzDesignAdvistoryUnitRecord.setReleaseId("0");
        zjTzDesignAdvistoryUnitRecord.setReleaseName("未上报");
        zjTzDesignAdvistoryUnitRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignAdvistoryUnitRecordMapper.insert(zjTzDesignAdvistoryUnitRecord);
        // 附件list
        List<ZjTzFile> zjTzFileRecordList = zjTzDesignAdvistoryUnitRecord.getZjTzFileList();
        if(zjTzFileRecordList != null && zjTzFileRecordList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileRecordList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignAdvistoryUnitRecord);
        }
    }

    @Override
    public ResponseEntity updateZjTzDesignAdvistoryUnitRecord(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignAdvistoryUnitRecord dbzjTzDesignAdvistoryUnitRecord = zjTzDesignAdvistoryUnitRecordMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        if (dbzjTzDesignAdvistoryUnitRecord != null && StrUtil.isNotEmpty(dbzjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId())) {
        	if(StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getReleaseId(), "1")) {
        		return repEntity.layerMessage("no", "已上报的数据不能修改");
        	}
        	
        	// 设计、咨询单位标准化id
           dbzjTzDesignAdvistoryUnitRecord.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitStandardId());
           // 设计阶段id
           dbzjTzDesignAdvistoryUnitRecord.setDesignStageId(zjTzDesignAdvistoryUnitRecord.getDesignStageId());
           //设计阶段name   1.工程可研   2.初步设计   3.施工图设计
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getDesignStageId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignAdvistoryUnitRecord.getDesignStageId());
        	   dbzjTzDesignAdvistoryUnitRecord.setDesignStageName(openBankName);
        	   //给外层的金额进行累加计算
        	   ZjTzDesignAdvistoryUnit unit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        	   if(unit != null && StrUtil.isNotEmpty(unit.getDesignAdvistoryUnitId())) {
        		   if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "1")) {
        			   unit.setTotal1Amount1(CalcUtils.calcAdd((CalcUtils.calcSubtract(unit.getTotal1Amount1(), dbzjTzDesignAdvistoryUnitRecord.getAmount1())),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        			   unit.setTotal1Amount2(CalcUtils.calcAdd((CalcUtils.calcSubtract(unit.getTotal1Amount2(), dbzjTzDesignAdvistoryUnitRecord.getAmount2())),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        		   }else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "2")) {
        			   unit.setTotal2Amount1(CalcUtils.calcAdd((CalcUtils.calcSubtract(unit.getTotal2Amount1(), dbzjTzDesignAdvistoryUnitRecord.getAmount1())),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        			   unit.setTotal2Amount2(CalcUtils.calcAdd((CalcUtils.calcSubtract(unit.getTotal2Amount2(), dbzjTzDesignAdvistoryUnitRecord.getAmount2())),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        		   }else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "3")) {
        			   unit.setTotal3Amount1(CalcUtils.calcAdd((CalcUtils.calcSubtract(unit.getTotal3Amount1(), dbzjTzDesignAdvistoryUnitRecord.getAmount1())),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        			   unit.setTotal3Amount2(CalcUtils.calcAdd((CalcUtils.calcSubtract(unit.getTotal3Amount2(), dbzjTzDesignAdvistoryUnitRecord.getAmount2())),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        		   }
        		   unit.setModifyUserInfo(userKey, realName);
        		   zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(unit);
        	   }
           }
           // 选定方式id
           dbzjTzDesignAdvistoryUnitRecord.setSelectModeId(zjTzDesignAdvistoryUnitRecord.getSelectModeId());
           // 选定方式name
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getSelectModeId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("xuanDiFangShi", zjTzDesignAdvistoryUnitRecord.getSelectModeId());
           	dbzjTzDesignAdvistoryUnitRecord.setSelectModeName(openBankName);
           }
           // 工作内容
           dbzjTzDesignAdvistoryUnitRecord.setContent(zjTzDesignAdvistoryUnitRecord.getContent());
           // 金额1
           dbzjTzDesignAdvistoryUnitRecord.setAmount1(zjTzDesignAdvistoryUnitRecord.getAmount1());
           // 金额2
           dbzjTzDesignAdvistoryUnitRecord.setAmount2(zjTzDesignAdvistoryUnitRecord.getAmount2());
           // 项目负责人
           dbzjTzDesignAdvistoryUnitRecord.setProLeader(zjTzDesignAdvistoryUnitRecord.getProLeader());
           // 负责人电话
           dbzjTzDesignAdvistoryUnitRecord.setProLeaderTel(zjTzDesignAdvistoryUnitRecord.getProLeaderTel());
           // 评价等级id
           dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderId(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
           // 评价等级name
           //评价等级
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
           	dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderName(openBankName);
           }
           // 备注
           dbzjTzDesignAdvistoryUnitRecord.setRemarks(zjTzDesignAdvistoryUnitRecord.getRemarks());
           // 共通
           dbzjTzDesignAdvistoryUnitRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignAdvistoryUnitRecordMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitRecord);
           
           //附件先删除再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileRecordList = zjTzDesignAdvistoryUnitRecord.getZjTzFileList();
           if(zjTzFileRecordList != null && zjTzFileRecordList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileRecordList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnitRecord(List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        int totalSize = 0;
        int delSize = 0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        if (zjTzDesignAdvistoryUnitRecordList != null && zjTzDesignAdvistoryUnitRecordList.size() > 0) {
        	for (ZjTzDesignAdvistoryUnitRecord advistoryUnitRecord : zjTzDesignAdvistoryUnitRecordList) {
        		if(StrUtil.equals(advistoryUnitRecord.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "已上报的数据不能删除！");
        		}
        	}
        	ZjTzDesignAdvistoryUnitRecord dbRecord = new ZjTzDesignAdvistoryUnitRecord();
        	dbRecord.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignAdvistoryUnitRecordMapper.batchDeleteUpdateZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList, dbRecord);
        	
        	delSize = zjTzDesignAdvistoryUnitRecordList.size();
        	for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord : zjTzDesignAdvistoryUnitRecordList) {
        		//删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		
        		List<ZjTzDesignAdvistoryUnit> delUnit = new ArrayList<>();
        		ZjTzDesignAdvistoryUnit unit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        		//1.如果只有一条记录表，里外层数据都删除 
        		ZjTzDesignAdvistoryUnitRecord record = new ZjTzDesignAdvistoryUnitRecord();
        		record.setDesignAdvistoryUnitId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        		List<ZjTzDesignAdvistoryUnitRecord> recordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(record);
        		if(recordList != null && recordList.size() >0) {
        			totalSize = recordList.size();
        		}
        		if(totalSize -delSize >0) {
        			//修改外层的统计金额==给外层的金额进行累减计算
        			if(unit != null && StrUtil.isNotEmpty(unit.getDesignAdvistoryUnitId())) {
        				if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "1")) {
        					unit.setTotal1Amount1(CalcUtils.calcSubtract(unit.getTotal1Amount1(), zjTzDesignAdvistoryUnitRecord.getAmount1()));
        					unit.setTotal1Amount2(CalcUtils.calcSubtract(unit.getTotal1Amount2(),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        				}else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "2")) {
        					unit.setTotal2Amount1(CalcUtils.calcSubtract(unit.getTotal2Amount1(),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        					unit.setTotal2Amount2(CalcUtils.calcSubtract(unit.getTotal2Amount2(),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        				}else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getDesignStageId(), "3")) {
        					unit.setTotal3Amount1(CalcUtils.calcSubtract(unit.getTotal3Amount1(),zjTzDesignAdvistoryUnitRecord.getAmount1()));
        					unit.setTotal3Amount2(CalcUtils.calcSubtract(unit.getTotal3Amount2(),zjTzDesignAdvistoryUnitRecord.getAmount2()));
        				}
        				for (ZjTzDesignAdvistoryUnitRecord dbzjTzDesignAdvistoryUnitRecord : recordList) {
        					if (StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getRenew1(), "0")) {
    							count1 = count1 + 1;
    						}else if (StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getRenew2(), "0")) {
    							count2 = count2 + 1;
    						}else if (StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getRenew3(), "0")) {
    							count3 = count3 + 1;
    						}else if (StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getRenew4(), "0")) {
    							count4 = count4 + 1;
    						}
						}
        				if (count1 >= 1) {
    						unit.setRenew1("0");
    		            }else {
    		            	unit.setRenew1("");
						}
    					if (count2 >= 1) {
    		            	unit.setRenew2("0");
    		            }else {
    		            	unit.setRenew2("");
							
						}
    					if (count3 >= 1) {
    		            	unit.setRenew3("0");
    		            }else {
    		            	unit.setRenew3("");
						}
    					if (count4 >= 1) {
    		            	unit.setRenew4("0");
    		            }else {
    		            	unit.setRenew4("");
						}
        				unit.setModifyUserInfo(userKey, realName);
        				zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(unit);
        			}
        		}else {
        			delUnit.add(unit);
        			if(delUnit != null && delUnit.size() >0) {
        				//删除主附件
        				for (ZjTzDesignAdvistoryUnit delUnitFile : delUnit) {
        					ZjTzFile zjTzFileUnit = new ZjTzFile();
        					zjTzFileUnit.setOtherId(delUnitFile.getDesignAdvistoryUnitId());
        					List<ZjTzFile> deleteZjTzFileUnitList = zjTzFileMapper.selectByZjTzFileList(zjTzFileUnit);
        					if(deleteZjTzFileUnitList != null && deleteZjTzFileUnitList.size()>0) {
        						zjTzFileUnit.setModifyUserInfo(userKey, realName);
        						zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileUnit);
        					}
        				}
        				unit.setRenew1("");
        				unit.setRenew2("");
        				unit.setRenew3("");
        				unit.setRenew4("");
        				unit.setModifyUserInfo(userKey, realName);
        				zjTzDesignAdvistoryUnitMapper.batchDeleteUpdateZjTzDesignAdvistoryUnit(delUnit, unit);
        			}
        		}
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignAdvistoryUnitRecordList);
        }
    }

    @Override
	public ResponseEntity batchReleaseZjTzDesignAdvistoryUnitRecord(List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        int flag = 0;
        if (zjTzDesignAdvistoryUnitRecordList != null && zjTzDesignAdvistoryUnitRecordList.size() > 0) {
        	for (ZjTzDesignAdvistoryUnitRecord zjTzRules : zjTzDesignAdvistoryUnitRecordList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已上报的数据，请重新选择！");
				}else if (StrUtil.equals(zjTzRules.getReleaseId(), "3") && StrUtil.equals("4", ext1)) {
					return repEntity.layerMessage("no", "无法上报，请重新选择!!");
				}
				ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzRules.getDesignAdvistoryUnitId());
				if (zjTzDesignAdvistoryUnit != null && StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId())) {
					if (StrUtil.equals("4", ext1)) {
						zjTzDesignAdvistoryUnit.setRenew3("0");
						zjTzDesignAdvistoryUnit.setRenew1("");
    				}else if (StrUtil.equals("3", ext1)) {
    					zjTzDesignAdvistoryUnit.setRenew4("0");
    					zjTzDesignAdvistoryUnit.setRenew3("");
    					zjTzDesignAdvistoryUnit.setRenew2("");
    				}else if (StrUtil.equals("2", ext1)) {
    					zjTzDesignAdvistoryUnit.setRenew4("0");
    					zjTzDesignAdvistoryUnit.setRenew3("");
    					zjTzDesignAdvistoryUnit.setRenew2("");
    				}
					zjTzDesignAdvistoryUnit.setModifyUserInfo(userKey, realName);
					flag = zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(zjTzDesignAdvistoryUnit);
				}
        	}
        	ZjTzDesignAdvistoryUnitRecord zjTzRules = new ZjTzDesignAdvistoryUnitRecord();
//        	zjTzRules.setReleaseId("1");
//        	zjTzRules.setReleaseName("已上报");
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setReleaseId("3");
            	zjTzRules.setReleaseName("托管项目上报");
			}else {
				zjTzRules.setReleaseId("1");
	        	zjTzRules.setReleaseName("已上报");
			}
        	
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setRenew3("0");
        		zjTzRules.setRenew1("");
			}else if (StrUtil.equals("3", ext1)) {
				zjTzRules.setRenew4("0");
				zjTzRules.setRenew3("");
				zjTzRules.setRenew2("");
			}else if (StrUtil.equals("2", ext1)) {
				zjTzRules.setRenew4("0");
				zjTzRules.setRenew3("");
				zjTzRules.setRenew2("");
			}
        	
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignAdvistoryUnitRecordMapper.batchRecallZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList, zjTzRules);
        
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitRecordList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzDesignAdvistoryUnitRecord(List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignAdvistoryUnitRecordList != null && zjTzDesignAdvistoryUnitRecordList.size() > 0) {
        	for (ZjTzDesignAdvistoryUnitRecord zjTzRules : zjTzDesignAdvistoryUnitRecordList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0") || StrUtil.equals(zjTzRules.getReleaseId(), "2")) {
        			return repEntity.layerMessage("no", "包含未上报或被撤回的数据，请重新选择！");
        		}
        		ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzRules.getDesignAdvistoryUnitId());
				if (zjTzDesignAdvistoryUnit != null && StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId())) {
					zjTzDesignAdvistoryUnit.setRenew1("0");
					zjTzDesignAdvistoryUnit.setRenew2("0");
					zjTzDesignAdvistoryUnit.setRenew3("");
					zjTzDesignAdvistoryUnit.setRenew4("");
					zjTzDesignAdvistoryUnit.setModifyUserInfo(userKey, realName);
					flag = zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(zjTzDesignAdvistoryUnit);
				}
        	}
        	ZjTzDesignAdvistoryUnitRecord zjTzRules = new ZjTzDesignAdvistoryUnitRecord();
        	zjTzRules.setReleaseId("2");
        	zjTzRules.setReleaseName("被退回");
        	zjTzRules.setRenew1("0");
        	zjTzRules.setRenew2("0");
        	zjTzRules.setRenew3("");
        	zjTzRules.setRenew4("");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignAdvistoryUnitRecordMapper.batchRecallZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitRecordList);
        }
	}
	
    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordAllList(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
    	if (zjTzDesignAdvistoryUnitRecord == null) {
    		zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
    	}
    	// 分页查询
    	PageHelper.startPage(zjTzDesignAdvistoryUnitRecord.getPage(),zjTzDesignAdvistoryUnitRecord.getLimit());
    	// 获取数据
    	List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
    	for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
    		ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
    		zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitId());
    		List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
    		zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);
    	}
    	// 得到分页信息
    	PageInfo<ZjTzDesignAdvistoryUnitRecord> p = new PageInfo<>(zjTzDesignAdvistoryUnitRecordList);

    	return repEntity.okList(zjTzDesignAdvistoryUnitRecordList, p.getTotal());
    }

	@Override
	public ResponseEntity evaluateZjTzDesignAdvistoryUnitRecord(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        ZjTzDesignAdvistoryUnitRecord dbzjTzDesignAdvistoryUnitRecord = zjTzDesignAdvistoryUnitRecordMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
	        if (dbzjTzDesignAdvistoryUnitRecord != null && StrUtil.isNotEmpty(dbzjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId())) {
	        	if(StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getReleaseId(), "0") || StrUtil.equals(dbzjTzDesignAdvistoryUnitRecord.getReleaseId(), "2")) {
	        		return repEntity.layerMessage("no", "只有已上报的数据才能进行评价");
	        	}
	        	// 评价等级id
	           dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderId(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
	           // 评价等级name
	           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId())) {
	           	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
	           	dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderName(openBankName);
	           }
	           //评价人
	           dbzjTzDesignAdvistoryUnitRecord.setEvaluatePerson(zjTzDesignAdvistoryUnitRecord.getEvaluatePerson());
	           //评价时间
	           dbzjTzDesignAdvistoryUnitRecord.setEvaluateDate(zjTzDesignAdvistoryUnitRecord.getEvaluateDate());
	           // 共通
	           dbzjTzDesignAdvistoryUnitRecord.setModifyUserInfo(userKey, realName);
	           flag = zjTzDesignAdvistoryUnitRecordMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitRecord);
	        }
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitRecord);
	        }
	}

	@Override
	public ResponseEntity getZjTzDesignAdvistoryUnitRecordTotalList(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
		if (zjTzDesignAdvistoryUnitRecord == null) {
            zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
        }
		
        // 分页查询
        PageHelper.startPage(zjTzDesignAdvistoryUnitRecord.getPage(),zjTzDesignAdvistoryUnitRecord.getLimit());
        // 获取数据
        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);

             //资质
             ZjTzQuality zjTzQuality = new ZjTzQuality();
             zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitStandardId());
             List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
             String correspondQualityName = "";
             for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
            	 ZjTzFile zjTzFileQuality = new ZjTzFile();
            	 zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
            	 List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
            	 zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
            	 correspondQualityName += "'" + zjTzQuality2.getCorrespondQualityName() + "',";
            	 if(correspondQualityName.indexOf(",")>0) {
            		 correspondQualityName = correspondQualityName.substring(0, correspondQualityName.length()-1);
            	 }

             }
             zjTzDesignAdvistoryUnitRecord2.setZjTzQualityList(zjTzQualityList);
             zjTzDesignAdvistoryUnitRecord2.setCorrespondQualityName(correspondQualityName);
		}
        // 得到分页信息
        PageInfo<ZjTzDesignAdvistoryUnitRecord> p = new PageInfo<>(zjTzDesignAdvistoryUnitRecordList);

        return repEntity.okList(zjTzDesignAdvistoryUnitRecordList, p.getTotal());
	}

//	@Override
//	public List<ZjTzDesignAdvistoryUnitRecord> reportZjTzDesignAdvistoryUnitRecordTotalList(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
//		if (zjTzDesignAdvistoryUnitRecord == null) {
//            zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
//        }
//        // 获取数据
//        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
//        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
//        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
//             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
//             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
//             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);
//
//             //资质
//             ZjTzQuality zjTzQuality = new ZjTzQuality();
//             zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitStandardId());
//             List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
//             String correspondQualityName = "";
//             for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
//            	 ZjTzFile zjTzFileQuality = new ZjTzFile();
//            	 zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
//            	 List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
//            	 zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
//            	 correspondQualityName += "'" + zjTzQuality2.getCorrespondQualityName() + "',";
//            	 if(correspondQualityName.indexOf(",")>0) {
//            		 correspondQualityName = correspondQualityName.substring(0, correspondQualityName.length()-1);
//            	 }
//
//             }
//             zjTzDesignAdvistoryUnitRecord2.setZjTzQualityList(zjTzQualityList);
//             zjTzDesignAdvistoryUnitRecord2.setCorrespondQualityName(correspondQualityName);
//		}
//        return zjTzDesignAdvistoryUnitRecordList;
//	}

	@Override
	public ResponseEntity reportZjTzDesignAdvistoryUnitRecordTotalList(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord, HttpServletResponse response) {
			if (zjTzDesignAdvistoryUnitRecord == null) {
	      zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
	  }
			// 获取数据
        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);

             //资质
             ZjTzQuality zjTzQuality = new ZjTzQuality();
             zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitStandardId());
             List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
             String correspondQualityName = "";
             for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
            	 ZjTzFile zjTzFileQuality = new ZjTzFile();
            	 zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
            	 List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
            	 zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
            	 correspondQualityName += "'" + zjTzQuality2.getCorrespondQualityName() + "',";
            	 if(correspondQualityName.indexOf(",")>0) {
            		 correspondQualityName = correspondQualityName.substring(0, correspondQualityName.length()-1);
            	 }

             }
             zjTzDesignAdvistoryUnitRecord2.setZjTzQualityList(zjTzQualityList);
             zjTzDesignAdvistoryUnitRecord2.setCorrespondQualityName(correspondQualityName);
		}
	  
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1= CollUtil.newArrayList();
        // 设计单位
        if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "1")) {
        	row1 = CollUtil.newArrayList("评价等级","设计单位全称","资质等级","参与项目名称","子项目名称","设计单位选定方式","参与设计阶段","承担设计工作内容","对应标段估算/概算（万元）","设计费（万元）","备注");
		}else if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "2")) {
			// 咨询单位
			row1 = CollUtil.newArrayList("评价等级","咨询单位全称","资质等级","参与项目名称","子项目名称","咨询单位选定方式","参与设计阶段","承担设计工作内容","预估成效（万元）","咨询费（万元）","备注");
		}
        rowsList.add(row1);
        

     // 数据装载（与上面的表头对应）
        if(zjTzDesignAdvistoryUnitRecordList != null && zjTzDesignAdvistoryUnitRecordList.size()>0) {
            for(ZjTzDesignAdvistoryUnitRecord dbzjTzDesignAdvistoryUnitRecord:zjTzDesignAdvistoryUnitRecordList) {
                rowsList.add(CollUtil.newArrayList(
                		dbzjTzDesignAdvistoryUnitRecord.getEvaluateOrderName(),
                		dbzjTzDesignAdvistoryUnitRecord.getUnitName(),
                		dbzjTzDesignAdvistoryUnitRecord.getCorrespondQualityName(),
                		dbzjTzDesignAdvistoryUnitRecord.getProjectName(),
                		dbzjTzDesignAdvistoryUnitRecord.getSubprojectName(),
                		dbzjTzDesignAdvistoryUnitRecord.getSelectModeName(),
                		dbzjTzDesignAdvistoryUnitRecord.getDesignStageName(),
                		dbzjTzDesignAdvistoryUnitRecord.getContent(),
                		dbzjTzDesignAdvistoryUnitRecord.getAmount1(),
                		dbzjTzDesignAdvistoryUnitRecord.getAmount2(),
                		dbzjTzDesignAdvistoryUnitRecord.getRemarks()
                        )
                );
            }
        

            // 报表名称
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
//            String fileName = "xxx报表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
         // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.setColumnWidth(0, 12);//评价等级
            writer.setColumnWidth(1, 50);//设计单位全称
            writer.setColumnWidth(2, 10);//资质等级
            writer.setColumnWidth(3, 50);//参与项目名称
            writer.setColumnWidth(4, 50);//子项目名称
            writer.setColumnWidth(5, 18);//设计单位选定方式
            writer.setColumnWidth(6, 12);//参与设计阶段
            writer.setColumnWidth(7, 100);//承担设计工作内容
            writer.setColumnWidth(8, 25);//对应标段估算/概算（万元）
            writer.setColumnWidth(9, 18);//设计费（万元）	
            writer.setColumnWidth(10, 12);//备注
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭writer，释放内存
                if (writer != null) {
                    writer.close();
                }
                // 此处记得关闭输出Servlet流
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
           return null;
        } else {
            return repEntity.ok("无数据");
        }
	}

	@Override
	public ResponseEntity checkAndFinishZjTzDesignAdvistoryUnitRecord(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignAdvistoryUnitRecord dbzjTzDesignAdvistoryUnitRecord = zjTzDesignAdvistoryUnitRecordMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
    	if (dbzjTzDesignAdvistoryUnitRecord != null && StrUtil.isNotEmpty(dbzjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId())) {
    			//是最新的一条数据，可以上报
	    		dbzjTzDesignAdvistoryUnitRecord.setRenew1("");
	    		dbzjTzDesignAdvistoryUnitRecord.setRenew2("");
	    		dbzjTzDesignAdvistoryUnitRecord.setRenew3("");
	    		dbzjTzDesignAdvistoryUnitRecord.setRenew4("");
	    		dbzjTzDesignAdvistoryUnitRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzDesignAdvistoryUnitRecordMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitRecord);
    			
    			ZjTzDesignAdvistoryUnitRecord record = new ZjTzDesignAdvistoryUnitRecord();
        		record.setDesignAdvistoryUnitId(dbzjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        		List<ZjTzDesignAdvistoryUnitRecord> records = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(record);
        		
        		int i = 0;
        		if (records != null && records.size() > 0) {
    				for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : records) {
    					if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord2.getRenew4(), "0")) {
    						i = i + 1;
    					}
    				}
    			}
        		
    			if (i == 0) {
    				//执行修改主表
    				ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(dbzjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
    				if(zjTzDesignAdvistoryUnit != null && StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId())) {
    					zjTzDesignAdvistoryUnit.setRenew1("");
    					zjTzDesignAdvistoryUnit.setRenew2("");
    					zjTzDesignAdvistoryUnit.setRenew3("");
    					zjTzDesignAdvistoryUnit.setRenew4("");
    					flag = zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(zjTzDesignAdvistoryUnit);
    				}
				}
    	}
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitRecord);
        }
	}
        
    
}