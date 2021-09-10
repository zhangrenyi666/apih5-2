package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.api.sysdb.entity.BaseCode;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
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
import com.apih5.mybatis.dao.ZxSkLimitPriceMapper;
import com.apih5.mybatis.pojo.ZxSkLimitPrice;
import com.apih5.service.ZxSkLimitPriceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkLimitPriceService")
public class ZxSkLimitPriceServiceImpl implements ZxSkLimitPriceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkLimitPriceMapper zxSkLimitPriceMapper;

    @Autowired(required = true)
    private ZxSkLimitPriceItemMapper zxSkLimitPriceItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkResCategoryMaterialsMapper zxSkResCategoryMaterialsMapper;

    @Autowired(required = true)
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired
    private BaseCodeService baseCodeService;

    @Override
    public ResponseEntity getZxSkLimitPriceListByCondition(ZxSkLimitPrice zxSkLimitPrice) {
        if (zxSkLimitPrice == null) {
            zxSkLimitPrice = new ZxSkLimitPrice();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkLimitPrice.setOrgId("");
            zxSkLimitPrice.setProjectId("");
            if(StrUtil.isNotEmpty(zxSkLimitPrice.getOrgID2())){
                zxSkLimitPrice.setProjectId(zxSkLimitPrice.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkLimitPrice.setOrgId(zxSkLimitPrice.getProjectId());
            zxSkLimitPrice.setProjectId("");
            if(StrUtil.isNotEmpty(zxSkLimitPrice.getOrgID2())){
                zxSkLimitPrice.setProjectId(zxSkLimitPrice.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkLimitPrice.setProjectId(zxSkLimitPrice.getProjectId());
            if(StrUtil.isNotEmpty(zxSkLimitPrice.getOrgID2())){
                zxSkLimitPrice.setProjectId(zxSkLimitPrice.getOrgID2());
            }
        }
        if(zxSkLimitPrice.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPrice.getPeriodDate());
            if(StrUtil.equals(result.substring(4),"12")){
                zxSkLimitPrice.setPeriod(result.substring(0,4)+"年下半年");
            }else {
                zxSkLimitPrice.setPeriod(result.substring(0,4)+"年上半年");
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkLimitPrice.getPage(),zxSkLimitPrice.getLimit());
        // 获取数据
        List<ZxSkLimitPrice> zxSkLimitPriceList = zxSkLimitPriceMapper.selectByZxSkLimitPriceList(zxSkLimitPrice);
        //查询明细
        for (ZxSkLimitPrice zxSkLimitPrice1 : zxSkLimitPriceList) {
            //期次
            if(zxSkLimitPrice1.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = zxSkLimitPrice1.getPeriod().toString().substring(0, 4);
                    String substring = zxSkLimitPrice1.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"年上半年")){
                        year = year+"01";
                    }else {
                        year = year+"12";
                    }
                    date = simpleDateFormat.parse(year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zxSkLimitPrice1.setPeriodDate(date);
            }
            ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
            zxSkLimitPriceItem.setMasterId(zxSkLimitPrice1.getId());
            List<ZxSkLimitPriceItem> zxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
            for (ZxSkLimitPriceItem SkLimitPriceItem : zxSkLimitPriceItems) {
                //设定调整的新增/1:新增
                SkLimitPriceItem.setAdjustType("1");
                //id也克隆一下
                SkLimitPriceItem.setLimitPriceAdjustItemID(SkLimitPriceItem.getId());
                String name = "";
                String[] id = SkLimitPriceItem.getResourceId().split(",");
                for (String s : id) {
                    ZxSkResCategoryMaterials zxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(s);
                    if(zxSkResCategoryMaterials == null){
                        ZxSkResourceMaterials zxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(s);
                        if(zxSkResourceMaterials != null && StrUtil.isNotEmpty(zxSkResourceMaterials.getResName())){
                            name += "-"+ zxSkResourceMaterials.getResName();
                        }
                    }else {
                        name += zxSkResCategoryMaterials.getCatName();
                    }
                }
                SkLimitPriceItem.setJoinName(name);
            }
            zxSkLimitPrice1.setZxSkLimitPriceItemList(zxSkLimitPriceItems);
        }
        //查询附件
        for (ZxSkLimitPrice zxSkLimitPrice1 : zxSkLimitPriceList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPrice1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPrice1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkLimitPrice> p = new PageInfo<>(zxSkLimitPriceList);
        return repEntity.okList(zxSkLimitPriceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkLimitPriceDetails(ZxSkLimitPrice zxSkLimitPrice) {
        if (zxSkLimitPrice == null) {
            zxSkLimitPrice = new ZxSkLimitPrice();
        }
        if(StrUtil.isNotEmpty(zxSkLimitPrice.getId())){
            zxSkLimitPrice = zxSkLimitPriceMapper.selectByPrimaryKey(zxSkLimitPrice.getId());
        }else if(StrUtil.isNotEmpty(zxSkLimitPrice.getWorkId())){
            List<ZxSkLimitPrice> zxSkLimitPrices = zxSkLimitPriceMapper.selectByZxSkLimitPriceList(zxSkLimitPrice);
            if(zxSkLimitPrices != null && zxSkLimitPrices.size() >0){
                zxSkLimitPrice = zxSkLimitPrices.get(0);
            }
        }
        // 数据存在
        if (zxSkLimitPrice != null) {
            //期次
            if(zxSkLimitPrice.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = zxSkLimitPrice.getPeriod().toString().substring(0, 4);
                    String substring = zxSkLimitPrice.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"年上半年")){
                        year = year+"01";
                    }else {
                        year = year+"12";
                    }
                    date = simpleDateFormat.parse(year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                zxSkLimitPrice.setPeriodDate(date);
            }
            ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
            zxSkLimitPriceItem.setMasterId(zxSkLimitPrice.getId());
            List<ZxSkLimitPriceItem> zxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
            for (ZxSkLimitPriceItem SkLimitPriceItem : zxSkLimitPriceItems) {
                //设定调整的新增/1:新增
                SkLimitPriceItem.setAdjustType("1");
                //id也克隆一下
                SkLimitPriceItem.setLimitPriceAdjustItemID(SkLimitPriceItem.getId());
                String name = "";
                String[] id = SkLimitPriceItem.getResourceId().split(",");
                for (String s : id) {
                    ZxSkResCategoryMaterials zxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(s);
                    if(zxSkResCategoryMaterials ==null){
                        ZxSkResourceMaterials zxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(s);
                        name += "-"+ zxSkResourceMaterials.getResName();
                    }else {
                        name += zxSkResCategoryMaterials.getCatName();
                    }
                }
                SkLimitPriceItem.setJoinName(name);
            }
            zxSkLimitPrice.setZxSkLimitPriceItemList(zxSkLimitPriceItems);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPrice.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPrice.setFileList(zxErpFiles);
            return repEntity.ok(zxSkLimitPrice);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkLimitPrice(ZxSkLimitPrice zxSkLimitPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkLimitPrice.setId(UuidUtil.generate());
        zxSkLimitPrice.setCreateUserInfo(userKey, realName);
        String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPrice.getPeriodDate());
        if(StrUtil.equals(result.substring(4),"12")){
            zxSkLimitPrice.setPeriod(result.substring(0,4)+"年下半年");
        }else {
            zxSkLimitPrice.setPeriod(result.substring(0,4)+"年上半年");
        }
        //新增-1
        zxSkLimitPrice.setApih5FlowStatus("-1");
        int flag = zxSkLimitPriceMapper.insert(zxSkLimitPrice);
        //创建明细
        List<ZxSkLimitPriceItem> zxSkLimitPriceItemList = zxSkLimitPrice.getZxSkLimitPriceItemList();
        if(zxSkLimitPriceItemList != null && zxSkLimitPriceItemList.size()>0) {
            for (ZxSkLimitPriceItem zxSkLimitPriceItem : zxSkLimitPriceItemList) {
                zxSkLimitPriceItem.setMasterId(zxSkLimitPrice.getId());
                zxSkLimitPriceItem.setId((UuidUtil.generate()));
                zxSkLimitPriceItem.setCreateUserInfo(userKey, realName);
                zxSkLimitPriceItemMapper.insert(zxSkLimitPriceItem);
            }
        }
        //添加附件
        List<ZxErpFile> fileList = zxSkLimitPrice.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkLimitPrice.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkLimitPrice);
        }
    }

    @Override
    public ResponseEntity updateZxSkLimitPrice(ZxSkLimitPrice zxSkLimitPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkLimitPrice dbzxSkLimitPrice = zxSkLimitPriceMapper.selectByPrimaryKey(zxSkLimitPrice.getId());
        if (dbzxSkLimitPrice != null && StrUtil.isNotEmpty(dbzxSkLimitPrice.getId()) && (StrUtil.equals(zxSkLimitPrice.getApih5FlowStatus(),"-1")||StrUtil.equals(zxSkLimitPrice.getApih5FlowStatus(),"0"))) {
            if(!StrUtil.equals(zxSkLimitPrice.getFaqi(),"1")){
                // 公司名称
                dbzxSkLimitPrice.setOrgName(zxSkLimitPrice.getOrgName());
                // 公司id
                dbzxSkLimitPrice.setOrgId(zxSkLimitPrice.getOrgId());
                // 项目名称
                dbzxSkLimitPrice.setProjectName(zxSkLimitPrice.getProjectName());
                // 项目id
                dbzxSkLimitPrice.setProjectId(zxSkLimitPrice.getProjectId());
                // 项目所属省份
                dbzxSkLimitPrice.setProvince(zxSkLimitPrice.getProvince());
                // 工程类型
                dbzxSkLimitPrice.setProjectType(zxSkLimitPrice.getProjectType());
                // 数据期次
                String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPrice.getPeriodDate());
                if(StrUtil.equals(result.substring(4),"12")){
                    dbzxSkLimitPrice.setPeriod(result.substring(0,4)+"年下半年");
                }else {
                    dbzxSkLimitPrice.setPeriod(result.substring(0,4)+"年上半年");
                }
                // 填报人
                dbzxSkLimitPrice.setPreparer(zxSkLimitPrice.getPreparer());
                //
                dbzxSkLimitPrice.setReportStatus(zxSkLimitPrice.getReportStatus());
                // 审核状态
                dbzxSkLimitPrice.setStatusName(zxSkLimitPrice.getStatusName());
                // 审核标识
                dbzxSkLimitPrice.setStatusFlag(zxSkLimitPrice.getStatusFlag());
                // 更新时间
                dbzxSkLimitPrice.setEditTime(zxSkLimitPrice.getEditTime());
                // 所属公司id
                dbzxSkLimitPrice.setComID(zxSkLimitPrice.getComID());
                // 公司名称
                dbzxSkLimitPrice.setComName(zxSkLimitPrice.getComName());
                // 所属公司排序
                dbzxSkLimitPrice.setComOrders(zxSkLimitPrice.getComOrders());
                // 是否本期入场
                dbzxSkLimitPrice.setIsThisIn(zxSkLimitPrice.getIsThisIn());
                // 填报日期
                dbzxSkLimitPrice.setPrepareDate(zxSkLimitPrice.getPrepareDate());
                // 限价编号
                dbzxSkLimitPrice.setLimitNo(zxSkLimitPrice.getLimitNo());
                // 流水号
                dbzxSkLimitPrice.setSerialNumber(zxSkLimitPrice.getSerialNumber());
                // 发起人
                dbzxSkLimitPrice.setBeginPer(zxSkLimitPrice.getBeginPer());
                // 评审状态
                dbzxSkLimitPrice.setStatus(zxSkLimitPrice.getStatus());
                // 流程实例ID
                dbzxSkLimitPrice.setInstProcessId(zxSkLimitPrice.getInstProcessId());
                // 审批进度ID
                dbzxSkLimitPrice.setWorkitemID(zxSkLimitPrice.getWorkitemID());
                // 限价类型
                dbzxSkLimitPrice.setLimitType(zxSkLimitPrice.getLimitType());
                // 合同编号
                dbzxSkLimitPrice.setContractId(zxSkLimitPrice.getContractId());
                // 合同名称
                dbzxSkLimitPrice.setContractNo(zxSkLimitPrice.getContractNo());
                // 调整历史
                dbzxSkLimitPrice.setAdjustHistory (zxSkLimitPrice.getAdjustHistory());
                //删除在新增
                ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
                zxSkLimitPriceItem.setMasterId(dbzxSkLimitPrice.getId());
                List<ZxSkLimitPriceItem> zxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
                if(zxSkLimitPriceItems != null && zxSkLimitPriceItems.size()>0) {
                    zxSkLimitPriceItem.setModifyUserInfo(userKey, realName);
                    zxSkLimitPriceItemMapper.batchDeleteUpdateZxSkLimitPriceItem(zxSkLimitPriceItems, zxSkLimitPriceItem);
                }
                //明细list
                List<ZxSkLimitPriceItem> zxSkLimitPriceItemList = zxSkLimitPrice.getZxSkLimitPriceItemList();
                if(zxSkLimitPriceItemList != null && zxSkLimitPriceItemList.size()>0) {
                    for(ZxSkLimitPriceItem zxSkLimitPriceItem1 : zxSkLimitPriceItemList) {
                        zxSkLimitPriceItem1.setId(UuidUtil.generate());
                        zxSkLimitPriceItem1.setMasterId(dbzxSkLimitPrice.getId());
                        zxSkLimitPriceItem1.setCreateUserInfo(userKey, realName);
                        zxSkLimitPriceItemMapper.insert(zxSkLimitPriceItem1);
                    }
                }
            }
            // 流程id
            dbzxSkLimitPrice.setWorkId(zxSkLimitPrice.getWorkId());
            // 流程状态
            dbzxSkLimitPrice.setApih5FlowStatus(zxSkLimitPrice.getApih5FlowStatus());
            // 共通
            dbzxSkLimitPrice.setModifyUserInfo(userKey, realName);
            flag = zxSkLimitPriceMapper.updateByPrimaryKey(dbzxSkLimitPrice);
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkLimitPrice.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkLimitPrice.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkLimitPrice.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }else {
            // 流程id
            dbzxSkLimitPrice.setWorkId(zxSkLimitPrice.getWorkId());
            // 流程状态
            dbzxSkLimitPrice.setApih5FlowStatus(zxSkLimitPrice.getApih5FlowStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField1(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField1()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField2", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField2(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField2()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField3", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField3(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField3()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField4", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField4(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField4()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField5", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField5(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField5()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField6", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField6(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField6()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField7", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField7(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField7()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField8", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField8(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField8()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField9", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField9(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField9()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField10", zxSkLimitPrice.getOpinionField(), true)){
                dbzxSkLimitPrice.setOpinionField10(zxSkLimitPrice.getOpinionContent(realName, dbzxSkLimitPrice.getOpinionField10()));
            }
            // 共通
            dbzxSkLimitPrice.setModifyUserInfo(userKey, realName);
            flag = zxSkLimitPriceMapper.updateByPrimaryKey(dbzxSkLimitPrice);
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkLimitPrice.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkLimitPrice.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkLimitPrice.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkLimitPrice);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkLimitPrice(List<ZxSkLimitPrice> zxSkLimitPriceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSkLimitPriceList != null && zxSkLimitPriceList.size() > 0) {
            for (ZxSkLimitPrice zxSkLimitPrice : zxSkLimitPriceList) {
                // 删除明细
                ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
                zxSkLimitPriceItem.setMasterId(zxSkLimitPrice.getId());
                List<ZxSkLimitPriceItem> deleteZxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
                if(deleteZxSkLimitPriceItems != null && deleteZxSkLimitPriceItems.size()>0) {
                    zxSkLimitPriceItem.setModifyUserInfo(userKey, realName);
                    zxSkLimitPriceItemMapper.batchDeleteUpdateZxSkLimitPriceItem(deleteZxSkLimitPriceItems, zxSkLimitPriceItem);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkLimitPrice.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                if(StrUtil.isNotEmpty(zxSkLimitPrice.getWorkId())) {
                    jsonArr.add(zxSkLimitPrice.getWorkId());
                }
            }

           ZxSkLimitPrice zxSkLimitPrice = new ZxSkLimitPrice();
           zxSkLimitPrice.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceMapper.batchDeleteUpdateZxSkLimitPrice(zxSkLimitPriceList, zxSkLimitPrice);
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
            return repEntity.ok("sys.data.delete",zxSkLimitPriceList);
        }
    }

    @Override
    public ResponseEntity getZxSkLimitPriceBase(ZxSkLimitPrice zxSkLimitPrice) {
        if(StrUtil.isEmpty(zxSkLimitPrice.getOrgId())){
            return repEntity.ok(new ArrayList<>());
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(zxSkLimitPrice.getOrgId());
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        //限价编号
        ZxSkLimitPrice zxSkLimitPrice1 = new ZxSkLimitPrice();
        zxSkLimitPrice1.setProjectId(zxSkLimitPrice.getOrgId());
        List<ZxSkLimitPrice> zxSkLimitPrices = zxSkLimitPriceMapper.selectByZxSkLimitPriceList(zxSkLimitPrice1);
        String s = CalcUtils.calcAdd(new BigDecimal(zxSkLimitPrices.size()), new BigDecimal("1")).toString();
        if (s.length() == 1){
            s = "00"+s;
        }else if(s.length() == 2){
            s = "0"+s;
        }
        zxSkLimitPrice.setLimitNo(zxCtContractList.get(0).getContractNo()+"-"+ s);
        BaseCode projectLocationBaseCode = new BaseCode();
        projectLocationBaseCode.setItemId("xingzhengquhuadaima");
        List<BaseCode> projectLocationList = (List<BaseCode>) baseCodeService.getBaseCodeSelect(projectLocationBaseCode).getData();
        BaseCode projectTypeBaseCode = new BaseCode();
        projectTypeBaseCode.setItemId("gongChengLeiBie");
        List<BaseCode> projectTypeList = (List<BaseCode>) baseCodeService.getBaseCodeSelect(projectTypeBaseCode).getData();
        //省份
        if (projectLocationList != null && projectLocationList.size() > 0) {
            for (BaseCode baseCode : projectLocationList) {
                if (StrUtil.equals(zxCtContractList.get(0).getProjectLocation(), baseCode.getItemId())) {
                    zxSkLimitPrice.setProvince(baseCode.getItemName());
                    break;
                }
            }
        }
        //工程类型
        if (projectTypeList != null && projectTypeList.size() > 0) {
            for (BaseCode baseCode : projectTypeList) {
                if (StrUtil.equals(zxCtContractList.get(0).getProjType(), baseCode.getItemId())) {
                    zxSkLimitPrice.setProjectType(baseCode.getItemName());
                    break;
                }
            }
        }
        return repEntity.ok(zxSkLimitPrice);
    }

    @Override
    public ResponseEntity getZxSkLimitPriceByLimitNoList(ZxSkLimitPrice zxSkLimitPrice) {
        if (StrUtil.isEmpty(zxSkLimitPrice.getProjectId())) {
            return repEntity.ok(null);
        }
        if(zxSkLimitPrice.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPrice.getPeriodDate());
            if(StrUtil.equals(result.substring(4),"12")){
                zxSkLimitPrice.setPeriod(result.substring(0,4)+"年下半年");
            }else {
                zxSkLimitPrice.setPeriod(result.substring(0,4)+"年上半年");
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkLimitPrice.getPage(),zxSkLimitPrice.getLimit());
        // 获取数据
        List<ZxSkLimitPrice> zxSkLimitPriceList = zxSkLimitPriceMapper.selectByZxSkLimitPriceList(zxSkLimitPrice);
        //查询明细
        for (ZxSkLimitPrice zxSkLimitPrice1 : zxSkLimitPriceList) {
            //期次
            if(zxSkLimitPrice1.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    String year = zxSkLimitPrice1.getPeriod().toString().substring(0, 4);
                    String substring = zxSkLimitPrice1.getPeriod().toString().substring(4);
                    if(StrUtil.equals(substring,"年上半年")){
                        year = year+"01";
                    }else {
                        year = year+"12";
                    }
                    date = simpleDateFormat.parse(year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zxSkLimitPrice1.setPeriodDate(date);

            }
            ZxSkLimitPriceItem zxSkLimitPriceItem = new ZxSkLimitPriceItem();
            zxSkLimitPriceItem.setMasterId(zxSkLimitPrice1.getId());
            List<ZxSkLimitPriceItem> zxSkLimitPriceItems = zxSkLimitPriceItemMapper.selectByZxSkLimitPriceItemList(zxSkLimitPriceItem);
            for (ZxSkLimitPriceItem SkLimitPriceItem : zxSkLimitPriceItems) {
                //设定调整的新增/1:新增
                SkLimitPriceItem.setAdjustType("1");
                //id也克隆一下
                SkLimitPriceItem.setLimitPriceAdjustItemID(SkLimitPriceItem.getId());
                String name = "";
                String[] id = SkLimitPriceItem.getResourceId().split(",");
                for (String s : id) {
                    ZxSkResCategoryMaterials zxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(s);
                    if(zxSkResCategoryMaterials ==null){
                        ZxSkResourceMaterials zxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(s);
                        name += "-"+ zxSkResourceMaterials.getResName();
                    }else {
                        name += zxSkResCategoryMaterials.getCatName();
                    }
                }
                SkLimitPriceItem.setJoinName(name);
                SkLimitPriceItem.setAdjustPrice(new BigDecimal("0"));
            }
            zxSkLimitPrice1.setZxSkLimitPriceItemList(zxSkLimitPriceItems);
        }
        //查询附件
        for (ZxSkLimitPrice zxSkLimitPrice1 : zxSkLimitPriceList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkLimitPrice1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkLimitPrice1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkLimitPrice> p = new PageInfo<>(zxSkLimitPriceList);
        return repEntity.okList(zxSkLimitPriceList, p.getTotal());
    }





}
