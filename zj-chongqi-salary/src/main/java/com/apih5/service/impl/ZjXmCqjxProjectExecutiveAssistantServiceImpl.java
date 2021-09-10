package com.apih5.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectOaDeptMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSecretaryrAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectStaffAssistantDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDeputyLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSecretaryrAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectStaffAssistantDetailed;
import com.apih5.service.ZjXmCqjxProjectExecutiveAssistantService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@Service("zjXmCqjxProjectExecutiveAssistantService")
public class ZjXmCqjxProjectExecutiveAssistantServiceImpl implements ZjXmCqjxProjectExecutiveAssistantService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDepartmentHeadMapper zjXmCqjxProjectDepartmentHeadMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectStaffAssistantDetailedMapper zjXmCqjxProjectStaffAssistantDetailedMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectSecretaryrAssistantDetailedMapper zjXmCqjxProjectSecretaryrAssistantDetailedMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectAssistantLeaderApprovalMapper zjXmCqjxProjectAssistantLeaderApprovalMapper;

	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private ZjXmCqjxProjectAssessmentManageMapper zjXmCqjxProjectAssessmentManageMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;

	@Override
	public ResponseEntity checkZjXmCqjxProjectFinishPlanAssistant(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant) {
		ZjXmCqjxProjectExecutiveAssistant executiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
		executiveAssistant.setManagerId(zjXmCqjxExecutiveAssistant.getManagerId());
		List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByZjXmCqjxProjectExecutiveAssistantList(executiveAssistant);
		for (ZjXmCqjxProjectExecutiveAssistant assistant : assistantList) {
			if (!StrUtil.equals(assistant.getAssessmentState(), "5")) {
				return repEntity.layerMessage("NO", "当前项目下有人员没有完成计划流程，无法发起考核流程！");
			}
		}
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity getZjXmCqjxProjectLeaderApprovalAllList(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant) {
		if (StrUtil.equals("3", zjXmCqjxExecutiveAssistant.getAssessmentType())) {
			ZjXmCqjxProjectStaffAssistantDetailed assistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
			assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
					.selectByZjXmCqjxProjectStaffAssistantDetailedList(assistantDetailed);
			double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectStaffAssistantDetailed::getAssessmentScore)
					.sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
			} else if (sum > 60.0) {
				return repEntity.layerMessage("NO", "考核分数已经超过标准分，请修改！");
			}
		} else if (StrUtil.equals("2", zjXmCqjxExecutiveAssistant.getAssessmentType())) {
			ZjXmCqjxProjectSecretaryrAssistantDetailed assistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
			assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
					.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(assistantDetailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
			if (sum < 60.0) {
				return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
			} else if (sum > 60.0) {
				return repEntity.layerMessage("NO", "考核分数已经超过标准分，请修改！");
			}
		} else {
			ZjXmCqjxProjectDeputyLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
			assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
					.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(assistantDetailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectDeputyLeaderAssistantDetailed::getAssessmentScore).sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
			} else if (sum > 60.0) {
				return repEntity.layerMessage("NO", "考核分数已经超过标准分，请修改！");
			}
		}
		ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
		ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
		head.setDepartmentId(assistant.getDepartmentId());
		List<ZjXmCqjxProjectDepartmentHead> deptHeadList = zjXmCqjxProjectDepartmentHeadMapper
				.selectByZjXmCqjxProjectDepartmentHeadList(head);
		ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
		detail.setOtherId(deptHeadList.get(0).getDepartmentHeadId());
		if (StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "0")) {
				detail.setOtherType("2");
			}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
				detail.setOtherType("3");				
			}
		} else if (StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "0")) {
				detail.setOtherType("1");
			}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
				detail.setOtherType("2");				
			}
		} else if (StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "0")) {
				detail.setOtherType("0");
			}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
				detail.setOtherType("1");					
			}			
		}
		List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
				.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
		return repEntity.okList(detailList, detailList.size());
	}

	@Override
	public ResponseEntity zjXmCqjxProjectExecutiveAssistantReleaseTempSave(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		ZjXmCqjxProjectAssistantLeaderApproval dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectAssistantLeaderApprovalMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
		ZjXmCqjxProjectDisciplineAssistantLeaderApproval dbDisciplineApproval = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
		if (dbzjXmCqjxExecutiveAssistant != null) {
			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getLeaderFlag(), "0")) {
				dbzjXmCqjxExecutiveAssistant.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
			} else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getLeaderFlag(), "1")) {
				dbzjXmCqjxExecutiveAssistant
						.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
			}
			zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbzjXmCqjxExecutiveAssistant);
		} else if (dbDisciplineApproval != null) {
			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getLeaderFlag(), "0")) {
				dbDisciplineApproval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getDeptHeadOption());
			} else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getLeaderFlag(), "1")) {
				dbDisciplineApproval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
			}
			zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbDisciplineApproval);
		}
		return repEntity.ok(zjXmCqjxProjectExecutiveAssistant);
	}

	@Override
	public ResponseEntity getZjXmCqjxProjectExecutiveAssistantListByCondition(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		if (zjXmCqjxProjectExecutiveAssistant == null) {
			zjXmCqjxProjectExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		zjXmCqjxProjectExecutiveAssistant.setCreateUser(userKey);
		// 分页查询
		PageHelper.startPage(zjXmCqjxProjectExecutiveAssistant.getPage(), zjXmCqjxProjectExecutiveAssistant.getLimit());
		// 获取数据
		if (StrUtil.isNotEmpty(zjXmCqjxProjectExecutiveAssistant.getYears())) {
			zjXmCqjxProjectExecutiveAssistant
					.setAssessmentYears(DateUtil.parse(zjXmCqjxProjectExecutiveAssistant.getYears(), "yyyy-MM"));
		}
		List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByZjXmCqjxProjectExecutiveAssistantList(zjXmCqjxProjectExecutiveAssistant);
		for (ZjXmCqjxProjectExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
			ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
			head.setDepartmentId(assistant.getDepartmentId());
			List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
					.selectByZjXmCqjxProjectDepartmentHeadList(head);
			if (headList.size() > 0) {
				ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
				detail.setOtherId(headList.get(0).getDepartmentHeadId());
				detail.setOtherType("1");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
						.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if (detailList.size() == 0) {
					assistant.setHaveChangerLeader("1");
				} else {
					assistant.setHaveChangerLeader("0");
				}
			}
			if (!StrUtil.equals(assistant.getState(), "2")) {
				assistant.setScoreClosingEndDate(null);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmCqjxProjectExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);

		return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmCqjxProjectExecutiveAssistantDetails(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		if (zjXmCqjxProjectExecutiveAssistant == null) {
			zjXmCqjxProjectExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
		}
		// 获取数据
		ZjXmCqjxProjectExecutiveAssistant dbZjXmCqjxProjectExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		// 数据存在
		if (dbZjXmCqjxProjectExecutiveAssistant != null) {
			return repEntity.ok(dbZjXmCqjxProjectExecutiveAssistant);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmCqjxProjectExecutiveAssistant(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmCqjxProjectExecutiveAssistant.setExecutiveId(UuidUtil.generate());
		zjXmCqjxProjectExecutiveAssistant.setCreateUserInfo(userKey, realName);
		int flag = zjXmCqjxProjectExecutiveAssistantMapper.insert(zjXmCqjxProjectExecutiveAssistant);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmCqjxProjectExecutiveAssistant);
		}
	}

	@Override
	public ResponseEntity updateZjXmCqjxProjectExecutiveAssistant(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxProjectExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (dbzjXmCqjxProjectExecutiveAssistant != null
				&& StrUtil.isNotEmpty(dbzjXmCqjxProjectExecutiveAssistant.getExecutiveId())) {
			// 考核类别
			dbzjXmCqjxProjectExecutiveAssistant
					.setAssessmentType(zjXmCqjxProjectExecutiveAssistant.getAssessmentType());
			// 考核计划ID
			dbzjXmCqjxProjectExecutiveAssistant.setManagerId(zjXmCqjxProjectExecutiveAssistant.getManagerId());
			// 考核年度
			dbzjXmCqjxProjectExecutiveAssistant
					.setAssessmentYears(zjXmCqjxProjectExecutiveAssistant.getAssessmentYears());
			// 考核标题
			dbzjXmCqjxProjectExecutiveAssistant
					.setAssessmentTitle(zjXmCqjxProjectExecutiveAssistant.getAssessmentTitle());
			// 考核季度
			dbzjXmCqjxProjectExecutiveAssistant
					.setAssessmentQuarter(zjXmCqjxProjectExecutiveAssistant.getAssessmentQuarter());
			// 考核状态
			dbzjXmCqjxProjectExecutiveAssistant
					.setAssessmentState(zjXmCqjxProjectExecutiveAssistant.getAssessmentState());
			// 岗位
			dbzjXmCqjxProjectExecutiveAssistant.setPosition(zjXmCqjxProjectExecutiveAssistant.getPosition());
			// 分管领导ID
			dbzjXmCqjxProjectExecutiveAssistant
					.setChargeLeaderId(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderId());
			// 人员所在部门
			dbzjXmCqjxProjectExecutiveAssistant.setDepartmentId(zjXmCqjxProjectExecutiveAssistant.getDepartmentId());
			// 分管领导意见
			dbzjXmCqjxProjectExecutiveAssistant
					.setChargeLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
			// 主管领导ID
			dbzjXmCqjxProjectExecutiveAssistant
					.setExecutiveLeaderId(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderId());
			// 主管领导意见
			dbzjXmCqjxProjectExecutiveAssistant
					.setExecutiveLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
			// 锁定
			dbzjXmCqjxProjectExecutiveAssistant.setAssistantLock(zjXmCqjxProjectExecutiveAssistant.getAssistantLock());
			// 领导是否可见
			dbzjXmCqjxProjectExecutiveAssistant.setLeaderSee(zjXmCqjxProjectExecutiveAssistant.getLeaderSee());
			// 季度得分
			dbzjXmCqjxProjectExecutiveAssistant.setQuarterScore(zjXmCqjxProjectExecutiveAssistant.getQuarterScore());
			// 任务审核标识
			dbzjXmCqjxProjectExecutiveAssistant.setTaskFlag(zjXmCqjxProjectExecutiveAssistant.getTaskFlag());
			// 任务得分
			dbzjXmCqjxProjectExecutiveAssistant.setTaskScore(zjXmCqjxProjectExecutiveAssistant.getTaskScore());
			// 协作性审核标识
			dbzjXmCqjxProjectExecutiveAssistant
					.setCooperationFlag(zjXmCqjxProjectExecutiveAssistant.getCooperationFlag());
			// 协作性得分
			dbzjXmCqjxProjectExecutiveAssistant
					.setCooperationScore(zjXmCqjxProjectExecutiveAssistant.getCooperationScore());
			// 纪律性审核标识
			dbzjXmCqjxProjectExecutiveAssistant
					.setDisciplineFlag(zjXmCqjxProjectExecutiveAssistant.getDisciplineFlag());
			// 纪律性得分
			dbzjXmCqjxProjectExecutiveAssistant
					.setDisciplineScore(zjXmCqjxProjectExecutiveAssistant.getDisciplineScore());
			// 部门系数
			dbzjXmCqjxProjectExecutiveAssistant
					.setDeptCoefficient(zjXmCqjxProjectExecutiveAssistant.getDeptCoefficient());
			// 共通
			dbzjXmCqjxProjectExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxProjectExecutiveAssistant);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant(
			List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxProjectExecutiveAssistantList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmCqjxProjectExecutiveAssistantList != null && zjXmCqjxProjectExecutiveAssistantList.size() > 0) {
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
			zjXmCqjxProjectExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant(
					zjXmCqjxProjectExecutiveAssistantList, zjXmCqjxProjectExecutiveAssistant);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmCqjxProjectExecutiveAssistantList);
		}
	}

	@Override
	public ResponseEntity checkZjXmCqjxAssistantScoreQualified(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		ZjXmCqjxProjectExecutiveAssistant executiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (StrUtil.equals("3", executiveAssistant.getAssessmentType())) {
			ZjXmCqjxProjectStaffAssistantDetailed assistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
			assistantDetailed.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
					.selectByZjXmCqjxProjectStaffAssistantDetailedList(assistantDetailed);
			double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectStaffAssistantDetailed::getAssessmentScore)
					.sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
			} else if (sum > 60.0) {
				return repEntity.layerMessage("NO", "考核分数已经超过标准分，请修改！");
			}
		} else if (StrUtil.equals("2", executiveAssistant.getAssessmentType())) {
			ZjXmCqjxProjectSecretaryrAssistantDetailed assistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
			assistantDetailed.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
					.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(assistantDetailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
			if (sum < 60.0) {
				return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
			} else if (sum > 60.0) {
				return repEntity.layerMessage("NO", "考核分数已经超过标准分，请修改！");
			}
		} else {
			ZjXmCqjxProjectDeputyLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
			assistantDetailed.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
					.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(assistantDetailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectDeputyLeaderAssistantDetailed::getAssessmentScore).sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
			} else if (sum > 60.0) {
				return repEntity.layerMessage("NO", "考核分数已经超过标准分，请修改！");
			}
		}
		return repEntity.ok(zjXmCqjxProjectExecutiveAssistant);
	}

	public String checkContent(String executiveId) {
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(executiveId);
		if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
			ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
			detailed.setExecutiveId(executiveId);
			List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
					.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(detailed);
			boolean bool = detailedList.stream().anyMatch(m -> m.getWorkPlan() == null || m.getWorkPlan().equals(""));
			if (bool) {
				return "工作计划不能为空，请修改！";
			}
			bool = detailedList.stream().anyMatch(m -> m.getAssessmentScore() == 0);
			if (bool) {
				return "计划考核分数不能为0，请修改！";
			}
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
				bool = detailedList.stream().anyMatch(m -> m.getCompletion() == null || m.getCompletion().equals(""));
				if (bool) {
					return "完成情况不能为空，请完善！";
				}
			}
		} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
			ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
			detailed.setExecutiveId(executiveId);
			List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
					.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(detailed);
			boolean bool = detailedList.stream()
					.anyMatch(m -> m.getWorkTarget() == null || m.getWorkTarget().equals(""));
			if (bool) {
				return "工作计划不能为空，请修改！";
			}
			bool = detailedList.stream().anyMatch(m -> m.getWorkPlan() == null || m.getWorkPlan().equals(""));
			if (bool) {
				return "工作计划不能为空，请修改！";
			}
			bool = detailedList.stream().anyMatch(m -> m.getAssessmentScore() == 0);
			if (bool) {
				return "计划考核分数不能为0，请修改！";
			}
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
				bool = detailedList.stream().anyMatch(m -> m.getCompletion() == null || m.getCompletion().equals(""));
				if (bool) {
					return "完成情况不能为空，请完善！";
				}
			}
		} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
			ZjXmCqjxProjectStaffAssistantDetailed detailed = new ZjXmCqjxProjectStaffAssistantDetailed();
			detailed.setExecutiveId(executiveId);
			List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
					.selectByZjXmCqjxProjectStaffAssistantDetailedList(detailed);
			boolean bool = detailedList.stream()
					.anyMatch(m -> m.getWorkTarget() == null || m.getWorkTarget().equals(""));
			if (bool) {
				return "工作计划目标不能为空，请修改！";
			}
			bool = detailedList.stream().anyMatch(m -> m.getWorkPlan() == null || m.getWorkPlan().equals(""));
			if (bool) {
				return "工作计划目标不能为空，请修改！";
			}
			bool = detailedList.stream().anyMatch(m -> m.getAssessmentScore() == 0);
			if (bool) {
				return "计划考核分数不能为0，请修改！";
			}
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
				bool = detailedList.stream().anyMatch(m -> m.getCompletion() == null || m.getCompletion().equals(""));
				if (bool) {
					return "完成情况不能为空，请完善！";
				}
			}
		}
		return "0";
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxExecutiveAssistantFillIn(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		String result = checkContent(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (!StrUtil.equals(result, "0")) {
			return repEntity.layerMessage("NO", result);
		}
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		// 分数校验
		if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
			ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
			detailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
					.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(detailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectDeputyLeaderAssistantDetailed::getAssessmentScore).sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得小于满分60，请修改！");
			} else if ((sum > 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得大于满分60，请修改！");
			}
		} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
			ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
			detailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
					.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(detailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得小于满分60，请修改！");
			} else if ((sum > 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得大于满分60，请修改！");
			}
		} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
			ZjXmCqjxProjectStaffAssistantDetailed detailed = new ZjXmCqjxProjectStaffAssistantDetailed();
			detailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
					.selectByZjXmCqjxProjectStaffAssistantDetailedList(detailed);
			double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectStaffAssistantDetailed::getAssessmentScore)
					.sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得小于满分60，请修改！");
			} else if ((sum > 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得大于满分60，请修改！");
			}
		}
