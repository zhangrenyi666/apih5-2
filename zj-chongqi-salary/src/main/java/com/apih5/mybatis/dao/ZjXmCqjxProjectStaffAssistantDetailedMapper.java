package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectStaffAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;

public interface ZjXmCqjxProjectStaffAssistantDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectStaffAssistantDetailed record);

    int insertSelective(ZjXmCqjxProjectStaffAssistantDetailed record);

    ZjXmCqjxProjectStaffAssistantDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectStaffAssistantDetailed record);

    int updateByPrimaryKey(ZjXmCqjxProjectStaffAssistantDetailed record);

    List<ZjXmCqjxProjectStaffAssistantDetailed> selectByZjXmCqjxProjectStaffAssistantDetailedList(ZjXmCqjxProjectStaffAssistantDetailed record);

    int batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed(List<ZjXmCqjxProjectStaffAssistantDetailed> recordList, ZjXmCqjxProjectStaffAssistantDetailed record);
    
    List<ZjXmCqjxAssistantReportBean> selectZjXmCqjxProjectStaffDetailedRportListByQuarter(ZjXmCqjxStaffAssistantDetailed record);
    
    

}

