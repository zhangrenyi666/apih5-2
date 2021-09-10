package com.apih5.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
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
import com.apih5.mybatis.dao.ZjXmCqjxYearAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxYearScoreApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxYearScoreApproval;
import com.apih5.service.ZjXmCqjxYearAssistantService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxYearAssistantService")
public class ZjXmCqjxYearAssistantServiceImpl implements ZjXmCqjxYearAssistantService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxYearAssistantMapper zjXmCqjxYearAssistantMapper;
    
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
    
    @Autowired(required = true)
    private ZjXmCqjxYearScoreApprovalMapper zjXmCqjxYearScoreApprovalMapper;
    
	@Override
	public ResponseEntity getZjXmCqjxYearAssistantTodoList(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxYearAssistant.setCreateUser(userKey);
//        zjXmCqjxProjectOverallEvaluationAssistant.setLeaderSee("1");
        // 分页查询
        PageHelper.startPage(zjXmCqjxYearAssistant.getPage(),zjXmCqjxYearAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxYearAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxYearAssistantMapper.selectEvaluationLeaderTodoListByUserKey(zjXmCqjxYearAssistant);
        for(ZjXmCqjxYearAssistant assistant : zjXmCqjxExecutiveAssistantList) {
        	if(StrUtil.equals(assistant.getAssessmentType(), "1")) {
        		switch (assistant.getItemType()) {
				case "0":
					assistant.setItemText("周边业绩考核");	
					assistant.setItemScore("8");
					break;
				case "1":
					assistant.setItemText("对公司各单位服务和指导职能的发挥");
					assistant.setItemScore("8");
					break;
				case "2":
					assistant.setItemText("工作成绩");
					assistant.setItemScore("12");					
					break;
				case "3":
					assistant.setItemText("公司主要领导总体评价");
					assistant.setItemScore("12");					
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "2")) {
        		switch (assistant.getItemType()) {
				case "0":
					assistant.setItemText("周边业绩考核");	
					assistant.setItemScore("5");						
					break;
				case "1":
					assistant.setItemText("对公司各单位服务和指导职能的发挥");	
					assistant.setItemScore("10");						
					break;
				case "2":
					assistant.setItemText("工作成绩");
					assistant.setItemScore("10");						
					break;
				case "3":
					assistant.setItemText("公司主要领导总体评价");
					assistant.setItemScore("10");
					break;
				case "4":
					assistant.setItemText("督办工作");
					assistant.setItemScore("5");						
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "3")) {
           		switch (assistant.getItemType()) {
    				case "0":
    					assistant.setItemText("工作任务完成情况");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "1":
    					assistant.setItemText("工作积极性");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "2":
    					assistant.setItemText("部门负责人评价");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "3":
    					assistant.setItemText("公司分管领导评价");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "4":
    					assistant.setItemText("公司主管领导评价");	
    					assistant.setItemScore("8");    					
    					break;
    				}        		
        	}
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxYearAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());   
	}
	
    @Override
    public ResponseEntity getZjXmCqjxYearAssistantListByCondition(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        if (zjXmCqjxYearAssistant == null) {
            zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxYearAssistant.getPage(),zjXmCqjxYearAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(zjXmCqjxYearAssistant);
        // 得到分页信息
        PageInfo<ZjXmCqjxYearAssistant> p = new PageInfo<>(zjXmCqjxYearAssistantList);

        return repEntity.okList(zjXmCqjxYearAssistantList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxYearAssistantDetails(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        if (zjXmCqjxYearAssistant == null) {
            zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
        }
        // 获取数据
        ZjXmCqjxYearAssistant dbZjXmCqjxYearAssistant = zjXmCqjxYearAssistantMapper.selectByPrimaryKey(zjXmCqjxYearAssistant.getYearAssistantId());
        // 数据存在
        if (dbZjXmCqjxYearAssistant != null) {
            return repEntity.ok(dbZjXmCqjxYearAssistant);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxYearAssistant(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxYearAssistant.setYearAssistantId(UuidUtil.generate());
        zjXmCqjxYearAssistant.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxYearAssistantMapper.insert(zjXmCqjxYearAssistant);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxYearAssistant);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxYearAssistant(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxYearAssistant dbzjXmCqjxYearAssistant = zjXmCqjxYearAssistantMapper.selectByPrimaryKey(zjXmCqjxYearAssistant.getYearAssistantId());
        if (dbzjXmCqjxYearAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxYearAssistant.getYearAssistantId())) {
           // 考核类别
           dbzjXmCqjxYearAssistant.setAssessmentType(zjXmCqjxYearAssistant.getAssessmentType());
           // 考核计划ID
           dbzjXmCqjxYearAssistant.setManagerId(zjXmCqjxYearAssistant.getManagerId());
           // 考核年度
           dbzjXmCqjxYearAssistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
           // 考核标题
           dbzjXmCqjxYearAssistant.setAssessmentTitle(zjXmCqjxYearAssistant.getAssessmentTitle());
           // 考核季度
           dbzjXmCqjxYearAssistant.setAssessmentQuarter(zjXmCqjxYearAssistant.getAssessmentQuarter());
           // 考核状态
           dbzjXmCqjxYearAssistant.setAssessmentState(zjXmCqjxYearAssistant.getAssessmentState());
           // 岗位
           dbzjXmCqjxYearAssistant.setPosition(zjXmCqjxYearAssistant.getPosition());
           // 人员所在部门
           dbzjXmCqjxYearAssistant.setDepartmentId(zjXmCqjxYearAssistant.getDepartmentId());
           // 锁定
           dbzjXmCqjxYearAssistant.setAssistantLock(zjXmCqjxYearAssistant.getAssistantLock());
           // 领导是否可见
           dbzjXmCqjxYearAssistant.setLeaderSee(zjXmCqjxYearAssistant.getLeaderSee());
           // 季度平均得分
           dbzjXmCqjxYearAssistant.setQuarterAverageScore(zjXmCqjxYearAssistant.getQuarterAverageScore());
           // 考核综合得分
           dbzjXmCqjxYearAssistant.setComprehensiveScore(zjXmCqjxYearAssistant.getComprehensiveScore());
           // 年度最终得分
           dbzjXmCqjxYearAssistant.setYearFinalScore(zjXmCqjxYearAssistant.getYearFinalScore());
           // 选项一
           dbzjXmCqjxYearAssistant.setItemOne(zjXmCqjxYearAssistant.getItemOne());
           // 选项二
           dbzjXmCqjxYearAssistant.setItemTwo(zjXmCqjxYearAssistant.getItemTwo());
           // 选项三
           dbzjXmCqjxYearAssistant.setItemThree(zjXmCqjxYearAssistant.getItemThree());
           // 选项四
           dbzjXmCqjxYearAssistant.setItemFour(zjXmCqjxYearAssistant.getItemFour());
           // 选项五
           dbzjXmCqjxYearAssistant.setItemFive(zjXmCqjxYearAssistant.getItemFive());
           // 共通
           dbzjXmCqjxYearAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbzjXmCqjxYearAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxYearAssistant);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxYearAssistant(List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxYearAssistantList != null && zjXmCqjxYearAssistantList.size() > 0) {
           ZjXmCqjxYearAssistant zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
           zjXmCqjxYearAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxYearAssistantMapper.batchDeleteUpdateZjXmCqjxYearAssistant(zjXmCqjxYearAssistantList, zjXmCqjxYearAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxYearAssistantList);
        }
    }

	@Override
	@Transactional(rollbackFor = Exception.class)		
	public ResponseEntity saveZjXmCqjxYearAssistantByManagerId(ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String sendId = "";
			// 流程发起，将所有的员工平均分计算，发起
		ZjXmCqjxYearAssistant zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
		ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
		List<ZjXmCqjxExecutiveAssistant> assistantList = new ArrayList<ZjXmCqjxExecutiveAssistant>();
		zjXmCqjxOaDeptMember.setOtherId(zjXmCqjxAssessmentManage.getManagerId());
		zjXmCqjxOaDeptMember.setOaDepartmentMemberFlag("1");
		List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper
				.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
		for (ZjXmCqjxOaDeptMember member : memberList) {
			zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
			zjXmCqjxYearAssistant.setManagerId(zjXmCqjxAssessmentManage.getManagerId());
			zjXmCqjxYearAssistant.setCreateUser(member.getOaUserId());
			if (zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(zjXmCqjxYearAssistant).size() == 0) {
				//查询四个季度的得分，计算平均分
				assistant = new ZjXmCqjxExecutiveAssistant();
				assistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
				assistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
				assistant.setCreateUser(member.getOaUserId());
				assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
				if(assistantList.size()>0) {
					//计算季度平均分
			        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
			        zjXmCqjxYearAssistant.setQuarterAverageScore(avg);
					zjXmCqjxYearAssistant.setYearAssistantId(UuidUtil.generate());
					zjXmCqjxYearAssistant.setAssessmentTitle(zjXmCqjxAssessmentManage.getAssessmentTitle());
					zjXmCqjxYearAssistant.setAssessmentYears(zjXmCqjxAssessmentManage.getAssessmentYears());
					zjXmCqjxYearAssistant.setAssessmentQuarter(zjXmCqjxAssessmentManage.getAssessmentQuarter());
					zjXmCqjxYearAssistant.setAssessmentType(zjXmCqjxAssessmentManage.getAssessmentType());
					zjXmCqjxYearAssistant.setAssessmentState("0");
					zjXmCqjxYearAssistant.setAssistantLock("0");
					zjXmCqjxYearAssistant.setDepartmentId(member.getOaOrgId());					
					zjXmCqjxYearAssistant.setCreateUserInfo(member.getOaUserId(), member.getOaUserName());
					zjXmCqjxYearAssistantMapper.insert(zjXmCqjxYearAssistant);		        
			        //获取年度考核评价人
					if(StrUtil.equals(zjXmCqjxAssessmentManage.getAssessmentType(), "1")) {
						//查询副总师、经理助理评分节点-----周边业绩考核
			    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
			    		head.setDepartmentId(assistantList.get(0).getDepartmentId());
			    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
						ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("3");
						List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
						ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval = new ZjXmCqjxYearScoreApproval();
						for(ZjXmCqjxDepartmentHeadDetail headDetail : detailList) {
							if(!StrUtil.equals(headDetail.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {								
								zjXmCqjxYearScoreApproval.setOtherType("3");
								zjXmCqjxYearScoreApproval.setItemType("0");
								zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
								zjXmCqjxYearScoreApproval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
								zjXmCqjxYearScoreApproval.setLeaderId(headDetail.getOaUserId());
								zjXmCqjxYearScoreApproval.setLeaderName(headDetail.getOaUserName());
								zjXmCqjxYearScoreApproval.setLeaderOrgId(headDetail.getOaOrgId());
								zjXmCqjxYearScoreApproval.setApprovalFlag("1");
								zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
								SysUser user = userService.getSysUserByUserKey(member.getOaUserId());						
								sendId = sendId + user.getUserId() + "|";	
							}
						}
						//查询副总师、经理助理评分节点-----对各单位服务.....
						detail.setOtherType("4");
						detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
						for(ZjXmCqjxDepartmentHeadDetail headDetail : detailList) {
							zjXmCqjxYearScoreApproval.setOtherType("4");
							zjXmCqjxYearScoreApproval.setItemType("1");							
							zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
							zjXmCqjxYearScoreApproval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							zjXmCqjxYearScoreApproval.setLeaderId(headDetail.getOaUserId());
							zjXmCqjxYearScoreApproval.setLeaderName(headDetail.getOaUserName());
							zjXmCqjxYearScoreApproval.setLeaderOrgId(headDetail.getOaOrgId());
							zjXmCqjxYearScoreApproval.setApprovalFlag("1");
					        zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
					        zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
							SysUser user = userService.getSysUserByUserKey(member.getOaUserId());						
							sendId = sendId + user.getUserId() + "|";	
						}
					}else if(StrUtil.equals(zjXmCqjxAssessmentManage.getAssessmentType(), "2")) {
					//查询部门正、副职评分节点-----查询所有部门的部门负责人
			    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
			    		head.setDepartmentId(assistantList.get(0).getDepartmentId());
			    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
						ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
//						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("0");
						List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
						ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval = new ZjXmCqjxYearScoreApproval();
						for(ZjXmCqjxDepartmentHeadDetail headDetail : detailList) {
							if(!StrUtil.equals(headDetail.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
								zjXmCqjxYearScoreApproval.setOtherType("0");
								zjXmCqjxYearScoreApproval.setItemType("0");
								zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
								zjXmCqjxYearScoreApproval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
								zjXmCqjxYearScoreApproval.setLeaderId(headDetail.getOaUserId());
								zjXmCqjxYearScoreApproval.setLeaderName(headDetail.getOaUserName());
								zjXmCqjxYearScoreApproval.setLeaderOrgId(headDetail.getOaOrgId());
								zjXmCqjxYearScoreApproval.setApprovalFlag("1");
								zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
								SysUser user = userService.getSysUserByUserKey(member.getOaUserId());						
								sendId = sendId + user.getUserId() + "|";	
							}
						}
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("4");
						detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
						for(ZjXmCqjxDepartmentHeadDetail headDetail : detailList) {
							zjXmCqjxYearScoreApproval.setOtherType("4");
							zjXmCqjxYearScoreApproval.setItemType("1");
							zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
							zjXmCqjxYearScoreApproval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							zjXmCqjxYearScoreApproval.setLeaderId(headDetail.getOaUserId());
							zjXmCqjxYearScoreApproval.setLeaderName(headDetail.getOaUserName());
							zjXmCqjxYearScoreApproval.setLeaderOrgId(headDetail.getOaOrgId());
							zjXmCqjxYearScoreApproval.setApprovalFlag("1");
					        zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
					        zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
							SysUser user = userService.getSysUserByUserKey(member.getOaUserId());						
							sendId = sendId + user.getUserId() + "|";	
						}
					}else if(StrUtil.equals(zjXmCqjxAssessmentManage.getAssessmentType(), "3")) {
				    //查询员工评分节点			
			    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
			    		head.setDepartmentId(assistantList.get(0).getDepartmentId());
			    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
						ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("0");
						List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
						ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval = new ZjXmCqjxYearScoreApproval();
						for(ZjXmCqjxDepartmentHeadDetail headDetail : detailList) {
								zjXmCqjxYearScoreApproval.setOtherType("0");
								zjXmCqjxYearScoreApproval.setItemType("0");
								zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
								zjXmCqjxYearScoreApproval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
								zjXmCqjxYearScoreApproval.setLeaderId(headDetail.getOaUserId());
								zjXmCqjxYearScoreApproval.setLeaderName(headDetail.getOaUserName());
								zjXmCqjxYearScoreApproval.setLeaderOrgId(headDetail.getOaOrgId());
								zjXmCqjxYearScoreApproval.setApprovalFlag("1");
								zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
								SysUser user = userService.getSysUserByUserKey(member.getOaUserId());						
								sendId = sendId + user.getUserId() + "|";	
								zjXmCqjxYearScoreApproval.setItemType("1");
								zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
								zjXmCqjxYearScoreApproval.setApprovalFlag("1");
								zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
								user = userService.getSysUserByUserKey(member.getOaUserId());						
								sendId = sendId + user.getUserId() + "|";	
								zjXmCqjxYearScoreApproval.setItemType("2");
								zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
								zjXmCqjxYearScoreApproval.setApprovalFlag("1");
								zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
								zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
								user = userService.getSysUserByUserKey(member.getOaUserId());						
								sendId = sendId + user.getUserId() + "|";									
						}						
					}				        
				}
			}
		}		
				String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"评分";
//				sendId = "haiwei_xichengjian_test";
				weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);			
			ZjXmCqjxAssessmentManage dbzjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper
					.selectByPrimaryKey(zjXmCqjxAssessmentManage.getManagerId());
			dbzjXmCqjxAssessmentManage.setState("2");
			dbzjXmCqjxAssessmentManage.setModifyUserInfo(userKey, realName);
			zjXmCqjxAssessmentManageMapper.updateByPrimaryKey(dbzjXmCqjxAssessmentManage);
			
		return repEntity.layerMessage("NO", "发送成功！");
	}

	@Override
	public ResponseEntity zjXmCqjxSurroundAssistantApproval(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String itemType = ""; 
	        String type = "";
	        String sendId = "";
	        BigDecimal oneScore = null;
	        BigDecimal twoScore = null;
	        BigDecimal threeScore = null;
	        BigDecimal fourScore = null;
	        BigDecimal fiveScore = null;
	        String realName = TokenUtils.getRealName(request);		
	        ZjXmCqjxYearScoreApproval approval = zjXmCqjxYearScoreApprovalMapper.selectByPrimaryKey(zjXmCqjxYearAssistant.getYearScoreApprovalId());
	        approval.setApprovalFlag("2");
	        approval.setModifyUserInfo(userKey, realName);
	        zjXmCqjxYearScoreApprovalMapper.updateByPrimaryKeySelective(approval);
	        itemType = approval.getItemType();
			type = approval.getOtherType();
	        approval = new ZjXmCqjxYearScoreApproval();
			approval.setApprovalFlag("0");
			approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
			approval.setOtherType(type);
			List<ZjXmCqjxYearScoreApproval> approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
			approval.setApprovalFlag("1");
			approval.setItemType(itemType);
			if(approvalList.size()>0) {
				approval = approvalList.get(0);
				approval.setApprovalFlag("1");
				zjXmCqjxYearScoreApprovalMapper.updateByPrimaryKeySelective(approval);	
			}else if(zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval).size()>0){
				return repEntity.ok(zjXmCqjxYearAssistant);
			}else {
				ZjXmCqjxYearAssistant dbZjXmCqjxYearAssistant = zjXmCqjxYearAssistantMapper.selectByPrimaryKey(zjXmCqjxYearAssistant.getYearAssistantId());
				//如果当前节点完成，则计算评分
				approval = new ZjXmCqjxYearScoreApproval();
				approval.setItemType(itemType);
				approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
				approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
		        double sum = approvalList.stream().mapToDouble(ZjXmCqjxYearScoreApproval::getLeaderScore).average().getAsDouble();
		        DecimalFormat df = new DecimalFormat("0.0");
		        switch (itemType) {
				case "0":
					dbZjXmCqjxYearAssistant.setItemOne(df.format(sum));				
					break;
				case "1":
					dbZjXmCqjxYearAssistant.setItemTwo(df.format(sum));					
					break;
				case "2":
					dbZjXmCqjxYearAssistant.setItemThree(df.format(sum));					
					break;
				case "3":
					dbZjXmCqjxYearAssistant.setItemFour(df.format(sum));					
					break;
				case "4":
					dbZjXmCqjxYearAssistant.setItemFive(df.format(sum));					
					break;
				}
				ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
				SysUser user = new SysUser();
		        if(StrUtil.equals(dbZjXmCqjxYearAssistant.getAssessmentType(), "1")) {
					if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "1")) {
					    //查询周边业绩考核和所属项目正职考核是否都完成，都完成则发送给领导班子副职
						if(StrUtil.equals(itemType, "1")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("0");
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);
							}
						}
						if(StrUtil.equals(itemType, "0")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("1");
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);	
							}
						}
							head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
									.selectByZjXmCqjxDepartmentHeadList(head);	
							if(headList.size()>0) {
								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
								detail.setOtherId(headList.get(0).getDepartmentHeadId());
								detail.setOtherType("1");
								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
								if (detailList.size() > 0) {
								for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
									if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
										approval = new ZjXmCqjxYearScoreApproval();
										approval.setYearScoreApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("1");
										approval.setItemType("2");
										approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
										approval.setLeaderId(ass.getOaUserId());
										approval.setLeaderName(ass.getOaUserName());
										approval.setLeaderOrgId(ass.getOaOrgId());
										approval.setApprovalFlag("1");
										user = userService.getSysUserByUserKey(ass.getCreateUser());
											sendId = sendId + user.getUserId() + "|";
											zjXmCqjxYearScoreApprovalMapper.insert(approval);	
									}						
								}
								}							
							}
							dbZjXmCqjxYearAssistant.setAssessmentState("1");
					}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "2")) {
					//发送给领导班子正职
						head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
									.selectByZjXmCqjxDepartmentHeadList(head);	
							if(headList.size()>0) {
								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
								detail.setOtherId(headList.get(0).getDepartmentHeadId());
								detail.setOtherType("2");
								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
								if (detailList.size() > 0) {
								for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
									if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
										approval = new ZjXmCqjxYearScoreApproval();
										approval.setYearScoreApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("2");
										approval.setItemType("3");
										approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
										approval.setLeaderId(ass.getOaUserId());
										approval.setLeaderName(ass.getOaUserName());
										approval.setLeaderOrgId(ass.getOaOrgId());
										approval.setApprovalFlag("1");
										user = userService.getSysUserByUserKey(ass.getCreateUser());
											sendId = sendId + user.getUserId() + "|";
											zjXmCqjxYearScoreApprovalMapper.insert(approval);	
									}						
								}
								}							
							}
							dbZjXmCqjxYearAssistant.setAssessmentState("2");						
					}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "3")) {
					//领导班子正职评分完成，结束;计算得分，并将得分放入考核表里
						oneScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemOne());
						twoScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemTwo());
						threeScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemThree());
						fourScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemFour());
						BigDecimal comprehensiveScore = oneScore.add(twoScore).add(threeScore).add(fourScore);
						BigDecimal quarterAverageScore = new BigDecimal(dbZjXmCqjxYearAssistant.getQuarterAverageScore());
						dbZjXmCqjxYearAssistant.setComprehensiveScore(comprehensiveScore.doubleValue());
