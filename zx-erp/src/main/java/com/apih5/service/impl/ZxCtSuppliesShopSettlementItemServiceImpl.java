package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesMarginRatioMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesMarginRatio;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.service.ZxCtSuppliesShopSettlementItemService;
import com.apih5.utils.DigitalConversionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesShopSettlementItemService")
public class ZxCtSuppliesShopSettlementItemServiceImpl implements ZxCtSuppliesShopSettlementItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementItemMapper zxCtSuppliesShopSettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesMarginRatioMapper zxCtSuppliesMarginRatioMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListMapper zxCtSuppliesShopSettlementListMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementItemListByCondition(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        if (zxCtSuppliesShopSettlementItem == null) {
            zxCtSuppliesShopSettlementItem = new ZxCtSuppliesShopSettlementItem();
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesShopSettlementItem.getPage(),zxCtSuppliesShopSettlementItem.getLimit());
        // ????????????
        List<ZxCtSuppliesShopSettlementItem> zxCtSuppliesShopSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(zxCtSuppliesShopSettlementItem);
        // ??????????????????
        PageInfo<ZxCtSuppliesShopSettlementItem> p = new PageInfo<>(zxCtSuppliesShopSettlementItemList);

        return repEntity.okList(zxCtSuppliesShopSettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementItemDetail(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        if (zxCtSuppliesShopSettlementItem == null) {
            zxCtSuppliesShopSettlementItem = new ZxCtSuppliesShopSettlementItem();
        }
        // ????????????
        ZxCtSuppliesShopSettlementItem dbZxCtSuppliesShopSettlementItem = zxCtSuppliesShopSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementItemId());
        // ????????????
        if (dbZxCtSuppliesShopSettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesShopSettlementItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopSettlementItem(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopSettlementItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopSettlementItem(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopSettlementItem dbZxCtSuppliesShopSettlementItem = zxCtSuppliesShopSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementItemId());
        if (dbZxCtSuppliesShopSettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementItemId())) {
           // ??????????????????
           dbZxCtSuppliesShopSettlementItem.setEditTime(zxCtSuppliesShopSettlementItem.getEditTime());
           // ??????ID
           dbZxCtSuppliesShopSettlementItem.setMainID(zxCtSuppliesShopSettlementItem.getMainID());
           // ??????ID
           dbZxCtSuppliesShopSettlementItem.setOrgID(zxCtSuppliesShopSettlementItem.getOrgID());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
           // ?????????ID
           dbZxCtSuppliesShopSettlementItem.setStatisticsID(zxCtSuppliesShopSettlementItem.getStatisticsID());
           // ?????????
           dbZxCtSuppliesShopSettlementItem.setStatisticsName(zxCtSuppliesShopSettlementItem.getStatisticsName());
           // ??????????????????
           dbZxCtSuppliesShopSettlementItem.setComOrders(zxCtSuppliesShopSettlementItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesShopSettlementItem.setComName(zxCtSuppliesShopSettlementItem.getComName());
           // ????????????ID
           dbZxCtSuppliesShopSettlementItem.setComID(zxCtSuppliesShopSettlementItem.getComID());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setUpAmt(zxCtSuppliesShopSettlementItem.getUpAmt());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setPeriod(zxCtSuppliesShopSettlementItem.getPeriod());
           // ??????(???)
           dbZxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getTotalAmt());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setBillNo(zxCtSuppliesShopSettlementItem.getBillNo());
           // ??????ID
           dbZxCtSuppliesShopSettlementItem.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setRate(zxCtSuppliesShopSettlementItem.getRate());
           // ??????(???)
           dbZxCtSuppliesShopSettlementItem.setThisAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setRemarks(zxCtSuppliesShopSettlementItem.getRemarks());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setSort(zxCtSuppliesShopSettlementItem.getSort());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopSettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopSettlementItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopSettlementItem(List<ZxCtSuppliesShopSettlementItem> zxCtSuppliesShopSettlementItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesShopSettlementItemList != null && zxCtSuppliesShopSettlementItemList.size() > 0) {
           ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem = new ZxCtSuppliesShopSettlementItem();
           zxCtSuppliesShopSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementItemMapper.batchDeleteUpdateZxCtSuppliesShopSettlementItem(zxCtSuppliesShopSettlementItemList, zxCtSuppliesShopSettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopSettlementItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementItemListByConID(
    		ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	BigDecimal thisAmt = new BigDecimal(0);
    	BigDecimal thisAmtTax = new BigDecimal(0);
    	BigDecimal thisEquipAmt = new BigDecimal(0);
    	BigDecimal thisAmtNoTax = new BigDecimal(0);
    	BigDecimal totalAmt = new BigDecimal(0);
    	BigDecimal totalAmtTax = new BigDecimal(0);
    	BigDecimal totalAmtNoTax = new BigDecimal(0);
    	BigDecimal thisDeduBond = new BigDecimal(0);//?????????????????????
    	BigDecimal totalDeduBond = new BigDecimal(0);//?????????????????????
    	BigDecimal totalReturnBond = new BigDecimal(0);//?????????????????????
    	
    	BigDecimal upTotalAmt = new BigDecimal(0);//??????
    	BigDecimal upTotalAmtTax = new BigDecimal(0);//??????
    	BigDecimal upTotalAmtNoTax = new BigDecimal(0);//?????????????????????
    	BigDecimal upTotalDeduBond = new BigDecimal(0);//?????????????????????
    	BigDecimal upTotalReturnBond = new BigDecimal(0);//?????????????????????
    	BigDecimal upLeaseAmt = new BigDecimal(0);//?????????????????????
    	BigDecimal shouldPayAmt = new BigDecimal(0);//???????????????
    	String billNo = "";
        // ????????????
    	ZxCtSuppliesShopSettlementList settlement = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementId());
        thisEquipAmt = settlement.getResThisAmt();
			if(settlement.getThisAmt() != null) {
				thisAmt = settlement.getThisAmt();//??????????????????(???)
				thisAmtTax = settlement.getThisAmtTax();//??????????????????(???)
				thisAmtNoTax = settlement.getThisAmtNoTax();//???????????????????????????(???)
			}
			DecimalFormat decimalFormat = new DecimalFormat("00");	
			ZxCtSuppliesShopSettlementList shopSettlement = new ZxCtSuppliesShopSettlementList();
	        int num = Integer.parseInt(settlement.getInitSerialNumber());
//	        shopSettlement.setInitSerialNumber(num-1+"");
	        shopSettlement.setInitSerialNumber(decimalFormat.format(num-1));
//			shopSettlement.setBillNo(billNo);
			shopSettlement.setContractID(settlement.getContractID());
			List<ZxCtSuppliesShopSettlementList> shopSettlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(shopSettlement);
			if(shopSettlementList.size()>0) {
				billNo = shopSettlementList.get(0).getBillNo();
			}
			ZxCtSuppliesShopSettlementItem dbSettlementItem = new ZxCtSuppliesShopSettlementItem();
        	dbSettlementItem.setContractID(settlement.getContractID());
        	dbSettlementItem.setBillNo(billNo);
        	ZxCtSuppliesShopSettlementItem settlementItem = new ZxCtSuppliesShopSettlementItem();
        settlementItem.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
        settlementItem.setMainID(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementId());
    	List<ZxCtSuppliesShopSettlementItem> zxCtSuppliesLeaseSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
    	if(zxCtSuppliesLeaseSettlementItemList.size() == 0) {
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100100");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("????????????????????????????????????");
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100100");
    		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
    		List<ZxCtSuppliesShopSettlementItem> dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			totalAmt = CalcUtils.calcAdd(thisAmt, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			upTotalAmt = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmt);
    		}else {
    			totalAmt = thisAmt;	
    		}
    		settlement.setTotalAmt(totalAmt);//??????????????????(???)
    		zxCtSuppliesShopSettlementItem.setContractID(settlement.getContractID());
    		zxCtSuppliesShopSettlementItem.setBillNo(settlement.getBillNo());
    		zxCtSuppliesShopSettlementItem.setThisAmt(thisAmt.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setTotalAmt(totalAmt.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setMainID(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementId());
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100110");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("???????????????????????????????????????");
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			totalAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			upTotalAmtTax = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmtTax);
    		}else {
    			totalAmtNoTax = thisAmtNoTax;	
    		}
    		zxCtSuppliesShopSettlementItem.setThisAmt(thisAmtNoTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setTotalAmt(totalAmtNoTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100110");
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100120");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("??????????????????????????????");
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			totalAmtTax = CalcUtils.calcAdd(thisAmtTax, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			upTotalAmtNoTax = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmtNoTax);
    		}else {
    			totalAmtTax = thisAmtTax;	
    		}
    		zxCtSuppliesShopSettlementItem.setThisAmt(thisAmtTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100120");
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100200");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("????????????????????????????????????");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
    		zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmt));
    		zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmt);
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100200");
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100210");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("???????????????????????????????????????");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtNoTax));
    		zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100210");
    		zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmtTax);
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100220");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("??????????????????????????????");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtTax));
    		zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtTax));
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100220");
    		zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmtNoTax);
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        	//?????????????????????
    		ZxCtSuppliesMarginRatio ratio = new ZxCtSuppliesMarginRatio();
    		ratio.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
        	List<ZxCtSuppliesMarginRatio> ratioList = zxCtSuppliesMarginRatioMapper.selectByZxCtSuppliesMarginRatioList(ratio);
        	if(ratioList.size()>0) {    	
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {
        			thisDeduBond = CalcUtils.calcAdd(thisDeduBond, CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)));
        		}
        	zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        	zxCtSuppliesShopSettlementItem.setStatisticsNo("100300");
        	zxCtSuppliesShopSettlementItem.setStatisticsName("???????????????????????????");
        	zxCtSuppliesShopSettlementItem.setThisAmt(thisDeduBond.stripTrailingZeros().toPlainString());
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			totalDeduBond = CalcUtils.calcAdd(thisDeduBond, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			zxCtSuppliesShopSettlementItem.setTotalAmt(totalDeduBond.stripTrailingZeros().toPlainString());
    			upTotalDeduBond = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesShopSettlementItem.setUpAmt(upTotalDeduBond);
    		}else {
    			zxCtSuppliesShopSettlementItem.setTotalAmt(thisDeduBond.stripTrailingZeros().toPlainString());
    		}      		
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100300");
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {    			
        			//????????????
        			zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        			zxCtSuppliesShopSettlementItem.setStatisticsNo(marginRatio.getZxCtSuppliesMarginRatioId());
        			zxCtSuppliesShopSettlementItem.setStatisticsName(marginRatio.getStatisticsName());
        			zxCtSuppliesShopSettlementItem.setThisAmt(CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)).stripTrailingZeros().toPlainString());
               		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
               		dbSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());                   		
            		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
            		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
            			zxCtSuppliesShopSettlementItem.setTotalAmt(CalcUtils.calcAdd(new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()), new BigDecimal(zxCtSuppliesShopSettlementItem.getThisAmt())).stripTrailingZeros().toPlainString());
            			zxCtSuppliesShopSettlementItem.setUpAmt(new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
            		}else {
            			zxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
            		}
            		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
            		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        		}
        	}    		
        	zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        	zxCtSuppliesShopSettlementItem.setStatisticsNo("100500");
        	zxCtSuppliesShopSettlementItem.setStatisticsName("?????????????????????");
        	zxCtSuppliesShopSettlementItem.setThisAmt("0");
        	zxCtSuppliesShopSettlementItem.setStatisticsType("100500");
        	if(ratioList.size()>0) {
        	//???????????????????????????
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
       		dbSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());          		
    		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			zxCtSuppliesShopSettlementItem.setTotalAmt(dbSettlementItemList.get(0).getTotalAmt());
    			upTotalReturnBond = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesShopSettlementItem.setUpAmt(upTotalReturnBond);
    		}else {
    			zxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
    		}
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {    			
        			//????????????
        			zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        			zxCtSuppliesShopSettlementItem.setStatisticsNo(marginRatio.getZxCtSuppliesMarginRatioId());
        			zxCtSuppliesShopSettlementItem.setStatisticsName(marginRatio.getStatisticsName());
        			zxCtSuppliesShopSettlementItem.setThisAmt("0");
                	//???????????????????????????
               		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
               		dbSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());               		
            		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
            		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
            			zxCtSuppliesShopSettlementItem.setTotalAmt(dbSettlementItemList.get(0).getTotalAmt());
            			zxCtSuppliesShopSettlementItem.setUpAmt(new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
            		}else {
            			zxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
            		}
            		zxCtSuppliesShopSettlementItem.setSort(2);
            		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
            		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        		}
        	}   
        	zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        	zxCtSuppliesShopSettlementItem.setStatisticsNo("100700");
        	zxCtSuppliesShopSettlementItem.setStatisticsName("???????????????????????????");
        	zxCtSuppliesShopSettlementItem.setStatisticsType("100700");
        	zxCtSuppliesShopSettlementItem.setThisAmt(CalcUtils.calcSubtract(thisAmt, thisDeduBond).stripTrailingZeros().toPlainString());
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
       		dbSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());
    		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			shouldPayAmt = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesShopSettlementItem.setTotalAmt(CalcUtils.calcAdd(upTotalReturnBond, CalcUtils.calcSubtract(totalAmt, totalDeduBond)).stripTrailingZeros().toPlainString());
