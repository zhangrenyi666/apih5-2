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
import com.apih5.mybatis.dao.ZxSfJuExamItemMapper;
import com.apih5.mybatis.pojo.ZxSfAccessRoom;
import com.apih5.mybatis.pojo.ZxSfExamItem;
import com.apih5.mybatis.pojo.ZxSfJuExamItem;
import com.apih5.service.ZxSfAccessRoomService;
import com.apih5.service.ZxSfJuExamItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfJuExamItemService")
public class ZxSfJuExamItemServiceImpl implements ZxSfJuExamItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfJuExamItemMapper zxSfJuExamItemMapper;

    @Autowired(required = true)
    private ZxSfAccessRoomService zxSfAccessRoomService;

    @Override
    public ResponseEntity getZxSfJuExamItemListByCondition(ZxSfJuExamItem zxSfJuExamItem) {
        if (zxSfJuExamItem == null) {
            zxSfJuExamItem = new ZxSfJuExamItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfJuExamItem.getPage(), zxSfJuExamItem.getLimit());
        // 获取数据
        List<ZxSfJuExamItem> zxSfJuExamItemList = zxSfJuExamItemMapper.selectByZxSfJuExamItemList(zxSfJuExamItem);
        // 得到分页信息
        PageInfo<ZxSfJuExamItem> p = new PageInfo<>(zxSfJuExamItemList);

        return repEntity.okList(zxSfJuExamItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfJuExamItemDetail(ZxSfJuExamItem zxSfJuExamItem) {
        if (zxSfJuExamItem == null) {
            zxSfJuExamItem = new ZxSfJuExamItem();
        }
        // 获取数据
        ZxSfJuExamItem dbZxSfJuExamItem = zxSfJuExamItemMapper.selectByPrimaryKey(zxSfJuExamItem.getZxSfJuExamItemId());
        // 数据存在
        if (dbZxSfJuExamItem != null) {
            return repEntity.ok(dbZxSfJuExamItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfJuExamItem(ZxSfJuExamItem zxSfJuExamItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfJuExamItem.setZxSfJuExamItemId(UuidUtil.generate());
        zxSfJuExamItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfJuExamItemMapper.insert(zxSfJuExamItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfJuExamItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfJuExamItem(ZxSfJuExamItem zxSfJuExamItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfJuExamItem dbZxSfJuExamItem = zxSfJuExamItemMapper.selectByPrimaryKey(zxSfJuExamItem.getZxSfJuExamItemId());
        if (dbZxSfJuExamItem != null && StrUtil.isNotEmpty(dbZxSfJuExamItem.getZxSfJuExamItemId())) {
            // 主表ID
            dbZxSfJuExamItem.setExamID(zxSfJuExamItem.getExamID());
            // 考核项目
            dbZxSfJuExamItem.setExamName(zxSfJuExamItem.getExamName());
            // 满分
            dbZxSfJuExamItem.setAllScore(zxSfJuExamItem.getAllScore());
            // 自评
            dbZxSfJuExamItem.setSelfScore(zxSfJuExamItem.getSelfScore());
            // 考评
            dbZxSfJuExamItem.setExamScore(zxSfJuExamItem.getExamScore());
            // 备注
            dbZxSfJuExamItem.setNotes(zxSfJuExamItem.getNotes());
            // 编辑时间
            dbZxSfJuExamItem.setEditTime(zxSfJuExamItem.getEditTime());
            // 备注
            dbZxSfJuExamItem.setRemarks(zxSfJuExamItem.getRemarks());
            // 排序
            dbZxSfJuExamItem.setSort(zxSfJuExamItem.getSort());
            // 共通
            dbZxSfJuExamItem.setModifyUserInfo(userKey, realName);
            flag = zxSfJuExamItemMapper.updateByPrimaryKey(dbZxSfJuExamItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfJuExamItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfJuExamItem(List<ZxSfJuExamItem> zxSfJuExamItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfJuExamItemList != null && zxSfJuExamItemList.size() > 0) {
            ZxSfJuExamItem zxSfJuExamItem = new ZxSfJuExamItem();
            zxSfJuExamItem.setModifyUserInfo(userKey, realName);
            flag = zxSfJuExamItemMapper.batchDeleteUpdateZxSfJuExamItem(zxSfJuExamItemList, zxSfJuExamItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfJuExamItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZxSfJuExamItemInit(ZxSfJuExamItem zxSfJuExamItem) {
        if (zxSfJuExamItem == null) {
            zxSfJuExamItem = new ZxSfJuExamItem();
        }
        List<ZxSfAccessRoom> zxSfAccessRoomList = zxSfAccessRoomService.getZxSfAccessRoomInit();
        for (int i = 0; i <= zxSfAccessRoomList.size(); i++) {
            ZxSfAccessRoom a = zxSfAccessRoomList.get(i);
            zxSfJuExamItem.setExamName(a.getExamName());
            zxSfJuExamItem.setExamContext(a.getExamContext());
            zxSfJuExamItem.setAllScore(a.getAllScore());
            zxSfJuExamItem.setZxSfJuExamItemId(UuidUtil.generate());
            zxSfJuExamItemMapper.insertInit(zxSfJuExamItem);
        }
        // 分页查询
        PageHelper.startPage(zxSfJuExamItem.getPage(), zxSfJuExamItem.getLimit());
        // 获取数据
        List<ZxSfJuExamItem> zxSfJuExamItemList = zxSfJuExamItemMapper.selectByZxSfJuExamItemList(zxSfJuExamItem);
        // 得到分页信息
        PageInfo<ZxSfJuExamItem> p = new PageInfo<>(zxSfJuExamItemList);

        return repEntity.okList(zxSfJuExamItemList, p.getTotal());
    }
}
