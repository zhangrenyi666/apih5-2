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
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettleItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.service.ZxCtSuppliesLeasePaySettlementItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesLeasePaySettlementItemService")
public class ZxCtSuppliesLeasePaySettlementItemServiceImpl implements ZxCtSuppliesLeasePaySettlementItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementItemMapper zxCtSuppliesLeasePaySettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementMapper zxCtSuppliesLeasePaySettlementMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListMapper zxCtSuppliesLeaseSettlementListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettleItemMapper zxCtSuppliesLeaseResSettleItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettlementMapper zxCtSuppliesLeaseResSettlementMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemListByCondition(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        if (zxCtSuppliesLeasePaySettlementItem == null) {
            zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesLeasePaySettlementItem.getPage(),zxCtSuppliesLeasePaySettlementItem.getLimit());
        // ????????????
        List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(zxCtSuppliesLeasePaySettlementItem);
        // ??????????????????
        PageInfo<ZxCtSuppliesLeasePaySettlementItem> p = new PageInfo<>(zxCtSuppliesLeasePaySettlementItemList);

        return repEntity.okList(zxCtSuppliesLeasePaySettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemDetail(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        if (zxCtSuppliesLeasePaySettlementItem == null) {
            zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
        }
        // ????????????
        ZxCtSuppliesLeasePaySettlementItem dbZxCtSuppliesLeasePaySettlementItem = zxCtSuppliesLeasePaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId());
        // ????????????
        if (dbZxCtSuppliesLeasePaySettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesLeasePaySettlementItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesLeasePaySettlementItem(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesLeasePaySettlementItem.setZxCtSuppliesLeasePaySettlementItemId(UuidUtil.generate());
        zxCtSuppliesLeasePaySettlementItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeasePaySettlementItemMapper.insert(zxCtSuppliesLeasePaySettlementItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeasePaySettlementItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesLeasePaySettlementItem(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeasePaySettlementItem dbZxCtSuppliesLeasePaySettlementItem = zxCtSuppliesLeasePaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId());
        if (dbZxCtSuppliesLeasePaySettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId())) {
           // ??????????????????
           dbZxCtSuppliesLeasePaySettlementItem.setEditTime(zxCtSuppliesLeasePaySettlementItem.getEditTime());
           // ??????ID
           dbZxCtSuppliesLeasePaySettlementItem.setMainID(zxCtSuppliesLeasePaySettlementItem.getMainID());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
           // ?????????ID
           dbZxCtSuppliesLeasePaySettlementItem.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setOrderNum(zxCtSuppliesLeasePaySettlementItem.getOrderNum());
           // ??????????????????
           dbZxCtSuppliesLeasePaySettlementItem.setComOrders(zxCtSuppliesLeasePaySettlementItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesLeasePaySettlementItem.setComName(zxCtSuppliesLeasePaySettlementItem.getComName());
           // ????????????ID
           dbZxCtSuppliesLeasePaySettlementItem.setComID(zxCtSuppliesLeasePaySettlementItem.getComID());
           // ??????(%)
           dbZxCtSuppliesLeasePaySettlementItem.setTaxRate(zxCtSuppliesLeasePaySettlementItem.getTaxRate());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setQty(zxCtSuppliesLeasePaySettlementItem.getQty());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setIsFixed(zxCtSuppliesLeasePaySettlementItem.getIsFixed());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setUpQty(zxCtSuppliesLeasePaySettlementItem.getUpQty());
           // ?????????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setUpAmt(zxCtSuppliesLeasePaySettlementItem.getUpAmt());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setPayName(zxCtSuppliesLeasePaySettlementItem.getPayName());
           // ????????????
           dbZxCtSuppliesLeasePaySettlementItem.setPeriod(zxCtSuppliesLeasePaySettlementItem.getPeriod());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setBillNo(zxCtSuppliesLeasePaySettlementItem.getBillNo());
           // ????????????
           dbZxCtSuppliesLeasePaySettlementItem.setContrType(zxCtSuppliesLeasePaySettlementItem.getContrType());
           // ??????ID
           dbZxCtSuppliesLeasePaySettlementItem.setContractID(zxCtSuppliesLeasePaySettlementItem.getContractID());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setUnit(zxCtSuppliesLeasePaySettlementItem.getUnit());
           // ??????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setPrice(zxCtSuppliesLeasePaySettlementItem.getPrice());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setPayNo(zxCtSuppliesLeasePaySettlementItem.getPayNo());
           // ??????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtTax());
           // ??????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
           // ???????????????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtNoTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtNoTax());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setRemarks(zxCtSuppliesLeasePaySettlementItem.getRemarks());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setSort(zxCtSuppliesLeasePaySettlementItem.getSort());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeasePaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeasePaySettlementItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesLeasePaySettlementItemList != null && zxCtSuppliesLeasePaySettlementItemList.size() > 0) {
           ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
           zxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(zxCtSuppliesLeasePaySettlementItemList, zxCtSuppliesLeasePaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeasePaySettlementItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity saveZxCtSuppliesLeasePaySettlementItemByPayId(
    		ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
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
        BigDecimal thisAmt = new BigDecimal(0);//??????????????????
        BigDecimal thisAmtNoTax = new BigDecimal(0);//?????????????????????
        BigDecimal thisAmtTax = new BigDecimal(0);//????????????
        System.out.println("1");
        int flag = 0;
        
        ZxCtSuppliesLeaseSettlementList dbSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
        ZxCtSuppliesLeaseResSettlement resSettlement = new ZxCtSuppliesLeaseResSettlement();
        resSettlement.setBillID(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
        List<ZxCtSuppliesLeaseResSettlement> resSettlementList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(resSettlement);
        if(resSettlementList.size()>0) {
        	thisAmt = resSettlementList.get(0).getThisAmt();
            thisAmtNoTax = resSettlementList.get(0).getThisAmtNoTax();
            thisAmtTax = resSettlementList.get(0).getThisAmtTax();
        }
        ZxCtSuppliesLeasePaySettlement paySettlement = new ZxCtSuppliesLeasePaySettlement();
        paySettlement.setBillID(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
        List<ZxCtSuppliesLeasePaySettlement> paySettlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(paySettlement);
        //???????????????????????????????????????????????????????????????????????????
        if(paySettlementList.size()>0) {
        	ZxCtSuppliesLeasePaySettlement dbPaySettlement = paySettlementList.get(0);
            //????????????????????????
        	ZxCtSuppliesLeasePaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlementItem();
            suppliesShopPaySettlement.setMainID(dbPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            suppliesShopPaySettlement.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
            if(zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(suppliesShopPaySettlement).size()>0) {
            	return repEntity.layerMessage("NO", "????????????????????????????????????!");
            }        	
        	ZxCtSuppliesLeasePaySettlement dbZxCtSuppliesShopPaySettlement = paySettlementList.get(0);
        	zxCtSuppliesLeasePaySettlementItem.setZxCtSuppliesLeasePaySettlementItemId(UuidUtil.generate());
        	zxCtSuppliesLeasePaySettlementItem.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
        	zxCtSuppliesLeasePaySettlementItem.setZxCtSuppliesLeaseSettlementListId(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
        	zxCtSuppliesLeasePaySettlementItem.setCreateUserInfo(userKey, realName);
            flag = zxCtSuppliesLeasePaySettlementItemMapper.insert(zxCtSuppliesLeasePaySettlementItem);
            dbZxCtSuppliesShopPaySettlement.setThisAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getThisAmt(), zxCtSuppliesLeasePaySettlementItem.getThisAmt()));
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax(), zxCtSuppliesLeasePaySettlementItem.getThisAmtNoTax()));
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getThisAmtTax(), zxCtSuppliesLeasePaySettlementItem.getThisAmtTax()));
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), dbZxCtSuppliesShopPaySettlement.getThisAmt()));
            thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
            thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
            thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
            if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "??????")) {
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	fineAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "??????")){
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	padTariffAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setFoodAmt(padTariffAmt);            	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "????????????")){
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	otherAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "?????????")){
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	transportAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setInOutAmt(transportAmt);                      	
        }
            totalAmt = CalcUtils.calcAdd(dbSettlementList.getTotalAmt(), dbZxCtSuppliesShopPaySettlement.getTotalAmt());
            payThisAmt = dbZxCtSuppliesShopPaySettlement.getThisAmt();
            payTotalAmt = dbZxCtSuppliesShopPaySettlement.getTotalAmt();
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesLeasePaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
        }else {
            String billNo = "";
            int num = Integer.parseInt(dbSettlementList.getInitSerialNumber());
    			ZxCtSuppliesLeaseSettlementList leaseSettlement = new ZxCtSuppliesLeaseSettlementList();
    			leaseSettlement.setInitSerialNumber(num-1+"");
    			leaseSettlement.setApih5FlowStatus("0");
    			List<ZxCtSuppliesLeaseSettlementList> leaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(leaseSettlement);
    			if(leaseSettlementList.size()>0) {
    				billNo = leaseSettlementList.get(0).getBillNo();
    			}else {
    				billNo = "1111";
    			}
        	//???????????????
        	ZxCtSuppliesLeasePaySettlement dbZxCtSuppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlement();
        	dbZxCtSuppliesShopPaySettlement.setBillNo(billNo);
        	List<ZxCtSuppliesLeasePaySettlement> settlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(dbZxCtSuppliesShopPaySettlement);
        	dbZxCtSuppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlement();
        	if(settlementList.size()>0) {
        		dbZxCtSuppliesShopPaySettlement.setUpInOutAmt(settlementList.get(0).getInOutAmt());
        		dbZxCtSuppliesShopPaySettlement.setUpFoodAmt(settlementList.get(0).getFoodAmt());
        		dbZxCtSuppliesShopPaySettlement.setUpOtherAmt(settlementList.get(0).getOtherAmt());
        		dbZxCtSuppliesShopPaySettlement.setUpAmt(settlementList.get(0).getThisAmt());
        		dbZxCtSuppliesShopPaySettlement.setUpFineAmt(settlementList.get(0).getFineAmt());
        	}
        	dbZxCtSuppliesShopPaySettlement.setZxCtSuppliesLeasePaySettlementId(UuidUtil.generate());
        	dbZxCtSuppliesShopPaySettlement.setBillID(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
        	dbZxCtSuppliesShopPaySettlement.setBillNo(dbSettlementList.getBillNo());
//            zxCtSuppliesShopPaySettlementId = dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId();
            dbZxCtSuppliesShopPaySettlement.setCreateUserInfo(userKey, realName);
            
            zxCtSuppliesLeasePaySettlementItem.setZxCtSuppliesLeasePaySettlementItemId(UuidUtil.generate());
            zxCtSuppliesLeasePaySettlementItem.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            zxCtSuppliesLeasePaySettlementItem.setCreateUserInfo(userKey, realName);
            flag = zxCtSuppliesLeasePaySettlementItemMapper.insert(zxCtSuppliesLeasePaySettlementItem);
            if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "??????")) {
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "??????")){
            	dbZxCtSuppliesShopPaySettlement.setFoodAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());            	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "????????????")){
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());                	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "?????????")){
            	dbZxCtSuppliesShopPaySettlement.setInOutAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());                      	
        }
            dbZxCtSuppliesShopPaySettlement.setThisAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtNoTax());
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtTax());
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), zxCtSuppliesLeasePaySettlementItem.getThisAmt()));
            payThisAmt = dbZxCtSuppliesShopPaySettlement.getThisAmt();
            payTotalAmt = dbZxCtSuppliesShopPaySettlement.getTotalAmt();
            totalAmt = CalcUtils.calcAdd(dbSettlementList.getTotalAmt(), dbZxCtSuppliesShopPaySettlement.getTotalAmt());
            thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
            thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
            thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
            zxCtSuppliesLeasePaySettlementMapper.insert(dbZxCtSuppliesShopPaySettlement);
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
    		zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeasePaySettlementItem);
        }
    }
    
    @Override
    public ResponseEntity updateZxCtSuppliesLeasePaySettlementItemByPayId(
    		ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
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
        ZxCtSuppliesLeaseSettlementList dbSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
        ZxCtSuppliesLeaseResSettlement resSettlement = new ZxCtSuppliesLeaseResSettlement();
        resSettlement.setBillID(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
        List<ZxCtSuppliesLeaseResSettlement> resSettlementList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(resSettlement);
        if(resSettlementList.size()>0) {
        	thisAmt = resSettlementList.get(0).getThisAmt();
            thisAmtNoTax = resSettlementList.get(0).getThisAmtNoTax();
            thisAmtTax = resSettlementList.get(0).getThisAmtTax();
        }
//        ZxCtSuppliesLeaseResSettleItem settleItem = new ZxCtSuppliesLeaseResSettleItem();
//        settleItem.setMainID(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeaseSettlementListId());
//        List<ZxCtSuppliesLeaseResSettleItem> settleItemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(settleItem);
//        if(settleItemList.size()>0) {
//    		for(ZxCtSuppliesLeaseResSettleItem item : settleItemList) {
//    			thisAmt = CalcUtils.calcAdd(thisAmt, CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()));
//    			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, CalcUtils.calcDivide(CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()), CalcUtils.calcAdd(taxRate,CalcUtils.calcDivide(new BigDecimal(item.getTaxRate()), new BigDecimal(100),6))));
//    		}
//    		thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);        	
//        }
        ZxCtSuppliesLeasePaySettlementItem dbZxCtSuppliesLeasePaySettlementItem = zxCtSuppliesLeasePaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId());
        //????????????????????????
    	ZxCtSuppliesLeasePaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlementItem();
        suppliesShopPaySettlement.setMainID(dbZxCtSuppliesLeasePaySettlementItem.getMainID());
        suppliesShopPaySettlement.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
        List<ZxCtSuppliesLeasePaySettlementItem> suppliesShopPaySettlementList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(suppliesShopPaySettlement);
        if(suppliesShopPaySettlementList.size()>0 && !StrUtil.equals(suppliesShopPaySettlementList.get(0).getZxCtSuppliesLeasePaySettlementItemId(), zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId())) {
        	return repEntity.layerMessage("NO", "????????????????????????????????????!");
        }       
        if (dbZxCtSuppliesLeasePaySettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId())) {
           // ??????????????????
           dbZxCtSuppliesLeasePaySettlementItem.setEditTime(zxCtSuppliesLeasePaySettlementItem.getEditTime());
           // ??????ID
           dbZxCtSuppliesLeasePaySettlementItem.setMainID(zxCtSuppliesLeasePaySettlementItem.getMainID());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
           // ?????????ID
           dbZxCtSuppliesLeasePaySettlementItem.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setOrderNum(zxCtSuppliesLeasePaySettlementItem.getOrderNum());
           // ??????????????????
           dbZxCtSuppliesLeasePaySettlementItem.setComOrders(zxCtSuppliesLeasePaySettlementItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesLeasePaySettlementItem.setComName(zxCtSuppliesLeasePaySettlementItem.getComName());
           // ????????????ID
           dbZxCtSuppliesLeasePaySettlementItem.setComID(zxCtSuppliesLeasePaySettlementItem.getComID());
           // ??????(%)
           dbZxCtSuppliesLeasePaySettlementItem.setTaxRate(zxCtSuppliesLeasePaySettlementItem.getTaxRate());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setQty(zxCtSuppliesLeasePaySettlementItem.getQty());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setIsFixed(zxCtSuppliesLeasePaySettlementItem.getIsFixed());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setUpQty(zxCtSuppliesLeasePaySettlementItem.getUpQty());
           // ?????????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setUpAmt(zxCtSuppliesLeasePaySettlementItem.getUpAmt());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setPayName(zxCtSuppliesLeasePaySettlementItem.getPayName());
           // ????????????
           dbZxCtSuppliesLeasePaySettlementItem.setPeriod(zxCtSuppliesLeasePaySettlementItem.getPeriod());
           // ???????????????
           dbZxCtSuppliesLeasePaySettlementItem.setBillNo(zxCtSuppliesLeasePaySettlementItem.getBillNo());
           // ????????????
           dbZxCtSuppliesLeasePaySettlementItem.setContrType(zxCtSuppliesLeasePaySettlementItem.getContrType());
           // ??????ID
           dbZxCtSuppliesLeasePaySettlementItem.setContractID(zxCtSuppliesLeasePaySettlementItem.getContractID());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setUnit(zxCtSuppliesLeasePaySettlementItem.getUnit());
           // ??????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setPrice(zxCtSuppliesLeasePaySettlementItem.getPrice());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setPayNo(zxCtSuppliesLeasePaySettlementItem.getPayNo());
           // ??????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtTax());
           // ??????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
           // ???????????????????????????(???)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtNoTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtNoTax());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setRemarks(zxCtSuppliesLeasePaySettlementItem.getRemarks());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setSort(zxCtSuppliesLeasePaySettlementItem.getSort());
           // ??????
           dbZxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeasePaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	ZxCtSuppliesLeasePaySettlement dbZxCtSuppliesShopPaySettlement = zxCtSuppliesLeasePaySettlementMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getMainID());
        	ZxCtSuppliesLeasePaySettlementItem settlementItem = new ZxCtSuppliesLeasePaySettlementItem();
        	settlementItem.setMainID(zxCtSuppliesLeasePaySettlementItem.getMainID());
        	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(settlementItem);
        	dbZxCtSuppliesShopPaySettlement.setThisAmt(itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmtNoTax).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setThisAmtTax(itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmtTax).reduce(BigDecimal.ZERO,BigDecimal::add));
            dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), dbZxCtSuppliesShopPaySettlement.getThisAmt()));
            thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
            thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
            thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
                if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "??????")) {
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "??????")) {
                			fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
                }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "??????")){
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "??????")) {
                			padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setFoodAmt(padTariffAmt);            	
                	
                }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "????????????")){
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "????????????")) {
                			otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
                	
                }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "?????????")){
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "?????????")) {
                			transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setInOutAmt(transportAmt);                      	
            }
                dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
                zxCtSuppliesLeasePaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
                
            	//?????????,????????????
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.update",zxCtSuppliesLeasePaySettlementItem);
        }
    }
    
    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId(
    		List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList) {
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
        if (zxCtSuppliesLeasePaySettlementItemList != null && zxCtSuppliesLeasePaySettlementItemList.size() > 0) {
           ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
           zxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(zxCtSuppliesLeasePaySettlementItemList, zxCtSuppliesLeasePaySettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//??????????????????
            ZxCtSuppliesLeaseSettlementList dbSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItemList.get(0).getZxCtSuppliesLeaseSettlementListId());
            ZxCtSuppliesLeaseResSettlement resSettlement = new ZxCtSuppliesLeaseResSettlement();
            resSettlement.setBillID(zxCtSuppliesLeasePaySettlementItemList.get(0).getZxCtSuppliesLeaseSettlementListId());
            List<ZxCtSuppliesLeaseResSettlement> resSettlementList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(resSettlement);
            if(resSettlementList.size()>0) {
            	thisAmt = resSettlementList.get(0).getThisAmt();
                thisAmtNoTax = resSettlementList.get(0).getThisAmtNoTax();
                thisAmtTax = resSettlementList.get(0).getThisAmtTax();
            }
//            ZxCtSuppliesLeaseResSettleItem settleItem = new ZxCtSuppliesLeaseResSettleItem();
//            settleItem.setMainID(zxCtSuppliesLeasePaySettlementItemList.get(0).getZxCtSuppliesLeaseSettlementListId());
//            List<ZxCtSuppliesLeaseResSettleItem> settleItemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(settleItem);
//            if(settleItemList.size()>0) {
//        		for(ZxCtSuppliesLeaseResSettleItem item : settleItemList) {
//        			thisAmt = CalcUtils.calcAdd(thisAmt, CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()));
//        			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, CalcUtils.calcDivide(CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()), CalcUtils.calcAdd(taxRate,CalcUtils.calcDivide(new BigDecimal(item.getTaxRate()), new BigDecimal(100),6))));
//        		}
//        		thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);        	
//            }
            ZxCtSuppliesLeasePaySettlement dbZxCtSuppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlement();
            dbZxCtSuppliesShopPaySettlement.setBillID(zxCtSuppliesLeasePaySettlementItemList.get(0).getZxCtSuppliesLeaseSettlementListId());
        	List<ZxCtSuppliesLeasePaySettlement> dbZxCtSuppliesShopPaySettlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(dbZxCtSuppliesShopPaySettlement);
        	dbZxCtSuppliesShopPaySettlement = dbZxCtSuppliesShopPaySettlementList.get(0);
        	ZxCtSuppliesLeasePaySettlementItem settlementItem = new ZxCtSuppliesLeasePaySettlementItem();
        	settlementItem.setMainID(dbZxCtSuppliesShopPaySettlementList.get(0).getZxCtSuppliesLeasePaySettlementId());
        	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(settlementItem);
        	if(itemList.size()>0) {        		
        		dbZxCtSuppliesShopPaySettlement.setThisAmt(itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add));
        		dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmtNoTax).reduce(BigDecimal.ZERO,BigDecimal::add));
        		dbZxCtSuppliesShopPaySettlement.setThisAmtTax(itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmtTax).reduce(BigDecimal.ZERO,BigDecimal::add));
        		dbZxCtSuppliesShopPaySettlement.setTotalAmt(CalcUtils.calcAdd(dbZxCtSuppliesShopPaySettlement.getUpAmt(), dbZxCtSuppliesShopPaySettlement.getThisAmt()));
        		thisAmt = CalcUtils.calcAdd(thisAmt, dbZxCtSuppliesShopPaySettlement.getThisAmt());
        		thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, dbZxCtSuppliesShopPaySettlement.getThisAmtNoTax());
        		thisAmtTax = CalcUtils.calcAdd(thisAmtTax, dbZxCtSuppliesShopPaySettlement.getThisAmtTax());
        		for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
        			if(StrUtil.equals(item.getPayType(), "??????")) {
        				fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());	
        			}else if(StrUtil.equals(item.getPayType(), "??????")) {
        				padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
        			}else if(StrUtil.equals(item.getPayType(), "????????????")) {
        				otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
        			}else if(StrUtil.equals(item.getPayType(), "?????????")) {
        				transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
        			}
        		}
        		dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
        		dbZxCtSuppliesShopPaySettlement.setFoodAmt(padTariffAmt);
        		dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);
        		dbZxCtSuppliesShopPaySettlement.setInOutAmt(transportAmt);
        	}else {
        		dbZxCtSuppliesShopPaySettlement.setFineAmt(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setFoodAmt(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setOtherAmt(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setInOutAmt(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setThisAmt(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setThisAmtNoTax(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setThisAmtTax(new BigDecimal(0));
        		dbZxCtSuppliesShopPaySettlement.setTotalAmt(new BigDecimal(0));
        	}
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesLeasePaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);   
        	//?????????,????????????
    		dbSettlementList.setThisAmt(thisAmt);
    		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
    		dbSettlementList.setThisAmtTax(thisAmtTax);
    		dbSettlementList.setModifyUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeasePaySettlementItemList);
        }
    }

	@Override
	public ResponseEntity getZxCtSuppliesLeasePaySettlementItemListByContID(
			ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        if (zxCtSuppliesLeasePaySettlementItem == null) {
            zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
        }
        ZxCtSuppliesLeasePaySettlement paySettlement = new ZxCtSuppliesLeasePaySettlement();
        paySettlement.setBillID(zxCtSuppliesLeasePaySettlementItem.getMainID());
        List<ZxCtSuppliesLeasePaySettlement> paySettlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(paySettlement);
        if(paySettlementList.size()>0) {
        	zxCtSuppliesLeasePaySettlementItem.setMainID(paySettlementList.get(0).getZxCtSuppliesLeasePaySettlementId());
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesLeasePaySettlementItem.getPage(),zxCtSuppliesLeasePaySettlementItem.getLimit());
        // ????????????
        List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(zxCtSuppliesLeasePaySettlementItem);
        // ??????????????????
        PageInfo<ZxCtSuppliesLeasePaySettlementItem> p = new PageInfo<>(zxCtSuppliesLeasePaySettlementItemList);

        return repEntity.okList(zxCtSuppliesLeasePaySettlementItemList, p.getTotal());
	}
}
