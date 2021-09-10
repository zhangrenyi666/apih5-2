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
import com.apih5.mybatis.dao.ProInvHgxyMapper;
import com.apih5.mybatis.pojo.ProInvHgxy;
import com.apih5.service.ProInvHgxyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvHgxyService")
public class ProInvHgxyServiceImpl implements ProInvHgxyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvHgxyMapper proInvHgxyMapper;

    @Override
    public ResponseEntity getProInvHgxyListByCondition(ProInvHgxy proInvHgxy) {
        if (proInvHgxy == null) {
            proInvHgxy = new ProInvHgxy();
        }
        // 分页查询
        PageHelper.startPage(proInvHgxy.getPage(),proInvHgxy.getLimit());
        // 获取数据
        List<ProInvHgxy> proInvHgxyList = proInvHgxyMapper.selectByProInvHgxyList(proInvHgxy);
        // 得到分页信息
        PageInfo<ProInvHgxy> p = new PageInfo<>(proInvHgxyList);

        return repEntity.okList(proInvHgxyList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvHgxyDetails(ProInvHgxy proInvHgxy) {
        if (proInvHgxy == null) {
            proInvHgxy = new ProInvHgxy();
        }
        // 获取数据
        ProInvHgxy dbProInvHgxy = proInvHgxyMapper.selectByPrimaryKey(proInvHgxy.getId());
        // 数据存在
        if (dbProInvHgxy != null) {
            return repEntity.ok(dbProInvHgxy);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvHgxy(ProInvHgxy proInvHgxy) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvHgxy.setId(UuidUtil.generate());
        proInvHgxy.setCreateUserInfo(userKey, realName);
        int flag = proInvHgxyMapper.insert(proInvHgxy);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvHgxy);
        }
    }

    @Override
    public ResponseEntity updateProInvHgxy(ProInvHgxy proInvHgxy) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvHgxy dbproInvHgxy = proInvHgxyMapper.selectByPrimaryKey(proInvHgxy.getId());
        if (dbproInvHgxy != null && StrUtil.isNotEmpty(dbproInvHgxy.getId())) {
           // 排序号order
           dbproInvHgxy.setInvHtinfoId(proInvHgxy.getInvHtinfoId());
           // 公司id
           dbproInvHgxy.setHtmc(proInvHgxy.getHtmc());
           // 公司name
           dbproInvHgxy.setHtjf(proInvHgxy.getHtjf());
           // 数据采集平台项目名称
           dbproInvHgxy.setHtyf(proInvHgxy.getHtyf());
           // 项目编号
           dbproInvHgxy.setHgje(proInvHgxy.getHgje());
           // 项目名称
           dbproInvHgxy.setQdrq(proInvHgxy.getQdrq());
           // 项目简称
           dbproInvHgxy.setHgxyfj(proInvHgxy.getHgxyfj());
           // 共通
           dbproInvHgxy.setModifyUserInfo(userKey, realName);
           flag = proInvHgxyMapper.updateByPrimaryKey(dbproInvHgxy);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvHgxy);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvHgxy(List<ProInvHgxy> proInvHgxyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvHgxyList != null && proInvHgxyList.size() > 0) {
           ProInvHgxy proInvHgxy = new ProInvHgxy();
           proInvHgxy.setModifyUserInfo(userKey, realName);
           flag = proInvHgxyMapper.batchDeleteUpdateProInvHgxy(proInvHgxyList, proInvHgxy);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvHgxyList);
        }
    }
}
