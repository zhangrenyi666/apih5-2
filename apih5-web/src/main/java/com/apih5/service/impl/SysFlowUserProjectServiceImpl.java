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
import com.apih5.mybatis.dao.SysFlowUserProjectMapper;
import com.apih5.mybatis.dao.SysProjectMapper;
import com.apih5.mybatis.pojo.SysFlowUserProject;
import com.apih5.mybatis.pojo.SysProject;
import com.apih5.service.SysFlowUserProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("sysFlowUserProjectService")
public class SysFlowUserProjectServiceImpl implements SysFlowUserProjectService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysFlowUserProjectMapper sysFlowUserProjectMapper;
    
    @Autowired(required = true)
    private SysProjectMapper sysProjectMapper;

    @Override
    public ResponseEntity getSysFlowUserProjectListByCondition(SysFlowUserProject sysFlowUserProject) {
        if (sysFlowUserProject == null) {
            sysFlowUserProject = new SysFlowUserProject();
        }
        // 分页查询
        PageHelper.startPage(sysFlowUserProject.getPage(),sysFlowUserProject.getLimit());
        // 获取数据
        List<SysFlowUserProject> sysFlowUserProjectList = sysFlowUserProjectMapper.selectBySysFlowUserProjectList(sysFlowUserProject);
        // 得到分页信息
        PageInfo<SysFlowUserProject> p = new PageInfo<>(sysFlowUserProjectList);

        return repEntity.okList(sysFlowUserProjectList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysFlowUserProjectDetail(SysFlowUserProject sysFlowUserProject) {
        if (sysFlowUserProject == null) {
            sysFlowUserProject = new SysFlowUserProject();
        }
        // 获取数据
        SysFlowUserProject dbSysFlowUserProject = sysFlowUserProjectMapper.selectByPrimaryKey(sysFlowUserProject.getWorkId());
        // 数据存在
        if (dbSysFlowUserProject != null) {
            return repEntity.ok(dbSysFlowUserProject);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveSysFlowUserProject(SysFlowUserProject sysFlowUserProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysFlowUserProject.setWorkId(UuidUtil.generate());
        sysFlowUserProject.setCreateUserInfo(userKey, realName);
        int flag = sysFlowUserProjectMapper.insert(sysFlowUserProject);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysFlowUserProject);
        }
    }

    @Override
    public ResponseEntity updateSysFlowUserProject(SysFlowUserProject sysFlowUserProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysFlowUserProject dbSysFlowUserProject = sysFlowUserProjectMapper.selectByPrimaryKey(sysFlowUserProject.getWorkId());
        if (dbSysFlowUserProject != null && StrUtil.isNotEmpty(dbSysFlowUserProject.getWorkId())) {
           // 项目ID
           dbSysFlowUserProject.setProjectId(sysFlowUserProject.getProjectId());
           // 项目名称
           dbSysFlowUserProject.setProjectName(sysFlowUserProject.getProjectName());
           // 公司ID
           dbSysFlowUserProject.setCompanyId(sysFlowUserProject.getCompanyId());
           // 公司名称
           dbSysFlowUserProject.setCompanyName(sysFlowUserProject.getCompanyName());
           // 局ID
           dbSysFlowUserProject.setJuId(sysFlowUserProject.getJuId());
           // 局名称
           dbSysFlowUserProject.setJuName(sysFlowUserProject.getJuName());
           // 总部ID
           dbSysFlowUserProject.setHeadquartersId(sysFlowUserProject.getHeadquartersId());
           // 总部名称
           dbSysFlowUserProject.setHeadquartersName(sysFlowUserProject.getHeadquartersName());
           // 托管ID
           dbSysFlowUserProject.setDelegateId(sysFlowUserProject.getDelegateId());
           // 托管名称
           dbSysFlowUserProject.setDelegateName(sysFlowUserProject.getDelegateName());
           // 共通
           dbSysFlowUserProject.setModifyUserInfo(userKey, realName);
           flag = sysFlowUserProjectMapper.updateByPrimaryKey(dbSysFlowUserProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysFlowUserProject);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysFlowUserProject(List<SysFlowUserProject> sysFlowUserProjectList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysFlowUserProjectList != null && sysFlowUserProjectList.size() > 0) {
           SysFlowUserProject sysFlowUserProject = new SysFlowUserProject();
           sysFlowUserProject.setModifyUserInfo(userKey, realName);
           flag = sysFlowUserProjectMapper.batchDeleteUpdateSysFlowUserProject(sysFlowUserProjectList, sysFlowUserProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysFlowUserProjectList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    /**
     * 项目类型查询
     * 
     * @param sysFlowUserProject
     * @return
     */
    @Override
    public String getSysFlowUserProjectType(SysFlowUserProject sysFlowUserProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String flag = request.getParameter("flag");
        if(sysFlowUserProject == null || StrUtil.isEmpty(sysFlowUserProject.getWorkId())) {
            return "";
        }
        SysFlowUserProject dbSysFlowUserProject = sysFlowUserProjectMapper.selectByPrimaryKey(sysFlowUserProject.getWorkId());
        if(dbSysFlowUserProject == null) {
            return "";
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("zb", true);
        jsonObject.set("tg", true);
        
        // 总部
        if(StrUtil.equals("zb", flag)
                && StrUtil.isNotEmpty(dbSysFlowUserProject.getHeadquartersId())) {
            jsonObject.set("zb", false);
        } 
        // 托管
        if(StrUtil.equals("tg", flag)
                && StrUtil.isNotEmpty(dbSysFlowUserProject.getDelegateId())) {
            jsonObject.set("tg", false);
        } 
        
        return jsonObject.toString();
    }
    
    @Override
    public ResponseEntity addSysFlowUserProjectByFlow(SysFlowUserProject sysFlowUserProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysFlowUserProject.setWorkId(sysFlowUserProject.getWorkId());
        // 项目信息
        SysProject sysProject = sysProjectMapper.selectByPrimaryKey(sysFlowUserProject.getProjectId());
        BeanUtil.copyProperties(sysProject, sysFlowUserProject, true);
        
        sysFlowUserProject.setCreateUserInfo(userKey, realName);
        int flag = sysFlowUserProjectMapper.insert(sysFlowUserProject);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysFlowUserProject);
        }
    }
}
