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
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzQualityManagementSystemMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzQualityManagementSystem;
import com.apih5.mybatis.pojo.ZjTzSecurityManagementSystem;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzQualityManagementSystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzQualityManagementSystemService")
public class ZjTzQualityManagementSystemServiceImpl implements ZjTzQualityManagementSystemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzQualityManagementSystemMapper zjTzQualityManagementSystemMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity updateZjTzQualityManagementSystemAddFile(
			ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
	      HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        ZjTzQualityManagementSystem dbzjTzQualityManagementSystem = zjTzQualityManagementSystemMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getQualityId());
	        if (dbzjTzQualityManagementSystem != null && StrUtil.isNotEmpty(dbzjTzQualityManagementSystem.getQualityId())) {
	           // 项目ID
	           dbzjTzQualityManagementSystem.setProjectId(zjTzQualityManagementSystem.getProjectId());
	           // 项目名称
	           dbzjTzQualityManagementSystem.setProjectName(zjTzQualityManagementSystem.getProjectName());
	           // 子项目ID           
	           if(!StrUtil.equals(zjTzQualityManagementSystem.getSubprojectsId(), dbzjTzQualityManagementSystem.getSubprojectsId())) {
	        	   dbzjTzQualityManagementSystem.setSubprojectsId(zjTzQualityManagementSystem.getSubprojectsId());
	               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getSubprojectsId());        
	               if(info !=null) {
	            	   dbzjTzQualityManagementSystem.setSubprojectsName(info.getSubprojectName());
	               }
	           }
	           // 标题
	           dbzjTzQualityManagementSystem.setQualityTitle(zjTzQualityManagementSystem.getQualityTitle());
	           // 主要内容
	           dbzjTzQualityManagementSystem.setQualityContent(zjTzQualityManagementSystem.getQualityContent());
	           // 登记日期
	           dbzjTzQualityManagementSystem.setQualityRegisterDate(zjTzQualityManagementSystem.getQualityRegisterDate());
	           // 登记用户
	           dbzjTzQualityManagementSystem.setQualityRegisterUser(zjTzQualityManagementSystem.getQualityRegisterUser());
	           // 共通
	           dbzjTzQualityManagementSystem.setModifyUserInfo(userKey, realName);
	           flag = zjTzQualityManagementSystemMapper.updateByPrimaryKey(dbzjTzQualityManagementSystem);
	        }
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	        	ZjTzFile zjTzFile = new ZjTzFile();
	        	zjTzFile.setOtherId(zjTzQualityManagementSystem.getQualityId());
	        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
	        	if(zjTzFileList.size()>0) {
	        		zjTzFile.setModifyUserInfo(userKey, realName);
	        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
	        	}
	        	//新增附件
	        	if(zjTzQualityManagementSystem.getQualityFileList().size()>0 && zjTzQualityManagementSystem.getQualityFileList() != null) {
	        		for(ZjTzFile file : zjTzQualityManagementSystem.getQualityFileList()) {
	        			file.setUid(UuidUtil.generate());
	        			file.setOtherId(zjTzQualityManagementSystem.getQualityId());
	        			file.setCreateUserInfo(userKey, realName);
	                    zjTzFileMapper.insert(file);
	        		}
	        	}  
	            return repEntity.ok("sys.data.update",zjTzQualityManagementSystem);
	        }
	}
	
	@Override
	public ResponseEntity saveZjTzQualityManagementSystemAddFile(
			ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getSubprojectsId());        
        if(info !=null) {
        	zjTzQualityManagementSystem.setSubprojectsName(info.getSubprojectName());
        }
        zjTzQualityManagementSystem.setQualityId(UuidUtil.generate());
        zjTzQualityManagementSystem.setCreateUserInfo(userKey, realName);
        int flag = zjTzQualityManagementSystemMapper.insert(zjTzQualityManagementSystem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//新增附件
        	if(zjTzQualityManagementSystem.getQualityFileList().size()>0 && zjTzQualityManagementSystem.getQualityFileList() != null) {
        		for(ZjTzFile file : zjTzQualityManagementSystem.getQualityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzQualityManagementSystem.getQualityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}
            return repEntity.ok("sys.data.sava", zjTzQualityManagementSystem);
        }
	}
	
    @Override
    public ResponseEntity getZjTzQualityManagementSystemListByCondition(ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        if (zjTzQualityManagementSystem == null) {
            zjTzQualityManagementSystem = new ZjTzQualityManagementSystem();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzQualityManagementSystem.getProjectId(), true)) {
//        	zjTzQualityManagementSystem.setProjectId("");
//        	zjTzQualityManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }   
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzQualityManagementSystem.getProjectId(), true)) {
                zjTzQualityManagementSystem.setProjectId("");
                zjTzQualityManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzQualityManagementSystem.getProjectId(), true)) {
                zjTzQualityManagementSystem.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzQualityManagementSystem.getPage(),zjTzQualityManagementSystem.getLimit());
        // 获取数据
        List<ZjTzQualityManagementSystem> zjTzQualityManagementSystemList = zjTzQualityManagementSystemMapper.selectByZjTzQualityManagementSystemList(zjTzQualityManagementSystem);
        for(ZjTzQualityManagementSystem managementSystem : zjTzQualityManagementSystemList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementSystem.getQualityId());
        	managementSystem.setQualityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }        
        // 得到分页信息
        PageInfo<ZjTzQualityManagementSystem> p = new PageInfo<>(zjTzQualityManagementSystemList);

        return repEntity.okList(zjTzQualityManagementSystemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzQualityManagementSystemDetails(ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        if (zjTzQualityManagementSystem == null) {
            zjTzQualityManagementSystem = new ZjTzQualityManagementSystem();
        }
        // 获取数据
        ZjTzQualityManagementSystem dbZjTzQualityManagementSystem = zjTzQualityManagementSystemMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getQualityId());
        // 数据存在
        if (dbZjTzQualityManagementSystem != null) {
            return repEntity.ok(dbZjTzQualityManagementSystem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzQualityManagementSystem(ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzQualityManagementSystem.setQualityId(UuidUtil.generate());
        zjTzQualityManagementSystem.setCreateUserInfo(userKey, realName);
        int flag = zjTzQualityManagementSystemMapper.insert(zjTzQualityManagementSystem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzQualityManagementSystem);
        }
    }

    @Override
    public ResponseEntity updateZjTzQualityManagementSystem(ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzQualityManagementSystem dbzjTzQualityManagementSystem = zjTzQualityManagementSystemMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getQualityId());
        if (dbzjTzQualityManagementSystem != null && StrUtil.isNotEmpty(dbzjTzQualityManagementSystem.getQualityId())) {
           // 项目ID
           dbzjTzQualityManagementSystem.setProjectId(zjTzQualityManagementSystem.getProjectId());
           // 项目名称
           dbzjTzQualityManagementSystem.setProjectName(zjTzQualityManagementSystem.getProjectName());
           // 子项目ID
           dbzjTzQualityManagementSystem.setSubprojectsId(zjTzQualityManagementSystem.getSubprojectsId());
           // 子项目名称
           dbzjTzQualityManagementSystem.setSubprojectsName(zjTzQualityManagementSystem.getSubprojectsName());
           // 标题
           dbzjTzQualityManagementSystem.setQualityTitle(zjTzQualityManagementSystem.getQualityTitle());
           // 主要内容
           dbzjTzQualityManagementSystem.setQualityContent(zjTzQualityManagementSystem.getQualityContent());
           // 登记日期
           dbzjTzQualityManagementSystem.setQualityRegisterDate(zjTzQualityManagementSystem.getQualityRegisterDate());
           // 登记用户
           dbzjTzQualityManagementSystem.setQualityRegisterUser(zjTzQualityManagementSystem.getQualityRegisterUser());
           // 共通
           dbzjTzQualityManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityManagementSystemMapper.updateByPrimaryKey(dbzjTzQualityManagementSystem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzQualityManagementSystem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzQualityManagementSystem(List<ZjTzQualityManagementSystem> zjTzQualityManagementSystemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzQualityManagementSystemList != null && zjTzQualityManagementSystemList.size() > 0) {
           ZjTzQualityManagementSystem zjTzQualityManagementSystem = new ZjTzQualityManagementSystem();
           zjTzQualityManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityManagementSystemMapper.batchDeleteUpdateZjTzQualityManagementSystem(zjTzQualityManagementSystemList, zjTzQualityManagementSystem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除附件表
        	for(ZjTzQualityManagementSystem managementSystem : zjTzQualityManagementSystemList) {
        		ZjTzFile zjTzFile = new ZjTzFile();
	        	zjTzFile.setOtherId(managementSystem.getQualityId());
	        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
	        	if(zjTzFileList.size()>0) {
	        		zjTzFile.setModifyUserInfo(userKey, realName);
	        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
	        	}
        	}
            return repEntity.ok("sys.data.delete",zjTzQualityManagementSystemList);
        }
    }
    
}
