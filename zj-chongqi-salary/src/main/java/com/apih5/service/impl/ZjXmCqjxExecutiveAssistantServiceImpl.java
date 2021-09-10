package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.zjoa.common.BusinessZj;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDeptLeaderAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxOaDeptMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxOaLeaderMapper;
import com.apih5.mybatis.dao.ZjXmCqjxStaffAssistantDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxYearAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.mybatis.pojo.ZjXmCqjxOaLeader;
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;
import com.apih5.service.ZjXmCqjxExecutiveAssistantService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.text.DecimalFormat;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@Service("zjXmCqjxExecutiveAssistantService")
public class ZjXmCqjxExecutiveAssistantServiceImpl implements ZjXmCqjxExecutiveAssistantService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxOaLeaderMapper zjXmCqjxOaLeaderMapper;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantDetailedMapper zjXmCqjxExecutiveAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxDeptLeaderAssistantDetailedMapper zjXmCqjxDeptLeaderAssistantDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxStaffAssistantDetailedMapper zjXmCqjxStaffAssistantDetailedMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxOaDeptMemberMapper zjXmCqjxOaDeptMemberMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

	@Autowired
	private SysDepartmentService sysDepartmentService;

    @Autowired(required = true)
    private ZjXmCqjxAssessmentManageMapper zjXmCqjxAssessmentManageMapper;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMapper zjXmCqjxCollaborationAssessmentMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMemberMapper zjXmCqjxCollaborationAssessmentMemberMapper;

	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;

	@Autowired
	private UserService userService;

    @Autowired(required = true)
    private ZjXmCqjxAssistantLeaderApprovalMapper zjXmCqjxAssistantLeaderApprovalMapper;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssistantLeaderApprovalMapper zjXmCqjxDisciplineAssistantLeaderApprovalMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxYearAssistantMapper zjXmCqjxYearAssistantMapper;

	@Override
	public ResponseEntity checkZjXmCqjxAssistantScoreQualified(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
		ZjXmCqjxExecutiveAssistant executiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(StrUtil.equals("3", executiveAssistant.getAssessmentType())) {
        	ZjXmCqjxStaffAssistantDetailed assistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
            assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(assistantDetailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();          	
            if((sum<50.0)) {
            	return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
            }            	
        }else if(StrUtil.equals("2", executiveAssistant.getAssessmentType())){
        	ZjXmCqjxDeptLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
            assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(assistantDetailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();          	
            if((sum<50.0)) {
            	return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
            }     
        }else {
        	ZjXmCqjxExecutiveAssistantDetailed assistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
            assistantDetailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(assistantDetailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();        	
            if((sum<50.0)) {
            	return repEntity.layerMessage("NO", "考核分数没有达到标准分，请修改！");
            }  
        }
		return repEntity.ok(zjXmCqjxExecutiveAssistant);
	}
	
    @Override
    public ResponseEntity getZjXmCqjxExecutiveAssistantListByCondition(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        if (zjXmCqjxExecutiveAssistant == null) {
            zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);    
        zjXmCqjxExecutiveAssistant.setCreateUser(userKey);
        // 分页查询
        PageHelper.startPage(zjXmCqjxExecutiveAssistant.getPage(),zjXmCqjxExecutiveAssistant.getLimit());
        // 获取数据
        if(StrUtil.isNotEmpty(zjXmCqjxExecutiveAssistant.getYears())) {
        	zjXmCqjxExecutiveAssistant.setAssessmentYears(DateUtil.parse(zjXmCqjxExecutiveAssistant.getYears(), "yyyy"));
        }
        List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = new ArrayList<ZjXmCqjxExecutiveAssistant>();
        if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssistantFlag(), "0")) {
         zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant);        	
        }else {        	
         zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectZjXmCqjxExecutiveAssistantListByList(zjXmCqjxExecutiveAssistant);
        }
        for(ZjXmCqjxExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
    		head.setDepartmentId(assistant.getDepartmentId());
    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
    		if(headList.size()>0) {
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    			detail.setOtherId(headList.get(0).getDepartmentHeadId());
    			detail.setOtherType("1");
    			List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);    
    			if(detailList.size() == 0) {
    				assistant.setHaveChangerLeader("1");
    			}else {
    				assistant.setHaveChangerLeader("0");    				
    			}
    		}
        	if(!StrUtil.equals(assistant.getState(), "2")) {
        		assistant.setScoreClosingEndDate(null);
        	}
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);

        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetails(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        if (zjXmCqjxExecutiveAssistant == null) {
            zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
        }
        // 获取数据
        ZjXmCqjxExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        // 数据存在
        if (dbZjXmCqjxExecutiveAssistant != null) {
            return repEntity.ok(dbZjXmCqjxExecutiveAssistant);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    
    public String checkContent(String executiveId) {
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveId);
        if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
        	ZjXmCqjxExecutiveAssistantDetailed detailed = new ZjXmCqjxExecutiveAssistantDetailed();
        	detailed.setExecutiveId(executiveId);
        	List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(detailed); 
        	boolean bool = detailedList.stream().anyMatch(m -> m.getWorkPlan() == null || m.getWorkPlan().equals(""));
            if(bool) {
            	return "工作计划不能为空，请修改！";
            }            
            bool = detailedList.stream().anyMatch(m -> m.getAssessmentScore() == 0);
            if(bool) {
            	return "计划考核分数不能为0，请修改！";
            }             
            if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
            	bool = detailedList.stream().anyMatch(m -> m.getCompletion() == null || m.getCompletion().equals(""));
                if(bool) {
                	return "完成情况不能为空，请完善！";
                }                       	
            }
        }else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
        	ZjXmCqjxDeptLeaderAssistantDetailed detailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
        	detailed.setExecutiveId(executiveId);        	
        	List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
        	boolean bool = detailedList.stream().anyMatch(m -> m.getWorkTarget() == null || m.getWorkTarget().equals(""));
            if(bool) {
            	return "工作计划不能为空，请修改！";
            }             
            bool = detailedList.stream().anyMatch(m -> m.getWorkPlan() == null || m.getWorkPlan().equals(""));
            if(bool) {
            	return "工作计划不能为空，请修改！";
            } 
            bool = detailedList.stream().anyMatch(m -> m.getAssessmentScore() == 0);
            if(bool) {
            	return "计划考核分数不能为0，请修改！";
            }   
            if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
            	bool = detailedList.stream().anyMatch(m -> m.getCompletion() == null || m.getCompletion().equals(""));
                if(bool) {
                	return "完成情况不能为空，请完善！";
                }                       	
            }            
        }else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        	ZjXmCqjxStaffAssistantDetailed detailed = new ZjXmCqjxStaffAssistantDetailed();
        	detailed.setExecutiveId(executiveId);
        	List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
        	boolean bool = detailedList.stream().anyMatch(m -> m.getWorkTarget() == null || m.getWorkTarget().equals(""));
            if(bool) {
            	return "工作计划目标不能为空，请修改！";
            }             
            bool = detailedList.stream().anyMatch(m -> m.getWorkPlan() == null || m.getWorkPlan().equals(""));
            if(bool) {
            	return "工作计划目标不能为空，请修改！";
            }
            bool = detailedList.stream().anyMatch(m -> m.getAssessmentScore() == 0);
            if(bool) {
            	return "计划考核分数不能为0，请修改！";
            }   
            if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
            	bool = detailedList.stream().anyMatch(m -> m.getCompletion() == null || m.getCompletion().equals(""));
                if(bool) {
                	return "完成情况不能为空，请完善！";
                }                       	
            }
        }    	
    	return "0";
    }
	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        String result = checkContent(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(!StrUtil.equals(result, "0")) {
        	return repEntity.layerMessage("NO", result);
        }
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
            ZjXmCqjxExecutiveAssistantDetailed detailed = new ZjXmCqjxExecutiveAssistantDetailed();
            detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(detailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();
            if((sum<50.0)) {
            	return repEntity.layerMessage("NO", "考核分数不得小于满分50，请修改！");
            }else if((sum>50.0)){
            	return repEntity.layerMessage("NO", "考核分数不得大于满分50，请修改！");
            }
        }else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
            ZjXmCqjxDeptLeaderAssistantDetailed detailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
            detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            detailed.setWorkType("0");
            List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
            if(sum<40.0) {
            	return repEntity.layerMessage("NO", "任务业绩不得小于满分40，请修改！");
            }else if(sum>40.0){
            	return repEntity.layerMessage("NO", "任务业绩不得大于满分40，请修改！");
            }else {
                detailed.setWorkType("1");
                detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
                sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
                if(sum<10.0) {
                	return repEntity.layerMessage("NO", "部门业绩不得小于满分10，请修改！");
                }else if(sum>10.0){
                	return repEntity.layerMessage("NO", "部门业绩不得大于满分10，请修改！");
                }
            }         	
        }else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        	ZjXmCqjxStaffAssistantDetailed detailed = new ZjXmCqjxStaffAssistantDetailed();
            detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            detailed.setWorkType("0");
            List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
            if(sum<25.0) {
            	return repEntity.layerMessage("NO", "任务业绩不得小于满分25，请修改！");
            }else if(sum>25.0){
            	return repEntity.layerMessage("NO", "任务业绩不得大于满分25，请修改！");
            }else {
                detailed.setWorkType("1");
                detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
                sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
                if(sum<25.0) {
                	return repEntity.layerMessage("NO", "日常工作任务不得小于满分25，请修改！");
                }else if(sum>25.0){
                	return repEntity.layerMessage("NO", "日常工作任务不得大于满分25，请修改！");
                }
            }           	
        }  
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
           dbzjXmCqjxExecutiveAssistant.setPosition(zjXmCqjxExecutiveAssistant.getPosition());
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
        }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String sendUserKey = "";
        String sendId = "";
        String state = "";
        String result = checkContent(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(!StrUtil.equals(result, "0")) {
        	return repEntity.layerMessage("NO", result);
        }        
        int flag = 0;
        //分数校验
        if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
            ZjXmCqjxExecutiveAssistantDetailed detailed = new ZjXmCqjxExecutiveAssistantDetailed();
            detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(detailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();
            if((sum<50.0)) {
            	return repEntity.layerMessage("NO", "考核分数不得小于满分50，请修改！");
            }else if((sum>50.0)){
            	return repEntity.layerMessage("NO", "考核分数不得大于满分50，请修改！");
            }
        }else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
            ZjXmCqjxDeptLeaderAssistantDetailed detailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
            detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            detailed.setWorkType("0");
            List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
            if(sum<40.0) {
            	return repEntity.layerMessage("NO", "任务业绩不得小于满分40，请修改！");
            }else if(sum>40.0){
            	return repEntity.layerMessage("NO", "任务业绩不得大于满分40，请修改！");
            }else {
                detailed.setWorkType("1");
                detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
                sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
                if(sum<10.0) {
                	return repEntity.layerMessage("NO", "部门业绩不得小于满分10，请修改！");
                }else if(sum>10.0){
                	return repEntity.layerMessage("NO", "部门业绩不得大于满分10，请修改！");
                }
            }         	
        }else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        	ZjXmCqjxStaffAssistantDetailed detailed = new ZjXmCqjxStaffAssistantDetailed();
            detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            detailed.setWorkType("0");
            List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
            double sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
            if(sum<25.0) {
            	return repEntity.layerMessage("NO", "任务业绩不得小于满分25，请修改！");
            }else if(sum>25.0){
            	return repEntity.layerMessage("NO", "任务业绩不得大于满分25，请修改！");
            }else {
                detailed.setWorkType("1");
                detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
                sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
                if(sum<25.0) {
                	return repEntity.layerMessage("NO", "日常工作任务不得小于满分25，请修改！");
                }else if(sum>25.0){
                	return repEntity.layerMessage("NO", "日常工作任务不得大于满分25，请修改！");
                }
            }           	
        }       
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(!StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "0") && !StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2") && !StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
        	return repEntity.layerMessage("NO", "请勿重复发起！");
        }
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
        	//如果是部门负责人
        	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
				ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
				//如果是重新发起
				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("0");
					List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					for(int i=0; i<zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
						ZjXmCqjxAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList.get(i);
						if(i == 0) {
							sendUserKey = approval.getLeaderId();    						
							approval.setApprovalFlag("1");
							approval.setLeaderOption("");
						}else {
							approval.setApprovalFlag("0");    	
							approval.setLeaderOption("");							
						}
						approval.setModifyUserInfo(sendUserKey, realName);
						zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
					}
					state = "1";    					
				}
