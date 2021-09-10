package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxUserIndexLibrary;

public interface ZjXmJxUserIndexLibraryMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxUserIndexLibrary record);

	int insertSelective(ZjXmJxUserIndexLibrary record);

	ZjXmJxUserIndexLibrary selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxUserIndexLibrary record);

	int updateByPrimaryKey(ZjXmJxUserIndexLibrary record);

	List<ZjXmJxUserIndexLibrary> selectByZjXmJxUserIndexLibraryList(ZjXmJxUserIndexLibrary record);

	int batchDeleteUpdateZjXmJxUserIndexLibrary(List<ZjXmJxUserIndexLibrary> recordList, ZjXmJxUserIndexLibrary record);

	int batchInsertZjXmJxUserIndexLibrary(List<ZjXmJxUserIndexLibrary> recordList);

	int sumZjXmJxUserIndexLibraryByCondition(ZjXmJxUserIndexLibrary record);

	int deleteZjXmJxUserIndexLibraryByCondition(ZjXmJxUserIndexLibrary record);
}