//        ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			dbzjXmCqjxExecutiveAssistant.setPosition(zjXmCqjxProjectExecutiveAssistant.getPosition());
			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxExecutiveAssistantLaunch(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendUserKey = "";
		String sendId = "";
		String state = "";
		String result = checkContent(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (!StrUtil.equals(result, "0")) {
			return repEntity.layerMessage("NO", result);
		}
		int flag = 0;
		// 分数校验
		if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentType(), "1")) {
			ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
			detailed.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
					.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(detailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectDeputyLeaderAssistantDetailed::getAssessmentScore).sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得小于满分60，请修改！");
			} else if ((sum > 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得大于满分60，请修改！");
			}
		} else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentType(), "2")) {
			ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
			detailed.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
					.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(detailed);
			double sum = detailedList.stream()
					.mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得小于满分60，请修改！");
			} else if ((sum > 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得大于满分60，请修改！");
			}
		} else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentType(), "3")) {
			ZjXmCqjxProjectStaffAssistantDetailed detailed = new ZjXmCqjxProjectStaffAssistantDetailed();
			detailed.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
			List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
					.selectByZjXmCqjxProjectStaffAssistantDetailedList(detailed);
			double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectStaffAssistantDetailed::getAssessmentScore)
					.sum();
			if ((sum < 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得小于满分60，请修改！");
			} else if ((sum > 60.0)) {
				return repEntity.layerMessage("NO", "考核分数不得大于满分60，请修改！");
			}
		}
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (!StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "0")
				&& !StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")
				&& !StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
			return repEntity.layerMessage("NO", "请勿重复发起！");
		}
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			// 如果是员工
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
				ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				// 如果是重新发起
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setOtherType("0");
					List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					for (int i = 0; i < zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
						ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList.get(i);
						if (i == 0) {
							sendUserKey = approval.getLeaderId();
							approval.setApprovalFlag("1");
							approval.setLeaderOption("");
						} else {
							approval.setApprovalFlag("0");
							approval.setLeaderOption("");
						}
						approval.setModifyUserInfo(sendUserKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					}
					state = "1";
				} else {
					// 首次发起
					ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
					head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);
					ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setOtherId(headList.get(0).getDepartmentHeadId());
					detail.setOtherType("0");
					List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
							.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
					if (detailList.size() > 0) {
						for (int i = 0; i < detailList.size(); i++) {
							zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
							zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
							zjXmCqjxAssistantLeaderApproval.setOtherType("0");
							zjXmCqjxAssistantLeaderApproval
									.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
							zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
							zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
							zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
							if (i == 0) {
								sendUserKey = detailList.get(i).getOaUserId();
								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
							} else {
								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
							}
							zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
						}
						state = "1";
					}
				}
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
				// 如果是项目中层干部
				ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				// 重新发起
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
					List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					if (zjXmCqjxAssistantLeaderApprovalList.size() > 0) {
						for (int i = 0; i < zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
							ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList
									.get(i);
							if (i == 0) {
								sendUserKey = approval.getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
						state = "1";
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
					List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					if (zjXmCqjxAssistantLeaderApprovalList.size() > 0) {
						for (int i = 0; i < zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
							ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList
									.get(i);
							if (i == 0) {
								sendUserKey = approval.getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
						state = "3";
					}
				} else {
					// 首次发起-------领导班子副职
					ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
					head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);
					ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setOtherId(headList.get(0).getDepartmentHeadId());
					detail.setOtherType("1");
					List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
							.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
					if (detailList.size() > 0) {
						for (int i = 0; i < detailList.size(); i++) {
							zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
							zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
							zjXmCqjxAssistantLeaderApproval.setOtherType("1");
							zjXmCqjxAssistantLeaderApproval
									.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
							zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
							zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
							zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
							if (i == 0) {
								sendUserKey = detailList.get(i).getOaUserId();
								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
							} else {
								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
							}
							zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
						}
						state = "1";
					} else {
						detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("2");
						detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
								.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if (detailList.size() > 0) {
							for (int i = 0; i < detailList.size(); i++) {
								zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
								zjXmCqjxAssistantLeaderApproval.setOtherType("2");
								zjXmCqjxAssistantLeaderApproval
										.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
								zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
								zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
								zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
								if (i == 0) {
									sendUserKey = detailList.get(i).getOaUserId();
									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
								} else {
									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
								}
								zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
							}
							state = "3";
						}
					}
				}
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
				// 如果是领导班子副职
				ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				// 重新发起
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
					List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					if (zjXmCqjxAssistantLeaderApprovalList.size() > 0) {
						for (int i = 0; i < zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
							ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList
									.get(i);
							if (i == 0) {
								sendUserKey = approval.getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
						state = "1";
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
					List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					if (zjXmCqjxAssistantLeaderApprovalList.size() > 0) {
						for (int i = 0; i < zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
							ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList
									.get(i);
							if (i == 0) {
								sendUserKey = approval.getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
						state = "3";
					}
				} else {
					// 首次发起-------领导班子副职
					ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
					head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);
					ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setOtherId(headList.get(0).getDepartmentHeadId());
					detail.setOtherType("2");
					List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
							.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
					if (detailList.size() > 0) {
						for (int i = 0; i < detailList.size(); i++) {
							zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
							zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
							zjXmCqjxAssistantLeaderApproval.setOtherType("2");
							zjXmCqjxAssistantLeaderApproval
									.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
							zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
							zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
							zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
							if (i == 0) {
								sendUserKey = detailList.get(i).getOaUserId();
								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
							} else {
								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
							}
							zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
						}
						state = "1";
					} else {
						detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("2");
						detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
								.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if (detailList.size() > 0) {
							for (int i = 0; i < detailList.size(); i++) {
								zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
								zjXmCqjxAssistantLeaderApproval.setOtherType("2");
								zjXmCqjxAssistantLeaderApproval
										.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
								zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
								zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
								zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
								if (i == 0) {
									sendUserKey = detailList.get(i).getOaUserId();
									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
								} else {
									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
								}
								zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
							}
							state = "3";
						}
					}
				}
			}
			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "1")) {
				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
				dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
				dbzjXmCqjxExecutiveAssistant.setAssessmentState(state);
				dbzjXmCqjxExecutiveAssistant.setLeaderSee("0");
			} else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "5")) {
				dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");
			}
			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "1")) {
				// 如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
				ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
				assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
				assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
				assistant.setAssessmentState("0");
				List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
						.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
				if (assistantList.size() == 0) {
					assistant.setAssessmentState(state);
					assistantList = zjXmCqjxProjectExecutiveAssistantMapper
							.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
					for (ZjXmCqjxProjectExecutiveAssistant ass : assistantList) {
						ass.setLeaderSee("1");
						ass.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(ass);
					}
					if (assistantList.size() > 0) {
						ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
								.selectByPrimaryKey(assistantList.get(0).getManagerId());
						// 给领导发送消息
						String content = "请于"
								+ DateUtil.format(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate(),
										"yyyy-MM-dd HH:mm:ss")
								+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程审批";
						SysUser user = userService.getSysUserByUserKey(sendUserKey);
						sendId = user.getUserId();
//   					sendId = "haiwei_xichengjian_test";
						weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
		}
	}

	@Override
	public ResponseEntity getZjXmCqjxExecutiveAssistantTodoList(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		zjXmCqjxProjectExecutiveAssistant.setCreateUser(userKey);
		zjXmCqjxProjectExecutiveAssistant.setLeaderSee("1");
		// 分页查询
		PageHelper.startPage(zjXmCqjxProjectExecutiveAssistant.getPage(), zjXmCqjxProjectExecutiveAssistant.getLimit());
		// 获取数据
		List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectProjectLeaderTodoListByUserKey(zjXmCqjxProjectExecutiveAssistant);
		for (ZjXmCqjxProjectExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
			ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
			head.setDepartmentId(assistant.getDepartmentId());
			List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
					.selectByZjXmCqjxProjectDepartmentHeadList(head);
			if (headList.size() > 0) {
				ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
				detail.setOtherId(headList.get(0).getDepartmentHeadId());
				detail.setOtherType("1");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
						.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if (detailList.size() == 0) {
					assistant.setHaveChangerLeader("1");
				} else {
					assistant.setHaveChangerLeader("0");
				}
			}
			if (!StrUtil.equals(assistant.getState(), "2")) {
				assistant.setFirstScoreClosingEndDate(null);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmCqjxProjectExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
		return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxAssistantChargeLeaderApproval(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendId = "";
		String sendUserKey = "";
		ZjXmCqjxProjectAssistantLeaderApproval approval = new ZjXmCqjxProjectAssistantLeaderApproval();
		approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
		if (!StrUtil.equals(approval.getApprovalFlag(), "1")) {
			return repEntity.layerMessage("NO", "已经审批过了！");
		}
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			// 分管领导退回
			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getApprovalFlag(), "1")) {
				approval = new ZjXmCqjxProjectAssistantLeaderApproval();
				approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
				approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
				approval.setApprovalFlag("2");
				zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(
						getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
								zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));
//    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
				dbzjXmCqjxExecutiveAssistant.setAssessmentState("2");
			} else {
				// 更新当前数据
				approval = new ZjXmCqjxProjectAssistantLeaderApproval();
				approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
				approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
				approval.setApprovalFlag("3");
				zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(
						getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
								zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));
				dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
				// 如果是员工类别，当前部门负责人
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("0");
					approval.setApprovalFlag("0");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					// 同级别审批完成，查看下一级别
					if (approvalList.size() > 0) {
						for (int i = 0; i < approvalList.size(); i++) {
							approval = approvalList.get(i);
							if (i == 0) {
								sendUserKey = approvalList.get(i).getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setCreateUserInfo(userKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
					} else {
						// 查询下一审批人，分管领导
						approval.setOtherType("1");
						approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								if (i == 0) {
									sendUserKey = approvalList.get(i).getLeaderId();
									approval.setApprovalFlag("1");
									approval.setLeaderOption("");
								} else {
									approval.setApprovalFlag("0");
									approval.setLeaderOption("");
								}
								approval.setCreateUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
							}
							dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");
						} else {
							// 领导表查询下一节点--分管领导
							ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
							head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
							List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);
							for (ZjXmCqjxProjectDepartmentHead deptHead : headList) {
								ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
								detail.setOtherId(deptHead.getDepartmentHeadId());
								detail.setOtherType("1");
								List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
								// 如果当前分管领导为空，需要去审批表里查询主管领导是否存在
								if (detailList.size() > 0) {
									for (int i = 0; i < detailList.size(); i++) {
										ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
										zjXmCqjxAssistantLeaderApproval
												.setAssistantLeaderApprovalId(UuidUtil.generate());
										zjXmCqjxAssistantLeaderApproval.setOtherType("1");
										zjXmCqjxAssistantLeaderApproval
												.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
										zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
										zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
										zjXmCqjxAssistantLeaderApproval
												.setLeaderName(detailList.get(i).getOaUserName());
										if (i == 0) {
											sendUserKey = detailList.get(i).getOaUserId();
											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
										} else {
											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
										}
										zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
										zjXmCqjxProjectAssistantLeaderApprovalMapper
												.insert(zjXmCqjxAssistantLeaderApproval);
									}
								} else {
									// 查询主管领导，没有则去领导表查询数据插入审批表
									approval.setOtherType("2");
									approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
											.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
									if (approvalList.size() > 0) {
										for (int i = 0; i < approvalList.size(); i++) {
											approval = approvalList.get(i);
											if (i == 0) {
												sendUserKey = approvalList.get(i).getLeaderId();
												approval.setApprovalFlag("1");
												approval.setLeaderOption("");
											} else {
												approval.setApprovalFlag("0");
												approval.setLeaderOption("");
											}
											approval.setCreateUserInfo(userKey, realName);
											zjXmCqjxProjectAssistantLeaderApprovalMapper
													.updateByPrimaryKeySelective(approval);
										}
									} else {
										detail = new ZjXmCqjxProjectDepartmentHeadDetail();
										detail.setOtherId(deptHead.getDepartmentHeadId());
										detail.setOtherType("2");
										detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
												.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
										if (detailList.size() > 0) {
											for (int i = 0; i < detailList.size(); i++) {
												ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
												zjXmCqjxAssistantLeaderApproval
														.setAssistantLeaderApprovalId(UuidUtil.generate());
												zjXmCqjxAssistantLeaderApproval.setOtherType("1");
												zjXmCqjxAssistantLeaderApproval
														.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
												zjXmCqjxAssistantLeaderApproval
														.setLeaderId(detailList.get(i).getOaUserId());
												zjXmCqjxAssistantLeaderApproval
														.setLeaderOrgId(detailList.get(i).getOaOrgId());
												zjXmCqjxAssistantLeaderApproval
														.setLeaderName(detailList.get(i).getOaUserName());
												if (i == 0) {
													sendUserKey = detailList.get(i).getOaUserId();
													zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
												} else {
													zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
												}
												zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
												zjXmCqjxProjectAssistantLeaderApprovalMapper
														.insert(zjXmCqjxAssistantLeaderApproval);
											}
										}

									}
								}
							}
//                		dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//                				zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));               		
							dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");
						}
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
					// 如果是项目中层干部
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("1");
					approval.setApprovalFlag("0");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					// 同级别审批完成，查看下一级别
					if (approvalList.size() > 0) {
						for (int i = 0; i < approvalList.size(); i++) {
							approval = approvalList.get(i);
							if (i == 0) {
								sendUserKey = approvalList.get(i).getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
					} else {
						// 查询下一审批人，分管领导
						approval.setOtherType("2");
						approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								if (i == 0) {
									sendUserKey = approvalList.get(i).getLeaderId();
									approval.setApprovalFlag("1");
									approval.setLeaderOption("");
								} else {
									approval.setApprovalFlag("0");
									approval.setLeaderOption("");
								}
								approval.setCreateUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
							}
						} else {
							// 领导表查询下一节点--分管领导
							ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
							head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
							List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);
							for (ZjXmCqjxProjectDepartmentHead deptHead : headList) {
								ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
								detail.setOtherId(deptHead.getDepartmentHeadId());
								detail.setOtherType("2");
								List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
								// 如果当前分管领导为空，需要去审批表里查询主管领导是否存在
								if (detailList.size() > 0) {
									for (int i = 0; i < detailList.size(); i++) {
										ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
										zjXmCqjxAssistantLeaderApproval
												.setAssistantLeaderApprovalId(UuidUtil.generate());
										zjXmCqjxAssistantLeaderApproval.setOtherType("2");
										zjXmCqjxAssistantLeaderApproval
												.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
										zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
										zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
										zjXmCqjxAssistantLeaderApproval
												.setLeaderName(detailList.get(i).getOaUserName());
										if (i == 0) {
											sendUserKey = detailList.get(i).getOaUserId();
											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
										} else {
											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
										}
										zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
										zjXmCqjxProjectAssistantLeaderApprovalMapper
												.insert(zjXmCqjxAssistantLeaderApproval);
									}
								}
							}
						}
//    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//    					zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));               		
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
					// 如果是项目领导班子副职
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("2");
					approval.setApprovalFlag("0");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					// 同级别审批完成，查看下一级别
					if (approvalList.size() > 0) {
						for (int i = 0; i < approvalList.size(); i++) {
							approval = approvalList.get(i);
							if (i == 0) {
								sendUserKey = approvalList.get(i).getLeaderId();
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							} else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
					} else {
						// 查询下一审批人，分管领导
						approval.setOtherType("3");
						approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								if (i == 0) {
									sendUserKey = approvalList.get(i).getLeaderId();
									approval.setApprovalFlag("1");
									approval.setLeaderOption("");
								} else {
									approval.setApprovalFlag("0");
									approval.setLeaderOption("");
								}
								approval.setCreateUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
							}
						} else {
							// 领导表查询下一节点--分管领导
							ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
							head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
							List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);
							for (ZjXmCqjxProjectDepartmentHead deptHead : headList) {
								ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
								detail.setOtherId(deptHead.getDepartmentHeadId());
								detail.setOtherType("3");
								List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
								// 如果当前分管领导为空，需要去审批表里查询主管领导是否存在
								if (detailList.size() > 0) {
									for (int i = 0; i < detailList.size(); i++) {
										ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
										zjXmCqjxAssistantLeaderApproval
												.setAssistantLeaderApprovalId(UuidUtil.generate());
										zjXmCqjxAssistantLeaderApproval.setOtherType("3");
										zjXmCqjxAssistantLeaderApproval
												.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
										zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
										zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
										zjXmCqjxAssistantLeaderApproval
												.setLeaderName(detailList.get(i).getOaUserName());
										if (i == 0) {
											sendUserKey = detailList.get(i).getOaUserId();
											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
										} else {
											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
										}
										zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
										zjXmCqjxProjectAssistantLeaderApprovalMapper
												.insert(zjXmCqjxAssistantLeaderApproval);
									}
								}
							}
						}
//        			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//        					zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));               		
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
					}
				}
			}

			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
			// 如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
			ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
			assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
			assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
			assistant.setAssessmentState("1");
			List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
					.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
			if (assistantList.size() == 0) {
				assistant.setAssessmentState("2");
				assistantList = zjXmCqjxProjectExecutiveAssistantMapper
						.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
				if (assistantList.size() == 0) {
					assistant.setAssessmentState("3");
					assistantList = zjXmCqjxProjectExecutiveAssistantMapper
							.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
					for (ZjXmCqjxProjectExecutiveAssistant ass : assistantList) {
						ass.setLeaderSee("1");
						ass.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(ass);
					}
					if (assistantList.size() > 0) {
						ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
								.selectByPrimaryKey(assistantList.get(0).getManagerId());
						// 给领导发送消息
						String content = "请于"
								+ DateUtil.format(zjXmCqjxAssessmentManage.getFinalDutyClosingEndDate(),
										"yyyy-MM-dd HH:mm:ss")
								+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程审批";
						SysUser user = userService.getSysUserByUserKey(sendUserKey);
						sendId = user.getUserId();
//  	  					sendId = "haiwei_xichengjian_test";
						weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
					}
				}
			}
		}
		return repEntity.ok(zjXmCqjxProjectExecutiveAssistant);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxAssistantExecutiveLeaderApproval(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String backType = "";
		ZjXmCqjxProjectAssistantLeaderApproval approval = new ZjXmCqjxProjectAssistantLeaderApproval();
		approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
		backType = approval.getOtherType();
		if (!StrUtil.equals(approval.getApprovalFlag(), "1")) {
			return repEntity.layerMessage("NO", "已经审批过了！");
		}
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			// 退回
			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getApprovalFlag(), "1")) {
				approval = new ZjXmCqjxProjectAssistantLeaderApproval();
				approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
				approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
				approval.setApprovalFlag("2");
				zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					// 退回到部门负责人
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("0");
					approval.setApprovalFlag("3");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					if (approvalList.size() > 0) {
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								if (i == 0) {
									approval.setApprovalFlag("1");
									approval.setLeaderOption("");
								} else {
									approval.setApprovalFlag("0");
									approval.setLeaderOption("");
								}
								approval.setModifyUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
							}
						}
						approval = new ZjXmCqjxProjectAssistantLeaderApproval();
						approval.setOtherType(backType);
						approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
						approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								approval.setApprovalFlag("0");
								approval.setModifyUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
							}
						}
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");
						dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
					} else {
						// 如果没有，则直接回到发起人手里
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
					// 项目中层干部
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("1");
					approval.setApprovalFlag("3");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					if (approvalList.size() > 0) {
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								if (i == 0) {
									approval.setApprovalFlag("1");
									approval.setLeaderOption("");
								} else {
									approval.setApprovalFlag("0");
									approval.setLeaderOption("");
								}
								approval.setModifyUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
							}
						}
						approval = new ZjXmCqjxProjectAssistantLeaderApproval();
						approval.setOtherType("2");
						approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
						approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								approval.setApprovalFlag("0");
								approval.setModifyUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
							}
						}
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");
						dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
					} else {
						approval = new ZjXmCqjxProjectAssistantLeaderApproval();
						approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
						approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
						approval.setApprovalFlag("2");
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						// 如果没有，则直接回到发起人手里
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
					// 项目领导班子副职
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("2");
					approval.setApprovalFlag("3");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					if (approvalList.size() > 0) {
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								if (i == 0) {
									approval.setApprovalFlag("1");
									approval.setLeaderOption("");
								} else {
									approval.setApprovalFlag("0");
									approval.setLeaderOption("");
								}
								approval.setModifyUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
							}
						}
						approval = new ZjXmCqjxProjectAssistantLeaderApproval();
						approval.setOtherType("3");
						approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
						approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
						if (approvalList.size() > 0) {
							for (int i = 0; i < approvalList.size(); i++) {
								approval = approvalList.get(i);
								approval.setApprovalFlag("0");
								approval.setModifyUserInfo(userKey, realName);
								zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
							}
						}
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");
						dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
					} else {
						approval = new ZjXmCqjxProjectAssistantLeaderApproval();
						approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
								.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
						approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
						approval.setApprovalFlag("2");
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						// 如果没有，则直接回到发起人手里
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");
					}
				}
			} else {
				// 如果是员工
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
					approval.setApprovalFlag("3");
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("1");
					approval.setApprovalFlag("0");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					if (approvalList.size() > 0) {
						approval = approvalList.get(0);
						approval.setApprovalFlag("1");
						approval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
					} else {
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
					// 如果是项目中层干部
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
					approval.setApprovalFlag("3");
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("2");
					approval.setApprovalFlag("0");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					if (approvalList.size() > 0) {
						approval = approvalList.get(0);
						approval.setApprovalFlag("1");
						approval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
					} else {
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
					// 如果是项目领导班子副职
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption());
					approval.setApprovalFlag("3");
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval.setOtherType("3");
					approval.setApprovalFlag("0");
					approval.setExecutiveId(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(approval);
					if (approvalList.size() > 0) {
						approval = approvalList.get(0);
						approval.setApprovalFlag("1");
						approval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
					} else {
						dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(
								getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
										zjXmCqjxProjectExecutiveAssistant.getExecutiveLeaderOption()));
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");
					}
				}
			}

			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
		}
		return repEntity.ok(zjXmCqjxProjectExecutiveAssistant);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxAssistantExecutiveLeaderScore(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		double taskScore = 0;// 任务得分
		double quarterScore = 0;// 任务得分
		String leaderScore = "";
		String taskFlag = "0";
		// 设置权重
//   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
//   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
		BigDecimal executiveScore;// 明细得分
		BigDecimal executiveScoreSum = null;// 明细得分合计
		BigDecimal cooperationScore = null;// 协作性得分
		BigDecimal disciplineScore = null;// 纪律性得分
		BigDecimal quarterScoreDem = null;// 纪律性得分
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		// 得到审批数据
		ZjXmCqjxProjectAssistantLeaderApproval dbApproval = zjXmCqjxProjectAssistantLeaderApprovalMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			if (StrUtil.equals(dbApproval.getApprovalFlag(), "5")) {
				return repEntity.layerMessage("NO", "已经评分过了");
			}
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
				// 如果还有主管领导没有评分完毕，则选择主管领导
				ZjXmCqjxProjectAssistantLeaderApproval leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
				leaderApproval.setOtherType("3");
				leaderApproval.setApprovalFlag("3");
				List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
				if (approvalList.size() > 0) {
					for (int i = 0; i < approvalList.size(); i++) {
						leaderApproval = approvalList.get(i);
						if (i == 0) {
							leaderApproval.setApprovalFlag("4");
						}
						leaderApproval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval);
					}
					ZjXmCqjxProjectDeputyLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
					assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
							.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(assistantDetailed);
					for (ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed : detailedList) {
						leaderScore = leaderScore + detailed.getExecutiveLeaderScore() + ",";
						detailed.setExecutiveLeaderScore(0);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(leaderScore);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
				} else {
					taskFlag = "1";
					ZjXmCqjxProjectDeputyLeaderAssistantDetailed exDetail = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
					exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> exDetailList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
							.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(exDetail);
					for (int i = 0; i < exDetailList.size(); i++) {
						ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = exDetailList.get(i);
						leaderScore = leaderScore + detailed.getExecutiveLeaderScore() + ",";
					}
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(leaderScore);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
					// 计算主管领导得分
					leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					leaderApproval.setApprovalFlag("5");
					leaderApproval.setOtherType("3");
					approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
					List<String[]> strArrList = new ArrayList<>();
					for (ZjXmCqjxProjectAssistantLeaderApproval app : approvalList) {
						strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length() - 1).split(","));
					}
					double[] doubleStr = new double[approvalList.size()];
					for (int k = 0; k < exDetailList.size(); k++) {
						ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = exDetailList.get(k);
						for (int j = 0; j < strArrList.size(); j++) {
							doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
						}
						// 主管领导平均评分+乘以权重以后评分
						double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
						if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getHaveChangerLeader(), "1")) {
							// 如果没有分管领导，则变换算法，不需要7:3，只是求平均数
							detailed.setExecutiveScore(asDouble);
							detailed.setExecutiveLeaderScore(asDouble);
							detailed.setModifyUserInfo(userKey, realName);
							zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
						} else {
							BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
							BigDecimal asDoubleDml = new BigDecimal(asDouble);
							executiveScoreSum = asDoubleDml.multiply(executiveLeaderWeight);
							// 获取分管领导平均得分+乘以权重以后评分
							BigDecimal changerLeaderScore = new BigDecimal(exDetailList.get(k).getChargeLeaderScore());
							BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
							changerLeaderScore = changerLeaderScore.multiply(chargeLeaderWeight);
							// 两个得分相加得出该计划最终季度得分
							executiveScoreSum = changerLeaderScore.add(executiveScoreSum);
							detailed.setExecutiveScore(executiveScoreSum.doubleValue());
							detailed.setExecutiveLeaderScore(asDoubleDml.doubleValue());
							detailed.setModifyUserInfo(userKey, realName);
							zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
						}
					}
					ZjXmCqjxProjectDeputyLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
					assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					// 计算各项任务得分，其中分管领导占有7成，主管领导占有3成
					List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
							.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(assistantDetailed);
					executiveScoreSum = new BigDecimal(taskScore);
					for (ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed : detailedList) {
						executiveScore = new BigDecimal(detailed.getExecutiveScore());
						executiveScoreSum = executiveScore.add(executiveScoreSum);
					}
					taskScore = executiveScoreSum.doubleValue();
					dbzjXmCqjxExecutiveAssistant.setTaskScore(taskScore);
				}
			}

			else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
				// 如果还有分管领导没有评分完毕，则选择分管领导
				ZjXmCqjxProjectAssistantLeaderApproval leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
				leaderApproval.setOtherType("2");
				leaderApproval.setApprovalFlag("3");
				List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
				if (approvalList.size() > 0) {
					for (int i = 0; i < approvalList.size(); i++) {
						leaderApproval = approvalList.get(i);
						if (i == 0) {
							leaderApproval.setApprovalFlag("4");
						}
						leaderApproval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval);
					}
					ZjXmCqjxProjectSecretaryrAssistantDetailed assistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
					assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					// 计算各项任务得分，其中分管领导占有7成，主管领导占有3成
					List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
							.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(assistantDetailed);
					for (ZjXmCqjxProjectSecretaryrAssistantDetailed detailed : detailedList) {
						leaderScore = leaderScore + detailed.getExecutiveLeaderScore() + ",";
						detailed.setExecutiveLeaderScore(0);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(leaderScore);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
				} else {
					taskFlag = "1";
					ZjXmCqjxProjectSecretaryrAssistantDetailed exDetail = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
					exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectSecretaryrAssistantDetailed> exDetailList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
							.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(exDetail);
					for (int i = 0; i < exDetailList.size(); i++) {
						ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = exDetailList.get(i);
						leaderScore = leaderScore + detailed.getExecutiveLeaderScore() + ",";
					}
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(leaderScore);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
					// 计算分管领导得分
					leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					leaderApproval.setApprovalFlag("5");
					leaderApproval.setOtherType("2");
					if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
						leaderApproval.setOtherType("1");
					}
					approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
					List<String[]> strArrList = new ArrayList<>();
					for (ZjXmCqjxProjectAssistantLeaderApproval app : approvalList) {
						strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length() - 1).split(","));
					}
					double[] doubleStr = new double[approvalList.size()];
					for (int k = 0; k < exDetailList.size(); k++) {
						ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = exDetailList.get(k);
						for (int j = 0; j < strArrList.size(); j++) {
							doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
						}
						// 主管领导平均评分+乘以权重以后评分
						double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
						if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getHaveChangerLeader(), "1")) {
							detailed.setExecutiveScore(asDouble);
							detailed.setExecutiveLeaderScore(asDouble);
							zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
						} else {
							BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
							BigDecimal asDoubleDml = new BigDecimal(asDouble);
							executiveScoreSum = asDoubleDml.multiply(executiveLeaderWeight);
							// 获取分管领导平均得分+乘以权重以后评分
							BigDecimal changerLeaderScore = new BigDecimal(exDetailList.get(k).getChargeLeaderScore());
							BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
							changerLeaderScore = changerLeaderScore.multiply(chargeLeaderWeight);
							// 两个得分相加得出该计划最终季度得分
							executiveScoreSum = changerLeaderScore.add(executiveScoreSum);
							detailed.setExecutiveScore(executiveScoreSum.doubleValue());
							detailed.setExecutiveLeaderScore(asDoubleDml.doubleValue());
							zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
						}
					}

					ZjXmCqjxProjectSecretaryrAssistantDetailed assistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
					assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					// 计算各项任务得分，其中分管领导占有7成，主管领导占有3成
					List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
							.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(assistantDetailed);
					executiveScoreSum = new BigDecimal(taskScore);
					for (ZjXmCqjxProjectSecretaryrAssistantDetailed detailed : detailedList) {
						executiveScore = new BigDecimal(detailed.getExecutiveScore());
						executiveScoreSum = executiveScore.add(executiveScoreSum);
					}
					taskScore = executiveScoreSum.doubleValue();
					dbzjXmCqjxExecutiveAssistant.setTaskScore(taskScore);
				}
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
				// 如果还有分管领导没有评分完毕，则选择分管领导
				ZjXmCqjxProjectAssistantLeaderApproval leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
				leaderApproval.setOtherType("1");
				if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getHaveChangerLeader(), "1")) {
					leaderApproval.setOtherType("1");
				}
				leaderApproval.setApprovalFlag("3");
				List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
				if (approvalList.size() > 0) {
					for (int i = 0; i < approvalList.size(); i++) {
						leaderApproval = approvalList.get(i);
						if (i == 0) {
							leaderApproval.setApprovalFlag("4");
						}
						leaderApproval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval);
					}
					ZjXmCqjxProjectStaffAssistantDetailed assistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
					assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					// 计算各项任务得分，其中分管领导占有7成，主管领导占有3成
					List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
							.selectByZjXmCqjxProjectStaffAssistantDetailedList(assistantDetailed);
					for (ZjXmCqjxProjectStaffAssistantDetailed detailed : detailedList) {
						leaderScore = leaderScore + detailed.getExecutiveLeaderScore() + ",";
						detailed.setExecutiveLeaderScore(0);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
					dbApproval.setLeaderScore(leaderScore);
					dbApproval.setApprovalFlag("5");
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
				} else {
					taskFlag = "1";
					ZjXmCqjxProjectStaffAssistantDetailed exDetail = new ZjXmCqjxProjectStaffAssistantDetailed();
					exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectStaffAssistantDetailed> exDetailList = zjXmCqjxProjectStaffAssistantDetailedMapper
							.selectByZjXmCqjxProjectStaffAssistantDetailedList(exDetail);
					for (int i = 0; i < exDetailList.size(); i++) {
						ZjXmCqjxProjectStaffAssistantDetailed detailed = exDetailList.get(i);
						leaderScore = leaderScore + detailed.getExecutiveLeaderScore() + ",";
					}
//               		String score = leaderScore.substring(0, leaderScore.length()-1);
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(leaderScore);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
					// 计算分管领导得分
					leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					leaderApproval.setApprovalFlag("5");
					leaderApproval.setOtherType("2");
					if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
						leaderApproval.setOtherType("1");
					}
					approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
					List<String[]> strArrList = new ArrayList<>();
					for (ZjXmCqjxProjectAssistantLeaderApproval app : approvalList) {
						strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length() - 1).split(","));
					}
					double[] doubleStr = new double[approvalList.size()];
					for (int k = 0; k < exDetailList.size(); k++) {
						ZjXmCqjxProjectStaffAssistantDetailed detailed = exDetailList.get(k);
						for (int j = 0; j < strArrList.size(); j++) {
							doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
						}
						// 主管领导平均评分+乘以权重以后评分
						double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
						BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
						BigDecimal asDoubleDml = new BigDecimal(asDouble);
						executiveScoreSum = asDoubleDml.multiply(executiveLeaderWeight);
						// 获取分管领导平均得分+乘以权重以后评分
						BigDecimal changerLeaderScore = new BigDecimal(exDetailList.get(k).getChargeLeaderScore());
						BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
						changerLeaderScore = changerLeaderScore.multiply(chargeLeaderWeight);
						// 两个得分相加得出该计划最终季度得分
						executiveScoreSum = changerLeaderScore.add(executiveScoreSum);
						detailed.setExecutiveScore(executiveScoreSum.doubleValue());
						detailed.setExecutiveLeaderScore(asDoubleDml.doubleValue());
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}

					ZjXmCqjxProjectStaffAssistantDetailed assistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
					assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					// 计算各项任务得分，其中分管领导占有7成，主管领导占有3成
					executiveScoreSum = new BigDecimal(taskScore);
					List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper
							.selectByZjXmCqjxProjectStaffAssistantDetailedList(assistantDetailed);
					for (ZjXmCqjxProjectStaffAssistantDetailed detailed : detailedList) {
						executiveScore = new BigDecimal(detailed.getExecutiveScore());
						executiveScoreSum = executiveScore.add(executiveScoreSum);
					}
					taskScore = executiveScoreSum.doubleValue();
					dbzjXmCqjxExecutiveAssistant.setTaskScore(taskScore);
				}
			}
			// 功能暂时注释，等待业务完整后打开修改
			if (StrUtil.equals(taskFlag, "1")) {
				// 如果各项评分完成，则计算季度得分
//                 if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getDisciplineFlag(), "1")) {
//                	 if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//                		 //获取员工本季度得分
//                       	cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
//                      	disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
//                      	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
//                     	//得到部门系数
//                     	ZjXmCqjxOaDeptMember member = new ZjXmCqjxOaDeptMember();
//                     	member.setOtherId(dbzjXmCqjxExecutiveAssistant.getManagerId());
//                     	member.setOaUserId(dbzjXmCqjxExecutiveAssistant.getCreateUser());
//                     	List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxProjectOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(member);
//                     	if(memberList.size()>0) {
//                 			ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//                 			head.setDepartmentId(memberList.get(0).getOaOrgId());
//                 			List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);   
//                         	ZjXmCqjxProjectExecutiveAssistant executiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
//                         	executiveAssistant.setYears(dbzjXmCqjxExecutiveAssistant.getYears());
//                         	executiveAssistant.setAssessmentQuarter(dbzjXmCqjxExecutiveAssistant.getAssessmentQuarter());
//                         	executiveAssistant.setCreateUser(headList.get(0).getUserKey());
//                         	List<ZjXmCqjxProjectExecutiveAssistant> executiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(executiveAssistant);
//                         	if(executiveAssistantList.size()>0) {
//                         		if(!StrUtil.equals(executiveAssistantList.get(0).getAssessmentState(), "8")) {
//                         			quarterScoreDem = new BigDecimal(0);
//                         		}else {
//                             		BigDecimal deptLeaderScore = new BigDecimal(executiveAssistantList.get(0).getQuarterScore());
//                             		BigDecimal weight = new BigDecimal(60);
//                             		BigDecimal deptWeight = deptLeaderScore.divide(weight);
//                             		quarterScoreDem.multiply(deptWeight);    
//                             		dbzjXmCqjxExecutiveAssistant.setDeptCoefficient(deptWeight.doubleValue()+"");
//                         		}
//                         	}
//                         	quarterScore = quarterScoreDem.doubleValue();
//                         	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);                        	
//                     	}
//                	 }
//                	 else 
//                		 
//                		 if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")){
//                      	cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
//                     	disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
//                     	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
//                     	quarterScore = quarterScoreDem.doubleValue();
//                 		BigDecimal deptLeaderScore = new BigDecimal(quarterScore);
//                 		BigDecimal weight = new BigDecimal(60);
//                 		BigDecimal deptWeight = deptLeaderScore.divide(weight);   
//                 		DecimalFormat df = new DecimalFormat("0.0");
//                 		dbzjXmCqjxExecutiveAssistant.setDeptCoefficient(df.format(deptWeight));
//                     	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);            		 
//                	 }
//                	 else {
//                		 cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
//                      	disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
//                      	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
//                      	quarterScore = quarterScoreDem.doubleValue();
//                      	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
//                	 }
//                 }             
				// 考核状态
				dbzjXmCqjxExecutiveAssistant.setAssessmentState("8");
				dbzjXmCqjxExecutiveAssistant.setTaskFlag("1");
				// 共通
				dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
			}
		}
		return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxAssistantChargeLeaderScore(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendId = "";
		String leaderScore = "";
		String sendUserKey = "";
		// 得到考核数据
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		// 得到审批数据
		ZjXmCqjxProjectAssistantLeaderApproval dbApproval = zjXmCqjxProjectAssistantLeaderApprovalMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			if (StrUtil.equals(dbApproval.getApprovalFlag(), "5")) {
				return repEntity.layerMessage("NO", "已经评分过了");
			}
			// 如果还有分管领导没有评分完毕，则选择分管领导
			ZjXmCqjxProjectAssistantLeaderApproval leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
			leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//           leaderApproval.setOtherType("1");
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
				leaderApproval.setOtherType("0");
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
				leaderApproval.setOtherType("1");
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
				leaderApproval.setOtherType("2");
			}
			leaderApproval.setApprovalFlag("3");
			List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
					.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
			if (approvalList.size() > 0) {
				for (int i = 0; i < approvalList.size(); i++) {
					leaderApproval = approvalList.get(i);
					if (i == 0) {
						leaderApproval.setApprovalFlag("4");
					}
					leaderApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval);
				}
				// 将得分写入到审批表中
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
					ZjXmCqjxProjectDeputyLeaderAssistantDetailed exDetail = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
					exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> exDetailList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
							.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(exDetail);
					for (ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed : exDetailList) {
						leaderScore = leaderScore + detailed.getChargeLeaderScore() + ",";
						detailed.setChargeLeaderScore(0);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
//        		String score = leaderScore.substring(0, leaderScore.length()-1);
					dbApproval.setLeaderScore(leaderScore);
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
					ZjXmCqjxProjectSecretaryrAssistantDetailed deptDetail = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
					deptDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectSecretaryrAssistantDetailed> deptDetailList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
							.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(deptDetail);
					for (ZjXmCqjxProjectSecretaryrAssistantDetailed detailed : deptDetailList) {
						leaderScore = leaderScore + detailed.getChargeLeaderScore() + ",";
						detailed.setChargeLeaderScore(0);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
					dbApproval.setLeaderScore(leaderScore);
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					ZjXmCqjxProjectStaffAssistantDetailed staDetail = new ZjXmCqjxProjectStaffAssistantDetailed();
					staDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectStaffAssistantDetailed> staDetailList = zjXmCqjxProjectStaffAssistantDetailedMapper
							.selectByZjXmCqjxProjectStaffAssistantDetailedList(staDetail);
					for (ZjXmCqjxProjectStaffAssistantDetailed detailed : staDetailList) {
						leaderScore = leaderScore + detailed.getChargeLeaderScore() + ",";
						detailed.setChargeLeaderScore(0);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
					dbApproval.setLeaderScore(leaderScore);
				}
				dbApproval.setApprovalFlag("5");
				dbApproval.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
			} else {
				// 将审批发给主管领导
				leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//        	 leaderApproval.setOtherType("2");
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					leaderApproval.setOtherType("1");
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
					leaderApproval.setOtherType("2");
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
					leaderApproval.setOtherType("3");
				}
				leaderApproval.setApprovalFlag("3");
				approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
				for (int i = 0; i < approvalList.size(); i++) {
					leaderApproval = approvalList.get(i);
					if (i == 0) {
						sendUserKey = leaderApproval.getLeaderId();
						leaderApproval.setApprovalFlag("4");
					}
					leaderApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval);
				}
				// 考核状态
				dbzjXmCqjxExecutiveAssistant.setAssessmentState("7");
				// 共通
				dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
				// 将分管领导写入审批表中，并得出所有分管领导之和
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
					ZjXmCqjxProjectDeputyLeaderAssistantDetailed exDetail = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
					exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> exDetailList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper
							.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(exDetail);
					for (int i = 0; i < exDetailList.size(); i++) {
						ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = exDetailList.get(i);
						leaderScore = leaderScore + detailed.getChargeLeaderScore() + ",";
					}
