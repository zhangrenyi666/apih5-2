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
    	// ????????????????????????
//        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//           
//        } else {
//            // ???????????????
//        	zjTzDesignAdvistoryUnitRecord.setExt1SeeFlag("??????????????????????????????????????????????????????");
//        }
        // ????????????????????????????????????????????????????????????
        if (StrUtil.equals("3", ext1)) {
            // ???????????????????????????????????????sql?????????
        	zjTzDesignAdvistoryUnitRecord.setExt1Flag1("????????????????????????????????????????????????????????????");
        //	???????????????????????????????????????
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzDesignAdvistoryUnitRecord.setExt1Flag2("???????????????????????????????????????");;
        // ???????????????????????????????????????????????????????????????	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzDesignAdvistoryUnitRecord.setExt1Flag3("???????????????????????????????????????????????????????????????");
		}
        // ????????????
        PageHelper.startPage(zjTzDesignAdvistoryUnitRecord.getPage(),zjTzDesignAdvistoryUnitRecord.getLimit());
        // ????????????
        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);
             
             //??????
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
        // ??????????????????
        PageInfo<ZjTzDesignAdvistoryUnitRecord> p = new PageInfo<>(zjTzDesignAdvistoryUnitRecordList);

        return repEntity.okList(zjTzDesignAdvistoryUnitRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordDetails(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        if (zjTzDesignAdvistoryUnitRecord == null) {
            zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
        }
        // ????????????
        ZjTzDesignAdvistoryUnitRecord dbZjTzDesignAdvistoryUnitRecord = zjTzDesignAdvistoryUnitRecordMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        // ????????????
        if (dbZjTzDesignAdvistoryUnitRecord != null) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(dbZjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             dbZjTzDesignAdvistoryUnitRecord.setZjTzFileList(zjTzFileList);
             
             //??????
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
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignAdvistoryUnitRecord(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(StrUtil.isEmpty(zjTzDesignAdvistoryUnitRecord.getTypeId())) {
        	return repEntity.layerMessage("no", "???????????????????????????????????????");
        }else {
        	if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "1")) {
        		zjTzDesignAdvistoryUnitRecord.setTypeName("??????");
        	}else if(StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "2")) {
        		zjTzDesignAdvistoryUnitRecord.setTypeName("??????");
        	}
        }
        //???????????????
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getSubprojectInfoId());
        if(info != null) {
        	zjTzDesignAdvistoryUnitRecord.setSubprojectName(info.getSubprojectName());
        }
        ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getProjectId());
        if (dbzjTzProManage != null) {
        	zjTzDesignAdvistoryUnitRecord.setProjectName(dbzjTzProManage.getProjectName());
		}
        zjTzDesignAdvistoryUnitRecord.setDesignAdvistoryUnitRecordId(UuidUtil.generate());
        //????????????  1.????????????   2.????????????   3.???????????????
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getDesignStageId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignAdvistoryUnitRecord.getDesignStageId());
        	zjTzDesignAdvistoryUnitRecord.setDesignStageName(openBankName);
        	//????????????????????????????????????
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
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getSelectModeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("xuanDiFangShi", zjTzDesignAdvistoryUnitRecord.getSelectModeId());
        	zjTzDesignAdvistoryUnitRecord.setSelectModeName(openBankName);
        }
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
        	zjTzDesignAdvistoryUnitRecord.setEvaluateOrderName(openBankName);
        }
        zjTzDesignAdvistoryUnitRecord.setReleaseId("0");
        zjTzDesignAdvistoryUnitRecord.setReleaseName("?????????");
        zjTzDesignAdvistoryUnitRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignAdvistoryUnitRecordMapper.insert(zjTzDesignAdvistoryUnitRecord);
        // ??????list
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
        		return repEntity.layerMessage("no", "??????????????????????????????");
        	}
        	
        	// ??????????????????????????????id
           dbzjTzDesignAdvistoryUnitRecord.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitStandardId());
           // ????????????id
           dbzjTzDesignAdvistoryUnitRecord.setDesignStageId(zjTzDesignAdvistoryUnitRecord.getDesignStageId());
           //????????????name   1.????????????   2.????????????   3.???????????????
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getDesignStageId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignAdvistoryUnitRecord.getDesignStageId());
        	   dbzjTzDesignAdvistoryUnitRecord.setDesignStageName(openBankName);
        	   //????????????????????????????????????
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
           // ????????????id
           dbzjTzDesignAdvistoryUnitRecord.setSelectModeId(zjTzDesignAdvistoryUnitRecord.getSelectModeId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getSelectModeId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("xuanDiFangShi", zjTzDesignAdvistoryUnitRecord.getSelectModeId());
           	dbzjTzDesignAdvistoryUnitRecord.setSelectModeName(openBankName);
           }
           // ????????????
           dbzjTzDesignAdvistoryUnitRecord.setContent(zjTzDesignAdvistoryUnitRecord.getContent());
           // ??????1
           dbzjTzDesignAdvistoryUnitRecord.setAmount1(zjTzDesignAdvistoryUnitRecord.getAmount1());
           // ??????2
           dbzjTzDesignAdvistoryUnitRecord.setAmount2(zjTzDesignAdvistoryUnitRecord.getAmount2());
           // ???????????????
           dbzjTzDesignAdvistoryUnitRecord.setProLeader(zjTzDesignAdvistoryUnitRecord.getProLeader());
           // ???????????????
           dbzjTzDesignAdvistoryUnitRecord.setProLeaderTel(zjTzDesignAdvistoryUnitRecord.getProLeaderTel());
           // ????????????id
           dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderId(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
           // ????????????name
           //????????????
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
           	dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderName(openBankName);
           }
           // ??????
           dbzjTzDesignAdvistoryUnitRecord.setRemarks(zjTzDesignAdvistoryUnitRecord.getRemarks());
           // ??????
           dbzjTzDesignAdvistoryUnitRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignAdvistoryUnitRecordMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitRecord);
           
           //????????????????????????
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
        // ??????
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
        			return repEntity.layerMessage("no", "?????????????????????????????????");
        		}
        	}
        	ZjTzDesignAdvistoryUnitRecord dbRecord = new ZjTzDesignAdvistoryUnitRecord();
        	dbRecord.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignAdvistoryUnitRecordMapper.batchDeleteUpdateZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList, dbRecord);
        	
        	delSize = zjTzDesignAdvistoryUnitRecordList.size();
        	for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord : zjTzDesignAdvistoryUnitRecordList) {
        		//????????????
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitRecordId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		
        		List<ZjTzDesignAdvistoryUnit> delUnit = new ArrayList<>();
        		ZjTzDesignAdvistoryUnit unit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        		//1.?????????????????????????????????????????????????????? 
        		ZjTzDesignAdvistoryUnitRecord record = new ZjTzDesignAdvistoryUnitRecord();
        		record.setDesignAdvistoryUnitId(zjTzDesignAdvistoryUnitRecord.getDesignAdvistoryUnitId());
        		List<ZjTzDesignAdvistoryUnitRecord> recordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(record);
        		if(recordList != null && recordList.size() >0) {
        			totalSize = recordList.size();
        		}
        		if(totalSize -delSize >0) {
        			//???????????????????????????==????????????????????????????????????
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
        				//???????????????
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
        // ??????
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
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}else if (StrUtil.equals(zjTzRules.getReleaseId(), "3") && StrUtil.equals("4", ext1)) {
					return repEntity.layerMessage("no", "??????????????????????????????!!");
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
//        	zjTzRules.setReleaseName("?????????");
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setReleaseId("3");
            	zjTzRules.setReleaseName("??????????????????");
			}else {
				zjTzRules.setReleaseId("1");
	        	zjTzRules.setReleaseName("?????????");
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
        // ??????
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
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
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
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setRenew1("0");
        	zjTzRules.setRenew2("0");
        	zjTzRules.setRenew3("");
        	zjTzRules.setRenew4("");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignAdvistoryUnitRecordMapper.batchRecallZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList, zjTzRules);
        }
        // ??????
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
    	// ????????????
    	PageHelper.startPage(zjTzDesignAdvistoryUnitRecord.getPage(),zjTzDesignAdvistoryUnitRecord.getLimit());
    	// ????????????
    	List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
    	for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
    		ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
    		zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitId());
    		List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
    		zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);
    	}
    	// ??????????????????
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
	        		return repEntity.layerMessage("no", "??????????????????????????????????????????");
	        	}
	        	// ????????????id
	           dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderId(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
	           // ????????????name
	           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId())) {
	           	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnitRecord.getEvaluateOrderId());
	           	dbzjTzDesignAdvistoryUnitRecord.setEvaluateOrderName(openBankName);
	           }
	           //?????????
	           dbzjTzDesignAdvistoryUnitRecord.setEvaluatePerson(zjTzDesignAdvistoryUnitRecord.getEvaluatePerson());
	           //????????????
	           dbzjTzDesignAdvistoryUnitRecord.setEvaluateDate(zjTzDesignAdvistoryUnitRecord.getEvaluateDate());
	           // ??????
	           dbzjTzDesignAdvistoryUnitRecord.setModifyUserInfo(userKey, realName);
	           flag = zjTzDesignAdvistoryUnitRecordMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitRecord);
	        }
	        // ??????
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
		
        // ????????????
        PageHelper.startPage(zjTzDesignAdvistoryUnitRecord.getPage(),zjTzDesignAdvistoryUnitRecord.getLimit());
        // ????????????
        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);

             //??????
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
        // ??????????????????
        PageInfo<ZjTzDesignAdvistoryUnitRecord> p = new PageInfo<>(zjTzDesignAdvistoryUnitRecordList);

        return repEntity.okList(zjTzDesignAdvistoryUnitRecordList, p.getTotal());
	}

