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
import com.apih5.mybatis.dao.ZjTzDesignChangeMapper;
import com.apih5.mybatis.dao.ZjTzDesignChangeRecordMapper;
import com.apih5.mybatis.dao.ZjTzDesignChangeStatisticsMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnit;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitRecord;
import com.apih5.mybatis.pojo.ZjTzDesignChange;
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;
import com.apih5.mybatis.pojo.ZjTzDesignChangeStatistics;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSizeControl;
import com.apih5.service.ZjTzDesignChangeRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDesignChangeRecordService")
public class ZjTzDesignChangeRecordServiceImpl implements ZjTzDesignChangeRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;
    
    @Autowired(required = true)
   	private BaseCodeService baseCodeService;
   	
   	@Autowired(required = true)
   	private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzDesignChangeRecordMapper zjTzDesignChangeRecordMapper;
    
    @Autowired(required = true)
    private ZjTzDesignChangeMapper zjTzDesignChangeMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzDesignChangeStatisticsMapper zjTzDesignChangeStatisticsMapper;

    @Override
    public ResponseEntity getZjTzDesignChangeRecordListByCondition(ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzDesignChangeRecord == null) {
            zjTzDesignChangeRecord = new ZjTzDesignChangeRecord();
        }
        // ????????????????????????????????????????????????????????????
        if (StrUtil.equals("3", ext1)) {
            // ???????????????????????????????????????sql?????????
        	zjTzDesignChangeRecord.setExt1Flag1("????????????????????????????????????????????????????????????");
        //	???????????????????????????????????????
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzDesignChangeRecord.setExt1Flag2("???????????????????????????????????????");;
        // ???????????????????????????????????????????????????????????????	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzDesignChangeRecord.setExt1Flag3("???????????????????????????????????????????????????????????????");
		}
        // ????????????
        PageHelper.startPage(zjTzDesignChangeRecord.getPage(),zjTzDesignChangeRecord.getLimit());
        // ????????????
        List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(zjTzDesignChangeRecord);
        for (ZjTzDesignChangeRecord zjTzDesignChangeRecord2 : zjTzDesignChangeRecordList) {
        	// 
        	ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
        	zjTzFileContractConditionSelect.setOtherId(zjTzDesignChangeRecord2.getDesignChangeRecordId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
        	zjTzDesignChangeRecord2.setZjTzFileList(zjTzFileList);
        }
        // ??????????????????
        PageInfo<ZjTzDesignChangeRecord> p = new PageInfo<>(zjTzDesignChangeRecordList);

        return repEntity.okList(zjTzDesignChangeRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignChangeRecordDetails(ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        if (zjTzDesignChangeRecord == null) {
            zjTzDesignChangeRecord = new ZjTzDesignChangeRecord();
        }
        zjTzDesignChangeRecord = zjTzDesignChangeRecordMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getDesignChangeRecordId());
        // ????????????
        if (zjTzDesignChangeRecord != null && StrUtil.isNotEmpty(zjTzDesignChangeRecord.getDesignChangeRecordId())) {
        	ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
        	zjTzFileContractConditionSelect.setOtherId(zjTzDesignChangeRecord.getDesignChangeRecordId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
        	zjTzDesignChangeRecord.setZjTzFileList(zjTzFileList);
            return repEntity.ok(zjTzDesignChangeRecord);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignChangeRecord(ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
    	//add
    	//???????????????
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getSubprojectInfoId());
        if(info != null) {
        	zjTzDesignChangeRecord.setSubprojectName(info.getSubprojectName());
        }
        ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getProjectId());
        if(dbzjTzProManage != null) {
        	zjTzDesignChangeRecord.setProjectName(dbzjTzProManage.getProjectName());
        }
        zjTzDesignChangeRecord.setDesignChangeRecordId(UuidUtil.generate());
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignChangeRecord.getDesignStageId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignChangeRecord.getDesignStageId());
        	zjTzDesignChangeRecord.setDesignStageName(openBankName);
        }
      //????????????(1-6:A.B.C1.C2.D1.D2)
        if (StrUtil.isNotEmpty(zjTzDesignChangeRecord.getChangeLevelId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianGengDengJi", zjTzDesignChangeRecord.getChangeLevelId());
        	zjTzDesignChangeRecord.setChangeLevelName(openBankName);
        	
//        	if(zjTzDesignChangeRecord.getChangeAmount() != null) {
//        		if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "1")) {//500,+???
//        			if(zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("500")) >=0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "2")) {//200,500
//            		if(zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("200")) >=0 && zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("500")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "3") || StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "4")) {//80,200
//            		if(zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("80")) >=0 && zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("200")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "5") || StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "6")) {//30,80
//            		if(zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("30")) >=0 && zjTzDesignChangeRecord.getChangeAmount().compareTo(new BigDecimal("80")) <0) {
//            			
//            		}else {
//            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
//            		}
//            	}	
//        	}
        }
        //????????????
        if (StrUtil.isNotEmpty(zjTzDesignChangeRecord.getChangeNatureId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianGengXingZhi", zjTzDesignChangeRecord.getChangeNatureId());
        	zjTzDesignChangeRecord.setChangeNatureName(openBankName);
        }
        if (StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "1")) {
        	zjTzDesignChangeRecord.setDynamicName("???");
    	}else {
    		zjTzDesignChangeRecord.setDynamicName("???");
    	}
        if (StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {
        	zjTzDesignChangeRecord.setInnerCheckName("???");
    	}else {
    		zjTzDesignChangeRecord.setInnerCheckName("???");
    	}
        if (StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {
        	zjTzDesignChangeRecord.setLegalName("???");
    	}else {
    		zjTzDesignChangeRecord.setLegalName("???");
    	}
        zjTzDesignChangeRecord.setStatusId("0");
        zjTzDesignChangeRecord.setStatusName("?????????");
        zjTzDesignChangeRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignChangeRecordMapper.insert(zjTzDesignChangeRecord);
        List<ZjTzFile> ZjTzFileList = zjTzDesignChangeRecord.getZjTzFileList();
        if(ZjTzFileList != null && ZjTzFileList.size()>0) {
     	   for(ZjTzFile zjTzFile:ZjTzFileList) {
     		   zjTzFile.setUid(UuidUtil.generate());
     		   zjTzFile.setOtherId(zjTzDesignChangeRecord.getDesignChangeRecordId());
     		   zjTzFile.setCreateUserInfo(userKey, realName);
     		   zjTzFileMapper.insert(zjTzFile);
     	   }
        }
        //?????????????????????????????????????????????????????????
        int changeNum = 0;
        BigDecimal changeAmount = new BigDecimal("0");
        //????????????????????????
        ZjTzDesignChangeRecord record = new ZjTzDesignChangeRecord();
    	record.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
    	List<ZjTzDesignChangeRecord> records = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(record);
    	if(records != null &&records.size() > 0) {
    		changeNum = records.size();
    		for (ZjTzDesignChangeRecord zjTzDesignChangeRecord2 : records) {
    			changeAmount = CalcUtils.calcAdd(changeAmount, zjTzDesignChangeRecord2.getChangeAmount());
			}
    	}
        ZjTzDesignChange zjTzDesignChangeAdd = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getDesignChangeId());
        zjTzDesignChangeAdd.setChangeNum(changeNum);
        zjTzDesignChangeAdd.setChangeAmount(changeAmount);
        zjTzDesignChangeAdd.setModifyUserInfo(userKey, realName);
        flag = zjTzDesignChangeMapper.updateByPrimaryKey(zjTzDesignChangeAdd);
        
    	//add ??????======????????????????????????????????????30??????????????????
    	if(zjTzDesignChangeRecord.getChangeAmount() != null && zjTzDesignChangeRecord.getChangeAmount().abs().compareTo(new BigDecimal("30")) >= 0) {
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
        	ZjTzDesignChangeStatistics statistics = new ZjTzDesignChangeStatistics();
    		statistics.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
    		List<ZjTzDesignChangeStatistics> statisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(statistics);
    		if(statisticsList != null &&statisticsList.size() >0) {
    			for (ZjTzDesignChangeStatistics statisticsRecord : statisticsList) {
    				extA1 = extA1 + statisticsRecord.getExtA1();
    				extA2 = CalcUtils.calcAdd(extA2, statisticsRecord.getExtA2());
    				extA3 = extA3 + statisticsRecord.getExtA3();
    				extA4 = CalcUtils.calcAdd(extA4, statisticsRecord.getExtA4());
    		        extA5 = extA5 + statisticsRecord.getExtA5();
    		        extA6 = CalcUtils.calcAdd(extA6, statisticsRecord.getExtA6());
		        	extA7 = extA7 + statisticsRecord.getExtA7();
		        	extA8 = extA8 + statisticsRecord.getExtA8();
    		        extB1 = extB1 + statisticsRecord.getExtB1();
    				extB2 = CalcUtils.calcAdd(extB2, statisticsRecord.getExtB2());
    				extB3 = extB3 + statisticsRecord.getExtB3();
    				extB4 = CalcUtils.calcAdd(extB4, statisticsRecord.getExtB4());
    		        extB5 = extB5 + statisticsRecord.getExtB5();
    		        extB6 = CalcUtils.calcAdd(extB6, statisticsRecord.getExtB6());
    		        extB7 = extB7 + statisticsRecord.getExtB7();
    		        extB8 = extB8 + statisticsRecord.getExtB8();
    		        extC11 = extC11 + statisticsRecord.getExtC11();
    				extC12 = CalcUtils.calcAdd(extC12, statisticsRecord.getExtC12());
    				extC13 = extC13 + statisticsRecord.getExtC13();
    				extC14 = CalcUtils.calcAdd(extC14, statisticsRecord.getExtC14());
    		        extC15 = extC15 + statisticsRecord.getExtC15();
    		        extC16 = extC16 + statisticsRecord.getExtC16();
    		        extC21 = extC21 + statisticsRecord.getExtC21();
    				extC22 = CalcUtils.calcAdd(extC22, statisticsRecord.getExtC22()); 
    		        extC23 = extC23 + statisticsRecord.getExtC23();
    		        extC24 = extC24 + statisticsRecord.getExtC24();
    		        extD11 = extD11 + statisticsRecord.getExtD11();
    				extD12 = CalcUtils.calcAdd(extD12, statisticsRecord.getExtD12());
    				extD13 = extD13 + statisticsRecord.getExtD13();
    				extD14 = CalcUtils.calcAdd(extD14, statisticsRecord.getExtD14());
    		        extD15 = extD15 + statisticsRecord.getExtD15();
    		        extD16 = extD16 + statisticsRecord.getExtD16();
    		        extD21 = extD21 + statisticsRecord.getExtD21();
    				extD22 = CalcUtils.calcAdd(extD22, statisticsRecord.getExtD22());
    				extD23 = extD23 + statisticsRecord.getExtD23();
    		        extAll1 = extAll1 + statisticsRecord.getExtAll1();
    		        extAll2 = CalcUtils.calcAdd(extAll2, statisticsRecord.getExtAll2());
    		        extAll3 = extAll3 + statisticsRecord.getExtAll3();
    		        extAll4 = CalcUtils.calcAdd(extAll4, statisticsRecord.getExtAll4());
    		        extAll5 = extAll5 + statisticsRecord.getExtAll5();
    		        extAll6 = CalcUtils.calcAdd(extAll6, statisticsRecord.getExtAll6());
    		        extAll7 = extAll7 + statisticsRecord.getExtAll7();
    		        extAll8 = CalcUtils.calcAdd(extAll8, statisticsRecord.getExtAll8());
    			}
    			//???????????????30??????????????????===???update
    			ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = statisticsList.get(0);
                if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "1")) {//=============a
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "1")) {//??????
               		 extA1 = extA1 + 1;
               		 extA2 = CalcUtils.calcAdd(extA2, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "2")) {//??????
               		 extA3 = extA3 + 1;
               		 extA4 = CalcUtils.calcAdd(extA4, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 extA5 = extA1 + extA3;
               	 extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extA7 = extA7 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extA8 = extA8 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "2")) {//========b
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "1")) {//??????
               		 extB1 = extB1 + 1;
               		 extB2 = CalcUtils.calcAdd(extB2, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "2")) {//??????
               		 extB3 = extB3 + 1;
               		 extB4 = CalcUtils.calcAdd(extB4, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 extB5 = extB5 + extB1 + extB3;
               	 extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extB7 = extB7 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extB8 = extB8 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "3")) {//=============c1
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extC11 = extC11 + 1;
               		 extC12 = CalcUtils.calcAdd(extC12, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "1")) {//??????
               		 extC13 = extC13 + 1;
               		 extC14 = CalcUtils.calcAdd(extC14, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extC15 = extC15 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extC16 = extC16 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "4")) {//=============c2
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extC21 = extC21 + 1;
               		 extC22 = CalcUtils.calcAdd(extC22, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extC23 = extC23 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extC24 = extC24 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "5")) {//=============d1
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extD11 = extD11 + 1;
               		 extD12 = CalcUtils.calcAdd(extD12, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "1")) {//??????
               		 extD13 = extD13 + 1;
               		 extD14 = CalcUtils.calcAdd(extD14, zjTzDesignChangeRecord.getChangeAmount());
               	 } if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extD15 = extD15 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extD16 = extD16 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "6")) {//=============d2
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extD21 = extD21 + 1;
               		 extD22 = CalcUtils.calcAdd(extD22, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
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
                 dbzjTzDesignChangeStatistics.setRemarks(zjTzDesignChangeRecord.getRemarks());
                 dbzjTzDesignChangeStatistics.setModifyUserInfo(userKey, realName);
                 flag = zjTzDesignChangeStatisticsMapper.updateByPrimaryKey(dbzjTzDesignChangeStatistics);
    		}else {
    			//???????????????30??????????????????====???add
     			ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
     			dbzjTzDesignChangeStatistics.setDesignChangeStatisticsId(UuidUtil.generate());
     			dbzjTzDesignChangeStatistics.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
     			dbzjTzDesignChangeStatistics.setProjectId(zjTzDesignChangeRecord.getProjectId());
     			dbzjTzDesignChangeStatistics.setProjectName(zjTzDesignChangeRecord.getProjectName());
     			dbzjTzDesignChangeStatistics.setSubprojectInfoId(zjTzDesignChangeRecord.getSubprojectInfoId());
     			dbzjTzDesignChangeStatistics.setSubprojectName(zjTzDesignChangeRecord.getSubprojectName());
     			if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "1")) {//=============a
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "1")) {//??????
               		 extA1 = extA1 + 1;
               		 extA2 = CalcUtils.calcAdd(extA2, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "2")) {//??????
               		 extA3 = extA3 + 1;
               		 extA4 = CalcUtils.calcAdd(extA4, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 extA5 = extA1 + extA3;
               	 extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extA7 = extA7 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extA8 = extA8 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "2")) {//========b
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "1")) {//??????
               		 extB1 = extB1 + 1;
               		 extB2 = CalcUtils.calcAdd(extB2, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeNatureId(), "2")) {//??????
               		 extB3 = extB3 + 1;
               		 extB4 = CalcUtils.calcAdd(extB4, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 extB5 = extB5 + extB1 + extB3;
               	 extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extB7 = extB7 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extB8 = extB8 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "3")) {//=============c1
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extC11 = extC11 + 1;
               		 extC12 = CalcUtils.calcAdd(extC12, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "1")) {//??????
               		 extC13 = extC13 + 1;
               		 extC14 = CalcUtils.calcAdd(extC14, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extC15 = extC15 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extC16 = extC16 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "4")) {//=============c2
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extC21 = extC21 + 1;
               		 extC22 = CalcUtils.calcAdd(extC22, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extC23 = extC23 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extC24 = extC24 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "5")) {//=============d1
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extD11 = extD11 + 1;
               		 extD12 = CalcUtils.calcAdd(extD12, zjTzDesignChangeRecord.getChangeAmount());
               	 }else if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "1")) {//??????
               		 extD13 = extD13 + 1;
               		 extD14 = CalcUtils.calcAdd(extD14, zjTzDesignChangeRecord.getChangeAmount());
               	 } if(StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {//??????????????????
               		 extD15 = extD15 + 1;
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
               		 extD16 = extD16 + 1;
               	 }
                }else if(StrUtil.equals(zjTzDesignChangeRecord.getChangeLevelId(), "6")) {//=============d2
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
               		 extD21 = extD21 + 1;
               		 extD22 = CalcUtils.calcAdd(extD22, zjTzDesignChangeRecord.getChangeAmount());
               	 }
               	 if(StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {//??????????????????
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
                 dbzjTzDesignChangeStatistics.setRemarks(zjTzDesignChangeRecord.getRemarks());
                 dbzjTzDesignChangeStatistics.setReleaseId("0");
                 dbzjTzDesignChangeStatistics.setCreateUserInfo(userKey, realName);
                 flag = zjTzDesignChangeStatisticsMapper.insert(dbzjTzDesignChangeStatistics);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignChangeRecord);
        }
    }
    
    @Override
    public ResponseEntity updateZjTzDesignChangeRecord(ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignChangeRecord dbzjTzDesignChangeRecord = zjTzDesignChangeRecordMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getDesignChangeRecordId());
        if (dbzjTzDesignChangeRecord != null && StrUtil.isNotEmpty(dbzjTzDesignChangeRecord.getDesignChangeRecordId())) {
//           // ????????????id
//           dbzjTzDesignChangeRecord.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
//           // ??????id
//           dbzjTzDesignChangeRecord.setProjectId(zjTzDesignChangeRecord.getProjectId());
//           // ??????name
//           dbzjTzDesignChangeRecord.setProjectName(zjTzDesignChangeRecord.getProjectName());
//           // ?????????id
//           dbzjTzDesignChangeRecord.setSubprojectInfoId(zjTzDesignChangeRecord.getSubprojectInfoId());
//           // ?????????name
//           dbzjTzDesignChangeRecord.setSubprojectName(zjTzDesignChangeRecord.getSubprojectName());
           // ????????????id
           dbzjTzDesignChangeRecord.setDesignStageId(zjTzDesignChangeRecord.getDesignStageId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzDesignChangeRecord.getDesignStageId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("sheJiJieDuan", zjTzDesignChangeRecord.getDesignStageId());
        	   dbzjTzDesignChangeRecord.setDesignStageName(openBankName);
           }
           // ??????????????????
           dbzjTzDesignChangeRecord.setDesignChangeNumber(zjTzDesignChangeRecord.getDesignChangeNumber());
           // ??????????????????
           dbzjTzDesignChangeRecord.setDesignChangeName(zjTzDesignChangeRecord.getDesignChangeName());
           // ????????????id
           dbzjTzDesignChangeRecord.setChangeLevelId(zjTzDesignChangeRecord.getChangeLevelId());
           // ????????????name=(1-6:A.B.C1.C2.D1.D2)
           if (StrUtil.isNotEmpty(zjTzDesignChangeRecord.getChangeLevelId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("bianGengDengJi", zjTzDesignChangeRecord.getChangeLevelId());
        	   dbzjTzDesignChangeRecord.setChangeLevelName(openBankName);
           }
           // ????????????id
           dbzjTzDesignChangeRecord.setChangeNatureId(zjTzDesignChangeRecord.getChangeNatureId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzDesignChangeRecord.getChangeNatureId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("bianGengXingZhi", zjTzDesignChangeRecord.getChangeNatureId());
           	dbzjTzDesignChangeRecord.setChangeNatureName(openBankName);
           }
           // ???????????????????????????id
           dbzjTzDesignChangeRecord.setDynamicId(zjTzDesignChangeRecord.getDynamicId());
           // ???????????????????????????name
           if (StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "1")) {
        	   dbzjTzDesignChangeRecord.setDynamicName("???");
          	}else {
          		dbzjTzDesignChangeRecord.setDynamicName("???");
          	}
           //
           dbzjTzDesignChangeRecord.setInnerCheckId(zjTzDesignChangeRecord.getInnerCheckId());
           if (StrUtil.equals(zjTzDesignChangeRecord.getInnerCheckId(), "1")) {
        	   dbzjTzDesignChangeRecord.setInnerCheckName("???");
           }else {
        	   dbzjTzDesignChangeRecord.setInnerCheckName("???");
           }
           //
           dbzjTzDesignChangeRecord.setLegalId(zjTzDesignChangeRecord.getLegalId());
           if (StrUtil.equals(zjTzDesignChangeRecord.getLegalId(), "1")) {
        	   dbzjTzDesignChangeRecord.setLegalName("???");
           }else {
        	   dbzjTzDesignChangeRecord.setLegalName("???");
           }
           // ??????????????????
           dbzjTzDesignChangeRecord.setChangeAmount(zjTzDesignChangeRecord.getChangeAmount());
           // ????????????
           dbzjTzDesignChangeRecord.setChangeDate(zjTzDesignChangeRecord.getChangeDate());
           // ????????????????????????
           dbzjTzDesignChangeRecord.setContent(zjTzDesignChangeRecord.getContent());
           // ??????
           dbzjTzDesignChangeRecord.setRemarks(zjTzDesignChangeRecord.getRemarks());
           // ??????
           dbzjTzDesignChangeRecord.setModifyUserInfo(userKey, realName);
           
           flag = zjTzDesignChangeRecordMapper.updateByPrimaryKey(dbzjTzDesignChangeRecord);
           //????????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzDesignChangeRecord.getDesignChangeRecordId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> ZjTzFileList = zjTzDesignChangeRecord.getZjTzFileList();
           if(ZjTzFileList != null && ZjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:ZjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzDesignChangeRecord.getDesignChangeRecordId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
           //??????????????????????????????????????????
           BigDecimal changeAmount = new BigDecimal("0");
           //????????????????????????
           ZjTzDesignChangeRecord record = new ZjTzDesignChangeRecord();
           record.setDesignChangeId(dbzjTzDesignChangeRecord.getDesignChangeId());
           List<ZjTzDesignChangeRecord> records = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(record);
           if(records != null &&records.size() > 0) {
        	   for (ZjTzDesignChangeRecord zjTzDesignChangeRecord2 : records) {
        		   changeAmount = CalcUtils.calcAdd(changeAmount, zjTzDesignChangeRecord2.getChangeAmount());
        	   }
           }
           ZjTzDesignChange zjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(dbzjTzDesignChangeRecord.getDesignChangeId());
           zjTzDesignChange.setChangeAmount(changeAmount);
           zjTzDesignChange.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignChangeMapper.updateByPrimaryKey(zjTzDesignChange);
           
           //?????????????????????????????????????????????????????????????????????????????????????????????????????????
           if(zjTzDesignChange != null && StrUtil.isNotEmpty(zjTzDesignChange.getDesignChangeId())) {
               ZjTzDesignChangeStatistics delStatistics = new ZjTzDesignChangeStatistics();
               delStatistics.setDesignChangeId(zjTzDesignChange.getDesignChangeId());
               List<ZjTzDesignChangeStatistics> delStatisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(delStatistics);
               if(delStatisticsList != null && delStatisticsList.size() >0) {
            	   delStatistics.setModifyUserInfo(userKey, realName);
            	   flag = zjTzDesignChangeStatisticsMapper.batchDeleteUpdateZjTzDesignChangeStatistics(delStatisticsList, delStatistics);
               }
               //???????????????????????????????????????==??????add
               ZjTzDesignChangeRecord changeRecord = new ZjTzDesignChangeRecord();
               changeRecord.setDesignChangeId(dbzjTzDesignChangeRecord.getDesignChangeId());
               List<ZjTzDesignChangeRecord> changeRecordList = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(changeRecord);
               if(changeRecordList != null &&changeRecordList.size() > 0) {
            	   for (ZjTzDesignChangeRecord changeRecordSelect : changeRecordList) {
//==============================================??????=============================================================================================
            		   //add ??????======???????????????????????????30??????????????????
            		   if(changeRecordSelect.getChangeAmount() != null && changeRecordSelect.getChangeAmount().abs().compareTo(new BigDecimal("30")) >= 0) {
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
            			   ZjTzDesignChangeStatistics statistics = new ZjTzDesignChangeStatistics();
            			   statistics.setDesignChangeId(changeRecordSelect.getDesignChangeId());
            			   List<ZjTzDesignChangeStatistics> statisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(statistics);
            			   if(statisticsList != null &&statisticsList.size() >0) {
            				   for (ZjTzDesignChangeStatistics statisticsRecord : statisticsList) {
            					   extA1 = extA1 + statisticsRecord.getExtA1();
            					   extA2 = CalcUtils.calcAdd(extA2, statisticsRecord.getExtA2());
            					   extA3 = extA3 + statisticsRecord.getExtA3();
            					   extA4 = CalcUtils.calcAdd(extA4, statisticsRecord.getExtA4());
            					   extA5 = extA5 + statisticsRecord.getExtA5();
            					   extA6 = CalcUtils.calcAdd(extA6, statisticsRecord.getExtA6());
            					   extA7 = extA7 + statisticsRecord.getExtA7();
            					   extA8 = extA8 + statisticsRecord.getExtA8();
            					   extB1 = extB1 + statisticsRecord.getExtB1();
            					   extB2 = CalcUtils.calcAdd(extB2, statisticsRecord.getExtB2());
            					   extB3 = extB3 + statisticsRecord.getExtB3();
            					   extB4 = CalcUtils.calcAdd(extB4, statisticsRecord.getExtB4());
            					   extB5 = extB5 + statisticsRecord.getExtB5();
            					   extB6 = CalcUtils.calcAdd(extB6, statisticsRecord.getExtB6());
            					   extB7 = extB7 + statisticsRecord.getExtB7();
            					   extB8 = extB8 + statisticsRecord.getExtB8();
            					   extC11 = extC11 + statisticsRecord.getExtC11();
            					   extC12 = CalcUtils.calcAdd(extC12, statisticsRecord.getExtC12());
            					   extC13 = extC13 + statisticsRecord.getExtC13();
            					   extC14 = CalcUtils.calcAdd(extC14, statisticsRecord.getExtC14());
            					   extC15 = extC15 + statisticsRecord.getExtC15();
            					   extC16 = extC16 + statisticsRecord.getExtC16();
            					   extC21 = extC21 + statisticsRecord.getExtC21();
            					   extC22 = CalcUtils.calcAdd(extC22, statisticsRecord.getExtC22()); 
            					   extC23 = extC23 + statisticsRecord.getExtC23();
            					   extC24 = extC24 + statisticsRecord.getExtC24();
            					   extD11 = extD11 + statisticsRecord.getExtD11();
            					   extD12 = CalcUtils.calcAdd(extD12, statisticsRecord.getExtD12());
            					   extD13 = extD13 + statisticsRecord.getExtD13();
            					   extD14 = CalcUtils.calcAdd(extD14, statisticsRecord.getExtD14());
            					   extD15 = extD15 + statisticsRecord.getExtD15();
            					   extD16 = extD16 + statisticsRecord.getExtD16();
            					   extD21 = extD21 + statisticsRecord.getExtD21();
            					   extD22 = CalcUtils.calcAdd(extD22, statisticsRecord.getExtD22());
            					   extD23 = extD23 + statisticsRecord.getExtD23();
            					   extAll1 = extAll1 + statisticsRecord.getExtAll1();
            					   extAll2 = CalcUtils.calcAdd(extAll2, statisticsRecord.getExtAll2());
            					   extAll3 = extAll3 + statisticsRecord.getExtAll3();
            					   extAll4 = CalcUtils.calcAdd(extAll4, statisticsRecord.getExtAll4());
            					   extAll5 = extAll5 + statisticsRecord.getExtAll5();
            					   extAll6 = CalcUtils.calcAdd(extAll6, statisticsRecord.getExtAll6());
            					   extAll7 = extAll7 + statisticsRecord.getExtAll7();
            					   extAll8 = CalcUtils.calcAdd(extAll8, statisticsRecord.getExtAll8());
            				   }
            				   //???????????????30??????????????????===???update
            						   ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = statisticsList.get(0);
            				   if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "1")) {//=============a
            					   if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
            						   extA1 = extA1 + 1;
            						   extA2 = CalcUtils.calcAdd(extA2, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
            						   extA3 = extA3 + 1;
            						   extA4 = CalcUtils.calcAdd(extA4, changeRecordSelect.getChangeAmount());
            					   }
            					   extA5 = extA1 + extA3;
            					   extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extA7 = extA7 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extA8 = extA8 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "2")) {//========b
            					   if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
            						   extB1 = extB1 + 1;
            						   extB2 = CalcUtils.calcAdd(extB2, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
            						   extB3 = extB3 + 1;
            						   extB4 = CalcUtils.calcAdd(extB4, changeRecordSelect.getChangeAmount());
            					   }
            					   extB5 = extB5 + extB1 + extB3;
            					   extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extB7 = extB7 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extB8 = extB8 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "3")) {//=============c1
            					   if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
            						   extC11 = extC11 + 1;
            						   extC12 = CalcUtils.calcAdd(extC12, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
            						   extC13 = extC13 + 1;
            						   extC14 = CalcUtils.calcAdd(extC14, changeRecordSelect.getChangeAmount());
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extC15 = extC15 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extC16 = extC16 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "4")) {//=============c2
            					   if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
            						   extC21 = extC21 + 1;
            						   extC22 = CalcUtils.calcAdd(extC22, changeRecordSelect.getChangeAmount());
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extC23 = extC23 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extC24 = extC24 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "5")) {//=============d1
            					   if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
            						   extD11 = extD11 + 1;
            						   extD12 = CalcUtils.calcAdd(extD12, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
            						   extD13 = extD13 + 1;
            						   extD14 = CalcUtils.calcAdd(extD14, changeRecordSelect.getChangeAmount());
            					   } if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extD15 = extD15 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extD16 = extD16 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "6")) {//=============d2
            					   if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
            						   extD21 = extD21 + 1;
            						   extD22 = CalcUtils.calcAdd(extD22, changeRecordSelect.getChangeAmount());
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
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
            				   dbzjTzDesignChangeStatistics.setRemarks(changeRecordSelect.getRemarks());
            				   dbzjTzDesignChangeStatistics.setModifyUserInfo(userKey, realName);
            				   flag = zjTzDesignChangeStatisticsMapper.updateByPrimaryKey(dbzjTzDesignChangeStatistics);
            			   }else {
            				   //???????????????30??????????????????====???add
            				   ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
            				   dbzjTzDesignChangeStatistics.setDesignChangeStatisticsId(UuidUtil.generate());
            				   dbzjTzDesignChangeStatistics.setDesignChangeId(changeRecordSelect.getDesignChangeId());
            				   dbzjTzDesignChangeStatistics.setProjectId(changeRecordSelect.getProjectId());
            				   dbzjTzDesignChangeStatistics.setProjectName(changeRecordSelect.getProjectName());
            				   dbzjTzDesignChangeStatistics.setSubprojectInfoId(changeRecordSelect.getSubprojectInfoId());
            				   dbzjTzDesignChangeStatistics.setSubprojectName(changeRecordSelect.getSubprojectName());
            				   if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "1")) {//=============a
            					   if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
            						   extA1 = extA1 + 1;
            						   extA2 = CalcUtils.calcAdd(extA2, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
            						   extA3 = extA3 + 1;
            						   extA4 = CalcUtils.calcAdd(extA4, changeRecordSelect.getChangeAmount());
            					   }
            					   extA5 = extA1 + extA3;
            					   extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extA7 = extA7 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extA8 = extA8 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "2")) {//========b
            					   if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
            						   extB1 = extB1 + 1;
            						   extB2 = CalcUtils.calcAdd(extB2, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
            						   extB3 = extB3 + 1;
            						   extB4 = CalcUtils.calcAdd(extB4, changeRecordSelect.getChangeAmount());
            					   }
            					   extB5 = extB5 + extB1 + extB3;
            					   extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extB7 = extB7 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extB8 = extB8 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "3")) {//=============c1
            					   if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
            						   extC11 = extC11 + 1;
            						   extC12 = CalcUtils.calcAdd(extC12, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
            						   extC13 = extC13 + 1;
            						   extC14 = CalcUtils.calcAdd(extC14, changeRecordSelect.getChangeAmount());
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extC15 = extC15 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extC16 = extC16 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "4")) {//=============c2
            					   if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
            						   extC21 = extC21 + 1;
            						   extC22 = CalcUtils.calcAdd(extC22, changeRecordSelect.getChangeAmount());
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extC23 = extC23 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extC24 = extC24 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "5")) {//=============d1
            					   if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
            						   extD11 = extD11 + 1;
            						   extD12 = CalcUtils.calcAdd(extD12, changeRecordSelect.getChangeAmount());
            					   }else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
            						   extD13 = extD13 + 1;
            						   extD14 = CalcUtils.calcAdd(extD14, changeRecordSelect.getChangeAmount());
            					   } if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
            						   extD15 = extD15 + 1;
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
            						   extD16 = extD16 + 1;
            					   }
            				   }else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "6")) {//=============d2
            					   if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
            						   extD21 = extD21 + 1;
            						   extD22 = CalcUtils.calcAdd(extD22, changeRecordSelect.getChangeAmount());
            					   }
            					   if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
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
            				   dbzjTzDesignChangeStatistics.setRemarks(changeRecordSelect.getRemarks());
            				   dbzjTzDesignChangeStatistics.setCreateUserInfo(userKey, realName);
            				   dbzjTzDesignChangeStatistics.setReleaseId("0");
            				   flag = zjTzDesignChangeStatisticsMapper.insert(dbzjTzDesignChangeStatistics);
            			   }
            		   }
