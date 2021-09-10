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
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzSafetyEmergencyPlanMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSafetyEmergencyPlan;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSafetyEmergencyPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSafetyEmergencyPlanService")
public class ZjTzSafetyEmergencyPlanServiceImpl implements ZjTzSafetyEmergencyPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSafetyEmergencyPlanMapper zjTzSafetyEmergencyPlanMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSafetyEmergencyPlanAddFile(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSafetyEmergencyPlan.setEmergencyPlanId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyEmergencyPlan.getSubprojectsId());        
        if(info !=null) {
        	zjTzSafetyEmergencyPlan.setSubprojectsName(info.getSubprojectName());
        }        
        zjTzSafetyEmergencyPlan.setCreateUserInfo(userKey, realName);
        int flag = zjTzSafetyEmergencyPlanMapper.insert(zjTzSafetyEmergencyPlan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//新增附件
        	if(zjTzSafetyEmergencyPlan.getEmergencyPlanFileList().size()>0 && zjTzSafetyEmergencyPlan.getEmergencyPlanFileList() != null) {
        		for(ZjTzFile file : zjTzSafetyEmergencyPlan.getEmergencyPlanFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSafetyEmergencyPlan.getEmergencyPlanId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}        	
            return repEntity.ok("sys.data.sava", zjTzSafetyEmergencyPlan);
        }
	}

	@Override
	public ResponseEntity updateZjTzSafetyEmergencyPlanAddFile(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSafetyEmergencyPlan dbzjTzSafetyEmergencyPlan = zjTzSafetyEmergencyPlanMapper.selectByPrimaryKey(zjTzSafetyEmergencyPlan.getEmergencyPlanId());
        if (dbzjTzSafetyEmergencyPlan != null && StrUtil.isNotEmpty(dbzjTzSafetyEmergencyPlan.getEmergencyPlanId())) {
           // 项目ID
           dbzjTzSafetyEmergencyPlan.setProjectId(zjTzSafetyEmergencyPlan.getProjectId());
           // 项目名称
           dbzjTzSafetyEmergencyPlan.setProjectName(zjTzSafetyEmergencyPlan.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzSafetyEmergencyPlan.getSubprojectsId(), dbzjTzSafetyEmergencyPlan.getSubprojectsId())) {
        	   dbzjTzSafetyEmergencyPlan.setSubprojectsId(zjTzSafetyEmergencyPlan.getSubprojectsId());        	   
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSafetyEmergencyPlan.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSafetyEmergencyPlan.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanTitle(zjTzSafetyEmergencyPlan.getEmergencyPlanTitle());
           // 主要内容
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanContent(zjTzSafetyEmergencyPlan.getEmergencyPlanContent());
           // 登记日期
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanDate(zjTzSafetyEmergencyPlan.getEmergencyPlanDate());
           // 登记用户
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanUser(zjTzSafetyEmergencyPlan.getEmergencyPlanUser());
           // 共通
           dbzjTzSafetyEmergencyPlan.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyEmergencyPlanMapper.updateByPrimaryKey(dbzjTzSafetyEmergencyPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzSafetyEmergencyPlan.getEmergencyPlanId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//新增附件
        	if(zjTzSafetyEmergencyPlan.getEmergencyPlanFileList().size()>0 && zjTzSafetyEmergencyPlan.getEmergencyPlanFileList() != null) {
        		for(ZjTzFile file : zjTzSafetyEmergencyPlan.getEmergencyPlanFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSafetyEmergencyPlan.getEmergencyPlanId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}  
            return repEntity.ok("sys.data.update",zjTzSafetyEmergencyPlan);
        }
	}
	
    @Override
    public ResponseEntity getZjTzSafetyEmergencyPlanListByCondition(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        if (zjTzSafetyEmergencyPlan == null) {
            zjTzSafetyEmergencyPlan = new ZjTzSafetyEmergencyPlan();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzSafetyEmergencyPlan.getProjectId(), true)) {
//        	zjTzSafetyEmergencyPlan.setProjectId("");
//        	zjTzSafetyEmergencyPlan.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        }   
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSafetyEmergencyPlan.getProjectId(), true)) {
                zjTzSafetyEmergencyPlan.setProjectId("");
                zjTzSafetyEmergencyPlan.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSafetyEmergencyPlan.getProjectId(), true)) {
                zjTzSafetyEmergencyPlan.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzSafetyEmergencyPlan.getPage(),zjTzSafetyEmergencyPlan.getLimit());
        // 获取数据
        List<ZjTzSafetyEmergencyPlan> zjTzSafetyEmergencyPlanList = zjTzSafetyEmergencyPlanMapper.selectByZjTzSafetyEmergencyPlanList(zjTzSafetyEmergencyPlan);
        for(ZjTzSafetyEmergencyPlan plan : zjTzSafetyEmergencyPlanList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(plan.getEmergencyPlanId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	plan.setEmergencyPlanFileList(zjTzFileList);	
        }
        // 得到分页信息
        PageInfo<ZjTzSafetyEmergencyPlan> p = new PageInfo<>(zjTzSafetyEmergencyPlanList);

        return repEntity.okList(zjTzSafetyEmergencyPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSafetyEmergencyPlanDetails(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        if (zjTzSafetyEmergencyPlan == null) {
            zjTzSafetyEmergencyPlan = new ZjTzSafetyEmergencyPlan();
        }
        // 获取数据
        ZjTzSafetyEmergencyPlan dbZjTzSafetyEmergencyPlan = zjTzSafetyEmergencyPlanMapper.selectByPrimaryKey(zjTzSafetyEmergencyPlan.getEmergencyPlanId());
        // 数据存在
        if (dbZjTzSafetyEmergencyPlan != null) {
            return repEntity.ok(dbZjTzSafetyEmergencyPlan);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSafetyEmergencyPlan(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSafetyEmergencyPlan.setEmergencyPlanId(UuidUtil.generate());
        zjTzSafetyEmergencyPlan.setCreateUserInfo(userKey, realName);
        int flag = zjTzSafetyEmergencyPlanMapper.insert(zjTzSafetyEmergencyPlan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSafetyEmergencyPlan);
        }
    }

    @Override
    public ResponseEntity updateZjTzSafetyEmergencyPlan(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSafetyEmergencyPlan dbzjTzSafetyEmergencyPlan = zjTzSafetyEmergencyPlanMapper.selectByPrimaryKey(zjTzSafetyEmergencyPlan.getEmergencyPlanId());
        if (dbzjTzSafetyEmergencyPlan != null && StrUtil.isNotEmpty(dbzjTzSafetyEmergencyPlan.getEmergencyPlanId())) {
           // 项目ID
           dbzjTzSafetyEmergencyPlan.setProjectId(zjTzSafetyEmergencyPlan.getProjectId());
           // 项目名称
           dbzjTzSafetyEmergencyPlan.setProjectName(zjTzSafetyEmergencyPlan.getProjectName());
           // 子项目ID
           dbzjTzSafetyEmergencyPlan.setSubprojectsId(zjTzSafetyEmergencyPlan.getSubprojectsId());
           // 子项目名称
           dbzjTzSafetyEmergencyPlan.setSubprojectsName(zjTzSafetyEmergencyPlan.getSubprojectsName());
           // 标题
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanTitle(zjTzSafetyEmergencyPlan.getEmergencyPlanTitle());
           // 主要内容
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanContent(zjTzSafetyEmergencyPlan.getEmergencyPlanContent());
           // 登记日期
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanDate(zjTzSafetyEmergencyPlan.getEmergencyPlanDate());
           // 登记用户
           dbzjTzSafetyEmergencyPlan.setEmergencyPlanUser(zjTzSafetyEmergencyPlan.getEmergencyPlanUser());
           // 共通
           dbzjTzSafetyEmergencyPlan.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyEmergencyPlanMapper.updateByPrimaryKey(dbzjTzSafetyEmergencyPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSafetyEmergencyPlan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSafetyEmergencyPlan(List<ZjTzSafetyEmergencyPlan> zjTzSafetyEmergencyPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSafetyEmergencyPlanList != null && zjTzSafetyEmergencyPlanList.size() > 0) {
           ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan = new ZjTzSafetyEmergencyPlan();
           zjTzSafetyEmergencyPlan.setModifyUserInfo(userKey, realName);
           flag = zjTzSafetyEmergencyPlanMapper.batchDeleteUpdateZjTzSafetyEmergencyPlan(zjTzSafetyEmergencyPlanList, zjTzSafetyEmergencyPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
            for(ZjTzSafetyEmergencyPlan emergencyPlan : zjTzSafetyEmergencyPlanList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(emergencyPlan.getEmergencyPlanId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            }        	
            return repEntity.ok("sys.data.delete",zjTzSafetyEmergencyPlanList);
        }
    }

}
