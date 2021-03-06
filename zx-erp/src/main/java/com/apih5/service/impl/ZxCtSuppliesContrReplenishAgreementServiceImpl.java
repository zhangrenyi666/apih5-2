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
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContrReplenishAgreement.setCompanyId("");
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesContrReplenishAgreement.setCompanyId(zxCtSuppliesContrReplenishAgreement.getOrgID());
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesContrReplenishAgreement.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
        }        
        // ????????????
        PageHelper.startPage(zxCtSuppliesContrReplenishAgreement.getPage(),zxCtSuppliesContrReplenishAgreement.getLimit());
        // ????????????
        List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList = zxCtSuppliesContrReplenishAgreementMapper.selectByZxCtSuppliesContrReplenishAgreementList(zxCtSuppliesContrReplenishAgreement);
        for(ZxCtSuppliesContrReplenishAgreement agreement : zxCtSuppliesContrReplenishAgreementList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(agreement.getReplenishAgreementId());
        	agreement.setReplenishAgreementFileList(zxErpFileMapper.selectByZxErpFileList(file));
        }
        // ??????????????????
        PageInfo<ZxCtSuppliesContrReplenishAgreement> p = new PageInfo<>(zxCtSuppliesContrReplenishAgreementList);

        return repEntity.okList(zxCtSuppliesContrReplenishAgreementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementDetail(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        if (zxCtSuppliesContrReplenishAgreement == null) {
            zxCtSuppliesContrReplenishAgreement = new ZxCtSuppliesContrReplenishAgreement();
        }
        // ????????????
        ZxCtSuppliesContrReplenishAgreement dbZxCtSuppliesContrReplenishAgreement = zxCtSuppliesContrReplenishAgreementMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
        // ????????????
        if (dbZxCtSuppliesContrReplenishAgreement != null) {
            return repEntity.ok(dbZxCtSuppliesContrReplenishAgreement);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setSecondName(zxCtSuppliesContrReplenishAgreement.getSecondName());
           // ??????ID
           dbZxCtSuppliesContrReplenishAgreement.setSecondID(zxCtSuppliesContrReplenishAgreement.getSecondID());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setMaterialSource(zxCtSuppliesContrReplenishAgreement.getMaterialSource());
           // ??????(%)
           dbZxCtSuppliesContrReplenishAgreement.setTaxRate(zxCtSuppliesContrReplenishAgreement.getTaxRate());
           // ??????????????????
           dbZxCtSuppliesContrReplenishAgreement.setIsFlagZhb(zxCtSuppliesContrReplenishAgreement.getIsFlagZhb());
           // ???????????????
           dbZxCtSuppliesContrReplenishAgreement.setIsFlag(zxCtSuppliesContrReplenishAgreement.getIsFlag());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setIsDeduct(zxCtSuppliesContrReplenishAgreement.getIsDeduct());
           // ????????????????????????
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumTax());
           // ?????????????????????????????????
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSum(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSum());
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setPp8(zxCtSuppliesContrReplenishAgreement.getPp8());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setStatus(zxCtSuppliesContrReplenishAgreement.getStatus());
           // ????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setInstProcessId(zxCtSuppliesContrReplenishAgreement.getInstProcessId());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setStartDate(zxCtSuppliesContrReplenishAgreement.getStartDate());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setEndDate(zxCtSuppliesContrReplenishAgreement.getEndDate());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setFirstName(zxCtSuppliesContrReplenishAgreement.getFirstName());
           // ??????ID
           dbZxCtSuppliesContrReplenishAgreement.setFirstID(zxCtSuppliesContrReplenishAgreement.getFirstID());
           // ????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostTax(zxCtSuppliesContrReplenishAgreement.getContractCostTax());
           // ???????????????
           dbZxCtSuppliesContrReplenishAgreement.setAgent(zxCtSuppliesContrReplenishAgreement.getAgent());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setContent(zxCtSuppliesContrReplenishAgreement.getContent());
           // ????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setContractName(zxCtSuppliesContrReplenishAgreement.getContractName());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setContractType(zxCtSuppliesContrReplenishAgreement.getContractType());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setCode7(zxCtSuppliesContrReplenishAgreement.getCode7());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setCsTimeLimit(zxCtSuppliesContrReplenishAgreement.getCsTimeLimit());
           // ??????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setContractCost(zxCtSuppliesContrReplenishAgreement.getContractCost());
           // ????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setWorkitemID(zxCtSuppliesContrReplenishAgreement.getWorkitemID());
           // ??????????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToZhbID(zxCtSuppliesContrReplenishAgreement.getSendToZhbID());
           // ???????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToJuID(zxCtSuppliesContrReplenishAgreement.getSendToJuID());
           // ?????????
           dbZxCtSuppliesContrReplenishAgreement.setBeginPer(zxCtSuppliesContrReplenishAgreement.getBeginPer());
           // ?????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostNoTax(zxCtSuppliesContrReplenishAgreement.getContractCostNoTax());
           // ??????????????????
           dbZxCtSuppliesContrReplenishAgreement.setPp3(zxCtSuppliesContrReplenishAgreement.getPp3());
           // ??????????????????
           dbZxCtSuppliesContrReplenishAgreement.setContractNo(zxCtSuppliesContrReplenishAgreement.getContractNo());
           // ???????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumTax());
           // ?????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSum(zxCtSuppliesContrReplenishAgreement.getAlterContractSum());
           // ????????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumNoTax());
           // ??????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setPp4Tax(zxCtSuppliesContrReplenishAgreement.getPp4Tax());
           // ????????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setPp4(zxCtSuppliesContrReplenishAgreement.getPp4());
           // ???????????????????????????(??????)
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
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setRemarks(zxCtSuppliesContrReplenishAgreement.getRemarks());
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setSort(zxCtSuppliesContrReplenishAgreement.getSort());
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishAgreement);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishAgreementList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

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
	    		return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????");
	    	}
	    }
	    zxCtSuppliesContrReplenishAgreement.setReplenishAgreementId(UuidUtil.generate());
	    zxCtSuppliesContrReplenishAgreement.setContractType("???????????????????????????");
	    //????????????????????????
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
	    //??????????????????????????????????????????????????????
	    ZxCtSuppliesContract contract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishAgreement.getPp6());
	    if(contract != null) {
	    	zxCtSuppliesContrReplenishAgreement.setUpAlterContractSum(contract.getAlterContractSum());
	    	zxCtSuppliesContrReplenishAgreement.setUpAlterContractSumNoTax(contract.getAlterContractSumNoTax());
	    	zxCtSuppliesContrReplenishAgreement.setUpAlterContractSumTax(contract.getAlterContractSumTax());
	    	zxCtSuppliesContrReplenishAgreement.setComID(contract.getComID());
	    }
        zxCtSuppliesContrReplenishAgreement.setCreateUserInfo(userKey, realName);
        flag = zxCtSuppliesContrReplenishAgreementMapper.insert(zxCtSuppliesContrReplenishAgreement);    
	    // ??????
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
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setSecondName(zxCtSuppliesContrReplenishAgreement.getSecondName());
           // ??????ID
           dbZxCtSuppliesContrReplenishAgreement.setSecondID(zxCtSuppliesContrReplenishAgreement.getSecondID());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setMaterialSource(zxCtSuppliesContrReplenishAgreement.getMaterialSource());
           // ??????(%)
           dbZxCtSuppliesContrReplenishAgreement.setTaxRate(zxCtSuppliesContrReplenishAgreement.getTaxRate());
           // ??????????????????
           dbZxCtSuppliesContrReplenishAgreement.setIsFlagZhb(zxCtSuppliesContrReplenishAgreement.getIsFlagZhb());
           // ???????????????
           dbZxCtSuppliesContrReplenishAgreement.setIsFlag(zxCtSuppliesContrReplenishAgreement.getIsFlag());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setIsDeduct(zxCtSuppliesContrReplenishAgreement.getIsDeduct());
           // ????????????????????????
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumTax());
           // ?????????????????????????????????
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrReplenishAgreement.setUpAlterContractSum(zxCtSuppliesContrReplenishAgreement.getUpAlterContractSum());
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setPp8(zxCtSuppliesContrReplenishAgreement.getPp8());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setStatus(zxCtSuppliesContrReplenishAgreement.getStatus());
           // ????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setInstProcessId(zxCtSuppliesContrReplenishAgreement.getInstProcessId());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setStartDate(zxCtSuppliesContrReplenishAgreement.getStartDate());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setEndDate(zxCtSuppliesContrReplenishAgreement.getEndDate());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setFirstName(zxCtSuppliesContrReplenishAgreement.getFirstName());
           // ??????ID
           dbZxCtSuppliesContrReplenishAgreement.setFirstID(zxCtSuppliesContrReplenishAgreement.getFirstID());
           // ????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostTax(zxCtSuppliesContrReplenishAgreement.getContractCostTax());
           // ???????????????
           dbZxCtSuppliesContrReplenishAgreement.setAgent(zxCtSuppliesContrReplenishAgreement.getAgent());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setContent(zxCtSuppliesContrReplenishAgreement.getContent());
           // ????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setContractName(zxCtSuppliesContrReplenishAgreement.getContractName());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setContractType(zxCtSuppliesContrReplenishAgreement.getContractType());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setCode7(zxCtSuppliesContrReplenishAgreement.getCode7());
           // ????????????
           dbZxCtSuppliesContrReplenishAgreement.setCsTimeLimit(zxCtSuppliesContrReplenishAgreement.getCsTimeLimit());
           // ??????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setContractCost(zxCtSuppliesContrReplenishAgreement.getContractCost());
           // ????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setWorkitemID(zxCtSuppliesContrReplenishAgreement.getWorkitemID());
           // ??????????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToZhbID(zxCtSuppliesContrReplenishAgreement.getSendToZhbID());
           // ???????????????ID
           dbZxCtSuppliesContrReplenishAgreement.setSendToJuID(zxCtSuppliesContrReplenishAgreement.getSendToJuID());
           // ?????????
           dbZxCtSuppliesContrReplenishAgreement.setBeginPer(zxCtSuppliesContrReplenishAgreement.getBeginPer());
           // ?????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setContractCostNoTax(zxCtSuppliesContrReplenishAgreement.getContractCostNoTax());
           // ??????????????????
           dbZxCtSuppliesContrReplenishAgreement.setPp3(zxCtSuppliesContrReplenishAgreement.getPp3());
           // ??????????????????
           dbZxCtSuppliesContrReplenishAgreement.setContractNo(zxCtSuppliesContrReplenishAgreement.getContractNo());
           // ???????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumTax());
           // ?????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSum(zxCtSuppliesContrReplenishAgreement.getAlterContractSum());
           // ????????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setAlterContractSumNoTax(zxCtSuppliesContrReplenishAgreement.getAlterContractSumNoTax());
           // ??????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setPp4Tax(zxCtSuppliesContrReplenishAgreement.getPp4Tax());
           // ????????????????????????(??????)
           dbZxCtSuppliesContrReplenishAgreement.setPp4(zxCtSuppliesContrReplenishAgreement.getPp4());
           // ???????????????????????????(??????)
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
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setRemarks(zxCtSuppliesContrReplenishAgreement.getRemarks());
           // ??????
           dbZxCtSuppliesContrReplenishAgreement.setSort(zxCtSuppliesContrReplenishAgreement.getSort());
           // ??????
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
        // ??????
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
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContrReplenishAgreement.setComID("");
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesContrReplenishAgreement.setComID(zxCtSuppliesContrReplenishAgreement.getOrgID());
        	zxCtSuppliesContrReplenishAgreement.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesContrReplenishAgreement.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
        }   
        // ????????????
        PageHelper.startPage(zxCtSuppliesContrReplenishAgreement.getPage(),zxCtSuppliesContrReplenishAgreement.getLimit()); 
        // ????????????
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
        // ??????????????????
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
		//??????????????????
    	if(StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "WZ") || StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "LC")) {
            //???????????????????????????
            ZxCtSuppliesContrReplenishShopList replenishShopList = new ZxCtSuppliesContrReplenishShopList();
            replenishShopList.setZxCtSuppliesContrReplenishShopListId(UuidUtil.generate());
            // ????????????????????????
            replenishShopList.setUpAlterContractSumTax(agreement.getUpAlterContractSumTax());
            // ?????????????????????????????????
            replenishShopList.setUpAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
            // ????????????????????????
            replenishShopList.setUpAlterContractSum(agreement.getUpAlterContractSum());
            // ????????????
            replenishShopList.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
            // ????????????
            replenishShopList.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
            // ??????ID
            replenishShopList.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
            // ????????????(??????)
            replenishShopList.setPp2Tax(zxCtSuppliesContrReplenishAgreement.getContractCostTax());
            // ?????????????????????(??????)
            replenishShopList.setPp2NoTax(zxCtSuppliesContrReplenishAgreement.getContractCostNoTax());
            // ?????????????????????
            replenishShopList.setReplyNo(zxCtSuppliesContrReplenishAgreement.getContractNo());
            // ???????????????ID
            replenishShopList.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
            // ????????????
            replenishShopList.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
            // ????????????
            replenishShopList.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
		if(zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList() != null) {
			zxCtSuppliesContrReplenishAgreement.getReplenishShopDetailedList().parallelStream().forEach((detial)->{
				ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopList = detial;
				//????????????????????????????????????????????????
				ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrShopList = new ZxCtSuppliesContrReplenishShopDetail();
		            zxCtSuppliesContrShopList.setZxCtSuppliesContrReplenishShopDetailId(UuidUtil.generate());
		            zxCtSuppliesContrShopList.setContrAlterID(replenishShopList.getZxCtSuppliesContrReplenishShopListId());
		            zxCtSuppliesContrShopList.setAlterType(detial.getAlterType());
		            // ????????????
		            zxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrReplenishShopList.getRentUnit());
		            // ??????
		            zxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrReplenishShopList.getContrTrrm());
		            // ????????????
		            zxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrReplenishShopList.getWorkName());
		            // ????????????ID
		            zxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrReplenishShopList.getWorkTypeID());
		            // ????????????
		            zxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrReplenishShopList.getWorkType());
		            // ????????????ID
		            zxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrReplenishShopList.getWorkID());
		            // ????????????
		            zxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrReplenishShopList.getWorkNo());
		            // ??????(%)
		            zxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrReplenishShopList.getTaxRate());
		            // ??????
		            zxCtSuppliesContrShopList.setQty(zxCtSuppliesContrReplenishShopList.getQty());
		            // ????????????
		            zxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrReplenishShopList.getIsNetPrice());
		            // ??????????????????
		            zxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrReplenishShopList.getActualStartDate());
		            // ??????????????????
		            zxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrReplenishShopList.getActualEndDate());
		            // ??????????????????
		            zxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrReplenishShopList.getViewType());
		            // ??????????????????
		            zxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrReplenishShopList.getPlanStartDate());
		            // ??????????????????
		            zxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrReplenishShopList.getPlanEndDate());
		            // ????????????
		            zxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrReplenishShopList.getContractSum());
		            // ????????????
		            zxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrReplenishShopList.getPrice());
		            // ????????????
		            zxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrReplenishShopList.getSpec());
		            // ??????
		            zxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrReplenishShopList.getUnit());
		            // ??????
		            zxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrReplenishShopList.getTreenode());
		            // ???????????????
		            zxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrReplenishShopList.getContractSumNoTax());
		            // ???????????????
		            zxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrReplenishShopList.getPriceNoTax());
		            // ????????????
		            zxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrReplenishShopList.getChangeDate());
		            // ???????????????
		            zxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrReplenishShopList.getAlterTrrm());
		            // ???????????????
		            zxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrReplenishShopList.getChangeQty());
		            // ?????????????????????
		            zxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrReplenishShopList.getChangeContractSum());
		            // ?????????????????????
		            zxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrReplenishShopList.getChangePrice());
		            //???????????????
		            zxCtSuppliesContrShopList.setChangeContractSumTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumTax());
		            // ????????????????????????
		            zxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumNoTax());
		            // ????????????????????????
		            zxCtSuppliesContrShopList.setChangePriceNoTax(zxCtSuppliesContrReplenishShopList.getChangePriceNoTax());
		            // ??????
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
				//?????????????????????????????????????????????????????????
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
				//????????????????????????????????????
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}
			//????????????????????????????????????????????????????????????
			if(agreement.getUpAlterContractSumTax() != null) {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//???????????????????????????(??????)				
			}else {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
			}
