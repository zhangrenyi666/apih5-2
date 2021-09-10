package com.apih5.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectEvaluationApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectOverallEvaluationAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSuperviseApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;
import com.apih5.service.ZjXmCqjxProjectOverallEvaluationAssistantService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@Service("zjXmCqjxProjectOverallEvaluationAssistantService")
public class ZjXmCqjxProjectOverallEvaluationAssistantServiceImpl implements ZjXmCqjxProjectOverallEvaluationAssistantService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectOverallEvaluationAssistantMapper zjXmCqjxProjectOverallEvaluationAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectEvaluationApprovalMapper zjXmCqjxProjectEvaluationApprovalMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectSuperviseApprovalMapper zjXmCqjxProjectSuperviseApprovalMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDepartmentHeadMapper zjXmCqjxProjectDepartmentHeadMapper;
	
	@Autowired(required = true)
	private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectAssessmentManageMapper zjXmCqjxProjectAssessmentManageMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;
	
	@Autowired(required = true)
	private UserService userService;

    @Autowired(required = true)
    private ZjXmCqjxProjectAssistantLeaderApprovalMapper zjXmCqjxProjectAssistantLeaderApprovalMapper;
    
	@Override
	public ResponseEntity getZjXmCqjxProjectOverallAssistantListByPrimaryKey(
			ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        if (zjXmCqjxProjectOverallEvaluationAssistant == null) {
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
        }
        List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxProjectOverallEvaluationAssistantList = new ArrayList<ZjXmCqjxProjectOverallEvaluationAssistant>();
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectOverallEvaluationAssistant.getPage(),zjXmCqjxProjectOverallEvaluationAssistant.getLimit());
        // 获取数据
        if(StrUtil.isNotEmpty(zjXmCqjxProjectOverallEvaluationAssistant.getManagerId())) {
        	ZjXmCqjxProjectOverallEvaluationAssistant assistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
        	assistant.setManagerId(zjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
        	assistant.setCreateUser(zjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
        	List<ZjXmCqjxProjectOverallEvaluationAssistant> assistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByZjXmCqjxProjectOverallEvaluationAssistantList(assistant);
        	if(assistantList.size()>0) {
        		zjXmCqjxProjectOverallEvaluationAssistant.setOverallEvaluationId(assistantList.get(0).getOverallEvaluationId());
        	}else {
                return repEntity.okList(zjXmCqjxProjectOverallEvaluationAssistantList, zjXmCqjxProjectOverallEvaluationAssistantList.size());
        	}
        }
        ZjXmCqjxProjectOverallEvaluationAssistant dbZjXmCqjxProjectOverallEvaluationAssistant = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
    	if(StrUtil.equals(dbZjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType(), "1")) {
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("对项目各项工作开展的指导情况");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("8");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目中层干部综合评价");
            zjXmCqjxProjectOverallEvaluationAssistant.setOverallEvaluationId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("与其他班子成员配合情况");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("8");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("其他领导班子成员综合评价");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("对公司部署工作的完成情况");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("7");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("公司机关分管领导评价");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("对公司部署工作的完成情况");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("7");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("公司主管领导评价");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
    	}else if(StrUtil.equals(dbZjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType(), "2")) {
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("周边业绩");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("7");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目其他部门负责人、副总师、经理助理综合评价");
            zjXmCqjxProjectOverallEvaluationAssistant.setOverallEvaluationId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("对本部门管理情况");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("7");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("部门员工综合评价");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("对项目分管领导对其工作开展情况的评价");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("6");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目分管领导评价");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("项目主要领导对其工作开展情况的评价");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("5");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目主管领导评价");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);    
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("督办工作开展情况");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFiveScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("5");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目办公室");
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant); 
            
    	}else if(StrUtil.equals(dbZjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType(), "3")) {
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("工作态度");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("8");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("部门负责人");
            zjXmCqjxProjectOverallEvaluationAssistant.setOverallEvaluationId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("工作协作性");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("8");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("部门员工综合评价");            
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("工作完成质量");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("7");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目分管领导评价");               
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);
            
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
            zjXmCqjxProjectOverallEvaluationAssistant.setItemText("工作完成质量");
            zjXmCqjxProjectOverallEvaluationAssistant.setItemScore(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
            zjXmCqjxProjectOverallEvaluationAssistant.setAssistantScore("7");
            zjXmCqjxProjectOverallEvaluationAssistant.setStateText("项目主管领导评价");               
            zjXmCqjxProjectOverallEvaluationAssistantList.add(zjXmCqjxProjectOverallEvaluationAssistant);      		      		
    	}        
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectOverallEvaluationAssistant> p = new PageInfo<>(zjXmCqjxProjectOverallEvaluationAssistantList);

        return repEntity.okList(zjXmCqjxProjectOverallEvaluationAssistantList, p.getTotal());
	}
	
	@Override
	public ResponseEntity getZjXmCqjxProjectEvaluationAssistantTodoList(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxProjectOverallEvaluationAssistant.setCreateUser(userKey);
//        zjXmCqjxProjectOverallEvaluationAssistant.setLeaderSee("1");
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectOverallEvaluationAssistant.getPage(),zjXmCqjxProjectOverallEvaluationAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectProjectEvaluationLeaderTodoListByUserKey(zjXmCqjxProjectOverallEvaluationAssistant);
        for(ZjXmCqjxProjectOverallEvaluationAssistant assistant : zjXmCqjxExecutiveAssistantList) {
        	if(StrUtil.equals(assistant.getAssessmentType(), "1")) {
        		switch (assistant.getAssessmentState()) {
				case "0":
					assistant.setItemText("对项目各项工作开展的指导情况");	
					assistant.setItemScore("8");
					break;
				case "1":
					assistant.setItemText("与其他班子成员配合情况");
					assistant.setItemScore("8");
					break;
				case "2":
					assistant.setItemText("对公司部署工作的完成情况");
					assistant.setItemScore("7");					
					break;
				case "3":
					assistant.setItemText("对公司部署工作的完成情况");
					assistant.setItemScore("7");					
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "2") && StrUtil.equals(assistant.getFlowType(), "1")) {
        		switch (assistant.getAssessmentState()) {
				case "0":
					assistant.setItemText("周边业绩");	
					assistant.setItemScore("7");						
					break;
				case "1":
					assistant.setItemText("对本部门管理情况");	
					assistant.setItemScore("7");						
					break;
				case "2":
					assistant.setItemText("项目分管领导对其工作开展情况的评价");
					assistant.setItemScore("6");						
					break;
				case "3":
					assistant.setItemText("项目主要领导对其工作开展情况的评价");
					assistant.setItemScore("5");						
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "2") && StrUtil.equals(assistant.getFlowType(), "2")) {
        		assistant.setItemText("督办工作开展情况");
        		assistant.setItemScore("5");
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "3")) {
           		switch (assistant.getAssessmentState()) {
    				case "0":
    					assistant.setItemText("工作态度");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "1":
    					assistant.setItemText("工作协作性");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "2":
    					assistant.setItemText("工作完成质量");	
    	        		assistant.setItemScore("7");    					
    					break;
    				case "3":
    					assistant.setItemText("工作完成质量");	
    	        		assistant.setItemScore("7");    					
    					break;
    				}        		
        	}
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectOverallEvaluationAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());   
	}

	@Override
	public ResponseEntity zjXmCqjxProjectEvaluationAssistantApproval(
			ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String type = "";
        String itemType = ""; 
        BigDecimal oneScore = null;
        BigDecimal twoScore = null;
        BigDecimal threeScore = null;
        BigDecimal fourScore = null;
        BigDecimal fiveScore = null;
        String sendId = "";
        ZjXmCqjxProjectEvaluationApproval approval = zjXmCqjxProjectEvaluationApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getAssistantLeaderApprovalId());
		approval.setApprovalFlag("2");
		approval.setModifyUserInfo(userKey, realName);
		zjXmCqjxProjectEvaluationApprovalMapper.updateByPrimaryKeySelective(approval);
		type = approval.getOtherType();
		itemType = approval.getItemType();	
		approval = new ZjXmCqjxProjectEvaluationApproval();
		approval.setApprovalFlag("0");
		approval.setExecutiveId(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
		approval.setOtherType(type);
		List<ZjXmCqjxProjectEvaluationApproval> approvalList = zjXmCqjxProjectEvaluationApprovalMapper.selectByZjXmCqjxProjectEvaluationApprovalList(approval);
		approval.setApprovalFlag("1");
		if(approvalList.size()>0) {
			approval = approvalList.get(0);
			approval.setApprovalFlag("1");
			zjXmCqjxProjectEvaluationApprovalMapper.updateByPrimaryKeySelective(approval);			
		}else if(zjXmCqjxProjectEvaluationApprovalMapper.selectByZjXmCqjxProjectEvaluationApprovalList(approval).size()>0){
			return repEntity.ok(zjXmCqjxProjectOverallEvaluationAssistant);
		} else {
			ZjXmCqjxProjectOverallEvaluationAssistant dbZjXmCqjxProjectOverallEvaluationAssistant = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
			//如果当前节点完成，则计算评分
			approval = new ZjXmCqjxProjectEvaluationApproval();
			approval.setOtherType(type);
			approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
			approvalList = zjXmCqjxProjectEvaluationApprovalMapper.selectByZjXmCqjxProjectEvaluationApprovalList(approval);
//	        double sum = approvalList.stream().mapToDouble(ZjXmCqjxProjectEvaluationApproval::getLeaderScore).sum();
	        double sum = approvalList.stream().mapToDouble(ZjXmCqjxProjectEvaluationApproval::getLeaderScore).average().getAsDouble();
	        DecimalFormat df = new DecimalFormat("0.0");
	        switch (itemType) {
			case "1":
		        dbZjXmCqjxProjectOverallEvaluationAssistant.setItemOneScore(df.format(sum));				
				break;
			case "2":
				dbZjXmCqjxProjectOverallEvaluationAssistant.setItemTwoScore(df.format(sum));					
				break;
			case "3":
				dbZjXmCqjxProjectOverallEvaluationAssistant.setItemThreeScore(df.format(sum));					
				break;
			case "4":
				dbZjXmCqjxProjectOverallEvaluationAssistant.setItemFourScore(df.format(sum));					
				break;
			}
			// 新增审批表
			ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
			SysUser user = new SysUser();
		//进行下一环节,判断当前人节点
			if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType(), "1")) {
				if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "2")) {
				    //发送给其他领导班子
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
						List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
								.selectByZjXmCqjxProjectDepartmentHeadList(head);	
						if(headList.size()>0) {
							ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
							detail.setOtherId(headList.get(0).getDepartmentHeadId());
							detail.setOtherType("1");
							List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
							if (detailList.size() > 0) {
							for(ZjXmCqjxProjectDepartmentHeadDetail ass : detailList) {
								if(!StrUtil.equals(ass.getOaUserId(), dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser())) {
									approval = new ZjXmCqjxProjectEvaluationApproval();
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("1");
									approval.setItemType("2");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(ass.getOaUserId());
									approval.setLeaderName(ass.getOaUserName());
									approval.setLeaderOrgId(ass.getOaOrgId());
									approval.setApprovalFlag("1");
									user = userService.getSysUserByUserKey(ass.getCreateUser());
										sendId = sendId + user.getUserId() + "|";
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);	
								}						
							}
							}							
						}
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("1");
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "3")) {
				 //发送给公司分管领导
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("3");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if(detailList.size()>1) {
							ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
							exeAssistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
							exeAssistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
							List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
							if(assList.size()>0) {											
								ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
								assistantApproval.setOtherType("3");
								List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
								if(assistantApprovalList.size()>0) {
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("3");
									approval.setItemType("3");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
									approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
									approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
									approval.setApprovalFlag("1");
									sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
								}
							}
						}else {
						for(int i = 0; i<detailList.size(); i++ ) {
							approval = new ZjXmCqjxProjectEvaluationApproval();
							approval.setAssistantLeaderApprovalId(UuidUtil.generate());
							approval.setCreateUserInfo(userKey, realName);
							approval.setOtherType("3");
							approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
							approval.setLeaderId(detailList.get(i).getOaUserId());
							approval.setLeaderName(detailList.get(i).getOaUserName());
							approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
							approval.setItemType("3");							
							if(i == 0) {
								approval.setApprovalFlag("1");
								user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
								sendId = sendId + user.getUserId() + "|";
							}else {
								approval.setApprovalFlag("0");
							}
							zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);							
						}
						}
					}
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "4")) {
				//发送给公司主管领导	
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());					
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("4");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						for(int i = 0; i<detailList.size(); i++ ) {
							approval = new ZjXmCqjxProjectEvaluationApproval();
							approval.setAssistantLeaderApprovalId(UuidUtil.generate());
							approval.setCreateUserInfo(userKey, realName);
							approval.setOtherType("4");
							approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
							approval.setLeaderId(detailList.get(i).getOaUserId());
							approval.setLeaderName(detailList.get(i).getOaUserName());
							approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
							approval.setItemType("4");							
							if(i == 0) {
								approval.setApprovalFlag("1");
								user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
								sendId = sendId + user.getUserId() + "|";
							}else {
								approval.setApprovalFlag("0");
							}
							zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);							
						}
					}	
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("3");					
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "5")) {
					//计算得分，并将得分放入考核表里
					oneScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
					twoScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
					threeScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
					fourScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
					BigDecimal quarterScore = oneScore.add(twoScore).add(threeScore).add(fourScore);
					dbZjXmCqjxProjectOverallEvaluationAssistant.setQuarterScore(quarterScore+"");
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(assistantList.get(0).getExecutiveId());
					dbAssistant.setCooperationScore(quarterScore.doubleValue());
					dbAssistant.setCooperationFlag("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("4");
				}
			}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType(), "2")){
				if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "2")) {
				//发送给部门员工
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("6");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						for(int i = 0; i<detailList.size(); i++ ) {
							approval = new ZjXmCqjxProjectEvaluationApproval();
							approval.setAssistantLeaderApprovalId(UuidUtil.generate());
							approval.setCreateUserInfo(userKey, realName);
							approval.setOtherType("6");
							approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
							approval.setLeaderId(detailList.get(i).getOaUserId());
							approval.setLeaderName(detailList.get(i).getOaUserName());
							approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
							approval.setItemType("2");									
							approval.setApprovalFlag("1");
							user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
							sendId = sendId + user.getUserId() + "|";
//							if(i == 0) {
//							}else {
//								approval.setApprovalFlag("0");
//							}
							zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);							
						}
					}	
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("1");					
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "3")) {
					//发送给项目分管领导变更
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("1");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if(detailList.size()>1) {
							ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
							exeAssistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
							exeAssistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
							List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
							if(assList.size()>0) {											
								ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
								assistantApproval.setOtherType("1");
								List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
								if(assistantApprovalList.size()>0) {
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("1");
									approval.setItemType("3");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
									approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
									approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
									approval.setApprovalFlag("1");
									sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
								}
							}
							dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
						}else {
							for(int i = 0; i<detailList.size(); i++ ) {
								approval = new ZjXmCqjxProjectEvaluationApproval();
								approval.setAssistantLeaderApprovalId(UuidUtil.generate());
								approval.setCreateUserInfo(userKey, realName);
								approval.setOtherType("1");
								approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
								approval.setLeaderId(detailList.get(i).getOaUserId());
								approval.setLeaderName(detailList.get(i).getOaUserName());
								approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
								approval.setItemType("3");								
								if(i == 0) {
									approval.setApprovalFlag("1");
									user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
									sendId = sendId + user.getUserId() + "|";
								}else {
									approval.setApprovalFlag("0");
								}
								zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);							
							}							
						}
					}	
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "4")) {
					//发送给主管领导变更
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("2");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if(detailList.size()>1) {
							ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
							exeAssistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
							exeAssistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
							List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
							if(assList.size()>0) {											
								ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
								assistantApproval.setOtherType("2");
								List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
								if(assistantApprovalList.size()>0) {
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("2");
									approval.setItemType("4");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
									approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
									approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
									approval.setApprovalFlag("1");
									sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
								}
							}
							dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
						}else {
							for(int i = 0; i<detailList.size(); i++ ) {
								approval = new ZjXmCqjxProjectEvaluationApproval();
								approval.setAssistantLeaderApprovalId(UuidUtil.generate());
								approval.setCreateUserInfo(userKey, realName);
								approval.setOtherType("2");
								approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
								approval.setLeaderId(detailList.get(i).getOaUserId());
								approval.setLeaderName(detailList.get(i).getOaUserName());
								approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
								approval.setItemType("4");								
								if(i == 0) {
									approval.setApprovalFlag("1");
									user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
									sendId = sendId + user.getUserId() + "|";
								}else {
									approval.setApprovalFlag("0");
								}
								zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);							
							}							
						}
					}			
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("3");
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "5")) {
					ZjXmCqjxProjectSuperviseApproval superviseApproval = new ZjXmCqjxProjectSuperviseApproval();
					superviseApproval.setApprovalFlag("1");
					superviseApproval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
					List<ZjXmCqjxProjectSuperviseApproval> superviseApprovalList = zjXmCqjxProjectSuperviseApprovalMapper.selectByZjXmCqjxProjectSuperviseApprovalList(superviseApproval);
					if(superviseApprovalList.size() == 0) {
						//计算得分，并将得分放入考核表里
						oneScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
						twoScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
						threeScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
						fourScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
						
						fiveScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFiveScore());
						
						BigDecimal quarterScore = oneScore.add(twoScore).add(threeScore).add(fourScore).add(fiveScore);
						dbZjXmCqjxProjectOverallEvaluationAssistant.setQuarterScore(quarterScore+"");
						ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
						assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
						assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
						List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(assistantList.get(0).getExecutiveId());
						dbAssistant.setCooperationScore(quarterScore.doubleValue());
						dbAssistant.setCooperationFlag("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("4");	
				}
			}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType(), "3")){
				if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "2")) {
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("6");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						//员工股工作协作性考核，如果部门下只有自己，那么跳出当前节点
						if(detailList.size() == 1) {
							sum = 8;
							dbZjXmCqjxProjectOverallEvaluationAssistant.setItemTwoScore(df.format(sum));
							assistant = new ZjXmCqjxProjectExecutiveAssistant();
							assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
							assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
							assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
								head.setDepartmentId(assistantList.get(0).getDepartmentId());
							 headList = zjXmCqjxProjectDepartmentHeadMapper
									.selectByZjXmCqjxProjectDepartmentHeadList(head);	
							if(headList.size()>0) {
								 detail = new ZjXmCqjxProjectDepartmentHeadDetail();
								detail.setOtherId(headList.get(0).getDepartmentHeadId());
								detail.setOtherType("1");
								 detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
								if(detailList.size()>1) {
									ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
									exeAssistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
									exeAssistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
									List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
									if(assList.size()>0) {											
										ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
										assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
										assistantApproval.setOtherType("1");
										List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
										if(assistantApprovalList.size()>0) {
											approval.setAssistantLeaderApprovalId(UuidUtil.generate());
											approval.setCreateUserInfo(userKey, realName);
											approval.setOtherType("1");
											approval.setItemType("3");
											approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
											approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
											approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
											approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
											approval.setApprovalFlag("1");
											sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
											zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
										}
									}
									dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
								}else {
									for(int i = 0; i<detailList.size(); i++ ) {
										approval = new ZjXmCqjxProjectEvaluationApproval();
										approval.setAssistantLeaderApprovalId(UuidUtil.generate());
										approval.setCreateUserInfo(userKey, realName);
										approval.setOtherType("1");
										approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
										approval.setLeaderId(detailList.get(i).getOaUserId());
										approval.setLeaderName(detailList.get(i).getOaUserName());
										approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
										approval.setItemType("3");								
										if(i == 0) {
											approval.setApprovalFlag("1");
											user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
											sendId = sendId + user.getUserId() + "|";
										}else {
											approval.setApprovalFlag("0");
										}
										zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);									
								}							
								}
								dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");							
							}
						}else {
							for(int i = 0; i<detailList.size(); i++ ) {
								if(!StrUtil.equals(detailList.get(i).getOaUserId(), dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser())) {
									approval = new ZjXmCqjxProjectEvaluationApproval();
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("6");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(detailList.get(i).getOaUserId());
									approval.setLeaderName(detailList.get(i).getOaUserName());
									approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
									approval.setItemType("2");		
									approval.setApprovalFlag("1");
									user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
									sendId = sendId + user.getUserId() + "|";
//								if(i == 0) {
//									approval.setApprovalFlag("1");
//									user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
//									sendId = sendId + user.getUserId() + "|";
//								}else {
//									approval.setApprovalFlag("0");
//								}
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);									
								}						
							}
							dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("1");
						}
					}
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "3")) {
					//发送给分管领导变更，如果是多位分管领导，则需要匹配责任指标表里的分管领导
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("1");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if(detailList.size()>1) {
							ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
							exeAssistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
							exeAssistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
							List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
							if(assList.size()>0) {											
								ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
								assistantApproval.setOtherType("1");
								List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
								if(assistantApprovalList.size()>0) {
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("1");
									approval.setItemType("3");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
									approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
									approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
									approval.setApprovalFlag("1");
									sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
								}
							}
							dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
						}else {
							for(int i = 0; i<detailList.size(); i++ ) {
								approval = new ZjXmCqjxProjectEvaluationApproval();
								approval.setAssistantLeaderApprovalId(UuidUtil.generate());
								approval.setCreateUserInfo(userKey, realName);
								approval.setOtherType("1");
								approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
								approval.setLeaderId(detailList.get(i).getOaUserId());
								approval.setLeaderName(detailList.get(i).getOaUserName());
								approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
								approval.setItemType("3");								
								if(i == 0) {
									approval.setApprovalFlag("1");
									user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
									sendId = sendId + user.getUserId() + "|";
								}else {
									approval.setApprovalFlag("0");
								}
								zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);									
						}							
						}
					}
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("2");
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "4")) {
					//发送给主管领导变更，如果是多位主管领导，则需要匹配其部门负责人责任指标表里的主管领导
//					ZjXmCqjxProjectAssessmentManage manager = zjXmCqjxProjectAssessmentManageMapper.selectByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
//					head.setDepartmentId(manager.getAssessmentDept());
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
						head.setDepartmentId(assistantList.get(0).getDepartmentId());
					List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper
							.selectByZjXmCqjxProjectDepartmentHeadList(head);	
					if(headList.size()>0) {
						ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
						detail.setOtherId(headList.get(0).getDepartmentHeadId());
						detail.setOtherType("2");
						List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
						if(detailList.size()>1) {
							ZjXmCqjxProjectExecutiveAssistant exeAssistant = new ZjXmCqjxProjectExecutiveAssistant();
							exeAssistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
							exeAssistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
							List<ZjXmCqjxProjectExecutiveAssistant> assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
							if(assList.size()>0) {
								//先取部门负责人
								ZjXmCqjxProjectAssistantLeaderApproval assistantApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
								assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
								assistantApproval.setOtherType("0");
								List<ZjXmCqjxProjectAssistantLeaderApproval> assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);
								//再取部门负责人的主管领导
								exeAssistant.setCreateUser(assistantApprovalList.get(0).getLeaderId());
								exeAssistant.setManagerId("");
								exeAssistant.setAssessmentYears(dbZjXmCqjxProjectOverallEvaluationAssistant.getAssessmentYears());
								exeAssistant.setAssessmentType("2");
								assList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(exeAssistant);
								assistantApproval.setExecutiveId(assList.get(0).getExecutiveId());
								assistantApproval.setOtherType("2");
								assistantApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(assistantApproval);								
								if(assistantApprovalList.size()>0) {
									approval.setAssistantLeaderApprovalId(UuidUtil.generate());
									approval.setCreateUserInfo(userKey, realName);
									approval.setOtherType("2");
									approval.setItemType("4");
									approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
									approval.setLeaderId(assistantApprovalList.get(0).getLeaderId());
									approval.setLeaderName(assistantApprovalList.get(0).getLeaderName());
									approval.setLeaderOrgId(assistantApprovalList.get(0).getLeaderOrgId());
									approval.setApprovalFlag("1");
									sendId = sendId + assistantApprovalList.get(0).getLeaderId() + "|";
									zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);												
								}
							}							
						}else {
							for(int i = 0; i<detailList.size(); i++ ) {
								approval = new ZjXmCqjxProjectEvaluationApproval();
								approval.setAssistantLeaderApprovalId(UuidUtil.generate());
								approval.setCreateUserInfo(userKey, realName);
								approval.setOtherType("2");
								approval.setExecutiveId(dbZjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
								approval.setLeaderId(detailList.get(i).getOaUserId());
								approval.setLeaderName(detailList.get(i).getOaUserName());
								approval.setLeaderOrgId(detailList.get(i).getOaOrgId());
								approval.setItemType("4");								
								if(i == 0) {
									approval.setApprovalFlag("1");
									user = userService.getSysUserByUserKey(detailList.get(i).getOaUserId());
									sendId = sendId + user.getUserId() + "|";
								}else {
									approval.setApprovalFlag("0");
								}
								zjXmCqjxProjectEvaluationApprovalMapper.insert(approval);							
							}							
						}
					}	
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("3");
				}else if(StrUtil.equals(zjXmCqjxProjectOverallEvaluationAssistant.getState(), "5")) {
					//计算得分，并将得分放入考核表里
					oneScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
					twoScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
					threeScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
					fourScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
					BigDecimal quarterScore = oneScore.add(twoScore).add(threeScore).add(fourScore);
					dbZjXmCqjxProjectOverallEvaluationAssistant.setQuarterScore(quarterScore+"");
					ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
					assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
					assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
					List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
					ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(assistantList.get(0).getExecutiveId());
					dbAssistant.setCooperationScore(quarterScore.doubleValue());
					dbAssistant.setCooperationFlag("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					dbZjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState("4");
				}
			}
			dbZjXmCqjxProjectOverallEvaluationAssistant.setModifyUserInfo(userKey, realName);
			zjXmCqjxProjectOverallEvaluationAssistantMapper.updateByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant);
		}
