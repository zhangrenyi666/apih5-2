package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrProcess;

public interface ZxCtContrProcessMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxCtContrProcess record);

	int insertSelective(ZxCtContrProcess record);

	ZxCtContrProcess selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxCtContrProcess record);

	int updateByPrimaryKey(ZxCtContrProcess record);

	List<ZxCtContrProcess> selectByZxCtContrProcessList(ZxCtContrProcess record);

	List<ZxCtContrProcess> gcsgGetZxCtContrProcessList(ZxCtContrProcess record);

	int batchDeleteUpdateZxCtContrProcess(List<ZxCtContrProcess> recordList, ZxCtContrProcess record);

	List<ZxCtContrProcess> getZxCtContrProcessItemList(ZxCtContrProcess zxCtContrProcess);

}
