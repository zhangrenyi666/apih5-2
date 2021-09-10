package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;

public interface ZjXmCqjxCollaborationAssessmentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxCollaborationAssessment record);

    int insertSelective(ZjXmCqjxCollaborationAssessment record);

    ZjXmCqjxCollaborationAssessment selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxCollaborationAssessment record);

    int updateByPrimaryKey(ZjXmCqjxCollaborationAssessment record);

    List<ZjXmCqjxCollaborationAssessment> selectByZjXmCqjxCollaborationAssessmentList(ZjXmCqjxCollaborationAssessment record);

    int batchDeleteUpdateZjXmCqjxCollaborationAssessment(List<ZjXmCqjxCollaborationAssessment> recordList, ZjXmCqjxCollaborationAssessment record);

}

