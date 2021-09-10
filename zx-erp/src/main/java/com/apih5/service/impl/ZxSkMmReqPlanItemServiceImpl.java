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
import com.apih5.mybatis.dao.ZxSkMmReqPlanItemMapper;
import com.apih5.mybatis.pojo.ZxSkMmReqPlanItem;
import com.apih5.service.ZxSkMmReqPlanItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMmReqPlanItemService")
public class ZxSkMmReqPlanItemServiceImpl implements ZxSkMmReqPlanItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMmReqPlanItemMapper zxSkMmReqPlanItemMapper;

    @Override
    public ResponseEntity getZxSkMmReqPlanItemListByCondition(ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        if (zxSkMmReqPlanItem == null) {
            zxSkMmReqPlanItem = new ZxSkMmReqPlanItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkMmReqPlanItem.getPage(),zxSkMmReqPlanItem.getLimit());
        // 获取数据
        List<ZxSkMmReqPlanItem> zxSkMmReqPlanItemList = zxSkMmReqPlanItemMapper.selectByZxSkMmReqPlanItemList(zxSkMmReqPlanItem);
        // 得到分页信息
        PageInfo<ZxSkMmReqPlanItem> p = new PageInfo<>(zxSkMmReqPlanItemList);

        return repEntity.okList(zxSkMmReqPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMmReqPlanItemDetails(ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        if (zxSkMmReqPlanItem == null) {
            zxSkMmReqPlanItem = new ZxSkMmReqPlanItem();
        }
        // 获取数据
        ZxSkMmReqPlanItem dbZxSkMmReqPlanItem = zxSkMmReqPlanItemMapper.selectByPrimaryKey(zxSkMmReqPlanItem.getId());
        // 数据存在
        if (dbZxSkMmReqPlanItem != null) {
            return repEntity.ok(dbZxSkMmReqPlanItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkMmReqPlanItem(ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkMmReqPlanItem.setId(UuidUtil.generate());
        zxSkMmReqPlanItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkMmReqPlanItemMapper.insert(zxSkMmReqPlanItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkMmReqPlanItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkMmReqPlanItem(ZxSkMmReqPlanItem zxSkMmReqPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMmReqPlanItem dbzxSkMmReqPlanItem = zxSkMmReqPlanItemMapper.selectByPrimaryKey(zxSkMmReqPlanItem.getId());
        if (dbzxSkMmReqPlanItem != null && StrUtil.isNotEmpty(dbzxSkMmReqPlanItem.getId())) {
           // 主键id
           dbzxSkMmReqPlanItem.setMmReqPlanID(zxSkMmReqPlanItem.getMmReqPlanID());
           // 分部分项ID
           dbzxSkMmReqPlanItem.setCbsID(zxSkMmReqPlanItem.getCbsID());
           // 分部分项
           dbzxSkMmReqPlanItem.setCbsName(zxSkMmReqPlanItem.getCbsName());
           // 物资ID
           dbzxSkMmReqPlanItem.setResID(zxSkMmReqPlanItem.getResID());
           // 物资编码
           dbzxSkMmReqPlanItem.setResCode(zxSkMmReqPlanItem.getResCode());
           // 物资名称
           dbzxSkMmReqPlanItem.setResName(zxSkMmReqPlanItem.getResName());
           // 计量单位
           dbzxSkMmReqPlanItem.setUnit(zxSkMmReqPlanItem.getUnit());
           // 规格型号
           dbzxSkMmReqPlanItem.setSpec(zxSkMmReqPlanItem.getSpec());
           // 单价
           dbzxSkMmReqPlanItem.setPrice(zxSkMmReqPlanItem.getPrice());
           // 月物资需用数量
           dbzxSkMmReqPlanItem.setPurNum(zxSkMmReqPlanItem.getPurNum());
           // 金额
           dbzxSkMmReqPlanItem.setTotalMoney(zxSkMmReqPlanItem.getTotalMoney());
           // 下月预估用量
           dbzxSkMmReqPlanItem.setNextMonthNum(zxSkMmReqPlanItem.getNextMonthNum());
           // 下月预估金额
           dbzxSkMmReqPlanItem.setNextMonthAmt(zxSkMmReqPlanItem.getNextMonthAmt());
           // 备注
           dbzxSkMmReqPlanItem.setRemark(zxSkMmReqPlanItem.getRemark());
           // 明细
           dbzxSkMmReqPlanItem.setCombProp(zxSkMmReqPlanItem.getCombProp());
           // 共通
           dbzxSkMmReqPlanItem.setModifyUserInfo(userKey, realName);
           flag = zxSkMmReqPlanItemMapper.updateByPrimaryKey(dbzxSkMmReqPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkMmReqPlanItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMmReqPlanItem(List<ZxSkMmReqPlanItem> zxSkMmReqPlanItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkMmReqPlanItemList != null && zxSkMmReqPlanItemList.size() > 0) {
           ZxSkMmReqPlanItem zxSkMmReqPlanItem = new ZxSkMmReqPlanItem();
           zxSkMmReqPlanItem.setModifyUserInfo(userKey, realName);
           flag = zxSkMmReqPlanItemMapper.batchDeleteUpdateZxSkMmReqPlanItem(zxSkMmReqPlanItemList, zxSkMmReqPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkMmReqPlanItemList);
        }
    }
}