//				else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")){
//					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
////					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
//					zjXmCqjxAssistantLeaderApproval.setOtherType("0");
//					List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
//					for(int i=0; i<zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
//						ZjXmCqjxAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList.get(i);
//						if(i == 0) {
//							sendUserKey = approval.getLeaderId();    						
//							approval.setApprovalFlag("1");
//						}else {
//							approval.setApprovalFlag("0");    						
//						}
//						approval.setModifyUserInfo(sendUserKey, realName);
//						zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//					}
//					state = "1";    
//				}
				else {
					//首次发起
		    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
		    		head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
		    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail.setOtherId(headList.get(0).getDepartmentHeadId());
    				detail.setOtherType("0");
    				List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
    				if(detailList.size()>0) {
    					for(int i = 0; i<detailList.size(); i++) {
        					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
        					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        					zjXmCqjxAssistantLeaderApproval.setOtherType("0");
        					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
        					zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
        					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
        					zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
        					if(i == 0) {
        						sendUserKey = detailList.get(i).getOaUserId();
        						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
        					}else {
        						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
        					}
        					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
        					zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
    					}
    					state = "1";
    				}    					
				}        		
        	}else {
        		//如果是部门正、副职/副总师、经理助理
				ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
				//重新发起
				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
					List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					if(zjXmCqjxAssistantLeaderApprovalList.size()>0) {
						for(int i=0; i<zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
							ZjXmCqjxAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList.get(i);
							if(i == 0) {
								sendUserKey = approval.getLeaderId();    						
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							}else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
						state = "1";    							
					}
				}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")){
					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
					List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
					if(zjXmCqjxAssistantLeaderApprovalList.size()>0) {
						for(int i=0; i<zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
							ZjXmCqjxAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList.get(i);
							if(i == 0) {
								sendUserKey = approval.getLeaderId();    						
								approval.setApprovalFlag("1");
								approval.setLeaderOption("");
							}else {
								approval.setApprovalFlag("0");
								approval.setLeaderOption("");
							}
							approval.setModifyUserInfo(sendUserKey, realName);
							zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
						}
						state = "3";    							
					}					
				}else {
					//首次发起
		    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
		    		head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
		    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail.setOtherId(headList.get(0).getDepartmentHeadId());
    				detail.setOtherType("1");
    				List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
    				if(detailList.size()>0) {
    					for(int i = 0; i<detailList.size(); i++) {
        					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
        					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
        					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
        					zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
        					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
        					zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
        					if(i == 0) {
        						sendUserKey = detailList.get(i).getOaUserId();
        						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
        					}else {
        						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
        					}
        					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
        					zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
    					}
    					state = "1";
    				} else {
        				detail = new ZjXmCqjxDepartmentHeadDetail();
        				detail.setOtherId(headList.get(0).getDepartmentHeadId());
        				detail.setOtherType("2");
        				detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);    
        				if(detailList.size()>0) {
        					for(int i = 0; i<detailList.size(); i++) {
            					zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
            					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
            					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
            					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
            					zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
            					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
            					zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
            					if(i == 0) {
            						sendUserKey = detailList.get(i).getOaUserId();
            						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
            					}else {
            						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
            					}
            					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
            					zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
        					}
        					state = "3";        					
        				}
    				}
				}          		
        	}
        	
			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
				dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
				dbzjXmCqjxExecutiveAssistant.setAssessmentState(state);
				dbzjXmCqjxExecutiveAssistant.setLeaderSee("0");
			}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
				dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");
			}
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
           if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
   			//如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
   			ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
   			assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
   			assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
   			assistant.setAssessmentState("0");
   			List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
   			if(assistantList.size()== 0) {
   				assistant.setAssessmentState(state);
   				assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
   				for(ZjXmCqjxExecutiveAssistant ass : assistantList) {
   					ass.setLeaderSee("1");
   					ass.setModifyUserInfo(userKey, realName);
   					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(ass);					
   				}
   				if(assistantList.size()>0) {
   					ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
   	   	   			//给领导发送消息
   					String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程审批";
				    SysUser user = userService.getSysUserByUserKey(sendUserKey);
				    sendId = user.getUserId();   					
//   					sendId = "haiwei_xichengjian_test";
   					weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   					
   				}	   				
   			}  
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
        }
	}
//	@Override
//	@Transactional(rollbackFor = Exception.class)	
//	public ResponseEntity zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
//		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//		String userKey = TokenUtils.getUserKey(request);
//		String realName = TokenUtils.getRealName(request);
//		String sendUserKey = "";
//		String sendId = "";
//		String state = "";
//		String result = checkContent(zjXmCqjxExecutiveAssistant.getExecutiveId());
//		if(!StrUtil.equals(result, "0")) {
//			return repEntity.layerMessage("NO", result);
//		}        
//		int flag = 0;
//		//分数校验
//		if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
//			ZjXmCqjxExecutiveAssistantDetailed detailed = new ZjXmCqjxExecutiveAssistantDetailed();
//			detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//			List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(detailed);
//			double sum = detailedList.stream().mapToDouble(ZjXmCqjxExecutiveAssistantDetailed::getAssessmentScore).sum();
//			if((sum<50.0)) {
//				return repEntity.layerMessage("NO", "考核分数不得小于满分50，请修改！");
//			} 
//		}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
//			ZjXmCqjxDeptLeaderAssistantDetailed detailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
//			detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//			List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(detailed);
//			double sum = detailedList.stream().mapToDouble(ZjXmCqjxDeptLeaderAssistantDetailed::getAssessmentScore).sum();
//			if((sum<40.0)) {
//				return repEntity.layerMessage("NO", "考核分数不得小于满分40，请修改！");
//			}         	
//		}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//			ZjXmCqjxStaffAssistantDetailed detailed = new ZjXmCqjxStaffAssistantDetailed();
//			detailed.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//			List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(detailed);
//			double sum = detailedList.stream().mapToDouble(ZjXmCqjxStaffAssistantDetailed::getAssessmentScore).sum();
//			if((sum<50.0)) {
//				return repEntity.layerMessage("NO", "考核分数不得小于满分50，请修改！");
//			}          	
//		}       
//		ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
//		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
//			ZjXmCqjxOaDeptMember member = new ZjXmCqjxOaDeptMember();
//			member.setOtherId(dbzjXmCqjxExecutiveAssistant.getManagerId());
//			member.setOaUserId(dbzjXmCqjxExecutiveAssistant.getCreateUser());
//			List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(member);
////    		List<SysDepartment> deptList = sysDepartmentService.getSysDepartmentByUserkey(dbzjXmCqjxExecutiveAssistant.getCreateUser());
//			ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//			head.setDepartmentId(memberList.get(0).getOaOrgId());
//			List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
//			for(ZjXmCqjxDepartmentHead deptHead : headList) {
//				//员工考核审批人不一样，单拉出来写
//				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//					ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//					if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
//						zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("3");
//						zjXmCqjxAssistantLeaderApproval.setOtherType("0");
//						List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
//						for(int i=0; i<zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
//							ZjXmCqjxAssistantLeaderApproval approval = zjXmCqjxAssistantLeaderApprovalList.get(i);
//							if(i == 0) {
//								sendUserKey = approval.getLeaderId();    						
//								approval.setApprovalFlag("1");
//							}else {
//								approval.setApprovalFlag("0");    						
//							}
//							approval.setModifyUserInfo(sendUserKey, realName);
//							zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//						}
//						state = "1";    					
//					}else {
//						ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
//						detail.setOtherId(deptHead.getDepartmentHeadId());
//						detail.setOtherType("0");
//						List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
//						if(detailList.size()>0) {
//							for(int i = 0; i<detailList.size(); i++) {
//								zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//								zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
//								zjXmCqjxAssistantLeaderApproval.setOtherType("0");
//								zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//								zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
//								zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
//								zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
//								if(i == 0) {
//									sendUserKey = detailList.get(i).getOaUserId();
//									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//								}else {
//									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//								}
//								zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
//								zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
//							}
//							state = "1";
//						}    					
//					}
//				}else {
//					ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//					if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
//						zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
////    					zjXmCqjxAssistantLeaderApproval.setApprovalFlag("2");
//						zjXmCqjxAssistantLeaderApproval.setOtherType("1");
//						List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
//						for(int i = 0; i<zjXmCqjxAssistantLeaderApprovalList.size(); i++) {
//							zjXmCqjxAssistantLeaderApproval = zjXmCqjxAssistantLeaderApprovalList.get(i);
//							if(i == 0) {
//								sendUserKey = zjXmCqjxAssistantLeaderApprovalList.get(i).getLeaderId();    							
//								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//							}else {
//								zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//							}
//							zjXmCqjxAssistantLeaderApproval.setLeaderOption("");
//							zjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
//							zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(zjXmCqjxAssistantLeaderApproval);    	    						
//						}
////    					for(ZjXmCqjxAssistantLeaderApproval approval : zjXmCqjxAssistantLeaderApprovalList) {
////    						sendUserKey = approval.getLeaderId();
////    						approval.setApprovalFlag("1");
////    						approval.setModifyUserInfo(sendUserKey, realName);
////    						zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
////    					}
//						state = "1";
//					}else {
//						ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
//						detail.setOtherId(deptHead.getDepartmentHeadId());
//						detail.setOtherType("1");
//						List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
//						if(detailList.size()>0) {
//							for(int i = 0; i<detailList.size(); i++) {
//								zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//								zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
//								zjXmCqjxAssistantLeaderApproval.setOtherType("1");
//								zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//								zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
//								zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
//								zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
//								if(i == 0) {
//									sendUserKey = detailList.get(i).getOaUserId();
//									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//								}else {
//									zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//								}
//								zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
//								zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
//							}
//							state = "1";
//						}
//						//如果分管领导为空，则跳到主管领导
//						else {
//							ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();    				
//							approval.setOtherType("2");
//							approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//							List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//							if(approvalList.size()>0) {    
//								for(int i = 0; i<approvalList.size(); i++) {
//									approval = approvalList.get(i);
//									if(i == 0) {
//										sendUserKey = approval.getLeaderId();           	        					
//										approval.setApprovalFlag("1");
//									}else {
//										approval.setApprovalFlag("0");
//									}
//									approval.setModifyUserInfo(sendUserKey, realName);
//									zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//								}
//							}else {
//								detail = new ZjXmCqjxDepartmentHeadDetail();
//								detail.setOtherId(deptHead.getDepartmentHeadId());
//								detail.setOtherType("2");
//								detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
//								if(detailList.size()>0) {
//									for(int i = 0; i<detailList.size(); i++) {
//										zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//										zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
//										zjXmCqjxAssistantLeaderApproval.setOtherType("2");
//										zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
//										if(i == 0) {
//											sendUserKey = detailList.get(i).getOaUserId();
//											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//										}else {
//											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//										}
//										zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
//										zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
//									}
//								} 
//							}
//							state = "3";
//						}
//					}
//				}			
//			}     	
//			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
////				dbzjXmCqjxExecutiveAssistant.setChargeLeaderId(zjXmCqjxOaLeader.getOaUserId());
//				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");
//				dbzjXmCqjxExecutiveAssistant.setAssessmentState(state);
//				dbzjXmCqjxExecutiveAssistant.setLeaderSee("0");
//			}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
////				dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderId(zjXmCqjxOaLeader.getOaUserId());
//				dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");
//			}
//			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
//			flag = zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
//			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
//				//如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
//				ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
//				assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
//				assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
//				assistant.setAssessmentState("0");
//				List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
//				if(assistantList.size()== 0) {
//					assistant.setAssessmentState(state);
//					assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
//					for(ZjXmCqjxExecutiveAssistant ass : assistantList) {
//						ass.setLeaderSee("1");
//						ass.setModifyUserInfo(userKey, realName);
//						zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(ass);					
//					}
//					if(assistantList.size()>0) {
//						ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
//						//给领导发送消息
//						String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程审批";
//						SysUser user = userService.getSysUserByUserKey(sendUserKey);
//						sendId = user.getUserId();   					
////   					sendId = "haiwei_xichengjian_test";
//						weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   					
//					}	   				
//				}  
//			}
//		}
//		// 失败
//		if (flag == 0) {
//			return repEntity.errorSave();
//		}
//		else {
//			return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
//		}
//	}

	@Override
	public ResponseEntity getZjXmCqjxExecutiveAssistantTodoList(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxExecutiveAssistant.setCreateUser(userKey);
        zjXmCqjxExecutiveAssistant.setLeaderSee("1");
        // 分页查询
        PageHelper.startPage(zjXmCqjxExecutiveAssistant.getPage(),zjXmCqjxExecutiveAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectLeaderTodoListByUserKey(zjXmCqjxExecutiveAssistant);
        for(ZjXmCqjxExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
    		head.setDepartmentId(assistant.getDepartmentId());
    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
    		if(headList.size()>0) {
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    			detail.setOtherId(headList.get(0).getDepartmentHeadId());
    			detail.setOtherType("1");
    			List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);    
    			if(detailList.size() == 0) {
    				assistant.setHaveChangerLeader("1");
    			}else {
    				assistant.setHaveChangerLeader("0");    				
    			}
    		}        	
        	if(!StrUtil.equals(assistant.getState(), "2")) {
        		assistant.setFirstScoreClosingEndDate(null);
        	}
        	
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());        
	}

	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxAssistantChargeLeaderApproval(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);    
        String sendId = "";
        String sendUserKey = "";
		ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
		if(!StrUtil.equals(approval.getApprovalFlag(), "1")) {
			return repEntity.layerMessage("NO", "已经审批过了！");
		}
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
    		//分管领导退回
    		if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "1")) {
        		approval = new ZjXmCqjxAssistantLeaderApproval();
        		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
        		approval.setApprovalFlag("2");
        		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);        		
//    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
        		dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
        				zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));    
