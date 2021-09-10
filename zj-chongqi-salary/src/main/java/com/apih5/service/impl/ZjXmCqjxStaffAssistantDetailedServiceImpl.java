package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxOaDeptMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxStaffAssistantDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;
import com.apih5.service.ZjXmCqjxStaffAssistantDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxStaffAssistantDetailedService")
public class ZjXmCqjxStaffAssistantDetailedServiceImpl implements ZjXmCqjxStaffAssistantDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxStaffAssistantDetailedMapper zjXmCqjxStaffAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxOaDeptMemberMapper zjXmCqjxOaDeptMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

    @Autowired(required = true)
    private ZjXmCqjxAssistantLeaderApprovalMapper zjXmCqjxAssistantLeaderApprovalMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxStaffAssistantDetailedListByCondition(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        if (zjXmCqjxStaffAssistantDetailed == null) {
            zjXmCqjxStaffAssistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxStaffAssistantDetailed.getPage(),zjXmCqjxStaffAssistantDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxStaffAssistantDetailed> zjXmCqjxStaffAssistantDetailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(zjXmCqjxStaffAssistantDetailed);
        if(zjXmCqjxStaffAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxStaffAssistantDetailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
            String lastScore = "";            
            if(StrUtil.equals(zjXmCqjxStaffAssistantDetailed.getWorkType(), "0")) {
                lastScore = (int)(25-sum)+"";
            }else if(StrUtil.equals(zjXmCqjxStaffAssistantDetailed.getWorkType(), "1")){
              lastScore = (int)(25-sum)+"";
            }
            zjXmCqjxStaffAssistantDetailedList.get(0).setLastScore(lastScore);  
        }          
        // 得到分页信息
        PageInfo<ZjXmCqjxStaffAssistantDetailed> p = new PageInfo<>(zjXmCqjxStaffAssistantDetailedList);

        return repEntity.okList(zjXmCqjxStaffAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxStaffAssistantDetailedDetails(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        if (zjXmCqjxStaffAssistantDetailed == null) {
            zjXmCqjxStaffAssistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
        }
        // 获取数据
        ZjXmCqjxStaffAssistantDetailed dbZjXmCqjxStaffAssistantDetailed = zjXmCqjxStaffAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxStaffAssistantDetailed.getStaffId());
        // 数据存在
        if (dbZjXmCqjxStaffAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxStaffAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxStaffAssistantDetailed(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
//        if(zjXmCqjxStaffAssistantDetailed.getAssessmentScore() == 0) {
//        	return repEntity.layerMessage("NO", "考核分数不能为0");
//        }         
        ZjXmCqjxStaffAssistantDetailed detailed = new ZjXmCqjxStaffAssistantDetailed();
        detailed.setExecutiveId(zjXmCqjxStaffAssistantDetailed.getExecutiveId());
        detailed.setWorkType(zjXmCqjxStaffAssistantDetailed.getWorkType());
        List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
        if((sum+zjXmCqjxStaffAssistantDetailed.getAssessmentScore()>25.0)) {
        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
        }        
        zjXmCqjxStaffAssistantDetailed.setStaffId(UuidUtil.generate());
        zjXmCqjxStaffAssistantDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxStaffAssistantDetailedMapper.insert(zjXmCqjxStaffAssistantDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxStaffAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxStaffAssistantDetailed(ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");                     
        int flag = 0;
        ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
        approval.setLeaderId(userKey);
        approval.setExecutiveId(zjXmCqjxStaffAssistantDetailed.getExecutiveId());
        List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//        if(approvalList.size()>0) {        	
//        	if(!StrUtil.equals(approvalList.get(0).getApprovalFlag(), "1") && !StrUtil.equals(approvalList.get(0).getApprovalFlag(), "4")) {
//        		return repEntity.layerMessage("NO", "已发起，无法修改");
//        	}
//        }
        ZjXmCqjxStaffAssistantDetailed assistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxStaffAssistantDetailed.getExecutiveId());
        assistantDetailed.setWorkType(zjXmCqjxStaffAssistantDetailed.getWorkType());
        List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxStaffAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getStaffId(), zjXmCqjxStaffAssistantDetailed.getStaffId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxStaffAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "该工作计划内容重复，请修改！");
        	}            	
        	if(StrUtil.equals(detailed.getStaffId(), zjXmCqjxStaffAssistantDetailed.getStaffId())) {
        		detailed.setAssessmentScore(zjXmCqjxStaffAssistantDetailed.getAssessmentScore());
        	}
        }
//        double sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
//        if((sum>25.0)) {
//        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
//        }  
        ZjXmCqjxExecutiveAssistant assistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxStaffAssistantDetailed.getExecutiveId());
        //如果两位领导评分完成，则计算该项的分数
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxStaffAssistantDetailed.getChargeLeaderScore()>zjXmCqjxStaffAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}else if(zjXmCqjxStaffAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxStaffAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}
        }
