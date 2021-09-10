package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzAerialVideo;

public interface ZjTzAerialVideoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzAerialVideo record);

    int insertSelective(ZjTzAerialVideo record);

    ZjTzAerialVideo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzAerialVideo record);

    int updateByPrimaryKey(ZjTzAerialVideo record);

    List<ZjTzAerialVideo> selectByZjTzAerialVideoList(ZjTzAerialVideo record);

    int batchDeleteUpdateZjTzAerialVideo(List<ZjTzAerialVideo> recordList, ZjTzAerialVideo record);

}

