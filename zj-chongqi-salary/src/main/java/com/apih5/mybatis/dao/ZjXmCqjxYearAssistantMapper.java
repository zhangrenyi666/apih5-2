package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;

public interface ZjXmCqjxYearAssistantMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxYearAssistant record);

    int insertSelective(ZjXmCqjxYearAssistant record);

    ZjXmCqjxYearAssistant selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxYearAssistant record);

    int updateByPrimaryKey(ZjXmCqjxYearAssistant record);
    
    int selectZjXmCqjxYearLeaderTodoCount(ZjXmCqjxYearAssistant record);
    
    int selectZjXmCqjxYearLeaderHasTodoCount(ZjXmCqjxYearAssistant record);

    List<ZjXmCqjxYearAssistant> selectByZjXmCqjxYearAssistantList(ZjXmCqjxYearAssistant record);
    
    List<ZjXmCqjxYearAssistant> selectZjXmCqjxYearAssessmentManageListByDeptHeader(ZjXmCqjxYearAssistant record);
    
    List<ZjXmCqjxYearAssistant> selectZjXmCqjxYearAssistantScoreDetail(ZjXmCqjxYearAssistant record);
    
    List<ZjXmCqjxYearAssistant> selectEvaluationLeaderTodoListByUserKey(ZjXmCqjxYearAssistant record);

    int batchDeleteUpdateZjXmCqjxYearAssistant(List<ZjXmCqjxYearAssistant> recordList, ZjXmCqjxYearAssistant record);

	List<ZjXmCqjxYearAssistant> selectEvaluationLeaderDoneListByUserKey(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant);


}