//    		String score = leaderScore.substring(0, leaderScore.length()-1);

					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(dbApproval.getLeaderScore() + leaderScore);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
					// 计算分管领导得分
					leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					leaderApproval.setApprovalFlag("5");
					leaderApproval.setOtherType("2");
					if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
						leaderApproval.setOtherType("0");
					}
					approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
					List<String[]> strArrList = new ArrayList<>();
					for (ZjXmCqjxProjectAssistantLeaderApproval app : approvalList) {
						strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length() - 1).split(","));
					}
					double[] doubleStr = new double[approvalList.size()];
					for (int k = 0; k < exDetailList.size(); k++) {
						ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed = exDetailList.get(k);
						for (int j = 0; j < strArrList.size(); j++) {
							doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
						}
						double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
						detailed.setChargeLeaderScore(asDouble);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
					ZjXmCqjxProjectSecretaryrAssistantDetailed deptDetail = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
					deptDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectSecretaryrAssistantDetailed> deptDetailList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper
							.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(deptDetail);
					for (int i = 0; i < deptDetailList.size(); i++) {
						ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = deptDetailList.get(i);
						leaderScore = leaderScore + detailed.getChargeLeaderScore() + ",";
					}
					String score = leaderScore.substring(0, leaderScore.length() - 1);
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(score);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
					// 计算分管领导得分
					leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					leaderApproval.setApprovalFlag("5");
					leaderApproval.setOtherType("1");
					if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
						leaderApproval.setOtherType("0");
					}
					approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
					List<String[]> strArrList = new ArrayList<>();
					for (ZjXmCqjxProjectAssistantLeaderApproval app : approvalList) {
						strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length() - 1).split(","));
					}
					double[] doubleStr = new double[approvalList.size()];
					for (int k = 0; k < deptDetailList.size(); k++) {
						ZjXmCqjxProjectSecretaryrAssistantDetailed detailed = deptDetailList.get(k);
						for (int j = 0; j < strArrList.size(); j++) {
							doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
						}
						double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
						detailed.setChargeLeaderScore(asDouble);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
				} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					ZjXmCqjxProjectStaffAssistantDetailed staDetail = new ZjXmCqjxProjectStaffAssistantDetailed();
					staDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					List<ZjXmCqjxProjectStaffAssistantDetailed> staDetailList = zjXmCqjxProjectStaffAssistantDetailedMapper
							.selectByZjXmCqjxProjectStaffAssistantDetailedList(staDetail);
					for (int i = 0; i < staDetailList.size(); i++) {
						ZjXmCqjxProjectStaffAssistantDetailed detailed = staDetailList.get(i);
						leaderScore = leaderScore + detailed.getChargeLeaderScore() + ",";
					}
					String score = leaderScore.substring(0, leaderScore.length() - 1);
					dbApproval.setApprovalFlag("5");
					dbApproval.setLeaderScore(score);
					dbApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
					// 计算分管领导得分
					leaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					leaderApproval.setApprovalFlag("5");
					leaderApproval.setOtherType("1");
					if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
						leaderApproval.setOtherType("0");
					}
					approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByZjXmCqjxProjectAssistantLeaderApprovalList(leaderApproval);
					List<String[]> strArrList = new ArrayList<>();
					for (ZjXmCqjxProjectAssistantLeaderApproval app : approvalList) {
						strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()).split(","));
					}
					double[] doubleStr = new double[approvalList.size()];
					for (int k = 0; k < staDetailList.size(); k++) {
						ZjXmCqjxProjectStaffAssistantDetailed detailed = staDetailList.get(k);
						for (int j = 0; j < strArrList.size(); j++) {
							doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
						}
						double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
						detailed.setChargeLeaderScore(asDouble);
						detailed.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
					}
				}
			}
		}
		// 如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
		ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
		assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
		assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
		assistant.setAssessmentState("6");
		List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
		if (assistantList.size() == 0) {
			assistant.setAssessmentState("7");
			assistantList = zjXmCqjxProjectExecutiveAssistantMapper
					.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
			if (assistantList.size() > 0) {
				ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
						.selectByPrimaryKey(assistantList.get(0).getManagerId());
				// 给领导发送消息
				String content = "请于"
						+ DateUtil.format(zjXmCqjxAssessmentManage.getFinalScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
						+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程评分";
				SysUser user = userService.getSysUserByUserKey(sendUserKey);
				sendId = user.getUserId();
//					sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
			}
		}
		return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxExecutiveScoreLaunch(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendId = "";
		String sendUserKey = "";
		String state = "";
		int flag = 0;
		String result = checkContent(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (!StrUtil.equals(result, "0")) {
			return repEntity.layerMessage("NO", result);
		}
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (!StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
			return repEntity.layerMessage("NO", "请勿重复发起！");
		}
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
//           dbzjXmCqjxExecutiveAssistant.setPosition(zjXmCqjxProjectExecutiveAssistant.getPosition());
			ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
			zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//           zjXmCqjxAssistantLeaderApproval.setOtherType("1");
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
				zjXmCqjxAssistantLeaderApproval.setOtherType("0");
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
				zjXmCqjxAssistantLeaderApproval.setOtherType("1");
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
				zjXmCqjxAssistantLeaderApproval.setOtherType("2");
			}
			List<ZjXmCqjxProjectAssistantLeaderApproval> approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
					.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
			if (approvalList.size() > 0) {
				for (int i = 0; i < approvalList.size(); i++) {
					zjXmCqjxAssistantLeaderApproval = approvalList.get(i);
					if (i == 0) {
						sendUserKey = zjXmCqjxAssistantLeaderApproval.getLeaderId();
						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("4");
					}
					zjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper
							.updateByPrimaryKeySelective(zjXmCqjxAssistantLeaderApproval);
					dbzjXmCqjxExecutiveAssistant.setAssessmentState("6");
				}
			} else {
				zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
				zjXmCqjxAssistantLeaderApproval.setOtherType("2");
				if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
					zjXmCqjxAssistantLeaderApproval.setOtherType("0");
				}
				approvalList = zjXmCqjxProjectAssistantLeaderApprovalMapper
						.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
				if (approvalList.size() > 0) {
					for (int i = 0; i < approvalList.size(); i++) {
						zjXmCqjxAssistantLeaderApproval = approvalList.get(i);
						if (i == 0) {
							sendUserKey = zjXmCqjxAssistantLeaderApproval.getLeaderId();
							zjXmCqjxAssistantLeaderApproval.setApprovalFlag("4");
						}
						zjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectAssistantLeaderApprovalMapper
								.updateByPrimaryKeySelective(zjXmCqjxAssistantLeaderApproval);
						dbzjXmCqjxExecutiveAssistant.setAssessmentState("7");
					}
				}
			}
			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
		}
		// 如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
		ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
		assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
		assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
		assistant.setAssessmentState("5");
		List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
		if (assistantList.size() == 0) {
//				assistant.setAssessmentState("6");
			assistant.setAssessmentState(dbzjXmCqjxExecutiveAssistant.getAssessmentState());
			assistantList = zjXmCqjxProjectExecutiveAssistantMapper
					.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
			for (ZjXmCqjxProjectExecutiveAssistant ass : assistantList) {
				ass.setLeaderSee("1");
				ass.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(ass);
			}
			if (assistantList.size() > 0) {
				ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
						.selectByPrimaryKey(assistantList.get(0).getManagerId());
				// 给领导发送消息
				String content = "请于"
						+ DateUtil.format(zjXmCqjxAssessmentManage.getFirstScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
						+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程评分";
				SysUser user = userService.getSysUserByUserKey(sendUserKey);
				sendId = user.getUserId();
//   					sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
			}
		} else {
			assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
			assistant.setAssessmentState("");
			assistantList = zjXmCqjxProjectExecutiveAssistantMapper
					.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
			for (ZjXmCqjxProjectExecutiveAssistant ass : assistantList) {
				ass.setLeaderSee("0");
				ass.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(ass);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
		}
	}

	// 此方法不用了
	@Override
	public ResponseEntity getZjXmCqjxAssistantOaLeaderListByExecutiveId(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
//			if(assistant.getAssessmentState().equals("0") || assistant.getAssessmentState().equals("2") || assistant.getAssessmentState().equals("5")) {
//				if(StrUtil.isNotEmpty(assistant.getChargeLeaderId())) {
////					ZjXmCqjxOaLeader leader = new ZjXmCqjxOaLeader();
////					leader.setOtherId(assistant.getExecutiveId());
////					leader.setOtherType("0");
////					leader.setOaUserId(assistant.getChargeLeaderId());
////					List leaderList = zjXmCqjxOaLeaderMapper.selectByZjXmCqjxOaLeaderList(leader);
////					assistant.setLeaderList(BusinessZj.dbToPageByMember(leaderList));
//					//如果初始化是员工时，自动选择部门负责人
//				}else if(assistant.getAssessmentType().equals("3")) {
//					ZjXmCqjxOaDeptMember member = new ZjXmCqjxOaDeptMember();
//					member.setOtherId(assistant.getManagerId());
//					member.setOaUserId(assistant.getCreateUser());
//					List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxProjectOaDeptMemberMapper.selectByZjXmCqjxProjectOaDeptMemberList(member);
//					ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
//					head.setDepartmentOrgId(memberList.get(0).getOaOrgId());
//					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(head);
//					List zcMemberList = new ArrayList();
//					if(headList.size()>0) {
//			        	head = headList.get(0);
//			        	head.setOaOrgId(head.getUserOrgId());
//			        	head.setOaUserId(head.getUserKey());
//			        	head.setOaUserName(head.getUserName());
//			        	zcMemberList.add(head);					
//						assistant.setLeaderList(BusinessZj.dbToPageByMember(zcMemberList));						
//					}else {
//						assistant.setLeaderList(BusinessZj.dbToPageByMember(zcMemberList));	
//					}
//				}
//			}else if(assistant.getAssessmentState().equals("1") || assistant.getAssessmentState().equals("4") || assistant.getAssessmentState().equals("6")) {
//				if(StrUtil.isNotEmpty(assistant.getExecutiveLeaderId())) {
//					ZjXmCqjxOaLeader leader = new ZjXmCqjxOaLeader();
//					leader.setOtherId(assistant.getExecutiveId());
//					leader.setOtherType("1");					
//					leader.setOaUserId(assistant.getExecutiveLeaderId());
//					List leaderList = zjXmCqjxOaLeaderMapper.selectByZjXmCqjxOaLeaderList(leader);
//					assistant.setLeaderList(BusinessZj.dbToPageByMember(leaderList));
//				}				
//			}
		return repEntity.ok(assistant);
	}

	@Override
	public ResponseEntity checkZjXmCqjxExecutiveAssistantLaunch(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getManagerId());
		if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "5")
				&& !StrUtil.equals(manager.getState(), "2")) {
			return repEntity.layerMessage("NO", "该考核流程未开启，请等待管理员开启！");
		}
		ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		Date now = new Date();
		// 申请人发起审核和退回到发起人重新发起状态
		if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "0")
				|| StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "2")) {
			if (!StrUtil.equals(assistant.getAssistantLock(), "2")) {
				if (now.after(manager.getDutyClosingEndDate())) {
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}
			}
		} // 分管领导审核和主管领导退回给分管领导调整状态
		else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "1")) {
			if (!StrUtil.equals(assistant.getAssistantLock(), "3")) {
				if (now.after(manager.getFirstDutyClosingEndDate())) {
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}
			}
		} // 主管领导审核
		else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "3")) {
			if (!StrUtil.equals(assistant.getAssistantLock(), "4")) {
				if (now.after(manager.getFinalDutyClosingEndDate())) {
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}
			}
		} // 申请人发起评分申请状态
		else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "5")) {
			if (!StrUtil.equals(assistant.getAssistantLock(), "5")) {
				if (now.after(manager.getScoreClosingEndDate())) {
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}
			}
		} // 分管领导评分状态
		else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "6")) {
			if (!StrUtil.equals(assistant.getAssistantLock(), "6")) {
				if (now.after(manager.getFirstScoreClosingEndDate())) {
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}
			}
		} // 主管领导评分状态
		else if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "7")) {
			if (!StrUtil.equals(assistant.getAssistantLock(), "7")) {
				if (now.after(manager.getFinalScoreClosingEndDate())) {
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}
			}
		}
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity batchCheckZjXmCqjxExecutiveAssistantLaunch(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getManagerId());
//		if(StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "5") && !StrUtil.equals(manager.getState(), "2")) {
//			return repEntity.layerMessage("NO", "该考核流程未开启，请等待管理员开启！");
//		}
		Date now = new Date();
