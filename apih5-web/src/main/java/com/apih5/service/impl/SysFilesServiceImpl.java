package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysFiles;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.SysFilesMapper;
import com.apih5.service.SysFilesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysFilesService")
public class SysFilesServiceImpl implements SysFilesService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysFilesMapper sysFilesMapper;

    @Override
    public ResponseEntity getSysFilesListByCondition(SysFiles sysFiles) {
        if (sysFiles == null) {
            sysFiles = new SysFiles();
        }
        // 分页查询
        PageHelper.startPage(sysFiles.getPage(),sysFiles.getLimit());
        // 获取数据
        List<SysFiles> sysFilesList = sysFilesMapper.selectBySysFilesList(sysFiles);
        // 得到分页信息
        PageInfo<SysFiles> p = new PageInfo<>(sysFilesList);

        return repEntity.okList(sysFilesList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysFiles(SysFiles sysFiles) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysFiles.setUid(UuidUtil.generate());
        sysFiles.setCreateUserInfo(userKey, realName);
        int flag = sysFilesMapper.insert(sysFiles);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysFiles);
        }
    }

    @Override
    public ResponseEntity updateSysFiles(SysFiles sysFiles) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysFiles dbsysFiles = sysFilesMapper.selectByPrimaryKey(sysFiles.getUid());
        if (dbsysFiles != null && StrUtil.isNotEmpty(dbsysFiles.getUid())) {
           // 共通
           dbsysFiles.setModifyUserInfo(userKey, realName);
           flag = sysFilesMapper.updateByPrimaryKey(dbsysFiles);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysFiles);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysFiles(List<SysFiles> sysFilesList) {
        int flag = 0;
        if (sysFilesList != null && sysFilesList.size() > 0) {
           flag = sysFilesMapper.batchDeleteUpdateSysFiles(sysFilesList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysFilesList);
        }
   }
}
