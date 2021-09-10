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
import com.apih5.mybatis.dao.ZxEqAssetSellItemMapper;
import com.apih5.mybatis.pojo.ZxEqAssetSellItem;
import com.apih5.service.ZxEqAssetSellItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqAssetSellItemService")
public class ZxEqAssetSellItemServiceImpl implements ZxEqAssetSellItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqAssetSellItemMapper zxEqAssetSellItemMapper;

    @Override
    public ResponseEntity getZxEqAssetSellItemListByCondition(ZxEqAssetSellItem zxEqAssetSellItem) {
        if (zxEqAssetSellItem == null) {
            zxEqAssetSellItem = new ZxEqAssetSellItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqAssetSellItem.getPage(),zxEqAssetSellItem.getLimit());
        // 获取数据
        List<ZxEqAssetSellItem> zxEqAssetSellItemList = zxEqAssetSellItemMapper.selectByZxEqAssetSellItemList(zxEqAssetSellItem);
        // 得到分页信息
        PageInfo<ZxEqAssetSellItem> p = new PageInfo<>(zxEqAssetSellItemList);

        return repEntity.okList(zxEqAssetSellItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqAssetSellItemDetails(ZxEqAssetSellItem zxEqAssetSellItem) {
        if (zxEqAssetSellItem == null) {
            zxEqAssetSellItem = new ZxEqAssetSellItem();
        }
        // 获取数据
        ZxEqAssetSellItem dbZxEqAssetSellItem = zxEqAssetSellItemMapper.selectByPrimaryKey(zxEqAssetSellItem.getId());
        // 数据存在
        if (dbZxEqAssetSellItem != null) {
            return repEntity.ok(dbZxEqAssetSellItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqAssetSellItem(ZxEqAssetSellItem zxEqAssetSellItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqAssetSellItem.setId(UuidUtil.generate());
        zxEqAssetSellItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqAssetSellItemMapper.insert(zxEqAssetSellItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqAssetSellItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqAssetSellItem(ZxEqAssetSellItem zxEqAssetSellItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqAssetSellItem dbzxEqAssetSellItem = zxEqAssetSellItemMapper.selectByPrimaryKey(zxEqAssetSellItem.getId());
        if (dbzxEqAssetSellItem != null && StrUtil.isNotEmpty(dbzxEqAssetSellItem.getId())) {
           // z主表id
           dbzxEqAssetSellItem.setMasID(zxEqAssetSellItem.getMasID());
           // 资产名称id
           dbzxEqAssetSellItem.setEquipID(zxEqAssetSellItem.getEquipID());
           // 资产名称name
           dbzxEqAssetSellItem.setEquipName(zxEqAssetSellItem.getEquipName());
           // 财务编号
           dbzxEqAssetSellItem.setFinanceNo(zxEqAssetSellItem.getFinanceNo());
           // 规格形式
           dbzxEqAssetSellItem.setSpec(zxEqAssetSellItem.getSpec());
           // 厂家
           dbzxEqAssetSellItem.setFactory(zxEqAssetSellItem.getFactory());
           // 出厂年月
           dbzxEqAssetSellItem.setOutFactoryDate(zxEqAssetSellItem.getOutFactoryDate());
           // 数量
           dbzxEqAssetSellItem.setQty(zxEqAssetSellItem.getQty());
           // 单位
           dbzxEqAssetSellItem.setUnit(zxEqAssetSellItem.getUnit());
           // 资产原值
           dbzxEqAssetSellItem.setOrginalValue(zxEqAssetSellItem.getOrginalValue());
           // 已提折旧
           dbzxEqAssetSellItem.setDeprevalue(zxEqAssetSellItem.getDeprevalue());
           // 资产净值
           dbzxEqAssetSellItem.setLeftvalue(zxEqAssetSellItem.getLeftvalue());
           // 转让价值
           dbzxEqAssetSellItem.setSellValue(zxEqAssetSellItem.getSellValue());
           // 备注
           dbzxEqAssetSellItem.setRemark(zxEqAssetSellItem.getRemark());
           // 编制时间
           dbzxEqAssetSellItem.setEditTime(zxEqAssetSellItem.getEditTime());
           // 型号
           dbzxEqAssetSellItem.setModel(zxEqAssetSellItem.getModel());
           // 机械管理编号
           dbzxEqAssetSellItem.setEquipNo(zxEqAssetSellItem.getEquipNo());
           // 共通
           dbzxEqAssetSellItem.setModifyUserInfo(userKey, realName);
           flag = zxEqAssetSellItemMapper.updateByPrimaryKey(dbzxEqAssetSellItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqAssetSellItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqAssetSellItem(List<ZxEqAssetSellItem> zxEqAssetSellItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqAssetSellItemList != null && zxEqAssetSellItemList.size() > 0) {
           ZxEqAssetSellItem zxEqAssetSellItem = new ZxEqAssetSellItem();
           zxEqAssetSellItem.setModifyUserInfo(userKey, realName);
           flag = zxEqAssetSellItemMapper.batchDeleteUpdateZxEqAssetSellItem(zxEqAssetSellItemList, zxEqAssetSellItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqAssetSellItemList);
        }
    }

	@Override
	public List<ZxEqAssetSellItem> ureportZxEqAssetSellItemList(ZxEqAssetSellItem zxEqAssetSellItem) {
		  if (zxEqAssetSellItem == null) {
	            zxEqAssetSellItem = new ZxEqAssetSellItem();
	        }
	        // 获取数据
	        List<ZxEqAssetSellItem> zxEqAssetSellItemList = zxEqAssetSellItemMapper.ureportZxEqAssetSellItemList(zxEqAssetSellItem);
	        return zxEqAssetSellItemList;
	}
}
