package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSecretaryrAssistantDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDeputyLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSecretaryrAssistantDetailed;
import com.apih5.service.ZjXmCqjxProjectSecretaryrAssistantDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectSecretaryrAssistantDetailedService")
public class ZjXmCqjxProjectSecretaryrAssistantDetailedServiceImpl implements ZjXmCqjxProjectSecretaryrAssistantDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectSecretaryrAssistantDetailedMapper zjXmCqjxProjectSecretaryrAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxProjectSecretaryrAssistantDetailedListByCondition(ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        if (zjXmCqjxProjectSecretaryrAssistantDetailed == null) {
            zjXmCqjxProjectSecretaryrAssistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectSecretaryrAssistantDetailed.getPage(),zjXmCqjxProjectSecretaryrAssistantDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectSecretaryrAssistantDetailed> zjXmCqjxProjectSecretaryrAssistantDetailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(zjXmCqjxProjectSecretaryrAssistantDetailed);
        if(zjXmCqjxProjectSecretaryrAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxProjectSecretaryrAssistantDetailedList.stream().mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
            String lastScore = ((int)(60-sum)+"");
            zjXmCqjxProjectSecretaryrAssistantDetailedList.get(0).setLastScore(lastScore);  
        }         
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectSecretaryrAssistantDetailed> p = new PageInfo<>(zjXmCqjxProjectSecretaryrAssistantDetailedList);

        return repEntity.okList(zjXmCqjxProjectSecretaryrAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectSecretaryrAssistantDetailedDetails(ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        if (zjXmCqjxProjectSecretaryrAssistantDetailed == null) {
            zjXmCqjxProjectSecretaryrAssistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
        }
        // 获取数据
        ZjXmCqjxProjectSecretaryrAssistantDetailed dbZjXmCqjxProjectSecretaryrAssistantDetailed = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId());
        // 数据存在
        if (dbZjXmCqjxProjectSecretaryrAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxProjectSecretaryrAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectSecretaryrAssistantDetailed(ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectSecretaryrAssistantDetailed.setDeptLeaderId(UuidUtil.generate());
        zjXmCqjxProjectSecretaryrAssistantDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.insert(zjXmCqjxProjectSecretaryrAssistantDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectSecretaryrAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectSecretaryrAssistantDetailed(ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");           
        int flag = 0; 
        ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
        approval.setLeaderId(userKey);
        approval.setExecutiveId(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
//        List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//        if(approvalList.size()>0) {        	
//        	if(!StrUtil.equals(approvalList.get(0).getApprovalFlag(), "1") && !StrUtil.equals(approvalList.get(0).getApprovalFlag(), "4")) {
//        		return repEntity.layerMessage("NO", "已发起，无法修改");
//        	}
//        }
        ZjXmCqjxProjectSecretaryrAssistantDetailed assistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
        assistantDetailed.setWorkType(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkType());        
        List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxProjectSecretaryrAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getDeptLeaderId(), zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "该工作计划内容重复，请修改！");
        	}        	
        	if(StrUtil.equals(detailed.getDeptLeaderId(), zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId())) {
        		detailed.setAssessmentScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore());
        	}
        }
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
//        if((sum>60.0) && StrUtil.equals(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkType(), "0")) {
//        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
//        }
//        double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
        if((sum>60.0)) {
        	zjXmCqjxProjectSecretaryrAssistantDetailed.setLastScore((60-sum)+"");
        }else {
        	zjXmCqjxProjectSecretaryrAssistantDetailed.setLastScore((60-sum)+"");
        }        
        ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
        //如果两位领导评分完成，则计算该项的分数
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore()>zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}else if(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}
        }
        if(StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	BigDecimal changeLeaderScore = new BigDecimal(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore());
        	BigDecimal executiveLeaderScore = new BigDecimal(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore());
        	changeLeaderScore = changeLeaderScore.multiply(chargeLeaderWeight);
        	executiveLeaderScore = executiveLeaderScore.multiply(executiveLeaderWeight);
        	BigDecimal executiveScore = changeLeaderScore.add(executiveLeaderScore);        	
        	zjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveScore(executiveScore.doubleValue());
        }        
        ZjXmCqjxProjectSecretaryrAssistantDetailed dbzjXmCqjxProjectSecretaryrAssistantDetailed = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId());
        if (dbzjXmCqjxProjectSecretaryrAssistantDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId())) {
        	if(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore() != dbzjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()) {
                if(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore() == 0) {
                	return repEntity.layerMessage("NO", "考核分数不能为0");
                }else if(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "考核分数不能为负分");
                }
        	}   
        	if(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }else if(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }   
           // 考核计划ID
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setManagerId(zjXmCqjxProjectSecretaryrAssistantDetailed.getManagerId());
           // 副总师、经理助理考核ID
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveId(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
           // 工作类型
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setWorkType(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkType());
           // 工作计划
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setWorkPlan(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkPlan());
           // 目标
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setWorkTarget(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkTarget());
           // 考核分
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setAssessmentScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore());
           // 完成情况
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setCompletion(zjXmCqjxProjectSecretaryrAssistantDetailed.getCompletion());
           // 分管领导评分
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setChargeLeaderScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore());
           // 主管领导评分
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore());
           // 得分
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveScore());
           // 共通
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxProjectSecretaryrAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectSecretaryrAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed(List<ZjXmCqjxProjectSecretaryrAssistantDetailed> zjXmCqjxProjectSecretaryrAssistantDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectSecretaryrAssistantDetailedList != null && zjXmCqjxProjectSecretaryrAssistantDetailedList.size() > 0) {
           ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
           zjXmCqjxProjectSecretaryrAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed(zjXmCqjxProjectSecretaryrAssistantDetailedList, zjXmCqjxProjectSecretaryrAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectSecretaryrAssistantDetailedList);
        }
    }
}