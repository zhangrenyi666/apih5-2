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
import com.apih5.mybatis.dao.ZjTzSuperiorUnitSecurityDutyMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitSecurityDuty;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSuperiorUnitSecurityDutyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSuperiorUnitSecurityDutyService")
public class ZjTzSuperiorUnitSecurityDutyServiceImpl implements ZjTzSuperiorUnitSecurityDutyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSuperiorUnitSecurityDutyMapper zjTzSuperiorUnitSecurityDutyMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSuperiorUnitSecurityDutyAddFile(
			ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSuperiorUnitSecurityDuty.getSubprojectsId());        
        if(info !=null) {
        	zjTzSuperiorUnitSecurityDuty.setSubprojectsName(info.getSubprojectName());
        }          
        zjTzSuperiorUnitSecurityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzSuperiorUnitSecurityDutyMapper.insert(zjTzSuperiorUnitSecurityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//新增附件
        	if(zjTzSuperiorUnitSecurityDuty.getUnitSecurityFileList().size()>0 && zjTzSuperiorUnitSecurityDuty.getUnitSecurityFileList() != null) {
        		for(ZjTzFile file : zjTzSuperiorUnitSecurityDuty.getUnitSecurityFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.sava", zjTzSuperiorUnitSecurityDuty);
        }
	}

	@Override
	public ResponseEntity updateZjTzSuperiorUnitSecurityDutyAddFile(
			ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
	      HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        ZjTzSuperiorUnitSecurityDuty dbzjTzSuperiorUnitSecurityDuty = zjTzSuperiorUnitSecurityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId());
	        if (dbzjTzSuperiorUnitSecurityDuty != null && StrUtil.isNotEmpty(dbzjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId())) {
	           // 项目ID
	           dbzjTzSuperiorUnitSecurityDuty.setProjectId(zjTzSuperiorUnitSecurityDuty.getProjectId());
	           // 项目名称
	           dbzjTzSuperiorUnitSecurityDuty.setProjectName(zjTzSuperiorUnitSecurityDuty.getProjectName());
	           // 子项目ID           
	           if(!StrUtil.equals(zjTzSuperiorUnitSecurityDuty.getSubprojectsId(), dbzjTzSuperiorUnitSecurityDuty.getSubprojectsId())) {
	        	   dbzjTzSuperiorUnitSecurityDuty.setSubprojectsId(zjTzSuperiorUnitSecurityDuty.getSubprojectsId());
	               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSuperiorUnitSecurityDuty.getSubprojectsId());        
	               if(info !=null) {
	            	   dbzjTzSuperiorUnitSecurityDuty.setSubprojectsName(info.getSubprojectName());
	               }
	           }
	           // 标题
	           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityTitle(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityTitle());
	           // 主要内容
	           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityContent(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityContent());
	           // 登记日期
	           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityDate(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityDate());
	           // 登记用户
	           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityUser(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityUser());
	           // 共通
	           dbzjTzSuperiorUnitSecurityDuty.setModifyUserInfo(userKey, realName);
	           flag = zjTzSuperiorUnitSecurityDutyMapper.updateByPrimaryKey(dbzjTzSuperiorUnitSecurityDuty);
	        }
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	        	ZjTzFile zjTzFile = new ZjTzFile();
	        	zjTzFile.setOtherId(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId());
	        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
	        	if(zjTzFileList.size()>0) {
	        		zjTzFile.setModifyUserInfo(userKey, realName);
	        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
	        	}
	        	//新增附件
	        	if(zjTzSuperiorUnitSecurityDuty.getUnitSecurityFileList().size()>0 && zjTzSuperiorUnitSecurityDuty.getUnitSecurityFileList() != null) {
	        		for(ZjTzFile file : zjTzSuperiorUnitSecurityDuty.getUnitSecurityFileList()) {
	        			file.setUid(UuidUtil.generate());
	        			file.setOtherId(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId());
	        			file.setCreateUserInfo(userKey, realName);
	                    zjTzFileMapper.insert(file);
	        		}
	        	}    	        	
	            return repEntity.ok("sys.data.update",zjTzSuperiorUnitSecurityDuty);
	        }
	}
	
    @Override
    public ResponseEntity getZjTzSuperiorUnitSecurityDutyListByCondition(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (zjTzSuperiorUnitSecurityDuty == null) {
            zjTzSuperiorUnitSecurityDuty = new ZjTzSuperiorUnitSecurityDuty();
        }
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzSuperiorUnitSecurityDuty.getProjectId(), true)) {
//        	zjTzSuperiorUnitSecurityDuty.setProjectId("");
//        	zjTzSuperiorUnitSecurityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSuperiorUnitSecurityDuty.getProjectId(), true)) {
                zjTzSuperiorUnitSecurityDuty.setProjectId("");
                zjTzSuperiorUnitSecurityDuty.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSuperiorUnitSecurityDuty.getProjectId(), true)) {
                zjTzSuperiorUnitSecurityDuty.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzSuperiorUnitSecurityDuty.getPage(),zjTzSuperiorUnitSecurityDuty.getLimit());
        // 获取数据
        List<ZjTzSuperiorUnitSecurityDuty> zjTzSuperiorUnitSecurityDutyList = zjTzSuperiorUnitSecurityDutyMapper.selectByZjTzSuperiorUnitSecurityDutyList(zjTzSuperiorUnitSecurityDuty);
        for(ZjTzSuperiorUnitSecurityDuty managementOrgan : zjTzSuperiorUnitSecurityDutyList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementOrgan.getSuperiorUnitSecurityId());
        	managementOrgan.setUnitSecurityFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // 得到分页信息
        PageInfo<ZjTzSuperiorUnitSecurityDuty> p = new PageInfo<>(zjTzSuperiorUnitSecurityDutyList);

        return repEntity.okList(zjTzSuperiorUnitSecurityDutyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSuperiorUnitSecurityDutyDetails(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        if (zjTzSuperiorUnitSecurityDuty == null) {
            zjTzSuperiorUnitSecurityDuty = new ZjTzSuperiorUnitSecurityDuty();
        }
        // 获取数据
        ZjTzSuperiorUnitSecurityDuty dbZjTzSuperiorUnitSecurityDuty = zjTzSuperiorUnitSecurityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId());
        // 数据存在
        if (dbZjTzSuperiorUnitSecurityDuty != null) {
            return repEntity.ok(dbZjTzSuperiorUnitSecurityDuty);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSuperiorUnitSecurityDuty(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityId(UuidUtil.generate());
        zjTzSuperiorUnitSecurityDuty.setCreateUserInfo(userKey, realName);
        int flag = zjTzSuperiorUnitSecurityDutyMapper.insert(zjTzSuperiorUnitSecurityDuty);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSuperiorUnitSecurityDuty);
        }
    }

    @Override
    public ResponseEntity updateZjTzSuperiorUnitSecurityDuty(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSuperiorUnitSecurityDuty dbzjTzSuperiorUnitSecurityDuty = zjTzSuperiorUnitSecurityDutyMapper.selectByPrimaryKey(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId());
        if (dbzjTzSuperiorUnitSecurityDuty != null && StrUtil.isNotEmpty(dbzjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityId())) {
           // 项目ID
           dbzjTzSuperiorUnitSecurityDuty.setProjectId(zjTzSuperiorUnitSecurityDuty.getProjectId());
           // 项目名称
           dbzjTzSuperiorUnitSecurityDuty.setProjectName(zjTzSuperiorUnitSecurityDuty.getProjectName());
           // 子项目ID
           dbzjTzSuperiorUnitSecurityDuty.setSubprojectsId(zjTzSuperiorUnitSecurityDuty.getSubprojectsId());
           // 子项目名称
           dbzjTzSuperiorUnitSecurityDuty.setSubprojectsName(zjTzSuperiorUnitSecurityDuty.getSubprojectsName());
           // 标题
           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityTitle(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityTitle());
           // 主要内容
           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityContent(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityContent());
           // 登记日期
           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityDate(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityDate());
           // 登记用户
           dbzjTzSuperiorUnitSecurityDuty.setSuperiorUnitSecurityUser(zjTzSuperiorUnitSecurityDuty.getSuperiorUnitSecurityUser());
           // 共通
           dbzjTzSuperiorUnitSecurityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitSecurityDutyMapper.updateByPrimaryKey(dbzjTzSuperiorUnitSecurityDuty);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSuperiorUnitSecurityDuty);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSuperiorUnitSecurityDuty(List<ZjTzSuperiorUnitSecurityDuty> zjTzSuperiorUnitSecurityDutyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSuperiorUnitSecurityDutyList != null && zjTzSuperiorUnitSecurityDutyList.size() > 0) {
           ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty = new ZjTzSuperiorUnitSecurityDuty();
           zjTzSuperiorUnitSecurityDuty.setModifyUserInfo(userKey, realName);
           flag = zjTzSuperiorUnitSecurityDutyMapper.batchDeleteUpdateZjTzSuperiorUnitSecurityDuty(zjTzSuperiorUnitSecurityDutyList, zjTzSuperiorUnitSecurityDuty);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
            for(ZjTzSuperiorUnitSecurityDuty managementOrgan : zjTzSuperiorUnitSecurityDutyList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(managementOrgan.getSuperiorUnitSecurityId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            } 
            return repEntity.ok("sys.data.delete",zjTzSuperiorUnitSecurityDutyList);
        }
    }

}
