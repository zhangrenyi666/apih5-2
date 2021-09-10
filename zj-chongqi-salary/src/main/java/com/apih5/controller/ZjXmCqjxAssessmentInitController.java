package com.apih5.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.apih5.framework.api.zjoa.common.BusinessZj;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.DesUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentService;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentService;
import com.apih5.service.ZjXmCqjxExecutiveAssistantService;

import cn.hutool.core.util.StrUtil;

@RestController
public class ZjXmCqjxAssessmentInitController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	private Apih5Properties apih5Properties;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantService zjXmCqjxExecutiveAssistantService;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentService zjXmCqjxDisciplineAssessmentService;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentService zjXmCqjxCollaborationAssessmentService;
    
	/**
	 * 部门负责人设置
	 */
	@RequestMapping(value = "/initOaZjCqjxDeptLeaderList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxDeptLeaderList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/listout.html"+"?code=" + token);
		}
	}
	
	/**
	 * 绩效考核计划
	 */
	@RequestMapping(value = "/initOaZjCqjxManageList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxManageList(HttpServletRequest request) {
		String userId = "";
		try {
			DesUtil des = new DesUtil("yongan_sys");
			userId  = des.decrypt(request.getParameter("userAccount"));
			LoggerUtils.printLogger(logger, "getDocWorkList 开始 "+userId);			
		} catch (Exception e) {
		}		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
//			LoggerUtils.printLogger(logger, "getDocWorkList 开始 " + "11111");
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
//			LoggerUtils.printLogger(logger, "getDocWorkList 开始 " + "222222");
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/Ministerout.html"+"?code=" + token);
		}
	}
	
	/**
	 * 部门正、副职季度考核填报
	 */
	@RequestMapping(value = "/initOaZjCqjxDeptLeaderAssistantList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxDeptLeaderAssistantList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/DepartmentChief.html"+"?code=" + token);
		}
	}
	
	/**
	 * 副总师、经理助理半年考核填报
	 */
	@RequestMapping(value = "/initOaZjCqjxExecutiveAssistantList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxExecutiveAssistantList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/ExecutiveAssistant.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工季度考核填报
	 */
	@RequestMapping(value = "/initOaZjCqjxStaffAssistantList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxStaffAssistantList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/StaffQuarters.html"+"?code=" + token);
		}
	}
	
	/**
	 * 部门正、副职季度计划填报
	 */
	@RequestMapping(value = "/initOaZjCqjxDeptLeaderAssistantPlanList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxDeptLeaderAssistantPlanList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/DepartmentChiefPlan.html"+"?code=" + token);
		}
	}
	
	/**
	 * 副总师、经理助理半年计划填报
	 */
	@RequestMapping(value = "/initOaZjCqjxExecutiveAssistantPlanList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxExecutiveAssistantPlanList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/ExecutiveAssistantPlan.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工季度计划填报
	 */
	@RequestMapping(value = "/initOaZjCqjxStaffAssistantPlanList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxStaffAssistantPlanList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/StaffQuartersPlan.html"+"?code=" + token);
		}
	}
	
	/**
	 * 事项待办
	 */
	@RequestMapping(value = "/initOaZjCqjxLeaderToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxLeaderToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/LeadTheBacklogList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 年度考核待办
	 */
	@RequestMapping(value = "/initOaZjCqjxYearLeaderToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxYearLeaderToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/LeadEvaluationList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 年度考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxYearLeaderTodoCount", method = RequestMethod.GET)    
	public ResponseEntity apiZjXmCqjxYearLeaderTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c1c1b5509993f7fa7b2a8033d31b659b6666cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0e1c974b6dbf49bfd679f2218d05daedb8a49b7ef855ac7fdecd3d9e07c3b1ed9aa8b4c529baa6614";	
		return zjXmCqjxExecutiveAssistantService.getZjXmCqjxYearLeaderTodoCount(token,"1","0","0");
	}
	
	/**
	 * 年度考核已办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxYearLeaderHasTodoCount", method = RequestMethod.GET)    
	public ResponseEntity apiZjXmCqjxYearLeaderHasTodoCount(HttpServletRequest request) {
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
		return zjXmCqjxExecutiveAssistantService.getZjXmCqjxYearLeaderHasTodoCount(token);
	}
	
	/**
	 * 员工纪律性考评
	 */
	@RequestMapping(value = "/initOaZjCqjxDisciplineList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxDisciplineList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/discipline.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工协作性考评
	 */
	@RequestMapping(value = "/initOaZjCqjxCollaborationList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxCollaborationList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/interoperability.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工互评
	 */
	@RequestMapping(value = "/initOaZjCqjxMutualEvaluationList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxMutualEvaluationList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/mutualEvaluation.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工考核统计
	 */
	@RequestMapping(value = "/initOaZjCqjxMutualStaticList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxMutualStaticList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/deptHeaderList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 纪律性流程
	 */
	@RequestMapping(value = "/initOaZjCqjxDisciplineToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxDisciplineToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/LeadDisciplineList.html"+"?code=" + token);
		}
	}
    
	/**
	 * 副总师考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxExecutiveAssistantTodoCount", method = RequestMethod.GET)    
    public ResponseEntity apiZjXmCqjxExecutiveAssistantTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c1c1b5509993f7fa7b2a8033d31b659b6666cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0e1c974b6dbf49bfd679f2218d05daedb8a49b7ef855ac7fdecd3d9e07c3b1ed9aa8b4c529baa6614";	
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"1","0","1");
    }
	
	/**
	 * 部门正、副职考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxDeptLeaderAssistantTodoCount", method = RequestMethod.GET)    
    public ResponseEntity apiZjXmCqjxDeptLeaderAssistantTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"2","0","1");
    }
    
	/**
	 * 员工考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxStaffAssistantTodoCount", method = RequestMethod.GET)    	
    public ResponseEntity apiZjXmCqjxStaffAssistantTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"3","0","1");
    }
	/**
	 * 副总师任务待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxExecutiveAssistantPlanTodoCount", method = RequestMethod.GET)    
	public ResponseEntity apiZjXmCqjxExecutiveAssistantPlanTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c1c1b5509993f7fa7b2a8033d31b659b6666cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0e1c974b6dbf49bfd679f2218d05daedb8a49b7ef855ac7fdecd3d9e07c3b1ed9aa8b4c529baa6614";	
		return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"1","0","0");
	}
	
	/**
	 * 部门正、副职任务待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxDeptLeaderAssistantPlanTodoCount", method = RequestMethod.GET)    
	public ResponseEntity apiZjXmCqjxDeptLeaderAssistantPlanTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
		return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"2","0","0");
	}
	
	/**
	 * 员工任务待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxStaffAssistantPlanTodoCount", method = RequestMethod.GET)    	
	public ResponseEntity apiZjXmCqjxStaffAssistantPlanTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
		return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"3","0","0");
	}
    
	/**
	 * 领导任务考核审批待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxAssistantLeaderTodoCount", method = RequestMethod.GET)    		
    public ResponseEntity apiZjXmCqjxAssistantLeaderTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c109b8337bdc6da2894d9c1af344bf170666cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad1220b5453b4739bbcfdfcd49ba1eb99b046774518af025b35bb96ecc893be70bc3d80ec919efdb25495aa8b4c529baa6614";	
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"0","1","");
    }
	
	/**
	 * 领导任务考核审批已办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxAssistantLeaderHasTodoCount", method = RequestMethod.GET)    		
	public ResponseEntity apiZjXmCqjxAssistantLeaderHasTodoCount(HttpServletRequest request) {
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
		return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantHasTodoCount(token,"0","1","");
	}
    
	
	/**
	 * 事项已办
	 */
	@RequestMapping(value = "/initOaZjCqjxLeaderHasToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxLeaderHasToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/performanceAppraisalOldPc/LeadTheBacklogList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 领导纪律性考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxDisciplineLeaderTodoCount", method = RequestMethod.GET)    	
    public ResponseEntity apiZjXmCqjxDisciplineLeaderTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c109b8337bdc6da2894d9c1af344bf170666cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad1220b5453b4739bbcfdfcd49ba1eb99b046774518af025b35bb96ecc893be70bc3d80ec919efdb25495aa8b4c529baa6614";	
        return zjXmCqjxDisciplineAssessmentService.getZjXmCqjxDisciplineLeaderTodoCount(token,"1");
    }
	
	/**
	 * 领导纪律性考核已办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxDisciplineLeaderHasTodoCount", method = RequestMethod.GET)    	
	public ResponseEntity apiZjXmCqjxDisciplineLeaderHasTodoCount(HttpServletRequest request) {
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
		return zjXmCqjxDisciplineAssessmentService.getZjXmCqjxDisciplineLeaderHasTodoCount(token);
	}
    
	/**
	 * 员工纪律性考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxDisciplineTodoCount", method = RequestMethod.GET)  
    public ResponseEntity apiZjXmCqjxDisciplineTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
    	String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//    	token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
    	return zjXmCqjxDisciplineAssessmentService.getZjXmCqjxDisciplineLeaderTodoCount(token,"0");
    }
    
	/**
	 * 员工协作性考核待办数量
	 */
	@RequestMapping(value = "/apiZjCqjxCollaborationTodoCount", method = RequestMethod.GET)  
    public ResponseEntity apiZjCqjxCollaborationTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
    	String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//    	token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
    	return zjXmCqjxCollaborationAssessmentService.selectZjXmCqjxCollaborationTodoCount(token);
    }
    
}
