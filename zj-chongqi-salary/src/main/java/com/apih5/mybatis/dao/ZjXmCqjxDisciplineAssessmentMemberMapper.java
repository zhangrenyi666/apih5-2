package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;

public interface ZjXmCqjxDisciplineAssessmentMemberMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxDisciplineAssessmentMember record);

    int insertSelective(ZjXmCqjxDisciplineAssessmentMember record);

    ZjXmCqjxDisciplineAssessmentMember selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxDisciplineAssessmentMember record);

    int updateByPrimaryKey(ZjXmCqjxDisciplineAssessmentMember record);

    List<ZjXmCqjxDisciplineAssessmentMember> selectByZjXmCqjxDisciplineAssessmentMemberList(ZjXmCqjxDisciplineAssessmentMember record);

    int batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember(List<ZjXmCqjxDisciplineAssessmentMember> recordList, ZjXmCqjxDisciplineAssessmentMember record);

}

