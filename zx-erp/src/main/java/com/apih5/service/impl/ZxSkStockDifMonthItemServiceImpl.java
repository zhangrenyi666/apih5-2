package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockDifMonthItemMapper;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import com.apih5.service.ZxSkStockDifMonthItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockDifMonthItemService")
public class ZxSkStockDifMonthItemServiceImpl implements ZxSkStockDifMonthItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockDifMonthItemMapper zxSkStockDifMonthItemMapper;

    @Override
    public ResponseEntity getZxSkStockDifMonthItemListByCondition(ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        if (zxSkStockDifMonthItem == null) {
            zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockDifMonthItem.getPage(),zxSkStockDifMonthItem.getLimit());
        // 获取数据
        List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList = zxSkStockDifMonthItemMapper.selectByZxSkStockDifMonthItemList(zxSkStockDifMonthItem);
        // 得到分页信息
        PageInfo<ZxSkStockDifMonthItem> p = new PageInfo<>(zxSkStockDifMonthItemList);

        return repEntity.okList(zxSkStockDifMonthItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockDifMonthItemDetail(ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        if (zxSkStockDifMonthItem == null) {
            zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
        }
        // 获取数据
        ZxSkStockDifMonthItem dbZxSkStockDifMonthItem = zxSkStockDifMonthItemMapper.selectByPrimaryKey(zxSkStockDifMonthItem.getZxSkStockDifMonthItemId());
        // 数据存在
        if (dbZxSkStockDifMonthItem != null) {
            return repEntity.ok(dbZxSkStockDifMonthItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockDifMonthItem(ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockDifMonthItem.setZxSkStockDifMonthItemId(UuidUtil.generate());
        zxSkStockDifMonthItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockDifMonthItemMapper.insert(zxSkStockDifMonthItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStockDifMonthItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockDifMonthItem(ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockDifMonthItem dbZxSkStockDifMonthItem = zxSkStockDifMonthItemMapper.selectByPrimaryKey(zxSkStockDifMonthItem.getZxSkStockDifMonthItemId());
        if (dbZxSkStockDifMonthItem != null && StrUtil.isNotEmpty(dbZxSkStockDifMonthItem.getZxSkStockDifMonthItemId())) {
           // 主表ID
           dbZxSkStockDifMonthItem.setMainID(zxSkStockDifMonthItem.getMainID());
           // 物资ID
           dbZxSkStockDifMonthItem.setResID(zxSkStockDifMonthItem.getResID());
           // 物资编号
           dbZxSkStockDifMonthItem.setResCode(zxSkStockDifMonthItem.getResCode());
           // 物资名称
           dbZxSkStockDifMonthItem.setResName(zxSkStockDifMonthItem.getResName());
           // 规格型号
           dbZxSkStockDifMonthItem.setSpec(zxSkStockDifMonthItem.getSpec());
           // 单位
           dbZxSkStockDifMonthItem.setUnit(zxSkStockDifMonthItem.getUnit());
           // 本月实际采购加权平均价
           dbZxSkStockDifMonthItem.setThisPrice(zxSkStockDifMonthItem.getThisPrice());
           // 本月实际消耗量
           dbZxSkStockDifMonthItem.setThisQty(zxSkStockDifMonthItem.getThisQty());
           // 设计用量
           dbZxSkStockDifMonthItem.setDesignQty(zxSkStockDifMonthItem.getDesignQty());
           // 局定额损耗率(%)
           dbZxSkStockDifMonthItem.setSunHaoLv(zxSkStockDifMonthItem.getSunHaoLv());
           // 物资类别ID
           dbZxSkStockDifMonthItem.setCategoryID(zxSkStockDifMonthItem.getCategoryID());
           // 物资类别
           dbZxSkStockDifMonthItem.setCategoryName(zxSkStockDifMonthItem.getCategoryName());
           // 类型
           dbZxSkStockDifMonthItem.setMtType(zxSkStockDifMonthItem.getMtType());
           // 备注
           dbZxSkStockDifMonthItem.setRemarks(zxSkStockDifMonthItem.getRemarks());
           // 排序
           dbZxSkStockDifMonthItem.setSort(zxSkStockDifMonthItem.getSort());
           // 共通
           dbZxSkStockDifMonthItem.setModifyUserInfo(userKey, realName);
           flag = zxSkStockDifMonthItemMapper.updateByPrimaryKey(dbZxSkStockDifMonthItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkStockDifMonthItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockDifMonthItem(List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockDifMonthItemList != null && zxSkStockDifMonthItemList.size() > 0) {
           ZxSkStockDifMonthItem zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
           zxSkStockDifMonthItem.setModifyUserInfo(userKey, realName);
           flag = zxSkStockDifMonthItemMapper.batchDeleteUpdateZxSkStockDifMonthItem(zxSkStockDifMonthItemList, zxSkStockDifMonthItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkStockDifMonthItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public List<ZxSkStockDifMonthItem> getStockDifMonthForm(){
        return zxSkStockDifMonthItemMapper.getStockDifMonthForm();
    }

    @Override
    public List<ZxSkStockDifMonthItem> getStockDifJiDuForm() {
        return zxSkStockDifMonthItemMapper.getStockDifJiDuForm();
    }
    
    @Override
    public List<ZxSkStockDifMonthItem> getStockDifMonthMaterialCategory() {
        return zxSkStockDifMonthItemMapper.getStockDifMonthMaterialCategory();
    }
    
    @Override
    public ResponseEntity getReceivingDynamicItem(ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
    	 if (zxSkStockDifMonthItem == null) {
             zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
         }
    	List<ZxSkStockDifMonthItem> ZxSkStockDifMonthItem = zxSkStockDifMonthItemMapper.getReceivingDynamic(zxSkStockDifMonthItem);
        for(ZxSkStockDifMonthItem zxSkStockDifMonthItem1 : ZxSkStockDifMonthItem) {
        	zxSkStockDifMonthItem1.setInbusDateStr(DateUtil.formatDateTime(zxSkStockDifMonthItem1.getInbusDate()));
        }
     // 得到分页信息
        PageInfo<ZxSkStockDifMonthItem> p = new PageInfo<>(ZxSkStockDifMonthItem);
    	return repEntity.okList(ZxSkStockDifMonthItem, p.getTotal());
    }
    
    @Override
    public List<ZxSkStockDifMonthItem> getReceivingDynamic(ZxSkStockDifMonthItem zxSkStockDifMonthItem) {
    	 if (zxSkStockDifMonthItem == null) {
             zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
         }
    	List<ZxSkStockDifMonthItem> ZxSkStockDifMonthItem = zxSkStockDifMonthItemMapper.getReceivingDynamic(zxSkStockDifMonthItem);
        for(ZxSkStockDifMonthItem zxSkStockDifMonthItem2 : ZxSkStockDifMonthItem) {
        	zxSkStockDifMonthItem2.setInbusDateStr(DateUtil.formatDateTime(zxSkStockDifMonthItem2.getInbusDate()));
        }
     // 得到分页信息
        PageInfo<ZxSkStockDifMonthItem> p = new PageInfo<>(ZxSkStockDifMonthItem);
    	return ZxSkStockDifMonthItem;
    }

    
    @Override
    public List<ZxSkStockDifMonthItem> getResMoveMonthMP() {
    	List<ZxSkStockDifMonthItem> ZxSkStockDifMonthItem = zxSkStockDifMonthItemMapper.getResMoveMonthMP();
        for(ZxSkStockDifMonthItem zxSkStockDifMonthItem : ZxSkStockDifMonthItem) {
        	zxSkStockDifMonthItem.setInbusDateStr(DateUtil.formatDateTime(zxSkStockDifMonthItem.getInbusDate()));
        }
   
    	return ZxSkStockDifMonthItem;
    }
    
    @Override
    public ResponseEntity getResMoveMonthMPItem() {
    	List<ZxSkStockDifMonthItem> ZxSkStockDifMonthItem = zxSkStockDifMonthItemMapper.getResMoveMonthMP();
        for(ZxSkStockDifMonthItem zxSkStockDifMonthItem : ZxSkStockDifMonthItem) {
        	zxSkStockDifMonthItem.setInbusDateStr(DateUtil.formatDateTime(zxSkStockDifMonthItem.getInbusDate()));
        }
     // 得到分页信息
        PageInfo<ZxSkStockDifMonthItem> p = new PageInfo<>(ZxSkStockDifMonthItem);
    	return repEntity.okList(ZxSkStockDifMonthItem, p.getTotal());
    }


    @Override
    public ResponseEntity getStockDifMonthFormList(Date period) {

        // 获取数据
        List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList = zxSkStockDifMonthItemMapper.getStockDifMonthForm();
       long total= zxSkStockDifMonthItemList.size();

        return repEntity.okList(zxSkStockDifMonthItemList,total);
    }

    @Override
    public ResponseEntity getStockDifJiDuFormList(Date period) {

        // 获取数据
        List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList = zxSkStockDifMonthItemMapper.getStockDifJiDuForm();
        long total= zxSkStockDifMonthItemList.size();

        return repEntity.okList(zxSkStockDifMonthItemList,total);
    }

}
