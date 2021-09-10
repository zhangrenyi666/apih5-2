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
        // 分页查询
        PageHelper.startPage(zjTzSizeControlRecord.getPage(),zjTzSizeControlRecord.getLimit());
        
        // 直管项目用户不可见托管项目上报状态的数据
        if (StrUtil.equals("3", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
        	zjTzSizeControlRecord.setExt1Flag1("直管项目用户不可见托管项目上报状态的数据");
        //	托管公司不可见未上报的数据
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzSizeControlRecord.setExt1Flag2("托管公司不可见未上报的数据");;
        // 局用户不可见未上报和托管项目上报状态的数据	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzSizeControlRecord.setExt1Flag3("局用户不可见未上报和托管项目上报状态的数据");
		}
        
        // 获取数据
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
	    			//有值则去取上一次的aomunt1
	    			damount1 = CalcUtils.calcSubtract(controlRecord.getAmount1(), lastRecords.get(0).getAmount1());
	    			damount2 = CalcUtils.calcSubtract(controlRecord.getAmount2(), lastRecords.get(0).getAmount2());
	    		}
			}
        	controlRecord.setDvalue1(damount1);
        	controlRecord.setDvalue2(damount2);
        	
        }
        // 得到分页信息
        PageInfo<ZjTzSizeControlRecord> p = new PageInfo<>(zjTzSizeControlRecordList);

        return repEntity.okList(zjTzSizeControlRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSizeControlRecordDetails(ZjTzSizeControlRecord zjTzSizeControlRecord) {
        if (zjTzSizeControlRecord == null) {
            zjTzSizeControlRecord = new ZjTzSizeControlRecord();
        }
        zjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
        // 数据存在
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
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int changeNumber = 0;
        //先去查一下记录表
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
    			//有值则去取上一次的aomunt1
    			zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount1(), lastRecords.get(0).getAmount1()));
    			zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount2(), lastRecords.get(0).getAmount2()));
    		}
    	}else {
    		if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getSubprojectInfoId())) {
    			//子项目名称
    	        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSubprojectInfoId());
    	        if(info != null) {
    	        	zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(info.getAmount1(), zjTzSizeControlRecord.getAmount1()));
    	        	zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(info.getAmount3(), zjTzSizeControlRecord.getAmount2()));
    	        }
			}else if (StrUtil.isEmpty(zjTzSizeControlRecord.getSubprojectInfoId())) {
				//项目名称
				ZjTzProManage zjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzSizeControlRecord.getProjectId());
				if (zjTzProManage != null) {
					zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzProManage.getAmount1(), zjTzSizeControlRecord.getAmount1()));
					zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzProManage.getAmount3(), zjTzSizeControlRecord.getAmount2()));
					
				}
			}
    		zjTzSizeControlRecord.setChangeNumber(changeNumber);
    	}
    	
    	
    	//add
    	//子项目名称
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
        //变动属性
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getChangePropertyId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControlRecord.getChangePropertyId());
        	zjTzSizeControlRecord.setChangePropertyName(openBankName);
        }
        //1
        if (StrUtil.equals(zjTzSizeControlRecord.getSecondNegotiateId(), "1")) {
        	zjTzSizeControlRecord.setSecondNegotiateName("是");
        }else {
        	zjTzSizeControlRecord.setSecondNegotiateName("否");
        }
        //2 
        if (StrUtil.equals(zjTzSizeControlRecord.getThirdReplyId(), "1")) {
        	zjTzSizeControlRecord.setThirdReplyName("是");
        }else {
        	zjTzSizeControlRecord.setThirdReplyName("否");
        }
        //3
        if (StrUtil.equals(zjTzSizeControlRecord.getLocalGovId(), "1")) {
        	zjTzSizeControlRecord.setLocalGovName("是");
        }else {
        	zjTzSizeControlRecord.setLocalGovName("否");
        }
        //4
        if (StrUtil.equals(zjTzSizeControlRecord.getJuId(), "1")) {
        	zjTzSizeControlRecord.setJuName("是");
        }else {
        	zjTzSizeControlRecord.setJuName("否");
        }
        //5
        if (StrUtil.equals(zjTzSizeControlRecord.getChinaId(), "1")) {
        	zjTzSizeControlRecord.setChinaName("是");
        }else {
        	zjTzSizeControlRecord.setChinaName("否");
        }
        zjTzSizeControlRecord.setStatusId("0");
        zjTzSizeControlRecord.setStatusName("未审核");
        zjTzSizeControlRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzSizeControlRecordMapper.insert(zjTzSizeControlRecord);
        //附件1
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
        //附件2
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
        //附件3
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
        //附件4
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
        //附件5
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
        //更新主表的变动次数、本次、last
    	ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlId());
    	zjTzSizeControl.setChangeNumber(changeNumber);
    	//last=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
    	zjTzSizeControl.setLastChangePropertyId(zjTzSizeControl.getChangePropertyId());
    	zjTzSizeControl.setLastChangePropertyName(zjTzSizeControl.getChangePropertyName());
    	zjTzSizeControl.setLastAmount1(zjTzSizeControl.getAmount1());
    	zjTzSizeControl.setLastAmount2(zjTzSizeControl.getAmount2());
    	zjTzSizeControl.setLastAmount3(zjTzSizeControl.getAmount3());
    	//本次=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
    	zjTzSizeControl.setChangePropertyId(zjTzSizeControlRecord.getChangePropertyId());
    	zjTzSizeControl.setChangePropertyName(zjTzSizeControlRecord.getChangePropertyName());
    	zjTzSizeControl.setAmount1(zjTzSizeControlRecord.getAmount1());
    	zjTzSizeControl.setAmount2(zjTzSizeControlRecord.getAmount2());
    	zjTzSizeControl.setAmount3(zjTzSizeControlRecord.getAmount3());
    	zjTzSizeControl.setAddFlag("false");
    	flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
    	
    	//add合同条件
    	ZjTzContractConditionRecord dbzjTzContractCondition = new ZjTzContractConditionRecord();
    	dbzjTzContractCondition.setContractConditionRecordId(UuidUtil.generate());
    	// 投资规模控制记录id
    	dbzjTzContractCondition.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
        // 投资规模控制id
        dbzjTzContractCondition.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
        // 项目id
        dbzjTzContractCondition.setProjectId(zjTzSizeControlRecord.getProjectId());
        // 登记日期
        dbzjTzContractCondition.setRegisterDate(zjTzSizeControlRecord.getRegisterDate1());
        // 登记人
        dbzjTzContractCondition.setRegistrant(zjTzSizeControlRecord.getRegistrant1());
        // 投资收益模式id
        dbzjTzContractCondition.setInvestId(zjTzSizeControlRecord.getInvestId());
        // 投资收益模式name
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getInvestId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControlRecord.getInvestId());
     	   dbzjTzContractCondition.setInvestName(openBankName);
        }
        // 一公局集团股比
        dbzjTzContractCondition.setJuShare(zjTzSizeControlRecord.getJuShare());
        // 一公局集团控制性地位id
        dbzjTzContractCondition.setJuId(zjTzSizeControlRecord.getJuId1());
        // 一公局集团控制性地位name
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getJuId1())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControlRecord.getJuId1());
     	   dbzjTzContractCondition.setJuName(openBankName);
        }
        // 总承包结算模式id
        dbzjTzContractCondition.setZcbId(zjTzSizeControlRecord.getZcbId());
        // 总承包结算模式name
        if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getZcbId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControlRecord.getZcbId());
     	   dbzjTzContractCondition.setZcbName(openBankName);
        }
        // 施工总承包比例
        dbzjTzContractCondition.setZcbShare(zjTzSizeControlRecord.getZcbShare());
        // 设计管理情况
        dbzjTzContractCondition.setExt1(zjTzSizeControlRecord.getExt1());
        // 合同对投资规模控制的要求
        dbzjTzContractCondition.setExt2(zjTzSizeControlRecord.getExt2());
        // 设计变更特殊条款
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

    //修改删除都是按顺序操作的，  每一次都要变动一下主表
    @Override
    public ResponseEntity updateZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSizeControlRecord dbzjTzSizeControlRecord = zjTzSizeControlRecordMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSizeControlRecordId());
        if (dbzjTzSizeControlRecord != null && StrUtil.isNotEmpty(dbzjTzSizeControlRecord.getSizeControlRecordId())) {
        	//修改校验,只能修改最新的一条数据
        	ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
        	record.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
        	List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
        	if(records != null &&records.size() > 1) {
        		for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
        			if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
        				return repEntity.layerMessage("no", "只能修改最新的数据，请重新选择!!");
        			}
        		}
        	}else {
        		//只有1条做修改的时候这2个值的计算方法不变
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
        	
        	//修改主表的本次、last
            ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(dbzjTzSizeControlRecord.getSizeControlId());
            //last=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
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
        		//有值则去取上一次的aomunt1
        		dbzjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount1(), lastRecords.get(0).getAmount1()));
        		dbzjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzSizeControlRecord.getAmount2(), lastRecords.get(0).getAmount2()));
        	}
           // 项目id
           dbzjTzSizeControlRecord.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
           // 项目id
           dbzjTzSizeControlRecord.setProjectId(zjTzSizeControlRecord.getProjectId());
           // 子项目id
           dbzjTzSizeControlRecord.setSubprojectInfoId(zjTzSizeControlRecord.getSubprojectInfoId());
           // 子项目name
           ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControlRecord.getSubprojectInfoId());
           if(info != null && StrUtil.isNotEmpty(info.getProjectId())) {
        	   dbzjTzSizeControlRecord.setSubprojectName(info.getSubprojectName());
           }
           // 变动次数
