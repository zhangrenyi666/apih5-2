package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxCtFsSideAgreementInventoryService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtFsSideAgreementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxCtFsSideAgreementService")
public class ZxCtFsSideAgreementServiceImpl implements ZxCtFsSideAgreementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsSideAgreementMapper zxCtFsSideAgreementMapper;

    @Autowired(required = true)
    private ZxCtFsSideAgreementInventoryService zxCtFsSideAgreementInventoryService;

    @Autowired(required = true)
    private ZxCtFsSideAgreementInventoryMapper zxCtFsSideAgreementInventoryMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Override
    public ResponseEntity getZxCtFsSideAgreementListByCondition(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        if (zxCtFsSideAgreement == null) {
            zxCtFsSideAgreement = new ZxCtFsSideAgreement();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtFsSideAgreement.setComID("");
            zxCtFsSideAgreement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxCtFsSideAgreement.setComID(zxCtFsSideAgreement.getOrgID());
            zxCtFsSideAgreement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxCtFsSideAgreement.setOrgID(zxCtFsSideAgreement.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxCtFsSideAgreement.getPage(), zxCtFsSideAgreement.getLimit());
        // ????????????
        List<ZxCtFsSideAgreement> zxCtFsSideAgreementList = zxCtFsSideAgreementMapper.selectByZxCtFsSideAgreementList(zxCtFsSideAgreement);

        for (ZxCtFsSideAgreement zxCtFsSideAgreement1 : zxCtFsSideAgreementList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxCtFsSideAgreement1.getZxCtFsSideAgreementId());
            file.setOtherType("1");
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsSideAgreement1.setZxErpFileList(zxErpFileList);
            file.setOtherType("2");
            List<ZxErpFile> zhengWenFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsSideAgreement1.setZxZhengWenFileList(zhengWenFileList);
        }
        // ??????????????????
        PageInfo<ZxCtFsSideAgreement> p = new PageInfo<>(zxCtFsSideAgreementList);

        return repEntity.okList(zxCtFsSideAgreementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsSideAgreementDetail(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        if (zxCtFsSideAgreement == null) {
            zxCtFsSideAgreement = new ZxCtFsSideAgreement();
        }
        ZxCtFsSideAgreement dbZxCtFsSideAgreement = new ZxCtFsSideAgreement();
        // ????????????
        if (zxCtFsSideAgreement.getZxCtFsSideAgreementId() != "") {
            dbZxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        } else if (zxCtFsSideAgreement.getWorkId() != null) {
            dbZxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryWorkId(zxCtFsSideAgreement.getWorkId());
        }

        // ????????????
        if (dbZxCtFsSideAgreement != null) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(dbZxCtFsSideAgreement.getZxCtFsSideAgreementId());
            file.setOtherType("1");
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsSideAgreement.setZxErpFileList(zxErpFileList);

            file.setOtherType("2");
            List<ZxErpFile> zhengWenFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsSideAgreement.setZxZhengWenFileList(zhengWenFileList);
            return repEntity.ok(dbZxCtFsSideAgreement);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsSideAgreement.setZxCtFsSideAgreementId(UuidUtil.generate());
        zxCtFsSideAgreement.setCreateUserInfo(userKey, realName);
        zxCtFsSideAgreement.setBeginPer(realName);
        zxCtFsSideAgreement.setApih5FlowStatus("-1");
//        zxCtFsSideAgreement.setUpAlterContractSum(zxCtFsSideAgreement.getContractCost());//??????????????????????????????
//        zxCtFsSideAgreement.setUpAlterContractSumNoTax(zxCtFsSideAgreement.getContractCostNoTax());//???????????????????????????????????????
        ZxCtFsSideAgreement zxCtFsSideAgreement2 = new ZxCtFsSideAgreement();
        zxCtFsSideAgreement2.setZxCtFsContractId(zxCtFsSideAgreement.getZxCtFsContractId());
        List<ZxCtFsSideAgreement> list = zxCtFsSideAgreementMapper.checkSideAgement(zxCtFsSideAgreement2);
        if (list.size() > 0) {
            return repEntity.layerMessage("no", "??????????????????????????????????????????");
        }

        ZxCtFsSideAgreement zxCtFsSideAgreement3 = new ZxCtFsSideAgreement();
        zxCtFsSideAgreement3.setContractNo(zxCtFsSideAgreement.getContractNo());
        List<ZxCtFsSideAgreement> list2 = zxCtFsSideAgreementMapper.checkSideAgement(zxCtFsSideAgreement2);
        if (list2.size() > 0) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
//        int sum= zxCtFsSideAgreementMapper.sumContractNo(zxCtFsSideAgreement.getContractNo())+1;
//        zxCtFsSideAgreement.setContractNo(zxCtFsSideAgreement.getContractNo()+"-"+"???"+String.format("%02d", sum));
        int flag = zxCtFsSideAgreementMapper.insert(zxCtFsSideAgreement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // ??????
            List<ZxErpFile> zxErpFileList = zxCtFsSideAgreement.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "?????????????????????");
                    }
                }
            }
            return repEntity.ok("sys.data.sava", zxCtFsSideAgreement);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsSideAgreement dbZxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        if (dbZxCtFsSideAgreement != null && StrUtil.isNotEmpty(dbZxCtFsSideAgreement.getZxCtFsSideAgreementId())) {
            // ??????????????????
            dbZxCtFsSideAgreement.setContractNo(zxCtFsSideAgreement.getContractNo());
            // ????????????
            dbZxCtFsSideAgreement.setContractName(zxCtFsSideAgreement.getContractName());
            // ????????????
            dbZxCtFsSideAgreement.setContractType(zxCtFsSideAgreement.getContractType());
            // ??????????????????(??????)
            dbZxCtFsSideAgreement.setContractCost(zxCtFsSideAgreement.getContractCost());
            // ??????ID
            dbZxCtFsSideAgreement.setFirstID(zxCtFsSideAgreement.getFirstID());
            // ????????????
            dbZxCtFsSideAgreement.setFirstName(zxCtFsSideAgreement.getFirstName());
            // ??????ID
            dbZxCtFsSideAgreement.setSecondID(zxCtFsSideAgreement.getSecondID());
            // ????????????
            dbZxCtFsSideAgreement.setSecondName(zxCtFsSideAgreement.getSecondName());
            // ????????????
            dbZxCtFsSideAgreement.setStartDate(zxCtFsSideAgreement.getStartDate());
            // ????????????
            dbZxCtFsSideAgreement.setEndDate(zxCtFsSideAgreement.getEndDate());
            // ????????????
            dbZxCtFsSideAgreement.setCsTimeLimit(zxCtFsSideAgreement.getCsTimeLimit());
            // ???????????????
            dbZxCtFsSideAgreement.setAgent(zxCtFsSideAgreement.getAgent());
            // ????????????
            dbZxCtFsSideAgreement.setContent(zxCtFsSideAgreement.getContent());
            // ?????????
            dbZxCtFsSideAgreement.setBeginPer(zxCtFsSideAgreement.getBeginPer());
            // ????????????
            dbZxCtFsSideAgreement.setStatus(zxCtFsSideAgreement.getStatus());
            // combProp
            dbZxCtFsSideAgreement.setCombProp(zxCtFsSideAgreement.getCombProp());
            // pp1
            dbZxCtFsSideAgreement.setPp1(zxCtFsSideAgreement.getPp1());
            // pp2
            dbZxCtFsSideAgreement.setPp2(zxCtFsSideAgreement.getPp2());
            // ??????????????????
            dbZxCtFsSideAgreement.setPp3(zxCtFsSideAgreement.getPp3());
            // ????????????????????????(??????)
            dbZxCtFsSideAgreement.setPp4(zxCtFsSideAgreement.getPp4());
            // pp5
            dbZxCtFsSideAgreement.setPp5(zxCtFsSideAgreement.getPp5());
            // ????????????ID
            dbZxCtFsSideAgreement.setPp6(zxCtFsSideAgreement.getPp6());
            // pp7
            dbZxCtFsSideAgreement.setPp7(zxCtFsSideAgreement.getPp7());
            // ??????
            dbZxCtFsSideAgreement.setPp8(zxCtFsSideAgreement.getPp8());
            // pp9
            dbZxCtFsSideAgreement.setPp9(zxCtFsSideAgreement.getPp9());
            // pp10
            dbZxCtFsSideAgreement.setPp10(zxCtFsSideAgreement.getPp10());
            // ????????????ID
            dbZxCtFsSideAgreement.setInstProcessId(zxCtFsSideAgreement.getInstProcessId());
            // ????????????ID
            dbZxCtFsSideAgreement.setWorkitemID(zxCtFsSideAgreement.getWorkitemID());
            // name
            dbZxCtFsSideAgreement.setCode(zxCtFsSideAgreement.getCode());
            // ?????????????????????
            dbZxCtFsSideAgreement.setAlterContractSum(zxCtFsSideAgreement.getAlterContractSum());
            // ???????????????
            dbZxCtFsSideAgreement.setIsFlag(zxCtFsSideAgreement.getIsFlag());
            // ???????????????ID
            dbZxCtFsSideAgreement.setSendToJuID(zxCtFsSideAgreement.getSendToJuID());
            // ????????????
            dbZxCtFsSideAgreement.setMaterialSource(zxCtFsSideAgreement.getMaterialSource());
            // ??????????????????????????????
            dbZxCtFsSideAgreement.setUpAlterContractSum(zxCtFsSideAgreement.getUpAlterContractSum());
            // ??????????????????
            dbZxCtFsSideAgreement.setIsFlagZhb(zxCtFsSideAgreement.getIsFlagZhb());
            // ??????????????????ID
            dbZxCtFsSideAgreement.setSendToZhbID(zxCtFsSideAgreement.getSendToZhbID());
            // ??????(%)
            dbZxCtFsSideAgreement.setTaxRate(zxCtFsSideAgreement.getTaxRate());

            // ?????????????????????(??????)
            dbZxCtFsSideAgreement.setContractCostNoTax(zxCtFsSideAgreement.getContractCostNoTax());
            // ????????????????????????
            dbZxCtFsSideAgreement.setAlterContractSumNoTax(zxCtFsSideAgreement.getAlterContractSumNoTax());
            // ???????????????
            dbZxCtFsSideAgreement.setAlterContractSumTax(zxCtFsSideAgreement.getAlterContractSumTax());
            // ???????????????????????????(??????)
            dbZxCtFsSideAgreement.setPp4NoTax(zxCtFsSideAgreement.getPp4NoTax());
            // ??????????????????(??????)
            dbZxCtFsSideAgreement.setPp4Tax(zxCtFsSideAgreement.getPp4Tax());
            // ????????????(??????)
            dbZxCtFsSideAgreement.setContractCostTax(zxCtFsSideAgreement.getContractCostTax());
            // upAlterContractSumNoTax
            dbZxCtFsSideAgreement.setUpAlterContractSumNoTax(zxCtFsSideAgreement.getUpAlterContractSumNoTax());
            // comID
            dbZxCtFsSideAgreement.setComID(zxCtFsSideAgreement.getComID());
            // upAlterContractSumTax
            dbZxCtFsSideAgreement.setUpAlterContractSumTax(zxCtFsSideAgreement.getUpAlterContractSumTax());
            // orgID
            dbZxCtFsSideAgreement.setOrgID(zxCtFsSideAgreement.getOrgID());
            // ????????????
            dbZxCtFsSideAgreement.setOrgName(zxCtFsSideAgreement.getOrgName());
            // ????????????
            dbZxCtFsSideAgreement.setAlterLevel(zxCtFsSideAgreement.getAlterLevel());
            // ????????????
            dbZxCtFsSideAgreement.setProposer(zxCtFsSideAgreement.getProposer());
            // ????????????
            dbZxCtFsSideAgreement.setAlterContent(zxCtFsSideAgreement.getAlterContent());
            // ????????????
            dbZxCtFsSideAgreement.setAlterReason(zxCtFsSideAgreement.getAlterReason());
            // ????????????
            dbZxCtFsSideAgreement.setHappenDate(zxCtFsSideAgreement.getHappenDate());
            // ??????????????????????????????(??????)
            dbZxCtFsSideAgreement.setApplyAmount(zxCtFsSideAgreement.getApplyAmount());
            // ????????????
            dbZxCtFsSideAgreement.setApplyDate(zxCtFsSideAgreement.getApplyDate());
            // ????????????
            dbZxCtFsSideAgreement.setApplyNo(zxCtFsSideAgreement.getApplyNo());
            // ??????????????????
            dbZxCtFsSideAgreement.setApplyDelay(zxCtFsSideAgreement.getApplyDelay());
            // ???????????????????????????(??????)
            dbZxCtFsSideAgreement.setReplyAmount(zxCtFsSideAgreement.getReplyAmount());
            // ????????????
            dbZxCtFsSideAgreement.setReplyDate(zxCtFsSideAgreement.getReplyDate());
            // ?????????????????????
            dbZxCtFsSideAgreement.setReplyNo(zxCtFsSideAgreement.getReplyNo());
            // ??????????????????
            dbZxCtFsSideAgreement.setReplyDelay(zxCtFsSideAgreement.getReplyDelay());
            // ????????????
            dbZxCtFsSideAgreement.setReplyStatus(zxCtFsSideAgreement.getReplyStatus());
            // ??????????????????
            dbZxCtFsSideAgreement.setCompanyHelp(zxCtFsSideAgreement.getCompanyHelp());
            // ??????????????????
            dbZxCtFsSideAgreement.setTakeEffectDate(zxCtFsSideAgreement.getTakeEffectDate());
            // ???????????????
            dbZxCtFsSideAgreement.setTakeEffectMan(zxCtFsSideAgreement.getTakeEffectMan());
            // ??????
            dbZxCtFsSideAgreement.setAuditStatus(zxCtFsSideAgreement.getAuditStatus());
            // ?????????
            dbZxCtFsSideAgreement.setRecorder(zxCtFsSideAgreement.getRecorder());
            // ????????????
            dbZxCtFsSideAgreement.setRecordDate(zxCtFsSideAgreement.getRecordDate());
            // ?????????????????????????????????(??????)
            dbZxCtFsSideAgreement.setApplyAmountNoTax(zxCtFsSideAgreement.getApplyAmountNoTax());
            // ????????????????????????(??????)
            dbZxCtFsSideAgreement.setApplyAmountTax(zxCtFsSideAgreement.getApplyAmountTax());
            // ??????????????????????????????(??????)
            dbZxCtFsSideAgreement.setReplyAmountNoTax(zxCtFsSideAgreement.getReplyAmountNoTax());
            // ?????????????????????(??????)
            dbZxCtFsSideAgreement.setReplyAmountTax(zxCtFsSideAgreement.getReplyAmountTax());
            // ?????????????????????(??????)
            dbZxCtFsSideAgreement.setPp2NoTax(zxCtFsSideAgreement.getPp2NoTax());
            // ????????????(??????)
            dbZxCtFsSideAgreement.setPp2Tax(zxCtFsSideAgreement.getPp2Tax());
            // ??????????????????
            dbZxCtFsSideAgreement.setViewType(zxCtFsSideAgreement.getViewType());
            // ???????????????ID
            dbZxCtFsSideAgreement.setCoBookId(zxCtFsSideAgreement.getCoBookId());
            // ????????????
            dbZxCtFsSideAgreement.setClaimPeriod(zxCtFsSideAgreement.getClaimPeriod());
            // ????????????
            dbZxCtFsSideAgreement.setSystemType(zxCtFsSideAgreement.getSystemType());
            // ????????????ID
            dbZxCtFsSideAgreement.setZxCtFsContractId(zxCtFsSideAgreement.getZxCtFsContractId());
            // ??????
            dbZxCtFsSideAgreement.setRemarks(zxCtFsSideAgreement.getRemarks());
            // ??????
            dbZxCtFsSideAgreement.setSort(zxCtFsSideAgreement.getSort());
            dbZxCtFsSideAgreement.setApih5FlowStatus(zxCtFsSideAgreement.getApih5FlowStatus());
            dbZxCtFsSideAgreement.setApih5FlowId(zxCtFsSideAgreement.getApih5FlowId());
            dbZxCtFsSideAgreement.setApih5FlowNodeStatus(zxCtFsSideAgreement.getApih5FlowNodeStatus());
            dbZxCtFsSideAgreement.setWorkId(zxCtFsSideAgreement.getWorkId());
            if (StrUtil.equals("opinionField1", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField1(zxCtFsSideAgreement.getOpinionContent(realName,
                        dbZxCtFsSideAgreement.getOpinionField1()));
            }
            //
            if (StrUtil.equals("opinionField2", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField2(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField2()));
            }
            //
            if (StrUtil.equals("opinionField3", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField3(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField3()));
            }
            //
            if (StrUtil.equals("opinionField4", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField4(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField4()));
            }
            //
            if (StrUtil.equals("opinionField5", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField5(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField5()));
            }
            //
            if (StrUtil.equals("opinionField6", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField6(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField6()));
            }
            //
            if (StrUtil.equals("opinionField7", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField7(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField7()));
            }
            //
            if (StrUtil.equals("opinionField8", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField8(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField8()));
            }
            //
            if (StrUtil.equals("opinionField9", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField9(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField9()));
            }
            //
            if (StrUtil.equals("opinionField10", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField10(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField10()));
            }
//            if(zxCtFsSideAgreement.getIsDeduct().equals(dbZxCtFsSideAgreement.getIsDeduct())){
//                ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory= new ZxCtFsSideAgreementInventory();
//                zxCtFsSideAgreementInventory.setZxCtFsSideAgreementId(dbZxCtFsSideAgreement.getZxCtFsSideAgreementId());
//              List<ZxCtFsSideAgreementInventory>  inventories= zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory);
//                for (ZxCtFsSideAgreementInventory inventory:inventories
//                     ) {
//                    inventory.setIsDeduct(zxCtFsSideAgreement.getIsDeduct());
//                   flag= zxCtFsSideAgreementInventoryMapper.updateByPrimaryKey(inventory);
//                }
//                if(flag==0) {
//                    return repEntity.layerMessage("no", "???????????????????????????????????????");
//                }
//            }
            // ????????????
            dbZxCtFsSideAgreement.setIsDeduct(zxCtFsSideAgreement.getIsDeduct());
            // ??????
            dbZxCtFsSideAgreement.setModifyUserInfo(userKey, realName);
            if ("2".equals(dbZxCtFsSideAgreement.getApih5FlowStatus())) {
                synReviewDetails(dbZxCtFsSideAgreement);
                ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsSideAgreement.getZxCtFsContractId());
                //zxCtFsContract.setZxCtFsContractId(zxCtFsSideAgreement.getZxCtFsContractId());
                zxCtFsContract.setAlterContractSum(zxCtFsSideAgreement.getAlterContractSum());
                zxCtFsContract.setAlterContractSumNoTax(zxCtFsSideAgreement.getAlterContractSumNoTax());
                zxCtFsContract.setAlterContractSumTax(zxCtFsSideAgreement.getAlterContractSumTax());
                flag = zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
                if (flag == 0) {
                    return repEntity.layerMessage("no", "???????????????????????????????????????");
                }
            }

            flag = zxCtFsSideAgreementMapper.updateByPrimaryKey(dbZxCtFsSideAgreement);

            //????????????????????????
            ZxErpFile delFile = new ZxErpFile();

            if (zxCtFsSideAgreement.getZxErpFileList() != null && zxCtFsSideAgreement.getZxErpFileList().size() > 0) {
                delFile.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                delFile.setOtherType("1");
                List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
                if (delFileList != null && delFileList.size() > 0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
                }
                for (ZxErpFile file : zxCtFsSideAgreement.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }

            if (zxCtFsSideAgreement.getZxZhengWenFileList() != null && zxCtFsSideAgreement.getZxZhengWenFileList().size() > 0) {
                delFile.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                delFile.setOtherType("2");
                List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
                if (delFileList != null && delFileList.size() > 0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
                }
                for (ZxErpFile file : zxCtFsSideAgreement.getZxZhengWenFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                    file.setOtherType("2");
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtFsSideAgreement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreement(List<ZxCtFsSideAgreement> zxCtFsSideAgreementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtFsSideAgreementList != null && zxCtFsSideAgreementList.size() > 0) {
            for (ZxCtFsSideAgreement zxCtFsSide : zxCtFsSideAgreementList) {
                if (StrUtil.isNotEmpty(zxCtFsSide.getWorkId())) {
                    jsonArr.add(zxCtFsSide.getWorkId());
                }
            }

            ZxCtFsSideAgreement zxCtFsSideAgreement = new ZxCtFsSideAgreement();
            zxCtFsSideAgreement.setModifyUserInfo(userKey, realName);
            flag = zxCtFsSideAgreementMapper.batchDeleteUpdateZxCtFsSideAgreement(zxCtFsSideAgreementList, zxCtFsSideAgreement);
            if (jsonArr.size() > 0) {
                HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsSideAgreementList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    @Override
    public ResponseEntity updateAgreementAndInventoryList(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        if (zxCtFsSideAgreement != null && zxCtFsSideAgreement.getInventoryList().size() > 0) {
            updateZxCtFsSideAgreement(zxCtFsSideAgreement);
            zxCtFsSideAgreementInventoryService.batchUpdateOrAdd(zxCtFsSideAgreement.getInventoryList());
            return repEntity.ok("sys.data.update", zxCtFsSideAgreement);
        } else {
            return repEntity.layerMessage("no", "?????????????????????");
        }
    }


    /**
     * ??????????????????????????????
     *
     * @param zxCtFsSideAgreement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void synReviewDetails(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory = new ZxCtFsSideAgreementInventory();
        zxCtFsSideAgreementInventory.setZxCtFsSideAgreementId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventorys = zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory);
        if (zxCtFsSideAgreementInventorys.size() > 0) {
            for (ZxCtFsSideAgreementInventory agreementInventory1 : zxCtFsSideAgreementInventorys
            ) {
                if ("1".equals(agreementInventory1.getAlterType())) {
                    ZxCtFsContractReviewDetail addReviewDetail = new ZxCtFsContractReviewDetail();
                    addReviewDetail.setConRevDetailId(UuidUtil.generate());
                    addReviewDetail.setZxCtFsContractId(agreementInventory1.getZxCtFsContractId());// ??????ID
                    addReviewDetail.setWorkNo(agreementInventory1.getWorkNo()); // ????????????
                    addReviewDetail.setWorkName(agreementInventory1.getWorkName());// ????????????
                    addReviewDetail.setSpec(agreementInventory1.getSpec()); // ??????
                    addReviewDetail.setUnit(agreementInventory1.getUnit()); // ????????????
                    addReviewDetail.setWorkType(agreementInventory1.getWorkType());// ??????ID(??????workType)
                    addReviewDetail.setTreenode(agreementInventory1.getTreenode());// ??????
                    addReviewDetail.setQty(agreementInventory1.getQty());// ????????????
                    addReviewDetail.setPrice(agreementInventory1.getChangePrice());//??????????????????
                    addReviewDetail.setPriceNoTax(agreementInventory1.getChangePrice());//?????????????????????
                    addReviewDetail.setContractSum(agreementInventory1.getContractSum());// ??????????????????
                    addReviewDetail.setViewType(agreementInventory1.getViewType());// ??????????????????
                    addReviewDetail.setPlanStartDate(agreementInventory1.getPlanStartDate()); // ??????????????????
                    addReviewDetail.setPlanEndDate(agreementInventory1.getPlanEndDate());// ??????????????????
                    addReviewDetail.setActualStartDate(agreementInventory1.getActualStartDate());// ??????????????????
                    addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// ??????????????????
                    addReviewDetail.setChangeQty(agreementInventory1.getChangeQty());// ???????????????
                    addReviewDetail.setChangePrice(agreementInventory1.getChangePrice()); // ?????????????????????
                    addReviewDetail.setChangeContractSum(agreementInventory1.getChangeContractSum());// ?????????????????????
                    addReviewDetail.setRentUnit(agreementInventory1.getRentUnit());// ????????????
                    addReviewDetail.setAlterRentStartDate(agreementInventory1.getActualStartDate()); // ???????????????????????????
                    addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// ???????????????????????????
                    addReviewDetail.setPriceNoTax(agreementInventory1.getPriceNoTax());// ?????????????????????
                    addReviewDetail.setContractSumNoTax(agreementInventory1.getContractSumNoTax());// ?????????????????????
                    addReviewDetail.setChangePriceNoTax(agreementInventory1.getChangePriceNoTax());// ????????????????????????
                    addReviewDetail.setChangeContractSumNoTax(agreementInventory1.getChangeContractSumNoTax());// ????????????????????????
                    addReviewDetail.setTaxRate(agreementInventory1.getTaxRate());// ??????(%)
                    addReviewDetail.setDelFlag("0");
                    addReviewDetail.setRemarks(agreementInventory1.getRemarks());
                    zxCtFsContractReviewDetailMapper.insert(addReviewDetail);
                } else if ("2".equals(agreementInventory1.getAlterType())) {
                    ZxCtFsContractReviewDetail addReviewDetail = new ZxCtFsContractReviewDetail();
                    addReviewDetail = zxCtFsContractReviewDetailMapper.selectByPrimaryKey(agreementInventory1.getConRevDetailId());
                    if (addReviewDetail != null) {
                        addReviewDetail.setConRevDetailId(agreementInventory1.getConRevDetailId());
                        //addReviewDetail.setZxCtFsContractId(agreementInventory1.getZxCtFsContractId());// ??????ID
                        addReviewDetail.setWorkNo(agreementInventory1.getWorkNo()); // ????????????
                        addReviewDetail.setWorkName(agreementInventory1.getWorkName());// ????????????
                        addReviewDetail.setSpec(agreementInventory1.getSpec()); // ??????
                        addReviewDetail.setUnit(agreementInventory1.getUnit()); // ????????????
                        addReviewDetail.setWorkType(agreementInventory1.getWorkType());// ??????ID(??????workType)
                        addReviewDetail.setTreenode(agreementInventory1.getTreenode());// ??????
                        addReviewDetail.setQty(agreementInventory1.getQty());// ????????????
                        addReviewDetail.setPrice(agreementInventory1.getPrice());//??????????????????
                        addReviewDetail.setContractSum(agreementInventory1.getContractSum());// ??????????????????
                        addReviewDetail.setViewType(agreementInventory1.getViewType());// ??????????????????
                        addReviewDetail.setPlanStartDate(agreementInventory1.getPlanStartDate()); // ??????????????????
                        addReviewDetail.setPlanEndDate(agreementInventory1.getPlanEndDate());// ??????????????????
                        addReviewDetail.setActualStartDate(agreementInventory1.getActualStartDate());// ??????????????????
                        addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// ??????????????????
                        addReviewDetail.setChangeQty(agreementInventory1.getChangeQty());// ???????????????
                        addReviewDetail.setChangePrice(agreementInventory1.getChangePrice()); // ?????????????????????
                        addReviewDetail.setChangeContractSum(agreementInventory1.getChangeContractSum());// ?????????????????????
                        addReviewDetail.setRentUnit(agreementInventory1.getRentUnit());// ????????????
                        addReviewDetail.setAlterRentStartDate(agreementInventory1.getActualStartDate()); // ???????????????????????????
                        addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// ???????????????????????????
                        addReviewDetail.setPriceNoTax(agreementInventory1.getPriceNoTax());// ?????????????????????
                        addReviewDetail.setContractSumNoTax(agreementInventory1.getContractSumNoTax());// ?????????????????????
                        addReviewDetail.setChangePriceNoTax(agreementInventory1.getChangePriceNoTax());// ????????????????????????
                        addReviewDetail.setChangeContractSumNoTax(agreementInventory1.getChangeContractSumNoTax());// ????????????????????????
                        addReviewDetail.setTaxRate(agreementInventory1.getTaxRate());// ??????(%)
                        addReviewDetail.setDelFlag("0");
                        addReviewDetail.setRemarks(agreementInventory1.getRemarks());
                        zxCtFsContractReviewDetailMapper.updateByPrimaryKey(addReviewDetail);
                    }
                }
            }

        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param response
     * @author suncg
     */

    @Override
    public ResponseEntity exportSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxCtFsSideAgreement == null) {
            zxCtFsSideAgreement = new ZxCtFsSideAgreement();
        }

        // ????????????
        List<ZxCtFsSideAgreement> zxCtFsSideAgreementList = zxCtFsSideAgreementMapper.selectByZxCtFsSideAgreementList(zxCtFsSideAgreement);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("??????????????????", "??????????????????", "????????????", "????????????", "????????????", "????????????",
                "???????????????", "??????????????????????????????", "????????????????????????????????????", "?????????????????????????????????", "????????????", "????????????", "????????????", "?????????", "????????????");
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxCtFsSideAgreementList != null && zxCtFsSideAgreementList.size() > 0) {
            for (ZxCtFsSideAgreement zxCtFsSideAgreement1 : zxCtFsSideAgreementList) {
                rowsList.add(CollUtil.newArrayList(zxCtFsSideAgreement1.getContractNo(),
                        zxCtFsSideAgreement1.getContractNo(),
                        zxCtFsSideAgreement1.getPp3(),
                        zxCtFsSideAgreement1.getContractName(),
                        zxCtFsSideAgreement1.getContractType(),
                        zxCtFsSideAgreement1.getFirstName(),
                        zxCtFsSideAgreement1.getSecondName(),
                        zxCtFsSideAgreement1.getAgent(),
                        zxCtFsSideAgreement1.getContractCost(),
                        zxCtFsSideAgreement1.getPp4(),
                        zxCtFsSideAgreement1.getAlterContractSum(),
                        zxCtFsSideAgreement1.getIsDeduct(),
                        zxCtFsSideAgreement1.getStartDate(),
                        zxCtFsSideAgreement1.getEndDate(),
                        zxCtFsSideAgreement1.getBeginPer(),
                        zxCtFsSideAgreement1.getStatus()
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

    @Override
    public ResponseEntity getContractNo(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        if (zxCtFsSideAgreement == null) {
            repEntity.layerMessage("no", "????????????????????????");
        } else if (zxCtFsSideAgreement.getContractNo().equals("")) {
            repEntity.layerMessage("no", "????????????????????????");
        }

        int sum = zxCtFsSideAgreementMapper.sumContractNo(zxCtFsSideAgreement.getContractNo()) + 1;
        zxCtFsSideAgreement.setContractNo(zxCtFsSideAgreement.getContractNo() + "-" + "???" + String.format("%02d", sum));
        //ZxCtFsContractReview dbZxCtFsContractReview = new ZxCtFsContractReview();
        return repEntity.ok(zxCtFsSideAgreement);

    }
}
