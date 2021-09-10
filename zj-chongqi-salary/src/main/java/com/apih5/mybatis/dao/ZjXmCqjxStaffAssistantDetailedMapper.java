package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;

public interface ZjXmCqjxStaffAssistantDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxStaffAssistantDetailed record);

    int insertSelective(ZjXmCqjxStaffAssistantDetailed record);

    ZjXmCqjxStaffAssistantDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxStaffAssistantDetailed record);

    int updateByPrimaryKey(ZjXmCqjxStaffAssistantDetailed record);

    List<ZjXmCqjxStaffAssistantDetailed> selectByZjXmCqjxStaffAssistantDetailedList(ZjXmCqjxStaffAssistantDetailed record);
    
    List<ZjXmCqjxAssistantReportBean> selectZjXmCqjxStaffDetailedRportListByQuarter(ZjXmCqjxStaffAssistantDetailed record);

    int batchDeleteUpdateZjXmCqjxStaffAssistantDetailed(List<ZjXmCqjxStaffAssistantDetailed> recordList, ZjXmCqjxStaffAssistantDetailed record);

}

