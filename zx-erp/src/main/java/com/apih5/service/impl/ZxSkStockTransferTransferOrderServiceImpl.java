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

        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferTransferOrder.setCompanyId("");
            zxSkStockTransferTransferOrder.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID2())){
                zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferTransferOrder.setCompanyId(zxSkStockTransferTransferOrder.getOutOrgID());
            zxSkStockTransferTransferOrder.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID2())){
                zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOutOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferTransferOrder.getOrgID2())){
                zxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferTransferOrder.getPage(),zxSkStockTransferTransferOrder.getLimit());
        // 获取数据
        List<ZxSkStockTransferTransferOrder> zxSkStockTransferTransferOrderList = zxSkStockTransferTransferOrderMapper.selectByZxSkStockTransferTransferOrderList(zxSkStockTransferTransferOrder);
        //查询明细
        for (ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 : zxSkStockTransferTransferOrderList) {
            ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
            zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrders = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
            zxSkStockTransferTransferOrder1.setZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrders);
        }
        //附件
        for (ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 : zxSkStockTransferTransferOrderList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferTransferOrder1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferTransferOrder1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferTransferOrder> p = new PageInfo<>(zxSkStockTransferTransferOrderList);

        return repEntity.okList(zxSkStockTransferTransferOrderList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferTransferOrderDetails(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        if (zxSkStockTransferTransferOrder == null) {
            zxSkStockTransferTransferOrder = new ZxSkStockTransferTransferOrder();
        }
        // 获取数据
        ZxSkStockTransferTransferOrder dbZxSkStockTransferTransferOrder = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        // 数据存在
        if (dbZxSkStockTransferTransferOrder != null) {
            ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
            zxSkStockTransItemTransferOrder.setBillID(dbZxSkStockTransferTransferOrder.getId());
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrders = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
            dbZxSkStockTransferTransferOrder.setZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrders);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferTransferOrder.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferTransferOrder.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferTransferOrder);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferTransferOrder.setId(UuidUtil.generate());
        zxSkStockTransferTransferOrder.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkStockTransferTransferOrder.setBillStatus("0");
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransferTransferOrder.getZxSkStockTransItemTransferOrderList();
        if(zxSkStockTransItemTransferOrderList != null && zxSkStockTransItemTransferOrderList.size()>0) {
            for (ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder : zxSkStockTransItemTransferOrderList) {
                //单据总额
                if(CalcUtils.compareToZero(zxSkStockTransItemTransferOrder.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemTransferOrder.getOutAmt());
                }
                //stdPrice ->outCostPrice
                //平均单价->出库成本价
                if(CalcUtils.compareToZero(zxSkStockTransItemTransferOrder.getStdPrice())!=0){
                    zxSkStockTransItemTransferOrder.setOutCostPrice(zxSkStockTransItemTransferOrder.getStdPrice());
                }else {
                    zxSkStockTransItemTransferOrder.setOutCostPrice(new BigDecimal(0));
                }
                //outAmt ->outCostAmt
                //金额->出库成本金额
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
        //添加附件
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
           // 仓库(1)
           dbzxSkStockTransferTransferOrder.setOrgID(zxSkStockTransferTransferOrder.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferTransferOrder.setWhOrgID(zxSkStockTransferTransferOrder.getWhOrgID());
           // 供方ID
           dbzxSkStockTransferTransferOrder.setOutOrgID(zxSkStockTransferTransferOrder.getOutOrgID());
           // 接收单位ID
           dbzxSkStockTransferTransferOrder.setInOrgID(zxSkStockTransferTransferOrder.getInOrgID());
           // 业务类型
           dbzxSkStockTransferTransferOrder.setBizType(zxSkStockTransferTransferOrder.getBizType());
           // 单据编号
           dbzxSkStockTransferTransferOrder.setBillNo(zxSkStockTransferTransferOrder.getBillNo());
           // 物资来源
           dbzxSkStockTransferTransferOrder.setMaterialSource(zxSkStockTransferTransferOrder.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferTransferOrder.setPurpose(zxSkStockTransferTransferOrder.getPurpose());
           // 业务日期
           dbzxSkStockTransferTransferOrder.setBusDate(zxSkStockTransferTransferOrder.getBusDate());
           // 调出单位
           dbzxSkStockTransferTransferOrder.setOutOrgName(zxSkStockTransferTransferOrder.getOutOrgName());
           // 接收单位
           dbzxSkStockTransferTransferOrder.setInOrgName(zxSkStockTransferTransferOrder.getInOrgName());
           // 对内/对外
           dbzxSkStockTransferTransferOrder.setInOrOut(zxSkStockTransferTransferOrder.getInOrOut());
           // 接收仓库id
           dbzxSkStockTransferTransferOrder.setInWhOrgID(zxSkStockTransferTransferOrder.getInWhOrgID());
           // 接收仓库
           dbzxSkStockTransferTransferOrder.setInWhOrg(zxSkStockTransferTransferOrder.getInWhOrg());
           // 单据金额
           dbzxSkStockTransferTransferOrder.setTotalAmt(zxSkStockTransferTransferOrder.getTotalAmt());
           // 经办人(1)
           dbzxSkStockTransferTransferOrder.setOuttransactor(zxSkStockTransferTransferOrder.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferTransferOrder.setIntransactor(zxSkStockTransferTransferOrder.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferTransferOrder.setWaretransactor(zxSkStockTransferTransferOrder.getWaretransactor());
           // 采购人
           dbzxSkStockTransferTransferOrder.setBuyer(zxSkStockTransferTransferOrder.getBuyer());
           // 收料人
           dbzxSkStockTransferTransferOrder.setConsignee(zxSkStockTransferTransferOrder.getConsignee());
           // 审核人
           dbzxSkStockTransferTransferOrder.setAuditor(zxSkStockTransferTransferOrder.getAuditor());
           // 凭证号
           dbzxSkStockTransferTransferOrder.setVoucherNo(zxSkStockTransferTransferOrder.getVoucherNo());
           // 合同号
           dbzxSkStockTransferTransferOrder.setContractNo(zxSkStockTransferTransferOrder.getContractNo());
           // 发票号
           dbzxSkStockTransferTransferOrder.setInvoiceNo(zxSkStockTransferTransferOrder.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferTransferOrder.setBillType(zxSkStockTransferTransferOrder.getBillType());
           // 单据标志
           dbzxSkStockTransferTransferOrder.setBillFlag(zxSkStockTransferTransferOrder.getBillFlag());
           // 单据状态
           dbzxSkStockTransferTransferOrder.setBillStatus(zxSkStockTransferTransferOrder.getBillStatus());
           // 制单人
           dbzxSkStockTransferTransferOrder.setReporter(zxSkStockTransferTransferOrder.getReporter());
           // 扣款类型
           dbzxSkStockTransferTransferOrder.setDeductAmtType(zxSkStockTransferTransferOrder.getDeductAmtType());
           // 备注
           dbzxSkStockTransferTransferOrder.setRemark(zxSkStockTransferTransferOrder.getRemark());
           // 明细
           dbzxSkStockTransferTransferOrder.setCombProp(zxSkStockTransferTransferOrder.getCombProp());
           // 物资类别ID
           dbzxSkStockTransferTransferOrder.setResourceID(zxSkStockTransferTransferOrder.getResourceID());
           // 物资类别
           dbzxSkStockTransferTransferOrder.setResourceName(zxSkStockTransferTransferOrder.getResourceName());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferTransferOrder.setInvoiceNum(zxSkStockTransferTransferOrder.getInvoiceNum());
           // 仓库(3)
           dbzxSkStockTransferTransferOrder.setWarehouseName(zxSkStockTransferTransferOrder.getWarehouseName());
           // 公司id
           dbzxSkStockTransferTransferOrder.setCompanyId(zxSkStockTransferTransferOrder.getCompanyId());
           // 公司名称
           dbzxSkStockTransferTransferOrder.setCompanyName(zxSkStockTransferTransferOrder.getCompanyName());
           // 共通
           dbzxSkStockTransferTransferOrder.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
            zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder.getId());
            List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrders = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
            if(zxSkStockTransItemTransferOrders != null && zxSkStockTransItemTransferOrders.size()>0) {
                zxSkStockTransItemTransferOrder.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemTransferOrderMapper.batchDeleteUpdateZxSkStockTransItemTransferOrder(zxSkStockTransItemTransferOrders, zxSkStockTransItemTransferOrder);
            }
            //明细list
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
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferTransferOrder.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
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
        // 失败
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
                // 删除明细
                ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
                zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder.getId());
                List<ZxSkStockTransItemTransferOrder> deleteZxSkStockTransItems = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
                if(deleteZxSkStockTransItems != null && deleteZxSkStockTransItems.size()>0) {
                    zxSkStockTransItemTransferOrder.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemTransferOrderMapper.batchDeleteUpdateZxSkStockTransItemTransferOrder(deleteZxSkStockTransItems, zxSkStockTransItemTransferOrder);
                }
                //删除附件
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferTransferOrderList);
        }
    }

    //审核调拨单 (出库 只根据数量)
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        //然后审核数据
        ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if(!StrUtil.equals(zxSkStockTransferTransferOrder1.getBillStatus(), "0")) {
            return repEntity.layerMessage("no", "选的数据状态不正，请重新选择！");
        }
        //查询明细
        ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        if(CollUtil.isEmpty(zxSkStockTransItemTransferOrderList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //先减少库存
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
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemTransferOrder item : zxSkStockTransItemTransferOrderListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferTransferOrder1.getCompanyId());
            //项目ID(发料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferTransferOrder1.getOutOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferTransferOrder1.getWhOrgID());
            //物资大类resourceId
            zxSkStock.setResourceId(zxSkStockTransferTransferOrder1.getResourceID());
            //物资大类名称resourceName
            zxSkStock.setResourceName(zxSkStockTransferTransferOrder1.getResourceName());
            //物资Id
            zxSkStock.setResID(item.getResID());
            //资源编号
            zxSkStock.setResCode(item.getResCode());
            //资源名称
            zxSkStock.setResName(item.getResName());
            //规格型号spec
            zxSkStock.setSpec(item.getSpec());
            //单位unit
            zxSkStock.setUnit(item.getResUnit());
            //出库数量
            zxSkStock.setStockQty(item.getOutQty());
            //出库单价
            zxSkStock.setStockPrice(item.getStdPrice());
            //出库金额
            zxSkStock.setStockAmt(item.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.reduceZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferTransferOrder1.setBillStatus("1");
            zxSkStockTransferTransferOrder1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferTransferOrderMapper.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder1);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核异常");
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

    //调拨单收料 (入库 根据数量,调拨单价,调拨总价)
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferTransferOrderIn(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        //然后审核数据
        ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if(!StrUtil.equals(zxSkStockTransferTransferOrder1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "选的数据状态不正，请重新选择！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        //先减少库存
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemTransferOrder item : zxSkStockTransItemTransferOrderList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferTransferOrder1.getCompanyId());
            //收料项目ID
            zxSkStock.setOrgID(zxSkStockTransferTransferOrder1.getInOrgID());
            //收料仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferTransferOrder1.getInWhOrgID());
            //物资大类resourceId
            zxSkStock.setResourceId(zxSkStockTransferTransferOrder1.getResourceID());
            //物资大类名称resourceName
            zxSkStock.setResourceName(zxSkStockTransferTransferOrder1.getResourceName());
            //物资Id
            zxSkStock.setResID(item.getResID());
            //资源编号
            zxSkStock.setResCode(item.getResCode());
            //资源名称
            zxSkStock.setResName(item.getResName());
            //规格型号spec
            zxSkStock.setSpec(item.getSpec());
            //单位unit
            zxSkStock.setUnit(item.getResUnit());
            //入库数量
            zxSkStock.setStockQty(item.getOutQty());
            //入库金额(拨让总价)
            zxSkStock.setStockAmt(item.getTransOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferTransferOrder1.setBillStatus("2");
            zxSkStockTransferTransferOrder1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferTransferOrderMapper.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder1);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核异常");
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

    //调拨单退回 (入库 根据数量,调拨单价,调拨总价)
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferTransferOrderOut(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        //然后审核数据
        ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder1 = zxSkStockTransferTransferOrderMapper.selectByPrimaryKey(zxSkStockTransferTransferOrder.getId());
        if(!StrUtil.equals(zxSkStockTransferTransferOrder1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "选的数据状态不正，请重新选择！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //先减少库存
        ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        zxSkStockTransItemTransferOrder.setBillID(zxSkStockTransferTransferOrder1.getId());
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemTransferOrder item : zxSkStockTransItemTransferOrderList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferTransferOrder1.getCompanyId());
            //回退项目ID
            zxSkStock.setOrgID(zxSkStockTransferTransferOrder1.getOutOrgID());
            //收料仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferTransferOrder1.getWhOrgID());
            //物资大类resourceId
            zxSkStock.setResourceId(zxSkStockTransferTransferOrder1.getResourceID());
            //物资大类名称resourceName
            zxSkStock.setResourceName(zxSkStockTransferTransferOrder1.getResourceName());
            //物资Id
            zxSkStock.setResID(item.getResID());
            //资源编号
            zxSkStock.setResCode(item.getResCode());
            //资源名称
            zxSkStock.setResName(item.getResName());
            //规格型号spec
            zxSkStock.setSpec(item.getSpec());
            //单位unit
            zxSkStock.setUnit(item.getResUnit());
            //入库数量
            zxSkStock.setStockQty(item.getOutQty());
            //入库金额(拨让总价)
            zxSkStock.setStockAmt(item.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = zxSkStockService.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferTransferOrder1.setBillStatus("3");
            zxSkStockTransferTransferOrder1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferTransferOrderMapper.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder1);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核异常");
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

    //合同编号”+“外调字第”+“年份+月份+顺序号”+“号
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
        String no = contractNo + " 外调字第 " + result + "-" + str + " 号";
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
            // 集团全部可见
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                    || StrUtil.equals("admin", userId)) {
                zxCtContract.setCompanyId("");
                zxCtContract.setOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                // 公司只看见自己的
                zxCtContract.setCompanyId(zxCtContract.getOrgID());
                zxCtContract.setOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                // 项目去查询公司id 看公司的.
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
