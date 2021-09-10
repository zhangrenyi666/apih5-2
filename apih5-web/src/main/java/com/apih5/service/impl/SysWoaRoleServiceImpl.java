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
import com.apih5.mybatis.dao.SysWoaRoleMapper;
import com.apih5.mybatis.pojo.SysWoaRole;
import com.apih5.service.SysWoaRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysWoaRoleService")
public class SysWoaRoleServiceImpl implements SysWoaRoleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWoaRoleMapper sysWoaRoleMapper;

    @Override
    public ResponseEntity getSysWoaRoleListByCondition(SysWoaRole sysWoaRole) {
        if (sysWoaRole == null) {
            sysWoaRole = new SysWoaRole();
        }
        // 分页查询
        PageHelper.startPage(sysWoaRole.getPage(),sysWoaRole.getLimit());
        // 获取数据
        List<SysWoaRole> sysWoaRoleList = sysWoaRoleMapper.selectBySysWoaRoleList(sysWoaRole);
        // 得到分页信息
        PageInfo<SysWoaRole> p = new PageInfo<>(sysWoaRoleList);

        return repEntity.okList(sysWoaRoleList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysWoaRole(SysWoaRole sysWoaRole) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysWoaRole.setRoleId(UuidUtil.generate());
        sysWoaRole.setCreateUserInfo(userKey, realName);
        int flag = sysWoaRoleMapper.insert(sysWoaRole);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWoaRole);
        }
    }

    @Override
    public ResponseEntity updateSysWoaRole(SysWoaRole sysWoaRole) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWoaRole dbsysWoaRole = sysWoaRoleMapper.selectByPrimaryKey(sysWoaRole.getRoleId());
        if (dbsysWoaRole != null && StrUtil.isNotEmpty(dbsysWoaRole.getRoleId())) {
           // 权限名称
           dbsysWoaRole.setRoleName(sysWoaRole.getRoleName());
           // 用户list
           dbsysWoaRole.setUserKeys(sysWoaRole.getUserKeys());
           // 共通
           dbsysWoaRole.setModifyUserInfo(userKey, realName);
           flag = sysWoaRoleMapper.updateByPrimaryKey(dbsysWoaRole);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWoaRole);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWoaRole(List<SysWoaRole> sysWoaRoleList) {
        int flag = 0;
        if (sysWoaRoleList != null && sysWoaRoleList.size() > 0) {
           flag = sysWoaRoleMapper.batchDeleteUpdateSysWoaRole(sysWoaRoleList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWoaRoleList);
        }
    }
}
