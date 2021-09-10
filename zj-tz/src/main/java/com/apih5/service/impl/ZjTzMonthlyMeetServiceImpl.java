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
import com.apih5.mybatis.dao.ZjTzMonthlyMeetMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzMonthlyMeet;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzMonthlyMeetService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzMonthlyMeetService")
public class ZjTzMonthlyMeetServiceImpl implements ZjTzMonthlyMeetService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzMonthlyMeetMapper zjTzMonthlyMeetMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzMonthlyMeetListByCondition(ZjTzMonthlyMeet zjTzMonthlyMeet) {
        if (zjTzMonthlyMeet == null) {
            zjTzMonthlyMeet = new ZjTzMonthlyMeet();
        }
        // 分页查询
        PageHelper.startPage(zjTzMonthlyMeet.getPage(),zjTzMonthlyMeet.getLimit());
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzMonthlyMeet.getProjectId(), true)) {
            	zjTzMonthlyMeet.setProjectId("");
            	zjTzMonthlyMeet.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzMonthlyMeet.getProjectId(), true)) {
            	zjTzMonthlyMeet.setProjectId("");
            }
        }
        
        // 获取数据
        List<ZjTzMonthlyMeet> zjTzMonthlyMeetList = zjTzMonthlyMeetMapper.selectByZjTzMonthlyMeetList(zjTzMonthlyMeet);
        for (ZjTzMonthlyMeet zjTzMonthlyMeet2 : zjTzMonthlyMeetList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzMonthlyMeet2.getMonthlyMeetId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzMonthlyMeet2.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzMonthlyMeet> p = new PageInfo<>(zjTzMonthlyMeetList);

        return repEntity.okList(zjTzMonthlyMeetList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzMonthlyMeetDetails(ZjTzMonthlyMeet zjTzMonthlyMeet) {
        if (zjTzMonthlyMeet == null) {
            zjTzMonthlyMeet = new ZjTzMonthlyMeet();
        }
        // 获取数据
        ZjTzMonthlyMeet dbZjTzMonthlyMeet = zjTzMonthlyMeetMapper.selectByPrimaryKey(zjTzMonthlyMeet.getMonthlyMeetId());
        // 数据存在
        if (dbZjTzMonthlyMeet != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzMonthlyMeet.getMonthlyMeetId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzMonthlyMeet.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzMonthlyMeet);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzMonthlyMeet(ZjTzMonthlyMeet zjTzMonthlyMeet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzMonthlyMeet.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzMonthlyMeet.setProjectName(manage.getProjectName());
        }
        if(zjTzMonthlyMeet.getMonthDate() != null) {
        	zjTzMonthlyMeet.setMonthStr(DateUtil.format(zjTzMonthlyMeet.getMonthDate(), "yyyy-MM"));
        }
        //项目和月份的  重复校验
        ZjTzMonthlyMeet meetSelect = new ZjTzMonthlyMeet();
        meetSelect.setProjectId(zjTzMonthlyMeet.getProjectId());
        meetSelect.setMonthStr(zjTzMonthlyMeet.getMonthStr());
        List<ZjTzMonthlyMeet> meetList = zjTzMonthlyMeetMapper.selectByZjTzMonthlyMeetList(meetSelect);
        if(meetList != null && meetList.size() >0) {
        	 return repEntity.layerMessage("no", "该项目已经填写，请重新选择！");
        }
        zjTzMonthlyMeet.setMonthlyMeetId(UuidUtil.generate());
        zjTzMonthlyMeet.setCreateUserInfo(userKey, realName);
        int flag = zjTzMonthlyMeetMapper.insert(zjTzMonthlyMeet);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzMonthlyMeet.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzMonthlyMeet.getMonthlyMeetId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzMonthlyMeet);
        }
    }

    @Override
    public ResponseEntity updateZjTzMonthlyMeet(ZjTzMonthlyMeet zjTzMonthlyMeet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzMonthlyMeet dbzjTzMonthlyMeet = zjTzMonthlyMeetMapper.selectByPrimaryKey(zjTzMonthlyMeet.getMonthlyMeetId());
        if (dbzjTzMonthlyMeet != null && StrUtil.isNotEmpty(dbzjTzMonthlyMeet.getMonthlyMeetId())) {
//           // 项目id
//           dbzjTzMonthlyMeet.setProjectId(zjTzMonthlyMeet.getProjectId());
//           // 项目name
//           dbzjTzMonthlyMeet.setProjectName(zjTzMonthlyMeet.getProjectName());
//           // 月度
//           dbzjTzMonthlyMeet.setMonthDate(zjTzMonthlyMeet.getMonthDate());
//           // 月度str
//           if(zjTzMonthlyMeet.getMonthDate() != null) {
//        	   dbzjTzMonthlyMeet.setMonthStr(DateUtil.format(zjTzMonthlyMeet.getMonthDate(), "yyyy-MM"));
//           }
           // 主要内容
           dbzjTzMonthlyMeet.setContent(zjTzMonthlyMeet.getContent());
           // 登记日期
           dbzjTzMonthlyMeet.setRegisterDate(zjTzMonthlyMeet.getRegisterDate());
           // 登记用户
           dbzjTzMonthlyMeet.setRegisterPerson(zjTzMonthlyMeet.getRegisterPerson());
           // 共通
           dbzjTzMonthlyMeet.setModifyUserInfo(userKey, realName);
           flag = zjTzMonthlyMeetMapper.updateByPrimaryKey(dbzjTzMonthlyMeet);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzMonthlyMeet.getMonthlyMeetId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzMonthlyMeet.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzMonthlyMeet.getMonthlyMeetId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzMonthlyMeet);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzMonthlyMeet(List<ZjTzMonthlyMeet> zjTzMonthlyMeetList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzMonthlyMeetList != null && zjTzMonthlyMeetList.size() > 0) {
        	// 删除附件
        	for (ZjTzMonthlyMeet zjTzMonthlyMeet : zjTzMonthlyMeetList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzMonthlyMeet.getMonthlyMeetId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzMonthlyMeet zjTzMonthlyMeet = new ZjTzMonthlyMeet();
           zjTzMonthlyMeet.setModifyUserInfo(userKey, realName);
           flag = zjTzMonthlyMeetMapper.batchDeleteUpdateZjTzMonthlyMeet(zjTzMonthlyMeetList, zjTzMonthlyMeet);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzMonthlyMeetList);
        }
    }
}
