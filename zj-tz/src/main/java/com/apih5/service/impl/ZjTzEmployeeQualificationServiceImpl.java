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
import com.apih5.mybatis.dao.ZjTzEmployeeQualificationMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeQualification;
import com.apih5.service.ZjTzEmployeeQualificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeeQualificationService")
public class ZjTzEmployeeQualificationServiceImpl implements ZjTzEmployeeQualificationService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeQualificationMapper zjTzEmployeeQualificationMapper;

    @Override
    public ResponseEntity getZjTzEmployeeQualificationListByCondition(ZjTzEmployeeQualification zjTzEmployeeQualification) {
        if (zjTzEmployeeQualification == null) {
            zjTzEmployeeQualification = new ZjTzEmployeeQualification();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeQualification.getPage(),zjTzEmployeeQualification.getLimit());
        // 获取数据
        List<ZjTzEmployeeQualification> zjTzEmployeeQualificationList = zjTzEmployeeQualificationMapper.selectByZjTzEmployeeQualificationList(zjTzEmployeeQualification);
        // 得到分页信息
        PageInfo<ZjTzEmployeeQualification> p = new PageInfo<>(zjTzEmployeeQualificationList);

        return repEntity.okList(zjTzEmployeeQualificationList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeeQualificationDetails(ZjTzEmployeeQualification zjTzEmployeeQualification) {
        if (zjTzEmployeeQualification == null) {
            zjTzEmployeeQualification = new ZjTzEmployeeQualification();
        }
        // 获取数据
        ZjTzEmployeeQualification dbZjTzEmployeeQualification = zjTzEmployeeQualificationMapper.selectByPrimaryKey(zjTzEmployeeQualification.getQualificationId());
        // 数据存在
        if (dbZjTzEmployeeQualification != null) {
            return repEntity.ok(dbZjTzEmployeeQualification);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeeQualification(ZjTzEmployeeQualification zjTzEmployeeQualification) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeeQualification.setQualificationId(UuidUtil.generate());
        zjTzEmployeeQualification.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeQualificationMapper.insert(zjTzEmployeeQualification);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeQualification);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeQualification(ZjTzEmployeeQualification zjTzEmployeeQualification) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeQualification dbzjTzEmployeeQualification = zjTzEmployeeQualificationMapper.selectByPrimaryKey(zjTzEmployeeQualification.getQualificationId());
        if (dbzjTzEmployeeQualification != null && StrUtil.isNotEmpty(dbzjTzEmployeeQualification.getQualificationId())) {
           // 员工信息id
           dbzjTzEmployeeQualification.setEmployeeInfoId(zjTzEmployeeQualification.getEmployeeInfoId());
           // 职（执）业资格名称
           dbzjTzEmployeeQualification.setQualificationName(zjTzEmployeeQualification.getQualificationName());
           // 所属专业
           dbzjTzEmployeeQualification.setMajor(zjTzEmployeeQualification.getMajor());
           // 获得时间
           dbzjTzEmployeeQualification.setGetTime(zjTzEmployeeQualification.getGetTime());
           // 证书号
           dbzjTzEmployeeQualification.setCertificateNumber(zjTzEmployeeQualification.getCertificateNumber());
           // 注册单位
           dbzjTzEmployeeQualification.setRegisteredUnit(zjTzEmployeeQualification.getRegisteredUnit());
           // 颁发单位
           dbzjTzEmployeeQualification.setIssuedUnit(zjTzEmployeeQualification.getIssuedUnit());
           // 共通
           dbzjTzEmployeeQualification.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeQualificationMapper.updateByPrimaryKey(dbzjTzEmployeeQualification);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeQualification);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeQualification(List<ZjTzEmployeeQualification> zjTzEmployeeQualificationList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeQualificationList != null && zjTzEmployeeQualificationList.size() > 0) {
           ZjTzEmployeeQualification zjTzEmployeeQualification = new ZjTzEmployeeQualification();
           zjTzEmployeeQualification.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeQualificationMapper.batchDeleteUpdateZjTzEmployeeQualification(zjTzEmployeeQualificationList, zjTzEmployeeQualification);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeQualificationList);
        }
    }
}