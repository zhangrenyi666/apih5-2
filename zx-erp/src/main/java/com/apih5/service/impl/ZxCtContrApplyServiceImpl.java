package com.apih5.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxCtContrApplyWorksMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrApplyMapper;
import com.apih5.service.ZxCtContrApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtContrApplyService")
public class ZxCtContrApplyServiceImpl implements ZxCtContrApplyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrApplyMapper zxCtContrApplyMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContrApplyWorksMapper zxCtContrApplyWorksMapper;

    @Override
    public ResponseEntity getZxCtContrApplyListByCondition(ZxCtContrApply zxCtContrApply) {
        if (zxCtContrApply == null) {
            zxCtContrApply = new ZxCtContrApply();
        }
        // ????????????
        PageHelper.startPage(zxCtContrApply.getPage(), zxCtContrApply.getLimit());
        // ????????????
        List<ZxCtContrApply> zxCtContrApplyList = zxCtContrApplyMapper.selectByZxCtContrApplyList(zxCtContrApply);

        //????????????
        for (ZxCtContrApply zxCtContrApply1 : zxCtContrApplyList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCtContrApply1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCtContrApply1.setFileList(zxErpFiles);
        }

        // ??????????????????
        PageInfo<ZxCtContrApply> p = new PageInfo<>(zxCtContrApplyList);

        return repEntity.okList(zxCtContrApplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrApplyDetail(ZxCtContrApply zxCtContrApply) {
        if (zxCtContrApply == null) {
            zxCtContrApply = new ZxCtContrApply();
        }
        // ????????????
        ZxCtContrApply dbZxCtContrApply = zxCtContrApplyMapper.selectByPrimaryKey(zxCtContrApply.getId());
        // ????????????
        if (dbZxCtContrApply != null) {
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtContrApply.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxCtContrApply.setFileList(zxErpFiles);
            return repEntity.ok(dbZxCtContrApply);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrApply(ZxCtContrApply zxCtContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrApply.setId(UuidUtil.generate());
        zxCtContrApply.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrApplyMapper.insert(zxCtContrApply);

        //????????????
        List<ZxErpFile> fileList = zxCtContrApply.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCtContrApply.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrApply);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrApply(ZxCtContrApply zxCtContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrApply dbZxCtContrApply = zxCtContrApplyMapper.selectByPrimaryKey(zxCtContrApply.getId());
        if (dbZxCtContrApply != null && StrUtil.isNotEmpty(dbZxCtContrApply.getId())) {
            // ????????????
            dbZxCtContrApply.setContractNo(zxCtContrApply.getContractNo());
            // ????????????
            dbZxCtContrApply.setContractName(zxCtContrApply.getContractName());
            // ????????????
            dbZxCtContrApply.setContractType(zxCtContrApply.getContractType());
            // ??????????????????(??????)
            dbZxCtContrApply.setContractCost(zxCtContrApply.getContractCost());
            // ??????ID
            dbZxCtContrApply.setFirstId(zxCtContrApply.getFirstId());
            // ????????????
            dbZxCtContrApply.setFirstName(zxCtContrApply.getFirstName());
            // ??????ID
            dbZxCtContrApply.setSecondId(zxCtContrApply.getSecondId());
            // ????????????
            dbZxCtContrApply.setSecondName(zxCtContrApply.getSecondName());
            // ????????????
            dbZxCtContrApply.setStartDate(zxCtContrApply.getStartDate());
            // ????????????
            dbZxCtContrApply.setEndDate(zxCtContrApply.getEndDate());
            // ????????????
            dbZxCtContrApply.setCsTimeLimit(zxCtContrApply.getCsTimeLimit());
            // ???????????????
            dbZxCtContrApply.setAgent(zxCtContrApply.getAgent());
            // ????????????
            dbZxCtContrApply.setContent(zxCtContrApply.getContent());
            // ??????
            dbZxCtContrApply.setRemark(zxCtContrApply.getRemark());
            // ?????????
            dbZxCtContrApply.setBeginPer(zxCtContrApply.getBeginPer());
            // ????????????
            dbZxCtContrApply.setStatus(zxCtContrApply.getStatus());
            // ????????????
            dbZxCtContrApply.setSupplyAgreement(zxCtContrApply.getSupplyAgreement());
            // pp4
            dbZxCtContrApply.setPp4(zxCtContrApply.getPp4());
            // pp5
            dbZxCtContrApply.setPp5(zxCtContrApply.getPp5());
            // ????????????Id
            dbZxCtContrApply.setContractNameId(zxCtContrApply.getContractNameId());
            // pp10
            dbZxCtContrApply.setPp10(zxCtContrApply.getPp10());
            // ????????????ID
            dbZxCtContrApply.setInstProcessId(zxCtContrApply.getInstProcessId());
            // ????????????ID
            dbZxCtContrApply.setWorkitemId(zxCtContrApply.getWorkitemId());
            // ????????????
            dbZxCtContrApply.setContractCode(zxCtContrApply.getContractCode());
            // ????????????
            dbZxCtContrApply.setOrganCode(zxCtContrApply.getOrganCode());
            // ??????????????????
            dbZxCtContrApply.setContractorAbbreviation(zxCtContrApply.getContractorAbbreviation());
            // ??????????????????
            dbZxCtContrApply.setBidWinnerAbbreviation(zxCtContrApply.getBidWinnerAbbreviation());
            // ????????????????????????
            dbZxCtContrApply.setProjectProvinceAbbreviation(zxCtContrApply.getProjectProvinceAbbreviation());
            // ????????????
            dbZxCtContrApply.setProjectAbbreviation(zxCtContrApply.getProjectAbbreviation());
            // ?????????
            dbZxCtContrApply.setLotNo(zxCtContrApply.getLotNo());
            // ????????????
            dbZxCtContrApply.setContractCategory(zxCtContrApply.getContractCategory());
            // ????????????
            dbZxCtContrApply.setContractNumber(zxCtContrApply.getContractNumber());
            // ??????????????????
            dbZxCtContrApply.setOwnerContractCode(zxCtContrApply.getOwnerContractCode());
            // ????????????
            dbZxCtContrApply.setOneFirm(zxCtContrApply.getOneFirm());
            // ??????????????????
            dbZxCtContrApply.setFlowBeginDate(zxCtContrApply.getFlowBeginDate());
            // ??????????????????
            dbZxCtContrApply.setFlowEndDate(zxCtContrApply.getFlowEndDate());
            // alterContractSum
            dbZxCtContrApply.setAlterContractSum(zxCtContrApply.getAlterContractSum());
            // ???????????????
            dbZxCtContrApply.setIsFlag(zxCtContrApply.getIsFlag());
            // ????????????
            dbZxCtContrApply.setIsReport(zxCtContrApply.getIsReport());
            // appInsHistId
            dbZxCtContrApply.setAppInsHistId(zxCtContrApply.getAppInsHistId());
            // ???????????????ID
            dbZxCtContrApply.setSendToJuId(zxCtContrApply.getSendToJuId());
            // materialSource
            dbZxCtContrApply.setMaterialSource(zxCtContrApply.getMaterialSource());
            // ????????????????????????
            dbZxCtContrApply.setUpAlterContractSum(zxCtContrApply.getUpAlterContractSum());
            // ???????????????
            dbZxCtContrApply.setLegalPerson(zxCtContrApply.getLegalPerson());
            // ???????????????
            dbZxCtContrApply.setAgentPerson(zxCtContrApply.getAgentPerson());
            // ???????????????
            dbZxCtContrApply.setChargePerson(zxCtContrApply.getChargePerson());
            // ??????????????????
            dbZxCtContrApply.setIsFlagZhb(zxCtContrApply.getIsFlagZhb());
            // isReportZhb
            dbZxCtContrApply.setIsReportZhb(zxCtContrApply.getIsReportZhb());
            // ??????????????????ID
            dbZxCtContrApply.setSendToZhbId(zxCtContrApply.getSendToZhbId());
            // appInsHistIDZhb
            dbZxCtContrApply.setAppInsHistIDZhb(zxCtContrApply.getAppInsHistIDZhb());
            // ?????????????????????(??????)
            dbZxCtContrApply.setContractCostNoTax(zxCtContrApply.getContractCostNoTax());
            // ??????(%)
            dbZxCtContrApply.setTaxRate(zxCtContrApply.getTaxRate());
            // ?????????????????????????????????
            dbZxCtContrApply.setAlterContractSumNoTax(zxCtContrApply.getAlterContractSumNoTax());
            // ????????????????????????
            dbZxCtContrApply.setAlterContractSumTax(zxCtContrApply.getAlterContractSumTax());
            // pp4NoTax
            dbZxCtContrApply.setPp4NoTax(zxCtContrApply.getPp4NoTax());
            // pp4Tax
            dbZxCtContrApply.setPp4Tax(zxCtContrApply.getPp4Tax());
            // ????????????
            dbZxCtContrApply.setIsDeduct(zxCtContrApply.getIsDeduct());
            // fromContractID
            dbZxCtContrApply.setFromContractId(zxCtContrApply.getFromContractId());
            // fromContractNo
            dbZxCtContrApply.setFromContractNo(zxCtContrApply.getFromContractNo());
            // fromContractName
            dbZxCtContrApply.setFromContractName(zxCtContrApply.getFromContractName());
            // ????????????(??????)
            dbZxCtContrApply.setContractCostTax(zxCtContrApply.getContractCostTax());
            // ?????????????????????
            dbZxCtContrApply.setWtrName(zxCtContrApply.getWtrName());
            // ???????????????????????????
            dbZxCtContrApply.setWtrPhone(zxCtContrApply.getWtrPhone());
            // ???????????????
            dbZxCtContrApply.setReferenceName(zxCtContrApply.getReferenceName());
            // ???????????????
            dbZxCtContrApply.setReferencePost(zxCtContrApply.getReferencePost());
            // ???????????????
            dbZxCtContrApply.setReferencePhone(zxCtContrApply.getReferencePhone());
            // ?????????????????????????????????
            dbZxCtContrApply.setUpAlterContractSumNoTax(zxCtContrApply.getUpAlterContractSumNoTax());
            // comID
            dbZxCtContrApply.setComId(zxCtContrApply.getComId());
            // ????????????????????????
            dbZxCtContrApply.setUpAlterContractSumTax(zxCtContrApply.getUpAlterContractSumTax());
            // ????????????ID
            dbZxCtContrApply.setFiId(zxCtContrApply.getFiId());
            // actDepartment
            dbZxCtContrApply.setActDepartment(zxCtContrApply.getActDepartment());
            // actDepartmentBM
            dbZxCtContrApply.setActDepartmentBm(zxCtContrApply.getActDepartmentBm());
            // actDepartmentID
            dbZxCtContrApply.setActDepartmentId(zxCtContrApply.getActDepartmentId());
            // biddersId
            dbZxCtContrApply.setBiddersId(zxCtContrApply.getBiddersId());
            // biddersCode
            dbZxCtContrApply.setBiddersCode(zxCtContrApply.getBiddersCode());
            // biddersName
            dbZxCtContrApply.setBiddersName(zxCtContrApply.getBiddersName());
            // ????????????ID
            dbZxCtContrApply.setAccountUnitId(zxCtContrApply.getAccountUnitId());
            // ??????????????????
            dbZxCtContrApply.setAccountUnitCode(zxCtContrApply.getAccountUnitCode());
            // ????????????
            dbZxCtContrApply.setAccountUnitName(zxCtContrApply.getAccountUnitName());
            // accountProjId
            dbZxCtContrApply.setAccountProjId(zxCtContrApply.getAccountProjId());
            // accountProjCode
            dbZxCtContrApply.setAccountProjCode(zxCtContrApply.getAccountProjCode());
            // accountProjName
            dbZxCtContrApply.setAccountProjName(zxCtContrApply.getAccountProjName());
            // projSite
            dbZxCtContrApply.setProjSite(zxCtContrApply.getProjSite());
            // busiSegments
            dbZxCtContrApply.setBusiSegments(zxCtContrApply.getBusiSegments());
            // projFundsSource
            dbZxCtContrApply.setProjFundsSource(zxCtContrApply.getProjFundsSource());
            // division
            dbZxCtContrApply.setDivision(zxCtContrApply.getDivision());
            // taxFilingCode
            dbZxCtContrApply.setTaxFilingCode(zxCtContrApply.getTaxFilingCode());
            // taxCountWay
            dbZxCtContrApply.setTaxCountWay(zxCtContrApply.getTaxCountWay());
            // taxAdvanceRate
            dbZxCtContrApply.setTaxAdvanceRate(zxCtContrApply.getTaxAdvanceRate());
            // taxUseWay
            dbZxCtContrApply.setTaxUseWay(zxCtContrApply.getTaxUseWay());
            // prepaidLand
            dbZxCtContrApply.setPrepaidLand(zxCtContrApply.getPrepaidLand());
            // nationalTax
            dbZxCtContrApply.setNationalTax(zxCtContrApply.getNationalTax());
            // nationalTaxTel
            dbZxCtContrApply.setNationalTaxTel(zxCtContrApply.getNationalTaxTel());
            // nationalTaxAdds
            dbZxCtContrApply.setNationalTaxAdds(zxCtContrApply.getNationalTaxAdds());
            // projDepAdds
            dbZxCtContrApply.setProjDepAdds(zxCtContrApply.getProjDepAdds());
            // projDepZipCode
            dbZxCtContrApply.setProjDepZipCode(zxCtContrApply.getProjDepZipCode());
            // projDepTel
            dbZxCtContrApply.setProjDepTel(zxCtContrApply.getProjDepTel());
            // projDepFax
            dbZxCtContrApply.setProjDepFax(zxCtContrApply.getProjDepFax());
            // projStage
            dbZxCtContrApply.setProjStage(zxCtContrApply.getProjStage());
            // pmFixedLine
            dbZxCtContrApply.setPmFixedLine(zxCtContrApply.getPmFixedLine());
            // pmMail
            dbZxCtContrApply.setPmMail(zxCtContrApply.getPmMail());
            // fiCharge
            dbZxCtContrApply.setFiCharge(zxCtContrApply.getFiCharge());
            // fiTel
            dbZxCtContrApply.setFiTel(zxCtContrApply.getFiTel());
            // fiFixedLine
            dbZxCtContrApply.setFiFixedLine(zxCtContrApply.getFiFixedLine());
            // fiMail
            dbZxCtContrApply.setFiMail(zxCtContrApply.getFiMail());
            // ctrCharge
            dbZxCtContrApply.setCtrCharge(zxCtContrApply.getCtrCharge());
            // ctrTel
            dbZxCtContrApply.setCtrTel(zxCtContrApply.getCtrTel());
            // ctrFixedLine
            dbZxCtContrApply.setCtrFixedLine(zxCtContrApply.getCtrFixedLine());
            // ctrMail
            dbZxCtContrApply.setCtrMail(zxCtContrApply.getCtrMail());
            // dcLeader
            dbZxCtContrApply.setDcLeader(zxCtContrApply.getDcLeader());
            // dcTel
            dbZxCtContrApply.setDcTel(zxCtContrApply.getDcTel());
            // dcFixedLine
            dbZxCtContrApply.setDcFixedLine(zxCtContrApply.getDcFixedLine());
            // dcMail
            dbZxCtContrApply.setDcMail(zxCtContrApply.getDcMail());
            // ????????????
            dbZxCtContrApply.setCreateTime(zxCtContrApply.getCreateTime());
            // ????????????
            dbZxCtContrApply.setDoubleCheckDate(zxCtContrApply.getDoubleCheckDate());
            // ????????????
            dbZxCtContrApply.setWriteDate(zxCtContrApply.getWriteDate());
            // ????????????id
            dbZxCtContrApply.setAccountDepId(zxCtContrApply.getAccountDepId());
            // ??????????????????
            dbZxCtContrApply.setAccountDepCode(zxCtContrApply.getAccountDepCode());
            // ????????????
            dbZxCtContrApply.setAccountDepName(zxCtContrApply.getAccountDepName());
            // ??????????????????
            dbZxCtContrApply.setSecondIDCode(zxCtContrApply.getSecondIDCode());
            // ?????????
            dbZxCtContrApply.setWriter(zxCtContrApply.getWriter());
            // ????????????
            dbZxCtContrApply.setSystemNo(zxCtContrApply.getSystemNo());
            // ????????????
            dbZxCtContrApply.setCurrencyCode(zxCtContrApply.getCurrencyCode());
            // ????????????
            dbZxCtContrApply.setCtrNature(zxCtContrApply.getCtrNature());
            // ??????????????????
            dbZxCtContrApply.setCtrUpdateState(zxCtContrApply.getCtrUpdateState());
            // ??????????????????
            dbZxCtContrApply.setFiCtrState(zxCtContrApply.getFiCtrState());
            // ????????????
            dbZxCtContrApply.setRevenueDir(zxCtContrApply.getRevenueDir());
            // ????????????
            dbZxCtContrApply.setInvoiceType(zxCtContrApply.getInvoiceType());
            // ????????????
            dbZxCtContrApply.setStaCtr(zxCtContrApply.getStaCtr());
            // ????????????
            dbZxCtContrApply.setKeyCtr(zxCtContrApply.getKeyCtr());
            // ????????????ID
            dbZxCtContrApply.setOrgId(zxCtContrApply.getOrgId());
            // notDisplay
            dbZxCtContrApply.setNotDisplay(zxCtContrApply.getNotDisplay());
            // ?????????
            dbZxCtContrApply.setDoubleCheckPerson(zxCtContrApply.getDoubleCheckPerson());
            // ??????????????????
            dbZxCtContrApply.setSecondIDCodeBh(zxCtContrApply.getSecondIDCodeBh());
            // auditWorkitemID
            dbZxCtContrApply.setAuditWorkitemId(zxCtContrApply.getAuditWorkitemId());
            // auditSys
            dbZxCtContrApply.setAuditSys(zxCtContrApply.getAuditSys());
            // ??????????????????
            dbZxCtContrApply.setSecondIDCodeName(zxCtContrApply.getSecondIDCodeName());
            // ????????????
            dbZxCtContrApply.setOrgName(zxCtContrApply.getOrgName());
            // ??????????????????
            dbZxCtContrApply.setSecondOrgType(zxCtContrApply.getSecondOrgType());
            // firstOAorgID
            dbZxCtContrApply.setFirstOAorgId(zxCtContrApply.getFirstOAorgId());
            // ?????????????????????????????????
            dbZxCtContrApply.setIsBiddedOnCloud(zxCtContrApply.getIsBiddedOnCloud());
            // ????????????
            dbZxCtContrApply.setBidType(zxCtContrApply.getBidType());
            // ???????????????????????????
            dbZxCtContrApply.setCloudBidNo(zxCtContrApply.getCloudBidNo());
            // ??????????????????
            dbZxCtContrApply.setBidOrgType(zxCtContrApply.getBidOrgType());
            // ?????????????????????????????????
            dbZxCtContrApply.setIsAllDepartJoin(zxCtContrApply.getIsAllDepartJoin());
            // ????????????
            dbZxCtContrApply.setJoinType(zxCtContrApply.getJoinType());
            // ???????????????????????????????????????
            dbZxCtContrApply.setIsDepartJoinBid(zxCtContrApply.getIsDepartJoinBid());
            // ?????????????????????
            dbZxCtContrApply.setContractDepart(zxCtContrApply.getContractDepart());
            // ????????????
            dbZxCtContrApply.setPackageNo(zxCtContrApply.getPackageNo());
            // ??????ID
            dbZxCtContrApply.setCloudEcoId(zxCtContrApply.getCloudEcoId());
            // ??????????????????
            dbZxCtContrApply.setInvestContractNo(zxCtContrApply.getInvestContractNo());
            // resCategoryID
            dbZxCtContrApply.setResCategoryId(zxCtContrApply.getResCategoryId());
            // resCategoryName
            dbZxCtContrApply.setResCategoryName(zxCtContrApply.getResCategoryName());
            // ??????
            dbZxCtContrApply.setSort(zxCtContrApply.getSort());
            // ??????
            dbZxCtContrApply.setModifyUserInfo(userKey, realName);
            flag = zxCtContrApplyMapper.updateByPrimaryKey(dbZxCtContrApply);

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtContrApply.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //??????list
            List<ZxErpFile> fileList = zxCtContrApply.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtContrApply.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtContrApply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrApply(List<ZxCtContrApply> zxCtContrApplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrApplyList != null && zxCtContrApplyList.size() > 0) {
            for (ZxCtContrApply zxCtContrApply : zxCtContrApplyList) {
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtContrApply.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }

            ZxCtContrApply zxCtContrApply = new ZxCtContrApply();
            zxCtContrApply.setModifyUserInfo(userKey, realName);
            flag = zxCtContrApplyMapper.batchDeleteUpdateZxCtContrApply(zxCtContrApplyList, zxCtContrApply);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtContrApplyList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    @Override
    public ResponseEntity getZxCtContrApplyWorksDetailList(ZxCtContrApply zxCtContrApply) {
        if (zxCtContrApply == null) {
            zxCtContrApply = new ZxCtContrApply();
        }
        // ????????????
        ZxCtContrApply dbZxCtContrApply = zxCtContrApplyMapper.selectByPrimaryKey(zxCtContrApply.getId());
        // ????????????
        if (dbZxCtContrApply != null) {
            //?????????????????????????????????
            ZxCtContrApplyWorks zxCtContrApplyWorks = new ZxCtContrApplyWorks();
            zxCtContrApplyWorks.setId("work" + dbZxCtContrApply.getId());
            zxCtContrApplyWorks.setParentId("work" + dbZxCtContrApply.getId());
            List<List<ZxCtContrApplyWorks>> zxCtContrApplyWorksList = new ArrayList<List<ZxCtContrApplyWorks>>();
            List<ZxCtContrApplyWorks> zxCtContrApplyWorksParentList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks);
            //zxCtContrApplyWorksList.add(zxCtContrApplyWorksList);
            if (zxCtContrApplyWorksParentList != null && zxCtContrApplyWorksParentList.size() > 0) {
                for (ZxCtContrApplyWorks zxCtContrApplyWorks1 : zxCtContrApplyWorksParentList) {
                    zxCtContrApplyWorks1.setParentId(zxCtContrApplyWorks1.getId());
                    //List<ZxCtContrApplyWorks> zxCtContrApplyWorksList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks1);
                    //zxCtContrApplyWorksList.add(zxCtContrApplyWorksList);
                }
            }

            //dbZxCtContrApply.setZxCtContrApplyWorksList(zxCtContrApplyWorksParentList);
            return repEntity.ok(dbZxCtContrApply);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    /**
     * ?????????????????????????????????????????????
     * @param workList
     * @param pid
     * @return
     */
    public  List<ZxCtContrApplyWorks> treeWorkList(List<ZxCtContrApplyWorks> workList, String pid) {
        List<ZxCtContrApplyWorks> childWorkList = new ArrayList<>();
        for (ZxCtContrApplyWorks zxCtContrApplyWorks : workList) {
            //????????????id???????????????id???add??????????????????
            if (pid.equals(zxCtContrApplyWorks.getParentId())) {
                //?????????????????????
                this.treeWorkList(workList,zxCtContrApplyWorks.getId());
                childWorkList.add(zxCtContrApplyWorks);
            }
        }

//        workList.stream()
//                //????????????id???????????????id
//                .filter(work -> StringUtils.isNotBlank(work.getParentId()) && work.getParentId().equals(pid))
//                .forEach(work -> {
//                    //?????????????????????
//                    treeWorkList(workList, work.getId());
//                    //??????
//                    childWorkList.add(work);
//                });
        return childWorkList;
    }

    // ?????????????????????????????????????????????
    @Override
    public ResponseEntity importZxCtContrApplyWorks(ZxCtContrApplyWorks zxCtContrApplyWorks) {
        if (zxCtContrApplyWorks == null) {
            zxCtContrApplyWorks = new ZxCtContrApplyWorks();
        }

        // ??????????????????
        if (zxCtContrApplyWorks.getImportFileList() == null || zxCtContrApplyWorks.getImportFileList().size() == 0) {
            return repEntity.layerMessage("no", "??????????????????!");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<ZxCtContrApplyWorks> importExcelList = null;
        try {
            String filePath = zxCtContrApplyWorks.getImportFileList().get(0).getUrl();
            InputStream inputStream = ResourceUtil.getStream(filePath);
            //InputStream inputStream = ResourceUtil.getStream("C:\\Users\\sjr\\Desktop\\worksImport1Tax.xlsx");
            String[] excelHead = {"????????????", "????????????", "??????", "??????", "??????????????????", "??????(%)"};
            String[] excelHeadAlias = {"workNo", "workName", "unit", "qty", "price", "taxRate"};
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<Object> header = reader.readRow(0);
            if ("?????????????????????".equals(header.get(4))) {
                excelHead[4] = excelHead[4].replaceAll("??????????????????", "?????????????????????");
                excelHeadAlias[4] = excelHeadAlias[4].replaceAll("price", "priceNoTax");
            }
            if ("??????".equals(header.get(5))) {
                excelHead[5] = excelHead[5].replaceAll("\\(\\%\\)", "");
            }
            //?????????????????????
            if (StrUtil.hasEmpty(excelHead) || StrUtil.hasEmpty(excelHeadAlias) || excelHead.length != excelHeadAlias.length) {
                return null;
            } else {
                for (int i = 0; i < header.size(); i++) {
                    if (excelHead[i].equals(header.get(i))) {
                        reader.addHeaderAlias(excelHead[i], excelHeadAlias[i]);
                    } else {
                        return repEntity.layerMessage("no", "???" + i + "??????????????????");
                    }
                }
            }
            importExcelList = reader.readAll(ZxCtContrApplyWorks.class);
            if (importExcelList == null || importExcelList.size() == 0) {
                return repEntity.layerMessage("no", "??????????????????????????????!");
            }
            if (importExcelList != null && importExcelList.size() > 0) {
                for (int i = 0; i < importExcelList.size(); i++) {
                    // ????????????
                    if (StrUtil.isEmpty(importExcelList.get(i).getWorkNo())) {
                        continue;
                    }
                    zxCtContrApplyWorks.setWorkNo(importExcelList.get(i).getWorkNo());
                    // ????????????
                    zxCtContrApplyWorks.setWorkName(importExcelList.get(i).getWorkName());
                    // ??????
                    zxCtContrApplyWorks.setUnit(importExcelList.get(i).getUnit());
                    // ??????
                    String qty = importExcelList.get(i).getQty().toString();
                    if (StrUtil.isNotEmpty(qty)) {
                        try {
                            BigDecimal qty1 = new BigDecimal(qty);
                            zxCtContrApplyWorks.setQty(qty1);
                        } catch (Exception e) {
                            return repEntity.layerMessage("no", "???Excel???" + i + "??????4????????????????????????(??????????????????),???????????????????????????????????????????????????");
                        }
                    }
                    // ??????
                    String price = importExcelList.get(i).getPrice().toString();
                    // ??????
                    String taxRate = importExcelList.get(i).getTaxRate();
                    if (StrUtil.isNotEmpty(price)) {
                        try {
                            BigDecimal price1 = new BigDecimal(price);
                            zxCtContrApplyWorks.setPrice(price1);

                            if (StrUtil.isEmpty(taxRate) || StrUtil.equals("???", taxRate)) {
                                zxCtContrApplyWorks.setPriceNoTax(new BigDecimal(0));
                            } else {
                                zxCtContrApplyWorks.setTaxRate(taxRate);
                                zxCtContrApplyWorks.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(price), CalcUtils.calcMultiply(CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(taxRate)), new BigDecimal(0.01)), 2));
                            }
                        } catch (Exception e) {
                            return repEntity.layerMessage("no", "???Excel???" + i + "??????5????????????????????????(??????????????????),???????????????????????????????????????????????????");
                        }
                    } else {
                        // ?????????????????????
                        zxCtContrApplyWorks.setPriceNoTax(importExcelList.get(i).getPriceNoTax());
                    }
                    zxCtContrApplyWorks.setId(UuidUtil.generate());
                    zxCtContrApplyWorks.setCreateUserInfo(userKey, realName);
                    zxCtContrApplyWorksMapper.insert(zxCtContrApplyWorks);
                }
                LoggerUtils.printDebugLogger(logger, "?????????????????????" + importExcelList.size() + "??????");
                LoggerUtils.printDebugLogger(logger, "??????????????????" + importExcelList);
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, e.getMessage());
            repEntity.layerMessage("no", "????????????");
        }
        return repEntity.ok("????????????" + importExcelList.size() + "????????????");

    }

    @Override
    public ResponseEntity getZxCtContrApplyWorksTree(ZxCtContrApplyWorks zxCtContrApplyWorks) {
        if(zxCtContrApplyWorks == null) {
            zxCtContrApplyWorks = new ZxCtContrApplyWorks();
        }
        List<ZxCtContrApplyWorks> zxCtContrApplyWorksList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks);
        JSONArray jsonArray = listToTree(new JSONArray(zxCtContrApplyWorksList), "id", "parentId", "workName");
        return repEntity.ok(jsonArray);
    }

    @Override
    public ResponseEntity getZxCtContrApplyWorksTreeData(ZxCtContrApplyWorks zxCtContrApplyWorks) {
        if(zxCtContrApplyWorks == null) {
            zxCtContrApplyWorks = new ZxCtContrApplyWorks();
        }
        List<ZxCtContrApplyWorks> zxCtContrApplyWorksList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks);
        return repEntity.ok(zxCtContrApplyWorksList);
    }

    /**
     * list???tree
     *
     * @param arr
     * @param id
     * @param parentId
     * @param workName
     * @return tree
     */
    private static JSONArray listToTree(JSONArray arr, String id, String parentId, String workName) {
        String child = "childrenList";
        JSONArray r = new JSONArray();
        JSONArray newArr = new JSONArray();
        JSONObject hash = new JSONObject();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            JSONObject newJSONObject = new JSONObject();
            newJSONObject.set(id, json.get(id));
            newJSONObject.set(parentId, json.get(parentId));
            newJSONObject.set(workName, json.get(workName));
            newJSONObject.set("treeNode", json.get("treeNode"));
            newJSONObject.set("orgId", json.get("orgId"));
            newJSONObject.set("contrApplyId", json.get("contrApplyId"));
            newJSONObject.set("workType", json.get("workType"));
            newJSONObject.set("workNo", json.get("workNo"));
            newJSONObject.set("unit", json.get("unit"));
            newJSONObject.set("contractPrice", json.get("contractPrice"));
            newJSONObject.set("contractQty", json.get("contractQty"));
            newJSONObject.set("contractAmt", json.get("contractAmt"));
            newJSONObject.set("quantity", json.get("quantity"));
            newJSONObject.set("price", json.get("price"));
            newJSONObject.set("isLeaf", json.get("isLeaf"));
            newJSONObject.set("exsitStatus", json.get("exsitStatus"));
            newJSONObject.set("isAssignable", json.get("isAssignable"));
            newJSONObject.set("updateFlag", json.get("updateFlag"));
            newJSONObject.set("combProp", json.get("combProp"));
            newJSONObject.set("pp1", json.get("pp1"));
            newJSONObject.set("pp2", json.get("pp2"));
            newJSONObject.set("pp3", json.get("pp3"));
            newJSONObject.set("pp4", json.get("pp4"));
            newJSONObject.set("contractReview", json.get("contractReview"));
            newJSONObject.set("pp6", json.get("pp6"));
            newJSONObject.set("pp7", json.get("pp7"));
            newJSONObject.set("pp8", json.get("pp8"));
            newJSONObject.set("pp9", json.get("pp9"));
            newJSONObject.set("leaseTerm", json.get("leaseTerm"));
            newJSONObject.set("checkQty", json.get("checkQty"));
            newJSONObject.set("expectChangeQty", json.get("expectChangeQty"));
            newJSONObject.set("expectChangePrice", json.get("expectChangePrice"));
            newJSONObject.set("inputWorkType", json.get("inputWorkType"));
            newJSONObject.set("isReCountAmt", json.get("isReCountAmt"));
            newJSONObject.set("qty", json.get("qty"));
            newJSONObject.set("isGroup", json.get("isGroup"));
            newJSONObject.set("requestEdit", json.get("requestEdit"));
            newJSONObject.set("contractPriceNoTax", json.get("contractPriceNoTax"));
            newJSONObject.set("priceNoTax", json.get("priceNoTax"));
            newJSONObject.set("taxRate", json.get("taxRate"));
            newJSONObject.set("amtNoTax", json.get("amtNoTax"));
            newJSONObject.set("ruleId", json.get("ruleId"));
            newJSONObject.set("ruleName", json.get("ruleName"));
            hash.set(json.getStr(id), newJSONObject);
            newArr.add(newJSONObject);
        }

        for (int j = 0; j < newArr.size(); j++) {
            JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
            JSONObject hashVP = hash.getJSONObject(aVal.getStr(parentId));
            if (hashVP != null) {
                if (hashVP.get(child) != null) {
                    JSONArray ch = hashVP.getJSONArray(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else
                r.add(aVal);
        }
        return r;
    }

    // ????????????????????????????????????????????????
    @Override
    public ResponseEntity getZxCtContrApplyFirstName(ZxCtContrApply zxCtContrApply) {
        // ????????????
        PageHelper.startPage(zxCtContrApply.getPage(), zxCtContrApply.getLimit());
        List<ZxCtContrApply> zxCtContrApplyList = new ArrayList<>();
        // ????????????
//        List<ZxCtContrApply> zxCtContrApplyList = zxCtContrApplyMapper.selectByZxCtContrApplyFirstNameList(zxCtContrApply);
//        if (zxCtContrApplyList != null && zxCtContrApplyList.size() > 0) {
//            for (ZxCtContrApply zxCtContrApply1 : zxCtContrApplyList) {
//                int expenseNo = sequenceService.getSequenceNextval(zxCtContrApply1.getContractCode());
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.set("limitNo",zxCtContrApply1.getContractCode()+"-"+"FB"+"-"+String.format("%03d",expenseNo));
//                zxCtContrApply1.setContractCode(jsonObject.toString());
//            }
//
//        }
        // ??????????????????
        PageInfo<ZxCtContrApply> p = new PageInfo<>(zxCtContrApplyList);
        return repEntity.okList(zxCtContrApplyList, p.getTotal());
    }
    public void level(List<ZxCtContrApplyWorks> importExcelList,String userKey,String realName) {
        String pWorkNo = importExcelList.get(0).getWorkNo();
        importExcelList.get(0).setParentId("-1");
        String genid = "work" + UuidUtil.generate();
        importExcelList.get(0).setId(genid);
        importExcelList.get(0).setCreateUserInfo(userKey,realName);
        zxCtContrApplyWorksMapper.insert(importExcelList.get(0));

        List<ZxCtContrApplyWorks> list = new ArrayList<>();
        for (int i = 1; i < importExcelList.size()-1; i++) {

            String workNo  = importExcelList.get(i).getWorkNo();
            if (pWorkNo.length() != workNo.length()) {
                list.add(importExcelList.get(i));
            } else if (pWorkNo.length() == workNo.length()) {
                list.get(0).setParentId(genid);
                list.get(0).setId(UuidUtil.generate());
                list.get(0).setCreateUserInfo(userKey,realName);
                zxCtContrApplyWorksMapper.insert(list.get(0));
                this.level2(list,userKey,realName);
                list.clear();

                genid = "work" + UuidUtil.generate();
                importExcelList.get(i).setId(genid);
                importExcelList.get(i).setParentId("-1");
                importExcelList.get(i).setCreateUserInfo(userKey,realName);
                zxCtContrApplyWorksMapper.insert(importExcelList.get(i));
                //workList = importExcelList.subList(index, importExcelList.size() - index);
                //break;
            } else if (importExcelList.get(i) == null){
                list.get(0).setParentId(genid);
                list.get(0).setId(UuidUtil.generate());
                list.get(0).setCreateUserInfo(userKey,realName);
                zxCtContrApplyWorksMapper.insert(list.get(0));
                this.level2(list,userKey,realName);
            }
        }
    }

    public void level2(List<ZxCtContrApplyWorks> list,String userKey,String realName) {
        String pWorkNo = list.get(0).getWorkNo();
        for (int j = 1; j < list.size(); j++) {
            String workNo  = list.get(j).getWorkNo();
            if(workNo.length() > pWorkNo.length()) {
                if(workNo.length() == list.get(j-1).getWorkNo().length()){
                    list.get(j).setParentId(list.get(j-1).getParentId());
                } else {
                    list.get(j).setParentId(list.get(j-1).getId());
                }
                list.get(j).setId(UuidUtil.generate());
            }
            if(workNo.length() == pWorkNo.length()){
                list.get(j).setParentId(list.get(0).getParentId());
                list.get(j).setId(UuidUtil.generate());
            }
            list.get(j).setCreateUserInfo(userKey,realName);
            zxCtContrApplyWorksMapper.insert(list.get(j));
        }
    }
}
