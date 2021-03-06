package com.apih5.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxCtOtherMapper;
import com.apih5.mybatis.pojo.ZxCtContrApplyWorks;
import com.apih5.mybatis.pojo.ZxCtOther;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtOtherWorksMapper;
import com.apih5.mybatis.pojo.ZxCtOtherWorks;
import com.apih5.service.ZxCtOtherWorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherWorksService")
public class ZxCtOtherWorksServiceImpl implements ZxCtOtherWorksService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherMapper zxCtOtherMapper;


    @Override
    public ResponseEntity getZxCtOtherWorksListByCondition(ZxCtOtherWorks zxCtOtherWorks) {
        if (zxCtOtherWorks == null) {
            zxCtOtherWorks = new ZxCtOtherWorks();
        }
        // ????????????
        PageHelper.startPage(zxCtOtherWorks.getPage(),zxCtOtherWorks.getLimit());
        // ????????????
        List<ZxCtOtherWorks> zxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(zxCtOtherWorks);
        // ??????????????????
        PageInfo<ZxCtOtherWorks> p = new PageInfo<>(zxCtOtherWorksList);

        return repEntity.okList(zxCtOtherWorksList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherWorksDetail(ZxCtOtherWorks zxCtOtherWorks) {
        if (zxCtOtherWorks == null) {
            zxCtOtherWorks = new ZxCtOtherWorks();
        }
        // ????????????
        ZxCtOtherWorks dbZxCtOtherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherWorks.getZxCtOtherWorksId());
        // ????????????
        if (dbZxCtOtherWorks != null) {
            return repEntity.ok(dbZxCtOtherWorks);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtOtherWorks.setZxCtOtherWorksId(UuidUtil.generate());
        zxCtOtherWorks.setCreateUserInfo(userKey, realName);
        // ??????????????????id????????????
        if (StrUtil.isEmpty(zxCtOtherWorks.getZxCtOtherId())) {
            return repEntity.layerMessage("no", "??????????????????ID???????????????");
        }
        // ????????????id
        zxCtOtherWorks.setZxCtOtherId(zxCtOtherWorks.getZxCtOtherId());
        // ??????????????? ???????????? / ???1 + ??????????????????
        BigDecimal rate = CalcUtils.calcDivide(new BigDecimal(zxCtOtherWorks.getTaxRate()), new BigDecimal(100), 6);
        zxCtOtherWorks.setPriceNoTax(CalcUtils.calcDivide(zxCtOtherWorks.getPrice(), CalcUtils.calcAdd(new BigDecimal(1), rate), 6));
        // ??????????????????  ???????????? * ??????
        BigDecimal contractSum = CalcUtils.calcMultiply(zxCtOtherWorks.getPrice(), zxCtOtherWorks.getQty());
        zxCtOtherWorks.setContractSum(contractSum);
        // ????????????????????? ???????????? / (1 + ???????????????)
        BigDecimal contractSumNoTax = CalcUtils.calcDivide(contractSum, CalcUtils.calcAdd(new BigDecimal(1), rate), 6);
        zxCtOtherWorks.setContractSumNoTax(contractSumNoTax);
        // ??????  ?????????????????????????????????????????????
        BigDecimal contractSumTax = CalcUtils.calcSubtract(contractSum, contractSumNoTax);
        zxCtOtherWorks.setContractSumTax(contractSumTax);
        // ???????????????
        zxCtOtherWorks.setChangeQty(null);
        // ?????????????????????
        zxCtOtherWorks.setChangePrice(null);
        // ?????????????????????
        zxCtOtherWorks.setChangeContractSum(null);
        // ????????????????????????
        zxCtOtherWorks.setChangePriceNoTax(null);
        // ????????????????????????
        zxCtOtherWorks.setChangeContractSumNoTax(null);
        // ???????????????
        zxCtOtherWorks.setChangeContractSumTax(null);
        int flag = zxCtOtherWorksMapper.insert(zxCtOtherWorks);
        // ?????????????????????????????????????????????????????????????????????
        this.sumZxCtOtherWorksAmount(zxCtOtherWorks);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherWorks);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherWorks dbZxCtOtherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherWorks.getZxCtOtherWorksId());
        if (dbZxCtOtherWorks != null && StrUtil.isNotEmpty(dbZxCtOtherWorks.getZxCtOtherWorksId())) {
            // ????????????
            dbZxCtOtherWorks.setWorkNo(zxCtOtherWorks.getWorkNo());
            // ????????????
            dbZxCtOtherWorks.setWorkName(zxCtOtherWorks.getWorkName());
            // ????????????
            dbZxCtOtherWorks.setUnit(zxCtOtherWorks.getUnit());
            // ????????????
            dbZxCtOtherWorks.setQty(zxCtOtherWorks.getQty());
            // ??????????????????
            dbZxCtOtherWorks.setPrice(zxCtOtherWorks.getPrice());
            // ??????????????? ???????????? / ???1 + ??????????????????
            BigDecimal rate = CalcUtils.calcDivide(new BigDecimal(zxCtOtherWorks.getTaxRate()), new BigDecimal(100), 6);
            dbZxCtOtherWorks.setPriceNoTax(CalcUtils.calcDivide(zxCtOtherWorks.getPrice(), CalcUtils.calcAdd(new BigDecimal(1), rate), 6));
            // ??????(%)
            dbZxCtOtherWorks.setTaxRate(zxCtOtherWorks.getTaxRate());
            // ????????????
            dbZxCtOtherWorks.setIsDeduct(zxCtOtherWorks.getIsDeduct());
            // ??????????????????  ???????????? * ??????
            BigDecimal contractSum = CalcUtils.calcMultiply(zxCtOtherWorks.getPrice(), zxCtOtherWorks.getQty());
            dbZxCtOtherWorks.setContractSum(contractSum);
            // ????????????????????? ???????????? / (1 + ???????????????)
            BigDecimal contractSumNoTax = CalcUtils.calcDivide(contractSum, CalcUtils.calcAdd(new BigDecimal(1), rate), 6);
            dbZxCtOtherWorks.setContractSumNoTax(contractSumNoTax);
            // ??????
            dbZxCtOtherWorks.setModifyUserInfo(userKey, realName);
            // ??????
            dbZxCtOtherWorks.setRemark(zxCtOtherWorks.getRemark());
            // ??????  ?????????????????????????????????????????????
            BigDecimal contractSumTax = CalcUtils.calcSubtract(contractSum, contractSumNoTax);
            dbZxCtOtherWorks.setContractSumTax(contractSumTax);
            flag = zxCtOtherWorksMapper.updateByPrimaryKey(dbZxCtOtherWorks);
            // ?????????????????????????????????????????????????????????????????????
            this.sumZxCtOtherWorksAmount(dbZxCtOtherWorks);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherWorks);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherWorks(List<ZxCtOtherWorks> zxCtOtherWorksList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtOtherWorksList != null && zxCtOtherWorksList.size() > 0) {
           ZxCtOtherWorks zxCtOtherWorks = new ZxCtOtherWorks();
           zxCtOtherWorks.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherWorksMapper.batchDeleteUpdateZxCtOtherWorks(zxCtOtherWorksList, zxCtOtherWorks);
            // ?????????????????????????????????????????????????????????????????????
           for (ZxCtOtherWorks zxCtOtherWorks1 : zxCtOtherWorksList) {
               this.sumZxCtOtherWorksAmount(zxCtOtherWorks1);
           }
        }

        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherWorksList);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param zxCtOtherWorks ??????
     *
     * */
    private void sumZxCtOtherWorksAmount(ZxCtOtherWorks zxCtOtherWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
        dbZxCtOtherWorks.setZxCtOtherId(zxCtOtherWorks.getZxCtOtherId());
        // ??????????????????????????????zxCtOtherId????????????????????????
        List<ZxCtOtherWorks> zxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
        // ????????????????????????????????????
        ZxCtOther zxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOtherWorks.getZxCtOtherId());
        // ??????????????????(??????)
        BigDecimal totalContractSum = null;
        // ?????????????????????(??????)
        BigDecimal totalContractSumNoTax = null;
        // ????????????(??????)
        BigDecimal totalContractCostTax = null;
        if (zxCtOtherWorksList != null && zxCtOtherWorksList.size() > 0) {
            for (ZxCtOtherWorks zxCtOtherWorks1 : zxCtOtherWorksList) {
                // ?????????????????????????????????
                totalContractSum = CalcUtils.calcAdd(totalContractSum, zxCtOtherWorks1.getContractSum());
                // ????????????????????????????????????
                totalContractSumNoTax = CalcUtils.calcAdd(totalContractSumNoTax, zxCtOtherWorks1.getContractSumNoTax());
                // ???????????????????????????
                totalContractCostTax = CalcUtils.calcAdd(totalContractCostTax, zxCtOtherWorks1.getContractSumTax());
            }
            // ???????????????????????????????????????
            zxCtOther.setContractCost(CalcUtils.calcDivide(totalContractSum, new BigDecimal("10000"), 6));
            // ??????????????????????????????????????????
            zxCtOther.setContractCostNoTax(CalcUtils.calcDivide(totalContractSumNoTax, new BigDecimal("10000"), 6));
            // ?????????????????????????????????
            zxCtOther.setContractCostTax(CalcUtils.calcDivide(totalContractCostTax, new BigDecimal("10000"), 6));
            zxCtOther.setModifyUserInfo(userKey, realName);
            zxCtOtherMapper.updateByPrimaryKey(zxCtOther);
        } else {
            zxCtOther.setContractCost(new BigDecimal(0));
            zxCtOther.setContractCostNoTax(new BigDecimal(0));
            zxCtOther.setContractCostTax(new BigDecimal(0));
            zxCtOther.setModifyUserInfo(userKey, realName);
            zxCtOtherMapper.updateByPrimaryKey(zxCtOther);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ???????????????????????????????????????
     *
     * @param zxCtOtherWorks ??????
     * @ return ??????
     * */
    @Override
    public ResponseEntity importZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks) {
        if (zxCtOtherWorks == null) {
            zxCtOtherWorks = new ZxCtOtherWorks();
        }
        int flag = 0;
        // ??????????????????
        if (zxCtOtherWorks.getZxErpFileList() == null || zxCtOtherWorks.getZxErpFileList().size() == 0) {
            return repEntity.layerMessage("no", "??????????????????!");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<ZxCtOtherWorks> returnWorksList = new ArrayList<>();
        int i = 0;
        String workNo = "";
        try {
            String path = Apih5Properties.getFilePath() + zxCtOtherWorks.getZxErpFileList().get(0).getRelativeUrl();
            ExcelReader reader = ExcelUtil.getReader(path);

            List<Map<String, Object>> importExcelList = reader.readAll();
            if (importExcelList == null || importExcelList.size() == 0) {
                return repEntity.layerMessage("no", "??????????????????????????????!");
            }

            if (importExcelList != null && importExcelList.size() > 0) {
                for(Map<String,Object> map:importExcelList) {
                    JSONObject json = new JSONObject(map);
                    // ????????????
                    if (StrUtil.isEmpty(json.getStr("????????????"))) {
                        continue;
                    }
                    zxCtOtherWorks.setWorkNo(json.getStr("????????????"));
                    workNo = zxCtOtherWorks.getWorkNo();
                    
                    if(StrUtil.isNotEmpty(json.getStr("????????????"))){
                        // ????????????
                        zxCtOtherWorks.setWorkName(json.getStr("????????????"));
                    }
                    if(StrUtil.isNotEmpty(json.getStr("????????????"))){
                        // ????????????
                        zxCtOtherWorks.setSpec(json.getStr("????????????"));
                    }
                    if(StrUtil.isNotEmpty(json.getStr("??????"))){
                        // ??????
                        zxCtOtherWorks.setSpec(json.getStr("??????"));
                    }
                    if (StrUtil.isNotEmpty(json.getStr("??????"))){
                        BigDecimal qty1 = new BigDecimal(json.getStr("??????"));
                        zxCtOtherWorks.setQty(qty1);
                    }
                    // ??????
                    if (StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
                        BigDecimal price1 = new BigDecimal(json.getStr("??????????????????"));
                        zxCtOtherWorks.setPrice(price1);
                        // ??????
                        if (StrUtil.isEmpty(json.getStr("??????(%)")) || StrUtil.equals("???", (json.getStr("??????(%)")))) {
                            zxCtOtherWorks.setPriceNoTax(new BigDecimal(0));
                        } else {
                            zxCtOtherWorks.setTaxRate(json.getStr("??????(%)"));
                            zxCtOtherWorks.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(json.getStr("??????????????????")), CalcUtils.calcMultiply(CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(json.getStr("??????(%)"))), new BigDecimal(0.01)), 2));
                        }
                    }
                    zxCtOtherWorks.setZxCtOtherWorksId(UuidUtil.generate());
                    zxCtOtherWorks.setCreateUserInfo(userKey, realName);
                    flag = zxCtOtherWorksMapper.insert(zxCtOtherWorks);
                    if (flag == 0) {
                        repEntity.layerMessage("no", "???" + i + "????????????????????????" + workNo + "??????????????????(??????????????????)");
                    } else {
                        returnWorksList.add(zxCtOtherWorks);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, e.getMessage());
            repEntity.layerMessage("no", "???" + i + "????????????????????????" + workNo + "??????????????????  " + e.getMessage());
        }
        // ??????????????????
        return repEntity.okList(returnWorksList, returnWorksList.size());
    }
}
