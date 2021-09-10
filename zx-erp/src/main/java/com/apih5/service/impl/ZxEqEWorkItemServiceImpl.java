package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEWorkItemMapper;
import com.apih5.mybatis.pojo.ZxEqEWorkItem;
import com.apih5.service.ZxEqEWorkItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEWorkItemService")
public class ZxEqEWorkItemServiceImpl implements ZxEqEWorkItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEWorkItemMapper zxEqEWorkItemMapper;

    @Override
    public ResponseEntity getZxEqEWorkItemListByCondition(ZxEqEWorkItem zxEqEWorkItem) {
        if (zxEqEWorkItem == null) {
            zxEqEWorkItem = new ZxEqEWorkItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqEWorkItem.getPage(),zxEqEWorkItem.getLimit());
        // 获取数据
        List<ZxEqEWorkItem> zxEqEWorkItemList = zxEqEWorkItemMapper.selectByZxEqEWorkItemList(zxEqEWorkItem);
        // 得到分页信息
        PageInfo<ZxEqEWorkItem> p = new PageInfo<>(zxEqEWorkItemList);

        return repEntity.okList(zxEqEWorkItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEWorkItemDetails(ZxEqEWorkItem zxEqEWorkItem) {
        if (zxEqEWorkItem == null) {
            zxEqEWorkItem = new ZxEqEWorkItem();
        }
        // 获取数据
        ZxEqEWorkItem dbZxEqEWorkItem = zxEqEWorkItemMapper.selectByPrimaryKey(zxEqEWorkItem.getId());
        // 数据存在
        if (dbZxEqEWorkItem != null) {
            return repEntity.ok(dbZxEqEWorkItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEWorkItem(ZxEqEWorkItem zxEqEWorkItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEWorkItem.setId(UuidUtil.generate());
        zxEqEWorkItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqEWorkItemMapper.insert(zxEqEWorkItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEWorkItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqEWorkItem(ZxEqEWorkItem zxEqEWorkItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEWorkItem dbzxEqEWorkItem = zxEqEWorkItemMapper.selectByPrimaryKey(zxEqEWorkItem.getId());
        if (dbzxEqEWorkItem != null && StrUtil.isNotEmpty(dbzxEqEWorkItem.getId())) {
           // 日期
           dbzxEqEWorkItem.setWorkDate(zxEqEWorkItem.getWorkDate());
           // 工作地点
           dbzxEqEWorkItem.setWorkAddress(zxEqEWorkItem.getWorkAddress());
           // 工作内容
           dbzxEqEWorkItem.setWorkContext(zxEqEWorkItem.getWorkContext());
           // 本日运转
           dbzxEqEWorkItem.setTodayMiles(zxEqEWorkItem.getTodayMiles());
           // 累计运转
           dbzxEqEWorkItem.setTotalMiles(zxEqEWorkItem.getTotalMiles());
           // 行驶里程（千米）
           dbzxEqEWorkItem.setLoadMiles(zxEqEWorkItem.getLoadMiles());
           // 空驶里程（千米）
           dbzxEqEWorkItem.setNullMiles(zxEqEWorkItem.getNullMiles());
           // 汽油消耗（升）
           dbzxEqEWorkItem.setGasoline(zxEqEWorkItem.getGasoline());
           // 柴油消耗（升）
           dbzxEqEWorkItem.setDiesel(zxEqEWorkItem.getDiesel());
           // 机油消耗
           dbzxEqEWorkItem.setEngineOil(zxEqEWorkItem.getEngineOil());
           // 备注
           dbzxEqEWorkItem.setRemark(zxEqEWorkItem.getRemark());
           // 
           dbzxEqEWorkItem.setEquipID(zxEqEWorkItem.getEquipID());
           // 
           dbzxEqEWorkItem.setMainID(zxEqEWorkItem.getMainID());
           // 使用人
           dbzxEqEWorkItem.setOpertaor(zxEqEWorkItem.getOpertaor());
           // 运转台时
           dbzxEqEWorkItem.setRunHour(zxEqEWorkItem.getRunHour());
           // 挺直台时
           dbzxEqEWorkItem.setStopHour(zxEqEWorkItem.getStopHour());
           // 日历天数
           dbzxEqEWorkItem.setCalendarNumDay(zxEqEWorkItem.getCalendarNumDay());
           // 运转台日
           dbzxEqEWorkItem.setRunDay(zxEqEWorkItem.getRunDay());
           // 完好台日
           dbzxEqEWorkItem.setWellDays(zxEqEWorkItem.getWellDays());
           // 电消耗（度）
           dbzxEqEWorkItem.setConsumption(zxEqEWorkItem.getConsumption());
           // 共通
           dbzxEqEWorkItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEWorkItemMapper.updateByPrimaryKey(dbzxEqEWorkItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEWorkItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEWorkItem(List<ZxEqEWorkItem> zxEqEWorkItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEWorkItemList != null && zxEqEWorkItemList.size() > 0) {
           ZxEqEWorkItem zxEqEWorkItem = new ZxEqEWorkItem();
           zxEqEWorkItem.setModifyUserInfo(userKey, realName);
           flag = zxEqEWorkItemMapper.batchDeleteUpdateZxEqEWorkItem(zxEqEWorkItemList, zxEqEWorkItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEWorkItemList);
        }
    }

    @Override
    public List<ZxEqEWorkItem> ureportZxEqEWorkItemListForCar(ZxEqEWorkItem zxEqEWorkItem) {
    	if (zxEqEWorkItem == null) {
    		zxEqEWorkItem = new ZxEqEWorkItem();
    	}
    	// 获取数据
    	List<ZxEqEWorkItem> zxEqEWorkItemList = zxEqEWorkItemMapper.ureportZxEqEWorkItemListForCar(zxEqEWorkItem);
    	return zxEqEWorkItemList;
    }
}
