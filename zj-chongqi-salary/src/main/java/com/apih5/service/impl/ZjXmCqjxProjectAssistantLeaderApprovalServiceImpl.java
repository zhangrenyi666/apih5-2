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
import com.apih5.mybatis.dao.ZjXmCqjxProjectAssistantLeaderApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxProjectAssistantLeaderApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectAssistantLeaderApprovalService")
public class ZjXmCqjxProjectAssistantLeaderApprovalServiceImpl implements ZjXmCqjxProjectAssistantLeaderApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectAssistantLeaderApprovalMapper zjXmCqjxProjectAssistantLeaderApprovalMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectAssistantLeaderApprovalListByCondition(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        if (zjXmCqjxProjectAssistantLeaderApproval == null) {
            zjXmCqjxProjectAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectAssistantLeaderApproval.getPage(),zjXmCqjxProjectAssistantLeaderApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxProjectAssistantLeaderApprovalList = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByZjXmCqjxProjectAssistantLeaderApprovalList(zjXmCqjxProjectAssistantLeaderApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectAssistantLeaderApproval> p = new PageInfo<>(zjXmCqjxProjectAssistantLeaderApprovalList);

        return repEntity.okList(zjXmCqjxProjectAssistantLeaderApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectAssistantLeaderApprovalDetails(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        if (zjXmCqjxProjectAssistantLeaderApproval == null) {
            zjXmCqjxProjectAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
        }
        // 获取数据
        ZjXmCqjxProjectAssistantLeaderApproval dbZjXmCqjxProjectAssistantLeaderApproval = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectAssistantLeaderApproval.getAssistantLeaderApprovalId());
        // 数据存在
        if (dbZjXmCqjxProjectAssistantLeaderApproval != null) {
            return repEntity.ok(dbZjXmCqjxProjectAssistantLeaderApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectAssistantLeaderApproval(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        zjXmCqjxProjectAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectAssistantLeaderApprovalMapper.insert(zjXmCqjxProjectAssistantLeaderApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectAssistantLeaderApproval(ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectAssistantLeaderApproval dbzjXmCqjxProjectAssistantLeaderApproval = zjXmCqjxProjectAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectAssistantLeaderApproval.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxProjectAssistantLeaderApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectAssistantLeaderApproval.getAssistantLeaderApprovalId())) {
           // 其他关联表id
           dbzjXmCqjxProjectAssistantLeaderApproval.setExecutiveId(zjXmCqjxProjectAssistantLeaderApproval.getExecutiveId());
           // 其他类型
           dbzjXmCqjxProjectAssistantLeaderApproval.setOtherType(zjXmCqjxProjectAssistantLeaderApproval.getOtherType());
           // 领导ID
           dbzjXmCqjxProjectAssistantLeaderApproval.setLeaderId(zjXmCqjxProjectAssistantLeaderApproval.getLeaderId());
           // 领导名称
           dbzjXmCqjxProjectAssistantLeaderApproval.setLeaderName(zjXmCqjxProjectAssistantLeaderApproval.getLeaderName());
           // 领导所属部门ID
           dbzjXmCqjxProjectAssistantLeaderApproval.setLeaderOrgId(zjXmCqjxProjectAssistantLeaderApproval.getLeaderOrgId());
           // 领导意见
           dbzjXmCqjxProjectAssistantLeaderApproval.setLeaderOption(zjXmCqjxProjectAssistantLeaderApproval.getLeaderOption());
           // 领导评分
           dbzjXmCqjxProjectAssistantLeaderApproval.setLeaderScore(zjXmCqjxProjectAssistantLeaderApproval.getLeaderScore());
           // 审批FLG
           dbzjXmCqjxProjectAssistantLeaderApproval.setApprovalFlag(zjXmCqjxProjectAssistantLeaderApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxProjectAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectAssistantLeaderApprovalMapper.updateByPrimaryKey(dbzjXmCqjxProjectAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval(List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxProjectAssistantLeaderApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectAssistantLeaderApprovalList != null && zjXmCqjxProjectAssistantLeaderApprovalList.size() > 0) {
           ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval = new ZjXmCqjxProjectAssistantLeaderApproval();
           zjXmCqjxProjectAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectAssistantLeaderApprovalMapper.batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval(zjXmCqjxProjectAssistantLeaderApprovalList, zjXmCqjxProjectAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectAssistantLeaderApprovalList);
        }
    }
}