//    			zxCtSuppliesShopSettlementItem.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, totalDeduBond), new BigDecimal(dbSettlementItemList.get(0).getTotalAmt())).stripTrailingZeros().toPlainString());
        		upLeaseAmt = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
        		zxCtSuppliesShopSettlementItem.setUpAmt(upLeaseAmt);
    		}else {
    			zxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
    		}
    		settlement.setThisPayAmt(new BigDecimal(zxCtSuppliesShopSettlementItem.getThisAmt()));//??????????????????(???)
    		settlement.setTotalPayAmt(new BigDecimal(zxCtSuppliesShopSettlementItem.getTotalAmt()));//??????????????????(???)
    		zxCtSuppliesShopSettlementItem.setSort(0);
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100800");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("???????????????????????????");
        	zxCtSuppliesShopSettlementItem.setStatisticsType("100800");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(thisAmt, thisDeduBond)));
       		dbSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem).size()>0 && shopSettlementList.size()>0) {
    			zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, totalDeduBond), shouldPayAmt)));
    			zxCtSuppliesShopSettlementItem.setUpAmt(upLeaseAmt);
    		}else {
    			zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(thisAmt, thisDeduBond)));
    		}
    		zxCtSuppliesShopSettlementItem.setSort(0);
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100800");
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
            // ????????????
            zxCtSuppliesLeaseSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
            settlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(settlement);
    	}else {
    		//?????????????????????????????????
    		for(ZxCtSuppliesShopSettlementItem settlementList : zxCtSuppliesLeaseSettlementItemList) {
    			if(StrUtil.equals(settlementList.getStatisticsNo(), "100100")) {//????????????????????????????????????
    				settlementList.setThisAmt(thisAmt.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmt = CalcUtils.calcAdd(thisAmt, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmt = thisAmt;	
    	    		}
    	    		settlement.setTotalAmt(totalAmt);//??????????????????(???)
    				settlementList.setTotalAmt(totalAmt.stripTrailingZeros().toPlainString());
//    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100110")){//???????????????????????????????????????
    				settlementList.setThisAmt(thisAmtNoTax.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmtNoTax = thisAmtNoTax;	
    	    		}
    				settlementList.setTotalAmt(totalAmtNoTax.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100120")){//??????????????????????????????
    				settlementList.setThisAmt(thisAmtTax.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(thisAmtTax, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmtTax = thisAmtTax;	
    	    		}
    	    		settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100200")){//????????????????????????????????????
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
    	    		settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmt));
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100210")){//???????????????????????????????????????
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtNoTax));
    				settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100220")){//??????????????????????????????
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtTax));
    				settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtTax));
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsType(), "100300")){//???????????????
    				if(!StrUtil.equals(settlementList.getStatisticsNo(), "100300")) {
    					ZxCtSuppliesMarginRatio marginRatio = zxCtSuppliesMarginRatioMapper.selectByPrimaryKey(settlementList.getStatisticsNo());
    					thisDeduBond = CalcUtils.calcAdd(thisDeduBond, CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)));
    					settlementList.setThisAmt(CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)).stripTrailingZeros().toPlainString());
