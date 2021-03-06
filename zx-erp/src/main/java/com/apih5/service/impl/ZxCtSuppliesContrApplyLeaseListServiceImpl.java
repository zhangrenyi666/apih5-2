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
        // ????????????
        PageHelper.startPage(zxCtSuppliesContrApplyLeaseList.getPage(),zxCtSuppliesContrApplyLeaseList.getLimit());
        // ????????????
        List<ZxCtSuppliesContrApplyLeaseList> zxCtSuppliesContrApplyLeaseListList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(zxCtSuppliesContrApplyLeaseList);
        // ??????????????????
        PageInfo<ZxCtSuppliesContrApplyLeaseList> p = new PageInfo<>(zxCtSuppliesContrApplyLeaseListList);

        return repEntity.okList(zxCtSuppliesContrApplyLeaseListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrApplyLeaseListDetail(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList) {
        if (zxCtSuppliesContrApplyLeaseList == null) {
            zxCtSuppliesContrApplyLeaseList = new ZxCtSuppliesContrApplyLeaseList();
        }
        // ????????????
        ZxCtSuppliesContrApplyLeaseList dbZxCtSuppliesContrApplyLeaseList = zxCtSuppliesContrApplyLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getApplyLeaseListId());
        // ????????????
        if (dbZxCtSuppliesContrApplyLeaseList != null) {
            return repEntity.ok(dbZxCtSuppliesContrApplyLeaseList);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setRequestEdit(zxCtSuppliesContrApplyLeaseList.getRequestEdit());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setEditDate(zxCtSuppliesContrApplyLeaseList.getEditDate());
           // ?????????
           dbZxCtSuppliesContrApplyLeaseList.setEditUserID(zxCtSuppliesContrApplyLeaseList.getEditUserID());
           // ?????????
           dbZxCtSuppliesContrApplyLeaseList.setEditUserName(zxCtSuppliesContrApplyLeaseList.getEditUserName());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
           // ????????????ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
           // ????????????ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
           // ??????(%)
           dbZxCtSuppliesContrApplyLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setContractSumTax(zxCtSuppliesContrApplyLeaseList.getContractSumTax());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setIsDeduct(zxCtSuppliesContrApplyLeaseList.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
           // ???????????????
           dbZxCtSuppliesContrApplyLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
           // ???????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumTax());
           // ???????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
           // ????????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setRemarks(zxCtSuppliesContrApplyLeaseList.getRemarks());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setSort(zxCtSuppliesContrApplyLeaseList.getSort());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyLeaseListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyLeaseList);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyLeaseListList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
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
        		return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        	}
        }            
        ZxCtSuppliesContrApplyLeaseList dbZxCtSuppliesContrApplyLeaseList = zxCtSuppliesContrApplyLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getApplyLeaseListId());
        if (dbZxCtSuppliesContrApplyLeaseList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApplyLeaseList.getApplyLeaseListId())) {
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setRequestEdit(zxCtSuppliesContrApplyLeaseList.getRequestEdit());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setEditDate(zxCtSuppliesContrApplyLeaseList.getEditDate());
           // ?????????
           dbZxCtSuppliesContrApplyLeaseList.setEditUserID(zxCtSuppliesContrApplyLeaseList.getEditUserID());
           // ?????????
           dbZxCtSuppliesContrApplyLeaseList.setEditUserName(zxCtSuppliesContrApplyLeaseList.getEditUserName());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
           // ????????????ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
           // ????????????ID
           dbZxCtSuppliesContrApplyLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
           // ??????(%)
           dbZxCtSuppliesContrApplyLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setContractSumTax(zxCtSuppliesContrApplyLeaseList.getContractSumTax());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setIsDeduct(zxCtSuppliesContrApplyLeaseList.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
           // ??????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
           // ????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
           // ???????????????
           dbZxCtSuppliesContrApplyLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
           // ???????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumTax());
           // ???????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
           // ?????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
           // ????????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrApplyLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setRemarks(zxCtSuppliesContrApplyLeaseList.getRemarks());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setSort(zxCtSuppliesContrApplyLeaseList.getSort());
           // ??????
           dbZxCtSuppliesContrApplyLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyLeaseListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyLeaseList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//????????????????????????????????????????????????????????????
        	applyShop = new ZxCtSuppliesContrApplyLeaseList();
        	applyShop.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
        	List<ZxCtSuppliesContrApplyLeaseList> shopList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getPp5());
        	//??????????????????
            double contractCost = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSum().doubleValue()).reduce(0, Double::sum);
            double contractCostNoTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumNoTax().doubleValue()).reduce(0, Double::sum);
            double contractCostTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumTax().doubleValue()).reduce(0, Double::sum);
