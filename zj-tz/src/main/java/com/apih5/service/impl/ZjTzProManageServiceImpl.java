package com.apih5.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;

import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzProManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("zjTzProManageService")
public class ZjTzProManageServiceImpl implements ZjTzProManageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzProShareholderInfoMapper zjTzProShareholderInfoMapper;

    @Autowired(required = true)
    private ZjTzProRebuyInfoMapper zjTzProRebuyInfoMapper;
    
    @Autowired(required = true)
    private ZjTzSizeControlMapper zjTzSizeControlMapper;
    
    @Autowired(required = true)
    private ZjTzDesignFlowMapper zjTzDesignFlowMapper;
    
    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitMapper zjTzDesignAdvistoryUnitMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Autowired(required = true)
    private ZjTzPolicyLocalMapper zjTzPolicyLocalMapper;
    @Autowired(required = true)
    private ZjTzPolicyLocalReplyMapper zjTzPolicyLocalReplyMapper;
    @Autowired(required = true)
    private ZjTzPolicyLocalReplyRecordMapper zjTzPolicyLocalReplyRecordMapper;
    @Autowired(required = true)
    private ZjTzComplianceDealMapper zjTzComplianceDealMapper;
    @Autowired(required = true)
    private ZjTzThreeDirectorMapper zjTzThreeDirectorMapper;
    @Autowired(required = true)
    private ZjTzThreeShareholderMapper zjTzThreeShareholderMapper;
    @Autowired(required = true)
    private ZjTzThreeSupervisorMapper zjTzThreeSupervisorMapper;
    @Autowired(required = true)
    private ZjTzRunFileMapper zjTzRunFileMapper;
    @Autowired(required = true)
    private ZjTzRunSchemeMapper zjTzRunSchemeMapper;
    @Autowired(required = true)
    private ZjTzDebtMapper zjTzDebtMapper;
    @Autowired(required = true)
    private ZjTzFinanceMonthMapper zjTzFinanceMonthMapper;
    @Autowired(required = true)
    private ZjTzRiskAnalysisMapper zjTzRiskAnalysisMapper;
    @Autowired(required = true)
    private ZjTzRiskMapper zjTzRiskMapper;
    @Autowired(required = true)
    private ZjTzLifeCycleChangeMapper zjTzLifeCycleChangeMapper;
    @Autowired(required = true)
    private ZjTzLifeCycleMapper zjTzLifeCycleMapper;
    @Autowired(required = true)
    private ZjTzProEventMapper zjTzProEventMapper;
    @Autowired(required = true)
    private ZjTzMonthlyMeetMapper zjTzMonthlyMeetMapper;
    @Autowired(required = true)
    private ZjTzPppTermMapper zjTzPppTermMapper;
    @Autowired(required = true)
    private ZjTzProPreApprovalMapper zjTzProPreApprovalMapper;
    @Autowired(required = true)
    private ZjTzThousandCheckMapper zjTzThousandCheckMapper;
    @Autowired(required = true)
    private ZjTzAnnualAssessMapper zjTzAnnualAssessMapper;
    @Autowired(required = true)
    private ZjTzComprehensiveSupMapper zjTzComprehensiveSupMapper;
    @Autowired(required = true)
    private ZjTzDesignChangeStatisticsMapper zjTzDesignChangeStatisticsMapper;
    @Autowired(required = true)
    private ZjTzDesignChangeRecordMapper zjTzDesignChangeRecordMapper;
    @Autowired(required = true)
    private ZjTzDesignChangeMapper zjTzDesignChangeMapper;
    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitRecordMapper zjTzDesignAdvistoryUnitRecordMapper;
   
    @Autowired(required = true)
    private ProInvBasicMapper proInvBasicMapper;//投资项目_投资项目基本信息0 =主表
    @Autowired(required = true)
    private ProInvHtInfoMapper proInvHtInfoMapper;//投资项目_投资合同信息1 =关联0且1对1
    @Autowired(required = true)
    private ProInvBgbcxyMapper proInvBgbcxyMapper;//投资项目_投资合同变更补充 = 关联1 且1对多
    @Autowired(required = true)
    private ProInvTxjyqxyMapper proInvTxjyqxyMapper;//投资项目_特许经营权协议2 = 关联0且1对多关系=取倒叙最后一条
    @Autowired(required = true)
    private ProInvBasicPfinfoMapper proInvBasicPfinfoMapper;//投资项目_投资项目批复信息3  = 关联0且1对多关系
    @Autowired(required = true)
    private ProInvHgxyMapper proInvHgxyMapper;//投资项目_回购协议合同4 = 关联0 库里只有一条数据暂时不知道关系
    @Autowired(required = true)
    private ProInvLrsxqkMapper proInvLrsxqkMapper;//投资项目_利润实现情况5 = 关联0且1对多关系
    @Autowired(required = true)
    private ProInvComInfMapper proInvComInfMapper;//投资项目_投资项目公司信息6 = =关联0且1对1
    @Autowired(required = true)
    private ProInvComInfGdMapper proInvComInfGdMapper;//投资项目_项目公司信息股东子表7 =关联0且1对多关系(true)
    @Autowired(required = true)
    private ProInvXmhgInfoMapper proInvXmhgInfoMapper;//投资项目_项目回购信息8 = 关联1且1对多关系
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    @Override
    public ResponseEntity getZjTzProManageListByCondition(ZjTzProManage zjTzProManage) {
    	if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
        // 分页查询
        PageHelper.startPage(zjTzProManage.getPage(),zjTzProManage.getLimit());
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
        	if(StrUtil.equals("all", zjTzProManage.getProjectId(), true)) {
        		zjTzProManage.setProjectId("");
        		zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
        	}
        } else {
        	// 集团人员时
        	if(StrUtil.equals("all", zjTzProManage.getProjectId(), true)) {
        		zjTzProManage.setProjectId("");
        	}
        }
