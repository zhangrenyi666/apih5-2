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
import com.apih5.mybatis.dao.ZjXmCqjxAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxOaDeptMemberMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.service.ZjXmCqjxAssessmentManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxAssessmentManageService")
public class ZjXmCqjxAssessmentManageServiceImpl implements ZjXmCqjxAssessmentManageService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmCqjxAssessmentManageMapper zjXmCqjxAssessmentManageMapper;

	@Autowired(required = true)
	private ZjXmCqjxOaDeptMemberMapper zjXmCqjxOaDeptMemberMapper;

	@Autowired(required = true)
	private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

	@Autowired
	private SysDepartmentService sysDepartmentService;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private ZjXmCqjxDisciplineAssessmentMapper zjXmCqjxDisciplineAssessmentMapper;

	@Autowired(required = true)
	private ZjXmCqjxDisciplineAssessmentMemberMapper zjXmCqjxDisciplineAssessmentMemberMapper;

	@Autowired(required = true)
	private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;

	@Autowired(required = true)
	private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;

	@Override
	public ResponseEntity getZjXmCqjxAssessmentManageListByCondition(
			ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		if(!StrUtil.equals("chongqgs_suyan", userId) && !StrUtil.equals("chongqgs_admin", userId)) {
			List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxAssessmentManage>();
			return repEntity.okList(zjXmCqjxAssessmentManageList, 0);	
		}
		if (zjXmCqjxAssessmentManage == null) {
			zjXmCqjxAssessmentManage = new ZjXmCqjxAssessmentManage();
		}
		// 分页查询
		PageHelper.startPage(zjXmCqjxAssessmentManage.getPage(), zjXmCqjxAssessmentManage.getLimit());
		// 获取数据
		List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList = zjXmCqjxAssessmentManageMapper
				.selectByZjXmCqjxAssessmentManageList(zjXmCqjxAssessmentManage);
		for (ZjXmCqjxAssessmentManage manage : zjXmCqjxAssessmentManageList) {
			// 获取管理员
			ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
			zjXmCqjxOaDeptMember.setOtherId(manage.getManagerId());
			zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
			List zcMemberList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
			manage.setAssessmentMemberList(BusinessZj.dbToPageByMember(zcMemberList));
			// 使用单位
			zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("0");
			List zcDepartmentList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
			manage.setAssessmentDeptList(BusinessZj.dbToPageByDepartment(zcDepartmentList));
		}
		// 得到分页信息
		PageInfo<ZjXmCqjxAssessmentManage> p = new PageInfo<>(zjXmCqjxAssessmentManageList);

		return repEntity.okList(zjXmCqjxAssessmentManageList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmCqjxAssessmentManageDetails(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		if (zjXmCqjxAssessmentManage == null) {
			zjXmCqjxAssessmentManage = new ZjXmCqjxAssessmentManage();
		}
		// 获取数据
		ZjXmCqjxAssessmentManage dbZjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxAssessmentManage.getManagerId());
		// 数据存在
		if (dbZjXmCqjxAssessmentManage != null) {
			return repEntity.ok(dbZjXmCqjxAssessmentManage);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmCqjxAssessmentManage(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmCqjxAssessmentManage manage = new ZjXmCqjxAssessmentManage();
		manage.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
		if (zjXmCqjxAssessmentManageMapper.selectByZjXmCqjxAssessmentManageList(manage).size() > 0) {
			return repEntity.layerMessage("NO", "标题重复，请修改！");
		}
		manage.setAssessmentTitle("");
		manage.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
		manage.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
		manage.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
		if (zjXmCqjxAssessmentManageMapper.selectByZjXmCqjxAssessmentManageList(manage).size() > 0) {
			return repEntity.layerMessage("NO", "该计划已经生成，请勿重复添加！");
		}
		zjXmCqjxAssessmentManage.setManagerId(UuidUtil.generate());
		if (zjXmCqjxAssessmentManage.getAssessmentDeptList() != null
				&& zjXmCqjxAssessmentManage.getAssessmentDeptList().getOaDepartmentList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxAssessmentManage.getAssessmentDeptList();
			for (OADepartment oadept : departmentHead.getOaDepartmentList()) {
				ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
				zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
				zjXmCqjxOaDeptMember.setOaUserId(oadept.getId());
				zjXmCqjxOaDeptMember.setOaUserName(oadept.getName());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("0");
				zjXmCqjxOaDeptMember.setOaOrgId(oadept.getOrgId());
				SysDepartment department = sysDepartmentService.getSysDepartmentByPrimaryKey(oadept.getOrgId());
				if (department != null) {
					zjXmCqjxOaDeptMember.setOaOrgName(department.getDepartmentName());
				}
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
				zjXmCqjxOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
			}
		}
		if (zjXmCqjxAssessmentManage.getAssessmentMemberList() != null
				&& zjXmCqjxAssessmentManage.getAssessmentMemberList().getOaMemberList().size() > 0) {
			OADepartmentMember member = zjXmCqjxAssessmentManage.getAssessmentMemberList();
			for (OAMember oaMember : member.getOaMemberList()) {
				ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
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
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
				zjXmCqjxOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
			}
		}
		zjXmCqjxAssessmentManage.setState("0");
		zjXmCqjxAssessmentManage.setCreateUserInfo(userKey, realName);
		int flag = zjXmCqjxAssessmentManageMapper.insert(zjXmCqjxAssessmentManage);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmCqjxAssessmentManage);
		}
	}

	@Override
	public ResponseEntity updateZjXmCqjxAssessmentManage(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmCqjxAssessmentManage dbzjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxAssessmentManage.getManagerId());
		ZjXmCqjxOaDeptMember oaDeptMember = new ZjXmCqjxOaDeptMember();
		oaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
		List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper
				.selectByZjXmCqjxOaDeptMemberList(oaDeptMember);
		if (memberList.size() > 0) {
			oaDeptMember = new ZjXmCqjxOaDeptMember();
			oaDeptMember.setModifyUserInfo(userKey, realName);
			zjXmCqjxOaDeptMemberMapper.batchDeleteUpdateZjXmCqjxOaDeptMember(memberList, oaDeptMember);
		}
		if (dbzjXmCqjxAssessmentManage != null && StrUtil.isNotEmpty(dbzjXmCqjxAssessmentManage.getManagerId())) {
			if (zjXmCqjxAssessmentManage.getAssessmentDeptList() != null
					&& zjXmCqjxAssessmentManage.getAssessmentDeptList().getOaDepartmentList().size() > 0) {
				OADepartmentMember departmentHead = zjXmCqjxAssessmentManage.getAssessmentDeptList();
				for (OADepartment oadept : departmentHead.getOaDepartmentList()) {
					ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
					zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
					zjXmCqjxOaDeptMember.setOaUserId(oadept.getId());
					zjXmCqjxOaDeptMember.setOaUserName(oadept.getName());
					zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("0");
					zjXmCqjxOaDeptMember.setOaOrgId(oadept.getOrgId());
					zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
					zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
					zjXmCqjxOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
				}
			}
			if (zjXmCqjxAssessmentManage.getAssessmentMemberList() != null
					&& zjXmCqjxAssessmentManage.getAssessmentMemberList().getOaMemberList().size() > 0) {
				OADepartmentMember member = zjXmCqjxAssessmentManage.getAssessmentMemberList();
				for (OAMember oaMember : member.getOaMemberList()) {
					ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
					zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
					zjXmCqjxOaDeptMember.setOaUserId(oaMember.getUserid());
					SysUser user = userService.getSysUserByUserKey(zjXmCqjxOaDeptMember.getOaUserId());
					SysDepartment department = sysDepartmentService.getSysDepartmentByPrimaryKey(oaMember.getOrgId());
					zjXmCqjxOaDeptMember.setOaOrgName(department.getDepartmentName());
					zjXmCqjxOaDeptMember.setMobile(user.getMobile());
					zjXmCqjxOaDeptMember.setOaUserName(oaMember.getName());
					zjXmCqjxOaDeptMember.setOaOrgId(oaMember.getOrgId());
					zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
					zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
					zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
					zjXmCqjxOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
				}
			}
			// 考核年度
			dbzjXmCqjxAssessmentManage.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
//           // 考核单位
//           dbzjXmCqjxAssessmentManage.setAssessmentDept(zjXmCqjxAssessmentManage.getAssessmentDept());
//           // 考核单位名称
//           dbzjXmCqjxAssessmentManage.setAssessmentDeptName(zjXmCqjxAssessmentManage.getAssessmentDeptName());
//           // 考核单位org
//           dbzjXmCqjxAssessmentManage.setAssessmentDeptOrgId(zjXmCqjxAssessmentManage.getAssessmentDeptOrgId());
			// 考核标题
			dbzjXmCqjxAssessmentManage.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());

			dbzjXmCqjxAssessmentManage.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
			// 考核季度
			dbzjXmCqjxAssessmentManage.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
			// 考核备注
			dbzjXmCqjxAssessmentManage.setAssessmentRemarks(zjXmCqjxAssessmentManage.getAssessmentRemarks());
			dbzjXmCqjxAssessmentManage.setDutyClosingEndDate(zjXmCqjxAssessmentManage.getDutyClosingEndDate());
			dbzjXmCqjxAssessmentManage
					.setFinalDutyClosingEndDate(zjXmCqjxAssessmentManage.getFinalDutyClosingEndDate());
			dbzjXmCqjxAssessmentManage
					.setFinalScoreClosingEndDate(zjXmCqjxAssessmentManage.getFinalScoreClosingEndDate());
			dbzjXmCqjxAssessmentManage
					.setFirstDutyClosingEndDate(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate());
			dbzjXmCqjxAssessmentManage
					.setFirstScoreClosingEndDate(zjXmCqjxAssessmentManage.getFirstScoreClosingEndDate());
			dbzjXmCqjxAssessmentManage.setScoreClosingEndDate(zjXmCqjxAssessmentManage.getScoreClosingEndDate());
			// 共通
			dbzjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxAssessmentManageMapper.updateByPrimaryKey(dbzjXmCqjxAssessmentManage);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmCqjxAssessmentManage);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmCqjxAssessmentManage(
			List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmCqjxAssessmentManageList != null && zjXmCqjxAssessmentManageList.size() > 0) {
			for (ZjXmCqjxAssessmentManage manage : zjXmCqjxAssessmentManageList) {
				if (StrUtil.equals(manage.getState(), "1") || StrUtil.equals(manage.getState(), "2")) {
					return repEntity.layerMessage("NO", "选中的数据中有已发送的数据，请重新选择！");
				}
			}
			ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = new ZjXmCqjxAssessmentManage();
			zjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
			flag = zjXmCqjxAssessmentManageMapper
					.batchDeleteUpdateZjXmCqjxAssessmentManage(zjXmCqjxAssessmentManageList, zjXmCqjxAssessmentManage);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmCqjxAssessmentManageList);
		}
	}

	@Override
	public ResponseEntity batchZjXmCqjxAssessmentManageSendMessage(
			List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendId = "";
		ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
		ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
		for (ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage : zjXmCqjxAssessmentManageList) {
			// 如果状态为已发起，则不需要新增其他考核数据
			if (StrUtil.equals(zjXmCqjxAssessmentManage.getState(), "2")) {
				zjXmCqjxDisciplineAssessment.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
				List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxDisciplineAssessmentMapper
						.selectByZjXmCqjxDisciplineAssessmentList(zjXmCqjxDisciplineAssessment);
				if (zjXmCqjxDisciplineAssessmentList.size() > 0) {
					zjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentList.get(0);
				} else {
//					zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
//		    		List<SysDepartment> deptList = sysDepartmentService.getSysDepartmentByUserkey(zjXmCqjxAssessmentManage.getCreateUser());
					ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//		    		head.setDepartmentId(deptList.get(0).getDepartmentId());
					head.setDepartmentId("77dbf87dM15ca0b9f2a2M456750cab3e8d472a55ba6658b2eac34");
					List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
							.selectByZjXmCqjxDepartmentHeadList(head);
					if (headList.size() > 0) {
						zjXmCqjxDisciplineAssessment.setDeptHeadId(headList.get(0).getUserKey());
						zjXmCqjxDisciplineAssessment.setExecutiveLeaderId(headList.get(0).getExecutiveLeaderId());
						zjXmCqjxDisciplineAssessment.setDeptHeadOption("");
						zjXmCqjxDisciplineAssessment.setExecutiveLeaderOption("");
					}
					// 插入纪律性考核表
					zjXmCqjxDisciplineAssessment.setDisciplineId(UuidUtil.generate());
					zjXmCqjxDisciplineAssessment.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
					zjXmCqjxDisciplineAssessment.setDisciplineTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
					zjXmCqjxDisciplineAssessment.setDisciplineYears(zjXmCqjxAssessmentManage.getAssessmentYears());
					zjXmCqjxDisciplineAssessment.setDisciplineQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
					zjXmCqjxDisciplineAssessment.setDisciplineRemarks(zjXmCqjxAssessmentManage.getAssessmentRemarks());
					zjXmCqjxDisciplineAssessment.setDisciplineState("0");
					zjXmCqjxDisciplineAssessment.setCreateUserInfo(userKey, realName);
					zjXmCqjxDisciplineAssessmentMapper.insert(zjXmCqjxDisciplineAssessment);
				}
				// 人员部门二选一，当前部门
				ZjXmCqjxOaDeptMember zjXmCqjxOaDept = new ZjXmCqjxOaDeptMember();
				zjXmCqjxOaDept.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDept.setOaDepartmentMemberFlag("0");
//				ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
				List<ZjXmCqjxOaDeptMember> zcMemberList = zjXmCqjxOaDeptMemberMapper
						.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDept);
				for (ZjXmCqjxOaDeptMember member : zcMemberList) {
					SysUser sysUser = new SysUser();
					sysUser.setDepartmentId(member.getOaUserId());
					List<SysUser> userList = sysDepartmentService.selectBySysUserListByDepartment(sysUser);
					for (SysUser user : userList) {
						zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
						zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
						zjXmCqjxDisciplineAssessmentMember.setOaUserId(user.getUserKey());
						if (zjXmCqjxDisciplineAssessmentMemberMapper
								.selectByZjXmCqjxDisciplineAssessmentMemberList(zjXmCqjxDisciplineAssessmentMember)
								.size() == 0) {
							// 插入纪律性考核人员
							zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
							zjXmCqjxDisciplineAssessmentMember.setZcOaId(UuidUtil.generate());
							zjXmCqjxDisciplineAssessmentMember
									.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
							zjXmCqjxDisciplineAssessmentMember.setOaUserId(user.getUserKey());
							zjXmCqjxDisciplineAssessmentMember.setOaUserName(user.getRealName());
							zjXmCqjxDisciplineAssessmentMember.setOaOrgId(member.getOaUserId());
							zjXmCqjxDisciplineAssessmentMember.setOaOrgName(member.getOaUserName());
							zjXmCqjxDisciplineAssessmentMember.setMobile(user.getMobile());
							zjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("0");
							zjXmCqjxDisciplineAssessmentMember.setScore("0.0");
							zjXmCqjxDisciplineAssessmentMember.setCreateUserInfo(userKey, realName);
							zjXmCqjxDisciplineAssessmentMemberMapper.insert(zjXmCqjxDisciplineAssessmentMember);
							sendId = sendId + user.getUserId() + "|";
						}
					}
				}
				ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
				List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper
						.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
				for (ZjXmCqjxOaDeptMember member : memberList) {
					zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
					zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
					zjXmCqjxDisciplineAssessmentMember.setOaUserId(member.getOaUserId());
					if (zjXmCqjxDisciplineAssessmentMemberMapper
							.selectByZjXmCqjxDisciplineAssessmentMemberList(zjXmCqjxDisciplineAssessmentMember)
							.size() == 0) {
						// 插入纪律性考核人员
						zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
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
						zjXmCqjxDisciplineAssessmentMemberMapper.insert(zjXmCqjxDisciplineAssessmentMember);
						sendId = sendId + user.getUserId() + "|";
					}
				}
				String content = "请于"
						+ DateUtil.format(zjXmCqjxAssessmentManage.getScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
						+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程填报";
//				sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
			} else {
				// 人员部门二选一，当前部门
				ZjXmCqjxOaDeptMember zjXmCqjxOaDept = new ZjXmCqjxOaDeptMember();
				zjXmCqjxOaDept.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDept.setOaDepartmentMemberFlag("0");
				ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
				List<ZjXmCqjxOaDeptMember> zcMemberList = zjXmCqjxOaDeptMemberMapper
						.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDept);
				for (ZjXmCqjxOaDeptMember member : zcMemberList) {
					SysUser sysUser = new SysUser();
					sysUser.setDepartmentId(member.getOaUserId());
					List<SysUser> userList = sysDepartmentService.selectBySysUserListByDepartment(sysUser);
					for (SysUser user : userList) {
						zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
						zjXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
						zjXmCqjxExecutiveAssistant.setCreateUser(user.getUserKey());
						if (zjXmCqjxExecutiveAssistantMapper
								.selectListByZjXmCqjxExecutiveAssistant(zjXmCqjxExecutiveAssistant).size() == 0) {
							zjXmCqjxExecutiveAssistant.setExecutiveId(UuidUtil.generate());
							zjXmCqjxExecutiveAssistant
									.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
							zjXmCqjxExecutiveAssistant
									.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
							zjXmCqjxExecutiveAssistant
									.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
							zjXmCqjxExecutiveAssistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
							zjXmCqjxExecutiveAssistant.setAssessmentState("0");
							zjXmCqjxExecutiveAssistant.setAssistantLock("0");
							zjXmCqjxExecutiveAssistant.setDepartmentId(member.getOaUserId());
							zjXmCqjxExecutiveAssistant.setCreateUserInfo(user.getUserKey(), user.getRealName());
							zjXmCqjxExecutiveAssistantMapper.insert(zjXmCqjxExecutiveAssistant);
							sendId = sendId + user.getUserId() + "|";
						}
					}
				}
				ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
				zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
				List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper
						.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
				for (ZjXmCqjxOaDeptMember member : memberList) {
					zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
					zjXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
					zjXmCqjxExecutiveAssistant.setCreateUser(member.getOaUserId());
					if (zjXmCqjxExecutiveAssistantMapper
							.selectListByZjXmCqjxExecutiveAssistant(zjXmCqjxExecutiveAssistant).size() == 0) {
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
						zjXmCqjxExecutiveAssistantMapper.insert(zjXmCqjxExecutiveAssistant);
						SysUser user = userService.getSysUserByUserKey(member.getOaUserId());
						sendId = sendId + user.getUserId() + "|";
					}
				}
				String content = "请于"
						+ DateUtil.format(zjXmCqjxAssessmentManage.getDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")
						+ " 前完成" + zjXmCqjxAssessmentManage.getAssessmentTitle() + "流程填报";
//				sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);
			}
			ZjXmCqjxAssessmentManage dbzjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper
					.selectByPrimaryKey(zjXmCqjxAssessmentManage.getManagerId());
			dbzjXmCqjxAssessmentManage.setState(zjXmCqjxAssessmentManage.getState());
			dbzjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
			zjXmCqjxAssessmentManageMapper.updateByPrimaryKey(dbzjXmCqjxAssessmentManage);

		}
		return repEntity.layerMessage("NO", "发送成功！");
	}

	@Override
	public ResponseEntity zjXmCqjxAssessmentManageDetailByQuarter(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		if (zjXmCqjxAssessmentManage == null) {
			zjXmCqjxAssessmentManage = new ZjXmCqjxAssessmentManage();
		}
		ZjXmCqjxExecutiveAssistant zXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
		zXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
		zXmCqjxExecutiveAssistant.setDepartmentName(zjXmCqjxAssessmentManage.getDeptName());
		zXmCqjxExecutiveAssistant.setCreateUserName(zjXmCqjxAssessmentManage.getCreateUserName());
		// 分页查询
		PageHelper.startPage(zjXmCqjxAssessmentManage.getPage(), zjXmCqjxAssessmentManage.getLimit());
		// 获取数据
		List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper
				.selectAssistantDetailByQuarter(zXmCqjxExecutiveAssistant);
        for(ZjXmCqjxExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
    		head.setDepartmentId(assistant.getDepartmentId());
    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
    		if(headList.size()>0) {
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    			detail.setOtherId(headList.get(0).getDepartmentHeadId());
    			detail.setOtherType("1");
    			List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);    
    			if(detailList.size() == 0) {
    				assistant.setHaveChangerLeader("1");
    			}else {
    				assistant.setHaveChangerLeader("0");    				
    			}
    		}
        }		
		// 得到分页信息
		PageInfo<ZjXmCqjxExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
		return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}

	@Override
	public ResponseEntity zjXmCqjxAssessmentManageSendMessage(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
		ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
//		for(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage : zjXmCqjxAssessmentManageList) {
		// 如果状态为已发起，则不需要新增其他考核数据
		if (StrUtil.equals(zjXmCqjxAssessmentManage.getState(), "2")) {
			// 插入纪律性考核表
			zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
			zjXmCqjxDisciplineAssessment.setDisciplineId(UuidUtil.generate());
			zjXmCqjxDisciplineAssessment.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
			zjXmCqjxDisciplineAssessment.setDisciplineTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
			zjXmCqjxDisciplineAssessment.setDisciplineYears(zjXmCqjxAssessmentManage.getAssessmentYears());
			zjXmCqjxDisciplineAssessment.setDisciplineQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
			zjXmCqjxDisciplineAssessment.setDisciplineRemarks(zjXmCqjxAssessmentManage.getAssessmentRemarks());
			zjXmCqjxDisciplineAssessment.setDisciplineState("0");
			zjXmCqjxDisciplineAssessment.setCreateUserInfo(userKey, realName);
			zjXmCqjxDisciplineAssessmentMapper.insert(zjXmCqjxDisciplineAssessment);
			// 插入协作性考核表
//		        zjXmCqjxCollaborationAssessment.setCollaborationId(UuidUtil.generate());
//		        zjXmCqjxCollaborationAssessment.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
//		        zjXmCqjxCollaborationAssessment.setCollaborationTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
//		        zjXmCqjxCollaborationAssessment.setCollaborationYears(zjXmCqjxAssessmentManage.getAssessmentYears());
//		        zjXmCqjxCollaborationAssessment.setCollaborationQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
//		        zjXmCqjxCollaborationAssessment.setCollaborationRemarks(zjXmCqjxAssessmentManage.getAssessmentRemarks());
//		        zjXmCqjxCollaborationAssessment.setCollaborationState("0");	        
//		        zjXmCqjxCollaborationAssessment.setCreateUserInfo(userKey, realName);
//		        zjXmCqjxCollaborationAssessmentMapper.insert(zjXmCqjxCollaborationAssessment);				
		}
		// 人员部门二选一，当前部门
		ZjXmCqjxOaDeptMember zjXmCqjxOaDept = new ZjXmCqjxOaDeptMember();
		zjXmCqjxOaDept.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
		zjXmCqjxOaDept.setOaDepartmentMemberFlag("0");
		ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
		List<ZjXmCqjxOaDeptMember> zcMemberList = zjXmCqjxOaDeptMemberMapper
				.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDept);
		for (ZjXmCqjxOaDeptMember member : zcMemberList) {
			SysUser sysUser = new SysUser();
			sysUser.setDepartmentId(member.getOaUserId());
			List<SysUser> userList = sysDepartmentService.selectBySysUserListByDepartment(sysUser);
			for (SysUser user : userList) {
				zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
				zjXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
				zjXmCqjxExecutiveAssistant.setCreateUser(user.getUserKey());
				if (zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant)
						.size() == 0) {
					zjXmCqjxExecutiveAssistant.setExecutiveId(UuidUtil.generate());
					zjXmCqjxExecutiveAssistant.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
					zjXmCqjxExecutiveAssistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
					zjXmCqjxExecutiveAssistant.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
					zjXmCqjxExecutiveAssistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
					zjXmCqjxExecutiveAssistant.setAssessmentState("0");
					zjXmCqjxExecutiveAssistant.setCreateUserInfo(user.getUserKey(), user.getRealName());
					zjXmCqjxExecutiveAssistantMapper.insert(zjXmCqjxExecutiveAssistant);
					if (StrUtil.equals(zjXmCqjxAssessmentManage.getState(), "2")) {
						// 插入纪律性考核人员
						zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
						zjXmCqjxDisciplineAssessmentMember.setZcOaId(UuidUtil.generate());
						zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
						zjXmCqjxDisciplineAssessmentMember.setOaUserId(user.getUserKey());
						zjXmCqjxDisciplineAssessmentMember.setOaUserName(user.getRealName());
						zjXmCqjxDisciplineAssessmentMember.setOaOrgId(member.getOaUserId());
						zjXmCqjxDisciplineAssessmentMember.setOaOrgName(member.getOaUserName());
						zjXmCqjxDisciplineAssessmentMember.setMobile(user.getMobile());
						zjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("0");
						zjXmCqjxDisciplineAssessmentMember.setCreateUserInfo(userKey, realName);
						zjXmCqjxDisciplineAssessmentMemberMapper.insert(zjXmCqjxDisciplineAssessmentMember);
					}
					// 插入协作性考核人员
//					        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
//					        zjXmCqjxCollaborationAssessmentMember.setManagerId(zjXmCqjxCollaborationAssessment.getManagerId());
//					        zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
//					        zjXmCqjxCollaborationAssessmentMember.setOaUserId(user.getUserKey());
//					        zjXmCqjxCollaborationAssessmentMember.setOaUserName(user.getRealName());
//					        zjXmCqjxCollaborationAssessmentMember.setOaOrgId(member.getOaUserId());
//					        zjXmCqjxCollaborationAssessmentMember.setOaOrgName(member.getOaUserName());
//					        zjXmCqjxCollaborationAssessmentMember.setMobile(user.getMobile());
//					        zjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("0");					        
//					        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
//					        zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);					        
				}
			}
		}
		ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
		zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
		zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
		List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper
				.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
		for (ZjXmCqjxOaDeptMember member : memberList) {
			zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
			zjXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
			zjXmCqjxExecutiveAssistant.setCreateUser(member.getOaUserId());
			if (zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant)
					.size() == 0) {
				zjXmCqjxExecutiveAssistant.setExecutiveId(UuidUtil.generate());
				zjXmCqjxExecutiveAssistant.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
				zjXmCqjxExecutiveAssistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
				zjXmCqjxExecutiveAssistant.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
				zjXmCqjxExecutiveAssistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
				zjXmCqjxExecutiveAssistant.setAssessmentState("0");
				zjXmCqjxExecutiveAssistant.setCreateUserInfo(member.getOaUserId(), member.getOaUserName());
				zjXmCqjxExecutiveAssistantMapper.insert(zjXmCqjxExecutiveAssistant);
				if (StrUtil.equals(zjXmCqjxAssessmentManage.getState(), "2")) {
					// 插入纪律性考核人员
					zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
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
					zjXmCqjxDisciplineAssessmentMemberMapper.insert(zjXmCqjxDisciplineAssessmentMember);
				}
				// 插入协作性考核人员(默认数据先不被看见，由管理员发起才可见)
//				        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
//				        zjXmCqjxCollaborationAssessmentMember.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
//				        zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
//				        zjXmCqjxCollaborationAssessmentMember.setOaUserId(user.getUserKey());
//				        zjXmCqjxCollaborationAssessmentMember.setOaUserName(user.getRealName());
//				        zjXmCqjxCollaborationAssessmentMember.setOaOrgId(member.getOaUserId());
//				        zjXmCqjxCollaborationAssessmentMember.setOaOrgName(member.getOaUserName());
//				        zjXmCqjxCollaborationAssessmentMember.setMobile(user.getMobile());
//				        zjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("0");					        
//				        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
//				        zjXmCqjxCollaborationAssessmentMember.setDelFlag("1");
//				        zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);				        
			}
		}
		ZjXmCqjxAssessmentManage dbzjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper
				.selectByPrimaryKey(zjXmCqjxAssessmentManage.getManagerId());
		dbzjXmCqjxAssessmentManage.setState(zjXmCqjxAssessmentManage.getState());
