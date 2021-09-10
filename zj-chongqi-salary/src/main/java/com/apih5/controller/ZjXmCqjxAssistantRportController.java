package com.apih5.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDeptLeaderAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxStaffAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxYearAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantYearReportBean;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;


@RestController
public class ZjXmCqjxAssistantRportController {

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantDetailedMapper zjXmCqjxExecutiveAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxDeptLeaderAssistantDetailedMapper zjXmCqjxDeptLeaderAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxStaffAssistantDetailedMapper zjXmCqjxStaffAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMemberMapper zjXmCqjxCollaborationAssessmentMemberMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxYearAssistantMapper zjXmCqjxYearAssistantMapper;
    
	@Autowired(required = true)
	private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;
	
    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;
    
	@PostMapping("/getZjXmCqjxAssistantRportListByQuarter")	
	public List<ZjXmCqjxAssistantReportBean> getZjXmCqjxAssistantRportListByQuarter(@RequestBody(required = false) ZjXmCqjxAssistantReportBean zjXmCqjxAssistantReportBean) {
        if (zjXmCqjxAssistantReportBean == null) {
        	zjXmCqjxAssistantReportBean = new ZjXmCqjxAssistantReportBean();
        }      
        int planIndex = 0;
        int tagerIndex = 0;
        double executiveScore = 0.0;
        String year = "";
        String deptWorkPlan = "";
        String deptWorkTarget = "";
        String deptCompletion = "";
        String deptChargeLeaderScore = "";
        String deptExecutiveLeaderScore = "";
        double changeSubtotal = 0.0;
        double executiveSubtotal = 0.0;
        List<ZjXmCqjxAssistantReportBean> detailList = new ArrayList<ZjXmCqjxAssistantReportBean>();
        ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxAssistantReportBean.getExecutiveId());
        switch (zjXmCqjxExecutiveAssistant.getAssessmentQuarter()) {
		case "0":
			year = "考核期间："+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")
			       +"年  1月至  "+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")+"年  3月";
			break;
		case "1":
			year = "考核期间："+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")
		       +"年  4月至  "+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")+"年  6月";			
			break;
		case "2":
			year = "考核期间："+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")
		       +"年  7月至  "+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")+"年  9月";					
			break;
		case "3":
			year = "考核期间："+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")
		       +"年  10月至  "+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")+"年  12月";				
			break;
		case "4":
			year = "考核期间："+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")
		       +"年  1月至  "+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")+"年  6月";				
			break;
		case "5":
			year = "考核期间："+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")
		       +"年  7月至  "+DateUtil.format(zjXmCqjxExecutiveAssistant.getAssessmentYears(), "YYYY")+"年  12月";				
			break;
		default:
			break;
		}
        if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
        	ZjXmCqjxExecutiveAssistantDetailed detail = new ZjXmCqjxExecutiveAssistantDetailed();
        	detail.setExecutiveId(zjXmCqjxAssistantReportBean.getExecutiveId());
        	detailList = zjXmCqjxExecutiveAssistantDetailedMapper.selectZjXmCqjxAssistantDetailedRportListByQuarter(detail);
        	if(detailList.size()>0) {
        		for(ZjXmCqjxAssistantReportBean bean : detailList) {
        			bean.setYears(year);
        			executiveScore = executiveScore + bean.getExecutiveScore();
        			bean.setDisciplineScoreStr(zjXmCqjxExecutiveAssistant.getDisciplineScore()+zjXmCqjxExecutiveAssistant.getCooperationScore()+"");
        			if(StrUtil.equals(bean.getWorkType(), "0")) {
        				bean.setWorkType("年度承担的工作开展情况  （满分50分）");
        				planIndex = planIndex+1;
        				bean.setIndex(planIndex+"");        				
        			}
        		}
        	}           	
        }else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
        	ZjXmCqjxDeptLeaderAssistantDetailed detail = new ZjXmCqjxDeptLeaderAssistantDetailed();
        	detail.setExecutiveId(zjXmCqjxAssistantReportBean.getExecutiveId());
        	detailList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectZjXmCqjxDeptLeaderDetailedRportListByQuarter(detail);
        	if(detailList.size()>0) {
        		for(int i = 0; i<detailList.size(); i++) {
        			if(StrUtil.equals(detailList.get(i).getWorkType(), "1")) {
        				deptWorkPlan = detailList.get(i).getWorkPlan();
        				deptWorkTarget = detailList.get(i).getWorkTarget();
        				deptCompletion = detailList.get(i).getCompletion();
        				deptChargeLeaderScore = detailList.get(i).getChargeLeaderScore()+"";
        				deptExecutiveLeaderScore = detailList.get(i).getExecutiveLeaderScore()+"";
        				detailList.remove(i);
        			}
        			 changeSubtotal = detailList.stream().mapToDouble(ZjXmCqjxAssistantReportBean::getChargeLeaderScore).sum();
        			 executiveSubtotal = detailList.stream().mapToDouble(ZjXmCqjxAssistantReportBean::getExecutiveLeaderScore).sum();
        		}
        		for(ZjXmCqjxAssistantReportBean bean : detailList) {
        			bean.setYears(year);
        			executiveScore = executiveScore + bean.getExecutiveScore();
        			bean.setDeptWorkPlan(deptWorkPlan);
        			bean.setDeptWorkTarget(deptWorkTarget);
        			bean.setDeptCompletion(deptCompletion);
        			bean.setDeptChargeLeaderScore(deptChargeLeaderScore);
        			bean.setDeptExecutiveLeaderScore(deptExecutiveLeaderScore);
        			bean.setChangeSubtotal("小计："+changeSubtotal);
        			bean.setExecutiveSubtotal("小计："+executiveSubtotal);
        			bean.setDisciplineScoreStr("小计："+(zjXmCqjxExecutiveAssistant.getDisciplineScore()+zjXmCqjxExecutiveAssistant.getCooperationScore()));
        			bean.setExecutiveScoreSumStr("合计："+zjXmCqjxExecutiveAssistant.getTaskScore());
        			if(StrUtil.equals(bean.getWorkType(), "0")) {
        				bean.setWorkType("任务业绩（满分40分）");
        				planIndex = planIndex+1;
        				bean.setIndex(planIndex+"");        				
        			}
        		}
        	}        	
        }else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "3")){
        	ZjXmCqjxStaffAssistantDetailed detail = new ZjXmCqjxStaffAssistantDetailed();
        	detail.setExecutiveId(zjXmCqjxAssistantReportBean.getExecutiveId());
        	detailList = zjXmCqjxStaffAssistantDetailedMapper.selectZjXmCqjxStaffDetailedRportListByQuarter(detail);
        	if(detailList.size()>0) {
        		for(ZjXmCqjxAssistantReportBean bean : detailList) {
        			bean.setYears(year);
        			executiveScore = executiveScore + bean.getExecutiveScore();
        			bean.setDeptCoefficient("部门系数："+bean.getDeptCoefficient());
        			bean.setQuarterScoreStr("最终得分=部门系数*个人考核得分："+zjXmCqjxExecutiveAssistant.getQuarterScore());
        			bean.setDisciplineScoreStr(zjXmCqjxExecutiveAssistant.getDisciplineScore()+zjXmCqjxExecutiveAssistant.getCooperationScore()+"");
        			bean.setExecutiveScoreSumStr("个人季度考核得分："+zjXmCqjxExecutiveAssistant.getTaskScore());
        			if(StrUtil.equals(bean.getWorkType(), "0")) {
        				bean.setWorkType("任务业绩（满分25分）");
        				planIndex = planIndex+1;
        				bean.setIndex(planIndex+"");        				
        			}else {       
        				bean.setWorkType("日常工作任务（25分）");
        				tagerIndex = tagerIndex+1;
        				bean.setIndex(tagerIndex+"");
        			}
        		}
        	}
        }
		return detailList;
	}	
	@PostMapping("/getZjXmCqjxAssistantRportListByYear")	
	public ZjXmCqjxAssistantReportBean getZjXmCqjxAssistantRportListByYear(@RequestBody(required = false) ZjXmCqjxAssistantReportBean zjXmCqjxAssistantReportBean) {   
		ZjXmCqjxAssistantReportBean reportBean = new ZjXmCqjxAssistantReportBean();
		ZjXmCqjxYearAssistant zjXmCqjxYearExecutiveAssistant = zjXmCqjxYearAssistantMapper.selectByPrimaryKey(zjXmCqjxAssistantReportBean.getExecutiveId());
		if(zjXmCqjxYearExecutiveAssistant!= null) {
			reportBean.setItemFiveScore(zjXmCqjxYearExecutiveAssistant.getItemFive());
			reportBean.setItemFourScore(zjXmCqjxYearExecutiveAssistant.getItemFour());
			reportBean.setItemThreeScore(zjXmCqjxYearExecutiveAssistant.getItemThree());
			reportBean.setItemTwoScore(zjXmCqjxYearExecutiveAssistant.getItemTwo());
			reportBean.setItemOneScore(zjXmCqjxYearExecutiveAssistant.getItemOne());
			ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
			assistant.setAssessmentYears(zjXmCqjxYearExecutiveAssistant.getAssessmentYears());
			assistant.setAssessmentType(zjXmCqjxYearExecutiveAssistant.getAssessmentType());
			assistant.setCreateUser(zjXmCqjxYearExecutiveAssistant.getCreateUser());
			List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
			if(assistantList.size()>0) {
				reportBean.setDepartmentName(assistantList.get(0).getDepartmentName());
				reportBean.setCompletion(assistantList.get(0).getPosition());
				//计算季度平均分
				double avg = assistantList.stream().collect(Collectors.averagingDouble(ZjXmCqjxExecutiveAssistant::getQuarterScore));	
				BigDecimal bigAvg = new BigDecimal(avg);
				avg = bigAvg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();   
				if(StrUtil.equals(zjXmCqjxYearExecutiveAssistant.getAssessmentType(), "1")) {
					reportBean.setAssessmentTitle("公司副总师、经理助理"+DateUtil.format(zjXmCqjxYearExecutiveAssistant.getAssessmentYears(), "yyyy")+"年度绩效考核评分表");
					reportBean.setCreateUserName(zjXmCqjxYearExecutiveAssistant.getCreateUserName());
					reportBean.setPosition(assistantList.get(0).getPosition());
					reportBean.setAverageScore(avg+"");
					if(assistantList.size()>1) {						
						reportBean.setFirstQuarterScore(assistantList.get(0).getQuarterScore()+"");
						reportBean.setSecondQuarterScore(assistantList.get(1).getQuarterScore()+"");
					}else {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore(assistantList.get(1).getQuarterScore()+"");						
					}
					reportBean.setQuarterScore(zjXmCqjxYearExecutiveAssistant.getYearFinalScore());//年度最终得分
				}else if(StrUtil.equals(zjXmCqjxYearExecutiveAssistant.getAssessmentType(), "2")) {
					reportBean.setAssessmentTitle(" 部门正副职"+DateUtil.format(zjXmCqjxYearExecutiveAssistant.getAssessmentYears(), "yyyy")+"年度绩效考核评分表");
					reportBean.setCreateUserName(zjXmCqjxYearExecutiveAssistant.getCreateUserName());
					reportBean.setPosition(assistantList.get(0).getPosition());
					reportBean.setAverageScore(avg+"");
					if(assistantList.size() == 4) {						
						reportBean.setFirstQuarterScore(assistantList.get(0).getQuarterScore()+"");
						reportBean.setSecondQuarterScore(assistantList.get(1).getQuarterScore()+"");			
						reportBean.setThirdQuarterScore(assistantList.get(2).getQuarterScore()+"");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");	
					}else if(assistantList.size() == 3) {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore(assistantList.get(1).getQuarterScore()+"");			
						reportBean.setThirdQuarterScore(assistantList.get(2).getQuarterScore()+"");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");							
					}else if(assistantList.size() == 2) {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore("");			
						reportBean.setThirdQuarterScore(assistantList.get(2).getQuarterScore()+"");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");							
					}else if(assistantList.size() == 1) {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore("");			
						reportBean.setThirdQuarterScore("");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");						
					}
					reportBean.setQuarterScore(zjXmCqjxYearExecutiveAssistant.getYearFinalScore());//年度最终得分
					reportBean.setComprehensiveScore(zjXmCqjxYearExecutiveAssistant.getComprehensiveScore()+"");//年度综合得分
				}else if(StrUtil.equals(zjXmCqjxYearExecutiveAssistant.getAssessmentType(), "3")){
					reportBean.setAssessmentTitle("部门员工"+DateUtil.format(zjXmCqjxYearExecutiveAssistant.getAssessmentYears(), "yyyy")+"年度绩效考核评分表");
					reportBean.setCreateUserName(zjXmCqjxYearExecutiveAssistant.getCreateUserName());
					reportBean.setPosition(assistantList.get(0).getPosition());
					reportBean.setAverageScore(avg+"");
					if(assistantList.size() == 4) {						
						reportBean.setFirstQuarterScore(assistantList.get(0).getQuarterScore()+"");
						reportBean.setSecondQuarterScore(assistantList.get(1).getQuarterScore()+"");			
						reportBean.setThirdQuarterScore(assistantList.get(2).getQuarterScore()+"");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");	
					}else if(assistantList.size() == 3) {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore(assistantList.get(1).getQuarterScore()+"");			
						reportBean.setThirdQuarterScore(assistantList.get(2).getQuarterScore()+"");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");							
					}else if(assistantList.size() == 2) {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore("");			
						reportBean.setThirdQuarterScore(assistantList.get(2).getQuarterScore()+"");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");							
					}else if(assistantList.size() == 1) {
						reportBean.setFirstQuarterScore("");
						reportBean.setSecondQuarterScore("");			
						reportBean.setThirdQuarterScore("");			
						reportBean.setFourthQuarterScore(assistantList.get(3).getQuarterScore()+"");						
					}
					reportBean.setQuarterScore(zjXmCqjxYearExecutiveAssistant.getYearFinalScore());//年度最终得分
					reportBean.setComprehensiveScore(zjXmCqjxYearExecutiveAssistant.getComprehensiveScore()+"");//年度综合得分			
				}
			}			
		}
		return reportBean;
	}	
	@PostMapping("/getZjXmCqjxCollAssReportByCollaborationId")	
	public List<ZjXmCqjxCollaborationAssessmentMember> getZjXmCqjxCollAssReportByCollaborationId(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
		// 获取数据
		int index = 0;
        List<ZjXmCqjxCollaborationAssessmentMember> dateilList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(zjXmCqjxCollaborationAssessmentMember);
        for(ZjXmCqjxCollaborationAssessmentMember date : dateilList) {
        	index = index+1;
        	date.setIndex(index);
        	switch (date.getAssessmentFlag()) {
			case "0":
				date.setAssessmentFlag("未完成");
				break;
			case "1":
				date.setAssessmentFlag("已完成");				
				break;
			}        	
        }
		return dateilList;
	}
	@PostMapping("/getZjXmCqjxYearAssistantRportListByYear")	
	public List<ZjXmCqjxAssistantYearReportBean> getZjXmCqjxYearAssistantRportListByYear(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
		String deptHeadId = "";
		BigDecimal deptCoefficient = null;
		BigDecimal quarterEndScore = null;
		BigDecimal quarterScore = null;
		String title = "中交一公局重庆城市发展有限公司总部职工"+DateUtil.format(zjXmCqjxYearAssistant.getAssessmentYears(), "yyyy")+"年年度职工考核情况";
		List<ZjXmCqjxAssistantYearReportBean> yearReportBeanList = new ArrayList<ZjXmCqjxAssistantYearReportBean>();
		List<ZjXmCqjxDepartmentHead> headList =  zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(null);
		for(ZjXmCqjxDepartmentHead head : headList) {
			ZjXmCqjxDepartmentHeadDetail headDetail = new ZjXmCqjxDepartmentHeadDetail();
			headDetail.setOtherId(head.getDepartmentHeadId());
			headDetail.setOtherType("0");
			List<ZjXmCqjxDepartmentHeadDetail> headDetailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(headDetail);
			deptHeadId = headDetailList.get(0).getOaUserId();
			ZjXmCqjxYearAssistant yearAssistant = new ZjXmCqjxYearAssistant();
        	yearAssistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
        	yearAssistant.setCreateUser(deptHeadId);
		    List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(yearAssistant);
		    ZjXmCqjxAssistantYearReportBean reportBean = new ZjXmCqjxAssistantYearReportBean();
		    reportBean.setAssessmentTitle(title);
		    reportBean.setDepartmentName(zjXmCqjxYearAssistantList.get(0).getDepartmentName());
		    if(StrUtil.equals(zjXmCqjxYearAssistantList.get(0).getDepartmentName(), "市场投资营销部")) {
		    	reportBean.setDepartmentName("投资开发管理部");
		    }
		    reportBean.setCreateUserName(zjXmCqjxYearAssistantList.get(0).getCreateUserName());
		    reportBean.setYearFinalScore(zjXmCqjxYearAssistantList.get(0).getYearFinalScore()+"");
		    reportBean.setComprehensiveScore(zjXmCqjxYearAssistantList.get(0).getComprehensiveScore()+"");
		    reportBean.setAverageScore(zjXmCqjxYearAssistantList.get(0).getQuarterAverageScore()+"");
		    ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
		    assistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
		    assistant.setCreateUser(deptHeadId);
		    List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(assistant);
		    for(ZjXmCqjxExecutiveAssistant executiveAssistant : assistantList) {
		    	if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "0")) {
//				    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
		    		reportBean.setFirstDeptCoefficient("1.0");
		    		reportBean.setFirstQuarterEndScore(executiveAssistant.getQuarterScore()+"");
		    		reportBean.setFirstQuarterScore(executiveAssistant.getQuarterScore()+"");
		    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "1")) {
//				    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
		    		reportBean.setSecondDeptCoefficient("1.0");
		    		reportBean.setSecondQuarterEndScore(executiveAssistant.getQuarterScore()+"");
		    		reportBean.setSecondQuarterScore(executiveAssistant.getQuarterScore()+"");
		    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "2")) {
//				    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
		    		reportBean.setThirdDeptCoefficient("1.0");
		    		reportBean.setThirdQuarterEndScore(executiveAssistant.getQuarterScore()+"");
		    		reportBean.setThirdQuarterScore(executiveAssistant.getQuarterScore()+"");
		    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "3")) {
//				    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
		    		reportBean.setFourthDeptCoefficient("1.0");
		    		reportBean.setFourthQuarterEndScore(executiveAssistant.getQuarterScore()+"");
		    		reportBean.setFourthQuarterScore(executiveAssistant.getQuarterScore()+"");
		    	}
		    }
		    yearReportBeanList.add(reportBean);
		    
			yearAssistant = new ZjXmCqjxYearAssistant();
        	yearAssistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
        	yearAssistant.setDepartmentId(head.getDepartmentId());
        	yearAssistant.setAssessmentType("2");
		    zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(yearAssistant);
		    for(ZjXmCqjxYearAssistant zjXmCqjxYearAssistanta : zjXmCqjxYearAssistantList) {
		    	if(!StrUtil.equals(zjXmCqjxYearAssistanta.getCreateUser(), deptHeadId)) {
				    reportBean = new ZjXmCqjxAssistantYearReportBean();
				    reportBean.setAssessmentTitle(title);
				    reportBean.setCreateUserName(zjXmCqjxYearAssistanta.getCreateUserName());
				    reportBean.setYearFinalScore(zjXmCqjxYearAssistanta.getYearFinalScore()+"");
				    reportBean.setComprehensiveScore(zjXmCqjxYearAssistanta.getComprehensiveScore()+"");
				    reportBean.setAverageScore(zjXmCqjxYearAssistanta.getQuarterAverageScore()+"");				    
				    assistant = new ZjXmCqjxExecutiveAssistant();
				    assistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
				    assistant.setDepartmentId(head.getDepartmentId());
				    assistant.setCreateUser(zjXmCqjxYearAssistanta.getCreateUser());
				    assistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(assistant);
				    for(ZjXmCqjxExecutiveAssistant executiveAssistant : assistantList) {
				    	if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "0")) {
//						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
				    		reportBean.setFirstDeptCoefficient("1.0");
				    		reportBean.setFirstQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		reportBean.setFirstQuarterScore(executiveAssistant.getQuarterScore()+"");
				    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "1")) {
//						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
				    		reportBean.setSecondDeptCoefficient("1.0");
				    		reportBean.setSecondQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		reportBean.setSecondQuarterScore(executiveAssistant.getQuarterScore()+"");
				    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "2")) {
//						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
				    		reportBean.setThirdDeptCoefficient("1.0");
				    		reportBean.setThirdQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		reportBean.setThirdQuarterScore(executiveAssistant.getQuarterScore()+"");
				    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "3")) {
						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
						    if(StrUtil.equals(executiveAssistant.getDepartmentName(), "市场投资营销部")) {
						    	reportBean.setDepartmentName("投资开发管理部");
						    }
				    		reportBean.setFourthDeptCoefficient("1.0");
				    		reportBean.setFourthQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		reportBean.setFourthQuarterScore(executiveAssistant.getQuarterScore()+"");
				    	}
				    }
				    yearReportBeanList.add(reportBean);		    		
		    	}
		    }
			yearAssistant = new ZjXmCqjxYearAssistant();
        	yearAssistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
        	yearAssistant.setDepartmentId(head.getDepartmentId());
        	yearAssistant.setAssessmentType("3");
		    zjXmCqjxYearAssistantList = zjXmCqjxYearAssistantMapper.selectByZjXmCqjxYearAssistantList(yearAssistant);
		    for(ZjXmCqjxYearAssistant zjXmCqjxYearAssistanta : zjXmCqjxYearAssistantList) {
		    	reportBean = new ZjXmCqjxAssistantYearReportBean();
			    reportBean.setAssessmentTitle(title);
			    reportBean.setCreateUserName(zjXmCqjxYearAssistanta.getCreateUserName());
			    reportBean.setYearFinalScore(zjXmCqjxYearAssistanta.getYearFinalScore()+"");
			    reportBean.setComprehensiveScore(zjXmCqjxYearAssistanta.getComprehensiveScore()+"");
			    reportBean.setAverageScore(zjXmCqjxYearAssistanta.getQuarterAverageScore()+"");		
				    assistant = new ZjXmCqjxExecutiveAssistant();
				    assistant.setAssessmentYears(zjXmCqjxYearAssistant.getAssessmentYears());
				    assistant.setDepartmentId(head.getDepartmentId());
				    assistant.setCreateUser(zjXmCqjxYearAssistanta.getCreateUser());
				    assistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(assistant);
				    for(ZjXmCqjxExecutiveAssistant executiveAssistant : assistantList) {
				    	if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "0")) {
//						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
				    		reportBean.setFirstDeptCoefficient(executiveAssistant.getDeptCoefficient());
				    		reportBean.setFirstQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		quarterEndScore = new BigDecimal(executiveAssistant.getQuarterScore());
				    		deptCoefficient = new BigDecimal(executiveAssistant.getDeptCoefficient());
				    		quarterScore = CalcUtils.calcDivide(quarterEndScore, deptCoefficient, 2);
				    		reportBean.setFirstQuarterScore(quarterScore.toPlainString());
				    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "1")) {
//						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
				    		reportBean.setSecondDeptCoefficient(executiveAssistant.getDeptCoefficient());
				    		reportBean.setSecondQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		quarterEndScore = new BigDecimal(executiveAssistant.getQuarterScore());
				    		deptCoefficient = new BigDecimal(executiveAssistant.getDeptCoefficient());
				    		quarterScore = CalcUtils.calcDivide(quarterEndScore, deptCoefficient, 2);
				    		reportBean.setSecondQuarterScore(quarterScore.toPlainString());
				    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "2")) {
//						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
				    		reportBean.setThirdDeptCoefficient(executiveAssistant.getDeptCoefficient());
				    		reportBean.setThirdQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		quarterEndScore = new BigDecimal(executiveAssistant.getQuarterScore());
				    		deptCoefficient = new BigDecimal(executiveAssistant.getDeptCoefficient());
				    		quarterScore = CalcUtils.calcDivide(quarterEndScore, deptCoefficient, 2);
				    		reportBean.setThirdQuarterScore(quarterScore.toPlainString());
				    	}else if(StrUtil.equals(executiveAssistant.getAssessmentQuarter(), "3")) {
						    reportBean.setDepartmentName(executiveAssistant.getDepartmentName());
						    if(StrUtil.equals(executiveAssistant.getDepartmentName(), "市场投资营销部")) {
						    	reportBean.setDepartmentName("投资开发管理部");
						    }
				    		reportBean.setFourthDeptCoefficient(executiveAssistant.getDeptCoefficient());
				    		reportBean.setFourthQuarterEndScore(executiveAssistant.getQuarterScore()+"");
				    		quarterEndScore = new BigDecimal(executiveAssistant.getQuarterScore());
				    		deptCoefficient = new BigDecimal(executiveAssistant.getDeptCoefficient());
				    		quarterScore = CalcUtils.calcDivide(quarterEndScore, deptCoefficient, 2);
				    		reportBean.setFourthQuarterScore(quarterScore.toPlainString());
				    	}
				    }				    
				    yearReportBeanList.add(reportBean);		    		
		    }
		}
		return yearReportBeanList;
	}
}
