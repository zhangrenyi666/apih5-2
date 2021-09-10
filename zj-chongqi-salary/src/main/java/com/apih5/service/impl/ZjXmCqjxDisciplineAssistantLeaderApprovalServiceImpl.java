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
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssistantLeaderApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxDisciplineAssistantLeaderApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxDisciplineAssistantLeaderApprovalService")
public class ZjXmCqjxDisciplineAssistantLeaderApprovalServiceImpl implements ZjXmCqjxDisciplineAssistantLeaderApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssistantLeaderApprovalMapper zjXmCqjxDisciplineAssistantLeaderApprovalMapper;

    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssistantLeaderApprovalListByCondition(ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        if (zjXmCqjxDisciplineAssistantLeaderApproval == null) {
            zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxDisciplineAssistantLeaderApproval.getPage(),zjXmCqjxDisciplineAssistantLeaderApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxDisciplineAssistantLeaderApproval> zjXmCqjxDisciplineAssistantLeaderApprovalList = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByZjXmCqjxDisciplineAssistantLeaderApprovalList(zjXmCqjxDisciplineAssistantLeaderApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxDisciplineAssistantLeaderApproval> p = new PageInfo<>(zjXmCqjxDisciplineAssistantLeaderApprovalList);

        return repEntity.okList(zjXmCqjxDisciplineAssistantLeaderApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssistantLeaderApprovalDetails(ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        if (zjXmCqjxDisciplineAssistantLeaderApproval == null) {
            zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
        }
        // 获取数据
        ZjXmCqjxDisciplineAssistantLeaderApproval dbZjXmCqjxDisciplineAssistantLeaderApproval = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssistantLeaderApproval.getAssistantLeaderApprovalId());
        // 数据存在
        if (dbZjXmCqjxDisciplineAssistantLeaderApproval != null) {
            return repEntity.ok(dbZjXmCqjxDisciplineAssistantLeaderApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDisciplineAssistantLeaderApproval(ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxDisciplineAssistantLeaderApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        zjXmCqjxDisciplineAssistantLeaderApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.insert(zjXmCqjxDisciplineAssistantLeaderApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDisciplineAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDisciplineAssistantLeaderApproval(ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxDisciplineAssistantLeaderApproval dbzjXmCqjxDisciplineAssistantLeaderApproval = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssistantLeaderApproval.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxDisciplineAssistantLeaderApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssistantLeaderApproval.getAssistantLeaderApprovalId())) {
           // 其他关联表id
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setExecutiveId(zjXmCqjxDisciplineAssistantLeaderApproval.getExecutiveId());
           // 其他类型
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setOtherType(zjXmCqjxDisciplineAssistantLeaderApproval.getOtherType());
           // 领导ID
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setLeaderId(zjXmCqjxDisciplineAssistantLeaderApproval.getLeaderId());
           // 领导名称
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setLeaderName(zjXmCqjxDisciplineAssistantLeaderApproval.getLeaderName());
           // 领导所属部门ID
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setLeaderOrgId(zjXmCqjxDisciplineAssistantLeaderApproval.getLeaderOrgId());
           // 领导意见
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setLeaderOption(zjXmCqjxDisciplineAssistantLeaderApproval.getLeaderOption());
           // 领导评分
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setLeaderScore(zjXmCqjxDisciplineAssistantLeaderApproval.getLeaderScore());
           // 审批FLG
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setApprovalFlag(zjXmCqjxDisciplineAssistantLeaderApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxDisciplineAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssistantLeaderApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssistantLeaderApproval(List<ZjXmCqjxDisciplineAssistantLeaderApproval> zjXmCqjxDisciplineAssistantLeaderApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDisciplineAssistantLeaderApprovalList != null && zjXmCqjxDisciplineAssistantLeaderApprovalList.size() > 0) {
           ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval = new ZjXmCqjxDisciplineAssistantLeaderApproval();
           zjXmCqjxDisciplineAssistantLeaderApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssistantLeaderApprovalMapper.batchDeleteUpdateZjXmCqjxDisciplineAssistantLeaderApproval(zjXmCqjxDisciplineAssistantLeaderApprovalList, zjXmCqjxDisciplineAssistantLeaderApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDisciplineAssistantLeaderApprovalList);
        }
    }
}
