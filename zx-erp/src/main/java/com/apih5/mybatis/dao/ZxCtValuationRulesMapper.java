package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtValuationRules;

public interface ZxCtValuationRulesMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtValuationRules record);

    int insertSelective(ZxCtValuationRules record);

    ZxCtValuationRules selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtValuationRules record);

    int updateByPrimaryKey(ZxCtValuationRules record);

    List<ZxCtValuationRules> selectByZxCtValuationRulesList(ZxCtValuationRules record);

    int batchDeleteUpdateZxCtValuationRules(List<ZxCtValuationRules> recordList, ZxCtValuationRules record);

}

