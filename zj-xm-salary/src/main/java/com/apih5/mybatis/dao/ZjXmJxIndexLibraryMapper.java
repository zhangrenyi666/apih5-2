package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxIndexLibrary;

public interface ZjXmJxIndexLibraryMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxIndexLibrary record);

	int insertSelective(ZjXmJxIndexLibrary record);

	ZjXmJxIndexLibrary selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxIndexLibrary record);

	int updateByPrimaryKey(ZjXmJxIndexLibrary record);

	List<ZjXmJxIndexLibrary> selectByZjXmJxIndexLibraryList(ZjXmJxIndexLibrary record);

	int batchDeleteUpdateZjXmJxIndexLibrary(List<ZjXmJxIndexLibrary> recordList, ZjXmJxIndexLibrary record);

	int batchInsertZjXmJxIndexLibrary(List<ZjXmJxIndexLibrary> recordList);

	int sumZjXmJxIndexLibraryByCondition(ZjXmJxIndexLibrary record);

	int checkZjXmJxIndexLibraryByCondition(List<ZjXmJxIndexLibrary> recordList);
}