//        zjTzProManage.setProjectType("0");
        // 获取数据
        List<ZjTzProManage> zjTzProManageList = zjTzProManageMapper.selectByZjTzProManageList(zjTzProManage);
        // 得到分页信息
        PageInfo<ZjTzProManage> p = new PageInfo<>(zjTzProManageList);
        return repEntity.okList(zjTzProManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProManageDetails(ZjTzProManage zjTzProManage) {
        if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
        // 获取数据
        ZjTzProManage dbZjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzProManage.getProjectId());
        // 数据存在
        if (dbZjTzProManage != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzProManage.getProjectId());
        	zjTzFileSelect.setOtherType("0");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzProManage.setZjTzFileList(zjTzFileList);
        	//营业执照附件
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzLicenseFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzProManage.setZjTzLicenseFileList(zjTzLicenseFileList);
        	//公司章程附件
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzBylawFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzProManage.setZjTzBylawFileList(zjTzBylawFileList);

        	//所属公司
        	List<JSONObject> projectList = new ArrayList<>();
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.accumulate("value", dbZjTzProManage.getCompanyId());
        	jsonObject.accumulate("label", dbZjTzProManage.getCompanyName());
        	projectList.add(jsonObject);
         	dbZjTzProManage.setCompanyList(projectList);
             
            return repEntity.ok(dbZjTzProManage);
        }
        else {
        	 return repEntity.ok(zjTzProManage);
//            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
	    public ResponseEntity saveZjTzProManage(ZjTzProManage zjTzProManage) {
	        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        
	        ZjTzProManage manage = new ZjTzProManage();
	        manage.setProNo(zjTzProManage.getProNo());
	        List<ZjTzProManage> manages = zjTzProManageMapper.selectByZjTzProManageList(manage);
	        if(manages != null && manages.size() >0) {
	        	return repEntity.layerMessage("no", "项目编号已存在，请重新输入！");
	        }
	        
	        zjTzProManage.setProjectId(UuidUtil.generate());
	        zjTzProManage.setKeyFlag("0");//新增的项目默认未费重点项目，想要修改局手动操作数据库---实施说的
	        zjTzProManage.setCreateUserInfo(userKey, realName);
	        
	        List<JSONObject> approve1List = zjTzProManage.getCompanyList();
			if(approve1List != null && approve1List.size()>0) {
				Object object = approve1List.get(0);
				JSONObject jsonObject = new JSONObject(object);
				zjTzProManage.setCompanyId(jsonObject.getStr("value"));
				zjTzProManage.setCompanyName(jsonObject.getStr("label"));
				zjTzProManage.setSort1(getSort(zjTzProManage.getCompanyName()));
			}
	        
	        // 项目类别
	        if (StrUtil.isNotEmpty(zjTzProManage.getProCategoryId())) {
	        	String openBankName = baseCodeService.getBaseCodeItemName("xiangMuLeiBie", zjTzProManage.getProCategoryId());
	        	zjTzProManage.setProCategoryName(openBankName);
	        }
	        //
	        if (StrUtil.isNotEmpty(zjTzProManage.getProTypeId())) {
	        	String openBankName = baseCodeService.getBaseCodeItemName("xiangMuLeiXing", zjTzProManage.getProTypeId());
	        	zjTzProManage.setProTypeName(openBankName);
	        }
	        //
	        if (StrUtil.isNotEmpty(zjTzProManage.getProProcessId())) {
	        	String openBankName = baseCodeService.getBaseCodeItemName("xiangMuJinZhan", zjTzProManage.getProProcessId());
	        	zjTzProManage.setProProcessName(openBankName);
	        }
	        //
	        if (StrUtil.isNotEmpty(zjTzProManage.getUnitId())) {
	        	String openBankName = baseCodeService.getBaseCodeItemName("guanLiDanWei", zjTzProManage.getUnitId());
	        	zjTzProManage.setUnitName(openBankName);
	        }
	        //
	        if (StrUtil.isNotEmpty(zjTzProManage.getAreaId())) {
	        	String openBankName = baseCodeService.getBaseCodeItemName("suoZaiQuYu", zjTzProManage.getAreaId());
	        	zjTzProManage.setAreaName(openBankName);
	        }
	       
	        
	        int flag = zjTzProManageMapper.insert(zjTzProManage);
	        //附件
	        List<ZjTzFile> ZjTzFileList = zjTzProManage.getZjTzFileList();
	        if(ZjTzFileList != null && ZjTzFileList.size()>0) {
	            for(ZjTzFile zjTzFile:ZjTzFileList) {
	                zjTzFile.setUid(UuidUtil.generate());
	                zjTzFile.setOtherType("0");
	                zjTzFile.setOtherId(zjTzProManage.getProjectId());
	                zjTzFile.setCreateUserInfo(userKey, realName);
	                zjTzFileMapper.insert(zjTzFile);
	            }
	        }
	        //营业执照附件
	        List<ZjTzFile> zjTzLicenseFileList = zjTzProManage.getZjTzLicenseFileList();
	        if(zjTzLicenseFileList != null && zjTzLicenseFileList.size()>0) {
	            for(ZjTzFile zjTzFile:zjTzLicenseFileList) {
	                zjTzFile.setUid(UuidUtil.generate());
	                zjTzFile.setOtherType("1");
	                zjTzFile.setOtherId(zjTzProManage.getProjectId());
	                zjTzFile.setCreateUserInfo(userKey, realName);
	                zjTzFileMapper.insert(zjTzFile);
	            }
	        }
	        
	        //公司章程附件
	        List<ZjTzFile> zjTzBylawFileList = zjTzProManage.getZjTzBylawFileList();
	        if(zjTzBylawFileList != null && zjTzBylawFileList.size()>0) {
	            for(ZjTzFile zjTzFile:zjTzBylawFileList) {
	                zjTzFile.setUid(UuidUtil.generate());
	                zjTzFile.setOtherType("2");
	                zjTzFile.setOtherId(zjTzProManage.getProjectId());
	                zjTzFile.setCreateUserInfo(userKey, realName);
	                zjTzFileMapper.insert(zjTzFile);
	            }
	        }
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.sava", zjTzProManage);
	        }
	    }

    
    
    private String getSort(String name) {
    	  String sort1 = "";
          if(StrUtil.equals(name, "直属项目") || StrUtil.equals(name, "集团机关")) {
          	sort1 = "1";
          }else if(StrUtil.equals(name, "一公司")) {
          	sort1 = "2";
          }else if(StrUtil.equals(name, "二公司")) {
          	sort1 = "3";
          }else if(StrUtil.equals(name, "三公司")) {
          	sort1 = "4";
          }else if(StrUtil.equals(name, "四公司")) {
          	sort1 = "5";
          }else if(StrUtil.equals(name, "五公司")) {
          	sort1 = "6";
          }else if(StrUtil.equals(name, "六公司")) {
          	sort1 = "7";
          }else if(StrUtil.equals(name, "七公司")) {
          	sort1 = "8";
          }else if (StrUtil.equals(name, "八公司")) {
        	sort1 = "9";
          }else if (StrUtil.equals(name, "九公司")) {
        	sort1 = "10";
          }else if (StrUtil.equals(name, "十公司")) {
			sort1 = "11";
          }else if(StrUtil.equals(name, "桥隧公司")) {
          	sort1 = "12";
          }else if(StrUtil.equals(name, "厦门公司")) {
          	sort1 = "13";
          }else if(StrUtil.equals(name, "海威公司")) {
          	sort1 = "14";
          }else if(StrUtil.equals(name, "豪生公司")) {
          	sort1 = "15";
          }else if(StrUtil.equals(name, "总承包分公司")) {
          	sort1 = "16";
          }else if(StrUtil.equals(name, "建筑分公司")) {
          	sort1 = "17";
          }else if(StrUtil.equals(name, "重庆公司")) {
          	sort1 = "18";
          }else if(StrUtil.equals(name, "水利公司")) {
          	sort1 = "19";
          }else if(StrUtil.equals(name, "华中公司")) {
          	sort1 = "20";
          }else if(StrUtil.equals(name, "西北公司")) {
          	sort1 = "21";
          }else if(StrUtil.equals(name, "西南公司")) {
          	sort1 = "22";
          }else if(StrUtil.equals(name, "华南公司")) {
          	sort1 = "23";
          }else if(StrUtil.equals(name, "盾构分公司")) {
          	sort1 = "24";
          }else if(StrUtil.equals(name, "电气化分公司")) {
          	sort1 = "25";
          }
          return sort1;
    }
    
    @Override
    public ResponseEntity updateZjTzProManage(ZjTzProManage zjTzProManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzProManage.getProjectId());
        if (dbzjTzProManage != null && StrUtil.isNotEmpty(dbzjTzProManage.getProjectId())) {
        	//项目编号重复判断
        	ZjTzProManage manage = new ZjTzProManage();
        	manage.setProNoSelect(zjTzProManage.getProNo());
        	manage.setProjectIdSelect(zjTzProManage.getProjectId());
            List<ZjTzProManage> manages = zjTzProManageMapper.selectByZjTzProManageList(manage);
            if(manages != null && manages.size() >0) {
            	return repEntity.layerMessage("no", "项目编号已存在，请重新输入！");
            }
        	
        	
        	//所属公司 
        	List<JSONObject> approve1List = zjTzProManage.getCompanyList();
      		if(approve1List != null && approve1List.size()>0) {
      			Object object = approve1List.get(0);
      			JSONObject jsonObject = new JSONObject(object);
      			dbzjTzProManage.setCompanyId(jsonObject.getStr("value"));
      			dbzjTzProManage.setCompanyName(jsonObject.getStr("label"));
      			dbzjTzProManage.setSort1(getSort(dbzjTzProManage.getCompanyName()));
      		}
        	// 项目编号
           dbzjTzProManage.setProNo(zjTzProManage.getProNo());
//           if (!StrUtil.equals(dbzjTzProManage.getProjectName(), zjTzProManage.getProjectName()) || (!StrUtil.equals(dbzjTzProManage.getProjectShortName(), zjTzProManage.getProjectShortName()))) {
//        	   //
//        	   ZjTzPolicyLocal local = new ZjTzPolicyLocal();
//        	   local.setProjectName(zjTzProManage.getProjectName());
//        	   local.setProjectShortName(zjTzProManage.getProjectShortName());
//        	   local.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzPolicyLocalMapper.updateZjTzPolicyLocalProjectShortName(local);
//        	   //
//        	   ZjTzPolicyLocalReply localReply = new ZjTzPolicyLocalReply();
//        	   localReply.setProjectName(zjTzProManage.getProjectName());
//        	   localReply.setProjectShortName(zjTzProManage.getProjectShortName());
//        	   localReply.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzPolicyLocalReplyMapper.updateZjTzPolicyLocalReplyProjectShortName(localReply);
//        	   //
//        	   ZjTzPolicyLocalReplyRecord localReplyRecord = new ZjTzPolicyLocalReplyRecord();
//        	   localReplyRecord.setProjectName(zjTzProManage.getProjectName());
//        	   localReplyRecord.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzPolicyLocalReplyRecordMapper.updateZjTzPolicyLocalReplyRecordProjectShortName(localReplyRecord);
//        	   //
//        	   ZjTzDesignFlow designFlow = new ZjTzDesignFlow();
//        	   designFlow.setProjectName(zjTzProManage.getProjectName());
//        	   designFlow.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzDesignFlowMapper.updateZjTzDesignFlowProjectShortName(designFlow);
//        	   //
//        	   ZjTzDesignAdvistoryUnit designAdvistoryUnit = new ZjTzDesignAdvistoryUnit();
//        	   designAdvistoryUnit.setProjectName(zjTzProManage.getProjectName());
//        	   designAdvistoryUnit.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzDesignAdvistoryUnitMapper.updateZjTzDesignAdvistoryUnitProjectShortName(designAdvistoryUnit);
//        	   //
//        	   ZjTzComplianceDeal complianceDeal = new ZjTzComplianceDeal();
//        	   complianceDeal.setProjectName(zjTzProManage.getProjectName());
//        	   complianceDeal.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzComplianceDealMapper.updateZjTzComplianceDealProjectShortName(complianceDeal);
//        	   //
//        	   ZjTzThreeDirector director = new ZjTzThreeDirector();
//        	   director.setProjectName(zjTzProManage.getProjectName());
//        	   director.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzThreeDirectorMapper.updateZjTzThreeDirectorProjectShortName(director);
//        	   //
//        	   ZjTzThreeShareholder shareholder = new ZjTzThreeShareholder();
//        	   shareholder.setProjectName(zjTzProManage.getProjectName());
//        	   shareholder.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzThreeShareholderMapper.updateZjTzThreeShareholderProjectShortName(shareholder);
//        	   //
//        	   ZjTzThreeSupervisor supervisor = new ZjTzThreeSupervisor();
//        	   supervisor.setProjectName(zjTzProManage.getProjectName());
//        	   supervisor.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzThreeSupervisorMapper.updateZjTzThreeSupervisorProjectShortName(supervisor);
//        	   //
//        	   ZjTzRunFile runFile = new ZjTzRunFile();
//        	   runFile.setProjectName(zjTzProManage.getProjectName());
//        	   runFile.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzRunFileMapper.updateZjTzRunFileProjectShortName(runFile);
//        	   //
//        	   ZjTzRunScheme runScheme = new ZjTzRunScheme();
//        	   runScheme.setProjectName(zjTzProManage.getProjectName());
//        	   runScheme.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzRunSchemeMapper.updateZjTzRunSchemeProjectShortName(runScheme);
//        	   //
//        	   ZjTzDebt debt = new ZjTzDebt();
//        	   debt.setProjectName(zjTzProManage.getProjectName());
//        	   debt.setProjectId(dbzjTzProManage.getProjectId());
//        	   flag = zjTzDebtMapper.updateZjTzDebtProjectShortName(debt);
//        	   //
////        	   ZjTzFinanceMonth financeMonth = new ZjTzFinanceMonth();
////        	   financeMonth.setProjectName(zjTzProManage.getProjectName());
////        	   financeMonth.setProjectId(dbzjTzProManage.getProjectId());
////        	   flag = zjTzFinanceMonthMapper.updateZjTzFinanceMonthProjectShortName(financeMonth);
////        	    private ZjTzRiskAnalysisMapper zjTzRiskAnalysisMapper;
////        	    private ZjTzRiskMapper zjTzRiskMapper;
////        	    private ZjTzLifeCycleChangeMapper zjTzLifeCycleChangeMapper;
////        	    private ZjTzLifeCycleMapper zjTzLifeCycleMapper;
////        	    private ZjTzProEventMapper zjTzProEventMapper;
////        	    private ZjTzMonthlyMeetMapper zjTzMonthlyMeetMapper;
////        	    private ZjTzPppTermMapper zjTzPppTermMapper;
////        	    private ZjTzProPreApprovalMapper zjTzProPreApprovalMapper;
////        	    private ZjTzThousandCheckMapper zjTzThousandCheckMapper;
////        	    private ZjTzAnnualAssessMapper zjTzAnnualAssessMapper;
////        	    private ZjTzComprehensiveSupMapper zjTzComprehensiveSupMapper;
////        	    private ZjTzDesignChangeStatisticsMapper zjTzDesignChangeStatisticsMapper;
////        	    private ZjTzDesignChangeRecordMapper zjTzDesignChangeRecordMapper;
////        	    private ZjTzDesignChangeMapper zjTzDesignChangeMapper;
////        	    private ZjTzDesignAdvistoryUnitRecordMapper zjTzDesignAdvistoryUnitRecordMapper;
//           }
           // 项目名称
           dbzjTzProManage.setProjectName(zjTzProManage.getProjectName());
           // 项目简称
           dbzjTzProManage.setProjectShortName(zjTzProManage.getProjectShortName());
           // 项目类别id
           dbzjTzProManage.setProCategoryId(zjTzProManage.getProCategoryId());
           // 项目类别name
           if (StrUtil.isNotEmpty(zjTzProManage.getProCategoryId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("xiangMuLeiBie", zjTzProManage.getProCategoryId());
        	   dbzjTzProManage.setProCategoryName(openBankName);
           }
           // 项目类型id
           dbzjTzProManage.setProTypeId(zjTzProManage.getProTypeId());
           // 项目类型name
           if (StrUtil.isNotEmpty(zjTzProManage.getProTypeId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("xiangMuLeiXing", zjTzProManage.getProTypeId());
        	   dbzjTzProManage.setProTypeName(openBankName);
           }
           // 项目进展id
           dbzjTzProManage.setProProcessId(zjTzProManage.getProProcessId());
           // 项目进展name
           if (StrUtil.isNotEmpty(zjTzProManage.getProProcessId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("xiangMuJinZhan", zjTzProManage.getProProcessId());
        	   dbzjTzProManage.setProProcessName(openBankName);
           }
           // 管理单位id
           dbzjTzProManage.setUnitId(zjTzProManage.getUnitId());
           // 管理单位name
           if (StrUtil.isNotEmpty(zjTzProManage.getUnitId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("guanLiDanWei", zjTzProManage.getUnitId());
        	   dbzjTzProManage.setUnitName(openBankName);
           }
           // 投资模式
           dbzjTzProManage.setInvestPatten(zjTzProManage.getInvestPatten());
           // 排序号
           dbzjTzProManage.setSequence(zjTzProManage.getSequence());
           // 建设期（年）
           dbzjTzProManage.setConstructPeriod(zjTzProManage.getConstructPeriod());
           // 运营期/回购期（年）
           dbzjTzProManage.setRunPeriod(zjTzProManage.getRunPeriod());
           // 投资协议签订时间
           dbzjTzProManage.setSignDate1(zjTzProManage.getSignDate1());
           // 特权合同签订时间
           dbzjTzProManage.setSignDate2(zjTzProManage.getSignDate2());
           // 合同约定开工时间
           dbzjTzProManage.setSignDate3(zjTzProManage.getSignDate3());
           // 实际开工时间
           dbzjTzProManage.setActualDate(zjTzProManage.getActualDate());
           // 交工验收时间
           dbzjTzProManage.setCheckDate(zjTzProManage.getCheckDate());
           // 计划完工日期
           dbzjTzProManage.setPlanDate(zjTzProManage.getPlanDate());
           // 合同约定运营/回购开始时间
           dbzjTzProManage.setRunDate1(zjTzProManage.getRunDate1());
           // 实际运营/回购开始时间
           dbzjTzProManage.setRunDate2(zjTzProManage.getRunDate2());
           // 合同约定运营/回购结束时间
           dbzjTzProManage.setRunDate3(zjTzProManage.getRunDate3());
           // 实际运营/回购结束时间
           dbzjTzProManage.setRunDate4(zjTzProManage.getRunDate4());
           // 是否并入一公局集团财务报表
           dbzjTzProManage.setMergeFlag(zjTzProManage.getMergeFlag());
           // 所在区域id
           dbzjTzProManage.setAreaId(zjTzProManage.getAreaId());
           // 所在区域name
           if (StrUtil.isNotEmpty(zjTzProManage.getAreaId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("suoZaiQuYu", zjTzProManage.getAreaId());
        	   dbzjTzProManage.setAreaName(openBankName);
           }
           // 中交批复时间
           dbzjTzProManage.setZjDate(zjTzProManage.getZjDate());
           // 中交批复投资额（元）
           dbzjTzProManage.setZjAmount1(zjTzProManage.getZjAmount1());
           // 其中：建安费 （元）
           dbzjTzProManage.setZjAmount2(zjTzProManage.getZjAmount2());
           // 中交集团批复文号 
           dbzjTzProManage.setZjNo(zjTzProManage.getZjNo());
           // 政府批复概算（元）
           dbzjTzProManage.setGoverAmount(zjTzProManage.getGoverAmount());
           // 工程概况
           dbzjTzProManage.setProProfile(zjTzProManage.getProProfile());
           // 纳入国家、省发改委PPP项目库或财政部、省财政厅PPP综合信息平台项目库情况（具体阶段）
           dbzjTzProManage.setExt1(zjTzProManage.getExt1());
           // 政府承诺给予的优惠、补贴等
           dbzjTzProManage.setExt2(zjTzProManage.getExt2());
           // 项目地图跳转参数
           dbzjTzProManage.setExt3(zjTzProManage.getExt3());
           // 跳转参数例子以及说明：
           dbzjTzProManage.setExt4(zjTzProManage.getExt4());
           // 是否签约
           dbzjTzProManage.setSignFlag(zjTzProManage.getSignFlag());
           // 合同额（元）
           dbzjTzProManage.setAmount1(zjTzProManage.getAmount1());
           // 回购总额
           dbzjTzProManage.setAmount2(zjTzProManage.getAmount2());
           // 期中：建安费
           dbzjTzProManage.setAmount3(zjTzProManage.getAmount3());
           // 征拆费
           dbzjTzProManage.setAmount4(zjTzProManage.getAmount4());
           // 管理费
           dbzjTzProManage.setAmount5(zjTzProManage.getAmount5());
           // 监理费
           dbzjTzProManage.setAmount6(zjTzProManage.getAmount6());
           // 勘察设计费（元）
           dbzjTzProManage.setAmount7(zjTzProManage.getAmount7());
           // 其他
           dbzjTzProManage.setAmount8(zjTzProManage.getAmount8());
           // 资本金（元）
           dbzjTzProManage.setFund1(zjTzProManage.getFund1());
           // 自有资金
           dbzjTzProManage.setFund2(zjTzProManage.getFund2());
           // 基金
           dbzjTzProManage.setFund3(zjTzProManage.getFund3());
           // 其中：一公局认购基金
           dbzjTzProManage.setFund4(zjTzProManage.getFund4());
           // 其他股东
           dbzjTzProManage.setFund5(zjTzProManage.getFund5());
           // 融资
           dbzjTzProManage.setFund6(zjTzProManage.getFund6());
           // 银行借款
           dbzjTzProManage.setFund7(zjTzProManage.getFund7());
           // 基金
           dbzjTzProManage.setFund8(zjTzProManage.getFund8());
           // 其中：一公局认购基金
           dbzjTzProManage.setFund9(zjTzProManage.getFund9());
           // 信托等其他
           dbzjTzProManage.setFund10(zjTzProManage.getFund10());
           // 政府补贴
           dbzjTzProManage.setFund11(zjTzProManage.getFund11());
           // 其他资金
           dbzjTzProManage.setFund12(zjTzProManage.getFund12());
           // 评估预算类别
           dbzjTzProManage.setEvaluate1(zjTzProManage.getEvaluate1());
           // 评估投资总额
           dbzjTzProManage.setEvaluate2(zjTzProManage.getEvaluate2());
           // 其中：建安费
           dbzjTzProManage.setEvaluate3(zjTzProManage.getEvaluate3());
           // 项目财务内部收益率
           dbzjTzProManage.setEvaluate4(zjTzProManage.getEvaluate4());
           // 资本金财务内部收益率
           dbzjTzProManage.setEvaluate5(zjTzProManage.getEvaluate5());
           // 动态投资回收期
           dbzjTzProManage.setEvaluate6(zjTzProManage.getEvaluate6());
           // 评估时假定价差收益率
           dbzjTzProManage.setEvaluate7(zjTzProManage.getEvaluate7());
           // 评估价差收益
           dbzjTzProManage.setEvaluate8(zjTzProManage.getEvaluate8());
           // 评估施工利润
           dbzjTzProManage.setEvaluate9(zjTzProManage.getEvaluate9());
           // 评估投融收益
           dbzjTzProManage.setEvaluate10(zjTzProManage.getEvaluate10());
           // 承诺价差收益
           dbzjTzProManage.setEvaluate11(zjTzProManage.getEvaluate11());
           // 承诺投融收益
           dbzjTzProManage.setEvaluate12(zjTzProManage.getEvaluate12());
           // 投评月均收入
           dbzjTzProManage.setEvaluate13(zjTzProManage.getEvaluate13());
           // 投评月均车辆通行数量
           dbzjTzProManage.setEvaluate14(zjTzProManage.getEvaluate14());
           // 项目公司名称
           dbzjTzProManage.setCompany1(zjTzProManage.getCompany1());
           // 项目公司成立时间
           dbzjTzProManage.setCompany2(zjTzProManage.getCompany2());
           // 项目公司注册地
           dbzjTzProManage.setCompany3(zjTzProManage.getCompany3());
           // 注册资本金
           dbzjTzProManage.setCompany4(zjTzProManage.getCompany4());
           // 项目资本金比例
           dbzjTzProManage.setCompany5(zjTzProManage.getCompany5());
           // 我方资本金（自有资金）
           dbzjTzProManage.setCompany6(zjTzProManage.getCompany6());
           // 一公局占整个项目的股比
           dbzjTzProManage.setCompany7(zjTzProManage.getCompany7());
           // 我方施工比例
           dbzjTzProManage.setCompany8(zjTzProManage.getCompany8());
           // 中交方占整个项目的股比
           dbzjTzProManage.setCompany9(zjTzProManage.getCompany9());
           // 项目公司股权结构（股东名称及股比）
           dbzjTzProManage.setCompany10(zjTzProManage.getCompany10());
           // 公司经理/电话
           dbzjTzProManage.setMember1(zjTzProManage.getMember1());
           // 公司总会/电话
           dbzjTzProManage.setMember2(zjTzProManage.getMember2());
           // 公司经营负责人/电话
           dbzjTzProManage.setMember3(zjTzProManage.getMember3());
           // 报表负责人/电话
           dbzjTzProManage.setMember4(zjTzProManage.getMember4());
           // 共通
           dbzjTzProManage.setModifyUserInfo(userKey, realName);
           //建设期结束标志
           dbzjTzProManage.setConstructEnd(zjTzProManage.getConstructEnd());
           //应交工日期
           dbzjTzProManage.setHandoverDatePlan(zjTzProManage.getHandoverDatePlan());
           //应竣工日期
           dbzjTzProManage.setComplateDatePlan(zjTzProManage.getComplateDatePlan());
           //实际交工日期
           dbzjTzProManage.setHandoverDateActrual(zjTzProManage.getHandoverDateActrual());
           //实际竣工日期
           dbzjTzProManage.setComplateDateActrual(zjTzProManage.getComplateDateActrual());
           //批复类型
           dbzjTzProManage.setReplyType(zjTzProManage.getReplyType());
           //管理单位
           dbzjTzProManage.setManagerDepartmentName(zjTzProManage.getManagerDepartmentName());
           //项目类型
           dbzjTzProManage.setProjectType(zjTzProManage.getProjectType());
           //法定代表人
           dbzjTzProManage.setLegalPersonName(zjTzProManage.getLegalPersonName());
           //营业执照
           dbzjTzProManage.setBusinessLicense(zjTzProManage.getProjectId()+"_License");
           //公司章程
           dbzjTzProManage.setBylaw(zjTzProManage.getProjectId()+"_Bylaw");
           //项目所在地
           dbzjTzProManage.setLocation(zjTzProManage.getLocation());
           // 策划批复开工日期
           dbzjTzProManage.setApprovalStartDate(zjTzProManage.getApprovalStartDate());
           //策划批复交工日期
           dbzjTzProManage.setApprovalHandoverDate(zjTzProManage.getApprovalHandoverDate());
           //策划批复竣工日期
           dbzjTzProManage.setApprovalCompleteDate(zjTzProManage.getApprovalCompleteDate());
           //工期分析主体
           dbzjTzProManage.setAnalySubject(zjTzProManage.getAnalySubject());
           // 规模控制主体
           dbzjTzProManage.setSizeControlSubject(zjTzProManage.getSizeControlSubject());
           flag = zjTzProManageMapper.updateByPrimaryKey(dbzjTzProManage);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzProManage.getProjectId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> ZjTzFileList = zjTzProManage.getZjTzFileList();
           if(ZjTzFileList != null && ZjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:ZjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherType("0");
                   zjTzFile.setOtherId(zjTzProManage.getProjectId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //营业执照附件
           List<ZjTzFile> zjTzLicenseFileList = zjTzProManage.getZjTzLicenseFileList();
           if(zjTzLicenseFileList != null && zjTzLicenseFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzLicenseFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherType("1");
                   zjTzFile.setOtherId(zjTzProManage.getProjectId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //公司章程附件
           List<ZjTzFile> zjTzBylawFileList = zjTzProManage.getZjTzBylawFileList();
           if(zjTzBylawFileList != null && zjTzBylawFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzBylawFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherType("2");
                   zjTzFile.setOtherId(zjTzProManage.getProjectId());
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
            return repEntity.ok("sys.data.update",zjTzProManage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProManage(List<ZjTzProManage> zjTzProManageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProManageList != null && zjTzProManageList.size() > 0) {
        	//删除之前的校验
        	for (ZjTzProManage zjTzProManage : zjTzProManageList) {
        		ZjTzSizeControl sizeControl = new ZjTzSizeControl();
        		sizeControl.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzSizeControl> controls = zjTzSizeControlMapper.selectByZjTzSizeControlList(sizeControl);
        		if(controls != null && controls.size() >0) {
        			 return repEntity.layerMessage("no", "该项目已经填写投资规模控制信息，不能删除！！");
        		}
        		ZjTzThousandCheck check = new ZjTzThousandCheck();
        		check.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzThousandCheck> checks = zjTzThousandCheckMapper.selectByZjTzThousandCheckList(check);
        		if(checks != null && checks.size() >0) {
        			 return repEntity.layerMessage("no", "该项目已经填写千分制，不能删除！！");
        		}
        		ZjTzComprehensiveSup comprehensiveSup = new ZjTzComprehensiveSup();
        		comprehensiveSup.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzComprehensiveSup> comprehensiveSups = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(comprehensiveSup);
        		if(comprehensiveSups != null && comprehensiveSups.size() >0) {
        			return repEntity.layerMessage("no", "该项目已经填写综合督、查/综合督导，不能删除！！");
        		}
        		ZjTzAnnualAssess annualAssess = new ZjTzAnnualAssess();
        		annualAssess.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzAnnualAssess> annualAssesses = zjTzAnnualAssessMapper.selectByZjTzAnnualAssessList(annualAssess);
        		if(annualAssesses != null && annualAssesses.size() >0) {
        			return repEntity.layerMessage("no", "该项目已经填写项目年度考核，不能删除！！");
        		}
        		ZjTzDesignFlow designFlow = new ZjTzDesignFlow();
        		designFlow.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzDesignFlow> designFlows = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(designFlow);
        		if(designFlows != null && designFlows.size() >0) {
        			return repEntity.layerMessage("no", "该项目已经填写设计流程管理，不能删除！！");
        		}

        		ZjTzDesignAdvistoryUnit advistoryUnit = new ZjTzDesignAdvistoryUnit();
        		advistoryUnit.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzDesignAdvistoryUnit> advistoryUnits = zjTzDesignAdvistoryUnitMapper.selectByZjTzDesignAdvistoryUnitList(advistoryUnit);
        		if(advistoryUnits != null && advistoryUnits.size() >0) {
        			return repEntity.layerMessage("no", "该项目已经填写设计、咨询单位管理，不能删除！！");
        		}

        		ZjTzDesignChange designChange = new ZjTzDesignChange();
        		designChange.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzDesignChange> designChanges = zjTzDesignChangeMapper.selectByZjTzDesignChangeList(designChange);
        		if(designChanges != null && designChanges.size() >0) {
        			return repEntity.layerMessage("no", "该项目已经填写设计变更管理，不能删除！！");
        		}
        		
			}
        	//del
        	for (ZjTzProManage zjTzProManage : zjTzProManageList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzProManage.getProjectId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//
        		ZjTzProShareholderInfo zjTzProShareholderInfoSelect = new ZjTzProShareholderInfo();
        		zjTzProShareholderInfoSelect.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzProShareholderInfo> delectZjTzProShareholderInfoList =  zjTzProShareholderInfoMapper.selectByZjTzProShareholderInfoList(zjTzProShareholderInfoSelect);
        		if(delectZjTzProShareholderInfoList != null && delectZjTzProShareholderInfoList.size() >0) {
        			zjTzProShareholderInfoSelect.setModifyUserInfo(userKey, realName);
        			zjTzProShareholderInfoMapper.batchDeleteUpdateZjTzProShareholderInfo(delectZjTzProShareholderInfoList, zjTzProShareholderInfoSelect);
        		}
        		//
        		ZjTzProRebuyInfo zjTzProRebuyInfoSelect = new ZjTzProRebuyInfo();
        		zjTzProRebuyInfoSelect.setProjectId(zjTzProManage.getProjectId());
        		List<ZjTzProRebuyInfo> delectZjTzProRebuyInfoList = zjTzProRebuyInfoMapper.selectByZjTzProRebuyInfoList(zjTzProRebuyInfoSelect);
        		if(delectZjTzProRebuyInfoList != null && delectZjTzProRebuyInfoList.size() >0) {
        			zjTzProRebuyInfoSelect.setModifyUserInfo(userKey, realName);
        			zjTzProRebuyInfoMapper.batchDeleteUpdateZjTzProRebuyInfo(delectZjTzProRebuyInfoList, zjTzProRebuyInfoSelect);
        		}
        	}
        	ZjTzProManage zjTzProManage = new ZjTzProManage();
        	zjTzProManage.setModifyUserInfo(userKey, realName);
        	flag = zjTzProManageMapper.batchDeleteUpdateZjTzProManage(zjTzProManageList, zjTzProManage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProManageList);
        }
    }

	@Override
	public ResponseEntity getZjTzProManageListForHard(ZjTzProManage zjTzProManage) {
		  if (zjTzProManage == null) {
	            zjTzProManage = new ZjTzProManage();
	        }
	        // 分页查询
	        PageHelper.startPage(zjTzProManage.getPage(),zjTzProManage.getLimit());
	        
	        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        // 权限（1：公司级
	        String ext1 = TokenUtils.getExt1(request);
	        String userId = TokenUtils.getUserId(request);
	        // 不是集团的人员时
	        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
	        	// 选择全部项目是，通过拼接的sql去查询
	        	if(StrUtil.equals("all", zjTzProManage.getProjectId(), true)) {
	        		zjTzProManage.setProjectId("");
	        		zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
	        	}
	        } else {
	        	// 集团人员时
	        	if(StrUtil.equals("all", zjTzProManage.getProjectId(), true)) {
	        		zjTzProManage.setProjectId("");
	        	}
	        }
	        // 获取数据
	        List<ZjTzProManage> zjTzProManageList = zjTzProManageMapper.selectByZjTzProManageList(zjTzProManage);
	        for (ZjTzProManage proManage : zjTzProManageList) {
	        	ZjTzFile zjTzFileSelect = new ZjTzFile();
	            zjTzFileSelect.setOtherId(proManage.getProjectId());
	            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	            proManage.setZjTzFileList(zjTzFileList);
	            
	            List<JSONObject> projectList = new ArrayList<>();
	        	JSONObject jsonObject = new JSONObject();
	        	jsonObject.accumulate("value", proManage.getCompanyId());
	        	jsonObject.accumulate("label", proManage.getCompanyName());
	        	projectList.add(jsonObject);
	        	proManage.setCompanyList(projectList);
	            
	        }
	        // 得到分页信息
	        PageInfo<ZjTzProManage> p = new PageInfo<>(zjTzProManageList);

	        return repEntity.okList(zjTzProManageList, p.getTotal());
	}
	
	@Override
	public ResponseEntity changeZjTzProManageToSign(ZjTzProManage zjTzProManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectByPrimaryKey(zjTzProManage.getProjectId());
		if (dbzjTzProManage != null && StrUtil.isNotEmpty(dbzjTzProManage.getProjectId())) {
			//项目编号重复判断
			ZjTzProManage manage = new ZjTzProManage();
			manage.setProNoSelect(zjTzProManage.getProNo());
			manage.setProjectIdSelect(zjTzProManage.getProjectId());
			List<ZjTzProManage> manages = zjTzProManageMapper.selectByZjTzProManageList(manage);
			if(manages != null && manages.size() >0) {
				return repEntity.layerMessage("no", "转出位置已存在相同项目编号！");
			}
			// 是否签约   0:否   1:是
			dbzjTzProManage.setSignFlag("1");
			dbzjTzProManage.setProjectType("0");
			dbzjTzProManage.setProNo(zjTzProManage.getProNo());
			dbzjTzProManage.setModifyUserInfo(userKey, realName);
			flag = zjTzProManageMapper.updateByPrimaryKey(dbzjTzProManage);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzProManage);
		}
	}
	
	@Override
	public ResponseEntity synZjTzProManage() {
		int flag = 0;
		List<ZjTzProManage> zjTzProManageList = zjTzProManageMapper.synZjTzProManageList(new ZjTzProManage());
		
		if(zjTzProManageList != null && zjTzProManageList.size() >0) {
			ProInvBasic basic = new ProInvBasic();
			for (ZjTzProManage dbzjTzProManage : zjTzProManageList) {
				if (StrUtil.isNotEmpty(dbzjTzProManage.getProjectId())) {
					basic = proInvBasicMapper.getProInvBasicByProjectId(dbzjTzProManage.getProjectId());
				}
				if(basic == null) {
				    continue;
				}
				ProInvComInf comInf = new ProInvComInf();
				comInf.setInvProId(basic.getId());
				List<ProInvComInf> comInfs = proInvComInfMapper.selectByProInvComInfList(comInf);
				if(comInfs != null && comInfs.size() >0) {
					comInf = comInfs.get(0);
				}
				
				ProInvBasicPfinfo basicPfinfo = new ProInvBasicPfinfo();
				basicPfinfo.setInvProId(basic.getId());
				List<ProInvBasicPfinfo> basicPfinfoList = proInvBasicPfinfoMapper.selectByProInvBasicPfinfoList(basicPfinfo);
				if(basicPfinfoList != null && basicPfinfoList.size() >0) {
					basicPfinfo = basicPfinfoList.get(0);
				}
				ProInvLrsxqk lrsxqk = new ProInvLrsxqk();
				lrsxqk.setXmmc(basic.getId());
				List<ProInvLrsxqk> lrsxqkList = proInvLrsxqkMapper.selectByProInvLrsxqkList(lrsxqk);
				if(lrsxqkList != null && lrsxqkList.size() >0) {
					
				}
				
				ProInvHgxy hgxy = new ProInvHgxy();
				ProInvHtInfo htInfo = new ProInvHtInfo();
				ProInvTxjyqxy txjyqxy = new ProInvTxjyqxy();
				htInfo.setInvProId(basic.getId());
				List<ProInvHtInfo> infoList = proInvHtInfoMapper.selectByProInvHtInfoList(htInfo);
			
				BigDecimal bcjaf_money = new BigDecimal("0");
				BigDecimal bczcf_money = new BigDecimal("0");
				BigDecimal bcglf_money = new BigDecimal("0");
				BigDecimal bcjlf_money = new BigDecimal("0");
				BigDecimal bcccsjf_money = new BigDecimal("0");
				BigDecimal bcoth_money = new BigDecimal("0");
				
				if(infoList != null && infoList.size() >0) {
					htInfo = infoList.get(0);
					
					ProInvBgbcxy bgbcxy = new ProInvBgbcxy();
					bgbcxy.setInvHtinfoId(htInfo.getId());
					List<ProInvBgbcxy> bgbcxyList = proInvBgbcxyMapper.selectByProInvBgbcxyList(bgbcxy);
					if(bgbcxyList != null && bgbcxyList.size() >0) {
						for (ProInvBgbcxy invBgbcxy : bgbcxyList) {
							bcjaf_money = CalcUtils.calcAdd(bcjaf_money, invBgbcxy.getBcjafMoney());
							bczcf_money = CalcUtils.calcAdd(bczcf_money, invBgbcxy.getBczcfMoney());
							bcglf_money = CalcUtils.calcAdd(bcglf_money, invBgbcxy.getBcglfMoney());
							bcjlf_money = CalcUtils.calcAdd(bcjlf_money, invBgbcxy.getBcjlfMoney());
							bcccsjf_money = CalcUtils.calcAdd(bcccsjf_money, invBgbcxy.getBcccsjfMoney());
							bcoth_money = CalcUtils.calcAdd(bcoth_money, invBgbcxy.getBcothMoney());
						}
					}
					
					txjyqxy.setInvHtinfoId(htInfo.getId());
					List<ProInvTxjyqxy> txjyqxyList = proInvTxjyqxyMapper.selectByProInvTxjyqxyList(txjyqxy);
					if(txjyqxyList != null && txjyqxyList.size() >0) {
						txjyqxy = txjyqxyList.get(txjyqxyList.size()-1);
					}
					hgxy.setInvHtinfoId(htInfo.getId());
					List<ProInvHgxy> hgxuList = proInvHgxyMapper.selectByProInvHgxyList(hgxy);
					if(hgxuList != null && hgxuList.size() >0) {
						hgxy = hgxuList.get(0);
					}
				}
				
				// 工程类别id
				dbzjTzProManage.setProCategoryId(basic.getGclb());
				// 工程类别name
				if (StrUtil.isNotEmpty(basic.getGclb())) {
					String openBankName = baseCodeService.getBaseCodeItemName("xiangMuLeiBie", basic.getGclb());
					dbzjTzProManage.setProCategoryName(openBankName);
				}
				// 项目类型id
				dbzjTzProManage.setProTypeId(basic.getProCategory());
				// 项目类型name
				if (StrUtil.isNotEmpty(basic.getProCategory())) {
					String openBankName = baseCodeService.getBaseCodeItemName("xiangMuLeiXing", basic.getProCategory());
					dbzjTzProManage.setProTypeName(openBankName);
				}
				// 投资模式
				dbzjTzProManage.setInvestPatten(basic.getInvestPatten());
				// 项目进展id
				dbzjTzProManage.setProProcessId(basic.getXmjd());
				// 项目进展name
				if (StrUtil.isNotEmpty(basic.getXmjd())) {
					String openBankName = baseCodeService.getBaseCodeItemName("xiangMuJinZhan", basic.getXmjd());
					dbzjTzProManage.setProProcessName(openBankName);
				}
				// 建设期（年）
				dbzjTzProManage.setConstructPeriod(basic.getJsnx());
				// 运营期/回购期（年）
				if(StrUtil.equals(basic.getProCategory(), "2.2")) {
					//2.2经营性投资
					if(basic.getYynx() != null) {
						dbzjTzProManage.setRunPeriod(basic.getYynx()+"");
					}else {
						dbzjTzProManage.setRunPeriod("");
					}
				}
				if(StrUtil.equals(basic.getProCategory(), "2.1") || StrUtil.equals(basic.getProCategory(), "2.3")) {
					//2.1非经营性投资     2.3准经营性投资
					if(basic.getHgnx() != null) {
						dbzjTzProManage.setRunPeriod(basic.getHgnx()+"");
					}else {
						dbzjTzProManage.setRunPeriod("");
					}
				}
				// 投资协议签订时间
				dbzjTzProManage.setSignDate1(htInfo.getHtqdDate());
				// 特权合同签订时间
				dbzjTzProManage.setSignDate2(txjyqxy.getHtqdDate());
				// 合同约定开工时间
				dbzjTzProManage.setSignDate3(htInfo.getKgDate());
				// 实际开工时间
				dbzjTzProManage.setActualDate(htInfo.getSjkgrq());
				// 交工验收时间
				dbzjTzProManage.setCheckDate(basic.getJgrq());
				// 计划完工日期
				dbzjTzProManage.setPlanDate(htInfo.getHtjgrq());
				// 合同约定运营/回购开始时间
				if(StrUtil.equals(basic.getProCategory(), "2.2")) {
					//2.2经营性投资
					dbzjTzProManage.setRunDate1(txjyqxy.getYyqsDate());
				}
				if(StrUtil.equals(basic.getProCategory(), "2.1") || StrUtil.equals(basic.getProCategory(), "2.3")) {
					//2.1非经营性投资     2.3准经营性投资
					dbzjTzProManage.setRunDate1(htInfo.getYdkshgrq());
				}
				// 实际运营/回购开始时间
				dbzjTzProManage.setRunDate2(basic.getKshgyyrq());
				// 合同约定运营/回购结束时间
				dbzjTzProManage.setRunDate3(basic.getJsrq());
				//批复类型       1:中国交建批复   2:一公局集团批复   3:未批复
				dbzjTzProManage.setReplyType(basic.getIsReport());
				// 是否并入一公局集团财务报表         0:否  1:是
				dbzjTzProManage.setMergeFlag(basic.getIsBb());
				// 中交批复时间
				dbzjTzProManage.setZjDate(basic.getPfsj());
				//项目所在地
				dbzjTzProManage.setLocation(basic.getLocation());
				// 中交批复投资额（元）
				dbzjTzProManage.setZjAmount1(basic.getZjpftze());
				// 中交集团批复文号 
				dbzjTzProManage.setZjNo(basic.getPfwh());
				//政府批复概算(元)
				 dbzjTzProManage.setGoverAmount(basicPfinfo.getPfzje());
				// 工程概况
				dbzjTzProManage.setProProfile(basic.getProContent());
				// 政府承诺给予的优惠、补贴等
				dbzjTzProManage.setExt2(basic.getZfcnyhtj());
		//合同金额================【问题票4547】
				BigDecimal amount1 = new BigDecimal("0");
				if(htInfo.getZtze() != null) {
					amount1 = htInfo.getZtze();
				}
				amount1 = CalcUtils.calcAdd(amount1, CalcUtils.calcAdd(bcjaf_money,CalcUtils.calcAdd(bczcf_money,CalcUtils.calcAdd(bcglf_money,CalcUtils.calcAdd(bcjlf_money,CalcUtils.calcAdd(bcccsjf_money,bcoth_money))))));
				// 合同额（元）
				dbzjTzProManage.setAmount1(amount1);
				// 回购总额
				dbzjTzProManage.setAmount2(hgxy.getHgje());
				// 期中：建安费
				dbzjTzProManage.setAmount3(CalcUtils.calcAdd(htInfo.getJafMoney(),bcjaf_money));
				// 征拆费
				dbzjTzProManage.setAmount4(CalcUtils.calcAdd(htInfo.getZcfMoney(),bczcf_money));
				// 管理费
				dbzjTzProManage.setAmount5(CalcUtils.calcAdd(htInfo.getGlfMoney(),bcglf_money));
				// 监理费
				dbzjTzProManage.setAmount6(CalcUtils.calcAdd(htInfo.getJlfMoney(),bcjlf_money));
				// 勘察设计费（元）
				dbzjTzProManage.setAmount7(CalcUtils.calcAdd(htInfo.getCcsjfMoney(),bcccsjf_money));
				// 其他
				dbzjTzProManage.setAmount8(CalcUtils.calcAdd(htInfo.getOthMoney(),bcoth_money));
		//资金结构=======================
				BigDecimal fund1 = new BigDecimal("0");
				BigDecimal fund2 = new BigDecimal("0");
				BigDecimal fund3 = new BigDecimal("0");
				BigDecimal fund4 = new BigDecimal("0");
				BigDecimal fund5 = new BigDecimal("0");
				BigDecimal fund6 = new BigDecimal("0");
				BigDecimal fund7 = new BigDecimal("0");
				BigDecimal fund8 = new BigDecimal("0");
				BigDecimal fund9 = new BigDecimal("0");
				BigDecimal fund10 = new BigDecimal("0");
				BigDecimal fund11 = new BigDecimal("0");
				BigDecimal fund12 = new BigDecimal("0");
				// 资本金（元）
				if(htInfo.getZbjbl() != null) {
					fund1 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getZbjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund1(fund1);
				// 自有资金
				if(htInfo.getZyzjbl() != null) {
					fund2 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getZyzjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund2(fund2);
				// 基金
				if(htInfo.getJjbl() != null) {
					fund3 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getJjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund3(fund3);
				// 其中：一公局认购基金
				if(htInfo.getZbjYgjrgjjbl() != null) {
					fund4 = CalcUtils.calcMultiply(fund3, CalcUtils.calcDivide(htInfo.getZbjYgjrgjjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund4(fund4);
				// 其他股东
				if(htInfo.getQtgdzjbl() != null) {
					fund5 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getQtgdzjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund5(fund5);
				// 融资
				if(htInfo.getRzbl() != null) {
					fund6 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getRzbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund6(fund6);
				// 银行借款
				if(htInfo.getYhjdbl() != null) {
					fund7 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getYhjdbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund7(fund7);
				// 基金
				if(htInfo.getRzJjbl() != null) {
					fund8 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getRzJjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund8(fund8);
				// 其中：一公局认购基金
				if(htInfo.getRzYgjrgjjbl() != null) {
					fund9 = CalcUtils.calcMultiply(fund8, CalcUtils.calcDivide(htInfo.getRzYgjrgjjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund9(fund9);
				// 信托等其他
				if(htInfo.getRzXtdqt() != null) {
					fund10 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getRzXtdqt(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund10(fund10);
				// 政府补贴
				if(htInfo.getZfbtbl() != null) {
					fund11 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getZfbtbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund11(fund11);
				// 其他资金
				if(htInfo.getQtzjbl() != null) {
					fund12 = CalcUtils.calcMultiply(amount1, CalcUtils.calcDivide(htInfo.getQtzjbl(), new BigDecimal("100"),4));
				}
				dbzjTzProManage.setFund12(fund12);
		//项目评估与预算
				// 项目财务内部收益率
				dbzjTzProManage.setEvaluate4(basic.getXmcwnbsyl()==null?new BigDecimal("0")+"":basic.getXmcwnbsyl()+"");
				// 资本金财务内部收益率
				dbzjTzProManage.setEvaluate5(basic.getZbjcwnbsyl()==null?new BigDecimal("0")+"":basic.getZbjcwnbsyl()+"");
				// 动态投资回收期
				dbzjTzProManage.setEvaluate6(basic.getDttzhsqn());
				// 评估时假定价差收益率
				dbzjTzProManage.setEvaluate7(basic.getPgsjdjcsyl()==null?new BigDecimal("0")+"":basic.getPgsjdjcsyl()+"");
				// 评估价差收益
				dbzjTzProManage.setEvaluate8(basic.getPgjdjcsy());
				// 评估施工利润
				dbzjTzProManage.setEvaluate9(basic.getSglr());
				// 评估投融收益
				dbzjTzProManage.setEvaluate10(basic.getPgjdtrzsy());
				// 承诺价差收益
				dbzjTzProManage.setEvaluate11(basic.getCnmbjcsy());
				// 承诺投融收益
				dbzjTzProManage.setEvaluate12(basic.getCnmutrzsy());
				// 投评月均车辆通行数量
				dbzjTzProManage.setEvaluate14(basic.getTpyjcltxsl());
		//项目公司信息
				// 项目公司名称
				dbzjTzProManage.setCompany1(comInf.getProComName());
				// 项目公司成立时间
				dbzjTzProManage.setCompany2(comInf.getRegisterDate());
				// 项目公司注册地 
				dbzjTzProManage.setCompany3(comInf.getCompany3());
				// 注册资本金
				dbzjTzProManage.setCompany4(comInf.getZczj());
				// 项目资本金比例
				dbzjTzProManage.setCompany5(basic.getProZbjbl());
				// 一公局占整个项目的股比
				dbzjTzProManage.setCompany7(comInf.getSzgq());
				// 法定代表人
				dbzjTzProManage.setLegalPersonName(comInf.getFddbr());
				// 中交方占整个项目的股比
				dbzjTzProManage.setCompany9(comInf.getZgjjszgq());
	//项目成员
				// 公司经理/电话
				dbzjTzProManage.setMember1(comInf.getComZjl()+"/"+comInf.getZjlTel());
				// 公司总会/电话
				dbzjTzProManage.setMember2(comInf.getComCwfzr()+"/"+comInf.getCwTel());
	//股东先删除再覆盖哦
				ZjTzProShareholderInfo delShareholderInfo = new ZjTzProShareholderInfo();
				delShareholderInfo.setProjectId(dbzjTzProManage.getProjectId());
				List<ZjTzProShareholderInfo> delShareholderInfoList = zjTzProShareholderInfoMapper.selectByZjTzProShareholderInfoList(delShareholderInfo);
				if(delShareholderInfoList != null && delShareholderInfoList.size() >0) {
					delShareholderInfo.setModifyUserInfo("del", "del");
					zjTzProShareholderInfoMapper.batchDeleteUpdateZjTzProShareholderInfo(delShareholderInfoList, delShareholderInfo);
				}
				ProInvComInfGd comInfGd = new ProInvComInfGd();
				comInfGd.setInvProId(basic.getId());
				List<ProInvComInfGd> comInfGdList = proInvComInfGdMapper.selectByProInvComInfGdList(comInfGd);
				BigDecimal company8 = new BigDecimal("0");
				if(comInfGdList != null && comInfGdList.size() >0) {
					for (ProInvComInfGd gd : comInfGdList) {
						company8 = CalcUtils.calcAdd(company8, gd.getFpbl());
						ZjTzProShareholderInfo addShareholderInfo = new ZjTzProShareholderInfo();
						addShareholderInfo.setShareholderInfoId(UuidUtil.generate());
						addShareholderInfo.setProjectId(dbzjTzProManage.getProjectId());
						// 股东名称
						addShareholderInfo.setShareholderName(gd.getGdName());
						//股东类型   01:公司内    02:公司外
						addShareholderInfo.setShareholderType(gd.getGdCategory());
						// 股比
						addShareholderInfo.setProportion(gd.getCgbl());
						//施工比例
						addShareholderInfo.setConstructionProportion(gd.getFpbl());
						// 出资额
						addShareholderInfo.setContributionAmount(gd.getCze());
						addShareholderInfo.setCreateUserInfo("copy", "copy");
						flag = zjTzProShareholderInfoMapper.insert(addShareholderInfo);
					}
			//回购明细先删除再覆盖哦
					ZjTzProRebuyInfo delRebuyInfo = new ZjTzProRebuyInfo();
					delRebuyInfo.setProjectId(dbzjTzProManage.getProjectId());
					List<ZjTzProRebuyInfo> delRebuyInfoList = zjTzProRebuyInfoMapper.selectByZjTzProRebuyInfoList(delRebuyInfo);
					if(delRebuyInfoList != null && delRebuyInfoList.size() >0) {
						delRebuyInfo.setModifyUserInfo("del", "del");
						flag = zjTzProRebuyInfoMapper.batchDeleteUpdateZjTzProRebuyInfo(delRebuyInfoList, delRebuyInfo);
					}
					
					if(htInfo != null && StrUtil.isNotEmpty(htInfo.getId())) {
						ProInvXmhgInfo xmhgInfo = new ProInvXmhgInfo();
						xmhgInfo.setInvHtinfoId(htInfo.getId());
						List<ProInvXmhgInfo> xmhgInfoList = proInvXmhgInfoMapper.selectByProInvXmhgInfoList(xmhgInfo);
						int i = 1;
						if(xmhgInfoList != null && xmhgInfoList.size() >0) {
							for (ProInvXmhgInfo xmhgInfoSub : xmhgInfoList) {
								ZjTzProRebuyInfo rebuyInfo = new ZjTzProRebuyInfo(); 
								rebuyInfo.setRebuyInfoId(UuidUtil.generate());
								// 项目id
								rebuyInfo.setProjectId(dbzjTzProManage.getProjectId());
								// 协议约定次数
								rebuyInfo.setAppointNumber(i);
								// 协议约定金额
								rebuyInfo.setAppointAmount(xmhgInfoSub.getHgxyMoney());
								// 协议约定时间 
								rebuyInfo.setAppointDate(xmhgInfoSub.getHgxyDate());
								rebuyInfo.setCreateUserInfo("copy", "copy");
								zjTzProRebuyInfoMapper.insert(rebuyInfo);
								i++;
							}
						}
					}
				}
				// 我方施工比例
				dbzjTzProManage.setCompany8(company8);
				flag = zjTzProManageMapper.updateByPrimaryKey(dbzjTzProManage);
			}
		}
		if (flag == 0) {
			return repEntity.errorUpdate();
		}
		else {
			return repEntity.ok("sys.data.update","");
		}
	}
	
	/**
	 * 
	 * 首页地域总览-项目进展/项目类型
	 */
	@Override
	public ResponseEntity getHomeRegionalOverviewProcessAndType(ZjTzProManage zjTzProManage) {
		if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		List<ZjTzProManage> dbZjTzProManageList = zjTzProManageMapper.selectHomeRegionalOverviewProcessAndType(zjTzProManage);
		if (dbZjTzProManageList != null && dbZjTzProManageList.size() > 0) {
			for (ZjTzProManage zjTzProManage2 : dbZjTzProManageList) {
				BigDecimal percent = CalcUtils.calcMultiply(zjTzProManage2.getPercent(), new BigDecimal(100));
				zjTzProManage2.setPercent(percent.setScale(2, BigDecimal.ROUND_HALF_UP));
				if (StrUtil.isEmpty(zjTzProManage2.getProProcessName())) {
					zjTzProManage2.setProProcessName("空");
				}
				if (StrUtil.isEmpty(zjTzProManage2.getProTypeName())) {
					zjTzProManage2.setProTypeName("空");
				}
			}
		}
		return repEntity.ok(dbZjTzProManageList);
	}
	/**
	 * 
	 * 首页地域总览-项目列表
	 */
	@Override
	public ResponseEntity getHomeRegionalOverviewProList(ZjTzProManage zjTzProManage) {
		if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		List<ZjTzProManage> dbProList = zjTzProManageMapper.selectHomeRegionalOverviewProList(zjTzProManage);
		if (dbProList != null && dbProList.size() > 0) {
			for (ZjTzProManage zjTzProManage2 : dbProList) {
				BigDecimal company7 = zjTzProManage2.getCompany7();
				zjTzProManage2.setCompany7(company7.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				BigDecimal amount1 = CalcUtils.calcDivide(zjTzProManage2.getAmount1(), new BigDecimal(10000));
				zjTzProManage2.setAmount1(amount1.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal amount3 = CalcUtils.calcDivide(zjTzProManage2.getAmount3(), new BigDecimal(10000));
				zjTzProManage2.setAmount3(amount3.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return repEntity.ok(dbProList);
	}
	/**
	 * 
	 * 项目列表导出
	 */
	@Override
	public ResponseEntity exportHomeRegionalOverviewProList(ZjTzProManage zjTzProManage, HttpServletResponse response) {
//	        HttpServletResponse response = new HttpServletResponse();
	        if (zjTzProManage == null) {
	        	zjTzProManage = new ZjTzProManage();
	        }
	        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userId = TokenUtils.getUserId(request);
	        String ext1 = TokenUtils.getExt1(request);
	        // 不是集团的人员时
	        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
	        	//其他身份
	        	zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
	        }
	        // 获取数据
	        List<ZjTzProManage> dbProList = zjTzProManageMapper.selectHomeRegionalOverviewProList(zjTzProManage);
	        
	        // 表头
	        List<List<?>> rowsList = Lists.newArrayList();
	        List<?> row1 = CollUtil.newArrayList("管理单位筛选："+zjTzProManage.getCompanyName(), "项目类型筛选："+zjTzProManage.getProTypeName(), "项目进展筛选："+zjTzProManage.getProProcessName(),"项目所在地筛选："+zjTzProManage.getAreaName(),"","",
	                "","","","单位：万元");
	        List<?> row2 = CollUtil.newArrayList("项目编号", "项目名称", "项目简称","管理单位","投资总额","建安费",
	                "我方持股比例","项目类型","项目进展","项目所在地");
	        rowsList.add(row1);
	        rowsList.add(row2);
	        
	        
	        // 数据装载（与上面的表头对应）
	        if(dbProList != null && dbProList.size()>0) {
	            for(ZjTzProManage dbzjTzProManage:dbProList) {
	                rowsList.add(CollUtil.newArrayList(dbzjTzProManage.getProNo(),
	                		dbzjTzProManage.getProjectName(),
	                		dbzjTzProManage.getProjectShortName(),
	                		dbzjTzProManage.getCompanyName(),
	                		dbzjTzProManage.getAmount1(),
	                		dbzjTzProManage.getAmount3(),
	                		dbzjTzProManage.getCompany7(),
	                		dbzjTzProManage.getProTypeName(),
	                		dbzjTzProManage.getProProcessName(),
	                		dbzjTzProManage.getAreaName()

	                        )
	                );
	            }

	            // 报表名称
	            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
	            String fileName = "xxx报表-" + datestr + ".xlsx";

	            List<List<?>> rows = CollUtil.newArrayList(rowsList);
	            // 通过工具类创建writer，创建xlsx格式
	            ExcelWriter writer = ExcelUtil.getWriter(true);
	            //定义启始行
	            int index = 2;
	            int index2 = 2;
	            //按照管理单位分组数据汇总处理
	            Map<String, List<ZjTzProManage>> companyNameGroupList =
	            		dbProList.stream().collect(Collectors.groupingBy(dbzjTzProManage->dbzjTzProManage.getCompanyName(),LinkedHashMap::new,Collectors.toList()));
	            for (Map.Entry<String, List<ZjTzProManage>> listEntry : companyNameGroupList.entrySet()) {
	            	List<ZjTzProManage> companyNameList = listEntry.getValue();
	            	//根据数据条数设置合并单元格信息
	            	if (companyNameList.size() == 1) {
	            		//一条数据不合并
		            	index = index + companyNameList.size();
		                index2 = index2 + companyNameList.size();
					}else {
						//规则编写
						writer.merge(index, index + companyNameList.size() - 1, 3, 3, null, true);
						index = index + companyNameList.size();
					}
				}
	            writer.setColumnWidth(0, 15);//项目编号：第1列9px宽
	            writer.setColumnWidth(1, 45);//项目名称：第1列9px宽
	            writer.setColumnWidth(2, 25);//项目简称
	            writer.setColumnWidth(3, 17);//管理单位
	            writer.setColumnWidth(4, 20);//投资总额
	            writer.setColumnWidth(5, 20);//建安费
	            writer.setColumnWidth(6, 13);//我方持股比例
	            writer.setColumnWidth(7, 20);//项目类型
	            writer.setColumnWidth(8, 20);//项目进展
	            writer.setColumnWidth(9, 12);//项目所在地
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
	
	/**
	 * 
	 * 所有项目工期状态
	 */
	@Override
	public ResponseEntity getHomeProjectStatusAllProject(ZjTzProManage zjTzProManage) {
		if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
        JSONArray jsonArray = new JSONArray();
        List<ZjTzProManage> zjTzProManageList = zjTzProManageMapper.selectHomeProjectStatus(zjTzProManage);
        	if (zjTzProManageList != null && zjTzProManageList.size() > 0) {
        		int totalCount = 0;
        		int count1 = 0;
        		int count2 = 0;
        		int count3 = 0;
        		int count4 = 0;
        		int count5 = 0;
        		for (ZjTzProManage dbzjTzProManage : zjTzProManageList) {
        			//实际竣工日期
        			Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
        			//合同约定竣工日期
        			Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
        			//实际交工日期
        			Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
        			//合同约定交工日期
        			Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
        			//策划批复竣工日期
        	    	Date approvalCompleteDate = dbzjTzProManage.getApprovalCompleteDate();
        	    	//策划批复交工日期
        	    	Date approvalHandoverDate = dbzjTzProManage.getApprovalHandoverDate();
        			//当前日期
					Date currDate = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd"));
					
					ZjTzProSubprojectInfo  zjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
					zjTzProSubprojectInfo.setProjectId(dbzjTzProManage.getProjectId());
					// 获取子项目数据
			        List<ZjTzProSubprojectInfo> zjTzProSubprojectInfoList = zjTzProSubprojectInfoMapper.selectByZjTzProSubprojectInfoList(zjTzProSubprojectInfo);
        			
			        
			        //预警日期类型为合同约定
        			if (StrUtil.equals(zjTzProManage.getDateType(), "1")) {
						//工期分析主体选的项目
        				if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
        					//建设期结束标志为交工
        					if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
        						// 填写了实际竣工日期
        						if (complateDateActrual != null) {
        							//填写合同约定竣工日期
        							if (complateDatePlan != null) {
        								//实际竣工日期早于等于合同约定竣工日期
        								if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
        									//填写实际交工日期和合同约定交工日期
        									if (handoverDateActrual != null && handoverDatePlan != null) {
        										//实际交工日期早于等于合同约定交工日期
        										if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
        											dbzjTzProManage.setPeriodFlag("正常完工");//已交工，已竣工
        											//实际交工日期晚于合同约定交工日期	
        										}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
        											dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，已竣工
        										}
        									}
        									//实际竣工日期晚于合同约定竣工日期
        								}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
        									//填写实际交工日期和合同约定交工日期
        									if (handoverDateActrual != null && handoverDatePlan != null) {
        										//实际交工日期早于等于合同约定交工日期
        										if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
        											dbzjTzProManage.setPeriodFlag("超期完工");//已交工，超期竣工
        											//实际交工日期晚于合同约定交工日期	
        										}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
        											dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，超期竣工
        										}
        									}
        								}
        							}
        							//未填写实际竣工日期
        						}else if (complateDateActrual == null) {
        							//填写了实际交工日期
        							if (handoverDateActrual != null) {
        								//填写实际交工日期和合同约定交工日期
        								if (handoverDateActrual != null && handoverDatePlan != null) {
        									//实际交工日期早于等于合同约定交工日期
        									if (handoverDateActrual.compareTo(handoverDatePlan) <= 0) {
        										//填写合同约定竣工日期
        										if (complateDatePlan != null) {
        											//当前日期早于等于合同约定竣工日期
        											if (currDate.compareTo(complateDatePlan) <= 0) {
        												dbzjTzProManage.setPeriodFlag("建设中");//已交工，竣工中
        												//当前日期晚于合同约定竣工日期
        											}else if (currDate.compareTo(complateDatePlan) > 0) {
        												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//已交工，应竣工未竣工
        											}
        										}
        										//实际交工日期晚于合同约定交工日期
        									}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
        										//填写合同约定竣工日期
        										if (complateDatePlan != null) {
        											//当前日期早于等于合同约定竣工日期
        											if (currDate.compareTo(complateDatePlan) <= 0) {
        												dbzjTzProManage.setPeriodFlag("建设中");//超期交工，竣工中
        												//当前日期晚于合同约定竣工日期
        											}else if (currDate.compareTo(complateDatePlan) > 0) {
        												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//超期交工，应竣工未竣工
        											}
        										}
        										
        									}
        								}
        								//未填写实际交工日期
        							}else if (handoverDateActrual == null) {
        								//填写合同约定交工日期
        								if (handoverDatePlan != null) {
        									//当前日期早于等于合同约定交工日期
        									if (currDate.compareTo(handoverDatePlan) <= 0) {
        										dbzjTzProManage.setPeriodFlag("建设中");//建设中
        										//当前日期晚于合同约定交工日期
        									}else if (currDate.compareTo(handoverDatePlan) > 0) {
        										dbzjTzProManage.setPeriodFlag("应交工未交工");//应交工未交工
        									}
        								}
        							}
        						}
        						
        						//建设期结束标志为竣工
        					}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
        						//填写了实际竣工日期
        						if (complateDateActrual != null) {
        							//填写了合同约定竣工日期
        							if (complateDatePlan != null) {
        								//实际竣工日期早于等于合同约定竣工日期
        								if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
        									dbzjTzProManage.setPeriodFlag("正常完工");//已竣工
        									//实际竣工日期晚于合同约定竣工日期
        								}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
        									dbzjTzProManage.setPeriodFlag("超期完工");//超期竣工
        								}
        							}
        							//未填写实际竣工日期	
        						}else if (complateDateActrual == null) {
        							//填写了合同约定竣工日期
        							if (complateDatePlan != null) {
        								//当前日期早于等于合同约定竣工日期
        								if (currDate.compareTo(complateDatePlan) <= 0) {
        									dbzjTzProManage.setPeriodFlag("建设中");//建设中
        									//当前日期晚于合同约定竣工日期
        								}else if (currDate.compareTo(complateDatePlan) > 0) {
        									dbzjTzProManage.setPeriodFlag("应竣工未竣工");//应竣工未竣工
        								}
        								
        							}
        						}
        					}
        					//工期分析主体选的子项目
						}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
							
					        String[] strs = new String[] {"","","","",""};
					        if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
								for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
									//实际竣工日期
									Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
									//合同约定竣工日期
									Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
									//实际交工日期
									Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
									//合同约定交工日期
									Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
									
									//建设期结束标志为交工
		        					if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
		        						// 填写了实际竣工日期
		        						if (complateDateActual != null) {
		        							//填写合同约定竣工日期
		        							if (subComplateDatePlan != null) {
		        								//实际竣工日期早于等于合同约定竣工日期
		        								if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
		        									//填写实际交工日期和合同约定交工日期
		        									if (handoverDateActual != null && subHandoverDatePlan != null) {
		        										//实际交工日期早于等于合同约定交工日期
		        										if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0 ) {
//		        											dbzjTzProManage.setPeriodFlag("正常完工");//已交工，已竣工
		        											strs[0] = "正常完工";
		        											//实际交工日期晚于合同约定交工日期	
		        										}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
//		        											dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，已竣工
		        											strs[1] = "超期完工";
		        										}
		        									}
		        									//实际竣工日期晚于合同约定竣工日期
		        								}else if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
		        									//填写实际交工日期和合同约定交工日期
		        									if (handoverDateActual != null && subHandoverDatePlan != null) {
		        										//实际交工日期早于等于合同约定交工日期
		        										if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0 ) {
//		        											dbzjTzProManage.setPeriodFlag("超期完工");//已交工，超期竣工
		        											strs[1] = "超期完工";
		        											//实际交工日期晚于合同约定交工日期	
		        										}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
//		        											dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，超期竣工
		        											strs[1] = "超期完工";
		        										}
		        									}
		        								}
		        							}
		        							//未填写实际竣工日期
		        						}else if (complateDateActual == null) {
		        							//填写了实际交工日期
		        							if (handoverDateActual != null) {
		        								//填写实际交工日期和合同约定交工日期
		        								if (handoverDateActual != null && subHandoverDatePlan != null) {
		        									//实际交工日期早于等于合同约定交工日期
		        									if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0) {
		        										//填写合同约定竣工日期
		        										if (subComplateDatePlan != null) {
		        											//当前日期早于等于合同约定竣工日期
		        											if (currDate.compareTo(subComplateDatePlan) <= 0) {
//		        												dbzjTzProManage.setPeriodFlag("建设中");//已交工，竣工中
		        												strs[2] = "建设中";
		        												//当前日期晚于合同约定竣工日期
		        											}else if (currDate.compareTo(subComplateDatePlan) > 0) {
//		        												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//已交工，应竣工未竣工
		        												strs[3] = "应竣工未竣工";
		        											}
		        										}
		        										//实际交工日期晚于合同约定交工日期
		        									}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
		        										//填写合同约定竣工日期
		        										if (subComplateDatePlan != null) {
		        											//当前日期早于等于合同约定竣工日期
		        											if (currDate.compareTo(subComplateDatePlan) <= 0) {
//		        												dbzjTzProManage.setPeriodFlag("建设中");//超期交工，竣工中
		        												strs[2] = "建设中";
		        												//当前日期晚于合同约定竣工日期
		        											}else if (currDate.compareTo(subComplateDatePlan) > 0) {
//		        												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//超期交工，应竣工未竣工
		        												strs[3] = "应竣工未竣工";
		        											}
		        										}
		        										
		        									}
		        								}
		        								//未填写实际交工日期
		        							}else if (handoverDateActual == null) {
		        								//填写合同约定交工日期
		        								if (subHandoverDatePlan != null) {
		        									//当前日期早于等于合同约定交工日期
		        									if (currDate.compareTo(subHandoverDatePlan) <= 0) {
//		        										dbzjTzProManage.setPeriodFlag("建设中");//建设中
		        										strs[2] = "建设中";
		        										//当前日期晚于合同约定交工日期
		        									}else if (currDate.compareTo(subHandoverDatePlan) > 0) {
//		        										dbzjTzProManage.setPeriodFlag("应交工未交工");//应交工未交工
		        										strs[4] = "应交工未交工";
		        									}
		        								}
		        							}
		        						}
		        						
		        						//建设期结束标志为竣工
		        					}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
		        						//填写了实际竣工日期
		        						if (complateDateActual != null) {
		        							//填写了合同约定竣工日期
		        							if (subComplateDatePlan != null) {
		        								//实际竣工日期早于等于合同约定竣工日期
		        								if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
//		        									dbzjTzProManage.setPeriodFlag("正常完工");//已竣工
		        									strs[0] = "正常完工";
		        									//实际竣工日期晚于合同约定竣工日期
		        								}else if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
//		        									dbzjTzProManage.setPeriodFlag("超期完工");//超期竣工
		        									strs[1] = "超期完工";
		        								}
		        							}
		        							//未填写实际竣工日期	
		        						}else if (complateDateActual == null) {
		        							//填写了合同约定竣工日期
		        							if (subComplateDatePlan != null) {
		        								//当前日期早于等于合同约定竣工日期
		        								if (currDate.compareTo(subComplateDatePlan) <= 0) {
//		        									dbzjTzProManage.setPeriodFlag("建设中");//建设中
		        									strs[2] = "建设中";
		        									//当前日期晚于合同约定竣工日期
		        								}else if (currDate.compareTo(subComplateDatePlan) > 0) {
//		        									dbzjTzProManage.setPeriodFlag("应竣工未竣工");//应竣工未竣
		        									strs[3] = "应竣工未竣工";
		        								}
		        								
		        							}
		        						}
		        					}

								}
							//项目下的子项目按照优先级，判断父项目的工期预警分类	
							for (int i = 0; i < strs.length; i++) {
								if (StrUtil.equals(strs[4], "应交工未交工")) {
									dbzjTzProManage.setPeriodFlag("应交工未交工");
								}else if (StrUtil.equals(strs[3], "应竣工未竣工") && StrUtil.equals(strs[4], "")) {
									dbzjTzProManage.setPeriodFlag("应竣工未竣工");
								}else if (StrUtil.equals(strs[2], "建设中") && StrUtil.equals(strs[4], "") && StrUtil.equals(strs[3], "")) {
									dbzjTzProManage.setPeriodFlag("建设中");
								}else if (StrUtil.equals(strs[1], "超期完工") && StrUtil.equals(strs[4], "") && StrUtil.equals(strs[3], "") && StrUtil.equals(strs[2], "")) {
									dbzjTzProManage.setPeriodFlag("超期完工");
								}else if (StrUtil.equals(strs[0], "正常完工") && StrUtil.equals(strs[4], "") && StrUtil.equals(strs[3], "") && StrUtil.equals(strs[2], "") && StrUtil.equals(strs[1], "")) {
									dbzjTzProManage.setPeriodFlag("正常完工");
								}
							}	
								
								
							}
						
						}
        				//预警日期类型为策划批复
					}else if (StrUtil.equals(zjTzProManage.getDateType(), "2")) {
						//工期分析主体为项目
							if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
								
								//建设期结束标志为交工
								if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
									// 填写了实际竣工日期
									if (complateDateActrual != null) {
										//填写策划批复竣工日期
										if (approvalCompleteDate != null) {
											//实际竣工日期早于等于策划批复竣工日期
											if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
												//填写实际交工日期和策划批复交工日期
												if (handoverDateActrual != null && approvalHandoverDate != null) {
													//实际交工日期早于等于策划批复交工日期
													if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0 ) {
														dbzjTzProManage.setPeriodFlag("正常完工");//已交工，已竣工
														//实际交工日期晚于策划批复交工日期
													}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
														dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，已竣工
													}
												}
												//实际竣工日期晚于策划批复竣工日期
											}else if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
												//填写实际交工日期和策划批复交工日期
												if (handoverDateActrual != null && approvalHandoverDate != null) {
													//实际交工日期早于等于策划批复交工日期
													if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0 ) {
														dbzjTzProManage.setPeriodFlag("超期完工");//已交工，超期竣工
														//实际交工日期晚于策划批复交工日期
													}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
														dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，超期竣工
													}
												}
											}
										}
										//未填写实际竣工日期
									}else if (complateDateActrual == null) {
										//填写了实际交工日期
										if (handoverDateActrual != null) {
											//填写实际交工日期和策划批复交工日期
											if (handoverDateActrual != null && approvalHandoverDate != null) {
												//实际交工日期早于等于策划批复交工日期
												if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0) {
													//填写策划批复竣工日期
													if (approvalCompleteDate != null) {
														//当前日期早于等于策划批复竣工日期
														if (currDate.compareTo(approvalCompleteDate) <= 0) {
															dbzjTzProManage.setPeriodFlag("建设中");//已交工，竣工中
															//当前日期晚于策划批复竣工日期
														}else if (currDate.compareTo(approvalCompleteDate) > 0) {
															dbzjTzProManage.setPeriodFlag("应竣工未竣工");//已交工，应竣工未竣工
															
														}
													}
													//实际交工日期晚于策划批复交工日期
												}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
													//填写策划批复竣工日期
													if (approvalCompleteDate != null) {
														//当前日期早于等于策划批复竣工日期
														if (currDate.compareTo(approvalCompleteDate) <= 0) {
															dbzjTzProManage.setPeriodFlag("建设中");//超期交工，竣工中
															//当前日期晚于策划批复竣工日期
														}else if (currDate.compareTo(approvalCompleteDate) > 0) {
															dbzjTzProManage.setPeriodFlag("应竣工未竣工");//超期交工，应竣工未竣工
														}
													}
													
												} 
											}
											//未填写实际交工日期
										}else if (handoverDateActrual == null) {
											//填写策划批复交工日期
											if (approvalHandoverDate != null) {
												//当前日期早于等于合同约定交工日期
												if (currDate.compareTo(approvalHandoverDate) <= 0) {
													dbzjTzProManage.setPeriodFlag("建设中");//建设中
													//当前日期晚于合同约定交工日期
												}else if (currDate.compareTo(approvalHandoverDate) > 0) {
													dbzjTzProManage.setPeriodFlag("应交工未交工");//应交工未交工
												}
											}
										}
									}
									
									//建设期结束标志为竣工
								}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
									//填写了实际竣工日期
									if (complateDateActrual != null) {
										//填写了策划批复竣工日期
										if (approvalCompleteDate != null) {
											//实际竣工日期早于等于策划批复竣工日期
											if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
												dbzjTzProManage.setPeriodFlag("正常完工");//已竣工
												//实际竣工日期晚于策划批复竣工日期
											}else if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
												dbzjTzProManage.setPeriodFlag("超期完工");//超期竣工
											}
										}
										//未填写实际竣工日期	
									}else if (complateDateActrual == null) {
										//填写了策划批复竣工日期
										if (approvalCompleteDate != null) {
											//当前日期早于等于策划批复竣工日期
											if (currDate.compareTo(approvalCompleteDate) <= 0) {
												dbzjTzProManage.setPeriodFlag("建设中");//建设中
												//当前日期晚于策划批复竣工日期
											}else if (currDate.compareTo(approvalCompleteDate) > 0) {
												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//应竣工未竣工
											}
											
										}
									}
								}
								//工期分析主体为子项目
							}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {

					        String[] strs = new String[] {"","","","",""};
					        if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
								for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
									//实际竣工日期
									Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
									//实际交工日期
									Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
									//策划批复竣工日期
									Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
									//策划批复交工日期
									Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
									
									//建设期结束标志为交工
		        					if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
		        						// 填写了实际竣工日期
		        						if (complateDateActual != null) {
		        							//填写策划批复竣工日期
		        							if (subApprovalCompleteDate != null) {
		        								//实际竣工日期早于等于策划批复竣工日期
		        								if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
		        									//填写实际交工日期和策划批复交工日期
		        									if (handoverDateActual != null && subApprovalHandoverDate != null) {
		        										//实际交工日期早于等于策划批复交工日期
		        										if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0 ) {
//		        											dbzjTzProManage.setPeriodFlag("正常完工");//已交工，已竣工
		        											strs[0] = "正常完工";
		        											//实际交工日期晚于策划批复交工日期
		        										}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
//		        											dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，已竣工
		        											strs[1] = "超期完工";
		        										}
		        									}
		        									//实际竣工日期晚于策划批复竣工日期
		        								}else if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
		        									//填写实际交工日期和策划批复交工日期
		        									if (handoverDateActual != null && subApprovalHandoverDate != null) {
		        										//实际交工日期早于等于策划批复交工日期
		        										if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0 ) {
//		        											dbzjTzProManage.setPeriodFlag("超期完工");//已交工，超期竣工
		        											strs[1] = "超期完工";
		        											//实际交工日期晚于策划批复交工日期
		        										}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
