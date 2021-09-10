package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationScore;

public interface ZxCrProjectEvaluationScoreMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrProjectEvaluationScore record);

    int insertSelective(ZxCrProjectEvaluationScore record);

    ZxCrProjectEvaluationScore selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrProjectEvaluationScore record);

    int updateByPrimaryKey(ZxCrProjectEvaluationScore record);

    List<ZxCrProjectEvaluationScore> selectByZxCrProjectEvaluationScoreList(ZxCrProjectEvaluationScore record);

    int batchDeleteUpdateZxCrProjectEvaluationScore(List<ZxCrProjectEvaluationScore> recordList, ZxCrProjectEvaluationScore record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrProjectEvaluationScore> selectByMainID(ZxCrProjectEvaluationScore record);
}
