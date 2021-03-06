package com.apih5.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtOtherManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherManageService")
public class ZxCtOtherManageServiceImpl implements ZxCtOtherManageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleMapper zxSaOtherEquipSettleMapper;

    @Autowired(required = true)
    private ZxCtOtherMapper zxCtOtherMapper;

    @Override
    public ResponseEntity getZxCtOtherManageListByCondition(ZxCtOtherManage zxCtOtherManage) {
        if (zxCtOtherManage == null) {
            zxCtOtherManage = new ZxCtOtherManage();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtOtherManage.setCompanyId("");
            zxCtOtherManage.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxCtOtherManage.setCompanyId(zxCtOtherManage.getOrgId());
            zxCtOtherManage.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxCtOtherManage.setOrgId(zxCtOtherManage.getOrgId());
        }
        // ????????????
        PageHelper.startPage(zxCtOtherManage.getPage(),zxCtOtherManage.getLimit());
        // ????????????
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByZxCtOtherManageList(zxCtOtherManage);
        //????????????
        for (ZxCtOtherManage otherManage : zxCtOtherManageList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(otherManage.getZxCtOtherManageId());
            zxErpFile.setOtherType("0");
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            otherManage.setZxErpFileList(zxErpFiles);

            // ?????????
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setZxCtOtherManageId(otherManage.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(zxCtOtherManageAmtRate);
            otherManage.setZxCtOtherManageAmtRateList(zxCtOtherManageAmtRateList);
        }
        // ??????????????????
        PageInfo<ZxCtOtherManage> p = new PageInfo<>(zxCtOtherManageList);
        return repEntity.okList(zxCtOtherManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherManageDetail(ZxCtOtherManage zxCtOtherManage) {
        if (zxCtOtherManage == null) {
            zxCtOtherManage = new ZxCtOtherManage();
        }
        // ????????????
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
        // ????????????
        if (dbZxCtOtherManage != null) {
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtOtherManage.getZxCtOtherManageId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxCtOtherManage.setZxErpFileList(zxErpFiles);

            // ?????????
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setZxCtOtherManageId(dbZxCtOtherManage.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(zxCtOtherManageAmtRate);
            dbZxCtOtherManage.setZxCtOtherManageAmtRateList(zxCtOtherManageAmtRateList);
            return repEntity.ok(dbZxCtOtherManage);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherManage(ZxCtOtherManage zxCtOtherManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtOtherManage.setZxCtOtherManageId(UuidUtil.generate());
        zxCtOtherManage.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherManageMapper.insert(zxCtOtherManage);

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherManage);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherManage(ZxCtOtherManage zxCtOtherManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
        if (dbZxCtOtherManage != null && StrUtil.isNotEmpty(dbZxCtOtherManage.getZxCtOtherManageId())) {
            // ????????????
            dbZxCtOtherManage.setContractName(zxCtOtherManage.getContractName());
            // ????????????
            dbZxCtOtherManage.setContent(zxCtOtherManage.getContent());
            // ????????????
            dbZxCtOtherManage.setFirstName(zxCtOtherManage.getFirstName());
            // ?????????????????????
            dbZxCtOtherManage.setFirstPrincipalIdCard(zxCtOtherManage.getFirstPrincipalIdCard());
            // ??????????????????
            dbZxCtOtherManage.setFirstUnitTel(zxCtOtherManage.getFirstUnitTel());
            // ??????id
            dbZxCtOtherManage.setSecondId(zxCtOtherManage.getSecondId());
            // ????????????
            dbZxCtOtherManage.setSecondName(zxCtOtherManage.getSecondName());
            // ????????????
            dbZxCtOtherManage.setSecondPrincipal(zxCtOtherManage.getSecondPrincipal());
            // ?????????????????????
            dbZxCtOtherManage.setSecondPrincipalIdCard(zxCtOtherManage.getSecondPrincipalIdCard());
            // ??????(??????)
            dbZxCtOtherManage.setSecondUnitTel(zxCtOtherManage.getSecondUnitTel());
            // ????????????
            dbZxCtOtherManage.setSignatureDate(zxCtOtherManage.getSignatureDate());
            // ??????????????????(??????)
            dbZxCtOtherManage.setContractCost(zxCtOtherManage.getContractCost());
            // ????????????(???)
            dbZxCtOtherManage.setCsTimeLimit(zxCtOtherManage.getCsTimeLimit());
            // ??????????????????
            dbZxCtOtherManage.setPlanStartDate(zxCtOtherManage.getPlanStartDate());
            // ??????????????????
            dbZxCtOtherManage.setActualStartDate(zxCtOtherManage.getActualStartDate());
            // ??????????????????
            dbZxCtOtherManage.setPlanEndDate(zxCtOtherManage.getPlanEndDate());
            // ??????????????????
            dbZxCtOtherManage.setActualEndDate(zxCtOtherManage.getActualEndDate());
            // ????????????
            dbZxCtOtherManage.setConsultative(zxCtOtherManage.getConsultative());
            // ????????????
            dbZxCtOtherManage.setProjectManager(zxCtOtherManage.getProjectManager());
            // ????????????
            dbZxCtOtherManage.setAgent(zxCtOtherManage.getAgent());
            // ??????(??????)
            dbZxCtOtherManage.setSecondPhone(zxCtOtherManage.getSecondPhone());
            // ??????(??????)
            dbZxCtOtherManage.setSecondFax(zxCtOtherManage.getSecondFax());
            // ????????????
            dbZxCtOtherManage.setContractType(zxCtOtherManage.getContractType());
            // ????????????
            dbZxCtOtherManage.setContractCategory(zxCtOtherManage.getContractCategory());
            // ???????????????????????????(??????)
            dbZxCtOtherManage.setAlterContractSum(zxCtOtherManage.getAlterContractSum());
            // ????????????
            dbZxCtOtherManage.setSettleType(zxCtOtherManage.getSettleType());
            // ?????????????????????(??????)
            dbZxCtOtherManage.setContractCostNoTax(zxCtOtherManage.getContractCostNoTax());
            // ????????????(??????)
            dbZxCtOtherManage.setContractCostTax(zxCtOtherManage.getContractCostTax());
            // ??????????????????????????????(??????)
            dbZxCtOtherManage.setAlterContractSumNoTax(zxCtOtherManage.getAlterContractSumNoTax());
            // ?????????????????????(??????)
            dbZxCtOtherManage.setAlterContractSumTax(zxCtOtherManage.getAlterContractSumTax());
            // ????????????
            dbZxCtOtherManage.setIsDeduct(zxCtOtherManage.getIsDeduct());
            // ??????
            dbZxCtOtherManage.setRemark(zxCtOtherManage.getRemark());
            // ????????????
            dbZxCtOtherManage.setOrgName(zxCtOtherManage.getOrgName());
            // ??????
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //??????list
            List<ZxErpFile> fileList = zxCtOtherManage.getZxErpFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherManage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherManage(List<ZxCtOtherManage> zxCtOtherManageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtOtherManageList != null && zxCtOtherManageList.size() > 0) {
            for (ZxCtOtherManage zxCtOtherManage : zxCtOtherManageList) {
                ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
                dbZxSaOtherEquipSettle.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                List<ZxSaOtherEquipSettle> dbZxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle);
                if (dbZxSaOtherEquipSettleList != null && dbZxSaOtherEquipSettleList.size() > 0) {
                    return repEntity.layerMessage("no", "???????????????????????????????????????");
                }
                // ??????????????????????????????
                ZxCtOther delZxCtOther = new ZxCtOther();
                delZxCtOther.setZxCtOtherId(zxCtOtherManage.getZxCtOtherId());
                List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(delZxCtOther);
                if(zxCtOtherList != null && zxCtOtherList.size()>0) {
                    delZxCtOther.setModifyUserInfo(userKey, realName);
                    zxCtOtherMapper.batchDeleteUpdateZxCtOther(zxCtOtherList, delZxCtOther);
                }
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // ?????????
                ZxCtOtherManageAmtRate delZxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
                delZxCtOtherManageAmtRate.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(delZxCtOtherManageAmtRate);
                if(zxCtOtherManageAmtRateList != null && zxCtOtherManageAmtRateList.size()>0) {
                    delZxCtOtherManageAmtRate.setModifyUserInfo(userKey, realName);
                    zxCtOtherManageAmtRateMapper.batchDeleteUpdateZxCtOtherManageAmtRate(zxCtOtherManageAmtRateList, delZxCtOtherManageAmtRate);
                }
            }
           ZxCtOtherManage zxCtOtherManage = new ZxCtOtherManage();
           zxCtOtherManage.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherManageMapper.batchDeleteUpdateZxCtOtherManage(zxCtOtherManageList, zxCtOtherManage);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherManageList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    @Override
    public ResponseEntity zxCtOtherManageRegainExecute(ZxCtOtherManage zxCtOtherManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
        if (dbZxCtOtherManage != null && StrUtil.isNotEmpty(dbZxCtOtherManage.getZxCtOtherId())) {
//            if ("3".equals(dbZxCtOtherManage.getContractStatus())) {
//                dbZxCtOtherManage.setContractStatus("1");
//            } else if ("1".equals(dbZxCtOtherManage.getContractStatus())) {
//                dbZxCtOtherManage.setContractStatus("3");
//            }
            dbZxCtOtherManage.setContractStatus("3".equals(dbZxCtOtherManage.getContractStatus())? "1" : "3");
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherManage);
        }
    }

    @Override
    public void exportZxCtOtherManage(ZxCtOtherManage zxCtOtherManage, HttpServletResponse response) {
        if (zxCtOtherManage == null) {
            zxCtOtherManage = new ZxCtOtherManage();
        }
        // ????????????
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByZxCtOtherManageList(zxCtOtherManage);
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("????????????",
                "????????????",
                "????????????",
                "????????????",
                "????????????",
                "????????????",
                "??????????????????",
                "??????????????????????????????",
                "???????????????????????????????????????",
                "??????????????????",
                "????????????",
                "????????????",
                "????????????",
                "????????????",
                "??????"
        );
        rowsList.add(row1);

        // ??????????????????????????????????????????
        if (zxCtOtherManageList != null && zxCtOtherManageList.size() > 0) {
            for (ZxCtOtherManage dbZxCtOtherManage : zxCtOtherManageList) {
                rowsList.add(CollUtil.newArrayList(dbZxCtOtherManage.getContractNo(),
                        dbZxCtOtherManage.getContractName(),
                        dbZxCtOtherManage.getOrgName(),
                        dbZxCtOtherManage.getContractType(),
                        dbZxCtOtherManage.getFirstName(),
                        dbZxCtOtherManage.getSecondName(),
                        dbZxCtOtherManage.getAuditStatus(),
                        dbZxCtOtherManage.getContractCost(),
                        dbZxCtOtherManage.getAlterContractSum(),
                        dbZxCtOtherManage.getTotalSettleAmount(),
                        dbZxCtOtherManage.getIsDeduct(),
                        dbZxCtOtherManage.getSettleType(),
                        dbZxCtOtherManage.getConsultative(),
                        dbZxCtOtherManage.getContractCategory(),
                        dbZxCtOtherManage.getRemark()
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
}
