package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssistantLeaderApprovalMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

@Service("zjXmCqjxDisciplineAssessmentService")
public class ZjXmCqjxDisciplineAssessmentServiceImpl implements ZjXmCqjxDisciplineAssessmentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMapper zjXmCqjxDisciplineAssessmentMapper;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMemberMapper zjXmCqjxDisciplineAssessmentMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentDetailedMapper zjXmCqjxDisciplineAssessmentDetailedMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssistantLeaderApprovalMapper zjXmCqjxDisciplineAssistantLeaderApprovalMapper;
    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssessmentListByCondition(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        if (zjXmCqjxDisciplineAssessment == null) {
            zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		if(!StrUtil.equals("chongqgs_suyan", userId) && !StrUtil.equals("chongqgs_admin", userId)) {
			List<ZjXmCqjxDisciplineAssessment> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxDisciplineAssessment>();
			return repEntity.okList(zjXmCqjxAssessmentManageList, 0);	
		}
        // 分页查询
        PageHelper.startPage(zjXmCqjxDisciplineAssessment.getPage(),zjXmCqjxDisciplineAssessment.getLimit());
        // 获取数据
        List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxDisciplineAssessmentMapper.selectByZjXmCqjxDisciplineAssessmentList(zjXmCqjxDisciplineAssessment);
        // 得到分页信息
        PageInfo<ZjXmCqjxDisciplineAssessment> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentList);

        return repEntity.okList(zjXmCqjxDisciplineAssessmentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetails(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        if (zjXmCqjxDisciplineAssessment == null) {
            zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
        }
        // 获取数据
        ZjXmCqjxDisciplineAssessment dbZjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
        // 数据存在
        if (dbZjXmCqjxDisciplineAssessment != null) {
            return repEntity.ok(dbZjXmCqjxDisciplineAssessment);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDisciplineAssessment(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxDisciplineAssessment.setDisciplineId(UuidUtil.generate());
        zjXmCqjxDisciplineAssessment.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDisciplineAssessmentMapper.insert(zjXmCqjxDisciplineAssessment);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDisciplineAssessment);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDisciplineAssessment(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           // 绩效考核ID
           dbzjXmCqjxDisciplineAssessment.setManagerId(zjXmCqjxDisciplineAssessment.getManagerId());
           // 状态
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxDisciplineAssessment.getDisciplineState());
           // 标题
           dbzjXmCqjxDisciplineAssessment.setDisciplineTitle(zjXmCqjxDisciplineAssessment.getDisciplineTitle());
           // 年度
           dbzjXmCqjxDisciplineAssessment.setDisciplineYears(zjXmCqjxDisciplineAssessment.getDisciplineYears());
           // 季度
           dbzjXmCqjxDisciplineAssessment.setDisciplineQuarter(zjXmCqjxDisciplineAssessment.getDisciplineQuarter());
           // 备注
           dbzjXmCqjxDisciplineAssessment.setDisciplineRemarks(zjXmCqjxDisciplineAssessment.getDisciplineRemarks());
           // 共通
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessment);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessment(List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDisciplineAssessmentList != null && zjXmCqjxDisciplineAssessmentList.size() > 0) {
           ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
           zjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMapper.batchDeleteUpdateZjXmCqjxDisciplineAssessment(zjXmCqjxDisciplineAssessmentList, zjXmCqjxDisciplineAssessment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDisciplineAssessmentList);
        }
    }

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentLaunch(
			ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String sendUserKey = "";
        int flag = 0;
        ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxDisciplineAssessment.getDisciplineState());
           // 共通
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessment);
        }
	}
//	@Override
//	public ResponseEntity zjXmCqjxDisciplineAssessmentLaunch(
//			ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
//		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//		String userKey = TokenUtils.getUserKey(request);
//		String realName = TokenUtils.getRealName(request);
//		int flag = 0;
//		ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
//		if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
//			dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxDisciplineAssessment.getDisciplineState());
//			// 共通
//			dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
//			flag = zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
//		}
//		// 失败
//		if (flag == 0) {
//			return repEntity.errorSave();
//		}
//		else {
//			return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessment);
//		}
//	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentDeptLaunch(
			ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