//				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
//				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
//				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
				agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);
	        // ???????????????(??????)
	        replenishShopList.setReplyAmountTax(agreement.getAlterContractSumTax());
	        // ?????????????????????(??????)
	        replenishShopList.setReplyAmount(agreement.getAlterContractSum());
	        // ????????????????????????(??????)
	        replenishShopList.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
	        // ??????????????????(??????)
	        replenishShopList.setApplyAmountTax(agreement.getPp4Tax());
	        // ????????????????????????(??????)
	        replenishShopList.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
	        // ???????????????????????????(??????)
	        replenishShopList.setApplyAmountNoTax(agreement.getPp4NoTax());			
		}
        // (IP?????????ID)
        replenishShopList.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
        replenishShopList.setCreateUserInfo(userKey, realName);
        zxCtSuppliesContrReplenishShopListMapper.insert(replenishShopList);		        
        //????????????
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
		//????????????
		ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList = new ZxCtSuppliesContrReplenishLeaseList();		
		   zxCtSuppliesContrReplenishLeaseList.setZxCtSuppliesContrReplenishLeaseListId(UuidUtil.generate());
           // ????????????????????????
		   zxCtSuppliesContrReplenishLeaseList.setUpAlterContractSumTax(agreement.getUpAlterContractSumTax());
           // ?????????????????????????????????
		   zxCtSuppliesContrReplenishLeaseList.setUpAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
           // ????????????????????????
		   zxCtSuppliesContrReplenishLeaseList.setUpAlterContractSum(agreement.getUpAlterContractSum());
           // ????????????
		   zxCtSuppliesContrReplenishLeaseList.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
           // ????????????
		   zxCtSuppliesContrReplenishLeaseList.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
           // ??????ID
		   zxCtSuppliesContrReplenishLeaseList.setOrgID(zxCtSuppliesContrReplenishAgreement.getOrgID());
           // ??????????????????(??????)
		   zxCtSuppliesContrReplenishLeaseList.setPp2(zxCtSuppliesContrReplenishAgreement.getPp2());
           // ???????????????ID
		   zxCtSuppliesContrReplenishLeaseList.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId());
           // ????????????
		   zxCtSuppliesContrReplenishLeaseList.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
           // ????????????
		   zxCtSuppliesContrReplenishLeaseList.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
		   // ??????
		   zxCtSuppliesContrReplenishLeaseList.setRemarks(zxCtSuppliesContrReplenishAgreement.getRemarks());
		if(zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList() != null) {
			zxCtSuppliesContrReplenishAgreement.getReplenishLeaseDetailedList().parallelStream().forEach((detial)->{
				ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishDetail = detial;		
			   ZxCtSuppliesContrReplenishLeaseDetail dbZxCtSuppliesContrReplenishLeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
			   dbZxCtSuppliesContrReplenishLeaseDetail.setZxCtSuppliesContrReplenishLeaseDetailId(UuidUtil.generate());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRentUnit(zxCtSuppliesContrReplenishDetail.getRentUnit());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrTrrm(zxCtSuppliesContrReplenishDetail.getContrTrrm());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRequestEdit(zxCtSuppliesContrReplenishDetail.getRequestEdit());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditDate(zxCtSuppliesContrReplenishDetail.getEditDate());
	           // ?????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserID(zxCtSuppliesContrReplenishDetail.getEditUserID());
	           // ?????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserName(zxCtSuppliesContrReplenishDetail.getEditUserName());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkName(zxCtSuppliesContrReplenishDetail.getWorkName());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkTypeID(zxCtSuppliesContrReplenishDetail.getWorkTypeID());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkType(zxCtSuppliesContrReplenishDetail.getWorkType());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setSpec(zxCtSuppliesContrReplenishDetail.getSpec());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkID(zxCtSuppliesContrReplenishDetail.getWorkID());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkNo(zxCtSuppliesContrReplenishDetail.getWorkNo());
	           // ??????(%)
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTaxRate(zxCtSuppliesContrReplenishDetail.getTaxRate());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setQty(zxCtSuppliesContrReplenishDetail.getQty());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setIsNetPrice(zxCtSuppliesContrReplenishDetail.getIsNetPrice());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualStartDate(zxCtSuppliesContrReplenishDetail.getActualStartDate());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualEndDate(zxCtSuppliesContrReplenishDetail.getActualEndDate());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumTax(zxCtSuppliesContrReplenishDetail.getUpAlterContractSumTax());
	           // ?????????????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishDetail.getUpAlterContractSumNoTax());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSum(zxCtSuppliesContrReplenishDetail.getUpAlterContractSum());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setViewType(zxCtSuppliesContrReplenishDetail.getViewType());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanStartDate(zxCtSuppliesContrReplenishDetail.getPlanStartDate());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanEndDate(zxCtSuppliesContrReplenishDetail.getPlanEndDate());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrItemID(zxCtSuppliesContrReplenishDetail.getContrItemID());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(zxCtSuppliesContrReplenishDetail.getContrAlterID());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSum(zxCtSuppliesContrReplenishDetail.getContractSum());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPrice(zxCtSuppliesContrReplenishDetail.getPrice());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUnit(zxCtSuppliesContrReplenishDetail.getUnit());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTreenode(zxCtSuppliesContrReplenishDetail.getTreenode());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumTax(zxCtSuppliesContrReplenishDetail.getContractSumTax());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumNoTax(zxCtSuppliesContrReplenishDetail.getContractSumNoTax());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPriceNoTax(zxCtSuppliesContrReplenishDetail.getPriceNoTax());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeDate(zxCtSuppliesContrReplenishDetail.getChangeDate());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterType(zxCtSuppliesContrReplenishDetail.getAlterType());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterTrrm(zxCtSuppliesContrReplenishDetail.getAlterTrrm());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishDetail.getChangeContractSumTax());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeQty(zxCtSuppliesContrReplenishDetail.getChangeQty());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSum(zxCtSuppliesContrReplenishDetail.getChangeContractSum());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePrice(zxCtSuppliesContrReplenishDetail.getChangePrice());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishDetail.getChangeContractSumNoTax());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishDetail.getChangePriceNoTax());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRemarks(zxCtSuppliesContrReplenishDetail.getRemarks());
	           // ??????
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
				//?????????????????????????????????????????????????????????
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
				//????????????????????????????????????
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}			
			//????????????????????????????????????????????????????????????
			if(agreement.getUpAlterContractSumTax() != null) {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//???????????????????????????(??????)		
			}else {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
			}
//				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
//				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
//				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
				agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);		
	        // ???????????????(??????)
			zxCtSuppliesContrReplenishLeaseList.setReplyAmountTax(agreement.getAlterContractSumTax());
	        // ?????????????????????(??????)
			zxCtSuppliesContrReplenishLeaseList.setReplyAmount(agreement.getAlterContractSum());
	        // ????????????????????????(??????)
			zxCtSuppliesContrReplenishLeaseList.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
	        // ??????????????????(??????)
			zxCtSuppliesContrReplenishLeaseList.setApplyAmountTax(agreement.getPp4Tax());
	        // ????????????????????????(??????)
			zxCtSuppliesContrReplenishLeaseList.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
	        // ???????????????????????????(??????)
			zxCtSuppliesContrReplenishLeaseList.setApplyAmountNoTax(agreement.getPp4NoTax());			
		}
        // (IP?????????ID)
		zxCtSuppliesContrReplenishLeaseList.setPp6(zxCtSuppliesContrReplenishAgreement.getPp6());
		zxCtSuppliesContrReplenishLeaseList.setCreateUserInfo(userKey, realName);
		zxCtSuppliesContrReplenishLeaseListMapper.insert(zxCtSuppliesContrReplenishLeaseList);		        
        //????????????
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
		//??????????????????
    	if(StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "WZ") || StrUtil.equals(zxCtSuppliesContrReplenishAgreement.getCode7(), "LC")) {
	        //???????????????????????????
            ZxCtSuppliesContrReplenishShopList replenishShop = new ZxCtSuppliesContrReplenishShopList();
            replenishShop.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId()); 
            List<ZxCtSuppliesContrReplenishShopList> replenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(replenishShop);
            replenishShop = replenishShopList.get(0);
            // ????????????
            replenishShop.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
            // ????????????
            replenishShop.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
            // ????????????
            replenishShop.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
            // ????????????
            replenishShop.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
            //????????????
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
				//??????
				    ZxCtSuppliesContrReplenishShopDetail shopDetail = new ZxCtSuppliesContrReplenishShopDetail();
				    shopDetail.setZxCtSuppliesContrReplenishShopDetailId(UuidUtil.generate());
		            shopDetail.setContrAlterID(replenishShop.getZxCtSuppliesContrReplenishShopListId());
		            shopDetail.setAlterType(zxCtSuppliesContrReplenishShopList.getAlterType());
		            // ????????????
		            shopDetail.setRentUnit(zxCtSuppliesContrReplenishShopList.getRentUnit());
		            // ??????
		            shopDetail.setContrTrrm(zxCtSuppliesContrReplenishShopList.getContrTrrm());
		            // ????????????
		            shopDetail.setWorkName(zxCtSuppliesContrReplenishShopList.getWorkName());
		            // ????????????ID
		            shopDetail.setWorkTypeID(zxCtSuppliesContrReplenishShopList.getWorkTypeID());
		            // ????????????
		            shopDetail.setWorkType(zxCtSuppliesContrReplenishShopList.getWorkType());
		            // ????????????ID
		            shopDetail.setWorkID(zxCtSuppliesContrReplenishShopList.getWorkID());
		            // ????????????
		            shopDetail.setWorkNo(zxCtSuppliesContrReplenishShopList.getWorkNo());
		            // ??????(%)
		            shopDetail.setTaxRate(zxCtSuppliesContrReplenishShopList.getTaxRate());
		            // ??????
		            shopDetail.setQty(zxCtSuppliesContrReplenishShopList.getQty());
		            // ????????????
		            shopDetail.setIsNetPrice(zxCtSuppliesContrReplenishShopList.getIsNetPrice());
		            // ??????????????????
		            shopDetail.setActualStartDate(zxCtSuppliesContrReplenishShopList.getActualStartDate());
		            // ??????????????????
		            shopDetail.setActualEndDate(zxCtSuppliesContrReplenishShopList.getActualEndDate());
		            // ??????????????????
		            shopDetail.setViewType(zxCtSuppliesContrReplenishShopList.getViewType());
		            // ??????????????????
		            shopDetail.setPlanStartDate(zxCtSuppliesContrReplenishShopList.getPlanStartDate());
		            // ??????????????????
		            shopDetail.setPlanEndDate(zxCtSuppliesContrReplenishShopList.getPlanEndDate());
		            // ????????????
		            shopDetail.setContractSum(zxCtSuppliesContrReplenishShopList.getContractSum());
		            // ????????????
		            shopDetail.setPrice(zxCtSuppliesContrReplenishShopList.getPrice());
		            // ????????????
		            shopDetail.setSpec(zxCtSuppliesContrReplenishShopList.getSpec());
		            // ??????
		            shopDetail.setUnit(zxCtSuppliesContrReplenishShopList.getUnit());
		            // ??????
		            shopDetail.setTreenode(zxCtSuppliesContrReplenishShopList.getTreenode());
		            // ???????????????
		            shopDetail.setContractSumNoTax(zxCtSuppliesContrReplenishShopList.getContractSumNoTax());
		            // ???????????????
		            shopDetail.setPriceNoTax(zxCtSuppliesContrReplenishShopList.getPriceNoTax());
		            // ????????????
		            shopDetail.setChangeDate(zxCtSuppliesContrReplenishShopList.getChangeDate());
		            // ???????????????
		            shopDetail.setAlterTrrm(zxCtSuppliesContrReplenishShopList.getAlterTrrm());
		            // ???????????????
		            shopDetail.setChangeQty(zxCtSuppliesContrReplenishShopList.getChangeQty());
		            // ?????????????????????
		            shopDetail.setChangeContractSum(zxCtSuppliesContrReplenishShopList.getChangeContractSum());
		            // ?????????????????????
		            shopDetail.setChangePrice(zxCtSuppliesContrReplenishShopList.getChangePrice());
		            // ????????????????????????
		            shopDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumNoTax());
		            
		            shopDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishShopList.getChangeContractSumTax());
		            // ????????????????????????
		            shopDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishShopList.getChangePriceNoTax());
		            // ??????
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
				//?????????????????????????????????????????????????????????
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
				//????????????????????????????????????
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}
			//????????????????????????????????????????????????????????????
			if(agreement.getUpAlterContractSumTax() != null) {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//???????????????????????????(??????)		
			}else {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishShopDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumNoTax()));
				
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
			}
				
