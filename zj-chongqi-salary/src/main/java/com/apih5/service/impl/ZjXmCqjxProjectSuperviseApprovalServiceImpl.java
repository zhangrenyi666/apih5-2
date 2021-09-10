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
import com.apih5.mybatis.dao.ZjXmCqjxProjectSuperviseApprovalMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;
import com.apih5.service.ZjXmCqjxProjectSuperviseApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectSuperviseApprovalService")
public class ZjXmCqjxProjectSuperviseApprovalServiceImpl implements ZjXmCqjxProjectSuperviseApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectSuperviseApprovalMapper zjXmCqjxProjectSuperviseApprovalMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectSuperviseApprovalListByCondition(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        if (zjXmCqjxProjectSuperviseApproval == null) {
            zjXmCqjxProjectSuperviseApproval = new ZjXmCqjxProjectSuperviseApproval();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectSuperviseApproval.getPage(),zjXmCqjxProjectSuperviseApproval.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectSuperviseApproval> zjXmCqjxProjectSuperviseApprovalList = zjXmCqjxProjectSuperviseApprovalMapper.selectByZjXmCqjxProjectSuperviseApprovalList(zjXmCqjxProjectSuperviseApproval);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectSuperviseApproval> p = new PageInfo<>(zjXmCqjxProjectSuperviseApprovalList);

        return repEntity.okList(zjXmCqjxProjectSuperviseApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectSuperviseApprovalDetails(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        if (zjXmCqjxProjectSuperviseApproval == null) {
            zjXmCqjxProjectSuperviseApproval = new ZjXmCqjxProjectSuperviseApproval();
        }
        // 获取数据
        ZjXmCqjxProjectSuperviseApproval dbZjXmCqjxProjectSuperviseApproval = zjXmCqjxProjectSuperviseApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectSuperviseApproval.getAssistantLeaderApprovalId());
        // 数据存在
        if (dbZjXmCqjxProjectSuperviseApproval != null) {
            return repEntity.ok(dbZjXmCqjxProjectSuperviseApproval);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectSuperviseApproval(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectSuperviseApproval.setAssistantLeaderApprovalId(UuidUtil.generate());
        zjXmCqjxProjectSuperviseApproval.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectSuperviseApprovalMapper.insert(zjXmCqjxProjectSuperviseApproval);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectSuperviseApproval);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectSuperviseApproval(ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectSuperviseApproval dbzjXmCqjxProjectSuperviseApproval = zjXmCqjxProjectSuperviseApprovalMapper.selectByPrimaryKey(zjXmCqjxProjectSuperviseApproval.getAssistantLeaderApprovalId());
        if (dbzjXmCqjxProjectSuperviseApproval != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectSuperviseApproval.getAssistantLeaderApprovalId())) {
        	if(zjXmCqjxProjectSuperviseApproval.getLeaderScore()>zjXmCqjxProjectSuperviseApproval.getItemScore()) {
        		return repEntity.layerMessage("NO", "评分不能超过考核分数！");
        	}        	
           // 其他关联表id
//           dbzjXmCqjxProjectSuperviseApproval.setExecutiveId(zjXmCqjxProjectSuperviseApproval.getExecutiveId());
           // 其他类型
//           dbzjXmCqjxProjectSuperviseApproval.setOtherType(zjXmCqjxProjectSuperviseApproval.getOtherType());
           // 领导ID
//           dbzjXmCqjxProjectSuperviseApproval.setLeaderId(zjXmCqjxProjectSuperviseApproval.getLeaderId());
           // 领导名称
//           dbzjXmCqjxProjectSuperviseApproval.setLeaderName(zjXmCqjxProjectSuperviseApproval.getLeaderName());
           // 领导所属部门ID
//           dbzjXmCqjxProjectSuperviseApproval.setLeaderOrgId(zjXmCqjxProjectSuperviseApproval.getLeaderOrgId());
           // 领导评分
           dbzjXmCqjxProjectSuperviseApproval.setLeaderScore(zjXmCqjxProjectSuperviseApproval.getLeaderScore());
           // 审批FLG
//           dbzjXmCqjxProjectSuperviseApproval.setApprovalFlag(zjXmCqjxProjectSuperviseApproval.getApprovalFlag());
           // 共通
           dbzjXmCqjxProjectSuperviseApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSuperviseApprovalMapper.updateByPrimaryKey(dbzjXmCqjxProjectSuperviseApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectSuperviseApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSuperviseApproval(List<ZjXmCqjxProjectSuperviseApproval> zjXmCqjxProjectSuperviseApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectSuperviseApprovalList != null && zjXmCqjxProjectSuperviseApprovalList.size() > 0) {
           ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval = new ZjXmCqjxProjectSuperviseApproval();
           zjXmCqjxProjectSuperviseApproval.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSuperviseApprovalMapper.batchDeleteUpdateZjXmCqjxProjectSuperviseApproval(zjXmCqjxProjectSuperviseApprovalList, zjXmCqjxProjectSuperviseApproval);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectSuperviseApprovalList);
        }
    }
}