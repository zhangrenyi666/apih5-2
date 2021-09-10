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
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipPaySettleItem.getPage(),zxSaOtherEquipPaySettleItem.getLimit());
        // 获取数据
        List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(zxSaOtherEquipPaySettleItem);
        // 得到分页信息
        PageInfo<ZxSaOtherEquipPaySettleItem> p = new PageInfo<>(zxSaOtherEquipPaySettleItemList);

        return repEntity.okList(zxSaOtherEquipPaySettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipPaySettleItemDetail(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        if (zxSaOtherEquipPaySettleItem == null) {
            zxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
        }
        // 获取数据
        ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = zxSaOtherEquipPaySettleItemMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId());
        // 数据存在
        if (dbZxSaOtherEquipPaySettleItem != null) {
            return repEntity.ok(dbZxSaOtherEquipPaySettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipPaySettleItem(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 支付项主表id不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId())) {
            return repEntity.layerMessage("no", "支付项主表ID不能为空！");
        }
        // 支付项ID不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipPaySettleItem.getPayId())) {
            return repEntity.layerMessage("no", "支付项ID不能为空！");
        } else {
            ZxSaOtherEquipPaySettleItem dbPaySettleItem = new ZxSaOtherEquipPaySettleItem();
            dbPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
            dbPaySettleItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
            List<ZxSaOtherEquipPaySettleItem> dbPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbPaySettleItem);
            if (dbPaySettleItemList != null && dbPaySettleItemList.size() > 0) {
                return repEntity.layerMessage("no", "数据已存在，不能重复添加！");
            }
        }
        ZxSaOtherEquipPaySettle dbPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        BeanUtil.copyProperties(dbPaySettle, zxSaOtherEquipPaySettleItem);

        // 本期结算金额(元) 数量*含税单价
        BigDecimal thisAmt = CalcUtils.calcMultiply(zxSaOtherEquipPaySettleItem.getPrice(),zxSaOtherEquipPaySettleItem.getQty());
        zxSaOtherEquipPaySettleItem.setThisAmt(thisAmt);
        // 本期结算不含税金额 本期结算金额（元）/（1+税率百分比）
        BigDecimal thisAmtNoTax = CalcUtils.calcDivide(thisAmt,CalcUtils.calcAdd(new BigDecimal(1),CalcUtils.calcDivide(new BigDecimal(zxSaOtherEquipPaySettleItem.getTaxRate()),new BigDecimal(100))));
        zxSaOtherEquipPaySettleItem.setThisAmtNoTax(thisAmtNoTax);
        // 本期结算税额 本期结金额（元）-本期结算不含税金额（元）
        BigDecimal thisAmtTax = CalcUtils.calcSubtract(thisAmt,thisAmtNoTax);
        zxSaOtherEquipPaySettleItem.setThisAmtTax(thisAmtTax);

        // 支付项明细表上期末结算金额(元) 上期末数量
        ZxSaOtherEquipPaySettleItem payItem = new ZxSaOtherEquipPaySettleItem();
        payItem.setZxCtOtherManageId(dbPaySettle.getZxCtOtherManageId());
        payItem.setPeriod(dbPaySettle.getPeriod());
        payItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
        ZxSaOtherEquipPaySettleItem selectItem = zxSaOtherEquipPaySettleItemMapper.selectUpQtyAndAmt(payItem);
        if(selectItem != null){
            // 上期末结算金额(元)
            zxSaOtherEquipPaySettleItem.setUpAmt(selectItem.getUpAmt());
            // 上期末数量
            zxSaOtherEquipPaySettleItem.setUpQty(selectItem.getUpQty());
        } else {
            // 上期末结算金额(元)
            zxSaOtherEquipPaySettleItem.setUpAmt(new BigDecimal(0));
            // 上期末数量
            zxSaOtherEquipPaySettleItem.setUpQty(new BigDecimal(0));
        }

        zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleItemId(UuidUtil.generate());
        zxSaOtherEquipPaySettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipPaySettleItemMapper.insert(zxSaOtherEquipPaySettleItem);

        // 添加支付项明细表数据后，更新主表金额数据
        this.updatePaySettleAmount(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());

        // 更新统计项和结算单主表数据
        ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
        ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        // 结算单主表id
        zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
        // 合同管理id
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
            return repEntity.layerMessage("no", "数据已存在，不能重复添加！");
        }
        ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = zxSaOtherEquipPaySettleItemMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId());
        if (dbZxSaOtherEquipPaySettleItem != null && StrUtil.isNotEmpty(dbZxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleItemId())) {
            // 支付项类型
            dbZxSaOtherEquipPaySettleItem.setPayType(zxSaOtherEquipPaySettleItem.getPayType());
            // 支付项id
            dbZxSaOtherEquipPaySettleItem.setPayId(zxSaOtherEquipPaySettleItem.getPayId());
            // 名称
            dbZxSaOtherEquipPaySettleItem.setPayName(zxSaOtherEquipPaySettleItem.getPayName());
            // 单位
            dbZxSaOtherEquipPaySettleItem.setUnit(zxSaOtherEquipPaySettleItem.getUnit());
            // 数量
            dbZxSaOtherEquipPaySettleItem.setQty(zxSaOtherEquipPaySettleItem.getQty());
            // 单价(元)
            dbZxSaOtherEquipPaySettleItem.setPrice(zxSaOtherEquipPaySettleItem.getPrice());
            // 税率(%)
            dbZxSaOtherEquipPaySettleItem.setTaxRate(zxSaOtherEquipPaySettleItem.getTaxRate());
            // 本期结算金额(元) 数量*含税单价
            BigDecimal thisAmt = CalcUtils.calcMultiply(zxSaOtherEquipPaySettleItem.getPrice(),zxSaOtherEquipPaySettleItem.getQty());
            dbZxSaOtherEquipPaySettleItem.setThisAmt(thisAmt);
            // 本期结算不含税金额 本期结算金额（元）/（1+税率百分比）
            BigDecimal thisAmtNoTax = CalcUtils.calcDivide(thisAmt,CalcUtils.calcAdd(new BigDecimal(1),CalcUtils.calcDivide(new BigDecimal(zxSaOtherEquipPaySettleItem.getTaxRate()),new BigDecimal(100))));
            dbZxSaOtherEquipPaySettleItem.setThisAmtNoTax(thisAmtNoTax);
            // 本期结算税额 本期结金额（元）-本期结算不含税金额（元）
            BigDecimal thisAmtTax = CalcUtils.calcSubtract(thisAmt,thisAmtNoTax);
            dbZxSaOtherEquipPaySettleItem.setThisAmtTax(thisAmtTax);
            // 备注
            dbZxSaOtherEquipPaySettleItem.setRemark(zxSaOtherEquipPaySettleItem.getRemark());
            // 共通
            dbZxSaOtherEquipPaySettleItem.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipPaySettleItemMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettleItem);
            // 更新支付项明细表数据后，更新主表数据
            this.updatePaySettleAmount(dbZxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
            // 更新统计项和结算单主表数据
            ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettleItem.getZxSaOtherEquipPaySettleId());
            ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
            // 结算单主表id
            zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
            // 合同管理id
            zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
            zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);
        }
        // 失败
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
            // 删除支付项明细表数据后，更新主表数据
            Map<String, List<ZxSaOtherEquipPaySettleItem>> map = zxSaOtherEquipPaySettleItemList.stream().collect(Collectors.groupingBy(s -> s.getZxSaOtherEquipPaySettleId()));
            for (String settleId : map.keySet()) {
                this.updatePaySettleAmount(settleId);
                // 更新统计项和结算单主表数据
                ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(settleId);
                ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                // 结算单主表id
                zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
                // 合同管理id
                zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
                zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaOtherEquipPaySettleItemList);
        }
    }

    private void updatePaySettleAmount(String settleId){
        // 查询当前细目结算清单明细列表所属的主表数据
        ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(settleId);
        // 查询当前支付项主表的所有子表数据
        ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
        dbZxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(settleId);
        List<ZxSaOtherEquipPaySettleItem> dbZxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbZxSaOtherEquipPaySettleItem);
        if(dbZxSaOtherEquipPaySettleItemList != null && dbZxSaOtherEquipPaySettleItemList.size()>0) {
            BigDecimal totalThisAmt = null;
            BigDecimal totalThisAmtNoTax = null;
            BigDecimal totalThisAmtTax = null;
            for (ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem1 : dbZxSaOtherEquipPaySettleItemList) {
                // 当前所有支付项结算明细的本期结算含税金额(元)值汇总，也就是支付项结算主表页面上的本期清单结算金额(元)
                totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipPaySettleItem1.getThisAmt());
                // 当前所有支付项结算明细的本期结算不含税金额(元)值汇总，也就是支付项结算主表页面上的本期清单结算不含税金额(元)
                totalThisAmtNoTax = CalcUtils.calcAdd(totalThisAmtNoTax, zxSaOtherEquipPaySettleItem1.getThisAmtNoTax());
                // 当前所有支付项结算明细的本期结算税额(元)值汇总，也就是支付项结算主表页面上的本期清单结算税额(元)
                totalThisAmtTax = CalcUtils.calcAdd(totalThisAmtTax, zxSaOtherEquipPaySettleItem1.getThisAmtTax());
            }
            // 本期支付项结算含税金额(元)
            dbZxSaOtherEquipPaySettle.setThisAmt(totalThisAmt);
            // 本期支付项结算不含税金额(元)
            dbZxSaOtherEquipPaySettle.setThisAmtNoTax(totalThisAmtNoTax);
            // 本期支付项结算税额(元)
            dbZxSaOtherEquipPaySettle.setThisAmtTax(totalThisAmtTax);
        } else {
            // 本期支付项结算含税金额(元)
            dbZxSaOtherEquipPaySettle.setThisAmt(BigDecimal.ZERO);
            // 本期支付项结算不含税金额(元)
            dbZxSaOtherEquipPaySettle.setThisAmtNoTax(BigDecimal.ZERO);
            // 本期支付项结算税额(元)
            dbZxSaOtherEquipPaySettle.setThisAmtTax(BigDecimal.ZERO);
        }
        // 新增支付项结算明细列表以后，同时修改所属主表的数据
        zxSaOtherEquipPaySettleMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettle);
        // 累计支付项结算含税金额(元)
        ZxSaOtherEquipPaySettle dbTotalZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        dbTotalZxSaOtherEquipPaySettle.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.getZxCtOtherManageId());
        // 根据合同id查询当前合同的所有支付项结算主表数据
        List<ZxSaOtherEquipPaySettle> dbZxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbTotalZxSaOtherEquipPaySettle);
        if(dbZxSaOtherEquipPaySettleList != null && dbZxSaOtherEquipPaySettleList.size()>0) {
            BigDecimal totalAllThisAmt = null;
            for (ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle1 : dbZxSaOtherEquipPaySettleList) {
                // 当前合同的所有支付项结算主表的本期支付项结算金额(元)值汇总
                totalAllThisAmt = CalcUtils.calcAdd(totalAllThisAmt, zxSaOtherEquipPaySettle1.getThisAmt());
            }
            // 累计支付项结算含税金额(元)
            dbZxSaOtherEquipPaySettle.setTotalAmt(totalAllThisAmt);
        }
        // 修改累计支付项结算含税金额
        zxSaOtherEquipPaySettleMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettle);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
