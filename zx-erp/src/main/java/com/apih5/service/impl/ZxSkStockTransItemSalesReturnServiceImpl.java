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
import com.apih5.mybatis.dao.ZxSkStockTransItemSalesReturnMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;
import com.apih5.service.ZxSkStockTransItemSalesReturnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemSalesReturnService")
public class ZxSkStockTransItemSalesReturnServiceImpl implements ZxSkStockTransItemSalesReturnService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemSalesReturnMapper zxSkStockTransItemSalesReturnMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemSalesReturnListByCondition(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        if (zxSkStockTransItemSalesReturn == null) {
            zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemSalesReturn.getPage(),zxSkStockTransItemSalesReturn.getLimit());
        // 获取数据
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemSalesReturn> p = new PageInfo<>(zxSkStockTransItemSalesReturnList);

        return repEntity.okList(zxSkStockTransItemSalesReturnList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemSalesReturnDetails(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        if (zxSkStockTransItemSalesReturn == null) {
            zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
        }
        // 获取数据
        ZxSkStockTransItemSalesReturn dbZxSkStockTransItemSalesReturn = zxSkStockTransItemSalesReturnMapper.selectByPrimaryKey(zxSkStockTransItemSalesReturn.getZxSkStockTransItemSalesReturnId());
        // 数据存在
        if (dbZxSkStockTransItemSalesReturn != null) {
            return repEntity.ok(dbZxSkStockTransItemSalesReturn);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemSalesReturn(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId(UuidUtil.generate());
        zxSkStockTransItemSalesReturn.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemSalesReturnMapper.insert(zxSkStockTransItemSalesReturn);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemSalesReturn);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemSalesReturn(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemSalesReturn dbzxSkStockTransItemSalesReturn = zxSkStockTransItemSalesReturnMapper.selectByPrimaryKey(zxSkStockTransItemSalesReturn.getZxSkStockTransItemSalesReturnId());
        if (dbzxSkStockTransItemSalesReturn != null && StrUtil.isNotEmpty(dbzxSkStockTransItemSalesReturn.getZxSkStockTransItemSalesReturnId())) {
           // 单据
           dbzxSkStockTransItemSalesReturn.setBillID(zxSkStockTransItemSalesReturn.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemSalesReturn.setResID(zxSkStockTransItemSalesReturn.getResID());
           // 批次
           dbzxSkStockTransItemSalesReturn.setBatchNo(zxSkStockTransItemSalesReturn.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemSalesReturn.setResCode(zxSkStockTransItemSalesReturn.getResCode());
           // 物资名称
           dbzxSkStockTransItemSalesReturn.setResName(zxSkStockTransItemSalesReturn.getResName());
           // 物资型号
           dbzxSkStockTransItemSalesReturn.setSpec(zxSkStockTransItemSalesReturn.getSpec());
           // 计量单位
           dbzxSkStockTransItemSalesReturn.setResUnit(zxSkStockTransItemSalesReturn.getResUnit());
           // 标准单价
           dbzxSkStockTransItemSalesReturn.setStdPrice(zxSkStockTransItemSalesReturn.getStdPrice());
           // 其他费
           dbzxSkStockTransItemSalesReturn.setInFee(zxSkStockTransItemSalesReturn.getInFee());
           // 退货数量
           dbzxSkStockTransItemSalesReturn.setInQty(zxSkStockTransItemSalesReturn.getInQty());
           // 含税单价
           dbzxSkStockTransItemSalesReturn.setInPrice(zxSkStockTransItemSalesReturn.getInPrice());
           // 退账金额
           dbzxSkStockTransItemSalesReturn.setInAmt(zxSkStockTransItemSalesReturn.getInAmt());
           // 管理单元ID
           dbzxSkStockTransItemSalesReturn.setMuID(zxSkStockTransItemSalesReturn.getMuID());
           // 清单名称ID
           dbzxSkStockTransItemSalesReturn.setWorkID(zxSkStockTransItemSalesReturn.getWorkID());
           // 管理单元名称
           dbzxSkStockTransItemSalesReturn.setMuName(zxSkStockTransItemSalesReturn.getMuName());
           // 清单名称
           dbzxSkStockTransItemSalesReturn.setWorkName(zxSkStockTransItemSalesReturn.getWorkName());
           // 退货数量
           dbzxSkStockTransItemSalesReturn.setOutQty(zxSkStockTransItemSalesReturn.getOutQty());
           // 其他费
           dbzxSkStockTransItemSalesReturn.setOutFee(zxSkStockTransItemSalesReturn.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemSalesReturn.setOutCostPrice(zxSkStockTransItemSalesReturn.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemSalesReturn.setOutCostAmt(zxSkStockTransItemSalesReturn.getOutCostAmt());
           // 单价
           dbzxSkStockTransItemSalesReturn.setOutPrice(zxSkStockTransItemSalesReturn.getOutPrice());
           // 金额
           dbzxSkStockTransItemSalesReturn.setOutAmt(zxSkStockTransItemSalesReturn.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemSalesReturn.setIsDepreciation(zxSkStockTransItemSalesReturn.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemSalesReturn.setRemark(zxSkStockTransItemSalesReturn.getRemark());
           // 含税金额
           dbzxSkStockTransItemSalesReturn.setResAllFee(zxSkStockTransItemSalesReturn.getResAllFee());
           // 对应收料单ID
           dbzxSkStockTransItemSalesReturn.setItemID(zxSkStockTransItemSalesReturn.getItemID());
           // 总价
           dbzxSkStockTransItemSalesReturn.setInAmtAll(zxSkStockTransItemSalesReturn.getInAmtAll());
           // 不含税单价
           dbzxSkStockTransItemSalesReturn.setInPriceNoTax(zxSkStockTransItemSalesReturn.getInPriceNoTax());
           // 不含税金额
           dbzxSkStockTransItemSalesReturn.setResAllFeeNoTax(zxSkStockTransItemSalesReturn.getResAllFeeNoTax());
           // 税额
           dbzxSkStockTransItemSalesReturn.setResAllFeeTax(zxSkStockTransItemSalesReturn.getResAllFeeTax());
           // 运输费
           dbzxSkStockTransItemSalesReturn.setYsFee(zxSkStockTransItemSalesReturn.getYsFee());
           // 退货税额
           dbzxSkStockTransItemSalesReturn.setInAmtTax(zxSkStockTransItemSalesReturn.getInAmtTax());
           // 领料单id-领料单明细id
           dbzxSkStockTransItemSalesReturn.setMainIdAndItemId(zxSkStockTransItemSalesReturn.getMainIdAndItemId());
           // 结算状态
           dbzxSkStockTransItemSalesReturn.setSettlementStatus(zxSkStockTransItemSalesReturn.getSettlementStatus());
           // 共通
           dbzxSkStockTransItemSalesReturn.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemSalesReturnMapper.updateByPrimaryKey(dbzxSkStockTransItemSalesReturn);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemSalesReturn);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemSalesReturn(List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemSalesReturnList != null && zxSkStockTransItemSalesReturnList.size() > 0) {
           ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
           zxSkStockTransItemSalesReturn.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemSalesReturnMapper.batchDeleteUpdateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturnList, zxSkStockTransItemSalesReturn);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemSalesReturnList);
        }
    }
}
