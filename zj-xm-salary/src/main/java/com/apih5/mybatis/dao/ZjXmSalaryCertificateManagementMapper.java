package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryCertificateManagement;

public interface ZjXmSalaryCertificateManagementMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryCertificateManagement record);

	int insertSelective(ZjXmSalaryCertificateManagement record);

	ZjXmSalaryCertificateManagement selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryCertificateManagement record);

	int updateByPrimaryKey(ZjXmSalaryCertificateManagement record);

	List<ZjXmSalaryCertificateManagement> selectByZjXmSalaryCertificateManagementList(
			ZjXmSalaryCertificateManagement record);

	int batchDeleteUpdateZjXmSalaryCertificateManagement(List<ZjXmSalaryCertificateManagement> recordList,
			ZjXmSalaryCertificateManagement record);

	int batchDeleteZjXmSalaryCertificateManagementByExtensionId(List recordList,
			ZjXmSalaryCertificateManagement record);

}
