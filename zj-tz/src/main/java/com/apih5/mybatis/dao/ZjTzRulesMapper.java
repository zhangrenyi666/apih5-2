package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRules;

public interface ZjTzRulesMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRules record);

    int insertSelective(ZjTzRules record);

    ZjTzRules selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRules record);

    int updateByPrimaryKey(ZjTzRules record);

    List<ZjTzRules> selectByZjTzRulesList(ZjTzRules record);

    int batchDeleteUpdateZjTzRules(List<ZjTzRules> recordList, ZjTzRules record);

	int batchDeleteReleaseZjTzRules(List<ZjTzRules> zjTzRulesList, ZjTzRules zjTzRules);

	int batchDeleteRecallZjTzRules(List<ZjTzRules> zjTzRulesList, ZjTzRules zjTzRules);
	
	List<ZjTzRules> selectZjTzHomeRules(ZjTzRules record);
}

