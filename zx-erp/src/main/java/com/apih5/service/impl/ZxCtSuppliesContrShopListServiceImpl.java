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
import com.apih5.mybatis.dao.ZxCtSuppliesContrShopListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;
import com.apih5.service.ZxCtSuppliesContrShopListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrShopListService")
public class ZxCtSuppliesContrShopListServiceImpl implements ZxCtSuppliesContrShopListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrShopListMapper zxCtSuppliesContrShopListMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContrShopListListByCondition(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        if (zxCtSuppliesContrShopList == null) {
            zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrShopList.getPage(),zxCtSuppliesContrShopList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrShopList> zxCtSuppliesContrShopListList = zxCtSuppliesContrShopListMapper.selectByZxCtSuppliesContrShopListList(zxCtSuppliesContrShopList);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrShopList> p = new PageInfo<>(zxCtSuppliesContrShopListList);

        return repEntity.okList(zxCtSuppliesContrShopListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrShopListDetail(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        if (zxCtSuppliesContrShopList == null) {
            zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
        }
        // 获取数据
        ZxCtSuppliesContrShopList dbZxCtSuppliesContrShopList = zxCtSuppliesContrShopListMapper.selectByPrimaryKey(zxCtSuppliesContrShopList.getZxCtSuppliesContrShopListId());
        // 数据存在
        if (dbZxCtSuppliesContrShopList != null) {
            return repEntity.ok(dbZxCtSuppliesContrShopList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrShopList(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrShopList.setZxCtSuppliesContrShopListId(UuidUtil.generate());
        zxCtSuppliesContrShopList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrShopListMapper.insert(zxCtSuppliesContrShopList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrShopList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrShopList(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrShopList dbZxCtSuppliesContrShopList = zxCtSuppliesContrShopListMapper.selectByPrimaryKey(zxCtSuppliesContrShopList.getZxCtSuppliesContrShopListId());
        if (dbZxCtSuppliesContrShopList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrShopList.getZxCtSuppliesContrShopListId())) {
           // 租期单位
           dbZxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrShopList.getRentUnit());
           // 租期
           dbZxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrShopList.getContrTrrm());
           // 物资名称
           dbZxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrShopList.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrShopList.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrShopList.getWorkType());
           // 物资编码ID
           dbZxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrShopList.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrShopList.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrShopList.getTaxRate());
           // 数量
           dbZxCtSuppliesContrShopList.setQty(zxCtSuppliesContrShopList.getQty());
           // 是否网价
           dbZxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrShopList.getIsNetPrice());
           // 实际开始时间
           dbZxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrShopList.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrShopList.getActualEndDate());
           // 评审ID
           dbZxCtSuppliesContrShopList.setPp5(zxCtSuppliesContrShopList.getPp5());
           // 界面展现类型
           dbZxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrShopList.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrShopList.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrShopList.getPlanEndDate());
           // 合同ID
           dbZxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrShopList.getContractID());
           // 含税金额
           dbZxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrShopList.getContractSum());
           // 含税单价
           dbZxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrShopList.getPrice());
           // 规格型号
           dbZxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrShopList.getSpec());
           // 单位
           dbZxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrShopList.getUnit());
           // 单位
           dbZxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrShopList.getTreenode());
           // 不含税金额
           dbZxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrShopList.getContractSumNoTax());
           // 不含税单价
           dbZxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrShopList.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrShopList.getChangeDate());
           // 变更后租期
           dbZxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrShopList.getAlterTrrm());
           // 变更后数量
           dbZxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrShopList.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrShopList.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrShopList.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrShopList.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrShopList.setChangePriceNoTax(zxCtSuppliesContrShopList.getChangePriceNoTax());
           // 变更ID
           dbZxCtSuppliesContrShopList.setPp6(zxCtSuppliesContrShopList.getPp6());
           // 备注
           dbZxCtSuppliesContrShopList.setRemarks(zxCtSuppliesContrShopList.getRemarks());
           // 排序
           dbZxCtSuppliesContrShopList.setSort(zxCtSuppliesContrShopList.getSort());
           // 共通
           dbZxCtSuppliesContrShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrShopListMapper.updateByPrimaryKey(dbZxCtSuppliesContrShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrShopList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrShopList(List<ZxCtSuppliesContrShopList> zxCtSuppliesContrShopListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrShopListList != null && zxCtSuppliesContrShopListList.size() > 0) {
           ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
           zxCtSuppliesContrShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrShopListMapper.batchDeleteUpdateZxCtSuppliesContrShopList(zxCtSuppliesContrShopListList, zxCtSuppliesContrShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrShopListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity getZxCtSuppliesContrShopListListByContID(
    		ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        if (zxCtSuppliesContrShopList == null) {
            zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrShopList.getPage(),zxCtSuppliesContrShopList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrShopList> zxCtSuppliesContrShopListList = zxCtSuppliesContrShopListMapper.selectByZxCtSuppliesContrShopListByContId(zxCtSuppliesContrShopList);
        if(StrUtil.isAllNotEmpty(zxCtSuppliesContrShopList.getIsTurnover())) {
        	for(ZxCtSuppliesContrShopList shop : zxCtSuppliesContrShopListList) {
        		if(shop.getChangeQty() != null) {
        			shop.setQty(shop.getChangeQty());	
        		}
        		if(shop.getChangeContractSumTax() != null) {
        			shop.setContractSumTax(shop.getChangeContractSumTax());	
        		}
        		if(shop.getChangeContractSumNoTax() != null) {
        			shop.setContractSumNoTax(shop.getChangeContractSumNoTax());	
        		}
        		if(shop.getChangeContractSum() != null) {
        			shop.setContractSum(shop.getChangeContractSum());	
        		}
        	}
        }
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrShopList> p = new PageInfo<>(zxCtSuppliesContrShopListList);

        return repEntity.okList(zxCtSuppliesContrShopListList, p.getTotal());
    }

	@Override
	public ResponseEntity getZxCtSuppliesContrShopListByWorkID(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList) {
        if (zxCtSuppliesContrShopList == null) {
            zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrShopList.getPage(),zxCtSuppliesContrShopList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrShopList> zxCtSuppliesContrShopListList = zxCtSuppliesContrShopListMapper.selectByZxCtSuppliesContrShopListList(zxCtSuppliesContrShopList);
        for(ZxCtSuppliesContrShopList shop : zxCtSuppliesContrShopListList) {
        	if(StrUtil.isNotEmpty(shop.getSettledQty())) {
        		if(shop.getChangeQty() != null) {
            		shop.setSurplusQty(CalcUtils.calcSubtract(shop.getChangeQty(), new BigDecimal(shop.getSettledQty())).stripTrailingZeros().toPlainString());
        		}else {
        			shop.setSurplusQty(CalcUtils.calcSubtract(shop.getQty(), new BigDecimal(shop.getSettledQty())).stripTrailingZeros().toPlainString());        			
        		}
        	}else {
        		if(shop.getChangeQty() != null) {
        			shop.setSurplusQty(shop.getChangeQty().stripTrailingZeros().toPlainString());        		
        		}else {
        			shop.setSurplusQty(shop.getQty().stripTrailingZeros().toPlainString());     		
        		}
        	}
        }
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrShopList> p = new PageInfo<>(zxCtSuppliesContrShopListList);

        return repEntity.okList(zxCtSuppliesContrShopListList, p.getTotal());
	}
}
