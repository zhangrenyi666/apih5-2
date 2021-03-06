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
	           // ??????ID
	           dbzjTzQualityManagementSystem.setProjectId(zjTzQualityManagementSystem.getProjectId());
	           // ????????????
	           dbzjTzQualityManagementSystem.setProjectName(zjTzQualityManagementSystem.getProjectName());
	           // ?????????ID           
	           if(!StrUtil.equals(zjTzQualityManagementSystem.getSubprojectsId(), dbzjTzQualityManagementSystem.getSubprojectsId())) {
	        	   dbzjTzQualityManagementSystem.setSubprojectsId(zjTzQualityManagementSystem.getSubprojectsId());
	               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getSubprojectsId());        
	               if(info !=null) {
	            	   dbzjTzQualityManagementSystem.setSubprojectsName(info.getSubprojectName());
	               }
	           }
	           // ??????
	           dbzjTzQualityManagementSystem.setQualityTitle(zjTzQualityManagementSystem.getQualityTitle());
	           // ????????????
	           dbzjTzQualityManagementSystem.setQualityContent(zjTzQualityManagementSystem.getQualityContent());
	           // ????????????
	           dbzjTzQualityManagementSystem.setQualityRegisterDate(zjTzQualityManagementSystem.getQualityRegisterDate());
	           // ????????????
	           dbzjTzQualityManagementSystem.setQualityRegisterUser(zjTzQualityManagementSystem.getQualityRegisterUser());
	           // ??????
	           dbzjTzQualityManagementSystem.setModifyUserInfo(userKey, realName);
	           flag = zjTzQualityManagementSystemMapper.updateByPrimaryKey(dbzjTzQualityManagementSystem);
	        }
	        // ??????
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
	        	//????????????
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
        	//????????????
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
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzQualityManagementSystem.getProjectId(), true)) {
//        	zjTzQualityManagementSystem.setProjectId("");
//        	zjTzQualityManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }   
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzQualityManagementSystem.getProjectId(), true)) {
                zjTzQualityManagementSystem.setProjectId("");
                zjTzQualityManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzQualityManagementSystem.getProjectId(), true)) {
                zjTzQualityManagementSystem.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzQualityManagementSystem.getPage(),zjTzQualityManagementSystem.getLimit());
        // ????????????
        List<ZjTzQualityManagementSystem> zjTzQualityManagementSystemList = zjTzQualityManagementSystemMapper.selectByZjTzQualityManagementSystemList(zjTzQualityManagementSystem);
        for(ZjTzQualityManagementSystem managementSystem : zjTzQualityManagementSystemList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementSystem.getQualityId());
        	managementSystem.setQualityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }        
        // ??????????????????
        PageInfo<ZjTzQualityManagementSystem> p = new PageInfo<>(zjTzQualityManagementSystemList);

        return repEntity.okList(zjTzQualityManagementSystemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzQualityManagementSystemDetails(ZjTzQualityManagementSystem zjTzQualityManagementSystem) {
        if (zjTzQualityManagementSystem == null) {
            zjTzQualityManagementSystem = new ZjTzQualityManagementSystem();
        }
        // ????????????
        ZjTzQualityManagementSystem dbZjTzQualityManagementSystem = zjTzQualityManagementSystemMapper.selectByPrimaryKey(zjTzQualityManagementSystem.getQualityId());
        // ????????????
        if (dbZjTzQualityManagementSystem != null) {
            return repEntity.ok(dbZjTzQualityManagementSystem);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
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
           // ??????ID
           dbzjTzQualityManagementSystem.setProjectId(zjTzQualityManagementSystem.getProjectId());
           // ????????????
           dbzjTzQualityManagementSystem.setProjectName(zjTzQualityManagementSystem.getProjectName());
           // ?????????ID
           dbzjTzQualityManagementSystem.setSubprojectsId(zjTzQualityManagementSystem.getSubprojectsId());
           // ???????????????
           dbzjTzQualityManagementSystem.setSubprojectsName(zjTzQualityManagementSystem.getSubprojectsName());
           // ??????
           dbzjTzQualityManagementSystem.setQualityTitle(zjTzQualityManagementSystem.getQualityTitle());
           // ????????????
           dbzjTzQualityManagementSystem.setQualityContent(zjTzQualityManagementSystem.getQualityContent());
           // ????????????
           dbzjTzQualityManagementSystem.setQualityRegisterDate(zjTzQualityManagementSystem.getQualityRegisterDate());
           // ????????????
           dbzjTzQualityManagementSystem.setQualityRegisterUser(zjTzQualityManagementSystem.getQualityRegisterUser());
           // ??????
           dbzjTzQualityManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityManagementSystemMapper.updateByPrimaryKey(dbzjTzQualityManagementSystem);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//???????????????
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
