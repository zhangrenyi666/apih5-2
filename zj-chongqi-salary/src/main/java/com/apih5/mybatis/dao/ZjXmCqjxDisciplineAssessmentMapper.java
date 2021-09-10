package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;

public interface ZjXmCqjxDisciplineAssessmentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDisciplineAssessment record);

    int insertSelective(ZjXmCqjxDisciplineAssessment record);

    ZjXmCqjxDisciplineAssessment selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDisciplineAssessment record);

    int updateByPrimaryKey(ZjXmCqjxDisciplineAssessment record);
    
    int selectZjXmCqjxDisciplineLeaderTodoCount(ZjXmCqjxDisciplineAssessment record);
    
    int selectZjXmCqjxDisciplineLeaderHasTodoCount(ZjXmCqjxDisciplineAssessment record);

    List<ZjXmCqjxDisciplineAssessment> selectByZjXmCqjxDisciplineAssessmentList(ZjXmCqjxDisciplineAssessment record);
    
    List<ZjXmCqjxDisciplineAssessment> selectDisciplineLeaderTodoListByUserKey(ZjXmCqjxDisciplineAssessment record);

    int batchDeleteUpdateZjXmCqjxDisciplineAssessment(List<ZjXmCqjxDisciplineAssessment> recordList, ZjXmCqjxDisciplineAssessment record);

	List<ZjXmCqjxDisciplineAssessment> selectDisciplineLeaderDoneListByUserKey(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment);

}

