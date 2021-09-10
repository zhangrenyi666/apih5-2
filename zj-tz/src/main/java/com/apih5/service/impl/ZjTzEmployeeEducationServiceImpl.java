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
import com.apih5.mybatis.dao.ZjTzEmployeeEducationMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;
import com.apih5.service.ZjTzEmployeeEducationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeeEducationService")
public class ZjTzEmployeeEducationServiceImpl implements ZjTzEmployeeEducationService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeEducationMapper zjTzEmployeeEducationMapper;

    @Override
    public ResponseEntity getZjTzEmployeeEducationListByCondition(ZjTzEmployeeEducation zjTzEmployeeEducation) {
        if (zjTzEmployeeEducation == null) {
            zjTzEmployeeEducation = new ZjTzEmployeeEducation();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeEducation.getPage(),zjTzEmployeeEducation.getLimit());
        // 获取数据
        List<ZjTzEmployeeEducation> zjTzEmployeeEducationList = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation);
        // 得到分页信息
        PageInfo<ZjTzEmployeeEducation> p = new PageInfo<>(zjTzEmployeeEducationList);

        return repEntity.okList(zjTzEmployeeEducationList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeeEducationDetails(ZjTzEmployeeEducation zjTzEmployeeEducation) {
        if (zjTzEmployeeEducation == null) {
            zjTzEmployeeEducation = new ZjTzEmployeeEducation();
        }
        // 获取数据
        ZjTzEmployeeEducation dbZjTzEmployeeEducation = zjTzEmployeeEducationMapper.selectByPrimaryKey(zjTzEmployeeEducation.getEducationId());
        // 数据存在
        if (dbZjTzEmployeeEducation != null) {
            return repEntity.ok(dbZjTzEmployeeEducation);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeeEducation(ZjTzEmployeeEducation zjTzEmployeeEducation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeeEducation.setEducationId(UuidUtil.generate());
        zjTzEmployeeEducation.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeEducationMapper.insert(zjTzEmployeeEducation);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeEducation);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeEducation(ZjTzEmployeeEducation zjTzEmployeeEducation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeEducation dbzjTzEmployeeEducation = zjTzEmployeeEducationMapper.selectByPrimaryKey(zjTzEmployeeEducation.getEducationId());
        if (dbzjTzEmployeeEducation != null && StrUtil.isNotEmpty(dbzjTzEmployeeEducation.getEducationId())) {
           // 员工信息id
           dbzjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeEducation.getEmployeeInfoId());
           // 入学时间
           dbzjTzEmployeeEducation.setStratTime(zjTzEmployeeEducation.getStratTime());
           // 毕业时间
           dbzjTzEmployeeEducation.setGraduateTime(zjTzEmployeeEducation.getGraduateTime());
           // 毕业学校
           dbzjTzEmployeeEducation.setGraduatSchool(zjTzEmployeeEducation.getGraduatSchool());
           // 学习形式
           dbzjTzEmployeeEducation.setStudyForm(zjTzEmployeeEducation.getStudyForm());
           // 所学专业
           dbzjTzEmployeeEducation.setMajorIn(zjTzEmployeeEducation.getMajorIn());
           // 学历
           dbzjTzEmployeeEducation.setEducation(zjTzEmployeeEducation.getEducation());
           // 学位
           dbzjTzEmployeeEducation.setDegree(zjTzEmployeeEducation.getDegree());
           // 共通
           dbzjTzEmployeeEducation.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeEducationMapper.updateByPrimaryKey(dbzjTzEmployeeEducation);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeEducation);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeEducation(List<ZjTzEmployeeEducation> zjTzEmployeeEducationList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeEducationList != null && zjTzEmployeeEducationList.size() > 0) {
           ZjTzEmployeeEducation zjTzEmployeeEducation = new ZjTzEmployeeEducation();
           zjTzEmployeeEducation.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeEducationMapper.batchDeleteUpdateZjTzEmployeeEducation(zjTzEmployeeEducationList, zjTzEmployeeEducation);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeEducationList);
        }
    }

    @Override
    public ZjTzEmployeeEducation printZjTzEmployeeEducation(ZjTzEmployeeEducation zjTzEmployeeEducation) {
        if (zjTzEmployeeEducation == null) {
            zjTzEmployeeEducation = new ZjTzEmployeeEducation();
        }
     // 获取数据
        List<ZjTzEmployeeEducation> zjTzEmployeeEducationList = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation);

        return zjTzEmployeeEducationList.get(zjTzEmployeeEducationList.size()-1);
    }
}