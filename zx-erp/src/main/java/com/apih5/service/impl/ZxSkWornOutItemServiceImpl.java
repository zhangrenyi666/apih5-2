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
import com.apih5.mybatis.dao.ZxSkWornOutItemMapper;
import com.apih5.mybatis.pojo.ZxSkWornOutItem;
import com.apih5.service.ZxSkWornOutItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkWornOutItemService")
public class ZxSkWornOutItemServiceImpl implements ZxSkWornOutItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkWornOutItemMapper zxSkWornOutItemMapper;

    @Override
    public ResponseEntity getZxSkWornOutItemListByCondition(ZxSkWornOutItem zxSkWornOutItem) {
        if (zxSkWornOutItem == null) {
            zxSkWornOutItem = new ZxSkWornOutItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkWornOutItem.getPage(),zxSkWornOutItem.getLimit());
        // 获取数据
        List<ZxSkWornOutItem> zxSkWornOutItemList = zxSkWornOutItemMapper.selectByZxSkWornOutItemList(zxSkWornOutItem);
        // 得到分页信息
        PageInfo<ZxSkWornOutItem> p = new PageInfo<>(zxSkWornOutItemList);

        return repEntity.okList(zxSkWornOutItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkWornOutItemDetail(ZxSkWornOutItem zxSkWornOutItem) {
        if (zxSkWornOutItem == null) {
            zxSkWornOutItem = new ZxSkWornOutItem();
        }
        // 获取数据
        ZxSkWornOutItem dbZxSkWornOutItem = zxSkWornOutItemMapper.selectByPrimaryKey(zxSkWornOutItem.getZxSkWornOutItemId());
        // 数据存在
        if (dbZxSkWornOutItem != null) {
            return repEntity.ok(dbZxSkWornOutItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkWornOutItem(ZxSkWornOutItem zxSkWornOutItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkWornOutItem.setZxSkWornOutItemId(UuidUtil.generate());
        zxSkWornOutItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkWornOutItemMapper.insert(zxSkWornOutItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkWornOutItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkWornOutItem(ZxSkWornOutItem zxSkWornOutItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkWornOutItem dbZxSkWornOutItem = zxSkWornOutItemMapper.selectByPrimaryKey(zxSkWornOutItem.getZxSkWornOutItemId());
        if (dbZxSkWornOutItem != null && StrUtil.isNotEmpty(dbZxSkWornOutItem.getZxSkWornOutItemId())) {
           // 单据
           dbZxSkWornOutItem.setBillID(zxSkWornOutItem.getBillID());
           // 物资编码ID
           dbZxSkWornOutItem.setResID(zxSkWornOutItem.getResID());
           // 物资名称
           dbZxSkWornOutItem.setResName(zxSkWornOutItem.getResName());
           // 物资编码
           dbZxSkWornOutItem.setResCode(zxSkWornOutItem.getResCode());
           // 单位
           dbZxSkWornOutItem.setUnit(zxSkWornOutItem.getUnit());
           // 数量
           dbZxSkWornOutItem.setQty(zxSkWornOutItem.getQty());
           // 总金额(万元)
           dbZxSkWornOutItem.setAmt(zxSkWornOutItem.getAmt());
           // 备注
           dbZxSkWornOutItem.setRemarks(zxSkWornOutItem.getRemarks());
           // 排序
           dbZxSkWornOutItem.setSort(zxSkWornOutItem.getSort());
           // 共通
           dbZxSkWornOutItem.setModifyUserInfo(userKey, realName);
           flag = zxSkWornOutItemMapper.updateByPrimaryKey(dbZxSkWornOutItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkWornOutItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkWornOutItem(List<ZxSkWornOutItem> zxSkWornOutItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkWornOutItemList != null && zxSkWornOutItemList.size() > 0) {
           ZxSkWornOutItem zxSkWornOutItem = new ZxSkWornOutItem();
           zxSkWornOutItem.setModifyUserInfo(userKey, realName);
           flag = zxSkWornOutItemMapper.batchDeleteUpdateZxSkWornOutItem(zxSkWornOutItemList, zxSkWornOutItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkWornOutItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
