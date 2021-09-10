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
import com.apih5.mybatis.dao.ProInvXmhgInfoMapper;
import com.apih5.mybatis.pojo.ProInvXmhgInfo;
import com.apih5.service.ProInvXmhgInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvXmhgInfoService")
public class ProInvXmhgInfoServiceImpl implements ProInvXmhgInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvXmhgInfoMapper proInvXmhgInfoMapper;

    @Override
    public ResponseEntity getProInvXmhgInfoListByCondition(ProInvXmhgInfo proInvXmhgInfo) {
        if (proInvXmhgInfo == null) {
            proInvXmhgInfo = new ProInvXmhgInfo();
        }
        // 分页查询
        PageHelper.startPage(proInvXmhgInfo.getPage(),proInvXmhgInfo.getLimit());
        // 获取数据
        List<ProInvXmhgInfo> proInvXmhgInfoList = proInvXmhgInfoMapper.selectByProInvXmhgInfoList(proInvXmhgInfo);
        // 得到分页信息
        PageInfo<ProInvXmhgInfo> p = new PageInfo<>(proInvXmhgInfoList);

        return repEntity.okList(proInvXmhgInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvXmhgInfoDetails(ProInvXmhgInfo proInvXmhgInfo) {
        if (proInvXmhgInfo == null) {
            proInvXmhgInfo = new ProInvXmhgInfo();
        }
        // 获取数据
        ProInvXmhgInfo dbProInvXmhgInfo = proInvXmhgInfoMapper.selectByPrimaryKey(proInvXmhgInfo.getId());
        // 数据存在
        if (dbProInvXmhgInfo != null) {
            return repEntity.ok(dbProInvXmhgInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvXmhgInfo(ProInvXmhgInfo proInvXmhgInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvXmhgInfo.setId(UuidUtil.generate());
        proInvXmhgInfo.setCreateUserInfo(userKey, realName);
        int flag = proInvXmhgInfoMapper.insert(proInvXmhgInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvXmhgInfo);
        }
    }

    @Override
    public ResponseEntity updateProInvXmhgInfo(ProInvXmhgInfo proInvXmhgInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvXmhgInfo dbproInvXmhgInfo = proInvXmhgInfoMapper.selectByPrimaryKey(proInvXmhgInfo.getId());
        if (dbproInvXmhgInfo != null && StrUtil.isNotEmpty(dbproInvXmhgInfo.getId())) {
           // 排序号order
           dbproInvXmhgInfo.setInvHtinfoId(proInvXmhgInfo.getInvHtinfoId());
           // 公司id
           dbproInvXmhgInfo.setHgxyDate(proInvXmhgInfo.getHgxyDate());
           // 公司name
           dbproInvXmhgInfo.setHgxyMoney(proInvXmhgInfo.getHgxyMoney());
           // 共通
           dbproInvXmhgInfo.setModifyUserInfo(userKey, realName);
           flag = proInvXmhgInfoMapper.updateByPrimaryKey(dbproInvXmhgInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvXmhgInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvXmhgInfo(List<ProInvXmhgInfo> proInvXmhgInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvXmhgInfoList != null && proInvXmhgInfoList.size() > 0) {
           ProInvXmhgInfo proInvXmhgInfo = new ProInvXmhgInfo();
           proInvXmhgInfo.setModifyUserInfo(userKey, realName);
           flag = proInvXmhgInfoMapper.batchDeleteUpdateProInvXmhgInfo(proInvXmhgInfoList, proInvXmhgInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvXmhgInfoList);
        }
    }
}