//		        											dbzjTzProManage.setPeriodFlag("超期完工");//超期交工，超期竣工
		        											strs[1] = "超期完工";
		        										}
		        									}
		        								}
		        							}
		        							//未填写实际竣工日期
		        						}else if (complateDateActual == null) {
		        							//填写了实际交工日期
		        							if (handoverDateActual != null) {
		        								//填写实际交工日期和策划批复交工日期
		        								if (handoverDateActual != null && subApprovalHandoverDate != null) {
		        									//实际交工日期早于等于策划批复交工日期
		        									if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0) {
		        										//填写策划批复竣工日期
		        										if (subApprovalCompleteDate != null) {
		        											//当前日期早于等于策划批复竣工日期
		        											if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
//		        												dbzjTzProManage.setPeriodFlag("建设中");//已交工，竣工中
		        												strs[2] = "建设中";
		        												//当前日期晚于策划批复竣工日期
		        											}else if (currDate.compareTo(subApprovalCompleteDate) > 0) {
//		        												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//已交工，应竣工未竣工
		        												strs[3] = "应竣工未竣工";
		        											}
		        										}
		        										//实际交工日期晚于策划批复交工日期
		        									}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
		        										//策划批复竣工日期
		        										if (subApprovalCompleteDate != null) {
		        											//当前日期早于等于策划批复竣工日期
		        											if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
//		        												dbzjTzProManage.setPeriodFlag("建设中");//超期交工，竣工中
		        												strs[2] = "建设中";
		        												//当前日期晚于策划批复竣工日期
		        											}else if (currDate.compareTo(subApprovalCompleteDate) > 0) {
//		        												dbzjTzProManage.setPeriodFlag("应竣工未竣工");//超期交工，应竣工未竣工
		        												strs[3] = "应竣工未竣工";
		        											}
		        										}
		        										
		        									}
		        								}
		        								//未填写实际交工日期
		        							}else if (handoverDateActual == null) {
		        								//填写策划批复交工日期
		        								if (subApprovalHandoverDate != null) {
		        									//当前日期早于等于策划批复交工日期
		        									if (currDate.compareTo(subApprovalHandoverDate) <= 0) {
//		        										dbzjTzProManage.setPeriodFlag("建设中");//建设中
		        										strs[2] = "建设中";
		        										//当前日期晚于策划批复交工日期
		        									}else if (currDate.compareTo(subApprovalHandoverDate) > 0) {
//		        										dbzjTzProManage.setPeriodFlag("应交工未交工");//应交工未交工
		        										strs[4] = "应交工未交工";
		        									}
		        								}
		        							}
		        						}
		        						
		        						//建设期结束标志为竣工
		        					}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
		        						//填写了实际竣工日期
		        						if (complateDateActual != null) {
		        							//填写了策划批复竣工日期
		        							if (subApprovalCompleteDate != null) {
		        								//实际竣工日期早于等于策划批复竣工日期
		        								if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
//		        									dbzjTzProManage.setPeriodFlag("正常完工");//已竣工
		        									strs[0] = "正常完工";
		        									//实际竣工日期晚于策划批复竣工日期
		        								}else if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
//		        									dbzjTzProManage.setPeriodFlag("超期完工");//超期竣工
		        									strs[1] = "超期完工";
		        								}
		        							}
		        							//未填写实际竣工日期	
		        						}else if (complateDateActual == null) {
		        							//填写了策划批复竣工日期
		        							if (subApprovalCompleteDate != null) {
		        								//当前日期早于等于策划批复竣工日期
		        								if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
//		        									dbzjTzProManage.setPeriodFlag("建设中");//建设中
		        									strs[2] = "建设中";
		        									//当前日期晚于策划批复竣工日期
		        								}else if (currDate.compareTo(subApprovalCompleteDate) > 0) {
//		        									dbzjTzProManage.setPeriodFlag("应竣工未竣工");//应竣工未竣
		        									strs[3] = "应竣工未竣工";
		        								}
		        								
		        							}
		        						}
		        					}

								}
							//项目下的子项目按照优先级，判断父项目的工期预警分类	
							for (int i = 0; i < strs.length; i++) {
								if (StrUtil.equals(strs[4], "应交工未交工")) {
									dbzjTzProManage.setPeriodFlag("应交工未交工");
								}else if (StrUtil.equals(strs[3], "应竣工未竣工") && StrUtil.equals(strs[4], "")) {
									dbzjTzProManage.setPeriodFlag("应竣工未竣工");
								}else if (StrUtil.equals(strs[2], "建设中") && StrUtil.equals(strs[4], "") && StrUtil.equals(strs[3], "")) {
									dbzjTzProManage.setPeriodFlag("建设中");
								}else if (StrUtil.equals(strs[1], "超期完工") && StrUtil.equals(strs[4], "") && StrUtil.equals(strs[3], "") && StrUtil.equals(strs[2], "")) {
									dbzjTzProManage.setPeriodFlag("超期完工");
								}else if (StrUtil.equals(strs[0], "正常完工") && StrUtil.equals(strs[4], "") && StrUtil.equals(strs[3], "") && StrUtil.equals(strs[2], "") && StrUtil.equals(strs[1], "")) {
									dbzjTzProManage.setPeriodFlag("正常完工");
								}
							}	
								
								
							}
						
						
						}
						
						
						
					}
					
					if (StrUtil.equals(dbzjTzProManage.getPeriodFlag(), "正常完工")) {
						count1 = count1 + 1;
					}
					if (StrUtil.equals(dbzjTzProManage.getPeriodFlag(), "超期完工")) {
						count2 = count2 + 1;
					}
					if (StrUtil.equals(dbzjTzProManage.getPeriodFlag(), "建设中")) {
						count3 = count3 + 1;
					}
					if (StrUtil.equals(dbzjTzProManage.getPeriodFlag(), "应竣工未竣工")) {
						count4 = count4 + 1;
					}
					if (StrUtil.equals(dbzjTzProManage.getPeriodFlag(), "应交工未交工")) {
						count5 = count5 + 1;
					}
        		}
				totalCount = count1 + count2 + count3 + count4 + count5;
        		BigDecimal dbcount1 = CalcUtils.calcDivide(new BigDecimal(count1), new BigDecimal(totalCount));
        		BigDecimal percent1 = CalcUtils.calcMultiply(dbcount1, new BigDecimal(100));
        		BigDecimal dbcount2 = CalcUtils.calcDivide(new BigDecimal(count2), new BigDecimal(totalCount));
        		BigDecimal percent2 = CalcUtils.calcMultiply(dbcount2, new BigDecimal(100));
        		BigDecimal dbcount3 = CalcUtils.calcDivide(new BigDecimal(count3), new BigDecimal(totalCount));
        		BigDecimal percent3 = CalcUtils.calcMultiply(dbcount3, new BigDecimal(100));
        		BigDecimal dbcount4 = CalcUtils.calcDivide(new BigDecimal(count4), new BigDecimal(totalCount));
        		BigDecimal percent4 = CalcUtils.calcMultiply(dbcount4, new BigDecimal(100));
        		BigDecimal dbcount5 = CalcUtils.calcDivide(new BigDecimal(count5), new BigDecimal(totalCount));
        		BigDecimal percent5 = CalcUtils.calcMultiply(dbcount5, new BigDecimal(100));
        		
        		JSONObject jsonObject = new JSONObject();
        		jsonObject.set("count1", count1);
        		jsonObject.set("percent1", percent1.setScale(2, BigDecimal.ROUND_HALF_UP));
        		jsonObject.set("count2", count2);
        		jsonObject.set("percent2", percent2.setScale(2, BigDecimal.ROUND_HALF_UP));
        		jsonObject.set("count3", count3);
        		jsonObject.set("percent3", percent3.setScale(2, BigDecimal.ROUND_HALF_UP));
        		jsonObject.set("count4", count4);
        		jsonObject.set("percent4", percent4.setScale(2, BigDecimal.ROUND_HALF_UP));
        		jsonObject.set("count5", count5);
        		jsonObject.set("percent5", percent5.setScale(2, BigDecimal.ROUND_HALF_UP));
        		jsonArray.add(jsonObject);
        	}
		return repEntity.ok(jsonArray);
	}
	
	/**
	 * 
	 * 所有项目工期状态饼图各部分弹出页面
	 */
	@Override
	public ResponseEntity getHomeProjectStatusAllProjectAlertPage(ZjTzProManage zjTzProManage) {
		if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        // 数据装载（与上面的表头对应）
        List<ZjTzProManage> projectStatusList = zjTzProManageMapper.selectHomeProjectStatus(zjTzProManage);
        if(projectStatusList != null && projectStatusList.size()>0) {
        	for (ZjTzProManage dbzjTzProManage:projectStatusList) {
        		//实际竣工日期
				Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
				//合同约定竣工日期
				Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
				//实际交工日期
				Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
				//合同约定交工日期
				Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
				//策划批复竣工日期
				Date approvalCompleteDate = dbzjTzProManage.getApprovalCompleteDate();
				//策划批复交工日期
				Date approvalHandoverDate = dbzjTzProManage.getApprovalHandoverDate();
				//实际开工日期
		    	Date actualDate = dbzjTzProManage.getActualDate();
				//当前日期
				Date currDate = dbzjTzProManage.getCurrDate();
				
				ZjTzProSubprojectInfo zjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
				zjTzProSubprojectInfo.setProjectId(dbzjTzProManage.getProjectId());
				List<ZjTzProSubprojectInfo> zjTzProSubprojectInfoList = zjTzProSubprojectInfoMapper.selectByZjTzProSubprojectInfoList(zjTzProSubprojectInfo);
            			//预警日期类型选择合同约定
            			if (StrUtil.equals(zjTzProManage.getDateType(), "1")) {
            				dbzjTzProManage.setDateType(zjTzProManage.getDateType());
            				//正常完工
            				if (StrUtil.equals(zjTzProManage.getStatus(), "1")) {
            					dbzjTzProManage.setStatus(zjTzProManage.getStatus());
            					//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							// 填写了实际竣工日期
            							if (complateDateActrual != null) {
            								//填写了合同约定竣工日期
            								if (complateDatePlan != null) {
            									//实际竣工日期早于等于合同约定竣工日期
            									if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
            										//填写了实际交工日期和合同约定交工日期
            										if (handoverDateActrual != null && handoverDatePlan!= null) {
            											//实际交工日期早于等于合同约定交工日期
            											if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
            												//工期状态：已交工，已竣工
            												dbzjTzProManage.setPeriodFlag("已交工，已竣工");
            												dbzjTzProManage.setPeriodId("5");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            											}
            										}
            									}
            								}
            							}
            							//建设期结束标志为竣工
            						}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            							//填写了实际竣工日期
            							if (complateDateActrual != null) {
            								//填写了合同约定竣工日期
            								if (complateDatePlan != null) {
            									//实际竣工日期早于等于合同约定竣工日期
            									if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
            										//工期状态：已竣工
            										dbzjTzProManage.setPeriodFlag("已竣工");
            										dbzjTzProManage.setPeriodId("1");
            										jsonObject = showProjectContent(dbzjTzProManage);
            										jsonArray.add(jsonObject);
            									}
            								}
            							}
            						}
            						//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//合同约定竣工日期
											Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//合同约定交工日期
											Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
											//建设期结束标志为交工
											if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
												
												// 填写了实际竣工日期
												if (complateDateActual != null) {
													//填写了合同约定竣工日期
													if (subComplateDatePlan != null) {
														//实际竣工日期早于等于合同约定竣工日期
														if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
															//填写了实际交工日期和合同约定交工日期
															if (handoverDateActual != null && subHandoverDatePlan!= null) {
																//实际交工日期早于等于合同约定交工日期
																if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0 ) {
																	//工期状态：已交工，已竣工
																	dbzjTzProManage.setPeriodFlag("已交工，已竣工");
																	dbzjTzProManage.setPeriodId("5");
																	jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																	jsonArray.add(jsonObject);
																}
															}
														}
													}
												}
												//建设期结束标志为竣工
											}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
												//填写了实际竣工日期
												if (complateDateActual != null) {
													//填写了合同约定竣工日期
													if (subComplateDatePlan != null) {
														//实际竣工日期早于等于合同约定竣工日期
														if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
															//工期状态：已竣工
															dbzjTzProManage.setPeriodFlag("已竣工");
															dbzjTzProManage.setPeriodId("1");
															jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
															jsonArray.add(jsonObject);
														}
													}
												}
											}
										}
									}
									
								}
            					//超期完工
            				}else if (StrUtil.equals(zjTzProManage.getStatus(), "2")) {
            					dbzjTzProManage.setStatus(zjTzProManage.getStatus());
            					//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							// 填写了实际竣工日期
            							if (complateDateActrual != null) {
            								// 填写了合同约定竣工日期
            								if (complateDatePlan != null) {
            									//实际竣工日期早于等于合同约定竣工日期
            									if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
            										//填写了实际交工日期和合同约定交工日期
            										if (handoverDateActrual != null && handoverDatePlan != null) {
            											//实际交工日期晚于应交工日期
            											if (handoverDateActrual.compareTo(handoverDatePlan) > 0 ) {
            												//工期状态：超期交工，已竣工
            												dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
            												dbzjTzProManage.setPeriodId("6");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            												//实际交工日期晚于应交工日期
            											}
            										}
            									}//实际竣工日期晚于合同约定竣工日期
            									else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
            										//填写了实际交工日期和合同约定交工日期
            										if (handoverDateActrual != null && handoverDatePlan != null) {
            											//实际交工日期早于等于合同约定交工日期
            											if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
            												//工期状态：已交工，超期竣工
            												dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
            												dbzjTzProManage.setPeriodId("7");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            												//实际交工日期晚于合同约定交工日期
            											}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
            												//工期状态：超期交工，超期竣工
            												dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
            												dbzjTzProManage.setPeriodId("8");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            											}
            										}
            									}
            								}
            							}
            					
								}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            						//填写了实际竣工日期
            						if (complateDateActrual != null) {
            							//填写了合同约定竣工日期
            							if (complateDatePlan != null) {
            								//实际竣工日期晚于合同约定竣工日期
            								if (complateDateActrual.compareTo(complateDatePlan) > 0) {
            									//工期状态：超期竣工
            									dbzjTzProManage.setPeriodFlag("超期竣工");
            									dbzjTzProManage.setPeriodId("2");
            									jsonObject = showProjectContent(dbzjTzProManage);
            									jsonArray.add(jsonObject);
            								}
            							}
            						}
            						
            					}
            						//工期分析主体为子项目	
            				}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
            					if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
									for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
										//实际竣工日期
										Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
										//合同约定竣工日期
										Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
										//实际交工日期
										Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
										//合同约定交工日期
										Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
										//建设期结束标志为交工
										if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
											// 填写了实际竣工日期
											if (complateDateActual != null) {
												// 填写了合同约定竣工日期
												if (subComplateDatePlan != null) {
													//实际竣工日期早于等于合同约定竣工日期
													if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
														//填写了实际交工日期和合同约定交工日期
														if (handoverDateActual != null && subHandoverDatePlan != null) {
															//实际交工日期晚于合同约定交工日期
															if (handoverDateActual.compareTo(subHandoverDatePlan) > 0 ) {
																//工期状态：超期交工，已竣工
																dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
																dbzjTzProManage.setPeriodId("6");
																jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																jsonArray.add(jsonObject);
																//实际交工日期晚于应交工日期
															}
														}
													}//实际竣工日期晚于合同约定竣工日期
													else if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
														//填写了实际交工日期和合同约定交工日期
														if (handoverDateActual != null && subHandoverDatePlan != null) {
															//实际交工日期早于等于合同约定交工日期
															if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0 ) {
																//工期状态：已交工，超期竣工
																dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
																dbzjTzProManage.setPeriodId("7");
																jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																jsonArray.add(jsonObject);
																//实际交工日期晚于合同约定交工日期
															}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
																//工期状态：超期交工，超期竣工
																dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
																dbzjTzProManage.setPeriodId("8");
																jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
													}
												}
											}
											
										}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
											//填写了实际竣工日期
											if (complateDateActual != null) {
												//填写了合同约定竣工日期
												if (subComplateDatePlan != null) {
													//实际竣工日期晚于合同约定竣工日期
													if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
														//工期状态：超期竣工
														dbzjTzProManage.setPeriodFlag("超期竣工");
														dbzjTzProManage.setPeriodId("2");
														jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
														jsonArray.add(jsonObject);
													}
												}
											}
										}
									}
								}
							}
            					//建设中
							}else if (StrUtil.equals(zjTzProManage.getStatus(), "3")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
								if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
									//建设期结束标志为交工
									if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
										// 未填写实际竣工日期
										if (complateDateActrual == null) {
											//填写了实际交工日期
											if (handoverDateActrual != null) {
												//填写了合同约定交工日期
												if (handoverDatePlan != null) {
													//实际交工日期早于等于合同约定交工日期
													if (handoverDateActrual.compareTo(handoverDatePlan) <= 0) {
														//填写了合同约定竣工日期
														if (complateDatePlan != null) {
															//当前日期早于等于合同约定竣工日期
															if (currDate.compareTo(complateDatePlan) <= 0) {
																//工期状态：已交工，竣工中
																dbzjTzProManage.setPeriodFlag("已交工，竣工中");
																dbzjTzProManage.setPeriodId("9");
																jsonObject = showProjectContent(dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
														//实际交工日期晚于合同约定交工日期
													}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
														//填写了合同约定竣工日期
														if (complateDatePlan != null) {
															//当前日期早于等于合同约定竣工日期
															if (currDate.compareTo(complateDatePlan) <= 0) {
																//工期状态：超期交工，竣工中
																dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
																dbzjTzProManage.setPeriodId("10");
																jsonObject = showProjectContent(dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
														
													}
												}
												//未填写实际交工日期
											}else if (handoverDateActrual  == null) {
												//合同约定交工日期
												if (handoverDatePlan != null) {
													//当前日期早于等于合同约定交工日期
													if (currDate.compareTo(handoverDatePlan) <= 0) {
														//工期状态：建设中
														dbzjTzProManage.setPeriodFlag("建设中");
														dbzjTzProManage.setPeriodId("13");
														jsonObject = showProjectContent(dbzjTzProManage);
														jsonArray.add(jsonObject);
													}
												}
											}
										}
										//建设期结束标志为竣工
									}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
										//未填写实际竣工日期
										if (complateDateActrual  == null) {
											//填写了合同约定竣工日期
											if (complateDatePlan  != null) {
												//当前日期早于等于合同约定竣工日期
												if (currDate.compareTo(complateDatePlan) <= 0) {
													//工期状态：建设中
													dbzjTzProManage.setPeriodFlag("建设中");
													dbzjTzProManage.setPeriodId("3");
													jsonObject = showProjectContent(dbzjTzProManage);
													jsonArray.add(jsonObject);
												}
												
											}
										}
									}
								//工期分析主体为子项目	
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//合同约定竣工日期
											Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//合同约定交工日期
											Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
											
											//建设期结束标志为交工
											if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
												// 未填写实际竣工日期
												if (complateDateActual == null) {
													//填写了实际交工日期
													if (handoverDateActual != null) {
														//填写了合同约定交工日期
														if (subHandoverDatePlan != null) {
															//实际交工日期早于等于合同约定交工日期
															if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0) {
																//填写了合同约定竣工日期
																if (subComplateDatePlan != null) {
																	//当前日期早于等于合同约定竣工日期
																	if (currDate.compareTo(subComplateDatePlan) <= 0) {
																		//工期状态：已交工，竣工中
																		dbzjTzProManage.setPeriodFlag("已交工，竣工中");
																		dbzjTzProManage.setPeriodId("9");
																		jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																		jsonArray.add(jsonObject);
																	}
																}
																//实际交工日期晚于合同约定交工日期
															}else if (handoverDateActual.compareTo(subHandoverDatePlan ) > 0) {
																//填写了合同约定竣工日期
																if (subComplateDatePlan != null) {
																	//当前日期早于等于合同约定竣工日期
																	if (currDate.compareTo(subComplateDatePlan ) <= 0) {
																		//工期状态：超期交工，竣工中
																		dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
																		dbzjTzProManage.setPeriodId("10");
																		jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																		jsonArray.add(jsonObject);
																	}
																}
																
															}
														}
														//未填写实际交工日期
													}else if (handoverDateActual == null) {
														//合同约定交工日期
														if (subHandoverDatePlan != null) {
															//当前日期早于等于合同约定交工日期
															if (currDate.compareTo(subHandoverDatePlan ) <= 0) {
																//工期状态：建设中
																dbzjTzProManage.setPeriodFlag("建设中");
																dbzjTzProManage.setPeriodId("13");
																jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
													}
										}
										}
										//建设期结束标志为竣工
									else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
										//未填写实际竣工日期
										if (complateDateActual == null) {
											//填写了合同约定竣工日期
											if (subComplateDatePlan  != null) {
												//当前日期早于等于合同约定竣工日期
												if (currDate.compareTo(subComplateDatePlan) <= 0) {
													//工期状态：建设中
													dbzjTzProManage.setPeriodFlag("建设中");
													dbzjTzProManage.setPeriodId("3");
													jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
													jsonArray.add(jsonObject);
												}
												
											}
										}
										
										
									}
									
								}
								}
								}//应竣工未竣工
							}else if (StrUtil.equals(zjTzProManage.getStatus(), "4")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
								if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
									//建设期结束标志为交工
									if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
										
										// 未填写了实际竣工日期
										if (complateDateActrual == null) {
											//填写了实际交工日期
											if (handoverDateActrual  != null) {
												//填写了合同约定交工日期
												if (handoverDatePlan  != null) {
													//实际交工日期早于等于合同约定交工日期
													if (handoverDateActrual.compareTo(handoverDatePlan) <= 0) {
														//填写了合同约定竣工日期
														if (complateDatePlan != null) {
															//当前日期晚于合同约定竣工日期
															if (currDate.compareTo(complateDatePlan) > 0) {
																//工期状态：已交工，应竣工未竣工
																dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
																dbzjTzProManage.setPeriodId("11");
																jsonObject = showProjectContent(dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
														//实际交工日期晚于合同约定交工日期
													}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
														//填写了合同约定竣工日期
														if (complateDatePlan != null) {
															//当前日期晚于合同约定竣工日期
															if (currDate.compareTo(complateDatePlan) > 0) {
																//工期状态：超期交工，应竣工未竣工
																dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
																dbzjTzProManage.setPeriodId("12");
																jsonObject = showProjectContent(dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
														
													}
												}
											}
										}
										//建设期结束标志为竣工
									}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
										//未填写实际竣工日期
										if (complateDateActrual  == null) {
											//填写了合同约定竣工日期
											if (complateDatePlan != null) {
												//当前日期晚于合同约定竣工日期
												if (currDate.compareTo(complateDatePlan) > 0) {
													//工期状态：应竣工未竣工
													dbzjTzProManage.setPeriodFlag("应竣工未竣工");
													dbzjTzProManage.setPeriodId("4");
													jsonObject = showProjectContent(dbzjTzProManage);
													jsonArray.add(jsonObject);
												}
												
											}
										}
										
										
									}
									
								//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//合同约定竣工日期
											Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//合同约定交工日期
											Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
											//建设期结束标志为交工
											if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
												
												// 未填写了实际竣工日期
												if (complateDateActual  == null) {
													//填写了实际交工日期
													if (handoverDateActual  != null) {
														//填写了合同约定交工日期
														if (subHandoverDatePlan  != null) {
															//实际交工日期早于等于合同约定交工日期
															if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0) {
																//填写了合同约定竣工日期
																if (subComplateDatePlan != null) {
																	//当前日期晚于合同约定竣工日期
																	if (currDate.compareTo(subComplateDatePlan) > 0) {
																		//工期状态：已交工，应竣工未竣工
																		dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
																		dbzjTzProManage.setPeriodId("11");
																		jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																		jsonArray.add(jsonObject);
																	}
																}
																//实际交工日期晚于合同约定交工日期
															}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
																//填写了合同约定竣工日期
																if (subComplateDatePlan  != null) {
																	//当前日期晚于合同约定竣工日期
																	if (currDate.compareTo(subComplateDatePlan ) > 0) {
																		//工期状态：超期交工，应竣工未竣工
																		dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
																		dbzjTzProManage.setPeriodId("12");
																		jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																		jsonArray.add(jsonObject);
																	}
																}
																
															}
														}
													}
												}
												//建设期结束标志为竣工
											}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
												//未填写实际竣工日期
												if (complateDateActual  == null) {
													//填写了合同约定竣工日期
													if (subComplateDatePlan  != null) {
														//当前日期晚于合同约定竣工日期
														if (currDate.compareTo(subComplateDatePlan ) > 0) {
															//工期状态：应竣工未竣工
															dbzjTzProManage.setPeriodFlag("应竣工未竣工");
															dbzjTzProManage.setPeriodId("4");
															jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
															jsonArray.add(jsonObject);
														}
														
													}
												}
												
												
											}
										}
										}
									
								}
								//应交工未交工
							}else if (StrUtil.equals(zjTzProManage.getStatus(), "5")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
								if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
									//建设期结束标志为交工
									if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
										
										// 未填写实际竣工日期
										if (complateDateActrual  == null) {
											//未填写实际交工日期
											if (handoverDateActrual  == null) {
												//填写了合同约定交工日期
												if (handoverDatePlan  != null) {
													//当前日期晚于合同约定交工日期
													if (currDate.compareTo(handoverDatePlan) > 0) {
														//工期状态：应交工未交工
														dbzjTzProManage.setPeriodFlag("应交工未交工");
														dbzjTzProManage.setPeriodId("14");
														jsonObject = showProjectContent(dbzjTzProManage);
														jsonArray.add(jsonObject);
													}
												}
											}
										}
										
									}
								//工期分析主体为子项目	
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//合同约定竣工日期
											Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//合同约定交工日期
											Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
											//建设期结束标志为交工
											if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
												// 未填写实际竣工日期
												if (complateDateActual  == null) {
													//未填写实际交工日期
													if (handoverDateActual  == null) {
														//填写了合同约定交工日期
														if (subHandoverDatePlan  != null) {
															//当前日期晚于合同约定交工日期
															if (currDate.compareTo(subHandoverDatePlan) > 0) {
																//工期状态：应交工未交工
																dbzjTzProManage.setPeriodFlag("应交工未交工");
																dbzjTzProManage.setPeriodId("14");
																jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																jsonArray.add(jsonObject);
															}
														}
													}
												}
												
											}
											
										}
									}
								}
								
            			}
            				//预警日期类型选择策划批复
            			}else if (StrUtil.equals(zjTzProManage.getDateType(), "2")) {
            				dbzjTzProManage.setDateType(zjTzProManage.getDateType());
            				//正常完工
            				if (StrUtil.equals(zjTzProManage.getStatus(), "1")) {
            					dbzjTzProManage.setStatus(zjTzProManage.getStatus());
            					//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							
            							// 填写了实际竣工日期
            							if (complateDateActrual != null) {
            								//填写了策划批复竣工日期
            								if (approvalCompleteDate != null) {
            									//实际竣工日期早于等于策划批复竣工日期
            									if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
            										//填写了实际交工日期和策划批复交工日期
            										if (handoverDateActrual  != null && approvalHandoverDate != null) {
            											//实际交工日期早于等于策划批复交工日期
            											if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0 ) {
            												//工期状态：已交工，已竣工
            												dbzjTzProManage.setPeriodFlag("已交工，已竣工");
            												dbzjTzProManage.setPeriodId("5");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            											}
            										}
            									}
            								}
            							}
            							
            							
            							//建设期结束标志为竣工
            						}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            							//填写了实际竣工日期
            							if (complateDateActrual != null) {
            								//填写了策划批复竣工日期
            								if (approvalCompleteDate != null) {
            									//实际竣工日期早于等于策划批复竣工日期
            									if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
            										//工期状态：已竣工
            										dbzjTzProManage.setPeriodFlag("已竣工");
            										dbzjTzProManage.setPeriodId("1");
            										jsonObject = showProjectContent(dbzjTzProManage);
            										jsonArray.add(jsonObject);
            									}
            								}
            							}
            							
            							
            						}
            						//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//策划批复竣工日期
											Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
											//策划批复交工日期
											Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
											//建设期结束标志为交工
											if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
												
												// 填写了实际竣工日期
												if (complateDateActual  != null) {
													//填写了策划批复竣工日期
													if (subApprovalCompleteDate != null) {
														//实际竣工日期早于等于策划批复竣工日期
														if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
															//填写了实际交工日期和策划批复交工日期
															if (handoverDateActual != null && subApprovalHandoverDate!= null) {
																//实际交工日期早于等于策划批复交工日期
																if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0 ) {
																	//工期状态：已交工，已竣工
																	dbzjTzProManage.setPeriodFlag("已交工，已竣工");
																	dbzjTzProManage.setPeriodId("5");
																	jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																	jsonArray.add(jsonObject);
																}
															}
														}
													}
												}
												//建设期结束标志为竣工
											}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
												
												//填写了实际竣工日期
												if (complateDateActual  != null) {
													//填写了策划批复竣工日期
													if (subApprovalCompleteDate  != null) {
														//实际竣工日期早于等于策划批复竣工日期
														if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
															//工期状态：已竣工
															dbzjTzProManage.setPeriodFlag("已竣工");
															dbzjTzProManage.setPeriodId("1");
															jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
															jsonArray.add(jsonObject);
														}
													}
												}
												
												
											}
										}
									}
								}
            					
            					//超期完工
            				}else if (StrUtil.equals(zjTzProManage.getStatus(), "2")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							
            							// 填写了实际竣工日期
            							if (complateDateActrual != null) {
            								// 填写了策划批复竣工日期
            								if (approvalCompleteDate != null) {
            									//实际竣工日期早于等于策划批复竣工日期
            									if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
            										//填写了实际交工日期和策划批复交工日期
            										if (handoverDateActrual != null && approvalHandoverDate != null) {
            											//实际交工日期晚于策划批复交工日期
            											if (handoverDateActrual.compareTo(approvalHandoverDate) > 0 ) {
            												//工期状态：超期交工，已竣工
            												dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
            												dbzjTzProManage.setPeriodId("6");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            												//实际交工日期晚于应交工日期
            											}
            										}
            									}//实际竣工日期晚于策划批复竣工日期
            									else if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
            										//填写了实际交工日期和策划批复交工日期
            										if (handoverDateActrual != null && approvalHandoverDate != null) {
            											//实际交工日期早于等于策划批复交工日期
            											if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0 ) {
            												//工期状态：已交工，超期竣工
            												dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
            												dbzjTzProManage.setPeriodId("7");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            												//实际交工日期晚于策划批复交工日期
            											}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
            												//工期状态：超期交工，超期竣工
            												dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
            												dbzjTzProManage.setPeriodId("8");
            												jsonObject = showProjectContent(dbzjTzProManage);
            												jsonArray.add(jsonObject);
            											}
            										}
            									}
            								}
            							}
            							
            							//建设期结束标志为竣工
            						}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            							
            							//填写了实际竣工日期
            							if (complateDateActrual != null) {
            								//填写了策划批复竣工日期
            								if (approvalCompleteDate != null) {
            									//实际竣工日期晚于策划批复竣工日期
            									if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
            										//工期状态：超期竣工
                									dbzjTzProManage.setPeriodFlag("超期竣工");
                									dbzjTzProManage.setPeriodId("2");
            										jsonObject = showProjectContent(dbzjTzProManage);
            										jsonArray.add(jsonObject);
            									}
            								}
            							}
            							
            						}
            					//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//策划批复竣工日期
											Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
											//策划批复交工日期
											Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
											//建设期结束标志为交工
											if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
												// 填写了实际竣工日期
												if (complateDateActual != null) {
													// 填写了策划批复竣工日期
													if (subApprovalCompleteDate != null) {
														//实际竣工日期早于等于策划批复竣工日期
														if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
															//填写了实际交工日期和策划批复交工日期
															if (handoverDateActual != null && subApprovalHandoverDate != null) {
																//实际交工日期晚于策划批复交工日期
																if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0 ) {
																	//工期状态：超期交工，已竣工
		            												dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
		            												dbzjTzProManage.setPeriodId("6");
																	jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																	jsonArray.add(jsonObject);
																	//实际交工日期晚于应交工日期
																}
															}
														}//实际竣工日期晚于策划批复竣工日期
														else if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
															//填写了实际交工日期和策划批复交工日期
															if (handoverDateActual  != null && subApprovalHandoverDate  != null) {
																//实际交工日期早于等于策划批复交工日期
																if (handoverDateActual .compareTo(subApprovalHandoverDate) <= 0 ) {
																	//工期状态：已交工，超期竣工
		            												dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
		            												dbzjTzProManage.setPeriodId("7");
																	jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																	jsonArray.add(jsonObject);
																	//实际交工日期晚于策划批复交工日期
																}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
																	//工期状态：超期交工，超期竣工
		            												dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
		            												dbzjTzProManage.setPeriodId("8");
																	jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
																	jsonArray.add(jsonObject);
																}
															}
														}
													}
												}
												
												//建设期结束标志为竣工
											}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
												
												//填写了实际竣工日期
												if (complateDateActual  != null) {
													//填写了策划批复竣工日期
													if (subApprovalCompleteDate  != null) {
														//实际竣工日期晚于策划批复竣工日期
														if (complateDateActual .compareTo(subApprovalCompleteDate ) > 0) {
															//工期状态：超期竣工
			            									dbzjTzProManage.setPeriodFlag("超期竣工");
			            									dbzjTzProManage.setPeriodId("2");
															jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
															jsonArray.add(jsonObject);
														}
													}
												}
												
											}
										}
									}
									
								}
								
            					//建设中
							}else if (StrUtil.equals(zjTzProManage.getStatus(), "3")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							
            							// 未填写实际竣工日期
            							if (complateDateActrual  == null) {
            								//填写了实际交工日期
            								if (handoverDateActrual != null) {
            									//填写了策划批复交工日期
            									if (approvalHandoverDate != null) {
            										//实际交工日期早于等于策划批复交工日期
            										if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0) {
            											//填写了策划批复竣工日期
            											if (approvalCompleteDate != null) {
            												//当前日期早于等于策划批复竣工日期
            												if (currDate.compareTo(approvalCompleteDate) <= 0) {
            													//工期状态：已交工，竣工中
																dbzjTzProManage.setPeriodFlag("已交工，竣工中");
																dbzjTzProManage.setPeriodId("9");
            													jsonObject = showProjectContent(dbzjTzProManage);
            													jsonArray.add(jsonObject);
            												}
            											}
            											//实际交工日期晚于策划批复交工日期
            										}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
            											//填写了策划批复竣工日期
            											if (approvalCompleteDate != null) {
            												//当前日期早于等于策划批复竣工日期
            												if (currDate.compareTo(approvalCompleteDate) <= 0) {
            													//工期状态：超期交工，竣工中
																dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
																dbzjTzProManage.setPeriodId("10");
            													jsonObject = showProjectContent(dbzjTzProManage);
            													jsonArray.add(jsonObject);
            												}
            											}
            											
            										}
            									}
            									//未填写实际交工日期
            								}else if (handoverDateActrual== null) {
            									//策划批复交工日期
            									if (approvalHandoverDate != null) {
            										//当前日期早于等于策划批复交工日期
            										if (currDate.compareTo(approvalHandoverDate) <= 0) {
            											//工期状态：建设中
														dbzjTzProManage.setPeriodFlag("建设中");
														dbzjTzProManage.setPeriodId("13");
            											jsonObject = showProjectContent(dbzjTzProManage);
            											jsonArray.add(jsonObject);
            										}
            									}
            								}
            							}
            							
            							
            							//建设期结束标志为竣工
            						}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            							//未填写实际竣工日期
            							if (complateDateActrual == null) {
            								//填写了策划批复竣工日期
            								if (approvalCompleteDate != null) {
            									//当前日期早于等于策划批复竣工日期
            									if (currDate.compareTo(approvalCompleteDate) <= 0) {
            										//工期状态：建设中
													dbzjTzProManage.setPeriodFlag("建设中");
													dbzjTzProManage.setPeriodId("3");
            										jsonObject = showProjectContent(dbzjTzProManage);
            										jsonArray.add(jsonObject);
            									}
            									
            								}
            							}
            							
            							
            						}
            					//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//策划批复竣工日期
											Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
											//策划批复交工日期
											Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
											//建设期结束标志为交工
		            						if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
		            							
		            							// 未填写实际竣工日期
		            							if (complateDateActual  == null) {
		            								//填写了实际交工日期
		            								if (handoverDateActual != null) {
		            									//填写了策划批复交工日期
		            									if (subApprovalHandoverDate != null) {
		            										
		            										//实际交工日期早于等于策划批复交工日期
		            										if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0) {
		            											//填写了策划批复竣工日期
		            											if (subApprovalCompleteDate != null) {
		            												//当前日期早于等于策划批复竣工日期
		            												if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
		            													//工期状态：已交工，竣工中
																		dbzjTzProManage.setPeriodFlag("已交工，竣工中");
																		dbzjTzProManage.setPeriodId("9");
		            													jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            													jsonArray.add(jsonObject);
		            												}
		            											}
		            											//实际交工日期晚于策划批复交工日期
		            										}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
		            											//填写了策划批复竣工日期
		            											if (subApprovalCompleteDate != null) {
		            												//当前日期早于等于策划批复竣工日期
		            												if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
		            													//工期状态：超期交工，竣工中
																		dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
																		dbzjTzProManage.setPeriodId("10");
		            													jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            													jsonArray.add(jsonObject);
		            												}
		            											}
		            											
		            										}
		            									}
		            									//未填写实际交工日期
		            								}else if (handoverDateActual== null) {
		            									//策划批复交工日期
		            									if (subApprovalHandoverDate != null) {
		            										//当前日期早于等于策划批复交工日期
		            										if (currDate.compareTo(subApprovalHandoverDate) <= 0) {
		            											//工期状态：建设中
																dbzjTzProManage.setPeriodFlag("建设中");
																dbzjTzProManage.setPeriodId("13");
		            											jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            											jsonArray.add(jsonObject);
		            										}
		            									}
		            								}
		            							}
		            							
		            							
		            							//建设期结束标志为竣工
		            						}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
		            							//未填写实际竣工日期
		            							if (complateDateActual  == null) {
		            								//填写了策划批复竣工日期
		            								if (subApprovalCompleteDate  != null) {
		            									//当前日期早于等于策划批复竣工日期
		            									if (currDate.compareTo(subApprovalCompleteDate ) <= 0) {
		            										//工期状态：建设中
															dbzjTzProManage.setPeriodFlag("建设中");
															dbzjTzProManage.setPeriodId("3");
		            										jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            										jsonArray.add(jsonObject);
		            									}
		            									
		            								}
		            							}
		            							
		            							
		            						}
										}
									}
								}
								//应竣工未竣工
							}else if (StrUtil.equals(zjTzProManage.getStatus(), "4")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							
            							// 未填写了实际竣工日期
            							if (complateDateActrual  == null) {
            								//填写了实际交工日期
            								if (handoverDateActrual != null) {
            									//填写了策划批复交工日期
            									if (approvalHandoverDate != null) {
            										//实际交工日期早于等于策划批复交工日期
            										if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0) {
            											//填写了策划批复竣工日期
            											if (approvalCompleteDate != null) {
            												//当前日期晚于策划批复竣工日期
            												if (currDate.compareTo(approvalCompleteDate) > 0) {
            													//工期状态：已交工，应竣工未竣工
																dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
																dbzjTzProManage.setPeriodId("11");
            													jsonObject = showProjectContent(dbzjTzProManage);
            													jsonArray.add(jsonObject);
            												}
            											}
            											//实际交工日期晚于策划批复交工日期
            										}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
            											//填写了策划批复竣工日期
            											if (approvalCompleteDate != null) {
            												//当前日期晚于策划批复竣工日期
            												if (currDate.compareTo(approvalCompleteDate) > 0) {
            													//工期状态：超期交工，应竣工未竣工
																dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
																dbzjTzProManage.setPeriodId("12");
            													jsonObject = showProjectContent(dbzjTzProManage);
            													jsonArray.add(jsonObject);
            												}
            											}
            											
            										}
            									}
            								}
            							}
            							
            							//建设期结束标志为竣工
            						}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            							//未填写实际竣工日期
            							if (complateDateActrual  == null) {
            								//填写了策划批复竣工日期
            								if (approvalCompleteDate != null) {
            									//当前日期晚于策划批复竣工日期
            									if (currDate.compareTo(approvalCompleteDate) > 0) {
            										//工期状态：应竣工未竣工
													dbzjTzProManage.setPeriodFlag("应竣工未竣工");
													dbzjTzProManage.setPeriodId("4");
            										jsonObject = showProjectContent(dbzjTzProManage);
            										jsonArray.add(jsonObject);
            									}
            									
            								}
            							}
            							
            							
            						}
            					//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//实际交工日期
											Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//策划批复竣工日期
											Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
											//策划批复交工日期
											Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
											//建设期结束标志为交工
		            						if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
		            							
		            							// 未填写了实际竣工日期
		            							if (complateDateActual  == null) {
		            								//填写了实际交工日期
		            								if (handoverDateActual != null) {
		            									//填写了策划批复交工日期
		            									if (subApprovalHandoverDate != null) {
		            										//实际交工日期早于等于策划批复交工日期
		            										if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0) {
		            											//填写了策划批复竣工日期
		            											if (subApprovalCompleteDate != null) {
		            												//当前日期晚于策划批复竣工日期
		            												if (currDate.compareTo(subApprovalCompleteDate) > 0) {
		            													//工期状态：已交工，应竣工未竣工
																		dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
																		dbzjTzProManage.setPeriodId("11");
		            													jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            													jsonArray.add(jsonObject);
		            												}
		            											}
		            											//实际交工日期晚于策划批复交工日期
		            										}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
		            											//填写了策划批复竣工日期
		            											if (subApprovalCompleteDate != null) {
		            												//当前日期晚于策划批复竣工日期
		            												if (currDate.compareTo(subApprovalCompleteDate) > 0) {
		            													//工期状态：超期交工，应竣工未竣工
																		dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
																		dbzjTzProManage.setPeriodId("12");
		            													jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            													jsonArray.add(jsonObject);
		            												}
		            											}
		            											
		            										}
		            									}
		            								}
		            							}
		            							
		            							//建设期结束标志为竣工
		            						}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
		            							//未填写实际竣工日期
		            							if (complateDateActual  == null) {
		            								//填写了策划批复竣工日期
		            								if (subApprovalCompleteDate  != null) {
		            									//当前日期晚于策划批复竣工日期
		            									if (currDate.compareTo(subApprovalCompleteDate ) > 0) {
		            										//工期状态：应竣工未竣工
															dbzjTzProManage.setPeriodFlag("应竣工未竣工");
															dbzjTzProManage.setPeriodId("4");
		            										jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            										jsonArray.add(jsonObject);
		            									}
		            									
		            								}
		            							}
		            							
		            							
		            						}
										}
									}
								}
								//应交工未交工
							}else if (StrUtil.equals(zjTzProManage.getStatus(), "5")) {
								dbzjTzProManage.setStatus(zjTzProManage.getStatus());
								//工期分析主体为项目
            					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            						//建设期结束标志为交工
            						if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            							// 未填写实际竣工日期
            							if (complateDateActrual  == null) {
            								//未填写实际交工日期
            								if (handoverDateActrual  == null) {
            									//填写了策划批复交工日期
            									if (approvalHandoverDate  != null) {
            										//当前日期晚于策划批复交工日期
            										if (currDate.compareTo(approvalHandoverDate) > 0) {
            											//工期状态：应交工未交工
														dbzjTzProManage.setPeriodFlag("应交工未交工");
														dbzjTzProManage.setPeriodId("14");
            											jsonObject = showProjectContent(dbzjTzProManage);
            											jsonArray.add(jsonObject);
            										}
            									}
            								}
            							}
            							
            						}
            					//工期分析主体为子项目
								}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
									if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
										for (ZjTzProSubprojectInfo dbzjTzProSubprojectInfo : zjTzProSubprojectInfoList) {
											//实际竣工日期
											Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
											//实际交工日期
											Date handoverDateActual  = dbzjTzProSubprojectInfo.getHandoverDateActual();
											//策划批复竣工日期
											Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
											//策划批复交工日期
											Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
											//建设期结束标志为交工
		            						if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
		            							// 未填写实际竣工日期
		            							if (complateDateActual  == null) {
		            								//未填写实际交工日期
		            								if (handoverDateActual   == null) {
		            									//填写了策划批复交工日期
		            									if (subApprovalHandoverDate  != null) {
		            										//当前日期晚于策划批复交工日期
		            										if (currDate.compareTo(subApprovalHandoverDate) > 0) {
		            											//工期状态：应交工未交工
																dbzjTzProManage.setPeriodFlag("应交工未交工");
																dbzjTzProManage.setPeriodId("14");
		            											jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            											jsonArray.add(jsonObject);
		            										}
		            									}
		            								}
		            							}
		            							
		            						}
										}
									}
								}
							}
						
            			
						}
            			
            			
            			
				}
        }
        return repEntity.ok(jsonArray);
	}
	/**
	 * 
	 * 建设页工期状态
	 */
	@Override
	public ResponseEntity getHomeProjectStatusByProjectId(ZjTzProManage zjTzProManage) {
		if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
        	if(StrUtil.equals("all", zjTzProManage.getProjectId(), true)) {
        		zjTzProManage.setProjectId("");
        		zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
        	}
        } else {
        	// 集团人员时
        	if(StrUtil.equals("all", zjTzProManage.getProjectId(), true)) {
        		zjTzProManage.setProjectId("");
        	}
        }
        ZjTzProManage dbzjTzProManage = zjTzProManageMapper.selectHomeProjectStatusByProjectId(zjTzProManage);
        //实际竣工日期
    	Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
    	//合同约定竣工日期
    	Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
    	//实际交工日期
    	Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
    	//合同约定交工日期
    	Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
    	//策划批复竣工日期
    	Date approvalCompleteDate = dbzjTzProManage.getApprovalCompleteDate();
    	//策划批复交工日期
    	Date approvalHandoverDate = dbzjTzProManage.getApprovalHandoverDate();
    	//当前日期
    	Date currDate = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd"));
    	//实际开工日期
    	Date actualDate = dbzjTzProManage.getActualDate();
    	
