package com.apih5.service;

import java.util.List;

import com.apih5.mybatis.pojo.SysRole;

/**
 * 角色Service
 */
public interface RoleService {

    /**
     * 根据条件查找系统角色
     * @param sysRole
     * @return
     */
    List<SysRole> listAll(SysRole sysRole);

    /**
     * 查看引用该角色的用户数量
     * @return
     */
    int getUserReferencesCountByRoleId(String roleId);

    /**
     * 删除角色
     * @param roleId
     */
    void delete(String roleId);

    /**
     * 根据用户Id查看角色
     * @param userId
     * @return
     */
    List<SysRole> getRoleByUserId(int userId);

    /**
     * 查询角色名称是否存在
     * @param sysRole
     * @return
     */
    int checkRoleNameExists(SysRole sysRole);

    /**
     * 新增角色
     * @param sysRole
     */
    void addRole(SysRole sysRole);

    /**
     * 根据角色Id查询角色
     * @param roleId
     * @return
     */
    SysRole getRoleByRoleId(int roleId);

    /**
     * 根据角色ID获得对应的所有菜单ID
     * @return
     */
    List<Integer> fetchAllMenuId(int roleId);

    /**
     * 修改系统角色
     * @param sysRole
     */
    void updateRole(SysRole sysRole);

    /**
     * 判断角色是否存在
     * @param sysRole
     * @return
     */
    int checkRoleExists(SysRole sysRole);
}
