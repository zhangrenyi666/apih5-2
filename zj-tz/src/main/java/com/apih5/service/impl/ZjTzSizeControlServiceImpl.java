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
        // 分页查询
        PageHelper.startPage(zjTzSizeControl.getPage(),zjTzSizeControl.getLimit());
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSizeControl.getProjectId(), true)) {
            	zjTzSizeControl.setProjectId("");
            	zjTzSizeControl.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSizeControl.getProjectId(), true)) {
            	zjTzSizeControl.setProjectId("");
            }
        }
        // 获取数据
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
             //本次投资规模
        // 得到分页信息
        PageInfo<ZjTzSizeControl> p = new PageInfo<>(zjTzSizeControlList);

        return repEntity.okList(zjTzSizeControlList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSizeControlDetails(ZjTzSizeControl zjTzSizeControl) {
        if (zjTzSizeControl == null) {
            zjTzSizeControl = new ZjTzSizeControl();
        }
        // 获取数据
        ZjTzSizeControl dbZjTzSizeControl = zjTzSizeControlMapper.selectByPrimaryKey(zjTzSizeControl.getSizeControlId());
        // 数据存在
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
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSizeControl(ZjTzSizeControl zjTzSizeControl) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	//一、项目id和子项目id   共同判断
    	if(StrUtil.isNotEmpty(zjTzSizeControl.getSubprojectInfoId())) {
    		ZjTzSizeControl zjTzSizeControlSelect = new ZjTzSizeControl();
    		zjTzSizeControlSelect.setProjectId(zjTzSizeControl.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId(zjTzSizeControl.getSubprojectInfoId());
    		List<ZjTzSizeControl> sizeControls = zjTzSizeControlMapper.selectByZjTzSizeControlList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
    		}
    		//子项目名称
    		ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSizeControl.getSubprojectInfoId());
    		if(info != null) {
    			zjTzSizeControl.setSubprojectName(info.getSubprojectName());
    		}
    	}else {
    		//二、不填子项目的  = 用项目id判断
    		ZjTzSizeControl zjTzSizeControlSelect = new ZjTzSizeControl();
    		zjTzSizeControlSelect.setProjectId(zjTzSizeControl.getProjectId());
    		zjTzSizeControlSelect.setSubprojectInfoId("无");
    		List<ZjTzSizeControl> sizeControls = zjTzSizeControlMapper.selectByZjTzSizeControlList(zjTzSizeControlSelect);
    		if(sizeControls != null && sizeControls.size() >0) {
    			return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
    		}
    		zjTzSizeControl.setSubprojectInfoId("无");
    	}
    	zjTzSizeControl.setSizeControlId(UuidUtil.generate());
    	zjTzSizeControl.setChangeNumber(0);
    	//变动属性
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getChangePropertyId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControl.getChangePropertyId());
    		zjTzSizeControl.setChangePropertyName(openBankName);
    		//0=变动属性
    		zjTzSizeControl.setZeroChangePropertyId(zjTzSizeControl.getChangePropertyId());
    		zjTzSizeControl.setZeroChangePropertyName(zjTzSizeControl.getChangePropertyName());
//    		//last=变动属性
//    		zjTzSizeControl.setLastChangePropertyId(zjTzSizeControl.getChangePropertyId());
//    		zjTzSizeControl.setLastChangePropertyName(zjTzSizeControl.getChangePropertyName());
    	}
    	//0=投资规模(元)、建安费(元)、查缺补漏(元)
    	zjTzSizeControl.setZeroAmount1(zjTzSizeControl.getAmount1());
    	zjTzSizeControl.setZeroAmount2(zjTzSizeControl.getAmount2());
    	zjTzSizeControl.setZeroAmount3(zjTzSizeControl.getAmount3());
