package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContrLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishAgreementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishLeaseDetailMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishShopDetailMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishAgreement;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseDetail;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopDetail;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtSuppliesContrReplenishAgreementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxCtSuppliesContrReplenishAgreementService")
public class ZxCtSuppliesContrReplenishAgreementServiceImpl implements ZxCtSuppliesContrReplenishAgreementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishAgreementMapper zxCtSuppliesContrReplenishAgreementMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishShopListMapper zxCtSuppliesContrReplenishShopListMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishLeaseListMapper zxCtSuppliesContrReplenishLeaseListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrShopListMapper zxCtSuppliesContrShopListMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrLeaseListMapper zxCtSuppliesContrLeaseListMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishShopDetailMapper zxCtSuppliesContrReplenishShopDetailMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishLeaseDetailMapper zxCtSuppliesContrReplenishLeaseDetailMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementListByCondition(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        if (zxCtSuppliesContrReplenishAgreement == null) {
            zxCtSuppliesContrReplenishAgreement = new ZxCtSuppliesContrReplenishAgreement();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContrReplenishAgreement.setCompanyId("");
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtSuppliesContrReplenishAgreement.setCompanyId(zxCtSuppliesContrReplenishAgreement.getOrgID());
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtSuppliesContrReplenishAgreement.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
        }        
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrReplenishAgreement.getPage(),zxCtSuppliesContrReplenishAgreement.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList = zxCtSuppliesContrReplenishAgreementMapper.selectByZxCtSuppliesContrReplenishAgreementList(zxCtSuppliesContrReplenishAgreement);
        for(ZxCtSuppliesContrReplenishAgreement agreement : zxCtSuppliesContrReplenishAgreementList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(agreement.getReplenishAgreementId());
        	agreement.setReplenishAgreementFileList(zxErpFileMapper.selectByZxErpFileList(file));
        }
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrReplenishAgreement> p = new PageInfo<>(zxCtSuppliesContrReplenishAgreementList);

        return repEntity.okList(zxCtSuppliesContrReplenishAgreementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementDetail(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        if (zxCtSuppliesContrReplenishAgreement == null) {
            zxCtSuppliesContrReplenishAgreement = new ZxCtSuppliesContrReplenishAgreement();
        }
        // 获取数据
        ZxCtSuppliesContrReplenishAgreement dbZxCtSuppliesContrReplenishAgreement = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        // 数据存在
        if (dbZxCtSuppliesContrReplenishAgreement != null) {
            return repEntity.ok(dbZxCtSuppliesContrReplenishAgreement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrReplenishAgreement(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrReplenishAgreement.setReplenishAgreementId(UuidUtil.generate());
        zxCtSuppliesContrReplenishAgreement.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrReplenishAgreementMapper.insert(zxCtSuppliesContrReplenishAgreement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishAgreement);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreement(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrReplenishAgreement dbZxCtSuppliesContrReplenishAgreement = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        if (dbZxCtSuppliesContrReplenishAgreement != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrReplenishAgreement.getReplenishAgreementId())) {
           // 乙方名称
           dbZxCtSuppliesContrReplenishAgreement.setSecondName(zxCtSuppliesContrReplenishAgreement.getSecondName());
           // 乙方ID
           dbZxCtSuppliesContrReplenishAgreement.setSecondID(zxCtSuppliesContrReplenishAgreement.getSecondID());
           // 物资来源
           dbZxCtSuppliesContrReplenishAgreement.setMaterialSource(zxCtSuppliesContrReplenishAgreement.getMaterialSource());
           // 税率(%)
           dbZxCtSuppliesContrReplenishAgreement.setTaxRate(zxCtSuppliesContrReplenishAgreement.getTaxRate());
           // 是否局指审批
           dbZxCtSuppliesContrReplenishAgreement.setIsFlagZhb(zxCtSuppliesContrReplenishAgreement.getIsFlagZhb());
           // 是否局审批
           dbZxCtSuppliesContrReplenishAgreement.setIsFlag(zxCtSuppliesContrReplenishAgreement.getIsFlag());
           // 是否抵扣
           dbZxCtSuppliesContrReplenishAgreement.setIsDeduct(zxCtSuppliesContrReplenishAgreement.getIsDeduct());
           // 上期末变更后税额
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSum(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSum());
           // 清单
           dbZxCtSuppliesContrReplenishAgreement.setPp8(zxCtSuppliesContrReplenishAgreement.getPp8());
           // 评审状态
           dbZxCtSuppliesContrReplenishAgreement.setStatus(zxCtSuppliesContrReplenishAgreement.getStatus());
           // 流程实例ID
           dbZxCtSuppliesContrReplenishAgreement.setInstProcessId(zxCtSuppliesContrReplenishAgreement.getInstProcessId());
           // 开工日期
           dbZxCtSuppliesContrReplenishAgreement.setStartDate(zxCtSuppliesContrReplenishAgreement.getStartDate());
           // 竣工日期
           dbZxCtSuppliesContrReplenishAgreement.setEndDate(zxCtSuppliesContrReplenishAgreement.getEndDate());
           // 甲方名称
           dbZxCtSuppliesContrReplenishAgreement.setFirstName(zxCtSuppliesContrReplenishAgreement.getFirstName());
           // 甲方ID
           dbZxCtSuppliesContrReplenishAgreement.setFirstID(zxCtSuppliesContrReplenishAgreement.getFirstID());
           // 合同税额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostTax(zxCtSuppliesContrReplenishAgreement.getContractCostTax());
           // 合同签定人
           dbZxCtSuppliesContrReplenishAgreement.setAgent(zxCtSuppliesContrReplenishAgreement.getAgent());
           // 合同内容
           dbZxCtSuppliesContrReplenishAgreement.setContent(zxCtSuppliesContrReplenishAgreement.getContent());
           // 合同名称ID
           dbZxCtSuppliesContrReplenishAgreement.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
           // 合同名称
           dbZxCtSuppliesContrReplenishAgreement.setContractName(zxCtSuppliesContrReplenishAgreement.getContractName());
           // 合同类型
           dbZxCtSuppliesContrReplenishAgreement.setContractType(zxCtSuppliesContrReplenishAgreement.getContractType());
           // 合同类型
           dbZxCtSuppliesContrReplenishAgreement.setCode7(zxCtSuppliesContrReplenishAgreement.getCode7());
           // 合同工期
           dbZxCtSuppliesContrReplenishAgreement.setCsTimeLimit(zxCtSuppliesContrReplenishAgreement.getCsTimeLimit());
           // 含税合同金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setContractCost(zxCtSuppliesContrReplenishAgreement.getContractCost());
           // 公文任务ID
           dbZxCtSuppliesContrReplenishAgreement.setWorkitemID(zxCtSuppliesContrReplenishAgreement.getWorkitemID());
           // 发送局指判断ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToZhbID(zxCtSuppliesContrReplenishAgreement.getSendToZhbID());
           // 发送局判断ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToJuID(zxCtSuppliesContrReplenishAgreement.getSendToJuID());
           // 发起人
           dbZxCtSuppliesContrReplenishAgreement.setBeginPer(zxCtSuppliesContrReplenishAgreement.getBeginPer());
           // 不含税合同金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostNoTax(zxCtSuppliesContrReplenishAgreement.getContractCostNoTax());
           // 补充协议名称
           dbZxCtSuppliesContrReplenishAgreement.setPp3(zxCtSuppliesContrReplenishAgreement.getPp3());
           // 补充协议编号
           dbZxCtSuppliesContrReplenishAgreement.setContractNo(zxCtSuppliesContrReplenishAgreement.getContractNo());
           // 变更后税额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumTax());
           // 变更后含税金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSum(zxCtSuppliesContrReplenishAgreement.getAlterContractSum());
           // 变更后不含税金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumNoTax());
           // 本期增减税额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setPp4Tax(zxCtSuppliesContrReplenishAgreement.getPp4Tax());
           // 本期含税增减金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setPp4(zxCtSuppliesContrReplenishAgreement.getPp4());
           // 本期不含税增减金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setPp4NoTax(zxCtSuppliesContrReplenishAgreement.getPp4NoTax());
           // pp9
           dbZxCtSuppliesContrReplenishAgreement.setPp9(zxCtSuppliesContrReplenishAgreement.getPp9());
           // pp7
           dbZxCtSuppliesContrReplenishAgreement.setPp7(zxCtSuppliesContrReplenishAgreement.getPp7());
           // pp5
           dbZxCtSuppliesContrReplenishAgreement.setPp5(zxCtSuppliesContrReplenishAgreement.getPp5());
           // pp2
           dbZxCtSuppliesContrReplenishAgreement.setPp2(zxCtSuppliesContrReplenishAgreement.getPp2());
           // pp10
           dbZxCtSuppliesContrReplenishAgreement.setPp10(zxCtSuppliesContrReplenishAgreement.getPp10());
           // pp1
           dbZxCtSuppliesContrReplenishAgreement.setPp1(zxCtSuppliesContrReplenishAgreement.getPp1());
           // orgID
           dbZxCtSuppliesContrReplenishAgreement.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
           // comID
           dbZxCtSuppliesContrReplenishAgreement.setComID(zxCtSuppliesContrReplenishAgreement.getComID());
           // combProp
           dbZxCtSuppliesContrReplenishAgreement.setCombProp(zxCtSuppliesContrReplenishAgreement.getCombProp());
           // 备注
           dbZxCtSuppliesContrReplenishAgreement.setRemarks(zxCtSuppliesContrReplenishAgreement.getRemarks());
           // 排序
           dbZxCtSuppliesContrReplenishAgreement.setSort(zxCtSuppliesContrReplenishAgreement.getSort());
           // 共通
           dbZxCtSuppliesContrReplenishAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishAgreement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	file.setModifyUserInfo(userKey, realName);
        	if(fileList.size()>0) {
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
        	}
        	if(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList() != null && zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList().size()>0) {
        		for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()) {
        			zxErpFile.setUid(UuidUtil.generate());
        			zxErpFile.setOtherId(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        			zxErpFile.setCreateUserInfo(userKey, realName);
        			zxErpFileMapper.insert(zxErpFile);  
        		}
        	}
            return repEntity.ok("sys.data.update",zxCtSuppliesContrReplenishAgreement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrReplenishAgreementList != null && zxCtSuppliesContrReplenishAgreementList.size() > 0) {
           ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement = new ZxCtSuppliesContrReplenishAgreement();
           zxCtSuppliesContrReplenishAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishAgreementMapper.batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(zxCtSuppliesContrReplenishAgreementList, zxCtSuppliesContrReplenishAgreement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishAgreementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity saveZxCtSuppliesContrReplenishAgreementByContrId(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
	    HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	    int flag = 0;
	    String userKey = TokenUtils.getUserKey(request);
	    String realName = TokenUtils.getRealName(request);
	    ZxCtSuppliesContrReplenishAgreement agreement = new ZxCtSuppliesContrReplenishAgreement();
	    agreement.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
	    List<ZxCtSuppliesContrReplenishAgreement> agreementList = zxCtSuppliesContrReplenishAgreementMapper.selectByZxCtSuppliesContrReplenishAgreementList(agreement);
	    for(ZxCtSuppliesContrReplenishAgreement agree : agreementList) {
	    	if(!StrUtil.equals(agree.getApih5FlowStatus(), "2")) {
	    		return repEntity.layerMessage("no", "当前合同存在审核中的补充协议，无法新增！");
	    	}
	    }
	    zxCtSuppliesContrReplenishAgreement.setReplenishAgreementId(UuidUtil.generate());
	    zxCtSuppliesContrReplenishAgreement.setContractType("物资管理类补充协议");
	    //新增外层补充协议
	    if(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()!= null && zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList().size()>0) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	file.setModifyUserInfo(userKey, realName);
        	if(fileList.size()>0) {
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
        	}
     	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()) {
                zxErpFile.setUid(UuidUtil.generate());
                zxErpFile.setOtherId(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());                
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);  
     	   }
        }
	    //将合同表里的变更后，带入到至上期字段
	    ZxCtSuppliesContract contract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getPp6());
	    if(contract != null) {
	    	zxCtSuppliesContrReplenishAgreement.setUpAlterContractSum(contract.getAlterContractSum());
	    	zxCtSuppliesContrReplenishAgreement.setUpAlterContractSumNoTax(contract.getAlterContractSumNoTax());
	    	zxCtSuppliesContrReplenishAgreement.setUpAlterContractSumTax(contract.getAlterContractSumTax());
	    	zxCtSuppliesContrReplenishAgreement.setComID(contract.getComID());
	    }
        zxCtSuppliesContrReplenishAgreement.setCreateUserInfo(userKey, realName);
        flag = zxCtSuppliesContrReplenishAgreementMapper.insert(zxCtSuppliesContrReplenishAgreement);    
	    // 失败
	    if (flag == 0) {
	        return repEntity.errorSave();
	    } else {
	    	return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishAgreement);
	    }
	}
	
    @Override
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreementByContrId(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrReplenishAgreement dbZxCtSuppliesContrReplenishAgreement = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        if (dbZxCtSuppliesContrReplenishAgreement != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrReplenishAgreement.getReplenishAgreementId())) {
           // 乙方名称
           dbZxCtSuppliesContrReplenishAgreement.setSecondName(zxCtSuppliesContrReplenishAgreement.getSecondName());
           // 乙方ID
           dbZxCtSuppliesContrReplenishAgreement.setSecondID(zxCtSuppliesContrReplenishAgreement.getSecondID());
           // 物资来源
           dbZxCtSuppliesContrReplenishAgreement.setMaterialSource(zxCtSuppliesContrReplenishAgreement.getMaterialSource());
           // 税率(%)
           dbZxCtSuppliesContrReplenishAgreement.setTaxRate(zxCtSuppliesContrReplenishAgreement.getTaxRate());
           // 是否局指审批
           dbZxCtSuppliesContrReplenishAgreement.setIsFlagZhb(zxCtSuppliesContrReplenishAgreement.getIsFlagZhb());
           // 是否局审批
           dbZxCtSuppliesContrReplenishAgreement.setIsFlag(zxCtSuppliesContrReplenishAgreement.getIsFlag());
           // 是否抵扣
           dbZxCtSuppliesContrReplenishAgreement.setIsDeduct(zxCtSuppliesContrReplenishAgreement.getIsDeduct());
           // 上期末变更后税额
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSum(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSum());
           // 清单
           dbZxCtSuppliesContrReplenishAgreement.setPp8(zxCtSuppliesContrReplenishAgreement.getPp8());
           // 评审状态
           dbZxCtSuppliesContrReplenishAgreement.setStatus(zxCtSuppliesContrReplenishAgreement.getStatus());
           // 流程实例ID
           dbZxCtSuppliesContrReplenishAgreement.setInstProcessId(zxCtSuppliesContrReplenishAgreement.getInstProcessId());
           // 开工日期
           dbZxCtSuppliesContrReplenishAgreement.setStartDate(zxCtSuppliesContrReplenishAgreement.getStartDate());
           // 竣工日期
           dbZxCtSuppliesContrReplenishAgreement.setEndDate(zxCtSuppliesContrReplenishAgreement.getEndDate());
           // 甲方名称
           dbZxCtSuppliesContrReplenishAgreement.setFirstName(zxCtSuppliesContrReplenishAgreement.getFirstName());
           // 甲方ID
           dbZxCtSuppliesContrReplenishAgreement.setFirstID(zxCtSuppliesContrReplenishAgreement.getFirstID());
           // 合同税额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostTax(zxCtSuppliesContrReplenishAgreement.getContractCostTax());
           // 合同签定人
           dbZxCtSuppliesContrReplenishAgreement.setAgent(zxCtSuppliesContrReplenishAgreement.getAgent());
           // 合同内容
           dbZxCtSuppliesContrReplenishAgreement.setContent(zxCtSuppliesContrReplenishAgreement.getContent());
           // 合同名称ID
           dbZxCtSuppliesContrReplenishAgreement.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
           // 合同名称
           dbZxCtSuppliesContrReplenishAgreement.setContractName(zxCtSuppliesContrReplenishAgreement.getContractName());
           // 合同类型
           dbZxCtSuppliesContrReplenishAgreement.setContractType(zxCtSuppliesContrReplenishAgreement.getContractType());
           // 合同类型
           dbZxCtSuppliesContrReplenishAgreement.setCode7(zxCtSuppliesContrReplenishAgreement.getCode7());
           // 合同工期
           dbZxCtSuppliesContrReplenishAgreement.setCsTimeLimit(zxCtSuppliesContrReplenishAgreement.getCsTimeLimit());
           // 含税合同金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setContractCost(zxCtSuppliesContrReplenishAgreement.getContractCost());
           // 公文任务ID
           dbZxCtSuppliesContrReplenishAgreement.setWorkitemID(zxCtSuppliesContrReplenishAgreement.getWorkitemID());
           // 发送局指判断ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToZhbID(zxCtSuppliesContrReplenishAgreement.getSendToZhbID());
           // 发送局判断ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToJuID(zxCtSuppliesContrReplenishAgreement.getSendToJuID());
           // 发起人
           dbZxCtSuppliesContrReplenishAgreement.setBeginPer(zxCtSuppliesContrReplenishAgreement.getBeginPer());
           // 不含税合同金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostNoTax(zxCtSuppliesContrReplenishAgreement.getContractCostNoTax());
           // 补充协议名称
           dbZxCtSuppliesContrReplenishAgreement.setPp3(zxCtSuppliesContrReplenishAgreement.getPp3());
           // 补充协议编号
           dbZxCtSuppliesContrReplenishAgreement.setContractNo(zxCtSuppliesContrReplenishAgreement.getContractNo());
           // 变更后税额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumTax());
           // 变更后含税金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSum(zxCtSuppliesContrReplenishAgreement.getAlterContractSum());
           // 变更后不含税金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumNoTax());
           // 本期增减税额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setPp4Tax(zxCtSuppliesContrReplenishAgreement.getPp4Tax());
           // 本期含税增减金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setPp4(zxCtSuppliesContrReplenishAgreement.getPp4());
           // 本期不含税增减金额(万元)
           dbZxCtSuppliesContrReplenishAgreement.setPp4NoTax(zxCtSuppliesContrReplenishAgreement.getPp4NoTax());
           // pp9
           dbZxCtSuppliesContrReplenishAgreement.setPp9(zxCtSuppliesContrReplenishAgreement.getPp9());
           // pp7
           dbZxCtSuppliesContrReplenishAgreement.setPp7(zxCtSuppliesContrReplenishAgreement.getPp7());
           // pp5
           dbZxCtSuppliesContrReplenishAgreement.setPp5(zxCtSuppliesContrReplenishAgreement.getPp5());
           // pp2
           dbZxCtSuppliesContrReplenishAgreement.setPp2(zxCtSuppliesContrReplenishAgreement.getPp2());
           // pp10
           dbZxCtSuppliesContrReplenishAgreement.setPp10(zxCtSuppliesContrReplenishAgreement.getPp10());
           // pp1
           dbZxCtSuppliesContrReplenishAgreement.setPp1(zxCtSuppliesContrReplenishAgreement.getPp1());
           // orgID
           dbZxCtSuppliesContrReplenishAgreement.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
           // comID
           dbZxCtSuppliesContrReplenishAgreement.setComID(zxCtSuppliesContrReplenishAgreement.getComID());
           // combProp
           dbZxCtSuppliesContrReplenishAgreement.setCombProp(zxCtSuppliesContrReplenishAgreement.getCombProp());
           // 备注
           dbZxCtSuppliesContrReplenishAgreement.setRemarks(zxCtSuppliesContrReplenishAgreement.getRemarks());
           // 排序
           dbZxCtSuppliesContrReplenishAgreement.setSort(zxCtSuppliesContrReplenishAgreement.getSort());
           // 共通
           dbZxCtSuppliesContrReplenishAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishAgreement);
   	    if(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()!= null && zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList().size()>0) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	file.setModifyUserInfo(userKey, realName);
        	if(fileList.size()>0) {
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
        	}
     	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()) {
                zxErpFile.setUid(UuidUtil.generate());
                zxErpFile.setOtherId(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);  
     	   }
        }           
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrReplenishAgreement);
        }
    }
		
	@Override
	public ResponseEntity getZxCtSuppliesContrReplenishAgreementListByContrId(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        if (zxCtSuppliesContrReplenishAgreement == null) {
            zxCtSuppliesContrReplenishAgreement = new ZxCtSuppliesContrReplenishAgreement();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContrReplenishAgreement.setComID("");
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtSuppliesContrReplenishAgreement.setComID(zxCtSuppliesContrReplenishAgreement.getOrgID());
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtSuppliesContrReplenishAgreement.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
        }   
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrReplenishAgreement.getPage(),zxCtSuppliesContrReplenishAgreement.getLimit()); 
        // 获取数据
        List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList = zxCtSuppliesContrReplenishAgreementMapper.selectByZxCtSuppliesContrReplenishAgreementList(zxCtSuppliesContrReplenishAgreement);
        for(ZxCtSuppliesContrReplenishAgreement agreement : zxCtSuppliesContrReplenishAgreementList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(agreement.getReplenishAgreementId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	if(fileList.size()>0) {
        		agreement.setReplenishAgreementFileList(fileList);
        	}
//        	ZxCtSuppliesContractChange change = new ZxCtSuppliesContractChange();
//        	change.setPp3(agreement.getReplenishAgreementId());
//        	List<ZxCtSuppliesContractChange> changeList = zxCtSuppliesContractChangeMapper.selectByZxCtSuppliesContractChangeList(change);
//        		agreement.setAlterContent(changeList.get(0).getAlterContent());
//        		agreement.setAlterReason(changeList.get(0).getAlterReason());
//        		agreement.setReplyUnit(changeList.get(0).getPp1());
//        		agreement.setReplyDate(changeList.get(0).getReplyDate());
//            	file.setOtherId(changeList.get(0).getZxCtSuppliesContractChangeId());
//            	fileList = zxErpFileMapper.selectByZxErpFileList(file);
//            	if(fileList.size()>0) {
//            		agreement.setReplenishShopListFileList(fileList);
//            	}   
//            	agreement.setZxCtSuppliesContractChangeId(changeList.get(0).getZxCtSuppliesContractChangeId());
        	if(StrUtil.equals(agreement.getCode7(), "WZ") || StrUtil.equals(agreement.getCode7(), "LC")) {
        		ZxCtSuppliesContrReplenishShopList replenishShop = new ZxCtSuppliesContrReplenishShopList();
        		replenishShop.setPp3(agreement.getReplenishAgreementId());
        		List<ZxCtSuppliesContrReplenishShopList> replenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(replenishShop);
        		if(replenishShopList.size()>0) {      
        			agreement.setZxCtSuppliesContractChangeId(replenishShopList.get(0).getZxCtSuppliesContrReplenishShopListId());
            		agreement.setAlterContent(replenishShopList.get(0).getAlterContent());
            		agreement.setAlterReason(replenishShopList.get(0).getAlterReason());
            		agreement.setReplyUnit(replenishShopList.get(0).getPp1());
            		agreement.setReplyDate(replenishShopList.get(0).getReplyDate());
        			file.setOtherId(replenishShopList.get(0).getZxCtSuppliesContrReplenishShopListId());
        			fileList = zxErpFileMapper.selectByZxErpFileList(file);
        			if(fileList.size()>0) {
        				agreement.setReplenishShopListFileList(fileList);
        			}   
        			ZxCtSuppliesContrReplenishShopDetail shopDetail = new ZxCtSuppliesContrReplenishShopDetail();
        			shopDetail.setContrAlterID(replenishShopList.get(0).getZxCtSuppliesContrReplenishShopListId());
        			List<ZxCtSuppliesContrReplenishShopDetail> shopDetailList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(shopDetail);
        			agreement.setReplenishShopDetailedList(shopDetailList);
        		}
        	}else {
        		ZxCtSuppliesContrReplenishLeaseList replenishLease = new ZxCtSuppliesContrReplenishLeaseList();
        		replenishLease.setPp3(agreement.getReplenishAgreementId());
        		List<ZxCtSuppliesContrReplenishLeaseList> replenishLeaseList = zxCtSuppliesContrReplenishLeaseListMapper.selectByZxCtSuppliesContrReplenishLeaseListList(replenishLease);
        		if(replenishLeaseList.size()>0) {   
        			agreement.setZxCtSuppliesContractChangeId(replenishLeaseList.get(0).getZxCtSuppliesContrReplenishLeaseListId());
            		agreement.setAlterContent(replenishLeaseList.get(0).getAlterContent());
            		agreement.setAlterReason(replenishLeaseList.get(0).getAlterReason());
            		agreement.setReplyUnit(replenishLeaseList.get(0).getPp1());
            		agreement.setReplyDate(replenishLeaseList.get(0).getReplyDate());
        			file.setOtherId(replenishLeaseList.get(0).getZxCtSuppliesContrReplenishLeaseListId());
        			fileList = zxErpFileMapper.selectByZxErpFileList(file);
        			if(fileList.size()>0) {
        				agreement.setReplenishShopListFileList(fileList);
        			}           		
        			ZxCtSuppliesContrReplenishLeaseDetail replenishDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
        			replenishDetail.setContrAlterID(replenishLeaseList.get(0).getZxCtSuppliesContrReplenishLeaseListId());
        			List<ZxCtSuppliesContrReplenishLeaseDetail> replenishDetailList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(replenishDetail);
        			agreement.setReplenishLeaseDetailedList(replenishDetailList);    		
        		}
        	}
        }
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrReplenishAgreement> p = new PageInfo<>(zxCtSuppliesContrReplenishAgreementList);

        return repEntity.okList(zxCtSuppliesContrReplenishAgreementList, p.getTotal());
	}

	@Override
	public ResponseEntity saveZxCtSuppliesContrReplenishAgreementBycontrAlterID(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesContrReplenishAgreement agreement = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
		//新增变更清单
    	if(StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "WZ") || StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "LC")) {
            //新增补充协议清单表
            ZxCtSuppliesContrReplenishShopList replenishShopList = new ZxCtSuppliesContrReplenishShopList();
            replenishShopList.setZxCtSuppliesContrReplenishShopListId(UuidUtil.generate());
            // 上期末变更后税额
            replenishShopList.setUpAlterContractSumTax(agreement.getUpAlterContractSumTax());
            // 上期末变更后金额不含税
            replenishShopList.setUpAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
            // 上期末变更后金额
            replenishShopList.setUpAlterContractSum(agreement.getUpAlterContractSum());
            // 批复日期
            replenishShopList.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
            // 批复单位
            replenishShopList.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
            // 机构ID
            replenishShopList.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
            // 合同税额(万元)
            replenishShopList.setPp2Tax(zxCtSuppliesContrReplenishAgreement.getContractCostTax());
            // 不含税合同金额(万元)
            replenishShopList.setPp2NoTax(zxCtSuppliesContrReplenishAgreement.getContractCostNoTax());
            // 补充协议书编号
            replenishShopList.setReplyNo(zxCtSuppliesContrReplenishAgreement.getContractNo());
            // 补充协议书ID
            replenishShopList.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
            // 变更原因
            replenishShopList.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
            // 变更内容
            replenishShopList.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
		if(zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList() != null) {
			zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList().parallelStream().forEach((detial)->{
				ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopList = detial;
				//查询合同清单变更后数据，插入进去
				ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrShopList = new ZxCtSuppliesContrReplenishShopDetail();
		            zxCtSuppliesContrShopList.setZxCtSuppliesContrReplenishShopDetailId(UuidUtil.generate());
		            zxCtSuppliesContrShopList.setContrAlterID(replenishShopList.getZxCtSuppliesContrReplenishShopListId());
		            zxCtSuppliesContrShopList.setAlterType(detial.getAlterType());
		            // 租期单位
		            zxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrReplenishShopList.getRentUnit());
		            // 租期
		            zxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrReplenishShopList.getContrTrrm());
		            // 物资名称
		            zxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrReplenishShopList.getWorkName());
		            // 物资类别ID
		            zxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrReplenishShopList.getWorkTypeID());
		            // 物资类别
		            zxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrReplenishShopList.getWorkType());
		            // 物资编码ID
		            zxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrReplenishShopList.getWorkID());
		            // 物资编码
		            zxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrReplenishShopList.getWorkNo());
		            // 税率(%)
		            zxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrReplenishShopList.getTaxRate());
		            // 数量
		            zxCtSuppliesContrShopList.setQty(zxCtSuppliesContrReplenishShopList.getQty());
		            // 是否网价
		            zxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrReplenishShopList.getIsNetPrice());
		            // 实际开始时间
		            zxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrReplenishShopList.getActualStartDate());
		            // 实际结束时间
		            zxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrReplenishShopList.getActualEndDate());
		            // 界面展现类型
		            zxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrReplenishShopList.getViewType());
		            // 计划开始时间
		            zxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrReplenishShopList.getPlanStartDate());
		            // 计划结束时间
		            zxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrReplenishShopList.getPlanEndDate());
		            // 含税金额
		            zxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrReplenishShopList.getContractSum());
		            // 含税单价
		            zxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrReplenishShopList.getPrice());
		            // 规格型号
		            zxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrReplenishShopList.getSpec());
		            // 单位
		            zxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrReplenishShopList.getUnit());
		            // 单位
		            zxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrReplenishShopList.getTreenode());
		            // 不含税金额
		            zxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrReplenishShopList.getContractSumNoTax());
		            // 不含税单价
		            zxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrReplenishShopList.getPriceNoTax());
		            // 变更日期
		            zxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrReplenishShopList.getChangeDate());
		            // 变更后租期
		            zxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrReplenishShopList.getAlterTrrm());
		            // 变更后数量
		            zxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrReplenishShopList.getChangeQty());
		            // 变更后含税金额
		            zxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrReplenishShopList.getChangeContractSum());
		            // 变更后含税单价
		            zxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrReplenishShopList.getChangePrice());
		            //变更后税额
		            zxCtSuppliesContrShopList.setChangeContractSumTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumTax());
		            // 变更后不含税金额
		            zxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumNoTax());
		            // 变更后不含税单价
		            zxCtSuppliesContrShopList.setChangePriceNoTax(zxCtSuppliesContrReplenishShopList.getChangePriceNoTax());
		            // 备注
		            zxCtSuppliesContrShopList.setRemarks(zxCtSuppliesContrShopList.getRemarks());
		            zxCtSuppliesContrShopList.setCreateUserInfo(userKey, realName);
		            zxCtSuppliesContrReplenishShopDetailMapper.insert(zxCtSuppliesContrShopList);
			});
			ZxCtSuppliesContrReplenishShopDetail shop = new ZxCtSuppliesContrReplenishShopDetail();
			shop.setContrAlterID(replenishShopList.getZxCtSuppliesContrReplenishShopListId());
			shop.setAlterType("1");
			List<ZxCtSuppliesContrReplenishShopDetail> addShopList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(shop);
			shop.setAlterType("2");
			List<ZxCtSuppliesContrReplenishShopDetail> editShopList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(shop);
			for(ZxCtSuppliesContrReplenishShopDetail detail : editShopList) {
				//如果存在上期，则查询对应的补充协议清单
				if(agreement.getUpAlterContractSumTax() != null) {
					ZxCtSuppliesContrShopList contrShop = new ZxCtSuppliesContrShopList();
					contrShop.setContractID(agreement.getPp6());
					contrShop.setWorkID(detail.getWorkID());
					List<ZxCtSuppliesContrShopList> contrShopList = zxCtSuppliesContrShopListMapper.selectByZxCtSuppliesContrShopListList(contrShop);
					if(contrShopList.size()>0) {
						contrShop = contrShopList.get(0);
						if(contrShop.getChangeContractSum() != null) {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));							
						}else {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getContractSumTax()));							
						}
					}
				} else {
				//如果首期，则查询合同清单
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}
			//查询上期末相关金额字段，如果有值，则加上
			if(agreement.getUpAlterContractSumTax() != null) {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//本期不含税增减金额(万元)				
			}else {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
			}
