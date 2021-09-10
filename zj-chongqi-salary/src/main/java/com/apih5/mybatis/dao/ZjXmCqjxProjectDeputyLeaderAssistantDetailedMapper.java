package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDeputyLeaderAssistantDetailed;

public interface ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDeputyLeaderAssistantDetailed record);

    int insertSelective(ZjXmCqjxProjectDeputyLeaderAssistantDetailed record);

    ZjXmCqjxProjectDeputyLeaderAssistantDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDeputyLeaderAssistantDetailed record);

    int updateByPrimaryKey(ZjXmCqjxProjectDeputyLeaderAssistantDetailed record);

    List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(ZjXmCqjxProjectDeputyLeaderAssistantDetailed record);

    int batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed(List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> recordList, ZjXmCqjxProjectDeputyLeaderAssistantDetailed record);
    
    List<ZjXmCqjxAssistantReportBean> selectZjXmCqjxAssistantDetailedRportListByQuarter(ZjXmCqjxExecutiveAssistantDetailed record);

}

