package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;

public interface ZjXmCqjxAssessmentManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxAssessmentManage record);

    int insertSelective(ZjXmCqjxAssessmentManage record);

    ZjXmCqjxAssessmentManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxAssessmentManage record);

    int updateByPrimaryKey(ZjXmCqjxAssessmentManage record);

    List<ZjXmCqjxAssessmentManage> selectByZjXmCqjxAssessmentManageList(ZjXmCqjxAssessmentManage record);
    
    List<ZjXmCqjxAssessmentManage> getZjXmCqjxAssessmentManageListByDeptHeader(ZjXmCqjxAssessmentManage record);

    int batchDeleteUpdateZjXmCqjxAssessmentManage(List<ZjXmCqjxAssessmentManage> recordList, ZjXmCqjxAssessmentManage record);

}

