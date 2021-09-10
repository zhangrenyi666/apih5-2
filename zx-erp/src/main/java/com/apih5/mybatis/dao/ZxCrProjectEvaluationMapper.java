package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCrCustomerInfo;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluation;

public interface ZxCrProjectEvaluationMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrProjectEvaluation record);

    int insertSelective(ZxCrProjectEvaluation record);

    ZxCrProjectEvaluation selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrProjectEvaluation record);

    int updateByPrimaryKey(ZxCrProjectEvaluation record);

    List<ZxCrProjectEvaluation> selectByZxCrProjectEvaluationList(ZxCrProjectEvaluation record);

    int batchDeleteUpdateZxCrProjectEvaluation(List<ZxCrProjectEvaluation> recordList, ZxCrProjectEvaluation record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    int updateByAuditStatus(ZxCrProjectEvaluation record);
    
    int updateByAuditStatusOut(ZxCrProjectEvaluation record);
    
    List<ZxCrProjectEvaluation> selectFirstHalfYearProjectEvaluationList(ZxCrProjectEvaluation record);

    List<ZxCrProjectEvaluation> selectSecondHalfYearProjectEvaluationList(ZxCrProjectEvaluation record);
}
