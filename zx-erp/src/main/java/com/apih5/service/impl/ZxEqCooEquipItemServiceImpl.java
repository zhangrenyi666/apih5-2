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
import com.apih5.mybatis.dao.ZxEqCooEquipItemMapper;
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;
import com.apih5.service.ZxEqCooEquipItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqCooEquipItemService")
public class ZxEqCooEquipItemServiceImpl implements ZxEqCooEquipItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqCooEquipItemMapper zxEqCooEquipItemMapper;

    @Override
    public ResponseEntity getZxEqCooEquipItemListByCondition(ZxEqCooEquipItem zxEqCooEquipItem) {
        if (zxEqCooEquipItem == null) {
            zxEqCooEquipItem = new ZxEqCooEquipItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqCooEquipItem.getPage(),zxEqCooEquipItem.getLimit());
        // 获取数据
        List<ZxEqCooEquipItem> zxEqCooEquipItemList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(zxEqCooEquipItem);
        // 得到分页信息
        PageInfo<ZxEqCooEquipItem> p = new PageInfo<>(zxEqCooEquipItemList);

        return repEntity.okList(zxEqCooEquipItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqCooEquipItemDetails(ZxEqCooEquipItem zxEqCooEquipItem) {
        if (zxEqCooEquipItem == null) {
            zxEqCooEquipItem = new ZxEqCooEquipItem();
        }
        // 获取数据
        ZxEqCooEquipItem dbZxEqCooEquipItem = zxEqCooEquipItemMapper.selectByPrimaryKey(zxEqCooEquipItem.getId());
        // 数据存在
        if (dbZxEqCooEquipItem != null) {
            return repEntity.ok(dbZxEqCooEquipItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqCooEquipItem(ZxEqCooEquipItem zxEqCooEquipItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqCooEquipItem.setId(UuidUtil.generate());
        zxEqCooEquipItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqCooEquipItemMapper.insert(zxEqCooEquipItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqCooEquipItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqCooEquipItem(ZxEqCooEquipItem zxEqCooEquipItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqCooEquipItem dbzxEqCooEquipItem = zxEqCooEquipItemMapper.selectByPrimaryKey(zxEqCooEquipItem.getId());
        if (dbzxEqCooEquipItem != null && StrUtil.isNotEmpty(dbzxEqCooEquipItem.getId())) {
           // 主表id
           dbzxEqCooEquipItem.setMasID(zxEqCooEquipItem.getMasID());
           // 设备名称
           dbzxEqCooEquipItem.setEquipName(zxEqCooEquipItem.getEquipName());
           // 型号
           dbzxEqCooEquipItem.setSpec(zxEqCooEquipItem.getSpec());
           // 规格
           dbzxEqCooEquipItem.setModel(zxEqCooEquipItem.getModel());
           // 功率（KW）
           dbzxEqCooEquipItem.setPower(zxEqCooEquipItem.getPower());
           // 生产厂家
           dbzxEqCooEquipItem.setOutfactory(zxEqCooEquipItem.getOutfactory());
           // 出产日期
           dbzxEqCooEquipItem.setOutfactoryDate(zxEqCooEquipItem.getOutfactoryDate());
           // 原值（元）
           dbzxEqCooEquipItem.setOrginalValue(zxEqCooEquipItem.getOrginalValue());
           // 净值（元）
           dbzxEqCooEquipItem.setLeftValue(zxEqCooEquipItem.getLeftValue());
           // 新旧程度
           dbzxEqCooEquipItem.setOldrate(zxEqCooEquipItem.getOldrate());
           // 使用开始时间
           dbzxEqCooEquipItem.setBeginDate(zxEqCooEquipItem.getBeginDate());
           // 使用结束时间
           dbzxEqCooEquipItem.setEndDate(zxEqCooEquipItem.getEndDate());
           // 备注
           dbzxEqCooEquipItem.setRemark(zxEqCooEquipItem.getRemark());
           // 编制时间
           dbzxEqCooEquipItem.setEditTime(zxEqCooEquipItem.getEditTime());
           // 编号
           dbzxEqCooEquipItem.setEquipNo(zxEqCooEquipItem.getEquipNo());
           // 设备名称id
           dbzxEqCooEquipItem.setEquipID(zxEqCooEquipItem.getEquipID());
           // 
           dbzxEqCooEquipItem.setAcceptance(zxEqCooEquipItem.getAcceptance());
           // 是否特种设备
           dbzxEqCooEquipItem.setIsSepcial(zxEqCooEquipItem.getIsSepcial());
           // 特种设备检验报告
           dbzxEqCooEquipItem.setInspReport(zxEqCooEquipItem.getInspReport());
           // 特种设备使用登记证
           dbzxEqCooEquipItem.setInspCert(zxEqCooEquipItem.getInspCert());
           // 
           dbzxEqCooEquipItem.setOpCert(zxEqCooEquipItem.getOpCert());
           // 是否退场
           dbzxEqCooEquipItem.setIsOut(zxEqCooEquipItem.getIsOut());
           // 共通
           dbzxEqCooEquipItem.setModifyUserInfo(userKey, realName);
           flag = zxEqCooEquipItemMapper.updateByPrimaryKey(dbzxEqCooEquipItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqCooEquipItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqCooEquipItem(List<ZxEqCooEquipItem> zxEqCooEquipItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqCooEquipItemList != null && zxEqCooEquipItemList.size() > 0) {
           ZxEqCooEquipItem zxEqCooEquipItem = new ZxEqCooEquipItem();
           zxEqCooEquipItem.setModifyUserInfo(userKey, realName);
           flag = zxEqCooEquipItemMapper.batchDeleteUpdateZxEqCooEquipItem(zxEqCooEquipItemList, zxEqCooEquipItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqCooEquipItemList);
        }
    }
}
