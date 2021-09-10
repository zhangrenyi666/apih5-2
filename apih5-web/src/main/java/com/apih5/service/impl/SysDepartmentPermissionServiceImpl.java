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
import com.apih5.mybatis.dao.SysDepartmentPermissionMapper;
import com.apih5.mybatis.pojo.SysDepartmentPermission;
import com.apih5.service.SysDepartmentPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysDepartmentPermissionService")
public class SysDepartmentPermissionServiceImpl implements SysDepartmentPermissionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysDepartmentPermissionMapper sysDepartmentPermissionMapper;

    @Override
    public ResponseEntity getSysDepartmentPermissionListByCondition(SysDepartmentPermission sysDepartmentPermission) {
        if (sysDepartmentPermission == null) {
            sysDepartmentPermission = new SysDepartmentPermission();
        }
        // 分页查询
        PageHelper.startPage(sysDepartmentPermission.getPage(),sysDepartmentPermission.getLimit());
        // 获取数据
        List<SysDepartmentPermission> sysDepartmentPermissionList = sysDepartmentPermissionMapper.selectBySysDepartmentPermissionList(sysDepartmentPermission);
        // 得到分页信息
        PageInfo<SysDepartmentPermission> p = new PageInfo<>(sysDepartmentPermissionList);

        return repEntity.okList(sysDepartmentPermissionList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysDepartmentPermission(SysDepartmentPermission sysDepartmentPermission) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysDepartmentPermission.setPermissionId(UuidUtil.generate());
        sysDepartmentPermission.setCreateUserInfo(userKey, realName);
        int flag = sysDepartmentPermissionMapper.insert(sysDepartmentPermission);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysDepartmentPermission);
        }
    }

    @Override
    public ResponseEntity updateSysDepartmentPermission(SysDepartmentPermission sysDepartmentPermission) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysDepartmentPermission dbsysDepartmentPermission = sysDepartmentPermissionMapper.selectByPrimaryKey(sysDepartmentPermission.getPermissionId());
        if (dbsysDepartmentPermission != null && StrUtil.isNotEmpty(dbsysDepartmentPermission.getPermissionId())) {
           // 共通
           dbsysDepartmentPermission.setModifyUserInfo(userKey, realName);
           flag = sysDepartmentPermissionMapper.updateByPrimaryKey(dbsysDepartmentPermission);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysDepartmentPermission);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysDepartmentPermission(List<SysDepartmentPermission> sysDepartmentPermissionList) {
        int flag = 0;
        if (sysDepartmentPermissionList != null && sysDepartmentPermissionList.size() > 0) {
           flag = sysDepartmentPermissionMapper.batchDeleteUpdateSysDepartmentPermission(sysDepartmentPermissionList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysDepartmentPermissionList);
        }
   }
}
