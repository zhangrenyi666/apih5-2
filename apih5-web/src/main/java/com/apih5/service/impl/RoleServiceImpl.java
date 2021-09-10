package com.apih5.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apih5.mybatis.dao.RoleMapper;
import com.apih5.mybatis.pojo.SysRole;
import com.apih5.mybatis.pojo.SysRoleMenu;
import com.apih5.service.RoleService;
import com.google.common.collect.Lists;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<SysRole> listAll(SysRole sysRole) {
        return roleMapper.listAll(sysRole);
    }

    @Override
    public int getUserReferencesCountByRoleId(String roleId) {
        return roleMapper.getUserReferencesCountByRoleId(roleId);
    }

    @Override
    public void delete(String roleId) {
        roleMapper.deleteRole(roleId);
        roleMapper.deleteRoleMenuMapping(roleId);
    }

    @Override
    public List<SysRole> getRoleByUserId(int userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    public int checkRoleNameExists(SysRole sysRole) {
        return roleMapper.checkRoleNameExists(sysRole);
    }

    @Override
    public void addRole(SysRole sysRole) {
        // 首先向角色表中新增一条数据
        roleMapper.roleAdd(sysRole);
        // 得到用户选择的角色对应的菜单
        String roleMenuStr = sysRole.getRoleMenuStr();
        // 如果角色对应的菜单不为空
        if(!StringUtils.isEmpty(roleMenuStr)) {
            List<SysRoleMenu> sysRoleMenus = Lists.newArrayList();
            String[] split = roleMenuStr.split(",");
            for (String s : split) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getRoleId());
//                sysRoleMenu.setMenuId(Integer.parseInt(s));
                sysRoleMenus.add(sysRoleMenu);
            }
            roleMapper.addSysRoleMenu(sysRoleMenus);
        }
    }

    @Override
    public SysRole getRoleByRoleId(int roleId) {
        return roleMapper.getRoleByRoleId(roleId);
    }

    @Override
    public List<Integer> fetchAllMenuId(int roleId) {
        return roleMapper.fetchAllMenuId(roleId);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        // 首先更新角色表
        roleMapper.roleUpdate(sysRole);
        // 然后将角色菜单表中数据根据角色id清空
        String roleId = sysRole.getRoleId();
        roleMapper.deleteRoleMenuMapping(roleId);
        // 最后如果用户选择了角色对应的菜单，向角色菜单表中增加数据
        String roleMenuStr = sysRole.getRoleMenuStr();
        // 如果角色对应的菜单不为空
        if(!StringUtils.isEmpty(roleMenuStr)) {
            List<SysRoleMenu> sysRoleMenus = Lists.newArrayList();
            String[] split = roleMenuStr.split(",");
            for (String s : split) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
//                sysRoleMenu.setMenuId(Integer.parseInt(s));
                sysRoleMenus.add(sysRoleMenu);
            }
            roleMapper.addSysRoleMenu(sysRoleMenus);
        }
    }

    @Override
    public int checkRoleExists(SysRole sysRole) {
        return roleMapper.checkRoleExists(sysRole);
    }
}
