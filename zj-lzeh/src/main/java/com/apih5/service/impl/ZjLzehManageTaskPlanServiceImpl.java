package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehManageTaskPlanItemMapper;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlanItem;
import com.apih5.service.ZjLzehManageTaskPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehManageTaskPlanMapper;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlan;
import com.apih5.service.ZjLzehManageTaskPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zjLzehManageTaskPlanService")
public class ZjLzehManageTaskPlanServiceImpl implements ZjLzehManageTaskPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehManageTaskPlanMapper zjLzehManageTaskPlanMapper;

    @Autowired(required = true)
    private ZjLzehManageTaskPlanItemService zjLzehManageTaskPlanItemService;

    @Autowired(required = true)
    private ZjLzehManageTaskPlanItemMapper zjLzehManageTaskPlanItemMapper;

    @Override
    public ResponseEntity getZjLzehManageTaskPlanListByCondition(ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        if (zjLzehManageTaskPlan == null) {
            zjLzehManageTaskPlan = new ZjLzehManageTaskPlan();
        }
        // 分页查询
        PageHelper.startPage(zjLzehManageTaskPlan.getPage(), zjLzehManageTaskPlan.getLimit());
        // 获取数据
        List<ZjLzehManageTaskPlan> zjLzehManageTaskPlanList = zjLzehManageTaskPlanMapper.selectByZjLzehManageTaskPlanList(zjLzehManageTaskPlan);
        // 得到分页信息
        PageInfo<ZjLzehManageTaskPlan> p = new PageInfo<>(zjLzehManageTaskPlanList);

        return repEntity.okList(zjLzehManageTaskPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehManageTaskPlanDetail(ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        if (zjLzehManageTaskPlan == null) {
            zjLzehManageTaskPlan = new ZjLzehManageTaskPlan();
        }
        // 获取数据
        ZjLzehManageTaskPlan dbZjLzehManageTaskPlan = zjLzehManageTaskPlanMapper.selectByPrimaryKey(zjLzehManageTaskPlan.getZjLzehManageTaskPlanId());
        // 数据存在
        if (dbZjLzehManageTaskPlan != null) {
            return repEntity.ok(dbZjLzehManageTaskPlan);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehManageTaskPlan(ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjLzehManageTaskPlan zjLzehManageTaskPlan1 = new ZjLzehManageTaskPlan();
        zjLzehManageTaskPlan1.setMonth(zjLzehManageTaskPlan.getMonth());
        List<ZjLzehManageTaskPlan> zjLzehManageTaskPlanList = zjLzehManageTaskPlanMapper.selectByZjLzehManageTaskPlanList(zjLzehManageTaskPlan1);
        if (zjLzehManageTaskPlanList.size() > 0) {
            return repEntity.layerMessage("no", "该月份数据已存在");
        }
        zjLzehManageTaskPlan.setZjLzehManageTaskPlanId(UuidUtil.generate());
        zjLzehManageTaskPlan.setCreateUserInfo(userKey, realName);
        int flag = zjLzehManageTaskPlanMapper.insert(zjLzehManageTaskPlan);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehManageTaskPlan);
        }
    }

    @Override
    public ResponseEntity updateZjLzehManageTaskPlan(ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehManageTaskPlan dbZjLzehManageTaskPlan = zjLzehManageTaskPlanMapper.selectByPrimaryKey(zjLzehManageTaskPlan.getZjLzehManageTaskPlanId());
        if (dbZjLzehManageTaskPlan != null && StrUtil.isNotEmpty(dbZjLzehManageTaskPlan.getZjLzehManageTaskPlanId())) {
            // 月份
            dbZjLzehManageTaskPlan.setMonth(zjLzehManageTaskPlan.getMonth());
            // 任务数
            dbZjLzehManageTaskPlan.setTaskQty(zjLzehManageTaskPlan.getTaskQty());
            // 备注
            dbZjLzehManageTaskPlan.setRemarks(zjLzehManageTaskPlan.getRemarks());
            // 排序
            dbZjLzehManageTaskPlan.setSort(zjLzehManageTaskPlan.getSort());
            // 共通
            dbZjLzehManageTaskPlan.setModifyUserInfo(userKey, realName);
            flag = zjLzehManageTaskPlanMapper.updateByPrimaryKey(dbZjLzehManageTaskPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zjLzehManageTaskPlan);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteUpdateZjLzehManageTaskPlan(List<ZjLzehManageTaskPlan> zjLzehManageTaskPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        for (ZjLzehManageTaskPlan zp :zjLzehManageTaskPlanList) {
            ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem =new ZjLzehManageTaskPlanItem();
            zjLzehManageTaskPlanItem.setZjLzehManageTaskPlanId(zp.getZjLzehManageTaskPlanId());
            List<ZjLzehManageTaskPlanItem> dbZjLzehManageTaskPlanItems=  zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem);
            zjLzehManageTaskPlanItemService.batchDeleteUpdateZjLzehManageTaskPlanItem(dbZjLzehManageTaskPlanItems);

        }
        if (zjLzehManageTaskPlanList != null && zjLzehManageTaskPlanList.size() > 0) {
            ZjLzehManageTaskPlan zjLzehManageTaskPlan = new ZjLzehManageTaskPlan();
            zjLzehManageTaskPlan.setModifyUserInfo(userKey, realName);
            flag = zjLzehManageTaskPlanMapper.batchDeleteUpdateZjLzehManageTaskPlan(zjLzehManageTaskPlanList, zjLzehManageTaskPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zjLzehManageTaskPlanList);
        }
    }

	@Override
	public ResponseEntity getZjLzehTaskNum(ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
		if (zjLzehManageTaskPlan == null) {
			zjLzehManageTaskPlan = new ZjLzehManageTaskPlan();
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        
        zjLzehManageTaskPlan.setCreateUser(userKey);
        zjLzehManageTaskPlan.setModifyUser(userId);
        
        ZjLzehManageTaskPlan dbZjLzehManageTaskPlan = zjLzehManageTaskPlanMapper.getZjLzehTaskNum(zjLzehManageTaskPlan);
        
		return repEntity.ok(dbZjLzehManageTaskPlan);
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
