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
import com.apih5.mybatis.dao.ZxSkReturnsItemMapper;
import com.apih5.mybatis.pojo.ZxSkReturnsItem;
import com.apih5.service.ZxSkReturnsItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkReturnsItemService")
public class ZxSkReturnsItemServiceImpl implements ZxSkReturnsItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkReturnsItemMapper zxSkReturnsItemMapper;

    @Override
    public ResponseEntity getZxSkReturnsItemListByCondition(ZxSkReturnsItem zxSkReturnsItem) {
        if (zxSkReturnsItem == null) {
            zxSkReturnsItem = new ZxSkReturnsItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkReturnsItem.getPage(),zxSkReturnsItem.getLimit());
        // 获取数据
        List<ZxSkReturnsItem> zxSkReturnsItemList = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
        // 得到分页信息
        PageInfo<ZxSkReturnsItem> p = new PageInfo<>(zxSkReturnsItemList);

        return repEntity.okList(zxSkReturnsItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkReturnsItemDetail(ZxSkReturnsItem zxSkReturnsItem) {
        if (zxSkReturnsItem == null) {
            zxSkReturnsItem = new ZxSkReturnsItem();
        }
        // 获取数据
        ZxSkReturnsItem dbZxSkReturnsItem = zxSkReturnsItemMapper.selectByPrimaryKey(zxSkReturnsItem.getId());
        // 数据存在
        if (dbZxSkReturnsItem != null) {
            return repEntity.ok(dbZxSkReturnsItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkReturnsItem(ZxSkReturnsItem zxSkReturnsItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkReturnsItem.setId(UuidUtil.generate());
        zxSkReturnsItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkReturnsItemMapper.insert(zxSkReturnsItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkReturnsItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkReturnsItem(ZxSkReturnsItem zxSkReturnsItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkReturnsItem dbZxSkReturnsItem = zxSkReturnsItemMapper.selectByPrimaryKey(zxSkReturnsItem.getId());
        if (dbZxSkReturnsItem != null && StrUtil.isNotEmpty(dbZxSkReturnsItem.getId())) {
           // 单据ID
           dbZxSkReturnsItem.setBillID(zxSkReturnsItem.getBillID());
           // 批次
           dbZxSkReturnsItem.setBatchNo(zxSkReturnsItem.getBatchNo());
           // 物资ID
           dbZxSkReturnsItem.setResID(zxSkReturnsItem.getResID());
           // 物资编码
           dbZxSkReturnsItem.setResCode(zxSkReturnsItem.getResCode());
           // 物资名称
           dbZxSkReturnsItem.setResName(zxSkReturnsItem.getResName());
           // 规格型号
           dbZxSkReturnsItem.setSpec(zxSkReturnsItem.getSpec());
           // 计量单位
           dbZxSkReturnsItem.setResUnit(zxSkReturnsItem.getResUnit());
           // 退货数量
           dbZxSkReturnsItem.setReturnQty(zxSkReturnsItem.getReturnQty());
           // 入库日期
           dbZxSkReturnsItem.setInBusDate(zxSkReturnsItem.getInBusDate());
           // 库存量
           dbZxSkReturnsItem.setStockQty(zxSkReturnsItem.getStockQty());
           // 入库单价
           dbZxSkReturnsItem.setStockPrice(zxSkReturnsItem.getStockPrice());
           // 退货金额
           dbZxSkReturnsItem.setReturnAmt(zxSkReturnsItem.getReturnAmt());
           // 退货单价合计
           dbZxSkReturnsItem.setReturnPrice(zxSkReturnsItem.getReturnPrice());
           // 退货原值
           dbZxSkReturnsItem.setOriginAmt(zxSkReturnsItem.getOriginAmt());
           // 累计摊销
           dbZxSkReturnsItem.setFeeSum(zxSkReturnsItem.getFeeSum());
           // 退货净值
           dbZxSkReturnsItem.setRemainAmt(zxSkReturnsItem.getRemainAmt());
           // 退货单价税金
           dbZxSkReturnsItem.setReturnPriceTax(zxSkReturnsItem.getReturnPriceTax());
           // 退货不含税单价
           dbZxSkReturnsItem.setReturnPriceNoTax(zxSkReturnsItem.getReturnPriceNoTax());
           // 退货税额
           dbZxSkReturnsItem.setReturnAmtTax(zxSkReturnsItem.getReturnAmtTax());
           // 结算状态
           dbZxSkReturnsItem.setSettlementStatus(zxSkReturnsItem.getSettlementStatus());
           // 备注
           dbZxSkReturnsItem.setRemarks(zxSkReturnsItem.getRemarks());
           // 排序
           dbZxSkReturnsItem.setSort(zxSkReturnsItem.getSort());
           // 共通
           dbZxSkReturnsItem.setModifyUserInfo(userKey, realName);
           flag = zxSkReturnsItemMapper.updateByPrimaryKey(dbZxSkReturnsItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkReturnsItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkReturnsItem(List<ZxSkReturnsItem> zxSkReturnsItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkReturnsItemList != null && zxSkReturnsItemList.size() > 0) {
           ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
           zxSkReturnsItem.setModifyUserInfo(userKey, realName);
           flag = zxSkReturnsItemMapper.batchDeleteUpdateZxSkReturnsItem(zxSkReturnsItemList, zxSkReturnsItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkReturnsItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
