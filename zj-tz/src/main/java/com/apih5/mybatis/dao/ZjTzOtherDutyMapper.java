package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzOtherDuty;

public interface ZjTzOtherDutyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzOtherDuty record);

    int insertSelective(ZjTzOtherDuty record);

    ZjTzOtherDuty selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzOtherDuty record);

    int updateByPrimaryKey(ZjTzOtherDuty record);

    List<ZjTzOtherDuty> selectByZjTzOtherDutyList(ZjTzOtherDuty record);

    int batchDeleteUpdateZjTzOtherDuty(List<ZjTzOtherDuty> recordList, ZjTzOtherDuty record);

}

