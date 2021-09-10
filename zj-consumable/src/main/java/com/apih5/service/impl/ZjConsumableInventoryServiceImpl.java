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
import com.apih5.mybatis.dao.ZjConsumableInventoryMapper;
import com.apih5.mybatis.pojo.ZjConsumableInventory;
import com.apih5.service.ZjConsumableInventoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjConsumableInventoryService")
public class ZjConsumableInventoryServiceImpl implements ZjConsumableInventoryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjConsumableInventoryMapper zjConsumableInventoryMapper;

    @Override
    public ResponseEntity getZjConsumableInventoryListByCondition(ZjConsumableInventory zjConsumableInventory) {
        if (zjConsumableInventory == null) {
            zjConsumableInventory = new ZjConsumableInventory();
        }
        // 分页查询
        PageHelper.startPage(zjConsumableInventory.getPage(),zjConsumableInventory.getLimit());
        // 获取数据
        List<ZjConsumableInventory> zjConsumableInventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(zjConsumableInventory);
        // 得到分页信息
        PageInfo<ZjConsumableInventory> p = new PageInfo<>(zjConsumableInventoryList);

        return repEntity.okList(zjConsumableInventoryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjConsumableInventoryDetails(ZjConsumableInventory zjConsumableInventory) {
        if (zjConsumableInventory == null) {
            zjConsumableInventory = new ZjConsumableInventory();
        }
        // 获取数据
        ZjConsumableInventory dbZjConsumableInventory = zjConsumableInventoryMapper.selectByPrimaryKey(zjConsumableInventory.getInventoryId());
        // 数据存在
        if (dbZjConsumableInventory != null) {
            return repEntity.ok(dbZjConsumableInventory);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjConsumableInventory(ZjConsumableInventory zjConsumableInventory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjConsumableInventory.setInventoryId(UuidUtil.generate());
        zjConsumableInventory.setCreateUserInfo(userKey, realName);
        int flag = zjConsumableInventoryMapper.insert(zjConsumableInventory);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjConsumableInventory);
        }
    }

    @Override
    public ResponseEntity updateZjConsumableInventory(ZjConsumableInventory zjConsumableInventory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumableInventory dbzjConsumableInventory = zjConsumableInventoryMapper.selectByPrimaryKey(zjConsumableInventory.getInventoryId());
        if (dbzjConsumableInventory != null && StrUtil.isNotEmpty(dbzjConsumableInventory.getInventoryId())) {
           // 耗材设置id
           dbzjConsumableInventory.setSetId(zjConsumableInventory.getSetId());
           // 类别
           dbzjConsumableInventory.setCategory(zjConsumableInventory.getCategory());
           // 品牌
           dbzjConsumableInventory.setBrand(zjConsumableInventory.getBrand());
           // 型号
           dbzjConsumableInventory.setModel(zjConsumableInventory.getModel());
           // 颜色
           dbzjConsumableInventory.setColour(zjConsumableInventory.getColour());
           // 当前库存量
           dbzjConsumableInventory.setNowNum(zjConsumableInventory.getNowNum());
           // 总入库量
           dbzjConsumableInventory.setTotalInNum(zjConsumableInventory.getTotalInNum());
           // 总领用量
           dbzjConsumableInventory.setTotalUseNum(zjConsumableInventory.getTotalUseNum());
           // 共通
           dbzjConsumableInventory.setModifyUserInfo(userKey, realName);
           flag = zjConsumableInventoryMapper.updateByPrimaryKey(dbzjConsumableInventory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumableInventory);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjConsumableInventory(List<ZjConsumableInventory> zjConsumableInventoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjConsumableInventoryList != null && zjConsumableInventoryList.size() > 0) {
           ZjConsumableInventory zjConsumableInventory = new ZjConsumableInventory();
           zjConsumableInventory.setModifyUserInfo(userKey, realName);
           flag = zjConsumableInventoryMapper.batchDeleteUpdateZjConsumableInventory(zjConsumableInventoryList, zjConsumableInventory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjConsumableInventoryList);
        }
    }
}
