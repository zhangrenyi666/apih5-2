package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmCqjxProjectEvaluationApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;
import com.apih5.service.ZjXmCqjxProjectEvaluationApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectEvaluationApprovalService")
public class ZjXmCqjxProjectEvaluationApprovalServiceImpl implements ZjXmCqjxProjectEvaluationApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectEvaluationApprovalMapper zjXmCqjxProjectEvaluationApprovalMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectEvaluationApprovalListByCondition(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        if (zjXmCqjxProjectEvaluationApproval == null) {
            zjXmCqjxProjectEvaluationApproval = new ZjXmCqjxProjectEvaluationApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectEvaluationApproval.getPage(),zjXmCqjxProjectEvaluationApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectEvaluationApproval> zjXmCqjxProjectEvaluationApprovalList = zjXmCqjxProjectEvaluationApprovalMapper.selectByZjXmCqjxProjectEvaluationApprovalList(zjXmCqjxProjectEvaluationApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectEvaluationApproval> p = new PageInfo<>(zjXmCqjxProjectEvaluationApprovalList);

        return repEntity.okList(zjXmCqjxProjectEvaluationApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectEvaluationApprovalDetails(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        if (zjXmCqjxProjectEvaluationApproval == null) {
            zjXmCqjxProjectEvaluationApproval = new ZjXmCqjxProjectEvaluationApproval();
        }
        // 获取数据
        ZjXmCqjxProjectEvaluationApproval dbZjXmCqjxProjectEvaluationApproval = zjXmCqjxProjectEvaluationApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectEvaluationApproval.getAssistantLeaderApprovalId());
        // 数据存在
        if (dbZjXmCqjxProjectEvaluationApproval != null) {
            return repEntity.ok(dbZjXmCqjxProjectEvaluationApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectEvaluationApproval(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectEvaluationApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        zjXmCqjxProjectEvaluationApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectEvaluationApprovalMapper.insert(zjXmCqjxProjectEvaluationApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectEvaluationApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectEvaluationApproval(ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectEvaluationApproval dbzjXmCqjxProjectEvaluationApproval = zjXmCqjxProjectEvaluationApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectEvaluationApproval.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxProjectEvaluationApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectEvaluationApproval.getAssistantLeaderApprovalId())) {
        	if(zjXmCqjxProjectEvaluationApproval.getLeaderScore()>zjXmCqjxProjectEvaluationApproval.getItemScore()) {
        		return repEntity.layerMessage("NO", "评分不能超过考核分数！");
        	}
           // 其他关联表id
//           dbzjXmCqjxProjectEvaluationApproval.setExecutiveId(zjXmCqjxProjectEvaluationApproval.getExecutiveId());
//           // 其他类型
//           dbzjXmCqjxProjectEvaluationApproval.setOtherType(zjXmCqjxProjectEvaluationApproval.getOtherType());
//           // 领导ID
//           dbzjXmCqjxProjectEvaluationApproval.setLeaderId(zjXmCqjxProjectEvaluationApproval.getLeaderId());
//           // 领导名称
//           dbzjXmCqjxProjectEvaluationApproval.setLeaderName(zjXmCqjxProjectEvaluationApproval.getLeaderName());
//           // 领导所属部门ID
//           dbzjXmCqjxProjectEvaluationApproval.setLeaderOrgId(zjXmCqjxProjectEvaluationApproval.getLeaderOrgId());
           // 领导评分
           dbzjXmCqjxProjectEvaluationApproval.setLeaderScore(zjXmCqjxProjectEvaluationApproval.getLeaderScore());
           // 审批FLG
//           dbzjXmCqjxProjectEvaluationApproval.setApprovalFlag(zjXmCqjxProjectEvaluationApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxProjectEvaluationApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectEvaluationApprovalMapper.updateByPrimaryKey(dbzjXmCqjxProjectEvaluationApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectEvaluationApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectEvaluationApproval(List<ZjXmCqjxProjectEvaluationApproval> zjXmCqjxProjectEvaluationApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectEvaluationApprovalList != null && zjXmCqjxProjectEvaluationApprovalList.size() > 0) {
           ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval = new ZjXmCqjxProjectEvaluationApproval();
           zjXmCqjxProjectEvaluationApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectEvaluationApprovalMapper.batchDeleteUpdateZjXmCqjxProjectEvaluationApproval(zjXmCqjxProjectEvaluationApprovalList, zjXmCqjxProjectEvaluationApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectEvaluationApprovalList);
        }
    }
}