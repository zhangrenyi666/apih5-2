package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzMonthlyMeet;

public interface ZjTzMonthlyMeetMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzMonthlyMeet record);

    int insertSelective(ZjTzMonthlyMeet record);

    ZjTzMonthlyMeet selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzMonthlyMeet record);

    int updateByPrimaryKey(ZjTzMonthlyMeet record);

    List<ZjTzMonthlyMeet> selectByZjTzMonthlyMeetList(ZjTzMonthlyMeet record);

    int batchDeleteUpdateZjTzMonthlyMeet(List<ZjTzMonthlyMeet> recordList, ZjTzMonthlyMeet record);

}

