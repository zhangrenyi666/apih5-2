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
import com.apih5.mybatis.dao.ZjXmCqjxOaDeptMemberMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.service.ZjXmCqjxOaDeptMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxOaDeptMemberService")
public class ZjXmCqjxOaDeptMemberServiceImpl implements ZjXmCqjxOaDeptMemberService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxOaDeptMemberMapper zjXmCqjxOaDeptMemberMapper;

    @Override
    public ResponseEntity getZjXmCqjxOaDeptMemberListByCondition(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        if (zjXmCqjxOaDeptMember == null) {
            zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxOaDeptMember.getPage(),zjXmCqjxOaDeptMember.getLimit());
        // 获取数据
        List<ZjXmCqjxOaDeptMember> zjXmCqjxOaDeptMemberList = zjXmCqjxOaDeptMemberMapper.selectByZjXmCqjxOaDeptMemberList(zjXmCqjxOaDeptMember);
        // 得到分页信息
        PageInfo<ZjXmCqjxOaDeptMember> p = new PageInfo<>(zjXmCqjxOaDeptMemberList);

        return repEntity.okList(zjXmCqjxOaDeptMemberList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxOaDeptMemberDetails(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        if (zjXmCqjxOaDeptMember == null) {
            zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
        }
        // 获取数据
        ZjXmCqjxOaDeptMember dbZjXmCqjxOaDeptMember = zjXmCqjxOaDeptMemberMapper.selectByPrimaryKey(zjXmCqjxOaDeptMember.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxOaDeptMember != null) {
            return repEntity.ok(dbZjXmCqjxOaDeptMember);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxOaDeptMember(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxOaDeptMember.setZcOaId(UuidUtil.generate());
        zjXmCqjxOaDeptMember.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxOaDeptMemberMapper.insert(zjXmCqjxOaDeptMember);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxOaDeptMember);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxOaDeptMember(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxOaDeptMember dbzjXmCqjxOaDeptMember = zjXmCqjxOaDeptMemberMapper.selectByPrimaryKey(zjXmCqjxOaDeptMember.getZcOaId());
        if (dbzjXmCqjxOaDeptMember != null && StrUtil.isNotEmpty(dbzjXmCqjxOaDeptMember.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxOaDeptMember.setOtherId(zjXmCqjxOaDeptMember.getOtherId());
           // 其他类型
           dbzjXmCqjxOaDeptMember.setOtherType(zjXmCqjxOaDeptMember.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxOaDeptMember.setOaDepartmentMemberFlag(zjXmCqjxOaDeptMember.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxOaDeptMember.setOaUserId(zjXmCqjxOaDeptMember.getOaUserId());
           // oaUserName
           dbzjXmCqjxOaDeptMember.setOaUserName(zjXmCqjxOaDeptMember.getOaUserName());
           // oaOrgId
           dbzjXmCqjxOaDeptMember.setOaOrgId(zjXmCqjxOaDeptMember.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxOaDeptMember.setOaOrgName(zjXmCqjxOaDeptMember.getOaOrgName());
           // 手机号
           dbzjXmCqjxOaDeptMember.setMobile(zjXmCqjxOaDeptMember.getMobile());
           // 共通
           dbzjXmCqjxOaDeptMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxOaDeptMemberMapper.updateByPrimaryKey(dbzjXmCqjxOaDeptMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxOaDeptMember);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxOaDeptMember(List<ZjXmCqjxOaDeptMember> zjXmCqjxOaDeptMemberList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxOaDeptMemberList != null && zjXmCqjxOaDeptMemberList.size() > 0) {
           ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember = new ZjXmCqjxOaDeptMember();
           zjXmCqjxOaDeptMember.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxOaDeptMemberMapper.batchDeleteUpdateZjXmCqjxOaDeptMember(zjXmCqjxOaDeptMemberList, zjXmCqjxOaDeptMember);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxOaDeptMemberList);
        }
    }
}
