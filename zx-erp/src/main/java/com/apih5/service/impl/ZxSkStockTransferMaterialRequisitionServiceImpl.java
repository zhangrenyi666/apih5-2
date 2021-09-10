package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.apih5.mybatis.dao.ZxSkStockTransItemMaterialRequisitionMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemWithdrawalMapper;
import com.apih5.mybatis.pojo.*;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockTransferMaterialRequisitionMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransferMaterialRequisition;
import com.apih5.service.ZxSkStockTransferMaterialRequisitionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferMaterialRequisitionService")
public class ZxSkStockTransferMaterialRequisitionServiceImpl implements ZxSkStockTransferMaterialRequisitionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferMaterialRequisitionMapper zxSkStockTransferMaterialRequisitionMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemMaterialRequisitionMapper zxSkStockTransItemMaterialRequisitionMapper;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private SequenceService sequenceService;

//    @Autowired(required = true)
//    private ZxSkResCategoryMaterialsMapper zxSkResCategoryMaterialsMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemWithdrawalMapper zxSkStockTransItemWithdrawalMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionListByCondition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (zxSkStockTransferMaterialRequisition == null) {
            zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferMaterialRequisition.setCompanyId("");
            zxSkStockTransferMaterialRequisition.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferMaterialRequisition.getOrgID2())){
                zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferMaterialRequisition.setCompanyId(zxSkStockTransferMaterialRequisition.getOutOrgID());
            zxSkStockTransferMaterialRequisition.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferMaterialRequisition.getOrgID2())){
                zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOutOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferMaterialRequisition.getOrgID2())){
                zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // 获取数据
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList = zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionList(zxSkStockTransferMaterialRequisition);

        //查询明细
        for (ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition1 : zxSkStockTransferMaterialRequisitionList) {
            ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
            zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition1.getId());
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitions = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
            zxSkStockTransferMaterialRequisition1.setZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisitions);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);

        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionDetails(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (zxSkStockTransferMaterialRequisition == null) {
            zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
        }
        // 获取数据
        ZxSkStockTransferMaterialRequisition dbZxSkStockTransferMaterialRequisition = zxSkStockTransferMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransferMaterialRequisition.getId());
        // 数据存在
        if (dbZxSkStockTransferMaterialRequisition != null) {
            ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
            zxSkStockTransItemMaterialRequisition.setBillID(dbZxSkStockTransferMaterialRequisition.getId());
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitions = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
            dbZxSkStockTransferMaterialRequisition.setZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisitions);
            return repEntity.ok(dbZxSkStockTransferMaterialRequisition);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferMaterialRequisition.setId(UuidUtil.generate());
        zxSkStockTransferMaterialRequisition.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkStockTransferMaterialRequisition.setBillStatus("0");
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList = zxSkStockTransferMaterialRequisition.getZxSkStockTransItemMaterialRequisitionList();
        if(zxSkStockTransItemMaterialRequisitionList != null && zxSkStockTransItemMaterialRequisitionList.size()>0) {
            for (ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition : zxSkStockTransItemMaterialRequisitionList) {
                //单据总额
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemMaterialRequisition.getOutAmt());
                }
                //采购单价 ->出库成本价
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getStdPrice())!=0){
                    zxSkStockTransItemMaterialRequisition.setOutCostPrice(zxSkStockTransItemMaterialRequisition.getStdPrice());
                }else {
                    zxSkStockTransItemMaterialRequisition.setOutCostPrice(new BigDecimal(0));
                }

                //总价 -> 出库成本金额
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getOutAmt())!=0){
                    zxSkStockTransItemMaterialRequisition.setOutCostAmt(zxSkStockTransItemMaterialRequisition.getOutAmt());
                }else {
                    zxSkStockTransItemMaterialRequisition.setOutCostAmt(new BigDecimal(0));
                }

                //领料数量 -> 可退数量
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getOutQty())!=0){
                    zxSkStockTransItemMaterialRequisition.setIsOutNumber(zxSkStockTransItemMaterialRequisition.getOutQty());
                }else {
                    zxSkStockTransItemMaterialRequisition.setIsOutNumber(new BigDecimal(0));
                }
                zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition.getId());
                zxSkStockTransItemMaterialRequisition.setId((UuidUtil.generate()));
                zxSkStockTransItemMaterialRequisition.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemMaterialRequisitionMapper.insert(zxSkStockTransItemMaterialRequisition);
            }
        }
        zxSkStockTransferMaterialRequisition.setTotalAmt(total);
        int flag = zxSkStockTransferMaterialRequisitionMapper.insert(zxSkStockTransferMaterialRequisition);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferMaterialRequisition);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferMaterialRequisition dbzxSkStockTransferMaterialRequisition = zxSkStockTransferMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransferMaterialRequisition.getId());
        if (dbzxSkStockTransferMaterialRequisition != null && StrUtil.isNotEmpty(dbzxSkStockTransferMaterialRequisition.getId())) {
           // 仓库(1)
           dbzxSkStockTransferMaterialRequisition.setOrgID(zxSkStockTransferMaterialRequisition.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferMaterialRequisition.setWhOrgID(zxSkStockTransferMaterialRequisition.getWhOrgID());
           // 发料单位ID
           dbzxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOutOrgID());
           // 领料部门ID
           dbzxSkStockTransferMaterialRequisition.setInOrgID(zxSkStockTransferMaterialRequisition.getInOrgID());
           // 业务类型
           dbzxSkStockTransferMaterialRequisition.setBizType(zxSkStockTransferMaterialRequisition.getBizType());
           // 单据编号
           dbzxSkStockTransferMaterialRequisition.setBillNo(zxSkStockTransferMaterialRequisition.getBillNo());
           // 物资来源
           dbzxSkStockTransferMaterialRequisition.setMaterialSource(zxSkStockTransferMaterialRequisition.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferMaterialRequisition.setPurpose(zxSkStockTransferMaterialRequisition.getPurpose());
           // 业务日期
           dbzxSkStockTransferMaterialRequisition.setBusDate(zxSkStockTransferMaterialRequisition.getBusDate());
           // 发料单位
           dbzxSkStockTransferMaterialRequisition.setOutOrgName(zxSkStockTransferMaterialRequisition.getOutOrgName());
           // 领料部门
           dbzxSkStockTransferMaterialRequisition.setInOrgName(zxSkStockTransferMaterialRequisition.getInOrgName());
           // 单据金额
           dbzxSkStockTransferMaterialRequisition.setTotalAmt(zxSkStockTransferMaterialRequisition.getTotalAmt());
           // 经办人(1)
           dbzxSkStockTransferMaterialRequisition.setOuttransactor(zxSkStockTransferMaterialRequisition.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferMaterialRequisition.setIntransactor(zxSkStockTransferMaterialRequisition.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferMaterialRequisition.setWaretransactor(zxSkStockTransferMaterialRequisition.getWaretransactor());
           // 发料
           dbzxSkStockTransferMaterialRequisition.setBuyer(zxSkStockTransferMaterialRequisition.getBuyer());
           // 料帐
           dbzxSkStockTransferMaterialRequisition.setConsignee(zxSkStockTransferMaterialRequisition.getConsignee());
           // 操作人
           dbzxSkStockTransferMaterialRequisition.setAuditor(zxSkStockTransferMaterialRequisition.getAuditor());
           // 凭证号
           dbzxSkStockTransferMaterialRequisition.setVoucherNo(zxSkStockTransferMaterialRequisition.getVoucherNo());
           // 合同号
           dbzxSkStockTransferMaterialRequisition.setContractNo(zxSkStockTransferMaterialRequisition.getContractNo());
           // 发票号
           dbzxSkStockTransferMaterialRequisition.setInvoiceNo(zxSkStockTransferMaterialRequisition.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferMaterialRequisition.setBillType(zxSkStockTransferMaterialRequisition.getBillType());
           // 领料
           dbzxSkStockTransferMaterialRequisition.setBillFlag(zxSkStockTransferMaterialRequisition.getBillFlag());
           // 单据状态
           dbzxSkStockTransferMaterialRequisition.setBillStatus(zxSkStockTransferMaterialRequisition.getBillStatus());
           // 制单人
           dbzxSkStockTransferMaterialRequisition.setReporter(zxSkStockTransferMaterialRequisition.getReporter());
           // 扣款类型
           dbzxSkStockTransferMaterialRequisition.setDeductAmtType(zxSkStockTransferMaterialRequisition.getDeductAmtType());
           // 备注
           dbzxSkStockTransferMaterialRequisition.setRemark(zxSkStockTransferMaterialRequisition.getRemark());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferMaterialRequisition.setInvoiceNum(zxSkStockTransferMaterialRequisition.getInvoiceNum());
           // 物资类别ID
           dbzxSkStockTransferMaterialRequisition.setResourceID(zxSkStockTransferMaterialRequisition.getResourceID());
           // 物资类别
           dbzxSkStockTransferMaterialRequisition.setResourceName(zxSkStockTransferMaterialRequisition.getResourceName());
           // 分部分项ID
           dbzxSkStockTransferMaterialRequisition.setCbsID(zxSkStockTransferMaterialRequisition.getCbsID());
           // 分部分项
           dbzxSkStockTransferMaterialRequisition.setCbsName(zxSkStockTransferMaterialRequisition.getCbsName());
           // 仓库(3)
           dbzxSkStockTransferMaterialRequisition.setWarehouseName(zxSkStockTransferMaterialRequisition.getWarehouseName());
           // 公司id
           dbzxSkStockTransferMaterialRequisition.setCompanyId(zxSkStockTransferMaterialRequisition.getCompanyId());
           // 公司名称
           dbzxSkStockTransferMaterialRequisition.setCompanyName(zxSkStockTransferMaterialRequisition.getCompanyName());
           // 共通
           dbzxSkStockTransferMaterialRequisition.setModifyUserInfo(userKey, realName);


            //删除在新增
            ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
            zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition.getId());
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitions = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
            if(zxSkStockTransItemMaterialRequisitions != null && zxSkStockTransItemMaterialRequisitions.size()>0) {
                zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransItemMaterialRequisition(zxSkStockTransItemMaterialRequisitions, zxSkStockTransItemMaterialRequisition);
            }
            //明细list
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList = zxSkStockTransferMaterialRequisition.getZxSkStockTransItemMaterialRequisitionList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemMaterialRequisitionList != null && zxSkStockTransItemMaterialRequisitionList.size()>0) {
                for(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition1 : zxSkStockTransItemMaterialRequisitionList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemMaterialRequisition1.getOutAmt());
                    zxSkStockTransItemMaterialRequisition1.setId(UuidUtil.generate());
                    zxSkStockTransItemMaterialRequisition1.setBillID(zxSkStockTransferMaterialRequisition.getId());
                    zxSkStockTransItemMaterialRequisition1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemMaterialRequisitionMapper.insert(zxSkStockTransItemMaterialRequisition1);
                }
            }
            dbzxSkStockTransferMaterialRequisition.setTotalAmt(total);
            flag = zxSkStockTransferMaterialRequisitionMapper.updateByPrimaryKey(dbzxSkStockTransferMaterialRequisition);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferMaterialRequisition);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferMaterialRequisition(List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferMaterialRequisitionList != null && zxSkStockTransferMaterialRequisitionList.size() > 0) {
            for (ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition : zxSkStockTransferMaterialRequisitionList) {
                // 删除明细
                ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
                zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition.getId());
                List<ZxSkStockTransItemMaterialRequisition> deleteZxSkStockTransItems = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
                if(deleteZxSkStockTransItems != null && deleteZxSkStockTransItems.size()>0) {
                    zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransItemMaterialRequisition(deleteZxSkStockTransItems, zxSkStockTransItemMaterialRequisition);
                }
            }
           ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
           zxSkStockTransferMaterialRequisition.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisitionList, zxSkStockTransferMaterialRequisition);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferMaterialRequisitionList);
        }
    }

    //审核
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        //审核数据
        ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition1 = zxSkStockTransferMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransferMaterialRequisition.getId());
        if(StrUtil.equals(zxSkStockTransferMaterialRequisition1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
        zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition1.getId());
        List<ZxSkStockTransItemMaterialRequisition> itemList = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
        if(CollUtil.isEmpty(itemList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //先减少库存
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionListNew = new ArrayList<>();
        itemList.parallelStream()
                .collect(Collectors.groupingBy(o ->
                        (o.getResID()+o.getStdPrice()), Collectors.toList())).forEach(
                (id,transfer) ->{
                    transfer.stream().reduce((a,b)-> new ZxSkStockTransItemMaterialRequisition(
                            //resId
                            a.getResID(),
                            a.getResCode(),
                            a.getResName(),
                            a.getSpec(),
                            a.getResUnit(),
                            a.getStdPrice(),
                            CalcUtils.calcAdd(a.getOutQty(),b.getOutQty()),
                            CalcUtils.calcAdd(a.getOutAmt(),b.getOutAmt())
                    )).ifPresent(zxSkStockTransItemMaterialRequisitionListNew::add);
                }
        );
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemMaterialRequisition item : zxSkStockTransItemMaterialRequisitionListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferMaterialRequisition1.getCompanyId());
            //项目ID(发料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferMaterialRequisition1.getOutOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferMaterialRequisition1.getWhOrgID());
            //物资大类resourceId
            zxSkStock.setResourceId(zxSkStockTransferMaterialRequisition1.getResourceID());
            //物资大类名称resourceName
            zxSkStock.setResourceName(zxSkStockTransferMaterialRequisition1.getResourceName());
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
//          batchNo
            //入库数量
            zxSkStock.setStockQty(item.getOutQty());
            //出库单价
            zxSkStock.setStockPrice(item.getStdPrice());
            //出库金额
            zxSkStock.setStockAmt(item.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        //减少库存前先比较单价.(如果库存单价和单据单价相同,则继续进行,如果不同则提示并修改物资的信息)
        ResponseEntity responseEntity = ZxSkStockServiceImpl.reduceZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferMaterialRequisition1.setBillStatus("1");
            zxSkStockTransferMaterialRequisition1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferMaterialRequisitionMapper.checkZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisition1);
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

    //合同编号”+“领字第”+“年份-月份-顺序号”+“号
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionNo(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if(StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID()) || zxSkStockTransferMaterialRequisition.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferMaterialRequisition.getOutOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferMaterialRequisition.getBusDate());
        int month = DateUtil.month(zxSkStockTransferMaterialRequisition.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferMaterialRequisition.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferMaterialRequisitionMapper.getZxSkStockTransferMaterialRequisitionCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 领字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    //whOrgID,outOrgID
    //(根据仓库id,项目id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionInOrgName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if ( StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // 获取数据
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionInOrgNameList(zxSkStockTransferMaterialRequisition);
        // 得到分页信息
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    //whOrgID,outOrgID
    //(根据仓库id,项目id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionCbsName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // 获取数据
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionCbsNameList(zxSkStockTransferMaterialRequisition);
        // 得到分页信息
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    //whOrgID,outOrgID ,inOrgID
    //(根据仓库id,项目id,退料部门id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionResourceName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getInOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // 获取数据
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionResourceNameList(zxSkStockTransferMaterialRequisition);
        // 得到分页信息
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    //whOrgID,outOrgID ,inOrgID,resourceID
    //(根据仓库id,项目id,退料部门id,分类id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionResName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getResourceID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // 获取数据
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionResNameList(zxSkStockTransferMaterialRequisition);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemMaterialRequisition> p = new PageInfo<>(zxSkStockTransItemMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransItemMaterialRequisitionList, p.getTotal());
    }




}
