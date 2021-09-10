package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;

public interface ZjXmCqjxCollaborationAssessmentMemberMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxCollaborationAssessmentMember record);

    int insertSelective(ZjXmCqjxCollaborationAssessmentMember record);

    ZjXmCqjxCollaborationAssessmentMember selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxCollaborationAssessmentMember record);

    int updateByPrimaryKey(ZjXmCqjxCollaborationAssessmentMember record);
    
    int selectZjXmCqjxCollaborationTodoCount(ZjXmCqjxCollaborationAssessmentMember record);

    List<ZjXmCqjxCollaborationAssessmentMember> selectByZjXmCqjxCollaborationAssessmentMemberList(ZjXmCqjxCollaborationAssessmentMember record);
    
    List<ZjXmCqjxCollaborationAssessmentMember> selectByZjXmCqjxCollaborationAssessmentList(ZjXmCqjxCollaborationAssessmentMember record);
    
    List<ZjXmCqjxCollaborationAssessmentMember> selectByZjXmCqjxCollaborationAssessmentListByUser(ZjXmCqjxCollaborationAssessmentMember record);
    
    List<ZjXmCqjxCollaborationAssessmentMember> selectDeleteByZjXmCqjxCollaborationAssessmentMemberList(ZjXmCqjxCollaborationAssessmentMember record);

    int batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(List<ZjXmCqjxCollaborationAssessmentMember> recordList, ZjXmCqjxCollaborationAssessmentMember record);
    
    int batchUpdateZjXmCqjxCollaborationAssessmentMember(List<ZjXmCqjxCollaborationAssessmentMember> recordList, ZjXmCqjxCollaborationAssessmentMember record);

}

