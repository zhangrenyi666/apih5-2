package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.BaseCode;

public interface BaseCodeMapper {
	int deleteByPrimaryKey(String key);

	int insert(BaseCode record);

	int insertSelective(BaseCode record);

	BaseCode selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(BaseCode record);

	int updateByPrimaryKey(BaseCode record);

	List<BaseCode> selectByBaseCodeList(BaseCode record);

	int batchDeleteUpdateBaseCode(List<BaseCode> recordList);

	// ---扩展
	List<BaseCode> selectByBaseCodeListByLike(BaseCode record);

	List<BaseCode> getBaseCodeTree(BaseCode record);

	// 指定层级
	List<BaseCode> selectBaseCodeTreeByLevel(BaseCode record);

	int countBaseCodeListByCodePid(BaseCode record);

	int batchUpdateBaseCodePidNameAll(BaseCode record);

	int batchUpdatePidAllAndPidNameAll(BaseCode record);

	int batchUpdateBaseCode(List<BaseCode> recordList);

}
