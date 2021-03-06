package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtEqCoContractAmtRateMapper;
import com.apih5.mybatis.dao.ZxSaEquipPaySettleMapper;
import com.apih5.mybatis.dao.ZxSaEquipResSettleItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipResSettleMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxCtEqCoContractAmtRate;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;
import com.apih5.mybatis.pojo.ZxSaEquipResSettleItem;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;
import com.apih5.service.ZxSaEquipResSettleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

@Service("zxSaEquipResSettleItemService")
public class ZxSaEquipResSettleItemServiceImpl implements ZxSaEquipResSettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaEquipResSettleItemMapper zxSaEquipResSettleItemMapper;
    
    @Autowired(required = true)
    private ZxSaEquipResSettleMapper zxSaEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaEquipSettleAuditMapper zxSaEquipSettleAuditMapper;
    
    @Autowired(required = true)
    private ZxSaEquipSettleAuditItemMapper zxSaEquipSettleAuditItemMapper;
    
    @Autowired(required = true)
    private ZxSaEquipPaySettleMapper zxSaEquipPaySettleMapper;
    
    @Autowired(required = true)
    private ZxCtEqCoContractAmtRateMapper zxCtEqCoContractAmtRateMapper;

    @Override
    public ResponseEntity getZxSaEquipResSettleItemListByCondition(ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        if (zxSaEquipResSettleItem == null) {
            zxSaEquipResSettleItem = new ZxSaEquipResSettleItem();
        }
       if(StrUtil.isEmpty(zxSaEquipResSettleItem.getZxSaEquipResSettleId())) {
    	   return repEntity.layerMessage("no", "??????id???zxSaEquipResSettleId????????????"); 
       }
        
        // ????????????
        PageHelper.startPage(zxSaEquipResSettleItem.getPage(),zxSaEquipResSettleItem.getLimit());
        // ????????????
        List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(zxSaEquipResSettleItem);
        // ??????????????????
        PageInfo<ZxSaEquipResSettleItem> p = new PageInfo<>(zxSaEquipResSettleItemList);

        return repEntity.okList(zxSaEquipResSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaEquipResSettleItemDetail(ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        if (zxSaEquipResSettleItem == null) {
            zxSaEquipResSettleItem = new ZxSaEquipResSettleItem();
        }
        // ????????????
        ZxSaEquipResSettleItem dbZxSaEquipResSettleItem = zxSaEquipResSettleItemMapper.selectByPrimaryKey(zxSaEquipResSettleItem.getZxSaEquipResSettleItemId());
        // ????????????
        if (dbZxSaEquipResSettleItem != null) {
            return repEntity.ok(dbZxSaEquipResSettleItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSaEquipResSettleItem(ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaEquipResSettleItem.setZxSaEquipResSettleItemId(UuidUtil.generate());
        zxSaEquipResSettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaEquipResSettleItemMapper.insert(zxSaEquipResSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaEquipResSettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaEquipResSettleItem(ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaEquipResSettleItem dbZxSaEquipResSettleItem = zxSaEquipResSettleItemMapper.selectByPrimaryKey(zxSaEquipResSettleItem.getZxSaEquipResSettleItemId());
        if (dbZxSaEquipResSettleItem != null && StrUtil.isNotEmpty(dbZxSaEquipResSettleItem.getZxSaEquipResSettleItemId())) {
        	//??????thisQty????????????????????????????????????????????????
        	//???????????????????????????????????????????????????????????????????????????????????????
        	if(dbZxSaEquipResSettleItem.getChangedQty() != null) {
        		//???????????????????????????
        		BigDecimal thisQty = new BigDecimal("0");
              	ZxSaEquipResSettleItem alterItemResoure = new ZxSaEquipResSettleItem();
              	alterItemResoure.setContractID(dbZxSaEquipResSettleItem.getContractID());
              	alterItemResoure.setContractItemID(dbZxSaEquipResSettleItem.getContractItemID());
              	List<ZxSaEquipResSettleItem> alterItemResoureList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(alterItemResoure);
              	if(alterItemResoureList != null && alterItemResoureList.size() >0) {
              		for (ZxSaEquipResSettleItem itemResoure : alterItemResoureList) {
              			if(itemResoure.getThisQty() != null) {
              				thisQty = CalcUtils.calcAdd(thisQty, itemResoure.getThisQty());
              			}
              		}
              	}
              	thisQty = CalcUtils.calcSubtract(thisQty, dbZxSaEquipResSettleItem.getThisQty());
       
              	thisQty = CalcUtils.calcAdd(thisQty, zxSaEquipResSettleItem.getThisQty());
      			if(thisQty.compareTo(dbZxSaEquipResSettleItem.getChangedQty()) >0) {
      				return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
      			}
        	}else if(dbZxSaEquipResSettleItem.getContractQty() != null){
        		//???????????????????????????
        		BigDecimal thisQty = new BigDecimal("0");
              	ZxSaEquipResSettleItem alterItemResoure = new ZxSaEquipResSettleItem();
              	alterItemResoure.setContractID(dbZxSaEquipResSettleItem.getContractID());
              	alterItemResoure.setContractItemID(dbZxSaEquipResSettleItem.getContractItemID());
              	List<ZxSaEquipResSettleItem> alterItemResoureList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(alterItemResoure);
              	if(alterItemResoureList != null && alterItemResoureList.size() >0) {
              		for (ZxSaEquipResSettleItem itemResoure : alterItemResoureList) {
              			if(itemResoure.getThisQty() != null) {
              				thisQty = CalcUtils.calcAdd(thisQty, itemResoure.getThisQty());
              			}
              		}
              	}
              	thisQty = CalcUtils.calcSubtract(thisQty, dbZxSaEquipResSettleItem.getThisQty());
       
              	thisQty = CalcUtils.calcAdd(thisQty, zxSaEquipResSettleItem.getThisQty());
      			if(thisQty.compareTo(dbZxSaEquipResSettleItem.getContractQty()) >0) {
      				return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
      			}
        	}else {
        		return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
        	}
        	
        	BigDecimal upQty_old = dbZxSaEquipResSettleItem.getUpQty();
    		BigDecimal upAmt_old = dbZxSaEquipResSettleItem.getUpAmt();
        	
        	
        	
        	//????????????(L)
        	dbZxSaEquipResSettleItem.setUseOil(zxSaEquipResSettleItem.getUseOil());
        	//??????????????????(??????)
        	dbZxSaEquipResSettleItem.setRunHour(zxSaEquipResSettleItem.getRunHour());
        	//??????
        	dbZxSaEquipResSettleItem.setRemark(zxSaEquipResSettleItem.getRemark());

        	// ??????????????????
        	dbZxSaEquipResSettleItem.setThisQty(zxSaEquipResSettleItem.getThisQty());
        	// ????????????????????????(???) = ??????????????????*??????(???)
        	dbZxSaEquipResSettleItem.setThisAmt(CalcUtils.calcMultiply(zxSaEquipResSettleItem.getThisQty(), dbZxSaEquipResSettleItem.getContractPrice()));
        	// ???????????????????????????(???) = ????????????/(1+??????/100)
        	if(StrUtil.isNotEmpty(dbZxSaEquipResSettleItem.getTaxRate())) {
        		dbZxSaEquipResSettleItem.setThisAmtNoTax(CalcUtils.calcDivide(dbZxSaEquipResSettleItem.getThisAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(dbZxSaEquipResSettleItem.getTaxRate()), new BigDecimal("100"),6))));
            }
        	// ??????????????????(???) = ??????????????????(???) - ???????????????????????????(???)
        	dbZxSaEquipResSettleItem.setThisAmtTax(CalcUtils.calcSubtract(dbZxSaEquipResSettleItem.getThisAmt(), dbZxSaEquipResSettleItem.getThisAmtNoTax()));
        	
        	//???????????????????????????????????????????????????
        	ZxSaEquipResSettleItem resItem = new ZxSaEquipResSettleItem();
        	resItem.setContractID(dbZxSaEquipResSettleItem.getContractID());
        	resItem.setContractItemID(dbZxSaEquipResSettleItem.getContractItemID());
        	resItem.setZxSaEquipResSettleItemIdNotFlag(zxSaEquipResSettleItem.getZxSaEquipResSettleItemId());
        	List<ZxSaEquipResSettleItem> resItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resItem);
        	if(resItemList != null && resItemList.size() >0) {
        		//?????????
        		//??????????????????????????????upQty
        		//????????????????????????????????????(???)upAmt
        		//??????????????????????????????totalQty = ThisQty
        		//??????????????????????????????totalAmt = ThisAmt
        		BigDecimal upQty = new BigDecimal("0");
        		BigDecimal upAmt = new BigDecimal("0");
        		BigDecimal totalQty = new BigDecimal("0");
        		BigDecimal totalAmt = new BigDecimal("0");
        		for (ZxSaEquipResSettleItem resSettleItem : resItemList) {
        			upQty = CalcUtils.calcAdd(upQty, resSettleItem.getThisQty());
        			upAmt = CalcUtils.calcAdd(upAmt, resSettleItem.getThisAmt());
				}
        		totalQty = CalcUtils.calcAdd(upQty, dbZxSaEquipResSettleItem.getThisQty());
        		totalAmt = CalcUtils.calcAdd(upAmt, dbZxSaEquipResSettleItem.getThisAmt());
        		//
        		dbZxSaEquipResSettleItem.setUpQty(upQty);
        		dbZxSaEquipResSettleItem.setUpAmt(upAmt);
        		dbZxSaEquipResSettleItem.setTotalQty(totalQty);
        		dbZxSaEquipResSettleItem.setTotalAmt(totalAmt);
        	}else {
        		//????????????
        		//??????????????????????????????upQty
        		//????????????????????????????????????(???)upAmt
        		//??????????????????????????????totalQty = ThisQty
        		//??????????????????????????????totalAmt = ThisAmt		
        		dbZxSaEquipResSettleItem.setUpQty(new BigDecimal("0"));
        		dbZxSaEquipResSettleItem.setUpAmt(new BigDecimal("0"));
        		dbZxSaEquipResSettleItem.setTotalQty(dbZxSaEquipResSettleItem.getThisQty());
        		dbZxSaEquipResSettleItem.setTotalAmt(dbZxSaEquipResSettleItem.getThisAmt());
        	}
        	// ??????
        	dbZxSaEquipResSettleItem.setModifyUserInfo(userKey, realName);
        	flag = zxSaEquipResSettleItemMapper.updateByPrimaryKey(dbZxSaEquipResSettleItem);
        	
        	//update?????????????????????=====???????????????????????????????????????
        	ZxSaEquipResSettle updateRes = zxSaEquipResSettleMapper.selectByPrimaryKey(dbZxSaEquipResSettleItem.getZxSaEquipResSettleId());
        	if(updateRes != null && StrUtil.isNotEmpty(updateRes.getZxSaEquipResSettleId())) {
        		BigDecimal thisAmt_res_big = new BigDecimal("0");//??????????????????????????????(???)
        		BigDecimal thisAmtNoTax_res_big = new BigDecimal("0");//?????????????????????????????????(???)
        		BigDecimal thisAmtTax_res_big = new BigDecimal("0");//????????????????????????(???)
        		BigDecimal totalAmt_res_big = new BigDecimal("0");//??????????????????????????????(???)
        		BigDecimal totalAmtNoTax_res_big = new BigDecimal("0");//?????????????????????????????????(???)

        		
        		
        		ZxSaEquipResSettleItem resItemSelect = new ZxSaEquipResSettleItem();
        		resItemSelect.setZxSaEquipResSettleId(updateRes.getZxSaEquipResSettleId());
        		List<ZxSaEquipResSettleItem> resItemSelectList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resItemSelect);
        		if(resItemSelectList != null && resItemSelectList.size() >0) {
        			for (ZxSaEquipResSettleItem item : resItemSelectList) {
        				thisAmt_res_big = CalcUtils.calcAdd(thisAmt_res_big, item.getThisAmt());
        				thisAmtNoTax_res_big = CalcUtils.calcAdd(thisAmtNoTax_res_big,item.getThisAmtNoTax());
        				thisAmtTax_res_big = CalcUtils.calcAdd(thisAmtTax_res_big,item.getThisAmtTax());
        			}
        		}
        		//???????????????????????????
        		resItemSelect.setZxSaEquipResSettleId("");
        		resItemSelect.setContractID(updateRes.getContractID());
        		List<ZxSaEquipResSettleItem> resSettleItemSelectTotalList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resItemSelect);
        		if(resSettleItemSelectTotalList != null && resSettleItemSelectTotalList.size() >0) {
        			for (ZxSaEquipResSettleItem item : resSettleItemSelectTotalList) {
        				totalAmt_res_big = CalcUtils.calcAdd(totalAmt_res_big, item.getThisAmt());
        				totalAmtNoTax_res_big = CalcUtils.calcAdd(totalAmtNoTax_res_big,item.getThisAmtNoTax());
        			}
        		}
        		updateRes.setThisAmt(thisAmt_res_big);
        		updateRes.setThisAmtNoTax(thisAmtNoTax_res_big);
        		updateRes.setThisAmtTax(thisAmtTax_res_big);
        		updateRes.setTotalAmt(totalAmt_res_big);
        		updateRes.setTotalAmtNoTax(totalAmtNoTax_res_big);
        		updateRes.setModifyUserInfo(userKey, realName);
        		flag = zxSaEquipResSettleMapper.updateByPrimaryKey(updateRes);
        		
        		//?????????????????????????????????
        		ZxSaEquipSettleAudit auditSelect = zxSaEquipSettleAuditMapper.selectByPrimaryKey(updateRes.getZxSaEquipSettleAuditId());
        		if(auditSelect != null && StrUtil.isNotEmpty(auditSelect.getZxSaEquipSettleAuditId())) {
        			//?????????
        			ZxSaEquipPaySettle paySettle = new ZxSaEquipPaySettle();
        			paySettle.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
        			List<ZxSaEquipPaySettle> paySettles = zxSaEquipPaySettleMapper.selectByZxSaEquipPaySettleList(paySettle);
        			BigDecimal thisAmt_pay_big = new BigDecimal("0");
        			BigDecimal totalAmt_pay_big = new BigDecimal("0");
        			BigDecimal thisAmtNoTax_pay_big = new BigDecimal("0");
        			BigDecimal totalAmtNoTax_pay_big = new BigDecimal("0");
        			if(paySettles != null && paySettles.size() >0) {
        				thisAmt_pay_big = paySettles.get(0).getThisAmt();
        				totalAmt_pay_big = paySettles.get(0).getTotalAmt();
        				thisAmtNoTax_pay_big = paySettles.get(0).getThisAmtNoTax();
        				totalAmtNoTax_pay_big = paySettles.get(0).getTotalAmtNoTax();
        			}
        			
        			
        			ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
        			auditItem.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
        			List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
        			if(auditItemList != null && auditItemList.size() >0) {
        				//???????????????????????????
        				BigDecimal rate_This = new BigDecimal("0");
        				BigDecimal rate_Total = new BigDecimal("0");
        				ZxCtEqCoContractAmtRate rate = new ZxCtEqCoContractAmtRate();
        				rate.setContractID(dbZxSaEquipResSettleItem.getContractID());
        				List<ZxCtEqCoContractAmtRate> rateList = zxCtEqCoContractAmtRateMapper.selectByZxCtEqCoContractAmtRateList(rate);
        				
        				BigDecimal thisAmt_100 = new BigDecimal("0");
        				BigDecimal totalAmt_100 = new BigDecimal("0");
        				BigDecimal thisAmt_110 = new BigDecimal("0");
        				BigDecimal totalAmt_110 = new BigDecimal("0");
        				BigDecimal thisAmt_120 = new BigDecimal("0");
        				BigDecimal totalAmt_120 = new BigDecimal("0");
        				BigDecimal thisAmt_300 = new BigDecimal("0");
        				BigDecimal totalAmt_300 = new BigDecimal("0");
        				BigDecimal thisAmt_500 = new BigDecimal("0");
        				BigDecimal totalAmt_500 = new BigDecimal("0");
        				BigDecimal thisAmt_700 = new BigDecimal("0");
        				BigDecimal totalAmt_700 = new BigDecimal("0");
        				for (ZxSaEquipSettleAuditItem auditItemSelect : auditItemList) {
        					if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100100")) {
        						//????????????????????????(??????)
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmt_res_big, thisAmt_pay_big)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmt_res_big, totalAmt_pay_big)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_100 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_100 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100110")) {
        						//???????????????????????????(??????)
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmtNoTax_res_big, thisAmtNoTax_pay_big)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmtNoTax_res_big, totalAmtNoTax_pay_big)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_110 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_110 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100120")) {
        						//??????????????????(??????) = ????????????????????????(??????) - ???????????????????????????(??????)
        						auditItemSelect.setThisAmt(CalcUtils.calcSubtract(thisAmt_100, thisAmt_110)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcSubtract(totalAmt_100, totalAmt_110)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_120 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_120 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100200")) {
        						//????????????????????????(??????)
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_100)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_100)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100210")) {
        						//???????????????????????????????????????
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_110)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_110)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100220")) {
        						//??????????????????(??????)
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_120)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_120)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100300")) {
        				        if(rateList != null && rateList.size() >0) {
        				        	for (ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate : rateList) {
        				        		rate_This = CalcUtils.calcAdd(rate_This, CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), thisAmt_res_big));
        				        		rate_Total = CalcUtils.calcAdd(rate_Total, CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), totalAmt_res_big));
        				        	}
        				        }
        				        //???????????????????????????
        				        auditItemSelect.setThisAmt(rate_This+"");
        				        auditItemSelect.setTotalAmt(rate_Total+"");
        				        auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_300 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_300 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100400")) {
        						//?????????
        						if(rateList != null && rateList.size() >0) {
        							for (ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate : rateList) {
        								if(StrUtil.equals(zxCtEqCoContractAmtRate.getZxCtEqCoContractAmtRateId(),auditItemSelect.getStatisticsID())) {
        									auditItemSelect.setThisAmt(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), thisAmt_res_big)+"");
        									auditItemSelect.setTotalAmt(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), totalAmt_res_big)+"");
        									auditItemSelect.setModifyUserInfo(userKey, realName);
        									flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        									break;
        								}
        							}
        						}
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100500")) {
        						//?????????????????????
        						thisAmt_500 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_500 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100600")) {
        						//?????????
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100700")) {
        						//???????????????????????????=????????????????????????100-???????????????????????????300+?????????????????????500
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt_100, thisAmt_300), thisAmt_500)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt_100, totalAmt_300), totalAmt_500)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_700 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_700 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100800")) {
        						//???????????????????????????
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_700)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_700)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}
        				}
        //????????????????????????????????????
        		        //????????????????????????thisAmt	= eqAuditItem_100
        		        //????????????????????????totalAmt = eqAuditItem_100
        		        //???????????????????????????thisPayAmt = eqAuditItem_700
        		        //???????????????????????????totalPayAmt = eqAuditItem_700
        				auditSelect.setThisAmt(thisAmt_100);
        				auditSelect.setTotalAmt(totalAmt_100);
        				auditSelect.setThisPayAmt(thisAmt_700);
        				auditSelect.setTotalPayAmt(totalAmt_700);
        				flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(auditSelect);
        			}
        		}
        	}
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaEquipResSettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaEquipResSettleItem(List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaEquipResSettleItemList != null && zxSaEquipResSettleItemList.size() > 0) {
           ZxSaEquipResSettleItem zxSaEquipResSettleItem = new ZxSaEquipResSettleItem();
           zxSaEquipResSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipResSettleItemMapper.batchDeleteUpdateZxSaEquipResSettleItem(zxSaEquipResSettleItemList, zxSaEquipResSettleItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaEquipResSettleItemList);
        }
    }

	@Override
	public List<ZxSaEquipResSettleItem> ureportZxSaEquipResSettleItem(ZxSaEquipResSettleItem zxSaEquipResSettleItem) {
		List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList = new ArrayList<>();
		
		ZxSaEquipSettleAudit dbZxSaEquipSettleAudit = zxSaEquipSettleAuditMapper.selectByPrimaryKey(zxSaEquipResSettleItem.getZxSaEquipSettleAuditId());
		// ????????????
        if (dbZxSaEquipSettleAudit != null) {	      
        	//??????
        	ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
        	resSettle.setZxSaEquipSettleAuditId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
        	if(resSettles != null && resSettles.size() >0) {
        		dbZxSaEquipSettleAudit.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
        		dbZxSaEquipSettleAudit.setContractAmt(resSettles.get(0).getContractAmt());
        		dbZxSaEquipSettleAudit.setChangeAmt(resSettles.get(0).getChangeAmt());
        		dbZxSaEquipSettleAudit.setUpAmtRes(resSettles.get(0).getUpAmt());
        		dbZxSaEquipSettleAudit.setThisAmtRes(resSettles.get(0).getThisAmt());
        		dbZxSaEquipSettleAudit.setTotalAmtRes(resSettles.get(0).getTotalAmt());
        		dbZxSaEquipSettleAudit.setThisAmtNoTaxRes(resSettles.get(0).getThisAmtNoTax());
        		dbZxSaEquipSettleAudit.setThisAmtTaxRes(resSettles.get(0).getThisAmtTax());
        		//????????????
        		ZxSaEquipResSettleItem resSettleItem = new ZxSaEquipResSettleItem();   
        		resSettleItem.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
        		zxSaEquipResSettleItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resSettleItem);
        		for (ZxSaEquipResSettleItem resItem : zxSaEquipResSettleItemList) {
        			resItem.setContractPriceNoTax(CalcUtils.calcDivide(resItem.getContractPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//??????????????????/(1+??????/100)
        			resItem.setContractAmtNoTax(CalcUtils.calcDivide(resItem.getContractAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//=????????????/(1+??????/100)
        			resItem.setContractAmtTax(CalcUtils.calcSubtract(resItem.getContractAmt(), resItem.getContractAmtNoTax()));//?????????????????? - ?????????????????????
        			resItem.setChangedAmtNoTax(CalcUtils.calcDivide(resItem.getChangedAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//=????????????/(1+??????/100)
        			resItem.setChangedAmtTax(CalcUtils.calcSubtract(resItem.getChangedAmt(), resItem.getChangedAmtNoTax()));//?????????????????? - ?????????????????????
        			resItem.setTotalAmtNoTax(CalcUtils.calcDivide(resItem.getTotalAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//=????????????/(1+??????/100)
        			resItem.setTotalAmtTax(CalcUtils.calcSubtract(resItem.getTotalAmt(), resItem.getTotalAmtNoTax()));//?????????????????? - ?????????????????????
        			resItem.setUpAmtNoTax(CalcUtils.calcDivide(resItem.getUpAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));
        			resItem.setUpAmtTax(CalcUtils.calcSubtract(resItem.getUpAmt(), resItem.getUpAmtNoTax()));
        		
        			resItem.setOrgName(dbZxSaEquipSettleAudit.getOrgName());
        			resItem.setSecondName(dbZxSaEquipSettleAudit.getSecondName());
        			resItem.setContractNo(dbZxSaEquipSettleAudit.getContractNo());
        		
        		}
        		dbZxSaEquipSettleAudit.setZxSaEquipResSettleItemList(zxSaEquipResSettleItemList);
        	}
        } 
		return zxSaEquipResSettleItemList;
	}

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