//			agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
//			agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
//			agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
			agreement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);				
            // ???????????????(??????)
            replenishShop.setReplyAmountTax(agreement.getAlterContractSumTax());
            // ?????????????????????(??????)
            replenishShop.setReplyAmount(agreement.getAlterContractSum());
            // ????????????????????????(??????)
            replenishShop.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
            // ??????????????????(??????)
            replenishShop.setApplyAmountTax(agreement.getPp4Tax());
            // ????????????????????????(??????)
            replenishShop.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
            // ???????????????????????????(??????)
            replenishShop.setApplyAmountNoTax(agreement.getPp4NoTax());
            replenishShop.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishShopListMapper.updateByPrimaryKeySelective(replenishShop);		
		}else {
			if(agreement.getUpAlterContractSumTax() != null) {
				//?????????????????????(??????)
				agreement.setAlterContractSum(agreement.getUpAlterContractSum());
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
				//???????????????(??????)
				agreement.setAlterContractSumTax(agreement.getUpAlterContractSumTax());
	            // ???????????????(??????)
				replenishShop.setReplyAmountTax(agreement.getUpAlterContractSumTax());
	            // ?????????????????????(??????)
				replenishShop.setReplyAmount(agreement.getUpAlterContractSum());
	            // ????????????????????????(??????)
	            replenishShop.setReplyAmountNoTax(agreement.getUpAlterContractSumNoTax());
			}else {
				//?????????????????????(??????)
				agreement.setAlterContractSum(new BigDecimal(0));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(new BigDecimal(0));
				//???????????????(??????)
				agreement.setAlterContractSumTax(new BigDecimal(0));
	            // ???????????????(??????)
				replenishShop.setReplyAmountTax(new BigDecimal(0));
	            // ?????????????????????(??????)
				replenishShop.setReplyAmount(new BigDecimal(0));
	            // ????????????????????????(??????)
	            replenishShop.setReplyAmountNoTax(new BigDecimal(0));				
			}
			agreement.setPp4("0");//????????????????????????(??????)
			agreement.setPp4NoTax(new BigDecimal(0));//??????????????????(??????)
			agreement.setPp4Tax(new BigDecimal(0));//???????????????????????????(??????)
			agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);	
            // ??????????????????(??????)
            replenishShop.setApplyAmountTax(new BigDecimal(0));
            // ????????????????????????(??????)
            replenishShop.setApplyAmount(new BigDecimal(0));
            // ???????????????????????????(??????)
            replenishShop.setApplyAmountNoTax(new BigDecimal(0));
            replenishShop.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishShopListMapper.updateByPrimaryKeySelective(replenishShop);					
		}
	}else {
        //???????????????????????????
		ZxCtSuppliesContrReplenishLeaseList replenishLease = new ZxCtSuppliesContrReplenishLeaseList();
		replenishLease.setPp3(zxCtSuppliesContrReplenishAgreement.getReplenishAgreementId()); 
        List<ZxCtSuppliesContrReplenishLeaseList> replenishLeaseList = zxCtSuppliesContrReplenishLeaseListMapper.selectByZxCtSuppliesContrReplenishLeaseListList(replenishLease);
        replenishLease = replenishLeaseList.get(0);
        // ????????????
        replenishLease.setReplyDate(zxCtSuppliesContrReplenishAgreement.getReplyDate());
        // ????????????
        replenishLease.setPp1(zxCtSuppliesContrReplenishAgreement.getReplyUnit());
        // ????????????
        replenishLease.setAlterReason(zxCtSuppliesContrReplenishAgreement.getAlterReason());
        // ????????????
        replenishLease.setAlterContent(zxCtSuppliesContrReplenishAgreement.getAlterContent());
        //????????????
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
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRentUnit(zxCtSuppliesContrReplenishLeaseList.getRentUnit());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrTrrm(zxCtSuppliesContrReplenishLeaseList.getContrTrrm());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRequestEdit(zxCtSuppliesContrReplenishLeaseList.getRequestEdit());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditDate(zxCtSuppliesContrReplenishLeaseList.getEditDate());
	           // ?????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserID(zxCtSuppliesContrReplenishLeaseList.getEditUserID());
	           // ?????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserName(zxCtSuppliesContrReplenishLeaseList.getEditUserName());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkName(zxCtSuppliesContrReplenishLeaseList.getWorkName());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkTypeID(zxCtSuppliesContrReplenishLeaseList.getWorkTypeID());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkType(zxCtSuppliesContrReplenishLeaseList.getWorkType());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setSpec(zxCtSuppliesContrReplenishLeaseList.getSpec());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkID(zxCtSuppliesContrReplenishLeaseList.getWorkID());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkNo(zxCtSuppliesContrReplenishLeaseList.getWorkNo());
	           // ??????(%)
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTaxRate(zxCtSuppliesContrReplenishLeaseList.getTaxRate());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setQty(zxCtSuppliesContrReplenishLeaseList.getQty());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setIsNetPrice(zxCtSuppliesContrReplenishLeaseList.getIsNetPrice());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualStartDate(zxCtSuppliesContrReplenishLeaseList.getActualStartDate());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setActualEndDate(zxCtSuppliesContrReplenishLeaseList.getActualEndDate());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumTax(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSumTax());
	           // ?????????????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSumNoTax());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSum(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSum());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setViewType(zxCtSuppliesContrReplenishLeaseList.getViewType());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanStartDate(zxCtSuppliesContrReplenishLeaseList.getPlanStartDate());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanEndDate(zxCtSuppliesContrReplenishLeaseList.getPlanEndDate());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrItemID(zxCtSuppliesContrReplenishLeaseList.getContrItemID());
	           // ????????????ID
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(zxCtSuppliesContrReplenishLeaseList.getContrAlterID());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSum(zxCtSuppliesContrReplenishLeaseList.getContractSum());
	           // ??????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPrice(zxCtSuppliesContrReplenishLeaseList.getPrice());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setUnit(zxCtSuppliesContrReplenishLeaseList.getUnit());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setTreenode(zxCtSuppliesContrReplenishLeaseList.getTreenode());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumTax(zxCtSuppliesContrReplenishLeaseList.getContractSumTax());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getContractSumNoTax());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setPriceNoTax(zxCtSuppliesContrReplenishLeaseList.getPriceNoTax());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeDate(zxCtSuppliesContrReplenishLeaseList.getChangeDate());
	           // ????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterType(zxCtSuppliesContrReplenishLeaseList.getAlterType());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterTrrm(zxCtSuppliesContrReplenishLeaseList.getAlterTrrm());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishLeaseList.getChangeContractSumTax());
	           // ???????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeQty(zxCtSuppliesContrReplenishLeaseList.getChangeQty());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSum(zxCtSuppliesContrReplenishLeaseList.getChangeContractSum());
	           // ?????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePrice(zxCtSuppliesContrReplenishLeaseList.getChangePrice());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getChangeContractSumNoTax());
	           // ????????????????????????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishLeaseList.getChangePriceNoTax());
	           // ??????
	           dbZxCtSuppliesContrReplenishLeaseDetail.setRemarks(zxCtSuppliesContrReplenishLeaseList.getRemarks());
	           // ??????
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
				//?????????????????????????????????????????????????????????
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
				//????????????????????????????????????
					detail.setChangeContractSum(CalcUtils.calcSubtract(detail.getChangeContractSum(), detail.getContractSum()));
					detail.setChangeContractSumNoTax(CalcUtils.calcSubtract(detail.getChangeContractSumNoTax(), detail.getContractSumNoTax()));
					detail.setChangeContractSumTax(CalcUtils.calcSubtract(detail.getChangeContractSumTax(), detail.getContractSumTax()));
				}
			}			
			//????????????????????????????????????????????????????????????
			if(agreement.getUpAlterContractSumTax() != null) {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(
						                      CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                      CalcUtils.calcAdd(agreement.getUpAlterContractSum(), 
//						                      CalcUtils.calcAdd(agreement.getContractCost(), 
						                      CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
						                           CalcUtils.calcAdd(agreement.getUpAlterContractSumNoTax(), 
//						                           CalcUtils.calcAdd(agreement.getContractCostNoTax(), 
						                           CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
				agreement.setAlterContractSumTax(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getAlterContractSumTax()));
//				agreement.setAlterContractSumTax(CalcUtils.calcAdd(
//						                         CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),
//						                         CalcUtils.calcAdd(agreement.getUpAlterContractSumTax(), 
////						                         CalcUtils.calcAdd(agreement.getContractCostTax(), 
//						                         CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(),agreement.getUpAlterContractSum()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(),agreement.getUpAlterContractSumNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(),agreement.getUpAlterContractSumTax()));//???????????????????????????(??????)		
			}else {
				//?????????????????????(??????)
				agreement.setAlterContractSum(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCost(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSum).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostNoTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumNoTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				//???????????????(??????)
				agreement.setAlterContractSumTax(CalcUtils.calcAdd(CalcUtils.calcDivide(editShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6),CalcUtils.calcAdd(agreement.getContractCostTax(), CalcUtils.calcDivide(addShopList.stream().map(ZxCtSuppliesContrReplenishLeaseDetail::getChangeContractSumTax).reduce(BigDecimal.ZERO,BigDecimal::add),new BigDecimal(10000),6))));
				agreement.setPp4(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()).toString());//????????????????????????(??????)
				agreement.setPp4NoTax(CalcUtils.calcSubtract(agreement.getAlterContractSumNoTax(), agreement.getContractCostNoTax()));//??????????????????(??????)
				agreement.setPp4Tax(CalcUtils.calcSubtract(agreement.getAlterContractSumTax(), agreement.getContractCostTax()));//???????????????????????????(??????)
			}
				agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);	
            // ???????????????(??????)
			replenishLease.setReplyAmountTax(agreement.getAlterContractSumTax());
            // ?????????????????????(??????)
            replenishLease.setReplyAmount(agreement.getAlterContractSum());
            // ????????????????????????(??????)
            replenishLease.setReplyAmountNoTax(agreement.getAlterContractSumNoTax());
            // ??????????????????(??????)
            replenishLease.setApplyAmountTax(agreement.getPp4Tax());
            // ????????????????????????(??????)
            replenishLease.setApplyAmount(CalcUtils.calcSubtract(agreement.getAlterContractSum(), agreement.getContractCost()));
            // ???????????????????????????(??????)
            replenishLease.setApplyAmountNoTax(agreement.getPp4NoTax());
            replenishLease.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContrReplenishLeaseListMapper.updateByPrimaryKeySelective(replenishLease);				
		}else {
			if(agreement.getUpAlterContractSumTax() != null) {
				//?????????????????????(??????)
				agreement.setAlterContractSum(agreement.getUpAlterContractSum());
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(agreement.getUpAlterContractSumNoTax());
				//???????????????(??????)
				agreement.setAlterContractSumTax(agreement.getUpAlterContractSumTax());
	            // ???????????????(??????)
				replenishLease.setReplyAmountTax(agreement.getUpAlterContractSumTax());
	            // ?????????????????????(??????)
	            replenishLease.setReplyAmount(agreement.getUpAlterContractSum());
	            // ????????????????????????(??????)
	            replenishLease.setReplyAmountNoTax(agreement.getUpAlterContractSumNoTax());
			}else {
				//?????????????????????(??????)
				agreement.setAlterContractSum(new BigDecimal(0));
				//????????????????????????(??????)
				agreement.setAlterContractSumNoTax(new BigDecimal(0));
				//???????????????(??????)
				agreement.setAlterContractSumTax(new BigDecimal(0));
	            // ???????????????(??????)
				replenishLease.setReplyAmountTax(new BigDecimal(0));
	            // ?????????????????????(??????)
	            replenishLease.setReplyAmount(new BigDecimal(0));
	            // ????????????????????????(??????)
	            replenishLease.setReplyAmountNoTax(new BigDecimal(0));				
			}
			agreement.setPp4("0");//????????????????????????(??????)
			agreement.setPp4NoTax(new BigDecimal(0));//??????????????????(??????)
			agreement.setPp4Tax(new BigDecimal(0));//???????????????????????????(??????)
			agreement.setModifyUserInfo(userKey, realName);
			zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(agreement);	
            // ??????????????????(??????)
            replenishLease.setApplyAmountTax(new BigDecimal(0));
            // ????????????????????????(??????)
            replenishLease.setApplyAmount(new BigDecimal(0));
            // ???????????????????????????(??????)
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
        	//?????????:0;????????????:1;????????????:2;???????????????:3;????????????:4;????????????:5;????????????:6
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
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
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
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesContrReplenishAgreement.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesContrReplenishAgreement.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setApih5FlowStatus("2");
			flag = zxCtSuppliesContrReplenishAgreementMapper.updateByPrimaryKeySelective(flowDetail);
        	//???????????????????????????????????????????????????????????????
			if(StrUtil.equals(flowDetail.getCode7(), "WZ") || StrUtil.equals(flowDetail.getCode7(), "LC")) {
				ZxCtSuppliesContrReplenishShopList replenishShop = new ZxCtSuppliesContrReplenishShopList();
				replenishShop.setPp3(flowDetail.getReplenishAgreementId());
				List<ZxCtSuppliesContrReplenishShopList> replenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(replenishShop);
				if(replenishShopList.size()>0) {
					ZxCtSuppliesContrReplenishShopDetail replenishShopDetail = new ZxCtSuppliesContrReplenishShopDetail();
					replenishShopDetail.setContrAlterID(replenishShopList.get(0).getZxCtSuppliesContrReplenishShopListId());
					List<ZxCtSuppliesContrReplenishShopDetail> shopDetailList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(replenishShopDetail);
					for(ZxCtSuppliesContrReplenishShopDetail shopDetail : shopDetailList) {
						if(StrUtil.equals(shopDetail.getAlterType(), "1")) {//??????
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
						if(StrUtil.equals(shopDetail.getAlterType(), "1")) {//??????
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
			//??????????????????????????????????????????	
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
		// ??????
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
//      //????????????????????????        
        if(jsonArr.size()>0) {
        	HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);      
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishAgreementList);
        }
	}
}
