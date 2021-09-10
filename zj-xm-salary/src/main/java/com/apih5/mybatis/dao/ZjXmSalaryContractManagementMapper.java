package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryContractManagement;

public interface ZjXmSalaryContractManagementMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryContractManagement record);

	int insertSelective(ZjXmSalaryContractManagement record);

	ZjXmSalaryContractManagement selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryContractManagement record);

	int updateByPrimaryKey(ZjXmSalaryContractManagement record);

	List<ZjXmSalaryContractManagement> selectByZjXmSalaryContractManagementList(ZjXmSalaryContractManagement record);

	int batchDeleteUpdateZjXmSalaryContractManagement(List<ZjXmSalaryContractManagement> recordList,
			ZjXmSalaryContractManagement record);

	int batchDeleteZjXmSalaryContractManagementByExtensionId(List recordList, ZjXmSalaryContractManagement record);

	ZjXmSalaryContractManagement getZjXmSalaryContractManagementByExtensionId(String key);
}
