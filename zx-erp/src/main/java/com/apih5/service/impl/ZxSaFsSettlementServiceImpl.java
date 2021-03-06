package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.*;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.*;
import com.apih5.utils.DigitalConversionUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxSaFsSettlementService")
public class ZxSaFsSettlementServiceImpl implements ZxSaFsSettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementDetailService zxSaFsInventorySettlementDetailService;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementService zxSaFsInventorySettlementService;

    @Autowired(required = true)
    private ZxSaFsPaySettlementService zxSaFsPaySettlementService;

    @Autowired(required = true)
    private ZxSaFsPaySettlementDetailService zxSaFsPaySettlementDetailService;

    @Autowired(required = true)
    private ZxSaFsStatisticsDetailService zxSaFsStatisticsDetailService;

    @Autowired(required = true)
    private ZxSaFsSettlementMapper zxSaFsSettlementMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Autowired(required = true)
    ZxSaFsInventorySettlementDetailMapper zxSaFsInventorySettlementDetailMapper;

    @Autowired(required = true)
    ZxSaFsInventorySettlementMapper zxSaFsInventorySettlementMapper;

    @Autowired(required = true)
    ZxSaFsPaySettlementMapper zxSaFsPaySettlementMapper;

    @Autowired(required = true)
    ZxSaFsStatisticsDetailMapper zxSaFsStatisticsDetailMapper;

    @Autowired(required = true)
    ZxCtFsContractBondMapper zxCtFsContractBondMapper;

    @Autowired(required = true)
    ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    ZxSaFsPaySettlementDetailMapper zxSaFsPaySettlementDetailMapper;


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public ResponseEntity getZxSaFsSettlementListByCondition(ZxSaFsSettlement zxSaFsSettlement) {
        if (zxSaFsSettlement == null) {
            zxSaFsSettlement = new ZxSaFsSettlement();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSaFsSettlement.setComID("");
            zxSaFsSettlement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSaFsSettlement.setComID(zxSaFsSettlement.getOrgID());
            zxSaFsSettlement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSaFsSettlement.setOrgID(zxSaFsSettlement.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxSaFsSettlement.getPage(), zxSaFsSettlement.getLimit());
        // ????????????
        List<ZxSaFsSettlement> zxSaFsSettlementList = zxSaFsSettlementMapper.selectByZxSaFsSettlementList(zxSaFsSettlement);
        for (ZxSaFsSettlement zxSaFsSettlement1 : zxSaFsSettlementList
        ) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxSaFsSettlement1.getZxSaFsSettlementId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxSaFsSettlement1.setZxErpFileList(zxErpFileList);
            if (zxSaFsSettlement1.getPeriod() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(zxSaFsSettlement1.getPeriod().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long ts = date.getTime();
                zxSaFsSettlement1.setPeriodTime(ts);
            }

        }
        // ??????????????????
        PageInfo<ZxSaFsSettlement> p = new PageInfo<>(zxSaFsSettlementList);

        return repEntity.okList(zxSaFsSettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsSettlementDetail(ZxSaFsSettlement zxSaFsSettlement) {
        if (zxSaFsSettlement == null) {
            zxSaFsSettlement = new ZxSaFsSettlement();
        }
        ZxSaFsSettlement dbZxSaFsSettlement = new ZxSaFsSettlement();
        // ????????????
        if (zxSaFsSettlement.getZxSaFsSettlementId() != "") {
            dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());
        } else if (StrUtil.isNotEmpty(zxSaFsSettlement.getWorkId())) {
            dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByWorkId(zxSaFsSettlement.getWorkId());
        }


        // ????????????
        if (dbZxSaFsSettlement != null) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(dbZxSaFsSettlement.getZxSaFsSettlementId());
            file.setOtherType("1");
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxSaFsSettlement.setZxErpFileList(zxErpFileList);
            file.setOtherType("2");
            List<ZxErpFile> ZhengWen = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxSaFsSettlement.setZxZhengWenFileList(ZhengWen);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(dbZxSaFsSettlement.getPeriod().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long ts = date.getTime();
            dbZxSaFsSettlement.setPeriodTime(ts);
            return repEntity.ok(dbZxSaFsSettlement);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsSettlement.setZxSaFsSettlementId(UuidUtil.generate());
        zxSaFsSettlement.setCreateUserInfo(userKey, realName);
        // ??????????????????
        String result = new SimpleDateFormat("yyyyMM").format(zxSaFsSettlement.getPeriodTime());
        zxSaFsSettlement.setPeriod(result);
        zxSaFsSettlement.setApih5FlowStatus("-1");
        List<ZxSaFsSettlement> zxSaFsSettlements = zxSaFsSettlementMapper.selectMaxPeriod(zxSaFsSettlement);
        if (zxSaFsSettlements.size() > 0) {
            return repEntity.layerMessage("no", "????????????????????????????????????????????????");
        }
        //???????????????????????????
        syncAddinventorySettlement(zxSaFsSettlement);
        //????????????????????????
        syncInitZxPaySettlement(zxSaFsSettlement);

        ZxSaFsInventorySettlement zxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectBysettlementKey(zxSaFsSettlement.getZxSaFsSettlementId());
        ZxSaFsSettlement zxSaFsSettlement1 = zxSaFsSettlementMapper.selectLeijiInfo(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId());
//        zxSaFsSettlement.setThisAmt(zxSaFsInventorySettlement.getThisAmt());
//        zxSaFsSettlement.setThisAmtNoTax(zxSaFsInventorySettlement.getThisAmtNoTax());
//        zxSaFsSettlement.setThisAmtTax(zxSaFsInventorySettlement.getThisAmtTax());
        if (zxSaFsSettlement1 != null) {
            zxSaFsSettlement.setTotalAmt(CalcUtils.calcAdd(zxSaFsSettlement1.getTotalAmt(), zxSaFsSettlement.getThisAmt()));
            zxSaFsSettlement.setTotalAmtNoTax(CalcUtils.calcAdd(zxSaFsSettlement1.getTotalAmtNoTax(), zxSaFsSettlement.getThisAmtNoTax()));
        }
        ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = new ZxSaFsStatisticsDetail();
        zxSaFsStatisticsDetail1.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
        zxSaFsStatisticsDetail1.setStatisticsType("100700");
        zxSaFsStatisticsDetail1 = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail1);

        zxSaFsSettlement.setThisPayAmt(new BigDecimal(zxSaFsStatisticsDetail1.getThisAmt()));
        zxSaFsSettlement.setTotalPayAmt(new BigDecimal(zxSaFsStatisticsDetail1.getTotalAmt()));
        int flag = zxSaFsSettlementMapper.insert(zxSaFsSettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // ??????
            List<ZxErpFile> zxErpFileList = zxSaFsSettlement.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxSaFsSettlement.getZxSaFsSettlementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "?????????????????????");
                    }
                }
            }
            if ("1".equals(zxSaFsSettlement.getIsFirst())) {
                ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
                zxCtFsContract.setSettleType("??????????????????");
                zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
            }


            return repEntity.ok("sys.data.sava", zxSaFsSettlement);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaFsSettlement dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());
        if (dbZxSaFsSettlement != null && StrUtil.isNotEmpty(dbZxSaFsSettlement.getZxSaFsSettlementId())) {
            // ??????ID
            dbZxSaFsSettlement.setOrgID(zxSaFsSettlement.getOrgID());
            // ????????????
            dbZxSaFsSettlement.setOrgName(zxSaFsSettlement.getOrgName());
            // ???????????????
            dbZxSaFsSettlement.setSignedNo(zxSaFsSettlement.getSignedNo());
            // ????????????
            String result = new SimpleDateFormat("yyyyMM").format(zxSaFsSettlement.getPeriodTime());
            dbZxSaFsSettlement.setPeriod(result);
            // ????????????ID
            dbZxSaFsSettlement.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
            // ????????????
            dbZxSaFsSettlement.setContractNo(zxSaFsSettlement.getContractNo());
            // ????????????
            dbZxSaFsSettlement.setContractName(zxSaFsSettlement.getContractName());
            // ??????ID
            dbZxSaFsSettlement.setSecondID(zxSaFsSettlement.getSecondID());
            // ????????????
            dbZxSaFsSettlement.setSecondName(zxSaFsSettlement.getSecondName());
            // ????????????
            dbZxSaFsSettlement.setReportDate(zxSaFsSettlement.getReportDate());
            // ??????????????????
            dbZxSaFsSettlement.setIsFirst(zxSaFsSettlement.getIsFirst());
            // ???????????????????????????
            dbZxSaFsSettlement.setInitSerialNumber(zxSaFsSettlement.getInitSerialNumber());
            // ?????????????????????????????????
            dbZxSaFsSettlement.setStartDate(zxSaFsSettlement.getStartDate());
            // ?????????
            dbZxSaFsSettlement.setReportPerson(zxSaFsSettlement.getReportPerson());
            // ???????????????(??????????????????)
            dbZxSaFsSettlement.setOtherInfo(zxSaFsSettlement.getOtherInfo());
            // ????????????
            dbZxSaFsSettlement.setAppraisal(zxSaFsSettlement.getAppraisal());
            // ????????????
            dbZxSaFsSettlement.setAuditStatus(zxSaFsSettlement.getAuditStatus());
            // ?????????
            dbZxSaFsSettlement.setSerialNumber(zxSaFsSettlement.getSerialNumber());
            // ??????????????????
            dbZxSaFsSettlement.setEditTime(zxSaFsSettlement.getEditTime());
            // ????????????ID
            dbZxSaFsSettlement.setComID(zxSaFsSettlement.getComID());
            // ??????????????????
            dbZxSaFsSettlement.setComName(zxSaFsSettlement.getComName());
            // ??????????????????
            dbZxSaFsSettlement.setComOrders(zxSaFsSettlement.getComOrders());
            // ???????????????
            dbZxSaFsSettlement.setUseCount(zxSaFsSettlement.getUseCount());
            // ??????????????????
            dbZxSaFsSettlement.setIsMaxPeriod(zxSaFsSettlement.getIsMaxPeriod());
            // ???????????????
            dbZxSaFsSettlement.setBillNo(zxSaFsSettlement.getBillNo());
            // ????????????
            dbZxSaFsSettlement.setBusinessDate(zxSaFsSettlement.getBusinessDate());
            // ??????????????????(??????)
            dbZxSaFsSettlement.setContractAmt(zxSaFsSettlement.getContractAmt());
            // ???????????????????????????(??????)
            dbZxSaFsSettlement.setChangeAmt(zxSaFsSettlement.getChangeAmt());
            // ????????????
            dbZxSaFsSettlement.setBillType(zxSaFsSettlement.getBillType());
            // ????????????????????????(???)
            dbZxSaFsSettlement.setThisAmt(zxSaFsSettlement.getThisAmt());
            // ????????????????????????(???)
            dbZxSaFsSettlement.setTotalAmt(zxSaFsSettlement.getTotalAmt());
            // ????????????????????????(???)
            dbZxSaFsSettlement.setThisEquipAmt(zxSaFsSettlement.getThisEquipAmt());
            // ????????????????????????(???)
            dbZxSaFsSettlement.setTotalEquipAmt(zxSaFsSettlement.getTotalEquipAmt());
            // ??????????????????(???)
            dbZxSaFsSettlement.setThisPayAmt(zxSaFsSettlement.getThisPayAmt());
            // ??????????????????(???)
            dbZxSaFsSettlement.setTotalPayAmt(zxSaFsSettlement.getTotalPayAmt());
            // ?????????????????????
            dbZxSaFsSettlement.setIsFished(zxSaFsSettlement.getIsFished());
            // ???????????????????????????
            dbZxSaFsSettlement.setThisAmtNoTax(zxSaFsSettlement.getThisAmtNoTax());
            // ??????????????????
            dbZxSaFsSettlement.setThisAmtTax(zxSaFsSettlement.getThisAmtTax());
            // ??????????????????????????????
            dbZxSaFsSettlement.setUseSignedOrder(zxSaFsSettlement.getUseSignedOrder());
            // ??????(%)
            dbZxSaFsSettlement.setTaxRate(zxSaFsSettlement.getTaxRate());
            // ????????????
            dbZxSaFsSettlement.setIsDeduct(zxSaFsSettlement.getIsDeduct());
            // ???????????????ID
            dbZxSaFsSettlement.setBillID(zxSaFsSettlement.getBillID());
            // ????????????
            dbZxSaFsSettlement.setContrType(zxSaFsSettlement.getContrType());
            // ????????????????????????
            dbZxSaFsSettlement.setBeginDate(zxSaFsSettlement.getBeginDate());
            // ????????????????????????
            dbZxSaFsSettlement.setEndDate(zxSaFsSettlement.getEndDate());
            // ??????
            dbZxSaFsSettlement.setRemarks(zxSaFsSettlement.getRemarks());
            // ??????
            dbZxSaFsSettlement.setSort(zxSaFsSettlement.getSort());
            dbZxSaFsSettlement.setCountPerson(zxSaFsSettlement.getCountPerson());
            dbZxSaFsSettlement.setReCountPerson(zxSaFsSettlement.getCountPerson());
            dbZxSaFsSettlement.setContent(zxSaFsSettlement.getContent());

            dbZxSaFsSettlement.setApih5FlowStatus(zxSaFsSettlement.getApih5FlowStatus());
            dbZxSaFsSettlement.setApih5FlowNodeStatus(zxSaFsSettlement.getApih5FlowNodeStatus());
            dbZxSaFsSettlement.setApih5FlowId(zxSaFsSettlement.getApih5FlowId());
            dbZxSaFsSettlement.setWorkId(zxSaFsSettlement.getWorkId());

            if (StrUtil.equals("opinionField1", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField1(zxSaFsSettlement.getOpinionContent(realName,
                        dbZxSaFsSettlement.getOpinionField1()));
            }
            //
            if (StrUtil.equals("opinionField2", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField2(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField2()));
            }
            //
            if (StrUtil.equals("opinionField3", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField3(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField3()));
            }
            //
            if (StrUtil.equals("opinionField4", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField4(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField4()));
            }
            //
            if (StrUtil.equals("opinionField5", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField5(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField5()));
            }
            //
            if (StrUtil.equals("opinionField6", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField6(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField6()));
            }
            //
            if (StrUtil.equals("opinionField7", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField7(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField7()));
            }
            //
            if (StrUtil.equals("opinionField8", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField8(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField8()));
            }
            //
            if (StrUtil.equals("opinionField9", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField9(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField9()));
            }
            //
            if (StrUtil.equals("opinionField10", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField10(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField10()));
            }


            // ??????
            dbZxSaFsSettlement.setModifyUserInfo(userKey, realName);

            flag = zxSaFsSettlementMapper.updateByPrimaryKey(dbZxSaFsSettlement);
            //????????????????????????
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxSaFsSettlement.getZxSaFsSettlementId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxSaFsSettlement.getZxErpFileList() != null && zxSaFsSettlement.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxSaFsSettlement.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxSaFsSettlement.getZxSaFsSettlementId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            if ("2".equals(zxSaFsSettlement.getApih5FlowStatus()) && "1".equals(zxSaFsSettlement.getBillType())) {
                ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
                zxCtFsContract.setSettleType("???????????????");
                zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
            }
            return repEntity.ok("sys.data.update", zxSaFsSettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaFsSettlement(List<ZxSaFsSettlement> zxSaFsSettlementList) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSaFsSettlementList != null && zxSaFsSettlementList.size() > 0) {
            for (ZxSaFsSettlement zxSaFsSett : zxSaFsSettlementList) {
                if (StrUtil.isNotEmpty(zxSaFsSett.getWorkId())) {
                    jsonArr.add(zxSaFsSett.getWorkId());
                }
            }
            ZxSaFsSettlement zxSaFsSettlement = new ZxSaFsSettlement();
            zxSaFsSettlement.setModifyUserInfo(userKey, realName);
            synDelete(zxSaFsSettlementList);
            flag = zxSaFsSettlementMapper.batchDeleteUpdateZxSaFsSettlement(zxSaFsSettlementList, zxSaFsSettlement);
            if (jsonArr.size() > 0) {
                HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            for (ZxSaFsSettlement zxSaFsSettlement : zxSaFsSettlementList
            ) {
                if ("1".equals(zxSaFsSettlement.getIsFirst())) {
                    ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
                    zxCtFsContract.setSettleType("???????????????");
                    zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
                }
            }
            return repEntity.ok("sys.data.delete", zxSaFsSettlementList);
        }
    }

    //?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ????????????????????????????????????????????????????????????????????????
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncAddinventorySettlement(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
        ZxSaFsInventorySettlement zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
        ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        zxCtFsContractReviewDetail.setZxCtFsContractId(zxCtFsContract.getZxCtFsContractId());
        zxCtFsContractReviewDetail.setContractReviewId(zxCtFsContract.getContractReviewId());
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetails = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
        zxSaFsInventorySettlement.setContractAmt(zxCtFsContract.getContractCost());
        zxSaFsInventorySettlement.setChangeAmt(zxCtFsContract.getAlterContractSum());
        zxSaFsInventorySettlement.setZxSaFsInventorySettlementId(UuidUtil.generate());
        zxSaFsInventorySettlement.setCreateUserInfo(userKey, realName);
        BigDecimal thisAmt = new BigDecimal("0");
        BigDecimal thisAmtNoTax = new BigDecimal("0");
        BigDecimal thisAmtTax = new BigDecimal("0");
        BigDecimal upAmt = new BigDecimal("0");
        BigDecimal contractSum = new BigDecimal("0");
        BigDecimal changeContractSum = new BigDecimal("0");
        int flag = 0;
        for (ZxCtFsContractReviewDetail item : zxCtFsContractReviewDetails) {
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            thisAmt = CalcUtils.compareToZero(item.getChangeContractSum()) == 0 ?
                    CalcUtils.calcAdd(item.getContractSum(), thisAmt) : CalcUtils.calcAdd(item.getChangeContractSum(), thisAmt);
            thisAmtNoTax = CalcUtils.compareToZero(item.getChangeContractSumNoTax()) == 0 ?
                    CalcUtils.calcAdd(item.getContractSumNoTax(), thisAmtNoTax) : CalcUtils.calcAdd(item.getChangeContractSumNoTax(), thisAmt);

            thisAmtTax = CalcUtils.compareToZero(item.getChangeContractSum()) == 0 ?
                    CalcUtils.calcAdd(CalcUtils.calcSubtract(item.getContractSum(), item.getContractSumNoTax()), thisAmtTax) :
                    CalcUtils.calcAdd(CalcUtils.calcSubtract(item.getChangeContractSum(), item.getChangeContractSumNoTax()), thisAmtTax);

            contractSum = CalcUtils.calcAdd(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000"), 6), contractSum);
            changeContractSum = CalcUtils.calcAdd(CalcUtils.calcDivide(item.getChangeContractSum(), new BigDecimal("10000"), 6), changeContractSum);

            //???????????? ??????????????? ????????????????????????ID ???????????? ?????????????????? ??????????????????(???) ????????????????????????(???) ???????????????????????????(???) ??????????????????(???)

            zxSaFsInventorySettlementDetail.setZxSaFsEnumerationSettlementDetailedId(UuidUtil.generate());
            zxSaFsInventorySettlementDetail.setZxSaFsInventorySettlementId(zxSaFsInventorySettlement.getZxSaFsInventorySettlementId());//????????????id
            zxSaFsInventorySettlementDetail.setPeriod(zxSaFsSettlement.getPeriod());// ????????????
            zxSaFsInventorySettlementDetail.setBillNo(zxSaFsSettlement.getBillNo());// ???????????????
            zxSaFsInventorySettlementDetail.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());//????????????ID
            zxSaFsInventorySettlementDetail.setSignedNo(zxSaFsSettlement.getSignedNo());//???????????????
            zxSaFsInventorySettlementDetail.setConRevDetailId(item.getConRevDetailId());//??????ID(????????????ID)
            zxSaFsInventorySettlementDetail.setEquipCode(item.getWorkNo());//????????????
            zxSaFsInventorySettlementDetail.setEquipName(item.getWorkName());//????????????
            zxSaFsInventorySettlementDetail.setSpec(item.getSpec());//??????
            zxSaFsInventorySettlementDetail.setUnit(item.getUnit());// ????????????
            zxSaFsInventorySettlementDetail.setContractPrice(item.getPrice());// ????????????(???)
            zxSaFsInventorySettlementDetail.setContractQty(item.getQty()==null?null:new BigDecimal(item.getQty())); // ????????????
            zxSaFsInventorySettlementDetail.setContractAmt(item.getContractSum());//??????????????????
            if (item.getChangeQty() != null) {
                zxSaFsInventorySettlementDetail.setChangedQty(item.getChangeQty()==null?null:new BigDecimal((item.getChangeQty()))); // ???????????????
            }
            zxSaFsInventorySettlementDetail.setChangedAmt(item.getChangeContractSum()); // ?????????????????????(???)
            zxSaFsInventorySettlementDetail.setDelFlag("0");
            ZxSaFsInventorySettlementDetail SettlementDetail = new ZxSaFsInventorySettlementDetail();

            SettlementDetail = zxSaFsInventorySettlementDetailMapper.selectUpInfo(zxSaFsSettlement.getPeriod(), item.getConRevDetailId());

            if (SettlementDetail != null) {
                zxSaFsInventorySettlementDetail.setUpQty(SettlementDetail.getThisQty());// ??????????????????????????????
                zxSaFsInventorySettlementDetail.setUpAmt(SettlementDetail.getThisAmt());// ????????????????????????????????????(???)
                zxSaFsInventorySettlementDetail.setTotalQty(SettlementDetail.getThisQty());// ??????????????????????????????
                zxSaFsInventorySettlementDetail.setTotalAmt(SettlementDetail.getThisAmt());// ????????????????????????????????????(???)
            }
            zxSaFsInventorySettlementDetail.setComID(zxSaFsSettlement.getComID());// ????????????ID
            zxSaFsInventorySettlementDetail.setComName(zxSaFsSettlement.getComName());//??????????????????
            zxSaFsInventorySettlementDetail.setComOrders(zxSaFsSettlement.getComOrders());// ??????????????????
            zxSaFsInventorySettlementDetail.setAlterPrice(item.getChangePrice());// ???????????????(???)
            zxSaFsInventorySettlementDetail.setTaxRate(item.getTaxRate());// ??????(%)
            flag = zxSaFsInventorySettlementDetailMapper.insert(zxSaFsInventorySettlementDetail);
            if (flag == 0) {
                throw new Apih5Exception("????????????????????????????????????!");
            }

            upAmt = CalcUtils.calcAdd(zxSaFsInventorySettlementDetail.getUpAmt(), upAmt);

        }
        //  ?????????ID
        //zxSaFsInventorySettlement.setThisAmt(thisAmt);// ??????????????????????????????(???)
        //zxSaFsInventorySettlement.setThisAmtNoTax(thisAmtNoTax);//?????????????????????????????????(???)
        //zxSaFsInventorySettlement.setThisAmtTax(thisAmtTax);//????????????????????????(???)
        ZxSaFsInventorySettlement zxSaFsInventorySettlement1 = new ZxSaFsInventorySettlement();
        zxSaFsInventorySettlement1.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        zxSaFsInventorySettlement1.setPeriod(zxSaFsSettlement.getPeriod());
        zxSaFsInventorySettlement1 = zxSaFsInventorySettlementMapper.countTotalAmt(zxSaFsInventorySettlement1);
        if (zxSaFsInventorySettlement1 != null) {
            zxSaFsInventorySettlement.setTotalAmt(zxSaFsInventorySettlement1.getTotalAmt());//????????????????????????(???)
            zxSaFsInventorySettlement.setUpAmt(zxSaFsInventorySettlement1.getTotalAmt());//???????????????????????????
        }


        zxSaFsInventorySettlement.setOrgID(zxSaFsSettlement.getOrgID());// ??????ID
        zxSaFsInventorySettlement.setOrgName(zxSaFsSettlement.getOrgName());// ????????????
        zxSaFsInventorySettlement.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());//????????????ID
        zxSaFsInventorySettlement.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());//??????????????????ID
        zxSaFsInventorySettlement.setBillNo(zxSaFsSettlement.getBillNo());// ???????????????
        zxSaFsInventorySettlement.setPeriod(zxSaFsSettlement.getPeriod());// ????????????
        zxSaFsInventorySettlement.setSignedNos(zxSaFsSettlement.getSignedNo());// ???????????????
        zxSaFsInventorySettlement.setContractAmt(contractSum);// ??????????????????(??????)
        //zxSaFsInventorySettlement.setChangeAmt(changeContractSum);// ???????????????????????????(??????)
        zxSaFsInventorySettlement.setComID(zxSaFsSettlement.getComID()); // ????????????ID
        zxSaFsInventorySettlement.setComName(zxSaFsSettlement.getComName());// ??????????????????
        zxSaFsInventorySettlement.setComOrders(zxSaFsSettlement.getComOrders());// ??????????????????
        zxSaFsInventorySettlement.setContrType(zxSaFsSettlement.getContrType());// ????????????
        zxSaFsInventorySettlement.setIsDeduct(zxSaFsSettlement.getIsDeduct());// ????????????
        zxSaFsInventorySettlement.setTaxRate(zxSaFsSettlement.getTaxRate());// ??????(%)
        zxSaFsInventorySettlement.setIsFirst(zxSaFsSettlement.getIsFirst());// ??????????????????


        flag = zxSaFsInventorySettlementMapper.insert(zxSaFsInventorySettlement);
        if (flag == 0) {
            throw new Apih5Exception("??????????????????????????????!");
        }
        ZxCtFsContractBond zxCtFsContractBond = new ZxCtFsContractBond();
        zxCtFsContractBond.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        List<ZxCtFsContractBond> zxCtFsContractBondlist = zxCtFsContractBondMapper.selectByZxCtFsContractBondList(zxCtFsContractBond);
        synInitStatistics(zxSaFsSettlement, "100100", "????????????????????????????????????", "100100", null, null, 1);
        synInitStatistics(zxSaFsSettlement, "100110", "???????????????????????????????????????", "100110", null, null, 2);
        synInitStatistics(zxSaFsSettlement, "100120", "??????????????????????????????", "100120", null, null, 3);

        synInitStatistics(zxSaFsSettlement, "100200", "????????????????????????????????????", "100200", null, null, 4);
        synInitStatistics(zxSaFsSettlement, "100210", "???????????????????????????????????????", "100210", null, null, 5);
        synInitStatistics(zxSaFsSettlement, "100220", "??????????????????????????????", "100220", null, null, 6);

        synInitStatistics(zxSaFsSettlement, "100300", "???????????????????????????", "100300", null, null, 7);
        int i = 8;
        if (zxCtFsContractBondlist.size() != 0) {
            for (ZxCtFsContractBond Bond : zxCtFsContractBondlist) {
                synInitStatistics(zxSaFsSettlement, "", "", "", Bond, 1, i);
                i++;
            }

        }
        synInitStatistics(zxSaFsSettlement, "100300", "?????????????????????", "100500", null, null, i);
        int j = i + 1;
        if (zxCtFsContractBondlist.size() != 0) {
            for (ZxCtFsContractBond Bond : zxCtFsContractBondlist) {
                synInitStatistics(zxSaFsSettlement, "", "", "", Bond, 2, j);
                j++;
            }
        }
        synInitStatistics(zxSaFsSettlement, "100700", "???????????????????????????", "100700", null, null, j);
        synInitStatistics(zxSaFsSettlement, "100800", "???????????????????????????", "100800", null, null, j + 1);

    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncInitZxPaySettlement(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        //???????????????ID?????????????????????
        BigDecimal totalAmt = zxSaFsPaySettlementMapper.sumThisAmt(zxSaFsSettlement.getZxCtFsContractId(), zxSaFsSettlement.getPeriod());
        totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;

        ZxSaFsPaySettlement zxSaFsPaySettlement = new ZxSaFsPaySettlement();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsPaySettlement.setZxSaFsPaySettlementId(UuidUtil.generate());
        zxSaFsPaySettlement.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
        zxSaFsPaySettlement.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        zxSaFsPaySettlement.setCreateUserInfo(userKey, realName);
        zxSaFsPaySettlement.setTotalAmt(totalAmt);
        int flag = 0;
        flag = zxSaFsPaySettlementMapper.insert(zxSaFsPaySettlement);
        if (flag == 0) {
            throw new Apih5Exception("??????????????????????????????!");
        }
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void synInitStatistics(ZxSaFsSettlement zxSaFsSettlement, String statisticsNo, String statisticsName, String statisticsTyp, ZxCtFsContractBond zxCtFsContractBond, Integer type, int sortFlag) throws Exception {
        int flag = 0;

        ZxSaFsStatisticsDetail zxSaFsStatisticsDetails = new ZxSaFsStatisticsDetail();
        ZxSaFsStatisticsDetail zxSaFsStatisticsDetails1 = new ZxSaFsStatisticsDetail();
        zxSaFsStatisticsDetails.setZxSaFsStatisticsDetailId(UuidUtil.generate());
        zxSaFsStatisticsDetails.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
        zxSaFsStatisticsDetails.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        zxSaFsStatisticsDetails.setPeriod(zxSaFsSettlement.getPeriod());
        zxSaFsStatisticsDetails.setOrgID(zxSaFsSettlement.getOrgID());
        zxSaFsStatisticsDetails.setComID(zxSaFsSettlement.getComID());
        zxSaFsStatisticsDetails.setComName(zxSaFsSettlement.getComName());
        zxSaFsStatisticsDetails.setComOrders(zxSaFsSettlement.getComOrders());
        zxSaFsStatisticsDetails.setDelFlag("0");
        BigDecimal thisAmt = new BigDecimal("0");
        zxSaFsStatisticsDetails.setTotalAmt("0");
        if (zxCtFsContractBond == null) {
            zxSaFsStatisticsDetails.setStatisticsNo(statisticsNo);
            if ("100300".equals(statisticsTyp)) {
                zxSaFsStatisticsDetails.setStatisticsID(zxSaFsSettlement.getZxCtFsContractId() + statisticsNo);
                zxSaFsStatisticsDetails1.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails1.setStatisticsType("100300");
                zxSaFsStatisticsDetails1.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
                //zxSaFsStatisticsDetails1.setSort(1);
                zxSaFsStatisticsDetails.setThisAmt("0");
                zxSaFsStatisticsDetails.setTotalAmt("0");
                ZxSaFsStatisticsDetail SaFsStatisticsDetail1 = zxSaFsStatisticsDetailMapper.selectBaoZhengJinSum(zxSaFsStatisticsDetails1);
                if (SaFsStatisticsDetail1 != null) {
                    if (SaFsStatisticsDetail1.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(SaFsStatisticsDetail1.getAmt()));
                    }
                }

            }
            if ("100500".equals(statisticsTyp)) {
                zxSaFsStatisticsDetails.setStatisticsID(zxSaFsSettlement.getZxCtFsContractId() + statisticsNo + "_RETURN");
                zxSaFsStatisticsDetails1.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails1.setStatisticsType("100500");
                zxSaFsStatisticsDetails1.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
                // zxSaFsStatisticsDetails1.setSort(2);
                zxSaFsStatisticsDetails.setThisAmt("0");
                zxSaFsStatisticsDetails.setTotalAmt("0");
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = zxSaFsStatisticsDetailMapper.selectBaoZhengJinSum(zxSaFsStatisticsDetails1);
                if (zxSaFsStatisticsDetail1 != null) {
                    if (zxSaFsStatisticsDetail1.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(zxSaFsStatisticsDetail1.getAmt()));
                    }
                }

            } else {
                zxSaFsStatisticsDetails.setStatisticsID(zxSaFsSettlement.getZxCtFsContractId() + statisticsNo);
                if ("100200".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmt() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmt();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), "100100");
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100210".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtNoTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtNoTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), "100110");
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100220".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), "100120");
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100100".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmt() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmt();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), statisticsTyp);
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100110".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtNoTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtNoTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), statisticsTyp);
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100120".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), statisticsTyp);
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100700".equals(statisticsTyp)) {
                    ZxSaFsStatisticsDetail amt = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail kouChu = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail fanhuan = new ZxSaFsStatisticsDetail();
                    fanhuan.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    fanhuan.setPeriod(zxSaFsSettlement.getPeriod());
                    fanhuan.setStatisticsType("100500");
                    kouChu.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    kouChu.setPeriod(zxSaFsSettlement.getPeriod());
                    kouChu.setStatisticsType("100300");
                    amt.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    amt.setPeriod(zxSaFsSettlement.getPeriod());
                    amt.setStatisticsType("100100");
                    amt = zxSaFsStatisticsDetailMapper.selectByContractId(amt);
                    kouChu = zxSaFsStatisticsDetailMapper.selectByContractId(kouChu);
                    fanhuan = zxSaFsStatisticsDetailMapper.selectByContractId(fanhuan);

                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(CalcUtils.calcSubtract(new BigDecimal(amt.getThisAmt()), new BigDecimal(kouChu.getThisAmt()))));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(amt.getTotalAmt()), new BigDecimal(kouChu.getTotalAmt())), new BigDecimal(fanhuan.getTotalAmt()))));
                } else if ("100800".equals(statisticsTyp)) {
                    ZxSaFsStatisticsDetail amt = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail kouChu = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail fanhuan = new ZxSaFsStatisticsDetail();
                    fanhuan.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    fanhuan.setPeriod(zxSaFsSettlement.getPeriod());
                    fanhuan.setStatisticsType("100500");
                    kouChu.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    kouChu.setPeriod(zxSaFsSettlement.getPeriod());
                    kouChu.setStatisticsType("100300");
                    amt.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    amt.setPeriod(zxSaFsSettlement.getPeriod());
                    amt.setStatisticsType("100100");
                    amt = zxSaFsStatisticsDetailMapper.selectByContractId(amt);
                    kouChu = zxSaFsStatisticsDetailMapper.selectByContractId(kouChu);
                    fanhuan = zxSaFsStatisticsDetailMapper.selectByContractId(fanhuan);

                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(new BigDecimal(amt.getThisAmt()), new BigDecimal(kouChu.getThisAmt()))));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(amt.getTotalAmt()), new BigDecimal(kouChu.getTotalAmt())), new BigDecimal(fanhuan.getTotalAmt()))));
                }
            }
            zxSaFsStatisticsDetails.setStatisticsName(statisticsName);
            zxSaFsStatisticsDetails.setStatisticsType(statisticsTyp);
        } else {
            //??????????????????????????????
            if (type == 1) {
                zxSaFsStatisticsDetails.setThisAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getThisAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6))));
                //?????????????????????
                ZxSaFsStatisticsDetail baoZhengJin = new ZxSaFsStatisticsDetail();
                baoZhengJin.setStatisticsID(zxCtFsContractBond.getZxCtFsContractBondId());
                baoZhengJin.setSort(1);
                baoZhengJin.setPeriod(zxSaFsSettlement.getPeriod());

                baoZhengJin = zxSaFsStatisticsDetailMapper.selectBzjTotal(baoZhengJin);
                if (baoZhengJin != null) {
                    if (baoZhengJin.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(baoZhengJin.getAmt(), CalcUtils.calcMultiply(zxSaFsSettlement.getTotalAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6)))));
                    } else {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getTotalAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6))));
                    }
                } else {
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getTotalAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6))));
                }

                zxSaFsStatisticsDetails.setRate(zxCtFsContractBond.getStatisticsRate());
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails2 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails2.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                zxSaFsStatisticsDetails2.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails2.setStatisticsType("100300");
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails3 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails3 = zxSaFsStatisticsDetailMapper.selectByContractId(zxSaFsStatisticsDetails2);

                BigDecimal thisAmt1 = zxSaFsStatisticsDetails3.getThisAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getThisAmt());
                BigDecimal totalAmt1 = zxSaFsStatisticsDetails3.getTotalAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getTotalAmt());
                zxSaFsStatisticsDetails3.setThisAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getThisAmt()), thisAmt1)));
                zxSaFsStatisticsDetails3.setTotalAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getTotalAmt()), totalAmt1)));
                zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetails3);

            }
            //?????????????????????????????????
            if (type == 2) {
                //?????????????????????
                ZxSaFsStatisticsDetail baoZhengJin = new ZxSaFsStatisticsDetail();
                baoZhengJin.setStatisticsID(zxCtFsContractBond.getZxCtFsContractBondId());
                baoZhengJin.setSort(2);
                baoZhengJin.setPeriod(zxSaFsSettlement.getPeriod());
                baoZhengJin = zxSaFsStatisticsDetailMapper.selectBzjTotal(baoZhengJin);

                if (baoZhengJin != null) {
                    if (baoZhengJin.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(baoZhengJin.getAmt()));
                    }
                }

                zxSaFsStatisticsDetails.setRate(zxCtFsContractBond.getStatisticsRate());
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails2 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails2.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                zxSaFsStatisticsDetails2.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails2.setStatisticsType("100500");
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails3 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails3 = zxSaFsStatisticsDetailMapper.selectByContractId(zxSaFsStatisticsDetails2);

                BigDecimal thisAmt1 = zxSaFsStatisticsDetails3.getThisAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getThisAmt());
                BigDecimal totalAmt1 = zxSaFsStatisticsDetails3.getTotalAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getTotalAmt());
                //zxSaFsStatisticsDetails3.setThisAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getThisAmt()),thisAmt1)));
                zxSaFsStatisticsDetails3.setTotalAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getTotalAmt()), totalAmt1)));
                zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetails3);
            }
            zxSaFsStatisticsDetails.setSort(type);
            zxSaFsStatisticsDetails.setStatisticsNo(zxCtFsContractBond.getStatisticsNo());
            zxSaFsStatisticsDetails.setStatisticsName(zxCtFsContractBond.getStatisticsName());
            zxSaFsStatisticsDetails.setStatisticsID(zxCtFsContractBond.getZxCtFsContractBondId());

        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsStatisticsDetails.setCreateUserInfo(userKey, realName);
        zxSaFsStatisticsDetails.setSortFlag(sortFlag);
        flag = zxSaFsStatisticsDetailMapper.insert(zxSaFsStatisticsDetails);
        if (flag == 0) {
            throw new Apih5Exception("?????????????????????!");
        }
    }


    /**
     * ????????????????????????????????????
     *
     * @param response
     * @author suncg
     */

    @Override
    public ResponseEntity exportZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxSaFsSettlement == null) {
            zxSaFsSettlement = new ZxSaFsSettlement();
        }

        // ????????????
        List<ZxSaFsSettlement> zxSaFsSettlementList = zxSaFsSettlementMapper.selectByZxSaFsSettlementList(zxSaFsSettlement);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("????????????", "????????????", "????????????", "????????????", "????????????????????????", "????????????????????????",
                "???????????????????????????", "???????????????????????????");
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxSaFsSettlementList != null && zxSaFsSettlementList.size() > 0) {
            for (ZxSaFsSettlement zxSaFsSettlement1 : zxSaFsSettlementList) {
                rowsList.add(CollUtil.newArrayList(zxSaFsSettlement1.getContractNo(),
                        zxSaFsSettlement1.getContractNo(),
                        zxSaFsSettlement1.getPeriod(),
                        zxSaFsSettlement1.getBillType(),
                        zxSaFsSettlement1.getApih5FlowStatus(),
                        zxSaFsSettlement1.getThisAmt(),
                        zxSaFsSettlement1.getTotalAmt(),
                        zxSaFsSettlement1.getThisPayAmt(),
                        zxSaFsSettlement1.getTotalPayAmt()
                        )
                );
            }

            // ????????????
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
            String fileName = "xxx??????-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // ??????response????????????
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("fdsafdsa".getBytes("utf-8"), "iso-8859-1") + "\"");
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

            //String url = HttpUtil.
            return null;
        } else {
            return repEntity.ok("?????????");
        }
    }

    /**
     * @suncg ????????????????????????
     */
    @Override
    public ResponseEntity countByContract(ZxSaFsSettlement zxSaFsSettlement) {
        ZxSaFsSettlement zxSaFsSettlement1 = zxSaFsSettlementMapper.selectCountByContract(zxSaFsSettlement);
        if (zxSaFsSettlement1 == null) {
            zxSaFsSettlement1 = new ZxSaFsSettlement();
            zxSaFsSettlement1.setInitSerialNumber("0");
        } else {
            if ("".equals(zxSaFsSettlement1.getInitSerialNumber())) {
                zxSaFsSettlement1.setInitSerialNumber("0");
            }
        }
        return repEntity.ok(zxSaFsSettlement1);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param zxSaFsSettlementList
     * @suncg
     */

    @Transactional(rollbackFor = Exception.class)
    public void synDelete(List<ZxSaFsSettlement> zxSaFsSettlementList) throws Exception {
        for (ZxSaFsSettlement zx : zxSaFsSettlementList
        ) {
            //???????????????????????????
            ZxSaFsInventorySettlement zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
            zxSaFsInventorySettlement.setZxSaFsSettlementId(zx.getZxSaFsSettlementId());
            List<ZxSaFsInventorySettlement> inventoryList = zxSaFsInventorySettlementMapper.selectByZxSaFsInventorySettlementList(zxSaFsInventorySettlement);
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            if (inventoryList.size() > 0) {
                zxSaFsInventorySettlementDetail.setZxSaFsInventorySettlementId(inventoryList.get(0).getZxSaFsInventorySettlementId());
                List<ZxSaFsInventorySettlementDetail> InvertoryDetailList = zxSaFsInventorySettlementDetailMapper.selectByZxSaFsInventorySettlementDetailList(zxSaFsInventorySettlementDetail);
                if (InvertoryDetailList.size() > 0) {
                    zxSaFsInventorySettlementDetailService.batchDeleteUpdateZxSaFsInventorySettlementDetail(InvertoryDetailList);
                }
                zxSaFsInventorySettlementService.batchDeleteUpdateZxSaFsInventorySettlement(inventoryList);
            }


            //??????????????????????????????
            ZxSaFsPaySettlement zxSaFsPaySettlement = new ZxSaFsPaySettlement();
            zxSaFsPaySettlement.setZxSaFsSettlementId(zx.getZxSaFsSettlementId());
            List<ZxSaFsPaySettlement> zxSaFsPaySettlementList = zxSaFsPaySettlementMapper.selectByZxSaFsPaySettlementList(zxSaFsPaySettlement);

            ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail = new ZxSaFsPaySettlementDetail();
            if (zxSaFsPaySettlementList.size() > 0) {
                zxSaFsPaySettlementDetail.setZxSaFsPaySettlementId(zxSaFsPaySettlementList.get(0).getZxSaFsPaySettlementId());
                List<ZxSaFsPaySettlementDetail> payDetailList = zxSaFsPaySettlementDetailMapper.selectByZxSaFsPaySettlementDetailList(zxSaFsPaySettlementDetail);
                if (payDetailList.size() > 0) {
                    zxSaFsPaySettlementDetailService.batchDeleteUpdateZxSaFsPaySettlementDetail(payDetailList);
                }
                zxSaFsPaySettlementService.batchDeleteUpdateZxSaFsPaySettlement(zxSaFsPaySettlementList);
            }
            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = new ZxSaFsStatisticsDetail();
            zxSaFsStatisticsDetail1.setZxSaFsSettlementId(zx.getZxSaFsSettlementId());
            List<ZxSaFsStatisticsDetail> zxSaFsStatisticsDetailList = zxSaFsStatisticsDetailMapper.selectByZxSaFsStatisticsDetailList(zxSaFsStatisticsDetail1);
            if (zxSaFsStatisticsDetailList.size() > 0) {
                zxSaFsStatisticsDetailService.batchDeleteUpdateZxSaFsStatisticsDetail(zxSaFsStatisticsDetailList);
            }
        }
    }

    @Override
    public ResponseEntity getZxSaFsSettlementReport(ZxSaFsSettlement zxSaFsSettlement) {

        // ????????????
        ZxSaFsSettlement dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());

        // ????????????
        if (dbZxSaFsSettlement != null) {
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            zxSaFsInventorySettlementDetail.setZxSaFsSettlementId(dbZxSaFsSettlement.getZxSaFsSettlementId());
            zxSaFsInventorySettlementDetail.setZxCtFsContractId(dbZxSaFsSettlement.getZxCtFsContractId());
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

            List<ZxSaFsInventorySettlementDetail> DzInfo = zxSaFsInventorySettlementDetailMapper.getDzInfo(zxSaFsInventorySettlementDetail);
            List<ZxSaFsInventorySettlementDetail> JfInfo = zxSaFsInventorySettlementDetailMapper.getJfInfo(zxSaFsInventorySettlementDetail);
            List<ZxSaFsInventorySettlementDetail> QtInfo = zxSaFsInventorySettlementDetailMapper.getQtInfo(zxSaFsInventorySettlementDetail);
            List<ZxSaFsInventorySettlementDetail> YzInfo = zxSaFsInventorySettlementDetailMapper.getYzInfo(zxSaFsInventorySettlementDetail);

            heji.setEquipCode("??????");

            BigDecimal contractSumhj =new BigDecimal("0");
            BigDecimal changeContractSumhj =new BigDecimal("0");
            BigDecimal upAmthj =new BigDecimal("0");
            BigDecimal thisAmthj =new BigDecimal("0");
            for (ZxSaFsInventorySettlementDetail Dz:DzInfo
                 ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,Dz.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,Dz.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,Dz.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,Dz.getThisAmt()) ;
            }
            for (ZxSaFsInventorySettlementDetail jf:JfInfo
            ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,jf.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,jf.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,jf.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,jf.getThisAmt()) ;
            }
            for (ZxSaFsInventorySettlementDetail Qt:QtInfo
            ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,Qt.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,Qt.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,Qt.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,Qt.getThisAmt()) ;
            }
            for (ZxSaFsInventorySettlementDetail Yz:YzInfo
            ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,Yz.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,Yz.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,Yz.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,Yz.getThisAmt()) ;
            }

            heji.setContractSum(contractSumhj);
            heji.setChangeContractSum(changeContractSumhj);
            heji.setUpAmt(upAmthj);
            heji.setThisAmt(thisAmthj);

            ygzList.add(xiaoji);
            ygzList.addAll(DzInfo);
            ygzList.addAll(JfInfo);
            ygzList.addAll(QtInfo);
            ygzList.addAll(YzInfo);
            ygzList.add(heji);
            dbZxSaFsSettlement.setuReportList(ygzList);
            return repEntity.ok(dbZxSaFsSettlement);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity addZxSaFsSettlementApplyFlow(ZxSaFsSettlement zxSaFsSettlement) {


        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        System.out.println("1111");
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxSaFsSettlement dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());
        if (dbZxSaFsSettlement != null && StrUtil.isNotEmpty(dbZxSaFsSettlement.getZxSaFsSettlementId())) {
            dbZxSaFsSettlement.setApih5FlowStatus(zxSaFsSettlement.getApih5FlowStatus());
            //?????????:0;????????????:1;????????????:2;???????????????:3;????????????:4;????????????:5;????????????:6
            dbZxSaFsSettlement.setWorkId(zxSaFsSettlement.getWorkId());
            dbZxSaFsSettlement.setModifyUserInfo(userKey, realName);
            zxSaFsSettlementMapper.updateByPrimaryKey(dbZxSaFsSettlement);
            if (zxSaFsSettlement.getZxZhengWenFileList() != null && zxSaFsSettlement.getZxZhengWenFileList().size() > 0) {
                ZxErpFile file = new ZxErpFile();
                file.setOtherId(dbZxSaFsSettlement.getZxSaFsSettlementId());
                file.setOtherType("2");
                List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
                file.setModifyUserInfo(userKey, realName);
                if (fileList.size() > 0) {
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
                }
                for (ZxErpFile zxErpFile : zxSaFsSettlement.getZxZhengWenFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(dbZxSaFsSettlement.getZxSaFsSettlementId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFile.setOtherType("2");
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        return repEntity.ok("sys.data.update", zxSaFsSettlement);
    }

}
