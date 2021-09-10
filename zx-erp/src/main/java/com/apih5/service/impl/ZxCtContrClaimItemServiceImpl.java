package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContrClaimItemMapper;
import com.apih5.mybatis.pojo.ZxCtContrClaimItem;
import com.apih5.service.ZxCtContrClaimItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtContrClaimItemService")
public class ZxCtContrClaimItemServiceImpl implements ZxCtContrClaimItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrClaimItemMapper zxCtContrClaimItemMapper;

    @Override
    public ResponseEntity getZxCtContrClaimItemListByCondition(ZxCtContrClaimItem zxCtContrClaimItem) {
        if (zxCtContrClaimItem == null) {
            zxCtContrClaimItem = new ZxCtContrClaimItem();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrClaimItem.getPage(),zxCtContrClaimItem.getLimit());
        // 获取数据
        List<ZxCtContrClaimItem> zxCtContrClaimItemList = zxCtContrClaimItemMapper.selectByZxCtContrClaimItemList(zxCtContrClaimItem);
        // 得到分页信息
        PageInfo<ZxCtContrClaimItem> p = new PageInfo<>(zxCtContrClaimItemList);

        return repEntity.okList(zxCtContrClaimItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrClaimItemDetail(ZxCtContrClaimItem zxCtContrClaimItem) {
        if (zxCtContrClaimItem == null) {
            zxCtContrClaimItem = new ZxCtContrClaimItem();
        }
        // 获取数据
        ZxCtContrClaimItem dbZxCtContrClaimItem = zxCtContrClaimItemMapper.selectByPrimaryKey(zxCtContrClaimItem.getId());
        // 数据存在
        if (dbZxCtContrClaimItem != null) {
            return repEntity.ok(dbZxCtContrClaimItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrClaimItem.setId(UuidUtil.generate());
        zxCtContrClaimItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrClaimItemMapper.insert(zxCtContrClaimItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrClaimItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrClaimItem dbZxCtContrClaimItem = zxCtContrClaimItemMapper.selectByPrimaryKey(zxCtContrClaimItem.getId());
        if (dbZxCtContrClaimItem != null && StrUtil.isNotEmpty(dbZxCtContrClaimItem.getId())) {
           // 索赔主表ID
           dbZxCtContrClaimItem.setClaimID(zxCtContrClaimItem.getClaimID());
           // 索赔明细编号
           dbZxCtContrClaimItem.setClaimDetailNo(zxCtContrClaimItem.getClaimDetailNo());
           // 索赔明细名称
           dbZxCtContrClaimItem.setClaimDetailName(zxCtContrClaimItem.getClaimDetailName());
           // 索赔明细内容
           dbZxCtContrClaimItem.setClaimDetailContent(zxCtContrClaimItem.getClaimDetailContent());
           // 备注
           dbZxCtContrClaimItem.setRemarks(zxCtContrClaimItem.getRemarks());
           // 排序
           dbZxCtContrClaimItem.setSort(zxCtContrClaimItem.getSort());
           // 共通
           dbZxCtContrClaimItem.setModifyUserInfo(userKey, realName);
           flag = zxCtContrClaimItemMapper.updateByPrimaryKey(dbZxCtContrClaimItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrClaimItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrClaimItem(List<ZxCtContrClaimItem> zxCtContrClaimItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrClaimItemList != null && zxCtContrClaimItemList.size() > 0) {
           ZxCtContrClaimItem zxCtContrClaimItem = new ZxCtContrClaimItem();
           zxCtContrClaimItem.setModifyUserInfo(userKey, realName);
           flag = zxCtContrClaimItemMapper.batchDeleteUpdateZxCtContrClaimItem(zxCtContrClaimItemList, zxCtContrClaimItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContrClaimItemList);
        }
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity delAllZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrClaimItem != null && StrUtil.isNotEmpty(zxCtContrClaimItem.getClaimID())) {
           zxCtContrClaimItem.setModifyUserInfo(userKey, realName);
           flag = zxCtContrClaimItemMapper.delAllZxCtContrClaimItem(zxCtContrClaimItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("删除成功！");
        }
	}
    
}