//    	//last=投资规模(元)、建安费(元)、查缺补漏(元)
//    	zjTzSizeControl.setLastAmount1(zjTzSizeControl.getAmount1());
//    	zjTzSizeControl.setLastAmount2(zjTzSizeControl.getAmount2());
//    	zjTzSizeControl.setLastAmount3(zjTzSizeControl.getAmount3());
    	//1
    	if (StrUtil.equals(zjTzSizeControl.getSecondNegotiateId(), "1")) {
    		zjTzSizeControl.setSecondNegotiateName("是");
    	}else {
    		zjTzSizeControl.setSecondNegotiateName("否");
    	}
    	//2
    	if (StrUtil.equals(zjTzSizeControl.getThirdReplyId(), "1")) {
    		zjTzSizeControl.setThirdReplyName("是");
    	}else {
    		zjTzSizeControl.setThirdReplyName("否");
    	}
    	//3
    	if (StrUtil.equals(zjTzSizeControl.getLocalGovId(), "1")) {
    		zjTzSizeControl.setLocalGovName("是");
    	}else {
    		zjTzSizeControl.setLocalGovName("否");
    	}
    	//4
    	if (StrUtil.equals(zjTzSizeControl.getJuId(), "1")) {
    		zjTzSizeControl.setJuName("是");
    	}else {
    		zjTzSizeControl.setJuName("否");
    	}
    	//5
    	if (StrUtil.equals(zjTzSizeControl.getChinaId(), "1")) {
    		zjTzSizeControl.setChinaName("是");
    	}else {
    		zjTzSizeControl.setChinaName("否");
    	}
    	zjTzSizeControl.setAddFlag("false");
    	zjTzSizeControl.setCreateUserInfo(userKey, realName);
    	int flag = zjTzSizeControlMapper.insert(zjTzSizeControl);
    	//附件1
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
    	//附件2
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
    	//附件3
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
    	//附件4
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
    	//附件5
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
    	//add合同条件
    	ZjTzContractCondition dbzjTzContractCondition = new ZjTzContractCondition();
    	dbzjTzContractCondition.setContractConditionId(UuidUtil.generate());
    	// 投资规模控制id
    	dbzjTzContractCondition.setSizeControlId(zjTzSizeControl.getSizeControlId());
    	// 项目id
    	dbzjTzContractCondition.setProjectId(zjTzSizeControl.getProjectId());
    	// 登记日期
    	dbzjTzContractCondition.setRegisterDate(zjTzSizeControl.getRegisterDate1());
    	// 登记人
    	dbzjTzContractCondition.setRegistrant(zjTzSizeControl.getRegistrant1());
    	// 投资收益模式id
    	dbzjTzContractCondition.setInvestId(zjTzSizeControl.getInvestId());
    	// 投资收益模式name
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getInvestId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControl.getInvestId());
    		dbzjTzContractCondition.setInvestName(openBankName);
    	}
    	// 一公局集团股比
    	dbzjTzContractCondition.setJuShare(zjTzSizeControl.getJuShare());
    	// 一公局集团控制性地位id
    	dbzjTzContractCondition.setJuId(zjTzSizeControl.getJuId1());
    	// 一公局集团控制性地位name
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getJuId1())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControl.getJuId1());
    		dbzjTzContractCondition.setJuName(openBankName);
    	}
    	// 总承包结算模式id
    	dbzjTzContractCondition.setZcbId(zjTzSizeControl.getZcbId());
    	// 总承包结算模式name
    	if (StrUtil.isNotEmpty(zjTzSizeControl.getZcbId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControl.getZcbId());
    		dbzjTzContractCondition.setZcbName(openBankName);
    	}
    	// 施工总承包比例
    	dbzjTzContractCondition.setZcbShare(zjTzSizeControl.getZcbShare());
    	// 设计管理情况
    	dbzjTzContractCondition.setExt1(zjTzSizeControl.getExt1());
    	// 合同对投资规模控制的要求
    	dbzjTzContractCondition.setExt2(zjTzSizeControl.getExt2());
    	// 设计变更特殊条款
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
    	//同时add记录表的投资规模和合同条件记录
    	ZjTzSizeControlRecord zjTzSizeControlRecord = new ZjTzSizeControlRecord();
    	zjTzSizeControlRecord.setSizeControlRecordId(UuidUtil.generate());
        // 项目id
        zjTzSizeControlRecord.setSizeControlId(zjTzSizeControl.getSizeControlId());
        // 项目id
        zjTzSizeControlRecord.setProjectId(zjTzSizeControl.getProjectId());
        //项目名
        zjTzSizeControlRecord.setProjectName(zjTzSizeControl.getProjectName());
        // 子项目id
        zjTzSizeControlRecord.setSubprojectInfoId(zjTzSizeControl.getSubprojectInfoId());
        // 子项目name
        zjTzSizeControlRecord.setSubprojectName(zjTzSizeControl.getSubprojectName());
        // 变动次数
        zjTzSizeControlRecord.setChangeNumber(zjTzSizeControl.getChangeNumber());
        // 变动属性id
        zjTzSizeControlRecord.setChangePropertyId(zjTzSizeControl.getChangePropertyId());
        // 变动属性name
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
        // 投资规模(元)
        zjTzSizeControlRecord.setAmount1(zjTzSizeControl.getAmount1());
        // 建安费(元)
        zjTzSizeControlRecord.setAmount2(zjTzSizeControl.getAmount2());
        // 查缺补漏(元)
        zjTzSizeControlRecord.setAmount3(zjTzSizeControl.getAmount3());
        // 是否二次谈判id
        zjTzSizeControlRecord.setSecondNegotiateId(zjTzSizeControl.getSecondNegotiateId());
        // 是否二次谈判name
        if (StrUtil.equals(zjTzSizeControl.getSecondNegotiateId(), "1")) {
        	zjTzSizeControlRecord.setSecondNegotiateName("是");
        }else {
        	zjTzSizeControlRecord.setSecondNegotiateName("否");
        }
        // 查缺补漏方案
        zjTzSizeControlRecord.setScheme(zjTzSizeControl.getScheme());
        // 三会批复id
        zjTzSizeControlRecord.setThirdReplyId(zjTzSizeControl.getThirdReplyId());
        // 三会批复name
        if (StrUtil.equals(zjTzSizeControl.getThirdReplyId(), "1")) {
        	zjTzSizeControlRecord.setThirdReplyName("是");
        }else {
        	zjTzSizeControlRecord.setThirdReplyName("否");
        }
        // 地方政府批复id
        zjTzSizeControlRecord.setLocalGovId(zjTzSizeControl.getLocalGovId());
        // 地方政府批复name
        if (StrUtil.equals(zjTzSizeControl.getLocalGovId(), "1")) {
        	zjTzSizeControlRecord.setLocalGovName("是");
        }else {
        	zjTzSizeControlRecord.setLocalGovName("否");
        }
        // 一公局集团批复id
        zjTzSizeControlRecord.setJuId(zjTzSizeControl.getJuId());
        // 一公局集团批复name
        if (StrUtil.equals(zjTzSizeControl.getJuId(), "1")) {
        	zjTzSizeControlRecord.setJuName("是");
        }else {
        	zjTzSizeControlRecord.setJuName("否");
        }
        // 中国交建批复id
        zjTzSizeControlRecord.setChinaId(zjTzSizeControl.getChinaId());
        // 中国交建批复name
        if (StrUtil.equals(zjTzSizeControl.getChinaId(), "1")) {
        	zjTzSizeControlRecord.setChinaName("是");
        }else {
        	zjTzSizeControlRecord.setChinaName("否");
        }
        // 登记日期
        zjTzSizeControlRecord.setRegisterDate(zjTzSizeControl.getRegisterDate());
        // 登记人
        zjTzSizeControlRecord.setRegistrant(zjTzSizeControl.getRegistrant());
        //备注
        zjTzSizeControlRecord.setRemarks(zjTzSizeControl.getRemarks());
        //审核id
        zjTzSizeControlRecord.setStatusId("0");
        zjTzSizeControlRecord.setStatusName("未审核");
        zjTzSizeControlRecord.setCreateUserInfo(userKey, realName);
        flag = zjTzSizeControlRecordMapper.insert(zjTzSizeControlRecord);
        //附件1
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
        if(chinaFileList != null && chinaFileList.size()>0) {
            for(ZjTzFile zjTzFile:chinaFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzSizeControlRecord.getSizeControlRecordId());
                zjTzFile.setOtherType("5");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
    	
    	//add合同条件
    	ZjTzContractConditionRecord dbzjTzContractConditionRecord = new ZjTzContractConditionRecord();
    	dbzjTzContractConditionRecord.setContractConditionRecordId(UuidUtil.generate());
    	// 投资规模控制记录id
    	dbzjTzContractConditionRecord.setSizeControlRecordId(zjTzSizeControlRecord.getSizeControlRecordId());
        // 投资规模控制id
    	dbzjTzContractConditionRecord.setSizeControlId(zjTzSizeControl.getSizeControlId());
        // 项目id
    	dbzjTzContractConditionRecord.setProjectId(zjTzSizeControl.getProjectId());
        // 登记日期
    	dbzjTzContractConditionRecord.setRegisterDate(zjTzSizeControl.getRegisterDate1());
        // 登记人
    	dbzjTzContractConditionRecord.setRegistrant(zjTzSizeControl.getRegistrant1());
        // 投资收益模式id
    	dbzjTzContractConditionRecord.setInvestId(zjTzSizeControl.getInvestId());
        // 投资收益模式name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getInvestId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzSizeControl.getInvestId());
     	  dbzjTzContractConditionRecord.setInvestName(openBankName);
        }
        // 一公局集团股比
        dbzjTzContractConditionRecord.setJuShare(zjTzSizeControl.getJuShare());
        // 一公局集团控制性地位id
        dbzjTzContractConditionRecord.setJuId(zjTzSizeControl.getJuId1());
        // 一公局集团控制性地位name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getJuId1())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzSizeControl.getJuId1());
     	  dbzjTzContractConditionRecord.setJuName(openBankName);
        }
        // 总承包结算模式id
        dbzjTzContractConditionRecord.setZcbId(zjTzSizeControl.getZcbId());
        // 总承包结算模式name
        if (StrUtil.isNotEmpty(zjTzSizeControl.getZcbId())) {
     	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzSizeControl.getZcbId());
     	  dbzjTzContractConditionRecord.setZcbName(openBankName);
        }
        // 施工总承包比例
        dbzjTzContractConditionRecord.setZcbShare(zjTzSizeControl.getZcbShare());
        // 设计管理情况
        dbzjTzContractConditionRecord.setExt1(zjTzSizeControl.getExt1());
        // 合同对投资规模控制的要求
        dbzjTzContractConditionRecord.setExt2(zjTzSizeControl.getExt2());
        // 设计变更特殊条款
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
           // 项目id
           dbzjTzSizeControl.setProjectId(zjTzSizeControl.getProjectId());
           // 子项目id
           dbzjTzSizeControl.setSubprojectInfoId(zjTzSizeControl.getSubprojectInfoId());
           // 子项目name
           dbzjTzSizeControl.setSubprojectName(zjTzSizeControl.getSubprojectName());
           // 变动次数
