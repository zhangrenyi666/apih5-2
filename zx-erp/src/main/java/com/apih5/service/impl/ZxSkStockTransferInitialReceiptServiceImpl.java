package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemInitialReceiptMapper;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxSkStock;
import com.apih5.mybatis.pojo.ZxSkStockTransItemInitialReceipt;
import com.apih5.service.ZxSkStockService;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockTransferInitialReceiptMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import com.apih5.service.ZxSkStockTransferInitialReceiptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferInitialReceiptService")
public class ZxSkStockTransferInitialReceiptServiceImpl implements ZxSkStockTransferInitialReceiptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferInitialReceiptMapper zxSkStockTransferInitialReceiptMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptMapper zxSkStockTransItemInitialReceiptMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkStockTransferInitialReceiptListByCondition(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        if (zxSkStockTransferInitialReceipt == null) {
            zxSkStockTransferInitialReceipt = new ZxSkStockTransferInitialReceipt();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferInitialReceipt.setCompanyId("");
            zxSkStockTransferInitialReceipt.setInOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferInitialReceipt.getOrgID2())){
                zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferInitialReceipt.setCompanyId(zxSkStockTransferInitialReceipt.getInOrgID());
            zxSkStockTransferInitialReceipt.setInOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferInitialReceipt.getOrgID2())){
                zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getInOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferInitialReceipt.getOrgID2())){
                zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferInitialReceipt.getPage(),zxSkStockTransferInitialReceipt.getLimit());
        // ????????????
        List<ZxSkStockTransferInitialReceipt> zxSkStockTransferInitialReceiptList = zxSkStockTransferInitialReceiptMapper.selectByZxSkStockTransferInitialReceiptList(zxSkStockTransferInitialReceipt);
        //????????????
        for (ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt1 : zxSkStockTransferInitialReceiptList) {
            //????????????
            zxSkStockTransferInitialReceipt1.setTotalAmt(NumberUtil.round(zxSkStockTransferInitialReceipt1.getTotalAmt(),2));
            ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
            zxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt1.getId());
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
            zxSkStockTransferInitialReceipt1.setZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipts);
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferInitialReceipt> p = new PageInfo<>(zxSkStockTransferInitialReceiptList);
        return repEntity.okList(zxSkStockTransferInitialReceiptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferInitialReceiptDetails(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        if (zxSkStockTransferInitialReceipt == null) {
            zxSkStockTransferInitialReceipt = new ZxSkStockTransferInitialReceipt();
        }
        // ????????????
        ZxSkStockTransferInitialReceipt dbZxSkStockTransferInitialReceipt = zxSkStockTransferInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransferInitialReceipt.getId());
        // ????????????
        if (dbZxSkStockTransferInitialReceipt != null) {
            ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
            zxSkStockTransItemInitialReceipt.setBillID(dbZxSkStockTransferInitialReceipt.getId());
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
            dbZxSkStockTransferInitialReceipt.setZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipts);
            return repEntity.ok(dbZxSkStockTransferInitialReceipt);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferInitialReceipt.setId(UuidUtil.generate());
        zxSkStockTransferInitialReceipt.setCreateUserInfo(userKey, realName);

        //??????????????????: 0:?????????
        zxSkStockTransferInitialReceipt.setBillStatus("0");
        //????????????
        BigDecimal total = new BigDecimal(0);
        //????????????
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransferInitialReceipt.getZxSkStockTransItemInitialReceiptList();
        if(zxSkStockTransItemInitialReceiptList != null && zxSkStockTransItemInitialReceiptList.size()>0) {
            for (ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt : zxSkStockTransItemInitialReceiptList) {
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getInAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemInitialReceipt.getInAmt());
                }
                //???????????? ->   ????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getInPrice())!=0){
                    zxSkStockTransItemInitialReceipt.setStdPrice(zxSkStockTransItemInitialReceipt.getInPrice());
                }else {
                    zxSkStockTransItemInitialReceipt.setStdPrice(new BigDecimal(0));
                }
                //??????????????? -> ???????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getResAllFeeNoTax())!=0){
                    zxSkStockTransItemInitialReceipt.setInAmtNoTax(zxSkStockTransItemInitialReceipt.getResAllFeeNoTax());
                }else {
                    zxSkStockTransItemInitialReceipt.setInAmtNoTax(new BigDecimal(0));
                }
                //?????????????????? -> ????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getInQty())!=0){
                    zxSkStockTransItemInitialReceipt.setIsOutNumber(zxSkStockTransItemInitialReceipt.getInQty());
                }else {
                    zxSkStockTransItemInitialReceipt.setIsOutNumber(new BigDecimal(0));
                }
                zxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt.getId());
                zxSkStockTransItemInitialReceipt.setId((UuidUtil.generate()));
                zxSkStockTransItemInitialReceipt.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemInitialReceiptMapper.insert(zxSkStockTransItemInitialReceipt);
            }
        }
        zxSkStockTransferInitialReceipt.setTotalAmt(total);
        int flag = zxSkStockTransferInitialReceiptMapper.insert(zxSkStockTransferInitialReceipt);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferInitialReceipt);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferInitialReceipt dbzxSkStockTransferInitialReceipt = zxSkStockTransferInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransferInitialReceipt.getId());
        if (dbzxSkStockTransferInitialReceipt != null && StrUtil.isNotEmpty(dbzxSkStockTransferInitialReceipt.getId())) {
           // ??????(1)????????????id
           dbzxSkStockTransferInitialReceipt.setOrgID(zxSkStockTransferInitialReceipt.getOrgID());
           // ??????(2)
           dbzxSkStockTransferInitialReceipt.setWhOrgID(zxSkStockTransferInitialReceipt.getWhOrgID());
           // ??????(3)
           dbzxSkStockTransferInitialReceipt.setWarehouseName(zxSkStockTransferInitialReceipt.getWarehouseName());
           // ????????????ID
           dbzxSkStockTransferInitialReceipt.setOutOrgID(zxSkStockTransferInitialReceipt.getOutOrgID());
           // ????????????ID
           dbzxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getInOrgID());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setBizType(zxSkStockTransferInitialReceipt.getBizType());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setBillNo(zxSkStockTransferInitialReceipt.getBillNo());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setMaterialSource(zxSkStockTransferInitialReceipt.getMaterialSource());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setPurpose(zxSkStockTransferInitialReceipt.getPurpose());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setBusDate(zxSkStockTransferInitialReceipt.getBusDate());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setOutOrgName(zxSkStockTransferInitialReceipt.getOutOrgName());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setInOrgName(zxSkStockTransferInitialReceipt.getInOrgName());
           // ????????????

           // ?????????(1)
           dbzxSkStockTransferInitialReceipt.setOuttransactor(zxSkStockTransferInitialReceipt.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferInitialReceipt.setIntransactor(zxSkStockTransferInitialReceipt.getIntransactor());
           // ???????????????
           dbzxSkStockTransferInitialReceipt.setWaretransactor(zxSkStockTransferInitialReceipt.getWaretransactor());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setBuyer(zxSkStockTransferInitialReceipt.getBuyer());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setConsignee(zxSkStockTransferInitialReceipt.getConsignee());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setAuditor(zxSkStockTransferInitialReceipt.getAuditor());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setVoucherNo(zxSkStockTransferInitialReceipt.getVoucherNo());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setContractNo(zxSkStockTransferInitialReceipt.getContractNo());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setInvoiceNo(zxSkStockTransferInitialReceipt.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setBillType(zxSkStockTransferInitialReceipt.getBillType());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setBillFlag(zxSkStockTransferInitialReceipt.getBillFlag());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setBillStatus(zxSkStockTransferInitialReceipt.getBillStatus());
           // ?????????
           dbzxSkStockTransferInitialReceipt.setReporter(zxSkStockTransferInitialReceipt.getReporter());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setDeductAmtType(zxSkStockTransferInitialReceipt.getDeductAmtType());
           // ??????
           dbzxSkStockTransferInitialReceipt.setRemark(zxSkStockTransferInitialReceipt.getRemark());
           // ??????
           dbzxSkStockTransferInitialReceipt.setCombProp(zxSkStockTransferInitialReceipt.getCombProp());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setExpirationDate(zxSkStockTransferInitialReceipt.getExpirationDate());
           // ????????????ID
           dbzxSkStockTransferInitialReceipt.setResourceID(zxSkStockTransferInitialReceipt.getResourceID());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setResourceName(zxSkStockTransferInitialReceipt.getResourceName());
           // cbsID
           dbzxSkStockTransferInitialReceipt.setCbsID(zxSkStockTransferInitialReceipt.getCbsID());
           // cbs??????
           dbzxSkStockTransferInitialReceipt.setCbsName(zxSkStockTransferInitialReceipt.getCbsName());
           // ?????????????????????????????????
           dbzxSkStockTransferInitialReceipt.setInvoiceNum(zxSkStockTransferInitialReceipt.getInvoiceNum());
           // ??????(%)
           dbzxSkStockTransferInitialReceipt.setTaxRate(zxSkStockTransferInitialReceipt.getTaxRate());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setIsDeduct(zxSkStockTransferInitialReceipt.getIsDeduct());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setOrNotPrecollecte(zxSkStockTransferInitialReceipt.getOrNotPrecollecte());
           // ??????id
           dbzxSkStockTransferInitialReceipt.setCompanyId(zxSkStockTransferInitialReceipt.getCompanyId());
           // ????????????
           dbzxSkStockTransferInitialReceipt.setCompanyName(zxSkStockTransferInitialReceipt.getCompanyName());
           // ??????
           dbzxSkStockTransferInitialReceipt.setModifyUserInfo(userKey, realName);
            //???????????????
            ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
            zxSkStockTransItemInitialReceipt.setBillID(dbzxSkStockTransferInitialReceipt.getId());
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
            if(zxSkStockTransItemInitialReceipts != null && zxSkStockTransItemInitialReceipts.size()>0) {
                zxSkStockTransItemInitialReceipt.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemInitialReceiptMapper.batchDeleteUpdateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceipts, zxSkStockTransItemInitialReceipt);
            }
            //??????list
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransferInitialReceipt.getZxSkStockTransItemInitialReceiptList();
            //????????????
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemInitialReceiptList != null && zxSkStockTransItemInitialReceiptList.size()>0) {
                for(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt1 : zxSkStockTransItemInitialReceiptList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemInitialReceipt1.getInAmt());

                    //???????????? ->   ????????????
                    if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt1.getInPrice())!=0){
                        zxSkStockTransItemInitialReceipt1.setStdPrice(zxSkStockTransItemInitialReceipt1.getInPrice());
                    }else {
                        zxSkStockTransItemInitialReceipt1.setStdPrice(new BigDecimal(0));
                    }
                    //??????????????? -> ???????????????
                    if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt1.getResAllFeeNoTax())!=0){
                        zxSkStockTransItemInitialReceipt1.setInAmtNoTax(zxSkStockTransItemInitialReceipt1.getResAllFeeNoTax());
                    }else {
                        zxSkStockTransItemInitialReceipt1.setInAmtNoTax(new BigDecimal(0));
                    }
                    //?????????????????? -> ????????????
                    if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt1.getInQty())!=0){
                        zxSkStockTransItemInitialReceipt1.setIsOutNumber(zxSkStockTransItemInitialReceipt1.getInQty());
                    }else {
                        zxSkStockTransItemInitialReceipt1.setIsOutNumber(new BigDecimal(0));
                    }
                    zxSkStockTransItemInitialReceipt1.setId(UuidUtil.generate());
                    zxSkStockTransItemInitialReceipt1.setBillID(zxSkStockTransferInitialReceipt.getId());
                    zxSkStockTransItemInitialReceipt1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemInitialReceiptMapper.insert(zxSkStockTransItemInitialReceipt1);
                }
            }

            dbzxSkStockTransferInitialReceipt.setTotalAmt(total);
            flag = zxSkStockTransferInitialReceiptMapper.updateByPrimaryKey(dbzxSkStockTransferInitialReceipt);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferInitialReceipt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferInitialReceipt(List<ZxSkStockTransferInitialReceipt> zxSkStockTransferInitialReceiptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferInitialReceiptList != null && zxSkStockTransferInitialReceiptList.size() > 0) {
            for (ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt : zxSkStockTransferInitialReceiptList) {
                // ????????????
                ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
                zxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt.getId());
                List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
                if(zxSkStockTransItemInitialReceipts != null && zxSkStockTransItemInitialReceipts.size()>0) {
                    zxSkStockTransItemInitialReceipt.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemInitialReceiptMapper.batchDeleteUpdateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceipts, zxSkStockTransItemInitialReceipt);
                }
            }
           ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt = new ZxSkStockTransferInitialReceipt();
           zxSkStockTransferInitialReceipt.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferInitialReceiptMapper.batchDeleteUpdateZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceiptList, zxSkStockTransferInitialReceipt);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferInitialReceiptList);
        }
    }

    @Override
    public ResponseEntity getZxSkStockTransferInitialReceiptNo(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        if(StrUtil.isEmpty(zxSkStockTransferInitialReceipt.getInOrgID()) || zxSkStockTransferInitialReceipt.getBusDate() == null){
            return repEntity.ok(null);
        }
        String orgID = zxSkStockTransferInitialReceipt.getInOrgID();
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferInitialReceipt.getBusDate());
        int month = DateUtil.month(zxSkStockTransferInitialReceipt.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferInitialReceipt.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" + (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferInitialReceiptMapper.getZxSkStockTransferInitialReceiptCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + "???????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    @Override
    public synchronized ResponseEntity checkZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        //??????????????????
        ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt1 = zxSkStockTransferInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransferInitialReceipt.getId());
        if(StrUtil.equals(zxSkStockTransferInitialReceipt1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkStockTransItemInitialReceipt dbzxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
        dbzxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt1.getId());
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(dbzxSkStockTransItemInitialReceipt);
        if(CollUtil.isEmpty(zxSkStockTransItemInitialReceiptList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptListNew = new ArrayList<>();
        zxSkStockTransItemInitialReceiptList
                //?????????parallelStream
                .parallelStream()
                //collect?????????????????????????????????reduce??????????????????????????????????????????????????????????????????????????????????????????
                .collect(Collectors.groupingBy(o -> (o.getResID()+o.getInPrice()), Collectors.toList())).forEach(
                (id,transfer) -> {
                    transfer.stream().reduce((a,b) -> new ZxSkStockTransItemInitialReceipt(
                            //resID
                            a.getResID(),
                            //code
                            a.getResCode(),
                            //Name
                            a.getResName(),
                            //spec
                            a.getSpec(),
                            //unit
                            a.getResUnit(),
                            //????????????
                            CalcUtils.calcAdd(a.getInQty(),b.getInQty()),
                            //???????????????
                            CalcUtils.calcAdd(a.getInAmt(),b.getInAmt())
                    )).ifPresent(zxSkStockTransItemInitialReceiptListNew::add);
                }
        );
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt : zxSkStockTransItemInitialReceiptListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferInitialReceipt1.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferInitialReceipt1.getInOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferInitialReceipt1.getWhOrgID());
            //????????????ID
            zxSkStock.setResourceId(zxSkStockTransferInitialReceipt1.getResourceID());
            //??????????????????
            zxSkStock.setResourceName(zxSkStockTransferInitialReceipt1.getResourceName());
            //??????Id
            zxSkStock.setResID(zxSkStockTransItemInitialReceipt.getResID());
            //????????????
            zxSkStock.setResCode(zxSkStockTransItemInitialReceipt.getResCode());
            //????????????
            zxSkStock.setResName(zxSkStockTransItemInitialReceipt.getResName());
            //????????????spec
            zxSkStock.setSpec(zxSkStockTransItemInitialReceipt.getSpec());
            //??????unit
            zxSkStock.setUnit(zxSkStockTransItemInitialReceipt.getResUnit());
            //????????????/????????????
            zxSkStock.setStockQty(zxSkStockTransItemInitialReceipt.getInQty());
            //????????????(???????????????)//??????(????????????)
            zxSkStock.setStockAmt(zxSkStockTransItemInitialReceipt.getInAmt());
            zxSkStockList.add(zxSkStock);
        }
        //?????????
        ResponseEntity responseEntity = zxSkStockService.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferInitialReceipt1.setBillStatus("1");
            zxSkStockTransferInitialReceipt1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferInitialReceiptMapper.checkZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceipt1);
            // ??????
            if (flag == 0) {
                try {
                    throw new Exception("??????????????????");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.errorUpdate();
            }
            return responseEntity;
        }else {
            return responseEntity;
        }
    }




}
