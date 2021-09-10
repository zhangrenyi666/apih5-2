package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentMember;

public interface ZjXmCqjxProjectDisciplineAssessmentMemberMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectDisciplineAssessmentMember record);

    int insertSelective(ZjXmCqjxProjectDisciplineAssessmentMember record);

    ZjXmCqjxProjectDisciplineAssessmentMember selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectDisciplineAssessmentMember record);

    int updateByPrimaryKey(ZjXmCqjxProjectDisciplineAssessmentMember record);

    List<ZjXmCqjxProjectDisciplineAssessmentMember> selectByZjXmCqjxProjectDisciplineAssessmentMemberList(ZjXmCqjxProjectDisciplineAssessmentMember record);

    int batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentMember(List<ZjXmCqjxProjectDisciplineAssessmentMember> recordList, ZjXmCqjxProjectDisciplineAssessmentMember record);

}

