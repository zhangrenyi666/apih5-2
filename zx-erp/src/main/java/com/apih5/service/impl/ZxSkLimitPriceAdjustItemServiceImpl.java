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
import com.apih5.mybatis.dao.ZxSkLimitPriceAdjustItemMapper;
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjustItem;
import com.apih5.service.ZxSkLimitPriceAdjustItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkLimitPriceAdjustItemService")
public class ZxSkLimitPriceAdjustItemServiceImpl implements ZxSkLimitPriceAdjustItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkLimitPriceAdjustItemMapper zxSkLimitPriceAdjustItemMapper;

    @Override
    public ResponseEntity getZxSkLimitPriceAdjustItemListByCondition(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        if (zxSkLimitPriceAdjustItem == null) {
            zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkLimitPriceAdjustItem.getPage(),zxSkLimitPriceAdjustItem.getLimit());
        // 获取数据
        List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
        // 得到分页信息
        PageInfo<ZxSkLimitPriceAdjustItem> p = new PageInfo<>(zxSkLimitPriceAdjustItemList);

        return repEntity.okList(zxSkLimitPriceAdjustItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkLimitPriceAdjustItemDetails(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        if (zxSkLimitPriceAdjustItem == null) {
            zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
        }
        // 获取数据
        ZxSkLimitPriceAdjustItem dbZxSkLimitPriceAdjustItem = zxSkLimitPriceAdjustItemMapper.selectByPrimaryKey(zxSkLimitPriceAdjustItem.getId());
        // 数据存在
        if (dbZxSkLimitPriceAdjustItem != null) {
            return repEntity.ok(dbZxSkLimitPriceAdjustItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkLimitPriceAdjustItem(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkLimitPriceAdjustItem.setId(UuidUtil.generate());
        zxSkLimitPriceAdjustItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkLimitPriceAdjustItemMapper.insert(zxSkLimitPriceAdjustItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkLimitPriceAdjustItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkLimitPriceAdjustItem(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkLimitPriceAdjustItem dbzxSkLimitPriceAdjustItem = zxSkLimitPriceAdjustItemMapper.selectByPrimaryKey(zxSkLimitPriceAdjustItem.getId());
        if (dbzxSkLimitPriceAdjustItem != null && StrUtil.isNotEmpty(dbzxSkLimitPriceAdjustItem.getId())) {
           // 主表ID
           dbzxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjustItem.getMainID());
           // 物资大类ID
           dbzxSkLimitPriceAdjustItem.setResourceId(zxSkLimitPriceAdjustItem.getResourceId());
           // 物资大类
           dbzxSkLimitPriceAdjustItem.setResourceName(zxSkLimitPriceAdjustItem.getResourceName());
           // 物资编码
           dbzxSkLimitPriceAdjustItem.setResourceNo(zxSkLimitPriceAdjustItem.getResourceNo());
           // 物资id
           dbzxSkLimitPriceAdjustItem.setWorkId(zxSkLimitPriceAdjustItem.getWorkId());
           // 物资名称
           dbzxSkLimitPriceAdjustItem.setWorkName(zxSkLimitPriceAdjustItem.getWorkName());
           // 物资编码
           dbzxSkLimitPriceAdjustItem.setWorkNo(zxSkLimitPriceAdjustItem.getWorkNo());
           // 规格型号
           dbzxSkLimitPriceAdjustItem.setSpec(zxSkLimitPriceAdjustItem.getSpec());
           // 单位
           dbzxSkLimitPriceAdjustItem.setUnit(zxSkLimitPriceAdjustItem.getUnit());
           // 当期采集单价
           dbzxSkLimitPriceAdjustItem.setPrice(zxSkLimitPriceAdjustItem.getPrice());
           // 调整采集单价
           dbzxSkLimitPriceAdjustItem.setAdjustPrice(zxSkLimitPriceAdjustItem.getAdjustPrice());
           // 填报日期
           dbzxSkLimitPriceAdjustItem.setPrepareDate(zxSkLimitPriceAdjustItem.getPrepareDate());
           // editTime
           dbzxSkLimitPriceAdjustItem.setEditTime(zxSkLimitPriceAdjustItem.getEditTime());
           // 公司Id
           dbzxSkLimitPriceAdjustItem.setComId(zxSkLimitPriceAdjustItem.getComId());
           // 公司名称
           dbzxSkLimitPriceAdjustItem.setComName(zxSkLimitPriceAdjustItem.getComName());
           // 公司排序
           dbzxSkLimitPriceAdjustItem.setComOrders(zxSkLimitPriceAdjustItem.getComOrders());
           // 调整类型
           dbzxSkLimitPriceAdjustItem.setAdjustType(zxSkLimitPriceAdjustItem.getAdjustType());
           // 共通
           dbzxSkLimitPriceAdjustItem.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceAdjustItemMapper.updateByPrimaryKey(dbzxSkLimitPriceAdjustItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkLimitPriceAdjustItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceAdjustItem(List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkLimitPriceAdjustItemList != null && zxSkLimitPriceAdjustItemList.size() > 0) {
           ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
           zxSkLimitPriceAdjustItem.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceAdjustItemMapper.batchDeleteUpdateZxSkLimitPriceAdjustItem(zxSkLimitPriceAdjustItemList, zxSkLimitPriceAdjustItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkLimitPriceAdjustItemList);
        }
    }
}