//		ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
		assistant.setManagerId(zjXmCqjxProjectExecutiveAssistant.getManagerId());
		List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
		for (ZjXmCqjxProjectExecutiveAssistant executiveAssistant : assistantList) {
			// 申请人发起审核和退回到发起人重新发起状态
			if (StrUtil.equals(executiveAssistant.getAssessmentState(), "0")
					|| StrUtil.equals(executiveAssistant.getAssessmentState(), "2")) {
				if (!StrUtil.equals(executiveAssistant.getAssistantLock(), "2")) {
					if (now.after(manager.getDutyClosingEndDate())) {
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
								.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
				}
			} // 分管领导审核和主管领导退回给分管领导调整状态
			else if (StrUtil.equals(executiveAssistant.getAssessmentState(), "1")) {
				if (!StrUtil.equals(executiveAssistant.getAssistantLock(), "3")) {
					if (now.after(manager.getFirstDutyClosingEndDate())) {
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
								.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
				}
			} // 主管领导审核
			else if (StrUtil.equals(executiveAssistant.getAssessmentState(), "3")) {
				if (!StrUtil.equals(executiveAssistant.getAssistantLock(), "4")) {
					if (now.after(manager.getFinalDutyClosingEndDate())) {
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
								.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
				}
			} // 申请人发起评分申请状态
			else if (StrUtil.equals(executiveAssistant.getAssessmentState(), "5")) {
				if (!StrUtil.equals(executiveAssistant.getAssistantLock(), "5")) {
					if (now.after(manager.getScoreClosingEndDate())) {
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
								.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
				}
			} // 分管领导评分状态
			else if (StrUtil.equals(executiveAssistant.getAssessmentState(), "6")) {
				if (!StrUtil.equals(executiveAssistant.getAssistantLock(), "6")) {
					if (now.after(manager.getFirstScoreClosingEndDate())) {
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
								.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
				}
			} // 主管领导评分状态
			else if (StrUtil.equals(executiveAssistant.getAssessmentState(), "7")) {
				if (!StrUtil.equals(executiveAssistant.getAssistantLock(), "7")) {
					if (now.after(manager.getFinalScoreClosingEndDate())) {
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
								.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
				}
			}
		}

		return repEntity.ok("");
	}

	@Override
	public ResponseEntity zjXmCqjxExecutiveAssistantReleaseLock(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			switch (dbzjXmCqjxExecutiveAssistant.getAssessmentState()) {
			case "0":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("2");
				break;
			case "2":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("2");
				break;
			case "1":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("3");
				break;
			case "4":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("3");
				break;
			case "3":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("4");
				break;
			case "5":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("5");
				break;
			case "6":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("6");
				break;
			case "7":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("7");
				break;
			default:
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("0");
				break;
			}
			// 共通
			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.layerMessage("YES", "操作成功!");
		}
	}

	@Override
	public ResponseEntity checkZjXmCqjxAssessmentManageState(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getManagerId());
		if (!StrUtil.equals(manager.getState(), "2")) {
			return repEntity.layerMessage("NO", "该考核流程未开启，请等待管理员开启！");
		}
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity getZjXmCqjxAssistantListByDeptLeader(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		// 判断当前人是否是部门负责人
//        ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//        head.setUserKey(userKey);
//        List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
		ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
		detail.setOtherType("0");
		detail.setOaUserId(userKey);
		List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper
				.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
		if (detailList.size() == 0) {
			List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxProjectAssessmentManage>();
			return repEntity.okList(zjXmCqjxAssessmentManageList, 0);
		} else {
			zjXmCqjxProjectExecutiveAssistant.setDepartmentId(detailList.get(0).getOaOrgId());
		}
		// 分页查询
		PageHelper.startPage(zjXmCqjxProjectExecutiveAssistant.getPage(), zjXmCqjxProjectExecutiveAssistant.getLimit());
		// 获取数据
		List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectZjXmCqjxProjectAssessmentManageListByDeptHeader(zjXmCqjxProjectExecutiveAssistant);
		// 得到分页信息
		PageInfo<ZjXmCqjxProjectExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);

		return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmCqjxExecutiveAssistantTodoCount(String token, String type, String leaderFlag,
			String state) {
		int num = 0;
		if (StrUtil.isEmpty(token)) {
			return repEntity.ok(0);
		}
		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
		JSONObject json = new JSONObject(tokenDecrypt);
		String userKey = (String) json.get("userKey");
		List<ZjXmCqjxProjectExecutiveAssistant> list = new ArrayList<ZjXmCqjxProjectExecutiveAssistant>();
		ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
		ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
		if (StrUtil.equals(leaderFlag, "1")) {
			zjXmCqjxProjectExecutiveAssistant.setLeaderSee("1");
			zjXmCqjxProjectExecutiveAssistant.setChargeLeaderId(userKey);
			assistant.setAssessmentState("1");
			list.add(assistant);
			assistant = new ZjXmCqjxProjectExecutiveAssistant();
			assistant.setAssessmentState("3");
			list.add(assistant);
			assistant = new ZjXmCqjxProjectExecutiveAssistant();
			assistant.setAssessmentState("6");
			list.add(assistant);
			assistant = new ZjXmCqjxProjectExecutiveAssistant();
			assistant.setAssessmentState("7");
			list.add(assistant);
			zjXmCqjxProjectExecutiveAssistant.setAssistantList(list);
			num = zjXmCqjxProjectExecutiveAssistantMapper
					.selectZjXmCqjxProjectAssistantLeaderTodoCount(zjXmCqjxProjectExecutiveAssistant);
		} else if (StrUtil.equals(leaderFlag, "0")) {
			if (StrUtil.equals(state, "0")) {
//        			zjXmCqjxProjectExecutiveAssistant.setAssessmentType("2");
				zjXmCqjxProjectExecutiveAssistant.setAssessmentType(type);
				zjXmCqjxProjectExecutiveAssistant.setCreateUser(userKey);
				assistant.setAssessmentState("0");
				list.add(assistant);
				assistant = new ZjXmCqjxProjectExecutiveAssistant();
				assistant.setAssessmentState("2");
				list.add(assistant);
				zjXmCqjxProjectExecutiveAssistant.setAssistantList(list);
			} else {
//        			zjXmCqjxProjectExecutiveAssistant.setAssessmentType("2");
				zjXmCqjxProjectExecutiveAssistant.setAssessmentType(type);
				zjXmCqjxProjectExecutiveAssistant.setCreateUser(userKey);
				assistant = new ZjXmCqjxProjectExecutiveAssistant();
				assistant.setAssessmentState("5");
				list.add(assistant);
				zjXmCqjxProjectExecutiveAssistant.setAssistantList(list);
			}
			num = zjXmCqjxProjectExecutiveAssistantMapper
					.selectZjXmCqjxProjectExecutiveAssistantTodoCount(zjXmCqjxProjectExecutiveAssistant);
		}
		return repEntity.ok(num);
	}

	@Override
	public ResponseEntity zjXmCqjxProjectAssistantMonthScoreTask(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		// 设置权重
		BigDecimal executiveScoreSum = null;// 明细得分合计
		BigDecimal cooperationScore = null;// 协作性得分
		BigDecimal disciplineScore = null;// 纪律性得分
		BigDecimal taskScore = null;// 纪律性得分
		// 将所有员工数据查出
		zjXmCqjxProjectExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
		zjXmCqjxProjectExecutiveAssistant.setAssessmentState("8");
		zjXmCqjxProjectExecutiveAssistant.setDisciplineFlag("1");
		zjXmCqjxProjectExecutiveAssistant.setCooperationFlag("1");
		zjXmCqjxProjectExecutiveAssistant.setTaskFlag("1");
		List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByZjXmCqjxProjectExecutiveAssistantList(zjXmCqjxProjectExecutiveAssistant);
		for (ZjXmCqjxProjectExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
			cooperationScore = new BigDecimal(assistant.getCooperationScore());
			disciplineScore = new BigDecimal(assistant.getDisciplineScore());
			taskScore = new BigDecimal(assistant.getTaskScore());
			executiveScoreSum = cooperationScore.add(disciplineScore).add(taskScore);
			ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper
					.selectByPrimaryKey(assistant.getExecutiveId());
			dbAssistant.setQuarterScore(executiveScoreSum.doubleValue());
			dbAssistant.setModifyUserInfo("admin", "admin");
			zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
		}
		return repEntity.ok("");
	}

	/**
	 * 
	 * @param userName                 ==姓名
	 * @param dbOpinionContent==数据库里的值
	 * @param opinionContent===过来的值
	 * @return
	 */
	private String getOpinionContent(String userName, String dbOpinionContent, String opinionContent) {
		if (StrUtil.isNotEmpty(opinionContent)) {
			opinionContent = StrUtil.isEmpty(dbOpinionContent) ? opinionContent
					: dbOpinionContent + "<br><br>" + opinionContent;
			opinionContent += "<br>" + userName + "  " + DateUtil.formatDateTime(new Date());
		}
		return opinionContent;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity zjXmCqjxExecutiveAssistantSpecialLaunch(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendUserKey = "";
		String sendId = "";
		String state = "";
		String result = checkContent(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
		if (!StrUtil.equals(result, "0")) {
			return repEntity.layerMessage("NO", result);
		}
		int flag = 0;
		ZjXmCqjxProjectExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper
				.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getExecutiveId());
//		if (!StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "0")
//				&& !StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")
//				&& !StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
//			return repEntity.layerMessage("NO", "请勿重复发起！");
//		}
		// 此方法针对项目特殊情况（部门下的各类别的人会选择不同的部门负责人、分管领导、主管领导来审批）
		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
			// 如果是员工
			if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
				String[] strArr = zjXmCqjxProjectExecutiveAssistant.getApprovalUserId().split("-");
				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "0")) {
					ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
					zjXmCqjxAssistantLeaderApproval.setOtherType("0");
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setLeaderId(strArr[0]);
					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(strArr[2]);
					zjXmCqjxAssistantLeaderApproval.setLeaderName(strArr[1]);
					sendUserKey = strArr[0];
					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
					state = "1";					
				}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "1")){
					ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					if (!StrUtil.equals(approval.getApprovalFlag(), "1")) {
						return repEntity.layerMessage("NO", "已经审批过了！");
					}
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
					approval.setApprovalFlag("3");
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(
							getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
									zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));
					dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
					ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setLeaderId(strArr[0]);
					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(strArr[2]);
					zjXmCqjxAssistantLeaderApproval.setLeaderName(strArr[1]);
					sendUserKey = strArr[0];
					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
					state = "3";						
				}
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
				// 如果是项目中层干部
				ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				String[] strArr = zjXmCqjxProjectExecutiveAssistant.getApprovalUserId().split("-");
				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "0")) {
					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setLeaderId(strArr[0]);
					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(strArr[2]);
					zjXmCqjxAssistantLeaderApproval.setLeaderName(strArr[1]);
					sendUserKey = strArr[0];
					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
					state = "1";					
				}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
					ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					if (!StrUtil.equals(approval.getApprovalFlag(), "1")) {
						return repEntity.layerMessage("NO", "已经审批过了！");
					}
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
					approval.setApprovalFlag("3");
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(
							getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
									zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));
					dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setLeaderId(strArr[0]);
					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(strArr[2]);
					zjXmCqjxAssistantLeaderApproval.setLeaderName(strArr[1]);
					sendUserKey = strArr[0];
					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
					state = "3";						
				}
			} else if (StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
				// 如果是领导班子副职
				ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
				String[] strArr = zjXmCqjxProjectExecutiveAssistant.getApprovalUserId().split("-");
				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "0")) {
					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setLeaderId(strArr[0]);
					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(strArr[2]);
					zjXmCqjxAssistantLeaderApproval.setLeaderName(strArr[1]);
					sendUserKey = strArr[0];
					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
					state = "1";					
				}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
					ZjXmCqjxProjectAssistantLeaderApproval approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					if (!StrUtil.equals(approval.getApprovalFlag(), "1")) {
						return repEntity.layerMessage("NO", "已经审批过了！");
					}
					approval = new ZjXmCqjxProjectAssistantLeaderApproval();
					approval = zjXmCqjxProjectAssistantLeaderApprovalMapper
							.selectByPrimaryKey(zjXmCqjxProjectExecutiveAssistant.getAssistantLeaderApprovalId());
					approval.setLeaderOption(zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption());
					approval.setApprovalFlag("3");
					zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(
							getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
									zjXmCqjxProjectExecutiveAssistant.getChargeLeaderOption()));
					dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
					zjXmCqjxAssistantLeaderApproval.setOtherType("3");
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
					zjXmCqjxAssistantLeaderApproval.setLeaderId(strArr[0]);
					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(strArr[2]);
					zjXmCqjxAssistantLeaderApproval.setLeaderName(strArr[1]);
					sendUserKey = strArr[0];
					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
					state = "3";						
				}
			}
				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
				dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
				dbzjXmCqjxExecutiveAssistant.setAssessmentState(state);
				dbzjXmCqjxExecutiveAssistant.setLeaderSee("0");
			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
