package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryEducationBackground;

public interface ZjXmSalaryEducationBackgroundMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryEducationBackground record);

	int insertSelective(ZjXmSalaryEducationBackground record);

	ZjXmSalaryEducationBackground selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryEducationBackground record);

	int updateByPrimaryKey(ZjXmSalaryEducationBackground record);

	List<ZjXmSalaryEducationBackground> selectByZjXmSalaryEducationBackgroundList(ZjXmSalaryEducationBackground record);

	int batchDeleteUpdateZjXmSalaryEducationBackground(List<ZjXmSalaryEducationBackground> recordList,
			ZjXmSalaryEducationBackground record);

	int batchDeleteZjXmSalaryEducationBackgroundByExtensionId(List recordList, ZjXmSalaryEducationBackground record);

}
