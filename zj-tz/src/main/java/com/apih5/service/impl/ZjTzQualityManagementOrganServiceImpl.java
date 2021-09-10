package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzQualityManagementOrganMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzQualityManagementOrgan;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzQualityManagementOrganService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzQualityManagementOrganService")
public class ZjTzQualityManagementOrganServiceImpl implements ZjTzQualityManagementOrganService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzQualityManagementOrganMapper zjTzQualityManagementOrganMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzQualityManagementOrganAddFile(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzQualityManagementOrgan.setQualityOrganId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzQualityManagementOrgan.getSubprojectsId());        
        if(info !=null) {
        	zjTzQualityManagementOrgan.setSubprojectsName(info.getSubprojectName());
        }             
        zjTzQualityManagementOrgan.setCreateUserInfo(userKey, realName);
        int flag = zjTzQualityManagementOrganMapper.insert(zjTzQualityManagementOrgan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//新增附件
        	if(zjTzQualityManagementOrgan.getQualityOrganFileList().size()>0 && zjTzQualityManagementOrgan.getQualityOrganFileList() != null) {
        		for(ZjTzFile file : zjTzQualityManagementOrgan.getQualityOrganFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzQualityManagementOrgan.getQualityOrganId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}        	
            return repEntity.ok("sys.data.sava", zjTzQualityManagementOrgan);
        }
	}

	@Override
	public ResponseEntity updateZjTzQualityManagementOrganAddFile(
			ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzQualityManagementOrgan dbzjTzQualityManagementOrgan = zjTzQualityManagementOrganMapper.selectByPrimaryKey(zjTzQualityManagementOrgan.getQualityOrganId());
        if (dbzjTzQualityManagementOrgan != null && StrUtil.isNotEmpty(dbzjTzQualityManagementOrgan.getQualityOrganId())) {
           // 项目ID
           dbzjTzQualityManagementOrgan.setProjectId(zjTzQualityManagementOrgan.getProjectId());
           // 项目名称
           dbzjTzQualityManagementOrgan.setProjectName(zjTzQualityManagementOrgan.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzQualityManagementOrgan.getSubprojectsId(), dbzjTzQualityManagementOrgan.getSubprojectsId())) {
        	   dbzjTzQualityManagementOrgan.setSubprojectsId(zjTzQualityManagementOrgan.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzQualityManagementOrgan.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzQualityManagementOrgan.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzQualityManagementOrgan.setQualityOrganTitle(zjTzQualityManagementOrgan.getQualityOrganTitle());
           // 主要内容
           dbzjTzQualityManagementOrgan.setQualityOrganContent(zjTzQualityManagementOrgan.getQualityOrganContent());
           // 登记日期
           dbzjTzQualityManagementOrgan.setQualityOrganDate(zjTzQualityManagementOrgan.getQualityOrganDate());
           // 登记用户
           dbzjTzQualityManagementOrgan.setQualityOrganUser(zjTzQualityManagementOrgan.getQualityOrganUser());
           // 共通
           dbzjTzQualityManagementOrgan.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityManagementOrganMapper.updateByPrimaryKey(dbzjTzQualityManagementOrgan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzQualityManagementOrgan.getQualityOrganId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//新增附件
        	if(zjTzQualityManagementOrgan.getQualityOrganFileList().size()>0 && zjTzQualityManagementOrgan.getQualityOrganFileList() != null) {
        		for(ZjTzFile file : zjTzQualityManagementOrgan.getQualityOrganFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzQualityManagementOrgan.getQualityOrganId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}           	
            return repEntity.ok("sys.data.update",zjTzQualityManagementOrgan);
        }
	}
	
    @Override
    public ResponseEntity getZjTzQualityManagementOrganListByCondition(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        if (zjTzQualityManagementOrgan == null) {
            zjTzQualityManagementOrgan = new ZjTzQualityManagementOrgan();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzQualityManagementOrgan.getProjectId(), true)) {
//        	zjTzQualityManagementOrgan.setProjectId("");
//        	zjTzQualityManagementOrgan.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }    
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzQualityManagementOrgan.getProjectId(), true)) {
                zjTzQualityManagementOrgan.setProjectId("");
                zjTzQualityManagementOrgan.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzQualityManagementOrgan.getProjectId(), true)) {
                zjTzQualityManagementOrgan.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzQualityManagementOrgan.getPage(),zjTzQualityManagementOrgan.getLimit());
        // 获取数据
        List<ZjTzQualityManagementOrgan> zjTzQualityManagementOrganList = zjTzQualityManagementOrganMapper.selectByZjTzQualityManagementOrganList(zjTzQualityManagementOrgan);
        for(ZjTzQualityManagementOrgan managementOrgan : zjTzQualityManagementOrganList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getQualityOrganId());
        	managementOrgan.setQualityOrganFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }  
        // 得到分页信息
        PageInfo<ZjTzQualityManagementOrgan> p = new PageInfo<>(zjTzQualityManagementOrganList);

        return repEntity.okList(zjTzQualityManagementOrganList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzQualityManagementOrganDetails(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        if (zjTzQualityManagementOrgan == null) {
            zjTzQualityManagementOrgan = new ZjTzQualityManagementOrgan();
        }
        // 获取数据
        ZjTzQualityManagementOrgan dbZjTzQualityManagementOrgan = zjTzQualityManagementOrganMapper.selectByPrimaryKey(zjTzQualityManagementOrgan.getQualityOrganId());
        // 数据存在
        if (dbZjTzQualityManagementOrgan != null) {
            return repEntity.ok(dbZjTzQualityManagementOrgan);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzQualityManagementOrgan(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzQualityManagementOrgan.setQualityOrganId(UuidUtil.generate());
        zjTzQualityManagementOrgan.setCreateUserInfo(userKey, realName);
        int flag = zjTzQualityManagementOrganMapper.insert(zjTzQualityManagementOrgan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzQualityManagementOrgan);
        }
    }

    @Override
    public ResponseEntity updateZjTzQualityManagementOrgan(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzQualityManagementOrgan dbzjTzQualityManagementOrgan = zjTzQualityManagementOrganMapper.selectByPrimaryKey(zjTzQualityManagementOrgan.getQualityOrganId());
        if (dbzjTzQualityManagementOrgan != null && StrUtil.isNotEmpty(dbzjTzQualityManagementOrgan.getQualityOrganId())) {
           // 项目ID
           dbzjTzQualityManagementOrgan.setProjectId(zjTzQualityManagementOrgan.getProjectId());
           // 项目名称
           dbzjTzQualityManagementOrgan.setProjectName(zjTzQualityManagementOrgan.getProjectName());
           // 子项目ID
           dbzjTzQualityManagementOrgan.setSubprojectsId(zjTzQualityManagementOrgan.getSubprojectsId());
           // 子项目名称
           dbzjTzQualityManagementOrgan.setSubprojectsName(zjTzQualityManagementOrgan.getSubprojectsName());
           // 标题
           dbzjTzQualityManagementOrgan.setQualityOrganTitle(zjTzQualityManagementOrgan.getQualityOrganTitle());
           // 主要内容
           dbzjTzQualityManagementOrgan.setQualityOrganContent(zjTzQualityManagementOrgan.getQualityOrganContent());
           // 登记日期
           dbzjTzQualityManagementOrgan.setQualityOrganDate(zjTzQualityManagementOrgan.getQualityOrganDate());
           // 登记用户
           dbzjTzQualityManagementOrgan.setQualityOrganUser(zjTzQualityManagementOrgan.getQualityOrganUser());
           // 共通
           dbzjTzQualityManagementOrgan.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityManagementOrganMapper.updateByPrimaryKey(dbzjTzQualityManagementOrgan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzQualityManagementOrgan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzQualityManagementOrgan(List<ZjTzQualityManagementOrgan> zjTzQualityManagementOrganList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzQualityManagementOrganList != null && zjTzQualityManagementOrganList.size() > 0) {
           ZjTzQualityManagementOrgan zjTzQualityManagementOrgan = new ZjTzQualityManagementOrgan();
           zjTzQualityManagementOrgan.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityManagementOrganMapper.batchDeleteUpdateZjTzQualityManagementOrgan(zjTzQualityManagementOrganList, zjTzQualityManagementOrgan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
            for(ZjTzQualityManagementOrgan managementOrgan : zjTzQualityManagementOrganList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementOrgan.getQualityOrganId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }   
            return repEntity.ok("sys.data.delete",zjTzQualityManagementOrganList);
        }
    }

}
