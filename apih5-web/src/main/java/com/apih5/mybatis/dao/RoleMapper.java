package com.apih5.mybatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apih5.mybatis.pojo.SysRole;
import com.apih5.mybatis.pojo.SysRoleMenu;

/**
 * 角色mapper
 */
@Repository
public interface RoleMapper {

    /**
     * 根据条件查找系统角色
     * @param sysRole
     * @return
     */
    List<SysRole> listAll(SysRole sysRole);

    /**
     * 根据条件获得系统角色数量
     * @param sysRole
     * @return
     */
    int getRolesListCount(SysRole sysRole);

    /**
     * 查看引用该角色的用户数量
     * @return
     */
    int getUserReferencesCountByRoleId(String roleId);

    /**
     * 通过id删除角色信息
     * @param roleId
     */
    void deleteRole(String roleId);

    /**
     * 通过id删除角色菜单对应表中的信息
     * @param roleId
     */
    void deleteRoleMenuMapping(String roleId);

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
     * 添加系统角色
     * @param sysRole
     */
    void roleAdd(SysRole sysRole);

    /**
     * 添加系统角色菜单表
     * @param sysRoleMenus
     */
    void addSysRoleMenu(List<SysRoleMenu> sysRoleMenus);

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
    void roleUpdate(SysRole sysRole);

    /**
     * 判断角色是否存在
     * @param sysRole
     * @return
     */
    int checkRoleExists(SysRole sysRole);
}