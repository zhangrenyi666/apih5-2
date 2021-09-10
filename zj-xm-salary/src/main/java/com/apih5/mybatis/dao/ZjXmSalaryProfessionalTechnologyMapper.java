package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryProfessionalTechnology;

public interface ZjXmSalaryProfessionalTechnologyMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryProfessionalTechnology record);

	int insertSelective(ZjXmSalaryProfessionalTechnology record);

	ZjXmSalaryProfessionalTechnology selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryProfessionalTechnology record);

	int updateByPrimaryKey(ZjXmSalaryProfessionalTechnology record);

	List<ZjXmSalaryProfessionalTechnology> selectByZjXmSalaryProfessionalTechnologyList(
			ZjXmSalaryProfessionalTechnology record);

	int batchDeleteUpdateZjXmSalaryProfessionalTechnology(List<ZjXmSalaryProfessionalTechnology> recordList,
			ZjXmSalaryProfessionalTechnology record);

	int batchDeleteZjXmSalaryProfessionalTechnologyByExtensionId(List recordList,
			ZjXmSalaryProfessionalTechnology record);

}
