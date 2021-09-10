package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryFamilyBackground;

public interface ZjXmSalaryFamilyBackgroundMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryFamilyBackground record);

	int insertSelective(ZjXmSalaryFamilyBackground record);

	ZjXmSalaryFamilyBackground selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryFamilyBackground record);

	int updateByPrimaryKey(ZjXmSalaryFamilyBackground record);

	List<ZjXmSalaryFamilyBackground> selectByZjXmSalaryFamilyBackgroundList(ZjXmSalaryFamilyBackground record);

	int batchDeleteUpdateZjXmSalaryFamilyBackground(List<ZjXmSalaryFamilyBackground> recordList,
			ZjXmSalaryFamilyBackground record);

	int batchDeleteZjXmSalaryFamilyBackgroundByExtensionId(List recordList, ZjXmSalaryFamilyBackground record);

}
