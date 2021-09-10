package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;

public interface ZjXmCqjxProjectExecutiveAssistantMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectExecutiveAssistant record);

    int insertSelective(ZjXmCqjxProjectExecutiveAssistant record);

    ZjXmCqjxProjectExecutiveAssistant selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectExecutiveAssistant record);

    int updateByPrimaryKey(ZjXmCqjxProjectExecutiveAssistant record);

    List<ZjXmCqjxProjectExecutiveAssistant> selectByZjXmCqjxProjectExecutiveAssistantList(ZjXmCqjxProjectExecutiveAssistant record);

    int batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant(List<ZjXmCqjxProjectExecutiveAssistant> recordList, ZjXmCqjxProjectExecutiveAssistant record);
    
    ZjXmCqjxProjectExecutiveAssistant selectByManagerId(ZjXmCqjxProjectExecutiveAssistant record);

    int selectZjXmCqjxProjectExecutiveAssistantTodoCount(ZjXmCqjxProjectExecutiveAssistant record);
    
    int selectZjXmCqjxProjectAssistantLeaderTodoCount(ZjXmCqjxProjectExecutiveAssistant record);

    List<ZjXmCqjxProjectExecutiveAssistant> selectZjXmCqjxProjectAssessmentManageListByDeptHeader(ZjXmCqjxProjectExecutiveAssistant record);
    
    List<ZjXmCqjxProjectExecutiveAssistant> selectListByZjXmCqjxProjectExecutiveAssistant(ZjXmCqjxProjectExecutiveAssistant record);
    
    List<ZjXmCqjxProjectExecutiveAssistant> selectProjectLeaderTodoListByUserKey(ZjXmCqjxProjectExecutiveAssistant record);
    
    List<ZjXmCqjxProjectExecutiveAssistant> selectProjectAssistantDetailByQuarter(ZjXmCqjxProjectExecutiveAssistant record);

	List<ZjXmCqjxProjectExecutiveAssistant> selectProjectLeaderDoneListByUserKey(ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant);


}

