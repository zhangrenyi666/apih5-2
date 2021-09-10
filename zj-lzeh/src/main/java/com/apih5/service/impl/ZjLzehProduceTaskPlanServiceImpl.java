package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehProduceTaskPlanItemMapper;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlanItem;
import com.apih5.service.ZjLzehProduceTaskPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehProduceTaskPlanMapper;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlan;
import com.apih5.service.ZjLzehProduceTaskPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zjLzehProduceTaskPlanService")
public class ZjLzehProduceTaskPlanServiceImpl implements ZjLzehProduceTaskPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanMapper zjLzehProduceTaskPlanMapper;

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanItemMapper zjLzehProduceTaskPlanItemMapper;

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanItemService zjLzehProduceTaskPlanItemService;

    @Override
    public ResponseEntity getZjLzehProduceTaskPlanListByCondition(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        if (zjLzehProduceTaskPlan == null) {
            zjLzehProduceTaskPlan = new ZjLzehProduceTaskPlan();
        }
        // 分页查询
        PageHelper.startPage(zjLzehProduceTaskPlan.getPage(),zjLzehProduceTaskPlan.getLimit());
        // 获取数据
        List<ZjLzehProduceTaskPlan> zjLzehProduceTaskPlanList = zjLzehProduceTaskPlanMapper.selectByZjLzehProduceTaskPlanList(zjLzehProduceTaskPlan);
        // 得到分页信息
        PageInfo<ZjLzehProduceTaskPlan> p = new PageInfo<>(zjLzehProduceTaskPlanList);

        return repEntity.okList(zjLzehProduceTaskPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehProduceTaskPlanDetail(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        if (zjLzehProduceTaskPlan == null) {
            zjLzehProduceTaskPlan = new ZjLzehProduceTaskPlan();
        }
        // 获取数据
        ZjLzehProduceTaskPlan dbZjLzehProduceTaskPlan = zjLzehProduceTaskPlanMapper.selectByPrimaryKey(zjLzehProduceTaskPlan.getZjLzehProduceTaskPlanId());
        // 数据存在
        if (dbZjLzehProduceTaskPlan != null) {
            return repEntity.ok(dbZjLzehProduceTaskPlan);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehProduceTaskPlan(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjLzehProduceTaskPlan zjLzehProduceTaskPlan1 = new ZjLzehProduceTaskPlan();
        zjLzehProduceTaskPlan1.setMonth(zjLzehProduceTaskPlan.getMonth());
        List<ZjLzehProduceTaskPlan> ZjLzehProduceTaskPlanList = zjLzehProduceTaskPlanMapper.selectByZjLzehProduceTaskPlanList(zjLzehProduceTaskPlan1);
        if (ZjLzehProduceTaskPlanList.size() > 0) {
            return repEntity.layerMessage("no", "该月份数据已存在");
        }
        zjLzehProduceTaskPlan.setZjLzehProduceTaskPlanId(UuidUtil.generate());
        zjLzehProduceTaskPlan.setCreateUserInfo(userKey, realName);
        int flag = zjLzehProduceTaskPlanMapper.insert(zjLzehProduceTaskPlan);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehProduceTaskPlan);
        }
    }

    @Override
    public ResponseEntity updateZjLzehProduceTaskPlan(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehProduceTaskPlan dbZjLzehProduceTaskPlan = zjLzehProduceTaskPlanMapper.selectByPrimaryKey(zjLzehProduceTaskPlan.getZjLzehProduceTaskPlanId());
        if (dbZjLzehProduceTaskPlan != null && StrUtil.isNotEmpty(dbZjLzehProduceTaskPlan.getZjLzehProduceTaskPlanId())) {
           // 月份
           dbZjLzehProduceTaskPlan.setMonth(zjLzehProduceTaskPlan.getMonth());
           // 任务数
           dbZjLzehProduceTaskPlan.setTaskQty(zjLzehProduceTaskPlan.getTaskQty());
           // 备注
           dbZjLzehProduceTaskPlan.setRemarks(zjLzehProduceTaskPlan.getRemarks());
           // 排序
           dbZjLzehProduceTaskPlan.setSort(zjLzehProduceTaskPlan.getSort());
           // 共通
           dbZjLzehProduceTaskPlan.setModifyUserInfo(userKey, realName);
           flag = zjLzehProduceTaskPlanMapper.updateByPrimaryKey(dbZjLzehProduceTaskPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehProduceTaskPlan);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteUpdateZjLzehProduceTaskPlan(List<ZjLzehProduceTaskPlan> zjLzehProduceTaskPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;

        for (ZjLzehProduceTaskPlan zp :zjLzehProduceTaskPlanList) {
            ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem =new ZjLzehProduceTaskPlanItem();
            zjLzehProduceTaskPlanItem.setZjLzehProduceTaskPlanId(zp.getZjLzehProduceTaskPlanId());
            List<ZjLzehProduceTaskPlanItem> dbZjLzehProduceTaskPlanItems=  zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem);
            zjLzehProduceTaskPlanItemService.batchDeleteUpdateZjLzehProduceTaskPlanItem(dbZjLzehProduceTaskPlanItems);

        }

        if (zjLzehProduceTaskPlanList != null && zjLzehProduceTaskPlanList.size() > 0) {
           ZjLzehProduceTaskPlan zjLzehProduceTaskPlan = new ZjLzehProduceTaskPlan();
           zjLzehProduceTaskPlan.setModifyUserInfo(userKey, realName);
           flag = zjLzehProduceTaskPlanMapper.batchDeleteUpdateZjLzehProduceTaskPlan(zjLzehProduceTaskPlanList, zjLzehProduceTaskPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehProduceTaskPlanList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