//				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
//				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
//				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
				agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);
	        // 变更后税额(万元)
	        replenishShopList.setReplyAmountTax(agreement.getAlterContractSumTax());
	        // 变更后含税金额(万元)
	        replenishShopList.setReplyAmount(agreement.getAlterContractSum());
	        // 变更后不含税金额(万元)
	        replenishShopList.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
	        // 本期增减税额(万元)
	        replenishShopList.setApplyAmountTax(agreement.getPp4Tax());
	        // 本期含税增减金额(万元)
	        replenishShopList.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
	        // 本期不含税增减金额(万元)
	        replenishShopList.setApplyAmountNoTax(agreement.getPp4NoTax());			
		}
        // (IP的合同ID)
        replenishShopList.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
        replenishShopList.setCreateUserInfo(userKey, realName);
        zxCtSuppliesContrReplenishShopListMapper.insert(replenishShopList);		        
        //新增附件
   	    if(zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList()!= null && zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList().size()>0) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(replenishShopList.getZxCtSuppliesContrReplenishShopListId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	file.setModifyUserInfo(userKey, realName);
        	if(fileList.size()>0) {
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
        	}
     	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList()) {
                zxErpFile.setUid(UuidUtil.generate());
                zxErpFile.setOtherId(replenishShopList.getZxCtSuppliesContrReplenishShopListId());
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);  
     	   }
        }      
	}else {
		//新增清单
		ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList = new ZxCtSuppliesContrReplenishLeaseList();		
		   zxCtSuppliesContrReplenishLeaseList.setZxCtSuppliesContrReplenishLeaseListId(UuidUtil.generate());
           // 上期末变更后税额
		   zxCtSuppliesContrReplenishLeaseList.setUpAlterContractSumTax(agreement.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
		   zxCtSuppliesContrReplenishLeaseList.setUpAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
           // 上期末变更后金额
		   zxCtSuppliesContrReplenishLeaseList.setUpAlterContractSum(agreement.getUpAlterContractSum());
           // 批复单位
		   zxCtSuppliesContrReplenishLeaseList.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
           // 批复日期
		   zxCtSuppliesContrReplenishLeaseList.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
           // 机构ID
		   zxCtSuppliesContrReplenishLeaseList.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
           // 含税合同金额(万元)
		   zxCtSuppliesContrReplenishLeaseList.setPp2(zxCtSuppliesContrReplenishAgreement.getPp2());
           // 补充协议书ID
		   zxCtSuppliesContrReplenishLeaseList.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
           // 变更原因
		   zxCtSuppliesContrReplenishLeaseList.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
           // 变更内容
		   zxCtSuppliesContrReplenishLeaseList.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
		   // 备注
		   zxCtSuppliesContrReplenishLeaseList.setRemarks(zxCtSuppliesContrReplenishAgreement.getRemarks());
		if(zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList() != null) {
			zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList().parallelStream().forEach((detial)->{
				ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishDetail = detial;		
			   ZxCtSuppliesContrReplenishLeaseDetail dbZxCtSuppliesContrReplenishLeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
			   dbZxCtSuppliesContrReplenishLeaseDetail.setZxCtSuppliesContrReplenishLeaseDetailId(UuidUtil.generate());
	           // 租期单位
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRentUnit(zxCtSuppliesContrReplenishDetail.getRentUnit());
	           // 租期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrTrrm(zxCtSuppliesContrReplenishDetail.getContrTrrm());
	           // 要求修改
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRequestEdit(zxCtSuppliesContrReplenishDetail.getRequestEdit());
	           // 修改日期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditDate(zxCtSuppliesContrReplenishDetail.getEditDate());
	           // 修改人
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserID(zxCtSuppliesContrReplenishDetail.getEditUserID());
	           // 修改人
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserName(zxCtSuppliesContrReplenishDetail.getEditUserName());
	           // 物资名称
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkName(zxCtSuppliesContrReplenishDetail.getWorkName());
	           // 物资类别ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkTypeID(zxCtSuppliesContrReplenishDetail.getWorkTypeID());
	           // 物资类别
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkType(zxCtSuppliesContrReplenishDetail.getWorkType());
	           // 物资规格
	           dbZxCtSuppliesContrReplenishLeaseDetail.setSpec(zxCtSuppliesContrReplenishDetail.getSpec());
	           // 物资编码ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkID(zxCtSuppliesContrReplenishDetail.getWorkID());
	           // 物资编码
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkNo(zxCtSuppliesContrReplenishDetail.getWorkNo());
	           // 税率(%)
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTaxRate(zxCtSuppliesContrReplenishDetail.getTaxRate());
	           // 数量
	           dbZxCtSuppliesContrReplenishLeaseDetail.setQty(zxCtSuppliesContrReplenishDetail.getQty());
	           // 是否网价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setIsNetPrice(zxCtSuppliesContrReplenishDetail.getIsNetPrice());
	           // 实际开始时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualStartDate(zxCtSuppliesContrReplenishDetail.getActualStartDate());
	           // 实际结束时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualEndDate(zxCtSuppliesContrReplenishDetail.getActualEndDate());
	           // 上期末变更后税额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumTax(zxCtSuppliesContrReplenishDetail.getUpAlterContractSumTax());
	           // 上期末变更后金额不含税
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishDetail.getUpAlterContractSumNoTax());
	           // 上期末变更后金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSum(zxCtSuppliesContrReplenishDetail.getUpAlterContractSum());
	           // 界面展现类型
	           dbZxCtSuppliesContrReplenishLeaseDetail.setViewType(zxCtSuppliesContrReplenishDetail.getViewType());
	           // 计划开始时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanStartDate(zxCtSuppliesContrReplenishDetail.getPlanStartDate());
	           // 计划结束时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanEndDate(zxCtSuppliesContrReplenishDetail.getPlanEndDate());
	           // 合同明细ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrItemID(zxCtSuppliesContrReplenishDetail.getContrItemID());
	           // 合同变更ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(zxCtSuppliesContrReplenishDetail.getContrAlterID());
	           // 含税合同金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSum(zxCtSuppliesContrReplenishDetail.getContractSum());
	           // 含税合同单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPrice(zxCtSuppliesContrReplenishDetail.getPrice());
	           // 单位
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUnit(zxCtSuppliesContrReplenishDetail.getUnit());
	           // 单位
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTreenode(zxCtSuppliesContrReplenishDetail.getTreenode());
	           // 不含税税额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumTax(zxCtSuppliesContrReplenishDetail.getContractSumTax());
	           // 不含税合同金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumNoTax(zxCtSuppliesContrReplenishDetail.getContractSumNoTax());
	           // 不含税合同单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPriceNoTax(zxCtSuppliesContrReplenishDetail.getPriceNoTax());
	           // 变更日期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeDate(zxCtSuppliesContrReplenishDetail.getChangeDate());
	           // 变更类型
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterType(zxCtSuppliesContrReplenishDetail.getAlterType());
	           // 变更后租期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterTrrm(zxCtSuppliesContrReplenishDetail.getAlterTrrm());
	           // 变更后税额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishDetail.getChangeContractSumTax());
	           // 变更后数量
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeQty(zxCtSuppliesContrReplenishDetail.getChangeQty());
	           // 变更后含税金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSum(zxCtSuppliesContrReplenishDetail.getChangeContractSum());
	           // 变更后含税单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePrice(zxCtSuppliesContrReplenishDetail.getChangePrice());
	           // 变更后不含税金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishDetail.getChangeContractSumNoTax());
	           // 变更后不含税单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishDetail.getChangePriceNoTax());
	           // 备注
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRemarks(zxCtSuppliesContrReplenishDetail.getRemarks());
	           // 排序
	           dbZxCtSuppliesContrReplenishLeaseDetail.setSort(zxCtSuppliesContrReplenishDetail.getSort());
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(zxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId());
	           dbZxCtSuppliesContrReplenishLeaseDetail.setCreateUserInfo(userKey, realName);
				zxCtSuppliesContrReplenishLeaseDetailMapper.insert(dbZxCtSuppliesContrReplenishLeaseDetail);     	
			});
			ZxCtSuppliesContrReplenishLeaseDetail lease = new ZxCtSuppliesContrReplenishLeaseDetail();
			lease.setContrAlterID(zxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId());
			lease.setAlterType("1");
			List<ZxCtSuppliesContrReplenishLeaseDetail> addShopList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(lease);
			lease.setAlterType("2");
			List<ZxCtSuppliesContrReplenishLeaseDetail> editShopList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(lease);
			for(ZxCtSuppliesContrReplenishLeaseDetail detail : editShopList) {
				//如果存在上期，则查询对应的补充协议清单
				if(agreement.getUpAlterContractSumTax() != null) {
					ZxCtSuppliesContrLeaseList contrShop = new ZxCtSuppliesContrLeaseList();
					contrShop.setContractID(agreement.getPp6());
					contrShop.setWorkID(detail.getWorkID());
					List<ZxCtSuppliesContrLeaseList> contrShopList = zxCtSuppliesContrLeaseListMapper.selectByZxCtSuppliesContrLeaseListList(contrShop);
					if(contrShopList.size()>0) {
						contrShop = contrShopList.get(0);
//						detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
//						detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
//						detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));
						if(contrShop.getChangeContractSum() != null) {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));							
						}else {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getContractSumTax()));							
						}
					}
				} else {
				//如果首期，则查询合同清单
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}			
			//查询上期末相关金额字段，如果有值，则加上
			if(agreement.getUpAlterContractSumTax() != null) {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//本期不含税增减金额(万元)		
			}else {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
			}
