package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipFactRealItemMapper;
import com.apih5.mybatis.pojo.ZxEqEquipFactRealItem;
import com.apih5.service.ZxEqEquipFactRealItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipFactRealItemService")
public class ZxEqEquipFactRealItemServiceImpl implements ZxEqEquipFactRealItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipFactRealItemMapper zxEqEquipFactRealItemMapper;

    @Override
    public ResponseEntity getZxEqEquipFactRealItemListByCondition(ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        if (zxEqEquipFactRealItem == null) {
            zxEqEquipFactRealItem = new ZxEqEquipFactRealItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipFactRealItem.getPage(),zxEqEquipFactRealItem.getLimit());
        // 获取数据
        List<ZxEqEquipFactRealItem> zxEqEquipFactRealItemList = zxEqEquipFactRealItemMapper.selectByZxEqEquipFactRealItemList(zxEqEquipFactRealItem);
        // 得到分页信息
        PageInfo<ZxEqEquipFactRealItem> p = new PageInfo<>(zxEqEquipFactRealItemList);

        return repEntity.okList(zxEqEquipFactRealItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipFactRealItemDetails(ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        if (zxEqEquipFactRealItem == null) {
            zxEqEquipFactRealItem = new ZxEqEquipFactRealItem();
        }
        // 获取数据
        ZxEqEquipFactRealItem dbZxEqEquipFactRealItem = zxEqEquipFactRealItemMapper.selectByPrimaryKey(zxEqEquipFactRealItem.getId());
        // 数据存在
        if (dbZxEqEquipFactRealItem != null) {
            return repEntity.ok(dbZxEqEquipFactRealItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipFactRealItem(ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquipFactRealItem.setId(UuidUtil.generate());
        zxEqEquipFactRealItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipFactRealItemMapper.insert(zxEqEquipFactRealItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipFactRealItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipFactRealItem(ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipFactRealItem dbzxEqEquipFactRealItem = zxEqEquipFactRealItemMapper.selectByPrimaryKey(zxEqEquipFactRealItem.getId());
        if (dbzxEqEquipFactRealItem != null && StrUtil.isNotEmpty(dbzxEqEquipFactRealItem.getId())) {
           // mainID
           dbzxEqEquipFactRealItem.setMainID(zxEqEquipFactRealItem.getMainID());
           // 机械名称id
           dbzxEqEquipFactRealItem.setEquipID(zxEqEquipFactRealItem.getEquipID());
           // equipNo
           dbzxEqEquipFactRealItem.setEquipNo(zxEqEquipFactRealItem.getEquipNo());
           // 机械名称name
           dbzxEqEquipFactRealItem.setEquipName(zxEqEquipFactRealItem.getEquipName());
           // 规格
           dbzxEqEquipFactRealItem.setSpec(zxEqEquipFactRealItem.getSpec());
           // 生产厂家
           dbzxEqEquipFactRealItem.setFactory(zxEqEquipFactRealItem.getFactory());
           // 进场日期
           dbzxEqEquipFactRealItem.setInDate(zxEqEquipFactRealItem.getInDate());
           // 设备来源
           dbzxEqEquipFactRealItem.setSource(zxEqEquipFactRealItem.getSource());
           // 是否退场
           dbzxEqEquipFactRealItem.setIsOut(zxEqEquipFactRealItem.getIsOut());
           // 编制时间
           dbzxEqEquipFactRealItem.setEditTime(zxEqEquipFactRealItem.getEditTime());
           // 车牌号
           dbzxEqEquipFactRealItem.setCarNo(zxEqEquipFactRealItem.getCarNo());
           // 供应商
           dbzxEqEquipFactRealItem.setSupplier(zxEqEquipFactRealItem.getSupplier());
           // 验收设备名称id
           dbzxEqEquipFactRealItem.setRealEquipID(zxEqEquipFactRealItem.getRealEquipID());
           // 验收设备编号
           dbzxEqEquipFactRealItem.setRealEquipNo(zxEqEquipFactRealItem.getRealEquipNo());
           // 验收设备名称name
           dbzxEqEquipFactRealItem.setRealEquipName(zxEqEquipFactRealItem.getRealEquipName());
           // 规格
           dbzxEqEquipFactRealItem.setRealUnit(zxEqEquipFactRealItem.getRealUnit());
           // 型号
           dbzxEqEquipFactRealItem.setRealSpec(zxEqEquipFactRealItem.getRealSpec());
           // 
           dbzxEqEquipFactRealItem.setFactItemID(zxEqEquipFactRealItem.getFactItemID());
           // 共通
           dbzxEqEquipFactRealItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipFactRealItemMapper.updateByPrimaryKey(dbzxEqEquipFactRealItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipFactRealItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipFactRealItem(List<ZxEqEquipFactRealItem> zxEqEquipFactRealItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipFactRealItemList != null && zxEqEquipFactRealItemList.size() > 0) {
           ZxEqEquipFactRealItem zxEqEquipFactRealItem = new ZxEqEquipFactRealItem();
           zxEqEquipFactRealItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipFactRealItemMapper.batchDeleteUpdateZxEqEquipFactRealItem(zxEqEquipFactRealItemList, zxEqEquipFactRealItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipFactRealItemList);
        }
    }
}
