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
import com.apih5.mybatis.dao.ProInvComInfMapper;
import com.apih5.mybatis.pojo.ProInvComInf;
import com.apih5.service.ProInvComInfService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvComInfService")
public class ProInvComInfServiceImpl implements ProInvComInfService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvComInfMapper proInvComInfMapper;

    @Override
    public ResponseEntity getProInvComInfListByCondition(ProInvComInf proInvComInf) {
        if (proInvComInf == null) {
            proInvComInf = new ProInvComInf();
        }
        // 分页查询
        PageHelper.startPage(proInvComInf.getPage(),proInvComInf.getLimit());
        // 获取数据
        List<ProInvComInf> proInvComInfList = proInvComInfMapper.selectByProInvComInfList(proInvComInf);
        // 得到分页信息
        PageInfo<ProInvComInf> p = new PageInfo<>(proInvComInfList);

        return repEntity.okList(proInvComInfList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvComInfDetails(ProInvComInf proInvComInf) {
        if (proInvComInf == null) {
            proInvComInf = new ProInvComInf();
        }
        // 获取数据
        ProInvComInf dbProInvComInf = proInvComInfMapper.selectByPrimaryKey(proInvComInf.getId());
        // 数据存在
        if (dbProInvComInf != null) {
            return repEntity.ok(dbProInvComInf);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvComInf(ProInvComInf proInvComInf) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvComInf.setId(UuidUtil.generate());
        proInvComInf.setCreateUserInfo(userKey, realName);
        int flag = proInvComInfMapper.insert(proInvComInf);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvComInf);
        }
    }

    @Override
    public ResponseEntity updateProInvComInf(ProInvComInf proInvComInf) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvComInf dbproInvComInf = proInvComInfMapper.selectByPrimaryKey(proInvComInf.getId());
        if (dbproInvComInf != null && StrUtil.isNotEmpty(dbproInvComInf.getId())) {
           // 排序号order
           dbproInvComInf.setProComName(proInvComInf.getProComName());
           // 公司id
           dbproInvComInf.setZczj(proInvComInf.getZczj());
           // 公司name
           dbproInvComInf.setRegisterArea(proInvComInf.getRegisterArea());
           // 数据采集平台项目名称
           dbproInvComInf.setFddbr(proInvComInf.getFddbr());
           // 项目编号
           dbproInvComInf.setRegisterDate(proInvComInf.getRegisterDate());
           // 项目名称
           dbproInvComInf.setComZjl(proInvComInf.getComZjl());
           // 项目简称
           dbproInvComInf.setZjlTel(proInvComInf.getZjlTel());
           // 
           dbproInvComInf.setComCwfzr(proInvComInf.getComCwfzr());
           // 
           dbproInvComInf.setCwTel(proInvComInf.getCwTel());
           // 
           dbproInvComInf.setSzgq(proInvComInf.getSzgq());
           // 
           dbproInvComInf.setGldw(proInvComInf.getGldw());
           // 
           dbproInvComInf.setYyzz(proInvComInf.getYyzz());
           // 
           dbproInvComInf.setGszc(proInvComInf.getGszc());
           // 
           dbproInvComInf.setCreateBy(proInvComInf.getCreateBy());
           // 
           dbproInvComInf.setCreateOrg(proInvComInf.getCreateOrg());
           // 
           dbproInvComInf.setCreateDate(proInvComInf.getCreateDate());
           // 
           dbproInvComInf.setUpdateBy(proInvComInf.getUpdateBy());
           // 
           dbproInvComInf.setUpdateOrg(proInvComInf.getUpdateOrg());
           // 
           dbproInvComInf.setUpdateDate(proInvComInf.getUpdateDate());
           // 
           dbproInvComInf.setRemarks(proInvComInf.getRemarks());
           // 
           dbproInvComInf.setInvProId(proInvComInf.getInvProId());
           // 
           dbproInvComInf.setXmmc(proInvComInf.getXmmc());
           // 
           dbproInvComInf.setWfgddbmc(proInvComInf.getWfgddbmc());
           // 
           dbproInvComInf.setZgjjszgq(proInvComInf.getZgjjszgq());
           // 
           dbproInvComInf.setYyzzh(proInvComInf.getYyzzh());
           // 共通
           dbproInvComInf.setModifyUserInfo(userKey, realName);
           flag = proInvComInfMapper.updateByPrimaryKey(dbproInvComInf);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvComInf);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvComInf(List<ProInvComInf> proInvComInfList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvComInfList != null && proInvComInfList.size() > 0) {
           ProInvComInf proInvComInf = new ProInvComInf();
           proInvComInf.setModifyUserInfo(userKey, realName);
           flag = proInvComInfMapper.batchDeleteUpdateProInvComInf(proInvComInfList, proInvComInf);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvComInfList);
        }
    }
}
