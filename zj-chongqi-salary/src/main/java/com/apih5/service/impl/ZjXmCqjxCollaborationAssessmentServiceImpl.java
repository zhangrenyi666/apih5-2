package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.zjoa.common.BusinessZj;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxAssistantRangeMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMemberMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantRange;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@Service("zjXmCqjxCollaborationAssessmentService")
public class ZjXmCqjxCollaborationAssessmentServiceImpl implements ZjXmCqjxCollaborationAssessmentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMapper zjXmCqjxCollaborationAssessmentMapper;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMemberMapper zjXmCqjxCollaborationAssessmentMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxAssistantRangeMapper zjXmCqjxAssistantRangeMapper;

	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;
	
    @Override
    public ResponseEntity getZjXmCqjxCollaborationAssessmentListByCondition(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        if (zjXmCqjxCollaborationAssessment == null) {
            zjXmCqjxCollaborationAssessment = new ZjXmCqjxCollaborationAssessment();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		if(!StrUtil.equals("chongqgs_suyan", userId) && !StrUtil.equals("chongqgs_admin", userId)) {
			List<ZjXmCqjxCollaborationAssessment> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxCollaborationAssessment>();
			return repEntity.okList(zjXmCqjxAssessmentManageList, 0);	
		}
        // 分页查询
        PageHelper.startPage(zjXmCqjxCollaborationAssessment.getPage(),zjXmCqjxCollaborationAssessment.getLimit());
        // 获取数据
        List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList = zjXmCqjxCollaborationAssessmentMapper.selectByZjXmCqjxCollaborationAssessmentList(zjXmCqjxCollaborationAssessment);
        for(ZjXmCqjxCollaborationAssessment assessment : zjXmCqjxCollaborationAssessmentList) {
        	ZjXmCqjxAssistantRange rang = new ZjXmCqjxAssistantRange();
        	rang.setOtherId(assessment.getCollaborationId());
        	rang.setOtherType("0");
        	List rangList = zjXmCqjxAssistantRangeMapper.selectByZjXmCqjxAssistantRangeList(rang);
        	assessment.setAssessmentRange(BusinessZj.dbToPageByDepartment(rangList));
        	rang.setOtherType("1");
        	rangList = zjXmCqjxAssistantRangeMapper.selectByZjXmCqjxAssistantRangeList(rang);
        	assessment.setAssessmentPersonnel(BusinessZj.dbToPageByMember(rangList));
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxCollaborationAssessment> p = new PageInfo<>(zjXmCqjxCollaborationAssessmentList);

        return repEntity.okList(zjXmCqjxCollaborationAssessmentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetails(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        if (zjXmCqjxCollaborationAssessment == null) {
            zjXmCqjxCollaborationAssessment = new ZjXmCqjxCollaborationAssessment();
        }
        // 获取数据
        ZjXmCqjxCollaborationAssessment dbZjXmCqjxCollaborationAssessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessment.getCollaborationId());
        // 数据存在
        if (dbZjXmCqjxCollaborationAssessment != null) {
            return repEntity.ok(dbZjXmCqjxCollaborationAssessment);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxCollaborationAssessment(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxCollaborationAssessment.setCollaborationId(UuidUtil.generate());
        zjXmCqjxCollaborationAssessment.setCollaborationState("0");        
        zjXmCqjxCollaborationAssessment.setCreateUserInfo(userKey, realName);        
        //表里存入范围
        if(zjXmCqjxCollaborationAssessment.getAssessmentRange() != null && zjXmCqjxCollaborationAssessment.getAssessmentRange().getOaDepartmentList().size()>0) {
        	ZjXmCqjxAssistantRange zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
			OADepartmentMember rangList = zjXmCqjxCollaborationAssessment.getAssessmentRange();
			for(OADepartment dept : rangList.getOaDepartmentList()){
	        	zjXmCqjxAssistantRange.setZcOaId(UuidUtil.generate());
	        	zjXmCqjxAssistantRange.setOtherType("0");
	        	zjXmCqjxAssistantRange.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
	        	zjXmCqjxAssistantRange.setOaUserId(dept.getId());
	        	zjXmCqjxAssistantRange.setOaUserName(dept.getName());
	        	zjXmCqjxAssistantRange.setOaOrgId(dept.getOrgId());
	        	zjXmCqjxAssistantRange.setCreateUserInfo(userKey, realName);
	        	zjXmCqjxAssistantRangeMapper.insert(zjXmCqjxAssistantRange);        		
        	}
			//根据范围查询下面人员，插入人员表中
			SysUser sysUser = new SysUser();
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
			for(OADepartment dept : rangList.getOaDepartmentList()) {
				sysUser = new SysUser();
				sysUser.setCompanyId(dept.getId());
				List<SysUser> userList = userService.getUserListByRoleAndCompanyId(sysUser);	
				for(SysUser user : userList) {
					zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();					
			        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
			        zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
			        zjXmCqjxCollaborationAssessmentMember.setManagerId("");
			        zjXmCqjxCollaborationAssessmentMember.setOaUserId(user.getUserKey());
			        zjXmCqjxCollaborationAssessmentMember.setOaUserName(user.getRealName());
			        zjXmCqjxCollaborationAssessmentMember.setOaOrgId(dept.getId());
			        zjXmCqjxCollaborationAssessmentMember.setOaOrgName(dept.getName());
			        zjXmCqjxCollaborationAssessmentMember.setMobile(user.getMobile());
			        zjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("0");					        
			        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
			        zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);						
				}
			}
			
        }
        //人员范围
		if (zjXmCqjxCollaborationAssessment.getAssessmentPersonnel() != null
				&& zjXmCqjxCollaborationAssessment.getAssessmentPersonnel().getOaMemberList().size() > 0) {
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
			OADepartmentMember personnel = zjXmCqjxCollaborationAssessment.getAssessmentPersonnel();
        	ZjXmCqjxAssistantRange zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
			for(OAMember member : personnel.getOaMemberList()){
	        	zjXmCqjxAssistantRange.setZcOaId(UuidUtil.generate());
	        	zjXmCqjxAssistantRange.setOtherType("1");
	        	zjXmCqjxAssistantRange.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
	        	zjXmCqjxAssistantRange.setOaUserId(member.getUserid());
	        	zjXmCqjxAssistantRange.setOaUserName(member.getName());
	        	zjXmCqjxAssistantRange.setOaOrgId(member.getOrgId());
	        	zjXmCqjxAssistantRange.setCreateUserInfo(userKey, realName);
	        	zjXmCqjxAssistantRangeMapper.insert(zjXmCqjxAssistantRange);  
	        	zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
	        	zjXmCqjxCollaborationAssessmentMember.setOaUserId(member.getUserid());
	        	if(zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(zjXmCqjxCollaborationAssessmentMember).size() == 0) {
					zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();					
			        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
			        zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
			        zjXmCqjxCollaborationAssessmentMember.setManagerId("");
			        SysUser user = userService.getSysUserByUserKey(member.getUserid());
			        SysDepartment dept = sysDepartmentService.getSysDepartmentByPrimaryKey(member.getOrgId());
			        zjXmCqjxCollaborationAssessmentMember.setOaUserId(member.getUserid());
			        zjXmCqjxCollaborationAssessmentMember.setOaUserName(member.getName());
			        zjXmCqjxCollaborationAssessmentMember.setOaOrgId(member.getOrgId());
			        zjXmCqjxCollaborationAssessmentMember.setOaOrgName(dept.getDepartmentName());
			        zjXmCqjxCollaborationAssessmentMember.setMobile(user.getMobile());
			        zjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("0");					        
			        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
			        zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);				        		
	        	}
        	}
		}        
        int flag = zjXmCqjxCollaborationAssessmentMapper.insert(zjXmCqjxCollaborationAssessment);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxCollaborationAssessment);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxCollaborationAssessment(ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxCollaborationAssessment dbzjXmCqjxCollaborationAssessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessment.getCollaborationId());
        if (dbzjXmCqjxCollaborationAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxCollaborationAssessment.getCollaborationId())) {
            //表里存入范围
            if(zjXmCqjxCollaborationAssessment.getAssessmentRange() != null && zjXmCqjxCollaborationAssessment.getAssessmentRange().getOaDepartmentList().size()>0) {
            	ZjXmCqjxAssistantRange zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
            	zjXmCqjxAssistantRange.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
            	List<ZjXmCqjxAssistantRange> zjXmCqjxAssistantRangeList = zjXmCqjxAssistantRangeMapper.selectByZjXmCqjxAssistantRangeList(zjXmCqjxAssistantRange);
            	zjXmCqjxAssistantRange.setModifyUserInfo(userKey, realName);
            	zjXmCqjxAssistantRangeMapper.batchDeleteUpdateZjXmCqjxAssistantRange(zjXmCqjxAssistantRangeList, zjXmCqjxAssistantRange);
    			OADepartmentMember rangList = zjXmCqjxCollaborationAssessment.getAssessmentRange();
    			for(OADepartment dept : rangList.getOaDepartmentList()){
        	        	zjXmCqjxAssistantRange.setZcOaId(UuidUtil.generate());
        	        	zjXmCqjxAssistantRange.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
        	        	zjXmCqjxAssistantRange.setOtherType("0");
        	        	zjXmCqjxAssistantRange.setOaUserId(dept.getId());
        	        	zjXmCqjxAssistantRange.setOaUserName(dept.getName());
        	        	zjXmCqjxAssistantRange.setOaOrgId(dept.getOrgId());
        	        	zjXmCqjxAssistantRange.setCreateUserInfo(userKey, realName);
        	        	zjXmCqjxAssistantRangeMapper.insert(zjXmCqjxAssistantRange);         					
            	}
    			//根据范围查询下面人员，插入人员表中
    			SysUser sysUser = new SysUser();
    			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
    			zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
    			List<ZjXmCqjxCollaborationAssessmentMember> memberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(zjXmCqjxCollaborationAssessmentMember);
    			zjXmCqjxCollaborationAssessmentMember.setModifyUserInfo(userKey, realName);
    			zjXmCqjxCollaborationAssessmentMemberMapper.batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(memberList, zjXmCqjxCollaborationAssessmentMember);
    			for(OADepartment dept : rangList.getOaDepartmentList()) {
    				sysUser = new SysUser();
    				sysUser.setCompanyId(dept.getId());
    				List<SysUser> userList = userService.getUserListByRoleAndCompanyId(sysUser);	
    				for(SysUser user : userList) {
    					zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();					
    			        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
    			        zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
    			        zjXmCqjxCollaborationAssessmentMember.setManagerId("");
    			        zjXmCqjxCollaborationAssessmentMember.setOaUserId(user.getUserKey());
    			        zjXmCqjxCollaborationAssessmentMember.setOaUserName(user.getRealName());
    			        zjXmCqjxCollaborationAssessmentMember.setOaOrgId(dept.getId());
    			        zjXmCqjxCollaborationAssessmentMember.setOaOrgName(dept.getName());
    			        zjXmCqjxCollaborationAssessmentMember.setMobile(user.getMobile());
    			        zjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("0");					        
    			        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
    			        zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);						
    				}
    			}
            }  
            //人员范围
    		if (zjXmCqjxCollaborationAssessment.getAssessmentPersonnel() != null
    				&& zjXmCqjxCollaborationAssessment.getAssessmentPersonnel().getOaMemberList().size() > 0) {
    			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
    			OADepartmentMember personnel = zjXmCqjxCollaborationAssessment.getAssessmentPersonnel();
            	ZjXmCqjxAssistantRange zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
            	zjXmCqjxAssistantRange.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
            	zjXmCqjxAssistantRange.setModifyUserInfo(userKey, realName);
            	List<ZjXmCqjxAssistantRange> rangList = zjXmCqjxAssistantRangeMapper.selectByZjXmCqjxAssistantRangeList(zjXmCqjxAssistantRange);
            	zjXmCqjxAssistantRangeMapper.batchDeleteUpdateZjXmCqjxAssistantRange(rangList, zjXmCqjxAssistantRange);
    			for(OAMember member : personnel.getOaMemberList()){
    				zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
    	        	zjXmCqjxAssistantRange.setZcOaId(UuidUtil.generate());
    	        	zjXmCqjxAssistantRange.setOtherType("1");
    	        	zjXmCqjxAssistantRange.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
    	        	zjXmCqjxAssistantRange.setOaUserId(member.getUserid());
    	        	zjXmCqjxAssistantRange.setOaUserName(member.getName());
    	        	zjXmCqjxAssistantRange.setOaOrgId(member.getOrgId());
    	        	zjXmCqjxAssistantRange.setCreateUserInfo(userKey, realName);
    	        	zjXmCqjxAssistantRangeMapper.insert(zjXmCqjxAssistantRange);  
    	        	zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
    	        	zjXmCqjxCollaborationAssessmentMember.setOaUserId(member.getUserid());
    	        	if(zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(zjXmCqjxCollaborationAssessmentMember).size() == 0) {
    					zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();					
    			        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
    			        zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessment.getCollaborationId());
    			        zjXmCqjxCollaborationAssessmentMember.setManagerId("");
    			        SysUser user = userService.getSysUserByUserKey(member.getUserid());
    			        SysDepartment dept = sysDepartmentService.getSysDepartmentByPrimaryKey(member.getOrgId());
    			        zjXmCqjxCollaborationAssessmentMember.setOaUserId(member.getUserid());
    			        zjXmCqjxCollaborationAssessmentMember.setOaUserName(member.getName());
    			        zjXmCqjxCollaborationAssessmentMember.setOaOrgId(member.getOrgId());
    			        zjXmCqjxCollaborationAssessmentMember.setOaOrgName(dept.getDepartmentName());
    			        zjXmCqjxCollaborationAssessmentMember.setMobile(user.getMobile());
    			        zjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("0");					        
    			        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
    			        zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);				        		
    	        	}
            	}
    		}                
           // 绩效考核ID
           dbzjXmCqjxCollaborationAssessment.setManagerId(zjXmCqjxCollaborationAssessment.getManagerId());
           // 状态