//				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
//				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
//				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
				agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);		
	        // 变更后税额(万元)
			zxCtSuppliesContrReplenishLeaseList.setReplyAmountTax(agreement.getAlterContractSumTax());
	        // 变更后含税金额(万元)
			zxCtSuppliesContrReplenishLeaseList.setReplyAmount(agreement.getAlterContractSum());
	        // 变更后不含税金额(万元)
			zxCtSuppliesContrReplenishLeaseList.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
	        // 本期增减税额(万元)
			zxCtSuppliesContrReplenishLeaseList.setApplyAmountTax(agreement.getPp4Tax());
	        // 本期含税增减金额(万元)
			zxCtSuppliesContrReplenishLeaseList.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
	        // 本期不含税增减金额(万元)
			zxCtSuppliesContrReplenishLeaseList.setApplyAmountNoTax(agreement.getPp4NoTax());			
		}
        // (IP的合同ID)
		zxCtSuppliesContrReplenishLeaseList.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
		zxCtSuppliesContrReplenishLeaseList.setCreateUserInfo(userKey, realName);
		zxCtSuppliesContrReplenishLeaseListMapper.insert(zxCtSuppliesContrReplenishLeaseList);		        
        //新增附件
   	    if(zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList()!= null && zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList().size()>0) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	file.setModifyUserInfo(userKey, realName);
        	if(fileList.size()>0) {
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
        	}
     	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList()) {
                zxErpFile.setUid(UuidUtil.generate());
                zxErpFile.setOtherId(zxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId());
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);  
     	   }
        } 
	}
	    return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishAgreement);
	}

	@Override
	public ResponseEntity updateZxCtSupContrReplLeaseDetailBycontrAlterID(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesContrReplenishAgreement agreement = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());        
		//新增变更清单
    	if(StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "WZ") || StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "LC")) {
	        //新增补充协议清单表
            ZxCtSuppliesContrReplenishShopList replenishShop = new ZxCtSuppliesContrReplenishShopList();
            replenishShop.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId()); 
            List<ZxCtSuppliesContrReplenishShopList> replenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(replenishShop);
            replenishShop = replenishShopList.get(0);
            // 批复日期
            replenishShop.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
            // 批复单位
            replenishShop.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
            // 变更原因
            replenishShop.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
            // 变更内容
            replenishShop.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
            //新增附件
       	    if(zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList()!= null && zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(replenishShop.getZxCtSuppliesContrReplenishShopListId());
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, realName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(replenishShop.getZxCtSuppliesContrReplenishShopListId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);  
         	   }
            }    
    		ZxCtSuppliesContrReplenishShopDetail replenishShopDetail = new ZxCtSuppliesContrReplenishShopDetail();
    		replenishShopDetail.setContrAlterID(replenishShop.getZxCtSuppliesContrReplenishShopListId());
    		List<ZxCtSuppliesContrReplenishShopDetail> detailList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(replenishShopDetail);
    		if(detailList.size()>0) {
    			replenishShopDetail.setModifyUserInfo(userKey, realName);
    			zxCtSuppliesContrReplenishShopDetailMapper.batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(detailList, replenishShopDetail);
    		}
		if(zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList() != null) {
			for(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopList : zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList()) {
//			zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList().parallelStream().forEach((detial)->{
//				ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopList = detial;
				//新增
				    ZxCtSuppliesContrReplenishShopDetail shopDetail = new ZxCtSuppliesContrReplenishShopDetail();
				    shopDetail.setZxCtSuppliesContrReplenishShopDetailId(UuidUtil.generate());
		            shopDetail.setContrAlterID(replenishShop.getZxCtSuppliesContrReplenishShopListId());
		            shopDetail.setAlterType(zxCtSuppliesContrReplenishShopList.getAlterType());
		            // 租期单位
		            shopDetail.setRentUnit(zxCtSuppliesContrReplenishShopList.getRentUnit());
		            // 租期
		            shopDetail.setContrTrrm(zxCtSuppliesContrReplenishShopList.getContrTrrm());
		            // 物资名称
		            shopDetail.setWorkName(zxCtSuppliesContrReplenishShopList.getWorkName());
		            // 物资类别ID
		            shopDetail.setWorkTypeID(zxCtSuppliesContrReplenishShopList.getWorkTypeID());
		            // 物资类别
		            shopDetail.setWorkType(zxCtSuppliesContrReplenishShopList.getWorkType());
		            // 物资编码ID
		            shopDetail.setWorkID(zxCtSuppliesContrReplenishShopList.getWorkID());
		            // 物资编码
		            shopDetail.setWorkNo(zxCtSuppliesContrReplenishShopList.getWorkNo());
		            // 税率(%)
		            shopDetail.setTaxRate(zxCtSuppliesContrReplenishShopList.getTaxRate());
		            // 数量
		            shopDetail.setQty(zxCtSuppliesContrReplenishShopList.getQty());
		            // 是否网价
		            shopDetail.setIsNetPrice(zxCtSuppliesContrReplenishShopList.getIsNetPrice());
		            // 实际开始时间
		            shopDetail.setActualStartDate(zxCtSuppliesContrReplenishShopList.getActualStartDate());
		            // 实际结束时间
		            shopDetail.setActualEndDate(zxCtSuppliesContrReplenishShopList.getActualEndDate());
		            // 界面展现类型
		            shopDetail.setViewType(zxCtSuppliesContrReplenishShopList.getViewType());
		            // 计划开始时间
		            shopDetail.setPlanStartDate(zxCtSuppliesContrReplenishShopList.getPlanStartDate());
		            // 计划结束时间
		            shopDetail.setPlanEndDate(zxCtSuppliesContrReplenishShopList.getPlanEndDate());
		            // 含税金额
		            shopDetail.setContractSum(zxCtSuppliesContrReplenishShopList.getContractSum());
		            // 含税单价
		            shopDetail.setPrice(zxCtSuppliesContrReplenishShopList.getPrice());
		            // 规格型号
		            shopDetail.setSpec(zxCtSuppliesContrReplenishShopList.getSpec());
		            // 单位
		            shopDetail.setUnit(zxCtSuppliesContrReplenishShopList.getUnit());
		            // 单位
		            shopDetail.setTreenode(zxCtSuppliesContrReplenishShopList.getTreenode());
		            // 不含税金额
		            shopDetail.setContractSumNoTax(zxCtSuppliesContrReplenishShopList.getContractSumNoTax());
		            // 不含税单价
		            shopDetail.setPriceNoTax(zxCtSuppliesContrReplenishShopList.getPriceNoTax());
		            // 变更日期
		            shopDetail.setChangeDate(zxCtSuppliesContrReplenishShopList.getChangeDate());
		            // 变更后租期
		            shopDetail.setAlterTrrm(zxCtSuppliesContrReplenishShopList.getAlterTrrm());
		            // 变更后数量
		            shopDetail.setChangeQty(zxCtSuppliesContrReplenishShopList.getChangeQty());
		            // 变更后含税金额
		            shopDetail.setChangeContractSum(zxCtSuppliesContrReplenishShopList.getChangeContractSum());
		            // 变更后含税单价
		            shopDetail.setChangePrice(zxCtSuppliesContrReplenishShopList.getChangePrice());
		            // 变更后不含税金额
		            shopDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumNoTax());
		            
		            shopDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumTax());
		            // 变更后不含税单价
		            shopDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishShopList.getChangePriceNoTax());
		            // 备注
		            shopDetail.setRemarks(replenishShop.getRemarks());
		            shopDetail.setCreateUserInfo(userKey, realName);
		            zxCtSuppliesContrReplenishShopDetailMapper.insert(shopDetail);
		}
