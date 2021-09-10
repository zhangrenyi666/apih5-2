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
        	//新增附件
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
           // 项目ID
           dbzjTzSafetyQualityCheckBulletin.setProjectId(zjTzSafetyQualityCheckBulletin.getProjectId());
           // 项目名称
           dbzjTzSafetyQualityCheckBulletin.setProjectName(zjTzSafetyQualityCheckBulletin.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzSafetyQualityCheckBulletin.getSubprojectsId(), dbzjTzSafetyQualityCheckBulletin.getSubprojectsId())) {
        	   dbzjTzSafetyQualityCheckBulletin.setSubprojectsId(zjTzSafetyQualityCheckBulletin.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSafetyQualityCheckBulletin.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinTitle(zjTzSafetyQualityCheckBulletin.getCheckBulletinTitle());
           // 主要内容
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinContent(zjTzSafetyQualityCheckBulletin.getCheckBulletinContent());
           // 检查单位
           dbzjTzSafetyQualityCheckBulletin.setCheckUnit(zjTzSafetyQualityCheckBulletin.getCheckUnit());
           // 整改落实情况
           dbzjTzSafetyQualityCheckBulletin.setCheckRectificationImplementation(zjTzSafetyQualityCheckBulletin.getCheckRectificationImplementation());
           // 检查日期
           dbzjTzSafetyQualityCheckBulletin.setCheckDate(zjTzSafetyQualityCheckBulletin.getCheckDate());
           // 登记日期
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinDate(zjTzSafetyQualityCheckBulletin.getCheckBulletinDate());
           // 登记用户
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinUser(zjTzSafetyQualityCheckBulletin.getCheckBulletinUser());
           // 共通
           dbzjTzSafetyQualityCheckBulletin.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyQualityCheckBulletinMapper.updateByPrimaryKey(dbzjTzSafetyQualityCheckBulletin);
        }
        // 失败
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
        	//新增附件
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
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzSafetyQualityCheckBulletin.getProjectId(), true)) {
//        	zjTzSafetyQualityCheckBulletin.setProjectId("");
//        	zjTzSafetyQualityCheckBulletin.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }     
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSafetyQualityCheckBulletin.getProjectId(), true)) {
                zjTzSafetyQualityCheckBulletin.setProjectId("");
                zjTzSafetyQualityCheckBulletin.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSafetyQualityCheckBulletin.getProjectId(), true)) {
                zjTzSafetyQualityCheckBulletin.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzSafetyQualityCheckBulletin.getPage(),zjTzSafetyQualityCheckBulletin.getLimit());
        // 获取数据
        List<ZjTzSafetyQualityCheckBulletin> zjTzSafetyQualityCheckBulletinList = zjTzSafetyQualityCheckBulletinMapper.selectByZjTzSafetyQualityCheckBulletinList(zjTzSafetyQualityCheckBulletin);
        for(ZjTzSafetyQualityCheckBulletin checkBulletin : zjTzSafetyQualityCheckBulletinList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(checkBulletin.getCheckBulletinId());
        	checkBulletin.setCheckBulletinFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        }          
        // 得到分页信息
        PageInfo<ZjTzSafetyQualityCheckBulletin> p = new PageInfo<>(zjTzSafetyQualityCheckBulletinList);

        return repEntity.okList(zjTzSafetyQualityCheckBulletinList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSafetyQualityCheckBulletinDetails(ZjTzSafetyQualityCheckBulletin zjTzSafetyQualityCheckBulletin) {
        if (zjTzSafetyQualityCheckBulletin == null) {
            zjTzSafetyQualityCheckBulletin = new ZjTzSafetyQualityCheckBulletin();
        }
        // 获取数据
        ZjTzSafetyQualityCheckBulletin dbZjTzSafetyQualityCheckBulletin = zjTzSafetyQualityCheckBulletinMapper.selectByPrimaryKey(zjTzSafetyQualityCheckBulletin.getCheckBulletinId());
        // 数据存在
        if (dbZjTzSafetyQualityCheckBulletin != null) {
            return repEntity.ok(dbZjTzSafetyQualityCheckBulletin);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 项目ID
           dbzjTzSafetyQualityCheckBulletin.setProjectId(zjTzSafetyQualityCheckBulletin.getProjectId());
           // 项目名称
           dbzjTzSafetyQualityCheckBulletin.setProjectName(zjTzSafetyQualityCheckBulletin.getProjectName());
           // 子项目ID
           dbzjTzSafetyQualityCheckBulletin.setSubprojectsId(zjTzSafetyQualityCheckBulletin.getSubprojectsId());
           // 子项目名称
           dbzjTzSafetyQualityCheckBulletin.setSubprojectsName(zjTzSafetyQualityCheckBulletin.getSubprojectsName());
           // 标题
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinTitle(zjTzSafetyQualityCheckBulletin.getCheckBulletinTitle());
           // 主要内容
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinContent(zjTzSafetyQualityCheckBulletin.getCheckBulletinContent());
           // 检查单位
           dbzjTzSafetyQualityCheckBulletin.setCheckUnit(zjTzSafetyQualityCheckBulletin.getCheckUnit());
           // 整改落实情况
           dbzjTzSafetyQualityCheckBulletin.setCheckRectificationImplementation(zjTzSafetyQualityCheckBulletin.getCheckRectificationImplementation());
           // 检查日期
           dbzjTzSafetyQualityCheckBulletin.setCheckDate(zjTzSafetyQualityCheckBulletin.getCheckDate());
           // 登记日期
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinDate(zjTzSafetyQualityCheckBulletin.getCheckBulletinDate());
           // 登记用户
           dbzjTzSafetyQualityCheckBulletin.setCheckBulletinUser(zjTzSafetyQualityCheckBulletin.getCheckBulletinUser());
           // 共通
           dbzjTzSafetyQualityCheckBulletin.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyQualityCheckBulletinMapper.updateByPrimaryKey(dbzjTzSafetyQualityCheckBulletin);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
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
