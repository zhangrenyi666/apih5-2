package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryHealthCondition;

public interface ZjXmSalaryHealthConditionMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryHealthCondition record);

	int insertSelective(ZjXmSalaryHealthCondition record);

	ZjXmSalaryHealthCondition selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryHealthCondition record);

	int updateByPrimaryKey(ZjXmSalaryHealthCondition record);

	List<ZjXmSalaryHealthCondition> selectByZjXmSalaryHealthConditionList(ZjXmSalaryHealthCondition record);

	int batchDeleteUpdateZjXmSalaryHealthCondition(List<ZjXmSalaryHealthCondition> recordList,
			ZjXmSalaryHealthCondition record);

	int batchDeleteZjXmSalaryHealthConditionByExtensionId(List recordList, ZjXmSalaryHealthCondition record);

	ZjXmSalaryHealthCondition getZjXmSalaryHealthConditionByExtensionId(String key);

}
