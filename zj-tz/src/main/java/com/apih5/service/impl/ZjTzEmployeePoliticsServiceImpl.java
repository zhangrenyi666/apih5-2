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
import com.apih5.mybatis.dao.ZjTzEmployeePoliticsMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeePolitics;
import com.apih5.service.ZjTzEmployeePoliticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeePoliticsService")
public class ZjTzEmployeePoliticsServiceImpl implements ZjTzEmployeePoliticsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeePoliticsMapper zjTzEmployeePoliticsMapper;

    @Override
    public ResponseEntity getZjTzEmployeePoliticsListByCondition(ZjTzEmployeePolitics zjTzEmployeePolitics) {
        if (zjTzEmployeePolitics == null) {
            zjTzEmployeePolitics = new ZjTzEmployeePolitics();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeePolitics.getPage(),zjTzEmployeePolitics.getLimit());
        // 获取数据
        List<ZjTzEmployeePolitics> zjTzEmployeePoliticsList = zjTzEmployeePoliticsMapper.selectByZjTzEmployeePoliticsList(zjTzEmployeePolitics);
        // 得到分页信息
        PageInfo<ZjTzEmployeePolitics> p = new PageInfo<>(zjTzEmployeePoliticsList);

        return repEntity.okList(zjTzEmployeePoliticsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeePoliticsDetails(ZjTzEmployeePolitics zjTzEmployeePolitics) {
        if (zjTzEmployeePolitics == null) {
            zjTzEmployeePolitics = new ZjTzEmployeePolitics();
        }
        // 获取数据
        ZjTzEmployeePolitics dbZjTzEmployeePolitics = zjTzEmployeePoliticsMapper.selectByPrimaryKey(zjTzEmployeePolitics.getPoliticsId());
        // 数据存在
        if (dbZjTzEmployeePolitics != null) {
            return repEntity.ok(dbZjTzEmployeePolitics);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeePolitics(ZjTzEmployeePolitics zjTzEmployeePolitics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeePolitics.setPoliticsId(UuidUtil.generate());
        zjTzEmployeePolitics.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeePoliticsMapper.insert(zjTzEmployeePolitics);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeePolitics);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeePolitics(ZjTzEmployeePolitics zjTzEmployeePolitics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeePolitics dbzjTzEmployeePolitics = zjTzEmployeePoliticsMapper.selectByPrimaryKey(zjTzEmployeePolitics.getPoliticsId());
        if (dbzjTzEmployeePolitics != null && StrUtil.isNotEmpty(dbzjTzEmployeePolitics.getPoliticsId())) {
           // 员工信息id
           dbzjTzEmployeePolitics.setEmployeeInfoId(zjTzEmployeePolitics.getEmployeeInfoId());
           // 政治面貌
           dbzjTzEmployeePolitics.setPoliticsStatus(zjTzEmployeePolitics.getPoliticsStatus());
           // 参加党派日期
           dbzjTzEmployeePolitics.setJoinPartTime(zjTzEmployeePolitics.getJoinPartTime());
           // 参加党派时所在单位
           dbzjTzEmployeePolitics.setJoinPartUnit(zjTzEmployeePolitics.getJoinPartUnit());
           // 共通
           dbzjTzEmployeePolitics.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeePoliticsMapper.updateByPrimaryKey(dbzjTzEmployeePolitics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeePolitics);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeePolitics(List<ZjTzEmployeePolitics> zjTzEmployeePoliticsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeePoliticsList != null && zjTzEmployeePoliticsList.size() > 0) {
           ZjTzEmployeePolitics zjTzEmployeePolitics = new ZjTzEmployeePolitics();
           zjTzEmployeePolitics.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeePoliticsMapper.batchDeleteUpdateZjTzEmployeePolitics(zjTzEmployeePoliticsList, zjTzEmployeePolitics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeePoliticsList);
        }
    }

    @Override
    public ZjTzEmployeePolitics printZjTzEmployeePolitics(ZjTzEmployeePolitics zjTzEmployeePolitics) {
        if (zjTzEmployeePolitics == null) {
            zjTzEmployeePolitics = new ZjTzEmployeePolitics();
        }
        // 获取数据
        List<ZjTzEmployeePolitics> zjTzEmployeePoliticsList = zjTzEmployeePoliticsMapper.selectByZjTzEmployeePoliticsList(zjTzEmployeePolitics);

        return zjTzEmployeePoliticsList.get(zjTzEmployeePoliticsList.size()-1);
    }
}