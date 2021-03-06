package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssessmentDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSetupPersonnelMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupPersonnel;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@Service("zjXmCqjxProjectDisciplineAssessmentService")
public class ZjXmCqjxProjectDisciplineAssessmentServiceImpl implements ZjXmCqjxProjectDisciplineAssessmentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentMapper zjXmCqjxProjectDisciplineAssessmentMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentMemberMapper zjXmCqjxProjectDisciplineAssessmentMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentDetailedMapper zjXmCqjxProjectDisciplineAssessmentDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadMapper zjXmCqjxProjectDepartmentHeadMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantMapper zjXmCqjxProjectExecutiveAssistantMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxProjectAssessmentManageMapper zjXmCqjxProjectAssessmentManageMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupPersonnelMapper zjXmCqjxProjectSetupPersonnelMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentListByCondition(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        if (zjXmCqjxProjectDisciplineAssessment == null) {
            zjXmCqjxProjectDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);    
        String userId = TokenUtils.getUserId(request);    
        ZjXmCqjxProjectSetupPersonnel personnel = new ZjXmCqjxProjectSetupPersonnel();
		if(!StrUtil.equals(userId, "chongqgs_admin")) {		
	        personnel.setOaUserId(userKey);
		}
        List<ZjXmCqjxProjectSetupPersonnel> personnelList = zjXmCqjxProjectSetupPersonnelMapper.selectByZjXmCqjxProjectSetupPersonnelList(personnel);
        if(personnelList.size()>0) {
        	zjXmCqjxProjectDisciplineAssessment.setAssessmentDept(personnelList.get(0).getDepartmentId());
        }else {
            return repEntity.okList(new ArrayList<ZjXmCqjxProjectDisciplineAssessment>(), 0);
        }
        // ????????????
        PageHelper.startPage(zjXmCqjxProjectDisciplineAssessment.getPage(),zjXmCqjxProjectDisciplineAssessment.getLimit());
        // ????????????
//        List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxProjectDisciplineAssessmentList = zjXmCqjxProjectDisciplineAssessmentMapper.selectByZjXmCqjxProjectDisciplineAssessmentList(zjXmCqjxProjectDisciplineAssessment);
        List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxProjectDisciplineAssessmentList = zjXmCqjxProjectDisciplineAssessmentMapper.selectZjXmCqjxProjectDisciplineAssessmentListByDeptId(zjXmCqjxProjectDisciplineAssessment);
        // ??????????????????
        PageInfo<ZjXmCqjxProjectDisciplineAssessment> p = new PageInfo<>(zjXmCqjxProjectDisciplineAssessmentList);

        return repEntity.okList(zjXmCqjxProjectDisciplineAssessmentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetails(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        if (zjXmCqjxProjectDisciplineAssessment == null) {
            zjXmCqjxProjectDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
        }
        // ????????????
        ZjXmCqjxProjectDisciplineAssessment dbZjXmCqjxProjectDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
        // ????????????
        if (dbZjXmCqjxProjectDisciplineAssessment != null) {
            return repEntity.ok(dbZjXmCqjxProjectDisciplineAssessment);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDisciplineAssessment(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectDisciplineAssessment.setDisciplineId(UuidUtil.generate());
        zjXmCqjxProjectDisciplineAssessment.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectDisciplineAssessmentMapper.insert(zjXmCqjxProjectDisciplineAssessment);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDisciplineAssessment);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessment(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectDisciplineAssessment dbzjXmCqjxProjectDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxProjectDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectDisciplineAssessment.getDisciplineId())) {
           // ????????????ID
           dbzjXmCqjxProjectDisciplineAssessment.setManagerId(zjXmCqjxProjectDisciplineAssessment.getManagerId());
           // ??????
           dbzjXmCqjxProjectDisciplineAssessment.setDisciplineState(zjXmCqjxProjectDisciplineAssessment.getDisciplineState());
           // ??????
           dbzjXmCqjxProjectDisciplineAssessment.setDisciplineTitle(zjXmCqjxProjectDisciplineAssessment.getDisciplineTitle());
           // ??????
           dbzjXmCqjxProjectDisciplineAssessment.setDisciplineYears(zjXmCqjxProjectDisciplineAssessment.getDisciplineYears());
           // ??????
           dbzjXmCqjxProjectDisciplineAssessment.setDisciplineQuarter(zjXmCqjxProjectDisciplineAssessment.getDisciplineQuarter());
           // ???????????????ID
           dbzjXmCqjxProjectDisciplineAssessment.setDeptHeadId(zjXmCqjxProjectDisciplineAssessment.getDeptHeadId());
           // ?????????????????????
           dbzjXmCqjxProjectDisciplineAssessment.setDeptHeadOption(zjXmCqjxProjectDisciplineAssessment.getDeptHeadOption());
           // ????????????ID
           dbzjXmCqjxProjectDisciplineAssessment.setExecutiveLeaderId(zjXmCqjxProjectDisciplineAssessment.getExecutiveLeaderId());
           // ??????????????????
           dbzjXmCqjxProjectDisciplineAssessment.setExecutiveLeaderOption(zjXmCqjxProjectDisciplineAssessment.getExecutiveLeaderOption());
           // ??????
           dbzjXmCqjxProjectDisciplineAssessment.setDisciplineRemarks(zjXmCqjxProjectDisciplineAssessment.getDisciplineRemarks());
           // ??????
           dbzjXmCqjxProjectDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxProjectDisciplineAssessment);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssessment);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment(List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxProjectDisciplineAssessmentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDisciplineAssessmentList != null && zjXmCqjxProjectDisciplineAssessmentList.size() > 0) {
           ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
           zjXmCqjxProjectDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMapper.batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment(zjXmCqjxProjectDisciplineAssessmentList, zjXmCqjxProjectDisciplineAssessment);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDisciplineAssessmentList);
        }
    }
	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentLaunch(
			ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String sendUserKey = "";
        int flag = 0;
        ZjXmCqjxProjectDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxProjectDisciplineAssessment.getDisciplineState());
           // ??????
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssessment);
        }
	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentDeptLaunch(
			ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
//        if(StrUtil.equals(zjXmCqjxProjectDisciplineAssessment.getApprovalFlag(), "1")) {
//        	return repEntity.layerMessage("NO", "?????????????????????");
//        }
//        ZjXmCqjxProjectDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());        
    	ZjXmCqjxProjectDisciplineAssistantLeaderApproval apple = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getAssistantLeaderApprovalId());
    	if(StrUtil.equals(apple.getApprovalFlag(), "3")) {
        	return repEntity.layerMessage("NO", "?????????????????????");
    	}
    	apple.setApprovalFlag("3");
    	apple.setLeaderOption(zjXmCqjxProjectDisciplineAssessment.getDeptHeadOption());
    	apple.setModifyUserInfo(userKey, realName);
    	zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(apple);        
        if(StrUtil.equals(zjXmCqjxProjectDisciplineAssessment.getApprovalFlag(), "0")) {
           	ZjXmCqjxProjectDisciplineAssistantLeaderApproval approval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
           	approval.setExecutiveId(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
           	approval.setOtherType("0");
           	approval.setApprovalFlag("0");
           	List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> approvalList = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectDisciplineAssistantLeaderApprovalList(approval);
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
           			zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
                   	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("3");             			
           		}
           		}else {
           			 approval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
                   	approval.setExecutiveId(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
                   	approval.setOtherType("2");
//                   	approval.setApprovalFlag("0");
                   	approvalList = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectDisciplineAssistantLeaderApprovalList(approval);
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
                   			zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
                           	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("3");             			
                   		}
                   	}else {
                        ZjXmCqjxProjectDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
                            ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
                       		head.setDepartmentId(dbzjXmCqjxDisciplineAssessment.getDisciplineDeptId());                            
//                       		head.setDepartmentId("77dbf87dM15ca0b9f2a2M456750cab3e8d472a55ba6658b2eac34");
                               List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(head);
                               if(headList.size()>0) {
                                   ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
                                   detail.setOtherType("2");
                                   detail.setOtherId(headList.get(0).getDepartmentHeadId());
                                   List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
                                   for(int i = 0; i<detailList.size(); i++) {
                                   	ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setExecutiveId(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());                                  
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setOtherType("2");
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
                                       if(i == 0) {
//                                       	sendUserKey = 
                                       	zjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag("1");
                                       }else {
                                       	zjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag("0");
                                       }
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
                                       zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.insert(zjXmCqjxDisciplineAssistantLeaderApproval);                           
                                   }        	
                               }                    			
                   		}
                   	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("5");                       
           		}
        }else {
        	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("4");
        }
        ZjXmCqjxProjectDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           dbzjXmCqjxDisciplineAssessment.setDeptHeadOption(getOpinionContent(realName, dbzjXmCqjxDisciplineAssessment.getDeptHeadOption(),
    			zjXmCqjxProjectDisciplineAssessment.getDeptHeadOption()));
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxProjectDisciplineAssessment.getDisciplineState());
//           dbzjXmCqjxDisciplineAssessment.setDeptHeadOption(zjXmCqjxProjectDisciplineAssessment.getDeptHeadOption());
           // ??????
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssessment);
        }
	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentExecutiveApproval(
			ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        double quarterScore = 0;//????????????        
   	    BigDecimal executiveScore;//????????????
   	    BigDecimal cooperationScore = null;//???????????????
   	    BigDecimal disciplineScore = null;//???????????????
   	    BigDecimal quarterScoreDem = null;//???????????????      		
        //??????????????????????????????????????????????????????????????????????????????????????????        
        int flag = 0;
//        if(StrUtil.equals(zjXmCqjxProjectDisciplineAssessment.getApprovalFlag(), "1")) {
//        	return repEntity.layerMessage("NO", "?????????????????????");
//        }        
    	ZjXmCqjxProjectDisciplineAssistantLeaderApproval apple = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getAssistantLeaderApprovalId());
    	if(StrUtil.equals(apple.getApprovalFlag(), "3")) {
        	return repEntity.layerMessage("NO", "?????????????????????");
    	}
    	apple.setApprovalFlag("3");
    	apple.setLeaderOption(zjXmCqjxProjectDisciplineAssessment.getExecutiveLeaderOption());
    	apple.setModifyUserInfo(userKey, realName);
    	zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(apple);               
        if(StrUtil.equals(zjXmCqjxProjectDisciplineAssessment.getApprovalFlag(), "0")) {
           	ZjXmCqjxProjectDisciplineAssistantLeaderApproval approval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
           	approval.setExecutiveId(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
           	approval.setOtherType("2");
           	approval.setApprovalFlag("0");
           	List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> approvalList = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectDisciplineAssistantLeaderApprovalList(approval);
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
           			zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
           		}
           		zjXmCqjxProjectDisciplineAssessment.setDisciplineState("5");
           	}else {
           		ZjXmCqjxProjectDisciplineAssessmentMember member = new ZjXmCqjxProjectDisciplineAssessmentMember();
                member.setOtherId(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
                member.setAssessmentFlag("0");
                ZjXmCqjxProjectDisciplineAssessment assessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(member.getOtherId());
                if(zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByZjXmCqjxProjectDisciplineAssessmentMemberList(member).size()==0) {
                	ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
                	zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
                	List<ZjXmCqjxProjectDisciplineAssessmentDetailed> detailedList = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByZjXmCqjxProjectDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
                	if(assessment != null) {
                		ZjXmCqjxProjectExecutiveAssistant assistant = new ZjXmCqjxProjectExecutiveAssistant();
                    	for(ZjXmCqjxProjectDisciplineAssessmentDetailed detailed : detailedList) {
                    		assistant.setManagerId(assessment.getManagerId());
                    		assistant.setCreateUser(detailed.getUserKey());
                    		ZjXmCqjxProjectExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxProjectExecutiveAssistantMapper.selectByManagerId(assistant);
                    		if(dbZjXmCqjxExecutiveAssistant != null) {
                    			dbZjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
                    			dbZjXmCqjxExecutiveAssistant.setDisciplineScore(Double.parseDouble(detailed.getDisciplineDetailedScore()));
                                //????????????????????????????????????????????????
                                if(StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
                                	executiveScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getDisciplineScore());
                                	cooperationScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getCooperationScore());
                                	disciplineScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getTaskScore());
                                	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
                                	quarterScore = quarterScoreDem.doubleValue();
                                	dbZjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
                                }  
                    			zjXmCqjxProjectExecutiveAssistantMapper.updateByPrimaryKeySelective(dbZjXmCqjxExecutiveAssistant);
                    		}
                    	}   
                    	assessment.setDisciplineState("2");
                    	zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);
                	}
                }        	
            	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("2");           		
           	}
        }else {
        	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("6");
        }
        ZjXmCqjxProjectDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxProjectDisciplineAssessment.getDisciplineState());
           dbzjXmCqjxDisciplineAssessment.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxDisciplineAssessment.getExecutiveLeaderOption(),
       			zjXmCqjxProjectDisciplineAssessment.getExecutiveLeaderOption()));
           // ??????
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {                                                                                                                                                                                
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssessment);
        }
	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentToDoList(
			ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        if (zjXmCqjxProjectDisciplineAssessment == null) {
            zjXmCqjxProjectDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
        }
        zjXmCqjxProjectDisciplineAssessment.setCreateUser(userKey);
        // ????????????
        PageHelper.startPage(zjXmCqjxProjectDisciplineAssessment.getPage(),zjXmCqjxProjectDisciplineAssessment.getLimit());
        // ????????????
        List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxProjectDisciplineAssessmentMapper.selectxProjectDisciplineLeaderTodoListByUserKey(zjXmCqjxProjectDisciplineAssessment);
        // ??????????????????
        PageInfo<ZjXmCqjxProjectDisciplineAssessment> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentList);

        return repEntity.okList(zjXmCqjxDisciplineAssessmentList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmCqjxDisciplineLeaderTodoCount(
			String token, String leaderFlag) {
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		} else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		List<ZjXmCqjxProjectDisciplineAssessment> list = new ArrayList<ZjXmCqjxProjectDisciplineAssessment>();
    		ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
    		ZjXmCqjxProjectDisciplineAssessment assistant = new ZjXmCqjxProjectDisciplineAssessment();
    		if(StrUtil.equals(leaderFlag, "1")) {
    			zjXmCqjxProjectDisciplineAssessment.setDeptHeadId(userKey);
    			assistant.setDisciplineState("3");
    			list.add(assistant);
    			zjXmCqjxProjectDisciplineAssessment.setDisciplineState("5");
    			list.add(zjXmCqjxProjectDisciplineAssessment);
    			zjXmCqjxProjectDisciplineAssessment.setDisciplineList(list);
    		}else if(StrUtil.equals(leaderFlag, "0")){
    			zjXmCqjxProjectDisciplineAssessment.setCreateUser(userKey);
    			assistant.setDisciplineState("0");
    			list.add(assistant);
    			assistant = new ZjXmCqjxProjectDisciplineAssessment();
    			assistant.setDisciplineState("1");
    			list.add(assistant);
    			assistant = new ZjXmCqjxProjectDisciplineAssessment();
    			assistant.setDisciplineState("4");
    			list.add(assistant);	
    			assistant = new ZjXmCqjxProjectDisciplineAssessment();
    			assistant.setDisciplineState("6");
    			list.add(assistant);	
    			zjXmCqjxProjectDisciplineAssessment.setDisciplineList(list);    			
    		}
    		return repEntity.ok(zjXmCqjxProjectDisciplineAssessmentMapper.selectZjXmCqjxProjectDisciplineLeaderTodoCount(zjXmCqjxProjectDisciplineAssessment));
			}
	}
