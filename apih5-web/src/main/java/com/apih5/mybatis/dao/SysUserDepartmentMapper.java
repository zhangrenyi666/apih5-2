package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;

public interface SysUserDepartmentMapper {
	int deleteByPrimaryKey(String key);

	int insert(SysUserDepartment record);

	int insertSelective(SysUserDepartment record);

	SysUserDepartment selectByPrimaryKey(String key);
	
	SysUserDepartment getRelationByUserKeyAndDepId(SysUserDepartment record);

	int updateByPrimaryKeySelective(SysUserDepartment record);

	int updateByPrimaryKey(SysUserDepartment record);

	List<SysUserDepartment> selectBySysUserDepartmentList(SysUserDepartment record);

	int batchDeleteUpdateSysUserDepartment(List<SysUserDepartment> recordList);

	int batchDeleteUpdateSysUserDepartmentByUserKey(List<SysUserDepartment> recordList);
	
	List<SysUserDepartment> selectSysUserDepartmentListBySync(SysUserDepartment record);

	// -----厦门公司多部门处理---
	int tempDeleteXiaMenZjyjUserDepartment(SysUserDepartment record);
	int tempDeleteXiaMenUserDepartment(SysUserDepartment record);
	int tempInsertXiaMenZjyjUserDepartment(SysUserDepartment record);
	// -----厦门公司多部门处理---

}
