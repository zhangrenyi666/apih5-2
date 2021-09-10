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
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxDepartmentHeadDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxDepartmentHeadDetailService")
public class ZjXmCqjxDepartmentHeadDetailServiceImpl implements ZjXmCqjxDepartmentHeadDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;

    @Override
    public ResponseEntity getZjXmCqjxDepartmentHeadDetailListByCondition(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        if (zjXmCqjxDepartmentHeadDetail == null) {
            zjXmCqjxDepartmentHeadDetail = new ZjXmCqjxDepartmentHeadDetail();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxDepartmentHeadDetail.getPage(),zjXmCqjxDepartmentHeadDetail.getLimit());
        // 获取数据
        List<ZjXmCqjxDepartmentHeadDetail> zjXmCqjxDepartmentHeadDetailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(zjXmCqjxDepartmentHeadDetail);
        // 得到分页信息
        PageInfo<ZjXmCqjxDepartmentHeadDetail> p = new PageInfo<>(zjXmCqjxDepartmentHeadDetailList);

        return repEntity.okList(zjXmCqjxDepartmentHeadDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDepartmentHeadDetailDetails(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        if (zjXmCqjxDepartmentHeadDetail == null) {
            zjXmCqjxDepartmentHeadDetail = new ZjXmCqjxDepartmentHeadDetail();
        }
        // 获取数据
        ZjXmCqjxDepartmentHeadDetail dbZjXmCqjxDepartmentHeadDetail = zjXmCqjxDepartmentHeadDetailMapper.selectByPrimaryKey(zjXmCqjxDepartmentHeadDetail.getDeptDetailId());
        // 数据存在
        if (dbZjXmCqjxDepartmentHeadDetail != null) {
            return repEntity.ok(dbZjXmCqjxDepartmentHeadDetail);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDepartmentHeadDetail(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxDepartmentHeadDetail.setDeptDetailId(UuidUtil.generate());
        zjXmCqjxDepartmentHeadDetail.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDepartmentHeadDetailMapper.insert(zjXmCqjxDepartmentHeadDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDepartmentHeadDetail);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDepartmentHeadDetail(ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxDepartmentHeadDetail dbzjXmCqjxDepartmentHeadDetail = zjXmCqjxDepartmentHeadDetailMapper.selectByPrimaryKey(zjXmCqjxDepartmentHeadDetail.getDeptDetailId());
        if (dbzjXmCqjxDepartmentHeadDetail != null && StrUtil.isNotEmpty(dbzjXmCqjxDepartmentHeadDetail.getDeptDetailId())) {
           // 其他关联表id
           dbzjXmCqjxDepartmentHeadDetail.setOtherId(zjXmCqjxDepartmentHeadDetail.getOtherId());
           // 其他类型
           dbzjXmCqjxDepartmentHeadDetail.setOtherType(zjXmCqjxDepartmentHeadDetail.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxDepartmentHeadDetail.setOaDepartmentMemberFlag(zjXmCqjxDepartmentHeadDetail.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxDepartmentHeadDetail.setOaUserId(zjXmCqjxDepartmentHeadDetail.getOaUserId());
           // oaUserName
           dbzjXmCqjxDepartmentHeadDetail.setOaUserName(zjXmCqjxDepartmentHeadDetail.getOaUserName());
           // oaOrgId
           dbzjXmCqjxDepartmentHeadDetail.setOaOrgId(zjXmCqjxDepartmentHeadDetail.getOaOrgId());
           // oaOrgName
           dbzjXmCqjxDepartmentHeadDetail.setOaOrgName(zjXmCqjxDepartmentHeadDetail.getOaOrgName());
           // 共通
           dbzjXmCqjxDepartmentHeadDetail.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDepartmentHeadDetailMapper.updateByPrimaryKey(dbzjXmCqjxDepartmentHeadDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDepartmentHeadDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(List<ZjXmCqjxDepartmentHeadDetail> zjXmCqjxDepartmentHeadDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDepartmentHeadDetailList != null && zjXmCqjxDepartmentHeadDetailList.size() > 0) {
           ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail = new ZjXmCqjxDepartmentHeadDetail();
           zjXmCqjxDepartmentHeadDetail.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(zjXmCqjxDepartmentHeadDetailList, zjXmCqjxDepartmentHeadDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDepartmentHeadDetailList);
        }
    }
    
}