//    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
    			dbzjXmCqjxExecutiveAssistant.setAssessmentState("2");        
        	}else {
            	//更新当前数据
        		approval = new ZjXmCqjxAssistantLeaderApproval();
        		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
        		approval.setApprovalFlag("3");
        		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);     
    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
    					zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));      
        		//如果是员工类别，当前部门负责人
            	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
    				approval = new ZjXmCqjxAssistantLeaderApproval();
    				approval.setOtherType("0");
        			approval.setApprovalFlag("0");					
    				approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
            		//同级别审批完成，查看下一级别
            		if(approvalList.size()>0) {
    	        		for(int i = 0; i<approvalList.size(); i++) {
    	        			approval = approvalList.get(i);
        					if(i == 0) {
        						sendUserKey = approvalList.get(i).getLeaderId();
        						approval.setApprovalFlag("1");
        						approval.setLeaderOption("");
        					}else {
        						approval.setApprovalFlag("0");
        						approval.setLeaderOption("");        						
        					}
        					approval.setCreateUserInfo(userKey, realName);
        					zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
    	        		}
            		}else {
            		//查询下一审批人，分管领导
        			approval.setOtherType("1");
        			approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
        			if(approvalList.size()>0) {
    	        		for(int i = 0; i<approvalList.size(); i++) {
    	        			approval = approvalList.get(i);
        					if(i == 0) {
        						sendUserKey = approvalList.get(i).getLeaderId();
        						approval.setApprovalFlag("1");
        						approval.setLeaderOption("");
        					}else {
        						approval.setApprovalFlag("0");
        						approval.setLeaderOption("");
        					}
        					approval.setCreateUserInfo(userKey, realName);
        					zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
    	        		}
    	    			dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");        
        			}else {
        				//领导表查询下一节点--分管领导
                		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
                		head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
                		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
                		for(ZjXmCqjxDepartmentHead deptHead : headList) {
            				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
            				detail.setOtherId(deptHead.getDepartmentHeadId());
            				detail.setOtherType("1");
            				List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
            				//如果当前分管领导为空，需要去审批表里查询主管领导是否存在
            				if(detailList.size()>0) {
            					for(int i = 0; i<detailList.size(); i++) {
                					ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
                					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
                					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                					zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
                					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
                					zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
                					if(i == 0) {
                						sendUserKey = detailList.get(i).getOaUserId();
                						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
                					}else {
                						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
                					}
                					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
                					zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
            					}            			
                		}else {
                    		//查询主管领导，没有则去领导表查询数据插入审批表
                			approval.setOtherType("2");
                			approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
                			if(approvalList.size()>0) {
            	        		for(int i = 0; i<approvalList.size(); i++) {
            	        			approval = approvalList.get(i);
                					if(i == 0) {
                						sendUserKey = approvalList.get(i).getLeaderId();
                						approval.setApprovalFlag("1");
                						approval.setLeaderOption("");
                					}else {
                						approval.setApprovalFlag("0");
                						approval.setLeaderOption("");
                					}
                					approval.setCreateUserInfo(userKey, realName);
                					zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
            	        		}	
                			}else {
                				detail = new ZjXmCqjxDepartmentHeadDetail();
                				detail.setOtherId(deptHead.getDepartmentHeadId());
                				detail.setOtherType("2");
                				detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
                				if(detailList.size()>0) {
                					for(int i = 0; i<detailList.size(); i++) {
                    					ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                    					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
                    					zjXmCqjxAssistantLeaderApproval.setOtherType("1");
                    					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                    					zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
                    					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
                    					zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
                    					if(i == 0) {
                    						sendUserKey = detailList.get(i).getOaUserId();
                    						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
                    					}else {
                    						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
                    					}
                    					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
                    					zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
                					}             				
                			}

            				}
                		}
            		}
//                		dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));               		
                		dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");        				
            			}
            		}
            	}else {
                //如果是副总师、部门正、副职页面
    			approval = new ZjXmCqjxAssistantLeaderApproval();
    			approval.setOtherType("1");
    			approval.setApprovalFlag("0");					
    			approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
        		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
        		//同级别审批完成，查看下一级别
        		if(approvalList.size()>0) {
            		for(int i = 0; i<approvalList.size(); i++) {
            			approval = approvalList.get(i);
    					if(i == 0) {
    						sendUserKey = approvalList.get(i).getLeaderId();
    						approval.setApprovalFlag("1");
    						approval.setLeaderOption("");
    					}else {
    						approval.setApprovalFlag("0");
    						approval.setLeaderOption("");
    					}
    					approval.setModifyUserInfo(sendUserKey, realName);
    					zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
            		}
        		}else {
        		//查询下一审批人，分管领导
    			approval.setOtherType("2");
    			approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
    			if(approvalList.size()>0) {
            		for(int i = 0; i<approvalList.size(); i++) {
            			approval = approvalList.get(i);
    					if(i == 0) {
    						sendUserKey = approvalList.get(i).getLeaderId();
    						approval.setApprovalFlag("1");
    						approval.setLeaderOption("");
    					}else {
    						approval.setApprovalFlag("0");
    						approval.setLeaderOption("");
    					}
    					approval.setCreateUserInfo(userKey, realName);
    					zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
            		}	
    			}else {
    				//领导表查询下一节点--分管领导
            		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
            		head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
            		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
            		for(ZjXmCqjxDepartmentHead deptHead : headList) {
        				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
        				detail.setOtherId(deptHead.getDepartmentHeadId());
        				detail.setOtherType("2");
        				List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        				//如果当前分管领导为空，需要去审批表里查询主管领导是否存在
        				if(detailList.size()>0) {
        					for(int i = 0; i<detailList.size(); i++) {
            					ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
            					zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
            					zjXmCqjxAssistantLeaderApproval.setOtherType("2");
            					zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
            					zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
            					zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
            					zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
            					if(i == 0) {
            						sendUserKey = detailList.get(i).getOaUserId();
            						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
            					}else {
            						zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
            					}
            					zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
            					zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
        					}            			
            		}
        		}
        			}
//    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//    					zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));               		
    			dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");  
    			dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
        		}        		
            	}        		
        	}
    		
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
  			//如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
  			ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
  			assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
  			assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
  			assistant.setAssessmentState("1");
  			List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
  			if(assistantList.size() == 0) {
  				assistant.setAssessmentState("2");
  	  			assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
  	  			if(assistantList.size() == 0) {
  	  				assistant.setAssessmentState("3");
  	  				assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
  	  				for(ZjXmCqjxExecutiveAssistant ass : assistantList) {
  	  					ass.setLeaderSee("1");
  	  					ass.setModifyUserInfo(userKey, realName);
  	  					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(ass);					
  	  				}
  	  				if(assistantList.size()>0) {
  	  					ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
  	  	   	   			//给领导发送消息
  	  					String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFinalDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程审批";
  						SysUser user = userService.getSysUserByUserKey(sendUserKey);
  						sendId = user.getUserId();
//  	  					sendId = "haiwei_xichengjian_test";
  	  					weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   					
  	  				}	  	  				
  	  			}   				
  			} 
        }        
		return repEntity.ok(zjXmCqjxExecutiveAssistant);
	}
//	@Override
//	@Transactional(rollbackFor = Exception.class)	
//	public ResponseEntity zjXmCqjxAssistantChargeLeaderApproval(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
//		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//		String userKey = TokenUtils.getUserKey(request);
//		String realName = TokenUtils.getRealName(request);    
//		String sendId = "";
//		String sendUserKey = "";
//		ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
//		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {        	
//			//同意        	
//			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "0")) {
//				ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
//				approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
//				approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
//				approval.setApprovalFlag("3");
//				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//					//如果是主管退回状态
//					if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
//						approval = new ZjXmCqjxAssistantLeaderApproval();
//						approval.setOtherType("0");
//						approval.setApprovalFlag("0");					
//						approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//						List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//						if(approvalList.size()>0) {
//							for(int i = 0; i<approvalList.size(); i++) {
//								approval = approvalList.get(i);
//								if(i == 0) {
//									sendUserKey = approvalList.get(i).getLeaderId();
//									approval.setApprovalFlag("1");
//								}else {
//									approval.setApprovalFlag("0");
//								}
//								approval.setCreateUserInfo(userKey, realName);
//								zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
//							}	  	        			
//						}else {
//							approval.setOtherType("1");
//							approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//							approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//							for(int i = 0; i<approvalList.size(); i++) {
//								approval = approvalList.get(i);
//								if(i == 0) {
//									sendUserKey = approvalList.get(i).getLeaderId();
//									approval.setApprovalFlag("1");
//								}else {
//									approval.setApprovalFlag("0");
//								}
//								approval.setCreateUserInfo(userKey, realName);
//								zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
//							}	        			
//							dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
//							dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");    
//						}
//					}else {
//						approval = new ZjXmCqjxAssistantLeaderApproval();    				
//						approval.setOtherType("0");
//						approval.setApprovalFlag("0");
//						approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//						List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//						if(approvalList.size()>0) {
//							approval = approvalList.get(0);
//							approval.setApprovalFlag("1");
//							approval.setModifyUserInfo(userKey, realName);
//							zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//							dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//									zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));             			
//						}else {
//							ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//							head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
//							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
//							for(ZjXmCqjxDepartmentHead deptHead : headList) {
//								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
//								detail.setOtherId(deptHead.getDepartmentHeadId());
//								detail.setOtherType("1");
//								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
//								if(detailList.size()>0) {
//									for(int i = 0; i<detailList.size(); i++) {
//										ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//										zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
//										zjXmCqjxAssistantLeaderApproval.setOtherType("1");
//										zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
//										if(i == 0) {
//											sendUserKey = detailList.get(i).getOaUserId();
//											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//										}else {
//											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//										}
//										zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
//										zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
//									}            			
//								}else {
//									detail = new ZjXmCqjxDepartmentHeadDetail();
//									detail.setOtherId(deptHead.getDepartmentHeadId());
//									detail.setOtherType("2");
//									detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
//									if(detailList.size()>0) {
//										for(int i = 0; i<detailList.size(); i++) {
//											ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//											zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
//											zjXmCqjxAssistantLeaderApproval.setOtherType("1");
//											zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//											zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
//											zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
//											zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
//											if(i == 0) {
//												sendUserKey = detailList.get(i).getOaUserId();
//												zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//											}else {
//												zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//											}
//											zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
//											zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
//										} 
//									}
//								}
//							}
//							dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//									zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));               		
//							dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");
//						}    					
//					}
//				}else {
//					//如果是主管退回状态
//					if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "4")) {
//						approval = new ZjXmCqjxAssistantLeaderApproval();
//						approval.setOtherType("1");
//						approval.setApprovalFlag("0");					
//						approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//						List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//						if(approvalList.size()>0) {
//							for(int i = 0; i<approvalList.size(); i++) {
//								approval = approvalList.get(i);
//								if(i == 0) {
//									sendUserKey = approvalList.get(i).getLeaderId();
//									approval.setApprovalFlag("1");
//								}else {
//									approval.setApprovalFlag("0");
//								}
//								approval.setCreateUserInfo(userKey, realName);
//								zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
//							}	  	        			
//						}else {
//							approval.setOtherType("2");
//							approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//							approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//							for(int i = 0; i<approvalList.size(); i++) {
//								approval = approvalList.get(i);
//								if(i == 0) {
//									sendUserKey = approvalList.get(i).getLeaderId();
//									approval.setApprovalFlag("1");
//								}else {
//									approval.setApprovalFlag("0");
//								}
//								approval.setCreateUserInfo(userKey, realName);
//								zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);  
//							}	        			
//							dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption("");
//							dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");    
//						}
//					}else {
//						approval = new ZjXmCqjxAssistantLeaderApproval();
//						approval.setOtherType("1");
//						approval.setApprovalFlag("0");
//						approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//						List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//						if(approvalList.size()>0) {
//							approval = approvalList.get(0);
//							approval.setApprovalFlag("1");
//							approval.setModifyUserInfo(userKey, realName);
//							zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
////	    				dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");   	        			
//						}else {
//							ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//							head.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
//							List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
//							for(ZjXmCqjxDepartmentHead deptHead : headList) {
//								ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
//								detail.setOtherId(deptHead.getDepartmentHeadId());
//								detail.setOtherType("2");
//								List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
//								if(detailList.size()>0) {
//									for(int i = 0; i<detailList.size(); i++) {
//										ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
//										zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
//										zjXmCqjxAssistantLeaderApproval.setOtherType("2");
//										zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
//										zjXmCqjxAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
//										if(i == 0) {
//											sendUserKey = detailList.get(i).getOaUserId();
//											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("1");
//										}else {
//											zjXmCqjxAssistantLeaderApproval.setApprovalFlag("0");
//										}
//										zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
//										zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);    						
//									}            			
//								}
//							}	
//							dbzjXmCqjxExecutiveAssistant.setAssessmentState("3");    
//						}
//					}    	
//					dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//							zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));    				
////				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
//					
//				}			
//			}
//			//退回
//			else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "1")) {
//				ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
//				approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
//				approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
//				approval.setApprovalFlag("3");
//				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);        		
////    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
//				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getChargeLeaderOption(),
//						zjXmCqjxExecutiveAssistant.getChargeLeaderOption()));    
////    			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
//				dbzjXmCqjxExecutiveAssistant.setAssessmentState("2");           		
//			}
//			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
//			zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
//			//如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
//			ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
//			assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
//			assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
//			assistant.setAssessmentState("1");
//			List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
//			if(assistantList.size() == 0) {
//				assistant.setAssessmentState("2");
//				assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
//				if(assistantList.size() == 0) {
//					assistant.setAssessmentState("3");
//					assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
//					for(ZjXmCqjxExecutiveAssistant ass : assistantList) {
//						ass.setLeaderSee("1");
//						ass.setModifyUserInfo(userKey, realName);
//						zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(ass);					
//					}
//					if(assistantList.size()>0) {
//						ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
//						//给领导发送消息
//						String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFinalDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程审批";
//						SysUser user = userService.getSysUserByUserKey(sendUserKey);
//						sendId = user.getUserId();
//						sendId = "haiwei_xichengjian_test";
//						weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   					
//					}	  	  				
//				}   				
//			} 
//		}        
//		return repEntity.ok(zjXmCqjxExecutiveAssistant);
//	}

	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxAssistantExecutiveLeaderApproval(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);     
        String backType = "";
		ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
		backType = approval.getOtherType();
		if(!StrUtil.equals(approval.getApprovalFlag(), "1")) {
			return repEntity.layerMessage("NO", "已经审批过了！");
		}
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
        	//退回
        	if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "1")) {
        	    approval = new ZjXmCqjxAssistantLeaderApproval();
        		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
        		approval.setApprovalFlag("2");
        		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);   
        		if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        			//退回到部门负责人
            		approval = new ZjXmCqjxAssistantLeaderApproval();    				
        			approval.setOtherType("0");
        			approval.setApprovalFlag("3");
    	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
            		if(approvalList.size()>0) {
                		if(approvalList.size()>0) {
                			for(int i = 0; i<approvalList.size(); i++) {
                				approval = approvalList.get(i);
                				if(i == 0) {
                					approval.setApprovalFlag("1");
                					approval.setLeaderOption("");
                				}else {
                					approval.setApprovalFlag("0");
                					approval.setLeaderOption("");                					
                				}
                				approval.setModifyUserInfo(userKey, realName);
                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
                		}
                		}
                		approval = new ZjXmCqjxAssistantLeaderApproval();    				
            			approval.setOtherType(backType);
        	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
                		approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
                		if(approvalList.size()>0) {
                			for(int i = 0; i<approvalList.size(); i++) {
                				approval = approvalList.get(i);
                				approval.setApprovalFlag("0");        				
                				approval.setModifyUserInfo(userKey, realName);
                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
                		}
                		}        		
                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");           		
            			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");                    			
            		}else {
            			//如果没有，则直接回到发起人手里
                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");                       			
            		}
        		}else {
        			//副总师、部门正、副职
            		approval = new ZjXmCqjxAssistantLeaderApproval();    				
        			approval.setOtherType("1");
        			approval.setApprovalFlag("3");
    	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
            		if(approvalList.size()>0) {
                		if(approvalList.size()>0) {
                			for(int i = 0; i<approvalList.size(); i++) {
                				approval = approvalList.get(i);
                				if(i == 0) {
                					approval.setApprovalFlag("1");
                					approval.setLeaderOption("");
                				}else {
                					approval.setApprovalFlag("0");
                					approval.setLeaderOption("");
                				}
                				approval.setModifyUserInfo(userKey, realName);
                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
                		}
                		}
                		approval = new ZjXmCqjxAssistantLeaderApproval();    				
            			approval.setOtherType("2");
        	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
                		approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
                		if(approvalList.size()>0) {
                			for(int i = 0; i<approvalList.size(); i++) {
                				approval = approvalList.get(i);
                				approval.setApprovalFlag("0");        				
                				approval.setModifyUserInfo(userKey, realName);
                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
                		}
                		}        		
                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");           		
            			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");                    			
            		}else {
                		approval = new ZjXmCqjxAssistantLeaderApproval();
                		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
                		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
                		approval.setApprovalFlag("2");
                		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
            			//如果没有，则直接回到发起人手里
                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");                       			
            		}        			
        		}
        	}else {
        	//如果是员工
        	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        	    approval = new ZjXmCqjxAssistantLeaderApproval();
        		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
        		approval.setApprovalFlag("3");
        		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
    			approval = new ZjXmCqjxAssistantLeaderApproval();    				
    			approval.setOtherType("1");
    			approval.setApprovalFlag("0");
	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
        		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
        		if(approvalList.size()>0) {
        			approval = approvalList.get(0);
        			approval.setApprovalFlag("1");
        			approval.setModifyUserInfo(userKey, realName);
        			zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
            		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
            				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
        		}else {
            		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
            				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
        			dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");    
        		}          		
        	}else {
        	//如果是部门正、副职、副总师
        		approval = new ZjXmCqjxAssistantLeaderApproval();
        		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
        		approval.setApprovalFlag("3");
        		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
    			approval = new ZjXmCqjxAssistantLeaderApproval();    				
    			approval.setOtherType("2");
    			approval.setApprovalFlag("0");
	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
        		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
        		if(approvalList.size()>0) {
        			approval = approvalList.get(0);
        			approval.setApprovalFlag("1");
        			approval.setModifyUserInfo(userKey, realName);
        			zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
            		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
            				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
        		}else {
            		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
            				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
        			dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");    
        		}        		
        	}
        	}
        	
        	
