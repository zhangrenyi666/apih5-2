package com.apih5.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApply;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.service.ZxCtSuppliesContrApplyLeaseListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrApplyLeaseListService")
public class ZxCtSuppliesContrApplyLeaseListServiceImpl implements ZxCtSuppliesContrApplyLeaseListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyLeaseListMapper zxCtSuppliesContrApplyLeaseListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyShopListMapper zxCtSuppliesContrApplyShopListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyMapper zxCtSuppliesContrApplyMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrLeaseListMapper zxCtSuppliesContrLeaseListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContrApplyLeaseListListByCondition(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        if (zxCtSuppliesContrApplyLeaseList == null) {
            zxCtSuppliesContrApplyLeaseList = new ZxCtSuppliesContrApplyLeaseList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrApplyLeaseList.getPage(),zxCtSuppliesContrApplyLeaseList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrApplyLeaseList> zxCtSuppliesContrApplyLeaseListList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(zxCtSuppliesContrApplyLeaseList);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrApplyLeaseList> p = new PageInfo<>(zxCtSuppliesContrApplyLeaseListList);

        return repEntity.okList(zxCtSuppliesContrApplyLeaseListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrApplyLeaseListDetail(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        if (zxCtSuppliesContrApplyLeaseList == null) {
            zxCtSuppliesContrApplyLeaseList = new ZxCtSuppliesContrApplyLeaseList();
        }
        // 获取数据
        ZxCtSuppliesContrApplyLeaseList dbZxCtSuppliesContrApplyLeaseList = zxCtSuppliesContrApplyLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getApplyLeaseListId());
        // 数据存在
        if (dbZxCtSuppliesContrApplyLeaseList != null) {
            return repEntity.ok(dbZxCtSuppliesContrApplyLeaseList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrApplyLeaseList(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrApplyLeaseList.setApplyLeaseListId(UuidUtil.generate());
        zxCtSuppliesContrApplyLeaseList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrApplyLeaseListMapper.insert(zxCtSuppliesContrApplyLeaseList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrApplyLeaseList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrApplyLeaseList(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrApplyLeaseList dbZxCtSuppliesContrApplyLeaseList = zxCtSuppliesContrApplyLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getApplyLeaseListId());
        if (dbZxCtSuppliesContrApplyLeaseList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApplyLeaseList.getApplyLeaseListId())) {
           // 租期单位
           dbZxCtSuppliesContrApplyLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
           // 租期
           dbZxCtSuppliesContrApplyLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
           // 要求修改
           dbZxCtSuppliesContrApplyLeaseList.setRequestEdit(zxCtSuppliesContrApplyLeaseList.getRequestEdit());
           // 修改日期
           dbZxCtSuppliesContrApplyLeaseList.setEditDate(zxCtSuppliesContrApplyLeaseList.getEditDate());
           // 修改人
           dbZxCtSuppliesContrApplyLeaseList.setEditUserID(zxCtSuppliesContrApplyLeaseList.getEditUserID());
           // 修改人
           dbZxCtSuppliesContrApplyLeaseList.setEditUserName(zxCtSuppliesContrApplyLeaseList.getEditUserName());
           // 物资名称
           dbZxCtSuppliesContrApplyLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrApplyLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
           // 物资规格
           dbZxCtSuppliesContrApplyLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
           // 物资编码ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrApplyLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrApplyLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
           // 税额
           dbZxCtSuppliesContrApplyLeaseList.setContractSumTax(zxCtSuppliesContrApplyLeaseList.getContractSumTax());
           // 数量
           dbZxCtSuppliesContrApplyLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
           // 是否网价
           dbZxCtSuppliesContrApplyLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
           // 是否抵扣
           dbZxCtSuppliesContrApplyLeaseList.setIsDeduct(zxCtSuppliesContrApplyLeaseList.getIsDeduct());
           // 实际开始时间
           dbZxCtSuppliesContrApplyLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrApplyLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
           // 评审ID
           dbZxCtSuppliesContrApplyLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
           // 界面展现类型
           dbZxCtSuppliesContrApplyLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrApplyLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrApplyLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
           // 合同ID
           dbZxCtSuppliesContrApplyLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
           // 含税合同金额
           dbZxCtSuppliesContrApplyLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
           // 含税合同单价
           dbZxCtSuppliesContrApplyLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
           // 单位
           dbZxCtSuppliesContrApplyLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
           // 单位
           dbZxCtSuppliesContrApplyLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
           // 不含税合同金额
           dbZxCtSuppliesContrApplyLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
           // 不含税合同单价
           dbZxCtSuppliesContrApplyLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrApplyLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
           // 变更后租期
           dbZxCtSuppliesContrApplyLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
           // 变更后税额
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumTax());
           // 变更后数量
           dbZxCtSuppliesContrApplyLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrApplyLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrApplyLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());
           // 备注
           dbZxCtSuppliesContrApplyLeaseList.setRemarks(zxCtSuppliesContrApplyLeaseList.getRemarks());
           // 排序
           dbZxCtSuppliesContrApplyLeaseList.setSort(zxCtSuppliesContrApplyLeaseList.getSort());
           // 共通
           dbZxCtSuppliesContrApplyLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyLeaseListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrApplyLeaseList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyLeaseList(List<ZxCtSuppliesContrApplyLeaseList> zxCtSuppliesContrApplyLeaseListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrApplyLeaseListList != null && zxCtSuppliesContrApplyLeaseListList.size() > 0) {
           ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList = new ZxCtSuppliesContrApplyLeaseList();
           zxCtSuppliesContrApplyLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyLeaseListMapper.batchDeleteUpdateZxCtSuppliesContrApplyLeaseList(zxCtSuppliesContrApplyLeaseListList, zxCtSuppliesContrApplyLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyLeaseListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity updateZxCtSuppliesContrApplyLeaseListByApplyId(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrApplyLeaseList applyShop = new ZxCtSuppliesContrApplyLeaseList();
        applyShop.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
        applyShop.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
        List<ZxCtSuppliesContrApplyLeaseList> applyShopList = zxCtSuppliesContrApplyLeaseListMapper.selectSuppliesContrApplyLeaseListByCondition(applyShop);
        if(applyShopList.size()>0) {
        	if(!StrUtil.equals(applyShopList.get(0).getApplyLeaseListId(), zxCtSuppliesContrApplyLeaseList.getApplyLeaseListId())) {        		
        		return repEntity.layerMessage("no", "当前物资编码已存在，无法修改！");
        	}
        }            
        ZxCtSuppliesContrApplyLeaseList dbZxCtSuppliesContrApplyLeaseList = zxCtSuppliesContrApplyLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getApplyLeaseListId());
        if (dbZxCtSuppliesContrApplyLeaseList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApplyLeaseList.getApplyLeaseListId())) {
           // 租期单位
           dbZxCtSuppliesContrApplyLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
           // 租期
           dbZxCtSuppliesContrApplyLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
           // 要求修改
           dbZxCtSuppliesContrApplyLeaseList.setRequestEdit(zxCtSuppliesContrApplyLeaseList.getRequestEdit());
           // 修改日期
           dbZxCtSuppliesContrApplyLeaseList.setEditDate(zxCtSuppliesContrApplyLeaseList.getEditDate());
           // 修改人
           dbZxCtSuppliesContrApplyLeaseList.setEditUserID(zxCtSuppliesContrApplyLeaseList.getEditUserID());
           // 修改人
           dbZxCtSuppliesContrApplyLeaseList.setEditUserName(zxCtSuppliesContrApplyLeaseList.getEditUserName());
           // 物资名称
           dbZxCtSuppliesContrApplyLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrApplyLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
           // 物资规格
           dbZxCtSuppliesContrApplyLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
           // 物资编码ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrApplyLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrApplyLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
           // 税额
           dbZxCtSuppliesContrApplyLeaseList.setContractSumTax(zxCtSuppliesContrApplyLeaseList.getContractSumTax());
           // 数量
           dbZxCtSuppliesContrApplyLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
           // 是否网价
           dbZxCtSuppliesContrApplyLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
           // 是否抵扣
           dbZxCtSuppliesContrApplyLeaseList.setIsDeduct(zxCtSuppliesContrApplyLeaseList.getIsDeduct());
           // 实际开始时间
           dbZxCtSuppliesContrApplyLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrApplyLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
           // 评审ID
           dbZxCtSuppliesContrApplyLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
           // 界面展现类型
           dbZxCtSuppliesContrApplyLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrApplyLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrApplyLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
           // 合同ID
           dbZxCtSuppliesContrApplyLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
           // 含税合同金额
           dbZxCtSuppliesContrApplyLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
           // 含税合同单价
           dbZxCtSuppliesContrApplyLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
           // 单位
           dbZxCtSuppliesContrApplyLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
           // 单位
           dbZxCtSuppliesContrApplyLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
           // 不含税合同金额
           dbZxCtSuppliesContrApplyLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
           // 不含税合同单价
           dbZxCtSuppliesContrApplyLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrApplyLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
           // 变更后租期
           dbZxCtSuppliesContrApplyLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
           // 变更后税额
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumTax());
           // 变更后数量
           dbZxCtSuppliesContrApplyLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrApplyLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrApplyLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());
           // 备注
           dbZxCtSuppliesContrApplyLeaseList.setRemarks(zxCtSuppliesContrApplyLeaseList.getRemarks());
           // 排序
           dbZxCtSuppliesContrApplyLeaseList.setSort(zxCtSuppliesContrApplyLeaseList.getSort());
           // 共通
           dbZxCtSuppliesContrApplyLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyLeaseListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//计算当前清单下所有金额，存到合同评审表中
        	applyShop = new ZxCtSuppliesContrApplyLeaseList();
        	applyShop.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
        	List<ZxCtSuppliesContrApplyLeaseList> shopList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getPp5());
        	//含税合同金额
            double contractCost = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSum().doubleValue()).reduce(0, Double::sum);
            double contractCostNoTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumNoTax().doubleValue()).reduce(0, Double::sum);
            double contractCostTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumTax().doubleValue()).reduce(0, Double::sum);
//        	apply.setContractCost(new BigDecimal(contractCost).setScale(6, RoundingMode.HALF_UP));
//        	//不含税合同金额
//        	apply.setContractCostNoTax(new BigDecimal(contractCostNoTax).setScale(6, RoundingMode.HALF_UP));
//        	//合同税额
//        	apply.setContractCostTax(new BigDecimal(contractCostTax).setScale(6, RoundingMode.HALF_UP));
        	apply.setContractCost(CalcUtils.calcDivide(new BigDecimal(contractCost), new BigDecimal(10000),6));
        	//不含税合同金额
        	apply.setContractCostNoTax(CalcUtils.calcDivide(new BigDecimal(contractCostNoTax), new BigDecimal(10000),6));
        	//合同税额
        	apply.setContractCostTax(CalcUtils.calcDivide(new BigDecimal(contractCostTax), new BigDecimal(10000),6));
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(apply);
        	/*************************临时代码，将数据同步到清单表中**************************************/
//            ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList = zxCtSuppliesContrLeaseListMapper.selectByPrimaryKey("");
//            if (zxCtSuppliesContrLeaseList != null && StrUtil.isNotEmpty(zxCtSuppliesContrLeaseList.getZxCtSuppliesContrLeaseListId())) {
//                // 租期单位
//                zxCtSuppliesContrLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
//                // 租期
//                zxCtSuppliesContrLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
//                // 物资名称
//                zxCtSuppliesContrLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
//                // 物资类别ID
//                zxCtSuppliesContrLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
//                // 物资类别
//                zxCtSuppliesContrLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
//                // 物资编码ID
//                zxCtSuppliesContrLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
//                // 物资编码
//                zxCtSuppliesContrLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
//                // 税率(%)
//                zxCtSuppliesContrLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
//                // 数量
//                zxCtSuppliesContrLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
//                // 是否网价
//                zxCtSuppliesContrLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
//                // 实际开始时间
//                zxCtSuppliesContrLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
//                // 实际结束时间
//                zxCtSuppliesContrLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
//                // 评审ID
//                zxCtSuppliesContrLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
//                // 界面展现类型
//                zxCtSuppliesContrLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
//                // 计划开始时间
//                zxCtSuppliesContrLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
//                // 计划结束时间
//                zxCtSuppliesContrLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
//                // 合同ID
//                zxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
//                // 含税金额
//                zxCtSuppliesContrLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
//                // 含税单价
//                zxCtSuppliesContrLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
//                // 规格型号
//                zxCtSuppliesContrLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
//                // 单位
//                zxCtSuppliesContrLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
//                // 单位
//                zxCtSuppliesContrLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
//                // 不含税金额
//                zxCtSuppliesContrLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
//                // 不含税单价
//                zxCtSuppliesContrLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
//                // 变更日期
//                zxCtSuppliesContrLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
//                // 变更后租期
//                zxCtSuppliesContrLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
//                // 变更后数量
//                zxCtSuppliesContrLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
//                // 变更后含税金额
//                zxCtSuppliesContrLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
//                // 变更后含税单价
//                zxCtSuppliesContrLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
//                // 变更后不含税金额
//                zxCtSuppliesContrLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
//                // 变更后不含税单价
//                zxCtSuppliesContrLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());                 	
//               zxCtSuppliesContrLeaseList.setModifyUserInfo(userKey, realName);
//               flag = zxCtSuppliesContrLeaseListMapper.updateByPrimaryKey(zxCtSuppliesContrLeaseList);
//            }
            return repEntity.ok("sys.data.update",zxCtSuppliesContrApplyLeaseList);
        }
    }

	@Override
	public ResponseEntity saveZxCtSuppliesContrApplyLeaseListByApplyId(
			ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesContrApplyLeaseList applyShop = new ZxCtSuppliesContrApplyLeaseList();
        applyShop.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
        applyShop.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
        List<ZxCtSuppliesContrApplyLeaseList> applyShopList = zxCtSuppliesContrApplyLeaseListMapper.selectSuppliesContrApplyLeaseListByCondition(applyShop);
        if(applyShopList.size()>0) {
            return repEntity.layerMessage("no", "当前物资编码已存在，无法保存！");
        }
        zxCtSuppliesContrApplyLeaseList.setApplyLeaseListId(UuidUtil.generate());
        zxCtSuppliesContrApplyLeaseList.setCreateUserInfo(userKey, realName);
        ZxCtSuppliesContract contract = new ZxCtSuppliesContract();
        contract.setFromApplyID(zxCtSuppliesContrApplyLeaseList.getPp5());
        List<ZxCtSuppliesContract> contractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(contract);
        if(contractList.size()>0) {
        	zxCtSuppliesContrApplyLeaseList.setContractID(contractList.get(0).getZxCtSuppliesContractId());
        }
        int flag = zxCtSuppliesContrApplyLeaseListMapper.insert(zxCtSuppliesContrApplyLeaseList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//计算当前清单下所有金额，存到合同评审表中
        	applyShop = new ZxCtSuppliesContrApplyLeaseList();
        	applyShop.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
        	List<ZxCtSuppliesContrApplyLeaseList> shopList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getPp5());
        	//含税合同金额
            double contractCost = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSum().doubleValue()).reduce(0, Double::sum);
            double contractCostNoTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumNoTax().doubleValue()).reduce(0, Double::sum);
            double contractCostTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumTax().doubleValue()).reduce(0, Double::sum);
