package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
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
import com.apih5.service.ZxSkStockTransferDiskFillingStorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferDiskFillingStorageService")
public class ZxSkStockTransferDiskFillingStorageServiceImpl implements ZxSkStockTransferDiskFillingStorageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferDiskFillingStorageMapper zxSkStockTransferDiskFillingStorageMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemDiskFillingStorageMapper zxSkStockTransItemDiskFillingStorageMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxSkWarehouseMapper zxSkWarehouseMapper;

    @Override
    public ResponseEntity getZxSkStockTransferDiskFillingStorageListByCondition(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        if (zxSkStockTransferDiskFillingStorage == null) {
            zxSkStockTransferDiskFillingStorage = new ZxSkStockTransferDiskFillingStorage();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferDiskFillingStorage.setCompanyId("");
            zxSkStockTransferDiskFillingStorage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferDiskFillingStorage.setCompanyId(zxSkStockTransferDiskFillingStorage.getProjectId());
            zxSkStockTransferDiskFillingStorage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferDiskFillingStorage.setProjectId(zxSkStockTransferDiskFillingStorage.getProjectId());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferDiskFillingStorage.getPage(),zxSkStockTransferDiskFillingStorage.getLimit());
        // ????????????
        List<ZxSkStockTransferDiskFillingStorage> zxSkStockTransferDiskFillingStorageList = zxSkStockTransferDiskFillingStorageMapper.selectByZxSkStockTransferDiskFillingStorageList(zxSkStockTransferDiskFillingStorage);
        //????????????
        for (ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage1 : zxSkStockTransferDiskFillingStorageList) {
            ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
            zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage1.getId());
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
            zxSkStockTransferDiskFillingStorage1.setZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorages);
        }
        //??????
        for (ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage1 : zxSkStockTransferDiskFillingStorageList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferDiskFillingStorage1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferDiskFillingStorage1.setFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferDiskFillingStorage> p = new PageInfo<>(zxSkStockTransferDiskFillingStorageList);

        return repEntity.okList(zxSkStockTransferDiskFillingStorageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferDiskFillingStorageDetails(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        if (zxSkStockTransferDiskFillingStorage == null) {
            zxSkStockTransferDiskFillingStorage = new ZxSkStockTransferDiskFillingStorage();
        }
        // ????????????
        ZxSkStockTransferDiskFillingStorage dbZxSkStockTransferDiskFillingStorage = zxSkStockTransferDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransferDiskFillingStorage.getId());
        // ????????????
        if (dbZxSkStockTransferDiskFillingStorage != null) {
            ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
            zxSkStockTransItemDiskFillingStorage.setBillID(dbZxSkStockTransferDiskFillingStorage.getId());
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
            dbZxSkStockTransferDiskFillingStorage.setZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorages);

            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferDiskFillingStorage.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferDiskFillingStorage.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferDiskFillingStorage);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferDiskFillingStorage.setId(UuidUtil.generate());
        zxSkStockTransferDiskFillingStorage.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkStockTransferDiskFillingStorage.setBillStatus("0");
        //????????????
        BigDecimal total = new BigDecimal(0);
        //????????????
        List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList = zxSkStockTransferDiskFillingStorage.getZxSkStockTransItemDiskFillingStorageList();
        if(zxSkStockTransItemDiskFillingStorageList != null && zxSkStockTransItemDiskFillingStorageList.size()>0) {
            for (ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage : zxSkStockTransItemDiskFillingStorageList) {
                //????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemDiskFillingStorage.getInAmtAll())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemDiskFillingStorage.getInAmtAll());
                }
                zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage.getId());
                zxSkStockTransItemDiskFillingStorage.setId((UuidUtil.generate()));
                zxSkStockTransItemDiskFillingStorage.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemDiskFillingStorageMapper.insert(zxSkStockTransItemDiskFillingStorage);
            }
        }
        //????????????
        List<ZxErpFile> fileList = zxSkStockTransferDiskFillingStorage.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferDiskFillingStorage.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferDiskFillingStorage.setTotalAmt(total);
        int flag = zxSkStockTransferDiskFillingStorageMapper.insert(zxSkStockTransferDiskFillingStorage);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferDiskFillingStorage);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferDiskFillingStorage dbzxSkStockTransferDiskFillingStorage = zxSkStockTransferDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransferDiskFillingStorage.getId());
        if (dbzxSkStockTransferDiskFillingStorage != null && StrUtil.isNotEmpty(dbzxSkStockTransferDiskFillingStorage.getId())) {
           // ??????(1)
           dbzxSkStockTransferDiskFillingStorage.setOrgID(zxSkStockTransferDiskFillingStorage.getOrgID());
           // ??????(2)
           dbzxSkStockTransferDiskFillingStorage.setWhOrgID(zxSkStockTransferDiskFillingStorage.getWhOrgID());
           // ??????ID
           dbzxSkStockTransferDiskFillingStorage.setOutOrgID(zxSkStockTransferDiskFillingStorage.getOutOrgID());
           // ??????ID
           dbzxSkStockTransferDiskFillingStorage.setInOrgID(zxSkStockTransferDiskFillingStorage.getInOrgID());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setBizType(zxSkStockTransferDiskFillingStorage.getBizType());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setBillNo(zxSkStockTransferDiskFillingStorage.getBillNo());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setMaterialSource(zxSkStockTransferDiskFillingStorage.getMaterialSource());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setPurpose(zxSkStockTransferDiskFillingStorage.getPurpose());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setBusDate(zxSkStockTransferDiskFillingStorage.getBusDate());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setOutOrgName(zxSkStockTransferDiskFillingStorage.getOutOrgName());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setInOrgName(zxSkStockTransferDiskFillingStorage.getInOrgName());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setTotalAmt(zxSkStockTransferDiskFillingStorage.getTotalAmt());
           // ?????????(1)
           dbzxSkStockTransferDiskFillingStorage.setOuttransactor(zxSkStockTransferDiskFillingStorage.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferDiskFillingStorage.setIntransactor(zxSkStockTransferDiskFillingStorage.getIntransactor());
           // ???????????????
           dbzxSkStockTransferDiskFillingStorage.setWaretransactor(zxSkStockTransferDiskFillingStorage.getWaretransactor());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setBuyer(zxSkStockTransferDiskFillingStorage.getBuyer());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setConsignee(zxSkStockTransferDiskFillingStorage.getConsignee());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setAuditor(zxSkStockTransferDiskFillingStorage.getAuditor());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setVoucherNo(zxSkStockTransferDiskFillingStorage.getVoucherNo());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setContractNo(zxSkStockTransferDiskFillingStorage.getContractNo());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setInvoiceNo(zxSkStockTransferDiskFillingStorage.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setBillType(zxSkStockTransferDiskFillingStorage.getBillType());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setBillFlag(zxSkStockTransferDiskFillingStorage.getBillFlag());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setBillStatus(zxSkStockTransferDiskFillingStorage.getBillStatus());
           // ?????????
           dbzxSkStockTransferDiskFillingStorage.setReporter(zxSkStockTransferDiskFillingStorage.getReporter());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setDeductAmtType(zxSkStockTransferDiskFillingStorage.getDeductAmtType());
           // ??????
           dbzxSkStockTransferDiskFillingStorage.setRemark(zxSkStockTransferDiskFillingStorage.getRemark());
           // ?????????????????????????????????
           dbzxSkStockTransferDiskFillingStorage.setInvoiceNum(zxSkStockTransferDiskFillingStorage.getInvoiceNum());
           // ??????(3)
           dbzxSkStockTransferDiskFillingStorage.setWarehouseName(zxSkStockTransferDiskFillingStorage.getWarehouseName());
           // ??????id
           dbzxSkStockTransferDiskFillingStorage.setProjectId(zxSkStockTransferDiskFillingStorage.getProjectId());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setProjectName(zxSkStockTransferDiskFillingStorage.getProjectName());
           // ??????id
           dbzxSkStockTransferDiskFillingStorage.setCompanyId(zxSkStockTransferDiskFillingStorage.getCompanyId());
           // ????????????
           dbzxSkStockTransferDiskFillingStorage.setCompanyName(zxSkStockTransferDiskFillingStorage.getCompanyName());
           // ??????
           dbzxSkStockTransferDiskFillingStorage.setModifyUserInfo(userKey, realName);


            //???????????????
            ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
            zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage.getId());
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
            if(zxSkStockTransItemDiskFillingStorages != null && zxSkStockTransItemDiskFillingStorages.size()>0) {
                zxSkStockTransItemDiskFillingStorage.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemDiskFillingStorageMapper.batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorages, zxSkStockTransItemDiskFillingStorage);
            }
            //??????list
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList = zxSkStockTransferDiskFillingStorage.getZxSkStockTransItemDiskFillingStorageList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemDiskFillingStorageList != null && zxSkStockTransItemDiskFillingStorageList.size()>0) {
                for(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage1 : zxSkStockTransItemDiskFillingStorageList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemDiskFillingStorage1.getInAmtAll());
                    zxSkStockTransItemDiskFillingStorage1.setId(UuidUtil.generate());
                    zxSkStockTransItemDiskFillingStorage1.setBillID(zxSkStockTransferDiskFillingStorage.getId());
                    zxSkStockTransItemDiskFillingStorage1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemDiskFillingStorageMapper.insert(zxSkStockTransItemDiskFillingStorage1);
                }
            }
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferDiskFillingStorage.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkStockTransferDiskFillingStorage.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferDiskFillingStorage.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferDiskFillingStorage.setTotalAmt(total);
            flag = zxSkStockTransferDiskFillingStorageMapper.updateByPrimaryKey(dbzxSkStockTransferDiskFillingStorage);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferDiskFillingStorage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferDiskFillingStorage(List<ZxSkStockTransferDiskFillingStorage> zxSkStockTransferDiskFillingStorageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferDiskFillingStorageList != null && zxSkStockTransferDiskFillingStorageList.size() > 0) {
            for (ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage : zxSkStockTransferDiskFillingStorageList) {
                // ????????????
                ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
                zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage.getId());
                List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
                if(zxSkStockTransItemDiskFillingStorages != null && zxSkStockTransItemDiskFillingStorages.size()>0) {
                    zxSkStockTransItemDiskFillingStorage.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemDiskFillingStorageMapper.batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorages, zxSkStockTransItemDiskFillingStorage);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferDiskFillingStorage.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage = new ZxSkStockTransferDiskFillingStorage();
           zxSkStockTransferDiskFillingStorage.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferDiskFillingStorageMapper.batchDeleteUpdateZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorageList, zxSkStockTransferDiskFillingStorage);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferDiskFillingStorageList);
        }
    }

    //???????????????
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        //??????????????????
        ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage1 = zxSkStockTransferDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransferDiskFillingStorage.getId());
        if(StrUtil.equals(zxSkStockTransferDiskFillingStorage1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        ZxSkStockTransItemDiskFillingStorage dbzxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
        dbzxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage1.getId());
        List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(dbzxSkStockTransItemDiskFillingStorage);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage : zxSkStockTransItemDiskFillingStorageList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferDiskFillingStorage1.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferDiskFillingStorage1.getInOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferDiskFillingStorage1.getWhOrgID());
            //??????Id
            zxSkStock.setResID(zxSkStockTransItemDiskFillingStorage.getResID());
            //????????????
            zxSkStock.setResCode(zxSkStockTransItemDiskFillingStorage.getResCode());
            //????????????
            zxSkStock.setResName(zxSkStockTransItemDiskFillingStorage.getResName());
            //????????????spec
            zxSkStock.setSpec(zxSkStockTransItemDiskFillingStorage.getSpec());
            //??????unit
            zxSkStock.setUnit(zxSkStockTransItemDiskFillingStorage.getResUnit());
            //??????
            zxSkStock.setStockQty(zxSkStockTransItemDiskFillingStorage.getInQty());
            //??????
            zxSkStock.setStockAmt(zxSkStockTransItemDiskFillingStorage.getInAmtAll());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.addZxSkStockNumTotalChange(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferDiskFillingStorage1.setBillStatus("1");
            zxSkStockTransferDiskFillingStorage1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferDiskFillingStorageMapper.checkZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorage1);
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

    //?????????????????? ??????????????? ??????-??????-?????????
    //??? HWGS???T???-HAN-WYTJ-04??????????????? 2019-11-001 ???  ?????????-001?????????
    @Override
    public ResponseEntity getZxSkStockTransferDiskFillingStorageNo(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        if(StrUtil.isEmpty(zxSkStockTransferDiskFillingStorage.getWhOrgID()) || zxSkStockTransferDiskFillingStorage.getBusDate() == null){
            return repEntity.ok(null);
        }
        //????????????id????????? ??????id
        ZxSkWarehouse zxSkWarehouse = new ZxSkWarehouse();
        zxSkWarehouse.setId(zxSkStockTransferDiskFillingStorage.getWhOrgID());
        List<ZxSkWarehouse> zxSkWarehouses = zxSkWarehouseMapper.selectByZxSkWarehouseList(zxSkWarehouse);
        if(CollectionUtil.isEmpty(zxSkWarehouses)){
            return repEntity.ok(new ArrayList<>());
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkWarehouses.get(0).getParentOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferDiskFillingStorage.getBusDate());
        int month = DateUtil.month(zxSkStockTransferDiskFillingStorage.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferDiskFillingStorage.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferDiskFillingStorageMapper.getzxSkStockTransferDiskFillingStorageCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ??????????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }


}
