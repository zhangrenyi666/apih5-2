package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessment;

public interface ZjXmCqjxProjectDisciplineAssessmentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDisciplineAssessment record);

    int insertSelective(ZjXmCqjxProjectDisciplineAssessment record);

    ZjXmCqjxProjectDisciplineAssessment selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDisciplineAssessment record);

    int updateByPrimaryKey(ZjXmCqjxProjectDisciplineAssessment record);

    List<ZjXmCqjxProjectDisciplineAssessment> selectByZjXmCqjxProjectDisciplineAssessmentList(ZjXmCqjxProjectDisciplineAssessment record);

    int batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment(List<ZjXmCqjxProjectDisciplineAssessment> recordList, ZjXmCqjxProjectDisciplineAssessment record);
    
    int selectZjXmCqjxProjectDisciplineLeaderTodoCount(ZjXmCqjxProjectDisciplineAssessment record);

    List<ZjXmCqjxProjectDisciplineAssessment> selectxProjectDisciplineLeaderTodoListByUserKey(ZjXmCqjxProjectDisciplineAssessment record);
    
    List<ZjXmCqjxProjectDisciplineAssessment> selectZjXmCqjxProjectDisciplineAssessmentListByDeptId(ZjXmCqjxProjectDisciplineAssessment record);

	List<ZjXmCqjxProjectDisciplineAssessment> selectProjectDisciplineLeaderDoneListByUserKey(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment);    

}

