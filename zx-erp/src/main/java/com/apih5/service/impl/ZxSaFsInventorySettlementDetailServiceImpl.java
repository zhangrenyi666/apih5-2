package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlement;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import com.apih5.mybatis.pojo.ZxSaFsStatisticsDetail;
import com.apih5.utils.DigitalConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlementDetail;
import com.apih5.service.ZxSaFsInventorySettlementDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxSaFsInventorySettlementDetailService")
public class ZxSaFsInventorySettlementDetailServiceImpl implements ZxSaFsInventorySettlementDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementDetailMapper zxSaFsInventorySettlementDetailMapper;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementMapper zxSaFsInventorySettlementMapper;

    @Autowired(required = true)
    private ZxSaFsSettlementMapper zxSaFsSettlementMapper;

    @Autowired(required = true)
    private ZxSaFsStatisticsDetailMapper zxSaFsStatisticsDetailMapper;

    @Override
    public ResponseEntity getZxSaFsInventorySettlementDetailListByCondition(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        if (zxSaFsInventorySettlementDetail == null) {
            zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
        }
        // ????????????
        PageHelper.startPage(zxSaFsInventorySettlementDetail.getPage(), zxSaFsInventorySettlementDetail.getLimit());
        // ????????????
        List<ZxSaFsInventorySettlementDetail> zxSaFsInventorySettlementDetailList = zxSaFsInventorySettlementDetailMapper.selectByZxSaFsInventorySettlementDetailList(zxSaFsInventorySettlementDetail);
        // ??????????????????
        PageInfo<ZxSaFsInventorySettlementDetail> p = new PageInfo<>(zxSaFsInventorySettlementDetailList);

        return repEntity.okList(zxSaFsInventorySettlementDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsInventorySettlementDetailDetail(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        if (zxSaFsInventorySettlementDetail == null) {
            zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
        }
        // ????????????
        ZxSaFsInventorySettlementDetail dbZxSaFsInventorySettlementDetail = zxSaFsInventorySettlementDetailMapper.selectByPrimaryKey(zxSaFsInventorySettlementDetail.getZxSaFsEnumerationSettlementDetailedId());
        // ????????????
        if (dbZxSaFsInventorySettlementDetail != null) {
            return repEntity.ok(dbZxSaFsInventorySettlementDetail);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsInventorySettlementDetail(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsInventorySettlementDetail.setZxSaFsEnumerationSettlementDetailedId(UuidUtil.generate());
        zxSaFsInventorySettlementDetail.setCreateUserInfo(userKey, realName);
        int flag = zxSaFsInventorySettlementDetailMapper.insert(zxSaFsInventorySettlementDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaFsInventorySettlementDetail);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsInventorySettlementDetail(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) throws Exception {


        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;


        //zxSaFsInventorySettlementDetail.setThisAmt(CalcUtils.calcMultiply(zxSaFsInventorySettlementDetail.getThisPrice(),zxSaFsInventorySettlementDetail.getThisQty()));
        //zxSaFsInventorySettlementDetail.setThisAmtNoTax(CalcUtils.calcDivide(zxSaFsInventorySettlementDetail.getThisAmt(),CalcUtils.calcAdd(new BigDecimal("1"),CalcUtils.calcDivide(new BigDecimal(zxSaFsInventorySettlementDetail.getTaxRate()),new BigDecimal("100"))),6));
        //zxSaFsInventorySettlementDetail.setThisAmtTax(CalcUtils.calcSubtract(zxSaFsInventorySettlementDetail.getThisAmt(),zxSaFsInventorySettlementDetail.getThisAmtNoTax()));

        ZxSaFsInventorySettlementDetail dbZxSaFsInventorySettlementDetail = zxSaFsInventorySettlementDetailMapper.selectByPrimaryKey(zxSaFsInventorySettlementDetail.getZxSaFsEnumerationSettlementDetailedId());
        if (dbZxSaFsInventorySettlementDetail != null && StrUtil.isNotEmpty(dbZxSaFsInventorySettlementDetail.getZxSaFsEnumerationSettlementDetailedId())) {

            BigDecimal contartQty = dbZxSaFsInventorySettlementDetail.getChangedQty() == null ?
                    CalcUtils.calcSubtract(dbZxSaFsInventorySettlementDetail.getContractQty(), dbZxSaFsInventorySettlementDetail.getUpQty())
                    : CalcUtils.calcSubtract(dbZxSaFsInventorySettlementDetail.getChangedQty(), dbZxSaFsInventorySettlementDetail.getUpQty());
            if (CalcUtils.compareTo(zxSaFsInventorySettlementDetail.getThisQty(), contartQty) == 1) {
                return repEntity.layerMessage("no", "????????????????????????" + contartQty);
            }
            // ?????????????????????ID
            dbZxSaFsInventorySettlementDetail.setZxSaFsInventorySettlementId(zxSaFsInventorySettlementDetail.getZxSaFsInventorySettlementId());
            // ????????????
            dbZxSaFsInventorySettlementDetail.setPeriod(zxSaFsInventorySettlementDetail.getPeriod());
            // ???????????????
            dbZxSaFsInventorySettlementDetail.setBillNo(zxSaFsInventorySettlementDetail.getBillNo());
            // ??????ID
            dbZxSaFsInventorySettlementDetail.setZxCtFsContractId(zxSaFsInventorySettlementDetail.getZxCtFsContractId());
            // ??????
            dbZxSaFsInventorySettlementDetail.setOrderNum(zxSaFsInventorySettlementDetail.getOrderNum());
            // ?????????ID
            dbZxSaFsInventorySettlementDetail.setSignedOrderID(zxSaFsInventorySettlementDetail.getSignedOrderID());
            // ???????????????
            dbZxSaFsInventorySettlementDetail.setSignedNo(zxSaFsInventorySettlementDetail.getSignedNo());
            // ???????????????ID
            dbZxSaFsInventorySettlementDetail.setSignedOrderItemID(zxSaFsInventorySettlementDetail.getSignedOrderItemID());
            // ????????????ID
            dbZxSaFsInventorySettlementDetail.setConRevDetailId(zxSaFsInventorySettlementDetail.getConRevDetailId());
            // ????????????
            dbZxSaFsInventorySettlementDetail.setEquipCode(zxSaFsInventorySettlementDetail.getEquipCode());
            // ????????????
            dbZxSaFsInventorySettlementDetail.setEquipName(zxSaFsInventorySettlementDetail.getEquipName());
            // ??????
            dbZxSaFsInventorySettlementDetail.setSpec(zxSaFsInventorySettlementDetail.getSpec());
            // ????????????
            dbZxSaFsInventorySettlementDetail.setUnit(zxSaFsInventorySettlementDetail.getUnit());
            // ????????????
            dbZxSaFsInventorySettlementDetail.setStartDate(zxSaFsInventorySettlementDetail.getStartDate());
            // ????????????(???)
            dbZxSaFsInventorySettlementDetail.setContractPrice(zxSaFsInventorySettlementDetail.getContractPrice());
            // ????????????
            dbZxSaFsInventorySettlementDetail.setContractQty(zxSaFsInventorySettlementDetail.getContractQty());
            // ??????????????????(???)
            dbZxSaFsInventorySettlementDetail.setContractAmt(zxSaFsInventorySettlementDetail.getContractAmt());
            // ???????????????
            dbZxSaFsInventorySettlementDetail.setChangedQty(zxSaFsInventorySettlementDetail.getChangedQty());
            // ?????????????????????(???)
            dbZxSaFsInventorySettlementDetail.setChangedAmt(zxSaFsInventorySettlementDetail.getChangedAmt());
            // ??????????????????
            dbZxSaFsInventorySettlementDetail.setThisQty(zxSaFsInventorySettlementDetail.getThisQty());
            // ??????????????????(???)
            dbZxSaFsInventorySettlementDetail.setThisPrice(zxSaFsInventorySettlementDetail.getThisPrice());
            // ????????????????????????(???)
            dbZxSaFsInventorySettlementDetail.setThisAmt(zxSaFsInventorySettlementDetail.getThisAmt());
            // ??????????????????????????????
            dbZxSaFsInventorySettlementDetail.setUpQty(zxSaFsInventorySettlementDetail.getUpQty());
            // ????????????????????????????????????(???)
            dbZxSaFsInventorySettlementDetail.setUpAmt(zxSaFsInventorySettlementDetail.getUpAmt());
            // ??????????????????????????????
            dbZxSaFsInventorySettlementDetail.setTotalQty(zxSaFsInventorySettlementDetail.getTotalQty());
            // ????????????????????????????????????(???)
            dbZxSaFsInventorySettlementDetail.setTotalAmt(zxSaFsInventorySettlementDetail.getTotalAmt());
            // ??????????????????
            dbZxSaFsInventorySettlementDetail.setEditTime(zxSaFsInventorySettlementDetail.getEditTime());
            // ????????????ID
            dbZxSaFsInventorySettlementDetail.setComID(zxSaFsInventorySettlementDetail.getComID());
            // ??????????????????
            dbZxSaFsInventorySettlementDetail.setComName(zxSaFsInventorySettlementDetail.getComName());
            // ??????????????????
            dbZxSaFsInventorySettlementDetail.setComOrders(zxSaFsInventorySettlementDetail.getComOrders());
            // ???????????????(???)
            dbZxSaFsInventorySettlementDetail.setAlterPrice(zxSaFsInventorySettlementDetail.getAlterPrice());
            // ??????(%)
            dbZxSaFsInventorySettlementDetail.setTaxRate(zxSaFsInventorySettlementDetail.getTaxRate());
            // ???????????????????????????(???)
            dbZxSaFsInventorySettlementDetail.setThisAmtNoTax(zxSaFsInventorySettlementDetail.getThisAmtNoTax());
            // ??????????????????(???)
            dbZxSaFsInventorySettlementDetail.setThisAmtTax(zxSaFsInventorySettlementDetail.getThisAmtTax());
            // ??????
            dbZxSaFsInventorySettlementDetail.setRemarks(zxSaFsInventorySettlementDetail.getRemarks());
            // ??????
            dbZxSaFsInventorySettlementDetail.setSort(zxSaFsInventorySettlementDetail.getSort());
            // ??????
            dbZxSaFsInventorySettlementDetail.setModifyUserInfo(userKey, realName);
            dbZxSaFsInventorySettlementDetail.setThisQty(dbZxSaFsInventorySettlementDetail.getThisQty() == null ? new BigDecimal("0") : dbZxSaFsInventorySettlementDetail.getThisQty());
            dbZxSaFsInventorySettlementDetail.setThisAmt(CalcUtils.calcMultiply(zxSaFsInventorySettlementDetail.getContractPrice(), zxSaFsInventorySettlementDetail.getThisQty()));
            dbZxSaFsInventorySettlementDetail.setThisAmtNoTax(CalcUtils.calcDivide(dbZxSaFsInventorySettlementDetail.getThisAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxSaFsInventorySettlementDetail.getTaxRate()), new BigDecimal("100"), 2))));
            dbZxSaFsInventorySettlementDetail.setThisAmtTax(CalcUtils.calcSubtract(dbZxSaFsInventorySettlementDetail.getThisAmt(), dbZxSaFsInventorySettlementDetail.getThisAmtNoTax()));
            flag = syncUpdateZxSaFsInventorySettlement(dbZxSaFsInventorySettlementDetail);
            if (flag == 0) {
                return repEntity.layerMessage("no", "???????????????????????????????????????");
            }
            flag = zxSaFsInventorySettlementDetailMapper.updateByPrimaryKey(dbZxSaFsInventorySettlementDetail);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        } else {
            return repEntity.ok("sys.data.update", zxSaFsInventorySettlementDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaFsInventorySettlementDetail(List<ZxSaFsInventorySettlementDetail> zxSaFsInventorySettlementDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaFsInventorySettlementDetailList != null && zxSaFsInventorySettlementDetailList.size() > 0) {
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            zxSaFsInventorySettlementDetail.setModifyUserInfo(userKey, realName);
            flag = zxSaFsInventorySettlementDetailMapper.batchDeleteUpdateZxSaFsInventorySettlementDetail(zxSaFsInventorySettlementDetailList, zxSaFsInventorySettlementDetail);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaFsInventorySettlementDetailList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ?????????????????????
     *
     * @author suncg
     */
    @Override
    public ResponseEntity getYgzYiLan(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {
        if (zxSaFsInventorySettlementDetail == null) {
            zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
        }
        // ????????????
        PageHelper.startPage(zxSaFsInventorySettlementDetail.getPage(), zxSaFsInventorySettlementDetail.getLimit());

        // ????????????
       // List<ZxSaFsInventorySettlementDetail> zxSaFsInventorySettlementDetailList = zxSaFsInventorySettlementDetailMapper.YgzYiLan(zxSaFsInventorySettlementDetail);

        List<ZxSaFsInventorySettlementDetail> ygzList=zxSaFsInventorySettlementDetailMapper.YgzYiLan(zxSaFsInventorySettlementDetail);
        ZxSaFsInventorySettlementDetail xiaoji = new ZxSaFsInventorySettlementDetail();
        ZxSaFsInventorySettlementDetail heji = new ZxSaFsInventorySettlementDetail();
        BigDecimal contractSum =new BigDecimal("0");
        BigDecimal changeContractSum =new BigDecimal("0");
        BigDecimal upAmt =new BigDecimal("0");
        BigDecimal thisAmt =new BigDecimal("0");
        for (ZxSaFsInventorySettlementDetail zd: ygzList
        ) {
            contractSum=CalcUtils.calcAdd(contractSum,zd.getContractSum()) ;
            changeContractSum=CalcUtils.calcAdd(changeContractSum,zd.getChangeContractSum()) ;
            upAmt=CalcUtils.calcAdd(upAmt,zd.getUpAmt()) ;
            thisAmt=CalcUtils.calcAdd(thisAmt,zd.getThisAmt()) ;

        }
        xiaoji.setEquipCode("??????");
        xiaoji.setContractSum(contractSum);
        xiaoji.setChangeContractSum(changeContractSum);
        xiaoji.setUpAmt(upAmt);
        xiaoji.setThisAmt(thisAmt);


        heji.setEquipCode("??????");
        heji.setContractSum(contractSum);
        heji.setChangeContractSum(changeContractSum);
        heji.setUpAmt(upAmt);
        heji.setThisAmt(thisAmt);

        ygzList.add(xiaoji);
        ygzList.add(heji);

        // ??????????????????
        PageInfo<ZxSaFsInventorySettlementDetail> p = new PageInfo<>(ygzList);

        return repEntity.okList(ygzList, p.getTotal());
    }

    /**
     * ?????????????????????
     *
     * @author suncg
     */
    @Override
    public List<ZxSaFsInventorySettlementDetail> exportYgzYiLan(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) {

        List<ZxSaFsInventorySettlementDetail> ygzList=zxSaFsInventorySettlementDetailMapper.YgzYiLan(zxSaFsInventorySettlementDetail);
        ZxSaFsInventorySettlementDetail xiaoji = new ZxSaFsInventorySettlementDetail();
        ZxSaFsInventorySettlementDetail heji = new ZxSaFsInventorySettlementDetail();
        BigDecimal contractSum =new BigDecimal("0");
        BigDecimal changeContractSum =new BigDecimal("0");
        BigDecimal upAmt =new BigDecimal("0");
        BigDecimal thisAmt =new BigDecimal("0");
        for (ZxSaFsInventorySettlementDetail zd: ygzList
        ) {
            contractSum=CalcUtils.calcAdd(contractSum,zd.getContractSum()) ;
            changeContractSum=CalcUtils.calcAdd(changeContractSum,zd.getChangeContractSum()) ;
            upAmt=CalcUtils.calcAdd(upAmt,zd.getUpAmt()) ;
            thisAmt=CalcUtils.calcAdd(thisAmt,zd.getThisAmt()) ;

        }
        xiaoji.setEquipCode("??????");
        xiaoji.setContractSum(contractSum);
        xiaoji.setChangeContractSum(changeContractSum);
        xiaoji.setUpAmt(upAmt);
        xiaoji.setThisAmt(thisAmt);



        heji.setEquipCode("??????");
        heji.setContractSum(contractSum);
        heji.setChangeContractSum(changeContractSum);
        heji.setUpAmt(upAmt);
        heji.setThisAmt(thisAmt);

        ygzList.add(xiaoji);
        ygzList.add(heji);
        // ??????????????????
        //PageInfo<ZxSaFsInventorySettlementDetail> p = new PageInfo<>(zxSaFsInventorySettlementDetailList);

        return ygzList;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param zxSaFsInventorySettlementDetail
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public int syncUpdateZxSaFsInventorySettlement(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail) throws Exception {
        ZxSaFsInventorySettlement zxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectByPrimaryKey(zxSaFsInventorySettlementDetail.getZxSaFsInventorySettlementId());
        ZxSaFsInventorySettlementDetail InventoryDetail = zxSaFsInventorySettlementDetailMapper.selectByPrimaryKey(zxSaFsInventorySettlementDetail.getZxSaFsEnumerationSettlementDetailedId());
        if (zxSaFsInventorySettlement != null) {
            int flag = 0;
            BigDecimal oldAmt = InventoryDetail.getThisAmt() == null ? new BigDecimal("0") : InventoryDetail.getThisAmt();
            BigDecimal oldAmtNoTax = InventoryDetail.getThisAmtNoTax() == null ? new BigDecimal("0") : InventoryDetail.getThisAmtNoTax();

            BigDecimal inventoryOldAmt = zxSaFsInventorySettlement.getThisAmt() == null ? new BigDecimal("0") : zxSaFsInventorySettlement.getThisAmt();
            BigDecimal inventoryOldAmtNoTax = zxSaFsInventorySettlement.getThisAmtNoTax() == null ? new BigDecimal("0") : zxSaFsInventorySettlement.getThisAmtNoTax();
            BigDecimal inventoryOldTotalAmt = zxSaFsInventorySettlement.getTotalAmt() == null ? new BigDecimal("0") : zxSaFsInventorySettlement.getTotalAmt();
            //          contractCost  ??????????????????(??????) alterContractSum?????????????????????
//             pp4 ????????????????????????(??????) contractCostNoTax;?????????????????????(??????)
//             alterContractSumNoTax;  ????????????????????????  alterContractSumTax???????????????
//            pp4NoTax ???????????????????????????(??????) pp4Tax ??????????????????(??????) contractCostTax????????????(??????)
            BigDecimal wan = new BigDecimal("10000");
            // zxSaFsInventorySettlement.setChangeAmt(CalcUtils.calcAdd(CalcUtils.calcDivide(CalcUtils.calcSubtract(zxSaFsInventorySettlementDetail.getChangedAmt(),InventoryDetail.getChangedAmt()),wan),zxSaFsInventorySettlement.getChangeAmt()));
            zxSaFsInventorySettlement.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(
                    zxSaFsInventorySettlementDetail.getThisAmt(), oldAmt), zxSaFsInventorySettlement.getThisAmt()));
            zxSaFsInventorySettlement.setThisAmtNoTax(CalcUtils.calcAdd(CalcUtils.calcSubtract(
                    zxSaFsInventorySettlementDetail.getThisAmtNoTax(), oldAmtNoTax), zxSaFsInventorySettlement.getThisAmtNoTax()));
            zxSaFsInventorySettlement.setThisAmtTax(CalcUtils.calcSubtract(zxSaFsInventorySettlement.getThisAmt(), zxSaFsInventorySettlement.getThisAmtNoTax()));
            zxSaFsInventorySettlement.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(
                    zxSaFsInventorySettlementDetail.getThisAmt(), oldAmt), zxSaFsInventorySettlement.getTotalAmt()));
            flag = zxSaFsInventorySettlementMapper.updateByPrimaryKey(zxSaFsInventorySettlement);
            if (flag == 0) {
                throw new Apih5Exception("??????????????????????????????");
            }
            ZxSaFsSettlement zxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsInventorySettlement.getZxSaFsSettlementId());
            if (zxSaFsSettlement != null) {
                zxSaFsSettlement.setThisAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmt(), zxSaFsInventorySettlement.getThisAmt()), inventoryOldAmt));
                zxSaFsSettlement.setThisAmtNoTax(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmtNoTax(), zxSaFsInventorySettlement.getThisAmtNoTax()), inventoryOldAmtNoTax));
                zxSaFsSettlement.setThisAmtTax(CalcUtils.calcSubtract(zxSaFsSettlement.getThisAmt(), zxSaFsSettlement.getThisAmtNoTax()));
                zxSaFsSettlement.setTotalAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getTotalAmt(), zxSaFsInventorySettlement.getTotalAmt()), inventoryOldTotalAmt));
                flag = zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);
            } else {
                return 0;
            }

            if (flag == 0) {
                throw new Apih5Exception("??????????????????????????????");
            }
            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail = new ZxSaFsStatisticsDetail();
            zxSaFsStatisticsDetail.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            List<ZxSaFsStatisticsDetail> StaList = zxSaFsStatisticsDetailMapper.selectByZxSaFsStatisticsDetailList(zxSaFsStatisticsDetail);
            StaList.stream().filter(satis -> "100100".equals(satis.getStatisticsType())).forEach(o -> {
                o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(o.getTotalAmt()), zxSaFsSettlement.getThisAmt()), new BigDecimal(o.getThisAmt()))));
                o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmt()));
            });

