package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzCash;

public interface ZjTzCashMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzCash record);

    int insertSelective(ZjTzCash record);

    ZjTzCash selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzCash record);

    int updateByPrimaryKey(ZjTzCash record);

    List<ZjTzCash> selectByZjTzCashList(ZjTzCash record);

    int batchDeleteUpdateZjTzCash(List<ZjTzCash> recordList, ZjTzCash record);

}