//        	//员工分管领导审核
//        	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//            	if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "0")) { 
//            	    approval = new ZjXmCqjxAssistantLeaderApproval();
//            		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
//            		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
//            		approval.setApprovalFlag("3");
//            		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//        			approval = new ZjXmCqjxAssistantLeaderApproval();    				
//        			approval.setOtherType("1");
//        			approval.setApprovalFlag("0");
//    	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//            		if(approvalList.size()>0) {
//            			approval = approvalList.get(0);
//            			approval.setApprovalFlag("1");
//            			approval.setModifyUserInfo(userKey, realName);
//            			zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
//            		}else {
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
//            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");    
//            		}            		
//            	}else {
//            		approval = new ZjXmCqjxAssistantLeaderApproval();    				
//        			approval.setOtherType("1");
//        			approval.setApprovalFlag("3");
//    	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//            		if(approvalList.size()>0) {
//                		if(approvalList.size()>0) {
//                			for(int i = 0; i<approvalList.size(); i++) {
//                				approval = approvalList.get(i);
//                				if(i == 0) {
//                					approval.setApprovalFlag("1");
//                				}else {
//                					approval.setApprovalFlag("0");
//                				}
//                				approval.setModifyUserInfo(userKey, realName);
//                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
//                		}
//                		}
//                		approval = new ZjXmCqjxAssistantLeaderApproval();    				
//            			approval.setOtherType("2");
//        	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//                		approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//                		if(approvalList.size()>0) {
//                			for(int i = 0; i<approvalList.size(); i++) {
//                				approval = approvalList.get(i);
//                				approval.setApprovalFlag("0");        				
//                				approval.setModifyUserInfo(userKey, realName);
//                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
//                		}
//                		}        		
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
//            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");           		
//            			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");                    			
//            		}else {
//            			//如果没有，则直接回到发起人手里
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
//            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");                       			
//            		}
//            	}
//        	}else {
//        	//部门正、副职，副总师、经理助理考核
//            	if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "0")) { 
//            		approval = new ZjXmCqjxAssistantLeaderApproval();
//            		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
//            		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
//            		approval.setApprovalFlag("3");
//            		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//        			approval = new ZjXmCqjxAssistantLeaderApproval();    				
//        			approval.setOtherType("2");
//        			approval.setApprovalFlag("0");
//    	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//            		if(approvalList.size()>0) {
//            			approval = approvalList.get(0);
//            			approval.setApprovalFlag("1");
//            			approval.setModifyUserInfo(userKey, realName);
//            			zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
//            		}else {
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
//            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");    
//            		}            		
//            	}else {
//            		approval = new ZjXmCqjxAssistantLeaderApproval();    				
//        			approval.setOtherType("1");
//        			approval.setApprovalFlag("3");
//    	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//            		List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//            		if(approvalList.size()>0) {
//                		if(approvalList.size()>0) {
//                			for(int i = 0; i<approvalList.size(); i++) {
//                				approval = approvalList.get(i);
//                				if(i == 0) {
//                					approval.setApprovalFlag("1");
//                				}else {
//                					approval.setApprovalFlag("0");
//                				}
//                				approval.setModifyUserInfo(userKey, realName);
//                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
//                		}
//                		}
//                		approval = new ZjXmCqjxAssistantLeaderApproval();    				
//            			approval.setOtherType("2");
//        	        	approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//                		approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//                		if(approvalList.size()>0) {
//                			for(int i = 0; i<approvalList.size(); i++) {
//                				approval = approvalList.get(i);
//                				approval.setApprovalFlag("0");        				
//                				approval.setModifyUserInfo(userKey, realName);
//                				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
//                		}
//                		}        		
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
//            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");           		
//            			dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");                    			
//            		}else {
//                		approval = new ZjXmCqjxAssistantLeaderApproval();
//                		approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
//                		approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
//                		approval.setApprovalFlag("2");
//                		zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//            			//如果没有，则直接回到发起人手里
//                		dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//                				zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
//            			dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");                       			
//            		}
//            	}        		
//        	}
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
        }        
		return repEntity.ok(zjXmCqjxExecutiveAssistant);
	}
