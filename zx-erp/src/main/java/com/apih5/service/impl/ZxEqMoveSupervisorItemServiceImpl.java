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
import com.apih5.mybatis.dao.ZxEqMoveSupervisorItemMapper;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisorItem;
import com.apih5.service.ZxEqMoveSupervisorItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqMoveSupervisorItemService")
public class ZxEqMoveSupervisorItemServiceImpl implements ZxEqMoveSupervisorItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqMoveSupervisorItemMapper zxEqMoveSupervisorItemMapper;

    @Override
    public ResponseEntity getZxEqMoveSupervisorItemListByCondition(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        if (zxEqMoveSupervisorItem == null) {
            zxEqMoveSupervisorItem = new ZxEqMoveSupervisorItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqMoveSupervisorItem.getPage(),zxEqMoveSupervisorItem.getLimit());
        // 获取数据
        List<ZxEqMoveSupervisorItem> zxEqMoveSupervisorItemList = zxEqMoveSupervisorItemMapper.selectByZxEqMoveSupervisorItemList(zxEqMoveSupervisorItem);
        // 得到分页信息
        PageInfo<ZxEqMoveSupervisorItem> p = new PageInfo<>(zxEqMoveSupervisorItemList);

        return repEntity.okList(zxEqMoveSupervisorItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMoveSupervisorItemDetails(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        if (zxEqMoveSupervisorItem == null) {
            zxEqMoveSupervisorItem = new ZxEqMoveSupervisorItem();
        }
        // 获取数据
        ZxEqMoveSupervisorItem dbZxEqMoveSupervisorItem = zxEqMoveSupervisorItemMapper.selectByPrimaryKey(zxEqMoveSupervisorItem.getId());
        // 数据存在
        if (dbZxEqMoveSupervisorItem != null) {
            return repEntity.ok(dbZxEqMoveSupervisorItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqMoveSupervisorItem(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqMoveSupervisorItem.setId(UuidUtil.generate());
        zxEqMoveSupervisorItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqMoveSupervisorItemMapper.insert(zxEqMoveSupervisorItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqMoveSupervisorItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqMoveSupervisorItem(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqMoveSupervisorItem dbzxEqMoveSupervisorItem = zxEqMoveSupervisorItemMapper.selectByPrimaryKey(zxEqMoveSupervisorItem.getId());
        if (dbzxEqMoveSupervisorItem != null && StrUtil.isNotEmpty(dbzxEqMoveSupervisorItem.getId())) {
           // 主表id
           dbzxEqMoveSupervisorItem.setMasID(zxEqMoveSupervisorItem.getMasID());
           // 资产名称id
           dbzxEqMoveSupervisorItem.setEquipID(zxEqMoveSupervisorItem.getEquipID());
           // 资产名称name
           dbzxEqMoveSupervisorItem.setEquipName(zxEqMoveSupervisorItem.getEquipName());
           // 财务编号
           dbzxEqMoveSupervisorItem.setFinanceNo(zxEqMoveSupervisorItem.getFinanceNo());
           // 规格形式
           dbzxEqMoveSupervisorItem.setSpec(zxEqMoveSupervisorItem.getSpec());
           // 国家厂家
           dbzxEqMoveSupervisorItem.setFactory(zxEqMoveSupervisorItem.getFactory());
           // 出厂年月
           dbzxEqMoveSupervisorItem.setOutFactoryDate(zxEqMoveSupervisorItem.getOutFactoryDate());
           // 数量
           dbzxEqMoveSupervisorItem.setQty(zxEqMoveSupervisorItem.getQty());
           // 单位
           dbzxEqMoveSupervisorItem.setUnit(zxEqMoveSupervisorItem.getUnit());
           // 原值（元）
           dbzxEqMoveSupervisorItem.setOrginalValue(zxEqMoveSupervisorItem.getOrginalValue());
           // 已提折旧
           dbzxEqMoveSupervisorItem.setDeprevalue(zxEqMoveSupervisorItem.getDeprevalue());
           // 净值（元）
           dbzxEqMoveSupervisorItem.setLeftvalue(zxEqMoveSupervisorItem.getLeftvalue());
           // 转让价值（元）
           dbzxEqMoveSupervisorItem.setSellValue(zxEqMoveSupervisorItem.getSellValue());
           // 备注
           dbzxEqMoveSupervisorItem.setRemark(zxEqMoveSupervisorItem.getRemark());
           // 编制时间
           dbzxEqMoveSupervisorItem.setEditTime(zxEqMoveSupervisorItem.getEditTime());
           // 设备新管理编号
           dbzxEqMoveSupervisorItem.setNewManageNo(zxEqMoveSupervisorItem.getNewManageNo());
           // 型号
           dbzxEqMoveSupervisorItem.setModel(zxEqMoveSupervisorItem.getModel());
           // 设备管理编号
           dbzxEqMoveSupervisorItem.setEquipNo(zxEqMoveSupervisorItem.getEquipNo());
           // 使用单位id
           dbzxEqMoveSupervisorItem.setUseOrgId(zxEqMoveSupervisorItem.getUseOrgId());
           // 使用单位name
           dbzxEqMoveSupervisorItem.setUseOrgName(zxEqMoveSupervisorItem.getUseOrgName());
           // 共通
           dbzxEqMoveSupervisorItem.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorItemMapper.updateByPrimaryKey(dbzxEqMoveSupervisorItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqMoveSupervisorItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqMoveSupervisorItem(List<ZxEqMoveSupervisorItem> zxEqMoveSupervisorItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqMoveSupervisorItemList != null && zxEqMoveSupervisorItemList.size() > 0) {
           ZxEqMoveSupervisorItem zxEqMoveSupervisorItem = new ZxEqMoveSupervisorItem();
           zxEqMoveSupervisorItem.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorItemMapper.batchDeleteUpdateZxEqMoveSupervisorItem(zxEqMoveSupervisorItemList, zxEqMoveSupervisorItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqMoveSupervisorItemList);
        }
    }

	@Override
	public ResponseEntity getZxEqMoveSupervisorItemListForTab(ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
		if (zxEqMoveSupervisorItem == null) {
			zxEqMoveSupervisorItem = new ZxEqMoveSupervisorItem();
		}
		// 分页查询
		PageHelper.startPage(zxEqMoveSupervisorItem.getPage(),zxEqMoveSupervisorItem.getLimit());
		// 获取数据
		List<ZxEqMoveSupervisorItem> zxEqMoveSupervisorItemList = zxEqMoveSupervisorItemMapper.getZxEqMoveSupervisorItemListForTab(zxEqMoveSupervisorItem);
		// 得到分页信息
		PageInfo<ZxEqMoveSupervisorItem> p = new PageInfo<>(zxEqMoveSupervisorItemList);

		return repEntity.okList(zxEqMoveSupervisorItemList, p.getTotal());
	}
}