//            StaList.stream().filter(satis -> "100200".equals(satis.getStatisticsType())).forEach(o->{
//                o.setTotalAmt( DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(o.getTotalAmt()),zxSaFsSettlement.getThisAmt()),new BigDecimal(o.getThisAmt()))));
//                o.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmt()));
//
//            });
            StaList.stream().filter(satis -> "100110".equals(satis.getStatisticsType())).forEach(o -> {
                o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(o.getTotalAmt()), zxSaFsSettlement.getThisAmtNoTax()), new BigDecimal(o.getThisAmt()))));
                o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmtNoTax()));

            });
//            StaList.stream().filter(satis -> "100210".equals(satis.getStatisticsType())).forEach(o->{
//                o.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(o.getTotalAmt()),zxSaFsSettlement.getThisAmtNoTax()),new BigDecimal(o.getThisAmt())) ));
//                o.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmtNoTax()));
//                //zxSaFsStatisticsDetailMapper.updateByPrimaryKey(o);
//            });
            StaList.stream().filter(satis -> "100120".equals(satis.getStatisticsType())).forEach(o -> {
                o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(o.getTotalAmt()), zxSaFsSettlement.getThisAmtTax()), new BigDecimal(o.getThisAmt()))));
                o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmtTax()));
            });
//            StaList.stream().filter(satis -> "100220".equals(satis.getStatisticsType())).forEach(o->{
//                o.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(o.getTotalAmt()),zxSaFsSettlement.getThisAmtTax()),new BigDecimal(o.getThisAmt())) ));
//                o.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmtTax()));
//            });

            StaList.stream().filter(satis -> 1 == satis.getSort()).forEach(o -> {
                BigDecimal total = new BigDecimal("0");
                BigDecimal thisAmt = new BigDecimal("0");

                if (o.getThisAmt() != null) {
                    thisAmt = new BigDecimal(o.getThisAmt());
                }
                o.setThisAmt(String.valueOf(CalcUtils.calcMultiply(o.getRate(), CalcUtils.calcDivide(zxSaFsInventorySettlement.getThisAmt(), new BigDecimal("100"), 2))));

                if (o.getTotalAmt() != null) {
                    total = new BigDecimal(o.getTotalAmt());
                }
                o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(total, new BigDecimal(o.getThisAmt())), thisAmt)));


            });

            flag = zxSaFsStatisticsDetailMapper.updateStatisticsDetail(StaList);
            if (flag == 0) {
                throw new Apih5Exception("??????????????????????????????");
            }

            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail2 = new ZxSaFsStatisticsDetail();
            zxSaFsStatisticsDetail2.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            zxSaFsStatisticsDetail2.setStatisticsType("100300");
            zxSaFsStatisticsDetail2.setSort(1);
            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail3 = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail2);
            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail4 = zxSaFsStatisticsDetailMapper.selectBaoZhengJinSum(zxSaFsStatisticsDetail2);
            if (zxSaFsStatisticsDetail4 != null) {
                zxSaFsStatisticsDetail3.setThisAmt(String.valueOf(zxSaFsStatisticsDetail4.getAmt()));
                zxSaFsStatisticsDetail3.setTotalAmt(String.valueOf(zxSaFsStatisticsDetail4.gettAmt()));
            }

            flag = zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetail3);
            if (flag == 0) {
                throw new Apih5Exception("??????????????????????????????");
            }

            //????????????????????????????????????????????????????????????
            ZxSaFsStatisticsDetail fanHuanBjz = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail yifuhetong = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail dXyifuhetong = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail dxHanShui = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail dxBuHanShui = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail dxShuiE = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail HanShui = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail BuHanShui = new ZxSaFsStatisticsDetail();
            ZxSaFsStatisticsDetail ShuiE = new ZxSaFsStatisticsDetail();

            fanHuanBjz.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            yifuhetong.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            dXyifuhetong.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            dxHanShui.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            dxBuHanShui.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            dxShuiE.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            HanShui.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            BuHanShui.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
            ShuiE.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());

            fanHuanBjz.setStatisticsType("100500");
            yifuhetong.setStatisticsType("100700");
            dXyifuhetong.setStatisticsType("100800");
            dxHanShui.setStatisticsType("100200");
            dxBuHanShui.setStatisticsType("100210");
            dxShuiE.setStatisticsType("100220");
            HanShui.setStatisticsType("100100");
            BuHanShui.setStatisticsType("100110");
            ShuiE.setStatisticsType("100120");

            fanHuanBjz = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(fanHuanBjz);
            yifuhetong = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(yifuhetong);
            dXyifuhetong = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(dXyifuhetong);
            dxHanShui = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(dxHanShui);
            dxBuHanShui = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(dxBuHanShui);
            dxShuiE = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(dxShuiE);
            HanShui = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(HanShui);
            BuHanShui = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(BuHanShui);
            ShuiE = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(ShuiE);


            BigDecimal fanHuanAmt = new BigDecimal("0");
            BigDecimal fanHuanTotalAmt = new BigDecimal("0");
            if (fanHuanBjz != null) {
                fanHuanAmt = fanHuanBjz.getThisAmt() == null ? new BigDecimal("0") : new BigDecimal(fanHuanBjz.getThisAmt());
                fanHuanTotalAmt = fanHuanBjz.getTotalAmt() == null ? new BigDecimal("0") : new BigDecimal(fanHuanBjz.getTotalAmt());
            }

            BigDecimal oldThisAmt = new BigDecimal("0");
            if (yifuhetong.getThisAmt() != null) {
                oldThisAmt = new BigDecimal(yifuhetong.getThisAmt());
            }
            yifuhetong.setThisAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmt(), fanHuanAmt), new BigDecimal(zxSaFsStatisticsDetail3.getThisAmt()))));
            yifuhetong.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(yifuhetong.getTotalAmt()), new BigDecimal(yifuhetong.getThisAmt())), oldThisAmt)));

            dXyifuhetong.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(yifuhetong.getThisAmt())));
            dXyifuhetong.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(yifuhetong.getTotalAmt())));
            dxHanShui.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(HanShui.getThisAmt())));
            dxHanShui.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(HanShui.getTotalAmt())));
            dxBuHanShui.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(BuHanShui.getThisAmt())));
            dxBuHanShui.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(BuHanShui.getTotalAmt())));
            dxShuiE.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(ShuiE.getThisAmt())));
            dxShuiE.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(ShuiE.getTotalAmt())));
            zxSaFsStatisticsDetailMapper.updateByPrimaryKey(yifuhetong);
            zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dxHanShui);
            zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dxBuHanShui);
            zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dxShuiE);
            zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dXyifuhetong);

            if (zxSaFsSettlement != null) {
                zxSaFsSettlement.setThisPayAmt(new BigDecimal(yifuhetong.getThisAmt()));
                zxSaFsSettlement.setTotalPayAmt(new BigDecimal(yifuhetong.getTotalAmt()));
                flag = zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);
            } else {
                return 0;
            }

            return flag;

        } else {
            return 0;
        }
    }


}
