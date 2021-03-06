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
import com.apih5.mybatis.dao.ZjTzOtherDutyMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzOtherDuty;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.service.ZjTzOtherDutyService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzOtherDutyService")
public class ZjTzOtherDutyServiceImpl implements ZjTzOtherDutyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzOtherDutyMapper zjTzOtherDutyMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzOtherDutyAddFile(ZjTzOtherDuty zjTzOtherDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzOtherDuty.setOtherDutyId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzOtherDuty.getSubprojectsId());        
        if(info !=null) {
        	zjTzOtherDuty.setSubprojectsName(info.getSubprojectName());
        }          
        zjTzOtherDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzOtherDutyMapper.insert(zjTzOtherDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//????????????
        	if(zjTzOtherDuty.getOtherDutyFileList().size()>0 && zjTzOtherDuty.getOtherDutyFileList() != null) {
        		for(ZjTzFile file : zjTzOtherDuty.getOtherDutyFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzOtherDuty.getOtherDutyId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.sava", zjTzOtherDuty);
        }
	}

	@Override
	public ResponseEntity updateZjTzOtherDutyAddFile(ZjTzOtherDuty zjTzOtherDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzOtherDuty dbzjTzOtherDuty = zjTzOtherDutyMapper.selectByPrimaryKey(zjTzOtherDuty.getOtherDutyId());
        if (dbzjTzOtherDuty != null && StrUtil.isNotEmpty(dbzjTzOtherDuty.getOtherDutyId())) {
           // ??????ID
           dbzjTzOtherDuty.setProjectId(zjTzOtherDuty.getProjectId());
           // ????????????
           dbzjTzOtherDuty.setProjectName(zjTzOtherDuty.getProjectName());
           // ?????????ID           
           if(!StrUtil.equals(zjTzOtherDuty.getSubprojectsId(), dbzjTzOtherDuty.getSubprojectsId())) {
        	   dbzjTzOtherDuty.setSubprojectsId(zjTzOtherDuty.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzOtherDuty.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzOtherDuty.setSubprojectsName(info.getSubprojectName());
               }
           }
           // ??????
           dbzjTzOtherDuty.setOtherDutyTitle(zjTzOtherDuty.getOtherDutyTitle());
           // ????????????
           dbzjTzOtherDuty.setOtherDutyContent(zjTzOtherDuty.getOtherDutyContent());
           // ????????????
           dbzjTzOtherDuty.setOtherDutyDate(zjTzOtherDuty.getOtherDutyDate());
           // ????????????
           dbzjTzOtherDuty.setOtherDutyUser(zjTzOtherDuty.getOtherDutyUser());
           // ??????
           dbzjTzOtherDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherDutyMapper.updateByPrimaryKey(dbzjTzOtherDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzOtherDuty.getOtherDutyId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//????????????
        	if(zjTzOtherDuty.getOtherDutyFileList().size()>0 && zjTzOtherDuty.getOtherDutyFileList() != null) {
        		for(ZjTzFile file : zjTzOtherDuty.getOtherDutyFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzOtherDuty.getOtherDutyId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}         	
            return repEntity.ok("sys.data.update",zjTzOtherDuty);
        }
	}

    @Override
    public ResponseEntity getZjTzOtherDutyListByCondition(ZjTzOtherDuty zjTzOtherDuty) {
        if (zjTzOtherDuty == null) {
            zjTzOtherDuty = new ZjTzOtherDuty();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// ???????????????????????????????????????sql?????????
//        if(StrUtil.equals("all", zjTzOtherDuty.getProjectId(), true)) {
//        	zjTzOtherDuty.setProjectId("");
//        	zjTzOtherDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }  
        
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzOtherDuty.getProjectId(), true)) {
                zjTzOtherDuty.setProjectId("");
                zjTzOtherDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzOtherDuty.getProjectId(), true)) {
                zjTzOtherDuty.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzOtherDuty.getPage(),zjTzOtherDuty.getLimit());
        // ????????????
        List<ZjTzOtherDuty> zjTzOtherDutyList = zjTzOtherDutyMapper.selectByZjTzOtherDutyList(zjTzOtherDuty);
        for(ZjTzOtherDuty otherDuty : zjTzOtherDutyList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(otherDuty.getOtherDutyId());
        	otherDuty.setOtherDutyFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }           
        // ??????????????????
        PageInfo<ZjTzOtherDuty> p = new PageInfo<>(zjTzOtherDutyList);

        return repEntity.okList(zjTzOtherDutyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzOtherDutyDetails(ZjTzOtherDuty zjTzOtherDuty) {
        if (zjTzOtherDuty == null) {
            zjTzOtherDuty = new ZjTzOtherDuty();
        }
        // ????????????
        ZjTzOtherDuty dbZjTzOtherDuty = zjTzOtherDutyMapper.selectByPrimaryKey(zjTzOtherDuty.getOtherDutyId());
        // ????????????
        if (dbZjTzOtherDuty != null) {
            return repEntity.ok(dbZjTzOtherDuty);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzOtherDuty(ZjTzOtherDuty zjTzOtherDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzOtherDuty.setOtherDutyId(UuidUtil.generate());
        zjTzOtherDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzOtherDutyMapper.insert(zjTzOtherDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzOtherDuty);
        }
    }

    @Override
    public ResponseEntity updateZjTzOtherDuty(ZjTzOtherDuty zjTzOtherDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzOtherDuty dbzjTzOtherDuty = zjTzOtherDutyMapper.selectByPrimaryKey(zjTzOtherDuty.getOtherDutyId());
        if (dbzjTzOtherDuty != null && StrUtil.isNotEmpty(dbzjTzOtherDuty.getOtherDutyId())) {
           // ??????ID
           dbzjTzOtherDuty.setProjectId(zjTzOtherDuty.getProjectId());
           // ????????????
           dbzjTzOtherDuty.setProjectName(zjTzOtherDuty.getProjectName());
           // ?????????ID
           dbzjTzOtherDuty.setSubprojectsId(zjTzOtherDuty.getSubprojectsId());
           // ???????????????
           dbzjTzOtherDuty.setSubprojectsName(zjTzOtherDuty.getSubprojectsName());
           // ??????
           dbzjTzOtherDuty.setOtherDutyTitle(zjTzOtherDuty.getOtherDutyTitle());
           // ????????????
           dbzjTzOtherDuty.setOtherDutyContent(zjTzOtherDuty.getOtherDutyContent());
           // ????????????
           dbzjTzOtherDuty.setOtherDutyDate(zjTzOtherDuty.getOtherDutyDate());
           // ????????????
           dbzjTzOtherDuty.setOtherDutyUser(zjTzOtherDuty.getOtherDutyUser());
           // ??????
           dbzjTzOtherDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherDutyMapper.updateByPrimaryKey(dbzjTzOtherDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzOtherDuty);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzOtherDuty(List<ZjTzOtherDuty> zjTzOtherDutyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzOtherDutyList != null && zjTzOtherDutyList.size() > 0) {
           ZjTzOtherDuty zjTzOtherDuty = new ZjTzOtherDuty();
           zjTzOtherDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherDutyMapper.batchDeleteUpdateZjTzOtherDuty(zjTzOtherDutyList, zjTzOtherDuty);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//??????????????????
            for(ZjTzOtherDuty otherDuty : zjTzOtherDutyList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(otherDuty.getOtherDutyId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }           	
            return repEntity.ok("sys.data.delete",zjTzOtherDutyList);
        }
    }
}
