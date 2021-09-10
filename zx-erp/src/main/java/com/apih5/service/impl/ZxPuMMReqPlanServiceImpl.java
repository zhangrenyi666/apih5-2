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
import com.apih5.mybatis.dao.ZxPuMMReqPlanMapper;
import com.apih5.mybatis.pojo.ZxPuMMReqPlan;
import com.apih5.service.ZxPuMMReqPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxPuMMReqPlanService")
public class ZxPuMMReqPlanServiceImpl implements ZxPuMMReqPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxPuMMReqPlanMapper zxPuMMReqPlanMapper;

    @Override
    public ResponseEntity getZxPuMMReqPlanListByCondition(ZxPuMMReqPlan zxPuMMReqPlan) {
        if (zxPuMMReqPlan == null) {
            zxPuMMReqPlan = new ZxPuMMReqPlan();
        }
        // 分页查询
        PageHelper.startPage(zxPuMMReqPlan.getPage(),zxPuMMReqPlan.getLimit());
        // 获取数据
        List<ZxPuMMReqPlan> zxPuMMReqPlanList = zxPuMMReqPlanMapper.selectByZxPuMMReqPlanList(zxPuMMReqPlan);
        // 得到分页信息
        PageInfo<ZxPuMMReqPlan> p = new PageInfo<>(zxPuMMReqPlanList);

        return repEntity.okList(zxPuMMReqPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxPuMMReqPlanDetail(ZxPuMMReqPlan zxPuMMReqPlan) {
        if (zxPuMMReqPlan == null) {
            zxPuMMReqPlan = new ZxPuMMReqPlan();
        }
        // 获取数据
        ZxPuMMReqPlan dbZxPuMMReqPlan = zxPuMMReqPlanMapper.selectByPrimaryKey(zxPuMMReqPlan.getZxPuMMReqPlanId());
        // 数据存在
        if (dbZxPuMMReqPlan != null) {
            return repEntity.ok(dbZxPuMMReqPlan);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxPuMMReqPlan(ZxPuMMReqPlan zxPuMMReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxPuMMReqPlan.setZxPuMMReqPlanId(UuidUtil.generate());
        zxPuMMReqPlan.setCreateUserInfo(userKey, realName);
        int flag = zxPuMMReqPlanMapper.insert(zxPuMMReqPlan);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxPuMMReqPlan);
        }
    }

    @Override
    public ResponseEntity updateZxPuMMReqPlan(ZxPuMMReqPlan zxPuMMReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxPuMMReqPlan dbZxPuMMReqPlan = zxPuMMReqPlanMapper.selectByPrimaryKey(zxPuMMReqPlan.getZxPuMMReqPlanId());
        if (dbZxPuMMReqPlan != null && StrUtil.isNotEmpty(dbZxPuMMReqPlan.getZxPuMMReqPlanId())) {
           // 项目名称
           dbZxPuMMReqPlan.setProjectName(zxPuMMReqPlan.getProjectName());
           // 分部分项
           dbZxPuMMReqPlan.setCbsName(zxPuMMReqPlan.getCbsName());
           // 物资类别
           dbZxPuMMReqPlan.setResCateName(zxPuMMReqPlan.getResCateName());
           // 物资编码
           dbZxPuMMReqPlan.setResCode(zxPuMMReqPlan.getResCode());
           // 物资名称
           dbZxPuMMReqPlan.setResName(zxPuMMReqPlan.getResName());
           // 计量单位
           dbZxPuMMReqPlan.setUnit(zxPuMMReqPlan.getUnit());
           // 规格型号
           dbZxPuMMReqPlan.setSpec(zxPuMMReqPlan.getSpec());
           // 单价
           dbZxPuMMReqPlan.setPrice(zxPuMMReqPlan.getPrice());
           // 本月物资需用量
           dbZxPuMMReqPlan.setPurNum(zxPuMMReqPlan.getPurNum());
           // 金额
           dbZxPuMMReqPlan.setTotalMoney(zxPuMMReqPlan.getTotalMoney());
           // 下月预估用量
           dbZxPuMMReqPlan.setNextMonthNum(zxPuMMReqPlan.getNextMonthNum());
           // 备注
           dbZxPuMMReqPlan.setRemarks(zxPuMMReqPlan.getRemarks());
           // 排序
           dbZxPuMMReqPlan.setSort(zxPuMMReqPlan.getSort());
           // 共通
           dbZxPuMMReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxPuMMReqPlanMapper.updateByPrimaryKey(dbZxPuMMReqPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxPuMMReqPlan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxPuMMReqPlan(List<ZxPuMMReqPlan> zxPuMMReqPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxPuMMReqPlanList != null && zxPuMMReqPlanList.size() > 0) {
           ZxPuMMReqPlan zxPuMMReqPlan = new ZxPuMMReqPlan();
           zxPuMMReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxPuMMReqPlanMapper.batchDeleteUpdateZxPuMMReqPlan(zxPuMMReqPlanList, zxPuMMReqPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxPuMMReqPlanList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxPuMMReqPlan> ureportZxPuMMReqPlan(ZxPuMMReqPlan zxPuMMReqPlan) {
    	if(zxPuMMReqPlan.getResID().equals("null")) {
    		zxPuMMReqPlan.setResID("");
    	}
    	if(zxPuMMReqPlan.getResCateID().equals("null")) {
    		zxPuMMReqPlan.setResCateID("");
    	}
    	if(zxPuMMReqPlan.getType().equals("null")) {
    		zxPuMMReqPlan.setType("");
    	}
    	List<ZxPuMMReqPlan> zxPuMMReqPlanList = zxPuMMReqPlanMapper.selectZxPuMMReqPlan(zxPuMMReqPlan);
    	return zxPuMMReqPlanList;
    }
    
    @Override
    public ResponseEntity ureportZxPuMMReqPlanIdle(ZxPuMMReqPlan zxPuMMReqPlan) {
    	
    	 if (zxPuMMReqPlan == null) {
             zxPuMMReqPlan = new ZxPuMMReqPlan();
         }
         // 分页查询
         PageHelper.startPage(zxPuMMReqPlan.getPage(),zxPuMMReqPlan.getLimit());
         // 获取数据
         List<ZxPuMMReqPlan> zxPuMMReqPlanList = zxPuMMReqPlanMapper.selectZxPuMMReqPlan(zxPuMMReqPlan);
         // 得到分页信息
         PageInfo<ZxPuMMReqPlan> p = new PageInfo<>(zxPuMMReqPlanList);

         return repEntity.okList(zxPuMMReqPlanList, p.getTotal());
    }
}
