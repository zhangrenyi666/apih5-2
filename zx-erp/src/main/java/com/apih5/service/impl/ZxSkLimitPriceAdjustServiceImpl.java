package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkLimitPriceAdjustMapper;
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjust;
import com.apih5.service.ZxSkLimitPriceAdjustService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkLimitPriceAdjustService")
public class ZxSkLimitPriceAdjustServiceImpl implements ZxSkLimitPriceAdjustService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkLimitPriceAdjustMapper zxSkLimitPriceAdjustMapper;

    @Autowired(required = true)
    private ZxSkLimitPriceAdjustItemMapper zxSkLimitPriceAdjustItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSkResCategoryMaterialsMapper zxSkResCategoryMaterialsMapper;

    @Autowired(required = true)
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkLimitPriceMapper zxSkLimitPriceMap;

    @Autowired(required = true)
    private ZxSkLimitPriceItemMapper zxSkLimitPriceItemMapper;

    @Override
    public ResponseEntity getZxSkLimitPriceAdjustListByCondition(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        if (zxSkLimitPriceAdjust == null) {
            zxSkLimitPriceAdjust = new ZxSkLimitPriceAdjust();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkLimitPriceAdjust.setOrgID("");
            zxSkLimitPriceAdjust.setProjectId("");
            if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getOrgID2())){
                zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkLimitPriceAdjust.setOrgID(zxSkLimitPriceAdjust.getProjectId());
            zxSkLimitPriceAdjust.setProjectId("");
            if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getOrgID2())){
                zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getProjectId());
            if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getOrgID2())){
                zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getOrgID2());
            }
        }
        if(zxSkLimitPriceAdjust.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceAdjust.getPeriodDate());
            if(StrUtil.equals(result.substring(4),"12")){
                zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"????????????");
            }else {
                zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"????????????");
            }
        }
        // ????????????
        PageHelper.startPage(zxSkLimitPriceAdjust.getPage(),zxSkLimitPriceAdjust.getLimit());
        // ????????????
        List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjustList = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust);
        //????????????
        for (ZxSkLimitPriceAdjust IeskLimitPriceAdjust : zxSkLimitPriceAdjustList) {
            //??????
            if(IeskLimitPriceAdjust.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = IeskLimitPriceAdjust.getPeriod().toString().substring(0, 4);
                    String substring = IeskLimitPriceAdjust.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"????????????")){
                        year = year+"01";
                    }else {
                        year = year+"12";
                    }
                    date = simpleDateFormat.parse(year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                IeskLimitPriceAdjust.setPeriodDate(date);
            }
            ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
            zxSkLimitPriceAdjustItem.setMainID(IeskLimitPriceAdjust.getId());
            List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItems = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
            for (ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem1 : zxSkLimitPriceAdjustItems) {
                String name = "";
                String[] id = zxSkLimitPriceAdjustItem1.getResourceId().split(",");
                for (String s : id) {
                    ZxSkResCategoryMaterials zxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(s);
                    if(zxSkResCategoryMaterials ==null){
                        ZxSkResourceMaterials zxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(s);
                        if(zxSkResourceMaterials != null && StrUtil.isNotEmpty(zxSkResourceMaterials.getResName())){
                            name += "-"+ zxSkResourceMaterials.getResName();
                        }
                    }else {
                        name += zxSkResCategoryMaterials.getCatName();
                    }
                }
                zxSkLimitPriceAdjustItem1.setJoinName(name);
            }
            IeskLimitPriceAdjust.setZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItems);
        }
        //????????????
        for (ZxSkLimitPriceAdjust zxSkLimitPriceAdjust1 : zxSkLimitPriceAdjustList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPriceAdjust1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPriceAdjust1.setFileList(zxErpFiles);
        }

        // ??????????????????
        PageInfo<ZxSkLimitPriceAdjust> p = new PageInfo<>(zxSkLimitPriceAdjustList);

        return repEntity.okList(zxSkLimitPriceAdjustList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkLimitPriceAdjustDetails(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        if (zxSkLimitPriceAdjust == null) {
            zxSkLimitPriceAdjust = new ZxSkLimitPriceAdjust();
        }
        if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getId())){
            zxSkLimitPriceAdjust = zxSkLimitPriceAdjustMapper.selectByPrimaryKey(zxSkLimitPriceAdjust.getId());
        }else if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getWorkId())){
            List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjusts = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust);
            if(zxSkLimitPriceAdjusts != null && zxSkLimitPriceAdjusts.size() >0){
                zxSkLimitPriceAdjust = zxSkLimitPriceAdjusts.get(0);
            }
        }
        // ????????????
