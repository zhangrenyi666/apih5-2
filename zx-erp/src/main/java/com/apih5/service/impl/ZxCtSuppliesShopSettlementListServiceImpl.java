package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.apih5.mybatis.dao.ZxCtSuppliesContrShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementListMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.service.ZxCtSuppliesShopSettlementListService;
import com.apih5.service.ZxSkStockTransferReceivingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxCtSuppliesShopSettlementListService")
public class ZxCtSuppliesShopSettlementListServiceImpl implements ZxCtSuppliesShopSettlementListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListMapper zxCtSuppliesShopSettlementListMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementMapper zxCtSuppliesShopPaySettlementMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleMapper zxCtSuppliesShopResSettleMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementItemMapper zxCtSuppliesShopSettlementItemMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementItemMapper zxCtSuppliesShopPaySettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleItemMapper zxCtSuppliesShopResSettleItemMapper;
    
    @Autowired(required = true)
    private ZxSkStockTransferReceivingService zxSkStockTransferReceivingService;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrShopListMapper zxCtSuppliesContrShopListMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementListListByCondition(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        if (zxCtSuppliesShopSettlementList == null) {
            zxCtSuppliesShopSettlementList = new ZxCtSuppliesShopSettlementList();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();        
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesShopSettlementList.setCompanyId("");
        	zxCtSuppliesShopSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesShopSettlementList.setCompanyId(zxCtSuppliesShopSettlementList.getOrgID());
        	zxCtSuppliesShopSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesShopSettlementList.setOrgID(zxCtSuppliesShopSettlementList.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesShopSettlementList.getPage(),zxCtSuppliesShopSettlementList.getLimit());
        // ????????????
        List<ZxCtSuppliesShopSettlementList> zxCtSuppliesShopSettlementListList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(zxCtSuppliesShopSettlementList);
        // ??????????????????
        PageInfo<ZxCtSuppliesShopSettlementList> p = new PageInfo<>(zxCtSuppliesShopSettlementListList);

        return repEntity.okList(zxCtSuppliesShopSettlementListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementListDetail(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        if (zxCtSuppliesShopSettlementList == null) {
            zxCtSuppliesShopSettlementList = new ZxCtSuppliesShopSettlementList();
        }
        ZxCtSuppliesShopSettlementList dbZxCtSuppliesShopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        // ????????????
        if (dbZxCtSuppliesShopSettlementList != null) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        	file.setOtherType("1");
        	dbZxCtSuppliesShopSettlementList.setDocumentFileList(zxErpFileMapper.selectByZxErpFileList(file));
        	file = new ZxErpFile();
        	file.setOtherId(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
			file.setOtherType("0");
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	dbZxCtSuppliesShopSettlementList.setSettlementFileList(fileList);
        	ZxCtSuppliesShopPaySettlement settlement = new ZxCtSuppliesShopPaySettlement();
            settlement.setBillID(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
            List<ZxCtSuppliesShopPaySettlement> settlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(settlement);
            if(settlementList.size()>0) {        	
            	dbZxCtSuppliesShopSettlementList.setFineAmt(settlementList.get(0).getFineAmt());//??????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setTransportAmt(settlementList.get(0).getTransportAmt());//??????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setPadTariffAmt(settlementList.get(0).getPadTariffAmt());//??????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setOtherAmt(settlementList.get(0).getOtherAmt());//?????????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setThisAmtByPay(settlementList.get(0).getThisAmt()); //????????????????????????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setThisAmtNoTaxByPay(settlementList.get(0).getThisAmtNoTax());//???????????????????????????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setThisAmtTaxByPay(settlementList.get(0).getThisAmtTax());//??????????????????????????????????????????
            	dbZxCtSuppliesShopSettlementList.setTotalAmtByPay(settlementList.get(0).getTotalAmt());
//            	dbZxCtSuppliesShopSettlementList.setPayTotalAmt(settlementList.get(0).getTotalAmt());
//            	dbZxCtSuppliesShopSettlementList.setPayUpAmt(settlementList.get(0).getUpAmt());
            }     
            ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
            resSettle.setBillID(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
            List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
            if(resSettleList.size()>0) {
            	dbZxCtSuppliesShopSettlementList.setStockBillNos(resSettleList.get(0).getSignedNos());
            	dbZxCtSuppliesShopSettlementList.setResThisAmt(resSettleList.get(0).getThisAmt());
            	dbZxCtSuppliesShopSettlementList.setResThisAmtNoTax(resSettleList.get(0).getThisAmtNoTax());
            	dbZxCtSuppliesShopSettlementList.setResThisAmtTax(resSettleList.get(0).getThisAmtTax());
            	dbZxCtSuppliesShopSettlementList.setResTotalAmt(resSettleList.get(0).getTotalAmt());
            }
            ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem = new ZxCtSuppliesShopResSettleItem();
            ZxCtSuppliesShopResSettle settle = new ZxCtSuppliesShopResSettle();
            settle.setBillID(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
            List<ZxCtSuppliesShopResSettle> settleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(settle);
            if(settleList.size()>0) {
            	zxCtSuppliesShopResSettleItem.setMainID(settleList.get(0).getZxCtSuppliesShopResSettleId());
                List<ZxCtSuppliesShopResSettleItem> zxCtSuppliesShopResSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(zxCtSuppliesShopResSettleItem);
                dbZxCtSuppliesShopSettlementList.setShopResSettleItemList(zxCtSuppliesShopResSettleItemList);
            }else {
            	dbZxCtSuppliesShopSettlementList.setShopResSettleItemList(new ArrayList<ZxCtSuppliesShopResSettleItem>());
            }
            return repEntity.ok(dbZxCtSuppliesShopSettlementList);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopSettlementList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopSettlementList.setZxCtSuppliesShopSettlementId(UuidUtil.generate());
        zxCtSuppliesShopSettlementList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopSettlementListMapper.insert(zxCtSuppliesShopSettlementList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopSettlementList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopSettlementList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopSettlementList dbZxCtSuppliesShopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        if (dbZxCtSuppliesShopSettlementList != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId())) {
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setEditTime(zxCtSuppliesShopSettlementList.getEditTime());
           // ??????
           dbZxCtSuppliesShopSettlementList.setAuditStatus(zxCtSuppliesShopSettlementList.getAuditStatus());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setNotPassNum(zxCtSuppliesShopSettlementList.getNotPassNum());
           // ???????????????????????????
           dbZxCtSuppliesShopSettlementList.setIsAudit(zxCtSuppliesShopSettlementList.getIsAudit());
           // ??????ID
           dbZxCtSuppliesShopSettlementList.setSecondID(zxCtSuppliesShopSettlementList.getSecondID());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setBusinessDate(zxCtSuppliesShopSettlementList.getBusinessDate());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setOrgName(zxCtSuppliesShopSettlementList.getOrgName());
           // ??????ID
           dbZxCtSuppliesShopSettlementList.setOrgID(zxCtSuppliesShopSettlementList.getOrgID());
           // ??????
           dbZxCtSuppliesShopSettlementList.setZjgcxmXmmc(zxCtSuppliesShopSettlementList.getZjgcxmXmmc());
           // ???????????????ID
           dbZxCtSuppliesShopSettlementList.setBillID(zxCtSuppliesShopSettlementList.getBillID());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setReportDate(zxCtSuppliesShopSettlementList.getReportDate());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setReportPerson(zxCtSuppliesShopSettlementList.getReportPerson());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setComOrders(zxCtSuppliesShopSettlementList.getComOrders());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setComName(zxCtSuppliesShopSettlementList.getComName());
           // ????????????ID
           dbZxCtSuppliesShopSettlementList.setComID(zxCtSuppliesShopSettlementList.getComID());
           // ??????(%)
           dbZxCtSuppliesShopSettlementList.setTaxRate(zxCtSuppliesShopSettlementList.getTaxRate());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setIsFirst(zxCtSuppliesShopSettlementList.getIsFirst());
           // ???????????????
           dbZxCtSuppliesShopSettlementList.setIsReport(zxCtSuppliesShopSettlementList.getIsReport());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setFlowBeginDate(zxCtSuppliesShopSettlementList.getFlowBeginDate());
           // ????????????ID
           dbZxCtSuppliesShopSettlementList.setInstProcessID(zxCtSuppliesShopSettlementList.getInstProcessID());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setFlowEndDate(zxCtSuppliesShopSettlementList.getFlowEndDate());
           // ??????ID
           dbZxCtSuppliesShopSettlementList.setWorkItemID(zxCtSuppliesShopSettlementList.getWorkItemID());
           // ?????????ID
           dbZxCtSuppliesShopSettlementList.setOldWorkItemID(zxCtSuppliesShopSettlementList.getOldWorkItemID());
           // ??????????????????(???)
           dbZxCtSuppliesShopSettlementList.setTotalPayAmt(zxCtSuppliesShopSettlementList.getTotalPayAmt());
           // ??????????????????(???)
           dbZxCtSuppliesShopSettlementList.setTotalAmt(zxCtSuppliesShopSettlementList.getTotalAmt());
           // ????????????????????????
           dbZxCtSuppliesShopSettlementList.setBeginDate(zxCtSuppliesShopSettlementList.getBeginDate());
           // ????????????????????????
           dbZxCtSuppliesShopSettlementList.setEndDate(zxCtSuppliesShopSettlementList.getEndDate());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setPeriod(zxCtSuppliesShopSettlementList.getPeriod());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setContent(zxCtSuppliesShopSettlementList.getContent());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setBillType(zxCtSuppliesShopSettlementList.getBillType());
           // ???????????????
           dbZxCtSuppliesShopSettlementList.setBillNo(zxCtSuppliesShopSettlementList.getBillNo());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setCountPerson(zxCtSuppliesShopSettlementList.getCountPerson());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setTaxCountWay(zxCtSuppliesShopSettlementList.getTaxCountWay());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setSecondName(zxCtSuppliesShopSettlementList.getSecondName());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setContractName(zxCtSuppliesShopSettlementList.getContractName());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setContractNo(zxCtSuppliesShopSettlementList.getContractNo());
           // ??????ID
           dbZxCtSuppliesShopSettlementList.setContractID(zxCtSuppliesShopSettlementList.getContractID());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setReCountPerson(zxCtSuppliesShopSettlementList.getReCountPerson());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setFlowBeginPerson(zxCtSuppliesShopSettlementList.getFlowBeginPerson());
           // ???????????????????????????
           dbZxCtSuppliesShopSettlementList.setTchljjsje(zxCtSuppliesShopSettlementList.getTchljjsje());
           // ????????????????????????
           dbZxCtSuppliesShopSettlementList.setCwStatusRemark(zxCtSuppliesShopSettlementList.getCwStatusRemark());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setCwStatus(zxCtSuppliesShopSettlementList.getCwStatus());
           // ??????????????????(???)
           dbZxCtSuppliesShopSettlementList.setThisPayAmt(zxCtSuppliesShopSettlementList.getThisPayAmt());
           // ??????????????????(???)
           dbZxCtSuppliesShopSettlementList.setThisAmt(zxCtSuppliesShopSettlementList.getThisAmt());
           // ???????????????????????????
           dbZxCtSuppliesShopSettlementList.setBqtchjsje(zxCtSuppliesShopSettlementList.getBqtchjsje());
           // zjgcxm_xmbh
           dbZxCtSuppliesShopSettlementList.setZjgcxmXmbh(zxCtSuppliesShopSettlementList.getZjgcxmXmbh());
           // zjgcxm_nm
           dbZxCtSuppliesShopSettlementList.setZjgcxmNm(zxCtSuppliesShopSettlementList.getZjgcxmNm());
           // upWorkItemID
           dbZxCtSuppliesShopSettlementList.setUpWorkItemID(zxCtSuppliesShopSettlementList.getUpWorkItemID());
           // oaOrgID
           dbZxCtSuppliesShopSettlementList.setOaOrgID(zxCtSuppliesShopSettlementList.getOaOrgID());
           // notDisplay
           dbZxCtSuppliesShopSettlementList.setNotDisplay(zxCtSuppliesShopSettlementList.getNotDisplay());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setSaveNumbers(zxCtSuppliesShopSettlementList.getSaveNumbers());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setAppraisal(zxCtSuppliesShopSettlementList.getAppraisal());
           // ???????????????
           dbZxCtSuppliesShopSettlementList.setUseCount(zxCtSuppliesShopSettlementList.getUseCount());
           // ??????0??????????????????
           dbZxCtSuppliesShopSettlementList.setPp1(zxCtSuppliesShopSettlementList.getPp1());
           // ???????????????
           dbZxCtSuppliesShopSettlementList.setSignedNo(zxCtSuppliesShopSettlementList.getSignedNo());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setSerialNumber(zxCtSuppliesShopSettlementList.getSerialNumber());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setSaType(zxCtSuppliesShopSettlementList.getSaType());
           // ???????????????????????????
           dbZxCtSuppliesShopSettlementList.setInitSerialNumber(zxCtSuppliesShopSettlementList.getInitSerialNumber());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setStartDate(zxCtSuppliesShopSettlementList.getStartDate());
           // ????????????ID
           dbZxCtSuppliesShopSettlementList.setStockBillIDs(zxCtSuppliesShopSettlementList.getStockBillIDs());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setStockBillNos(zxCtSuppliesShopSettlementList.getStockBillNos());
           // ?????????????????????
           dbZxCtSuppliesShopSettlementList.setUpAmt(zxCtSuppliesShopSettlementList.getUpAmt());
           // ???????????????
           dbZxCtSuppliesShopSettlementList.setSignedNos(zxCtSuppliesShopSettlementList.getSignedNos());
           // ?????????ID
           dbZxCtSuppliesShopSettlementList.setSignedOrders(zxCtSuppliesShopSettlementList.getSignedOrders());
           // ??????????????????(??????)
           dbZxCtSuppliesShopSettlementList.setContractAmt(zxCtSuppliesShopSettlementList.getContractAmt());
           // ????????????????????????????????????(???)
           dbZxCtSuppliesShopSettlementList.setThisAmtTax(zxCtSuppliesShopSettlementList.getThisAmtTax());
           // ???????????????????????????????????????(???)
           dbZxCtSuppliesShopSettlementList.setThisAmtNoTax(zxCtSuppliesShopSettlementList.getThisAmtNoTax());
           // ??????
           dbZxCtSuppliesShopSettlementList.setRemarks(zxCtSuppliesShopSettlementList.getRemarks());
           // ??????
           dbZxCtSuppliesShopSettlementList.setSort(zxCtSuppliesShopSettlementList.getSort());
           // ??????
           dbZxCtSuppliesShopSettlementList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(dbZxCtSuppliesShopSettlementList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopSettlementList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopSettlementList(List<ZxCtSuppliesShopSettlementList> zxCtSuppliesShopSettlementListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtSuppliesShopSettlementListList != null && zxCtSuppliesShopSettlementListList.size() > 0) {
            for(ZxCtSuppliesShopSettlementList apply : zxCtSuppliesShopSettlementListList){
            	if(StrUtil.isNotEmpty(apply.getWorkId())) {
            		jsonArr.add(apply.getWorkId());
            	}
            //?????????????????????????????????
            	ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
            	resSettle.setBillID(apply.getZxCtSuppliesShopSettlementId());
            	List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
            	if(resSettleList.size()>0) {
            		resSettle.setModifyUserInfo(userKey, realName);
            		zxCtSuppliesShopResSettleMapper.batchDeleteUpdateZxCtSuppliesShopResSettle(resSettleList, resSettle);
            	}
            //
            	ZxCtSuppliesShopPaySettlement paySettlement = new ZxCtSuppliesShopPaySettlement();
            	paySettlement.setBillID(apply.getZxCtSuppliesShopSettlementId());
            	List<ZxCtSuppliesShopPaySettlement> paySettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(paySettlement);
            	if(paySettlementList.size()>0) {
            		paySettlement.setModifyUserInfo(userKey, realName);
            		zxCtSuppliesShopPaySettlementMapper.batchDeleteUpdateZxCtSuppliesShopPaySettlement(paySettlementList, paySettlement);
            	}
           //
            	ZxCtSuppliesShopSettlementItem item = new ZxCtSuppliesShopSettlementItem();
            	item.setMainID(apply.getZxCtSuppliesShopSettlementId());
            	List<ZxCtSuppliesShopSettlementItem> itemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(item);
            	if(itemList.size()>0) {
            		item.setModifyUserInfo(userKey, realName);	
            		zxCtSuppliesShopSettlementItemMapper.batchDeleteUpdateZxCtSuppliesShopSettlementItem(itemList, item);
            	}
            }
           ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList = new ZxCtSuppliesShopSettlementList();
           zxCtSuppliesShopSettlementList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementListMapper.batchDeleteUpdateZxCtSuppliesShopSettlementList(zxCtSuppliesShopSettlementListList, zxCtSuppliesShopSettlementList);
           for(ZxCtSuppliesShopSettlementList settlement : zxCtSuppliesShopSettlementListList) {
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
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopSettlementListList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementListListByOrgId(
    		ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        if (zxCtSuppliesShopSettlementList == null) {
            zxCtSuppliesShopSettlementList = new ZxCtSuppliesShopSettlementList();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();        
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesShopSettlementList.setComID("");
        	zxCtSuppliesShopSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesShopSettlementList.setComID(zxCtSuppliesShopSettlementList.getOrgID());
        	zxCtSuppliesShopSettlementList.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesShopSettlementList.setOrgID(zxCtSuppliesShopSettlementList.getOrgID());
        }        
        // ????????????
        PageHelper.startPage(zxCtSuppliesShopSettlementList.getPage(),zxCtSuppliesShopSettlementList.getLimit());
        // ????????????
        List<ZxCtSuppliesShopSettlementList> zxCtSuppliesShopSettlementListList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(zxCtSuppliesShopSettlementList);
        zxCtSuppliesShopSettlementListList.parallelStream().forEach((shop)->{
        	if(StrUtil.isNotEmpty(shop.getPeriod())) {        		
        		StringBuffer stringBuffer = new StringBuffer(shop.getPeriod());
        		shop.setPeriod(stringBuffer.insert(4, "???").toString());
        		shop.setPeriodDate(DateUtil.parse(shop.getPeriod(), "yyyy???MM"));        	
        	}
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(shop.getZxCtSuppliesShopSettlementId());
			file.setOtherType("0");
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	shop.setSettlementFileList(fileList);
        });
        // ??????????????????
        PageInfo<ZxCtSuppliesShopSettlementList> p = new PageInfo<>(zxCtSuppliesShopSettlementListList);

        return repEntity.okList(zxCtSuppliesShopSettlementListList, p.getTotal());
    }

	@Override
	public ResponseEntity addZxCtSuppliesShopSettlementListByOrgId(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
		DecimalFormat decimalFormat = new DecimalFormat("00");	
        zxCtSuppliesShopSettlementList.setZxCtSuppliesShopSettlementId(UuidUtil.generate());
        if(zxCtSuppliesShopSettlementList.getPeriodDate() != null) {
        	zxCtSuppliesShopSettlementList.setPeriod(DateUtil.format(zxCtSuppliesShopSettlementList.getPeriodDate(), "yyyyMM"));
        }      
        ZxCtSuppliesShopSettlementList settlementList = new ZxCtSuppliesShopSettlementList();
           settlementList.setContractID(zxCtSuppliesShopSettlementList.getContractID());
           settlementList.setInitSerialNumber(decimalFormat.format(Integer.parseInt(zxCtSuppliesShopSettlementList.getInitSerialNumber())-1));
           List<ZxCtSuppliesShopSettlementList> settlementListList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(settlementList);
        if(settlementListList.size()>0) {
        	if(Integer.parseInt(zxCtSuppliesShopSettlementList.getPeriod())<Integer.parseInt(settlementListList.get(0).getPeriod())) {
        		return repEntity.layerMessage("no", "????????????????????????????????????");
        	}
            zxCtSuppliesShopSettlementList.setTotalPayAmt(settlementListList.get(0).getTotalPayAmt());
            zxCtSuppliesShopSettlementList.setTotalAmt(settlementListList.get(0).getTotalAmt());   
        	//???????????????????????????????????????????????????
        	ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
        	resSettle.setBillID(settlementListList.get(0).getZxCtSuppliesShopSettlementId());
        	List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
        	resSettle.setZxCtSuppliesShopResSettleId(UuidUtil.generate());
        	resSettle.setBillID(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        	resSettle.setContractID(zxCtSuppliesShopSettlementList.getContractID());
        	if(resSettleList.size()>0) {
        		resSettle.setUpAmt(resSettleList.get(0).getTotalAmt());
        		resSettle.setTotalAmt(resSettleList.get(0).getTotalAmt());
        	}else {
        		resSettle.setUpAmt(new BigDecimal(0));
        		resSettle.setTotalAmt(new BigDecimal(0));

        	}
        	resSettle.setCreateUserInfo(userKey, realName);
        	zxCtSuppliesShopResSettleMapper.insert(resSettle);
        	ZxCtSuppliesShopPaySettlement paySettlement = new ZxCtSuppliesShopPaySettlement();
        	paySettlement.setBillID(settlementListList.get(0).getZxCtSuppliesShopSettlementId());
        	List<ZxCtSuppliesShopPaySettlement> paySettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(paySettlement);
        	paySettlement.setZxCtSuppliesShopPaySettlementId(UuidUtil.generate());
        	paySettlement.setBillID(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        	paySettlement.setContractID(zxCtSuppliesShopSettlementList.getContractID());
        	if(paySettlementList.size()>0) {
            	paySettlement.setUpAmt(paySettlementList.get(0).getTotalAmt());
            	paySettlement.setTotalAmt(paySettlementList.get(0).getTotalAmt());        		
        	}else {
            	paySettlement.setUpAmt(new BigDecimal(0));
            	paySettlement.setTotalAmt(new BigDecimal(0));
        	}
        	paySettlement.setCreateUserInfo(userKey, realName);
        	zxCtSuppliesShopPaySettlementMapper.insert(paySettlement);
        }
		ZxCtSuppliesContract contract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getContractID());
		if(contract != null) {
			contract.setSettleType("2");
			contract.setModifyUserInfo(userKey, realName);
    		zxCtSuppliesContractMapper.updateByPrimaryKeySelective(contract);
			zxCtSuppliesShopSettlementList.setContractAmt(contract.getContractCost());
			zxCtSuppliesShopSettlementList.setChangeAmt(contract.getAlterContractSum());
		}
        zxCtSuppliesShopSettlementList.setApih5FlowStatus("-1");
        zxCtSuppliesShopSettlementList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopSettlementListMapper.insert(zxCtSuppliesShopSettlementList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	if(zxCtSuppliesShopSettlementList.getSettlementFileList() != null  && zxCtSuppliesShopSettlementList.getSettlementFileList().size()>0) {
        		List<ZxErpFile> fileList = zxCtSuppliesShopSettlementList.getSettlementFileList();
        		fileList.parallelStream().forEach((file)->{
        			file.setOtherId(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        			file.setOtherType("0");
        			file.setUid((UuidUtil.generate()));
        			file.setCreateUserInfo(userKey, realName);
        			zxErpFileMapper.insert(file);
        		});
        	}
//        	ZxCtSuppliesContract dbZxContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getContractID());
//        	if(dbZxContract != null) {
//        		if(StrUtil.equals(zxCtSuppliesShopSettlementList.getBillType(), "1")) {
//        			dbZxContract.setSettleType("3");
//        		}else {
//        			dbZxContract.setSettleType("2");
//        		}
//        		dbZxContract.setModifyUserInfo(userKey, realName);
//        		zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbZxContract);   
//        	}
        	return repEntity.ok("sys.data.sava", zxCtSuppliesShopSettlementList);
        }
	}

	@Override
	public ResponseEntity updateZxCtSuppliesShopSettlementListByOrgId(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopSettlementList dbZxCtSuppliesShopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        if (dbZxCtSuppliesShopSettlementList != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId())) {
           // ????????????
           dbZxCtSuppliesShopSettlementList.setReportDate(zxCtSuppliesShopSettlementList.getReportDate());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setReportPerson(zxCtSuppliesShopSettlementList.getReportPerson());
           // ????????????????????????
           dbZxCtSuppliesShopSettlementList.setBeginDate(zxCtSuppliesShopSettlementList.getBeginDate());
           // ????????????????????????
           dbZxCtSuppliesShopSettlementList.setEndDate(zxCtSuppliesShopSettlementList.getEndDate());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setContent(zxCtSuppliesShopSettlementList.getContent());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setBillType(zxCtSuppliesShopSettlementList.getBillType());
           // ???????????????
           dbZxCtSuppliesShopSettlementList.setBillNo(zxCtSuppliesShopSettlementList.getBillNo());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setCountPerson(zxCtSuppliesShopSettlementList.getCountPerson());
           // ?????????
           dbZxCtSuppliesShopSettlementList.setReCountPerson(zxCtSuppliesShopSettlementList.getReCountPerson());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setStartDate(zxCtSuppliesShopSettlementList.getStartDate());
           //??????????????????
           dbZxCtSuppliesShopSettlementList.setDocumentsEndTime(zxCtSuppliesShopSettlementList.getDocumentsEndTime());
           // ??????
           dbZxCtSuppliesShopSettlementList.setRemarks(zxCtSuppliesShopSettlementList.getRemarks());
           // ??????
           dbZxCtSuppliesShopSettlementList.setSort(zxCtSuppliesShopSettlementList.getSort());
           // ??????????????????
           dbZxCtSuppliesShopSettlementList.setAppraisal(zxCtSuppliesShopSettlementList.getAppraisal());
           // ????????????????????????
           dbZxCtSuppliesShopSettlementList.setCwStatusRemark(zxCtSuppliesShopSettlementList.getCwStatusRemark());
           // ????????????
           dbZxCtSuppliesShopSettlementList.setBusinessDate(zxCtSuppliesShopSettlementList.getBusinessDate());
           // ??????
           dbZxCtSuppliesShopSettlementList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(dbZxCtSuppliesShopSettlementList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	if(zxCtSuppliesShopSettlementList.getSettlementFileList() != null  && zxCtSuppliesShopSettlementList.getSettlementFileList().size()>0) {
        		ZxErpFile erpFile = new ZxErpFile(); 
        		erpFile.setOtherType("0");
        		erpFile.setOtherId(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(erpFile);
        		if(fileList.size()>0) {
        			erpFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, erpFile);
        		}
        		fileList = zxCtSuppliesShopSettlementList.getSettlementFileList();
        		fileList.parallelStream().forEach((file)->{
        			file.setOtherType("0");
        			file.setOtherId(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        			file.setUid((UuidUtil.generate()));
        			file.setCreateUserInfo(userKey, realName);
        			zxErpFileMapper.insert(file);
        		});
        	}        	
            return repEntity.ok("sys.data.update",zxCtSuppliesShopSettlementList);
        }
	}

	@Override
	public ResponseEntity getZxCtSuppliesShopSettlementListFlowDetail(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
		ZxCtSuppliesShopSettlementList settlementList = zxCtSuppliesShopSettlementListMapper.getDetailByWorkId(zxCtSuppliesShopSettlementList.getWorkId());
		if(settlementList != null) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(settlementList.getZxCtSuppliesShopSettlementId());
        	file.setOtherType("1");
        	settlementList.setDocumentFileList(zxErpFileMapper.selectByZxErpFileList(file));
        	file = new ZxErpFile();
        	file.setOtherId(settlementList.getZxCtSuppliesShopSettlementId());
			file.setOtherType("0");
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	settlementList.setSettlementFileList(fileList);
		}
		return repEntity.ok(settlementList);
	}

	@Override
	public ResponseEntity saveZxCtSuppliesShopSettlementListFlow(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesShopSettlementList dbZxCtSuppliesContrApply = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
        if (dbZxCtSuppliesContrApply != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApply.getZxCtSuppliesShopSettlementId())) {
        	dbZxCtSuppliesContrApply.setApih5FlowStatus("0");
        	//?????????:0;????????????:1;????????????:2;???????????????:3;????????????:4;???????????????:5;
        	//????????????????????????
        	dbZxCtSuppliesContrApply.setAuditStatus("1");
        	
        	dbZxCtSuppliesContrApply.setWorkId(zxCtSuppliesShopSettlementList.getWorkId());
        	dbZxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
        	zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApply);
      	   for(ZxErpFile zxErpFile : zxCtSuppliesShopSettlementList.getDocumentFileList()) {
               zxErpFile.setUid(UuidUtil.generate());
               zxErpFile.setOtherType("1");
               zxErpFile.setOtherId(dbZxCtSuppliesContrApply.getZxCtSuppliesShopSettlementId());
               zxErpFile.setCreateUserInfo(userKey, realName);
               zxErpFileMapper.insert(zxErpFile);  
    	   }            	
        }
        return repEntity.ok("sys.data.sava", zxCtSuppliesShopSettlementList);
	}

	@Override
	public ResponseEntity updateZxCtSuppliesShopSettlementListFlow(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);		
		String userName = TokenUtils.getRealName(request);
		int flag = 0;
//		if(zxCtSuppliesShopSettlementList.getApih5FlowStatus().equals("0")) {
//			ZxCtSuppliesShopSettlementList flowDetail = zxCtSuppliesShopSettlementListMapper
//					.getDetailByWorkId(zxCtSuppliesShopSettlementList.getWorkId());	
//			flowDetail.setApih5FlowStatus(zxCtSuppliesShopSettlementList.getApih5FlowStatus());
//			flag = zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(flowDetail);
//			if (flag == 0) {
//				return repEntity.errorSave();
//			} else {
//				return repEntity.ok("sys.data.update", flowDetail);
//			}			
//		}
		if (zxCtSuppliesShopSettlementList.getApih5FlowStatus().equals("1")) {
			ZxCtSuppliesShopSettlementList flowDetail = zxCtSuppliesShopSettlementListMapper
					.getDetailByWorkId(zxCtSuppliesShopSettlementList.getWorkId());
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesShopSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesShopSettlementList.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesShopSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesShopSettlementList.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesShopSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesShopSettlementList.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setWorkId(zxCtSuppliesShopSettlementList.getWorkId());
//			flowDetail.setApih5FlowStatus("1");
			flowDetail.setModifyUserInfo(userKey, userName);
			flag = zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(flowDetail);
			if (flag == 0) {
				return repEntity.errorSave();
			} else {
	        	if(zxCtSuppliesShopSettlementList.getDocumentFileList() != null && zxCtSuppliesShopSettlementList.getDocumentFileList().size()>0) {
	            	ZxErpFile file = new ZxErpFile();
	            	file.setOtherId(flowDetail.getZxCtSuppliesShopSettlementId());
	            	file.setOtherType("1");
	            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
	            	file.setModifyUserInfo(userKey, userName);
	            	if(fileList.size()>0) {
	            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
	            	}
	         	   for(ZxErpFile zxErpFile : zxCtSuppliesShopSettlementList.getDocumentFileList()) {
	                    zxErpFile.setUid(UuidUtil.generate());
	                    zxErpFile.setOtherId(flowDetail.getZxCtSuppliesShopSettlementId());
	                    zxErpFile.setOtherType("1");
	                    zxErpFile.setCreateUserInfo(userKey, userName);
	                    zxErpFileMapper.insert(zxErpFile);  
	         	   }        		
	        	}
				return repEntity.ok("sys.data.update", flowDetail);
			}
		} else if (zxCtSuppliesShopSettlementList.getApih5FlowStatus().equals("2")) {
			ZxCtSuppliesShopSettlementList flowDetail = zxCtSuppliesShopSettlementListMapper
					.getDetailByWorkId(zxCtSuppliesShopSettlementList.getWorkId());
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesShopSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesShopSettlementList.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesShopSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesShopSettlementList.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesShopSettlementList.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesShopSettlementList.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setApih5FlowStatus("2");
			flag = zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(flowDetail);
        	//?????????????????????????????????????????????????????????
			if(StrUtil.equals(flowDetail.getBillType(), "1")) {				
				ZxCtSuppliesContract dbZxContract = zxCtSuppliesContractMapper.selectByPrimaryKey(flowDetail.getContractID());
				if(dbZxContract != null) {
					dbZxContract.setSettleType("3");
					dbZxContract.setModifyUserInfo(userKey, userName);
					zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbZxContract);
				}				
			}
			//????????????????????????????????????,?????????????????????
			List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingsList = new ArrayList<ZxSkStockTransItemReceiving>();
			ZxSkStockTransItemReceiving receiving = new ZxSkStockTransItemReceiving();
			ZxCtSuppliesShopResSettleItem resSettleItem = new ZxCtSuppliesShopResSettleItem();
			ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
			resSettle.setBillID(flowDetail.getZxCtSuppliesShopSettlementId());
			List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
				resSettle = resSettleList.get(0);
    		resSettleItem.setMainID(resSettle.getZxCtSuppliesShopResSettleId());
    		List<ZxCtSuppliesShopResSettleItem> resSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(resSettleItem);
    		for(ZxCtSuppliesShopResSettleItem item : resSettleItemList) {
    			receiving.setId(item.getStockBillItemID());
    			receiving.setBizType(item.getBizType());
    			zxSkStockTransItemReceivingsList.add(receiving);
    			ZxCtSuppliesContrShopList dbShopList = zxCtSuppliesContrShopListMapper.selectByPrimaryKey(item.getResID());
    			if(StrUtil.isNotEmpty(dbShopList.getSettledQty())) {    				
    				BigDecimal settledQty = new BigDecimal(dbShopList.getSettledQty());
    				BigDecimal thisQty = item.getThisQty();
    				dbShopList.setSettledQty(CalcUtils.calcAdd(settledQty, thisQty).stripTrailingZeros().toPlainString());
    			}else {
    				dbShopList.setSettledQty(item.getThisQty()+"");	
    			}
    			dbShopList.setModifyUserInfo(userKey, userName);
    			zxCtSuppliesContrShopListMapper.updateByPrimaryKeySelective(dbShopList);
    		}
    		zxSkStockTransferReceivingService.updateSuppliesShopResState(zxSkStockTransItemReceivingsList);
        	if(zxCtSuppliesShopSettlementList.getDocumentFileList() != null && zxCtSuppliesShopSettlementList.getDocumentFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(flowDetail.getZxCtSuppliesShopSettlementId());
            	file.setOtherType("1");
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, userName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesShopSettlementList.getDocumentFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(flowDetail.getZxCtSuppliesShopSettlementId());
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
			return repEntity.ok("sys.data.update", zxCtSuppliesShopSettlementList);
		}
	}

	@Override
	public ResponseEntity getZxCtSuppliesShopCampChangeIncreaseList(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
		zxCtSuppliesShopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
		List<ZxCtSuppliesLeaseCampChangeIncrease> changeIncreaseList = getCampChangeIncreaseList(zxCtSuppliesShopSettlementList); 
		zxCtSuppliesShopSettlementList.setCampChangeIncreaseList(changeIncreaseList);
		return repEntity.ok(zxCtSuppliesShopSettlementList);
	}

	@Override
	public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesShopSettlementReportList(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
		List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList = new ArrayList<ZxCtSuppliesLeaseCampChangeIncrease>();
		ZxCtSuppliesLeaseCampChangeIncrease campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
		zxCtSuppliesShopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
		campChangeIncreaseList = zxCtSuppliesShopSettlementListMapper.selectZxCtSuppliesShopCampChangeIncreaseList(zxCtSuppliesShopSettlementList);
		ZxCtSuppliesShopSettlementItem item = new ZxCtSuppliesShopSettlementItem();
		List<ZxCtSuppliesShopSettlementItem> itemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(item);
		ZxCtSuppliesShopPaySettlement settlement = new ZxCtSuppliesShopPaySettlement();
		settlement.setBillID(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
		List<ZxCtSuppliesShopPaySettlement> settlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(settlement);
		double qtySubtotal = campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisQty()).doubleValue()).reduce(0, Double::sum);
		double amtSubtotal = campChangeIncreaseList.stream().mapToDouble(ZxCtSuppliesLeaseCampChangeIncrease->new BigDecimal(ZxCtSuppliesLeaseCampChangeIncrease.getThisAmt()).doubleValue()).reduce(0, Double::sum);
		for(int i = 0; i<campChangeIncreaseList.size(); i++) {
			ZxCtSuppliesLeaseCampChangeIncrease changeIncrease = campChangeIncreaseList.get(i);
			changeIncrease.setFirstNum(i+"");
			changeIncrease.setOrgName(zxCtSuppliesShopSettlementList.getOrgName());
			changeIncrease.setBillNo(zxCtSuppliesShopSettlementList.getBillNo());
			changeIncrease.setContractNo(zxCtSuppliesShopSettlementList.getContractNo());
			changeIncrease.setPeriod(zxCtSuppliesShopSettlementList.getPeriod());
			changeIncrease.setSignedNo(zxCtSuppliesShopSettlementList.getSignedNo());
			changeIncrease.setQtySubtotal(qtySubtotal+"");
			changeIncrease.setAmtSubtotal(amtSubtotal+"");
			if(settlementList.get(0).getTransportAmt() != null) {				
				changeIncrease.setTransportAmt(settlementList.get(0).getTransportAmt()+"");
			}else {
				changeIncrease.setTransportAmt("0");
			}
			if(settlementList.get(0).getPadTariffAmt() != null) {				
				changeIncrease.setPadTariffAmt(settlementList.get(0).getPadTariffAmt()+"");
			}else {
				changeIncrease.setPadTariffAmt("0");
			}
			if(settlementList.get(0).getFineAmt() != null) {				
				changeIncrease.setFineAmt(settlementList.get(0).getFineAmt()+"");
			}else {
				changeIncrease.setFineAmt("0");
			}
			if(settlementList.get(0).getOtherAmt() != null) {				
				changeIncrease.setOtherAmt(settlementList.get(0).getOtherAmt()+"");
			}else {
				changeIncrease.setOtherAmt("0");
			}
			changeIncrease.setQtyTotal(qtySubtotal+"");
			changeIncrease.setAmtTotal(zxCtSuppliesShopSettlementList.getThisAmt()+"");
		}
		return campChangeIncreaseList;
	}

	@Override
	public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesShopSettlementSummaryReportList(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
		zxCtSuppliesShopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
		List<ZxCtSuppliesLeaseCampChangeIncrease> changeIncreaseList = getCampChangeIncreaseList(zxCtSuppliesShopSettlementList);
		for(int i = 0; i< changeIncreaseList.size(); i++) {
			ZxCtSuppliesLeaseCampChangeIncrease changeIncrease = changeIncreaseList.get(i);
			changeIncrease.setFirstNum(i+1+"");
			changeIncrease.setOrgName(zxCtSuppliesShopSettlementList.getOrgName());
			changeIncrease.setBillNo(zxCtSuppliesShopSettlementList.getBillNo());
			changeIncrease.setContractNo(zxCtSuppliesShopSettlementList.getContractNo());
			changeIncrease.setPeriod(zxCtSuppliesShopSettlementList.getPeriod());
			changeIncrease.setSignedNo(zxCtSuppliesShopSettlementList.getSignedNo());
		}
		return changeIncreaseList;
	}
	
	public List<ZxCtSuppliesLeaseCampChangeIncrease> getCampChangeIncreaseList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
		ZxCtSuppliesLeaseCampChangeIncrease campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
		List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList = zxCtSuppliesShopSettlementListMapper.selectZxCtSuppliesShopCampChangeIncreaseList(zxCtSuppliesShopSettlementList);
		//?????????????????????????????????
		if(campChangeIncreaseList.size()>0) {
			campChangeIncreaseList.parallelStream().forEach((crease)->{
				if(StrUtil.isEmpty(crease.getChangeQty())) {
					crease.setChangeQty(crease.getQty());
					crease.setChangePrice(crease.getPrice());
					crease.setChangePriceNoTax(crease.getPriceNoTax());
					crease.setChangeTax(crease.getTax());
					crease.setChangeContractSum(crease.getContractSum());
					crease.setChangeContractSumNoTax(crease.getContractSumNoTax());
					crease.setChangeContractTax(crease.getContractTax());
				}
				ZxCtSuppliesShopResSettleItem shopResSettleItem = new ZxCtSuppliesShopResSettleItem();
				shopResSettleItem.setContractID(crease.getContractID());
				shopResSettleItem.setResID(crease.getResID());
				List<ZxCtSuppliesShopResSettleItem> settlementItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(shopResSettleItem);
				if(settlementItemList.size()>0) {
					crease.setUpAmt(CalcUtils.calcSubtract(settlementItemList.stream().map(ZxCtSuppliesShopResSettleItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add), new BigDecimal(crease.getThisAmt())).toString());
					crease.setUpAmtNoTax(CalcUtils.calcSubtract(settlementItemList.stream().map(ZxCtSuppliesShopResSettleItem::getThisAmtNoTax).reduce(BigDecimal.ZERO,BigDecimal::add), new BigDecimal(crease.getThisAmtNoTax())).toString());
					crease.setUpAmtTax(CalcUtils.calcSubtract(settlementItemList.stream().map(ZxCtSuppliesShopResSettleItem::getThisAmtTax).reduce(BigDecimal.ZERO,BigDecimal::add), new BigDecimal(crease.getThisAmtTax())).toString());
					crease.setTotalAmt(settlementItemList.stream().map(ZxCtSuppliesShopResSettleItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add).toString());
					crease.setTotalAmtNoTax(settlementItemList.stream().map(ZxCtSuppliesShopResSettleItem::getThisAmtNoTax).reduce(BigDecimal.ZERO,BigDecimal::add).toString());
					crease.setTotalAmtTax(settlementItemList.stream().map(ZxCtSuppliesShopResSettleItem::getThisAmtTax).reduce(BigDecimal.ZERO,BigDecimal::add).toString());
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
			campChangeIncrease.setThisAmtNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getThisAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setThisAmtTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getThisAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmt(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getUpAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmtNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getUpAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmtTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getUpAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmt(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getTotalAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmtNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getTotalAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmtTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getTotalAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTaxRate("-");
			campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
			campChangeIncreaseList.add(campChangeIncrease);
			if(StrUtil.isNotEmpty(zxCtSuppliesShopSettlementList.getFlag())) {
				ZxCtSuppliesShopPaySettlement settlement = new ZxCtSuppliesShopPaySettlement();
				ZxCtSuppliesShopSettlementItem settlementItem = new ZxCtSuppliesShopSettlementItem();
				settlement.setBillID(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
				List<ZxCtSuppliesShopPaySettlement> settlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(settlement);
				if(settlementList.size()>0) {
					ZxCtSuppliesShopPaySettlementItem paySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
					paySettlementItem.setMainID(settlementList.get(0).getZxCtSuppliesShopPaySettlementId());
					List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(paySettlementItem);
					List<ZxCtSuppliesShopPaySettlementItem> transportList = new ArrayList<ZxCtSuppliesShopPaySettlementItem>(); 
					List<ZxCtSuppliesShopPaySettlementItem> fineList = new ArrayList<ZxCtSuppliesShopPaySettlementItem>(); 
					List<ZxCtSuppliesShopPaySettlementItem> otherList = new ArrayList<ZxCtSuppliesShopPaySettlementItem>(); 
					List<ZxCtSuppliesShopPaySettlementItem> padTariffList = new ArrayList<ZxCtSuppliesShopPaySettlementItem>(); 
					for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
						switch (item.getPayType()) {
						case "?????????":
							transportList.add(item);
							break;
						case "?????????":
							fineList.add(item);
							break;
						case "?????????":
							padTariffList.add(item);
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
					campChangeIncrease.setThisAmt(settlementList.get(0).getTransportAmt()+"");
					campChangeIncrease.setThisAmtNoTax("0");
					campChangeIncrease.setThisAmtTax("0");
					campChangeIncrease.setUpAmt(settlementList.get(0).getUpTransportAmt()+"");
					campChangeIncrease.setUpAmtNoTax("0");
					campChangeIncrease.setUpAmtTax("0");
					campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(settlementList.get(0).getTransportAmt(), settlementList.get(0).getUpTransportAmt())+"");
					campChangeIncrease.setTotalAmtNoTax("0");
					campChangeIncrease.setTotalAmtTax("0");
					campChangeIncrease.setTaxRate("-");
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setUnit("???");
					campChangeIncrease.setThisAmt(settlementList.get(0).getPadTariffAmt()+"");
					campChangeIncrease.setThisAmtNoTax("0");
					campChangeIncrease.setThisAmtTax("0");
					campChangeIncrease.setUpAmt(settlementList.get(0).getUpPadTariffAmt()+"");
					campChangeIncrease.setUpAmtNoTax("0");
					campChangeIncrease.setUpAmtTax("0");
					campChangeIncrease.setTotalAmt(CalcUtils.calcAdd(settlementList.get(0).getPadTariffAmt(), settlementList.get(0).getUpPadTariffAmt())+"");
					campChangeIncrease.setTotalAmtNoTax("0");
					campChangeIncrease.setTotalAmtTax("0");
					campChangeIncrease.setTaxRate("-");
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("C");
					campChangeIncrease.setWorkName("?????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
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
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("D");
					campChangeIncrease.setWorkName("?????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
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
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("E");
					campChangeIncrease.setWorkName("????????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setWorkNo("F");
					campChangeIncrease.setWorkName("??????");
					campChangeIncrease.setUnit("???");
					ZxCtSuppliesShopSettlementList shopSettlement = new ZxCtSuppliesShopSettlementList();
					int num = Integer.parseInt(zxCtSuppliesShopSettlementList.getInitSerialNumber());
					shopSettlement.setInitSerialNumber(num-1+"");
					shopSettlement.setContractID(zxCtSuppliesShopSettlementList.getContractID());
					List<ZxCtSuppliesShopSettlementList> shopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(shopSettlement);
					if(shopSettlementList.size()>0) {
						settlementItem = new ZxCtSuppliesShopSettlementItem();
						settlementItem.setMainID(shopSettlementList.get(0).getZxCtSuppliesShopSettlementId());
						List<ZxCtSuppliesShopSettlementItem> settlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
						for(ZxCtSuppliesShopSettlementItem item :settlementItemList) {
							if(StrUtil.equals(item.getStatisticsNo(), "100100")) {
								campChangeIncrease.setUpAmt(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100110")) {
								campChangeIncrease.setUpAmtNoTax(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100120")) {
								campChangeIncrease.setUpAmtTax(item.getTotalAmt());
							}
						}
					}
					settlementItem = new ZxCtSuppliesShopSettlementItem();
					settlementItem.setMainID(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
					List<ZxCtSuppliesShopSettlementItem> settlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
					for(ZxCtSuppliesShopSettlementItem item :settlementItemList) {
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
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
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
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
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
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
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
					campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
					campChangeIncrease.setWorkNo("E");
					campChangeIncrease.setWorkName("????????????");
					campChangeIncreaseList.add(campChangeIncrease);
					campChangeIncrease = new ZxCtSuppliesLeaseCampChangeIncrease();
					campChangeIncrease.setWorkNo("F");
					campChangeIncrease.setWorkName("??????");
					campChangeIncrease.setUnit("???");
					ZxCtSuppliesShopSettlementList shopSettlement = new ZxCtSuppliesShopSettlementList();
					int num = Integer.parseInt(zxCtSuppliesShopSettlementList.getInitSerialNumber());
					shopSettlement.setInitSerialNumber(num-1+"");
					shopSettlement.setContractID(zxCtSuppliesShopSettlementList.getContractID());
					List<ZxCtSuppliesShopSettlementList> shopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(shopSettlement);
					if(shopSettlementList.size()>0) {
						settlementItem = new ZxCtSuppliesShopSettlementItem();
						settlementItem.setMainID(shopSettlementList.get(0).getZxCtSuppliesShopSettlementId());
						List<ZxCtSuppliesShopSettlementItem> settlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
						for(ZxCtSuppliesShopSettlementItem item :settlementItemList) {
							if(StrUtil.equals(item.getStatisticsNo(), "100100")) {
								campChangeIncrease.setUpAmt(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100110")) {
								campChangeIncrease.setUpAmtNoTax(item.getTotalAmt());
							}else if(StrUtil.equals(item.getStatisticsNo(), "100120")) {
								campChangeIncrease.setUpAmtTax(item.getTotalAmt());
							}
						}
					}
					settlementItem = new ZxCtSuppliesShopSettlementItem();
					settlementItem.setMainID(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
					List<ZxCtSuppliesShopSettlementItem> settlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
					for(ZxCtSuppliesShopSettlementItem item :settlementItemList) {
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
			}
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
			campChangeIncrease.setThisAmt(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getThisAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setThisAmtNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getThisAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setThisAmtTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getThisAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmt(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getUpAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmtNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getUpAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setUpAmtTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getUpAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmt(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getTotalAmt()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmtNoTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getTotalAmtNoTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTotalAmtTax(campChangeIncreaseList.stream().mapToDouble(camchage->new BigDecimal(camchage.getTotalAmtTax()).doubleValue()).reduce(0, Double::sum)+"");
			campChangeIncrease.setTaxRate("-");
			campChangeIncrease.setIsDeduct(zxCtSuppliesShopSettlementList.getIsDeduct());
			campChangeIncreaseList.add(campChangeIncrease);
		}else {
			campChangeIncreaseList = new ArrayList<ZxCtSuppliesLeaseCampChangeIncrease>();
		}
		return campChangeIncreaseList;
	}
}

