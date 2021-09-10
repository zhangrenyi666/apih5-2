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
import com.apih5.mybatis.dao.ZxSaProjectSetItemMapper;
import com.apih5.mybatis.pojo.ZxSaProjectSetItem;
import com.apih5.service.ZxSaProjectSetItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectSetItemService")
public class ZxSaProjectSetItemServiceImpl implements ZxSaProjectSetItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectSetItemMapper zxSaProjectSetItemMapper;

    @Override
    public ResponseEntity getZxSaProjectSetItemListByCondition(ZxSaProjectSetItem zxSaProjectSetItem) {
        if (zxSaProjectSetItem == null) {
            zxSaProjectSetItem = new ZxSaProjectSetItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaProjectSetItem.getPage(),zxSaProjectSetItem.getLimit());
        // 获取数据
        List<ZxSaProjectSetItem> zxSaProjectSetItemList = zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(zxSaProjectSetItem);
        // 得到分页信息
        PageInfo<ZxSaProjectSetItem> p = new PageInfo<>(zxSaProjectSetItemList);

        return repEntity.okList(zxSaProjectSetItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectSetItemDetails(ZxSaProjectSetItem zxSaProjectSetItem) {
        if (zxSaProjectSetItem == null) {
            zxSaProjectSetItem = new ZxSaProjectSetItem();
        }
        // 获取数据
        ZxSaProjectSetItem dbZxSaProjectSetItem = zxSaProjectSetItemMapper.selectByPrimaryKey(zxSaProjectSetItem.getId());
        // 数据存在
        if (dbZxSaProjectSetItem != null) {
            return repEntity.ok(dbZxSaProjectSetItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSaProjectSetItem(ZxSaProjectSetItem zxSaProjectSetItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectSetItem.setId(UuidUtil.generate());
        zxSaProjectSetItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectSetItemMapper.insert(zxSaProjectSetItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSaProjectSetItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaProjectSetItem(ZxSaProjectSetItem zxSaProjectSetItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaProjectSetItem dbzxSaProjectSetItem = zxSaProjectSetItemMapper.selectByPrimaryKey(zxSaProjectSetItem.getId());
        if (dbzxSaProjectSetItem != null && StrUtil.isNotEmpty(dbzxSaProjectSetItem.getId())) {
           // 主表id
           dbzxSaProjectSetItem.setMainID(zxSaProjectSetItem.getMainID());
           // 项目id
           dbzxSaProjectSetItem.setOrgID(zxSaProjectSetItem.getOrgID());
           // 编号
           dbzxSaProjectSetItem.setWorkNo(zxSaProjectSetItem.getWorkNo());
           // 类型
           dbzxSaProjectSetItem.setWorkType(zxSaProjectSetItem.getWorkType());
           // 名称
           dbzxSaProjectSetItem.setWorkName(zxSaProjectSetItem.getWorkName());
           // 单位
           dbzxSaProjectSetItem.setUnit(zxSaProjectSetItem.getUnit());
           // 备注
           dbzxSaProjectSetItem.setRemark(zxSaProjectSetItem.getRemark());
           // 排序号
           dbzxSaProjectSetItem.setOrderNum(zxSaProjectSetItem.getOrderNum());
           // 
           dbzxSaProjectSetItem.setComID(zxSaProjectSetItem.getComID());
           // 
           dbzxSaProjectSetItem.setComName(zxSaProjectSetItem.getComName());
           // 
           dbzxSaProjectSetItem.setComOrders(zxSaProjectSetItem.getComOrders());
           // 共通
           dbzxSaProjectSetItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSetItemMapper.updateByPrimaryKey(dbzxSaProjectSetItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSaProjectSetItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectSetItem(List<ZxSaProjectSetItem> zxSaProjectSetItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectSetItemList != null && zxSaProjectSetItemList.size() > 0) {
           ZxSaProjectSetItem zxSaProjectSetItem = new ZxSaProjectSetItem();
           zxSaProjectSetItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSetItemMapper.batchDeleteUpdateZxSaProjectSetItem(zxSaProjectSetItemList, zxSaProjectSetItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSaProjectSetItemList);
        }
    }
}
