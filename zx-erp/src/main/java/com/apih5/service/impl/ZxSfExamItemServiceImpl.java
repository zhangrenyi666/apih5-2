package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxSfJuAccessRoomMapper;
import com.apih5.mybatis.pojo.ZxSfJuAccessRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSfExamItemMapper;
import com.apih5.mybatis.pojo.ZxSfAccessRoom;
import com.apih5.mybatis.pojo.ZxSfExamItem;
import com.apih5.service.ZxSfAccessRoomService;
import com.apih5.service.ZxSfExamItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfExamItemService")
public class ZxSfExamItemServiceImpl implements ZxSfExamItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfExamItemMapper zxSfExamItemMapper;

    @Autowired(required = true)
    private ZxSfAccessRoomService zxSfAccessRoomService;

    @Override
    public ResponseEntity getZxSfExamItemListByCondition(ZxSfExamItem zxSfExamItem) {
        if (zxSfExamItem == null) {
            zxSfExamItem = new ZxSfExamItem();
        }
        // 分页查询
        // PageHelper.startPage(zxSfExamItem.getPage(), zxSfExamItem.getLimit());
        // 获取数据
        List<ZxSfExamItem> zxSfExamItemList = zxSfExamItemMapper.selectByZxSfExamItemList(zxSfExamItem);

        // 得到分页信息
        // PageInfo<ZxSfExamItem> p = new PageInfo<>(zxSfExamItemList);

        return repEntity.ok(zxSfExamItemList);
    }

    @Override
    public ResponseEntity getZxSfExamItemDetail(ZxSfExamItem zxSfExamItem) {
        if (zxSfExamItem == null) {
            zxSfExamItem = new ZxSfExamItem();
        }
        // 获取数据
        ZxSfExamItem dbZxSfExamItem = zxSfExamItemMapper.selectByPrimaryKey(zxSfExamItem.getZxSfExamItemId());
        // 数据存在
        if (dbZxSfExamItem != null) {
            return repEntity.ok(dbZxSfExamItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfExamItem(ZxSfExamItem zxSfExamItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfExamItem.setZxSfExamItemId(UuidUtil.generate());
        zxSfExamItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfExamItemMapper.insert(zxSfExamItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfExamItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfExamItem(ZxSfExamItem zxSfExamItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfExamItem dbZxSfExamItem = zxSfExamItemMapper.selectByPrimaryKey(zxSfExamItem.getZxSfExamItemId());
        if (dbZxSfExamItem != null && StrUtil.isNotEmpty(dbZxSfExamItem.getZxSfExamItemId())) {
            // 自评
            dbZxSfExamItem.setSelfScore(zxSfExamItem.getSelfScore());
            // 考评
            dbZxSfExamItem.setExamScore(zxSfExamItem.getExamScore());
            // 共通
            dbZxSfExamItem.setModifyUserInfo(userKey, realName);
            flag = zxSfExamItemMapper.updateByPrimaryKey(dbZxSfExamItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfExamItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfExamItem(List<ZxSfExamItem> zxSfExamItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfExamItemList != null && zxSfExamItemList.size() > 0) {
            ZxSfExamItem zxSfExamItem = new ZxSfExamItem();
            zxSfExamItem.setModifyUserInfo(userKey, realName);
            flag = zxSfExamItemMapper.batchDeleteUpdateZxSfExamItem(zxSfExamItemList, zxSfExamItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfExamItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZxSfExamItemInit(ZxSfExamItem zxSfExamItem) {
        if (zxSfExamItem == null) {
            zxSfExamItem = new ZxSfExamItem();
        }
        List<ZxSfAccessRoom> zxSfAccessRoomList = zxSfAccessRoomService.getZxSfAccessRoomInit();
        for (int i = 0; i <= zxSfAccessRoomList.size(); i++) {
            ZxSfAccessRoom a = zxSfAccessRoomList.get(i);
            zxSfExamItem.setExamName(a.getExamName());
            zxSfExamItem.setExamContext(a.getExamContext());
            zxSfExamItem.setAllScore(a.getAllScore());
            zxSfExamItem.setZxSfExamItemId(UuidUtil.generate());
            zxSfExamItemMapper.insertInit(zxSfExamItem);
        }
        // 分页查询
        PageHelper.startPage(zxSfExamItem.getPage(), zxSfExamItem.getLimit());
        // 获取数据
        List<ZxSfExamItem> zxSfExamItemList = zxSfExamItemMapper.selectByZxSfExamItemList(zxSfExamItem);
        // 得到分页信息
        PageInfo<ZxSfExamItem> p = new PageInfo<>(zxSfExamItemList);

        return repEntity.okList(zxSfExamItemList, p.getTotal());
    }

}