//           dbzjXmCqjxCollaborationAssessment.setCollaborationState(zjXmCqjxCollaborationAssessment.getCollaborationState());
           // 标题
           dbzjXmCqjxCollaborationAssessment.setCollaborationTitle(zjXmCqjxCollaborationAssessment.getCollaborationTitle());
           // 年度
           dbzjXmCqjxCollaborationAssessment.setCollaborationYears(zjXmCqjxCollaborationAssessment.getCollaborationYears());
           // 季度
           dbzjXmCqjxCollaborationAssessment.setCollaborationQuarter(zjXmCqjxCollaborationAssessment.getCollaborationQuarter());
           // 备注
           dbzjXmCqjxCollaborationAssessment.setCollaborationRemarks(zjXmCqjxCollaborationAssessment.getCollaborationRemarks());
           // 共通
           dbzjXmCqjxCollaborationAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxCollaborationAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxCollaborationAssessment);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessment(List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxCollaborationAssessmentList != null && zjXmCqjxCollaborationAssessmentList.size() > 0) {
           ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment = new ZjXmCqjxCollaborationAssessment();
           zjXmCqjxCollaborationAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxCollaborationAssessmentMapper.batchDeleteUpdateZjXmCqjxCollaborationAssessment(zjXmCqjxCollaborationAssessmentList, zjXmCqjxCollaborationAssessment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	for(ZjXmCqjxCollaborationAssessment assessment : zjXmCqjxCollaborationAssessmentList) {
            	ZjXmCqjxAssistantRange zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
            	zjXmCqjxAssistantRange.setOtherId(assessment.getCollaborationId());
            	List<ZjXmCqjxAssistantRange> zjXmCqjxAssistantRangeList = zjXmCqjxAssistantRangeMapper.selectByZjXmCqjxAssistantRangeList(zjXmCqjxAssistantRange);
            	zjXmCqjxAssistantRange.setModifyUserInfo(userKey, realName);
            	zjXmCqjxAssistantRangeMapper.batchDeleteUpdateZjXmCqjxAssistantRange(zjXmCqjxAssistantRangeList, zjXmCqjxAssistantRange);
    			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
    			zjXmCqjxCollaborationAssessmentMember.setOtherId(assessment.getCollaborationId());
    			List<ZjXmCqjxCollaborationAssessmentMember> memberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(zjXmCqjxCollaborationAssessmentMember);
    			zjXmCqjxCollaborationAssessmentMember.setModifyUserInfo(userKey, realName);
    			zjXmCqjxCollaborationAssessmentMemberMapper.batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(memberList, zjXmCqjxCollaborationAssessmentMember);            	
        	}
            return repEntity.ok("sys.data.delete",zjXmCqjxCollaborationAssessmentList);
        }
    }

	@Override
	public ResponseEntity zjXmCqjxCollaborationAssessmentSendMessage(
			List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String sendId = "";
        for(ZjXmCqjxCollaborationAssessment assessment : zjXmCqjxCollaborationAssessmentList) {
        	ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        	zjXmCqjxCollaborationAssessmentMember.setOtherId(assessment.getCollaborationId());
        	List<ZjXmCqjxCollaborationAssessmentMember> assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectDeleteByZjXmCqjxCollaborationAssessmentMemberList(zjXmCqjxCollaborationAssessmentMember);
        	if(assessmentMemberList.size()>0) {
                ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
                assessmentMember.setModifyUserInfo(userKey, realName);
                zjXmCqjxCollaborationAssessmentMemberMapper.batchUpdateZjXmCqjxCollaborationAssessmentMember(assessmentMemberList, assessmentMember);
                ZjXmCqjxCollaborationAssessment dbzjXmCqjxCollaborationAssessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(assessment.getCollaborationId());
                if (dbzjXmCqjxCollaborationAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxCollaborationAssessment.getCollaborationId())) {
                   dbzjXmCqjxCollaborationAssessment.setCollaborationState("1");
                   // 共通
                   dbzjXmCqjxCollaborationAssessment.setModifyUserInfo(userKey, realName);
                   zjXmCqjxCollaborationAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessment);
                }
        	}
        	ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        	assessmentMember.setOtherId(assessment.getCollaborationId());
        	assessmentMember.setAssessmentFlag("0");
        	List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(assessmentMember);
        	for(ZjXmCqjxCollaborationAssessmentMember member : zjXmCqjxCollaborationAssessmentMemberList) {
    	        SysUser user = userService.getSysUserByUserKey(member.getOaUserId());
				sendId = sendId + user.getUserId() + "|"; 	
        	}
	   			//给领导发送消息
			String content = "请于"+DateUtil.format(assessment.getClosingDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+assessment.getCollaborationTitle()+"评分";
//			sendId = "haiwei_xichengjian_test";
			weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	 			
			ZjXmCqjxCollaborationAssessment collaborationAssessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(assessment.getCollaborationId());
			collaborationAssessment.setCollaborationState("1");
			zjXmCqjxCollaborationAssessmentMapper.updateByPrimaryKeySelective(collaborationAssessment);
        }
        return repEntity.layerMessage("NO", "发送成功！");
	}

	@Override
	public ResponseEntity selectZjXmCqjxCollaborationTodoCount(String token) {
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		} else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
    		assessmentMember.setOaUserId(userKey);
    		return repEntity.ok(zjXmCqjxCollaborationAssessmentMemberMapper.selectZjXmCqjxCollaborationTodoCount(assessmentMember));
			}
	}

	@Override
	public ResponseEntity zjXmCqjxCollaborationAssessmentTimeOutTask(
			ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
		zjXmCqjxCollaborationAssessment = new ZjXmCqjxCollaborationAssessment();
		zjXmCqjxCollaborationAssessment.setCollaborationState("1");
		List<ZjXmCqjxCollaborationAssessment> assessmentList = zjXmCqjxCollaborationAssessmentMapper.selectByZjXmCqjxCollaborationAssessmentList(zjXmCqjxCollaborationAssessment);
		for(ZjXmCqjxCollaborationAssessment assessment : assessmentList) {
			long betweenDay = DateUtil.between(DateUtil.date(), assessment.getClosingDate(), DateUnit.HOUR);
			if(betweenDay<24) {
				//判断日期是否为早上8:30,如果是，则将人员查出来发送消息
			}
		}
		return repEntity.ok("");
	}

	@Override
	public ResponseEntity zjXmCqjxCollaborationAssistantReleaseLock(
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);		
		ZjXmCqjxCollaborationAssessmentMember dbZjXmCqjxCollaborationAssessmentMember = zjXmCqjxCollaborationAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentMember.getZcOaId());
		if(dbZjXmCqjxCollaborationAssessmentMember != null) {
			dbZjXmCqjxCollaborationAssessmentMember.setAssessmentLock("0");
			dbZjXmCqjxCollaborationAssessmentMember.setModifyUserInfo(userKey, realName);
			zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKeySelective(dbZjXmCqjxCollaborationAssessmentMember);
		}
        return repEntity.ok("sys.data.update",dbZjXmCqjxCollaborationAssessmentMember);
	}

}