//        if(StrUtil.equals(zjXmCqjxDisciplineAssessment.getApprovalFlag(), "1")) {
//        	return repEntity.layerMessage("NO", "已经审批过了！");
//        }
//        ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());        
    	ZjXmCqjxDisciplineAssistantLeaderApproval apple = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getAssistantLeaderApprovalId());
    	if(StrUtil.equals(apple.getApprovalFlag(), "3")) {
        	return repEntity.layerMessage("NO", "已经审批过了！");
    	}
    	apple.setApprovalFlag("3");
    	apple.setLeaderOption(zjXmCqjxDisciplineAssessment.getDeptHeadOption());
    	apple.setModifyUserInfo(userKey, realName);
    	zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(apple);        
        if(StrUtil.equals(zjXmCqjxDisciplineAssessment.getApprovalFlag(), "0")) {
           	ZjXmCqjxDisciplineAssistantLeaderApproval approval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
           	approval.setExecutiveId(zjXmCqjxDisciplineAssessment.getDisciplineId());
           	approval.setOtherType("0");
           	approval.setApprovalFlag("0");
           	List<ZjXmCqjxDisciplineAssistantLeaderApproval> approvalList = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxDisciplineAssistantLeaderApprovalList(approval);
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
           			zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
                   	zjXmCqjxDisciplineAssessment.setDisciplineState("3");             			
           		}
           		}else {
           			 approval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
                   	approval.setExecutiveId(zjXmCqjxDisciplineAssessment.getDisciplineId());
                   	approval.setOtherType("2");
//                   	approval.setApprovalFlag("0");
                   	approvalList = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxDisciplineAssistantLeaderApprovalList(approval);
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
                   			zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
                           	zjXmCqjxDisciplineAssessment.setDisciplineState("3");             			
                   		}
                   	}else {
                            ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
                       		head.setDepartmentId("77dbf87dM15ca0b9f2a2M456750cab3e8d472a55ba6658b2eac34");
                               List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
                               if(headList.size()>0) {
                                   ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
                                   detail.setOtherType("2");
                                   detail.setOtherId(headList.get(0).getDepartmentHeadId());
                                   List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
                                   for(int i = 0; i<detailList.size(); i++) {
                                   	ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
                                       zjXmCqjxDisciplineAssistantLeaderApproval.setExecutiveId(zjXmCqjxDisciplineAssessment.getDisciplineId());                                  
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
                                       zjXmCqjxDisciplineAssistantLeaderApprovalMapper.insert(zjXmCqjxDisciplineAssistantLeaderApproval);                           
                                   }        	
                               }                    			
                   		}
                   	zjXmCqjxDisciplineAssessment.setDisciplineState("5");                       
           		}
        }else {
        	zjXmCqjxDisciplineAssessment.setDisciplineState("4");
        }
        ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           dbzjXmCqjxDisciplineAssessment.setDeptHeadOption(getOpinionContent(realName, dbzjXmCqjxDisciplineAssessment.getDeptHeadOption(),
    			zjXmCqjxDisciplineAssessment.getDeptHeadOption()));
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxDisciplineAssessment.getDisciplineState());
//           dbzjXmCqjxDisciplineAssessment.setDeptHeadOption(zjXmCqjxDisciplineAssessment.getDeptHeadOption());
           // 共通
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessment);
        }
	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentExecutiveApproval(
			ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        double quarterScore = 0;//任务得分        
   	    BigDecimal executiveScore;//明细得分
   	    BigDecimal cooperationScore = null;//协作性得分
   	    BigDecimal disciplineScore = null;//纪律性得分
   	    BigDecimal quarterScoreDem = null;//纪律性得分      		
        //当前所有人评分完成后，将每个人的纪律性得分插入到个人考核表中        
        int flag = 0;
//        if(StrUtil.equals(zjXmCqjxDisciplineAssessment.getApprovalFlag(), "1")) {
//        	return repEntity.layerMessage("NO", "已经审批过了！");
//        }        
    	ZjXmCqjxDisciplineAssistantLeaderApproval apple = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getAssistantLeaderApprovalId());
    	if(StrUtil.equals(apple.getApprovalFlag(), "3")) {
        	return repEntity.layerMessage("NO", "已经审批过了！");
    	}
    	apple.setApprovalFlag("3");
    	apple.setLeaderOption(zjXmCqjxDisciplineAssessment.getExecutiveLeaderOption());
    	apple.setModifyUserInfo(userKey, realName);
    	zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(apple);               
        if(StrUtil.equals(zjXmCqjxDisciplineAssessment.getApprovalFlag(), "0")) {
           	ZjXmCqjxDisciplineAssistantLeaderApproval approval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
           	approval.setExecutiveId(zjXmCqjxDisciplineAssessment.getDisciplineId());
           	approval.setOtherType("2");
           	approval.setApprovalFlag("0");
           	List<ZjXmCqjxDisciplineAssistantLeaderApproval> approvalList = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxDisciplineAssistantLeaderApprovalList(approval);
           	if(approvalList.size()>0) {
           		for(int i = 0; i<approvalList.size(); i++) {
           			approval = approvalList.get(i);
           			if(i == 0) {
           				approval.setApprovalFlag("1");
           			}else {
           				approval.setApprovalFlag("0");
           			}
           			approval.setModifyUserInfo(userKey, realName);
           			zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKeySelective(approval);
           		}
           		zjXmCqjxDisciplineAssessment.setDisciplineState("5");
           	}else {
                ZjXmCqjxDisciplineAssessmentMember member = new ZjXmCqjxDisciplineAssessmentMember();
                member.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
                member.setAssessmentFlag("0");
                ZjXmCqjxDisciplineAssessment assessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(member.getOtherId());
                if(zjXmCqjxDisciplineAssessmentMemberMapper.selectByZjXmCqjxDisciplineAssessmentMemberList(member).size()==0) {
                	ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
                	zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
                	List<ZjXmCqjxDisciplineAssessmentDetailed> detailedList = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
                	if(assessment != null) {
                		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
                    	for(ZjXmCqjxDisciplineAssessmentDetailed detailed : detailedList) {
                    		assistant.setManagerId(assessment.getManagerId());
                    		assistant.setCreateUser(detailed.getUserKey());
                    		ZjXmCqjxExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByManagerId(assistant);
                    		if(dbZjXmCqjxExecutiveAssistant != null) {
                    			dbZjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
                    			dbZjXmCqjxExecutiveAssistant.setDisciplineScore(Double.parseDouble(detailed.getDisciplineDetailedScore()));
                                //如果各项评分完成，则计算季度得分
                                if(StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
                                	executiveScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getDisciplineScore());
                                	cooperationScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getCooperationScore());
                                	disciplineScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getTaskScore());
                                	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
                                	quarterScore = quarterScoreDem.doubleValue();
                                	dbZjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
                                }  
                    			zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbZjXmCqjxExecutiveAssistant);
                    		}
                    	}   
                    	assessment.setDisciplineState("2");
                    	zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);
                	}
                }        	
            	zjXmCqjxDisciplineAssessment.setDisciplineState("2");           		
           	}
        }else {
        	zjXmCqjxDisciplineAssessment.setDisciplineState("6");
        }
        ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
        if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
           dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxDisciplineAssessment.getDisciplineState());
           dbzjXmCqjxDisciplineAssessment.setExecutiveLeaderOption(getOpinionContent(realName, dbzjXmCqjxDisciplineAssessment.getExecutiveLeaderOption(),
       			zjXmCqjxDisciplineAssessment.getExecutiveLeaderOption()));
           // 共通
           dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {                                                                                                                                                                                
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessment);
        }
	}
