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
import com.apih5.mybatis.dao.SysRoleUserMapper;
import com.apih5.mybatis.pojo.SysRoleUser;
import com.apih5.service.SysRoleUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.Hutool;
import cn.hutool.core.util.StrUtil;

@Service("sysRoleUserService")
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public ResponseEntity getSysRoleUserListByCondition(SysRoleUser sysRoleUser) {
        if (sysRoleUser == null) {
            sysRoleUser = new SysRoleUser();
        }
        // 分页查询
        PageHelper.startPage(sysRoleUser.getPage(),sysRoleUser.getLimit());
        // 获取数据
        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUser);
        // 得到分页信息
        PageInfo<SysRoleUser> p = new PageInfo<>(sysRoleUserList);

        return repEntity.okList(sysRoleUserList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysRoleUser(SysRoleUser sysRoleUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysRoleUser.setRoleUserId(UuidUtil.generate());
        sysRoleUser.setCreateUserInfo(userKey, realName);
        int flag = sysRoleUserMapper.insert(sysRoleUser);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysRoleUser);
        }
    }

    @Override
    public ResponseEntity updateSysRoleUser(SysRoleUser sysRoleUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
//        SysRoleUser dbsysRoleUser = sysRoleUserMapper.selectByPrimaryKey(sysRoleUser.getRoleUserId());
//        if (dbsysRoleUser != null && StrUtil.isNotEmpty(dbsysRoleUser.getRoleUserId())) {
//           // 共通
//           dbsysRoleUser.setModifyUserInfo(userKey, realName);
//           flag = sysRoleUserMapper.updateByPrimaryKey(dbsysRoleUser);
//        }
        String roleId = sysRoleUser.getRoleId();
        
        // 获取数据
        SysRoleUser sysRoleUserDb = new SysRoleUser();
        sysRoleUserDb.setRoleId(roleId);
        List<SysRoleUser> sysRoleUserDbList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUser);
        if(sysRoleUserDbList != null && sysRoleUserDbList.size() > 0) {
        	flag = sysRoleUserMapper.batchDeleteUpdateSysRoleUser(sysRoleUserDbList);
        }
        
        List<SysRoleUser> sysRoleUserList = sysRoleUser.getSysRoleUserList();
        for(SysRoleUser sysRoleUserPage:sysRoleUserList) {
        	sysRoleUserPage.setRoleUserId(UuidUtil.generate());
        	sysRoleUserPage.setRoleId(roleId);
        	sysRoleUserPage.setCreateUserInfo(userKey, realName);
            flag = sysRoleUserMapper.insert(sysRoleUserPage);
        }
        
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysRoleUser);
        }
        
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysRoleUser(List<SysRoleUser> sysRoleUserList) {
        int flag = 0;
        if (sysRoleUserList != null && sysRoleUserList.size() > 0) {
           flag = sysRoleUserMapper.batchDeleteUpdateSysRoleUser(sysRoleUserList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysRoleUserList);
        }
   }
    
//    @Override
//    public ResponseEntity getSysRoleUserTree(SysRoleUser sysRoleUser) {
//        if (sysRoleUser == null) {
//            sysRoleUser = new SysRoleUser();
//        }
//        // 分页查询
//        PageHelper.startPage(sysRoleUser.getPage(),sysRoleUser.getLimit());
//        // 获取数据
//        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUser);
//        // 得到分页信息
//        PageInfo<SysRoleUser> p = new PageInfo<>(sysRoleUserList);
//
//        return repEntity.okList(sysRoleUserList, p.getTotal());
//    }
}
