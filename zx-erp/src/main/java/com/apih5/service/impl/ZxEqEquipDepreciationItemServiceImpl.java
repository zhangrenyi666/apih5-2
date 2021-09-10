package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipDepreciationItemMapper;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciationItem;
import com.apih5.service.ZxEqEquipDepreciationItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipDepreciationItemService")
public class ZxEqEquipDepreciationItemServiceImpl implements ZxEqEquipDepreciationItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipDepreciationItemMapper zxEqEquipDepreciationItemMapper;

    @Override
    public ResponseEntity getZxEqEquipDepreciationItemListByCondition(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        if (zxEqEquipDepreciationItem == null) {
            zxEqEquipDepreciationItem = new ZxEqEquipDepreciationItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipDepreciationItem.getPage(),zxEqEquipDepreciationItem.getLimit());
        // 获取数据
        List<ZxEqEquipDepreciationItem> zxEqEquipDepreciationItemList = zxEqEquipDepreciationItemMapper.selectByZxEqEquipDepreciationItemList(zxEqEquipDepreciationItem);
        // 得到分页信息
        PageInfo<ZxEqEquipDepreciationItem> p = new PageInfo<>(zxEqEquipDepreciationItemList);

        return repEntity.okList(zxEqEquipDepreciationItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipDepreciationItemDetails(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        if (zxEqEquipDepreciationItem == null) {
            zxEqEquipDepreciationItem = new ZxEqEquipDepreciationItem();
        }
        // 获取数据
        ZxEqEquipDepreciationItem dbZxEqEquipDepreciationItem = zxEqEquipDepreciationItemMapper.selectByPrimaryKey(zxEqEquipDepreciationItem.getId());
        // 数据存在
        if (dbZxEqEquipDepreciationItem != null) {
            return repEntity.ok(dbZxEqEquipDepreciationItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipDepreciationItem(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquipDepreciationItem.setId(UuidUtil.generate());
        zxEqEquipDepreciationItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipDepreciationItemMapper.insert(zxEqEquipDepreciationItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipDepreciationItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipDepreciationItem(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipDepreciationItem dbzxEqEquipDepreciationItem = zxEqEquipDepreciationItemMapper.selectByPrimaryKey(zxEqEquipDepreciationItem.getId());
        if (dbzxEqEquipDepreciationItem != null && StrUtil.isNotEmpty(dbzxEqEquipDepreciationItem.getId())) {
           // 主表id
           dbzxEqEquipDepreciationItem.setMasID(zxEqEquipDepreciationItem.getMasID());
           // 设备id
           dbzxEqEquipDepreciationItem.setEquipID(zxEqEquipDepreciationItem.getEquipID());
           // 使用部门id
           dbzxEqEquipDepreciationItem.setUseOrgID(zxEqEquipDepreciationItem.getUseOrgID());
           // 本月折旧
           dbzxEqEquipDepreciationItem.setDepreamout(zxEqEquipDepreciationItem.getDepreamout());
           // 折旧累计
           dbzxEqEquipDepreciationItem.setFinanceOrginalValue(zxEqEquipDepreciationItem.getFinanceOrginalValue());
           // 原值
           dbzxEqEquipDepreciationItem.setOrginalValue(zxEqEquipDepreciationItem.getOrginalValue());
           // 期末净值
           dbzxEqEquipDepreciationItem.setLeftValue(zxEqEquipDepreciationItem.getLeftValue());
           // 备注
           dbzxEqEquipDepreciationItem.setRemark(zxEqEquipDepreciationItem.getRemark());
           // 共通
           dbzxEqEquipDepreciationItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipDepreciationItemMapper.updateByPrimaryKey(dbzxEqEquipDepreciationItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipDepreciationItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipDepreciationItem(List<ZxEqEquipDepreciationItem> zxEqEquipDepreciationItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipDepreciationItemList != null && zxEqEquipDepreciationItemList.size() > 0) {
           ZxEqEquipDepreciationItem zxEqEquipDepreciationItem = new ZxEqEquipDepreciationItem();
           zxEqEquipDepreciationItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipDepreciationItemMapper.batchDeleteUpdateZxEqEquipDepreciationItem(zxEqEquipDepreciationItemList, zxEqEquipDepreciationItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipDepreciationItemList);
        }
    }

	@Override
	public ResponseEntity getZxEqEquipDepreciationItemListForTab(ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
		if (zxEqEquipDepreciationItem == null) {
            zxEqEquipDepreciationItem = new ZxEqEquipDepreciationItem();
        }
		if(StrUtil.isEmpty(zxEqEquipDepreciationItem.getEquipID())) {
			return repEntity.layerMessage("no", "设备id必传！");
		}
        // 分页查询
        PageHelper.startPage(zxEqEquipDepreciationItem.getPage(),zxEqEquipDepreciationItem.getLimit());
        // 获取数据
        List<ZxEqEquipDepreciationItem> zxEqEquipDepreciationItemList = zxEqEquipDepreciationItemMapper.getZxEqEquipDepreciationItemListForTab(zxEqEquipDepreciationItem);
        // 得到分页信息
        PageInfo<ZxEqEquipDepreciationItem> p = new PageInfo<>(zxEqEquipDepreciationItemList);

        return repEntity.okList(zxEqEquipDepreciationItemList, p.getTotal());
    
	}
}
