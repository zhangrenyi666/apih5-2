package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSupervisorMonthlyMeeting;

public interface ZjTzSupervisorMonthlyMeetingMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSupervisorMonthlyMeeting record);

    int insertSelective(ZjTzSupervisorMonthlyMeeting record);

    ZjTzSupervisorMonthlyMeeting selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSupervisorMonthlyMeeting record);

    int updateByPrimaryKey(ZjTzSupervisorMonthlyMeeting record);

    List<ZjTzSupervisorMonthlyMeeting> selectByZjTzSupervisorMonthlyMeetingList(ZjTzSupervisorMonthlyMeeting record);

    int batchDeleteUpdateZjTzSupervisorMonthlyMeeting(List<ZjTzSupervisorMonthlyMeeting> recordList, ZjTzSupervisorMonthlyMeeting record);

}