//	@Override
//	public List<ZjTzDesignAdvistoryUnitRecord> reportZjTzDesignAdvistoryUnitRecordTotalList(ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
//		if (zjTzDesignAdvistoryUnitRecord == null) {
//            zjTzDesignAdvistoryUnitRecord = new ZjTzDesignAdvistoryUnitRecord();
//        }
//        // ????????????
//        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
//        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
//        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
//             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
//             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
//             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);
//
//             //??????
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
			// ????????????
        List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList = zjTzDesignAdvistoryUnitRecordMapper.selectByZjTzDesignAdvistoryUnitRecordList(zjTzDesignAdvistoryUnitRecord);
        for (ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord2 : zjTzDesignAdvistoryUnitRecordList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnitRecord2.getDesignAdvistoryUnitRecordId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnitRecord2.setZjTzFileList(zjTzFileList);

             //??????
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
	  
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1= CollUtil.newArrayList();
        // ????????????
        if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "1")) {
        	row1 = CollUtil.newArrayList("????????????","??????????????????","????????????","??????????????????","???????????????","????????????????????????","??????????????????","????????????????????????","??????????????????/??????????????????","?????????????????????","??????");
		}else if (StrUtil.equals(zjTzDesignAdvistoryUnitRecord.getTypeId(), "2")) {
			// ????????????
			row1 = CollUtil.newArrayList("????????????","??????????????????","????????????","??????????????????","???????????????","????????????????????????","??????????????????","????????????????????????","????????????????????????","?????????????????????","??????");
		}
        rowsList.add(row1);
        

     // ??????????????????????????????????????????
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
        

            // ????????????
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
//            String fileName = "xxx??????-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
         // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.setColumnWidth(0, 12);//????????????
            writer.setColumnWidth(1, 50);//??????????????????
            writer.setColumnWidth(2, 10);//????????????
            writer.setColumnWidth(3, 50);//??????????????????
            writer.setColumnWidth(4, 50);//???????????????
            writer.setColumnWidth(5, 18);//????????????????????????
            writer.setColumnWidth(6, 12);//??????????????????
            writer.setColumnWidth(7, 100);//????????????????????????
            writer.setColumnWidth(8, 25);//??????????????????/??????????????????
            writer.setColumnWidth(9, 18);//?????????????????????	
            writer.setColumnWidth(10, 12);//??????
            // ??????response????????????
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // ???????????????????????????????????????????????????????????????
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // ??????writer???????????????
                if (writer != null) {
                    writer.close();
                }
                // ????????????????????????Servlet???
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
           return null;
        } else {
            return repEntity.ok("?????????");
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
    			//???????????????????????????????????????
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
    				//??????????????????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitRecord);
        }
	}
        
    
}