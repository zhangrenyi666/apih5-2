package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzQuality;

public interface ZjTzQualityMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzQuality record);

    int insertSelective(ZjTzQuality record);

    ZjTzQuality selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzQuality record);

    int updateByPrimaryKey(ZjTzQuality record);

    List<ZjTzQuality> selectByZjTzQualityList(ZjTzQuality record);

    int batchDeleteUpdateZjTzQuality(List<ZjTzQuality> recordList, ZjTzQuality record);

}

