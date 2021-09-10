package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentDetailed;

public interface ZjXmCqjxProjectDisciplineAssessmentDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDisciplineAssessmentDetailed record);

    int insertSelective(ZjXmCqjxProjectDisciplineAssessmentDetailed record);

    ZjXmCqjxProjectDisciplineAssessmentDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDisciplineAssessmentDetailed record);

    int updateByPrimaryKey(ZjXmCqjxProjectDisciplineAssessmentDetailed record);

    List<ZjXmCqjxProjectDisciplineAssessmentDetailed> selectByZjXmCqjxProjectDisciplineAssessmentDetailedList(ZjXmCqjxProjectDisciplineAssessmentDetailed record);

    int batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentDetailed(List<ZjXmCqjxProjectDisciplineAssessmentDetailed> recordList, ZjXmCqjxProjectDisciplineAssessmentDetailed record);

}

