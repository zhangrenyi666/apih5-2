package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectSet;

public interface ZxSaProjectSetMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectSet record);

    int insertSelective(ZxSaProjectSet record);

    ZxSaProjectSet selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectSet record);

    int updateByPrimaryKey(ZxSaProjectSet record);

    List<ZxSaProjectSet> selectByZxSaProjectSetList(ZxSaProjectSet record);

    int batchDeleteUpdateZxSaProjectSet(List<ZxSaProjectSet> recordList, ZxSaProjectSet record);

}

