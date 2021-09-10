package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrJYearCreditEva;

public interface ZxCrJYearCreditEvaMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrJYearCreditEva record);

    int insertSelective(ZxCrJYearCreditEva record);

    ZxCrJYearCreditEva selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrJYearCreditEva record);

    int updateByPrimaryKey(ZxCrJYearCreditEva record);

    List<ZxCrJYearCreditEva> selectByZxCrJYearCreditEvaList(ZxCrJYearCreditEva record);

    int batchDeleteUpdateZxCrJYearCreditEva(List<ZxCrJYearCreditEva> recordList, ZxCrJYearCreditEva record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrJYearCreditEva> selectAll();
}
