package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.apih5.service.ZxSkStockTransferWithdrawalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferWithdrawalService")
public class ZxSkStockTransferWithdrawalServiceImpl implements ZxSkStockTransferWithdrawalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferWithdrawalMapper zxSkStockTransferWithdrawalMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemWithdrawalMapper zxSkStockTransItemWithdrawalMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private ZxSkStockTransferMaterialRequisitionMapper zxSkStockTransferMaterialRequisitionMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemMaterialRequisitionMapper zxSkStockTransItemMaterialRequisitionMapper;

    @Override
    public ResponseEntity getZxSkStockTransferWithdrawalListByCondition(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        if (zxSkStockTransferWithdrawal == null) {
            zxSkStockTransferWithdrawal = new ZxSkStockTransferWithdrawal();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferWithdrawal.setCompanyId("");
            zxSkStockTransferWithdrawal.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferWithdrawal.getOrgID2())){
                zxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferWithdrawal.setCompanyId(zxSkStockTransferWithdrawal.getOutOrgID());
            zxSkStockTransferWithdrawal.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferWithdrawal.getOrgID2())){
                zxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferWithdrawal.setOutOrgID(zxSkStockTransferWithdrawal.getOutOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferWithdrawal.getOrgID2())){
                zxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferWithdrawal.getPage(),zxSkStockTransferWithdrawal.getLimit());
        // ????????????
        List<ZxSkStockTransferWithdrawal> zxSkStockTransferWithdrawalList = zxSkStockTransferWithdrawalMapper.selectByZxSkStockTransferWithdrawalList(zxSkStockTransferWithdrawal);
        //????????????
        for (ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal1 : zxSkStockTransferWithdrawalList) {
            ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
            zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal1.getId());
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
            if(CollUtil.isNotEmpty(zxSkStockTransItemWithdrawals)){
                //????????????????????????
                for (ZxSkStockTransItemWithdrawal skStockTransItemWithdrawal : zxSkStockTransItemWithdrawals) {
                    String str = skStockTransItemWithdrawal.getMainIdAndItemId();
                    ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = zxSkStockTransItemMaterialRequisitionMapper.selectByPrimaryKey(StrUtil.sub(str, str.indexOf("-") + 1, str.length()));
                    if(ObjectUtil.isNotEmpty(zxSkStockTransItemMaterialRequisition)){
                        skStockTransItemWithdrawal.setIsOutNumber(zxSkStockTransItemMaterialRequisition.getIsOutNumber());
                    }else {
                        skStockTransItemWithdrawal.setIsOutNumber(new BigDecimal("0"));
                    }
                }
            }
            zxSkStockTransferWithdrawal1.setZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawals);
        }
        //????????????
        for (ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal1 : zxSkStockTransferWithdrawalList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferWithdrawal1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferWithdrawal1.setFileList(zxErpFiles);
        }

        // ??????????????????
        PageInfo<ZxSkStockTransferWithdrawal> p = new PageInfo<>(zxSkStockTransferWithdrawalList);

        return repEntity.okList(zxSkStockTransferWithdrawalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferWithdrawalDetails(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        if (zxSkStockTransferWithdrawal == null) {
            zxSkStockTransferWithdrawal = new ZxSkStockTransferWithdrawal();
        }
        // ????????????
        ZxSkStockTransferWithdrawal dbZxSkStockTransferWithdrawal = zxSkStockTransferWithdrawalMapper.selectByPrimaryKey(zxSkStockTransferWithdrawal.getId());
        // ????????????
        if (dbZxSkStockTransferWithdrawal != null) {
            ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
            zxSkStockTransItemWithdrawal.setBillID(dbZxSkStockTransferWithdrawal.getId());
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
            dbZxSkStockTransferWithdrawal.setZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawals);
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferWithdrawal.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferWithdrawal.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferWithdrawal);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferWithdrawal.setId(UuidUtil.generate());
        zxSkStockTransferWithdrawal.setCreateUserInfo(userKey, realName);

        //??????????????????: 0:?????????
        zxSkStockTransferWithdrawal.setBillStatus("0");
        //totalAmt
        //????????????
        BigDecimal total = new BigDecimal(0);
        //????????????
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransferWithdrawal.getZxSkStockTransItemWithdrawalList();
        if(zxSkStockTransItemWithdrawalList != null && zxSkStockTransItemWithdrawalList.size()>0) {
            for (ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal : zxSkStockTransItemWithdrawalList) {
                //????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemWithdrawal.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemWithdrawal.getOutAmt());
                }
                //stdPrice -> outPrice
                //???????????? -> ??????
                if(CalcUtils.compareToZero(zxSkStockTransItemWithdrawal.getStdPrice())!=0){
                    zxSkStockTransItemWithdrawal.setOutPrice(zxSkStockTransItemWithdrawal.getStdPrice());
                }else {
                    zxSkStockTransItemWithdrawal.setOutPrice(new BigDecimal(0));
                }
                zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal.getId());
                //???????????????id,??????????????? (??????)
                zxSkStockTransItemWithdrawal.setZxSkStockTransItemWithdrawalId((UuidUtil.generate()));
                zxSkStockTransItemWithdrawal.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemWithdrawalMapper.insert(zxSkStockTransItemWithdrawal);
            }
        }
        //????????????
        List<ZxErpFile> fileList = zxSkStockTransferWithdrawal.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferWithdrawal.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferWithdrawal.setTotalAmt(total);
        int flag = zxSkStockTransferWithdrawalMapper.insert(zxSkStockTransferWithdrawal);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferWithdrawal);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferWithdrawal dbzxSkStockTransferWithdrawal = zxSkStockTransferWithdrawalMapper.selectByPrimaryKey(zxSkStockTransferWithdrawal.getId());
        if (dbzxSkStockTransferWithdrawal != null && StrUtil.isNotEmpty(dbzxSkStockTransferWithdrawal.getId())) {
           // ??????(1)
           dbzxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID());
           // ??????(2)
           dbzxSkStockTransferWithdrawal.setWhOrgID(zxSkStockTransferWithdrawal.getWhOrgID());
           // ????????????ID
           dbzxSkStockTransferWithdrawal.setOutOrgID(zxSkStockTransferWithdrawal.getOutOrgID());
           // ????????????ID
           dbzxSkStockTransferWithdrawal.setInOrgID(zxSkStockTransferWithdrawal.getInOrgID());
           // ????????????
           dbzxSkStockTransferWithdrawal.setBizType(zxSkStockTransferWithdrawal.getBizType());
           // ????????????
           dbzxSkStockTransferWithdrawal.setBillNo(zxSkStockTransferWithdrawal.getBillNo());
           // ????????????
           dbzxSkStockTransferWithdrawal.setMaterialSource(zxSkStockTransferWithdrawal.getMaterialSource());
           // ??????
           dbzxSkStockTransferWithdrawal.setPurpose(zxSkStockTransferWithdrawal.getPurpose());
           // ????????????
           dbzxSkStockTransferWithdrawal.setBusDate(zxSkStockTransferWithdrawal.getBusDate());
           // ????????????
           dbzxSkStockTransferWithdrawal.setOutOrgName(zxSkStockTransferWithdrawal.getOutOrgName());
           // ????????????
           dbzxSkStockTransferWithdrawal.setInOrgName(zxSkStockTransferWithdrawal.getInOrgName());
           // ????????????
           dbzxSkStockTransferWithdrawal.setTotalAmt(zxSkStockTransferWithdrawal.getTotalAmt());
           // ?????????(1)
           dbzxSkStockTransferWithdrawal.setOuttransactor(zxSkStockTransferWithdrawal.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferWithdrawal.setIntransactor(zxSkStockTransferWithdrawal.getIntransactor());
           // ???????????????
           dbzxSkStockTransferWithdrawal.setWaretransactor(zxSkStockTransferWithdrawal.getWaretransactor());
           // ??????
           dbzxSkStockTransferWithdrawal.setBuyer(zxSkStockTransferWithdrawal.getBuyer());
           // ??????
           dbzxSkStockTransferWithdrawal.setConsignee(zxSkStockTransferWithdrawal.getConsignee());
           // ?????????
           dbzxSkStockTransferWithdrawal.setAuditor(zxSkStockTransferWithdrawal.getAuditor());
           // ?????????
           dbzxSkStockTransferWithdrawal.setVoucherNo(zxSkStockTransferWithdrawal.getVoucherNo());
           // ?????????
           dbzxSkStockTransferWithdrawal.setContractNo(zxSkStockTransferWithdrawal.getContractNo());
           // ????????????
           dbzxSkStockTransferWithdrawal.setInvoiceNo(zxSkStockTransferWithdrawal.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferWithdrawal.setBillType(zxSkStockTransferWithdrawal.getBillType());
           // ????????????
           dbzxSkStockTransferWithdrawal.setBillFlag(zxSkStockTransferWithdrawal.getBillFlag());
           // ????????????
           dbzxSkStockTransferWithdrawal.setBillStatus(zxSkStockTransferWithdrawal.getBillStatus());
           // ?????????
           dbzxSkStockTransferWithdrawal.setReporter(zxSkStockTransferWithdrawal.getReporter());
           // ????????????
           dbzxSkStockTransferWithdrawal.setDeductAmtType(zxSkStockTransferWithdrawal.getDeductAmtType());
           // ??????
           dbzxSkStockTransferWithdrawal.setRemark(zxSkStockTransferWithdrawal.getRemark());
           // ??????
           dbzxSkStockTransferWithdrawal.setCombProp(zxSkStockTransferWithdrawal.getCombProp());
           // ????????????ID
           dbzxSkStockTransferWithdrawal.setResourceID(zxSkStockTransferWithdrawal.getResourceID());
           // ????????????
           dbzxSkStockTransferWithdrawal.setResourceName(zxSkStockTransferWithdrawal.getResourceName());
           // ????????????ID
           dbzxSkStockTransferWithdrawal.setCbsID(zxSkStockTransferWithdrawal.getCbsID());
           // ????????????
           dbzxSkStockTransferWithdrawal.setCbsName(zxSkStockTransferWithdrawal.getCbsName());
           // ??????(3)
           dbzxSkStockTransferWithdrawal.setWarehouseName(zxSkStockTransferWithdrawal.getWarehouseName());
           // ?????????????????????????????????
           dbzxSkStockTransferWithdrawal.setInvoiceNum(zxSkStockTransferWithdrawal.getInvoiceNum());
           // ??????id
           dbzxSkStockTransferWithdrawal.setCompanyId(zxSkStockTransferWithdrawal.getCompanyId());
           // ????????????
           dbzxSkStockTransferWithdrawal.setCompanyName(zxSkStockTransferWithdrawal.getCompanyName());
           // ??????
           dbzxSkStockTransferWithdrawal.setModifyUserInfo(userKey, realName);


            //???????????????
            ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
            zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal.getId());
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
            if(zxSkStockTransItemWithdrawals != null && zxSkStockTransItemWithdrawals.size()>0) {
                zxSkStockTransItemWithdrawal.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemWithdrawalMapper.batchDeleteUpdateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawals, zxSkStockTransItemWithdrawal);
            }
            //??????list
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransferWithdrawal.getZxSkStockTransItemWithdrawalList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemWithdrawalList != null && zxSkStockTransItemWithdrawalList.size()>0) {
                for(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal1 : zxSkStockTransItemWithdrawalList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemWithdrawal1.getOutAmt());
                    zxSkStockTransItemWithdrawal1.setZxSkStockTransItemWithdrawalId(UuidUtil.generate());
                    zxSkStockTransItemWithdrawal1.setBillID(zxSkStockTransferWithdrawal.getId());
                    zxSkStockTransItemWithdrawal1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemWithdrawalMapper.insert(zxSkStockTransItemWithdrawal1);
                }
            }

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferWithdrawal.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkStockTransferWithdrawal.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferWithdrawal.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferWithdrawal.setTotalAmt(total);
            flag = zxSkStockTransferWithdrawalMapper.updateByPrimaryKey(dbzxSkStockTransferWithdrawal);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferWithdrawal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferWithdrawal(List<ZxSkStockTransferWithdrawal> zxSkStockTransferWithdrawalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferWithdrawalList != null && zxSkStockTransferWithdrawalList.size() > 0) {
            for (ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal : zxSkStockTransferWithdrawalList) {
                // ????????????
                ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
                zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal.getId());
                List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
                if(zxSkStockTransItemWithdrawals != null && zxSkStockTransItemWithdrawals.size()>0) {
                    zxSkStockTransItemWithdrawal.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemWithdrawalMapper.batchDeleteUpdateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawals, zxSkStockTransItemWithdrawal);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferWithdrawal.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal = new ZxSkStockTransferWithdrawal();
           zxSkStockTransferWithdrawal.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferWithdrawalMapper.batchDeleteUpdateZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawalList, zxSkStockTransferWithdrawal);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferWithdrawalList);
        }
    }

    //????????????
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        //????????????
        ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal1 = zxSkStockTransferWithdrawalMapper.selectByPrimaryKey(zxSkStockTransferWithdrawal.getId());
        if(StrUtil.equals(zxSkStockTransferWithdrawal1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkStockTransItemWithdrawal dbzxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
        dbzxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal1.getId());
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(dbzxSkStockTransItemWithdrawal);
        if(CollUtil.isEmpty(zxSkStockTransItemWithdrawalList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????????????????
        //????????????
        ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
        //????????????id
        zxSkStockTransferMaterialRequisition.setWhOrgID(zxSkStockTransferWithdrawal1.getWhOrgID());
        //??????id
        zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferWithdrawal1.getOutOrgID());
        //????????????id
        zxSkStockTransferMaterialRequisition.setInOrgID(zxSkStockTransferWithdrawal1.getInOrgID());
        //??????id
        zxSkStockTransferMaterialRequisition.setResourceID(zxSkStockTransferWithdrawal1.getResourceID());
        //whOrgID,outOrgID ,inOrgID,resourceID, resId
        //(????????????id,??????id,????????????id,??????id,????????????ID List)
        Map<String,ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionMap = zxSkStockTransferMaterialRequisitionMapper.getZxSkStockTransferMaterialRequisitionResName(zxSkStockTransItemWithdrawalList,zxSkStockTransferMaterialRequisition);

        //???????????????????????????
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionIsOutNumberList = new ArrayList<>();
        //??????List (?????????????????????????????????)
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = new ArrayList<>();
        for (ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal : zxSkStockTransItemWithdrawalList) {
            ZxSkStockTransItemMaterialRequisition dbzxSkStockTransItemMaterialRequisition = zxSkStockTransItemMaterialRequisitionMap.get(zxSkStockTransItemWithdrawal.getMainIdAndItemId());
            if(CalcUtils.compareTo(dbzxSkStockTransItemMaterialRequisition.getIsOutNumber(),zxSkStockTransItemWithdrawal.getOutQty())<0){
                zxSkStockTransItemWithdrawals.add(zxSkStockTransItemWithdrawal);
            }else {
                String str = zxSkStockTransItemWithdrawal.getMainIdAndItemId();
                ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
                zxSkStockTransItemMaterialRequisition.setId(StrUtil.sub(str,str.indexOf("-")+1,str.length()));
                zxSkStockTransItemMaterialRequisition.setIsOutNumber(CalcUtils.calcSubtract(dbzxSkStockTransItemMaterialRequisition.getIsOutNumber(),zxSkStockTransItemWithdrawal.getOutQty()));
                zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey,realName);
                zxSkStockTransItemMaterialRequisitionIsOutNumberList.add(zxSkStockTransItemMaterialRequisition);
            }
        }
        if(CollUtil.isNotEmpty(zxSkStockTransItemWithdrawals)){
            return repEntity.layerMessage(false,zxSkStockTransItemWithdrawals, "????????????????????????????????????????????????");
        }
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal : zxSkStockTransItemWithdrawalList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferWithdrawal1.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferWithdrawal1.getOutOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferWithdrawal1.getWhOrgID());
            //????????????ID
            zxSkStock.setResourceId(zxSkStockTransferWithdrawal1.getResourceID());
            //??????????????????
            zxSkStock.setResourceName(zxSkStockTransferWithdrawal1.getResourceName());
            //??????Id
            zxSkStock.setResID(zxSkStockTransItemWithdrawal.getResID());
            //????????????
            zxSkStock.setResCode(zxSkStockTransItemWithdrawal.getResCode());
            //????????????
            zxSkStock.setResName(zxSkStockTransItemWithdrawal.getResName());
            //????????????spec
            zxSkStock.setSpec(zxSkStockTransItemWithdrawal.getSpec());
            //??????unit
            zxSkStock.setUnit(zxSkStockTransItemWithdrawal.getResUnit());
            //????????????
            zxSkStock.setStockQty(zxSkStockTransItemWithdrawal.getOutQty());
            //????????????
            zxSkStock.setStockAmt(zxSkStockTransItemWithdrawal.getOutAmt());

            zxSkStockList.add(zxSkStock);
        }
        //?????????
        ResponseEntity responseEntity = ZxSkStockServiceImpl.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferWithdrawal1.setBillStatus("1");
            zxSkStockTransferWithdrawal1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferWithdrawalMapper.checkZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawal1);
            //???????????????????????????????????????????????????
            for (ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition : zxSkStockTransItemMaterialRequisitionIsOutNumberList) {
                zxSkStockTransItemMaterialRequisitionMapper.updateZxSkStockTransItemMaterialRequisitionIsOutNumber(zxSkStockTransItemMaterialRequisition);
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

    //???????????????+???????????????+?????????-??????-????????????+??????
    @Override
    public ResponseEntity getZxSkStockTransferWithdrawalNo(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        if(StrUtil.isEmpty(zxSkStockTransferWithdrawal.getOutOrgID()) || zxSkStockTransferWithdrawal.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferWithdrawal.getOutOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferWithdrawal.getBusDate());
        int month = DateUtil.month(zxSkStockTransferWithdrawal.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferWithdrawal.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferWithdrawalMapper.getZxSkStockTransferWithdrawalCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }





}
