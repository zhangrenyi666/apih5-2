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
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettlementMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;
import com.apih5.service.ZxCtSuppliesLeaseResSettlementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesLeaseResSettlementService")
public class ZxCtSuppliesLeaseResSettlementServiceImpl implements ZxCtSuppliesLeaseResSettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettlementMapper zxCtSuppliesLeaseResSettlementMapper;

    @Override
    public ResponseEntity getZxCtSuppliesLeaseResSettlementListByCondition(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        if (zxCtSuppliesLeaseResSettlement == null) {
            zxCtSuppliesLeaseResSettlement = new ZxCtSuppliesLeaseResSettlement();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesLeaseResSettlement.getPage(),zxCtSuppliesLeaseResSettlement.getLimit());
        // 获取数据
        List<ZxCtSuppliesLeaseResSettlement> zxCtSuppliesLeaseResSettlementList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(zxCtSuppliesLeaseResSettlement);
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeaseResSettlement> p = new PageInfo<>(zxCtSuppliesLeaseResSettlementList);

        return repEntity.okList(zxCtSuppliesLeaseResSettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeaseResSettlementDetail(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        if (zxCtSuppliesLeaseResSettlement == null) {
            zxCtSuppliesLeaseResSettlement = new ZxCtSuppliesLeaseResSettlement();
        }
        // 获取数据
        ZxCtSuppliesLeaseResSettlement dbZxCtSuppliesLeaseResSettlement = zxCtSuppliesLeaseResSettlementMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettlement.getZxCtSuppliesLeaseResSettlementId());
        // 数据存在
        if (dbZxCtSuppliesLeaseResSettlement != null) {
            return repEntity.ok(dbZxCtSuppliesLeaseResSettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesLeaseResSettlement(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesLeaseResSettlement.setZxCtSuppliesLeaseResSettlementId(UuidUtil.generate());
        zxCtSuppliesLeaseResSettlement.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeaseResSettlementMapper.insert(zxCtSuppliesLeaseResSettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeaseResSettlement);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesLeaseResSettlement(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeaseResSettlement dbZxCtSuppliesLeaseResSettlement = zxCtSuppliesLeaseResSettlementMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettlement.getZxCtSuppliesLeaseResSettlementId());
        if (dbZxCtSuppliesLeaseResSettlement != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseResSettlement.getZxCtSuppliesLeaseResSettlementId())) {
           // 最后编辑时间
           dbZxCtSuppliesLeaseResSettlement.setEditTime(zxCtSuppliesLeaseResSettlement.getEditTime());
           // 所属公司排序
           dbZxCtSuppliesLeaseResSettlement.setComOrders(zxCtSuppliesLeaseResSettlement.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeaseResSettlement.setComName(zxCtSuppliesLeaseResSettlement.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeaseResSettlement.setComID(zxCtSuppliesLeaseResSettlement.getComID());
           // 上期末清单结算金额(元)
           dbZxCtSuppliesLeaseResSettlement.setUpAmt(zxCtSuppliesLeaseResSettlement.getUpAmt());
           // 结算期次
           dbZxCtSuppliesLeaseResSettlement.setPeriod(zxCtSuppliesLeaseResSettlement.getPeriod());
           // 合同ID
           dbZxCtSuppliesLeaseResSettlement.setContractID(zxCtSuppliesLeaseResSettlement.getContractID());
           // 含税合同金额(万)
           dbZxCtSuppliesLeaseResSettlement.setContractAmt(zxCtSuppliesLeaseResSettlement.getContractAmt());
           // 本期清单结算含税金额(元)
           dbZxCtSuppliesLeaseResSettlement.setThisAmt(zxCtSuppliesLeaseResSettlement.getThisAmt());
           // 累计清单结算含税金额(元)
           dbZxCtSuppliesLeaseResSettlement.setTotalAmt(zxCtSuppliesLeaseResSettlement.getTotalAmt());
           // 项目ID
           dbZxCtSuppliesLeaseResSettlement.setOrgID(zxCtSuppliesLeaseResSettlement.getOrgID());
           // 项目名称
           dbZxCtSuppliesLeaseResSettlement.setOrgName(zxCtSuppliesLeaseResSettlement.getOrgName());
           // 租赁类结算表ID
           dbZxCtSuppliesLeaseResSettlement.setBillID(zxCtSuppliesLeaseResSettlement.getBillID());
           // 结算单编号
           dbZxCtSuppliesLeaseResSettlement.setBillNo(zxCtSuppliesLeaseResSettlement.getBillNo());
           // 签认单编号
           dbZxCtSuppliesLeaseResSettlement.setSignedNos(zxCtSuppliesLeaseResSettlement.getSignedNos());
           // 本期清单结算不含税金额(元)
           dbZxCtSuppliesLeaseResSettlement.setThisAmtNoTax(zxCtSuppliesLeaseResSettlement.getThisAmtNoTax());
           // 本期清单结算税额(元)
           dbZxCtSuppliesLeaseResSettlement.setThisAmtTax(zxCtSuppliesLeaseResSettlement.getThisAmtTax());
           // 签认单ID
           dbZxCtSuppliesLeaseResSettlement.setSignedOrders(zxCtSuppliesLeaseResSettlement.getSignedOrders());
           // 备注
           dbZxCtSuppliesLeaseResSettlement.setRemarks(zxCtSuppliesLeaseResSettlement.getRemarks());
           // 排序
           dbZxCtSuppliesLeaseResSettlement.setSort(zxCtSuppliesLeaseResSettlement.getSort());
           // 共通
           dbZxCtSuppliesLeaseResSettlement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettlementMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseResSettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseResSettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseResSettlement(List<ZxCtSuppliesLeaseResSettlement> zxCtSuppliesLeaseResSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesLeaseResSettlementList != null && zxCtSuppliesLeaseResSettlementList.size() > 0) {
           ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement = new ZxCtSuppliesLeaseResSettlement();
           zxCtSuppliesLeaseResSettlement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettlementMapper.batchDeleteUpdateZxCtSuppliesLeaseResSettlement(zxCtSuppliesLeaseResSettlementList, zxCtSuppliesLeaseResSettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeaseResSettlementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
