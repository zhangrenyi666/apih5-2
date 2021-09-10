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
import com.apih5.mybatis.dao.ZxSkStockTransItemWithdrawalMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemWithdrawal;
import com.apih5.service.ZxSkStockTransItemWithdrawalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemWithdrawalService")
public class ZxSkStockTransItemWithdrawalServiceImpl implements ZxSkStockTransItemWithdrawalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemWithdrawalMapper zxSkStockTransItemWithdrawalMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemWithdrawalListByCondition(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        if (zxSkStockTransItemWithdrawal == null) {
            zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemWithdrawal.getPage(),zxSkStockTransItemWithdrawal.getLimit());
        // 获取数据
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemWithdrawal> p = new PageInfo<>(zxSkStockTransItemWithdrawalList);

        return repEntity.okList(zxSkStockTransItemWithdrawalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemWithdrawalDetails(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        if (zxSkStockTransItemWithdrawal == null) {
            zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
        }
        // 获取数据
        ZxSkStockTransItemWithdrawal dbZxSkStockTransItemWithdrawal = zxSkStockTransItemWithdrawalMapper.selectByPrimaryKey(zxSkStockTransItemWithdrawal.getZxSkStockTransItemWithdrawalId());
        // 数据存在
        if (dbZxSkStockTransItemWithdrawal != null) {
            return repEntity.ok(dbZxSkStockTransItemWithdrawal);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemWithdrawal(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemWithdrawal.setZxSkStockTransItemWithdrawalId(UuidUtil.generate());
        zxSkStockTransItemWithdrawal.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemWithdrawalMapper.insert(zxSkStockTransItemWithdrawal);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemWithdrawal);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemWithdrawal(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemWithdrawal dbzxSkStockTransItemWithdrawal = zxSkStockTransItemWithdrawalMapper.selectByPrimaryKey(zxSkStockTransItemWithdrawal.getZxSkStockTransItemWithdrawalId());
        if (dbzxSkStockTransItemWithdrawal != null && StrUtil.isNotEmpty(dbzxSkStockTransItemWithdrawal.getZxSkStockTransItemWithdrawalId())) {
           // 单据
           dbzxSkStockTransItemWithdrawal.setBillID(zxSkStockTransItemWithdrawal.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemWithdrawal.setResID(zxSkStockTransItemWithdrawal.getResID());
           // 批次
           dbzxSkStockTransItemWithdrawal.setBatchNo(zxSkStockTransItemWithdrawal.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemWithdrawal.setResCode(zxSkStockTransItemWithdrawal.getResCode());
           // 物资名称
           dbzxSkStockTransItemWithdrawal.setResName(zxSkStockTransItemWithdrawal.getResName());
           // 规格型号
           dbzxSkStockTransItemWithdrawal.setSpec(zxSkStockTransItemWithdrawal.getSpec());
           // 计量单位
           dbzxSkStockTransItemWithdrawal.setResUnit(zxSkStockTransItemWithdrawal.getResUnit());
           // 标准单价
           dbzxSkStockTransItemWithdrawal.setStdPrice(zxSkStockTransItemWithdrawal.getStdPrice());
           // 其他费
           dbzxSkStockTransItemWithdrawal.setInFee(zxSkStockTransItemWithdrawal.getInFee());
           // 数量
           dbzxSkStockTransItemWithdrawal.setInQty(zxSkStockTransItemWithdrawal.getInQty());
           // 入库单价
           dbzxSkStockTransItemWithdrawal.setInPrice(zxSkStockTransItemWithdrawal.getInPrice());
           // 入账金额
           dbzxSkStockTransItemWithdrawal.setInAmt(zxSkStockTransItemWithdrawal.getInAmt());
           // 材质单编号
           dbzxSkStockTransItemWithdrawal.setMuID(zxSkStockTransItemWithdrawal.getMuID());
           // cbsID
           dbzxSkStockTransItemWithdrawal.setCbsID(zxSkStockTransItemWithdrawal.getCbsID());
           // 清单项ID
           dbzxSkStockTransItemWithdrawal.setWorkID(zxSkStockTransItemWithdrawal.getWorkID());
           // 管理单元名称(批次)
           dbzxSkStockTransItemWithdrawal.setMuName(zxSkStockTransItemWithdrawal.getMuName());
           // cbs名称
           dbzxSkStockTransItemWithdrawal.setCBSName(zxSkStockTransItemWithdrawal.getCBSName());
           // 清单名称
           dbzxSkStockTransItemWithdrawal.setWorkName(zxSkStockTransItemWithdrawal.getWorkName());
           // 数量
           dbzxSkStockTransItemWithdrawal.setOutQty(zxSkStockTransItemWithdrawal.getOutQty());
           // 其他费
           dbzxSkStockTransItemWithdrawal.setOutFee(zxSkStockTransItemWithdrawal.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemWithdrawal.setOutCostPrice(zxSkStockTransItemWithdrawal.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemWithdrawal.setOutCostAmt(zxSkStockTransItemWithdrawal.getOutCostAmt());
           // 单价
           dbzxSkStockTransItemWithdrawal.setOutPrice(zxSkStockTransItemWithdrawal.getOutPrice());
           // 金额
           dbzxSkStockTransItemWithdrawal.setOutAmt(zxSkStockTransItemWithdrawal.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemWithdrawal.setIsDepreciation(zxSkStockTransItemWithdrawal.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemWithdrawal.setRemark(zxSkStockTransItemWithdrawal.getRemark());
           // 金额
           dbzxSkStockTransItemWithdrawal.setInAmtAll(zxSkStockTransItemWithdrawal.getInAmtAll());
           // 不含税总价
           dbzxSkStockTransItemWithdrawal.setInAmtNoTax(zxSkStockTransItemWithdrawal.getInAmtNoTax());
           // 领料单id-领料单明细id
           dbzxSkStockTransItemWithdrawal.setMainIdAndItemId(zxSkStockTransItemWithdrawal.getMainIdAndItemId());
           // 共通
           dbzxSkStockTransItemWithdrawal.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemWithdrawalMapper.updateByPrimaryKey(dbzxSkStockTransItemWithdrawal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemWithdrawal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemWithdrawal(List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemWithdrawalList != null && zxSkStockTransItemWithdrawalList.size() > 0) {
           ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
           zxSkStockTransItemWithdrawal.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemWithdrawalMapper.batchDeleteUpdateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawalList, zxSkStockTransItemWithdrawal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemWithdrawalList);
        }
    }
}