//	@Override
//	@Transactional(rollbackFor = Exception.class)	
//	public ResponseEntity zjXmCqjxAssistantExecutiveLeaderApproval(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
//		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//		String userKey = TokenUtils.getUserKey(request);
//		String realName = TokenUtils.getRealName(request);     
//		String type = "";
//		String backType = "";
//		ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
//		if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
//			//同意        	
//			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "0")) { 
//				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//					type = "1";
//				}else {
//					type = "2";
//				}
//				ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();
//				approval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
//				approval.setLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
//				approval.setApprovalFlag("3");
//				zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//				approval = new ZjXmCqjxAssistantLeaderApproval();    				
//				approval.setOtherType(type);
//				approval.setApprovalFlag("0");
//				approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//				List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//				if(approvalList.size()>0) {
//					approval = approvalList.get(0);
//					approval.setApprovalFlag("1");
//					approval.setModifyUserInfo(userKey, realName);
//					zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
//					dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//							zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
//				}else {
////        			dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
//					dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//							zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));           			
//					dbzjXmCqjxExecutiveAssistant.setAssessmentState("5");    
//				}
//			}
//			//退回
//			else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getApprovalFlag(), "1")) {
//				if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
//					type = "0";
//					backType = "1";        			
//				}else {
//					type = "1";
//					backType = "2";
//				}
//				ZjXmCqjxAssistantLeaderApproval approval = new ZjXmCqjxAssistantLeaderApproval();    				
//				approval.setOtherType(type);
//				approval.setApprovalFlag("3");
//				approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//				List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//				if(approvalList.size()>0) {
//					for(int i = 0; i<approvalList.size(); i++) {
//						approval = approvalList.get(i);
//						if(i == 0) {
//							approval.setApprovalFlag("1");
//						}else {
//							approval.setApprovalFlag("0");
//						}
//						approval.setModifyUserInfo(userKey, realName);
//						zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
//					}
//				}
//				approval = new ZjXmCqjxAssistantLeaderApproval();    				
//				approval.setOtherType(backType);
//				approval.setExecutiveId(zjXmCqjxExecutiveAssistant.getExecutiveId());
//				approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(approval);
//				if(approvalList.size()>0) {
//					for(int i = 0; i<approvalList.size(); i++) {
//						approval = approvalList.get(i);
//						approval.setApprovalFlag("0");        				
//						approval.setModifyUserInfo(userKey, realName);
//						zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(approval);
//					}
//				}        		
//				dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxExecutiveAssistant.getExecutiveLeaderOption(),
//						zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption()));        		
////    			dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
//				dbzjXmCqjxExecutiveAssistant.setAssessmentState("4");           		
//				dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption("");           		
//			}
//			dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
//			zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
//		}        
//		return repEntity.ok(zjXmCqjxExecutiveAssistant);
//	}

	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxAssistantExecutiveLeaderScore(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        double taskScore = 0;//任务得分
        double quarterScore = 0;//任务得分
        String leaderScore = "";
        String taskFlag = "0";
        //设置权重
//   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
//   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
   	    BigDecimal executiveScore;//明细得分
   	    BigDecimal executiveScoreSum = null;//明细得分合计
   	    BigDecimal cooperationScore = null;//协作性得分
   	    BigDecimal disciplineScore = null;//纪律性得分
   	    BigDecimal quarterScoreDem = null;//纪律性得分
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        //得到审批数据
        ZjXmCqjxAssistantLeaderApproval dbApproval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
        	if(StrUtil.equals(dbApproval.getApprovalFlag(), "5")) {
        		return repEntity.layerMessage("NO", "已经评分过了");
        	}
        	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
            	//如果还有主管领导没有评分完毕，则选择主管领导
                ZjXmCqjxAssistantLeaderApproval leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                leaderApproval.setOtherType("2");
                leaderApproval.setApprovalFlag("3");
                List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
                if(approvalList.size()>0) {
               	 for(int i = 0; i<approvalList.size(); i++) {
               		 leaderApproval = approvalList.get(i);
               		 if(i == 0) {        			 
               			 leaderApproval.setApprovalFlag("4");
               		 }
               		 leaderApproval.setModifyUserInfo(userKey, realName);
               		 zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval); 
               	 }
              	 ZjXmCqjxExecutiveAssistantDetailed assistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
                 assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                 List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(assistantDetailed);
         		for(ZjXmCqjxExecutiveAssistantDetailed detailed : detailedList) {
        			leaderScore = leaderScore+detailed.getExecutiveLeaderScore()+",";
        			detailed.setExecutiveLeaderScore(0);
        			detailed.setModifyUserInfo(userKey, realName);
        			zjXmCqjxExecutiveAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        		}
           		dbApproval.setApprovalFlag("5");
           		dbApproval.setLeaderScore(leaderScore);
           		dbApproval.setModifyUserInfo(userKey, realName);
               	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
                }else {
                	taskFlag = "1";
               		ZjXmCqjxExecutiveAssistantDetailed exDetail = new ZjXmCqjxExecutiveAssistantDetailed();
               		exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
               		List<ZjXmCqjxExecutiveAssistantDetailed> exDetailList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(exDetail);
               		for(int i = 0; i<exDetailList.size(); i++) {
               			ZjXmCqjxExecutiveAssistantDetailed detailed = exDetailList.get(i);
               			leaderScore = leaderScore+detailed.getExecutiveLeaderScore()+",";
               		}
               		dbApproval.setApprovalFlag("5");
               		dbApproval.setLeaderScore(leaderScore);
               		dbApproval.setModifyUserInfo(userKey, realName);
                   	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
                   	//计算主管领导得分
                       leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                       leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                       leaderApproval.setApprovalFlag("5");
                       leaderApproval.setOtherType("2");      
                   approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
                   List<String[]> strArrList = new ArrayList<>();
                   for(ZjXmCqjxAssistantLeaderApproval app : approvalList) {
                 	  strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()-1).split(","));
                   }
                   double [] doubleStr = new double[approvalList.size()]; 
                   for(int k = 0; k<exDetailList.size(); k++) {
           			ZjXmCqjxExecutiveAssistantDetailed detailed = exDetailList.get(k);        	
           			for(int j = 0; j<strArrList.size(); j++) {
           				doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
           			}
           			//主管领导平均评分+乘以权重以后评分
           			double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
           			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getHaveChangerLeader(), "1")) {
           			//如果没有分管领导，则变换算法，不需要7:3，只是求平均数
               			detailed.setExecutiveScore(asDouble);
               			detailed.setExecutiveLeaderScore(asDouble);
               			detailed.setModifyUserInfo(userKey, realName);
               			zjXmCqjxExecutiveAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
           			}else {
               			BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
               			BigDecimal asDoubleDml = new BigDecimal(asDouble);
                        executiveScoreSum = asDoubleDml.multiply(executiveLeaderWeight); 
                        //获取分管领导平均得分+乘以权重以后评分
                        BigDecimal changerLeaderScore = new BigDecimal(exDetailList.get(k).getChargeLeaderScore());
                        BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
                        changerLeaderScore = changerLeaderScore.multiply(chargeLeaderWeight); 
                        //两个得分相加得出该计划最终季度得分
                        executiveScoreSum = changerLeaderScore.add(executiveScoreSum);
               			detailed.setExecutiveScore(executiveScoreSum.doubleValue());
               			detailed.setExecutiveLeaderScore(asDoubleDml.doubleValue());
               			detailed.setModifyUserInfo(userKey, realName);
               			zjXmCqjxExecutiveAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
           			}
                   }
                   ZjXmCqjxExecutiveAssistantDetailed  assistantDetailed = new ZjXmCqjxExecutiveAssistantDetailed();
                     assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                     //计算各项任务得分，其中分管领导占有7成，主管领导占有3成
                     List<ZjXmCqjxExecutiveAssistantDetailed> detailedList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(assistantDetailed);
                     executiveScoreSum = new BigDecimal(taskScore);
                     for(ZjXmCqjxExecutiveAssistantDetailed detailed : detailedList) {
                    	 executiveScore = new BigDecimal(detailed.getExecutiveScore());
                    	 executiveScoreSum = executiveScore.add(executiveScoreSum);       
                     }
                     taskScore = executiveScoreSum.doubleValue();
                     dbzjXmCqjxExecutiveAssistant.setTaskScore(taskScore);                        	
                }
        	}
        	
        	else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")) {
            	//如果还有分管领导没有评分完毕，则选择分管领导
                ZjXmCqjxAssistantLeaderApproval leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                leaderApproval.setOtherType("2");
                if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
             	   leaderApproval.setOtherType("1");
                }
                leaderApproval.setApprovalFlag("3");
                List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
                if(approvalList.size()>0) {
               	 for(int i = 0; i<approvalList.size(); i++) {
               		 leaderApproval = approvalList.get(i);
               		 if(i == 0) {        			 
               			 leaderApproval.setApprovalFlag("4");
               		 }
               		 leaderApproval.setModifyUserInfo(userKey, realName);
               		 zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval); 
               	 }
               	ZjXmCqjxDeptLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
                 assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                 //计算各项任务得分，其中分管领导占有7成，主管领导占有3成
                 List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(assistantDetailed);
         		for(ZjXmCqjxDeptLeaderAssistantDetailed detailed : detailedList) {
        			leaderScore = leaderScore+detailed.getExecutiveLeaderScore()+",";
        			detailed.setExecutiveLeaderScore(0);
        			detailed.setModifyUserInfo(userKey, realName);
        			zjXmCqjxDeptLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        		}
           		dbApproval.setApprovalFlag("5");
           		dbApproval.setLeaderScore(leaderScore);
           		dbApproval.setModifyUserInfo(userKey, realName);
               	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);        		
                }else {
                	taskFlag = "1";
                	ZjXmCqjxDeptLeaderAssistantDetailed exDetail = new ZjXmCqjxDeptLeaderAssistantDetailed();
               		exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
               		List<ZjXmCqjxDeptLeaderAssistantDetailed> exDetailList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(exDetail);
               		for(int i = 0; i<exDetailList.size(); i++) {
               			ZjXmCqjxDeptLeaderAssistantDetailed detailed = exDetailList.get(i);
               			leaderScore = leaderScore+detailed.getExecutiveLeaderScore()+",";
               		}
               		dbApproval.setApprovalFlag("5");
               		dbApproval.setLeaderScore(leaderScore);
               		dbApproval.setModifyUserInfo(userKey, realName);
                   	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
                   	//计算分管领导得分
                       leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                       leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                       leaderApproval.setApprovalFlag("5");
                       leaderApproval.setOtherType("2");
                      if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
                     	 leaderApproval.setOtherType("1");
                      }        
                   approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
                   List<String[]> strArrList = new ArrayList<>();
                   for(ZjXmCqjxAssistantLeaderApproval app : approvalList) {
                 	  strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()-1).split(","));
                   }
                   double [] doubleStr = new double[approvalList.size()]; 
                   for(int k = 0; k<exDetailList.size(); k++) {
                	   ZjXmCqjxDeptLeaderAssistantDetailed detailed = exDetailList.get(k);        	
           			for(int j = 0; j<strArrList.size(); j++) {
           				doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
           			}
           			//主管领导平均评分+乘以权重以后评分
           			double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
           			if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getHaveChangerLeader(), "1")) {
               			detailed.setExecutiveScore(asDouble);
               			detailed.setExecutiveLeaderScore(asDouble);
               			zjXmCqjxDeptLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);              				
           			}else {
               			BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
               			BigDecimal asDoubleDml = new BigDecimal(asDouble);
                        executiveScoreSum = asDoubleDml.multiply(executiveLeaderWeight); 
                        //获取分管领导平均得分+乘以权重以后评分
                        BigDecimal changerLeaderScore = new BigDecimal(exDetailList.get(k).getChargeLeaderScore());
                        BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
                        changerLeaderScore = changerLeaderScore.multiply(chargeLeaderWeight); 
                        //两个得分相加得出该计划最终季度得分
                        executiveScoreSum = changerLeaderScore.add(executiveScoreSum);
               			detailed.setExecutiveScore(executiveScoreSum.doubleValue());
               			detailed.setExecutiveLeaderScore(asDoubleDml.doubleValue());
               			zjXmCqjxDeptLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);           				
           			}
                   }
                	
                	ZjXmCqjxDeptLeaderAssistantDetailed assistantDetailed = new ZjXmCqjxDeptLeaderAssistantDetailed();
                     assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                     //计算各项任务得分，其中分管领导占有7成，主管领导占有3成
                     List<ZjXmCqjxDeptLeaderAssistantDetailed> detailedList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(assistantDetailed);
                     executiveScoreSum = new BigDecimal(taskScore);
                     for(ZjXmCqjxDeptLeaderAssistantDetailed detailed : detailedList) {
                    	 executiveScore = new BigDecimal(detailed.getExecutiveScore());
                    	 executiveScoreSum = executiveScore.add(executiveScoreSum);       
                     }
                     taskScore = executiveScoreSum.doubleValue();
                     dbzjXmCqjxExecutiveAssistant.setTaskScore(taskScore);                     	
                }   		
        	}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        		//如果还有分管领导没有评分完毕，则选择分管领导
                ZjXmCqjxAssistantLeaderApproval leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                leaderApproval.setOtherType("2");
                if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getHaveChangerLeader(), "1")) {
             	   leaderApproval.setOtherType("1");
                }
                leaderApproval.setApprovalFlag("3");
                List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
                if(approvalList.size()>0) {
               	 for(int i = 0; i<approvalList.size(); i++) {
               		 leaderApproval = approvalList.get(i);
               		 if(i == 0) {        			 
               			 leaderApproval.setApprovalFlag("4");
               		 }
               		 leaderApproval.setModifyUserInfo(userKey, realName);
               		 zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval); 
               	 }
               	ZjXmCqjxStaffAssistantDetailed assistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
                 assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                 //计算各项任务得分，其中分管领导占有7成，主管领导占有3成
                 List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(assistantDetailed);
         		for(ZjXmCqjxStaffAssistantDetailed detailed : detailedList) {
        			leaderScore = leaderScore+detailed.getExecutiveLeaderScore()+",";
        			detailed.setExecutiveLeaderScore(0);
        			detailed.setModifyUserInfo(userKey, realName);
        			zjXmCqjxStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        		}
        		dbApproval.setLeaderScore(leaderScore);
        		dbApproval.setApprovalFlag("5");
           		dbApproval.setModifyUserInfo(userKey, realName);
               	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);        		
                }else {
                	taskFlag = "1";
                	ZjXmCqjxStaffAssistantDetailed exDetail = new ZjXmCqjxStaffAssistantDetailed();
               		exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
               		List<ZjXmCqjxStaffAssistantDetailed> exDetailList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(exDetail);
               		for(int i = 0; i<exDetailList.size(); i++) {
               			ZjXmCqjxStaffAssistantDetailed detailed = exDetailList.get(i);
               			leaderScore = leaderScore+detailed.getExecutiveLeaderScore()+",";
               		}
//               		String score = leaderScore.substring(0, leaderScore.length()-1);
               		dbApproval.setApprovalFlag("5");
               		dbApproval.setLeaderScore(leaderScore);
               		dbApproval.setModifyUserInfo(userKey, realName);
                   	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
                   	//计算分管领导得分
                       leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
                       leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                       leaderApproval.setApprovalFlag("5");
                       leaderApproval.setOtherType("2");
                      if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
                     	 leaderApproval.setOtherType("1");
                      }        
                   approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
                   List<String[]> strArrList = new ArrayList<>();
                   for(ZjXmCqjxAssistantLeaderApproval app : approvalList) {
                 	  strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()-1).split(","));
                   }
                   double [] doubleStr = new double[approvalList.size()]; 
                   for(int k = 0; k<exDetailList.size(); k++) {
                	   ZjXmCqjxStaffAssistantDetailed detailed = exDetailList.get(k);        	
           			for(int j = 0; j<strArrList.size(); j++) {
           				doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
           			}
           			//主管领导平均评分+乘以权重以后评分
           			double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
           			BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
           			BigDecimal asDoubleDml = new BigDecimal(asDouble);
                    executiveScoreSum = asDoubleDml.multiply(executiveLeaderWeight); 
                    //获取分管领导平均得分+乘以权重以后评分
                    BigDecimal changerLeaderScore = new BigDecimal(exDetailList.get(k).getChargeLeaderScore());
                    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
                    changerLeaderScore = changerLeaderScore.multiply(chargeLeaderWeight); 
                    //两个得分相加得出该计划最终季度得分
                    executiveScoreSum = changerLeaderScore.add(executiveScoreSum);
           			detailed.setExecutiveScore(executiveScoreSum.doubleValue());
           			detailed.setExecutiveLeaderScore(asDoubleDml.doubleValue());
           			detailed.setModifyUserInfo(userKey, realName);
           			zjXmCqjxStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
                   }
                   
           		ZjXmCqjxStaffAssistantDetailed assistantDetailed = new ZjXmCqjxStaffAssistantDetailed();
                assistantDetailed.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
                //计算各项任务得分，其中分管领导占有7成，主管领导占有3成
                executiveScoreSum = new BigDecimal(taskScore);
                List<ZjXmCqjxStaffAssistantDetailed> detailedList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(assistantDetailed);
                for(ZjXmCqjxStaffAssistantDetailed detailed : detailedList) {
               	 executiveScore = new BigDecimal(detailed.getExecutiveScore());
               	 executiveScoreSum = executiveScore.add(executiveScoreSum);       
                }
                taskScore = executiveScoreSum.doubleValue();
                dbzjXmCqjxExecutiveAssistant.setTaskScore(taskScore);    
                }
        	}
        	if(StrUtil.equals(taskFlag, "1")) {
            	//更新协作性评分
            	ZjXmCqjxCollaborationAssessment collAssessMent = new ZjXmCqjxCollaborationAssessment();
            	collAssessMent.setCollaborationYears(dbzjXmCqjxExecutiveAssistant.getAssessmentYears());
            	collAssessMent.setCollaborationQuarter(dbzjXmCqjxExecutiveAssistant.getAssessmentQuarter());
            	List<ZjXmCqjxCollaborationAssessment> collAssessMentList = zjXmCqjxCollaborationAssessmentMapper.selectByZjXmCqjxCollaborationAssessmentList(collAssessMent);
            	if(collAssessMentList.size()>0 && StrUtil.equals(collAssessMentList.get(0).getCollaborationState(), "2")) {
            		ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
            		assessmentMember.setCollaborationId(collAssessMentList.get(0).getCollaborationId());
            		assessmentMember.setOaUserId(dbzjXmCqjxExecutiveAssistant.getCreateUser());
            		List<ZjXmCqjxCollaborationAssessmentMember> assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(assessmentMember);
            		if(assessmentMemberList.size()>0) {
            			dbzjXmCqjxExecutiveAssistant.setCooperationFlag("1");
            			dbzjXmCqjxExecutiveAssistant.setCooperationScore(assessmentMemberList.get(0).getAssessmentScore());
                        dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
                        zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);        			
            		}
            	}
                 //如果各项评分完成，则计算季度得分
                 if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getDisciplineFlag(), "1")) {
                	 if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
                		 //获取员工本季度得分
                       	cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
                      	disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
                      	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
                     	//得到部门系数
                     	ZjXmCqjxOaDeptMember member = new ZjXmCqjxOaDeptMember();
                     	member.setOtherId(dbzjXmCqjxExecutiveAssistant.getManagerId());
                     	member.setOaUserId(dbzjXmCqjxExecutiveAssistant.getCreateUser());
                     	List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(member);
                     	if(memberList.size()>0) {
                 			ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
                 			head.setDepartmentId(memberList.get(0).getOaOrgId());
                 			List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);   
                         	ZjXmCqjxExecutiveAssistant executiveAssistant = new ZjXmCqjxExecutiveAssistant();
                         	executiveAssistant.setYears(dbzjXmCqjxExecutiveAssistant.getYears());
                         	executiveAssistant.setAssessmentQuarter(dbzjXmCqjxExecutiveAssistant.getAssessmentQuarter());
                         	executiveAssistant.setCreateUser(headList.get(0).getUserKey());
                         	List<ZjXmCqjxExecutiveAssistant> executiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(executiveAssistant);
                         	if(executiveAssistantList.size()>0) {
                         		if(!StrUtil.equals(executiveAssistantList.get(0).getAssessmentState(), "8")) {
                         			quarterScoreDem = new BigDecimal(0);
                         		}else {
                             		BigDecimal deptLeaderScore = new BigDecimal(executiveAssistantList.get(0).getQuarterScore());
                             		BigDecimal weight = new BigDecimal(60);
                             		BigDecimal deptWeight = deptLeaderScore.divide(weight);
                             		quarterScoreDem.multiply(deptWeight);    
                             		dbzjXmCqjxExecutiveAssistant.setDeptCoefficient(deptWeight.doubleValue()+"");
                         		}
                         	}
                         	quarterScore = quarterScoreDem.doubleValue();
                         	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);                        	
                     	}
                	 }
                	 else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")){
                      	cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
                     	disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
                     	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
                     	quarterScore = quarterScoreDem.doubleValue();
                 		BigDecimal deptLeaderScore = new BigDecimal(quarterScore);
                 		BigDecimal weight = new BigDecimal(60);
                 		BigDecimal deptWeight = deptLeaderScore.divide(weight);   
                 		DecimalFormat df = new DecimalFormat("0.0");
                 		dbzjXmCqjxExecutiveAssistant.setDeptCoefficient(df.format(deptWeight));
                     	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);            		 
                	 }
                	 else {
                		 cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
                      	disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
                      	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
                      	quarterScore = quarterScoreDem.doubleValue();
                      	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
                	 }
                 }             
               // 考核状态
               dbzjXmCqjxExecutiveAssistant.setAssessmentState("8");
               dbzjXmCqjxExecutiveAssistant.setTaskFlag("1");
               // 共通
               dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
               zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);        		
        	}
        }
        return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
