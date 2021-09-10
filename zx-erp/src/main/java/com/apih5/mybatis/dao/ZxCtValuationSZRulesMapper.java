package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;

public interface ZxCtValuationSZRulesMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtValuationSZRules record);

    int insertSelective(ZxCtValuationSZRules record);

    ZxCtValuationSZRules selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtValuationSZRules record);

    int updateByPrimaryKey(ZxCtValuationSZRules record);

    List<ZxCtValuationSZRules> selectByZxCtValuationSZRulesList(ZxCtValuationSZRules record);

    int batchDeleteUpdateZxCtValuationSZRules(List<ZxCtValuationSZRules> recordList, ZxCtValuationSZRules record);

}

