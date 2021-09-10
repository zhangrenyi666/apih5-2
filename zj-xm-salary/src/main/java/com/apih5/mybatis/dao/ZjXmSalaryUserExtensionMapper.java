package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryUserExtension;

public interface ZjXmSalaryUserExtensionMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryUserExtension record);

	int insertSelective(ZjXmSalaryUserExtension record);

	ZjXmSalaryUserExtension selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryUserExtension record);

	int updateByPrimaryKey(ZjXmSalaryUserExtension record);

	List<ZjXmSalaryUserExtension> selectByZjXmSalaryUserExtensionList(ZjXmSalaryUserExtension record);

	int batchDeleteUpdateZjXmSalaryUserExtension(List<ZjXmSalaryUserExtension> recordList,
			ZjXmSalaryUserExtension record);

	List<ZjXmSalaryUserExtension> getZjXmSalaryUserExtensionListByCondition(ZjXmSalaryUserExtension record);

	ZjXmSalaryUserExtension getZjXmSalaryUserExtensionDetails(ZjXmSalaryUserExtension record);

	List<ZjXmSalaryUserExtension> getZjXmSalaryUserExtensionListByDept(ZjXmSalaryUserExtension record);
}
