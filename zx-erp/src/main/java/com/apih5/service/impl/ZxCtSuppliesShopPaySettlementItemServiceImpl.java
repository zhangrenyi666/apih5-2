package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopPaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.service.ZxCtSuppliesShopPaySettlementItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesShopPaySettlementItemService")
public class ZxCtSuppliesShopPaySettlementItemServiceImpl implements ZxCtSuppliesShopPaySettlementItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementItemMapper zxCtSuppliesShopPaySettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementMapper zxCtSuppliesShopPaySettlementMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListMapper zxCtSuppliesShopSettlementListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleItemMapper zxCtSuppliesShopResSettleItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleMapper zxCtSuppliesShopResSettleMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementMapper zxCtSuppliesLeasePaySettlementMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemListByCondition(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        if (zxCtSuppliesShopPaySettlementItem == null) {
            zxCtSuppliesShopPaySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesShopPaySettlementItem.getPage(),zxCtSuppliesShopPaySettlementItem.getLimit());
        // ????????????
        List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(zxCtSuppliesShopPaySettlementItem);
        // ??????????????????
        PageInfo<ZxCtSuppliesShopPaySettlementItem> p = new PageInfo<>(zxCtSuppliesShopPaySettlementItemList);

        return repEntity.okList(zxCtSuppliesShopPaySettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemDetail(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        if (zxCtSuppliesShopPaySettlementItem == null) {
            zxCtSuppliesShopPaySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
        }
        // ????????????
        ZxCtSuppliesShopPaySettlementItem dbZxCtSuppliesShopPaySettlementItem = zxCtSuppliesShopPaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId());
        // ????????????
        if (dbZxCtSuppliesShopPaySettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesShopPaySettlementItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopPaySettlementItem(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopPaySettlementItem.setZxCtSuppliesShopPaySettlementItemId(UuidUtil.generate());
        zxCtSuppliesShopPaySettlementItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopPaySettlementItemMapper.insert(zxCtSuppliesShopPaySettlementItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopPaySettlementItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopPaySettlementItem(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopPaySettlementItem dbZxCtSuppliesShopPaySettlementItem = zxCtSuppliesShopPaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId());
        if (dbZxCtSuppliesShopPaySettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId())) {
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setEditTime(zxCtSuppliesShopPaySettlementItem.getEditTime());
           // ??????ID
           dbZxCtSuppliesShopPaySettlementItem.setMainID(zxCtSuppliesShopPaySettlementItem.getMainID());
           // ???????????????
           dbZxCtSuppliesShopPaySettlementItem.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
           // ?????????ID
           dbZxCtSuppliesShopPaySettlementItem.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setOrderNum(zxCtSuppliesShopPaySettlementItem.getOrderNum());
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setComOrders(zxCtSuppliesShopPaySettlementItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setComName(zxCtSuppliesShopPaySettlementItem.getComName());
           // ????????????ID
           dbZxCtSuppliesShopPaySettlementItem.setComID(zxCtSuppliesShopPaySettlementItem.getComID());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setTaxRate(zxCtSuppliesShopPaySettlementItem.getTaxRate());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setQty(zxCtSuppliesShopPaySettlementItem.getQty());
           // ???????????????
           dbZxCtSuppliesShopPaySettlementItem.setIsFixed(zxCtSuppliesShopPaySettlementItem.getIsFixed());
           // ?????????????????????(???)
           dbZxCtSuppliesShopPaySettlementItem.setUpAmt(zxCtSuppliesShopPaySettlementItem.getUpAmt());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setPayName(zxCtSuppliesShopPaySettlementItem.getPayName());
           // ????????????
           dbZxCtSuppliesShopPaySettlementItem.setPeriod(zxCtSuppliesShopPaySettlementItem.getPeriod());
           // ??????ID
           dbZxCtSuppliesShopPaySettlementItem.setContractID(zxCtSuppliesShopPaySettlementItem.getContractID());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setUnit(zxCtSuppliesShopPaySettlementItem.getUnit());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setPrice(zxCtSuppliesShopPaySettlementItem.getPrice());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setPayNo(zxCtSuppliesShopPaySettlementItem.getPayNo());
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtTax(zxCtSuppliesShopPaySettlementItem.getThisAmtTax());
           // ??????????????????(???)
           dbZxCtSuppliesShopPaySettlementItem.setThisAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
           // ???????????????????????????
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtNoTax(zxCtSuppliesShopPaySettlementItem.getThisAmtNoTax());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setRemarks(zxCtSuppliesShopPaySettlementItem.getRemarks());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setSort(zxCtSuppliesShopPaySettlementItem.getSort());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopPaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopPaySettlementItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesShopPaySettlementItemList != null && zxCtSuppliesShopPaySettlementItemList.size() > 0) {
           ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
           zxCtSuppliesShopPaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementItemMapper.batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(zxCtSuppliesShopPaySettlementItemList, zxCtSuppliesShopPaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopPaySettlementItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity saveZxCtSuppliesShopPaySettlementItemByPayId(
    		ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal padTariffAmt = null;//???????????????(???)
        BigDecimal fineAmt = null;//???????????????(???)
        BigDecimal otherAmt = null;//??????????????????(???)
        BigDecimal transportAmt = null;//???????????????(???)
        BigDecimal totalAmt = new BigDecimal(0);//??????????????????
        BigDecimal payThisAmt = new BigDecimal(0);//??????????????????
        BigDecimal payTotalAmt = new BigDecimal(0);//??????????????????
        BigDecimal taxRate = new BigDecimal(1);//????????????
        BigDecimal thisAmt = new BigDecimal(0);//??????????????????
        BigDecimal thisAmtNoTax = new BigDecimal(0);//?????????????????????
        BigDecimal thisAmtTax = new BigDecimal(0);//????????????
        ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
        resSettle.setBillID(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopSettlementId());
        List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
        if(resSettleList.size()>0) {
			thisAmt = CalcUtils.calcAdd(thisAmt, resSettleList.get(0).getThisAmt());
			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, resSettleList.get(0).getThisAmtNoTax());
		    thisAmtTax = CalcUtils.calcAdd(thisAmtTax, resSettleList.get(0).getThisAmtTax());
        }
        int flag = 0;
        ZxCtSuppliesShopSettlementList dbSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopSettlementId());
//        ZxCtSuppliesShopResSettleItem settleItem = new ZxCtSuppliesShopResSettleItem();
//        settleItem.setMainID(resSettleList.get(0).getZxCtSuppliesShopResSettleId());
//        List<ZxCtSuppliesShopResSettleItem> settleItemList = zxCtSuppliesShopResSettleItemMapper.selectByZxCtSuppliesShopResSettleItemList(settleItem);
//        if(settleItemList.size()>0) {
//    		for(ZxCtSuppliesShopResSettleItem item : settleItemList) {
//    			thisAmt = CalcUtils.calcAdd(thisAmt, CalcUtils.calcMultiply(item.getThisQty(), item.getThisPrice()));
//    			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax,CalcUtils.calcMultiply(item.getThisQty(),CalcUtils.calcAdd(taxRate,CalcUtils.calcDivide(item.getThisPrice(), new BigDecimal(100)))));
//    		}
//    		thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);        	
//        }
        ZxCtSuppliesShopPaySettlement paySettlement = new ZxCtSuppliesShopPaySettlement();
        paySettlement.setBillID(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopSettlementId());
        List<ZxCtSuppliesShopPaySettlement> paySettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(paySettlement);
        //???????????????????????????????????????????????????????????????????????????
        if(paySettlementList.size()>0) {
        	ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = paySettlementList.get(0);
            //????????????????????????
        	ZxCtSuppliesShopPaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlementItem();
            suppliesShopPaySettlement.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            suppliesShopPaySettlement.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
            if(zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(suppliesShopPaySettlement).size()>0) {
            	return repEntity.layerMessage("NO", "????????????????????????????????????!");
            }
            zxCtSuppliesShopPaySettlementItem.setZxCtSuppliesShopPaySettlementItemId(UuidUtil.generate());
            zxCtSuppliesShopPaySettlementItem.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            zxCtSuppliesShopPaySettlementItem.setCreateUserInfo(userKey, realName);
            flag = zxCtSuppliesShopPaySettlementItemMapper.insert(zxCtSuppliesShopPaySettlementItem);
            dbZxCtSuppliesShopPaySettlement.setThisAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getThisAmt(), zxCtSuppliesShopPaySettlementItem.getThisAmt()));
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax(), zxCtSuppliesShopPaySettlementItem.getThisAmtNoTax()));
//            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getThisAmtTax(), zxCtSuppliesShopPaySettlementItem.getThisAmtTax()));
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(CalcUtils.calcSubtract(dbZxCtSuppliesShopPaySettlement.getThisAmt(), dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax()));
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), dbZxCtSuppliesShopPaySettlement.getThisAmt()));
            thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
            thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
            thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
            if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")) {
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	fineAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")){
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	padTariffAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(padTariffAmt);            	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "????????????")){
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	otherAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")){
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	transportAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setTransportAmt(transportAmt);                      	
        }
            payThisAmt = dbZxCtSuppliesShopPaySettlement.getThisAmt();
            payTotalAmt = CalcUtils.calcAdd(payThisAmt, dbSettlementList.getTotalPayAmt());
