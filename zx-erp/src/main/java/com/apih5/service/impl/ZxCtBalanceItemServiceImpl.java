package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtBalanceItemMapper;
import com.apih5.mybatis.dao.ZxCtBalanceMapper;
import com.apih5.mybatis.pojo.ZxCtBalanceItem;
import com.apih5.service.ZxCtBalanceItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtBalanceItemService")
public class ZxCtBalanceItemServiceImpl implements ZxCtBalanceItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtBalanceItemMapper zxCtBalanceItemMapper;
    
    @Autowired(required = true)
    private ZxCtBalanceMapper zxCtBalanceMapper;

    @Override
    public ResponseEntity getZxCtBalanceItemListByCondition(ZxCtBalanceItem zxCtBalanceItem) {
        if (zxCtBalanceItem == null) {
            zxCtBalanceItem = new ZxCtBalanceItem();
        }
        // 分页查询
        PageHelper.startPage(zxCtBalanceItem.getPage(),zxCtBalanceItem.getLimit());
        // 获取数据
        List<ZxCtBalanceItem> zxCtBalanceItemList = zxCtBalanceItemMapper.selectByZxCtBalanceItemList(zxCtBalanceItem);
        // 得到分页信息
        PageInfo<ZxCtBalanceItem> p = new PageInfo<>(zxCtBalanceItemList);

        return repEntity.okList(zxCtBalanceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtBalanceItemDetail(ZxCtBalanceItem zxCtBalanceItem) {
        if (zxCtBalanceItem == null) {
            zxCtBalanceItem = new ZxCtBalanceItem();
        }
        // 获取数据
        ZxCtBalanceItem dbZxCtBalanceItem = zxCtBalanceItemMapper.selectByPrimaryKey(zxCtBalanceItem.getId());
        // 数据存在
        if (dbZxCtBalanceItem != null) {
            return repEntity.ok(dbZxCtBalanceItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtBalanceItem(ZxCtBalanceItem zxCtBalanceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtBalanceItem.setId(UuidUtil.generate());
        zxCtBalanceItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtBalanceItemMapper.insert(zxCtBalanceItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(zxCtBalanceItem.getBalID());
            return repEntity.ok("sys.data.sava", zxCtBalanceItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtBalanceItem(ZxCtBalanceItem zxCtBalanceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtBalanceItem dbZxCtBalanceItem = zxCtBalanceItemMapper.selectByPrimaryKey(zxCtBalanceItem.getId());
        if (dbZxCtBalanceItem != null && StrUtil.isNotEmpty(dbZxCtBalanceItem.getId())) {
           // 计量单ID
           dbZxCtBalanceItem.setBalID(zxCtBalanceItem.getBalID());
           // 管理单元ID
           dbZxCtBalanceItem.setMuID(zxCtBalanceItem.getMuID());
           // 清单ID
           dbZxCtBalanceItem.setWorkID(zxCtBalanceItem.getWorkID());
           // 单价
           dbZxCtBalanceItem.setPrice(zxCtBalanceItem.getPrice());
           // 本期合同内计量数量
           dbZxCtBalanceItem.setBalQty(zxCtBalanceItem.getBalQty());
           // 本期合同外计量数量
           dbZxCtBalanceItem.setBalAltQty(zxCtBalanceItem.getBalAltQty());
           // 计量金额
           dbZxCtBalanceItem.setBalAmt(zxCtBalanceItem.getBalAmt());
           // 清单上报量
           dbZxCtBalanceItem.setReqQty(zxCtBalanceItem.getReqQty());
           // 变更上报量
           dbZxCtBalanceItem.setReqAltQty(zxCtBalanceItem.getReqAltQty());
           // 上报金额
           dbZxCtBalanceItem.setReqAmt(zxCtBalanceItem.getReqAmt());
           // combProp
           dbZxCtBalanceItem.setCombProp(zxCtBalanceItem.getCombProp());
           // pp1
           dbZxCtBalanceItem.setPp1(zxCtBalanceItem.getPp1());
           // pp2
           dbZxCtBalanceItem.setPp2(zxCtBalanceItem.getPp2());
           // pp3
           dbZxCtBalanceItem.setPp3(zxCtBalanceItem.getPp3());
           // pp4
           dbZxCtBalanceItem.setPp4(zxCtBalanceItem.getPp4());
           // pp5
           dbZxCtBalanceItem.setPp5(zxCtBalanceItem.getPp5());
           // pp6
           dbZxCtBalanceItem.setPp6(zxCtBalanceItem.getPp6());
           // pp7
           dbZxCtBalanceItem.setPp7(zxCtBalanceItem.getPp7());
           // pp8
           dbZxCtBalanceItem.setPp8(zxCtBalanceItem.getPp8());
           // pp9
           dbZxCtBalanceItem.setPp9(zxCtBalanceItem.getPp9());
           // pp10
           dbZxCtBalanceItem.setPp10(zxCtBalanceItem.getPp10());
           // editTime
           dbZxCtBalanceItem.setEditTime(zxCtBalanceItem.getEditTime());
           // 本期变更增补金额
           dbZxCtBalanceItem.setChangeAltAmt(zxCtBalanceItem.getChangeAltAmt());
           // 备注
           dbZxCtBalanceItem.setRemarks(zxCtBalanceItem.getRemarks());
           // 排序
           dbZxCtBalanceItem.setSort(zxCtBalanceItem.getSort());
           // 共通
           dbZxCtBalanceItem.setModifyUserInfo(userKey, realName);
           flag = zxCtBalanceItemMapper.updateByPrimaryKey(dbZxCtBalanceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(dbZxCtBalanceItem.getBalID());
            return repEntity.ok("sys.data.update",zxCtBalanceItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtBalanceItem(List<ZxCtBalanceItem> zxCtBalanceItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtBalanceItemList != null && zxCtBalanceItemList.size() > 0) {
           ZxCtBalanceItem zxCtBalanceItem = new ZxCtBalanceItem();
           zxCtBalanceItem.setModifyUserInfo(userKey, realName);
           flag = zxCtBalanceItemMapper.batchDeleteUpdateZxCtBalanceItem(zxCtBalanceItemList, zxCtBalanceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(zxCtBalanceItemList.get(0).getBalID());
            return repEntity.ok("sys.data.delete",zxCtBalanceItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity updateZxCtBalanceItemById(ZxCtBalanceItem zxCtBalanceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtBalanceItem dbZxCtBalanceItem = zxCtBalanceItemMapper.selectByPrimaryKey(zxCtBalanceItem.getId());
        if (dbZxCtBalanceItem != null && StrUtil.isNotEmpty(dbZxCtBalanceItem.getId())) {
           // 单价
           dbZxCtBalanceItem.setPrice(zxCtBalanceItem.getPrice());
           // 本期合同内计量数量
           dbZxCtBalanceItem.setBalQty(zxCtBalanceItem.getBalQty());
           // 本期合同外计量数量
           dbZxCtBalanceItem.setBalAltQty(zxCtBalanceItem.getBalAltQty());
           // 计量金额
           dbZxCtBalanceItem.setBalAmt(zxCtBalanceItem.getBalAmt());
           // 本期变更增补金额
           dbZxCtBalanceItem.setChangeAltAmt(zxCtBalanceItem.getChangeAltAmt());
           // 共通
           dbZxCtBalanceItem.setModifyUserInfo(userKey, realName);
           flag = zxCtBalanceItemMapper.updateByPrimaryKey(dbZxCtBalanceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(dbZxCtBalanceItem.getBalID());
            return repEntity.ok("sys.data.update",zxCtBalanceItem);
        }
    }
}
