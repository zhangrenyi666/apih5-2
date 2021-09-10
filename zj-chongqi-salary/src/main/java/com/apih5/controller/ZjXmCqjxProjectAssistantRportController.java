package com.apih5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.mybatis.dao.ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectOverallEvaluationAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSecretaryrAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectStaffAssistantDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;


@RestController
public class ZjXmCqjxProjectAssistantRportController {

	@Autowired(required = true)
	private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectStaffAssistantDetailedMapper zjXmCqjxProjectStaffAssistantDetailedMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectSecretaryrAssistantDetailedMapper zjXmCqjxProjectSecretaryrAssistantDetailedMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectDeputyLeaderAssistantDetailedMapper zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper;

	@Autowired(required = true)
	private ZjXmCqjxProjectOverallEvaluationAssistantMapper zjXmCqjxProjectOverallEvaluationAssistantMapper;
	
	@PostMapping("/getZjXmCqjxProjectAssistantRportListByQuarter")	
	public List<ZjXmCqjxAssistantReportBean> getZjXmCqjxAssistantRportListByQuarter(@RequestBody(required = false) ZjXmCqjxAssistantReportBean zjXmCqjxAssistantReportBean) {
        if (zjXmCqjxAssistantReportBean == null) {
        	zjXmCqjxAssistantReportBean = new ZjXmCqjxAssistantReportBean();
        }      
        int planIndex = 0;
        int tagerIndex = 0;
//        double executiveScore = 0.0;
        String year = "";
        String deptWorkPlan = "";
        String deptWorkTarget = "";
        String deptCompletion = "";
        String deptChargeLeaderScore = "";
        String deptExecutiveLeaderScore = "";
        double changeSubtotal = 0.0;
        double executiveSubtotal = 0.0;
        String itemOneScore = "";
        String itemTwoScore = "";
        String itemThreeScore = "";
        String itemFourScore = "";
        String itemFiveScore = "";
        List<ZjXmCqjxAssistantReportBean> detailList = new ArrayList<ZjXmCqjxAssistantReportBean>();
        ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxAssistantReportBean.getExecutiveId());
        DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY-MM");
        DateUtil.format(DateUtil.offsetMonth(zjXmCqjxExecutiveAssistant.getAssessmentYears(), -1), "YYYY-MM");
        DateUtil.offsetMonth(zjXmCqjxExecutiveAssistant.getAssessmentYears(), -1);
        year = "考核期间："+DateUtil.format(DateUtil.offsetMonth(zjXmCqjxExecutiveAssistant.getAssessmentYears(), -1), "YYYY年MM月")+"至"+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY年MM月");
        if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
            //综合评价
           	ZjXmCqjxProjectOverallEvaluationAssistant evAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
           	evAssistant.setCreateUser(zjXmCqjxExecutiveAssistant.getCreateUser());
           	evAssistant.setManagerId(zjXmCqjxExecutiveAssistant.getManagerId());
           	List<ZjXmCqjxProjectOverallEvaluationAssistant> evAssistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByZjXmCqjxProjectOverallEvaluationAssistantList(evAssistant);
           	if(evAssistantList.size()>0) {
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemOneScore())) {
           			itemOneScore = evAssistantList.get(0).getItemOneScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemTwoScore())) {
           			itemTwoScore = evAssistantList.get(0).getItemTwoScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemThreeScore())) {
           			itemThreeScore = evAssistantList.get(0).getItemThreeScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemFourScore())) {
           			itemFourScore = evAssistantList.get(0).getItemFourScore();
           		}
           	}
        	ZjXmCqjxExecutiveAssistantDetailed detail = new ZjXmCqjxExecutiveAssistantDetailed();
        	detail.setExecutiveId(zjXmCqjxAssistantReportBean.getExecutiveId());
        	detailList = zjXmCqjxProjectDeputyLeaderAssistantDetailedMapper.selectZjXmCqjxAssistantDetailedRportListByQuarter(detail);
        	//责任指标
        	if(detailList.size()>0) {
        		for(ZjXmCqjxAssistantReportBean bean : detailList) {
        			bean.setItemOneScore(itemOneScore);
        			bean.setItemTwoScore(itemTwoScore);
        			bean.setItemThreeScore(itemThreeScore);
        			bean.setItemFourScore(itemFourScore);
        			bean.setYears(year);
        			bean.setDisciplineScoreStr(zjXmCqjxExecutiveAssistant.getDisciplineScore()+"");
        			bean.setQuarterScoreStr(zjXmCqjxExecutiveAssistant.getQuarterScore()+"");
    				planIndex = planIndex+1;
    				bean.setIndex(planIndex+"");        				
        		}
        	}  
        }else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
            //综合评价
           	ZjXmCqjxProjectOverallEvaluationAssistant evAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
           	evAssistant.setCreateUser(zjXmCqjxExecutiveAssistant.getCreateUser());
           	evAssistant.setManagerId(zjXmCqjxExecutiveAssistant.getManagerId());
           	List<ZjXmCqjxProjectOverallEvaluationAssistant> evAssistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByZjXmCqjxProjectOverallEvaluationAssistantList(evAssistant);
           	if(evAssistantList.size()>0) {
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemOneScore())) {
           			itemOneScore = evAssistantList.get(0).getItemOneScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemTwoScore())) {
           			itemTwoScore = evAssistantList.get(0).getItemTwoScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemThreeScore())) {
           			itemThreeScore = evAssistantList.get(0).getItemThreeScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemFourScore())) {
           			itemFourScore = evAssistantList.get(0).getItemFourScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemFiveScore())) {
           			itemFiveScore = evAssistantList.get(0).getItemFiveScore();
           		}
           	} 
        	ZjXmCqjxDeptLeaderAssistantDetailed detail = new ZjXmCqjxDeptLeaderAssistantDetailed();
        	detail.setExecutiveId(zjXmCqjxAssistantReportBean.getExecutiveId());
        	detailList = zjXmCqjxProjectSecretaryrAssistantDetailedMapper.selectZjXmCqjxDeptLeaderDetailedRportListByQuarter(detail);
        	if(detailList.size()>0) {
        		for(ZjXmCqjxAssistantReportBean bean : detailList) {
        			bean.setItemOneScore(itemOneScore);
        			bean.setItemTwoScore(itemTwoScore);
        			bean.setItemThreeScore(itemThreeScore);
        			bean.setItemFourScore(itemFourScore);
        			bean.setItemFiveScore(itemFiveScore);
        			bean.setYears(year);
        			bean.setDisciplineScoreStr(zjXmCqjxExecutiveAssistant.getDisciplineScore()+"");
        			bean.setQuarterScoreStr(zjXmCqjxExecutiveAssistant.getQuarterScore()+"");
    				planIndex = planIndex+1;
    				bean.setIndex(planIndex+"");        				
        		}
        	}        	
        }else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "3")){
            //综合评价
           	ZjXmCqjxProjectOverallEvaluationAssistant evAssistant = new ZjXmCqjxProjectOverallEvaluationAssistant();
           	evAssistant.setCreateUser(zjXmCqjxExecutiveAssistant.getCreateUser());
           	evAssistant.setManagerId(zjXmCqjxExecutiveAssistant.getManagerId());
           	List<ZjXmCqjxProjectOverallEvaluationAssistant> evAssistantList = zjXmCqjxProjectOverallEvaluationAssistantMapper.selectByZjXmCqjxProjectOverallEvaluationAssistantList(evAssistant);
           	if(evAssistantList.size()>0) {
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemOneScore())) {
           			itemOneScore = evAssistantList.get(0).getItemOneScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemTwoScore())) {
           			itemTwoScore = evAssistantList.get(0).getItemTwoScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemThreeScore())) {
           			itemThreeScore = evAssistantList.get(0).getItemThreeScore();
           		}
           		if(StrUtil.isNotEmpty(evAssistantList.get(0).getItemFourScore())) {
           			itemFourScore = evAssistantList.get(0).getItemFourScore();
           		}
           	}        	
        	ZjXmCqjxStaffAssistantDetailed detail = new ZjXmCqjxStaffAssistantDetailed();
        	detail.setExecutiveId(zjXmCqjxAssistantReportBean.getExecutiveId());
        	detailList = zjXmCqjxProjectStaffAssistantDetailedMapper.selectZjXmCqjxProjectStaffDetailedRportListByQuarter(detail);
        	if(detailList.size()>0) {
        		for(ZjXmCqjxAssistantReportBean bean : detailList) {
        			bean.setItemOneScore(itemOneScore);
        			bean.setItemTwoScore(itemTwoScore);
        			bean.setItemThreeScore(itemThreeScore);
        			bean.setItemFourScore(itemFourScore);
        			bean.setYears(year);
        			bean.setDisciplineScoreStr(zjXmCqjxExecutiveAssistant.getDisciplineScore()+"");
        			bean.setQuarterScoreStr(zjXmCqjxExecutiveAssistant.getQuarterScore()+"");
    				planIndex = planIndex+1;
    				bean.setIndex(planIndex+"");        				
        		}
        	}
        }
		return detailList;
	}	
//	@PostMapping("/getZjXmCqjxCollAssReportByCollaborationId")	
//	public List<ZjXmCqjxCollaborationAssessmentMember> getZjXmCqjxCollAssReportByCollaborationId(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
//		// 获取数据
//		int index = 0;
//        List<ZjXmCqjxCollaborationAssessmentMember> dateilList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(zjXmCqjxCollaborationAssessmentMember);
//        for(ZjXmCqjxCollaborationAssessmentMember date : dateilList) {
//        	index = index+1;
//        	date.setIndex(index);
//        	switch (date.getAssessmentFlag()) {
//			case "0":
//				date.setAssessmentFlag("未完成");
//				break;
//			case "1":
//				date.setAssessmentFlag("已完成");				
//				break;
//			}        	
//        }
////        dateilList.parallelStream().forEach((date)->{
////        	switch (date.getAssessmentFlag()) {
////			case "0":
////				date.setAssessmentFlag("未完成");
////				break;
////			case "1":
////				date.setAssessmentFlag("已完成");				
////				break;
////			}
////        });
//		return dateilList;
//	}	
}