//						dbZjXmCqjxYearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
						dbZjXmCqjxYearAssistant.setAssessmentState("3");	
					}
		        }else if(StrUtil.equals(dbZjXmCqjxYearAssistant.getAssessmentType(), "2")) {
					if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "1")) {
					    //查询周边业绩考核和所属项目正职考核是否都完成，都完成则发送给领导班子副职
						if(StrUtil.equals(itemType, "1")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("0");
//							approval.setOtherType(type);
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);
							}
						}else if(StrUtil.equals(itemType, "0")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("1");
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);	
							}
						}
						head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
									.selectByZjXmCqjxDepartmentHeadList(head);	
							if(headList.size()>0) {
								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
//								detail.setOtherId(headList.get(0).getDepartmentHeadId());
								detail.setOtherType("1");
								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
								if (detailList.size() > 0) {
								for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
									if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
										approval = new ZjXmCqjxYearScoreApproval();
										approval.setYearScoreApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("1");
										approval.setItemType("2");
										approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
										approval.setLeaderId(ass.getOaUserId());
										approval.setLeaderName(ass.getOaUserName());
										approval.setLeaderOrgId(ass.getOaOrgId());
										approval.setApprovalFlag("1");
										user = userService.getSysUserByUserKey(ass.getCreateUser());
											sendId = sendId + user.getUserId() + "|";
											zjXmCqjxYearScoreApprovalMapper.insert(approval);	
									}						
								}
								}							
							}
							dbZjXmCqjxYearAssistant.setAssessmentState("1");
					}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "2")) {
					//发送给领导班子正职
						head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
									.selectByZjXmCqjxDepartmentHeadList(head);	
							if(headList.size()>0) {
								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
								detail.setOtherId(headList.get(0).getDepartmentHeadId());
								detail.setOtherType("2");
								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
								if (detailList.size() > 0) {
								for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
									if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
										approval = new ZjXmCqjxYearScoreApproval();
										approval.setYearScoreApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("2");
										approval.setItemType("3");
										approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
										approval.setLeaderId(ass.getOaUserId());
										approval.setLeaderName(ass.getOaUserName());
										approval.setLeaderOrgId(ass.getOaOrgId());
										approval.setApprovalFlag("1");
										user = userService.getSysUserByUserKey(ass.getCreateUser());
											sendId = sendId + user.getUserId() + "|";
											zjXmCqjxYearScoreApprovalMapper.insert(approval);	
									}						
								}
								}							
							}
							dbZjXmCqjxYearAssistant.setAssessmentState("2");						
					}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "3")) {
						//发送给领导班子正职
						head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
									.selectByZjXmCqjxDepartmentHeadList(head);	
							if(headList.size()>0) {
								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
								detail.setOtherId(headList.get(0).getDepartmentHeadId());
								detail.setOtherType("2");
								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
								if (detailList.size() > 0) {
								for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
									if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
										approval = new ZjXmCqjxYearScoreApproval();
										approval.setYearScoreApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("2");
										approval.setItemType("4");
										approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
										approval.setLeaderId(ass.getOaUserId());
										approval.setLeaderName(ass.getOaUserName());
										approval.setLeaderOrgId(ass.getOaOrgId());
										approval.setApprovalFlag("1");
										user = userService.getSysUserByUserKey(ass.getCreateUser());
											sendId = sendId + user.getUserId() + "|";
											zjXmCqjxYearScoreApprovalMapper.insert(approval);	
									}						
								}
								}							
							}
							dbZjXmCqjxYearAssistant.setAssessmentState("3");	
					}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "4")) {
						oneScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemOne());
						twoScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemTwo());
						threeScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemThree());
						fourScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemFour());
						fiveScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemFive());
						BigDecimal comprehensiveScore = oneScore.add(twoScore).add(threeScore).add(fourScore).add(fiveScore);
						BigDecimal quarterAverageScore = new BigDecimal(dbZjXmCqjxYearAssistant.getQuarterAverageScore());
						dbZjXmCqjxYearAssistant.setComprehensiveScore(comprehensiveScore.doubleValue());
