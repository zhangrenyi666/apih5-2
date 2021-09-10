package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.apih5.mybatis.pojo.SysRoleMenu;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);

    List<SysRoleMenu> selectBySysRoleMenuList(SysRoleMenu record);

    int batchDeleteUpdateSysRoleMenu(List<SysRoleMenu> recordList);

    List<String> selectBySysRoleMenuIdList(Map<String, Object> map);
//    List<String> selectBySysRoleMenuIdList(List<String> recordList, SysRoleMenu record);
}

