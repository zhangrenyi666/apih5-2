package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettleItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementListMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtSuppliesLeaseSettlementListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxCtSuppliesLeaseSettlementListService")
public class ZxCtSuppliesLeaseSettlementListServiceImpl implements ZxCtSuppliesLeaseSettlementListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListMapper zxCtSuppliesLeaseSettlementListMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementMapper zxCtSuppliesLeasePaySettlementMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettleItemMapper zxCtSuppliesLeaseResSettleItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementItemMapper zxCtSuppliesLeaseSettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettlementMapper zxCtSuppliesLeaseResSettlementMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementItemMapper zxCtSuppliesLeasePaySettlementItemMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesLeaseSettlementListListByCondition(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        if (zxCtSuppliesLeaseSettlementList == null) {
            zxCtSuppliesLeaseSettlementList = new ZxCtSuppliesLeaseSettlementList();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();        
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesLeaseSettlementList.setCompanyId("");
        	zxCtSuppliesLeaseSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesLeaseSettlementList.setCompanyId(zxCtSuppliesLeaseSettlementList.getOrgID());
        	zxCtSuppliesLeaseSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesLeaseSettlementList.setOrgID(zxCtSuppliesLeaseSettlementList.getOrgID());
        }        
        // ????????????
        PageHelper.startPage(zxCtSuppliesLeaseSettlementList.getPage(),zxCtSuppliesLeaseSettlementList.getLimit());
        // ????????????
        List<ZxCtSuppliesLeaseSettlementList> zxCtSuppliesLeaseSettlementListList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(zxCtSuppliesLeaseSettlementList);
        // ??????????????????
        PageInfo<ZxCtSuppliesLeaseSettlementList> p = new PageInfo<>(zxCtSuppliesLeaseSettlementListList);

        return repEntity.okList(zxCtSuppliesLeaseSettlementListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeaseSettlementListDetail(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        if (zxCtSuppliesLeaseSettlementList == null) {
            zxCtSuppliesLeaseSettlementList = new ZxCtSuppliesLeaseSettlementList();
        }
        BigDecimal thisAmt = new BigDecimal(0);
        BigDecimal thisAmtNoTax = new BigDecimal(0);
        BigDecimal thisAmtTax = new BigDecimal(0);
        BigDecimal taxRate = new BigDecimal(1);//????????????
        // ????????????
        ZxCtSuppliesLeaseSettlementList dbZxCtSuppliesLeaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        // ????????????
        if (dbZxCtSuppliesLeaseSettlementList != null) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(dbZxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        	file.setOtherType("1");
        	dbZxCtSuppliesLeaseSettlementList.setDocumentFileList(zxErpFileMapper.selectByZxErpFileList(file));
        	file = new ZxErpFile();
			file.setOtherType("0");
        	file.setOtherId(dbZxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	dbZxCtSuppliesLeaseSettlementList.setSettlementFileList(fileList);             	
            ZxCtSuppliesLeasePaySettlement settlement = new ZxCtSuppliesLeasePaySettlement();
            settlement.setBillID(dbZxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
            List<ZxCtSuppliesLeasePaySettlement> settlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(settlement);
            if(settlementList.size()>0) {        	
            	dbZxCtSuppliesLeaseSettlementList.setPayFineAmt(settlementList.get(0).getFineAmt());
            	dbZxCtSuppliesLeaseSettlementList.setPayFoodAmt(settlementList.get(0).getFoodAmt());
            	dbZxCtSuppliesLeaseSettlementList.setPayInOutAmt(settlementList.get(0).getInOutAmt());
            	dbZxCtSuppliesLeaseSettlementList.setPayOtherAmt(settlementList.get(0).getOtherAmt());
            	dbZxCtSuppliesLeaseSettlementList.setPayThisAmt(settlementList.get(0).getThisAmt());
            	dbZxCtSuppliesLeaseSettlementList.setPayThisAmtNoTax(settlementList.get(0).getThisAmtNoTax());
            	dbZxCtSuppliesLeaseSettlementList.setPayThisAmtTax(settlementList.get(0).getThisAmtTax());
            	dbZxCtSuppliesLeaseSettlementList.setPayTotalAmt(settlementList.get(0).getTotalAmt());
            	dbZxCtSuppliesLeaseSettlementList.setPayUpAmt(settlementList.get(0).getUpAmt());
            }        	
            ZxCtSuppliesLeaseResSettlement resSettlement = new ZxCtSuppliesLeaseResSettlement();
            resSettlement.setBillID(dbZxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
            List<ZxCtSuppliesLeaseResSettlement> resSettlementList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(resSettlement);
            if(resSettlementList.size()>0) {            	
            	dbZxCtSuppliesLeaseSettlementList.setThisEquipAmtNoTax(resSettlementList.get(0).getThisAmtNoTax());
            	dbZxCtSuppliesLeaseSettlementList.setThisEquipAmt(resSettlementList.get(0).getThisAmt());
            	dbZxCtSuppliesLeaseSettlementList.setThisEquipAmtTax(resSettlementList.get(0).getThisAmtTax());
            }
            return repEntity.ok(dbZxCtSuppliesLeaseSettlementList);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesLeaseSettlementList(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesLeaseSettlementList.setZxCtSuppliesLeaseSettlementListId(UuidUtil.generate());
        zxCtSuppliesLeaseSettlementList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeaseSettlementListMapper.insert(zxCtSuppliesLeaseSettlementList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeaseSettlementList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesLeaseSettlementList(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeaseSettlementList dbZxCtSuppliesLeaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        if (dbZxCtSuppliesLeaseSettlementList != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId())) {
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setEditTime(zxCtSuppliesLeaseSettlementList.getEditTime());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setAuditStatus(zxCtSuppliesLeaseSettlementList.getAuditStatus());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setNotPassNum(zxCtSuppliesLeaseSettlementList.getNotPassNum());
           // ???????????????
           dbZxCtSuppliesLeaseSettlementList.setSecondIDCodeBh(zxCtSuppliesLeaseSettlementList.getSecondIDCodeBh());
           // ???????????????
           dbZxCtSuppliesLeaseSettlementList.setSecondCode(zxCtSuppliesLeaseSettlementList.getSecondCode());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setSummary(zxCtSuppliesLeaseSettlementList.getSummary());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setResponseUnitName(zxCtSuppliesLeaseSettlementList.getResponseUnitName());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setResponseUnitCode(zxCtSuppliesLeaseSettlementList.getResponseUnitCode());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setEstPayDate(zxCtSuppliesLeaseSettlementList.getEstPayDate());
           // ??????ID
           dbZxCtSuppliesLeaseSettlementList.setSecondID(zxCtSuppliesLeaseSettlementList.getSecondID());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setBusinessDate(zxCtSuppliesLeaseSettlementList.getBusinessDate());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setOrgName(zxCtSuppliesLeaseSettlementList.getOrgName());
           // ??????ID
           dbZxCtSuppliesLeaseSettlementList.setOrgID(zxCtSuppliesLeaseSettlementList.getOrgID());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setZjgcxmXmmc(zxCtSuppliesLeaseSettlementList.getZjgcxmXmmc());
           // ???????????????ID
           dbZxCtSuppliesLeaseSettlementList.setBillID(zxCtSuppliesLeaseSettlementList.getBillID());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setIsSend(zxCtSuppliesLeaseSettlementList.getIsSend());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setSendDate(zxCtSuppliesLeaseSettlementList.getSendDate());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setOrgCertificate(zxCtSuppliesLeaseSettlementList.getOrgCertificate());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setReportDate(zxCtSuppliesLeaseSettlementList.getReportDate());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setReportPerson(zxCtSuppliesLeaseSettlementList.getReportPerson());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setComOrders(zxCtSuppliesLeaseSettlementList.getComOrders());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setComName(zxCtSuppliesLeaseSettlementList.getComName());
           // ????????????ID
           dbZxCtSuppliesLeaseSettlementList.setComID(zxCtSuppliesLeaseSettlementList.getComID());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setTcse(zxCtSuppliesLeaseSettlementList.getTcse());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setFlowLock(zxCtSuppliesLeaseSettlementList.getFlowLock());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setIsFirst(zxCtSuppliesLeaseSettlementList.getIsFirst());
           // ???????????????
           dbZxCtSuppliesLeaseSettlementList.setIsReport(zxCtSuppliesLeaseSettlementList.getIsReport());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setIsSign(zxCtSuppliesLeaseSettlementList.getIsSign());
           // ???????????????
           dbZxCtSuppliesLeaseSettlementList.setIsAudit(zxCtSuppliesLeaseSettlementList.getIsAudit());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setFlowBeginDate(zxCtSuppliesLeaseSettlementList.getFlowBeginDate());
           // ????????????ID
           dbZxCtSuppliesLeaseSettlementList.setInstProcessID(zxCtSuppliesLeaseSettlementList.getInstProcessID());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setFlowEndDate(zxCtSuppliesLeaseSettlementList.getFlowEndDate());
           // ??????ID
           dbZxCtSuppliesLeaseSettlementList.setWorkItemID(zxCtSuppliesLeaseSettlementList.getWorkItemID());
           // ?????????ID
           dbZxCtSuppliesLeaseSettlementList.setOldWorkItemID(zxCtSuppliesLeaseSettlementList.getOldWorkItemID());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setAssessUnitName(zxCtSuppliesLeaseSettlementList.getAssessUnitName());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setAssessUnitCode(zxCtSuppliesLeaseSettlementList.getAssessUnitCode());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setTotalPayAmt(zxCtSuppliesLeaseSettlementList.getTotalPayAmt());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setTotalAmt(zxCtSuppliesLeaseSettlementList.getTotalAmt());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setUpTotalAmt(zxCtSuppliesLeaseSettlementList.getUpTotalAmt());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setEndDate(zxCtSuppliesLeaseSettlementList.getEndDate());
           // ????????????
           if(StrUtil.isNotEmpty(zxCtSuppliesLeaseSettlementList.getPeriod())) {        	   
        	   dbZxCtSuppliesLeaseSettlementList.setPeriod(zxCtSuppliesLeaseSettlementList.getPeriod());
           }
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setContent(zxCtSuppliesLeaseSettlementList.getContent());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setBillType(zxCtSuppliesLeaseSettlementList.getBillType());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setTcje(zxCtSuppliesLeaseSettlementList.getTcje());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setSetDir(zxCtSuppliesLeaseSettlementList.getSetDir());
           // ???????????????
           dbZxCtSuppliesLeaseSettlementList.setBillNo(zxCtSuppliesLeaseSettlementList.getBillNo());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setCountPerson(zxCtSuppliesLeaseSettlementList.getCountPerson());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setExchangeRate(zxCtSuppliesLeaseSettlementList.getExchangeRate());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setAccountUnitId(zxCtSuppliesLeaseSettlementList.getAccountUnitId());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setAccountUnitName(zxCtSuppliesLeaseSettlementList.getAccountUnitName());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setAccountDepCode(zxCtSuppliesLeaseSettlementList.getAccountDepCode());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setSecondName(zxCtSuppliesLeaseSettlementList.getSecondName());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setContractName(zxCtSuppliesLeaseSettlementList.getContractName());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setContrType(zxCtSuppliesLeaseSettlementList.getContrType());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setCode7(zxCtSuppliesLeaseSettlementList.getCode7());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setContractNo(zxCtSuppliesLeaseSettlementList.getContractNo());
           // ??????ID
           dbZxCtSuppliesLeaseSettlementList.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setReCountPerson(zxCtSuppliesLeaseSettlementList.getReCountPerson());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setNumOfSheets(zxCtSuppliesLeaseSettlementList.getNumOfSheets());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setFlowBeginPerson(zxCtSuppliesLeaseSettlementList.getFlowBeginPerson());
           // ???????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setTchljjsje(zxCtSuppliesLeaseSettlementList.getTchljjsje());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setExpDate(zxCtSuppliesLeaseSettlementList.getExpDate());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setCreateTime(zxCtSuppliesLeaseSettlementList.getCreateTime());
           // ????????????id
           dbZxCtSuppliesLeaseSettlementList.setFiId(zxCtSuppliesLeaseSettlementList.getFiId());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setCwStatusRemark(zxCtSuppliesLeaseSettlementList.getCwStatusRemark());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setCwStatus(zxCtSuppliesLeaseSettlementList.getCwStatus());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setCurrency(zxCtSuppliesLeaseSettlementList.getCurrency());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setThisPayAmt(zxCtSuppliesLeaseSettlementList.getThisPayAmt());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setThisAmtTax(zxCtSuppliesLeaseSettlementList.getThisAmtTax());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setThisAmt(zxCtSuppliesLeaseSettlementList.getThisAmt());
           // ???????????????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setThisAmtNoTax(zxCtSuppliesLeaseSettlementList.getThisAmtNoTax());
           // ???????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setBqtchjsje(zxCtSuppliesLeaseSettlementList.getBqtchjsje());
           // ?????????????????????
           dbZxCtSuppliesLeaseSettlementList.setBqtchse(zxCtSuppliesLeaseSettlementList.getBqtchse());
           // zjgcxm_xmbh
           dbZxCtSuppliesLeaseSettlementList.setZjgcxmXmbh(zxCtSuppliesLeaseSettlementList.getZjgcxmXmbh());
           // zjgcxm_nm
           dbZxCtSuppliesLeaseSettlementList.setZjgcxmNm(zxCtSuppliesLeaseSettlementList.getZjgcxmNm());
           // upWorkItemID
           dbZxCtSuppliesLeaseSettlementList.setUpWorkItemID(zxCtSuppliesLeaseSettlementList.getUpWorkItemID());
           // taxRate
           dbZxCtSuppliesLeaseSettlementList.setTaxRate(zxCtSuppliesLeaseSettlementList.getTaxRate());
           // oaOrgID
           dbZxCtSuppliesLeaseSettlementList.setOaOrgID(zxCtSuppliesLeaseSettlementList.getOaOrgID());
           // notDisplay
           dbZxCtSuppliesLeaseSettlementList.setNotDisplay(zxCtSuppliesLeaseSettlementList.getNotDisplay());
           // isDeduct
           dbZxCtSuppliesLeaseSettlementList.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
           // contractCost
           dbZxCtSuppliesLeaseSettlementList.setContractCost(zxCtSuppliesLeaseSettlementList.getContractCost());
           // ??????????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setAppraisal(zxCtSuppliesLeaseSettlementList.getAppraisal());
           // ???????????????
           dbZxCtSuppliesLeaseSettlementList.setSignedNo(zxCtSuppliesLeaseSettlementList.getSignedNo());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setOtherInfo(zxCtSuppliesLeaseSettlementList.getOtherInfo());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setStartDate(zxCtSuppliesLeaseSettlementList.getStartDate());
           // ??????????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setUseSignedOrder(zxCtSuppliesLeaseSettlementList.getUseSignedOrder());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setIsMaxPeriod(zxCtSuppliesLeaseSettlementList.getIsMaxPeriod());
           // ?????????????????????
           dbZxCtSuppliesLeaseSettlementList.setIsFished(zxCtSuppliesLeaseSettlementList.getIsFished());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setUseCount(zxCtSuppliesLeaseSettlementList.getUseCount());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setSerialNumber(zxCtSuppliesLeaseSettlementList.getSerialNumber());
           // ????????????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setTotalEquipAmt(zxCtSuppliesLeaseSettlementList.getTotalEquipAmt());
           // ???????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setInitSerialNumber(zxCtSuppliesLeaseSettlementList.getInitSerialNumber());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setContractAmt(zxCtSuppliesLeaseSettlementList.getContractAmt());
           // ???????????????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setChangeAmt(zxCtSuppliesLeaseSettlementList.getChangeAmt());
           // ????????????????????????(???)
           dbZxCtSuppliesLeaseSettlementList.setThisEquipAmt(zxCtSuppliesLeaseSettlementList.getThisEquipAmt());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setRemarks(zxCtSuppliesLeaseSettlementList.getRemarks());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setSort(zxCtSuppliesLeaseSettlementList.getSort());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseSettlementList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseSettlementList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseSettlementList(List<ZxCtSuppliesLeaseSettlementList> zxCtSuppliesLeaseSettlementListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtSuppliesLeaseSettlementListList != null && zxCtSuppliesLeaseSettlementListList.size() > 0) {
            for(ZxCtSuppliesLeaseSettlementList apply : zxCtSuppliesLeaseSettlementListList){
            	if(StrUtil.isNotEmpty(apply.getWorkId())) {
            		jsonArr.add(apply.getWorkId());
            	}
            //?????????????????????????????????
            	ZxCtSuppliesLeasePaySettlement resSettle = new ZxCtSuppliesLeasePaySettlement();
            	resSettle.setBillID(apply.getZxCtSuppliesLeaseSettlementListId());
            	List<ZxCtSuppliesLeasePaySettlement> resSettleList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(resSettle);
            	if(resSettleList.size()>0) {
            		resSettle.setModifyUserInfo(userKey, realName);
            		zxCtSuppliesLeasePaySettlementMapper.batchDeleteUpdateZxCtSuppliesLeasePaySettlement(resSettleList, resSettle);
            	}
            //
            	ZxCtSuppliesLeaseResSettleItem paySettlement = new ZxCtSuppliesLeaseResSettleItem();
            	paySettlement.setMainID(apply.getZxCtSuppliesLeaseSettlementListId());
            	List<ZxCtSuppliesLeaseResSettleItem> paySettlementList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(paySettlement);
            	if(paySettlementList.size()>0) {
            		paySettlement.setModifyUserInfo(userKey, realName);
            		zxCtSuppliesLeaseResSettleItemMapper.batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(paySettlementList, paySettlement);
            	}
            //
            	ZxCtSuppliesLeaseSettlementItem item = new ZxCtSuppliesLeaseSettlementItem();
            	item.setMainID(apply.getZxCtSuppliesLeaseSettlementListId());
            	List<ZxCtSuppliesLeaseSettlementItem> itemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(item);
            	if(itemList.size()>0) {
            		item.setModifyUserInfo(userKey, realName);	
            		zxCtSuppliesLeaseSettlementItemMapper.batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(itemList, item);
            	}
            }
           ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList = new ZxCtSuppliesLeaseSettlementList();
           zxCtSuppliesLeaseSettlementList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseSettlementListMapper.batchDeleteUpdateZxCtSuppliesLeaseSettlementList(zxCtSuppliesLeaseSettlementListList, zxCtSuppliesLeaseSettlementList);
           for(ZxCtSuppliesLeaseSettlementList settlement : zxCtSuppliesLeaseSettlementListList) {
        	   ZxCtSuppliesContract dbZxContract = zxCtSuppliesContractMapper.selectByPrimaryKey(settlement.getContractID());
        	   if(dbZxContract != null) {
        		   if(StrUtil.equals(settlement.getIsFirst(), "1")) {
        			   dbZxContract.setSettleType("1");
        			   dbZxContract.setModifyUserInfo(userKey, realName);
        			   zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbZxContract);
        		   }
        	   }
           }
        }
//      //????????????????????????        
        if(jsonArr.size()>0) {
        	HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);      
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeaseSettlementListList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity getZxCtSuppliesLeaseSettlementListListByOrgId(
    		ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        if (zxCtSuppliesLeaseSettlementList == null) {
            zxCtSuppliesLeaseSettlementList = new ZxCtSuppliesLeaseSettlementList();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();        
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesLeaseSettlementList.setComID("");
        	zxCtSuppliesLeaseSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesLeaseSettlementList.setComID(zxCtSuppliesLeaseSettlementList.getOrgID());
        	zxCtSuppliesLeaseSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesLeaseSettlementList.setOrgID(zxCtSuppliesLeaseSettlementList.getOrgID());
        }    
        // ????????????
        PageHelper.startPage(zxCtSuppliesLeaseSettlementList.getPage(),zxCtSuppliesLeaseSettlementList.getLimit());
        // ????????????
        List<ZxCtSuppliesLeaseSettlementList> zxCtSuppliesLeaseSettlementListList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(zxCtSuppliesLeaseSettlementList);
        zxCtSuppliesLeaseSettlementListList.parallelStream().forEach((lease)->{    
        	if(StrUtil.isNotEmpty(lease.getPeriod())) {        		
        		StringBuffer stringBuffer = new StringBuffer(lease.getPeriod());
        		lease.setPeriod(stringBuffer.insert(4, "-").toString());
        		lease.setPeriodDate(DateUtil.parse(lease.getPeriod(), "yyyy-MM"));
        	}
        	ZxErpFile file = new ZxErpFile();
			file.setOtherType("0");
        	file.setOtherId(lease.getZxCtSuppliesLeaseSettlementListId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	lease.setSettlementFileList(fileList);
        });
        // ??????????????????
        PageInfo<ZxCtSuppliesLeaseSettlementList> p = new PageInfo<>(zxCtSuppliesLeaseSettlementListList);

        return repEntity.okList(zxCtSuppliesLeaseSettlementListList, p.getTotal());
    }
    
    @Override
    public ResponseEntity updateZxCtSuppliesLeaseSettlementListByOrgId(
    		ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeaseSettlementList dbZxCtSuppliesLeaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        if (dbZxCtSuppliesLeaseSettlementList != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId())) {
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setSummary(zxCtSuppliesLeaseSettlementList.getSummary());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setResponseUnitName(zxCtSuppliesLeaseSettlementList.getResponseUnitName());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setResponseUnitCode(zxCtSuppliesLeaseSettlementList.getResponseUnitCode());
           // ??????????????????
           dbZxCtSuppliesLeaseSettlementList.setEstPayDate(zxCtSuppliesLeaseSettlementList.getEstPayDate());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setBusinessDate(zxCtSuppliesLeaseSettlementList.getBusinessDate());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setReportDate(zxCtSuppliesLeaseSettlementList.getReportDate());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setReportPerson(zxCtSuppliesLeaseSettlementList.getReportPerson());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setUpTotalAmt(zxCtSuppliesLeaseSettlementList.getUpTotalAmt());
           // ????????????????????????
           dbZxCtSuppliesLeaseSettlementList.setEndDate(zxCtSuppliesLeaseSettlementList.getEndDate());
           // ????????????
           dbZxCtSuppliesLeaseSettlementList.setContent(zxCtSuppliesLeaseSettlementList.getContent());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setCountPerson(zxCtSuppliesLeaseSettlementList.getCountPerson());
           // ?????????
           dbZxCtSuppliesLeaseSettlementList.setReCountPerson(zxCtSuppliesLeaseSettlementList.getReCountPerson());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setRemarks(zxCtSuppliesLeaseSettlementList.getRemarks());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setSort(zxCtSuppliesLeaseSettlementList.getSort());
           // ??????
           dbZxCtSuppliesLeaseSettlementList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseSettlementList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	if(zxCtSuppliesLeaseSettlementList.getSettlementFileList() != null  && zxCtSuppliesLeaseSettlementList.getSettlementFileList().size()>0) {
        		ZxErpFile erpFile = new ZxErpFile(); 
    			erpFile.setOtherType("0");
        		erpFile.setOtherId(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(erpFile);
        		if(fileList.size()>0) {
        			erpFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, erpFile);
        		}
        		fileList = zxCtSuppliesLeaseSettlementList.getSettlementFileList();
        		fileList.parallelStream().forEach((file)->{
        			file.setOtherId(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        			file.setUid((UuidUtil.generate()));
        			file.setOtherType("0");
        			file.setCreateUserInfo(userKey, realName);
        			zxErpFileMapper.insert(file);
        		});
        	}           	
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseSettlementList);
        }
    }
    
    @Override
    public ResponseEntity saveZxCtSuppliesLeaseSettlementListByOrgId(
    		ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
		DecimalFormat decimalFormat = new DecimalFormat("00");	
        zxCtSuppliesLeaseSettlementList.setZxCtSuppliesLeaseSettlementListId(UuidUtil.generate());
        if(zxCtSuppliesLeaseSettlementList.getPeriodDate() != null) {
        	zxCtSuppliesLeaseSettlementList.setPeriod(DateUtil.format(zxCtSuppliesLeaseSettlementList.getPeriodDate(), "yyyyMM"));
        }
        ZxCtSuppliesLeaseSettlementList settlement = new ZxCtSuppliesLeaseSettlementList();
		settlement.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
		List<ZxCtSuppliesLeaseSettlementList> settlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(settlement);
		if(settlementList.size()>0) {    
			if(!StrUtil.equals(settlementList.get(0).getApih5FlowStatus(), "2")) {				
				return repEntity.layerMessage("no", "???????????????????????????????????????????????????");
			}
		}else {
        	ZxCtSuppliesContract dbZxContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getContractID());
        	if(dbZxContract != null) {
        			dbZxContract.setSettleType("2");
        		dbZxContract.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbZxContract);
        	}			
		}
		settlement = new ZxCtSuppliesLeaseSettlementList();
		settlement.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
		settlement.setApih5FlowStatus("2");
		settlement.setInitSerialNumber(decimalFormat.format(Integer.parseInt(zxCtSuppliesLeaseSettlementList.getInitSerialNumber())-1));
        List<ZxCtSuppliesLeaseSettlementList> settlementListList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(settlement);
     if(settlementListList.size()>0) {
     	if(Integer.parseInt(zxCtSuppliesLeaseSettlementList.getPeriod())<Integer.parseInt(settlementListList.get(0).getPeriod())) {
     		return repEntity.layerMessage("no", "????????????????????????????????????");
     	}
     	zxCtSuppliesLeaseSettlementList.setTotalPayAmt(settlementListList.get(0).getTotalPayAmt());
     	zxCtSuppliesLeaseSettlementList.setTotalAmt(settlementListList.get(0).getTotalAmt());
     	//???????????????????????????????????????????????????
     	ZxCtSuppliesLeaseResSettlement resSettle = new ZxCtSuppliesLeaseResSettlement();
    	resSettle.setBillID(settlementListList.get(0).getZxCtSuppliesLeaseSettlementListId());
    	List<ZxCtSuppliesLeaseResSettlement> resSettleList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(resSettle);
    	resSettle.setZxCtSuppliesLeaseResSettlementId(UuidUtil.generate());
    	resSettle.setBillID(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
    	resSettle.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
    	if(resSettleList.size()>0) {
    		resSettle.setUpAmt(resSettleList.get(0).getTotalAmt());
    		resSettle.setTotalAmt(resSettleList.get(0).getTotalAmt());
    		zxCtSuppliesLeaseSettlementList.setTotalEquipAmt(resSettleList.get(0).getTotalAmt());
    	}else {
    		resSettle.setUpAmt(new BigDecimal(0));
    		resSettle.setTotalAmt(new BigDecimal(0));
    	}
    	resSettle.setCreateUserInfo(userKey, realName);
    	zxCtSuppliesLeaseResSettlementMapper.insert(resSettle);
    	ZxCtSuppliesLeasePaySettlement paySettlement = new ZxCtSuppliesLeasePaySettlement();
    	paySettlement.setBillID(settlementListList.get(0).getZxCtSuppliesLeaseSettlementListId());
    	List<ZxCtSuppliesLeasePaySettlement> paySettlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(paySettlement);
    	paySettlement.setZxCtSuppliesLeasePaySettlementId(UuidUtil.generate());
    	paySettlement.setBillID(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
    	paySettlement.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
    	if(paySettlementList.size()>0) {
        	paySettlement.setUpAmt(paySettlementList.get(0).getTotalAmt());
        	paySettlement.setTotalAmt(paySettlementList.get(0).getTotalAmt());        		
    	}else {
        	paySettlement.setUpAmt(new BigDecimal(0));
        	paySettlement.setTotalAmt(new BigDecimal(0));
    	}
    	paySettlement.setCreateUserInfo(userKey, realName);
    	zxCtSuppliesLeasePaySettlementMapper.insert(paySettlement);
     }
		ZxCtSuppliesContract contract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getContractID());
		if(contract != null) {
			zxCtSuppliesLeaseSettlementList.setContractAmt(contract.getContractCost());
			zxCtSuppliesLeaseSettlementList.setChangeAmt(contract.getAlterContractSum());
		}
        zxCtSuppliesLeaseSettlementList.setApih5FlowStatus("-1");
        zxCtSuppliesLeaseSettlementList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeaseSettlementListMapper.insert(zxCtSuppliesLeaseSettlementList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	if(zxCtSuppliesLeaseSettlementList.getSettlementFileList() != null  && zxCtSuppliesLeaseSettlementList.getSettlementFileList().size()>0) {
        		List<ZxErpFile> fileList = zxCtSuppliesLeaseSettlementList.getSettlementFileList();
        		fileList.parallelStream().forEach((file)->{
        			file.setOtherId(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        			file.setUid((UuidUtil.generate()));
        			file.setOtherType("0");
        			file.setCreateUserInfo(userKey, realName);
        			zxErpFileMapper.insert(file);
        		});
        	}      
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeaseSettlementList);
        }
    }

	@Override
	public ResponseEntity getZxCtSuppliesLeaseSettlementListFlowDetail(
			ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
		ZxCtSuppliesLeaseSettlementList settlementList = zxCtSuppliesLeaseSettlementListMapper.getDetailByWorkId(zxCtSuppliesLeaseSettlementList.getWorkId());
		if(settlementList != null) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(settlementList.getZxCtSuppliesLeaseSettlementListId());
        	file.setOtherType("1");
        	settlementList.setDocumentFileList(zxErpFileMapper.selectByZxErpFileList(file));
        	file = new ZxErpFile();
			file.setOtherType("0");
        	file.setOtherId(settlementList.getZxCtSuppliesLeaseSettlementListId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	settlementList.setSettlementFileList(fileList);        	
		}
		return repEntity.ok(settlementList);
	}

	@Override
	public ResponseEntity updateZxCtSuppliesLeaseSettlementListFlow(
			ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);		
		String userName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtSuppliesLeaseSettlementList.getApih5FlowStatus().equals("1")) {
			ZxCtSuppliesLeaseSettlementList flowDetail = zxCtSuppliesLeaseSettlementListMapper
					.getDetailByWorkId(zxCtSuppliesLeaseSettlementList.getWorkId());
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesLeaseSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesLeaseSettlementList.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesLeaseSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesLeaseSettlementList.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesLeaseSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesLeaseSettlementList.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setWorkId(zxCtSuppliesLeaseSettlementList.getWorkId());
			flowDetail.setApih5FlowStatus("1");
			flowDetail.setModifyUserInfo(userKey, userName);
			flag = zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKey(flowDetail);
			if (flag == 0) {
				return repEntity.errorSave();
			} else {
	        	if(zxCtSuppliesLeaseSettlementList.getDocumentFileList() != null && zxCtSuppliesLeaseSettlementList.getDocumentFileList().size()>0) {
	            	ZxErpFile file = new ZxErpFile();
	            	file.setOtherId(flowDetail.getZxCtSuppliesLeaseSettlementListId());
	            	file.setOtherType("1");
	            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
	            	file.setModifyUserInfo(userKey, userName);
	            	if(fileList.size()>0) {
	            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
	            	}
	         	   for(ZxErpFile zxErpFile : zxCtSuppliesLeaseSettlementList.getDocumentFileList()) {
	                    zxErpFile.setUid(UuidUtil.generate());
	                    zxErpFile.setOtherId(flowDetail.getZxCtSuppliesLeaseSettlementListId());
	                    zxErpFile.setOtherType("1");
	                    zxErpFile.setCreateUserInfo(userKey, userName);
	                    zxErpFileMapper.insert(zxErpFile);  
	         	   }        		
	        	}
				return repEntity.ok("sys.data.update", flowDetail);
			}
		} else if (zxCtSuppliesLeaseSettlementList.getApih5FlowStatus().equals("2")) {
			ZxCtSuppliesLeaseSettlementList flowDetail = zxCtSuppliesLeaseSettlementListMapper
					.getDetailByWorkId(zxCtSuppliesLeaseSettlementList.getWorkId());
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesLeaseSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesLeaseSettlementList.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesLeaseSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesLeaseSettlementList.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesLeaseSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesLeaseSettlementList.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setApih5FlowStatus("2");
			flag = zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKeySelective(flowDetail);
        	//?????????????????????????????????????????????????????????
			if(StrUtil.equals(flowDetail.getBillType(), "1")) {				
				ZxCtSuppliesContract dbZxContract = zxCtSuppliesContractMapper.selectByPrimaryKey(flowDetail.getContractID());
				if(dbZxContract != null) {
					dbZxContract.setSettleType("3");
					dbZxContract.setModifyUserInfo(userKey, userName);
					zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbZxContract);
				}				
			}
        	if(zxCtSuppliesLeaseSettlementList.getDocumentFileList() != null && zxCtSuppliesLeaseSettlementList.getDocumentFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(flowDetail.getZxCtSuppliesLeaseSettlementListId());
            	file.setOtherType("1");
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, userName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesLeaseSettlementList.getDocumentFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(flowDetail.getZxCtSuppliesLeaseSettlementListId());
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
			return repEntity.ok("sys.data.update", zxCtSuppliesLeaseSettlementList);
		}
	}

	@Override
	public ResponseEntity saveZxCtSuppliesLeaseSettlementListFlow(
			ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesLeaseSettlementList dbZxCtSuppliesContrApply = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
        if (dbZxCtSuppliesContrApply != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApply.getZxCtSuppliesLeaseSettlementListId())) {
        	dbZxCtSuppliesContrApply.setApih5FlowStatus("0");
        	//?????????:0;????????????:1;????????????:2;???????????????:3;????????????:4;???????????????:5;
        	dbZxCtSuppliesContrApply.setAuditStatus("1");
        	dbZxCtSuppliesContrApply.setWorkId(zxCtSuppliesLeaseSettlementList.getWorkId());
        	dbZxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
        	zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApply);
      	   for(ZxErpFile zxErpFile : zxCtSuppliesLeaseSettlementList.getDocumentFileList()) {
               zxErpFile.setUid(UuidUtil.generate());
               zxErpFile.setOtherType("1");
               zxErpFile.setOtherId(dbZxCtSuppliesContrApply.getZxCtSuppliesLeaseSettlementListId());
               zxErpFile.setCreateUserInfo(userKey, realName);
               zxErpFileMapper.insert(zxErpFile);  
    	   }    
        }
        return repEntity.ok("sys.data.sava", zxCtSuppliesLeaseSettlementList);
	}

	@Override
	public ResponseEntity getZxCtSuppliesLeaseCampChangeIncrease(
			ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
		zxCtSuppliesLeaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
		List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList = getCampChangeIncreaseList(zxCtSuppliesLeaseSettlementList);
		zxCtSuppliesLeaseSettlementList.setCampChangeIncreaseList(campChangeIncreaseList);
		return repEntity.ok(zxCtSuppliesLeaseSettlementList);
	}

	@Override
	public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesLeaseSettlementReportList(
			ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
		zxCtSuppliesLeaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
		List<ZxCtSuppliesLeaseCampChangeIncrease> changeIncreaseList = getCampChangeIncreaseList(zxCtSuppliesLeaseSettlementList);
		for(int i = 0; i< changeIncreaseList.size(); i++) {
			ZxCtSuppliesLeaseCampChangeIncrease changeIncrease = changeIncreaseList.get(i);
			changeIncrease.setFirstNum(i+1+"");
			changeIncrease.setOrgName(zxCtSuppliesLeaseSettlementList.getOrgName());
			changeIncrease.setBillNo(zxCtSuppliesLeaseSettlementList.getBillNo());
			changeIncrease.setContractNo(zxCtSuppliesLeaseSettlementList.getContractNo());
			changeIncrease.setPeriod(zxCtSuppliesLeaseSettlementList.getPeriod());
			changeIncrease.setSignedNo(zxCtSuppliesLeaseSettlementList.getSignedNo());
		}
		return changeIncreaseList;
	}
	
	public List<ZxCtSuppliesLeaseCampChangeIncrease> getCampChangeIncreaseList(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList) {
		ZxCtSuppliesLeaseCampChangeIncrease campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
		List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList = zxCtSuppliesLeaseSettlementListMapper.selectZxCtSuppliesLeaseCampChangeIncreaseList(zxCtSuppliesLeaseSettlementList);
		//?????????????????????????????????
		if(campChangeIncreaseList.size()>0) {
			campChangeIncreaseList.parallelStream().forEach((increase)->{
				if(StrUtil.isEmpty(increase.getChangePrice())) {
					increase.setChangePrice(increase.getPrice());
				}
				if(StrUtil.isEmpty(increase.getThisAmt())) {
					increase.setThisAmt("0");
				}
				if(StrUtil.isEmpty(increase.getThisAmtNoTax())) {
					increase.setThisAmtNoTax("0");
				}
				if(StrUtil.isEmpty(increase.getThisAmtTax())) {
					increase.setThisAmtTax("0");
				}
			});
			campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
			campChangeIncrease.setWorkNo("A");
			campChangeIncrease.setWorkName("??????");
			campChangeIncrease.setUnit("???");
			campChangeIncrease.setContractTax("0");
			campChangeIncrease.setContractSum(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getContractSum()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setContractSumNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getContractSumNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setContractTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getContractTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setChangeContractSum(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getChangeContractSum()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setChangeContractSumNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getChangeContractSumNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setChangeContractTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getChangeContractTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setThisAmt(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getThisAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setThisAmtNoTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setThisAmtTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmt(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getUpAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmtNoTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getUpAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmtTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getUpAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmt(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getTotalAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmtNoTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getTotalAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmtTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getTotalAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTaxRate("-");
			campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
			campChangeIncreaseList.add(campChangeIncrease);
			if(StrUtil.isNotEmpty(zxCtSuppliesLeaseSettlementList.getFlag())) {
				ZxCtSuppliesLeasePaySettlement settlement = new ZxCtSuppliesLeasePaySettlement();
				ZxCtSuppliesLeaseSettlementItem settlementItem = new ZxCtSuppliesLeaseSettlementItem();
				settlement.setBillID(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
				List<ZxCtSuppliesLeasePaySettlement> settlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(settlement);
				if(settlementList.size()>0) {
					//?????????????????????????????????????????????????????????????????????
						ZxCtSuppliesLeasePaySettlementItem paySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
						paySettlementItem.setMainID(settlementList.get(0).getZxCtSuppliesLeasePaySettlementId());
						List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(paySettlementItem);
						List<ZxCtSuppliesLeasePaySettlementItem> transportList = new ArrayList<ZxCtSuppliesLeasePaySettlementItem>(); 
						List<ZxCtSuppliesLeasePaySettlementItem> fineList = new ArrayList<ZxCtSuppliesLeasePaySettlementItem>(); 
						List<ZxCtSuppliesLeasePaySettlementItem> otherList = new ArrayList<ZxCtSuppliesLeasePaySettlementItem>(); 
						List<ZxCtSuppliesLeasePaySettlementItem> foodList = new ArrayList<ZxCtSuppliesLeasePaySettlementItem>(); 
						for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
							switch (item.getPayType()) {
							case "?????????":
								transportList.add(item);
								break;
							case "?????????":
								fineList.add(item);
								break;
							case "?????????":
								foodList.add(item);
								break;
							case "????????????":
								otherList.add(item);
								break;						
							}
						}
						campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
						campChangeIncrease.setWorkNo("B");
						campChangeIncrease.setWorkName("?????????");
						campChangeIncrease.setUnit("???");
						campChangeIncrease.setThisAmt(settlementList.get(0).getInOutAmt()+"");
						campChangeIncrease.setThisAmtNoTax("0");
						campChangeIncrease.setThisAmtTax("0");
						campChangeIncrease.setUpAmt(settlementList.get(0).getUpInOutAmt()+"");
						campChangeIncrease.setUpAmtNoTax("0");
						campChangeIncrease.setUpAmtTax("0");
						campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(settlementList.get(0).getInOutAmt(), settlementList.get(0).getUpInOutAmt())+"");
						campChangeIncrease.setTotalAmtNoTax("0");
						campChangeIncrease.setTotalAmtTax("0");
						campChangeIncrease.setTaxRate("-");
						campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
						campChangeIncreaseList.add(campChangeIncrease);
						for(ZxCtSuppliesLeasePaySettlementItem food : foodList) {
							campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
							campChangeIncrease.setWorkNo(food.getPayNo());
							campChangeIncrease.setWorkName(food.getPayName());
							campChangeIncrease.setUnit(food.getUnit());
							campChangeIncrease.setChangePrice(food.getPrice()+"");
							campChangeIncrease.setThisQty(food.getQty()+"");
							campChangeIncrease.setThisAmt(food.getThisAmt()+"");
							campChangeIncrease.setThisAmtNoTax(food.getThisAmtNoTax()+"");
							campChangeIncrease.setThisAmtTax(food.getThisAmtTax()+"");
							campChangeIncrease.setUpQty(food.getUpQty()+"");
							campChangeIncrease.setUpAmt(food.getUpAmt()+"");
							campChangeIncrease.setTotalQty(CalcUtils.calcAdd(food.getQty(), food.getUpQty())+"");
							campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(food.getThisAmt(), food.getUpAmt())+"");
							campChangeIncrease.setTaxRate("-");
							campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
							campChangeIncreaseList.add(campChangeIncrease);					
						}
						campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
						campChangeIncrease.setWorkNo("C");
						campChangeIncrease.setWorkName("?????????");
						campChangeIncrease.setUnit("???");
						campChangeIncrease.setThisAmt(settlementList.get(0).getFoodAmt()+"");
						campChangeIncrease.setThisAmtNoTax("0");
						campChangeIncrease.setThisAmtTax("0");
						campChangeIncrease.setUpAmt(settlementList.get(0).getUpFoodAmt()+"");
						campChangeIncrease.setUpAmtNoTax("0");
						campChangeIncrease.setUpAmtTax("0");
						campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(settlementList.get(0).getFoodAmt(), settlementList.get(0).getUpFoodAmt())+"");
						campChangeIncrease.setTotalAmtNoTax("0");
						campChangeIncrease.setTotalAmtTax("0");
						campChangeIncrease.setTaxRate("-");
						campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
						campChangeIncreaseList.add(campChangeIncrease);
						for(ZxCtSuppliesLeasePaySettlementItem transport : transportList) {
							campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
							campChangeIncrease.setWorkNo(transport.getPayNo());
							campChangeIncrease.setWorkName(transport.getPayName());
							campChangeIncrease.setUnit(transport.getUnit());
							campChangeIncrease.setChangePrice(transport.getPrice()+"");
							campChangeIncrease.setThisQty(transport.getQty()+"");
							campChangeIncrease.setThisAmt(transport.getThisAmt()+"");
							campChangeIncrease.setThisAmtNoTax(transport.getThisAmtNoTax()+"");
							campChangeIncrease.setThisAmtTax(transport.getThisAmtTax()+"");
							campChangeIncrease.setUpQty(transport.getUpQty()+"");
							campChangeIncrease.setUpAmt(transport.getUpAmt()+"");
							campChangeIncrease.setTotalQty(CalcUtils.calcAdd(transport.getQty(), transport.getUpQty())+"");
							campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(transport.getThisAmt(), transport.getUpAmt())+"");
							campChangeIncrease.setTaxRate("-");
							campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
							campChangeIncreaseList.add(campChangeIncrease);					
						}
						campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
						campChangeIncrease.setWorkNo("D");
						campChangeIncrease.setWorkName("?????????");
						campChangeIncrease.setUnit("???");
						campChangeIncrease.setThisAmt(settlementList.get(0).getFineAmt()+"");
						campChangeIncrease.setThisAmtNoTax("0");
						campChangeIncrease.setThisAmtTax("0");
						campChangeIncrease.setUpAmt(settlementList.get(0).getUpFineAmt()+"");
						campChangeIncrease.setUpAmtNoTax("0");
						campChangeIncrease.setUpAmtTax("0");
						campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(settlementList.get(0).getFineAmt(), settlementList.get(0).getUpFineAmt())+"");
						campChangeIncrease.setTotalAmtNoTax("0");
						campChangeIncrease.setTotalAmtTax("0");
						campChangeIncrease.setTaxRate("-");
						campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
						campChangeIncreaseList.add(campChangeIncrease);
						for(ZxCtSuppliesLeasePaySettlementItem fine : fineList) {
							campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
							campChangeIncrease.setWorkNo(fine.getPayNo());
							campChangeIncrease.setWorkName(fine.getPayName());
							campChangeIncrease.setUnit(fine.getUnit());
							campChangeIncrease.setChangePrice(fine.getPrice()+"");
							campChangeIncrease.setThisQty(fine.getQty()+"");
							campChangeIncrease.setThisAmt(fine.getThisAmt()+"");
							campChangeIncrease.setThisAmtNoTax(fine.getThisAmtNoTax()+"");
							campChangeIncrease.setThisAmtTax(fine.getThisAmtTax()+"");
							campChangeIncrease.setUpQty(fine.getUpQty()+"");
							campChangeIncrease.setUpAmt(fine.getUpAmt()+"");
							campChangeIncrease.setTotalQty(CalcUtils.calcAdd(fine.getQty(), fine.getUpQty())+"");
							campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(fine.getThisAmt(), fine.getUpAmt())+"");
							campChangeIncrease.setTaxRate("-");
							campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
							campChangeIncreaseList.add(campChangeIncrease);					
						}
						campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
						campChangeIncrease.setWorkNo("E");
						campChangeIncrease.setWorkName("????????????");
						campChangeIncrease.setUnit("???");
						campChangeIncrease.setThisAmt(settlementList.get(0).getOtherAmt()+"");
						campChangeIncrease.setThisAmtNoTax("0");
						campChangeIncrease.setThisAmtTax("0");
						campChangeIncrease.setUpAmt(settlementList.get(0).getUpOtherAmt()+"");
						campChangeIncrease.setUpAmtNoTax("0");
						campChangeIncrease.setUpAmtTax("0");
						campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(settlementList.get(0).getOtherAmt(), settlementList.get(0).getUpOtherAmt())+"");
						campChangeIncrease.setTotalAmtNoTax("0");
						campChangeIncrease.setTotalAmtTax("0");
						campChangeIncrease.setTaxRate("-");
						campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
						campChangeIncreaseList.add(campChangeIncrease);
						for(ZxCtSuppliesLeasePaySettlementItem other : otherList) {
							campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
							campChangeIncrease.setWorkNo(other.getPayNo());
							campChangeIncrease.setWorkName(other.getPayName());
							campChangeIncrease.setUnit(other.getUnit());
							campChangeIncrease.setChangePrice(other.getPrice()+"");
							campChangeIncrease.setThisQty(other.getQty()+"");
							campChangeIncrease.setThisAmt(other.getThisAmt()+"");
							campChangeIncrease.setThisAmtNoTax(other.getThisAmtNoTax()+"");
							campChangeIncrease.setThisAmtTax(other.getThisAmtTax()+"");
							campChangeIncrease.setUpQty(other.getUpQty()+"");
							campChangeIncrease.setUpAmt(other.getUpAmt()+"");
							campChangeIncrease.setTotalQty(CalcUtils.calcAdd(other.getQty(), other.getUpQty())+"");
							campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(other.getThisAmt(), other.getUpAmt())+"");
							campChangeIncrease.setTaxRate("-");
							campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
							campChangeIncreaseList.add(campChangeIncrease);					
						}	
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setWorkNo("F");
					campChangeIncrease.setWorkName("??????");
					ZxCtSuppliesLeaseSettlementList shopSettlement = new ZxCtSuppliesLeaseSettlementList();
			        int num = Integer.parseInt(zxCtSuppliesLeaseSettlementList.getInitSerialNumber());
			        shopSettlement.setInitSerialNumber(num-1+"");
					shopSettlement.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
					List<ZxCtSuppliesLeaseSettlementList> shopSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(shopSettlement);
					if(shopSettlementList.size()>0) {
						settlementItem = new ZxCtSuppliesLeaseSettlementItem();
						settlementItem.setMainID(shopSettlementList.get(0).getZxCtSuppliesLeaseSettlementListId());
						List<ZxCtSuppliesLeaseSettlementItem> settlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(settlementItem);
						for(ZxCtSuppliesLeaseSettlementItem item :settlementItemList) {
							if(StrUtil.equals(item.getStatisticsNo(), "100100")) {
								campChangeIncrease.setUpAmt(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100110")) {
								campChangeIncrease.setUpAmtNoTax(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100120")) {
								campChangeIncrease.setUpAmtTax(item.getTotalAmt());
							}
						}
					}
					settlementItem = new ZxCtSuppliesLeaseSettlementItem();
					settlementItem.setMainID(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
					List<ZxCtSuppliesLeaseSettlementItem> settlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(settlementItem);
					for(ZxCtSuppliesLeaseSettlementItem item :settlementItemList) {
						if(StrUtil.equals(item.getStatisticsNo(), "100100")) {
							campChangeIncrease.setThisAmt(item.getThisAmt());
							campChangeIncrease.setTotalAmt(item.getTotalAmt());
						}else if(StrUtil.equals(item.getStatisticsNo(), "100110")) {
							campChangeIncrease.setThisAmtNoTax(item.getThisAmt());
							campChangeIncrease.setTotalAmtNoTax(item.getTotalAmt());
						}else if(StrUtil.equals(item.getStatisticsNo(), "100120")) {
							campChangeIncrease.setThisAmtTax(item.getThisAmt());
							campChangeIncrease.setTotalAmtTax(item.getTotalAmt());
						}
					}
					campChangeIncreaseList.add(campChangeIncrease);					
				}else {
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setWorkNo("B");
					campChangeIncrease.setWorkName("?????????");
					campChangeIncrease.setUnit("???");
					campChangeIncrease.setThisAmt("0");
					campChangeIncrease.setThisAmtNoTax("0");
					campChangeIncrease.setThisAmtTax("0");
					campChangeIncrease.setUpAmt("0");
					campChangeIncrease.setUpAmtNoTax("0");
					campChangeIncrease.setUpAmtTax("0");
					campChangeIncrease.setTotalAmt("0");
					campChangeIncrease.setTotalAmtNoTax("0");
					campChangeIncrease.setTotalAmtTax("0");
					campChangeIncrease.setTaxRate("-");
					campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setUnit("???");
					campChangeIncrease.setThisAmt("0");
					campChangeIncrease.setThisAmtNoTax("0");
					campChangeIncrease.setThisAmtTax("0");
					campChangeIncrease.setUpAmt("0");
					campChangeIncrease.setUpAmtNoTax("0");
					campChangeIncrease.setUpAmtTax("0");
					campChangeIncrease.setTotalAmt("0");
					campChangeIncrease.setTotalAmtNoTax("0");
					campChangeIncrease.setTotalAmtTax("0");
					campChangeIncrease.setTaxRate("-");
					campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("C");
					campChangeIncrease.setWorkName("?????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setUnit("???");
					campChangeIncrease.setThisAmt("0");
					campChangeIncrease.setThisAmtNoTax("0");
					campChangeIncrease.setThisAmtTax("0");
					campChangeIncrease.setUpAmt("0");
					campChangeIncrease.setUpAmtNoTax("0");
					campChangeIncrease.setUpAmtTax("0");
					campChangeIncrease.setTotalAmt("0");
					campChangeIncrease.setTotalAmtNoTax("0");
					campChangeIncrease.setTotalAmtTax("0");
					campChangeIncrease.setTaxRate("-");
					campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("D");
					campChangeIncrease.setWorkName("?????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setUnit("???");
					campChangeIncrease.setThisAmt("0");
					campChangeIncrease.setThisAmtNoTax("0");
					campChangeIncrease.setThisAmtTax("0");
					campChangeIncrease.setUpAmt("0");
					campChangeIncrease.setUpAmtNoTax("0");
					campChangeIncrease.setUpAmtTax("0");
					campChangeIncrease.setTotalAmt("0");
					campChangeIncrease.setTotalAmtNoTax("0");
					campChangeIncrease.setTotalAmtTax("0");
					campChangeIncrease.setTaxRate("-");
					campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("E");
					campChangeIncrease.setWorkName("????????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setWorkNo("F");
					campChangeIncrease.setWorkName("??????");
					ZxCtSuppliesLeaseSettlementList shopSettlement = new ZxCtSuppliesLeaseSettlementList();
			        int num = Integer.parseInt(zxCtSuppliesLeaseSettlementList.getInitSerialNumber());
			        shopSettlement.setInitSerialNumber(num-1+"");
					shopSettlement.setContractID(zxCtSuppliesLeaseSettlementList.getContractID());
					List<ZxCtSuppliesLeaseSettlementList> shopSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(shopSettlement);
					if(shopSettlementList.size()>0) {
						settlementItem = new ZxCtSuppliesLeaseSettlementItem();
						settlementItem.setMainID(shopSettlementList.get(0).getZxCtSuppliesLeaseSettlementListId());
						List<ZxCtSuppliesLeaseSettlementItem> settlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(settlementItem);
						for(ZxCtSuppliesLeaseSettlementItem item :settlementItemList) {
							if(StrUtil.equals(item.getStatisticsNo(), "100100")) {
								campChangeIncrease.setUpAmt(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100110")) {
								campChangeIncrease.setUpAmtNoTax(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100120")) {
								campChangeIncrease.setUpAmtTax(item.getTotalAmt());
							}
						}
					}
					settlementItem = new ZxCtSuppliesLeaseSettlementItem();
					settlementItem.setMainID(zxCtSuppliesLeaseSettlementList.getZxCtSuppliesLeaseSettlementListId());
					List<ZxCtSuppliesLeaseSettlementItem> settlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(settlementItem);
					for(ZxCtSuppliesLeaseSettlementItem item :settlementItemList) {
						if(StrUtil.equals(item.getStatisticsNo(), "100100")) {
							campChangeIncrease.setThisAmt(item.getThisAmt());
							campChangeIncrease.setTotalAmt(item.getTotalAmt());
						}else if(StrUtil.equals(item.getStatisticsNo(), "100110")) {
							campChangeIncrease.setThisAmtNoTax(item.getThisAmt());
							campChangeIncrease.setTotalAmtNoTax(item.getTotalAmt());
						}else if(StrUtil.equals(item.getStatisticsNo(), "100120")) {
							campChangeIncrease.setThisAmtTax(item.getThisAmt());
							campChangeIncrease.setTotalAmtTax(item.getTotalAmt());
						}
					}
					campChangeIncreaseList.add(campChangeIncrease);				
			}
			}else {
				campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
				campChangeIncrease.setWorkNo("B");
				campChangeIncrease.setWorkName("??????");
				campChangeIncrease.setUnit("???");
				campChangeIncrease.setContractTax("0");
				campChangeIncrease.setContractSum(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getContractSum()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setContractSumNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getContractSumNoTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setContractTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getContractTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setChangeContractSum(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getChangeContractSum()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setChangeContractSumNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getChangeContractSumNoTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setChangeContractTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getChangeContractTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setThisAmt(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisAmt()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setThisAmtNoTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setThisAmtTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setUpAmt(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getUpAmt()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setUpAmtNoTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getUpAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setUpAmtTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getUpAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setTotalAmt(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getTotalAmt()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setTotalAmtNoTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getTotalAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setTotalAmtTax(campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getTotalAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
				campChangeIncrease.setTaxRate("-");
				campChangeIncrease.setIsDeduct(zxCtSuppliesLeaseSettlementList.getIsDeduct());
				campChangeIncreaseList.add(campChangeIncrease);
			}
			zxCtSuppliesLeaseSettlementList.setCampChangeIncreaseList(campChangeIncreaseList);
		}else {
			campChangeIncreaseList = new ArrayList<ZxCtSuppliesLeaseCampChangeIncrease>();
			zxCtSuppliesLeaseSettlementList.setCampChangeIncreaseList(campChangeIncreaseList);
		}
		return campChangeIncreaseList;
	}
}
