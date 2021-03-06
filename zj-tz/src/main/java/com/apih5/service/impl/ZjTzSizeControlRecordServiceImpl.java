package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.apih5.mybatis.dao.ZjTzContractConditionMapper;
import com.apih5.mybatis.dao.ZjTzContractConditionRecordMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzSizeControlMapper;
import com.apih5.mybatis.dao.ZjTzSizeControlRecordMapper;
import com.apih5.mybatis.pojo.ZjTzContractCondition;
import com.apih5.mybatis.pojo.ZjTzContractConditionRecord;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzSizeControlRecord;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSizeControl;
import com.apih5.service.ZjTzSizeControlRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSizeControlRecordService")
public class ZjTzSizeControlRecordServiceImpl implements ZjTzSizeControlRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
	private BaseCodeService baseCodeService;
	
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzSizeControlMapper zjTzSizeControlMapper;
    
    @Autowired(required = true)
    private ZjTzSizeControlRecordMapper zjTzSizeControlRecordMapper;
    
    @Autowired(required = true)
    private ZjTzContractConditionMapper zjTzContractConditionMapper;
    
    @Autowired(required = true)
    private ZjTzContractConditionRecordMapper zjTzContractConditionRecordMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    

    @Override
    public ResponseEntity getZjTzSizeControlRecordListByCondition(ZjTzSizeControlRecord zjTzSizeControlRecord) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        
    	if (zjTzSizeControlRecord == null) {
            zjTzSizeControlRecord = new ZjTzSizeControlRecord();
        } 
        if(StrUtil.isEmpty(zjTzSizeControlRecord.getSizeControlId())){
        	return repEntity.okList(null, 0);
        }
        // ????????????
        PageHelper.startPage(zjTzSizeControlRecord.getPage(),zjTzSizeControlRecord.getLimit());
        
        // ????????????????????????????????????????????????????????????
        if (StrUtil.equals("3", ext1)) {
            // ???????????????????????????????????????sql?????????
        	zjTzSizeControlRecord.setExt1Flag1("????????????????????????????????????????????????????????????");
        //	???????????????????????????????????????
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzSizeControlRecord.setExt1Flag2("???????????????????????????????????????");;
        // ???????????????????????????????????????????????????????????????	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzSizeControlRecord.setExt1Flag3("???????????????????????????????????????????????????????????????");
		}
        
        // ????????????
        BigDecimal damount1 = new BigDecimal(0);
        BigDecimal damount2 = new BigDecimal(0);
        
        List<ZjTzSizeControlRecord> zjTzSizeControlRecordList = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(zjTzSizeControlRecord);
        for (ZjTzSizeControlRecord controlRecord : zjTzSizeControlRecordList) {
        	ZjTzSizeControl sizeControl = zjTzSizeControlMapper.selectByPrimaryKey(controlRecord.getSizeControlId());
        	if(sizeControl != null && StrUtil.isNotEmpty(sizeControl.getSizeControlId())) {
        		controlRecord.setAddFlag(sizeControl.getAddFlag());
        	}
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(controlRecord.getSizeControlRecordId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> schemeFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	controlRecord.setSchemeFileList(schemeFileList);
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> thirdReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	controlRecord.setThirdReplyFileList(thirdReplyFileList);
        	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> localGovFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	controlRecord.setLocalGovFileList(localGovFileList);
        	zjTzFileSelect.setOtherType("4");
        	List<ZjTzFile> juFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	controlRecord.setJuFileList(juFileList);
        	zjTzFileSelect.setOtherType("5");
        	List<ZjTzFile> chinaFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	controlRecord.setChinaFileList(chinaFileList);
        	// 
        	ZjTzContractConditionRecord record = new ZjTzContractConditionRecord();
        	record.setSizeControlRecordId(controlRecord.getSizeControlRecordId());
        	List<ZjTzContractConditionRecord> recordList = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(record);
        	if(recordList != null && recordList.size() >0) {
        		ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
        		zjTzFileContractConditionSelect.setOtherId(recordList.get(0).getContractConditionRecordId());
        		List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
        		controlRecord.setZjTzFileList(zjTzFileList);
        		controlRecord.setCount(zjTzSizeControlRecordList.size());
        	}
        	if (controlRecord.getChangeNumber() == 0 && StrUtil.isNotEmpty(controlRecord.getSubprojectInfoId())) {
        		ZjTzProSubprojectInfo dbzjTzProSubprojectInfo = zjTzProSubprojectInfoMapper.selectByPrimaryKey(controlRecord.getSubprojectInfoId());
        		if (dbzjTzProSubprojectInfo != null && StrUtil.isNotEmpty(dbzjTzProSubprojectInfo.getSubprojectInfoId())) {
        			damount1 = CalcUtils.calcSubtract(dbzjTzProSubprojectInfo.getAmount1(), controlRecord.getAmount1());
        			damount2 = CalcUtils.calcSubtract(dbzjTzProSubprojectInfo.getAmount3(), controlRecord.getAmount2());
				}
        	}else if (controlRecord.getChangeNumber() == 0 && StrUtil.isEmpty(controlRecord.getSubprojectInfoId())) {
        		ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(controlRecord.getProjectId());
        		if (dbzjTzProManage != null && StrUtil.isNotEmpty(dbzjTzProManage.getProjectId())) {
        			damount1 = CalcUtils.calcSubtract(dbzjTzProManage.getAmount1(), controlRecord.getAmount1());
        			damount2 = CalcUtils.calcSubtract(dbzjTzProManage.getAmount3(), controlRecord.getAmount2());
				}
			}else if (controlRecord.getChangeNumber() != 0) {
				ZjTzSizeControlRecord lastRecord = new ZjTzSizeControlRecord();
	    		lastRecord.setSizeControlId(controlRecord.getSizeControlId());
	    		lastRecord.setChangeNumber(controlRecord.getChangeNumber()-1);
	    		List<ZjTzSizeControlRecord> lastRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(lastRecord);
	    		if(lastRecords != null &&lastRecords.size()>0) {
	    			//???????????????????????????aomunt1
	    			damount1 = CalcUtils.calcSubtract(controlRecord.getAmount1(), lastRecords.get(0).getAmount1());
	    			damount2 = CalcUtils.calcSubtract(controlRecord.getAmount2(), lastRecords.get(0).getAmount2());
	    		}
			}
        	controlRecord.setDvalue1(damount1);
        	controlRecord.setDvalue2(damount2);
        	
        }
        // ??????????????????
        PageInfo<ZjTzSizeControlRecord> p = new PageInfo<>(zjTzSizeControlRecordList);

        return repEntity.okList(zjTzSizeControlRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSizeControlRecordDetails(ZjTzSizeControlRecord zjTzSizeControlRecord) {
        if (zjTzSizeControlRecord == null) {
            zjTzSizeControlRecord = new ZjTzSizeControlRecord();
        }
        zjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
        // ????????????
        if (zjTzSizeControlRecord != null && StrUtil.isNotEmpty(zjTzSizeControlRecord.getSizeControlRecordId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> schemeFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSizeControlRecord.setSchemeFileList(schemeFileList);
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> thirdReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSizeControlRecord.setThirdReplyFileList(thirdReplyFileList);
        	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> localGovFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSizeControlRecord.setLocalGovFileList(localGovFileList);
        	zjTzFileSelect.setOtherType("4");
        	List<ZjTzFile> juFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSizeControlRecord.setJuFileList(juFileList);
        	zjTzFileSelect.setOtherType("5");
        	List<ZjTzFile> chinaFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSizeControlRecord.setChinaFileList(chinaFileList);
        	// 
        	ZjTzContractConditionRecord record = new ZjTzContractConditionRecord();
        	record.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
        	List<ZjTzContractConditionRecord> recordList = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(record);
        	if(recordList != null && recordList.size() >0) {
        		ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
        		zjTzFileContractConditionSelect.setOtherId(recordList.get(0).getContractConditionRecordId());
        		List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
        		zjTzSizeControlRecord.setZjTzFileList(zjTzFileList);
        	}
            return repEntity.ok(zjTzSizeControlRecord);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int changeNumber = 0;
        //????????????????????????
    	ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
    	record.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
    	List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
    	if(records != null &&records.size() > 0) {
    		changeNumber = records.size();
    		ZjTzSizeControlRecord lastRecord = new ZjTzSizeControlRecord();
    		lastRecord.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
    		lastRecord.setChangeNumber(changeNumber-1);
    		List<ZjTzSizeControlRecord> lastRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(lastRecord);
    		if(lastRecords != null &&lastRecords.size()>0) {
    			//???????????????????????????aomunt1
    			zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount1(), lastRecords.get(0).getAmount1()));
    			zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount2(), lastRecords.get(0).getAmount2()));
    		}
    	}else {
    		if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getSubprojectInfoId())) {
    			//???????????????
    	        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSubprojectInfoId());
    	        if(info != null) {
    	        	zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(info.getAmount1(), zjTzSizeControlRecord.getAmount1()));
    	        	zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(info.getAmount3(), zjTzSizeControlRecord.getAmount2()));
    	        }
			}else if (StrUtil.isEmpty(zjTzSizeControlRecord.getSubprojectInfoId())) {
				//????????????
				ZjTzProManage zjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzSizeControlRecord.getProjectId());
				if (zjTzProManage != null) {
					zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzProManage.getAmount1(), zjTzSizeControlRecord.getAmount1()));
					zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzProManage.getAmount3(), zjTzSizeControlRecord.getAmount2()));
					
				}
			}
    		zjTzSizeControlRecord.setChangeNumber(changeNumber);
    	}
    	
    	
    	//add
    	//???????????????
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSubprojectInfoId());
        if(info != null) {
        	zjTzSizeControlRecord.setSubprojectName(info.getSubprojectName());
        }
        ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzSizeControlRecord.getProjectId());
        if(dbzjTzProManage != null) {
        	zjTzSizeControlRecord.setProjectName(dbzjTzProManage.getProjectName());
        }
        zjTzSizeControlRecord.setSizeControlRecordId(UuidUtil.generate());
        zjTzSizeControlRecord.setChangeNumber(changeNumber);
        //????????????
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getChangePropertyId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControlRecord.getChangePropertyId());
        	zjTzSizeControlRecord.setChangePropertyName(openBankName);
        }
        //1
        if (StrUtil.equals(zjTzSizeControlRecord.getSecondNegotiateId(), "1")) {
        	zjTzSizeControlRecord.setSecondNegotiateName("???");
        }else {
        	zjTzSizeControlRecord.setSecondNegotiateName("???");
        }
        //2 
        if (StrUtil.equals(zjTzSizeControlRecord.getThirdReplyId(), "1")) {
        	zjTzSizeControlRecord.setThirdReplyName("???");
        }else {
        	zjTzSizeControlRecord.setThirdReplyName("???");
        }
        //3
        if (StrUtil.equals(zjTzSizeControlRecord.getLocalGovId(), "1")) {
        	zjTzSizeControlRecord.setLocalGovName("???");
        }else {
        	zjTzSizeControlRecord.setLocalGovName("???");
        }
        //4
        if (StrUtil.equals(zjTzSizeControlRecord.getJuId(), "1")) {
        	zjTzSizeControlRecord.setJuName("???");
        }else {
        	zjTzSizeControlRecord.setJuName("???");
        }
        //5
        if (StrUtil.equals(zjTzSizeControlRecord.getChinaId(), "1")) {
        	zjTzSizeControlRecord.setChinaName("???");
        }else {
        	zjTzSizeControlRecord.setChinaName("???");
        }
        zjTzSizeControlRecord.setStatusId("0");
        zjTzSizeControlRecord.setStatusName("?????????");
        zjTzSizeControlRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzSizeControlRecordMapper.insert(zjTzSizeControlRecord);
        //??????1
        List<ZjTzFile> schemeFileList = zjTzSizeControlRecord.getSchemeFileList();
        if(schemeFileList != null && schemeFileList.size()>0) {
            for(ZjTzFile zjTzFile:schemeFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("1");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        //??????2
        List<ZjTzFile> thirdReplyFileList = zjTzSizeControlRecord.getThirdReplyFileList();
        if(thirdReplyFileList != null && thirdReplyFileList.size()>0) {
            for(ZjTzFile zjTzFile:thirdReplyFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("2");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        //??????3
        List<ZjTzFile> localGovFileList = zjTzSizeControlRecord.getLocalGovFileList();
        if(localGovFileList != null && localGovFileList.size()>0) {
            for(ZjTzFile zjTzFile:localGovFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("3");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        //??????4
        List<ZjTzFile> juFileList = zjTzSizeControlRecord.getJuFileList();
        if(juFileList != null && juFileList.size()>0) {
            for(ZjTzFile zjTzFile:juFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("4");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        //??????5
        List<ZjTzFile> chinaFileList = zjTzSizeControlRecord.getChinaFileList();
        if(chinaFileList != null && chinaFileList.size()>0) {
            for(ZjTzFile zjTzFile:chinaFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("5");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        //???????????????????????????????????????last
    	ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlId());
    	zjTzSizeControl.setChangeNumber(changeNumber);
    	//last=???????????????????????????(???)????????????(???)???????????????(???)
    	zjTzSizeControl.setLastChangePropertyId(zjTzSizeControl.getChangePropertyId());
    	zjTzSizeControl.setLastChangePropertyName(zjTzSizeControl.getChangePropertyName());
    	zjTzSizeControl.setLastAmount1(zjTzSizeControl.getAmount1());
    	zjTzSizeControl.setLastAmount2(zjTzSizeControl.getAmount2());
    	zjTzSizeControl.setLastAmount3(zjTzSizeControl.getAmount3());
    	//??????=???????????????????????????(???)????????????(???)???????????????(???)
    	zjTzSizeControl.setChangePropertyId(zjTzSizeControlRecord.getChangePropertyId());
    	zjTzSizeControl.setChangePropertyName(zjTzSizeControlRecord.getChangePropertyName());
    	zjTzSizeControl.setAmount1(zjTzSizeControlRecord.getAmount1());
    	zjTzSizeControl.setAmount2(zjTzSizeControlRecord.getAmount2());
    	zjTzSizeControl.setAmount3(zjTzSizeControlRecord.getAmount3());
    	zjTzSizeControl.setAddFlag("false");
    	flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    	
    	//add????????????
    	ZjTzContractConditionRecord dbzjTzContractCondition = new ZjTzContractConditionRecord();
    	dbzjTzContractCondition.setContractConditionRecordId(UuidUtil.generate());
    	// ????????????????????????id
    	dbzjTzContractCondition.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
        // ??????????????????id
        dbzjTzContractCondition.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
        // ??????id
        dbzjTzContractCondition.setProjectId(zjTzSizeControlRecord.getProjectId());
        // ????????????
        dbzjTzContractCondition.setRegisterDate(zjTzSizeControlRecord.getRegisterDate1());
        // ?????????
        dbzjTzContractCondition.setRegistrant(zjTzSizeControlRecord.getRegistrant1());
        // ??????????????????id
        dbzjTzContractCondition.setInvestId(zjTzSizeControlRecord.getInvestId());
        // ??????????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getInvestId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControlRecord.getInvestId());
     	   dbzjTzContractCondition.setInvestName(openBankName);
        }
        // ?????????????????????
        dbzjTzContractCondition.setJuShare(zjTzSizeControlRecord.getJuShare());
        // ??????????????????????????????id
        dbzjTzContractCondition.setJuId(zjTzSizeControlRecord.getJuId1());
        // ??????????????????????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getJuId1())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControlRecord.getJuId1());
     	   dbzjTzContractCondition.setJuName(openBankName);
        }
        // ?????????????????????id
        dbzjTzContractCondition.setZcbId(zjTzSizeControlRecord.getZcbId());
        // ?????????????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getZcbId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControlRecord.getZcbId());
     	   dbzjTzContractCondition.setZcbName(openBankName);
        }
        // ?????????????????????
        dbzjTzContractCondition.setZcbShare(zjTzSizeControlRecord.getZcbShare());
        // ??????????????????
        dbzjTzContractCondition.setExt1(zjTzSizeControlRecord.getExt1());
        // ????????????????????????????????????
        dbzjTzContractCondition.setExt2(zjTzSizeControlRecord.getExt2());
        // ????????????????????????
        dbzjTzContractCondition.setExt3(zjTzSizeControlRecord.getExt3());
        dbzjTzContractCondition.setCreateUserInfo(userKey, realName);
        List<ZjTzFile> ZjTzFileList = zjTzSizeControlRecord.getZjTzFileList();
        if(ZjTzFileList != null && ZjTzFileList.size()>0) {
     	   for(ZjTzFile zjTzFile:ZjTzFileList) {
     		   zjTzFile.setUid(UuidUtil.generate());
     		   zjTzFile.setOtherId(dbzjTzContractCondition.getContractConditionRecordId());
     		   zjTzFile.setCreateUserInfo(userKey, realName);
     		   zjTzFileMapper.insert(zjTzFile);
     	   }
        }
        flag = zjTzContractConditionRecordMapper.insert(dbzjTzContractCondition);
    	
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	return repEntity.ok("sys.data.sava", zjTzSizeControlRecord);
        }
    }

    //???????????????????????????????????????  ?????????????????????????????????
    @Override
    public ResponseEntity updateZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSizeControlRecord dbzjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
        if (dbzjTzSizeControlRecord != null && StrUtil.isNotEmpty(dbzjTzSizeControlRecord.getSizeControlRecordId())) {
        	//????????????,?????????????????????????????????
        	ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
        	record.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
        	List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
        	if(records != null &&records.size() > 1) {
        		for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
        			if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
        				return repEntity.layerMessage("no", "?????????????????????????????????????????????!!");
        			}
        		}
        	}else {
        		//??????1????????????????????????2???????????????????????????
        		ZjTzProManage proManage =  zjTzProManageMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getProjectId());
            	if(proManage != null && StrUtil.isNotEmpty(proManage.getProjectId())) {
            		if(proManage.getAmount1() != null) {
            			dbzjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount1(), proManage.getAmount1()));
            		}else {
            			dbzjTzSizeControlRecord.setDvalue1(zjTzSizeControlRecord.getAmount1());
            		}
            		if(proManage.getAmount3() != null) {
            			dbzjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount2(), proManage.getAmount3()));
            		}else {
            			dbzjTzSizeControlRecord.setDvalue2(zjTzSizeControlRecord.getAmount2());
            		}
            	}
        	}
        	
        	//????????????????????????last
            ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
            //last=???????????????????????????(???)????????????(???)???????????????(???)
            zjTzSizeControl.setLastChangePropertyId(dbzjTzSizeControlRecord.getChangePropertyId());
            zjTzSizeControl.setLastChangePropertyName(dbzjTzSizeControlRecord.getChangePropertyName());
            zjTzSizeControl.setLastAmount1(dbzjTzSizeControlRecord.getAmount1());
            zjTzSizeControl.setLastAmount2(dbzjTzSizeControlRecord.getAmount2());
            zjTzSizeControl.setLastAmount3(dbzjTzSizeControlRecord.getAmount3());
        	
            
            ZjTzSizeControlRecord lastRecord = new ZjTzSizeControlRecord();
        	lastRecord.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
        	lastRecord.setChangeNumber(dbzjTzSizeControlRecord.getChangeNumber()-1);
        	List<ZjTzSizeControlRecord> lastRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(lastRecord);
        	if(lastRecords != null &&lastRecords.size()>0) {
        		//???????????????????????????aomunt1
        		dbzjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount1(), lastRecords.get(0).getAmount1()));
        		dbzjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount2(), lastRecords.get(0).getAmount2()));
        	}
           // ??????id
           dbzjTzSizeControlRecord.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
           // ??????id
           dbzjTzSizeControlRecord.setProjectId(zjTzSizeControlRecord.getProjectId());
           // ?????????id
           dbzjTzSizeControlRecord.setSubprojectInfoId(zjTzSizeControlRecord.getSubprojectInfoId());
           // ?????????name
           ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSubprojectInfoId());
           if(info != null && StrUtil.isNotEmpty(info.getProjectId())) {
        	   dbzjTzSizeControlRecord.setSubprojectName(info.getSubprojectName());
           }
           // ????????????
