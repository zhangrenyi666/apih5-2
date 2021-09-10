package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjTzBrandResultShow;
import com.apih5.mybatis.pojo.ZjTzProEvent;

public interface ZjTzProEventMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProEvent record);

    int insertSelective(ZjTzProEvent record);

    ZjTzProEvent selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProEvent record);

    int updateByPrimaryKey(ZjTzProEvent record);

    List<ZjTzProEvent> selectByZjTzProEventList(ZjTzProEvent record);

    int batchDeleteUpdateZjTzProEvent(List<ZjTzProEvent> recordList, ZjTzProEvent record);
    
    int batchRecallZjTzProEvent(List<ZjTzProEvent> recordList, ZjTzProEvent record);
    
    List<ZjTzProEvent> exportZjTzProEventList(ZjTzProEvent record);
}

