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
import com.apih5.mybatis.dao.SysWoaHintInformationMapper;
import com.apih5.mybatis.pojo.SysWoaHintInformation;
import com.apih5.service.SysWoaHintInformationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysWoaHintInformationService")
public class SysWoaHintInformationServiceImpl implements SysWoaHintInformationService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWoaHintInformationMapper sysWoaHintInformationMapper;

    @Override
    public ResponseEntity getSysWoaHintInformationListByCondition(SysWoaHintInformation sysWoaHintInformation) {
        if (sysWoaHintInformation == null) {
            sysWoaHintInformation = new SysWoaHintInformation();
        }
        // 分页查询
        PageHelper.startPage(sysWoaHintInformation.getPage(),sysWoaHintInformation.getLimit());
        // 获取数据
        List<SysWoaHintInformation> sysWoaHintInformationList = sysWoaHintInformationMapper.selectBySysWoaHintInformationList(sysWoaHintInformation);
        // 得到分页信息
        PageInfo<SysWoaHintInformation> p = new PageInfo<>(sysWoaHintInformationList);

        return repEntity.okList(sysWoaHintInformationList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysWoaHintInformation(SysWoaHintInformation sysWoaHintInformation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysWoaHintInformation.setHintInformationId(UuidUtil.generate());
        sysWoaHintInformation.setCreateUserInfo(userKey, realName);
        int flag = sysWoaHintInformationMapper.insert(sysWoaHintInformation);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWoaHintInformation);
        }
    }

    @Override
    public ResponseEntity updateSysWoaHintInformation(SysWoaHintInformation sysWoaHintInformation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWoaHintInformation dbsysWoaHintInformation = sysWoaHintInformationMapper.selectByPrimaryKey(sysWoaHintInformation.getHintInformationId());
        if (dbsysWoaHintInformation != null && StrUtil.isNotEmpty(dbsysWoaHintInformation.getHintInformationId())) {
           // 图标
           dbsysWoaHintInformation.setIcon(sysWoaHintInformation.getIcon());
           // 消息
           dbsysWoaHintInformation.setMsg(sysWoaHintInformation.getMsg());
           // 链接
           dbsysWoaHintInformation.setMsgLink(sysWoaHintInformation.getMsgLink());
           // 开始时间
           dbsysWoaHintInformation.setStartTime(sysWoaHintInformation.getStartTime());
           // 结束时间
           dbsysWoaHintInformation.setEndTime(sysWoaHintInformation.getEndTime());
           // 排序
           dbsysWoaHintInformation.setHintSort(sysWoaHintInformation.getHintSort());
           // 共通
           dbsysWoaHintInformation.setModifyUserInfo(userKey, realName);
           flag = sysWoaHintInformationMapper.updateByPrimaryKey(dbsysWoaHintInformation);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWoaHintInformation);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWoaHintInformation(List<SysWoaHintInformation> sysWoaHintInformationList) {
        int flag = 0;
        if (sysWoaHintInformationList != null && sysWoaHintInformationList.size() > 0) {
           flag = sysWoaHintInformationMapper.batchDeleteUpdateSysWoaHintInformation(sysWoaHintInformationList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWoaHintInformationList);
        }
    }
}
