package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDeputyLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.service.ZjXmCqjxProjectDeputyLeaderAssistantDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectDeputyLeaderAssistantDetailedService")
public class ZjXmCqjxProjectDeputyLeaderAssistantDetailedServiceImpl implements ZjXmCqjxProjectDeputyLeaderAssistantDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxProjectDeputyLeaderAssistantDetailedListByCondition(ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        if (zjXmCqjxProjectDeputyLeaderAssistantDetailed == null) {
            zjXmCqjxProjectDeputyLeaderAssistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
        }
        // ????????????
        PageHelper.startPage(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getPage(),zjXmCqjxProjectDeputyLeaderAssistantDetailed.getLimit());
        // ????????????
        List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> zjXmCqjxProjectDeputyLeaderAssistantDetailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(zjXmCqjxProjectDeputyLeaderAssistantDetailed);
        if(zjXmCqjxProjectDeputyLeaderAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxProjectDeputyLeaderAssistantDetailedList.stream().mapToDouble(ZjXmCqjxProjectDeputyLeaderAssistantDetailed::getAssessmentScore).sum();
            String lastScore = ((int)(60-sum)+"");
            zjXmCqjxProjectDeputyLeaderAssistantDetailedList.get(0).setLastScore(lastScore);  
        }    
        // ??????????????????
        PageInfo<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> p = new PageInfo<>(zjXmCqjxProjectDeputyLeaderAssistantDetailedList);

        return repEntity.okList(zjXmCqjxProjectDeputyLeaderAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDeputyLeaderAssistantDetailedDetails(ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        if (zjXmCqjxProjectDeputyLeaderAssistantDetailed == null) {
            zjXmCqjxProjectDeputyLeaderAssistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
        }
        // ????????????
        ZjXmCqjxProjectDeputyLeaderAssistantDetailed dbZjXmCqjxProjectDeputyLeaderAssistantDetailed = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveDetailId());
        // ????????????
        if (dbZjXmCqjxProjectDeputyLeaderAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxProjectDeputyLeaderAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDeputyLeaderAssistantDetailed(ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectDeputyLeaderAssistantDetailed.setExecutiveDetailId(UuidUtil.generate());
        zjXmCqjxProjectDeputyLeaderAssistantDetailed.setCreateUserInfo(userKey, realName);
        zjXmCqjxProjectDeputyLeaderAssistantDetailed.setWorkTarget("");
        int flag = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.insert(zjXmCqjxProjectDeputyLeaderAssistantDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDeputyLeaderAssistantDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDeputyLeaderAssistantDetailed(ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");        
        int flag = 0;            
        ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
        approval.setLeaderId(userKey);
        approval.setExecutiveId(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveId());      
        //???????????????????????????
//        List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//        if(approvalList.size()>0) {        	
//        	if(!StrUtil.equals(approvalList.get(0).getApprovalFlag(), "1") && !StrUtil.equals(approvalList.get(0).getApprovalFlag(), "4")) {
//        		return repEntity.layerMessage("NO", "????????????????????????");
//        	}
//        }
        ZjXmCqjxProjectDeputyLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveId());
        List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> detailedList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.selectByZjXmCqjxProjectDeputyLeaderAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxProjectDeputyLeaderAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getExecutiveDetailId(), zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveDetailId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxProjectDeputyLeaderAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "??????????????????????????????????????????");
        	}
        	if(StrUtil.equals(detailed.getExecutiveDetailId(), zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveDetailId())) {
        		detailed.setAssessmentScore(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore());
        	}
        }
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectDeputyLeaderAssistantDetailed::getAssessmentScore).sum();
        if((sum>60.0)) {
        	zjXmCqjxProjectDeputyLeaderAssistantDetailed.setLastScore((60-sum)+"");
        }else {
        	zjXmCqjxProjectDeputyLeaderAssistantDetailed.setLastScore((60-sum)+"");
        }
        ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveId());
        //?????????????????????????????????????????????????????????
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getChargeLeaderScore()>zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "??????????????????????????????????????????");
        	}else if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "??????????????????????????????????????????");
        	}
        }
        if(StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	BigDecimal changeLeaderScore = new BigDecimal(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getChargeLeaderScore());
        	BigDecimal executiveLeaderScore = new BigDecimal(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveLeaderScore());
        	changeLeaderScore = changeLeaderScore.multiply(chargeLeaderWeight);
        	executiveLeaderScore = executiveLeaderScore.multiply(executiveLeaderWeight);
        	BigDecimal executiveScore = changeLeaderScore.add(executiveLeaderScore);
        	zjXmCqjxProjectDeputyLeaderAssistantDetailed.setExecutiveScore(executiveScore.doubleValue());
        }
        ZjXmCqjxProjectDeputyLeaderAssistantDetailed dbzjXmCqjxProjectDeputyLeaderAssistantDetailed = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveDetailId());
        if (dbzjXmCqjxProjectDeputyLeaderAssistantDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveDetailId())) {
        	if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore() != dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore()) {
                if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore() == 0) {
                	return repEntity.layerMessage("NO", "?????????????????????0");
                }else if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "???????????????????????????");
                }
        	}
        	if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "?????????????????????");
            }else if(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "?????????????????????");
            }   
            // ?????????????????????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setWorkPlan(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getWorkPlan());
            
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setWorkTarget(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getWorkTarget());
            // ?????????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setAssessmentScore(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getAssessmentScore());
            // ????????????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setCompletion(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getCompletion());
            // ??????????????????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setChargeLeaderScore(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getChargeLeaderScore());
            // ??????????????????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveLeaderScore());
            // ??????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setExecutiveScore(zjXmCqjxProjectDeputyLeaderAssistantDetailed.getExecutiveScore());
            // ??????
            dbzjXmCqjxProjectDeputyLeaderAssistantDetailed.setModifyUserInfo(userKey, realName);
            flag = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxProjectDeputyLeaderAssistantDetailed);
         }
         // ??????
         if (flag == 0) {
             return repEntity.errorSave();
         }
         else {
             return repEntity.ok("sys.data.update",zjXmCqjxProjectDeputyLeaderAssistantDetailed);
         }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed(List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> zjXmCqjxProjectDeputyLeaderAssistantDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDeputyLeaderAssistantDetailedList != null && zjXmCqjxProjectDeputyLeaderAssistantDetailedList.size() > 0) {
           ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed = new ZjXmCqjxProjectDeputyLeaderAssistantDetailed();
           zjXmCqjxProjectDeputyLeaderAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed(zjXmCqjxProjectDeputyLeaderAssistantDetailedList, zjXmCqjxProjectDeputyLeaderAssistantDetailed);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDeputyLeaderAssistantDetailedList);
        }
    }
}