//	@Override
//	public ResponseEntity zjXmCqjxDisciplineAssessmentExecutiveApproval(
//			ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
//		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//		String userKey = TokenUtils.getUserKey(request);
//		String realName = TokenUtils.getRealName(request);
//		double quarterScore = 0;//任务得分        
//		BigDecimal executiveScore;//明细得分
//		BigDecimal cooperationScore = null;//协作性得分
//		BigDecimal disciplineScore = null;//纪律性得分
//		BigDecimal quarterScoreDem = null;//纪律性得分      		
//		//当前所有人评分完成后，将每个人的纪律性得分插入到个人考核表中        
//		int flag = 0;
//		if(StrUtil.equals(zjXmCqjxDisciplineAssessment.getApprovalFlag(), "0")) {
//			ZjXmCqjxDisciplineAssessmentMember member = new ZjXmCqjxDisciplineAssessmentMember();
//			member.setOtherId(zjXmCqjxDisciplineAssessment.getDisciplineId());
//			member.setAssessmentFlag("0");
//			ZjXmCqjxDisciplineAssessment assessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(member.getOtherId());
//			if(zjXmCqjxDisciplineAssessmentMemberMapper.selectByZjXmCqjxDisciplineAssessmentMemberList(member).size()==0) {
//				ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
//				zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
//				List<ZjXmCqjxDisciplineAssessmentDetailed> detailedList = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
//				if(assessment != null) {
//					ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
//					for(ZjXmCqjxDisciplineAssessmentDetailed detailed : detailedList) {
//						assistant.setManagerId(assessment.getManagerId());
//						assistant.setCreateUser(detailed.getUserKey());
//						ZjXmCqjxExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByManagerId(assistant);
//						if(dbZjXmCqjxExecutiveAssistant != null) {
//							dbZjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
//							dbZjXmCqjxExecutiveAssistant.setDisciplineScore(Double.parseDouble(detailed.getDisciplineDetailedScore()));
//							//如果各项评分完成，则计算季度得分
//							if(StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
//								executiveScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getDisciplineScore());
//								cooperationScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getCooperationScore());
//								disciplineScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getTaskScore());
//								quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
//								quarterScore = quarterScoreDem.doubleValue();
//								dbZjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
//							}  
//							zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbZjXmCqjxExecutiveAssistant);
//						}
//					}   
//					assessment.setDisciplineState("2");
//					zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);
//				}
//			}        	
//			zjXmCqjxDisciplineAssessment.setDisciplineState("2");
//		}else {
//			zjXmCqjxDisciplineAssessment.setDisciplineState("6");
//		}
//		ZjXmCqjxDisciplineAssessment dbzjXmCqjxDisciplineAssessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessment.getDisciplineId());
//		if (dbzjXmCqjxDisciplineAssessment != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessment.getDisciplineId())) {
//			dbzjXmCqjxDisciplineAssessment.setDisciplineState(zjXmCqjxDisciplineAssessment.getDisciplineState());
//			// 共通
//			dbzjXmCqjxDisciplineAssessment.setModifyUserInfo(userKey, realName);
//			flag = zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessment);
//		}
//		// 失败
//		if (flag == 0) {
//			return repEntity.errorSave();
//		}
//		else {                                                                                                                                                                                
//			return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessment);
//		}
//	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentToDoList(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        if (zjXmCqjxDisciplineAssessment == null) {
            zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
        }
        zjXmCqjxDisciplineAssessment.setCreateUser(userKey);
        // 分页查询
        PageHelper.startPage(zjXmCqjxDisciplineAssessment.getPage(),zjXmCqjxDisciplineAssessment.getLimit());
        // 获取数据
        List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxDisciplineAssessmentMapper.selectDisciplineLeaderTodoListByUserKey(zjXmCqjxDisciplineAssessment);
        // 得到分页信息
        PageInfo<ZjXmCqjxDisciplineAssessment> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentList);

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
    		List<ZjXmCqjxDisciplineAssessment> list = new ArrayList<ZjXmCqjxDisciplineAssessment>();
    		ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
    		ZjXmCqjxDisciplineAssessment assistant = new ZjXmCqjxDisciplineAssessment();
    		if(StrUtil.equals(leaderFlag, "1")) {
    			zjXmCqjxDisciplineAssessment.setDeptHeadId(userKey);
    			assistant.setDisciplineState("3");
    			list.add(assistant);
    			zjXmCqjxDisciplineAssessment.setDisciplineState("5");
    			list.add(zjXmCqjxDisciplineAssessment);
    			zjXmCqjxDisciplineAssessment.setDisciplineList(list);
    		}else if(StrUtil.equals(leaderFlag, "0")){
    			zjXmCqjxDisciplineAssessment.setCreateUser(userKey);
    			assistant.setDisciplineState("0");
    			list.add(assistant);
    			assistant = new ZjXmCqjxDisciplineAssessment();
    			assistant.setDisciplineState("1");
    			list.add(assistant);
    			assistant = new ZjXmCqjxDisciplineAssessment();
    			assistant.setDisciplineState("4");
    			list.add(assistant);	
    			assistant = new ZjXmCqjxDisciplineAssessment();
    			assistant.setDisciplineState("6");
    			list.add(assistant);	
    			zjXmCqjxDisciplineAssessment.setDisciplineList(list);    			
    		}
    		return repEntity.ok(zjXmCqjxDisciplineAssessmentMapper.selectZjXmCqjxDisciplineLeaderTodoCount(zjXmCqjxDisciplineAssessment));
			}
	}
