package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxAssessmentManageMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.dao.ZjXmCqjxOaDeptMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxYearScoreApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxYearScoreApproval;
import com.apih5.service.ZjXmCqjxYearScoreApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxYearScoreApprovalService")
public class ZjXmCqjxYearScoreApprovalServiceImpl implements ZjXmCqjxYearScoreApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxYearScoreApprovalMapper zjXmCqjxYearScoreApprovalMapper;
    
	@Autowired(required = true)
	private ZjXmCqjxAssessmentManageMapper zjXmCqjxAssessmentManageMapper;

	@Autowired(required = true)
	private ZjXmCqjxOaDeptMemberMapper zjXmCqjxOaDeptMemberMapper;

	@Autowired(required = true)
	private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;
    
	@Autowired
	private SysDepartmentService sysDepartmentService;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private ZjXmCqjxDisciplineAssessmentMapper zjXmCqjxDisciplineAssessmentMapper;

	@Autowired(required = true)
	private ZjXmCqjxDisciplineAssessmentMemberMapper zjXmCqjxDisciplineAssessmentMemberMapper;

	@Autowired(required = true)
	private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;
	
    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxYearScoreApprovalListByCondition(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        if (zjXmCqjxYearScoreApproval == null) {
            zjXmCqjxYearScoreApproval = new ZjXmCqjxYearScoreApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxYearScoreApproval.getPage(),zjXmCqjxYearScoreApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxYearScoreApproval> zjXmCqjxYearScoreApprovalList = zjXmCqjxYearScoreApprovalMapper.selectByZjXmCqjxYearScoreApprovalList(zjXmCqjxYearScoreApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxYearScoreApproval> p = new PageInfo<>(zjXmCqjxYearScoreApprovalList);

        return repEntity.okList(zjXmCqjxYearScoreApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxYearScoreApprovalDetails(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        if (zjXmCqjxYearScoreApproval == null) {
            zjXmCqjxYearScoreApproval = new ZjXmCqjxYearScoreApproval();
        }
        // 获取数据
        ZjXmCqjxYearScoreApproval dbZjXmCqjxYearScoreApproval = zjXmCqjxYearScoreApprovalMapper.selectByPrimaryKey(zjXmCqjxYearScoreApproval.getYearScoreApprovalId());
        // 数据存在
        if (dbZjXmCqjxYearScoreApproval != null) {
            return repEntity.ok(dbZjXmCqjxYearScoreApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxYearScoreApproval(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxYearScoreApproval.setYearScoreApprovalId(UuidUtil.generate());
        zjXmCqjxYearScoreApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxYearScoreApprovalMapper.insert(zjXmCqjxYearScoreApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxYearScoreApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxYearScoreApproval(ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxYearScoreApproval dbzjXmCqjxYearScoreApproval = zjXmCqjxYearScoreApprovalMapper.selectByPrimaryKey(zjXmCqjxYearScoreApproval.getYearScoreApprovalId());
        if (dbzjXmCqjxYearScoreApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxYearScoreApproval.getYearScoreApprovalId())) {
        	if(zjXmCqjxYearScoreApproval.getLeaderScore()>zjXmCqjxYearScoreApproval.getItemScore()) {
        		return repEntity.layerMessage("NO", "评分不能超过考核分数！");
        	}
           // 其他关联表id
           dbzjXmCqjxYearScoreApproval.setExecutiveId(zjXmCqjxYearScoreApproval.getExecutiveId());
           // 其他类型
           dbzjXmCqjxYearScoreApproval.setOtherType(zjXmCqjxYearScoreApproval.getOtherType());
           // 节点类型
           dbzjXmCqjxYearScoreApproval.setItemType(zjXmCqjxYearScoreApproval.getItemType());
           // 领导ID
           dbzjXmCqjxYearScoreApproval.setLeaderId(zjXmCqjxYearScoreApproval.getLeaderId());
           // 领导名称
           dbzjXmCqjxYearScoreApproval.setLeaderName(zjXmCqjxYearScoreApproval.getLeaderName());
           // 领导所属部门ID
           dbzjXmCqjxYearScoreApproval.setLeaderOrgId(zjXmCqjxYearScoreApproval.getLeaderOrgId());
           // 领导评分
           dbzjXmCqjxYearScoreApproval.setLeaderScore(zjXmCqjxYearScoreApproval.getLeaderScore());
           // 审批FLG
           dbzjXmCqjxYearScoreApproval.setApprovalFlag(zjXmCqjxYearScoreApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxYearScoreApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxYearScoreApprovalMapper.updateByPrimaryKeySelective(dbzjXmCqjxYearScoreApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxYearScoreApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxYearScoreApproval(List<ZjXmCqjxYearScoreApproval> zjXmCqjxYearScoreApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxYearScoreApprovalList != null && zjXmCqjxYearScoreApprovalList.size() > 0) {
           ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval = new ZjXmCqjxYearScoreApproval();
           zjXmCqjxYearScoreApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxYearScoreApprovalMapper.batchDeleteUpdateZjXmCqjxYearScoreApproval(zjXmCqjxYearScoreApprovalList, zjXmCqjxYearScoreApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxYearScoreApprovalList);
        }
    }

}