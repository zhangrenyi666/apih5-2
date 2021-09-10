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
import com.apih5.mybatis.dao.ZxSkTurnoverCheckItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnoverCheckItem;
import com.apih5.service.ZxSkTurnoverCheckItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverCheckItemService")
public class ZxSkTurnoverCheckItemServiceImpl implements ZxSkTurnoverCheckItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverCheckItemMapper zxSkTurnoverCheckItemMapper;

    @Override
    public ResponseEntity getZxSkTurnoverCheckItemListByCondition(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        if (zxSkTurnoverCheckItem == null) {
            zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverCheckItem.getPage(),zxSkTurnoverCheckItem.getLimit());
        // 获取数据
        List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList = zxSkTurnoverCheckItemMapper.selectByZxSkTurnoverCheckItemList(zxSkTurnoverCheckItem);
        // 得到分页信息
        PageInfo<ZxSkTurnoverCheckItem> p = new PageInfo<>(zxSkTurnoverCheckItemList);

        return repEntity.okList(zxSkTurnoverCheckItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverCheckItemDetail(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        if (zxSkTurnoverCheckItem == null) {
            zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
        }
        // 获取数据
        ZxSkTurnoverCheckItem dbZxSkTurnoverCheckItem = zxSkTurnoverCheckItemMapper.selectByPrimaryKey(zxSkTurnoverCheckItem.getId());
        // 数据存在
        if (dbZxSkTurnoverCheckItem != null) {
            return repEntity.ok(dbZxSkTurnoverCheckItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverCheckItem(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverCheckItem.setId(UuidUtil.generate());
        zxSkTurnoverCheckItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnoverCheckItemMapper.insert(zxSkTurnoverCheckItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverCheckItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverCheckItem(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverCheckItem dbZxSkTurnoverCheckItem = zxSkTurnoverCheckItemMapper.selectByPrimaryKey(zxSkTurnoverCheckItem.getId());
        if (dbZxSkTurnoverCheckItem != null && StrUtil.isNotEmpty(dbZxSkTurnoverCheckItem.getId())) {
           // 单据ID
           dbZxSkTurnoverCheckItem.setBillID(zxSkTurnoverCheckItem.getBillID());
           // 批次
           dbZxSkTurnoverCheckItem.setBatchNo(zxSkTurnoverCheckItem.getBatchNo());
           // 物资ID
           dbZxSkTurnoverCheckItem.setResID(zxSkTurnoverCheckItem.getResID());
           // 资源编号
           dbZxSkTurnoverCheckItem.setResCode(zxSkTurnoverCheckItem.getResCode());
           // 物资名称
           dbZxSkTurnoverCheckItem.setResName(zxSkTurnoverCheckItem.getResName());
           // 规格型号
           dbZxSkTurnoverCheckItem.setSpec(zxSkTurnoverCheckItem.getSpec());
           // 计量单位
           dbZxSkTurnoverCheckItem.setResUnit(zxSkTurnoverCheckItem.getResUnit());
           // 转移单明细ID
           dbZxSkTurnoverCheckItem.setStockTransItemID(zxSkTurnoverCheckItem.getStockTransItemID());
           // 预收单ID
           dbZxSkTurnoverCheckItem.setStockTransID(zxSkTurnoverCheckItem.getStockTransID());
           // 预收单号
           dbZxSkTurnoverCheckItem.setStockTransBillNo(zxSkTurnoverCheckItem.getStockTransBillNo());
           // 数量
           dbZxSkTurnoverCheckItem.setInQty(zxSkTurnoverCheckItem.getInQty());
           // 购入单价合计
           dbZxSkTurnoverCheckItem.setInPrice(zxSkTurnoverCheckItem.getInPrice());
           // 原值
           dbZxSkTurnoverCheckItem.setInAmt(zxSkTurnoverCheckItem.getInAmt());
           // 累计摊销
           dbZxSkTurnoverCheckItem.setFeeSum(zxSkTurnoverCheckItem.getFeeSum());
           // 净值
           dbZxSkTurnoverCheckItem.setRemainAmt(zxSkTurnoverCheckItem.getRemainAmt());
           // 累计摊销单价
           dbZxSkTurnoverCheckItem.setFeePrice(zxSkTurnoverCheckItem.getFeePrice());
           // 未冲数量
           dbZxSkTurnoverCheckItem.setUnCheckQty(zxSkTurnoverCheckItem.getUnCheckQty());
           // 税率(%)
           dbZxSkTurnoverCheckItem.setTaxRate(zxSkTurnoverCheckItem.getTaxRate());
           // 是否抵扣
           dbZxSkTurnoverCheckItem.setIsDeduct(zxSkTurnoverCheckItem.getIsDeduct());
           // 购入不含税单价
           dbZxSkTurnoverCheckItem.setInPriceNoTax(zxSkTurnoverCheckItem.getInPriceNoTax());
           // 购入单价税额
           dbZxSkTurnoverCheckItem.setInPriceTax(zxSkTurnoverCheckItem.getInPriceTax());
           // 备注
           dbZxSkTurnoverCheckItem.setRemarks(zxSkTurnoverCheckItem.getRemarks());
           // 排序
           dbZxSkTurnoverCheckItem.setSort(zxSkTurnoverCheckItem.getSort());
           // 共通
           dbZxSkTurnoverCheckItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverCheckItemMapper.updateByPrimaryKey(dbZxSkTurnoverCheckItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverCheckItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverCheckItem(List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverCheckItemList != null && zxSkTurnoverCheckItemList.size() > 0) {
           ZxSkTurnoverCheckItem zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
           zxSkTurnoverCheckItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverCheckItemMapper.batchDeleteUpdateZxSkTurnoverCheckItem(zxSkTurnoverCheckItemList, zxSkTurnoverCheckItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverCheckItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
