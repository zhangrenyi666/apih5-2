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
import com.apih5.mybatis.dao.ZjTzGeneralContractSecurityDutyMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzGeneralContractSecurityDuty;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitSecurityDuty;
import com.apih5.service.ZjTzGeneralContractSecurityDutyService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzGeneralContractSecurityDutyService")
public class ZjTzGeneralContractSecurityDutyServiceImpl implements ZjTzGeneralContractSecurityDutyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzGeneralContractSecurityDutyMapper zjTzGeneralContractSecurityDutyMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzGeneralContractSecurityDutyAddFile(
			ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzGeneralContractSecurityDuty.setGeneralContractSecurityId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzGeneralContractSecurityDuty.getSubprojectsId());        
        if(info !=null) {
        	zjTzGeneralContractSecurityDuty.setSubprojectsName(info.getSubprojectName());
        }           
        zjTzGeneralContractSecurityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzGeneralContractSecurityDutyMapper.insert(zjTzGeneralContractSecurityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzGeneralContractSecurityDuty.getGenConSecurityFileList().size()>0 && zjTzGeneralContractSecurityDuty.getGenConSecurityFileList() != null) {
        		for(ZjTzFile file : zjTzGeneralContractSecurityDuty.getGenConSecurityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}         	
            return repEntity.ok("sys.data.sava", zjTzGeneralContractSecurityDuty);
        }
	}

	@Override
	public ResponseEntity updateZjTzGeneralContractSecurityDutyAddFile(
			ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzGeneralContractSecurityDuty dbzjTzGeneralContractSecurityDuty = zjTzGeneralContractSecurityDutyMapper.selectByPrimaryKey(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityId());
        if (dbzjTzGeneralContractSecurityDuty != null && StrUtil.isNotEmpty(dbzjTzGeneralContractSecurityDuty.getGeneralContractSecurityId())) {
           // ??????ID
           dbzjTzGeneralContractSecurityDuty.setProjectId(zjTzGeneralContractSecurityDuty.getProjectId());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setProjectName(zjTzGeneralContractSecurityDuty.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzGeneralContractSecurityDuty.getSubprojectsId(), dbzjTzGeneralContractSecurityDuty.getSubprojectsId())) {
        	   dbzjTzGeneralContractSecurityDuty.setSubprojectsId(zjTzGeneralContractSecurityDuty.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzGeneralContractSecurityDuty.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzGeneralContractSecurityDuty.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityTitle(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityTitle());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityContent(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityContent());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityDate(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityDate());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityUser(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityUser());
           // ??????
           dbzjTzGeneralContractSecurityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzGeneralContractSecurityDutyMapper.updateByPrimaryKey(dbzjTzGeneralContractSecurityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzGeneralContractSecurityDuty.getGenConSecurityFileList().size()>0 && zjTzGeneralContractSecurityDuty.getGenConSecurityFileList() != null) {
        		for(ZjTzFile file : zjTzGeneralContractSecurityDuty.getGenConSecurityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}         	
            return repEntity.ok("sys.data.update",zjTzGeneralContractSecurityDuty);
        }
	}

    @Override
    public ResponseEntity getZjTzGeneralContractSecurityDutyListByCondition(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        if (zjTzGeneralContractSecurityDuty == null) {
            zjTzGeneralContractSecurityDuty = new ZjTzGeneralContractSecurityDuty();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzGeneralContractSecurityDuty.getProjectId(), true)) {
//        	zjTzGeneralContractSecurityDuty.setProjectId("");
//        	zjTzGeneralContractSecurityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }      
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzGeneralContractSecurityDuty.getProjectId(), true)) {
                zjTzGeneralContractSecurityDuty.setProjectId("");
                zjTzGeneralContractSecurityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzGeneralContractSecurityDuty.getProjectId(), true)) {
                zjTzGeneralContractSecurityDuty.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzGeneralContractSecurityDuty.getPage(),zjTzGeneralContractSecurityDuty.getLimit());
        // ????????????
        List<ZjTzGeneralContractSecurityDuty> zjTzGeneralContractSecurityDutyList = zjTzGeneralContractSecurityDutyMapper.selectByZjTzGeneralContractSecurityDutyList(zjTzGeneralContractSecurityDuty);
        for(ZjTzGeneralContractSecurityDuty managementOrgan : zjTzGeneralContractSecurityDutyList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getGeneralContractSecurityId());
        	managementOrgan.setGenConSecurityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }         
        // ??????????????????
        PageInfo<ZjTzGeneralContractSecurityDuty> p = new PageInfo<>(zjTzGeneralContractSecurityDutyList);

        return repEntity.okList(zjTzGeneralContractSecurityDutyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzGeneralContractSecurityDutyDetails(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        if (zjTzGeneralContractSecurityDuty == null) {
            zjTzGeneralContractSecurityDuty = new ZjTzGeneralContractSecurityDuty();
        }
        // ????????????
        ZjTzGeneralContractSecurityDuty dbZjTzGeneralContractSecurityDuty = zjTzGeneralContractSecurityDutyMapper.selectByPrimaryKey(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityId());
        // ????????????
        if (dbZjTzGeneralContractSecurityDuty != null) {
            return repEntity.ok(dbZjTzGeneralContractSecurityDuty);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzGeneralContractSecurityDuty(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzGeneralContractSecurityDuty.setGeneralContractSecurityId(UuidUtil.generate());
        zjTzGeneralContractSecurityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzGeneralContractSecurityDutyMapper.insert(zjTzGeneralContractSecurityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzGeneralContractSecurityDuty);
        }
    }

    @Override
    public ResponseEntity updateZjTzGeneralContractSecurityDuty(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzGeneralContractSecurityDuty dbzjTzGeneralContractSecurityDuty = zjTzGeneralContractSecurityDutyMapper.selectByPrimaryKey(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityId());
        if (dbzjTzGeneralContractSecurityDuty != null && StrUtil.isNotEmpty(dbzjTzGeneralContractSecurityDuty.getGeneralContractSecurityId())) {
           // ??????ID
           dbzjTzGeneralContractSecurityDuty.setProjectId(zjTzGeneralContractSecurityDuty.getProjectId());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setProjectName(zjTzGeneralContractSecurityDuty.getProjectName());
           // ?????????ID
           dbzjTzGeneralContractSecurityDuty.setSubprojectsId(zjTzGeneralContractSecurityDuty.getSubprojectsId());
           // ???????????????
           dbzjTzGeneralContractSecurityDuty.setSubprojectsName(zjTzGeneralContractSecurityDuty.getSubprojectsName());
           // ??????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityTitle(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityTitle());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityContent(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityContent());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityDate(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityDate());
           // ????????????
           dbzjTzGeneralContractSecurityDuty.setGeneralContractSecurityUser(zjTzGeneralContractSecurityDuty.getGeneralContractSecurityUser());
           // ??????
           dbzjTzGeneralContractSecurityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzGeneralContractSecurityDutyMapper.updateByPrimaryKey(dbzjTzGeneralContractSecurityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzGeneralContractSecurityDuty);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzGeneralContractSecurityDuty(List<ZjTzGeneralContractSecurityDuty> zjTzGeneralContractSecurityDutyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzGeneralContractSecurityDutyList != null && zjTzGeneralContractSecurityDutyList.size() > 0) {
           ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty = new ZjTzGeneralContractSecurityDuty();
           zjTzGeneralContractSecurityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzGeneralContractSecurityDutyMapper.batchDeleteUpdateZjTzGeneralContractSecurityDuty(zjTzGeneralContractSecurityDutyList, zjTzGeneralContractSecurityDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzGeneralContractSecurityDuty managementOrgan : zjTzGeneralContractSecurityDutyList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementOrgan.getGeneralContractSecurityId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }         	
            return repEntity.ok("sys.data.delete",zjTzGeneralContractSecurityDutyList);
        }
    }
}
