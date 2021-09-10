package com.apih5.mybatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apih5.framework.api.sysdb.entity.SysUser;

/**
 * 用户mapper
 */
@Repository
public interface SysUserMapper {

	int deleteByPrimaryKey(String key);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	List<SysUser> selectBySysUserList(SysUser record);

	int selectBySysUserTotalNum(SysUser record);

	int batchDeleteSysUser(List<SysUser> recordList, SysUser record);

	int bathInsertUserCommon(List<SysUser> recordList);

	// SysUser selectByPrimaryKey(String key);

	List<SysUser> getSysUserListByCompany(SysUser record);

	// 根据手机号、公司获取该用户信息，切换公司使用
	List<SysUser> getSysUserListByCompanyMobile(SysUser record);

	List<SysUser> getMemberListByDepartmentId(SysUser record);

	List<SysUser> getUserListByRoleAndCompanyId(SysUser record);
	
	List<SysUser> selectBySysUserListByProject(SysUser record);
	
	List<SysUser>  selectByUserListByProJobType(SysUser record);

	/**
	 * 判断用户是否存在
	 *
	 * @param sysUser
	 * @return
	 */
	SysUser checkUserExists(SysUser sysUser);

	/**
	 * 判断用户是否存在
	 *
	 * @param sysUser
	 * @return
	 */
	SysUser checkUserIdExists(SysUser sysUser);

	/**
	 * 通过userId获取用户信息
	 * 
	 * @param key
	 * @return
	 */
	SysUser getSysUserByUserId(String key);

	/**
	 * 判断用户账号是否有效
	 * 
	 * @param sysUser
	 * @return
	 */
	SysUser checkUserStatusAndType(SysUser sysUser);

	List<SysUser> selectBySysUserListByDepartment(SysUser sysUser);

	List<SysUser> getSysUserListByUserKeyList(List sysUserList);

	/**
	 * 临时使用，旧oa替换后此方法需要删除
	 * 
	 * @return
	 */
	List<SysUser> tempSyncZjSysInfo();

	int tempSelectSysUser(SysUser record);

	int tempInsertSyncZjSysInfo(SysUser record);

	int tempDeleteSyncZjSysInfoDepartment(SysUser record);

	int tempInsertSyncZjSysInfoDepartment(SysUser record);

	// /**
	// * 通过用户登录名称获得用户信息
	// *
	// * @param loginAccount
	// * @return
	// */
	//// SysUser getSysUserByloginAccount(String loginAccount);
	//
	// /**
	// * 根据条件查找系统用户
	// * @param sysUser
	// * @return
	// */
	//// List<SysUser> listAll(SysUser sysUser);
	// List selectBySysUserList(SysUser record);
	//
	// /**
	// * 通过用户id获得用户信息
	// * @param id
	// * @return
	// */
	// SysUser getSysUserByUserId(int id);
	//
	//// /**
	//// * 通过id删除用户信息
	//// * @param userId
	//// */
	//// void deleteUser(int userId);
	// int deleteByPrimaryKey(String key);
	//
	// /**
	// * 通过id删除用户角色对应表中的信息
	// * @param userId
	// */
	// void deleteUserRoleMapping(int userId);
	//
	// /**
	// * 新增用户
	// * @param sysUser
	// */
	// void userAdd(SysUser sysUser);
	//
	// /**
	// * 新增用户角色
	// * @param sysUserRoles
	// */
	// void addSysUserRole(List<SysUserRole> sysUserRoles);
	//
	// /**
	// * 更新用户
	// * @param sysUser
	// */
	// void userUpdate(SysUser sysUser);

	List<SysUser> selectSysUserListByLikeTree(SysUser sysUser);

	List<SysUser> selectSysUserListBySync(SysUser sysUser);
	
	SysUser getScoringLeaderByDepartmentId(SysUser sysUser);

	int batchUpdateSysUser(List<SysUser> sysUserList);
	
	int updateSysUserScoringLeader(SysUser sysUser);
}