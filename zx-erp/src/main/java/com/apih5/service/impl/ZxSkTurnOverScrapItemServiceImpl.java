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
import com.apih5.mybatis.dao.ZxSkTurnOverScrapItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnOverScrapItem;
import com.apih5.service.ZxSkTurnOverScrapItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnOverScrapItemService")
public class ZxSkTurnOverScrapItemServiceImpl implements ZxSkTurnOverScrapItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverScrapItemMapper zxSkTurnOverScrapItemMapper;

    @Override
    public ResponseEntity getZxSkTurnOverScrapItemListByCondition(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        if (zxSkTurnOverScrapItem == null) {
            zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverScrapItem.getPage(),zxSkTurnOverScrapItem.getLimit());
        // 获取数据
        List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList = zxSkTurnOverScrapItemMapper.selectByZxSkTurnOverScrapItemList(zxSkTurnOverScrapItem);
        // 得到分页信息
        PageInfo<ZxSkTurnOverScrapItem> p = new PageInfo<>(zxSkTurnOverScrapItemList);

        return repEntity.okList(zxSkTurnOverScrapItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverScrapItemDetail(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        if (zxSkTurnOverScrapItem == null) {
            zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
        }
        // 获取数据
        ZxSkTurnOverScrapItem dbZxSkTurnOverScrapItem = zxSkTurnOverScrapItemMapper.selectByPrimaryKey(zxSkTurnOverScrapItem.getId());
        // 数据存在
        if (dbZxSkTurnOverScrapItem != null) {
            return repEntity.ok(dbZxSkTurnOverScrapItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverScrapItem(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverScrapItem.setId(UuidUtil.generate());
        zxSkTurnOverScrapItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnOverScrapItemMapper.insert(zxSkTurnOverScrapItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverScrapItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverScrapItem(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverScrapItem dbZxSkTurnOverScrapItem = zxSkTurnOverScrapItemMapper.selectByPrimaryKey(zxSkTurnOverScrapItem.getId());
        if (dbZxSkTurnOverScrapItem != null && StrUtil.isNotEmpty(dbZxSkTurnOverScrapItem.getId())) {
           // 单据ID
           dbZxSkTurnOverScrapItem.setBillID(zxSkTurnOverScrapItem.getBillID());
           // 批次
           dbZxSkTurnOverScrapItem.setBatchNo(zxSkTurnOverScrapItem.getBatchNo());
           // 物资ID
           dbZxSkTurnOverScrapItem.setResID(zxSkTurnOverScrapItem.getResID());
           // 物资编码
           dbZxSkTurnOverScrapItem.setResCode(zxSkTurnOverScrapItem.getResCode());
           // 物资名称
           dbZxSkTurnOverScrapItem.setResName(zxSkTurnOverScrapItem.getResName());
           // 规格型号
           dbZxSkTurnOverScrapItem.setSpec(zxSkTurnOverScrapItem.getSpec());
           // 计量单位
           dbZxSkTurnOverScrapItem.setResUnit(zxSkTurnOverScrapItem.getResUnit());
           // 购入数量
           dbZxSkTurnOverScrapItem.setBuyQty(zxSkTurnOverScrapItem.getBuyQty());
           // 购入单价
           dbZxSkTurnOverScrapItem.setBuyPrice(zxSkTurnOverScrapItem.getBuyPrice());
           // 购入金额
           dbZxSkTurnOverScrapItem.setBuyAmt(zxSkTurnOverScrapItem.getBuyAmt());
           // 净值
           dbZxSkTurnOverScrapItem.setCurrentAmt(zxSkTurnOverScrapItem.getCurrentAmt());
           // 处理数量
           dbZxSkTurnOverScrapItem.setDiscardQty(zxSkTurnOverScrapItem.getDiscardQty());
           // 处理单价
           dbZxSkTurnOverScrapItem.setDiscardPrice(zxSkTurnOverScrapItem.getDiscardPrice());
           // 处理金额
           dbZxSkTurnOverScrapItem.setDiscardAmt(zxSkTurnOverScrapItem.getDiscardAmt());
           // 入库日期
           dbZxSkTurnOverScrapItem.setInBusDate(zxSkTurnOverScrapItem.getInBusDate());
           // 库存数量
           dbZxSkTurnOverScrapItem.setStockQty(zxSkTurnOverScrapItem.getStockQty());
           // 原值
           dbZxSkTurnOverScrapItem.setOriginalAmt(zxSkTurnOverScrapItem.getOriginalAmt());
           // 退货原值
           dbZxSkTurnOverScrapItem.setThisOriginalAmt(zxSkTurnOverScrapItem.getThisOriginalAmt());
           // 退货净值
           dbZxSkTurnOverScrapItem.setThisRemainAmt(zxSkTurnOverScrapItem.getThisRemainAmt());
           // 备注
           dbZxSkTurnOverScrapItem.setRemarks(zxSkTurnOverScrapItem.getRemarks());
           // 排序
           dbZxSkTurnOverScrapItem.setSort(zxSkTurnOverScrapItem.getSort());
           // 共通
           dbZxSkTurnOverScrapItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverScrapItemMapper.updateByPrimaryKey(dbZxSkTurnOverScrapItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnOverScrapItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverScrapItem(List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnOverScrapItemList != null && zxSkTurnOverScrapItemList.size() > 0) {
           ZxSkTurnOverScrapItem zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
           zxSkTurnOverScrapItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverScrapItemMapper.batchDeleteUpdateZxSkTurnOverScrapItem(zxSkTurnOverScrapItemList, zxSkTurnOverScrapItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverScrapItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
