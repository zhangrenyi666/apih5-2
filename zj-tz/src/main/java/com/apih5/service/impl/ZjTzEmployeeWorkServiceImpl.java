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
import com.apih5.mybatis.dao.ZjTzEmployeeWorkMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeWork;
import com.apih5.service.ZjTzEmployeeWorkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeeWorkService")
public class ZjTzEmployeeWorkServiceImpl implements ZjTzEmployeeWorkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeWorkMapper zjTzEmployeeWorkMapper;

    @Override
    public ResponseEntity getZjTzEmployeeWorkListByCondition(ZjTzEmployeeWork zjTzEmployeeWork) {
        if (zjTzEmployeeWork == null) {
            zjTzEmployeeWork = new ZjTzEmployeeWork();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeWork.getPage(),zjTzEmployeeWork.getLimit());
        // 获取数据
        List<ZjTzEmployeeWork> zjTzEmployeeWorkList = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork);
        // 得到分页信息
        PageInfo<ZjTzEmployeeWork> p = new PageInfo<>(zjTzEmployeeWorkList);

        return repEntity.okList(zjTzEmployeeWorkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeeWorkDetails(ZjTzEmployeeWork zjTzEmployeeWork) {
        if (zjTzEmployeeWork == null) {
            zjTzEmployeeWork = new ZjTzEmployeeWork();
        }
        // 获取数据
        ZjTzEmployeeWork dbZjTzEmployeeWork = zjTzEmployeeWorkMapper.selectByPrimaryKey(zjTzEmployeeWork.getWorkId());
        // 数据存在
        if (dbZjTzEmployeeWork != null) {
            return repEntity.ok(dbZjTzEmployeeWork);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeeWork(ZjTzEmployeeWork zjTzEmployeeWork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeeWork.setWorkId(UuidUtil.generate());
        zjTzEmployeeWork.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeWorkMapper.insert(zjTzEmployeeWork);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeWork);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeWork(ZjTzEmployeeWork zjTzEmployeeWork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeWork dbzjTzEmployeeWork = zjTzEmployeeWorkMapper.selectByPrimaryKey(zjTzEmployeeWork.getWorkId());
        if (dbzjTzEmployeeWork != null && StrUtil.isNotEmpty(dbzjTzEmployeeWork.getWorkId())) {
           // 员工信息id
           dbzjTzEmployeeWork.setEmployeeInfoId(zjTzEmployeeWork.getEmployeeInfoId());
           // 开始时间
           dbzjTzEmployeeWork.setStartTime(zjTzEmployeeWork.getStartTime());
           // 截止时间
           dbzjTzEmployeeWork.setEndTime(zjTzEmployeeWork.getEndTime());
           // 工作单位
           dbzjTzEmployeeWork.setWorkUnit(zjTzEmployeeWork.getWorkUnit());
           // 职务（岗位）
           dbzjTzEmployeeWork.setDuty(zjTzEmployeeWork.getDuty());
           // 共通
           dbzjTzEmployeeWork.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeWorkMapper.updateByPrimaryKey(dbzjTzEmployeeWork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeWork);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeWork(List<ZjTzEmployeeWork> zjTzEmployeeWorkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeWorkList != null && zjTzEmployeeWorkList.size() > 0) {
           ZjTzEmployeeWork zjTzEmployeeWork = new ZjTzEmployeeWork();
           zjTzEmployeeWork.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeWorkMapper.batchDeleteUpdateZjTzEmployeeWork(zjTzEmployeeWorkList, zjTzEmployeeWork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeWorkList);
        }
    }

    @Override
    public ZjTzEmployeeWork printZjTzEmployeeWork(ZjTzEmployeeWork zjTzEmployeeWork) {
        if (zjTzEmployeeWork == null) {
            zjTzEmployeeWork = new ZjTzEmployeeWork();
        }
        // 获取数据
        List<ZjTzEmployeeWork> zjTzEmployeePoliticsList = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork);

        return zjTzEmployeePoliticsList.get(zjTzEmployeePoliticsList.size()-1);
    }
}