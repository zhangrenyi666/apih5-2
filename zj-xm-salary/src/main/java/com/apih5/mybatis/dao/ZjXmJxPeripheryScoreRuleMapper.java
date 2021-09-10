package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreRule;

public interface ZjXmJxPeripheryScoreRuleMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxPeripheryScoreRule record);

	int insertSelective(ZjXmJxPeripheryScoreRule record);

	ZjXmJxPeripheryScoreRule selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxPeripheryScoreRule record);

	int updateByPrimaryKey(ZjXmJxPeripheryScoreRule record);

	List<ZjXmJxPeripheryScoreRule> selectByZjXmJxPeripheryScoreRuleList(ZjXmJxPeripheryScoreRule record);

	int batchDeleteUpdateZjXmJxPeripheryScoreRule(List<ZjXmJxPeripheryScoreRule> recordList,
			ZjXmJxPeripheryScoreRule record);

	int updateZjXmJxPeripheryScoreRuleByIdList(List recordList);

}
