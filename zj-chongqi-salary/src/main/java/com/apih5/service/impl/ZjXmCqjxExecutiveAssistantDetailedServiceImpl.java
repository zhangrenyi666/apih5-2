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
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectStaffAssistantDetailed;
import com.apih5.service.ZjXmCqjxExecutiveAssistantDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxExecutiveAssistantDetailedService")
public class ZjXmCqjxExecutiveAssistantDetailedServiceImpl implements ZjXmCqjxExecutiveAssistantDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantDetailedMapper zjXmCqjxExecutiveAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxAssistantLeaderApprovalMapper zjXmCqjxAssistantLeaderApprovalMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetailedListByCondition(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        if (zjXmCqjxExecutiveAssistantDetailed == null) {
            zjXmCqjxExecutiveAssistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxExecutiveAssistantDetailed.getPage(),zjXmCqjxExecutiveAssistantDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxExecutiveAssistantDetailed> zjXmCqjxExecutiveAssistantDetailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(zjXmCqjxExecutiveAssistantDetailed);
        if(zjXmCqjxExecutiveAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxExecutiveAssistantDetailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();
            String lastScore = ((int)(50-sum)+"");
            zjXmCqjxExecutiveAssistantDetailedList.get(0).setLastScore(lastScore);  
        }               
        // 得到分页信息
        PageInfo<ZjXmCqjxExecutiveAssistantDetailed> p = new PageInfo<>(zjXmCqjxExecutiveAssistantDetailedList);

        return repEntity.okList(zjXmCqjxExecutiveAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetailedDetails(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        if (zjXmCqjxExecutiveAssistantDetailed == null) {
            zjXmCqjxExecutiveAssistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
        }
        // 获取数据
        ZjXmCqjxExecutiveAssistantDetailed dbZjXmCqjxExecutiveAssistantDetailed = zjXmCqjxExecutiveAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistantDetailed.getExecutiveDetailId());
        // 数据存在
        if (dbZjXmCqjxExecutiveAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxExecutiveAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxExecutiveAssistantDetailed(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
//        if(zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore() == 0) {
//        	return repEntity.layerMessage("NO", "考核分数不能为0");
//        }
        ZjXmCqjxExecutiveAssistantDetailed detailed = new ZjXmCqjxExecutiveAssistantDetailed();
        detailed.setExecutiveId(zjXmCqjxExecutiveAssistantDetailed.getExecutiveId());
        detailed.setWorkPlan("");
//        detailed.setWorkTarget("");
        List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(detailed);
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();
        if((sum+zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore()>50.0)) {
        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
        }
        zjXmCqjxExecutiveAssistantDetailed.setExecutiveDetailId(UuidUtil.generate());
        zjXmCqjxExecutiveAssistantDetailed.setWorkTarget("");
        zjXmCqjxExecutiveAssistantDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxExecutiveAssistantDetailedMapper.insert(zjXmCqjxExecutiveAssistantDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxExecutiveAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxExecutiveAssistantDetailed(ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");        
        int flag = 0;            
        ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
        approval.setLeaderId(userKey);
        approval.setExecutiveId(zjXmCqjxExecutiveAssistantDetailed.getExecutiveId());
        List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//        if(approvalList.size()>0) {        	
//        	if(!StrUtil.equals(approvalList.get(0).getApprovalFlag(), "1") && !StrUtil.equals(approvalList.get(0).getApprovalFlag(), "4")) {
//        		return repEntity.layerMessage("NO", "已发起，无法修改");
//        	}
//        }
        ZjXmCqjxExecutiveAssistantDetailed assistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistantDetailed.getExecutiveId());
        List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxExecutiveAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getExecutiveDetailId(), zjXmCqjxExecutiveAssistantDetailed.getExecutiveDetailId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxExecutiveAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "该工作计划内容重复，请修改！");
        	}
        	if(StrUtil.equals(detailed.getExecutiveDetailId(), zjXmCqjxExecutiveAssistantDetailed.getExecutiveDetailId())) {
        		detailed.setAssessmentScore(zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore());
        	}
        }
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();
        zjXmCqjxExecutiveAssistantDetailed.setLastScore((50-sum)+"");
//        if((sum>50.0)) {
//        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
//        }  
        ZjXmCqjxExecutiveAssistant assistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistantDetailed.getExecutiveId());
        //如果两位领导评分完成，则计算该项的分数
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxExecutiveAssistantDetailed.getChargeLeaderScore()>zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}else if(zjXmCqjxExecutiveAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}
        }
        if(StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	BigDecimal changeLeaderScore = new BigDecimal(zjXmCqjxExecutiveAssistantDetailed.getChargeLeaderScore());
        	BigDecimal executiveLeaderScore = new BigDecimal(zjXmCqjxExecutiveAssistantDetailed.getExecutiveLeaderScore());
        	changeLeaderScore = changeLeaderScore.multiply(chargeLeaderWeight);
        	executiveLeaderScore = executiveLeaderScore.multiply(executiveLeaderWeight);
        	BigDecimal executiveScore = changeLeaderScore.add(executiveLeaderScore);
        	zjXmCqjxExecutiveAssistantDetailed.setExecutiveScore(executiveScore.doubleValue());
        }
        ZjXmCqjxExecutiveAssistantDetailed dbzjXmCqjxExecutiveAssistantDetailed = zjXmCqjxExecutiveAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistantDetailed.getExecutiveDetailId());
        if (dbzjXmCqjxExecutiveAssistantDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistantDetailed.getExecutiveDetailId())) {
        	if(zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore() != dbzjXmCqjxExecutiveAssistantDetailed.getAssessmentScore()) {
                if(zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore() == 0) {
                	return repEntity.layerMessage("NO", "考核分数不能为0");
                }else if(zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "考核分数不能为负分");
                }
        	}
        	if(zjXmCqjxExecutiveAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }else if(zjXmCqjxExecutiveAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }   
           dbzjXmCqjxExecutiveAssistantDetailed.setWorkPlan(zjXmCqjxExecutiveAssistantDetailed.getWorkPlan());
           
           dbzjXmCqjxExecutiveAssistantDetailed.setWorkTarget(zjXmCqjxExecutiveAssistantDetailed.getWorkTarget());
           // 考核分
           dbzjXmCqjxExecutiveAssistantDetailed.setAssessmentScore(zjXmCqjxExecutiveAssistantDetailed.getAssessmentScore());
           // 完成情况
           dbzjXmCqjxExecutiveAssistantDetailed.setCompletion(zjXmCqjxExecutiveAssistantDetailed.getCompletion());
           // 分管领导评分
           dbzjXmCqjxExecutiveAssistantDetailed.setChargeLeaderScore(zjXmCqjxExecutiveAssistantDetailed.getChargeLeaderScore());
           // 主管领导评分
           dbzjXmCqjxExecutiveAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxExecutiveAssistantDetailed.getExecutiveLeaderScore());
           // 得分
           dbzjXmCqjxExecutiveAssistantDetailed.setExecutiveScore(zjXmCqjxExecutiveAssistantDetailed.getExecutiveScore());
           // 共通
           dbzjXmCqjxExecutiveAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed(List<ZjXmCqjxExecutiveAssistantDetailed> zjXmCqjxExecutiveAssistantDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxExecutiveAssistantDetailedList != null && zjXmCqjxExecutiveAssistantDetailedList.size() > 0) {
           ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
           zjXmCqjxExecutiveAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantDetailedMapper.batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed(zjXmCqjxExecutiveAssistantDetailedList, zjXmCqjxExecutiveAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxExecutiveAssistantDetailedList);
        }
    }
}
