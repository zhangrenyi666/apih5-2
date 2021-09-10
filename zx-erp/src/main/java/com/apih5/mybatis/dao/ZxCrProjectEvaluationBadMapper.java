package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationBad;

public interface ZxCrProjectEvaluationBadMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrProjectEvaluationBad record);

    int insertSelective(ZxCrProjectEvaluationBad record);

    ZxCrProjectEvaluationBad selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrProjectEvaluationBad record);

    int updateByPrimaryKey(ZxCrProjectEvaluationBad record);

    List<ZxCrProjectEvaluationBad> selectByZxCrProjectEvaluationBadList(ZxCrProjectEvaluationBad record);

    int batchDeleteUpdateZxCrProjectEvaluationBad(List<ZxCrProjectEvaluationBad> recordList, ZxCrProjectEvaluationBad record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrProjectEvaluationBad> selectBymainID(ZxCrProjectEvaluationBad record);
}