//        ZxSkLimitPriceAdjust dbZxSkLimitPriceAdjust = zxSkLimitPriceAdjustMapper.selectByPrimaryKey(zxSkLimitPriceAdjust.getId());
        // ????????????
        if (zxSkLimitPriceAdjust != null) {
            //??????
            if(zxSkLimitPriceAdjust.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = zxSkLimitPriceAdjust.getPeriod().toString().substring(0, 4);
                    String substring = zxSkLimitPriceAdjust.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"????????????")){
                        year = year+"01";
                    }else {
                        year = year+"12";
                    }
                    date = simpleDateFormat.parse(year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zxSkLimitPriceAdjust.setPeriodDate(date);
            }
            ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
            zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
            List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItems = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
            for (ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem1 : zxSkLimitPriceAdjustItems) {
                String name = "";
                String[] id = zxSkLimitPriceAdjustItem1.getResourceId().split(",");
                for (String s : id) {
                    ZxSkResCategoryMaterials zxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(s);
                    if(zxSkResCategoryMaterials ==null){
                        ZxSkResourceMaterials zxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(s);
                        name += "-"+ zxSkResourceMaterials.getResName();
                    }else {
                        name += zxSkResCategoryMaterials.getCatName();
                    }
                }
                zxSkLimitPriceAdjustItem1.setJoinName(name);
            }
            zxSkLimitPriceAdjust.setZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItems);

            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPriceAdjust.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPriceAdjust.setFileList(zxErpFiles);
            return repEntity.ok(zxSkLimitPriceAdjust);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkLimitPriceAdjust(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkLimitPriceAdjust.setId(UuidUtil.generate());
        zxSkLimitPriceAdjust.setCreateUserInfo(userKey, realName);
        String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceAdjust.getPeriodDate());
        if(StrUtil.equals(result.substring(4),"12")){
            zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"????????????");
        }else {
            zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"????????????");
        }
        //??????-1
        zxSkLimitPriceAdjust.setApih5FlowStatus("-1");
        int flag = zxSkLimitPriceAdjustMapper.insert(zxSkLimitPriceAdjust);
        //????????????
        List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList = zxSkLimitPriceAdjust.getZxSkLimitPriceAdjustItemList();
        if(zxSkLimitPriceAdjustItemList != null && zxSkLimitPriceAdjustItemList.size()>0) {
            for (ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem : zxSkLimitPriceAdjustItemList) {
                //?????????????????????1
//                zxSkLimitPriceAdjustItem.setAdjustType("1");
                zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
                zxSkLimitPriceAdjustItem.setId((UuidUtil.generate()));
                zxSkLimitPriceAdjustItem.setCreateUserInfo(userKey, realName);
                zxSkLimitPriceAdjustItemMapper.insert(zxSkLimitPriceAdjustItem);
            }
        }
        //????????????
        List<ZxErpFile> fileList = zxSkLimitPriceAdjust.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkLimitPriceAdjust.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkLimitPriceAdjust);
        }
    }

    @Override
    public ResponseEntity updateZxSkLimitPriceAdjust(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkLimitPriceAdjust dbzxSkLimitPriceAdjust = zxSkLimitPriceAdjustMapper.selectByPrimaryKey(zxSkLimitPriceAdjust.getId());
        if (dbzxSkLimitPriceAdjust != null && StrUtil.isNotEmpty(dbzxSkLimitPriceAdjust.getId()) && (StrUtil.equals(zxSkLimitPriceAdjust.getApih5FlowStatus(),"-1")||StrUtil.equals(zxSkLimitPriceAdjust.getApih5FlowStatus(),"0"))) {
            if(StrUtil.equals(zxSkLimitPriceAdjust.getApih5FlowStatus(),"0")){
                // ??????id
                dbzxSkLimitPriceAdjust.setWorkId(zxSkLimitPriceAdjust.getWorkId());
                // ????????????
                dbzxSkLimitPriceAdjust.setApih5FlowStatus(zxSkLimitPriceAdjust.getApih5FlowStatus());
            }
           // ????????????
           dbzxSkLimitPriceAdjust.setAdjustNo(zxSkLimitPriceAdjust.getAdjustNo());
           // ????????????
           dbzxSkLimitPriceAdjust.setLimitNo(zxSkLimitPriceAdjust.getLimitNo());
           // ????????????ID
           dbzxSkLimitPriceAdjust.setLimitPriceId(zxSkLimitPriceAdjust.getLimitPriceId());
           // ??????ID
           dbzxSkLimitPriceAdjust.setOrgID(zxSkLimitPriceAdjust.getOrgID());
           // ????????????
           dbzxSkLimitPriceAdjust.setOrgName(zxSkLimitPriceAdjust.getOrgName());
           // ??????ID
           dbzxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getProjectId());
           // ????????????
           dbzxSkLimitPriceAdjust.setProjectName(zxSkLimitPriceAdjust.getProjectName());
           // ????????????
           dbzxSkLimitPriceAdjust.setProvince(zxSkLimitPriceAdjust.getProvince());
           // ????????????
           dbzxSkLimitPriceAdjust.setProjectType(zxSkLimitPriceAdjust.getProjectType());
           // ????????????
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceAdjust.getPeriodDate());
            if(StrUtil.equals(result.substring(4),"12")){
                dbzxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"????????????");
            }else {
                dbzxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"????????????");
            }
           // ?????????
           dbzxSkLimitPriceAdjust.setPerpare(zxSkLimitPriceAdjust.getPerpare());
           // ????????????
           dbzxSkLimitPriceAdjust.setPrepareDate(zxSkLimitPriceAdjust.getPrepareDate());
           // ??????????????????
           dbzxSkLimitPriceAdjust.setIsThisIn(zxSkLimitPriceAdjust.getIsThisIn());
           // comID
           dbzxSkLimitPriceAdjust.setComId(zxSkLimitPriceAdjust.getComId());
           // ????????????
           dbzxSkLimitPriceAdjust.setComName(zxSkLimitPriceAdjust.getComName());
           // comOrders
           dbzxSkLimitPriceAdjust.setComOrders(zxSkLimitPriceAdjust.getComOrders());
           // editTime
           dbzxSkLimitPriceAdjust.setEditTime(zxSkLimitPriceAdjust.getEditTime());
           // ????????????
           dbzxSkLimitPriceAdjust.setStatus(zxSkLimitPriceAdjust.getStatus());
           // ?????????
           dbzxSkLimitPriceAdjust.setSerialNumber(zxSkLimitPriceAdjust.getSerialNumber());
           // ?????????
           dbzxSkLimitPriceAdjust.setBeginPer(zxSkLimitPriceAdjust.getBeginPer());
           // ????????????ID
           dbzxSkLimitPriceAdjust.setInstProcessId(zxSkLimitPriceAdjust.getInstProcessId());
           // ????????????ID
           dbzxSkLimitPriceAdjust.setWorkitemID(zxSkLimitPriceAdjust.getWorkitemID());
           // ????????????
           dbzxSkLimitPriceAdjust.setLimitType(zxSkLimitPriceAdjust.getLimitType());

           // ??????
           dbzxSkLimitPriceAdjust.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceAdjustMapper.updateByPrimaryKey(dbzxSkLimitPriceAdjust);
            //???????????????
            ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
            zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
            List<ZxSkLimitPriceAdjustItem> IeskLimitPriceAdjustItems = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
            if(IeskLimitPriceAdjustItems != null && IeskLimitPriceAdjustItems.size()>0) {
                zxSkLimitPriceAdjustItem.setModifyUserInfo(userKey, realName);
                zxSkLimitPriceAdjustItemMapper.batchDeleteUpdateZxSkLimitPriceAdjustItem(IeskLimitPriceAdjustItems, zxSkLimitPriceAdjustItem);
            }
            //??????list
            List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList = zxSkLimitPriceAdjust.getZxSkLimitPriceAdjustItemList();
            if(zxSkLimitPriceAdjustItemList != null && zxSkLimitPriceAdjustItemList.size()>0) {
                for (ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem1 : zxSkLimitPriceAdjustItemList) {
                    zxSkLimitPriceAdjustItem1.setMainID(zxSkLimitPriceAdjust.getId());
                    zxSkLimitPriceAdjustItem1.setId((UuidUtil.generate()));
                    zxSkLimitPriceAdjustItem1.setCreateUserInfo(userKey, realName);
//                    zxSkLimitPriceAdjustItem1.setAdjustType("1");
                    zxSkLimitPriceAdjustItemMapper.insert(zxSkLimitPriceAdjustItem1);
                }
            }
            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkLimitPriceAdjust.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkLimitPriceAdjust.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkLimitPriceAdjust.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }else {
            // ??????id
            dbzxSkLimitPriceAdjust.setWorkId(zxSkLimitPriceAdjust.getWorkId());
            // ????????????
            dbzxSkLimitPriceAdjust.setApih5FlowStatus(zxSkLimitPriceAdjust.getApih5FlowStatus());
            //???????????????
            if(StrUtil.equals("opinionField1", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField1(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField1()));
            }
            //???????????????
            if(StrUtil.equals("opinionField2", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField2(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField2()));
            }
            //???????????????
            if(StrUtil.equals("opinionField3", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField3(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField3()));
            }
            //???????????????
            if(StrUtil.equals("opinionField4", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField4(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField4()));
            }
            //???????????????
            if(StrUtil.equals("opinionField5", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField5(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField5()));
            }
            //???????????????
            if(StrUtil.equals("opinionField6", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField6(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField6()));
            }
            //???????????????
            if(StrUtil.equals("opinionField7", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField7(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField7()));
            }
            //???????????????
            if(StrUtil.equals("opinionField8", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField8(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField8()));
            }
            //???????????????
            if(StrUtil.equals("opinionField9", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField9(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField9()));
            }
            //???????????????
            if(StrUtil.equals("opinionField10", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField10(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField10()));
            }
            // ??????
            dbzxSkLimitPriceAdjust.setModifyUserInfo(userKey, realName);
            flag = zxSkLimitPriceAdjustMapper.updateByPrimaryKey(dbzxSkLimitPriceAdjust);

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkLimitPriceAdjust.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //??????list
            List<ZxErpFile> fileList = zxSkLimitPriceAdjust.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkLimitPriceAdjust.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        //todo:????????????????????????????????????????????????
        //?????????????????????????????????????????????
        //zxSkLimitPriceAdjust
        if(StrUtil.equals(zxSkLimitPriceAdjust.getApih5FlowStatus(),"2")){
            //????????????????????????
            String limitNo = zxSkLimitPriceAdjust.getLimitNo();
            //???????????????????????????????????????
            ZxSkLimitPrice zxSkLimitPrice = new ZxSkLimitPrice();
            zxSkLimitPrice.setLimitNo(limitNo);
            List<ZxSkLimitPrice> zxSkLimitPrices = zxSkLimitPriceMap.selectByZxSkLimitPriceList(zxSkLimitPrice);
            if(zxSkLimitPrices!=null && zxSkLimitPrices.size()>0){
                //????????????????????????,?????????????????????????????????
                ZxSkLimitPrice dbzxSkLimitPrice = zxSkLimitPrices.get(0);
                //????????????
                List<ZxSkLimitPriceItem>  zxSkLimitPriceItemList = new ArrayList<>();
                List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList = zxSkLimitPriceAdjust.getZxSkLimitPriceAdjustItemList();
                for (ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem : zxSkLimitPriceAdjustItemList) {
                    ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
                    zxSkLimitPriceItem.setResourceId(zxSkLimitPriceAdjustItem.getResourceId());
                    zxSkLimitPriceItem.setResourceName(zxSkLimitPriceAdjustItem.getResourceName());
                    zxSkLimitPriceItem.setResourceNo(zxSkLimitPriceAdjustItem.getResourceNo());
                    zxSkLimitPriceItem.setWorkName(zxSkLimitPriceAdjustItem.getWorkName());
                    zxSkLimitPriceItem.setWorkId(zxSkLimitPriceAdjustItem.getWorkId());
                    zxSkLimitPriceItem.setSpec(zxSkLimitPriceAdjustItem.getSpec());
                    zxSkLimitPriceItem.setUnit(zxSkLimitPriceAdjustItem.getUnit());
                    zxSkLimitPriceItem.setPrice(zxSkLimitPriceAdjustItem.getAdjustPrice());
                    zxSkLimitPriceItem.setWorkNo(zxSkLimitPriceAdjustItem.getWorkNo());
                    zxSkLimitPriceItemList.add(zxSkLimitPriceItem);
                }
                dbzxSkLimitPrice.setZxSkLimitPriceItemList(zxSkLimitPriceItemList);

                //??????????????????
                //???????????????
                ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
                zxSkLimitPriceItem.setMasterId(dbzxSkLimitPrice.getId());
                List<ZxSkLimitPriceItem> zxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
                if(zxSkLimitPriceItems != null && zxSkLimitPriceItems.size()>0) {
                    zxSkLimitPriceItem.setModifyUserInfo(userKey, realName);
                    zxSkLimitPriceItemMapper.batchDeleteUpdateZxSkLimitPriceItem(zxSkLimitPriceItems, zxSkLimitPriceItem);
                }
                //??????list
                if(zxSkLimitPriceItemList != null && zxSkLimitPriceItemList.size()>0) {
                    for(ZxSkLimitPriceItem zxSkLimitPriceItem1 : zxSkLimitPriceItemList) {
                        zxSkLimitPriceItem1.setId(UuidUtil.generate());
                        zxSkLimitPriceItem1.setMasterId(dbzxSkLimitPrice.getId());
                        zxSkLimitPriceItem1.setCreateUserInfo(userKey, realName);
                        zxSkLimitPriceItemMapper.insert(zxSkLimitPriceItem1);
                    }
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkLimitPriceAdjust);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceAdjust(List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjustList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSkLimitPriceAdjustList != null && zxSkLimitPriceAdjustList.size() > 0) {
            for (ZxSkLimitPriceAdjust zxSkLimitPriceAdjust : zxSkLimitPriceAdjustList) {
                // ????????????
                ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
                zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
                List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItems = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
                if(zxSkLimitPriceAdjustItems != null && zxSkLimitPriceAdjustItems.size()>0) {
                    zxSkLimitPriceAdjustItem.setModifyUserInfo(userKey, realName);
                    zxSkLimitPriceAdjustItemMapper.batchDeleteUpdateZxSkLimitPriceAdjustItem(zxSkLimitPriceAdjustItems, zxSkLimitPriceAdjustItem);
                }
                // ????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkLimitPriceAdjust.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getWorkId())) {
                    jsonArr.add(zxSkLimitPriceAdjust.getWorkId());
                }
            }
           ZxSkLimitPriceAdjust zxSkLimitPriceAdjust = new ZxSkLimitPriceAdjust();
           zxSkLimitPriceAdjust.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceAdjustMapper.batchDeleteUpdateZxSkLimitPriceAdjust(zxSkLimitPriceAdjustList, zxSkLimitPriceAdjust);
        }
        //????????????????????????
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkLimitPriceAdjustList);
        }
    }

    @Override
    public ResponseEntity getZxSkLimitPriceAdjustNo(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        //todo: ?????????????????????????????????  ???????????????????????????????????????
        //??????????????????????????????????????????????????????.
//        int i = zxSkLimitPriceAdjustMapper.selectByLimitNo(zxSkLimitPriceAdjust.getLimitNo());
//        if(!NumberUtil.equals(new BigDecimal(i),new BigDecimal(0))){
//            return repEntity.ok(null);
//        }
        ZxSkLimitPriceAdjust zxSkLimitPriceAdjust1 = new ZxSkLimitPriceAdjust();
        //????????????
        zxSkLimitPriceAdjust1.setLimitNo(zxSkLimitPriceAdjust.getLimitNo());
        //??????????????????
        zxSkLimitPriceAdjust1.setApih5FlowStatus("2");
        List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjusts = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust1);
        String str = CalcUtils.calcAdd(new BigDecimal(zxSkLimitPriceAdjusts.size()), new BigDecimal("1")).toString();
        if(str.length() == 1){
            str = "0"+str;
        }
        String no = zxSkLimitPriceAdjust.getLimitNo()+"-???-"+str;
        ZxSkLimitPriceAdjust zxSkLimitPriceAdjust2 = new ZxSkLimitPriceAdjust();
        zxSkLimitPriceAdjust2.setAdjustNo(no);
        List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjusts1 = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust2);
        if(CollectionUtil.isNotEmpty(zxSkLimitPriceAdjusts1)){
            return repEntity.layerMessage(false,null,"??????????????????????????????????????????");
        }
        return repEntity.ok(no);
    }



}
