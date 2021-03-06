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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemTransferOrderMapper;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockTransferTransferOrderMapper;
import com.apih5.service.ZxSkStockTransferTransferOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferTransferOrderService")
public class ZxSkStockTransferTransferOrderServiceImpl implements ZxSkStockTransferTransferOrderService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferTransferOrderMapper zxSkStockTransferTransferOrderMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemTransferOrderMapper zxSkStockTransItemTransferOrderMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkStockTransferTransferOrderListByCondition(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        if (zxSkStockTransferTransferOrder == null) {
            zxSkStockTransferTransferOrder = new ZxSkStockTransferTransferOrder();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);

        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferTransferOrder.setCompanyId("");
            zxSkStockTransferTransferOrder.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID2())){
                zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferTransferOrder.setCompanyId(zxSkStockTransferTransferOrder.getOutOrgID());
            zxSkStockTransferTransferOrder.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID2())){
                zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOutOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID2())){
                zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferTransferOrder.getPage(),zxSkStockTransferTransferOrder.getLimit());
        // ????????????
        List<ZxSkStockTransferTransferOrder> zxSkStockTransferTransferOrderList = zxSkStockTransferTransferOrderMapper.selectByZxSkStockTransferTransferOrderList(zxSkStockTransferTransferOrder);
        //????????????
        for (ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 : zxSkStockTransferTransferOrderList) {
            ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
            zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrders = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
            zxSkStockTransferTransferOrder1.setZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrders);
        }
        //??????
        for (ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 : zxSkStockTransferTransferOrderList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferTransferOrder1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferTransferOrder1.setFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferTransferOrder> p = new PageInfo<>(zxSkStockTransferTransferOrderList);

        return repEntity.okList(zxSkStockTransferTransferOrderList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferTransferOrderDetails(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        if (zxSkStockTransferTransferOrder == null) {
            zxSkStockTransferTransferOrder = new ZxSkStockTransferTransferOrder();
        }
        // ????????????
        ZxSkStockTransferTransferOrder dbZxSkStockTransferTransferOrder = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        // ????????????
        if (dbZxSkStockTransferTransferOrder != null) {
            ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
            zxSkStockTransItemTransferOrder.setBillID(dbZxSkStockTransferTransferOrder.getId());
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrders = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
            dbZxSkStockTransferTransferOrder.setZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrders);

            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferTransferOrder.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferTransferOrder.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferTransferOrder);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferTransferOrder.setId(UuidUtil.generate());
        zxSkStockTransferTransferOrder.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkStockTransferTransferOrder.setBillStatus("0");
        //????????????
        BigDecimal total = new BigDecimal(0);
        //????????????
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransferTransferOrder.getZxSkStockTransItemTransferOrderList();
        if(zxSkStockTransItemTransferOrderList != null && zxSkStockTransItemTransferOrderList.size()>0) {
            for (ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder : zxSkStockTransItemTransferOrderList) {
                //????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemTransferOrder.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemTransferOrder.getOutAmt());
                }
                //stdPrice ->outCostPrice
                //????????????->???????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemTransferOrder.getStdPrice())!=0){
                    zxSkStockTransItemTransferOrder.setOutCostPrice(zxSkStockTransItemTransferOrder.getStdPrice());
                }else {
                    zxSkStockTransItemTransferOrder.setOutCostPrice(new BigDecimal(0));
                }
                //outAmt ->outCostAmt
                //??????->??????????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemTransferOrder.getOutAmt())!=0){
                    zxSkStockTransItemTransferOrder.setOutCostAmt(zxSkStockTransItemTransferOrder.getOutAmt());
                }else {
                    zxSkStockTransItemTransferOrder.setOutCostAmt(new BigDecimal(0));
                }
                zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder.getId());
                zxSkStockTransItemTransferOrder.setId((UuidUtil.generate()));
                zxSkStockTransItemTransferOrder.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemTransferOrderMapper.insert(zxSkStockTransItemTransferOrder);
            }
        }
        //????????????
        List<ZxErpFile> fileList = zxSkStockTransferTransferOrder.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferTransferOrder.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferTransferOrder.setTotalAmt(total);
        int flag = zxSkStockTransferTransferOrderMapper.insert(zxSkStockTransferTransferOrder);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferTransferOrder);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferTransferOrder dbzxSkStockTransferTransferOrder = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if (dbzxSkStockTransferTransferOrder != null && StrUtil.isNotEmpty(dbzxSkStockTransferTransferOrder.getId())) {
           // ??????(1)
           dbzxSkStockTransferTransferOrder.setOrgID(zxSkStockTransferTransferOrder.getOrgID());
           // ??????(2)
           dbzxSkStockTransferTransferOrder.setWhOrgID(zxSkStockTransferTransferOrder.getWhOrgID());
           // ??????ID
           dbzxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOutOrgID());
           // ????????????ID
           dbzxSkStockTransferTransferOrder.setInOrgID(zxSkStockTransferTransferOrder.getInOrgID());
           // ????????????
           dbzxSkStockTransferTransferOrder.setBizType(zxSkStockTransferTransferOrder.getBizType());
           // ????????????
           dbzxSkStockTransferTransferOrder.setBillNo(zxSkStockTransferTransferOrder.getBillNo());
           // ????????????
           dbzxSkStockTransferTransferOrder.setMaterialSource(zxSkStockTransferTransferOrder.getMaterialSource());
           // ????????????
           dbzxSkStockTransferTransferOrder.setPurpose(zxSkStockTransferTransferOrder.getPurpose());
           // ????????????
           dbzxSkStockTransferTransferOrder.setBusDate(zxSkStockTransferTransferOrder.getBusDate());
           // ????????????
           dbzxSkStockTransferTransferOrder.setOutOrgName(zxSkStockTransferTransferOrder.getOutOrgName());
           // ????????????
           dbzxSkStockTransferTransferOrder.setInOrgName(zxSkStockTransferTransferOrder.getInOrgName());
           // ??????/??????
           dbzxSkStockTransferTransferOrder.setInOrOut(zxSkStockTransferTransferOrder.getInOrOut());
           // ????????????id
           dbzxSkStockTransferTransferOrder.setInWhOrgID(zxSkStockTransferTransferOrder.getInWhOrgID());
           // ????????????
           dbzxSkStockTransferTransferOrder.setInWhOrg(zxSkStockTransferTransferOrder.getInWhOrg());
           // ????????????
           dbzxSkStockTransferTransferOrder.setTotalAmt(zxSkStockTransferTransferOrder.getTotalAmt());
           // ?????????(1)
           dbzxSkStockTransferTransferOrder.setOuttransactor(zxSkStockTransferTransferOrder.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferTransferOrder.setIntransactor(zxSkStockTransferTransferOrder.getIntransactor());
           // ???????????????
           dbzxSkStockTransferTransferOrder.setWaretransactor(zxSkStockTransferTransferOrder.getWaretransactor());
           // ?????????
           dbzxSkStockTransferTransferOrder.setBuyer(zxSkStockTransferTransferOrder.getBuyer());
           // ?????????
           dbzxSkStockTransferTransferOrder.setConsignee(zxSkStockTransferTransferOrder.getConsignee());
           // ?????????
           dbzxSkStockTransferTransferOrder.setAuditor(zxSkStockTransferTransferOrder.getAuditor());
           // ?????????
           dbzxSkStockTransferTransferOrder.setVoucherNo(zxSkStockTransferTransferOrder.getVoucherNo());
           // ?????????
           dbzxSkStockTransferTransferOrder.setContractNo(zxSkStockTransferTransferOrder.getContractNo());
           // ?????????
           dbzxSkStockTransferTransferOrder.setInvoiceNo(zxSkStockTransferTransferOrder.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferTransferOrder.setBillType(zxSkStockTransferTransferOrder.getBillType());
           // ????????????
           dbzxSkStockTransferTransferOrder.setBillFlag(zxSkStockTransferTransferOrder.getBillFlag());
           // ????????????
           dbzxSkStockTransferTransferOrder.setBillStatus(zxSkStockTransferTransferOrder.getBillStatus());
           // ?????????
           dbzxSkStockTransferTransferOrder.setReporter(zxSkStockTransferTransferOrder.getReporter());
           // ????????????
           dbzxSkStockTransferTransferOrder.setDeductAmtType(zxSkStockTransferTransferOrder.getDeductAmtType());
           // ??????
           dbzxSkStockTransferTransferOrder.setRemark(zxSkStockTransferTransferOrder.getRemark());
           // ??????
           dbzxSkStockTransferTransferOrder.setCombProp(zxSkStockTransferTransferOrder.getCombProp());
           // ????????????ID
           dbzxSkStockTransferTransferOrder.setResourceID(zxSkStockTransferTransferOrder.getResourceID());
           // ????????????
           dbzxSkStockTransferTransferOrder.setResourceName(zxSkStockTransferTransferOrder.getResourceName());
           // ?????????????????????????????????
           dbzxSkStockTransferTransferOrder.setInvoiceNum(zxSkStockTransferTransferOrder.getInvoiceNum());
           // ??????(3)
           dbzxSkStockTransferTransferOrder.setWarehouseName(zxSkStockTransferTransferOrder.getWarehouseName());
           // ??????id
           dbzxSkStockTransferTransferOrder.setCompanyId(zxSkStockTransferTransferOrder.getCompanyId());
           // ????????????
           dbzxSkStockTransferTransferOrder.setCompanyName(zxSkStockTransferTransferOrder.getCompanyName());
           // ??????
           dbzxSkStockTransferTransferOrder.setModifyUserInfo(userKey, realName);

            //???????????????
            ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
            zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder.getId());
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrders = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
            if(zxSkStockTransItemTransferOrders != null && zxSkStockTransItemTransferOrders.size()>0) {
                zxSkStockTransItemTransferOrder.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemTransferOrderMapper.batchDeleteUpdateZxSkStockTransItemTransferOrder(zxSkStockTransItemTransferOrders, zxSkStockTransItemTransferOrder);
            }
            //??????list
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransferTransferOrder.getZxSkStockTransItemTransferOrderList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemTransferOrderList != null && zxSkStockTransItemTransferOrderList.size()>0) {
                for(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder1 : zxSkStockTransItemTransferOrderList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemTransferOrder1.getOutAmt());
                    zxSkStockTransItemTransferOrder1.setId(UuidUtil.generate());
                    zxSkStockTransItemTransferOrder1.setBillID(zxSkStockTransferTransferOrder.getId());
                    zxSkStockTransItemTransferOrder1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemTransferOrderMapper.insert(zxSkStockTransItemTransferOrder1);
                }
            }
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferTransferOrder.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkStockTransferTransferOrder.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferTransferOrder.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferTransferOrder.setTotalAmt(total);
            flag = zxSkStockTransferTransferOrderMapper.updateByPrimaryKey(dbzxSkStockTransferTransferOrder);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferTransferOrder);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferTransferOrder(List<ZxSkStockTransferTransferOrder> zxSkStockTransferTransferOrderList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferTransferOrderList != null && zxSkStockTransferTransferOrderList.size() > 0) {
            for (ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder : zxSkStockTransferTransferOrderList) {
                // ????????????
                ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
                zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder.getId());
                List<ZxSkStockTransItemTransferOrder> deleteZxSkStockTransItems = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
                if(deleteZxSkStockTransItems != null && deleteZxSkStockTransItems.size()>0) {
                    zxSkStockTransItemTransferOrder.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemTransferOrderMapper.batchDeleteUpdateZxSkStockTransItemTransferOrder(deleteZxSkStockTransItems, zxSkStockTransItemTransferOrder);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferTransferOrder.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder = new ZxSkStockTransferTransferOrder();
           zxSkStockTransferTransferOrder.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferTransferOrderMapper.batchDeleteUpdateZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrderList, zxSkStockTransferTransferOrder);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferTransferOrderList);
        }
    }

    //??????????????? (?????? ???????????????)
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        //??????????????????
        ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if(!StrUtil.equals(zxSkStockTransferTransferOrder1.getBillStatus(), "0")) {
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        }
        //????????????
        ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        if(CollUtil.isEmpty(zxSkStockTransItemTransferOrderList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderListNew = new ArrayList<>();
        zxSkStockTransItemTransferOrderList.parallelStream()
                .collect(Collectors.groupingBy(o ->
                        (o.getResID()+o.getStdPrice()), Collectors.toList())).forEach(
                (id,transfer) ->{
                    transfer.stream().reduce((a,b)-> new ZxSkStockTransItemTransferOrder(
                            a.getResID(),
                            a.getResCode(),
                            a.getResName(),
                            a.getSpec(),
                            a.getResUnit(),
                            a.getStdPrice(),
                            CalcUtils.calcAdd(a.getOutQty(),b.getOutQty()),
                            CalcUtils.calcAdd(a.getOutAmt(),b.getOutAmt())
                    )).ifPresent(zxSkStockTransItemTransferOrderListNew::add);
                }
        );
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemTransferOrder item : zxSkStockTransItemTransferOrderListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferTransferOrder1.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferTransferOrder1.getOutOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferTransferOrder1.getWhOrgID());
            //????????????resourceId
            zxSkStock.setResourceId(zxSkStockTransferTransferOrder1.getResourceID());
            //??????????????????resourceName
            zxSkStock.setResourceName(zxSkStockTransferTransferOrder1.getResourceName());
            //??????Id
            zxSkStock.setResID(item.getResID());
            //????????????
            zxSkStock.setResCode(item.getResCode());
            //????????????
            zxSkStock.setResName(item.getResName());
            //????????????spec
            zxSkStock.setSpec(item.getSpec());
            //??????unit
            zxSkStock.setUnit(item.getResUnit());
            //????????????
            zxSkStock.setStockQty(item.getOutQty());
            //????????????
            zxSkStock.setStockPrice(item.getStdPrice());
            //????????????
            zxSkStock.setStockAmt(item.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.reduceZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferTransferOrder1.setBillStatus("1");
            zxSkStockTransferTransferOrder1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferTransferOrderMapper.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder1);
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

    //??????????????? (?????? ????????????,????????????,????????????)
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferTransferOrderIn(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        //??????????????????
        ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if(!StrUtil.equals(zxSkStockTransferTransferOrder1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        //???????????????
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemTransferOrder item : zxSkStockTransItemTransferOrderList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferTransferOrder1.getCompanyId());
            //????????????ID
            zxSkStock.setOrgID(zxSkStockTransferTransferOrder1.getInOrgID());
            //????????????ID
            zxSkStock.setWhOrgID(zxSkStockTransferTransferOrder1.getInWhOrgID());
            //????????????resourceId
            zxSkStock.setResourceId(zxSkStockTransferTransferOrder1.getResourceID());
            //??????????????????resourceName
            zxSkStock.setResourceName(zxSkStockTransferTransferOrder1.getResourceName());
            //??????Id
            zxSkStock.setResID(item.getResID());
            //????????????
            zxSkStock.setResCode(item.getResCode());
            //????????????
            zxSkStock.setResName(item.getResName());
            //????????????spec
            zxSkStock.setSpec(item.getSpec());
            //??????unit
            zxSkStock.setUnit(item.getResUnit());
            //????????????
            zxSkStock.setStockQty(item.getOutQty());
            //????????????(????????????)
            zxSkStock.setStockAmt(item.getTransOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferTransferOrder1.setBillStatus("2");
            zxSkStockTransferTransferOrder1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferTransferOrderMapper.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder1);
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

    //??????????????? (?????? ????????????,????????????,????????????)
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferTransferOrderOut(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        //??????????????????
        ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if(!StrUtil.equals(zxSkStockTransferTransferOrder1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????
        ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemTransferOrder item : zxSkStockTransItemTransferOrderList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferTransferOrder1.getCompanyId());
            //????????????ID
            zxSkStock.setOrgID(zxSkStockTransferTransferOrder1.getOutOrgID());
            //????????????ID
            zxSkStock.setWhOrgID(zxSkStockTransferTransferOrder1.getWhOrgID());
            //????????????resourceId
            zxSkStock.setResourceId(zxSkStockTransferTransferOrder1.getResourceID());
            //??????????????????resourceName
            zxSkStock.setResourceName(zxSkStockTransferTransferOrder1.getResourceName());
            //??????Id
            zxSkStock.setResID(item.getResID());
            //????????????
            zxSkStock.setResCode(item.getResCode());
            //????????????
            zxSkStock.setResName(item.getResName());
            //????????????spec
            zxSkStock.setSpec(item.getSpec());
            //??????unit
            zxSkStock.setUnit(item.getResUnit());
            //????????????
            zxSkStock.setStockQty(item.getOutQty());
            //????????????(????????????)
            zxSkStock.setStockAmt(item.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferTransferOrder1.setBillStatus("3");
            zxSkStockTransferTransferOrder1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferTransferOrderMapper.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder1);
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

    //???????????????+??????????????????+?????????+??????+????????????+??????
    @Override
    public ResponseEntity getZxSkStockTransferTransferOrderNo(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        if(StrUtil.isEmpty(zxSkStockTransferTransferOrder.getOutOrgID()) || zxSkStockTransferTransferOrder.getBusDate() == null){
            return repEntity.ok(null);
        }
        String orgID = zxSkStockTransferTransferOrder.getOutOrgID();
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferTransferOrder.getBusDate());
        int month = DateUtil.month(zxSkStockTransferTransferOrder.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferTransferOrder.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferTransferOrderMapper.getZxSkStockTransferTransferOrderCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ???????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkStockTransferOrderReceiveOrg(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID()) && StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getContrStatus())){
            ZxCtContract zxCtContract = new ZxCtContract();
            zxCtContract.setOrgID(zxSkStockTransferTransferOrder.getOrgID());
            zxCtContract.setContrStatus(zxSkStockTransferTransferOrder.getContrStatus());
            HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
            // ??????????????????
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                    || StrUtil.equals("admin", userId)) {
                zxCtContract.setCompanyId("");
                zxCtContract.setOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                // ????????????????????????
                zxCtContract.setCompanyId(zxCtContract.getOrgID());
                zxCtContract.setOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                // ?????????????????????id ????????????.
                List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
                ZxCtContract zxCtContract1 = zxCtContracts.get(0);
                if(ObjectUtil.isNotEmpty(zxCtContract1)){
                    zxCtContract.setCompanyId(zxCtContract1.getCompanyId());
                    zxCtContract.setOrgID("");
                }else {
                    return repEntity.ok(new ArrayList<>());
                }
            }
            List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
            return repEntity.ok(zxCtContractList);
        }else {
            return repEntity.ok(new ArrayList<>());
        }
    }
}