//        if(StrUtil.equals(assistant.getAssessmentState(), "7")) {
//        	BigDecimal changeLeaderScore = new BigDecimal(zjXmCqjxStaffAssistantDetailed.getChargeLeaderScore());
//        	BigDecimal executiveLeaderScore = new BigDecimal(zjXmCqjxStaffAssistantDetailed.getExecutiveLeaderScore());
//        	changeLeaderScore = changeLeaderScore.multiply(chargeLeaderWeight);
//        	executiveLeaderScore = executiveLeaderScore.multiply(executiveLeaderWeight);
//        	BigDecimal executiveScore = changeLeaderScore.add(executiveLeaderScore);        	
//        	zjXmCqjxStaffAssistantDetailed.setExecutiveScore(executiveScore.doubleValue());            	
//        }        
        ZjXmCqjxStaffAssistantDetailed dbzjXmCqjxStaffAssistantDetailed = zjXmCqjxStaffAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxStaffAssistantDetailed.getStaffId());
        if (dbzjXmCqjxStaffAssistantDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxStaffAssistantDetailed.getStaffId())) {
        	if(zjXmCqjxStaffAssistantDetailed.getAssessmentScore() != dbzjXmCqjxStaffAssistantDetailed.getAssessmentScore()) {
                if(zjXmCqjxStaffAssistantDetailed.getAssessmentScore() == 0) {
                	return repEntity.layerMessage("NO", "考核分数不能为0");
                }else if(zjXmCqjxStaffAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "考核分数不能为负分");
                }
        	}    
        	if(zjXmCqjxStaffAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }else if(zjXmCqjxStaffAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }        	
           // 考核计划ID
           dbzjXmCqjxStaffAssistantDetailed.setManagerId(zjXmCqjxStaffAssistantDetailed.getManagerId());
           // 副总师、经理助理考核ID
           dbzjXmCqjxStaffAssistantDetailed.setExecutiveId(zjXmCqjxStaffAssistantDetailed.getExecutiveId());
           // 工作计划
           dbzjXmCqjxStaffAssistantDetailed.setWorkPlan(zjXmCqjxStaffAssistantDetailed.getWorkPlan());
           // 目标
           dbzjXmCqjxStaffAssistantDetailed.setWorkTarget(zjXmCqjxStaffAssistantDetailed.getWorkTarget());
           // 考核分
           dbzjXmCqjxStaffAssistantDetailed.setAssessmentScore(zjXmCqjxStaffAssistantDetailed.getAssessmentScore());
           // 完成情况
           dbzjXmCqjxStaffAssistantDetailed.setCompletion(zjXmCqjxStaffAssistantDetailed.getCompletion());
           // 分管领导评分
           dbzjXmCqjxStaffAssistantDetailed.setChargeLeaderScore(zjXmCqjxStaffAssistantDetailed.getChargeLeaderScore());
           // 主管领导评分
           dbzjXmCqjxStaffAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxStaffAssistantDetailed.getExecutiveLeaderScore());
           // 得分
           dbzjXmCqjxStaffAssistantDetailed.setExecutiveScore(zjXmCqjxStaffAssistantDetailed.getExecutiveScore());
           // 共通
           dbzjXmCqjxStaffAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxStaffAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxStaffAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxStaffAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxStaffAssistantDetailed(List<ZjXmCqjxStaffAssistantDetailed> zjXmCqjxStaffAssistantDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxStaffAssistantDetailedList != null && zjXmCqjxStaffAssistantDetailedList.size() > 0) {
           ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
           zjXmCqjxStaffAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxStaffAssistantDetailedMapper.batchDeleteUpdateZjXmCqjxStaffAssistantDetailed(zjXmCqjxStaffAssistantDetailedList, zjXmCqjxStaffAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxStaffAssistantDetailedList);
        }
    }
}
