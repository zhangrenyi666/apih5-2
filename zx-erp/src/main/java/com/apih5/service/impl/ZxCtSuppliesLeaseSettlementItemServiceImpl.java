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
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesMarginRatioMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesMarginRatio;
import com.apih5.service.ZxCtSuppliesLeaseSettlementItemService;
import com.apih5.utils.DigitalConversionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.text.NumberFormat;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesLeaseSettlementItemService")
public class ZxCtSuppliesLeaseSettlementItemServiceImpl implements ZxCtSuppliesLeaseSettlementItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementItemMapper zxCtSuppliesLeaseSettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesMarginRatioMapper zxCtSuppliesMarginRatioMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListMapper zxCtSuppliesLeaseSettlementListMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemListByCondition(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        if (zxCtSuppliesLeaseSettlementItem == null) {
            zxCtSuppliesLeaseSettlementItem = new ZxCtSuppliesLeaseSettlementItem();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesLeaseSettlementItem.getPage(),zxCtSuppliesLeaseSettlementItem.getLimit());
        // 获取数据
        List<ZxCtSuppliesLeaseSettlementItem> zxCtSuppliesLeaseSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(zxCtSuppliesLeaseSettlementItem);
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeaseSettlementItem> p = new PageInfo<>(zxCtSuppliesLeaseSettlementItemList);

        return repEntity.okList(zxCtSuppliesLeaseSettlementItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemDetail(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        if (zxCtSuppliesLeaseSettlementItem == null) {
            zxCtSuppliesLeaseSettlementItem = new ZxCtSuppliesLeaseSettlementItem();
        }
        // 获取数据
        ZxCtSuppliesLeaseSettlementItem dbZxCtSuppliesLeaseSettlementItem = zxCtSuppliesLeaseSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementItemId());
        // 数据存在
        if (dbZxCtSuppliesLeaseSettlementItem != null) {
            return repEntity.ok(dbZxCtSuppliesLeaseSettlementItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesLeaseSettlementItem(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
        zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesLeaseSettlementItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesLeaseSettlementItem(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeaseSettlementItem dbZxCtSuppliesLeaseSettlementItem = zxCtSuppliesLeaseSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementItemId());
        if (dbZxCtSuppliesLeaseSettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementItemId())) {
           // 最后编辑时间
           dbZxCtSuppliesLeaseSettlementItem.setEditTime(zxCtSuppliesLeaseSettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesLeaseSettlementItem.setMainID(zxCtSuppliesLeaseSettlementItem.getMainID());
           // 项目ID
           dbZxCtSuppliesLeaseSettlementItem.setOrgID(zxCtSuppliesLeaseSettlementItem.getOrgID());
           // 统计项类型
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsType(zxCtSuppliesLeaseSettlementItem.getStatisticsType());
           // 统计项编号
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
           // 统计项ID
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsID(zxCtSuppliesLeaseSettlementItem.getStatisticsID());
           // 统计项
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsName(zxCtSuppliesLeaseSettlementItem.getStatisticsName());
           // 所属公司排序
           dbZxCtSuppliesLeaseSettlementItem.setComOrders(zxCtSuppliesLeaseSettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeaseSettlementItem.setComName(zxCtSuppliesLeaseSettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeaseSettlementItem.setComID(zxCtSuppliesLeaseSettlementItem.getComID());
           // 上期末金额
           dbZxCtSuppliesLeaseSettlementItem.setUpAmt(zxCtSuppliesLeaseSettlementItem.getUpAmt());
           // 期次
           dbZxCtSuppliesLeaseSettlementItem.setPeriod(zxCtSuppliesLeaseSettlementItem.getPeriod());
           // 开累(元)
           dbZxCtSuppliesLeaseSettlementItem.setTotalAmt(zxCtSuppliesLeaseSettlementItem.getTotalAmt());
           // 结算单编号
           dbZxCtSuppliesLeaseSettlementItem.setBillNo(zxCtSuppliesLeaseSettlementItem.getBillNo());
           // 合同ID
           dbZxCtSuppliesLeaseSettlementItem.setContractID(zxCtSuppliesLeaseSettlementItem.getContractID());
           // 比例
           dbZxCtSuppliesLeaseSettlementItem.setRate(zxCtSuppliesLeaseSettlementItem.getRate());
           // 本期(元)
           dbZxCtSuppliesLeaseSettlementItem.setThisAmt(zxCtSuppliesLeaseSettlementItem.getThisAmt());
           // 备注
           dbZxCtSuppliesLeaseSettlementItem.setRemarks(zxCtSuppliesLeaseSettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesLeaseSettlementItem.setSort(zxCtSuppliesLeaseSettlementItem.getSort());
           // 共通
           dbZxCtSuppliesLeaseSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseSettlementItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseSettlementItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(List<ZxCtSuppliesLeaseSettlementItem> zxCtSuppliesLeaseSettlementItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesLeaseSettlementItemList != null && zxCtSuppliesLeaseSettlementItemList.size() > 0) {
           ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem = new ZxCtSuppliesLeaseSettlementItem();
           zxCtSuppliesLeaseSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseSettlementItemMapper.batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(zxCtSuppliesLeaseSettlementItemList, zxCtSuppliesLeaseSettlementItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesLeaseSettlementItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemListByConID(
    		ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	BigDecimal thisAmt = new BigDecimal(0);
    	BigDecimal thisEquipAmt = new BigDecimal(0);
    	BigDecimal thisAmtTax = new BigDecimal(0);
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
        ZxCtSuppliesLeaseSettlementList settlement = zxCtSuppliesLeaseSettlementListMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementListId());
		DecimalFormat decimalFormat = new DecimalFormat("00");	
        thisEquipAmt = settlement.getThisEquipAmt();
        int num = Integer.parseInt(settlement.getInitSerialNumber());
			ZxCtSuppliesLeaseSettlementList leaseSettlement = new ZxCtSuppliesLeaseSettlementList();
			leaseSettlement.setInitSerialNumber(decimalFormat.format(num-1));
			List<ZxCtSuppliesLeaseSettlementList> leaseSettlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(leaseSettlement);
			if(leaseSettlementList.size()>0) {
				billNo = leaseSettlementList.get(0).getBillNo();
			}
			if(settlement.getThisAmt() != null) {
				thisAmt = settlement.getThisAmt();//本期结算金额(元)
				thisAmtTax = settlement.getThisAmtTax();//本期结算税额(元)
				thisAmtNoTax = settlement.getThisAmtNoTax();//本期结算不含税金额(元)
			}
        	ZxCtSuppliesLeaseSettlementItem dbSettlementItem = new ZxCtSuppliesLeaseSettlementItem();
        	dbSettlementItem.setContractID(settlement.getContractID());
        	dbSettlementItem.setBillNo(billNo);
        ZxCtSuppliesLeaseSettlementItem settlementItem = new ZxCtSuppliesLeaseSettlementItem();
        settlementItem.setContractID(zxCtSuppliesLeaseSettlementItem.getContractID());
        settlementItem.setMainID(zxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementListId());
    	List<ZxCtSuppliesLeaseSettlementItem> zxCtSuppliesLeaseSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(settlementItem);
    	if(zxCtSuppliesLeaseSettlementItemList.size() == 0) {
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100100");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("合计含税结算金额（小写）");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100100");
    		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
    		List<ZxCtSuppliesLeaseSettlementItem> dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
    			totalAmt = CalcUtils.calcAdd(thisAmt, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			upTotalAmt = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalAmt);
    		}else {
    			totalAmt = thisAmt;	
    		}
    		zxCtSuppliesLeaseSettlementItem.setContractID(settlement.getContractID());
    		zxCtSuppliesLeaseSettlementItem.setBillNo(settlement.getBillNo());
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(thisAmt.stripTrailingZeros().toPlainString());
    		zxCtSuppliesLeaseSettlementItem.setTotalAmt(totalAmt.stripTrailingZeros().toPlainString());
    		zxCtSuppliesLeaseSettlementItem.setMainID(zxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementListId());
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
    		
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100110");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("合计不含税结算金额（小写）");
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
    			totalAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			upTotalAmtTax = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalAmtTax);
    		}else {
    			totalAmtNoTax = thisAmtNoTax;	
    		}
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(thisAmtNoTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesLeaseSettlementItem.setTotalAmt(totalAmtNoTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100110");
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
    		
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100120");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("合计结算税额（小写）");
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
    			totalAmtTax = CalcUtils.calcAdd(thisAmtTax, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			upTotalAmtNoTax = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalAmtNoTax);
    		}else {
    			totalAmtTax = thisAmtTax;	
    		}
    		settlement.setTotalAmt(totalAmt);//开累结算金额(元)
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(thisAmtTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesLeaseSettlementItem.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100120");
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
    		
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100200");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("合计含税结算金额（大写）");
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
    		zxCtSuppliesLeaseSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmt));
    		zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalAmt);
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100200");
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
    		
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100210");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("合计不含税结算金额（大写）");
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtNoTax));
    		zxCtSuppliesLeaseSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100210");
    		zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalAmtTax);
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
    		
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100220");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("合计结算税额（大写）");
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtTax));
    		zxCtSuppliesLeaseSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtTax));
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100220");
    		zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalAmtNoTax);
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
        	//获取保证金比例
    		ZxCtSuppliesMarginRatio ratio = new ZxCtSuppliesMarginRatio();
    		ratio.setContractID(zxCtSuppliesLeaseSettlementItem.getContractID());
        	List<ZxCtSuppliesMarginRatio> ratioList = zxCtSuppliesMarginRatioMapper.selectByZxCtSuppliesMarginRatioList(ratio);
        	if(ratioList.size()>0) {    	
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {
        			thisDeduBond = CalcUtils.calcAdd(thisDeduBond, CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)));
        		}
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100300");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("其中扣除保证金合计");
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(thisDeduBond.stripTrailingZeros().toPlainString());
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
    			totalDeduBond = CalcUtils.calcAdd(thisDeduBond, new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
    			zxCtSuppliesLeaseSettlementItem.setTotalAmt(totalDeduBond.stripTrailingZeros().toPlainString());
    			upTotalDeduBond = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalDeduBond);
    		}else {
        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(thisDeduBond.stripTrailingZeros().toPlainString());
    		}      		
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100300");
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {    			
        			//先增后删
        			zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
            		zxCtSuppliesLeaseSettlementItem.setStatisticsNo(marginRatio.getZxCtSuppliesMarginRatioId());
            		zxCtSuppliesLeaseSettlementItem.setStatisticsName(marginRatio.getStatisticsName());
            		zxCtSuppliesLeaseSettlementItem.setThisAmt(CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)).stripTrailingZeros().toPlainString());
               		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
            		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
            		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
                		zxCtSuppliesLeaseSettlementItem.setTotalAmt(CalcUtils.calcAdd(new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()), new BigDecimal(zxCtSuppliesLeaseSettlementItem.getThisAmt())).stripTrailingZeros().toPlainString());
            			zxCtSuppliesLeaseSettlementItem.setUpAmt(new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
            		}else {
                		zxCtSuppliesLeaseSettlementItem.setTotalAmt(zxCtSuppliesLeaseSettlementItem.getThisAmt());
            		}
        			zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
        			zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
        		}
        	}    		
        	zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
        	zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100500");
        	zxCtSuppliesLeaseSettlementItem.setStatisticsName("返还保证金合计");
        	zxCtSuppliesLeaseSettlementItem.setThisAmt("0");
        	zxCtSuppliesLeaseSettlementItem.setStatisticsType("100500");
        	if(ratioList.size()>0) {
        	//拥有往期，计算开累
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(dbSettlementItemList.get(0).getTotalAmt());
    			upTotalReturnBond = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upTotalReturnBond);
    		}else {
        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(zxCtSuppliesLeaseSettlementItem.getThisAmt());
    		}
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
        		for(ZxCtSuppliesMarginRatio marginRatio : ratioList) {    			
        			//先增后删
        			zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
            		zxCtSuppliesLeaseSettlementItem.setStatisticsNo(marginRatio.getZxCtSuppliesMarginRatioId());
            		zxCtSuppliesLeaseSettlementItem.setStatisticsName(marginRatio.getStatisticsName());
            		zxCtSuppliesLeaseSettlementItem.setThisAmt("0");
                	//拥有往期，计算开累
               		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
               		dbSettlementItem.setStatisticsType(zxCtSuppliesLeaseSettlementItem.getStatisticsType());
            		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
            		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
                		zxCtSuppliesLeaseSettlementItem.setTotalAmt(dbSettlementItemList.get(0).getTotalAmt());
            			zxCtSuppliesLeaseSettlementItem.setUpAmt(new BigDecimal(dbSettlementItemList.get(0).getTotalAmt()));
            		}else {
                		zxCtSuppliesLeaseSettlementItem.setTotalAmt(zxCtSuppliesLeaseSettlementItem.getThisAmt());
            		}
            		zxCtSuppliesLeaseSettlementItem.setSort(2);
        			zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
        			zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
        		}
        	}   
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100700");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("应付租赁款（小写）");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100700");
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(CalcUtils.calcSubtract(thisAmt, thisDeduBond).stripTrailingZeros().toPlainString());
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
       		dbSettlementItem.setStatisticsType(zxCtSuppliesLeaseSettlementItem.getStatisticsType());
    		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
    			shouldPayAmt = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