//						dbZjXmCqjxYearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
						dbZjXmCqjxYearAssistant.setAssessmentState("4");							
					}
		        }else if(StrUtil.equals(dbZjXmCqjxYearAssistant.getAssessmentType(), "3")) {
		         if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "1")) {
						if(StrUtil.equals(itemType, "0")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("1");
//							approval.setOtherType(type);
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							approval.setItemType("2");
							List<ZjXmCqjxYearScoreApproval> scoreApprovalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0 || scoreApprovalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);
							}
						}else if(StrUtil.equals(itemType, "1")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("0");
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							approval.setItemType("2");
							List<ZjXmCqjxYearScoreApproval> scoreApprovalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0 || scoreApprovalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);	
							}
						}else if(StrUtil.equals(itemType, "2")) {
							approval = new ZjXmCqjxYearScoreApproval();
							approval.setApprovalFlag("1");
							approval.setExecutiveId(zjXmCqjxYearAssistant.getYearAssistantId());
							approval.setItemType("0");
							approvalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							approval.setItemType("1");
							List<ZjXmCqjxYearScoreApproval> scoreApprovalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(approval);
							if(approvalList.size()>0 || scoreApprovalList.size()>0) {
						        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
								return repEntity.ok(zjXmCqjxYearAssistant);	
							}							
						}
						//发送给领导班子副职
						head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
								List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
										.selectByZjXmCqjxDepartmentHeadList(head);	
								if(headList.size()>0) {
									ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
									detail.setOtherId(headList.get(0).getDepartmentHeadId());
									detail.setOtherType("1");
									List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
									if (detailList.size() > 0) {
									for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
										if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
											approval = new ZjXmCqjxYearScoreApproval();
											approval.setYearScoreApprovalId(UuidUtil.generate());
											approval.setCreateUserInfo(userKey, realName);
											approval.setOtherType("1");
											approval.setItemType("3");
											approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
											approval.setLeaderId(ass.getOaUserId());
											approval.setLeaderName(ass.getOaUserName());
											approval.setLeaderOrgId(ass.getOaOrgId());
											approval.setApprovalFlag("1");
											user = userService.getSysUserByUserKey(ass.getCreateUser());
												sendId = sendId + user.getUserId() + "|";
												zjXmCqjxYearScoreApprovalMapper.insert(approval);	
										}						
									}
									}else {
									//如果分管领导没有，则发给主管领导，当前评分为满分
										dbZjXmCqjxYearAssistant.setItemFour("8");	
										//发送给领导班子正职
										head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
											headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);	
											if(headList.size()>0) {
												detail = new ZjXmCqjxDepartmentHeadDetail();
												detail.setOtherId(headList.get(0).getDepartmentHeadId());
												detail.setOtherType("2");
												detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
												if (detailList.size() > 0) {
												for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
													if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
														approval = new ZjXmCqjxYearScoreApproval();
														approval.setYearScoreApprovalId(UuidUtil.generate());
														approval.setCreateUserInfo(userKey, realName);
														approval.setOtherType("2");
														approval.setItemType("4");
														approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
														approval.setLeaderId(ass.getOaUserId());
														approval.setLeaderName(ass.getOaUserName());
														approval.setLeaderOrgId(ass.getOaOrgId());
														approval.setApprovalFlag("1");
														user = userService.getSysUserByUserKey(ass.getCreateUser());
															sendId = sendId + user.getUserId() + "|";
															zjXmCqjxYearScoreApprovalMapper.insert(approval);	
													}						
												}
												}							
											}
											dbZjXmCqjxYearAssistant.setAssessmentState("2");										
									}
								}
								dbZjXmCqjxYearAssistant.setAssessmentState("1");						
						}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "2")) {
							//发送给领导班子正职
							head.setDepartmentId(dbZjXmCqjxYearAssistant.getDepartmentId());
								List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper
										.selectByZjXmCqjxDepartmentHeadList(head);	
								if(headList.size()>0) {
									ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
									detail.setOtherId(headList.get(0).getDepartmentHeadId());
									detail.setOtherType("2");
									List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
									if (detailList.size() > 0) {
									for(ZjXmCqjxDepartmentHeadDetail ass : detailList) {
										if(!StrUtil.equals(ass.getOaUserId(), zjXmCqjxYearAssistant.getCreateUser())) {
											approval = new ZjXmCqjxYearScoreApproval();
											approval.setYearScoreApprovalId(UuidUtil.generate());
											approval.setCreateUserInfo(userKey, realName);
											approval.setOtherType("2");
											approval.setItemType("4");
											approval.setExecutiveId(dbZjXmCqjxYearAssistant.getYearAssistantId());
											approval.setLeaderId(ass.getOaUserId());
											approval.setLeaderName(ass.getOaUserName());
											approval.setLeaderOrgId(ass.getOaOrgId());
											approval.setApprovalFlag("1");
											user = userService.getSysUserByUserKey(ass.getCreateUser());
												sendId = sendId + user.getUserId() + "|";
												zjXmCqjxYearScoreApprovalMapper.insert(approval);	
										}						
									}
									}							
								}
								dbZjXmCqjxYearAssistant.setAssessmentState("2");	
						}else if(StrUtil.equals(zjXmCqjxYearAssistant.getState(), "3")) {
							oneScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemOne());
							twoScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemTwo());
							threeScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemThree());
							fourScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemFour());
							fiveScore = new BigDecimal(dbZjXmCqjxYearAssistant.getItemFive());
							BigDecimal comprehensiveScore = oneScore.add(twoScore).add(threeScore).add(fourScore).add(fiveScore);
							BigDecimal quarterAverageScore = new BigDecimal(dbZjXmCqjxYearAssistant.getQuarterAverageScore());
							dbZjXmCqjxYearAssistant.setComprehensiveScore(comprehensiveScore.doubleValue());
