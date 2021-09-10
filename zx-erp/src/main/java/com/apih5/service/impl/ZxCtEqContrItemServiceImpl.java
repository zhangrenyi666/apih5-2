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
        // 分页查询
        PageHelper.startPage(zxCtEqContrItem.getPage(),zxCtEqContrItem.getLimit());
        // 获取数据
        List<ZxCtEqContrItem> zxCtEqContrItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(zxCtEqContrItem);
        // 得到分页信息
        PageInfo<ZxCtEqContrItem> p = new PageInfo<>(zxCtEqContrItemList);

        return repEntity.okList(zxCtEqContrItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtEqContrItemDetail(ZxCtEqContrItem zxCtEqContrItem) {
        if (zxCtEqContrItem == null) {
            zxCtEqContrItem = new ZxCtEqContrItem();
        }
        // 获取数据
        ZxCtEqContrItem dbZxCtEqContrItem = zxCtEqContrItemMapper.selectByPrimaryKey(zxCtEqContrItem.getZxCtEqContrItemId());
        // 数据存在
        if (dbZxCtEqContrItem != null) {
            return repEntity.ok(dbZxCtEqContrItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
        // 含税合同金额 = 含税合同单价*合同数量
        zxCtEqContrItem.setContractSum(CalcUtils.calcMultiply(zxCtEqContrItem.getPrice(), zxCtEqContrItem.getQty()));
        BigDecimal priceNoTax = new BigDecimal("0");// 不含税合同单价
        BigDecimal contractSumNoTax = new BigDecimal("0");// 不含税合同金额
        BigDecimal contractSumTax = new BigDecimal("0");// 税额
        if(StrUtil.isNotEmpty(zxCtEqContrItem.getTaxRate())) {
        	//不含税合同单价 = 含税合同单价/(1+税率/100)
        	priceNoTax = CalcUtils.calcDivide(zxCtEqContrItem.getPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
        	//不含税合同金额 = 含税金额/(1+税率/100)
        	contractSumNoTax = CalcUtils.calcDivide(zxCtEqContrItem.getContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
        	//税额(contractSumTax) = 含税合同金额 - 不含税合同金额
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
            // 设备分类基础表中的parentId
            dbZxCtEqContrItem.setParentID(zxCtEqContrItem.getParentID());
            // 设备大类名称
            ZxEqResCategory category = zxEqResCategoryMapper.selectByPrimaryKey(zxCtEqContrItem.getParentID());
            if(category != null && StrUtil.isNotEmpty(category.getId())) {
            	dbZxCtEqContrItem.setCatParentName(category.getCatName());
            }
            // 设备分类基础表中的主键id
            dbZxCtEqContrItem.setZxEqResCategoryId(zxCtEqContrItem.getZxEqResCategoryId());
            // 设备编号
            dbZxCtEqContrItem.setCatCode(zxCtEqContrItem.getCatCode());
            // 设备名称
            dbZxCtEqContrItem.setCatName(zxCtEqContrItem.getCatName());
            // 型号
            dbZxCtEqContrItem.setSpec(zxCtEqContrItem.getSpec());
            // 计量单位
            dbZxCtEqContrItem.setUnit(zxCtEqContrItem.getUnit());
            //租赁单位
            dbZxCtEqContrItem.setRentUnit(zxCtEqContrItem.getRentUnit());
            // 合同数量
            dbZxCtEqContrItem.setQty(zxCtEqContrItem.getQty());
            // 含税合同单价
            dbZxCtEqContrItem.setPrice(zxCtEqContrItem.getPrice());
            // 备注
            dbZxCtEqContrItem.setRemark(zxCtEqContrItem.getRemark());
            //开始时间
            dbZxCtEqContrItem.setActualStartDate(zxCtEqContrItem.getActualStartDate());
            //结束时间
            dbZxCtEqContrItem.setActualEndDate(zxCtEqContrItem.getActualEndDate());
            
            // 税率(%)
            dbZxCtEqContrItem.setTaxRate(zxCtEqContrItem.getTaxRate());
            // 含税合同金额 = 含税合同单价*合同数量
            dbZxCtEqContrItem.setContractSum(CalcUtils.calcMultiply(zxCtEqContrItem.getPrice(), zxCtEqContrItem.getQty()));
            BigDecimal priceNoTax = new BigDecimal("0");// 不含税合同单价
            BigDecimal contractSumNoTax = new BigDecimal("0");// 不含税合同金额
            BigDecimal contractSumTax = new BigDecimal("0");// 税额
            if(StrUtil.isNotEmpty(zxCtEqContrItem.getTaxRate())) {
            	//不含税合同单价 = 含税合同单价/(1+税率/100)
            	priceNoTax = CalcUtils.calcDivide(zxCtEqContrItem.getPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
            	//不含税合同金额 = 含税金额/(1+税率/100)
            	contractSumNoTax = CalcUtils.calcDivide(dbZxCtEqContrItem.getContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtEqContrItem.getTaxRate()), new BigDecimal("100"))));
            	//税额(contractSumTax) = 含税合同金额 - 不含税合同金额
            	contractSumTax = CalcUtils.calcSubtract(dbZxCtEqContrItem.getContractSum(), contractSumNoTax);
            }
            dbZxCtEqContrItem.setPriceNoTax(priceNoTax);
            dbZxCtEqContrItem.setContractSumNoTax(contractSumNoTax);
            dbZxCtEqContrItem.setContractSumTax(contractSumTax);
            // 共通
            dbZxCtEqContrItem.setModifyUserInfo(userKey, realName);
           flag = zxCtEqContrItemMapper.updateByPrimaryKey(dbZxCtEqContrItem);
        }
        // 失败
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
        // 失败
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
    		return repEntity.layerMessage("no", "设备分类id没传！");
    	}
    	ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem = new ZxEqEquipLimitPriceItem();
    	zxEqEquipLimitPriceItem.setEquipID(zxCtEqContrItem.getZxEqResCategoryId());
    	zxEqEquipLimitPriceItem.setApih5FlowStatus("2");
    	zxEqEquipLimitPriceItem.setComID(zxCtEqContrItem.getComID());
    	// 分页查询
    	PageHelper.startPage(zxEqEquipLimitPriceItem.getPage(),zxEqEquipLimitPriceItem.getLimit());
    	// 获取数据
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
    	// 得到分页信息
    	PageInfo<ZxEqEquipLimitPriceItem> p = new PageInfo<>(zxEqEquipLimitPriceItemList);

    	return repEntity.okList(zxEqEquipLimitPriceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity importZxCtEqContrItem(ZxCtEqContrItem zxCtEqContrItem) {
    	if(zxCtEqContrItem == null){
    		zxCtEqContrItem = new ZxCtEqContrItem();
    	}
    	// 上传文件为空
    	if (zxCtEqContrItem.getZxErpFileList() == null || zxCtEqContrItem.getZxErpFileList().size() == 0) {
    		return repEntity.layerMessage("no", "没有导入文件!");
    	}
    	if(StrUtil.isEmpty(zxCtEqContrItem.getZxCtEqContrApplyId())) {
    		return repEntity.layerMessage("no", "请在对应的合同评审下导入清单!");
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
    			return repEntity.layerMessage("no", "导入失败，无导入数据!");
    		}
    		if(readAll != null && readAll.size() >0) {
    			for(Map<String,Object> map:readAll) {
    				JSONObject json = new JSONObject(map);
    				//清单编号  清单名称  规格型号 单位   数量   含税合同单价    税率(%)
    				ZxCtEqContrItem contrItem = new ZxCtEqContrItem();
    				contrItem.setZxCtEqContrItemId(UuidUtil.generate());
    				contrItem.setZxCtEqContrApplyId(zxCtEqContrItem.getZxCtEqContrApplyId());
    				if (StrUtil.isEmpty(json.getStr("清单编号"))) {
    					continue;
    				}
    				contrItem.setCatCode(json.getStr("清单编号"));
    				workNo = contrItem.getCatCode();
    				if(StrUtil.isNotEmpty(json.getStr("清单名称"))){
    					contrItem.setCatName(json.getStr("清单名称"));
    				}
    				if(StrUtil.isNotEmpty(json.getStr("规格型号"))){
    					contrItem.setSpec(json.getStr("规格型号"));
    				}
    				if(StrUtil.isNotEmpty(json.getStr("单位"))){
    					contrItem.setUnit(json.getStr("单位"));
    				}
    				if (StrUtil.isNotEmpty(json.getStr("数量"))){
    					contrItem.setQty(new BigDecimal(json.getStr("数量")));
    				}
    				if (StrUtil.isNotEmpty(json.getStr("含税合同单价"))) {
    					contrItem.setPrice(new BigDecimal(json.getStr("含税合同单价")));
    					// 税率
    					if (StrUtil.isEmpty(json.getStr("税率(%)")) || StrUtil.equals("空", (json.getStr("税率(%)")))) {
    						contrItem.setPriceNoTax(new BigDecimal(0));
    					} else {
    						contrItem.setTaxRate(json.getStr("税率(%)"));
    						contrItem.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(json.getStr("含税合同单价")), CalcUtils.calcMultiply(CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(json.getStr("税率(%)"))), new BigDecimal(0.01)), 2));
    					} 
    				}
    				contrItem.setCreateUserInfo(userKey, realName);
    				flag = zxCtEqContrItemMapper.insert(contrItem);
    				if (flag == 0) {
    					repEntity.layerMessage("no", "第" + i + "行【清单编号为：" + workNo + "】导入失败！(数据操作失败)");
    				} else {
    					returnWorksList.add(contrItem);
    				}
    			}
    		}
    		i++;
    	}catch (Exception e) {
    		LoggerUtils.printDebugLogger(logger, e.getMessage());
    		repEntity.layerMessage("no", "第" + i + "行【清单编号为：" + workNo + "】导入失败！(数据操作失败)");
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
        // 分页查询
        PageHelper.startPage(zxCtEqContrItem.getPage(),zxCtEqContrItem.getLimit());
        // 获取数据
        List<ZxCtEqContrItem> zxCtEqContrItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(zxCtEqContrItem);
        // 得到分页信息
        PageInfo<ZxCtEqContrItem> p = new PageInfo<>(zxCtEqContrItemList);

        return repEntity.okList(zxCtEqContrItemList, p.getTotal());
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