//			});
			ZxCtSuppliesContrReplenishShopDetail shop = new ZxCtSuppliesContrReplenishShopDetail();
			shop.setContrAlterID(replenishShop.getZxCtSuppliesContrReplenishShopListId());
			shop.setAlterType("1");
			List<ZxCtSuppliesContrReplenishShopDetail> addShopList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(shop);
			shop.setAlterType("2");
			List<ZxCtSuppliesContrReplenishShopDetail> editShopList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(shop);
			for(ZxCtSuppliesContrReplenishShopDetail detail : editShopList) {
				//如果存在上期，则查询对应的补充协议清单
				if(agreement.getUpAlterContractSumTax() != null) {
					ZxCtSuppliesContrShopList contrShop = new ZxCtSuppliesContrShopList();
					contrShop.setContractID(agreement.getPp6());
					contrShop.setWorkID(detail.getWorkID());
					List<ZxCtSuppliesContrShopList> contrShopList = zxCtSuppliesContrShopListMapper.selectByZxCtSuppliesContrShopListList(contrShop);
					if(contrShopList.size()>0) {
						contrShop = contrShopList.get(0);
//						detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
//						detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
//						detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));
						if(contrShop.getChangeContractSum() != null) {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));							
						}else {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getContractSumTax()));							
						}
					}
				} else {
				//如果首期，则查询合同清单
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}
			//查询上期末相关金额字段，如果有值，则加上
			if(agreement.getUpAlterContractSumTax() != null) {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//本期不含税增减金额(万元)		
			}else {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
				
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
			}
				
