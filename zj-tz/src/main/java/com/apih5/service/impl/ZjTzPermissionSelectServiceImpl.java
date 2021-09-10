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
import com.apih5.mybatis.dao.ZjTzPermissionSelectMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;
import com.apih5.service.ZjTzPermissionSelectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPermissionSelectService")
public class ZjTzPermissionSelectServiceImpl implements ZjTzPermissionSelectService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPermissionSelectMapper zjTzPermissionSelectMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;

    @Override
    public ResponseEntity getZjTzPermissionSelectListByCondition(ZjTzPermissionSelect zjTzPermissionSelect) {
        if (zjTzPermissionSelect == null) {
            zjTzPermissionSelect = new ZjTzPermissionSelect();
        }
        // 分页查询
        PageHelper.startPage(zjTzPermissionSelect.getPage(),zjTzPermissionSelect.getLimit());
        // 获取数据
        List<ZjTzPermissionSelect> zjTzPermissionSelectList = zjTzPermissionSelectMapper.selectByZjTzPermissionSelectList(zjTzPermissionSelect);
        // 得到分页信息
        PageInfo<ZjTzPermissionSelect> p = new PageInfo<>(zjTzPermissionSelectList);

        return repEntity.okList(zjTzPermissionSelectList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPermissionSelectDetails(ZjTzPermissionSelect zjTzPermissionSelect) {
        if (zjTzPermissionSelect == null) {
            zjTzPermissionSelect = new ZjTzPermissionSelect();
        }
        // 获取数据
        ZjTzPermissionSelect dbZjTzPermissionSelect = zjTzPermissionSelectMapper.selectByPrimaryKey(zjTzPermissionSelect.getPermissionSelectId());
        // 数据存在
        if (dbZjTzPermissionSelect != null) {
            return repEntity.ok(dbZjTzPermissionSelect);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPermissionSelect(ZjTzPermissionSelect zjTzPermissionSelect) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPermissionSelect.setPermissionSelectId(UuidUtil.generate());
        zjTzPermissionSelect.setCreateUserInfo(userKey, realName);
        int flag = zjTzPermissionSelectMapper.insert(zjTzPermissionSelect);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPermissionSelect);
        }
    }

    @Override
    public ResponseEntity updateZjTzPermissionSelect(ZjTzPermissionSelect zjTzPermissionSelect) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPermissionSelect dbzjTzPermissionSelect = zjTzPermissionSelectMapper.selectByPrimaryKey(zjTzPermissionSelect.getPermissionSelectId());
        if (dbzjTzPermissionSelect != null && StrUtil.isNotEmpty(dbzjTzPermissionSelect.getPermissionSelectId())) {
           // 项目ID
           dbzjTzPermissionSelect.setProjectId(zjTzPermissionSelect.getProjectId());
           // 项目名称
           dbzjTzPermissionSelect.setProjectName(zjTzPermissionSelect.getProjectName());
           // 项目简称
           dbzjTzPermissionSelect.setProjectShortName(zjTzPermissionSelect.getProjectShortName());
           // 用户Key
           dbzjTzPermissionSelect.setUserKey(zjTzPermissionSelect.getUserKey());
           // 用户名称
           dbzjTzPermissionSelect.setRealName(zjTzPermissionSelect.getRealName());
           // ext1
           dbzjTzPermissionSelect.setExt1(zjTzPermissionSelect.getExt1());
           // ext2
           dbzjTzPermissionSelect.setExt2(zjTzPermissionSelect.getExt2());
           // ext3
           dbzjTzPermissionSelect.setExt3(zjTzPermissionSelect.getExt3());
           // ext4
           dbzjTzPermissionSelect.setExt4(zjTzPermissionSelect.getExt4());
           // ext5
           dbzjTzPermissionSelect.setExt5(zjTzPermissionSelect.getExt5());
           // ext6
           dbzjTzPermissionSelect.setExt6(zjTzPermissionSelect.getExt6());
           // 共通
           dbzjTzPermissionSelect.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionSelectMapper.updateByPrimaryKey(dbzjTzPermissionSelect);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPermissionSelect);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPermissionSelect(List<ZjTzPermissionSelect> zjTzPermissionSelectList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPermissionSelectList != null && zjTzPermissionSelectList.size() > 0) {
           ZjTzPermissionSelect zjTzPermissionSelect = new ZjTzPermissionSelect();
           zjTzPermissionSelect.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionSelectMapper.batchDeleteUpdateZjTzPermissionSelect(zjTzPermissionSelectList, zjTzPermissionSelect);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPermissionSelectList);
        }
    }

    @Override
    public ResponseEntity changeZjTzProjectManagement(ZjTzPermissionSelect zjTzPermissionSelect) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 切换项目的ID
        String changeProjectId = zjTzPermissionSelect.getProjectId();
        if(StrUtil.isEmpty(changeProjectId)) {
            return repEntity.layerMessage("no", "请选择要切换的项目。");
        }
        if(StrUtil.equals("All", changeProjectId, true)) {
            return repEntity.ok("sys.data.sava", zjTzPermissionSelect);
        }

        // 删除所有项目
        ZjTzPermissionSelect zjTzPermissionSelectSelect = new ZjTzPermissionSelect();
        zjTzPermissionSelectSelect.setUserKey(userKey);
        List<ZjTzPermissionSelect> zjTzPermissionSelectList = zjTzPermissionSelectMapper.selectByZjTzPermissionSelectList(zjTzPermissionSelectSelect);
        if(zjTzPermissionSelectList != null && zjTzPermissionSelectList.size()>0) {
            for(ZjTzPermissionSelect dbZjTzPermissionSelect:zjTzPermissionSelectList) {
                zjTzPermissionSelectMapper.deleteByPrimaryKey(dbZjTzPermissionSelect.getPermissionSelectId());
            }
        }

        // 新增
        zjTzPermissionSelect.setPermissionSelectId(UuidUtil.generate());
        // 查询一下项目，并将项目的最新值给本表
        ZjTzProManage zjTzProjectManagement = zjTzProManageMapper.selectByPrimaryKey(zjTzPermissionSelect.getProjectId());
        // 项目ID
        zjTzPermissionSelect.setProjectId(zjTzProjectManagement.getProjectId());
        // 项目名称
        zjTzPermissionSelect.setProjectName(zjTzProjectManagement.getProjectName());
        // 项目简称
        zjTzPermissionSelect.setProjectShortName(zjTzProjectManagement.getProjectShortName());
        // 项目进展
        zjTzPermissionSelect.setProProcessId(zjTzProjectManagement.getProProcessId());
        // 工期分析主体
        zjTzPermissionSelect.setAnalySubject(zjTzProjectManagement.getAnalySubject());
        // 规模控制主体
        zjTzPermissionSelect.setSizeControlSubject(zjTzProjectManagement.getSizeControlSubject());
        // 用户Key
        zjTzPermissionSelect.setUserKey(userKey);
        // 用户名称
        zjTzPermissionSelect.setRealName(realName);
        zjTzPermissionSelect.setCreateUserInfo(userKey, realName);
        int flag = zjTzPermissionSelectMapper.insert(zjTzPermissionSelect);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPermissionSelect);
        }
    }
}
