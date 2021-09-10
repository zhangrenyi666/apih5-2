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
import com.apih5.mybatis.dao.ZxSkTurnoverOutItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnoverOutItem;
import com.apih5.service.ZxSkTurnoverOutItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverOutItemService")
public class ZxSkTurnoverOutItemServiceImpl implements ZxSkTurnoverOutItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverOutItemMapper zxSkTurnoverOutItemMapper;

    @Override
    public ResponseEntity getZxSkTurnoverOutItemListByCondition(ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        if (zxSkTurnoverOutItem == null) {
            zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverOutItem.getPage(),zxSkTurnoverOutItem.getLimit());
        // 获取数据
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
        // 得到分页信息
        PageInfo<ZxSkTurnoverOutItem> p = new PageInfo<>(zxSkTurnoverOutItemList);

        return repEntity.okList(zxSkTurnoverOutItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverOutItemDetail(ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        if (zxSkTurnoverOutItem == null) {
            zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
        }
        // 获取数据
        ZxSkTurnoverOutItem dbZxSkTurnoverOutItem = zxSkTurnoverOutItemMapper.selectByPrimaryKey(zxSkTurnoverOutItem.getId());
        // 数据存在
        if (dbZxSkTurnoverOutItem != null) {
            return repEntity.ok(dbZxSkTurnoverOutItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverOutItem(ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverOutItem.setId(UuidUtil.generate());
        zxSkTurnoverOutItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnoverOutItemMapper.insert(zxSkTurnoverOutItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverOutItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverOutItem(ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverOutItem dbZxSkTurnoverOutItem = zxSkTurnoverOutItemMapper.selectByPrimaryKey(zxSkTurnoverOutItem.getId());
        if (dbZxSkTurnoverOutItem != null && StrUtil.isNotEmpty(dbZxSkTurnoverOutItem.getId())) {
           // 单据ID
           dbZxSkTurnoverOutItem.setBillID(zxSkTurnoverOutItem.getBillID());
           // 批次
           dbZxSkTurnoverOutItem.setBatchNo(zxSkTurnoverOutItem.getBatchNo());
           // 物资ID
           dbZxSkTurnoverOutItem.setResID(zxSkTurnoverOutItem.getResID());
           // 物资编码
           dbZxSkTurnoverOutItem.setResCode(zxSkTurnoverOutItem.getResCode());
           // 物资名称
           dbZxSkTurnoverOutItem.setResName(zxSkTurnoverOutItem.getResName());
           // 规格型号
           dbZxSkTurnoverOutItem.setSpec(zxSkTurnoverOutItem.getSpec());
           // 计量单位
           dbZxSkTurnoverOutItem.setResUnit(zxSkTurnoverOutItem.getResUnit());
           // 出库数量
           dbZxSkTurnoverOutItem.setOutQty(zxSkTurnoverOutItem.getOutQty());
           // 己归还数量
           dbZxSkTurnoverOutItem.setHasReturnQty(zxSkTurnoverOutItem.getHasReturnQty());
           // 入库日期
           dbZxSkTurnoverOutItem.setInBusDate(zxSkTurnoverOutItem.getInBusDate());
           // 库存数量
           dbZxSkTurnoverOutItem.setStockQty(zxSkTurnoverOutItem.getStockQty());
           // 原值
           dbZxSkTurnoverOutItem.setOriginalAmt(zxSkTurnoverOutItem.getOriginalAmt());
           // 净值
           dbZxSkTurnoverOutItem.setRemainAmt(zxSkTurnoverOutItem.getRemainAmt());
           // 备注
           dbZxSkTurnoverOutItem.setRemarks(zxSkTurnoverOutItem.getRemarks());
           // 排序
           dbZxSkTurnoverOutItem.setSort(zxSkTurnoverOutItem.getSort());
           // 共通
           dbZxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverOutItemMapper.updateByPrimaryKey(dbZxSkTurnoverOutItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverOutItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverOutItem(List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverOutItemList != null && zxSkTurnoverOutItemList.size() > 0) {
           ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
           zxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverOutItemMapper.batchDeleteUpdateZxSkTurnoverOutItem(zxSkTurnoverOutItemList, zxSkTurnoverOutItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverOutItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
