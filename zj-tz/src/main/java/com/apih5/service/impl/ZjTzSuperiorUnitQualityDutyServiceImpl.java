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
        	//新增附件
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
           // 项目ID
           dbzjTzSuperiorUnitQualityDuty.setProjectId(zjTzSuperiorUnitQualityDuty.getProjectId());
           // 项目名称
           dbzjTzSuperiorUnitQualityDuty.setProjectName(zjTzSuperiorUnitQualityDuty.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzSuperiorUnitQualityDuty.getSubprojectsId(), dbzjTzSuperiorUnitQualityDuty.getSubprojectsId())) {
        	   dbzjTzSuperiorUnitQualityDuty.setSubprojectsId(zjTzSuperiorUnitQualityDuty.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSuperiorUnitQualityDuty.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityTitle(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityTitle());
           // 主要内容
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityContent(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityContent());
           // 登记日期
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityDate(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityDate());
           // 登记用户
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityUser(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityUser());
           // 共通
           dbzjTzSuperiorUnitQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitQualityDutyMapper.updateByPrimaryKey(dbzjTzSuperiorUnitQualityDuty);
        }
        // 失败
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
        	//新增附件
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
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzSuperiorUnitQualityDuty.getProjectId(), true)) {
//        	zjTzSuperiorUnitQualityDuty.setProjectId("");
//        	zjTzSuperiorUnitQualityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }    
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSuperiorUnitQualityDuty.getProjectId(), true)) {
                zjTzSuperiorUnitQualityDuty.setProjectId("");
                zjTzSuperiorUnitQualityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSuperiorUnitQualityDuty.getProjectId(), true)) {
                zjTzSuperiorUnitQualityDuty.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzSuperiorUnitQualityDuty.getPage(),zjTzSuperiorUnitQualityDuty.getLimit());
        // 获取数据
        List<ZjTzSuperiorUnitQualityDuty> zjTzSuperiorUnitQualityDutyList = zjTzSuperiorUnitQualityDutyMapper.selectByZjTzSuperiorUnitQualityDutyList(zjTzSuperiorUnitQualityDuty);
        for(ZjTzSuperiorUnitQualityDuty managementOrgan : zjTzSuperiorUnitQualityDutyList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getSuperiorUnitQualityId());
        	managementOrgan.setUnitQualityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // 得到分页信息
        PageInfo<ZjTzSuperiorUnitQualityDuty> p = new PageInfo<>(zjTzSuperiorUnitQualityDutyList);

        return repEntity.okList(zjTzSuperiorUnitQualityDutyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSuperiorUnitQualityDutyDetails(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty) {
        if (zjTzSuperiorUnitQualityDuty == null) {
            zjTzSuperiorUnitQualityDuty = new ZjTzSuperiorUnitQualityDuty();
        }
        // 获取数据
        ZjTzSuperiorUnitQualityDuty dbZjTzSuperiorUnitQualityDuty = zjTzSuperiorUnitQualityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityId());
        // 数据存在
        if (dbZjTzSuperiorUnitQualityDuty != null) {
            return repEntity.ok(dbZjTzSuperiorUnitQualityDuty);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 项目ID
           dbzjTzSuperiorUnitQualityDuty.setProjectId(zjTzSuperiorUnitQualityDuty.getProjectId());
           // 项目名称
           dbzjTzSuperiorUnitQualityDuty.setProjectName(zjTzSuperiorUnitQualityDuty.getProjectName());
           // 子项目ID
           dbzjTzSuperiorUnitQualityDuty.setSubprojectsId(zjTzSuperiorUnitQualityDuty.getSubprojectsId());
           // 子项目名称
           dbzjTzSuperiorUnitQualityDuty.setSubprojectsName(zjTzSuperiorUnitQualityDuty.getSubprojectsName());
           // 标题
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityTitle(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityTitle());
           // 主要内容
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityContent(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityContent());
           // 登记日期
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityDate(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityDate());
           // 登记用户
           dbzjTzSuperiorUnitQualityDuty.setSuperiorUnitQualityUser(zjTzSuperiorUnitQualityDuty.getSuperiorUnitQualityUser());
           // 共通
           dbzjTzSuperiorUnitQualityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitQualityDutyMapper.updateByPrimaryKey(dbzjTzSuperiorUnitQualityDuty);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
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