//		ZjXmCqjxProjectSuperviseApproval approval = new ZjXmCqjxProjectSuperviseApproval();
		return repEntity.ok(zjXmCqjxProjectOverallEvaluationAssistant);
	}

	@Override
	public ResponseEntity zjXmCqjxProjectOfficeAssistantApproval(
			ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);		
        ZjXmCqjxProjectSuperviseApproval approval = zjXmCqjxProjectSuperviseApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getAssistantLeaderApprovalId());
        approval.setApprovalFlag("2");
        approval.setModifyUserInfo(userKey, realName);
        zjXmCqjxProjectSuperviseApprovalMapper.updateByPrimaryKeySelective(approval);
        approval = new ZjXmCqjxProjectSuperviseApproval();
        approval.setExecutiveId(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
        approval.setOtherType("7");
        approval.setApprovalFlag("0");
        List<ZjXmCqjxProjectSuperviseApproval> approvalList = zjXmCqjxProjectSuperviseApprovalMapper.selectByZjXmCqjxProjectSuperviseApprovalList(approval);
        if(approvalList.size()>0) {
			approval = approvalList.get(0);
			approval.setApprovalFlag("1");
			zjXmCqjxProjectSuperviseApprovalMapper.updateByPrimaryKeySelective(approval);	
        }else {
        	approval.setApprovalFlag("2");
        	approvalList = zjXmCqjxProjectSuperviseApprovalMapper.selectByZjXmCqjxProjectSuperviseApprovalList(approval);        	
	        double sum = approvalList.stream().mapToDouble(ZjXmCqjxProjectSuperviseApproval::getLeaderScore).sum();
        	ZjXmCqjxProjectOverallEvaluationAssistant dbZjXmCqjxProjectOverallEvaluationAssistant = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
        	dbZjXmCqjxProjectOverallEvaluationAssistant.setItemFiveScore(sum+"");
			dbZjXmCqjxProjectOverallEvaluationAssistant.setModifyUserInfo(userKey, realName);
			zjXmCqjxProjectOverallEvaluationAssistantMapper.updateByPrimaryKey(dbZjXmCqjxProjectOverallEvaluationAssistant);
			if(StrUtil.equals(dbZjXmCqjxProjectOverallEvaluationAssistant.getAssessmentState(), "4")) {
				BigDecimal oneScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
				BigDecimal twoScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
				BigDecimal threeScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
				BigDecimal fourScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
				BigDecimal fiveScore = new BigDecimal(dbZjXmCqjxProjectOverallEvaluationAssistant.getItemFiveScore());
				BigDecimal quarterScore = oneScore.add(twoScore).add(threeScore).add(fourScore).add(fiveScore);
				dbZjXmCqjxProjectOverallEvaluationAssistant.setQuarterScore(quarterScore+"");
				ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
				assistant.setManagerId(dbZjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
				assistant.setCreateUser(dbZjXmCqjxProjectOverallEvaluationAssistant.getCreateUser());
				List<ZjXmCqjxProjectExecutiveAssistant> assistantList = zjXmCqjxProjectExecutiveAssistantMapper.selectByZjXmCqjxProjectExecutiveAssistantList(assistant);
				ZjXmCqjxProjectExecutiveAssistant dbAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(assistantList.get(0).getExecutiveId());
				dbAssistant.setCooperationScore(quarterScore.doubleValue());
				dbAssistant.setCooperationFlag("1");
				dbAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
			}
        }
		return repEntity.ok(zjXmCqjxProjectOverallEvaluationAssistant);
	}
	
    @Override
    public ResponseEntity getZjXmCqjxProjectOverallEvaluationAssistantListByCondition(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        if (zjXmCqjxProjectOverallEvaluationAssistant == null) {
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectOverallEvaluationAssistant.getPage(),zjXmCqjxProjectOverallEvaluationAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxProjectOverallEvaluationAssistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByZjXmCqjxProjectOverallEvaluationAssistantList(zjXmCqjxProjectOverallEvaluationAssistant);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectOverallEvaluationAssistant> p = new PageInfo<>(zjXmCqjxProjectOverallEvaluationAssistantList);

        return repEntity.okList(zjXmCqjxProjectOverallEvaluationAssistantList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectOverallEvaluationAssistantDetails(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        if (zjXmCqjxProjectOverallEvaluationAssistant == null) {
            zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
        }
        // 获取数据
        ZjXmCqjxProjectOverallEvaluationAssistant dbZjXmCqjxProjectOverallEvaluationAssistant = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
        // 数据存在
        if (dbZjXmCqjxProjectOverallEvaluationAssistant != null) {
            return repEntity.ok(dbZjXmCqjxProjectOverallEvaluationAssistant);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectOverallEvaluationAssistant(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectOverallEvaluationAssistant.setOverallEvaluationId(UuidUtil.generate());
        zjXmCqjxProjectOverallEvaluationAssistant.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectOverallEvaluationAssistantMapper.insert(zjXmCqjxProjectOverallEvaluationAssistant);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectOverallEvaluationAssistant);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectOverallEvaluationAssistant(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectOverallEvaluationAssistant dbzjXmCqjxProjectOverallEvaluationAssistant = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId());
        if (dbzjXmCqjxProjectOverallEvaluationAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectOverallEvaluationAssistant.getOverallEvaluationId())) {
           // 考核类别
           dbzjXmCqjxProjectOverallEvaluationAssistant.setAssessmentType(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentType());
           // 考核计划ID
           dbzjXmCqjxProjectOverallEvaluationAssistant.setManagerId(zjXmCqjxProjectOverallEvaluationAssistant.getManagerId());
           // 考核年度
           dbzjXmCqjxProjectOverallEvaluationAssistant.setAssessmentYears(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentYears());
           // 考核标题
           dbzjXmCqjxProjectOverallEvaluationAssistant.setAssessmentTitle(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentTitle());
           // 考核季度
           dbzjXmCqjxProjectOverallEvaluationAssistant.setAssessmentQuarter(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentQuarter());
           // 考核状态
           dbzjXmCqjxProjectOverallEvaluationAssistant.setAssessmentState(zjXmCqjxProjectOverallEvaluationAssistant.getAssessmentState());
           // 岗位
           dbzjXmCqjxProjectOverallEvaluationAssistant.setPosition(zjXmCqjxProjectOverallEvaluationAssistant.getPosition());
           // 选项一得分
           dbzjXmCqjxProjectOverallEvaluationAssistant.setItemOneScore(zjXmCqjxProjectOverallEvaluationAssistant.getItemOneScore());
           // 选项二得分
           dbzjXmCqjxProjectOverallEvaluationAssistant.setItemTwoScore(zjXmCqjxProjectOverallEvaluationAssistant.getItemTwoScore());
           // 选项三得分
           dbzjXmCqjxProjectOverallEvaluationAssistant.setItemThreeScore(zjXmCqjxProjectOverallEvaluationAssistant.getItemThreeScore());
           // 选项四得分
           dbzjXmCqjxProjectOverallEvaluationAssistant.setItemFourScore(zjXmCqjxProjectOverallEvaluationAssistant.getItemFourScore());
           // 选项五得分
           dbzjXmCqjxProjectOverallEvaluationAssistant.setItemFiveScore(zjXmCqjxProjectOverallEvaluationAssistant.getItemFiveScore());
           // 锁定
           dbzjXmCqjxProjectOverallEvaluationAssistant.setAssistantLock(zjXmCqjxProjectOverallEvaluationAssistant.getAssistantLock());
           // 领导是否可见
           dbzjXmCqjxProjectOverallEvaluationAssistant.setLeaderSee(zjXmCqjxProjectOverallEvaluationAssistant.getLeaderSee());
           // 总体得分
           dbzjXmCqjxProjectOverallEvaluationAssistant.setQuarterScore(zjXmCqjxProjectOverallEvaluationAssistant.getQuarterScore());
           // 共通
           dbzjXmCqjxProjectOverallEvaluationAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectOverallEvaluationAssistantMapper.updateByPrimaryKey(dbzjXmCqjxProjectOverallEvaluationAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectOverallEvaluationAssistant);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant(List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxProjectOverallEvaluationAssistantList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectOverallEvaluationAssistantList != null && zjXmCqjxProjectOverallEvaluationAssistantList.size() > 0) {
           ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
           zjXmCqjxProjectOverallEvaluationAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectOverallEvaluationAssistantMapper.batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant(zjXmCqjxProjectOverallEvaluationAssistantList, zjXmCqjxProjectOverallEvaluationAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectOverallEvaluationAssistantList);
        }
    }

	@Override
	public ResponseEntity selectZjXmCqjxOverallEvaluationAssistantTodoCount(String token) {
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		}
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxProjectSuperviseApproval approval = new ZjXmCqjxProjectSuperviseApproval();
    		approval.setCreateUser(userKey);
    		int num = zjXmCqjxProjectSuperviseApprovalMapper.selectProjectSuperviseLeaderTodoCount(approval);
    		ZjXmCqjxProjectOverallEvaluationAssistant assistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
    		assistant.setCreateUser(userKey);
    		int evaNum = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectProjectEvaluationLeaderTodoCount(assistant);
		return repEntity.ok(num+evaNum);
	}
	/**
	 * 
	 * 项目绩效总体评价考核已办
	 */
	@Override
	public ResponseEntity getZjXmCqjxProjectEvaluationAssistantDoneList(ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxProjectOverallEvaluationAssistant.setCreateUser(userKey);
//        zjXmCqjxProjectOverallEvaluationAssistant.setLeaderSee("1");
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectOverallEvaluationAssistant.getPage(),zjXmCqjxProjectOverallEvaluationAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxProjectOverallEvaluationAssistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectProjectEvaluationLeaderDoneListByUserKey(zjXmCqjxProjectOverallEvaluationAssistant);
        for(ZjXmCqjxProjectOverallEvaluationAssistant assistant : zjXmCqjxProjectOverallEvaluationAssistantList) {
        	if(StrUtil.equals(assistant.getAssessmentType(), "1")) {
        		switch (assistant.getAssessmentState()) {
				case "0":
					assistant.setItemText("对项目各项工作开展的指导情况");	
					assistant.setItemScore("8");
					break;
				case "1":
					assistant.setItemText("与其他班子成员配合情况");
					assistant.setItemScore("8");
					break;
				case "2":
					assistant.setItemText("对公司部署工作的完成情况");
					assistant.setItemScore("7");					
					break;
				case "3":
					assistant.setItemText("对公司部署工作的完成情况");
					assistant.setItemScore("7");					
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "2") && StrUtil.equals(assistant.getFlowType(), "1")) {
        		switch (assistant.getAssessmentState()) {
				case "0":
					assistant.setItemText("周边业绩");	
					assistant.setItemScore("7");						
					break;
				case "1":
					assistant.setItemText("对本部门管理情况");	
					assistant.setItemScore("7");						
					break;
				case "2":
					assistant.setItemText("项目分管领导对其工作开展情况的评价");
					assistant.setItemScore("6");						
					break;
				case "3":
					assistant.setItemText("项目主要领导对其工作开展情况的评价");
					assistant.setItemScore("5");						
					break;
				}
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "2") && StrUtil.equals(assistant.getFlowType(), "2")) {
        		assistant.setItemText("督办工作开展情况");
        		assistant.setItemScore("5");
        	}else if(StrUtil.equals(assistant.getAssessmentType(), "3")) {
           		switch (assistant.getAssessmentState()) {
    				case "0":
    					assistant.setItemText("工作态度");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "1":
    					assistant.setItemText("工作协作性");	
    	        		assistant.setItemScore("8");    					
    					break;
    				case "2":
    					assistant.setItemText("工作完成质量");	
    	        		assistant.setItemScore("7");    					
    					break;
    				case "3":
    					assistant.setItemText("工作完成质量");	
    	        		assistant.setItemScore("7");    					
    					break;
    				}        		
        	}
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectOverallEvaluationAssistant> p = new PageInfo<>(zjXmCqjxProjectOverallEvaluationAssistantList);
        return repEntity.okList(zjXmCqjxProjectOverallEvaluationAssistantList, p.getTotal());
	}

}