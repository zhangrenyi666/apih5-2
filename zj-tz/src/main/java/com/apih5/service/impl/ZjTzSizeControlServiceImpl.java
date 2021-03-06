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
import com.apih5.mybatis.dao.ZjTzContractConditionMapper;
import com.apih5.mybatis.dao.ZjTzContractConditionRecordMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzSizeControlMapper;
import com.apih5.mybatis.dao.ZjTzSizeControlRecordMapper;
import com.apih5.mybatis.pojo.ZjTzContractCondition;
import com.apih5.mybatis.pojo.ZjTzContractConditionRecord;
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSizeControl;
import com.apih5.mybatis.pojo.ZjTzSizeControlRecord;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSizeControlService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSizeControlService")
public class ZjTzSizeControlServiceImpl implements ZjTzSizeControlService {

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
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzSizeControlListByCondition(ZjTzSizeControl zjTzSizeControl) {
    	 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userId = TokenUtils.getUserId(request);
         String ext1 = TokenUtils.getExt1(request);
    	if (zjTzSizeControl == null) {
            zjTzSizeControl = new ZjTzSizeControl();
        }
        // ????????????
        PageHelper.startPage(zjTzSizeControl.getPage(),zjTzSizeControl.getLimit());
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzSizeControl.getProjectId(), true)) {
            	zjTzSizeControl.setProjectId("");
            	zjTzSizeControl.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzSizeControl.getProjectId(), true)) {
            	zjTzSizeControl.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzSizeControl> zjTzSizeControlList = zjTzSizeControlMapper.selectByZjTzSizeControlList(zjTzSizeControl);
        for (ZjTzSizeControl sizeControl : zjTzSizeControlList) {
        	 ZjTzFile zjTzFileSelect = new ZjTzFile();
        	 zjTzFileSelect.setOtherId(sizeControl.getSizeControlId());
        	 zjTzFileSelect.setOtherType("1");
             List<ZjTzFile> schemeFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             sizeControl.setSchemeFileList(schemeFileList);
        	 zjTzFileSelect.setOtherType("2");
             List<ZjTzFile> thirdReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             sizeControl.setThirdReplyFileList(thirdReplyFileList);
        	 zjTzFileSelect.setOtherType("3");
             List<ZjTzFile> localGovFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             sizeControl.setLocalGovFileList(localGovFileList);
        	 zjTzFileSelect.setOtherType("4");
             List<ZjTzFile> juFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             sizeControl.setJuFileList(juFileList);
        	 zjTzFileSelect.setOtherType("5");
             List<ZjTzFile> chinaFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             sizeControl.setChinaFileList(chinaFileList);
             // 
             ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(sizeControl.getContractConditionId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             sizeControl.setZjTzFileList(zjTzFileList);
             
             BigDecimal amount1 = new BigDecimal(0);
             BigDecimal amount2 = new BigDecimal(0);
             BigDecimal zeroAmount1 = new BigDecimal(0);
             BigDecimal zeroAmount2 = new BigDecimal(0);
             BigDecimal lastAmount1 = new BigDecimal(0);
             BigDecimal lastAmount2 = new BigDecimal(0);
             BigDecimal zeroAmount3 = new BigDecimal(0);
             String zeroChangeName = "";
             String ChangeName = "";
             String lastChangeName = "";
             int count1 = 0;
             int count2 = 0;
             int count3 = 0;
             int count4 = 0;
             ZjTzSizeControlRecord zjTzSizeControlRecord = new ZjTzSizeControlRecord();
             zjTzSizeControlRecord.setSizeControlId(sizeControl.getSizeControlId());
             List<ZjTzSizeControlRecord> records = zjTzSizeControlRecordMapper.selectByZjTzSizeControlRecordList(zjTzSizeControlRecord);
             if (records != null && records.size() > 0) {
				for (ZjTzSizeControlRecord zjTzSizeControlRecord2 : records) {
					if (records.size() == 1) {
						if (!StrUtil.equals(zjTzSizeControlRecord2.getStatusId(), "1")) {
							zeroAmount1 = new BigDecimal(0);
							zeroAmount2 = new BigDecimal(0);
							amount1 = new BigDecimal(0);
							amount2 = new BigDecimal(0);
							lastAmount1 = new BigDecimal(0);
							lastAmount2 = new BigDecimal(0);
							zeroAmount3 = new BigDecimal(0);
							zeroChangeName = "";
				            ChangeName = "";
				            lastChangeName = "";
						}else {
							zeroAmount1 = zjTzSizeControlRecord2.getAmount1();
							zeroAmount2 = zjTzSizeControlRecord2.getAmount2();
							amount1 = zjTzSizeControlRecord2.getAmount1();
							amount2 = zjTzSizeControlRecord2.getAmount2();
							lastAmount1 = zjTzSizeControlRecord2.getAmount1();
							lastAmount2 = zjTzSizeControlRecord2.getAmount2();
							zeroAmount3 = zjTzSizeControlRecord2.getAmount3();
							zeroChangeName = zjTzSizeControlRecord2.getChangePropertyName();
				            ChangeName = zjTzSizeControlRecord2.getChangePropertyName();
				            lastChangeName = zjTzSizeControlRecord2.getChangePropertyName();
						}
					}else if (records.size() == 2) {
						if (!StrUtil.equals(records.get(0).getStatusId(), "1")) {
							zeroAmount1 = records.get(1).getAmount1();
							zeroAmount2 = records.get(1).getAmount2();
							amount1 = records.get(1).getAmount1();
							amount2 = records.get(1).getAmount2();
							lastAmount1 = records.get(1).getAmount1();
							lastAmount2 = records.get(1).getAmount2();
							zeroAmount3 = records.get(1).getAmount3();
							zeroChangeName = records.get(1).getChangePropertyName();
				            ChangeName = records.get(1).getChangePropertyName();
				            lastChangeName = records.get(1).getChangePropertyName();
						}else {
							zeroAmount1 = records.get(1).getAmount1();
							zeroAmount2 = records.get(1).getAmount2();
							amount1 = records.get(0).getAmount1();
							amount2 = records.get(0).getAmount2();
							lastAmount1 = records.get(1).getAmount1();
							lastAmount2 = records.get(1).getAmount2();
							zeroAmount3 = records.get(1).getAmount3();
							zeroChangeName = records.get(1).getChangePropertyName();
				            ChangeName = records.get(0).getChangePropertyName();
				            lastChangeName = records.get(1).getChangePropertyName();
						}
					}else if (records.size() == 3) {
						if (!StrUtil.equals(records.get(0).getStatusId(), "1")) {
							zeroAmount1 = records.get(2).getAmount1();
							zeroAmount2 = records.get(2).getAmount2();
							amount1 = records.get(1).getAmount1();
							amount2 = records.get(1).getAmount2();
							lastAmount1 = records.get(2).getAmount1();
							lastAmount2 = records.get(2).getAmount2();
							zeroAmount3 = records.get(2).getAmount3();
							zeroChangeName = records.get(2).getChangePropertyName();
				            ChangeName = records.get(1).getChangePropertyName();
				            lastChangeName = records.get(2).getChangePropertyName();
						}else {
							zeroAmount1 = records.get(2).getAmount1();
							zeroAmount2 = records.get(2).getAmount2();
							amount1 = records.get(0).getAmount1();
							amount2 = records.get(0).getAmount2();
							lastAmount1 = records.get(1).getAmount1();
							lastAmount2 = records.get(1).getAmount2();
							zeroAmount3 = records.get(2).getAmount3();
							zeroChangeName = records.get(2).getChangePropertyName();
				            ChangeName = records.get(0).getChangePropertyName();
				            lastChangeName = records.get(1).getChangePropertyName();
						}
					}else if (records.size() >= 4) {
						if (!StrUtil.equals(records.get(0).getStatusId(), "1")) {
							zeroAmount1 = records.get(records.size()-1).getAmount1();
							zeroAmount2 = records.get(records.size()-1).getAmount2();
							amount1 = records.get(1).getAmount1();
							amount2 = records.get(1).getAmount2();
							lastAmount1 = records.get(2).getAmount1();
							lastAmount2 = records.get(2).getAmount2();
							zeroAmount3 = records.get(records.size()-1).getAmount3();
							zeroChangeName = records.get(records.size()-1).getChangePropertyName();
				            ChangeName = records.get(1).getChangePropertyName();
				            lastChangeName = records.get(2).getChangePropertyName();
						}else {
							zeroAmount1 = records.get(records.size()-1).getAmount1();
							zeroAmount2 = records.get(records.size()-1).getAmount2();
							amount1 = records.get(0).getAmount1();
							amount2 = records.get(0).getAmount2();
							lastAmount1 = records.get(1).getAmount1();
							lastAmount2 = records.get(1).getAmount2();
							zeroAmount3 = records.get(records.size()-1).getAmount3();
							zeroChangeName = records.get(records.size()-1).getChangePropertyName();
				            ChangeName = records.get(0).getChangePropertyName();
				            lastChangeName = records.get(1).getChangePropertyName();
						}
					}
					if (StrUtil.equals(zjTzSizeControlRecord2.getRenew1(), "0")) {
						count1 = count1 + 1;
					}else if (StrUtil.equals(zjTzSizeControlRecord2.getRenew2(), "0")) {
						count2 = count2 + 1;
					}else if (StrUtil.equals(zjTzSizeControlRecord2.getRenew3(), "0")) {
						count3 = count3 + 1;
					}else if (StrUtil.equals(zjTzSizeControlRecord2.getRenew4(), "0")) {
						count4 = count4 + 1;
					}
				}
			}
             if (count1 >= 1) {
            	 sizeControl.setRenew1("0");
             }else if (count2 >= 1) {
            	 sizeControl.setRenew2("0");
             }else if (count3 >= 1) {
            	 sizeControl.setRenew3("0");
             }else if (count4 >= 1) {
            	 sizeControl.setRenew4("0");
             }
	        sizeControl.setZeroAmount1(zeroAmount1);
	        sizeControl.setZeroAmount2(zeroAmount2);
	        sizeControl.setAmount1(amount1);
	        sizeControl.setAmount2(amount2);
	        sizeControl.setLastAmount1(lastAmount1);
	        sizeControl.setLastAmount2(lastAmount2);
	        sizeControl.setZeroAmount3(zeroAmount3);
	        sizeControl.setZeroChangePropertyName(zeroChangeName);
	        sizeControl.setChangePropertyName(ChangeName);
	        sizeControl.setLastChangePropertyName(lastChangeName);
			}
             //??????????????????
        // ??????????????????
        PageInfo<ZjTzSizeControl> p = new PageInfo<>(zjTzSizeControlList);

        return repEntity.okList(zjTzSizeControlList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSizeControlDetails(ZjTzSizeControl zjTzSizeControl) {
        if (zjTzSizeControl == null) {
            zjTzSizeControl = new ZjTzSizeControl();
        }
        // ????????????
        ZjTzSizeControl dbZjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControl.getSizeControlId());
        // ????????????
        if (dbZjTzSizeControl != null) {
        	 ZjTzFile zjTzFileSelect = new ZjTzFile();
        	 zjTzFileSelect.setOtherId(dbZjTzSizeControl.getSizeControlId());
        	 zjTzFileSelect.setOtherType("1");
             List<ZjTzFile> schemeFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzSizeControl.setSchemeFileList(schemeFileList);
        	 zjTzFileSelect.setOtherType("2");
             List<ZjTzFile> thirdReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzSizeControl.setThirdReplyFileList(thirdReplyFileList);
        	 zjTzFileSelect.setOtherType("3");
             List<ZjTzFile> localGovFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzSizeControl.setLocalGovFileList(localGovFileList);
        	 zjTzFileSelect.setOtherType("4");
             List<ZjTzFile> juFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzSizeControl.setJuFileList(juFileList);
        	 zjTzFileSelect.setOtherType("5");
             List<ZjTzFile> chinaFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzSizeControl.setChinaFileList(chinaFileList);
             //
             ZjTzFile zjTzFileContractConditionSelect = new ZjTzFile();
             zjTzFileContractConditionSelect.setOtherId(dbZjTzSizeControl.getContractConditionId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileContractConditionSelect);
             dbZjTzSizeControl.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzSizeControl);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzSizeControl(ZjTzSizeControl zjTzSizeControl) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	//????????????id????????????id   ????????????
    	if(StrUtil.isNotEmpty(zjTzSizeControl.getSubprojectInfoId())) {
    		ZjTzSizeControl zjTzSizeControlSelect = new ZjTzSizeControl();
    		zjTzSizeControlSelect.setProjectId(zjTzSizeControl.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId(zjTzSizeControl.getSubprojectInfoId());
    		List<ZjTzSizeControl> sizeControls = zjTzSizeControlMapper.selectByZjTzSizeControlList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		//???????????????
    		ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControl.getSubprojectInfoId());
    		if(info != null) {
    			zjTzSizeControl.setSubprojectName(info.getSubprojectName());
    		}
    	}else {
    		//????????????????????????  = ?????????id??????
    		ZjTzSizeControl zjTzSizeControlSelect = new ZjTzSizeControl();
    		zjTzSizeControlSelect.setProjectId(zjTzSizeControl.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId("???");
    		List<ZjTzSizeControl> sizeControls = zjTzSizeControlMapper.selectByZjTzSizeControlList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		zjTzSizeControl.setSubprojectInfoId("???");
    	}
    	zjTzSizeControl.setSizeControlId(UuidUtil.generate());
    	zjTzSizeControl.setChangeNumber(0);
    	//????????????
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getChangePropertyId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControl.getChangePropertyId());
    		zjTzSizeControl.setChangePropertyName(openBankName);
    		//0=????????????
    		zjTzSizeControl.setZeroChangePropertyId(zjTzSizeControl.getChangePropertyId());
    		zjTzSizeControl.setZeroChangePropertyName(zjTzSizeControl.getChangePropertyName());
//    		//last=????????????
//    		zjTzSizeControl.setLastChangePropertyId(zjTzSizeControl.getChangePropertyId());
//    		zjTzSizeControl.setLastChangePropertyName(zjTzSizeControl.getChangePropertyName());
    	}
    	//0=????????????(???)????????????(???)???????????????(???)
    	zjTzSizeControl.setZeroAmount1(zjTzSizeControl.getAmount1());
    	zjTzSizeControl.setZeroAmount2(zjTzSizeControl.getAmount2());
    	zjTzSizeControl.setZeroAmount3(zjTzSizeControl.getAmount3());
//    	//last=????????????(???)????????????(???)???????????????(???)
//    	zjTzSizeControl.setLastAmount1(zjTzSizeControl.getAmount1());
//    	zjTzSizeControl.setLastAmount2(zjTzSizeControl.getAmount2());
//    	zjTzSizeControl.setLastAmount3(zjTzSizeControl.getAmount3());
    	//1
    	if (StrUtil.equals(zjTzSizeControl.getSecondNegotiateId(), "1")) {
    		zjTzSizeControl.setSecondNegotiateName("???");
    	}else {
    		zjTzSizeControl.setSecondNegotiateName("???");
    	}
    	//2
    	if (StrUtil.equals(zjTzSizeControl.getThirdReplyId(), "1")) {
    		zjTzSizeControl.setThirdReplyName("???");
    	}else {
    		zjTzSizeControl.setThirdReplyName("???");
    	}
    	//3
    	if (StrUtil.equals(zjTzSizeControl.getLocalGovId(), "1")) {
    		zjTzSizeControl.setLocalGovName("???");
    	}else {
    		zjTzSizeControl.setLocalGovName("???");
    	}
    	//4
    	if (StrUtil.equals(zjTzSizeControl.getJuId(), "1")) {
    		zjTzSizeControl.setJuName("???");
    	}else {
    		zjTzSizeControl.setJuName("???");
    	}
    	//5
    	if (StrUtil.equals(zjTzSizeControl.getChinaId(), "1")) {
    		zjTzSizeControl.setChinaName("???");
    	}else {
    		zjTzSizeControl.setChinaName("???");
    	}
    	zjTzSizeControl.setAddFlag("false");
    	zjTzSizeControl.setCreateUserInfo(userKey, realName);
    	int flag = zjTzSizeControlMapper.insert(zjTzSizeControl);
    	//??????1
    	List<ZjTzFile> schemeFileList = zjTzSizeControl.getSchemeFileList();
    	if(schemeFileList != null && schemeFileList.size()>0) {
    		for(ZjTzFile zjTzFile:schemeFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
    			zjTzFile.setOtherType("1");
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//??????2
    	List<ZjTzFile> thirdReplyFileList = zjTzSizeControl.getThirdReplyFileList();
    	if(thirdReplyFileList != null && thirdReplyFileList.size()>0) {
    		for(ZjTzFile zjTzFile:thirdReplyFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
    			zjTzFile.setOtherType("2");
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//??????3
    	List<ZjTzFile> localGovFileList = zjTzSizeControl.getLocalGovFileList();
    	if(localGovFileList != null && localGovFileList.size()>0) {
    		for(ZjTzFile zjTzFile:localGovFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
    			zjTzFile.setOtherType("3");
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//??????4
    	List<ZjTzFile> juFileList = zjTzSizeControl.getJuFileList();
    	if(juFileList != null && juFileList.size()>0) {
    		for(ZjTzFile zjTzFile:juFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
    			zjTzFile.setOtherType("4");
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//??????5
    	List<ZjTzFile> chinaFileList = zjTzSizeControl.getChinaFileList();
    	if(chinaFileList != null && chinaFileList.size()>0) {
    		for(ZjTzFile zjTzFile:chinaFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
    			zjTzFile.setOtherType("5");
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//add????????????
    	ZjTzContractCondition dbzjTzContractCondition = new ZjTzContractCondition();
    	dbzjTzContractCondition.setContractConditionId(UuidUtil.generate());
    	// ??????????????????id
    	dbzjTzContractCondition.setSizeControlId(zjTzSizeControl.getSizeControlId());
    	// ??????id
    	dbzjTzContractCondition.setProjectId(zjTzSizeControl.getProjectId());
    	// ????????????
    	dbzjTzContractCondition.setRegisterDate(zjTzSizeControl.getRegisterDate1());
    	// ?????????
    	dbzjTzContractCondition.setRegistrant(zjTzSizeControl.getRegistrant1());
    	// ??????????????????id
    	dbzjTzContractCondition.setInvestId(zjTzSizeControl.getInvestId());
    	// ??????????????????name
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getInvestId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControl.getInvestId());
    		dbzjTzContractCondition.setInvestName(openBankName);
    	}
    	// ?????????????????????
    	dbzjTzContractCondition.setJuShare(zjTzSizeControl.getJuShare());
    	// ??????????????????????????????id
    	dbzjTzContractCondition.setJuId(zjTzSizeControl.getJuId1());
    	// ??????????????????????????????name
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getJuId1())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControl.getJuId1());
    		dbzjTzContractCondition.setJuName(openBankName);
    	}
    	// ?????????????????????id
    	dbzjTzContractCondition.setZcbId(zjTzSizeControl.getZcbId());
    	// ?????????????????????name
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getZcbId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControl.getZcbId());
    		dbzjTzContractCondition.setZcbName(openBankName);
    	}
    	// ?????????????????????
    	dbzjTzContractCondition.setZcbShare(zjTzSizeControl.getZcbShare());
    	// ??????????????????
    	dbzjTzContractCondition.setExt1(zjTzSizeControl.getExt1());
    	// ????????????????????????????????????
    	dbzjTzContractCondition.setExt2(zjTzSizeControl.getExt2());
    	// ????????????????????????
    	dbzjTzContractCondition.setExt3(zjTzSizeControl.getExt3());
    	dbzjTzContractCondition.setCreateUserInfo(userKey, realName);
    	List<ZjTzFile> ZjTzFileList = zjTzSizeControl.getZjTzFileList();
    	if(ZjTzFileList != null && ZjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:ZjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(dbzjTzContractCondition.getContractConditionId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	flag = zjTzContractConditionMapper.insert(dbzjTzContractCondition);
    	//??????add?????????????????????????????????????????????
    	ZjTzSizeControlRecord zjTzSizeControlRecord = new ZjTzSizeControlRecord();
    	zjTzSizeControlRecord.setSizeControlRecordId(UuidUtil.generate());
        // ??????id
        zjTzSizeControlRecord.setSizeControlId(zjTzSizeControl.getSizeControlId());
        // ??????id
        zjTzSizeControlRecord.setProjectId(zjTzSizeControl.getProjectId());
        //?????????
        zjTzSizeControlRecord.setProjectName(zjTzSizeControl.getProjectName());
        // ?????????id
        zjTzSizeControlRecord.setSubprojectInfoId(zjTzSizeControl.getSubprojectInfoId());
        // ?????????name
        zjTzSizeControlRecord.setSubprojectName(zjTzSizeControl.getSubprojectName());
        // ????????????
        zjTzSizeControlRecord.setChangeNumber(zjTzSizeControl.getChangeNumber());
        // ????????????id
        zjTzSizeControlRecord.setChangePropertyId(zjTzSizeControl.getChangePropertyId());
        // ????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getChangePropertyId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControl.getChangePropertyId());
        	zjTzSizeControlRecord.setChangePropertyName(openBankName);
        }
        ZjTzProManage proManage =  zjTzProManageMapper.selectByPrimaryKey(zjTzSizeControl.getProjectId());
    	if(proManage != null && StrUtil.isNotEmpty(proManage.getProjectId())) {
    		if(proManage.getAmount1() != null) {
    			zjTzSizeControlRecord.setDvalue1(CalcUtils.calcSubtract(zjTzSizeControl.getAmount1(), proManage.getAmount1()));
    		}else {
    			zjTzSizeControlRecord.setDvalue1(zjTzSizeControl.getAmount1());
    		}
    		if(proManage.getAmount3() != null) {
    			zjTzSizeControlRecord.setDvalue2(CalcUtils.calcSubtract(zjTzSizeControl.getAmount2(), proManage.getAmount3()));
    		}else {
    			zjTzSizeControlRecord.setDvalue2(zjTzSizeControl.getAmount2());
    		}
    	}
        // ????????????(???)
        zjTzSizeControlRecord.setAmount1(zjTzSizeControl.getAmount1());
        // ?????????(???)
        zjTzSizeControlRecord.setAmount2(zjTzSizeControl.getAmount2());
        // ????????????(???)
        zjTzSizeControlRecord.setAmount3(zjTzSizeControl.getAmount3());
        // ??????????????????id
        zjTzSizeControlRecord.setSecondNegotiateId(zjTzSizeControl.getSecondNegotiateId());
        // ??????????????????name
        if (StrUtil.equals(zjTzSizeControl.getSecondNegotiateId(), "1")) {
        	zjTzSizeControlRecord.setSecondNegotiateName("???");
        }else {
        	zjTzSizeControlRecord.setSecondNegotiateName("???");
        }
        // ??????????????????
        zjTzSizeControlRecord.setScheme(zjTzSizeControl.getScheme());
        // ????????????id
        zjTzSizeControlRecord.setThirdReplyId(zjTzSizeControl.getThirdReplyId());
        // ????????????name
        if (StrUtil.equals(zjTzSizeControl.getThirdReplyId(), "1")) {
        	zjTzSizeControlRecord.setThirdReplyName("???");
        }else {
        	zjTzSizeControlRecord.setThirdReplyName("???");
        }
        // ??????????????????id
        zjTzSizeControlRecord.setLocalGovId(zjTzSizeControl.getLocalGovId());
        // ??????????????????name
        if (StrUtil.equals(zjTzSizeControl.getLocalGovId(), "1")) {
        	zjTzSizeControlRecord.setLocalGovName("???");
        }else {
        	zjTzSizeControlRecord.setLocalGovName("???");
        }
        // ?????????????????????id
        zjTzSizeControlRecord.setJuId(zjTzSizeControl.getJuId());
        // ?????????????????????name
        if (StrUtil.equals(zjTzSizeControl.getJuId(), "1")) {
        	zjTzSizeControlRecord.setJuName("???");
        }else {
        	zjTzSizeControlRecord.setJuName("???");
        }
        // ??????????????????id
        zjTzSizeControlRecord.setChinaId(zjTzSizeControl.getChinaId());
        // ??????????????????name
        if (StrUtil.equals(zjTzSizeControl.getChinaId(), "1")) {
        	zjTzSizeControlRecord.setChinaName("???");
        }else {
        	zjTzSizeControlRecord.setChinaName("???");
        }
        // ????????????
        zjTzSizeControlRecord.setRegisterDate(zjTzSizeControl.getRegisterDate());
        // ?????????
        zjTzSizeControlRecord.setRegistrant(zjTzSizeControl.getRegistrant());
        //??????
        zjTzSizeControlRecord.setRemarks(zjTzSizeControl.getRemarks());
        //??????id
        zjTzSizeControlRecord.setStatusId("0");
        zjTzSizeControlRecord.setStatusName("?????????");
        zjTzSizeControlRecord.setCreateUserInfo(userKey, realName);
        flag = zjTzSizeControlRecordMapper.insert(zjTzSizeControlRecord);
        //??????1
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
        if(chinaFileList != null && chinaFileList.size()>0) {
            for(ZjTzFile zjTzFile:chinaFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("5");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
    	
    	//add????????????
    	ZjTzContractConditionRecord dbzjTzContractConditionRecord = new ZjTzContractConditionRecord();
    	dbzjTzContractConditionRecord.setContractConditionRecordId(UuidUtil.generate());
    	// ????????????????????????id
    	dbzjTzContractConditionRecord.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
        // ??????????????????id
    	dbzjTzContractConditionRecord.setSizeControlId(zjTzSizeControl.getSizeControlId());
        // ??????id
    	dbzjTzContractConditionRecord.setProjectId(zjTzSizeControl.getProjectId());
        // ????????????
    	dbzjTzContractConditionRecord.setRegisterDate(zjTzSizeControl.getRegisterDate1());
        // ?????????
    	dbzjTzContractConditionRecord.setRegistrant(zjTzSizeControl.getRegistrant1());
        // ??????????????????id
    	dbzjTzContractConditionRecord.setInvestId(zjTzSizeControl.getInvestId());
        // ??????????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getInvestId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControl.getInvestId());
     	  dbzjTzContractConditionRecord.setInvestName(openBankName);
        }
        // ?????????????????????
        dbzjTzContractConditionRecord.setJuShare(zjTzSizeControl.getJuShare());
        // ??????????????????????????????id
        dbzjTzContractConditionRecord.setJuId(zjTzSizeControl.getJuId1());
        // ??????????????????????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getJuId1())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControl.getJuId1());
     	  dbzjTzContractConditionRecord.setJuName(openBankName);
        }
        // ?????????????????????id
        dbzjTzContractConditionRecord.setZcbId(zjTzSizeControl.getZcbId());
        // ?????????????????????name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getZcbId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControl.getZcbId());
     	  dbzjTzContractConditionRecord.setZcbName(openBankName);
        }
        // ?????????????????????
        dbzjTzContractConditionRecord.setZcbShare(zjTzSizeControl.getZcbShare());
        // ??????????????????
        dbzjTzContractConditionRecord.setExt1(zjTzSizeControl.getExt1());
        // ????????????????????????????????????
        dbzjTzContractConditionRecord.setExt2(zjTzSizeControl.getExt2());
        // ????????????????????????
        dbzjTzContractConditionRecord.setExt3(zjTzSizeControl.getExt3());
        dbzjTzContractConditionRecord.setCreateUserInfo(userKey, realName);
        if(ZjTzFileList != null && ZjTzFileList.size()>0) {
     	   for(ZjTzFile zjTzFile:ZjTzFileList) {
     		   zjTzFile.setUid(UuidUtil.generate());
     		   zjTzFile.setOtherId(dbzjTzContractConditionRecord.getContractConditionRecordId());
     		   zjTzFile.setCreateUserInfo(userKey, realName);
     		   zjTzFileMapper.insert(zjTzFile);
     	   }
        }
        flag = zjTzContractConditionRecordMapper.insert(dbzjTzContractConditionRecord);
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zjTzSizeControl);
    	}
    }

    @Override
    public ResponseEntity updateZjTzSizeControl (ZjTzSizeControl zjTzSizeControl) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSizeControl dbzjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControl.getSizeControlId());
        if (dbzjTzSizeControl != null && StrUtil.isNotEmpty(dbzjTzSizeControl.getSizeControlId())) {
           // ??????id
           dbzjTzSizeControl.setProjectId(zjTzSizeControl.getProjectId());
           // ?????????id
           dbzjTzSizeControl.setSubprojectInfoId(zjTzSizeControl.getSubprojectInfoId());
           // ?????????name
           dbzjTzSizeControl.setSubprojectName(zjTzSizeControl.getSubprojectName());
           // ????????????
//           dbzjTzSizeControl.setChangeNumber(zjTzSizeControl.getChangeNumber());
           // ????????????id
           dbzjTzSizeControl.setChangePropertyId(zjTzSizeControl.getChangePropertyId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzSizeControl.getChangePropertyId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControl.getChangePropertyId());
           	dbzjTzSizeControl.setChangePropertyName(openBankName);
           }
           // ????????????(???)
           dbzjTzSizeControl.setAmount1(zjTzSizeControl.getAmount1());
           // ?????????(???)
           dbzjTzSizeControl.setAmount2(zjTzSizeControl.getAmount2());
           // ????????????(???)
           dbzjTzSizeControl.setAmount3(zjTzSizeControl.getAmount3());
           // ??????????????????id
           dbzjTzSizeControl.setSecondNegotiateId(zjTzSizeControl.getSecondNegotiateId());
           // ??????????????????name
           if (StrUtil.equals(zjTzSizeControl.getSecondNegotiateId(), "1")) {
        	   dbzjTzSizeControl.setSecondNegotiateName("???");
           }else {
        	   dbzjTzSizeControl.setSecondNegotiateName("???");
           }
           // ??????????????????
           dbzjTzSizeControl.setScheme(zjTzSizeControl.getScheme());
           // ????????????id
           dbzjTzSizeControl.setThirdReplyId(zjTzSizeControl.getThirdReplyId());
           // ????????????name
           if (StrUtil.equals(zjTzSizeControl.getThirdReplyId(), "1")) {
        	   dbzjTzSizeControl.setThirdReplyName("???");
           }else {
        	   dbzjTzSizeControl.setThirdReplyName("???");
           }
           // ??????????????????id
           dbzjTzSizeControl.setLocalGovId(zjTzSizeControl.getLocalGovId());
           // ??????????????????name
           if (StrUtil.equals(zjTzSizeControl.getLocalGovId(), "1")) {
        	   dbzjTzSizeControl.setLocalGovName("???");
           }else {
        	   dbzjTzSizeControl.setLocalGovName("???");
           }
           // ?????????????????????id
           dbzjTzSizeControl.setJuId(zjTzSizeControl.getJuId());
           // ?????????????????????name
           if (StrUtil.equals(zjTzSizeControl.getJuId(), "1")) {
        	   dbzjTzSizeControl.setJuName("???");
           }else {
        	   dbzjTzSizeControl.setJuName("???");
           }
           // ??????????????????id
           dbzjTzSizeControl.setChinaId(zjTzSizeControl.getChinaId());
           // ??????????????????name
           if (StrUtil.equals(zjTzSizeControl.getChinaId(), "1")) {
        	   dbzjTzSizeControl.setChinaName("???");
           }else {
        	   dbzjTzSizeControl.setChinaName("???");
           }
           // ????????????
           dbzjTzSizeControl.setRegisterDate(zjTzSizeControl.getRegisterDate());
           // ?????????
           dbzjTzSizeControl.setRegistrant(zjTzSizeControl.getRegistrant());
           //??????
           dbzjTzSizeControl.setRemarks(zjTzSizeControl.getRemarks());
           // ??????
           dbzjTzSizeControl.setModifyUserInfo(userKey, realName);
           // ????????????
//           dbzjTzSizeControl.setRenew(zjTzSizeControl.getRenew());
           // ??????????????????
           dbzjTzSizeControl.setSizeControlSubject(zjTzSizeControl.getSizeControlSubject());
           flag = zjTzSizeControlMapper.updateByPrimaryKey(dbzjTzSizeControl);
           
           
           
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzSizeControl.getSizeControlId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           //??????1
           List<ZjTzFile> schemeFileList = zjTzSizeControl.getSchemeFileList();
           if(schemeFileList != null && schemeFileList.size()>0) {
               for(ZjTzFile zjTzFile:schemeFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
                   zjTzFile.setOtherType("1");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????2
           List<ZjTzFile> thirdReplyFileList = zjTzSizeControl.getThirdReplyFileList();
           if(thirdReplyFileList != null && thirdReplyFileList.size()>0) {
               for(ZjTzFile zjTzFile:thirdReplyFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
                   zjTzFile.setOtherType("2");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????3
           List<ZjTzFile> localGovFileList = zjTzSizeControl.getLocalGovFileList();
           if(localGovFileList != null && localGovFileList.size()>0) {
               for(ZjTzFile zjTzFile:localGovFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
                   zjTzFile.setOtherType("3");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????4
           List<ZjTzFile> juFileList = zjTzSizeControl.getJuFileList();
           if(juFileList != null && juFileList.size()>0) {
               for(ZjTzFile zjTzFile:juFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
                   zjTzFile.setOtherType("4");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //??????5
           List<ZjTzFile> chinaFileList = zjTzSizeControl.getChinaFileList();
           if(chinaFileList != null && chinaFileList.size()>0) {
               for(ZjTzFile zjTzFile:chinaFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzSizeControl.getSizeControlId());
                   zjTzFile.setOtherType("5");
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
            return repEntity.ok("sys.data.update",zjTzSizeControl);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSizeControl(List<ZjTzSizeControl> zjTzSizeControlList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSizeControlList != null && zjTzSizeControlList.size() > 0) {
           ZjTzSizeControl zjTzSizeControl = new ZjTzSizeControl();
           zjTzSizeControl.setModifyUserInfo(userKey, realName);
           flag = zjTzSizeControlMapper.batchDeleteUpdateZjTzSizeControl(zjTzSizeControlList, zjTzSizeControl);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSizeControlList);
        }
    }
}
