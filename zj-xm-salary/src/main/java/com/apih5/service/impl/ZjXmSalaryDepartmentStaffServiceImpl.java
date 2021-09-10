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
import com.apih5.mybatis.dao.ZjXmSalaryDepartmentStaffMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentStaff;
import com.apih5.service.ZjXmSalaryDepartmentStaffService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryDepartmentStaffService")
public class ZjXmSalaryDepartmentStaffServiceImpl implements ZjXmSalaryDepartmentStaffService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmSalaryDepartmentStaffMapper zjXmSalaryDepartmentStaffMapper;

    @Override
    public ResponseEntity getZjXmSalaryDepartmentStaffListByCondition(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        if (zjXmSalaryDepartmentStaff == null) {
            zjXmSalaryDepartmentStaff = new ZjXmSalaryDepartmentStaff();
        }
        // 分页查询
        PageHelper.startPage(zjXmSalaryDepartmentStaff.getPage(),zjXmSalaryDepartmentStaff.getLimit());
        // 获取数据
        List<ZjXmSalaryDepartmentStaff> zjXmSalaryDepartmentStaffList = zjXmSalaryDepartmentStaffMapper.selectByZjXmSalaryDepartmentStaffList(zjXmSalaryDepartmentStaff);
        // 得到分页信息
        PageInfo<ZjXmSalaryDepartmentStaff> p = new PageInfo<>(zjXmSalaryDepartmentStaffList);

        return repEntity.okList(zjXmSalaryDepartmentStaffList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmSalaryDepartmentStaffDetails(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        if (zjXmSalaryDepartmentStaff == null) {
            zjXmSalaryDepartmentStaff = new ZjXmSalaryDepartmentStaff();
        }
        // 获取数据
        ZjXmSalaryDepartmentStaff dbZjXmSalaryDepartmentStaff = zjXmSalaryDepartmentStaffMapper.selectByPrimaryKey(zjXmSalaryDepartmentStaff.getStaffId());
        // 数据存在
        if (dbZjXmSalaryDepartmentStaff != null) {
            return repEntity.ok(dbZjXmSalaryDepartmentStaff);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmSalaryDepartmentStaff(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmSalaryDepartmentStaff.setStaffId(UuidUtil.generate());
        zjXmSalaryDepartmentStaff.setCreateUserInfo(userKey, realName);
        int flag = zjXmSalaryDepartmentStaffMapper.insert(zjXmSalaryDepartmentStaff);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmSalaryDepartmentStaff);
        }
    }

    @Override
    public ResponseEntity updateZjXmSalaryDepartmentStaff(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmSalaryDepartmentStaff dbzjXmSalaryDepartmentStaff = zjXmSalaryDepartmentStaffMapper.selectByPrimaryKey(zjXmSalaryDepartmentStaff.getStaffId());
        if (dbzjXmSalaryDepartmentStaff != null && StrUtil.isNotEmpty(dbzjXmSalaryDepartmentStaff.getStaffId())) {
           // 岗位
           dbzjXmSalaryDepartmentStaff.setPosition(zjXmSalaryDepartmentStaff.getPosition());
           // 人数
           dbzjXmSalaryDepartmentStaff.setNumber(zjXmSalaryDepartmentStaff.getNumber());
           // 是否兼职
           dbzjXmSalaryDepartmentStaff.setIsPartTime(zjXmSalaryDepartmentStaff.getIsPartTime());
           // 项目部门表id
           dbzjXmSalaryDepartmentStaff.setDepartmentId(zjXmSalaryDepartmentStaff.getDepartmentId());
           // 备注
           dbzjXmSalaryDepartmentStaff.setRemarks(zjXmSalaryDepartmentStaff.getRemarks());
           // 排序
           dbzjXmSalaryDepartmentStaff.setSort(zjXmSalaryDepartmentStaff.getSort());
           // 共通
           dbzjXmSalaryDepartmentStaff.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryDepartmentStaffMapper.updateByPrimaryKey(dbzjXmSalaryDepartmentStaff);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmSalaryDepartmentStaff);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmSalaryDepartmentStaff(List<ZjXmSalaryDepartmentStaff> zjXmSalaryDepartmentStaffList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmSalaryDepartmentStaffList != null && zjXmSalaryDepartmentStaffList.size() > 0) {
           ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff = new ZjXmSalaryDepartmentStaff();
           zjXmSalaryDepartmentStaff.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryDepartmentStaffMapper.batchDeleteUpdateZjXmSalaryDepartmentStaff(zjXmSalaryDepartmentStaffList, zjXmSalaryDepartmentStaff);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmSalaryDepartmentStaffList);
        }
    }
}
