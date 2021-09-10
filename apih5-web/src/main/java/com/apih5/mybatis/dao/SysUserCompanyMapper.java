package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.framework.api.sysdb.entity.SysUserCompany;

public interface SysUserCompanyMapper {
	int deleteByPrimaryKey(String key);

	int insert(SysUserCompany record);

	int insertSelective(SysUserCompany record);

	SysUserCompany selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(SysUserCompany record);

	int updateByPrimaryKey(SysUserCompany record);

	List<SysUserCompany> selectBySysUserCompanyList(SysUserCompany record);

	int batchDeleteUpdateSysUserCompany(List<SysUserCompany> recordList);

}
