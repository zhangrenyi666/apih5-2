package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.bean.BeanUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSaOtherEquipPaySettleMapper;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettle;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaOtherEquipPaySettleItemMapper;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettleItem;
import com.apih5.service.ZxSaOtherEquipPaySettleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxSaOtherEquipPaySettleItemService")
public class ZxSaOtherEquipPaySettleItemServiceImpl implements ZxSaOtherEquipPaySettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleItemMapper zxSaOtherEquipPaySettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleMapper zxSaOtherEquipPaySettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemServiceImpl zxSaOtherEquipSettleItemServiceImpl;

    @Override
    public ResponseEntity getZxSaOtherEquipPaySettleItemListByCondition(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        if (zxSaOtherEquipPaySettleItem == null) {
            zxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
        }
        // ????????????
        PageHelper.startPage(zxSaOtherEquipPaySettleItem.getPage(),zxSaOtherEquipPaySettleItem.getLimit());
        // ????????????
        List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(zxSaOtherEquipPaySettleItem);
        // ??????????????????
        PageInfo<ZxSaOtherEquipPaySettleItem> p = new PageInfo<>(zxSaOtherEquipPaySettleItemList);

        return repEntity.okList(zxSaOtherEquipPaySettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipPaySettleItemDetail(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        if (zxSaOtherEquipPaySettleItem == null) {
            zxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
        }
        // ????????????
        ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = zxSaOtherEquipPaySettleItemMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId());
        // ????????????
        if (dbZxSaOtherEquipPaySettleItem != null) {
            return repEntity.ok(dbZxSaOtherEquipPaySettleItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipPaySettleItem(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // ???????????????id????????????
        if (StrUtil.isEmpty(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId())) {
            return repEntity.layerMessage("no", "???????????????ID???????????????");
        }
        // ?????????ID????????????
        if (StrUtil.isEmpty(zxSaOtherEquipPaySettleItem.getPayId())) {
            return repEntity.layerMessage("no", "?????????ID???????????????");
        } else {
            ZxSaOtherEquipPaySettleItem dbPaySettleItem = new ZxSaOtherEquipPaySettleItem();
            dbPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
            dbPaySettleItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
            List<ZxSaOtherEquipPaySettleItem> dbPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbPaySettleItem);
            if (dbPaySettleItemList != null && dbPaySettleItemList.size() > 0) {
                return repEntity.layerMessage("no", "???????????????????????????????????????");
            }
        }
        ZxSaOtherEquipPaySettle dbPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        BeanUtil.copyProperties(dbPaySettle, zxSaOtherEquipPaySettleItem);

        // ??????????????????(???) ??????*????????????
        BigDecimal thisAmt = CalcUtils.calcMultiply(zxSaOtherEquipPaySettleItem.getPrice(),zxSaOtherEquipPaySettleItem.getQty());
        zxSaOtherEquipPaySettleItem.setThisAmt(thisAmt);
        // ??????????????????????????? ???????????????????????????/???1+??????????????????
        BigDecimal thisAmtNoTax = CalcUtils.calcDivide(thisAmt,CalcUtils.calcAdd(new BigDecimal(1),CalcUtils.calcDivide(new BigDecimal(zxSaOtherEquipPaySettleItem.getTaxRate()),new BigDecimal(100))));
        zxSaOtherEquipPaySettleItem.setThisAmtNoTax(thisAmtNoTax);
        // ?????????????????? ????????????????????????-????????????????????????????????????
        BigDecimal thisAmtTax = CalcUtils.calcSubtract(thisAmt,thisAmtNoTax);
        zxSaOtherEquipPaySettleItem.setThisAmtTax(thisAmtTax);

        // ???????????????????????????????????????(???) ???????????????
        ZxSaOtherEquipPaySettleItem payItem = new ZxSaOtherEquipPaySettleItem();
        payItem.setZxCtOtherManageId(dbPaySettle.getZxCtOtherManageId());
        payItem.setPeriod(dbPaySettle.getPeriod());
        payItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
        ZxSaOtherEquipPaySettleItem selectItem = zxSaOtherEquipPaySettleItemMapper.selectUpQtyAndAmt(payItem);
        if(selectItem != null){
            // ?????????????????????(???)
            zxSaOtherEquipPaySettleItem.setUpAmt(selectItem.getUpAmt());
            // ???????????????
            zxSaOtherEquipPaySettleItem.setUpQty(selectItem.getUpQty());
        } else {
            // ?????????????????????(???)
            zxSaOtherEquipPaySettleItem.setUpAmt(new BigDecimal(0));
            // ???????????????
            zxSaOtherEquipPaySettleItem.setUpQty(new BigDecimal(0));
        }

        zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleItemId(UuidUtil.generate());
        zxSaOtherEquipPaySettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipPaySettleItemMapper.insert(zxSaOtherEquipPaySettleItem);

        // ????????????????????????????????????????????????????????????
        this.updatePaySettleAmount(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());

        // ???????????????????????????????????????
        ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        // ???????????????id
        zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
        // ????????????id
        zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
        zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipPaySettleItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateZxSaOtherEquipPaySettleItem(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipPaySettleItem dbPaySettleItem = new ZxSaOtherEquipPaySettleItem();
        dbPaySettleItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
        dbPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        dbPaySettleItem.setZxSaOtherEquipPaySettleItemId(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId());
        List<ZxSaOtherEquipPaySettleItem> dbPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.repeatOtherEquipPaySettleItemCheck(dbPaySettleItem);
        if (dbPaySettleItemList != null && dbPaySettleItemList.size() > 0) {
            return repEntity.layerMessage("no", "???????????????????????????????????????");
        }
        ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = zxSaOtherEquipPaySettleItemMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId());
        if (dbZxSaOtherEquipPaySettleItem != null && StrUtil.isNotEmpty(dbZxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId())) {
            // ???????????????
            dbZxSaOtherEquipPaySettleItem.setPayType(zxSaOtherEquipPaySettleItem.getPayType());
            // ?????????id
            dbZxSaOtherEquipPaySettleItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
            // ??????
            dbZxSaOtherEquipPaySettleItem.setPayName(zxSaOtherEquipPaySettleItem.getPayName());
            // ??????
            dbZxSaOtherEquipPaySettleItem.setUnit(zxSaOtherEquipPaySettleItem.getUnit());
            // ??????
            dbZxSaOtherEquipPaySettleItem.setQty(zxSaOtherEquipPaySettleItem.getQty());
            // ??????(???)
            dbZxSaOtherEquipPaySettleItem.setPrice(zxSaOtherEquipPaySettleItem.getPrice());
            // ??????(%)
            dbZxSaOtherEquipPaySettleItem.setTaxRate(zxSaOtherEquipPaySettleItem.getTaxRate());
            // ??????????????????(???) ??????*????????????
            BigDecimal thisAmt = CalcUtils.calcMultiply(zxSaOtherEquipPaySettleItem.getPrice(),zxSaOtherEquipPaySettleItem.getQty());
            dbZxSaOtherEquipPaySettleItem.setThisAmt(thisAmt);
            // ??????????????????????????? ???????????????????????????/???1+??????????????????
            BigDecimal thisAmtNoTax = CalcUtils.calcDivide(thisAmt,CalcUtils.calcAdd(new BigDecimal(1),CalcUtils.calcDivide(new BigDecimal(zxSaOtherEquipPaySettleItem.getTaxRate()),new BigDecimal(100))));
            dbZxSaOtherEquipPaySettleItem.setThisAmtNoTax(thisAmtNoTax);
            // ?????????????????? ????????????????????????-????????????????????????????????????
            BigDecimal thisAmtTax = CalcUtils.calcSubtract(thisAmt,thisAmtNoTax);
            dbZxSaOtherEquipPaySettleItem.setThisAmtTax(thisAmtTax);
            // ??????
            dbZxSaOtherEquipPaySettleItem.setRemark(zxSaOtherEquipPaySettleItem.getRemark());
            // ??????
            dbZxSaOtherEquipPaySettleItem.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipPaySettleItemMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettleItem);
            // ??????????????????????????????????????????????????????
            this.updatePaySettleAmount(dbZxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
            // ???????????????????????????????????????
            ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
            ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
            // ???????????????id
            zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
            // ????????????id
            zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
            zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaOtherEquipPaySettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipPaySettleItem(List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaOtherEquipPaySettleItemList != null && zxSaOtherEquipPaySettleItemList.size() > 0) {
           ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
           zxSaOtherEquipPaySettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaOtherEquipPaySettleItemMapper.batchDeleteUpdateZxSaOtherEquipPaySettleItem(zxSaOtherEquipPaySettleItemList, zxSaOtherEquipPaySettleItem);
            // ??????????????????????????????????????????????????????
            Map<String, List<ZxSaOtherEquipPaySettleItem>> map = zxSaOtherEquipPaySettleItemList.stream().collect(Collectors.groupingBy(s -> s.getZxSaOtherEquipPaySettleId()));
            for (String settleId : map.keySet()) {
                this.updatePaySettleAmount(settleId);
                // ???????????????????????????????????????
                ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(settleId);
                ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                // ???????????????id
                zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
                // ????????????id
                zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
                zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaOtherEquipPaySettleItemList);
        }
    }

    private void updatePaySettleAmount(String settleId){
        // ???????????????????????????????????????????????????????????????
        ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(settleId);
        // ????????????????????????????????????????????????
        ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
        dbZxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(settleId);
        List<ZxSaOtherEquipPaySettleItem> dbZxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbZxSaOtherEquipPaySettleItem);
        if(dbZxSaOtherEquipPaySettleItemList != null && dbZxSaOtherEquipPaySettleItemList.size()>0) {
            BigDecimal totalThisAmt = null;
            BigDecimal totalThisAmtNoTax = null;
            BigDecimal totalThisAmtTax = null;
            for (ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem1 : dbZxSaOtherEquipPaySettleItemList) {
                // ????????????????????????????????????????????????????????????(???)??????????????????????????????????????????????????????????????????????????????(???)
                totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipPaySettleItem1.getThisAmt());
                // ???????????????????????????????????????????????????????????????(???)???????????????????????????????????????????????????????????????????????????????????????(???)
                totalThisAmtNoTax = CalcUtils.calcAdd(totalThisAmtNoTax, zxSaOtherEquipPaySettleItem1.getThisAmtNoTax());
                // ??????????????????????????????????????????????????????(???)??????????????????????????????????????????????????????????????????????????????(???)
                totalThisAmtTax = CalcUtils.calcAdd(totalThisAmtTax, zxSaOtherEquipPaySettleItem1.getThisAmtTax());
            }
            // ?????????????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setThisAmt(totalThisAmt);
            // ????????????????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setThisAmtNoTax(totalThisAmtNoTax);
            // ???????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setThisAmtTax(totalThisAmtTax);
        } else {
            // ?????????????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setThisAmt(BigDecimal.ZERO);
            // ????????????????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setThisAmtNoTax(BigDecimal.ZERO);
            // ???????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setThisAmtTax(BigDecimal.ZERO);
        }
        // ???????????????????????????????????????????????????????????????????????????
        zxSaOtherEquipPaySettleMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettle);
        // ?????????????????????????????????(???)
        ZxSaOtherEquipPaySettle dbTotalZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        dbTotalZxSaOtherEquipPaySettle.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
        // ????????????id??????????????????????????????????????????????????????
        List<ZxSaOtherEquipPaySettle> dbZxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbTotalZxSaOtherEquipPaySettle);
        if(dbZxSaOtherEquipPaySettleList != null && dbZxSaOtherEquipPaySettleList.size()>0) {
            BigDecimal totalAllThisAmt = null;
            for (ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle1 : dbZxSaOtherEquipPaySettleList) {
                // ????????????????????????????????????????????????????????????????????????(???)?????????
                totalAllThisAmt = CalcUtils.calcAdd(totalAllThisAmt, zxSaOtherEquipPaySettle1.getThisAmt());
            }
            // ?????????????????????????????????(???)
            dbZxSaOtherEquipPaySettle.setTotalAmt(totalAllThisAmt);
        }
        // ???????????????????????????????????????
        zxSaOtherEquipPaySettleMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettle);
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
