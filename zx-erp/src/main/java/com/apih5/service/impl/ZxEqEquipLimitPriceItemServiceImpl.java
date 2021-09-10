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
import com.apih5.mybatis.dao.ZxEqEquipLimitPriceItemMapper;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPriceItem;
import com.apih5.service.ZxEqEquipLimitPriceItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipLimitPriceItemService")
public class ZxEqEquipLimitPriceItemServiceImpl implements ZxEqEquipLimitPriceItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipLimitPriceItemMapper zxEqEquipLimitPriceItemMapper;

    @Override
    public ResponseEntity getZxEqEquipLimitPriceItemListByCondition(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        if (zxEqEquipLimitPriceItem == null) {
            zxEqEquipLimitPriceItem = new ZxEqEquipLimitPriceItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipLimitPriceItem.getPage(),zxEqEquipLimitPriceItem.getLimit());
        // 获取数据
        List<ZxEqEquipLimitPriceItem> zxEqEquipLimitPriceItemList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(zxEqEquipLimitPriceItem);
        // 得到分页信息
        PageInfo<ZxEqEquipLimitPriceItem> p = new PageInfo<>(zxEqEquipLimitPriceItemList);

        return repEntity.okList(zxEqEquipLimitPriceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipLimitPriceItemDetails(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        if (zxEqEquipLimitPriceItem == null) {
            zxEqEquipLimitPriceItem = new ZxEqEquipLimitPriceItem();
        }
        // 获取数据
        ZxEqEquipLimitPriceItem dbZxEqEquipLimitPriceItem = zxEqEquipLimitPriceItemMapper.selectByPrimaryKey(zxEqEquipLimitPriceItem.getId());
        // 数据存在
        if (dbZxEqEquipLimitPriceItem != null) {
            return repEntity.ok(dbZxEqEquipLimitPriceItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipLimitPriceItem(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquipLimitPriceItem.setId(UuidUtil.generate());
        zxEqEquipLimitPriceItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipLimitPriceItemMapper.insert(zxEqEquipLimitPriceItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipLimitPriceItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipLimitPriceItem(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipLimitPriceItem dbzxEqEquipLimitPriceItem = zxEqEquipLimitPriceItemMapper.selectByPrimaryKey(zxEqEquipLimitPriceItem.getId());
        if (dbzxEqEquipLimitPriceItem != null && StrUtil.isNotEmpty(dbzxEqEquipLimitPriceItem.getId())) {
           // mainid
           dbzxEqEquipLimitPriceItem.setMainID(zxEqEquipLimitPriceItem.getMainID());
           // 机种分类id
           dbzxEqEquipLimitPriceItem.setResCatalogID(zxEqEquipLimitPriceItem.getResCatalogID());
           // 设备编码id
           dbzxEqEquipLimitPriceItem.setEquipID(zxEqEquipLimitPriceItem.getEquipID());
           // 设备编码
           dbzxEqEquipLimitPriceItem.setEquipNo(zxEqEquipLimitPriceItem.getEquipNo());
           // 设备名称
           dbzxEqEquipLimitPriceItem.setEquipName(zxEqEquipLimitPriceItem.getEquipName());
           // 所在省份
           dbzxEqEquipLimitPriceItem.setProvince(zxEqEquipLimitPriceItem.getProvince());
           // 厂家
           dbzxEqEquipLimitPriceItem.setFactory(zxEqEquipLimitPriceItem.getFactory());
           // 规格型号
           dbzxEqEquipLimitPriceItem.setSpec(zxEqEquipLimitPriceItem.getSpec());
           // 工作时间
           dbzxEqEquipLimitPriceItem.setWorkTime(zxEqEquipLimitPriceItem.getWorkTime());
           // 租赁情况
           dbzxEqEquipLimitPriceItem.setRentContent(zxEqEquipLimitPriceItem.getRentContent());
           // 燃油情况
           dbzxEqEquipLimitPriceItem.setRanyouQingkuang(zxEqEquipLimitPriceItem.getRanyouQingkuang());
           // 租赁限价（元/台.月）
           dbzxEqEquipLimitPriceItem.setRentPrice(zxEqEquipLimitPriceItem.getRentPrice());
           // 备注
           dbzxEqEquipLimitPriceItem.setRemark(zxEqEquipLimitPriceItem.getRemark());
           // 是否含司机
           dbzxEqEquipLimitPriceItem.setIsDriver(zxEqEquipLimitPriceItem.getIsDriver());
           // 共通
           dbzxEqEquipLimitPriceItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipLimitPriceItemMapper.updateByPrimaryKey(dbzxEqEquipLimitPriceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipLimitPriceItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipLimitPriceItem(List<ZxEqEquipLimitPriceItem> zxEqEquipLimitPriceItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipLimitPriceItemList != null && zxEqEquipLimitPriceItemList.size() > 0) {
           ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem = new ZxEqEquipLimitPriceItem();
           zxEqEquipLimitPriceItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipLimitPriceItemMapper.batchDeleteUpdateZxEqEquipLimitPriceItem(zxEqEquipLimitPriceItemList, zxEqEquipLimitPriceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipLimitPriceItemList);
        }
    }

    public List<ZxEqEquipLimitPriceItem> getLimitPriceForm( ){
      return  zxEqEquipLimitPriceItemMapper.getLimitPriceForm();
    }
}
