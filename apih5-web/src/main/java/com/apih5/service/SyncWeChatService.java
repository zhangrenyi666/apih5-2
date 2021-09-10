package com.apih5.service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.entity.ResponseEntity;

public interface SyncWeChatService {
	/**
	 * 从企业微信同步到本系统（部门、用户）
	 * 
	 * @return
	 */
	ResponseEntity syncWeChatToSysInfo() throws Exception;
	
	/**
	 * 从企业微信同步到本系统 - 增量方式同步（部门、用户）
	 * 
	 * @return
	 */
	public ResponseEntity syncWeChatToSysInfoByUpdate() throws Exception;

//	/**
//	 * 从企业微信同步到本系统 - 四局
//	 * 
//	 * @return
//	 */
//	public ResponseEntity syncWeChatToSysInfoBySiju() throws Exception;

//	/**
//	 * 从本系统部门、人员同步到企业微信（四局）
//	 * 
//	 * @return 树形结构
//	 */
//	public ResponseEntity syncWeChatToUpdateSysDeptBySiju(SysDepartment sysDepartment);
	
	/**
	 * 从本系统部门同步到企业微信
	 * 
	 * @return 树形结构
	 */
	public ResponseEntity syncSysInfoToWeChat(SysDepartment sysDepartment);
	
	/**
	 * 从其他系统到本系统（部门、用户）
	 * 
	 * @return 树形结构
	 */
	public ResponseEntity syncOtherOaToSysInfo();
}