//    					thisDeduBond = CalcUtils.calcAdd(thisDeduBond, CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)));
//    					settlementList.setThisAmt(CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)).stripTrailingZeros().toPlainString());
                		if(settlementList.getUpAmt() != null) {
                			settlementList.setTotalAmt(CalcUtils.calcAdd(settlementList.getUpAmt(), new BigDecimal(settlementList.getThisAmt())).stripTrailingZeros().toPlainString());
                		}else {
                			settlementList.setTotalAmt(settlementList.getThisAmt());
                		}
        				settlementList.setModifyUserInfo(userKey, realName);
        				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    				}
    			}else if(StrUtil.equals(settlementList.getStatisticsType(), "100500")){//???????????????
    				if(!StrUtil.equals(settlementList.getStatisticsNo(), "100500")) {
    					totalReturnBond = CalcUtils.calcAdd(totalReturnBond, new BigDecimal(settlementList.getThisAmt()));
                		if(settlementList.getUpAmt() != null) {
                			settlementList.setTotalAmt(CalcUtils.calcAdd(settlementList.getUpAmt(), new BigDecimal(settlementList.getThisAmt())).stripTrailingZeros().toPlainString());
                		}else {
                			settlementList.setTotalAmt(settlementList.getThisAmt());
                		}
        				settlementList.setModifyUserInfo(userKey, realName);
        				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    				}
    			}
    		}
    		for(ZxCtSuppliesShopSettlementItem settlementList : zxCtSuppliesLeaseSettlementItemList) {
    			if(StrUtil.equals(settlementList.getStatisticsNo(), "100500")) {//???????????????????????????
    				settlementList.setThisAmt(totalReturnBond.stripTrailingZeros().toPlainString());
    	        	//???????????????????????????
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(totalReturnBond, settlementList.getUpAmt());
    	    			settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    	    		}else {
    	    			settlementList.setTotalAmt(totalReturnBond.stripTrailingZeros().toPlainString());
    	    		}    				
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100300")) {//???????????????????????????
    				settlementList.setThisAmt(thisDeduBond.stripTrailingZeros().toPlainString());
    	        	//???????????????????????????
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(thisDeduBond, settlementList.getUpAmt());
    	    			settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    	    		}else {
    	    			settlementList.setTotalAmt(thisDeduBond.stripTrailingZeros().toPlainString());
    	    		}    				
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100700")) {//???????????????????????????
    				settlementList.setThisAmt(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond)).stripTrailingZeros().toPlainString());
    				//???????????????????????????
    	    		if(settlementList.getUpAmt() != null) {
    	    			shouldPayAmt = settlementList.getUpAmt();
    	    			settlementList.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond)), settlementList.getUpAmt()).stripTrailingZeros().toPlainString());
    	    		}else {
    	    			settlementList.setTotalAmt(totalDeduBond.stripTrailingZeros().toPlainString());
    	    		}    				
    	    		settlement.setThisPayAmt(new BigDecimal(settlementList.getThisAmt()));//??????????????????(???)
    	    		settlement.setTotalPayAmt(new BigDecimal(settlementList.getTotalAmt()));//??????????????????(???)    	    		
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100800")) {//???????????????????????????
    	        	//???????????????????????????
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond))));
    	       		dbSettlementItem.setStatisticsNo(settlementList.getStatisticsNo());
    	       		List<ZxCtSuppliesShopSettlementItem> dbSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(dbSettlementItem);
    	    		if(dbSettlementItemList.size()>0) {
    	    			settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond)), shouldPayAmt)));
    	    		}else {
    	    			settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond))));
    	    		}    				
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}
    		}
