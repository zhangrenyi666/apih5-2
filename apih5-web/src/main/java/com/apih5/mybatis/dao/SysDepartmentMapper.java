package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;

public interface SysDepartmentMapper {
	int deleteByPrimaryKey(String key);

	int insert(SysDepartment record);

	int insertSelective(SysDepartment record);

	SysDepartment selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(SysDepartment record);

	int updateByPrimaryKey(SysDepartment record);

	List<SysDepartment> selectBySysDepartmentList(SysDepartment record);

	int countRepeatSysDepartmentName(SysDepartment record);

	List<SysDepartment> getSysDepartmentListByMobile(SysDepartment record);

	int batchDeleteUpdateSysDepartment(List<SysDepartment> recordList, SysDepartment record);

	int batchUpdateSysDepartment(List<SysDepartment> recordList);

	List selectSysDepartmentByUserkey(SysDepartment record);

	List getSysDepartmentProjectByUserkey(SysDepartment record);

	List<SysDepartment> getSysDepartmentListByUserkey(SysDepartment record);
	
	/**
	 * 模糊检索姓名、电话、部门等
	 * 
	 * @param record
	 * @return
	 */
	List<SysDepartment> selectSysDepartmentUserListByLike(SysDepartment record);

	/**
	 * 批量删除人员部门关系表数据
	 * 
	 * @param recordList
	 * @return
	 */
	int batchDeleteByDepIdAndUserList(SysDepartment record);

	/**
	 * 临时使用，旧oa替换后此方法需要删除
	 * 
	 * @return
	 */
	List<SysDepartment> tempSyncZjSysInfoDepartment();

	int tempSelectSysDepartment(SysDepartment record);

	int tempInsertSyncZjSysInfo(SysDepartment record);

	int countSysDepartmentListByCondition(SysDepartment record);

	int batchUpdateDepartmentPathAndDepartmentPathName(SysDepartment record);
	// int tempDeleteSyncZjSysInfoDepartment(SysUser record);
	// int tempInsertSyncZjSysInfoDepartment(SysUser record);

	List<SysDepartment> tempBySysDepartmentList(SysDepartment record);
	
	// 此方法用于tree，人员信息转部门信息
	List<SysDepartment> getUserListByCompanyId(SysUser record);
	// 同步接口
	List<SysDepartment> selectSysDepartmentListBySync(SysDepartment record);
}
