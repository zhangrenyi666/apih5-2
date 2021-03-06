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
        // ????????????
        PageHelper.startPage(zjTzDesignAdvistoryUnit.getPage(),zjTzDesignAdvistoryUnit.getLimit());
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzDesignAdvistoryUnit.getProjectId(), true)) {
            	zjTzDesignAdvistoryUnit.setProjectId("");
            	zjTzDesignAdvistoryUnit.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzDesignAdvistoryUnit.getProjectId(), true)) {
            	zjTzDesignAdvistoryUnit.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzDesignAdvistoryUnit> zjTzDesignAdvistoryUnitList = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(zjTzDesignAdvistoryUnit);
        for (ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit2 : zjTzDesignAdvistoryUnitList) {
        	 ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(zjTzDesignAdvistoryUnit2.getDesignAdvistoryUnitId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             zjTzDesignAdvistoryUnit2.setZjTzFileList(zjTzFileList);
             //??????
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
         	// ?????????????????????????????????????????????????????????
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
        // ??????????????????
        PageInfo<ZjTzDesignAdvistoryUnit> p = new PageInfo<>(zjTzDesignAdvistoryUnitList);

        return repEntity.okList(zjTzDesignAdvistoryUnitList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitDetails(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        if (zjTzDesignAdvistoryUnit == null) {
            zjTzDesignAdvistoryUnit = new ZjTzDesignAdvistoryUnit();
        }
        // ????????????
        ZjTzDesignAdvistoryUnit dbZjTzDesignAdvistoryUnit = zjTzDesignAdvistoryUnitMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
        // ????????????
        if (dbZjTzDesignAdvistoryUnit != null) {
        	ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
            zjTzFileContractConditionSelect.setOtherId(dbZjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
            dbZjTzDesignAdvistoryUnit.setZjTzFileList(zjTzFileList);

            //??????
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
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignAdvistoryUnit(ZjTzDesignAdvistoryUnit zjTzDesignAdvistoryUnit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(StrUtil.isEmpty(zjTzDesignAdvistoryUnit.getTypeId())) {
        	return repEntity.layerMessage("no", "???????????????????????????????????????");
        }else {
        	if(StrUtil.equals(zjTzDesignAdvistoryUnit.getTypeId(), "1")) {
        		zjTzDesignAdvistoryUnit.setTypeName("??????");
        	}else if(StrUtil.equals(zjTzDesignAdvistoryUnit.getTypeId(), "2")) {
        		zjTzDesignAdvistoryUnit.setTypeName("??????");
        	}
        }
    	//????????????id????????????id   ????????????
    	if(StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getSubprojectInfoId())) {
    		ZjTzDesignAdvistoryUnit zjTzSizeControlSelect = new ZjTzDesignAdvistoryUnit();
    		zjTzSizeControlSelect.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
    		zjTzSizeControlSelect.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
    		List<ZjTzDesignAdvistoryUnit> sizeControls = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		//???????????????
    		ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
    		if(info != null) {
    			zjTzDesignAdvistoryUnit.setSubprojectName(info.getSubprojectName());
    		}
    	}else {
    		//????????????????????????  = ?????????id??????
    		ZjTzDesignAdvistoryUnit zjTzSizeControlSelect = new ZjTzDesignAdvistoryUnit();
    		zjTzSizeControlSelect.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
    		zjTzSizeControlSelect.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId("???");
    		List<ZjTzDesignAdvistoryUnit> sizeControls = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		zjTzDesignAdvistoryUnit.setSubprojectInfoId("???");
    	}
        zjTzDesignAdvistoryUnit.setDesignAdvistoryUnitId(UuidUtil.generate());
        //????????????  1.????????????   2.????????????   3.???????????????
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
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getSelectModeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("xuanDiFangShi", zjTzDesignAdvistoryUnit.getSelectModeId());
        	zjTzDesignAdvistoryUnit.setSelectModeName(openBankName);
        }
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnit.getEvaluateOrderId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("pingJiaDengJi", zjTzDesignAdvistoryUnit.getEvaluateOrderId());
        	zjTzDesignAdvistoryUnit.setEvaluateOrderName(openBankName);
        }
        
        zjTzDesignAdvistoryUnit.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignAdvistoryUnitMapper.insert(zjTzDesignAdvistoryUnit);
        // ??????list
    	List<ZjTzFile> zjTzFileList = zjTzDesignAdvistoryUnit.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//add??????
        ZjTzDesignAdvistoryUnitRecord record = new ZjTzDesignAdvistoryUnitRecord();
        record.setDesignAdvistoryUnitRecordId(UuidUtil.generate());
        // ???????????????????????????id
        record.setDesignAdvistoryUnitId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitId());
        // ??????id
        record.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
        // ??????name
        record.setTypeName(zjTzDesignAdvistoryUnit.getTypeName());
        // ??????????????????????????????id
        record.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitStandardId());
        // ??????id
        record.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
        // ??????name
        record.setProjectName(zjTzDesignAdvistoryUnit.getProjectName());
        // ?????????id
        record.setSubprojectInfoId(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
        // ?????????name
        record.setSubprojectName(zjTzDesignAdvistoryUnit.getSubprojectName());
        // ????????????id
        record.setDesignStageId(zjTzDesignAdvistoryUnit.getDesignStageId());
        // ????????????name
        record.setDesignStageName(zjTzDesignAdvistoryUnit.getDesignStageName());
        // ????????????id
        record.setSelectModeId(zjTzDesignAdvistoryUnit.getSelectModeId());
        // ????????????name
        record.setSelectModeName(zjTzDesignAdvistoryUnit.getSelectModeName());
        // ????????????
        record.setContent(zjTzDesignAdvistoryUnit.getContent());
        // ??????1
        record.setAmount1(zjTzDesignAdvistoryUnit.getAmount1());
        // ??????2
        record.setAmount2(zjTzDesignAdvistoryUnit.getAmount2());
        // ???????????????
        record.setProLeader(zjTzDesignAdvistoryUnit.getProLeader());
        // ???????????????
        record.setProLeaderTel(zjTzDesignAdvistoryUnit.getProLeaderTel());
        // ????????????id
        record.setEvaluateOrderId(zjTzDesignAdvistoryUnit.getEvaluateOrderId());
        // ????????????name
        record.setEvaluateOrderName(zjTzDesignAdvistoryUnit.getEvaluateOrderName());
        record.setReleaseId("0");
        record.setReleaseName("?????????");
        // ??????
        record.setRemarks(zjTzDesignAdvistoryUnit.getRemarks());
        record.setCreateUserInfo(userKey, realName);
        zjTzDesignAdvistoryUnitRecordMapper.insert(record);
        // ??????list
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
           // ??????id
           dbzjTzDesignAdvistoryUnit.setTypeId(zjTzDesignAdvistoryUnit.getTypeId());
           // ??????name
           dbzjTzDesignAdvistoryUnit.setTypeName(zjTzDesignAdvistoryUnit.getTypeName());
           // ??????????????????????????????id
           dbzjTzDesignAdvistoryUnit.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnit.getDesignAdvistoryUnitStandardId());
           // ??????id
           dbzjTzDesignAdvistoryUnit.setProjectId(zjTzDesignAdvistoryUnit.getProjectId());
           // ??????name
           dbzjTzDesignAdvistoryUnit.setProjectName(zjTzDesignAdvistoryUnit.getProjectName());
           // ?????????id
           dbzjTzDesignAdvistoryUnit.setSubprojectInfoId(zjTzDesignAdvistoryUnit.getSubprojectInfoId());
           // ?????????name
           dbzjTzDesignAdvistoryUnit.setSubprojectName(zjTzDesignAdvistoryUnit.getSubprojectName());
           // ????????????id
           dbzjTzDesignAdvistoryUnit.setDesignStageId(zjTzDesignAdvistoryUnit.getDesignStageId());
           // ????????????name
           dbzjTzDesignAdvistoryUnit.setDesignStageName(zjTzDesignAdvistoryUnit.getDesignStageName());
           // ????????????id
           dbzjTzDesignAdvistoryUnit.setSelectModeId(zjTzDesignAdvistoryUnit.getSelectModeId());
           // ????????????name
           dbzjTzDesignAdvistoryUnit.setSelectModeName(zjTzDesignAdvistoryUnit.getSelectModeName());
           // ????????????
           dbzjTzDesignAdvistoryUnit.setContent(zjTzDesignAdvistoryUnit.getContent());
           // ??????1
           dbzjTzDesignAdvistoryUnit.setAmount1(zjTzDesignAdvistoryUnit.getAmount1());
           // ??????2
           dbzjTzDesignAdvistoryUnit.setAmount2(zjTzDesignAdvistoryUnit.getAmount2());
           // ???????????????
           dbzjTzDesignAdvistoryUnit.setProLeader(zjTzDesignAdvistoryUnit.getProLeader());
           // ???????????????
           dbzjTzDesignAdvistoryUnit.setProLeaderTel(zjTzDesignAdvistoryUnit.getProLeaderTel());
           // ????????????id
           dbzjTzDesignAdvistoryUnit.setEvaluateOrderId(zjTzDesignAdvistoryUnit.getEvaluateOrderId());
           // ????????????name
           dbzjTzDesignAdvistoryUnit.setEvaluateOrderName(zjTzDesignAdvistoryUnit.getEvaluateOrderName());
           // ??????
           dbzjTzDesignAdvistoryUnit.setRemarks(zjTzDesignAdvistoryUnit.getRemarks());
           // ??????
           dbzjTzDesignAdvistoryUnit.setTotal1Amount1(zjTzDesignAdvistoryUnit.getTotal1Amount1());
           // ??????
           dbzjTzDesignAdvistoryUnit.setTotal1Amount2(zjTzDesignAdvistoryUnit.getTotal1Amount2());
           // ??????
           dbzjTzDesignAdvistoryUnit.setTotal2Amount1(zjTzDesignAdvistoryUnit.getTotal2Amount1());
           // ??????
           dbzjTzDesignAdvistoryUnit.setTotal2Amount2(zjTzDesignAdvistoryUnit.getTotal2Amount2());
           // ??????
           dbzjTzDesignAdvistoryUnit.setTotal3Amount1(zjTzDesignAdvistoryUnit.getTotal3Amount1());
           // ??????
           dbzjTzDesignAdvistoryUnit.setTotal3Amount2(zjTzDesignAdvistoryUnit.getTotal3Amount2());
           // ??????
           dbzjTzDesignAdvistoryUnit.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignAdvistoryUnitMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnit);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignAdvistoryUnitList);
        }
    }
}
