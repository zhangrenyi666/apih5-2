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
import com.apih5.mybatis.dao.ProInvBasicPfinfoMapper;
import com.apih5.mybatis.pojo.ProInvBasicPfinfo;
import com.apih5.service.ProInvBasicPfinfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvBasicPfinfoService")
public class ProInvBasicPfinfoServiceImpl implements ProInvBasicPfinfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvBasicPfinfoMapper proInvBasicPfinfoMapper;

    @Override
    public ResponseEntity getProInvBasicPfinfoListByCondition(ProInvBasicPfinfo proInvBasicPfinfo) {
        if (proInvBasicPfinfo == null) {
            proInvBasicPfinfo = new ProInvBasicPfinfo();
        }
        // 分页查询
        PageHelper.startPage(proInvBasicPfinfo.getPage(),proInvBasicPfinfo.getLimit());
        // 获取数据
        List<ProInvBasicPfinfo> proInvBasicPfinfoList = proInvBasicPfinfoMapper.selectByProInvBasicPfinfoList(proInvBasicPfinfo);
        // 得到分页信息
        PageInfo<ProInvBasicPfinfo> p = new PageInfo<>(proInvBasicPfinfoList);

        return repEntity.okList(proInvBasicPfinfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvBasicPfinfoDetails(ProInvBasicPfinfo proInvBasicPfinfo) {
        if (proInvBasicPfinfo == null) {
            proInvBasicPfinfo = new ProInvBasicPfinfo();
        }
        // 获取数据
        ProInvBasicPfinfo dbProInvBasicPfinfo = proInvBasicPfinfoMapper.selectByPrimaryKey(proInvBasicPfinfo.getId());
        // 数据存在
        if (dbProInvBasicPfinfo != null) {
            return repEntity.ok(dbProInvBasicPfinfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvBasicPfinfo(ProInvBasicPfinfo proInvBasicPfinfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvBasicPfinfo.setId(UuidUtil.generate());
        proInvBasicPfinfo.setCreateUserInfo(userKey, realName);
        int flag = proInvBasicPfinfoMapper.insert(proInvBasicPfinfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvBasicPfinfo);
        }
    }

    @Override
    public ResponseEntity updateProInvBasicPfinfo(ProInvBasicPfinfo proInvBasicPfinfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvBasicPfinfo dbproInvBasicPfinfo = proInvBasicPfinfoMapper.selectByPrimaryKey(proInvBasicPfinfo.getId());
        if (dbproInvBasicPfinfo != null && StrUtil.isNotEmpty(dbproInvBasicPfinfo.getId())) {
           // 排序号order
           dbproInvBasicPfinfo.setInvProId(proInvBasicPfinfo.getInvProId());
           // 公司id
           dbproInvBasicPfinfo.setReplyCategory(proInvBasicPfinfo.getReplyCategory());
           // 公司name
           dbproInvBasicPfinfo.setJaf(proInvBasicPfinfo.getJaf());
           // 数据采集平台项目名称
           dbproInvBasicPfinfo.setZcf(proInvBasicPfinfo.getZcf());
           // 项目编号
           dbproInvBasicPfinfo.setGlf(proInvBasicPfinfo.getGlf());
           // 项目名称
           dbproInvBasicPfinfo.setJlf(proInvBasicPfinfo.getJlf());
           // 项目简称
           dbproInvBasicPfinfo.setKcsjf(proInvBasicPfinfo.getKcsjf());
           // 项目类别id
           dbproInvBasicPfinfo.setOth(proInvBasicPfinfo.getOth());
           // 项目类别name
           dbproInvBasicPfinfo.setPfzje(proInvBasicPfinfo.getPfzje());
           // 项目类型id
           dbproInvBasicPfinfo.setReplyDate(proInvBasicPfinfo.getReplyDate());
           // 
           dbproInvBasicPfinfo.setPgPf(proInvBasicPfinfo.getPgPf());
           // 
           dbproInvBasicPfinfo.setBz(proInvBasicPfinfo.getBz());
           // 共通
           dbproInvBasicPfinfo.setModifyUserInfo(userKey, realName);
           flag = proInvBasicPfinfoMapper.updateByPrimaryKey(dbproInvBasicPfinfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvBasicPfinfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvBasicPfinfo(List<ProInvBasicPfinfo> proInvBasicPfinfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvBasicPfinfoList != null && proInvBasicPfinfoList.size() > 0) {
           ProInvBasicPfinfo proInvBasicPfinfo = new ProInvBasicPfinfo();
           proInvBasicPfinfo.setModifyUserInfo(userKey, realName);
           flag = proInvBasicPfinfoMapper.batchDeleteUpdateProInvBasicPfinfo(proInvBasicPfinfoList, proInvBasicPfinfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvBasicPfinfoList);
        }
    }
}
