package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxCtFsContractMapper;
import com.apih5.mybatis.dao.ZxCtFsContractReviewMapper;
import com.apih5.mybatis.pojo.ZxCtFsContract;
import com.apih5.mybatis.pojo.ZxCtFsContractReview;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractReviewDetailMapper;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;
import com.apih5.service.ZxCtFsContractReviewDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

@Service("zxCtFsContractReviewDetailService")
public class ZxCtFsContractReviewDetailServiceImpl implements ZxCtFsContractReviewDetailService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewMapper zxCtFsContractReviewMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Override
    public ResponseEntity getZxCtFsContractReviewDetailListByCondition(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        if (zxCtFsContractReviewDetail == null) {
            zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        }
        // ????????????
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        // ????????????
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectByZxCtFsContractReviewDetailList(zxCtFsContractReviewDetail);
        // ??????????????????
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractReviewDetailDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        if (zxCtFsContractReviewDetail == null) {
            zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        }
        // ????????????
        ZxCtFsContractReviewDetail dbZxCtFsContractReviewDetail = zxCtFsContractReviewDetailMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getConRevDetailId());
        // ????????????
        if (dbZxCtFsContractReviewDetail != null) {
            return repEntity.ok(dbZxCtFsContractReviewDetail);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContractReviewDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        int flag = 0;
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContractReviewDetail.setConRevDetailId(UuidUtil.generate());
        zxCtFsContractReviewDetail.setCreateUserInfo(userKey, realName);
        zxCtFsContractReviewDetail.setContractSum(CalcUtils.calcMultiply(zxCtFsContractReviewDetail.getPrice(), BigDecimal.valueOf(zxCtFsContractReviewDetail.getQty())));
        zxCtFsContractReviewDetail.setContractSumNoTax(CalcUtils.calcDivide(zxCtFsContractReviewDetail.getContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtFsContractReviewDetail.getTaxRate()), new BigDecimal("100")))));
        zxCtFsContractReviewDetail.setContractSumTax(CalcUtils.calcSubtract(zxCtFsContractReviewDetail.getContractSum(), zxCtFsContractReviewDetail.getContractSumNoTax()));
        flag = zxCtFsContractReviewDetailMapper.insert(zxCtFsContractReviewDetail);

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            flag = updateZxCtFsContractReviewContractSum(zxCtFsContractReviewDetail);
        }

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtFsContractReviewDetail);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContractReviewDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContractReviewDetail dbZxCtFsContractReviewDetail = zxCtFsContractReviewDetailMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getConRevDetailId());
        if (dbZxCtFsContractReviewDetail != null && StrUtil.isNotEmpty(dbZxCtFsContractReviewDetail.getConRevDetailId())) {
            // ??????ID
            dbZxCtFsContractReviewDetail.setZxCtFsContractId(zxCtFsContractReviewDetail.getZxCtFsContractId());
            // ????????????
            dbZxCtFsContractReviewDetail.setWorkNo(zxCtFsContractReviewDetail.getWorkNo());
            // ????????????
            dbZxCtFsContractReviewDetail.setWorkName(zxCtFsContractReviewDetail.getWorkName());
            // ??????
            dbZxCtFsContractReviewDetail.setSpec(zxCtFsContractReviewDetail.getSpec());
            // ????????????
            dbZxCtFsContractReviewDetail.setUnit(zxCtFsContractReviewDetail.getUnit());
            // ??????ID(??????workType)
            dbZxCtFsContractReviewDetail.setWorkType(zxCtFsContractReviewDetail.getWorkType());
            // ??????
            dbZxCtFsContractReviewDetail.setTreenode(zxCtFsContractReviewDetail.getTreenode());
            // ????????????
            dbZxCtFsContractReviewDetail.setQty(zxCtFsContractReviewDetail.getQty());
            // ??????????????????
            dbZxCtFsContractReviewDetail.setPrice(zxCtFsContractReviewDetail.getPrice());
            // ??????????????????
            dbZxCtFsContractReviewDetail.setViewType(zxCtFsContractReviewDetail.getViewType());
            // ??????????????????
            dbZxCtFsContractReviewDetail.setPlanStartDate(zxCtFsContractReviewDetail.getPlanStartDate());
            // ??????????????????
            dbZxCtFsContractReviewDetail.setPlanEndDate(zxCtFsContractReviewDetail.getPlanEndDate());
            // ??????????????????
            dbZxCtFsContractReviewDetail.setActualStartDate(zxCtFsContractReviewDetail.getActualStartDate());
            // ??????????????????
            dbZxCtFsContractReviewDetail.setActualEndDate(zxCtFsContractReviewDetail.getActualEndDate());
            // ??????ID
            dbZxCtFsContractReviewDetail.setConRevDetailId(zxCtFsContractReviewDetail.getConRevDetailId());
            // ??????(??????)
            dbZxCtFsContractReviewDetail.setPp10(zxCtFsContractReviewDetail.getPp10());
            // ???????????????
            dbZxCtFsContractReviewDetail.setChangeQty(zxCtFsContractReviewDetail.getChangeQty());
            // ?????????????????????
            dbZxCtFsContractReviewDetail.setChangePrice(zxCtFsContractReviewDetail.getChangePrice());
            // ?????????????????????
            dbZxCtFsContractReviewDetail.setChangeContractSum(zxCtFsContractReviewDetail.getChangeContractSum());
            // ????????????
            dbZxCtFsContractReviewDetail.setRentUnit(zxCtFsContractReviewDetail.getRentUnit());
            // ???????????????????????????
            dbZxCtFsContractReviewDetail.setAlterRentStartDate(zxCtFsContractReviewDetail.getAlterRentStartDate());
            // ???????????????????????????
            dbZxCtFsContractReviewDetail.setAlterRentEndDate(zxCtFsContractReviewDetail.getAlterRentEndDate());
            // ????????????
            dbZxCtFsContractReviewDetail.setRequestEdit(zxCtFsContractReviewDetail.getRequestEdit());
            // ?????????
            dbZxCtFsContractReviewDetail.setEditUserId(zxCtFsContractReviewDetail.getEditUserId());
            // ?????????
            dbZxCtFsContractReviewDetail.setEditUserName(zxCtFsContractReviewDetail.getEditUserName());
            // ????????????
            dbZxCtFsContractReviewDetail.setEditDate(zxCtFsContractReviewDetail.getEditDate());
            // ?????????????????????
            dbZxCtFsContractReviewDetail.setPriceNoTax(zxCtFsContractReviewDetail.getPriceNoTax());
            // ????????????????????????
            dbZxCtFsContractReviewDetail.setChangePriceNoTax(zxCtFsContractReviewDetail.getChangePriceNoTax());
            // ????????????????????????
            dbZxCtFsContractReviewDetail.setChangeContractSumNoTax(zxCtFsContractReviewDetail.getChangeContractSumNoTax());
            // ??????(%)
            dbZxCtFsContractReviewDetail.setTaxRate(zxCtFsContractReviewDetail.getTaxRate());
            // ??????
            dbZxCtFsContractReviewDetail.setRemarks(zxCtFsContractReviewDetail.getRemarks());
            // ??????
            dbZxCtFsContractReviewDetail.setSort(zxCtFsContractReviewDetail.getSort());
            // ??????
            dbZxCtFsContractReviewDetail.setModifyUserInfo(userKey, realName);

            // ??????????????????
            dbZxCtFsContractReviewDetail.setContractSum(zxCtFsContractReviewDetail.getContractSum());
            // ?????????????????????
            dbZxCtFsContractReviewDetail.setContractSumNoTax(zxCtFsContractReviewDetail.getContractSumNoTax());
            flag = zxCtFsContractReviewDetailMapper.updateByPrimaryKey(dbZxCtFsContractReviewDetail);
            if (flag != 0) {
                flag = updateZxCtFsContractReviewContractSum(zxCtFsContractReviewDetail);
                if (flag == 0) {
                    return repEntity.errorSave();
                } else {
                    return repEntity.ok("sys.data.update", zxCtFsContractReviewDetail);
                }
            } else {
                return repEntity.errorUpdate();
            }
        } else {
            return repEntity.layerMessage("no", "???????????????????????????");
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContractReviewDetail(List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtFsContractReviewDetailList != null && zxCtFsContractReviewDetailList.size() > 0) {
            for (ZxCtFsContractReviewDetail zxCtFsContractReviewDetail : zxCtFsContractReviewDetailList
            ) {
                flag = synZxCtFsReviewDelete(zxCtFsContractReviewDetail);
                if (flag == 0) {
                    return repEntity.errorSave();
                }
            }
            ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
            zxCtFsContractReviewDetail.setModifyUserInfo(userKey, realName);

            flag = zxCtFsContractReviewDetailMapper.batchDeleteUpdateZxCtFsContractReviewDetail(zxCtFsContractReviewDetailList, zxCtFsContractReviewDetail);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractReviewDetailList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ???excel??????????????????????????????????????????????????????????????????????????????
     *
     * @param zxCtFsContractReviewDetail
     * @author suncg
     */
    public ResponseEntity importExcel(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        // ??????????????????
        if (zxCtFsContractReviewDetail.getZxErpFileList() == null || zxCtFsContractReviewDetail.getZxErpFileList().size() == 0) {
            return repEntity.layerMessage("no", "??????????????????!");
        }
        //?????????????????????????????????
        // String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        int i = 0;
        List<ZxCtFsContractReviewDetail> excelImportList = new ArrayList<ZxCtFsContractReviewDetail>();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<Map<String, Object>> excelList = null;
        String path = Apih5Properties.getFilePath() + zxCtFsContractReviewDetail.getZxErpFileList().get(0).getRelativeUrl();
        ExcelReader reader = ExcelUtil.getReader(path);
        try {
            //ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
//            reader.addHeaderAlias("????????????","workNo");
//            reader.addHeaderAlias("????????????","workName");
//            reader.addHeaderAlias("????????????","spec");
//            reader.addHeaderAlias("??????","unit");
//            reader.addHeaderAlias("??????","qty");
//            reader.addHeaderAlias("??????????????????","price");
//            reader.addHeaderAlias("??????(%)","taxRate");
            excelList = reader.readAll();

            for (Map<String, Object> item : excelList) {
                ZxCtFsContractReviewDetail zxCtFsContractReviewDetail12 = new ZxCtFsContractReviewDetail();
                JSONObject json = new JSONObject(item);
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zxCtFsContractReviewDetail12.setWorkName(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zxCtFsContractReviewDetail12.setWorkNo(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("??????"))) {
                    zxCtFsContractReviewDetail12.setUnit(json.getStr("??????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zxCtFsContractReviewDetail12.setSpec(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("??????"))) {
                    zxCtFsContractReviewDetail12.setQty(Integer.parseInt(json.getStr("??????")));
                }
                if (StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
                    BigDecimal price = new BigDecimal(json.getStr("??????????????????"));
                    zxCtFsContractReviewDetail12.setPrice(price);
                }
                if (StrUtil.isNotEmpty(json.getStr("??????"))) {
                    zxCtFsContractReviewDetail12.setTaxRate(json.getStr("??????"));
                }
                zxCtFsContractReviewDetail12.setConRevDetailId(UuidUtil.generate());
                zxCtFsContractReviewDetail12.setCreateUserInfo(userKey, realName);
                excelImportList.add(zxCtFsContractReviewDetail12);
                i++;
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, "????????????????????????" + e.getMessage());
            logger.info("UuidUtil.generate()???" + excelList.size() + "??????");
            logger.info("??????????????????" + excelList);
            logger.info("?????????????????????" + excelImportList);
            logger.info("??????????????????" + i);
            return repEntity.layerMessage("no", "?????????????????????" + e.getMessage() + "???" + excelList.size() + "/" + i);
        } finally {
            reader.close();
        }
        int flag = zxCtFsContractReviewDetailMapper.importZxCtFsContractReviewDetailsList(excelImportList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", excelImportList);
        }

    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param zxCtFsContractReviewDetail
     * @author suncg
     */
    private int updateZxCtFsContractReviewContractSum(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        int flag = 0;
        ZxCtFsContractReview zxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getContractReviewId());
        if (zxCtFsContractReview != null) {
            ZxCtFsContractReviewDetail params = new ZxCtFsContractReviewDetail();
            params.setContractReviewId(zxCtFsContractReviewDetail.getContractReviewId());
            List<ZxCtFsContractReviewDetail> list1 = zxCtFsContractReviewDetailMapper.selectByZxCtFsContractReviewDetailList(params);
            BigDecimal contractSum = new BigDecimal("0");
            BigDecimal contractSumNoTax = new BigDecimal("0");
            BigDecimal wan = new BigDecimal("10000");
            if (list1.size() > 0) {
                for (ZxCtFsContractReviewDetail item : list1) {
                    contractSum = CalcUtils.calcAdd(contractSum, CalcUtils.calcDivide(item.getContractSum(), wan, 6));
                    contractSumNoTax = CalcUtils.calcAdd(contractSumNoTax, CalcUtils.calcDivide(item.getContractSumNoTax(), wan, 6));
                }
            }

            BigDecimal contractCostTax = contractSum.subtract(contractSumNoTax);
            zxCtFsContractReview.setContractCost(contractSum);
            zxCtFsContractReview.setContractCostNoTax(contractSumNoTax);
            zxCtFsContractReview.setContractCostTax(contractCostTax);
            flag = zxCtFsContractReviewMapper.updateByPrimaryKey(zxCtFsContractReview);
        }

        return flag;
    }

    private int synZxCtFsReviewDelete(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        int flag = 0;
        ZxCtFsContractReview zxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getContractReviewId());
        BigDecimal contractCostTax = CalcUtils.calcSubtract(zxCtFsContractReviewDetail.getContractSum(), zxCtFsContractReviewDetail.getContractSumNoTax());
        zxCtFsContractReview.setContractCost(CalcUtils.calcSubtract(zxCtFsContractReview.getContractCost(), CalcUtils.calcDivide(zxCtFsContractReviewDetail.getContractSum(), new BigDecimal("10000"), 6)));
        zxCtFsContractReview.setContractCostNoTax(CalcUtils.calcSubtract(zxCtFsContractReview.getContractCostNoTax(), CalcUtils.calcDivide(zxCtFsContractReviewDetail.getContractSumNoTax(), new BigDecimal("10000"), 6)));
        zxCtFsContractReview.setContractCostTax(CalcUtils.calcSubtract(zxCtFsContractReview.getContractCostTax(), CalcUtils.calcDivide(contractCostTax, new BigDecimal("10000"), 6)));
        flag = zxCtFsContractReviewMapper.updateByPrimaryKey(zxCtFsContractReview);
        return flag;
    }


    @Override
    public ResponseEntity getZxCtFsContractReviewDetailList(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        // ????????????
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        // ????????????
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
        // ??????????????????
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }


    /**
     * ??????????????????ID??????????????????
     *
     * @param zxCtFsContractReviewDetail ??????excel??????
     * @author suncg
     */
    public ResponseEntity getReviewDetailList(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        if (zxCtFsContractReviewDetail == null) {
            zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        }
        // ????????????
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        // ????????????
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectByFsContractId(zxCtFsContractReviewDetail.getZxCtFsContractId());
        // ??????????????????
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }


    @Override
    public ResponseEntity getAllReviewDetailListByContract(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        // ????????????
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getZxCtFsContractId());
        zxCtFsContractReviewDetail.setContractReviewId(zxCtFsContract.getContractReviewId());
        zxCtFsContractReviewDetail.setZxCtFsContractId(zxCtFsContract.getZxCtFsContractId());
        // ????????????
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
        // ??????????????????
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }


}