//							dbZjXmCqjxYearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
							dbZjXmCqjxYearAssistant.setAssessmentState("3");							
						}
		        	
		        }
		        zjXmCqjxYearAssistantMapper.updateByPrimaryKey(dbZjXmCqjxYearAssistant);
			}
		return repEntity.ok(zjXmCqjxYearAssistant);
	}

	@Override
	public ResponseEntity jobZjXmCqjxYearAssistantLastScore(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
		BigDecimal quarterAverageScore = null;
			// 流程发起，将所有的员工平均分计算，发起
		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
		List<ZjXmCqjxExecutiveAssistant> assistantList = new ArrayList<ZjXmCqjxExecutiveAssistant>();
		zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
		zjXmCqjxYearAssistant.setAssessmentType("1");
		zjXmCqjxYearAssistant.setAssessmentState("3");
		List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(zjXmCqjxYearAssistant);
		for(ZjXmCqjxYearAssistant yearAssistant : zjXmCqjxYearAssistantList) {
			BigDecimal comprehensiveScore = new BigDecimal(yearAssistant.getComprehensiveScore());
			//查询四个季度的得分，计算平均分
			assistant = new ZjXmCqjxExecutiveAssistant();
			assistant.setAssessmentYears(yearAssistant.getAssessmentYears());
			assistant.setAssessmentType(yearAssistant.getAssessmentType());
			assistant.setAssessmentState("8");
			assistant.setCreateUser(yearAssistant.getCreateUser());
			assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
			if(assistantList.size()>0 && assistantList.size() == 4) {
				//计算季度平均分
		        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
		        BigDecimal bdAvg = new BigDecimal(avg);
		        avg = bdAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		        quarterAverageScore = new BigDecimal(avg);
		        yearAssistant.setQuarterAverageScore(avg);
		        yearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
		        yearAssistant.setModifyUserInfo("admin", "admin");
				zjXmCqjxYearAssistantMapper.updateByPrimaryKeySelective(yearAssistant);		        		        
			}else {
				//计算季度平均分
		        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
		        BigDecimal bdAvg = new BigDecimal(avg);
		        avg = bdAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		        quarterAverageScore = new BigDecimal(avg);
		        yearAssistant.setQuarterAverageScore(avg);
		        yearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
		        yearAssistant.setModifyUserInfo("admin", "admin");
				zjXmCqjxYearAssistantMapper.updateByPrimaryKeySelective(yearAssistant);	
			}
		}
		zjXmCqjxYearAssistant.setAssessmentType("2");
		zjXmCqjxYearAssistant.setAssessmentState("4");
		   zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(zjXmCqjxYearAssistant);
		for(ZjXmCqjxYearAssistant yearAssistant : zjXmCqjxYearAssistantList) {
			BigDecimal comprehensiveScore = new BigDecimal(yearAssistant.getComprehensiveScore());
			//查询四个季度的得分，计算平均分
			assistant = new ZjXmCqjxExecutiveAssistant();
			assistant.setAssessmentYears(yearAssistant.getAssessmentYears());
			assistant.setAssessmentType(yearAssistant.getAssessmentType());
			assistant.setAssessmentState("8");
			assistant.setCreateUser(yearAssistant.getCreateUser());
			assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
			if(assistantList.size()>0 && assistantList.size() == 4) {
				//计算季度平均分
		        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
		        BigDecimal bdAvg = new BigDecimal(avg);
		        avg = bdAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		        quarterAverageScore = new BigDecimal(avg);
		        yearAssistant.setQuarterAverageScore(avg);
		        yearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
		        yearAssistant.setModifyUserInfo("admin", "admin");
				zjXmCqjxYearAssistantMapper.updateByPrimaryKeySelective(yearAssistant);		        		        
			}else {
				//计算季度平均分
		        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
		        BigDecimal bdAvg = new BigDecimal(avg);
		        avg = bdAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		        quarterAverageScore = new BigDecimal(avg);
		        yearAssistant.setQuarterAverageScore(avg);
		        yearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
		        yearAssistant.setModifyUserInfo("admin", "admin");
				zjXmCqjxYearAssistantMapper.updateByPrimaryKeySelective(yearAssistant);	
			}
		}
		zjXmCqjxYearAssistant.setAssessmentType("3");
		zjXmCqjxYearAssistant.setAssessmentState("3");
		zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(zjXmCqjxYearAssistant);
		for(ZjXmCqjxYearAssistant yearAssistant : zjXmCqjxYearAssistantList) {
			BigDecimal comprehensiveScore = new BigDecimal(yearAssistant.getComprehensiveScore());
			//查询四个季度的得分，计算平均分
			assistant = new ZjXmCqjxExecutiveAssistant();
			assistant.setAssessmentYears(yearAssistant.getAssessmentYears());
			assistant.setAssessmentType(yearAssistant.getAssessmentType());
			assistant.setAssessmentState("8");
			assistant.setCreateUser(yearAssistant.getCreateUser());
			assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
			if(assistantList.size()>0 && assistantList.size() == 4) {
				//计算季度平均分
		        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
		        BigDecimal bdAvg = new BigDecimal(avg);
		        avg = bdAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		        quarterAverageScore = new BigDecimal(avg);
		        yearAssistant.setQuarterAverageScore(avg);
		        yearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
		        yearAssistant.setModifyUserInfo("admin", "admin");
				zjXmCqjxYearAssistantMapper.updateByPrimaryKeySelective(yearAssistant);		        		        
			}else {
				//计算季度平均分
		        double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));
		        BigDecimal bdAvg = new BigDecimal(avg);
		        avg = bdAvg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		        quarterAverageScore = new BigDecimal(avg);
		        yearAssistant.setQuarterAverageScore(avg);
		        yearAssistant.setYearFinalScore((comprehensiveScore.add(quarterAverageScore)).doubleValue());
		        yearAssistant.setModifyUserInfo("admin", "admin");
				zjXmCqjxYearAssistantMapper.updateByPrimaryKeySelective(yearAssistant);	
			}
		}	
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity getZjXmCqjxYearAssistantScoreDetail(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        if (zjXmCqjxYearAssistant == null) {
            zjXmCqjxYearAssistant = new ZjXmCqjxYearAssistant();
        }
        // 获取数据
        List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectZjXmCqjxYearAssistantScoreDetail(zjXmCqjxYearAssistant);
        return repEntity.okList(zjXmCqjxYearAssistantList, zjXmCqjxYearAssistantList.size());   
	}

	@Override
	public ResponseEntity getZjXmCqjxYearAssistantListByDeptLeader(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);    
        // 判断当前人是否是部门负责人
