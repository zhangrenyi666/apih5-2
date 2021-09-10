package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;

public interface ZxCrProjCreditScoreMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrProjCreditScore record);

    int insertSelective(ZxCrProjCreditScore record);

    ZxCrProjCreditScore selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrProjCreditScore record);

    int updateByPrimaryKey(ZxCrProjCreditScore record);

    List<ZxCrProjCreditScore> selectByZxCrProjCreditScoreList(ZxCrProjCreditScore record);

    int batchDeleteUpdateZxCrProjCreditScore(List<ZxCrProjCreditScore> recordList, ZxCrProjCreditScore record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
