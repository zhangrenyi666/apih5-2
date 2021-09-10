package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementListMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemInitialReceiptMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemReceivingMapper;
import com.apih5.mybatis.dao.ZxSkStockTransferInitialReceiptMapper;
import com.apih5.mybatis.dao.ZxSkStockTransferReceivingMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;
import com.apih5.service.ZxCtSuppliesShopResSettleItemService;
import com.apih5.service.ZxSkStockTransferReceivingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesShopResSettleItemService")
public class ZxCtSuppliesShopResSettleItemServiceImpl implements ZxCtSuppliesShopResSettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleItemMapper zxCtSuppliesShopResSettleItemMapper;

    @Autowired(required = true)
    private ZxSkStockTransferInitialReceiptMapper zxSkStockTransferInitialReceiptMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptMapper zxSkStockTransItemInitialReceiptMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListMapper zxCtSuppliesShopSettlementListMapper;
    
    @Autowired(required = true)
    private ZxSkStockTransferReceivingService zxSkStockTransferReceivingService;

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementMapper zxCtSuppliesShopPaySettlementMapper;

    @Autowired(required = true)
    private ZxSkStockTransferReceivingMapper zxSkStockTransferReceivingMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemReceivingMapper zxSkStockTransItemReceivingMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleMapper zxCtSuppliesShopResSettleMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesShopResSettleItemListByCondition(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        if (zxCtSuppliesShopResSettleItem == null) {
            zxCtSuppliesShopResSettleItem = new ZxCtSuppliesShopResSettleItem();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopResSettleItem.getPage(),zxCtSuppliesShopResSettleItem.getLimit());
//        zxCtSuppliesShopResSettleItem.setMainID(zxCtSuppliesShopResSettleItem.getZxCtSuppliesShopSettlementId);
        // 获取数据
        List<ZxCtSuppliesShopResSettleItem> zxCtSuppliesShopResSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(zxCtSuppliesShopResSettleItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopResSettleItem> p = new PageInfo<>(zxCtSuppliesShopResSettleItemList);

        return repEntity.okList(zxCtSuppliesShopResSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopResSettleItemDetail(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        if (zxCtSuppliesShopResSettleItem == null) {
            zxCtSuppliesShopResSettleItem = new ZxCtSuppliesShopResSettleItem();
        }
        // 获取数据
        ZxCtSuppliesShopResSettleItem dbZxCtSuppliesShopResSettleItem = zxCtSuppliesShopResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemId());
        // 数据存在
        if (dbZxCtSuppliesShopResSettleItem != null) {
            return repEntity.ok(dbZxCtSuppliesShopResSettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopResSettleItem(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopResSettleItem.setZxCtSuppliesShopResSettleItemId(UuidUtil.generate());
        zxCtSuppliesShopResSettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopResSettleItemMapper.insert(zxCtSuppliesShopResSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopResSettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopResSettleItem(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopResSettleItem dbZxCtSuppliesShopResSettleItem = zxCtSuppliesShopResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemId());
        if (dbZxCtSuppliesShopResSettleItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemId())) {
           // 最后编辑时间
           dbZxCtSuppliesShopResSettleItem.setEditTime(zxCtSuppliesShopResSettleItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopResSettleItem.setMainID(zxCtSuppliesShopResSettleItem.getMainID());
           // 序号
           dbZxCtSuppliesShopResSettleItem.setOrderNum(zxCtSuppliesShopResSettleItem.getOrderNum());
           // 物资名称
           dbZxCtSuppliesShopResSettleItem.setResName(zxCtSuppliesShopResSettleItem.getResName());
           // 物资编码
           dbZxCtSuppliesShopResSettleItem.setResCode(zxCtSuppliesShopResSettleItem.getResCode());
           // 物资ID
           dbZxCtSuppliesShopResSettleItem.setResID(zxCtSuppliesShopResSettleItem.getResID());
           // 所属公司排序
           dbZxCtSuppliesShopResSettleItem.setComOrders(zxCtSuppliesShopResSettleItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopResSettleItem.setComName(zxCtSuppliesShopResSettleItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopResSettleItem.setComID(zxCtSuppliesShopResSettleItem.getComID());
           // 税率
           dbZxCtSuppliesShopResSettleItem.setTaxRate(zxCtSuppliesShopResSettleItem.getTaxRate());
           // 收料退货单明细ID
           dbZxCtSuppliesShopResSettleItem.setStockBillItemID(zxCtSuppliesShopResSettleItem.getStockBillItemID());
           // 收料单(或退货单)编号
           dbZxCtSuppliesShopResSettleItem.setStockBillNo(zxCtSuppliesShopResSettleItem.getStockBillNo());
           // 收料单(或退货单)ID
           dbZxCtSuppliesShopResSettleItem.setStockBillID(zxCtSuppliesShopResSettleItem.getStockBillID());
           // 是否预收
           dbZxCtSuppliesShopResSettleItem.setPrecollecte(zxCtSuppliesShopResSettleItem.getPrecollecte());
           // 签认单明细ID
           dbZxCtSuppliesShopResSettleItem.setSignedOrderItemID(zxCtSuppliesShopResSettleItem.getSignedOrderItemID());
           // 签认单编号
           dbZxCtSuppliesShopResSettleItem.setSignedNo(zxCtSuppliesShopResSettleItem.getSignedNo());
           // 签认单ID
           dbZxCtSuppliesShopResSettleItem.setSignedOrderID(zxCtSuppliesShopResSettleItem.getSignedOrderID());
           // 结算期次
           dbZxCtSuppliesShopResSettleItem.setPeriod(zxCtSuppliesShopResSettleItem.getPeriod());
           // 合同清单ID
           dbZxCtSuppliesShopResSettleItem.setContrItemID(zxCtSuppliesShopResSettleItem.getContrItemID());
           // 合同ID
           dbZxCtSuppliesShopResSettleItem.setContractID(zxCtSuppliesShopResSettleItem.getContractID());
           // 规格型号
           dbZxCtSuppliesShopResSettleItem.setSpec(zxCtSuppliesShopResSettleItem.getSpec());
           // 单位
           dbZxCtSuppliesShopResSettleItem.setUnit(zxCtSuppliesShopResSettleItem.getUnit());
           // 本期税额(元)
           dbZxCtSuppliesShopResSettleItem.setThisAmtTax(zxCtSuppliesShopResSettleItem.getThisAmtTax());
           // 本期数量
           dbZxCtSuppliesShopResSettleItem.setThisQty(zxCtSuppliesShopResSettleItem.getThisQty());
           // 本期含税金额(元)
           dbZxCtSuppliesShopResSettleItem.setThisAmt(zxCtSuppliesShopResSettleItem.getThisAmt());
           // 本期含税单价(元)
           dbZxCtSuppliesShopResSettleItem.setThisPrice(zxCtSuppliesShopResSettleItem.getThisPrice());
           // 本期单价(元)
           dbZxCtSuppliesShopResSettleItem.setIsChoosePrice(zxCtSuppliesShopResSettleItem.getIsChoosePrice());
           // 本期不含税金额(元)
           dbZxCtSuppliesShopResSettleItem.setThisAmtNoTax(zxCtSuppliesShopResSettleItem.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesShopResSettleItem.setRemarks(zxCtSuppliesShopResSettleItem.getRemarks());
           // 排序
           dbZxCtSuppliesShopResSettleItem.setSort(zxCtSuppliesShopResSettleItem.getSort());
           // 共通
           dbZxCtSuppliesShopResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopResSettleItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopResSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopResSettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopResSettleItem(List<ZxCtSuppliesShopResSettleItem> zxCtSuppliesShopResSettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesShopResSettleItemList != null && zxCtSuppliesShopResSettleItemList.size() > 0) {
           ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem = new ZxCtSuppliesShopResSettleItem();
           zxCtSuppliesShopResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopResSettleItemMapper.batchDeleteUpdateZxCtSuppliesShopResSettleItem(zxCtSuppliesShopResSettleItemList, zxCtSuppliesShopResSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopResSettleItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxCtSuppliesShopResSettleItemListByContID(
			ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        if (zxCtSuppliesShopResSettleItem == null) {
            zxCtSuppliesShopResSettleItem = new ZxCtSuppliesShopResSettleItem();
        }
        ZxCtSuppliesShopResSettle settle = new ZxCtSuppliesShopResSettle();
        settle.setBillID(zxCtSuppliesShopResSettleItem.getMainID());
        List<ZxCtSuppliesShopResSettle> settleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(settle);
        if(settleList.size()>0) {
        	zxCtSuppliesShopResSettleItem.setMainID(settleList.get(0).getZxCtSuppliesShopResSettleId());	
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopResSettleItem.getPage(),zxCtSuppliesShopResSettleItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopResSettleItem> zxCtSuppliesShopResSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(zxCtSuppliesShopResSettleItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopResSettleItem> p = new PageInfo<>(zxCtSuppliesShopResSettleItemList);

        return repEntity.okList(zxCtSuppliesShopResSettleItemList, p.getTotal());
	}    
	
    //待修改
	@Override
	public ResponseEntity getZxCtSupReceivingAndReturnListByResID(
			ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
		String[] strArr = {""};
//		String[] strArr = null;
        // 分页查询
        if (StrUtil.isEmpty(zxSkStockTransferReceiving.getPurchaseContractID()) ||
                zxSkStockTransferReceiving.getBusDate() == null) {
            return repEntity.ok(new ArrayList<>());
        }
        //处理时间
        String format = DateUtil.format(zxSkStockTransferReceiving.getBusDate(), "yyyy-MM");
        zxSkStockTransferReceiving.setTime(format);
        // 分页查询
        PageHelper.startPage(zxSkStockTransferReceiving.getPage(),zxSkStockTransferReceiving.getLimit());
        // 获取数据
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkStockTransferReceivingService.getZxSkStockTransferReceivingByContractNoList(zxSkStockTransferReceiving);
        //查询明细
        ZxCtSuppliesShopResSettle settle = new ZxCtSuppliesShopResSettle();
        settle.setBillID(zxSkStockTransferReceiving.getMainID());
        List<ZxCtSuppliesShopResSettle> settleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(settle);
        List<ZxCtSuppliesShopResSettleItem> itemList = new ArrayList<ZxCtSuppliesShopResSettleItem>();
        ZxCtSuppliesShopResSettleItem settleItem = new ZxCtSuppliesShopResSettleItem();
        if(settleList.size()>0) {
        String signedNos = settleList.get(0).getSignedNos();
        strArr = signedNos.split("\r\n");
            settleItem.setMainID(settleList.get(0).getZxCtSuppliesShopResSettleId());
            itemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(settleItem);        	
        }
        for (ZxSkStockTransferReceiving zxSkStockTransferReceiving1 : zxSkStockTransferReceivingList) {
            ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
            zxSkStockTransItemReceiving.setBillID(zxSkStockTransferReceiving1.getId());
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = zxSkStockTransItemReceivingMapper.selectByZxSkStockTransItemReceivingList(zxSkStockTransItemReceiving);
            zxSkStockTransferReceiving1.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivings);
            zxSkStockTransferReceiving1.setIsSelect("0");
        	for(String str : strArr) {
        		if(StrUtil.equals(str, zxSkStockTransferReceiving1.getBillNo())) {
        			zxSkStockTransferReceiving1.setIsSelect("1");
        			break;
        		}
        	}      
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);

        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());	
	}

	@Override
	public ResponseEntity addZxCtSuppliesShopResSettleItemByStockId(
			ZxCtSuppliesShopResSettleItem suppliesShopResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//本期结算金额(元)
        BigDecimal thisAmtNoTax = new BigDecimal(0);//本期清单结算不含税金额
        BigDecimal thisAmtTax = new BigDecimal(0);//本期清单结算税额
        BigDecimal totalAmt = new BigDecimal(0);//累计清单结算含税金额
        
        BigDecimal resThisAmt = null;//本期结算金额(元)
        BigDecimal resThisAmtNoTax = null;//本期清单结算不含税金额
        BigDecimal resThisAmtTax = null;//本期清单结算税额
        BigDecimal resTotalAmt = null;//累计清单结算含税金额        
        String mainId = "";
        String stockBillNos = "";
        ZxCtSuppliesShopSettlementList dbSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(suppliesShopResSettleItem.getZxCtSuppliesShopSettlementId());
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = new ArrayList<ZxSkStockTransferReceiving>();
        if(suppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemList() != null && suppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemList().size()>0) {        	
        	for(ZxSkStockTransferInitialReceipt receipt : suppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemList()) {
        		ZxSkStockTransferReceiving receiving = new ZxSkStockTransferReceiving();
        		receiving.setId(receipt.getId());
        		receiving.setBizType(receipt.getBizType());
        		zxSkStockTransferReceivingList.add(receiving);
				stockBillNos = stockBillNos + receipt.getBillNo()+"\r\n";
        	}
        }else {
    		ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
    		resSettle.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
    		List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
    		if(resSettleList.size() == 0) {
    			//查询上期，添加
    			mainId = resSettle.getZxCtSuppliesShopResSettleId();
    		}else {
    			resSettle = resSettleList.get(0);
    			mainId = resSettle.getZxCtSuppliesShopResSettleId();
    			resSettle.setSignedNos("");
    			resSettle.setThisAmt(new BigDecimal(0));
    			resSettle.setThisAmtNoTax(new BigDecimal(0));
    			resSettle.setThisAmtTax(new BigDecimal(0));
    			resSettle.setTotalAmt(resSettle.getUpAmt());
    			resSettle.setModifyUserInfo(userKey, realName);
    			zxCtSuppliesShopResSettleMapper.updateByPrimaryKeySelective(resSettle);
    		}
    		ZxCtSuppliesShopResSettleItem resSettleItem = new ZxCtSuppliesShopResSettleItem();
    		resSettleItem.setMainID(mainId);
    		List<ZxCtSuppliesShopResSettleItem> resSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(resSettleItem);
    		if(resSettleItemList.size()>0) {
    			resSettleItem.setModifyUserInfo(userKey, realName);
    			zxCtSuppliesShopResSettleItemMapper.batchDeleteUpdateZxCtSuppliesShopResSettleItem(resSettleItemList, resSettleItem);
    		}
    		ZxCtSuppliesShopPaySettlement shopSettlement = new ZxCtSuppliesShopPaySettlement();
    		shopSettlement.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
    		List<ZxCtSuppliesShopPaySettlement> shopSettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(shopSettlement);
    		if(shopSettlementList.size()>0) {
    			thisAmt = CalcUtils.calcAdd(thisAmt, shopSettlementList.get(0).getThisAmt());
    			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, shopSettlementList.get(0).getThisAmtNoTax());
    			thisAmtTax = CalcUtils.calcAdd(thisAmtTax, shopSettlementList.get(0).getThisAmtTax());
    			totalAmt = CalcUtils.calcAdd(resTotalAmt, thisAmt);
    		}
    		dbSettlementList.setResThisAmt(new BigDecimal(0));
    		dbSettlementList.setThisAmt(thisAmt);
    		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
    		dbSettlementList.setThisAmtTax(thisAmtTax);
    		dbSettlementList.setTotalAmt(totalAmt);
    		dbSettlementList.setModifyUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.sava", null);
        }
        if(zxSkStockTransferReceivingList.size()>0) {
    		ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
    		resSettle.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
    		List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
    		if(resSettleList.size() == 0) {
    			//查询上期，添加
        		resSettle.setZxCtSuppliesShopResSettleId(UuidUtil.generate());
    			mainId = resSettle.getZxCtSuppliesShopResSettleId();
    		}else {
    			resSettle = resSettleList.get(0);
    			mainId = resSettle.getZxCtSuppliesShopResSettleId();
    		}
//        	List<ZxSkStockTransItemReceiving> receivingList = zxSkStockTransferReceivingService.getZxSkStockTransferReceivingByContractNoItemList(zxSkStockTransferReceivingList);       
        	List<ZxSkStockTransItemReceiving> receivingList = zxSkStockTransferReceivingService.getZxSkStockTransferReceivingByContractNoItemListNew(zxSkStockTransferReceivingList);       
        	if(receivingList != null && receivingList.size()>0) {
        		ZxCtSuppliesShopResSettleItem resSettleItem = new ZxCtSuppliesShopResSettleItem();
        		resSettleItem.setMainID(mainId);
        		List<ZxCtSuppliesShopResSettleItem> resSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(resSettleItem);
        		if(resSettleItemList.size()>0) {
        			resSettleItem.setModifyUserInfo(userKey, realName);
        			zxCtSuppliesShopResSettleItemMapper.batchDeleteUpdateZxCtSuppliesShopResSettleItem(resSettleItemList, resSettleItem);
        		}
        				for(ZxSkStockTransItemReceiving item : receivingList) {
        					ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem = new ZxCtSuppliesShopResSettleItem();
        					zxCtSuppliesShopResSettleItem.setZxCtSuppliesShopResSettleItemId(UuidUtil.generate());
        					if(StrUtil.equals(item.getBizType(), "退货单") || StrUtil.equals(item.getBizType(), "周转材退货单 ")) {        						
        						zxCtSuppliesShopResSettleItem.setThisQty(item.getInQty().negate());
        					}else {
        						zxCtSuppliesShopResSettleItem.setThisQty(item.getInQty());        						
        					}
        					zxCtSuppliesShopResSettleItem.setThisAmt(new BigDecimal(0));
        					zxCtSuppliesShopResSettleItem.setThisAmtNoTax(new BigDecimal(0));
        					zxCtSuppliesShopResSettleItem.setThisAmtTax(new BigDecimal(0));
        					zxCtSuppliesShopResSettleItem.setResName(item.getResName());
        					zxCtSuppliesShopResSettleItem.setResCode(item.getResCode());
        					zxCtSuppliesShopResSettleItem.setSpec(item.getSpec());
        					zxCtSuppliesShopResSettleItem.setBizType(item.getBizType());
//        					zxCtSuppliesShopResSettleItem.setTaxRate(item.getTaxRate());
//        					zxCtSuppliesShopResSettleItem.setThisPrice(item.getInPrice());
        					zxCtSuppliesShopResSettleItem.setUnit(item.getResUnit());
        					zxCtSuppliesShopResSettleItem.setStockBillID(item.getBillID());
        					zxCtSuppliesShopResSettleItem.setStockBillNo(item.getStockTransBillNo());
        					zxCtSuppliesShopResSettleItem.setStockBillItemID(item.getId());
        					zxCtSuppliesShopResSettleItem.setContractID(suppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemList().get(0).getContractID());
        					zxCtSuppliesShopResSettleItem.setMainID(mainId);
        					zxCtSuppliesShopResSettleItem.setCreateUserInfo(userKey, realName);
        					zxCtSuppliesShopResSettleItemMapper.insert(zxCtSuppliesShopResSettleItem);	 
        				}	        		
//        		}
	    		if(resSettleList.size() == 0) {
	    			//查询上期，添加
	                String billNo = "";
	                DecimalFormat decimalFormat = new DecimalFormat("00");	
	                int num = Integer.parseInt(dbSettlementList.getInitSerialNumber());
	                ZxCtSuppliesShopSettlementList leaseSettlement = new ZxCtSuppliesShopSettlementList();
	        			leaseSettlement.setInitSerialNumber(decimalFormat.format(num-1));
	        			List<ZxCtSuppliesShopSettlementList> leaseSettlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(leaseSettlement);
	        			if(leaseSettlementList.size()>0) {
	        				billNo = leaseSettlementList.get(0).getBillNo();
		        			ZxCtSuppliesShopResSettle resSettleBean = new ZxCtSuppliesShopResSettle();
		        			resSettleBean.setBillID(leaseSettlementList.get(0).getZxCtSuppliesShopSettlementId());
		        			resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettleBean);
		        			if(resSettleList.size()>0) {
		        				resSettle.setUpAmt(resSettleList.get(0).getTotalAmt());
		        			}      				
	        			}else {
	        				resSettle.setUpAmt(new BigDecimal(0));
	        			}

		    		resTotalAmt = CalcUtils.calcAdd(resThisAmt, resSettle.getUpAmt());
	        		resSettle.setTotalAmt(resTotalAmt);
	        		resSettle.setThisAmtNoTax(resThisAmtNoTax);
	        		resSettle.setThisAmtTax(resThisAmtTax);
	        		resSettle.setSignedNos(stockBillNos);
	        		resSettle.setThisAmt(resThisAmt);
	        		resSettle.setContractID(dbSettlementList.getContractID());
	        		resSettle.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
	        		resSettle.setCreateUserInfo(userKey, realName);
	        		zxCtSuppliesShopResSettleMapper.insert(resSettle);
	    		}else {
	    			resSettle = resSettleList.get(0);
	    			resTotalAmt = CalcUtils.calcAdd(resThisAmt, resSettle.getUpAmt());
	        		resSettle.setTotalAmt(resTotalAmt);
	        		resSettle.setThisAmtNoTax(resThisAmtNoTax);
	        		resSettle.setThisAmtTax(resThisAmtTax);
	        		resSettle.setSignedNos(stockBillNos);
	        		resSettle.setThisAmt(resThisAmt);
	        		resSettle.setContractID(dbSettlementList.getContractID());
	        		resSettle.setCreateUserInfo(userKey, realName);
	        		zxCtSuppliesShopResSettleMapper.updateByPrimaryKeySelective(resSettle);
	    		}
    			thisAmt = CalcUtils.calcAdd(thisAmt, resThisAmt);
    			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, resThisAmtNoTax);
    			thisAmtTax = CalcUtils.calcAdd(thisAmtTax, resThisAmtTax);
    			totalAmt = CalcUtils.calcAdd(totalAmt, resThisAmt);
        		ZxCtSuppliesShopPaySettlement shopSettlement = new ZxCtSuppliesShopPaySettlement();
        		shopSettlement.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
        		List<ZxCtSuppliesShopPaySettlement> shopSettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(shopSettlement);
        		if(shopSettlementList.size()>0) {
        			thisAmt = CalcUtils.calcAdd(thisAmt, shopSettlementList.get(0).getThisAmt());
        			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, shopSettlementList.get(0).getThisAmtNoTax());
        			thisAmtTax = CalcUtils.calcAdd(thisAmtTax, shopSettlementList.get(0).getThisAmtTax());
//        			totalAmt = CalcUtils.calcAdd(resTotalAmt, thisAmt);
        			totalAmt = CalcUtils.calcAdd(resTotalAmt, shopSettlementList.get(0).getTotalAmt());
        		}
        		dbSettlementList.setResThisAmt(resThisAmt);
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setTotalAmt(totalAmt);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
        	}else {
        		ZxCtSuppliesShopResSettleItem resSettleItem = new ZxCtSuppliesShopResSettleItem();
        		resSettleItem.setMainID(mainId);
        		List<ZxCtSuppliesShopResSettleItem> resSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(resSettleItem);
        		if(resSettleItemList.size()>0) {
        			resSettleItem.setModifyUserInfo(userKey, realName);
        			zxCtSuppliesShopResSettleItemMapper.batchDeleteUpdateZxCtSuppliesShopResSettleItem(resSettleItemList, resSettleItem);
        		}
        		dbSettlementList.setResThisAmt(thisAmt);
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setTotalAmt(totalAmt);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
        	}
        }
        return repEntity.ok("sys.data.sava", null);
	}

	@Override
	public ResponseEntity updateZxCtSuppliesShopResSettleItemByContID(
			ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = null;//本期结算金额(元)
        BigDecimal thisAmtNoTax = null;//本期清单结算不含税金额
        BigDecimal thisAmtTax = null;//本期清单结算税额
        BigDecimal totalAmt = null;//累计清单结算含税金额        
        int flag = 0;
        ZxCtSuppliesShopResSettleItem dbZxCtSuppliesShopResSettleItem = zxCtSuppliesShopResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemId());
        if (dbZxCtSuppliesShopResSettleItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopResSettleItem.getZxCtSuppliesShopResSettleItemId())) {
           // 最后编辑时间
           dbZxCtSuppliesShopResSettleItem.setEditTime(zxCtSuppliesShopResSettleItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopResSettleItem.setMainID(zxCtSuppliesShopResSettleItem.getMainID());
           // 序号
           dbZxCtSuppliesShopResSettleItem.setOrderNum(zxCtSuppliesShopResSettleItem.getOrderNum());
           // 物资名称
           dbZxCtSuppliesShopResSettleItem.setResName(zxCtSuppliesShopResSettleItem.getResName());
           // 物资编码
           dbZxCtSuppliesShopResSettleItem.setResCode(zxCtSuppliesShopResSettleItem.getResCode());
           // 物资ID
           dbZxCtSuppliesShopResSettleItem.setResID(zxCtSuppliesShopResSettleItem.getResID());
           // 所属公司排序
           dbZxCtSuppliesShopResSettleItem.setComOrders(zxCtSuppliesShopResSettleItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopResSettleItem.setComName(zxCtSuppliesShopResSettleItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopResSettleItem.setComID(zxCtSuppliesShopResSettleItem.getComID());
           // 税率
           dbZxCtSuppliesShopResSettleItem.setTaxRate(zxCtSuppliesShopResSettleItem.getTaxRate());
           // 收料退货单明细ID
           dbZxCtSuppliesShopResSettleItem.setStockBillItemID(zxCtSuppliesShopResSettleItem.getStockBillItemID());
           // 收料单(或退货单)编号
           dbZxCtSuppliesShopResSettleItem.setStockBillNo(zxCtSuppliesShopResSettleItem.getStockBillNo());
           // 收料单(或退货单)ID
           dbZxCtSuppliesShopResSettleItem.setStockBillID(zxCtSuppliesShopResSettleItem.getStockBillID());
           // 是否预收
           dbZxCtSuppliesShopResSettleItem.setPrecollecte(zxCtSuppliesShopResSettleItem.getPrecollecte());
           // 签认单明细ID
           dbZxCtSuppliesShopResSettleItem.setSignedOrderItemID(zxCtSuppliesShopResSettleItem.getSignedOrderItemID());
           // 签认单编号
           dbZxCtSuppliesShopResSettleItem.setSignedNo(zxCtSuppliesShopResSettleItem.getSignedNo());
           // 签认单ID
           dbZxCtSuppliesShopResSettleItem.setSignedOrderID(zxCtSuppliesShopResSettleItem.getSignedOrderID());
           // 结算期次
           dbZxCtSuppliesShopResSettleItem.setPeriod(zxCtSuppliesShopResSettleItem.getPeriod());
           // 合同清单ID
           dbZxCtSuppliesShopResSettleItem.setContrItemID(zxCtSuppliesShopResSettleItem.getContrItemID());
           // 合同ID
           dbZxCtSuppliesShopResSettleItem.setContractID(zxCtSuppliesShopResSettleItem.getContractID());
           // 规格型号
           dbZxCtSuppliesShopResSettleItem.setSpec(zxCtSuppliesShopResSettleItem.getSpec());
           // 单位
           dbZxCtSuppliesShopResSettleItem.setUnit(zxCtSuppliesShopResSettleItem.getUnit());
           // 本期税额(元)
           dbZxCtSuppliesShopResSettleItem.setThisAmtTax(zxCtSuppliesShopResSettleItem.getThisAmtTax());
           // 本期数量
           dbZxCtSuppliesShopResSettleItem.setThisQty(zxCtSuppliesShopResSettleItem.getThisQty());
           // 本期含税金额(元)
           dbZxCtSuppliesShopResSettleItem.setThisAmt(zxCtSuppliesShopResSettleItem.getThisAmt());
           // 本期含税单价(元)
           dbZxCtSuppliesShopResSettleItem.setThisPrice(zxCtSuppliesShopResSettleItem.getThisPrice());
           // 本期单价(元)
           dbZxCtSuppliesShopResSettleItem.setIsChoosePrice(zxCtSuppliesShopResSettleItem.getIsChoosePrice());
           // 本期不含税金额(元)
           dbZxCtSuppliesShopResSettleItem.setThisAmtNoTax(zxCtSuppliesShopResSettleItem.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesShopResSettleItem.setRemarks(zxCtSuppliesShopResSettleItem.getRemarks());
           // 排序
           dbZxCtSuppliesShopResSettleItem.setSort(zxCtSuppliesShopResSettleItem.getSort());
           // 共通
           dbZxCtSuppliesShopResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopResSettleItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopResSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//计算金额
        	ZxCtSuppliesShopSettlementList dbSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(dbZxCtSuppliesShopResSettleItem.getMainID());
        	if(dbSettlementList != null) {
        		ZxCtSuppliesShopPaySettlement shopSettlement = new ZxCtSuppliesShopPaySettlement();
        		shopSettlement.setBillID(dbZxCtSuppliesShopResSettleItem.getMainID());
        		List<ZxCtSuppliesShopPaySettlement> shopSettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(shopSettlement);
        		if(shopSettlementList.size()>0) {
        			thisAmt = CalcUtils.calcAdd(thisAmt, shopSettlementList.get(0).getThisAmt());
        			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, shopSettlementList.get(0).getThisAmtNoTax());
        			thisAmtTax = CalcUtils.calcAdd(thisAmtTax, shopSettlementList.get(0).getThisAmtTax());
        		}
        		ZxCtSuppliesShopResSettleItem settleItem = new ZxCtSuppliesShopResSettleItem();
        		settleItem.setMainID(dbSettlementList.getZxCtSuppliesShopSettlementId());
        		settleItem.setStockBillNo(dbZxCtSuppliesShopResSettleItem.getStockBillNo());
        		List<ZxCtSuppliesShopResSettleItem> itemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(settleItem);
        		for(ZxCtSuppliesShopResSettleItem item : itemList) {
        			thisAmt = CalcUtils.calcAdd(thisAmt, CalcUtils.calcMultiply(item.getThisQty(), item.getThisPrice()));
        			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax,CalcUtils.calcMultiply(item.getThisQty(),CalcUtils.calcAdd(taxRate,CalcUtils.calcDivide(item.getThisPrice(), new BigDecimal(100)))));
        		}
        		thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);
        		ZxCtSuppliesShopSettlementList settlement = new ZxCtSuppliesShopSettlementList();
        		settlement.setContractID(dbSettlementList.getContractID());
        		settlement.setApih5FlowStatus("2");
        		List<ZxCtSuppliesShopSettlementList> settlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(settlement);
        		if(settlementList.size()>0) {
        			totalAmt = CalcUtils.calcAdd(settlementList.get(0).getTotalAmt(), thisAmt);
        		}else {
        			totalAmt = thisAmt;
        		}
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setTotalAmt(totalAmt);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
        	}        	
            return repEntity.ok("sys.data.update",zxCtSuppliesShopResSettleItem);
        }
	}

	@Override
	public ResponseEntity addZxCtSuppliesShopResSettleItemList(
			ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//本期结算金额(元)
        BigDecimal thisAmtNoTax = new BigDecimal(0);//本期清单结算不含税金额
        BigDecimal thisAmtTax = new BigDecimal(0);//本期清单结算税额
        BigDecimal totalAmt = new BigDecimal(0);//累计清单结算含税金额
        
        BigDecimal resThisAmt = null;//本期结算金额(元)
        BigDecimal resThisAmtNoTax = null;//本期清单结算不含税金额
        BigDecimal resThisAmtTax = null;//本期清单结算税额
        BigDecimal resTotalAmt = null;//累计清单结算含税金额        
        String mainId = "";
        String stockBillNos = "";
        ZxCtSuppliesShopSettlementList dbSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementList.getZxCtSuppliesShopSettlementId());
		ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
		resSettle.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
		List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
		if(resSettleList.size() == 0) {
			//查询上期，添加
    		resSettle.setZxCtSuppliesShopResSettleId(UuidUtil.generate());
			mainId = resSettle.getZxCtSuppliesShopResSettleId();
		}else {
			resSettle = resSettleList.get(0);
			mainId = resSettle.getZxCtSuppliesShopResSettleId();
		}
        if(zxCtSuppliesShopSettlementList.getShopResSettleItemList() != null && zxCtSuppliesShopSettlementList.getShopResSettleItemList().size()>0) {
        	for(ZxCtSuppliesShopResSettleItem resSettleItem : zxCtSuppliesShopSettlementList.getShopResSettleItemList()) {
        		ZxCtSuppliesShopResSettleItem dbResSettleItem = zxCtSuppliesShopResSettleItemMapper.selectByPrimaryKey(resSettleItem.getZxCtSuppliesShopResSettleItemId());
        		dbResSettleItem.setThisAmt(resSettleItem.getThisAmt());
        		dbResSettleItem.setResID(resSettleItem.getResID());
        		dbResSettleItem.setTaxRate(resSettleItem.getTaxRate());
        		dbResSettleItem.setThisAmtNoTax(resSettleItem.getThisAmtNoTax());
        		dbResSettleItem.setThisAmtTax(resSettleItem.getThisAmtTax());
        		dbResSettleItem.setThisPrice(resSettleItem.getThisPrice());
        		dbResSettleItem.setThisQty(resSettleItem.getThisQty());
        		dbResSettleItem.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesShopResSettleItemMapper.updateByPrimaryKeySelective(dbResSettleItem);
        		resThisAmt = CalcUtils.calcAdd(resThisAmt, dbResSettleItem.getThisAmt());
				resThisAmtNoTax = CalcUtils.calcAdd(resThisAmtNoTax, dbResSettleItem.getThisAmtNoTax());
				resThisAmtTax = CalcUtils.calcAdd(resThisAmtTax, CalcUtils.calcSubtract(dbResSettleItem.getThisAmt(), dbResSettleItem.getThisAmtNoTax()));
        	}
	    		if(resSettleList.size() == 0) {
	    			//查询上期，添加
	                String billNo = "";
	                DecimalFormat decimalFormat = new DecimalFormat("00");	
	                int num = Integer.parseInt(dbSettlementList.getInitSerialNumber());
	                ZxCtSuppliesShopSettlementList leaseSettlement = new ZxCtSuppliesShopSettlementList();
	        			leaseSettlement.setInitSerialNumber(decimalFormat.format(num-1));
	        			List<ZxCtSuppliesShopSettlementList> leaseSettlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(leaseSettlement);
	        			if(leaseSettlementList.size()>0) {
	        				billNo = leaseSettlementList.get(0).getBillNo();
		        			ZxCtSuppliesShopResSettle resSettleBean = new ZxCtSuppliesShopResSettle();
		        			resSettleBean.setBillID(leaseSettlementList.get(0).getZxCtSuppliesShopSettlementId());
		        			resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettleBean);
		        			if(resSettleList.size()>0) {
		        				resSettle.setUpAmt(resSettleList.get(0).getTotalAmt());
		        			}      				
	        			}else {
	        				resSettle.setUpAmt(new BigDecimal(0));
	        			}

		    		resTotalAmt = CalcUtils.calcAdd(resThisAmt, resSettle.getUpAmt());
	        		resSettle.setTotalAmt(resTotalAmt);
	        		resSettle.setThisAmtNoTax(resThisAmtNoTax);
	        		resSettle.setThisAmtTax(resThisAmtTax);
	        		resSettle.setThisAmt(resThisAmt);
	        		resSettle.setContractID(dbSettlementList.getContractID());
	        		resSettle.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
	        		resSettle.setCreateUserInfo(userKey, realName);
	        		zxCtSuppliesShopResSettleMapper.insert(resSettle);
	    		}else {
	    			resSettle = resSettleList.get(0);
	    			resTotalAmt = CalcUtils.calcAdd(resThisAmt, resSettle.getUpAmt());
	        		resSettle.setTotalAmt(resTotalAmt);
	        		resSettle.setThisAmtNoTax(resThisAmtNoTax);
	        		resSettle.setThisAmtTax(resThisAmtTax);
	        		resSettle.setThisAmt(resThisAmt);
	        		resSettle.setContractID(dbSettlementList.getContractID());
	        		resSettle.setCreateUserInfo(userKey, realName);
	        		zxCtSuppliesShopResSettleMapper.updateByPrimaryKeySelective(resSettle);
	    		}
    			thisAmt = CalcUtils.calcAdd(thisAmt, resThisAmt);
    			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, resThisAmtNoTax);
    			thisAmtTax = CalcUtils.calcAdd(thisAmtTax, resThisAmtTax);
    			totalAmt = CalcUtils.calcAdd(totalAmt, resThisAmt);
        		ZxCtSuppliesShopPaySettlement shopSettlement = new ZxCtSuppliesShopPaySettlement();
        		shopSettlement.setBillID(dbSettlementList.getZxCtSuppliesShopSettlementId());
        		List<ZxCtSuppliesShopPaySettlement> shopSettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(shopSettlement);
        		if(shopSettlementList.size()>0) {
        			thisAmt = CalcUtils.calcAdd(thisAmt, shopSettlementList.get(0).getThisAmt());
        			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, shopSettlementList.get(0).getThisAmtNoTax());
        			thisAmtTax = CalcUtils.calcAdd(thisAmtTax, shopSettlementList.get(0).getThisAmtTax());
        			totalAmt = CalcUtils.calcAdd(resTotalAmt, shopSettlementList.get(0).getTotalAmt());
        		}
        		dbSettlementList.setResThisAmt(resThisAmt);
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setTotalAmt(totalAmt);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
        	}else {
        		ZxCtSuppliesShopResSettleItem resSettleItem = new ZxCtSuppliesShopResSettleItem();
        		resSettleItem.setMainID(mainId);
        		List<ZxCtSuppliesShopResSettleItem> resSettleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(resSettleItem);
        		if(resSettleItemList.size()>0) {
        			resSettleItem.setModifyUserInfo(userKey, realName);
        			zxCtSuppliesShopResSettleItemMapper.batchDeleteUpdateZxCtSuppliesShopResSettleItem(resSettleItemList, resSettleItem);
        		}
        		dbSettlementList.setResThisAmt(thisAmt);
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setTotalAmt(totalAmt);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
        	}
        return repEntity.ok("sys.data.sava", null);
	}
}
