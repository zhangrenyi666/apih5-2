package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;

public interface ZjTzProSubprojectInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProSubprojectInfo record);

    int insertSelective(ZjTzProSubprojectInfo record);

    ZjTzProSubprojectInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProSubprojectInfo record);

    int updateByPrimaryKey(ZjTzProSubprojectInfo record);

    List<ZjTzProSubprojectInfo> selectByZjTzProSubprojectInfoList(ZjTzProSubprojectInfo record);

    int batchDeleteUpdateZjTzProSubprojectInfo(List<ZjTzProSubprojectInfo> recordList, ZjTzProSubprojectInfo record);

}

