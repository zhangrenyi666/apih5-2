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
import com.apih5.mybatis.dao.ZjTzSafetyQualityCheckBulletinMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSafetyQualityCheckBulletin;
import com.apih5.mybatis.pojo.ZjTzSupervisorMonthlyMeeting;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSafetyQualityCheckBulletinService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSafetyQualityCheckBulletinService")
public class ZjTzSafetyQualityCheckBulletinServiceImpl implements ZjTzSafetyQualityCheckBulletinService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSafetyQualityCheckBulletinMapper zjTzSafetyQualityCheckBulletinMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSafetyQualityCheckBulletinAddFile(
			ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSafetyQualityCheckBulletin.setCheckBulletinId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getSubprojectsId());        
        if(info !=null) {
        	zjTzSafetyQualityCheckBulletin.setSubprojectsName(info.getSubprojectName());
        }         
        zjTzSafetyQualityCheckBulletin.setCreateUserInfo(userKey, realName);
        int flag = zjTzSafetyQualityCheckBulletinMapper.insert(zjTzSafetyQualityCheckBulletin);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzSafetyQualityCheckBulletin.getCheckBulletinFileList().size()>0 && zjTzSafetyQualityCheckBulletin.getCheckBulletinFileList() != null) {
        		for(ZjTzFile file : zjTzSafetyQualityCheckBulletin.getCheckBulletinFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.sava", zjTzSafetyQualityCheckBulletin);
        }
	}

	@Override
	public ResponseEntity updateZjTzSafetyQualityCheckBulletinAddFile(
			ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSafetyQualityCheckBulletin dbzjTzSafetyQualityCheckBulletin = zjTzSafetyQualityCheckBulletinMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        if (dbzjTzSafetyQualityCheckBulletin != null && StrUtil.isNotEmpty(dbzjTzSafetyQualityCheckBulletin.getCheckBulletinId())) {
           // ??????ID
           dbzjTzSafetyQualityCheckBulletin.setProjectId(zjTzSafetyQualityCheckBulletin.getProjectId());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setProjectName(zjTzSafetyQualityCheckBulletin.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzSafetyQualityCheckBulletin.getSubprojectsId(), dbzjTzSafetyQualityCheckBulletin.getSubprojectsId())) {
        	   dbzjTzSafetyQualityCheckBulletin.setSubprojectsId(zjTzSafetyQualityCheckBulletin.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSafetyQualityCheckBulletin.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinTitle(zjTzSafetyQualityCheckBulletin.getCheckBulletinTitle());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinContent(zjTzSafetyQualityCheckBulletin.getCheckBulletinContent());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckUnit(zjTzSafetyQualityCheckBulletin.getCheckUnit());
           // ??????????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckRectificationImplementation(zjTzSafetyQualityCheckBulletin.getCheckRectificationImplementation());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckDate(zjTzSafetyQualityCheckBulletin.getCheckDate());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinDate(zjTzSafetyQualityCheckBulletin.getCheckBulletinDate());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinUser(zjTzSafetyQualityCheckBulletin.getCheckBulletinUser());
           // ??????
           dbzjTzSafetyQualityCheckBulletin.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyQualityCheckBulletinMapper.updateByPrimaryKey(dbzjTzSafetyQualityCheckBulletin);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzSafetyQualityCheckBulletin.getCheckBulletinFileList().size()>0 && zjTzSafetyQualityCheckBulletin.getCheckBulletinFileList() != null) {
        		for(ZjTzFile file : zjTzSafetyQualityCheckBulletin.getCheckBulletinFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.update",zjTzSafetyQualityCheckBulletin);
        }
	}
	
    @Override
    public ResponseEntity getZjTzSafetyQualityCheckBulletinListByCondition(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        if (zjTzSafetyQualityCheckBulletin == null) {
            zjTzSafetyQualityCheckBulletin = new ZjTzSafetyQualityCheckBulletin();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzSafetyQualityCheckBulletin.getProjectId(), true)) {
//        	zjTzSafetyQualityCheckBulletin.setProjectId("");
//        	zjTzSafetyQualityCheckBulletin.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }     
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzSafetyQualityCheckBulletin.getProjectId(), true)) {
                zjTzSafetyQualityCheckBulletin.setProjectId("");
                zjTzSafetyQualityCheckBulletin.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzSafetyQualityCheckBulletin.getProjectId(), true)) {
                zjTzSafetyQualityCheckBulletin.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzSafetyQualityCheckBulletin.getPage(),zjTzSafetyQualityCheckBulletin.getLimit());
        // ????????????
        List<ZjTzSafetyQualityCheckBulletin> zjTzSafetyQualityCheckBulletinList = zjTzSafetyQualityCheckBulletinMapper.selectByZjTzSafetyQualityCheckBulletinList(zjTzSafetyQualityCheckBulletin);
        for(ZjTzSafetyQualityCheckBulletin checkBulletin : zjTzSafetyQualityCheckBulletinList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(checkBulletin.getCheckBulletinId());
        	checkBulletin.setCheckBulletinFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // ??????????????????
        PageInfo<ZjTzSafetyQualityCheckBulletin> p = new PageInfo<>(zjTzSafetyQualityCheckBulletinList);

        return repEntity.okList(zjTzSafetyQualityCheckBulletinList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSafetyQualityCheckBulletinDetails(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        if (zjTzSafetyQualityCheckBulletin == null) {
            zjTzSafetyQualityCheckBulletin = new ZjTzSafetyQualityCheckBulletin();
        }
        // ????????????
        ZjTzSafetyQualityCheckBulletin dbZjTzSafetyQualityCheckBulletin = zjTzSafetyQualityCheckBulletinMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        // ????????????
        if (dbZjTzSafetyQualityCheckBulletin != null) {
            return repEntity.ok(dbZjTzSafetyQualityCheckBulletin);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzSafetyQualityCheckBulletin(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSafetyQualityCheckBulletin.setCheckBulletinId(UuidUtil.generate());
        zjTzSafetyQualityCheckBulletin.setCreateUserInfo(userKey, realName);
        int flag = zjTzSafetyQualityCheckBulletinMapper.insert(zjTzSafetyQualityCheckBulletin);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSafetyQualityCheckBulletin);
        }
    }

    @Override
    public ResponseEntity updateZjTzSafetyQualityCheckBulletin(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSafetyQualityCheckBulletin dbzjTzSafetyQualityCheckBulletin = zjTzSafetyQualityCheckBulletinMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        if (dbzjTzSafetyQualityCheckBulletin != null && StrUtil.isNotEmpty(dbzjTzSafetyQualityCheckBulletin.getCheckBulletinId())) {
           // ??????ID
           dbzjTzSafetyQualityCheckBulletin.setProjectId(zjTzSafetyQualityCheckBulletin.getProjectId());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setProjectName(zjTzSafetyQualityCheckBulletin.getProjectName());
           // ?????????ID
           dbzjTzSafetyQualityCheckBulletin.setSubprojectsId(zjTzSafetyQualityCheckBulletin.getSubprojectsId());
           // ???????????????
           dbzjTzSafetyQualityCheckBulletin.setSubprojectsName(zjTzSafetyQualityCheckBulletin.getSubprojectsName());
           // ??????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinTitle(zjTzSafetyQualityCheckBulletin.getCheckBulletinTitle());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinContent(zjTzSafetyQualityCheckBulletin.getCheckBulletinContent());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckUnit(zjTzSafetyQualityCheckBulletin.getCheckUnit());
           // ??????????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckRectificationImplementation(zjTzSafetyQualityCheckBulletin.getCheckRectificationImplementation());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckDate(zjTzSafetyQualityCheckBulletin.getCheckDate());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinDate(zjTzSafetyQualityCheckBulletin.getCheckBulletinDate());
           // ????????????
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinUser(zjTzSafetyQualityCheckBulletin.getCheckBulletinUser());
           // ??????
           dbzjTzSafetyQualityCheckBulletin.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyQualityCheckBulletinMapper.updateByPrimaryKey(dbzjTzSafetyQualityCheckBulletin);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSafetyQualityCheckBulletin);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSafetyQualityCheckBulletin(List<ZjTzSafetyQualityCheckBulletin> zjTzSafetyQualityCheckBulletinList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSafetyQualityCheckBulletinList != null && zjTzSafetyQualityCheckBulletinList.size() > 0) {
           ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin = new ZjTzSafetyQualityCheckBulletin();
           zjTzSafetyQualityCheckBulletin.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyQualityCheckBulletinMapper.batchDeleteUpdateZjTzSafetyQualityCheckBulletin(zjTzSafetyQualityCheckBulletinList, zjTzSafetyQualityCheckBulletin);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzSafetyQualityCheckBulletin checkBulletin : zjTzSafetyQualityCheckBulletinList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(checkBulletin.getCheckBulletinId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            } 
            return repEntity.ok("sys.data.delete",zjTzSafetyQualityCheckBulletinList);
        }
    }

}