//======================================??????====================================================================
            	   }
               }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignChangeRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
    	if (zjTzDesignChangeRecordList != null && zjTzDesignChangeRecordList.size() > 0) {
    		ZjTzDesignChangeRecord zjTzDesignChangeRecordDel = new ZjTzDesignChangeRecord();
    		zjTzDesignChangeRecordDel.setModifyUserInfo(userKey, realName);
    		flag = zjTzDesignChangeRecordMapper.batchDeleteUpdateZjTzDesignChangeRecord(zjTzDesignChangeRecordList, zjTzDesignChangeRecordDel);
    		for (ZjTzDesignChangeRecord zjTzDesignChangeRecord : zjTzDesignChangeRecordList) {
    			//????????????
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherId(zjTzDesignChangeRecord.getDesignChangeRecordId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}

    			List<ZjTzDesignChange> delUnit = new ArrayList<>();
    			ZjTzDesignChange unit = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getDesignChangeId());
    			//1.?????????????????????????????????????????????????????? 
    			ZjTzDesignChangeRecord record = new ZjTzDesignChangeRecord();
    			record.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
    			List<ZjTzDesignChangeRecord> recordList = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(record);
    			if(recordList != null &&recordList.size() >0) {
    				//??????????????????????????? ??? ????????????==
    				if(unit != null && StrUtil.isNotEmpty(unit.getDesignChangeId())){
    					BigDecimal changeAmount = new BigDecimal("0");
    					for (ZjTzDesignChangeRecord zjTzDesignChangeRecord2 : zjTzDesignChangeRecordList) {
    						changeAmount = CalcUtils.calcAdd(changeAmount, zjTzDesignChangeRecord2.getChangeAmount());
    					}
    					for (ZjTzDesignChangeRecord dbzjTzDesignChangeRecord : recordList) {
    						if (StrUtil.equals(dbzjTzDesignChangeRecord.getRenew1(), "0")) {
    							count1 = count1 + 1;
    						}else if (StrUtil.equals(dbzjTzDesignChangeRecord.getRenew2(), "0")) {
    							count2 = count2 + 1;
    						}else if (StrUtil.equals(dbzjTzDesignChangeRecord.getRenew3(), "0")) {
    							count3 = count3 + 1;
    						}else if (StrUtil.equals(dbzjTzDesignChangeRecord.getRenew4(), "0")) {
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
    					unit.setChangeNum(recordList.size());
    					unit.setChangeAmount(changeAmount);
    					unit.setModifyUserInfo(userKey, realName);
    					zjTzDesignChangeMapper.updateByPrimaryKey(unit);
    				}
    			}else {
    				delUnit.add(unit);
    				if(delUnit != null && delUnit.size() >0) {
    					//???????????????
    					for (ZjTzDesignChange delUnitFile : delUnit) {
    						ZjTzFile zjTzFileUnit = new ZjTzFile();
    						zjTzFileUnit.setOtherId(delUnitFile.getDesignChangeId());
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
    					zjTzDesignChangeMapper.batchDeleteUpdateZjTzDesignChange(delUnit, unit);
    				}
    			}

    			//?????????????????????????????????????????????????????????????????????????????????????????????????????????
    			ZjTzDesignChangeStatistics delStatistics = new ZjTzDesignChangeStatistics();
    			delStatistics.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
    			List<ZjTzDesignChangeStatistics> delStatisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(delStatistics);
    			if(delStatisticsList != null && delStatisticsList.size() >0) {
    				delStatistics.setModifyUserInfo(userKey, realName);
    				flag = zjTzDesignChangeStatisticsMapper.batchDeleteUpdateZjTzDesignChangeStatistics(delStatisticsList, delStatistics);
    			}
    			//???????????????????????????????????????==??????add
    			ZjTzDesignChangeRecord changeRecord = new ZjTzDesignChangeRecord();
    			changeRecord.setDesignChangeId(zjTzDesignChangeRecord.getDesignChangeId());
    			List<ZjTzDesignChangeRecord> changeRecordList = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(changeRecord);
    			if(changeRecordList != null &&changeRecordList.size() > 0) {
    				for (ZjTzDesignChangeRecord changeRecordSelect : changeRecordList) {
    					//==============================================??????=============================================================================================
    					//add ??????======???????????????????????????30??????????????????
    					if(changeRecordSelect.getChangeAmount() != null && changeRecordSelect.getChangeAmount().abs().compareTo(new BigDecimal("30")) >= 0) {
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
    						ZjTzDesignChangeStatistics statistics = new ZjTzDesignChangeStatistics();
    						statistics.setDesignChangeId(changeRecordSelect.getDesignChangeId());
    						List<ZjTzDesignChangeStatistics> statisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(statistics);
    						if(statisticsList != null &&statisticsList.size() >0) {
    							for (ZjTzDesignChangeStatistics statisticsRecord : statisticsList) {
    								extA1 = extA1 + statisticsRecord.getExtA1();
    								extA2 = CalcUtils.calcAdd(extA2, statisticsRecord.getExtA2());
    								extA3 = extA3 + statisticsRecord.getExtA3();
    								extA4 = CalcUtils.calcAdd(extA4, statisticsRecord.getExtA4());
    								extA5 = extA5 + statisticsRecord.getExtA5();
    								extA6 = CalcUtils.calcAdd(extA6, statisticsRecord.getExtA6());
    								extA7 = extA7 + statisticsRecord.getExtA7();
    								extA8 = extA8 + statisticsRecord.getExtA8();
    								extB1 = extB1 + statisticsRecord.getExtB1();
    								extB2 = CalcUtils.calcAdd(extB2, statisticsRecord.getExtB2());
    								extB3 = extB3 + statisticsRecord.getExtB3();
    								extB4 = CalcUtils.calcAdd(extB4, statisticsRecord.getExtB4());
    								extB5 = extB5 + statisticsRecord.getExtB5();
    								extB6 = CalcUtils.calcAdd(extB6, statisticsRecord.getExtB6());
    								extB7 = extB7 + statisticsRecord.getExtB7();
    								extB8 = extB8 + statisticsRecord.getExtB8();
    								extC11 = extC11 + statisticsRecord.getExtC11();
    								extC12 = CalcUtils.calcAdd(extC12, statisticsRecord.getExtC12());
    								extC13 = extC13 + statisticsRecord.getExtC13();
    								extC14 = CalcUtils.calcAdd(extC14, statisticsRecord.getExtC14());
    								extC15 = extC15 + statisticsRecord.getExtC15();
    								extC16 = extC16 + statisticsRecord.getExtC16();
    								extC21 = extC21 + statisticsRecord.getExtC21();
    								extC22 = CalcUtils.calcAdd(extC22, statisticsRecord.getExtC22()); 
    								extC23 = extC23 + statisticsRecord.getExtC23();
    								extC24 = extC24 + statisticsRecord.getExtC24();
    								extD11 = extD11 + statisticsRecord.getExtD11();
    								extD12 = CalcUtils.calcAdd(extD12, statisticsRecord.getExtD12());
    								extD13 = extD13 + statisticsRecord.getExtD13();
    								extD14 = CalcUtils.calcAdd(extD14, statisticsRecord.getExtD14());
    								extD15 = extD15 + statisticsRecord.getExtD15();
    								extD16 = extD16 + statisticsRecord.getExtD16();
    								extD21 = extD21 + statisticsRecord.getExtD21();
    								extD22 = CalcUtils.calcAdd(extD22, statisticsRecord.getExtD22());
    								extD23 = extD23 + statisticsRecord.getExtD23();
    								extAll1 = extAll1 + statisticsRecord.getExtAll1();
    								extAll2 = CalcUtils.calcAdd(extAll2, statisticsRecord.getExtAll2());
    								extAll3 = extAll3 + statisticsRecord.getExtAll3();
    								extAll4 = CalcUtils.calcAdd(extAll4, statisticsRecord.getExtAll4());
    								extAll5 = extAll5 + statisticsRecord.getExtAll5();
    								extAll6 = CalcUtils.calcAdd(extAll6, statisticsRecord.getExtAll6());
    								extAll7 = extAll7 + statisticsRecord.getExtAll7();
    								extAll8 = CalcUtils.calcAdd(extAll8, statisticsRecord.getExtAll8());
    							}
    							//???????????????30??????????????????===???update
    									ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = statisticsList.get(0);
    							if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "1")) {//=============a
    								if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
    									extA1 = extA1 + 1;
    									extA2 = CalcUtils.calcAdd(extA2, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
    									extA3 = extA3 + 1;
    									extA4 = CalcUtils.calcAdd(extA4, changeRecordSelect.getChangeAmount());
    								}
    								extA5 = extA1 + extA3;
    								extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extA7 = extA7 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extA8 = extA8 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "2")) {//========b
    								if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
    									extB1 = extB1 + 1;
    									extB2 = CalcUtils.calcAdd(extB2, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
    									extB3 = extB3 + 1;
    									extB4 = CalcUtils.calcAdd(extB4, changeRecordSelect.getChangeAmount());
    								}
    								extB5 = extB5 + extB1 + extB3;
    								extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extB7 = extB7 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extB8 = extB8 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "3")) {//=============c1
    								if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
    									extC11 = extC11 + 1;
    									extC12 = CalcUtils.calcAdd(extC12, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
    									extC13 = extC13 + 1;
    									extC14 = CalcUtils.calcAdd(extC14, changeRecordSelect.getChangeAmount());
    								}
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extC15 = extC15 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extC16 = extC16 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "4")) {//=============c2
    								if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
    									extC21 = extC21 + 1;
    									extC22 = CalcUtils.calcAdd(extC22, changeRecordSelect.getChangeAmount());
    								}
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extC23 = extC23 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extC24 = extC24 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "5")) {//=============d1
    								if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
    									extD11 = extD11 + 1;
    									extD12 = CalcUtils.calcAdd(extD12, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
    									extD13 = extD13 + 1;
    									extD14 = CalcUtils.calcAdd(extD14, changeRecordSelect.getChangeAmount());
    								} if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extD15 = extD15 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extD16 = extD16 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "6")) {//=============d2
    								if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
    									extD21 = extD21 + 1;
    									extD22 = CalcUtils.calcAdd(extD22, changeRecordSelect.getChangeAmount());
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
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
    							dbzjTzDesignChangeStatistics.setRemarks(changeRecordSelect.getRemarks());
    							dbzjTzDesignChangeStatistics.setModifyUserInfo(userKey, realName);
    							flag = zjTzDesignChangeStatisticsMapper.updateByPrimaryKey(dbzjTzDesignChangeStatistics);
    						}else {
    							//???????????????30??????????????????====???add
    							ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
    							dbzjTzDesignChangeStatistics.setDesignChangeStatisticsId(UuidUtil.generate());
    							dbzjTzDesignChangeStatistics.setDesignChangeId(changeRecordSelect.getDesignChangeId());
    							dbzjTzDesignChangeStatistics.setProjectId(changeRecordSelect.getProjectId());
    							dbzjTzDesignChangeStatistics.setProjectName(changeRecordSelect.getProjectName());
    							dbzjTzDesignChangeStatistics.setSubprojectInfoId(changeRecordSelect.getSubprojectInfoId());
    							dbzjTzDesignChangeStatistics.setSubprojectName(changeRecordSelect.getSubprojectName());
    							if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "1")) {//=============a
    								if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
    									extA1 = extA1 + 1;
    									extA2 = CalcUtils.calcAdd(extA2, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
    									extA3 = extA3 + 1;
    									extA4 = CalcUtils.calcAdd(extA4, changeRecordSelect.getChangeAmount());
    								}
    								extA5 = extA1 + extA3;
    								extA6 = CalcUtils.calcAdd(extA6, CalcUtils.calcAdd(extA2, extA4));
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extA7 = extA7 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extA8 = extA8 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "2")) {//========b
    								if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "1")) {//??????
    									extB1 = extB1 + 1;
    									extB2 = CalcUtils.calcAdd(extB2, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getChangeNatureId(), "2")) {//??????
    									extB3 = extB3 + 1;
    									extB4 = CalcUtils.calcAdd(extB4, changeRecordSelect.getChangeAmount());
    								}
    								extB5 = extB5 + extB1 + extB3;
    								extB6 = CalcUtils.calcAdd(extB6, CalcUtils.calcAdd(extB2, extB4));
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extB7 = extB7 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extB8 = extB8 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "3")) {//=============c1
    								if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
    									extC11 = extC11 + 1;
    									extC12 = CalcUtils.calcAdd(extC12, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
    									extC13 = extC13 + 1;
    									extC14 = CalcUtils.calcAdd(extC14, changeRecordSelect.getChangeAmount());
    								}
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extC15 = extC15 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extC16 = extC16 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "4")) {//=============c2
    								if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
    									extC21 = extC21 + 1;
    									extC22 = CalcUtils.calcAdd(extC22, changeRecordSelect.getChangeAmount());
    								}
    								if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extC23 = extC23 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extC24 = extC24 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "5")) {//=============d1
    								if(StrUtil.equals(zjTzDesignChangeRecord.getDynamicId(), "0")) {//??????
    									extD11 = extD11 + 1;
    									extD12 = CalcUtils.calcAdd(extD12, changeRecordSelect.getChangeAmount());
    								}else if(StrUtil.equals(changeRecordSelect.getDynamicId(), "1")) {//??????
    									extD13 = extD13 + 1;
    									extD14 = CalcUtils.calcAdd(extD14, changeRecordSelect.getChangeAmount());
    								} if(StrUtil.equals(changeRecordSelect.getInnerCheckId(), "1")) {//??????????????????
    									extD15 = extD15 + 1;
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
    									extD16 = extD16 + 1;
    								}
    							}else if(StrUtil.equals(changeRecordSelect.getChangeLevelId(), "6")) {//=============d2
    								if(StrUtil.equals(changeRecordSelect.getDynamicId(), "0")) {//??????
    									extD21 = extD21 + 1;
    									extD22 = CalcUtils.calcAdd(extD22, changeRecordSelect.getChangeAmount());
    								}
    								if(StrUtil.equals(changeRecordSelect.getLegalId(), "1")) {//??????????????????
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
    							dbzjTzDesignChangeStatistics.setRemarks(changeRecordSelect.getRemarks());
    							dbzjTzDesignChangeStatistics.setReleaseId("0");
    							dbzjTzDesignChangeStatistics.setCreateUserInfo(userKey, realName);
    							flag = zjTzDesignChangeStatisticsMapper.insert(dbzjTzDesignChangeStatistics);
    						}
    						//======================================??????====================================================================
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
    		return repEntity.ok("sys.data.delete",zjTzDesignChangeRecordList);
    	}
    }

	@Override
	public ResponseEntity batchReleaseZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        int flag = 0;
        if (zjTzDesignChangeRecordList != null && zjTzDesignChangeRecordList.size() > 0) {
        	for (ZjTzDesignChangeRecord zjTzRules : zjTzDesignChangeRecordList) {
				if(StrUtil.equals(zjTzRules.getStatusId(), "1")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}else if (StrUtil.equals(zjTzRules.getStatusId(), "3") && StrUtil.equals("4", ext1)) {
					return repEntity.layerMessage("no", "??????????????????????????????!!");
				}
				ZjTzDesignChange zjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzRules.getDesignChangeId());
    			if(zjTzDesignChange != null && StrUtil.isNotEmpty(zjTzDesignChange.getDesignChangeId())) {
    				if (StrUtil.equals("4", ext1)) {
    					zjTzDesignChange.setRenew3("0");
    					zjTzDesignChange.setRenew1("");
    				}else if (StrUtil.equals("3", ext1)) {
    					zjTzDesignChange.setRenew4("0");
    					zjTzDesignChange.setRenew3("");
    					zjTzDesignChange.setRenew2("");
    				}else if (StrUtil.equals("2", ext1)) {
    					zjTzDesignChange.setRenew4("0");
    					zjTzDesignChange.setRenew3("");
    					zjTzDesignChange.setRenew2("");
    				}
    				zjTzDesignChange.setModifyUserInfo(userKey, realName);
    				flag = zjTzDesignChangeMapper.updateByPrimaryKey(zjTzDesignChange);
    			}
    			//?????????????????????????????????
    			ZjTzDesignChangeStatistics statisticsSelect = new ZjTzDesignChangeStatistics();
    			statisticsSelect.setDesignChangeId(zjTzRules.getDesignChangeId());
    			List<ZjTzDesignChangeStatistics> statisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(statisticsSelect);
    			if (statisticsList != null && statisticsList.size() > 0) {
					for (ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics : statisticsList) {
						if (StrUtil.equals("4", ext1)) {
							dbzjTzDesignChangeStatistics.setReleaseId("3");
						}else {
							dbzjTzDesignChangeStatistics.setReleaseId("1");
						}
						zjTzDesignChangeStatisticsMapper.updateByPrimaryKey(dbzjTzDesignChangeStatistics);
					}
				}
        	}
        	
        	ZjTzDesignChangeRecord zjTzRules = new ZjTzDesignChangeRecord();
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setStatusId("3");
        		zjTzRules.setStatusName("??????????????????");
			}else {
				zjTzRules.setStatusId("1");
				zjTzRules.setStatusName("?????????");
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
        	flag = zjTzDesignChangeRecordMapper.batchRecallZjTzDesignChangeRecord(zjTzDesignChangeRecordList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignChangeRecordList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignChangeRecordList != null && zjTzDesignChangeRecordList.size() > 0) {
        	for (ZjTzDesignChangeRecord zjTzRules : zjTzDesignChangeRecordList) {
        		if(StrUtil.equals(zjTzRules.getStatusId(), "0") || StrUtil.equals(zjTzRules.getStatusId(), "2")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
        		}
        		ZjTzDesignChange zjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(zjTzRules.getDesignChangeId());
    			if(zjTzDesignChange != null && StrUtil.isNotEmpty(zjTzDesignChange.getDesignChangeId())) {
    				zjTzDesignChange.setRenew1("0");
    				zjTzDesignChange.setRenew2("0");
    				zjTzDesignChange.setRenew3("");
    				zjTzDesignChange.setRenew4("");
    				zjTzDesignChange.setModifyUserInfo(userKey, realName);
    				flag = zjTzDesignChangeMapper.updateByPrimaryKey(zjTzDesignChange);
    			}
    			//?????????????????????????????????
    			ZjTzDesignChangeStatistics statisticsSelect = new ZjTzDesignChangeStatistics();
    			statisticsSelect.setDesignChangeId(zjTzRules.getDesignChangeId());
    			List<ZjTzDesignChangeStatistics> statisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(statisticsSelect);
    			if (statisticsList != null && statisticsList.size() > 0) {
					for (ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics : statisticsList) {
						dbzjTzDesignChangeStatistics.setReleaseId("2");
						zjTzDesignChangeStatisticsMapper.updateByPrimaryKey(dbzjTzDesignChangeStatistics);
					}
				}
        	}
        	ZjTzDesignChangeRecord zjTzRules = new ZjTzDesignChangeRecord();
        	zjTzRules.setStatusId("2");
        	zjTzRules.setStatusName("?????????");
        	zjTzRules.setRenew1("0");
        	zjTzRules.setRenew2("0");
        	zjTzRules.setRenew3("");
        	zjTzRules.setRenew4("");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignChangeRecordMapper.batchRecallZjTzDesignChangeRecord(zjTzDesignChangeRecordList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignChangeRecordList);
        }
	}

	@Override
	public ResponseEntity checkAndFinishZjTzDesignChangeRecord(ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignChangeRecord dbzjTzDesignChangeRecord = zjTzDesignChangeRecordMapper.selectByPrimaryKey(zjTzDesignChangeRecord.getDesignChangeRecordId());
    	if (dbzjTzDesignChangeRecord != null && StrUtil.isNotEmpty(dbzjTzDesignChangeRecord.getDesignChangeRecordId())) {
    			//???????????????????????????????????????
	    		dbzjTzDesignChangeRecord.setRenew1("");
	    		dbzjTzDesignChangeRecord.setRenew2("");
	    		dbzjTzDesignChangeRecord.setRenew3("");
	    		dbzjTzDesignChangeRecord.setRenew4("");
	    		dbzjTzDesignChangeRecord.setModifyUserInfo(userKey, realName);
    			flag = zjTzDesignChangeRecordMapper.updateByPrimaryKey(dbzjTzDesignChangeRecord);
    			
    			ZjTzDesignChangeRecord record = new ZjTzDesignChangeRecord();
        		record.setDesignChangeId(dbzjTzDesignChangeRecord.getDesignChangeId());;
        		List<ZjTzDesignChangeRecord> records = zjTzDesignChangeRecordMapper.selectByZjTzDesignChangeRecordList(record);
        		
        		int i = 0;
        		if (records != null && records.size() > 0) {
    				for (ZjTzDesignChangeRecord ZjTzDesignChangeRecord2 : records) {
    					if (StrUtil.equals(ZjTzDesignChangeRecord2.getRenew4(), "0")) {
    						i = i + 1;
    					}
    				}
    			}
        		
    			if (i == 0) {
    				//??????????????????
    				ZjTzDesignChange zjTzDesignChange = zjTzDesignChangeMapper.selectByPrimaryKey(dbzjTzDesignChangeRecord.getDesignChangeId());
    				if(zjTzDesignChange != null && StrUtil.isNotEmpty(zjTzDesignChange.getDesignChangeId())) {
    					zjTzDesignChange.setRenew1("");
    					zjTzDesignChange.setRenew2("");
    					zjTzDesignChange.setRenew3("");
    					zjTzDesignChange.setRenew4("");
    					flag = zjTzDesignChangeMapper.updateByPrimaryKey(zjTzDesignChange);
    				}
				}
    	}
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignChangeRecord);
        }
	
	}
}
