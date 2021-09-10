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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkLimitPriceAdjust.setOrgID("");
            zxSkLimitPriceAdjust.setProjectId("");
            if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getOrgID2())){
                zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkLimitPriceAdjust.setOrgID(zxSkLimitPriceAdjust.getProjectId());
            zxSkLimitPriceAdjust.setProjectId("");
            if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getOrgID2())){
                zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getProjectId());
            if(StrUtil.isNotEmpty(zxSkLimitPriceAdjust.getOrgID2())){
                zxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getOrgID2());
            }
        }
        if(zxSkLimitPriceAdjust.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceAdjust.getPeriodDate());
            if(StrUtil.equals(result.substring(4),"12")){
                zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"年下半年");
            }else {
                zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"年上半年");
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkLimitPriceAdjust.getPage(),zxSkLimitPriceAdjust.getLimit());
        // 获取数据
        List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjustList = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust);
        //查询明细
        for (ZxSkLimitPriceAdjust IeskLimitPriceAdjust : zxSkLimitPriceAdjustList) {
            //期次
            if(IeskLimitPriceAdjust.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = IeskLimitPriceAdjust.getPeriod().toString().substring(0, 4);
                    String substring = IeskLimitPriceAdjust.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"年上半年")){
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
        //查询附件
        for (ZxSkLimitPriceAdjust zxSkLimitPriceAdjust1 : zxSkLimitPriceAdjustList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPriceAdjust1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPriceAdjust1.setFileList(zxErpFiles);
        }

        // 得到分页信息
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
        // 获取数据
//        ZxSkLimitPriceAdjust dbZxSkLimitPriceAdjust = zxSkLimitPriceAdjustMapper.selectByPrimaryKey(zxSkLimitPriceAdjust.getId());
        // 数据存在
        if (zxSkLimitPriceAdjust != null) {
            //期次
            if(zxSkLimitPriceAdjust.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = zxSkLimitPriceAdjust.getPeriod().toString().substring(0, 4);
                    String substring = zxSkLimitPriceAdjust.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"年上半年")){
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

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPriceAdjust.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPriceAdjust.setFileList(zxErpFiles);
            return repEntity.ok(zxSkLimitPriceAdjust);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
            zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"年下半年");
        }else {
            zxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"年上半年");
        }
        //新增-1
        zxSkLimitPriceAdjust.setApih5FlowStatus("-1");
        int flag = zxSkLimitPriceAdjustMapper.insert(zxSkLimitPriceAdjust);
        //创建明细
        List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList = zxSkLimitPriceAdjust.getZxSkLimitPriceAdjustItemList();
        if(zxSkLimitPriceAdjustItemList != null && zxSkLimitPriceAdjustItemList.size()>0) {
            for (ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem : zxSkLimitPriceAdjustItemList) {
                //直接写死新增是1
//                zxSkLimitPriceAdjustItem.setAdjustType("1");
                zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
                zxSkLimitPriceAdjustItem.setId((UuidUtil.generate()));
                zxSkLimitPriceAdjustItem.setCreateUserInfo(userKey, realName);
                zxSkLimitPriceAdjustItemMapper.insert(zxSkLimitPriceAdjustItem);
            }
        }
        //添加附件
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
                // 流程id
                dbzxSkLimitPriceAdjust.setWorkId(zxSkLimitPriceAdjust.getWorkId());
                // 流程状态
                dbzxSkLimitPriceAdjust.setApih5FlowStatus(zxSkLimitPriceAdjust.getApih5FlowStatus());
            }
           // 调整编号
           dbzxSkLimitPriceAdjust.setAdjustNo(zxSkLimitPriceAdjust.getAdjustNo());
           // 限价编号
           dbzxSkLimitPriceAdjust.setLimitNo(zxSkLimitPriceAdjust.getLimitNo());
           // 限价数据ID
           dbzxSkLimitPriceAdjust.setLimitPriceId(zxSkLimitPriceAdjust.getLimitPriceId());
           // 公司ID
           dbzxSkLimitPriceAdjust.setOrgID(zxSkLimitPriceAdjust.getOrgID());
           // 公司名称
           dbzxSkLimitPriceAdjust.setOrgName(zxSkLimitPriceAdjust.getOrgName());
           // 项目ID
           dbzxSkLimitPriceAdjust.setProjectId(zxSkLimitPriceAdjust.getProjectId());
           // 项目名称
           dbzxSkLimitPriceAdjust.setProjectName(zxSkLimitPriceAdjust.getProjectName());
           // 所属省份
           dbzxSkLimitPriceAdjust.setProvince(zxSkLimitPriceAdjust.getProvince());
           // 工程类别
           dbzxSkLimitPriceAdjust.setProjectType(zxSkLimitPriceAdjust.getProjectType());
           // 数据期次
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceAdjust.getPeriodDate());
            if(StrUtil.equals(result.substring(4),"12")){
                dbzxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"年下半年");
            }else {
                dbzxSkLimitPriceAdjust.setPeriod(result.substring(0,4)+"年上半年");
            }
           // 填报人
           dbzxSkLimitPriceAdjust.setPerpare(zxSkLimitPriceAdjust.getPerpare());
           // 填报日期
           dbzxSkLimitPriceAdjust.setPrepareDate(zxSkLimitPriceAdjust.getPrepareDate());
           // 是否本期入场
           dbzxSkLimitPriceAdjust.setIsThisIn(zxSkLimitPriceAdjust.getIsThisIn());
           // comID
           dbzxSkLimitPriceAdjust.setComId(zxSkLimitPriceAdjust.getComId());
           // 公司名称
           dbzxSkLimitPriceAdjust.setComName(zxSkLimitPriceAdjust.getComName());
           // comOrders
           dbzxSkLimitPriceAdjust.setComOrders(zxSkLimitPriceAdjust.getComOrders());
           // editTime
           dbzxSkLimitPriceAdjust.setEditTime(zxSkLimitPriceAdjust.getEditTime());
           // 评审状态
           dbzxSkLimitPriceAdjust.setStatus(zxSkLimitPriceAdjust.getStatus());
           // 流水号
           dbzxSkLimitPriceAdjust.setSerialNumber(zxSkLimitPriceAdjust.getSerialNumber());
           // 发起人
           dbzxSkLimitPriceAdjust.setBeginPer(zxSkLimitPriceAdjust.getBeginPer());
           // 流程实例ID
           dbzxSkLimitPriceAdjust.setInstProcessId(zxSkLimitPriceAdjust.getInstProcessId());
           // 评审进度ID
           dbzxSkLimitPriceAdjust.setWorkitemID(zxSkLimitPriceAdjust.getWorkitemID());
           // 限价类型
           dbzxSkLimitPriceAdjust.setLimitType(zxSkLimitPriceAdjust.getLimitType());

           // 共通
           dbzxSkLimitPriceAdjust.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceAdjustMapper.updateByPrimaryKey(dbzxSkLimitPriceAdjust);
            //删除在新增
            ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
            zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
            List<ZxSkLimitPriceAdjustItem> IeskLimitPriceAdjustItems = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
            if(IeskLimitPriceAdjustItems != null && IeskLimitPriceAdjustItems.size()>0) {
                zxSkLimitPriceAdjustItem.setModifyUserInfo(userKey, realName);
                zxSkLimitPriceAdjustItemMapper.batchDeleteUpdateZxSkLimitPriceAdjustItem(IeskLimitPriceAdjustItems, zxSkLimitPriceAdjustItem);
            }
            //明细list
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
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkLimitPriceAdjust.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
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
            // 流程id
            dbzxSkLimitPriceAdjust.setWorkId(zxSkLimitPriceAdjust.getWorkId());
            // 流程状态
            dbzxSkLimitPriceAdjust.setApih5FlowStatus(zxSkLimitPriceAdjust.getApih5FlowStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField1(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField1()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField2", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField2(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField2()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField3", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField3(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField3()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField4", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField4(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField4()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField5", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField5(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField5()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField6", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField6(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField6()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField7", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField7(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField7()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField8", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField8(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField8()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField9", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField9(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField9()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField10", zxSkLimitPriceAdjust.getOpinionField(), true)){
                dbzxSkLimitPriceAdjust.setOpinionField10(zxSkLimitPriceAdjust.getOpinionContent(realName, dbzxSkLimitPriceAdjust.getOpinionField10()));
            }
            // 共通
            dbzxSkLimitPriceAdjust.setModifyUserInfo(userKey, realName);
            flag = zxSkLimitPriceAdjustMapper.updateByPrimaryKey(dbzxSkLimitPriceAdjust);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkLimitPriceAdjust.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
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
        //todo:这里获取限价采集编号可能会有问题
        //审核成功要修改限价数据采集页面
        //zxSkLimitPriceAdjust
        if(StrUtil.equals(zxSkLimitPriceAdjust.getApih5FlowStatus(),"2")){
            //获取限价采集编号
            String limitNo = zxSkLimitPriceAdjust.getLimitNo();
            //根据编号获取限价采集的数据
            ZxSkLimitPrice zxSkLimitPrice = new ZxSkLimitPrice();
            zxSkLimitPrice.setLimitNo(limitNo);
            List<ZxSkLimitPrice> zxSkLimitPrices = zxSkLimitPriceMap.selectByZxSkLimitPriceList(zxSkLimitPrice);
            if(zxSkLimitPrices!=null && zxSkLimitPrices.size()>0){
                //如果是一个人操作,那么取第一个编号没问题
                ZxSkLimitPrice dbzxSkLimitPrice = zxSkLimitPrices.get(0);
                //新建明细
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

                //更新数据采集
                //删除在新增
                ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
                zxSkLimitPriceItem.setMasterId(dbzxSkLimitPrice.getId());
                List<ZxSkLimitPriceItem> zxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
                if(zxSkLimitPriceItems != null && zxSkLimitPriceItems.size()>0) {
                    zxSkLimitPriceItem.setModifyUserInfo(userKey, realName);
                    zxSkLimitPriceItemMapper.batchDeleteUpdateZxSkLimitPriceItem(zxSkLimitPriceItems, zxSkLimitPriceItem);
                }
                //明细list
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
        // 失败
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
                // 删除明细
                ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem = new ZxSkLimitPriceAdjustItem();
                zxSkLimitPriceAdjustItem.setMainID(zxSkLimitPriceAdjust.getId());
                List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItems = zxSkLimitPriceAdjustItemMapper.selectByZxSkLimitPriceAdjustItemList(zxSkLimitPriceAdjustItem);
                if(zxSkLimitPriceAdjustItems != null && zxSkLimitPriceAdjustItems.size()>0) {
                    zxSkLimitPriceAdjustItem.setModifyUserInfo(userKey, realName);
                    zxSkLimitPriceAdjustItemMapper.batchDeleteUpdateZxSkLimitPriceAdjustItem(zxSkLimitPriceAdjustItems, zxSkLimitPriceAdjustItem);
                }
                // 删除附件
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
        //删除流程后台数据
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkLimitPriceAdjustList);
        }
    }

    @Override
    public ResponseEntity getZxSkLimitPriceAdjustNo(ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        //todo: 根据限价编号去查询数据  在根据审核的编号的数量去做
        //先根据编号查询这个是否有未审核的数据.
//        int i = zxSkLimitPriceAdjustMapper.selectByLimitNo(zxSkLimitPriceAdjust.getLimitNo());
//        if(!NumberUtil.equals(new BigDecimal(i),new BigDecimal(0))){
//            return repEntity.ok(null);
//        }
        ZxSkLimitPriceAdjust zxSkLimitPriceAdjust1 = new ZxSkLimitPriceAdjust();
        //限价编号
        zxSkLimitPriceAdjust1.setLimitNo(zxSkLimitPriceAdjust.getLimitNo());
        //审核成功状态
        zxSkLimitPriceAdjust1.setApih5FlowStatus("2");
        List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjusts = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust1);
        String str = CalcUtils.calcAdd(new BigDecimal(zxSkLimitPriceAdjusts.size()), new BigDecimal("1")).toString();
        if(str.length() == 1){
            str = "0"+str;
        }
        String no = zxSkLimitPriceAdjust.getLimitNo()+"-调-"+str;
        ZxSkLimitPriceAdjust zxSkLimitPriceAdjust2 = new ZxSkLimitPriceAdjust();
        zxSkLimitPriceAdjust2.setAdjustNo(no);
        List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjusts1 = zxSkLimitPriceAdjustMapper.selectByZxSkLimitPriceAdjustList(zxSkLimitPriceAdjust2);
        if(CollectionUtil.isNotEmpty(zxSkLimitPriceAdjusts1)){
            return repEntity.layerMessage(false,null,"请先审核该限价编号的调整单据");
        }
        return repEntity.ok(no);
    }



}
