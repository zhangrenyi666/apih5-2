package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysFlowUserMapper;
import com.apih5.mybatis.dao.SysProjectMapper;
import com.apih5.mybatis.pojo.SysFlowUser;
import com.apih5.mybatis.pojo.SysProject;
import com.apih5.service.SysFlowUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service("sysFlowUserService")
public class SysFlowUserServiceImpl implements SysFlowUserService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysFlowUserMapper sysFlowUserMapper;
    
    @Autowired(required = true)
    private SysDepartmentMapper sysDepartmentMapper;
    
    @Autowired(required = true)
    private SysProjectMapper sysProjectMapper;

    @Override
    public ResponseEntity getSysFlowUserListByCondition(SysFlowUser sysFlowUser) {
        if (sysFlowUser == null) {
            sysFlowUser = new SysFlowUser();
        }
        // 分页查询
        PageHelper.startPage(sysFlowUser.getPage(),sysFlowUser.getLimit());
        // 获取数据
        List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUser);
        // 得到分页信息
        PageInfo<SysFlowUser> p = new PageInfo<>(sysFlowUserList);

        return repEntity.okList(sysFlowUserList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysFlowUserDetail(SysFlowUser sysFlowUser) {
        if (sysFlowUser == null) {
            sysFlowUser = new SysFlowUser();
        }
        // 获取数据
        SysFlowUser dbSysFlowUser = sysFlowUserMapper.selectByPrimaryKey(sysFlowUser.getSysFlowUserId());
        // 数据存在
        if (dbSysFlowUser != null) {
            return repEntity.ok(dbSysFlowUser);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveSysFlowUser(SysFlowUser sysFlowUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysFlowUser.setSysFlowUserId(UuidUtil.generate());
        sysFlowUser.setCreateUserInfo(userKey, realName);
        int flag = sysFlowUserMapper.insert(sysFlowUser);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysFlowUser);
        }
    }

    @Override
    public ResponseEntity updateSysFlowUser(SysFlowUser sysFlowUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysFlowUser dbSysFlowUser = sysFlowUserMapper.selectByPrimaryKey(sysFlowUser.getSysFlowUserId());
        if (dbSysFlowUser != null && StrUtil.isNotEmpty(dbSysFlowUser.getSysFlowUserId())) {
           // 流程角色
           dbSysFlowUser.setFlowRoleId(sysFlowUser.getFlowRoleId());
           // 项目或公司
           dbSysFlowUser.setTopId(sysFlowUser.getTopId());
           // 用户key
           dbSysFlowUser.setUserKey(sysFlowUser.getUserKey());
           // 姓名
           dbSysFlowUser.setRealName(sysFlowUser.getRealName());
           // 备注
           dbSysFlowUser.setRemarks(sysFlowUser.getRemarks());
           // 排序
           dbSysFlowUser.setSort(sysFlowUser.getSort());
           // 共通
           dbSysFlowUser.setModifyUserInfo(userKey, realName);
           flag = sysFlowUserMapper.updateByPrimaryKey(dbSysFlowUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysFlowUser);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysFlowUser(List<SysFlowUser> sysFlowUserList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysFlowUserList != null && sysFlowUserList.size() > 0) {
           SysFlowUser sysFlowUser = new SysFlowUser();
           sysFlowUser.setModifyUserInfo(userKey, realName);
           flag = sysFlowUserMapper.batchDeleteUpdateSysFlowUser(sysFlowUserList, sysFlowUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysFlowUserList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity saveSysFlowUserByFlow(SysFlowUser sysFlowUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        // 部门插入
        String[] departmentIds = null;
        String[] departmentNames = null;
        SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysFlowUser.getValuePid());
        if(sysDepartment != null) {
            departmentIds = sysDepartment.getDepartmentPath().split(",");
            departmentNames = sysDepartment.getDepartmentPathName().split(",");
        } else {
            // 不存在则再项目里面检索
            SysProject sysProject = sysProjectMapper.selectByPrimaryKey(sysFlowUser.getValuePid());
            departmentIds = sysProject.getDepartmentPath().split(",");
            departmentNames = sysProject.getDepartmentPathName().split(",");
        }
        
        
        boolean insertFlag = false;
        for(int i=0; i<departmentIds.length; i++) {
            // 没有判断true之前都可能进入此判断
            if(!insertFlag && StrUtil.equals(sysFlowUser.getTopId(), departmentIds[i])) {
                insertFlag = true;
            }
            if(insertFlag) {
                SysFlowUser sysFlowUserSelect = new SysFlowUser();
                sysFlowUserSelect.setFlowRoleId(sysFlowUser.getFlowRoleId());
                sysFlowUserSelect.setTopId(sysFlowUser.getTopId());
                sysFlowUserSelect.setValue(departmentIds[i]);
                List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUserSelect);
                if(sysFlowUserList == null || sysFlowUserList.size()==0) {
                    SysFlowUser sysFlowUserDept = new SysFlowUser();
                    sysFlowUserDept.setSysFlowUserId(UuidUtil.generate());
                    sysFlowUserDept.setFlowRoleId(sysFlowUser.getFlowRoleId());
                    sysFlowUserDept.setTopId(sysFlowUser.getTopId());
                    sysFlowUserDept.setValue(departmentIds[i]);
                    if(i==0) {
                        sysFlowUserDept.setValuePid("0");
                    } else {
                        sysFlowUserDept.setValuePid(departmentIds[i-1]);
                    }
                    sysFlowUserDept.setLabel(departmentNames[i]);
                    sysFlowUserDept.setType("1");
                    sysFlowUserDept.setCreateUserInfo(userKey, realName);
                    sysFlowUserMapper.insert(sysFlowUserDept);
                }
            }
        }
        sysFlowUser.setType("2");
        sysFlowUser.setSysFlowUserId(UuidUtil.generate());
        sysFlowUser.setCreateUserInfo(userKey, realName);
        int flag = sysFlowUserMapper.insert(sysFlowUser);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysFlowUser);
        }
    }

    @Override
    public ResponseEntity deleteUpdateSysFlowUser(SysFlowUser sysFlowUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        // 删除人
        SysFlowUser sysFlowUserSelect = new SysFlowUser();
        sysFlowUserSelect.setFlowRoleId(sysFlowUser.getFlowRoleId());
        sysFlowUserSelect.setTopId(sysFlowUser.getTopId());
        sysFlowUserSelect.setValue(sysFlowUser.getValue());
        List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUserSelect);
        if (sysFlowUserList != null && sysFlowUserList.size() > 0) {
           SysFlowUser sysFlowUserInfo = new SysFlowUser();
           sysFlowUserInfo.setModifyUserInfo(userKey, realName);
           flag = sysFlowUserMapper.batchDeleteUpdateSysFlowUser(sysFlowUserList, sysFlowUserInfo);
        }
        
        SysFlowUser sysFlowUserCallback = new SysFlowUser();
        sysFlowUserCallback.setFlowRoleId(sysFlowUser.getFlowRoleId());
        sysFlowUserCallback.setTopId(sysFlowUser.getTopId());
        sysFlowUserCallback.setValue(sysFlowUser.getValuePid());
        sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUserCallback);
        deleteCallback(sysFlowUserList.get(0));
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysFlowUserList);
        }
    }
    
    // ----私有方法
    /**
     * 删除回调
     * @param sysFlowUser
     */
    private void deleteCallback(SysFlowUser sysFlowUser) {
        if(StrUtil.equals("0", sysFlowUser.getValue())
                || StrUtil.equals(sysFlowUser.getTopId(), sysFlowUser.getValue())) {
            return;
        }
        
        SysFlowUser sysFlowUserSelect = new SysFlowUser();
        sysFlowUserSelect.setFlowRoleId(sysFlowUser.getFlowRoleId());
        sysFlowUserSelect.setTopId(sysFlowUser.getTopId());
        sysFlowUserSelect.setValuePid(sysFlowUser.getValue());
        List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUserSelect);
        if(sysFlowUserList == null || sysFlowUserList.size()==0) {
            // 删除当前id
            SysFlowUser sysFlowUserDelete = new SysFlowUser();
            sysFlowUserDelete.setFlowRoleId(sysFlowUser.getFlowRoleId());
            sysFlowUserDelete.setTopId(sysFlowUser.getTopId());
            sysFlowUserDelete.setValue(sysFlowUser.getValue());
            sysFlowUserMapper.deleteUpdateSysFlowUser(sysFlowUserDelete);
            
            SysFlowUser sysFlowUserCallback = new SysFlowUser();
            sysFlowUserCallback.setFlowRoleId(sysFlowUser.getFlowRoleId());
            sysFlowUserCallback.setTopId(sysFlowUser.getTopId());
            sysFlowUserCallback.setValue(sysFlowUser.getValuePid());
            sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUserCallback);
            
            deleteCallback(sysFlowUserList.get(0));
        }
    }
}
