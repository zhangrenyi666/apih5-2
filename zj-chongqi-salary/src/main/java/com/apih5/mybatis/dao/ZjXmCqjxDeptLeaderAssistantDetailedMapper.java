package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;

public interface ZjXmCqjxDeptLeaderAssistantDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDeptLeaderAssistantDetailed record);

    int insertSelective(ZjXmCqjxDeptLeaderAssistantDetailed record);

    ZjXmCqjxDeptLeaderAssistantDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDeptLeaderAssistantDetailed record);

    int updateByPrimaryKey(ZjXmCqjxDeptLeaderAssistantDetailed record);

    List<ZjXmCqjxDeptLeaderAssistantDetailed> selectByZjXmCqjxDeptLeaderAssistantDetailedList(ZjXmCqjxDeptLeaderAssistantDetailed record);
    
    List<ZjXmCqjxAssistantReportBean> selectZjXmCqjxDeptLeaderDetailedRportListByQuarter(ZjXmCqjxDeptLeaderAssistantDetailed record);

    int batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed(List<ZjXmCqjxDeptLeaderAssistantDetailed> recordList, ZjXmCqjxDeptLeaderAssistantDetailed record);

}

