package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzSafetyManagementOrganMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSafetyManagementOrgan;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSafetyManagementOrganService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjTzSafetyManagementOrganService")
public class ZjTzSafetyManagementOrganServiceImpl implements ZjTzSafetyManagementOrganService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSafetyManagementOrganMapper zjTzSafetyManagementOrganMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSafetyManagementOrganAddFile(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSafetyManagementOrgan.setSafetyOrganId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyManagementOrgan.getSubprojectsId());        
        if(info !=null) {
        	zjTzSafetyManagementOrgan.setSubprojectsName(info.getSubprojectName());
        }          
        zjTzSafetyManagementOrgan.setCreateUserInfo(userKey, realName);
        int flag = zjTzSafetyManagementOrganMapper.insert(zjTzSafetyManagementOrgan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//新增附件
        	if(zjTzSafetyManagementOrgan.getSafetyOrganFileList().size()>0 && zjTzSafetyManagementOrgan.getSafetyOrganFileList() != null) {
        		for(ZjTzFile file : zjTzSafetyManagementOrgan.getSafetyOrganFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSafetyManagementOrgan.getSafetyOrganId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}             	
            return repEntity.ok("sys.data.sava", zjTzSafetyManagementOrgan);
        }
	}

	@Override
	public ResponseEntity updateZjTzSafetyManagementOrganAddFile(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSafetyManagementOrgan dbzjTzSafetyManagementOrgan = zjTzSafetyManagementOrganMapper.selectByPrimaryKey(zjTzSafetyManagementOrgan.getSafetyOrganId());
        if (dbzjTzSafetyManagementOrgan != null && StrUtil.isNotEmpty(dbzjTzSafetyManagementOrgan.getSafetyOrganId())) {
           // 项目ID
           dbzjTzSafetyManagementOrgan.setProjectId(zjTzSafetyManagementOrgan.getProjectId());
           // 项目名称
           dbzjTzSafetyManagementOrgan.setProjectName(zjTzSafetyManagementOrgan.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzSafetyManagementOrgan.getSubprojectsId(), dbzjTzSafetyManagementOrgan.getSubprojectsId())) {
        	   dbzjTzSafetyManagementOrgan.setSubprojectsId(zjTzSafetyManagementOrgan.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyManagementOrgan.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSafetyManagementOrgan.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzSafetyManagementOrgan.setSafetyOrganTitle(zjTzSafetyManagementOrgan.getSafetyOrganTitle());
           // 主要内容
           dbzjTzSafetyManagementOrgan.setSafetyOrganContent(zjTzSafetyManagementOrgan.getSafetyOrganContent());
           // 登记日期
           dbzjTzSafetyManagementOrgan.setSafetyOrganDate(zjTzSafetyManagementOrgan.getSafetyOrganDate());
           // 登记用户
           dbzjTzSafetyManagementOrgan.setSafetyOrganUser(zjTzSafetyManagementOrgan.getSafetyOrganUser());
           // 共通
           dbzjTzSafetyManagementOrgan.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyManagementOrganMapper.updateByPrimaryKey(dbzjTzSafetyManagementOrgan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzSafetyManagementOrgan.getSafetyOrganId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//新增附件
        	if(zjTzSafetyManagementOrgan.getSafetyOrganFileList().size()>0 && zjTzSafetyManagementOrgan.getSafetyOrganFileList() != null) {
        		for(ZjTzFile file : zjTzSafetyManagementOrgan.getSafetyOrganFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSafetyManagementOrgan.getSafetyOrganId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.update",zjTzSafetyManagementOrgan);
        }
	}
	
    @Override
    public ResponseEntity getZjTzSafetyManagementOrganListByCondition(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        if (zjTzSafetyManagementOrgan == null) {
            zjTzSafetyManagementOrgan = new ZjTzSafetyManagementOrgan();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzSafetyManagementOrgan.getProjectId(), true)) {
//        	zjTzSafetyManagementOrgan.setProjectId("");
//        	zjTzSafetyManagementOrgan.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }   
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSafetyManagementOrgan.getProjectId(), true)) {
                zjTzSafetyManagementOrgan.setProjectId("");
                zjTzSafetyManagementOrgan.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSafetyManagementOrgan.getProjectId(), true)) {
                zjTzSafetyManagementOrgan.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzSafetyManagementOrgan.getPage(),zjTzSafetyManagementOrgan.getLimit());
        // 获取数据
        List<ZjTzSafetyManagementOrgan> zjTzSafetyManagementOrganList = zjTzSafetyManagementOrganMapper.selectByZjTzSafetyManagementOrganList(zjTzSafetyManagementOrgan);
        for(ZjTzSafetyManagementOrgan managementOrgan : zjTzSafetyManagementOrganList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getSafetyOrganId());
        	managementOrgan.setSafetyOrganFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // 得到分页信息
        PageInfo<ZjTzSafetyManagementOrgan> p = new PageInfo<>(zjTzSafetyManagementOrganList);

        return repEntity.okList(zjTzSafetyManagementOrganList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSafetyManagementOrganDetails(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        if (zjTzSafetyManagementOrgan == null) {
            zjTzSafetyManagementOrgan = new ZjTzSafetyManagementOrgan();
        }
        // 获取数据
        ZjTzSafetyManagementOrgan dbZjTzSafetyManagementOrgan = zjTzSafetyManagementOrganMapper.selectByPrimaryKey(zjTzSafetyManagementOrgan.getSafetyOrganId());
        // 数据存在
        if (dbZjTzSafetyManagementOrgan != null) {
            return repEntity.ok(dbZjTzSafetyManagementOrgan);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSafetyManagementOrgan(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSafetyManagementOrgan.setSafetyOrganId(UuidUtil.generate());
        zjTzSafetyManagementOrgan.setCreateUserInfo(userKey, realName);
        int flag = zjTzSafetyManagementOrganMapper.insert(zjTzSafetyManagementOrgan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSafetyManagementOrgan);
        }
    }

    @Override
    public ResponseEntity updateZjTzSafetyManagementOrgan(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSafetyManagementOrgan dbzjTzSafetyManagementOrgan = zjTzSafetyManagementOrganMapper.selectByPrimaryKey(zjTzSafetyManagementOrgan.getSafetyOrganId());
        if (dbzjTzSafetyManagementOrgan != null && StrUtil.isNotEmpty(dbzjTzSafetyManagementOrgan.getSafetyOrganId())) {
           // 项目ID
           dbzjTzSafetyManagementOrgan.setProjectId(zjTzSafetyManagementOrgan.getProjectId());
           // 项目名称
           dbzjTzSafetyManagementOrgan.setProjectName(zjTzSafetyManagementOrgan.getProjectName());
           // 子项目ID
           dbzjTzSafetyManagementOrgan.setSubprojectsId(zjTzSafetyManagementOrgan.getSubprojectsId());
           // 子项目名称
           dbzjTzSafetyManagementOrgan.setSubprojectsName(zjTzSafetyManagementOrgan.getSubprojectsName());
           // 标题
           dbzjTzSafetyManagementOrgan.setSafetyOrganTitle(zjTzSafetyManagementOrgan.getSafetyOrganTitle());
           // 主要内容
           dbzjTzSafetyManagementOrgan.setSafetyOrganContent(zjTzSafetyManagementOrgan.getSafetyOrganContent());
           // 登记日期
           dbzjTzSafetyManagementOrgan.setSafetyOrganDate(zjTzSafetyManagementOrgan.getSafetyOrganDate());
           // 登记用户
           dbzjTzSafetyManagementOrgan.setSafetyOrganUser(zjTzSafetyManagementOrgan.getSafetyOrganUser());
           // 共通
           dbzjTzSafetyManagementOrgan.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyManagementOrganMapper.updateByPrimaryKey(dbzjTzSafetyManagementOrgan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSafetyManagementOrgan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSafetyManagementOrgan(List<ZjTzSafetyManagementOrgan> zjTzSafetyManagementOrganList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSafetyManagementOrganList != null && zjTzSafetyManagementOrganList.size() > 0) {
           ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan = new ZjTzSafetyManagementOrgan();
           zjTzSafetyManagementOrgan.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyManagementOrganMapper.batchDeleteUpdateZjTzSafetyManagementOrgan(zjTzSafetyManagementOrganList, zjTzSafetyManagementOrgan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
            for(ZjTzSafetyManagementOrgan managementOrgan : zjTzSafetyManagementOrganList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementOrgan.getSafetyOrganId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }           	
            return repEntity.ok("sys.data.delete",zjTzSafetyManagementOrganList);
        }
    }

}
