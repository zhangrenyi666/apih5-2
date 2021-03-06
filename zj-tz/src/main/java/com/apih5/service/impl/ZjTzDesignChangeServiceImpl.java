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
        // ????????????
        PageHelper.startPage(zjTzDesignChange.getPage(),zjTzDesignChange.getLimit());
     // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzDesignChange.getProjectId(), true)) {
            	zjTzDesignChange.setProjectId("");
            	zjTzDesignChange.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzDesignChange.getProjectId(), true)) {
            	zjTzDesignChange.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzDesignChange> zjTzDesignChangeList = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(zjTzDesignChange);
        for (ZjTzDesignChange zjTzDesignChange2 : zjTzDesignChangeList) {
            ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
            zjTzFileContractConditionSelect.setOtherId(zjTzDesignChange2.getDesignChangeId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
            zjTzDesignChange2.setZjTzFileList(zjTzFileList);
            // ?????????????????????????????????????????????????????????
            int changeNum = 0;
            BigDecimal changeAmount = new BigDecimal("0");
            
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            //????????????????????????
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
        // ??????????????????
        PageInfo<ZjTzDesignChange> p = new PageInfo<>(zjTzDesignChangeList);

        return repEntity.okList(zjTzDesignChangeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignChangeDetails(ZjTzDesignChange zjTzDesignChange) {
        if (zjTzDesignChange == null) {
            zjTzDesignChange = new ZjTzDesignChange();
        }
        // ????????????
        ZjTzDesignChange dbZjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzDesignChange.getDesignChangeId());
        // ????????????
        if (dbZjTzDesignChange != null) {
        	ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
            zjTzFileContractConditionSelect.setOtherId(dbZjTzDesignChange.getDesignChangeId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
            dbZjTzDesignChange.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzDesignChange);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignChange(ZjTzDesignChange zjTzDesignChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
      //????????????id????????????id   ????????????
    	if(StrUtil.isNotEmpty(zjTzDesignChange.getSubprojectInfoId())) {
    		ZjTzDesignChange zjTzSizeControlSelect = new ZjTzDesignChange();
    		zjTzSizeControlSelect.setProjectId(zjTzDesignChange.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
    		List<ZjTzDesignChange> sizeControls = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		//???????????????
    		ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignChange.getSubprojectInfoId());
    		if(info != null) {
    			zjTzDesignChange.setSubprojectName(info.getSubprojectName());
    		}
    	}else {
    		//????????????????????????  = ?????????id??????
    		ZjTzDesignChange zjTzSizeControlSelect = new ZjTzDesignChange();
    		zjTzSizeControlSelect.setProjectId(zjTzDesignChange.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId("???");
    		List<ZjTzDesignChange> sizeControls = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		zjTzDesignChange.setSubprojectInfoId("???");
    	}
        
        zjTzDesignChange.setDesignChangeId(UuidUtil.generate());
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignChange.getDesignStageId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignChange.getDesignStageId());
        	zjTzDesignChange.setDesignStageName(openBankName);
    	}
        //????????????(1-6:A.B.C1.C2.D1.D2)
        if (StrUtil.isNotEmpty(zjTzDesignChange.getChangeLevelId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianGengDengJi", zjTzDesignChange.getChangeLevelId());
        	zjTzDesignChange.setChangeLevelName(openBankName);
        	
//        	if(zjTzDesignChange.getChangeAmount() != null) {
//        		if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "1")) {//500,+???
//        			if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("500")) >=0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "2")) {//200,500
//            		if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("200")) >=0 && zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("500")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "3") || StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "4")) {//80,200
//            		if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("80")) >=0 && zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("200")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "5") || StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "6")) {//30,80
//            		if(zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("30")) >=0 && zjTzDesignChange.getChangeAmount().compareTo(new BigDecimal("80")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}	
//        	}
    	}
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignChange.getChangeNatureId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianGengXingZhi", zjTzDesignChange.getChangeNatureId());
        	zjTzDesignChange.setChangeNatureName(openBankName);
    	}
        if (StrUtil.equals(zjTzDesignChange.getDynamicId(), "1")) {
        	zjTzDesignChange.setDynamicName("???");
    	}else {
    		zjTzDesignChange.setDynamicName("???");
    	}
        if (StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {
        	zjTzDesignChange.setInnerCheckName("???");
    	}else {
    		zjTzDesignChange.setInnerCheckName("???");
    	}
        if (StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {
        	zjTzDesignChange.setLegalName("???");
    	}else {
    		zjTzDesignChange.setLegalName("???");
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
    	//add????????????
    	ZjTzDesignChangeRecord dbzjTzDesignChangeRecord = new ZjTzDesignChangeRecord();
    	dbzjTzDesignChangeRecord.setDesignChangeRecordId(UuidUtil.generate());
    	 // ????????????id
        dbzjTzDesignChangeRecord.setDesignChangeId(zjTzDesignChange.getDesignChangeId());
        // ??????id
        dbzjTzDesignChangeRecord.setProjectId(zjTzDesignChange.getProjectId());
        // ??????name
        dbzjTzDesignChangeRecord.setProjectName(zjTzDesignChange.getProjectName());
        // ?????????id
        dbzjTzDesignChangeRecord.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
        // ?????????name
        dbzjTzDesignChangeRecord.setSubprojectName(zjTzDesignChange.getSubprojectName());
        // ????????????id
        dbzjTzDesignChangeRecord.setDesignStageId(zjTzDesignChange.getDesignStageId());
        // ????????????name
        dbzjTzDesignChangeRecord.setDesignStageName(zjTzDesignChange.getDesignStageName());
        // ??????????????????
        dbzjTzDesignChangeRecord.setDesignChangeNumber(zjTzDesignChange.getDesignChangeNumber());
        // ??????????????????
        dbzjTzDesignChangeRecord.setDesignChangeName(zjTzDesignChange.getDesignChangeName());
        // ????????????id
        dbzjTzDesignChangeRecord.setChangeLevelId(zjTzDesignChange.getChangeLevelId());
        // ????????????name
        dbzjTzDesignChangeRecord.setChangeLevelName(zjTzDesignChange.getChangeLevelName());
        // ????????????id
        dbzjTzDesignChangeRecord.setChangeNatureId(zjTzDesignChange.getChangeNatureId());
        // ????????????name
        dbzjTzDesignChangeRecord.setChangeNatureName(zjTzDesignChange.getChangeNatureName());
        // ???????????????????????????id
        dbzjTzDesignChangeRecord.setDynamicId(zjTzDesignChange.getDynamicId());
        // ???????????????????????????name
        dbzjTzDesignChangeRecord.setDynamicName(zjTzDesignChange.getDynamicName());
        // ??????????????????
        dbzjTzDesignChangeRecord.setChangeAmount(zjTzDesignChange.getChangeAmount());
        // ????????????
        dbzjTzDesignChangeRecord.setChangeDate(zjTzDesignChange.getChangeDate());
        // ????????????????????????
        dbzjTzDesignChangeRecord.setContent(zjTzDesignChange.getContent());
        dbzjTzDesignChangeRecord.setStatusId("0");
        dbzjTzDesignChangeRecord.setStatusName("?????????");
        // ??????
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
    	//add ??????======???????????????????????????30????????????
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
            	 if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "1")) {//??????
            		 extA1 = extA1 + 1;
            		 extA2 = CalcUtils.calcAdd(extA2, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "2")) {//??????
            		 extA3 = extA3 + 1;
            		 extA4 = CalcUtils.calcAdd(extA4, zjTzDesignChange.getChangeAmount());
            	 }
            	 extA5 = extA1 + extA3;
            	 extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//??????????????????
            		 extA7 = extA7 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//??????????????????
            		 extA8 = extA8 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "2")) {//========b
            	 if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "1")) {//??????
            		 extB1 = extB1 + 1;
            		 extB2 = CalcUtils.calcAdd(extB2, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getChangeNatureId(), "2")) {//??????
            		 extB3 = extB3 + 1;
            		 extB4 = CalcUtils.calcAdd(extB4, zjTzDesignChange.getChangeAmount());
            	 }
            	 extB5 = extB5 + extB1 + extB3;
            	 extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//??????????????????
            		 extB7 = extB7 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//??????????????????
            		 extB8 = extB8 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "3")) {//=============c1
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//??????
            		 extC11 = extC11 + 1;
            		 extC12 = CalcUtils.calcAdd(extC12, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "1")) {//??????
            		 extC13 = extC13 + 1;
            		 extC14 = CalcUtils.calcAdd(extC14, zjTzDesignChange.getChangeAmount());
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//??????????????????
            		 extC15 = extC15 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//??????????????????
            		 extC16 = extC16 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "4")) {//=============c2
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//??????
            		 extC21 = extC21 + 1;
            		 extC22 = CalcUtils.calcAdd(extC22, zjTzDesignChange.getChangeAmount());
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//??????????????????
            		 extC23 = extC23 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//??????????????????
            		 extC24 = extC24 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "5")) {//=============d1
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//??????
            		 extD11 = extD11 + 1;
            		 extD12 = CalcUtils.calcAdd(extD12, zjTzDesignChange.getChangeAmount());
            	 }else if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "1")) {//??????
            		 extD13 = extD13 + 1;
            		 extD14 = CalcUtils.calcAdd(extD14, zjTzDesignChange.getChangeAmount());
            	 } if(StrUtil.equals(zjTzDesignChange.getInnerCheckId(), "1")) {//??????????????????
            		 extD15 = extD15 + 1;
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//??????????????????
            		 extD16 = extD16 + 1;
            	 }
             }else if(StrUtil.equals(zjTzDesignChange.getChangeLevelId(), "6")) {//=============d2
            	 if(StrUtil.equals(zjTzDesignChange.getDynamicId(), "0")) {//??????
            		 extD21 = extD21 + 1;
            		 extD22 = CalcUtils.calcAdd(extD22, zjTzDesignChange.getChangeAmount());
            	 }
            	 if(StrUtil.equals(zjTzDesignChange.getLegalId(), "1")) {//??????????????????
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
             // a????????????
             dbzjTzDesignChangeStatistics.setExtA1(extA1);
             // a????????????
             dbzjTzDesignChangeStatistics.setExtA2(extA2);
             // a????????????
             dbzjTzDesignChangeStatistics.setExtA3(extA3);
             // a????????????
             dbzjTzDesignChangeStatistics.setExtA4(extA4);
             // a?????????
             dbzjTzDesignChangeStatistics.setExtA5(extA5);
             // a?????????
             dbzjTzDesignChangeStatistics.setExtA6(extA6);
             // a???????????????????????????
             dbzjTzDesignChangeStatistics.setExtA7(extA7);
             // a???????????????????????????
             dbzjTzDesignChangeStatistics.setExtA8(extA8);
             // b????????????
             dbzjTzDesignChangeStatistics.setExtB1(extB1);
             // b????????????
             dbzjTzDesignChangeStatistics.setExtB2(extB2);
             // b????????????
             dbzjTzDesignChangeStatistics.setExtB3(extB3);
             // b????????????
             dbzjTzDesignChangeStatistics.setExtB4(extB4);
             // b?????????
             dbzjTzDesignChangeStatistics.setExtB5(extB5);
             // b?????????
             dbzjTzDesignChangeStatistics.setExtB6(extB6);
             // b???????????????????????????
             dbzjTzDesignChangeStatistics.setExtB7(extB7);
             // b???????????????????????????
             dbzjTzDesignChangeStatistics.setExtB8(extB8);
             // c1????????????
             dbzjTzDesignChangeStatistics.setExtC11(extC11);
             // c1????????????
             dbzjTzDesignChangeStatistics.setExtC12(extC12);
             // c1??????????????????
             dbzjTzDesignChangeStatistics.setExtC13(extC13);
             // c1??????????????????
             dbzjTzDesignChangeStatistics.setExtC14(extC14);
             // c1???????????????????????????
             dbzjTzDesignChangeStatistics.setExtC15(extC15);
             // c1???????????????????????????
             dbzjTzDesignChangeStatistics.setExtC16(extC16);
             // c2????????????
             dbzjTzDesignChangeStatistics.setExtC21(extC21);
             // c2????????????
             dbzjTzDesignChangeStatistics.setExtC22(extC22);
             // c2???????????????????????????
             dbzjTzDesignChangeStatistics.setExtC23(extC23);
             // c2???????????????????????????
             dbzjTzDesignChangeStatistics.setExtC24(extC24);
             // d1????????????
             dbzjTzDesignChangeStatistics.setExtD11(extD11);
             // d1????????????
             dbzjTzDesignChangeStatistics.setExtD12(extD12);
             // d1??????????????????
             dbzjTzDesignChangeStatistics.setExtD13(extD13);
             // d1??????????????????
             dbzjTzDesignChangeStatistics.setExtD14(extD14);
             // d1???????????????????????????
             dbzjTzDesignChangeStatistics.setExtD15(extD15);
             // d1???????????????????????????
             dbzjTzDesignChangeStatistics.setExtD16(extD16);
             // d2????????????
             dbzjTzDesignChangeStatistics.setExtD21(extD21);
             // d2????????????
             dbzjTzDesignChangeStatistics.setExtD22(extD22);
             // d2???????????????????????????
             dbzjTzDesignChangeStatistics.setExtD23(extD23);
             // ???????????????
             dbzjTzDesignChangeStatistics.setExtAll1(extAll1);
             // ???????????????
             dbzjTzDesignChangeStatistics.setExtAll2(extAll2);
             // ???????????????
             dbzjTzDesignChangeStatistics.setExtAll3(extAll3);
             // ???????????????
             dbzjTzDesignChangeStatistics.setExtAll4(extAll4);
             // ?????????????????????
             dbzjTzDesignChangeStatistics.setExtAll5(extAll5);
             // ?????????????????????
             dbzjTzDesignChangeStatistics.setExtAll6(extAll6);
             // ?????????
             dbzjTzDesignChangeStatistics.setExtAll7(extAll7);
             // ?????????
             dbzjTzDesignChangeStatistics.setExtAll8(extAll8);
             // ??????
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
           // ??????id
           dbzjTzDesignChange.setProjectId(zjTzDesignChange.getProjectId());
           // ??????name
           dbzjTzDesignChange.setProjectName(zjTzDesignChange.getProjectName());
           // ?????????id
           dbzjTzDesignChange.setSubprojectInfoId(zjTzDesignChange.getSubprojectInfoId());
           // ?????????name
           dbzjTzDesignChange.setSubprojectName(zjTzDesignChange.getSubprojectName());
           // ????????????id
           dbzjTzDesignChange.setDesignStageId(zjTzDesignChange.getDesignStageId());
           // ????????????name
           dbzjTzDesignChange.setDesignStageName(zjTzDesignChange.getDesignStageName());
           // ????????????
           dbzjTzDesignChange.setChangeNum(zjTzDesignChange.getChangeNum());
           // ??????????????????
           dbzjTzDesignChange.setDesignChangeNumber(zjTzDesignChange.getDesignChangeNumber());
           // ??????????????????
           dbzjTzDesignChange.setDesignChangeName(zjTzDesignChange.getDesignChangeName());
           // ????????????id
           dbzjTzDesignChange.setChangeLevelId(zjTzDesignChange.getChangeLevelId());
           // ????????????name
           dbzjTzDesignChange.setChangeLevelName(zjTzDesignChange.getChangeLevelName());
           // ????????????id
           dbzjTzDesignChange.setChangeNatureId(zjTzDesignChange.getChangeNatureId());
           // ????????????name
           dbzjTzDesignChange.setChangeNatureName(zjTzDesignChange.getChangeNatureName());
           // ???????????????????????????id
           dbzjTzDesignChange.setDynamicId(zjTzDesignChange.getDynamicId());
           // ???????????????????????????name
           dbzjTzDesignChange.setDynamicName(zjTzDesignChange.getDynamicName());
           // ??????????????????
           dbzjTzDesignChange.setChangeAmount(zjTzDesignChange.getChangeAmount());
           // ????????????
           dbzjTzDesignChange.setChangeDate(zjTzDesignChange.getChangeDate());
           // ????????????????????????
           dbzjTzDesignChange.setContent(zjTzDesignChange.getContent());
           // ??????
           dbzjTzDesignChange.setRemarks(zjTzDesignChange.getRemarks());
           // ??????
           dbzjTzDesignChange.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignChangeMapper.updateByPrimaryKey(dbzjTzDesignChange);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignChangeList);
        }
    }
}
