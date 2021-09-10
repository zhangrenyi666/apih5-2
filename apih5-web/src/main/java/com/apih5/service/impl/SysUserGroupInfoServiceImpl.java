package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUserGroupInfo;
import com.apih5.framework.api.sysdb.service.SysUserGroupInfoService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysUserGroupInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysUserGroupInfoService")
public class SysUserGroupInfoServiceImpl implements SysUserGroupInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysUserGroupInfoMapper sysUserGroupInfoMapper;

    @Override
    public ResponseEntity getSysUserGroupInfoListByCondition(SysUserGroupInfo sysUserGroupInfo) {
        if (sysUserGroupInfo == null) {
            sysUserGroupInfo = new SysUserGroupInfo();
        }
        // 分页查询
        PageHelper.startPage(sysUserGroupInfo.getPage(),sysUserGroupInfo.getLimit());
        // 获取数据
        List<SysUserGroupInfo> sysUserGroupInfoList = sysUserGroupInfoMapper.selectBySysUserGroupInfoList(sysUserGroupInfo);
        // 得到分页信息
        PageInfo<SysUserGroupInfo> p = new PageInfo<>(sysUserGroupInfoList);

        return repEntity.okList(sysUserGroupInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysUserGroupInfoDetails(SysUserGroupInfo sysUserGroupInfo) {
        if (sysUserGroupInfo == null) {
            sysUserGroupInfo = new SysUserGroupInfo();
        }
        // 获取数据
        SysUserGroupInfo dbSysUserGroupInfo = sysUserGroupInfoMapper.selectByPrimaryKey(sysUserGroupInfo.getGroupInfoId());
        // 数据存在
        if (dbSysUserGroupInfo != null) {
            return repEntity.ok(dbSysUserGroupInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveSysUserGroupInfo(SysUserGroupInfo sysUserGroupInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysUserGroupInfo.setGroupInfoId(UuidUtil.generate());
        sysUserGroupInfo.setCreateUserInfo(userKey, realName);
        int flag = sysUserGroupInfoMapper.insert(sysUserGroupInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysUserGroupInfo);
        }
    }

    @Override
    public ResponseEntity updateSysUserGroupInfo(SysUserGroupInfo sysUserGroupInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysUserGroupInfo dbsysUserGroupInfo = sysUserGroupInfoMapper.selectByPrimaryKey(sysUserGroupInfo.getGroupInfoId());
        if (dbsysUserGroupInfo != null && StrUtil.isNotEmpty(dbsysUserGroupInfo.getGroupInfoId())) {
           // 用户组ID
           dbsysUserGroupInfo.setGroupId(sysUserGroupInfo.getGroupId());
           // 用户组名称
           dbsysUserGroupInfo.setGroupName(sysUserGroupInfo.getGroupName());
           // 用户key
           dbsysUserGroupInfo.setValue(sysUserGroupInfo.getValue());
           // 用户id
           dbsysUserGroupInfo.setUserId(sysUserGroupInfo.getUserId());
           // 真实姓名
           dbsysUserGroupInfo.setLabel(sysUserGroupInfo.getLabel());
           // 电话
           dbsysUserGroupInfo.setMobile(sysUserGroupInfo.getMobile());
           // 备注
           dbsysUserGroupInfo.setRemarks(sysUserGroupInfo.getRemarks());
           // 共通
           dbsysUserGroupInfo.setModifyUserInfo(userKey, realName);
           flag = sysUserGroupInfoMapper.updateByPrimaryKey(dbsysUserGroupInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysUserGroupInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysUserGroupInfo(List<SysUserGroupInfo> sysUserGroupInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysUserGroupInfoList != null && sysUserGroupInfoList.size() > 0) {
           SysUserGroupInfo sysUserGroupInfo = new SysUserGroupInfo();
           sysUserGroupInfo.setModifyUserInfo(userKey, realName);
           flag = sysUserGroupInfoMapper.batchDeleteUpdateSysUserGroupInfo(sysUserGroupInfoList, sysUserGroupInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysUserGroupInfoList);
        }
    }
}
