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
import com.apih5.mybatis.dao.ZxEqEemployeeMapper;
import com.apih5.mybatis.pojo.ZxEqEemployee;
import com.apih5.service.ZxEqEemployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEemployeeService")
public class ZxEqEemployeeServiceImpl implements ZxEqEemployeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEemployeeMapper zxEqEemployeeMapper;

    @Override
    public ResponseEntity getZxEqEemployeeListByCondition(ZxEqEemployee zxEqEemployee) {
        if (zxEqEemployee == null) {
            zxEqEemployee = new ZxEqEemployee();
        }
        // 分页查询
        PageHelper.startPage(zxEqEemployee.getPage(),zxEqEemployee.getLimit());
        // 获取数据
        List<ZxEqEemployee> zxEqEemployeeList = zxEqEemployeeMapper.selectByZxEqEemployeeList(zxEqEemployee);
        // 得到分页信息
        PageInfo<ZxEqEemployee> p = new PageInfo<>(zxEqEemployeeList);

        return repEntity.okList(zxEqEemployeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEemployeeDetails(ZxEqEemployee zxEqEemployee) {
        if (zxEqEemployee == null) {
            zxEqEemployee = new ZxEqEemployee();
        }
        // 获取数据
        ZxEqEemployee dbZxEqEemployee = zxEqEemployeeMapper.selectByPrimaryKey(zxEqEemployee.getId());
        // 数据存在
        if (dbZxEqEemployee != null) {
            return repEntity.ok(dbZxEqEemployee);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEemployee(ZxEqEemployee zxEqEemployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEemployee.setId(UuidUtil.generate());
        zxEqEemployee.setCreateUserInfo(userKey, realName);
        int flag = zxEqEemployeeMapper.insert(zxEqEemployee);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEemployee);
        }
    }

    @Override
    public ResponseEntity updateZxEqEemployee(ZxEqEemployee zxEqEemployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEemployee dbzxEqEemployee = zxEqEemployeeMapper.selectByPrimaryKey(zxEqEemployee.getId());
        if (dbzxEqEemployee != null && StrUtil.isNotEmpty(dbzxEqEemployee.getId())) {
           // 序号
           dbzxEqEemployee.setCode(zxEqEemployee.getCode());
           // 项目名称
           dbzxEqEemployee.setOrgName(zxEqEemployee.getOrgName());
           // 机构id
           dbzxEqEemployee.setOrgID(zxEqEemployee.getOrgID());
           // 姓名
           dbzxEqEemployee.setName(zxEqEemployee.getName());
           // 年龄
           dbzxEqEemployee.setAge(zxEqEemployee.getAge());
           // 文化程度
           dbzxEqEemployee.setEduLevel(zxEqEemployee.getEduLevel());
           // 职称
           dbzxEqEemployee.setTitle(zxEqEemployee.getTitle());
           // 所在岗位
           dbzxEqEemployee.setPos(zxEqEemployee.getPos());
           // 本岗工龄
           dbzxEqEemployee.setPosAge(zxEqEemployee.getPosAge());
           // 备注
           dbzxEqEemployee.setRemark(zxEqEemployee.getRemark());
           // 编制时间
           dbzxEqEemployee.setEditTime(zxEqEemployee.getEditTime());
           // 
           dbzxEqEemployee.setCombProp(zxEqEemployee.getCombProp());
           // 
           dbzxEqEemployee.setPp1(zxEqEemployee.getPp1());
           // 
           dbzxEqEemployee.setPp2(zxEqEemployee.getPp2());
           // 
           dbzxEqEemployee.setPp3(zxEqEemployee.getPp3());
           // 
           dbzxEqEemployee.setPp4(zxEqEemployee.getPp4());
           // 
           dbzxEqEemployee.setPp5(zxEqEemployee.getPp5());
           // 
           dbzxEqEemployee.setPp6(zxEqEemployee.getPp6());
           // 
           dbzxEqEemployee.setPp7(zxEqEemployee.getPp7());
           // 
           dbzxEqEemployee.setPp8(zxEqEemployee.getPp8());
           // 
           dbzxEqEemployee.setPp9(zxEqEemployee.getPp9());
           // 
           dbzxEqEemployee.setPp10(zxEqEemployee.getPp10());
           // 
           dbzxEqEemployee.setParentID(zxEqEemployee.getParentID());
           // 所在岗位id
           dbzxEqEemployee.setJobID(zxEqEemployee.getJobID());
           // 共通
           dbzxEqEemployee.setModifyUserInfo(userKey, realName);
           flag = zxEqEemployeeMapper.updateByPrimaryKey(dbzxEqEemployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEemployee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEemployee(List<ZxEqEemployee> zxEqEemployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEemployeeList != null && zxEqEemployeeList.size() > 0) {
           ZxEqEemployee zxEqEemployee = new ZxEqEemployee();
           zxEqEemployee.setModifyUserInfo(userKey, realName);
           flag = zxEqEemployeeMapper.batchDeleteUpdateZxEqEemployee(zxEqEemployeeList, zxEqEemployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEemployeeList);
        }
    }

    @Override
    public List<ZxEqEemployee> ureportZxEqEemployeeList(ZxEqEemployee zxEqEemployee) {
        if (zxEqEemployee == null) {
            zxEqEemployee = new ZxEqEemployee();
        }
        // 获取数据
        List<ZxEqEemployee> zxEqEemployeeList = zxEqEemployeeMapper.getZxEqEemployeeListForReport(zxEqEemployee);
        return zxEqEemployeeList;
    }
    
    @Override
    public ResponseEntity ureportZxEqEemployeeListIdle(ZxEqEemployee zxEqEemployee) {
        if (zxEqEemployee == null) {
            zxEqEemployee = new ZxEqEemployee();
        }
        // 分页查询
        PageHelper.startPage(zxEqEemployee.getPage(),zxEqEemployee.getLimit());
        // 获取数据
        List<ZxEqEemployee> zxEqEemployeeList = zxEqEemployeeMapper.getZxEqEemployeeListForReport(zxEqEemployee);
        // 得到分页信息
        PageInfo<ZxEqEemployee> p = new PageInfo<>(zxEqEemployeeList);

        return repEntity.okList(zxEqEemployeeList, p.getTotal());
    }
}
