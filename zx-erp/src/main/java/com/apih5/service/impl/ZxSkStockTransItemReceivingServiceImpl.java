package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockTransItemReceivingMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.service.ZxSkStockTransItemReceivingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemReceivingService")
public class ZxSkStockTransItemReceivingServiceImpl implements ZxSkStockTransItemReceivingService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemReceivingMapper zxSkStockTransItemReceivingMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemReceivingListByCondition(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        if (zxSkStockTransItemReceiving == null) {
            zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemReceiving.getPage(),zxSkStockTransItemReceiving.getLimit());
        // 获取数据
        List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemReceiving> p = new PageInfo<>(zxSkStockTransItemReceivingList);

        return repEntity.okList(zxSkStockTransItemReceivingList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemReceivingDetails(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        if (zxSkStockTransItemReceiving == null) {
            zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
        }
        // 获取数据
        ZxSkStockTransItemReceiving dbZxSkStockTransItemReceiving = zxSkStockTransItemReceivingMapper.selectByPrimaryKey(zxSkStockTransItemReceiving.getId());
        // 数据存在
        if (dbZxSkStockTransItemReceiving != null) {
            return repEntity.ok(dbZxSkStockTransItemReceiving);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemReceiving(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemReceiving.setId(UuidUtil.generate());
        zxSkStockTransItemReceiving.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemReceivingMapper.insert(zxSkStockTransItemReceiving);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemReceiving);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemReceiving(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemReceiving dbzxSkStockTransItemReceiving = zxSkStockTransItemReceivingMapper.selectByPrimaryKey(zxSkStockTransItemReceiving.getId());
        if (dbzxSkStockTransItemReceiving != null && StrUtil.isNotEmpty(dbzxSkStockTransItemReceiving.getId())) {
           // 单据
           dbzxSkStockTransItemReceiving.setBillID(zxSkStockTransItemReceiving.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemReceiving.setResID(zxSkStockTransItemReceiving.getResID());
           // 批次
           dbzxSkStockTransItemReceiving.setBatchNo(zxSkStockTransItemReceiving.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemReceiving.setResCode(zxSkStockTransItemReceiving.getResCode());
           // 物资名称
           dbzxSkStockTransItemReceiving.setResName(zxSkStockTransItemReceiving.getResName());
           // 物资规格
           dbzxSkStockTransItemReceiving.setSpec(zxSkStockTransItemReceiving.getSpec());
           // 计量单位
           dbzxSkStockTransItemReceiving.setResUnit(zxSkStockTransItemReceiving.getResUnit());
           // 采购单价
           dbzxSkStockTransItemReceiving.setStdPrice(zxSkStockTransItemReceiving.getStdPrice());
           // 入账其它费
           dbzxSkStockTransItemReceiving.setInFee(zxSkStockTransItemReceiving.getInFee());
           // 实收数量
           dbzxSkStockTransItemReceiving.setInQty(zxSkStockTransItemReceiving.getInQty());
           // 含税单价
           dbzxSkStockTransItemReceiving.setInPrice(zxSkStockTransItemReceiving.getInPrice());
           // 入账金额
           dbzxSkStockTransItemReceiving.setInAmt(zxSkStockTransItemReceiving.getInAmt());
           // 管理单元名称ID
           dbzxSkStockTransItemReceiving.setMuID(zxSkStockTransItemReceiving.getMuID());
           // 清单名称ID
           dbzxSkStockTransItemReceiving.setWorkID(zxSkStockTransItemReceiving.getWorkID());
           // 管理单元名称
           dbzxSkStockTransItemReceiving.setMuName(zxSkStockTransItemReceiving.getMuName());
           // 清单名称
           dbzxSkStockTransItemReceiving.setWorkName(zxSkStockTransItemReceiving.getWorkName());
           // 数量
           dbzxSkStockTransItemReceiving.setOutQty(zxSkStockTransItemReceiving.getOutQty());
           // 其他费
           dbzxSkStockTransItemReceiving.setOutFee(zxSkStockTransItemReceiving.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemReceiving.setOutCostPrice(zxSkStockTransItemReceiving.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemReceiving.setOutCostAmt(zxSkStockTransItemReceiving.getOutCostAmt());
           // 单价
           dbzxSkStockTransItemReceiving.setOutPrice(zxSkStockTransItemReceiving.getOutPrice());
           // 金额
           dbzxSkStockTransItemReceiving.setOutAmt(zxSkStockTransItemReceiving.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemReceiving.setIsDepreciation(zxSkStockTransItemReceiving.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemReceiving.setRemark(zxSkStockTransItemReceiving.getRemark());
           // 物资其他费明细ID
           dbzxSkStockTransItemReceiving.setMaterialsExpensesDetailID (zxSkStockTransItemReceiving.getMaterialsExpensesDetailID ());
           // 物资运输费明细ID
           dbzxSkStockTransItemReceiving.setMaterialsTransportationDetailID (zxSkStockTransItemReceiving.getMaterialsTransportationDetailID ());
           // 应收数量
           dbzxSkStockTransItemReceiving.setShouldQty(zxSkStockTransItemReceiving.getShouldQty());
           // 材质单编号
           dbzxSkStockTransItemReceiving.setQualityNo(zxSkStockTransItemReceiving.getQualityNo());
           // 是否预收
           dbzxSkStockTransItemReceiving.setPrecollecte(zxSkStockTransItemReceiving.getPrecollecte());
           // 含税金额
           dbzxSkStockTransItemReceiving.setResAllFee(zxSkStockTransItemReceiving.getResAllFee());
           // 冲账累积数量
           dbzxSkStockTransItemReceiving.setInvoiceCumulateQty(zxSkStockTransItemReceiving.getInvoiceCumulateQty());
           // 冲账累积金额
           dbzxSkStockTransItemReceiving.setInvoiceCumulateAmt(zxSkStockTransItemReceiving.getInvoiceCumulateAmt());
           // 其它费合计
           dbzxSkStockTransItemReceiving.setInFeeTotal(zxSkStockTransItemReceiving.getInFeeTotal());
           // 运输费合计
           dbzxSkStockTransItemReceiving.setYsFeeTotal(zxSkStockTransItemReceiving.getYsFeeTotal());
           // 总价
           dbzxSkStockTransItemReceiving.setInAmtAll(zxSkStockTransItemReceiving.getInAmtAll());
           // 不含税单价
           dbzxSkStockTransItemReceiving.setInPriceNoTax(zxSkStockTransItemReceiving.getInPriceNoTax());
           // 不含税金额
           dbzxSkStockTransItemReceiving.setResAllFeeNoTax(zxSkStockTransItemReceiving.getResAllFeeNoTax());
           // 税额
           dbzxSkStockTransItemReceiving.setResAllFeeTax(zxSkStockTransItemReceiving.getResAllFeeTax());
           // 不含税总价
           dbzxSkStockTransItemReceiving.setInAmtNoTax(zxSkStockTransItemReceiving.getInAmtNoTax());
           // 入账运输费
           dbzxSkStockTransItemReceiving.setYsFee(zxSkStockTransItemReceiving.getYsFee());
           // 结算状态
           dbzxSkStockTransItemReceiving.setSettlementStatus(zxSkStockTransItemReceiving.getSettlementStatus());
           // 可退数量
           dbzxSkStockTransItemReceiving.setIsOutNumber(zxSkStockTransItemReceiving.getIsOutNumber());
           // 共通
           dbzxSkStockTransItemReceiving.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemReceivingMapper.updateByPrimaryKey(dbzxSkStockTransItemReceiving);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemReceiving);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemReceiving(List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemReceivingList != null && zxSkStockTransItemReceivingList.size() > 0) {
           ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
           zxSkStockTransItemReceiving.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemReceivingMapper.batchDeleteUpdateZxSkStockTransItemReceiving(zxSkStockTransItemReceivingList, zxSkStockTransItemReceiving);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemReceivingList);
        }
    }
}