//            settlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(settlement);
    	}
        // ??????????????????
        PageInfo<ZxCtSuppliesShopSettlementItem> p = new PageInfo<>(zxCtSuppliesLeaseSettlementItemList);

        return repEntity.okList(zxCtSuppliesLeaseSettlementItemList, p.getTotal());
    }

	@Override
	public ResponseEntity updateZxCtSuppliesShopSettlementItemByConID(
			ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopSettlementItem dbZxCtSuppliesShopSettlementItem = zxCtSuppliesShopSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementItemId());
        if (dbZxCtSuppliesShopSettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementItemId())) {
           // ??????????????????
           dbZxCtSuppliesShopSettlementItem.setEditTime(zxCtSuppliesShopSettlementItem.getEditTime());
           // ??????ID
           dbZxCtSuppliesShopSettlementItem.setMainID(zxCtSuppliesShopSettlementItem.getMainID());
           // ??????ID
           dbZxCtSuppliesShopSettlementItem.setOrgID(zxCtSuppliesShopSettlementItem.getOrgID());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
           // ?????????ID
           dbZxCtSuppliesShopSettlementItem.setStatisticsID(zxCtSuppliesShopSettlementItem.getStatisticsID());
           // ?????????
           dbZxCtSuppliesShopSettlementItem.setStatisticsName(zxCtSuppliesShopSettlementItem.getStatisticsName());
           // ??????????????????
           dbZxCtSuppliesShopSettlementItem.setComOrders(zxCtSuppliesShopSettlementItem.getComOrders());
           // ??????????????????
           dbZxCtSuppliesShopSettlementItem.setComName(zxCtSuppliesShopSettlementItem.getComName());
           // ????????????ID
           dbZxCtSuppliesShopSettlementItem.setComID(zxCtSuppliesShopSettlementItem.getComID());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setUpAmt(zxCtSuppliesShopSettlementItem.getUpAmt());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setPeriod(zxCtSuppliesShopSettlementItem.getPeriod());
           // ??????(???)
           dbZxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getTotalAmt());
           // ???????????????
           dbZxCtSuppliesShopSettlementItem.setBillNo(zxCtSuppliesShopSettlementItem.getBillNo());
           // ??????ID
           dbZxCtSuppliesShopSettlementItem.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setRate(zxCtSuppliesShopSettlementItem.getRate());
           // ??????(???)
           dbZxCtSuppliesShopSettlementItem.setThisAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setRemarks(zxCtSuppliesShopSettlementItem.getRemarks());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setSort(zxCtSuppliesShopSettlementItem.getSort());
           // ??????
           dbZxCtSuppliesShopSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopSettlementItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopSettlementItem);
        }
	}
}
