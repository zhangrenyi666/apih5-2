package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;

public interface ZjXmCqjxProjectEvaluationApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectEvaluationApproval record);

    int insertSelective(ZjXmCqjxProjectEvaluationApproval record);

    ZjXmCqjxProjectEvaluationApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectEvaluationApproval record);

    int updateByPrimaryKey(ZjXmCqjxProjectEvaluationApproval record);

    List<ZjXmCqjxProjectEvaluationApproval> selectByZjXmCqjxProjectEvaluationApprovalList(ZjXmCqjxProjectEvaluationApproval record);

    int batchDeleteUpdateZjXmCqjxProjectEvaluationApproval(List<ZjXmCqjxProjectEvaluationApproval> recordList, ZjXmCqjxProjectEvaluationApproval record);

}

