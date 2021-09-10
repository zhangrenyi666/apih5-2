package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;

public interface ZjXmCqjxProjectOverallEvaluationAssistantMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectOverallEvaluationAssistant record);

    int insertSelective(ZjXmCqjxProjectOverallEvaluationAssistant record);

    ZjXmCqjxProjectOverallEvaluationAssistant selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectOverallEvaluationAssistant record);

    int updateByPrimaryKey(ZjXmCqjxProjectOverallEvaluationAssistant record);

    List<ZjXmCqjxProjectOverallEvaluationAssistant> selectByZjXmCqjxProjectOverallEvaluationAssistantList(ZjXmCqjxProjectOverallEvaluationAssistant record);

    int batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant(List<ZjXmCqjxProjectOverallEvaluationAssistant> recordList, ZjXmCqjxProjectOverallEvaluationAssistant record);
    
    List<ZjXmCqjxProjectOverallEvaluationAssistant> selectProjectEvaluationLeaderTodoListByUserKey(ZjXmCqjxProjectOverallEvaluationAssistant record);
    
    int selectProjectEvaluationLeaderTodoCount(ZjXmCqjxProjectOverallEvaluationAssistant record);

	List<ZjXmCqjxProjectOverallEvaluationAssistant> selectProjectEvaluationLeaderDoneListByUserKey(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant);

}

