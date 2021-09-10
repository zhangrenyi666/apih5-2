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
import com.apih5.mybatis.dao.ProInvBgbcxyMapper;
import com.apih5.mybatis.pojo.ProInvBgbcxy;
import com.apih5.service.ProInvBgbcxyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvBgbcxyService")
public class ProInvBgbcxyServiceImpl implements ProInvBgbcxyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvBgbcxyMapper proInvBgbcxyMapper;

    @Override
    public ResponseEntity getProInvBgbcxyListByCondition(ProInvBgbcxy proInvBgbcxy) {
        if (proInvBgbcxy == null) {
            proInvBgbcxy = new ProInvBgbcxy();
        }
        // 分页查询
        PageHelper.startPage(proInvBgbcxy.getPage(),proInvBgbcxy.getLimit());
        // 获取数据
        List<ProInvBgbcxy> proInvBgbcxyList = proInvBgbcxyMapper.selectByProInvBgbcxyList(proInvBgbcxy);
        // 得到分页信息
        PageInfo<ProInvBgbcxy> p = new PageInfo<>(proInvBgbcxyList);

        return repEntity.okList(proInvBgbcxyList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvBgbcxyDetails(ProInvBgbcxy proInvBgbcxy) {
        if (proInvBgbcxy == null) {
            proInvBgbcxy = new ProInvBgbcxy();
        }
        // 获取数据
        ProInvBgbcxy dbProInvBgbcxy = proInvBgbcxyMapper.selectByPrimaryKey(proInvBgbcxy.getId());
        // 数据存在
        if (dbProInvBgbcxy != null) {
            return repEntity.ok(dbProInvBgbcxy);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvBgbcxy(ProInvBgbcxy proInvBgbcxy) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvBgbcxy.setId(UuidUtil.generate());
        proInvBgbcxy.setCreateUserInfo(userKey, realName);
        int flag = proInvBgbcxyMapper.insert(proInvBgbcxy);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvBgbcxy);
        }
    }

    @Override
    public ResponseEntity updateProInvBgbcxy(ProInvBgbcxy proInvBgbcxy) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvBgbcxy dbproInvBgbcxy = proInvBgbcxyMapper.selectByPrimaryKey(proInvBgbcxy.getId());
        if (dbproInvBgbcxy != null && StrUtil.isNotEmpty(dbproInvBgbcxy.getId())) {
           // 排序号order
           dbproInvBgbcxy.setInvHtinfoId(proInvBgbcxy.getInvHtinfoId());
           // 公司id
           dbproInvBgbcxy.setBchtName(proInvBgbcxy.getBchtName());
           // 公司name
           dbproInvBgbcxy.setBcjafMoney(proInvBgbcxy.getBcjafMoney());
           // 
           dbproInvBgbcxy.setBchjafMoney(proInvBgbcxy.getBchjafMoney());
           // 
           dbproInvBgbcxy.setBczcfMoney(proInvBgbcxy.getBczcfMoney());
           // 
           dbproInvBgbcxy.setBchzcfMoney(proInvBgbcxy.getBchzcfMoney());
           // 
           dbproInvBgbcxy.setBcglfMoney(proInvBgbcxy.getBcglfMoney());
           // 
           dbproInvBgbcxy.setBchglfMoney(proInvBgbcxy.getBchglfMoney());
           // 
           dbproInvBgbcxy.setBcjlfMoney(proInvBgbcxy.getBcjlfMoney());
           // 
           dbproInvBgbcxy.setBchjlfMoney(proInvBgbcxy.getBchjlfMoney());
           // 
           dbproInvBgbcxy.setBcccsjfMoney(proInvBgbcxy.getBcccsjfMoney());
           // 
           dbproInvBgbcxy.setBchccsjfMoney(proInvBgbcxy.getBchccsjfMoney());
           // 
           dbproInvBgbcxy.setBcothMoney(proInvBgbcxy.getBcothMoney());
           // 
           dbproInvBgbcxy.setBchothMoney(proInvBgbcxy.getBchothMoney());
           // 
           dbproInvBgbcxy.setBchhtMoney(proInvBgbcxy.getBchhtMoney());
           // 
           dbproInvBgbcxy.setBchhtgq(proInvBgbcxy.getBchhtgq());
           // 
           dbproInvBgbcxy.setIsNewlabel(proInvBgbcxy.getIsNewlabel());
           // 
           dbproInvBgbcxy.setNrxtglDate(proInvBgbcxy.getNrxtglDate());
           // 
           dbproInvBgbcxy.setSbjtglDate(proInvBgbcxy.getSbjtglDate());
           // 
           dbproInvBgbcxy.setBcxyqdDate(proInvBgbcxy.getBcxyqdDate());
           // 
           dbproInvBgbcxy.setCurrency(proInvBgbcxy.getCurrency());
           // 
           dbproInvBgbcxy.setCreateBy(proInvBgbcxy.getCreateBy());
           // 
           dbproInvBgbcxy.setCreateOrg(proInvBgbcxy.getCreateOrg());
           // 
           dbproInvBgbcxy.setCreateDate(proInvBgbcxy.getCreateDate());
           // 
           dbproInvBgbcxy.setUpdateBy(proInvBgbcxy.getUpdateBy());
           // 
           dbproInvBgbcxy.setUpdateOrg(proInvBgbcxy.getUpdateOrg());
           // 
           dbproInvBgbcxy.setUpdateDate(proInvBgbcxy.getUpdateDate());
           // 
           dbproInvBgbcxy.setRemarks(proInvBgbcxy.getRemarks());
           // 共通
           dbproInvBgbcxy.setModifyUserInfo(userKey, realName);
           flag = proInvBgbcxyMapper.updateByPrimaryKey(dbproInvBgbcxy);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvBgbcxy);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvBgbcxy(List<ProInvBgbcxy> proInvBgbcxyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvBgbcxyList != null && proInvBgbcxyList.size() > 0) {
           ProInvBgbcxy proInvBgbcxy = new ProInvBgbcxy();
           proInvBgbcxy.setModifyUserInfo(userKey, realName);
           flag = proInvBgbcxyMapper.batchDeleteUpdateProInvBgbcxy(proInvBgbcxyList, proInvBgbcxy);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvBgbcxyList);
        }
    }
}