//        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, totalDeduBond), new BigDecimal(dbSettlementItemList.get(0).getTotalAmt())).stripTrailingZeros().toPlainString());
//        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(CalcUtils.calcSubtract(totalAmt, totalDeduBond).stripTrailingZeros().toPlainString());
    			zxCtSuppliesLeaseSettlementItem.setTotalAmt(CalcUtils.calcAdd(upTotalReturnBond, CalcUtils.calcSubtract(totalAmt, totalDeduBond)).stripTrailingZeros().toPlainString());
        		upLeaseAmt = new BigDecimal(dbSettlementItemList.get(0).getTotalAmt());
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upLeaseAmt);
    		}else {
        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(zxCtSuppliesLeaseSettlementItem.getThisAmt());
    		}
    		settlement.setThisPayAmt(new BigDecimal(zxCtSuppliesLeaseSettlementItem.getThisAmt()));//本期应付金额(元)
    		settlement.setTotalPayAmt(new BigDecimal(zxCtSuppliesLeaseSettlementItem.getTotalAmt()));//开累应付金额(元)
    		zxCtSuppliesLeaseSettlementItem.setSort(0);
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
    		
    		zxCtSuppliesLeaseSettlementItem.setZxCtSuppliesLeaseSettlementItemId(UuidUtil.generate());
    		zxCtSuppliesLeaseSettlementItem.setStatisticsNo("100800");
    		zxCtSuppliesLeaseSettlementItem.setStatisticsName("应付租赁款（大写）");
    		zxCtSuppliesLeaseSettlementItem.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(thisAmt, thisDeduBond)));
       		dbSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
    		dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    		if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem).size()>0) {
        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, totalDeduBond), shouldPayAmt)));
    			zxCtSuppliesLeaseSettlementItem.setUpAmt(upLeaseAmt);
    		}else {
        		zxCtSuppliesLeaseSettlementItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(totalAmt, thisDeduBond)));
    		}
    		zxCtSuppliesLeaseSettlementItem.setSort(0);
    		zxCtSuppliesLeaseSettlementItem.setStatisticsType("100800");
    		zxCtSuppliesLeaseSettlementItem.setCreateUserInfo(userKey, realName);
    		zxCtSuppliesLeaseSettlementItemMapper.insert(zxCtSuppliesLeaseSettlementItem);
            // 获取数据
            zxCtSuppliesLeaseSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(settlementItem);
