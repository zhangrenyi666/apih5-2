package com.apih5.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxCollaborationAssessmentDetailedService")
public class ZjXmCqjxCollaborationAssessmentDetailedServiceImpl implements ZjXmCqjxCollaborationAssessmentDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentDetailedMapper zjXmCqjxCollaborationAssessmentDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMemberMapper zjXmCqjxCollaborationAssessmentMemberMapper;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMapper zjXmCqjxCollaborationAssessmentMapper;

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetailedListByCondition(ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        if (zjXmCqjxCollaborationAssessmentDetailed == null) {
            zjXmCqjxCollaborationAssessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxCollaborationAssessmentDetailed.getPage(),zjXmCqjxCollaborationAssessmentDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxCollaborationAssessmentDetailed> zjXmCqjxCollaborationAssessmentDetailedList = zjXmCqjxCollaborationAssessmentDetailedMapper.selectByZjXmCqjxCollaborationAssessmentDetailedList(zjXmCqjxCollaborationAssessmentDetailed);
        // 得到分页信息
        PageInfo<ZjXmCqjxCollaborationAssessmentDetailed> p = new PageInfo<>(zjXmCqjxCollaborationAssessmentDetailedList);

        return repEntity.okList(zjXmCqjxCollaborationAssessmentDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetailedDetails(ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        if (zjXmCqjxCollaborationAssessmentDetailed == null) {
            zjXmCqjxCollaborationAssessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();
        }
        // 获取数据
        ZjXmCqjxCollaborationAssessmentDetailed dbZjXmCqjxCollaborationAssessmentDetailed = zjXmCqjxCollaborationAssessmentDetailedMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentDetailed.getCollaborationDetailedId());
        // 数据存在
        if (dbZjXmCqjxCollaborationAssessmentDetailed != null) {
            return repEntity.ok(dbZjXmCqjxCollaborationAssessmentDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxCollaborationAssessmentDetailed(ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);     
        zjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedId(UuidUtil.generate());
        zjXmCqjxCollaborationAssessmentDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxCollaborationAssessmentDetailedMapper.insert(zjXmCqjxCollaborationAssessmentDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxCollaborationAssessmentDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxCollaborationAssessmentDetailed(ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxCollaborationAssessmentDetailed dbzjXmCqjxCollaborationAssessmentDetailed = zjXmCqjxCollaborationAssessmentDetailedMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentDetailed.getCollaborationDetailedId());
        if (dbzjXmCqjxCollaborationAssessmentDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxCollaborationAssessmentDetailed.getCollaborationDetailedId())) {
           // 协作性考核ID
           dbzjXmCqjxCollaborationAssessmentDetailed.setCollaborationtId(zjXmCqjxCollaborationAssessmentDetailed.getCollaborationtId());
           // 员工ID
           dbzjXmCqjxCollaborationAssessmentDetailed.setUserKey(zjXmCqjxCollaborationAssessmentDetailed.getUserKey());
           // 员工名称
           dbzjXmCqjxCollaborationAssessmentDetailed.setUserName(zjXmCqjxCollaborationAssessmentDetailed.getUserName());
           // 员工部门ID
           dbzjXmCqjxCollaborationAssessmentDetailed.setDepartmentId(zjXmCqjxCollaborationAssessmentDetailed.getDepartmentId());
           // 员工部门名称
           dbzjXmCqjxCollaborationAssessmentDetailed.setDepartmentName(zjXmCqjxCollaborationAssessmentDetailed.getDepartmentName());
           // 员工手机号
           dbzjXmCqjxCollaborationAssessmentDetailed.setMobile(zjXmCqjxCollaborationAssessmentDetailed.getMobile());
           // 员工得分
           dbzjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedScore(zjXmCqjxCollaborationAssessmentDetailed.getCollaborationDetailedScore());
           // 评价人匿名
           dbzjXmCqjxCollaborationAssessmentDetailed.setCollaborationAnonymous(zjXmCqjxCollaborationAssessmentDetailed.getCollaborationAnonymous());
           // 共通
           dbzjXmCqjxCollaborationAssessmentDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxCollaborationAssessmentDetailedMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessmentDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxCollaborationAssessmentDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessmentDetailed(List<ZjXmCqjxCollaborationAssessmentDetailed> zjXmCqjxCollaborationAssessmentDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxCollaborationAssessmentDetailedList != null && zjXmCqjxCollaborationAssessmentDetailedList.size() > 0) {
           ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();
           zjXmCqjxCollaborationAssessmentDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxCollaborationAssessmentDetailedMapper.batchDeleteUpdateZjXmCqjxCollaborationAssessmentDetailed(zjXmCqjxCollaborationAssessmentDetailedList, zjXmCqjxCollaborationAssessmentDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxCollaborationAssessmentDetailedList);
        }
    }

	@Override
	public ResponseEntity batchAddZjXmCqjxCollaborationAssessmentDetailed(
			List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        boolean bool = zjXmCqjxCollaborationAssessmentMemberList.stream().anyMatch(m -> m.getOption() == null || m.getOption().equals(""));
        if(bool) {
        	return repEntity.layerMessage("NO", "人员评分列表中有未评分人员，请评分后再提交");
        }
        zjXmCqjxCollaborationAssessmentMemberList.parallelStream().forEach((member)->{
        	ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();
        	zjXmCqjxCollaborationAssessmentDetailed.setCollaborationtId(member.getOtherId());
        	zjXmCqjxCollaborationAssessmentDetailed.setUserKey(member.getOaUserId());
        	if(zjXmCqjxCollaborationAssessmentDetailedMapper.selectByZjXmCqjxCollaborationAssessmentDetailedList(zjXmCqjxCollaborationAssessmentDetailed).size()>0) {
        		ZjXmCqjxCollaborationAssessmentDetailed dbZjXmCqjxCollaborationAssessmentDetailed = zjXmCqjxCollaborationAssessmentDetailedMapper.selectByZjXmCqjxCollaborationAssessmentDetailedList(zjXmCqjxCollaborationAssessmentDetailed).get(0);
        		dbZjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedScore(member.getOption());
        		dbZjXmCqjxCollaborationAssessmentDetailed.setModifyUserInfo(userKey, realName);
        		zjXmCqjxCollaborationAssessmentDetailedMapper.updateByPrimaryKeySelective(dbZjXmCqjxCollaborationAssessmentDetailed);
        	}else {
                zjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedId(UuidUtil.generate());
                zjXmCqjxCollaborationAssessmentDetailed.setCollaborationtId(member.getOtherId());
                zjXmCqjxCollaborationAssessmentDetailed.setMemberId(member.getZcOaId());
                zjXmCqjxCollaborationAssessmentDetailed.setUserKey(member.getOaUserId());
                zjXmCqjxCollaborationAssessmentDetailed.setUserName(member.getOaUserName());
                zjXmCqjxCollaborationAssessmentDetailed.setDepartmentId(member.getOaOrgId());
                zjXmCqjxCollaborationAssessmentDetailed.setDepartmentName(member.getOaOrgName());
                zjXmCqjxCollaborationAssessmentDetailed.setMobile(member.getMobile());
                zjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedScore(member.getOption());
                zjXmCqjxCollaborationAssessmentDetailed.setCollaborationAnonymous("***");
                zjXmCqjxCollaborationAssessmentDetailed.setCreateUserInfo(userKey, realName);
                zjXmCqjxCollaborationAssessmentDetailedMapper.insert(zjXmCqjxCollaborationAssessmentDetailed);         		
        	}
		});
        ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        assessmentMember.setOtherId(zjXmCqjxCollaborationAssessmentMemberList.get(0).getOtherId());
        assessmentMember.setOaUserId(userKey);
        List<ZjXmCqjxCollaborationAssessmentMember> assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(assessmentMember);
        if(assessmentMemberList.size()>0) {
        	ZjXmCqjxCollaborationAssessmentMember dbzjXmCqjxCollaborationAssessmentMember = assessmentMemberList.get(0);
            dbzjXmCqjxCollaborationAssessmentMember.setAssessmentFlag("1");
            dbzjXmCqjxCollaborationAssessmentMember.setEffectiveFlag("0");
            dbzjXmCqjxCollaborationAssessmentMember.setModifyUserInfo(userKey, realName);
            flag = zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessmentMember);        	
        }
        //查询所有人的评分结果，如果所有人评分完成，则计算每个人的协作性得分
//        assessmentMember.setOtherId(zjXmCqjxCollaborationAssessmentMemberList.get(0).getOtherId());
//        assessmentMember.setOaUserId("");
//        assessmentMember.setAssessmentFlag("0");
//        assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(assessmentMember);  
//
//        if(assessmentMemberList.size() == 0) {
//        	assessmentMember.setAssessmentFlag("1");
//        	assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(assessmentMember);
//            int all = assessmentMemberList.size() - 1;        	
//        	assessmentMemberList.parallelStream().forEach((member)->{
//        		ZjXmCqjxCollaborationAssessmentDetailed assessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();        		
//        		assessmentDetailed.setCollaborationDetailedId(member.getOtherId());
//        		assessmentDetailed.setUserKey(member.getOaUserId());
////        		assessmentDetailed.setCollaborationDetailedScore("A");
//        		List<ZjXmCqjxCollaborationAssessmentDetailed> detailList = zjXmCqjxCollaborationAssessmentDetailedMapper.selectByZjXmCqjxCollaborationAssessmentDetailedList(assessmentDetailed);
//        		detailList.stream().forEach((detail)->{
//        			detail.setScore(Double.parseDouble(detail.getCollaborationDetailedScore()));
//        		});
//        		//重新排序
//                detailList = detailList.stream().sorted(Comparator.comparing(ZjXmCqjxCollaborationAssessmentDetailed::getCollaborationDetailedScore).reversed()).collect(toList());
//                //去除最高分、最低分
//                detailList.remove(0);
//                detailList.remove(detailList.size()-1);
//                // 求平均数
//                double asDouble = detailList.stream().mapToDouble(ZjXmCqjxCollaborationAssessmentDetailed::getScore).average().getAsDouble();
//                System.out.println("average:" + asDouble);
//                member.setAssessmentScore(asDouble);
//        		member.setModifyUserInfo(userKey, realName);
//        		zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKeySelective(member);
//        		//更新个人协作性评分
//        		ZjXmCqjxCollaborationAssessment collaboration = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(member.getOtherId());
//        		ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
////                zjXmCqjxExecutiveAssistant.setManagerId(member.getManagerId());
//        		zjXmCqjxExecutiveAssistant.setAssessmentYears(collaboration.getCollaborationYears());
//        		zjXmCqjxExecutiveAssistant.setAssessmentQuarter(collaboration.getCollaborationQuarter());
//                zjXmCqjxExecutiveAssistant.setCreateUser(member.getOaUserId());
//                List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant);
//                if(zjXmCqjxExecutiveAssistantList.size()>0) {
//                    ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantList.get(0);
//                    dbzjXmCqjxExecutiveAssistant.setCooperationFlag("1");
//                    dbzjXmCqjxExecutiveAssistant.setCooperationScore(member.getAssessmentScore());
//                    dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
//                    //如果各项评分完成，则计算季度得分
//                    if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getDisciplineFlag(), "1") && StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
//                    	BigDecimal executiveScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
//                    	BigDecimal cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
//                    	BigDecimal disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getTaskScore());
//                    	BigDecimal quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
//                    	double quarterScore = quarterScoreDem.doubleValue();
//                    	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
//                    }  
//                    zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);                      	
//                }                     
//            });
//        	//更新协作性考核表
//            ZjXmCqjxCollaborationAssessment dbzjXmCqjxCollaborationAssessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentMemberList.get(0).getOtherId());
//               // 状态
//               dbzjXmCqjxCollaborationAssessment.setCollaborationState("2");
//               // 共通
//               dbzjXmCqjxCollaborationAssessment.setModifyUserInfo(userKey, realName);
//               zjXmCqjxCollaborationAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessment);
//        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxCollaborationAssessmentMemberList);
        }
	}

	@Override
	public ResponseEntity saveZjXmCqjxCollaborationAssessmentDetailedByMember(
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(Double.parseDouble(zjXmCqjxCollaborationAssessmentMember.getOption()) > 5.0) {
        	return repEntity.layerMessage("NO", "分数不得超过5分，请修改！");
        }
        if(Double.parseDouble(zjXmCqjxCollaborationAssessmentMember.getOption()) < 0) {
        	return repEntity.layerMessage("NO", "不可以评负分！");
        }
        if(zjXmCqjxCollaborationAssessmentMember.getOption().indexOf(".") == -1) {
        	zjXmCqjxCollaborationAssessmentMember.setOption(zjXmCqjxCollaborationAssessmentMember.getOption()+".0");
        }else if(zjXmCqjxCollaborationAssessmentMember.getOption().indexOf(".0") == -1 && zjXmCqjxCollaborationAssessmentMember.getOption().indexOf(".5") == -1) {
        	return repEntity.layerMessage("NO", "评分最小分段为0.5分");
        }
    	ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();
    	zjXmCqjxCollaborationAssessmentDetailed.setCollaborationtId(zjXmCqjxCollaborationAssessmentMember.getOtherId());
    	zjXmCqjxCollaborationAssessmentDetailed.setUserKey(zjXmCqjxCollaborationAssessmentMember.getOaUserId());
    	zjXmCqjxCollaborationAssessmentDetailed.setCreateUser(userKey);
    	if(zjXmCqjxCollaborationAssessmentDetailedMapper.selectByZjXmCqjxCollaborationAssessmentDetailedList(zjXmCqjxCollaborationAssessmentDetailed).size()>0) {
    		ZjXmCqjxCollaborationAssessmentDetailed dbZjXmCqjxCollaborationAssessmentDetailed = zjXmCqjxCollaborationAssessmentDetailedMapper.selectByZjXmCqjxCollaborationAssessmentDetailedList(zjXmCqjxCollaborationAssessmentDetailed).get(0);
    		dbZjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedScore(zjXmCqjxCollaborationAssessmentMember.getOption());
    		dbZjXmCqjxCollaborationAssessmentDetailed.setModifyUserInfo(userKey, realName);
    		zjXmCqjxCollaborationAssessmentDetailedMapper.updateByPrimaryKeySelective(dbZjXmCqjxCollaborationAssessmentDetailed);
    	}else {
            zjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedId(UuidUtil.generate());
            zjXmCqjxCollaborationAssessmentDetailed.setCollaborationtId(zjXmCqjxCollaborationAssessmentMember.getOtherId());
            zjXmCqjxCollaborationAssessmentDetailed.setMemberId(zjXmCqjxCollaborationAssessmentMember.getZcOaId());
            zjXmCqjxCollaborationAssessmentDetailed.setUserKey(zjXmCqjxCollaborationAssessmentMember.getOaUserId());
            zjXmCqjxCollaborationAssessmentDetailed.setUserName(zjXmCqjxCollaborationAssessmentMember.getOaUserName());
            zjXmCqjxCollaborationAssessmentDetailed.setDepartmentId(zjXmCqjxCollaborationAssessmentMember.getOaOrgId());
            zjXmCqjxCollaborationAssessmentDetailed.setDepartmentName(zjXmCqjxCollaborationAssessmentMember.getOaOrgName());
            zjXmCqjxCollaborationAssessmentDetailed.setMobile(zjXmCqjxCollaborationAssessmentMember.getMobile());
            zjXmCqjxCollaborationAssessmentDetailed.setCollaborationDetailedScore(zjXmCqjxCollaborationAssessmentMember.getOption());
            zjXmCqjxCollaborationAssessmentDetailed.setCollaborationAnonymous("***");
            zjXmCqjxCollaborationAssessmentDetailed.setCreateUserInfo(userKey, realName);
            zjXmCqjxCollaborationAssessmentDetailedMapper.insert(zjXmCqjxCollaborationAssessmentDetailed);         		
    	}    
    	return repEntity.ok("sys.data.sava", zjXmCqjxCollaborationAssessmentMember);
	}

	/*
	 * 判断超时，将数据锁定，等待解锁
	 */
	@Override
	public ResponseEntity zjXmCqjxCollaborationAssessmentDetailedTask(
			ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        String userKey = "admin";
        String realName = "admin";		
	        List<ZjXmCqjxCollaborationAssessment> assessmentList = zjXmCqjxCollaborationAssessmentMapper.selectByZjXmCqjxCollaborationAssessmentList(null);
	        for(ZjXmCqjxCollaborationAssessment assessment : assessmentList){
	        	if(StrUtil.equals(assessment.getCollaborationState(), "1") || StrUtil.equals(assessment.getCollaborationState(), "0")) {
		        	if(new Date().after(assessment.getClosingDate())) {
		        		ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
		        		assessmentMember.setOtherId(assessment.getCollaborationId());
		        		List<ZjXmCqjxCollaborationAssessmentMember> memberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(assessmentMember);
		        		boolean bool = memberList.stream().anyMatch(m -> m.getAssessmentFlag().equals("0"));
		        		if(bool) {
		        			//如果未提交评分，则超时
		        			for(ZjXmCqjxCollaborationAssessmentMember member : memberList) {
		        				if(StrUtil.equals(member.getAssessmentFlag(), "0") && StrUtil.isEmpty(member.getAssessmentLock())) {
		        					member.setAssessmentLock("1");
		        					member.setModifyUserInfo(userKey, realName);
		        					zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKeySelective(member);
		        				}
		        			}
		        		}       		
		        	}	        		
	        	}
	        }
	        return repEntity.ok("");
	}

	/*
	 * 判断评分完成，计算协作性得分，考核结束
	 */
	@Override
	public ResponseEntity zjXmCqjxCollaborationAssessmentScoreTask(
			ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
//	       HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String userKey = TokenUtils.getUserKey(request);
//        String realName = TokenUtils.getRealName(request);
    String userKey = "admin";
    String realName = "admin";		
        List<ZjXmCqjxCollaborationAssessment> assessmentList = zjXmCqjxCollaborationAssessmentMapper.selectByZjXmCqjxCollaborationAssessmentList(null);
        for(ZjXmCqjxCollaborationAssessment assessment : assessmentList){
        	if(StrUtil.equals(assessment.getCollaborationState(), "1") || StrUtil.equals(assessment.getCollaborationState(), "0")) {
	        	if(new Date().after(assessment.getClosingDate())) {
	        		ZjXmCqjxCollaborationAssessmentMember assessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
	        		assessmentMember.setOtherId(assessment.getCollaborationId());
	        		List<ZjXmCqjxCollaborationAssessmentMember> memberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentList(assessmentMember);
	        		boolean bool = memberList.stream().anyMatch(m -> m.getAssessmentFlag().equals("1"));
	        		if(bool) {
	    	        //查询所有人的评分结果，如果所有人评分完成，则计算每个人的协作性得分
	    	        assessmentMember.setAssessmentFlag("0");
	    	        List<ZjXmCqjxCollaborationAssessmentMember> assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(assessmentMember);  
	    	        if(assessmentMemberList.size() == 0) {
//	    	        	assessmentMember.setEffectiveFlag("0");		    	        	
	    	        	assessmentMember.setAssessmentFlag("1");
	    	        	assessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(assessmentMember);
	    	            int all = assessmentMemberList.size() - 1;        	
	    	        	assessmentMemberList.parallelStream().forEach((member)->{
	    	        		ZjXmCqjxCollaborationAssessmentDetailed assessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();        		
	    	        		assessmentDetailed.setCollaborationtId(member.getOtherId());
	    	        		assessmentDetailed.setEffectiveFlag("0");
	    	        		assessmentDetailed.setUserKey(member.getOaUserId());
	    	        		List<ZjXmCqjxCollaborationAssessmentDetailed> detailList = zjXmCqjxCollaborationAssessmentDetailedMapper.selectCollaborationAssessmentDetailedByEffectiveFlag(assessmentDetailed);
	    	        		if(detailList.size()>0) {
		    	        		detailList.stream().forEach((detail)->{
		    	        			detail.setScore(Double.parseDouble(detail.getCollaborationDetailedScore()));
		    	        		});
		    	        		if(detailList.size()>2) {
		    	        			//重新排序          
		    	        			detailList = detailList.stream().sorted(Comparator.comparing(ZjXmCqjxCollaborationAssessmentDetailed::getCollaborationDetailedScore).reversed()).collect(toList());
		    	        			//去除最高分、最低分
		    	        			detailList.remove(0);
		    	        			detailList.remove(detailList.size()-1);
		    	        		}
		    	                // 求平均数
		    	                double asDouble = detailList.stream().mapToDouble(ZjXmCqjxCollaborationAssessmentDetailed::getScore).average().getAsDouble();
//		    	                System.out.println("average:" + asDouble);
		    	                member.setAssessmentScore(asDouble);
		    	        		member.setModifyUserInfo(userKey, realName);
		    	        		zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKeySelective(member);		    	        			
	    	        		}else {
		    	                member.setAssessmentScore(0);
		    	        		member.setModifyUserInfo(userKey, realName);
		    	        		zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKeySelective(member);			    	        			
	    	        		}

	    	        		//更新个人协作性评分
	    	        		ZjXmCqjxCollaborationAssessment collaboration = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(member.getOtherId());
	    	        		ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxExecutiveAssistant();
//	    	                zjXmCqjxExecutiveAssistant.setManagerId(member.getManagerId());
	    	        		zjXmCqjxExecutiveAssistant.setAssessmentYears(collaboration.getCollaborationYears());
	    	        		zjXmCqjxExecutiveAssistant.setAssessmentQuarter(collaboration.getCollaborationQuarter());
	    	                zjXmCqjxExecutiveAssistant.setCreateUser(member.getOaUserId());
	    	                List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList = zjXmCqjxExecutiveAssistantMapper.selectByZjXmCqjxExecutiveAssistantList(zjXmCqjxExecutiveAssistant);
	    	                if(zjXmCqjxExecutiveAssistantList.size()>0) {
	    	                    ZjXmCqjxExecutiveAssistant dbzjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantList.get(0);
	    	                    dbzjXmCqjxExecutiveAssistant.setCooperationFlag("1");
	    	                    dbzjXmCqjxExecutiveAssistant.setCooperationScore(member.getAssessmentScore());
	    	                    dbzjXmCqjxExecutiveAssistant.setModifyUserInfo(userKey, realName);
	    	                    //如果各项评分完成，则计算季度得分
	    	                    if(StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getDisciplineFlag(), "1") && StrUtil.equals(dbzjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
	    	                    	BigDecimal executiveScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getDisciplineScore());
	    	                    	BigDecimal cooperationScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getCooperationScore());
	    	                    	BigDecimal disciplineScore = new BigDecimal(dbzjXmCqjxExecutiveAssistant.getTaskScore());
	    	                    	BigDecimal quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
	    	                    	double quarterScore = quarterScoreDem.doubleValue();
	    	                    	dbzjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
	    	                    }  
	    	                    zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKey(dbzjXmCqjxExecutiveAssistant);                      	
	    	                }                     
	    	            });
	    	        	//更新协作性考核表
	    	            ZjXmCqjxCollaborationAssessment dbzjXmCqjxCollaborationAssessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(assessment.getCollaborationId());
	    	               // 状态
	    	               dbzjXmCqjxCollaborationAssessment.setCollaborationState("2");
	    	               // 共通
	    	               dbzjXmCqjxCollaborationAssessment.setModifyUserInfo(userKey, realName);
	    	               zjXmCqjxCollaborationAssessmentMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessment);
	    	        }	        		
	        	}	        		
        	}
        	}
        }
        return repEntity.ok("");
	}
}
