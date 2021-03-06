package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
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
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtOtherSupplyAgreementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxCtOtherSupplyAgreementService")
public class ZxCtOtherSupplyAgreementServiceImpl implements ZxCtOtherSupplyAgreementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementMapper zxCtOtherSupplyAgreementMapper;

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementWorksMapper zxCtOtherSupplyAgreementWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;
    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementListByCondition(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        if (zxCtOtherSupplyAgreement == null) {
            zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtOtherSupplyAgreement.setCompanyId("");
            zxCtOtherSupplyAgreement.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxCtOtherSupplyAgreement.setCompanyId(zxCtOtherSupplyAgreement.getOrgId());
            zxCtOtherSupplyAgreement.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxCtOtherSupplyAgreement.setOrgId(zxCtOtherSupplyAgreement.getOrgId());
        }
        // ????????????
        PageHelper.startPage(zxCtOtherSupplyAgreement.getPage(),zxCtOtherSupplyAgreement.getLimit());
        // ????????????
        List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(zxCtOtherSupplyAgreement);
        //????????????
        for (ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 : zxCtOtherSupplyAgreementList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCtOtherSupplyAgreement1.getZxCtOtherSupplyAgreementId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCtOtherSupplyAgreement1.setZxErpFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxCtOtherSupplyAgreement> p = new PageInfo<>(zxCtOtherSupplyAgreementList);

        return repEntity.okList(zxCtOtherSupplyAgreementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementDetail(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        if (zxCtOtherSupplyAgreement == null) {
            zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        }
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        if(StrUtil.isNotEmpty(zxCtOtherSupplyAgreement.getWorkId())) {
            ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 = new ZxCtOtherSupplyAgreement();
            zxCtOtherSupplyAgreement1.setWorkId(zxCtOtherSupplyAgreement.getWorkId());
            List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(zxCtOtherSupplyAgreement1);
            if(zxCtOtherSupplyAgreementList != null && zxCtOtherSupplyAgreementList.size() >0) {
                dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementList.get(0);
            }
        }else {
            // ????????????
            dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        }
        // ????????????
        if (dbZxCtOtherSupplyAgreement != null) {
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            List<ZxErpFile> zw = new ArrayList<>();
            List<ZxErpFile> fj = new ArrayList<>();
            zxErpFiles.forEach(f -> {
                if ("1".equals(f.getOtherType())) {
                    zw.add(f);
                } else if ("0".equals(f.getOtherType())) {
                    fj.add(f);
                }
            });
            dbZxCtOtherSupplyAgreement.setZxErpFileList(fj);
            dbZxCtOtherSupplyAgreement.setZxZhengWenFileList(zw);
            return repEntity.ok(dbZxCtOtherSupplyAgreement);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // ??????????????????id????????????
        if (StrUtil.isEmpty(zxCtOtherSupplyAgreement.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "??????????????????ID???????????????");
        }
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        dbZxCtOtherSupplyAgreement.setZxCtOtherManageId(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
        List<ZxCtOtherSupplyAgreement> dbZxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(dbZxCtOtherSupplyAgreement);
        if (dbZxCtOtherSupplyAgreementList != null && dbZxCtOtherSupplyAgreementList.size() > 0) {
            for (ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 : dbZxCtOtherSupplyAgreementList) {
                // ???????????????????????????????????????????????????????????????????????????????????????
                if(!"2".equals(zxCtOtherSupplyAgreement1.getApih5FlowStatus())){
                    return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????????????????");
                }
            }
        }
        // ??????????????????
        ZxCtOtherManage zxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
        if (zxCtOtherManage == null) {
            return repEntity.layerMessage("no", "????????????????????????");
        }

        // ?????????????????????????????? ???????????????????????????????????????
        zxCtOtherSupplyAgreement.setUpAlterContractSum(zxCtOtherManage.getAlterContractSum() == null ? new BigDecimal(0) : zxCtOtherManage.getAlterContractSum());
        // ????????????????????????????????? ????????????????????????????????????????????????
        zxCtOtherSupplyAgreement.setUpAlterContractSumNoTax(zxCtOtherManage.getAlterContractSumNoTax() == null ? new BigDecimal(0) : zxCtOtherManage.getAlterContractSumNoTax());
        // ???????????????????????? ?????????????????????????????????
        zxCtOtherSupplyAgreement.setUpAlterContractSumTax(zxCtOtherManage.getAlterContractSumTax() == null ? new BigDecimal(0) : zxCtOtherManage.getAlterContractSumTax());
        // ?????????????????? ??????????????????????????????
        zxCtOtherSupplyAgreement.setAlterContractSum(zxCtOtherSupplyAgreement.getUpAlterContractSum());
        zxCtOtherSupplyAgreement.setAlterContractSumNoTax(zxCtOtherSupplyAgreement.getUpAlterContractSumNoTax());
        zxCtOtherSupplyAgreement.setAlterContractSumTax(zxCtOtherSupplyAgreement.getUpAlterContractSumTax());
        zxCtOtherSupplyAgreement.setZxCtOtherManageId(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
        // ??????????????????????????????
        zxCtOtherSupplyAgreement.setApih5FlowStatus("-1");
        // ????????????????????????????????????
        zxCtOtherSupplyAgreement.setBeginPer(realName);
        zxCtOtherSupplyAgreement.setZxCtOtherSupplyAgreementId(UuidUtil.generate());
        zxCtOtherSupplyAgreement.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherSupplyAgreementMapper.insert(zxCtOtherSupplyAgreement);

        //????????????
        List<ZxErpFile> fileList = zxCtOtherSupplyAgreement.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherSupplyAgreement);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        if (dbZxCtOtherSupplyAgreement != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId())) {
            // ????????????
            dbZxCtOtherSupplyAgreement.setIsDeduct(zxCtOtherSupplyAgreement.getIsDeduct());
            // ??????????????????
            dbZxCtOtherSupplyAgreement.setSupplyAgreementName(zxCtOtherSupplyAgreement.getSupplyAgreementName());
            dbZxCtOtherSupplyAgreement.setProposer(zxCtOtherSupplyAgreement.getProposer());
            // ???????????????
            dbZxCtOtherSupplyAgreement.setAgent(zxCtOtherSupplyAgreement.getAgent());
            // ??????????????????(??????)
            dbZxCtOtherSupplyAgreement.setContractCost(zxCtOtherSupplyAgreement.getContractCost());
            // ?????????????????????(??????)
            dbZxCtOtherSupplyAgreement.setContractCostNoTax(zxCtOtherSupplyAgreement.getContractCostNoTax());
            // ????????????(??????)
            dbZxCtOtherSupplyAgreement.setContractCostTax(zxCtOtherSupplyAgreement.getContractCostTax());
            // ?????????????????????
            dbZxCtOtherSupplyAgreement.setAlterContractSum(zxCtOtherSupplyAgreement.getAlterContractSum());
            // ????????????????????????
            dbZxCtOtherSupplyAgreement.setAlterContractSumNoTax(zxCtOtherSupplyAgreement.getAlterContractSumNoTax());
            // ???????????????
            dbZxCtOtherSupplyAgreement.setAlterContractSumTax(zxCtOtherSupplyAgreement.getAlterContractSumTax());
            // ????????????
            dbZxCtOtherSupplyAgreement.setStartDate(zxCtOtherSupplyAgreement.getStartDate());
            // ????????????
            dbZxCtOtherSupplyAgreement.setEndDate(zxCtOtherSupplyAgreement.getEndDate());
            // ????????????
            dbZxCtOtherSupplyAgreement.setCsTimeLimit(zxCtOtherSupplyAgreement.getCsTimeLimit());
            // ????????????
            dbZxCtOtherSupplyAgreement.setContent(zxCtOtherSupplyAgreement.getContent());
            // ??????
            dbZxCtOtherSupplyAgreement.setRemark(zxCtOtherSupplyAgreement.getRemark());
            // ????????????
            dbZxCtOtherSupplyAgreement.setReplyUnit(zxCtOtherSupplyAgreement.getReplyUnit());
            // ????????????
            dbZxCtOtherSupplyAgreement.setReplyDate(zxCtOtherSupplyAgreement.getReplyDate());
            // ????????????
            dbZxCtOtherSupplyAgreement.setAlterContent(zxCtOtherSupplyAgreement.getAlterContent());
            // ????????????
            dbZxCtOtherSupplyAgreement.setAlterReason(zxCtOtherSupplyAgreement.getAlterReason());
            // workId
            dbZxCtOtherSupplyAgreement.setWorkId(zxCtOtherSupplyAgreement.getWorkId());
            // ??????id
            dbZxCtOtherSupplyAgreement.setApih5FlowId(zxCtOtherSupplyAgreement.getApih5FlowId());
            // ????????????
            dbZxCtOtherSupplyAgreement.setApih5FlowStatus(zxCtOtherSupplyAgreement.getApih5FlowStatus());
            // ??????????????????
            dbZxCtOtherSupplyAgreement.setApih5FlowNodeStatus(zxCtOtherSupplyAgreement.getApih5FlowNodeStatus());
            //???????????????
            if(StrUtil.equals("opinionField1", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField1(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField1(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField2(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField2(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField3(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField3(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField4(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField4(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField5(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField5(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField6(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField6(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField7(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField7(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField8(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField8(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField9(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField9(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField10(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField10(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }

           // ??????
           dbZxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherSupplyAgreementMapper.updateByPrimaryKey(dbZxCtOtherSupplyAgreement);

           // ????????????????????????????????????????????????
            ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
            dbZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxCtOtherSupplyAgreementWorks> dbZxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbZxCtOtherSupplyAgreementWorks);
            if (dbZxCtOtherSupplyAgreementWorksList != null && dbZxCtOtherSupplyAgreementWorksList.size() > 0) {
                for(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks : dbZxCtOtherSupplyAgreementWorksList) {
                    zxCtOtherSupplyAgreementWorks.setIsDeduct(dbZxCtOtherSupplyAgreement.getIsDeduct());
                    zxCtOtherSupplyAgreementWorksMapper.updateByPrimaryKey(zxCtOtherSupplyAgreementWorks);
                }
            }

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //??????list 0?????? 1??????
            List<ZxErpFile> fileList = zxCtOtherSupplyAgreement.getZxErpFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                    zxErpFile.setOtherType("0");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

            List<ZxErpFile> zhengWen = zxCtOtherSupplyAgreement.getZxZhengWenFileList();
            if (!CollectionUtils.isEmpty(zhengWen)) {
                for(ZxErpFile zxErpFile : zhengWen) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

            // ????????????
            if(StrUtil.equals(zxCtOtherSupplyAgreement.getApih5FlowStatus(), "2")) {
                this.zxCtOtherSupplyAgreementReviewApply(zxCtOtherSupplyAgreement);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherSupplyAgreement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreement(List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxCtOtherSupplyAgreementList != null && zxCtOtherSupplyAgreementList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement : zxCtOtherSupplyAgreementList) {
                //????????????
                ZxCtOtherSupplyAgreementWorks delZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
                delZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                List<ZxCtOtherSupplyAgreementWorks> delWorkList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(delZxCtOtherSupplyAgreementWorks);
                if(delWorkList != null && delWorkList.size() > 0) {
                    delZxCtOtherSupplyAgreementWorks.setModifyUserInfo(userKey, realName);
                    zxCtOtherSupplyAgreementWorksMapper.batchDeleteUpdateZxCtOtherSupplyAgreementWorks(delWorkList, delZxCtOtherSupplyAgreementWorks);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                if(StrUtil.isNotEmpty(zxCtOtherSupplyAgreement.getWorkId())) {
                    jsonArray.add(zxCtOtherSupplyAgreement.getWorkId());
                }
            }
           ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
           zxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherSupplyAgreementMapper.batchDeleteUpdateZxCtOtherSupplyAgreement(zxCtOtherSupplyAgreementList, zxCtOtherSupplyAgreement);

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
            return repEntity.ok("sys.data.delete",zxCtOtherSupplyAgreementList);
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
    public ResponseEntity zxCtOtherSupplyAgreementReviewApply(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        if (dbZxCtOtherSupplyAgreement != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId())) {
            // ?????????????????????????????????????????????????????????
            dbZxCtOtherSupplyAgreement.setApih5FlowStatus("2");
            dbZxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherSupplyAgreementMapper.updateByPrimaryKey(dbZxCtOtherSupplyAgreement);

            //??????????????????????????????????????????????????????
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(dbZxCtOtherSupplyAgreement.getZxCtOtherManageId());
            // ???????????????????????????(??????)
            dbZxCtOtherManage.setAlterContractSum(dbZxCtOtherSupplyAgreement.getAlterContractSum());
            // ??????????????????????????????(??????)
            dbZxCtOtherManage.setAlterContractSumNoTax(dbZxCtOtherSupplyAgreement.getAlterContractSumNoTax());
            // ?????????????????????(??????)
            dbZxCtOtherManage.setAlterContractSumTax(dbZxCtOtherSupplyAgreement.getAlterContractSumTax());
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
            // ??????????????????????????????????????????????????????????????????
            ZxCtOtherSupplyAgreementWorks dbWorks = new ZxCtOtherSupplyAgreementWorks();
            dbWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxCtOtherSupplyAgreementWorks> worksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbWorks);
            for (ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks1 : worksList) {
                if ("2".equals(zxCtOtherSupplyAgreementWorks1.getAlterType())) {
                    ZxCtOtherWorks dbZxCtOtherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherSupplyAgreementWorks1.getZxCtOtherWorksId());
                    // ???????????????
                    dbZxCtOtherWorks.setChangeQty(zxCtOtherSupplyAgreementWorks1.getChangeQty());
                    // ?????????????????????
                    dbZxCtOtherWorks.setChangeContractSum(zxCtOtherSupplyAgreementWorks1.getChangeContractSum());
                    // ????????????????????????
                    dbZxCtOtherWorks.setChangeContractSumNoTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumNoTax());
                    // ???????????????
                    dbZxCtOtherWorks.setChangeContractSumTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumTax());
                    // ???????????????
                    if (dbZxCtOtherWorks.getChangePrice() == null) {
                        dbZxCtOtherWorks.setChangePrice(zxCtOtherSupplyAgreementWorks1.getChangePrice());
                    }
                    // ????????????????????????
                    if (dbZxCtOtherWorks.getChangePriceNoTax() == null) {
                        dbZxCtOtherWorks.setChangePriceNoTax(zxCtOtherSupplyAgreementWorks1.getChangePriceNoTax());
                    }
                    dbZxCtOtherWorks.setModifyUserInfo(userKey, realName);
                    zxCtOtherWorksMapper.updateByPrimaryKey(dbZxCtOtherWorks);
                } else {
                    ZxCtOtherWorks zxCtOtherWorks = new ZxCtOtherWorks();
                    // ????????????
                    zxCtOtherWorks.setWorkNo(zxCtOtherSupplyAgreementWorks1.getWorkNo());
                    // ????????????
                    zxCtOtherWorks.setWorkName(zxCtOtherSupplyAgreementWorks1.getWorkName());
                    // ??????
                    zxCtOtherWorks.setUnit(zxCtOtherSupplyAgreementWorks1.getUnit());
                    // ???????????????
                    zxCtOtherWorks.setChangeQty(zxCtOtherSupplyAgreementWorks1.getChangeQty());
                    // ???????????????
                    zxCtOtherWorks.setChangePrice(zxCtOtherSupplyAgreementWorks1.getChangePrice());
                    // ????????????????????????
                    zxCtOtherWorks.setChangePriceNoTax(zxCtOtherSupplyAgreementWorks1.getChangePriceNoTax());
                    // ?????????????????????
                    zxCtOtherWorks.setChangeContractSum(zxCtOtherSupplyAgreementWorks1.getChangeContractSum());
                    // ????????????????????????
                    zxCtOtherWorks.setChangeContractSumNoTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumNoTax());
                    // ???????????????
                    zxCtOtherWorks.setChangeContractSumTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumTax());
                    // ??????
                    zxCtOtherWorks.setTaxRate(zxCtOtherSupplyAgreementWorks1.getTaxRate());
                    // ????????????
                    zxCtOtherWorks.setIsDeduct(zxCtOtherSupplyAgreementWorks1.getIsDeduct());
                    // ??????
                    zxCtOtherWorks.setRemark(zxCtOtherSupplyAgreementWorks1.getRemark());
                    // ????????????id
                    zxCtOtherWorks.setZxCtOtherManageId(dbZxCtOtherManage.getZxCtOtherManageId());
                    zxCtOtherWorks.setZxCtOtherWorksId(UuidUtil.generate());
                    zxCtOtherWorks.setCreateUserInfo(userKey, realName);
                    zxCtOtherWorksMapper.insert(zxCtOtherWorks);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",dbZxCtOtherSupplyAgreement);
        }
    }

    @Override
    public ResponseEntity zxCtOtherSupplyAgreementReviewApplyCheck(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        if (dbZxCtOtherSupplyAgreement != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId())) {
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
            ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
            dbZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxCtOtherSupplyAgreementWorks> dbZxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbZxCtOtherSupplyAgreementWorks);
            if (dbZxCtOtherSupplyAgreementWorksList == null || dbZxCtOtherSupplyAgreementWorksList.size() == 0) {
                return repEntity.layerMessage("no", "????????????????????????????????????");
            }
        }
        return null;
    }

    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementContractNo(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        if (StrUtil.isEmpty(zxCtOtherSupplyAgreement.getContractNo())) {
            return repEntity.layerMessage("no", "??????????????????????????????????????????");
        }
        // ????????????
        String contractNo = zxCtOtherSupplyAgreement.getContractNo();
        // ??????????????????????????????
        List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByContractNo(contractNo);
        String initSerialNumber = String.format("%02d", zxCtOtherSupplyAgreementList.size()+1);

        ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 = new ZxCtOtherSupplyAgreement();
        zxCtOtherSupplyAgreement1.setContractNo(contractNo+"-???"+initSerialNumber);
        return repEntity.ok(zxCtOtherSupplyAgreement1);
    }

    @Override
    public void exportZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement, HttpServletResponse response) {
        if (zxCtOtherSupplyAgreement == null) {
            zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        }
        // ????????????
        List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(zxCtOtherSupplyAgreement);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("??????????????????",
                "??????????????????",
                "???????????????",
                "??????????????????????????????",
                "??????????????????????????????????????????",
                "???????????????????????????????????????",
                "????????????",
                "????????????",
                "?????????",
                "????????????"
        );
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxCtOtherSupplyAgreementList != null && zxCtOtherSupplyAgreementList.size() > 0) {
            for (ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement : zxCtOtherSupplyAgreementList) {
                rowsList.add(CollUtil.newArrayList(dbZxCtOtherSupplyAgreement.getContractNo(),
                        dbZxCtOtherSupplyAgreement.getSupplyAgreementName(),
                        dbZxCtOtherSupplyAgreement.getAgent(),
                        dbZxCtOtherSupplyAgreement.getContractCost(),
                        dbZxCtOtherSupplyAgreement.getApplyAmount(),
                        dbZxCtOtherSupplyAgreement.getAlterContractSum(),
                        dbZxCtOtherSupplyAgreement.getStartDate(),
                        dbZxCtOtherSupplyAgreement.getEndDate(),
                        dbZxCtOtherSupplyAgreement.getBeginPer(),
                        dbZxCtOtherSupplyAgreement.getApih5FlowStatus()
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
                        "attachment; filename=\"" + new String("??????????????????????????????".getBytes("utf-8"), "iso-8859-1") + "\"");
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

}
