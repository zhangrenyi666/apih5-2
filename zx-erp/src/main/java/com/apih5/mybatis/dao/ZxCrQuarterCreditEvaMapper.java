package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrQuarterCreditEva;

public interface ZxCrQuarterCreditEvaMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrQuarterCreditEva record);

    int insertSelective(ZxCrQuarterCreditEva record);

    ZxCrQuarterCreditEva selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrQuarterCreditEva record);

    int updateByPrimaryKey(ZxCrQuarterCreditEva record);

    List<ZxCrQuarterCreditEva> selectByZxCrQuarterCreditEvaList(ZxCrQuarterCreditEva record);

    int batchDeleteUpdateZxCrQuarterCreditEva(List<ZxCrQuarterCreditEva> recordList, ZxCrQuarterCreditEva record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
