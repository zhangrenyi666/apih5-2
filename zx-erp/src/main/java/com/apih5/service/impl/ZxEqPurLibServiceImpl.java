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
import com.apih5.mybatis.dao.ZxEqPurLibMapper;
import com.apih5.mybatis.pojo.ZxEqPurLib;
import com.apih5.service.ZxEqPurLibService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqPurLibService")
public class ZxEqPurLibServiceImpl implements ZxEqPurLibService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqPurLibMapper zxEqPurLibMapper;

    @Override
    public ResponseEntity getZxEqPurLibListByCondition(ZxEqPurLib zxEqPurLib) {
        if (zxEqPurLib == null) {
            zxEqPurLib = new ZxEqPurLib();
        }
        // 分页查询
        PageHelper.startPage(zxEqPurLib.getPage(),zxEqPurLib.getLimit());
        // 获取数据
        List<ZxEqPurLib> zxEqPurLibList = zxEqPurLibMapper.selectByZxEqPurLibList(zxEqPurLib);
        // 得到分页信息
        PageInfo<ZxEqPurLib> p = new PageInfo<>(zxEqPurLibList);

        return repEntity.okList(zxEqPurLibList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqPurLibDetails(ZxEqPurLib zxEqPurLib) {
        if (zxEqPurLib == null) {
            zxEqPurLib = new ZxEqPurLib();
        }
        // 获取数据
        ZxEqPurLib dbZxEqPurLib = zxEqPurLibMapper.selectByPrimaryKey(zxEqPurLib.getId());
        // 数据存在
        if (dbZxEqPurLib != null) {
            return repEntity.ok(dbZxEqPurLib);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqPurLib(ZxEqPurLib zxEqPurLib) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqPurLib.setId(UuidUtil.generate());
        zxEqPurLib.setCreateUserInfo(userKey, realName);
        int flag = zxEqPurLibMapper.insert(zxEqPurLib);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqPurLib);
        }
    }

    @Override
    public ResponseEntity updateZxEqPurLib(ZxEqPurLib zxEqPurLib) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqPurLib dbzxEqPurLib = zxEqPurLibMapper.selectByPrimaryKey(zxEqPurLib.getId());
        if (dbzxEqPurLib != null && StrUtil.isNotEmpty(dbzxEqPurLib.getId())) {
           // 主表id
           dbzxEqPurLib.setMasID(zxEqPurLib.getMasID());
           // 随机技术资料名称
           dbzxEqPurLib.setName(zxEqPurLib.getName());
           // 数量
           dbzxEqPurLib.setQty(zxEqPurLib.getQty());
           // 说明
           dbzxEqPurLib.setRemark(zxEqPurLib.getRemark());
           // 编制时间
           dbzxEqPurLib.setEditTime(zxEqPurLib.getEditTime());
           // 共通
           dbzxEqPurLib.setModifyUserInfo(userKey, realName);
           flag = zxEqPurLibMapper.updateByPrimaryKey(dbzxEqPurLib);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqPurLib);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqPurLib(List<ZxEqPurLib> zxEqPurLibList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqPurLibList != null && zxEqPurLibList.size() > 0) {
           ZxEqPurLib zxEqPurLib = new ZxEqPurLib();
           zxEqPurLib.setModifyUserInfo(userKey, realName);
           flag = zxEqPurLibMapper.batchDeleteUpdateZxEqPurLib(zxEqPurLibList, zxEqPurLib);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqPurLibList);
        }
    }
}