//            settlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKey(settlement);
    	}else {
    		//数据重新计算，重新赋值
    		for(ZxCtSuppliesLeaseSettlementItem settlementList : zxCtSuppliesLeaseSettlementItemList) {
    			if(StrUtil.equals(settlementList.getStatisticsNo(), "100100")) {//合计含税结算金额（小写）
    				settlementList.setThisAmt(thisAmt.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmt = CalcUtils.calcAdd(thisAmt, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmt = thisAmt;	
    	    		}
    	    		settlement.setTotalAmt(totalAmt);//开累结算金额(元)
    				settlementList.setTotalAmt(totalAmt.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100110")){//合计不含税结算金额（小写）
    				settlementList.setThisAmt(thisAmtNoTax.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmtNoTax = thisAmtNoTax;	
    	    		}
    				settlementList.setTotalAmt(totalAmtNoTax.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100120")){//合计结算税额（小写）
    				settlementList.setThisAmt(thisAmtTax.stripTrailingZeros().toPlainString());
    	    		if(settlementList.getUpAmt() != null) {
    	    			totalAmtTax = CalcUtils.calcAdd(thisAmtTax, settlementList.getUpAmt());
    	    		}else {
    	    			totalAmtTax = thisAmtTax;	
    	    		}
    	    		settlementList.setTotalAmt(totalAmtTax.stripTrailingZeros().toPlainString());
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100200")){//合计含税结算金额（大写）
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
    	    		settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmt));
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100210")){//合计不含税结算金额（大写）
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtNoTax));
    				settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100220")){//合计结算税额（大写）
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmtTax));
    				settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(totalAmtTax));
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsType(), "100300")){//扣除保证金
    				if(!StrUtil.equals(settlementList.getStatisticsNo(), "100300")) {
    					ZxCtSuppliesMarginRatio marginRatio = zxCtSuppliesMarginRatioMapper.selectByPrimaryKey(settlementList.getStatisticsNo());
    					thisDeduBond = CalcUtils.calcAdd(thisDeduBond, CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)));
    					settlementList.setThisAmt(CalcUtils.calcMultiply(thisEquipAmt, CalcUtils.calcDivide(marginRatio.getStatisticsRate(), new BigDecimal(100), 6)).stripTrailingZeros().toPlainString());
                		if(settlementList.getUpAmt() != null) {
                			settlementList.setTotalAmt(CalcUtils.calcAdd(settlementList.getUpAmt(), new BigDecimal(settlementList.getThisAmt())).stripTrailingZeros().toPlainString());
                		}else {
                			settlementList.setTotalAmt(settlementList.getThisAmt());
                		}
        				settlementList.setModifyUserInfo(userKey, realName);
        	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
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
        	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    				}
