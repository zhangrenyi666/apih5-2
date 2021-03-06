package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxSkTurnoverOutItemMapper;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkTurnOverStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkTurnoverOutMapper;
import com.apih5.service.ZxSkTurnoverOutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverOutService")
public class ZxSkTurnoverOutServiceImpl implements ZxSkTurnoverOutService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverOutMapper zxSkTurnoverOutMapper;

    @Autowired(required = true)
    private ZxSkTurnoverOutItemMapper zxSkTurnoverOutItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkTurnOverStockService zxSkTurnOverStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkTurnoverOutListByCondition(ZxSkTurnoverOut zxSkTurnoverOut) {
        if (zxSkTurnoverOut == null) {
            zxSkTurnoverOut = new ZxSkTurnoverOut();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnoverOut.setCompanyId("");
            zxSkTurnoverOut.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverOut.getOrgID2())){
                zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkTurnoverOut.setCompanyId(zxSkTurnoverOut.getOrgID());
            zxSkTurnoverOut.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverOut.getOrgID2())){
                zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID());
            if(StrUtil.isNotEmpty(zxSkTurnoverOut.getOrgID2())){
                zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkTurnoverOut.getPage(),zxSkTurnoverOut.getLimit());
        // ????????????
        List<ZxSkTurnoverOut> zxSkTurnoverOutList = zxSkTurnoverOutMapper.selectByZxSkTurnoverOutList(zxSkTurnoverOut);
        //????????????
        for (ZxSkTurnoverOut zxSkTurnoverOut1 : zxSkTurnoverOutList) {
            ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
            zxSkTurnoverOutItem.setBillID(zxSkTurnoverOut1.getId());
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
            //????????????????????????????????????
            for (ZxSkTurnoverOutItem skTurnoverOutItem : zxSkTurnoverOutItems) {
                if(skTurnoverOutItem.getHasReturnQty()==null){
                    skTurnoverOutItem.setOldhasReturnQty(new BigDecimal(0));
                }else {
                    skTurnoverOutItem.setOldhasReturnQty(skTurnoverOutItem.getHasReturnQty());
                }
            }

            zxSkTurnoverOut1.setZxSkTurnoverOutItemList(zxSkTurnoverOutItems);
        }
        // ??????????????????
        PageInfo<ZxSkTurnoverOut> p = new PageInfo<>(zxSkTurnoverOutList);

        return repEntity.okList(zxSkTurnoverOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverOutDetail(ZxSkTurnoverOut zxSkTurnoverOut) {
        if (zxSkTurnoverOut == null) {
            zxSkTurnoverOut = new ZxSkTurnoverOut();
        }
        // ????????????
        ZxSkTurnoverOut dbZxSkTurnoverOut = zxSkTurnoverOutMapper.selectByPrimaryKey(zxSkTurnoverOut.getId());
        // ????????????
        if (dbZxSkTurnoverOut != null) {
            ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
            zxSkTurnoverOutItem.setBillID(dbZxSkTurnoverOut.getId());
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
            //????????????????????????????????????
            for (ZxSkTurnoverOutItem skTurnoverOutItem : zxSkTurnoverOutItems) {
                if(skTurnoverOutItem.getHasReturnQty()==null){
                    skTurnoverOutItem.setOldhasReturnQty(new BigDecimal(0));
                }else {
                    skTurnoverOutItem.setOldhasReturnQty(skTurnoverOutItem.getHasReturnQty());
                }
            }
            dbZxSkTurnoverOut.setZxSkTurnoverOutItemList(zxSkTurnoverOutItems);
            return repEntity.ok(dbZxSkTurnoverOut);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverOut.setId(UuidUtil.generate());
        zxSkTurnoverOut.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkTurnoverOut.setStatus("0");
        //????????????
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOut.getZxSkTurnoverOutItemList();
        if(zxSkTurnoverOutItemList != null && zxSkTurnoverOutItemList.size()>0) {
            for (ZxSkTurnoverOutItem zxSkTurnoverOutItem : zxSkTurnoverOutItemList) {
                zxSkTurnoverOutItem.setBillID(zxSkTurnoverOut.getId());
                zxSkTurnoverOutItem.setId((UuidUtil.generate()));
                zxSkTurnoverOutItem.setCreateUserInfo(userKey, realName);
                zxSkTurnoverOutItemMapper.insert(zxSkTurnoverOutItem);
            }
        }
        int flag = zxSkTurnoverOutMapper.insert(zxSkTurnoverOut);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverOut);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverOut dbZxSkTurnoverOut = zxSkTurnoverOutMapper.selectByPrimaryKey(zxSkTurnoverOut.getId());
        if (dbZxSkTurnoverOut != null && StrUtil.isNotEmpty(dbZxSkTurnoverOut.getId())) {
           // ????????????ID
           dbZxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID());
           // ????????????
           dbZxSkTurnoverOut.setOrgName(zxSkTurnoverOut.getOrgName());
           // ????????????
           dbZxSkTurnoverOut.setBillNo(zxSkTurnoverOut.getBillNo());
           // ????????????
           dbZxSkTurnoverOut.setBusDate(zxSkTurnoverOut.getBusDate());
           // ????????????id
           dbZxSkTurnoverOut.setInOrgID(zxSkTurnoverOut.getInOrgID());
           // ????????????
           dbZxSkTurnoverOut.setInOrgName(zxSkTurnoverOut.getInOrgName());
           // ?????????
           dbZxSkTurnoverOut.setConsignee(zxSkTurnoverOut.getConsignee());
           // ????????????
           dbZxSkTurnoverOut.setBillStatus(zxSkTurnoverOut.getBillStatus());
           // ????????????
           dbZxSkTurnoverOut.setStatus(zxSkTurnoverOut.getStatus());
           // ??????id
           dbZxSkTurnoverOut.setCompanyId(zxSkTurnoverOut.getCompanyId());
           // ????????????
           dbZxSkTurnoverOut.setCompanyName(zxSkTurnoverOut.getCompanyName());
           // ??????
           dbZxSkTurnoverOut.setRemarks(zxSkTurnoverOut.getRemarks());
           // ??????
           dbZxSkTurnoverOut.setSort(zxSkTurnoverOut.getSort());
           // ??????
           dbZxSkTurnoverOut.setModifyUserInfo(userKey, realName);

            //???????????????
            ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
            zxSkTurnoverOutItem.setBillID(zxSkTurnoverOut.getId());
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
            if(zxSkTurnoverOutItems != null && zxSkTurnoverOutItems.size()>0) {
                zxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
                zxSkTurnoverOutItemMapper.batchDeleteUpdateZxSkTurnoverOutItem(zxSkTurnoverOutItems, zxSkTurnoverOutItem);
            }
            //??????list
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOut.getZxSkTurnoverOutItemList();
            if(zxSkTurnoverOutItemList != null && zxSkTurnoverOutItemList.size()>0) {
                for(ZxSkTurnoverOutItem zxSkTurnoverOutItem1 : zxSkTurnoverOutItemList) {
                    zxSkTurnoverOutItem1.setId(UuidUtil.generate());
                    zxSkTurnoverOutItem1.setBillID(zxSkTurnoverOut.getId());
                    zxSkTurnoverOutItem1.setCreateUserInfo(userKey, realName);
                    zxSkTurnoverOutItemMapper.insert(zxSkTurnoverOutItem1);
                }
            }

           flag = zxSkTurnoverOutMapper.updateByPrimaryKey(dbZxSkTurnoverOut);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverOut);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverOut(List<ZxSkTurnoverOut> zxSkTurnoverOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverOutList != null && zxSkTurnoverOutList.size() > 0) {
            for (ZxSkTurnoverOut zxSkTurnoverOut : zxSkTurnoverOutList) {
                // ????????????
                ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
                zxSkTurnoverOutItem.setBillID(zxSkTurnoverOut.getId());
                List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
                if(zxSkTurnoverOutItems != null && zxSkTurnoverOutItems.size()>0) {
                    zxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
                    zxSkTurnoverOutItemMapper.batchDeleteUpdateZxSkTurnoverOutItem(zxSkTurnoverOutItems, zxSkTurnoverOutItem);
                }
            }
           ZxSkTurnoverOut zxSkTurnoverOut = new ZxSkTurnoverOut();
           zxSkTurnoverOut.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverOutMapper.batchDeleteUpdateZxSkTurnoverOut(zxSkTurnoverOutList, zxSkTurnoverOut);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverOutList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    //????????????????????????+?????????????????????+?????????-??????-001??????
    @Override
    public ResponseEntity getZxSkTurnoverOutNo(ZxSkTurnoverOut zxSkTurnoverOut) {
        if(StrUtil.isEmpty(zxSkTurnoverOut.getOrgID()) || zxSkTurnoverOut.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkTurnoverOut.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkTurnoverOut.getBusDate());
        int month = DateUtil.month(zxSkTurnoverOut.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkTurnoverOut.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkTurnoverOutMapper.getZxSkTurnoverOutCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ??????????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    //orgID
    //??????id
    @Override
    public ResponseEntity getZxSkTurnoverOutResourceList(ZxSkTurnoverOut zxSkTurnoverOut) {
        if (StrUtil.isEmpty(zxSkTurnoverOut.getOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkTurnoverOut.getPage(),zxSkTurnoverOut.getLimit());
        // ????????????(????????????)
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutMapper.getZxSkTurnoverOutResourceList(zxSkTurnoverOut);
        // ??????????????????
        PageInfo<ZxSkTurnoverOutItem> p = new PageInfo<>(zxSkTurnoverOutItems);
        return repEntity.okList(zxSkTurnoverOutItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        //????????????
        ZxSkTurnoverOut dbZxSkTurnoverOut = zxSkTurnoverOutMapper.selectByPrimaryKey(zxSkTurnoverOut.getId());
        if(StrUtil.equals(dbZxSkTurnoverOut.getStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkTurnoverOutItem dbzxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
        dbzxSkTurnoverOutItem.setBillID(dbZxSkTurnoverOut.getId());
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(dbzxSkTurnoverOutItem);
        //????????????????????????????????????
        for (ZxSkTurnoverOutItem skTurnoverOutItem : zxSkTurnoverOutItemList) {
            if(skTurnoverOutItem.getHasReturnQty()==null){
                skTurnoverOutItem.setOldhasReturnQty(new BigDecimal(0));
            }else {
                skTurnoverOutItem.setOldhasReturnQty(skTurnoverOutItem.getHasReturnQty());
            }
        }
        if(CollUtil.isEmpty(zxSkTurnoverOutItemList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //??????
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemListNew = new ArrayList<>();
        zxSkTurnoverOutItemList.parallelStream()
                //collect?????????????????????????????????reduce??????????????????????????????????????????????????????????????????????????????????????????
                .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
                (id,transfer) -> {
                    transfer.stream().reduce((a,b) -> new ZxSkTurnoverOutItem(
                            //??????
                            a.getBatchNo(),
                            //??????
                            CalcUtils.calcAdd(a.getOutQty(),b.getOutQty())
                    )).ifPresent(zxSkTurnoverOutItemListNew::add);
                }
        );
        //??????List
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnoverOutItem item : zxSkTurnoverOutItemListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //??????ID
//            zxSkTurnOverStock.setCompanyID(dbZxSkTurnoverOut.getCompanyID());
            //??????ID(????????????Id)
            zxSkTurnOverStock.setOrgID(dbZxSkTurnoverOut.getOrgID());
            //????????????
            zxSkTurnOverStock.setOrgName(dbZxSkTurnoverOut.getOrgName());
            //??????Id
            //????????????
            //????????????
            //????????????spec
            //??????unit
            //??????(??????id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //????????????/??????
            zxSkTurnOverStock.setStockQty(item.getOutQty());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //???????????????
        ResponseEntity responseEntity = zxSkTurnOverStockService.reduceZxSkStock(zxSkTurnOverStockList);
        if(responseEntity.isSuccess()){
            dbZxSkTurnoverOut.setStatus("1");
            dbZxSkTurnoverOut.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverOutMapper.checkZxSkTurnoverOut(dbZxSkTurnoverOut);
            // ??????
            if (flag == 0) {
                try {
                    throw new Exception("??????????????????");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.errorUpdate();
            }
            return responseEntity;
        }else {
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity returnZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //??????????????????
        if(!StrUtil.equals(zxSkTurnoverOut.getStatus(), "1")) {
            return repEntity.layerMessage("no", "???????????????????????????????????????");
        }
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOut.getZxSkTurnoverOutItemList();
        if(CollUtil.isEmpty(zxSkTurnoverOutItemList)){
            return repEntity.layerMessage("no", "???????????????????????????????????????");
        }
        for (ZxSkTurnoverOutItem zxSkTurnoverOutItem : zxSkTurnoverOutItemList) {
            //????????????????????????????????????????????????
            zxSkTurnoverOutItem.setOldhasReturnQty(
                    CalcUtils.calcSubtract(
                            zxSkTurnoverOutItem.getHasReturnQty(),zxSkTurnoverOutItem.getOldhasReturnQty()));
        }

        int flag = 0;
        //??????
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemListNew = new ArrayList<>();
        zxSkTurnoverOutItemList.parallelStream()
            //collect?????????????????????????????????reduce??????????????????????????????????????????????????????????????????????????????????????????
            .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
            (id,transfer) -> {
                transfer.stream().reduce((a,b) -> new ZxSkTurnoverOutItem(
                        //??????
                        a.getBatchNo(),
                        //resID,
                        a.getResID(),
                        //????????????
                        CalcUtils.calcAdd(a.getOldhasReturnQty(),b.getOldhasReturnQty())
                )).ifPresent(zxSkTurnoverOutItemListNew::add);
            }
        );
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnoverOutItem item : zxSkTurnoverOutItemListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //??????ID
//            zxSkTurnOverStock.setCompanyID(zxSkTurnoverOut.getCompanyID());
            //??????ID(????????????Id)
            zxSkTurnOverStock.setOrgID(zxSkTurnoverOut.getOrgID());
            //????????????
            zxSkTurnOverStock.setOrgName(zxSkTurnoverOut.getOrgName());
            //??????(??????id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //????????????
            zxSkTurnOverStock.setStockQty(item.getOldhasReturnQty());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //???????????????
        ResponseEntity responseEntity = zxSkTurnOverStockService.returnZxSkStock(zxSkTurnOverStockList);
        if(responseEntity.isSuccess()){
            zxSkTurnoverOut.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverOutMapper.updateByPrimaryKey(zxSkTurnoverOut);
            for (ZxSkTurnoverOutItem zxSkTurnoverOutItem : zxSkTurnoverOutItemList) {
                zxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
                flag = zxSkTurnoverOutItemMapper.updateByPrimaryKey(zxSkTurnoverOutItem);
            }
            // ??????
            if (flag == 0) {
                try {
                    throw new Exception("??????????????????");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.errorUpdate();
            }
            return responseEntity;
        }else {
            return responseEntity;
        }
    }



}
