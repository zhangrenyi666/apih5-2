package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProShareholderInfo;

public interface ZjTzProShareholderInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProShareholderInfo record);

    int insertSelective(ZjTzProShareholderInfo record);

    ZjTzProShareholderInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProShareholderInfo record);

    int updateByPrimaryKey(ZjTzProShareholderInfo record);

    List<ZjTzProShareholderInfo> selectByZjTzProShareholderInfoList(ZjTzProShareholderInfo record);

    int batchDeleteUpdateZjTzProShareholderInfo(List<ZjTzProShareholderInfo> recordList, ZjTzProShareholderInfo record);

}

