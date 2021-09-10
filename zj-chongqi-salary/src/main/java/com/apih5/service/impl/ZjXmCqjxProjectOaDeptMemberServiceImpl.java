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
import com.apih5.mybatis.dao.ZjXmCqjxProjectOaDeptMemberMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOaDeptMember;
import com.apih5.service.ZjXmCqjxProjectOaDeptMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectOaDeptMemberService")
public class ZjXmCqjxProjectOaDeptMemberServiceImpl implements ZjXmCqjxProjectOaDeptMemberService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectOaDeptMemberMapper zjXmCqjxProjectOaDeptMemberMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectOaDeptMemberListByCondition(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        if (zjXmCqjxProjectOaDeptMember == null) {
            zjXmCqjxProjectOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectOaDeptMember.getPage(),zjXmCqjxProjectOaDeptMember.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectOaDeptMember> zjXmCqjxProjectOaDeptMemberList = zjXmCqjxProjectOaDeptMemberMapper.selectByZjXmCqjxProjectOaDeptMemberList(zjXmCqjxProjectOaDeptMember);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectOaDeptMember> p = new PageInfo<>(zjXmCqjxProjectOaDeptMemberList);

        return repEntity.okList(zjXmCqjxProjectOaDeptMemberList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectOaDeptMemberDetails(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        if (zjXmCqjxProjectOaDeptMember == null) {
            zjXmCqjxProjectOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
        }
        // 获取数据
        ZjXmCqjxProjectOaDeptMember dbZjXmCqjxProjectOaDeptMember = zjXmCqjxProjectOaDeptMemberMapper.selectByPrimaryKey(zjXmCqjxProjectOaDeptMember.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxProjectOaDeptMember != null) {
            return repEntity.ok(dbZjXmCqjxProjectOaDeptMember);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectOaDeptMember(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectOaDeptMember.setZcOaId(UuidUtil.generate());
        zjXmCqjxProjectOaDeptMember.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectOaDeptMemberMapper.insert(zjXmCqjxProjectOaDeptMember);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectOaDeptMember);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectOaDeptMember(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectOaDeptMember dbzjXmCqjxProjectOaDeptMember = zjXmCqjxProjectOaDeptMemberMapper.selectByPrimaryKey(zjXmCqjxProjectOaDeptMember.getZcOaId());
        if (dbzjXmCqjxProjectOaDeptMember != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectOaDeptMember.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxProjectOaDeptMember.setOtherId(zjXmCqjxProjectOaDeptMember.getOtherId());
           // 其他类型
           dbzjXmCqjxProjectOaDeptMember.setOtherType(zjXmCqjxProjectOaDeptMember.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxProjectOaDeptMember.setOaDepartmentMemberFlag(zjXmCqjxProjectOaDeptMember.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxProjectOaDeptMember.setOaUserId(zjXmCqjxProjectOaDeptMember.getOaUserId());
           // oaUserName
           dbzjXmCqjxProjectOaDeptMember.setOaUserName(zjXmCqjxProjectOaDeptMember.getOaUserName());
           // oaOrgId
           dbzjXmCqjxProjectOaDeptMember.setOaOrgId(zjXmCqjxProjectOaDeptMember.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxProjectOaDeptMember.setOaOrgName(zjXmCqjxProjectOaDeptMember.getOaOrgName());
           // 手机号
           dbzjXmCqjxProjectOaDeptMember.setMobile(zjXmCqjxProjectOaDeptMember.getMobile());
           // 共通
           dbzjXmCqjxProjectOaDeptMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectOaDeptMemberMapper.updateByPrimaryKey(dbzjXmCqjxProjectOaDeptMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectOaDeptMember);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectOaDeptMember(List<ZjXmCqjxProjectOaDeptMember> zjXmCqjxProjectOaDeptMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectOaDeptMemberList != null && zjXmCqjxProjectOaDeptMemberList.size() > 0) {
           ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember = new ZjXmCqjxProjectOaDeptMember();
           zjXmCqjxProjectOaDeptMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectOaDeptMemberMapper.batchDeleteUpdateZjXmCqjxProjectOaDeptMember(zjXmCqjxProjectOaDeptMemberList, zjXmCqjxProjectOaDeptMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectOaDeptMemberList);
        }
    }
}