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
import com.apih5.mybatis.dao.ZjTzEmployeeProfessionalMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeProfessional;
import com.apih5.service.ZjTzEmployeeProfessionalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeeProfessionalService")
public class ZjTzEmployeeProfessionalServiceImpl implements ZjTzEmployeeProfessionalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeProfessionalMapper zjTzEmployeeProfessionalMapper;

    @Override
    public ResponseEntity getZjTzEmployeeProfessionalListByCondition(ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        if (zjTzEmployeeProfessional == null) {
            zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeProfessional.getPage(),zjTzEmployeeProfessional.getLimit());
        // 获取数据
        List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionalList = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
        // 得到分页信息
        PageInfo<ZjTzEmployeeProfessional> p = new PageInfo<>(zjTzEmployeeProfessionalList);

        return repEntity.okList(zjTzEmployeeProfessionalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeeProfessionalDetails(ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        if (zjTzEmployeeProfessional == null) {
            zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
        }
        // 获取数据
        ZjTzEmployeeProfessional dbZjTzEmployeeProfessional = zjTzEmployeeProfessionalMapper.selectByPrimaryKey(zjTzEmployeeProfessional.getProfessionalId());
        // 数据存在
        if (dbZjTzEmployeeProfessional != null) {
            return repEntity.ok(dbZjTzEmployeeProfessional);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeeProfessional(ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeeProfessional.setProfessionalId(UuidUtil.generate());
        zjTzEmployeeProfessional.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeProfessionalMapper.insert(zjTzEmployeeProfessional);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeProfessional);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeProfessional(ZjTzEmployeeProfessional zjTzEmployeeProfessional) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeProfessional dbzjTzEmployeeProfessional = zjTzEmployeeProfessionalMapper.selectByPrimaryKey(zjTzEmployeeProfessional.getProfessionalId());
        if (dbzjTzEmployeeProfessional != null && StrUtil.isNotEmpty(dbzjTzEmployeeProfessional.getProfessionalId())) {
           // 员工信息id
           dbzjTzEmployeeProfessional.setEmployeeInfoId(zjTzEmployeeProfessional.getEmployeeInfoId());
           // 专业技术资格名称
           dbzjTzEmployeeProfessional.setProfessionalNameId(zjTzEmployeeProfessional.getProfessionalNameId());
           // 职称等级
           dbzjTzEmployeeProfessional.setProfessionalLevelId(zjTzEmployeeProfessional.getProfessionalLevelId());
           // 获得资格时间
           dbzjTzEmployeeProfessional.setGetTime(zjTzEmployeeProfessional.getGetTime());
           // 取得资格途径
           dbzjTzEmployeeProfessional.setGetWayId(zjTzEmployeeProfessional.getGetWayId());
           // 资格审批单位
           dbzjTzEmployeeProfessional.setAppUnit(zjTzEmployeeProfessional.getAppUnit());
           // 聘任专业技术职务名称
           dbzjTzEmployeeProfessional.setEngageProfessionalName(zjTzEmployeeProfessional.getEngageProfessionalName());
           // 聘任开始时间
           dbzjTzEmployeeProfessional.setEngageStartTime(zjTzEmployeeProfessional.getEngageStartTime());
           // 聘任结束时间
           dbzjTzEmployeeProfessional.setEngageEndTime(zjTzEmployeeProfessional.getEngageEndTime());
           // 是否具有职业资格
           dbzjTzEmployeeProfessional.setProfessionalFlag(zjTzEmployeeProfessional.getProfessionalFlag());
           // 共通
           dbzjTzEmployeeProfessional.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeProfessionalMapper.updateByPrimaryKey(dbzjTzEmployeeProfessional);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeProfessional);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeProfessional(List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeProfessionalList != null && zjTzEmployeeProfessionalList.size() > 0) {
           ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
           zjTzEmployeeProfessional.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeProfessionalMapper.batchDeleteUpdateZjTzEmployeeProfessional(zjTzEmployeeProfessionalList, zjTzEmployeeProfessional);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeProfessionalList);
        }
    }
}