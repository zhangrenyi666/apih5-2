package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSecurityManagementSystem;

public interface ZjTzSecurityManagementSystemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSecurityManagementSystem record);

    int insertSelective(ZjTzSecurityManagementSystem record);

    ZjTzSecurityManagementSystem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSecurityManagementSystem record);

    int updateByPrimaryKey(ZjTzSecurityManagementSystem record);

    List<ZjTzSecurityManagementSystem> selectByZjTzSecurityManagementSystemList(ZjTzSecurityManagementSystem record);

    int batchDeleteUpdateZjTzSecurityManagementSystem(List<ZjTzSecurityManagementSystem> recordList, ZjTzSecurityManagementSystem record);

}

