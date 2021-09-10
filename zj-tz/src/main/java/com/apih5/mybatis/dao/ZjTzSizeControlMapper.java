package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSizeControl;

public interface ZjTzSizeControlMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSizeControl record);

    int insertSelective(ZjTzSizeControl record);

    ZjTzSizeControl selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSizeControl record);

    int updateByPrimaryKey(ZjTzSizeControl record);

    List<ZjTzSizeControl> selectByZjTzSizeControlList(ZjTzSizeControl record);

    int batchDeleteUpdateZjTzSizeControl(List<ZjTzSizeControl> recordList, ZjTzSizeControl record);

}

