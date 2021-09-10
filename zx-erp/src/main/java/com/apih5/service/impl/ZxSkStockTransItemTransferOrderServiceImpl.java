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
import com.apih5.mybatis.dao.ZxSkStockTransItemTransferOrderMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemTransferOrder;
import com.apih5.service.ZxSkStockTransItemTransferOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemTransferOrderService")
public class ZxSkStockTransItemTransferOrderServiceImpl implements ZxSkStockTransItemTransferOrderService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemTransferOrderMapper zxSkStockTransItemTransferOrderMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemTransferOrderListByCondition(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        if (zxSkStockTransItemTransferOrder == null) {
            zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemTransferOrder.getPage(),zxSkStockTransItemTransferOrder.getLimit());
        // 获取数据
        List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList = zxSkStockTransItemTransferOrderMapper.selectByZxSkStockTransItemTransferOrderList(zxSkStockTransItemTransferOrder);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemTransferOrder> p = new PageInfo<>(zxSkStockTransItemTransferOrderList);

        return repEntity.okList(zxSkStockTransItemTransferOrderList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemTransferOrderDetails(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        if (zxSkStockTransItemTransferOrder == null) {
            zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
        }
        // 获取数据
        ZxSkStockTransItemTransferOrder dbZxSkStockTransItemTransferOrder = zxSkStockTransItemTransferOrderMapper.selectByPrimaryKey(zxSkStockTransItemTransferOrder.getId());
        // 数据存在
        if (dbZxSkStockTransItemTransferOrder != null) {
            return repEntity.ok(dbZxSkStockTransItemTransferOrder);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemTransferOrder(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemTransferOrder.setId(UuidUtil.generate());
        zxSkStockTransItemTransferOrder.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemTransferOrderMapper.insert(zxSkStockTransItemTransferOrder);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemTransferOrder);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemTransferOrder(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemTransferOrder dbzxSkStockTransItemTransferOrder = zxSkStockTransItemTransferOrderMapper.selectByPrimaryKey(zxSkStockTransItemTransferOrder.getId());
        if (dbzxSkStockTransItemTransferOrder != null && StrUtil.isNotEmpty(dbzxSkStockTransItemTransferOrder.getId())) {
           // 单据
           dbzxSkStockTransItemTransferOrder.setBillID(zxSkStockTransItemTransferOrder.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemTransferOrder.setResID(zxSkStockTransItemTransferOrder.getResID());
           // 批次
           dbzxSkStockTransItemTransferOrder.setBatchNo(zxSkStockTransItemTransferOrder.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemTransferOrder.setResCode(zxSkStockTransItemTransferOrder.getResCode());
           // 物资名称
           dbzxSkStockTransItemTransferOrder.setResName(zxSkStockTransItemTransferOrder.getResName());
           // 规格型号
           dbzxSkStockTransItemTransferOrder.setSpec(zxSkStockTransItemTransferOrder.getSpec());
           // 计量单位
           dbzxSkStockTransItemTransferOrder.setResUnit(zxSkStockTransItemTransferOrder.getResUnit());
           // 平均单价
           dbzxSkStockTransItemTransferOrder.setStdPrice(zxSkStockTransItemTransferOrder.getStdPrice());
           // 其他费
           dbzxSkStockTransItemTransferOrder.setInFee(zxSkStockTransItemTransferOrder.getInFee());
           // 数量
           dbzxSkStockTransItemTransferOrder.setInQty(zxSkStockTransItemTransferOrder.getInQty());
           // 单价
           dbzxSkStockTransItemTransferOrder.setInPrice(zxSkStockTransItemTransferOrder.getInPrice());
           // 入账金额
           dbzxSkStockTransItemTransferOrder.setInAmt(zxSkStockTransItemTransferOrder.getInAmt());
           // 管理单元ID
           dbzxSkStockTransItemTransferOrder.setMuID(zxSkStockTransItemTransferOrder.getMuID());
           // 清单项ID
           dbzxSkStockTransItemTransferOrder.setWorkID(zxSkStockTransItemTransferOrder.getWorkID());
           // 清单名称
           dbzxSkStockTransItemTransferOrder.setWorkName(zxSkStockTransItemTransferOrder.getWorkName());
           // 实发数量
           dbzxSkStockTransItemTransferOrder.setOutQty(zxSkStockTransItemTransferOrder.getOutQty());
           // 其他费
           dbzxSkStockTransItemTransferOrder.setOutFee(zxSkStockTransItemTransferOrder.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemTransferOrder.setOutCostPrice(zxSkStockTransItemTransferOrder.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemTransferOrder.setOutCostAmt(zxSkStockTransItemTransferOrder.getOutCostAmt());
           // 拨让单价
           dbzxSkStockTransItemTransferOrder.setOutPrice(zxSkStockTransItemTransferOrder.getOutPrice());
           // 金额
           dbzxSkStockTransItemTransferOrder.setOutAmt(zxSkStockTransItemTransferOrder.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemTransferOrder.setIsDepreciation(zxSkStockTransItemTransferOrder.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemTransferOrder.setRemark(zxSkStockTransItemTransferOrder.getRemark());
           // 应收数量
           dbzxSkStockTransItemTransferOrder.setShouldQty(zxSkStockTransItemTransferOrder.getShouldQty());
           // 拨让总价
           dbzxSkStockTransItemTransferOrder.setTransOutAmt(zxSkStockTransItemTransferOrder.getTransOutAmt());
           // 金额
           dbzxSkStockTransItemTransferOrder.setInAmtAll(zxSkStockTransItemTransferOrder.getInAmtAll());
           // 不含税总价
           dbzxSkStockTransItemTransferOrder.setInAmtNoTax(zxSkStockTransItemTransferOrder.getInAmtNoTax());
           // 共通
           dbzxSkStockTransItemTransferOrder.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemTransferOrderMapper.updateByPrimaryKey(dbzxSkStockTransItemTransferOrder);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemTransferOrder);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemTransferOrder(List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemTransferOrderList != null && zxSkStockTransItemTransferOrderList.size() > 0) {
           ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder = new ZxSkStockTransItemTransferOrder();
           zxSkStockTransItemTransferOrder.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemTransferOrderMapper.batchDeleteUpdateZxSkStockTransItemTransferOrder(zxSkStockTransItemTransferOrderList, zxSkStockTransItemTransferOrder);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemTransferOrderList);
        }
    }
}
