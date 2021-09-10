package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrEvaluate;

public interface ZxCtContrEvaluateMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrEvaluate record);

    int insertSelective(ZxCtContrEvaluate record);

    ZxCtContrEvaluate selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrEvaluate record);

    int updateByPrimaryKey(ZxCtContrEvaluate record);

    List<ZxCtContrEvaluate> selectByZxCtContrEvaluateList(ZxCtContrEvaluate record);

    int batchDeleteUpdateZxCtContrEvaluate(List<ZxCtContrEvaluate> recordList, ZxCtContrEvaluate record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