//		        dbzjXmCqjxAssessmentManage.setDutyClosingDate(dbzjXmCqjxAssessmentManage.getDutyClosingDate());
//		        dbzjXmCqjxAssessmentManage.setScoreClosingDate(dbzjXmCqjxAssessmentManage.getScoreClosingDate());
		dbzjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
		zjXmCqjxAssessmentManageMapper.updateByPrimaryKey(dbzjXmCqjxAssessmentManage);
//		}
		return repEntity.layerMessage("NO", "发送成功！");
	}

	@Override
	public ResponseEntity getZjXmCqjxAssessmentManageListByDeptHeader(
			ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		// 判断当前人是否是部门负责人
//        ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//        head.setUserKey(userKey);
//        List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
		ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
		detail.setOtherType("0");
		detail.setOaUserId(userKey);
		List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper
				.selectByZjXmCqjxDepartmentHeadDetailList(detail);
		if (detailList.size() == 0) {
			List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxAssessmentManage>();
			return repEntity.okList(zjXmCqjxAssessmentManageList, 0);
		} else {
			zjXmCqjxAssessmentManage.setDeptId(detailList.get(0).getOaOrgId());
		}
		// 分页查询
		PageHelper.startPage(zjXmCqjxAssessmentManage.getPage(), zjXmCqjxAssessmentManage.getLimit());
		// 获取数据
		List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList = zjXmCqjxAssessmentManageMapper
				.getZjXmCqjxAssessmentManageListByDeptHeader(zjXmCqjxAssessmentManage);
		// 得到分页信息
		PageInfo<ZjXmCqjxAssessmentManage> p = new PageInfo<>(zjXmCqjxAssessmentManageList);

		return repEntity.okList(zjXmCqjxAssessmentManageList, p.getTotal());
	}

}