//        ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//        head.setUserKey(userKey);
//        List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
        ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
        detail.setOtherType("0");
        detail.setOaUserId(userKey);
        List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        if(detailList.size() == 0) {
        	List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxAssessmentManage>();
    		return repEntity.okList(zjXmCqjxAssessmentManageList, 0);           	
        }else {
        	zjXmCqjxYearAssistant.setDepartmentId(detailList.get(0).getOaOrgId());
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxYearAssistant.getPage(),zjXmCqjxYearAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxYearAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxYearAssistantMapper.selectZjXmCqjxYearAssessmentManageListByDeptHeader(zjXmCqjxYearAssistant);
        // 得到分页信息
        PageInfo<ZjXmCqjxYearAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);

        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}
	/**
	 * 
	 *公司机关年度绩效考核流程已办
	 */
	@Override
	public ResponseEntity getZjXmCqjxYearAssistantDoneList(ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxYearAssistant.setCreateUser(userKey);
//        zjXmCqjxProjectOverallEvaluationAssistant.setLeaderSee("1");
        // 分页查询
        PageHelper.startPage(zjXmCqjxYearAssistant.getPage(),zjXmCqjxYearAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectEvaluationLeaderDoneListByUserKey(zjXmCqjxYearAssistant);
        for(ZjXmCqjxYearAssistant assistant : zjXmCqjxYearAssistantList) {
        	if(StrUtil.equals(assistant.getAssessmentType(), "1")) {
        		switch (assistant.getItemType()) {
				case "0":
					assistant.setItemText("周边业绩考核");	
					assistant.setItemScore("8");
					break;
				case "1":
					assistant.setItemText("对公司各单位服务和指导职能的发挥");
					assistant.setItemScore("8");
					break;
				case "2":
					assistant.setItemText("工作成绩");
					assistant.setItemScore("12");					
					break;
				case "3":
					assistant.setItemText("公司主要领导总体评价");
					assistant.setItemScore("12");					
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "2")) {
        		switch (assistant.getItemType()) {
				case "0":
					assistant.setItemText("周边业绩考核");	
					assistant.setItemScore("5");						
					break;
				case "1":
					assistant.setItemText("对公司各单位服务和指导职能的发挥");	
					assistant.setItemScore("10");						
					break;
				case "2":
					assistant.setItemText("工作成绩");
					assistant.setItemScore("10");						
					break;
				case "3":
					assistant.setItemText("公司主要领导总体评价");
					assistant.setItemScore("10");
					break;
				case "4":
					assistant.setItemText("督办工作");
					assistant.setItemScore("5");						
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "3")) {
           		switch (assistant.getItemType()) {
    				case "0":
    					assistant.setItemText("工作任务完成情况");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "1":
    					assistant.setItemText("工作积极性");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "2":
    					assistant.setItemText("部门负责人评价");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "3":
    					assistant.setItemText("公司分管领导评价");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "4":
    					assistant.setItemText("公司主管领导评价");	
    					assistant.setItemScore("8");    					
    					break;
    				}        		
        	}
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxYearAssistant> p = new PageInfo<>(zjXmCqjxYearAssistantList);
        return repEntity.okList(zjXmCqjxYearAssistantList, p.getTotal());   
	}
}