//           dbzjTzSizeControlRecord.setChangeNumber(zjTzSizeControlRecord.getChangeNumber());
           // ????????????id
           dbzjTzSizeControlRecord.setChangePropertyId(zjTzSizeControlRecord.getChangePropertyId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getChangePropertyId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControlRecord.getChangePropertyId());
           	dbzjTzSizeControlRecord.setChangePropertyName(openBankName);
           }
           // ????????????(???)
           dbzjTzSizeControlRecord.setAmount1(zjTzSizeControlRecord.getAmount1());
           // ?????????(???)
           dbzjTzSizeControlRecord.setAmount2(zjTzSizeControlRecord.getAmount2());
           // ????????????(???)
           dbzjTzSizeControlRecord.setAmount3(zjTzSizeControlRecord.getAmount3());
           // ??????????????????id
           dbzjTzSizeControlRecord.setSecondNegotiateId(zjTzSizeControlRecord.getSecondNegotiateId());
           // ??????????????????name
           if (StrUtil.equals(zjTzSizeControlRecord.getSecondNegotiateId(), "1")) {
        	   dbzjTzSizeControlRecord.setSecondNegotiateName("???");
           }else {
        	   dbzjTzSizeControlRecord.setSecondNegotiateName("???");
           }
           // ??????????????????
           dbzjTzSizeControlRecord.setScheme(zjTzSizeControlRecord.getScheme());
           // ????????????id
           dbzjTzSizeControlRecord.setThirdReplyId(zjTzSizeControlRecord.getThirdReplyId());
           // ????????????name
           if (StrUtil.equals(zjTzSizeControlRecord.getThirdReplyId(), "1")) {
        	   dbzjTzSizeControlRecord.setThirdReplyName("???");
           }else {
        	   dbzjTzSizeControlRecord.setThirdReplyName("???");
           }
           // ??????????????????id
           dbzjTzSizeControlRecord.setLocalGovId(zjTzSizeControlRecord.getLocalGovId());
           // ??????????????????name
           if (StrUtil.equals(zjTzSizeControlRecord.getLocalGovId(), "1")) {
        	   dbzjTzSizeControlRecord.setLocalGovName("???");
           }else {
        	   dbzjTzSizeControlRecord.setLocalGovName("???");
           }
           // ?????????????????????id
           dbzjTzSizeControlRecord.setJuId(zjTzSizeControlRecord.getJuId());
           // ?????????????????????name
           if (StrUtil.equals(zjTzSizeControlRecord.getJuId(), "1")) {
        	   dbzjTzSizeControlRecord.setJuName("???");
           }else {
        	   dbzjTzSizeControlRecord.setJuName("???");
           }
           // ??????????????????id
           dbzjTzSizeControlRecord.setChinaId(zjTzSizeControlRecord.getChinaId());
           // ??????????????????name
           if (StrUtil.equals(zjTzSizeControlRecord.getChinaId(), "1")) {
        	   dbzjTzSizeControlRecord.setChinaName("???");
           }else {
        	   dbzjTzSizeControlRecord.setChinaName("???");
           }
           // ????????????
           dbzjTzSizeControlRecord.setRegisterDate(zjTzSizeControlRecord.getRegisterDate());
           // ?????????
           dbzjTzSizeControlRecord.setRegistrant(zjTzSizeControlRecord.getRegistrant());
           // ??????
           dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
           //??????
           dbzjTzSizeControlRecord.setRemarks(zjTzSizeControlRecord.getRemarks());
           //????????????
