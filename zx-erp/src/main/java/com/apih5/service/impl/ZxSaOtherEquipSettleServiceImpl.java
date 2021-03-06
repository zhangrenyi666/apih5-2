package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.api.wechatutils.StringUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.ibm.icu.text.SimpleDateFormat;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSaOtherEquipSettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxSaOtherEquipSettleService")
public class ZxSaOtherEquipSettleServiceImpl implements ZxSaOtherEquipSettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleMapper zxSaOtherEquipSettleMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleMapper zxSaOtherEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleItemMapper zxSaOtherEquipResSettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleMapper zxSaOtherEquipPaySettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleItemMapper zxSaOtherEquipPaySettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemMapper zxSaOtherEquipSettleItemMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemServiceImpl zxSaOtherEquipSettleItemServiceImpl;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Override
    public ResponseEntity getZxSaOtherEquipSettleListByCondition(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSaOtherEquipSettle.setCompanyId("");
            zxSaOtherEquipSettle.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSaOtherEquipSettle.setCompanyId(zxSaOtherEquipSettle.getOrgId());
            zxSaOtherEquipSettle.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSaOtherEquipSettle.setOrgId(zxSaOtherEquipSettle.getOrgId());
        }
        // ????????????
        PageHelper.startPage(zxSaOtherEquipSettle.getPage(), zxSaOtherEquipSettle.getLimit());
        // ????????????
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
        //????????????
        for (ZxSaOtherEquipSettle zxSaOtherEquipSettle1 : zxSaOtherEquipSettleList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSaOtherEquipSettle1.getZxSaOtherEquipSettleId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSaOtherEquipSettle1.setZxErpFileList(zxErpFiles);
        }

        // ??????????????????
        PageInfo<ZxSaOtherEquipSettle> p = new PageInfo<>(zxSaOtherEquipSettleList);

        return repEntity.okList(zxSaOtherEquipSettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipSettleDetail(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        if(StrUtil.isNotEmpty(zxSaOtherEquipSettle.getWorkId())) {
            ZxSaOtherEquipSettle zxSaOtherEquipSettle1 = new ZxSaOtherEquipSettle();
            zxSaOtherEquipSettle1.setWorkId(zxSaOtherEquipSettle.getWorkId());
            List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle1);
            if(zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() >0) {
                dbZxSaOtherEquipSettle = zxSaOtherEquipSettleList.get(0);
            }
        }else {
            // ????????????
            dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        }
        // ????????????
        if (dbZxSaOtherEquipSettle != null) {
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            List<ZxErpFile> fj = new ArrayList<>();
            List<ZxErpFile> zw = new ArrayList<>();
            for (ZxErpFile file : zxErpFiles) {
                //???????????? 0?????? 1??????
                if ("0".equals(file.getOtherType())) {
                    fj.add(file);
                } else if ("1".equals(file.getOtherType())) {
                    zw.add(file);
                }
            }
            dbZxSaOtherEquipSettle.setZxErpFileList(fj);
            dbZxSaOtherEquipSettle.setDocumentFileList(zw);
            return repEntity.ok(dbZxSaOtherEquipSettle);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        dbZxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // ???????????????????????????
        String period = new SimpleDateFormat("yyyyMM").format(zxSaOtherEquipSettle.getPeriodDate());
        // ????????????
        dbZxSaOtherEquipSettle.setPeriod(period);
        List<ZxSaOtherEquipSettle> checkPeriodList = zxSaOtherEquipSettleMapper.checkPeriod(dbZxSaOtherEquipSettle);
        if (checkPeriodList != null && checkPeriodList.size() > 0) {
            return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????????????????");
        }

        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle1 = new ZxSaOtherEquipSettle();
        dbZxSaOtherEquipSettle1.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle1);
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            for (ZxSaOtherEquipSettle zxSaOtherEquipSettle1 : zxSaOtherEquipSettleList) {
                if (!"2".equals(zxSaOtherEquipSettle1.getApih5FlowStatus())) {
                    return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????");
                }
            }
        }
        // ?????????????????????ID????????????
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "?????????????????????ID???????????????");
        }
        zxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // ?????????????????????
        zxSaOtherEquipSettle.setPeriodDate(zxSaOtherEquipSettle.getPeriodDate());
        // ????????????
        zxSaOtherEquipSettle.setPeriod(period);
        // ????????????
        zxSaOtherEquipSettle.setApih5FlowStatus("-1");
        // ?????????
        zxSaOtherEquipSettle.setReportPerson(realName);
        // ?????????
        zxSaOtherEquipSettle.setFlowBeginPerson(realName);
        zxSaOtherEquipSettle.setZxSaOtherEquipSettleId(UuidUtil.generate());
        zxSaOtherEquipSettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipSettleMapper.insert(zxSaOtherEquipSettle);

        // ????????????????????????id???????????????????????????
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxCtOtherManageId());

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        ZxSaOtherEquipSettle isFirstZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        isFirstZxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        List<ZxSaOtherEquipSettle> isFirstZxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(isFirstZxSaOtherEquipSettle);
        // ?????????????????????????????????????????????????????????????????????????????????????????????
        if (isFirstZxSaOtherEquipSettleList != null && isFirstZxSaOtherEquipSettleList.size() == 1) {
            dbZxCtOtherManage.setSettleType("??????????????????");
            zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
        }

        BigDecimal zero = new BigDecimal(0);
        // ??????????????????????????????
        ZxSaOtherEquipResSettle zxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
        BeanUtil.copyProperties(zxSaOtherEquipSettle, zxSaOtherEquipResSettle);
        // ??????????????????
        zxSaOtherEquipResSettle.setContractAmt(dbZxCtOtherManage.getContractCost());
        // ???????????????????????????
        zxSaOtherEquipResSettle.setChangeAmt(dbZxCtOtherManage.getAlterContractSum());
        // ??????????????????????????????(???)
        zxSaOtherEquipResSettle.setThisAmt(zero);
        // ?????????????????????????????????(???)
        zxSaOtherEquipResSettle.setThisAmtNoTax(zero);
        // ????????????????????????(???)
        zxSaOtherEquipResSettle.setThisAmtTax(zero);
        // ???????????????
        zxSaOtherEquipResSettle.setSignedNos(zxSaOtherEquipSettle.getSignedNo());

        // ?????????????????????????????????????????????(???) ??????????????????????????????(???)
        if (zxSaOtherEquipSettleList == null || zxSaOtherEquipSettleList.size() == 0) {
            zxSaOtherEquipResSettle.setUpAmt(zero);
            zxSaOtherEquipResSettle.setTotalAmt(zero);
        } else {
            // ??????????????????????????????(???)
            ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
            dbZxSaOtherEquipResSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            // ????????????id?????????????????????????????????????????????????????????
            List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbZxSaOtherEquipResSettle);
            if (dbZxSaOtherEquipResSettleList != null && dbZxSaOtherEquipResSettleList.size() > 0) {
                BigDecimal totalThisAmt = null;
                for (ZxSaOtherEquipResSettle zxSaOtherEquipResSettle1 : dbZxSaOtherEquipResSettleList) {
                    // ?????????????????????????????????????????????????????????(???)?????????
                    totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipResSettle1.getThisAmt());
                }
                // ??????????????????????????????(???)???????????????
                zxSaOtherEquipResSettle.setTotalAmt(totalThisAmt);
            }

            // ???????????????????????????(???)
            ZxSaOtherEquipResSettle otherEquipResSettle = new ZxSaOtherEquipResSettle();
            otherEquipResSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            otherEquipResSettle.setPeriod(period);
            ZxSaOtherEquipResSettle selectItem = zxSaOtherEquipResSettleMapper.selectTotalAmt(otherEquipResSettle);
            zxSaOtherEquipResSettle.setUpAmt(selectItem.getUpAmt());
        }
        // ???????????????id
        zxSaOtherEquipResSettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        // ??????????????????id
        zxSaOtherEquipResSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // ??????????????????id
        zxSaOtherEquipResSettle.setZxSaOtherEquipResSettleId(UuidUtil.generate());
        zxSaOtherEquipResSettle.setCreateUserInfo(userKey, realName);
        // ??????????????????????????????
        zxSaOtherEquipResSettleMapper.insert(zxSaOtherEquipResSettle);

        // ???????????????????????????????????????
        ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
        dbZxCtOtherWorks.setZxCtOtherManageId(dbZxCtOtherManage.getZxCtOtherManageId());
        List<ZxCtOtherWorks> zxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
        if (zxCtOtherWorksList != null && zxCtOtherWorksList.size() > 0) {
            ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
            BeanUtil.copyProperties(zxSaOtherEquipSettle, zxSaOtherEquipResSettleItem);
            for (ZxCtOtherWorks zxCtOtherWorks : zxCtOtherWorksList) {
                // ???????????????????????????????????????
                zxSaOtherEquipResSettleItem.setEquipCode(zxCtOtherWorks.getWorkNo());
                // ????????????
                zxSaOtherEquipResSettleItem.setEquipName(zxCtOtherWorks.getWorkName());
                // ????????????
                zxSaOtherEquipResSettleItem.setUnit(zxCtOtherWorks.getUnit());
                // ?????????????????????
                zxSaOtherEquipResSettleItem.setContractPrice(zxCtOtherWorks.getPrice() == null ? zxCtOtherWorks.getChangePrice() : zxCtOtherWorks.getPrice());
                // ????????????
                zxSaOtherEquipResSettleItem.setContractQty(zxCtOtherWorks.getQty());
                // ??????????????????
                zxSaOtherEquipResSettleItem.setContractAmt(zxCtOtherWorks.getContractSum());
                // ??????
                zxSaOtherEquipResSettleItem.setTaxRate(zxCtOtherWorks.getTaxRate());
                // ?????????????????????
                zxSaOtherEquipResSettleItem.setChangedQty(zxCtOtherWorks.getChangeQty());
                // ???????????????????????????
                zxSaOtherEquipResSettleItem.setChangedAmt(zxCtOtherWorks.getChangeContractSum());
                // ??????????????????????????????
                zxSaOtherEquipResSettleItem.setTotalQty(zero);
                // ????????????????????????????????????(???)
                zxSaOtherEquipResSettleItem.setTotalAmt(zero);
                // ??????????????????????????????
                zxSaOtherEquipResSettleItem.setUpAmt(zero);
                // ????????????????????????????????????(???)
                zxSaOtherEquipResSettleItem.setUpQty(zero);
                // ??????????????????????????????????????????????????????(???) ??????????????????????????????
                if (!CollectionUtils.isEmpty(zxSaOtherEquipSettleList)) {
                    ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
                    dbZxSaOtherEquipResSettleItem.setZxCtOtherWorksId(zxCtOtherWorks.getZxCtOtherWorksId());
                    // ??????????????????????????????id???????????????????????????????????????????????????????????????
                    List<ZxSaOtherEquipResSettleItem> dbZxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(dbZxSaOtherEquipResSettleItem);
                    if (!CollectionUtils.isEmpty(dbZxSaOtherEquipResSettleItemList)) {
                        BigDecimal totalThisQty = null;
                        BigDecimal totalThisAmt = null;
                        for (ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem1 : dbZxSaOtherEquipResSettleItemList) {
                            // ??????????????????????????????????????????????????????????????????
                            totalThisQty = CalcUtils.calcAdd(totalThisQty, zxSaOtherEquipResSettleItem1.getThisQty());
                            // ?????????????????????????????????????????????????????????????????????????????????
                            totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipResSettleItem1.getThisAmt());
                        }
                        // ??????????????????????????????
                        zxSaOtherEquipResSettleItem.setTotalQty(totalThisQty);
                        // ????????????????????????????????????(???)
                        zxSaOtherEquipResSettleItem.setTotalAmt(totalThisAmt);
                    }
                    ZxSaOtherEquipResSettleItem resItem = new ZxSaOtherEquipResSettleItem();
                    resItem.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
                    resItem.setPeriod(period);
                    resItem.setZxCtOtherWorksId(zxCtOtherWorks.getZxCtOtherWorksId());
                    ZxSaOtherEquipResSettleItem selectItem = zxSaOtherEquipResSettleItemMapper.selectTotalQtyAmt(resItem);
                    if (selectItem != null) {
                        zxSaOtherEquipResSettleItem.setUpAmt(selectItem.getUpAmt());
                        zxSaOtherEquipResSettleItem.setUpQty(selectItem.getUpQty());
                    }
                }
                // ??????????????????id
                zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId());
                // ????????????id
                zxSaOtherEquipResSettleItem.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
                // ????????????????????????id
                zxSaOtherEquipResSettleItem.setZxCtOtherWorksId(zxCtOtherWorks.getZxCtOtherWorksId());
                zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleItemId(UuidUtil.createUUID());
                zxSaOtherEquipResSettleItem.setCreateUserInfo(userKey, realName);
                //???????????????????????????????????????
                zxSaOtherEquipResSettleItemMapper.insert(zxSaOtherEquipResSettleItem);
            }
        }
        //  ??????????????????????????????????????????????????????????????????????????????(???)
        ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        BeanUtil.copyProperties(zxSaOtherEquipSettle, zxSaOtherEquipPaySettle);
        // ???????????????????????????????????????????????????(???) ?????????????????????????????????(???)
        if (zxSaOtherEquipSettleList == null || zxSaOtherEquipSettleList.size() == 0) {
            zxSaOtherEquipPaySettle.setUpAmt(zero);
            zxSaOtherEquipPaySettle.setTotalAmt(zero);
        } else {
            ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
            dbZxSaOtherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            // ????????????id????????????????????????????????????????????????????????????
            List<ZxSaOtherEquipPaySettle> dbZxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbZxSaOtherEquipPaySettle);
            if (dbZxSaOtherEquipPaySettleList != null && dbZxSaOtherEquipPaySettleList.size() > 0) {
                BigDecimal totalThisAmt = null;
                for (ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle1 : dbZxSaOtherEquipPaySettleList) {
                    // ?????????????????????????????????????????????????????????????????????(???)?????????
                    totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipPaySettle1.getThisAmt());
                }
                // ?????????????????????????????????(???)???????????????
                zxSaOtherEquipPaySettle.setTotalAmt(totalThisAmt);
            }
            // ???????????????????????????????????????????????????(???)
            ZxSaOtherEquipPaySettle otherEquipPaySettle = new ZxSaOtherEquipPaySettle();
            otherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            otherEquipPaySettle.setPeriod(period);
            ZxSaOtherEquipPaySettle selectItem = zxSaOtherEquipPaySettleMapper.selectTotalAmt(otherEquipPaySettle);
            zxSaOtherEquipPaySettle.setUpAmt(selectItem.getUpAmt());
        }
        // ???????????????????????????(???)
        zxSaOtherEquipPaySettle.setThisAmt(zero);
        // ????????????????????????????????????(???)
        zxSaOtherEquipPaySettle.setThisAmtNoTax(zero);
        // ???????????????????????????(???)
        zxSaOtherEquipPaySettle.setThisAmtTax(zero);
        // ??????????????????id
        zxSaOtherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // ???????????????id
        zxSaOtherEquipPaySettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        // ?????????????????????id
        zxSaOtherEquipPaySettle.setZxSaOtherEquipPaySettleId(UuidUtil.createUUID());
        zxSaOtherEquipPaySettle.setCreateUserInfo(userKey, realName);
        // ?????????????????????????????????
        zxSaOtherEquipPaySettleMapper.insert(zxSaOtherEquipPaySettle);
        // ?????????????????????
        ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        zxSaOtherEquipSettleItem.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        zxSaOtherEquipSettleItem.setPeriod(period);
        List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = (List<ZxSaOtherEquipSettleItem>) zxSaOtherEquipSettleItemServiceImpl.getZxSaOtherEquipSettleItemByContractId(zxSaOtherEquipSettleItem).getData();
        if (zxSaOtherEquipSettleItemList != null && zxSaOtherEquipSettleItemList.size() > 0) {
            for (ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem1 : zxSaOtherEquipSettleItemList) {
                zxSaOtherEquipSettleItem1.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                zxSaOtherEquipSettleItem1.setBillNo(zxSaOtherEquipSettle.getBillNo());
                zxSaOtherEquipSettleItem1.setOrgId(zxSaOtherEquipSettle.getOrgId());
                zxSaOtherEquipSettleItem1.setComId(zxSaOtherEquipSettle.getCompanyId());
                zxSaOtherEquipSettleItem1.setComName(zxSaOtherEquipSettle.getCompanyName());
                zxSaOtherEquipSettleItem1.setComOrders(zxSaOtherEquipSettle.getComOrders());
                zxSaOtherEquipSettleItem1.setZxSaOtherEquipSettleItemId(UuidUtil.generate());
                zxSaOtherEquipSettleItem1.setCreateUserInfo(userKey, realName);
                zxSaOtherEquipSettleItemMapper.insert(zxSaOtherEquipSettleItem1);
                // ????????????????????????????????????
                if ("100100".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // ?????????????????? ?????????????????????????????????????????????
                    zxSaOtherEquipSettle.setThisAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                    // ?????????????????? ?????????????????????????????????????????????
                    zxSaOtherEquipSettle.setTotalAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getTotalAmt()));
                    // ???????????????????????????(???)
                    zxSaOtherEquipSettle.setUpTotalAmt(zxSaOtherEquipSettleItem1.getUpAmt());
                }
                if ("100110".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // ???????????????????????????(???) ??????????????????????????????????????????
                    zxSaOtherEquipSettle.setThisAmtNoTax(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                }
                if ("100120".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // ??????????????????(???)  ?????????????????????????????????
                    zxSaOtherEquipSettle.setThisAmtTax(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                }
                if ("100700".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // ??????????????????
                    zxSaOtherEquipSettle.setThisPayAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                    // ??????????????????
                    zxSaOtherEquipSettle.setTotalPayAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getTotalAmt()));
                }
                zxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
                zxSaOtherEquipSettleMapper.updateByPrimaryKey(zxSaOtherEquipSettle);
            }
        }
        //???????????? 0?????? 1??????
        List<ZxErpFile> fileList = zxSaOtherEquipSettle.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                zxErpFile.setOtherType("0");
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipSettle);
        }
    }

    @Override
    public ResponseEntity updateZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        if (dbZxSaOtherEquipSettle != null && StrUtil.isNotEmpty(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId())) {
            // ????????????
            dbZxSaOtherEquipSettle.setIsDeduct(zxSaOtherEquipSettle.getIsDeduct());
            // ????????????
            dbZxSaOtherEquipSettle.setContent(zxSaOtherEquipSettle.getContent());
            // ???????????????????????????
            dbZxSaOtherEquipSettle.setTchljjsje(zxSaOtherEquipSettle.getTchljjsje());
            // ???????????????????????????
            dbZxSaOtherEquipSettle.setBqtchjsje(zxSaOtherEquipSettle.getBqtchjsje());
            // ?????????????????????
            dbZxSaOtherEquipSettle.setPeriodDate(zxSaOtherEquipSettle.getPeriodDate());
            // ???????????????????????????
            String period = DateUtil.format(zxSaOtherEquipSettle.getPeriodDate(), "yyyyMM");
            // ????????????
            dbZxSaOtherEquipSettle.setPeriod(period);
            // ????????????????????????
            dbZxSaOtherEquipSettle.setBeginDate(zxSaOtherEquipSettle.getBeginDate());
            // ????????????
            dbZxSaOtherEquipSettle.setReportDate(zxSaOtherEquipSettle.getReportDate());
            // ???????????????
            dbZxSaOtherEquipSettle.setBillNo(zxSaOtherEquipSettle.getBillNo());
            // ????????????
            dbZxSaOtherEquipSettle.setContractNo(zxSaOtherEquipSettle.getContractNo());
            // ????????????
            dbZxSaOtherEquipSettle.setContractName(zxSaOtherEquipSettle.getContractName());
            // ??????ID
            dbZxSaOtherEquipSettle.setSecondId(zxSaOtherEquipSettle.getSecondId());
            // ????????????
            dbZxSaOtherEquipSettle.setSecondName(zxSaOtherEquipSettle.getSecondName());
            // ????????????
            dbZxSaOtherEquipSettle.setBillType(zxSaOtherEquipSettle.getBillType());
            // ????????????????????????
            dbZxSaOtherEquipSettle.setEndDate(zxSaOtherEquipSettle.getEndDate());
            // ????????????
            dbZxSaOtherEquipSettle.setBusinessDate(zxSaOtherEquipSettle.getBusinessDate());
            // ?????????
            dbZxSaOtherEquipSettle.setReportPerson(zxSaOtherEquipSettle.getReportPerson());
            // ???????????????
            dbZxSaOtherEquipSettle.setReportPersonPhone(zxSaOtherEquipSettle.getReportPersonPhone());
            // ?????????
            dbZxSaOtherEquipSettle.setCountPerson(zxSaOtherEquipSettle.getCountPerson());
            // ?????????
            dbZxSaOtherEquipSettle.setReCountPerson(zxSaOtherEquipSettle.getReCountPerson());
            // ??????????????????
            dbZxSaOtherEquipSettle.setIsFirst(zxSaOtherEquipSettle.getIsFirst());
            // ??????ID
            dbZxSaOtherEquipSettle.setWorkItemId(zxSaOtherEquipSettle.getWorkItemId());
            // ????????????ID
            dbZxSaOtherEquipSettle.setInstProcessId(zxSaOtherEquipSettle.getInstProcessId());
            // ??????????????????
            dbZxSaOtherEquipSettle.setFlowBeginDate(zxSaOtherEquipSettle.getFlowBeginDate());
            // ???????????????????????????
            dbZxSaOtherEquipSettle.setInitSerialNumber(zxSaOtherEquipSettle.getInitSerialNumber());
            // ???????????????
            dbZxSaOtherEquipSettle.setSignedNo(zxSaOtherEquipSettle.getSignedNo());
            // ????????????
            dbZxSaOtherEquipSettle.setAppraisal(zxSaOtherEquipSettle.getAppraisal());
            // workId
            dbZxSaOtherEquipSettle.setWorkId(zxSaOtherEquipSettle.getWorkId());
            // ??????id
            dbZxSaOtherEquipSettle.setApih5FlowId(zxSaOtherEquipSettle.getApih5FlowId());
            // ????????????
            dbZxSaOtherEquipSettle.setApih5FlowStatus(zxSaOtherEquipSettle.getApih5FlowStatus());
            // ??????????????????
            dbZxSaOtherEquipSettle.setApih5FlowNodeStatus(zxSaOtherEquipSettle.getApih5FlowNodeStatus());
            //???????????????
            if(StrUtil.equals("opinionField1", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField1(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField1(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField2(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField2(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField3(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField3(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField4(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField4(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField5(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField5(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField6(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField6(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField7(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField7(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField8(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField8(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField9(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField9(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField10(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField10(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            // ??????id
            dbZxSaOtherEquipSettle.setWorkId(zxSaOtherEquipSettle.getWorkId());
            // ??????1
            dbZxSaOtherEquipSettle.setOpinionField1(zxSaOtherEquipSettle.getOpinionField1());
            // ??????2
            dbZxSaOtherEquipSettle.setOpinionField2(zxSaOtherEquipSettle.getOpinionField2());
            // ??????3
            dbZxSaOtherEquipSettle.setOpinionField3(zxSaOtherEquipSettle.getOpinionField3());
            // ??????4
            dbZxSaOtherEquipSettle.setOpinionField4(zxSaOtherEquipSettle.getOpinionField4());
            // ??????5
            dbZxSaOtherEquipSettle.setOpinionField5(zxSaOtherEquipSettle.getOpinionField5());
            // ??????6
            dbZxSaOtherEquipSettle.setOpinionField6(zxSaOtherEquipSettle.getOpinionField6());
            // ??????7
            dbZxSaOtherEquipSettle.setOpinionField7(zxSaOtherEquipSettle.getOpinionField7());
            // ??????8
            dbZxSaOtherEquipSettle.setOpinionField8(zxSaOtherEquipSettle.getOpinionField8());
            // ??????9
            dbZxSaOtherEquipSettle.setOpinionField9(zxSaOtherEquipSettle.getOpinionField9());
            // ??????10
            dbZxSaOtherEquipSettle.setOpinionField10(zxSaOtherEquipSettle.getOpinionField10());
            // ??????
            dbZxSaOtherEquipSettle.setRemark(zxSaOtherEquipSettle.getRemark());
            // ??????
            dbZxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipSettle);

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
            zxErpFileSelect.setOtherType("0");
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //??????list
            List<ZxErpFile> fileList = zxSaOtherEquipSettle.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                    zxErpFile.setOtherType("0");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

            // ????????????
//            if(StrUtil.equals(zxSaOtherEquipSettle.getApih5FlowStatus(), "2")) {
//                this.zxSaOtherEquipSettleReviewApply(zxSaOtherEquipSettle);
//            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaOtherEquipSettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettle(List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ZxSaOtherEquipSettle zxSaOtherEquipSettle : zxSaOtherEquipSettleList) {
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // ?????????????????????????????????????????????
                ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
                dbZxSaOtherEquipResSettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbZxSaOtherEquipResSettle);
                if (dbZxSaOtherEquipResSettleList != null && dbZxSaOtherEquipResSettleList.size() > 0) {
                    // ??????????????????????????????
                    ZxSaOtherEquipResSettle delZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
                    delZxSaOtherEquipResSettle.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipResSettleMapper.batchDeleteUpdateZxSaOtherEquipResSettle(dbZxSaOtherEquipResSettleList, delZxSaOtherEquipResSettle);
                    // ????????????????????????????????????list
                    ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
                    dbZxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(dbZxSaOtherEquipResSettleList.get(0).getZxSaOtherEquipResSettleId());
                    List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(dbZxSaOtherEquipResSettleItem);
                    if (zxSaOtherEquipResSettleItemList != null && zxSaOtherEquipResSettleItemList.size() > 0) {
                        ZxSaOtherEquipResSettleItem delZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
                        delZxSaOtherEquipResSettleItem.setModifyUserInfo(userKey, realName);
                        zxSaOtherEquipResSettleItemMapper.batchDeleteUpdateZxSaOtherEquipResSettleItem(zxSaOtherEquipResSettleItemList, delZxSaOtherEquipResSettleItem);
                    }
                }
                // ????????????????????????????????????????????????
                ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
                dbZxSaOtherEquipPaySettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxSaOtherEquipPaySettle> zxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbZxSaOtherEquipPaySettle);
                if (zxSaOtherEquipPaySettleList != null && zxSaOtherEquipPaySettleList.size() > 0) {
                    // ?????????????????????????????????
                    ZxSaOtherEquipPaySettle delZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
                    delZxSaOtherEquipPaySettle.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipPaySettleMapper.batchDeleteUpdateZxSaOtherEquipPaySettle(zxSaOtherEquipPaySettleList, delZxSaOtherEquipPaySettle);
                    // ???????????????????????????????????????list
                    ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
                    dbZxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleList.get(0).getZxSaOtherEquipPaySettleId());
                    List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbZxSaOtherEquipPaySettleItem);
                    if (zxSaOtherEquipPaySettleItemList != null && zxSaOtherEquipPaySettleItemList.size() > 0) {
                        ZxSaOtherEquipPaySettleItem delZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
                        delZxSaOtherEquipPaySettleItem.setModifyUserInfo(userKey, realName);
                        zxSaOtherEquipPaySettleItemMapper.batchDeleteUpdateZxSaOtherEquipPaySettleItem(zxSaOtherEquipPaySettleItemList, delZxSaOtherEquipPaySettleItem);
                    }
                }
                ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                dbZxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(dbZxSaOtherEquipSettleItem);
                if (zxSaOtherEquipSettleItemList != null && zxSaOtherEquipSettleItemList.size() > 0) {
                    ZxSaOtherEquipSettleItem delZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                    delZxSaOtherEquipSettleItem.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.batchDeleteUpdateZxSaOtherEquipSettleItem(zxSaOtherEquipSettleItemList, delZxSaOtherEquipSettleItem);
                    // ???????????????????????????????????????????????????????????????1
                    for (ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem : zxSaOtherEquipSettleItemList) {
                        if (StringUtil.isNotEmpty(zxSaOtherEquipSettleItem.getZxCtOtherManageAmtRateId())) {
                            ZxCtOtherManageAmtRate dbZxCtOtherManageAmtRate = zxCtOtherManageAmtRateMapper.selectByPrimaryKey(zxSaOtherEquipSettleItem.getZxCtOtherManageAmtRateId());
                            dbZxCtOtherManageAmtRate.setUseCount(CalcUtils.calcSubtract(dbZxCtOtherManageAmtRate.getUseCount(), new BigDecimal(1)));
                            dbZxCtOtherManageAmtRate.setModifyUserInfo(userKey, realName);
                            zxCtOtherManageAmtRateMapper.updateByPrimaryKey(dbZxCtOtherManageAmtRate);
                        }
                    }
                }

                if(StrUtil.isNotEmpty(zxSaOtherEquipSettle.getWorkId())) {
                    jsonArray.add(zxSaOtherEquipSettle.getWorkId());
                }
            }
            String zxCtOtherManageId = zxSaOtherEquipSettleList.get(0).getZxCtOtherManageId();
            ZxSaOtherEquipSettle zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
            zxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleMapper.batchDeleteUpdateZxSaOtherEquipSettle(zxSaOtherEquipSettleList, zxSaOtherEquipSettle);

            // ????????????????????????id???????????????????????????
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManageId);

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            ZxSaOtherEquipSettle isFirstZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
            isFirstZxSaOtherEquipSettle.setZxCtOtherManageId(zxCtOtherManageId);
            List<ZxSaOtherEquipSettle> isFirstZxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(isFirstZxSaOtherEquipSettle);
            // ???????????????????????????????????????????????????
            if (isFirstZxSaOtherEquipSettleList == null || isFirstZxSaOtherEquipSettleList.size() == 0) {
                dbZxCtOtherManage.setSettleType("???????????????");
                zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
            }

            // ????????????????????????
            String url = Apih5Properties.getWebUrl() + "batchDeleteFlow";
            if(jsonArray.size()>0) {
                HttpUtil.sendPostToken(url, jsonArray.toString(), token);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaOtherEquipSettleList);
        }
    }

    /**
     *
     * @param realName ==??????
     * @param dbOpinionContent==??????????????????
     * @param opinionContent===????????????
     * @return
     */
    private String getOpinionContent(String realName, String dbOpinionContent, String opinionContent){
        if(StrUtil.isNotEmpty(opinionContent)){
            opinionContent = StrUtil.isEmpty(dbOpinionContent)? opinionContent: dbOpinionContent+ "<br/><br/>" + opinionContent;
            opinionContent += "<br/>" + realName + "  " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        return opinionContent;
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    @Override
    public ResponseEntity getZxSaOtherEquipSettleContractNo(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getOrgId())) {
            return repEntity.layerMessage("no", "orgId???????????????");
        }
        ZxCtOtherManage dbZxCtOtherManage = new ZxCtOtherManage();
        dbZxCtOtherManage.setOrgId(zxSaOtherEquipSettle.getOrgId());
        // ????????????
        PageHelper.startPage(zxSaOtherEquipSettle.getPage(), zxSaOtherEquipSettle.getLimit());
        // ????????????id????????????????????????????????????
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByOrgIdZxCtOtherManageList(dbZxCtOtherManage);

        // ??????????????????
        PageInfo<ZxCtOtherManage> p = new PageInfo<>(zxCtOtherManageList);
        return repEntity.okList(zxCtOtherManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getIsFirstByContractId(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        // ?????????????????????ID????????????
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "?????????????????????ID???????????????");
        }
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        dbZxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle);
        // ???????????????????????????????????????????????????????????????
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            zxSaOtherEquipSettle.setIsFirst("0");
        } else {
            zxSaOtherEquipSettle.setIsFirst("1");
        }
        return repEntity.ok(zxSaOtherEquipSettle);
    }

    @Override
    public ResponseEntity getSettleTypeByContractId(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        // ?????????????????????ID????????????
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "?????????????????????ID???????????????");
        }
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxCtOtherManageId());
        if("???????????????".equals(dbZxCtOtherManage.getSettleType())){
            return repEntity.ok("1");
        } else {
            return repEntity.ok("0");
        }
    }

    @Override
    public ResponseEntity zxSaOtherEquipSettleReviewApply(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        if (dbZxSaOtherEquipSettle != null && StrUtil.isNotEmpty(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId())) {
            // ?????????????????????????????????????????????
            // workId
            dbZxSaOtherEquipSettle.setWorkId(zxSaOtherEquipSettle.getWorkId());
            // ??????ID
            dbZxSaOtherEquipSettle.setWorkItemId(zxSaOtherEquipSettle.getWorkItemId());
            // ????????????ID
            dbZxSaOtherEquipSettle.setInstProcessId(zxSaOtherEquipSettle.getInstProcessId());
            // ??????????????????
            dbZxSaOtherEquipSettle.setFlowBeginDate(zxSaOtherEquipSettle.getFlowBeginDate());
            // ???????????????
            // dbZxSaOtherEquipSettle.setSignedNo(zxSaOtherEquipSettle.getSignedNo());
            // ????????????
            // dbZxSaOtherEquipSettle.setAppraisal(zxSaOtherEquipSettle.getAppraisal());
            // ??????id
            dbZxSaOtherEquipSettle.setApih5FlowId(zxSaOtherEquipSettle.getApih5FlowId());
            // ????????????
            dbZxSaOtherEquipSettle.setApih5FlowStatus(zxSaOtherEquipSettle.getApih5FlowStatus());
            // ??????????????????
            dbZxSaOtherEquipSettle.setApih5FlowNodeStatus(zxSaOtherEquipSettle.getApih5FlowNodeStatus());
            //???????????????
            if(StrUtil.equals("opinionField1", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField1(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField1(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField2(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField2(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField3(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField3(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField4(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField4(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField5(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField5(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField6(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField6(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField7(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField7(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField8(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField8(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField9(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField9(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField10(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField10(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            // ??????1
            dbZxSaOtherEquipSettle.setOpinionField1(zxSaOtherEquipSettle.getOpinionField1());
            // ??????2
            dbZxSaOtherEquipSettle.setOpinionField2(zxSaOtherEquipSettle.getOpinionField2());
            // ??????3
            dbZxSaOtherEquipSettle.setOpinionField3(zxSaOtherEquipSettle.getOpinionField3());
            // ??????4
            dbZxSaOtherEquipSettle.setOpinionField4(zxSaOtherEquipSettle.getOpinionField4());
            // ??????5
            dbZxSaOtherEquipSettle.setOpinionField5(zxSaOtherEquipSettle.getOpinionField5());
            // ??????6
            dbZxSaOtherEquipSettle.setOpinionField6(zxSaOtherEquipSettle.getOpinionField6());
            // ??????7
            dbZxSaOtherEquipSettle.setOpinionField7(zxSaOtherEquipSettle.getOpinionField7());
            // ??????8
            dbZxSaOtherEquipSettle.setOpinionField8(zxSaOtherEquipSettle.getOpinionField8());
            // ??????9
            dbZxSaOtherEquipSettle.setOpinionField9(zxSaOtherEquipSettle.getOpinionField9());
            // ??????10
            dbZxSaOtherEquipSettle.setOpinionField10(zxSaOtherEquipSettle.getOpinionField10());
            // ??????
            // dbZxSaOtherEquipSettle.setRemark(zxSaOtherEquipSettle.getRemark());

            dbZxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipSettle);
            if(!CollectionUtils.isEmpty(zxSaOtherEquipSettle.getDocumentFileList())) {
                ZxErpFile file = new ZxErpFile();
                file.setOtherId(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                file.setOtherType("1");
                List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
                file.setModifyUserInfo(userKey, realName);
                if(fileList.size() > 0) {
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
                }
                for(ZxErpFile zxErpFile : zxSaOtherEquipSettle.getDocumentFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFile.setOtherType("1");
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(dbZxSaOtherEquipSettle.getZxCtOtherManageId());
            // ????????????????????????????????????????????????????????????????????????????????????????????????
            if ("1".equals(dbZxSaOtherEquipSettle.getBillType())) {
                dbZxCtOtherManage.setSettleType("???????????????");
            }
            // ??????????????????????????????????????? ????????????????????????????????? ?????????????????????????????????????????????
            dbZxCtOtherManage.setTotalSettleAmount(dbZxSaOtherEquipSettle.getTotalAmt());
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaOtherEquipSettle);
        }
    }

    @Override
    public ResponseEntity getZxSaOtherEquipSettleBillNo(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
//        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getContractNo())) {
//            return repEntity.layerMessage("no", "???????????????????????????");
//        }
//        if ("".equals(zxSaOtherEquipSettle.getPeriodDate()) || zxSaOtherEquipSettle.getPeriodDate() == null) {
//            return repEntity.layerMessage("no", "????????????????????????????????????");
//        }
//        // ????????????
//        String contractNo = zxSaOtherEquipSettle.getContractNo();
//        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
//        dbZxSaOtherEquipSettle.setContractNo(contractNo);
//        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle);
//        // ????????????
//        String period = new SimpleDateFormat("yyyyMM").format(zxSaOtherEquipSettle.getPeriodDate());
//        // ???????????????????????????
//        String initSerialNumber = String.format("%02d", zxSaOtherEquipSettleList.size()+1);
//
//        ZxSaOtherEquipSettle zxSaOtherEquipSettle1 = new ZxSaOtherEquipSettle();
//        // ?????????????????????????????????
//        zxSaOtherEquipSettle1.setInitSerialNumber(initSerialNumber);
//        // ??????????????? ???????????? + ????????????????????? + ?????????
//        String billNo = contractNo + "-" + period.substring(period.length() - 4) + "-" + initSerialNumber;
//        zxSaOtherEquipSettle1.setBillNo(billNo);
//        // ?????????????????????
//        String signedNo = contractNo + "-SL-" + period.substring(period.length() - 4) + "-" + initSerialNumber;
//        zxSaOtherEquipSettle1.setSignedNo(signedNo);
//        return repEntity.ok(zxSaOtherEquipSettle1);
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getContractNo())) {
            return repEntity.layerMessage("no", "???????????????????????????");
        }
        ZxSaOtherEquipSettle otherEquipSettle = zxSaOtherEquipSettleMapper.getTheLastZxSaOtherEquipSettle(zxSaOtherEquipSettle.getContractNo());
        if (otherEquipSettle == null) {
            return repEntity.ok(0);
        } else {
            return repEntity.ok(Integer.parseInt(otherEquipSettle.getInitSerialNumber()));
        }
    }

    @Override
    public void exportZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle,HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // ????????????
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("????????????",
                "????????????",
                "????????????",
                "????????????",
                "????????????",
                "????????????",
                "????????????",
                "?????????????????????????????????",
                "?????????????????????????????????",
                "????????????????????????????????????",
                "????????????????????????????????????",
                "????????????????????????",
                "????????????????????????",
                "????????????",
                "?????????",
                "????????????"
        );
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            for (ZxSaOtherEquipSettle dbZxSaOtherEquipSettle : zxSaOtherEquipSettleList) {
                rowsList.add(CollUtil.newArrayList(dbZxSaOtherEquipSettle.getCompanyName(),
                        dbZxSaOtherEquipSettle.getBillNo(),
                        dbZxSaOtherEquipSettle.getOrgName(),
                        dbZxSaOtherEquipSettle.getPeriod(),
                        dbZxSaOtherEquipSettle.getBillType(),
                        dbZxSaOtherEquipSettle.getContractName(),
                        dbZxSaOtherEquipSettle.getSecondName(),
                        dbZxSaOtherEquipSettle.getThisAmt(),
                        dbZxSaOtherEquipSettle.getTotalAmt(),
                        dbZxSaOtherEquipSettle.getThisPayAmt(),
                        dbZxSaOtherEquipSettle.getTotalPayAmt(),
                        dbZxSaOtherEquipSettle.getBeginDate(),
                        dbZxSaOtherEquipSettle.getEndDate(),
                        dbZxSaOtherEquipSettle.getBusinessDate(),
                        dbZxSaOtherEquipSettle.getReportPerson(),
                        dbZxSaOtherEquipSettle.getApih5FlowStatus()
                        )
                );
            }

            // ????????????
            //String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
            //String fileName = "???????????????-" + datestr + ".xlsx";
            List<List<?>> rows = CollUtil.newArrayList(rowsList);

            // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // ??????response????????????
            // response.reset();
            //response???HttpServletResponse??????
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("?????????????????????".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // ???????????????????????????????????????????????????????????????
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // ??????writer???????????????
                if (writer != null) {
                    writer.close();
                }
                // ????????????????????????Servlet???
                if (out != null) {
                    IoUtil.close(out);
                }
            }
        }
    }

    @Override
    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // ????????????+??????
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.getZxSaOtherEquipSettleListInfo(zxSaOtherEquipSettle);
        BigDecimal thisAmt_t = BigDecimal.ZERO;
        BigDecimal upAmt_t = BigDecimal.ZERO;
        BigDecimal totalAmt_t = BigDecimal.ZERO;
        Optional<ZxSaOtherEquipSettle> optional = zxSaOtherEquipSettleList.stream().filter(e -> "??????".equals(e.getEquipCode())).findAny();
        if (optional.isPresent()) {
            thisAmt_t = CalcUtils.calcAdd(thisAmt_t, optional.get().getThisAmt());
            upAmt_t = CalcUtils.calcAdd(upAmt_t, optional.get().getUpAmt());
            totalAmt_t = CalcUtils.calcAdd(totalAmt_t, optional.get().getTotalAmt());
        }
        // ?????????
        List<ZxSaOtherEquipPaySettleItem> paySettleItems = zxSaOtherEquipSettleMapper.getZxSaOtherEquipPaySettleItemList(zxSaOtherEquipSettle);
        Map<String, List<ZxSaOtherEquipPaySettleItem>> maps = paySettleItems.stream().collect(Collectors.groupingBy(ZxSaOtherEquipPaySettleItem::getPayType));
        List<String> typeList = maps.keySet().stream().sorted().collect(Collectors.toList());
        // ????????????????????????
        String zxSaOtherEquipSettleId = zxSaOtherEquipSettle.getZxSaOtherEquipSettleId();
        for (String type : typeList) {
            // ????????????????????????
            ZxSaOtherEquipSettle pay = new ZxSaOtherEquipSettle();
            pay.setEquipCode(type);
            BigDecimal thisQty = BigDecimal.ZERO;
            BigDecimal thisAmt = BigDecimal.ZERO;
            BigDecimal upQty = BigDecimal.ZERO;
            BigDecimal upAmt = BigDecimal.ZERO;
            BigDecimal totalQty = BigDecimal.ZERO;
            BigDecimal totalAmt = BigDecimal.ZERO;
            for (ZxSaOtherEquipPaySettleItem paySettleItem : maps.get(type)) {
                if (paySettleItem.getZxSaOtherEquipSettleId().equals(zxSaOtherEquipSettleId)) {
                    thisQty = CalcUtils.calcAdd(thisQty, paySettleItem.getQty());
                    thisAmt = CalcUtils.calcAdd(thisAmt, paySettleItem.getThisAmt());
                } else {
                    upQty = CalcUtils.calcAdd(upQty, paySettleItem.getUpQty());
                    upAmt = CalcUtils.calcAdd(upAmt, paySettleItem.getUpAmt());
                }
                totalQty = CalcUtils.calcAdd(totalQty, paySettleItem.getQty());
                totalAmt = CalcUtils.calcAdd(totalAmt, paySettleItem.getThisAmt());
            }
            pay.setThisQty(thisQty);
            pay.setThisAmt(thisAmt);
            pay.setUpQty(upQty);
            pay.setUpAmt(upAmt);
            pay.setTotalQty(totalQty);
            pay.setTotalAmt(totalAmt);
            zxSaOtherEquipSettleList.add(pay);
            thisAmt_t = CalcUtils.calcAdd(thisAmt_t, thisAmt);
            upAmt_t = CalcUtils.calcAdd(upAmt_t, upAmt);
            totalAmt_t = CalcUtils.calcAdd(totalAmt_t, totalAmt);
            // ????????????????????????
            List<ZxSaOtherEquipPaySettleItem> paySettleItemList_this  = maps.get(type).stream().filter( e -> zxSaOtherEquipSettleId.equals(e.getZxSaOtherEquipSettleId())).collect(Collectors.toList());
            paySettleItemList_this.forEach(p -> {
                ZxSaOtherEquipSettle payItem = new ZxSaOtherEquipSettle();
                payItem.setEquipName(p.getPayName());
                payItem.setContractPrice(p.getPrice());
                payItem.setThisQty(p.getQty());
                payItem.setThisAmt(p.getThisAmt());
                payItem.setUpQty(BigDecimal.ZERO);
                payItem.setUpAmt(BigDecimal.ZERO);
                payItem.setTotalQty(p.getQty());
                payItem.setTotalAmt(p.getThisAmt());
                zxSaOtherEquipSettleList.add(payItem);
            });
        }
        // ??????
        ZxSaOtherEquipSettle totalDate = new ZxSaOtherEquipSettle();
        totalDate.setEquipCode("??????");
        totalDate.setThisAmt(thisAmt_t);
        totalDate.setUpAmt(upAmt_t);
        totalDate.setTotalAmt(totalAmt_t);
        zxSaOtherEquipSettleList.add(totalDate);
        // ?????????????????????
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        zxSaOtherEquipSettleList.forEach(e -> {
            e.setBillNo(dbZxSaOtherEquipSettle.getBillNo());
            e.setContractNo(dbZxSaOtherEquipSettle.getContractNo());
            e.setSecondName(dbZxSaOtherEquipSettle.getSecondName());
            e.setReportPerson(dbZxSaOtherEquipSettle.getReportPerson());
            e.setReCountPerson(dbZxSaOtherEquipSettle.getReCountPerson());
            e.setCompanyName(dbZxSaOtherEquipSettle.getCompanyName());
            e.setSecondName(dbZxSaOtherEquipSettle.getSecondName());
        });
        return zxSaOtherEquipSettleList;
    }

    @Override
    public ResponseEntity getUreportZxSaOtherEquipSettleList(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // ????????????
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.getZxSaOtherEquipSettleListForReport(zxSaOtherEquipSettle);
        JSONObject result = new JSONObject();
        result.set("settleList", zxSaOtherEquipSettleList);
        return repEntity.ok(result);
    }

    @Override
    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList_YGZ(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // ????????????
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.getZxSaOtherEquipSettleListForReport(zxSaOtherEquipSettle);
        return zxSaOtherEquipSettleList;
    }

}
