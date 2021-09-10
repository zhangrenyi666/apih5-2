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
import com.apih5.mybatis.dao.SysWebBgMapper;
import com.apih5.mybatis.pojo.SysWebBg;
import com.apih5.service.SysWebBgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysWebBgService")
public class SysWebBgServiceImpl implements SysWebBgService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWebBgMapper sysWebBgMapper;

    @Override
    public ResponseEntity getSysWebBgListByCondition(SysWebBg sysWebBg) {
        if (sysWebBg == null) {
            sysWebBg = new SysWebBg();
        }
        // 分页查询
        PageHelper.startPage(sysWebBg.getPage(),sysWebBg.getLimit());
        // 获取数据
        List<SysWebBg> sysWebBgList = sysWebBgMapper.selectBySysWebBgList(sysWebBg);
        // 得到分页信息
        PageInfo<SysWebBg> p = new PageInfo<>(sysWebBgList);

        return repEntity.okList(sysWebBgList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysWebBgDetails(SysWebBg sysWebBg) {
        if (sysWebBg == null) {
            sysWebBg = new SysWebBg();
        }
//        sysWebBg.setBgId("keys");
        // 获取数据
        SysWebBg dbSysWebBg = sysWebBgMapper.selectByPrimaryKey(sysWebBg.getBgId());
        // 数据存在
        if (dbSysWebBg != null) {
            return repEntity.ok(dbSysWebBg);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveSysWebBg(SysWebBg sysWebBg) {
    	if (sysWebBg == null || StrUtil.isEmpty(sysWebBg.getBgId())) {
    		 return repEntity.layerMessage("no", "模块ID必须输入!");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
//        sysWebBg.setBgId(UuidUtil.generate());
        sysWebBg.setCreateUserInfo(userKey, realName);
        int flag = sysWebBgMapper.insert(sysWebBg);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWebBg);
        }
    }

    @Override
    public ResponseEntity updateSysWebBg(SysWebBg sysWebBg) {
    	if (sysWebBg == null || StrUtil.isEmpty(sysWebBg.getBgId())) {
   		 return repEntity.layerMessage("no", "模块ID必须输入!");
       }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWebBg dbsysWebBg = sysWebBgMapper.selectByPrimaryKey(sysWebBg.getBgId());
        if (dbsysWebBg != null && StrUtil.isNotEmpty(dbsysWebBg.getBgId())) {
           // 内容
           dbsysWebBg.setContent(sysWebBg.getContent());
//           // 备注
//           dbsysWebBg.setRemarks(sysWebBg.getRemarks());
           // 共通
           dbsysWebBg.setModifyUserInfo(userKey, realName);
           flag = sysWebBgMapper.updateByPrimaryKey(dbsysWebBg);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWebBg);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWebBg(List<SysWebBg> sysWebBgList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysWebBgList != null && sysWebBgList.size() > 0) {
           SysWebBg sysWebBg = new SysWebBg();
           sysWebBg.setModifyUserInfo(userKey, realName);
           flag = sysWebBgMapper.batchDeleteUpdateSysWebBg(sysWebBgList, sysWebBg);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWebBgList);
        }
    }
    
    // --扩展
    @Override
    public ResponseEntity getSysWebBg(SysWebBg sysWebBg) {
        SysWebBg dbsysWebBg = sysWebBgMapper.selectByPrimaryKey(sysWebBg.getBgId());
        if (dbsysWebBg == null) {
        	return repEntity.ok("e2xvZ2luOiAidXNlci9sb2dpbiIsZWRpdFB3ZDogInVzZXIvdXBkYXRlVXNlclB3ZCIscmVzZXRQd2Q6ICJ1c2VyL3VwZGF0ZVVzZXJEZWZhdWx0UHdkIixyZXNldFVzZXJQd2Q6ICJ1c2VyL3Jlc2V0VXNlclB3ZCIscmVmcmVzaEFjY2Vzc1Rva2VuOiAidXNlci9yZWZyZXNoQWNjZXNzVG9rZW4iLHVwbG9hZDogInVwbG9hZCIsZ2V0WnhRcmNvZGVQZXJtaXNzaW9uT2JqZWN0TGlzdEJ5UHJvamVjdDogImdldFp4UXJjb2RlUGVybWlzc2lvbk9iamVjdExpc3RCeVByb2plY3QiLH0=");
        }
        return repEntity.ok(dbsysWebBg.getContent());
    }
}
