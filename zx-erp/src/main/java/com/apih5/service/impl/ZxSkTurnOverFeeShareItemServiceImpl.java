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
import com.apih5.mybatis.dao.ZxSkTurnOverFeeShareItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShareItem;
import com.apih5.service.ZxSkTurnOverFeeShareItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnOverFeeShareItemService")
public class ZxSkTurnOverFeeShareItemServiceImpl implements ZxSkTurnOverFeeShareItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverFeeShareItemMapper zxSkTurnOverFeeShareItemMapper;

    @Override
    public ResponseEntity getZxSkTurnOverFeeShareItemListByCondition(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        if (zxSkTurnOverFeeShareItem == null) {
            zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverFeeShareItem.getPage(),zxSkTurnOverFeeShareItem.getLimit());
        // 获取数据
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
        // 得到分页信息
        PageInfo<ZxSkTurnOverFeeShareItem> p = new PageInfo<>(zxSkTurnOverFeeShareItemList);

        return repEntity.okList(zxSkTurnOverFeeShareItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverFeeShareItemDetail(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        if (zxSkTurnOverFeeShareItem == null) {
            zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
        }
        // 获取数据
        ZxSkTurnOverFeeShareItem dbZxSkTurnOverFeeShareItem = zxSkTurnOverFeeShareItemMapper.selectByPrimaryKey(zxSkTurnOverFeeShareItem.getId());
        // 数据存在
        if (dbZxSkTurnOverFeeShareItem != null) {
            return repEntity.ok(dbZxSkTurnOverFeeShareItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverFeeShareItem(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverFeeShareItem.setId(UuidUtil.generate());
        zxSkTurnOverFeeShareItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnOverFeeShareItemMapper.insert(zxSkTurnOverFeeShareItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverFeeShareItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverFeeShareItem(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverFeeShareItem dbZxSkTurnOverFeeShareItem = zxSkTurnOverFeeShareItemMapper.selectByPrimaryKey(zxSkTurnOverFeeShareItem.getId());
        if (dbZxSkTurnOverFeeShareItem != null && StrUtil.isNotEmpty(dbZxSkTurnOverFeeShareItem.getId())) {
           // 单据ID
           dbZxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShareItem.getBillID());
           // 项目机构ID
           dbZxSkTurnOverFeeShareItem.setOrgID(zxSkTurnOverFeeShareItem.getOrgID());
           // 项目机构名称
           dbZxSkTurnOverFeeShareItem.setOrgName(zxSkTurnOverFeeShareItem.getOrgName());
           // 批次
           dbZxSkTurnOverFeeShareItem.setBatchNo(zxSkTurnOverFeeShareItem.getBatchNo());
           // 物资ID
           dbZxSkTurnOverFeeShareItem.setResID(zxSkTurnOverFeeShareItem.getResID());
           // 物资编码
           dbZxSkTurnOverFeeShareItem.setResCode(zxSkTurnOverFeeShareItem.getResCode());
           // 物资名称
           dbZxSkTurnOverFeeShareItem.setResName(zxSkTurnOverFeeShareItem.getResName());
           // 规格型号
           dbZxSkTurnOverFeeShareItem.setSpec(zxSkTurnOverFeeShareItem.getSpec());
           // 计量单位
           dbZxSkTurnOverFeeShareItem.setResUnit(zxSkTurnOverFeeShareItem.getResUnit());
           // 购入数量
           dbZxSkTurnOverFeeShareItem.setBuyQty(zxSkTurnOverFeeShareItem.getBuyQty());
           // 购入单价
           dbZxSkTurnOverFeeShareItem.setBuyPrice(zxSkTurnOverFeeShareItem.getBuyPrice());
           // 原值
           dbZxSkTurnOverFeeShareItem.setBuyAmt(zxSkTurnOverFeeShareItem.getBuyAmt());
           // 净值
           dbZxSkTurnOverFeeShareItem.setCurrentAmt(zxSkTurnOverFeeShareItem.getCurrentAmt());
           // 本期摊销
           dbZxSkTurnOverFeeShareItem.setShareAmt(zxSkTurnOverFeeShareItem.getShareAmt());
           // 入库日期
           dbZxSkTurnOverFeeShareItem.setInBusDate(zxSkTurnOverFeeShareItem.getInBusDate());
           // 累计摊销
           dbZxSkTurnOverFeeShareItem.setTotalShareAmt(zxSkTurnOverFeeShareItem.getTotalShareAmt());
           // 备注
           dbZxSkTurnOverFeeShareItem.setRemarks(zxSkTurnOverFeeShareItem.getRemarks());
           // 排序
           dbZxSkTurnOverFeeShareItem.setSort(zxSkTurnOverFeeShareItem.getSort());
           // 共通
           dbZxSkTurnOverFeeShareItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverFeeShareItemMapper.updateByPrimaryKey(dbZxSkTurnOverFeeShareItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnOverFeeShareItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverFeeShareItem(List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnOverFeeShareItemList != null && zxSkTurnOverFeeShareItemList.size() > 0) {
           ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
           zxSkTurnOverFeeShareItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverFeeShareItemMapper.batchDeleteUpdateZxSkTurnOverFeeShareItem(zxSkTurnOverFeeShareItemList, zxSkTurnOverFeeShareItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverFeeShareItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
