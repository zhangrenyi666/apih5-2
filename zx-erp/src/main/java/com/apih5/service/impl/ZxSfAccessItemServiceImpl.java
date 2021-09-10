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
import com.apih5.mybatis.dao.ZxSfAccessItemMapper;
import com.apih5.mybatis.pojo.ZxSfAccessItem;
import com.apih5.service.ZxSfAccessItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfAccessItemService")
public class ZxSfAccessItemServiceImpl implements ZxSfAccessItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfAccessItemMapper zxSfAccessItemMapper;

    @Override
    public ResponseEntity getZxSfAccessItemListByCondition(ZxSfAccessItem zxSfAccessItem) {
        if (zxSfAccessItem == null) {
            zxSfAccessItem = new ZxSfAccessItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfAccessItem.getPage(),zxSfAccessItem.getLimit());
        // 获取数据
        List<ZxSfAccessItem> zxSfAccessItemList = zxSfAccessItemMapper.selectByZxSfAccessItemList(zxSfAccessItem);
        // 得到分页信息
        PageInfo<ZxSfAccessItem> p = new PageInfo<>(zxSfAccessItemList);

        return repEntity.okList(zxSfAccessItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfAccessItemDetail(ZxSfAccessItem zxSfAccessItem) {
        if (zxSfAccessItem == null) {
            zxSfAccessItem = new ZxSfAccessItem();
        }
        // 获取数据
        ZxSfAccessItem dbZxSfAccessItem = zxSfAccessItemMapper.selectByPrimaryKey(zxSfAccessItem.getZxSfAccessItemId());
        // 数据存在
        if (dbZxSfAccessItem != null) {
            return repEntity.ok(dbZxSfAccessItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfAccessItem(ZxSfAccessItem zxSfAccessItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfAccessItem.setZxSfAccessItemId(UuidUtil.generate());
        zxSfAccessItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfAccessItemMapper.insert(zxSfAccessItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfAccessItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfAccessItem(ZxSfAccessItem zxSfAccessItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccessItem dbZxSfAccessItem = zxSfAccessItemMapper.selectByPrimaryKey(zxSfAccessItem.getZxSfAccessItemId());
        if (dbZxSfAccessItem != null && StrUtil.isNotEmpty(dbZxSfAccessItem.getZxSfAccessItemId())) {
           // 主表ID
           dbZxSfAccessItem.setAccessID(zxSfAccessItem.getAccessID());
           // 日期
           dbZxSfAccessItem.setBizDate(zxSfAccessItem.getBizDate());
           // 安全状况记录
           dbZxSfAccessItem.setSafeRecord(zxSfAccessItem.getSafeRecord());
           // 违章记录
           dbZxSfAccessItem.setWrongRecord(zxSfAccessItem.getWrongRecord());
           // 整改情况
           dbZxSfAccessItem.setChangeInfo(zxSfAccessItem.getChangeInfo());
           // 考核评价
           dbZxSfAccessItem.setExamInfo(zxSfAccessItem.getExamInfo());
           // 编辑时间
           dbZxSfAccessItem.setEditTime(zxSfAccessItem.getEditTime());
           // 备注
           dbZxSfAccessItem.setRemarks(zxSfAccessItem.getRemarks());
           // 排序
           dbZxSfAccessItem.setSort(zxSfAccessItem.getSort());
           // 共通
           dbZxSfAccessItem.setModifyUserInfo(userKey, realName);
           flag = zxSfAccessItemMapper.updateByPrimaryKey(dbZxSfAccessItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccessItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfAccessItem(List<ZxSfAccessItem> zxSfAccessItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfAccessItemList != null && zxSfAccessItemList.size() > 0) {
           ZxSfAccessItem zxSfAccessItem = new ZxSfAccessItem();
           zxSfAccessItem.setModifyUserInfo(userKey, realName);
           flag = zxSfAccessItemMapper.batchDeleteUpdateZxSfAccessItem(zxSfAccessItemList, zxSfAccessItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfAccessItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
