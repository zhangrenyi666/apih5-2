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
import com.apih5.mybatis.dao.ZjXmCqjxOaLeaderMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxOaLeader;
import com.apih5.service.ZjXmCqjxOaLeaderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxOaLeaderService")
public class ZjXmCqjxOaLeaderServiceImpl implements ZjXmCqjxOaLeaderService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxOaLeaderMapper zjXmCqjxOaLeaderMapper;

    @Override
    public ResponseEntity getZjXmCqjxOaLeaderListByCondition(ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        if (zjXmCqjxOaLeader == null) {
            zjXmCqjxOaLeader = new ZjXmCqjxOaLeader();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxOaLeader.getPage(),zjXmCqjxOaLeader.getLimit());
        // 获取数据
        List<ZjXmCqjxOaLeader> zjXmCqjxOaLeaderList = zjXmCqjxOaLeaderMapper.selectByZjXmCqjxOaLeaderList(zjXmCqjxOaLeader);
        // 得到分页信息
        PageInfo<ZjXmCqjxOaLeader> p = new PageInfo<>(zjXmCqjxOaLeaderList);

        return repEntity.okList(zjXmCqjxOaLeaderList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxOaLeaderDetails(ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        if (zjXmCqjxOaLeader == null) {
            zjXmCqjxOaLeader = new ZjXmCqjxOaLeader();
        }
        // 获取数据
        ZjXmCqjxOaLeader dbZjXmCqjxOaLeader = zjXmCqjxOaLeaderMapper.selectByPrimaryKey(zjXmCqjxOaLeader.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxOaLeader != null) {
            return repEntity.ok(dbZjXmCqjxOaLeader);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxOaLeader(ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxOaLeader.setZcOaId(UuidUtil.generate());
        zjXmCqjxOaLeader.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxOaLeaderMapper.insert(zjXmCqjxOaLeader);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxOaLeader);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxOaLeader(ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxOaLeader dbzjXmCqjxOaLeader = zjXmCqjxOaLeaderMapper.selectByPrimaryKey(zjXmCqjxOaLeader.getZcOaId());
        if (dbzjXmCqjxOaLeader != null && StrUtil.isNotEmpty(dbzjXmCqjxOaLeader.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxOaLeader.setOtherId(zjXmCqjxOaLeader.getOtherId());
           // 其他类型
           dbzjXmCqjxOaLeader.setOtherType(zjXmCqjxOaLeader.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxOaLeader.setOaDepartmentMemberFlag(zjXmCqjxOaLeader.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxOaLeader.setOaUserId(zjXmCqjxOaLeader.getOaUserId());
           // oaUserName
           dbzjXmCqjxOaLeader.setOaUserName(zjXmCqjxOaLeader.getOaUserName());
           // oaOrgId
           dbzjXmCqjxOaLeader.setOaOrgId(zjXmCqjxOaLeader.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxOaLeader.setOaOrgName(zjXmCqjxOaLeader.getOaOrgName());
           // 共通
           dbzjXmCqjxOaLeader.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxOaLeaderMapper.updateByPrimaryKey(dbzjXmCqjxOaLeader);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxOaLeader);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxOaLeader(List<ZjXmCqjxOaLeader> zjXmCqjxOaLeaderList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxOaLeaderList != null && zjXmCqjxOaLeaderList.size() > 0) {
           ZjXmCqjxOaLeader zjXmCqjxOaLeader = new ZjXmCqjxOaLeader();
           zjXmCqjxOaLeader.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxOaLeaderMapper.batchDeleteUpdateZjXmCqjxOaLeader(zjXmCqjxOaLeaderList, zjXmCqjxOaLeader);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxOaLeaderList);
        }
    }
}
