package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.mybatis.dao.ZjXmJxAssessmentUserScoreMapper;
import com.apih5.mybatis.dao.ZjXmJxIndexLibraryMapper;
import com.apih5.mybatis.dao.ZjXmJxMonthlyAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmJxPeripheryScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxPrincipalScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxTaskScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxUserIndexLibraryMapper;
import com.apih5.mybatis.dao.ZjXmJxUserScoringLeaderMapper;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxIndexLibrary;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessment;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxUserIndexLibrary;
import com.apih5.mybatis.pojo.ZjXmJxUserScoringLeader;
import com.apih5.service.ZjXmJxMonthlyAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zjXmJxMonthlyAssessmentService")
public class ZjXmJxMonthlyAssessmentServiceImpl implements ZjXmJxMonthlyAssessmentService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxMonthlyAssessmentMapper zjXmJxMonthlyAssessmentMapper;
	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreMapper zjXmJxAssessmentUserScoreMapper;
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private SysDepartmentService sysDepartmentService;
	@Autowired(required = true)
	private ZjXmJxUserIndexLibraryMapper zjXmJxUserIndexLibraryMapper;
	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedMapper zjXmJxTaskScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxPeripheryScoreDetailedMapper zjXmJxPeripheryScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxPrincipalScoreDetailedMapper zjXmJxPrincipalScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxUserScoringLeaderMapper zjXmJxUserScoringLeaderMapper;

	@Override
	public ResponseEntity getZjXmJxMonthlyAssessmentListByCondition(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		if (zjXmJxMonthlyAssessment == null) {
			zjXmJxMonthlyAssessment = new ZjXmJxMonthlyAssessment();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxMonthlyAssessment.getPage(), zjXmJxMonthlyAssessment.getLimit());
		// 获取数据
		List<ZjXmJxMonthlyAssessment> zjXmJxMonthlyAssessmentList = zjXmJxMonthlyAssessmentMapper
				.selectByZjXmJxMonthlyAssessmentList(zjXmJxMonthlyAssessment);
		// 得到分页信息
		PageInfo<ZjXmJxMonthlyAssessment> p = new PageInfo<>(zjXmJxMonthlyAssessmentList);

		return repEntity.okList(zjXmJxMonthlyAssessmentList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxMonthlyAssessmentDetails(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		if (zjXmJxMonthlyAssessment == null) {
			zjXmJxMonthlyAssessment = new ZjXmJxMonthlyAssessment();
		}
		// 获取数据
		ZjXmJxMonthlyAssessment dbZjXmJxMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		
		// 获取当前月度考核中周边考核所有被考核人数据
        ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
        userScore.setAssessmentId(dbZjXmJxMonthlyAssessment.getAssessmentId());
        userScore.setAssessmentType("1");
        List<ZjXmJxAssessmentUserScore> userScoreList = zjXmJxAssessmentUserScoreMapper
                .selectByZjXmJxAssessmentUserScoreList(userScore);
        // 将userScoreList处理成JSONArray
        // JSONUtil.parseArray(userScoreList);
        if (userScoreList.size() > 0) {
            JSONArray deputyArray = JSONUtil.createArray();
            JSONArray leaderArray = JSONUtil.createArray();
            JSONArray employeeArray = JSONUtil.createArray();
            // JSONArray userArray = JSONUtil.createArray();
            for (ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore : userScoreList) {
                JSONObject jsonObj = JSONUtil.createObj();
                jsonObj.set("value", zjXmJxAssessmentUserScore.getAuditeeKey());
                jsonObj.set("label", zjXmJxAssessmentUserScore.getAuditeeName());
                jsonObj.set("score", zjXmJxAssessmentUserScore.getScore());
                jsonObj.set("valuePid", zjXmJxAssessmentUserScore.getAuditeeDeptId());
                // userArray.add(jsonObj);
                if (StrUtil.equals("0", zjXmJxAssessmentUserScore.getAuditeeType())) {
                    deputyArray.add(jsonObj);
                } else if (StrUtil.equals("1", zjXmJxAssessmentUserScore.getAuditeeType())) {
                    leaderArray.add(jsonObj);
                } else if (StrUtil.equals("2", zjXmJxAssessmentUserScore.getAuditeeType())) {
                    employeeArray.add(jsonObj);
                }
            }
            // dbMonthlyAssessment.setUserArray(userArray);
            dbZjXmJxMonthlyAssessment.setDeputyArray(deputyArray);
            dbZjXmJxMonthlyAssessment.setLeaderArray(leaderArray);
            dbZjXmJxMonthlyAssessment.setEmployeeArray(employeeArray);
        }
        
		// 数据存在
        return repEntity.ok(dbZjXmJxMonthlyAssessment);
//		if (dbZjXmJxMonthlyAssessment != null) {
//		} else {
//			return repEntity.layerMessage("no", "无数据！");
//		}
	}

	@Override
	public ResponseEntity saveZjXmJxMonthlyAssessment(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxMonthlyAssessment.setAssessmentId(UuidUtil.generate());
		zjXmJxMonthlyAssessment.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxMonthlyAssessmentMapper.insert(zjXmJxMonthlyAssessment);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxMonthlyAssessment);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxMonthlyAssessment(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxMonthlyAssessment dbzjXmJxMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		if (dbzjXmJxMonthlyAssessment != null) {
			// check同一项目同一年月只能新增一个考核
			if (!StrUtil.equals(DateUtil.format(zjXmJxMonthlyAssessment.getYearMonth(), "yyyy-MM"),
					DateUtil.format(dbzjXmJxMonthlyAssessment.getYearMonth(), "yyyy-MM"))) {
				ZjXmJxMonthlyAssessment check = new ZjXmJxMonthlyAssessment();
				check.setYearMonth(zjXmJxMonthlyAssessment.getYearMonth());
				check.setProjectId(zjXmJxMonthlyAssessment.getProjectId());
				int count = zjXmJxMonthlyAssessmentMapper.checkZjXmJxMonthlyAssessmentByCondition(check);
				if (count > 0) {
					return repEntity.layerMessage("no", "同一项目同一年月只能存在一个考核任务。");
				}
			}
			// 年月
			dbzjXmJxMonthlyAssessment.setYearMonth(zjXmJxMonthlyAssessment.getYearMonth());
			// 考核标题
			dbzjXmJxMonthlyAssessment.setAssessmentTitle(zjXmJxMonthlyAssessment.getAssessmentTitle());
//			// 备注
//			dbzjXmJxMonthlyAssessment.setRemarks(zjXmJxMonthlyAssessment.getRemarks());
//			// 排序
//			dbzjXmJxMonthlyAssessment.setSort(zjXmJxMonthlyAssessment.getSort());
			// 共通
			dbzjXmJxMonthlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentMapper.updateByPrimaryKey(dbzjXmJxMonthlyAssessment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxMonthlyAssessment);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity batchDeleteUpdateZjXmJxMonthlyAssessment(
			List<ZjXmJxMonthlyAssessment> zjXmJxMonthlyAssessmentList) throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String userId = TokenUtils.getUserId(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
//		if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//		    return repEntity.layerMessage("no", "无权限删除");
//		}
		int flag = 0;
		if (zjXmJxMonthlyAssessmentList != null && zjXmJxMonthlyAssessmentList.size() > 0) {
			ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment = new ZjXmJxMonthlyAssessment();
			zjXmJxMonthlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentMapper.batchDeleteUpdateZjXmJxMonthlyAssessment(zjXmJxMonthlyAssessmentList,
					zjXmJxMonthlyAssessment);
			if (flag != 0) {
				// 删除zj_xm_jx_assessment_user_score
				// 删除zj_xm_jx_task_score_detailed
				// 删除zj_xm_jx_task_score_detailed_record
				// 删除zj_xm_jx_periphery_score_detailed
				// 删除zj_xm_jx_principal_score_detailed
				// 删除zj_xm_jx_system_auto_score_detailed
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxMonthlyAssessmentList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity cascadeAddZjXmJxMonthlyAssessment(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String projectId = zjXmJxMonthlyAssessment.getProjectId();
		String projectName = zjXmJxMonthlyAssessment.getProjectName();
		// check同一项目同一年月只能新增一个考核
		ZjXmJxMonthlyAssessment check = new ZjXmJxMonthlyAssessment();
		check.setYearMonth(zjXmJxMonthlyAssessment.getYearMonth());
		check.setProjectId(projectId);
		int count = zjXmJxMonthlyAssessmentMapper.checkZjXmJxMonthlyAssessmentByCondition(check);
		if (count > 0) {
			return repEntity.layerMessage("no", "同一项目同一年月只能新增一个考核任务。");
		}
		// 获取项目经理和书记
		String reviewerPosition = "";
		String reviewerKey = "";
		String reviewerName = "";
		SysUser manager = new SysUser();
		manager.setDepartmentId(projectId);
		List<SysUser> managerList = userService.selectBySysUserByJobType(manager);
		if (managerList.size() == 1 && (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, managerList.get(0).getJobType())
				|| StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, managerList.get(0).getJobType()))) {
			reviewerPosition = "0";
			reviewerKey = managerList.get(0).getUserKey();
			reviewerName = managerList.get(0).getRealName();
		} else if (managerList.size() == 1
				&& StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, managerList.get(0).getJobType())) {
			reviewerPosition = "1";
			reviewerKey = managerList.get(0).getUserKey();
			reviewerName = managerList.get(0).getRealName();
		} else if (managerList.size() == 2) {
			reviewerPosition = "2";
			reviewerKey = managerList.get(0).getUserKey() + "," + managerList.get(1).getUserKey();
			reviewerName = managerList.get(0).getRealName() + "," + managerList.get(1).getRealName();
		} else {
			return repEntity.layerMessage("no", "未获取到当前项目的经理或书记信息。");
		}
		SysUser sysUser = new SysUser();
		sysUser.setCompanyId(projectId);
		// List<SysUser> userList = userService.getUserListByRoleAndCompanyId(sysUser);
		List<SysUser> userList = userService.getUserListByRoleAndCompanyIdXMJX(sysUser);
		if (userList.size() > 0) {
			// 过滤掉项目经理和书记
			for (int i = 0; i < userList.size(); i++) {
				if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, userList.get(i).getJobType())
						|| StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, userList.get(i).getJobType())
						|| StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, userList.get(i).getJobType())) {
					userList.remove(i);
					i--;
				}
				// check所有被评分者的评分领导不能为空
				else if (StrUtil.isEmpty(userList.get(i).getExt2()) || StrUtil.isEmpty(userList.get(i).getExt3())
						|| StrUtil.isEmpty(userList.get(i).getExt4())) {
					return repEntity.layerMessage("no", "该项目有员工还未设置岗位或评分领导。");
				}
			}
		}
		zjXmJxMonthlyAssessment.setAssessmentId(UuidUtil.generate());
		zjXmJxMonthlyAssessment.setNoticeStatus("0");
		// 周边考核权重获取自动上个月份的输入值
		// 获取数据库最新数据
		ZjXmJxMonthlyAssessment dbNewest = zjXmJxMonthlyAssessmentMapper
				.getZjXmJxMonthlyAssessmentNewest(new ZjXmJxMonthlyAssessment());
		if (dbNewest != null) {
			// 项目副职同级权重
			zjXmJxMonthlyAssessment.setDeputyPeerWeight(dbNewest.getDeputyPeerWeight());
			// 项目副职下级权重
			zjXmJxMonthlyAssessment.setDeputySubordinateWeight(dbNewest.getDeputySubordinateWeight());
			// 部门负责人上级权重
			zjXmJxMonthlyAssessment.setLeaderSuperiorWeight(dbNewest.getLeaderSuperiorWeight());
			// 部门负责人同级权重
			zjXmJxMonthlyAssessment.setLeaderPeerWeight(dbNewest.getLeaderPeerWeight());
			// 部门负责人下级权重
			zjXmJxMonthlyAssessment.setLeaderSubordinateWeight(dbNewest.getLeaderSubordinateWeight());
			// 普通员工上级权重
			zjXmJxMonthlyAssessment.setEmployeeSuperiorWeight(dbNewest.getEmployeeSuperiorWeight());
			// 普通员工同级权重
			zjXmJxMonthlyAssessment.setEmployeePeerWeight(dbNewest.getEmployeePeerWeight());
		}
		zjXmJxMonthlyAssessment.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxMonthlyAssessmentMapper.insert(zjXmJxMonthlyAssessment);
		if (flag != 0) {
			// 新增该项目下所有人的任务考核和项目正职评分的得分表数据
		    List<ZjXmJxAssessmentUserScore> peripheryInsertList = Lists.newArrayList();
			List<ZjXmJxAssessmentUserScore> taskInsertList = Lists.newArrayList();
			List<ZjXmJxAssessmentUserScore> principalInsertList = Lists.newArrayList();
			for (SysUser user : userList) {
				// 查部门getSysDepartmentByPrimaryKey
				String deptId = "";
				String deptName = "";
				SysDepartment sysDepartment = new SysDepartment();
				sysDepartment.setDepartmentId(projectId);
				sysDepartment.setUserKey(user.getUserKey());
				SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentProDept(sysDepartment);
				if (dbSysDepartment != null) {
					deptId = dbSysDepartment.getDepartmentId();
					deptName = dbSysDepartment.getDepartmentName();
				}
				if (StrUtil.isEmpty(projectId) || StrUtil.isEmpty(projectName) || StrUtil.isEmpty(deptId)
						|| StrUtil.isEmpty(deptName)) {
					return repEntity.layerMessage("no", "项目或部门信息不能为空。");
				}
				ZjXmJxAssessmentUserScore taskInsertData = new ZjXmJxAssessmentUserScore();
				taskInsertData.setScoreId(UuidUtil.generate());
				taskInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
				taskInsertData.setAuditeeKey(user.getUserKey());
				taskInsertData.setAuditeeName(user.getRealName());
				taskInsertData.setAuditeeIdNumber(user.getIdentityCard());
				taskInsertData.setAuditeePosition(user.getJobType());
				taskInsertData.setAuditeeRankType(user.getExt5());
				taskInsertData.setAuditeeLastType(user.getExt6());
				taskInsertData.setAuditeeUserType(user.getExt9());
				taskInsertData.setAuditeeType(user.getExt2());
				taskInsertData.setAuditeeDeptId(deptId);
				taskInsertData.setAuditeeDeptName(deptName);
				taskInsertData.setAuditeeProId(projectId);
				taskInsertData.setAuditeeProName(projectName);
				taskInsertData.setAssessmentType("0");
				taskInsertData.setConfirmStatus("0");
				taskInsertData.setReviewerKey(user.getExt3());
				taskInsertData.setReviewerName(user.getExt4());
				taskInsertData.setAuditStatus("0");
				taskInsertData.setCreateUserInfo(userKey, realName);
				taskInsertList.add(taskInsertData);
				ZjXmJxAssessmentUserScore principalInsertData = new ZjXmJxAssessmentUserScore();
				principalInsertData.setScoreId(UuidUtil.generate());
				principalInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
				principalInsertData.setAuditeeKey(user.getUserKey());
				principalInsertData.setAuditeeName(user.getRealName());
				principalInsertData.setAuditeeIdNumber(user.getIdentityCard());
				principalInsertData.setAuditeePosition(user.getJobType());
				principalInsertData.setAuditeeRankType(user.getExt5());
				principalInsertData.setAuditeeLastType(user.getExt6());
				principalInsertData.setAuditeeUserType(user.getExt9());
				principalInsertData.setAuditeeType(user.getExt2());
				principalInsertData.setAuditeeDeptId(deptId);
				principalInsertData.setAuditeeDeptName(deptName);
				principalInsertData.setAuditeeProId(projectId);
				principalInsertData.setAuditeeProName(projectName);
				principalInsertData.setAssessmentType("2");
				principalInsertData.setConfirmStatus("0");
				principalInsertData.setReviewerPosition(reviewerPosition);
				principalInsertData.setReviewerKey(reviewerKey);
				principalInsertData.setReviewerName(reviewerName);
				principalInsertData.setAuditStatus("0");
				principalInsertData.setCreateUserInfo(userKey, realName);
				principalInsertList.add(principalInsertData);
				// 周边
				ZjXmJxAssessmentUserScore peripheryInsertData = new ZjXmJxAssessmentUserScore();
                peripheryInsertData.setScoreId(UuidUtil.generate());
                peripheryInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
                peripheryInsertData.setAuditeeKey(user.getUserKey());
                peripheryInsertData.setAuditeeName(user.getRealName());
                peripheryInsertData.setAuditeeIdNumber(user.getIdentityCard());
                peripheryInsertData.setAuditeePosition(user.getJobType());
                peripheryInsertData.setAuditeeRankType(user.getExt5());
                peripheryInsertData.setAuditeeLastType(user.getExt6());
                peripheryInsertData.setAuditeeUserType(user.getExt9());
                peripheryInsertData.setAuditeeType(user.getExt2());
                peripheryInsertData.setAuditeeDeptId(deptId);
                peripheryInsertData.setAuditeeDeptName(deptName);
                peripheryInsertData.setAuditeeProId(projectId);
                peripheryInsertData.setAuditeeProName(projectName);
                peripheryInsertData.setAssessmentType("1");
                peripheryInsertData.setConfirmStatus("0");
                reviewerKey = "";
                reviewerName = "";
                for (SysUser user2 : userList) {
                    if (!StrUtil.equals(user.getUserKey(), user2.getUserKey())) {
                        reviewerKey = reviewerKey + user2.getUserKey() + ",";
                        reviewerName = reviewerName + user2.getRealName() + ",";
                    }
                }
                reviewerKey = reviewerKey.substring(0, reviewerKey.lastIndexOf(","));
                reviewerName = reviewerName.substring(0, reviewerName.lastIndexOf(","));
                peripheryInsertData.setReviewerKey(reviewerKey);
                peripheryInsertData.setReviewerName(reviewerName);
                peripheryInsertData.setAuditStatus("0");
                peripheryInsertData.setCreateUserInfo(userKey, realName);
                peripheryInsertList.add(peripheryInsertData);
			}
			// 新增该项目下所有人的任务考核得分表数据
			flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(taskInsertList);
			// 新增该项目下所有人的项目正职评分得分表数据
			flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(principalInsertList);
		    // 新增该项目下所有人的周边考核的得分表数据
            flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(peripheryInsertList);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxMonthlyAssessment);
		}
	}

	@Override
	public ResponseEntity getZjXmJxMonthlyAssessmentTaskDetails(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmJxMonthlyAssessment dbMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		if (dbMonthlyAssessment != null) {
			// 获取当前月度考核中任务考核所有被考核人数据
			ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
			userScore.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			userScore.setAssessmentType("0");
			List<ZjXmJxAssessmentUserScore> userScoreList = zjXmJxAssessmentUserScoreMapper
					.selectByZjXmJxAssessmentUserScoreList(userScore);
			// 将userScoreList处理成JSONArray
			// JSONUtil.parseArray(userScoreList);
			if (userScoreList.size() > 0) {
				JSONArray deputyArray = JSONUtil.createArray();
				JSONArray leaderArray = JSONUtil.createArray();
				JSONArray employeeArray = JSONUtil.createArray();
				// JSONArray userArray = JSONUtil.createArray();
				for (ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore : userScoreList) {
					JSONObject jsonObj = JSONUtil.createObj();
					jsonObj.set("value", zjXmJxAssessmentUserScore.getAuditeeKey());
					jsonObj.set("label", zjXmJxAssessmentUserScore.getAuditeeName());
					jsonObj.set("score", zjXmJxAssessmentUserScore.getScore());
					jsonObj.set("valuePid", zjXmJxAssessmentUserScore.getAuditeeDeptId());
					// userArray.add(jsonObj);
					if (StrUtil.equals("0", zjXmJxAssessmentUserScore.getAuditeeType())) {
						deputyArray.add(jsonObj);
					} else if (StrUtil.equals("1", zjXmJxAssessmentUserScore.getAuditeeType())) {
						leaderArray.add(jsonObj);
					} else if (StrUtil.equals("2", zjXmJxAssessmentUserScore.getAuditeeType())) {
						employeeArray.add(jsonObj);
					}
				}
				// dbMonthlyAssessment.setUserArray(userArray);
				dbMonthlyAssessment.setDeputyArray(deputyArray);
				dbMonthlyAssessment.setLeaderArray(leaderArray);
				dbMonthlyAssessment.setEmployeeArray(employeeArray);
			}
		}
		return repEntity.ok(dbMonthlyAssessment);
	}

	@Override
	public ResponseEntity submitZjXmJxMonthlyAssessmentForTask(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		JSONArray userArray = JSONUtil.createArray();
		if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getDeputyArray() != null
				&& zjXmJxMonthlyAssessment.getDeputyArray().size() > 0) {
			userArray.addAll(zjXmJxMonthlyAssessment.getDeputyArray());
		}
		if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getLeaderArray() != null
				&& zjXmJxMonthlyAssessment.getLeaderArray().size() > 0) {
			userArray.addAll(zjXmJxMonthlyAssessment.getLeaderArray());
		}
		if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getEmployeeArray() != null
				&& zjXmJxMonthlyAssessment.getEmployeeArray().size() > 0) {
			userArray.addAll(zjXmJxMonthlyAssessment.getEmployeeArray());
		}
		// 每次提交都是修改任务考核
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxMonthlyAssessment dbMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		if (dbMonthlyAssessment != null) {
			dbMonthlyAssessment.setTaskRemarks(zjXmJxMonthlyAssessment.getTaskRemarks());
			dbMonthlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentMapper.updateByPrimaryKey(dbMonthlyAssessment);
			// 删除当前月度考核中任务考核所有被考核人信息
			ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
			userScore.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			userScore.setAssessmentType("0");
			zjXmJxAssessmentUserScoreMapper.deleteZjXmJxAssessmentUserScoreByCondition(userScore);
			// 再新增传过来的所有被考核人信息
			if (userArray != null && userArray.size() > 0) {
				// JSONArray userArray = zjXmJxMonthlyAssessment.getUserArray();
				// 新增该项目下所有人的任务考核的得分表数据
				List<ZjXmJxAssessmentUserScore> taskInsertList = Lists.newArrayList();
				for (int i = 0; i < userArray.size(); i++) {
					// 查部门
					String deptName = "";
					SysDepartment dbSysDepartment = sysDepartmentService
							.getSysDepartmentByPrimaryKey(userArray.getJSONObject(i).getStr("valuePid"));
					if (dbSysDepartment != null) {
						deptName = dbSysDepartment.getDepartmentName();
					}
					if (StrUtil.isEmpty(deptName) || StrUtil.isEmpty(zjXmJxMonthlyAssessment.getProjectId())
							|| StrUtil.isEmpty(zjXmJxMonthlyAssessment.getProjectName())) {
						return repEntity.layerMessage("no", "项目或部门信息不能为空。");
					}
					ZjXmJxAssessmentUserScore taskInsertData = new ZjXmJxAssessmentUserScore();
					taskInsertData.setScoreId(UuidUtil.generate());
					taskInsertData.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
					taskInsertData.setAuditeeKey(userArray.getJSONObject(i).getStr("value"));
					taskInsertData.setAuditeeName(userArray.getJSONObject(i).getStr("label"));
					// 获取评分领导ext3\ext4
					ZjXmJxUserScoringLeader zjXmJxUserScoringLeader = new ZjXmJxUserScoringLeader();
					zjXmJxUserScoringLeader.setProjectId(dbMonthlyAssessment.getProjectId());
					zjXmJxUserScoringLeader.setUserKey(userArray.getJSONObject(i).getStr("value"));
					List<ZjXmJxUserScoringLeader> zjXmJxUserScoringLeaderList = zjXmJxUserScoringLeaderMapper.selectByZjXmJxUserScoringLeaderList(zjXmJxUserScoringLeader);
					if(zjXmJxUserScoringLeaderList == null || zjXmJxUserScoringLeaderList.size()==0) {
					    String userName = userArray.getJSONObject(i).getStr("value");
					    return repEntity.layerMessage("no", userName + "的指标库评分领导设置有问题!");
					}
					ZjXmJxUserScoringLeader dbZjXmJxUserScoringLeader = zjXmJxUserScoringLeaderList.get(0);
					SysUser checkUser = userService.getSysUserByUserKey(dbZjXmJxUserScoringLeader.getLeaderId());
					if(checkUser == null) {
					    return repEntity.layerMessage("no", "评分领导[" + dbZjXmJxUserScoringLeader.getLeaderName() + "]可能被删除，请重新设置指标库后，再发起！");
					}
					
					SysUser dbUser = userService.getSysUserByUserKey(userArray.getJSONObject(i).getStr("value"));
					taskInsertData.setAuditeeIdNumber(dbUser.getIdentityCard());
					taskInsertData.setAuditeePosition(dbUser.getJobType());
					taskInsertData.setAuditeeRankType(dbUser.getExt5());
					taskInsertData.setAuditeeLastType(dbUser.getExt6());
					taskInsertData.setAuditeeUserType(dbUser.getExt9());
					taskInsertData.setAuditeeType(dbUser != null ? dbUser.getExt2() : "");
					taskInsertData.setAuditeeDeptId(userArray.getJSONObject(i).getStr("valuePid"));
					taskInsertData.setAuditeeDeptName(deptName);
					taskInsertData.setAuditeeProId(zjXmJxMonthlyAssessment.getProjectId());
					taskInsertData.setAuditeeProName(zjXmJxMonthlyAssessment.getProjectName());
					taskInsertData.setAssessmentType("0");
					taskInsertData.setConfirmStatus("0");
					taskInsertData.setReviewerKey(dbZjXmJxUserScoringLeader.getLeaderId());
					taskInsertData.setReviewerName(dbZjXmJxUserScoringLeader.getLeaderName());
					taskInsertData.setAuditStatus("0");
					taskInsertData.setCreateUserInfo(userKey, realName);
					taskInsertList.add(taskInsertData);
				}
				// 新增该项目下所有人的任务考核得分表数据
				flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(taskInsertList);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxMonthlyAssessment);
		}
	}

	@Override
	public ResponseEntity submitZjXmJxMonthlyAssessmentForPeriphery(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// check同一类型权重和为100
		BigDecimal sum1 = CalcUtils.calcAdd(new BigDecimal(zjXmJxMonthlyAssessment.getDeputyPeerWeight()),
				new BigDecimal(zjXmJxMonthlyAssessment.getDeputySubordinateWeight()));
		BigDecimal sum2 = CalcUtils.calcAdd(
				CalcUtils.calcAdd(new BigDecimal(zjXmJxMonthlyAssessment.getLeaderSuperiorWeight()),
						new BigDecimal(zjXmJxMonthlyAssessment.getLeaderPeerWeight())),
				new BigDecimal(zjXmJxMonthlyAssessment.getLeaderSubordinateWeight()));
		BigDecimal sum3 = CalcUtils.calcAdd(new BigDecimal(zjXmJxMonthlyAssessment.getEmployeeSuperiorWeight()),
				new BigDecimal(zjXmJxMonthlyAssessment.getEmployeePeerWeight()));
		if (sum1.compareTo(new BigDecimal(100)) != 0 || sum2.compareTo(new BigDecimal(100)) != 0
				|| sum3.compareTo(new BigDecimal(100)) != 0) {
			return repEntity.layerMessage("no", "同一类型权重和必须为100。");
		}
		ZjXmJxMonthlyAssessment dbzjXmJxMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		if (dbzjXmJxMonthlyAssessment != null && StrUtil.isNotEmpty(dbzjXmJxMonthlyAssessment.getAssessmentId())) {
		    List<SysUser> userList = Lists.newArrayList();
		    // 数据库userList
		    SysUser sysUser = new SysUser();
			sysUser.setCompanyId(dbzjXmJxMonthlyAssessment.getProjectId());
			List<SysUser> userListDb = userService.getUserListByRoleAndCompanyIdXMJX(sysUser);
			
			// 页面userList
			JSONArray userArray = JSONUtil.createArray();
	        if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getDeputyArray() != null
	                && zjXmJxMonthlyAssessment.getDeputyArray().size() > 0) {
	            userArray.addAll(zjXmJxMonthlyAssessment.getDeputyArray());
	        }
	        if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getLeaderArray() != null
	                && zjXmJxMonthlyAssessment.getLeaderArray().size() > 0) {
	            userArray.addAll(zjXmJxMonthlyAssessment.getLeaderArray());
	        }
	        if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getEmployeeArray() != null
	                && zjXmJxMonthlyAssessment.getEmployeeArray().size() > 0) {
	            userArray.addAll(zjXmJxMonthlyAssessment.getEmployeeArray());
	        }
	        
			// 数据库与页面比较，重新组合新的userList
	        for (Iterator<Object> iterator = userArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObject = (JSONObject)iterator.next();
                for (SysUser user : userListDb) {
                    if(StrUtil.equals(jsonObject.getStr("value"), user.getUserKey())) {
                        userList.add(user);
                    }
                }
            }
			
			// 获取该项目下是否缺失职位，副职、部门领导、普通员工
			int deputyNum = 0;
			int leaderNum = 0;
			int employeeNum = 0;
			if (userList.size() > 0) {
				for (SysUser dbUser : userList) {
					if (dbUser != null && StrUtil.equals("0", dbUser.getExt2())) {
						deputyNum++;
					} else if (dbUser != null && StrUtil.equals("1", dbUser.getExt2())) {
						leaderNum++;
					} else if (dbUser != null && StrUtil.equals("2", dbUser.getExt2())) {
						employeeNum++;
					}
				}
			}
			if (deputyNum == 0 && zjXmJxMonthlyAssessment.getLeaderSuperiorWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下无副职,部门部长上级权重必须为0。");
			}
			if (employeeNum == 0 && zjXmJxMonthlyAssessment.getLeaderSubordinateWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下无普通员工,部门部长下级权重必须为0。");
			}
			if (deputyNum == 0 && leaderNum == 0 && zjXmJxMonthlyAssessment.getEmployeeSuperiorWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下无副职和部门部长,普通员工上级权重必须为0。");
			}
			if (leaderNum == 0 && employeeNum == 0 && zjXmJxMonthlyAssessment.getDeputySubordinateWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下无部门部长和普通员工,副职下级权重必须为0。");
			}
			if (deputyNum == 1 && zjXmJxMonthlyAssessment.getDeputyPeerWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下副职仅有一人,副职同级权重必须为0。");
			}
			if (leaderNum == 1 && zjXmJxMonthlyAssessment.getLeaderPeerWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下部门部长仅有一人,部门部长同级权重必须为0。");
			}
			if (employeeNum == 1 && zjXmJxMonthlyAssessment.getEmployeePeerWeight() != 0) {
				return repEntity.layerMessage("no", "该项目下普通员工仅有一人,普通员工同级权重必须为0。");
			}
			// 周边考核状态
			// dbzjXmJxMonthlyAssessment.setPeripheryStatus(zjXmJxMonthlyAssessment.getPeripheryStatus());
			// 周边考核备注
			dbzjXmJxMonthlyAssessment.setPeripheryRemarks(zjXmJxMonthlyAssessment.getPeripheryRemarks());
			// 项目副职同级权重
			dbzjXmJxMonthlyAssessment.setDeputyPeerWeight(zjXmJxMonthlyAssessment.getDeputyPeerWeight());
			// 项目副职下级权重
			dbzjXmJxMonthlyAssessment.setDeputySubordinateWeight(zjXmJxMonthlyAssessment.getDeputySubordinateWeight());
			// 部门负责人上级权重
			dbzjXmJxMonthlyAssessment.setLeaderSuperiorWeight(zjXmJxMonthlyAssessment.getLeaderSuperiorWeight());
			// 部门负责人同级权重
			dbzjXmJxMonthlyAssessment.setLeaderPeerWeight(zjXmJxMonthlyAssessment.getLeaderPeerWeight());
			// 部门负责人下级权重
			dbzjXmJxMonthlyAssessment.setLeaderSubordinateWeight(zjXmJxMonthlyAssessment.getLeaderSubordinateWeight());
			// 普通员工上级权重
			dbzjXmJxMonthlyAssessment.setEmployeeSuperiorWeight(zjXmJxMonthlyAssessment.getEmployeeSuperiorWeight());
			// 普通员工同级权重
			dbzjXmJxMonthlyAssessment.setEmployeePeerWeight(zjXmJxMonthlyAssessment.getEmployeePeerWeight());
			// 共通
			dbzjXmJxMonthlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentMapper.updateByPrimaryKey(dbzjXmJxMonthlyAssessment);
			
			
	        // 删除当前月度考核中周边考核所有被考核人信息
            ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
            userScore.setAssessmentId(dbzjXmJxMonthlyAssessment.getAssessmentId());
            userScore.setAssessmentType("1");
            zjXmJxAssessmentUserScoreMapper.deleteZjXmJxAssessmentUserScoreByCondition(userScore);
			
			// 3、创建周边考核得分表
            // 新增该项目下所有人的周边考核的得分表数据
			List<ZjXmJxAssessmentUserScore> peripheryInsertList = Lists.newArrayList();
            // 过滤掉项目经理和书记
            for (int i = 0; i < userList.size(); i++) {
                if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, userList.get(i).getJobType())
                        || StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, userList.get(i).getJobType())
                        || StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, userList.get(i).getJobType())) {
                    userList.remove(i);
                    i--;
                }
            }
			
			for (SysUser user : userList) {
                String deptId = "";
                String deptName = "";
                String projectName = zjXmJxMonthlyAssessment.getProjectName();
                String projectId = zjXmJxMonthlyAssessment.getProjectId();
                        
                // 查部门
                SysDepartment sysDepartment = new SysDepartment();
                sysDepartment.setDepartmentId(projectId);
                sysDepartment.setUserKey(user.getUserKey());
                SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentProDept(sysDepartment);
                if (dbSysDepartment != null) {
                    deptId = dbSysDepartment.getDepartmentId();
                    deptName = dbSysDepartment.getDepartmentName();
                }
                if (StrUtil.isEmpty(projectId) || StrUtil.isEmpty(projectName) || StrUtil.isEmpty(deptId)
                        || StrUtil.isEmpty(deptName)) {
                    return repEntity.layerMessage("no", "项目或部门信息不能为空。");
                }
                ZjXmJxAssessmentUserScore peripheryInsertData = new ZjXmJxAssessmentUserScore();
                peripheryInsertData.setScoreId(UuidUtil.generate());
                peripheryInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
                peripheryInsertData.setAuditeeKey(user.getUserKey());
                peripheryInsertData.setAuditeeName(user.getRealName());
                peripheryInsertData.setAuditeeIdNumber(user.getIdentityCard());
                peripheryInsertData.setAuditeePosition(user.getJobType());
                peripheryInsertData.setAuditeeRankType(user.getExt5());
                peripheryInsertData.setAuditeeLastType(user.getExt6());
                peripheryInsertData.setAuditeeUserType(user.getExt9());
                peripheryInsertData.setAuditeeType(user.getExt2());
                peripheryInsertData.setAuditeeDeptId(deptId);
                peripheryInsertData.setAuditeeDeptName(deptName);
                peripheryInsertData.setAuditeeProId(projectId);
                peripheryInsertData.setAuditeeProName(projectName);
                peripheryInsertData.setAssessmentType("1");
                peripheryInsertData.setConfirmStatus("0");
                String reviewerKey = "";
                String reviewerName = "";
                for (SysUser user2 : userList) {
                    if (!StrUtil.equals(user.getUserKey(), user2.getUserKey())) {
                        reviewerKey = reviewerKey + user2.getUserKey() + ",";
                        reviewerName = reviewerName + user2.getRealName() + ",";
                    }
                }
                reviewerKey = reviewerKey.substring(0, reviewerKey.lastIndexOf(","));
                reviewerName = reviewerName.substring(0, reviewerName.lastIndexOf(","));
                peripheryInsertData.setReviewerKey(reviewerKey);
                peripheryInsertData.setReviewerName(reviewerName);
                peripheryInsertData.setAuditStatus("0");
                peripheryInsertData.setCreateUserInfo(userKey, realName);
                peripheryInsertList.add(peripheryInsertData);
            }
            // 新增该项目下所有人的周边考核的得分表数据
            flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(peripheryInsertList);
            if (flag == 0) {
//                throw new Apih5Exception("新增周边考核得分失败。");
            }
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxMonthlyAssessment);
		}
	}

	@Override
	public ResponseEntity getZjXmJxMonthlyAssessmentPrincipalDetails(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmJxMonthlyAssessment dbMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		if (dbMonthlyAssessment != null) {
			// 获取当前月度考核中正职评分的所有被考核人数据
			ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
			userScore.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			userScore.setAssessmentType("2");
			List<ZjXmJxAssessmentUserScore> userScoreList = zjXmJxAssessmentUserScoreMapper
					.selectByZjXmJxAssessmentUserScoreList(userScore);
			// 将userScoreList处理成JSONArray
			// JSONUtil.parseArray(userScoreList);
			if (userScoreList.size() > 0) {
				JSONArray deputyArray = JSONUtil.createArray();
				JSONArray leaderArray = JSONUtil.createArray();
				JSONArray employeeArray = JSONUtil.createArray();
				// JSONArray userArray = JSONUtil.createArray();
				for (ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore : userScoreList) {
					JSONObject jsonObj = JSONUtil.createObj();
					jsonObj.set("value", zjXmJxAssessmentUserScore.getAuditeeKey());
					jsonObj.set("label", zjXmJxAssessmentUserScore.getAuditeeName());
					jsonObj.set("score", zjXmJxAssessmentUserScore.getScore());
					jsonObj.set("valuePid", zjXmJxAssessmentUserScore.getAuditeeDeptId());
					// userArray.add(jsonObj);
					if (StrUtil.equals("0", zjXmJxAssessmentUserScore.getAuditeeType())) {
						deputyArray.add(jsonObj);
					} else if (StrUtil.equals("1", zjXmJxAssessmentUserScore.getAuditeeType())) {
						leaderArray.add(jsonObj);
					} else if (StrUtil.equals("2", zjXmJxAssessmentUserScore.getAuditeeType())) {
						employeeArray.add(jsonObj);
					}
				}
				// dbMonthlyAssessment.setUserArray(userArray);
				dbMonthlyAssessment.setDeputyArray(deputyArray);
				dbMonthlyAssessment.setLeaderArray(leaderArray);
				dbMonthlyAssessment.setEmployeeArray(employeeArray);
			}
		}
		return repEntity.ok(dbMonthlyAssessment);
	}

	@Override
	public ResponseEntity submitZjXmJxMonthlyAssessmentForPrincipal(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		JSONArray userArray = JSONUtil.createArray();
		if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getDeputyArray() != null
				&& zjXmJxMonthlyAssessment.getDeputyArray().size() > 0) {
			userArray.addAll(zjXmJxMonthlyAssessment.getDeputyArray());
		}
		if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getLeaderArray() != null
				&& zjXmJxMonthlyAssessment.getLeaderArray().size() > 0) {
			userArray.addAll(zjXmJxMonthlyAssessment.getLeaderArray());
		}
		if (zjXmJxMonthlyAssessment != null && zjXmJxMonthlyAssessment.getEmployeeArray() != null
				&& zjXmJxMonthlyAssessment.getEmployeeArray().size() > 0) {
			userArray.addAll(zjXmJxMonthlyAssessment.getEmployeeArray());
		}
		// 获取项目经理和书记
		String reviewerPosition = "";
		String reviewerKey = "";
		String reviewerName = "";
		SysUser manager = new SysUser();
		manager.setDepartmentId(zjXmJxMonthlyAssessment.getProjectId());
		List<SysUser> managerList = userService.selectBySysUserByJobType(manager);
		if (managerList.size() == 1 && (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, managerList.get(0).getJobType())
				|| StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, managerList.get(0).getJobType()))) {
			reviewerPosition = "0";
			reviewerKey = managerList.get(0).getUserKey();
			reviewerName = managerList.get(0).getRealName();
		} else if (managerList.size() == 1
				&& StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, managerList.get(0).getJobType())) {
			reviewerPosition = "1";
			reviewerKey = managerList.get(0).getUserKey();
			reviewerName = managerList.get(0).getRealName();
		} else if (managerList.size() == 2) {
			reviewerPosition = "2";
			reviewerKey = managerList.get(0).getUserKey() + "," + managerList.get(1).getUserKey();
			reviewerName = managerList.get(0).getRealName() + "," + managerList.get(1).getRealName();
		} else {
			return repEntity.layerMessage("no", "未获取到当前项目的经理或书记信息。");
		}
		// 每次提交都是修改项目正职评分
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxMonthlyAssessment dbMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		if (dbMonthlyAssessment != null) {
			dbMonthlyAssessment.setPrincipalRemarks(zjXmJxMonthlyAssessment.getPrincipalRemarks());
			dbMonthlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentMapper.updateByPrimaryKey(dbMonthlyAssessment);
			// 删除当前月度考核中正职评分的所有被考核人信息
			ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
			userScore.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			userScore.setAssessmentType("2");
			zjXmJxAssessmentUserScoreMapper.deleteZjXmJxAssessmentUserScoreByCondition(userScore);
			// 再新增传过来的所有被考核人信息
			if (userArray != null && userArray.size() > 0) {
				// JSONArray userArray = zjXmJxMonthlyAssessment.getUserArray();
				// 新增该项目下所有人的正职评分的得分表数据
				List<ZjXmJxAssessmentUserScore> principalInsertList = Lists.newArrayList();
				for (int i = 0; i < userArray.size(); i++) {
					// 查部门
					String deptName = "";
					SysDepartment dbSysDepartment = sysDepartmentService
							.getSysDepartmentByPrimaryKey(userArray.getJSONObject(i).getStr("valuePid"));
					if (dbSysDepartment != null) {
						deptName = dbSysDepartment.getDepartmentName();
					}
					if (StrUtil.isEmpty(deptName) || StrUtil.isEmpty(zjXmJxMonthlyAssessment.getProjectId())
							|| StrUtil.isEmpty(zjXmJxMonthlyAssessment.getProjectName())) {
						return repEntity.layerMessage("no", "项目或部门信息不能为空。");
					}
					ZjXmJxAssessmentUserScore principalInsertData = new ZjXmJxAssessmentUserScore();
					principalInsertData.setScoreId(UuidUtil.generate());
					principalInsertData.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
					principalInsertData.setAuditeeKey(userArray.getJSONObject(i).getStr("value"));
					principalInsertData.setAuditeeName(userArray.getJSONObject(i).getStr("label"));
					SysUser dbUser = userService.getSysUserByUserKey(userArray.getJSONObject(i).getStr("value"));
					principalInsertData.setAuditeeIdNumber(dbUser.getIdentityCard());
					principalInsertData.setAuditeePosition(dbUser.getJobType());
					principalInsertData.setAuditeeRankType(dbUser.getExt5());
					principalInsertData.setAuditeeLastType(dbUser.getExt6());
					principalInsertData.setAuditeeUserType(dbUser.getExt9());
					principalInsertData.setAuditeeType(dbUser != null ? dbUser.getExt2() : "");
					principalInsertData.setAuditeeDeptId(userArray.getJSONObject(i).getStr("valuePid"));
					principalInsertData.setAuditeeDeptName(deptName);
					principalInsertData.setAuditeeProId(zjXmJxMonthlyAssessment.getProjectId());
					principalInsertData.setAuditeeProName(zjXmJxMonthlyAssessment.getProjectName());
					principalInsertData.setAssessmentType("2");
					principalInsertData.setConfirmStatus("0");
					// 调用接口获取经理或者书记得userKey及name
					principalInsertData.setReviewerPosition(reviewerPosition);
					principalInsertData.setReviewerKey(reviewerKey);
					principalInsertData.setReviewerName(reviewerName);
					principalInsertData.setAuditStatus("0");
					principalInsertData.setCreateUserInfo(userKey, realName);
					principalInsertList.add(principalInsertData);
				}
				// 新增该项目下所有人的项目正职评分得分表数据
				flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(principalInsertList);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxMonthlyAssessment);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity sendZjXmJxMonthlyAssessmentNotice(ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment)
			throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String projectId = zjXmJxMonthlyAssessment.getProjectId();
		String projectName = zjXmJxMonthlyAssessment.getProjectName();
		// 获取项目经理和书记
		SysUser manager = new SysUser();
		manager.setDepartmentId(projectId);
		List<SysUser> managerList = userService.selectBySysUserByJobType(manager);
		if (managerList.size() < 1) {
			return repEntity.layerMessage("no", "未获取到当前项目的经理或书记信息。");
		}
		int flag = 0;
		ZjXmJxMonthlyAssessment dbMonthlyAssessment = zjXmJxMonthlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessment.getAssessmentId());
		// check
		if (dbMonthlyAssessment == null || StrUtil.equals("1", dbMonthlyAssessment.getNoticeStatus())) {
			return repEntity.layerMessage("no", "该月度考核已发送过通知。");
		}
		if (dbMonthlyAssessment != null) {
			// 2、创建任务考核得分明细表
			// 获取任务考核得分表集合(获取人)
			ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
			userScore.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			userScore.setAssessmentType("0");
			List<ZjXmJxAssessmentUserScore> userScoreList = zjXmJxAssessmentUserScoreMapper
					.selectByZjXmJxAssessmentUserScoreList(userScore);
			// 获取该项目基础指标库集合(获取题库)
			ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary = new ZjXmJxUserIndexLibrary();
			zjXmJxUserIndexLibrary.setProjectId(dbMonthlyAssessment.getProjectId());
			List<ZjXmJxUserIndexLibrary> libraryList = zjXmJxUserIndexLibraryMapper
					.selectByZjXmJxUserIndexLibraryList(zjXmJxUserIndexLibrary);
			if (userScoreList.size() > 0 && libraryList.size() > 0) {
				List<ZjXmJxTaskScoreDetailed> insertDetailedList = Lists.newArrayList();
				for (ZjXmJxAssessmentUserScore dbUserScore : userScoreList) {
					Boolean checkStep = true;
					for (ZjXmJxUserIndexLibrary dbLibrary : libraryList) {
						// 根据人的userKey和所在部门找到人和题库的关系(创建得分明细表)
						if (StrUtil.equals(dbUserScore.getAuditeeKey(), dbLibrary.getUserKey())
								&& StrUtil.equals(dbUserScore.getAuditeeDeptId(), dbLibrary.getDeptId())) {
							checkStep = false;
							ZjXmJxTaskScoreDetailed insertScoreDetailed = new ZjXmJxTaskScoreDetailed();
							insertScoreDetailed.setDetailedId(UuidUtil.generate());
							insertScoreDetailed.setScoreId(dbUserScore.getScoreId());
							insertScoreDetailed.setLibraryId(dbLibrary.getLibraryId());
							insertScoreDetailed.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
							insertScoreDetailed.setAuditeeKey(dbUserScore.getAuditeeKey());
							insertScoreDetailed.setAuditeeName(dbUserScore.getAuditeeName());
							insertScoreDetailed.setAuditeeDeptId(dbUserScore.getAuditeeDeptId());
							insertScoreDetailed.setAuditeeDeptName(dbUserScore.getAuditeeDeptName());
							insertScoreDetailed.setAuditeeProId(dbUserScore.getAuditeeProId());
							insertScoreDetailed.setAuditeeProName(dbUserScore.getAuditeeProName());
							insertScoreDetailed.setReviewerKey(dbUserScore.getReviewerKey());
							insertScoreDetailed.setReviewerName(dbUserScore.getReviewerName());
							insertScoreDetailed.setHrUserKey(userKey);
							insertScoreDetailed.setHrName(realName);
							insertScoreDetailed.setCostDutyIndex(dbLibrary.getCostDutyIndex());
							insertScoreDetailed.setTargetValue(dbLibrary.getTargetValue());
							insertScoreDetailed.setScoringStandard(dbLibrary.getScoringStandard());
							insertScoreDetailed.setWeightValue(dbLibrary.getWeightValue());
							// insertScoreDetailed.setScore(new BigDecimal(0));
							insertScoreDetailed.setAppealStatus("0");
							insertScoreDetailed.setDataSources(dbLibrary.getDataSources());
							insertScoreDetailed.setIsMandatory(dbLibrary.getIsMandatory());
							insertScoreDetailed.setIsAutomaticScoring(dbLibrary.getIsAutomaticScoring());
							insertScoreDetailed.setCreateUserInfo(userKey, realName);
							insertDetailedList.add(insertScoreDetailed);
						}
					}
					if (checkStep) {
						return repEntity.layerMessage("no", "请先添加" + dbUserScore.getAuditeeName() + "的个人指标库");
					}
				}
				// 批量新增当前月度考核的任务考核得分明细表
				flag = zjXmJxTaskScoreDetailedMapper.batchInsertZjXmJxTaskScoreDetailed(insertDetailedList);
				if (flag == 0) {
					throw new Apih5Exception("新增任务考核得分明细失败。");
				}
			}
			// 注释当前，分别移动到创建、提交部分，第4步骤报错当前逻辑，因为分数的最后设置在发消息时处理
//			// 3、创建周边考核得分表
//			// 新增该项目下所有人的周边考核的得分表数据
//			List<ZjXmJxAssessmentUserScore> peripheryInsertList = Lists.newArrayList();
//			SysUser sysUser = new SysUser();
//			sysUser.setCompanyId(projectId);
//			List<SysUser> userList = userService.getUserListByRoleAndCompanyIdXMJX(sysUser);
//			// 过滤掉项目经理和书记
//			for (int i = 0; i < userList.size(); i++) {
//				if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, userList.get(i).getJobType())
//						|| StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, userList.get(i).getJobType())
//						|| StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, userList.get(i).getJobType())) {
//					userList.remove(i);
//					i--;
//				}
//			}
//			if (userList.size() > 0) {
//				for (SysUser user : userList) {
//					String deptId = "";
//					String deptName = "";
//					// 查部门
//					SysDepartment sysDepartment = new SysDepartment();
//					sysDepartment.setDepartmentId(projectId);
//					sysDepartment.setUserKey(user.getUserKey());
//					SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentProDept(sysDepartment);
//					if (dbSysDepartment != null) {
//						deptId = dbSysDepartment.getDepartmentId();
//						deptName = dbSysDepartment.getDepartmentName();
//					}
//					if (StrUtil.isEmpty(projectId) || StrUtil.isEmpty(projectName) || StrUtil.isEmpty(deptId)
//							|| StrUtil.isEmpty(deptName)) {
//						return repEntity.layerMessage("no", "项目或部门信息不能为空。");
//					}
//					ZjXmJxAssessmentUserScore peripheryInsertData = new ZjXmJxAssessmentUserScore();
//					peripheryInsertData.setScoreId(UuidUtil.generate());
//					peripheryInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
//					peripheryInsertData.setAuditeeKey(user.getUserKey());
//					peripheryInsertData.setAuditeeName(user.getRealName());
//					peripheryInsertData.setAuditeeIdNumber(user.getIdentityCard());
//					peripheryInsertData.setAuditeePosition(user.getJobType());
//					peripheryInsertData.setAuditeeRankType(user.getExt5());
//					peripheryInsertData.setAuditeeLastType(user.getExt6());
//					peripheryInsertData.setAuditeeUserType(user.getExt9());
//					peripheryInsertData.setAuditeeType(user.getExt2());
//					peripheryInsertData.setAuditeeDeptId(deptId);
//					peripheryInsertData.setAuditeeDeptName(deptName);
//					peripheryInsertData.setAuditeeProId(projectId);
//					peripheryInsertData.setAuditeeProName(projectName);
//					peripheryInsertData.setAssessmentType("1");
//					peripheryInsertData.setConfirmStatus("0");
//					String reviewerKey = "";
//					String reviewerName = "";
//					for (SysUser user2 : userList) {
//						if (!StrUtil.equals(user.getUserKey(), user2.getUserKey())) {
//							reviewerKey = reviewerKey + user2.getUserKey() + ",";
//							reviewerName = reviewerName + user2.getRealName() + ",";
//						}
//					}
//					reviewerKey = reviewerKey.substring(0, reviewerKey.lastIndexOf(","));
//					reviewerName = reviewerName.substring(0, reviewerName.lastIndexOf(","));
//					peripheryInsertData.setReviewerKey(reviewerKey);
//					peripheryInsertData.setReviewerName(reviewerName);
//					peripheryInsertData.setAuditStatus("0");
//					peripheryInsertData.setCreateUserInfo(userKey, realName);
//					peripheryInsertList.add(peripheryInsertData);
//				}
//				// 新增该项目下所有人的周边考核的得分表数据
//				flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(peripheryInsertList);
//				if (flag == 0) {
//					throw new Apih5Exception("新增周边考核得分失败。");
//				}
//				// 4、创建周边考核得分明细表
//				if (flag != 0) {
//					List<ZjXmJxPeripheryScoreDetailed> insertList = Lists.newArrayList();
//					for (ZjXmJxAssessmentUserScore dbUserScore : peripheryInsertList) {
//						for (SysUser user : userList) {
//							// 同一项目每个同事都给自己打分
//							if (!StrUtil.equals(dbUserScore.getAuditeeKey(), user.getUserKey())) {
//								ZjXmJxPeripheryScoreDetailed insertData = new ZjXmJxPeripheryScoreDetailed();
//								insertData.setDetailedId(UuidUtil.generate());
//								insertData.setScoreId(dbUserScore.getScoreId());
//								insertData.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
//								insertData.setAuditeeKey(dbUserScore.getAuditeeKey());
//								insertData.setAuditeeName(dbUserScore.getAuditeeName());
//								insertData.setAuditeeType(dbUserScore.getAuditeeType());
//								insertData.setAuditeeDeptId(dbUserScore.getAuditeeDeptId());
//								insertData.setAuditeeDeptName(dbUserScore.getAuditeeDeptName());
//								insertData.setAuditeeProId(dbUserScore.getAuditeeProId());
//								insertData.setAuditeeProName(dbUserScore.getAuditeeProName());
//								insertData.setReviewerKey(user.getUserKey());
//								insertData.setReviewerName(user.getRealName());
//								insertData.setHrUserKey(userKey);
//								insertData.setHrName(realName);
//								// insertData.setScore(new BigDecimal(0));
//								insertData.setReviewerType(user.getExt2());
//								insertData.setAuditStatus("0");
//								// 根据身份类型获取权重
//								JSONObject jsonObj = getWeightValue(dbUserScore.getAuditeeType(), user.getExt2(),
//										dbMonthlyAssessment);
//								Integer weightValue = jsonObj.getInt("weightValue");
//								String scoringType = jsonObj.getStr("scoringType");
//								insertData.setWeightValue(weightValue);
//								insertData.setScoringType(scoringType);
//								insertData.setCreateUserInfo(userKey, realName);
//								insertList.add(insertData);
//							}
//						}
//					}
//					// 批量新增周边考核得分明细表数据
//					flag = zjXmJxPeripheryScoreDetailedMapper.batchInsertZjXmJxPeripheryScoreDetailed(insertList);
//					if (flag == 0) {
//						throw new Apih5Exception("新增周边考核得分明细失败。");
//					}
//				}
//			}
			
	        // 3步骤（周边考核的权重更新处理）
            ZjXmJxAssessmentUserScore peripheryInsertData = new ZjXmJxAssessmentUserScore();
            peripheryInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
            peripheryInsertData.setAssessmentType("1");
            List<ZjXmJxAssessmentUserScore> peripheryInsertList = zjXmJxAssessmentUserScoreMapper.selectByZjXmJxAssessmentUserScoreList(peripheryInsertData);
            // 4、修改周边考核得分明细表（主要权重设置）
            if (flag != 0) {
                List<ZjXmJxPeripheryScoreDetailed> insertList = Lists.newArrayList();
                for (ZjXmJxAssessmentUserScore dbUserScore : peripheryInsertList) {
                    String[] userKeys = dbUserScore.getReviewerKey().split(",");
                    for (int i=0; i<userKeys.length; i++) {
                        SysUser user = userService.getSysUserByUserKey(userKeys[i]);
                        // 同一项目每个同事都给自己打分
                        if (!StrUtil.equals(dbUserScore.getAuditeeKey(), user.getUserKey())) {
                            ZjXmJxPeripheryScoreDetailed insertData = new ZjXmJxPeripheryScoreDetailed();
                            insertData.setDetailedId(UuidUtil.generate());
                            insertData.setScoreId(dbUserScore.getScoreId());
                            insertData.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
                            insertData.setAuditeeKey(dbUserScore.getAuditeeKey());
                            insertData.setAuditeeName(dbUserScore.getAuditeeName());
                            insertData.setAuditeeType(dbUserScore.getAuditeeType());
                            insertData.setAuditeeDeptId(dbUserScore.getAuditeeDeptId());
                            insertData.setAuditeeDeptName(dbUserScore.getAuditeeDeptName());
                            insertData.setAuditeeProId(dbUserScore.getAuditeeProId());
                            insertData.setAuditeeProName(dbUserScore.getAuditeeProName());
                            insertData.setReviewerKey(user.getUserKey());
                            insertData.setReviewerName(user.getRealName());
                            insertData.setHrUserKey(userKey);
                            insertData.setHrName(realName);
                            // insertData.setScore(new BigDecimal(0));
                            insertData.setReviewerType(user.getExt2());
                            insertData.setAuditStatus("0");
                            // 根据身份类型获取权重
                            JSONObject jsonObj = getWeightValue(dbUserScore.getAuditeeType(), user.getExt2(),
                                    dbMonthlyAssessment);
                            Integer weightValue = jsonObj.getInt("weightValue");
                            String scoringType = jsonObj.getStr("scoringType");
                            insertData.setWeightValue(weightValue);
                            insertData.setScoringType(scoringType);
                            insertData.setCreateUserInfo(userKey, realName);
                            insertList.add(insertData);
                        }
                    }
                }
                // 批量新增周边考核得分明细表数据
                flag = zjXmJxPeripheryScoreDetailedMapper.batchInsertZjXmJxPeripheryScoreDetailed(insertList);
                if (flag == 0) {
                    throw new Apih5Exception("新增周边考核得分明细失败。");
                }
            }
			
			// 5、创建正职评分明细表
			// 获取正职评分得分表集合(获取被评分者)
			ZjXmJxAssessmentUserScore principalScore = new ZjXmJxAssessmentUserScore();
			principalScore.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			principalScore.setAssessmentType("2");
			List<ZjXmJxAssessmentUserScore> principalScoreList = zjXmJxAssessmentUserScoreMapper
					.selectByZjXmJxAssessmentUserScoreList(principalScore);
			// 获取该项目的经理和书记(获取打分者)(接口获取)
			if (principalScoreList.size() > 0 && managerList.size() > 0) {
				List<ZjXmJxPrincipalScoreDetailed> insertDetailedList = Lists.newArrayList();
				for (ZjXmJxAssessmentUserScore dbPrincipalScoreList : principalScoreList) {
					for (SysUser dbUser : managerList) {
						ZjXmJxPrincipalScoreDetailed insertPrincipalDetailed = new ZjXmJxPrincipalScoreDetailed();
						insertPrincipalDetailed.setDetailedId(UuidUtil.generate());
						insertPrincipalDetailed.setScoreId(dbPrincipalScoreList.getScoreId());
						insertPrincipalDetailed.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
						insertPrincipalDetailed.setAuditeeKey(dbPrincipalScoreList.getAuditeeKey());
						insertPrincipalDetailed.setAuditeeName(dbPrincipalScoreList.getAuditeeName());
						insertPrincipalDetailed.setAuditeeType(dbPrincipalScoreList.getAuditeeType());
						insertPrincipalDetailed.setAuditeeDeptId(dbPrincipalScoreList.getAuditeeDeptId());
						insertPrincipalDetailed.setAuditeeDeptName(dbPrincipalScoreList.getAuditeeDeptName());
						insertPrincipalDetailed.setAuditeeProId(dbPrincipalScoreList.getAuditeeProId());
						insertPrincipalDetailed.setAuditeeProName(dbPrincipalScoreList.getAuditeeProName());
						insertPrincipalDetailed.setReviewerKey(dbUser.getUserKey());
						insertPrincipalDetailed.setReviewerName(dbUser.getRealName());
						insertPrincipalDetailed.setHrUserKey(userKey);
						insertPrincipalDetailed.setHrName(realName);
						// insertPrincipalDetailed.setScore(new BigDecimal(0));
						insertPrincipalDetailed.setReviewerType(dbUser.getExt2());
						// 这个字段存什么待定
						String reviewerPosition = "";
						if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, dbUser.getJobType())
								|| StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, dbUser.getJobType())) {
							reviewerPosition = "0";
						} else if (StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, dbUser.getJobType())) {
							reviewerPosition = "1";
						}
						insertPrincipalDetailed.setReviewerPosition(reviewerPosition);
						insertPrincipalDetailed.setAuditStatus("0");
						insertPrincipalDetailed.setCreateUserInfo(userKey, realName);
						insertDetailedList.add(insertPrincipalDetailed);
					}
				}
				// 批量新增当前月度考核的正职评分得分明细表
				flag = zjXmJxPrincipalScoreDetailedMapper.batchInsertZjXmJxPrincipalScoreDetailed(insertDetailedList);
				if (flag == 0) {
					throw new Apih5Exception("新增正职评分得分明细失败。");
				}
			}
			
			// 原步骤注释，user数据从其他模式获取所有用户数据，不在用user表获取
