package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryWorkExperience;

public interface ZjXmSalaryWorkExperienceMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryWorkExperience record);

	int insertSelective(ZjXmSalaryWorkExperience record);

	ZjXmSalaryWorkExperience selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryWorkExperience record);

	int updateByPrimaryKey(ZjXmSalaryWorkExperience record);

	List<ZjXmSalaryWorkExperience> selectByZjXmSalaryWorkExperienceList(ZjXmSalaryWorkExperience record);

	int batchDeleteUpdateZjXmSalaryWorkExperience(List<ZjXmSalaryWorkExperience> recordList,
			ZjXmSalaryWorkExperience record);

	int batchDeleteZjXmSalaryWorkExperienceByExtensionId(List recordList, ZjXmSalaryWorkExperience record);

}
