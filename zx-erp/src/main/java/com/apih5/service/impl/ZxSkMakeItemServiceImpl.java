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
import com.apih5.mybatis.dao.ZxSkMakeItemMapper;
import com.apih5.mybatis.pojo.ZxSkMakeItem;
import com.apih5.service.ZxSkMakeItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMakeItemService")
public class ZxSkMakeItemServiceImpl implements ZxSkMakeItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMakeItemMapper zxSkMakeItemMapper;

    @Override
    public ResponseEntity getZxSkMakeItemListByCondition(ZxSkMakeItem zxSkMakeItem) {
        if (zxSkMakeItem == null) {
            zxSkMakeItem = new ZxSkMakeItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkMakeItem.getPage(),zxSkMakeItem.getLimit());
        // 获取数据
        List<ZxSkMakeItem> zxSkMakeItemList = zxSkMakeItemMapper.selectByZxSkMakeItemList(zxSkMakeItem);
        // 得到分页信息
        PageInfo<ZxSkMakeItem> p = new PageInfo<>(zxSkMakeItemList);

        return repEntity.okList(zxSkMakeItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMakeItemDetail(ZxSkMakeItem zxSkMakeItem) {
        if (zxSkMakeItem == null) {
            zxSkMakeItem = new ZxSkMakeItem();
        }
        // 获取数据
        ZxSkMakeItem dbZxSkMakeItem = zxSkMakeItemMapper.selectByPrimaryKey(zxSkMakeItem.getId());
        // 数据存在
        if (dbZxSkMakeItem != null) {
            return repEntity.ok(dbZxSkMakeItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkMakeItem(ZxSkMakeItem zxSkMakeItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkMakeItem.setId(UuidUtil.generate());
        zxSkMakeItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkMakeItemMapper.insert(zxSkMakeItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkMakeItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkMakeItem(ZxSkMakeItem zxSkMakeItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMakeItem dbZxSkMakeItem = zxSkMakeItemMapper.selectByPrimaryKey(zxSkMakeItem.getId());
        if (dbZxSkMakeItem != null && StrUtil.isNotEmpty(dbZxSkMakeItem.getId())) {
           // 盘点单ID
           dbZxSkMakeItem.setMakeInvID(zxSkMakeItem.getMakeInvID());
           // 库存ID
           dbZxSkMakeItem.setStockID(zxSkMakeItem.getStockID());
           // 物资ID
           dbZxSkMakeItem.setResID(zxSkMakeItem.getResID());
           // 账面数量
           dbZxSkMakeItem.setAccountsQty(zxSkMakeItem.getAccountsQty());
           // 账面金额
           dbZxSkMakeItem.setAccountsAmt(zxSkMakeItem.getAccountsAmt());
           // 盘点数量
           dbZxSkMakeItem.setMakeInvQty(zxSkMakeItem.getMakeInvQty());
           // 盘点金额
           dbZxSkMakeItem.setMakeInvAmt(zxSkMakeItem.getMakeInvAmt());
           // 计算金额
           dbZxSkMakeItem.setCountAmt(zxSkMakeItem.getCountAmt());
           // 盈亏数量
           dbZxSkMakeItem.setDiffQty(zxSkMakeItem.getDiffQty());
           // 盈亏金额
           dbZxSkMakeItem.setDiffAmt(zxSkMakeItem.getDiffAmt());
           // 批号
           dbZxSkMakeItem.setBatchNo(zxSkMakeItem.getBatchNo());
           // 物资编码
           dbZxSkMakeItem.setResCode(zxSkMakeItem.getResCode());
           // 物资名称
           dbZxSkMakeItem.setResName(zxSkMakeItem.getResName());
           // 规格型号
           dbZxSkMakeItem.setSpec(zxSkMakeItem.getSpec());
           // 单位
           dbZxSkMakeItem.setResUnit(zxSkMakeItem.getResUnit());
           // 单价
           dbZxSkMakeItem.setStockPrice(zxSkMakeItem.getStockPrice());
           // 备注
           dbZxSkMakeItem.setRemarks(zxSkMakeItem.getRemarks());
           // 排序
           dbZxSkMakeItem.setSort(zxSkMakeItem.getSort());
           // 共通
           dbZxSkMakeItem.setModifyUserInfo(userKey, realName);
           flag = zxSkMakeItemMapper.updateByPrimaryKey(dbZxSkMakeItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkMakeItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMakeItem(List<ZxSkMakeItem> zxSkMakeItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkMakeItemList != null && zxSkMakeItemList.size() > 0) {
           ZxSkMakeItem zxSkMakeItem = new ZxSkMakeItem();
           zxSkMakeItem.setModifyUserInfo(userKey, realName);
           flag = zxSkMakeItemMapper.batchDeleteUpdateZxSkMakeItem(zxSkMakeItemList, zxSkMakeItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkMakeItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
