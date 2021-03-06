package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSaFsPaySettlementMapper;
import com.apih5.mybatis.dao.ZxSaFsSettlementMapper;
import com.apih5.mybatis.dao.ZxSaFsStatisticsDetailMapper;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlement;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import com.apih5.mybatis.pojo.ZxSaFsStatisticsDetail;
import com.apih5.utils.DigitalConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaFsPaySettlementDetailMapper;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlementDetail;
import com.apih5.service.ZxSaFsPaySettlementDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxSaFsPaySettlementDetailService")
public class ZxSaFsPaySettlementDetailServiceImpl implements ZxSaFsPaySettlementDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsPaySettlementDetailMapper zxSaFsPaySettlementDetailMapper;

    @Autowired(required = true)
    private ZxSaFsPaySettlementMapper zxSaFsInventorySettlementMapper;

    @Autowired(required = true)
    private ZxSaFsSettlementMapper zxSaFsSettlementMapper;

    @Autowired(required = true)
    private ZxSaFsStatisticsDetailMapper zxSaFsStatisticsDetailMapper;

    @Override
    public ResponseEntity getZxSaFsPaySettlementDetailListByCondition(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) {
        if (zxSaFsPaySettlementDetail == null) {
            zxSaFsPaySettlementDetail = new ZxSaFsPaySettlementDetail();
        }
        // ????????????
        PageHelper.startPage(zxSaFsPaySettlementDetail.getPage(), zxSaFsPaySettlementDetail.getLimit());
        // ????????????
        List<ZxSaFsPaySettlementDetail> zxSaFsPaySettlementDetailList = zxSaFsPaySettlementDetailMapper.selectByZxSaFsPaySettlementDetailList(zxSaFsPaySettlementDetail);
        // ??????????????????
        PageInfo<ZxSaFsPaySettlementDetail> p = new PageInfo<>(zxSaFsPaySettlementDetailList);

        return repEntity.okList(zxSaFsPaySettlementDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsPaySettlementDetailDetail(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) {
        if (zxSaFsPaySettlementDetail == null) {
            zxSaFsPaySettlementDetail = new ZxSaFsPaySettlementDetail();
        }
        // ????????????
        ZxSaFsPaySettlementDetail dbZxSaFsPaySettlementDetail = zxSaFsPaySettlementDetailMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementDetailId());
        // ????????????
        if (dbZxSaFsPaySettlementDetail != null) {
            return repEntity.ok(dbZxSaFsPaySettlementDetail);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsPaySettlementDetail(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        int flag = 0;
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsPaySettlementDetail.setZxSaFsPaySettlementDetailId(UuidUtil.generate());
        zxSaFsPaySettlementDetail.setCreateUserInfo(userKey, realName);
        ZxSaFsPaySettlementDetail zxSaFsPayDetail = new ZxSaFsPaySettlementDetail();
        zxSaFsPayDetail.setPayName(zxSaFsPaySettlementDetail.getPayName());
        zxSaFsPayDetail.setZxSaFsSettlementId(zxSaFsPaySettlementDetail.getZxSaFsSettlementId());
        List<ZxSaFsPaySettlementDetail> ZxSaFsPaySettlementDetails = zxSaFsPaySettlementDetailMapper.selectByZxSaFsPaySettlementDetailList(zxSaFsPayDetail);
        if (ZxSaFsPaySettlementDetails.size() > 0) {
            return repEntity.layerMessage("no", "?????????????????????????????????");
        }

        flag = synUpdatePaySettlementSave(zxSaFsPaySettlementDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        }

        zxSaFsPaySettlementDetailMapper.insert(zxSaFsPaySettlementDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaFsPaySettlementDetail);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsPaySettlementDetail(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaFsPaySettlementDetail dbZxSaFsPaySettlementDetail = zxSaFsPaySettlementDetailMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementDetailId());
        if (dbZxSaFsPaySettlementDetail != null && StrUtil.isNotEmpty(dbZxSaFsPaySettlementDetail.getZxSaFsPaySettlementDetailId())) {
            // ??????ID
            dbZxSaFsPaySettlementDetail.setZxSaFsSettlementId(zxSaFsPaySettlementDetail.getZxSaFsSettlementId());
            // ????????????
            dbZxSaFsPaySettlementDetail.setPeriod(zxSaFsPaySettlementDetail.getPeriod());
            // ???????????????
            dbZxSaFsPaySettlementDetail.setBillNo(zxSaFsPaySettlementDetail.getBillNo());
            // ??????ID
            dbZxSaFsPaySettlementDetail.setZxCtFsContractId(zxSaFsPaySettlementDetail.getZxCtFsContractId());
            // ??????
            dbZxSaFsPaySettlementDetail.setOrderNum(zxSaFsPaySettlementDetail.getOrderNum());
            // ??????
            dbZxSaFsPaySettlementDetail.setPayNo(zxSaFsPaySettlementDetail.getPayNo());
            // ???????????????
            dbZxSaFsPaySettlementDetail.setPayType(zxSaFsPaySettlementDetail.getPayType());
            // ??????
            dbZxSaFsPaySettlementDetail.setPayName(zxSaFsPaySettlementDetail.getPayName());
            // ??????
            dbZxSaFsPaySettlementDetail.setUnit(zxSaFsPaySettlementDetail.getUnit());
            // ??????
            dbZxSaFsPaySettlementDetail.setQty(zxSaFsPaySettlementDetail.getQty());
            // ??????(???)
            dbZxSaFsPaySettlementDetail.setPrice(zxSaFsPaySettlementDetail.getPrice());
            // ??????????????????(???)
            dbZxSaFsPaySettlementDetail.setThisAmt(zxSaFsPaySettlementDetail.getThisAmt());
            // ?????????????????????(???)
            dbZxSaFsPaySettlementDetail.setUpAmt(zxSaFsPaySettlementDetail.getUpAmt());
            // ??????????????????
            dbZxSaFsPaySettlementDetail.setEditTime(zxSaFsPaySettlementDetail.getEditTime());
            // ????????????ID
            dbZxSaFsPaySettlementDetail.setComID(zxSaFsPaySettlementDetail.getComID());
            // ??????????????????
            dbZxSaFsPaySettlementDetail.setComName(zxSaFsPaySettlementDetail.getComName());
            // ??????????????????
            dbZxSaFsPaySettlementDetail.setComOrders(zxSaFsPaySettlementDetail.getComOrders());
            // ???????????????
            dbZxSaFsPaySettlementDetail.setIsFixed(zxSaFsPaySettlementDetail.getIsFixed());
            // ???????????????
            dbZxSaFsPaySettlementDetail.setUpQty(zxSaFsPaySettlementDetail.getUpQty());
            // ????????????
            dbZxSaFsPaySettlementDetail.setContrType(zxSaFsPaySettlementDetail.getContrType());
            // ??????(%)
            dbZxSaFsPaySettlementDetail.setTaxRate(zxSaFsPaySettlementDetail.getTaxRate());
            // ???????????????????????????(???)
            dbZxSaFsPaySettlementDetail.setThisAmtNoTax(zxSaFsPaySettlementDetail.getThisAmtNoTax());
            // ??????????????????(???)
            dbZxSaFsPaySettlementDetail.setThisAmtTax(zxSaFsPaySettlementDetail.getThisAmtTax());
            // ?????????Id
            dbZxSaFsPaySettlementDetail.setPayId(zxSaFsPaySettlementDetail.getPayId());
            // ???????????????Id
            dbZxSaFsPaySettlementDetail.setZxSaFsPaySettlementId(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementId());
            // ??????
            dbZxSaFsPaySettlementDetail.setRemarks(zxSaFsPaySettlementDetail.getRemarks());
            // ??????
            dbZxSaFsPaySettlementDetail.setSort(zxSaFsPaySettlementDetail.getSort());
            // ??????
            dbZxSaFsPaySettlementDetail.setModifyUserInfo(userKey, realName);
            flag = synUpdatePaySettlement(dbZxSaFsPaySettlementDetail);
            if (flag == 0) {
                return repEntity.errorSave();
            }
            flag = zxSaFsPaySettlementDetailMapper.updateByPrimaryKey(dbZxSaFsPaySettlementDetail);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaFsPaySettlementDetail);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteUpdateZxSaFsPaySettlementDetail(List<ZxSaFsPaySettlementDetail> zxSaFsPaySettlementDetailList) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaFsPaySettlementDetailList != null && zxSaFsPaySettlementDetailList.size() > 0) {
            ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail = new ZxSaFsPaySettlementDetail();
            zxSaFsPaySettlementDetail.setModifyUserInfo(userKey, realName);
            for (ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail1 :
                    zxSaFsPaySettlementDetailList) {
                flag = synUpdatePaySettlementDelete(zxSaFsPaySettlementDetail1);
                if (flag == 0) {
                    throw new Apih5Exception("??????????????????????????????!");
                }
            }

            flag = zxSaFsPaySettlementDetailMapper.batchDeleteUpdateZxSaFsPaySettlementDetail(zxSaFsPaySettlementDetailList, zxSaFsPaySettlementDetail);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaFsPaySettlementDetailList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public int synUpdatePaySettlementSave(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        int flag = 0;
        ZxSaFsPaySettlement zxSaFsPaySettlement = zxSaFsInventorySettlementMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementId());
        ZxSaFsSettlement zxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsPaySettlement.getZxSaFsSettlementId());
        if (zxSaFsPaySettlement != null) {
            zxSaFsPaySettlement.setThisAmt(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmt(), zxSaFsPaySettlement.getThisAmt()));
            zxSaFsPaySettlement.setThisAmtNoTax(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmtNoTax(), zxSaFsPaySettlement.getThisAmtNoTax()));
            zxSaFsPaySettlement.setThisAmtTax(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmtTax(), zxSaFsPaySettlement.getThisAmtTax()));
            zxSaFsPaySettlement.setTotalAmt(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmt(), zxSaFsPaySettlement.getTotalAmt()));

            zxSaFsSettlement.setThisAmt(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmt(), zxSaFsPaySettlementDetail.getThisAmt()));
            zxSaFsSettlement.setThisAmtNoTax(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmtNoTax(), zxSaFsPaySettlementDetail.getThisAmtNoTax()));
            zxSaFsSettlement.setThisAmtTax(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmtTax(), zxSaFsPaySettlementDetail.getThisAmtTax()));
            zxSaFsSettlement.setTotalAmt(CalcUtils.calcAdd(zxSaFsSettlement.getTotalAmt(), zxSaFsPaySettlementDetail.getThisAmt()));
            zxSaFsSettlement.setTotalAmtNoTax(CalcUtils.calcAdd(zxSaFsSettlement.getTotalAmtNoTax(), zxSaFsPaySettlementDetail.getThisAmtNoTax()));
            flag = zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);
            if (flag == 0) {
                throw new Apih5Exception("????????????????????????");
            }
            flag = zxSaFsInventorySettlementMapper.updateByPrimaryKey(zxSaFsPaySettlement);
            if (flag == 0) {
                throw new Apih5Exception("??????????????????????????????");
            }
            flag = syncUpdateStatisDetail(zxSaFsSettlement);
            if (flag == 0) {
                throw new Apih5Exception("????????????????????????");
            }
            return flag;
        } else {
            return 0;
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public int synUpdatePaySettlement(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        int flag = 0;
        ZxSaFsPaySettlement zxSaFsPaySettlement = zxSaFsInventorySettlementMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementId());
        ZxSaFsPaySettlement zxSaFsPaySettlement1 = zxSaFsInventorySettlementMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementId());
        ZxSaFsPaySettlementDetail PaySettlementDetail = zxSaFsPaySettlementDetailMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementDetailId());
        ZxSaFsSettlement zxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsPaySettlement.getZxSaFsSettlementId());
        if (zxSaFsPaySettlement != null) {
            zxSaFsPaySettlement.setThisAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmt(), zxSaFsPaySettlement.getThisAmt()), PaySettlementDetail.getThisAmt()));
            zxSaFsPaySettlement.setThisAmtNoTax(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmtNoTax(), zxSaFsPaySettlement.getThisAmtNoTax()), PaySettlementDetail.getThisAmtNoTax()));
            zxSaFsPaySettlement.setThisAmtTax(CalcUtils.calcSubtract(zxSaFsPaySettlement.getThisAmt(), zxSaFsPaySettlement.getThisAmtNoTax()));
            zxSaFsPaySettlement.setTotalAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsPaySettlementDetail.getThisAmt(), zxSaFsPaySettlement.getTotalAmt()), PaySettlementDetail.getThisAmt()));

            zxSaFsSettlement.setThisAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmt(), zxSaFsPaySettlement.getThisAmt()), zxSaFsPaySettlement1.getThisAmt()));
            zxSaFsSettlement.setThisAmtNoTax(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmtNoTax(), zxSaFsPaySettlement.getThisAmtNoTax()), zxSaFsPaySettlement1.getThisAmtNoTax()));
            zxSaFsSettlement.setThisAmtTax(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getThisAmtTax(), zxSaFsPaySettlement.getThisAmtTax()), zxSaFsPaySettlement1.getThisAmtTax()));
            zxSaFsSettlement.setTotalAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getTotalAmt(), zxSaFsPaySettlement.getThisAmt()), zxSaFsPaySettlement1.getThisAmt()));
            zxSaFsSettlement.setTotalAmtNoTax(CalcUtils.calcSubtract(CalcUtils.calcAdd(zxSaFsSettlement.getTotalAmtNoTax(), zxSaFsPaySettlement.getThisAmtNoTax()), zxSaFsPaySettlement1.getThisAmtNoTax()));
            flag = zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);
            if (flag == 0) {
                throw new Apih5Exception("????????????????????????");
            }
            flag = zxSaFsInventorySettlementMapper.updateByPrimaryKey(zxSaFsPaySettlement);
            if (flag == 0) {
                throw new Apih5Exception("??????????????????????????????");
            }
            flag = syncUpdateStatisDetail(zxSaFsSettlement);
            if (flag == 0) {
                throw new Apih5Exception("????????????????????????");
            }
            return flag;
        } else {
            return 0;
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public int synUpdatePaySettlementDelete(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        int flag = 0;
        ZxSaFsPaySettlementDetail PaySettlementDetail = zxSaFsPaySettlementDetailMapper.selectByPrimaryKey(zxSaFsPaySettlementDetail.getZxSaFsPaySettlementDetailId());
        ZxSaFsPaySettlement zxSaFsPaySettlement = zxSaFsInventorySettlementMapper.selectByPrimaryKey(PaySettlementDetail.getZxSaFsPaySettlementId());
        ZxSaFsSettlement zxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsPaySettlement.getZxSaFsSettlementId());
        if (zxSaFsPaySettlement != null) {
            zxSaFsPaySettlement.setThisAmt(CalcUtils.calcSubtract(zxSaFsPaySettlement.getThisAmt(), PaySettlementDetail.getThisAmt()));
            zxSaFsPaySettlement.setThisAmtNoTax(CalcUtils.calcSubtract(zxSaFsPaySettlement.getThisAmtNoTax(), PaySettlementDetail.getThisAmtNoTax()));
            zxSaFsPaySettlement.setThisAmtTax(CalcUtils.calcSubtract(zxSaFsPaySettlement.getThisAmtTax(), PaySettlementDetail.getThisAmtTax()));
            zxSaFsPaySettlement.setTotalAmt(CalcUtils.calcSubtract(zxSaFsPaySettlement.getTotalAmt(), PaySettlementDetail.getThisAmt()));

            zxSaFsSettlement.setThisAmt(CalcUtils.calcSubtract(zxSaFsSettlement.getThisAmt(), PaySettlementDetail.getThisAmt()));
            zxSaFsSettlement.setThisAmtNoTax(CalcUtils.calcSubtract(zxSaFsSettlement.getThisAmtNoTax(), PaySettlementDetail.getThisAmtNoTax()));
            zxSaFsSettlement.setThisAmtTax(CalcUtils.calcSubtract(zxSaFsSettlement.getThisAmtTax(), PaySettlementDetail.getThisAmtTax()));
            zxSaFsSettlement.setTotalAmt(CalcUtils.calcSubtract(zxSaFsSettlement.getTotalAmt(), PaySettlementDetail.getThisAmt()));
            zxSaFsSettlement.setTotalAmtNoTax(CalcUtils.calcSubtract(zxSaFsSettlement.getTotalAmtNoTax(), PaySettlementDetail.getThisAmtNoTax()));

            zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);

            zxSaFsInventorySettlementMapper.updateByPrimaryKey(zxSaFsPaySettlement);

            flag = syncUpdateStatisDetail(zxSaFsSettlement);
            return flag;
        } else {
            return 0;
        }
    }