//        // 失败
//        if (flag == 0) {
//            return repEntity.errorSave();
//        }
//        else {
//        }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxAssistantChargeLeaderScore(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String sendId = "";
        String leaderScore = "";
        String sendUserKey = "";        
        //得到考核数据
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        //得到审批数据
        ZjXmCqjxAssistantLeaderApproval dbApproval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
        	if(StrUtil.equals(dbApproval.getApprovalFlag(), "5")) {
        		return repEntity.layerMessage("NO", "已经评分过了");
        	}
        	//如果还有分管领导没有评分完毕，则选择分管领导
           ZjXmCqjxAssistantLeaderApproval leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
           leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
           leaderApproval.setOtherType("1");
           if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        	   leaderApproval.setOtherType("0");
           }
           leaderApproval.setApprovalFlag("3");
         List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
         if(approvalList.size()>0) {
        	 for(int i = 0; i<approvalList.size(); i++) {
        		 leaderApproval = approvalList.get(i);
        		 if(i == 0) {        			 
        			 leaderApproval.setApprovalFlag("4");
        		 }
        		 leaderApproval.setModifyUserInfo(userKey, realName);
        		 zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval); 
        	 }
        	 //将得分写入到审批表中
         	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
        		ZjXmCqjxExecutiveAssistantDetailed exDetail = new ZjXmCqjxExecutiveAssistantDetailed();
        		exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
        		List<ZjXmCqjxExecutiveAssistantDetailed> exDetailList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(exDetail);
        		for(ZjXmCqjxExecutiveAssistantDetailed detailed : exDetailList) {
        			leaderScore = leaderScore+detailed.getChargeLeaderScore()+",";
        			detailed.setChargeLeaderScore(0);
        			detailed.setModifyUserInfo(userKey, realName);
        			zjXmCqjxExecutiveAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        		}
