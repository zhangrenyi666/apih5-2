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
import com.apih5.mybatis.dao.ZxCrColResourceMapper;
import com.apih5.mybatis.pojo.ZxCrColResource;
import com.apih5.service.ZxCrColResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCrColResourceService")
public class ZxCrColResourceServiceImpl implements ZxCrColResourceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrColResourceMapper zxCrColResourceMapper;

    @Override
    public ResponseEntity getZxCrColResourceListByCondition(ZxCrColResource zxCrColResource) {
        if (zxCrColResource == null) {
            zxCrColResource = new ZxCrColResource();
        }
        // 分页查询
        PageHelper.startPage(zxCrColResource.getPage(),zxCrColResource.getLimit());
        // 获取数据
        List<ZxCrColResource> zxCrColResourceList = zxCrColResourceMapper.selectByZxCrColResourceList(zxCrColResource);
        // 得到分页信息
        PageInfo<ZxCrColResource> p = new PageInfo<>(zxCrColResourceList);

        return repEntity.okList(zxCrColResourceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrColResourceDetail(ZxCrColResource zxCrColResource) {
        if (zxCrColResource == null) {
            zxCrColResource = new ZxCrColResource();
        }
        // 获取数据
        ZxCrColResource dbZxCrColResource = zxCrColResourceMapper.selectByPrimaryKey(zxCrColResource.getId());
        // 数据存在
        if (dbZxCrColResource != null) {
            return repEntity.ok(dbZxCrColResource);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrColResource(ZxCrColResource zxCrColResource) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrColResource.setId(UuidUtil.generate());
        zxCrColResource.setCreateUserInfo(userKey, realName);
        int flag = zxCrColResourceMapper.insert(zxCrColResource);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrColResource);
        }
    }

    @Override
    public ResponseEntity updateZxCrColResource(ZxCrColResource zxCrColResource) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrColResource dbZxCrColResource = zxCrColResourceMapper.selectByPrimaryKey(zxCrColResource.getId());
        if (dbZxCrColResource != null && StrUtil.isNotEmpty(dbZxCrColResource.getId())) {
           // 资源分类ID
           dbZxCrColResource.setCategoryID(zxCrColResource.getCategoryID());
           // 分类代码
           dbZxCrColResource.setResCode(zxCrColResource.getResCode());
           // 分类名称
           dbZxCrColResource.setResName(zxCrColResource.getResName());
           // 说明
           dbZxCrColResource.setRemark(zxCrColResource.getRemark());
           // 启用标注
           dbZxCrColResource.setEnableFlag(zxCrColResource.getEnableFlag());
           // 资源类型
           dbZxCrColResource.setResStyle(zxCrColResource.getResStyle());
           // 是否局下达
           dbZxCrColResource.setIsGroup(zxCrColResource.getIsGroup());
           // 下达时间
           dbZxCrColResource.setSendTime(zxCrColResource.getSendTime());
           // 共通
           dbZxCrColResource.setModifyUserInfo(userKey, realName);
           flag = zxCrColResourceMapper.updateByPrimaryKey(dbZxCrColResource);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrColResource);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrColResource(List<ZxCrColResource> zxCrColResourceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrColResourceList != null && zxCrColResourceList.size() > 0) {
           ZxCrColResource zxCrColResource = new ZxCrColResource();
           zxCrColResource.setModifyUserInfo(userKey, realName);
           flag = zxCrColResourceMapper.batchDeleteUpdateZxCrColResource(zxCrColResourceList, zxCrColResource);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrColResourceList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