//           dbzjTzSizeControlRecord.setChangeNumber(zjTzSizeControlRecord.getChangeNumber());
           // 变动属性id
           dbzjTzSizeControlRecord.setChangePropertyId(zjTzSizeControlRecord.getChangePropertyId());
           // 变动属性name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getChangePropertyId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControlRecord.getChangePropertyId());
           	dbzjTzSizeControlRecord.setChangePropertyName(openBankName);
           }
           // 投资规模(元)
           dbzjTzSizeControlRecord.setAmount1(zjTzSizeControlRecord.getAmount1());
           // 建安费(元)
           dbzjTzSizeControlRecord.setAmount2(zjTzSizeControlRecord.getAmount2());
           // 查缺补漏(元)
           dbzjTzSizeControlRecord.setAmount3(zjTzSizeControlRecord.getAmount3());
           // 是否二次谈判id
           dbzjTzSizeControlRecord.setSecondNegotiateId(zjTzSizeControlRecord.getSecondNegotiateId());
           // 是否二次谈判name
           if (StrUtil.equals(zjTzSizeControlRecord.getSecondNegotiateId(), "1")) {
        	   dbzjTzSizeControlRecord.setSecondNegotiateName("是");
           }else {
        	   dbzjTzSizeControlRecord.setSecondNegotiateName("否");
           }
           // 查缺补漏方案
           dbzjTzSizeControlRecord.setScheme(zjTzSizeControlRecord.getScheme());
           // 三会批复id
           dbzjTzSizeControlRecord.setThirdReplyId(zjTzSizeControlRecord.getThirdReplyId());
           // 三会批复name
           if (StrUtil.equals(zjTzSizeControlRecord.getThirdReplyId(), "1")) {
        	   dbzjTzSizeControlRecord.setThirdReplyName("是");
           }else {
        	   dbzjTzSizeControlRecord.setThirdReplyName("否");
           }
           // 地方政府批复id
           dbzjTzSizeControlRecord.setLocalGovId(zjTzSizeControlRecord.getLocalGovId());
           // 地方政府批复name
           if (StrUtil.equals(zjTzSizeControlRecord.getLocalGovId(), "1")) {
        	   dbzjTzSizeControlRecord.setLocalGovName("是");
           }else {
        	   dbzjTzSizeControlRecord.setLocalGovName("否");
           }
           // 一公局集团批复id
           dbzjTzSizeControlRecord.setJuId(zjTzSizeControlRecord.getJuId());
           // 一公局集团批复name
           if (StrUtil.equals(zjTzSizeControlRecord.getJuId(), "1")) {
        	   dbzjTzSizeControlRecord.setJuName("是");
           }else {
        	   dbzjTzSizeControlRecord.setJuName("否");
           }
           // 中国交建批复id
           dbzjTzSizeControlRecord.setChinaId(zjTzSizeControlRecord.getChinaId());
           // 中国交建批复name
           if (StrUtil.equals(zjTzSizeControlRecord.getChinaId(), "1")) {
        	   dbzjTzSizeControlRecord.setChinaName("是");
           }else {
        	   dbzjTzSizeControlRecord.setChinaName("否");
           }
           // 登记日期
           dbzjTzSizeControlRecord.setRegisterDate(zjTzSizeControlRecord.getRegisterDate());
           // 登记人
           dbzjTzSizeControlRecord.setRegistrant(zjTzSizeControlRecord.getRegistrant());
           // 共通
           dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
           //备注
           dbzjTzSizeControlRecord.setRemarks(zjTzSizeControlRecord.getRemarks());
           //是否更新
