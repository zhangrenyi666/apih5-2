package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.SysWoaAddFlowMapper;
import com.apih5.mybatis.pojo.SysWoaAddFlow;
import com.apih5.service.SysWoaAddFlowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysWoaAddFlowService")
public class SysWoaAddFlowServiceImpl implements SysWoaAddFlowService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWoaAddFlowMapper sysWoaAddFlowMapper;
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Override
    public ResponseEntity getSysWoaAddFlowListByCondition(SysWoaAddFlow sysWoaAddFlow) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (sysWoaAddFlow == null) {
            sysWoaAddFlow = new SysWoaAddFlow();
        }
        // 分页查询
        PageHelper.startPage(sysWoaAddFlow.getPage(),sysWoaAddFlow.getLimit());
        // 获取数据
        List<SysWoaAddFlow> sysWoaAddFlowList = sysWoaAddFlowMapper.selectBySysWoaAddFlowList(sysWoaAddFlow);
        // 得到分页信息
        PageInfo<SysWoaAddFlow> p = new PageInfo<>(sysWoaAddFlowList);

        return repEntity.okList(sysWoaAddFlowList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysWoaAddFlow(SysWoaAddFlow sysWoaAddFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysWoaAddFlow.setAddPageId(UuidUtil.generate());
        sysWoaAddFlow.setCreateUserInfo(userKey, realName);
        int flag = sysWoaAddFlowMapper.insert(sysWoaAddFlow);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWoaAddFlow);
        }
    }

    @Override
    public ResponseEntity updateSysWoaAddFlow(SysWoaAddFlow sysWoaAddFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWoaAddFlow dbsysWoaAddFlow = sysWoaAddFlowMapper.selectByPrimaryKey(sysWoaAddFlow.getAddPageId());
        if (dbsysWoaAddFlow != null && StrUtil.isNotEmpty(dbsysWoaAddFlow.getAddPageId())) {
           // 小模块类型
           dbsysWoaAddFlow.setModuleType(sysWoaAddFlow.getModuleType());
           // 小模块标题
           dbsysWoaAddFlow.setModuleTitle(sysWoaAddFlow.getModuleTitle());
           // 小模块链接
           dbsysWoaAddFlow.setModuleLink(sysWoaAddFlow.getModuleLink());
           // 小模块图标
           dbsysWoaAddFlow.setModuleIcon(sysWoaAddFlow.getModuleIcon());
           // 公司ID
           dbsysWoaAddFlow.setCompanyId(sysWoaAddFlow.getCompanyId());
           // 排序
           dbsysWoaAddFlow.setModuleSort(sysWoaAddFlow.getModuleSort());
           // 共通
           dbsysWoaAddFlow.setModifyUserInfo(userKey, realName);
           flag = sysWoaAddFlowMapper.updateByPrimaryKey(dbsysWoaAddFlow);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWoaAddFlow);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWoaAddFlow(List<SysWoaAddFlow> sysWoaAddFlowList) {
        int flag = 0;
        if (sysWoaAddFlowList != null && sysWoaAddFlowList.size() > 0) {
           flag = sysWoaAddFlowMapper.batchDeleteUpdateSysWoaAddFlow(sysWoaAddFlowList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWoaAddFlowList);
        }
    }

    @Override
    public ResponseEntity getSysWoaAddFlowListByRole(SysWoaAddFlow sysWoaAddFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (sysWoaAddFlow == null) {
            sysWoaAddFlow = new SysWoaAddFlow();
        }
        // 组合成sql的in条件
        String sqlIn = "";
        // 获取登陆者的所有部门Id和userKey
        List<String> permissionList = TokenUtils.getPermissionIds(request);
        for (int i=0; i<permissionList.size(); i++) {
            String departmentId = permissionList.get(i);
            SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(departmentId);
            if(sysDepartment != null) {
                String[] sysDepartments = sysDepartment.getDepartmentPath().split(",");
                for(String sysDepartmentId:sysDepartments) {
                    sqlIn += "'" + sysDepartmentId + "',";
                }
            } else {
                sqlIn += "'" + departmentId + "',";
            }
        }
        sqlIn = sqlIn.substring(0, sqlIn.length() - 1);
        sysWoaAddFlow.setCompanyId(sqlIn);
        List<SysWoaAddFlow> sysWoaAddFlowList = sysWoaAddFlowMapper.selectBySysWoaAddFlowListByRole(sysWoaAddFlow);
        return repEntity.ok(sysWoaAddFlowList);
    }
}
