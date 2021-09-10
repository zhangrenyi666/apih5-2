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
import com.apih5.mybatis.dao.ZxSkStockTransItemMaterialRequisitionMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemMaterialRequisition;
import com.apih5.service.ZxSkStockTransItemMaterialRequisitionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemMaterialRequisitionService")
public class ZxSkStockTransItemMaterialRequisitionServiceImpl implements ZxSkStockTransItemMaterialRequisitionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemMaterialRequisitionMapper zxSkStockTransItemMaterialRequisitionMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemMaterialRequisitionListByCondition(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        if (zxSkStockTransItemMaterialRequisition == null) {
            zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemMaterialRequisition.getPage(),zxSkStockTransItemMaterialRequisition.getLimit());
        // 获取数据
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemMaterialRequisition> p = new PageInfo<>(zxSkStockTransItemMaterialRequisitionList);

        return repEntity.okList(zxSkStockTransItemMaterialRequisitionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemMaterialRequisitionDetails(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        if (zxSkStockTransItemMaterialRequisition == null) {
            zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
        }
        // 获取数据
        ZxSkStockTransItemMaterialRequisition dbZxSkStockTransItemMaterialRequisition = zxSkStockTransItemMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransItemMaterialRequisition.getId());
        // 数据存在
        if (dbZxSkStockTransItemMaterialRequisition != null) {
            return repEntity.ok(dbZxSkStockTransItemMaterialRequisition);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemMaterialRequisition(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemMaterialRequisition.setId(UuidUtil.generate());
        zxSkStockTransItemMaterialRequisition.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemMaterialRequisitionMapper.insert(zxSkStockTransItemMaterialRequisition);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemMaterialRequisition);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemMaterialRequisition(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemMaterialRequisition dbzxSkStockTransItemMaterialRequisition = zxSkStockTransItemMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransItemMaterialRequisition.getId());
        if (dbzxSkStockTransItemMaterialRequisition != null && StrUtil.isNotEmpty(dbzxSkStockTransItemMaterialRequisition.getId())) {
           // 单据
           dbzxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransItemMaterialRequisition.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemMaterialRequisition.setResID(zxSkStockTransItemMaterialRequisition.getResID());
           // 批次
           dbzxSkStockTransItemMaterialRequisition.setBatchNo(zxSkStockTransItemMaterialRequisition.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemMaterialRequisition.setResCode(zxSkStockTransItemMaterialRequisition.getResCode());
           // 物资名称
           dbzxSkStockTransItemMaterialRequisition.setResName(zxSkStockTransItemMaterialRequisition.getResName());
           // 规格型号
           dbzxSkStockTransItemMaterialRequisition.setSpec(zxSkStockTransItemMaterialRequisition.getSpec());
           // 计量单位
           dbzxSkStockTransItemMaterialRequisition.setResUnit(zxSkStockTransItemMaterialRequisition.getResUnit());
           // 采购单价
           dbzxSkStockTransItemMaterialRequisition.setStdPrice(zxSkStockTransItemMaterialRequisition.getStdPrice());
           // 入账其它费
           dbzxSkStockTransItemMaterialRequisition.setInFee(zxSkStockTransItemMaterialRequisition.getInFee());
           // 实收数量
           dbzxSkStockTransItemMaterialRequisition.setInQty(zxSkStockTransItemMaterialRequisition.getInQty());
           // 含税单价
           dbzxSkStockTransItemMaterialRequisition.setInPrice(zxSkStockTransItemMaterialRequisition.getInPrice());
           // 入账金额
           dbzxSkStockTransItemMaterialRequisition.setInAmt(zxSkStockTransItemMaterialRequisition.getInAmt());
           // 发票号
           dbzxSkStockTransItemMaterialRequisition.setMuID(zxSkStockTransItemMaterialRequisition.getMuID());
           // cbsID
           dbzxSkStockTransItemMaterialRequisition.setCbsID(zxSkStockTransItemMaterialRequisition.getCbsID());
           // 清单项ID
           dbzxSkStockTransItemMaterialRequisition.setWorkID(zxSkStockTransItemMaterialRequisition.getWorkID());
           // 管理单元名称
           dbzxSkStockTransItemMaterialRequisition.setMuName(zxSkStockTransItemMaterialRequisition.getMuName());
           // cbs名称
           dbzxSkStockTransItemMaterialRequisition.setCBSName(zxSkStockTransItemMaterialRequisition.getCBSName());
           // 清单名称
           dbzxSkStockTransItemMaterialRequisition.setWorkName(zxSkStockTransItemMaterialRequisition.getWorkName());
           // 数量
           dbzxSkStockTransItemMaterialRequisition.setOutQty(zxSkStockTransItemMaterialRequisition.getOutQty());
           // 其他费
           dbzxSkStockTransItemMaterialRequisition.setOutFee(zxSkStockTransItemMaterialRequisition.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemMaterialRequisition.setOutCostPrice(zxSkStockTransItemMaterialRequisition.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemMaterialRequisition.setOutCostAmt(zxSkStockTransItemMaterialRequisition.getOutCostAmt());
           // 出库单价
           dbzxSkStockTransItemMaterialRequisition.setOutPrice(zxSkStockTransItemMaterialRequisition.getOutPrice());
           // 总价
           dbzxSkStockTransItemMaterialRequisition.setOutAmt(zxSkStockTransItemMaterialRequisition.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemMaterialRequisition.setIsDepreciation(zxSkStockTransItemMaterialRequisition.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemMaterialRequisition.setRemark(zxSkStockTransItemMaterialRequisition.getRemark());
           // 材质单编号
           dbzxSkStockTransItemMaterialRequisition.setQualityNo(zxSkStockTransItemMaterialRequisition.getQualityNo());
           // 金额
           dbzxSkStockTransItemMaterialRequisition.setInAmtAll(zxSkStockTransItemMaterialRequisition.getInAmtAll());
           // 不含税总价
           dbzxSkStockTransItemMaterialRequisition.setInAmtNoTax(zxSkStockTransItemMaterialRequisition.getInAmtNoTax());
           // 库存数量
           dbzxSkStockTransItemMaterialRequisition.setStockQty(zxSkStockTransItemMaterialRequisition.getStockQty());
           // 可退数量
           dbzxSkStockTransItemMaterialRequisition.setIsOutNumber(zxSkStockTransItemMaterialRequisition.getIsOutNumber());
           // 共通
           dbzxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemMaterialRequisitionMapper.updateByPrimaryKey(dbzxSkStockTransItemMaterialRequisition);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemMaterialRequisition);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemMaterialRequisition(List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemMaterialRequisitionList != null && zxSkStockTransItemMaterialRequisitionList.size() > 0) {
           ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
           zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransItemMaterialRequisition(zxSkStockTransItemMaterialRequisitionList, zxSkStockTransItemMaterialRequisition);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemMaterialRequisitionList);
        }
    }
}
