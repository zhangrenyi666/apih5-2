package com.apih5.service.impl;

import java.io.File;
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
import cn.hutool.core.io.FileUtil;
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
import flex.messaging.util.URLEncoder;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtOtherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherService")
public class ZxCtOtherServiceImpl implements ZxCtOtherService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherMapper zxCtOtherMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateServiceImpl zxCtOtherManageAmtRateServiceImpl;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Override
    public ResponseEntity getZxCtOtherListByCondition(ZxCtOther zxCtOther) {
        if (zxCtOther == null) {
            zxCtOther = new ZxCtOther();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtOther.setCompanyId("");
            zxCtOther.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxCtOther.setCompanyId(zxCtOther.getOrgId());
            zxCtOther.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxCtOther.setOrgId(zxCtOther.getOrgId());
        }
        // ????????????
        PageHelper.startPage(zxCtOther.getPage(),zxCtOther.getLimit());
        // ????????????
        List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(zxCtOther);

        //????????????
        for (ZxCtOther other : zxCtOtherList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(other.getZxCtOtherId());
            zxErpFile.setOtherType("0");
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            other.setZxErpFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxCtOther> p = new PageInfo<>(zxCtOtherList);
        return repEntity.okList(zxCtOtherList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherDetail(ZxCtOther zxCtOther) {
        if (zxCtOther == null) {
            zxCtOther = new ZxCtOther();
        }
        ZxCtOther dbZxCtOther = new ZxCtOther();
        if(StrUtil.isNotEmpty(zxCtOther.getWorkId())) {
            ZxCtOther zxCtOther1 = new ZxCtOther();
            zxCtOther1.setWorkId(zxCtOther.getWorkId());
            List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(zxCtOther1);
            if(zxCtOtherList != null && zxCtOtherList.size() >0) {
                dbZxCtOther = zxCtOtherList.get(0);
            }
        }else {
            // ????????????
            dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        }
        // ????????????
        if (dbZxCtOther != null) {
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtOther.getZxCtOtherId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            List<ZxErpFile> fj = new ArrayList<>();
            List<ZxErpFile> zw = new ArrayList<>();
            zxErpFiles.forEach(f -> {
                // 0 ?????? 1 ??????
                if ("0".equals(f.getOtherType())) {
                    fj.add(f);
                } else if ("1".equals(f.getOtherType())) {
                    zw.add(f);
                }
            });
            dbZxCtOther.setZxErpFileList(fj);
            dbZxCtOther.setZxErpMainFileList(zw);
            return repEntity.ok(dbZxCtOther);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtOther(ZxCtOther zxCtOther) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // ????????????????????????
        if (StrUtil.isEmpty(zxCtOther.getContractCode())) {
            return repEntity.layerMessage("no", "????????????????????????,?????????????????????contractNo???");
        }
        zxCtOther.setContractCode(zxCtOther.getContractCode());
        // ????????????id ?????????id??????
        zxCtOther.setOrgId(zxCtOther.getFirstId());
        // ?????????????????? ?????????????????????
        zxCtOther.setOrgName(zxCtOther.getFirstName());
        // ??????????????????????????????
        zxCtOther.setApih5FlowStatus("-1");
        // ????????????????????????????????????
        zxCtOther.setBeginPer(realName);
        zxCtOther.setZxCtOtherId(UuidUtil.generate());
        zxCtOther.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherMapper.insert(zxCtOther);
        //????????????
        List<ZxErpFile> fileList = zxCtOther.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOther);
        }
    }

    @Override
    public ResponseEntity updateZxCtOther(ZxCtOther zxCtOther) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOther dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
            // ????????????
            dbZxCtOther.setContractName(zxCtOther.getContractName());
            // ??????id
            dbZxCtOther.setSecondId(zxCtOther.getSecondId());
            // ????????????
            dbZxCtOther.setSecondName(zxCtOther.getSecondName());
            // ???????????????
            dbZxCtOther.setAgent(zxCtOther.getAgent());
            // ????????????
            dbZxCtOther.setCsTimeLimit(zxCtOther.getCsTimeLimit());
            // ????????????
            dbZxCtOther.setContent(zxCtOther.getContent());
            // ????????????
            dbZxCtOther.setContractCategory(zxCtOther.getContractCategory());
            // ????????????
            dbZxCtOther.setIsDeduct(zxCtOther.getIsDeduct());
            // ??????
            dbZxCtOther.setRemark(zxCtOther.getRemark());
            // workId
            dbZxCtOther.setWorkId(zxCtOther.getWorkId());
            // ????????????
            dbZxCtOther.setApih5FlowStatus(zxCtOther.getApih5FlowStatus());
            // ??????????????????
            dbZxCtOther.setApih5FlowNodeStatus(zxCtOther.getApih5FlowNodeStatus());
            // ??????id
            dbZxCtOther.setApih5FlowId(zxCtOther.getApih5FlowId());
            //???????????????
            if(StrUtil.equals("opinionField1", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField1(getOpinionContent(realName, dbZxCtOther.getOpinionField1(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField2(getOpinionContent(realName, dbZxCtOther.getOpinionField2(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField3(getOpinionContent(realName, dbZxCtOther.getOpinionField3(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField4(getOpinionContent(realName, dbZxCtOther.getOpinionField4(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField5(getOpinionContent(realName, dbZxCtOther.getOpinionField5(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField6(getOpinionContent(realName, dbZxCtOther.getOpinionField6(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField7(getOpinionContent(realName, dbZxCtOther.getOpinionField7(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField8(getOpinionContent(realName, dbZxCtOther.getOpinionField8(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField9(getOpinionContent(realName, dbZxCtOther.getOpinionField9(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField10(getOpinionContent(realName, dbZxCtOther.getOpinionField10(), zxCtOther.getOpinionContent()));
            }
            // ??????
            dbZxCtOther.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherMapper.updateByPrimaryKey(dbZxCtOther);

            // ????????????????????????????????????????????????
            ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
            dbZxCtOtherWorks.setZxCtOtherId(dbZxCtOther.getZxCtOtherId());
            List<ZxCtOtherWorks> dbZxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
            if (dbZxCtOtherWorksList != null && dbZxCtOtherWorksList.size() > 0) {
                for(ZxCtOtherWorks zxCtOtherWorks : dbZxCtOtherWorksList) {
                    zxCtOtherWorks.setIsDeduct(dbZxCtOther.getIsDeduct());
                    zxCtOtherWorksMapper.updateByPrimaryKey(zxCtOtherWorks);
                }
            }
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtOther.getZxCtOtherId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //???????????? 0?????? 1??????
            //??????list
            List<ZxErpFile> fileList = zxCtOther.getZxErpFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                    zxErpFile.setOtherType("0");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            List<ZxErpFile> zhengWenList = zxCtOther.getZxErpMainFileList();
            if(zhengWenList != null && zhengWenList.size()>0) {
                for(ZxErpFile zxErpFile : zhengWenList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            // ????????????
            if(StrUtil.equals(zxCtOther.getApih5FlowStatus(), "2")) {
                this.zxCtOtherReviewApply(zxCtOther);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOther);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOther(List<ZxCtOther> zxCtOtherList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxCtOtherList != null && zxCtOtherList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ZxCtOther zxCtOther : zxCtOtherList) {
                //????????????
                ZxCtOtherWorks delZxCtOtherWorks = new ZxCtOtherWorks();
                delZxCtOtherWorks.setZxCtOtherId(zxCtOther.getZxCtOtherId());
                List<ZxCtOtherWorks> delWorkList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(delZxCtOtherWorks);
                if(delWorkList != null && delWorkList.size() > 0) {
                    delZxCtOtherWorks.setModifyUserInfo(userKey, realName);
                    zxCtOtherWorksMapper.batchDeleteUpdateZxCtOtherWorks(delWorkList, delZxCtOtherWorks);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                if(StrUtil.isNotEmpty(zxCtOther.getWorkId())) {
                    jsonArray.add(zxCtOther.getWorkId());
                }
            }
            ZxCtOther zxCtOther = new ZxCtOther();
            zxCtOther.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherMapper.batchDeleteUpdateZxCtOther(zxCtOtherList, zxCtOther);
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
            return repEntity.ok("sys.data.delete",zxCtOtherList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    @Override
    public ResponseEntity zxCtOtherReviewApplyCheck(ZxCtOther zxCtOther) {
        ZxCtOther dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
            if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????
                ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
                dbZxCtOtherWorks.setZxCtOtherId(dbZxCtOther.getZxCtOtherId());
                List<ZxCtOtherWorks> dbZxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
                if (dbZxCtOtherWorksList == null || dbZxCtOtherWorksList.size() == 0) {
                    return repEntity.layerMessage("no", "????????????????????????????????????");
                }
            }
        }
        return null;
    }

    @Override
    public ResponseEntity zxCtOtherReviewApply(ZxCtOther zxCtOther) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOther dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
            // ???????????????????????????????????????????????????
            dbZxCtOther.setApih5FlowStatus("2");
            dbZxCtOther.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherMapper.updateByPrimaryKey(dbZxCtOther);
            //?????????????????????????????????????????????????????????
            ZxCtOtherManage zxCtOtherManage = new ZxCtOtherManage();
            BeanUtil.copyProperties(dbZxCtOther, zxCtOtherManage);
            // ??????????????????ID??????????????????fromApplyId???????????????????????????zxCtOtherId??????????????????????????????ID ??????zxCtOtherId
            zxCtOtherManage.setZxCtOtherId(dbZxCtOther.getZxCtOtherId());
            // ????????????  ????????????????????????
            zxCtOtherManage.setSignatureDate(dbZxCtOther.getModifyTime());
            zxCtOtherManage.setFirstId(dbZxCtOther.getFirstId());
            zxCtOtherManage.setOrgId(dbZxCtOther.getFirstId());
            // ???????????? ??????????????????????????????????????????agent?????????copyProperties?????????????????????????????????????????????
            zxCtOtherManage.setAgent("");
            // ???????????? ???????????????????????????????????????
            zxCtOtherManage.setSecondPrincipal(dbZxCtOther.getAgent());
            // ??????????????????
            zxCtOtherManage.setAuditStatus("auditPassed");
            // ???????????????????????????
            zxCtOtherManage.setContractStatus("1");
            // ?????????????????????????????????
            zxCtOtherManage.setSettleType("???????????????");
            // ???????????????????????????0
            zxCtOtherManage.setTotalSettleAmount(new BigDecimal(0));
            zxCtOtherManage.setZxCtOtherManageId(UuidUtil.generate());
            zxCtOtherManage.setCreateUserInfo(userKey, realName);
            flag = zxCtOtherManageMapper.insert(zxCtOtherManage);

            //??????????????????????????????????????????????????????????????????????????????zxCtOtherManageId ????????????????????????????????????????????????zxCtOtherManageId
            ZxCtOtherWorks otherWorks = new ZxCtOtherWorks();
            otherWorks.setZxCtOtherId(zxCtOther.getZxCtOtherId());
            List<ZxCtOtherWorks> workList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(otherWorks);
            if (workList != null && workList.size() > 0) {
                for (ZxCtOtherWorks work : workList) {
                    work.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                    work.setModifyUserInfo(userKey, realName);
                    flag = zxCtOtherWorksMapper.updateByPrimaryKey(work);
                }
            }

            // ??????????????????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                for (ZxErpFile file : zxErpFiles) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
                    file.setModifyUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }

            // ???????????????????????????????????????????????????????????????
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = (List<ZxCtOtherManageAmtRate>) zxCtOtherManageAmtRateServiceImpl.getZxCtOtherManageAmtRateByContractId(zxCtOtherManageAmtRate).getData();
            if (zxCtOtherManageAmtRateList != null && zxCtOtherManageAmtRateList.size() > 0) {
                for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate1 : zxCtOtherManageAmtRateList) {
                    BeanUtil.copyProperties(dbZxCtOtherManage, zxCtOtherManageAmtRate1);
                    zxCtOtherManageAmtRate1.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                    zxCtOtherManageAmtRate1.setAllowDelete("N");
                    zxCtOtherManageAmtRate1.setUseCount(new BigDecimal(0));
                    zxCtOtherManageAmtRate1.setZxCtOtherManageAmtRateId(UuidUtil.generate());
                    zxCtOtherManageAmtRate1.setCreateUserInfo(userKey, realName);
                    flag = zxCtOtherManageAmtRateMapper.insert(zxCtOtherManageAmtRate1);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOther);
        }
    }

    @Override
    public ResponseEntity getZxCtOtherContractNo(ZxCtOther zxCtOther) {
        if (StrUtil.isEmpty(zxCtOther.getFirstId())) {
            return repEntity.layerMessage("no", "???????????????????????????");
        }
        if (StrUtil.isEmpty(zxCtOther.getContractCategory())) {
            return repEntity.layerMessage("no", "???????????????????????????");
        }
        // ????????????
        String firstNo = zxCtOther.getFirstId();
        // ????????????
        String contractCategory = zxCtOther.getContractCategory();
        ZxCtOther dbZxCtOther = new ZxCtOther();
        // ???????????? ?????????????????????contractNo ?????????contractNo??????FirstId?????????
        dbZxCtOther.setContractCode(firstNo);
        dbZxCtOther.setContractCategory(contractCategory);
        // ??????????????????
        List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByContractCodeAndCategory(dbZxCtOther);
        String initSerialNumber = String.format("%03d", zxCtOtherList.size()+1);
        // ????????????
        ZxCtOther zxCtOther1 = new ZxCtOther();
        zxCtOther1.setContractNo(firstNo+"-"+contractCategory+"-"+initSerialNumber);
        return repEntity.ok(zxCtOther1);
    }

    @Override
    public void exportZxCtOther(ZxCtOther zxCtOther, HttpServletResponse response) {
        if (zxCtOther == null) {
            zxCtOther = new ZxCtOther();
        }
        // ????????????
        List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(zxCtOther);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("????????????",
                "????????????",
                "????????????",
                "????????????",
                "??????????????????",
                "???????????????",
                "??????????????????????????????",
                "????????????",
                "????????????",
                "????????????",
                "?????????",
                "????????????"
        );
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxCtOtherList != null && zxCtOtherList.size() > 0) {
            for (ZxCtOther dbZxCtOther : zxCtOtherList) {
                rowsList.add(CollUtil.newArrayList(dbZxCtOther.getContractNo(),
                        dbZxCtOther.getContractName(),
                        dbZxCtOther.getFirstName(),
                        dbZxCtOther.getSecondName(),
                        dbZxCtOther.getSecondOrgType(),
                        dbZxCtOther.getAgent(),
                        dbZxCtOther.getContractCost(),
                        dbZxCtOther.getIsDeduct(),
                        dbZxCtOther.getCsTimeLimit(),
                        dbZxCtOther.getContractCategory(),
                        dbZxCtOther.getBeginPer(),
                        dbZxCtOther.getApih5FlowStatus()
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
                        "attachment; filename=\"" + new String("????????????????????????".getBytes("utf-8"), "iso-8859-1") + "\"");
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
    public void downLoadZxCtOtherFile(ZxCtOther zxCtOther, HttpServletResponse response) {
        if (zxCtOther.getZxErpFileList() != null && zxCtOther.getZxErpFileList().size() > 0) {
            for (ZxErpFile zxErpFile : zxCtOther.getZxErpFileList()) {
                // ???????????????????????????
                String fileName = zxErpFile.getName();
                if(StringUtil.isNotEmpty(fileName)){
                    //??????????????????
                    String path = Apih5Properties.getFilePath() + zxErpFile.getRelativeUrl();
                    File file = new File(path);
                    //????????????
                    if(file.exists()) {
                        // ????????????????????????
                        response.setContentType("application/octet-stream");
                        // out???OutputStream??????????????????????????????
                        ServletOutputStream outputStream = null;
                        try {
                            // ?????????????????????
                            outputStream = response.getOutputStream();

                            //????????????????????????????????????
                            byte[] bytes = FileUtil.readBytes(file);

                            // ????????????????????????????????????????????????????????????????????????????????????
                            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

                            // ?????????????????????????????????
                            outputStream.write(bytes);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            // ????????????????????????Servlet???
                            if (outputStream != null) {
                                // ???????????????
                                IoUtil.close(outputStream);
                            }
                        }
                    }
                }

            }
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

}
