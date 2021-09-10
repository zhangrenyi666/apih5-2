package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUserGroup;
import com.apih5.framework.api.sysdb.entity.SysUserGroupInfo;
import com.apih5.framework.api.sysdb.service.SysUserGroupService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysUserGroupInfoMapper;
import com.apih5.mybatis.dao.SysUserGroupMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysUserGroupService")
public class SysUserGroupServiceImpl implements SysUserGroupService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysUserGroupMapper sysUserGroupMapper;
    
    @Autowired(required = true)
    private SysUserGroupInfoMapper sysUserGroupInfoMapper;

    @Override
    public ResponseEntity getSysUserGroupListByCondition(SysUserGroup sysUserGroup) {
        if (sysUserGroup == null) {
            sysUserGroup = new SysUserGroup();
        }
        // 分页查询
        PageHelper.startPage(sysUserGroup.getPage(),sysUserGroup.getLimit());
        // 获取数据
        List<SysUserGroup> sysUserGroupList = sysUserGroupMapper.selectBySysUserGroupList(sysUserGroup);
        for(SysUserGroup dbSysUserGroup:sysUserGroupList) {
        	SysUserGroupInfo sysUserGroupInfo = new SysUserGroupInfo();
        	sysUserGroupInfo.setGroupId(dbSysUserGroup.getGroupId());
        	List<SysUserGroupInfo> sysUserGroupInfoList = sysUserGroupInfoMapper.selectBySysUserGroupInfoList(sysUserGroupInfo);
        	dbSysUserGroup.setSysUserGroupInfoList(sysUserGroupInfoList);
        }
        // 得到分页信息
        PageInfo<SysUserGroup> p = new PageInfo<>(sysUserGroupList);

        return repEntity.okList(sysUserGroupList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysUserGroupDetails(SysUserGroup sysUserGroup) {
        if (sysUserGroup == null) {
            sysUserGroup = new SysUserGroup();
        }
        // 获取数据
        SysUserGroup dbSysUserGroup = sysUserGroupMapper.selectByPrimaryKey(sysUserGroup.getGroupId());
        
        if(dbSysUserGroup != null) {
        	SysUserGroupInfo sysUserGroupInfo = new SysUserGroupInfo();
        	sysUserGroupInfo.setGroupId(dbSysUserGroup.getGroupId());
        	List<SysUserGroupInfo> sysUserGroupInfoList = sysUserGroupInfoMapper.selectBySysUserGroupInfoList(sysUserGroupInfo);
        	dbSysUserGroup.setSysUserGroupInfoList(sysUserGroupInfoList);
        }
    	// 数据存在
        if (dbSysUserGroup != null) {
            return repEntity.ok(dbSysUserGroup);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveSysUserGroup(SysUserGroup sysUserGroup) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	if(sysUserGroup == null || StrUtil.isEmpty(sysUserGroup.getGroupId())) {
    		return repEntity.layerMessage("no", "ID必填");
    	}
    	SysUserGroup dbSysUserGroup = sysUserGroupMapper.selectByPrimaryKey(sysUserGroup.getGroupId());
    	if(dbSysUserGroup != null && StrUtil.isNotEmpty(dbSysUserGroup.getGroupId())) {
    		return repEntity.layerMessage("no", "ID重复，请换另一个ID添加");
    	}
        sysUserGroup.setCreateUserInfo(userKey, realName);
        int flag = sysUserGroupMapper.insert(sysUserGroup);
        List<SysUserGroupInfo> sysUserGroupInfoList = sysUserGroup.getSysUserGroupInfoList();
        for(SysUserGroupInfo sysUserGroupInfo:sysUserGroupInfoList) {
        	sysUserGroupInfo.setGroupInfoId(UuidUtil.generate());
        	sysUserGroupInfo.setGroupId(sysUserGroup.getGroupId());
        	sysUserGroupInfo.setCreateUserInfo(userKey, realName);
        	sysUserGroupInfoMapper.insert(sysUserGroupInfo);
        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysUserGroup);
        }
    }

    @Override
    public ResponseEntity updateSysUserGroup(SysUserGroup sysUserGroup) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysUserGroup dbsysUserGroup = sysUserGroupMapper.selectByPrimaryKey(sysUserGroup.getGroupId());
        if (dbsysUserGroup != null && StrUtil.isNotEmpty(dbsysUserGroup.getGroupId())) {
        	// 用户组名称
        	dbsysUserGroup.setGroupName(sysUserGroup.getGroupName());
        	// 备注
        	dbsysUserGroup.setRemarks(sysUserGroup.getRemarks());
        	// 共通
        	dbsysUserGroup.setModifyUserInfo(userKey, realName);
        	flag = sysUserGroupMapper.updateByPrimaryKey(dbsysUserGroup);
        
        	// 物理删除原来的数据
        	SysUserGroupInfo sysUserGroupInfoSelect = new SysUserGroupInfo();
        	sysUserGroupInfoSelect.setGroupId(dbsysUserGroup.getGroupId());
        	List<SysUserGroupInfo> dbSysUserGroupInfoList = sysUserGroupInfoMapper.selectBySysUserGroupInfoList(sysUserGroupInfoSelect);
        	for(SysUserGroupInfo sysUserGroupInfo:dbSysUserGroupInfoList) {
        		sysUserGroupInfoMapper.deleteByPrimaryKey(sysUserGroupInfo.getGroupInfoId());
        	}
        	// 新组员添加
        	List<SysUserGroupInfo> sysUserGroupInfoList = sysUserGroup.getSysUserGroupInfoList();
        	for(SysUserGroupInfo sysUserGroupInfo:sysUserGroupInfoList) {
        		sysUserGroupInfo.setGroupInfoId(UuidUtil.generate());
        		sysUserGroupInfo.setGroupId(sysUserGroup.getGroupId());
        		sysUserGroupInfo.setCreateUserInfo(userKey, realName);
        		sysUserGroupInfoMapper.insert(sysUserGroupInfo);
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysUserGroup);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysUserGroup(List<SysUserGroup> sysUserGroupList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysUserGroupList != null && sysUserGroupList.size() > 0) {
           SysUserGroup sysUserGroup = new SysUserGroup();
           sysUserGroup.setModifyUserInfo(userKey, realName);
           flag = sysUserGroupMapper.batchDeleteUpdateSysUserGroup(sysUserGroupList, sysUserGroup);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysUserGroupList);
        }
    }
}
