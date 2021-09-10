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
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadDetailMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.service.ZjXmCqjxProjectDepartmentHeadDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectDepartmentHeadDetailService")
public class ZjXmCqjxProjectDepartmentHeadDetailServiceImpl implements ZjXmCqjxProjectDepartmentHeadDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetailListByCondition(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        if (zjXmCqjxProjectDepartmentHeadDetail == null) {
            zjXmCqjxProjectDepartmentHeadDetail = new ZjXmCqjxProjectDepartmentHeadDetail();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectDepartmentHeadDetail.getPage(),zjXmCqjxProjectDepartmentHeadDetail.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectDepartmentHeadDetail> zjXmCqjxProjectDepartmentHeadDetailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(zjXmCqjxProjectDepartmentHeadDetail);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectDepartmentHeadDetail> p = new PageInfo<>(zjXmCqjxProjectDepartmentHeadDetailList);

        return repEntity.okList(zjXmCqjxProjectDepartmentHeadDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetailDetails(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        if (zjXmCqjxProjectDepartmentHeadDetail == null) {
            zjXmCqjxProjectDepartmentHeadDetail = new ZjXmCqjxProjectDepartmentHeadDetail();
        }
        // 获取数据
        ZjXmCqjxProjectDepartmentHeadDetail dbZjXmCqjxProjectDepartmentHeadDetail = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByPrimaryKey(zjXmCqjxProjectDepartmentHeadDetail.getDeptDetailId());
        // 数据存在
        if (dbZjXmCqjxProjectDepartmentHeadDetail != null) {
            return repEntity.ok(dbZjXmCqjxProjectDepartmentHeadDetail);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDepartmentHeadDetail(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectDepartmentHeadDetail.setDeptDetailId(UuidUtil.generate());
        zjXmCqjxProjectDepartmentHeadDetail.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectDepartmentHeadDetailMapper.insert(zjXmCqjxProjectDepartmentHeadDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDepartmentHeadDetail);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDepartmentHeadDetail(ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectDepartmentHeadDetail dbzjXmCqjxProjectDepartmentHeadDetail = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByPrimaryKey(zjXmCqjxProjectDepartmentHeadDetail.getDeptDetailId());
        if (dbzjXmCqjxProjectDepartmentHeadDetail != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectDepartmentHeadDetail.getDeptDetailId())) {
           // 其他关联表id
           dbzjXmCqjxProjectDepartmentHeadDetail.setOtherId(zjXmCqjxProjectDepartmentHeadDetail.getOtherId());
           // 其他类型
           dbzjXmCqjxProjectDepartmentHeadDetail.setOtherType(zjXmCqjxProjectDepartmentHeadDetail.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxProjectDepartmentHeadDetail.setOaDepartmentMemberFlag(zjXmCqjxProjectDepartmentHeadDetail.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxProjectDepartmentHeadDetail.setOaUserId(zjXmCqjxProjectDepartmentHeadDetail.getOaUserId());
           // oaUserName
           dbzjXmCqjxProjectDepartmentHeadDetail.setOaUserName(zjXmCqjxProjectDepartmentHeadDetail.getOaUserName());
           // oaOrgId
           dbzjXmCqjxProjectDepartmentHeadDetail.setOaOrgId(zjXmCqjxProjectDepartmentHeadDetail.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxProjectDepartmentHeadDetail.setOaOrgName(zjXmCqjxProjectDepartmentHeadDetail.getOaOrgName());
           // 共通
           dbzjXmCqjxProjectDepartmentHeadDetail.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDepartmentHeadDetailMapper.updateByPrimaryKey(dbzjXmCqjxProjectDepartmentHeadDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDepartmentHeadDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(List<ZjXmCqjxProjectDepartmentHeadDetail> zjXmCqjxProjectDepartmentHeadDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDepartmentHeadDetailList != null && zjXmCqjxProjectDepartmentHeadDetailList.size() > 0) {
           ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail = new ZjXmCqjxProjectDepartmentHeadDetail();
           zjXmCqjxProjectDepartmentHeadDetail.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(zjXmCqjxProjectDepartmentHeadDetailList, zjXmCqjxProjectDepartmentHeadDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDepartmentHeadDetailList);
        }
    }
}