//            payTotalAmt = dbZxCtSuppliesShopPaySettlement.getTotalAmt();
            totalAmt = CalcUtils.calcAdd(dbSettlementList.getTotalAmt(), dbZxCtSuppliesShopPaySettlement.getTotalAmt());            
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopPaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
        }else {
        	ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlement();
        	dbZxCtSuppliesShopPaySettlement.setZxCtSuppliesShopPaySettlementId(UuidUtil.generate());
        	dbZxCtSuppliesShopPaySettlement.setBillID(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopSettlementId());
            dbZxCtSuppliesShopPaySettlement.setCreateUserInfo(userKey, realName);
            
            zxCtSuppliesShopPaySettlementItem.setZxCtSuppliesShopPaySettlementItemId(UuidUtil.generate());
            zxCtSuppliesShopPaySettlementItem.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            zxCtSuppliesShopPaySettlementItem.setCreateUserInfo(userKey, realName);
            flag = zxCtSuppliesShopPaySettlementItemMapper.insert(zxCtSuppliesShopPaySettlementItem);
            if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")) {
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")){
            	dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());            	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "????????????")){
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());                	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")){
            	dbZxCtSuppliesShopPaySettlement.setTransportAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());                      	
        }
            dbZxCtSuppliesShopPaySettlement.setThisAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(zxCtSuppliesShopPaySettlementItem.getThisAmtNoTax());
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(zxCtSuppliesShopPaySettlementItem.getThisAmtTax());
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(zxCtSuppliesShopPaySettlementItem.getUpAmtByPay(), zxCtSuppliesShopPaySettlementItem.getThisAmt()));
            payThisAmt = dbZxCtSuppliesShopPaySettlement.getThisAmt();
            payTotalAmt = CalcUtils.calcAdd(payThisAmt, dbSettlementList.getTotalPayAmt());