//    	ZjTzProSubprojectInfo zjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
//		zjTzProSubprojectInfo.setProjectId(zjTzProManage.getProjectId());
//		zjTzProSubprojectInfo.setSubprojectInfoId(zjTzProManage.getSubprojectId());
    	ZjTzProSubprojectInfo dbzjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
    	if (StrUtil.isNotEmpty(zjTzProManage.getSubprojectId())) {
    		dbzjTzProSubprojectInfo = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzProManage.getSubprojectId());
		}
		
		
		
//        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObject = new JSONObject();
//        	if (dbzjTzProManage == null) {
//        		dbzjTzProManage = new ZjTzProManage();
//			}
//        	JSONArray xAxisDataJSONARRAY = new JSONArray();
        	
        	//预警日期类型为合同约定
            if (StrUtil.equals(zjTzProManage.getDateType(), "1")) {
            	dbzjTzProManage.setDateType(zjTzProManage.getDateType());
            	//工期分析主体为项目
            	if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            		//建设期结束标志为交工
            		if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            			// 填写了实际竣工日期
            			if (complateDateActrual != null) {
            				//填写了合同约定竣工日期
            				if (complateDatePlan != null) {
            					//实际竣工日期早于等于应竣工日期
            					if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
            						//填写了实际交工日期和合同约定交工日期
            						if (handoverDateActrual != null && handoverDatePlan!= null) {
            							//实际交工日期早于等于合同约定交工日期
            							if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
            								dbzjTzProManage.setPeriodFlag("已交工，已竣工");
            								dbzjTzProManage.setPeriodId("5");
            								dbzjTzProManage.setStatus("1");
            								jsonObject = showProjectContent(dbzjTzProManage);
            							//实际交工日期晚于合同约定交工日期	
            							}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
            								dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
            								dbzjTzProManage.setPeriodId("6");
            								dbzjTzProManage.setStatus("2");
            								jsonObject = showProjectContent(dbzjTzProManage);
            							}else {
										}
            						}else {
									}
            						//实际竣工日期晚于应竣工日期
            					}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
            						//填写了实际交工日期和合同约定交工日期
            						if (handoverDateActrual != null && handoverDatePlan != null) {
            							//实际交工日期早于等于合同约定交工日期
            							if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
            								dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
            								dbzjTzProManage.setPeriodId("7");
            								dbzjTzProManage.setStatus("2");
            								jsonObject = showProjectContent(dbzjTzProManage);
            								//实际交工日期晚于应交工日期
            							}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
            								dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
            								dbzjTzProManage.setPeriodId("8");
            								dbzjTzProManage.setStatus("2");
            								jsonObject = showProjectContent(dbzjTzProManage);
            							}else {
										}
            						}else {
									}
            					}
            				}
            				//未填写实际竣工日期
            			}else if (complateDateActrual == null) {
            				//填写了实际交工日期
            				if (handoverDateActrual != null) {
            					if (handoverDatePlan != null) {
            						//实际交工日期早于等于应交工日期
            						if (handoverDateActrual.compareTo(handoverDatePlan) <= 0) {
            							if (complateDatePlan != null) {
            								//当前日期早于等于应竣工日期
            								if (currDate.compareTo(complateDatePlan) <= 0) {
            									dbzjTzProManage.setPeriodFlag("已交工，竣工中");
            									dbzjTzProManage.setPeriodId("9");
            									dbzjTzProManage.setStatus("3");
            									jsonObject = showProjectContent(dbzjTzProManage);
            									//当前日期晚于应竣工日期
            								}else if (currDate.compareTo(complateDatePlan) > 0) {
            									dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
            									dbzjTzProManage.setPeriodId("11");
            									dbzjTzProManage.setStatus("4");
            									jsonObject = showProjectContent(dbzjTzProManage);
            									
            								}
            							}else {
										}
            							//实际交工日期晚于应交工日期
            						}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
            							if (complateDatePlan != null) {
            								//当前日期早于等于应竣工日期
            								if (currDate.compareTo(complateDatePlan) <= 0) {
            									dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
            									dbzjTzProManage.setPeriodId("10");
            									dbzjTzProManage.setStatus("3");
            									jsonObject = showProjectContent(dbzjTzProManage);
            									//当前日期晚于应竣工日期
            								}else if (currDate.compareTo(complateDatePlan) > 0) {
            									dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
            									dbzjTzProManage.setPeriodId("12");
            									dbzjTzProManage.setStatus("4");
            									jsonObject = showProjectContent(dbzjTzProManage);
            								}
            							}else {
										}
            							
            						}
            					}else {
								}
            					//未填写实际交工日期
            				}else if (handoverDateActrual == null && StrUtil.equals(zjTzProManage.getConstructEnd(), "0")) {
            					if (handoverDatePlan != null) {
            						//当前日期早于等于应交工日期
            						if (currDate.compareTo(handoverDatePlan) <= 0) {
            							dbzjTzProManage.setPeriodFlag("建设中");
            							dbzjTzProManage.setPeriodId("13");
            							dbzjTzProManage.setStatus("3");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						//当前日期晚于应交工日期
            						}else if (currDate.compareTo(handoverDatePlan) > 0) {
            							dbzjTzProManage.setPeriodFlag("应交工未交工");
            							dbzjTzProManage.setPeriodId("14");
            							dbzjTzProManage.setStatus("5");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						}
            					}
            				}
            			}
            			
            			//建设期结束标志为竣工
            		}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            			// 阶段为竣工
            			if (StrUtil.equals(zjTzProManage.getConstructEnd(), "1")) {
            				//填写了实际竣工日期
            				if (complateDateActrual != null) {
            					//填写了合同约定竣工日期
            					if (complateDatePlan != null) {
            						//实际竣工日期早于等于应竣工日期
            						if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
            							dbzjTzProManage.setPeriodFlag("已竣工");
            							dbzjTzProManage.setPeriodId("1");
            							dbzjTzProManage.setStatus("1");
            							jsonObject = showProjectContent(dbzjTzProManage);
            							//实际竣工日期晚于应竣工日期
            						}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
            							dbzjTzProManage.setPeriodFlag("超期竣工");
            							dbzjTzProManage.setPeriodId("2");
            							dbzjTzProManage.setStatus("2");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						}
            					}
            					//未填写实际竣工日期	
            				}else if (complateDateActrual == null) {
            					if (complateDatePlan != null) {
            						//当前日期早于等于应竣工日期
            						if (currDate.compareTo(complateDatePlan) <= 0) {
            							dbzjTzProManage.setPeriodFlag("建设中");
            							dbzjTzProManage.setPeriodId("3");
            							dbzjTzProManage.setStatus("3");
            							jsonObject = showProjectContent(dbzjTzProManage);
            							//当前日期晚于应竣工日期
            						}else if (currDate.compareTo(complateDatePlan) > 0) {
            							dbzjTzProManage.setPeriodFlag("应竣工未竣工");
            							dbzjTzProManage.setPeriodId("4");
            							dbzjTzProManage.setStatus("4");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						}
            						
            					}
            				}
						}
            		}
					
            	//工期分析主体为子项目
				}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
							//实际竣工日期
							Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
							//合同约定竣工日期
							Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
							//实际交工日期
							Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
							//合同约定交工日期
							Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
							//策划批复竣工日期
							Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
							//策划批复交工日期
							Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
					    	//实际开工日期
					    	Date subactualDate = dbzjTzProSubprojectInfo.getStartDateActual();
					    	
					    	//建设期结束标志为交工
			            		if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
			            				// 填写了实际竣工日期
			            				if (complateDateActual != null) {
			            					//填写了合同约定竣工日期
			            					if (subComplateDatePlan != null) {
			            						//实际竣工日期早于等于应竣工日期
			            						if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
			            							//填写了实际交工日期和合同约定交工日期
			            							if (handoverDateActual != null && subHandoverDatePlan!= null) {
			            								//实际交工日期早于等于合同约定交工日期
			            								if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0 ) {
			            									dbzjTzProManage.setPeriodFlag("已交工，已竣工");
			            									dbzjTzProManage.setPeriodId("5");
			            									dbzjTzProManage.setStatus("1");
			            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            									//实际交工日期晚于合同约定交工日期	
			            								}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
			            									dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
			            									dbzjTzProManage.setPeriodId("6");
			            									dbzjTzProManage.setStatus("2");
			            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            								}
			            							}else {
			            							}
			            							//实际竣工日期晚于应竣工日期
			            						}else if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
			            							//填写了实际交工日期和合同约定交工日期
			            							if (handoverDateActual != null && subHandoverDatePlan != null) {
			            								//实际交工日期早于等于合同约定交工日期
			            								if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0 ) {
			            									dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
			            									dbzjTzProManage.setPeriodId("7");
			            									dbzjTzProManage.setStatus("2");
			            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            									//实际交工日期晚于应交工日期
			            								}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
			            									dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
			            									dbzjTzProManage.setPeriodId("8");
			            									dbzjTzProManage.setStatus("2");
			            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            								}
			            							}else {
			            							}
			            						}
			            					}else {
			            					}
			            					//未填写实际竣工日期
			            				}else if (complateDateActual == null) {
			            					//填写了实际交工日期
			            					if (handoverDateActual != null) {
			            						if (subHandoverDatePlan != null) {
			            							//实际交工日期早于等于应交工日期
			            							if (handoverDateActual.compareTo(subHandoverDatePlan) <= 0) {
			            								if (subComplateDatePlan != null) {
			            									//当前日期早于等于应竣工日期
			            									if (currDate.compareTo(subComplateDatePlan) <= 0) {
			            										dbzjTzProManage.setPeriodFlag("已交工，竣工中");
			            										dbzjTzProManage.setPeriodId("9");
			            										dbzjTzProManage.setStatus("3");
			            										jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            										//当前日期晚于应竣工日期
			            									}else if (currDate.compareTo(subComplateDatePlan) > 0) {
			            										dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
			            										dbzjTzProManage.setPeriodId("11");
			            										dbzjTzProManage.setStatus("4");
			            										jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            									}
			            								}else {
			            								}
			            								//实际交工日期晚于应交工日期
			            							}else if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
			            								if (subComplateDatePlan != null) {
			            									//当前日期早于等于应竣工日期
			            									if (currDate.compareTo(subComplateDatePlan) <= 0) {
			            										dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
			            										dbzjTzProManage.setPeriodId("10");
			            										dbzjTzProManage.setStatus("3");
			            										jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            										//当前日期晚于应竣工日期
			            									}else if (currDate.compareTo(subComplateDatePlan) > 0) {
			            										dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
			            										dbzjTzProManage.setPeriodId("12");
			            										dbzjTzProManage.setStatus("4");
			            										jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            									}
			            								}else {
			            								}
			            								
			            							}
			            						}else {
			            						}
			            						//未填写实际交工日期
			            					}else if (handoverDateActual == null && StrUtil.equals(zjTzProManage.getConstructEnd(), "0")) {
			            						if (subHandoverDatePlan != null) {
			            							//当前日期早于等于应交工日期
			            							if (currDate.compareTo(subHandoverDatePlan) <= 0) {
			            								dbzjTzProManage.setPeriodFlag("建设中");
			            								dbzjTzProManage.setPeriodId("13");
			            								dbzjTzProManage.setStatus("3");
			            								jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            								//当前日期晚于应交工日期
			            							}else if (currDate.compareTo(subHandoverDatePlan) > 0) {
			            								dbzjTzProManage.setPeriodFlag("应交工未交工");
			            								dbzjTzProManage.setPeriodId("14");
			            								dbzjTzProManage.setStatus("5");
			            								jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
			            							}
			            						}else {
			            						}
			            					}
			            				}

			            			//建设期结束标志为竣工
			            		}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
		            			// 阶段选择为竣工
			            			if (StrUtil.equals(zjTzProManage.getConstructEnd(), "1")) {
		            				//填写了实际竣工日期
		            				if (complateDateActual != null) {
		            					//填写了合同约定竣工日期
		            					if (subComplateDatePlan != null) {
		            						//实际竣工日期早于等于应竣工日期
		            						if (complateDateActual.compareTo(subComplateDatePlan) <= 0) {
		            							dbzjTzProManage.setPeriodFlag("已竣工");
		            							dbzjTzProManage.setPeriodId("1");
		            							dbzjTzProManage.setStatus("1");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							//实际竣工日期晚于应竣工日期
		            						}else if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
		            							dbzjTzProManage.setPeriodFlag("超期竣工");
		            							dbzjTzProManage.setPeriodId("2");
		            							dbzjTzProManage.setStatus("2");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            						}
		            					}else {
		            					}
		            					//未填写实际竣工日期	
		            				}else if (complateDateActual == null) {
		            					if (subComplateDatePlan != null) {
		            						//当前日期早于等于应竣工日期
		            						if (currDate.compareTo(subComplateDatePlan) <= 0) {
		            							dbzjTzProManage.setPeriodFlag("建设中");
		            							dbzjTzProManage.setPeriodId("3");
		            							dbzjTzProManage.setStatus("3");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							//当前日期晚于应竣工日期
		            						}else if (currDate.compareTo(subComplateDatePlan) > 0) {
		            							dbzjTzProManage.setPeriodFlag("应竣工未竣工");
		            							dbzjTzProManage.setPeriodId("4");
		            							dbzjTzProManage.setStatus("4");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            						}
		            						
		            					}else {
		            					}
		            				}
								}
		            		}
						
						
