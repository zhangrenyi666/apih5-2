package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentDetailed;

public interface ZjXmCqjxCollaborationAssessmentDetailedMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxCollaborationAssessmentDetailed record);

    int insertSelective(ZjXmCqjxCollaborationAssessmentDetailed record);

    ZjXmCqjxCollaborationAssessmentDetailed selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxCollaborationAssessmentDetailed record);

    int updateByPrimaryKey(ZjXmCqjxCollaborationAssessmentDetailed record);

    List<ZjXmCqjxCollaborationAssessmentDetailed> selectByZjXmCqjxCollaborationAssessmentDetailedList(ZjXmCqjxCollaborationAssessmentDetailed record);
    
    List<ZjXmCqjxCollaborationAssessmentDetailed> selectCollaborationAssessmentDetailedByEffectiveFlag(ZjXmCqjxCollaborationAssessmentDetailed record);

    int batchDeleteUpdateZjXmCqjxCollaborationAssessmentDetailed(List<ZjXmCqjxCollaborationAssessmentDetailed> recordList, ZjXmCqjxCollaborationAssessmentDetailed record);

}

