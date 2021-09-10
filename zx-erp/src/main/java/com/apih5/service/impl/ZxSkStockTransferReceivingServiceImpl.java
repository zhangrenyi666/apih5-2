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
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkStockTransferReceivingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferReceivingService")
public class ZxSkStockTransferReceivingServiceImpl implements ZxSkStockTransferReceivingService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    //收料单
    @Autowired(required = true)
    private ZxSkStockTransferReceivingMapper zxSkStockTransferReceivingMapper;

    //收料单明细
    @Autowired(required = true)
    private ZxSkStockTransItemReceivingMapper zxSkStockTransItemReceivingMapper;

    //退货单
    @Autowired(required = true)
    private ZxSkStockTransferSalesReturnMapper zxSkStockTransferSalesReturnMapper;

    //退货单明细
    @Autowired(required = true)
    private ZxSkStockTransItemSalesReturnMapper zxSkStockTransItemSalesReturnMapper;

    //周转材入库单
    @Autowired(required = true)
    private ZxSkTurnoverInMapper zxSkTurnoverInMapper;

    //周转材入库单明细
    @Autowired(required = true)
    private ZxSkTurnoverInItemMapper zxSkTurnoverInItemMapper;

    //周转材退货单
    @Autowired(required = true)
    private ZxSkReturnsMapper zxSkReturnsMapper;

    //周转材退货单明细
    @Autowired(required = true)
    private ZxSkReturnsItemMapper zxSkReturnsItemMapper;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkStockTransferReceivingListByCondition(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        if (zxSkStockTransferReceiving == null) {
            zxSkStockTransferReceiving = new ZxSkStockTransferReceiving();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferReceiving.setCompanyId("");
            zxSkStockTransferReceiving.setInOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferReceiving.getOrgID2())){
                zxSkStockTransferReceiving.setInOrgID(zxSkStockTransferReceiving.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferReceiving.setCompanyId(zxSkStockTransferReceiving.getInOrgID());
            zxSkStockTransferReceiving.setInOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferReceiving.getOrgID2())){
                zxSkStockTransferReceiving.setInOrgID(zxSkStockTransferReceiving.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferReceiving.setInOrgID(zxSkStockTransferReceiving.getInOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferReceiving.getOrgID2())){
                zxSkStockTransferReceiving.setInOrgID(zxSkStockTransferReceiving.getOrgID2());
            }
        }

        // 分页查询
        PageHelper.startPage(zxSkStockTransferReceiving.getPage(),zxSkStockTransferReceiving.getLimit());
        // 获取数据
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkStockTransferReceivingMapper.selectByZxSkStockTransferReceivingList(zxSkStockTransferReceiving);
        //查询明细
        for (ZxSkStockTransferReceiving zxSkStockTransferReceiving1 : zxSkStockTransferReceivingList) {
            ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
            zxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving1.getId());
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
            zxSkStockTransferReceiving1.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivings);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferReceivingDetails(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        if (zxSkStockTransferReceiving == null) {
            zxSkStockTransferReceiving = new ZxSkStockTransferReceiving();
        }
        // 获取数据
        ZxSkStockTransferReceiving dbZxSkStockTransferReceiving = zxSkStockTransferReceivingMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
        // 数据存在
        if (dbZxSkStockTransferReceiving != null) {
            ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
            zxSkStockTransItemReceiving.setBillID(dbZxSkStockTransferReceiving.getId());
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
            dbZxSkStockTransferReceiving.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivings);
            return repEntity.ok(dbZxSkStockTransferReceiving);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferReceiving.setId(UuidUtil.generate());
        zxSkStockTransferReceiving.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        if(StrUtil.isEmpty(zxSkStockTransferReceiving.getBillStatus())){
            zxSkStockTransferReceiving.setBillStatus("0");
        }
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = zxSkStockTransferReceiving.getZxSkStockTransItemReceivingList();
        if(zxSkStockTransItemReceivingList != null && zxSkStockTransItemReceivingList.size()>0) {
            for (ZxSkStockTransItemReceiving zxSkStockTransItemReceiving : zxSkStockTransItemReceivingList) {
                //入账金额
                if(CalcUtils.compareToZero(zxSkStockTransItemReceiving.getInAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemReceiving.getInAmt());
                }
                //计算采购单价  入账金额除以数量的  实际库存中(收料单的单价)
                if(CalcUtils.compareToZero(zxSkStockTransItemReceiving.getInAmt())!=0
                        &&CalcUtils.compareToZero(zxSkStockTransItemReceiving.getInQty())!=0){
                    zxSkStockTransItemReceiving.setStdPrice(CalcUtils.calcDivide(zxSkStockTransItemReceiving.getInAmt(),zxSkStockTransItemReceiving.getInQty(),6));
                }else {
                    zxSkStockTransItemReceiving.setStdPrice(new BigDecimal(0));
                }
                //不含税金额 -> 不含税总价
                if(CalcUtils.compareToZero(zxSkStockTransItemReceiving.getResAllFeeNoTax())!=0){
                    zxSkStockTransItemReceiving.setInAmtNoTax(zxSkStockTransItemReceiving.getResAllFeeNoTax());
                }else{
                    zxSkStockTransItemReceiving.setInAmtNoTax(new BigDecimal(0));
                }
                //收料数量 -> 可退数量
                if(CalcUtils.compareToZero(zxSkStockTransItemReceiving.getInQty())!=0){
                    zxSkStockTransItemReceiving.setIsOutNumber(zxSkStockTransItemReceiving.getInQty());
                }else{
                    zxSkStockTransItemReceiving.setIsOutNumber(new BigDecimal(0));
                }
                //是否结算  0:否,1:是
                zxSkStockTransItemReceiving.setSettlementStatus("0");

                zxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving.getId());
                zxSkStockTransItemReceiving.setId((UuidUtil.generate()));
                zxSkStockTransItemReceiving.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemReceivingMapper.insert(zxSkStockTransItemReceiving);
            }
        }
        zxSkStockTransferReceiving.setTotalAmt(total);
        int flag = zxSkStockTransferReceivingMapper.insert(zxSkStockTransferReceiving);

        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferReceiving);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferReceiving dbzxSkStockTransferReceiving = zxSkStockTransferReceivingMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
        if (dbzxSkStockTransferReceiving != null && StrUtil.isNotEmpty(dbzxSkStockTransferReceiving.getId())) {
           // 仓库(1)
           dbzxSkStockTransferReceiving.setOrgID(zxSkStockTransferReceiving.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferReceiving.setWhOrgID(zxSkStockTransferReceiving.getWhOrgID());
           // 供货单位ID
           dbzxSkStockTransferReceiving.setOutOrgID(zxSkStockTransferReceiving.getOutOrgID());
           // 收料单位ID
           dbzxSkStockTransferReceiving.setInOrgID(zxSkStockTransferReceiving.getInOrgID());
           // 业务类型
           dbzxSkStockTransferReceiving.setBizType(zxSkStockTransferReceiving.getBizType());
           // 单据编号
           dbzxSkStockTransferReceiving.setBillNo(zxSkStockTransferReceiving.getBillNo());
           // 物资来源
           dbzxSkStockTransferReceiving.setMaterialSource(zxSkStockTransferReceiving.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferReceiving.setPurpose(zxSkStockTransferReceiving.getPurpose());
           // 业务日期
           dbzxSkStockTransferReceiving.setBusDate(zxSkStockTransferReceiving.getBusDate());
           // 供货单位
           dbzxSkStockTransferReceiving.setOutOrgName(zxSkStockTransferReceiving.getOutOrgName());
           // 收料单位
           dbzxSkStockTransferReceiving.setInOrgName(zxSkStockTransferReceiving.getInOrgName());
           // 单据金额

           // 经办人(1)
           dbzxSkStockTransferReceiving.setOuttransactor(zxSkStockTransferReceiving.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferReceiving.setIntransactor(zxSkStockTransferReceiving.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferReceiving.setWaretransactor(zxSkStockTransferReceiving.getWaretransactor());
           // 发票号
           dbzxSkStockTransferReceiving.setBuyer(zxSkStockTransferReceiving.getBuyer());
           // 收料人
           dbzxSkStockTransferReceiving.setConsignee(zxSkStockTransferReceiving.getConsignee());
           // 审核人
           dbzxSkStockTransferReceiving.setAuditor(zxSkStockTransferReceiving.getAuditor());
           // 凭证号
           dbzxSkStockTransferReceiving.setVoucherNo(zxSkStockTransferReceiving.getVoucherNo());
           // 合同编号
           dbzxSkStockTransferReceiving.setContractNo(zxSkStockTransferReceiving.getContractNo());
           // 发票号
           dbzxSkStockTransferReceiving.setInvoiceNo(zxSkStockTransferReceiving.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferReceiving.setBillType(zxSkStockTransferReceiving.getBillType());
           // 单据标志
           dbzxSkStockTransferReceiving.setBillFlag(zxSkStockTransferReceiving.getBillFlag());
           // 单据状态
           dbzxSkStockTransferReceiving.setBillStatus(zxSkStockTransferReceiving.getBillStatus());
           // 制单人
           dbzxSkStockTransferReceiving.setReporter(zxSkStockTransferReceiving.getReporter());
           // 扣款类型
           dbzxSkStockTransferReceiving.setDeductAmtType(zxSkStockTransferReceiving.getDeductAmtType());
           // 备注
           dbzxSkStockTransferReceiving.setRemark(zxSkStockTransferReceiving.getRemark());
           // 明细
           dbzxSkStockTransferReceiving.setCombProp(zxSkStockTransferReceiving.getCombProp());
           // 采购合同ID
           dbzxSkStockTransferReceiving.setPurchaseContractID(zxSkStockTransferReceiving.getPurchaseContractID());
           // 采购合同
           dbzxSkStockTransferReceiving.setPurchaseContract(zxSkStockTransferReceiving.getPurchaseContract());
           // 登录机构ID
           dbzxSkStockTransferReceiving.setLoginOrgID(zxSkStockTransferReceiving.getLoginOrgID());
           // 物资类别ID
           dbzxSkStockTransferReceiving.setResourceID(zxSkStockTransferReceiving.getResourceID());
           // 物资类别
           dbzxSkStockTransferReceiving.setResourceName(zxSkStockTransferReceiving.getResourceName());
           // cbsID
           dbzxSkStockTransferReceiving.setCbsID(zxSkStockTransferReceiving.getCbsID());
           // cbs名称
           dbzxSkStockTransferReceiving.setCbsName(zxSkStockTransferReceiving.getCbsName());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferReceiving.setInvoiceNum(zxSkStockTransferReceiving.getInvoiceNum());
           // 供货单位类型
           dbzxSkStockTransferReceiving.setOutOrgType(zxSkStockTransferReceiving.getOutOrgType());
           // 采购类别
           dbzxSkStockTransferReceiving.setPurchType(zxSkStockTransferReceiving.getPurchType());
           // 税率(%)
           dbzxSkStockTransferReceiving.setTaxRate(zxSkStockTransferReceiving.getTaxRate());
           // 是否抵扣
           dbzxSkStockTransferReceiving.setIsDeduct(zxSkStockTransferReceiving.getIsDeduct());
           // 仓库(3)
           dbzxSkStockTransferReceiving.setWarehouseName(zxSkStockTransferReceiving.getWarehouseName());
           // 是否预收
           dbzxSkStockTransferReceiving.setOrNotPrecollecte(zxSkStockTransferReceiving.getOrNotPrecollecte());
           // 公司id
           dbzxSkStockTransferReceiving.setCompanyId(zxSkStockTransferReceiving.getCompanyId());
           // 公司名称
           dbzxSkStockTransferReceiving.setCompanyName(zxSkStockTransferReceiving.getCompanyName());
           // 共通
           dbzxSkStockTransferReceiving.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
            zxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving.getId());
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
            if(zxSkStockTransItemReceivings != null && zxSkStockTransItemReceivings.size()>0) {
                zxSkStockTransItemReceiving.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemReceivingMapper.batchDeleteUpdateZxSkStockTransItemReceiving(zxSkStockTransItemReceivings, zxSkStockTransItemReceiving);
            }
            //明细list
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = zxSkStockTransferReceiving.getZxSkStockTransItemReceivingList();
            //单据金额
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemReceivingList != null && zxSkStockTransItemReceivingList.size()>0) {
                for(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving1 : zxSkStockTransItemReceivingList) {
                    total = total.add(zxSkStockTransItemReceiving1.getInAmt());
                    //计算采购单价  入账金额除以数量的  实际库存中(收料单的单价)
                    if(CalcUtils.compareToZero(zxSkStockTransItemReceiving1.getInAmt())!=0
                            &&CalcUtils.compareToZero(zxSkStockTransItemReceiving1.getInQty())!=0){
                        zxSkStockTransItemReceiving1.setStdPrice(CalcUtils.calcDivide(zxSkStockTransItemReceiving1.getInAmt(),zxSkStockTransItemReceiving1.getInQty(),6));
                    }else {
                        zxSkStockTransItemReceiving1.setStdPrice(new BigDecimal(0));
                    }

                    //不含税金额 -> 不含税总价
                    if(CalcUtils.compareToZero(zxSkStockTransItemReceiving1.getResAllFeeNoTax())!=0){
                        zxSkStockTransItemReceiving1.setInAmtNoTax(zxSkStockTransItemReceiving1.getResAllFeeNoTax());
                    }else{
                        zxSkStockTransItemReceiving1.setInAmtNoTax(new BigDecimal(0));
                    }
                    //收料数量 -> 可退数量
                    if(CalcUtils.compareToZero(zxSkStockTransItemReceiving1.getInQty())!=0){
                        zxSkStockTransItemReceiving1.setIsOutNumber(zxSkStockTransItemReceiving1.getInQty());
                    }else{
                        zxSkStockTransItemReceiving1.setIsOutNumber(new BigDecimal(0));
                    }
                    //是否结算  0:否,1:是
                    zxSkStockTransItemReceiving1.setSettlementStatus("0");
                    zxSkStockTransItemReceiving1.setId(UuidUtil.generate());
                    zxSkStockTransItemReceiving1.setBillID(zxSkStockTransferReceiving.getId());
                    zxSkStockTransItemReceiving1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemReceivingMapper.insert(zxSkStockTransItemReceiving1);
                }
            }
            //单据金额
            dbzxSkStockTransferReceiving.setTotalAmt(total);
            flag = zxSkStockTransferReceivingMapper.updateByPrimaryKey(dbzxSkStockTransferReceiving);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferReceiving);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferReceiving(List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferReceivingList != null && zxSkStockTransferReceivingList.size() > 0) {
            for (ZxSkStockTransferReceiving zxSkStockTransferReceiving : zxSkStockTransferReceivingList) {
                // 删除明细
                ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
                zxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving.getId());
                List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
                if(zxSkStockTransItemReceivings != null && zxSkStockTransItemReceivings.size()>0) {
                    zxSkStockTransItemReceiving.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemReceivingMapper.batchDeleteUpdateZxSkStockTransItemReceiving(zxSkStockTransItemReceivings, zxSkStockTransItemReceiving);
                }
            }
           ZxSkStockTransferReceiving zxSkStockTransferReceiving = new ZxSkStockTransferReceiving();
           zxSkStockTransferReceiving.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferReceivingMapper.batchDeleteUpdateZxSkStockTransferReceiving(zxSkStockTransferReceivingList, zxSkStockTransferReceiving);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferReceivingList);
        }
    }

    //审核
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        //审核数据
        ZxSkStockTransferReceiving zxSkStockTransferReceiving1 = zxSkStockTransferReceivingMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
        if(StrUtil.equals(zxSkStockTransferReceiving1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkStockTransItemReceiving dbzxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
        dbzxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving1.getId());
        List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(dbzxSkStockTransItemReceiving);
        if(CollUtil.isEmpty(zxSkStockTransItemReceivingList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //先添加库存
        List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingListNew = new ArrayList<>();
        zxSkStockTransItemReceivingList
                .parallelStream()
                .collect(Collectors.groupingBy(o->(o.getResID()+o.getInPrice()),Collectors.toList())).forEach(
                (id,transfer) -> {
                    transfer.stream().reduce((a,b)-> new ZxSkStockTransItemReceiving(
                            //resId
                            a.getResID(),
                            //code
                            a.getResCode(),
                            //name
                            a.getResName(),
                            //spec
                            a.getSpec(),
                            //unit
                            a.getResUnit(),
                            //qty
                            CalcUtils.calcAdd(a.getInQty(),b.getInQty()),
                            //amt
                            CalcUtils.calcAdd(a.getInAmt(),b.getInAmt())
                    )).ifPresent(zxSkStockTransItemReceivingListNew::add);
                }
        );
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemReceiving zxSkStockTransItemReceiving : zxSkStockTransItemReceivingListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferReceiving1.getCompanyId());
            //项目ID(收料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferReceiving1.getInOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferReceiving1.getWhOrgID());
            //物资大类resourceId
            zxSkStock.setResourceId(zxSkStockTransferReceiving1.getResourceID());
            //物资大类名称resourceName
            zxSkStock.setResourceName(zxSkStockTransferReceiving1.getResourceName());
            //物资Id
            zxSkStock.setResID(zxSkStockTransItemReceiving.getResID());
            //资源编号
            zxSkStock.setResCode(zxSkStockTransItemReceiving.getResCode());
            //资源名称
            zxSkStock.setResName(zxSkStockTransItemReceiving.getResName());
            //规格型号spec
            zxSkStock.setSpec(zxSkStockTransItemReceiving.getSpec());
            //单位unit
            zxSkStock.setUnit(zxSkStockTransItemReceiving.getResUnit());
            //库存数量/实收数量
            zxSkStock.setStockQty(zxSkStockTransItemReceiving.getInQty());
            //库存金额(不含税金额)//改了 入账金额
            zxSkStock.setStockAmt(zxSkStockTransItemReceiving.getInAmt());
            zxSkStockList.add(zxSkStock);
        }
        ResponseEntity responseEntity = ZxSkStockServiceImpl.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferReceiving1.setBillStatus("1");
            zxSkStockTransferReceiving1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferReceivingMapper.checkZxSkStockTransferReceiving(zxSkStockTransferReceiving1);
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

    @Override
    public ResponseEntity getZxSkStockTransferReceivingNo(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        if(StrUtil.isEmpty(zxSkStockTransferReceiving.getOrgID()) || zxSkStockTransferReceiving.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferReceiving.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferReceiving.getBusDate());
        int month = DateUtil.month(zxSkStockTransferReceiving.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferReceiving.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferReceivingMapper.getZxSkStockTransferReceivingCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 收字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public List<ZxSkStockTransItemReceiving> getZxSkStockTransferReceivingByContractNoItemList(List<String> zxSkStockTransferReceivingList) {
        List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = new ArrayList<>();
        //查询明细
        for (String zxSkStockTransferReceiving1 : zxSkStockTransferReceivingList) {
            ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
            zxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving1);
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
            for (ZxSkStockTransItemReceiving skStockTransItemReceiving : zxSkStockTransItemReceivingList) {
                ZxSkStockTransferReceiving zxSkStockTransferReceiving = zxSkStockTransferReceivingMapper.selectByPrimaryKey(skStockTransItemReceiving.getBillID());
                //领料单税率是非必填的,物资合同数据是必填的
                skStockTransItemReceiving.setTaxRate(StrUtil.isEmpty(zxSkStockTransferReceiving.getTaxRate())?"0":zxSkStockTransferReceiving.getTaxRate());
            }
            zxSkStockTransItemReceivings.addAll(zxSkStockTransItemReceivingList);
        }
        return zxSkStockTransItemReceivings;
    }


    //接口名:getZxSkStockTransferReceivingByContractNoList
    /*
        返回:
        收料单         合同
        退货单
        周转材入库单   合同
        周转材退货单

        参数:
        1.id        =>id
        2.billNo    =>单据编号
        3.bizType   =>单据类型 固定: 收料单,退货单,周转材入库单,周转材退货单
        4.busDate   =>业务日期(时间戳)
        5.purchaseContractID    =>合同id
        6.contractNo            =>合同编号
        7.purchaseContract      =>合同名称
     */
    /*
        传入:
        参数:
        起始日期 startTime
        截止日期  (业务日期) endTime
        甲方:  收料单位(项目)  inOrgID
        乙方:  供应商 outOrgID
     */
    @Override
    public List<ZxSkStockTransferReceiving> getZxSkStockTransferReceivingByContractNoList(ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        if(zxSkStockTransferReceiving.getStartTime() == null
                || zxSkStockTransferReceiving.getEndTime() == null
                || StrUtil.isEmpty(zxSkStockTransferReceiving.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferReceiving.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferReceiving.getPurchaseContractID())){
            return new ArrayList<>();
        }
        return zxSkStockTransferReceivingMapper.selectOrderAll(zxSkStockTransferReceiving);
    }

    //接口名:getZxSkStockTransferReceivingByContractNoItemListNew
    /*
        返回:
        List<ZxSkStockTransItemReceiving>
        参数:
            1.id                              =>id
            2.单据编号                        =>stockTransBillNo
            3.物资编码                        =>resCode
            4.物资名称                        =>resName
            5.规格型号                        =>spec
            6.计量单位                        =>resUnit
            7.税率                            =>taxRate
            8.本期结算数量(实收,退货数量)     =>inQty
            9.本期结算单价(含税单价)          =>inPrice
     */
    /*
        传入:
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList
        参数:
            1.id  => id
            2.单据类型  => bizType
     */
    public List<ZxSkStockTransItemReceiving> getZxSkStockTransferReceivingByContractNoItemListNew(List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList) {
        List<ZxSkStockTransItemReceiving> getReceivingItemList = new ArrayList<>();
        for (ZxSkStockTransferReceiving zxSkStockTransferReceiving : zxSkStockTransferReceivingList) {
            switch (zxSkStockTransferReceiving.getBizType()) {
                case "收料单":
                    ZxSkStockTransItemReceiving receivingItem = new ZxSkStockTransItemReceiving();
                    receivingItem.setBillID(zxSkStockTransferReceiving.getId());
                    ZxSkStockTransferReceiving dbzxSkStockTransferReceiving = zxSkStockTransferReceivingMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
                    List<ZxSkStockTransItemReceiving> dbzxSkStockTransItemReceivingList = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(receivingItem);
                    for (ZxSkStockTransItemReceiving dbzxSkStockTransItemReceiving : dbzxSkStockTransItemReceivingList) {
                        if(StrUtil.equals(dbzxSkStockTransItemReceiving.getPrecollecte(),"0")){
                            ZxSkStockTransItemReceiving convertReceivingItem = new ZxSkStockTransItemReceiving();
                            //id
                            convertReceivingItem.setId(dbzxSkStockTransItemReceiving.getId());
                            //单据类型
                            convertReceivingItem.setBizType("收料单");
                            //主表id
                            convertReceivingItem.setBillID(dbzxSkStockTransferReceiving.getId());
                            //单据编号
                            convertReceivingItem.setStockTransBillNo(dbzxSkStockTransferReceiving.getBillNo());
                            //物资编码
                            convertReceivingItem.setResCode(dbzxSkStockTransItemReceiving.getResCode());
                            //物资名称
                            convertReceivingItem.setResName(dbzxSkStockTransItemReceiving.getResName());
                            //规格型号
                            convertReceivingItem.setSpec(dbzxSkStockTransItemReceiving.getSpec());
                            //计量单位
                            convertReceivingItem.setResUnit(dbzxSkStockTransItemReceiving.getResUnit());
                            //税率
                            convertReceivingItem.setTaxRate(dbzxSkStockTransferReceiving.getTaxRate());
                            //本期结算数量(实收,退货数量)
                            convertReceivingItem.setInQty(dbzxSkStockTransItemReceiving.getInQty());
                            //本期结算单价(含税单价)
                            convertReceivingItem.setInPrice(dbzxSkStockTransItemReceiving.getInPrice());
                            getReceivingItemList.add(convertReceivingItem);
                        }
                    }
                    break;
                case "退货单":
                    ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
                    zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferReceiving.getId());
                    ZxSkStockTransferSalesReturn dbzxSkStockTransferSalesReturn = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
                    List<ZxSkStockTransItemSalesReturn> dbzxSkStockTransItemSalesReturnList = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
                    for (ZxSkStockTransItemSalesReturn dbzxSkStockTransItemSalesReturn : dbzxSkStockTransItemSalesReturnList) {
                        ZxSkStockTransItemReceiving convertReceivingItem = new ZxSkStockTransItemReceiving();
                        //id
                        convertReceivingItem.setId(dbzxSkStockTransItemSalesReturn.getId());
                        //主表id
                        convertReceivingItem.setBillID(dbzxSkStockTransferSalesReturn.getId());
                        //单据类型
                        convertReceivingItem.setBizType("退货单");
                        //单据编号
                        convertReceivingItem.setStockTransBillNo(dbzxSkStockTransferSalesReturn.getBillNo());
                        //物资编码
                        convertReceivingItem.setResCode(dbzxSkStockTransItemSalesReturn.getResCode());
                        //物资名称
                        convertReceivingItem.setResName(dbzxSkStockTransItemSalesReturn.getResName());
                        //规格型号
                        convertReceivingItem.setSpec(dbzxSkStockTransItemSalesReturn.getSpec());
                        //计量单位
                        convertReceivingItem.setResUnit(dbzxSkStockTransItemSalesReturn.getResUnit());
                        //税率
                        convertReceivingItem.setTaxRate(dbzxSkStockTransferSalesReturn.getTaxRate());
                        //本期结算数量(实收,退货数量)
                        convertReceivingItem.setInQty(dbzxSkStockTransItemSalesReturn.getInQty());
                        //本期结算单价(含税单价)
                        convertReceivingItem.setInPrice(dbzxSkStockTransItemSalesReturn.getInPrice());
                        getReceivingItemList.add(convertReceivingItem);
                    }
                    break;
                case "周转材入库单":
                    ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
                    zxSkTurnoverInItem.setBillID(zxSkStockTransferReceiving.getId());
                    ZxSkTurnoverIn dbzxSkTurnoverIn = zxSkTurnoverInMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
                    List<ZxSkTurnoverInItem> dbzxSkTurnoverInItemList = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
                    for (ZxSkTurnoverInItem dbzxSkTurnoverInItem : dbzxSkTurnoverInItemList) {
                        ZxSkStockTransItemReceiving convertReceivingItem = new ZxSkStockTransItemReceiving();
                        //id
                        convertReceivingItem.setId(dbzxSkTurnoverInItem.getId());
                        //主表id
                        convertReceivingItem.setBillID(zxSkStockTransferReceiving.getId());
                        //单据类型
                        convertReceivingItem.setBizType("周转材入库单");
                        //单据编号
                        convertReceivingItem.setStockTransBillNo(dbzxSkTurnoverIn.getBillNo());
                        //物资编码
                        convertReceivingItem.setResCode(dbzxSkTurnoverInItem.getResCode());
                        //物资名称
                        convertReceivingItem.setResName(dbzxSkTurnoverInItem.getResName());
                        //规格型号
                        convertReceivingItem.setSpec(dbzxSkTurnoverInItem.getSpec());
                        //计量单位
                        convertReceivingItem.setResUnit(dbzxSkTurnoverInItem.getResUnit());
                        //税率
                        convertReceivingItem.setTaxRate(dbzxSkTurnoverIn.getTaxRate());
                        //本期结算数量(实收,退货数量)
                        convertReceivingItem.setInQty(dbzxSkTurnoverInItem.getInQty());
                        //本期结算单价(含税单价)
                        convertReceivingItem.setInPrice(dbzxSkTurnoverInItem.getInPrice());
                        getReceivingItemList.add(convertReceivingItem);
                    }
                    break;
                case "周转材退货单":
                    ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
                    zxSkReturnsItem.setBillID(zxSkStockTransferReceiving.getId());
                    ZxSkReturns dbzxSkReturns = zxSkReturnsMapper.selectByPrimaryKey(zxSkStockTransferReceiving.getId());
                    List<ZxSkReturnsItem> dbzxSkReturnsItemList = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
                    for (ZxSkReturnsItem dbzxSkReturnsItem : dbzxSkReturnsItemList) {
                        ZxSkStockTransItemReceiving convertReceivingItem = new ZxSkStockTransItemReceiving();
                        //id
                        convertReceivingItem.setId(dbzxSkReturnsItem.getId());
                        //主表id
                        convertReceivingItem.setBillID(zxSkStockTransferReceiving.getId());
                        //单据类型
                        convertReceivingItem.setBizType("周转材退货单");
                        //单据编号
                        convertReceivingItem.setStockTransBillNo(dbzxSkReturns.getBillNo());
                        //物资编码
                        convertReceivingItem.setResCode(dbzxSkReturnsItem.getResCode());
                        //物资名称
                        convertReceivingItem.setResName(dbzxSkReturnsItem.getResName());
                        //规格型号
                        convertReceivingItem.setSpec(dbzxSkReturnsItem.getSpec());
                        //计量单位
                        convertReceivingItem.setResUnit(dbzxSkReturnsItem.getResUnit());
                        //税率
                        convertReceivingItem.setTaxRate(dbzxSkReturns.getTaxRate());
                        //本期结算数量(实收,退货数量)
                        convertReceivingItem.setInQty(dbzxSkReturnsItem.getReturnQty());
                        //本期结算单价(含税单价)
                        convertReceivingItem.setInPrice(dbzxSkReturnsItem.getReturnPrice());
                        getReceivingItemList.add(convertReceivingItem);
                    }
                    break;
            }
        }
        return getReceivingItemList;
    }

    /*
        1.id  => id
        2.单据类型  => bizType
     */
    //收料单
    //退货单
    //周转材入库单
    //周转材退货单
    @Override
    public void updateSuppliesShopResState(List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingsList) {
        for (ZxSkStockTransItemReceiving receiving : zxSkStockTransItemReceivingsList){
            switch (receiving.getBizType()) {
                case "收料单":
                    zxSkStockTransItemReceivingMapper.updateSettlementStatusByPrimaryKey(receiving.getId());
                    break;
                case "退货单":
                    zxSkStockTransItemSalesReturnMapper.updateSettlementStatusByPrimaryKey(receiving.getId());
                    break;
                case "周转材入库单":
                    zxSkTurnoverInItemMapper.updateSettlementStatusByPrimaryKey(receiving.getId());
                    break;
                case "周转材退货单":
                    zxSkReturnsItemMapper.updateSettlementStatusByPrimaryKey(receiving.getId());
                    break;
            }
        }
    }

}
