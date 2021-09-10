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
import com.apih5.mybatis.dao.ZxSkTurnOverTransferItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnOverTransferItem;
import com.apih5.service.ZxSkTurnOverTransferItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnOverTransferItemService")
public class ZxSkTurnOverTransferItemServiceImpl implements ZxSkTurnOverTransferItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverTransferItemMapper zxSkTurnOverTransferItemMapper;

    @Override
    public ResponseEntity getZxSkTurnOverTransferItemListByCondition(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        if (zxSkTurnOverTransferItem == null) {
            zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverTransferItem.getPage(),zxSkTurnOverTransferItem.getLimit());
        // 获取数据
        List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemList = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(zxSkTurnOverTransferItem);
        // 得到分页信息
        PageInfo<ZxSkTurnOverTransferItem> p = new PageInfo<>(zxSkTurnOverTransferItemList);

        return repEntity.okList(zxSkTurnOverTransferItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverTransferItemDetail(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        if (zxSkTurnOverTransferItem == null) {
            zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
        }
        // 获取数据
        ZxSkTurnOverTransferItem dbZxSkTurnOverTransferItem = zxSkTurnOverTransferItemMapper.selectByPrimaryKey(zxSkTurnOverTransferItem.getId());
        // 数据存在
        if (dbZxSkTurnOverTransferItem != null) {
            return repEntity.ok(dbZxSkTurnOverTransferItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverTransferItem(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverTransferItem.setId(UuidUtil.generate());
        zxSkTurnOverTransferItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnOverTransferItemMapper.insert(zxSkTurnOverTransferItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverTransferItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverTransferItem(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverTransferItem dbZxSkTurnOverTransferItem = zxSkTurnOverTransferItemMapper.selectByPrimaryKey(zxSkTurnOverTransferItem.getId());
        if (dbZxSkTurnOverTransferItem != null && StrUtil.isNotEmpty(dbZxSkTurnOverTransferItem.getId())) {
           // 单据ID
           dbZxSkTurnOverTransferItem.setBillID(zxSkTurnOverTransferItem.getBillID());
           // 批次
           dbZxSkTurnOverTransferItem.setBatchNo(zxSkTurnOverTransferItem.getBatchNo());
           // 物资ID
           dbZxSkTurnOverTransferItem.setResID(zxSkTurnOverTransferItem.getResID());
           // 物资编码
           dbZxSkTurnOverTransferItem.setResCode(zxSkTurnOverTransferItem.getResCode());
           // 物资名称
           dbZxSkTurnOverTransferItem.setResName(zxSkTurnOverTransferItem.getResName());
           // 规格型号
           dbZxSkTurnOverTransferItem.setSpec(zxSkTurnOverTransferItem.getSpec());
           // 计量单位
           dbZxSkTurnOverTransferItem.setResUnit(zxSkTurnOverTransferItem.getResUnit());
           // 库存原值
           dbZxSkTurnOverTransferItem.setBuyAmt(zxSkTurnOverTransferItem.getBuyAmt());
           // 库存净值
           dbZxSkTurnOverTransferItem.setCurrentAmt(zxSkTurnOverTransferItem.getCurrentAmt());
           // 调拨数量
           dbZxSkTurnOverTransferItem.setOutQty(zxSkTurnOverTransferItem.getOutQty());
           // 调拨单价
           dbZxSkTurnOverTransferItem.setOutPrice(zxSkTurnOverTransferItem.getOutPrice());
           // 调拨金额
           dbZxSkTurnOverTransferItem.setOurAmt(zxSkTurnOverTransferItem.getOurAmt());
           // 入库日期
           dbZxSkTurnOverTransferItem.setInBusDate(zxSkTurnOverTransferItem.getInBusDate());
           // 库存数量
           dbZxSkTurnOverTransferItem.setStockQty(zxSkTurnOverTransferItem.getStockQty());
           // 调拨原值
           dbZxSkTurnOverTransferItem.setThisOriginalAmt(zxSkTurnOverTransferItem.getThisOriginalAmt());
           // 调拨净值
           dbZxSkTurnOverTransferItem.setThisRemainAmt(zxSkTurnOverTransferItem.getThisRemainAmt());
           // 备注
           dbZxSkTurnOverTransferItem.setRemarks(zxSkTurnOverTransferItem.getRemarks());
           // 排序
           dbZxSkTurnOverTransferItem.setSort(zxSkTurnOverTransferItem.getSort());
           // 共通
           dbZxSkTurnOverTransferItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverTransferItemMapper.updateByPrimaryKey(dbZxSkTurnOverTransferItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnOverTransferItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverTransferItem(List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnOverTransferItemList != null && zxSkTurnOverTransferItemList.size() > 0) {
           ZxSkTurnOverTransferItem zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
           zxSkTurnOverTransferItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverTransferItemMapper.batchDeleteUpdateZxSkTurnOverTransferItem(zxSkTurnOverTransferItemList, zxSkTurnOverTransferItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverTransferItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