//			if (StrUtil.equals(zjXmCqjxProjectExecutiveAssistant.getAssessmentState(), "1")) {
				// 如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
				ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
				assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
				assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
				assistant.setAssessmentState("0");
				List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper
						.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
				if (assistantList.size() == 0) {
					assistant.setAssessmentState(state);
					assistantList = zjXmCqjxProjectExecutiveAssistantMapper
							.selectListByZjXmCqjxProjectExecutiveAssistant(assistant);
					for (ZjXmCqjxProjectExecutiveAssistant ass : assistantList) {
						ass.setLeaderSee("1");
						ass.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKey(ass);
					}
					if (assistantList.size() > 0) {
						ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
								.selectByPrimaryKey(assistantList.get(0).getManagerId());
						// 给领导发送消息
						String content = "请于"
								+ DateUtil.format(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate(),
										"yyyy-MM-dd HH:mm:ss")
								+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程审批";
						SysUser user = userService.getSysUserByUserKey(sendUserKey);
						sendId = user.getUserId();
//   					sendId = "haiwei_xichengjian_test";
						weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
					}
				}
//			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxProjectExecutiveAssistant);
		}
	}

	@Override
	public ResponseEntity getZjXmCqjxProjectComprehensiveRanking(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		if (StrUtil.isNotEmpty(zjXmCqjxProjectExecutiveAssistant.getYears())) {
			zjXmCqjxProjectExecutiveAssistant
					.setAssessmentYears(DateUtil.parse(zjXmCqjxProjectExecutiveAssistant.getYears(), "yyyy-MM"));
		}
		zjXmCqjxProjectExecutiveAssistant.setAssessmentState("8");
		List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(zjXmCqjxProjectExecutiveAssistant);
		if(assistantList.size()>0) {
		      // 对象根据年龄属性降序排序
			assistantList = assistantList.stream().sorted(Comparator.comparing(ZjXmCqjxProjectExecutiveAssistant::getQuarterScore).reversed()).collect(toList());
			ZjXmCqjxProjectExecutiveAssistant assistant = assistantList.get(assistantList.size()-1);
	        //过滤出符合条件的数据
	        List<ZjXmCqjxProjectExecutiveAssistant> filterList = assistantList.stream().filter(a -> a.getQuarterScore() == (assistant.getQuarterScore())).collect(toList());
	        for(ZjXmCqjxProjectExecutiveAssistant ass : filterList) {
	        	if(StrUtil.isEmpty(ass.getRankNum())) {
	            	//查询上个月的记录
	            	ZjXmCqjxProjectExecutiveAssistant exAssistant = new ZjXmCqjxProjectExecutiveAssistant();
	            	exAssistant.setCreateUser(ass.getCreateUser());
	            	exAssistant.setAssessmentType(ass.getAssessmentType());
	            	exAssistant.setAssessmentYears(DateUtil.offsetMonth(DateUtil.parse(zjXmCqjxProjectExecutiveAssistant.getYears(), "yyyy-MM"), -1));
	            	List<ZjXmCqjxProjectExecutiveAssistant> exAssistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exAssistant);
	            	if(exAssistantList.size()>0) {
	            		if(StrUtil.equals(exAssistantList.get(0).getRankNum(), "1")) {
	                		ass.setRankNum("2");
	            		}else if(StrUtil.equals(exAssistantList.get(0).getRankNum(), "2")) {
	                		ass.setRankNum("3");
	            		}else if(StrUtil.equals(exAssistantList.get(0).getRankNum(), "3")) {
	                		ass.setRankNum("3");	
	            		}else {
	                		ass.setRankNum("1");        			
	            		}
	            	}else {        		
	            		ass.setRankNum("1");
	            	}
	            	zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(ass);        		
	        	}
	        }
			assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(zjXmCqjxProjectExecutiveAssistant);
	        // 对象根据年龄属性降序排序
			assistantList = assistantList.stream().sorted(Comparator.comparing(ZjXmCqjxProjectExecutiveAssistant::getQuarterScore).reversed()).collect(toList());			
		}
		return repEntity.okList(assistantList, assistantList.size());
	}
	/**
	 * 
	 * 项目绩效任务已办
	 */
	@Override
	public ResponseEntity getZjXmCqjxProjectAssistantDoneList(
			ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		zjXmCqjxProjectExecutiveAssistant.setCreateUser(userKey);
		zjXmCqjxProjectExecutiveAssistant.setLeaderSee("1");
		// 分页查询
		PageHelper.startPage(zjXmCqjxProjectExecutiveAssistant.getPage(), zjXmCqjxProjectExecutiveAssistant.getLimit());
		// 获取数据
		List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxProjectExecutiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectProjectLeaderDoneListByUserKey(zjXmCqjxProjectExecutiveAssistant);
		for (ZjXmCqjxProjectExecutiveAssistant assistant : zjXmCqjxProjectExecutiveAssistantList) {
			ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
			head.setDepartmentId(assistant.getDepartmentId());
			List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(head);
			if (headList.size() > 0) {
				ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
				detail.setOtherId(headList.get(0).getDepartmentHeadId());
				detail.setOtherType("1");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if (detailList.size() == 0) {
					assistant.setHaveChangerLeader("1");
				} else {
					assistant.setHaveChangerLeader("0");
				}
			}
			if (!StrUtil.equals(assistant.getState(), "2")) {
				assistant.setFirstScoreClosingEndDate(null);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmCqjxProjectExecutiveAssistant> p = new PageInfo<>(zjXmCqjxProjectExecutiveAssistantList);
		return repEntity.okList(zjXmCqjxProjectExecutiveAssistantList, p.getTotal());
	}

}