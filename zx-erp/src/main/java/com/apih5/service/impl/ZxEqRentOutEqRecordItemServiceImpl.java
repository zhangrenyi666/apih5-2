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
import com.apih5.mybatis.dao.ZxEqRentOutEqRecordItemMapper;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecordItem;
import com.apih5.service.ZxEqRentOutEqRecordItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqRentOutEqRecordItemService")
public class ZxEqRentOutEqRecordItemServiceImpl implements ZxEqRentOutEqRecordItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqRentOutEqRecordItemMapper zxEqRentOutEqRecordItemMapper;

    @Override
    public ResponseEntity getZxEqRentOutEqRecordItemListByCondition(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        if (zxEqRentOutEqRecordItem == null) {
            zxEqRentOutEqRecordItem = new ZxEqRentOutEqRecordItem();
        }
        // 分页查询
        PageHelper.startPage(zxEqRentOutEqRecordItem.getPage(),zxEqRentOutEqRecordItem.getLimit());
        // 获取数据
        List<ZxEqRentOutEqRecordItem> zxEqRentOutEqRecordItemList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(zxEqRentOutEqRecordItem);
        // 得到分页信息
        PageInfo<ZxEqRentOutEqRecordItem> p = new PageInfo<>(zxEqRentOutEqRecordItemList);

        return repEntity.okList(zxEqRentOutEqRecordItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqRentOutEqRecordItemDetails(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        if (zxEqRentOutEqRecordItem == null) {
            zxEqRentOutEqRecordItem = new ZxEqRentOutEqRecordItem();
        }
        // 获取数据
        ZxEqRentOutEqRecordItem dbZxEqRentOutEqRecordItem = zxEqRentOutEqRecordItemMapper.selectByPrimaryKey(zxEqRentOutEqRecordItem.getId());
        // 数据存在
        if (dbZxEqRentOutEqRecordItem != null) {
            return repEntity.ok(dbZxEqRentOutEqRecordItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqRentOutEqRecordItem(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqRentOutEqRecordItem.setId(UuidUtil.generate());
        zxEqRentOutEqRecordItem.setCreateUserInfo(userKey, realName);
        int flag = zxEqRentOutEqRecordItemMapper.insert(zxEqRentOutEqRecordItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqRentOutEqRecordItem);
        }
    }

    @Override
    public ResponseEntity updateZxEqRentOutEqRecordItem(ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqRentOutEqRecordItem dbzxEqRentOutEqRecordItem = zxEqRentOutEqRecordItemMapper.selectByPrimaryKey(zxEqRentOutEqRecordItem.getId());
        if (dbzxEqRentOutEqRecordItem != null && StrUtil.isNotEmpty(dbzxEqRentOutEqRecordItem.getId())) {
           // 主表id
           dbzxEqRentOutEqRecordItem.setBillID(zxEqRentOutEqRecordItem.getBillID());
           // 日期
           dbzxEqRentOutEqRecordItem.setUseDate(zxEqRentOutEqRecordItem.getUseDate());
           // 工作内容
           dbzxEqRentOutEqRecordItem.setContent(zxEqRentOutEqRecordItem.getContent());
           // 使用地点
           dbzxEqRentOutEqRecordItem.setPlace(zxEqRentOutEqRecordItem.getPlace());
           // 完成工程量单位
           dbzxEqRentOutEqRecordItem.setUnit(zxEqRentOutEqRecordItem.getUnit());
           // 完成工程量数量
           dbzxEqRentOutEqRecordItem.setQty(zxEqRentOutEqRecordItem.getQty());
           // 里程数
           dbzxEqRentOutEqRecordItem.setMile(zxEqRentOutEqRecordItem.getMile());
           // 运转台时
           dbzxEqRentOutEqRecordItem.setActualQty(zxEqRentOutEqRecordItem.getActualQty());
           // 待工停滞台时
           dbzxEqRentOutEqRecordItem.setWaitQty(zxEqRentOutEqRecordItem.getWaitQty());
           // 气候停滞台时
           dbzxEqRentOutEqRecordItem.setWeatherQty(zxEqRentOutEqRecordItem.getWeatherQty());
           // 故障停滞台时
           dbzxEqRentOutEqRecordItem.setProblemQty(zxEqRentOutEqRecordItem.getProblemQty());
           // 汽油消耗（升）
           dbzxEqRentOutEqRecordItem.setGasQty(zxEqRentOutEqRecordItem.getGasQty());
           // 柴油消耗（升）
           dbzxEqRentOutEqRecordItem.setDervQty(zxEqRentOutEqRecordItem.getDervQty());
           // 煤消耗（千克）
           dbzxEqRentOutEqRecordItem.setCoalQty(zxEqRentOutEqRecordItem.getCoalQty());
           // 编制时间
           dbzxEqRentOutEqRecordItem.setEditTime(zxEqRentOutEqRecordItem.getEditTime());
           // 司机名称
           dbzxEqRentOutEqRecordItem.setDriverName(zxEqRentOutEqRecordItem.getDriverName());
           // 电消耗（度）
           dbzxEqRentOutEqRecordItem.setConsumption(zxEqRentOutEqRecordItem.getConsumption());
           // 日历天数
           dbzxEqRentOutEqRecordItem.setCalendarNumDay(zxEqRentOutEqRecordItem.getCalendarNumDay());
           // 完好台日
           dbzxEqRentOutEqRecordItem.setWellDays(zxEqRentOutEqRecordItem.getWellDays());
           // 运转台日
           dbzxEqRentOutEqRecordItem.setRunDay(zxEqRentOutEqRecordItem.getRunDay());
           // 
           dbzxEqRentOutEqRecordItem.setProjectName(zxEqRentOutEqRecordItem.getProjectName());
           // 共通
           dbzxEqRentOutEqRecordItem.setModifyUserInfo(userKey, realName);
           flag = zxEqRentOutEqRecordItemMapper.updateByPrimaryKey(dbzxEqRentOutEqRecordItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqRentOutEqRecordItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecordItem(List<ZxEqRentOutEqRecordItem> zxEqRentOutEqRecordItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqRentOutEqRecordItemList != null && zxEqRentOutEqRecordItemList.size() > 0) {
           ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem = new ZxEqRentOutEqRecordItem();
           zxEqRentOutEqRecordItem.setModifyUserInfo(userKey, realName);
           flag = zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(zxEqRentOutEqRecordItemList, zxEqRentOutEqRecordItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqRentOutEqRecordItemList);
        }
    }
}
