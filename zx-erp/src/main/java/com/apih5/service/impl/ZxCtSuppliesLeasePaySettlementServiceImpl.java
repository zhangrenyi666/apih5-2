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
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;
import com.apih5.service.ZxCtSuppliesLeasePaySettlementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesLeasePaySettlementService")
public class ZxCtSuppliesLeasePaySettlementServiceImpl implements ZxCtSuppliesLeasePaySettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementMapper zxCtSuppliesLeasePaySettlementMapper;

    @Override
    public ResponseEntity getZxCtSuppliesLeasePaySettlementListByCondition(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        if (zxCtSuppliesLeasePaySettlement == null) {
            zxCtSuppliesLeasePaySettlement = new ZxCtSuppliesLeasePaySettlement();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesLeasePaySettlement.getPage(),zxCtSuppliesLeasePaySettlement.getLimit());
        // 获取数据
        List<ZxCtSuppliesLeasePaySettlement> zxCtSuppliesLeasePaySettlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(zxCtSuppliesLeasePaySettlement);
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeasePaySettlement> p = new PageInfo<>(zxCtSuppliesLeasePaySettlementList);

        return repEntity.okList(zxCtSuppliesLeasePaySettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeasePaySettlementDetail(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        if (zxCtSuppliesLeasePaySettlement == null) {
            zxCtSuppliesLeasePaySettlement = new ZxCtSuppliesLeasePaySettlement();
        }
        // 获取数据
        ZxCtSuppliesLeasePaySettlement dbZxCtSuppliesLeasePaySettlement = zxCtSuppliesLeasePaySettlementMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlement.getZxCtSuppliesLeasePaySettlementId());
        // 数据存在
        if (dbZxCtSuppliesLeasePaySettlement != null) {
            return repEntity.ok(dbZxCtSuppliesLeasePaySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesLeasePaySettlement(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesLeasePaySettlement.setZxCtSuppliesLeasePaySettlementId(UuidUtil.generate());
        zxCtSuppliesLeasePaySettlement.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeasePaySettlementMapper.insert(zxCtSuppliesLeasePaySettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeasePaySettlement);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesLeasePaySettlement(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeasePaySettlement dbZxCtSuppliesLeasePaySettlement = zxCtSuppliesLeasePaySettlementMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlement.getZxCtSuppliesLeasePaySettlementId());
        if (dbZxCtSuppliesLeasePaySettlement != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeasePaySettlement.getZxCtSuppliesLeasePaySettlementId())) {
           // 最后编辑时间
           dbZxCtSuppliesLeasePaySettlement.setEditTime(zxCtSuppliesLeasePaySettlement.getEditTime());
           // 运杂费本期结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setInOutAmt(zxCtSuppliesLeasePaySettlement.getInOutAmt());
           // 项目名称
           dbZxCtSuppliesLeasePaySettlement.setOrgName(zxCtSuppliesLeasePaySettlement.getOrgName());
           // 项目ID
           dbZxCtSuppliesLeasePaySettlement.setOrgID(zxCtSuppliesLeasePaySettlement.getOrgID());
           // 所属公司排序
           dbZxCtSuppliesLeasePaySettlement.setComOrders(zxCtSuppliesLeasePaySettlement.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeasePaySettlement.setComName(zxCtSuppliesLeasePaySettlement.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeasePaySettlement.setComID(zxCtSuppliesLeasePaySettlement.getComID());
           // 损耗本期结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setFoodAmt(zxCtSuppliesLeasePaySettlement.getFoodAmt());
           // 上期运杂费结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setUpInOutAmt(zxCtSuppliesLeasePaySettlement.getUpInOutAmt());
           // 上期损耗结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setUpFoodAmt(zxCtSuppliesLeasePaySettlement.getUpFoodAmt());
           // 上期其它款项结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setUpOtherAmt(zxCtSuppliesLeasePaySettlement.getUpOtherAmt());
           // 上期末累计支付项结算金额(元)
           dbZxCtSuppliesLeasePaySettlement.setUpAmt(zxCtSuppliesLeasePaySettlement.getUpAmt());
           // 上期奖罚结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setUpFineAmt(zxCtSuppliesLeasePaySettlement.getUpFineAmt());
           // 其它款项本期结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setOtherAmt(zxCtSuppliesLeasePaySettlement.getOtherAmt());
           // 累计支付项结算金额(元)
           dbZxCtSuppliesLeasePaySettlement.setTotalAmt(zxCtSuppliesLeasePaySettlement.getTotalAmt());
           // 结算期次
           dbZxCtSuppliesLeasePaySettlement.setPeriod(zxCtSuppliesLeasePaySettlement.getPeriod());
           // 结算单编号
           dbZxCtSuppliesLeasePaySettlement.setBillNo(zxCtSuppliesLeasePaySettlement.getBillNo());
           // 结算表ID
           dbZxCtSuppliesLeasePaySettlement.setBillID(zxCtSuppliesLeasePaySettlement.getBillID());
           // 奖罚本期结算小计(元)
           dbZxCtSuppliesLeasePaySettlement.setFineAmt(zxCtSuppliesLeasePaySettlement.getFineAmt());
           // 合同类型
           dbZxCtSuppliesLeasePaySettlement.setContrType(zxCtSuppliesLeasePaySettlement.getContrType());
           // 合同ID
           dbZxCtSuppliesLeasePaySettlement.setContractID(zxCtSuppliesLeasePaySettlement.getContractID());
           // 本期支付项结算税额(元)
           dbZxCtSuppliesLeasePaySettlement.setThisAmtTax(zxCtSuppliesLeasePaySettlement.getThisAmtTax());
           // 本期支付项结算金额(元)
           dbZxCtSuppliesLeasePaySettlement.setThisAmt(zxCtSuppliesLeasePaySettlement.getThisAmt());
           // 本期支付项结算不含税金额(元)
           dbZxCtSuppliesLeasePaySettlement.setThisAmtNoTax(zxCtSuppliesLeasePaySettlement.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesLeasePaySettlement.setRemarks(zxCtSuppliesLeasePaySettlement.getRemarks());
           // 排序
           dbZxCtSuppliesLeasePaySettlement.setSort(zxCtSuppliesLeasePaySettlement.getSort());
           // 共通
           dbZxCtSuppliesLeasePaySettlement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementMapper.updateByPrimaryKey(dbZxCtSuppliesLeasePaySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeasePaySettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlement(List<ZxCtSuppliesLeasePaySettlement> zxCtSuppliesLeasePaySettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesLeasePaySettlementList != null && zxCtSuppliesLeasePaySettlementList.size() > 0) {
           ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement = new ZxCtSuppliesLeasePaySettlement();
           zxCtSuppliesLeasePaySettlement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementMapper.batchDeleteUpdateZxCtSuppliesLeasePaySettlement(zxCtSuppliesLeasePaySettlementList, zxCtSuppliesLeasePaySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeasePaySettlementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
