package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;

public interface ZjXmCqjxExecutiveAssistantMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxExecutiveAssistant record);

    int insertSelective(ZjXmCqjxExecutiveAssistant record);

    ZjXmCqjxExecutiveAssistant selectByPrimaryKey(String key);
    
    ZjXmCqjxExecutiveAssistant selectByManagerId(ZjXmCqjxExecutiveAssistant record);

    int updateByPrimaryKeySelective(ZjXmCqjxExecutiveAssistant record);

    int updateByPrimaryKey(ZjXmCqjxExecutiveAssistant record);
    
    int selectZjXmCqjxExecutiveAssistantTodoCount(ZjXmCqjxExecutiveAssistant record);
    
    int selectZjXmCqjxAssistantLeaderTodoCount(ZjXmCqjxExecutiveAssistant record);
    
    int selectZjXmCqjxAssistantLeaderHasTodoCount(ZjXmCqjxExecutiveAssistant record);

    List<ZjXmCqjxExecutiveAssistant> selectByZjXmCqjxExecutiveAssistantList(ZjXmCqjxExecutiveAssistant record);
    
    List<ZjXmCqjxExecutiveAssistant> selectZjXmCqjxExecutiveAssistantListByList(ZjXmCqjxExecutiveAssistant record);
    
    List<ZjXmCqjxExecutiveAssistant> selectZjXmCqjxAssessmentManageListByDeptHeader(ZjXmCqjxExecutiveAssistant record);
    
    List<ZjXmCqjxExecutiveAssistant> selectListByZjXmCqjxExecutiveAssistant(ZjXmCqjxExecutiveAssistant record);
    
    List<ZjXmCqjxExecutiveAssistant> selectLeaderTodoListByUserKey(ZjXmCqjxExecutiveAssistant record);
    
    List<ZjXmCqjxExecutiveAssistant> selectAssistantDetailByQuarter(ZjXmCqjxExecutiveAssistant record);

    int batchDeleteUpdateZjXmCqjxExecutiveAssistant(List<ZjXmCqjxExecutiveAssistant> recordList, ZjXmCqjxExecutiveAssistant record);
    
    List<ZjXmCqjxExecutiveAssistant> selectLeaderDoneListByUserKey(ZjXmCqjxExecutiveAssistant record);
    
}

