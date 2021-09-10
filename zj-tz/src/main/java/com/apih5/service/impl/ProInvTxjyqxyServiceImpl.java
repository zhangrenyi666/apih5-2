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
import com.apih5.mybatis.dao.ProInvTxjyqxyMapper;
import com.apih5.mybatis.pojo.ProInvTxjyqxy;
import com.apih5.service.ProInvTxjyqxyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvTxjyqxyService")
public class ProInvTxjyqxyServiceImpl implements ProInvTxjyqxyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvTxjyqxyMapper proInvTxjyqxyMapper;

    @Override
    public ResponseEntity getProInvTxjyqxyListByCondition(ProInvTxjyqxy proInvTxjyqxy) {
        if (proInvTxjyqxy == null) {
            proInvTxjyqxy = new ProInvTxjyqxy();
        }
        // 分页查询
        PageHelper.startPage(proInvTxjyqxy.getPage(),proInvTxjyqxy.getLimit());
        // 获取数据
        List<ProInvTxjyqxy> proInvTxjyqxyList = proInvTxjyqxyMapper.selectByProInvTxjyqxyList(proInvTxjyqxy);
        // 得到分页信息
        PageInfo<ProInvTxjyqxy> p = new PageInfo<>(proInvTxjyqxyList);

        return repEntity.okList(proInvTxjyqxyList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvTxjyqxyDetails(ProInvTxjyqxy proInvTxjyqxy) {
        if (proInvTxjyqxy == null) {
            proInvTxjyqxy = new ProInvTxjyqxy();
        }
        // 获取数据
        ProInvTxjyqxy dbProInvTxjyqxy = proInvTxjyqxyMapper.selectByPrimaryKey(proInvTxjyqxy.getId());
        // 数据存在
        if (dbProInvTxjyqxy != null) {
            return repEntity.ok(dbProInvTxjyqxy);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvTxjyqxy(ProInvTxjyqxy proInvTxjyqxy) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvTxjyqxy.setId(UuidUtil.generate());
        proInvTxjyqxy.setCreateUserInfo(userKey, realName);
        int flag = proInvTxjyqxyMapper.insert(proInvTxjyqxy);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvTxjyqxy);
        }
    }

    @Override
    public ResponseEntity updateProInvTxjyqxy(ProInvTxjyqxy proInvTxjyqxy) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvTxjyqxy dbproInvTxjyqxy = proInvTxjyqxyMapper.selectByPrimaryKey(proInvTxjyqxy.getId());
        if (dbproInvTxjyqxy != null && StrUtil.isNotEmpty(dbproInvTxjyqxy.getId())) {
           // 排序号order
           dbproInvTxjyqxy.setInvHtinfoId(proInvTxjyqxy.getInvHtinfoId());
           // 公司id
           dbproInvTxjyqxy.setXh(proInvTxjyqxy.getXh());
           // 公司name
           dbproInvTxjyqxy.setAgreementName(proInvTxjyqxy.getAgreementName());
           // 数据采集平台项目名称
           dbproInvTxjyqxy.setHtjf(proInvTxjyqxy.getHtjf());
           // 项目编号
           dbproInvTxjyqxy.setHtyf(proInvTxjyqxy.getHtyf());
           // 项目名称
           dbproInvTxjyqxy.setHtMoney(proInvTxjyqxy.getHtMoney());
           // 项目简称
           dbproInvTxjyqxy.setHtqdDate(proInvTxjyqxy.getHtqdDate());
           // 项目类别id
           dbproInvTxjyqxy.setYy(proInvTxjyqxy.getYy());
           // 项目类别name
           dbproInvTxjyqxy.setYyqsDate(proInvTxjyqxy.getYyqsDate());
           // 项目类型id
           dbproInvTxjyqxy.setHtFile(proInvTxjyqxy.getHtFile());
           // 共通
           dbproInvTxjyqxy.setModifyUserInfo(userKey, realName);
           flag = proInvTxjyqxyMapper.updateByPrimaryKey(dbproInvTxjyqxy);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvTxjyqxy);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvTxjyqxy(List<ProInvTxjyqxy> proInvTxjyqxyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvTxjyqxyList != null && proInvTxjyqxyList.size() > 0) {
           ProInvTxjyqxy proInvTxjyqxy = new ProInvTxjyqxy();
           proInvTxjyqxy.setModifyUserInfo(userKey, realName);
           flag = proInvTxjyqxyMapper.batchDeleteUpdateProInvTxjyqxy(proInvTxjyqxyList, proInvTxjyqxy);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvTxjyqxyList);
        }
    }
}
