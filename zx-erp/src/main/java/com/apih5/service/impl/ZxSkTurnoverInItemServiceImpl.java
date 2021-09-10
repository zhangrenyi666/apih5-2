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
import com.apih5.mybatis.dao.ZxSkTurnoverInItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;
import com.apih5.service.ZxSkTurnoverInItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverInItemService")
public class ZxSkTurnoverInItemServiceImpl implements ZxSkTurnoverInItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverInItemMapper zxSkTurnoverInItemMapper;

    @Override
    public ResponseEntity getZxSkTurnoverInItemListByCondition(ZxSkTurnoverInItem zxSkTurnoverInItem) {
        if (zxSkTurnoverInItem == null) {
            zxSkTurnoverInItem = new ZxSkTurnoverInItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverInItem.getPage(),zxSkTurnoverInItem.getLimit());
        // 获取数据
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
        // 得到分页信息
        PageInfo<ZxSkTurnoverInItem> p = new PageInfo<>(zxSkTurnoverInItemList);

        return repEntity.okList(zxSkTurnoverInItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverInItemDetail(ZxSkTurnoverInItem zxSkTurnoverInItem) {
        if (zxSkTurnoverInItem == null) {
            zxSkTurnoverInItem = new ZxSkTurnoverInItem();
        }
        // 获取数据
        ZxSkTurnoverInItem dbZxSkTurnoverInItem = zxSkTurnoverInItemMapper.selectByPrimaryKey(zxSkTurnoverInItem.getId());
        // 数据存在
        if (dbZxSkTurnoverInItem != null) {
            return repEntity.ok(dbZxSkTurnoverInItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverInItem(ZxSkTurnoverInItem zxSkTurnoverInItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverInItem.setId(UuidUtil.generate());
        zxSkTurnoverInItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnoverInItemMapper.insert(zxSkTurnoverInItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverInItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverInItem(ZxSkTurnoverInItem zxSkTurnoverInItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverInItem dbZxSkTurnoverInItem = zxSkTurnoverInItemMapper.selectByPrimaryKey(zxSkTurnoverInItem.getId());
        if (dbZxSkTurnoverInItem != null && StrUtil.isNotEmpty(dbZxSkTurnoverInItem.getId())) {
           // 单据ID
           dbZxSkTurnoverInItem.setBillID(zxSkTurnoverInItem.getBillID());
           // 批次
           dbZxSkTurnoverInItem.setBatchNo(zxSkTurnoverInItem.getBatchNo());
           // 物资ID
           dbZxSkTurnoverInItem.setResID(zxSkTurnoverInItem.getResID());
           // 物资编码
           dbZxSkTurnoverInItem.setResCode(zxSkTurnoverInItem.getResCode());
           // 物资名称
           dbZxSkTurnoverInItem.setResName(zxSkTurnoverInItem.getResName());
           // 规格型号
           dbZxSkTurnoverInItem.setSpec(zxSkTurnoverInItem.getSpec());
           // 计量单位
           dbZxSkTurnoverInItem.setResUnit(zxSkTurnoverInItem.getResUnit());
           // 数量
           dbZxSkTurnoverInItem.setInQty(zxSkTurnoverInItem.getInQty());
           // 购入单价合计
           dbZxSkTurnoverInItem.setInPrice(zxSkTurnoverInItem.getInPrice());
           // 原值
           dbZxSkTurnoverInItem.setInAmt(zxSkTurnoverInItem.getInAmt());
           // 累计摊销
           dbZxSkTurnoverInItem.setFeeSum(zxSkTurnoverInItem.getFeeSum());
           // 净值
           dbZxSkTurnoverInItem.setRemainAmt(zxSkTurnoverInItem.getRemainAmt());
           // 是否预收
           dbZxSkTurnoverInItem.setPrecollecte(zxSkTurnoverInItem.getPrecollecte());
           // checkQty
           dbZxSkTurnoverInItem.setCheckQty(zxSkTurnoverInItem.getCheckQty());
           // 购入单价税额
           dbZxSkTurnoverInItem.setInPriceTax(zxSkTurnoverInItem.getInPriceTax());
           // 购入单价
           dbZxSkTurnoverInItem.setInPriceNoTax(zxSkTurnoverInItem.getInPriceNoTax());
           // 总值
           dbZxSkTurnoverInItem.setInAmtTotal(zxSkTurnoverInItem.getInAmtTotal());
           // 结算状态
           dbZxSkTurnoverInItem.setSettlementStatus(zxSkTurnoverInItem.getSettlementStatus());
           // 备注
           dbZxSkTurnoverInItem.setRemarks(zxSkTurnoverInItem.getRemarks());
           // 排序
           dbZxSkTurnoverInItem.setSort(zxSkTurnoverInItem.getSort());
           // 共通
           dbZxSkTurnoverInItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverInItemMapper.updateByPrimaryKey(dbZxSkTurnoverInItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverInItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverInItem(List<ZxSkTurnoverInItem> zxSkTurnoverInItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverInItemList != null && zxSkTurnoverInItemList.size() > 0) {
           ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
           zxSkTurnoverInItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverInItemMapper.batchDeleteUpdateZxSkTurnoverInItem(zxSkTurnoverInItemList, zxSkTurnoverInItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverInItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
