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
import com.apih5.mybatis.dao.ZxSkTurnResAmoItemMapper;
import com.apih5.mybatis.pojo.ZxSkTurnResAmoItem;
import com.apih5.service.ZxSkTurnResAmoItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnResAmoItemService")
public class ZxSkTurnResAmoItemServiceImpl implements ZxSkTurnResAmoItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnResAmoItemMapper zxSkTurnResAmoItemMapper;

    @Override
    public ResponseEntity getZxSkTurnResAmoItemListByCondition(ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        if (zxSkTurnResAmoItem == null) {
            zxSkTurnResAmoItem = new ZxSkTurnResAmoItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnResAmoItem.getPage(),zxSkTurnResAmoItem.getLimit());
        // 获取数据
        List<ZxSkTurnResAmoItem> zxSkTurnResAmoItemList = zxSkTurnResAmoItemMapper.selectByZxSkTurnResAmoItemList(zxSkTurnResAmoItem);
        // 得到分页信息
        PageInfo<ZxSkTurnResAmoItem> p = new PageInfo<>(zxSkTurnResAmoItemList);

        return repEntity.okList(zxSkTurnResAmoItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnResAmoItemDetail(ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        if (zxSkTurnResAmoItem == null) {
            zxSkTurnResAmoItem = new ZxSkTurnResAmoItem();
        }
        // 获取数据
        ZxSkTurnResAmoItem dbZxSkTurnResAmoItem = zxSkTurnResAmoItemMapper.selectByPrimaryKey(zxSkTurnResAmoItem.getZxSkTurnResAmoItemId());
        // 数据存在
        if (dbZxSkTurnResAmoItem != null) {
            return repEntity.ok(dbZxSkTurnResAmoItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnResAmoItem(ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnResAmoItem.setZxSkTurnResAmoItemId(UuidUtil.generate());
        zxSkTurnResAmoItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnResAmoItemMapper.insert(zxSkTurnResAmoItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnResAmoItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnResAmoItem(ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnResAmoItem dbZxSkTurnResAmoItem = zxSkTurnResAmoItemMapper.selectByPrimaryKey(zxSkTurnResAmoItem.getZxSkTurnResAmoItemId());
        if (dbZxSkTurnResAmoItem != null && StrUtil.isNotEmpty(dbZxSkTurnResAmoItem.getZxSkTurnResAmoItemId())) {
           // 物资编号
           dbZxSkTurnResAmoItem.setResCode(zxSkTurnResAmoItem.getResCode());
           // 物资名称
           dbZxSkTurnResAmoItem.setResName(zxSkTurnResAmoItem.getResName());
           // 规格
           dbZxSkTurnResAmoItem.setSpec(zxSkTurnResAmoItem.getSpec());
           // 单位
           dbZxSkTurnResAmoItem.setResUnit(zxSkTurnResAmoItem.getResUnit());
           // 数量
           dbZxSkTurnResAmoItem.setBuyQty(zxSkTurnResAmoItem.getBuyQty());
           // 购入单价
           dbZxSkTurnResAmoItem.setBuyPrice(zxSkTurnResAmoItem.getBuyPrice());
           // 原值
           dbZxSkTurnResAmoItem.setBuyAmt(zxSkTurnResAmoItem.getBuyAmt());
           // 本期摊销金额
           dbZxSkTurnResAmoItem.setShareAmt(zxSkTurnResAmoItem.getShareAmt());
           // 累计摊销金额
           dbZxSkTurnResAmoItem.setAllShareAmt(zxSkTurnResAmoItem.getAllShareAmt());
           // 期末净值
           dbZxSkTurnResAmoItem.setCurrentAmt(zxSkTurnResAmoItem.getCurrentAmt());
           // 备注
           dbZxSkTurnResAmoItem.setRemarks(zxSkTurnResAmoItem.getRemarks());
           // 排序
           dbZxSkTurnResAmoItem.setSort(zxSkTurnResAmoItem.getSort());
           // 共通
           dbZxSkTurnResAmoItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnResAmoItemMapper.updateByPrimaryKey(dbZxSkTurnResAmoItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnResAmoItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnResAmoItem(List<ZxSkTurnResAmoItem> zxSkTurnResAmoItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnResAmoItemList != null && zxSkTurnResAmoItemList.size() > 0) {
           ZxSkTurnResAmoItem zxSkTurnResAmoItem = new ZxSkTurnResAmoItem();
           zxSkTurnResAmoItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnResAmoItemMapper.batchDeleteUpdateZxSkTurnResAmoItem(zxSkTurnResAmoItemList, zxSkTurnResAmoItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnResAmoItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkTurnResAmoItem> ureportZxSkTurnResAmoItem(ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
    	List<ZxSkTurnResAmoItem> zxSkTurnResAmoItemList = zxSkTurnResAmoItemMapper.selectZxSkTurnResAmoItem(zxSkTurnResAmoItem);
    	return zxSkTurnResAmoItemList;
    
    }

    @Override
    public ResponseEntity ureportZxSkTurnResAmo(ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        if (zxSkTurnResAmoItem == null) {
            zxSkTurnResAmoItem = new ZxSkTurnResAmoItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnResAmoItem.getPage(),zxSkTurnResAmoItem.getLimit());
        // 获取数据
        List<ZxSkTurnResAmoItem> zxSkTurnResAmoItemList = zxSkTurnResAmoItemMapper.selectZxSkTurnResAmoItem(zxSkTurnResAmoItem);
        // 得到分页信息
        PageInfo<ZxSkTurnResAmoItem> p = new PageInfo<>(zxSkTurnResAmoItemList);

        return repEntity.okList(zxSkTurnResAmoItemList, p.getTotal());
    }
}