//			agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
//			agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
//			agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
			agreement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);				
            // 变更后税额(万元)
            replenishShop.setReplyAmountTax(agreement.getAlterContractSumTax());
            // 变更后含税金额(万元)
            replenishShop.setReplyAmount(agreement.getAlterContractSum());
            // 变更后不含税金额(万元)
            replenishShop.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
            // 本期增减税额(万元)
            replenishShop.setApplyAmountTax(agreement.getPp4Tax());
            // 本期含税增减金额(万元)
            replenishShop.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
            // 本期不含税增减金额(万元)
            replenishShop.setApplyAmountNoTax(agreement.getPp4NoTax());
            replenishShop.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishShopListMapper.updateByPrimaryKeySelective(replenishShop);		
		}else {
			if(agreement.getUpAlterContractSumTax() != null) {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(agreement.getUpAlterContractSum());
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
				//变更后税额(万元)
				agreement.setAlterContractSumTax(agreement.getUpAlterContractSumTax());
	            // 变更后税额(万元)
				replenishShop.setReplyAmountTax(agreement.getUpAlterContractSumTax());
	            // 变更后含税金额(万元)
				replenishShop.setReplyAmount(agreement.getUpAlterContractSum());
	            // 变更后不含税金额(万元)
	            replenishShop.setReplyAmountNoTax(agreement.getUpAlterContractSumNoTax());
			}else {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(new BigDecimal(0));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(new BigDecimal(0));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(new BigDecimal(0));
	            // 变更后税额(万元)
				replenishShop.setReplyAmountTax(new BigDecimal(0));
	            // 变更后含税金额(万元)
				replenishShop.setReplyAmount(new BigDecimal(0));
	            // 变更后不含税金额(万元)
	            replenishShop.setReplyAmountNoTax(new BigDecimal(0));				
			}
			agreement.setPp4("0");//本期含税增减金额(万元)
			agreement.setPp4NoTax(new BigDecimal(0));//本期增减税额(万元)
			agreement.setPp4Tax(new BigDecimal(0));//本期不含税增减金额(万元)
			agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);	
            // 本期增减税额(万元)
            replenishShop.setApplyAmountTax(new BigDecimal(0));
            // 本期含税增减金额(万元)
            replenishShop.setApplyAmount(new BigDecimal(0));
            // 本期不含税增减金额(万元)
            replenishShop.setApplyAmountNoTax(new BigDecimal(0));
            replenishShop.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishShopListMapper.updateByPrimaryKeySelective(replenishShop);					
		}
	}else {
        //新增补充协议清单表
		ZxCtSuppliesContrReplenishLeaseList replenishLease = new ZxCtSuppliesContrReplenishLeaseList();
		replenishLease.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId()); 
        List<ZxCtSuppliesContrReplenishLeaseList> replenishLeaseList = zxCtSuppliesContrReplenishLeaseListMapper.selectByZxCtSuppliesContrReplenishLeaseListList(replenishLease);
        replenishLease = replenishLeaseList.get(0);
        // 批复日期
        replenishLease.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
        // 批复单位
        replenishLease.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
        // 变更原因
        replenishLease.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
        // 变更内容
        replenishLease.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
        //新增附件
   	    if(zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList()!= null && zxCtSuppliesContrReplenishAgreement.getReplenishShopListFileList().size()>0) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(replenishLease.getZxCtSuppliesContrReplenishLeaseListId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	file.setModifyUserInfo(userKey, realName);
        	if(fileList.size()>0) {
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
        	}
     	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getReplenishAgreementFileList()) {
                zxErpFile.setUid(UuidUtil.generate());
                zxErpFile.setOtherId(replenishLease.getZxCtSuppliesContrReplenishLeaseListId());
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);  
     	   }
        } 
   	    ZxCtSuppliesContrReplenishLeaseDetail LeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
   	       LeaseDetail.setContrAlterID(replenishLease.getZxCtSuppliesContrReplenishLeaseListId());
		List<ZxCtSuppliesContrReplenishLeaseDetail> detailList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(LeaseDetail);
		if(detailList.size()>0) {
			LeaseDetail.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishLeaseDetailMapper.batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(detailList, LeaseDetail);
		}
		if(zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList() != null) {
			for(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseList : zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList()) {
				
//			zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList().parallelStream().forEach((detial)->{
//				ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseList = detial;		
			   ZxCtSuppliesContrReplenishLeaseDetail dbZxCtSuppliesContrReplenishLeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
			   dbZxCtSuppliesContrReplenishLeaseDetail.setZxCtSuppliesContrReplenishLeaseDetailId(UuidUtil.generate());
	           // 租期单位
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRentUnit(zxCtSuppliesContrReplenishLeaseList.getRentUnit());
	           // 租期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrTrrm(zxCtSuppliesContrReplenishLeaseList.getContrTrrm());
	           // 要求修改
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRequestEdit(zxCtSuppliesContrReplenishLeaseList.getRequestEdit());
	           // 修改日期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditDate(zxCtSuppliesContrReplenishLeaseList.getEditDate());
	           // 修改人
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserID(zxCtSuppliesContrReplenishLeaseList.getEditUserID());
	           // 修改人
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserName(zxCtSuppliesContrReplenishLeaseList.getEditUserName());
	           // 物资名称
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkName(zxCtSuppliesContrReplenishLeaseList.getWorkName());
	           // 物资类别ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkTypeID(zxCtSuppliesContrReplenishLeaseList.getWorkTypeID());
	           // 物资类别
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkType(zxCtSuppliesContrReplenishLeaseList.getWorkType());
	           // 物资规格
	           dbZxCtSuppliesContrReplenishLeaseDetail.setSpec(zxCtSuppliesContrReplenishLeaseList.getSpec());
	           // 物资编码ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkID(zxCtSuppliesContrReplenishLeaseList.getWorkID());
	           // 物资编码
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkNo(zxCtSuppliesContrReplenishLeaseList.getWorkNo());
	           // 税率(%)
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTaxRate(zxCtSuppliesContrReplenishLeaseList.getTaxRate());
	           // 数量
	           dbZxCtSuppliesContrReplenishLeaseDetail.setQty(zxCtSuppliesContrReplenishLeaseList.getQty());
	           // 是否网价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setIsNetPrice(zxCtSuppliesContrReplenishLeaseList.getIsNetPrice());
	           // 实际开始时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualStartDate(zxCtSuppliesContrReplenishLeaseList.getActualStartDate());
	           // 实际结束时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualEndDate(zxCtSuppliesContrReplenishLeaseList.getActualEndDate());
	           // 上期末变更后税额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumTax(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSumTax());
	           // 上期末变更后金额不含税
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSumNoTax());
	           // 上期末变更后金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSum(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSum());
	           // 界面展现类型
	           dbZxCtSuppliesContrReplenishLeaseDetail.setViewType(zxCtSuppliesContrReplenishLeaseList.getViewType());
	           // 计划开始时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanStartDate(zxCtSuppliesContrReplenishLeaseList.getPlanStartDate());
	           // 计划结束时间
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanEndDate(zxCtSuppliesContrReplenishLeaseList.getPlanEndDate());
	           // 合同明细ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrItemID(zxCtSuppliesContrReplenishLeaseList.getContrItemID());
	           // 合同变更ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(zxCtSuppliesContrReplenishLeaseList.getContrAlterID());
	           // 含税合同金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSum(zxCtSuppliesContrReplenishLeaseList.getContractSum());
	           // 含税合同单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPrice(zxCtSuppliesContrReplenishLeaseList.getPrice());
	           // 单位
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUnit(zxCtSuppliesContrReplenishLeaseList.getUnit());
	           // 单位
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTreenode(zxCtSuppliesContrReplenishLeaseList.getTreenode());
	           // 不含税税额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumTax(zxCtSuppliesContrReplenishLeaseList.getContractSumTax());
	           // 不含税合同金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getContractSumNoTax());
	           // 不含税合同单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPriceNoTax(zxCtSuppliesContrReplenishLeaseList.getPriceNoTax());
	           // 变更日期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeDate(zxCtSuppliesContrReplenishLeaseList.getChangeDate());
	           // 变更类型
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterType(zxCtSuppliesContrReplenishLeaseList.getAlterType());
	           // 变更后租期
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterTrrm(zxCtSuppliesContrReplenishLeaseList.getAlterTrrm());
	           // 变更后税额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishLeaseList.getChangeContractSumTax());
	           // 变更后数量
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeQty(zxCtSuppliesContrReplenishLeaseList.getChangeQty());
	           // 变更后含税金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSum(zxCtSuppliesContrReplenishLeaseList.getChangeContractSum());
	           // 变更后含税单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePrice(zxCtSuppliesContrReplenishLeaseList.getChangePrice());
	           // 变更后不含税金额
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getChangeContractSumNoTax());
	           // 变更后不含税单价
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishLeaseList.getChangePriceNoTax());
	           // 备注
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRemarks(zxCtSuppliesContrReplenishLeaseList.getRemarks());
	           // 排序
	           dbZxCtSuppliesContrReplenishLeaseDetail.setSort(zxCtSuppliesContrReplenishLeaseList.getSort());
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(replenishLease.getZxCtSuppliesContrReplenishLeaseListId());
	           dbZxCtSuppliesContrReplenishLeaseDetail.setCreateUserInfo(userKey, realName);
				zxCtSuppliesContrReplenishLeaseDetailMapper.insert(dbZxCtSuppliesContrReplenishLeaseDetail);     	
