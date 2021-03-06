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
        // ????????????
        PageHelper.startPage(zxCtSuppliesContrApplyShopList.getPage(),zxCtSuppliesContrApplyShopList.getLimit());
        // ????????????
        List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(zxCtSuppliesContrApplyShopList);
        // ??????????????????
        PageInfo<ZxCtSuppliesContrApplyShopList> p = new PageInfo<>(zxCtSuppliesContrApplyShopListList);

        return repEntity.okList(zxCtSuppliesContrApplyShopListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrApplyShopListDetail(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList) {
        if (zxCtSuppliesContrApplyShopList == null) {
            zxCtSuppliesContrApplyShopList = new ZxCtSuppliesContrApplyShopList();
        }
        // ????????????
        ZxCtSuppliesContrApplyShopList dbZxCtSuppliesContrApplyShopList = zxCtSuppliesContrApplyShopListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getApplyShopListId());
        // ????????????
        if (dbZxCtSuppliesContrApplyShopList != null) {
            return repEntity.ok(dbZxCtSuppliesContrApplyShopList);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setRequestEdit(zxCtSuppliesContrApplyShopList.getRequestEdit());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setEditDate(zxCtSuppliesContrApplyShopList.getEditDate());
           // ?????????
           dbZxCtSuppliesContrApplyShopList.setEditUserName(zxCtSuppliesContrApplyShopList.getEditUserName());
           // ?????????
           dbZxCtSuppliesContrApplyShopList.setEditUserID(zxCtSuppliesContrApplyShopList.getEditUserID());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
           // ????????????ID
           dbZxCtSuppliesContrApplyShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
           // ????????????ID
           dbZxCtSuppliesContrApplyShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
           // ??????(%)
           dbZxCtSuppliesContrApplyShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setContractSumTax(zxCtSuppliesContrApplyShopList.getContractSumTax());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setIsDeduct(zxCtSuppliesContrApplyShopList.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
           // ???????????????
           dbZxCtSuppliesContrApplyShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
           // ???????????????
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumTax(zxCtSuppliesContrApplyShopList.getChangeContractSumTax());
           // ???????????????
           dbZxCtSuppliesContrApplyShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
           // ????????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setRemarks(zxCtSuppliesContrApplyShopList.getRemarks());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setSort(zxCtSuppliesContrApplyShopList.getSort());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyShopListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyShopList);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyShopListList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    

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
        		return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        	}
        }        
        ZxCtSuppliesContrApplyShopList dbZxCtSuppliesContrApplyShopList = zxCtSuppliesContrApplyShopListMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getApplyShopListId());
        
        if (dbZxCtSuppliesContrApplyShopList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApplyShopList.getApplyShopListId())) {
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setRequestEdit(zxCtSuppliesContrApplyShopList.getRequestEdit());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setEditDate(zxCtSuppliesContrApplyShopList.getEditDate());
           // ?????????
           dbZxCtSuppliesContrApplyShopList.setEditUserName(zxCtSuppliesContrApplyShopList.getEditUserName());
           // ?????????
           dbZxCtSuppliesContrApplyShopList.setEditUserID(zxCtSuppliesContrApplyShopList.getEditUserID());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
           // ????????????ID
           dbZxCtSuppliesContrApplyShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
           // ????????????ID
           dbZxCtSuppliesContrApplyShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
           // ??????(%)
           dbZxCtSuppliesContrApplyShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setContractSumTax(zxCtSuppliesContrApplyShopList.getContractSumTax());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setIsDeduct(zxCtSuppliesContrApplyShopList.getIsDeduct());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
           // ??????ID
           dbZxCtSuppliesContrApplyShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
           // ??????????????????
           dbZxCtSuppliesContrApplyShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
           // ????????????
           dbZxCtSuppliesContrApplyShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
           // ???????????????
           dbZxCtSuppliesContrApplyShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
           // ???????????????
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumTax(zxCtSuppliesContrApplyShopList.getChangeContractSumTax());
           // ???????????????
           dbZxCtSuppliesContrApplyShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
           // ?????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
           // ????????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrApplyShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setRemarks(zxCtSuppliesContrApplyShopList.getRemarks());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setSort(zxCtSuppliesContrApplyShopList.getSort());
           // ??????
           dbZxCtSuppliesContrApplyShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyShopListMapper.updateByPrimaryKey(dbZxCtSuppliesContrApplyShopList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//????????????????????????????????????????????????????????????
        	applyShop = new ZxCtSuppliesContrApplyShopList();
        	applyShop.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
        	List<ZxCtSuppliesContrApplyShopList> shopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getPp5());
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
        	/****************?????????????????????????????????????????????****************************/
        	 ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = zxCtSuppliesContrShopListMapper.selectByPrimaryKey("");
             if (zxCtSuppliesContrShopList != null && StrUtil.isNotEmpty(zxCtSuppliesContrShopList.getZxCtSuppliesContrShopListId())) {
                 zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
                 // ????????????
                 zxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
                 // ??????
                 zxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
                 // ????????????
                 zxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
                 // ????????????ID
                 zxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
                 // ????????????
                 zxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
                 // ????????????ID
                 zxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
                 // ????????????
                 zxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
                 // ??????(%)
                 zxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
                 // ??????
                 zxCtSuppliesContrShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
                 // ????????????
                 zxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
                 // ??????????????????
                 zxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
                 // ??????????????????
                 zxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
                 // ??????ID
                 zxCtSuppliesContrShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
                 // ??????????????????
                 zxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
                 // ??????????????????
                 zxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
                 // ??????????????????
                 zxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
                 // ??????ID
                 zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
                 // ????????????
                 zxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
                 // ????????????
                 zxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
                 // ????????????
                 zxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
                 // ??????
                 zxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
                 // ??????
                 zxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
                 // ???????????????
                 zxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
                 // ???????????????
                 zxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
                 // ????????????
                 zxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
                 // ???????????????
                 zxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
                 // ???????????????
                 zxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
                 // ?????????????????????
                 zxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
                 // ?????????????????????
                 zxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
                 // ????????????????????????
                 zxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
                 // ????????????????????????
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
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
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
        	//????????????????????????????????????????????????????????????
        	applyShop = new ZxCtSuppliesContrApplyShopList();
        	applyShop.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
        	List<ZxCtSuppliesContrApplyShopList> shopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopList.getPp5());
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
        	/****************?????????????????????????????????????????????****************************/
//        	ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
//            zxCtSuppliesContrShopList.setZxCtSuppliesContrShopListId(UuidUtil.generate());
//            zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
//            // ????????????
//            zxCtSuppliesContrShopList.setRentUnit(zxCtSuppliesContrApplyShopList.getRentUnit());
//            // ??????
//            zxCtSuppliesContrShopList.setContrTrrm(zxCtSuppliesContrApplyShopList.getContrTrrm());
//            // ????????????
//            zxCtSuppliesContrShopList.setWorkName(zxCtSuppliesContrApplyShopList.getWorkName());
//            // ????????????ID
//            zxCtSuppliesContrShopList.setWorkTypeID(zxCtSuppliesContrApplyShopList.getWorkTypeID());
//            // ????????????
//            zxCtSuppliesContrShopList.setWorkType(zxCtSuppliesContrApplyShopList.getWorkType());
//            // ????????????ID
//            zxCtSuppliesContrShopList.setWorkID(zxCtSuppliesContrApplyShopList.getWorkID());
//            // ????????????
//            zxCtSuppliesContrShopList.setWorkNo(zxCtSuppliesContrApplyShopList.getWorkNo());
//            // ??????(%)
//            zxCtSuppliesContrShopList.setTaxRate(zxCtSuppliesContrApplyShopList.getTaxRate());
//            // ??????
//            zxCtSuppliesContrShopList.setQty(zxCtSuppliesContrApplyShopList.getQty());
//            // ????????????
//            zxCtSuppliesContrShopList.setIsNetPrice(zxCtSuppliesContrApplyShopList.getIsNetPrice());
//            // ??????????????????
//            zxCtSuppliesContrShopList.setActualStartDate(zxCtSuppliesContrApplyShopList.getActualStartDate());
//            // ??????????????????
//            zxCtSuppliesContrShopList.setActualEndDate(zxCtSuppliesContrApplyShopList.getActualEndDate());
//            // ??????ID
//            zxCtSuppliesContrShopList.setPp5(zxCtSuppliesContrApplyShopList.getPp5());
//            // ??????????????????
//            zxCtSuppliesContrShopList.setViewType(zxCtSuppliesContrApplyShopList.getViewType());
//            // ??????????????????
//            zxCtSuppliesContrShopList.setPlanStartDate(zxCtSuppliesContrApplyShopList.getPlanStartDate());
//            // ??????????????????
//            zxCtSuppliesContrShopList.setPlanEndDate(zxCtSuppliesContrApplyShopList.getPlanEndDate());
//            // ??????ID
//            zxCtSuppliesContrShopList.setContractID(zxCtSuppliesContrApplyShopList.getContractID());
//            // ????????????
//            zxCtSuppliesContrShopList.setContractSum(zxCtSuppliesContrApplyShopList.getContractSum());
//            // ????????????
//            zxCtSuppliesContrShopList.setPrice(zxCtSuppliesContrApplyShopList.getPrice());
//            // ????????????
//            zxCtSuppliesContrShopList.setSpec(zxCtSuppliesContrApplyShopList.getSpec());
//            // ??????
//            zxCtSuppliesContrShopList.setUnit(zxCtSuppliesContrApplyShopList.getUnit());
//            // ??????
//            zxCtSuppliesContrShopList.setTreenode(zxCtSuppliesContrApplyShopList.getTreenode());
//            // ???????????????
//            zxCtSuppliesContrShopList.setContractSumNoTax(zxCtSuppliesContrApplyShopList.getContractSumNoTax());
//            // ???????????????
//            zxCtSuppliesContrShopList.setPriceNoTax(zxCtSuppliesContrApplyShopList.getPriceNoTax());
//            // ????????????
//            zxCtSuppliesContrShopList.setChangeDate(zxCtSuppliesContrApplyShopList.getChangeDate());
//            // ???????????????
//            zxCtSuppliesContrShopList.setAlterTrrm(zxCtSuppliesContrApplyShopList.getAlterTrrm());
//            // ???????????????
//            zxCtSuppliesContrShopList.setChangeQty(zxCtSuppliesContrApplyShopList.getChangeQty());
//            // ?????????????????????
//            zxCtSuppliesContrShopList.setChangeContractSum(zxCtSuppliesContrApplyShopList.getChangeContractSum());
//            // ?????????????????????
//            zxCtSuppliesContrShopList.setChangePrice(zxCtSuppliesContrApplyShopList.getChangePrice());
//            // ????????????????????????
//            zxCtSuppliesContrShopList.setChangeContractSumNoTax(zxCtSuppliesContrApplyShopList.getChangeContractSumNoTax());
//            // ????????????????????????
//            zxCtSuppliesContrShopList.setChangePriceNoTax(zxCtSuppliesContrApplyShopList.getChangePriceNoTax());
//            // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//????????????????????????????????????
        	ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList();
        	applyShop.setPp5(zxCtSuppliesContrApplyShopListList.get(0).getPp5());
        	List<ZxCtSuppliesContrApplyShopList> shopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        	ZxCtSuppliesContrApply apply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApplyShopListList.get(0).getPp5());
        	//??????????????????
            double contractCost = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSum().doubleValue()).reduce(0, Double::sum);
            double contractCostNoTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumNoTax().doubleValue()).reduce(0, Double::sum);
            double contractCostTax = shopList.stream().mapToDouble(ZxCtSuppliesContrApplyShopList->ZxCtSuppliesContrApplyShopList.getContractSumTax().doubleValue()).reduce(0, Double::sum);
        	apply.setContractCost(CalcUtils.calcDivide(new BigDecimal(contractCost), new BigDecimal(10000),6));
        	//?????????????????????
        	apply.setContractCostNoTax(CalcUtils.calcDivide(new BigDecimal(contractCostNoTax), new BigDecimal(10000),6));
        	//????????????
        	apply.setContractCostTax(CalcUtils.calcDivide(new BigDecimal(contractCostTax), new BigDecimal(10000),6));
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(apply);        	
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyShopListList);
        }
	}
}