//		ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage = zjXmCqjxAssessmentManageMapper.selectByPrimaryKey(assistantList.get(0).getManagerId());
//	   			//给领导发送消息
//			String content = "请于"+DateUtil.format(zjXmCqjxAssessmentManage.getFirstDutyClosingEndDate(), "yyyy-MM-dd HH:mm:ss")+" 前完成"+zjXmCqjxAssessmentManage.getAssessmentTitle()+"流程审批";
//	    SysUser user = userService.getSysUserByUserKey(sendUserKey);
//	    sendId = user.getUserId();   					
//			sendId = "haiwei_xichengjian_test";
//			weChatEnterpriseService.sendWeChatEnterpriseMsgText("zj_qyh_woa_id_0", 1, sendId, content);	   	
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
	/**
	 * 
	 * 公司机关纪律性考核已办
	 */
	@Override
	public ResponseEntity getZjXmCqjxDisciplineAssessmentDoneList(ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {

		if (zjXmCqjxDisciplineAssessment == null) {
			zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
		}
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxDisciplineAssessment.setCreateUser(userKey);
        // 分页查询
        PageHelper.startPage(zjXmCqjxDisciplineAssessment.getPage(),zjXmCqjxDisciplineAssessment.getLimit());
        // 获取数据
        List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList = zjXmCqjxDisciplineAssessmentMapper.selectDisciplineLeaderDoneListByUserKey(zjXmCqjxDisciplineAssessment);
        // 得到分页信息
        PageInfo<ZjXmCqjxDisciplineAssessment> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentList);

        return repEntity.okList(zjXmCqjxDisciplineAssessmentList, p.getTotal());
	
	}

	@Override
	public ResponseEntity getZjXmCqjxDisciplineLeaderHasTodoCount(String token) {
		if(StrUtil.isEmpty(token)){
			return repEntity.ok(0);
		} else {
    		String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
    		JSONObject json = new JSONObject(tokenDecrypt);
    		String userKey = (String) json.get("userKey");
    		ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment = new ZjXmCqjxDisciplineAssessment();
			zjXmCqjxDisciplineAssessment.setCreateUser(userKey);
			return repEntity.ok(zjXmCqjxDisciplineAssessmentMapper.selectZjXmCqjxDisciplineLeaderHasTodoCount(zjXmCqjxDisciplineAssessment));
		}
	}
}
