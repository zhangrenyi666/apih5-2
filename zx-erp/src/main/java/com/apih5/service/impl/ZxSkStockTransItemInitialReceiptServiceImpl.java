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
import com.apih5.mybatis.dao.ZxSkStockTransItemInitialReceiptMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemInitialReceipt;
import com.apih5.service.ZxSkStockTransItemInitialReceiptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemInitialReceiptService")
public class ZxSkStockTransItemInitialReceiptServiceImpl implements ZxSkStockTransItemInitialReceiptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptMapper zxSkStockTransItemInitialReceiptMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemInitialReceiptListByCondition(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        if (zxSkStockTransItemInitialReceipt == null) {
            zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemInitialReceipt.getPage(),zxSkStockTransItemInitialReceipt.getLimit());
        // 获取数据
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemInitialReceipt> p = new PageInfo<>(zxSkStockTransItemInitialReceiptList);

        return repEntity.okList(zxSkStockTransItemInitialReceiptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemInitialReceiptDetails(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        if (zxSkStockTransItemInitialReceipt == null) {
            zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
        }
        // 获取数据
        ZxSkStockTransItemInitialReceipt dbZxSkStockTransItemInitialReceipt = zxSkStockTransItemInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransItemInitialReceipt.getId());
        // 数据存在
        if (dbZxSkStockTransItemInitialReceipt != null) {
            return repEntity.ok(dbZxSkStockTransItemInitialReceipt);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemInitialReceipt(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemInitialReceipt.setId(UuidUtil.generate());
        zxSkStockTransItemInitialReceipt.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemInitialReceiptMapper.insert(zxSkStockTransItemInitialReceipt);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemInitialReceipt);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemInitialReceipt(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemInitialReceipt dbzxSkStockTransItemInitialReceipt = zxSkStockTransItemInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransItemInitialReceipt.getId());
        if (dbzxSkStockTransItemInitialReceipt != null && StrUtil.isNotEmpty(dbzxSkStockTransItemInitialReceipt.getId())) {
           // 单据
           dbzxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransItemInitialReceipt.getBillID());
           // 物资编号ID
           dbzxSkStockTransItemInitialReceipt.setResID(zxSkStockTransItemInitialReceipt.getResID());
           // 物资编码
           dbzxSkStockTransItemInitialReceipt.setResCode(zxSkStockTransItemInitialReceipt.getResCode());
           // 物资名称
           dbzxSkStockTransItemInitialReceipt.setResName(zxSkStockTransItemInitialReceipt.getResName());
           // 物资规格
           dbzxSkStockTransItemInitialReceipt.setSpec(zxSkStockTransItemInitialReceipt.getSpec());
           // 计量单位
           dbzxSkStockTransItemInitialReceipt.setResUnit(zxSkStockTransItemInitialReceipt.getResUnit());
           // 应收数量
           dbzxSkStockTransItemInitialReceipt.setShouldQty(zxSkStockTransItemInitialReceipt.getShouldQty());
           // 实收数量
           dbzxSkStockTransItemInitialReceipt.setInQty(zxSkStockTransItemInitialReceipt.getInQty());
           // 采购单价
           dbzxSkStockTransItemInitialReceipt.setStdPrice(zxSkStockTransItemInitialReceipt.getStdPrice());
           // 不含税单价
           dbzxSkStockTransItemInitialReceipt.setInPriceNoTax(zxSkStockTransItemInitialReceipt.getInPriceNoTax());
           // 不含税金额
           dbzxSkStockTransItemInitialReceipt.setResAllFeeNoTax(zxSkStockTransItemInitialReceipt.getResAllFeeNoTax());
           // 税额
           dbzxSkStockTransItemInitialReceipt.setResAllFeeTax(zxSkStockTransItemInitialReceipt.getResAllFeeTax());
           // 含税单价
           dbzxSkStockTransItemInitialReceipt.setInPrice(zxSkStockTransItemInitialReceipt.getInPrice());
           // 含税金额
           dbzxSkStockTransItemInitialReceipt.setResAllFee(zxSkStockTransItemInitialReceipt.getResAllFee());
           // 其他费
           dbzxSkStockTransItemInitialReceipt.setInFee(zxSkStockTransItemInitialReceipt.getInFee());
           // 含税总价
           dbzxSkStockTransItemInitialReceipt.setInAmtAll(zxSkStockTransItemInitialReceipt.getInAmtAll());
           // 不含税总价
           dbzxSkStockTransItemInitialReceipt.setInAmtNoTax(zxSkStockTransItemInitialReceipt.getInAmtNoTax());
           // 入账金额
           dbzxSkStockTransItemInitialReceipt.setInAmt(zxSkStockTransItemInitialReceipt.getInAmt());
           // 数量
           dbzxSkStockTransItemInitialReceipt.setOutQty(zxSkStockTransItemInitialReceipt.getOutQty());
           // 材质单编号
           dbzxSkStockTransItemInitialReceipt.setQualityNo(zxSkStockTransItemInitialReceipt.getQualityNo());
           // 单价
           dbzxSkStockTransItemInitialReceipt.setOutPrice(zxSkStockTransItemInitialReceipt.getOutPrice());
           // 其他费
           dbzxSkStockTransItemInitialReceipt.setOutFee(zxSkStockTransItemInitialReceipt.getOutFee());
           // 金额
           dbzxSkStockTransItemInitialReceipt.setOutAmt(zxSkStockTransItemInitialReceipt.getOutAmt());
           // 批次
           dbzxSkStockTransItemInitialReceipt.setBatchNo(zxSkStockTransItemInitialReceipt.getBatchNo());
           // 管理单元ID
           dbzxSkStockTransItemInitialReceipt.setMuID(zxSkStockTransItemInitialReceipt.getMuID());
           // 管理单元名称
           dbzxSkStockTransItemInitialReceipt.setMuName(zxSkStockTransItemInitialReceipt.getMuName());
           // 清单项ID
           dbzxSkStockTransItemInitialReceipt.setWorkID(zxSkStockTransItemInitialReceipt.getWorkID());
           // 清单名称
           dbzxSkStockTransItemInitialReceipt.setWorkName(zxSkStockTransItemInitialReceipt.getWorkName());
           // 出库成本价
           dbzxSkStockTransItemInitialReceipt.setOutCostPrice(zxSkStockTransItemInitialReceipt.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemInitialReceipt.setOutCostAmt(zxSkStockTransItemInitialReceipt.getOutCostAmt());
           // 是否计提折旧
           dbzxSkStockTransItemInitialReceipt.setIsDepreciation(zxSkStockTransItemInitialReceipt.getIsDepreciation());
           // 是否预收
           dbzxSkStockTransItemInitialReceipt.setPrecollecte(zxSkStockTransItemInitialReceipt.getPrecollecte());
           // 备注
           dbzxSkStockTransItemInitialReceipt.setRemark(zxSkStockTransItemInitialReceipt.getRemark());
           // 冲账累积数量
           dbzxSkStockTransItemInitialReceipt.setInvoiceCumulateQty(zxSkStockTransItemInitialReceipt.getInvoiceCumulateQty());
           // 冲账累积金额
           dbzxSkStockTransItemInitialReceipt.setInvoiceCumulateAmt(zxSkStockTransItemInitialReceipt.getInvoiceCumulateAmt());
           // 可退数量
           dbzxSkStockTransItemInitialReceipt.setIsOutNumber(zxSkStockTransItemInitialReceipt.getIsOutNumber());
           // 共通
           dbzxSkStockTransItemInitialReceipt.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemInitialReceiptMapper.updateByPrimaryKey(dbzxSkStockTransItemInitialReceipt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemInitialReceipt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemInitialReceipt(List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemInitialReceiptList != null && zxSkStockTransItemInitialReceiptList.size() > 0) {
           ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
           zxSkStockTransItemInitialReceipt.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemInitialReceiptMapper.batchDeleteUpdateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceiptList, zxSkStockTransItemInitialReceipt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemInitialReceiptList);
        }
    }
}