//        	apply.setContractCost(new BigDecimal(contractCost).setScale(6, RoundingMode.HALF_UP));
//        	//?????????????????????
//        	apply.setContractCostNoTax(new BigDecimal(contractCostNoTax).setScale(6, RoundingMode.HALF_UP));
//        	//????????????
//        	apply.setContractCostTax(new BigDecimal(contractCostTax).setScale(6, RoundingMode.HALF_UP));
        	apply.setContractCost(CalcUtils.calcDivide(new BigDecimal(contractCost), new BigDecimal(10000),6));
        	//?????????????????????
        	apply.setContractCostNoTax(CalcUtils.calcDivide(new BigDecimal(contractCostNoTax), new BigDecimal(10000),6));
        	//????????????
        	apply.setContractCostTax(CalcUtils.calcDivide(new BigDecimal(contractCostTax), new BigDecimal(10000),6));
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(apply);
        	/*************************?????????????????????????????????????????????**************************************/
//            ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList = zxCtSuppliesContrLeaseListMapper.selectByPrimaryKey("");
//            if (zxCtSuppliesContrLeaseList != null && StrUtil.isNotEmpty(zxCtSuppliesContrLeaseList.getZxCtSuppliesContrLeaseListId())) {
//                // ????????????
//                zxCtSuppliesContrLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
//                // ??????
//                zxCtSuppliesContrLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
//                // ????????????ID
//                zxCtSuppliesContrLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
//                // ????????????ID
//                zxCtSuppliesContrLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
//                // ??????(%)
//                zxCtSuppliesContrLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
//                // ??????
//                zxCtSuppliesContrLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
//                // ??????????????????
//                zxCtSuppliesContrLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
//                // ??????????????????
//                zxCtSuppliesContrLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
//                // ??????ID
//                zxCtSuppliesContrLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
//                // ??????????????????
//                zxCtSuppliesContrLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
//                // ??????????????????
//                zxCtSuppliesContrLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
//                // ??????????????????
//                zxCtSuppliesContrLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
//                // ??????ID
//                zxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
//                // ??????
//                zxCtSuppliesContrLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
//                // ??????
//                zxCtSuppliesContrLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
//                // ???????????????
//                zxCtSuppliesContrLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
//                // ???????????????
//                zxCtSuppliesContrLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
//                // ????????????
//                zxCtSuppliesContrLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
//                // ???????????????
//                zxCtSuppliesContrLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
//                // ???????????????
//                zxCtSuppliesContrLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
//                // ?????????????????????
//                zxCtSuppliesContrLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
//                // ?????????????????????
//                zxCtSuppliesContrLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
//                // ????????????????????????
//                zxCtSuppliesContrLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
//                // ????????????????????????
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
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
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
        	//????????????????????????????????????????????????????????????
        	applyShop = new ZxCtSuppliesContrApplyLeaseList();
        	applyShop.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
        	List<ZxCtSuppliesContrApplyLeaseList> shopList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyLeaseList.getPp5());
        	//??????????????????
            double contractCost = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSum().doubleValue()).reduce(0, Double::sum);
            double contractCostNoTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumNoTax().doubleValue()).reduce(0, Double::sum);
            double contractCostTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumTax().doubleValue()).reduce(0, Double::sum);
//        	apply.setContractCost(new BigDecimal(contractCost).setScale(6, RoundingMode.HALF_UP));
//        	//?????????????????????
//        	apply.setContractCostNoTax(new BigDecimal(contractCostNoTax).setScale(6, RoundingMode.HALF_UP));
//        	//????????????
//        	apply.setContractCostTax(new BigDecimal(contractCostTax).setScale(6, RoundingMode.HALF_UP));
        	apply.setContractCost(CalcUtils.calcDivide(new BigDecimal(contractCost), new BigDecimal(10000),6));
        	//?????????????????????
        	apply.setContractCostNoTax(CalcUtils.calcDivide(new BigDecimal(contractCostNoTax), new BigDecimal(10000),6));
        	//????????????
        	apply.setContractCostTax(CalcUtils.calcDivide(new BigDecimal(contractCostTax), new BigDecimal(10000),6));
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(apply);
        	/*************************?????????????????????????????????????????????**************************************/
