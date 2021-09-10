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
import com.apih5.mybatis.dao.ZjXmCqjxAssistantLeaderApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxAssistantLeaderApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxAssistantLeaderApprovalService")
public class ZjXmCqjxAssistantLeaderApprovalServiceImpl implements ZjXmCqjxAssistantLeaderApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxAssistantLeaderApprovalMapper zjXmCqjxAssistantLeaderApprovalMapper;

    @Override
    public ResponseEntity getZjXmCqjxAssistantLeaderApprovalListByCondition(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        if (zjXmCqjxAssistantLeaderApproval == null) {
            zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxAssistantLeaderApproval.getPage(),zjXmCqjxAssistantLeaderApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList = zjXmCqjxAssistantLeaderApprovalMapper.selectByZjXmCqjxAssistantLeaderApprovalList(zjXmCqjxAssistantLeaderApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxAssistantLeaderApproval> p = new PageInfo<>(zjXmCqjxAssistantLeaderApprovalList);

        return repEntity.okList(zjXmCqjxAssistantLeaderApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxAssistantLeaderApprovalDetails(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        if (zjXmCqjxAssistantLeaderApproval == null) {
            zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
        }
        // 获取数据
        ZjXmCqjxAssistantLeaderApproval dbZjXmCqjxAssistantLeaderApproval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxAssistantLeaderApproval.getAssistantLeaderApprovalId());
        // 数据存在
        if (dbZjXmCqjxAssistantLeaderApproval != null) {
            return repEntity.ok(dbZjXmCqjxAssistantLeaderApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxAssistantLeaderApproval(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        zjXmCqjxAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxAssistantLeaderApprovalMapper.insert(zjXmCqjxAssistantLeaderApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxAssistantLeaderApproval(ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxAssistantLeaderApproval dbzjXmCqjxAssistantLeaderApproval = zjXmCqjxAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxAssistantLeaderApproval.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxAssistantLeaderApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxAssistantLeaderApproval.getAssistantLeaderApprovalId())) {
           // 其他关联表id
           dbzjXmCqjxAssistantLeaderApproval.setExecutiveId(zjXmCqjxAssistantLeaderApproval.getExecutiveId());
           // 其他类型
           dbzjXmCqjxAssistantLeaderApproval.setOtherType(zjXmCqjxAssistantLeaderApproval.getOtherType());
           // 领导ID
           dbzjXmCqjxAssistantLeaderApproval.setLeaderId(zjXmCqjxAssistantLeaderApproval.getLeaderId());
           // 领导名称
           dbzjXmCqjxAssistantLeaderApproval.setLeaderName(zjXmCqjxAssistantLeaderApproval.getLeaderName());
           // 领导所属部门ID
           dbzjXmCqjxAssistantLeaderApproval.setLeaderOrgId(zjXmCqjxAssistantLeaderApproval.getLeaderOrgId());
           // 领导意见
           dbzjXmCqjxAssistantLeaderApproval.setLeaderOption(zjXmCqjxAssistantLeaderApproval.getLeaderOption());
           // 领导评分
           dbzjXmCqjxAssistantLeaderApproval.setLeaderScore(zjXmCqjxAssistantLeaderApproval.getLeaderScore());
           // 审批FLG
           dbzjXmCqjxAssistantLeaderApproval.setApprovalFlag(zjXmCqjxAssistantLeaderApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxAssistantLeaderApprovalMapper.updateByPrimaryKey(dbzjXmCqjxAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxAssistantLeaderApproval(List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxAssistantLeaderApprovalList != null && zjXmCqjxAssistantLeaderApprovalList.size() > 0) {
           ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval = new ZjXmCqjxAssistantLeaderApproval();
           zjXmCqjxAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxAssistantLeaderApprovalMapper.batchDeleteUpdateZjXmCqjxAssistantLeaderApproval(zjXmCqjxAssistantLeaderApprovalList, zjXmCqjxAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxAssistantLeaderApprovalList);
        }
    }
}
