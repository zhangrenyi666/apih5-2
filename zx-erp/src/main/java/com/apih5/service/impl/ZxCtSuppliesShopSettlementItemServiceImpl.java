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
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopSettlementItem.getPage(),zxCtSuppliesShopSettlementItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopSettlementItem> zxCtSuppliesShopSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(zxCtSuppliesShopSettlementItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopSettlementItem> p = new PageInfo<>(zxCtSuppliesShopSettlementItemList);

        return repEntity.okList(zxCtSuppliesShopSettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopSettlementItemDetail(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        if (zxCtSuppliesShopSettlementItem == null) {
            zxCtSuppliesShopSettlementItem = new ZxCtSuppliesShopSettlementItem();
        }
        // 获取数据
        ZxCtSuppliesShopSettlementItem dbZxCtSuppliesShopSettlementItem = zxCtSuppliesShopSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementItemId());
        // 数据存在
        if (dbZxCtSuppliesShopSettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesShopSettlementItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 最后编辑时间
           dbZxCtSuppliesShopSettlementItem.setEditTime(zxCtSuppliesShopSettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopSettlementItem.setMainID(zxCtSuppliesShopSettlementItem.getMainID());
           // 项目ID
           dbZxCtSuppliesShopSettlementItem.setOrgID(zxCtSuppliesShopSettlementItem.getOrgID());
           // 统计项类型
           dbZxCtSuppliesShopSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());
           // 统计项编号
           dbZxCtSuppliesShopSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
           // 统计项ID
           dbZxCtSuppliesShopSettlementItem.setStatisticsID(zxCtSuppliesShopSettlementItem.getStatisticsID());
           // 统计项
           dbZxCtSuppliesShopSettlementItem.setStatisticsName(zxCtSuppliesShopSettlementItem.getStatisticsName());
           // 所属公司排序
           dbZxCtSuppliesShopSettlementItem.setComOrders(zxCtSuppliesShopSettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopSettlementItem.setComName(zxCtSuppliesShopSettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopSettlementItem.setComID(zxCtSuppliesShopSettlementItem.getComID());
           // 上期末金额
           dbZxCtSuppliesShopSettlementItem.setUpAmt(zxCtSuppliesShopSettlementItem.getUpAmt());
           // 期次
           dbZxCtSuppliesShopSettlementItem.setPeriod(zxCtSuppliesShopSettlementItem.getPeriod());
           // 开累(元)
           dbZxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getTotalAmt());
           // 结算单编号
           dbZxCtSuppliesShopSettlementItem.setBillNo(zxCtSuppliesShopSettlementItem.getBillNo());
           // 合同ID
           dbZxCtSuppliesShopSettlementItem.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
           // 比例
           dbZxCtSuppliesShopSettlementItem.setRate(zxCtSuppliesShopSettlementItem.getRate());
           // 本期(元)
           dbZxCtSuppliesShopSettlementItem.setThisAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
           // 备注
           dbZxCtSuppliesShopSettlementItem.setRemarks(zxCtSuppliesShopSettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesShopSettlementItem.setSort(zxCtSuppliesShopSettlementItem.getSort());
           // 共通
           dbZxCtSuppliesShopSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopSettlementItem);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopSettlementItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
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
    	BigDecimal thisDeduBond = new BigDecimal(0);//扣除保证金合计
    	BigDecimal totalDeduBond = new BigDecimal(0);//扣除保证金合计
    	BigDecimal totalReturnBond = new BigDecimal(0);//返还保证金合计
    	
    	BigDecimal upTotalAmt = new BigDecimal(0);//上期
    	BigDecimal upTotalAmtTax = new BigDecimal(0);//上期
    	BigDecimal upTotalAmtNoTax = new BigDecimal(0);//扣除保证金合计
    	BigDecimal upTotalDeduBond = new BigDecimal(0);//扣除保证金合计
    	BigDecimal upTotalReturnBond = new BigDecimal(0);//返还保证金合计
    	BigDecimal upLeaseAmt = new BigDecimal(0);//返还保证金合计
    	BigDecimal shouldPayAmt = new BigDecimal(0);//应付租赁款
    	String billNo = "";
        // 分页查询
    	ZxCtSuppliesShopSettlementList settlement = zxCtSuppliesShopSettlementListMapper.selectByPrimaryKey(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementId());
        thisEquipAmt = settlement.getResThisAmt();
			if(settlement.getThisAmt() != null) {
				thisAmt = settlement.getThisAmt();//本期结算金额(元)
				thisAmtTax = settlement.getThisAmtTax();//本期结算税额(元)
				thisAmtNoTax = settlement.getThisAmtNoTax();//本期结算不含税金额(元)
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
    		zxCtSuppliesShopSettlementItem.setStatisticsName("合计含税结算金额（小写）");
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
    		settlement.setTotalAmt(totalAmt);//开累结算金额(元)
    		zxCtSuppliesShopSettlementItem.setContractID(settlement.getContractID());
    		zxCtSuppliesShopSettlementItem.setBillNo(settlement.getBillNo());
    		zxCtSuppliesShopSettlementItem.setThisAmt(thisAmt.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setTotalAmt(totalAmt.stripTrailingZeros().toPlainString());
    		zxCtSuppliesShopSettlementItem.setMainID(zxCtSuppliesShopSettlementItem.getZxCtSuppliesShopSettlementId());
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100110");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("合计不含税结算金额（小写）");
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
    		zxCtSuppliesShopSettlementItem.setStatisticsName("合计结算税额（小写）");
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
    		zxCtSuppliesShopSettlementItem.setStatisticsName("合计含税结算金额（大写）");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
    		zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmt));
    		zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmt);
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100200");
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100210");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("合计不含税结算金额（大写）");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtNoTax));
    		zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100210");
    		zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmtTax);
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100220");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("合计结算税额（大写）");
    		zxCtSuppliesShopSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtTax));
    		zxCtSuppliesShopSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtTax));
    		zxCtSuppliesShopSettlementItem.setStatisticsType("100220");
    		zxCtSuppliesShopSettlementItem.setUpAmt(upTotalAmtNoTax);
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
        	//获取保证金比例
    		ZxCtSuppliesMarginRatio ratio = new ZxCtSuppliesMarginRatio();
    		ratio.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
        	List<ZxCtSuppliesMarginRatio> ratioList = zxCtSuppliesMarginRatioMapper.selectByZxCtSuppliesMarginRatioList(ratio);
        	if(ratioList.size()>0) {    	
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {
        			thisDeduBond = CalcUtils.calcAdd(thisDeduBond, CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)));
        		}
        	zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        	zxCtSuppliesShopSettlementItem.setStatisticsNo("100300");
        	zxCtSuppliesShopSettlementItem.setStatisticsName("其中扣除保证金合计");
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
        			//先增后删
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
        	zxCtSuppliesShopSettlementItem.setStatisticsName("返还保证金合计");
        	zxCtSuppliesShopSettlementItem.setThisAmt("0");
        	zxCtSuppliesShopSettlementItem.setStatisticsType("100500");
        	if(ratioList.size()>0) {
        	//拥有往期，计算开累
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
        			//先增后删
        			zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
        			zxCtSuppliesShopSettlementItem.setStatisticsNo(marginRatio.getZxCtSuppliesMarginRatioId());
        			zxCtSuppliesShopSettlementItem.setStatisticsName(marginRatio.getStatisticsName());
        			zxCtSuppliesShopSettlementItem.setThisAmt("0");
                	//拥有往期，计算开累
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
        	zxCtSuppliesShopSettlementItem.setStatisticsName("应付材料款（小写）");
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
    		settlement.setThisPayAmt(new BigDecimal(zxCtSuppliesShopSettlementItem.getThisAmt()));//本期应付金额(元)
    		settlement.setTotalPayAmt(new BigDecimal(zxCtSuppliesShopSettlementItem.getTotalAmt()));//开累应付金额(元)
    		zxCtSuppliesShopSettlementItem.setSort(0);
    		zxCtSuppliesShopSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesShopSettlementItemMapper.insert(zxCtSuppliesShopSettlementItem);
    		
    		zxCtSuppliesShopSettlementItem.setZxCtSuppliesShopSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesShopSettlementItem.setStatisticsNo("100800");
    		zxCtSuppliesShopSettlementItem.setStatisticsName("应付材料款（大写）");
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
            // 获取数据
            zxCtSuppliesLeaseSettlementItemList = zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(settlementItem);
            settlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesShopSettlementListMapper.updateByPrimaryKey(settlement);
    	}else {
    		//数据重新计算，重新赋值
    		for(ZxCtSuppliesShopSettlementItem settlementList : zxCtSuppliesLeaseSettlementItemList) {
    			if(StrUtil.equals(settlementList.getStatisticsNo(), "100100")) {//合计含税结算金额（小写）
    				settlementList.setThisAmt(thisAmt.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmt = CalcUtils.calcAdd(thisAmt, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmt = thisAmt;	
    	    		}
    	    		settlement.setTotalAmt(totalAmt);//开累结算金额(元)
    				settlementList.setTotalAmt(totalAmt.stripTrailingZeros().toPlainString());
//    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100110")){//合计不含税结算金额（小写）
    				settlementList.setThisAmt(thisAmtNoTax.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmtNoTax = thisAmtNoTax;	
    	    		}
    				settlementList.setTotalAmt(totalAmtNoTax.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100120")){//合计结算税额（小写）
    				settlementList.setThisAmt(thisAmtTax.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(thisAmtTax, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmtTax = thisAmtTax;	
    	    		}
    	    		settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100200")){//合计含税结算金额（大写）
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
    	    		settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmt));
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100210")){//合计不含税结算金额（大写）
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtNoTax));
    				settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100220")){//合计结算税额（大写）
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtTax));
    				settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtTax));
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsType(), "100300")){//扣除保证金
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
    			}else if(StrUtil.equals(settlementList.getStatisticsType(), "100500")){//返还保证金
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
    			if(StrUtil.equals(settlementList.getStatisticsNo(), "100500")) {//其中扣除保证金合计
    				settlementList.setThisAmt(totalReturnBond.stripTrailingZeros().toPlainString());
    	        	//拥有往期，计算开累
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(totalReturnBond, settlementList.getUpAmt());
    	    			settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    	    		}else {
    	    			settlementList.setTotalAmt(totalReturnBond.stripTrailingZeros().toPlainString());
    	    		}    				
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100300")) {//其中扣除保证金合计
    				settlementList.setThisAmt(thisDeduBond.stripTrailingZeros().toPlainString());
    	        	//拥有往期，计算开累
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(thisDeduBond, settlementList.getUpAmt());
    	    			settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    	    		}else {
    	    			settlementList.setTotalAmt(thisDeduBond.stripTrailingZeros().toPlainString());
    	    		}    				
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100700")) {//应付租赁款（小写）
    				settlementList.setThisAmt(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond)).stripTrailingZeros().toPlainString());
    				//拥有往期，计算开累
    	    		if(settlementList.getUpAmt() != null) {
    	    			shouldPayAmt = settlementList.getUpAmt();
    	    			settlementList.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond)), settlementList.getUpAmt()).stripTrailingZeros().toPlainString());
    	    		}else {
    	    			settlementList.setTotalAmt(totalDeduBond.stripTrailingZeros().toPlainString());
    	    		}    				
    	    		settlement.setThisPayAmt(new BigDecimal(settlementList.getThisAmt()));//本期应付金额(元)
    	    		settlement.setTotalPayAmt(new BigDecimal(settlementList.getTotalAmt()));//开累应付金额(元)    	    		
    				settlementList.setModifyUserInfo(userKey, realName);
    				zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100800")) {//应付租赁款（大写）
    	        	//拥有往期，计算开累
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
        // 得到分页信息
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
           // 最后编辑时间
           dbZxCtSuppliesShopSettlementItem.setEditTime(zxCtSuppliesShopSettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopSettlementItem.setMainID(zxCtSuppliesShopSettlementItem.getMainID());
           // 项目ID
           dbZxCtSuppliesShopSettlementItem.setOrgID(zxCtSuppliesShopSettlementItem.getOrgID());
           // 统计项类型
           dbZxCtSuppliesShopSettlementItem.setStatisticsType(zxCtSuppliesShopSettlementItem.getStatisticsType());
           // 统计项编号
           dbZxCtSuppliesShopSettlementItem.setStatisticsNo(zxCtSuppliesShopSettlementItem.getStatisticsNo());
           // 统计项ID
           dbZxCtSuppliesShopSettlementItem.setStatisticsID(zxCtSuppliesShopSettlementItem.getStatisticsID());
           // 统计项
           dbZxCtSuppliesShopSettlementItem.setStatisticsName(zxCtSuppliesShopSettlementItem.getStatisticsName());
           // 所属公司排序
           dbZxCtSuppliesShopSettlementItem.setComOrders(zxCtSuppliesShopSettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopSettlementItem.setComName(zxCtSuppliesShopSettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopSettlementItem.setComID(zxCtSuppliesShopSettlementItem.getComID());
           // 上期末金额
           dbZxCtSuppliesShopSettlementItem.setUpAmt(zxCtSuppliesShopSettlementItem.getUpAmt());
           // 期次
           dbZxCtSuppliesShopSettlementItem.setPeriod(zxCtSuppliesShopSettlementItem.getPeriod());
           // 开累(元)
           dbZxCtSuppliesShopSettlementItem.setTotalAmt(zxCtSuppliesShopSettlementItem.getTotalAmt());
           // 结算单编号
           dbZxCtSuppliesShopSettlementItem.setBillNo(zxCtSuppliesShopSettlementItem.getBillNo());
           // 合同ID
           dbZxCtSuppliesShopSettlementItem.setContractID(zxCtSuppliesShopSettlementItem.getContractID());
           // 比例
           dbZxCtSuppliesShopSettlementItem.setRate(zxCtSuppliesShopSettlementItem.getRate());
           // 本期(元)
           dbZxCtSuppliesShopSettlementItem.setThisAmt(zxCtSuppliesShopSettlementItem.getThisAmt());
           // 备注
           dbZxCtSuppliesShopSettlementItem.setRemarks(zxCtSuppliesShopSettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesShopSettlementItem.setSort(zxCtSuppliesShopSettlementItem.getSort());
           // 共通
           dbZxCtSuppliesShopSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopSettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesShopSettlementItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopSettlementItem);
        }
	}
}