//        		String score = leaderScore.substring(0, leaderScore.length()-1);
        		dbApproval.setLeaderScore(leaderScore);
        	}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")){
        		ZjXmCqjxDeptLeaderAssistantDetailed deptDetail = new ZjXmCqjxDeptLeaderAssistantDetailed();
        		deptDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
        		List<ZjXmCqjxDeptLeaderAssistantDetailed> deptDetailList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(deptDetail);
        		for(ZjXmCqjxDeptLeaderAssistantDetailed detailed : deptDetailList) {
        			leaderScore = leaderScore+detailed.getChargeLeaderScore()+",";
        			detailed.setChargeLeaderScore(0);
        			detailed.setModifyUserInfo(userKey, realName);
        			zjXmCqjxDeptLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        		}
        		dbApproval.setLeaderScore(leaderScore);        		
        	}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        		ZjXmCqjxStaffAssistantDetailed staDetail = new ZjXmCqjxStaffAssistantDetailed();
        		staDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
        		List<ZjXmCqjxStaffAssistantDetailed> staDetailList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(staDetail);
        		for(ZjXmCqjxStaffAssistantDetailed detailed : staDetailList) {
        			leaderScore = leaderScore+detailed.getChargeLeaderScore()+",";
        			detailed.setChargeLeaderScore(0);
        			detailed.setModifyUserInfo(userKey, realName);
        			zjXmCqjxStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        		}
        		dbApproval.setLeaderScore(leaderScore);           		
        	}
         	dbApproval.setApprovalFlag("5");
         	dbApproval.setModifyUserInfo(userKey, realName);
        	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);          	 
         }else {
        	 //将审批发给主管领导
        	 leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
        	 leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
        	 leaderApproval.setOtherType("2");
             if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
            	 leaderApproval.setOtherType("1");
             }   
             leaderApproval.setApprovalFlag("3");
          approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);  
          for(int i = 0; i<approvalList.size(); i++) {
        	  leaderApproval = approvalList.get(i);
        	  if(i == 0) {
        		  sendUserKey = leaderApproval.getLeaderId();
        		  leaderApproval.setApprovalFlag("4"); 
        	  }
        	  leaderApproval.setModifyUserInfo(userKey, realName);
        	  zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(leaderApproval);
          }
          // 考核状态
          dbzjXmCqjxExecutiveAssistant.setAssessmentState("7");
          // 共通
          dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
          zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
          //将分管领导写入审批表中，并得出所有分管领导之和
      	if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "1")) {
    		ZjXmCqjxExecutiveAssistantDetailed exDetail = new ZjXmCqjxExecutiveAssistantDetailed();
    		exDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
    		List<ZjXmCqjxExecutiveAssistantDetailed> exDetailList = zjXmCqjxExecutiveAssistantDetailedMapper.selectByZjXmCqjxExecutiveAssistantDetailedList(exDetail);
    		for(int i = 0; i<exDetailList.size(); i++) {
    			ZjXmCqjxExecutiveAssistantDetailed detailed = exDetailList.get(i);
    			leaderScore = leaderScore+detailed.getChargeLeaderScore()+",";
    		}
//    		String score = leaderScore.substring(0, leaderScore.length()-1);
    		
    		dbApproval.setApprovalFlag("5");
    		dbApproval.setLeaderScore(dbApproval.getLeaderScore()+leaderScore);
    		dbApproval.setModifyUserInfo(userKey, realName);
        	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
        	//计算分管领导得分
            leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
            leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
            leaderApproval.setApprovalFlag("5");
            leaderApproval.setOtherType("1");
           if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
          	 leaderApproval.setOtherType("0");
           }        
        approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
        List<String[]> strArrList = new ArrayList<>();
        for(ZjXmCqjxAssistantLeaderApproval app : approvalList) {
      	  strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()-1).split(","));
        }
        double [] doubleStr = new double[approvalList.size()]; 
        for(int k = 0; k<exDetailList.size(); k++) {
			ZjXmCqjxExecutiveAssistantDetailed detailed = exDetailList.get(k);        	
			for(int j = 0; j<strArrList.size(); j++) {
				doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
			}
			double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
			detailed.setChargeLeaderScore(asDouble);
			detailed.setModifyUserInfo(userKey, realName);
			zjXmCqjxExecutiveAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        }
    	}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "2")){
    		ZjXmCqjxDeptLeaderAssistantDetailed deptDetail = new ZjXmCqjxDeptLeaderAssistantDetailed();
    		deptDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
    		List<ZjXmCqjxDeptLeaderAssistantDetailed> deptDetailList = zjXmCqjxDeptLeaderAssistantDetailedMapper.selectByZjXmCqjxDeptLeaderAssistantDetailedList(deptDetail);
    		for(int i = 0; i<deptDetailList.size(); i++) {
    			ZjXmCqjxDeptLeaderAssistantDetailed detailed = deptDetailList.get(i);
    			leaderScore = leaderScore+detailed.getChargeLeaderScore()+",";
    		}
    		String score = leaderScore.substring(0, leaderScore.length()-1);
    		dbApproval.setApprovalFlag("5");
    		dbApproval.setLeaderScore(score);
    		dbApproval.setModifyUserInfo(userKey, realName);
        	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
        	//计算分管领导得分
            leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
            leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
            leaderApproval.setApprovalFlag("5");
            leaderApproval.setOtherType("1");
           if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
          	 leaderApproval.setOtherType("0");
           }        
        approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
        List<String[]> strArrList = new ArrayList<>();
        for(ZjXmCqjxAssistantLeaderApproval app : approvalList) {
      	  strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()-1).split(","));
        }
        double [] doubleStr = new double[approvalList.size()]; 
        for(int k = 0; k<deptDetailList.size(); k++) {
        	ZjXmCqjxDeptLeaderAssistantDetailed detailed = deptDetailList.get(k);        	
			for(int j = 0; j<strArrList.size(); j++) {
				doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
			}
			double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
			detailed.setChargeLeaderScore(asDouble);
			detailed.setModifyUserInfo(userKey, realName);
			zjXmCqjxDeptLeaderAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);
        }   		
    	}else if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
    		ZjXmCqjxStaffAssistantDetailed staDetail = new ZjXmCqjxStaffAssistantDetailed();
    		staDetail.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
    		List<ZjXmCqjxStaffAssistantDetailed> staDetailList = zjXmCqjxStaffAssistantDetailedMapper.selectByZjXmCqjxStaffAssistantDetailedList(staDetail);
    		for(int i = 0; i<staDetailList.size(); i++) {
    			ZjXmCqjxStaffAssistantDetailed detailed = staDetailList.get(i);
    			leaderScore = leaderScore+detailed.getChargeLeaderScore()+",";
    		}
    		String score = leaderScore.substring(0, leaderScore.length()-1);
    		dbApproval.setApprovalFlag("5");
    		dbApproval.setLeaderScore(score);
    		dbApproval.setModifyUserInfo(userKey, realName);
        	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbApproval);
        	//计算分管领导得分
            leaderApproval = new ZjXmCqjxAssistantLeaderApproval();
            leaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
            leaderApproval.setApprovalFlag("5");
            leaderApproval.setOtherType("1");
           if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
          	 leaderApproval.setOtherType("0");
           }        
        approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(leaderApproval);
        List<String[]> strArrList = new ArrayList<>();
        for(ZjXmCqjxAssistantLeaderApproval app : approvalList) {
      	  strArrList.add(app.getLeaderScore().substring(0, app.getLeaderScore().length()-1).split(","));
        }
        double [] doubleStr = new double[approvalList.size()]; 
        for(int k = 0; k<staDetailList.size(); k++) {
        	ZjXmCqjxStaffAssistantDetailed detailed = staDetailList.get(k);        	
			for(int j = 0; j<strArrList.size(); j++) {
				doubleStr[j] = Double.parseDouble(strArrList.get(j)[k]);
			}
			double asDouble = Arrays.stream(doubleStr).average().getAsDouble();
			detailed.setChargeLeaderScore(asDouble);
			detailed.setModifyUserInfo(userKey, realName);
			zjXmCqjxStaffAssistantDetailedMapper.updateByPrimaryKeySelective(detailed);     		
    	}
         }  
         }
         }
		//如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
		assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
		assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
		assistant.setAssessmentState("6");
		List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
		if(assistantList.size()== 0) {
			assistant.setAssessmentState("7");
			assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
				if(assistantList.size()>0) {
					ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
	   	   			//给领导发送消息
					String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFinalScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程评分";
			        SysUser user = userService.getSysUserByUserKey(sendUserKey);
			        sendId = user.getUserId();   					
//					sendId = "haiwei_xichengjian_test";
					weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   					
				} 
		}
        return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity zjXmCqjxExecutiveScoreLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String sendId = "";
        String sendUserKey = "";
        String state = "";
        int flag = 0;
        String result = checkContent(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(!StrUtil.equals(result, "0")) {
        	return repEntity.layerMessage("NO", result);
        }        
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if(!StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
        	return repEntity.layerMessage("NO", "请勿重复发起！");
        }
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
//           dbzjXmCqjxExecutiveAssistant.setPosition(zjXmCqjxExecutiveAssistant.getPosition());
           ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
           zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
           zjXmCqjxAssistantLeaderApproval.setOtherType("1");
           if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
        	   zjXmCqjxAssistantLeaderApproval.setOtherType("0");
           }
           List<ZjXmCqjxAssistantLeaderApproval> approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
           if(approvalList.size()>0) {
               for(int i = 0; i<approvalList.size(); i++) {
            	   zjXmCqjxAssistantLeaderApproval = approvalList.get(i);
            	   if(i == 0) {
            		   sendUserKey = zjXmCqjxAssistantLeaderApproval.getLeaderId();
            		   zjXmCqjxAssistantLeaderApproval.setApprovalFlag("4");
            	   }
            	   zjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
            	   zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(zjXmCqjxAssistantLeaderApproval);
            	   dbzjXmCqjxExecutiveAssistant.setAssessmentState("6");
               }        	   
           }else {
        	   zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
               zjXmCqjxAssistantLeaderApproval.setExecutiveId(dbzjXmCqjxExecutiveAssistant.getExecutiveId());
               zjXmCqjxAssistantLeaderApproval.setOtherType("2");
               if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getAssessmentType(), "3")) {
            	   zjXmCqjxAssistantLeaderApproval.setOtherType("0");
               }
               approvalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
               if(approvalList.size()>0) {
                   for(int i = 0; i<approvalList.size(); i++) {
                	   zjXmCqjxAssistantLeaderApproval = approvalList.get(i);
                	   if(i == 0) {
                		   sendUserKey = zjXmCqjxAssistantLeaderApproval.getLeaderId();
                		   zjXmCqjxAssistantLeaderApproval.setApprovalFlag("4");
                	   }
                	   zjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
                	   zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(zjXmCqjxAssistantLeaderApproval);
                	   dbzjXmCqjxExecutiveAssistant.setAssessmentState("7");
                   }
               }
           }
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);           
        }
			//如果当前部门下所有人员都已经填报完毕，则发送到领导账号上
			ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
			assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
			assistant.setManagerId(dbzjXmCqjxExecutiveAssistant.getManagerId());
			assistant.setAssessmentState("5");
			List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
			if(assistantList.size()== 0) {
				assistant.setAssessmentState("6");
				assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
				for(ZjXmCqjxExecutiveAssistant ass : assistantList) {
					ass.setLeaderSee("1");
					ass.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(ass);					
				}
   				if(assistantList.size()>0) {
   					ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
   	   	   			//给领导发送消息
   					String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFirstScoreClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程评分";
				    SysUser user = userService.getSysUserByUserKey(sendUserKey);
				    sendId = user.getUserId();   					
//   					sendId = "haiwei_xichengjian_test";
   					weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   					
   				}					
			} else {
				assistant.setDepartmentId(dbzjXmCqjxExecutiveAssistant.getDepartmentId());
				assistant.setAssessmentState("");
				assistantList = zjXmCqjxExecutiveAssistantMapper.selectListByZjXmCqjxExecutiveAssistant(assistant);
				for(ZjXmCqjxExecutiveAssistant ass : assistantList) {
					ass.setLeaderSee("0");
					ass.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(ass);					
				}				
			}
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
        }
	}
	
    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxExecutiveAssistant(List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxExecutiveAssistantList != null && zjXmCqjxExecutiveAssistantList.size() > 0) {
           ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
           zjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantMapper.batchDeleteUpdateZjXmCqjxExecutiveAssistant(zjXmCqjxExecutiveAssistantList, zjXmCqjxExecutiveAssistant);
           //删除明细表数据
//           zjXmCqjxExecutiveAssistantDetailedMapper
//           zjXmCqjxDeptLeaderAssistantDetailedMapper
//           zjXmCqjxStaffAssistantDetailedMapper
           //删除对应审批表数据
//           zjXmCqjxAssistantLeaderApprovalMapper
           //删除纪律性考核
           //删除协作性考核
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxExecutiveAssistantList);
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxExecutiveAssistant(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxExecutiveAssistant.setExecutiveId(UuidUtil.generate());
        zjXmCqjxExecutiveAssistant.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxExecutiveAssistantMapper.insert(zjXmCqjxExecutiveAssistant);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxExecutiveAssistant);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxExecutiveAssistant(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
           // 考核计划ID
           dbzjXmCqjxExecutiveAssistant.setManagerId(zjXmCqjxExecutiveAssistant.getManagerId());
           // 考核年度
           dbzjXmCqjxExecutiveAssistant.setAssessmentYears(zjXmCqjxExecutiveAssistant.getAssessmentYears());
           // 考核标题
           dbzjXmCqjxExecutiveAssistant.setAssessmentTitle(zjXmCqjxExecutiveAssistant.getAssessmentTitle());
           // 考核季度
           dbzjXmCqjxExecutiveAssistant.setAssessmentQuarter(zjXmCqjxExecutiveAssistant.getAssessmentQuarter());
           // 考核状态
           dbzjXmCqjxExecutiveAssistant.setAssessmentState(zjXmCqjxExecutiveAssistant.getAssessmentState());
           // 岗位
           dbzjXmCqjxExecutiveAssistant.setPosition(zjXmCqjxExecutiveAssistant.getPosition());
           // 分管领导ID
           dbzjXmCqjxExecutiveAssistant.setChargeLeaderId(zjXmCqjxExecutiveAssistant.getChargeLeaderId());
           // 分管领导意见
           dbzjXmCqjxExecutiveAssistant.setChargeLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
           // 主管领导ID
           dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderId(zjXmCqjxExecutiveAssistant.getExecutiveLeaderId());
           // 主管领导意见
           dbzjXmCqjxExecutiveAssistant.setExecutiveLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
           // 季度得分
           dbzjXmCqjxExecutiveAssistant.setQuarterScore(zjXmCqjxExecutiveAssistant.getQuarterScore());
           // 共通
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxExecutiveAssistant);
        }
    }

	@Override
	public ResponseEntity getZjXmCqjxAssistantOaLeaderListByExecutiveId(
			ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
		ZjXmCqjxExecutiveAssistant assistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
			if(assistant.getAssessmentState().equals("0") || assistant.getAssessmentState().equals("2") || assistant.getAssessmentState().equals("5")) {
				if(StrUtil.isNotEmpty(assistant.getChargeLeaderId())) {
					ZjXmCqjxOaLeader leader = new ZjXmCqjxOaLeader();
					leader.setOtherId(assistant.getExecutiveId());
					leader.setOtherType("0");
					leader.setOaUserId(assistant.getChargeLeaderId());
					List leaderList = zjXmCqjxOaLeaderMapper.selectByZjXmCqjxOaLeaderList(leader);
					assistant.setLeaderList(BusinessZj.dbToPageByMember(leaderList));
					//如果初始化是员工时，自动选择部门负责人
				}else if(assistant.getAssessmentType().equals("3")) {
					ZjXmCqjxOaDeptMember member = new ZjXmCqjxOaDeptMember();
					member.setOtherId(assistant.getManagerId());
					member.setOaUserId(assistant.getCreateUser());
					List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(member);
					ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
					head.setDepartmentOrgId(memberList.get(0).getOaOrgId());
					List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
					List zcMemberList = new ArrayList();
					if(headList.size()>0) {
			        	head = headList.get(0);
			        	head.setOaOrgId(head.getUserOrgId());
			        	head.setOaUserId(head.getUserKey());
			        	head.setOaUserName(head.getUserName());
			        	zcMemberList.add(head);					
						assistant.setLeaderList(BusinessZj.dbToPageByMember(zcMemberList));						
					}else {
						assistant.setLeaderList(BusinessZj.dbToPageByMember(zcMemberList));	
					}
				}
			}else if(assistant.getAssessmentState().equals("1") || assistant.getAssessmentState().equals("4") || assistant.getAssessmentState().equals("6")) {
				if(StrUtil.isNotEmpty(assistant.getExecutiveLeaderId())) {
					ZjXmCqjxOaLeader leader = new ZjXmCqjxOaLeader();
					leader.setOtherId(assistant.getExecutiveId());
					leader.setOtherType("1");					
					leader.setOaUserId(assistant.getExecutiveLeaderId());
					List leaderList = zjXmCqjxOaLeaderMapper.selectByZjXmCqjxOaLeaderList(leader);
					assistant.setLeaderList(BusinessZj.dbToPageByMember(leaderList));
				}				
			}
		return repEntity.ok(assistant);
	}

	@Override
	public ResponseEntity checkZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
		ZjXmCqjxAssessmentManage manager = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getManagerId());
		if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "5") && !StrUtil.equals(manager.getState(), "2")) {
			return repEntity.layerMessage("NO", "该考核流程未开启，请等待管理员开启！");
		}
		ZjXmCqjxExecutiveAssistant assistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
		Date now = new Date();
		//申请人发起审核和退回到发起人重新发起状态
		if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "0") || StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "2")) {
			if(!StrUtil.equals(assistant.getAssistantLock(), "2")){
				if(now.after(manager.getDutyClosingEndDate())) {
					ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
				}				
			}
		}//分管领导审核和主管领导退回给分管领导调整状态
		else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "1")) {
			if(!StrUtil.equals(assistant.getAssistantLock(), "3")){
			if(now.after(manager.getFirstDutyClosingEndDate())) {
				ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
				dbAssistant.setAssistantLock("1");
				dbAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
			}
			}
		}//主管领导审核
		else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "3")){
			if(!StrUtil.equals(assistant.getAssistantLock(), "4")){
			if(now.after(manager.getFinalDutyClosingEndDate())) {
				ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
				dbAssistant.setAssistantLock("1");
				dbAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
			}
			}
		}//申请人发起评分申请状态
		else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "5")) {
			if(!StrUtil.equals(assistant.getAssistantLock(), "5")){
			if(now.after(manager.getScoreClosingEndDate())) {
				ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
				dbAssistant.setAssistantLock("1");
				dbAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
			}
			}
		}//分管领导评分状态
		else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "6")) {
			if(!StrUtil.equals(assistant.getAssistantLock(), "6")){
			if(now.after(manager.getFirstScoreClosingEndDate())) {
				ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
				dbAssistant.setAssistantLock("1");
				dbAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
			}
			}
		}//主管领导评分状态
		else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "7")) {
			if(!StrUtil.equals(assistant.getAssistantLock(), "7")){
			if(now.after(manager.getFinalScoreClosingEndDate())) {
				ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
				dbAssistant.setAssistantLock("1");
				dbAssistant.setModifyUserInfo(userKey, realName);
				zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				return repEntity.layerMessage("NO", "当前操作已经超过了规定的截止日期而被锁定，请联系管理员！");
			}
			}
		}
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity batchCheckZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
		ZjXmCqjxAssessmentManage manager = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getManagerId());
//		if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getAssessmentState(), "5") && !StrUtil.equals(manager.getState(), "2")) {
//			return repEntity.layerMessage("NO", "该考核流程未开启，请等待管理员开启！");
//		}
		Date now = new Date();
//		ZjXmCqjxExecutiveAssistant assistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
		assistant.setManagerId(zjXmCqjxExecutiveAssistant.getManagerId());
		List<ZjXmCqjxExecutiveAssistant> assistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(assistant);
		for(ZjXmCqjxExecutiveAssistant executiveAssistant : assistantList) {
			//申请人发起审核和退回到发起人重新发起状态
			if(StrUtil.equals(executiveAssistant.getAssessmentState(), "0") || StrUtil.equals(executiveAssistant.getAssessmentState(), "2")) {
				if(!StrUtil.equals(executiveAssistant.getAssistantLock(), "2")){
					if(now.after(manager.getDutyClosingEndDate())) {
						ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveAssistant.getExecutiveId());
						dbAssistant.setAssistantLock("1");
						dbAssistant.setModifyUserInfo(userKey, realName);
						zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
					}				
				}
			}//分管领导审核和主管领导退回给分管领导调整状态
			else if(StrUtil.equals(executiveAssistant.getAssessmentState(), "1")) {
				if(!StrUtil.equals(executiveAssistant.getAssistantLock(), "3")){
				if(now.after(manager.getFirstDutyClosingEndDate())) {
					ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				}
				}
			}//主管领导审核
			else if(StrUtil.equals(executiveAssistant.getAssessmentState(), "3")){
				if(!StrUtil.equals(executiveAssistant.getAssistantLock(), "4")){
				if(now.after(manager.getFinalDutyClosingEndDate())) {
					ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				}
				}
			}//申请人发起评分申请状态
			else if(StrUtil.equals(executiveAssistant.getAssessmentState(), "5")) {
				if(!StrUtil.equals(executiveAssistant.getAssistantLock(), "5")){
				if(now.after(manager.getScoreClosingEndDate())) {
					ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				}
				}
			}//分管领导评分状态
			else if(StrUtil.equals(executiveAssistant.getAssessmentState(), "6")) {
				if(!StrUtil.equals(executiveAssistant.getAssistantLock(), "6")){
				if(now.after(manager.getFirstScoreClosingEndDate())) {
					ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				}
				}
			}//主管领导评分状态
			else if(StrUtil.equals(executiveAssistant.getAssessmentState(), "7")) {
				if(!StrUtil.equals(executiveAssistant.getAssistantLock(), "7")){
				if(now.after(manager.getFinalScoreClosingEndDate())) {
					ZjXmCqjxExecutiveAssistant dbAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(executiveAssistant.getExecutiveId());
					dbAssistant.setAssistantLock("1");
					dbAssistant.setModifyUserInfo(userKey, realName);
					zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbAssistant);
				}
				}
			}			
		}

		return repEntity.ok("");
	}
	
	@Override
	public ResponseEntity zjXmCqjxExecutiveAssistantReleaseLock(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getExecutiveId());
        if (dbzjXmCqjxExecutiveAssistant != null && StrUtil.isNotEmpty(dbzjXmCqjxExecutiveAssistant.getExecutiveId())) {
        	switch (dbzjXmCqjxExecutiveAssistant.getAssessmentState()) {
			case "0":
                dbzjXmCqjxExecutiveAssistant.setAssistantLock("2");				
				break;
			case "2":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("2");				
				break;
			case "1":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("3");				
				break;
			case "4":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("3");				
				break;
			case "3":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("4");				
				break;
			case "5":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("5");				
				break;
			case "6":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("6");				
				break;
			case "7":
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("7");				
				break;
			default:
				dbzjXmCqjxExecutiveAssistant.setAssistantLock("0");					
				break;
			}
           // 共通
           dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.layerMessage("YES", "操作成功!");
        }
	}

	@Override
	public ResponseEntity checkZjXmCqjxAssessmentManageState(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
		ZjXmCqjxAssessmentManage manager = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getManagerId());
		if(!StrUtil.equals(manager.getState(), "2")) {
			return repEntity.layerMessage("NO", "该考核流程未开启，请等待管理员开启！");
		}
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity getZjXmCqjxAssistantListByDeptLeader(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);    
        // 判断当前人是否是部门负责人
