package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEva;

public interface ZxCrHalfYearCreditEvaMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrHalfYearCreditEva record);

    int insertSelective(ZxCrHalfYearCreditEva record);

    ZxCrHalfYearCreditEva selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrHalfYearCreditEva record);

    int updateByPrimaryKey(ZxCrHalfYearCreditEva record);

    List<ZxCrHalfYearCreditEva> selectByZxCrHalfYearCreditEvaList(ZxCrHalfYearCreditEva record);

    int batchDeleteUpdateZxCrHalfYearCreditEva(List<ZxCrHalfYearCreditEva> recordList, ZxCrHalfYearCreditEva record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    int updateauditStatusList(ZxCrHalfYearCreditEva record);
    
    List<ZxCrHalfYearCreditEva> selectByZxCrHalfYearCreditEvaListAll(ZxCrHalfYearCreditEva record);
}
