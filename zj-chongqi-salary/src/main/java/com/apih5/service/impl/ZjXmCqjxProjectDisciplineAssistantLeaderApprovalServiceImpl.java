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
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssistantLeaderApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectDisciplineAssistantLeaderApprovalService")
public class ZjXmCqjxProjectDisciplineAssistantLeaderApprovalServiceImpl implements ZjXmCqjxProjectDisciplineAssistantLeaderApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssistantLeaderApprovalListByCondition(ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        if (zjXmCqjxProjectDisciplineAssistantLeaderApproval == null) {
            zjXmCqjxProjectDisciplineAssistantLeaderApproval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getPage(),zjXmCqjxProjectDisciplineAssistantLeaderApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> zjXmCqjxProjectDisciplineAssistantLeaderApprovalList = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectDisciplineAssistantLeaderApprovalList(zjXmCqjxProjectDisciplineAssistantLeaderApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> p = new PageInfo<>(zjXmCqjxProjectDisciplineAssistantLeaderApprovalList);

        return repEntity.okList(zjXmCqjxProjectDisciplineAssistantLeaderApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssistantLeaderApprovalDetails(ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        if (zjXmCqjxProjectDisciplineAssistantLeaderApproval == null) {
            zjXmCqjxProjectDisciplineAssistantLeaderApproval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
        }
        // 获取数据
        ZjXmCqjxProjectDisciplineAssistantLeaderApproval dbZjXmCqjxProjectDisciplineAssistantLeaderApproval = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getAssistantLeaderApprovalId());
        // 数据存在
        if (dbZjXmCqjxProjectDisciplineAssistantLeaderApproval != null) {
            return repEntity.ok(dbZjXmCqjxProjectDisciplineAssistantLeaderApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDisciplineAssistantLeaderApproval(ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectDisciplineAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        zjXmCqjxProjectDisciplineAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.insert(zjXmCqjxProjectDisciplineAssistantLeaderApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDisciplineAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssistantLeaderApproval(ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectDisciplineAssistantLeaderApproval dbzjXmCqjxProjectDisciplineAssistantLeaderApproval = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxProjectDisciplineAssistantLeaderApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.getAssistantLeaderApprovalId())) {
           // 其他关联表id
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setExecutiveId(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getExecutiveId());
           // 其他类型
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setOtherType(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getOtherType());
           // 领导ID
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setLeaderId(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getLeaderId());
           // 领导名称
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setLeaderName(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getLeaderName());
           // 领导所属部门ID
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setLeaderOrgId(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getLeaderOrgId());
           // 领导意见
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setLeaderOption(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getLeaderOption());
           // 领导评分
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setLeaderScore(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getLeaderScore());
           // 审批FLG
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setApprovalFlag(zjXmCqjxProjectDisciplineAssistantLeaderApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxProjectDisciplineAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKey(dbzjXmCqjxProjectDisciplineAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssistantLeaderApproval(List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> zjXmCqjxProjectDisciplineAssistantLeaderApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDisciplineAssistantLeaderApprovalList != null && zjXmCqjxProjectDisciplineAssistantLeaderApprovalList.size() > 0) {
           ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval = new ZjXmCqjxProjectDisciplineAssistantLeaderApproval();
           zjXmCqjxProjectDisciplineAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssistantLeaderApprovalMapper.batchDeleteUpdateZjXmCqjxProjectDisciplineAssistantLeaderApproval(zjXmCqjxProjectDisciplineAssistantLeaderApprovalList, zjXmCqjxProjectDisciplineAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDisciplineAssistantLeaderApprovalList);
        }
    }
}