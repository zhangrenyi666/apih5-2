package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtEqContrAlterItemResoureMapper;
import com.apih5.mybatis.dao.ZxCtEqContrItemMapper;
import com.apih5.mybatis.dao.ZxCtEqContrSupplyMapper;
import com.apih5.mybatis.pojo.ZxCtEqContrAlterItemResoure;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.mybatis.pojo.ZxCtEqContrSupply;
import com.apih5.service.ZxCtEqContrAlterItemResoureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtEqContrAlterItemResoureService")
public class ZxCtEqContrAlterItemResoureServiceImpl implements ZxCtEqContrAlterItemResoureService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtEqContrAlterItemResoureMapper zxCtEqContrAlterItemResoureMapper;

	@Autowired(required = true)
	private ZxCtEqContrSupplyMapper zxCtEqContrSupplyMapper;
	
    @Autowired(required = true)
    private ZxCtEqContrItemMapper zxCtEqContrItemMapper;
	
    @Override
    public ResponseEntity getZxCtEqContrAlterItemResoureListByCondition(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        if (zxCtEqContrAlterItemResoure == null) {
            zxCtEqContrAlterItemResoure = new ZxCtEqContrAlterItemResoure();
        }
        if(StrUtil.isEmpty(zxCtEqContrAlterItemResoure.getContrAlterID())) {
        	 return repEntity.layerMessage("no", "contrAlterID不能为空");
        }
        // 分页查询
        PageHelper.startPage(zxCtEqContrAlterItemResoure.getPage(),zxCtEqContrAlterItemResoure.getLimit());
        // 获取数据
        List<ZxCtEqContrAlterItemResoure> zxCtEqContrAlterItemResoureList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(zxCtEqContrAlterItemResoure);
        // 得到分页信息
        PageInfo<ZxCtEqContrAlterItemResoure> p = new PageInfo<>(zxCtEqContrAlterItemResoureList);

        return repEntity.okList(zxCtEqContrAlterItemResoureList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtEqContrAlterItemResoureDetail(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        if (zxCtEqContrAlterItemResoure == null) {
            zxCtEqContrAlterItemResoure = new ZxCtEqContrAlterItemResoure();
        }
        // 获取数据
        ZxCtEqContrAlterItemResoure dbZxCtEqContrAlterItemResoure = zxCtEqContrAlterItemResoureMapper.selectByPrimaryKey(zxCtEqContrAlterItemResoure.getZxCtEqContrAlterItemResoureId());
        // 数据存在
        if (dbZxCtEqContrAlterItemResoure != null) {
            return repEntity.ok(dbZxCtEqContrAlterItemResoure);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtEqContrAlterItemResoure(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        zxCtEqContrAlterItemResoure.setZxCtEqContrAlterItemResoureId(UuidUtil.generate());
        if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {//修改
        	zxCtEqContrAlterItemResoure.setContrItemID(zxCtEqContrAlterItemResoure.getContrItemID());
        	//同一源清单不能被多行修改
            ZxCtEqContrAlterItemResoure alterItemResoure = new ZxCtEqContrAlterItemResoure();
            alterItemResoure.setAlterType("2");
            alterItemResoure.setContrItemID(zxCtEqContrAlterItemResoure.getContrItemID());
            alterItemResoure.setContrAlterID(zxCtEqContrAlterItemResoure.getContrAlterID());
            alterItemResoure.setZxEqResCategoryId(zxCtEqContrAlterItemResoure.getZxEqResCategoryId());
            List<ZxCtEqContrAlterItemResoure> alterItemResoureList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItemResoure);
            if(alterItemResoureList != null && alterItemResoureList.size() >0) {
            	 return repEntity.layerMessage("no", "该设备已经存在，请重新选择！");
            }
        }else {
        	zxCtEqContrAlterItemResoure.setContrItemID(UuidUtil.generate());
        }
        zxCtEqContrAlterItemResoure.setAlterAmt(CalcUtils.calcMultiply(zxCtEqContrAlterItemResoure.getAlterTrrm(), zxCtEqContrAlterItemResoure.getAlterPrice()));//变更后含税金额(元)alterAmt=变更后数量alterTrrm*变更后含税单价(元)alterPrice
        //变更后数量alterTrrm
        //变更后含税单价(元)alterPrice
        BigDecimal alterPriceNoTax = new BigDecimal("0");//变更后不含税单价(元)alterPriceNoTax = 变更后含税单价(元)alterPrice/(1+税率/100)
        BigDecimal alterAmtNoTax = new BigDecimal("0");//变更后不含税金额(元)alterAmtNoTax = 含税金额(alterAmt)/(1+税率/100)
        if(StrUtil.isNotEmpty(zxCtEqContrAlterItemResoure.getTaxRate())) {
        	alterPriceNoTax = CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrAlterItemResoure.getTaxRate()), new BigDecimal("100"))));
        	alterAmtNoTax = CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrAlterItemResoure.getTaxRate()), new BigDecimal("100"))));
        }
        zxCtEqContrAlterItemResoure.setAlterPriceNoTax(alterPriceNoTax);//变更后不含税单价(元)alterPriceNoTax
        zxCtEqContrAlterItemResoure.setAlterAmtNoTax(alterAmtNoTax);//变更后不含税金额(元)alterAmtNoTax
        zxCtEqContrAlterItemResoure.setAlterAmtTax(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), zxCtEqContrAlterItemResoure.getAlterAmtNoTax()));//变更后税额(元)alterAmtTax = 变更后含税金额(元)alterAmt - 变更后不含税金额(元)alterAmtNoTax
        zxCtEqContrAlterItemResoure.setCreateUserInfo(userKey, realName);
        
        //先判断是首次还是多期的补充协议
        
