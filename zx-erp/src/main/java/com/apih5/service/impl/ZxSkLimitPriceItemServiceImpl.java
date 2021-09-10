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
import com.apih5.mybatis.dao.ZxSkLimitPriceItemMapper;
import com.apih5.mybatis.pojo.ZxSkLimitPriceItem;
import com.apih5.service.ZxSkLimitPriceItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkLimitPriceItemService")
public class ZxSkLimitPriceItemServiceImpl implements ZxSkLimitPriceItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkLimitPriceItemMapper zxSkLimitPriceItemMapper;

    @Override
    public ResponseEntity getZxSkLimitPriceItemListByCondition(ZxSkLimitPriceItem zxSkLimitPriceItem) {
        if (zxSkLimitPriceItem == null) {
            zxSkLimitPriceItem = new ZxSkLimitPriceItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkLimitPriceItem.getPage(),zxSkLimitPriceItem.getLimit());
        // 获取数据
        List<ZxSkLimitPriceItem> zxSkLimitPriceItemList = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
        // 得到分页信息
        PageInfo<ZxSkLimitPriceItem> p = new PageInfo<>(zxSkLimitPriceItemList);

        return repEntity.okList(zxSkLimitPriceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkLimitPriceItemDetails(ZxSkLimitPriceItem zxSkLimitPriceItem) {
        if (zxSkLimitPriceItem == null) {
            zxSkLimitPriceItem = new ZxSkLimitPriceItem();
        }
        // 获取数据
        ZxSkLimitPriceItem dbZxSkLimitPriceItem = zxSkLimitPriceItemMapper.selectByPrimaryKey(zxSkLimitPriceItem.getId());
        // 数据存在
        if (dbZxSkLimitPriceItem != null) {
            return repEntity.ok(dbZxSkLimitPriceItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkLimitPriceItem(ZxSkLimitPriceItem zxSkLimitPriceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkLimitPriceItem.setId(UuidUtil.generate());
        zxSkLimitPriceItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkLimitPriceItemMapper.insert(zxSkLimitPriceItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkLimitPriceItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkLimitPriceItem(ZxSkLimitPriceItem zxSkLimitPriceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkLimitPriceItem dbzxSkLimitPriceItem = zxSkLimitPriceItemMapper.selectByPrimaryKey(zxSkLimitPriceItem.getId());
        if (dbzxSkLimitPriceItem != null && StrUtil.isNotEmpty(dbzxSkLimitPriceItem.getId())) {
           // 主表id
           dbzxSkLimitPriceItem.setMasterId(zxSkLimitPriceItem.getMasterId());
           // 物资大类id
           dbzxSkLimitPriceItem.setResourceId(zxSkLimitPriceItem.getResourceId());
           // 物资大类
           dbzxSkLimitPriceItem.setResourceName(zxSkLimitPriceItem.getResourceName());
           // 物资(大类)编码
           dbzxSkLimitPriceItem.setResourceNo(zxSkLimitPriceItem.getResourceNo());
           // 物资名称
           dbzxSkLimitPriceItem.setWorkName(zxSkLimitPriceItem.getWorkName());
           // 物资id
           dbzxSkLimitPriceItem.setWorkId(zxSkLimitPriceItem.getWorkId());
           // 规格型号
           dbzxSkLimitPriceItem.setSpec(zxSkLimitPriceItem.getSpec());
           // 单位
           dbzxSkLimitPriceItem.setUnit(zxSkLimitPriceItem.getUnit());
           // 当期采集单价
           dbzxSkLimitPriceItem.setPrice(zxSkLimitPriceItem.getPrice());
           // 更新时间
           dbzxSkLimitPriceItem.setEditTime(zxSkLimitPriceItem.getEditTime());
           // 所属公司排序
           dbzxSkLimitPriceItem.setComOrders(zxSkLimitPriceItem.getComOrders());
           // 所属公司id
           dbzxSkLimitPriceItem.setComId(zxSkLimitPriceItem.getComId());
           // 所属公司名称
           dbzxSkLimitPriceItem.setComName(zxSkLimitPriceItem.getComName());
           // 物资(单个详细)编码
           dbzxSkLimitPriceItem.setWorkNo(zxSkLimitPriceItem.getWorkNo());
           // 共通
           dbzxSkLimitPriceItem.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceItemMapper.updateByPrimaryKey(dbzxSkLimitPriceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkLimitPriceItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceItem(List<ZxSkLimitPriceItem> zxSkLimitPriceItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkLimitPriceItemList != null && zxSkLimitPriceItemList.size() > 0) {
           ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
           zxSkLimitPriceItem.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceItemMapper.batchDeleteUpdateZxSkLimitPriceItem(zxSkLimitPriceItemList, zxSkLimitPriceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkLimitPriceItemList);
        }
    }
}