//			});
		}
			ZxCtSuppliesContrReplenishLeaseDetail lease = new ZxCtSuppliesContrReplenishLeaseDetail();
			lease.setContrAlterID(replenishLease.getZxCtSuppliesContrReplenishLeaseListId());
			lease.setAlterType("1");
			List<ZxCtSuppliesContrReplenishLeaseDetail> addShopList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(lease);
			lease.setAlterType("2");
			List<ZxCtSuppliesContrReplenishLeaseDetail> editShopList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(lease);
			for(ZxCtSuppliesContrReplenishLeaseDetail detail : editShopList) {
				//如果存在上期，则查询对应的补充协议清单
				if(agreement.getUpAlterContractSumTax() != null) {
					ZxCtSuppliesContrLeaseList contrShop = new ZxCtSuppliesContrLeaseList();
					contrShop.setContractID(agreement.getPp6());
					contrShop.setWorkID(detail.getWorkID());
					List<ZxCtSuppliesContrLeaseList> contrShopList = zxCtSuppliesContrLeaseListMapper.selectByZxCtSuppliesContrLeaseListList(contrShop);
					if(contrShopList.size()>0) {
						contrShop = contrShopList.get(0);
//						detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
//						detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
//						detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));
						if(contrShop.getChangeContractSum() != null) {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getChangeContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getChangeContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getChangeContractSumTax()));							
						}else {
							detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), contrShop.getContractSum()));
							detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), contrShop.getContractSumNoTax()));
							detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), contrShop.getContractSumTax()));							
						}
					}
				} else {
				//如果首期，则查询合同清单
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}			
			//查询上期末相关金额字段，如果有值，则加上
			if(agreement.getUpAlterContractSumTax() != null) {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//本期不含税增减金额(万元)		
			}else {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//本期含税增减金额(万元)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//本期增减税额(万元)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//本期不含税增减金额(万元)
			}
				agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);	
            // 变更后税额(万元)
			replenishLease.setReplyAmountTax(agreement.getAlterContractSumTax());
            // 变更后含税金额(万元)
            replenishLease.setReplyAmount(agreement.getAlterContractSum());
            // 变更后不含税金额(万元)
            replenishLease.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
            // 本期增减税额(万元)
            replenishLease.setApplyAmountTax(agreement.getPp4Tax());
            // 本期含税增减金额(万元)
            replenishLease.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
            // 本期不含税增减金额(万元)
            replenishLease.setApplyAmountNoTax(agreement.getPp4NoTax());
            replenishLease.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishLeaseListMapper.updateByPrimaryKeySelective(replenishLease);				
		}else {
			if(agreement.getUpAlterContractSumTax() != null) {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(agreement.getUpAlterContractSum());
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
				//变更后税额(万元)
				agreement.setAlterContractSumTax(agreement.getUpAlterContractSumTax());
	            // 变更后税额(万元)
				replenishLease.setReplyAmountTax(agreement.getUpAlterContractSumTax());
	            // 变更后含税金额(万元)
	            replenishLease.setReplyAmount(agreement.getUpAlterContractSum());
	            // 变更后不含税金额(万元)
	            replenishLease.setReplyAmountNoTax(agreement.getUpAlterContractSumNoTax());
			}else {
				//变更后含税金额(万元)
				agreement.setAlterContractSum(new BigDecimal(0));
				//变更后不含税金额(万元)
				agreement.setAlterContractSumNoTax(new BigDecimal(0));
				//变更后税额(万元)
				agreement.setAlterContractSumTax(new BigDecimal(0));
	            // 变更后税额(万元)
				replenishLease.setReplyAmountTax(new BigDecimal(0));
	            // 变更后含税金额(万元)
	            replenishLease.setReplyAmount(new BigDecimal(0));
	            // 变更后不含税金额(万元)
	            replenishLease.setReplyAmountNoTax(new BigDecimal(0));				
			}
			agreement.setPp4("0");//本期含税增减金额(万元)
			agreement.setPp4NoTax(new BigDecimal(0));//本期增减税额(万元)
			agreement.setPp4Tax(new BigDecimal(0));//本期不含税增减金额(万元)
			agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);	
            // 本期增减税额(万元)
            replenishLease.setApplyAmountTax(new BigDecimal(0));
            // 本期含税增减金额(万元)
            replenishLease.setApplyAmount(new BigDecimal(0));
            // 本期不含税增减金额(万元)
            replenishLease.setApplyAmountNoTax(new BigDecimal(0));
            replenishLease.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishLeaseListMapper.updateByPrimaryKeySelective(replenishLease);					
		}
	}
	    return repEntity.ok("sys.data.update", zxCtSuppliesContrReplenishAgreement);
	}

	@Override
	public ResponseEntity getZxCtSuppliesContrReplenishFlowDetail(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
		ZxCtSuppliesContrReplenishAgreement agreement = zxCtSuppliesContrReplenishAgreementMapper.getDetailByWorkId(zxCtSuppliesContrReplenishAgreement.getWorkId());
//		String otherId = "";
		if(agreement != null) {
			if(StrUtil.equals(agreement.getCode7(), "WZ") || StrUtil.equals(agreement.getCode7(), "LC")) {
				ZxCtSuppliesContrReplenishShopList shop = new ZxCtSuppliesContrReplenishShopList();
				shop.setPp3(agreement.getReplenishAgreementId());
				List<ZxCtSuppliesContrReplenishShopList> shopList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(shop);
				ZxCtSuppliesContrReplenishShopDetail detail = new ZxCtSuppliesContrReplenishShopDetail();
				detail.setContrAlterID(shopList.get(0).getZxCtSuppliesContrReplenishShopListId());
				agreement.setReplyUnit(shopList.get(0).getPp1());
				agreement.setReplyDate(shopList.get(0).getReplyDate());
				agreement.setAlterReason(shopList.get(0).getAlterReason());
				agreement.setAlterContent(shopList.get(0).getAlterContent());
				agreement.setReplenishShopDetailedList(zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(detail));
			}else if(StrUtil.equals(agreement.getCode7(), "WL")){
				ZxCtSuppliesContrReplenishLeaseList shop = new ZxCtSuppliesContrReplenishLeaseList();
				shop.setPp3(agreement.getReplenishAgreementId());
				List<ZxCtSuppliesContrReplenishLeaseList> shopList = zxCtSuppliesContrReplenishLeaseListMapper.selectByZxCtSuppliesContrReplenishLeaseListList(shop);;
				ZxCtSuppliesContrReplenishLeaseDetail detail = new ZxCtSuppliesContrReplenishLeaseDetail();
				detail.setContrAlterID(shopList.get(0).getZxCtSuppliesContrReplenishLeaseListId());
				agreement.setReplyUnit(shopList.get(0).getPp1());
				agreement.setReplyDate(shopList.get(0).getReplyDate());
				agreement.setAlterReason(shopList.get(0).getAlterReason());
				agreement.setAlterContent(shopList.get(0).getAlterContent());
				agreement.setReplenishLeaseDetailedList(zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(detail));
			}
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(agreement.getReplenishAgreementId());
        	List<ZxErpFile> agreeFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	agreement.setReplenishAgreementFileList(agreeFileList);
//        	file.setOtherId(otherId);
//        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
//        	fileList = agreeFileList.add(fileList);
//        	agreement.setReplenishAgreementFileList();
		}
        return repEntity.ok(agreement);		
	}

	@Override
	public ResponseEntity addZxCtSuppliesContrReplenishAgreementFlow(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesContrReplenishAgreement dbZxCtSuppliesContrApply = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        if (dbZxCtSuppliesContrApply != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApply.getReplenishAgreementId())) {
        	dbZxCtSuppliesContrApply.setApih5FlowStatus("-1");
        	//未评审:0;正在评审:1;评审通过:2;评审不通过:3;重新评审:4;评审终止:5;评审通过:6
        	dbZxCtSuppliesContrApply.setStatus("1");
        	dbZxCtSuppliesContrApply.setWorkId(zxCtSuppliesContrReplenishAgreement.getWorkId());
        	dbZxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
        	zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKey(dbZxCtSuppliesContrApply);
        	if(zxCtSuppliesContrReplenishAgreement.getDocumentFileList() != null) {        		
        		for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getDocumentFileList()) {
        			zxErpFile.setUid(UuidUtil.generate());
        			zxErpFile.setOtherType("1");
        			zxErpFile.setOtherId(dbZxCtSuppliesContrApply.getReplenishAgreementId());
        			zxErpFile.setCreateUserInfo(userKey, realName);
        			zxErpFileMapper.insert(zxErpFile);  
        		}           	
        	}
        }
        return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishAgreement);
	}

	@Override
	public ResponseEntity updateZxCtSuppliesContrReplenishAgreementFlow(
			ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);		
		String userName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtSuppliesContrReplenishAgreement.getApih5FlowStatus().equals("1")) {
			ZxCtSuppliesContrReplenishAgreement flowDetail = zxCtSuppliesContrReplenishAgreementMapper
					.getDetailByWorkId(zxCtSuppliesContrReplenishAgreement.getWorkId());
			// 申请负责单位
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// 公司办公室
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// 部门领导意见
			if (StrUtil.equals("opinionField3", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setWorkId(zxCtSuppliesContrReplenishAgreement.getWorkId());
			flowDetail.setApih5FlowStatus("1");
			flowDetail.setModifyUserInfo(userKey, userName);
			flag = zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKey(flowDetail);
			if (flag == 0) {
				return repEntity.errorSave();
			} else {
	        	if(zxCtSuppliesContrReplenishAgreement.getDocumentFileList() != null && zxCtSuppliesContrReplenishAgreement.getDocumentFileList().size()>0) {
	            	ZxErpFile file = new ZxErpFile();
	            	file.setOtherId(flowDetail.getReplenishAgreementId());
	            	file.setOtherType("1");
	            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
	            	file.setModifyUserInfo(userKey, userName);
	            	if(fileList.size()>0) {
	            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
	            	}
	         	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getDocumentFileList()) {
	                    zxErpFile.setUid(UuidUtil.generate());
	                    zxErpFile.setOtherId(flowDetail.getReplenishAgreementId());
	                    zxErpFile.setOtherType("1");
	                    zxErpFile.setCreateUserInfo(userKey, userName);
	                    zxErpFileMapper.insert(zxErpFile);  
	         	   }        		
	        	}				
				return repEntity.ok("sys.data.update", flowDetail);
			}
		} else if (zxCtSuppliesContrReplenishAgreement.getApih5FlowStatus().equals("2")) {
			ZxCtSuppliesContrReplenishAgreement flowDetail = zxCtSuppliesContrReplenishAgreementMapper
					.getDetailByWorkId(zxCtSuppliesContrReplenishAgreement.getWorkId());
			// 申请负责单位
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// 公司办公室
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// 部门领导意见
			if (StrUtil.equals("opinionField3", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setApih5FlowStatus("2");
			flag = zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(flowDetail);
        	//审批通过，直接将清单数据同步到合同清单表里
			if(StrUtil.equals(flowDetail.getCode7(), "WZ") || StrUtil.equals(flowDetail.getCode7(), "LC")) {
				ZxCtSuppliesContrReplenishShopList replenishShop = new ZxCtSuppliesContrReplenishShopList();
				replenishShop.setPp3(flowDetail.getReplenishAgreementId());
				List<ZxCtSuppliesContrReplenishShopList> replenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(replenishShop);
				if(replenishShopList.size()>0) {
					ZxCtSuppliesContrReplenishShopDetail replenishShopDetail = new ZxCtSuppliesContrReplenishShopDetail();
					replenishShopDetail.setContrAlterID(replenishShopList.get(0).getZxCtSuppliesContrReplenishShopListId());
					List<ZxCtSuppliesContrReplenishShopDetail> shopDetailList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(replenishShopDetail);
					for(ZxCtSuppliesContrReplenishShopDetail shopDetail : shopDetailList) {
						if(StrUtil.equals(shopDetail.getAlterType(), "1")) {//新增
							ZxCtSuppliesContrShopList shop = new ZxCtSuppliesContrShopList();
							shop.setZxCtSuppliesContrShopListId(UuidUtil.generate());
							shop.setWorkID(shopDetail.getWorkID());
							shop.setWorkName(shopDetail.getWorkName());
							shop.setWorkNo(shopDetail.getWorkNo());
							shop.setWorkType(shopDetail.getWorkType());
							shop.setWorkTypeID(shopDetail.getWorkTypeID());
							shop.setChangeContractSum(shopDetail.getChangeContractSum());
							shop.setChangeContractSumNoTax(shopDetail.getChangeContractSumNoTax());
							shop.setChangePrice(shopDetail.getChangePrice());
							shop.setChangeContractSumTax(shopDetail.getChangeContractSumTax());
							shop.setChangePriceNoTax(shopDetail.getChangePriceNoTax());
							shop.setChangeQty(shopDetail.getChangeQty());
							shop.setChangeDate(shopDetail.getChangeDate());
							shop.setTaxRate(shopDetail.getTaxRate());
							shop.setUnit(shopDetail.getUnit());
							shop.setSpec(shopDetail.getSpec());
							shop.setIsNetPrice(shopDetail.getIsNetPrice());
							shop.setQty(shopDetail.getChangeQty());
							shop.setPrice(shopDetail.getChangePrice());
							shop.setContractSumTax(shopDetail.getChangeContractSumTax());
							shop.setContractSumNoTax(shopDetail.getChangeContractSumNoTax());
							shop.setContractSum(shopDetail.getChangeContractSum());
							shop.setPriceNoTax(shopDetail.getChangePrice());
							shop.setContractID(flowDetail.getPp6());
							shop.setChangeDate(new Date());
							shop.setCreateUserInfo(userKey, userName);
							zxCtSuppliesContrShopListMapper.insert(shop);
						}else if(StrUtil.equals(shopDetail.getAlterType(), "2")){
							ZxCtSuppliesContrShopList contrShop = new ZxCtSuppliesContrShopList();
							contrShop.setContractID(flowDetail.getPp6());
							List<ZxCtSuppliesContrShopList> shopList = zxCtSuppliesContrShopListMapper.selectByZxCtSuppliesContrShopListList(contrShop);
							for(ZxCtSuppliesContrShopList shop : shopList) {
								if(StrUtil.equals(shop.getWorkID(), shopDetail.getWorkID())) {
									shop.setChangeContractSum(shopDetail.getChangeContractSum());
									shop.setChangeContractSumNoTax(shopDetail.getChangeContractSumNoTax());
									shop.setChangeContractSumTax(shopDetail.getChangeContractSumTax());
									shop.setChangePrice(shopDetail.getChangePrice());
									shop.setChangePriceNoTax(shopDetail.getChangePriceNoTax());
									shop.setChangeQty(shopDetail.getChangeQty());
									shop.setChangeDate(new Date());
									shop.setModifyUserInfo(userKey, userName);
									zxCtSuppliesContrShopListMapper.updateByPrimaryKeySelective(shop);
								}
							}
						}
					}
				}				
			}else {
				ZxCtSuppliesContrReplenishLeaseList replenishShop = new ZxCtSuppliesContrReplenishLeaseList();
				replenishShop.setPp3(flowDetail.getReplenishAgreementId());
				List<ZxCtSuppliesContrReplenishLeaseList> replenishShopList = zxCtSuppliesContrReplenishLeaseListMapper.selectByZxCtSuppliesContrReplenishLeaseListList(replenishShop);
				if(replenishShopList.size()>0) {
					ZxCtSuppliesContrReplenishLeaseDetail replenishShopDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
					replenishShopDetail.setContrAlterID(replenishShopList.get(0).getZxCtSuppliesContrReplenishLeaseListId());
					List<ZxCtSuppliesContrReplenishLeaseDetail> shopDetailList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(replenishShopDetail);
					for(ZxCtSuppliesContrReplenishLeaseDetail shopDetail : shopDetailList) {
						if(StrUtil.equals(shopDetail.getAlterType(), "1")) {//新增
							ZxCtSuppliesContrLeaseList lease = new ZxCtSuppliesContrLeaseList();
							lease.setZxCtSuppliesContrLeaseListId(UuidUtil.generate());
							lease.setWorkID(shopDetail.getWorkID());
							lease.setWorkName(shopDetail.getWorkName());
							lease.setWorkNo(shopDetail.getWorkNo());
							lease.setWorkType(shopDetail.getWorkType());
							lease.setWorkTypeID(shopDetail.getWorkTypeID());
							lease.setChangeContractSum(shopDetail.getChangeContractSum());
							lease.setChangeContractSumNoTax(shopDetail.getChangeContractSumNoTax());
							lease.setChangePrice(shopDetail.getChangePrice());
							lease.setChangeContractSumTax(shopDetail.getChangeContractSumTax());
							lease.setChangePriceNoTax(shopDetail.getChangePriceNoTax());
							lease.setChangeQty(shopDetail.getChangeQty());
							lease.setContrTrrm(shopDetail.getAlterTrrm());
							lease.setAlterTrrm(shopDetail.getAlterTrrm());
							lease.setChangeDate(shopDetail.getChangeDate());
							lease.setTaxRate(shopDetail.getTaxRate());
							lease.setUnit(shopDetail.getUnit());
							lease.setSpec(shopDetail.getSpec());
							lease.setIsNetPrice(shopDetail.getIsNetPrice());
							lease.setQty(shopDetail.getChangeQty());
							lease.setPrice(shopDetail.getChangePrice());
							lease.setContractSumTax(shopDetail.getChangeContractSumTax());
							lease.setContractSumNoTax(shopDetail.getChangeContractSumNoTax());
							lease.setContractSum(shopDetail.getChangeContractSum());
							lease.setChangeDate(new Date());
							lease.setPriceNoTax(shopDetail.getChangePrice());
							lease.setContractID(flowDetail.getPp6());
							lease.setCreateUserInfo(userKey, userName);
							zxCtSuppliesContrLeaseListMapper.insert(lease);
						}else if(StrUtil.equals(shopDetail.getAlterType(), "2")){
							ZxCtSuppliesContrLeaseList contrShop = new ZxCtSuppliesContrLeaseList();
							contrShop.setContractID(flowDetail.getPp6());
							List<ZxCtSuppliesContrLeaseList> shopList = zxCtSuppliesContrLeaseListMapper.selectByZxCtSuppliesContrLeaseListList(contrShop);
							for(ZxCtSuppliesContrLeaseList lease : shopList) {
								if(StrUtil.equals(lease.getWorkID(), shopDetail.getWorkID())) {
									lease.setChangeContractSum(shopDetail.getChangeContractSum());
									lease.setChangeContractSumNoTax(shopDetail.getChangeContractSumNoTax());
									lease.setChangeContractSumTax(shopDetail.getChangeContractSumTax());
									lease.setChangePrice(shopDetail.getChangePrice());
									lease.setAlterTrrm(shopDetail.getAlterTrrm());
									lease.setChangePriceNoTax(shopDetail.getChangePriceNoTax());
									lease.setChangeQty(shopDetail.getChangeQty());
									lease.setChangeDate(new Date());
									lease.setModifyUserInfo(userKey, userName);
									zxCtSuppliesContrLeaseListMapper.updateByPrimaryKeySelective(lease);
								}
							}
						}
					}
				}	
			}
			//更新合同表，将变更后金额存入	
			ZxCtSuppliesContract dbContract = zxCtSuppliesContractMapper.selectByPrimaryKey(flowDetail.getPp6());
			dbContract.setAlterContractSumTax(flowDetail.getAlterContractSumTax());
			dbContract.setAlterContractSum(flowDetail.getAlterContractSum());
			dbContract.setAlterContractSumNoTax(flowDetail.getAlterContractSumNoTax());
			dbContract.setAuditContrAlterCount(dbContract.getAuditContrAlterCount()+1);
			dbContract.setModifyUserInfo(userKey, userName);
			zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbContract);
        	if(zxCtSuppliesContrReplenishAgreement.getDocumentFileList() != null && zxCtSuppliesContrReplenishAgreement.getDocumentFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(flowDetail.getReplenishAgreementId());
            	file.setOtherType("1");
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, userName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesContrReplenishAgreement.getDocumentFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(flowDetail.getReplenishAgreementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setCreateUserInfo(userKey, userName);
                    zxErpFileMapper.insert(zxErpFile);  
         	   }        		
        	}	
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {		
			return repEntity.ok("sys.data.update", zxCtSuppliesContrReplenishAgreement);
		}
	} 

	@Override
	public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishAgreementFlow(
			List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtSuppliesContrReplenishAgreementList != null && zxCtSuppliesContrReplenishAgreementList.size() > 0) {
            for(ZxCtSuppliesContrReplenishAgreement agreement : zxCtSuppliesContrReplenishAgreementList){
            	if(StrUtil.isNotEmpty(agreement.getWorkId())) {
            		jsonArr.add(agreement.getWorkId());
            	}
            }
            ZxCtSuppliesContrReplenishAgreement replenishAgreement = new ZxCtSuppliesContrReplenishAgreement();
            replenishAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishAgreementMapper.batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(zxCtSuppliesContrReplenishAgreementList, replenishAgreement);
        }
//      //删除流程后台数据        
        if(jsonArr.size()>0) {
        	HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);      
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishAgreementList);
        }
	}
}
