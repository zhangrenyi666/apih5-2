package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqMoveUseOrgItemMapper;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrgItem;
import com.apih5.service.ZxEqMoveUseOrgItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqMoveUseOrgItemService")
public class ZxEqMoveUseOrgItemServiceImpl implements ZxEqMoveUseOrgItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqMoveUseOrgItemMapper zxEqMoveUseOrgItemMapper;

    @Override
    public ResponseEntity getZxEqMoveUseOrgItemListByCondition(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        if (zxEqMoveUseOrgItem == null) {
            zxEqMoveUseOrgItem = new ZxEqMoveUseOrgItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqMoveUseOrgItem.getPage(),zxEqMoveUseOrgItem.getLimit());
        // 获取数据
        List<ZxEqMoveUseOrgItem> zxEqMoveUseOrgItemList = zxEqMoveUseOrgItemMapper.selectByZxEqMoveUseOrgItemList(zxEqMoveUseOrgItem);
        // 得到分页信息
        PageInfo<ZxEqMoveUseOrgItem> p = new PageInfo<>(zxEqMoveUseOrgItemList);

        return repEntity.okList(zxEqMoveUseOrgItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMoveUseOrgItemDetails(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        if (zxEqMoveUseOrgItem == null) {
            zxEqMoveUseOrgItem = new ZxEqMoveUseOrgItem();
        }
        // 获取数据
        ZxEqMoveUseOrgItem dbZxEqMoveUseOrgItem = zxEqMoveUseOrgItemMapper.selectByPrimaryKey(zxEqMoveUseOrgItem.getId());
        // 数据存在
        if (dbZxEqMoveUseOrgItem != null) {
            return repEntity.ok(dbZxEqMoveUseOrgItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqMoveUseOrgItem(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqMoveUseOrgItem.setId(UuidUtil.generate());
        zxEqMoveUseOrgItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqMoveUseOrgItemMapper.insert(zxEqMoveUseOrgItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqMoveUseOrgItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqMoveUseOrgItem(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqMoveUseOrgItem dbzxEqMoveUseOrgItem = zxEqMoveUseOrgItemMapper.selectByPrimaryKey(zxEqMoveUseOrgItem.getId());
        if (dbzxEqMoveUseOrgItem != null && StrUtil.isNotEmpty(dbzxEqMoveUseOrgItem.getId())) {
           // 主表id
           dbzxEqMoveUseOrgItem.setMoveID(zxEqMoveUseOrgItem.getMoveID());
           // 机械管理编号id
           dbzxEqMoveUseOrgItem.setEquipID(zxEqMoveUseOrgItem.getEquipID());
           // 签收日期
           dbzxEqMoveUseOrgItem.setAcceptDate(zxEqMoveUseOrgItem.getAcceptDate());
           // 签收人
           dbzxEqMoveUseOrgItem.setConsignee(zxEqMoveUseOrgItem.getConsignee());
           // 调出方经办人
           dbzxEqMoveUseOrgItem.setOutTransactor(zxEqMoveUseOrgItem.getOutTransactor());
           // 技术鉴定
           dbzxEqMoveUseOrgItem.setTechCheckup(zxEqMoveUseOrgItem.getTechCheckup());
           // 结算单位
           dbzxEqMoveUseOrgItem.setBalUnit(zxEqMoveUseOrgItem.getBalUnit());
           // 结算单价
           dbzxEqMoveUseOrgItem.setBalPrice(zxEqMoveUseOrgItem.getBalPrice());
           // 备注
           dbzxEqMoveUseOrgItem.setRemark(zxEqMoveUseOrgItem.getRemark());
           // 原值
           dbzxEqMoveUseOrgItem.setOrginalValue(zxEqMoveUseOrgItem.getOrginalValue());
           // 净值
           dbzxEqMoveUseOrgItem.setLeftValue(zxEqMoveUseOrgItem.getLeftValue());
           // 调入地点
           dbzxEqMoveUseOrgItem.setStartPlace(zxEqMoveUseOrgItem.getStartPlace());
           // 起始时间
           dbzxEqMoveUseOrgItem.setStartDate(zxEqMoveUseOrgItem.getStartDate());
           // 共通
           dbzxEqMoveUseOrgItem.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveUseOrgItemMapper.updateByPrimaryKey(dbzxEqMoveUseOrgItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqMoveUseOrgItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqMoveUseOrgItem(List<ZxEqMoveUseOrgItem> zxEqMoveUseOrgItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqMoveUseOrgItemList != null && zxEqMoveUseOrgItemList.size() > 0) {
           ZxEqMoveUseOrgItem zxEqMoveUseOrgItem = new ZxEqMoveUseOrgItem();
           zxEqMoveUseOrgItem.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveUseOrgItemMapper.batchDeleteUpdateZxEqMoveUseOrgItem(zxEqMoveUseOrgItemList, zxEqMoveUseOrgItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqMoveUseOrgItemList);
        }
    }

	@Override
	public ResponseEntity getZxEqMoveUseOrgItemListForTab(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
		if (zxEqMoveUseOrgItem == null) {
			zxEqMoveUseOrgItem = new ZxEqMoveUseOrgItem();
		}
		
		if(StrUtil.isEmpty(zxEqMoveUseOrgItem.getEquipID())) {
			return repEntity.layerMessage("no", "设备id必传！");
		}
		
		// 分页查询
		PageHelper.startPage(zxEqMoveUseOrgItem.getPage(),zxEqMoveUseOrgItem.getLimit());
		// 获取数据
		List<ZxEqMoveUseOrgItem> zxEqMoveUseOrgItemList = zxEqMoveUseOrgItemMapper.getZxEqMoveUseOrgItemListForTab(zxEqMoveUseOrgItem);
		// 得到分页信息
		PageInfo<ZxEqMoveUseOrgItem> p = new PageInfo<>(zxEqMoveUseOrgItemList);

		return repEntity.okList(zxEqMoveUseOrgItemList, p.getTotal());
	}

	@Override
	public List<ZxEqMoveUseOrgItem> ureportZxEqMoveUseOrgItemList(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
		if (zxEqMoveUseOrgItem == null) {
			zxEqMoveUseOrgItem = new ZxEqMoveUseOrgItem();
		}
		List<ZxEqMoveUseOrgItem> zxEqMoveUseOrgItemList = zxEqMoveUseOrgItemMapper.ureportZxEqMoveUseOrgItemList(zxEqMoveUseOrgItem);
		return zxEqMoveUseOrgItemList;
	}

}
