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
        // 分页查询
        PageHelper.startPage(zxCtSuppliesLeasePaySettlementItem.getPage(),zxCtSuppliesLeasePaySettlementItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(zxCtSuppliesLeasePaySettlementItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeasePaySettlementItem> p = new PageInfo<>(zxCtSuppliesLeasePaySettlementItemList);

        return repEntity.okList(zxCtSuppliesLeasePaySettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemDetail(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        if (zxCtSuppliesLeasePaySettlementItem == null) {
            zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
        }
        // 获取数据
        ZxCtSuppliesLeasePaySettlementItem dbZxCtSuppliesLeasePaySettlementItem = zxCtSuppliesLeasePaySettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId());
        // 数据存在
        if (dbZxCtSuppliesLeasePaySettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesLeasePaySettlementItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 最后编辑时间
           dbZxCtSuppliesLeasePaySettlementItem.setEditTime(zxCtSuppliesLeasePaySettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesLeasePaySettlementItem.setMainID(zxCtSuppliesLeasePaySettlementItem.getMainID());
           // 支付项类型
           dbZxCtSuppliesLeasePaySettlementItem.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
           // 支付项ID
           dbZxCtSuppliesLeasePaySettlementItem.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
           // 序号
           dbZxCtSuppliesLeasePaySettlementItem.setOrderNum(zxCtSuppliesLeasePaySettlementItem.getOrderNum());
           // 所属公司排序
           dbZxCtSuppliesLeasePaySettlementItem.setComOrders(zxCtSuppliesLeasePaySettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeasePaySettlementItem.setComName(zxCtSuppliesLeasePaySettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeasePaySettlementItem.setComID(zxCtSuppliesLeasePaySettlementItem.getComID());
           // 税率(%)
           dbZxCtSuppliesLeasePaySettlementItem.setTaxRate(zxCtSuppliesLeasePaySettlementItem.getTaxRate());
           // 数量
           dbZxCtSuppliesLeasePaySettlementItem.setQty(zxCtSuppliesLeasePaySettlementItem.getQty());
           // 是否被修改
           dbZxCtSuppliesLeasePaySettlementItem.setIsFixed(zxCtSuppliesLeasePaySettlementItem.getIsFixed());
           // 上期末数量
           dbZxCtSuppliesLeasePaySettlementItem.setUpQty(zxCtSuppliesLeasePaySettlementItem.getUpQty());
           // 上期末结算金额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setUpAmt(zxCtSuppliesLeasePaySettlementItem.getUpAmt());
           // 名称
           dbZxCtSuppliesLeasePaySettlementItem.setPayName(zxCtSuppliesLeasePaySettlementItem.getPayName());
           // 结算期次
           dbZxCtSuppliesLeasePaySettlementItem.setPeriod(zxCtSuppliesLeasePaySettlementItem.getPeriod());
           // 结算单编号
           dbZxCtSuppliesLeasePaySettlementItem.setBillNo(zxCtSuppliesLeasePaySettlementItem.getBillNo());
           // 合同类型
           dbZxCtSuppliesLeasePaySettlementItem.setContrType(zxCtSuppliesLeasePaySettlementItem.getContrType());
           // 合同ID
           dbZxCtSuppliesLeasePaySettlementItem.setContractID(zxCtSuppliesLeasePaySettlementItem.getContractID());
           // 单位
           dbZxCtSuppliesLeasePaySettlementItem.setUnit(zxCtSuppliesLeasePaySettlementItem.getUnit());
           // 单价(元)
           dbZxCtSuppliesLeasePaySettlementItem.setPrice(zxCtSuppliesLeasePaySettlementItem.getPrice());
           // 编号
           dbZxCtSuppliesLeasePaySettlementItem.setPayNo(zxCtSuppliesLeasePaySettlementItem.getPayNo());
           // 本期结算税额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtTax());
           // 本期结算金额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
           // 本期结算不含税金额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtNoTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesLeasePaySettlementItem.setRemarks(zxCtSuppliesLeasePaySettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesLeasePaySettlementItem.setSort(zxCtSuppliesLeasePaySettlementItem.getSort());
           // 共通
           dbZxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeasePaySettlementItem);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeasePaySettlementItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity saveZxCtSuppliesLeasePaySettlementItemByPayId(
    		ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
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
        BigDecimal thisAmt = new BigDecimal(0);//含税结算金额
        BigDecimal thisAmtNoTax = new BigDecimal(0);//不含税结算金额
        BigDecimal thisAmtTax = new BigDecimal(0);//结算税额
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
        //先新增支付项结算主表，如果主表存在，则修改主表数据
        if(paySettlementList.size()>0) {
        	ZxCtSuppliesLeasePaySettlement dbPaySettlement = paySettlementList.get(0);
            //校验重复的支付项
        	ZxCtSuppliesLeasePaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlementItem();
            suppliesShopPaySettlement.setMainID(dbPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            suppliesShopPaySettlement.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
            if(zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(suppliesShopPaySettlement).size()>0) {
            	return repEntity.layerMessage("NO", "相同的支付项不能重复添加!");
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
            if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "奖罚")) {
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	fineAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "损耗")){
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	padTariffAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setFoodAmt(padTariffAmt);            	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "其他款项")){
            	ZxCtSuppliesLeasePaySettlementItem item = new ZxCtSuppliesLeasePaySettlementItem();
            	item.setMainID(dbZxCtSuppliesShopPaySettlement.getZxCtSuppliesLeasePaySettlementId());
            	item.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
            	List<ZxCtSuppliesLeasePaySettlementItem> itemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(item);
            	otherAmt = itemList.stream().map(ZxCtSuppliesLeasePaySettlementItem::getThisAmt).reduce(BigDecimal.ZERO,BigDecimal::add);
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "运杂费")){
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
        	//查询上一期
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
            if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "奖罚")) {
            	dbZxCtSuppliesShopPaySettlement.setFineAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "损耗")){
            	dbZxCtSuppliesShopPaySettlement.setFoodAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());            	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "其他款项")){
            	dbZxCtSuppliesShopPaySettlement.setOtherAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());                	
            	
            }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "运杂费")){
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
        	//计算值,更新主表
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
        
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//含税结算金额
        BigDecimal thisAmtNoTax = new BigDecimal(0);//不含税结算金额
        BigDecimal thisAmtTax = new BigDecimal(0);//结算税额
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
        //校验重复的支付项
    	ZxCtSuppliesLeasePaySettlementItem suppliesShopPaySettlement = new ZxCtSuppliesLeasePaySettlementItem();
        suppliesShopPaySettlement.setMainID(dbZxCtSuppliesLeasePaySettlementItem.getMainID());
        suppliesShopPaySettlement.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
        List<ZxCtSuppliesLeasePaySettlementItem> suppliesShopPaySettlementList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(suppliesShopPaySettlement);
        if(suppliesShopPaySettlementList.size()>0 && !StrUtil.equals(suppliesShopPaySettlementList.get(0).getZxCtSuppliesLeasePaySettlementItemId(), zxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId())) {
        	return repEntity.layerMessage("NO", "相同的支付项不能重复添加!");
        }       
        if (dbZxCtSuppliesLeasePaySettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeasePaySettlementItem.getZxCtSuppliesLeasePaySettlementItemId())) {
           // 最后编辑时间
           dbZxCtSuppliesLeasePaySettlementItem.setEditTime(zxCtSuppliesLeasePaySettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesLeasePaySettlementItem.setMainID(zxCtSuppliesLeasePaySettlementItem.getMainID());
           // 支付项类型
           dbZxCtSuppliesLeasePaySettlementItem.setPayType(zxCtSuppliesLeasePaySettlementItem.getPayType());
           // 支付项ID
           dbZxCtSuppliesLeasePaySettlementItem.setPayID(zxCtSuppliesLeasePaySettlementItem.getPayID());
           // 序号
           dbZxCtSuppliesLeasePaySettlementItem.setOrderNum(zxCtSuppliesLeasePaySettlementItem.getOrderNum());
           // 所属公司排序
           dbZxCtSuppliesLeasePaySettlementItem.setComOrders(zxCtSuppliesLeasePaySettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeasePaySettlementItem.setComName(zxCtSuppliesLeasePaySettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeasePaySettlementItem.setComID(zxCtSuppliesLeasePaySettlementItem.getComID());
           // 税率(%)
           dbZxCtSuppliesLeasePaySettlementItem.setTaxRate(zxCtSuppliesLeasePaySettlementItem.getTaxRate());
           // 数量
           dbZxCtSuppliesLeasePaySettlementItem.setQty(zxCtSuppliesLeasePaySettlementItem.getQty());
           // 是否被修改
           dbZxCtSuppliesLeasePaySettlementItem.setIsFixed(zxCtSuppliesLeasePaySettlementItem.getIsFixed());
           // 上期末数量
           dbZxCtSuppliesLeasePaySettlementItem.setUpQty(zxCtSuppliesLeasePaySettlementItem.getUpQty());
           // 上期末结算金额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setUpAmt(zxCtSuppliesLeasePaySettlementItem.getUpAmt());
           // 名称
           dbZxCtSuppliesLeasePaySettlementItem.setPayName(zxCtSuppliesLeasePaySettlementItem.getPayName());
           // 结算期次
           dbZxCtSuppliesLeasePaySettlementItem.setPeriod(zxCtSuppliesLeasePaySettlementItem.getPeriod());
           // 结算单编号
           dbZxCtSuppliesLeasePaySettlementItem.setBillNo(zxCtSuppliesLeasePaySettlementItem.getBillNo());
           // 合同类型
           dbZxCtSuppliesLeasePaySettlementItem.setContrType(zxCtSuppliesLeasePaySettlementItem.getContrType());
           // 合同ID
           dbZxCtSuppliesLeasePaySettlementItem.setContractID(zxCtSuppliesLeasePaySettlementItem.getContractID());
           // 单位
           dbZxCtSuppliesLeasePaySettlementItem.setUnit(zxCtSuppliesLeasePaySettlementItem.getUnit());
           // 单价(元)
           dbZxCtSuppliesLeasePaySettlementItem.setPrice(zxCtSuppliesLeasePaySettlementItem.getPrice());
           // 编号
           dbZxCtSuppliesLeasePaySettlementItem.setPayNo(zxCtSuppliesLeasePaySettlementItem.getPayNo());
           // 本期结算税额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtTax());
           // 本期结算金额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmt(zxCtSuppliesLeasePaySettlementItem.getThisAmt());
           // 本期结算不含税金额(元)
           dbZxCtSuppliesLeasePaySettlementItem.setThisAmtNoTax(zxCtSuppliesLeasePaySettlementItem.getThisAmtNoTax());
           // 备注
           dbZxCtSuppliesLeasePaySettlementItem.setRemarks(zxCtSuppliesLeasePaySettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesLeasePaySettlementItem.setSort(zxCtSuppliesLeasePaySettlementItem.getSort());
           // 共通
           dbZxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeasePaySettlementItem);
        }
        // 失败
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
                if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "奖罚")) {
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "奖罚")) {
                			fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setFineAmt(fineAmt);
                }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "损耗")){
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "损耗")) {
                			padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setFoodAmt(padTariffAmt);            	
                	
                }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "其他款项")){
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "其他款项")) {
                			otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setOtherAmt(otherAmt);                	
                	
                }else if(StrUtil.equals(zxCtSuppliesLeasePaySettlementItem.getPayType(), "运杂费")){
                	for(ZxCtSuppliesLeasePaySettlementItem item : itemList) {
                		if(StrUtil.equals(item.getPayType(), "运杂费")) {
                			transportAmt = CalcUtils.calcAdd(transportAmt, item.getThisAmt());
                		}
                	}
                	dbZxCtSuppliesShopPaySettlement.setInOutAmt(transportAmt);                      	
            }
                dbZxCtSuppliesShopPaySettlement.setModifyUserInfo(userKey, realName);
                zxCtSuppliesLeasePaySettlementMapper.updateByPrimaryKeySelective(dbZxCtSuppliesShopPaySettlement);
                
            	//计算值,更新主表
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
        BigDecimal taxRate = new BigDecimal(1);//初始税率
        BigDecimal thisAmt = new BigDecimal(0);//含税结算金额
        BigDecimal thisAmtNoTax = new BigDecimal(0);//不含税结算金额
        BigDecimal thisAmtTax = new BigDecimal(0);//结算税额
        int flag = 0;
        if (zxCtSuppliesLeasePaySettlementItemList != null && zxCtSuppliesLeasePaySettlementItemList.size() > 0) {
           ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem = new ZxCtSuppliesLeasePaySettlementItem();
           zxCtSuppliesLeasePaySettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeasePaySettlementItemMapper.batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(zxCtSuppliesLeasePaySettlementItemList, zxCtSuppliesLeasePaySettlementItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//重新计算金额
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
        			if(StrUtil.equals(item.getPayType(), "奖罚")) {
        				fineAmt = CalcUtils.calcAdd(fineAmt, item.getThisAmt());	
        			}else if(StrUtil.equals(item.getPayType(), "损耗")) {
        				padTariffAmt = CalcUtils.calcAdd(padTariffAmt, item.getThisAmt());
        			}else if(StrUtil.equals(item.getPayType(), "其他款项")) {
        				otherAmt = CalcUtils.calcAdd(otherAmt, item.getThisAmt());
        			}else if(StrUtil.equals(item.getPayType(), "运杂费")) {
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
        	//计算值,更新主表
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
        // 分页查询
        PageHelper.startPage(zxCtSuppliesLeasePaySettlementItem.getPage(),zxCtSuppliesLeasePaySettlementItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList = zxCtSuppliesLeasePaySettlementItemMapper.selectByZxCtSuppliesLeasePaySettlementItemList(zxCtSuppliesLeasePaySettlementItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeasePaySettlementItem> p = new PageInfo<>(zxCtSuppliesLeasePaySettlementItemList);

        return repEntity.okList(zxCtSuppliesLeasePaySettlementItemList, p.getTotal());
	}
}
