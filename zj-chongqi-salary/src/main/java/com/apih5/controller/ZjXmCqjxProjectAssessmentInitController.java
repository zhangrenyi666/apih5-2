package com.apih5.controller;

import java.util.List;

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
import com.apih5.mybatis.dao.ZjXmCqjxProjectSetupAdminMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSetupPersonnelMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupAdmin;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupPersonnel;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentService;
import com.apih5.service.ZjXmCqjxProjectExecutiveAssistantService;
import com.apih5.service.ZjXmCqjxProjectOverallEvaluationAssistantService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@RestController
public class ZjXmCqjxProjectAssessmentInitController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	private Apih5Properties apih5Properties;

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantService zjXmCqjxProjectExecutiveAssistantService;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentService zjXmCqjxProjectDisciplineAssessmentService;

    @Autowired(required = true)
    private ZjXmCqjxProjectOverallEvaluationAssistantService zjXmCqjxProjectOverallEvaluationAssistantService;

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupAdminMapper zjXmCqjxProjectSetupAdminMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupPersonnelMapper zjXmCqjxProjectSetupPersonnelMapper;
    
	/**
	 * 管理员设置
	 */
	@RequestMapping(value = "/initOaZjCqjxProSetAdminList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProSetAdminList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/setUpAdmin.html"+"?code=" + token);
		}
	}
	
	/**
	 * 纪律性考核管理员设置
	 */
	@RequestMapping(value = "/initOaZjCqjxProSetPersonnelList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProSetPersonnelList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/setUpPersonnel.html"+"?code=" + token);
		}
	}
	
	/**
	 * 部门负责人设置
	 */
	@RequestMapping(value = "/initOaZjCqjxProDeptLeaderList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProDeptLeaderList(HttpServletRequest request) {
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxProjectSetupAdmin member = new ZjXmCqjxProjectSetupAdmin();
    		member.setOaUserId(userKey);
			List<ZjXmCqjxProjectSetupAdmin> memberList = zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(member);
			if(memberList.size() == 0){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
			}	
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/listout.html"+"?code=" + token);
		}
	}
	
	/**
	 * 绩效考核计划
	 */
	@RequestMapping(value = "/initOaZjCqjxProManageList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProManageList(HttpServletRequest request) {
		String userId = "";
		try {
			DesUtil des = new DesUtil("yongan_sys");
			userId  = des.decrypt(request.getParameter("userAccount"));
			LoggerUtils.printLogger(logger, "getDocWorkList 开始 "+userId);			
		} catch (Exception e) {
		}		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			LoggerUtils.printLogger(logger, "getDocWorkList 开始 " + "11111");
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxProjectSetupAdmin member = new ZjXmCqjxProjectSetupAdmin();
    		member.setOaUserId(userKey);
			List<ZjXmCqjxProjectSetupAdmin> memberList = zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(member);
			if(memberList.size() == 0){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
			}			
			LoggerUtils.printLogger(logger, "getDocWorkList 开始 " + "222222");
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/Ministerout.html"+"?code=" + token);
		}
	}
	
	/**
	 * 部门正、副职季度考核填报
	 */
	@RequestMapping(value = "/initOaZjCqjxProDeptLeaderAssistantList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProDeptLeaderAssistantList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/DepartmentChief.html"+"?code=" + token);
		}
	}
	
	/**
	 * 副总师、经理助理半年考核填报
	 */
	@RequestMapping(value = "/initOaZjCqjxProExecutiveAssistantList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProExecutiveAssistantList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/ExecutiveAssistant.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工季度考核填报
	 */
	@RequestMapping(value = "/initOaZjCqjxProStaffAssistantList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProStaffAssistantList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/StaffQuarters.html"+"?code=" + token);
		}
	}
	
	/**
	 * 部门正、副职季度计划填报
	 */
	@RequestMapping(value = "/initOaZjCqjxProDeptLeaderAssistantPlanList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProDeptLeaderAssistantPlanList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/DepartmentChiefPlan.html"+"?code=" + token);
		}
	}
	
	/**
	 * 副总师、经理助理半年计划填报
	 */
	@RequestMapping(value = "/initOaZjCqjxProExecutiveAssistantPlanList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProExecutiveAssistantPlanList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/ExecutiveAssistantPlan.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工季度计划填报
	 */
	@RequestMapping(value = "/initOaZjCqjxProStaffAssistantPlanList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProStaffAssistantPlanList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/StaffQuartersPlan.html"+"?code=" + token);
		}
	}
	
	/**
	 * 事项待办
	 */
	@RequestMapping(value = "/initOaZjCqjxProLeaderToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProLeaderToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/LeadTheBacklogList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 总体评价考核待办
	 */
	@RequestMapping(value = "/initOaZjCqjxProEvaluationToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProEvaluationToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/LeadEvaluationList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工纪律性考评
	 */
	@RequestMapping(value = "/initOaZjCqjxProDisciplineList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProDisciplineList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxProjectSetupPersonnel member = new ZjXmCqjxProjectSetupPersonnel();
    		member.setOaUserId(userKey);
			List<ZjXmCqjxProjectSetupPersonnel> memberList = zjXmCqjxProjectSetupPersonnelMapper.selectByZjXmCqjxProjectSetupPersonnelList(member);
			if(memberList.size() == 0){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
			}		
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/discipline.html"+"?code=" + token);
		}
	}
	
	
	/**
	 * 总体评价管理
	 */
	@RequestMapping(value = "/initOaZjCqjxProEvaluationList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProEvaluationList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxProjectSetupAdmin member = new ZjXmCqjxProjectSetupAdmin();
    		member.setOaUserId(userKey);
			List<ZjXmCqjxProjectSetupAdmin> memberList = zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(member);
			if(memberList.size() == 0){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
			}		
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/evaluationList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工协作性考评
	 */
	@RequestMapping(value = "/initOaZjCqjxProCollaborationList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProCollaborationList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/interoperability.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工互评
	 */
	@RequestMapping(value = "/initOaZjCqjxProMutualEvaluationList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProMutualEvaluationList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/mutualEvaluation.html"+"?code=" + token);
		}
	}
	
	/**
	 * 员工考核统计
	 */
	@RequestMapping(value = "/initOaZjCqjxProMutualStaticList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProMutualStaticList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/deptHeaderList.html"+"?code=" + token);
		}
	}
	
	/**
	 * 纪律性流程
	 */
	@RequestMapping(value = "/initOaZjCqjxProDisciplineToDoList", method = RequestMethod.GET)
	public ModelAndView initOaZjCqjxProDisciplineToDoList(HttpServletRequest request) {
		
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());
		if(StrUtil.isEmpty(token)){
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
		}
		else {
//    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
//    		JSONObject json = new JSONObject(tokenDecrypt);
//    		String userKey = (String) json.get("userKey");
//    		ZjXmCqjxProjectSetupAdmin member = new ZjXmCqjxProjectSetupAdmin();
//    		member.setOaUserId(userKey);
//			List<ZjXmCqjxProjectSetupAdmin> memberList = zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(member);
//			if(memberList.size() == 0){
//			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/error.html");
//			}				
			return new ModelAndView("redirect:http://10.11.240.2:88/projectAssessment/LeadDisciplineList.html"+"?code=" + token);
		}
	}
    
	/**
	 * 领导班子副职考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProExecutiveAssistantTodoCount", method = RequestMethod.GET)    
    public ResponseEntity apiZjXmCqjxProExecutiveAssistantTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15b2807a9f51a7fb51b603d9f7f2f432f66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad122dcf764335caade295a3f9be02974f19b641362678f17adbaa8cfbbc5aa0f6fd8c7467c548435f148aa8b4c529baa6614";	
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"1","0","1");
    }
	
	/**
	 * 项目中层干部考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProDeptLeaderAssistantTodoCount", method = RequestMethod.GET)    
    public ResponseEntity apiZjXmCqjxProDeptLeaderAssistantTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"2","0","1");
    }
    
	/**
	 * 员工考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProStaffAssistantTodoCount", method = RequestMethod.GET)    	
    public ResponseEntity apiZjXmCqjxProStaffAssistantTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"3","0","1");
    }
	/**
	 * 领导班子副职任务待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProExecutiveAssistantPlanTodoCount", method = RequestMethod.GET)    
	public ResponseEntity apiZjXmCqjxProExecutiveAssistantPlanTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c1c1b5509993f7fa7b2a8033d31b659b6666cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0e1c974b6dbf49bfd679f2218d05daedb8a49b7ef855ac7fdecd3d9e07c3b1ed9aa8b4c529baa6614";	
		return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"1","0","0");
	}
	
	/**
	 * 项目中层干部任务待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProDeptLeaderAssistantPlanTodoCount", method = RequestMethod.GET)    
	public ResponseEntity apiZjXmCqjxProDeptLeaderAssistantPlanTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
		return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"2","0","0");
	}
	
	/**
	 * 员工任务待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProStaffAssistantPlanTodoCount", method = RequestMethod.GET)    	
	public ResponseEntity apiZjXmCqjxProStaffAssistantPlanTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c15205ab0f1ca557d7b213afe50164663b66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0ce7a6f3b2058eabf3cb961076ac4b5b88c384c424465593cb4e935d86dd6d373aa8b4c529baa6614";	
		return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"3","0","0");
	}
    
	/**
	 * 领导任务考核审批待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProAssistantLeaderTodoCount", method = RequestMethod.GET)    		
    public ResponseEntity apiZjXmCqjxProAssistantLeaderTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c19e79e27899a537d51e496965d37ba50a66cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad12244d3ca6b36ae5cb0e98b7a7e9504de8fdee08f1e0fc91f6d501a855178fafe4cb346928e4cae7920aa8b4c529baa6614";	
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoCount(token,"0","1","");
    }
    
	/**
	 * 领导纪律性考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProDisciplineLeaderTodoCount", method = RequestMethod.GET)    	
    public ResponseEntity apiZjXmCqjxProDisciplineLeaderTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
		String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//		token = "b7055c4fe607ed7a0f18657f5dd1c6c190df3a09136754d1153c804d7b00de9366cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad122dcf764335caade29ad7bb67ee2ed5729f30a5d085d8215a708eb669ece32936234cc0f6c7f8c7f73aa8b4c529baa6614";	
        return zjXmCqjxProjectDisciplineAssessmentService.getZjXmCqjxDisciplineLeaderTodoCount(token,"1");
    }
    
	/**
	 * 员工纪律性考核待办数量
	 */
	@RequestMapping(value = "/apiZjXmCqjxProDisciplineTodoCount", method = RequestMethod.GET)  
    public ResponseEntity apiZjXmCqjxProDisciplineTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
    	String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//    	token = "b7055c4fe607ed7a0f18657f5dd1c6c1daa80413ddccc236bb7b598002e25b2566cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad1220b5453b4739bbcfd04c8125235e44aa119ccbd0c9242508dc0e7b9e552213d5df19175fa59a3c67eaa8b4c529baa6614";	
    	return zjXmCqjxProjectDisciplineAssessmentService.getZjXmCqjxDisciplineLeaderTodoCount(token,"0");
    }
    
	/**
	 * 总体评价考核待办数量
	 */
	@RequestMapping(value = "/apiZjCqjxProEvaluationTodoCount", method = RequestMethod.GET)  
    public ResponseEntity apiZjCqjxProEvaluationTodoCount(HttpServletRequest request) {
//		String token = request.getParameter("token");
    	String token = BusinessZj.getTokenByZjOa(request.getParameter("userAccount"), apih5Properties.getDefaultPassword());	
//    	token = "b7055c4fe607ed7a0f18657f5dd1c6c190df3a09136754d1153c804d7b00de9366cd18d64da1d142fbea0d8344171e0905a0e48c8dfbb5115d8a41670f6ce846253663eae0fa469f67239049b5125527e6cffb530364033159f8e06c1a1ad122dcf764335caade29ad7bb67ee2ed5729f30a5d085d8215a708eb669ece32936234cc0f6c7f8c7f73aa8b4c529baa6614";	
    	return zjXmCqjxProjectOverallEvaluationAssistantService.selectZjXmCqjxOverallEvaluationAssistantTodoCount(token);
    }
    
}