//		ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
//	   			//?????????????????????
//			String content = "??????"+DateUtil.format(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" ?????????"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"????????????";
//	    SysUser user = userService.getSysUserByUserKey(sendUserKey);
//	    sendId = user.getUserId();   					
//			sendId = "haiwei_xichengjian_test";
//			weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   	
	/**
	 * 
	 * @param userName
	 *            ==??????
	 * @param dbOpinionContent==??????????????????
	 * @param opinionContent===????????????
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
	/**
	 * 
	 * ?????????????????????????????????
	 */
	@Override
	public ResponseEntity zjXmCqjxProjectDisciplineAssessmentDoneList(ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        if (zjXmCqjxProjectDisciplineAssessment == null) {
            zjXmCqjxProjectDisciplineAssessment = new ZjXmCqjxProjectDisciplineAssessment();
        }
        zjXmCqjxProjectDisciplineAssessment.setCreateUser(userKey);
        // ????????????
        PageHelper.startPage(zjXmCqjxProjectDisciplineAssessment.getPage(),zjXmCqjxProjectDisciplineAssessment.getLimit());
        // ????????????
        List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxProjectDisciplineAssessmentMapper.selectProjectDisciplineLeaderDoneListByUserKey(zjXmCqjxProjectDisciplineAssessment);
        // ??????????????????
        PageInfo<ZjXmCqjxProjectDisciplineAssessment> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentList);

        return repEntity.okList(zjXmCqjxDisciplineAssessmentList, p.getTotal());
	}
}
