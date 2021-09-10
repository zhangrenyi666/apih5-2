package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysSyncUserInfo;

public interface SysSyncUserInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysSyncUserInfo record);

    int insertSelective(SysSyncUserInfo record);

    SysSyncUserInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysSyncUserInfo record);

    int updateByPrimaryKey(SysSyncUserInfo record);

    List<SysSyncUserInfo> selectBySysSyncUserInfoList(SysSyncUserInfo record);

    int batchDeleteUpdateSysSyncUserInfo(List<SysSyncUserInfo> recordList, SysSyncUserInfo record);

}

