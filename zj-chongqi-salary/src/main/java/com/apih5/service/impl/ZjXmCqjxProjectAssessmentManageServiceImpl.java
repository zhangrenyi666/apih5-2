package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.zjoa.common.BusinessZj;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectEvaluationApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectOaDeptMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectOverallEvaluationAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSuperviseApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOaDeptMember;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;
import com.apih5.service.ZjXmCqjxProjectAssessmentManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectAssessmentManageService")
public class ZjXmCqjxProjectAssessmentManageServiceImpl implements ZjXmCqjxProjectAssessmentManageService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmCqjxProjectAssessmentManageMapper zjXmCqjxProjectAssessmentManageMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectOaDeptMemberMapper zjXmCqjxProjectOaDeptMemberMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private SysDepartmentService sysDepartmentService;

	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;

	@Autowired(required = true)
	private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDisciplineAssessmentMemberMapper zjXmCqjxProjectDisciplineAssessmentMemberMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDisciplineAssessmentMapper zjXmCqjxProjectDisciplineAssessmentMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDepartmentHeadMapper zjXmCqjxProjectDepartmentHeadMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectOverallEvaluationAssistantMapper zjXmCqjxProjectOverallEvaluationAssistantMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectEvaluationApprovalMapper zjXmCqjxProjectEvaluationApprovalMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectSuperviseApprovalMapper zjXmCqjxProjectSuperviseApprovalMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectAssistantLeaderApprovalMapper zjXmCqjxProjectAssistantLeaderApprovalMapper;

	@Override
	public ResponseEntity getZjXmCqjxProjectAssessmentManageListByCondition(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String userId = TokenUtils.getUserId(request);
		if (zjXmCqjxProjectAssessmentManage == null) {
			zjXmCqjxProjectAssessmentManage = new ZjXmCqjxProjectAssessmentManage();
		}
		if(!StrUtil.equals(userId, "chongqgs_admin")) {			
			zjXmCqjxProjectAssessmentManage.setCreateUser(userKey);
		}
		// ????????????
		PageHelper.startPage(zjXmCqjxProjectAssessmentManage.getPage(), zjXmCqjxProjectAssessmentManage.getLimit());
		// ????????????
		List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManageList = zjXmCqjxProjectAssessmentManageMapper
				.selectByZjXmCqjxProjectAssessmentManageList(zjXmCqjxProjectAssessmentManage);
		for (ZjXmCqjxProjectAssessmentManage manage : zjXmCqjxProjectAssessmentManageList) {
			// ??????????????????
			ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
			zjXmCqjxOaDeptMember.setOtherId(manage.getManagerId());
			zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
			List zcMemberList = zjXmCqjxProjectOaDeptMemberMapper
					.selectByZjXmCqjxProjectOaDeptMemberList(zjXmCqjxOaDeptMember);
			manage.setAssessmentMemberList(BusinessZj.dbToPageByMember(zcMemberList));
			// ??????????????????
			zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
			zjXmCqjxOaDeptMember.setOtherId(manage.getManagerId());
			zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("0");
			List deptList = zjXmCqjxProjectOaDeptMemberMapper
					.selectByZjXmCqjxProjectOaDeptMemberList(zjXmCqjxOaDeptMember);
			manage.setAssessmentDeptList(BusinessZj.dbToPageByDepartment(deptList));
		}
		// ??????????????????
		PageInfo<ZjXmCqjxProjectAssessmentManage> p = new PageInfo<>(zjXmCqjxProjectAssessmentManageList);
		return repEntity.okList(zjXmCqjxProjectAssessmentManageList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmCqjxProjectAssessmentManageDetails(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		if (zjXmCqjxProjectAssessmentManage == null) {
			zjXmCqjxProjectAssessmentManage = new ZjXmCqjxProjectAssessmentManage();
		}
		// ????????????
		ZjXmCqjxProjectAssessmentManage dbZjXmCqjxProjectAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxProjectAssessmentManage.getManagerId());
		// ????????????
		if (dbZjXmCqjxProjectAssessmentManage != null) {
			return repEntity.ok(dbZjXmCqjxProjectAssessmentManage);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	public ResponseEntity saveZjXmCqjxProjectAssessmentManage(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmCqjxProjectAssessmentManage manage = new ZjXmCqjxProjectAssessmentManage();
		manage.setAssessmentTitle(zjXmCqjxProjectAssessmentManage.getAssessmentTitle());
		manage.setCreateUser(userKey);
		if (zjXmCqjxProjectAssessmentManageMapper.selectByZjXmCqjxProjectAssessmentManageList(manage).size() > 0) {
			return repEntity.layerMessage("NO", "???????????????????????????");
		}
//		manage.setAssessmentTitle("");
//		manage.setAssessmentType(zjXmCqjxProjectAssessmentManage.getAssessmentType());
//		manage.setAssessmentYears(zjXmCqjxProjectAssessmentManage.getAssessmentYears());
//		manage.setAssessmentQuarter(zjXmCqjxProjectAssessmentManage.getAssessmentQuarter());
//		if (zjXmCqjxProjectAssessmentManageMapper.selectByZjXmCqjxProjectAssessmentManageList(manage).size() > 0) {
//			return repEntity.layerMessage("NO", "?????????????????????????????????????????????");
//		}
		zjXmCqjxProjectAssessmentManage.setManagerId(UuidUtil.generate());
		if (zjXmCqjxProjectAssessmentManage.getAssessmentDeptList() != null
				&& zjXmCqjxProjectAssessmentManage.getAssessmentDeptList().getOaDepartmentList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectAssessmentManage.getAssessmentDeptList();
			for (OADepartment oadept : departmentHead.getOaDepartmentList()) {
				ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
				zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
				zjXmCqjxOaDeptMember.setOaUserId(oadept.getId());
				zjXmCqjxOaDeptMember.setOaUserName(oadept.getName());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("0");
				zjXmCqjxOaDeptMember.setOaOrgId(oadept.getOrgId());
				zjXmCqjxProjectAssessmentManage.setAssessmentDeptOrgId(oadept.getOrgId());
				zjXmCqjxProjectAssessmentManage.setAssessmentDept(oadept.getId());
				zjXmCqjxProjectAssessmentManage.setAssessmentDeptName(oadept.getName());
				SysDepartment department = sysDepartmentService.getSysDepartmentByPrimaryKey(oadept.getOrgId());
				if (department != null) {
					zjXmCqjxOaDeptMember.setOaOrgName(department.getDepartmentName());
				}
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxProjectAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
				zjXmCqjxProjectOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
			}
		}
		if (zjXmCqjxProjectAssessmentManage.getAssessmentMemberList() != null
				&& zjXmCqjxProjectAssessmentManage.getAssessmentMemberList().getOaMemberList().size() > 0) {
			OADepartmentMember member = zjXmCqjxProjectAssessmentManage.getAssessmentMemberList();
			for (OAMember oaMember : member.getOaMemberList()) {
				ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
				zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
				zjXmCqjxOaDeptMember.setOaUserId(oaMember.getUserid());
				SysUser user = userService.getSysUserByUserKey(zjXmCqjxOaDeptMember.getOaUserId());
				zjXmCqjxOaDeptMember.setMobile(user.getMobile());
				zjXmCqjxOaDeptMember.setOaUserName(oaMember.getName());
				zjXmCqjxOaDeptMember.setOaOrgId(oaMember.getOrgId());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
				SysDepartment department = sysDepartmentService.getSysDepartmentByPrimaryKey(oaMember.getOrgId());
				if (department != null) {
					zjXmCqjxOaDeptMember.setOaOrgName(department.getDepartmentName());
				}
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxProjectAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
				zjXmCqjxProjectOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
			}
		}
		zjXmCqjxProjectAssessmentManage.setState("0");
		zjXmCqjxProjectAssessmentManage.setCreateUserInfo(userKey, realName);
		int flag = zjXmCqjxProjectAssessmentManageMapper.insert(zjXmCqjxProjectAssessmentManage);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmCqjxProjectAssessmentManage);
		}
	}

	@Override
	public ResponseEntity updateZjXmCqjxProjectAssessmentManage(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmCqjxProjectAssessmentManage dbzjXmCqjxProjectAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxProjectAssessmentManage.getManagerId());
		if (dbzjXmCqjxProjectAssessmentManage != null
				&& StrUtil.isNotEmpty(dbzjXmCqjxProjectAssessmentManage.getManagerId())) {
			ZjXmCqjxProjectOaDeptMember oaDeptMember = new ZjXmCqjxProjectOaDeptMember();
			oaDeptMember.setOtherId(zjXmCqjxProjectAssessmentManage.getManagerId());
			List<ZjXmCqjxProjectOaDeptMember> memberList = zjXmCqjxProjectOaDeptMemberMapper
					.selectByZjXmCqjxProjectOaDeptMemberList(oaDeptMember);
			if (memberList.size() > 0) {
				oaDeptMember = new ZjXmCqjxProjectOaDeptMember();
				oaDeptMember.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectOaDeptMemberMapper.batchDeleteUpdateZjXmCqjxProjectOaDeptMember(memberList,
						oaDeptMember);
			}
			if (zjXmCqjxProjectAssessmentManage.getAssessmentDeptList() != null
					&& zjXmCqjxProjectAssessmentManage.getAssessmentDeptList().getOaDepartmentList().size() > 0) {
				OADepartmentMember departmentHead = zjXmCqjxProjectAssessmentManage.getAssessmentDeptList();
				for (OADepartment oadept : departmentHead.getOaDepartmentList()) {
					ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
					zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
					zjXmCqjxOaDeptMember.setOaUserId(oadept.getId());
					zjXmCqjxOaDeptMember.setOaUserName(oadept.getName());
					zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("0");
					zjXmCqjxOaDeptMember.setOaOrgId(oadept.getOrgId());
					zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxProjectAssessmentManage.getManagerId());
					zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
					dbzjXmCqjxProjectAssessmentManage.setAssessmentDeptOrgId(oadept.getOrgId());
					dbzjXmCqjxProjectAssessmentManage.setAssessmentDept(oadept.getId());
					dbzjXmCqjxProjectAssessmentManage.setAssessmentDeptName(oadept.getName());
					zjXmCqjxProjectOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
				}
			}
			if (zjXmCqjxProjectAssessmentManage.getAssessmentMemberList() != null
					&& zjXmCqjxProjectAssessmentManage.getAssessmentMemberList().getOaMemberList().size() > 0) {
				OADepartmentMember member = zjXmCqjxProjectAssessmentManage.getAssessmentMemberList();
				for (OAMember oaMember : member.getOaMemberList()) {
					ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
					zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
					zjXmCqjxOaDeptMember.setOaUserId(oaMember.getUserid());
					SysUser user = userService.getSysUserByUserKey(zjXmCqjxOaDeptMember.getOaUserId());
					SysDepartment department = sysDepartmentService.getSysDepartmentByPrimaryKey(oaMember.getOrgId());
					zjXmCqjxOaDeptMember.setOaOrgName(department.getDepartmentName());
					zjXmCqjxOaDeptMember.setMobile(user.getMobile());
					zjXmCqjxOaDeptMember.setOaUserName(oaMember.getName());
					zjXmCqjxOaDeptMember.setOaOrgId(oaMember.getOrgId());
					zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
					zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxProjectAssessmentManage.getManagerId());
					zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
				}
			}
			// ????????????
			dbzjXmCqjxProjectAssessmentManage.setAssessmentType(zjXmCqjxProjectAssessmentManage.getAssessmentType());
			// ????????????
			dbzjXmCqjxProjectAssessmentManage.setAssessmentYears(zjXmCqjxProjectAssessmentManage.getAssessmentYears());
			// ????????????
			dbzjXmCqjxProjectAssessmentManage.setAssessmentTitle(zjXmCqjxProjectAssessmentManage.getAssessmentTitle());
			// ????????????
			dbzjXmCqjxProjectAssessmentManage
					.setAssessmentQuarter(zjXmCqjxProjectAssessmentManage.getAssessmentQuarter());
			// ????????????
			dbzjXmCqjxProjectAssessmentManage
					.setAssessmentRemarks(zjXmCqjxProjectAssessmentManage.getAssessmentRemarks());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setDutyClosingStartDate(zjXmCqjxProjectAssessmentManage.getDutyClosingStartDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setDutyClosingEndDate(zjXmCqjxProjectAssessmentManage.getDutyClosingEndDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFirstDutyClosingStartDate(zjXmCqjxProjectAssessmentManage.getFirstDutyClosingStartDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFirstDutyClosingEndDate(zjXmCqjxProjectAssessmentManage.getFirstDutyClosingEndDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFinalDutyClosingStartDate(zjXmCqjxProjectAssessmentManage.getFinalDutyClosingStartDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFinalDutyClosingEndDate(zjXmCqjxProjectAssessmentManage.getFinalDutyClosingEndDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setScoreClosingStartDate(zjXmCqjxProjectAssessmentManage.getScoreClosingStartDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setScoreClosingEndDate(zjXmCqjxProjectAssessmentManage.getScoreClosingEndDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFirstScoreClosingStartDate(zjXmCqjxProjectAssessmentManage.getFirstScoreClosingStartDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFirstScoreClosingEndDate(zjXmCqjxProjectAssessmentManage.getFirstScoreClosingEndDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFinalScoreClosingStartDate(zjXmCqjxProjectAssessmentManage.getFinalScoreClosingStartDate());
			// ??????????????????????????????
			dbzjXmCqjxProjectAssessmentManage
					.setFinalScoreClosingEndDate(zjXmCqjxProjectAssessmentManage.getFinalScoreClosingEndDate());
			// ??????
//           dbzjXmCqjxProjectAssessmentManage.setState(zjXmCqjxProjectAssessmentManage.getState());
			// ??????
			dbzjXmCqjxProjectAssessmentManage.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectAssessmentManageMapper.updateByPrimaryKey(dbzjXmCqjxProjectAssessmentManage);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxProjectAssessmentManage);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmCqjxProjectAssessmentManage(
			List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManageList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmCqjxProjectAssessmentManageList != null && zjXmCqjxProjectAssessmentManageList.size() > 0) {
			for (ZjXmCqjxProjectAssessmentManage manage : zjXmCqjxProjectAssessmentManageList) {
				if (StrUtil.equals(manage.getState(), "1") || StrUtil.equals(manage.getState(), "2")) {
					return repEntity.layerMessage("NO", "????????????????????????????????????????????????????????????");
				}
			}
			ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage = new ZjXmCqjxProjectAssessmentManage();
			zjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxProjectAssessmentManageMapper.batchDeleteUpdateZjXmCqjxProjectAssessmentManage(
					zjXmCqjxProjectAssessmentManageList, zjXmCqjxAssessmentManage);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmCqjxProjectAssessmentManageList);
		}
	}

	@Override
	public ResponseEntity zjXmCqjxProjectAssessmentManageDetailByQuarter(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		if (zjXmCqjxProjectAssessmentManage == null) {
			zjXmCqjxProjectAssessmentManage = new ZjXmCqjxProjectAssessmentManage();
		}
		ZjXmCqjxProjectExecutiveAssistant zXmCqjxExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
		zXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxProjectAssessmentManage.getManagerId());
		zXmCqjxExecutiveAssistant.setDepartmentName(zjXmCqjxProjectAssessmentManage.getDeptName());
		zXmCqjxExecutiveAssistant.setCreateUserName(zjXmCqjxProjectAssessmentManage.getCreateUserName());
		zXmCqjxExecutiveAssistant.setPosition(zjXmCqjxProjectAssessmentManage.getPosition());
		// ????????????
		PageHelper.startPage(zjXmCqjxProjectAssessmentManage.getPage(), zjXmCqjxProjectAssessmentManage.getLimit());
		// ????????????
		List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxProjectExecutiveAssistantMapper
				.selectProjectAssistantDetailByQuarter(zXmCqjxExecutiveAssistant);
		// ??????????????????
		PageInfo<ZjXmCqjxProjectExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
		return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}

	@Override
	public ResponseEntity zjXmCqjxProjectAssessmentManageSendMessage(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity getZjXmCqjxProjectAssessmentManageListByDeptHeader(
			ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		// ???????????????????????????????????????
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
			ZjXmCqjxProjectDepartmentHead head = zjXmCqjxProjectDepartmentHeadMapper
					.selectByPrimaryKey(detailList.get(0).getOtherId());
			if (head != null) {
				zjXmCqjxProjectAssessmentManage.setDeptId(head.getDepartmentId());
			} else {
				zjXmCqjxProjectAssessmentManage.setDeptId(detailList.get(0).getOaOrgId());
			}
		}
		// ????????????
		PageHelper.startPage(zjXmCqjxProjectAssessmentManage.getPage(), zjXmCqjxProjectAssessmentManage.getLimit());
		// ????????????
		List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxAssessmentManageList = zjXmCqjxProjectAssessmentManageMapper
				.getZjXmCqjxAssessmentManageListByDeptHeader(zjXmCqjxProjectAssessmentManage);
		// ??????????????????
		PageInfo<ZjXmCqjxProjectAssessmentManage> p = new PageInfo<>(zjXmCqjxAssessmentManageList);

		return repEntity.okList(zjXmCqjxAssessmentManageList, p.getTotal());
	}

	@Override
	public ResponseEntity batchZjXmCqjxProjectAssessmentManageSendMessage(
			List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManageList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendId = "";
		ZjXmCqjxProjectDisciplineAssessment zjXmCqjxDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
		ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxProjectDisciplineAssessmentMember();
		for (ZjXmCqjxProjectAssessmentManage zjXmCqjxAssessmentManage : zjXmCqjxProjectAssessmentManageList) {
			// ???????????????????????????????????????????????????????????????
			if (StrUtil.equals(zjXmCqjxAssessmentManage.getState(), "2")) {
				zjXmCqjxDisciplineAssessment.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
				List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxProjectDisciplineAssessmentMapper
						.selectByZjXmCqjxProjectDisciplineAssessmentList(zjXmCqjxDisciplineAssessment);
				if (zjXmCqjxDisciplineAssessmentList.size() > 0) {
					zjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentList.get(0);
				} else {
					// ????????????????????????
					zjXmCqjxDisciplineAssessment.setDisciplineId(UuidUtil.generate());
					zjXmCqjxDisciplineAssessment.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
					zjXmCqjxDisciplineAssessment.setDisciplineTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
					zjXmCqjxDisciplineAssessment.setDisciplineYears(zjXmCqjxAssessmentManage.getAssessmentYears());
					zjXmCqjxDisciplineAssessment.setDisciplineQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
					zjXmCqjxDisciplineAssessment.setDisciplineRemarks(zjXmCqjxAssessmentManage.getAssessmentRemarks());
					zjXmCqjxDisciplineAssessment.setDisciplineState("0");
					zjXmCqjxDisciplineAssessment.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDisciplineAssessmentMapper.insert(zjXmCqjxDisciplineAssessment);
				}
				ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
				List<ZjXmCqjxProjectOaDeptMember> memberList = zjXmCqjxProjectOaDeptMemberMapper
						.selectByZjXmCqjxProjectOaDeptMemberList(zjXmCqjxOaDeptMember);
				for (ZjXmCqjxProjectOaDeptMember member : memberList) {
					zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxProjectDisciplineAssessmentMember();
					zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
					zjXmCqjxDisciplineAssessmentMember.setOaUserId(member.getOaUserId());
					if (zjXmCqjxProjectDisciplineAssessmentMemberMapper
							.selectByZjXmCqjxProjectDisciplineAssessmentMemberList(zjXmCqjxDisciplineAssessmentMember)
							.size() == 0) {
						// ???????????????????????????
						zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxProjectDisciplineAssessmentMember();
						zjXmCqjxDisciplineAssessmentMember.setZcOaId(UuidUtil.generate());
						zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
						zjXmCqjxDisciplineAssessmentMember.setOaUserId(member.getOaUserId());
						zjXmCqjxDisciplineAssessmentMember.setOaUserName(member.getOaUserName());
						zjXmCqjxDisciplineAssessmentMember.setOaOrgId(member.getOaOrgId());
						zjXmCqjxDisciplineAssessmentMember.setOaOrgName(member.getOaOrgName());
						SysUser user = userService.getSysUserByUserKey(member.getOaUserId());
						zjXmCqjxDisciplineAssessmentMember.setMobile(user.getMobile());
						zjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("0");
						zjXmCqjxDisciplineAssessmentMember.setCreateUserInfo(userKey, realName);
						zjXmCqjxProjectDisciplineAssessmentMemberMapper.insert(zjXmCqjxDisciplineAssessmentMember);
						sendId = sendId + user.getUserId() + "|";
					}
					String content = "??????"
							+ DateUtil.format(zjXmCqjxAssessmentManage.getScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
							+ " ?????????" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "????????????";
//					sendId = "haiwei_xichengjian_test";
					weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);

					// ??????????????????
					sendId = "";
					ZjXmCqjxProjectOverallEvaluationAssistant assistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
					assistant.setCreateUser(member.getOaUserId());
					assistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
					assistant.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
					assistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
					assistant.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
					if (zjXmCqjxProjectOverallEvaluationAssistantMapper
							.selectByZjXmCqjxProjectOverallEvaluationAssistantList(assistant).size() == 0) {
						assistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
						assistant.setOverallEvaluationId(UuidUtil.generate());
						assistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
						assistant.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
						assistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
						assistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
						assistant.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
						assistant.setAssessmentState("0");
						assistant.setCreateUserInfo(member.getOaUserId(), member.getOaUserName());
						zjXmCqjxProjectOverallEvaluationAssistantMapper.insert(assistant);
						// ???????????????
						ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
//						head.setDepartmentId(zjXmCqjxAssessmentManage.getAssessmentDept());
						head.setDepartmentId(member.getOaOrgId());
						// ????????????????????????????????????????????????????????????
						if (StrUtil.equals(zjXmCqjxAssessmentManage.getAssessmentType(), "1")) {
							List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);
							if (headList.size() > 0) {
								headList.get(0).getDepartmentHeadId();
								ZjXmCqjxProjectDepartmentHeadDetail headDetial = new ZjXmCqjxProjectDepartmentHeadDetail();
								headDetial.setOtherId(headList.get(0).getDepartmentHeadId());
								headDetial.setOtherType("5");
								List<ZjXmCqjxProjectDepartmentHeadDetail> headDetailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(headDetial);
								if (headDetailList.size() > 0) {
									ZjXmCqjxProjectEvaluationApproval approval = new ZjXmCqjxProjectEvaluationApproval();
									for (int i = 0; i < headDetailList.size(); i++) {
										approval.setAssistantLeaderApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("5");
										approval.setItemType("1");
										approval.setExecutiveId(assistant.getOverallEvaluationId());
										approval.setLeaderId(headDetailList.get(i).getOaUserId());
										approval.setLeaderName(headDetailList.get(i).getOaUserName());
										approval.setLeaderOrgId(headDetailList.get(i).getOaOrgId());
										approval.setApprovalFlag("1");
										sendId = sendId + headDetailList.get(i).getOaUserId() + "|";
//										if (i == 0) {
//											approval.setApprovalFlag("1");
//											sendId = sendId + headDetailList.get(i).getOaUserId() + "|";
//										} else {
//											approval.setApprovalFlag("0");
//										}
										zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);
									}
								}
							}
						} else
						// ???????????????????????????????????????????????????????????????n-1??????
						if (StrUtil.equals(zjXmCqjxAssessmentManage.getAssessmentType(), "2")) {
							List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);
							if (headList.size() > 0) {
								headList.get(0).getDepartmentHeadId();
								ZjXmCqjxProjectDepartmentHeadDetail headDetial = new ZjXmCqjxProjectDepartmentHeadDetail();
								headDetial.setOtherId(headList.get(0).getDepartmentHeadId());
								headDetial.setOtherType("5");
								List<ZjXmCqjxProjectDepartmentHeadDetail> headDetailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(headDetial);
								if (headDetailList.size() > 0) {
									ZjXmCqjxProjectEvaluationApproval approval = new ZjXmCqjxProjectEvaluationApproval();
									for (int i = 0; i < headDetailList.size(); i++) {
										if (!StrUtil.equals(headDetailList.get(i).getOaUserId(),
												member.getOaUserId())) {
											approval.setAssistantLeaderApprovalId(UuidUtil.generate());
											approval.setCreateUserInfo(userKey, realName);
											approval.setOtherType("5");
											approval.setItemType("1");
											approval.setExecutiveId(assistant.getOverallEvaluationId());
											approval.setLeaderId(headDetailList.get(i).getOaUserId());
											approval.setLeaderName(headDetailList.get(i).getOaUserName());
											approval.setLeaderOrgId(headDetailList.get(i).getOaOrgId());
											approval.setApprovalFlag("1");
											zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);
											sendId = sendId + headDetailList.get(i).getOaUserId() + "|";
										}
									}
								}
								// ?????????????????????????????????????????????????????????????????????
								headDetial.setOtherType("7");
								headDetailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(headDetial);
								if (headDetailList.size() > 0) {
									ZjXmCqjxProjectSuperviseApproval approval = new ZjXmCqjxProjectSuperviseApproval();
									for (int i = 0; i < headDetailList.size(); i++) {
										approval.setAssistantLeaderApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("7");
										approval.setExecutiveId(assistant.getOverallEvaluationId());
										approval.setLeaderId(headDetailList.get(i).getOaUserId());
										approval.setLeaderName(headDetailList.get(i).getOaUserName());
										approval.setLeaderOrgId(headDetailList.get(i).getOaOrgId());
										if (i == 0) {
											approval.setApprovalFlag("1");
											sendId = sendId + headDetailList.get(i).getOaUserId() + "|";
										} else {
											approval.setApprovalFlag("0");
										}
										zjXmCqjxProjectSuperviseApprovalMapper.insert(approval);
									}
								}
							}
						} else
						// ????????????????????????????????????????????????????????????
						if (StrUtil.equals(zjXmCqjxAssessmentManage.getAssessmentType(), "3")) {
							// ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
							List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);
							if (headList.size() > 0) {
								headList.get(0).getDepartmentHeadId();
								ZjXmCqjxProjectDepartmentHeadDetail headDetial = new ZjXmCqjxProjectDepartmentHeadDetail();
								headDetial.setOtherId(headList.get(0).getDepartmentHeadId());
								headDetial.setOtherType("0");
								List<ZjXmCqjxProjectDepartmentHeadDetail> headDetailList = zjXmCqjxProjectDepartmentHeadDetailMapper
										.selectByZjXmCqjxProjectDepartmentHeadDetailList(headDetial);
								if (headDetailList.size() > 0) {
									ZjXmCqjxProjectEvaluationApproval approval = new ZjXmCqjxProjectEvaluationApproval();
									if (headDetailList.size() > 1) {
										ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
										exeAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
										exeAssistant.setCreateUser(member.getOaUserId());
										List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
										if(assList.size()>0) {											
											ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
											assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
											assistantApproval.setOtherType("0");
											List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
											if(assistantApprovalList.size()>0) {
												approval.setAssistantLeaderApprovalId(UuidUtil.generate());
												approval.setCreateUserInfo(userKey, realName);
												approval.setOtherType("0");
												approval.setItemType("1");
												approval.setExecutiveId(assistant.getOverallEvaluationId());
												approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
												approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
												approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
												approval.setApprovalFlag("1");
												sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
												zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
											}
										}
									} else {
										approval.setAssistantLeaderApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("0");
										approval.setItemType("1");
										approval.setExecutiveId(assistant.getOverallEvaluationId());
										approval.setLeaderId(headDetailList.get(0).getOaUserId());
										approval.setLeaderName(headDetailList.get(0).getOaUserName());
										approval.setLeaderOrgId(headDetailList.get(0).getOaOrgId());
										approval.setApprovalFlag("1");
										sendId = sendId + headDetailList.get(0).getOaUserId() + "|";
										zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);
									}
								}
							}
						}
					}
				}
				String content = "??????"
						+ DateUtil.format(zjXmCqjxAssessmentManage.getScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
						+ " ?????????" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "????????????????????????";
//				sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
			} else {
				ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
				ZjXmCqjxProjectOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
				List<ZjXmCqjxProjectOaDeptMember> memberList = zjXmCqjxProjectOaDeptMemberMapper
						.selectByZjXmCqjxProjectOaDeptMemberList(zjXmCqjxOaDeptMember);
				for (ZjXmCqjxProjectOaDeptMember member : memberList) {
					zjXmCqjxExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
					zjXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
					zjXmCqjxExecutiveAssistant.setCreateUser(member.getOaUserId());
					if (zjXmCqjxProjectExecutiveAssistantMapper
							.selectListByZjXmCqjxProjectExecutiveAssistant(zjXmCqjxExecutiveAssistant).size() == 0) {
						zjXmCqjxExecutiveAssistant.setExecutiveId(UuidUtil.generate());
						zjXmCqjxExecutiveAssistant.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
						zjXmCqjxExecutiveAssistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
						zjXmCqjxExecutiveAssistant
								.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
						zjXmCqjxExecutiveAssistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
						zjXmCqjxExecutiveAssistant.setAssessmentState("0");
						zjXmCqjxExecutiveAssistant.setAssistantLock("0");
						zjXmCqjxExecutiveAssistant.setDepartmentId(member.getOaOrgId());
						zjXmCqjxExecutiveAssistant.setCreateUserInfo(member.getOaUserId(), member.getOaUserName());
						zjXmCqjxProjectExecutiveAssistantMapper.insert(zjXmCqjxExecutiveAssistant);
						SysUser user = userService.getSysUserByUserKey(member.getOaUserId());
						sendId = sendId + user.getUserId() + "|";
					}
				}
				String content = "??????"
						+ DateUtil.format(zjXmCqjxAssessmentManage.getDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
						+ " ?????????" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "????????????";
//				sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
			}
			ZjXmCqjxProjectAssessmentManage dbzjXmCqjxAssessmentManage = zjXmCqjxProjectAssessmentManageMapper
					.selectByPrimaryKey(zjXmCqjxAssessmentManage.getManagerId());
			dbzjXmCqjxAssessmentManage.setState(zjXmCqjxAssessmentManage.getState());
			dbzjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
			zjXmCqjxProjectAssessmentManageMapper.updateByPrimaryKey(dbzjXmCqjxAssessmentManage);

		}
		return repEntity.layerMessage("NO", "???????????????");
	}
}