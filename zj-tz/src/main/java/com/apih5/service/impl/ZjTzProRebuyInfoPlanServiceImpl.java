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
import com.apih5.mybatis.dao.ZjTzProRebuyInfoPlanMapper;
import com.apih5.mybatis.pojo.ZjTzProRebuyInfoPlan;
import com.apih5.service.ZjTzProRebuyInfoPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProRebuyInfoPlanService")
public class ZjTzProRebuyInfoPlanServiceImpl implements ZjTzProRebuyInfoPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProRebuyInfoPlanMapper zjTzProRebuyInfoPlanMapper;

    @Override
    public ResponseEntity getZjTzProRebuyInfoPlanListByCondition(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        if (zjTzProRebuyInfoPlan == null) {
            zjTzProRebuyInfoPlan = new ZjTzProRebuyInfoPlan();
        }
        if(StrUtil.isEmpty(zjTzProRebuyInfoPlan.getProjectId())){
        	return repEntity.okList(null, 0);
        }
        // 分页查询
        PageHelper.startPage(zjTzProRebuyInfoPlan.getPage(),zjTzProRebuyInfoPlan.getLimit());
        // 获取数据
        List<ZjTzProRebuyInfoPlan> zjTzProRebuyInfoPlanList = zjTzProRebuyInfoPlanMapper.selectByZjTzProRebuyInfoPlanList(zjTzProRebuyInfoPlan);
        // 得到分页信息
        PageInfo<ZjTzProRebuyInfoPlan> p = new PageInfo<>(zjTzProRebuyInfoPlanList);

        return repEntity.okList(zjTzProRebuyInfoPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProRebuyInfoPlanDetails(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        if (zjTzProRebuyInfoPlan == null) {
            zjTzProRebuyInfoPlan = new ZjTzProRebuyInfoPlan();
        }
        // 获取数据
        ZjTzProRebuyInfoPlan dbZjTzProRebuyInfoPlan = zjTzProRebuyInfoPlanMapper.selectByPrimaryKey(zjTzProRebuyInfoPlan.getRebuyInfoId());
        // 数据存在
        if (dbZjTzProRebuyInfoPlan != null) {
            return repEntity.ok(dbZjTzProRebuyInfoPlan);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzProRebuyInfoPlan(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProRebuyInfoPlan.setRebuyInfoId(UuidUtil.generate());
        zjTzProRebuyInfoPlan.setCreateUserInfo(userKey, realName);
        int flag = zjTzProRebuyInfoPlanMapper.insert(zjTzProRebuyInfoPlan);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProRebuyInfoPlan);
        }
    }

    @Override
    public ResponseEntity updateZjTzProRebuyInfoPlan(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProRebuyInfoPlan dbzjTzProRebuyInfoPlan = zjTzProRebuyInfoPlanMapper.selectByPrimaryKey(zjTzProRebuyInfoPlan.getRebuyInfoId());
        if (dbzjTzProRebuyInfoPlan != null && StrUtil.isNotEmpty(dbzjTzProRebuyInfoPlan.getRebuyInfoId())) {
           // 项目id
           dbzjTzProRebuyInfoPlan.setProjectId(zjTzProRebuyInfoPlan.getProjectId());
           // 协议约定次数
           dbzjTzProRebuyInfoPlan.setAppointNumber(zjTzProRebuyInfoPlan.getAppointNumber());
           // 协议约定金额
           dbzjTzProRebuyInfoPlan.setAppointAmount(zjTzProRebuyInfoPlan.getAppointAmount());
           // 协议约定时间
           dbzjTzProRebuyInfoPlan.setAppointDate(zjTzProRebuyInfoPlan.getAppointDate());
           // 共通
           dbzjTzProRebuyInfoPlan.setModifyUserInfo(userKey, realName);
           flag = zjTzProRebuyInfoPlanMapper.updateByPrimaryKey(dbzjTzProRebuyInfoPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProRebuyInfoPlan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProRebuyInfoPlan(List<ZjTzProRebuyInfoPlan> zjTzProRebuyInfoPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProRebuyInfoPlanList != null && zjTzProRebuyInfoPlanList.size() > 0) {
           ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan = new ZjTzProRebuyInfoPlan();
           zjTzProRebuyInfoPlan.setModifyUserInfo(userKey, realName);
           flag = zjTzProRebuyInfoPlanMapper.batchDeleteUpdateZjTzProRebuyInfoPlan(zjTzProRebuyInfoPlanList, zjTzProRebuyInfoPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProRebuyInfoPlanList);
        }
    }
}
