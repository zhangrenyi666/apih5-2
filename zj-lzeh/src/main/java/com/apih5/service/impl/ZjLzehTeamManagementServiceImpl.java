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
import com.apih5.mybatis.dao.ZjLzehTeamManagementMapper;
import com.apih5.mybatis.pojo.ZjLzehTeamManagement;
import com.apih5.service.ZjLzehTeamManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTeamManagementService")
public class ZjLzehTeamManagementServiceImpl implements ZjLzehTeamManagementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTeamManagementMapper zjLzehTeamManagementMapper;

    @Override
    public ResponseEntity getZjLzehTeamManagementListByCondition(ZjLzehTeamManagement zjLzehTeamManagement) {
        if (zjLzehTeamManagement == null) {
            zjLzehTeamManagement = new ZjLzehTeamManagement();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTeamManagement.getPage(),zjLzehTeamManagement.getLimit());
        // 获取数据
        List<ZjLzehTeamManagement> zjLzehTeamManagementList = zjLzehTeamManagementMapper.selectByZjLzehTeamManagementList(zjLzehTeamManagement);
        // 得到分页信息
        PageInfo<ZjLzehTeamManagement> p = new PageInfo<>(zjLzehTeamManagementList);

        return repEntity.okList(zjLzehTeamManagementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTeamManagementDetail(ZjLzehTeamManagement zjLzehTeamManagement) {
        if (zjLzehTeamManagement == null) {
            zjLzehTeamManagement = new ZjLzehTeamManagement();
        }
        // 获取数据
        ZjLzehTeamManagement dbZjLzehTeamManagement = zjLzehTeamManagementMapper.selectByPrimaryKey(zjLzehTeamManagement.getZjLzehTeamManagementId());
        // 数据存在
        if (dbZjLzehTeamManagement != null) {
            return repEntity.ok(dbZjLzehTeamManagement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTeamManagement(ZjLzehTeamManagement zjLzehTeamManagement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTeamManagement.setZjLzehTeamManagementId(UuidUtil.generate());
        zjLzehTeamManagement.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTeamManagementMapper.insert(zjLzehTeamManagement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehTeamManagement);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTeamManagement(ZjLzehTeamManagement zjLzehTeamManagement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTeamManagement dbZjLzehTeamManagement = zjLzehTeamManagementMapper.selectByPrimaryKey(zjLzehTeamManagement.getZjLzehTeamManagementId());
        if (dbZjLzehTeamManagement != null && StrUtil.isNotEmpty(dbZjLzehTeamManagement.getZjLzehTeamManagementId())) {
           // 公司名称
           dbZjLzehTeamManagement.setCompanyName(zjLzehTeamManagement.getCompanyName());
           // 班组名称
           dbZjLzehTeamManagement.setTeamName(zjLzehTeamManagement.getTeamName());
           // 班组ID
           dbZjLzehTeamManagement.setTeamId(zjLzehTeamManagement.getTeamId());
           // 班组人数
           dbZjLzehTeamManagement.setTeamPerson(zjLzehTeamManagement.getTeamPerson());
           // 班组组长
           dbZjLzehTeamManagement.setTeamLeader(zjLzehTeamManagement.getTeamLeader());
           // 联系方式
           dbZjLzehTeamManagement.setPhone(zjLzehTeamManagement.getPhone());
           // 公司ID
           dbZjLzehTeamManagement.setCompanyId(zjLzehTeamManagement.getCompanyId());
           // 公司简称
           dbZjLzehTeamManagement.setCompanyAbbreviation(zjLzehTeamManagement.getCompanyAbbreviation());
           // 是否评分
           dbZjLzehTeamManagement.setIsScore(zjLzehTeamManagement.getIsScore());
           // 备注
           dbZjLzehTeamManagement.setRemarks(zjLzehTeamManagement.getRemarks());
           // 排序
           dbZjLzehTeamManagement.setSort(zjLzehTeamManagement.getSort());
           // 共通
           dbZjLzehTeamManagement.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamManagementMapper.updateByPrimaryKey(dbZjLzehTeamManagement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehTeamManagement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTeamManagement(List<ZjLzehTeamManagement> zjLzehTeamManagementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTeamManagementList != null && zjLzehTeamManagementList.size() > 0) {
           ZjLzehTeamManagement zjLzehTeamManagement = new ZjLzehTeamManagement();
           zjLzehTeamManagement.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamManagementMapper.batchDeleteUpdateZjLzehTeamManagement(zjLzehTeamManagementList, zjLzehTeamManagement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehTeamManagementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity getZjLzehTeamManagementisScoreCount() {
        // 获取数据
        ZjLzehTeamManagement dbZjLzehTeamManagement = zjLzehTeamManagementMapper.getCount();
        // 数据存在
        if (dbZjLzehTeamManagement != null) {
            return repEntity.ok(dbZjLzehTeamManagement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
}
