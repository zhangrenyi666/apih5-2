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
import com.apih5.mybatis.dao.ZxSkInvoiceItemMapper;
import com.apih5.mybatis.pojo.ZxSkInvoiceItem;
import com.apih5.service.ZxSkInvoiceItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkInvoiceItemService")
public class ZxSkInvoiceItemServiceImpl implements ZxSkInvoiceItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkInvoiceItemMapper zxSkInvoiceItemMapper;

    @Override
    public ResponseEntity getZxSkInvoiceItemListByCondition(ZxSkInvoiceItem zxSkInvoiceItem) {
        if (zxSkInvoiceItem == null) {
            zxSkInvoiceItem = new ZxSkInvoiceItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkInvoiceItem.getPage(),zxSkInvoiceItem.getLimit());
        // 获取数据
        List<ZxSkInvoiceItem> zxSkInvoiceItemList = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
        // 得到分页信息
        PageInfo<ZxSkInvoiceItem> p = new PageInfo<>(zxSkInvoiceItemList);

        return repEntity.okList(zxSkInvoiceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkInvoiceItemDetail(ZxSkInvoiceItem zxSkInvoiceItem) {
        if (zxSkInvoiceItem == null) {
            zxSkInvoiceItem = new ZxSkInvoiceItem();
        }
        // 获取数据
        ZxSkInvoiceItem dbZxSkInvoiceItem = zxSkInvoiceItemMapper.selectByPrimaryKey(zxSkInvoiceItem.getId());
        // 数据存在
        if (dbZxSkInvoiceItem != null) {
            return repEntity.ok(dbZxSkInvoiceItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkInvoiceItem(ZxSkInvoiceItem zxSkInvoiceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkInvoiceItem.setId(UuidUtil.generate());
        zxSkInvoiceItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkInvoiceItemMapper.insert(zxSkInvoiceItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkInvoiceItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkInvoiceItem(ZxSkInvoiceItem zxSkInvoiceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkInvoiceItem dbZxSkInvoiceItem = zxSkInvoiceItemMapper.selectByPrimaryKey(zxSkInvoiceItem.getId());
        if (dbZxSkInvoiceItem != null && StrUtil.isNotEmpty(dbZxSkInvoiceItem.getId())) {
           // 发票ID
           dbZxSkInvoiceItem.setInvoiceID(zxSkInvoiceItem.getInvoiceID());
           // 物资ID
           dbZxSkInvoiceItem.setResID(zxSkInvoiceItem.getResID());
           // 冲账数量
           dbZxSkInvoiceItem.setQty(zxSkInvoiceItem.getQty());
           // 含税单价
           dbZxSkInvoiceItem.setUnitPrice(zxSkInvoiceItem.getUnitPrice());
           // 其它费入账
           dbZxSkInvoiceItem.setOtherExpense(zxSkInvoiceItem.getOtherExpense());
           // 入账金额
           dbZxSkInvoiceItem.setAmt(zxSkInvoiceItem.getAmt());
           // 其他费明细ID
           dbZxSkInvoiceItem.setOtherExpenseItemID(zxSkInvoiceItem.getOtherExpenseItemID());
           // 运输费明细ID
           dbZxSkInvoiceItem.setYsFeeItemID(zxSkInvoiceItem.getYsFeeItemID());
           // 收料单其它费明细id
           dbZxSkInvoiceItem.setReceivingOtherExpenseItemID(zxSkInvoiceItem.getReceivingOtherExpenseItemID());
           // 收料单运输费明细id
           dbZxSkInvoiceItem.setReceivingYsFeeItemID(zxSkInvoiceItem.getReceivingYsFeeItemID());
           // 关联的合同ID
           dbZxSkInvoiceItem.setContractID(zxSkInvoiceItem.getContractID());
           // 预收物资明细ID
           dbZxSkInvoiceItem.setStockTransItemID(zxSkInvoiceItem.getStockTransItemID());
           // 采购单价
           dbZxSkInvoiceItem.setStdPrice(zxSkInvoiceItem.getStdPrice());
           // 含税金额
           dbZxSkInvoiceItem.setStockAmt(zxSkInvoiceItem.getStockAmt());
           // 预收单ID
           dbZxSkInvoiceItem.setStockTransID(zxSkInvoiceItem.getStockTransID());
           // 预收单号
           dbZxSkInvoiceItem.setStockTransBillNo(zxSkInvoiceItem.getStockTransBillNo());
           // 税率(%)
           dbZxSkInvoiceItem.setTaxRate(zxSkInvoiceItem.getTaxRate());
           // 是否抵扣
           dbZxSkInvoiceItem.setIsDeduct(zxSkInvoiceItem.getIsDeduct());
           // 不含税单价
           dbZxSkInvoiceItem.setUnitPriceNoTax(zxSkInvoiceItem.getUnitPriceNoTax());
           // 不含税金额
           dbZxSkInvoiceItem.setStockAmtNoTax(zxSkInvoiceItem.getStockAmtNoTax());
           // 税额
           dbZxSkInvoiceItem.setStockAmtTax(zxSkInvoiceItem.getStockAmtTax());
           // 运输费入账
           dbZxSkInvoiceItem.setYsFee(zxSkInvoiceItem.getYsFee());
           // 总价
           dbZxSkInvoiceItem.setAmtTotal(zxSkInvoiceItem.getAmtTotal());
           // 运输费总价
           dbZxSkInvoiceItem.setYsFeeTotal(zxSkInvoiceItem.getYsFeeTotal());
           // 其它费总价
           dbZxSkInvoiceItem.setOtherExpenseTotal(zxSkInvoiceItem.getOtherExpenseTotal());
           // 预收数量
           dbZxSkInvoiceItem.setTempQty(zxSkInvoiceItem.getTempQty());
           // 物资编码
           dbZxSkInvoiceItem.setResCode(zxSkInvoiceItem.getResCode());
           // 物资名称
           dbZxSkInvoiceItem.setResName(zxSkInvoiceItem.getResName());
           // 物资规格
           dbZxSkInvoiceItem.setResSpec(zxSkInvoiceItem.getResSpec());
           // 计量单位
           dbZxSkInvoiceItem.setResUnit(zxSkInvoiceItem.getResUnit());
           // 原含税单价
           dbZxSkInvoiceItem.setOldInPrice(zxSkInvoiceItem.getOldInPrice());
           // 原入账金额
           dbZxSkInvoiceItem.setoldAmt(zxSkInvoiceItem.getoldAmt());
           // 备注
           dbZxSkInvoiceItem.setRemarks(zxSkInvoiceItem.getRemarks());
           // 排序
           dbZxSkInvoiceItem.setSort(zxSkInvoiceItem.getSort());
           // 共通
           dbZxSkInvoiceItem.setModifyUserInfo(userKey, realName);
           flag = zxSkInvoiceItemMapper.updateByPrimaryKey(dbZxSkInvoiceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkInvoiceItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkInvoiceItem(List<ZxSkInvoiceItem> zxSkInvoiceItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkInvoiceItemList != null && zxSkInvoiceItemList.size() > 0) {
           ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
           zxSkInvoiceItem.setModifyUserInfo(userKey, realName);
           flag = zxSkInvoiceItemMapper.batchDeleteUpdateZxSkInvoiceItem(zxSkInvoiceItemList, zxSkInvoiceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkInvoiceItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
