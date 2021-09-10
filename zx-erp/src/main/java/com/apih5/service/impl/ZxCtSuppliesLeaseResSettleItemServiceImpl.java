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
        // 分页查询
        PageHelper.startPage(zxCtSuppliesLeaseResSettleItem.getPage(),zxCtSuppliesLeaseResSettleItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesLeaseResSettleItem> zxCtSuppliesLeaseResSettleItemList = zxCtSuppliesLeaseResSettleItemMapper.selectByZxCtSuppliesLeaseResSettleItemList(zxCtSuppliesLeaseResSettleItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeaseResSettleItem> p = new PageInfo<>(zxCtSuppliesLeaseResSettleItemList);

        return repEntity.okList(zxCtSuppliesLeaseResSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemDetail(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        if (zxCtSuppliesLeaseResSettleItem == null) {
            zxCtSuppliesLeaseResSettleItem = new ZxCtSuppliesLeaseResSettleItem();
        }
        // 获取数据
        ZxCtSuppliesLeaseResSettleItem dbZxCtSuppliesLeaseResSettleItem = zxCtSuppliesLeaseResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId());
        // 数据存在
        if (dbZxCtSuppliesLeaseResSettleItem != null) {
            return repEntity.ok(dbZxCtSuppliesLeaseResSettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 最后编辑时间
           dbZxCtSuppliesLeaseResSettleItem.setEditTime(zxCtSuppliesLeaseResSettleItem.getEditTime());
           // 租期单位
           dbZxCtSuppliesLeaseResSettleItem.setRentUnit(zxCtSuppliesLeaseResSettleItem.getRentUnit());
           // 主表ID
           dbZxCtSuppliesLeaseResSettleItem.setMainID(zxCtSuppliesLeaseResSettleItem.getMainID());
           // 至上期末累计结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setUpQty(zxCtSuppliesLeaseResSettleItem.getUpQty());
           // 至上期末累计结算金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setUpAmt(zxCtSuppliesLeaseResSettleItem.getUpAmt());
           // 至本期末累计结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setTotalQty(zxCtSuppliesLeaseResSettleItem.getTotalQty());
           // 至本期末累计结算金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setTotalAmt(zxCtSuppliesLeaseResSettleItem.getTotalAmt());
           // 序号
           dbZxCtSuppliesLeaseResSettleItem.setOrderNum(zxCtSuppliesLeaseResSettleItem.getOrderNum());
           // 物资名称
           dbZxCtSuppliesLeaseResSettleItem.setEquipName(zxCtSuppliesLeaseResSettleItem.getEquipName());
           // 物资编码
           dbZxCtSuppliesLeaseResSettleItem.setEquipCode(zxCtSuppliesLeaseResSettleItem.getEquipCode());
           // 所属公司排序
           dbZxCtSuppliesLeaseResSettleItem.setComOrders(zxCtSuppliesLeaseResSettleItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeaseResSettleItem.setComName(zxCtSuppliesLeaseResSettleItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeaseResSettleItem.setComID(zxCtSuppliesLeaseResSettleItem.getComID());
           // 税率(%)
           dbZxCtSuppliesLeaseResSettleItem.setTaxRate(zxCtSuppliesLeaseResSettleItem.getTaxRate());
           // 签认单明细ID
           dbZxCtSuppliesLeaseResSettleItem.setSignedOrderItemID(zxCtSuppliesLeaseResSettleItem.getSignedOrderItemID());
           // 签认单编号
           dbZxCtSuppliesLeaseResSettleItem.setSignedNo(zxCtSuppliesLeaseResSettleItem.getSignedNo());
           // 起租日期
           dbZxCtSuppliesLeaseResSettleItem.setStartDate(zxCtSuppliesLeaseResSettleItem.getStartDate());
           // 结算期次
           dbZxCtSuppliesLeaseResSettleItem.setPeriod(zxCtSuppliesLeaseResSettleItem.getPeriod());
           // 结算单编号
           dbZxCtSuppliesLeaseResSettleItem.setBillNo(zxCtSuppliesLeaseResSettleItem.getBillNo());
           // 计量单位
           dbZxCtSuppliesLeaseResSettleItem.setUnit(zxCtSuppliesLeaseResSettleItem.getUnit());
           // 合同租期
           dbZxCtSuppliesLeaseResSettleItem.setContrTrrm(zxCtSuppliesLeaseResSettleItem.getContrTrrm());
           // 合同数量
           dbZxCtSuppliesLeaseResSettleItem.setContractQty(zxCtSuppliesLeaseResSettleItem.getContractQty());
           // 合同明细ID
           dbZxCtSuppliesLeaseResSettleItem.setContractItemID(zxCtSuppliesLeaseResSettleItem.getContractItemID());
           // 合同金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setContractAmt(zxCtSuppliesLeaseResSettleItem.getContractAmt());
           // 合同ID
           dbZxCtSuppliesLeaseResSettleItem.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
           // 规格型号
           dbZxCtSuppliesLeaseResSettleItem.setSpec(zxCtSuppliesLeaseResSettleItem.getSpec());
           // 单价(元)
           dbZxCtSuppliesLeaseResSettleItem.setContractPrice(zxCtSuppliesLeaseResSettleItem.getContractPrice());
           // 变更后租期
           dbZxCtSuppliesLeaseResSettleItem.setAlterTrrm(zxCtSuppliesLeaseResSettleItem.getAlterTrrm());
           // 变更后数量
           dbZxCtSuppliesLeaseResSettleItem.setChangedQty(zxCtSuppliesLeaseResSettleItem.getChangedQty());
           // 变更后金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setChangedAmt(zxCtSuppliesLeaseResSettleItem.getChangedAmt());
           // 变更后单价(元)
           dbZxCtSuppliesLeaseResSettleItem.setAlterPrice(zxCtSuppliesLeaseResSettleItem.getAlterPrice());
           // 本期结算税额(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtTax(zxCtSuppliesLeaseResSettleItem.getThisAmtTax());
           // 本期结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setThisQty(zxCtSuppliesLeaseResSettleItem.getThisQty());
           // 本期结算金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmt(zxCtSuppliesLeaseResSettleItem.getThisAmt());
           // 本期结算单价(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisPrice(zxCtSuppliesLeaseResSettleItem.getThisPrice());
           // 本期结算不含税金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtNoTax(zxCtSuppliesLeaseResSettleItem.getThisAmtNoTax());
           //本期结算租期
           dbZxCtSuppliesLeaseResSettleItem.setSignContrTrrm(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm());
           //本期结算数量
           dbZxCtSuppliesLeaseResSettleItem.setSignQty(zxCtSuppliesLeaseResSettleItem.getSignQty());
           //本期租赁截止日期
           dbZxCtSuppliesLeaseResSettleItem.setSignRentEndDate(zxCtSuppliesLeaseResSettleItem.getSignRentEndDate());
           //本期租赁起始日期
           dbZxCtSuppliesLeaseResSettleItem.setSignRentStartDate(zxCtSuppliesLeaseResSettleItem.getSignRentStartDate());
           //本期结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setSignThisAmt(zxCtSuppliesLeaseResSettleItem.getSignThisAmt());
           // 备注
           dbZxCtSuppliesLeaseResSettleItem.setRemarks(zxCtSuppliesLeaseResSettleItem.getRemarks());
           // 排序
           dbZxCtSuppliesLeaseResSettleItem.setSort(zxCtSuppliesLeaseResSettleItem.getSort());
           // 共通
           dbZxCtSuppliesLeaseResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettleItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseResSettleItem);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeaseResSettleItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
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
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeaseResSettleItem> p = new PageInfo<>(itemList);

        return repEntity.okList(itemList, p.getTotal());
    }

	@Override
	public ResponseEntity updateZxCtSuppliesLeaseResSettleItemByConID(
			ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = null;//本期结算金额(元)
        BigDecimal thisAmtNoTax = new BigDecimal(0);//本期清单结算不含税金额
        BigDecimal thisAmtTax = null;//本期清单结算税额
        BigDecimal totalAmt = null;//累计清单结算含税金额
        BigDecimal changedThisQty = null;
        BigDecimal thisEquipAmt = null;
        BigDecimal totalEquipAmt = null;
        int flag = 0;
        ZxCtSuppliesLeaseResSettleItem dbZxCtSuppliesLeaseResSettleItem = zxCtSuppliesLeaseResSettleItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId());
        if (dbZxCtSuppliesLeaseResSettleItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseResSettleItem.getZxCtSuppliesLeaseSettlementItemId())) {
        	if(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm() != null) {
        		if(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm().compareTo(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm()) == 1) {
        			return repEntity.layerMessage("no", "本期结算租期不得大于变更后合同租期！");	
        		}
        		if(zxCtSuppliesLeaseResSettleItem.getSignQty().compareTo(dbZxCtSuppliesLeaseResSettleItem.getChangedQty()) == 1) {
        			return repEntity.layerMessage("no", "本期结算数量不得大于变更后合同数量！");	
        		}
        	}else {
        		if(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm().compareTo(dbZxCtSuppliesLeaseResSettleItem.getContrTrrm()) == 1) {
        			return repEntity.layerMessage("no", "本期结算租期不得大于合同租期！");	
        		}
        		if(zxCtSuppliesLeaseResSettleItem.getSignQty().compareTo(dbZxCtSuppliesLeaseResSettleItem.getContractQty()) == 1) {
        			return repEntity.layerMessage("no", "本期结算数量不得大于合同数量！");	
        		}
        	}
        	if(dbZxCtSuppliesLeaseResSettleItem.getUpQty() != null) {
        		if(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm() == null) {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getContrTrrm(), dbZxCtSuppliesLeaseResSettleItem.getContractQty());
        		}else {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm(), dbZxCtSuppliesLeaseResSettleItem.getChangedQty());
        		}
        		if(CalcUtils.calcAdd(dbZxCtSuppliesLeaseResSettleItem.getUpQty(), zxCtSuppliesLeaseResSettleItem.getSignThisAmt()).compareTo(changedThisQty) == 1) {
        			return repEntity.layerMessage("no", "结算数量*租期不得大于合同数量*租期！");
        		}
        	}else {
        		if(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm() == null) {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getContrTrrm(), dbZxCtSuppliesLeaseResSettleItem.getContractQty()); 	
        		}else {
        			changedThisQty = CalcUtils.calcMultiply(dbZxCtSuppliesLeaseResSettleItem.getAlterTrrm(), dbZxCtSuppliesLeaseResSettleItem.getChangedQty());        		
        		}
        		if(zxCtSuppliesLeaseResSettleItem.getSignThisAmt().compareTo(changedThisQty) == 1) {
        			return repEntity.layerMessage("no", "结算数量*租期不得大于合同数量*租期！");
        		}
        	}
           // 最后编辑时间
           dbZxCtSuppliesLeaseResSettleItem.setEditTime(zxCtSuppliesLeaseResSettleItem.getEditTime());
           // 租期单位
           dbZxCtSuppliesLeaseResSettleItem.setRentUnit(zxCtSuppliesLeaseResSettleItem.getRentUnit());
           // 主表ID
           dbZxCtSuppliesLeaseResSettleItem.setMainID(zxCtSuppliesLeaseResSettleItem.getMainID());
           // 至上期末累计结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setUpQty(zxCtSuppliesLeaseResSettleItem.getUpQty());
           // 至上期末累计结算金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setUpAmt(zxCtSuppliesLeaseResSettleItem.getUpAmt());
           // 至本期末累计结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setTotalQty(zxCtSuppliesLeaseResSettleItem.getTotalQty());
           // 至本期末累计结算金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setTotalAmt(zxCtSuppliesLeaseResSettleItem.getTotalAmt());
           // 序号
           dbZxCtSuppliesLeaseResSettleItem.setOrderNum(zxCtSuppliesLeaseResSettleItem.getOrderNum());
           // 物资名称
           dbZxCtSuppliesLeaseResSettleItem.setEquipName(zxCtSuppliesLeaseResSettleItem.getEquipName());
           // 物资编码
           dbZxCtSuppliesLeaseResSettleItem.setEquipCode(zxCtSuppliesLeaseResSettleItem.getEquipCode());
           // 所属公司排序
           dbZxCtSuppliesLeaseResSettleItem.setComOrders(zxCtSuppliesLeaseResSettleItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeaseResSettleItem.setComName(zxCtSuppliesLeaseResSettleItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeaseResSettleItem.setComID(zxCtSuppliesLeaseResSettleItem.getComID());
           // 税率(%)
           dbZxCtSuppliesLeaseResSettleItem.setTaxRate(zxCtSuppliesLeaseResSettleItem.getTaxRate());
           // 签认单明细ID
           dbZxCtSuppliesLeaseResSettleItem.setSignedOrderItemID(zxCtSuppliesLeaseResSettleItem.getSignedOrderItemID());
           // 签认单编号
           dbZxCtSuppliesLeaseResSettleItem.setSignedNo(zxCtSuppliesLeaseResSettleItem.getSignedNo());
           // 起租日期
           dbZxCtSuppliesLeaseResSettleItem.setStartDate(zxCtSuppliesLeaseResSettleItem.getStartDate());
           // 结算期次
           dbZxCtSuppliesLeaseResSettleItem.setPeriod(zxCtSuppliesLeaseResSettleItem.getPeriod());
           // 结算单编号
           dbZxCtSuppliesLeaseResSettleItem.setBillNo(zxCtSuppliesLeaseResSettleItem.getBillNo());
           // 计量单位
           dbZxCtSuppliesLeaseResSettleItem.setUnit(zxCtSuppliesLeaseResSettleItem.getUnit());
           // 合同租期
           dbZxCtSuppliesLeaseResSettleItem.setContrTrrm(zxCtSuppliesLeaseResSettleItem.getContrTrrm());
           // 合同数量
           dbZxCtSuppliesLeaseResSettleItem.setContractQty(zxCtSuppliesLeaseResSettleItem.getContractQty());
           // 合同明细ID
           dbZxCtSuppliesLeaseResSettleItem.setContractItemID(zxCtSuppliesLeaseResSettleItem.getContractItemID());
           // 合同金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setContractAmt(zxCtSuppliesLeaseResSettleItem.getContractAmt());
           // 合同ID
           dbZxCtSuppliesLeaseResSettleItem.setContractID(zxCtSuppliesLeaseResSettleItem.getContractID());
           // 规格型号
           dbZxCtSuppliesLeaseResSettleItem.setSpec(zxCtSuppliesLeaseResSettleItem.getSpec());
           // 单价(元)
           dbZxCtSuppliesLeaseResSettleItem.setContractPrice(zxCtSuppliesLeaseResSettleItem.getContractPrice());
           // 变更后租期
           dbZxCtSuppliesLeaseResSettleItem.setAlterTrrm(zxCtSuppliesLeaseResSettleItem.getAlterTrrm());
           // 变更后数量
           dbZxCtSuppliesLeaseResSettleItem.setChangedQty(zxCtSuppliesLeaseResSettleItem.getChangedQty());
           // 变更后金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setChangedAmt(zxCtSuppliesLeaseResSettleItem.getChangedAmt());
           // 变更后单价(元)
           dbZxCtSuppliesLeaseResSettleItem.setAlterPrice(zxCtSuppliesLeaseResSettleItem.getAlterPrice());
           // 本期结算税额(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtTax(zxCtSuppliesLeaseResSettleItem.getThisAmtTax());
           // 本期结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setThisQty(zxCtSuppliesLeaseResSettleItem.getThisQty());
           // 本期结算金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmt(zxCtSuppliesLeaseResSettleItem.getThisAmt());
           // 本期结算单价(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisPrice(zxCtSuppliesLeaseResSettleItem.getThisPrice());
           // 本期结算不含税金额(元)
           dbZxCtSuppliesLeaseResSettleItem.setThisAmtNoTax(zxCtSuppliesLeaseResSettleItem.getThisAmtNoTax());
           //本期结算租期
           dbZxCtSuppliesLeaseResSettleItem.setSignContrTrrm(zxCtSuppliesLeaseResSettleItem.getSignContrTrrm());
           //本期结算数量
           dbZxCtSuppliesLeaseResSettleItem.setSignQty(zxCtSuppliesLeaseResSettleItem.getSignQty());
           //本期租赁截止日期
           dbZxCtSuppliesLeaseResSettleItem.setSignRentEndDate(zxCtSuppliesLeaseResSettleItem.getSignRentEndDate());
           //本期租赁起始日期
           dbZxCtSuppliesLeaseResSettleItem.setSignRentStartDate(zxCtSuppliesLeaseResSettleItem.getSignRentStartDate());
           //本期结算数量*租期
           dbZxCtSuppliesLeaseResSettleItem.setSignThisAmt(zxCtSuppliesLeaseResSettleItem.getSignThisAmt());
           // 备注
           dbZxCtSuppliesLeaseResSettleItem.setRemarks(zxCtSuppliesLeaseResSettleItem.getRemarks());
           // 排序
           dbZxCtSuppliesLeaseResSettleItem.setSort(zxCtSuppliesLeaseResSettleItem.getSort());
           // 共通
           dbZxCtSuppliesLeaseResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseResSettleItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseResSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//计算金额
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