//    public int synStatisticsDetail(ZxSaFsSettlement zxSaFsSettlement) throws Exception{
//        int flag =0;
//        ZxSaFsStatisticsDetail zxSaFsStatisticsDetail = new ZxSaFsStatisticsDetail();
//        zxSaFsStatisticsDetail.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
//        List<ZxSaFsStatisticsDetail> StaList = zxSaFsStatisticsDetailMapper.selectByZxSaFsStatisticsDetailList(zxSaFsStatisticsDetail);
//        StaList.stream().filter(satis -> "100100".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmt()));
//            o.setTotalAmt(String.valueOf(zxSaFsSettlement.getTotalAmt()));
//        });
//
//        StaList.stream().filter(satis -> "100200".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt( DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmt()));
//            o.setTotalAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getTotalAmt()));
//        });
//        StaList.stream().filter(satis -> "100110".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmtNoTax()));
//            o.setTotalAmt(String.valueOf(zxSaFsSettlement.getTotalAmtNoTax()));
//        });
//        StaList.stream().filter(satis -> "100210".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmtNoTax()));
//            o.setTotalAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getTotalAmtNoTax()));
//        });
//        StaList.stream().filter(satis -> "100120".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmtTax()));
//            o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(zxSaFsSettlement.getTotalAmt(),zxSaFsSettlement.getTotalAmtNoTax())));
//        });
//        StaList.stream().filter(satis -> "100220".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmtTax()));
//            o.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(zxSaFsSettlement.getTotalAmt(),zxSaFsSettlement.getTotalAmtNoTax())));
//        });
//
//        StaList.stream().filter(satis -> "100700".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(String.valueOf(zxSaFsSettlement.getThisAmt()));
//            o.setTotalAmt(String.valueOf(zxSaFsSettlement.getTotalAmt()));
//        });
//        StaList.stream().filter(satis -> "100800".equals(satis.getStatisticsType())).forEach(o->{
//            o.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getThisAmt()));
//            o.setTotalAmt(DigitalConversionUtil.digitUppercase(zxSaFsSettlement.getTotalAmt()));
//        });
//        StaList.stream().filter(satis -> 1==satis.getSort()).forEach(o->{
//            BigDecimal total=new BigDecimal("0");
//            BigDecimal thisAmt=new BigDecimal("0");
//            if(o.getThisAmt()!=null){
//                thisAmt=new BigDecimal(o.getThisAmt());
//            }
//            if(zxSaFsSettlement.getTotalAmt()!=null){
//                total=zxSaFsSettlement.getTotalAmt();
//            }
//            o.setThisAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getThisAmt(),CalcUtils.calcDivide(o.getRate(),new BigDecimal("100")))));
//            o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(total, new BigDecimal(o.getThisAmt())),thisAmt) ));
//        });
//        flag= zxSaFsStatisticsDetailMapper.updateStatisticsDetail(StaList);
//        if(flag==0){
//            throw new Apih5Exception("??????????????????????????????");
//        }
//        ZxSaFsStatisticsDetail zxSaFsStatisticsDetail2=new ZxSaFsStatisticsDetail();
//        zxSaFsStatisticsDetail2.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
//        zxSaFsStatisticsDetail2.setStatisticsType("100300");
//        zxSaFsStatisticsDetail2.setSort(1);
//        ZxSaFsStatisticsDetail zxSaFsStatisticsDetail3=zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail2);
//        ZxSaFsStatisticsDetail zxSaFsStatisticsDetail4= zxSaFsStatisticsDetailMapper.selectBaoZhengJinSum(zxSaFsStatisticsDetail2);
//        if(zxSaFsStatisticsDetail4!=null){
//            zxSaFsStatisticsDetail3.setThisAmt(String.valueOf(zxSaFsStatisticsDetail4.getAmt()));
//            zxSaFsStatisticsDetail3.setTotalAmt(String.valueOf(zxSaFsStatisticsDetail4.gettAmt()));
//        }
//
//        flag= zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetail3);
//        if(flag==0){
//            throw new Apih5Exception("??????????????????????????????");
//        }
//
//        return  flag;
//    }

    /**
     * ???????????????????????????
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public int syncUpdateStatisDetail(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        int flag = 0;
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

//        StaList.stream().filter(satis -> 1 == satis.getSort()).forEach(o -> {
//            BigDecimal total = new BigDecimal("0");
//            BigDecimal thisAmt = new BigDecimal("0");
//
//            if (o.getThisAmt() != null) {
//                thisAmt = new BigDecimal(o.getThisAmt());
//            }
//            o.setThisAmt(String.valueOf(CalcUtils.calcMultiply(o.getRate(), CalcUtils.calcDivide(zxSaFsSettlement.getThisAmt(), new BigDecimal("100"), 2))));
//
//            if (o.getTotalAmt() != null) {
//                total = new BigDecimal(o.getTotalAmt());
//            }
//            o.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(total, new BigDecimal(o.getThisAmt()) ),thisAmt)));
//
//
//        });

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

        dXyifuhetong.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(yifuhetong.getThisAmt())));
        dXyifuhetong.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(yifuhetong.getTotalAmt())));
        dxHanShui.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(HanShui.getThisAmt())));
        dxHanShui.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(HanShui.getTotalAmt())));
        dxBuHanShui.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(BuHanShui.getThisAmt())));
        dxBuHanShui.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(BuHanShui.getTotalAmt())));
        dxShuiE.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(ShuiE.getThisAmt())));
        dxShuiE.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(ShuiE.getTotalAmt())));

        //???????????????
        zxSaFsStatisticsDetailMapper.updateByPrimaryKey(yifuhetong);
        zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dXyifuhetong);
        zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dxHanShui);
        zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dxBuHanShui);
        zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dxShuiE);

        if (zxSaFsSettlement != null) {
            zxSaFsSettlement.setThisPayAmt(new BigDecimal(yifuhetong.getThisAmt()));
            zxSaFsSettlement.setTotalPayAmt(new BigDecimal(yifuhetong.getTotalAmt()));
            flag = zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);
        } else {
            return 0;
        }

        return flag;


    }
}