//					}
            		
					
				}
            //预警日期类型为策划批复
            }else if (StrUtil.equals(zjTzProManage.getDateType(), "2")) {
            	dbzjTzProManage.setDateType(zjTzProManage.getDateType());
            	//工期分析主体为项目
				if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
            		//建设期结束标志为交工
            		if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
            			// 填写了实际竣工日期
            			if (complateDateActrual != null) {
            				//填写了策划批复竣工日期
            				if (approvalCompleteDate != null) {
            					//实际竣工日期早于等于策划批复竣工日期
            					if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
            						//填写了实际交工日期和策划批复交工日期
            						if (handoverDateActrual != null && approvalHandoverDate!= null) {
            							//实际交工日期早于等于策划批复交工日期
            							if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0 ) {
            								dbzjTzProManage.setPeriodFlag("已交工，已竣工");
            								dbzjTzProManage.setPeriodId("5");
            								dbzjTzProManage.setStatus("1");
            								jsonObject = showProjectContent(dbzjTzProManage);
            								//实际交工日期晚于策划批复交工日期
            							}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
            								dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
            								dbzjTzProManage.setPeriodId("6");
            								dbzjTzProManage.setStatus("2");
            								jsonObject = showProjectContent(dbzjTzProManage);
            							}
            						}else {
									}
            						//实际竣工日期晚于策划批复竣工日期
            					}else if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
            						//填写了实际交工日期和策划批复交工日期
            						if (handoverDateActrual != null && approvalHandoverDate != null) {
            							//实际交工日期早于等于策划批复交工日期
            							if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0 ) {
            								dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
            								dbzjTzProManage.setPeriodId("7");
            								dbzjTzProManage.setStatus("2");
            								jsonObject = showProjectContent(dbzjTzProManage);
            								//实际交工日期晚于策划批复交工日期
            							}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
            								dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
            								dbzjTzProManage.setPeriodId("8");
            								dbzjTzProManage.setStatus("2");
            								jsonObject = showProjectContent(dbzjTzProManage);
            							}
            						}else {
									}
            					}
            				}else {
							}
            				//未填写实际竣工日期
            			}else if (complateDateActrual == null) {
            				//填写了实际交工日期
            				if (handoverDateActrual != null) {
            					//填写了策划批复交工日期
            					if (approvalHandoverDate != null) {
            						//实际交工日期早于等于策划批复交工日期
            						if (handoverDateActrual.compareTo(approvalHandoverDate) <= 0) {
            							if (complateDatePlan != null) {
            								//当前日期早于等于策划批复竣工日期
            								if (currDate.compareTo(approvalCompleteDate) <= 0) {
            									dbzjTzProManage.setPeriodFlag("已交工，竣工中");
            									dbzjTzProManage.setPeriodId("9");
            									dbzjTzProManage.setStatus("3");
            									jsonObject = showProjectContent(dbzjTzProManage);
            									//当前日期晚于策划批复竣工日期
            								}else if (currDate.compareTo(approvalCompleteDate) > 0) {
            									dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
            									dbzjTzProManage.setPeriodId("11");
            									dbzjTzProManage.setStatus("4");
            									jsonObject = showProjectContent(dbzjTzProManage);
            								}
            							}else {
										}
            							//实际交工日期晚于策划批复交工日期
            						}else if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
            							//填写了策划批复竣工日期
            							if (approvalCompleteDate != null) {
            								//当前日期早于等于策划批复竣工日期
            								if (currDate.compareTo(approvalCompleteDate) <= 0) {
            									dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
            									dbzjTzProManage.setPeriodId("10");
            									dbzjTzProManage.setStatus("3");
            									jsonObject = showProjectContent(dbzjTzProManage);
            									//当前日期晚于策划批复竣工日期
            								}else if (currDate.compareTo(approvalCompleteDate) > 0) {
            										dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
            										dbzjTzProManage.setPeriodId("12");
            										dbzjTzProManage.setStatus("4");
            										jsonObject = showProjectContent(dbzjTzProManage);
            								}
            							}else {
										}
            							
            						}
            					}else {
								}
            					//未填写实际交工日期
            				}else if (handoverDateActrual == null && StrUtil.equals(zjTzProManage.getConstructEnd(), "0")) {
            					//填写了策划批复交工日期
            					if (approvalHandoverDate != null) {
            						//当前日期早于等于策划批复交工日期
            						if (currDate.compareTo(approvalHandoverDate) <= 0) {
            							dbzjTzProManage.setPeriodFlag("建设中");
            							dbzjTzProManage.setPeriodId("13");
            							dbzjTzProManage.setStatus("3");
            							jsonObject = showProjectContent(dbzjTzProManage);
            							//当前日期晚于策划批复交工日期
            						}else if (currDate.compareTo(approvalHandoverDate) > 0) {
            							dbzjTzProManage.setPeriodFlag("应交工未交工");
            							dbzjTzProManage.setPeriodId("14");
            							dbzjTzProManage.setStatus("5");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						}
            					}else {
								}
            				}
            			}
            			
            			//建设期结束标志为竣工
            		}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
            			if (StrUtil.equals(zjTzProManage.getConstructEnd(), "1")) {
            				//填写了实际竣工日期
            				if (complateDateActrual != null) {
            					//填写了策划批复竣工日期
            					if (approvalCompleteDate != null) {
            						//实际竣工日期早于等于策划批复竣工日期
            						if (complateDateActrual.compareTo(approvalCompleteDate) <= 0) {
            							dbzjTzProManage.setPeriodFlag("已竣工");
            							dbzjTzProManage.setPeriodId("1");
            							dbzjTzProManage.setStatus("1");
            							jsonObject = showProjectContent(dbzjTzProManage);
            							//实际竣工日期晚于策划批复竣工日期
            						}else if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
            							dbzjTzProManage.setPeriodFlag("超期竣工");
            							dbzjTzProManage.setPeriodId("2");
            							dbzjTzProManage.setStatus("2");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						}
            					}else {
            					}
            					//未填写实际竣工日期	
            				}else if (complateDateActrual == null) {
            					//填写了策划批复竣工日期
            					if (approvalCompleteDate != null) {
            						//当前日期早于等于策划批复竣工日期
            						if (currDate.compareTo(approvalCompleteDate) <= 0) {
            							dbzjTzProManage.setPeriodFlag("建设中");
            							dbzjTzProManage.setPeriodId("3");
            							dbzjTzProManage.setStatus("3");
            							jsonObject = showProjectContent(dbzjTzProManage);
            							//当前日期晚于策划批复竣工日期
            						}else if (currDate.compareTo(approvalCompleteDate) > 0) {
            							dbzjTzProManage.setPeriodFlag("应竣工未竣工");
            							dbzjTzProManage.setPeriodId("4");
            							dbzjTzProManage.setStatus("4");
            							jsonObject = showProjectContent(dbzjTzProManage);
            						}
            						
            					}else {
            					}
            				}
						}
            		}
					
				//工期分析主体为子项目
				}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {

							//实际竣工日期
							Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
							//合同约定竣工日期
							Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
							//实际交工日期
							Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
							//合同约定交工日期
							Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
							//策划批复竣工日期
							Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
							//策划批复交工日期
							Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
					    	//实际开工日期
					    	Date subactualDate = dbzjTzProSubprojectInfo.getStartDateActual();
					    	
					    	//建设期结束标志为交工
		            		if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
		            			// 填写了实际竣工日期
		            			if (complateDateActual != null) {
		            				//填写了策划批复竣工日期
		            				if (subApprovalCompleteDate != null) {
		            					//实际竣工日期早于等于策划批复竣工日期
		            					if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
		            						//填写了实际交工日期和策划批复交工日期
		            						if (handoverDateActual != null && subApprovalHandoverDate!= null) {
		            							//实际交工日期早于等于策划批复交工日期
		            							if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0 ) {
		            								dbzjTzProManage.setPeriodFlag("已交工，已竣工");
		            								dbzjTzProManage.setPeriodId("5");
		            								dbzjTzProManage.setStatus("1");
		            								jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            								//实际交工日期晚于策划批复交工日期
		            							}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
		            								dbzjTzProManage.setPeriodFlag("超期交工，已竣工");
		            								dbzjTzProManage.setPeriodId("6");
		            								dbzjTzProManage.setStatus("2");
		            								jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							}
		            						}else {
											}
		            						//实际竣工日期晚于策划批复竣工日期
		            					}else if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
		            						//填写了实际交工日期和策划批复交工日期
		            						if (handoverDateActual != null && subApprovalHandoverDate != null) {
		            							//实际交工日期早于等于策划批复交工日期
		            							if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0 ) {
		            								dbzjTzProManage.setPeriodFlag("已交工，超期竣工");
		            								dbzjTzProManage.setPeriodId("7");
		            								dbzjTzProManage.setStatus("2");
		            								jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            								//实际交工日期晚于策划批复交工日期
		            							}else if (handoverDateActrual.compareTo(subApprovalHandoverDate) > 0) {
		            								dbzjTzProManage.setPeriodFlag("超期交工，超期竣工");
		            								dbzjTzProManage.setPeriodId("8");
		            								dbzjTzProManage.setStatus("2");
		            								jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							}
		            						}else {
											}
		            					}
		            				}else {
									}
		            				//未填写实际竣工日期
		            			}else if (complateDateActual == null) {
		            				//填写了实际交工日期
		            				if (handoverDateActual != null) {
		            					if (subApprovalHandoverDate != null) {
		            						//实际交工日期早于等于策划批复交工日期
		            						if (handoverDateActual.compareTo(subApprovalHandoverDate) <= 0) {
		            							if (subComplateDatePlan != null) {
		            								//当前日期早于等于策划批复竣工日期
		            								if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
		            									dbzjTzProManage.setPeriodFlag("已交工，竣工中");
		            									dbzjTzProManage.setPeriodId("9");
		            									dbzjTzProManage.setStatus("3");
		            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            									//当前日期晚于策划批复竣工日期
		            								}else if (currDate.compareTo(subApprovalCompleteDate) > 0) {
		            									dbzjTzProManage.setPeriodFlag("已交工，应竣工未竣工");
		            									dbzjTzProManage.setPeriodId("11");
		            									dbzjTzProManage.setStatus("4");
		            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            								}
		            							}else {
												}
		            							//实际交工日期晚于策划批复交工日期
		            						}else if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
		            							if (subApprovalCompleteDate != null) {
		            								//当前日期早于等于策划批复竣工日期
		            								if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
		            									dbzjTzProManage.setPeriodFlag("超期交工，竣工中");
		            									dbzjTzProManage.setPeriodId("10");
		            									dbzjTzProManage.setStatus("3");
		            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            									//当前日期晚于策划批复竣工日期
		            								}else if (currDate.compareTo(subApprovalCompleteDate) > 0) {
		            									dbzjTzProManage.setPeriodFlag("超期交工，应竣工未竣工");
		            									dbzjTzProManage.setPeriodId("12");
		            									dbzjTzProManage.setStatus("4");
		            									jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            								}
		            							}else {
												}
		            							
		            						}
		            					}else {
										}
		            					//未填写实际交工日期
		            				}else if (handoverDateActual == null && StrUtil.equals(zjTzProManage.getConstructEnd(), "0")) {
		            					if (subApprovalHandoverDate != null) {
		            						//当前日期早于等于策划批复交工日期
		            						if (currDate.compareTo(subApprovalHandoverDate) <= 0) {
		            							dbzjTzProManage.setPeriodFlag("建设中");
		            							dbzjTzProManage.setPeriodId("13");
		            							dbzjTzProManage.setStatus("3");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							//当前日期晚于策划批复交工日期
		            						}else if (currDate.compareTo(subApprovalHandoverDate) > 0) {
		            							dbzjTzProManage.setPeriodFlag("应交工未交工");
		            							dbzjTzProManage.setPeriodId("14");
		            							dbzjTzProManage.setStatus("5");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            						}
		            					}else {
										}
		            				}
		            			}
		            			
		            			//建设期结束标志为竣工
		            		}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
		            			if (StrUtil.equals(zjTzProManage.getConstructEnd(), "1")) {
		            				//填写了实际竣工日期
		            				if (complateDateActual != null) {
		            					//填写了策划批复竣工日期
		            					if (subApprovalCompleteDate != null) {
		            						//实际竣工日期早于等于策划批复竣工日期
		            						if (complateDateActual.compareTo(subApprovalCompleteDate) <= 0) {
		            							dbzjTzProManage.setPeriodFlag("已竣工");
		            							dbzjTzProManage.setPeriodId("1");
		            							dbzjTzProManage.setStatus("1");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							//实际竣工日期晚于策划批复竣工日期
		            						}else if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
		            							dbzjTzProManage.setPeriodFlag("超期竣工");
		            							dbzjTzProManage.setPeriodId("2");
		            							dbzjTzProManage.setStatus("2");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            						}
		            					}else {
		            					}
		            					//未填写实际竣工日期	
		            				}else if (complateDateActual == null) {
		            					if (subApprovalCompleteDate != null) {
		            						//当前日期早于等于策划批复竣工日期
		            						if (currDate.compareTo(subApprovalCompleteDate) <= 0) {
		            							dbzjTzProManage.setPeriodFlag("建设中");
		            							dbzjTzProManage.setPeriodId("3");
		            							dbzjTzProManage.setStatus("3");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            							//当前日期晚于策划批复竣工日期
		            						}else if (currDate.compareTo(subApprovalCompleteDate) > 0) {
		            							dbzjTzProManage.setPeriodFlag("应竣工未竣工");
		            							dbzjTzProManage.setPeriodId("4");
		            							dbzjTzProManage.setStatus("4");
		            							jsonObject = showSubprojectContent(dbzjTzProSubprojectInfo, dbzjTzProManage);
		            						}
		            						
		            					}else {
		            					}
		            				}
								}
		            		}
						
						
            		
				}
			}else {
			}
