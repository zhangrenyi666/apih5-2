package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysRoleUser;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<SysRoleUser> selectBySysRoleUserList(SysRoleUser record);

    int batchDeleteUpdateSysRoleUser(List<SysRoleUser> recordList);

}

