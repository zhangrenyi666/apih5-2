package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtEqContrItemMapper;
import com.apih5.mybatis.dao.ZxEqEquipLimitPriceItemMapper;
import com.apih5.mybatis.dao.ZxEqEquipLimitPriceMapper;
import com.apih5.mybatis.dao.ZxEqResCategoryMapper;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPrice;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPriceItem;
import com.apih5.mybatis.pojo.ZxEqResCategory;
import com.apih5.service.ZxCtEqContrItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxCtEqContrItemService")
public class ZxCtEqContrItemServiceImpl implements ZxCtEqContrItemService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtEqContrItemMapper zxCtEqContrItemMapper;
    
    @Autowired(required = true)
    private ZxEqEquipLimitPriceItemMapper zxEqEquipLimitPriceItemMapper;
    
    @Autowired(required = true)
    private ZxEqEquipLimitPriceMapper zxEqEquipLimitPriceMapper;
    
    @Autowired(required = true)
    private ZxEqResCategoryMapper zxEqResCategoryMapper;

    @Override
    public ResponseEntity getZxCtEqContrItemListByCondition(ZxCtEqContrItem zxCtEqContrItem) {
        if (zxCtEqContrItem == null) {
            zxCtEqContrItem = new ZxCtEqContrItem();
        }
        if(StrUtil.isEmpty(zxCtEqContrItem.getZxCtEqContrApplyId())) {
        	 return repEntity.okList(null, 0);
        }
        // ????????????
        PageHelper.startPage(zxCtEqContrItem.getPage(),zxCtEqContrItem.getLimit());
        // ????????????
        List<ZxCtEqContrItem> zxCtEqContrItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(zxCtEqContrItem);
        // ??????????????????
        PageInfo<ZxCtEqContrItem> p = new PageInfo<>(zxCtEqContrItemList);

        return repEntity.okList(zxCtEqContrItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtEqContrItemDetail(ZxCtEqContrItem zxCtEqContrItem) {
        if (zxCtEqContrItem == null) {
            zxCtEqContrItem = new ZxCtEqContrItem();
        }
        // ????????????
        ZxCtEqContrItem dbZxCtEqContrItem = zxCtEqContrItemMapper.selectByPrimaryKey(zxCtEqContrItem.getZxCtEqContrItemId());
        // ????????????
        if (dbZxCtEqContrItem != null) {
            return repEntity.ok(dbZxCtEqContrItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxEqResCategory category = zxEqResCategoryMapper.selectByPrimaryKey(zxCtEqContrItem.getParentID());
        if(category != null && StrUtil.isNotEmpty(category.getId())) {
        	zxCtEqContrItem.setCatParentName(category.getCatName());
        }
        zxCtEqContrItem.setZxCtEqContrItemId(UuidUtil.generate());
        zxCtEqContrItem.setZxCtEqContrApplyId(zxCtEqContrItem.getZxCtEqContrApplyId());
        // ?????????????????? = ??????????????????*????????????
        zxCtEqContrItem.setContractSum(CalcUtils.calcMultiply(zxCtEqContrItem.getPrice(), zxCtEqContrItem.getQty()));
        BigDecimal priceNoTax = new BigDecimal("0");// ?????????????????????
        BigDecimal contractSumNoTax = new BigDecimal("0");// ?????????????????????
        BigDecimal contractSumTax = new BigDecimal("0");// ??????
        if(StrUtil.isNotEmpty(zxCtEqContrItem.getTaxRate())) {
        	//????????????????????? = ??????????????????/(1+??????/100)
        	priceNoTax = CalcUtils.calcDivide(zxCtEqContrItem.getPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
        	//????????????????????? = ????????????/(1+??????/100)
        	contractSumNoTax = CalcUtils.calcDivide(zxCtEqContrItem.getContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
        	//??????(contractSumTax) = ?????????????????? - ?????????????????????
        	contractSumTax = CalcUtils.calcSubtract(zxCtEqContrItem.getContractSum(), contractSumNoTax);
        }
        zxCtEqContrItem.setPriceNoTax(priceNoTax);
        zxCtEqContrItem.setContractSumNoTax(contractSumNoTax);
        zxCtEqContrItem.setContractSumTax(contractSumTax);
        zxCtEqContrItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtEqContrItemMapper.insert(zxCtEqContrItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtEqContrItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtEqContrItem dbZxCtEqContrItem = zxCtEqContrItemMapper.selectByPrimaryKey(zxCtEqContrItem.getZxCtEqContrItemId());
        if (dbZxCtEqContrItem != null && StrUtil.isNotEmpty(dbZxCtEqContrItem.getZxCtEqContrItemId())) {
            // ???????????????????????????parentId
            dbZxCtEqContrItem.setParentID(zxCtEqContrItem.getParentID());
            // ??????????????????
            ZxEqResCategory category = zxEqResCategoryMapper.selectByPrimaryKey(zxCtEqContrItem.getParentID());
            if(category != null && StrUtil.isNotEmpty(category.getId())) {
            	dbZxCtEqContrItem.setCatParentName(category.getCatName());
            }
            // ?????????????????????????????????id
            dbZxCtEqContrItem.setZxEqResCategoryId(zxCtEqContrItem.getZxEqResCategoryId());
            // ????????????
            dbZxCtEqContrItem.setCatCode(zxCtEqContrItem.getCatCode());
            // ????????????
            dbZxCtEqContrItem.setCatName(zxCtEqContrItem.getCatName());
            // ??????
            dbZxCtEqContrItem.setSpec(zxCtEqContrItem.getSpec());
            // ????????????
            dbZxCtEqContrItem.setUnit(zxCtEqContrItem.getUnit());
            //????????????
            dbZxCtEqContrItem.setRentUnit(zxCtEqContrItem.getRentUnit());
            // ????????????
            dbZxCtEqContrItem.setQty(zxCtEqContrItem.getQty());
            // ??????????????????
            dbZxCtEqContrItem.setPrice(zxCtEqContrItem.getPrice());
            // ??????
            dbZxCtEqContrItem.setRemark(zxCtEqContrItem.getRemark());
            //????????????
            dbZxCtEqContrItem.setActualStartDate(zxCtEqContrItem.getActualStartDate());
            //????????????
            dbZxCtEqContrItem.setActualEndDate(zxCtEqContrItem.getActualEndDate());
            
            // ??????(%)
            dbZxCtEqContrItem.setTaxRate(zxCtEqContrItem.getTaxRate());
            // ?????????????????? = ??????????????????*????????????
            dbZxCtEqContrItem.setContractSum(CalcUtils.calcMultiply(zxCtEqContrItem.getPrice(), zxCtEqContrItem.getQty()));
            BigDecimal priceNoTax = new BigDecimal("0");// ?????????????????????
            BigDecimal contractSumNoTax = new BigDecimal("0");// ?????????????????????
            BigDecimal contractSumTax = new BigDecimal("0");// ??????
            if(StrUtil.isNotEmpty(zxCtEqContrItem.getTaxRate())) {
            	//????????????????????? = ??????????????????/(1+??????/100)
            	priceNoTax = CalcUtils.calcDivide(zxCtEqContrItem.getPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
            	//????????????????????? = ????????????/(1+??????/100)
            	contractSumNoTax = CalcUtils.calcDivide(dbZxCtEqContrItem.getContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
            	//??????(contractSumTax) = ?????????????????? - ?????????????????????
            	contractSumTax = CalcUtils.calcSubtract(dbZxCtEqContrItem.getContractSum(), contractSumNoTax);
            }
            dbZxCtEqContrItem.setPriceNoTax(priceNoTax);
            dbZxCtEqContrItem.setContractSumNoTax(contractSumNoTax);
            dbZxCtEqContrItem.setContractSumTax(contractSumTax);
            // ??????
            dbZxCtEqContrItem.setModifyUserInfo(userKey, realName);
           flag = zxCtEqContrItemMapper.updateByPrimaryKey(dbZxCtEqContrItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtEqContrItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtEqContrItem(List<ZxCtEqContrItem> zxCtEqContrItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtEqContrItemList != null && zxCtEqContrItemList.size() > 0) {
           ZxCtEqContrItem zxCtEqContrItem = new ZxCtEqContrItem();
           zxCtEqContrItem.setModifyUserInfo(userKey, realName);
           flag = zxCtEqContrItemMapper.batchDeleteUpdateZxCtEqContrItem(zxCtEqContrItemList, zxCtEqContrItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtEqContrItemList);
        }
    }

    @Override
    public ResponseEntity getZxCtEqContrItemForLimitPriceList(ZxCtEqContrItem zxCtEqContrItem) {
    	if(zxCtEqContrItem == null) {
    		zxCtEqContrItem = new ZxCtEqContrItem();
    	}
    	if(StrUtil.isEmpty(zxCtEqContrItem.getZxEqResCategoryId())) {
    		return repEntity.layerMessage("no", "????????????id?????????");
    	}
    	ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem = new ZxEqEquipLimitPriceItem();
    	zxEqEquipLimitPriceItem.setEquipID(zxCtEqContrItem.getZxEqResCategoryId());
    	zxEqEquipLimitPriceItem.setApih5FlowStatus("2");
    	zxEqEquipLimitPriceItem.setComID(zxCtEqContrItem.getComID());
    	// ????????????
    	PageHelper.startPage(zxEqEquipLimitPriceItem.getPage(),zxEqEquipLimitPriceItem.getLimit());
    	// ????????????
    	List<ZxEqEquipLimitPriceItem> zxEqEquipLimitPriceItemList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(zxEqEquipLimitPriceItem);
    	if(zxEqEquipLimitPriceItemList != null && zxEqEquipLimitPriceItemList.size() >0) {
    		for (ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem2 : zxEqEquipLimitPriceItemList) {
    			ZxEqEquipLimitPrice price = zxEqEquipLimitPriceMapper.selectByPrimaryKey(zxEqEquipLimitPriceItem2.getMainID());
    			if(price != null && StrUtil.isNotEmpty(price.getId())) {
    				zxEqEquipLimitPriceItem2.setOrgName(price.getOrgName());
    				zxEqEquipLimitPriceItem2.setPeriod(price.getPeriod());
    			}
    		}
    	}
    	// ??????????????????
    	PageInfo<ZxEqEquipLimitPriceItem> p = new PageInfo<>(zxEqEquipLimitPriceItemList);

    	return repEntity.okList(zxEqEquipLimitPriceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity importZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem) {
    	if(zxCtEqContrItem == null){
    		zxCtEqContrItem = new ZxCtEqContrItem();
    	}
    	// ??????????????????
    	if (zxCtEqContrItem.getZxErpFileList() == null || zxCtEqContrItem.getZxErpFileList().size() == 0) {
    		return repEntity.layerMessage("no", "??????????????????!");
    	}
    	if(StrUtil.isEmpty(zxCtEqContrItem.getZxCtEqContrApplyId())) {
    		return repEntity.layerMessage("no", "??????????????????????????????????????????!");
    	}
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	List<ZxCtEqContrItem> returnWorksList = new ArrayList<>();
    	String filePath = Apih5Properties.getFilePath() + zxCtEqContrItem.getZxErpFileList().get(0).getRelativeUrl();
    	int flag = 0;
    	String workNo = "";
    	int i = 0;
    	try {
    		ExcelReader reader = ExcelUtil.getReader(filePath);
    		List<Map<String,Object>> readAll = reader.readAll();
    		if (readAll == null || readAll.size() == 0) {
    			return repEntity.layerMessage("no", "??????????????????????????????!");
    		}
    		if(readAll != null && readAll.size() >0) {
    			for(Map<String,Object> map:readAll) {
    				JSONObject json = new JSONObject(map);
    				//????????????  ????????????  ???????????? ??????   ??????   ??????????????????    ??????(%)
    				ZxCtEqContrItem contrItem = new ZxCtEqContrItem();
    				contrItem.setZxCtEqContrItemId(UuidUtil.generate());
    				contrItem.setZxCtEqContrApplyId(zxCtEqContrItem.getZxCtEqContrApplyId());
    				if (StrUtil.isEmpty(json.getStr("????????????"))) {
    					continue;
    				}
    				contrItem.setCatCode(json.getStr("????????????"));
    				workNo = contrItem.getCatCode();
    				if(StrUtil.isNotEmpty(json.getStr("????????????"))){
    					contrItem.setCatName(json.getStr("????????????"));
    				}
    				if(StrUtil.isNotEmpty(json.getStr("????????????"))){
    					contrItem.setSpec(json.getStr("????????????"));
    				}
    				if(StrUtil.isNotEmpty(json.getStr("??????"))){
    					contrItem.setUnit(json.getStr("??????"));
    				}
    				if (StrUtil.isNotEmpty(json.getStr("??????"))){
    					contrItem.setQty(new BigDecimal(json.getStr("??????")));
    				}
    				if (StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
    					contrItem.setPrice(new BigDecimal(json.getStr("??????????????????")));
    					// ??????
    					if (StrUtil.isEmpty(json.getStr("??????(%)")) || StrUtil.equals("???", (json.getStr("??????(%)")))) {
    						contrItem.setPriceNoTax(new BigDecimal(0));
    					} else {
    						contrItem.setTaxRate(json.getStr("??????(%)"));
    						contrItem.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(json.getStr("??????????????????")), CalcUtils.calcMultiply(CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(json.getStr("??????(%)"))), new BigDecimal(0.01)), 2));
    					} 
    				}
    				contrItem.setCreateUserInfo(userKey, realName);
    				flag = zxCtEqContrItemMapper.insert(contrItem);
    				if (flag == 0) {
    					repEntity.layerMessage("no", "???" + i + "????????????????????????" + workNo + "??????????????????(??????????????????)");
    				} else {
    					returnWorksList.add(contrItem);
    				}
    			}
    		}
    		i++;
    	}catch (Exception e) {
    		LoggerUtils.printDebugLogger(logger, e.getMessage());
    		repEntity.layerMessage("no", "???" + i + "????????????????????????" + workNo + "??????????????????(??????????????????)");
    	}
    	return repEntity.okList(returnWorksList, returnWorksList.size());
    }

	@Override
	public ResponseEntity getZxCtEqContrItemListForContract(ZxCtEqContrItem zxCtEqContrItem) {
		if (zxCtEqContrItem == null) {
            zxCtEqContrItem = new ZxCtEqContrItem();
        }
        if(StrUtil.isEmpty(zxCtEqContrItem.getContractID())) {
        	 return repEntity.okList(null, 0);
        }
        // ????????????
        PageHelper.startPage(zxCtEqContrItem.getPage(),zxCtEqContrItem.getLimit());
        // ????????????
        List<ZxCtEqContrItem> zxCtEqContrItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(zxCtEqContrItem);
        // ??????????????????
        PageInfo<ZxCtEqContrItem> p = new PageInfo<>(zxCtEqContrItemList);

        return repEntity.okList(zxCtEqContrItemList, p.getTotal());
	}

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
