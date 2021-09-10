package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssistantLeaderApproval;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxDisciplineAssessmentMemberService")
public class ZjXmCqjxDisciplineAssessmentMemberServiceImpl implements ZjXmCqjxDisciplineAssessmentMemberService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMemberMapper zjXmCqjxDisciplineAssessmentMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentDetailedMapper zjXmCqjxDisciplineAssessmentDetailedMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMapper zjXmCqjxDisciplineAssessmentMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssistantLeaderApprovalMapper zjXmCqjxDisciplineAssistantLeaderApprovalMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssessmentMemberListByCondition(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        if (zjXmCqjxDisciplineAssessmentMember == null) {
            zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
        }
        ZjXmCqjxDisciplineAssessmentDetailed detailed = new ZjXmCqjxDisciplineAssessmentDetailed();
        if(StrUtil.isNotEmpty(zjXmCqjxDisciplineAssessmentMember.getDisciplineId())) {
        	zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessmentMember.getDisciplineId());
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxDisciplineAssessmentMember.getPage(),zjXmCqjxDisciplineAssessmentMember.getLimit());
        // 获取数据
        List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList = zjXmCqjxDisciplineAssessmentMemberMapper.selectByZjXmCqjxDisciplineAssessmentMemberList(zjXmCqjxDisciplineAssessmentMember);
        for(ZjXmCqjxDisciplineAssessmentMember member : zjXmCqjxDisciplineAssessmentMemberList) {
            detailed = new ZjXmCqjxDisciplineAssessmentDetailed();
            detailed.setDisciplineId(member.getOtherId());
            detailed.setUserKey(member.getOaUserId());
            List<ZjXmCqjxDisciplineAssessmentDetailed> detailedList = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(detailed);        	
            if(detailedList.size()>0) {
            	member.setDisciplineDetailedId(detailedList.get(0).getDisciplineDetailedId());
            	member.setScore(detailedList.get(0).getDisciplineDetailedScore());
            	member.setDisciplineDetailedContent(detailedList.get(0).getDisciplineDetailedContent());
            }
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxDisciplineAssessmentMember> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentMemberList);

        return repEntity.okList(zjXmCqjxDisciplineAssessmentMemberList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssessmentMemberDetails(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        if (zjXmCqjxDisciplineAssessmentMember == null) {
            zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
        }
        // 获取数据
        ZjXmCqjxDisciplineAssessmentMember dbZjXmCqjxDisciplineAssessmentMember = zjXmCqjxDisciplineAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentMember.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxDisciplineAssessmentMember != null) {
            return repEntity.ok(dbZjXmCqjxDisciplineAssessmentMember);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDisciplineAssessmentMember(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxDisciplineAssessmentMember.setZcOaId(UuidUtil.generate());
        zjXmCqjxDisciplineAssessmentMember.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDisciplineAssessmentMemberMapper.insert(zjXmCqjxDisciplineAssessmentMember);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDisciplineAssessmentMember);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDisciplineAssessmentMember(ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxDisciplineAssessmentMember dbzjXmCqjxDisciplineAssessmentMember = zjXmCqjxDisciplineAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentMember.getZcOaId());
        if (dbzjXmCqjxDisciplineAssessmentMember != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessmentMember.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessmentMember.getOtherId());
           // 其他类型
           dbzjXmCqjxDisciplineAssessmentMember.setOtherType(zjXmCqjxDisciplineAssessmentMember.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxDisciplineAssessmentMember.setOaDepartmentMemberFlag(zjXmCqjxDisciplineAssessmentMember.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxDisciplineAssessmentMember.setOaUserId(zjXmCqjxDisciplineAssessmentMember.getOaUserId());
           // oaUserName
           dbzjXmCqjxDisciplineAssessmentMember.setOaUserName(zjXmCqjxDisciplineAssessmentMember.getOaUserName());
           // oaOrgId
           dbzjXmCqjxDisciplineAssessmentMember.setOaOrgId(zjXmCqjxDisciplineAssessmentMember.getOaOrgId());
           // 手机号
           dbzjXmCqjxDisciplineAssessmentMember.setMobile(zjXmCqjxDisciplineAssessmentMember.getMobile());
           // 考核flag
           dbzjXmCqjxDisciplineAssessmentMember.setAssessmentFlag(zjXmCqjxDisciplineAssessmentMember.getAssessmentFlag());
           // oaOrgName
           dbzjXmCqjxDisciplineAssessmentMember.setOaOrgName(zjXmCqjxDisciplineAssessmentMember.getOaOrgName());
           // 共通
           dbzjXmCqjxDisciplineAssessmentMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMemberMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessmentMember);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember(List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDisciplineAssessmentMemberList != null && zjXmCqjxDisciplineAssessmentMemberList.size() > 0) {
           ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
           zjXmCqjxDisciplineAssessmentMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentMemberMapper.batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember(zjXmCqjxDisciplineAssessmentMemberList, zjXmCqjxDisciplineAssessmentMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDisciplineAssessmentMemberList);
        }
    }

	@Override
	public ResponseEntity batchAddZjXmCqjxDisciplineAssessmentMember(
			List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);         
        if(StrUtil.isNotEmpty(zjXmCqjxDisciplineAssessmentMemberList.get(0).getScore())) {
            if(zjXmCqjxDisciplineAssessmentMemberList.get(0).getScore().indexOf(".") == -1) {
            	zjXmCqjxDisciplineAssessmentMemberList.get(0).setScore(zjXmCqjxDisciplineAssessmentMemberList.get(0).getScore()+".0");
            }        	
        }
        int flag = 0;
        for(ZjXmCqjxDisciplineAssessmentMember assessmentMember : zjXmCqjxDisciplineAssessmentMemberList) {
        	if(StrUtil.isNotEmpty(assessmentMember.getScore())) {
        		double value = Double.valueOf(assessmentMember.getScore());
        		if(value > 5.0) {
        			return repEntity.layerMessage("NO", "评分不得超过5分！");
        		}else if(value<0) {
        			return repEntity.layerMessage("NO", "评分不得为负分");
        		}
        	}
            ZjXmCqjxDisciplineAssessmentMember dbzjXmCqjxDisciplineAssessmentMember = zjXmCqjxDisciplineAssessmentMemberMapper.selectByPrimaryKey(assessmentMember.getZcOaId());
            if(StrUtil.isNotEmpty(assessmentMember.getDisciplineDetailedId())) {
                ZjXmCqjxDisciplineAssessmentDetailed dbzjXmCqjxDisciplineAssessmentDetailed = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByPrimaryKey(assessmentMember.getDisciplineDetailedId());
                dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedScore(assessmentMember.getScore());
                dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedContent(assessmentMember.getDisciplineDetailedContent());
                dbzjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("1");                
                dbzjXmCqjxDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
                flag = zjXmCqjxDisciplineAssessmentDetailedMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentDetailed);    
            }else {
                if (dbzjXmCqjxDisciplineAssessmentMember != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessmentMember.getZcOaId())) {
                    dbzjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("1");
                    dbzjXmCqjxDisciplineAssessmentMember.setModifyUserInfo(userKey, realName);
                    flag = zjXmCqjxDisciplineAssessmentMemberMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentMember);
                 }        	
                 ZjXmCqjxDisciplineAssessmentMember member = zjXmCqjxDisciplineAssessmentMemberMapper.selectByPrimaryKey(assessmentMember.getZcOaId());
             	ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
                 zjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedId(UuidUtil.generate());
                 zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
                 zjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedScore(assessmentMember.getScore());
                 zjXmCqjxDisciplineAssessmentDetailed.setUserKey(member.getOaUserId());
                 zjXmCqjxDisciplineAssessmentDetailed.setUserName(member.getOaUserName());
                 zjXmCqjxDisciplineAssessmentDetailed.setDepartmentName(member.getOaOrgName());
                 zjXmCqjxDisciplineAssessmentDetailed.setMobile(member.getMobile());
                 zjXmCqjxDisciplineAssessmentDetailed.setCreateUserInfo(userKey, realName);
                 flag = zjXmCqjxDisciplineAssessmentDetailedMapper.insert(zjXmCqjxDisciplineAssessmentDetailed);             	
            }
        } 
        ZjXmCqjxDisciplineAssessment assessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentMemberList.get(0).getOtherId());
    	assessment.setDisciplineState("1");
    	assessment.setModifyUserInfo(userKey, realName);
    	zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);           
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
			ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
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
        ZjXmCqjxDisciplineAssessmentMember member = new ZjXmCqjxDisciplineAssessmentMember();
        member.setOtherId(zjXmCqjxDisciplineAssessmentMember.getDisciplineId());
        member.setAssessmentFlag("0");
        ZjXmCqjxDisciplineAssessment assessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(member.getOtherId());
        if(zjXmCqjxDisciplineAssessmentMemberMapper.selectByZjXmCqjxDisciplineAssessmentMemberList(member).size()==0) {
//        	ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
//        	zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(member.getOtherId());
//        	List<ZjXmCqjxDisciplineAssessmentDetailed> detailedList = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
        	if(assessment != null) {
//        		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
//        		assistant.setManagerId(assessment.getManagerId());        		
//            	for(ZjXmCqjxDisciplineAssessmentDetailed detailed : detailedList) {
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
            	assessment.setDisciplineState("3");
            	assessment.setDeptHeadOption("");
            	assessment.setExecutiveLeaderOption("");
            	assessment.setModifyUserInfo(userKey, realName);
//        		assistant.setManagerId(assessment.getManagerId());。
            	zjXmCqjxDisciplineAssessmentMapper.updateByPrimaryKeySelective(assessment);
              	ZjXmCqjxDisciplineAssistantLeaderApproval approval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
              	approval.setExecutiveId(assessment.getDisciplineId());
              	approval.setOtherType("0");
//              	approval.setApprovalFlag("0");
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
              		}
              		}else {
                        ZjXmCqjxDepartmentHead head = new ZjXmCqjxDepartmentHead();
                   		head.setDepartmentId("77dbf87dM15ca0b9f2a2M456750cab3e8d472a55ba6658b2eac34");
                           List<ZjXmCqjxDepartmentHead> headList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(head);
                           if(headList.size()>0) {
                               ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
                               detail.setOtherType("0");
                               detail.setOtherId(headList.get(0).getDepartmentHeadId());
                               List<ZjXmCqjxDepartmentHeadDetail> detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
                               for(int i = 0; i<detailList.size(); i++) {
                               	ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setExecutiveId(assessment.getDisciplineId());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setOtherType("0");
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderId(detailList.get(i).getOaUserId());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderName(detailList.get(i).getOaUserName());
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setLeaderOrgId(detailList.get(i).getOaOrgId());
                                   if(i == 0) {
//                                   	sendUserKey = 
                                   	zjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag("1");
                                   }else {
                                   	zjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag("0");
                                   }
                                   zjXmCqjxDisciplineAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
                                   zjXmCqjxDisciplineAssistantLeaderApprovalMapper.insert(zjXmCqjxDisciplineAssistantLeaderApproval);                	
                               }    	
                           }  	
              		}            	
        	}
        }else {
        	return repEntity.layerMessage("NO", "请完善评分，再进行提交");
        }
        return repEntity.ok("ti");
	}
}
