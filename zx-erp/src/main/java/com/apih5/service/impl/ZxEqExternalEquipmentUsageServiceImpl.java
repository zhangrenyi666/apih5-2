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
import com.apih5.mybatis.dao.ZxEqExternalEquipmentUsageMapper;
import com.apih5.mybatis.pojo.ZxEqExternalEquipmentUsage;
import com.apih5.service.ZxEqExternalEquipmentUsageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqExternalEquipmentUsageService")
public class ZxEqExternalEquipmentUsageServiceImpl implements ZxEqExternalEquipmentUsageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqExternalEquipmentUsageMapper zxEqExternalEquipmentUsageMapper;

    @Override
    public ResponseEntity getZxEqExternalEquipmentUsageListByCondition(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        if (zxEqExternalEquipmentUsage == null) {
            zxEqExternalEquipmentUsage = new ZxEqExternalEquipmentUsage();
        }
        // 分页查询
        PageHelper.startPage(zxEqExternalEquipmentUsage.getPage(),zxEqExternalEquipmentUsage.getLimit());
        // 获取数据
        List<ZxEqExternalEquipmentUsage> zxEqExternalEquipmentUsageList = zxEqExternalEquipmentUsageMapper.selectByZxEqExternalEquipmentUsageList(zxEqExternalEquipmentUsage);
        // 得到分页信息
        PageInfo<ZxEqExternalEquipmentUsage> p = new PageInfo<>(zxEqExternalEquipmentUsageList);

        return repEntity.okList(zxEqExternalEquipmentUsageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqExternalEquipmentUsageDetail(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        if (zxEqExternalEquipmentUsage == null) {
            zxEqExternalEquipmentUsage = new ZxEqExternalEquipmentUsage();
        }
        // 获取数据
        ZxEqExternalEquipmentUsage dbZxEqExternalEquipmentUsage = zxEqExternalEquipmentUsageMapper.selectByPrimaryKey(zxEqExternalEquipmentUsage.getZxEqExternalEquipmentUsageId());
        // 数据存在
        if (dbZxEqExternalEquipmentUsage != null) {
            return repEntity.ok(dbZxEqExternalEquipmentUsage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqExternalEquipmentUsage.setZxEqExternalEquipmentUsageId(UuidUtil.generate());
        zxEqExternalEquipmentUsage.setCreateUserInfo(userKey, realName);
        int flag = zxEqExternalEquipmentUsageMapper.insert(zxEqExternalEquipmentUsage);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqExternalEquipmentUsage);
        }
    }

    @Override
    public ResponseEntity updateZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqExternalEquipmentUsage dbZxEqExternalEquipmentUsage = zxEqExternalEquipmentUsageMapper.selectByPrimaryKey(zxEqExternalEquipmentUsage.getZxEqExternalEquipmentUsageId());
        if (dbZxEqExternalEquipmentUsage != null && StrUtil.isNotEmpty(dbZxEqExternalEquipmentUsage.getZxEqExternalEquipmentUsageId())) {
           // 组织名称
           dbZxEqExternalEquipmentUsage.setOrgName(zxEqExternalEquipmentUsage.getOrgName());
           // 项目名称
           dbZxEqExternalEquipmentUsage.setProjectName(zxEqExternalEquipmentUsage.getProjectName());
           // 设备名称
           dbZxEqExternalEquipmentUsage.setEquipName(zxEqExternalEquipmentUsage.getEquipName());
           // 设备型号
           dbZxEqExternalEquipmentUsage.setModel(zxEqExternalEquipmentUsage.getModel());
           // 设备规格
           dbZxEqExternalEquipmentUsage.setSpec(zxEqExternalEquipmentUsage.getSpec());
           // 设备功率
           dbZxEqExternalEquipmentUsage.setPower(zxEqExternalEquipmentUsage.getPower());
           // 生产厂家
           dbZxEqExternalEquipmentUsage.setOutfactory(zxEqExternalEquipmentUsage.getOutfactory());
           // 出厂日期
           dbZxEqExternalEquipmentUsage.setOutfactoryDate(zxEqExternalEquipmentUsage.getOutfactoryDate());
           // 原值（美元）
           dbZxEqExternalEquipmentUsage.setOrginalValue(zxEqExternalEquipmentUsage.getOrginalValue());
           // 使用地点
           dbZxEqExternalEquipmentUsage.setPlace(zxEqExternalEquipmentUsage.getPlace());
           // 租赁期限
           dbZxEqExternalEquipmentUsage.setLeaseLimit(zxEqExternalEquipmentUsage.getLeaseLimit());
           // 租赁金额
           dbZxEqExternalEquipmentUsage.setLeaseprice(zxEqExternalEquipmentUsage.getLeaseprice());
           // 机主姓名
           dbZxEqExternalEquipmentUsage.setMaster(zxEqExternalEquipmentUsage.getMaster());
           // 备注
           dbZxEqExternalEquipmentUsage.setRemarks(zxEqExternalEquipmentUsage.getRemarks());
           // 排序
           dbZxEqExternalEquipmentUsage.setSort(zxEqExternalEquipmentUsage.getSort());
           // 共通
           dbZxEqExternalEquipmentUsage.setModifyUserInfo(userKey, realName);
           flag = zxEqExternalEquipmentUsageMapper.updateByPrimaryKey(dbZxEqExternalEquipmentUsage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqExternalEquipmentUsage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqExternalEquipmentUsage(List<ZxEqExternalEquipmentUsage> zxEqExternalEquipmentUsageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqExternalEquipmentUsageList != null && zxEqExternalEquipmentUsageList.size() > 0) {
           ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage = new ZxEqExternalEquipmentUsage();
           zxEqExternalEquipmentUsage.setModifyUserInfo(userKey, realName);
           flag = zxEqExternalEquipmentUsageMapper.batchDeleteUpdateZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsageList, zxEqExternalEquipmentUsage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqExternalEquipmentUsageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public List<ZxEqExternalEquipmentUsage> ureportZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
    	if (zxEqExternalEquipmentUsage == null) {
            zxEqExternalEquipmentUsage = new ZxEqExternalEquipmentUsage();
        }
    	List<ZxEqExternalEquipmentUsage> zxEqExternalEquipmentUsageList = zxEqExternalEquipmentUsageMapper.selectZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsage);
    	return zxEqExternalEquipmentUsageList;
    }
    
    @Override
    public ResponseEntity ureportZxEqExternalEquipmentUsageIdle(ZxEqExternalEquipmentUsage zxEqExternalEquipmentUsage) {
        if (zxEqExternalEquipmentUsage == null) {
            zxEqExternalEquipmentUsage = new ZxEqExternalEquipmentUsage();
        }
        // 分页查询
        PageHelper.startPage(zxEqExternalEquipmentUsage.getPage(),zxEqExternalEquipmentUsage.getLimit());
        // 获取数据
        List<ZxEqExternalEquipmentUsage> zxEqExternalEquipmentUsageList = zxEqExternalEquipmentUsageMapper.selectZxEqExternalEquipmentUsage(zxEqExternalEquipmentUsage);
        // 得到分页信息
        PageInfo<ZxEqExternalEquipmentUsage> p = new PageInfo<>(zxEqExternalEquipmentUsageList);

        return repEntity.okList(zxEqExternalEquipmentUsageList, p.getTotal());
    }
}
