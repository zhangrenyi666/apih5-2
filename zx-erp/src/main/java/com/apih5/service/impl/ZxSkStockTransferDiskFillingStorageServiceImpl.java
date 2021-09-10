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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferDiskFillingStorage.setCompanyId("");
            zxSkStockTransferDiskFillingStorage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferDiskFillingStorage.setCompanyId(zxSkStockTransferDiskFillingStorage.getProjectId());
            zxSkStockTransferDiskFillingStorage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferDiskFillingStorage.setProjectId(zxSkStockTransferDiskFillingStorage.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferDiskFillingStorage.getPage(),zxSkStockTransferDiskFillingStorage.getLimit());
        // 获取数据
        List<ZxSkStockTransferDiskFillingStorage> zxSkStockTransferDiskFillingStorageList = zxSkStockTransferDiskFillingStorageMapper.selectByZxSkStockTransferDiskFillingStorageList(zxSkStockTransferDiskFillingStorage);
        //查询明细
        for (ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage1 : zxSkStockTransferDiskFillingStorageList) {
            ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
            zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage1.getId());
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
            zxSkStockTransferDiskFillingStorage1.setZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorages);
        }
        //附件
        for (ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage1 : zxSkStockTransferDiskFillingStorageList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferDiskFillingStorage1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferDiskFillingStorage1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferDiskFillingStorage> p = new PageInfo<>(zxSkStockTransferDiskFillingStorageList);

        return repEntity.okList(zxSkStockTransferDiskFillingStorageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferDiskFillingStorageDetails(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        if (zxSkStockTransferDiskFillingStorage == null) {
            zxSkStockTransferDiskFillingStorage = new ZxSkStockTransferDiskFillingStorage();
        }
        // 获取数据
        ZxSkStockTransferDiskFillingStorage dbZxSkStockTransferDiskFillingStorage = zxSkStockTransferDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransferDiskFillingStorage.getId());
        // 数据存在
        if (dbZxSkStockTransferDiskFillingStorage != null) {
            ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
            zxSkStockTransItemDiskFillingStorage.setBillID(dbZxSkStockTransferDiskFillingStorage.getId());
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
            dbZxSkStockTransferDiskFillingStorage.setZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorages);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferDiskFillingStorage.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferDiskFillingStorage.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferDiskFillingStorage);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferDiskFillingStorage.setId(UuidUtil.generate());
        zxSkStockTransferDiskFillingStorage.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkStockTransferDiskFillingStorage.setBillStatus("0");
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList = zxSkStockTransferDiskFillingStorage.getZxSkStockTransItemDiskFillingStorageList();
        if(zxSkStockTransItemDiskFillingStorageList != null && zxSkStockTransItemDiskFillingStorageList.size()>0) {
            for (ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage : zxSkStockTransItemDiskFillingStorageList) {
                //单据总额
                if(CalcUtils.compareToZero(zxSkStockTransItemDiskFillingStorage.getInAmtAll())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemDiskFillingStorage.getInAmtAll());
                }
                zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage.getId());
                zxSkStockTransItemDiskFillingStorage.setId((UuidUtil.generate()));
                zxSkStockTransItemDiskFillingStorage.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemDiskFillingStorageMapper.insert(zxSkStockTransItemDiskFillingStorage);
            }
        }
        //添加附件
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
           // 仓库(1)
           dbzxSkStockTransferDiskFillingStorage.setOrgID(zxSkStockTransferDiskFillingStorage.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferDiskFillingStorage.setWhOrgID(zxSkStockTransferDiskFillingStorage.getWhOrgID());
           // 供方ID
           dbzxSkStockTransferDiskFillingStorage.setOutOrgID(zxSkStockTransferDiskFillingStorage.getOutOrgID());
           // 需方ID
           dbzxSkStockTransferDiskFillingStorage.setInOrgID(zxSkStockTransferDiskFillingStorage.getInOrgID());
           // 业务类型
           dbzxSkStockTransferDiskFillingStorage.setBizType(zxSkStockTransferDiskFillingStorage.getBizType());
           // 单据编号
           dbzxSkStockTransferDiskFillingStorage.setBillNo(zxSkStockTransferDiskFillingStorage.getBillNo());
           // 物资来源
           dbzxSkStockTransferDiskFillingStorage.setMaterialSource(zxSkStockTransferDiskFillingStorage.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferDiskFillingStorage.setPurpose(zxSkStockTransferDiskFillingStorage.getPurpose());
           // 业务日期
           dbzxSkStockTransferDiskFillingStorage.setBusDate(zxSkStockTransferDiskFillingStorage.getBusDate());
           // 供方名称
           dbzxSkStockTransferDiskFillingStorage.setOutOrgName(zxSkStockTransferDiskFillingStorage.getOutOrgName());
           // 需方名称
           dbzxSkStockTransferDiskFillingStorage.setInOrgName(zxSkStockTransferDiskFillingStorage.getInOrgName());
           // 单据金额
           dbzxSkStockTransferDiskFillingStorage.setTotalAmt(zxSkStockTransferDiskFillingStorage.getTotalAmt());
           // 经办人(1)
           dbzxSkStockTransferDiskFillingStorage.setOuttransactor(zxSkStockTransferDiskFillingStorage.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferDiskFillingStorage.setIntransactor(zxSkStockTransferDiskFillingStorage.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferDiskFillingStorage.setWaretransactor(zxSkStockTransferDiskFillingStorage.getWaretransactor());
           // 采购人
           dbzxSkStockTransferDiskFillingStorage.setBuyer(zxSkStockTransferDiskFillingStorage.getBuyer());
           // 收料人
           dbzxSkStockTransferDiskFillingStorage.setConsignee(zxSkStockTransferDiskFillingStorage.getConsignee());
           // 审核人
           dbzxSkStockTransferDiskFillingStorage.setAuditor(zxSkStockTransferDiskFillingStorage.getAuditor());
           // 凭证号
           dbzxSkStockTransferDiskFillingStorage.setVoucherNo(zxSkStockTransferDiskFillingStorage.getVoucherNo());
           // 合同号
           dbzxSkStockTransferDiskFillingStorage.setContractNo(zxSkStockTransferDiskFillingStorage.getContractNo());
           // 发票号
           dbzxSkStockTransferDiskFillingStorage.setInvoiceNo(zxSkStockTransferDiskFillingStorage.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferDiskFillingStorage.setBillType(zxSkStockTransferDiskFillingStorage.getBillType());
           // 单据标志
           dbzxSkStockTransferDiskFillingStorage.setBillFlag(zxSkStockTransferDiskFillingStorage.getBillFlag());
           // 单据状态
           dbzxSkStockTransferDiskFillingStorage.setBillStatus(zxSkStockTransferDiskFillingStorage.getBillStatus());
           // 制单人
           dbzxSkStockTransferDiskFillingStorage.setReporter(zxSkStockTransferDiskFillingStorage.getReporter());
           // 扣款类型
           dbzxSkStockTransferDiskFillingStorage.setDeductAmtType(zxSkStockTransferDiskFillingStorage.getDeductAmtType());
           // 备注
           dbzxSkStockTransferDiskFillingStorage.setRemark(zxSkStockTransferDiskFillingStorage.getRemark());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferDiskFillingStorage.setInvoiceNum(zxSkStockTransferDiskFillingStorage.getInvoiceNum());
           // 仓库(3)
           dbzxSkStockTransferDiskFillingStorage.setWarehouseName(zxSkStockTransferDiskFillingStorage.getWarehouseName());
           // 项目id
           dbzxSkStockTransferDiskFillingStorage.setProjectId(zxSkStockTransferDiskFillingStorage.getProjectId());
           // 项目名称
           dbzxSkStockTransferDiskFillingStorage.setProjectName(zxSkStockTransferDiskFillingStorage.getProjectName());
           // 公司id
           dbzxSkStockTransferDiskFillingStorage.setCompanyId(zxSkStockTransferDiskFillingStorage.getCompanyId());
           // 公司名称
           dbzxSkStockTransferDiskFillingStorage.setCompanyName(zxSkStockTransferDiskFillingStorage.getCompanyName());
           // 共通
           dbzxSkStockTransferDiskFillingStorage.setModifyUserInfo(userKey, realName);


            //删除在新增
            ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
            zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage.getId());
            List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
            if(zxSkStockTransItemDiskFillingStorages != null && zxSkStockTransItemDiskFillingStorages.size()>0) {
                zxSkStockTransItemDiskFillingStorage.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemDiskFillingStorageMapper.batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorages, zxSkStockTransItemDiskFillingStorage);
            }
            //明细list
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
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferDiskFillingStorage.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
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
        // 失败
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
                // 删除明细
                ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
                zxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage.getId());
                List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorages = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
                if(zxSkStockTransItemDiskFillingStorages != null && zxSkStockTransItemDiskFillingStorages.size()>0) {
                    zxSkStockTransItemDiskFillingStorage.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemDiskFillingStorageMapper.batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorages, zxSkStockTransItemDiskFillingStorage);
                }
                //删除附件
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferDiskFillingStorageList);
        }
    }

    //盘盈入库单
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferDiskFillingStorage(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        //然后审核数据
        ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage1 = zxSkStockTransferDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransferDiskFillingStorage.getId());
        if(StrUtil.equals(zxSkStockTransferDiskFillingStorage1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        ZxSkStockTransItemDiskFillingStorage dbzxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
        dbzxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransferDiskFillingStorage1.getId());
        List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(dbzxSkStockTransItemDiskFillingStorage);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage : zxSkStockTransItemDiskFillingStorageList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferDiskFillingStorage1.getCompanyId());
            //项目ID(收料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferDiskFillingStorage1.getInOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferDiskFillingStorage1.getWhOrgID());
            //物资Id
            zxSkStock.setResID(zxSkStockTransItemDiskFillingStorage.getResID());
            //资源编号
            zxSkStock.setResCode(zxSkStockTransItemDiskFillingStorage.getResCode());
            //资源名称
            zxSkStock.setResName(zxSkStockTransItemDiskFillingStorage.getResName());
            //规格型号spec
            zxSkStock.setSpec(zxSkStockTransItemDiskFillingStorage.getSpec());
            //单位unit
            zxSkStock.setUnit(zxSkStockTransItemDiskFillingStorage.getResUnit());
            //数量
            zxSkStock.setStockQty(zxSkStockTransItemDiskFillingStorage.getInQty());
            //金额
            zxSkStock.setStockAmt(zxSkStockTransItemDiskFillingStorage.getInAmtAll());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.addZxSkStockNumTotalChange(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferDiskFillingStorage1.setBillStatus("1");
            zxSkStockTransferDiskFillingStorage1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferDiskFillingStorageMapper.checkZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorage1);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核失败");
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

    //业主合同编号 盘盈入字第 年份-月份-顺序号
    //例 HWGS（T）-HAN-WYTJ-04盘盈入字第 2019-11-001 号  每月从-001号开始
    @Override
    public ResponseEntity getZxSkStockTransferDiskFillingStorageNo(ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        if(StrUtil.isEmpty(zxSkStockTransferDiskFillingStorage.getWhOrgID()) || zxSkStockTransferDiskFillingStorage.getBusDate() == null){
            return repEntity.ok(null);
        }
        //根据仓库id去查询 项目id
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
        String no = contractNo + " 盘盈入字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }


}
