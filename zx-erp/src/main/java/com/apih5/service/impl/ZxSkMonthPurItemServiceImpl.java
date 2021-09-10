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
import com.apih5.mybatis.dao.ZxSkMonthPurItemMapper;
import com.apih5.mybatis.pojo.ZxSkMonthPurItem;
import com.apih5.service.ZxSkMonthPurItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMonthPurItemService")
public class ZxSkMonthPurItemServiceImpl implements ZxSkMonthPurItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMonthPurItemMapper zxSkMonthPurItemMapper;

    @Override
    public ResponseEntity getZxSkMonthPurItemListByCondition(ZxSkMonthPurItem zxSkMonthPurItem) {
        if (zxSkMonthPurItem == null) {
            zxSkMonthPurItem = new ZxSkMonthPurItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkMonthPurItem.getPage(),zxSkMonthPurItem.getLimit());
        // 获取数据
        List<ZxSkMonthPurItem> zxSkMonthPurItemList = zxSkMonthPurItemMapper.selectByZxSkMonthPurItemList(zxSkMonthPurItem);
        // 得到分页信息
        PageInfo<ZxSkMonthPurItem> p = new PageInfo<>(zxSkMonthPurItemList);

        return repEntity.okList(zxSkMonthPurItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMonthPurItemDetails(ZxSkMonthPurItem zxSkMonthPurItem) {
        if (zxSkMonthPurItem == null) {
            zxSkMonthPurItem = new ZxSkMonthPurItem();
        }
        // 获取数据
        ZxSkMonthPurItem dbZxSkMonthPurItem = zxSkMonthPurItemMapper.selectByPrimaryKey(zxSkMonthPurItem.getId());
        // 数据存在
        if (dbZxSkMonthPurItem != null) {
            return repEntity.ok(dbZxSkMonthPurItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkMonthPurItem(ZxSkMonthPurItem zxSkMonthPurItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkMonthPurItem.setId(UuidUtil.generate());
        zxSkMonthPurItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkMonthPurItemMapper.insert(zxSkMonthPurItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkMonthPurItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkMonthPurItem(ZxSkMonthPurItem zxSkMonthPurItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMonthPurItem dbzxSkMonthPurItem = zxSkMonthPurItemMapper.selectByPrimaryKey(zxSkMonthPurItem.getId());
        if (dbzxSkMonthPurItem != null && StrUtil.isNotEmpty(dbzxSkMonthPurItem.getId())) {
           // 主表ID
           dbzxSkMonthPurItem.setMonthPurID(zxSkMonthPurItem.getMonthPurID());
           // 分部分项ID
           dbzxSkMonthPurItem.setCbsID(zxSkMonthPurItem.getCbsID());
           // 分部分项
           dbzxSkMonthPurItem.setCbsName(zxSkMonthPurItem.getCbsName());
           // 物资ID
           dbzxSkMonthPurItem.setResID(zxSkMonthPurItem.getResID());
           // 物资编码
           dbzxSkMonthPurItem.setResCode(zxSkMonthPurItem.getResCode());
           // 物资名称
           dbzxSkMonthPurItem.setResName(zxSkMonthPurItem.getResName());
           // 计量单位
           dbzxSkMonthPurItem.setUnit(zxSkMonthPurItem.getUnit());
           // 规格型号
           dbzxSkMonthPurItem.setSpec(zxSkMonthPurItem.getSpec());
           // 质量标准
           dbzxSkMonthPurItem.setQualityStand(zxSkMonthPurItem.getQualityStand());
           // 计划采购数量
           dbzxSkMonthPurItem.setPurNum(zxSkMonthPurItem.getPurNum());
           // 估算单价
           dbzxSkMonthPurItem.setPrice(zxSkMonthPurItem.getPrice());
           // 估算总价
           dbzxSkMonthPurItem.setTotalMoney(zxSkMonthPurItem.getTotalMoney());
           // 总价
           dbzxSkMonthPurItem.setTotalAmt(zxSkMonthPurItem.getTotalAmt());
           // 运杂费
           dbzxSkMonthPurItem.setOtherFee(zxSkMonthPurItem.getOtherFee());
           // 备注
           dbzxSkMonthPurItem.setRemark(zxSkMonthPurItem.getRemark());
           // 明细
           dbzxSkMonthPurItem.setCombProp(zxSkMonthPurItem.getCombProp());
           // 共通
           dbzxSkMonthPurItem.setModifyUserInfo(userKey, realName);
           flag = zxSkMonthPurItemMapper.updateByPrimaryKey(dbzxSkMonthPurItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkMonthPurItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMonthPurItem(List<ZxSkMonthPurItem> zxSkMonthPurItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkMonthPurItemList != null && zxSkMonthPurItemList.size() > 0) {
           ZxSkMonthPurItem zxSkMonthPurItem = new ZxSkMonthPurItem();
           zxSkMonthPurItem.setModifyUserInfo(userKey, realName);
           flag = zxSkMonthPurItemMapper.batchDeleteUpdateZxSkMonthPurItem(zxSkMonthPurItemList, zxSkMonthPurItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkMonthPurItemList);
        }
    }
}
