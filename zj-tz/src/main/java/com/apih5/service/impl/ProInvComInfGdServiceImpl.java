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
import com.apih5.mybatis.dao.ProInvComInfGdMapper;
import com.apih5.mybatis.pojo.ProInvComInfGd;
import com.apih5.service.ProInvComInfGdService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvComInfGdService")
public class ProInvComInfGdServiceImpl implements ProInvComInfGdService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvComInfGdMapper proInvComInfGdMapper;

    @Override
    public ResponseEntity getProInvComInfGdListByCondition(ProInvComInfGd proInvComInfGd) {
        if (proInvComInfGd == null) {
            proInvComInfGd = new ProInvComInfGd();
        }
        // 分页查询
        PageHelper.startPage(proInvComInfGd.getPage(),proInvComInfGd.getLimit());
        // 获取数据
        List<ProInvComInfGd> proInvComInfGdList = proInvComInfGdMapper.selectByProInvComInfGdList(proInvComInfGd);
        // 得到分页信息
        PageInfo<ProInvComInfGd> p = new PageInfo<>(proInvComInfGdList);

        return repEntity.okList(proInvComInfGdList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvComInfGdDetails(ProInvComInfGd proInvComInfGd) {
        if (proInvComInfGd == null) {
            proInvComInfGd = new ProInvComInfGd();
        }
        // 获取数据
        ProInvComInfGd dbProInvComInfGd = proInvComInfGdMapper.selectByPrimaryKey(proInvComInfGd.getGdCategory());
        // 数据存在
        if (dbProInvComInfGd != null) {
            return repEntity.ok(dbProInvComInfGd);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvComInfGd(ProInvComInfGd proInvComInfGd) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvComInfGd.setGdCategory(UuidUtil.generate());
        proInvComInfGd.setCreateUserInfo(userKey, realName);
        int flag = proInvComInfGdMapper.insert(proInvComInfGd);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvComInfGd);
        }
    }

    @Override
    public ResponseEntity updateProInvComInfGd(ProInvComInfGd proInvComInfGd) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvComInfGd dbproInvComInfGd = proInvComInfGdMapper.selectByPrimaryKey(proInvComInfGd.getGdCategory());
        if (dbproInvComInfGd != null && StrUtil.isNotEmpty(dbproInvComInfGd.getGdCategory())) {
           // 排序号order
           dbproInvComInfGd.setProComId(proInvComInfGd.getProComId());
           // 公司id
           dbproInvComInfGd.setGdName(proInvComInfGd.getGdName());
           // 公司name
           dbproInvComInfGd.setCgbl(proInvComInfGd.getCgbl());
           // 数据采集平台项目名称
           dbproInvComInfGd.setFpbl(proInvComInfGd.getFpbl());
           // 项目编号
           dbproInvComInfGd.setCze(proInvComInfGd.getCze());
           // 项目名称
           dbproInvComInfGd.setInvProId(proInvComInfGd.getInvProId());
           // 项目简称
           dbproInvComInfGd.setGdId(proInvComInfGd.getGdId());
           // 
           dbproInvComInfGd.setId(proInvComInfGd.getId());
           // 共通
           dbproInvComInfGd.setModifyUserInfo(userKey, realName);
           flag = proInvComInfGdMapper.updateByPrimaryKey(dbproInvComInfGd);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvComInfGd);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvComInfGd(List<ProInvComInfGd> proInvComInfGdList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvComInfGdList != null && proInvComInfGdList.size() > 0) {
           ProInvComInfGd proInvComInfGd = new ProInvComInfGd();
           proInvComInfGd.setModifyUserInfo(userKey, realName);
           flag = proInvComInfGdMapper.batchDeleteUpdateProInvComInfGd(proInvComInfGdList, proInvComInfGd);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvComInfGdList);
        }
    }
}
