package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxUserScoringLeader;

public interface ZjXmJxUserScoringLeaderMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxUserScoringLeader record);

	int insertSelective(ZjXmJxUserScoringLeader record);

	ZjXmJxUserScoringLeader selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxUserScoringLeader record);

	int updateByPrimaryKey(ZjXmJxUserScoringLeader record);

	List<ZjXmJxUserScoringLeader> selectByZjXmJxUserScoringLeaderList(ZjXmJxUserScoringLeader record);

	int batchDeleteUpdateZjXmJxUserScoringLeader(List<ZjXmJxUserScoringLeader> recordList,
			ZjXmJxUserScoringLeader record);

	int batchDeleteUpdateZjXmJxUserScoringLeaderByDeptUser(List<ZjXmJxUserScoringLeader> recordList,
			ZjXmJxUserScoringLeader record);

	int bathInsertZjXmJxUserScoringLeader(List<ZjXmJxUserScoringLeader> recordList);

}
