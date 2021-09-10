package com.apih5.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApply;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.service.ZxCtSuppliesContrApplyShopListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrApplyShopListService")
public class ZxCtSuppliesContrApplyShopListServiceImpl implements ZxCtSuppliesContrApplyShopListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyShopListMapper zxCtSuppliesContrApplyShopListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyMapper zxCtSuppliesContrApplyMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrShopListMapper zxCtSuppliesContrShopListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContrApplyShopListListByCondition(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        if (zxCtSuppliesContrApplyShopList == null) {
            zxCtSuppliesContrApplyShopList = new ZxCtSuppliesContrApplyShopList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrApplyShopList.getPage(),zxCtSuppliesContrApplyShopList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(zxCtSuppliesContrApplyShopList);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrApplyShopList> p = new PageInfo<>(zxCtSuppliesContrApplyShopListList);

        return repEntity.okList(zxCtSuppliesContrApplyShopListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrApplyShopListDetail(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        if (zxCtSuppliesContrApplyShopList == null) {
            zxCtSuppliesContrApplyShopList = new ZxCtSuppliesContrApplyShopList();
        }
        // 获取数据
        ZxCtSuppliesContrApplyShopList dbZxCtSuppliesContrApplyShopList = zxCtSuppliesContrApplyShopListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getApplyShopListId());
        // 数据存在
        if (dbZxCtSuppliesContrApplyShopList != null) {
            return repEntity.ok(dbZxCtSuppliesContrApplyShopList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrApplyShopList(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrApplyShopList.setApplyShopListId(UuidUtil.generate());
        zxCtSuppliesContrApplyShopList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrApplyShopListMapper.insert(zxCtSuppliesContrApplyShopList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrApplyShopList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrApplyShopList(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrApplyShopList dbZxCtSuppliesContrApplyShopList = zxCtSuppliesContrApplyShopListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getApplyShopListId());
        if (dbZxCtSuppliesContrApplyShopList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApplyShopList.getApplyShopListId())) {
           // 租期单位
           dbZxCtSuppliesContrApplyShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
           // 租期
           dbZxCtSuppliesContrApplyShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
           // 要求修改
           dbZxCtSuppliesContrApplyShopList.setRequestEdit(zxCtSuppliesContrApplyShopList.getRequestEdit());
           // 修改日期
           dbZxCtSuppliesContrApplyShopList.setEditDate(zxCtSuppliesContrApplyShopList.getEditDate());
           // 修改人
           dbZxCtSuppliesContrApplyShopList.setEditUserName(zxCtSuppliesContrApplyShopList.getEditUserName());
           // 修改人
           dbZxCtSuppliesContrApplyShopList.setEditUserID(zxCtSuppliesContrApplyShopList.getEditUserID());
           // 物资名称
           dbZxCtSuppliesContrApplyShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrApplyShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrApplyShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
           // 物资规格
           dbZxCtSuppliesContrApplyShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
           // 物资编码ID
           dbZxCtSuppliesContrApplyShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrApplyShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrApplyShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
           // 税额
           dbZxCtSuppliesContrApplyShopList.setContractSumTax(zxCtSuppliesContrApplyShopList.getContractSumTax());
           // 数量
           dbZxCtSuppliesContrApplyShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
           // 是否网价
           dbZxCtSuppliesContrApplyShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
           // 是否抵扣
           dbZxCtSuppliesContrApplyShopList.setIsDeduct(zxCtSuppliesContrApplyShopList.getIsDeduct());
           // 实际开始时间
           dbZxCtSuppliesContrApplyShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrApplyShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
           // 评审ID
           dbZxCtSuppliesContrApplyShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
           // 界面展现类型
           dbZxCtSuppliesContrApplyShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrApplyShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrApplyShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
           // 合同ID
           dbZxCtSuppliesContrApplyShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
           // 含税合同金额
           dbZxCtSuppliesContrApplyShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
           // 含税合同单价
           dbZxCtSuppliesContrApplyShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
           // 单位
           dbZxCtSuppliesContrApplyShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
           // 单位
           dbZxCtSuppliesContrApplyShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
           // 不含税合同金额
           dbZxCtSuppliesContrApplyShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
           // 不含税合同单价
           dbZxCtSuppliesContrApplyShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrApplyShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
           // 变更后租期
           dbZxCtSuppliesContrApplyShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
           // 变更后税额
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumTax(zxCtSuppliesContrApplyShopList.getChangeContractSumTax());
           // 变更后数量
           dbZxCtSuppliesContrApplyShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrApplyShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrApplyShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrApplyShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());
           // 备注
           dbZxCtSuppliesContrApplyShopList.setRemarks(zxCtSuppliesContrApplyShopList.getRemarks());
           // 排序
           dbZxCtSuppliesContrApplyShopList.setSort(zxCtSuppliesContrApplyShopList.getSort());
           // 共通
           dbZxCtSuppliesContrApplyShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyShopListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrApplyShopList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyShopList(List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrApplyShopListList != null && zxCtSuppliesContrApplyShopListList.size() > 0) {
           ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList = new ZxCtSuppliesContrApplyShopList();
           zxCtSuppliesContrApplyShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyShopListMapper.batchDeleteUpdateZxCtSuppliesContrApplyShopList(zxCtSuppliesContrApplyShopListList, zxCtSuppliesContrApplyShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyShopListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    

    @Override
    public ResponseEntity updateZxCtSuppliesContrApplyShopListByApplyId(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList();
        applyShop.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
        applyShop.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
        List<ZxCtSuppliesContrApplyShopList> applyShopList = zxCtSuppliesContrApplyShopListMapper.selectSuppliesContrApplyShopListByCondition(applyShop);
        if(applyShopList.size()>0) {
        	if(!StrUtil.equals(applyShopList.get(0).getApplyShopListId(), zxCtSuppliesContrApplyShopList.getApplyShopListId())) {        		
        		return repEntity.layerMessage("no", "当前物资编码已存在，无法修改！");
        	}
        }        
        ZxCtSuppliesContrApplyShopList dbZxCtSuppliesContrApplyShopList = zxCtSuppliesContrApplyShopListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getApplyShopListId());
        
        if (dbZxCtSuppliesContrApplyShopList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApplyShopList.getApplyShopListId())) {
           // 租期单位
           dbZxCtSuppliesContrApplyShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
           // 租期
           dbZxCtSuppliesContrApplyShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
           // 要求修改
           dbZxCtSuppliesContrApplyShopList.setRequestEdit(zxCtSuppliesContrApplyShopList.getRequestEdit());
           // 修改日期
           dbZxCtSuppliesContrApplyShopList.setEditDate(zxCtSuppliesContrApplyShopList.getEditDate());
           // 修改人
           dbZxCtSuppliesContrApplyShopList.setEditUserName(zxCtSuppliesContrApplyShopList.getEditUserName());
           // 修改人
           dbZxCtSuppliesContrApplyShopList.setEditUserID(zxCtSuppliesContrApplyShopList.getEditUserID());
           // 物资名称
           dbZxCtSuppliesContrApplyShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrApplyShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrApplyShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
           // 物资规格
           dbZxCtSuppliesContrApplyShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
           // 物资编码ID
           dbZxCtSuppliesContrApplyShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrApplyShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrApplyShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
           // 税额
           dbZxCtSuppliesContrApplyShopList.setContractSumTax(zxCtSuppliesContrApplyShopList.getContractSumTax());
           // 数量
           dbZxCtSuppliesContrApplyShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
           // 是否网价
           dbZxCtSuppliesContrApplyShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
           // 是否抵扣
           dbZxCtSuppliesContrApplyShopList.setIsDeduct(zxCtSuppliesContrApplyShopList.getIsDeduct());
           // 实际开始时间
           dbZxCtSuppliesContrApplyShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrApplyShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
           // 评审ID
           dbZxCtSuppliesContrApplyShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
           // 界面展现类型
           dbZxCtSuppliesContrApplyShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrApplyShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrApplyShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
           // 合同ID
           dbZxCtSuppliesContrApplyShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
           // 含税合同单价
           dbZxCtSuppliesContrApplyShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
           // 单位
           dbZxCtSuppliesContrApplyShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
           // 单位
           dbZxCtSuppliesContrApplyShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
           // 不含税合同金额
           dbZxCtSuppliesContrApplyShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
           // 不含税合同单价
           dbZxCtSuppliesContrApplyShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrApplyShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
           // 变更后租期
           dbZxCtSuppliesContrApplyShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
           // 变更后税额
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumTax(zxCtSuppliesContrApplyShopList.getChangeContractSumTax());
           // 变更后数量
           dbZxCtSuppliesContrApplyShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrApplyShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrApplyShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrApplyShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());
           // 备注
           dbZxCtSuppliesContrApplyShopList.setRemarks(zxCtSuppliesContrApplyShopList.getRemarks());
           // 排序
           dbZxCtSuppliesContrApplyShopList.setSort(zxCtSuppliesContrApplyShopList.getSort());
           // 共通
           dbZxCtSuppliesContrApplyShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyShopListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//计算当前清单下所有金额，存到合同评审表中
        	applyShop = new ZxCtSuppliesContrApplyShopList();
        	applyShop.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
        	List<ZxCtSuppliesContrApplyShopList> shopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getPp5());
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
        	/****************临时代码，数据同步到物资清单表****************************/
        	 ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = zxCtSuppliesContrShopListMapper.selectByPrimaryKey("");
             if (zxCtSuppliesContrShopList != null && StrUtil.isNotEmpty(zxCtSuppliesContrShopList.getZxCtSuppliesContrShopListId())) {
                 zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
                 // 租期单位
                 zxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
                 // 租期
                 zxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
                 // 物资名称
                 zxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
                 // 物资类别ID
                 zxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
                 // 物资类别
                 zxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
                 // 物资编码ID
                 zxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
                 // 物资编码
                 zxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
                 // 税率(%)
                 zxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
                 // 数量
                 zxCtSuppliesContrShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
                 // 是否网价
                 zxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
                 // 实际开始时间
                 zxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
                 // 实际结束时间
                 zxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
                 // 评审ID
                 zxCtSuppliesContrShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
                 // 界面展现类型
                 zxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
                 // 计划开始时间
                 zxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
                 // 计划结束时间
                 zxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
                 // 合同ID
                 zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
                 // 含税金额
                 zxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
                 // 含税单价
                 zxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
                 // 规格型号
                 zxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
                 // 单位
                 zxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
                 // 单位
                 zxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
                 // 不含税金额
                 zxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
                 // 不含税单价
                 zxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
                 // 变更日期
                 zxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
                 // 变更后租期
                 zxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
                 // 变更后数量
                 zxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
                 // 变更后含税金额
                 zxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
                 // 变更后含税单价
                 zxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
                 // 变更后不含税金额
                 zxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
                 // 变更后不含税单价
                 zxCtSuppliesContrShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());            	 
                zxCtSuppliesContrShopList.setModifyUserInfo(userKey, realName);
                flag = zxCtSuppliesContrShopListMapper.updateByPrimaryKey(zxCtSuppliesContrShopList);     	
             }
            return repEntity.ok("sys.data.update",zxCtSuppliesContrApplyShopList);
        }
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)	
	public ResponseEntity saveZxCtSuppliesContrApplyShopListByApplyId(
			ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList();
        applyShop.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
        applyShop.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
        List<ZxCtSuppliesContrApplyShopList> applyShopList = zxCtSuppliesContrApplyShopListMapper.selectSuppliesContrApplyShopListByCondition(applyShop);
        if(applyShopList.size()>0) {
            return repEntity.layerMessage("no", "当前物资编码已存在，无法保存！");
        }
        zxCtSuppliesContrApplyShopList.setApplyShopListId(UuidUtil.generate());
        zxCtSuppliesContrApplyShopList.setCreateUserInfo(userKey, realName);
        ZxCtSuppliesContract contract = new ZxCtSuppliesContract();
        contract.setFromApplyID(zxCtSuppliesContrApplyShopList.getPp5());
        List<ZxCtSuppliesContract> contractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(contract);
        if(contractList.size()>0) {
        	zxCtSuppliesContrApplyShopList.setContractID(contractList.get(0).getZxCtSuppliesContractId());
        }
        int flag = zxCtSuppliesContrApplyShopListMapper.insert(zxCtSuppliesContrApplyShopList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//计算当前清单下所有金额，存到合同评审表中
        	applyShop = new ZxCtSuppliesContrApplyShopList();
        	applyShop.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
        	List<ZxCtSuppliesContrApplyShopList> shopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getPp5());
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
        	/****************临时代码，数据同步到物资清单表****************************/
//        	ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
//            zxCtSuppliesContrShopList.setZxCtSuppliesContrShopListId(UuidUtil.generate());
//            zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
//            // 租期单位
//            zxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
//            // 租期
//            zxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
//            // 物资名称
//            zxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
//            // 物资类别ID
//            zxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
//            // 物资类别
//            zxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
//            // 物资编码ID
//            zxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
//            // 物资编码
//            zxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
//            // 税率(%)
//            zxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
//            // 数量
//            zxCtSuppliesContrShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
//            // 是否网价
//            zxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
//            // 实际开始时间
//            zxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
//            // 实际结束时间
//            zxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
//            // 评审ID
//            zxCtSuppliesContrShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
//            // 界面展现类型
//            zxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
//            // 计划开始时间
//            zxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
//            // 计划结束时间
//            zxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
//            // 合同ID
//            zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
//            // 含税金额
//            zxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
//            // 含税单价
//            zxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
//            // 规格型号
//            zxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
//            // 单位
//            zxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
//            // 单位
//            zxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
//            // 不含税金额
//            zxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
//            // 不含税单价
//            zxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
//            // 变更日期
//            zxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
//            // 变更后租期
//            zxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
//            // 变更后数量
//            zxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
//            // 变更后含税金额
//            zxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
//            // 变更后含税单价
//            zxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
//            // 变更后不含税金额
//            zxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
//            // 变更后不含税单价
//            zxCtSuppliesContrShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());
//            // 备注
//            zxCtSuppliesContrShopList.setRemarks(zxCtSuppliesContrShopList.getRemarks());
//            zxCtSuppliesContrShopList.setCreateUserInfo(userKey, realName);
//            zxCtSuppliesContrShopListMapper.insert(zxCtSuppliesContrShopList);
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrApplyShopList);
        }
	}

	@Override
	public ResponseEntity batchDeleteZxCtSuppliesContrApplyShopListByApplyId(
			List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrApplyShopListList != null && zxCtSuppliesContrApplyShopListList.size() > 0) {
           ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList = new ZxCtSuppliesContrApplyShopList();
           zxCtSuppliesContrApplyShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyShopListMapper.batchDeleteUpdateZxCtSuppliesContrApplyShopList(zxCtSuppliesContrApplyShopListList, zxCtSuppliesContrApplyShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//删除成功后，重新计算金额
        	ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList();
        	applyShop.setPp5(zxCtSuppliesContrApplyShopListList.get(0).getPp5());
        	List<ZxCtSuppliesContrApplyShopList> shopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopListList.get(0).getPp5());
        	//含税合同金额
            double contractCost = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSum().doubleValue()).reduce(0, Double::sum);
            double contractCostNoTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumNoTax().doubleValue()).reduce(0, Double::sum);
            double contractCostTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumTax().doubleValue()).reduce(0, Double::sum);
        	apply.setContractCost(CalcUtils.calcDivide(new BigDecimal(contractCost), new BigDecimal(10000),6));
        	//不含税合同金额
        	apply.setContractCostNoTax(CalcUtils.calcDivide(new BigDecimal(contractCostNoTax), new BigDecimal(10000),6));
        	//合同税额
        	apply.setContractCostTax(CalcUtils.calcDivide(new BigDecimal(contractCostTax), new BigDecimal(10000),6));
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(apply);        	
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyShopListList);
        }
	}
}
