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
import com.apih5.mybatis.dao.ZjXmCqjxProjectStaffAssistantDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSecretaryrAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectStaffAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;
import com.apih5.service.ZjXmCqjxProjectStaffAssistantDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectStaffAssistantDetailedService")
public class ZjXmCqjxProjectStaffAssistantDetailedServiceImpl implements ZjXmCqjxProjectStaffAssistantDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectStaffAssistantDetailedMapper zjXmCqjxProjectStaffAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxProjectStaffAssistantDetailedListByCondition(ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        if (zjXmCqjxProjectStaffAssistantDetailed == null) {
            zjXmCqjxProjectStaffAssistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectStaffAssistantDetailed.getPage(),zjXmCqjxProjectStaffAssistantDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectStaffAssistantDetailed> zjXmCqjxProjectStaffAssistantDetailedList = zjXmCqjxProjectStaffAssistantDetailedMapper.selectByZjXmCqjxProjectStaffAssistantDetailedList(zjXmCqjxProjectStaffAssistantDetailed);
        if(zjXmCqjxProjectStaffAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxProjectStaffAssistantDetailedList.stream().mapToDouble(ZjXmCqjxProjectStaffAssistantDetailed::getAssessmentScore).sum();
            String lastScore = ((int)(60-sum)+"");
            zjXmCqjxProjectStaffAssistantDetailedList.get(0).setLastScore(lastScore);  
        }         
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectStaffAssistantDetailed> p = new PageInfo<>(zjXmCqjxProjectStaffAssistantDetailedList);

        return repEntity.okList(zjXmCqjxProjectStaffAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectStaffAssistantDetailedDetails(ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        if (zjXmCqjxProjectStaffAssistantDetailed == null) {
            zjXmCqjxProjectStaffAssistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
        }
        // 获取数据
        ZjXmCqjxProjectStaffAssistantDetailed dbZjXmCqjxProjectStaffAssistantDetailed = zjXmCqjxProjectStaffAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectStaffAssistantDetailed.getStaffId());
        // 数据存在
        if (dbZjXmCqjxProjectStaffAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxProjectStaffAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectStaffAssistantDetailed(ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectStaffAssistantDetailed.setStaffId(UuidUtil.generate());
        zjXmCqjxProjectStaffAssistantDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectStaffAssistantDetailedMapper.insert(zjXmCqjxProjectStaffAssistantDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectStaffAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectStaffAssistantDetailed(ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");                     
        int flag = 0;
        ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
        approval.setLeaderId(userKey);
        approval.setExecutiveId(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveId());
        //重复新增数值判断，有问题，目前注释中
//        List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//        if(approvalList.size()>0) {        	
//        	if(!StrUtil.equals(approvalList.get(0).getApprovalFlag(), "1") && !StrUtil.equals(approvalList.get(0).getApprovalFlag(), "4")) {
//        		return repEntity.layerMessage("NO", "已发起，无法修改");
//        	}
//        }
        ZjXmCqjxProjectStaffAssistantDetailed assistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveId());
        assistantDetailed.setWorkType(zjXmCqjxProjectStaffAssistantDetailed.getWorkType());
        List<ZjXmCqjxProjectStaffAssistantDetailed> detailedList = zjXmCqjxProjectStaffAssistantDetailedMapper.selectByZjXmCqjxProjectStaffAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxProjectStaffAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getStaffId(), zjXmCqjxProjectStaffAssistantDetailed.getStaffId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxProjectStaffAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "该工作计划内容重复，请修改！");
        	}            	
        	if(StrUtil.equals(detailed.getStaffId(), zjXmCqjxProjectStaffAssistantDetailed.getStaffId())) {
        		detailed.setAssessmentScore(zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore());
        	}
        }
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectStaffAssistantDetailed::getAssessmentScore).sum();
        if((sum>60.0)) {
        	zjXmCqjxProjectStaffAssistantDetailed.setLastScore((60-sum)+"");
        }else {
        	zjXmCqjxProjectStaffAssistantDetailed.setLastScore((60-sum)+"");
        }           
//        if((sum>25.0)) {
//        	return repEntity.layerMessage("NO", "考核分数已经超值，请修改！");
//        }  
        ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveId());
        //如果两位领导评分完成，则计算该项的分数
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxProjectStaffAssistantDetailed.getChargeLeaderScore()>zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}else if(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "评分已经超过考核分，请修改！");
        	}
        }       
        ZjXmCqjxProjectStaffAssistantDetailed dbzjXmCqjxProjectStaffAssistantDetailed = zjXmCqjxProjectStaffAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectStaffAssistantDetailed.getStaffId());
        if (dbzjXmCqjxProjectStaffAssistantDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectStaffAssistantDetailed.getStaffId())) {
        	if(zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore() != dbzjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore()) {
                if(zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore() == 0) {
                	return repEntity.layerMessage("NO", "考核分数不能为0");
                }else if(zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "考核分数不能为负分");
                }
        	}    
        	if(zjXmCqjxProjectStaffAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }else if(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "分数不能为负分");
            }        	
           // 考核计划ID
           dbzjXmCqjxProjectStaffAssistantDetailed.setManagerId(zjXmCqjxProjectStaffAssistantDetailed.getManagerId());
           // 副总师、经理助理考核ID
           dbzjXmCqjxProjectStaffAssistantDetailed.setExecutiveId(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveId());
           // 工作类型
           dbzjXmCqjxProjectStaffAssistantDetailed.setWorkPlan(zjXmCqjxProjectStaffAssistantDetailed.getWorkPlan());
           // 工作计划
           dbzjXmCqjxProjectStaffAssistantDetailed.setWorkType(zjXmCqjxProjectStaffAssistantDetailed.getWorkType());
           // 目标
           dbzjXmCqjxProjectStaffAssistantDetailed.setWorkTarget(zjXmCqjxProjectStaffAssistantDetailed.getWorkTarget());
           // 考核分
           dbzjXmCqjxProjectStaffAssistantDetailed.setAssessmentScore(zjXmCqjxProjectStaffAssistantDetailed.getAssessmentScore());
           // 完成情况
           dbzjXmCqjxProjectStaffAssistantDetailed.setCompletion(zjXmCqjxProjectStaffAssistantDetailed.getCompletion());
           // 分管领导评分
           dbzjXmCqjxProjectStaffAssistantDetailed.setChargeLeaderScore(zjXmCqjxProjectStaffAssistantDetailed.getChargeLeaderScore());
           // 主管领导评分
           dbzjXmCqjxProjectStaffAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveLeaderScore());
           // 得分
           dbzjXmCqjxProjectStaffAssistantDetailed.setExecutiveScore(zjXmCqjxProjectStaffAssistantDetailed.getExecutiveScore());
           // 共通
           dbzjXmCqjxProjectStaffAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectStaffAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxProjectStaffAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectStaffAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed(List<ZjXmCqjxProjectStaffAssistantDetailed> zjXmCqjxProjectStaffAssistantDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectStaffAssistantDetailedList != null && zjXmCqjxProjectStaffAssistantDetailedList.size() > 0) {
           ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed = new ZjXmCqjxProjectStaffAssistantDetailed();
           zjXmCqjxProjectStaffAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectStaffAssistantDetailedMapper.batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed(zjXmCqjxProjectStaffAssistantDetailedList, zjXmCqjxProjectStaffAssistantDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectStaffAssistantDetailedList);
        }
    }
}