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
import com.apih5.mybatis.dao.SysWoaSmallModuleMapper;
import com.apih5.mybatis.pojo.SysWoaSmallModule;
import com.apih5.service.SysWoaSmallModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysWoaSmallModuleService")
public class SysWoaSmallModuleServiceImpl implements SysWoaSmallModuleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWoaSmallModuleMapper sysWoaSmallModuleMapper;

    @Override
    public ResponseEntity getSysWoaSmallModuleListByCondition(SysWoaSmallModule sysWoaSmallModule) {
        if (sysWoaSmallModule == null) {
            sysWoaSmallModule = new SysWoaSmallModule();
        }
        // 分页查询
        PageHelper.startPage(sysWoaSmallModule.getPage(),sysWoaSmallModule.getLimit());
        // 获取数据
        List<SysWoaSmallModule> sysWoaSmallModuleList = sysWoaSmallModuleMapper.selectBySysWoaSmallModuleList(sysWoaSmallModule);
        // 得到分页信息
        PageInfo<SysWoaSmallModule> p = new PageInfo<>(sysWoaSmallModuleList);

        return repEntity.okList(sysWoaSmallModuleList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysWoaSmallModule(SysWoaSmallModule sysWoaSmallModule) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysWoaSmallModule.setSmallModuleId(UuidUtil.generate());
        sysWoaSmallModule.setCreateUserInfo(userKey, realName);
        int flag = sysWoaSmallModuleMapper.insert(sysWoaSmallModule);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWoaSmallModule);
        }
    }

    @Override
    public ResponseEntity updateSysWoaSmallModule(SysWoaSmallModule sysWoaSmallModule) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWoaSmallModule dbsysWoaSmallModule = sysWoaSmallModuleMapper.selectByPrimaryKey(sysWoaSmallModule.getSmallModuleId());
        if (dbsysWoaSmallModule != null && StrUtil.isNotEmpty(dbsysWoaSmallModule.getSmallModuleId())) {
           // 是否集成待办显示
           dbsysWoaSmallModule.setTodoShow(sysWoaSmallModule.getTodoShow());
           // 小模块类型
           dbsysWoaSmallModule.setSmallModuleType(sysWoaSmallModule.getSmallModuleType());
           // 小模块标题
           dbsysWoaSmallModule.setSmallModuleTitle(sysWoaSmallModule.getSmallModuleTitle());
           // 小模块链接
           dbsysWoaSmallModule.setSmallModuleLink(sysWoaSmallModule.getSmallModuleLink());
           // 小模块app链接
           dbsysWoaSmallModule.setSmallAppLink(sysWoaSmallModule.getSmallAppLink());
           // 小模块WEB链接
           dbsysWoaSmallModule.setSmallWebLink(sysWoaSmallModule.getSmallWebLink());
           // 小模块图标
           dbsysWoaSmallModule.setSmallModuleIcon(sysWoaSmallModule.getSmallModuleIcon());
           // 小模块点击后图标
           dbsysWoaSmallModule.setSmallModuleEventIcon(sysWoaSmallModule.getSmallModuleEventIcon());
           // 大模块主键ID
           dbsysWoaSmallModule.setLargelModuleId(sysWoaSmallModule.getLargelModuleId());
           // 排序
           dbsysWoaSmallModule.setSmallModuleSort(sysWoaSmallModule.getSmallModuleSort());
           // 共通
           dbsysWoaSmallModule.setModifyUserInfo(userKey, realName);
           flag = sysWoaSmallModuleMapper.updateByPrimaryKey(dbsysWoaSmallModule);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWoaSmallModule);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWoaSmallModule(List<SysWoaSmallModule> sysWoaSmallModuleList) {
        int flag = 0;
        if (sysWoaSmallModuleList != null && sysWoaSmallModuleList.size() > 0) {
           flag = sysWoaSmallModuleMapper.batchDeleteUpdateSysWoaSmallModule(sysWoaSmallModuleList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWoaSmallModuleList);
        }
    }
}
