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
import com.apih5.mybatis.dao.ZjTzSupervisorMonthlyMeetingMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSupervisorMonthlyMeeting;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSupervisorMonthlyMeetingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSupervisorMonthlyMeetingService")
public class ZjTzSupervisorMonthlyMeetingServiceImpl implements ZjTzSupervisorMonthlyMeetingService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSupervisorMonthlyMeetingMapper zjTzSupervisorMonthlyMeetingMapper;
    
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;
	
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
	@Override
	public ResponseEntity saveZjTzSupervisorMonthlyMeetingAddFile(
			ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSupervisorMonthlyMeeting.setMonthlyMeetingId(UuidUtil.generate());
        ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSupervisorMonthlyMeeting.getSubprojectsId());        
        if(info !=null) {
        	zjTzSupervisorMonthlyMeeting.setSubprojectsName(info.getSubprojectName());
        }         
        zjTzSupervisorMonthlyMeeting.setCreateUserInfo(userKey, realName);
        int flag = zjTzSupervisorMonthlyMeetingMapper.insert(zjTzSupervisorMonthlyMeeting);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//新增附件
        	if(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingFileList().size()>0 && zjTzSupervisorMonthlyMeeting.getMonthlyMeetingFileList() != null) {
        		for(ZjTzFile file : zjTzSupervisorMonthlyMeeting.getMonthlyMeetingFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}           	
            return repEntity.ok("sys.data.sava", zjTzSupervisorMonthlyMeeting);
        }
	}

	@Override
	public ResponseEntity updateZjTzSupervisorMonthlyMeetingAddFile(
			ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSupervisorMonthlyMeeting dbzjTzSupervisorMonthlyMeeting = zjTzSupervisorMonthlyMeetingMapper.selectByPrimaryKey(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingId());
        if (dbzjTzSupervisorMonthlyMeeting != null && StrUtil.isNotEmpty(dbzjTzSupervisorMonthlyMeeting.getMonthlyMeetingId())) {
           // 项目ID
           dbzjTzSupervisorMonthlyMeeting.setProjectId(zjTzSupervisorMonthlyMeeting.getProjectId());
           // 项目名称
           dbzjTzSupervisorMonthlyMeeting.setProjectName(zjTzSupervisorMonthlyMeeting.getProjectName());
           // 子项目ID           
           if(!StrUtil.equals(zjTzSupervisorMonthlyMeeting.getSubprojectsId(), dbzjTzSupervisorMonthlyMeeting.getSubprojectsId())) {
        	   dbzjTzSupervisorMonthlyMeeting.setSubprojectsId(zjTzSupervisorMonthlyMeeting.getSubprojectsId());
               ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzSupervisorMonthlyMeeting.getSubprojectsId());        
               if(info !=null) {
            	   dbzjTzSupervisorMonthlyMeeting.setSubprojectsName(info.getSubprojectName());
               }
           }
           // 标题
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingMonth(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingMonth());
           // 主要内容
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingContent(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingContent());
           // 登记日期
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingDate(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingDate());
           // 登记用户
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingUser(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingUser());
           // 共通
           dbzjTzSupervisorMonthlyMeeting.setModifyUserInfo(userKey, realName);
           flag = zjTzSupervisorMonthlyMeetingMapper.updateByPrimaryKey(dbzjTzSupervisorMonthlyMeeting);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        	if(zjTzFileList.size()>0) {
        		zjTzFile.setModifyUserInfo(userKey, realName);
        		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        	}
        	//新增附件
        	if(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingFileList().size()>0 && zjTzSupervisorMonthlyMeeting.getMonthlyMeetingFileList() != null) {
        		for(ZjTzFile file : zjTzSupervisorMonthlyMeeting.getMonthlyMeetingFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingId());
        			file.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(file);
        		}
        	}          	
            return repEntity.ok("sys.data.update",zjTzSupervisorMonthlyMeeting);
        }
	}
	
    @Override
    public ResponseEntity getZjTzSupervisorMonthlyMeetingListByCondition(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (zjTzSupervisorMonthlyMeeting == null) {
            zjTzSupervisorMonthlyMeeting = new ZjTzSupervisorMonthlyMeeting();
        }
//    	// 选择全部项目是，通过拼接的sql去查询
//        if(StrUtil.equals("all", zjTzSupervisorMonthlyMeeting.getProjectId(), true)) {
//        	zjTzSupervisorMonthlyMeeting.setProjectId("");
//        	zjTzSupervisorMonthlyMeeting.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
//        } 
        
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzSupervisorMonthlyMeeting.getProjectId(), true)) {
                zjTzSupervisorMonthlyMeeting.setProjectId("");
                zjTzSupervisorMonthlyMeeting.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzSupervisorMonthlyMeeting.getProjectId(), true)) {
                zjTzSupervisorMonthlyMeeting.setProjectId("");
            }
        }
        
        // 分页查询
        PageHelper.startPage(zjTzSupervisorMonthlyMeeting.getPage(),zjTzSupervisorMonthlyMeeting.getLimit());
        // 获取数据
        List<ZjTzSupervisorMonthlyMeeting> zjTzSupervisorMonthlyMeetingList = zjTzSupervisorMonthlyMeetingMapper.selectByZjTzSupervisorMonthlyMeetingList(zjTzSupervisorMonthlyMeeting);
        for(ZjTzSupervisorMonthlyMeeting monthlyMeeting : zjTzSupervisorMonthlyMeetingList) {
        	ZjTzFile zjTzFile = new ZjTzFile();
        	zjTzFile.setOtherId(monthlyMeeting.getMonthlyMeetingId());
        	monthlyMeeting.setMonthlyMeetingFileList(zjTzFileMapper.selectByZjTzFileList(zjTzFile));
        	monthlyMeeting.setMonthlyMeetingMonthStr(DateUtil.format(monthlyMeeting.getMonthlyMeetingDate(), "YYYY-MM-dd"));
        }           
        // 得到分页信息
        PageInfo<ZjTzSupervisorMonthlyMeeting> p = new PageInfo<>(zjTzSupervisorMonthlyMeetingList);

        return repEntity.okList(zjTzSupervisorMonthlyMeetingList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSupervisorMonthlyMeetingDetails(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        if (zjTzSupervisorMonthlyMeeting == null) {
            zjTzSupervisorMonthlyMeeting = new ZjTzSupervisorMonthlyMeeting();
        }
        // 获取数据
        ZjTzSupervisorMonthlyMeeting dbZjTzSupervisorMonthlyMeeting = zjTzSupervisorMonthlyMeetingMapper.selectByPrimaryKey(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingId());
        // 数据存在
        if (dbZjTzSupervisorMonthlyMeeting != null) {
            return repEntity.ok(dbZjTzSupervisorMonthlyMeeting);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSupervisorMonthlyMeeting(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSupervisorMonthlyMeeting.setMonthlyMeetingId(UuidUtil.generate());
        zjTzSupervisorMonthlyMeeting.setCreateUserInfo(userKey, realName);
        int flag = zjTzSupervisorMonthlyMeetingMapper.insert(zjTzSupervisorMonthlyMeeting);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSupervisorMonthlyMeeting);
        }
    }

    @Override
    public ResponseEntity updateZjTzSupervisorMonthlyMeeting(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSupervisorMonthlyMeeting dbzjTzSupervisorMonthlyMeeting = zjTzSupervisorMonthlyMeetingMapper.selectByPrimaryKey(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingId());
        if (dbzjTzSupervisorMonthlyMeeting != null && StrUtil.isNotEmpty(dbzjTzSupervisorMonthlyMeeting.getMonthlyMeetingId())) {
           // 项目ID
           dbzjTzSupervisorMonthlyMeeting.setProjectId(zjTzSupervisorMonthlyMeeting.getProjectId());
           // 项目名称
           dbzjTzSupervisorMonthlyMeeting.setProjectName(zjTzSupervisorMonthlyMeeting.getProjectName());
           // 子项目ID
           dbzjTzSupervisorMonthlyMeeting.setSubprojectsId(zjTzSupervisorMonthlyMeeting.getSubprojectsId());
           // 子项目名称
           dbzjTzSupervisorMonthlyMeeting.setSubprojectsName(zjTzSupervisorMonthlyMeeting.getSubprojectsName());
           // 标题
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingMonth(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingMonth());
           // 主要内容
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingContent(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingContent());
           // 登记日期
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingDate(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingDate());
           // 登记用户
           dbzjTzSupervisorMonthlyMeeting.setMonthlyMeetingUser(zjTzSupervisorMonthlyMeeting.getMonthlyMeetingUser());
           // 共通
           dbzjTzSupervisorMonthlyMeeting.setModifyUserInfo(userKey, realName);
           flag = zjTzSupervisorMonthlyMeetingMapper.updateByPrimaryKey(dbzjTzSupervisorMonthlyMeeting);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSupervisorMonthlyMeeting);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSupervisorMonthlyMeeting(List<ZjTzSupervisorMonthlyMeeting> zjTzSupervisorMonthlyMeetingList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSupervisorMonthlyMeetingList != null && zjTzSupervisorMonthlyMeetingList.size() > 0) {
           ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting = new ZjTzSupervisorMonthlyMeeting();
           zjTzSupervisorMonthlyMeeting.setModifyUserInfo(userKey, realName);
           flag = zjTzSupervisorMonthlyMeetingMapper.batchDeleteUpdateZjTzSupervisorMonthlyMeeting(zjTzSupervisorMonthlyMeetingList, zjTzSupervisorMonthlyMeeting);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
        	//删除对应附件
            for(ZjTzSupervisorMonthlyMeeting monthlyMeeting : zjTzSupervisorMonthlyMeetingList) {
            	ZjTzFile zjTzFile = new ZjTzFile();
            	zjTzFile.setOtherId(monthlyMeeting.getMonthlyMeetingId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
            	if(zjTzFileList.size()>0) {
            		zjTzFile.setModifyUserInfo(userKey, realName);
            		zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
            	}
            } 
            return repEntity.ok("sys.data.delete",zjTzSupervisorMonthlyMeetingList);
        }
    }

}
