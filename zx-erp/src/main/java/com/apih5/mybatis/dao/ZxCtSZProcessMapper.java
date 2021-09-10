package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSZProcess;

public interface ZxCtSZProcessMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxCtSZProcess record);

	int insertSelective(ZxCtSZProcess record);

	ZxCtSZProcess selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxCtSZProcess record);

	int updateByPrimaryKey(ZxCtSZProcess record);

	List<ZxCtSZProcess> selectByZxCtSZProcessList(ZxCtSZProcess record);

	int batchDeleteUpdateZxCtSZProcess(List<ZxCtSZProcess> recordList, ZxCtSZProcess record);

	List<ZxCtSZProcess> getZxCtSZProcessItemList(ZxCtSZProcess zxCtSZProcess);

	List<ZxCtSZProcess> gcsgGetZxCtSZProcessList(ZxCtSZProcess record);

	List<ZxCtSZProcess> gcsgGetZxCtSZProcessSelect(ZxCtSZProcess record);
}
