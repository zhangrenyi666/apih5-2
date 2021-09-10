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
import com.apih5.mybatis.dao.ProInvBasicMapper;
import com.apih5.mybatis.pojo.ProInvBasic;
import com.apih5.service.ProInvBasicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service("proInvBasicService")
public class ProInvBasicServiceImpl implements ProInvBasicService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvBasicMapper proInvBasicMapper;

    @Override
    public ResponseEntity getProInvBasicListByCondition(ProInvBasic proInvBasic) {
        if (proInvBasic == null) {
            proInvBasic = new ProInvBasic();
        }
        // 分页查询
        PageHelper.startPage(proInvBasic.getPage(),proInvBasic.getLimit());
        // 获取数据
        List<ProInvBasic> proInvBasicList = proInvBasicMapper.selectByProInvBasicList(proInvBasic);
        // 得到分页信息
        PageInfo<ProInvBasic> p = new PageInfo<>(proInvBasicList);

        return repEntity.okList(proInvBasicList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvBasicDetails(ProInvBasic proInvBasic) {
        if (proInvBasic == null) {
            proInvBasic = new ProInvBasic();
        }
        // 获取数据
        ProInvBasic dbProInvBasic = proInvBasicMapper.selectByPrimaryKey(proInvBasic.getId());
        // 数据存在
        if (dbProInvBasic != null) {
            return repEntity.ok(dbProInvBasic);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvBasic(ProInvBasic proInvBasic) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvBasic.setId(UuidUtil.generate());
        proInvBasic.setCreateUserInfo(userKey, realName);
        int flag = proInvBasicMapper.insert(proInvBasic);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvBasic);
        }
    }
    /**
     * 
     * 集采平台修改
     */
    @Override
    public ResponseEntity updateProInvBasic(ProInvBasic proInvBasic) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        // 获取数据
        if(StrUtil.isNotEmpty(proInvBasic.getProjectId())) {
            ProInvBasic selectProInvBasic = new ProInvBasic();
            selectProInvBasic.setProjectId(proInvBasic.getProjectId());
            List<ProInvBasic> proInvBasicList = proInvBasicMapper.selectByProInvBasicList(selectProInvBasic);
            if (proInvBasicList != null && proInvBasicList.size() > 0) {
                for(ProInvBasic dbProInvBasic:proInvBasicList) {
                    dbProInvBasic.setProjectId("");
                    // 共通
                    dbProInvBasic.setModifyUserInfo(userKey, realName);
                    proInvBasicMapper.updateByPrimaryKey(dbProInvBasic);
                }
            }
        }
        int flag = 0;
        ProInvBasic dbproInvBasic = proInvBasicMapper.selectByPrimaryKey(proInvBasic.getId());
        if (dbproInvBasic != null && StrUtil.isNotEmpty(dbproInvBasic.getId())) {
           // 项目所在地
           dbproInvBasic.setProjectId(proInvBasic.getProjectId());
           // 共通
           dbproInvBasic.setModifyUserInfo(userKey, realName);
           flag = proInvBasicMapper.updateByPrimaryKey(dbproInvBasic);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvBasic);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvBasic(List<ProInvBasic> proInvBasicList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvBasicList != null && proInvBasicList.size() > 0) {
           ProInvBasic proInvBasic = new ProInvBasic();
           proInvBasic.setModifyUserInfo(userKey, realName);
           flag = proInvBasicMapper.batchDeleteUpdateProInvBasic(proInvBasicList, proInvBasic);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvBasicList);
        }
    }
    
}
