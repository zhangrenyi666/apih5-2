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
import com.apih5.mybatis.dao.ZjTzSuperiorUnitQualityDutyMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitQualityDuty;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSuperiorUnitQualityDutyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjTzSuperiorUnitQualityDutyService")
public class ZjTzSuperiorUnitQualityDutyServiceImpl implements ZjTzSuperiorUnitQualityDutyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSuperiorUnitQualityDutyMapper zjTzSuperiorUnitQualityDutyMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSuperiorUnitQualityDutyAddFile(
			ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSubprojectsId());        
        if(info !=null) {
        	zjTzSuperiorUnitQualityDuty.setSubprojectsName(info.getSubprojectName());
        } 
        zjTzSuperiorUnitQualityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzSuperiorUnitQualityDutyMapper.insert(zjTzSuperiorUnitQualityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzSuperiorUnitQualityDuty.getUnitQualityFileList().size()>0 && zjTzSuperiorUnitQualityDuty.getUnitQualityFileList() != null) {
        		for(ZjTzFile file : zjTzSuperiorUnitQualityDuty.getUnitQualityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	} 
            return repEntity.ok("sys.data.sava", zjTzSuperiorUnitQualityDuty);
        }
	}

	@Override
	public ResponseEntity updateZjTzSuperiorUnitQualityDutyAddFile(
			ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSuperiorUnitQualityDuty dbzjTzSuperiorUnitQualityDuty = zjTzSuperiorUnitQualityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        if (dbzjTzSuperiorUnitQualityDuty != null && StrUtil.isNotEmpty(dbzjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId())) {
           // ??????ID
           dbzjTzSuperiorUnitQualityDuty.setProjectId(zjTzSuperiorUnitQualityDuty.getProjectId());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setProjectName(zjTzSuperiorUnitQualityDuty.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzSuperiorUnitQualityDuty.getSubprojectsId(), dbzjTzSuperiorUnitQualityDuty.getSubprojectsId())) {
        	   dbzjTzSuperiorUnitQualityDuty.setSubprojectsId(zjTzSuperiorUnitQualityDuty.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSuperiorUnitQualityDuty.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityTitle(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityTitle());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityContent(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityContent());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityDate(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityDate());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityUser(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityUser());
           // ??????
           dbzjTzSuperiorUnitQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitQualityDutyMapper.updateByPrimaryKey(dbzjTzSuperiorUnitQualityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzSuperiorUnitQualityDuty.getUnitQualityFileList().size()>0 && zjTzSuperiorUnitQualityDuty.getUnitQualityFileList() != null) {
        		for(ZjTzFile file : zjTzSuperiorUnitQualityDuty.getUnitQualityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}  
            return repEntity.ok("sys.data.update",zjTzSuperiorUnitQualityDuty);
        }
	}
	
    @Override
    public ResponseEntity getZjTzSuperiorUnitQualityDutyListByCondition(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        if (zjTzSuperiorUnitQualityDuty == null) {
            zjTzSuperiorUnitQualityDuty = new ZjTzSuperiorUnitQualityDuty();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzSuperiorUnitQualityDuty.getProjectId(), true)) {
//        	zjTzSuperiorUnitQualityDuty.setProjectId("");
//        	zjTzSuperiorUnitQualityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }    
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzSuperiorUnitQualityDuty.getProjectId(), true)) {
                zjTzSuperiorUnitQualityDuty.setProjectId("");
                zjTzSuperiorUnitQualityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzSuperiorUnitQualityDuty.getProjectId(), true)) {
                zjTzSuperiorUnitQualityDuty.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzSuperiorUnitQualityDuty.getPage(),zjTzSuperiorUnitQualityDuty.getLimit());
        // ????????????
        List<ZjTzSuperiorUnitQualityDuty> zjTzSuperiorUnitQualityDutyList = zjTzSuperiorUnitQualityDutyMapper.selectByZjTzSuperiorUnitQualityDutyList(zjTzSuperiorUnitQualityDuty);
        for(ZjTzSuperiorUnitQualityDuty managementOrgan : zjTzSuperiorUnitQualityDutyList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getSuperiorUnitQualityId());
        	managementOrgan.setUnitQualityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // ??????????????????
        PageInfo<ZjTzSuperiorUnitQualityDuty> p = new PageInfo<>(zjTzSuperiorUnitQualityDutyList);

        return repEntity.okList(zjTzSuperiorUnitQualityDutyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSuperiorUnitQualityDutyDetails(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        if (zjTzSuperiorUnitQualityDuty == null) {
            zjTzSuperiorUnitQualityDuty = new ZjTzSuperiorUnitQualityDuty();
        }
        // ????????????
        ZjTzSuperiorUnitQualityDuty dbZjTzSuperiorUnitQualityDuty = zjTzSuperiorUnitQualityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        // ????????????
        if (dbZjTzSuperiorUnitQualityDuty != null) {
            return repEntity.ok(dbZjTzSuperiorUnitQualityDuty);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzSuperiorUnitQualityDuty(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityId(UuidUtil.generate());
        zjTzSuperiorUnitQualityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzSuperiorUnitQualityDutyMapper.insert(zjTzSuperiorUnitQualityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSuperiorUnitQualityDuty);
        }
    }

    @Override
    public ResponseEntity updateZjTzSuperiorUnitQualityDuty(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSuperiorUnitQualityDuty dbzjTzSuperiorUnitQualityDuty = zjTzSuperiorUnitQualityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        if (dbzjTzSuperiorUnitQualityDuty != null && StrUtil.isNotEmpty(dbzjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId())) {
           // ??????ID
           dbzjTzSuperiorUnitQualityDuty.setProjectId(zjTzSuperiorUnitQualityDuty.getProjectId());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setProjectName(zjTzSuperiorUnitQualityDuty.getProjectName());
           // ?????????ID
           dbzjTzSuperiorUnitQualityDuty.setSubprojectsId(zjTzSuperiorUnitQualityDuty.getSubprojectsId());
           // ???????????????
           dbzjTzSuperiorUnitQualityDuty.setSubprojectsName(zjTzSuperiorUnitQualityDuty.getSubprojectsName());
           // ??????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityTitle(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityTitle());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityContent(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityContent());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityDate(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityDate());
           // ????????????
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityUser(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityUser());
           // ??????
           dbzjTzSuperiorUnitQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitQualityDutyMapper.updateByPrimaryKey(dbzjTzSuperiorUnitQualityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSuperiorUnitQualityDuty);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSuperiorUnitQualityDuty(List<ZjTzSuperiorUnitQualityDuty> zjTzSuperiorUnitQualityDutyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSuperiorUnitQualityDutyList != null && zjTzSuperiorUnitQualityDutyList.size() > 0) {
           ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty = new ZjTzSuperiorUnitQualityDuty();
           zjTzSuperiorUnitQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitQualityDutyMapper.batchDeleteUpdateZjTzSuperiorUnitQualityDuty(zjTzSuperiorUnitQualityDutyList, zjTzSuperiorUnitQualityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzSuperiorUnitQualityDuty managementOrgan : zjTzSuperiorUnitQualityDutyList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementOrgan.getSuperiorUnitQualityId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }         	
            return repEntity.ok("sys.data.delete",zjTzSuperiorUnitQualityDutyList);
        }
    }

}
