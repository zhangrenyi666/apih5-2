package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementMapper;
import com.apih5.mybatis.dao.ZxSaProjectSetItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSetMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;
import com.apih5.mybatis.pojo.ZxSaProjectSet;
import com.apih5.mybatis.pojo.ZxSaProjectSetItem;
import com.apih5.service.ZxCtSuppliesShopPaySettlementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesShopPaySettlementService")
public class ZxCtSuppliesShopPaySettlementServiceImpl implements ZxCtSuppliesShopPaySettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementMapper zxCtSuppliesShopPaySettlementMapper;

    @Autowired(required = true)
    private ZxSaProjectSetMapper zxSaProjectSetMapper;

    @Autowired(required = true)
    private ZxSaProjectSetItemMapper zxSaProjectSetItemMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesShopPaySettlementListByCondition(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        if (zxCtSuppliesShopPaySettlement == null) {
            zxCtSuppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlement();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopPaySettlement.getPage(),zxCtSuppliesShopPaySettlement.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopPaySettlement> zxCtSuppliesShopPaySettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(zxCtSuppliesShopPaySettlement);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopPaySettlement> p = new PageInfo<>(zxCtSuppliesShopPaySettlementList);

        return repEntity.okList(zxCtSuppliesShopPaySettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopPaySettlementDetail(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        if (zxCtSuppliesShopPaySettlement == null) {
            zxCtSuppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlement();
        }
        // 获取数据
        ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = zxCtSuppliesShopPaySettlementMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
        // 数据存在
        if (dbZxCtSuppliesShopPaySettlement != null) {
            return repEntity.ok(dbZxCtSuppliesShopPaySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopPaySettlement(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopPaySettlement.setZxCtSuppliesShopPaySettlementId(UuidUtil.generate());
        zxCtSuppliesShopPaySettlement.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopPaySettlementMapper.insert(zxCtSuppliesShopPaySettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopPaySettlement);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopPaySettlement(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = zxCtSuppliesShopPaySettlementMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
        if (dbZxCtSuppliesShopPaySettlement != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId())) {
           // 最后编辑时间
           dbZxCtSuppliesShopPaySettlement.setEditTime(zxCtSuppliesShopPaySettlement.getEditTime());
           // 项目名称
           dbZxCtSuppliesShopPaySettlement.setOrgName(zxCtSuppliesShopPaySettlement.getOrgName());
           // 项目ID
           dbZxCtSuppliesShopPaySettlement.setOrgID(zxCtSuppliesShopPaySettlement.getOrgID());
           // 物资结算表ID
           dbZxCtSuppliesShopPaySettlement.setBillID(zxCtSuppliesShopPaySettlement.getBillID());
           // 所属公司排序
           dbZxCtSuppliesShopPaySettlement.setComOrders(zxCtSuppliesShopPaySettlement.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopPaySettlement.setComName(zxCtSuppliesShopPaySettlement.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopPaySettlement.setComID(zxCtSuppliesShopPaySettlement.getComID());
           // 上期末运杂费(元)
           dbZxCtSuppliesShopPaySettlement.setUpTransportAmt(zxCtSuppliesShopPaySettlement.getUpTransportAmt());
           // 上期末其他款项(元)
           dbZxCtSuppliesShopPaySettlement.setUpOtherAmt(zxCtSuppliesShopPaySettlement.getUpOtherAmt());
           // 上期末累计支付项结算金额(元)
           dbZxCtSuppliesShopPaySettlement.setUpAmt(zxCtSuppliesShopPaySettlement.getUpAmt());
           // 上期末奖罚金(元)
           dbZxCtSuppliesShopPaySettlement.setUpFineAmt(zxCtSuppliesShopPaySettlement.getUpFineAmt());
           // 上期末垫资费(元)
           dbZxCtSuppliesShopPaySettlement.setUpPadTariffAmt(zxCtSuppliesShopPaySettlement.getUpPadTariffAmt());
           // 累计支付项结算金额(元)
           dbZxCtSuppliesShopPaySettlement.setTotalAmt(zxCtSuppliesShopPaySettlement.getTotalAmt());
           // 结算期次
           dbZxCtSuppliesShopPaySettlement.setPeriod(zxCtSuppliesShopPaySettlement.getPeriod());
           // 合同ID
           dbZxCtSuppliesShopPaySettlement.setContractID(zxCtSuppliesShopPaySettlement.getContractID());
           // 本期支付项结算税额
           dbZxCtSuppliesShopPaySettlement.setThisAmtTax(zxCtSuppliesShopPaySettlement.getThisAmtTax());
           // 本期支付项结算金额(元)
           dbZxCtSuppliesShopPaySettlement.setThisAmt(zxCtSuppliesShopPaySettlement.getThisAmt());
           // 本期支付项结算不含税金额
           dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(zxCtSuppliesShopPaySettlement.getThisAmtNoTax());
           // 本期运杂费(元)
           dbZxCtSuppliesShopPaySettlement.setTransportAmt(zxCtSuppliesShopPaySettlement.getTransportAmt());
           // 本期其他款项(元)
           dbZxCtSuppliesShopPaySettlement.setOtherAmt(zxCtSuppliesShopPaySettlement.getOtherAmt());
           // 本期奖罚金(元)
           dbZxCtSuppliesShopPaySettlement.setFineAmt(zxCtSuppliesShopPaySettlement.getFineAmt());
           // 本期垫资费(元)
           dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(zxCtSuppliesShopPaySettlement.getPadTariffAmt());
           // 备注
           dbZxCtSuppliesShopPaySettlement.setRemarks(zxCtSuppliesShopPaySettlement.getRemarks());
           // 排序
           dbZxCtSuppliesShopPaySettlement.setSort(zxCtSuppliesShopPaySettlement.getSort());
           // 共通
           dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementMapper.updateByPrimaryKey(dbZxCtSuppliesShopPaySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopPaySettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlement(List<ZxCtSuppliesShopPaySettlement> zxCtSuppliesShopPaySettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesShopPaySettlementList != null && zxCtSuppliesShopPaySettlementList.size() > 0) {
           ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlement();
           zxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementMapper.batchDeleteUpdateZxCtSuppliesShopPaySettlement(zxCtSuppliesShopPaySettlementList, zxCtSuppliesShopPaySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopPaySettlementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxSaProjectSetListAllListByOrgId(ZxSaProjectSet zxSaProjectSet) {
		List<ZxSaProjectSetItem> setItemList = new ArrayList<ZxSaProjectSetItem>();
		List<ZxSaProjectSet> zxSaProjectSetList = zxSaProjectSetMapper.selectByZxSaProjectSetList(zxSaProjectSet);
		if(zxSaProjectSetList.size()>0) {
			ZxSaProjectSetItem zxSaProjectSetItem = new ZxSaProjectSetItem();
			zxSaProjectSetItem.setMainID(zxSaProjectSetList.get(0).getId());
			 setItemList =  zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(zxSaProjectSetItem);
		}

        return repEntity.okList(setItemList, setItemList.size());		
	}    
}
