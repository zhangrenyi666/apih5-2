package com.apih5.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishAgreementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementListMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishAgreement;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtSuppliesContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContractService")
public class ZxCtSuppliesContractServiceImpl implements ZxCtSuppliesContractService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListMapper zxCtSuppliesLeaseSettlementListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListMapper zxCtSuppliesShopSettlementListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishAgreementMapper zxCtSuppliesContrReplenishAgreementMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContractListByCondition(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContract.setComID("");
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesContract.setComID(zxCtSuppliesContract.getOrgID());
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
        }        
        // ????????????
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
        // ????????????
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(zxCtSuppliesContract);
        zxCtSuppliesContractList.parallelStream().forEach((contract)->{
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(contract.getZxCtSuppliesContractId());
        	contract.setSuppliesContractFileList(zxErpFileMapper.selectByZxErpFileList(file));
        	contract.setApih5FlowStatus("2");
        });
        // ??????????????????
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContractDetail(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        // ????????????
        ZxCtSuppliesContract dbZxCtSuppliesContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContract.getZxCtSuppliesContractId());
        // ????????????
        if (dbZxCtSuppliesContract != null) {
            return repEntity.ok(dbZxCtSuppliesContract);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContract(ZxCtSuppliesContract zxCtSuppliesContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContract.setZxCtSuppliesContractId(UuidUtil.generate());
        zxCtSuppliesContract.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContractMapper.insert(zxCtSuppliesContract);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContract);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContract(ZxCtSuppliesContract zxCtSuppliesContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContract dbZxCtSuppliesContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContract.getZxCtSuppliesContractId());
        if (dbZxCtSuppliesContract != null && StrUtil.isNotEmpty(dbZxCtSuppliesContract.getZxCtSuppliesContractId())) {
           // ??????????????????
           dbZxCtSuppliesContract.setTransferAssumpsit(zxCtSuppliesContract.getTransferAssumpsit());
           // ????????????
           dbZxCtSuppliesContract.setBidDate(zxCtSuppliesContract.getBidDate());
           // ??????????????????
           dbZxCtSuppliesContract.setCode3(zxCtSuppliesContract.getCode3());
           // ????????????
           dbZxCtSuppliesContract.setQuanlityFeeRate(zxCtSuppliesContract.getQuanlityFeeRate());
           // ??????
           dbZxCtSuppliesContract.setSubject(zxCtSuppliesContract.getSubject());
           // ????????????
           dbZxCtSuppliesContract.setAgent(zxCtSuppliesContract.getAgent());
           // ????????????
           dbZxCtSuppliesContract.setSecondName(zxCtSuppliesContract.getSecondName());
           // ?????????????????????
           dbZxCtSuppliesContract.setSecondPrincipalIDCard(zxCtSuppliesContract.getSecondPrincipalIDCard());
           // ????????????
           dbZxCtSuppliesContract.setSecondPrincipal(zxCtSuppliesContract.getSecondPrincipal());
           // ??????ID
           dbZxCtSuppliesContract.setSecondID(zxCtSuppliesContract.getSecondID());
           // ??????(??????)
           dbZxCtSuppliesContract.setPp1(zxCtSuppliesContract.getPp1());
           // ??????(??????)
           dbZxCtSuppliesContract.setSecondUnitTel(zxCtSuppliesContract.getSecondUnitTel());
           // ??????(??????)
           dbZxCtSuppliesContract.setPp2(zxCtSuppliesContract.getPp2());
           // ?????????????????????
           dbZxCtSuppliesContract.setCodeP1(zxCtSuppliesContract.getCodeP1());
           // ??????????????????
           dbZxCtSuppliesContract.setThirdName(zxCtSuppliesContract.getThirdName());
           // ??????????????????
           dbZxCtSuppliesContract.setProjectChiefName(zxCtSuppliesContract.getProjectChiefName());
           // ??????????????????
           dbZxCtSuppliesContract.setProjectChiefTel(zxCtSuppliesContract.getProjectChiefTel());
           // ????????????????????????
           dbZxCtSuppliesContract.setCode4(zxCtSuppliesContract.getCode4());
           // ????????????
           dbZxCtSuppliesContract.setProjectName(zxCtSuppliesContract.getProjectName());
           // ??????????????????
           dbZxCtSuppliesContract.setProjectManagerTel(zxCtSuppliesContract.getProjectManagerTel());
           // ????????????
           dbZxCtSuppliesContract.setProjectManager(zxCtSuppliesContract.getProjectManager());
           // ????????????
           dbZxCtSuppliesContract.setFirstPrincipal(zxCtSuppliesContract.getFirstPrincipal());
           // ????????????
           dbZxCtSuppliesContract.setCode5(zxCtSuppliesContract.getCode5());
           // ????????????
           dbZxCtSuppliesContract.setShortName(zxCtSuppliesContract.getShortName());
           // ????????????
           dbZxCtSuppliesContract.setProjectNo(zxCtSuppliesContract.getProjectNo());
           // ????????????
           dbZxCtSuppliesContract.setMaterialSource(zxCtSuppliesContract.getMaterialSource());
           // ?????????????????????
           dbZxCtSuppliesContract.setStockSettleUseCount(zxCtSuppliesContract.getStockSettleUseCount());
           // ????????????????????????
           dbZxCtSuppliesContract.setStockAudit(zxCtSuppliesContract.getStockAudit());
           // code2T3
           dbZxCtSuppliesContract.setCode2T3(zxCtSuppliesContract.getCode2T3());
           // ????????????ID
           dbZxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
           // ????????????
           dbZxCtSuppliesContract.setOrgName(zxCtSuppliesContract.getOrgName());
           // ????????????ID
           dbZxCtSuppliesContract.setLocationInDeprId(zxCtSuppliesContract.getLocationInDeprId());
           // ??????(%)
           dbZxCtSuppliesContract.setTaxRate(zxCtSuppliesContract.getTaxRate());
           // ??????
           dbZxCtSuppliesContract.setFaxRate(zxCtSuppliesContract.getFaxRate());
           // ????????????
           dbZxCtSuppliesContract.setPayType(zxCtSuppliesContract.getPayType());
           // ????????????
           dbZxCtSuppliesContract.setIsDeduct(zxCtSuppliesContract.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesContract.setActualStartDate(zxCtSuppliesContract.getActualStartDate());
           // ??????????????????
           dbZxCtSuppliesContract.setActualEndDate(zxCtSuppliesContract.getActualEndDate());
           // ??????????????????????????????
           dbZxCtSuppliesContract.setEquipSettleUseCount(zxCtSuppliesContract.getEquipSettleUseCount());
           // ????????????
           dbZxCtSuppliesContract.setSignatureDate(zxCtSuppliesContract.getSignatureDate());
           // ????????????
           dbZxCtSuppliesContract.setOtherAssumpsit(zxCtSuppliesContract.getOtherAssumpsit());
           // ??????????????????
           dbZxCtSuppliesContract.setInnerContractSum(zxCtSuppliesContract.getInnerContractSum());
           // ??????????????????
           dbZxCtSuppliesContract.setPp5(zxCtSuppliesContract.getPp5());
           // ??????????????????
           dbZxCtSuppliesContract.setPp4(zxCtSuppliesContract.getPp4());
           // ????????????
           dbZxCtSuppliesContract.setManageIndex(zxCtSuppliesContract.getManageIndex());
           // ????????????
           dbZxCtSuppliesContract.setConsultative(zxCtSuppliesContract.getConsultative());
           // ??????????????????
           dbZxCtSuppliesContract.setFirstUnitTel(zxCtSuppliesContract.getFirstUnitTel());
           // ?????????????????????
           dbZxCtSuppliesContract.setFirstPrincipalIDCard(zxCtSuppliesContract.getFirstPrincipalIDCard());
           // ??????ID
           dbZxCtSuppliesContract.setFirstId(zxCtSuppliesContract.getFirstId());
           // ??????
           dbZxCtSuppliesContract.setFirstIdFormula(zxCtSuppliesContract.getFirstIdFormula());
           // ????????????
           dbZxCtSuppliesContract.setCode1(zxCtSuppliesContract.getCode1());
           // ????????????
           dbZxCtSuppliesContract.setContractStatus(zxCtSuppliesContract.getContractStatus());
           // ????????????
           dbZxCtSuppliesContract.setSecondIDFormula(zxCtSuppliesContract.getSecondIDFormula());
           // ????????????
           dbZxCtSuppliesContract.setCode8(zxCtSuppliesContract.getCode8());
           // ????????????(??????)
           dbZxCtSuppliesContract.setContractCostTax(zxCtSuppliesContract.getContractCostTax());
           // ????????????ID
           dbZxCtSuppliesContract.setFromApplyID(zxCtSuppliesContract.getFromApplyID());
           // ????????????
           dbZxCtSuppliesContract.setContent(zxCtSuppliesContract.getContent());
           // ????????????
           dbZxCtSuppliesContract.setContractName(zxCtSuppliesContract.getContractName());
           // ??????????????????
           dbZxCtSuppliesContract.setAudit(zxCtSuppliesContract.getAudit());
           // ????????????
           dbZxCtSuppliesContract.setContractType(zxCtSuppliesContract.getContractType());
           // ????????????
           dbZxCtSuppliesContract.setPp3(zxCtSuppliesContract.getPp3());
           // ????????????
           dbZxCtSuppliesContract.setCode7(zxCtSuppliesContract.getCode7());
           // ??????????????????
           dbZxCtSuppliesContract.setPlanStartDate(zxCtSuppliesContract.getPlanStartDate());
           // ??????????????????
           dbZxCtSuppliesContract.setPlanEndDate(zxCtSuppliesContract.getPlanEndDate());
           // ????????????
           dbZxCtSuppliesContract.setFirstName(zxCtSuppliesContract.getFirstName());
           // ????????????(???)
           dbZxCtSuppliesContract.setCsTimeLimit(zxCtSuppliesContract.getCsTimeLimit());
           // ????????????
           dbZxCtSuppliesContract.setCode(zxCtSuppliesContract.getCode());
           // ????????????
           dbZxCtSuppliesContract.setContractNo(zxCtSuppliesContract.getContractNo());
           // ??????????????????(??????)
           dbZxCtSuppliesContract.setContractCost(zxCtSuppliesContract.getContractCost());
           // ????????????
           dbZxCtSuppliesContract.setManageRate(zxCtSuppliesContract.getManageRate());
           // ????????????
           dbZxCtSuppliesContract.setGoodsSum(zxCtSuppliesContract.getGoodsSum());
           // ?????????????????????
           dbZxCtSuppliesContract.setProjectSettleUseCount(zxCtSuppliesContract.getProjectSettleUseCount());
           // ????????????
           dbZxCtSuppliesContract.setPaymentAssumpsit(zxCtSuppliesContract.getPaymentAssumpsit());
           // ???????????????
           dbZxCtSuppliesContract.setVentureGuaranty(zxCtSuppliesContract.getVentureGuaranty());
           // ????????????
           dbZxCtSuppliesContract.setRegisterDate(zxCtSuppliesContract.getRegisterDate());
           // ??????????????????
           dbZxCtSuppliesContract.setCode2(zxCtSuppliesContract.getCode2());
           // ?????????????????????(??????)
           dbZxCtSuppliesContract.setContractCostNoTax(zxCtSuppliesContract.getContractCostNoTax());
           // ?????????
           dbZxCtSuppliesContract.setCode6(zxCtSuppliesContract.getCode6());
           // ??????????????????
           dbZxCtSuppliesContract.setAuditContrAlterCount(zxCtSuppliesContract.getAuditContrAlterCount());
           // ?????????????????????(??????)
           dbZxCtSuppliesContract.setAlterContractSumTax(zxCtSuppliesContract.getAlterContractSumTax());
           // ???????????????????????????(??????)
           dbZxCtSuppliesContract.setAlterContractSum(zxCtSuppliesContract.getAlterContractSum());
           // ??????????????????????????????(??????)
           dbZxCtSuppliesContract.setAlterContractSumNoTax(zxCtSuppliesContract.getAlterContractSumNoTax());
           // pp9
           dbZxCtSuppliesContract.setPp9(zxCtSuppliesContract.getPp9());
           // pp10
           dbZxCtSuppliesContract.setPp10(zxCtSuppliesContract.getPp10());
           // comID
           dbZxCtSuppliesContract.setComID(zxCtSuppliesContract.getComID());
           // ????????????
           dbZxCtSuppliesContract.setSettleType(zxCtSuppliesContract.getSettleType());
           // ??????
           dbZxCtSuppliesContract.setRemarks(zxCtSuppliesContract.getRemarks());
           // ??????
           dbZxCtSuppliesContract.setSort(zxCtSuppliesContract.getSort());
           // ??????
           dbZxCtSuppliesContract.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractMapper.updateByPrimaryKey(dbZxCtSuppliesContract);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContract);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContract(List<ZxCtSuppliesContract> zxCtSuppliesContractList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContractList != null && zxCtSuppliesContractList.size() > 0) {
           ZxCtSuppliesContract zxCtSuppliesContract = new ZxCtSuppliesContract();
           zxCtSuppliesContract.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractMapper.batchDeleteUpdateZxCtSuppliesContract(zxCtSuppliesContractList, zxCtSuppliesContract);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContractList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity getZxCtSuppliesContractListByOrgId(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);    
        // ????????????
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
        // ????????????
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractListAddSettlement(zxCtSuppliesContract);
        for(ZxCtSuppliesContract contract : zxCtSuppliesContractList) {
        	if(StrUtil.equals(zxCtSuppliesContract.getCode7(), "WZ")) {
        		ZxCtSuppliesShopSettlementList settlement = new ZxCtSuppliesShopSettlementList();
        		settlement.setContractID(contract.getZxCtSuppliesContractId());
        		List<ZxCtSuppliesShopSettlementList> settlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(settlement);
        		if(settlementList.size()>0) {
        			if(!StrUtil.equals(settlementList.get(0).getApih5FlowStatus(), "2")) {
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
            			contract.setIsUsed("0");
        			}else {
        				contract.setIsUsed("1");
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
        			}
        		}else {
        			contract.setInitSerialNumber(0);	
        		}
        	}else if(StrUtil.equals(zxCtSuppliesContract.getCode7(), "WL")){
        		ZxCtSuppliesLeaseSettlementList settlement = new ZxCtSuppliesLeaseSettlementList();
        		settlement.setContractID(contract.getZxCtSuppliesContractId());
        		List<ZxCtSuppliesLeaseSettlementList> settlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(settlement);
        		if(settlementList.size()>0) {
        			if(!StrUtil.equals(settlementList.get(0).getApih5FlowStatus(), "2")) {
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
            			contract.setIsUsed("0");
        			}else {
        				contract.setIsUsed("1");
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
        			}
        		}else {
        			contract.setInitSerialNumber(0);
        		}        		
        	}
        }
        // ??????????????????
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
    }

	@Override
	public ResponseEntity updateZxCtSuppliesContractByContId(ZxCtSuppliesContract zxCtSuppliesContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContract dbZxCtSuppliesContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContract.getZxCtSuppliesContractId());
        if (dbZxCtSuppliesContract != null && StrUtil.isNotEmpty(dbZxCtSuppliesContract.getZxCtSuppliesContractId())) {
           // ??????????????????
           dbZxCtSuppliesContract.setTransferAssumpsit(zxCtSuppliesContract.getTransferAssumpsit());
           // ????????????
           dbZxCtSuppliesContract.setBidDate(zxCtSuppliesContract.getBidDate());
           // ??????????????????
           dbZxCtSuppliesContract.setCode3(zxCtSuppliesContract.getCode3());
           // ????????????
           dbZxCtSuppliesContract.setQuanlityFeeRate(zxCtSuppliesContract.getQuanlityFeeRate());
           // ??????
           dbZxCtSuppliesContract.setSubject(zxCtSuppliesContract.getSubject());
           // ????????????
           dbZxCtSuppliesContract.setAgent(zxCtSuppliesContract.getAgent());
           // ????????????
           dbZxCtSuppliesContract.setSecondName(zxCtSuppliesContract.getSecondName());
           // ?????????????????????
           dbZxCtSuppliesContract.setSecondPrincipalIDCard(zxCtSuppliesContract.getSecondPrincipalIDCard());
           // ????????????
           dbZxCtSuppliesContract.setSecondPrincipal(zxCtSuppliesContract.getSecondPrincipal());
           // ??????ID
//           dbZxCtSuppliesContract.setSecondID(zxCtSuppliesContract.getSecondID());
           // ??????(??????)
           dbZxCtSuppliesContract.setPp1(zxCtSuppliesContract.getPp1());
           // ??????(??????)
           dbZxCtSuppliesContract.setSecondUnitTel(zxCtSuppliesContract.getSecondUnitTel());
           // ??????(??????)
           dbZxCtSuppliesContract.setPp2(zxCtSuppliesContract.getPp2());
           // ?????????????????????
           dbZxCtSuppliesContract.setCodeP1(zxCtSuppliesContract.getCodeP1());
           // ??????????????????
           dbZxCtSuppliesContract.setThirdName(zxCtSuppliesContract.getThirdName());
           // ??????????????????
           dbZxCtSuppliesContract.setProjectChiefName(zxCtSuppliesContract.getProjectChiefName());
           // ??????????????????
           dbZxCtSuppliesContract.setProjectChiefTel(zxCtSuppliesContract.getProjectChiefTel());
           // ????????????????????????
           dbZxCtSuppliesContract.setCode4(zxCtSuppliesContract.getCode4());
           // ????????????
           dbZxCtSuppliesContract.setProjectName(zxCtSuppliesContract.getProjectName());
           // ??????????????????
           dbZxCtSuppliesContract.setProjectManagerTel(zxCtSuppliesContract.getProjectManagerTel());
           // ????????????
           dbZxCtSuppliesContract.setProjectManager(zxCtSuppliesContract.getProjectManager());
           // ????????????
           dbZxCtSuppliesContract.setFirstPrincipal(zxCtSuppliesContract.getFirstPrincipal());
           // ????????????
           dbZxCtSuppliesContract.setCode5(zxCtSuppliesContract.getCode5());
           // ????????????
           dbZxCtSuppliesContract.setShortName(zxCtSuppliesContract.getShortName());
           // ????????????
           dbZxCtSuppliesContract.setProjectNo(zxCtSuppliesContract.getProjectNo());
           // ????????????
           dbZxCtSuppliesContract.setMaterialSource(zxCtSuppliesContract.getMaterialSource());
           // ?????????????????????
           dbZxCtSuppliesContract.setStockSettleUseCount(zxCtSuppliesContract.getStockSettleUseCount());
           // ????????????????????????
           dbZxCtSuppliesContract.setStockAudit(zxCtSuppliesContract.getStockAudit());
           // code2T3
           dbZxCtSuppliesContract.setCode2T3(zxCtSuppliesContract.getCode2T3());
           // ????????????ID
           dbZxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
           // ????????????
           dbZxCtSuppliesContract.setOrgName(zxCtSuppliesContract.getOrgName());
           // ????????????ID
           dbZxCtSuppliesContract.setLocationInDeprId(zxCtSuppliesContract.getLocationInDeprId());
           // ??????(%)
           dbZxCtSuppliesContract.setTaxRate(zxCtSuppliesContract.getTaxRate());
           // ??????
           dbZxCtSuppliesContract.setFaxRate(zxCtSuppliesContract.getFaxRate());
           // ????????????
           dbZxCtSuppliesContract.setPayType(zxCtSuppliesContract.getPayType());
           // ????????????
           dbZxCtSuppliesContract.setIsDeduct(zxCtSuppliesContract.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesContract.setActualStartDate(zxCtSuppliesContract.getActualStartDate());
           // ??????????????????
           dbZxCtSuppliesContract.setActualEndDate(zxCtSuppliesContract.getActualEndDate());
           // ??????????????????????????????
           dbZxCtSuppliesContract.setEquipSettleUseCount(zxCtSuppliesContract.getEquipSettleUseCount());
           // ????????????
           dbZxCtSuppliesContract.setSignatureDate(zxCtSuppliesContract.getSignatureDate());
           // ????????????
           dbZxCtSuppliesContract.setOtherAssumpsit(zxCtSuppliesContract.getOtherAssumpsit());
           // ??????????????????
           dbZxCtSuppliesContract.setInnerContractSum(zxCtSuppliesContract.getInnerContractSum());
           // ??????????????????
           dbZxCtSuppliesContract.setPp5(zxCtSuppliesContract.getPp5());
           // ??????????????????
           dbZxCtSuppliesContract.setPp4(zxCtSuppliesContract.getPp4());
           // ????????????
           dbZxCtSuppliesContract.setManageIndex(zxCtSuppliesContract.getManageIndex());
           // ????????????
           dbZxCtSuppliesContract.setConsultative(zxCtSuppliesContract.getConsultative());
           // ??????????????????
           dbZxCtSuppliesContract.setFirstUnitTel(zxCtSuppliesContract.getFirstUnitTel());
           // ?????????????????????
           dbZxCtSuppliesContract.setFirstPrincipalIDCard(zxCtSuppliesContract.getFirstPrincipalIDCard());
           // ??????ID
//           dbZxCtSuppliesContract.setFirstId(zxCtSuppliesContract.getFirstId());
           // ??????
           dbZxCtSuppliesContract.setFirstIdFormula(zxCtSuppliesContract.getFirstIdFormula());
           // ????????????
           dbZxCtSuppliesContract.setCode1(zxCtSuppliesContract.getCode1());
           // ????????????
           dbZxCtSuppliesContract.setContractStatus(zxCtSuppliesContract.getContractStatus());
           // ????????????
           dbZxCtSuppliesContract.setSecondIDFormula(zxCtSuppliesContract.getSecondIDFormula());
           // ????????????
           dbZxCtSuppliesContract.setCode8(zxCtSuppliesContract.getCode8());
           // ????????????(??????)
           dbZxCtSuppliesContract.setContractCostTax(zxCtSuppliesContract.getContractCostTax());
           // ????????????ID
//           dbZxCtSuppliesContract.setFromApplyID(zxCtSuppliesContract.getFromApplyID());
           // ????????????
           dbZxCtSuppliesContract.setContent(zxCtSuppliesContract.getContent());
           // ????????????
           dbZxCtSuppliesContract.setContractName(zxCtSuppliesContract.getContractName());
           // ??????????????????
           dbZxCtSuppliesContract.setAudit(zxCtSuppliesContract.getAudit());
           // ????????????
           dbZxCtSuppliesContract.setContractType(zxCtSuppliesContract.getContractType());
           // ????????????
           dbZxCtSuppliesContract.setPp3(zxCtSuppliesContract.getPp3());
           // ????????????
           dbZxCtSuppliesContract.setCode7(zxCtSuppliesContract.getCode7());
           // ??????????????????
           dbZxCtSuppliesContract.setPlanStartDate(zxCtSuppliesContract.getPlanStartDate());
           // ??????????????????
           dbZxCtSuppliesContract.setPlanEndDate(zxCtSuppliesContract.getPlanEndDate());
           // ????????????
           dbZxCtSuppliesContract.setFirstName(zxCtSuppliesContract.getFirstName());
           // ????????????(???)
           dbZxCtSuppliesContract.setCsTimeLimit(zxCtSuppliesContract.getCsTimeLimit());
           // ????????????
           dbZxCtSuppliesContract.setCode(zxCtSuppliesContract.getCode());
           // ????????????
           dbZxCtSuppliesContract.setContractNo(zxCtSuppliesContract.getContractNo());
           // ??????????????????(??????)
           dbZxCtSuppliesContract.setContractCost(zxCtSuppliesContract.getContractCost());
           // ????????????
           dbZxCtSuppliesContract.setManageRate(zxCtSuppliesContract.getManageRate());
           // ????????????
           dbZxCtSuppliesContract.setGoodsSum(zxCtSuppliesContract.getGoodsSum());
           // ?????????????????????
           dbZxCtSuppliesContract.setProjectSettleUseCount(zxCtSuppliesContract.getProjectSettleUseCount());
           // ????????????
           dbZxCtSuppliesContract.setPaymentAssumpsit(zxCtSuppliesContract.getPaymentAssumpsit());
           // ???????????????
           dbZxCtSuppliesContract.setVentureGuaranty(zxCtSuppliesContract.getVentureGuaranty());
           // ????????????
           dbZxCtSuppliesContract.setRegisterDate(zxCtSuppliesContract.getRegisterDate());
           // ??????????????????
           dbZxCtSuppliesContract.setCode2(zxCtSuppliesContract.getCode2());
           // ?????????????????????(??????)
           dbZxCtSuppliesContract.setContractCostNoTax(zxCtSuppliesContract.getContractCostNoTax());
           // ?????????
           dbZxCtSuppliesContract.setCode6(zxCtSuppliesContract.getCode6());
           // ??????????????????
           dbZxCtSuppliesContract.setAuditContrAlterCount(zxCtSuppliesContract.getAuditContrAlterCount());
           // ?????????????????????(??????)
           dbZxCtSuppliesContract.setAlterContractSumTax(zxCtSuppliesContract.getAlterContractSumTax());
           // ???????????????????????????(??????)
           dbZxCtSuppliesContract.setAlterContractSum(zxCtSuppliesContract.getAlterContractSum());
           // ??????????????????????????????(??????)
           dbZxCtSuppliesContract.setAlterContractSumNoTax(zxCtSuppliesContract.getAlterContractSumNoTax());
           // pp9
           dbZxCtSuppliesContract.setPp9(zxCtSuppliesContract.getPp9());
           // pp10
           dbZxCtSuppliesContract.setPp10(zxCtSuppliesContract.getPp10());
           // comID
           dbZxCtSuppliesContract.setComID(zxCtSuppliesContract.getComID());
           // ????????????
           dbZxCtSuppliesContract.setSettleType(zxCtSuppliesContract.getSettleType());
           // ??????
           dbZxCtSuppliesContract.setRemarks(zxCtSuppliesContract.getRemarks());
           // ??????
           dbZxCtSuppliesContract.setSort(zxCtSuppliesContract.getSort());
           // ??????
           dbZxCtSuppliesContract.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractMapper.updateByPrimaryKey(dbZxCtSuppliesContract);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
    	    //????????????????????????
    	    if(zxCtSuppliesContract.getSuppliesContractFileList() != null && zxCtSuppliesContract.getSuppliesContractFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(zxCtSuppliesContract.getZxCtSuppliesContractId());
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, realName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesContract.getSuppliesContractFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtSuppliesContract.getZxCtSuppliesContractId());                
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);  
         	   }
            }        	
            return repEntity.ok("sys.data.update",zxCtSuppliesContract);
        }
	}

	@Override
	public ResponseEntity getZxCtSuppliesContractListAddAgreementNum(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContract.setComID("");
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesContract.setComID(zxCtSuppliesContract.getOrgID());
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
        }        
        // ????????????
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
        // ????????????
        zxCtSuppliesContract.setAgreementFlag("1");//??????????????????????????????????????????????????????????????????
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(zxCtSuppliesContract);
        zxCtSuppliesContractList.parallelStream().forEach((contract)->{
        	ZxCtSuppliesContrReplenishAgreement agreement = new ZxCtSuppliesContrReplenishAgreement();
        	agreement.setPp6(contract.getZxCtSuppliesContractId());
        	String code = zxCtSuppliesContrReplenishAgreementMapper.getZxCtReplenishAgreementNoByCode(agreement);
    		if(StrUtil.isNotEmpty(code)) {
    			DecimalFormat decimalFormat=new DecimalFormat("00");		
    			String headCode = code.substring(0, code.lastIndexOf("???")+1);
    			 String[] numArr = code.split("-???", code.lastIndexOf("-"));
    		        String num =decimalFormat.format(Integer.parseInt(numArr[numArr.length-1])+1);
    		        code =  headCode + num;
    		}else {
    			code = contract.getContractNo()+"-???01";
    		}  
    		contract.setAgreementNum(code);
        });
        // ??????????????????
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtSuppliesContractListByFirstId(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
//        //??????????????????0?????????1?????????
//        if(StrUtil.equals(zxCtSuppliesContract.getIsTurnover(), "0")) {
//        	
//        }else if(StrUtil.equals(zxCtSuppliesContract.getIsTurnover(), "1")){
//        	
//        }
        if(StrUtil.isEmpty(zxCtSuppliesContract.getFirstId())) {
        	return repEntity.layerMessage("NO", "?????????????????????");
        }
        if(StrUtil.isEmpty(zxCtSuppliesContract.getSecondID())) {
        	return repEntity.layerMessage("NO", "?????????????????????");
        }        
        // ????????????
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractListByFirstId(zxCtSuppliesContract);
        // ??????????????????
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
	}
}
