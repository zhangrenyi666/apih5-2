package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryPositionLevelSalary;

public interface ZjXmSalaryPositionLevelSalaryMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryPositionLevelSalary record);

	int insertSelective(ZjXmSalaryPositionLevelSalary record);

	ZjXmSalaryPositionLevelSalary selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryPositionLevelSalary record);

	int updateByPrimaryKey(ZjXmSalaryPositionLevelSalary record);

	List<ZjXmSalaryPositionLevelSalary> selectByZjXmSalaryPositionLevelSalaryList(ZjXmSalaryPositionLevelSalary record);

	int batchDeleteUpdateZjXmSalaryPositionLevelSalary(List<ZjXmSalaryPositionLevelSalary> recordList,
			ZjXmSalaryPositionLevelSalary record);

	int batchInsertZjXmSalaryPositionLevelSalary(List<ZjXmSalaryPositionLevelSalary> recordList);

	int batchUpdateZjXmSalaryPositionLevelSalary(List<ZjXmSalaryPositionLevelSalary> recordList);

	int checkZjXmSalarPositionLevelSalary(ZjXmSalaryPositionLevelSalary record);
}
