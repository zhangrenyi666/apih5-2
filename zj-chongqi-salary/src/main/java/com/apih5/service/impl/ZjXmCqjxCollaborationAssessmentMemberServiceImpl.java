package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxCollaborationAssessmentMemberMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxCollaborationAssessmentMemberService")
public class ZjXmCqjxCollaborationAssessmentMemberServiceImpl implements ZjXmCqjxCollaborationAssessmentMemberService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMemberMapper zjXmCqjxCollaborationAssessmentMemberMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMapper zjXmCqjxCollaborationAssessmentMapper;

	@Override
	public ResponseEntity getZjXmCqjxCollaborationMemberDetailsNoSelf(
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        if (zjXmCqjxCollaborationAssessmentMember == null) {
            zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        }
        if(StrUtil.isNotEmpty(zjXmCqjxCollaborationAssessmentMember.getCollaborationId())) {
        	zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessmentMember.getCollaborationId());	
        	zjXmCqjxCollaborationAssessmentMember.setCreateUser(userKey);
        }
        // 筛除自己的数据
        List<ZjXmCqjxCollaborationAssessmentMember> memberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(zjXmCqjxCollaborationAssessmentMember);
        memberList.removeIf(member -> member.getOaUserId().contains(userKey));
        return repEntity.okList(memberList, memberList.size());
	}

	@Override
	public ResponseEntity getZjXmCqjxCollaborationAssessmentListByUser(
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        if (zjXmCqjxCollaborationAssessmentMember == null) {
            zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        }
        zjXmCqjxCollaborationAssessmentMember.setOaUserId(userKey);
        // 分页查询
        PageHelper.startPage(zjXmCqjxCollaborationAssessmentMember.getPage(),zjXmCqjxCollaborationAssessmentMember.getLimit());
        // 获取数据
        List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentListByUser(zjXmCqjxCollaborationAssessmentMember);
        zjXmCqjxCollaborationAssessmentMemberList.parallelStream().forEach((member)->{
        	member.setCollaborationId(member.getOtherId());
        });
        // 得到分页信息
        PageInfo<ZjXmCqjxCollaborationAssessmentMember> p = new PageInfo<>(zjXmCqjxCollaborationAssessmentMemberList);

        return repEntity.okList(zjXmCqjxCollaborationAssessmentMemberList, p.getTotal());
	}
	
    @Override
    public ResponseEntity getZjXmCqjxCollaborationAssessmentMemberListByCondition(ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        if (zjXmCqjxCollaborationAssessmentMember == null) {
            zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        }
        if(StrUtil.isNotEmpty(zjXmCqjxCollaborationAssessmentMember.getCollaborationId())) {
        	zjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessmentMember.getCollaborationId());
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxCollaborationAssessmentMember.getPage(),zjXmCqjxCollaborationAssessmentMember.getLimit());
        // 获取数据
        List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList = zjXmCqjxCollaborationAssessmentMemberMapper.selectByZjXmCqjxCollaborationAssessmentMemberList(zjXmCqjxCollaborationAssessmentMember);
        // 得到分页信息
        PageInfo<ZjXmCqjxCollaborationAssessmentMember> p = new PageInfo<>(zjXmCqjxCollaborationAssessmentMemberList);

        return repEntity.okList(zjXmCqjxCollaborationAssessmentMemberList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxCollaborationAssessmentMemberDetails(ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        if (zjXmCqjxCollaborationAssessmentMember == null) {
            zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
        }
        // 获取数据
        ZjXmCqjxCollaborationAssessmentMember dbZjXmCqjxCollaborationAssessmentMember = zjXmCqjxCollaborationAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentMember.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxCollaborationAssessmentMember != null) {
            return repEntity.ok(dbZjXmCqjxCollaborationAssessmentMember);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxCollaborationAssessmentMember(ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);         
        zjXmCqjxCollaborationAssessmentMember.setZcOaId(UuidUtil.generate());
        zjXmCqjxCollaborationAssessmentMember.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxCollaborationAssessmentMemberMapper.insert(zjXmCqjxCollaborationAssessmentMember);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxCollaborationAssessmentMember);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxCollaborationAssessmentMember(ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxCollaborationAssessmentMember dbzjXmCqjxCollaborationAssessmentMember = zjXmCqjxCollaborationAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentMember.getZcOaId());
        if (dbzjXmCqjxCollaborationAssessmentMember != null && StrUtil.isNotEmpty(dbzjXmCqjxCollaborationAssessmentMember.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxCollaborationAssessmentMember.setOtherId(zjXmCqjxCollaborationAssessmentMember.getOtherId());
           // 其他类型
           dbzjXmCqjxCollaborationAssessmentMember.setOtherType(zjXmCqjxCollaborationAssessmentMember.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxCollaborationAssessmentMember.setOaDepartmentMemberFlag(zjXmCqjxCollaborationAssessmentMember.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxCollaborationAssessmentMember.setOaUserId(zjXmCqjxCollaborationAssessmentMember.getOaUserId());
           // oaUserName
           dbzjXmCqjxCollaborationAssessmentMember.setOaUserName(zjXmCqjxCollaborationAssessmentMember.getOaUserName());
           // oaOrgId
           dbzjXmCqjxCollaborationAssessmentMember.setOaOrgId(zjXmCqjxCollaborationAssessmentMember.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxCollaborationAssessmentMember.setOaOrgName(zjXmCqjxCollaborationAssessmentMember.getOaOrgName());
           // 手机号
           dbzjXmCqjxCollaborationAssessmentMember.setMobile(zjXmCqjxCollaborationAssessmentMember.getMobile());
           // 考核flag
           dbzjXmCqjxCollaborationAssessmentMember.setAssessmentFlag(zjXmCqjxCollaborationAssessmentMember.getAssessmentFlag());
           // 考核得分
           dbzjXmCqjxCollaborationAssessmentMember.setAssessmentScore(zjXmCqjxCollaborationAssessmentMember.getAssessmentScore());
           // 共通
           dbzjXmCqjxCollaborationAssessmentMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKey(dbzjXmCqjxCollaborationAssessmentMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxCollaborationAssessmentMember);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxCollaborationAssessmentMemberList != null && zjXmCqjxCollaborationAssessmentMemberList.size() > 0) {
           ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember = new ZjXmCqjxCollaborationAssessmentMember();
           zjXmCqjxCollaborationAssessmentMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxCollaborationAssessmentMemberMapper.batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(zjXmCqjxCollaborationAssessmentMemberList, zjXmCqjxCollaborationAssessmentMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxCollaborationAssessmentMemberList);
        }
    }

	@Override
	public ResponseEntity checkZjXmCqjxCollaborationAssessmentScore(
			ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        Date now = new Date();
        ZjXmCqjxCollaborationAssessment assessment = zjXmCqjxCollaborationAssessmentMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentMember.getOtherId());
		if(now.after(assessment.getClosingDate())) {
			ZjXmCqjxCollaborationAssessmentMember dbAssistantMember = zjXmCqjxCollaborationAssessmentMemberMapper.selectByPrimaryKey(zjXmCqjxCollaborationAssessmentMember.getZcOaId());
			dbAssistantMember.setEffectiveFlag("1");
			dbAssistantMember.setAssessmentFlag("1");
			dbAssistantMember.setModifyUserInfo(userKey, realName);
			zjXmCqjxCollaborationAssessmentMemberMapper.updateByPrimaryKeySelective(dbAssistantMember);
			return repEntity.layerMessage("NO", "已经超过评分截止日期，无法评分");
		}	
		return null;
	}
}
