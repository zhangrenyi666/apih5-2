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
import com.apih5.mybatis.dao.ZjXmCqjxAssistantRangeMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantRange;
import com.apih5.service.ZjXmCqjxAssistantRangeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxAssistantRangeService")
public class ZjXmCqjxAssistantRangeServiceImpl implements ZjXmCqjxAssistantRangeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxAssistantRangeMapper zjXmCqjxAssistantRangeMapper;

    @Override
    public ResponseEntity getZjXmCqjxAssistantRangeListByCondition(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        if (zjXmCqjxAssistantRange == null) {
            zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxAssistantRange.getPage(),zjXmCqjxAssistantRange.getLimit());
        // 获取数据
        List<ZjXmCqjxAssistantRange> zjXmCqjxAssistantRangeList = zjXmCqjxAssistantRangeMapper.selectByZjXmCqjxAssistantRangeList(zjXmCqjxAssistantRange);
        // 得到分页信息
        PageInfo<ZjXmCqjxAssistantRange> p = new PageInfo<>(zjXmCqjxAssistantRangeList);

        return repEntity.okList(zjXmCqjxAssistantRangeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxAssistantRangeDetails(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        if (zjXmCqjxAssistantRange == null) {
            zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
        }
        // 获取数据
        ZjXmCqjxAssistantRange dbZjXmCqjxAssistantRange = zjXmCqjxAssistantRangeMapper.selectByPrimaryKey(zjXmCqjxAssistantRange.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxAssistantRange != null) {
            return repEntity.ok(dbZjXmCqjxAssistantRange);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxAssistantRange(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxAssistantRange.setZcOaId(UuidUtil.generate());
        zjXmCqjxAssistantRange.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxAssistantRangeMapper.insert(zjXmCqjxAssistantRange);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxAssistantRange);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxAssistantRange(ZjXmCqjxAssistantRange zjXmCqjxAssistantRange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxAssistantRange dbzjXmCqjxAssistantRange = zjXmCqjxAssistantRangeMapper.selectByPrimaryKey(zjXmCqjxAssistantRange.getZcOaId());
        if (dbzjXmCqjxAssistantRange != null && StrUtil.isNotEmpty(dbzjXmCqjxAssistantRange.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxAssistantRange.setOtherId(zjXmCqjxAssistantRange.getOtherId());
           // 其他类型
           dbzjXmCqjxAssistantRange.setOtherType(zjXmCqjxAssistantRange.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxAssistantRange.setOaDepartmentMemberFlag(zjXmCqjxAssistantRange.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxAssistantRange.setOaUserId(zjXmCqjxAssistantRange.getOaUserId());
           // oaUserName
           dbzjXmCqjxAssistantRange.setOaUserName(zjXmCqjxAssistantRange.getOaUserName());
           // oaOrgId
           dbzjXmCqjxAssistantRange.setOaOrgId(zjXmCqjxAssistantRange.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxAssistantRange.setOaOrgName(zjXmCqjxAssistantRange.getOaOrgName());
           // 共通
           dbzjXmCqjxAssistantRange.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxAssistantRangeMapper.updateByPrimaryKey(dbzjXmCqjxAssistantRange);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxAssistantRange);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxAssistantRange(List<ZjXmCqjxAssistantRange> zjXmCqjxAssistantRangeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxAssistantRangeList != null && zjXmCqjxAssistantRangeList.size() > 0) {
           ZjXmCqjxAssistantRange zjXmCqjxAssistantRange = new ZjXmCqjxAssistantRange();
           zjXmCqjxAssistantRange.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxAssistantRangeMapper.batchDeleteUpdateZjXmCqjxAssistantRange(zjXmCqjxAssistantRangeList, zjXmCqjxAssistantRange);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxAssistantRangeList);
        }
    }
}
