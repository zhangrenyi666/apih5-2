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
import com.apih5.mybatis.dao.ZxEqEquipFactItemMapper;
import com.apih5.mybatis.pojo.ZxEqEquipFactItem;
import com.apih5.service.ZxEqEquipFactItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipFactItemService")
public class ZxEqEquipFactItemServiceImpl implements ZxEqEquipFactItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipFactItemMapper zxEqEquipFactItemMapper;

    @Override
    public ResponseEntity getZxEqEquipFactItemListByCondition(ZxEqEquipFactItem zxEqEquipFactItem) {
        if (zxEqEquipFactItem == null) {
            zxEqEquipFactItem = new ZxEqEquipFactItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipFactItem.getPage(),zxEqEquipFactItem.getLimit());
        // 获取数据
        List<ZxEqEquipFactItem> zxEqEquipFactItemList = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(zxEqEquipFactItem);
        // 得到分页信息
        PageInfo<ZxEqEquipFactItem> p = new PageInfo<>(zxEqEquipFactItemList);

        return repEntity.okList(zxEqEquipFactItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipFactItemDetails(ZxEqEquipFactItem zxEqEquipFactItem) {
        if (zxEqEquipFactItem == null) {
            zxEqEquipFactItem = new ZxEqEquipFactItem();
        }
        // 获取数据
        ZxEqEquipFactItem dbZxEqEquipFactItem = zxEqEquipFactItemMapper.selectByPrimaryKey(zxEqEquipFactItem.getId());
        // 数据存在
        if (dbZxEqEquipFactItem != null) {
            return repEntity.ok(dbZxEqEquipFactItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipFactItem(ZxEqEquipFactItem zxEqEquipFactItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquipFactItem.setId(UuidUtil.generate());
        zxEqEquipFactItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipFactItemMapper.insert(zxEqEquipFactItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipFactItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipFactItem(ZxEqEquipFactItem zxEqEquipFactItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipFactItem dbzxEqEquipFactItem = zxEqEquipFactItemMapper.selectByPrimaryKey(zxEqEquipFactItem.getId());
        if (dbzxEqEquipFactItem != null && StrUtil.isNotEmpty(dbzxEqEquipFactItem.getId())) {
           // mainID
           dbzxEqEquipFactItem.setMainID(zxEqEquipFactItem.getMainID());
           // 机械名称id
           dbzxEqEquipFactItem.setEquipID(zxEqEquipFactItem.getEquipID());
           // equipNo
           dbzxEqEquipFactItem.setEquipNo(zxEqEquipFactItem.getEquipNo());
           // 机械名称name
           dbzxEqEquipFactItem.setEquipName(zxEqEquipFactItem.getEquipName());
           // 规格
           dbzxEqEquipFactItem.setSpec(zxEqEquipFactItem.getSpec());
           // 生产厂家
           dbzxEqEquipFactItem.setFactory(zxEqEquipFactItem.getFactory());
           // 进场日期
           dbzxEqEquipFactItem.setInDate(zxEqEquipFactItem.getInDate());
           // 设备来源
           dbzxEqEquipFactItem.setSource(zxEqEquipFactItem.getSource());
           // 是否退场
           dbzxEqEquipFactItem.setIsOut(zxEqEquipFactItem.getIsOut());
           // 编制时间
           dbzxEqEquipFactItem.setEditTime(zxEqEquipFactItem.getEditTime());
           // 车牌号
           dbzxEqEquipFactItem.setCarNo(zxEqEquipFactItem.getCarNo());
           // 供应商
           dbzxEqEquipFactItem.setSupplier(zxEqEquipFactItem.getSupplier());
           // 共通
           dbzxEqEquipFactItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipFactItemMapper.updateByPrimaryKey(dbzxEqEquipFactItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipFactItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipFactItem(List<ZxEqEquipFactItem> zxEqEquipFactItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipFactItemList != null && zxEqEquipFactItemList.size() > 0) {
           ZxEqEquipFactItem zxEqEquipFactItem = new ZxEqEquipFactItem();
           zxEqEquipFactItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipFactItemMapper.batchDeleteUpdateZxEqEquipFactItem(zxEqEquipFactItemList, zxEqEquipFactItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipFactItemList);
        }
    }
}