//        	ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList = new ZxCtSuppliesContrLeaseList();
//            zxCtSuppliesContrLeaseList.setZxCtSuppliesContrLeaseListId(UuidUtil.generate());
//            zxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setRentUnit(zxCtSuppliesContrApplyLeaseList.getRentUnit());
//            // ??????
//            zxCtSuppliesContrLeaseList.setContrTrrm(zxCtSuppliesContrApplyLeaseList.getContrTrrm());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setWorkName(zxCtSuppliesContrApplyLeaseList.getWorkName());
//            // ????????????ID
//            zxCtSuppliesContrLeaseList.setWorkTypeID(zxCtSuppliesContrApplyLeaseList.getWorkTypeID());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setWorkType(zxCtSuppliesContrApplyLeaseList.getWorkType());
//            // ????????????ID
//            zxCtSuppliesContrLeaseList.setWorkID(zxCtSuppliesContrApplyLeaseList.getWorkID());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setWorkNo(zxCtSuppliesContrApplyLeaseList.getWorkNo());
//            // ??????(%)
//            zxCtSuppliesContrLeaseList.setTaxRate(zxCtSuppliesContrApplyLeaseList.getTaxRate());
//            // ??????
//            zxCtSuppliesContrLeaseList.setQty(zxCtSuppliesContrApplyLeaseList.getQty());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setIsNetPrice(zxCtSuppliesContrApplyLeaseList.getIsNetPrice());
//            // ??????????????????
//            zxCtSuppliesContrLeaseList.setActualStartDate(zxCtSuppliesContrApplyLeaseList.getActualStartDate());
//            // ??????????????????
//            zxCtSuppliesContrLeaseList.setActualEndDate(zxCtSuppliesContrApplyLeaseList.getActualEndDate());
//            // ??????ID
//            zxCtSuppliesContrLeaseList.setPp5(zxCtSuppliesContrApplyLeaseList.getPp5());
//            // ??????????????????
//            zxCtSuppliesContrLeaseList.setViewType(zxCtSuppliesContrApplyLeaseList.getViewType());
//            // ??????????????????
//            zxCtSuppliesContrLeaseList.setPlanStartDate(zxCtSuppliesContrApplyLeaseList.getPlanStartDate());
//            // ??????????????????
//            zxCtSuppliesContrLeaseList.setPlanEndDate(zxCtSuppliesContrApplyLeaseList.getPlanEndDate());
//            // ??????ID
//            zxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrApplyLeaseList.getContractID());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setContractSum(zxCtSuppliesContrApplyLeaseList.getContractSum());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setPrice(zxCtSuppliesContrApplyLeaseList.getPrice());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setSpec(zxCtSuppliesContrApplyLeaseList.getSpec());
//            // ??????
//            zxCtSuppliesContrLeaseList.setTreenode(zxCtSuppliesContrApplyLeaseList.getTreenode());
//            // ??????
//            zxCtSuppliesContrLeaseList.setUnit(zxCtSuppliesContrApplyLeaseList.getUnit());
//            // ???????????????
//            zxCtSuppliesContrLeaseList.setContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getContractSumNoTax());
//            // ???????????????
//            zxCtSuppliesContrLeaseList.setPriceNoTax(zxCtSuppliesContrApplyLeaseList.getPriceNoTax());
//            // ????????????
//            zxCtSuppliesContrLeaseList.setChangeDate(zxCtSuppliesContrApplyLeaseList.getChangeDate());
//            // ???????????????
//            zxCtSuppliesContrLeaseList.setAlterTrrm(zxCtSuppliesContrApplyLeaseList.getAlterTrrm());
//            // ???????????????
//            zxCtSuppliesContrLeaseList.setChangeQty(zxCtSuppliesContrApplyLeaseList.getChangeQty());
//            // ?????????????????????
//            zxCtSuppliesContrLeaseList.setChangeContractSum(zxCtSuppliesContrApplyLeaseList.getChangeContractSum());
//            // ?????????????????????
//            zxCtSuppliesContrLeaseList.setChangePrice(zxCtSuppliesContrApplyLeaseList.getChangePrice());
//            // ????????????????????????
//            zxCtSuppliesContrLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrApplyLeaseList.getChangeContractSumNoTax());
//            // ????????????????????????
//            zxCtSuppliesContrLeaseList.setChangePriceNoTax(zxCtSuppliesContrApplyLeaseList.getChangePriceNoTax());            
//            zxCtSuppliesContrLeaseList.setCreateUserInfo(userKey, realName);
//            zxCtSuppliesContrLeaseListMapper.insert(zxCtSuppliesContrLeaseList);
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrApplyLeaseList);
        }
	}
}
