package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.NumberUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSaOtherEquipResSettleMapper;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettle;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaOtherEquipResSettleItemMapper;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettleItem;
import com.apih5.service.ZxSaOtherEquipResSettleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaOtherEquipResSettleItemService")
public class ZxSaOtherEquipResSettleItemServiceImpl implements ZxSaOtherEquipResSettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleItemMapper zxSaOtherEquipResSettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleMapper zxSaOtherEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemServiceImpl zxSaOtherEquipSettleItemServiceImpl;

    @Override
    public ResponseEntity getZxSaOtherEquipResSettleItemListByCondition(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        if (zxSaOtherEquipResSettleItem == null) {
            zxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipResSettleItem.getPage(),zxSaOtherEquipResSettleItem.getLimit());
        // 获取数据
        List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(zxSaOtherEquipResSettleItem);
        // 得到分页信息
        PageInfo<ZxSaOtherEquipResSettleItem> p = new PageInfo<>(zxSaOtherEquipResSettleItemList);

        return repEntity.okList(zxSaOtherEquipResSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipResSettleItemDetail(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        if (zxSaOtherEquipResSettleItem == null) {
            zxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
        }
        // 获取数据
        ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = zxSaOtherEquipResSettleItemMapper.selectByPrimaryKey(zxSaOtherEquipResSettleItem.getZxSaOtherEquipResSettleItemId());
        // 数据存在
        if (dbZxSaOtherEquipResSettleItem != null) {
            return repEntity.ok(dbZxSaOtherEquipResSettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipResSettleItem(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleItemId(UuidUtil.generate());
        zxSaOtherEquipResSettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipResSettleItemMapper.insert(zxSaOtherEquipResSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipResSettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaOtherEquipResSettleItem(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = zxSaOtherEquipResSettleItemMapper.selectByPrimaryKey(zxSaOtherEquipResSettleItem.getZxSaOtherEquipResSettleItemId());
        if (dbZxSaOtherEquipResSettleItem != null && StrUtil.isNotEmpty(dbZxSaOtherEquipResSettleItem.getZxSaOtherEquipResSettleItemId())) {
            // 合同数量如果有变更按变更后为准
            BigDecimal qty;
            if (dbZxSaOtherEquipResSettleItem.getChangedQty() != null && dbZxSaOtherEquipResSettleItem.getChangedQty().compareTo(new BigDecimal(0)) > 0) {
                qty = dbZxSaOtherEquipResSettleItem.getChangedQty();
            } else {
                qty = dbZxSaOtherEquipResSettleItem.getContractQty();
            }
            // 本期结算数量+ 上期末累计结算数量，也就是至本期末累计结算数量
            BigDecimal totalQty = CalcUtils.calcAdd(zxSaOtherEquipResSettleItem.getThisQty(),zxSaOtherEquipResSettleItem.getUpQty());
            if (CalcUtils.compareTo(totalQty, qty) > 0) {
                return repEntity.layerMessage("no", "本期结算数量+上期末累计结算数量，也就是至本期末累计结算数量不能超过合同数量！");
            }
           // 本期结算数量
           dbZxSaOtherEquipResSettleItem.setThisQty(zxSaOtherEquipResSettleItem.getThisQty());
            BigDecimal price;
            // 获取变更后单价
            if (dbZxSaOtherEquipResSettleItem.getContractPrice() == null || dbZxSaOtherEquipResSettleItem.getContractPrice().compareTo(new BigDecimal(0)) == 0) {
                price = CalcUtils.calcDivide(dbZxSaOtherEquipResSettleItem.getChangedAmt(), dbZxSaOtherEquipResSettleItem.getChangedQty(), 6);
            } else {
                price = dbZxSaOtherEquipResSettleItem.getContractPrice();
            }
            // 本期结算含税金额(元) = 本期结算数 * 含税单价
            dbZxSaOtherEquipResSettleItem.setThisAmt(CalcUtils.calcMultiply(zxSaOtherEquipResSettleItem.getThisQty(), price));
            // 税率
            BigDecimal taxRate = CalcUtils.calcDivide(new BigDecimal(dbZxSaOtherEquipResSettleItem.getTaxRate()), new BigDecimal(100), 6);
            // 本期结算不含税金额(元)
            dbZxSaOtherEquipResSettleItem.setThisAmtNoTax(CalcUtils.calcDivide(dbZxSaOtherEquipResSettleItem.getThisAmt(), new BigDecimal(1).add(taxRate), 6));
            // 本期结算税额(元)
            dbZxSaOtherEquipResSettleItem.setThisAmtTax(dbZxSaOtherEquipResSettleItem.getThisAmt().subtract(dbZxSaOtherEquipResSettleItem.getThisAmtNoTax()));

//            // 本期结算含税金额(元)
//            dbZxSaOtherEquipResSettleItem.setThisAmt(zxSaOtherEquipResSettleItem.getThisAmt());
//            // 本期结算不含税金额(元)
//            dbZxSaOtherEquipResSettleItem.setThisAmtNoTax(zxSaOtherEquipResSettleItem.getThisAmtNoTax());
//            // 本期结算税额(元)
//            dbZxSaOtherEquipResSettleItem.setThisAmtTax(zxSaOtherEquipResSettleItem.getThisAmtTax());

            // 至本期末累计结算数量
            dbZxSaOtherEquipResSettleItem.setTotalQty(totalQty);
            // 至本期末累计结算含税金额(元) = 本期结算含税金额(元) + 至上期末累计结算含税金额(元)
            dbZxSaOtherEquipResSettleItem.setTotalAmt(CalcUtils.calcAdd(dbZxSaOtherEquipResSettleItem.getThisAmt(), zxSaOtherEquipResSettleItem.getUpAmt()));
            // 计算式
            dbZxSaOtherEquipResSettleItem.setPlanning(zxSaOtherEquipResSettleItem.getPlanning());
            // 备注
            dbZxSaOtherEquipResSettleItem.setRemark(zxSaOtherEquipResSettleItem.getRemark());
            // 共通
            dbZxSaOtherEquipResSettleItem.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipResSettleItemMapper.updateByPrimaryKey(dbZxSaOtherEquipResSettleItem);
            // 查询当前细目结算清单明细列表所属的主表数据
            ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = zxSaOtherEquipResSettleMapper.selectByPrimaryKey(dbZxSaOtherEquipResSettleItem.getZxSaOtherEquipResSettleId());
            // 查询细目结算明细表list
            ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem1 = new ZxSaOtherEquipResSettleItem();
            dbZxSaOtherEquipResSettleItem1.setZxSaOtherEquipResSettleId(dbZxSaOtherEquipResSettleItem.getZxSaOtherEquipResSettleId());
            List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(dbZxSaOtherEquipResSettleItem1);
            BigDecimal totalThisAmt = null;
            BigDecimal totalThisAmtNoTax = null;
            BigDecimal totalThisAmtTax = null;
            for (ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem1 : zxSaOtherEquipResSettleItemList) {
               // 当前所有细目结算明细的本期结算含税金额(元)值汇总，也就是细目结算主表页面上的本期清单结算金额(元)
               totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipResSettleItem1.getThisAmt());
               // 当前所有细目结算明细的本期结算不含税金额(元)值汇总，也就是细目结算主表页面上的本期清单结算不含税金额(元)
               totalThisAmtNoTax = CalcUtils.calcAdd(totalThisAmtNoTax, zxSaOtherEquipResSettleItem1.getThisAmtNoTax());
               // 当前所有细目结算明细的本期结算税额(元)值汇总，也就是细目结算主表页面上的本期清单结算税额(元)
               totalThisAmtTax = CalcUtils.calcAdd(totalThisAmtTax, zxSaOtherEquipResSettleItem1.getThisAmtTax());
            }

           // 本期清单结算含税金额(元)
           dbZxSaOtherEquipResSettle.setThisAmt(totalThisAmt);
           // 本期清单结算不含税金额(元)
           dbZxSaOtherEquipResSettle.setThisAmtNoTax(totalThisAmtNoTax);
           // 本期清单结算税额(元)
           dbZxSaOtherEquipResSettle.setThisAmtTax(totalThisAmtTax);

           // 修改细目结算明细列表以后，同时修改所属主表的数据
           zxSaOtherEquipResSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipResSettle);

           // 累计清单结算含税金额(元)
           ZxSaOtherEquipResSettle dbTotalZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
           dbTotalZxSaOtherEquipResSettle.setZxCtOtherManageId(dbZxSaOtherEquipResSettle.getZxCtOtherManageId());
           // 根据合同id查询当前合同的所有细目结算主表数据
           List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbTotalZxSaOtherEquipResSettle);
           if(dbZxSaOtherEquipResSettleList != null && dbZxSaOtherEquipResSettleList.size()>0) {
               BigDecimal totalAllThisAmt = null;
               for (ZxSaOtherEquipResSettle zxSaOtherEquipResSettle1 : dbZxSaOtherEquipResSettleList) {
                   // 当前合同的所有细目结算主表的本期清单结算金额(元)值汇总
                   totalAllThisAmt = CalcUtils.calcAdd(totalAllThisAmt, zxSaOtherEquipResSettle1.getThisAmt());
               }
               // 累计清单结算含税金额(元)
               dbZxSaOtherEquipResSettle.setTotalAmt(totalAllThisAmt);
           }

           // 修改累计清单结算含税金额
           zxSaOtherEquipResSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipResSettle);

           // 修改统计项子表页面数据
           ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
           // 结算单主表id
           zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipResSettle.getZxSaOtherEquipSettleId());
           // 合同管理id
           zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipResSettle.getZxCtOtherManageId());
           zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaOtherEquipResSettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipResSettleItem(List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaOtherEquipResSettleItemList != null && zxSaOtherEquipResSettleItemList.size() > 0) {
           ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
           zxSaOtherEquipResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaOtherEquipResSettleItemMapper.batchDeleteUpdateZxSaOtherEquipResSettleItem(zxSaOtherEquipResSettleItemList, zxSaOtherEquipResSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaOtherEquipResSettleItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
