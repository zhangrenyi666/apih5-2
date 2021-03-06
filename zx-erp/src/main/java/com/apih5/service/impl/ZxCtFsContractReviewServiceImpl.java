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
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.ZxCtFsContractBondMapper;
import com.apih5.mybatis.dao.ZxCtFsContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractReviewMapper;
import com.apih5.service.ZxCtFsContractReviewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtFsContractReviewService")
public class ZxCtFsContractReviewServiceImpl implements ZxCtFsContractReviewService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsContractReviewMapper zxCtFsContractReviewMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Autowired(required = true)
    private ZxCtFsContractBondMapper zxCtFsContractBondMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxCtFsContractReviewListByCondition(ZxCtFsContractReview zxCtFsContractReview) {
        if (zxCtFsContractReview == null) {
            zxCtFsContractReview = new ZxCtFsContractReview();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtFsContractReview.setComId("");
            zxCtFsContractReview.setOrgId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxCtFsContractReview.setComId(zxCtFsContractReview.getOrgId());
            zxCtFsContractReview.setOrgId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxCtFsContractReview.setOrgId(zxCtFsContractReview.getOrgId());
        }

        // ????????????
        PageHelper.startPage(zxCtFsContractReview.getPage(), zxCtFsContractReview.getLimit());
        // ????????????
        List<ZxCtFsContractReview> zxCtFsContractReviewList = zxCtFsContractReviewMapper.selectByZxCtFsContractReviewList(zxCtFsContractReview);

        for (ZxCtFsContractReview zxCtFsContractReview1 : zxCtFsContractReviewList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxCtFsContractReview1.getContractReviewId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsContractReview1.setZxErpFileList(zxErpFileList);
        }

        // ??????????????????
        PageInfo<ZxCtFsContractReview> p = new PageInfo<>(zxCtFsContractReviewList);

        return repEntity.okList(zxCtFsContractReviewList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractReviewDetail(ZxCtFsContractReview zxCtFsContractReview) {
        if (zxCtFsContractReview == null) {
            zxCtFsContractReview = new ZxCtFsContractReview();
        }
        ZxCtFsContractReview dbZxCtFsContractReview = new ZxCtFsContractReview();
        // ????????????
        if (zxCtFsContractReview.getContractReviewId() != "") {
            dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReview.getContractReviewId());
        } else if (zxCtFsContractReview.getWorkId() != null) {
            dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByWorkId(zxCtFsContractReview.getWorkId());
        }

        // ????????????
        if (dbZxCtFsContractReview != null) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherType("1");
            file.setOtherId(dbZxCtFsContractReview.getContractReviewId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsContractReview.setZxErpFileList(zxErpFileList);
            file.setOtherType("2");
            List<ZxErpFile> zhengWenFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsContractReview.setZxZhengWenFileList(zhengWenFileList);
            return repEntity.ok(dbZxCtFsContractReview);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        //zxCtFsContractReviewMapper.selectLeiJi(zxCtFsContractReview.getContractNo());
        ZxCtFsContractReview zxCtFsContractReview1 = new ZxCtFsContractReview();
        zxCtFsContractReview1.setContractNo(zxCtFsContractReview.getContractNo());
        List<ZxCtFsContractReview> list1 = zxCtFsContractReviewMapper.selectByZxCtFsContractReviewList(zxCtFsContractReview1);
        if (list1.size() > 0) {
            return repEntity.layerMessage("no", "???????????????????????????");
        }
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContractReview.setContractReviewId(UuidUtil.generate());
        zxCtFsContractReview.setCreateUserInfo(userKey, realName);
        zxCtFsContractReview.getFromContractNo();
        zxCtFsContractReview.setBeginPer(realName);
        zxCtFsContractReview.setApih5FlowStatus("-1");
        int flag = zxCtFsContractReviewMapper.insert(zxCtFsContractReview);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            List<ZxErpFile> zxErpFileList = zxCtFsContractReview.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxCtFsContractReview.getContractReviewId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "?????????????????????");
                    }
                }
            }
            return repEntity.ok("sys.data.sava", zxCtFsContractReview);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContractReview dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReview.getContractReviewId());
        if (dbZxCtFsContractReview != null && StrUtil.isNotEmpty(dbZxCtFsContractReview.getContractReviewId())) {
            if (!dbZxCtFsContractReview.getIsDeduct().equals(zxCtFsContractReview.getIsDeduct())) {

            }
            // ??????????????????
            dbZxCtFsContractReview.setContractNo(zxCtFsContractReview.getContractNo());
            // ??????????????????
            dbZxCtFsContractReview.setContractName(zxCtFsContractReview.getContractName());
            // ????????????
            dbZxCtFsContractReview.setContractType(zxCtFsContractReview.getContractType());
            // ??????????????????(??????)
            dbZxCtFsContractReview.setContractCost(zxCtFsContractReview.getContractCost());
            // ??????ID
            dbZxCtFsContractReview.setFirstId(zxCtFsContractReview.getFirstId());
            // ????????????
            dbZxCtFsContractReview.setFirstName(zxCtFsContractReview.getFirstName());
            // ??????ID
            dbZxCtFsContractReview.setSecondId(zxCtFsContractReview.getSecondId());
            // ????????????
            dbZxCtFsContractReview.setSecondName(zxCtFsContractReview.getSecondName());
            // ????????????
            dbZxCtFsContractReview.setStartDate(zxCtFsContractReview.getStartDate());
            // ????????????
            dbZxCtFsContractReview.setEndDate(zxCtFsContractReview.getEndDate());
            // ????????????
            dbZxCtFsContractReview.setCsTimeLimit(zxCtFsContractReview.getCsTimeLimit());
            // ???????????????
            dbZxCtFsContractReview.setAgent(zxCtFsContractReview.getAgent());
            // ????????????
            dbZxCtFsContractReview.setContent(zxCtFsContractReview.getContent());
            // ????????????
            dbZxCtFsContractReview.setStatus(zxCtFsContractReview.getStatus());
            // combProp
            dbZxCtFsContractReview.setCombProp(zxCtFsContractReview.getCombProp());
            // ????????????
            dbZxCtFsContractReview.setPp1(zxCtFsContractReview.getPp1());
            // ????????????
            dbZxCtFsContractReview.setPp2(zxCtFsContractReview.getPp2());
            // ????????????
            dbZxCtFsContractReview.setPp3(zxCtFsContractReview.getPp3());
            // ??????????????????
            dbZxCtFsContractReview.setPp4(zxCtFsContractReview.getPp4());
            // ??????????????????
            dbZxCtFsContractReview.setPp5(zxCtFsContractReview.getPp5());
            // pp6
            dbZxCtFsContractReview.setPp6(zxCtFsContractReview.getPp6());
            // pp7
            dbZxCtFsContractReview.setPp7(zxCtFsContractReview.getPp7());
            // ??????
            dbZxCtFsContractReview.setPp8(zxCtFsContractReview.getPp8());
            // pp9
            dbZxCtFsContractReview.setPp9(zxCtFsContractReview.getPp9());
            // ????????????
            dbZxCtFsContractReview.setPp10(zxCtFsContractReview.getPp10());
            // ????????????ID
            dbZxCtFsContractReview.setInstProcessId(zxCtFsContractReview.getInstProcessId());
            // ????????????ID
            dbZxCtFsContractReview.setWorkitemId(zxCtFsContractReview.getWorkitemId());
            // ??????
            dbZxCtFsContractReview.setCode(zxCtFsContractReview.getCode());
            // ????????????
            dbZxCtFsContractReview.setCode1(zxCtFsContractReview.getCode1());
            // ??????????????????
            dbZxCtFsContractReview.setCode2(zxCtFsContractReview.getCode2());
            // ??????????????????
            dbZxCtFsContractReview.setCode3(zxCtFsContractReview.getCode3());
            // ????????????????????????
            dbZxCtFsContractReview.setCode4(zxCtFsContractReview.getCode4());
            // ????????????
            dbZxCtFsContractReview.setCode5(zxCtFsContractReview.getCode5());
            // ?????????
            dbZxCtFsContractReview.setCode6(zxCtFsContractReview.getCode6());
            // ????????????
            dbZxCtFsContractReview.setCode7(zxCtFsContractReview.getCode7());
            // ????????????
            dbZxCtFsContractReview.setCode8(zxCtFsContractReview.getCode8());
            // ?????????????????????
            dbZxCtFsContractReview.setCodeP1(zxCtFsContractReview.getCodeP1());
            // ????????????
            dbZxCtFsContractReview.setCode2T3(zxCtFsContractReview.getCode2T3());
            // ???????????????
            dbZxCtFsContractReview.setIsFlag(zxCtFsContractReview.getIsFlag());
            // ???????????????ID
            dbZxCtFsContractReview.setSendToJuId(zxCtFsContractReview.getSendToJuId());
            // ????????????????????????
            dbZxCtFsContractReview.setUpAlterContractSum(zxCtFsContractReview.getUpAlterContractSum());
            // ??????????????????
            dbZxCtFsContractReview.setIsFlagZhb(zxCtFsContractReview.getIsFlagZhb());
            // ??????????????????ID
            dbZxCtFsContractReview.setSendToZhbId(zxCtFsContractReview.getSendToZhbId());
            // ???????????????
            dbZxCtFsContractReview.setFromContractName(zxCtFsContractReview.getFromContractName());
            // comId
            dbZxCtFsContractReview.setComId(zxCtFsContractReview.getComId());
            // ???????????????ID
            dbZxCtFsContractReview.setFromContractId(zxCtFsContractReview.getFromContractId());
            // ???????????????
            dbZxCtFsContractReview.setFromContractNo(zxCtFsContractReview.getFromContractNo());
            // ??????ID
            dbZxCtFsContractReview.setOrgId(zxCtFsContractReview.getOrgId());
            // ??????????????????
            dbZxCtFsContractReview.setSecondOrgType(zxCtFsContractReview.getSecondOrgType());
            // ?????????????????????????????????
            dbZxCtFsContractReview.setContractCostNoTax(zxCtFsContractReview.getContractCostNoTax());
            // ????????????
            dbZxCtFsContractReview.setContractCostTax(zxCtFsContractReview.getContractCostTax());
            // ??????
            dbZxCtFsContractReview.setTaxRate(zxCtFsContractReview.getTaxRate());

            // ?????????????????????????????????
            dbZxCtFsContractReview.setUpAlterContractSumNoTax(zxCtFsContractReview.getUpAlterContractSumNoTax());
            // ??????????????????????????????
            dbZxCtFsContractReview.setUpAlterContractSumTax(zxCtFsContractReview.getUpAlterContractSumTax());
            // ????????????id
            dbZxCtFsContractReview.setZxCtFsContractId(zxCtFsContractReview.getZxCtFsContractId());
            // ??????
            dbZxCtFsContractReview.setRemarks(zxCtFsContractReview.getRemarks());
            // ??????
            dbZxCtFsContractReview.setSort(zxCtFsContractReview.getSort());

            dbZxCtFsContractReview.setApih5FlowId(zxCtFsContractReview.getApih5FlowId());
            dbZxCtFsContractReview.setApih5FlowStatus(zxCtFsContractReview.getApih5FlowStatus());
            dbZxCtFsContractReview.setApih5FlowNodeStatus(zxCtFsContractReview.getApih5FlowNodeStatus());
            dbZxCtFsContractReview.setWorkId(zxCtFsContractReview.getWorkId());
            if (StrUtil.equals("opinionField1", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField1(zxCtFsContractReview.getOpinionContent(realName,
                        dbZxCtFsContractReview.getOpinionField1()));
            }
            //
            if (StrUtil.equals("opinionField2", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField2(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField2()));
            }
            //
            if (StrUtil.equals("opinionField3", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField3(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField3()));
            }
            //
            if (StrUtil.equals("opinionField4", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField4(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField4()));
            }
            //
            if (StrUtil.equals("opinionField5", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField5(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField5()));
            }
            //
            if (StrUtil.equals("opinionField6", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField6(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField6()));
            }
            //
            if (StrUtil.equals("opinionField7", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField7(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField7()));
            }
            //
            if (StrUtil.equals("opinionField8", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField8(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField8()));
            }
            //
            if (StrUtil.equals("opinionField9", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField9(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField9()));
            }
            //
            if (StrUtil.equals("opinionField10", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField10(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField10()));
            }
//            if(){
//
//            }
            // ????????????
            dbZxCtFsContractReview.setIsDeduct(zxCtFsContractReview.getIsDeduct());
            // ??????
            dbZxCtFsContractReview.setModifyUserInfo(userKey, realName);
            if ("2".equals(zxCtFsContractReview.getApih5FlowStatus())) {
                flag = synContract(dbZxCtFsContractReview);
                if (flag == 0) {
                    return repEntity.errorSave();
                }
            }
            flag = zxCtFsContractReviewMapper.updateByPrimaryKey(dbZxCtFsContractReview);
            //????????????????????????
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxCtFsContractReview.getContractReviewId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxCtFsContractReview.getZxErpFileList() != null && zxCtFsContractReview.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxCtFsContractReview.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsContractReview.getContractReviewId());
                    file.setOtherType("1");
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
            if (zxCtFsContractReview.getZxZhengWenFileList() != null && zxCtFsContractReview.getZxZhengWenFileList().size() > 0) {
                for (ZxErpFile file : zxCtFsContractReview.getZxZhengWenFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsContractReview.getContractReviewId());
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
            return repEntity.ok("sys.data.update", zxCtFsContractReview);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContractReview(List<ZxCtFsContractReview> zxCtFsContractReviewList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtFsContractReviewList != null && zxCtFsContractReviewList.size() > 0) {

            for (ZxCtFsContractReview zxCtFsReview : zxCtFsContractReviewList) {
                if (StrUtil.isNotEmpty(zxCtFsReview.getWorkId())) {
                    jsonArr.add(zxCtFsReview.getWorkId());
                }
            }
            ZxCtFsContractReview zxCtFsContractReview = new ZxCtFsContractReview();
            zxCtFsContractReview.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractReviewMapper.batchDeleteUpdateZxCtFsContractReview(zxCtFsContractReviewList, zxCtFsContractReview);
            if (jsonArr.size() > 0) {
                HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractReviewList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ????????????????????????
     *
     * @param zxCtFsContractReview
     * @author suncg
     */
    private int synContract(ZxCtFsContractReview zxCtFsContractReview) {
        int flag = 0;
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        ZxCtFsContract zxCtFsContract = new ZxCtFsContract();
        zxCtFsContract.setZxCtFsContractId(UuidUtil.generate()); // ??????
        zxCtFsContract.setSettleType("???????????????");
        zxCtFsContract.setAudit("????????????");
        zxCtFsContract.setSecondPrincipal(zxCtFsContractReview.getAgent());//????????????
        zxCtFsContract.setContractNo(zxCtFsContractReview.getContractNo());// ????????????
        zxCtFsContract.setContractName(zxCtFsContractReview.getContractName()); // ????????????
        zxCtFsContract.setContractType(zxCtFsContractReview.getContractType());  // ????????????
        zxCtFsContract.setContent(zxCtFsContractReview.getContent()); // ????????????
        zxCtFsContract.setOrgID(zxCtFsContractReview.getOrgId());// ????????????ID
        zxCtFsContract.setFirstId(zxCtFsContractReview.getFirstId());    // ??????ID
        zxCtFsContract.setFirstName(zxCtFsContractReview.getFirstName()); // ????????????
        zxCtFsContract.setSecondID(zxCtFsContractReview.getSecondId());//??????ID
        zxCtFsContract.setSecondName(zxCtFsContractReview.getSecondName());  // ????????????
        zxCtFsContract.setContractCost(zxCtFsContractReview.getContractCost());//??????????????????(??????)
        zxCtFsContract.setCsTimeLimit(zxCtFsContractReview.getCsTimeLimit());// ????????????(???)
        zxCtFsContract.setPlanStartDate(zxCtFsContractReview.getStartDate());// ??????????????????(????????????)
        zxCtFsContract.setPlanEndDate(zxCtFsContractReview.getEndDate()); //??????????????????(????????????)
        zxCtFsContract.setContractStatus("1");// ????????????(????????????)
        zxCtFsContract.setProjectName(zxCtFsContractReview.getFirstName());
        zxCtFsContract.setProjectNo(zxCtFsContractReview.getOrgId());
        zxCtFsContract.setShortName(zxCtFsContractReview.getCode5()); // ????????????
        zxCtFsContract.setFaxRate(zxCtFsContractReview.getTaxRate());// ??????
        zxCtFsContract.setPp1(zxCtFsContractReview.getCodeP1());    // ??????(??????)
        zxCtFsContract.setPp2(zxCtFsContractReview.getPp2()); // ??????(??????)
        zxCtFsContract.setPp3(zxCtFsContractReview.getPp3());  // ????????????
        zxCtFsContract.setPp4(zxCtFsContractReview.getPp4()); // ??????????????????
        zxCtFsContract.setPp5(zxCtFsContractReview.getPp5()); //  ??????????????????
        zxCtFsContract.setPp9(zxCtFsContractReview.getPp9()); //  pp9
        zxCtFsContract.setPp10(zxCtFsContractReview.getPp10()); //   ????????????
        zxCtFsContract.setCode(zxCtFsContractReview.getCode());// ??????
        zxCtFsContract.setCode1(zxCtFsContractReview.getCode1());// ????????????
        zxCtFsContract.setCode2(zxCtFsContractReview.getCode2());// ??????????????????
        zxCtFsContract.setCode3(zxCtFsContractReview.getCode3());// ??????????????????
        zxCtFsContract.setCode4(zxCtFsContractReview.getCode4());// ????????????????????????
        zxCtFsContract.setCode5(zxCtFsContractReview.getCode5());  // ????????????
        zxCtFsContract.setCode6(zxCtFsContractReview.getCode6());// ?????????
        zxCtFsContract.setCode7(zxCtFsContractReview.getCode7()); // ????????????
        zxCtFsContract.setCode8(zxCtFsContractReview.getCode8());// ????????????
        zxCtFsContract.setTaxRate(String.valueOf(zxCtFsContractReview.getTaxRate()));//??????
        zxCtFsContract.setIsDeduct(zxCtFsContractReview.getIsDeduct());// ????????????
        zxCtFsContract.setContractCostNoTax(zxCtFsContractReview.getContractCostNoTax());// ?????????????????????(??????)
        zxCtFsContract.setContractCostTax(zxCtFsContractReview.getContractCostTax());// ????????????
        zxCtFsContract.setComID(zxCtFsContractReview.getComId());//??????ID
        zxCtFsContract.setContractReviewId(zxCtFsContractReview.getContractReviewId());// ??????????????????ID
        zxCtFsContract.setDelFlag("0");
        zxCtFsContract.setWorkId(zxCtFsContractReview.getWorkId());
        zxCtFsContract.setRemarks(zxCtFsContractReview.getRemarks());
        zxCtFsContract.setFromContractNo(zxCtFsContractReview.getFromContractNo());
        zxCtFsContract.setSignatureDate(new Date());
        zxCtFsContract.setCreateUserInfo(userKey, realName);
        flag = zxCtFsContractMapper.insert(zxCtFsContract);

        return flag;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     *
     * @param zxCtFsContractReview
     * @author suncg
     */

    @Override
    public ResponseEntity getZxCtFsContractNo(ZxCtFsContractReview zxCtFsContractReview) {
        if (zxCtFsContractReview == null) {
            repEntity.layerMessage("no", "???????????????????????????");
        } else if (zxCtFsContractReview.getFromContractNo().equals("")) {
            repEntity.layerMessage("no", "???????????????????????????");
        }

        int num = zxCtFsContractReviewMapper.selectLeiJi(zxCtFsContractReview.getFromContractNo()) + 1;
        zxCtFsContractReview.setContractNo(zxCtFsContractReview.getFromContractNo() + "-" + "???" + String.format("%03d", num));
        //ZxCtFsContractReview dbZxCtFsContractReview = new ZxCtFsContractReview();
        return repEntity.ok(zxCtFsContractReview);

    }

    /**
     * ??????????????????????????????
     *
     * @param zxCtFsContractReview
     * @author suncg
     */

    @Override
    public ResponseEntity exportContractReview(ZxCtFsContractReview zxCtFsContractReview, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxCtFsContractReview == null) {
            zxCtFsContractReview = new ZxCtFsContractReview();
        }

        // ????????????
        List<ZxCtFsContractReview> zxCtFsContractReviewList = zxCtFsContractReviewMapper.selectByZxCtFsContractReviewList(zxCtFsContractReview);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("??????????????????", "??????????????????", "????????????", "???????????????", "????????????", "????????????",
                "????????????", "??????????????????", "???????????????", "??????????????????????????????", "????????????", "????????????", "?????????", "????????????");
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxCtFsContractReviewList != null && zxCtFsContractReviewList.size() > 0) {
            for (ZxCtFsContractReview zxCtFsContractReview1 : zxCtFsContractReviewList) {
                rowsList.add(CollUtil.newArrayList(zxCtFsContractReview1.getContractNo(),
                        zxCtFsContractReview1.getContractName(),
                        zxCtFsContractReview1.getContractType(),
                        zxCtFsContractReview1.getFromContractNo(),
                        zxCtFsContractReview1.getCode7(),
                        zxCtFsContractReview1.getFirstName(),
                        zxCtFsContractReview1.getSecondName(),
                        zxCtFsContractReview1.getSecondOrgType(),
                        zxCtFsContractReview1.getAgent(),
                        zxCtFsContractReview1.getContractCost(),
                        zxCtFsContractReview1.getIsDeduct(),
                        zxCtFsContractReview1.getCsTimeLimit(),
                        zxCtFsContractReview1.getBeginPer(),
                        zxCtFsContractReview1.getStatus()

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


//    @Override
//    public ResponseEntity updateZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview) {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String userKey = TokenUtils.getUserKey(request);
//        String realName = TokenUtils.getRealName(request);
//        int flag = 0;
//        ZxCtFsContractReview dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReview.getContractReviewId());
//        if (dbZxCtFsContractReview != null && StrUtil.isNotEmpty(dbZxCtFsContractReview.getContractReviewId())) {
//            if(!dbZxCtFsContractReview.getIsDeduct().equals(zxCtFsContractReview.getIsDeduct())){
//
//            }
//            // ??????????????????
//            dbZxCtFsContractReview.setContractNo(zxCtFsContractReview.getContractNo());
//            // ??????????????????
//            dbZxCtFsContractReview.setContractName(zxCtFsContractReview.getContractName());
//            // ????????????
//            dbZxCtFsContractReview.setContractType(zxCtFsContractReview.getContractType());
//            // ??????????????????(??????)
//            dbZxCtFsContractReview.setContractCost(zxCtFsContractReview.getContractCost());
//            // ??????ID
//            dbZxCtFsContractReview.setFirstId(zxCtFsContractReview.getFirstId());
//            // ????????????
//            dbZxCtFsContractReview.setFirstName(zxCtFsContractReview.getFirstName());
//            // ??????ID
//            dbZxCtFsContractReview.setSecondId(zxCtFsContractReview.getSecondId());
//            // ????????????
//            dbZxCtFsContractReview.setSecondName(zxCtFsContractReview.getSecondName());
//            // ????????????
//            dbZxCtFsContractReview.setStartDate(zxCtFsContractReview.getStartDate());
//            // ????????????
//            dbZxCtFsContractReview.setEndDate(zxCtFsContractReview.getEndDate());
//            // ????????????
//            dbZxCtFsContractReview.setCsTimeLimit(zxCtFsContractReview.getCsTimeLimit());
//            // ???????????????
//            dbZxCtFsContractReview.setAgent(zxCtFsContractReview.getAgent());
//            // ????????????
//            dbZxCtFsContractReview.setContent(zxCtFsContractReview.getContent());
//            // ????????????
//            dbZxCtFsContractReview.setStatus(zxCtFsContractReview.getStatus());
//            // combProp
//            dbZxCtFsContractReview.setCombProp(zxCtFsContractReview.getCombProp());
//            // ????????????
//            dbZxCtFsContractReview.setPp1(zxCtFsContractReview.getPp1());
//            // ????????????
//            dbZxCtFsContractReview.setPp2(zxCtFsContractReview.getPp2());
//            // ????????????
//            dbZxCtFsContractReview.setPp3(zxCtFsContractReview.getPp3());
//            // ??????????????????
//            dbZxCtFsContractReview.setPp4(zxCtFsContractReview.getPp4());
//            // ??????????????????
//            dbZxCtFsContractReview.setPp5(zxCtFsContractReview.getPp5());
//            // pp6
//            dbZxCtFsContractReview.setPp6(zxCtFsContractReview.getPp6());
//            // pp7
//            dbZxCtFsContractReview.setPp7(zxCtFsContractReview.getPp7());
//            // ??????
//            dbZxCtFsContractReview.setPp8(zxCtFsContractReview.getPp8());
//            // pp9
//            dbZxCtFsContractReview.setPp9(zxCtFsContractReview.getPp9());
//            // ????????????
//            dbZxCtFsContractReview.setPp10(zxCtFsContractReview.getPp10());
//            // ????????????ID
//            dbZxCtFsContractReview.setInstProcessId(zxCtFsContractReview.getInstProcessId());
//            // ????????????ID
//            dbZxCtFsContractReview.setWorkitemId(zxCtFsContractReview.getWorkitemId());
//            // ??????
//            dbZxCtFsContractReview.setCode(zxCtFsContractReview.getCode());
//            // ????????????
//            dbZxCtFsContractReview.setCode1(zxCtFsContractReview.getCode1());
//            // ??????????????????
//            dbZxCtFsContractReview.setCode2(zxCtFsContractReview.getCode2());
//            // ??????????????????
//            dbZxCtFsContractReview.setCode3(zxCtFsContractReview.getCode3());
//            // ????????????????????????
//            dbZxCtFsContractReview.setCode4(zxCtFsContractReview.getCode4());
//            // ????????????
//            dbZxCtFsContractReview.setCode5(zxCtFsContractReview.getCode5());
//            // ?????????
//            dbZxCtFsContractReview.setCode6(zxCtFsContractReview.getCode6());
//            // ????????????
//            dbZxCtFsContractReview.setCode7(zxCtFsContractReview.getCode7());
//            // ????????????
//            dbZxCtFsContractReview.setCode8(zxCtFsContractReview.getCode8());
//            // ?????????????????????
//            dbZxCtFsContractReview.setCodeP1(zxCtFsContractReview.getCodeP1());
//            // ????????????
//            dbZxCtFsContractReview.setCode2T3(zxCtFsContractReview.getCode2T3());
//            // ???????????????
//            dbZxCtFsContractReview.setIsFlag(zxCtFsContractReview.getIsFlag());
//            // ???????????????ID
//            dbZxCtFsContractReview.setSendToJuId(zxCtFsContractReview.getSendToJuId());
//            // ????????????????????????
//            dbZxCtFsContractReview.setUpAlterContractSum(zxCtFsContractReview.getUpAlterContractSum());
//            // ??????????????????
//            dbZxCtFsContractReview.setIsFlagZhb(zxCtFsContractReview.getIsFlagZhb());
//            // ??????????????????ID
//            dbZxCtFsContractReview.setSendToZhbId(zxCtFsContractReview.getSendToZhbId());
//            // ???????????????
//            dbZxCtFsContractReview.setFromContractName(zxCtFsContractReview.getFromContractName());
//            // comId
//            dbZxCtFsContractReview.setComId(zxCtFsContractReview.getComId());
//            // ???????????????ID
//            dbZxCtFsContractReview.setFromContractId(zxCtFsContractReview.getFromContractId());
//            // ???????????????
//            dbZxCtFsContractReview.setFromContractNo(zxCtFsContractReview.getFromContractNo());
//            // ??????ID
//            dbZxCtFsContractReview.setOrgId(zxCtFsContractReview.getOrgId());
//            // ??????????????????
//            dbZxCtFsContractReview.setSecondOrgType(zxCtFsContractReview.getSecondOrgType());
//            // ?????????????????????????????????
//            dbZxCtFsContractReview.setContractCostNoTax(zxCtFsContractReview.getContractCostNoTax());
//            // ????????????
//            dbZxCtFsContractReview.setContractCostTax(zxCtFsContractReview.getContractCostTax());
//            // ??????
//            dbZxCtFsContractReview.setTaxRate(zxCtFsContractReview.getTaxRate());
//
//            // ?????????????????????????????????
//            dbZxCtFsContractReview.setUpAlterContractSumNoTax(zxCtFsContractReview.getUpAlterContractSumNoTax());
//            // ??????????????????????????????
//            dbZxCtFsContractReview.setUpAlterContractSumTax(zxCtFsContractReview.getUpAlterContractSumTax());
//            // ????????????id
//            dbZxCtFsContractReview.setZxCtFsContractId(zxCtFsContractReview.getZxCtFsContractId());
//            // ??????
//            dbZxCtFsContractReview.setRemarks(zxCtFsContractReview.getRemarks());
//            // ??????
//            dbZxCtFsContractReview.setSort(zxCtFsContractReview.getSort());
//
//            dbZxCtFsContractReview.setOpinionField1(zxCtFsContractReview.getOpinionField1());
//            dbZxCtFsContractReview.setOpinionField2(zxCtFsContractReview.getOpinionField2());
//            dbZxCtFsContractReview.setOpinionField3(zxCtFsContractReview.getOpinionField3());
//            dbZxCtFsContractReview.setOpinionField4(zxCtFsContractReview.getOpinionField4());
//            dbZxCtFsContractReview.setOpinionField5(zxCtFsContractReview.getOpinionField5());
//            dbZxCtFsContractReview.setOpinionField6(zxCtFsContractReview.getOpinionField6());
//            dbZxCtFsContractReview.setOpinionField7(zxCtFsContractReview.getOpinionField7());
//            dbZxCtFsContractReview.setOpinionField8(zxCtFsContractReview.getOpinionField8());
//            dbZxCtFsContractReview.setOpinionField9(zxCtFsContractReview.getOpinionField9());
//            dbZxCtFsContractReview.setOpinionField10(zxCtFsContractReview.getOpinionField10());
//            dbZxCtFsContractReview.setApih5FlowId(zxCtFsContractReview.getApih5FlowId());
//            dbZxCtFsContractReview.setApih5FlowStatus(zxCtFsContractReview.getApih5FlowStatus());
//            dbZxCtFsContractReview.setApih5FlowNodeStatus(zxCtFsContractReview.getApih5FlowNodeStatus());
//            dbZxCtFsContractReview.setWorkId(zxCtFsContractReview.getWorkId());
////            if(){
////
////            }
//            // ????????????
//            dbZxCtFsContractReview.setIsDeduct(zxCtFsContractReview.getIsDeduct());
//            // ??????
//            dbZxCtFsContractReview.setModifyUserInfo(userKey, realName);
//            if("2".equals(zxCtFsContractReview.getApih5FlowStatus())){
//                flag=  synContract(dbZxCtFsContractReview);
//                if (flag==0){
//                    return repEntity.errorSave();
//                }
//            }
//            flag = zxCtFsContractReviewMapper.updateByPrimaryKey(dbZxCtFsContractReview);
//
//        }
//        // ??????
//        if (flag == 0) {
//            return repEntity.errorSave();
//        } else {
//            return repEntity.ok("sys.data.update",zxCtFsContractReview);
//        }
//    }

}
