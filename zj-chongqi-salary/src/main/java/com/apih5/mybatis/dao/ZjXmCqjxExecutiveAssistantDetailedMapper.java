package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;

public interface ZjXmCqjxExecutiveAssistantDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxExecutiveAssistantDetailed record);

    int insertSelective(ZjXmCqjxExecutiveAssistantDetailed record);

    ZjXmCqjxExecutiveAssistantDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxExecutiveAssistantDetailed record);

    int updateByPrimaryKey(ZjXmCqjxExecutiveAssistantDetailed record);

    List<ZjXmCqjxExecutiveAssistantDetailed> selectByZjXmCqjxExecutiveAssistantDetailedList(ZjXmCqjxExecutiveAssistantDetailed record);
    
    List<ZjXmCqjxAssistantReportBean> selectZjXmCqjxAssistantDetailedRportListByQuarter(ZjXmCqjxExecutiveAssistantDetailed record);

    int batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed(List<ZjXmCqjxExecutiveAssistantDetailed> recordList, ZjXmCqjxExecutiveAssistantDetailed record);

}

