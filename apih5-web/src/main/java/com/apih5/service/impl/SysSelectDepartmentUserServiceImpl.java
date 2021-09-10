package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysSelectDepartmentUser;
import com.apih5.framework.api.sysdb.service.SysSelectDepartmentUserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysSelectDepartmentUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysSelectDepartmentUserService")
public class SysSelectDepartmentUserServiceImpl implements SysSelectDepartmentUserService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysSelectDepartmentUserMapper sysSelectDepartmentUserMapper;

    @Override
    public ResponseEntity getSysSelectDepartmentUserListByCondition(SysSelectDepartmentUser sysSelectDepartmentUser) {
        if (sysSelectDepartmentUser == null) {
            sysSelectDepartmentUser = new SysSelectDepartmentUser();
        }
        // 分页查询
        PageHelper.startPage(sysSelectDepartmentUser.getPage(),sysSelectDepartmentUser.getLimit());
        // 获取数据
        List<SysSelectDepartmentUser> sysSelectDepartmentUserList = sysSelectDepartmentUserMapper.selectBySysSelectDepartmentUserList(sysSelectDepartmentUser);
        // 得到分页信息
        PageInfo<SysSelectDepartmentUser> p = new PageInfo<>(sysSelectDepartmentUserList);

        return repEntity.okList(sysSelectDepartmentUserList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysSelectDepartmentUser(SysSelectDepartmentUser sysSelectDepartmentUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysSelectDepartmentUser.setSelectId(UuidUtil.generate());
        sysSelectDepartmentUser.setCreateUserInfo(userKey, realName);
        int flag = sysSelectDepartmentUserMapper.insert(sysSelectDepartmentUser);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysSelectDepartmentUser);
        }
    }

    @Override
    public ResponseEntity updateSysSelectDepartmentUser(SysSelectDepartmentUser sysSelectDepartmentUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysSelectDepartmentUser dbsysSelectDepartmentUser = sysSelectDepartmentUserMapper.selectByPrimaryKey(sysSelectDepartmentUser.getSelectId());
        if (dbsysSelectDepartmentUser != null && StrUtil.isNotEmpty(dbsysSelectDepartmentUser.getSelectId())) {
           // 共通
           dbsysSelectDepartmentUser.setModifyUserInfo(userKey, realName);
           flag = sysSelectDepartmentUserMapper.updateByPrimaryKey(dbsysSelectDepartmentUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysSelectDepartmentUser);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysSelectDepartmentUser(List<SysSelectDepartmentUser> sysSelectDepartmentUserList) {
        int flag = 0;
        if (sysSelectDepartmentUserList != null && sysSelectDepartmentUserList.size() > 0) {
           flag = sysSelectDepartmentUserMapper.batchDeleteUpdateSysSelectDepartmentUser(sysSelectDepartmentUserList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysSelectDepartmentUserList);
        }
   }
}
