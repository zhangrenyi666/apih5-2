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
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopPaySettlementItem.getPage(),zxCtSuppliesShopPaySettlementItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(zxCtSuppliesShopPaySettlementItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopPaySettlementItem> p = new PageInfo<>(zxCtSuppliesShopPaySettlementItemList);

        return repEntity.okList(zxCtSuppliesShopPaySettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemDetail(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        if (zxCtSuppliesShopPaySettlementItem == null) {
            zxCtSuppliesShopPaySettlementItem = new ZxCtSuppliesShopPaySettlementItem();
        }
        // 获取数据
        ZxCtSuppliesShopPaySettlementItem dbZxCtSuppliesShopPaySettlementItem = zxCtSuppliesShopPaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId());
        // 数据存在
        if (dbZxCtSuppliesShopPaySettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesShopPaySettlementItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 最后编辑时间
           dbZxCtSuppliesShopPaySettlementItem.setEditTime(zxCtSuppliesShopPaySettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopPaySettlementItem.setMainID(zxCtSuppliesShopPaySettlementItem.getMainID());
           // 支付项类型
           dbZxCtSuppliesShopPaySettlementItem.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
           // 支付项ID
           dbZxCtSuppliesShopPaySettlementItem.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
           // 序号
           dbZxCtSuppliesShopPaySettlementItem.setOrderNum(zxCtSuppliesShopPaySettlementItem.getOrderNum());
           // 所属公司排序
           dbZxCtSuppliesShopPaySettlementItem.setComOrders(zxCtSuppliesShopPaySettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopPaySettlementItem.setComName(zxCtSuppliesShopPaySettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopPaySettlementItem.setComID(zxCtSuppliesShopPaySettlementItem.getComID());
           // 税率
           dbZxCtSuppliesShopPaySettlementItem.setTaxRate(zxCtSuppliesShopPaySettlementItem.getTaxRate());
           // 数量
           dbZxCtSuppliesShopPaySettlementItem.setQty(zxCtSuppliesShopPaySettlementItem.getQty());
           // 是否已修改
           dbZxCtSuppliesShopPaySettlementItem.setIsFixed(zxCtSuppliesShopPaySettlementItem.getIsFixed());
           // 上期末结算金额(元)
           dbZxCtSuppliesShopPaySettlementItem.setUpAmt(zxCtSuppliesShopPaySettlementItem.getUpAmt());
           // 名称
           dbZxCtSuppliesShopPaySettlementItem.setPayName(zxCtSuppliesShopPaySettlementItem.getPayName());
           // 结算期次
           dbZxCtSuppliesShopPaySettlementItem.setPeriod(zxCtSuppliesShopPaySettlementItem.getPeriod());
           // 合同ID
           dbZxCtSuppliesShopPaySettlementItem.setContractID(zxCtSuppliesShopPaySettlementItem.getContractID());
           // 单位
           dbZxCtSuppliesShopPaySettlementItem.setUnit(zxCtSuppliesShopPaySettlementItem.getUnit());
           // 单价
           dbZxCtSuppliesShopPaySettlementItem.setPrice(zxCtSuppliesShopPaySettlementItem.getPrice());
           // 编号
           dbZxCtSuppliesShopPaySettlementItem.setPayNo(zxCtSuppliesShopPaySettlementItem.getPayNo());
           // 本期结算税额
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtTax(zxCtSuppliesShopPaySettlementItem.getThisAmtTax());
           // 本期结算金额(元)
           dbZxCtSuppliesShopPaySettlementItem.setThisAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
           // 本期结算不含税金额
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtNoTax(zxCtSuppliesShopPaySettlementItem.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesShopPaySettlementItem.setRemarks(zxCtSuppliesShopPaySettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesShopPaySettlementItem.setSort(zxCtSuppliesShopPaySettlementItem.getSort());
           // 共通
           dbZxCtSuppliesShopPaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopPaySettlementItem);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopPaySettlementItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity saveZxCtSuppliesShopPaySettlementItemByPayId(
    		ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal padTariffAmt = null;//本期垫资费(元)
        BigDecimal fineAmt = null;//本期奖罚金(元)
        BigDecimal otherAmt = null;//本期其他款项(元)
        BigDecimal transportAmt = null;//本期运杂费(元)
        BigDecimal totalAmt = new BigDecimal(0);//开累结算金额
        BigDecimal payThisAmt = new BigDecimal(0);//开累结算金额
        BigDecimal payTotalAmt = new BigDecimal(0);//开累结算金额
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//含税结算金额
        BigDecimal thisAmtNoTax = new BigDecimal(0);//不含税结算金额
        BigDecimal thisAmtTax = new BigDecimal(0);//结算税额
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
        //先新增支付项结算主表，如果主表存在，则修改主表数据
        if(paySettlementList.size()>0) {
        	ZxCtSuppliesShopPaySettlement dbZxCtSuppliesShopPaySettlement = paySettlementList.get(0);
            //校验重复的支付项
        	ZxCtSuppliesShopPaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlementItem();
            suppliesShopPaySettlement.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            suppliesShopPaySettlement.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
            if(zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(suppliesShopPaySettlement).size()>0) {
            	return repEntity.layerMessage("NO", "相同的支付项不能重复添加!");
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
            if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "奖罚金")) {
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	fineAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "垫资费")){
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	padTariffAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(padTariffAmt);            	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "其他款项")){
            	ZxCtSuppliesShopPaySettlementItem item = new ZxCtSuppliesShopPaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesShopPaySettlementId());
            	item.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
            	List<ZxCtSuppliesShopPaySettlementItem> itemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(item);
            	otherAmt = itemList.stream().map(ZxCtSuppliesShopPaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "运杂费")){
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
            if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "奖罚金")) {
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "垫资费")){
            	dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());            	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "其他款项")){
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());                	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "运杂费")){
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
        	//计算值,更新主表
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
        BigDecimal payThisAmt = new BigDecimal(0);//开累结算金额
        BigDecimal payTotalAmt = new BigDecimal(0);//开累结算金额
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//含税结算金额
        BigDecimal thisAmtNoTax = new BigDecimal(0);//不含税结算金额
        BigDecimal thisAmtTax = new BigDecimal(0);//结算税额
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
            //校验重复的支付项
        	ZxCtSuppliesShopPaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesShopPaySettlementItem();
            suppliesShopPaySettlement.setMainID(dbZxCtSuppliesShopPaySettlementItem.getMainID());
            suppliesShopPaySettlement.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
            List<ZxCtSuppliesShopPaySettlementItem> suppliesShopPaySettlementList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(suppliesShopPaySettlement);
            if(suppliesShopPaySettlementList.size()>0 && !StrUtil.equals(suppliesShopPaySettlementList.get(0).getZxCtSuppliesShopPaySettlementItemId(), zxCtSuppliesShopPaySettlementItem.getZxCtSuppliesShopPaySettlementItemId()) ) {
            	return repEntity.layerMessage("NO", "相同的支付项不能重复添加!");
            }
           // 最后编辑时间
           dbZxCtSuppliesShopPaySettlementItem.setEditTime(zxCtSuppliesShopPaySettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopPaySettlementItem.setMainID(zxCtSuppliesShopPaySettlementItem.getMainID());
           // 支付项类型
           dbZxCtSuppliesShopPaySettlementItem.setPayType(zxCtSuppliesShopPaySettlementItem.getPayType());
           // 支付项ID
           dbZxCtSuppliesShopPaySettlementItem.setPayID(zxCtSuppliesShopPaySettlementItem.getPayID());
           // 序号
           dbZxCtSuppliesShopPaySettlementItem.setOrderNum(zxCtSuppliesShopPaySettlementItem.getOrderNum());
           // 所属公司排序
           dbZxCtSuppliesShopPaySettlementItem.setComOrders(zxCtSuppliesShopPaySettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopPaySettlementItem.setComName(zxCtSuppliesShopPaySettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopPaySettlementItem.setComID(zxCtSuppliesShopPaySettlementItem.getComID());
           // 税率
           dbZxCtSuppliesShopPaySettlementItem.setTaxRate(zxCtSuppliesShopPaySettlementItem.getTaxRate());
           // 数量
           dbZxCtSuppliesShopPaySettlementItem.setQty(zxCtSuppliesShopPaySettlementItem.getQty());
           // 是否已修改
           dbZxCtSuppliesShopPaySettlementItem.setIsFixed(zxCtSuppliesShopPaySettlementItem.getIsFixed());
           // 上期末结算金额(元)
           dbZxCtSuppliesShopPaySettlementItem.setUpAmt(zxCtSuppliesShopPaySettlementItem.getUpAmt());
           // 名称
           dbZxCtSuppliesShopPaySettlementItem.setPayName(zxCtSuppliesShopPaySettlementItem.getPayName());
           // 结算期次
           dbZxCtSuppliesShopPaySettlementItem.setPeriod(zxCtSuppliesShopPaySettlementItem.getPeriod());
           // 合同ID
           dbZxCtSuppliesShopPaySettlementItem.setContractID(zxCtSuppliesShopPaySettlementItem.getContractID());
           // 单位
           dbZxCtSuppliesShopPaySettlementItem.setUnit(zxCtSuppliesShopPaySettlementItem.getUnit());
           // 单价
           dbZxCtSuppliesShopPaySettlementItem.setPrice(zxCtSuppliesShopPaySettlementItem.getPrice());
           // 编号
           dbZxCtSuppliesShopPaySettlementItem.setPayNo(zxCtSuppliesShopPaySettlementItem.getPayNo());
           // 本期结算税额
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtTax(zxCtSuppliesShopPaySettlementItem.getThisAmtTax());
           // 本期结算金额(元)
           dbZxCtSuppliesShopPaySettlementItem.setThisAmt(zxCtSuppliesShopPaySettlementItem.getThisAmt());
           // 本期结算不含税金额
           dbZxCtSuppliesShopPaySettlementItem.setThisAmtNoTax(zxCtSuppliesShopPaySettlementItem.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesShopPaySettlementItem.setRemarks(zxCtSuppliesShopPaySettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesShopPaySettlementItem.setSort(zxCtSuppliesShopPaySettlementItem.getSort());
           // 共通
           dbZxCtSuppliesShopPaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopPaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopPaySettlementItem);
        }
        // 失败
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
            if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "奖罚金")) {
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "奖罚金")) {
            			fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "垫资费")){
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "垫资费")) {
            			padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(padTariffAmt);            	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "其他款项")){
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "其他款项")) {
            			otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
            	
            }else if(StrUtil.equals(zxCtSuppliesShopPaySettlementItem.getPayType(), "运杂费")){
            	for(ZxCtSuppliesShopPaySettlementItem item : itemList) {
            		if(StrUtil.equals(item.getPayType(), "运杂费")) {
            			transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
            		}
            	}
            	dbZxCtSuppliesShopPaySettlement.setTransportAmt(transportAmt);                      	
        }
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopPaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
        	//计算值,更新主表
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
        
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//含税结算金额
        BigDecimal thisAmtNoTax = new BigDecimal(0);//不含税结算金额
        BigDecimal thisAmtTax = new BigDecimal(0);//结算税额
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//重新计算金额
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
            	if(StrUtil.equals(item.getPayType(), "奖罚金")) {
        			fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());	
            	}else if(StrUtil.equals(item.getPayType(), "垫资费")) {
            		padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
            	}else if(StrUtil.equals(item.getPayType(), "其他款项")) {
            		otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
            	}else if(StrUtil.equals(item.getPayType(), "运杂费")) {
            		transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
            	}
            }
            dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            dbZxCtSuppliesShopPaySettlement.setPadTariffAmt(padTariffAmt);
            dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);
            dbZxCtSuppliesShopPaySettlement.setTransportAmt(transportAmt);
            dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopPaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
        	//计算值,更新主表
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
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopPaySettlementItem.getPage(),zxCtSuppliesShopPaySettlementItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList = zxCtSuppliesShopPaySettlementItemMapper.selectByZxCtSuppliesShopPaySettlementItemList(zxCtSuppliesShopPaySettlementItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopPaySettlementItem> p = new PageInfo<>(zxCtSuppliesShopPaySettlementItemList);

        return repEntity.okList(zxCtSuppliesShopPaySettlementItemList, p.getTotal());
	}
}
