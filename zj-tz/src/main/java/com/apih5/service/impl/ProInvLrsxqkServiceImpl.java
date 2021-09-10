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
import com.apih5.mybatis.dao.ProInvLrsxqkMapper;
import com.apih5.mybatis.pojo.ProInvLrsxqk;
import com.apih5.service.ProInvLrsxqkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvLrsxqkService")
public class ProInvLrsxqkServiceImpl implements ProInvLrsxqkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvLrsxqkMapper proInvLrsxqkMapper;

    @Override
    public ResponseEntity getProInvLrsxqkListByCondition(ProInvLrsxqk proInvLrsxqk) {
        if (proInvLrsxqk == null) {
            proInvLrsxqk = new ProInvLrsxqk();
        }
        // 分页查询
        PageHelper.startPage(proInvLrsxqk.getPage(),proInvLrsxqk.getLimit());
        // 获取数据
        List<ProInvLrsxqk> proInvLrsxqkList = proInvLrsxqkMapper.selectByProInvLrsxqkList(proInvLrsxqk);
        // 得到分页信息
        PageInfo<ProInvLrsxqk> p = new PageInfo<>(proInvLrsxqkList);

        return repEntity.okList(proInvLrsxqkList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvLrsxqkDetails(ProInvLrsxqk proInvLrsxqk) {
        if (proInvLrsxqk == null) {
            proInvLrsxqk = new ProInvLrsxqk();
        }
        // 获取数据
        ProInvLrsxqk dbProInvLrsxqk = proInvLrsxqkMapper.selectByPrimaryKey(proInvLrsxqk.getId());
        // 数据存在
        if (dbProInvLrsxqk != null) {
            return repEntity.ok(dbProInvLrsxqk);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvLrsxqk(ProInvLrsxqk proInvLrsxqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvLrsxqk.setId(UuidUtil.generate());
        proInvLrsxqk.setCreateUserInfo(userKey, realName);
        int flag = proInvLrsxqkMapper.insert(proInvLrsxqk);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvLrsxqk);
        }
    }

    @Override
    public ResponseEntity updateProInvLrsxqk(ProInvLrsxqk proInvLrsxqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvLrsxqk dbproInvLrsxqk = proInvLrsxqkMapper.selectByPrimaryKey(proInvLrsxqk.getId());
        if (dbproInvLrsxqk != null && StrUtil.isNotEmpty(dbproInvLrsxqk.getId())) {
           // 排序号order
           dbproInvLrsxqk.setXmmc(proInvLrsxqk.getXmmc());
           // 公司id
           dbproInvLrsxqk.setJaze(proInvLrsxqk.getJaze());
           // 公司name
           dbproInvLrsxqk.setLrmbPgjdmbJcsyl(proInvLrsxqk.getLrmbPgjdmbJcsyl());
           // 数据采集平台项目名称
           dbproInvLrsxqk.setLrmbPgjdmbZbjjrzsyl(proInvLrsxqk.getLrmbPgjdmbZbjjrzsyl());
           // 项目编号
           dbproInvLrsxqk.setLrmbPgjdmbJcsy(proInvLrsxqk.getLrmbPgjdmbJcsy());
           // 项目名称
           dbproInvLrsxqk.setLrmbPgjdmbZbjjrzsy(proInvLrsxqk.getLrmbPgjdmbZbjjrzsy());
           // 项目简称
           dbproInvLrsxqk.setLrmbCnmbJcsy(proInvLrsxqk.getLrmbCnmbJcsy());
           // 
           dbproInvLrsxqk.setLrmbCnmbZjjrzsy(proInvLrsxqk.getLrmbCnmbZjjrzsy());
           // 
           dbproInvLrsxqk.setCnlrsxBqJcsy(proInvLrsxqk.getCnlrsxBqJcsy());
           // 
           dbproInvLrsxqk.setCnlrsxBqZjjrzsy(proInvLrsxqk.getCnlrsxBqZjjrzsy());
           // 
           dbproInvLrsxqk.setCnlrsxBnJcsy(proInvLrsxqk.getCnlrsxBnJcsy());
           // 
           dbproInvLrsxqk.setCnlrsxBnZjjrzsy(proInvLrsxqk.getCnlrsxBnZjjrzsy());
           // 
           dbproInvLrsxqk.setCnlrsxKlJcsy(proInvLrsxqk.getCnlrsxKlJcsy());
           // 
           dbproInvLrsxqk.setCnlrsxKlZjjrzsy(proInvLrsxqk.getCnlrsxKlZjjrzsy());
           // 
           dbproInvLrsxqk.setCnlrsjBqJcsy(proInvLrsxqk.getCnlrsjBqJcsy());
           // 
           dbproInvLrsxqk.setCnlrsjBqZjjrzsy(proInvLrsxqk.getCnlrsjBqZjjrzsy());
           // 
           dbproInvLrsxqk.setCnlrsjBnJcsy(proInvLrsxqk.getCnlrsjBnJcsy());
           // 
           dbproInvLrsxqk.setCnlrsjBnZjjrzsy(proInvLrsxqk.getCnlrsjBnZjjrzsy());
           // 
           dbproInvLrsxqk.setCnlrsjKlJcsy(proInvLrsxqk.getCnlrsjKlJcsy());
           // 
           dbproInvLrsxqk.setCnlrsjKlZjjrzsy(proInvLrsxqk.getCnlrsjKlZjjrzsy());
           // 
           dbproInvLrsxqk.setZxsxlrzeBqXmgs(proInvLrsxqk.getZxsxlrzeBqXmgs());
           // 
           dbproInvLrsxqk.setZxsxlrzeBqZcbb(proInvLrsxqk.getZxsxlrzeBqZcbb());
           // 
           dbproInvLrsxqk.setZxsxlrzeBqGfb(proInvLrsxqk.getZxsxlrzeBqGfb());
           // 
           dbproInvLrsxqk.setZxsxlrzeBnXmgs(proInvLrsxqk.getZxsxlrzeBnXmgs());
           // 
           dbproInvLrsxqk.setZxsxlrzeBnZcbb(proInvLrsxqk.getZxsxlrzeBnZcbb());
           // 
           dbproInvLrsxqk.setZxsxlrzeBnGfb(proInvLrsxqk.getZxsxlrzeBnGfb());
           // 
           dbproInvLrsxqk.setZxsxlrzeKlXmgs(proInvLrsxqk.getZxsxlrzeKlXmgs());
           // 
           dbproInvLrsxqk.setZxsxlrzeKlZcbb(proInvLrsxqk.getZxsxlrzeKlZcbb());
           // 
           dbproInvLrsxqk.setZxsxlrzeKlGfb(proInvLrsxqk.getZxsxlrzeKlGfb());
           // 
           dbproInvLrsxqk.setBz(proInvLrsxqk.getBz());
           // 
           dbproInvLrsxqk.setPeriodType(proInvLrsxqk.getPeriodType());
           // 
           dbproInvLrsxqk.setPeriodValue(proInvLrsxqk.getPeriodValue());
           // 
           dbproInvLrsxqk.setCreateBy(proInvLrsxqk.getCreateBy());
           // 
           dbproInvLrsxqk.setCreateOrg(proInvLrsxqk.getCreateOrg());
           // 
           dbproInvLrsxqk.setCreateDate(proInvLrsxqk.getCreateDate());
           // 
           dbproInvLrsxqk.setUpdateBy(proInvLrsxqk.getUpdateBy());
           // 
           dbproInvLrsxqk.setUpdateOrg(proInvLrsxqk.getUpdateOrg());
           // 
           dbproInvLrsxqk.setUpdateDate(proInvLrsxqk.getUpdateDate());
           // 
           dbproInvLrsxqk.setRemarks(proInvLrsxqk.getRemarks());
           // 
           dbproInvLrsxqk.setCurrency(proInvLrsxqk.getCurrency());
           // 
           dbproInvLrsxqk.setPglrmbsxBqJcsy(proInvLrsxqk.getPglrmbsxBqJcsy());
           // 
           dbproInvLrsxqk.setPglrmbsxBnJcsy(proInvLrsxqk.getPglrmbsxBnJcsy());
           // 
           dbproInvLrsxqk.setPglrmbsxKlJcsy(proInvLrsxqk.getPglrmbsxKlJcsy());
           // 
           dbproInvLrsxqk.setPglrmbsxBqTrzsy(proInvLrsxqk.getPglrmbsxBqTrzsy());
           // 
           dbproInvLrsxqk.setPglrmbsxBnTrzsy(proInvLrsxqk.getPglrmbsxBnTrzsy());
           // 
           dbproInvLrsxqk.setPglrmbsxKlTrzsy(proInvLrsxqk.getPglrmbsxKlTrzsy());
           // 
           dbproInvLrsxqk.setCnlrysjBqJcsy(proInvLrsxqk.getCnlrysjBqJcsy());
           // 
           dbproInvLrsxqk.setCnlrysjBnJcsy(proInvLrsxqk.getCnlrysjBnJcsy());
           // 
           dbproInvLrsxqk.setCnlrysjKlJcsy(proInvLrsxqk.getCnlrysjKlJcsy());
           // 
           dbproInvLrsxqk.setCnlrysjBqTrzsy(proInvLrsxqk.getCnlrysjBqTrzsy());
           // 
           dbproInvLrsxqk.setCnlrysjBnTrzsy(proInvLrsxqk.getCnlrysjBnTrzsy());
           // 
           dbproInvLrsxqk.setCnlrysjKlTrzsy(proInvLrsxqk.getCnlrysjKlTrzsy());
           // 共通
           dbproInvLrsxqk.setModifyUserInfo(userKey, realName);
           flag = proInvLrsxqkMapper.updateByPrimaryKey(dbproInvLrsxqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvLrsxqk);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvLrsxqk(List<ProInvLrsxqk> proInvLrsxqkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvLrsxqkList != null && proInvLrsxqkList.size() > 0) {
           ProInvLrsxqk proInvLrsxqk = new ProInvLrsxqk();
           proInvLrsxqk.setModifyUserInfo(userKey, realName);
           flag = proInvLrsxqkMapper.batchDeleteUpdateProInvLrsxqk(proInvLrsxqkList, proInvLrsxqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvLrsxqkList);
        }
    }
}
