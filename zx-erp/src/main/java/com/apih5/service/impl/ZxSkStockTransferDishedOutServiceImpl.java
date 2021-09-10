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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferDishedOut.setCompanyId("");
            zxSkStockTransferDishedOut.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferDishedOut.setCompanyId(zxSkStockTransferDishedOut.getProjectId());
            zxSkStockTransferDishedOut.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferDishedOut.setProjectId(zxSkStockTransferDishedOut.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferDishedOut.getPage(),zxSkStockTransferDishedOut.getLimit());
        // 获取数据
        List<ZxSkStockTransferDishedOut> zxSkStockTransferDishedOutList = zxSkStockTransferDishedOutMapper.selectByZxSkStockTransferDishedOutList(zxSkStockTransferDishedOut);
        //查询明细
        for (ZxSkStockTransferDishedOut zxSkStockTransferDishedOut1 : zxSkStockTransferDishedOutList) {
            ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
            zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut1.getId());
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
            zxSkStockTransferDishedOut1.setZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOuts);
        }
        //附件
        for (ZxSkStockTransferDishedOut zxSkStockTransferDishedOut1 : zxSkStockTransferDishedOutList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferDishedOut1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferDishedOut1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferDishedOut> p = new PageInfo<>(zxSkStockTransferDishedOutList);

        return repEntity.okList(zxSkStockTransferDishedOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferDishedOutDetails(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        if (zxSkStockTransferDishedOut == null) {
            zxSkStockTransferDishedOut = new ZxSkStockTransferDishedOut();
        }
        // 获取数据
        ZxSkStockTransferDishedOut dbZxSkStockTransferDishedOut = zxSkStockTransferDishedOutMapper.selectByPrimaryKey(zxSkStockTransferDishedOut.getId());
        // 数据存在
        if (dbZxSkStockTransferDishedOut != null) {
            ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
            zxSkStockTransItemDishedOut.setBillID(dbZxSkStockTransferDishedOut.getId());
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
            dbZxSkStockTransferDishedOut.setZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOuts);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferDishedOut.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferDishedOut.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferDishedOut);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferDishedOut.setId(UuidUtil.generate());
        zxSkStockTransferDishedOut.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkStockTransferDishedOut.setBillStatus("0");
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList = zxSkStockTransferDishedOut.getZxSkStockTransItemDishedOutList();
        if(zxSkStockTransItemDishedOutList != null && zxSkStockTransItemDishedOutList.size()>0) {
            for (ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut : zxSkStockTransItemDishedOutList) {
                //单据总额
                if(CalcUtils.compareToZero(zxSkStockTransItemDishedOut.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemDishedOut.getOutAmt());
                }
                zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut.getId());
                zxSkStockTransItemDishedOut.setId((UuidUtil.generate()));
                zxSkStockTransItemDishedOut.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemDishedOutMapper.insert(zxSkStockTransItemDishedOut);
            }
        }
        //添加附件
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
           // 仓库(1)
           dbzxSkStockTransferDishedOut.setOrgID(zxSkStockTransferDishedOut.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferDishedOut.setWhOrgID(zxSkStockTransferDishedOut.getWhOrgID());
           // 供方ID
           dbzxSkStockTransferDishedOut.setOutOrgID(zxSkStockTransferDishedOut.getOutOrgID());
           // 需方ID
           dbzxSkStockTransferDishedOut.setInOrgID(zxSkStockTransferDishedOut.getInOrgID());
           // 业务类型
           dbzxSkStockTransferDishedOut.setBizType(zxSkStockTransferDishedOut.getBizType());
           // 单据编号
           dbzxSkStockTransferDishedOut.setBillNo(zxSkStockTransferDishedOut.getBillNo());
           // 物资来源
           dbzxSkStockTransferDishedOut.setMaterialSource(zxSkStockTransferDishedOut.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferDishedOut.setPurpose(zxSkStockTransferDishedOut.getPurpose());
           // 业务日期
           dbzxSkStockTransferDishedOut.setBusDate(zxSkStockTransferDishedOut.getBusDate());
           // 供方名称
           dbzxSkStockTransferDishedOut.setOutOrgName(zxSkStockTransferDishedOut.getOutOrgName());
           // 需方名称
           dbzxSkStockTransferDishedOut.setInOrgName(zxSkStockTransferDishedOut.getInOrgName());
           // 单据金额
           dbzxSkStockTransferDishedOut.setTotalAmt(zxSkStockTransferDishedOut.getTotalAmt());
           // 经办人(1)
           dbzxSkStockTransferDishedOut.setOuttransactor(zxSkStockTransferDishedOut.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferDishedOut.setIntransactor(zxSkStockTransferDishedOut.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferDishedOut.setWaretransactor(zxSkStockTransferDishedOut.getWaretransactor());
           // 采购人
           dbzxSkStockTransferDishedOut.setBuyer(zxSkStockTransferDishedOut.getBuyer());
           // 收料人
           dbzxSkStockTransferDishedOut.setConsignee(zxSkStockTransferDishedOut.getConsignee());
           // 审核人
           dbzxSkStockTransferDishedOut.setAuditor(zxSkStockTransferDishedOut.getAuditor());
           // 凭证号
           dbzxSkStockTransferDishedOut.setVoucherNo(zxSkStockTransferDishedOut.getVoucherNo());
           // 合同号
           dbzxSkStockTransferDishedOut.setContractNo(zxSkStockTransferDishedOut.getContractNo());
           // 发票号
           dbzxSkStockTransferDishedOut.setInvoiceNo(zxSkStockTransferDishedOut.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferDishedOut.setBillType(zxSkStockTransferDishedOut.getBillType());
           // 单据标志
           dbzxSkStockTransferDishedOut.setBillFlag(zxSkStockTransferDishedOut.getBillFlag());
           // 单据状态
           dbzxSkStockTransferDishedOut.setBillStatus(zxSkStockTransferDishedOut.getBillStatus());
           // 制单人
           dbzxSkStockTransferDishedOut.setReporter(zxSkStockTransferDishedOut.getReporter());
           // 扣款类型
           dbzxSkStockTransferDishedOut.setDeductAmtType(zxSkStockTransferDishedOut.getDeductAmtType());
           // 备注
           dbzxSkStockTransferDishedOut.setRemark(zxSkStockTransferDishedOut.getRemark());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferDishedOut.setInvoiceNum(zxSkStockTransferDishedOut.getInvoiceNum());
           // 仓库(3)
           dbzxSkStockTransferDishedOut.setWarehouseName(zxSkStockTransferDishedOut.getWarehouseName());
           // 项目id
           dbzxSkStockTransferDishedOut.setProjectId(zxSkStockTransferDishedOut.getProjectId());
           // 项目名称
           dbzxSkStockTransferDishedOut.setProjectName(zxSkStockTransferDishedOut.getProjectName());
           // 公司id
           dbzxSkStockTransferDishedOut.setCompanyId(zxSkStockTransferDishedOut.getCompanyId());
           // 公司名称
           dbzxSkStockTransferDishedOut.setCompanyName(zxSkStockTransferDishedOut.getCompanyName());
           // 共通
           dbzxSkStockTransferDishedOut.setModifyUserInfo(userKey, realName);


            //删除在新增
            ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
            zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut.getId());
            List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
            if(zxSkStockTransItemDishedOuts != null && zxSkStockTransItemDishedOuts.size()>0) {
                zxSkStockTransItemDishedOut.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemDishedOutMapper.batchDeleteUpdateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOuts, zxSkStockTransItemDishedOut);
            }

            //明细list
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
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferDishedOut.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
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
        // 失败
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
                // 删除明细
                ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
                zxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut.getId());
                List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOuts = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
                if(zxSkStockTransItemDishedOuts != null && zxSkStockTransItemDishedOuts.size()>0) {
                    zxSkStockTransItemDishedOut.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemDishedOutMapper.batchDeleteUpdateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOuts, zxSkStockTransItemDishedOut);
                }
                //删除附件
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferDishedOutList);
        }
    }


    //业主合同编号 盘亏出字第 年份-月份-顺序号
    //例 HWGS（T）-HAN-WYTJ-04盘亏出字第 2019-11-001 号  每月从-001号开始
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
        String no = contractNo + " 盘亏出字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public synchronized ResponseEntity checkZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        //审核数据
        ZxSkStockTransferDishedOut zxSkStockTransferDishedOut1 = zxSkStockTransferDishedOutMapper.selectByPrimaryKey(zxSkStockTransferDishedOut.getId());
        if(StrUtil.equals(zxSkStockTransferDishedOut1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkStockTransItemDishedOut dbzxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
        dbzxSkStockTransItemDishedOut.setBillID(zxSkStockTransferDishedOut1.getId());
        List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(dbzxSkStockTransItemDishedOut);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut : zxSkStockTransItemDishedOutList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferDishedOut1.getCompanyId());
            //项目ID(收料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferDishedOut1.getOutOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferDishedOut1.getWhOrgID());
            //物资Id
            zxSkStock.setResID(zxSkStockTransItemDishedOut.getResID());
            //资源编号
            zxSkStock.setResCode(zxSkStockTransItemDishedOut.getResCode());
            //资源名称
            zxSkStock.setResName(zxSkStockTransItemDishedOut.getResName());
            //规格型号spec
            zxSkStock.setSpec(zxSkStockTransItemDishedOut.getSpec());
            //单位unit
            zxSkStock.setUnit(zxSkStockTransItemDishedOut.getResUnit());
            //数量
            zxSkStock.setStockQty(zxSkStockTransItemDishedOut.getOutQty());
            //金额
            zxSkStock.setStockAmt(zxSkStockTransItemDishedOut.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.reduceZxSkStockNumTotalChange(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferDishedOut1.setBillStatus("1");
            zxSkStockTransferDishedOut1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferDishedOutMapper.checkZxSkStockTransferDishedOut(zxSkStockTransferDishedOut1);
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


}
