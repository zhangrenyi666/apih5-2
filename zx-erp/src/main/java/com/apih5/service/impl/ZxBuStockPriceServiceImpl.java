package com.apih5.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxBuStockPriceItemMapper;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import com.apih5.mybatis.pojo.ZxCtWorks;
import flex.messaging.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuStockPriceMapper;
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.service.ZxBuStockPriceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuStockPriceService")
public class ZxBuStockPriceServiceImpl implements ZxBuStockPriceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuStockPriceMapper zxBuStockPriceMapper;

    @Autowired(required = true)
    private ZxBuStockPriceItemMapper zxBuStockPriceItemMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity getZxBuStockPriceListByCondition(ZxBuStockPrice zxBuStockPrice) {
        if (zxBuStockPrice == null) {
            zxBuStockPrice = new ZxBuStockPrice();
        }
        //1.??????????????????
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxBuStockPrice.setComID("");
            zxBuStockPrice.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxBuStockPrice.setComID(zxBuStockPrice.getOrgID());
            zxBuStockPrice.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxBuStockPrice.setComID(zxBuStockPrice.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxBuStockPrice.getPage(), zxBuStockPrice.getLimit());
        // ????????????
        List<ZxBuStockPrice> zxBuStockPriceList = zxBuStockPriceMapper.selectByZxBuStockPriceList(zxBuStockPrice);
        // ??????????????????
        PageInfo<ZxBuStockPrice> p = new PageInfo<>(zxBuStockPriceList);
//        //??????????????????????????????
//        ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
//        zxBuStockPriceItem.setOrgID(zxBuStockPrice.getOrgID());
//        zxBuStockPriceItem.setMixType("11");
//        List<ZxBuStockPriceItem> zxBuStockPriceItems = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
//        zxBuStockPrice.setZxBuStockPriceItemList(zxBuStockPriceItems);
        return repEntity.okList(zxBuStockPriceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuStockPriceDetail(ZxBuStockPrice zxBuStockPrice) {
        if (zxBuStockPrice == null) {
            zxBuStockPrice = new ZxBuStockPrice();
        }
        // ????????????
        ZxBuStockPrice dbZxBuStockPrice = zxBuStockPriceMapper.selectByPrimaryKey(zxBuStockPrice.getZxBuStockPriceId());
        // ????????????
        if (dbZxBuStockPrice != null) {
            return repEntity.ok(dbZxBuStockPrice);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxBuStockPrice(ZxBuStockPrice zxBuStockPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuStockPrice.setZxBuStockPriceId(UuidUtil.generate());
        zxBuStockPrice.setCreateUserInfo(userKey, realName);
        int flag = zxBuStockPriceMapper.insert(zxBuStockPrice);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuStockPrice);
        }
    }

    @Override
    public ResponseEntity updateZxBuStockPrice(ZxBuStockPrice zxBuStockPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuStockPrice dbZxBuStockPrice = zxBuStockPriceMapper.selectByPrimaryKey(zxBuStockPrice.getZxBuStockPriceId());
        if (dbZxBuStockPrice != null && StrUtil.isNotEmpty(dbZxBuStockPrice.getZxBuStockPriceId())) {
            // ??????ID
            dbZxBuStockPrice.setOrgID(zxBuStockPrice.getOrgID());
            // ????????????
            dbZxBuStockPrice.setOrgName(zxBuStockPrice.getOrgName());
            // ????????????ID
            dbZxBuStockPrice.setReportOrgID(zxBuStockPrice.getReportOrgID());
            // ??????????????????
            dbZxBuStockPrice.setReportOrgName(zxBuStockPrice.getReportOrgName());
            // ?????????
            dbZxBuStockPrice.setReporter(zxBuStockPrice.getReporter());
            // ????????????
            dbZxBuStockPrice.setReportDate(zxBuStockPrice.getReportDate());
            // ?????????
            dbZxBuStockPrice.setAuditor(zxBuStockPrice.getAuditor());
            // ????????????
            dbZxBuStockPrice.setAuditDate(zxBuStockPrice.getAuditDate());
            // ??????
            dbZxBuStockPrice.setStatus(zxBuStockPrice.getStatus());
            // ????????????
            dbZxBuStockPrice.setEditTime(zxBuStockPrice.getEditTime());
            //
            dbZxBuStockPrice.setCombProp(zxBuStockPrice.getCombProp());
            //
            dbZxBuStockPrice.setPp1(zxBuStockPrice.getPp1());
            //
            dbZxBuStockPrice.setPp2(zxBuStockPrice.getPp2());
            //
            dbZxBuStockPrice.setPp3(zxBuStockPrice.getPp3());
            //
            dbZxBuStockPrice.setPp4(zxBuStockPrice.getPp4());
            //
            dbZxBuStockPrice.setPp5(zxBuStockPrice.getPp5());
            //
            dbZxBuStockPrice.setPp6(zxBuStockPrice.getPp6());
            //
            dbZxBuStockPrice.setPp7(zxBuStockPrice.getPp7());
            //
            dbZxBuStockPrice.setPp8(zxBuStockPrice.getPp8());
            //
            dbZxBuStockPrice.setPp9(zxBuStockPrice.getPp9());
            //
            dbZxBuStockPrice.setPp10(zxBuStockPrice.getPp10());
            // ??????
            dbZxBuStockPrice.setPeriod(zxBuStockPrice.getPeriod());
            // ??????
            dbZxBuStockPrice.setBudgetType(zxBuStockPrice.getBudgetType());
            // mcsgAPrice
            dbZxBuStockPrice.setMcsgAPrice(zxBuStockPrice.getMcsgAPrice());
            // ????????????
            dbZxBuStockPrice.setBusinessType(zxBuStockPrice.getBusinessType());
            // comID
            dbZxBuStockPrice.setComID(zxBuStockPrice.getComID());
            // comName
            dbZxBuStockPrice.setComName(zxBuStockPrice.getComName());
            // ??????????????????????????????
            dbZxBuStockPrice.setIsBHAudit(zxBuStockPrice.getIsBHAudit());
            // ??????????????????????????????
            dbZxBuStockPrice.setIsSGAudit(zxBuStockPrice.getIsSGAudit());
            // ??????
            dbZxBuStockPrice.setRemarks(zxBuStockPrice.getRemarks());
            // ??????
            dbZxBuStockPrice.setSort(zxBuStockPrice.getSort());
            // ??????
            dbZxBuStockPrice.setModifyUserInfo(userKey, realName);
            flag = zxBuStockPriceMapper.updateByPrimaryKey(dbZxBuStockPrice);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxBuStockPrice);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuStockPrice(List<ZxBuStockPrice> zxBuStockPriceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuStockPriceList != null && zxBuStockPriceList.size() > 0) {
            //????????????
            ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
            zxBuStockPriceItem.setOrgID(zxBuStockPriceList.get(0).getOrgID());
            zxBuStockPriceItem.setMixType("11");
            List<ZxBuStockPriceItem> zxBuStockPriceItemList = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
            if (zxBuStockPriceItemList != null && zxBuStockPriceItemList.size() > 0) {
                ZxBuStockPriceItem zxBuStockPriceItem1 = new ZxBuStockPriceItem();
                zxBuStockPriceItem1.setModifyUserInfo(userKey, realName);
                zxBuStockPriceItemMapper.batchDeleteUpdateZxBuStockPriceItem(zxBuStockPriceItemList, zxBuStockPriceItem1);
            }
            ZxBuStockPrice zxBuStockPrice = new ZxBuStockPrice();
            zxBuStockPrice.setModifyUserInfo(userKey, realName);
            flag = zxBuStockPriceMapper.batchDeleteUpdateZxBuStockPrice(zxBuStockPriceList, zxBuStockPrice);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxBuStockPriceList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    @Override
    public ResponseEntity importZxBuStockPrice(ZxBuStockPrice zxBuStockPrice) {
        if (zxBuStockPrice == null) {
            zxBuStockPrice = new ZxBuStockPrice();
        }
        if (StrUtil.isEmpty(zxBuStockPrice.getZxBuStockPriceId()) || CollUtil.isEmpty(zxBuStockPrice.getFileList())) {
            return repEntity.layerMessage("no", "????????????????????????");
        }
        if (zxBuStockPrice.getFileList().size() != 1) {
            return repEntity.layerMessage("no", "??????????????????????????????");
        }
        //????????????
        String filePath = Apih5Properties.getFilePath() + zxBuStockPrice.getFileList().get(0).getRelativeUrl();
        File file = FileUtil.file(filePath);
        String type = FileTypeUtil.getType(file);
        //??????????????????
        if (!StrUtil.equals(type, "xls")) {
            return repEntity.layerMessage("no", "????????????.xls????????????");
        }
        //??????excelUtil  ??????excel?????????
        ExcelReader reader = ExcelUtil.getReader(filePath);

        //todo:??????????????????????????????
        String filePathMaster = "C:\\Users\\??????\\Desktop\\stockPriceNew.xls";
        ExcelReader readerMaster = ExcelUtil.getReader(filePathMaster);
        List<ZxBuStockPriceItem> zxBuStockPriceItems = new ArrayList<>();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        try {
            List<Map<String, Object>> readMaster = readerMaster.read(2, 3, readerMaster.getSheet().getLastRowNum());
            List<Map<String, Object>> reads = reader.read(2, 3, reader.getSheet().getLastRowNum());
            if (reads == null || reads.size() == 0) {
                return repEntity.layerMessage("no", "excel?????????");
            }
            //??????????????????,?????????????????????
            for (int i = 0; i < reads.size(); i++) {
                ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
                String resCode = String.valueOf(reads.get(i).getOrDefault("????????????", ""));
                String resName = String.valueOf(reads.get(i).getOrDefault("????????????", ""));
                String unit = String.valueOf(reads.get(i).getOrDefault("??????", ""));
                String spec = String.valueOf(reads.get(i).getOrDefault("????????????", ""));
                if (i < 74) {
                    //????????????
                    String resCodeMaster = String.valueOf(readMaster.get(i).getOrDefault("????????????", ""));
                    if (!StrUtil.equals(resCode, resCodeMaster)) {
                        return repEntity.layerMessage("no", "??????75?????????????????????????????????");
                    }
                    //????????????
                    String resNameMaster = String.valueOf(readMaster.get(i).getOrDefault("????????????", ""));
                    if (!StrUtil.equals(resName, resNameMaster)) {
                        return repEntity.layerMessage("no", "??????75?????????????????????????????????");
                    }
                    //??????
//                    String unitMaster = String.valueOf(readMaster.get(i).getOrDefault("??????", ""));
//                    if (!StrUtil.equals(unit, unitMaster)) {
//                        return repEntity.layerMessage("no", "??????75?????????????????????????????????");
//                    }
                    //????????????
                    String specMaster = String.valueOf(readMaster.get(i).getOrDefault("????????????", ""));
                    if (!StrUtil.equals(spec, specMaster)) {
                        return repEntity.layerMessage("no", "??????75?????????????????????????????????");
                    }
                }
                //??????
                zxBuStockPriceItem.setResCode(StrUtil.isEmpty(resCode) ? "" : resCode);
                //????????????
                zxBuStockPriceItem.setResName(StrUtil.isEmpty(resName) ? "" : resName);
                //??????
                zxBuStockPriceItem.setUnit(StrUtil.isEmpty(unit) ? "" : unit);
                //????????????
                zxBuStockPriceItem.setSpec(StrUtil.isEmpty(spec) ? "" : spec);
                //??????id
                zxBuStockPriceItem.setZxBuStockPriceItemId(UUIDUtils.createUUID());
                //????????????????????????????????????????????????,???????????????,?????????0
                zxBuStockPriceItem.setQty(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("??????(?????????)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("??????(?????????)", "")) : "0"
                        )
                );
                //????????????(?????????)
                zxBuStockPriceItem.setMcsgPrice(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("????????????(?????????)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("????????????(?????????)", "")) : "0"
                        )
                );
                //????????????
                zxBuStockPriceItem.setTaxRate(
                        NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("????????????", "")))
                                ? String.valueOf(reads.get(i).getOrDefault("????????????", "")) : "0"
                );
                //??????????????????
                zxBuStockPriceItem.setMcsgPriceTax(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("??????????????????", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("??????????????????", "")) : "0"
                        )
                );
                //??????(?????????)
                zxBuStockPriceItem.setYsFee(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("??????(?????????)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("??????(?????????)", "")) : "0"
                        )
                );
                //????????????
                zxBuStockPriceItem.setYsTaxRate(
                        NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("????????????", "")))
                                ? String.valueOf(reads.get(i).getOrDefault("????????????", "")) : "0"
                );
                //??????????????????
                zxBuStockPriceItem.setYsFeeTax(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("??????????????????", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("??????????????????", "")) : "0"
                        )
                );
                //??????????????????(???)
                zxBuStockPriceItem.setScenePrice(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("??????????????????(???)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("??????????????????(???)", "")) : "0"
                        )
                );
                //????????????(???)
                zxBuStockPriceItem.setTaxAmt(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("????????????(???)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("????????????(???)", "")) : "0"
                        )
                );
                zxBuStockPriceItem.setMixType("11");
                zxBuStockPriceItem.setOrgID(zxBuStockPrice.getOrgID());
                zxBuStockPriceItem.setStockPriceID(zxBuStockPrice.getZxBuStockPriceId());
                zxBuStockPriceItem.setCreateUserInfo(userKey, realName);
                zxBuStockPriceItem.setDelFlag("0");
                //????????????????????? ???
                zxBuStockPriceItem.setIsAdjustment("0");
                //????????? ?????????0
                zxBuStockPriceItem.setReferValue(new BigDecimal("0"));
                //????????????id
                zxBuStockPriceItems.add(zxBuStockPriceItem);
            }
        } catch (Exception e) {
            LoggerUtils.printLogger(logger,e.getMessage());
            System.out.println(e);
        }
        //????????????
        ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
        zxBuStockPriceItem.setOrgID(zxBuStockPrice.getOrgID());
        zxBuStockPriceItem.setMixType("11");
        List<ZxBuStockPriceItem> zxBuStockPriceItemList = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
        if (zxBuStockPriceItemList != null && zxBuStockPriceItemList.size() > 0) {
            ZxBuStockPriceItem zxBuStockPriceItem1 = new ZxBuStockPriceItem();
            zxBuStockPriceItem1.setModifyUserInfo(userKey, realName);
            zxBuStockPriceItemMapper.batchDeleteUpdateZxBuStockPriceItem(zxBuStockPriceItemList, zxBuStockPriceItem1);
        }
        int flag = zxBuStockPriceItemMapper.insertAll(zxBuStockPriceItems);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuStockPrice);
        }
    }


}
