package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjTzEmployeeInfoMapper;
import com.apih5.mybatis.dao.ZjTzProjectEmployeeMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeInfo;
import com.apih5.mybatis.pojo.ZjTzProjectEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzProjectAndEmployeeMapper;
import com.apih5.mybatis.pojo.ZjTzProjectAndEmployee;
import com.apih5.service.ZjTzProjectAndEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProjectAndEmployeeService")
public class ZjTzProjectAndEmployeeServiceImpl implements ZjTzProjectAndEmployeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProjectAndEmployeeMapper zjTzProjectAndEmployeeMapper;

    @Autowired(required = true)
    private ZjTzProjectEmployeeMapper zjTzProjectEmployeeMapper;

    @Autowired(required = true)
    private ZjTzEmployeeInfoMapper zjTzEmployeeInfoMapper;


    @Override
    public ResponseEntity getZjTzProjectAndEmployeeListByCondition(ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        if (zjTzProjectAndEmployee == null) {
            zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        }
        // 分页查询
        PageHelper.startPage(zjTzProjectAndEmployee.getPage(),zjTzProjectAndEmployee.getLimit());
        // 获取数据
        List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee);
        //人员信息
        for (ZjTzProjectAndEmployee tzProjectAndEmployee : zjTzProjectAndEmployeeList) {
            ZjTzEmployeeInfo zjTzEmployeeInfo = new ZjTzEmployeeInfo();
            zjTzEmployeeInfo.setEmployeeInfoId(tzProjectAndEmployee.getEmployeeInfoId());
            List<ZjTzEmployeeInfo> zjTzEmployeeInfos = zjTzEmployeeInfoMapper.selectByZjTzEmployeeInfoListAll(zjTzEmployeeInfo);
            if (zjTzEmployeeInfos != null && zjTzEmployeeInfos.size() > 0) {
                tzProjectAndEmployee.setPerson(zjTzEmployeeInfos.get(0));
            }
        }
        // 得到分页信息
        PageInfo<ZjTzProjectAndEmployee> p = new PageInfo<>(zjTzProjectAndEmployeeList);

        return repEntity.okList(zjTzProjectAndEmployeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProjectAndEmployeeDetails(ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        if (zjTzProjectAndEmployee == null) {
            zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        }
        // 获取数据
        ZjTzProjectAndEmployee dbZjTzProjectAndEmployee = zjTzProjectAndEmployeeMapper.selectByPrimaryKey(zjTzProjectAndEmployee.getProjectAndEmployeeId());
        // 数据存在
        if (dbZjTzProjectAndEmployee != null) {
            return repEntity.ok(dbZjTzProjectAndEmployee);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzProjectAndEmployee(ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
        zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
        int flag = zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProjectAndEmployee);
        }
    }

    @Override
    public ResponseEntity updateZjTzProjectAndEmployee(ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProjectAndEmployee dbzjTzProjectAndEmployee = zjTzProjectAndEmployeeMapper.selectByPrimaryKey(zjTzProjectAndEmployee.getProjectAndEmployeeId());
        if (dbzjTzProjectAndEmployee != null && StrUtil.isNotEmpty(dbzjTzProjectAndEmployee.getProjectAndEmployeeId())) {
           // 项目id
           dbzjTzProjectAndEmployee.setProjectId(zjTzProjectAndEmployee.getProjectId());
           // 人员id
           dbzjTzProjectAndEmployee.setEmployeeInfoId(zjTzProjectAndEmployee.getEmployeeInfoId());
           // 项目人员id
           dbzjTzProjectAndEmployee.setProjectEmployeeId(zjTzProjectAndEmployee.getProjectEmployeeId());
           // 共通
           dbzjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
           flag = zjTzProjectAndEmployeeMapper.updateByPrimaryKey(dbzjTzProjectAndEmployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectAndEmployee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProjectAndEmployee(List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProjectAndEmployee dbZjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        if (zjTzProjectAndEmployeeList != null && zjTzProjectAndEmployeeList.size() > 0) {
            //先查询Id
            dbZjTzProjectAndEmployee = zjTzProjectAndEmployeeMapper.selectByPrimaryKey(zjTzProjectAndEmployeeList.get(0).getProjectAndEmployeeId());
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectAndEmployeeMapper.batchDeleteUpdateZjTzProjectAndEmployee(zjTzProjectAndEmployeeList, zjTzProjectAndEmployee);
            //修改人员的调出时间
            List<ZjTzEmployeeInfo> zjTzEmployeeInfoList = zjTzProjectAndEmployeeList.stream().map(o -> {
                ZjTzEmployeeInfo zjTzEmployeeInfo = new ZjTzEmployeeInfo();
                //id
                zjTzEmployeeInfo.setEmployeeInfoId(o.getEmployeeInfoId());
                //时间
                zjTzEmployeeInfo.setJoinTime(o.getJoinTime());
                zjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
                return zjTzEmployeeInfo;
            }).collect(Collectors.toList());
            for (ZjTzEmployeeInfo tzEmployeeInfo : zjTzEmployeeInfoList) {
                flag = zjTzEmployeeInfoMapper.updateJoinTimeAndProject(tzEmployeeInfo);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            if(dbZjTzProjectAndEmployee != null){
                //查询人数
                ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
                zjTzProjectAndEmployee.setProjectEmployeeId(dbZjTzProjectAndEmployee.getProjectEmployeeId());
                List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee);
                //修改人数
                ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
                zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
                zjTzProjectEmployee.setProjectEmployeeId(dbZjTzProjectAndEmployee.getProjectEmployeeId());
                zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
            }
            return repEntity.layerMessage(true,zjTzProjectAndEmployeeList,"已经成功调出人员");
        }
    }




}
