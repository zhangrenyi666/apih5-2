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
import com.apih5.mybatis.dao.ZxSkReturnsItemMapper;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkTurnOverStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkReturnsMapper;
import com.apih5.service.ZxSkReturnsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkReturnsService")
public class ZxSkReturnsServiceImpl implements ZxSkReturnsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkReturnsMapper zxSkReturnsMapper;

    @Autowired(required = true)
    private ZxSkReturnsItemMapper zxSkReturnsItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkTurnOverStockService zxSkTurnOverStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkReturnsListByCondition(ZxSkReturns zxSkReturns) {
        if (zxSkReturns == null) {
            zxSkReturns = new ZxSkReturns();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkReturns.setCompanyId("");
            zxSkReturns.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkReturns.setCompanyId(zxSkReturns.getOrgID());
            zxSkReturns.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkReturns.setOrgID(zxSkReturns.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxSkReturns.getPage(),zxSkReturns.getLimit());
        // ????????????
        List<ZxSkReturns> zxSkReturnsList = zxSkReturnsMapper.selectByZxSkReturnsList(zxSkReturns);
        //????????????
        for (ZxSkReturns zxSkReturns1 : zxSkReturnsList) {
            ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
            zxSkReturnsItem.setBillID(zxSkReturns1.getId());
            List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
            zxSkReturns1.setZxSkReturnsItemList(zxSkReturnsItems);
        }
        // ??????????????????
        PageInfo<ZxSkReturns> p = new PageInfo<>(zxSkReturnsList);

        return repEntity.okList(zxSkReturnsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkReturnsDetail(ZxSkReturns zxSkReturns) {
        if (zxSkReturns == null) {
            zxSkReturns = new ZxSkReturns();
        }
        // ????????????
        ZxSkReturns dbZxSkReturns = zxSkReturnsMapper.selectByPrimaryKey(zxSkReturns.getId());
        // ????????????
        if (dbZxSkReturns != null) {
            ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
            zxSkReturnsItem.setBillID(dbZxSkReturns.getId());
            List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
            dbZxSkReturns.setZxSkReturnsItemList(zxSkReturnsItems);
            return repEntity.ok(dbZxSkReturns);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkReturns(ZxSkReturns zxSkReturns) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkReturns.setId(UuidUtil.generate());
        zxSkReturns.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkReturns.setStatus("0");
        //????????????
        List<ZxSkReturnsItem> zxSkReturnsItemList = zxSkReturns.getZxSkReturnsItemList();
        if(CollUtil.isNotEmpty(zxSkReturnsItemList)) {
            for (ZxSkReturnsItem zxSkReturnsItem : zxSkReturnsItemList) {
                //???????????? 0:???.1:???
                zxSkReturnsItem.setSettlementStatus("0");
                zxSkReturnsItem.setBillID(zxSkReturns.getId());
                zxSkReturnsItem.setId((UuidUtil.generate()));
                zxSkReturnsItem.setCreateUserInfo(userKey, realName);
                zxSkReturnsItemMapper.insert(zxSkReturnsItem);
            }
        }
        int flag = zxSkReturnsMapper.insert(zxSkReturns);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkReturns);
        }
    }

    @Override
    public ResponseEntity updateZxSkReturns(ZxSkReturns zxSkReturns) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkReturns dbZxSkReturns = zxSkReturnsMapper.selectByPrimaryKey(zxSkReturns.getId());
        if (dbZxSkReturns != null && StrUtil.isNotEmpty(dbZxSkReturns.getId())) {
           // ????????????ID
           dbZxSkReturns.setOrgID(zxSkReturns.getOrgID());
           // ????????????
           dbZxSkReturns.setOrgName(zxSkReturns.getOrgName());
           // ????????????
           dbZxSkReturns.setBillNo(zxSkReturns.getBillNo());
           // ????????????
           dbZxSkReturns.setBusDate(zxSkReturns.getBusDate());
           // ????????????
           dbZxSkReturns.setStatus(zxSkReturns.getStatus());
           // ????????????ID
           dbZxSkReturns.setOutOrgID(zxSkReturns.getOutOrgID());
           // ????????????
           dbZxSkReturns.setOutOrgName(zxSkReturns.getOutOrgName());
           // ??????(%)
           dbZxSkReturns.setTaxRate(zxSkReturns.getTaxRate());
           // ????????????
           dbZxSkReturns.setIsDeduct(zxSkReturns.getIsDeduct());
           // ??????id
           dbZxSkReturns.setCompanyId(zxSkReturns.getCompanyId());
           // ????????????
           dbZxSkReturns.setCompanyName(zxSkReturns.getCompanyName());
           // ??????
           dbZxSkReturns.setRemarks(zxSkReturns.getRemarks());
           // ??????
           dbZxSkReturns.setSort(zxSkReturns.getSort());
           // ??????
           dbZxSkReturns.setModifyUserInfo(userKey, realName);

            //???????????????
            ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
            zxSkReturnsItem.setBillID(zxSkReturns.getId());
            List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
            if(zxSkReturnsItems != null && zxSkReturnsItems.size()>0) {
                zxSkReturnsItem.setModifyUserInfo(userKey, realName);
                zxSkReturnsItemMapper.batchDeleteUpdateZxSkReturnsItem(zxSkReturnsItems, zxSkReturnsItem);
            }
            //??????list
            List<ZxSkReturnsItem> zxSkReturnsItemList = zxSkReturns.getZxSkReturnsItemList();
            if(zxSkReturnsItemList != null && zxSkReturnsItemList.size()>0) {
                for(ZxSkReturnsItem zxSkReturnsItem1 : zxSkReturnsItemList) {
                    zxSkReturnsItem1.setId(UuidUtil.generate());
                    zxSkReturnsItem1.setBillID(zxSkReturns.getId());
                    zxSkReturnsItem1.setCreateUserInfo(userKey, realName);
                    zxSkReturnsItemMapper.insert(zxSkReturnsItem1);
                }
            }
           flag = zxSkReturnsMapper.updateByPrimaryKey(dbZxSkReturns);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkReturns);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkReturns(List<ZxSkReturns> zxSkReturnsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkReturnsList != null && zxSkReturnsList.size() > 0) {
            for (ZxSkReturns zxSkReturns : zxSkReturnsList) {
                // ????????????
                ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
                zxSkReturnsItem.setBillID(zxSkReturns.getId());
                List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
                if(zxSkReturnsItems != null && zxSkReturnsItems.size()>0) {
                    zxSkReturnsItem.setModifyUserInfo(userKey, realName);
                    zxSkReturnsItemMapper.batchDeleteUpdateZxSkReturnsItem(zxSkReturnsItems, zxSkReturnsItem);
                }
            }
           ZxSkReturns zxSkReturns = new ZxSkReturns();
           zxSkReturns.setModifyUserInfo(userKey, realName);
           flag = zxSkReturnsMapper.batchDeleteUpdateZxSkReturns(zxSkReturnsList, zxSkReturns);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkReturnsList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    //????????????????????????+?????????????????????+?????????-??????-????????????+????????????
    @Override
    public ResponseEntity getZxSkReturnsNo(ZxSkReturns zxSkReturns) {
        if(StrUtil.isEmpty(zxSkReturns.getOrgID()) || zxSkReturns.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkReturns.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkReturns.getBusDate());
        int month = DateUtil.month(zxSkReturns.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkReturns.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkReturnsMapper.getZxSkReturnsCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ??????????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkReturnsResourceList(ZxSkReturns zxSkReturns) {
        //????????????orgID,????????????outOrgID
        if (StrUtil.isEmpty(zxSkReturns.getOrgID()) ||
                StrUtil.isEmpty(zxSkReturns.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkReturns.getPage(),zxSkReturns.getLimit());
        // ????????????(????????????)
        List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsMapper.getZxSkReturnsResourceList(zxSkReturns);
        // ??????????????????
        PageInfo<ZxSkReturnsItem> p = new PageInfo<>(zxSkReturnsItems);
        return repEntity.okList(zxSkReturnsItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkReturns(ZxSkReturns zxSkReturns) {
        //??????????????????
        ZxSkReturns dbZxSkReturns = zxSkReturnsMapper.selectByPrimaryKey(zxSkReturns.getId());
        if (StrUtil.equals(dbZxSkReturns.getStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
        zxSkReturnsItem.setBillID(dbZxSkReturns.getId());
        List<ZxSkReturnsItem> zxSkReturnsItemList = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
        if (CollUtil.isEmpty(zxSkReturnsItemList)) {
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //?????? ??????
        List<ZxSkReturnsItem> zxSkReturnsItemListNew = new ArrayList<>();
        zxSkReturnsItemList.parallelStream()
                //collect?????????????????????????????????reduce??????????????????????????????????????????????????????????????????????????????????????????
                .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
                (id, transfer) -> {
                    transfer.stream().reduce((a, b) -> new ZxSkReturnsItem(
                            //??????
                            a.getBatchNo(),
                            //????????????
                            CalcUtils.calcAdd(a.getReturnQty(), b.getReturnQty()),
                            //originAmt ????????????
                            CalcUtils.calcAdd(a.getOriginAmt(), b.getOriginAmt()),
                            //remainAmt	????????????
                            CalcUtils.calcAdd(a.getRemainAmt(), b.getRemainAmt())
                    )).ifPresent(zxSkReturnsItemListNew::add);
                }
        );
        //??????List
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkReturnsItem item : zxSkReturnsItemListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //??????ID
//            zxSkTurnOverStock.setCompanyID(zxSkTurnoverOut.getCompanyID());
            //??????ID(????????????Id)
            zxSkTurnOverStock.setOrgID(dbZxSkReturns.getOrgID());
            //????????????
            zxSkTurnOverStock.setOrgName(dbZxSkReturns.getOrgName());
            //??????Id
            //????????????
            //????????????
            //????????????spec
            //??????unit
            //??????(??????id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //????????????/??????
            zxSkTurnOverStock.setStockQty(item.getReturnQty());
            //????????????
            zxSkTurnOverStock.setOriginalAmt(item.getOriginAmt());
            //????????????
            zxSkTurnOverStock.setRemainAmt(item.getRemainAmt());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //??????
        //???????????????
        ResponseEntity responseEntity = zxSkTurnOverStockService.reduceZxSkStock(zxSkTurnOverStockList);
        if (responseEntity.isSuccess()) {
            dbZxSkReturns.setStatus("1");
            dbZxSkReturns.setModifyUserInfo(userKey, realName);
            //???????????????????????????????????????.
            flag = zxSkReturnsMapper.checkZxSkReturns(dbZxSkReturns);
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
        } else {
            return responseEntity;
        }
    }




}
