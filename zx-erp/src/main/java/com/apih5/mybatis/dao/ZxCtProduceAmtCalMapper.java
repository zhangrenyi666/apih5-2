package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtProduceAmtCal;

public interface ZxCtProduceAmtCalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtProduceAmtCal record);

    int insertSelective(ZxCtProduceAmtCal record);

    ZxCtProduceAmtCal selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtProduceAmtCal record);

    int updateByPrimaryKey(ZxCtProduceAmtCal record);

    List<ZxCtProduceAmtCal> selectByZxCtProduceAmtCalList(ZxCtProduceAmtCal record);

    int batchDeleteUpdateZxCtProduceAmtCal(List<ZxCtProduceAmtCal> recordList, ZxCtProduceAmtCal record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxCtProduceAmtCal selectTotalProduceAmt(ZxCtProduceAmtCal selectProduceAmtCal, List<String> sensonList);
}
