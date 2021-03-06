package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContrLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeasePaySettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettleItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseResSettlementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.service.ZxCtSuppliesLeaseResSettleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesLeaseResSettleItemService")
public class ZxCtSuppliesLeaseResSettleItemServiceImpl implements ZxCtSuppliesLeaseResSettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettleItemMapper zxCtSuppliesLeaseResSettleItemMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrLeaseListMapper zxCtSuppliesContrLeaseListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListMapper zxCtSuppliesLeaseSettlementListMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementItemMapper zxCtSuppliesLeasePaySettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementMapper zxCtSuppliesLeasePaySettlementMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettlementMapper zxCtSuppliesLeaseResSettlementMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemListByCondition(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        if (zxCtSuppliesLeaseResSettleItem == null) {
            zxCtSuppliesLeaseResSettleItem = new ZxCtSuppliesLeaseResSettleItem();
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesLeaseResSettleItem.getPage(),zxCtSuppliesLeaseResSettleItem.getLimit());
        // ????????????
        List<ZxCtSuppliesLeaseResSettleItem> zxCtSuppliesLeaseResSettleItemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(zxCtSuppliesLeaseResSettleItem);
        // ??????????????????
        PageInfo<ZxCtSuppliesLeaseResSettleItem> p = new PageInfo<>(zxCtSuppliesLeaseResSettleItemList);

        return repEntity.okList(zxCtSuppliesLeaseResSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemDetail(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        if (zxCtSuppliesLeaseResSettleItem == null) {
            zxCtSuppliesLeaseResSettleItem = new ZxCtSuppliesLeaseResSettleItem();
        }
        // ????????????
        ZxCtSuppliesLeaseResSettleItem dbZxCtSuppliesLeaseResSettleItem = zxCtSuppliesLeaseResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId());
        // ????????????
        if (dbZxCtSuppliesLeaseResSettleItem != null) {
            return repEntity.ok(dbZxCtSuppliesLeaseResSettleItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesLeaseResSettleItem(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesLeaseResSettleItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
        zxCtSuppliesLeaseResSettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeaseResSettleItemMapper.insert(zxCtSuppliesLeaseResSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeaseResSettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesLeaseResSettleItem(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeaseResSettleItem dbZxCtSuppliesLeaseResSettleItem = zxCtSuppliesLeaseResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId());
        if (dbZxCtSuppliesLeaseResSettleItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId())) {
           // ??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setEditTime(zxCtSuppliesLeaseResSettleItem.getEditTime());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setRentUnit(zxCtSuppliesLeaseResSettleItem.getRentUnit());
           // ??????ID
           dbZxCtSuppliesLeaseResSettleItem.setMainID(zxCtSuppliesLeaseResSettleItem.getMainID());
           // ??????????????????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setUpQty(zxCtSuppliesLeaseResSettleItem.getUpQty());
           // ??????????????????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setUpAmt(zxCtSuppliesLeaseResSettleItem.getUpAmt());
           // ??????????????????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setTotalQty(zxCtSuppliesLeaseResSettleItem.getTotalQty());
           // ??????????????????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setTotalAmt(zxCtSuppliesLeaseResSettleItem.getTotalAmt());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setOrderNum(zxCtSuppliesLeaseResSettleItem.getOrderNum());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setEquipName(zxCtSuppliesLeaseResSettleItem.getEquipName());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setEquipCode(zxCtSuppliesLeaseResSettleItem.getEquipCode());
           // ??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setComOrders(zxCtSuppliesLeaseResSettleItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setComName(zxCtSuppliesLeaseResSettleItem.getComName());
           // ????????????ID
           dbZxCtSuppliesLeaseResSettleItem.setComID(zxCtSuppliesLeaseResSettleItem.getComID());
           // ??????(%)
           dbZxCtSuppliesLeaseResSettleItem.setTaxRate(zxCtSuppliesLeaseResSettleItem.getTaxRate());
           // ???????????????ID
           dbZxCtSuppliesLeaseResSettleItem.setSignedOrderItemID(zxCtSuppliesLeaseResSettleItem.getSignedOrderItemID());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignedNo(zxCtSuppliesLeaseResSettleItem.getSignedNo());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setStartDate(zxCtSuppliesLeaseResSettleItem.getStartDate());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setPeriod(zxCtSuppliesLeaseResSettleItem.getPeriod());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setBillNo(zxCtSuppliesLeaseResSettleItem.getBillNo());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setUnit(zxCtSuppliesLeaseResSettleItem.getUnit());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setContrTrrm(zxCtSuppliesLeaseResSettleItem.getContrTrrm());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setContractQty(zxCtSuppliesLeaseResSettleItem.getContractQty());
           // ????????????ID
           dbZxCtSuppliesLeaseResSettleItem.setContractItemID(zxCtSuppliesLeaseResSettleItem.getContractItemID());
           // ????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setContractAmt(zxCtSuppliesLeaseResSettleItem.getContractAmt());
           // ??????ID
           dbZxCtSuppliesLeaseResSettleItem.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setSpec(zxCtSuppliesLeaseResSettleItem.getSpec());
           // ??????(???)
           dbZxCtSuppliesLeaseResSettleItem.setContractPrice(zxCtSuppliesLeaseResSettleItem.getContractPrice());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setAlterTrrm(zxCtSuppliesLeaseResSettleItem.getAlterTrrm());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setChangedQty(zxCtSuppliesLeaseResSettleItem.getChangedQty());
           // ???????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setChangedAmt(zxCtSuppliesLeaseResSettleItem.getChangedAmt());
           // ???????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setAlterPrice(zxCtSuppliesLeaseResSettleItem.getAlterPrice());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtTax(zxCtSuppliesLeaseResSettleItem.getThisAmtTax());
           // ??????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setThisQty(zxCtSuppliesLeaseResSettleItem.getThisQty());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmt(zxCtSuppliesLeaseResSettleItem.getThisAmt());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisPrice(zxCtSuppliesLeaseResSettleItem.getThisPrice());
           // ???????????????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtNoTax(zxCtSuppliesLeaseResSettleItem.getThisAmtNoTax());
           //??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignContrTrrm(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm());
           //??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignQty(zxCtSuppliesLeaseResSettleItem.getSignQty());
           //????????????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignRentEndDate(zxCtSuppliesLeaseResSettleItem.getSignRentEndDate());
           //????????????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignRentStartDate(zxCtSuppliesLeaseResSettleItem.getSignRentStartDate());
           //??????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setSignThisAmt(zxCtSuppliesLeaseResSettleItem.getSignThisAmt());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setRemarks(zxCtSuppliesLeaseResSettleItem.getRemarks());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setSort(zxCtSuppliesLeaseResSettleItem.getSort());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettleItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseResSettleItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseResSettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(List<ZxCtSuppliesLeaseResSettleItem> zxCtSuppliesLeaseResSettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesLeaseResSettleItemList != null && zxCtSuppliesLeaseResSettleItemList.size() > 0) {
           ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem = new ZxCtSuppliesLeaseResSettleItem();
           zxCtSuppliesLeaseResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettleItemMapper.batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(zxCtSuppliesLeaseResSettleItemList, zxCtSuppliesLeaseResSettleItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeaseResSettleItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemListByConID(
    		ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String billNo = "";
		DecimalFormat decimalFormat = new DecimalFormat("00");	
        ZxCtSuppliesLeaseSettlementList dbSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getMainID());
			ZxCtSuppliesLeaseResSettlement resSettlement = new ZxCtSuppliesLeaseResSettlement();
			resSettlement.setBillID(zxCtSuppliesLeaseResSettleItem.getMainID());
			List<ZxCtSuppliesLeaseResSettlement> resSettlementList = zxCtSuppliesLeaseResSettlementMapper.selectByZxCtSuppliesLeaseResSettlementList(resSettlement);
			if(resSettlementList.size()>0) {
				resSettlement = resSettlementList.get(0);
			}else {
				resSettlement.setZxCtSuppliesLeaseResSettlementId(UuidUtil.generate());
				resSettlement.setBillNo(dbSettlementList.getBillNo());
				resSettlement.setContractID(dbSettlementList.getContractID());
				resSettlement.setCreateUserInfo(userKey, realName);
				zxCtSuppliesLeaseResSettlementMapper.insert(resSettlement);
			}
    	ZxCtSuppliesLeaseResSettleItem item = new ZxCtSuppliesLeaseResSettleItem();
    	item.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
    	item.setMainID(resSettlement.getZxCtSuppliesLeaseResSettlementId());
    	item.setBillNo(zxCtSuppliesLeaseResSettleItem.getBillNo());
    	List<ZxCtSuppliesLeaseResSettleItem> itemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(item);
    	if(itemList.size() == 0) {
            int num = Integer.parseInt(dbSettlementList.getInitSerialNumber());
    			ZxCtSuppliesLeaseSettlementList leaseSettlement = new ZxCtSuppliesLeaseSettlementList();
    			leaseSettlement.setInitSerialNumber(decimalFormat.format(num-1));
    			leaseSettlement.setApih5FlowStatus("2");
    			List<ZxCtSuppliesLeaseSettlementList> leaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(leaseSettlement);
    			if(leaseSettlementList.size()>0) {
    				billNo = leaseSettlementList.get(0).getBillNo();
    				dbSettlementList.setTotalEquipAmt(leaseSettlementList.get(0).getTotalEquipAmt());
    			}
    			dbSettlementList.setModifyUserInfo(userKey, realName);
    			zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);    		
    		ZxCtSuppliesContrLeaseList lease = new ZxCtSuppliesContrLeaseList();
    		lease.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());	
    		List<ZxCtSuppliesContrLeaseList> leaseList = zxCtSuppliesContrLeaseListMapper.selectByZxCtSuppliesContrLeaseListList(lease);
    		for(ZxCtSuppliesContrLeaseList contrLease : leaseList) {
    			item = new ZxCtSuppliesLeaseResSettleItem();
    	    	item.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());    			
    	    	item.setBillNo(billNo);    	
    	    	item.setDetailListId(contrLease.getZxCtSuppliesContrLeaseListId());
    			item.setEquipCode(contrLease.getWorkNo());
    			item.setEquipName(contrLease.getWorkName());
    			itemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(item);
    			item = new ZxCtSuppliesLeaseResSettleItem();
    			if(itemList.size()>0) {    				
    				item.setUpQty(itemList.get(0).getSignThisAmt());
    				item.setUpAmtNoTax(itemList.get(0).getThisAmtNoTax());
    				item.setUpAmtTax(itemList.get(0).getThisAmtTax());
    				item.setUpAmt(itemList.get(0).getTotalAmt());
    				item.setTotalQty(itemList.get(0).getSignThisAmt());
    				item.setTotalAmt(itemList.get(0).getTotalAmt());
    				item.setThisAmt(new BigDecimal(0));
    				item.setThisAmtNoTax(new BigDecimal(0));
    				item.setThisAmtTax(new BigDecimal(0));
    			}else {
    				item.setUpQty(new BigDecimal(0));
    				item.setUpAmtNoTax(new BigDecimal(0));
    				item.setUpAmtTax(new BigDecimal(0));
    				item.setUpAmt(new BigDecimal(0));
    				item.setTotalQty(new BigDecimal(0));
    				item.setTotalAmt(new BigDecimal(0));
    				item.setThisAmt(new BigDecimal(0));
    				item.setThisAmtNoTax(new BigDecimal(0));
    				item.setThisAmtTax(new BigDecimal(0));
    			}
    			item.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    			item.setEquipName(contrLease.getWorkName());
    			item.setEquipCode(contrLease.getWorkNo());
    	    	item.setDetailListId(contrLease.getZxCtSuppliesContrLeaseListId());
    	    	item.setMainID(resSettlement.getZxCtSuppliesLeaseResSettlementId());
    			item.setBillNo(zxCtSuppliesLeaseResSettleItem.getBillNo());
    			item.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
    			item.setCreateUserInfo(userKey, realName);
    			item.setSpec(contrLease.getSpec());
    			item.setUnit(contrLease.getUnit());
    			item.setRentUnit(contrLease.getRentUnit());
    			item.setContractPrice(contrLease.getPrice());
    			item.setThisPrice(contrLease.getChangePrice());
    			item.setContrTrrm(contrLease.getContrTrrm());
    			item.setTaxRate(contrLease.getTaxRate());
    			item.setContractQty(contrLease.getQty());
    			item.setAlterPrice(contrLease.getPrice());
    			item.setChangedQty(contrLease.getChangeQty());
    			item.setAlterTrrm(contrLease.getAlterTrrm());
//    			if(contrLease.getChangePrice() != null) {
//        			item.setChangedAmt(contrLease.getChangePrice());	
//    			}
    			zxCtSuppliesLeaseResSettleItemMapper.insert(item);
    		}
    		item = new ZxCtSuppliesLeaseResSettleItem();
			item.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
	    	item.setMainID(resSettlement.getZxCtSuppliesLeaseResSettlementId());
    		itemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(item);
    	}
        // ??????????????????
        PageInfo<ZxCtSuppliesLeaseResSettleItem> p = new PageInfo<>(itemList);

        return repEntity.okList(itemList, p.getTotal());
    }

	@Override
	public ResponseEntity updateZxCtSuppliesLeaseResSettleItemByConID(
			ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal taxRate = new BigDecimal(1);//????????????
        BigDecimal thisAmt = null;//??????????????????(???)
        BigDecimal thisAmtNoTax = new BigDecimal(0);//?????????????????????????????????
        BigDecimal thisAmtTax = null;//????????????????????????
        BigDecimal totalAmt = null;//??????????????????????????????
        BigDecimal changedThisQty = null;
        BigDecimal thisEquipAmt = null;
        BigDecimal totalEquipAmt = null;
        int flag = 0;
        ZxCtSuppliesLeaseResSettleItem dbZxCtSuppliesLeaseResSettleItem = zxCtSuppliesLeaseResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId());
        if (dbZxCtSuppliesLeaseResSettleItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId())) {
        	if(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm() != null) {
        		if(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm().compareTo(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm()) == 1) {
        			return repEntity.layerMessage("no", "??????????????????????????????????????????????????????");	
        		}
        		if(zxCtSuppliesLeaseResSettleItem.getSignQty().compareTo(dbZxCtSuppliesLeaseResSettleItem.getChangedQty()) == 1) {
        			return repEntity.layerMessage("no", "??????????????????????????????????????????????????????");	
        		}
        	}else {
        		if(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm().compareTo(dbZxCtSuppliesLeaseResSettleItem.getContrTrrm()) == 1) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????");	
        		}
        		if(zxCtSuppliesLeaseResSettleItem.getSignQty().compareTo(dbZxCtSuppliesLeaseResSettleItem.getContractQty()) == 1) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????");	
        		}
        	}
        	if(dbZxCtSuppliesLeaseResSettleItem.getUpQty() != null) {
        		if(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm() == null) {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getContrTrrm(), dbZxCtSuppliesLeaseResSettleItem.getContractQty());
        		}else {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm(), dbZxCtSuppliesLeaseResSettleItem.getChangedQty());
        		}
        		if(CalcUtils.calcAdd(dbZxCtSuppliesLeaseResSettleItem.getUpQty(), zxCtSuppliesLeaseResSettleItem.getSignThisAmt()).compareTo(changedThisQty) == 1) {
        			return repEntity.layerMessage("no", "????????????*??????????????????????????????*?????????");
        		}
        	}else {
        		if(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm() == null) {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getContrTrrm(), dbZxCtSuppliesLeaseResSettleItem.getContractQty()); 	
        		}else {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm(), dbZxCtSuppliesLeaseResSettleItem.getChangedQty());        		
        		}
        		if(zxCtSuppliesLeaseResSettleItem.getSignThisAmt().compareTo(changedThisQty) == 1) {
        			return repEntity.layerMessage("no", "????????????*??????????????????????????????*?????????");
        		}
        	}
           // ??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setEditTime(zxCtSuppliesLeaseResSettleItem.getEditTime());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setRentUnit(zxCtSuppliesLeaseResSettleItem.getRentUnit());
           // ??????ID
           dbZxCtSuppliesLeaseResSettleItem.setMainID(zxCtSuppliesLeaseResSettleItem.getMainID());
           // ??????????????????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setUpQty(zxCtSuppliesLeaseResSettleItem.getUpQty());
           // ??????????????????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setUpAmt(zxCtSuppliesLeaseResSettleItem.getUpAmt());
           // ??????????????????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setTotalQty(zxCtSuppliesLeaseResSettleItem.getTotalQty());
           // ??????????????????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setTotalAmt(zxCtSuppliesLeaseResSettleItem.getTotalAmt());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setOrderNum(zxCtSuppliesLeaseResSettleItem.getOrderNum());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setEquipName(zxCtSuppliesLeaseResSettleItem.getEquipName());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setEquipCode(zxCtSuppliesLeaseResSettleItem.getEquipCode());
           // ??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setComOrders(zxCtSuppliesLeaseResSettleItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setComName(zxCtSuppliesLeaseResSettleItem.getComName());
           // ????????????ID
           dbZxCtSuppliesLeaseResSettleItem.setComID(zxCtSuppliesLeaseResSettleItem.getComID());
           // ??????(%)
           dbZxCtSuppliesLeaseResSettleItem.setTaxRate(zxCtSuppliesLeaseResSettleItem.getTaxRate());
           // ???????????????ID
           dbZxCtSuppliesLeaseResSettleItem.setSignedOrderItemID(zxCtSuppliesLeaseResSettleItem.getSignedOrderItemID());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignedNo(zxCtSuppliesLeaseResSettleItem.getSignedNo());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setStartDate(zxCtSuppliesLeaseResSettleItem.getStartDate());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setPeriod(zxCtSuppliesLeaseResSettleItem.getPeriod());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setBillNo(zxCtSuppliesLeaseResSettleItem.getBillNo());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setUnit(zxCtSuppliesLeaseResSettleItem.getUnit());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setContrTrrm(zxCtSuppliesLeaseResSettleItem.getContrTrrm());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setContractQty(zxCtSuppliesLeaseResSettleItem.getContractQty());
           // ????????????ID
           dbZxCtSuppliesLeaseResSettleItem.setContractItemID(zxCtSuppliesLeaseResSettleItem.getContractItemID());
           // ????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setContractAmt(zxCtSuppliesLeaseResSettleItem.getContractAmt());
           // ??????ID
           dbZxCtSuppliesLeaseResSettleItem.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
           // ????????????
           dbZxCtSuppliesLeaseResSettleItem.setSpec(zxCtSuppliesLeaseResSettleItem.getSpec());
           // ??????(???)
           dbZxCtSuppliesLeaseResSettleItem.setContractPrice(zxCtSuppliesLeaseResSettleItem.getContractPrice());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setAlterTrrm(zxCtSuppliesLeaseResSettleItem.getAlterTrrm());
           // ???????????????
           dbZxCtSuppliesLeaseResSettleItem.setChangedQty(zxCtSuppliesLeaseResSettleItem.getChangedQty());
           // ???????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setChangedAmt(zxCtSuppliesLeaseResSettleItem.getChangedAmt());
           // ???????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setAlterPrice(zxCtSuppliesLeaseResSettleItem.getAlterPrice());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtTax(zxCtSuppliesLeaseResSettleItem.getThisAmtTax());
           // ??????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setThisQty(zxCtSuppliesLeaseResSettleItem.getThisQty());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmt(zxCtSuppliesLeaseResSettleItem.getThisAmt());
           // ??????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisPrice(zxCtSuppliesLeaseResSettleItem.getThisPrice());
           // ???????????????????????????(???)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtNoTax(zxCtSuppliesLeaseResSettleItem.getThisAmtNoTax());
           //??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignContrTrrm(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm());
           //??????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignQty(zxCtSuppliesLeaseResSettleItem.getSignQty());
           //????????????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignRentEndDate(zxCtSuppliesLeaseResSettleItem.getSignRentEndDate());
           //????????????????????????
           dbZxCtSuppliesLeaseResSettleItem.setSignRentStartDate(zxCtSuppliesLeaseResSettleItem.getSignRentStartDate());
           //??????????????????*??????
           dbZxCtSuppliesLeaseResSettleItem.setSignThisAmt(zxCtSuppliesLeaseResSettleItem.getSignThisAmt());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setRemarks(zxCtSuppliesLeaseResSettleItem.getRemarks());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setSort(zxCtSuppliesLeaseResSettleItem.getSort());
           // ??????
           dbZxCtSuppliesLeaseResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettleItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseResSettleItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//????????????
        	ZxCtSuppliesLeaseResSettlement resSettlement = zxCtSuppliesLeaseResSettlementMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getMainID());
        	ZxCtSuppliesLeaseSettlementList dbSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(resSettlement.getBillID());
        	if(dbSettlementList != null) {
        		ZxCtSuppliesLeaseResSettleItem settleItem = new ZxCtSuppliesLeaseResSettleItem();
        		settleItem.setMainID(resSettlement.getZxCtSuppliesLeaseResSettlementId());
        		settleItem.setBillNo(dbZxCtSuppliesLeaseResSettleItem.getBillNo());
        		List<ZxCtSuppliesLeaseResSettleItem> itemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(settleItem);
        		for(ZxCtSuppliesLeaseResSettleItem item : itemList) {
        			thisEquipAmt = CalcUtils.calcAdd(thisEquipAmt, CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()));
        			totalEquipAmt = CalcUtils.calcAdd(totalEquipAmt, CalcUtils.calcAdd(item.getUpAmt(), item.getThisAmt()));
        			thisAmt = CalcUtils.calcAdd(thisAmt, CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()));
        			thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, CalcUtils.calcDivide(CalcUtils.calcMultiply(CalcUtils.calcMultiply(item.getSignQty(), item.getSignContrTrrm()), item.getContractPrice()), CalcUtils.calcAdd(taxRate,CalcUtils.calcDivide(new BigDecimal(item.getTaxRate()), new BigDecimal(100),6))));
        		}
        		thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);
        		resSettlement.setTotalAmt(totalEquipAmt);
        		resSettlement.setThisAmt(thisEquipAmt);
        		resSettlement.setThisAmtNoTax(thisAmtNoTax);
        		resSettlement.setThisAmtTax(thisAmtTax);
        		resSettlement.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesLeaseResSettlementMapper.updateByPrimaryKeySelective(resSettlement);
                Iterator<ZxCtSuppliesLeaseResSettleItem> iterator = itemList.iterator();
                while (iterator.hasNext()) {
                	ZxCtSuppliesLeaseResSettleItem item = iterator.next();
                    if (item.getTotalAmt() == null) {
                        iterator.remove();
                    }
                }
        		totalAmt = itemList.stream().map(ZxCtSuppliesLeaseResSettleItem::getTotalAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
        		ZxCtSuppliesLeasePaySettlementItem shopSettlement = new ZxCtSuppliesLeasePaySettlementItem();
                ZxCtSuppliesLeasePaySettlement paySettlement = new ZxCtSuppliesLeasePaySettlement();
                paySettlement.setBillID(dbSettlementList.getZxCtSuppliesLeaseSettlementListId());
                List<ZxCtSuppliesLeasePaySettlement> paySettlementList = zxCtSuppliesLeasePaySettlementMapper.selectByZxCtSuppliesLeasePaySettlementList(paySettlement);
                if(paySettlementList.size()>0) {
                	shopSettlement.setMainID(paySettlementList.get(0).getZxCtSuppliesLeasePaySettlementId());
                }
        		List<ZxCtSuppliesLeasePaySettlementItem> shopSettlementList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(shopSettlement);
        		if(shopSettlementList.size()>0) {
        			for(ZxCtSuppliesLeasePaySettlementItem item : shopSettlementList) {
        				thisAmt = CalcUtils.calcAdd(thisAmt, item.getThisAmt());
        				thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, item.getThisAmtNoTax());
        				thisAmtTax = CalcUtils.calcAdd(thisAmtTax, item.getThisAmtTax());
        			}
        		}        		
        		dbSettlementList.setTotalEquipAmt(totalEquipAmt);
        		dbSettlementList.setThisEquipAmt(thisEquipAmt);
        		dbSettlementList.setThisAmt(thisAmt);
        		dbSettlementList.setThisAmtNoTax(thisAmtNoTax);
        		dbSettlementList.setThisAmtTax(thisAmtTax);
        		dbSettlementList.setTotalAmt(totalAmt);
        		dbSettlementList.setModifyUserInfo(userKey, realName);
        		zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKeySelective(dbSettlementList);
        	}
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseResSettleItem);
        }
	}
}
