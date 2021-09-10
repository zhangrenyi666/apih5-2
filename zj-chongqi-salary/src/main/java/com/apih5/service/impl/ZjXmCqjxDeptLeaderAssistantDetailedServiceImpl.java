package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmCqjxAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDeptLeaderAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.service.ZjXmCqjxDeptLeaderAssistantDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxDeptLeaderAssistantDetailedService")
public class ZjXmCqjxDeptLeaderAssistantDetailedServiceImpl implements ZjXmCqjxDeptLeaderAssistantDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDeptLeaderAssistantDetailedMapper zjXmCqjxDeptLeaderAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxAssistantLeaderApprovalMapper zjXmCqjxAssistantLeaderApprovalMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxDeptLeaderAssistantDetailedListByCondition(ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        if (zjXmCqjxDeptLeaderAssistantDetailed == null) {
            zjXmCqjxDeptLeaderAssistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxDeptLeaderAssistantDetailed.getPage(),zjXmCqjxDeptLeaderAssistantDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxDeptLeaderAssistantDetailed> zjXmCqjxDeptLeaderAssistantDetailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(zjXmCqjxDeptLeaderAssistantDetailed);
        if(zjXmCqjxDeptLeaderAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxDeptLeaderAssistantDetailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
            String lastScore = "";            
            if(StrUtil.equals(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType(), "0")) {
                lastScore = (int)(40-sum)+"";
            }else if(StrUtil.equals(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType(), "1")){
              lastScore = (int)(10-sum)+"";
            }
            zjXmCqjxDeptLeaderAssistantDetailedList.get(0).setLastScore(lastScore);  
        }           
        // 得到分页信息
        PageInfo<ZjXmCqjxDeptLeaderAssistantDetailed> p = new PageInfo<>(zjXmCqjxDeptLeaderAssistantDetailedList);

        return repEntity.okList(zjXmCqjxDeptLeaderAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDeptLeaderAssistantDetailedDetails(ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        if (zjXmCqjxDeptLeaderAssistantDetailed == null) {
            zjXmCqjxDeptLeaderAssistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
        }
        // 获取数据
        ZjXmCqjxDeptLeaderAssistantDetailed dbZjXmCqjxDeptLeaderAssistantDetailed = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxDeptLeaderAssistantDetailed.getDeptLeaderId());
        // 数据存在
        if (dbZjXmCqjxDeptLeaderAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxDeptLeaderAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDeptLeaderAssistantDetailed(ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
//        if(zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore() == 0) {
//        	return repEntity.layerMessage("NO", "考核分数不能为0");
//        }        
        ZjXmCqjxDeptLeaderAssistantDetailed detailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
        detailed.setExecutiveId(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveId());
        detailed.setWorkType(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType());
        List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
        if((sum+zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore()>40.0) && StrUtil.equals(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType(), "0")) {
        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
        }else if((sum+zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore()>10.0) && StrUtil.equals(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType(), "1")){
        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
        }
        zjXmCqjxDeptLeaderAssistantDetailed.setDeptLeaderId(UuidUtil.generate());
        zjXmCqjxDeptLeaderAssistantDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDeptLeaderAssistantDetailedMapper.insert(zjXmCqjxDeptLeaderAssistantDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDeptLeaderAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDeptLeaderAssistantDetailed(ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");           
        int flag = 0; 
        ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
        approval.setLeaderId(userKey);
        approval.setExecutiveId(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveId());
        List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//        if(approvalList.size()>0) {        	
//        	if(!StrUtil.equals(approvalList.get(0).getApprovalFlag(), "1") && !StrUtil.equals(approvalList.get(0).getApprovalFlag(), "4")) {
//        		return repEntity.layerMessage("NO", "已发起，无法修改");
//        	}
//        }
        ZjXmCqjxDeptLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveId());
        assistantDetailed.setWorkType(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType());        
        List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxDeptLeaderAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getDeptLeaderId(), zjXmCqjxDeptLeaderAssistantDetailed.getDeptLeaderId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxDeptLeaderAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "该工作计划内容重复，请修改！");
        	}        	
        	if(StrUtil.equals(detailed.getDeptLeaderId(), zjXmCqjxDeptLeaderAssistantDetailed.getDeptLeaderId())) {
        		detailed.setAssessmentScore(zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore());
        	}
        }
//        double sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
//        if((sum>40.0) && StrUtil.equals(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType(), "0")) {
//        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
//        }else if((sum>10.0) && StrUtil.equals(zjXmCqjxDeptLeaderAssistantDetailed.getWorkType(), "1")){
//        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
//        }
        ZjXmCqjxExecutiveAssistant assistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveId());
        //如果两位领导评分完成，则计算该项的分数
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxDeptLeaderAssistantDetailed.getChargeLeaderScore()>zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}else if(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}
        }
        if(StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	BigDecimal changeLeaderScore = new BigDecimal(zjXmCqjxDeptLeaderAssistantDetailed.getChargeLeaderScore());
        	BigDecimal executiveLeaderScore = new BigDecimal(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveLeaderScore());
        	changeLeaderScore = changeLeaderScore.multiply(chargeLeaderWeight);
        	executiveLeaderScore = executiveLeaderScore.multiply(executiveLeaderWeight);
        	BigDecimal executiveScore = changeLeaderScore.add(executiveLeaderScore);        	
        	zjXmCqjxDeptLeaderAssistantDetailed.setExecutiveScore(executiveScore.doubleValue());
        }        
        ZjXmCqjxDeptLeaderAssistantDetailed dbzjXmCqjxDeptLeaderAssistantDetailed = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxDeptLeaderAssistantDetailed.getDeptLeaderId());
        if (dbzjXmCqjxDeptLeaderAssistantDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxDeptLeaderAssistantDetailed.getDeptLeaderId())) {
        	if(zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore() != dbzjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore()) {
                if(zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore() == 0) {
                	return repEntity.layerMessage("NO", "考核分数不能为0");
                }else if(zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "考核分数不能为负分");
                }
        	}   
        	if(zjXmCqjxDeptLeaderAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }else if(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }   
           // 考核计划ID
           dbzjXmCqjxDeptLeaderAssistantDetailed.setManagerId(zjXmCqjxDeptLeaderAssistantDetailed.getManagerId());
           // 副总师、经理助理考核ID
           dbzjXmCqjxDeptLeaderAssistantDetailed.setExecutiveId(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveId());
           // 工作计划
           dbzjXmCqjxDeptLeaderAssistantDetailed.setWorkPlan(zjXmCqjxDeptLeaderAssistantDetailed.getWorkPlan());
           // 目标
           dbzjXmCqjxDeptLeaderAssistantDetailed.setWorkTarget(zjXmCqjxDeptLeaderAssistantDetailed.getWorkTarget());
           // 考核分
           dbzjXmCqjxDeptLeaderAssistantDetailed.setAssessmentScore(zjXmCqjxDeptLeaderAssistantDetailed.getAssessmentScore());
           // 完成情况
           dbzjXmCqjxDeptLeaderAssistantDetailed.setCompletion(zjXmCqjxDeptLeaderAssistantDetailed.getCompletion());
           // 分管领导评分
           dbzjXmCqjxDeptLeaderAssistantDetailed.setChargeLeaderScore(zjXmCqjxDeptLeaderAssistantDetailed.getChargeLeaderScore());
           // 主管领导评分
           dbzjXmCqjxDeptLeaderAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveLeaderScore());
           // 得分
           dbzjXmCqjxDeptLeaderAssistantDetailed.setExecutiveScore(zjXmCqjxDeptLeaderAssistantDetailed.getExecutiveScore());
           // 共通
           dbzjXmCqjxDeptLeaderAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDeptLeaderAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxDeptLeaderAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDeptLeaderAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed(List<ZjXmCqjxDeptLeaderAssistantDetailed> zjXmCqjxDeptLeaderAssistantDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDeptLeaderAssistantDetailedList != null && zjXmCqjxDeptLeaderAssistantDetailedList.size() > 0) {
           ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
           zjXmCqjxDeptLeaderAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDeptLeaderAssistantDetailedMapper.batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed(zjXmCqjxDeptLeaderAssistantDetailedList, zjXmCqjxDeptLeaderAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDeptLeaderAssistantDetailedList);
        }
    }
}