//            payTotalAmt = dbZxCtSuppliesShopPaySettlement.getTotalAmt();
            totalAmt = CalcUtils.calcAdd(dbSettlementList.getTotalAmt(), dbZxCtSuppliesShopPaySettlement.getTotalAmt());
            thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
            thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
            thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
            zxCtSuppliesShopPaySettlementMapper.insert(dbZxCtSuppliesShopPaySettlement);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//?????????,????????????
        	dbSettlementList.setThisPayAmt(payThisAmt);
        	dbSettlementList.setTotalPayAmt(payTotalAmt);
    		dbSettlementList.setTotalAmt(totalAmt);
    		
    		dbSettlementList.setThisAmt(thisAmt);
    		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
    		dbSettlementList.setThisAmtTax(thisAmtTax);
    		dbSettlementList.setModifyUserInfo(userKey, realName);
        	zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopPaySettlementItem);
        }
    }

	@Override
	public ResponseEntity updateZxCtSuppliesShopPaySettlementItemByPayId(
			ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal fineAmt = new BigDecimal(0);
        BigDecimal padTariffAmt = new BigDecimal(0);
        BigDecimal otherAmt = new BigDecimal(0);
        BigDecimal transportAmt = new BigDecimal(0);
        BigDecimal payThisAmt = new BigDecimal(0);//??????????????????
        BigDecimal payTotalAmt = new BigDecimal(0);//??????????????????
        BigDecimal taxRate = new BigDecimal(1);//????????????
        BigDecimal thisAmt = new BigDecimal(0);//??????????????????
        BigDecimal thisAmtNoTax = new BigDecimal(0);//?????????????????????
        BigDecimal thisAmtTax = new BigDecimal(0);//????????????
        int flag = 0;
        ZxCtSuppliesShopSettlementList dbSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopSettlementId());
        ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
        resSettle.setBillID(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopSettlementId());
        List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
        if(resSettleList.size()>0) {
			thisAmt = CalcUtils.calcAdd(thisAmt, resSettleList.get(0).getThisAmt());
			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, resSettleList.get(0).getThisAmtNoTax());
		    thisAmtTax = CalcUtils.calcAdd(thisAmtTax, resSettleList.get(0).getThisAmtTax());      
        }
        ZxCtSuppliesShopPaySettlementItem dbZxCtSuppliesShopPaySettlementItem = zxCtSuppliesShopPaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId());
        if (dbZxCtSuppliesShopPaySettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId())) {
            //????????????????????????
        	ZxCtSuppliesShopPaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlementItem();
            suppliesShopPaySettlement.setMainID(dbZxCtSuppliesShopPaySettlementItem.getMainID());
            suppliesShopPaySettlement.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
            List<ZxCtSuppliesShopPaySettlementItem> suppliesShopPaySettlementList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(suppliesShopPaySettlement);
            if(suppliesShopPaySettlementList.size()>0 && !StrUtil.equals(suppliesShopPaySettlementList.get(0).getZxCtSuppliesShopPaySettlementItemId(), zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId()) ) {
            	return repEntity.layerMessage("NO", "????????????????????????????????????!");
            }
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setEditTime(zxCtSuppliesShopPaySettlementItem.getEditTime());
           // ??????ID
           dbZxCtSuppliesShopPaySettlementItem.setMainID(zxCtSuppliesShopPaySettlementItem.getMainID());
           // ???????????????
           dbZxCtSuppliesShopPaySettlementItem.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
           // ?????????ID
           dbZxCtSuppliesShopPaySettlementItem.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setOrderNum(zxCtSuppliesShopPaySettlementItem.getOrderNum());
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setComOrders(zxCtSuppliesShopPaySettlementItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setComName(zxCtSuppliesShopPaySettlementItem.getComName());
           // ????????????ID
           dbZxCtSuppliesShopPaySettlementItem.setComID(zxCtSuppliesShopPaySettlementItem.getComID());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setTaxRate(zxCtSuppliesShopPaySettlementItem.getTaxRate());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setQty(zxCtSuppliesShopPaySettlementItem.getQty());
           // ???????????????
           dbZxCtSuppliesShopPaySettlementItem.setIsFixed(zxCtSuppliesShopPaySettlementItem.getIsFixed());
           // ?????????????????????(???)
           dbZxCtSuppliesShopPaySettlementItem.setUpAmt(zxCtSuppliesShopPaySettlementItem.getUpAmt());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setPayName(zxCtSuppliesShopPaySettlementItem.getPayName());
           // ????????????
           dbZxCtSuppliesShopPaySettlementItem.setPeriod(zxCtSuppliesShopPaySettlementItem.getPeriod());
           // ??????ID
           dbZxCtSuppliesShopPaySettlementItem.setContractID(zxCtSuppliesShopPaySettlementItem.getContractID());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setUnit(zxCtSuppliesShopPaySettlementItem.getUnit());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setPrice(zxCtSuppliesShopPaySettlementItem.getPrice());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setPayNo(zxCtSuppliesShopPaySettlementItem.getPayNo());
           // ??????????????????
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtTax(zxCtSuppliesShopPaySettlementItem.getThisAmtTax());
           // ??????????????????(???)
           dbZxCtSuppliesShopPaySettlementItem.setThisAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
           // ???????????????????????????
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtNoTax(zxCtSuppliesShopPaySettlementItem.getThisAmtNoTax());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setRemarks(zxCtSuppliesShopPaySettlementItem.getRemarks());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setSort(zxCtSuppliesShopPaySettlementItem.getSort());
           // ??????
           dbZxCtSuppliesShopPaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopPaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = zxCtSuppliesShopPaySettlementMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getMainID());
        	ZxCtSuppliesShopPaySettlementItem settlementItem = new ZxCtSuppliesShopPaySettlementItem();
        	settlementItem.setMainID(zxCtSuppliesShopPaySettlementItem.getMainID());
        	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(settlementItem);
        	dbZxCtSuppliesShopPaySettlement.setThisAmt(itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmtNoTax).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmtTax).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add)));
            thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
            thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
            thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
            payThisAmt = dbZxCtSuppliesShopPaySettlement.getThisAmt();
            payTotalAmt = CalcUtils.calcAdd(payThisAmt, dbSettlementList.getTotalPayAmt());
            if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")) {
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "?????????")) {
            			fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")){
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "?????????")) {
            			padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(padTariffAmt);            	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "????????????")){
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "????????????")) {
            			otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "?????????")){
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "?????????")) {
            			transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setTransportAmt(transportAmt);                      	
        }
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopPaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
        	//?????????,????????????
        	dbSettlementList.setThisPayAmt(payThisAmt);
        	dbSettlementList.setTotalPayAmt(payTotalAmt);
    		dbSettlementList.setThisAmt(thisAmt);
    		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
    		dbSettlementList.setThisAmtTax(thisAmtTax);
    		dbSettlementList.setModifyUserInfo(userKey, realName);
        	zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.update",zxCtSuppliesShopPaySettlementItem);
        }
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId(
			List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal fineAmt = new BigDecimal(0);
        BigDecimal padTariffAmt = new BigDecimal(0);
        BigDecimal otherAmt = new BigDecimal(0);
        BigDecimal transportAmt = new BigDecimal(0);
        
        BigDecimal taxRate = new BigDecimal(1);//????????????
        BigDecimal thisAmt = new BigDecimal(0);//??????????????????
        BigDecimal thisAmtNoTax = new BigDecimal(0);//?????????????????????
        BigDecimal thisAmtTax = new BigDecimal(0);//????????????
        int flag = 0;
        ZxCtSuppliesShopSettlementList dbSettlementList = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItemList.get(0).getZxCtSuppliesShopSettlementId());
        ZxCtSuppliesShopResSettle resSettle = new ZxCtSuppliesShopResSettle();
        resSettle.setBillID(zxCtSuppliesShopPaySettlementItemList.get(0).getZxCtSuppliesShopSettlementId());
        List<ZxCtSuppliesShopResSettle> resSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(resSettle);
        if(resSettleList.size()>0) {
			thisAmt = CalcUtils.calcAdd(thisAmt, resSettleList.get(0).getThisAmt());
			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, resSettleList.get(0).getThisAmtNoTax());
		    thisAmtTax = CalcUtils.calcSubtract(thisAmt, resSettleList.get(0).getThisAmtTax());      
        }
        if (zxCtSuppliesShopPaySettlementItemList != null && zxCtSuppliesShopPaySettlementItemList.size() > 0) {
           ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
           zxCtSuppliesShopPaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementItemMapper.batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(zxCtSuppliesShopPaySettlementItemList, zxCtSuppliesShopPaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//??????????????????
        	ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = zxCtSuppliesShopPaySettlementMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItemList.get(0).getMainID());
        	ZxCtSuppliesShopPaySettlementItem settlementItem = new ZxCtSuppliesShopPaySettlementItem();
        	settlementItem.setMainID(zxCtSuppliesShopPaySettlementItemList.get(0).getMainID());
        	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(settlementItem);
        	dbZxCtSuppliesShopPaySettlement.setThisAmt(itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmtNoTax).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmtTax).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add)));
    		thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
    		thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
    		thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
            for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            	if(StrUtil.equals(item.getPayType(), "?????????")) {
        			fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());	
            	}else if(StrUtil.equals(item.getPayType(), "?????????")) {
            		padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
            	}else if(StrUtil.equals(item.getPayType(), "????????????")) {
            		otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
            	}else if(StrUtil.equals(item.getPayType(), "?????????")) {
            		transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
            	}
            }
            dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(padTariffAmt);
            dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);
            dbZxCtSuppliesShopPaySettlement.setTransportAmt(transportAmt);
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopPaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
        	//?????????,????????????
    		dbSettlementList.setThisAmt(thisAmt);
    		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
    		dbSettlementList.setThisAmtTax(thisAmtTax);
    		dbSettlementList.setModifyUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopPaySettlementItemList);
        }
	}

	@Override
	public ResponseEntity getZxCtSuppliesShopPaySettlementItemListByContID(
			ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        if (zxCtSuppliesShopPaySettlementItem == null) {
            zxCtSuppliesShopPaySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
        }
        ZxCtSuppliesShopPaySettlement paySettlement = new ZxCtSuppliesShopPaySettlement();
        paySettlement.setBillID(zxCtSuppliesShopPaySettlementItem.getMainID());
        List<ZxCtSuppliesShopPaySettlement> paySettlementList = zxCtSuppliesShopPaySettlementMapper.selectByZxCtSuppliesShopPaySettlementList(paySettlement);
        if(paySettlementList.size()>0) {
        	zxCtSuppliesShopPaySettlementItem.setMainID(paySettlementList.get(0).getZxCtSuppliesShopPaySettlementId());
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesShopPaySettlementItem.getPage(),zxCtSuppliesShopPaySettlementItem.getLimit());
        // ????????????
        List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(zxCtSuppliesShopPaySettlementItem);
        // ??????????????????
        PageInfo<ZxCtSuppliesShopPaySettlementItem> p = new PageInfo<>(zxCtSuppliesShopPaySettlementItemList);

        return repEntity.okList(zxCtSuppliesShopPaySettlementItemList, p.getTotal());
	}
}
