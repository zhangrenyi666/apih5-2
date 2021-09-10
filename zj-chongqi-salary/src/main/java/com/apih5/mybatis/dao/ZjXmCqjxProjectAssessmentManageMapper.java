package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssessmentManage;

public interface ZjXmCqjxProjectAssessmentManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectAssessmentManage record);

    int insertSelective(ZjXmCqjxProjectAssessmentManage record);

    ZjXmCqjxProjectAssessmentManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectAssessmentManage record);

    int updateByPrimaryKey(ZjXmCqjxProjectAssessmentManage record);

    List<ZjXmCqjxProjectAssessmentManage> selectByZjXmCqjxProjectAssessmentManageList(ZjXmCqjxProjectAssessmentManage record);

    int batchDeleteUpdateZjXmCqjxProjectAssessmentManage(List<ZjXmCqjxProjectAssessmentManage> recordList, ZjXmCqjxProjectAssessmentManage record);
    
    List<ZjXmCqjxProjectAssessmentManage> getZjXmCqjxAssessmentManageListByDeptHeader(ZjXmCqjxProjectAssessmentManage record);

}

