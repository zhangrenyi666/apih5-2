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
        	//新增附件
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
           // 项目ID
           dbzjTzOtherManagementSystem.setProjectId(zjTzOtherManagementSystem.getProjectId());
           // 项目名称
           dbzjTzOtherManagementSystem.setProjectName(zjTzOtherManagementSystem.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzOtherManagementSystem.getSubprojectsId(), dbzjTzOtherManagementSystem.getSubprojectsId())) {
        	   dbzjTzOtherManagementSystem.setSubprojectsId(zjTzOtherManagementSystem.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzOtherManagementSystem.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzOtherManagementSystem.setOtherTitle(zjTzOtherManagementSystem.getOtherTitle());
           // 主要内容
           dbzjTzOtherManagementSystem.setOtherContent(zjTzOtherManagementSystem.getOtherContent());
           // 登记日期
           dbzjTzOtherManagementSystem.setOtherRegisterDate(zjTzOtherManagementSystem.getOtherRegisterDate());
           // 登记用户
           dbzjTzOtherManagementSystem.setOtherRegisterUser(zjTzOtherManagementSystem.getOtherRegisterUser());
           // 共通
           dbzjTzOtherManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherManagementSystemMapper.updateByPrimaryKey(dbzjTzOtherManagementSystem);
        }
        // 失败
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
        	//新增附件
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
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzOtherManagementSystem.getProjectId(), true)) {
//        	zjTzOtherManagementSystem.setProjectId("");
//        	zjTzOtherManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }      
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzOtherManagementSystem.getProjectId(), true)) {
                zjTzOtherManagementSystem.setProjectId("");
                zjTzOtherManagementSystem.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzOtherManagementSystem.getProjectId(), true)) {
                zjTzOtherManagementSystem.setProjectId("");
            }
        }
        
        // 分页查询
        PageHelper.startPage(zjTzOtherManagementSystem.getPage(),zjTzOtherManagementSystem.getLimit());
        // 获取数据
        List<ZjTzOtherManagementSystem> zjTzOtherManagementSystemList = zjTzOtherManagementSystemMapper.selectByZjTzOtherManagementSystemList(zjTzOtherManagementSystem);
        for(ZjTzOtherManagementSystem managementSystem : zjTzOtherManagementSystemList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(managementSystem.getOtherId());
        	managementSystem.setOtherFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }        
        // 得到分页信息
        PageInfo<ZjTzOtherManagementSystem> p = new PageInfo<>(zjTzOtherManagementSystemList);

        return repEntity.okList(zjTzOtherManagementSystemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzOtherManagementSystemDetails(ZjTzOtherManagementSystem zjTzOtherManagementSystem) {
        if (zjTzOtherManagementSystem == null) {
            zjTzOtherManagementSystem = new ZjTzOtherManagementSystem();
        }
        // 获取数据
        ZjTzOtherManagementSystem dbZjTzOtherManagementSystem = zjTzOtherManagementSystemMapper.selectByPrimaryKey(zjTzOtherManagementSystem.getOtherId());
        // 数据存在
        if (dbZjTzOtherManagementSystem != null) {
            return repEntity.ok(dbZjTzOtherManagementSystem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 项目ID
           dbzjTzOtherManagementSystem.setProjectId(zjTzOtherManagementSystem.getProjectId());
           // 项目名称
           dbzjTzOtherManagementSystem.setProjectName(zjTzOtherManagementSystem.getProjectName());
           // 子项目ID
           dbzjTzOtherManagementSystem.setSubprojectsId(zjTzOtherManagementSystem.getSubprojectsId());
           // 子项目名称
           dbzjTzOtherManagementSystem.setSubprojectsName(zjTzOtherManagementSystem.getSubprojectsName());
           // 标题
           dbzjTzOtherManagementSystem.setOtherTitle(zjTzOtherManagementSystem.getOtherTitle());
           // 主要内容
           dbzjTzOtherManagementSystem.setOtherContent(zjTzOtherManagementSystem.getOtherContent());
           // 登记日期
           dbzjTzOtherManagementSystem.setOtherRegisterDate(zjTzOtherManagementSystem.getOtherRegisterDate());
           // 登记用户
           dbzjTzOtherManagementSystem.setOtherRegisterUser(zjTzOtherManagementSystem.getOtherRegisterUser());
           // 共通
           dbzjTzOtherManagementSystem.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherManagementSystemMapper.updateByPrimaryKey(dbzjTzOtherManagementSystem);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
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
