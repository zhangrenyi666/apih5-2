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
import com.apih5.mybatis.dao.ZjTzSecurityManagementSystemMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSecurityManagementSystem;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSecurityManagementSystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjTzSecurityManagementSystemService")
public class ZjTzSecurityManagementSystemServiceImpl implements ZjTzSecurityManagementSystemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSecurityManagementSystemMapper zjTzSecurityManagementSystemMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSecurityManagementSystemAddFile(
			ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSecurityManagementSystem.getSubprojectsId());        
        if(info !=null) {
        	zjTzSecurityManagementSystem.setSubprojectsName(info.getSubprojectName());
        }
        zjTzSecurityManagementSystem.setSecurityId(UuidUtil.generate());
        zjTzSecurityManagementSystem.setCreateUserInfo(userKey, realName);
        int flag = zjTzSecurityManagementSystemMapper.insert(zjTzSecurityManagementSystem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzSecurityManagementSystem.getSecurityFileList().size()>0 && zjTzSecurityManagementSystem.getSecurityFileList() != null) {
        		for(ZjTzFile file : zjTzSecurityManagementSystem.getSecurityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSecurityManagementSystem.getSecurityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}
            return repEntity.ok("sys.data.sava", zjTzSecurityManagementSystem);
        }
	}

	@Override
	public ResponseEntity updateZjTzSecurityManagementSystemAddFile(
			ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSecurityManagementSystem dbzjTzSecurityManagementSystem = zjTzSecurityManagementSystemMapper.selectByPrimaryKey(zjTzSecurityManagementSystem.getSecurityId());
        if (dbzjTzSecurityManagementSystem != null && StrUtil.isNotEmpty(dbzjTzSecurityManagementSystem.getSecurityId())) {
           // ??????ID
           dbzjTzSecurityManagementSystem.setProjectId(zjTzSecurityManagementSystem.getProjectId());
           // ????????????
           dbzjTzSecurityManagementSystem.setProjectName(zjTzSecurityManagementSystem.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzSecurityManagementSystem.getSubprojectsId(), dbzjTzSecurityManagementSystem.getSubprojectsId())) {
        	   dbzjTzSecurityManagementSystem.setSubprojectsId(zjTzSecurityManagementSystem.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSecurityManagementSystem.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSecurityManagementSystem.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzSecurityManagementSystem.setSecurityTitle(zjTzSecurityManagementSystem.getSecurityTitle());
           // ????????????
           dbzjTzSecurityManagementSystem.setSecurityContent(zjTzSecurityManagementSystem.getSecurityContent());
           // ????????????
           dbzjTzSecurityManagementSystem.setSecurityRegisterDate(zjTzSecurityManagementSystem.getSecurityRegisterDate());
           // ????????????
           dbzjTzSecurityManagementSystem.setSecurityRegisterUser(zjTzSecurityManagementSystem.getSecurityRegisterUser());
           // ??????
           dbzjTzSecurityManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzSecurityManagementSystemMapper.updateByPrimaryKey(dbzjTzSecurityManagementSystem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzSecurityManagementSystem.getSecurityId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzSecurityManagementSystem.getSecurityFileList().size()>0 && zjTzSecurityManagementSystem.getSecurityFileList() != null) {
        		for(ZjTzFile file : zjTzSecurityManagementSystem.getSecurityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSecurityManagementSystem.getSecurityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}        	
            return repEntity.ok("sys.data.update",zjTzSecurityManagementSystem);
        }
	}
	
    @Override
    public ResponseEntity getZjTzSecurityManagementSystemListByCondition(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        if (zjTzSecurityManagementSystem == null) {
            zjTzSecurityManagementSystem = new ZjTzSecurityManagementSystem();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????
        PageHelper.startPage(zjTzSecurityManagementSystem.getPage(),zjTzSecurityManagementSystem.getLimit());
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzSecurityManagementSystem.getProjectId(), true)) {
//        	zjTzSecurityManagementSystem.setProjectId("");
//        	zjTzSecurityManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }    
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzSecurityManagementSystem.getProjectId(), true)) {
                zjTzSecurityManagementSystem.setProjectId("");
                zjTzSecurityManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzSecurityManagementSystem.getProjectId(), true)) {
                zjTzSecurityManagementSystem.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzSecurityManagementSystem> zjTzSecurityManagementSystemList = zjTzSecurityManagementSystemMapper.selectByZjTzSecurityManagementSystemList(zjTzSecurityManagementSystem);
        for(ZjTzSecurityManagementSystem managementSystem : zjTzSecurityManagementSystemList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementSystem.getSecurityId());
        	managementSystem.setSecurityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }
        // ??????????????????
        PageInfo<ZjTzSecurityManagementSystem> p = new PageInfo<>(zjTzSecurityManagementSystemList);

        return repEntity.okList(zjTzSecurityManagementSystemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSecurityManagementSystemDetails(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        if (zjTzSecurityManagementSystem == null) {
            zjTzSecurityManagementSystem = new ZjTzSecurityManagementSystem();
        }
        // ????????????
        ZjTzSecurityManagementSystem dbZjTzSecurityManagementSystem = zjTzSecurityManagementSystemMapper.selectByPrimaryKey(zjTzSecurityManagementSystem.getSecurityId());
        // ????????????
        if (dbZjTzSecurityManagementSystem != null) {
            return repEntity.ok(dbZjTzSecurityManagementSystem);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzSecurityManagementSystem(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSecurityManagementSystem.setSecurityId(UuidUtil.generate());
        zjTzSecurityManagementSystem.setCreateUserInfo(userKey, realName);
        int flag = zjTzSecurityManagementSystemMapper.insert(zjTzSecurityManagementSystem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSecurityManagementSystem);
        }
    }

    @Override
    public ResponseEntity updateZjTzSecurityManagementSystem(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSecurityManagementSystem dbzjTzSecurityManagementSystem = zjTzSecurityManagementSystemMapper.selectByPrimaryKey(zjTzSecurityManagementSystem.getSecurityId());
        if (dbzjTzSecurityManagementSystem != null && StrUtil.isNotEmpty(dbzjTzSecurityManagementSystem.getSecurityId())) {
           // ??????ID
           dbzjTzSecurityManagementSystem.setProjectId(zjTzSecurityManagementSystem.getProjectId());
           // ????????????
           dbzjTzSecurityManagementSystem.setProjectName(zjTzSecurityManagementSystem.getProjectName());
           // ?????????ID
           dbzjTzSecurityManagementSystem.setSubprojectsId(zjTzSecurityManagementSystem.getSubprojectsId());
           // ???????????????
           dbzjTzSecurityManagementSystem.setSubprojectsName(zjTzSecurityManagementSystem.getSubprojectsName());
           // ??????
           dbzjTzSecurityManagementSystem.setSecurityTitle(zjTzSecurityManagementSystem.getSecurityTitle());
           // ????????????
           dbzjTzSecurityManagementSystem.setSecurityContent(zjTzSecurityManagementSystem.getSecurityContent());
           // ????????????
           dbzjTzSecurityManagementSystem.setSecurityRegisterDate(zjTzSecurityManagementSystem.getSecurityRegisterDate());
           // ????????????
           dbzjTzSecurityManagementSystem.setSecurityRegisterUser(zjTzSecurityManagementSystem.getSecurityRegisterUser());
           // ??????
           dbzjTzSecurityManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzSecurityManagementSystemMapper.updateByPrimaryKey(dbzjTzSecurityManagementSystem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSecurityManagementSystem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSecurityManagementSystem(List<ZjTzSecurityManagementSystem> zjTzSecurityManagementSystemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSecurityManagementSystemList != null && zjTzSecurityManagementSystemList.size() > 0) {
           ZjTzSecurityManagementSystem zjTzSecurityManagementSystem = new ZjTzSecurityManagementSystem();
           zjTzSecurityManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzSecurityManagementSystemMapper.batchDeleteUpdateZjTzSecurityManagementSystem(zjTzSecurityManagementSystemList, zjTzSecurityManagementSystem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzSecurityManagementSystem managementSystem : zjTzSecurityManagementSystemList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementSystem.getSecurityId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }
            return repEntity.ok("sys.data.delete",zjTzSecurityManagementSystemList);
        }
    }

}
