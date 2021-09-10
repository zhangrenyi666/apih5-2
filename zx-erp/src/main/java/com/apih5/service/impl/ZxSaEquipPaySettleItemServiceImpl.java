package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaEquipPaySettleItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipPaySettleMapper;
import com.apih5.mybatis.dao.ZxSaEquipResSettleMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettleItem;
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;
import com.apih5.service.ZxSaEquipPaySettleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

@Service("zxSaEquipPaySettleItemService")
public class ZxSaEquipPaySettleItemServiceImpl implements ZxSaEquipPaySettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaEquipPaySettleItemMapper zxSaEquipPaySettleItemMapper;
    
    @Autowired(required = true)
    private ZxSaEquipPaySettleMapper zxSaEquipPaySettleMapper;

    @Autowired(required = true)
    private ZxSaEquipResSettleMapper zxSaEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaEquipSettleAuditMapper zxSaEquipSettleAuditMapper;
    
    @Autowired(required = true)
    private ZxSaEquipSettleAuditItemMapper zxSaEquipSettleAuditItemMapper;
    
    @Override
    public ResponseEntity getZxSaEquipPaySettleItemListByCondition(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        if (zxSaEquipPaySettleItem == null) {
            zxSaEquipPaySettleItem = new ZxSaEquipPaySettleItem();
        }
        if(StrUtil.isEmpty(zxSaEquipPaySettleItem.getZxSaEquipPaySettleId())) {
     	   return repEntity.layerMessage("no", "支付项id【zxSaEquipPaySettleId】必传！"); 
        }
        // 分页查询
        PageHelper.startPage(zxSaEquipPaySettleItem.getPage(),zxSaEquipPaySettleItem.getLimit());
        // 获取数据
        List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(zxSaEquipPaySettleItem);
        // 得到分页信息
        PageInfo<ZxSaEquipPaySettleItem> p = new PageInfo<>(zxSaEquipPaySettleItemList);

        return repEntity.okList(zxSaEquipPaySettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaEquipPaySettleItemDetail(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        if (zxSaEquipPaySettleItem == null) {
            zxSaEquipPaySettleItem = new ZxSaEquipPaySettleItem();
        }
        // 获取数据
        ZxSaEquipPaySettleItem dbZxSaEquipPaySettleItem = zxSaEquipPaySettleItemMapper.selectByPrimaryKey(zxSaEquipPaySettleItem.getZxSaEquipPaySettleItemId());
        // 数据存在
        if (dbZxSaEquipPaySettleItem != null) {
            return repEntity.ok(dbZxSaEquipPaySettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaEquipPaySettleItem(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        //重复校验==一个支付项名称只能新增一次
        ZxSaEquipPaySettleItem paySettleItem = new ZxSaEquipPaySettleItem();
        paySettleItem.setZxSaEquipPaySettleId(zxSaEquipPaySettleItem.getZxSaEquipPaySettleId());
        paySettleItem.setPayType(zxSaEquipPaySettleItem.getPayType());
        paySettleItem.setPayName(zxSaEquipPaySettleItem.getPayName());
        List<ZxSaEquipPaySettleItem> paySettleItemList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(paySettleItem);
        if(paySettleItemList !=null && paySettleItemList.size()>0) {
        	return repEntity.layerMessage("no", "该支付项已经存在，请重新选择！");
        }
        
        int flag = 0;
        zxSaEquipPaySettleItem.setZxSaEquipPaySettleItemId(UuidUtil.generate());
        zxSaEquipPaySettleItem.setCreateUserInfo(userKey, realName);

        BigDecimal thisAmt_small = new BigDecimal("0");//本期结算金额(元)=数量*单价(元)
        BigDecimal thisAmtNoTax_small = new BigDecimal("0");//本期结算不含税金额(元)= 本期结算金额(元)/(1+税率/100)
        BigDecimal thisAmtTax_small = new BigDecimal("0");//本期结算税额(元) = 本期结算金额(元) - 本期结算不含税金额(元)
        
        thisAmt_small = CalcUtils.calcMultiply(zxSaEquipPaySettleItem.getQty(), zxSaEquipPaySettleItem.getPrice());
        if(StrUtil.isNotEmpty(zxSaEquipPaySettleItem.getTaxRate())) {
        	thisAmtNoTax_small = CalcUtils.calcDivide(thisAmt_small, CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxSaEquipPaySettleItem.getTaxRate()), new BigDecimal("100"))));
        }
        thisAmtTax_small =  CalcUtils.calcSubtract(thisAmt_small, thisAmtNoTax_small);
        zxSaEquipPaySettleItem.setThisAmt(thisAmt_small);
        zxSaEquipPaySettleItem.setThisAmtNoTax(thisAmtNoTax_small);
        zxSaEquipPaySettleItem.setThisAmtTax(thisAmtTax_small);
        //每次新增之前都判断有没有往期的数据
        ZxSaEquipPaySettleItem payItemBefore = new ZxSaEquipPaySettleItem();
        payItemBefore.setContractID(zxSaEquipPaySettleItem.getContractID());
        payItemBefore.setPayID(zxSaEquipPaySettleItem.getPayID());
        List<ZxSaEquipPaySettleItem> payItemBeforeList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemBefore);
        if(payItemBeforeList != null && payItemBeforeList.size() >0) {
        	//有往期
        	// 上期末结算金额(元)upAmt
            // 上期末数量 upQty
        	BigDecimal upQty_pay_small = new BigDecimal("0");
    		BigDecimal upAmt_pay_small = new BigDecimal("0");
    		for (ZxSaEquipPaySettleItem paySettleItem1 : payItemBeforeList) {
    			upQty_pay_small = CalcUtils.calcAdd(upQty_pay_small, paySettleItem1.getQty());
    			upAmt_pay_small = CalcUtils.calcAdd(upAmt_pay_small, paySettleItem1.getThisAmt());
			}
    		zxSaEquipPaySettleItem.setUpQty(upQty_pay_small);
    		zxSaEquipPaySettleItem.setUpAmt(upAmt_pay_small);
        }else {
        	//没有往期
        	zxSaEquipPaySettleItem.setUpQty(new BigDecimal("0"));
        	zxSaEquipPaySettleItem.setUpAmt(new BigDecimal("0"));
        }
        flag = zxSaEquipPaySettleItemMapper.insert(zxSaEquipPaySettleItem);
        
        
        //更新
        ZxSaEquipPaySettle updatePay = zxSaEquipPaySettleMapper.selectByPrimaryKey(zxSaEquipPaySettleItem.getZxSaEquipPaySettleId());
        if(updatePay != null && StrUtil.isNotEmpty(updatePay.getZxSaEquipPaySettleId())) {
        	// 本期支付项结算金额(元)
        	BigDecimal thisAmt_pay_big = CalcUtils.calcAdd(updatePay.getThisAmt(), thisAmt_small);
        	//本期支付项结算不含税金额(元)
            BigDecimal thisAmtNoTax_pay_big = CalcUtils.calcAdd(updatePay.getThisAmtNoTax(),thisAmtNoTax_small);
            // 本期支付项结算税额(元)
            BigDecimal thisAmtTax_pay_big = CalcUtils.calcAdd(updatePay.getThisAmtTax(),thisAmtTax_small);
            
            BigDecimal inOutAmt = new BigDecimal("0");// 本期进退场费
            BigDecimal fineAmt = new BigDecimal("0");// 本期奖罚
            BigDecimal foodAmt = new BigDecimal("0");// 本期伙食费
            BigDecimal otherAmt = new BigDecimal("0");// 本期其他款项
            //更新之前应该查一下有没有往期的数据
            ZxSaEquipPaySettleItem payItemSelect = new ZxSaEquipPaySettleItem();
            payItemSelect.setZxSaEquipPaySettleId(zxSaEquipPaySettleItem.getZxSaEquipPaySettleId());
            List<ZxSaEquipPaySettleItem> payItemSelectList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemSelect);
            if(payItemSelectList != null && payItemSelectList.size() >0) {
            	//有往期==累计往期再更新
            	for (ZxSaEquipPaySettleItem paySettleItem2: payItemSelectList) {
            		if(StrUtil.equals(paySettleItem2.getPayType(), "伙食费")) {
                		foodAmt = CalcUtils.calcAdd(foodAmt,paySettleItem2.getThisAmt());
                	}else if(StrUtil.equals(paySettleItem2.getPayType(), "奖罚")) {
                		fineAmt = CalcUtils.calcAdd(fineAmt,paySettleItem2.getThisAmt());
                	}else if(StrUtil.equals(paySettleItem2.getPayType(), "进退场费")) {
                		inOutAmt = CalcUtils.calcAdd(inOutAmt,paySettleItem2.getThisAmt());
                	}else if(StrUtil.equals(paySettleItem2.getPayType(), "其他款项")) {
                		otherAmt = CalcUtils.calcAdd(otherAmt,paySettleItem2.getThisAmt());
                	}
            	}
            }else {
            	//没有往期==正常更新
            }

            
            
            BigDecimal totalAmt_pay_big = new BigDecimal("0");//累计支付项结算金额(元)
            BigDecimal totalAmtNoTax_pay_big = new BigDecimal("0");//累计支付项结算不含税金额(元)
            BigDecimal upAmt_pay_big = new BigDecimal("0");//上期末累计支付项结算金额(元)
            BigDecimal upInOutAmt_pay_big = new BigDecimal("0");//上期末进退场费
            BigDecimal upFineAmt_pay_big = new BigDecimal("0");//上期末奖罚
            BigDecimal upFoodAmt_pay_big = new BigDecimal("0");//上期末伙食费
            BigDecimal upOtherAmt_pay_big = new BigDecimal("0");//上期末其他款项
            
            //这个思路不对
            //更新之前应该查一下有没有往期的数据，为了进行后面的金额计算
            ZxSaEquipPaySettle payLess = new ZxSaEquipPaySettle();
            payLess.setContractID(updatePay.getContractID());
            payLess.setAutoNum(updatePay.getAutoNum());
            List<ZxSaEquipPaySettle> payLessList = zxSaEquipPaySettleMapper.getZxSaEquipPaySettleListLessAutoNum(payLess);
            if(payLessList != null && payLessList.size() >0) {
            	//多期
            	for (ZxSaEquipPaySettle payLesss : payLessList) {
            		totalAmt_pay_big = CalcUtils.calcAdd(totalAmt_pay_big, payLesss.getThisAmt());
            		totalAmtNoTax_pay_big = CalcUtils.calcAdd(totalAmtNoTax_pay_big, payLesss.getThisAmtNoTax());
            		upAmt_pay_big = CalcUtils.calcAdd(upAmt_pay_big, payLesss.getThisAmt());
            	//查往期支付项下的清单，为了计算上期末的那4个值
            		ZxSaEquipPaySettleItem payItemLess = new ZxSaEquipPaySettleItem();
            		payItemLess.setZxSaEquipPaySettleId(payLesss.getZxSaEquipPaySettleId());
                    List<ZxSaEquipPaySettleItem> payItemLessList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemLess);
                    if(payItemLessList != null && payItemLessList.size() >0) {
                    	for (ZxSaEquipPaySettleItem payItemLesss : payItemLessList) {
                    		if(StrUtil.equals(payItemLesss.getPayType(), "伙食费")) {
                    			upFoodAmt_pay_big = CalcUtils.calcAdd(upFoodAmt_pay_big,payItemLesss.getThisAmt());
                        	}else if(StrUtil.equals(payItemLesss.getPayType(), "奖罚")) {
                        		upFineAmt_pay_big = CalcUtils.calcAdd(upFineAmt_pay_big,payItemLesss.getThisAmt());
                        	}else if(StrUtil.equals(payItemLesss.getPayType(), "进退场费")) {
                        		upInOutAmt_pay_big = CalcUtils.calcAdd(upInOutAmt_pay_big,payItemLesss.getThisAmt());
                        	}else if(StrUtil.equals(payItemLesss.getPayType(), "其他款项")) {
                        		upOtherAmt_pay_big = CalcUtils.calcAdd(upOtherAmt_pay_big,payItemLesss.getThisAmt());
                        	}
						}
                    }
            	}
            	
            	totalAmt_pay_big = CalcUtils.calcAdd(totalAmt_pay_big, thisAmt_pay_big);
        		totalAmtNoTax_pay_big = CalcUtils.calcAdd(totalAmtNoTax_pay_big, thisAmtNoTax_pay_big);
            	
            }else {
            	//首期
            	totalAmt_pay_big = CalcUtils.calcAdd(totalAmt_pay_big, thisAmt_pay_big);
        		totalAmtNoTax_pay_big = CalcUtils.calcAdd(totalAmtNoTax_pay_big, thisAmtNoTax_pay_big);
            }
            
        	
           
        	updatePay.setThisAmt(thisAmt_pay_big);
        	updatePay.setThisAmtNoTax(thisAmtNoTax_pay_big);
        	updatePay.setThisAmtTax(thisAmtTax_pay_big);
        	updatePay.setTotalAmt(totalAmt_pay_big);
        	updatePay.setTotalAmtNoTax(totalAmtNoTax_pay_big);
        	updatePay.setUpAmt(upAmt_pay_big);
        	updatePay.setFoodAmt(foodAmt);
        	updatePay.setFineAmt(fineAmt);
        	updatePay.setInOutAmt(inOutAmt);
        	updatePay.setOtherAmt(otherAmt);
        	updatePay.setModifyUserInfo(userKey, realName);
        	flag = zxSaEquipPaySettleMapper.updateByPrimaryKey(updatePay);
        	
        	//同时更新统计项里的金额
    		ZxSaEquipSettleAudit auditSelect = zxSaEquipSettleAuditMapper.selectByPrimaryKey(updatePay.getZxSaEquipSettleAuditId());
    		if(auditSelect != null && StrUtil.isNotEmpty(auditSelect.getZxSaEquipSettleAuditId())) {
    			//清单项
    			ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
    			resSettle.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
    			List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
    			BigDecimal thisAmt_res_big = new BigDecimal("0");
    			BigDecimal totalAmt_res_big = new BigDecimal("0");
    			BigDecimal thisAmtNoTax_res_big = new BigDecimal("0");
    			BigDecimal totalAmtNoTax_res_big = new BigDecimal("0");
    			if(resSettles != null && resSettles.size() >0) {
    				thisAmt_res_big = resSettles.get(0).getThisAmt();
    				totalAmt_res_big = resSettles.get(0).getTotalAmt();
    				thisAmtNoTax_res_big = resSettles.get(0).getThisAmtNoTax();
    				totalAmtNoTax_res_big = resSettles.get(0).getTotalAmtNoTax();
    			}
    				
    			//统计项
    			ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
    			auditItem.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
    			List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
    			if(auditItemList != null && auditItemList.size() >0) {
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
    						//合计结算含税金额(小写)
    						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmt_res_big, thisAmt_pay_big)+"");
    						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmt_res_big, totalAmt_pay_big)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    						thisAmt_100 = new BigDecimal(auditItemSelect.getThisAmt());
    						totalAmt_100 = new BigDecimal(auditItemSelect.getTotalAmt());
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100110")) {
    						//合计不含税结算金额(小写)
    						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmtNoTax_res_big, thisAmtNoTax_pay_big)+"");
    						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmtNoTax_res_big, totalAmtNoTax_pay_big)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    						thisAmt_110 = new BigDecimal(auditItemSelect.getThisAmt());
    						totalAmt_110 = new BigDecimal(auditItemSelect.getTotalAmt());
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100120")) {
    						//合计结算税额(小写) = 合计结算含税金额(小写) - 合计不含税结算金额(小写)
    						auditItemSelect.setThisAmt(CalcUtils.calcSubtract(thisAmt_100, thisAmt_110)+"");
    						auditItemSelect.setTotalAmt(CalcUtils.calcSubtract(totalAmt_100, totalAmt_110)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    						thisAmt_120 = new BigDecimal(auditItemSelect.getThisAmt());
    						totalAmt_120 = new BigDecimal(auditItemSelect.getTotalAmt());
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100200")) {
    						//合计含税结算金额(大写)
    						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_100)+"");
    						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_100)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100210")) {
    						//合计不含税结算金额（大写）
    						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_110)+"");
    						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_110)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100220")) {
    						//合计结算税额(大写)
    						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_120)+"");
    						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_120)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100300")) {
    				        //其中扣除保证金合计
    				        //不变
    						thisAmt_300 = new BigDecimal(auditItemSelect.getThisAmt());
    						totalAmt_300 = new BigDecimal(auditItemSelect.getTotalAmt());
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100400")) {
    						//保证金
    						//不变
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100500")) {
    						//返还保证金合计
    						//不变
    						thisAmt_500 = new BigDecimal(auditItemSelect.getThisAmt());
    						totalAmt_500 = new BigDecimal(auditItemSelect.getTotalAmt());
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100600")) {
    						//保证金
    						//不变
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100700")) {
    						//应付租赁款（小写）=合计结算含税金额100-其中扣除保证金合计300+返还保证金合计500
    						auditItemSelect.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt_100, thisAmt_300), thisAmt_500)+"");
    						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt_100, totalAmt_300), totalAmt_500)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    						thisAmt_700 = new BigDecimal(auditItemSelect.getThisAmt());
    						totalAmt_700 = new BigDecimal(auditItemSelect.getTotalAmt());
    					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100800")) {
    						//应付租赁款（大写）
    						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_700)+"");
    						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_700)+"");
    						auditItemSelect.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
    					}
    				}
    //更新统计项就要更新结算单
    		        //本期结算含税金额thisAmt	= eqAuditItem_100
    		        //开累结算含税金额totalAmt = eqAuditItem_100
    		        //本期应支付含税金额thisPayAmt = eqAuditItem_700
    		        //开累应支付含税金额totalPayAmt = eqAuditItem_700
    				auditSelect.setThisAmt(thisAmt_100);
    				auditSelect.setTotalAmt(totalAmt_100);
    				auditSelect.setThisPayAmt(thisAmt_700);
    				auditSelect.setTotalPayAmt(totalAmt_700);
    				flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(auditSelect);
    			}
    		}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaEquipPaySettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaEquipPaySettleItem(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaEquipPaySettleItem dbZxSaEquipPaySettleItem = zxSaEquipPaySettleItemMapper.selectByPrimaryKey(zxSaEquipPaySettleItem.getZxSaEquipPaySettleItemId());
        if (dbZxSaEquipPaySettleItem != null && StrUtil.isNotEmpty(dbZxSaEquipPaySettleItem.getZxSaEquipPaySettleItemId())) {
        	//重复校验==一个支付项类型只能新增一次
        	ZxSaEquipPaySettleItem paySettleItem = new ZxSaEquipPaySettleItem();
        	paySettleItem.setSelectMainIdFlag("传主键id");
        	paySettleItem.setZxSaEquipPaySettleItemId(zxSaEquipPaySettleItem.getZxSaEquipPaySettleItemId());
        	paySettleItem.setZxSaEquipPaySettleId(zxSaEquipPaySettleItem.getZxSaEquipPaySettleId());
        	paySettleItem.setPayType(zxSaEquipPaySettleItem.getPayType());
        	paySettleItem.setPayName(zxSaEquipPaySettleItem.getPayName());
        	List<ZxSaEquipPaySettleItem> paySettleItemList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(paySettleItem);
        	if(paySettleItemList !=null && paySettleItemList.size()>0) {
        		return repEntity.layerMessage("no", "该支付项已经存在，请重新选择！");
        	}
        	// 支付项id
        	dbZxSaEquipPaySettleItem.setPayID(zxSaEquipPaySettleItem.getPayID());
        	// 编号
        	dbZxSaEquipPaySettleItem.setPayNo(zxSaEquipPaySettleItem.getPayNo());
        	// 支付项类型
        	dbZxSaEquipPaySettleItem.setPayType(zxSaEquipPaySettleItem.getPayType());
        	// 名称
        	dbZxSaEquipPaySettleItem.setPayName(zxSaEquipPaySettleItem.getPayName());
        	// 单位
        	dbZxSaEquipPaySettleItem.setUnit(zxSaEquipPaySettleItem.getUnit());
        	// 数量
        	dbZxSaEquipPaySettleItem.setQty(zxSaEquipPaySettleItem.getQty());
        	// 单价(元)
        	dbZxSaEquipPaySettleItem.setPrice(zxSaEquipPaySettleItem.getPrice());
        	// 税率(%)
        	dbZxSaEquipPaySettleItem.setTaxRate(zxSaEquipPaySettleItem.getTaxRate());
        	BigDecimal thisAmt_small = new BigDecimal("0");//本期结算金额(元)=数量*单价(元)
        	BigDecimal thisAmtNoTax_small = new BigDecimal("0");//本期结算不含税金额(元)= 本期结算金额(元)/(1+税率/100)
        	BigDecimal thisAmtTax_small = new BigDecimal("0");//本期结算税额(元) = 本期结算金额(元) - 本期结算不含税金额(元)
        	thisAmt_small = CalcUtils.calcMultiply(zxSaEquipPaySettleItem.getQty(), zxSaEquipPaySettleItem.getPrice());
        	if(StrUtil.isNotEmpty(zxSaEquipPaySettleItem.getTaxRate())) {
        		thisAmtNoTax_small = CalcUtils.calcDivide(thisAmt_small, CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxSaEquipPaySettleItem.getTaxRate()), new BigDecimal("100"))));
        	}
        	thisAmtTax_small =  CalcUtils.calcSubtract(thisAmt_small, thisAmtNoTax_small);
        	dbZxSaEquipPaySettleItem.setThisAmt(thisAmt_small);
        	dbZxSaEquipPaySettleItem.setThisAmtNoTax(thisAmtNoTax_small);
        	dbZxSaEquipPaySettleItem.setThisAmtTax(thisAmtTax_small);
        	//备注
        	dbZxSaEquipPaySettleItem.setRemark(zxSaEquipPaySettleItem.getRemark());
        	// 共通
        	dbZxSaEquipPaySettleItem.setModifyUserInfo(userKey, realName);
        	flag = zxSaEquipPaySettleItemMapper.updateByPrimaryKey(dbZxSaEquipPaySettleItem);
        	
      //更新
        	ZxSaEquipPaySettle updatePay = zxSaEquipPaySettleMapper.selectByPrimaryKey(dbZxSaEquipPaySettleItem.getZxSaEquipPaySettleId());
        	if(updatePay != null && StrUtil.isNotEmpty(updatePay.getZxSaEquipPaySettleId())) {
        		BigDecimal thisAmt_pay_big = new BigDecimal("0");// 本期支付项结算金额(元)
        		BigDecimal thisAmtNoTax_pay_big = new BigDecimal("0");	// 本期支付项结算不含税金额(元)
        		BigDecimal thisAmtTax_pay_big = new BigDecimal("0");// 本期支付项结算税额(元)
        		BigDecimal inOutAmt_pay_big = new BigDecimal("0");   // 本期进退场费
        		BigDecimal fineAmt_pay_big = new BigDecimal("0");// 本期奖罚
        		BigDecimal foodAmt_pay_big = new BigDecimal("0");  // 本期伙食费
        		BigDecimal otherAmt_pay_big = new BigDecimal("0");// 本期其他款项
        		BigDecimal totalAmt_pay_big = new BigDecimal("0");//累计支付项结算金额(元)
        		BigDecimal totalAmtNoTax_pay_big = new BigDecimal("0");//累计支付项结算不含税金额(元)
        		//
        		ZxSaEquipPaySettleItem payItemSelect = new ZxSaEquipPaySettleItem();
        		payItemSelect.setZxSaEquipPaySettleId(updatePay.getZxSaEquipPaySettleId());
        		List<ZxSaEquipPaySettleItem> payItemSelectList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemSelect);
        		if(payItemSelectList != null && payItemSelectList.size() >0) {
        			for (ZxSaEquipPaySettleItem item : payItemSelectList) {
        				thisAmt_pay_big = CalcUtils.calcAdd(thisAmt_pay_big, item.getThisAmt());
        				thisAmtNoTax_pay_big = CalcUtils.calcAdd(thisAmtNoTax_pay_big,item.getThisAmtNoTax());
        				thisAmtTax_pay_big = CalcUtils.calcAdd(thisAmtTax_pay_big,item.getThisAmtTax());
        				if(StrUtil.equals(item.getPayType(), "伙食费")) {
        					foodAmt_pay_big = CalcUtils.calcAdd(foodAmt_pay_big,item.getThisAmt());
        				}else if(StrUtil.equals(item.getPayType(), "奖罚")) {
        					fineAmt_pay_big = CalcUtils.calcAdd(fineAmt_pay_big,item.getThisAmt());
        				}else if(StrUtil.equals(item.getPayType(), "进退场费")) {
        					inOutAmt_pay_big = CalcUtils.calcAdd(inOutAmt_pay_big,item.getThisAmt());
        				}else if(StrUtil.equals(item.getPayType(), "其他款项")) {
        					otherAmt_pay_big = CalcUtils.calcAdd(otherAmt_pay_big,item.getThisAmt());
        				}
        			}
        		}

        		//更新之前应该查一下有没有往期的数据
        		payItemSelect.setZxSaEquipPaySettleId("");
        		payItemSelect.setContractID(zxSaEquipPaySettleItem.getContractID());
        		List<ZxSaEquipPaySettleItem> paySettleItemSelectTotalList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemSelect);
        		if(paySettleItemSelectTotalList != null && paySettleItemSelectTotalList.size() >0) {
        			//有往期==累计往期再更新
        			for (ZxSaEquipPaySettleItem paySettleItem2: paySettleItemSelectTotalList) {
        				totalAmt_pay_big = CalcUtils.calcAdd(totalAmt_pay_big, paySettleItem2.getThisAmt());
        				totalAmtNoTax_pay_big = CalcUtils.calcAdd(totalAmtNoTax_pay_big, paySettleItem2.getThisAmtNoTax());
        			}
        		}

        		updatePay.setThisAmt(thisAmt_pay_big);
        		updatePay.setThisAmtNoTax(thisAmtNoTax_pay_big);
        		updatePay.setThisAmtTax(thisAmtTax_pay_big);
        		updatePay.setFoodAmt(foodAmt_pay_big);
        		updatePay.setFineAmt(fineAmt_pay_big);
        		updatePay.setInOutAmt(inOutAmt_pay_big);
        		updatePay.setOtherAmt(otherAmt_pay_big);
        		updatePay.setTotalAmt(totalAmt_pay_big);
        		updatePay.setTotalAmtNoTax(totalAmtNoTax_pay_big);
        		updatePay.setModifyUserInfo(userKey, realName);
        		flag = zxSaEquipPaySettleMapper.updateByPrimaryKey(updatePay);
        		
        		
        		//同时更新统计项里的金额
        		ZxSaEquipSettleAudit auditSelect = zxSaEquipSettleAuditMapper.selectByPrimaryKey(updatePay.getZxSaEquipSettleAuditId());
        		if(auditSelect != null && StrUtil.isNotEmpty(auditSelect.getZxSaEquipSettleAuditId())) {
        			//清单项
        			ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
        			resSettle.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
        			List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
        			BigDecimal thisAmt_res_big = new BigDecimal("0");
        			BigDecimal totalAmt_res_big = new BigDecimal("0");
        			BigDecimal thisAmtNoTax_res_big = new BigDecimal("0");
        			BigDecimal totalAmtNoTax_res_big = new BigDecimal("0");
        			if(resSettles != null && resSettles.size() >0) {
        				thisAmt_res_big = resSettles.get(0).getThisAmt();
        				totalAmt_res_big = resSettles.get(0).getTotalAmt();
        				thisAmtNoTax_res_big = resSettles.get(0).getThisAmtNoTax();
        				totalAmtNoTax_res_big = resSettles.get(0).getTotalAmtNoTax();
        			}
        				
        			//统计项
        			ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
        			auditItem.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
        			List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
        			if(auditItemList != null && auditItemList.size() >0) {
        				
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
        						//合计结算含税金额(小写)
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmt_res_big, thisAmt_pay_big)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmt_res_big, totalAmt_pay_big)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_100 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_100 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100110")) {
        						//合计不含税结算金额(小写)
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmtNoTax_res_big, thisAmtNoTax_pay_big)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmtNoTax_res_big, totalAmtNoTax_pay_big)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_110 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_110 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100120")) {
        						//合计结算税额(小写) = 合计结算含税金额(小写) - 合计不含税结算金额(小写)
        						auditItemSelect.setThisAmt(CalcUtils.calcSubtract(thisAmt_100, thisAmt_110)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcSubtract(totalAmt_100, totalAmt_110)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_120 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_120 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100200")) {
        						//合计含税结算金额(大写)
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_100)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_100)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100210")) {
        						//合计不含税结算金额（大写）
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_110)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_110)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100220")) {
        						//合计结算税额(大写)
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_120)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_120)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100300")) {
        				        //其中扣除保证金合计
        						thisAmt_300 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_300 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100400")) {
        						//保证金
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100500")) {
        						//返还保证金合计
        						thisAmt_500 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_500 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100600")) {
        						//保证金
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100700")) {
        						//应付租赁款（小写）=合计结算含税金额100-其中扣除保证金合计300+返还保证金合计500
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt_100, thisAmt_300), thisAmt_500)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt_100, totalAmt_300), totalAmt_500)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_700 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_700 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100800")) {
        						//应付租赁款（大写）
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_700)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_700)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}
        				}
        //更新统计项就要更新结算单
        		        //本期结算含税金额thisAmt	= eqAuditItem_100
        		        //开累结算含税金额totalAmt = eqAuditItem_100
        		        //本期应支付含税金额thisPayAmt = eqAuditItem_700
        		        //开累应支付含税金额totalPayAmt = eqAuditItem_700
        				auditSelect.setThisAmt(thisAmt_100);
        				auditSelect.setTotalAmt(totalAmt_100);
        				auditSelect.setThisPayAmt(thisAmt_700);
        				auditSelect.setTotalPayAmt(totalAmt_700);
        				flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(auditSelect);
        			}
        		}
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaEquipPaySettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaEquipPaySettleItem(List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaEquipPaySettleItemList != null && zxSaEquipPaySettleItemList.size() > 0) {
        	ZxSaEquipPaySettleItem zxSaEquipPaySettleItem = new ZxSaEquipPaySettleItem();
        	zxSaEquipPaySettleItem.setModifyUserInfo(userKey, realName);
        	flag = zxSaEquipPaySettleItemMapper.batchDeleteUpdateZxSaEquipPaySettleItem(zxSaEquipPaySettleItemList, zxSaEquipPaySettleItem);
        //更新
        	ZxSaEquipPaySettle updatePay = zxSaEquipPaySettleMapper.selectByPrimaryKey(zxSaEquipPaySettleItemList.get(0).getZxSaEquipPaySettleId());
        	if(updatePay != null && StrUtil.isNotEmpty(updatePay.getZxSaEquipPaySettleId())) {
        		BigDecimal thisAmt_pay_big = new BigDecimal("0");// 本期支付项结算金额(元)
        		BigDecimal thisAmtNoTax_pay_big = new BigDecimal("0");	// 本期支付项结算不含税金额(元)
        		BigDecimal thisAmtTax_pay_big = new BigDecimal("0");// 本期支付项结算税额(元)
        		BigDecimal inOutAmt_pay_big = new BigDecimal("0");   // 本期进退场费
        		BigDecimal fineAmt_pay_big = new BigDecimal("0");// 本期奖罚
        		BigDecimal foodAmt_pay_big = new BigDecimal("0");  // 本期伙食费
        		BigDecimal otherAmt_pay_big = new BigDecimal("0");// 本期其他款项
        		BigDecimal totalAmt_pay_big = new BigDecimal("0");//累计支付项结算金额(元)
        		BigDecimal totalAmtNoTax_pay_big = new BigDecimal("0");//累计支付项结算不含税金额(元)
        		//
        		ZxSaEquipPaySettleItem payItemSelect = new ZxSaEquipPaySettleItem();
        		payItemSelect.setZxSaEquipPaySettleId(updatePay.getZxSaEquipPaySettleId());
        		List<ZxSaEquipPaySettleItem> payItemSelectList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemSelect);
        		if(payItemSelectList != null && payItemSelectList.size() >0) {
        			for (ZxSaEquipPaySettleItem item : payItemSelectList) {
        				thisAmt_pay_big = CalcUtils.calcAdd(thisAmt_pay_big, item.getThisAmt());
        				thisAmtNoTax_pay_big = CalcUtils.calcAdd(thisAmtNoTax_pay_big,item.getThisAmtNoTax());
        				thisAmtTax_pay_big = CalcUtils.calcAdd(thisAmtTax_pay_big,item.getThisAmtTax());
        				if(StrUtil.equals(item.getPayType(), "伙食费")) {
        					foodAmt_pay_big = CalcUtils.calcAdd(foodAmt_pay_big,item.getThisAmt());
        				}else if(StrUtil.equals(item.getPayType(), "奖罚")) {
        					fineAmt_pay_big = CalcUtils.calcAdd(fineAmt_pay_big,item.getThisAmt());
        				}else if(StrUtil.equals(item.getPayType(), "进退场费")) {
        					inOutAmt_pay_big = CalcUtils.calcAdd(inOutAmt_pay_big,item.getThisAmt());
        				}else if(StrUtil.equals(item.getPayType(), "其他款项")) {
        					otherAmt_pay_big = CalcUtils.calcAdd(otherAmt_pay_big,item.getThisAmt());
        				}
        			}
        		}

        		//更新之前应该查一下有没有往期的数据
        		payItemSelect.setZxSaEquipPaySettleId("");
        		payItemSelect.setContractID(zxSaEquipPaySettleItemList.get(0).getContractID());
        		List<ZxSaEquipPaySettleItem> payItemSelectTotalList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemSelect);
        		if(payItemSelectTotalList != null && payItemSelectTotalList.size() >0) {
        			//有往期==累计往期再更新
        			for (ZxSaEquipPaySettleItem paySettleItem2: payItemSelectTotalList) {
        				totalAmt_pay_big = CalcUtils.calcAdd(totalAmt_pay_big, paySettleItem2.getThisAmt());
        				totalAmtNoTax_pay_big = CalcUtils.calcAdd(totalAmtNoTax_pay_big, paySettleItem2.getThisAmtNoTax());
        			}
        		}

        		updatePay.setThisAmt(thisAmt_pay_big);
        		updatePay.setThisAmtNoTax(thisAmtNoTax_pay_big);
        		updatePay.setThisAmtTax(thisAmtTax_pay_big);
        		updatePay.setFoodAmt(foodAmt_pay_big);
        		updatePay.setFineAmt(fineAmt_pay_big);
        		updatePay.setInOutAmt(inOutAmt_pay_big);
        		updatePay.setOtherAmt(otherAmt_pay_big);
        		updatePay.setTotalAmt(totalAmt_pay_big);
        		updatePay.setTotalAmtNoTax(totalAmtNoTax_pay_big);
        		updatePay.setModifyUserInfo(userKey, realName);
        		flag = zxSaEquipPaySettleMapper.updateByPrimaryKey(updatePay);



        		//同时更新统计项里的金额
        		ZxSaEquipSettleAudit auditSelect = zxSaEquipSettleAuditMapper.selectByPrimaryKey(updatePay.getZxSaEquipSettleAuditId());
        		if(auditSelect != null && StrUtil.isNotEmpty(auditSelect.getZxSaEquipSettleAuditId())) {
        			//清单项
        			ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
        			resSettle.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
        			List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
        			BigDecimal thisAmt_res_big = new BigDecimal("0");
        			BigDecimal totalAmt_res_big = new BigDecimal("0");
        			BigDecimal thisAmtNoTax_res_big = new BigDecimal("0");
        			BigDecimal totalAmtNoTax_res_big = new BigDecimal("0");
        			if(resSettles != null && resSettles.size() >0) {
        				thisAmt_res_big = resSettles.get(0).getThisAmt();
        				totalAmt_res_big = resSettles.get(0).getTotalAmt();
        				thisAmtNoTax_res_big = resSettles.get(0).getThisAmtNoTax();
        				totalAmtNoTax_res_big = resSettles.get(0).getTotalAmtNoTax();
        			}

        			//统计项
        			ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
        			auditItem.setZxSaEquipSettleAuditId(auditSelect.getZxSaEquipSettleAuditId());
        			List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
        			if(auditItemList != null && auditItemList.size() >0) {
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
        						//合计结算含税金额(小写)
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmt_res_big, thisAmt_pay_big)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmt_res_big, totalAmt_pay_big)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_100 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_100 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100110")) {
        						//合计不含税结算金额(小写)
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(thisAmtNoTax_res_big, thisAmtNoTax_pay_big)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(totalAmtNoTax_res_big, totalAmtNoTax_pay_big)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_110 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_110 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100120")) {
        						//合计结算税额(小写) = 合计结算含税金额(小写) - 合计不含税结算金额(小写)
        						auditItemSelect.setThisAmt(CalcUtils.calcSubtract(thisAmt_100, thisAmt_110)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcSubtract(totalAmt_100, totalAmt_110)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_120 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_120 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100200")) {
        						//合计含税结算金额(大写)
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_100)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_100)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100210")) {
        						//合计不含税结算金额（大写）
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_110)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_110)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100220")) {
        						//合计结算税额(大写)
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_120)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_120)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100300")) {
        						//其中扣除保证金合计
        						thisAmt_300 =  new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_300 =  new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100400")) {
        						//保证金
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100500")) {
        						//返还保证金合计
        						thisAmt_500 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_500 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100600")) {
        						//保证金
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100700")) {
        						//应付租赁款（小写）=合计结算含税金额100-其中扣除保证金合计300+返还保证金合计500
        						auditItemSelect.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt_100, thisAmt_300), thisAmt_500)+"");
        						auditItemSelect.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt_100, totalAmt_300), totalAmt_500)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        						thisAmt_700 = new BigDecimal(auditItemSelect.getThisAmt());
        						totalAmt_700 = new BigDecimal(auditItemSelect.getTotalAmt());
        					}else if(StrUtil.equals(auditItemSelect.getStatisticsNo(), "100800")) {
        						//应付租赁款（大写）
        						auditItemSelect.setThisAmt(Convert.digitToChinese(thisAmt_700)+"");
        						auditItemSelect.setTotalAmt(Convert.digitToChinese(totalAmt_700)+"");
        						auditItemSelect.setModifyUserInfo(userKey, realName);
        						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItemSelect);
        					}
        				}
       //更新统计项就要更新结算单
       		        //本期结算含税金额thisAmt	= eqAuditItem_100
       		        //开累结算含税金额totalAmt = eqAuditItem_100
       		        //本期应支付含税金额thisPayAmt = eqAuditItem_700
       		        //开累应支付含税金额totalPayAmt = eqAuditItem_700
       				auditSelect.setThisAmt(thisAmt_100);
       				auditSelect.setTotalAmt(totalAmt_100);
       				auditSelect.setThisPayAmt(thisAmt_700);
       				auditSelect.setTotalPayAmt(totalAmt_700);
       				flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(auditSelect);
        			}
        		}
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaEquipPaySettleItemList);
        }
    }

	@Override
	public List<ZxSaEquipPaySettleItem> ureportZxSaEquipPaySettleItemList(ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
		List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemCurrentList = new ArrayList<>(); // 本期,最终返回数据
		List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemPastList = new ArrayList<>(); // 往期
		ZxSaEquipSettleAudit dbZxSaEquipSettleAudit = zxSaEquipSettleAuditMapper.selectByPrimaryKey(zxSaEquipPaySettleItem.getZxSaEquipSettleAuditId());

		// 数据存在
		if (dbZxSaEquipSettleAudit != null) {
			ZxSaEquipPaySettle paySettle = new ZxSaEquipPaySettle();
			paySettle.setAutoNum(String.valueOf(dbZxSaEquipSettleAudit.getAutoNum()));
			paySettle.setContractID(dbZxSaEquipSettleAudit.getContractID());
			// 支付项
			List<ZxSaEquipPaySettle> paySettles = zxSaEquipPaySettleMapper.selectByZxSaEquipPaySettlePastList(paySettle);

			if (paySettles != null && paySettles.size() > 0) {
				for (ZxSaEquipPaySettle payList : paySettles) {
					ZxSaEquipPaySettleItem paySettleItem = new ZxSaEquipPaySettleItem();
					paySettleItem.setZxSaEquipPaySettleId(payList.getZxSaEquipPaySettleId());
					// 本期详细
					if (Integer.valueOf(payList.getAutoNum()).intValue() == dbZxSaEquipSettleAudit.getAutoNum()) {
						List<ZxSaEquipPaySettleItem> currentList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(paySettleItem);
						zxSaEquipPaySettleItemCurrentList.addAll(currentList);

						// 往期详细
					} else {
						List<ZxSaEquipPaySettleItem> pastList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(paySettleItem);
						zxSaEquipPaySettleItemPastList.addAll(pastList);
					}
				}

				// 本期payIdlist
				List<String> payIdlist = zxSaEquipPaySettleItemCurrentList.stream().map(ZxSaEquipPaySettleItem::getPayID).collect(Collectors.toList());
				// 合并处理往期数据只返回list中
				for (ZxSaEquipPaySettleItem pastList : zxSaEquipPaySettleItemPastList) {
					if (!payIdlist.contains(pastList.getPayID())) {
						pastList.setUpQty(pastList.getQty());
						pastList.setUpAmt(pastList.getThisAmt());
						pastList.setQty(new BigDecimal("0"));
						pastList.setThisAmt(new BigDecimal("0"));
						zxSaEquipPaySettleItemCurrentList.add(pastList);
					}
				}

				// 本期末求和
				BigDecimal totalAmt = new BigDecimal("0");
				BigDecimal totalQty = new BigDecimal("0");
				for (ZxSaEquipPaySettleItem payItem : zxSaEquipPaySettleItemCurrentList) {
					totalAmt = CalcUtils.calcAdd(payItem.getThisAmt(), payItem.getUpAmt());
					totalQty = CalcUtils.calcAdd(payItem.getQty(), payItem.getUpQty());
					payItem.setTotalAmt(totalAmt);
					payItem.setTotalQty(totalQty);
				}
			}
		}
		return zxSaEquipPaySettleItemCurrentList;
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
