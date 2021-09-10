package com.apih5.service.impl;

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
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectDisciplineAssessmentMemberService")
public class ZjXmCqjxProjectDisciplineAssessmentMemberServiceImpl implements ZjXmCqjxProjectDisciplineAssessmentMemberService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentMemberMapper zjXmCqjxProjectDisciplineAssessmentMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentMapper zjXmCqjxProjectDisciplineAssessmentMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentDetailedMapper zjXmCqjxProjectDisciplineAssessmentDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadMapper zjXmCqjxProjectDepartmentHeadMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectAssessmentManageMapper zjXmCqjxProjectAssessmentManageMapper;

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupPersonnelMapper zjXmCqjxProjectSetupPersonnelMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentMemberListByCondition(ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        if (zjXmCqjxProjectDisciplineAssessmentMember == null) {
            zjXmCqjxProjectDisciplineAssessmentMember = new ZjXmCqjxProjectDisciplineAssessmentMember();
        }
        ZjXmCqjxProjectDisciplineAssessmentDetailed detailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
        if(StrUtil.isNotEmpty(zjXmCqjxProjectDisciplineAssessmentMember.getDisciplineId())) {
        	zjXmCqjxProjectDisciplineAssessmentMember.setOtherId(zjXmCqjxProjectDisciplineAssessmentMember.getDisciplineId());
        }        
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectDisciplineAssessmentMember.getPage(),zjXmCqjxProjectDisciplineAssessmentMember.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectDisciplineAssessmentMember> zjXmCqjxProjectDisciplineAssessmentMemberList = zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByZjXmCqjxProjectDisciplineAssessmentMemberList(zjXmCqjxProjectDisciplineAssessmentMember);
        for(ZjXmCqjxProjectDisciplineAssessmentMember member : zjXmCqjxProjectDisciplineAssessmentMemberList) {
            detailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
            detailed.setDisciplineId(member.getOtherId());
            detailed.setUserKey(member.getOaUserId());
            List<ZjXmCqjxProjectDisciplineAssessmentDetailed> detailedList = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByZjXmCqjxProjectDisciplineAssessmentDetailedList(detailed);        	
            if(detailedList.size()>0) {
            	member.setDisciplineDetailedId(detailedList.get(0).getDisciplineDetailedId());
            	member.setScore(detailedList.get(0).getDisciplineDetailedScore());
            	member.setDisciplineDetailedContent(detailedList.get(0).getDisciplineDetailedContent());
            }
        }        
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectDisciplineAssessmentMember> p = new PageInfo<>(zjXmCqjxProjectDisciplineAssessmentMemberList);

        return repEntity.okList(zjXmCqjxProjectDisciplineAssessmentMemberList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentMemberDetails(ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        if (zjXmCqjxProjectDisciplineAssessmentMember == null) {
            zjXmCqjxProjectDisciplineAssessmentMember = new ZjXmCqjxProjectDisciplineAssessmentMember();
        }
        // 获取数据
        ZjXmCqjxProjectDisciplineAssessmentMember dbZjXmCqjxProjectDisciplineAssessmentMember = zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessmentMember.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxProjectDisciplineAssessmentMember != null) {
            return repEntity.ok(dbZjXmCqjxProjectDisciplineAssessmentMember);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDisciplineAssessmentMember(ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectDisciplineAssessmentMember.setZcOaId(UuidUtil.generate());
        zjXmCqjxProjectDisciplineAssessmentMember.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectDisciplineAssessmentMemberMapper.insert(zjXmCqjxProjectDisciplineAssessmentMember);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDisciplineAssessmentMember);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessmentMember(ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectDisciplineAssessmentMember dbzjXmCqjxProjectDisciplineAssessmentMember = zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessmentMember.getZcOaId());
        if (dbzjXmCqjxProjectDisciplineAssessmentMember != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectDisciplineAssessmentMember.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOtherId(zjXmCqjxProjectDisciplineAssessmentMember.getOtherId());
           // 其他类型
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOtherType(zjXmCqjxProjectDisciplineAssessmentMember.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOaDepartmentMemberFlag(zjXmCqjxProjectDisciplineAssessmentMember.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOaUserId(zjXmCqjxProjectDisciplineAssessmentMember.getOaUserId());
           // oaUserName
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOaUserName(zjXmCqjxProjectDisciplineAssessmentMember.getOaUserName());
           // oaOrgId
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOaOrgId(zjXmCqjxProjectDisciplineAssessmentMember.getOaOrgId());
           // 手机号
           dbzjXmCqjxProjectDisciplineAssessmentMember.setMobile(zjXmCqjxProjectDisciplineAssessmentMember.getMobile());
           // 考核flag
           dbzjXmCqjxProjectDisciplineAssessmentMember.setAssessmentFlag(zjXmCqjxProjectDisciplineAssessmentMember.getAssessmentFlag());
           // oaOrgName
           dbzjXmCqjxProjectDisciplineAssessmentMember.setOaOrgName(zjXmCqjxProjectDisciplineAssessmentMember.getOaOrgName());
           // 共通
           dbzjXmCqjxProjectDisciplineAssessmentMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMemberMapper.updateByPrimaryKey(dbzjXmCqjxProjectDisciplineAssessmentMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssessmentMember);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentMember(List<ZjXmCqjxProjectDisciplineAssessmentMember> zjXmCqjxProjectDisciplineAssessmentMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDisciplineAssessmentMemberList != null && zjXmCqjxProjectDisciplineAssessmentMemberList.size() > 0) {
           ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember = new ZjXmCqjxProjectDisciplineAssessmentMember();
           zjXmCqjxProjectDisciplineAssessmentMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentMemberMapper.batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentMember(zjXmCqjxProjectDisciplineAssessmentMemberList, zjXmCqjxProjectDisciplineAssessmentMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDisciplineAssessmentMemberList);
        }
    }
	@Override
	public ResponseEntity batchAddZjXmCqjxDisciplineAssessmentMember(
			List<ZjXmCqjxProjectDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);         
        if(StrUtil.isNotEmpty(zjXmCqjxDisciplineAssessmentMemberList.get(0).getScore())) {
            if(zjXmCqjxDisciplineAssessmentMemberList.get(0).getScore().indexOf(".") == -1) {
            	zjXmCqjxDisciplineAssessmentMemberList.get(0).setScore(zjXmCqjxDisciplineAssessmentMemberList.get(0).getScore()+".0");
            }        	
        }
        int flag = 0;
        for(ZjXmCqjxProjectDisciplineAssessmentMember assessmentMember : zjXmCqjxDisciplineAssessmentMemberList) {
        	if(StrUtil.isNotEmpty(assessmentMember.getScore())) {
        		double value = Double.valueOf(assessmentMember.getScore());
        		if(value > 10.0) {
        			return repEntity.layerMessage("NO", "评分不得超过10分！");
        		}else if(value<0) {
        			return repEntity.layerMessage("NO", "评分不得为负分");
        		}
        	}
            ZjXmCqjxProjectDisciplineAssessmentMember dbzjXmCqjxDisciplineAssessmentMember = zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByPrimaryKey(assessmentMember.getZcOaId());
            if(StrUtil.isNotEmpty(assessmentMember.getDisciplineDetailedId())) {
                ZjXmCqjxProjectDisciplineAssessmentDetailed dbzjXmCqjxDisciplineAssessmentDetailed = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByPrimaryKey(assessmentMember.getDisciplineDetailedId());
                dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedScore(assessmentMember.getScore());
                dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedContent(assessmentMember.getDisciplineDetailedContent());
                dbzjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("1");                
                dbzjXmCqjxDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
                flag = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentDetailed);    
            }else {
                if (dbzjXmCqjxDisciplineAssessmentMember != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessmentMember.getZcOaId())) {
                    dbzjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("1");
                    dbzjXmCqjxDisciplineAssessmentMember.setModifyUserInfo(userKey, realName);
                    flag = zjXmCqjxProjectDisciplineAssessmentMemberMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentMember);
                 }        	
                 ZjXmCqjxProjectDisciplineAssessmentMember member = zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByPrimaryKey(assessmentMember.getZcOaId());
             	ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
                 zjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedId(UuidUtil.generate());
                 zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
                 zjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedScore(assessmentMember.getScore());
                 zjXmCqjxDisciplineAssessmentDetailed.setUserKey(member.getOaUserId());
                 zjXmCqjxDisciplineAssessmentDetailed.setUserName(member.getOaUserName());
                 zjXmCqjxDisciplineAssessmentDetailed.setDepartmentName(member.getOaOrgName());
                 zjXmCqjxDisciplineAssessmentDetailed.setMobile(member.getMobile());
                 zjXmCqjxDisciplineAssessmentDetailed.setCreateUserInfo(userKey, realName);
                 flag = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.insert(zjXmCqjxDisciplineAssessmentDetailed);             	
            }
        } 
        ZjXmCqjxProjectDisciplineAssessment assessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentMemberList.get(0).getOtherId());
    	assessment.setDisciplineState("1");
    	assessment.setModifyUserInfo(userKey, realName);
    	zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);           
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDisciplineAssessmentMemberList);
        }
	}

	@Override
	public ResponseEntity zjXmCqjxDisciplineAssessmentSubmit(
			ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);    