//        	apply.setContractCost(new BigDecimal(contractCost).setScale(6, RoundingMode.HALF_UP));
//        	//不含税合同金额
//        	apply.setContractCostNoTax(new BigDecimal(contractCostNoTax).setScale(6, RoundingMode.HALF_UP));
//        	//合同税额
//        	apply.setContractCostTax(new BigDecimal(contractCostTax).setScale(6, RoundingMode.HALF_UP));
        	apply.setContractCost(CalcUtils.calcDivide(new BigDecimal(contractCost), new BigDecimal(10000),6));
        	//不含税合同金额
        	apply.setContractCostNoTax(CalcUtils.calcDivide(new BigDecimal(contractCostNoTax), new BigDecimal(10000),6));
        	//合同税额
        	apply.setContractCostTax(CalcUtils.calcDivide(new BigDecimal(contractCostTax), new BigDecimal(10000),6));
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(apply);
        	/*************************临时代码，将数据同步到清单表中**************************************/
//        	ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList = new ZxCtSuppliesContrLeaseList();
//            zxCtSuppliesContrLeaseList.setZxCtSuppliesContrLeaseListId(UuidUtil.generate());
//            zxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
//            // 租期单位
//            zxCtSuppliesContrLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
//            // 租期
//            zxCtSuppliesContrLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
//            // 物资名称
//            zxCtSuppliesContrLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
//            // 物资类别ID
//            zxCtSuppliesContrLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
//            // 物资类别
//            zxCtSuppliesContrLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
//            // 物资编码ID
//            zxCtSuppliesContrLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
//            // 物资编码
//            zxCtSuppliesContrLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
//            // 税率(%)
//            zxCtSuppliesContrLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
//            // 数量
//            zxCtSuppliesContrLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
//            // 是否网价
//            zxCtSuppliesContrLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
//            // 实际开始时间
//            zxCtSuppliesContrLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
//            // 实际结束时间
//            zxCtSuppliesContrLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
//            // 评审ID
//            zxCtSuppliesContrLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
//            // 界面展现类型
//            zxCtSuppliesContrLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
//            // 计划开始时间
//            zxCtSuppliesContrLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
//            // 计划结束时间
//            zxCtSuppliesContrLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
//            // 合同ID
//            zxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
//            // 含税金额
//            zxCtSuppliesContrLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
//            // 含税单价
//            zxCtSuppliesContrLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
//            // 规格型号
//            zxCtSuppliesContrLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
//            // 单位
//            zxCtSuppliesContrLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
//            // 单位
//            zxCtSuppliesContrLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
//            // 不含税金额
//            zxCtSuppliesContrLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
//            // 不含税单价
//            zxCtSuppliesContrLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
//            // 变更日期
//            zxCtSuppliesContrLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
//            // 变更后租期
//            zxCtSuppliesContrLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
//            // 变更后数量
//            zxCtSuppliesContrLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
//            // 变更后含税金额
//            zxCtSuppliesContrLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
//            // 变更后含税单价
//            zxCtSuppliesContrLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
//            // 变更后不含税金额
//            zxCtSuppliesContrLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
//            // 变更后不含税单价
//            zxCtSuppliesContrLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());            
//            zxCtSuppliesContrLeaseList.setCreateUserInfo(userKey, realName);
//            zxCtSuppliesContrLeaseListMapper.insert(zxCtSuppliesContrLeaseList);
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrApplyLeaseList);
        }
	}
}