//			return repEntity;
			
		return repEntity.ok(jsonObject);
	}
	/**
	 * 
	 * 导出首页工期状态
	 */
	@Override
	public ResponseEntity exportHomeProjectStatus(ZjTzProManage zjTzProManage, HttpServletResponse response) {
		if (zjTzProManage == null) {
            zjTzProManage = new ZjTzProManage();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzProManage.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
        
        // 获取数据
        List<ZjTzProManage> projectStatusList = zjTzProManageMapper.exportHomeProjectStatus(zjTzProManage);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("项目工期情况表","","","","","",
        		"合同约定","","","","","","",
        		"","","","","","","","","","","","",
        		"策划批复","","","","","","",
        		"","","","","","","","","","","","");
        List<?> row2= CollUtil.newArrayList();
        row2 = CollUtil.newArrayList("项目编号","管理单位","项目名称","项目简称","子项目名称","建设期（月）",
        		"预警","工期状态","实际开工日期","合同约定交工日期","实际交工日期","当前建设期天数","实际建设期天数",
        		"交工推迟天数","交工提前天数","距离交工天数","超期交工天数","合同约定竣工日期","实际竣工日期","当前竣工阶段天数","实际竣工阶段天数","竣工推迟天数","竣工提前天数","距离竣工天数","超期竣工天数",
        		"预警","工期状态","实际开工日期","策划批复交工日期","实际交工日期","当前建设期天数","实际建设期天数",
				"交工推迟天数","交工提前天数","距离交工天数","超期交工天数","策划批复竣工日期","实际竣工日期","当前竣工阶段天数","实际竣工阶段天数","竣工推迟天数","竣工提前天数","距离竣工天数","超期竣工天数");
        rowsList.add(row1);
        rowsList.add(row2);
        
        // 数据装载（与上面的表头对应）
        if(projectStatusList != null && projectStatusList.size()>0) {
            for(ZjTzProManage dbzjTzProManage:projectStatusList) {
            	
                	//工期分析主体为项目
					if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "0")) {
						rowsList.addAll(exportProjectContent(dbzjTzProManage));
					//工期分析主体为子项目
					}else if (StrUtil.equals(dbzjTzProManage.getAnalySubject(), "1")) {
						rowsList.addAll(exportSubprojectContent(dbzjTzProManage));
					}
                	
            }
            
            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            
//            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);  
            
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
                StyleSet style = writer.getStyleSet();
                writer.write(rows, true);
                writer.merge(0, 0, 0, 5, "项目工期情况表", false);
                writer.merge(0, 0, 6, 24, "合同约定", false);
                writer.merge(0, 0, 25, 43, "策划批复", false);
                writer.setRowHeight(0, 30);
                writer.renameSheet(0, "工期预警");
                
                CellStyle headCellStyle = style.getHeadCellStyle();
                //水平居中
                headCellStyle.setAlignment(HorizontalAlignment.CENTER);
                headCellStyle.setWrapText(true);
                
                //设置内容字体
                Font font = writer.createFont();
                //加粗
                font.setBold(true);
                //设置标题字体大小
                font.setFontHeightInPoints((short) 30);
                
                headCellStyle.setFont(font);
                writer.setStyleSet(style);
                
                writer.setColumnWidth(0, 9);//项目编号：第1列9px宽
                writer.setColumnWidth(1, 12);//管理单位
                writer.setColumnWidth(2, 110);//项目名称
                writer.setColumnWidth(3, 35);//项目简称
                writer.setColumnWidth(4, 50);//子项目名称
                writer.setColumnWidth(5, 12);//建设期（月）
                writer.setColumnWidth(6, 8);//预警
                writer.setColumnWidth(7, 18);//工期状态
                writer.setColumnWidth(8, 16);//实际开工日期
                writer.setColumnWidth(9, 16);//合同约定交工日期
                writer.setColumnWidth(10, 16);//实际交工日期
                writer.setColumnWidth(11, 18);//当前建设期天数
                writer.setColumnWidth(12, 18);//实际建设期天数
                writer.setColumnWidth(13, 18);//交工推迟天数
                writer.setColumnWidth(14, 18);//交工提前天数
                writer.setColumnWidth(15, 18);//距离交工天数
                writer.setColumnWidth(16, 18);//超期交工天数
                writer.setColumnWidth(17, 16);//合同约定竣工日期
                writer.setColumnWidth(18, 16);//实际竣工日期
                writer.setColumnWidth(19, 18);//当前竣工阶段天数
                writer.setColumnWidth(20, 18);//实际竣工阶段天数
                writer.setColumnWidth(21, 18);//竣工推迟天数
                writer.setColumnWidth(22, 18);//竣工提前天数
                writer.setColumnWidth(23, 18);//距离竣工天数
                writer.setColumnWidth(24, 18);//超期竣工天数
                writer.setColumnWidth(25, 8);//预警
                writer.setColumnWidth(26, 18);//工期状态
                writer.setColumnWidth(27, 16);//实际开工日期
                writer.setColumnWidth(28, 16);//策划批复交工日期
                writer.setColumnWidth(29, 16);//实际交工日期
                writer.setColumnWidth(30, 18);//当前建设期天数
                writer.setColumnWidth(31, 18);//实际建设期天数
                writer.setColumnWidth(32, 18);//交工推迟天数
                writer.setColumnWidth(33, 18);//交工提前天数
                writer.setColumnWidth(34, 18);//距离交工天数
                writer.setColumnWidth(35, 18);//超期交工天数
                writer.setColumnWidth(36, 16);//策划批复竣工日期
                writer.setColumnWidth(37, 16);//实际竣工日期
                writer.setColumnWidth(38, 18);//当前竣工阶段天数
                writer.setColumnWidth(39, 18);//实际竣工阶段天数
                writer.setColumnWidth(40, 18);//竣工推迟天数
                writer.setColumnWidth(41, 18);//竣工提前天数
                writer.setColumnWidth(42, 18);//距离竣工天数
                writer.setColumnWidth(43, 18);//超期竣工天数
                
              //定义启始行
                int index = 2;
                int index2 = 2;
                int index3 = 2;
                int index4 = 2;
              //按照项目编号进行分组
				Map<String, List<ZjTzProManage>> proNoMaps =
						projectStatusList.stream().collect(Collectors.groupingBy(dbzjTzProManage->dbzjTzProManage.getProNo(),LinkedHashMap::new,Collectors.toList()));
				for (Map.Entry<String, List<ZjTzProManage>> list4Entry : proNoMaps.entrySet()) {
					List<ZjTzProManage> proNoList = list4Entry.getValue();
					//根据数据条数设置合并单元格信息
					if(proNoList.size() == 1){//一条数据不合并
						index = index + proNoList.size();
					}else {
						//规则编写
						writer.merge(index, index + proNoList.size() - 1, 0, 0,
								null, true);
						index = index + proNoList.size();
					}
				}
				//按照管理单位分组数据汇总处理
				Map<String, List<ZjTzProManage>> companyNameMaps =
						projectStatusList.stream().collect(Collectors.groupingBy(dbzjTzProManage->dbzjTzProManage.getCompanyName(),LinkedHashMap::new,Collectors.toList()));
				for (Map.Entry<String, List<ZjTzProManage>> listEntry : companyNameMaps.entrySet()) {
					List<ZjTzProManage> companyNameList = listEntry.getValue();
					//根据数据条数设置合并单元格信息
					if (companyNameList.size() == 1) {
						//一条数据不合并
						index2 = index2 + companyNameList.size();
					}else {
						//规则编写
						writer.merge(index2, index2 + companyNameList.size() - 1, 1, 1, null, true);
						index2 = index2 + companyNameList.size();
					}
				}
				//按照项目名称进行分组
				Map<String, List<ZjTzProManage>> projectNameMaps =
						projectStatusList.stream().collect(Collectors.groupingBy(dbzjTzProManage->dbzjTzProManage.getProjectName(),LinkedHashMap::new,Collectors.toList()));
				for (Map.Entry<String, List<ZjTzProManage>> list2Entry : projectNameMaps.entrySet()) {
					List<ZjTzProManage> projectNameList = list2Entry.getValue();
					//根据数据条数设置合并单元格信息
					if(projectNameList.size() == 1){//一条数据不合并
						index3 = index3 + projectNameList.size();
					}else {
						//规则编写
						writer.merge(index3, index3 + projectNameList.size() - 1, 2, 2,
								null, true);
						index3 = index3 + projectNameList.size();
						
					}
					
				}
				//按照项目简称进行分组
				Map<String, List<ZjTzProManage>> projectShortNameMaps =
						projectStatusList.stream().collect(Collectors.groupingBy(dbzjTzProManage->dbzjTzProManage.getProjectShortName(),LinkedHashMap::new,Collectors.toList()));
				for (Map.Entry<String, List<ZjTzProManage>> list3Entry : projectShortNameMaps.entrySet()) {
					List<ZjTzProManage> projectShortNameList = list3Entry.getValue();
					//根据数据条数设置合并单元格信息
					if(projectShortNameList.size() == 1){//一条数据不合并
						index4 = index4 + projectShortNameList.size();
					}else {
						//规则编写
						writer.merge(index4, index4 + projectShortNameList.size() - 1, 3, 3,
								null, true);
						index4 = index4 + projectShortNameList.size();
					}
				}
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
	/**
	 * 
	 * 管理进度计划下拉
	 */
	@Override
	public ResponseEntity getHomeProgressPlaningComname(ZjTzProManage zjTzProManage) {
		List<ZjTzProManage> comnameList = zjTzProManageMapper.selectHomeProgressPlaningComname(zjTzProManage);
		return repEntity.ok(comnameList);
	}
	/**
	 * 
	 * 集采不可重复
	 */
	@Override
	public ResponseEntity getZjTzProManageListNotUpdated(ZjTzProManage zjTzProManage) {
		List<ZjTzProManage> proManageList = zjTzProManageMapper.selectZjTzProManageListNotUpdated(zjTzProManage);
		return repEntity.ok(proManageList);
	}

	
	/**
	 * 点击饼图弹出表格，展示内容（工期分析主体为项目）
	 * 
	 */
	public JSONObject showProjectContent(ZjTzProManage dbzjTzProManage) {
		//实际竣工日期
    	Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
    	//合同约定竣工日期
    	Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
    	//实际交工日期
    	Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
    	//合同约定交工日期
    	Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
    	//策划批复竣工日期
    	Date approvalCompleteDate = dbzjTzProManage.getApprovalCompleteDate();
    	//策划批复交工日期
    	Date approvalHandoverDate = dbzjTzProManage.getApprovalHandoverDate();
    	//当前日期
    	Date currDate = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd"));
    	//实际开工日期
    	Date actualDate = dbzjTzProManage.getActualDate();
		JSONObject jsonObject = new JSONObject();
		//预警日期类型：合同约定
		if (StrUtil.equals(dbzjTzProManage.getDateType(), "1")) {
				// 建设期结束标志
				if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
					jsonObject.set("constructEnd", "交工");
				}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
					jsonObject.set("constructEnd", "竣工");
				}
				//预警日期类型
				jsonObject.set("dateType", "合同约定");
				//项目编号
				jsonObject.set("proNo", dbzjTzProManage.getProNo());
				//管理单位
				jsonObject.set("companyName", dbzjTzProManage.getCompanyName());
				//项目简称
				jsonObject.set("projectShortName", dbzjTzProManage.getProjectShortName());
				//子项目名称
				jsonObject.set("subprojectName", "");
				//工期状态
				jsonObject.set("periodFlag",dbzjTzProManage.getPeriodFlag());
				//工期预警分类
				if (StrUtil.equals(dbzjTzProManage.getStatus(), "1")) {
					jsonObject.set("periodWarn", "正常完工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "5")) {
						jsonObject.set("periodId", "5");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(handoverDatePlan) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY));
						}else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工提前天数
						if (complateDateActrual.compareTo(complateDatePlan) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActrual, complateDatePlan, DateUnit.DAY));
						}else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "1")) {
						jsonObject.set("periodId", "1");
						if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//竣工提前天数
						if (complateDateActrual.compareTo(complateDatePlan) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActrual, complateDatePlan, DateUnit.DAY));
						}else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "2")) {
					jsonObject.set("periodWarn", "超期完工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "6")) {
						jsonObject.set("periodId", "6");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工提前天数
						if (complateDateActrual.compareTo(complateDatePlan) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActrual, complateDatePlan, DateUnit.DAY));
						} else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "7")) {
						jsonObject.set("periodId", "7");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(handoverDatePlan) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActrual.compareTo(complateDatePlan) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(complateDatePlan, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "8")) {
						jsonObject.set("periodId", "8");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActrual.compareTo(complateDatePlan) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(complateDatePlan, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "2")) {
						jsonObject.set("periodId", "2");
						if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActrual.compareTo(complateDatePlan) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(complateDatePlan, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}
					
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "3")) {
					jsonObject.set("periodWarn", "建设中");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "9")) {
						jsonObject.set("periodId", "9");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(handoverDatePlan) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//距离竣工天数
						if (currDate.compareTo(complateDatePlan) < 0) {
							jsonObject.set("completeDay", DateUtil.between(currDate, complateDatePlan,  DateUnit.DAY));
						} else {
							jsonObject.set("completeDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "10")) {
						jsonObject.set("periodId", "10");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//距离竣工天数
						if (currDate.compareTo(complateDatePlan) < 0) {
							jsonObject.set("completeDay", DateUtil.between(currDate, complateDatePlan,  DateUnit.DAY));
						} else {
							jsonObject.set("completeDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "13")) {
						jsonObject.set("periodId", "13");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //距离交工天数
				        if (currDate.compareTo(handoverDatePlan) < 0) {
				        	jsonObject.set("handoverDay", DateUtil.between(currDate, handoverDatePlan,  DateUnit.DAY));
						} else {
				        	jsonObject.set("handoverDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "3")) {
						jsonObject.set("periodId", "3");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
						//距离竣工天数
				        if (currDate.compareTo(complateDatePlan) < 0) {
				        	jsonObject.set("completeDay", DateUtil.between(currDate, complateDatePlan,  DateUnit.DAY));
						} else {
				        	jsonObject.set("completeDay", "");
						}
					}
					
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "4")) {
					jsonObject.set("periodWarn", "应竣工未竣工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "11")) {
						jsonObject.set("periodId", "11");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(handoverDatePlan) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//超期竣工天数
						if (complateDatePlan.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(complateDatePlan, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "12")) {
						jsonObject.set("periodId", "12");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//超期竣工天数
						if (complateDatePlan.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(complateDatePlan, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "4")) {
						jsonObject.set("periodId", "4");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //超期竣工天数
				        if (complateDatePlan.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(complateDatePlan, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}
					
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "5")) {
					jsonObject.set("periodWarn", "应交工未交工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "14")) {
						jsonObject.set("periodId", "14");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //超期交工天数
				        if (handoverDatePlan.compareTo(currDate) < 0) {
				        	jsonObject.set("overdueHandoverDay", DateUtil.between(handoverDatePlan, currDate, DateUnit.DAY));
						} else {
				        	jsonObject.set("overdueHandoverDay", "");
						}
					}
					
					
				}
				//建设期（月）
				jsonObject.set("constructPeriod", dbzjTzProManage.getConstructPeriod());
				
				//实际开工日期
				if (actualDate != null) {
					jsonObject.set("actualDate", DateUtil.format(actualDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("actualDate", "");
				}
				//合同约定交工日期
				if (handoverDatePlan != null) {
					jsonObject.set("handoverDatePlan", DateUtil.format(handoverDatePlan, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("handoverDatePlan", "");
				}
				//实际交工日期
				if (handoverDateActrual != null) {
					jsonObject.set("handoverDateActrual", DateUtil.format(handoverDateActrual, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("handoverDateActrual", "");
				}
				//合同约定竣工日期
				if (complateDatePlan != null) {
					jsonObject.set("complateDatePlan", DateUtil.format(complateDatePlan, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("complateDatePlan", "");
				}
				
				//实际竣工日期
				if (complateDateActrual != null) {
					jsonObject.set("complateDateActrual", DateUtil.format(complateDateActrual, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("complateDateActrual", "");
				}
				// 当前日期
				jsonObject.set("currDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
				
		//预警日期类型：策划批复
		}else if (StrUtil.equals(dbzjTzProManage.getDateType(), "2")) {
				// 建设期结束标志
				if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
					jsonObject.set("constructEnd", "交工");
				}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
					jsonObject.set("constructEnd", "竣工");
				}
				//预警日期类型
				jsonObject.set("dateType", "策划批复");
				//项目编号
				jsonObject.set("proNo", dbzjTzProManage.getProNo());
				//管理单位
				jsonObject.set("companyName", dbzjTzProManage.getCompanyName());
				//项目简称
				jsonObject.set("projectShortName", dbzjTzProManage.getProjectShortName());
				//子项目名称
				jsonObject.set("subprojectName", "");
				//工期状态
				jsonObject.set("periodFlag",dbzjTzProManage.getPeriodFlag());
				//工期预警分类
				if (StrUtil.equals(dbzjTzProManage.getStatus(), "1")) {
					jsonObject.set("periodWarn", "正常完工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "5")) {
						jsonObject.set("periodId", "5");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY));
						}else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工提前天数
						if (complateDateActrual.compareTo(approvalCompleteDate) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActrual, approvalCompleteDate, DateUnit.DAY));
						}else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "1")) {
						jsonObject.set("periodId", "1");
						if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//竣工提前天数
						if (complateDateActrual.compareTo(approvalCompleteDate) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActrual, approvalCompleteDate, DateUnit.DAY));
						}else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}
					
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "2")) {
					jsonObject.set("periodWarn", "超期完工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "6")) {
						jsonObject.set("periodId", "6");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工提前天数
						if (complateDateActrual.compareTo(approvalCompleteDate) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActrual, approvalCompleteDate, DateUnit.DAY));
						} else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "7")) {
						jsonObject.set("periodId", "7");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(approvalCompleteDate, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "8")) {
						jsonObject.set("periodId", "8");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(approvalCompleteDate, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "2")) {
						jsonObject.set("periodId", "2");
						if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActrual.compareTo(approvalCompleteDate) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(approvalCompleteDate, complateDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}
					
					
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "3")) {
					jsonObject.set("periodWarn", "建设中");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "9")) {
						jsonObject.set("periodId", "9");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//距离竣工天数
						if (currDate.compareTo(approvalCompleteDate) < 0) {
							jsonObject.set("completeDay", DateUtil.between(currDate, approvalCompleteDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "10")) {
						jsonObject.set("periodId", "10");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//距离竣工天数
						if (currDate.compareTo(approvalCompleteDate) < 0) {
							jsonObject.set("completeDay", DateUtil.between(currDate, approvalCompleteDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "13")) {
						jsonObject.set("periodId", "13");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //距离交工天数
				        if (currDate.compareTo(approvalHandoverDate) < 0) {
				        	jsonObject.set("handoverDay", DateUtil.between(currDate, approvalHandoverDate,  DateUnit.DAY));
						} else {
				        	jsonObject.set("handoverDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "3")) {
						jsonObject.set("periodId", "3");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
						//距离竣工天数
				        if (currDate.compareTo(approvalCompleteDate) < 0) {
				        	jsonObject.set("completeDay", DateUtil.between(currDate, approvalCompleteDate,  DateUnit.DAY));
						} else {
				        	jsonObject.set("completeDay", "");
						}
					}
					
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "4")) {
					jsonObject.set("periodWarn", "应竣工未竣工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "11")) {
						jsonObject.set("periodId", "11");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//超期竣工天数
						if (approvalCompleteDate.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(approvalCompleteDate, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "12")) {
						jsonObject.set("periodId", "12");
						if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActrual.compareTo(approvalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActrual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//超期竣工天数
						if (approvalCompleteDate.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(approvalCompleteDate, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "4")) {
						jsonObject.set("periodId", "4");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //超期竣工天数
				        if (approvalCompleteDate.compareTo(currDate) < 0) {
				        	jsonObject.set("overduecompleteDay", DateUtil.between(approvalCompleteDate, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "5")) {
					jsonObject.set("periodWarn", "应交工未交工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "14")) {
						jsonObject.set("periodId", "14");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //超期交工天数
				        if (approvalHandoverDate.compareTo(currDate) < 0) {
				        	jsonObject.set("overdueHandoverDay", DateUtil.between(approvalHandoverDate, currDate, DateUnit.DAY));
						} else {
				        	jsonObject.set("overdueHandoverDay", "");
						}
					}
					
				}
				//建设期（月）
				jsonObject.set("constructPeriod", dbzjTzProManage.getConstructPeriod());
				//实际开工日期
				if (actualDate != null) {
					jsonObject.set("actualDate", DateUtil.format(actualDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("actualDate", "");
				}
				//策划批复交工日期
				if (approvalHandoverDate != null) {
					jsonObject.set("handoverDatePlan", DateUtil.format(approvalHandoverDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("handoverDatePlan", "");
				}
				//策划批复竣工日期
				if (approvalCompleteDate != null) {
					jsonObject.set("complateDatePlan", DateUtil.format(approvalCompleteDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("complateDatePlan", "");
				}
				//实际交工日期
				if (handoverDateActrual != null) {
					jsonObject.set("handoverDateActrual", DateUtil.format(handoverDateActrual, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("handoverDateActrual", "");
				}
				//实际竣工日期
				if (complateDateActrual != null) {
					jsonObject.set("complateDateActrual", DateUtil.format(complateDateActrual, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("complateDateActrual", "");
				}
				// 当前日期
				jsonObject.set("currDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
				
		}
	return jsonObject;
}
	
	/**
	 * 点击饼图弹出表格，展示内容（工期分析主体为子项目）
	 * 
	 */
	public JSONObject showSubprojectContent(ZjTzProSubprojectInfo dbzjTzProSubprojectInfo, ZjTzProManage dbzjTzProManage) {
		//实际竣工日期
		Date complateDateActual = dbzjTzProSubprojectInfo.getComplateDateActual();
		//合同约定竣工日期
		Date subComplateDatePlan = dbzjTzProSubprojectInfo.getComplateDatePlan();
		//实际交工日期
		Date handoverDateActual = dbzjTzProSubprojectInfo.getHandoverDateActual();
		//合同约定交工日期
		Date subHandoverDatePlan = dbzjTzProSubprojectInfo.getHandoverDatePlan();
		//策划批复竣工日期
		Date subApprovalCompleteDate = dbzjTzProSubprojectInfo.getApprovalCompleteDate();
		//策划批复交工日期
		Date subApprovalHandoverDate = dbzjTzProSubprojectInfo.getApprovalHandoverDate();
		//当前日期
		Date currDate = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd"));
		//实际开工日期
		Date actualDate = dbzjTzProSubprojectInfo.getStartDateActual();
		JSONObject jsonObject = new JSONObject();
		//预警日期类型为合同约定
		if (StrUtil.equals(dbzjTzProManage.getDateType(), "1")) {
			// 建设期结束标志
			if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
				jsonObject.set("constructEnd", "交工");
			}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
				jsonObject.set("constructEnd", "竣工");
			}
			//预警日期类型
			jsonObject.set("dateType", "合同约定");
			//项目编号
			jsonObject.set("proNo", dbzjTzProManage.getProNo());
			//管理单位
			jsonObject.set("companyName", dbzjTzProManage.getCompanyName());
			//项目简称
			jsonObject.set("projectShortName", dbzjTzProManage.getProjectShortName());
			//子项目名称
			jsonObject.set("subprojectName", dbzjTzProSubprojectInfo.getSubprojectName());
			//工期状态
			jsonObject.set("periodFlag",dbzjTzProManage.getPeriodFlag());
			//工期预警分类
			if (StrUtil.equals(dbzjTzProManage.getStatus(), "1")) {
				jsonObject.set("periodWarn", "正常完工");
				if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "5")) {
					jsonObject.set("periodId", "5");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工提前天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) < 0) {
						jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subHandoverDatePlan, DateUnit.DAY));
					}else {
						jsonObject.set("handoverAdvanceDay", "");
					}
					if (complateDateActual.compareTo(handoverDateActual) > 0) {
						//实际竣工阶段天数
						jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeActualDay", "");
					}
					//竣工提前天数
					if (complateDateActual.compareTo(subComplateDatePlan) < 0) {
						jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActual, subComplateDatePlan, DateUnit.DAY));
					}else {
						jsonObject.set("completeAdvanceDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "1")) {
					jsonObject.set("periodId", "1");
					if (actualDate != null && complateDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//竣工提前天数
					if (complateDateActual.compareTo(subComplateDatePlan) < 0) {
						jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActual, subComplateDatePlan, DateUnit.DAY));
					}else {
						jsonObject.set("completeAdvanceDay", "");
					}
				}
			}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "2")) {
				jsonObject.set("periodWarn", "超期完工");
				if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "6")) {
					jsonObject.set("periodId", "6");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工推迟天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
						jsonObject.set("handoverDelayDay", DateUtil.between(subHandoverDatePlan, handoverDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("handoverDelayDay", "");
					}
					if (complateDateActual.compareTo(handoverDateActual) > 0) {
						//实际竣工阶段天数
						jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeActualDay", "");
					}
					//竣工提前天数
					if (complateDateActual.compareTo(subComplateDatePlan) < 0) {
						jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActual, subComplateDatePlan, DateUnit.DAY));
					} else {
						jsonObject.set("completeAdvanceDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "7")) {
					jsonObject.set("periodId", "7");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工提前天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) < 0) {
						jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subHandoverDatePlan, DateUnit.DAY));
					} else {
						jsonObject.set("handoverAdvanceDay", "");
					}
					if (complateDateActual.compareTo(handoverDateActual) > 0) {
						//实际竣工阶段天数
						jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeActualDay", "");
					}
					//竣工推迟天数
					if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
						jsonObject.set("completeDelayDay", DateUtil.between(subComplateDatePlan, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeDelayDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "8")) {
					jsonObject.set("periodId", "8");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工推迟天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
						jsonObject.set("handoverDelayDay", DateUtil.between(subHandoverDatePlan, handoverDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("handoverDelayDay", "");
					}
					if (complateDateActual.compareTo(handoverDateActual) > 0) {
						//实际竣工阶段天数
						jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeActualDay", "");
					}
					//竣工推迟天数
					if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
						jsonObject.set("completeDelayDay", DateUtil.between(subComplateDatePlan, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeDelayDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "2")) {
					jsonObject.set("periodId", "2");
					if (actualDate != null && complateDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//竣工推迟天数
					if (complateDateActual.compareTo(subComplateDatePlan) > 0) {
						jsonObject.set("completeDelayDay", DateUtil.between(subComplateDatePlan, complateDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("completeDelayDay", "");
					}
				}
				
				
				
			}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "3")) {
				jsonObject.set("periodWarn", "建设中");
				if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "9")) {
					jsonObject.set("periodId", "9");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工提前天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) < 0) {
						jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subHandoverDatePlan, DateUnit.DAY));
					} else {
						jsonObject.set("handoverAdvanceDay", "");
					}
					//当前竣工阶段天数
					if (handoverDateActual.compareTo(currDate) < 0) {
						jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
					} else {
						jsonObject.set("completeCurrentDay", "");
					}
					//距离竣工天数
					if (currDate.compareTo(subComplateDatePlan) < 0) {
						jsonObject.set("completeDay", DateUtil.between(currDate, subComplateDatePlan,  DateUnit.DAY));
					} else {
						jsonObject.set("completeDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "10")) {
					jsonObject.set("periodId", "10");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工推迟天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
						jsonObject.set("handoverDelayDay", DateUtil.between(subHandoverDatePlan, handoverDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("handoverDelayDay", "");
					}
					//当前竣工阶段天数
					if (handoverDateActual.compareTo(currDate) < 0) {
						jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
					} else {
						jsonObject.set("completeCurrentDay", "");
					}
					//距离竣工天数
					if (currDate.compareTo(subComplateDatePlan) < 0) {
						jsonObject.set("completeDay", DateUtil.between(currDate, subComplateDatePlan,  DateUnit.DAY));
					} else {
						jsonObject.set("completeDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "13")) {
					jsonObject.set("periodId", "13");
					//当前建设期天数
			        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
			        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
					}else {
						jsonObject.set("constructCurrentDay", "");
					}
			        //距离交工天数
			        if (currDate.compareTo(subHandoverDatePlan) < 0) {
			        	jsonObject.set("handoverDay", DateUtil.between(currDate, subHandoverDatePlan,  DateUnit.DAY));
					} else {
			        	jsonObject.set("handoverDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "3")) {
					jsonObject.set("periodId", "3");
					//当前建设期天数
			        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
			        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
					}else {
						jsonObject.set("constructCurrentDay", "");
					}
					//距离竣工天数
			        if (currDate.compareTo(subComplateDatePlan) < 0) {
			        	jsonObject.set("completeDay", DateUtil.between(currDate, subComplateDatePlan,  DateUnit.DAY));
					} else {
			        	jsonObject.set("completeDay", "");
					}
				}
				
				
				
			}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "4")) {
				jsonObject.set("periodWarn", "应竣工未竣工");
				if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "11")) {
					jsonObject.set("periodId", "11");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工提前天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) < 0) {
						jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subHandoverDatePlan, DateUnit.DAY));
					} else {
						jsonObject.set("handoverAdvanceDay", "");
					}
					//当前竣工阶段天数
					if (handoverDateActual.compareTo(currDate) < 0) {
						jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
					} else {
						jsonObject.set("completeCurrentDay", "");
					}
					//超期竣工天数
					if (subComplateDatePlan.compareTo(currDate) < 0) {
						jsonObject.set("overduecompleteDay", DateUtil.between(subComplateDatePlan, currDate, DateUnit.DAY));
					} else {
						jsonObject.set("overduecompleteDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "12")) {
					jsonObject.set("periodId", "12");
					if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
						//实际建设期天数
						jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
					}else {
						jsonObject.set("constructActualDay", "");
					}
					//交工推迟天数
					if (handoverDateActual.compareTo(subHandoverDatePlan) > 0) {
						jsonObject.set("handoverDelayDay", DateUtil.between(subHandoverDatePlan, handoverDateActual, DateUnit.DAY));
					} else {
						jsonObject.set("handoverDelayDay", "");
					}
					//当前竣工阶段天数
					if (handoverDateActual.compareTo(currDate) < 0) {
						jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
					} else {
						jsonObject.set("completeCurrentDay", "");
					}
					//超期竣工天数
					if (subComplateDatePlan.compareTo(currDate) < 0) {
						jsonObject.set("overduecompleteDay", DateUtil.between(subComplateDatePlan, currDate, DateUnit.DAY));
					} else {
						jsonObject.set("overduecompleteDay", "");
					}
				}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "4")) {
					jsonObject.set("periodId", "4");
					//当前建设期天数
			        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
			        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
					}else {
						jsonObject.set("constructCurrentDay", "");
					}
			        //超期竣工天数
			        if (subComplateDatePlan.compareTo(currDate) < 0) {
			        	jsonObject.set("overduecompleteDay", DateUtil.between(subComplateDatePlan, currDate, DateUnit.DAY));
					} else {
						jsonObject.set("overduecompleteDay", "");
					}
				}
				
				
				
			}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "5")) {
				jsonObject.set("periodWarn", "应交工未交工");
				if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "14")) {
					jsonObject.set("periodId", "14");
					//当前建设期天数
			        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
			        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
					}else {
						jsonObject.set("constructCurrentDay", "");
					}
			        //超期交工天数
			        if (subHandoverDatePlan.compareTo(currDate) < 0) {
			        	jsonObject.set("overdueHandoverDay", DateUtil.between(subHandoverDatePlan, currDate, DateUnit.DAY));
					} else {
			        	jsonObject.set("overdueHandoverDay", "");
					}
				}
			}
			//建设期（月）
			jsonObject.set("constructPeriod", dbzjTzProSubprojectInfo.getConstructPeriod());
			//实际开工日期
			if (actualDate != null) {
				jsonObject.set("actualDate", DateUtil.format(actualDate, DatePattern.NORM_DATE_PATTERN));
			}else {
				jsonObject.set("actualDate", "");
			}
			//合同约定交工日期
			if (subHandoverDatePlan != null) {
				jsonObject.set("handoverDatePlan", DateUtil.format(subHandoverDatePlan, DatePattern.NORM_DATE_PATTERN));
			}else {
				jsonObject.set("handoverDatePlan", "");
			}
			//合同约定竣工日期
			if (subComplateDatePlan != null) {
				jsonObject.set("complateDatePlan", DateUtil.format(subComplateDatePlan, DatePattern.NORM_DATE_PATTERN));
			}else {
				jsonObject.set("complateDatePlan", "");
			}
			//实际交工日期
			if (handoverDateActual != null) {
				jsonObject.set("handoverDateActrual", DateUtil.format(handoverDateActual, DatePattern.NORM_DATE_PATTERN));
			}else {
				jsonObject.set("handoverDateActrual", "");
			}
			//实际竣工日期
			if (complateDateActual != null) {
				jsonObject.set("complateDateActrual", DateUtil.format(complateDateActual, DatePattern.NORM_DATE_PATTERN));
			}else {
				jsonObject.set("complateDateActrual", "");
			}
			// 当前日期
			jsonObject.set("currDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
			
		//预警日期类型为策划批复	
		}else if (StrUtil.equals(dbzjTzProManage.getDateType(), "2")) {
				
				// 建设期结束标志
				if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "0")) {
					jsonObject.set("constructEnd", "交工");
				}else if (StrUtil.equals(dbzjTzProSubprojectInfo.getConstructEnd(), "1")) {
					jsonObject.set("constructEnd", "竣工");
				}
				//预警日期类型
				jsonObject.set("dateType", "策划批复");
				//项目编号
				jsonObject.set("proNo", dbzjTzProManage.getProNo());
				//管理单位
				jsonObject.set("companyName", dbzjTzProManage.getCompanyName());
				//项目简称
				jsonObject.set("projectShortName", dbzjTzProManage.getProjectShortName());
				//子项目名称
				jsonObject.set("subprojectName", dbzjTzProSubprojectInfo.getSubprojectName());
				//工期状态
				jsonObject.set("periodFlag",dbzjTzProManage.getPeriodFlag());
				//工期预警分类
				if (StrUtil.equals(dbzjTzProManage.getStatus(), "1")) {
					jsonObject.set("periodWarn", "正常完工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "5")) {
						jsonObject.set("periodId", "5");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subApprovalHandoverDate, DateUnit.DAY));
						}else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						if (complateDateActual.compareTo(handoverDateActual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工提前天数
						if (complateDateActual.compareTo(subApprovalCompleteDate) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActual, subApprovalCompleteDate, DateUnit.DAY));
						}else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "1")) {
						jsonObject.set("periodId", "1");
						if (actualDate != null && complateDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//竣工提前天数
						if (complateDateActual.compareTo(subApprovalCompleteDate) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActual, subApprovalCompleteDate, DateUnit.DAY));
						}else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "2")) {
					jsonObject.set("periodWarn", "超期完工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "6")) {
						jsonObject.set("periodId", "6");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(subApprovalHandoverDate, handoverDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						if (complateDateActual.compareTo(handoverDateActual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工提前天数
						if (complateDateActual.compareTo(subApprovalCompleteDate) < 0) {
							jsonObject.set("completeAdvanceDay", DateUtil.between(complateDateActual, subApprovalCompleteDate, DateUnit.DAY));
						} else {
							jsonObject.set("completeAdvanceDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "7")) {
						jsonObject.set("periodId", "7");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subApprovalHandoverDate, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						if (complateDateActual.compareTo(handoverDateActual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(subApprovalCompleteDate, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "8")) {
						jsonObject.set("periodId", "8");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(subApprovalHandoverDate, handoverDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						if (complateDateActual.compareTo(handoverDateActual) > 0) {
							//实际竣工阶段天数
							jsonObject.set("completeActualDay", DateUtil.between(handoverDateActual, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(subApprovalCompleteDate, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "2")) {
						jsonObject.set("periodId", "2");
						if (actualDate != null && complateDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, complateDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//竣工推迟天数
						if (complateDateActual.compareTo(subApprovalCompleteDate) > 0) {
							jsonObject.set("completeDelayDay", DateUtil.between(subApprovalCompleteDate, complateDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("completeDelayDay", "");
						}
					}
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "3")) {
					jsonObject.set("periodWarn", "建设中");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "9")) {
						jsonObject.set("periodId", "9");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subApprovalHandoverDate, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//距离竣工天数
						if (currDate.compareTo(subApprovalCompleteDate) < 0) {
							jsonObject.set("completeDay", DateUtil.between(currDate, subApprovalCompleteDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "10")) {
						jsonObject.set("periodId", "10");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(subApprovalHandoverDate, handoverDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//距离竣工天数
						if (currDate.compareTo(subApprovalCompleteDate) < 0) {
							jsonObject.set("completeDay", DateUtil.between(currDate, subApprovalCompleteDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "13")) {
						jsonObject.set("periodId", "13");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //距离交工天数
				        if (currDate.compareTo(subApprovalHandoverDate) < 0) {
				        	jsonObject.set("handoverDay", DateUtil.between(currDate, subApprovalHandoverDate,  DateUnit.DAY));
						} else {
				        	jsonObject.set("handoverDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "3")) {
						jsonObject.set("periodId", "3");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
						//距离竣工天数
				        if (currDate.compareTo(subApprovalCompleteDate) < 0) {
				        	jsonObject.set("completeDay", DateUtil.between(currDate, subApprovalCompleteDate,  DateUnit.DAY));
						} else {
				        	jsonObject.set("completeDay", "");
						}
					}
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "4")) {
					jsonObject.set("periodWarn", "应竣工未竣工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "11")) {
						jsonObject.set("periodId", "11");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工提前天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) < 0) {
							jsonObject.set("handoverAdvanceDay", DateUtil.between(handoverDateActual, subApprovalHandoverDate, DateUnit.DAY));
						} else {
							jsonObject.set("handoverAdvanceDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//超期竣工天数
						if (subApprovalCompleteDate.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(subApprovalCompleteDate, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "12")) {
						jsonObject.set("periodId", "12");
						if (actualDate != null && handoverDateActual.compareTo(actualDate) > 0) {
							//实际建设期天数
							jsonObject.set("constructActualDay", DateUtil.between(actualDate, handoverDateActual, DateUnit.DAY));
						}else {
							jsonObject.set("constructActualDay", "");
						}
						//交工推迟天数
						if (handoverDateActual.compareTo(subApprovalHandoverDate) > 0) {
							jsonObject.set("handoverDelayDay", DateUtil.between(subApprovalHandoverDate, handoverDateActual, DateUnit.DAY));
						} else {
							jsonObject.set("handoverDelayDay", "");
						}
						//当前竣工阶段天数
						if (handoverDateActual.compareTo(currDate) < 0) {
							jsonObject.set("completeCurrentDay", DateUtil.between(handoverDateActual, currDate,  DateUnit.DAY));
						} else {
							jsonObject.set("completeCurrentDay", "");
						}
						//超期竣工天数
						if (subApprovalCompleteDate.compareTo(currDate) < 0) {
							jsonObject.set("overduecompleteDay", DateUtil.between(subApprovalCompleteDate, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}else if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "4")) {
						jsonObject.set("periodId", "4");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //超期竣工天数
				        if (subApprovalCompleteDate.compareTo(currDate) < 0) {
				        	jsonObject.set("overduecompleteDay", DateUtil.between(subApprovalCompleteDate, currDate, DateUnit.DAY));
						} else {
							jsonObject.set("overduecompleteDay", "");
						}
					}
					
					
				}else if (StrUtil.equals(dbzjTzProManage.getStatus(), "5")) {
					jsonObject.set("periodWarn", "应交工未交工");
					if (StrUtil.equals(dbzjTzProManage.getPeriodId(), "14")) {
						jsonObject.set("periodId", "14");
						//当前建设期天数
				        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
				        	jsonObject.set("constructCurrentDay", DateUtil.between(actualDate, currDate,  DateUnit.DAY));
						}else {
							jsonObject.set("constructCurrentDay", "");
						}
				        //超期交工天数
				        if (subApprovalHandoverDate.compareTo(currDate) < 0) {
				        	jsonObject.set("overdueHandoverDay", DateUtil.between(subApprovalHandoverDate, currDate, DateUnit.DAY));
						} else {
				        	jsonObject.set("overdueHandoverDay", "");
						}
					}
					
				}
				//建设期（月）
				jsonObject.set("constructPeriod", dbzjTzProSubprojectInfo.getConstructPeriod());
				//实际开工日期
				if (actualDate != null) {
					jsonObject.set("actualDate", DateUtil.format(actualDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("actualDate", "");
				}
				//策划批复交工日期
				if (subApprovalHandoverDate != null) {
					jsonObject.set("handoverDatePlan", DateUtil.format(subApprovalHandoverDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("handoverDatePlan", "");
				}
				//策划批复竣工日期
				if (subApprovalCompleteDate != null) {
					jsonObject.set("complateDatePlan", DateUtil.format(subApprovalCompleteDate, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("complateDatePlan", "");
				}
				//实际交工日期
				if (handoverDateActual != null) {
					jsonObject.set("handoverDateActrual", DateUtil.format(handoverDateActual, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("handoverDateActrual", "");
				}
				//实际竣工日期
				if (complateDateActual != null) {
					jsonObject.set("complateDateActrual", DateUtil.format(complateDateActual, DatePattern.NORM_DATE_PATTERN));
				}else {
					jsonObject.set("complateDateActrual", "");
				}
				// 当前日期
				jsonObject.set("currDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
				
		}
		return jsonObject;
	}
	
	/**
	 * 导出表格（工期分析主体为项目）
	 * 
	 */
	public List<List<?>> exportProjectContent(ZjTzProManage dbzjTzProManage) {
		List<List<?>> rowsList = Lists.newArrayList();
//		List<ZjTzProManage> proList = Lists.newArrayList();
		//实际竣工日期
    	Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
    	//合同约定竣工日期
    	Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
    	//实际交工日期
    	Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
    	//合同约定交工日期
    	Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
    	//策划批复竣工日期
    	Date approvalCompleteDate = dbzjTzProManage.getApprovalCompleteDate();
    	//策划批复交工日期
    	Date approvalHandoverDate = dbzjTzProManage.getApprovalHandoverDate();
    	//当前日期
    	Date currDate = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd"));
    	//实际开工日期
    	Date actualDate = dbzjTzProManage.getActualDate();
    	
    	//建设期结束标志
    	String constructEnd = "";
    	//工期状态
    	String periodFlag = "";
    	//工期预警分类
    	String periodWarn = "";
    	//预警
    	String colourFlag = "";
        //超期交工天数
    	String overdueHandoverDay  = "";
        //超期竣工天数
    	String overduecompleteDay  = "";
        //距离竣工天数
    	String completeDay  = "";
    	//距离交工天数
    	String handoverDay  = "";
        
        //当前建设期天数
    	String constructCurrentDay = "";
        //实际建设期天数
    	String constructActualDay = "";
        //当前竣工阶段天数
    	String completeCurrentDay  = "";
        //实际竣工阶段天数
    	String completeActualDay   = "";
        //交工提前天数
    	String handoverAdvanceDay = "";
        //交工推迟天数
    	String handoverDelayDay = "";
        //竣工提前天数
    	String completeAdvanceDay = "";
        //竣工推迟天数
    	String completeDelayDay = "";
        // 工期状态标识
    	String periodId = "";
    	//交工提前天数
    	String handoverAdvanceDay2 = "";
    	//竣工提前天数
    	String completeAdvanceDay2 = "";
    	//交工推迟天数
    	String handoverDelayDay2 = "";
    	//竣工推迟天数
    	String completeDelayDay2 = "";
    	//距离竣工天数
    	String completeDay2 = "";
    	//超期竣工天数
    	String overduecompleteDay2 = "";
    	//距离交工天数
    	String handoverDay2 = "";
    	//超期交工天数
    	String overdueHandoverDay2 = "";
        //预警日期类型为合同约定
//        if (StrUtil.equals(dbzjTzProManage.getDateType(), "1")) {
        	//建设期结束标志为交工
			if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
				constructEnd = "交工";
				
				
				// 填写了实际竣工日期
				if (complateDateActrual != null) {
					//填写了合同约定竣工日期
					if (complateDatePlan != null) {
						//实际竣工日期早于等于应竣工日期
						if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
							//填写了实际交工日期和合同约定交工日期
							if (handoverDateActrual != null && handoverDatePlan!= null) {
								//实际交工日期早于等于合同约定交工日期
								if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
									periodFlag = "已交工，已竣工";
									periodWarn = "正常完工";
									periodId = "5";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay = String.valueOf(DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY)) ;
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay = String.valueOf(DateUtil.between(complateDateActrual, complateDatePlan, DateUnit.DAY));
								}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
									periodFlag = "超期交工，已竣工";
									periodFlag = "超期完工";
									periodId = "6";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay = String.valueOf(DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay = String.valueOf(DateUtil.between(complateDateActrual, complateDatePlan, DateUnit.DAY));
								}
							}
							//实际竣工日期晚于应竣工日期
						}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
							//填写了实际交工日期和合同约定交工日期
							if (handoverDateActrual != null && handoverDatePlan != null) {
								//实际交工日期早于等于合同约定交工日期
								if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
									periodFlag = "已交工，超期竣工";
									periodFlag = "超期完工";
									periodId = "7";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay = String.valueOf(DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY)) ;
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay = String.valueOf(DateUtil.between(complateDatePlan, complateDateActrual, DateUnit.DAY));
									//实际交工日期晚于应交工日期
								}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
									periodFlag = "超期交工，超期竣工";
									periodFlag = "超期完工";
									periodId = "8";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay = String.valueOf(DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay = String.valueOf(DateUtil.between(complateDatePlan, complateDateActrual, DateUnit.DAY));
								}
							}
						}
					}
					//未填写实际竣工日期
				}else if (complateDateActrual == null) {
					//填写了实际交工日期
					if (handoverDateActrual != null) {
						if (handoverDateActrual != null && handoverDatePlan != null) {
							//实际交工日期早于等于应交工日期
							if (handoverDateActrual.compareTo(handoverDatePlan) <= 0) {
								if (complateDatePlan != null) {
									//当前日期早于等于应竣工日期
									if (currDate.compareTo(complateDatePlan) <= 0) {
										periodFlag = "已交工，竣工中";
										periodWarn = "建设中";
										periodId = "9";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay = String.valueOf(DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay = String.valueOf(DateUtil.between(currDate, complateDatePlan,  DateUnit.DAY));
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(complateDatePlan) > 0) {
										periodFlag = "已交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "11";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay = String.valueOf(DateUtil.between(handoverDateActrual, handoverDatePlan, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay = String.valueOf(DateUtil.between(complateDatePlan, currDate, DateUnit.DAY));
										
									}
								}
								//实际交工日期晚于应交工日期
							}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
								if (complateDatePlan != null) {
									//当前日期早于等于应竣工日期
									if (currDate.compareTo(complateDatePlan) <= 0) {
										periodFlag = "超期交工，竣工中";
										periodWarn = "建设中";
										periodId = "10";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay = String.valueOf(DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay = String.valueOf(DateUtil.between(currDate, complateDatePlan,  DateUnit.DAY));
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(complateDatePlan) > 0) {
										periodFlag = "超期交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "12";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay = String.valueOf(DateUtil.between(handoverDatePlan, handoverDateActrual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay = String.valueOf(DateUtil.between(complateDatePlan, currDate, DateUnit.DAY));
									}
								}
								
							}
						}
						//未填写实际交工日期
					}else if (handoverDateActrual == null) {
						if (handoverDatePlan != null) {
							//当前日期早于等于应交工日期
							if (currDate.compareTo(handoverDatePlan) <= 0) {
								periodFlag = "建设中";
								periodWarn = "建设中";
								periodId = "13";
								//当前建设期天数
						        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //距离交工天数
								handoverDay = String.valueOf(DateUtil.between(currDate, handoverDatePlan,  DateUnit.DAY));
							}else if (currDate.compareTo(handoverDatePlan) > 0) {
								periodFlag = "应交工未交工";
								periodWarn = "应交工未交工";
								colourFlag = "★";
								periodId = "14";
								//当前建设期天数
						        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //超期交工天数
								overdueHandoverDay = String.valueOf(DateUtil.between(handoverDatePlan, currDate, DateUnit.DAY));
							}
						}
					}
				}
			

				// 填写了实际竣工日期
				if (complateDateActrual != null) {
					//填写了策划批复竣工日期
					if (approvalCompleteDate  != null) {
						//实际竣工日期早于等于应竣工日期
						if (complateDateActrual.compareTo(approvalCompleteDate ) <= 0) {
							//填写了实际交工日期和策划批复交工日期
							if (handoverDateActrual != null && approvalHandoverDate != null) {
								//实际交工日期早于等于策划批复交工日期
								if (handoverDateActrual.compareTo(approvalHandoverDate ) <= 0 ) {
									periodFlag = "已交工，已竣工";
									periodWarn = "正常完工";
									periodId = "5";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay2 = String.valueOf(DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY)) ;
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay2 = String.valueOf(DateUtil.between(complateDateActrual, approvalCompleteDate, DateUnit.DAY));
								}else if (handoverDateActrual.compareTo(approvalHandoverDate ) > 0) {
									periodFlag = "超期交工，已竣工";
									periodFlag = "超期完工";
									periodId = "6";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay2 = String.valueOf(DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay2 = String.valueOf(DateUtil.between(complateDateActrual, approvalCompleteDate, DateUnit.DAY));
								}
							}
							//实际竣工日期晚于策划批复竣工日期
						}else if (complateDateActrual.compareTo(approvalCompleteDate ) > 0) {
							//填写了实际交工日期和策划批复交工日期
							if (handoverDateActrual != null && approvalHandoverDate  != null) {
								//实际交工日期早于等于策划批复交工日期
								if (handoverDateActrual.compareTo(approvalHandoverDate ) <= 0 ) {
									periodFlag = "已交工，超期竣工";
									periodFlag = "超期完工";
									periodId = "7";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay2 = String.valueOf(DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY)) ;
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay2 = String.valueOf(DateUtil.between(approvalCompleteDate, complateDateActrual, DateUnit.DAY));
									//实际交工日期晚于策划批复交工日期
								}else if (handoverDateActrual.compareTo(approvalHandoverDate ) > 0) {
									periodFlag = "超期交工，超期竣工";
									periodFlag = "超期完工";
									periodId = "8";
									if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay2 = String.valueOf(DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
									if (complateDateActrual.compareTo(handoverDateActrual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(handoverDateActrual, complateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay2 = String.valueOf(DateUtil.between(approvalCompleteDate, complateDateActrual, DateUnit.DAY));
								}
							}
						}
					}
					//未填写实际竣工日期
				}else if (complateDateActrual == null) {
					//填写了实际交工日期
					if (handoverDateActrual != null) {
						if (handoverDateActrual != null && approvalHandoverDate  != null) {
							//实际交工日期早于等于策划批复交工日期
							if (handoverDateActrual.compareTo(approvalHandoverDate ) <= 0) {
								if (approvalCompleteDate  != null) {
									//当前日期早于等于策划批复竣工日期
									if (currDate.compareTo(approvalCompleteDate ) <= 0) {
										periodFlag = "已交工，竣工中";
										periodWarn = "建设中";
										periodId = "9";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay2 = String.valueOf(DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay2 = String.valueOf(DateUtil.between(currDate, approvalCompleteDate,  DateUnit.DAY));
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(approvalCompleteDate ) > 0) {
										periodFlag = "已交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "11";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay2 = String.valueOf(DateUtil.between(handoverDateActrual, approvalHandoverDate, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay2 = String.valueOf(DateUtil.between(approvalCompleteDate, currDate, DateUnit.DAY));
									}
								}
								//实际交工日期晚于策划批复交工日期
							}else if (handoverDateActrual.compareTo(approvalHandoverDate ) > 0) {
								if (approvalCompleteDate  != null) {
									//当前日期早于等于策划批复竣工日期
									if (currDate.compareTo(approvalCompleteDate ) <= 0) {
										periodFlag = "超期交工，竣工中";
										periodWarn = "建设中";
										periodId = "10";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay2 = String.valueOf(DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay2 = String.valueOf(DateUtil.between(currDate, approvalCompleteDate,  DateUnit.DAY));
										//当前日期晚于策划批复竣工日期
									}else if (currDate.compareTo(approvalCompleteDate ) > 0) {
										periodFlag = "超期交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "12";
										if (actualDate != null && handoverDateActrual.compareTo(actualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(actualDate, handoverDateActrual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay2 = String.valueOf(DateUtil.between(approvalHandoverDate, handoverDateActrual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(handoverDateActrual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay2 = String.valueOf(DateUtil.between(approvalCompleteDate, currDate, DateUnit.DAY));
									}
								}
								
							}
						}
						//未填写实际交工日期
					}else if (handoverDateActrual == null) {
						if (approvalHandoverDate  != null) {
							//当前日期早于等于策划批复交工日期
							if (currDate.compareTo(approvalHandoverDate ) <= 0) {
								periodFlag = "建设中";
								periodWarn = "建设中";
								periodId = "13";
								//当前建设期天数
						        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //距离交工天数
								handoverDay2 = String.valueOf(DateUtil.between(currDate, approvalHandoverDate,  DateUnit.DAY));
							}else if (currDate.compareTo(approvalHandoverDate ) > 0) {
								periodFlag = "应交工未交工";
								periodWarn = "应交工未交工";
								colourFlag = "★";
								periodId = "14";
								//当前建设期天数
						        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //超期交工天数
								overdueHandoverDay2 = String.valueOf(DateUtil.between(approvalHandoverDate, currDate, DateUnit.DAY));
							}
						}
					}
				}
				
			
				//建设期结束标志为竣工
			}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
				constructEnd = "竣工";
				//填写了实际竣工日期
				if (complateDateActrual != null) {
					//填写了合同约定竣工日期
					if (complateDatePlan != null) {
						//实际竣工日期早于等于应竣工日期
						if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
							periodFlag = "已竣工";
							periodWarn = "正常完工";
							periodId = "1";
							if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工提前天数
							completeAdvanceDay = String.valueOf(DateUtil.between(complateDateActrual, complateDatePlan, DateUnit.DAY));
							//实际竣工日期晚于应竣工日期
						}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
							periodFlag = "超期竣工";
							periodWarn = "超期完工";
							periodId = "2";
							if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工推迟天数
							completeDelayDay = String.valueOf(DateUtil.between(complateDatePlan, complateDateActrual, DateUnit.DAY));
						}
					}
					//未填写实际竣工日期	
				}else if (complateDateActrual == null) {
					if (complateDatePlan != null) {
						//当前日期早于等于应竣工日期
						if (currDate.compareTo(complateDatePlan) <= 0) {
							periodFlag = "建设中";
							periodWarn = "建设中";
							periodId = "3";
							//当前建设期天数
					        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
							//距离竣工天数
							completeDay = String.valueOf(DateUtil.between(currDate, complateDatePlan,  DateUnit.DAY));
							//当前日期晚于应竣工日期
						}else if (currDate.compareTo(complateDatePlan) > 0) {
							periodFlag = "应竣工未竣工";
							periodWarn = "应竣工未竣工";
							colourFlag = "★";
							periodId = "4";
							//当前建设期天数
					        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
					        //超期竣工天数
							overduecompleteDay = String.valueOf(DateUtil.between(complateDatePlan, currDate, DateUnit.DAY));
						}
						
					}
				}
				

				//填写了实际竣工日期
				if (complateDateActrual != null) {
					//填写了策划批复竣工日期
					if (approvalCompleteDate  != null) {
						//实际竣工日期早于等于策划批复竣工日期
						if (complateDateActrual.compareTo(approvalCompleteDate ) <= 0) {
							periodFlag = "已竣工";
							periodWarn = "正常完工";
							periodId = "1";
							if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工提前天数
							completeAdvanceDay2 = String.valueOf(DateUtil.between(complateDateActrual, approvalCompleteDate, DateUnit.DAY));
							//实际竣工日期晚于策划批复竣工日期
						}else if (complateDateActrual.compareTo(approvalCompleteDate ) > 0) {
							periodFlag = "超期竣工";
							periodWarn = "超期完工";
							periodId = "2";
							if (actualDate != null && complateDateActrual.compareTo(actualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(actualDate, complateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工推迟天数
							completeDelayDay2 = String.valueOf(DateUtil.between(approvalCompleteDate, complateDateActrual, DateUnit.DAY));
						}
					}
					//未填写实际竣工日期	
				}else if (complateDateActrual == null) {
					if (approvalCompleteDate  != null) {
						//当前日期早于等于策划批复竣工日期
						if (currDate.compareTo(approvalCompleteDate ) <= 0) {
							periodFlag = "建设中";
							periodWarn = "建设中";
							periodId = "3";
							//当前建设期天数
					        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
							//距离竣工天数
							completeDay2 = String.valueOf(DateUtil.between(currDate, approvalCompleteDate,  DateUnit.DAY));
							//当前日期晚于策划批复竣工日期
						}else if (currDate.compareTo(approvalCompleteDate ) > 0) {
							periodFlag = "应竣工未竣工";
							periodWarn = "应竣工未竣工";
							colourFlag = "★";
							periodId = "4";
							//当前建设期天数
					        if (actualDate != null && currDate.compareTo(actualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(actualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
					        //超期竣工天数
							overduecompleteDay2 = String.valueOf(DateUtil.between(approvalCompleteDate, currDate, DateUnit.DAY));
						}
						
					}
				}
			
				
			}
			
			rowsList.add(CollUtil.newArrayList(
					dbzjTzProManage.getProNo(),
					dbzjTzProManage.getCompanyName(),
					dbzjTzProManage.getProjectName(),
					dbzjTzProManage.getProjectShortName(),
					"",
					dbzjTzProManage.getConstructPeriod(),
					colourFlag,
					periodWarn,
					//实际开工日期
					DateUtil.format(actualDate, DatePattern.NORM_DATE_PATTERN),
					//合同约定交工日期
					DateUtil.format(handoverDatePlan, DatePattern.NORM_DATE_PATTERN),
					//实际交工日期
					DateUtil.format(handoverDateActrual, DatePattern.NORM_DATE_PATTERN),
					//当前建设期天数
					constructCurrentDay,
					//实际建设期天数
					constructActualDay,
					//交工推迟天数
					handoverDelayDay,
					//交工提前天数
					handoverAdvanceDay,
					//距离交工天数
					handoverDay,
					//超期交工天数
					overdueHandoverDay,
					//合同约定竣工日期
					DateUtil.format(complateDatePlan, DatePattern.NORM_DATE_PATTERN),
					//实际竣工日期
					DateUtil.format(complateDateActrual, DatePattern.NORM_DATE_PATTERN),
					//当前竣工阶段天数
					completeCurrentDay,
					//实际竣工阶段天数
					completeActualDay,
					//竣工推迟天数
					completeDelayDay,
					//竣工提前天数
					completeAdvanceDay,
					//距离竣工天数
					completeDay,
					//超期竣工天数
					overduecompleteDay,
					//预警
					colourFlag,
					//工期状态
					periodWarn,
					//实际开工日期
					DateUtil.format(actualDate, DatePattern.NORM_DATE_PATTERN),
					//策划批复交工日期
					DateUtil.format(approvalHandoverDate, DatePattern.NORM_DATE_PATTERN),
					//实际交工日期
					DateUtil.format(handoverDateActrual, DatePattern.NORM_DATE_PATTERN),
					//当前建设期天数
					constructCurrentDay,
					//实际建设期天数
					constructActualDay,
					//交工推迟天数
					handoverDelayDay2,
					//交工提前天数
					handoverAdvanceDay2,
					//距离交工天数
					handoverDay2,
					//超期交工天数
					overdueHandoverDay2,
					//策划批复竣工日期
					DateUtil.format(approvalCompleteDate, DatePattern.NORM_DATE_PATTERN),
					//实际竣工日期
					DateUtil.format(complateDateActrual, DatePattern.NORM_DATE_PATTERN),
					//当前竣工阶段天数
					completeCurrentDay,
					//实际竣工阶段天数
					completeActualDay,
					//竣工推迟天数
					completeDelayDay2,
					//竣工提前天数
					completeAdvanceDay2,
					//距离竣工天数
					completeDay2,
					//超期竣工天数
					overduecompleteDay2
					)
					);
        
        
		return rowsList;
	}
	
	/**
	 * 导出表格（工期分析主体为子项目）
	 * 
	 */
	public List<List<?>> exportSubprojectContent(ZjTzProManage dbzjTzProManage) {
		List<List<?>> rowsList = Lists.newArrayList();
		//实际竣工日期
		Date subcomplateDateActrual = dbzjTzProManage.getComplateDateActrual();
		//合同约定竣工日期
		Date subcomplateDatePlan = dbzjTzProManage.getComplateDatePlan();
		//实际交工日期
		Date subhandoverDateActual = dbzjTzProManage.getHandoverDateActrual();
		//合同约定交工日期
		Date subhandoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
		//策划批复竣工日期
		Date subapprovalCompleteDate = dbzjTzProManage.getApprovalCompleteDate();
		//策划批复交工日期
		Date subapprovalHandoverDate = dbzjTzProManage.getApprovalHandoverDate();
		//实际开工日期
		Date subactualDate = dbzjTzProManage.getActualDate();
		//当前日期
		Date currDate = DateUtil.parse(DateUtil.format(new Date(), "yyyy-MM-dd"));
    	//分类
    	String periodFlag = "";
    	//工期预警分类
    	String periodWarn = "";
    	//预警
    	String colourFlag = "";
    	// 工期状态标识
    	String periodId = "";
        //超期交工天数
    	String overdueHandoverDay  = "";
        //超期竣工天数
    	String overduecompleteDay  = "";
        //距离竣工天数
    	String completeDay  = "";
    	//距离交工天数
    	String handoverDay  = "";
        
        //当前建设期天数
    	String constructCurrentDay = "";
        //实际建设期天数
    	String constructActualDay = "";
        //当前竣工阶段天数
    	String completeCurrentDay  = "";
        //实际竣工阶段天数
    	String completeActualDay   = "";
        //交工提前天数
//        int handoverAdvanceDay = 0;
        String handoverAdvanceDay = "";
        //交工推迟天数
        String handoverDelayDay = "";
        //竣工提前天数
        String completeAdvanceDay = "";
        //竣工推迟天数
        String completeDelayDay = "";
        
      //超期交工天数
        String overdueHandoverDay2 = "";
        //超期竣工天数
        String overduecompleteDay2 = "";
        //距离交工天数
        String handoverDay2 = "";
        //距离竣工天数
        String completeDay2 = "";
        //交工推迟天数
        String handoverDelayDay2 = "";
        //交工提前天数
        String handoverAdvanceDay2 = "";
        //竣工推迟天数
        String completeDelayDay2 = "";
        //竣工提前天数
        String completeAdvanceDay2 = "";
        //预警日期类型为合同约定
//		if (StrUtil.equals(dbzjTzProManage.getDateType(), "1")) {
			//建设期结束标志为交工
			if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
				// 填写了实际竣工日期
				if (subcomplateDateActrual  != null) {
					//填写了合同约定竣工日期
					if (subcomplateDatePlan  != null) {
						//实际竣工日期早于等于应竣工日期
						if (subcomplateDateActrual.compareTo(subcomplateDatePlan) <= 0) {
							//填写了实际交工日期和合同约定交工日期
							if (subhandoverDateActual  != null && subhandoverDatePlan != null) {
								//实际交工日期早于等于合同约定交工日期
								if (subhandoverDateActual.compareTo(subhandoverDatePlan) <= 0 ) {
									periodFlag = "已交工，已竣工";
									periodWarn = "正常完工";
									periodId = "5";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay = String.valueOf(DateUtil.between(subhandoverDateActual, subhandoverDatePlan, DateUnit.DAY)) ;
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay = String.valueOf(DateUtil.between(subcomplateDateActrual, subcomplateDatePlan, DateUnit.DAY));
								}else if (subhandoverDateActual.compareTo(subhandoverDatePlan) > 0) {
									periodFlag = "超期交工，已竣工";
									periodWarn = "超期完工";
									periodId = "6";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay = String.valueOf(DateUtil.between(subhandoverDatePlan, subhandoverDateActual, DateUnit.DAY));
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay = String.valueOf(DateUtil.between(subcomplateDateActrual, subcomplateDatePlan, DateUnit.DAY));
								}
							}
							//实际竣工日期晚于应竣工日期
						}else if (subcomplateDateActrual .compareTo(subcomplateDatePlan ) > 0) {
							//填写了实际交工日期和合同约定交工日期
							if (subhandoverDateActual  != null && subhandoverDatePlan  != null) {
								//实际交工日期早于等于合同约定交工日期
								if (subhandoverDateActual.compareTo(subhandoverDatePlan) <= 0 ) {
									periodFlag = "已交工，超期竣工";
									periodWarn = "超期完工";
									periodId = "7";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay = String.valueOf(DateUtil.between(subhandoverDateActual, subhandoverDatePlan, DateUnit.DAY)) ;
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay = String.valueOf(DateUtil.between(subcomplateDatePlan, subcomplateDateActrual, DateUnit.DAY));
									//实际交工日期晚于应交工日期
								}else if (subhandoverDateActual.compareTo(subhandoverDatePlan) > 0) {
									periodFlag = "超期交工，超期竣工";
									periodWarn = "超期完工";
									periodId = "8";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay = String.valueOf(DateUtil.between(subhandoverDatePlan, subhandoverDateActual, DateUnit.DAY));
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay = String.valueOf(DateUtil.between(subcomplateDatePlan, subcomplateDateActrual, DateUnit.DAY));
								}
							}
						}
					}
					//未填写实际竣工日期
				}else if (subcomplateDateActrual  == null) {
					//填写了实际交工日期
					if (subhandoverDateActual  != null) {
						if (subhandoverDateActual  != null && subhandoverDatePlan  != null) {
							//实际交工日期早于等于应交工日期
							if (subhandoverDateActual.compareTo(subhandoverDatePlan ) <= 0) {
								if (subcomplateDatePlan  != null) {
									//当前日期早于等于应竣工日期
									if (currDate.compareTo(subcomplateDatePlan ) <= 0) {
										periodFlag = "已交工，竣工中";
										periodWarn = "建设中";
										periodId = "9";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay = String.valueOf(DateUtil.between(subhandoverDateActual, subhandoverDatePlan, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay = String.valueOf(DateUtil.between(currDate, subcomplateDatePlan,  DateUnit.DAY));
										
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(subcomplateDatePlan ) > 0) {
										periodFlag = "已交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "11";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay = String.valueOf(DateUtil.between(subhandoverDateActual, subhandoverDatePlan, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay = String.valueOf(DateUtil.between(subcomplateDatePlan, currDate, DateUnit.DAY));
									}
								}
								//实际交工日期晚于应交工日期
							}else if (subhandoverDateActual .compareTo(subhandoverDatePlan ) > 0) {
								if (subcomplateDatePlan  != null) {
									//当前日期早于等于应竣工日期
									if (currDate.compareTo(subcomplateDatePlan ) <= 0) {
										periodFlag = "超期交工，竣工中";
										periodWarn = "建设中";
										periodId = "10";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay = String.valueOf(DateUtil.between(subhandoverDatePlan, subhandoverDateActual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay = String.valueOf(DateUtil.between(currDate, subcomplateDatePlan,  DateUnit.DAY));
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(subcomplateDatePlan ) > 0) {
										periodFlag = "超期交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "12";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay = String.valueOf(DateUtil.between(subhandoverDatePlan, subhandoverDateActual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay = String.valueOf(DateUtil.between(subcomplateDatePlan, currDate, DateUnit.DAY));
									}
								}
								
							}
						}
						//未填写实际交工日期
					}else if (subhandoverDateActual  == null) {
						if (subhandoverDatePlan  != null) {
							//当前日期早于等于应交工日期
							if (currDate.compareTo(subhandoverDatePlan ) <= 0) {
								periodFlag = "建设中";
								periodWarn = "建设中";
								periodId = "13";
								//当前建设期天数
						        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //距离交工天数
								handoverDay = String.valueOf(DateUtil.between(currDate, subhandoverDatePlan,  DateUnit.DAY));
							}else if (currDate.compareTo(subhandoverDatePlan ) > 0) {
								periodFlag = "应交工未交工";
								periodWarn = "应交工未交工";
								colourFlag = "★";
								periodId = "14";
								//当前建设期天数
						        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //超期交工天数
								overdueHandoverDay = String.valueOf(DateUtil.between(subhandoverDatePlan, currDate, DateUnit.DAY));
							}
						}
					}
				}
				
				

				// 填写了实际竣工日期
				if (subcomplateDateActrual  != null) {
					//填写了合同约定竣工日期
					if (subapprovalCompleteDate  != null) {
						//实际竣工日期早于等于应竣工日期
						if (subcomplateDateActrual.compareTo(subapprovalCompleteDate) <= 0) {
							//填写了实际交工日期和合同约定交工日期
							if (subhandoverDateActual  != null && subapprovalHandoverDate != null) {
								//实际交工日期早于等于合同约定交工日期
								if (subhandoverDateActual.compareTo(subapprovalHandoverDate) <= 0 ) {
									periodFlag = "已交工，已竣工";
									periodWarn = "正常完工";
									periodId = "5";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay2 = String.valueOf(DateUtil.between(subhandoverDateActual, subapprovalHandoverDate, DateUnit.DAY)) ;
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay2 = String.valueOf(DateUtil.between(subcomplateDateActrual, subapprovalCompleteDate, DateUnit.DAY));
								}else if (subhandoverDateActual.compareTo(subapprovalHandoverDate) > 0) {
									periodFlag = "超期交工，已竣工";
									periodWarn = "超期完工";
									periodId = "6";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay2 = String.valueOf(DateUtil.between(subapprovalHandoverDate, subhandoverDateActual, DateUnit.DAY));
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工提前天数
									completeAdvanceDay2 = String.valueOf(DateUtil.between(subcomplateDateActrual, subapprovalCompleteDate, DateUnit.DAY));
								}
							}
							//实际竣工日期晚于应竣工日期
						}else if (subcomplateDateActrual .compareTo(subapprovalCompleteDate ) > 0) {
							//填写了实际交工日期和合同约定交工日期
							if (subhandoverDateActual  != null && subapprovalCompleteDate  != null) {
								//实际交工日期早于等于合同约定交工日期
								if (subhandoverDateActual.compareTo(subapprovalCompleteDate) <= 0 ) {
									periodFlag = "已交工，超期竣工";
									periodWarn = "超期完工";
									periodId = "7";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工提前天数
									handoverAdvanceDay2 = String.valueOf(DateUtil.between(subhandoverDateActual, subapprovalHandoverDate, DateUnit.DAY)) ;
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay2 = String.valueOf(DateUtil.between(subapprovalCompleteDate, subcomplateDateActrual, DateUnit.DAY));
									//实际交工日期晚于应交工日期
								}else if (subhandoverDateActual.compareTo(subapprovalCompleteDate) > 0) {
									periodFlag = "超期交工，超期竣工";
									periodWarn = "超期完工";
									periodId = "8";
									if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
										//实际建设期天数
										constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
									}else {
										constructActualDay = "";
									}
									//交工推迟天数
									handoverDelayDay2 = String.valueOf(DateUtil.between(subapprovalHandoverDate, subhandoverDateActual, DateUnit.DAY));
									if (subcomplateDateActrual.compareTo(subhandoverDateActual) > 0) {
										//实际竣工阶段天数
										completeActualDay = String.valueOf(DateUtil.between(subhandoverDateActual, subcomplateDateActrual, DateUnit.DAY));
									} else {
										completeActualDay = "";
									}
									//竣工推迟天数
									completeDelayDay2 = String.valueOf(DateUtil.between(subapprovalCompleteDate, subcomplateDateActrual, DateUnit.DAY));
								}
							}
						}
					}
					//未填写实际竣工日期
				}else if (subcomplateDateActrual  == null) {
					//填写了实际交工日期
					if (subhandoverDateActual  != null) {
						if (subhandoverDateActual  != null && subapprovalHandoverDate  != null) {
							//实际交工日期早于等于应交工日期
							if (subhandoverDateActual.compareTo(subapprovalHandoverDate ) <= 0) {
								if (subapprovalCompleteDate  != null) {
									//当前日期早于等于应竣工日期
									if (currDate.compareTo(subapprovalCompleteDate ) <= 0) {
										periodFlag = "已交工，竣工中";
										periodWarn = "建设中";
										periodId = "9";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay2 = String.valueOf(DateUtil.between(subhandoverDateActual, subapprovalHandoverDate, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay2 = String.valueOf(DateUtil.between(currDate, subapprovalCompleteDate,  DateUnit.DAY));
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(subapprovalCompleteDate ) > 0) {
										periodFlag = "已交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "11";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工提前天数
										handoverAdvanceDay2 = String.valueOf(DateUtil.between(subhandoverDateActual, subapprovalHandoverDate, DateUnit.DAY)) ;
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay2 = String.valueOf(DateUtil.between(subapprovalCompleteDate, currDate, DateUnit.DAY));
									}
								}
								//实际交工日期晚于应交工日期
							}else if (subhandoverDateActual .compareTo(subapprovalHandoverDate ) > 0) {
								if (subapprovalCompleteDate  != null) {
									//当前日期早于等于应竣工日期
									if (currDate.compareTo(subapprovalCompleteDate ) <= 0) {
										periodFlag = "超期交工，竣工中";
										periodWarn = "建设中";
										periodId = "10";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay2 = String.valueOf(DateUtil.between(subapprovalHandoverDate, subhandoverDateActual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//距离竣工天数
										completeDay2 = String.valueOf(DateUtil.between(currDate, subapprovalCompleteDate,  DateUnit.DAY));
										//当前日期晚于应竣工日期
									}else if (currDate.compareTo(subapprovalCompleteDate ) > 0) {
										periodFlag = "超期交工，应竣工未竣工";
										periodWarn = "应竣工未竣工";
										colourFlag = "★";
										periodId = "12";
										if (subactualDate != null && subhandoverDateActual.compareTo(subactualDate) > 0) {
											//实际建设期天数
											constructActualDay = String.valueOf(DateUtil.between(subactualDate, subhandoverDateActual, DateUnit.DAY));
										}else {
											constructActualDay = "";
										}
										//交工推迟天数
										handoverDelayDay2 = String.valueOf(DateUtil.between(subapprovalHandoverDate, subhandoverDateActual, DateUnit.DAY));
										//当前竣工阶段天数
										completeCurrentDay = String.valueOf(DateUtil.between(subhandoverDateActual, currDate,  DateUnit.DAY));
										//超期竣工天数
										overduecompleteDay2 = String.valueOf(DateUtil.between(subapprovalCompleteDate, currDate, DateUnit.DAY));
									}
								}
								
							}
						}
						//未填写实际交工日期
					}else if (subhandoverDateActual  == null) {
						if (subapprovalHandoverDate  != null) {
							//当前日期早于等于应交工日期
							if (currDate.compareTo(subapprovalHandoverDate ) <= 0) {
								periodFlag = "建设中";
								periodWarn = "建设中";
								periodId = "13";
								//当前建设期天数
						        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //距离交工天数
								handoverDay2 = String.valueOf(DateUtil.between(currDate, subapprovalHandoverDate,  DateUnit.DAY));
							}else if (currDate.compareTo(subapprovalHandoverDate ) > 0) {
								periodFlag = "应交工未交工";
								periodWarn = "应交工未交工";
								colourFlag = "★";
								periodId = "14";
								//当前建设期天数
						        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
									constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
								}else {
									constructCurrentDay = "";
								}
						        //超期交工天数
								overdueHandoverDay2 = String.valueOf(DateUtil.between(subapprovalHandoverDate, currDate, DateUnit.DAY));
							}
						}
					}
				}
				
			
				
				//建设期结束标志为竣工
			}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
				//填写了实际竣工日期
				if (subcomplateDateActrual  != null) {
					//填写了合同约定竣工日期
					if (subcomplateDatePlan  != null) {
						//实际竣工日期早于等于应竣工日期
						if (subcomplateDateActrual.compareTo(subcomplateDatePlan) <= 0) {
							periodFlag = "已竣工";
							periodWarn = "正常完工";
							periodId = "1";
							if (subactualDate != null && subcomplateDateActrual.compareTo(subactualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(subactualDate, subcomplateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工提前天数
							completeAdvanceDay = String.valueOf(DateUtil.between(subcomplateDateActrual, subcomplateDatePlan, DateUnit.DAY));
							//实际竣工日期晚于应竣工日期
						}else if (subcomplateDateActrual.compareTo(subcomplateDatePlan) > 0) {
							periodFlag = "超期竣工";
							periodWarn = "超期完工";
							periodId = "2";
							if (subactualDate != null && subcomplateDateActrual.compareTo(subactualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(subactualDate, subcomplateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工推迟天数
							completeDelayDay = String.valueOf(DateUtil.between(subcomplateDatePlan, subcomplateDateActrual, DateUnit.DAY));
						}
					}
					//未填写实际竣工日期	
				}else if (subcomplateDateActrual == null) {
					if (subcomplateDatePlan != null) {
						//当前日期早于等于应竣工日期
						if (currDate.compareTo(subcomplateDatePlan) <= 0) {
							periodFlag = "建设中";
							periodWarn = "建设中";
							periodId = "3";
							//当前建设期天数
					        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
							//距离竣工天数
							completeDay = String.valueOf(DateUtil.between(currDate, subcomplateDatePlan,  DateUnit.DAY));
							//当前日期晚于应竣工日期
						}else if (currDate.compareTo(subcomplateDatePlan) > 0) {
							periodFlag = "应竣工未竣工";
							periodWarn = "应竣工未竣工";
							colourFlag = "★";
							periodId = "4";
							//当前建设期天数
					        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
					        //超期竣工天数
							overduecompleteDay = String.valueOf(DateUtil.between(subcomplateDatePlan, currDate, DateUnit.DAY));
						}
						
					}
				}
				

				//填写了实际竣工日期
				if (subcomplateDateActrual  != null) {
					//填写了合同约定竣工日期
					if (subapprovalCompleteDate  != null) {
						//实际竣工日期早于等于应竣工日期
						if (subcomplateDateActrual.compareTo(subapprovalCompleteDate) <= 0) {
							periodFlag = "已竣工";
							periodWarn = "正常完工";
							periodId = "1";
							if (subactualDate != null && subcomplateDateActrual.compareTo(subactualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(subactualDate, subcomplateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工提前天数
							completeAdvanceDay2 = String.valueOf(DateUtil.between(subcomplateDateActrual, subapprovalCompleteDate, DateUnit.DAY));
							//实际竣工日期晚于应竣工日期
						}else if (subcomplateDateActrual.compareTo(subapprovalCompleteDate) > 0) {
							periodFlag = "超期竣工";
							periodWarn = "超期完工";
							periodId = "2";
							if (subactualDate != null && subcomplateDateActrual.compareTo(subactualDate) > 0) {
								//实际建设期天数
								constructActualDay = String.valueOf(DateUtil.between(subactualDate, subcomplateDateActrual, DateUnit.DAY));
							}else {
								constructActualDay = "";
							}
							//竣工推迟天数
							completeDelayDay2 = String.valueOf(DateUtil.between(subapprovalCompleteDate, subcomplateDateActrual, DateUnit.DAY));
						}
					}
					//未填写实际竣工日期	
				}else if (subcomplateDateActrual == null) {
					if (subapprovalCompleteDate != null) {
						//当前日期早于等于应竣工日期
						if (currDate.compareTo(subapprovalCompleteDate) <= 0) {
							periodFlag = "建设中";
							periodWarn = "建设中";
							periodId = "3";
							//当前建设期天数
					        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
							//距离竣工天数
							completeDay2 = String.valueOf(DateUtil.between(currDate, subapprovalCompleteDate,  DateUnit.DAY));
							//当前日期晚于应竣工日期
						}else if (currDate.compareTo(subapprovalCompleteDate) > 0) {
							periodFlag = "应竣工未竣工";
							periodWarn = "应竣工未竣工";
							colourFlag = "★";
							periodId = "4";
							//当前建设期天数
					        if (subactualDate != null && currDate.compareTo(subactualDate) > 0) {
								constructCurrentDay = String.valueOf(DateUtil.between(subactualDate, currDate,  DateUnit.DAY));
							}else {
								constructCurrentDay = "";
							}
					        //超期竣工天数
							overduecompleteDay2 = String.valueOf(DateUtil.between(subapprovalCompleteDate, currDate, DateUnit.DAY));
						}
						
					}
				}
			
				
			}
			
			rowsList.add(CollUtil.newArrayList(
					dbzjTzProManage.getProNo(),
					dbzjTzProManage.getCompanyName(),
					dbzjTzProManage.getProjectName(),
					dbzjTzProManage.getProjectShortName(),
					dbzjTzProManage.getSubprojectName(),
					dbzjTzProManage.getConstructPeriod(),
					colourFlag,
					periodWarn,
					//实际开工日期
					DateUtil.format(subactualDate , DatePattern.NORM_DATE_PATTERN),
					//合同约定交工日期
					DateUtil.format(subhandoverDatePlan , DatePattern.NORM_DATE_PATTERN),
					//实际交工日期
					DateUtil.format(subhandoverDateActual , DatePattern.NORM_DATE_PATTERN),
					//当前建设期天数
					constructCurrentDay,
					//实际建设期天数
					constructActualDay,
					//交工推迟天数
					handoverDelayDay,
					//交工提前天数
					handoverAdvanceDay,
					//距离交工天数
					handoverDay,
					//超期交工天数
					overdueHandoverDay,
					//合同约定竣工日期
					DateUtil.format(subcomplateDatePlan , DatePattern.NORM_DATE_PATTERN),
					//实际竣工日期
					DateUtil.format(subcomplateDateActrual , DatePattern.NORM_DATE_PATTERN),
					//当前竣工阶段天数
					completeCurrentDay,
					//实际竣工阶段天数
					completeActualDay,
					//竣工推迟天数
					completeDelayDay,
					//竣工提前天数
					completeAdvanceDay,
					//距离竣工天数
					completeDay,
					//超期竣工天数
					overduecompleteDay,
					colourFlag,
					periodWarn,
					//实际开工日期
					DateUtil.format(subactualDate , DatePattern.NORM_DATE_PATTERN),
					//策划批复交工日期
					DateUtil.format(subapprovalHandoverDate , DatePattern.NORM_DATE_PATTERN),
					//实际交工日期
					DateUtil.format(subhandoverDateActual , DatePattern.NORM_DATE_PATTERN),
					//当前建设期天数
					constructCurrentDay,
					//实际建设期天数
					constructActualDay,
					//交工推迟天数
					handoverDelayDay2,
					//交工提前天数
					handoverAdvanceDay2,
					//距离交工天数
					handoverDay2,
					//超期交工天数
					overdueHandoverDay2,
					//合同约定竣工日期
					DateUtil.format(subapprovalCompleteDate , DatePattern.NORM_DATE_PATTERN),
					//实际竣工日期
					DateUtil.format(subcomplateDateActrual , DatePattern.NORM_DATE_PATTERN),
					//当前竣工阶段天数
					completeCurrentDay,
					//实际竣工阶段天数
					completeActualDay,
					//竣工推迟天数
					completeDelayDay2,
					//竣工提前天数
					completeAdvanceDay2,
					//距离竣工天数
					completeDay2,
					//超期竣工天数
					overduecompleteDay2
					)
					);	
			
		
		return rowsList;
	}

}