//        double taskScore = 0;//任务得分
//        double quarterScore = 0;//任务得分        
//   	    BigDecimal executiveScore;//明细得分
//   	    BigDecimal executiveScoreSum = null;//明细得分合计
//   	    BigDecimal cooperationScore = null;//协作性得分
//   	    BigDecimal disciplineScore = null;//纪律性得分
//   	    BigDecimal quarterScoreDem = null;//纪律性得分      		
        //当前所有人评分完成后，将每个人的纪律性得分插入到个人考核表中
        ZjXmCqjxProjectDisciplineAssessmentMember member = new ZjXmCqjxProjectDisciplineAssessmentMember();
        member.setOtherId(zjXmCqjxDisciplineAssessmentMember.getDisciplineId());
        member.setAssessmentFlag("0");
        ZjXmCqjxProjectDisciplineAssessment assessment = zjXmCqjxProjectDisciplineAssessmentMapper.selectByPrimaryKey(member.getOtherId());
        if(zjXmCqjxProjectDisciplineAssessmentMemberMapper.selectByZjXmCqjxProjectDisciplineAssessmentMemberList(member).size()==0) {
//        	ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
//        	zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
//        	List<ZjXmCqjxProjectDisciplineAssessmentDetailed> detailedList = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
        	if(assessment != null) {
//        		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
//        		assistant.setManagerId(assessment.getManagerId());        		
//            	for(ZjXmCqjxProjectDisciplineAssessmentDetailed detailed : detailedList) {
//            		assistant.setManagerId(assessment.getManagerId());
//            		assistant.setCreateUser(detailed.getUserKey());
//            		ZjXmCqjxExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByManagerId(assistant);
//            		if(dbZjXmCqjxExecutiveAssistant != null) {
//            			dbZjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
//            			dbZjXmCqjxExecutiveAssistant.setDisciplineScore(Double.parseDouble(detailed.getDisciplineDetailedScore()));
//                        //如果各项评分完成，则计算季度得分
//                        if(StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
//                        	executiveScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getDisciplineScore());
//                        	cooperationScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getCooperationScore());
//                        	disciplineScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getTaskScore());
//                        	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
//                        	quarterScore = quarterScoreDem.doubleValue();
//                        	dbZjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
//                        }  
//            			zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbZjXmCqjxExecutiveAssistant);
//            		}
//            	}   
//        		assistant.setManagerId(assessment.getManagerId());。

              	ZjXmCqjxProjectDisciplineAssistantLeaderApproval approval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
              	approval.setExecutiveId(assessment.getDisciplineId());
              	approval.setOtherType("0");
//              	approval.setApprovalFlag("0");
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
              		}else {
                        ZjXmCqjxProjectDepartmentHead head = new ZjXmCqjxProjectDepartmentHead();
                        ZjXmCqjxProjectSetupPersonnel personnel = new ZjXmCqjxProjectSetupPersonnel();
                        personnel.setOaUserId(userKey);
                        List<ZjXmCqjxProjectSetupPersonnel> personnelList = zjXmCqjxProjectSetupPersonnelMapper.selectByZjXmCqjxProjectSetupPersonnelList(personnel);
                   		head.setDepartmentId(personnelList.get(0).getOaOrgId());
                   		assessment.setDisciplineDeptId(personnelList.get(0).getOaOrgId());
                           List<ZjXmCqjxProjectDepartmentHead> headList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(head);
                           if(headList.size()>0) {
                               ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
                               detail.setOtherType("0");
                               detail.setOtherId(headList.get(0).getDepartmentHeadId());
                               List<ZjXmCqjxProjectDepartmentHeadDetail> detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
                               for(int i = 0; i<detailList.size(); i++) {
                               	ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setExecutiveId(assessment.getDisciplineId());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setOtherType("0");
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
                                   if(i == 0) {
                                   	zjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag("1");
                                   }else {
                                   	zjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag("0");
                                   }
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
                                   zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.insert(zjXmCqjxDisciplineAssistantLeaderApproval);                	
                               }    	
                           }  	
              		}   
            	assessment.setDisciplineState("3");
            	assessment.setDeptHeadOption("");
            	assessment.setExecutiveLeaderOption("");
            	assessment.setModifyUserInfo(userKey, realName);              	
            	zjXmCqjxProjectDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);              	
        	}
        }else {
        	return repEntity.layerMessage("NO", "请完善评分，再进行提交");
        }
        return repEntity.ok(zjXmCqjxDisciplineAssessmentMember);
	}
}