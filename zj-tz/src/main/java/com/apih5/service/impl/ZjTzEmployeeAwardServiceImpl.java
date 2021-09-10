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
import com.apih5.mybatis.dao.ZjTzEmployeeAwardMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeAward;
import com.apih5.service.ZjTzEmployeeAwardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeeAwardService")
public class ZjTzEmployeeAwardServiceImpl implements ZjTzEmployeeAwardService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeAwardMapper zjTzEmployeeAwardMapper;

    @Override
    public ResponseEntity getZjTzEmployeeAwardListByCondition(ZjTzEmployeeAward zjTzEmployeeAward) {
        if (zjTzEmployeeAward == null) {
            zjTzEmployeeAward = new ZjTzEmployeeAward();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeAward.getPage(),zjTzEmployeeAward.getLimit());
        // 获取数据
        List<ZjTzEmployeeAward> zjTzEmployeeAwardList = zjTzEmployeeAwardMapper.selectByZjTzEmployeeAwardList(zjTzEmployeeAward);
        // 得到分页信息
        PageInfo<ZjTzEmployeeAward> p = new PageInfo<>(zjTzEmployeeAwardList);

        return repEntity.okList(zjTzEmployeeAwardList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeeAwardDetails(ZjTzEmployeeAward zjTzEmployeeAward) {
        if (zjTzEmployeeAward == null) {
            zjTzEmployeeAward = new ZjTzEmployeeAward();
        }
        // 获取数据
        ZjTzEmployeeAward dbZjTzEmployeeAward = zjTzEmployeeAwardMapper.selectByPrimaryKey(zjTzEmployeeAward.getAwardId());
        // 数据存在
        if (dbZjTzEmployeeAward != null) {
            return repEntity.ok(dbZjTzEmployeeAward);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeeAward(ZjTzEmployeeAward zjTzEmployeeAward) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeeAward.setAwardId(UuidUtil.generate());
        zjTzEmployeeAward.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeAwardMapper.insert(zjTzEmployeeAward);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeAward);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeAward(ZjTzEmployeeAward zjTzEmployeeAward) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeAward dbzjTzEmployeeAward = zjTzEmployeeAwardMapper.selectByPrimaryKey(zjTzEmployeeAward.getAwardId());
        if (dbzjTzEmployeeAward != null && StrUtil.isNotEmpty(dbzjTzEmployeeAward.getAwardId())) {
           // 员工信息id
           dbzjTzEmployeeAward.setEmployeeInfoId(zjTzEmployeeAward.getEmployeeInfoId());
           // 奖励时间
           dbzjTzEmployeeAward.setAwardTime(zjTzEmployeeAward.getAwardTime());
           // 奖励级别
           dbzjTzEmployeeAward.setAwardRank(zjTzEmployeeAward.getAwardRank());
           // 奖励名称
           dbzjTzEmployeeAward.setAwardName(zjTzEmployeeAward.getAwardName());
           // 奖励原因
           dbzjTzEmployeeAward.setAwardReason(zjTzEmployeeAward.getAwardReason());
           // 奖励批准单位
           dbzjTzEmployeeAward.setAwardAppUnit(zjTzEmployeeAward.getAwardAppUnit());
           // 共通
           dbzjTzEmployeeAward.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeAwardMapper.updateByPrimaryKey(dbzjTzEmployeeAward);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeAward);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeAward(List<ZjTzEmployeeAward> zjTzEmployeeAwardList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeAwardList != null && zjTzEmployeeAwardList.size() > 0) {
           ZjTzEmployeeAward zjTzEmployeeAward = new ZjTzEmployeeAward();
           zjTzEmployeeAward.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeAwardMapper.batchDeleteUpdateZjTzEmployeeAward(zjTzEmployeeAwardList, zjTzEmployeeAward);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeAwardList);
        }
    }
}
