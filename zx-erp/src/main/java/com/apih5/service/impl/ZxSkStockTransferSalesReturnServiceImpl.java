package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkStockTransferSalesReturnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferSalesReturnService")
public class ZxSkStockTransferSalesReturnServiceImpl implements ZxSkStockTransferSalesReturnService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferSalesReturnMapper zxSkStockTransferSalesReturnMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemSalesReturnMapper zxSkStockTransItemSalesReturnMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSkStockMapper zxSkStockMapper;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    //???????????????Mapper
    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptMapper zxSkStockTransItemInitialReceiptMapper;

    //???????????????Mapper
    @Autowired(required = true)
    private ZxSkStockTransItemReceivingMapper zxSkStockTransItemReceivingMapper;

    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnListByCondition(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (zxSkStockTransferSalesReturn == null) {
            zxSkStockTransferSalesReturn = new ZxSkStockTransferSalesReturn();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferSalesReturn.setCompanyId("");
            zxSkStockTransferSalesReturn.setInOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferSalesReturn.setCompanyId(zxSkStockTransferSalesReturn.getInOrgID());
            zxSkStockTransferSalesReturn.setInOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferSalesReturn.setInOrgID(zxSkStockTransferSalesReturn.getInOrgID());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // ????????????
        List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList = zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnList(zxSkStockTransferSalesReturn);
        //????????????
        for (ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn1 : zxSkStockTransferSalesReturnList) {
            ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
            zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferSalesReturn1.getId());
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnListMinNumber(zxSkStockTransItemSalesReturn);
            zxSkStockTransferSalesReturn1.setZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturns);
        }
        //??????
        for (ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn1 : zxSkStockTransferSalesReturnList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferSalesReturn1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferSalesReturn1.setFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferSalesReturn> p = new PageInfo<>(zxSkStockTransferSalesReturnList);
        return repEntity.okList(zxSkStockTransferSalesReturnList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnDetails(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (zxSkStockTransferSalesReturn == null) {
            zxSkStockTransferSalesReturn = new ZxSkStockTransferSalesReturn();
        }
        // ????????????
        ZxSkStockTransferSalesReturn dbZxSkStockTransferSalesReturn = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferSalesReturn.getId());
        // ????????????
        if (dbZxSkStockTransferSalesReturn != null) {
            ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
            zxSkStockTransItemSalesReturn.setBillID(dbZxSkStockTransferSalesReturn.getId());
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
            for (ZxSkStockTransItemSalesReturn skStockTransItemSalesReturn : zxSkStockTransItemSalesReturns) {
                skStockTransItemSalesReturn.setId(skStockTransItemSalesReturn.getResID()+","+skStockTransItemSalesReturn.getInPrice());
            }
            dbZxSkStockTransferSalesReturn.setZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturns);

            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferSalesReturn.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferSalesReturn.setFileList(zxErpFiles);

            return repEntity.ok(dbZxSkStockTransferSalesReturn);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferSalesReturn.setId(UuidUtil.generate());
        zxSkStockTransferSalesReturn.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkStockTransferSalesReturn.setBillStatus("0");
        //totalAmt
        //????????????
        BigDecimal total = new BigDecimal(0);//inAmt
        //????????????
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransferSalesReturn.getZxSkStockTransItemSalesReturnList();
        if(zxSkStockTransItemSalesReturnList != null && zxSkStockTransItemSalesReturnList.size()>0) {
            for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnList) {
                if(CalcUtils.compareToZero(zxSkStockTransItemSalesReturn.getInAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemSalesReturn.getInAmt());
                }
                //resAllFee ->inAmtAll
                //???????????? -> ??????
                if(CalcUtils.compareToZero(zxSkStockTransItemSalesReturn.getResAllFee())!=0){
                    zxSkStockTransItemSalesReturn.setInAmtAll(zxSkStockTransItemSalesReturn.getResAllFee());
                }else {
                    zxSkStockTransItemSalesReturn.setInAmtAll(new BigDecimal(0));
                }
                zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferSalesReturn.getId());

                //????????????
                zxSkStockTransItemSalesReturn.setSettlementStatus("0");
//                ??????id??????????????????(??????)
                zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId(zxSkStockTransItemSalesReturn.getId());
                zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId((UuidUtil.generate()));
                zxSkStockTransItemSalesReturn.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemSalesReturnMapper.insert(zxSkStockTransItemSalesReturn);
            }
        }
        //????????????
        List<ZxErpFile> fileList = zxSkStockTransferSalesReturn.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferSalesReturn.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferSalesReturn.setTotalAmt(total);
        int flag = zxSkStockTransferSalesReturnMapper.insert(zxSkStockTransferSalesReturn);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferSalesReturn);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferSalesReturn dbzxSkStockTransferSalesReturn = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferSalesReturn.getId());
        if (dbzxSkStockTransferSalesReturn != null && StrUtil.isNotEmpty(dbzxSkStockTransferSalesReturn.getId())) {
           // ??????(1)
           dbzxSkStockTransferSalesReturn.setOrgID(zxSkStockTransferSalesReturn.getOrgID());
           // ??????(2)
           dbzxSkStockTransferSalesReturn.setWhOrgID(zxSkStockTransferSalesReturn.getWhOrgID());
           // ????????????ID
           dbzxSkStockTransferSalesReturn.setOutOrgID(zxSkStockTransferSalesReturn.getOutOrgID());
           // ????????????ID
           dbzxSkStockTransferSalesReturn.setInOrgID(zxSkStockTransferSalesReturn.getInOrgID());
           // ????????????
           dbzxSkStockTransferSalesReturn.setBizType(zxSkStockTransferSalesReturn.getBizType());
           // ????????????
           dbzxSkStockTransferSalesReturn.setBillNo(zxSkStockTransferSalesReturn.getBillNo());
           // ????????????
           dbzxSkStockTransferSalesReturn.setMaterialSource(zxSkStockTransferSalesReturn.getMaterialSource());
           // ????????????
           dbzxSkStockTransferSalesReturn.setPurpose(zxSkStockTransferSalesReturn.getPurpose());
           // ????????????
           dbzxSkStockTransferSalesReturn.setBusDate(zxSkStockTransferSalesReturn.getBusDate());
           // ????????????
           dbzxSkStockTransferSalesReturn.setOutOrgName(zxSkStockTransferSalesReturn.getOutOrgName());
           // ????????????
           dbzxSkStockTransferSalesReturn.setInOrgName(zxSkStockTransferSalesReturn.getInOrgName());
           // ????????????
           dbzxSkStockTransferSalesReturn.setTotalAmt(zxSkStockTransferSalesReturn.getTotalAmt());
           // ?????????(1)
           dbzxSkStockTransferSalesReturn.setOuttransactor(zxSkStockTransferSalesReturn.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferSalesReturn.setIntransactor(zxSkStockTransferSalesReturn.getIntransactor());
           // ???????????????
           dbzxSkStockTransferSalesReturn.setWaretransactor(zxSkStockTransferSalesReturn.getWaretransactor());
           // ?????????
           dbzxSkStockTransferSalesReturn.setBuyer(zxSkStockTransferSalesReturn.getBuyer());
           // ?????????
           dbzxSkStockTransferSalesReturn.setConsignee(zxSkStockTransferSalesReturn.getConsignee());
           // ?????????
           dbzxSkStockTransferSalesReturn.setAuditor(zxSkStockTransferSalesReturn.getAuditor());
           // ?????????
           dbzxSkStockTransferSalesReturn.setVoucherNo(zxSkStockTransferSalesReturn.getVoucherNo());
           // ?????????
           dbzxSkStockTransferSalesReturn.setContractNo(zxSkStockTransferSalesReturn.getContractNo());
           // ?????????
           dbzxSkStockTransferSalesReturn.setInvoiceNo(zxSkStockTransferSalesReturn.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferSalesReturn.setBillType(zxSkStockTransferSalesReturn.getBillType());
           // ????????????
           dbzxSkStockTransferSalesReturn.setBillFlag(zxSkStockTransferSalesReturn.getBillFlag());
           // ????????????
           dbzxSkStockTransferSalesReturn.setBillStatus(zxSkStockTransferSalesReturn.getBillStatus());
           // ?????????
           dbzxSkStockTransferSalesReturn.setReporter(zxSkStockTransferSalesReturn.getReporter());
           // ????????????
           dbzxSkStockTransferSalesReturn.setDeductAmtType(zxSkStockTransferSalesReturn.getDeductAmtType());
           // ??????
           dbzxSkStockTransferSalesReturn.setRemark(zxSkStockTransferSalesReturn.getRemark());
           // ????????????ID
           dbzxSkStockTransferSalesReturn.setResourceID(zxSkStockTransferSalesReturn.getResourceID());
           // ????????????
           dbzxSkStockTransferSalesReturn.setResourceName(zxSkStockTransferSalesReturn.getResourceName());
           // ??????(%)
           dbzxSkStockTransferSalesReturn.setTaxRate(zxSkStockTransferSalesReturn.getTaxRate());
           // ????????????
           dbzxSkStockTransferSalesReturn.setIsDeduct(zxSkStockTransferSalesReturn.getIsDeduct());
           // ??????(3)
           dbzxSkStockTransferSalesReturn.setWarehouseName(zxSkStockTransferSalesReturn.getWarehouseName());
           // ?????????????????????????????????
           dbzxSkStockTransferSalesReturn.setInvoiceNum(zxSkStockTransferSalesReturn.getInvoiceNum());
           // ??????id
           dbzxSkStockTransferSalesReturn.setCompanyId(zxSkStockTransferSalesReturn.getCompanyId());
           // ????????????
           dbzxSkStockTransferSalesReturn.setCompanyName(zxSkStockTransferSalesReturn.getCompanyName());
           // ??????
           dbzxSkStockTransferSalesReturn.setModifyUserInfo(userKey, realName);

            //???????????????
            ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
            zxSkStockTransItemSalesReturn.setBillID(dbzxSkStockTransferSalesReturn.getId());
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
            if(zxSkStockTransItemSalesReturns != null && zxSkStockTransItemSalesReturns.size()>0) {
                zxSkStockTransItemSalesReturn.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemSalesReturnMapper.batchDeleteUpdateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturns, zxSkStockTransItemSalesReturn);
            }
            //??????list
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransferSalesReturn.getZxSkStockTransItemSalesReturnList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemSalesReturnList != null && zxSkStockTransItemSalesReturnList.size()>0) {
                for(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn1 : zxSkStockTransItemSalesReturnList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemSalesReturn1.getInAmt());
                    zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId(zxSkStockTransItemSalesReturn.getId());
                    zxSkStockTransItemSalesReturn1.setZxSkStockTransItemSalesReturnId(UuidUtil.generate());
                    zxSkStockTransItemSalesReturn1.setBillID(dbzxSkStockTransferSalesReturn.getId());
                    zxSkStockTransItemSalesReturn1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemSalesReturnMapper.insert(zxSkStockTransItemSalesReturn1);
                }
            }

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferSalesReturn.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkStockTransferSalesReturn.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferSalesReturn.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferSalesReturn.setTotalAmt(total);
            flag = zxSkStockTransferSalesReturnMapper.updateByPrimaryKey(dbzxSkStockTransferSalesReturn);

        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferSalesReturn);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferSalesReturn(List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferSalesReturnList != null && zxSkStockTransferSalesReturnList.size() > 0) {
            for (ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn : zxSkStockTransferSalesReturnList) {
                // ????????????
                ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
                zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferSalesReturn.getId());
                List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
                if(zxSkStockTransItemSalesReturns != null && zxSkStockTransItemSalesReturns.size()>0) {
                    zxSkStockTransItemSalesReturn.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemSalesReturnMapper.batchDeleteUpdateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturns, zxSkStockTransItemSalesReturn);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferSalesReturn.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn = new ZxSkStockTransferSalesReturn();
           zxSkStockTransferSalesReturn.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferSalesReturnMapper.batchDeleteUpdateZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturnList, zxSkStockTransferSalesReturn);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferSalesReturnList);
        }
    }

    //????????????
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        //????????????
        ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn1 = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferSalesReturn.getId());
        if(StrUtil.equals(zxSkStockTransferSalesReturn1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkStockTransItemSalesReturn dbzxSkStockTransItemSalesReturn1 = new ZxSkStockTransItemSalesReturn();
        dbzxSkStockTransItemSalesReturn1.setBillID(zxSkStockTransferSalesReturn1.getId());
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnListMinNumber(dbzxSkStockTransItemSalesReturn1);
        if(CollUtil.isEmpty(zxSkStockTransItemSalesReturnList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????????????????
        //????????????
        //(????????????id(whOrgID),??????id(????????????inOrgID),????????????id(?????????outOrgID),??????id(resourceID),????????????ID List)
        Map<String,ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnMap = zxSkStockTransferSalesReturnMapper.getZxSkStockTransferSalesReturnResName(zxSkStockTransItemSalesReturnList);

        //?????????????????????
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnListIsOutNumber = new ArrayList<>();

        //??????List (?????????????????????????????????)
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = new ArrayList<>();
        for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnList) {
            ZxSkStockTransItemSalesReturn dbzxSkStockTransItemSalesReturn = zxSkStockTransItemSalesReturnMap.get(zxSkStockTransItemSalesReturn.getMainIdAndItemId());
            if(CalcUtils.compareTo(dbzxSkStockTransItemSalesReturn.getIsOutNumber(),zxSkStockTransItemSalesReturn.getInQty())<0){
                zxSkStockTransItemSalesReturns.add(zxSkStockTransItemSalesReturn);
            }else {
                String str = zxSkStockTransItemSalesReturn.getMainIdAndItemId();
                ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn1 = new ZxSkStockTransItemSalesReturn();
                zxSkStockTransItemSalesReturn1.setId(StrUtil.sub(str,str.indexOf("-")+1,str.length()));
                zxSkStockTransItemSalesReturn1.setIsOutNumber(CalcUtils.calcSubtract(dbzxSkStockTransItemSalesReturn.getIsOutNumber(),zxSkStockTransItemSalesReturn.getInQty()));
                zxSkStockTransItemSalesReturn1.setModifyUserInfo(userKey,realName);
                zxSkStockTransItemSalesReturnListIsOutNumber.add(zxSkStockTransItemSalesReturn1);

            }
        }
        if(CollUtil.isNotEmpty(zxSkStockTransItemSalesReturns)){
            return repEntity.layerMessage(false,zxSkStockTransItemSalesReturns, "?????????????????????????????????????????????");
        }
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferSalesReturn.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferSalesReturn.getInOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferSalesReturn.getWhOrgID());
            //????????????ID
            zxSkStock.setResourceId(zxSkStockTransferSalesReturn.getResourceID());
            //??????????????????
            zxSkStock.setResourceName(zxSkStockTransferSalesReturn.getResourceName());
            //??????Id
            zxSkStock.setResID(zxSkStockTransItemSalesReturn.getResID());
            //????????????
            zxSkStock.setResCode(zxSkStockTransItemSalesReturn.getResCode());
            //????????????
            zxSkStock.setResName(zxSkStockTransItemSalesReturn.getResName());
            //????????????spec
            zxSkStock.setSpec(zxSkStockTransItemSalesReturn.getSpec());
            //??????unit
            zxSkStock.setUnit(zxSkStockTransItemSalesReturn.getResUnit());
            //????????????
            zxSkStock.setStockQty(zxSkStockTransItemSalesReturn.getInQty());
            //????????????
            zxSkStock.setStockAmt(zxSkStockTransItemSalesReturn.getInAmt());
            zxSkStockList.add(zxSkStock);
        }
        //?????????
        ResponseEntity responseEntity = ZxSkStockServiceImpl.reduceZxSkStockPriceChange(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferSalesReturn.setBillStatus("1");
            zxSkStockTransferSalesReturn.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferSalesReturnMapper.checkZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturn);
            //??????????????????????????????????????????????????????????????????
            int flag2 = 0;
            for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnListIsOutNumber) {
                flag2 = zxSkStockTransItemInitialReceiptMapper.updateZxSkStockTransItemReceivingMapperIsOutNumber(zxSkStockTransItemSalesReturn);
                if(NumberUtil.equals(new BigDecimal(flag2),new BigDecimal("0"))){
                    zxSkStockTransItemReceivingMapper.updateZxSkStockTransItemReceivingMapperIsOutNumber(zxSkStockTransItemSalesReturn);
                }
            }
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

    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnNo(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if(StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID()) || zxSkStockTransferSalesReturn.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferSalesReturn.getInOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferSalesReturn.getBusDate());
        int month = DateUtil.month(zxSkStockTransferSalesReturn.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferSalesReturn.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferSalesReturnMapper.getZxSkStockTransferSalesReturnCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ???????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    //????????????ID:inOrgID
    //??????id:whOrgID
    //????????????id,??????id
    //???????????????
    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnOutOrgName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getWhOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // ????????????
        List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList =
                zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnOutOrgNameList(zxSkStockTransferSalesReturn);
        // ??????????????????
        PageInfo<ZxSkStockTransferSalesReturn> p = new PageInfo<>(zxSkStockTransferSalesReturnList);
        return repEntity.okList(zxSkStockTransferSalesReturnList, p.getTotal());
    }

    //????????????id,??????id,????????????id        outOrgID,outOrgName
    //????????? ???????????????/????????? ???   ????????????        resourceID  resourceName
    //??????????????????
    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnResourceName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // ????????????
        List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList =
                zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnResourceNameList(zxSkStockTransferSalesReturn);
        // ??????????????????
        PageInfo<ZxSkStockTransferSalesReturn> p = new PageInfo<>(zxSkStockTransferSalesReturnList);
        return repEntity.okList(zxSkStockTransferSalesReturnList, p.getTotal());
    }

    //todo: ????????????0?????????????????????
    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnResName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (StrUtil.isEmpty(zxSkStockTransferSalesReturn.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getResourceID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // ????????????
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns =
                zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnResNameList(zxSkStockTransferSalesReturn);
        // ??????????????????
        PageInfo<ZxSkStockTransItemSalesReturn> p = new PageInfo<>(zxSkStockTransItemSalesReturns);
        return repEntity.okList(zxSkStockTransItemSalesReturns, p.getTotal());
    }




}