//           dbzjTzSizeControl.setChangeNumber(zjTzSizeControl.getChangeNumber());
           // 变动属性id
           dbzjTzSizeControl.setChangePropertyId(zjTzSizeControl.getChangePropertyId());
           // 变动属性name
           if (StrUtil.isNotEmpty(zjTzSizeControl.getChangePropertyId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("bianDongShuXing", zjTzSizeControl.getChangePropertyId());
           	dbzjTzSizeControl.setChangePropertyName(openBankName);
           }
           // 投资规模(元)
           dbzjTzSizeControl.setAmount1(zjTzSizeControl.getAmount1());
           // 建安费(元)
           dbzjTzSizeControl.setAmount2(zjTzSizeControl.getAmount2());
           // 查缺补漏(元)
           dbzjTzSizeControl.setAmount3(zjTzSizeControl.getAmount3());
           // 是否二次谈判id
           dbzjTzSizeControl.setSecondNegotiateId(zjTzSizeControl.getSecondNegotiateId());
           // 是否二次谈判name
           if (StrUtil.equals(zjTzSizeControl.getSecondNegotiateId(), "1")) {
        	   dbzjTzSizeControl.setSecondNegotiateName("是");
           }else {
        	   dbzjTzSizeControl.setSecondNegotiateName("否");
           }
           // 查缺补漏方案
           dbzjTzSizeControl.setScheme(zjTzSizeControl.getScheme());
           // 三会批复id
           dbzjTzSizeControl.setThirdReplyId(zjTzSizeControl.getThirdReplyId());
           // 三会批复name
           if (StrUtil.equals(zjTzSizeControl.getThirdReplyId(), "1")) {
        	   dbzjTzSizeControl.setThirdReplyName("是");
           }else {
        	   dbzjTzSizeControl.setThirdReplyName("否");
           }
           // 地方政府批复id
           dbzjTzSizeControl.setLocalGovId(zjTzSizeControl.getLocalGovId());
           // 地方政府批复name
           if (StrUtil.equals(zjTzSizeControl.getLocalGovId(), "1")) {
        	   dbzjTzSizeControl.setLocalGovName("是");
           }else {
        	   dbzjTzSizeControl.setLocalGovName("否");
           }
           // 一公局集团批复id
           dbzjTzSizeControl.setJuId(zjTzSizeControl.getJuId());
           // 一公局集团批复name
           if (StrUtil.equals(zjTzSizeControl.getJuId(), "1")) {
        	   dbzjTzSizeControl.setJuName("是");
           }else {
        	   dbzjTzSizeControl.setJuName("否");
           }
           // 中国交建批复id
           dbzjTzSizeControl.setChinaId(zjTzSizeControl.getChinaId());
           // 中国交建批复name
           if (StrUtil.equals(zjTzSizeControl.getChinaId(), "1")) {
        	   dbzjTzSizeControl.setChinaName("是");
           }else {
        	   dbzjTzSizeControl.setChinaName("否");
           }
           // 登记日期
           dbzjTzSizeControl.setRegisterDate(zjTzSizeControl.getRegisterDate());
           // 登记人
           dbzjTzSizeControl.setRegistrant(zjTzSizeControl.getRegistrant());
           //备注
           dbzjTzSizeControl.setRemarks(zjTzSizeControl.getRemarks());
           // 共通
           dbzjTzSizeControl.setModifyUserInfo(userKey, realName);
           // 是否更新
//           dbzjTzSizeControl.setRenew(zjTzSizeControl.getRenew());
           // 规模控制主体
           dbzjTzSizeControl.setSizeControlSubject(zjTzSizeControl.getSizeControlSubject());
           flag = zjTzSizeControlMapper.updateByPrimaryKey(dbzjTzSizeControl);
           
           
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzSizeControl.getSizeControlId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           //附件1
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
           //附件2
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
           //附件3
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
           //附件4
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
           //附件5
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
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSizeControlList);
        }
    }
}