//           dbzjTzSizeControlRecord.setRenew(zjTzSizeControlRecord.getRenew());
           //??????????????????
           dbzjTzSizeControlRecord.setSizeControlSubject(zjTzSizeControlRecord.getSizeControlSubject());
           //??????=???????????????????????????(???)????????????(???)???????????????(???)
           zjTzSizeControl.setChangePropertyId(dbzjTzSizeControlRecord.getChangePropertyId());
           zjTzSizeControl.setChangePropertyName(dbzjTzSizeControlRecord.getChangePropertyName());
           zjTzSizeControl.setAmount1(zjTzSizeControl.getAmount1());
           zjTzSizeControl.setAmount2(zjTzSizeControl.getAmount2());
           zjTzSizeControl.setAmount3(zjTzSizeControl.getAmount3());
           flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
           //??????????????????
           flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
           
           //????????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzSizeControlRecord.getSizeControlRecordId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           //??????1
           List<ZjTzFile> schemeFileList = zjTzSizeControlRecord.getSchemeFileList();
           if(schemeFileList != null && schemeFileList.size()>0) {
               for(ZjTzFile zjTzFile:schemeFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                   zjTzFile.setOtherType("1");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????2
           List<ZjTzFile> thirdReplyFileList = zjTzSizeControlRecord.getThirdReplyFileList();
           if(thirdReplyFileList != null && thirdReplyFileList.size()>0) {
               for(ZjTzFile zjTzFile:thirdReplyFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                   zjTzFile.setOtherType("2");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????3
           List<ZjTzFile> localGovFileList = zjTzSizeControlRecord.getLocalGovFileList();
           if(localGovFileList != null && localGovFileList.size()>0) {
               for(ZjTzFile zjTzFile:localGovFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                   zjTzFile.setOtherType("3");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????4
           List<ZjTzFile> juFileList = zjTzSizeControlRecord.getJuFileList();
           if(juFileList != null && juFileList.size()>0) {
               for(ZjTzFile zjTzFile:juFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                   zjTzFile.setOtherType("4");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????5
           List<ZjTzFile> chinaFileList = zjTzSizeControlRecord.getChinaFileList();
           if(chinaFileList != null && chinaFileList.size()>0) {
               for(ZjTzFile zjTzFile:chinaFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                   zjTzFile.setOtherType("5");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           
           //??????==?????????????????? 
           ZjTzContractConditionRecord dbzjTzContractCondition = new ZjTzContractConditionRecord();
           dbzjTzContractCondition.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
           List<ZjTzContractConditionRecord> conditionRecords = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(dbzjTzContractCondition);
           if(conditionRecords != null && conditionRecords.size() >0) {
        	   dbzjTzContractCondition.setModifyUserInfo(userKey, realName);
        	   zjTzContractConditionRecordMapper.batchDeleteUpdateZjTzContractConditionRecord(conditionRecords, dbzjTzContractCondition);
        	   //??????????????????????????????
        	   ZjTzFile zjTzFileSelect1 = new ZjTzFile();
        	   zjTzFileSelect1.setOtherId(conditionRecords.get(0).getContractConditionRecordId());
               List<ZjTzFile> deleteZjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect1);
               if(deleteZjTzFileList1 != null && deleteZjTzFileList1.size()>0) {
            	   zjTzFileSelect1.setModifyUserInfo(userKey, realName);
            	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList1, zjTzFileSelect1);
               }
           }
           dbzjTzContractCondition.setContractConditionRecordId(UuidUtil.generate());
           // ????????????????????????id
           dbzjTzContractCondition.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
           // ??????????????????id
           dbzjTzContractCondition.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
           // ??????id
           dbzjTzContractCondition.setProjectId(zjTzSizeControlRecord.getProjectId());
           // ????????????
           dbzjTzContractCondition.setRegisterDate(zjTzSizeControlRecord.getRegisterDate1());
           // ?????????
           dbzjTzContractCondition.setRegistrant(zjTzSizeControlRecord.getRegistrant1());
           // ??????????????????id
           dbzjTzContractCondition.setInvestId(zjTzSizeControlRecord.getInvestId());
           // ??????????????????name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getInvestId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControlRecord.getInvestId());
        	   dbzjTzContractCondition.setInvestName(openBankName);
           }
           // ?????????????????????
           dbzjTzContractCondition.setJuShare(zjTzSizeControlRecord.getJuShare());
           // ??????????????????????????????id
           dbzjTzContractCondition.setJuId(zjTzSizeControlRecord.getJuId1());
           // ??????????????????????????????name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getJuId1())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControlRecord.getJuId1());
        	   dbzjTzContractCondition.setJuName(openBankName);
           }
           // ?????????????????????id
           dbzjTzContractCondition.setZcbId(zjTzSizeControlRecord.getZcbId());
           // ?????????????????????name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getZcbId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControlRecord.getZcbId());
        	   dbzjTzContractCondition.setZcbName(openBankName);
           }
           // ?????????????????????
           dbzjTzContractCondition.setZcbShare(zjTzSizeControlRecord.getZcbShare());
           // ??????????????????
           dbzjTzContractCondition.setExt1(zjTzSizeControlRecord.getExt1());
           // ????????????????????????????????????
           dbzjTzContractCondition.setExt2(zjTzSizeControlRecord.getExt2());
           // ????????????????????????
           dbzjTzContractCondition.setExt3(zjTzSizeControlRecord.getExt3());
           dbzjTzContractCondition.setCreateUserInfo(userKey, realName);
           List<ZjTzFile> ZjTzFileList = zjTzSizeControlRecord.getZjTzFileList();
           if(ZjTzFileList != null && ZjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:ZjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzContractCondition.getContractConditionRecordId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
           flag = zjTzContractConditionRecordMapper.insert(dbzjTzContractCondition);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSizeControlRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSizeControlRecord(List<ZjTzSizeControlRecord> zjTzSizeControlRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        int changeNumber = 0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        if (zjTzSizeControlRecordList != null && zjTzSizeControlRecordList.size() > 0) {
        	//????????????,?????????????????????????????????===???????????????????????????????????????????????????????????????????????????
        	if(zjTzSizeControlRecordList.size() > 1) {
        		return repEntity.layerMessage("no", "???????????????????????????????????????");
        	}else {
        		ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
            	record.setSizeControlId(zjTzSizeControlRecordList.get(0).getSizeControlId());
            	List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
            	if(records != null &&records.size() > 1) {
            		changeNumber = records.size();
            		for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
            			if(tzSizeControlRecord.getChangeNumber() >zjTzSizeControlRecordList.get(0).getChangeNumber()) {
            				return repEntity.layerMessage("no", "?????????????????????????????????????????????!!");
            			}
            		}
            		//??????2???:????????????????????????????????????????????????????????????????????????  ????????????????????????????????????(???????????????????????????changeNumber-1?????????)
                	//???????????????????????????????????????last
                	ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControlRecordList.get(0).getSizeControlId());
                	zjTzSizeControl.setChangeNumber(changeNumber-1);
                	//??????????????????(???????????????????????????changeNumber-1?????????)
                	ZjTzSizeControlRecord lastSizeControlRecord = new ZjTzSizeControlRecord();
                	lastSizeControlRecord.setSizeControlId(zjTzSizeControlRecordList.get(0).getSizeControlId());
                	lastSizeControlRecord.setChangeNumber(changeNumber-1);
                	List<ZjTzSizeControlRecord> lastSizeRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(lastSizeControlRecord);
                	if(lastSizeRecords != null && lastSizeRecords.size() >0) {
                		//last=???????????????????????????(???)????????????(???)???????????????(???)
                		zjTzSizeControl.setLastChangePropertyId(lastSizeRecords.get(0).getChangePropertyId());
                		zjTzSizeControl.setLastChangePropertyName(lastSizeRecords.get(0).getChangePropertyName());
                		zjTzSizeControl.setLastAmount1(lastSizeRecords.get(0).getAmount1());
                		zjTzSizeControl.setLastAmount2(lastSizeRecords.get(0).getAmount2());
                		zjTzSizeControl.setLastAmount3(lastSizeRecords.get(0).getAmount3());
                	}else {
                		//last=???????????????????????????(???)????????????(???)???????????????(???)
                		zjTzSizeControl.setLastChangePropertyId("");
                		zjTzSizeControl.setLastChangePropertyName("");
                		zjTzSizeControl.setLastAmount1(new BigDecimal("0"));
                		zjTzSizeControl.setLastAmount2(new BigDecimal("0"));
                		zjTzSizeControl.setLastAmount3(new BigDecimal("0"));
                	}
                    //??????=???????????????????????????(???)????????????(???)???????????????(???)
                    zjTzSizeControl.setChangePropertyId(zjTzSizeControl.getLastChangePropertyId());
                    zjTzSizeControl.setChangePropertyName(zjTzSizeControl.getLastChangePropertyName());
                    zjTzSizeControl.setAmount1(zjTzSizeControl.getLastAmount1());
                    zjTzSizeControl.setAmount2(zjTzSizeControl.getLastAmount2());
                    zjTzSizeControl.setAmount3(zjTzSizeControl.getLastAmount3());
                    zjTzSizeControl.setAddFlag("true");
                    
                    //del?????????
                    ZjTzSizeControlRecord dbRecord = new ZjTzSizeControlRecord();
                    dbRecord.setModifyUserInfo(userKey, realName);
                    flag = zjTzSizeControlRecordMapper.batchDeleteUpdateZjTzSizeControlRecord(zjTzSizeControlRecordList, dbRecord);
                    
                    ZjTzSizeControlRecord returnRecord = new ZjTzSizeControlRecord();
                    returnRecord.setSizeControlId(zjTzSizeControlRecordList.get(0).getSizeControlId());
                	List<ZjTzSizeControlRecord> returnRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(returnRecord);
                	
                	if (returnRecords != null && returnRecords.size() > 0) {
                		for (ZjTzSizeControlRecord dbzjTzSizeControlRecord : returnRecords) {
                			if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew1(), "0")) {
                				count1 = count1 + 1;
                			}else if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew2(), "0")) {
                				count2 = count2 + 1;
                			}else if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew3(), "0")) {
                				count3 = count3 + 1;
                			}else if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew4(), "0")) {
                				count4 = count4 + 1;
                			}
                		}
					}
                	if (count1 >= 1) {
                		zjTzSizeControl.setRenew1("0");
		            }else {
		            	zjTzSizeControl.setRenew1("");
					}
					if (count2 >= 1) {
						zjTzSizeControl.setRenew2("0");
		            }else {
		            	zjTzSizeControl.setRenew2("");
						
					}
					if (count3 >= 1) {
						zjTzSizeControl.setRenew3("0");
		            }else {
		            	zjTzSizeControl.setRenew3("");
					}
					if (count4 >= 1) {
						zjTzSizeControl.setRenew4("0");
		            }else {
		            	zjTzSizeControl.setRenew4("");
					}
					
                    //update??????
                    flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
            	}else {
            		//??????????????????  ===??????1???:?????????????????????????????????????????????????????????????????????
            		for (ZjTzSizeControlRecord zjTzSizeControlRecord : records) {
						//del?????????????????????
            			ZjTzContractConditionRecord delConditionRecord = new ZjTzContractConditionRecord();
            			delConditionRecord.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
            			List<ZjTzContractConditionRecord> delConditionRecords = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(delConditionRecord);
            			if(delConditionRecords != null && delConditionRecords.size() >0) {
            				//del??????????????????????????????
            				for (ZjTzContractConditionRecord delConditionRecordFile : delConditionRecords) {
            					ZjTzFile file = new ZjTzFile();
            					file.setOtherId(delConditionRecordFile.getContractConditionRecordId());
            					List<ZjTzFile> delFileList = zjTzFileMapper.selectByZjTzFileList(file);
            					if(delFileList != null && delFileList.size() >0) {
            						file.setModifyUserInfo(userKey, realName);
            						flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(delFileList, file);
            					}
							}
            				delConditionRecord.setModifyUserInfo(userKey, realName);
            				flag = zjTzContractConditionRecordMapper.batchDeleteUpdateZjTzContractConditionRecord(delConditionRecords, delConditionRecord);
            			}
					}
            		ZjTzSizeControlRecord zjTzSizeControlRecord = new ZjTzSizeControlRecord();
            		zjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
            		flag = zjTzSizeControlRecordMapper.batchDeleteUpdateZjTzSizeControlRecord(zjTzSizeControlRecordList, zjTzSizeControlRecord);
            		//del?????????
            		List<ZjTzSizeControl> delSizeControlList = new ArrayList<>();
            		ZjTzSizeControl control = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControlRecordList.get(0).getSizeControlId());
            		if(control != null && StrUtil.isNotEmpty(control.getSizeControlId())) {
            			//del??????????????????????????????
            			ZjTzContractCondition condition = new ZjTzContractCondition();
            			condition.setSizeControlId(control.getSizeControlId());
            			List<ZjTzContractCondition> delConditions = zjTzContractConditionMapper.selectByZjTzContractConditionList(condition);
            			if(delConditions != null && delConditions.size() >0) {
            				//del????????????????????????
            				for (ZjTzContractCondition zjTzContractCondition : delConditions) {
            					ZjTzFile file = new ZjTzFile();
            					file.setOtherId(zjTzContractCondition.getContractConditionId());
            					List<ZjTzFile> delFileList = zjTzFileMapper.selectByZjTzFileList(file);
            					if(delFileList != null && delFileList.size() >0) {
            						file.setModifyUserInfo(userKey, realName);
            						flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(delFileList, file);
            					}
							}
            				//del???????????????
            				condition.setModifyUserInfo(userKey, realName);
            				flag = zjTzContractConditionMapper.batchDeleteUpdateZjTzContractCondition(delConditions, condition);
            			}
            			delSizeControlList.add(control);
            			if(delSizeControlList != null && delSizeControlList.size() >0) {
            				for (ZjTzSizeControl sizeControl : delSizeControlList) {
								//del????????????????????????
            					ZjTzFile file = new ZjTzFile();
            					file.setOtherId(sizeControl.getSizeControlId());
            					List<ZjTzFile> delFileList = zjTzFileMapper.selectByZjTzFileList(file);
            					if(delFileList != null && delFileList.size() >0) {
            						file.setModifyUserInfo(userKey, realName);
            						flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(delFileList, file);
            					}
							}
            				
            				ZjTzSizeControlRecord returnRecord = new ZjTzSizeControlRecord();
                            returnRecord.setSizeControlId(zjTzSizeControlRecordList.get(0).getSizeControlId());
                        	List<ZjTzSizeControlRecord> returnRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(returnRecord);
                        	
                        	if (returnRecords != null && returnRecords.size() > 0) {
								for (ZjTzSizeControlRecord dbzjTzSizeControlRecord : returnRecords) {
									if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew1(), "0")) {
		                				count1 = count1 + 1;
		                			}else if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew2(), "0")) {
		                				count2 = count2 + 1;
		                			}else if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew3(), "0")) {
		                				count3 = count3 + 1;
		                			}else if (StrUtil.equals(dbzjTzSizeControlRecord.getRenew4(), "0")) {
		                				count4 = count4 + 1;
		                			}
								}
								
								if (count1 >= 1) {
									control.setRenew1("0");
					            }else {
					            	control.setRenew1("");
								}
								if (count2 >= 1) {
									control.setRenew2("0");
					            }else {
					            	control.setRenew2("");
									
								}
								if (count3 >= 1) {
									control.setRenew3("0");
					            }else {
					            	control.setRenew3("");
								}
								if (count4 >= 1) {
									control.setRenew4("0");
					            }else {
					            	control.setRenew4("");
								}
							}else {
								control.setRenew1("");
								control.setRenew2("");
								control.setRenew3("");
								control.setRenew4("");
							}
            				control.setModifyUserInfo(userKey, realName);
            				flag = zjTzSizeControlMapper.batchDeleteUpdateZjTzSizeControl(delSizeControlList, control);
            			}
            		}
            	}
        	}
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSizeControlRecordList);
        }
    }

	@Override
	public ResponseEntity singleReleaseZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        
        int flag = 0;
    	ZjTzSizeControlRecord dbzjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
    	if (dbzjTzSizeControlRecord != null && StrUtil.isNotEmpty(dbzjTzSizeControlRecord.getSizeControlRecordId())) {
    		if(StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "1")) {
    			return repEntity.layerMessage("no", "??????????????????????????????!!");
    		}else if (StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "3") && StrUtil.equals("4", ext1)) {
    			return repEntity.layerMessage("no", "??????????????????????????????!!");
			}
    		//????????????,?????????????????????????????????
    		ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
    		record.setSizeControlId(dbzjTzSizeControlRecord.getSizeControlId());
    		List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
    		if(records != null &&records.size() > 1) {
    			for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
    				if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
    					return repEntity.layerMessage("no", "?????????????????????????????????????????????!!");
    				}
    			}
    			
    			//???????????????????????????????????????
    			if (StrUtil.equals("4", ext1)) {
    				dbzjTzSizeControlRecord.setStatusId("3");
    				dbzjTzSizeControlRecord.setStatusName("??????????????????");
				}else {
					dbzjTzSizeControlRecord.setStatusId("1");
					dbzjTzSizeControlRecord.setStatusName("?????????");
				}
    			
    			if (StrUtil.equals("4", ext1)) {
    				dbzjTzSizeControlRecord.setRenew3("0");
    				dbzjTzSizeControlRecord.setRenew1("");
				}else if (StrUtil.equals("3", ext1)) {
					dbzjTzSizeControlRecord.setRenew4("0");
    				dbzjTzSizeControlRecord.setRenew3("");
    				dbzjTzSizeControlRecord.setRenew2("");
				}else if (StrUtil.equals("2", ext1)) {
					dbzjTzSizeControlRecord.setRenew4("0");
    				dbzjTzSizeControlRecord.setRenew3("");
    				dbzjTzSizeControlRecord.setRenew2("");
				}
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    		
    			//??????????????????
    			ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
    			if(zjTzSizeControl != null && StrUtil.isNotEmpty(zjTzSizeControl.getSizeControlId())) {
    				zjTzSizeControl.setAddFlag("true");
    				if (StrUtil.equals("4", ext1)) {
    					zjTzSizeControl.setRenew3("0");
    					zjTzSizeControl.setRenew1("");
    				}else if (StrUtil.equals("3", ext1)) {
    					zjTzSizeControl.setRenew4("0");
    					zjTzSizeControl.setRenew3("");
    					zjTzSizeControl.setRenew2("");
    				}else if (StrUtil.equals("2", ext1)) {
    					zjTzSizeControl.setRenew4("0");
    					zjTzSizeControl.setRenew3("");
    					zjTzSizeControl.setRenew2("");
    				}
    				flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    			}
    			zjTzSizeControlRecord.setAddFlag("true");
    			
    		}else {
    			//???????????????????????????????????????
    			if (StrUtil.equals("4", ext1)) {
    				dbzjTzSizeControlRecord.setStatusId("3");
    				dbzjTzSizeControlRecord.setStatusName("??????????????????");
				}else {
					dbzjTzSizeControlRecord.setStatusId("1");
					dbzjTzSizeControlRecord.setStatusName("?????????");
				}
    			
    			if (StrUtil.equals("4", ext1)) {
    				dbzjTzSizeControlRecord.setRenew3("0");
    				dbzjTzSizeControlRecord.setRenew1("");
				}else if (StrUtil.equals("3", ext1)) {
					dbzjTzSizeControlRecord.setRenew4("0");
    				dbzjTzSizeControlRecord.setRenew3("");
    				dbzjTzSizeControlRecord.setRenew2("");
				}else if (StrUtil.equals("2", ext1)) {
					dbzjTzSizeControlRecord.setRenew4("0");
    				dbzjTzSizeControlRecord.setRenew3("");
    				dbzjTzSizeControlRecord.setRenew2("");
				}
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    		
    			//??????????????????
    			ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
    			if(zjTzSizeControl != null && StrUtil.isNotEmpty(zjTzSizeControl.getSizeControlId())) {
    				zjTzSizeControl.setAddFlag("true");
    				if (StrUtil.equals("4", ext1)) {
    					zjTzSizeControl.setRenew3("0");
    					zjTzSizeControl.setRenew1("");
    				}else if (StrUtil.equals("3", ext1)) {
    					zjTzSizeControl.setRenew4("0");
    					zjTzSizeControl.setRenew3("");
    					zjTzSizeControl.setRenew2("");
    				}else if (StrUtil.equals("2", ext1)) {
    					zjTzSizeControl.setRenew4("0");
    					zjTzSizeControl.setRenew3("");
    					zjTzSizeControl.setRenew2("");
    				}
    				flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    			}
    			zjTzSizeControlRecord.setAddFlag("true");
    		}
    	}
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSizeControlRecord);
        }
	}

	@Override
	public ResponseEntity singleRecallZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
    	ZjTzSizeControlRecord dbzjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
    	if (dbzjTzSizeControlRecord != null && StrUtil.isNotEmpty(dbzjTzSizeControlRecord.getSizeControlRecordId())) {
    		if(StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "0") || StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "2")) {
    			return repEntity.layerMessage("no", "??????????????????????????????????????????????????????!!");
    		}
    		//????????????,???????????????????????????????????????
    		ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
    		record.setSizeControlId(dbzjTzSizeControlRecord.getSizeControlId());
    		List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
    		if(records != null &&records.size() > 1) {
    			for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
    				if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
    					return repEntity.layerMessage("no", "?????????????????????????????????????????????!!");
    				}
    			}
    			//???????????????????????????????????????
    			dbzjTzSizeControlRecord.setStatusId("2");
    			dbzjTzSizeControlRecord.setStatusName("?????????");
    			dbzjTzSizeControlRecord.setRenew1("0");
    			dbzjTzSizeControlRecord.setRenew2("0");
    			dbzjTzSizeControlRecord.setRenew3("");
    			dbzjTzSizeControlRecord.setRenew4("");
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    			//??????????????????
    			ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
    			if(zjTzSizeControl != null && StrUtil.isNotEmpty(zjTzSizeControl.getSizeControlId())) {
    				zjTzSizeControl.setAddFlag("false");
    				zjTzSizeControl.setRenew1("0");
    				zjTzSizeControl.setRenew2("0");
    				zjTzSizeControl.setRenew3("");
    				zjTzSizeControl.setRenew4("");
    				flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    			}
    		}else {
    			//???????????????????????????????????????
    			dbzjTzSizeControlRecord.setStatusId("2");
    			dbzjTzSizeControlRecord.setStatusName("?????????");
    			dbzjTzSizeControlRecord.setRenew1("0");
    			dbzjTzSizeControlRecord.setRenew2("0");
    			dbzjTzSizeControlRecord.setRenew3("");
    			dbzjTzSizeControlRecord.setRenew4("");
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    			//??????????????????
    			ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
    			if(zjTzSizeControl != null && StrUtil.isNotEmpty(zjTzSizeControl.getSizeControlId())) {
    				zjTzSizeControl.setAddFlag("false");
    				zjTzSizeControl.setRenew1("0");
    				zjTzSizeControl.setRenew2("0");
    				zjTzSizeControl.setRenew3("");
    				zjTzSizeControl.setRenew4("");
    				flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    			}
    		}
    	}
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSizeControlRecord);
        }
	}

	@Override
	public ResponseEntity checkAndFinishZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        int flag = 0;
    	ZjTzSizeControlRecord dbzjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
    	if (dbzjTzSizeControlRecord != null && StrUtil.isNotEmpty(dbzjTzSizeControlRecord.getSizeControlRecordId())) {
//    		if(StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "0") || StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "2")) {
//    			return repEntity.layerMessage("no", "??????????????????????????????????????????????????????!!");
//    		}
    		//????????????,???????????????????????????????????????
//    		if(records != null &&records.size() > 1) {
//    			for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
//    				if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
//    					return repEntity.layerMessage("no", "?????????????????????????????????????????????!!");
//    				}
//    				
//    			}
    			//???????????????????????????????????????
    			dbzjTzSizeControlRecord.setRenew1("");
    			dbzjTzSizeControlRecord.setRenew2("");
    			dbzjTzSizeControlRecord.setRenew3("");
    			dbzjTzSizeControlRecord.setRenew4("");
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    			
    			ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
        		record.setSizeControlId(dbzjTzSizeControlRecord.getSizeControlId());
        		List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
        		
        		int i = 0;
        		if (records != null && records.size() > 0) {
    				for (ZjTzSizeControlRecord zjTzSizeControlRecord2 : records) {
    					if (StrUtil.equals(zjTzSizeControlRecord2.getRenew4(), "0")) {
    						i = i + 1;
    					}
    				}
    			}
        		
    			if (i == 0) {
    				//??????????????????
    				ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
    				if(zjTzSizeControl != null && StrUtil.isNotEmpty(zjTzSizeControl.getSizeControlId())) {
    					zjTzSizeControl.setRenew1("");
    					zjTzSizeControl.setRenew2("");
    					zjTzSizeControl.setRenew3("");
    					zjTzSizeControl.setRenew4("");
    					flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    				}
				}
//    		}else {
//    			//???????????????????????????????????????
//    			dbzjTzSizeControlRecord.setRenew1("");
//    			dbzjTzSizeControlRecord.setRenew2("");
//    			dbzjTzSizeControlRecord.setRenew3("");
//    			dbzjTzSizeControlRecord.setRenew4("");
//    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
//    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
//    			//??????????????????
//    			ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
//    			if(zjTzSizeControl != null && StrUtil.isNotEmpty(zjTzSizeControl.getSizeControlId())) {
//    				zjTzSizeControl.setRenew1("");
//    				zjTzSizeControl.setRenew2("");
//    				zjTzSizeControl.setRenew3("");
//    				zjTzSizeControl.setRenew4("");
//    				flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
//    			}
//    		}
    	}
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSizeControlRecord);
        }
	
	}
}
