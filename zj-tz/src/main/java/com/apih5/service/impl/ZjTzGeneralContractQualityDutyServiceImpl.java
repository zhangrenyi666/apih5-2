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
import com.apih5.mybatis.dao.ZjTzGeneralContractQualityDutyMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzGeneralContractQualityDuty;
import com.apih5.mybatis.pojo.ZjTzGeneralContractSecurityDuty;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.service.ZjTzGeneralContractQualityDutyService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjTzGeneralContractQualityDutyService")
public class ZjTzGeneralContractQualityDutyServiceImpl implements ZjTzGeneralContractQualityDutyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzGeneralContractQualityDutyMapper zjTzGeneralContractQualityDutyMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzGeneralContractQualityDutyAddFile(
			ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzGeneralContractQualityDuty.setGeneralContractQualityId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzGeneralContractQualityDuty.getSubprojectsId());        
        if(info !=null) {
        	zjTzGeneralContractQualityDuty.setSubprojectsName(info.getSubprojectName());
        }           
        zjTzGeneralContractQualityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzGeneralContractQualityDutyMapper.insert(zjTzGeneralContractQualityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzGeneralContractQualityDuty.getGenConQualityFileList().size()>0 && zjTzGeneralContractQualityDuty.getGenConQualityFileList() != null) {
        		for(ZjTzFile file : zjTzGeneralContractQualityDuty.getGenConQualityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzGeneralContractQualityDuty.getGeneralContractQualityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}         	
            return repEntity.ok("sys.data.sava", zjTzGeneralContractQualityDuty);
        }
	}

	@Override
	public ResponseEntity updateZjTzGeneralContractQualityDutyAddFile(
			ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzGeneralContractQualityDuty dbzjTzGeneralContractQualityDuty = zjTzGeneralContractQualityDutyMapper.selectByPrimaryKey(zjTzGeneralContractQualityDuty.getGeneralContractQualityId());
        if (dbzjTzGeneralContractQualityDuty != null && StrUtil.isNotEmpty(dbzjTzGeneralContractQualityDuty.getGeneralContractQualityId())) {
           // ??????ID
           dbzjTzGeneralContractQualityDuty.setProjectId(zjTzGeneralContractQualityDuty.getProjectId());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setProjectName(zjTzGeneralContractQualityDuty.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzGeneralContractQualityDuty.getSubprojectsId(), dbzjTzGeneralContractQualityDuty.getSubprojectsId())) {
        	   dbzjTzGeneralContractQualityDuty.setSubprojectsId(zjTzGeneralContractQualityDuty.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzGeneralContractQualityDuty.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzGeneralContractQualityDuty.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityTitle(zjTzGeneralContractQualityDuty.getGeneralContractQualityTitle());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityContent(zjTzGeneralContractQualityDuty.getGeneralContractQualityContent());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityDate(zjTzGeneralContractQualityDuty.getGeneralContractQualityDate());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityUser(zjTzGeneralContractQualityDuty.getGeneralContractQualityUser());
           // ??????
           dbzjTzGeneralContractQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzGeneralContractQualityDutyMapper.updateByPrimaryKey(dbzjTzGeneralContractQualityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzGeneralContractQualityDuty.getGeneralContractQualityId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzGeneralContractQualityDuty.getGenConQualityFileList().size()>0 && zjTzGeneralContractQualityDuty.getGenConQualityFileList() != null) {
        		for(ZjTzFile file : zjTzGeneralContractQualityDuty.getGenConQualityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzGeneralContractQualityDuty.getGeneralContractQualityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.update",zjTzGeneralContractQualityDuty);
        }
	}
	
    @Override
    public ResponseEntity getZjTzGeneralContractQualityDutyListByCondition(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        if (zjTzGeneralContractQualityDuty == null) {
            zjTzGeneralContractQualityDuty = new ZjTzGeneralContractQualityDuty();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzGeneralContractQualityDuty.getProjectId(), true)) {
//        	zjTzGeneralContractQualityDuty.setProjectId("");
//        	zjTzGeneralContractQualityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }    
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzGeneralContractQualityDuty.getProjectId(), true)) {
                zjTzGeneralContractQualityDuty.setProjectId("");
                zjTzGeneralContractQualityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzGeneralContractQualityDuty.getProjectId(), true)) {
                zjTzGeneralContractQualityDuty.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzGeneralContractQualityDuty.getPage(),zjTzGeneralContractQualityDuty.getLimit());
        // ????????????
        List<ZjTzGeneralContractQualityDuty> zjTzGeneralContractQualityDutyList = zjTzGeneralContractQualityDutyMapper.selectByZjTzGeneralContractQualityDutyList(zjTzGeneralContractQualityDuty);
        for(ZjTzGeneralContractQualityDuty managementOrgan : zjTzGeneralContractQualityDutyList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getGeneralContractQualityId());
        	managementOrgan.setGenConQualityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // ??????????????????
        PageInfo<ZjTzGeneralContractQualityDuty> p = new PageInfo<>(zjTzGeneralContractQualityDutyList);

        return repEntity.okList(zjTzGeneralContractQualityDutyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzGeneralContractQualityDutyDetails(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        if (zjTzGeneralContractQualityDuty == null) {
            zjTzGeneralContractQualityDuty = new ZjTzGeneralContractQualityDuty();
        }
        // ????????????
        ZjTzGeneralContractQualityDuty dbZjTzGeneralContractQualityDuty = zjTzGeneralContractQualityDutyMapper.selectByPrimaryKey(zjTzGeneralContractQualityDuty.getGeneralContractQualityId());
        // ????????????
        if (dbZjTzGeneralContractQualityDuty != null) {
            return repEntity.ok(dbZjTzGeneralContractQualityDuty);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzGeneralContractQualityDuty(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzGeneralContractQualityDuty.setGeneralContractQualityId(UuidUtil.generate());
        zjTzGeneralContractQualityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzGeneralContractQualityDutyMapper.insert(zjTzGeneralContractQualityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzGeneralContractQualityDuty);
        }
    }

    @Override
    public ResponseEntity updateZjTzGeneralContractQualityDuty(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzGeneralContractQualityDuty dbzjTzGeneralContractQualityDuty = zjTzGeneralContractQualityDutyMapper.selectByPrimaryKey(zjTzGeneralContractQualityDuty.getGeneralContractQualityId());
        if (dbzjTzGeneralContractQualityDuty != null && StrUtil.isNotEmpty(dbzjTzGeneralContractQualityDuty.getGeneralContractQualityId())) {
           // ??????ID
           dbzjTzGeneralContractQualityDuty.setProjectId(zjTzGeneralContractQualityDuty.getProjectId());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setProjectName(zjTzGeneralContractQualityDuty.getProjectName());
           // ?????????ID
           dbzjTzGeneralContractQualityDuty.setSubprojectsId(zjTzGeneralContractQualityDuty.getSubprojectsId());
           // ???????????????
           dbzjTzGeneralContractQualityDuty.setSubprojectsName(zjTzGeneralContractQualityDuty.getSubprojectsName());
           // ??????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityTitle(zjTzGeneralContractQualityDuty.getGeneralContractQualityTitle());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityContent(zjTzGeneralContractQualityDuty.getGeneralContractQualityContent());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityDate(zjTzGeneralContractQualityDuty.getGeneralContractQualityDate());
           // ????????????
           dbzjTzGeneralContractQualityDuty.setGeneralContractQualityUser(zjTzGeneralContractQualityDuty.getGeneralContractQualityUser());
           // ??????
           dbzjTzGeneralContractQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzGeneralContractQualityDutyMapper.updateByPrimaryKey(dbzjTzGeneralContractQualityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzGeneralContractQualityDuty);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzGeneralContractQualityDuty(List<ZjTzGeneralContractQualityDuty> zjTzGeneralContractQualityDutyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzGeneralContractQualityDutyList != null && zjTzGeneralContractQualityDutyList.size() > 0) {
           ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty = new ZjTzGeneralContractQualityDuty();
           zjTzGeneralContractQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzGeneralContractQualityDutyMapper.batchDeleteUpdateZjTzGeneralContractQualityDuty(zjTzGeneralContractQualityDutyList, zjTzGeneralContractQualityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzGeneralContractQualityDuty managementOrgan : zjTzGeneralContractQualityDutyList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementOrgan.getGeneralContractQualityId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }            	
            return repEntity.ok("sys.data.delete",zjTzGeneralContractQualityDutyList);
        }
    }

}
