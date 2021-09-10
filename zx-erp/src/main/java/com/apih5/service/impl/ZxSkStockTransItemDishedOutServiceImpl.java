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
import com.apih5.mybatis.dao.ZxSkStockTransItemDishedOutMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemDishedOut;
import com.apih5.service.ZxSkStockTransItemDishedOutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemDishedOutService")
public class ZxSkStockTransItemDishedOutServiceImpl implements ZxSkStockTransItemDishedOutService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemDishedOutMapper zxSkStockTransItemDishedOutMapper;



    @Override
    public ResponseEntity getZxSkStockTransItemDishedOutListByCondition(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        if (zxSkStockTransItemDishedOut == null) {
            zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemDishedOut.getPage(),zxSkStockTransItemDishedOut.getLimit());
        // 获取数据
        List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList = zxSkStockTransItemDishedOutMapper.selectByZxSkStockTransItemDishedOutList(zxSkStockTransItemDishedOut);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemDishedOut> p = new PageInfo<>(zxSkStockTransItemDishedOutList);

        return repEntity.okList(zxSkStockTransItemDishedOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemDishedOutDetails(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        if (zxSkStockTransItemDishedOut == null) {
            zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
        }
        // 获取数据
        ZxSkStockTransItemDishedOut dbZxSkStockTransItemDishedOut = zxSkStockTransItemDishedOutMapper.selectByPrimaryKey(zxSkStockTransItemDishedOut.getId());
        // 数据存在
        if (dbZxSkStockTransItemDishedOut != null) {
            return repEntity.ok(dbZxSkStockTransItemDishedOut);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemDishedOut(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemDishedOut.setId(UuidUtil.generate());
        zxSkStockTransItemDishedOut.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemDishedOutMapper.insert(zxSkStockTransItemDishedOut);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemDishedOut);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemDishedOut(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemDishedOut dbzxSkStockTransItemDishedOut = zxSkStockTransItemDishedOutMapper.selectByPrimaryKey(zxSkStockTransItemDishedOut.getId());
        if (dbzxSkStockTransItemDishedOut != null && StrUtil.isNotEmpty(dbzxSkStockTransItemDishedOut.getId())) {
           // 单据
           dbzxSkStockTransItemDishedOut.setBillID(zxSkStockTransItemDishedOut.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemDishedOut.setResID(zxSkStockTransItemDishedOut.getResID());
           // 批次
           dbzxSkStockTransItemDishedOut.setBatchNo(zxSkStockTransItemDishedOut.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemDishedOut.setResCode(zxSkStockTransItemDishedOut.getResCode());
           // 物资名称
           dbzxSkStockTransItemDishedOut.setResName(zxSkStockTransItemDishedOut.getResName());
           // 规格型号
           dbzxSkStockTransItemDishedOut.setSpec(zxSkStockTransItemDishedOut.getSpec());
           // 计量单位
           dbzxSkStockTransItemDishedOut.setResUnit(zxSkStockTransItemDishedOut.getResUnit());
           // 标准单价
           dbzxSkStockTransItemDishedOut.setStdPrice(zxSkStockTransItemDishedOut.getStdPrice());
           // 其它费
           dbzxSkStockTransItemDishedOut.setInFee(zxSkStockTransItemDishedOut.getInFee());
           // 数量
           dbzxSkStockTransItemDishedOut.setInQty(zxSkStockTransItemDishedOut.getInQty());
           // 单价
           dbzxSkStockTransItemDishedOut.setInPrice(zxSkStockTransItemDishedOut.getInPrice());
           // 入账金额
           dbzxSkStockTransItemDishedOut.setInAmt(zxSkStockTransItemDishedOut.getInAmt());
           // cbsID
           dbzxSkStockTransItemDishedOut.setMuID(zxSkStockTransItemDishedOut.getMuID());
           // 清单项ID
           dbzxSkStockTransItemDishedOut.setWorkID(zxSkStockTransItemDishedOut.getWorkID());
           // 工程明细
           dbzxSkStockTransItemDishedOut.setMuName(zxSkStockTransItemDishedOut.getMuName());
           // 清单名称
           dbzxSkStockTransItemDishedOut.setWorkName(zxSkStockTransItemDishedOut.getWorkName());
           // 数量
           dbzxSkStockTransItemDishedOut.setOutQty(zxSkStockTransItemDishedOut.getOutQty());
           // 其他费
           dbzxSkStockTransItemDishedOut.setOutFee(zxSkStockTransItemDishedOut.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemDishedOut.setOutCostPrice(zxSkStockTransItemDishedOut.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemDishedOut.setOutCostAmt(zxSkStockTransItemDishedOut.getOutCostAmt());
           // 单价
           dbzxSkStockTransItemDishedOut.setOutPrice(zxSkStockTransItemDishedOut.getOutPrice());
           // 金额
           dbzxSkStockTransItemDishedOut.setOutAmt(zxSkStockTransItemDishedOut.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemDishedOut.setIsDepreciation(zxSkStockTransItemDishedOut.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemDishedOut.setRemark(zxSkStockTransItemDishedOut.getRemark());
           // 不含税总价
           dbzxSkStockTransItemDishedOut.setInAmtNoTax(zxSkStockTransItemDishedOut.getInAmtNoTax());
           // 共通
           dbzxSkStockTransItemDishedOut.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemDishedOutMapper.updateByPrimaryKey(dbzxSkStockTransItemDishedOut);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemDishedOut);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemDishedOut(List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemDishedOutList != null && zxSkStockTransItemDishedOutList.size() > 0) {
           ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut = new ZxSkStockTransItemDishedOut();
           zxSkStockTransItemDishedOut.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemDishedOutMapper.batchDeleteUpdateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOutList, zxSkStockTransItemDishedOut);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemDishedOutList);
        }
    }
}
