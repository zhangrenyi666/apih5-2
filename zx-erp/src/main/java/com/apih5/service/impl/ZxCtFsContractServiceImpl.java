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
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxCtFsContractReviewDetailMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtFsContractReview;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;
import com.apih5.mybatis.pojo.ZxErpFile;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractMapper;
import com.apih5.mybatis.pojo.ZxCtFsContract;
import com.apih5.service.ZxCtFsContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtFsContractService")
public class ZxCtFsContractServiceImpl implements ZxCtFsContractService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Override
    public ResponseEntity getZxCtFsContractListByCondition(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtFsContract.setComID("");
            zxCtFsContract.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxCtFsContract.setComID(zxCtFsContract.getOrgID());
            zxCtFsContract.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxCtFsContract.setOrgID(zxCtFsContract.getOrgID());
        }

        // ????????????
        PageHelper.startPage(zxCtFsContract.getPage(), zxCtFsContract.getLimit());
        // ????????????
        List<ZxCtFsContract> zxCtFsContractList = zxCtFsContractMapper.selectByZxCtFsContractList(zxCtFsContract);

        for (ZxCtFsContract zxCtFsContract1 : zxCtFsContractList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxCtFsContract1.getZxCtFsContractId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsContract1.setZxErpFileList(zxErpFileList);
        }
        // ??????????????????
        PageInfo<ZxCtFsContract> p = new PageInfo<>(zxCtFsContractList);

        return repEntity.okList(zxCtFsContractList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractDetail(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // ????????????
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsContract.getZxCtFsContractId());
        // ????????????
        if (dbZxCtFsContract != null) {
            return repEntity.ok(dbZxCtFsContract);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContract(ZxCtFsContract zxCtFsContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContract.setZxCtFsContractId(UuidUtil.generate());
        zxCtFsContract.setCreateUserInfo(userKey, realName);
        int flag = zxCtFsContractMapper.insert(zxCtFsContract);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtFsContract);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContract(ZxCtFsContract zxCtFsContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsContract.getZxCtFsContractId());
        if (dbZxCtFsContract != null && StrUtil.isNotEmpty(dbZxCtFsContract.getZxCtFsContractId())) {
            // ????????????
            dbZxCtFsContract.setContractNo(zxCtFsContract.getContractNo());
            // ????????????
            dbZxCtFsContract.setContractName(zxCtFsContract.getContractName());
            // ????????????
            dbZxCtFsContract.setContractType(zxCtFsContract.getContractType());
            // ??????
            dbZxCtFsContract.setSubject(zxCtFsContract.getSubject());
            // ????????????
            dbZxCtFsContract.setContent(zxCtFsContract.getContent());
            // ????????????ID
            dbZxCtFsContract.setOrgID(zxCtFsContract.getOrgID());
            // ??????ID
            dbZxCtFsContract.setFirstId(zxCtFsContract.getFirstId());
            // ????????????
            dbZxCtFsContract.setFirstName(zxCtFsContract.getFirstName());
            // ??????????????????
            dbZxCtFsContract.setFirstPrincipal(zxCtFsContract.getFirstPrincipal());
            // ?????????????????????
            dbZxCtFsContract.setFirstPrincipalIDCard(zxCtFsContract.getFirstPrincipalIDCard());
            // ??????????????????
            dbZxCtFsContract.setFirstUnitTel(zxCtFsContract.getFirstUnitTel());
            // ??????ID
            dbZxCtFsContract.setSecondID(zxCtFsContract.getSecondID());
            // ????????????
            dbZxCtFsContract.setSecondName(zxCtFsContract.getSecondName());
            // ????????????
            dbZxCtFsContract.setSecondPrincipal(zxCtFsContract.getSecondPrincipal());
            // ?????????????????????
            dbZxCtFsContract.setSecondPrincipalIDCard(zxCtFsContract.getSecondPrincipalIDCard());
            // ??????(??????)
            dbZxCtFsContract.setSecondUnitTel(zxCtFsContract.getSecondUnitTel());
            // ??????
            dbZxCtFsContract.setThirdName(zxCtFsContract.getThirdName());
            // ????????????
            dbZxCtFsContract.setPayType(zxCtFsContract.getPayType());
            // ????????????ID
            dbZxCtFsContract.setLocationInDeprId(zxCtFsContract.getLocationInDeprId());
            // ????????????
            dbZxCtFsContract.setRegisterDate(zxCtFsContract.getRegisterDate());
            // ????????????
            dbZxCtFsContract.setBidDate(zxCtFsContract.getBidDate());
            // ????????????
            dbZxCtFsContract.setSignatureDate(zxCtFsContract.getSignatureDate());
            // ??????????????????(??????)
            dbZxCtFsContract.setContractCost(zxCtFsContract.getContractCost());
            // ????????????(???)
            dbZxCtFsContract.setCsTimeLimit(zxCtFsContract.getCsTimeLimit());
            // ??????????????????
            dbZxCtFsContract.setPlanStartDate(zxCtFsContract.getPlanStartDate());
            // ??????????????????
            dbZxCtFsContract.setActualStartDate(zxCtFsContract.getActualStartDate());
            // ??????????????????
            dbZxCtFsContract.setPlanEndDate(zxCtFsContract.getPlanEndDate());
            // ??????????????????
            dbZxCtFsContract.setActualEndDate(zxCtFsContract.getActualEndDate());
            // ????????????
            dbZxCtFsContract.setConsultative(zxCtFsContract.getConsultative());
            // ??????????????????
            dbZxCtFsContract.setConsultativeTel(zxCtFsContract.getConsultativeTel());
            // ??????????????????
            dbZxCtFsContract.setOwnerLinkTel(zxCtFsContract.getOwnerLinkTel());
            // ????????????
            dbZxCtFsContract.setProjectManager(zxCtFsContract.getProjectManager());
            // ??????????????????
            dbZxCtFsContract.setProjectManagerTel(zxCtFsContract.getProjectManagerTel());
            // ??????????????????
            dbZxCtFsContract.setProjectChiefName(zxCtFsContract.getProjectChiefName());
            // ??????????????????
            dbZxCtFsContract.setProjectChiefTel(zxCtFsContract.getProjectChiefTel());
            // ????????????
            dbZxCtFsContract.setContractStatus(zxCtFsContract.getContractStatus());
            // ????????????
            dbZxCtFsContract.setProjectNo(zxCtFsContract.getProjectNo());
            // ????????????
            dbZxCtFsContract.setProjectName(zxCtFsContract.getProjectName());
            // ????????????
            dbZxCtFsContract.setShortName(zxCtFsContract.getShortName());
            // ???????????????
            dbZxCtFsContract.setVentureGuaranty(zxCtFsContract.getVentureGuaranty());
            // ??????????????????
            dbZxCtFsContract.setInnerContractSum(zxCtFsContract.getInnerContractSum());
            // ??????
            dbZxCtFsContract.setFaxRate(zxCtFsContract.getFaxRate());
            // ????????????
            dbZxCtFsContract.setManageRate(zxCtFsContract.getManageRate());
            // ????????????
            dbZxCtFsContract.setQuanlityFeeRate(zxCtFsContract.getQuanlityFeeRate());
            // ????????????
            dbZxCtFsContract.setGoodsSum(zxCtFsContract.getGoodsSum());
            // ????????????
            dbZxCtFsContract.setPaymentAssumpsit(zxCtFsContract.getPaymentAssumpsit());
            // ??????????????????
            dbZxCtFsContract.setTransferAssumpsit(zxCtFsContract.getTransferAssumpsit());
            // ????????????
            dbZxCtFsContract.setOtherAssumpsit(zxCtFsContract.getOtherAssumpsit());
            // ????????????
            dbZxCtFsContract.setAgent(zxCtFsContract.getAgent());
            // ????????????
            dbZxCtFsContract.setManageIndex(zxCtFsContract.getManageIndex());
            // ????????????
            dbZxCtFsContract.setImplementUnit(zxCtFsContract.getImplementUnit());
            // ????????????
            dbZxCtFsContract.setDesignUnit(zxCtFsContract.getDesignUnit());
            // ??????(??????)
            dbZxCtFsContract.setPp1(zxCtFsContract.getPp1());
            // ??????(??????)
            dbZxCtFsContract.setPp2(zxCtFsContract.getPp2());
            // ????????????
            dbZxCtFsContract.setPp3(zxCtFsContract.getPp3());
            // ??????????????????
            dbZxCtFsContract.setPp4(zxCtFsContract.getPp4());
            // ??????????????????
            dbZxCtFsContract.setPp5(zxCtFsContract.getPp5());
            // pp9
            dbZxCtFsContract.setPp9(zxCtFsContract.getPp9());
            // ????????????
            dbZxCtFsContract.setPp10(zxCtFsContract.getPp10());
            // name
            dbZxCtFsContract.setCode(zxCtFsContract.getCode());
            // ????????????
            dbZxCtFsContract.setCode1(zxCtFsContract.getCode1());
            // ??????????????????
            dbZxCtFsContract.setCode2(zxCtFsContract.getCode2());
            // ??????????????????
            dbZxCtFsContract.setCode3(zxCtFsContract.getCode3());
            // ????????????????????????
            dbZxCtFsContract.setCode4(zxCtFsContract.getCode4());
            // ????????????
            dbZxCtFsContract.setCode5(zxCtFsContract.getCode5());
            // ?????????
            dbZxCtFsContract.setCode6(zxCtFsContract.getCode6());
            // ????????????
            //dbZxCtFsContract.setCode7(zxCtFsContract.getCode7());
            // ????????????
            dbZxCtFsContract.setCode8(zxCtFsContract.getCode8());
            // ????????????ID
            dbZxCtFsContract.setFromApplyID(zxCtFsContract.getFromApplyID());
            // ?????????????????????
            dbZxCtFsContract.setCodeP1(zxCtFsContract.getCodeP1());
            // ????????????
            dbZxCtFsContract.setCode2T3(zxCtFsContract.getCode2T3());
            // ???????????????????????????????????????
            dbZxCtFsContract.setAlterContractSum(zxCtFsContract.getAlterContractSum());
            // ????????????
            dbZxCtFsContract.setSettleType(zxCtFsContract.getSettleType());
            // ??????(%)
            dbZxCtFsContract.setTaxRate(zxCtFsContract.getTaxRate());
            // ????????????
            dbZxCtFsContract.setIsDeduct(zxCtFsContract.getIsDeduct());
            // ?????????????????????(??????)
            dbZxCtFsContract.setContractCostNoTax(zxCtFsContract.getContractCostNoTax());
            // ????????????
            dbZxCtFsContract.setContractCostTax(zxCtFsContract.getContractCostTax());
            // ??????????????????????????????(??????)
            dbZxCtFsContract.setAlterContractSumNoTax(zxCtFsContract.getAlterContractSumNoTax());
            // ?????????????????????(??????)
            dbZxCtFsContract.setAlterContractSumTax(zxCtFsContract.getAlterContractSumTax());
            // comID
            dbZxCtFsContract.setComID(zxCtFsContract.getComID());
            // ?????????????????????
            dbZxCtFsContract.setStockSettleUseCount(zxCtFsContract.getStockSettleUseCount());
            // ????????????
            dbZxCtFsContract.setOrgName(zxCtFsContract.getOrgName());
            // ??????
            dbZxCtFsContract.setFirstIdFormula(zxCtFsContract.getFirstIdFormula());
            // ??????????????????????????????
            dbZxCtFsContract.setEquipSettleUseCount(zxCtFsContract.getEquipSettleUseCount());
            // ?????????????????????
            dbZxCtFsContract.setProjectSettleUseCount(zxCtFsContract.getProjectSettleUseCount());
            // ??????
            dbZxCtFsContract.setSecondIDFormula(zxCtFsContract.getSecondIDFormula());
            // ????????????
            dbZxCtFsContract.setLocationInDeprFormula(zxCtFsContract.getLocationInDeprFormula());
            // ??????????????????
            dbZxCtFsContract.setAuditContrAlterCount(zxCtFsContract.getAuditContrAlterCount());
            // ????????????
            dbZxCtFsContract.setAudit(zxCtFsContract.getAudit());
            // ??????????????????ID
            dbZxCtFsContract.setContractReviewId(zxCtFsContract.getContractReviewId());
            // ??????
            dbZxCtFsContract.setRemarks(zxCtFsContract.getRemarks());
            // ??????
            dbZxCtFsContract.setSort(zxCtFsContract.getSort());
            // ??????
            dbZxCtFsContract.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractMapper.updateByPrimaryKey(dbZxCtFsContract);

            //????????????????????????
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxCtFsContract.getZxCtFsContractId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxCtFsContract.getZxErpFileList() != null && zxCtFsContract.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxCtFsContract.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsContract.getZxCtFsContractId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtFsContract);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContract(List<ZxCtFsContract> zxCtFsContractList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtFsContractList != null && zxCtFsContractList.size() > 0) {
            ZxCtFsContract zxCtFsContract = new ZxCtFsContract();
            zxCtFsContract.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractMapper.batchDeleteUpdateZxCtFsContract(zxCtFsContractList, zxCtFsContract);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    public ResponseEntity getOrg(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // ????????????
        PageHelper.startPage(zxCtFsContract.getPage(), zxCtFsContract.getLimit());
        // ????????????
        List<ZxCtFsContract> OrgList = zxCtFsContractMapper.selectOrg(zxCtFsContract);
        // ??????????????????
        PageInfo<ZxCtFsContract> p = new PageInfo<>(OrgList);

        return repEntity.okList(OrgList, p.getTotal());
    }

    /**
     * ??????????????????????????????
     *
     * @param zxCtFsContract
     * @author suncg
     */

    @Override
    public ResponseEntity exportContract(ZxCtFsContract zxCtFsContract, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }

        // ????????????
        List<ZxCtFsContract> zxCtFsContractList = zxCtFsContractMapper.selectByZxCtFsContractList(zxCtFsContract);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("??????????????????", "??????????????????", "????????????", "????????????", "????????????", "??????????????????",
                "??????????????????????????????", "???????????????????????????????????????", "????????????", "????????????", "????????????", "????????????", "??????");
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxCtFsContractList != null && zxCtFsContractList.size() > 0) {
            for (ZxCtFsContract zxCtFsContract1 : zxCtFsContractList) {
                rowsList.add(CollUtil.newArrayList(zxCtFsContract1.getContractNo(),
                        zxCtFsContract1.getContractName(),
                        zxCtFsContract1.getContractType(),
                        zxCtFsContract1.getFirstName(),
                        zxCtFsContract1.getSecondName(),
                        zxCtFsContract1.getAudit(),
                        zxCtFsContract1.getContractCost(),
                        zxCtFsContract1.getAlterContractSum(),
                        zxCtFsContract1.getIsDeduct(),
                        zxCtFsContract1.getSettleType(),
                        zxCtFsContract1.getConsultative(),
                        zxCtFsContract1.getCode7(),
                        zxCtFsContract1.getRemarks()
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
    public ResponseEntity getZxCtFsContractList(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // ????????????
        PageHelper.startPage(zxCtFsContract.getPage(), zxCtFsContract.getLimit());
        // ????????????
        List<ZxCtFsContract> dbZxCtFsContractlsit = zxCtFsContractMapper.selectByOrg(zxCtFsContract);

        // ??????????????????
        PageInfo<ZxCtFsContract> p = new PageInfo<>(dbZxCtFsContractlsit);

        // ????????????
        if (dbZxCtFsContractlsit.size() > 0) {
            return repEntity.okList(dbZxCtFsContractlsit, p.getTotal());
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity getYunShuZxCtFsContractList(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // ????????????
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectYunShu(zxCtFsContract);

        // ????????????
        if (dbZxCtFsContract != null) {

            ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
            zxCtFsContractReviewDetail.setContractReviewId(dbZxCtFsContract.getContractReviewId());
            zxCtFsContractReviewDetail.setZxCtFsContractId(dbZxCtFsContract.getZxCtFsContractId());
            dbZxCtFsContract.setContractCost(new BigDecimal("0"));
            dbZxCtFsContract.setContractCostNoTax(new BigDecimal("0"));

            List<ZxCtFsContractReviewDetail> reviewDetails = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
            for (ZxCtFsContractReviewDetail zx : reviewDetails
            ) {
                if (zx.getChangeContractSum() != null) {
                    zx.setContractSum(zx.getChangeContractSum());
                    zx.setChangeContractSum(null);
                }
                if (zx.getChangeContractSumNoTax() != null) {
                    zx.setContractSumNoTax(zx.getChangeContractSumNoTax());
                    zx.setChangeContractSumNoTax(null);
                }
                if (zx.getChangeQty() != null) {
                    zx.setQty(zx.getChangeQty());
                    zx.setChangeQty(null);
                }
            }

            dbZxCtFsContract.setReviewDetailList(reviewDetails);
            return repEntity.ok(dbZxCtFsContract);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity getQiTaZxCtFsContractList(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // ????????????
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectQiTa(zxCtFsContract);
        // ????????????
        if (dbZxCtFsContract != null) {
            ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
            zxCtFsContractReviewDetail.setContractReviewId(dbZxCtFsContract.getContractReviewId());
            zxCtFsContractReviewDetail.setZxCtFsContractId(dbZxCtFsContract.getZxCtFsContractId());
            dbZxCtFsContract.setContractCost(new BigDecimal("0"));
            dbZxCtFsContract.setContractCostNoTax(new BigDecimal("0"));
            List<ZxCtFsContractReviewDetail> reviewDetails = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
            for (ZxCtFsContractReviewDetail zx : reviewDetails
            ) {
                if (zx.getChangeContractSum() != null) {
                    zx.setContractSum(zx.getChangeContractSum());
                    zx.setChangeContractSum(null);
                }
                if (zx.getChangeContractSumNoTax() != null) {
                    zx.setContractSumNoTax(zx.getChangeContractSumNoTax());
                    zx.setChangeContractSumNoTax(null);
                }
                if (zx.getChangeQty() != null) {
                    zx.setQty(zx.getChangeQty());
                    zx.setChangeQty(null);
                }
            }
            dbZxCtFsContract.setReviewDetailList(reviewDetails);
            return repEntity.ok(dbZxCtFsContract);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }


}