//			// 6、创建系统自动扣分表
//			// 新增该项目下所有人的系统扣分表数据
//			List<ZjXmJxAssessmentUserScore> systemInsertList = Lists.newArrayList();
//			SysUser sysUser2 = new SysUser();
//			sysUser2.setCompanyId(projectId);
//			List<SysUser> userList2 = userService.getUserListByRoleAndCompanyIdXMJX(sysUser2);
//			// 过滤掉项目经理和书记
//			for (int i = 0; i < userList2.size(); i++) {
//				if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, userList2.get(i).getJobType())
//						|| StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, userList2.get(i).getJobType())
//						|| StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, userList2.get(i).getJobType())) {
//					userList2.remove(i);
//					i--;
//				}
//			}
//			if (userList2.size() > 0) {
//				for (SysUser user : userList2) {
//					String deptId = "";
//					String deptName = "";
//					// 查部门
//					SysDepartment sysDepartment = new SysDepartment();
//					sysDepartment.setDepartmentId(projectId);
//					sysDepartment.setUserKey(user.getUserKey());
//					SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentProDept(sysDepartment);
//					if (dbSysDepartment != null) {
//						deptId = dbSysDepartment.getDepartmentId();
//						deptName = dbSysDepartment.getDepartmentName();
//					}
//					if (StrUtil.isEmpty(projectId) || StrUtil.isEmpty(projectName) || StrUtil.isEmpty(deptId)
//							|| StrUtil.isEmpty(deptName)) {
//						return repEntity.layerMessage("no", "项目或部门信息不能为空。");
//					}
//					ZjXmJxAssessmentUserScore systemInsertData = new ZjXmJxAssessmentUserScore();
//					systemInsertData.setScoreId(UuidUtil.generate());
//					systemInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
//					systemInsertData.setAuditeeKey(user.getUserKey());
//					systemInsertData.setAuditeeName(user.getRealName());
//					systemInsertData.setAuditeeIdNumber(user.getIdentityCard());
//					systemInsertData.setAuditeePosition(user.getJobType());
//					systemInsertData.setAuditeeRankType(user.getExt5());
//					systemInsertData.setAuditeeLastType(user.getExt6());
//					systemInsertData.setAuditeeUserType(user.getExt9());
//					systemInsertData.setAuditeeType(user.getExt2());
//					systemInsertData.setAuditeeDeptId(deptId);
//					systemInsertData.setAuditeeDeptName(deptName);
//					systemInsertData.setAuditeeProId(projectId);
//					systemInsertData.setAuditeeProName(projectName);
//					systemInsertData.setAssessmentType("4");
//					systemInsertData.setConfirmStatus("0");
//					systemInsertData.setScore(BigDecimal.ZERO);
//					systemInsertData.setReviewerKey("系统自动扣分");
//					systemInsertData.setReviewerName("系统自动扣分");
//					systemInsertData.setAuditStatus("0");
//					systemInsertData.setCreateUserInfo(userKey, realName);
//					systemInsertList.add(systemInsertData);
//				}
//				// 新增该项目下所有人的系统自动扣分的得分表数据
//				flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(systemInsertList);
//				if (flag == 0) {
//					throw new Apih5Exception("新增系统自动扣分数据失败。");
//				}
//			}
			
			// 6、创建系统自动扣分表
            // 新增该项目下所有人的系统扣分表数据
            if (principalScoreList.size() > 0 && managerList.size() > 0) {
                List<ZjXmJxAssessmentUserScore> systemInsertList = Lists.newArrayList();
                for (ZjXmJxAssessmentUserScore dbPrincipalScoreList : principalScoreList) {
                    SysUser user = userService.getSysUserByUserKey(dbPrincipalScoreList.getAuditeeKey());
                    if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, user.getJobType())
                          || StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, user.getJobType())
                          || StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, user.getJobType())) {
                        continue;
                    }
                    String deptId = "";
                    String deptName = "";
                    // 查部门
                    SysDepartment sysDepartment = new SysDepartment();
                    sysDepartment.setDepartmentId(projectId);
                    sysDepartment.setUserKey(user.getUserKey());
                    SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentProDept(sysDepartment);
                    if (dbSysDepartment != null) {
                        deptId = dbSysDepartment.getDepartmentId();
                        deptName = dbSysDepartment.getDepartmentName();
                    }
                    if (StrUtil.isEmpty(projectId) || StrUtil.isEmpty(projectName) || StrUtil.isEmpty(deptId)
                            || StrUtil.isEmpty(deptName)) {
                        return repEntity.layerMessage("no", "项目或部门信息不能为空。");
                    }
                    ZjXmJxAssessmentUserScore systemInsertData = new ZjXmJxAssessmentUserScore();
                    systemInsertData.setScoreId(UuidUtil.generate());
                    systemInsertData.setAssessmentId(zjXmJxMonthlyAssessment.getAssessmentId());
                    systemInsertData.setAuditeeKey(user.getUserKey());
                    systemInsertData.setAuditeeName(user.getRealName());
                    systemInsertData.setAuditeeIdNumber(user.getIdentityCard());
                    systemInsertData.setAuditeePosition(user.getJobType());
                    systemInsertData.setAuditeeRankType(user.getExt5());
                    systemInsertData.setAuditeeLastType(user.getExt6());
                    systemInsertData.setAuditeeUserType(user.getExt9());
                    systemInsertData.setAuditeeType(user.getExt2());
                    systemInsertData.setAuditeeDeptId(deptId);
                    systemInsertData.setAuditeeDeptName(deptName);
                    systemInsertData.setAuditeeProId(projectId);
                    systemInsertData.setAuditeeProName(projectName);
                    systemInsertData.setAssessmentType("4");
                    systemInsertData.setConfirmStatus("0");
                    systemInsertData.setScore(BigDecimal.ZERO);
                    systemInsertData.setReviewerKey("系统自动扣分");
                    systemInsertData.setReviewerName("系统自动扣分");
                    systemInsertData.setAuditStatus("0");
                    systemInsertData.setCreateUserInfo(userKey, realName);
                    systemInsertList.add(systemInsertData);
                }
                // 新增该项目下所有人的系统自动扣分的得分表数据
                flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(systemInsertList);
                if (flag == 0) {
                    throw new Apih5Exception("新增系统自动扣分数据失败。");
                }
            }
			
			// 1、批量修改任务考核、周边考核、正职考核得分表被通知时间(催打分定时任务)
			ZjXmJxAssessmentUserScore score = new ZjXmJxAssessmentUserScore();
			score.setNotifiedTime(DateUtil.date());
			score.setAssessmentId(dbMonthlyAssessment.getAssessmentId());
			// score.setAssessmentType("0");
			flag = zjXmJxAssessmentUserScoreMapper.updateZjXmJxAssessmentUserScoreByCondition(score);
			if (flag == 0) {
				throw new Apih5Exception("更新任务考核被通知时间失败。");
			}
			// 最后修改月度考核表通知状态
			dbMonthlyAssessment.setNoticeStatus("1");
			flag = zjXmJxMonthlyAssessmentMapper.updateByPrimaryKey(dbMonthlyAssessment);
			if (flag == 0) {
				throw new Apih5Exception("更新通知状态失败。");
			}
		}
		return repEntity.ok("消息发送成功。");
	}

	private JSONObject getWeightValue(String auditeeType, String reviewerType,
			ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		JSONObject jsonObj = JSONUtil.createObj();
		if (StrUtil.isNotEmpty(auditeeType) && StrUtil.isNotEmpty(reviewerType) && zjXmJxMonthlyAssessment != null) {
			// 如果被审核者为项目副职(同级权重)
			if (StrUtil.equals("0", auditeeType) && StrUtil.equals("0", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getDeputyPeerWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "同级打分");
			}
			// 如果被审核者为项目副职(下级权重)
			else if (StrUtil.equals("0", auditeeType) && !StrUtil.equals("0", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getDeputySubordinateWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "下级打分");
			}
			// 如果被审核者为部门负责人(上级权重)
			else if (StrUtil.equals("1", auditeeType) && StrUtil.equals("0", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getLeaderSuperiorWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "上级打分");
			}
			// 如果被审核者为部门负责人(同级权重)
			else if (StrUtil.equals("1", auditeeType) && StrUtil.equals("1", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getLeaderPeerWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "同级打分");
			}
			// 如果被审核者为部门负责人(下级权重)
			else if (StrUtil.equals("1", auditeeType) && StrUtil.equals("2", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getLeaderSubordinateWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "下级打分");
			}
			// 如果被审核者为普通员工(上级权重)
			else if (StrUtil.equals("2", auditeeType) && !StrUtil.equals("2", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getEmployeeSuperiorWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "上级打分");
			}
			// 如果被审核者为普通员工(同级权重)
			else if (StrUtil.equals("2", auditeeType) && StrUtil.equals("2", reviewerType)) {
				Integer weightValue = zjXmJxMonthlyAssessment.getEmployeePeerWeight();
				jsonObj.set("weightValue", weightValue);
				jsonObj.set("scoringType", "同级打分");
			}
		}
		return jsonObj;
	}
}
