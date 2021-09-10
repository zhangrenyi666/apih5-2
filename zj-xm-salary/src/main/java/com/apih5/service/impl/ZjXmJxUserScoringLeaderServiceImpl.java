package com.apih5.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxUserScoringLeaderMapper;
import com.apih5.mybatis.pojo.ZjXmJxUserScoringLeader;
import com.apih5.service.ZjXmJxUserScoringLeaderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service("zjXmJxUserScoringLeaderService")
public class ZjXmJxUserScoringLeaderServiceImpl implements ZjXmJxUserScoringLeaderService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxUserScoringLeaderMapper zjXmJxUserScoringLeaderMapper;
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private SysDepartmentService sysDepartmentService;

	@Override
	public ResponseEntity getZjXmJxUserScoringLeaderListByCondition(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		if (zjXmJxUserScoringLeader == null) {
			zjXmJxUserScoringLeader = new ZjXmJxUserScoringLeader();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxUserScoringLeader.getPage(), zjXmJxUserScoringLeader.getLimit());
		// 获取数据
		List<ZjXmJxUserScoringLeader> zjXmJxUserScoringLeaderList = zjXmJxUserScoringLeaderMapper
				.selectByZjXmJxUserScoringLeaderList(zjXmJxUserScoringLeader);
		// 得到分页信息
		PageInfo<ZjXmJxUserScoringLeader> p = new PageInfo<>(zjXmJxUserScoringLeaderList);

		return repEntity.okList(zjXmJxUserScoringLeaderList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxUserScoringLeaderDetails(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		if (zjXmJxUserScoringLeader == null) {
			zjXmJxUserScoringLeader = new ZjXmJxUserScoringLeader();
		}
		// 获取数据
		ZjXmJxUserScoringLeader dbZjXmJxUserScoringLeader = zjXmJxUserScoringLeaderMapper
				.selectByPrimaryKey(zjXmJxUserScoringLeader.getScoringLeaderId());
		// 数据存在
		if (dbZjXmJxUserScoringLeader != null) {
			return repEntity.ok(dbZjXmJxUserScoringLeader);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxUserScoringLeader.setScoringLeaderId(UuidUtil.generate());
		zjXmJxUserScoringLeader.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxUserScoringLeaderMapper.insert(zjXmJxUserScoringLeader);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxUserScoringLeader);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxUserScoringLeader dbzjXmJxUserScoringLeader = zjXmJxUserScoringLeaderMapper
				.selectByPrimaryKey(zjXmJxUserScoringLeader.getScoringLeaderId());
		if (dbzjXmJxUserScoringLeader != null && StrUtil.isNotEmpty(dbzjXmJxUserScoringLeader.getScoringLeaderId())) {
			// 人员主键
			dbzjXmJxUserScoringLeader.setUserKey(zjXmJxUserScoringLeader.getUserKey());
			// 人员姓名
			dbzjXmJxUserScoringLeader.setRealName(zjXmJxUserScoringLeader.getRealName());
			// 部门ID
			dbzjXmJxUserScoringLeader.setDeptId(zjXmJxUserScoringLeader.getDeptId());
			// 部门名称
			dbzjXmJxUserScoringLeader.setDeptName(zjXmJxUserScoringLeader.getDeptName());
			// 项目ID
			dbzjXmJxUserScoringLeader.setProjectId(zjXmJxUserScoringLeader.getProjectId());
			// 项目名称
			dbzjXmJxUserScoringLeader.setProjectName(zjXmJxUserScoringLeader.getProjectName());
			// 评分领导ID
			dbzjXmJxUserScoringLeader.setLeaderId(zjXmJxUserScoringLeader.getLeaderId());
			// 评分领导姓名
			dbzjXmJxUserScoringLeader.setLeaderName(zjXmJxUserScoringLeader.getLeaderName());
			// 备注
			dbzjXmJxUserScoringLeader.setRemarks(zjXmJxUserScoringLeader.getRemarks());
			// 排序
			dbzjXmJxUserScoringLeader.setSort(zjXmJxUserScoringLeader.getSort());
			// 共通
			dbzjXmJxUserScoringLeader.setModifyUserInfo(userKey, realName);
			flag = zjXmJxUserScoringLeaderMapper.updateByPrimaryKey(dbzjXmJxUserScoringLeader);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxUserScoringLeader);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxUserScoringLeader(
			List<ZjXmJxUserScoringLeader> zjXmJxUserScoringLeaderList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxUserScoringLeaderList != null && zjXmJxUserScoringLeaderList.size() > 0) {
			ZjXmJxUserScoringLeader zjXmJxUserScoringLeader = new ZjXmJxUserScoringLeader();
			zjXmJxUserScoringLeader.setModifyUserInfo(userKey, realName);
			flag = zjXmJxUserScoringLeaderMapper.batchDeleteUpdateZjXmJxUserScoringLeader(zjXmJxUserScoringLeaderList,
					zjXmJxUserScoringLeader);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxUserScoringLeaderList);
		}
	}

	@Override
	public ResponseEntity setUpZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String userName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxUserScoringLeader != null && StrUtil.isNotEmpty(zjXmJxUserScoringLeader.getProjectId())
				&& StrUtil.isNotEmpty(zjXmJxUserScoringLeader.getProjectName())
				&& zjXmJxUserScoringLeader.getLeaderArray() != null
				&& zjXmJxUserScoringLeader.getLeaderArray().size() > 0 && zjXmJxUserScoringLeader.getUserList() != null
				&& zjXmJxUserScoringLeader.getUserList().size() > 0) {
			String leaderId = zjXmJxUserScoringLeader.getLeaderArray().getJSONObject(0).getStr("value");
			String leaderName = (zjXmJxUserScoringLeader.getLeaderArray().getJSONObject(0).getStr("label"));
			List<ZjXmJxUserScoringLeader> insertList = Lists.newArrayList();
			for (SysUser dbUser : zjXmJxUserScoringLeader.getUserList()) {
				ZjXmJxUserScoringLeader insertData = new ZjXmJxUserScoringLeader();
				insertData.setScoringLeaderId(UuidUtil.generate());
				insertData.setUserKey(dbUser.getUserKey());
				insertData.setRealName(dbUser.getRealName());
				insertData.setDeptId("预留");
				insertData.setDeptName("预留");
				insertData.setProjectId(zjXmJxUserScoringLeader.getProjectId());
				insertData.setProjectName(zjXmJxUserScoringLeader.getProjectName());
				insertData.setLeaderId(leaderId);
				insertData.setLeaderName(leaderName);
				insertData.setCreateUserInfo(userKey, userName);
				insertList.add(insertData);
			}
			if (insertList.size() > 0) {
				// 根据userKey和projectId删除数据
				ZjXmJxUserScoringLeader userScoringLeader = new ZjXmJxUserScoringLeader();
				userScoringLeader.setModifyUserInfo(userKey, userName);
				zjXmJxUserScoringLeaderMapper.batchDeleteUpdateZjXmJxUserScoringLeaderByDeptUser(insertList,
						userScoringLeader);
				flag = zjXmJxUserScoringLeaderMapper.bathInsertZjXmJxUserScoringLeader(insertList);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxUserScoringLeader);
		}
	}

	@Override
	public ResponseEntity manualAddZjXmJxUserScoringLeader(ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		int flag = 0;
		// 获取userList
		List<SysUser> userList = userService.getSysUserListByUserKeyList(null);
		if (userList.size() > 0) {
			List<ZjXmJxUserScoringLeader> insertList = Lists.newArrayList();
			for (SysUser dbSysUser : userList) {
				if (dbSysUser != null && StrUtil.isNotEmpty(dbSysUser.getExt3())
						&& StrUtil.isNotEmpty(dbSysUser.getExt4())) {
					SysDepartment sysDepartment = new SysDepartment();
					sysDepartment.setUserKey(dbSysUser.getUserKey());
					List<SysDepartment> sysDepartmentList = sysDepartmentService
							.getSysDepartmentListByUserkey(sysDepartment);
					if (sysDepartmentList.size() > 0) {
						Map<String, String> projectMap = new HashMap<String, String>();
						for (SysDepartment dbSysDepartment : sysDepartmentList) {
							String projectId = "";
							String projectName = "";
							if (dbSysDepartment != null && StrUtil.equals("4", dbSysDepartment.getDepartmentFlag())
									&& StrUtil.equals("项目", dbSysDepartment.getDepartmentFlagName())) {
								projectId = dbSysDepartment.getDepartmentId();
								projectName = dbSysDepartment.getDepartmentName();
							} else if (dbSysDepartment != null && StrUtil.isNotEmpty(dbSysDepartment.getProjectId())
									&& StrUtil.isNotEmpty(dbSysDepartment.getProjectName())) {
								projectId = dbSysDepartment.getProjectId();
								projectName = dbSysDepartment.getProjectName();
							}
							if (!projectMap.containsKey(projectId)) {
								projectMap.put(projectId, projectName);
							}
						}
						if (projectMap.size() > 0) {
							for (Map.Entry<String, String> entry : projectMap.entrySet()) {
								ZjXmJxUserScoringLeader insertData = new ZjXmJxUserScoringLeader();
								insertData.setScoringLeaderId(UuidUtil.generate());
								insertData.setUserKey(dbSysUser.getUserKey());
								insertData.setRealName(dbSysUser.getRealName());
								insertData.setDeptId("预留");
								insertData.setDeptName("预留");
								insertData.setProjectId(entry.getKey());
								insertData.setProjectName(entry.getValue());
								insertData.setLeaderId(dbSysUser.getExt3());
								insertData.setLeaderName(dbSysUser.getExt4());
								insertData.setCreateUserInfo("手动迁移", "手动迁移");
								insertList.add(insertData);
							}
						}
					}
				}
			}
			if (insertList.size() > 0) {
				// 根据userKey和projectId删除数据
				ZjXmJxUserScoringLeader userScoringLeader = new ZjXmJxUserScoringLeader();
				userScoringLeader.setModifyUserInfo("手动迁移", "手动迁移");
				zjXmJxUserScoringLeaderMapper.batchDeleteUpdateZjXmJxUserScoringLeaderByDeptUser(insertList,
						userScoringLeader);
				flag = zjXmJxUserScoringLeaderMapper.bathInsertZjXmJxUserScoringLeader(insertList);
			}
		}
		return repEntity.ok("成功迁移数据量为： " + flag);
	}
}
