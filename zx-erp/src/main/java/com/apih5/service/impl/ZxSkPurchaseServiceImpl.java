package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxErpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkPurchaseMapper;
import com.apih5.mybatis.pojo.ZxSkPurchase;
import com.apih5.service.ZxSkPurchaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkPurchaseService")
public class ZxSkPurchaseServiceImpl implements ZxSkPurchaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkPurchaseMapper zxSkPurchaseMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkPurchaseListByCondition(ZxSkPurchase zxSkPurchase) {
        if (zxSkPurchase == null) {
            zxSkPurchase = new ZxSkPurchase();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkPurchase.setComID("");
            zxSkPurchase.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkPurchase.getOrgID2())){
                zxSkPurchase.setOrgID(zxSkPurchase.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkPurchase.setComID(zxSkPurchase.getOrgID());
            zxSkPurchase.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkPurchase.getOrgID2())){
                zxSkPurchase.setOrgID(zxSkPurchase.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkPurchase.setOrgID(zxSkPurchase.getOrgID());
            if(StrUtil.isNotEmpty(zxSkPurchase.getOrgID2())){
                zxSkPurchase.setOrgID(zxSkPurchase.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkPurchase.getPage(),zxSkPurchase.getLimit());
        // ????????????
        List<ZxSkPurchase> zxSkPurchaseList = zxSkPurchaseMapper.selectByZxSkPurchaseList(zxSkPurchase);

        //??????
        for (ZxSkPurchase zxSkPurchase1 : zxSkPurchaseList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkPurchase1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkPurchase1.setFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSkPurchase> p = new PageInfo<>(zxSkPurchaseList);
        return repEntity.okList(zxSkPurchaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkPurchaseDetail(ZxSkPurchase zxSkPurchase) {
        if (zxSkPurchase == null) {
            zxSkPurchase = new ZxSkPurchase();
        }
        if(StrUtil.isNotEmpty(zxSkPurchase.getId())){
            zxSkPurchase = zxSkPurchaseMapper.selectByPrimaryKey(zxSkPurchase.getId());
        }else if(StrUtil.isNotEmpty(zxSkPurchase.getWorkId())){
            List<ZxSkPurchase> zxSkPurchases = zxSkPurchaseMapper.selectByZxSkPurchaseList(zxSkPurchase);
            if(zxSkPurchases != null && zxSkPurchases.size() >0){
                zxSkPurchase = zxSkPurchases.get(0);
            }
        }
        // ????????????
//        ZxSkPurchase dbZxSkPurchase = zxSkPurchaseMapper.selectByPrimaryKey(zxSkPurchase.getId());
        // ????????????
        if (zxSkPurchase != null) {
            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkPurchase.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkPurchase.setFileList(zxErpFiles);
            return repEntity.ok(zxSkPurchase);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkPurchase(ZxSkPurchase zxSkPurchase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkPurchase.setId(UuidUtil.generate());
        zxSkPurchase.setCreateUserInfo(userKey, realName);
//        //??????????????????: 0:?????????
//        zxSkPurchase.setAuditStatus("0");
        zxSkPurchase.setApih5FlowStatus("-1");
        //????????????
        List<ZxErpFile> fileList = zxSkPurchase.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkPurchase.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSkPurchaseMapper.insert(zxSkPurchase);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkPurchase);
        }
    }

    @Override
    public ResponseEntity updateZxSkPurchase(ZxSkPurchase zxSkPurchase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkPurchase dbZxSkPurchase = zxSkPurchaseMapper.selectByPrimaryKey(zxSkPurchase.getId());
        if (dbZxSkPurchase != null && StrUtil.isNotEmpty(dbZxSkPurchase.getId()) && (StrUtil.equals(zxSkPurchase.getApih5FlowStatus(),"-1")||StrUtil.equals(zxSkPurchase.getApih5FlowStatus(),"0"))) {
            if(StrUtil.equals(zxSkPurchase.getApih5FlowStatus(),"0")){
                // ??????id
                dbZxSkPurchase.setWorkId(zxSkPurchase.getWorkId());
                // ????????????
                dbZxSkPurchase.setApih5FlowStatus(zxSkPurchase.getApih5FlowStatus());
            }
           // ????????????
           dbZxSkPurchase.setBillNo(zxSkPurchase.getBillNo());
           // ????????????
           dbZxSkPurchase.setOrgName(zxSkPurchase.getOrgName());
           // ????????????ID
           dbZxSkPurchase.setOrgID(zxSkPurchase.getOrgID());
           // ????????????(??????)
           dbZxSkPurchase.setPurchaseAmt(zxSkPurchase.getPurchaseAmt());
           // ????????????
           dbZxSkPurchase.setApproval(zxSkPurchase.getApproval());
           // ?????????
           dbZxSkPurchase.setBeginPer(zxSkPurchase.getBeginPer());
           // ????????????
           dbZxSkPurchase.setAuditStatus(zxSkPurchase.getAuditStatus());
           // ????????????ID
           dbZxSkPurchase.setInstProcessId(zxSkPurchase.getInstProcessId());
           // ????????????ID
           dbZxSkPurchase.setWorkitemID(zxSkPurchase.getWorkitemID());
           // ???????????????
           dbZxSkPurchase.setIsFlag(zxSkPurchase.getIsFlag());
           // ???????????????ID
           dbZxSkPurchase.setSendToJuID(zxSkPurchase.getSendToJuID());
           // ??????????????????
           dbZxSkPurchase.setIsFlagZhb(zxSkPurchase.getIsFlagZhb());
           // ??????????????????ID
           dbZxSkPurchase.setSendToZhbID(zxSkPurchase.getSendToZhbID());
           // comID
           dbZxSkPurchase.setComID(zxSkPurchase.getComID());
           // ????????????
           dbZxSkPurchase.setComName(zxSkPurchase.getComName());
           // comOrders
           dbZxSkPurchase.setComOrders(zxSkPurchase.getComOrders());
           // ????????????
           dbZxSkPurchase.setReportDate(zxSkPurchase.getReportDate());
           // appInsHistID
           dbZxSkPurchase.setAppInsHistID(zxSkPurchase.getAppInsHistID());
           // appInsHistIDZhb
           dbZxSkPurchase.setAppInsHistIDZhb(zxSkPurchase.getAppInsHistIDZhb());
           // isReportZhb
           dbZxSkPurchase.setIsReportZhb(zxSkPurchase.getIsReportZhb());
           // isReport
           dbZxSkPurchase.setIsReport(zxSkPurchase.getIsReport());
           // combProp
           dbZxSkPurchase.setCombProp(zxSkPurchase.getCombProp());

           // ??????
           dbZxSkPurchase.setRemarks(zxSkPurchase.getRemarks());
           // ??????
           dbZxSkPurchase.setSort(zxSkPurchase.getSort());
           // ??????
           dbZxSkPurchase.setModifyUserInfo(userKey, realName);
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkPurchase.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkPurchase.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkPurchase.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
           flag = zxSkPurchaseMapper.updateByPrimaryKey(dbZxSkPurchase);
        }else {
            // ??????id
            dbZxSkPurchase.setWorkId(zxSkPurchase.getWorkId());
            // ????????????
            dbZxSkPurchase.setApih5FlowStatus(zxSkPurchase.getApih5FlowStatus());
            //???????????????
            if(StrUtil.equals("opinionField1", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField1(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField1()));
            }
            //???????????????
            if(StrUtil.equals("opinionField2", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField2(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField2()));
            }
            //???????????????
            if(StrUtil.equals("opinionField3", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField3(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField3()));
            }
            //???????????????
            if(StrUtil.equals("opinionField4", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField4(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField4()));
            }
            //???????????????
            if(StrUtil.equals("opinionField5", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField5(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField5()));
            }
            //???????????????
            if(StrUtil.equals("opinionField6", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField6(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField6()));
            }
            //???????????????
            if(StrUtil.equals("opinionField7", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField7(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField7()));
            }
            //???????????????
            if(StrUtil.equals("opinionField8", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField8(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField8()));
            }
            //???????????????
            if(StrUtil.equals("opinionField9", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField9(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField9()));
            }
            //???????????????
            if(StrUtil.equals("opinionField10", zxSkPurchase.getOpinionField(), true)){
                dbZxSkPurchase.setOpinionField10(zxSkPurchase.getOpinionContent(realName, dbZxSkPurchase.getOpinionField10()));
            }
            // ??????
            dbZxSkPurchase.setModifyUserInfo(userKey, realName);
            flag = zxSkPurchaseMapper.updateByPrimaryKey(dbZxSkPurchase);
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkPurchase.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkPurchase.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkPurchase.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkPurchase);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkPurchase(List<ZxSkPurchase> zxSkPurchaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSkPurchaseList != null && zxSkPurchaseList.size() > 0) {
            for (ZxSkPurchase zxSkPurchase : zxSkPurchaseList) {
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkPurchase.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                if(StrUtil.isNotEmpty(zxSkPurchase.getWorkId())) {
                    jsonArr.add(zxSkPurchase.getWorkId());
                }
            }
           ZxSkPurchase zxSkPurchase = new ZxSkPurchase();
           zxSkPurchase.setModifyUserInfo(userKey, realName);
           flag = zxSkPurchaseMapper.batchDeleteUpdateZxSkPurchase(zxSkPurchaseList, zxSkPurchase);
        }
        //????????????????????????
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkPurchaseList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    //???????????????????????????+GR+???????????????????????????????????????????????????????????????????????????001????????????????????????
    @Override
    public ResponseEntity getZxSkPurchaseNo(ZxSkPurchase zxSkPurchase) {
        if(StrUtil.isEmpty(zxSkPurchase.getOrgID())){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(zxSkPurchase.getOrgID());
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        //??????????????????
        String contractNo = zxCtContracts.get(0).getContractNo();

        String str = String.valueOf(zxSkPurchaseMapper.getZxSkPurchaseCount(zxSkPurchase.getOrgID()));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + "-" + "GR" + "-" + str + " ???";
        return repEntity.ok(no);
    }




}