//           dbzjTzSizeControlRecord.setRenew(zjTzSizeControlRecord.getRenew());
           //规模控制主体
           dbzjTzSizeControlRecord.setSizeControlSubject(zjTzSizeControlRecord.getSizeControlSubject());
           //本次=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
           zjTzSizeControl.setChangePropertyId(dbzjTzSizeControlRecord.getChangePropertyId());
           zjTzSizeControl.setChangePropertyName(dbzjTzSizeControlRecord.getChangePropertyName());
           zjTzSizeControl.setAmount1(zjTzSizeControl.getAmount1());
           zjTzSizeControl.setAmount2(zjTzSizeControl.getAmount2());
           zjTzSizeControl.setAmount3(zjTzSizeControl.getAmount3());
           flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
           //执行修改主表
           flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
           
           //附件先删除再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzSizeControlRecord.getSizeControlRecordId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           //附件1
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
           //附件2
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
           //附件3
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
           //附件4
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
           //附件5
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
           
           //合同==先删除再新增 
           ZjTzContractConditionRecord dbzjTzContractCondition = new ZjTzContractConditionRecord();
           dbzjTzContractCondition.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
           List<ZjTzContractConditionRecord> conditionRecords = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(dbzjTzContractCondition);
           if(conditionRecords != null && conditionRecords.size() >0) {
        	   dbzjTzContractCondition.setModifyUserInfo(userKey, realName);
        	   zjTzContractConditionRecordMapper.batchDeleteUpdateZjTzContractConditionRecord(conditionRecords, dbzjTzContractCondition);
        	   //附件也是先删除再新增
        	   ZjTzFile zjTzFileSelect1 = new ZjTzFile();
        	   zjTzFileSelect1.setOtherId(conditionRecords.get(0).getContractConditionRecordId());
               List<ZjTzFile> deleteZjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect1);
               if(deleteZjTzFileList1 != null && deleteZjTzFileList1.size()>0) {
            	   zjTzFileSelect1.setModifyUserInfo(userKey, realName);
            	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList1, zjTzFileSelect1);
               }
           }
           dbzjTzContractCondition.setContractConditionRecordId(UuidUtil.generate());
           // 投资规模控制记录id
           dbzjTzContractCondition.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
           // 投资规模控制id
           dbzjTzContractCondition.setSizeControlId(zjTzSizeControlRecord.getSizeControlId());
           // 项目id
           dbzjTzContractCondition.setProjectId(zjTzSizeControlRecord.getProjectId());
           // 登记日期
           dbzjTzContractCondition.setRegisterDate(zjTzSizeControlRecord.getRegisterDate1());
           // 登记人
           dbzjTzContractCondition.setRegistrant(zjTzSizeControlRecord.getRegistrant1());
           // 投资收益模式id
           dbzjTzContractCondition.setInvestId(zjTzSizeControlRecord.getInvestId());
           // 投资收益模式name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getInvestId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControlRecord.getInvestId());
        	   dbzjTzContractCondition.setInvestName(openBankName);
           }
           // 一公局集团股比
           dbzjTzContractCondition.setJuShare(zjTzSizeControlRecord.getJuShare());
           // 一公局集团控制性地位id
           dbzjTzContractCondition.setJuId(zjTzSizeControlRecord.getJuId1());
           // 一公局集团控制性地位name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getJuId1())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControlRecord.getJuId1());
        	   dbzjTzContractCondition.setJuName(openBankName);
           }
           // 总承包结算模式id
           dbzjTzContractCondition.setZcbId(zjTzSizeControlRecord.getZcbId());
           // 总承包结算模式name
           if (StrUtil.isNotEmpty(zjTzSizeControlRecord.getZcbId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControlRecord.getZcbId());
        	   dbzjTzContractCondition.setZcbName(openBankName);
           }
           // 施工总承包比例
           dbzjTzContractCondition.setZcbShare(zjTzSizeControlRecord.getZcbShare());
           // 设计管理情况
           dbzjTzContractCondition.setExt1(zjTzSizeControlRecord.getExt1());
           // 合同对投资规模控制的要求
           dbzjTzContractCondition.setExt2(zjTzSizeControlRecord.getExt2());
           // 设计变更特殊条款
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
        // 失败
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
        	//删除校验,只能删除最新的一条数据===如果只有一条记录数据则把外层的主表数据也一并删除了
        	if(zjTzSizeControlRecordList.size() > 1) {
        		return repEntity.layerMessage("no", "只能删除一条，请重新选择！");
        	}else {
        		ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
            	record.setSizeControlId(zjTzSizeControlRecordList.get(0).getSizeControlId());
            	List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
            	if(records != null &&records.size() > 1) {
            		changeNumber = records.size();
            		for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
            			if(tzSizeControlRecord.getChangeNumber() >zjTzSizeControlRecordList.get(0).getChangeNumber()) {
            				return repEntity.layerMessage("no", "只能删除最新的数据，请重新选择!!");
            			}
            		}
            		//情况2是:有多条记录数据，删除最新的一条，同时修改主数据，  本次变上次，上次变大上次(是记录表变动次数是changeNumber-1的本次)
                	//更新主表的变动次数、本次、last
                	ZjTzSizeControl zjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControlRecordList.get(0).getSizeControlId());
                	zjTzSizeControl.setChangeNumber(changeNumber-1);
                	//上次变大上次(是记录表变动次数是changeNumber-1的本次)
                	ZjTzSizeControlRecord lastSizeControlRecord = new ZjTzSizeControlRecord();
                	lastSizeControlRecord.setSizeControlId(zjTzSizeControlRecordList.get(0).getSizeControlId());
                	lastSizeControlRecord.setChangeNumber(changeNumber-1);
                	List<ZjTzSizeControlRecord> lastSizeRecords = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(lastSizeControlRecord);
                	if(lastSizeRecords != null && lastSizeRecords.size() >0) {
                		//last=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
                		zjTzSizeControl.setLastChangePropertyId(lastSizeRecords.get(0).getChangePropertyId());
                		zjTzSizeControl.setLastChangePropertyName(lastSizeRecords.get(0).getChangePropertyName());
                		zjTzSizeControl.setLastAmount1(lastSizeRecords.get(0).getAmount1());
                		zjTzSizeControl.setLastAmount2(lastSizeRecords.get(0).getAmount2());
                		zjTzSizeControl.setLastAmount3(lastSizeRecords.get(0).getAmount3());
                	}else {
                		//last=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
                		zjTzSizeControl.setLastChangePropertyId("");
                		zjTzSizeControl.setLastChangePropertyName("");
                		zjTzSizeControl.setLastAmount1(new BigDecimal("0"));
                		zjTzSizeControl.setLastAmount2(new BigDecimal("0"));
                		zjTzSizeControl.setLastAmount3(new BigDecimal("0"));
                	}
                    //本次=变动属性、投资规模(元)、建安费(元)、查缺补漏(元)
                    zjTzSizeControl.setChangePropertyId(zjTzSizeControl.getLastChangePropertyId());
                    zjTzSizeControl.setChangePropertyName(zjTzSizeControl.getLastChangePropertyName());
                    zjTzSizeControl.setAmount1(zjTzSizeControl.getLastAmount1());
                    zjTzSizeControl.setAmount2(zjTzSizeControl.getLastAmount2());
                    zjTzSizeControl.setAmount3(zjTzSizeControl.getLastAmount3());
                    zjTzSizeControl.setAddFlag("true");
                    
                    //del记录表
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
					
                    //update主表
                    flag = zjTzSizeControlMapper.updateByPrimaryKey(zjTzSizeControl);
            	}else {
            		//这个执行删除  ===情况1是:只有一条记录数据则把外层的主表数据也一并删除了
            		for (ZjTzSizeControlRecord zjTzSizeControlRecord : records) {
						//del合同条件记录表
            			ZjTzContractConditionRecord delConditionRecord = new ZjTzContractConditionRecord();
            			delConditionRecord.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
            			List<ZjTzContractConditionRecord> delConditionRecords = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(delConditionRecord);
            			if(delConditionRecords != null && delConditionRecords.size() >0) {
            				//del合同条件记录表的附件
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
            		//del主数据
            		List<ZjTzSizeControl> delSizeControlList = new ArrayList<>();
            		ZjTzSizeControl control = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControlRecordList.get(0).getSizeControlId());
            		if(control != null && StrUtil.isNotEmpty(control.getSizeControlId())) {
            			//del主数据相关的合同条件
            			ZjTzContractCondition condition = new ZjTzContractCondition();
            			condition.setSizeControlId(control.getSizeControlId());
            			List<ZjTzContractCondition> delConditions = zjTzContractConditionMapper.selectByZjTzContractConditionList(condition);
            			if(delConditions != null && delConditions.size() >0) {
            				//del主合同条件的附件
            				for (ZjTzContractCondition zjTzContractCondition : delConditions) {
            					ZjTzFile file = new ZjTzFile();
            					file.setOtherId(zjTzContractCondition.getContractConditionId());
            					List<ZjTzFile> delFileList = zjTzFileMapper.selectByZjTzFileList(file);
            					if(delFileList != null && delFileList.size() >0) {
            						file.setModifyUserInfo(userKey, realName);
            						flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(delFileList, file);
            					}
							}
            				//del主合同条件
            				condition.setModifyUserInfo(userKey, realName);
            				flag = zjTzContractConditionMapper.batchDeleteUpdateZjTzContractCondition(delConditions, condition);
            			}
            			delSizeControlList.add(control);
            			if(delSizeControlList != null && delSizeControlList.size() >0) {
            				for (ZjTzSizeControl sizeControl : delSizeControlList) {
								//del主规模投资的附件
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
        // 失败
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
    			return repEntity.layerMessage("no", "已经上报，请重新选择!!");
    		}else if (StrUtil.equals(dbzjTzSizeControlRecord.getStatusId(), "3") && StrUtil.equals("4", ext1)) {
    			return repEntity.layerMessage("no", "无法上报，请重新选择!!");
			}
    		//修改校验,只能修改最新的一条数据
    		ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
    		record.setSizeControlId(dbzjTzSizeControlRecord.getSizeControlId());
    		List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
    		if(records != null &&records.size() > 1) {
    			for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
    				if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
    					return repEntity.layerMessage("no", "只能修改最新的数据，请重新选择!!");
    				}
    			}
    			
    			//是最新的一条数据，可以上报
    			if (StrUtil.equals("4", ext1)) {
    				dbzjTzSizeControlRecord.setStatusId("3");
    				dbzjTzSizeControlRecord.setStatusName("托管项目上报");
				}else {
					dbzjTzSizeControlRecord.setStatusId("1");
					dbzjTzSizeControlRecord.setStatusName("已上报");
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
    		
    			//执行修改主表
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
    			//是最新的一条数据，可以上报
    			if (StrUtil.equals("4", ext1)) {
    				dbzjTzSizeControlRecord.setStatusId("3");
    				dbzjTzSizeControlRecord.setStatusName("托管项目上报");
				}else {
					dbzjTzSizeControlRecord.setStatusId("1");
					dbzjTzSizeControlRecord.setStatusName("已上报");
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
    		
    			//执行修改主表
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
        // 失败
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
    			return repEntity.layerMessage("no", "包含未上报或被撤回的数据，请重新选择!!");
    		}
    		//修改校验,只能撤回最新的一条上报数据
    		ZjTzSizeControlRecord record = new ZjTzSizeControlRecord();
    		record.setSizeControlId(dbzjTzSizeControlRecord.getSizeControlId());
    		List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(record);
    		if(records != null &&records.size() > 1) {
    			for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
    				if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
    					return repEntity.layerMessage("no", "只能撤回最新的数据，请重新选择!!");
    				}
    			}
    			//是最新的一条数据，可以上报
    			dbzjTzSizeControlRecord.setStatusId("2");
    			dbzjTzSizeControlRecord.setStatusName("被撤回");
    			dbzjTzSizeControlRecord.setRenew1("0");
    			dbzjTzSizeControlRecord.setRenew2("0");
    			dbzjTzSizeControlRecord.setRenew3("");
    			dbzjTzSizeControlRecord.setRenew4("");
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    			//执行修改主表
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
    			//是最新的一条数据，可以上报
    			dbzjTzSizeControlRecord.setStatusId("2");
    			dbzjTzSizeControlRecord.setStatusName("被撤回");
    			dbzjTzSizeControlRecord.setRenew1("0");
    			dbzjTzSizeControlRecord.setRenew2("0");
    			dbzjTzSizeControlRecord.setRenew3("");
    			dbzjTzSizeControlRecord.setRenew4("");
    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
    			//执行修改主表
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
        // 失败
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
//    			return repEntity.layerMessage("no", "包含未上报或被撤回的数据，请重新选择!!");
//    		}
    		//修改校验,只能撤回最新的一条上报数据
//    		if(records != null &&records.size() > 1) {
//    			for (ZjTzSizeControlRecord tzSizeControlRecord : records) {
//    				if(tzSizeControlRecord.getChangeNumber() >dbzjTzSizeControlRecord.getChangeNumber()) {
//    					return repEntity.layerMessage("no", "只能撤回最新的数据，请重新选择!!");
//    				}
//    				
//    			}
    			//是最新的一条数据，可以上报
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
    				//执行修改主表
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
//    			//是最新的一条数据，可以上报
//    			dbzjTzSizeControlRecord.setRenew1("");
//    			dbzjTzSizeControlRecord.setRenew2("");
//    			dbzjTzSizeControlRecord.setRenew3("");
//    			dbzjTzSizeControlRecord.setRenew4("");
//    			dbzjTzSizeControlRecord.setModifyUserInfo(userKey, realName);
//    			flag = zjTzSizeControlRecordMapper.updateByPrimaryKey(dbzjTzSizeControlRecord);
//    			//执行修改主表
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
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSizeControlRecord);
        }
	
	}
}
