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
import com.apih5.mybatis.dao.ZjTzOtherManagementSystemMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzOtherManagementSystem;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSecurityManagementSystem;
import com.apih5.service.ZjTzOtherManagementSystemService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzOtherManagementSystemService")
public class ZjTzOtherManagementSystemServiceImpl implements ZjTzOtherManagementSystemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzOtherManagementSystemMapper zjTzOtherManagementSystemMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzOtherManagementSystemAddFile(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzOtherManagementSystem.setOtherId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getSubprojectsId());        
        if(info !=null) {
        	zjTzOtherManagementSystem.setSubprojectsName(info.getSubprojectName());
        }        
        zjTzOtherManagementSystem.setCreateUserInfo(userKey, realName);
        int flag = zjTzOtherManagementSystemMapper.insert(zjTzOtherManagementSystem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzOtherManagementSystem.getOtherFileList().size()>0 && zjTzOtherManagementSystem.getOtherFileList() != null) {
        		for(ZjTzFile file : zjTzOtherManagementSystem.getOtherFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzOtherManagementSystem.getOtherId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}
            return repEntity.ok("sys.data.sava", zjTzOtherManagementSystem);
        }
	}

	@Override
	public ResponseEntity updateZjTzOtherManagementSystemAddFile(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzOtherManagementSystem dbzjTzOtherManagementSystem = zjTzOtherManagementSystemMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getOtherId());
        if (dbzjTzOtherManagementSystem != null && StrUtil.isNotEmpty(dbzjTzOtherManagementSystem.getOtherId())) {
           // ??????ID
           dbzjTzOtherManagementSystem.setProjectId(zjTzOtherManagementSystem.getProjectId());
           // ????????????
           dbzjTzOtherManagementSystem.setProjectName(zjTzOtherManagementSystem.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzOtherManagementSystem.getSubprojectsId(), dbzjTzOtherManagementSystem.getSubprojectsId())) {
        	   dbzjTzOtherManagementSystem.setSubprojectsId(zjTzOtherManagementSystem.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzOtherManagementSystem.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzOtherManagementSystem.setOtherTitle(zjTzOtherManagementSystem.getOtherTitle());
           // ????????????
           dbzjTzOtherManagementSystem.setOtherContent(zjTzOtherManagementSystem.getOtherContent());
           // ????????????
           dbzjTzOtherManagementSystem.setOtherRegisterDate(zjTzOtherManagementSystem.getOtherRegisterDate());
           // ????????????
           dbzjTzOtherManagementSystem.setOtherRegisterUser(zjTzOtherManagementSystem.getOtherRegisterUser());
           // ??????
           dbzjTzOtherManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherManagementSystemMapper.updateByPrimaryKey(dbzjTzOtherManagementSystem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzOtherManagementSystem.getOtherId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzOtherManagementSystem.getOtherFileList().size()>0 && zjTzOtherManagementSystem.getOtherFileList() != null) {
        		for(ZjTzFile file : zjTzOtherManagementSystem.getOtherFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzOtherManagementSystem.getOtherId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}         	
            return repEntity.ok("sys.data.update",zjTzOtherManagementSystem);
        }
	}
	
    @Override
    public ResponseEntity getZjTzOtherManagementSystemListByCondition(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (zjTzOtherManagementSystem == null) {
            zjTzOtherManagementSystem = new ZjTzOtherManagementSystem();
        }
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzOtherManagementSystem.getProjectId(), true)) {
//        	zjTzOtherManagementSystem.setProjectId("");
//        	zjTzOtherManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }      
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzOtherManagementSystem.getProjectId(), true)) {
                zjTzOtherManagementSystem.setProjectId("");
                zjTzOtherManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzOtherManagementSystem.getProjectId(), true)) {
                zjTzOtherManagementSystem.setProjectId("");
            }
        }
        
        // ????????????
        PageHelper.startPage(zjTzOtherManagementSystem.getPage(),zjTzOtherManagementSystem.getLimit());
        // ????????????
        List<ZjTzOtherManagementSystem> zjTzOtherManagementSystemList = zjTzOtherManagementSystemMapper.selectByZjTzOtherManagementSystemList(zjTzOtherManagementSystem);
        for(ZjTzOtherManagementSystem managementSystem : zjTzOtherManagementSystemList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementSystem.getOtherId());
        	managementSystem.setOtherFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }        
        // ??????????????????
        PageInfo<ZjTzOtherManagementSystem> p = new PageInfo<>(zjTzOtherManagementSystemList);

        return repEntity.okList(zjTzOtherManagementSystemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzOtherManagementSystemDetails(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        if (zjTzOtherManagementSystem == null) {
            zjTzOtherManagementSystem = new ZjTzOtherManagementSystem();
        }
        // ????????????
        ZjTzOtherManagementSystem dbZjTzOtherManagementSystem = zjTzOtherManagementSystemMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getOtherId());
        // ????????????
        if (dbZjTzOtherManagementSystem != null) {
            return repEntity.ok(dbZjTzOtherManagementSystem);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzOtherManagementSystem(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzOtherManagementSystem.setOtherId(UuidUtil.generate());
        zjTzOtherManagementSystem.setCreateUserInfo(userKey, realName);
        int flag = zjTzOtherManagementSystemMapper.insert(zjTzOtherManagementSystem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzOtherManagementSystem);
        }
    }

    @Override
    public ResponseEntity updateZjTzOtherManagementSystem(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzOtherManagementSystem dbzjTzOtherManagementSystem = zjTzOtherManagementSystemMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getOtherId());
        if (dbzjTzOtherManagementSystem != null && StrUtil.isNotEmpty(dbzjTzOtherManagementSystem.getOtherId())) {
           // ??????ID
           dbzjTzOtherManagementSystem.setProjectId(zjTzOtherManagementSystem.getProjectId());
           // ????????????
           dbzjTzOtherManagementSystem.setProjectName(zjTzOtherManagementSystem.getProjectName());
           // ?????????ID
           dbzjTzOtherManagementSystem.setSubprojectsId(zjTzOtherManagementSystem.getSubprojectsId());
           // ???????????????
           dbzjTzOtherManagementSystem.setSubprojectsName(zjTzOtherManagementSystem.getSubprojectsName());
           // ??????
           dbzjTzOtherManagementSystem.setOtherTitle(zjTzOtherManagementSystem.getOtherTitle());
           // ????????????
           dbzjTzOtherManagementSystem.setOtherContent(zjTzOtherManagementSystem.getOtherContent());
           // ????????????
           dbzjTzOtherManagementSystem.setOtherRegisterDate(zjTzOtherManagementSystem.getOtherRegisterDate());
           // ????????????
           dbzjTzOtherManagementSystem.setOtherRegisterUser(zjTzOtherManagementSystem.getOtherRegisterUser());
           // ??????
           dbzjTzOtherManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherManagementSystemMapper.updateByPrimaryKey(dbzjTzOtherManagementSystem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzOtherManagementSystem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzOtherManagementSystem(List<ZjTzOtherManagementSystem> zjTzOtherManagementSystemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzOtherManagementSystemList != null && zjTzOtherManagementSystemList.size() > 0) {
           ZjTzOtherManagementSystem zjTzOtherManagementSystem = new ZjTzOtherManagementSystem();
           zjTzOtherManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherManagementSystemMapper.batchDeleteUpdateZjTzOtherManagementSystem(zjTzOtherManagementSystemList, zjTzOtherManagementSystem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzOtherManagementSystem managementSystem : zjTzOtherManagementSystemList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementSystem.getOtherId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }        	
            return repEntity.ok("sys.data.delete",zjTzOtherManagementSystemList);
        }
    }

}
