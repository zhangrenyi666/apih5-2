package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzOtherManagementSystem;

public interface ZjTzOtherManagementSystemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzOtherManagementSystem record);

    int insertSelective(ZjTzOtherManagementSystem record);

    ZjTzOtherManagementSystem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzOtherManagementSystem record);

    int updateByPrimaryKey(ZjTzOtherManagementSystem record);

    List<ZjTzOtherManagementSystem> selectByZjTzOtherManagementSystemList(ZjTzOtherManagementSystem record);

    int batchDeleteUpdateZjTzOtherManagementSystem(List<ZjTzOtherManagementSystem> recordList, ZjTzOtherManagementSystem record);

}

