package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkStockTransferDishedOutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferDishedOutService")
public class ZxSkStockTransferDishedOutServiceImpl implements ZxSkStockTransferDishedOutService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferDishedOutMapper zxSkStockTransferDishedOutMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemDishedOutMapper zxSkStockTransItemDishedOutMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxSkWarehouseMapper zxSkWarehouseMapper;

    @Override
    public ResponseEntity getZxSkStockTransferDishedOutListByCondition(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        if (zxSkStockTransferDishedOut == null) {
            zxSkStockTransferDishedOut = new ZxSkStockTransferDishedOut();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferDishedOut.setCompanyId("");
            zxSkStockTransferDishedOut.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferDishedOut.setCompanyId(zxSkStockTransferDishedOut.getProjectId());
            zxSkStockTransferDishedOut.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferDishedOut.setProjectId(zxSkStockTransferDishedOut.getProjectId());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferDishedOut.getPage(),zxSkStockTransferDishedOut.getLimit());
        // ????????????
        List<ZxSkStockTransferDishedOut> zxSkStockTransferDishedOutList = zxSkStockTransferDishedOutMapper.selectByZxSkStockTransferDishedOutList(zxSkStockTransferDishedOut);
        //????????????
        for (ZxSkStockTransferDishedOut zxSkStockTransferDishedOut1 : zxSkStockTransferDishedOutList) {
            ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
            zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut1.getId());
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
            zxSkStockTransferDishedOut1.setZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOuts);
        }
        //??????
        for (ZxSkStockTransferDishedOut zxSkStockTransferDishedOut1 : zxSkStockTransferDishedOutList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferDishedOut1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferDishedOut1.setFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferDishedOut> p = new PageInfo<>(zxSkStockTransferDishedOutList);

        return repEntity.okList(zxSkStockTransferDishedOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferDishedOutDetails(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        if (zxSkStockTransferDishedOut == null) {
            zxSkStockTransferDishedOut = new ZxSkStockTransferDishedOut();
        }
        // ????????????
        ZxSkStockTransferDishedOut dbZxSkStockTransferDishedOut = zxSkStockTransferDishedOutMapper.selectByPrimaryKey(zxSkStockTransferDishedOut.getId());
        // ????????????
        if (dbZxSkStockTransferDishedOut != null) {
            ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
            zxSkStockTransItemDishedOut.setBillID(dbZxSkStockTransferDishedOut.getId());
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
            dbZxSkStockTransferDishedOut.setZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOuts);

            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferDishedOut.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferDishedOut.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferDishedOut);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferDishedOut.setId(UuidUtil.generate());
        zxSkStockTransferDishedOut.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkStockTransferDishedOut.setBillStatus("0");
        //????????????
        BigDecimal total = new BigDecimal(0);
        //????????????
        List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList = zxSkStockTransferDishedOut.getZxSkStockTransItemDishedOutList();
        if(zxSkStockTransItemDishedOutList != null && zxSkStockTransItemDishedOutList.size()>0) {
            for (ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut : zxSkStockTransItemDishedOutList) {
                //????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemDishedOut.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemDishedOut.getOutAmt());
                }
                zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut.getId());
                zxSkStockTransItemDishedOut.setId((UuidUtil.generate()));
                zxSkStockTransItemDishedOut.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemDishedOutMapper.insert(zxSkStockTransItemDishedOut);
            }
        }
        //????????????
        List<ZxErpFile> fileList = zxSkStockTransferDishedOut.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferDishedOut.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferDishedOut.setTotalAmt(total);
        int flag = zxSkStockTransferDishedOutMapper.insert(zxSkStockTransferDishedOut);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferDishedOut);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferDishedOut dbzxSkStockTransferDishedOut = zxSkStockTransferDishedOutMapper.selectByPrimaryKey(zxSkStockTransferDishedOut.getId());
        if (dbzxSkStockTransferDishedOut != null && StrUtil.isNotEmpty(dbzxSkStockTransferDishedOut.getId())) {
           // ??????(1)
           dbzxSkStockTransferDishedOut.setOrgID(zxSkStockTransferDishedOut.getOrgID());
           // ??????(2)
           dbzxSkStockTransferDishedOut.setWhOrgID(zxSkStockTransferDishedOut.getWhOrgID());
           // ??????ID
           dbzxSkStockTransferDishedOut.setOutOrgID(zxSkStockTransferDishedOut.getOutOrgID());
           // ??????ID
           dbzxSkStockTransferDishedOut.setInOrgID(zxSkStockTransferDishedOut.getInOrgID());
           // ????????????
           dbzxSkStockTransferDishedOut.setBizType(zxSkStockTransferDishedOut.getBizType());
           // ????????????
           dbzxSkStockTransferDishedOut.setBillNo(zxSkStockTransferDishedOut.getBillNo());
           // ????????????
           dbzxSkStockTransferDishedOut.setMaterialSource(zxSkStockTransferDishedOut.getMaterialSource());
           // ????????????
           dbzxSkStockTransferDishedOut.setPurpose(zxSkStockTransferDishedOut.getPurpose());
           // ????????????
           dbzxSkStockTransferDishedOut.setBusDate(zxSkStockTransferDishedOut.getBusDate());
           // ????????????
           dbzxSkStockTransferDishedOut.setOutOrgName(zxSkStockTransferDishedOut.getOutOrgName());
           // ????????????
           dbzxSkStockTransferDishedOut.setInOrgName(zxSkStockTransferDishedOut.getInOrgName());
           // ????????????
           dbzxSkStockTransferDishedOut.setTotalAmt(zxSkStockTransferDishedOut.getTotalAmt());
           // ?????????(1)
           dbzxSkStockTransferDishedOut.setOuttransactor(zxSkStockTransferDishedOut.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferDishedOut.setIntransactor(zxSkStockTransferDishedOut.getIntransactor());
           // ???????????????
           dbzxSkStockTransferDishedOut.setWaretransactor(zxSkStockTransferDishedOut.getWaretransactor());
           // ?????????
           dbzxSkStockTransferDishedOut.setBuyer(zxSkStockTransferDishedOut.getBuyer());
           // ?????????
           dbzxSkStockTransferDishedOut.setConsignee(zxSkStockTransferDishedOut.getConsignee());
           // ?????????
           dbzxSkStockTransferDishedOut.setAuditor(zxSkStockTransferDishedOut.getAuditor());
           // ?????????
           dbzxSkStockTransferDishedOut.setVoucherNo(zxSkStockTransferDishedOut.getVoucherNo());
           // ?????????
           dbzxSkStockTransferDishedOut.setContractNo(zxSkStockTransferDishedOut.getContractNo());
           // ?????????
           dbzxSkStockTransferDishedOut.setInvoiceNo(zxSkStockTransferDishedOut.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferDishedOut.setBillType(zxSkStockTransferDishedOut.getBillType());
           // ????????????
           dbzxSkStockTransferDishedOut.setBillFlag(zxSkStockTransferDishedOut.getBillFlag());
           // ????????????
           dbzxSkStockTransferDishedOut.setBillStatus(zxSkStockTransferDishedOut.getBillStatus());
           // ?????????
           dbzxSkStockTransferDishedOut.setReporter(zxSkStockTransferDishedOut.getReporter());
           // ????????????
           dbzxSkStockTransferDishedOut.setDeductAmtType(zxSkStockTransferDishedOut.getDeductAmtType());
           // ??????
           dbzxSkStockTransferDishedOut.setRemark(zxSkStockTransferDishedOut.getRemark());
           // ?????????????????????????????????
           dbzxSkStockTransferDishedOut.setInvoiceNum(zxSkStockTransferDishedOut.getInvoiceNum());
           // ??????(3)
           dbzxSkStockTransferDishedOut.setWarehouseName(zxSkStockTransferDishedOut.getWarehouseName());
           // ??????id
           dbzxSkStockTransferDishedOut.setProjectId(zxSkStockTransferDishedOut.getProjectId());
           // ????????????
           dbzxSkStockTransferDishedOut.setProjectName(zxSkStockTransferDishedOut.getProjectName());
           // ??????id
           dbzxSkStockTransferDishedOut.setCompanyId(zxSkStockTransferDishedOut.getCompanyId());
           // ????????????
           dbzxSkStockTransferDishedOut.setCompanyName(zxSkStockTransferDishedOut.getCompanyName());
           // ??????
           dbzxSkStockTransferDishedOut.setModifyUserInfo(userKey, realName);


            //???????????????
            ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
            zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut.getId());
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
            if(zxSkStockTransItemDishedOuts != null && zxSkStockTransItemDishedOuts.size()>0) {
                zxSkStockTransItemDishedOut.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemDishedOutMapper.batchDeleteUpdateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOuts, zxSkStockTransItemDishedOut);
            }

            //??????list
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList = zxSkStockTransferDishedOut.getZxSkStockTransItemDishedOutList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemDishedOutList != null && zxSkStockTransItemDishedOutList.size()>0) {
                for(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut1 : zxSkStockTransItemDishedOutList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemDishedOut1.getOutAmt());
                    zxSkStockTransItemDishedOut1.setId(UuidUtil.generate());
                    zxSkStockTransItemDishedOut1.setBillID(zxSkStockTransferDishedOut.getId());
                    zxSkStockTransItemDishedOut1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemDishedOutMapper.insert(zxSkStockTransItemDishedOut1);
                }
            }
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferDishedOut.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkStockTransferDishedOut.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferDishedOut.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferDishedOut.setTotalAmt(total);
            flag = zxSkStockTransferDishedOutMapper.updateByPrimaryKey(dbzxSkStockTransferDishedOut);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferDishedOut);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferDishedOut(List<ZxSkStockTransferDishedOut> zxSkStockTransferDishedOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferDishedOutList != null && zxSkStockTransferDishedOutList.size() > 0) {
            for (ZxSkStockTransferDishedOut zxSkStockTransferDishedOut : zxSkStockTransferDishedOutList) {
                // ????????????
                ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
                zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut.getId());
                List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
                if(zxSkStockTransItemDishedOuts != null && zxSkStockTransItemDishedOuts.size()>0) {
                    zxSkStockTransItemDishedOut.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemDishedOutMapper.batchDeleteUpdateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOuts, zxSkStockTransItemDishedOut);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferDishedOut.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferDishedOut zxSkStockTransferDishedOut = new ZxSkStockTransferDishedOut();
           zxSkStockTransferDishedOut.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferDishedOutMapper.batchDeleteUpdateZxSkStockTransferDishedOut(zxSkStockTransferDishedOutList, zxSkStockTransferDishedOut);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferDishedOutList);
        }
    }


    //?????????????????? ??????????????? ??????-??????-?????????
    //??? HWGS???T???-HAN-WYTJ-04??????????????? 2019-11-001 ???  ?????????-001?????????
    @Override
    public ResponseEntity getZxSkStockTransferDishedOutNo(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        if(StrUtil.isEmpty(zxSkStockTransferDishedOut.getWhOrgID()) || zxSkStockTransferDishedOut.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxSkWarehouse zxSkWarehouse = new ZxSkWarehouse();
        zxSkWarehouse.setId(zxSkStockTransferDishedOut.getWhOrgID());
        List<ZxSkWarehouse> zxSkWarehouses = zxSkWarehouseMapper.selectByZxSkWarehouseList(zxSkWarehouse);
        if(zxSkWarehouses==null){
            return repEntity.ok(new ArrayList<>());
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkWarehouses.get(0).getParentOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferDishedOut.getBusDate());
        int month = DateUtil.month(zxSkStockTransferDishedOut.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferDishedOut.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferDishedOutMapper.getZxSkStockTransferDishedOutCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ??????????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    @Override
    public synchronized ResponseEntity checkZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        //????????????
        ZxSkStockTransferDishedOut zxSkStockTransferDishedOut1 = zxSkStockTransferDishedOutMapper.selectByPrimaryKey(zxSkStockTransferDishedOut.getId());
        if(StrUtil.equals(zxSkStockTransferDishedOut1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkStockTransItemDishedOut dbzxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
        dbzxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut1.getId());
        List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(dbzxSkStockTransItemDishedOut);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut : zxSkStockTransItemDishedOutList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferDishedOut1.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferDishedOut1.getOutOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferDishedOut1.getWhOrgID());
            //??????Id
            zxSkStock.setResID(zxSkStockTransItemDishedOut.getResID());
            //????????????
            zxSkStock.setResCode(zxSkStockTransItemDishedOut.getResCode());
            //????????????
            zxSkStock.setResName(zxSkStockTransItemDishedOut.getResName());
            //????????????spec
            zxSkStock.setSpec(zxSkStockTransItemDishedOut.getSpec());
            //??????unit
            zxSkStock.setUnit(zxSkStockTransItemDishedOut.getResUnit());
            //??????
            zxSkStock.setStockQty(zxSkStockTransItemDishedOut.getOutQty());
            //??????
            zxSkStock.setStockAmt(zxSkStockTransItemDishedOut.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.reduceZxSkStockNumTotalChange(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferDishedOut1.setBillStatus("1");
            zxSkStockTransferDishedOut1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferDishedOutMapper.checkZxSkStockTransferDishedOut(zxSkStockTransferDishedOut1);
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