//    				totalReturnBond = new BigDecimal(settlementList.getThisAmt());
    			}
    		}
    		for(ZxCtSuppliesLeaseSettlementItem settlementList : zxCtSuppliesLeaseSettlementItemList) {
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
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
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
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
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
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}else if(StrUtil.equals(settlementList.getStatisticsNo(), "100800")) {//应付租赁款（大写）
    	        	//拥有往期，计算开累
    				settlementList.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond))));
    	       		dbSettlementItem.setStatisticsNo(settlementList.getStatisticsNo());
    	       		List<ZxCtSuppliesLeaseSettlementItem> dbSettlementItemList = zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(dbSettlementItem);
    	    		if(dbSettlementItemList.size()>0) {
    	    			settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond)), shouldPayAmt)));
    	    		}else {
    	    			settlementList.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalReturnBond, CalcUtils.calcSubtract(thisAmt, thisDeduBond))));
    	    		}    				
    				settlementList.setModifyUserInfo(userKey, realName);
    	    		zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKeySelective(settlementList);
    			}
    		}
//            settlement.setModifyUserInfo(userKey, realName);
            zxCtSuppliesLeaseSettlementListMapper.updateByPrimaryKey(settlement);
    	}
        // 得到分页信息
        PageInfo<ZxCtSuppliesLeaseSettlementItem> p = new PageInfo<>(zxCtSuppliesLeaseSettlementItemList);

        return repEntity.okList(zxCtSuppliesLeaseSettlementItemList, p.getTotal());
    }

	@Override
	public ResponseEntity updateZxCtSuppliesLeaseSettlementItemByConID(
			ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesLeaseSettlementItem dbZxCtSuppliesLeaseSettlementItem = zxCtSuppliesLeaseSettlementItemMapper.selectByPrimaryKey(zxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementItemId());
        if (dbZxCtSuppliesLeaseSettlementItem != null && StrUtil.isNotEmpty(dbZxCtSuppliesLeaseSettlementItem.getZxCtSuppliesLeaseSettlementItemId())) {
           // 最后编辑时间
           dbZxCtSuppliesLeaseSettlementItem.setEditTime(zxCtSuppliesLeaseSettlementItem.getEditTime());
           // 主表ID
           dbZxCtSuppliesLeaseSettlementItem.setMainID(zxCtSuppliesLeaseSettlementItem.getMainID());
           // 项目ID
           dbZxCtSuppliesLeaseSettlementItem.setOrgID(zxCtSuppliesLeaseSettlementItem.getOrgID());
           // 统计项类型
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsType(zxCtSuppliesLeaseSettlementItem.getStatisticsType());
           // 统计项编号
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsNo(zxCtSuppliesLeaseSettlementItem.getStatisticsNo());
           // 统计项ID
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsID(zxCtSuppliesLeaseSettlementItem.getStatisticsID());
           // 统计项
           dbZxCtSuppliesLeaseSettlementItem.setStatisticsName(zxCtSuppliesLeaseSettlementItem.getStatisticsName());
           // 所属公司排序
           dbZxCtSuppliesLeaseSettlementItem.setComOrders(zxCtSuppliesLeaseSettlementItem.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesLeaseSettlementItem.setComName(zxCtSuppliesLeaseSettlementItem.getComName());
           // 所属公司ID
           dbZxCtSuppliesLeaseSettlementItem.setComID(zxCtSuppliesLeaseSettlementItem.getComID());
           // 上期末金额
           dbZxCtSuppliesLeaseSettlementItem.setUpAmt(zxCtSuppliesLeaseSettlementItem.getUpAmt());
           // 期次
           dbZxCtSuppliesLeaseSettlementItem.setPeriod(zxCtSuppliesLeaseSettlementItem.getPeriod());
           // 开累(元)
           dbZxCtSuppliesLeaseSettlementItem.setTotalAmt(zxCtSuppliesLeaseSettlementItem.getTotalAmt());
           // 结算单编号
           dbZxCtSuppliesLeaseSettlementItem.setBillNo(zxCtSuppliesLeaseSettlementItem.getBillNo());
           // 合同ID
           dbZxCtSuppliesLeaseSettlementItem.setContractID(zxCtSuppliesLeaseSettlementItem.getContractID());
           // 比例
           dbZxCtSuppliesLeaseSettlementItem.setRate(zxCtSuppliesLeaseSettlementItem.getRate());
           // 本期(元)
           dbZxCtSuppliesLeaseSettlementItem.setThisAmt(zxCtSuppliesLeaseSettlementItem.getThisAmt());
           // 备注
           dbZxCtSuppliesLeaseSettlementItem.setRemarks(zxCtSuppliesLeaseSettlementItem.getRemarks());
           // 排序
           dbZxCtSuppliesLeaseSettlementItem.setSort(zxCtSuppliesLeaseSettlementItem.getSort());
           // 共通
           dbZxCtSuppliesLeaseSettlementItem.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesLeaseSettlementItemMapper.updateByPrimaryKey(dbZxCtSuppliesLeaseSettlementItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesLeaseSettlementItem);
        }
	}
}
