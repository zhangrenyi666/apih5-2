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
        // ????????????
        PageHelper.startPage(zjXmCqjxProjectSecretaryrAssistantDetailed.getPage(),zjXmCqjxProjectSecretaryrAssistantDetailed.getLimit());
        // ????????????
        List<ZjXmCqjxProjectSecretaryrAssistantDetailed> zjXmCqjxProjectSecretaryrAssistantDetailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(zjXmCqjxProjectSecretaryrAssistantDetailed);
        if(zjXmCqjxProjectSecretaryrAssistantDetailedList.size()>0) {
            double sum = zjXmCqjxProjectSecretaryrAssistantDetailedList.stream().mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
            String lastScore = ((int)(60-sum)+"");
            zjXmCqjxProjectSecretaryrAssistantDetailedList.get(0).setLastScore(lastScore);  
        }         
        // ??????????????????
        PageInfo<ZjXmCqjxProjectSecretaryrAssistantDetailed> p = new PageInfo<>(zjXmCqjxProjectSecretaryrAssistantDetailedList);

        return repEntity.okList(zjXmCqjxProjectSecretaryrAssistantDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectSecretaryrAssistantDetailedDetails(ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        if (zjXmCqjxProjectSecretaryrAssistantDetailed == null) {
            zjXmCqjxProjectSecretaryrAssistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
        }
        // ????????????
        ZjXmCqjxProjectSecretaryrAssistantDetailed dbZjXmCqjxProjectSecretaryrAssistantDetailed = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId());
        // ????????????
        if (dbZjXmCqjxProjectSecretaryrAssistantDetailed != null) {
            return repEntity.ok(dbZjXmCqjxProjectSecretaryrAssistantDetailed);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
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
//        		return repEntity.layerMessage("NO", "????????????????????????");
//        	}
//        }
        ZjXmCqjxProjectSecretaryrAssistantDetailed assistantDetailed = new ZjXmCqjxProjectSecretaryrAssistantDetailed();
        assistantDetailed.setExecutiveId(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
        assistantDetailed.setWorkType(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkType());        
        List<ZjXmCqjxProjectSecretaryrAssistantDetailed> detailedList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectByZjXmCqjxProjectSecretaryrAssistantDetailedList(assistantDetailed);
        for(ZjXmCqjxProjectSecretaryrAssistantDetailed detailed : detailedList) {
        	if(!StrUtil.equals(detailed.getDeptLeaderId(), zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId()) && StrUtil.equals(detailed.getWorkPlan(), zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkPlan())) {
        		return repEntity.layerMessage("NO", "??????????????????????????????????????????");
        	}        	
        	if(StrUtil.equals(detailed.getDeptLeaderId(), zjXmCqjxProjectSecretaryrAssistantDetailed.getDeptLeaderId())) {
        		detailed.setAssessmentScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore());
        	}
        }
        double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
//        if((sum>60.0) && StrUtil.equals(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkType(), "0")) {
//        	return repEntity.layerMessage("NO", "???????????????????????????????????????");
//        }
//        double sum = detailedList.stream().mapToDouble(ZjXmCqjxProjectSecretaryrAssistantDetailed::getAssessmentScore).sum();
        if((sum>60.0)) {
        	zjXmCqjxProjectSecretaryrAssistantDetailed.setLastScore((60-sum)+"");
        }else {
        	zjXmCqjxProjectSecretaryrAssistantDetailed.setLastScore((60-sum)+"");
        }        
        ZjXmCqjxProjectExecutiveAssistant assistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
        //?????????????????????????????????????????????????????????
        if(StrUtil.equals(assistant.getAssessmentState(), "6") || StrUtil.equals(assistant.getAssessmentState(), "7")) {
        	if(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore()>zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "??????????????????????????????????????????");
        	}else if(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore()>zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()) {
            	return repEntity.layerMessage("NO", "??????????????????????????????????????????");
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
                	return repEntity.layerMessage("NO", "?????????????????????0");
                }else if(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore()<0){
                	return repEntity.layerMessage("NO", "???????????????????????????");
                }
        	}   
        	if(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "?????????????????????");
            }else if(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore()<0) {
            	return repEntity.layerMessage("NO", "?????????????????????");
            }   
           // ????????????ID
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setManagerId(zjXmCqjxProjectSecretaryrAssistantDetailed.getManagerId());
           // ??????????????????????????????ID
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveId(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveId());
           // ????????????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setWorkType(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkType());
           // ????????????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setWorkPlan(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkPlan());
           // ??????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setWorkTarget(zjXmCqjxProjectSecretaryrAssistantDetailed.getWorkTarget());
           // ?????????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setAssessmentScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getAssessmentScore());
           // ????????????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setCompletion(zjXmCqjxProjectSecretaryrAssistantDetailed.getCompletion());
           // ??????????????????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setChargeLeaderScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getChargeLeaderScore());
           // ??????????????????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveLeaderScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveLeaderScore());
           // ??????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setExecutiveScore(zjXmCqjxProjectSecretaryrAssistantDetailed.getExecutiveScore());
           // ??????
           dbzjXmCqjxProjectSecretaryrAssistantDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.updateByPrimaryKey(dbzjXmCqjxProjectSecretaryrAssistantDetailed);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectSecretaryrAssistantDetailedList);
        }
    }
}