//*****************更新
        BigDecimal thisAmount_big = new BigDecimal("0");
    	BigDecimal thisAmountNoTax_big = new BigDecimal("0");
    	BigDecimal thisAmountTax_big = new BigDecimal("0");
    	
    	BigDecimal alterContractSum_big = new BigDecimal("0");
    	BigDecimal alterContractSumNoTax_big = new BigDecimal("0");
    	BigDecimal alterContractSumTax_big = new BigDecimal("0");
    	
    	
    	
    	
    	
        //1.判断是首期还是多期的补充协议
        ZxCtEqContrSupply supplySelect = new ZxCtEqContrSupply();
        supplySelect.setContrAlterID(zxCtEqContrAlterItemResoure.getContrAlterID());
        List<ZxCtEqContrSupply> updateSupplySelects = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supplySelect);
        if(updateSupplySelects != null && updateSupplySelects.size() >0) {
        	ZxCtEqContrSupply supply = new ZxCtEqContrSupply();
        	supply.setApih5FlowStatus("2");
        	supply.setContractID(updateSupplySelects.get(0).getContractID());
        	List<ZxCtEqContrSupply> supplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supply);
        	if(supplyList != null && supplyList.size() >0) {
        		//多期
        		flag = addUpdateZxCtEqContrSupplyForMore(zxCtEqContrAlterItemResoure,updateSupplySelects.get(0),supplyList);
        	}else {
        		//首期
        		//2.看看首期下是否有多条的明细
            	ZxCtEqContrAlterItemResoure alterItem = new ZxCtEqContrAlterItemResoure();
            	alterItem.setContrAlterID(updateSupplySelects.get(0).getContrAlterID());
            	List<ZxCtEqContrAlterItemResoure> alterItemList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItem);
            	if(alterItemList != null && alterItemList.size() >0) {
            		//多条
            		//修改类 = 原本的+[(单条的变更金额-合同的含税金额)/10000]
            		if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {
            			thisAmount_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getThisAmount(), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), zxCtEqContrAlterItemResoure.getContractSum()), new BigDecimal("10000"),6));
            			thisAmountNoTax_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getThisAmountNoTax(),CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), zxCtEqContrAlterItemResoure.getContractSumNoTax()),new BigDecimal("10000"),6));
            		}else if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "1")) {
            		//新增类 = 原本的+[(变更数量*变更单价)/10000]
            			thisAmount_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getThisAmount(),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6));
            			thisAmountNoTax_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getThisAmountNoTax(), CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6));
            		}
            	}else {
            		//首条  
            		//修改类=单条的变更金额-合同的含税金额
            		if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {
            			thisAmount_big = CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), zxCtEqContrAlterItemResoure.getContractSum()), new BigDecimal("10000"),6);
            			thisAmountNoTax_big = CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), zxCtEqContrAlterItemResoure.getContractSumNoTax()),new BigDecimal("10000"),6);
            		}else if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "1")) {
            		//新增类= 变更数量*变更单价
            			thisAmount_big = CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6);
            			thisAmountNoTax_big = CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6);
            		}
            	}
            	thisAmountTax_big = CalcUtils.calcSubtract(thisAmount_big, thisAmountNoTax_big);
            	//变更后含税金额(万元)alterContractSum = 合同含税金额+本期协议增减税
            	//变更后不含税金额(万元)alterContractSumNoTax = contractCostNoTax+thisAmountNoTax_big
            	//变更后税额(万元)alterContractSumTax = contractCostTax+thisAmountTax_big
            	alterContractSum_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getContractCost(), thisAmount_big);
            	alterContractSumNoTax_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getContractCostNoTax(), thisAmountNoTax_big);
            	alterContractSumTax_big = CalcUtils.calcAdd(updateSupplySelects.get(0).getContractCostTax(), thisAmountTax_big);
            	//
            	updateSupplySelects.get(0).setThisAmount(thisAmount_big);
            	updateSupplySelects.get(0).setThisAmountNoTax(thisAmountNoTax_big);
            	updateSupplySelects.get(0).setThisAmountTax(thisAmountTax_big);
            	updateSupplySelects.get(0).setAlterContractSum(alterContractSum_big);
            	updateSupplySelects.get(0).setAlterContractSumNoTax(alterContractSumNoTax_big);
            	updateSupplySelects.get(0).setAlterContractSumTax(alterContractSumTax_big);
            	flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupplySelects.get(0));
        	}
        }
        flag = zxCtEqContrAlterItemResoureMapper.insert(zxCtEqContrAlterItemResoure);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtEqContrAlterItemResoure);
        }
    }
    
    //多期=update补充协议的主表
    private int addUpdateZxCtEqContrSupplyForMore(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure,ZxCtEqContrSupply updateSupplySelects,List<ZxCtEqContrSupply> suppliesList) {
    	int flag = 0;
    	if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {//变更类型是修改的时候，计算规则变了
    		ZxCtEqContrItem contrItemSelect = zxCtEqContrItemMapper.selectByPrimaryKey(zxCtEqContrAlterItemResoure.getContrItemID());
    		if(contrItemSelect != null && StrUtil.isNotEmpty(contrItemSelect.getZxCtEqContrItemId())) {
    			if(contrItemSelect.getAlterAmt() ==null || contrItemSelect.getAlterAmt().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmt().equals(new BigDecimal("0.000000"))) {
    				contrItemSelect.setAlterAmt(contrItemSelect.getContractSum());
    			}else {
    				if(contrItemSelect.getContractSum() ==null || contrItemSelect.getContractSum().equals(new BigDecimal("0"))|| contrItemSelect.getContractSum().equals(new BigDecimal("0.000000"))) {
        				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
        			}else {
        				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
        			}
    			}
    			if(contrItemSelect.getAlterAmtNoTax() ==null || contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0.000000"))) {
    				contrItemSelect.setAlterAmtNoTax(contrItemSelect.getContractSumNoTax());
    			}else {
    				if(contrItemSelect.getContractSumNoTax() ==null || contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0.000000"))) {
        				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
        			}else {
        				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
        			}
    			}
    			//本期含税增减金额(万元)thisAmount = thisAmount+(zxCtEqContrAlterItemResoure.getAlterAmt() - contrItemSelect.getAlterAmt())/10000
    			updateSupplySelects.setThisAmount(CalcUtils.calcAdd(updateSupplySelects.getThisAmount(), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), contrItemSelect.getAlterAmt()), new BigDecimal("10000.000000"),6)));
    			//本期不含税增减金额(万元)thisAmountNoTax = thisAmountNoTax+(zxCtEqContrAlterItemResoure.getAlterAmtNoTax() - contrItemSelect.getAlterAmtNoTax())/10000
    			updateSupplySelects.setThisAmountNoTax(CalcUtils.calcAdd(updateSupplySelects.getThisAmountNoTax(), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), contrItemSelect.getAlterAmtNoTax()), new BigDecimal("10000.000000"),6)));
    			//本期增减税额(万元)thisAmountTax = thisAmountTax+(zxCtEqContrAlterItemResoure.getAlterAmtTax() - contrItemSelect.getAlterAmtTax())/10000
    			updateSupplySelects.setThisAmountTax(CalcUtils.calcAdd(updateSupplySelects.getThisAmountTax(), CalcUtils.calcSubtract(updateSupplySelects.getThisAmount(), updateSupplySelects.getThisAmountNoTax())));
    		}
    	}else {
    		//本期含税增减金额(万元)thisAmount = thisAmount_big + AlterAmt_small
    		//本期不含税增减金额(万元)thisAmountNoTax
    		//本期增减税额(万元)thisAmountTax
    		updateSupplySelects.setThisAmount(CalcUtils.calcAdd(updateSupplySelects.getThisAmount(), CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6)));
    		updateSupplySelects.setThisAmountNoTax(CalcUtils.calcAdd(updateSupplySelects.getThisAmountNoTax(),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6)));
    		updateSupplySelects.setThisAmountTax(CalcUtils.calcAdd(updateSupplySelects.getThisAmountTax(),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtTax(), new BigDecimal("10000.000000"),6)));
    	}
    	

    	BigDecimal alterContractSum_before = new BigDecimal("0");
    	BigDecimal alterContractSumNoTax_before = new BigDecimal("0");
    	BigDecimal alterContractSumTax_before = new BigDecimal("0");
    	for (ZxCtEqContrSupply sup : suppliesList) {
    		alterContractSum_before = CalcUtils.calcAdd(alterContractSum_before, sup.getAlterContractSum());
    		alterContractSumNoTax_before = CalcUtils.calcAdd(alterContractSumNoTax_before, sup.getAlterContractSumNoTax());
    		alterContractSumTax_before = CalcUtils.calcAdd(alterContractSumTax_before, sup.getAlterContractSumTax());
    	}
    	//==第2次变更后含税金额(万元) = 第一次变更后含税金额(万元)+第二次本期
    	//变更后含税金额(万元)alterContractSum = alterContractSum_before + contrSupplies.get(0).thisAmount
    	//变更后不含税金额(万元)alterContractSumNoTax = alterContractSumNoTax_before + thisAmountNoTax
    	//变更后税额(万元)alterContractSumTax = alterContractSumTax_before + thisAmountTax
    	updateSupplySelects.setAlterContractSum(CalcUtils.calcAdd(alterContractSum_before, updateSupplySelects.getThisAmount()));
    	updateSupplySelects.setAlterContractSumNoTax(CalcUtils.calcAdd(alterContractSumNoTax_before, updateSupplySelects.getThisAmountNoTax()));
    	updateSupplySelects.setAlterContractSumTax(CalcUtils.calcAdd(alterContractSumTax_before, updateSupplySelects.getThisAmountTax()));

    	flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupplySelects);
    	return flag;
    }

    @Override
    public ResponseEntity updateZxCtEqContrAlterItemResoure(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtEqContrAlterItemResoure dbZxCtEqContrAlterItemResoure = zxCtEqContrAlterItemResoureMapper.selectByPrimaryKey(zxCtEqContrAlterItemResoure.getZxCtEqContrAlterItemResoureId());
        if (dbZxCtEqContrAlterItemResoure != null && StrUtil.isNotEmpty(dbZxCtEqContrAlterItemResoure.getZxCtEqContrAlterItemResoureId())) {
            if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {//修改
            	 //同一源清单不能被多行修改
                ZxCtEqContrAlterItemResoure alterItemResoure = new ZxCtEqContrAlterItemResoure();
                alterItemResoure.setAlterType("2");
                alterItemResoure.setZxEqResCategoryId(zxCtEqContrAlterItemResoure.getZxEqResCategoryId());
                alterItemResoure.setContrItemID(zxCtEqContrAlterItemResoure.getContrItemID());
                alterItemResoure.setContrAlterID(zxCtEqContrAlterItemResoure.getContrAlterID());
                alterItemResoure.setZxCtEqContrAlterItemResoureId(dbZxCtEqContrAlterItemResoure.getZxCtEqContrAlterItemResoureId());
                List<ZxCtEqContrAlterItemResoure> alterItemResoureList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItemResoure);
                if(alterItemResoureList != null && alterItemResoureList.size() >0) {
                	 return repEntity.layerMessage("no", "该设备已经存在，请重新选择！");
                }
            }
        	BigDecimal thisAmount_b = new BigDecimal("0.00000");
        	BigDecimal thisAmountNoTax_b = new BigDecimal("0.00000");
        	BigDecimal thisAmountTax_b = new BigDecimal("0.00000");

        	
        	if(StrUtil.equals(dbZxCtEqContrAlterItemResoure.getAlterType(), "2")) {
        		ZxCtEqContrItem contrItemSelect = zxCtEqContrItemMapper.selectByPrimaryKey(dbZxCtEqContrAlterItemResoure.getContrItemID());
            	if(contrItemSelect != null && StrUtil.isNotEmpty(contrItemSelect.getZxCtEqContrItemId())) {
            		if(contrItemSelect.getAlterAmt() ==null || contrItemSelect.getAlterAmt().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmt().equals(new BigDecimal("0.000000"))) {
        				contrItemSelect.setAlterAmt(contrItemSelect.getContractSum());
        			}else {
        				if(contrItemSelect.getContractSum() ==null || contrItemSelect.getContractSum().equals(new BigDecimal("0"))|| contrItemSelect.getContractSum().equals(new BigDecimal("0.000000"))) {
            				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
            			}else {
            				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
            			}
        			}
        			if(contrItemSelect.getAlterAmtNoTax() ==null || contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0.000000"))) {
        				contrItemSelect.setAlterAmtNoTax(contrItemSelect.getContractSumNoTax());
        			}else {
        				if(contrItemSelect.getContractSumNoTax() ==null || contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0.000000"))) {
            				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
            			}else {
            				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
            			}
        			}
            		
            	}
        		thisAmount_b = CalcUtils.calcDivide(CalcUtils.calcSubtract(dbZxCtEqContrAlterItemResoure.getAlterAmt(), contrItemSelect.getAlterAmt()), new BigDecimal("10000.000000"),6);
            	thisAmountNoTax_b = CalcUtils.calcDivide(CalcUtils.calcSubtract(dbZxCtEqContrAlterItemResoure.getAlterAmtNoTax(), contrItemSelect.getAlterAmtNoTax()), new BigDecimal("10000.000000"),6);
        	}else if(StrUtil.equals(dbZxCtEqContrAlterItemResoure.getAlterType(), "1")) {
        		thisAmount_b = CalcUtils.calcDivide(CalcUtils.calcMultiply(dbZxCtEqContrAlterItemResoure.getAlterTrrm(), dbZxCtEqContrAlterItemResoure.getAlterPrice()), new BigDecimal("10000.000000"),6);
            	thisAmountNoTax_b = CalcUtils.calcDivide(CalcUtils.calcMultiply(dbZxCtEqContrAlterItemResoure.getAlterTrrm(), dbZxCtEqContrAlterItemResoure.getAlterPriceNoTax()), new BigDecimal("10000.000000"),6);
        	}
        	thisAmountTax_b = CalcUtils.calcSubtract(thisAmount_b,thisAmountNoTax_b);
        	
        	
        	// 设备分类基础表中的parentID
           dbZxCtEqContrAlterItemResoure.setParentID(zxCtEqContrAlterItemResoure.getParentID());
           // 设备大类名称
           dbZxCtEqContrAlterItemResoure.setCatParentName(zxCtEqContrAlterItemResoure.getCatParentName());
           // 设备分类基础表中的ID
           dbZxCtEqContrAlterItemResoure.setZxEqResCategoryId(zxCtEqContrAlterItemResoure.getZxEqResCategoryId());
           // 设备编码
           dbZxCtEqContrAlterItemResoure.setCatCode(zxCtEqContrAlterItemResoure.getCatCode());
           // 设备名称
           dbZxCtEqContrAlterItemResoure.setCatName(zxCtEqContrAlterItemResoure.getCatName());
           // 单位
           dbZxCtEqContrAlterItemResoure.setUnit(zxCtEqContrAlterItemResoure.getUnit());
           // 型号
           dbZxCtEqContrAlterItemResoure.setSpec(zxCtEqContrAlterItemResoure.getSpec());
           // 数量
           dbZxCtEqContrAlterItemResoure.setQty(zxCtEqContrAlterItemResoure.getQty());
           // 含税合同单价（元/台）
           dbZxCtEqContrAlterItemResoure.setPrice(zxCtEqContrAlterItemResoure.getPrice());
           // 不含税合同单价（元/台)
           dbZxCtEqContrAlterItemResoure.setPriceNoTax(zxCtEqContrAlterItemResoure.getPriceNoTax());
           // 含税合同金额（元）
           dbZxCtEqContrAlterItemResoure.setContractSum(zxCtEqContrAlterItemResoure.getContractSum());
           // 不含税税额
           dbZxCtEqContrAlterItemResoure.setContractSumTax(zxCtEqContrAlterItemResoure.getContractSumTax());
           // 不含税合同金额（元）
           dbZxCtEqContrAlterItemResoure.setContractSumNoTax(zxCtEqContrAlterItemResoure.getContractSumNoTax());
           // 备注
           dbZxCtEqContrAlterItemResoure.setRemark(zxCtEqContrAlterItemResoure.getRemark());
           // 计划开始时间
           dbZxCtEqContrAlterItemResoure.setPlanStartDate(zxCtEqContrAlterItemResoure.getPlanStartDate());
           // 计划结束时间
           dbZxCtEqContrAlterItemResoure.setPlanEndDate(zxCtEqContrAlterItemResoure.getPlanEndDate());
           // 租赁开始时间
           dbZxCtEqContrAlterItemResoure.setActualStartDate(zxCtEqContrAlterItemResoure.getActualStartDate());
           // 租赁结束时间
           dbZxCtEqContrAlterItemResoure.setActualEndDate(zxCtEqContrAlterItemResoure.getActualEndDate());
           // 租期(台班)
           dbZxCtEqContrAlterItemResoure.setPp10(zxCtEqContrAlterItemResoure.getPp10());
           // 合同明细id
           dbZxCtEqContrAlterItemResoure.setContrItemID(zxCtEqContrAlterItemResoure.getContrItemID());
           // 变更日期
           dbZxCtEqContrAlterItemResoure.setChangeDate(zxCtEqContrAlterItemResoure.getChangeDate());
           // 租赁单位
           dbZxCtEqContrAlterItemResoure.setRentUnit(zxCtEqContrAlterItemResoure.getRentUnit());
           // 变更后租赁开始时间
           dbZxCtEqContrAlterItemResoure.setAlterRentStartDate(zxCtEqContrAlterItemResoure.getAlterRentStartDate());
           // 变更后租赁结束时间
           dbZxCtEqContrAlterItemResoure.setAlterRentEndDate(zxCtEqContrAlterItemResoure.getAlterRentEndDate());
           // 0
           dbZxCtEqContrAlterItemResoure.setContrTrrm(zxCtEqContrAlterItemResoure.getContrTrrm());
           // 变更类型
           dbZxCtEqContrAlterItemResoure.setAlterType(zxCtEqContrAlterItemResoure.getAlterType());
           // 0
           dbZxCtEqContrAlterItemResoure.setIsNetPrice(zxCtEqContrAlterItemResoure.getIsNetPrice());
           // 0
           dbZxCtEqContrAlterItemResoure.setRequestEdit(zxCtEqContrAlterItemResoure.getRequestEdit());
           // 变更后数量
           dbZxCtEqContrAlterItemResoure.setAlterTrrm(zxCtEqContrAlterItemResoure.getAlterTrrm());
           // 变更后含税单价（元/台）
           dbZxCtEqContrAlterItemResoure.setAlterPrice(zxCtEqContrAlterItemResoure.getAlterPrice());
           // 税率（%）
           dbZxCtEqContrAlterItemResoure.setTaxRate(zxCtEqContrAlterItemResoure.getTaxRate());
           
           
           //变更后含税金额(元)alterAmt=变更后数量alterTrrm*变更后含税单价(元)alterPrice
           dbZxCtEqContrAlterItemResoure.setAlterAmt(CalcUtils.calcMultiply(zxCtEqContrAlterItemResoure.getAlterTrrm(), zxCtEqContrAlterItemResoure.getAlterPrice()));
           //变更后数量alterTrrm
           //变更后含税单价(元)alterPrice
           BigDecimal alterPriceNoTax = new BigDecimal("0");//变更后不含税单价(元)alterPriceNoTax = 变更后含税单价(元)alterPrice/(1+税率/100)
           BigDecimal alterAmtNoTax = new BigDecimal("0");//变更后不含税金额(元)alterAmtNoTax = 含税金额/(1+税率/100)
           if(StrUtil.isNotEmpty(zxCtEqContrAlterItemResoure.getTaxRate())) {
           	alterPriceNoTax = CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrAlterItemResoure.getTaxRate()), new BigDecimal("100"))));
           	alterAmtNoTax = CalcUtils.calcDivide(dbZxCtEqContrAlterItemResoure.getAlterAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrAlterItemResoure.getTaxRate()), new BigDecimal("100"))));
           }
           dbZxCtEqContrAlterItemResoure.setAlterPriceNoTax(alterPriceNoTax);//变更后不含税单价(元)alterPriceNoTax
           dbZxCtEqContrAlterItemResoure.setAlterAmtNoTax(alterAmtNoTax);//变更后不含税金额(元)alterAmtNoTax
           dbZxCtEqContrAlterItemResoure.setAlterAmtTax(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), zxCtEqContrAlterItemResoure.getAlterAmtNoTax()));//变更后税额(元)alterAmtTax = 变更后含税金额(元)alterAmt - 变更后不含税金额(元)alterAmtNoTax
           // 共通
           dbZxCtEqContrAlterItemResoure.setModifyUserInfo(userKey, realName);

           //update补充协议的主表===先判断是首次还是多期的补充协议
           ZxCtEqContrSupply supplySelect = new ZxCtEqContrSupply();
           supplySelect.setContrAlterID(zxCtEqContrAlterItemResoure.getContrAlterID());
           List<ZxCtEqContrSupply> updateSupplySelects = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supplySelect);
           if(updateSupplySelects != null && updateSupplySelects.size() >0) {
        	   ZxCtEqContrSupply supply = new ZxCtEqContrSupply();
        	   supply.setApih5FlowStatus("2");
        	   supply.setContractID(updateSupplySelects.get(0).getContractID());
        	   List<ZxCtEqContrSupply> suppliesList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supply);
        	   if(suppliesList != null && suppliesList.size() >0) {
        		   flag = updateZxCtEqContrSupplyForMore(zxCtEqContrAlterItemResoure,updateSupplySelects.get(0),suppliesList,thisAmount_b,thisAmountNoTax_b,thisAmountTax_b);
        	   }else {
        		   flag = updateZxCtEqContrSupplyForOne(zxCtEqContrAlterItemResoure,updateSupplySelects.get(0),thisAmount_b,thisAmountNoTax_b,thisAmountTax_b);
        	   }
           }
           flag = zxCtEqContrAlterItemResoureMapper.updateByPrimaryKey(dbZxCtEqContrAlterItemResoure);  
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtEqContrAlterItemResoure);
        }
    }

    
  //只有一期==update补充协议的主表
    private int updateZxCtEqContrSupplyForOne(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure,ZxCtEqContrSupply updateSupply,BigDecimal thisAmount_b,BigDecimal thisAmountNoTax_b,BigDecimal thisAmountTax_b) {
    	int flag = 0;
    	if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {
    		//修改类，计算规则变了
    		//本期含税增减金额(万元)thisAmount = 总协议原来的本期-修改的这条明细之前的本期+(修改的这条明细之后的本期/10000)
    		updateSupply.setThisAmount(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmount(), thisAmount_b), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), zxCtEqContrAlterItemResoure.getContractSum()), new BigDecimal("10000.000000"),6)));
    		updateSupply.setThisAmountNoTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountNoTax(), thisAmountNoTax_b), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), zxCtEqContrAlterItemResoure.getContractSumNoTax()), new BigDecimal("10000.000000"),6)));
    		updateSupply.setThisAmountTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountTax(), thisAmountTax_b), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtTax(), zxCtEqContrAlterItemResoure.getContractSumTax()), new BigDecimal("10000.000000"),6)));
    	}else if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "1")){
    		//本期含税增减金额(万元)thisAmount = thisAmount_big + AlterAmt_small
    		//本期不含税增减金额(万元)thisAmountNoTax
    		//本期增减税额(万元)thisAmountTax
    		updateSupply.setThisAmount(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmount(), thisAmount_b), CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6)));
    		updateSupply.setThisAmountNoTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountNoTax(), thisAmountNoTax_b),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6)));
    		updateSupply.setThisAmountTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountTax(), thisAmountTax_b),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtTax(), new BigDecimal("10000.000000"),6)));
    	}
    	//变更后含税金额(万元)alterContractSum = contrSupplies.get(0).contractCost+contrSupplies.get(0).thisAmount
    	//变更后不含税金额(万元)alterContractSumNoTax = contractCostNoTax+thisAmountNoTax
    	//变更后税额(万元)alterContractSumTax = contractCostTax+thisAmountTax
    	updateSupply.setAlterContractSum(CalcUtils.calcAdd(updateSupply.getContractCost(), updateSupply.getThisAmount()));
    	updateSupply.setAlterContractSumNoTax(CalcUtils.calcAdd(updateSupply.getContractCostNoTax(), updateSupply.getThisAmountNoTax()));
    	updateSupply.setAlterContractSumTax(CalcUtils.calcAdd(updateSupply.getContractCostTax(), updateSupply.getThisAmountTax()));
    	flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupply);
    	return flag;
    }
    
    //多期=update补充协议的主表
    private int updateZxCtEqContrSupplyForMore(ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure,ZxCtEqContrSupply updateSupply,List<ZxCtEqContrSupply> suppliesList,BigDecimal thisAmount_b,BigDecimal thisAmountNoTax_b,BigDecimal thisAmountTax_b) {
    	int flag = 0;
     	if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {
     		//变更类型是修改的时候，计算规则变了
     		//本期含税增减金额(万元)thisAmount = 总协议原来的本期-修改的这条明细之前的本期+(修改的这条明细之后的本期/10000)
     		ZxCtEqContrItem contrItemSelect = zxCtEqContrItemMapper.selectByPrimaryKey(zxCtEqContrAlterItemResoure.getContrItemID());
    		if(contrItemSelect != null && StrUtil.isNotEmpty(contrItemSelect.getZxCtEqContrItemId())) {
    		//这个逻辑可能有点问题，28号看
    			//1.情况一，  只有原始的合同值，没有变更的金额值时=== 变更的-原始的
    			//2.情况二，  第一期新增过来的值，没有原始的合同值，有变更的金额值时 == 变更的-变更的
    			//3.情况三，   既有原始的合同值也有变更的合同值  == 变更的-变更的
    			if(contrItemSelect.getAlterAmt() ==null || contrItemSelect.getAlterAmt().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmt().equals(new BigDecimal("0.000000"))) {
    				contrItemSelect.setAlterAmt(contrItemSelect.getContractSum());
    			}else {
    				if(contrItemSelect.getContractSum() ==null || contrItemSelect.getContractSum().equals(new BigDecimal("0"))|| contrItemSelect.getContractSum().equals(new BigDecimal("0.000000"))) {
        				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
        			}else {
        				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
        			}
    			}
    			if(contrItemSelect.getAlterAmtNoTax() ==null || contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0.000000"))) {
    				contrItemSelect.setAlterAmtNoTax(contrItemSelect.getContractSumNoTax());
    			}else {
    				if(contrItemSelect.getContractSumNoTax() ==null || contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0.000000"))) {
        				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
        			}else {
        				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
        			}
    			}
    			updateSupply.setThisAmount(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmount(), thisAmount_b), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), contrItemSelect.getAlterAmt()), new BigDecimal("10000.000000"),6)));
        		updateSupply.setThisAmountNoTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountNoTax(), thisAmountNoTax_b), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), contrItemSelect.getAlterAmtNoTax()), new BigDecimal("10000.000000"),6)));
        		updateSupply.setThisAmountTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountTax(), thisAmountTax_b), CalcUtils.calcSubtract(updateSupply.getThisAmount(), updateSupply.getThisAmountNoTax())));
    		}
    	}else if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "1")) {
    		//本期含税增减金额(万元)thisAmount = thisAmount_big + AlterAmt_small
    		//本期不含税增减金额(万元)thisAmountNoTax
    		//本期增减税额(万元)thisAmountTax
    		updateSupply.setThisAmount(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmount(), thisAmount_b), CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6)));
    		updateSupply.setThisAmountNoTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountNoTax(), thisAmountNoTax_b),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6)));
    		updateSupply.setThisAmountTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(updateSupply.getThisAmountTax(), thisAmountTax_b),CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtTax(), new BigDecimal("10000.000000"),6)));
    	}

    	BigDecimal alterContractSum_before = new BigDecimal("0.0000000");
    	BigDecimal alterContractSumNoTax_before = new BigDecimal("0.0000000");
    	BigDecimal alterContractSumTax_before = new BigDecimal("0.0000000");
    	for (ZxCtEqContrSupply sup : suppliesList) {
    		alterContractSum_before = CalcUtils.calcAdd(alterContractSum_before, sup.getAlterContractSum());
    		alterContractSumNoTax_before = CalcUtils.calcAdd(alterContractSumNoTax_before, sup.getAlterContractSumNoTax());
    		alterContractSumTax_before = CalcUtils.calcAdd(alterContractSumTax_before, sup.getAlterContractSumTax());
    	}
    	//==第2次变更后含税金额(万元) = 第一次变更后含税金额(万元)+第二次本期
    	//变更后含税金额(万元)alterContractSum = alterContractSum_before + contrSupplies.get(0).thisAmount
    	//变更后不含税金额(万元)alterContractSumNoTax = alterContractSumNoTax_before + thisAmountNoTax
    	//变更后税额(万元)alterContractSumTax = alterContractSumTax_before + thisAmountTax
    	updateSupply.setAlterContractSum(CalcUtils.calcAdd(alterContractSum_before, updateSupply.getThisAmount()));
    	updateSupply.setAlterContractSumNoTax(CalcUtils.calcAdd(alterContractSumNoTax_before, updateSupply.getThisAmountNoTax()));
    	updateSupply.setAlterContractSumTax(CalcUtils.calcAdd(alterContractSumTax_before, updateSupply.getThisAmountTax()));

    	flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupply);
    	return flag;
    }
    
    
    @Override
    public ResponseEntity batchDeleteUpdateZxCtEqContrAlterItemResoure(List<ZxCtEqContrAlterItemResoure> zxCtEqContrAlterItemResoureList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtEqContrAlterItemResoureList != null && zxCtEqContrAlterItemResoureList.size() > 0) {
        	zxCtEqContrAlterItemResoureList.get(0).setModifyUserInfo(userKey, realName);
        	flag = zxCtEqContrAlterItemResoureMapper.batchDeleteUpdateZxCtEqContrAlterItemResoure(zxCtEqContrAlterItemResoureList, zxCtEqContrAlterItemResoureList.get(0));
        
        
        	//一共分为4个场景
        		ZxCtEqContrSupply supplySelect = new ZxCtEqContrSupply();
                supplySelect.setContrAlterID(zxCtEqContrAlterItemResoureList.get(0).getContrAlterID());
                List<ZxCtEqContrSupply> updateSupplySelects = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supplySelect);
                if(updateSupplySelects != null && updateSupplySelects.size() >0) {
             	   //1.判断这个补充协议下是否还有清单
                	ZxCtEqContrAlterItemResoure alterItemResoure = new ZxCtEqContrAlterItemResoure();
                	alterItemResoure.setContrAlterID(updateSupplySelects.get(0).getContrAlterID());
                	List<ZxCtEqContrAlterItemResoure> alterItemResoureList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItemResoure);
                	if(alterItemResoureList != null && alterItemResoureList.size() >0) {
                		//有
                		//2.判断是否是有多期审核通过的补充协议
                    	ZxCtEqContrSupply supply = new ZxCtEqContrSupply();
                    	supply.setApih5FlowStatus("2");
                    	supply.setContractID(updateSupplySelects.get(0).getContractID());
                    	List<ZxCtEqContrSupply> suppliesList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supply);
                    	if(suppliesList != null && suppliesList.size() >0) {
                    		//多期
                    		BigDecimal thisAmount = new BigDecimal("0");
                    		BigDecimal thisAmountNoTax = new BigDecimal("0");
                    		BigDecimal thisAmountTax = new BigDecimal("0");
                    		for (ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure : alterItemResoureList) {
                    			if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {//变更类型是修改的时候，计算规则变了
                    				ZxCtEqContrItem contrItemSelect = zxCtEqContrItemMapper.selectByPrimaryKey(zxCtEqContrAlterItemResoure.getContrItemID());
                    				if(contrItemSelect != null && StrUtil.isNotEmpty(contrItemSelect.getZxCtEqContrItemId())) {
                    					if(contrItemSelect.getAlterAmt() ==null || contrItemSelect.getAlterAmt().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmt().equals(new BigDecimal("0.000000"))) {
                    	    				contrItemSelect.setAlterAmt(contrItemSelect.getContractSum());
                    	    			}else {
                    	    				if(contrItemSelect.getContractSum() ==null || contrItemSelect.getContractSum().equals(new BigDecimal("0"))|| contrItemSelect.getContractSum().equals(new BigDecimal("0.000000"))) {
                    	        				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
                    	        			}else {
                    	        				contrItemSelect.setContractSum(contrItemSelect.getAlterAmt());
                    	        			}
                    	    			}
                    	    			if(contrItemSelect.getAlterAmtNoTax() ==null || contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getAlterAmtNoTax().equals(new BigDecimal("0.000000"))) {
                    	    				contrItemSelect.setAlterAmtNoTax(contrItemSelect.getContractSumNoTax());
                    	    			}else {
                    	    				if(contrItemSelect.getContractSumNoTax() ==null || contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0"))|| contrItemSelect.getContractSumNoTax().equals(new BigDecimal("0.000000"))) {
                    	        				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
                    	        			}else {
                    	        				contrItemSelect.setContractSumNoTax(contrItemSelect.getAlterAmtNoTax());
                    	        			}
                    	    			}
                    					//本期含税增减金额(万元)thisAmount = thisAmount+[alterAmt-contrItem.alterAmt)/10000]
                    					//本期不含税增减金额(万元)thisAmountNoTax = thisAmountNoTax+[(alterAmtNoTax-contrItem.alterAmtNoTax)/10000]
                    					//本期增减税额(万元)thisAmountTax = thisAmountTax+[(alterAmtTax-contrItem.alterAmtTax)/10000]
                    					thisAmount = CalcUtils.calcAdd(thisAmount, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), contrItemSelect.getAlterAmt()), new BigDecimal("10000.000000"),6));
                    					thisAmountNoTax = CalcUtils.calcAdd(thisAmountNoTax, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), contrItemSelect.getAlterAmtNoTax()), new BigDecimal("10000.000000"),6));
                    					thisAmountTax = CalcUtils.calcAdd(thisAmountTax, CalcUtils.calcSubtract(thisAmount, thisAmountNoTax));
                    				}
                    	    	}else if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "1")) {
                    	    		//本期含税增减金额(万元)thisAmount = 变更后数量alterTrrm*变更后含税单价(元)alterPrice
                    	    		//本期不含税增减金额(万元)thisAmountNoTax
                    	    		//本期增减税额(万元)thisAmountTax
                    	    		thisAmount = CalcUtils.calcAdd(thisAmount, CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6));
                    	    		thisAmountNoTax = CalcUtils.calcAdd(thisAmountNoTax,CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6));
                    	    		thisAmountTax = CalcUtils.calcAdd(thisAmountTax,CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtTax(), new BigDecimal("10000.000000"),6));
                    	    	}
                    			updateSupplySelects.get(0).setThisAmount(thisAmount);
                        		updateSupplySelects.get(0).setThisAmountNoTax(thisAmountNoTax);
                        		updateSupplySelects.get(0).setThisAmountTax(thisAmountTax);
                        		//第2次变更后含税金额(万元) = 第一次变更后含税金额(万元)+第二次本期
                            	//变更后含税金额(万元)alterContractSum = alterContractSum_before + contrSupplies.get(0).thisAmount
                            	//变更后不含税金额(万元)alterContractSumNoTax = alterContractSumNoTax_before + thisAmountNoTax
                            	//变更后税额(万元)alterContractSumTax = alterContractSumTax_before + thisAmountTax
                        		BigDecimal alterContractSum_before = new BigDecimal("0.0000000");
                            	BigDecimal alterContractSumNoTax_before = new BigDecimal("0.0000000");
                            	BigDecimal alterContractSumTax_before = new BigDecimal("0.0000000");
                            	for (ZxCtEqContrSupply sup : suppliesList) {
                            		alterContractSum_before = CalcUtils.calcAdd(alterContractSum_before, sup.getAlterContractSum());
                            		alterContractSumNoTax_before = CalcUtils.calcAdd(alterContractSumNoTax_before, sup.getAlterContractSumNoTax());
                            		alterContractSumTax_before = CalcUtils.calcAdd(alterContractSumTax_before, sup.getAlterContractSumTax());
                            	}
                    			updateSupplySelects.get(0).setAlterContractSum(CalcUtils.calcAdd(alterContractSum_before, thisAmount));
                    	    	updateSupplySelects.get(0).setAlterContractSumNoTax(CalcUtils.calcAdd(alterContractSumNoTax_before, thisAmountNoTax));
                    	    	updateSupplySelects.get(0).setAlterContractSumTax(CalcUtils.calcAdd(alterContractSumTax_before, thisAmountTax));
                    	    	flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupplySelects.get(0));
							}
                    	}else {
                    		//首期===下有多条清单
                    		BigDecimal thisAmount = new BigDecimal("0");
                    		BigDecimal thisAmountNoTax = new BigDecimal("0");
                    		BigDecimal thisAmountTax = new BigDecimal("0");
                    		for (ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure : alterItemResoureList) {
                    			if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "2")) {
                    				//变更类型是修改的时候，计算规则变了
                    	    		//本期含税增减金额(万元)thisAmount = thisAmount+[变更后含税金额alterAmt-含税合同金额contractSum)/10000]
                    	    		//本期不含税增减金额(万元)thisAmountNoTax = thisAmountNoTax+[(alterAmtNoTax-contractSumNoTax)/10000]
                    	    		//本期增减税额(万元)thisAmountTax = thisAmountTax+[(alterAmtTax-contractSumTax)/10000]
                    				thisAmount = CalcUtils.calcAdd(thisAmount, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmt(), zxCtEqContrAlterItemResoure.getContractSum()), new BigDecimal("10000.000000"),6));
                    				thisAmountNoTax = CalcUtils.calcAdd(thisAmountNoTax, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), zxCtEqContrAlterItemResoure.getContractSumNoTax()), new BigDecimal("10000.000000"),6));
                    				thisAmountTax = CalcUtils.calcAdd(thisAmountTax, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtEqContrAlterItemResoure.getAlterAmtTax(), zxCtEqContrAlterItemResoure.getContractSumTax()), new BigDecimal("10000.000000"),6));
                    	    	}else if(StrUtil.equals(zxCtEqContrAlterItemResoure.getAlterType(), "1")){
                    	    		//本期含税增减金额(万元)thisAmount = 变更后数量alterTrrm*变更后含税单价(元)alterPrice
                    	    		//本期不含税增减金额(万元)thisAmountNoTax
                    	    		//本期增减税额(万元)thisAmountTax
                    	    		thisAmount = CalcUtils.calcAdd(thisAmount, CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmt(), new BigDecimal("10000.000000"),6));
                    	    		thisAmountNoTax = CalcUtils.calcAdd(thisAmountNoTax,CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtNoTax(), new BigDecimal("10000.000000"),6));
                    	    		thisAmountTax = CalcUtils.calcAdd(thisAmountTax,CalcUtils.calcDivide(zxCtEqContrAlterItemResoure.getAlterAmtTax(), new BigDecimal("10000.000000"),6));
                    	    	}
                    			updateSupplySelects.get(0).setThisAmount(thisAmount);
                        		updateSupplySelects.get(0).setThisAmountNoTax(thisAmountNoTax);
                        		updateSupplySelects.get(0).setThisAmountTax(thisAmountTax);
                    	    	//变更后含税金额(万元)alterContractSum = contrSupplies.get(0).contractCost+contrSupplies.get(0).thisAmount
                    	    	//变更后不含税金额(万元)alterContractSumNoTax = contractCostNoTax+thisAmountNoTax
                    	    	//变更后税额(万元)alterContractSumTax = contractCostTax+thisAmountTax
                    			updateSupplySelects.get(0).setAlterContractSum(CalcUtils.calcAdd(updateSupplySelects.get(0).getContractCost(), thisAmount));
                    	    	updateSupplySelects.get(0).setAlterContractSumNoTax(CalcUtils.calcAdd(updateSupplySelects.get(0).getContractCostNoTax(), thisAmountNoTax));
                    	    	updateSupplySelects.get(0).setAlterContractSumTax(CalcUtils.calcAdd(updateSupplySelects.get(0).getContractCostTax(), thisAmountTax));
                    	    	flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupplySelects.get(0));
							}
                    	}
                	}else {
                		//没有
                		//2.判断是否是有多期审核通过的补充协议
                    	ZxCtEqContrSupply supply = new ZxCtEqContrSupply();
                    	supply.setApih5FlowStatus("2");
                    	supply.setContractID(updateSupplySelects.get(0).getContractID());
                    	List<ZxCtEqContrSupply> suppliesList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supply);
                    	if(suppliesList != null && suppliesList.size() >0) {
                    		//多期
                    		//本期的值为0
                    		//本期含税增减金额(万元)thisAmount 
                    		//本期不含税增减金额(万元)thisAmountNoTax
                    		//本期增减税额(万元)thisAmountTax
                    		updateSupplySelects.get(0).setThisAmount(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setThisAmountNoTax(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setThisAmountTax(new BigDecimal("0.00000"));
                    		//第2次变更后含税金额(万元) = 第一次变更后含税金额(万元)+第二次本期
                        	//变更后含税金额(万元)alterContractSum = alterContractSum_before + contrSupplies.get(0).thisAmount
                        	//变更后不含税金额(万元)alterContractSumNoTax = alterContractSumNoTax_before + thisAmountNoTax
                        	//变更后税额(万元)alterContractSumTax = alterContractSumTax_before + thisAmountTax
                    		BigDecimal alterContractSum_before = new BigDecimal("0.0000000");
                        	BigDecimal alterContractSumNoTax_before = new BigDecimal("0.0000000");
                        	BigDecimal alterContractSumTax_before = new BigDecimal("0.0000000");
                        	for (ZxCtEqContrSupply sup : suppliesList) {
                        		alterContractSum_before = CalcUtils.calcAdd(alterContractSum_before, sup.getAlterContractSum());
                        		alterContractSumNoTax_before = CalcUtils.calcAdd(alterContractSumNoTax_before, sup.getAlterContractSumNoTax());
                        		alterContractSumTax_before = CalcUtils.calcAdd(alterContractSumTax_before, sup.getAlterContractSumTax());
                        	}
                        	updateSupplySelects.get(0).setAlterContractSum(alterContractSum_before);
                        	updateSupplySelects.get(0).setAlterContractSumNoTax(alterContractSumNoTax_before);
                        	updateSupplySelects.get(0).setAlterContractSumTax(alterContractSumTax_before);

                    		
                    		flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupplySelects.get(0));
                    	}else {
                    		//一期
                    		//以下值都为0
                    		//本期含税增减金额(万元)thisAmount 
                    		//本期不含税增减金额(万元)thisAmountNoTax
                    		//本期增减税额(万元)thisAmountTax
                    		//变更后含税金额(万元)alterContractSum
                    		//变更后不含税金额(万元)alterContractSumNoTax
                    		//变更后税额(万元)alterContractSumTax
                    		updateSupplySelects.get(0).setThisAmount(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setThisAmountNoTax(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setThisAmountTax(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setAlterContractSum(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setAlterContractSumNoTax(new BigDecimal("0.00000"));
                    		updateSupplySelects.get(0).setAlterContractSumTax(new BigDecimal("0.00000"));
                    		flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(updateSupplySelects.get(0));
                    	}
                	}
                }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtEqContrAlterItemResoureList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
