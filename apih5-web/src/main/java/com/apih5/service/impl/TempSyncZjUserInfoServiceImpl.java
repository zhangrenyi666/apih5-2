package com.apih5.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.service.TempSyncZjUserInfoService;

@Service
public class TempSyncZjUserInfoServiceImpl implements TempSyncZjUserInfoService {

//	private final Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired(required = true)
//	private SysDepartmentMapper sysDepartmentMapper;
//	
//	@Autowired(required = true)
//	private SysUserMapper sysUserMapper;
	
//	@Override
//	public void SyncZjSysInfo() {
//		LoggerUtils.printDebugLogger(logger, "部门同步开始-----------start");
//		// 部门
//		// 查询两个表不一致的数据，所有不相同数据全部查出来
//		List<SysDepartment> listDepartment = sysDepartmentMapper.tempSyncZjSysInfoDepartment();
//		for(SysDepartment sysDepartment : listDepartment){
//			// 查找旧oa的数据，如果不存在此人则删除当前新系统数据
//			int count = sysDepartmentMapper.tempSelectSysDepartment(sysDepartment);
//			if(count == 0){
//				// 删除部门表
//				int  flag = sysDepartmentMapper.deleteByPrimaryKey(sysDepartment.getDepartmentId());
//				LoggerUtils.printDebugLogger(logger, "部门删除=" + flag +" " + sysDepartment.getDepartmentId());
//			}
//			else {
//				// 调用inser接口
//				int flag = sysDepartmentMapper.tempInsertSyncZjSysInfo(sysDepartment);
//				LoggerUtils.printDebugLogger(logger, "部门新增=" + flag +" " + sysDepartment.getDepartmentId());
//			}
//		}		
//		
//		// 用户信息表
//		// 查询两个表不一致的数据，所有不相同数据全部查出来
//		List<SysUser> list = sysUserMapper.tempSyncZjSysInfo();
//		for(SysUser sysUser : list){
//			// 查找旧oa的数据，如果不存在此人则删除当前新系统数据
//			int count = sysUserMapper.tempSelectSysUser(sysUser);
//			if(count == 0){
//				// 删除用户表
//				int  flag = sysUserMapper.deleteByPrimaryKey(sysUser.getUserKey());
//				LoggerUtils.printDebugLogger(logger, "用户删除1=" + flag +" " + sysUser.getUserId());
//				// 删除关系表
//				flag = sysUserMapper.tempDeleteSyncZjSysInfoDepartment(sysUser);
//				LoggerUtils.printDebugLogger(logger, "用户关系删除2=" + flag +" " + sysUser.getUserId());
//			}
//			else {
//				// 调用inser接口
//				int flag = sysUserMapper.tempInsertSyncZjSysInfo(sysUser);
//				System.out.println("人员=" + flag);
//				LoggerUtils.printDebugLogger(logger, "人员2=" + flag +" " + sysUser.getUserId());
//				
//				// 部门关系表
//				// 根据userid删除所有关于此人的部门关系数据
//				flag = sysUserMapper.tempDeleteSyncZjSysInfoDepartment(sysUser);
//				LoggerUtils.printDebugLogger(logger, "部门关系删除3=" + flag +" " + sysUser.getUserId());
//				// 新增数据
//				flag = sysUserMapper.tempInsertSyncZjSysInfoDepartment(sysUser);
//				LoggerUtils.printDebugLogger(logger, "部门关系新增5=" + flag +" " + sysUser.getUserId());
//			}
//		}
//		LoggerUtils.printDebugLogger(logger, "部门同步结束-----------start");
//	}

}
