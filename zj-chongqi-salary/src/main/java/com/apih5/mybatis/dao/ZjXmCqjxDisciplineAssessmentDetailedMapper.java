package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentDetailed;

public interface ZjXmCqjxDisciplineAssessmentDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDisciplineAssessmentDetailed record);

    int insertSelective(ZjXmCqjxDisciplineAssessmentDetailed record);

    ZjXmCqjxDisciplineAssessmentDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDisciplineAssessmentDetailed record);

    int updateByPrimaryKey(ZjXmCqjxDisciplineAssessmentDetailed record);

    List<ZjXmCqjxDisciplineAssessmentDetailed> selectByZjXmCqjxDisciplineAssessmentDetailedList(ZjXmCqjxDisciplineAssessmentDetailed record);

    int batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed(List<ZjXmCqjxDisciplineAssessmentDetailed> recordList, ZjXmCqjxDisciplineAssessmentDetailed record);

}