//        ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
//        head.setUserKey(userKey);
//        List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
        ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
        detail.setOtherType("0");
        detail.setOaUserId(userKey);
        List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        if(detailList.size() == 0) {
        	List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxAssessmentManage>();
    		return repEntity.okList(zjXmCqjxAssessmentManageList, 0);           	
        }else {
        	zjXmCqjxExecutiveAssistant.setDepartmentId(detailList.get(0).getOaOrgId());
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxExecutiveAssistant.getPage(),zjXmCqjxExecutiveAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectZjXmCqjxAssessmentManageListByDeptHeader(zjXmCqjxExecutiveAssistant);
        // 得到分页信息
        PageInfo<ZjXmCqjxExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);

        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmCqjxExecutiveAssistantTodoCount(String token,String type,String leaderFlag,String state) {
		int num = 0;
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		}
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		List<ZjXmCqjxExecutiveAssistant> list = new ArrayList<ZjXmCqjxExecutiveAssistant>();
    		ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
    		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
    		if(StrUtil.equals(leaderFlag, "1")) {
    			zjXmCqjxExecutiveAssistant.setLeaderSee("1");
    			zjXmCqjxExecutiveAssistant.setChargeLeaderId(userKey);
    			assistant.setAssessmentState("1");
    			list.add(assistant);
    			assistant = new ZjXmCqjxExecutiveAssistant();
    			assistant.setAssessmentState("3");
    			list.add(assistant);
    			assistant = new ZjXmCqjxExecutiveAssistant();
    			assistant.setAssessmentState("6");
    			list.add(assistant);
    			assistant = new ZjXmCqjxExecutiveAssistant();
    			assistant.setAssessmentState("7");
    			list.add(assistant);
    			zjXmCqjxExecutiveAssistant.setAssistantList(list);
    			num = zjXmCqjxExecutiveAssistantMapper.selectZjXmCqjxAssistantLeaderTodoCount(zjXmCqjxExecutiveAssistant);
    		}else if(StrUtil.equals(leaderFlag, "0")){
    			if(StrUtil.equals(state, "0")) {
//        			zjXmCqjxExecutiveAssistant.setAssessmentType("2");
        			zjXmCqjxExecutiveAssistant.setAssessmentType(type);
        			zjXmCqjxExecutiveAssistant.setCreateUser(userKey);
        			assistant.setAssessmentState("0");
        			list.add(assistant);
        			assistant = new ZjXmCqjxExecutiveAssistant();
        			assistant.setAssessmentState("2");
        			list.add(assistant);
        			zjXmCqjxExecutiveAssistant.setAssistantList(list);        				
    			}else {
//        			zjXmCqjxExecutiveAssistant.setAssessmentType("2");
        			zjXmCqjxExecutiveAssistant.setAssessmentType(type);
        			zjXmCqjxExecutiveAssistant.setCreateUser(userKey);
        			assistant = new ZjXmCqjxExecutiveAssistant();
        			assistant.setAssessmentState("5");
        			list.add(assistant);	
        			zjXmCqjxExecutiveAssistant.setAssistantList(list);        				
    			}	
    			num = zjXmCqjxExecutiveAssistantMapper.selectZjXmCqjxExecutiveAssistantTodoCount(zjXmCqjxExecutiveAssistant);
    		}
    		return repEntity.ok(num);
	}

	@Override
	public ResponseEntity zjXmCqjxStaffAssistantQuarterScoreTask(
			ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        double quarterScore = 0;//任务得分
        //设置权重
//   	    BigDecimal chargeLeaderWeight = new BigDecimal("0.7");
//   	    BigDecimal executiveLeaderWeight = new BigDecimal("0.3");
   	    BigDecimal executiveScoreSum = null;//明细得分合计
   	    BigDecimal cooperationScore = null;//协作性得分
   	    BigDecimal disciplineScore = null;//纪律性得分
   	    BigDecimal quarterScoreDem = null;//纪律性得分		
		//将所有员工数据查出
		zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
		zjXmCqjxExecutiveAssistant.setAssessmentType("3");
		zjXmCqjxExecutiveAssistant.setAssessmentState("8");
		zjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
		zjXmCqjxExecutiveAssistant.setCooperationFlag("1");
		zjXmCqjxExecutiveAssistant.setTaskFlag("1");
		List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant);
		for(ZjXmCqjxExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
			if(StrUtil.isEmpty(assistant.getDeptCoefficient())) {
        		 //获取员工本季度得分
				executiveScoreSum = new BigDecimal(assistant.getTaskScore());
               	cooperationScore = new BigDecimal(assistant.getCooperationScore());
              	disciplineScore = new BigDecimal(assistant.getDisciplineScore());
              	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScoreSum);        	
             	//得到部门系数	
             	ZjXmCqjxOaDeptMember member = new ZjXmCqjxOaDeptMember();
             	member.setOtherId(assistant.getManagerId());
             	member.setOaUserId(assistant.getCreateUser());
             	List<ZjXmCqjxOaDeptMember> memberList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(member);
             	if(memberList.size()>0) {
         			ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
         			head.setDepartmentId(memberList.get(0).getOaOrgId());
         			List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
         			ZjXmCqjxDepartmentHeadDetail headDetail = new ZjXmCqjxDepartmentHeadDetail();
         			headDetail.setOtherId(headList.get(0).getDepartmentHeadId());
         			headDetail.setOtherType("0");
         			List<ZjXmCqjxDepartmentHeadDetail> headDetailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(headDetail);
                 	ZjXmCqjxExecutiveAssistant executiveAssistant = new ZjXmCqjxExecutiveAssistant();
                 	executiveAssistant.setYears(assistant.getYears());
                 	executiveAssistant.setAssessmentQuarter(assistant.getAssessmentQuarter());
                 	executiveAssistant.setCreateUser(headDetailList.get(0).getOaUserId());
                 	List<ZjXmCqjxExecutiveAssistant> executiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(executiveAssistant);
                 	if(executiveAssistantList.size()>0) {
                 		if(!StrUtil.equals(executiveAssistantList.get(0).getAssessmentState(), "8")) {
                 			quarterScoreDem = new BigDecimal(0);
                 		}else {
                     		BigDecimal deptLeaderScore = new BigDecimal(executiveAssistantList.get(0).getQuarterScore());
                     		BigDecimal weight = new BigDecimal(60);
//                     		BigDecimal deptWeight = deptLeaderScore.divide(weight);
                     		BigDecimal deptWeight = CalcUtils.calcDivide(deptLeaderScore, weight, 3);
                     		BigDecimal weightbg = quarterScoreDem.multiply(deptWeight);  
                     		quarterScore = weightbg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                     		quarterScore = Double.ParseDouble(df.format(quarterScoreDem.multiply(deptWeight)));
  
                     		assistant.setDeptCoefficient(deptWeight.doubleValue()+"");
//                           	quarterScore = quarterScoreDem.doubleValue();
                         	assistant.setQuarterScore(quarterScore);      
                         	zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(assistant);
                 		}
                 	}
             	}
			}
		}
        return repEntity.ok("");
	}
	/**
	 * 
	 * @param userName
	 *            ==姓名
	 * @param dbOpinionContent==数据库里的值
	 * @param opinionContent===过来的值
	 * @return
	 */
	private String getOpinionContent(String userName, String dbOpinionContent, String opinionContent) {
		if (StrUtil.isNotEmpty(opinionContent)) {
			opinionContent = StrUtil.isEmpty(dbOpinionContent) ? opinionContent
					: dbOpinionContent + "<br><br>" + opinionContent;
			opinionContent += "<br>" + userName + "  " + DateUtil.formatDateTime(new Date());
		}
		return opinionContent;
	}

	@Override
	public ResponseEntity zjXmCqjxExecutiveAssistantReleaseTempSave(
			ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
		ZjXmCqjxAssistantLeaderApproval dbzjXmCqjxExecutiveAssistant = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
		ZjXmCqjxDisciplineAssistantLeaderApproval dbDisciplineApproval = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxExecutiveAssistant.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxExecutiveAssistant != null) {
        	if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getLeaderFlag(), "0")) {
        		dbzjXmCqjxExecutiveAssistant.setLeaderOption(zjXmCqjxExecutiveAssistant.getChargeLeaderOption());
        	}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getLeaderFlag(), "1")) {
        		dbzjXmCqjxExecutiveAssistant.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
        	}
        	zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbzjXmCqjxExecutiveAssistant);
        }else if(dbDisciplineApproval != null){
        	if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getLeaderFlag(), "0")) {
        		dbDisciplineApproval.setLeaderOption(zjXmCqjxExecutiveAssistant.getDeptHeadOption());
        	}else if(StrUtil.equals(zjXmCqjxExecutiveAssistant.getLeaderFlag(), "1")) {
        		dbDisciplineApproval.setLeaderOption(zjXmCqjxExecutiveAssistant.getExecutiveLeaderOption());
        	}
        	zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(dbDisciplineApproval);
        }
		return repEntity.ok(zjXmCqjxExecutiveAssistant);
	}

	@Override
	public ResponseEntity getZjXmCqjxYearLeaderTodoCount(String token, String type, String leaderFlag, String state) {
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		}
		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
		JSONObject json = new JSONObject(tokenDecrypt);
		String userKey = (String) json.get("userKey");
		ZjXmCqjxYearAssistant assistant = new ZjXmCqjxYearAssistant();
		assistant.setCreateUser(userKey);
		int sum = zjXmCqjxYearAssistantMapper.selectZjXmCqjxYearLeaderTodoCount(assistant);
		return repEntity.ok(sum);		
	}
	
	@Override
	public ResponseEntity getZjXmCqjxYearLeaderHasTodoCount(String token) {
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		}
		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
		JSONObject json = new JSONObject(tokenDecrypt);
		String userKey = (String) json.get("userKey");
		ZjXmCqjxYearAssistant assistant = new ZjXmCqjxYearAssistant();
		assistant.setCreateUser(userKey);
		int sum = zjXmCqjxYearAssistantMapper.selectZjXmCqjxYearLeaderHasTodoCount(assistant);
		return repEntity.ok(sum);		
	}

	@Override
	public ResponseEntity getZjXmCqjxExecutiveAssistantListByYear(
			ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        if (zjXmCqjxExecutiveAssistant == null) {
            zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);    
        zjXmCqjxExecutiveAssistant.setCreateUser(userKey);
        // 分页查询
        PageHelper.startPage(zjXmCqjxExecutiveAssistant.getPage(),zjXmCqjxExecutiveAssistant.getLimit());
        // 获取数据
        if(StrUtil.isNotEmpty(zjXmCqjxExecutiveAssistant.getYears())) {
        	zjXmCqjxExecutiveAssistant.setAssessmentYears(DateUtil.parse(zjXmCqjxExecutiveAssistant.getYears(), "yyyy"));
        }
        List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant);
        // 得到分页信息
        PageInfo<ZjXmCqjxExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);

        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());
	}
	/**
	 * 
	 * 公司机关任务已办
	 */
	@Override
	public ResponseEntity getZjXmCqjxAssistantDoneList(ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxExecutiveAssistant.setCreateUser(userKey);
        zjXmCqjxExecutiveAssistant.setLeaderSee("1");
        // 分页查询
        PageHelper.startPage(zjXmCqjxExecutiveAssistant.getPage(),zjXmCqjxExecutiveAssistant.getLimit());
        // 获取数据
        List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectLeaderDoneListByUserKey(zjXmCqjxExecutiveAssistant);
        for(ZjXmCqjxExecutiveAssistant assistant : zjXmCqjxExecutiveAssistantList) {
    		ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
    		head.setDepartmentId(assistant.getDepartmentId());
    		List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
    		if(headList.size()>0) {
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    			detail.setOtherId(headList.get(0).getDepartmentHeadId());
    			detail.setOtherType("1");
    			List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);    
    			if(detailList.size() == 0) {
    				//没有分管领导
    				assistant.setHaveChangerLeader("1");
    			}else {
    				//有分管领导
    				assistant.setHaveChangerLeader("0");    				
    			}
    		}        	
        	if(!StrUtil.equals(assistant.getState(), "2")) {
        		assistant.setFirstScoreClosingEndDate(null);
        	}
        	
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxExecutiveAssistant> p = new PageInfo<>(zjXmCqjxExecutiveAssistantList);
        return repEntity.okList(zjXmCqjxExecutiveAssistantList, p.getTotal());        
	
	}

	@Override
	public ResponseEntity getZjXmCqjxExecutiveAssistantHasTodoCount(String token, String type, String leaderFlag,
			String state) {
		int num = 0;
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		}
		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
		JSONObject json = new JSONObject(tokenDecrypt);
		String userKey = (String) json.get("userKey");
		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
		assistant.setCreateUser(userKey);
		num = zjXmCqjxExecutiveAssistantMapper.selectZjXmCqjxAssistantLeaderHasTodoCount(assistant);
		return repEntity.ok(num);
	}

}
