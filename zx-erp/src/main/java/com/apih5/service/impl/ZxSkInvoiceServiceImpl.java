package com.apih5.service.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkStockService;
import com.apih5.service.ZxSkStockTransferReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkInvoiceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkInvoiceService")
public class ZxSkInvoiceServiceImpl implements ZxSkInvoiceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkInvoiceMapper zxSkInvoiceMapper;

    @Autowired(required = true)
    private ZxSkInvoiceItemMapper zxSkInvoiceItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @Autowired(required = true)
    private ZxSkStockTransItemReceivingMapper zxSkStockTransItemReceivingMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxSkStockTransferReceivingMapper zxSkStockTransferReceivingMapper;

    @Autowired(required = true)
    private ZxSkStockTransferReceivingService zxSkStockTransferReceivingService;

    @Override
    public ResponseEntity getZxSkInvoiceListByCondition(ZxSkInvoice zxSkInvoice) {
        if (zxSkInvoice == null) {
            zxSkInvoice = new ZxSkInvoice();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkInvoice.setCompanyId("");
            zxSkInvoice.setMakeoutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkInvoice.setCompanyId(zxSkInvoice.getMakeoutOrgID());
            zxSkInvoice.setMakeoutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkInvoice.setMakeoutOrgID(zxSkInvoice.getMakeoutOrgID());
        }

        // ????????????
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // ????????????
        List<ZxSkInvoice> zxSkInvoiceList = zxSkInvoiceMapper.selectByZxSkInvoiceList(zxSkInvoice);
        //????????????
        for (ZxSkInvoice skInvoice : zxSkInvoiceList) {
            ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
            zxSkInvoiceItem.setInvoiceID(skInvoice.getId());
            List<ZxSkInvoiceItem> zxSkInvoiceItems = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
            if(zxSkInvoiceItems != null && zxSkInvoiceItems.size()!=0){
                List<ZxSkStockTransItemReceiving> zxSkInvoiceQtyList = zxSkInvoiceMapper.getZxSkInvoiceQty(zxSkInvoiceItems, skInvoice);
                for (ZxSkInvoiceItem skInvoiceItem : zxSkInvoiceItems) {
                    for (ZxSkStockTransItemReceiving zxSkStockTransItemReceiving : zxSkInvoiceQtyList) {
                        if(StrUtil.equals(skInvoiceItem.getStockTransID(),zxSkStockTransItemReceiving.getStockTransID())){
                            skInvoiceItem.setOldQty(zxSkStockTransItemReceiving.getQty());
                        }
                    }
                }
                skInvoice.setZxSkInvoiceItemList(zxSkInvoiceItems);
            }
        }
        // ??????????????????
        PageInfo<ZxSkInvoice> p = new PageInfo<>(zxSkInvoiceList);

        return repEntity.okList(zxSkInvoiceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkInvoiceDetail(ZxSkInvoice zxSkInvoice) {
        if (zxSkInvoice == null) {
            zxSkInvoice = new ZxSkInvoice();
        }
        // ????????????
        ZxSkInvoice dbZxSkInvoice = zxSkInvoiceMapper.selectByPrimaryKey(zxSkInvoice.getId());
        // ????????????
        if (dbZxSkInvoice != null) {
            ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
            zxSkInvoiceItem.setInvoiceID(dbZxSkInvoice.getId());
            List<ZxSkInvoiceItem> zxSkInvoiceItems = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
            dbZxSkInvoice.setZxSkInvoiceItemList(zxSkInvoiceItems);
            return repEntity.ok(dbZxSkInvoice);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkInvoice(ZxSkInvoice zxSkInvoice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkInvoice.setId(UuidUtil.generate());
        zxSkInvoice.setCreateUserInfo(userKey, realName);

        //??????????????????: 0:?????????
        zxSkInvoice.setBillStatus("0");
        //????????????
        List<ZxSkInvoiceItem> zxSkInvoiceItemList = zxSkInvoice.getZxSkInvoiceItemList();
        if(zxSkInvoiceItemList!=null&&zxSkInvoiceItemList.size()>0){
            for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
                zxSkInvoiceItem.setInvoiceID(zxSkInvoice.getId());
                zxSkInvoiceItem.setId(UuidUtil.generate());
                zxSkInvoiceItem.setCreateUserInfo(userKey,realName);
                zxSkInvoiceItemMapper.insert(zxSkInvoiceItem);
            }
        }
        int flag = zxSkInvoiceMapper.insert(zxSkInvoice);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkInvoice);
        }
    }

    @Override
    public ResponseEntity updateZxSkInvoice(ZxSkInvoice zxSkInvoice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkInvoice dbZxSkInvoice = zxSkInvoiceMapper.selectByPrimaryKey(zxSkInvoice.getId());
        if (dbZxSkInvoice != null && StrUtil.isNotEmpty(dbZxSkInvoice.getId())) {
           // ????????????
           dbZxSkInvoice.setOrgID(zxSkInvoice.getOrgID());
           // ????????????
           dbZxSkInvoice.setMakeoutOrgID(zxSkInvoice.getMakeoutOrgID());
           // ?????????
           dbZxSkInvoice.setInvoiceNo(zxSkInvoice.getInvoiceNo());
           // ????????????
           dbZxSkInvoice.setMakeoutDate(zxSkInvoice.getMakeoutDate());
           // ??????
           dbZxSkInvoice.setCustomer(zxSkInvoice.getCustomer());
           // ????????????
           dbZxSkInvoice.setAmt(zxSkInvoice.getAmt());
           // ??????ID
           dbZxSkInvoice.setWhOrgID(zxSkInvoice.getWhOrgID());
           // ??????
           dbZxSkInvoice.setWarehouseName(zxSkInvoice.getWarehouseName());
           // ????????????
           dbZxSkInvoice.setOutOrgName(zxSkInvoice.getOutOrgName());
           // ??????ID
           dbZxSkInvoice.setOutOrgID(zxSkInvoice.getOutOrgID());
           // ????????????
           dbZxSkInvoice.setBillStatus(zxSkInvoice.getBillStatus());
           // ?????????
           dbZxSkInvoice.setYsdID(zxSkInvoice.getYsdID());
           // ???????????????
           dbZxSkInvoice.setYsdNo(zxSkInvoice.getYsdNo());
           // ????????????
           dbZxSkInvoice.setReceNo(zxSkInvoice.getReceNo());
           // ????????????ID
           dbZxSkInvoice.setResourceID(zxSkInvoice.getResourceID());
           // ????????????
           dbZxSkInvoice.setResourceName(zxSkInvoice.getResourceName());
           // ????????????
           dbZxSkInvoice.setMaterialSource(zxSkInvoice.getMaterialSource());
           // ????????????
           dbZxSkInvoice.setPurchType(zxSkInvoice.getPurchType());
           // ??????ID???
           dbZxSkInvoice.setContractID(zxSkInvoice.getContractID());
           // ????????????
           dbZxSkInvoice.setContractName(zxSkInvoice.getContractName());
           // ?????????
           dbZxSkInvoice.setAmtTotal(zxSkInvoice.getAmtTotal());
           // ????????????
           dbZxSkInvoice.setMakeoutOrgName(zxSkInvoice.getMakeoutOrgName());
           // ??????id
           dbZxSkInvoice.setCompanyId(zxSkInvoice.getCompanyId());
           // ????????????
           dbZxSkInvoice.setCompanyName(zxSkInvoice.getCompanyName());
           // ??????
           dbZxSkInvoice.setRemarks(zxSkInvoice.getRemarks());
           // ??????
           dbZxSkInvoice.setSort(zxSkInvoice.getSort());
           // ??????
           dbZxSkInvoice.setModifyUserInfo(userKey, realName);

           //???????????????
            ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
            zxSkInvoiceItem.setInvoiceID(zxSkInvoice.getId());
            List<ZxSkInvoiceItem> zxSkInvoiceItems = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
            if(zxSkInvoiceItems!=null&&zxSkInvoiceItems.size()>0){
                zxSkInvoiceItem.setModifyUserInfo(userKey,realName);
                zxSkInvoiceItemMapper.batchDeleteUpdateZxSkInvoiceItem(zxSkInvoiceItems,zxSkInvoiceItem);
            }
            //??????List
            List<ZxSkInvoiceItem> zxSkInvoiceItemList = zxSkInvoice.getZxSkInvoiceItemList();
            if(zxSkInvoiceItemList!=null&&zxSkInvoiceItemList.size()>0){
                for (ZxSkInvoiceItem skInvoiceItem : zxSkInvoiceItemList) {
                    skInvoiceItem.setInvoiceID(zxSkInvoice.getId());
                    skInvoiceItem.setId(UuidUtil.generate());
                    skInvoiceItem.setCreateUserInfo(userKey,realName);
                    zxSkInvoiceItemMapper.insert(skInvoiceItem);
                }
            }
            flag = zxSkInvoiceMapper.updateByPrimaryKey(dbZxSkInvoice);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkInvoice);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkInvoice(List<ZxSkInvoice> zxSkInvoiceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkInvoiceList != null && zxSkInvoiceList.size() > 0) {
            for (ZxSkInvoice zxSkInvoice : zxSkInvoiceList) {
                ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
                zxSkInvoiceItem.setInvoiceID(zxSkInvoice.getId());
                List<ZxSkInvoiceItem> zxSkInvoiceItems = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
                if(zxSkInvoiceItems!=null&&zxSkInvoiceItems.size()>0){
                    zxSkInvoiceItem.setModifyUserInfo(userKey,realName);
                    zxSkInvoiceItemMapper.batchDeleteUpdateZxSkInvoiceItem(zxSkInvoiceItems,zxSkInvoiceItem);
                }
            }
           ZxSkInvoice zxSkInvoice = new ZxSkInvoice();
           zxSkInvoice.setModifyUserInfo(userKey, realName);
           flag = zxSkInvoiceMapper.batchDeleteUpdateZxSkInvoice(zxSkInvoiceList, zxSkInvoice);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkInvoiceList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    //???????????????+???????????????+?????????-??????-????????????+??????
    @Override
    public ResponseEntity getZxSkInvoiceNo(ZxSkInvoice zxSkInvoice) {
        if(StrUtil.isEmpty(zxSkInvoice.getOrgID()) || zxSkInvoice.getMakeoutDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkInvoice.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkInvoice.getMakeoutDate());
        int month = DateUtil.month(zxSkInvoice.getMakeoutDate())+1;
        int day = DateUtil.dayOfMonth(zxSkInvoice.getMakeoutDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkInvoiceMapper.getZxSkInvoiceCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkInvoiceReceivableOrder(ZxSkInvoice zxSkInvoice) {
        if(StrUtil.isEmpty(zxSkInvoice.getCompanyID())
            ||StrUtil.isEmpty(zxSkInvoice.getWhOrgID())
            ||StrUtil.isEmpty(zxSkInvoice.getOutOrgID())
            ||StrUtil.isEmpty(zxSkInvoice.getResourceID())){
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // ????????????
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkInvoiceMapper.getZxSkInvoiceReceivableOrder(zxSkInvoice);
        Iterator<ZxSkStockTransferReceiving> iterator = zxSkStockTransferReceivingList.iterator();
        while (iterator.hasNext()){
            ZxSkStockTransferReceiving zxSkStockTransferReceiving = iterator.next();
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = zxSkStockTransferReceiving.getZxSkStockTransItemReceivingList();
            Iterator<ZxSkStockTransItemReceiving> iterator1 = zxSkStockTransItemReceivingList.iterator();
            while (iterator1.hasNext()){
                ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = iterator1.next();
                if(CalcUtils.compareToZero(zxSkStockTransItemReceiving.getQty())==0){
                    iterator1.remove();
                }else {
                    //????????????????????????
                }
            }
            if(zxSkStockTransItemReceivingList.size()==0){
                iterator.remove();
            }
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkInvoice(ZxSkInvoice zxSkInvoice) {
        //??????????????????
        ZxSkInvoice zxSkInvoice1 = zxSkInvoiceMapper.selectByPrimaryKey(zxSkInvoice.getId());
        if(StrUtil.equals(zxSkInvoice1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkInvoiceItem dbzxSkInvoiceItem = new ZxSkInvoiceItem();
        dbzxSkInvoiceItem.setInvoiceID(zxSkInvoice1.getId());
        List<ZxSkInvoiceItem> zxSkInvoiceItemList = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(dbzxSkInvoiceItem);
        if(CollUtil.isEmpty(zxSkInvoiceItemList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        List<ZxSkStockTransItemReceiving> zxSkInvoiceQtyList = zxSkInvoiceMapper.getZxSkInvoiceQty(zxSkInvoiceItemList, zxSkInvoice1);
        for (ZxSkInvoiceItem skInvoiceItem : zxSkInvoiceItemList) {
            for (ZxSkStockTransItemReceiving zxSkStockTransItemReceiving : zxSkInvoiceQtyList) {
                if(StrUtil.equals(skInvoiceItem.getStockTransID(),zxSkStockTransItemReceiving.getStockTransID())){
                    skInvoiceItem.setOldQty(zxSkStockTransItemReceiving.getQty());
                }
            }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????????????????
        //????????????
        //(????????????id(whOrgID),????????????id(?????????outOrgID),??????id(resourceID),       ?????????ID(stockTransID)List)
        //todo:?????????
        List<ZxSkStockTransItemReceiving> zxSkInvoiceQty = zxSkInvoiceMapper.getZxSkInvoiceQty(zxSkInvoiceItemList, zxSkInvoice1);
        //??????List (?????????????????????????????????)
        List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings = new ArrayList<>();
        for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
            for (ZxSkStockTransItemReceiving zxSkStockTransItemReceiving : zxSkInvoiceQty) {
                if(StrUtil.equals(zxSkInvoiceItem.getStockTransID(),zxSkStockTransItemReceiving.getStockTransID())
                        &&CalcUtils.compareTo(zxSkInvoiceItem.getOldInPrice(),zxSkStockTransItemReceiving.getOldInPrice())==0
                        &&CalcUtils.compareTo(zxSkStockTransItemReceiving.getQty(),zxSkInvoiceItem.getQty())<0){
                    zxSkStockTransItemReceivings.add(zxSkStockTransItemReceiving);
                }
            }
        }
        if(CollUtil.isNotEmpty(zxSkStockTransItemReceivings)){
            return repEntity.layerMessage(false,zxSkStockTransItemReceivings, "??????????????????????????????????????????");
        }
        //?????????????????????amt    -    ?????????????????????oldAmt ?????? ?????????tempQty  *  ????????????qty
        for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
            BigDecimal pri = CalcUtils.calcDivide(zxSkInvoiceItem.getoldAmt(), zxSkInvoiceItem.getTempQty());
            BigDecimal oldamt = CalcUtils.calcMultiply(pri, zxSkInvoiceItem.getQty());
            zxSkInvoiceItem.setMoney(CalcUtils.calcSubtract(zxSkInvoiceItem.getAmt(),oldamt));
        }
//        ??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkInvoiceItem Item : zxSkInvoiceItemList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkInvoice1.getCompanyId());
            //??????ID
            zxSkStock.setWhOrgID(zxSkInvoice1.getWhOrgID());
            //????????????ID
            zxSkStock.setResourceId(zxSkInvoice1.getResourceID());
            //??????Id
            zxSkStock.setResID(Item.getResID());
            //????????????
            zxSkStock.setResCode(Item.getResCode());
            //????????????
            zxSkStock.setResName(Item.getResName());
            //????????????spec
            zxSkStock.setSpec(Item.getResSpec());
            //??????unit
            zxSkStock.setUnit(Item.getResUnit());
            //??????
            zxSkStock.setStockQty(Item.getQty());
            //??????
            zxSkStock.setStockAmt(Item.getMoney());
            zxSkStockList.add(zxSkStock);
        }
        //??????????????????
        ResponseEntity responseEntity = zxSkStockService.changeZxSkStockTotal(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkInvoice1.setBillStatus("1");
            zxSkInvoice1.setModifyUserInfo(userKey, realName);
            flag = zxSkInvoiceMapper.checkZxSkInvoice(zxSkInvoice1);
            // ??????
            if (flag == 0) {
                try {
                    throw new Exception("??????????????????");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.errorUpdate();
            }
            //???????????? ???????????????????????????
            //???????????????????????????????????????[???00]+???????????????????????????
            //?????????  //????????????
            List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = new ArrayList<>();
            List<ZxSkInvoiceItem> zxSkInvoiceItemBillNoList = new ArrayList<>();
            zxSkInvoiceItemList.parallelStream().collect(Collectors.groupingBy((o->o.getStockTransBillNo()),Collectors.toList())).forEach(
                    (id,transfer) -> {transfer.stream().reduce((a,b)-> new ZxSkInvoiceItem(
                            a.getStockTransBillNo()
                    )).ifPresent(zxSkInvoiceItemBillNoList::add);
                    }
            );
            //???????????????????????????//?????????list???
            for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemBillNoList) {
                ZxSkStockTransferReceiving zxSkStockTransferReceiving = new ZxSkStockTransferReceiving();
                zxSkStockTransferReceiving.setBillNo(zxSkInvoiceItem.getStockTransBillNo());
                List<ZxSkStockTransferReceiving> zxSkStockTransferReceivings = zxSkStockTransferReceivingMapper.selectByZxSkStockTransferReceivingListByPrimaryNo(zxSkStockTransferReceiving);
                for (ZxSkStockTransferReceiving skStockTransferReceiving : zxSkStockTransferReceivings) {
                    //????????????,??????,??????
                    //??????????????????(??????)??????
                    //??????????????????
//                    skStockTransferReceiving.setBusDate(zxSkInvoice1.getMakeoutDate());
                    //?????????
//                    skStockTransferReceiving.setOuttransactor("");
                    //??????????????? ??????
//                    skStockTransferReceiving.setIntransactor("");
                    //????????????????????????
//                    skStockTransferReceiving.setInvoiceNo(zxSkInvoice1.getInvoiceNo());
                    //?????????
//                    skStockTransferReceiving.setReporter(realName);
                    //????????? ??????
//                    skStockTransferReceiving.setVoucherNo("");
                    //??????id
//                    skStockTransferReceiving.setPurchaseContractID(zxSkInvoice1.getContractID());
                    //????????????
//                    skStockTransferReceiving.setPurchaseContract(zxSkInvoice1.getContractName());
                    //????????????
//                    skStockTransferReceiving.setPurchType(zxSkInvoice1.getPurchType());
                    //todo:??????????????????????????????
                    //??????
                    //????????????
                    //????????????
                    skStockTransferReceiving.setBillStatus("1");
                    zxSkStockTransferReceivingList.add(skStockTransferReceiving);
                }
            }
            Map<String, List<ZxSkStockTransItemReceiving>> zxSkStockTransItemReceivingMap = new HashMap<>();
            for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
                List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingsList = zxSkStockTransItemReceivingMap.get(zxSkInvoiceItem.getStockTransBillNo());
                if(CollUtil.isEmpty(zxSkStockTransItemReceivingsList)){
                    zxSkStockTransItemReceivingsList = new ArrayList<>();
                }
                //?????????????????? (?????????)
                ZxSkStockTransItemReceiving receivingItem = zxSkStockTransItemReceivingMapper.selectByPrimaryKey(zxSkInvoiceItem.getStockTransItemID());

                ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
                //??????ID
                zxSkStockTransItemReceiving.setResID(receivingItem.getResID());
                //????????????
                zxSkStockTransItemReceiving.setResCode(receivingItem.getResCode());
                //????????????
                zxSkStockTransItemReceiving.setResName(receivingItem.getResName());
                //????????????
                zxSkStockTransItemReceiving.setSpec(receivingItem.getSpec());
                //????????????
                zxSkStockTransItemReceiving.setResUnit(receivingItem.getResUnit());
                //???????????? -
                zxSkStockTransItemReceiving.setInQty(zxSkInvoiceItem.getQty().negate());
                //???????????????
                zxSkStockTransItemReceiving.setInPriceNoTax(receivingItem.getInPriceNoTax());
                //??????????????? -    //?????????????????? ????????????????????????
                BigDecimal resAllFeeNoTax = NumberUtil.round(CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPriceNoTax()),2);
                zxSkStockTransItemReceiving.setResAllFeeNoTax(resAllFeeNoTax.negate());
                //?????? -  //????????????-???????????????
                BigDecimal resAllFeeTax = NumberUtil.round(CalcUtils.calcSubtract(CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPrice()),CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPriceNoTax())),2);
                zxSkStockTransItemReceiving.setResAllFeeTax(resAllFeeTax.negate());
                //????????????
                zxSkStockTransItemReceiving.setInPrice(receivingItem.getInPrice());
                //???????????? -   //??????????????????  ?????????????????????
                BigDecimal resAllFee = NumberUtil.round(CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPrice()),2);
                zxSkStockTransItemReceiving.setResAllFee(resAllFee.negate());
                //??????????????? -
                zxSkStockTransItemReceiving.setInFee(zxSkInvoiceItem.getOtherExpense()!=null?zxSkInvoiceItem.getOtherExpense().negate():new BigDecimal("0"));
                //??????????????? -
                BigDecimal inFeeTotal = zxSkInvoiceItem.getOtherExpenseTotal();
                zxSkStockTransItemReceiving.setInFeeTotal(inFeeTotal!=null?zxSkInvoiceItem.getOtherExpenseTotal().negate():new BigDecimal("0"));
                //??????????????? -
                zxSkStockTransItemReceiving.setYsFee(zxSkInvoiceItem.getYsFee()!=null?zxSkInvoiceItem.getYsFee().negate():new BigDecimal("0"));
                //??????????????? -
                BigDecimal ysFeeTotal = zxSkInvoiceItem.getYsFeeTotal();
                zxSkStockTransItemReceiving.setYsFeeTotal(ysFeeTotal!=null?zxSkInvoiceItem.getYsFeeTotal().negate():new BigDecimal("0"));
                //?????? -   ???????????????,???????????????,????????????,
                zxSkStockTransItemReceiving.setInAmtAll(CalcUtils.calcAdd(resAllFee,CalcUtils.calcAdd(inFeeTotal,ysFeeTotal)).negate());
                //?????????????????? isDeduct ?????????????????????????????????   1:??? 0:???
                //???????????? -
                if(StrUtil.equals(zxSkInvoiceItem.getIsDeduct(),"1")){
                    //??????????????? + ??????????????? + ???????????????
                    zxSkStockTransItemReceiving.setInAmt(CalcUtils.calcAdd(resAllFeeNoTax,CalcUtils.calcAdd(inFeeTotal,ysFeeTotal)).negate());
                }else {
                    //???????????? + ??????????????? + ???????????????
                    zxSkStockTransItemReceiving.setInAmt(CalcUtils.calcAdd(resAllFee,CalcUtils.calcAdd(inFeeTotal,ysFeeTotal)).negate());
                }
                //??????
                zxSkStockTransItemReceiving.setRemark(zxSkInvoiceItem.getRemarks());
                zxSkStockTransItemReceivingsList.add(zxSkStockTransItemReceiving);
                zxSkStockTransItemReceivingMap.put(zxSkInvoiceItem.getStockTransBillNo(),zxSkStockTransItemReceivingsList);
            }
            int i = 0;
            for (ZxSkStockTransferReceiving zxSkStockTransferReceiving : zxSkStockTransferReceivingList) {
                //????????????????????????????????????
                List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings1 = zxSkStockTransItemReceivingMap.get(zxSkStockTransferReceiving.getBillNo());
                zxSkStockTransferReceiving.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivings1);
                //??????????????????
                zxSkStockTransferReceiving.setBillNo("[???0"+i+"]"+zxSkStockTransferReceiving.getBillNo());
                i++;
                zxSkStockTransferReceivingService.saveZxSkStockTransferReceiving(zxSkStockTransferReceiving);
            }

            //???1???
            //??????????????????????????????????????????+???????????????+?????????-??????-????????????
            //???????????????
            ZxSkStockTransferReceiving zxSkStockTransferReceiving = zxSkStockTransferReceivingList.get(0);
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = new ArrayList<>();
            for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
                ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
                //??????ID
                zxSkStockTransItemReceiving.setResID(zxSkInvoiceItem.getResID());
                //????????????
                zxSkStockTransItemReceiving.setResCode(zxSkInvoiceItem.getResCode());
                //????????????
                zxSkStockTransItemReceiving.setResName(zxSkInvoiceItem.getResName());
                //????????????
                zxSkStockTransItemReceiving.setSpec(zxSkInvoiceItem.getResSpec());
                //????????????
                zxSkStockTransItemReceiving.setResUnit(zxSkInvoiceItem.getResUnit());
                //????????????
                zxSkStockTransItemReceiving.setInQty(zxSkInvoiceItem.getQty());
                //???????????????
                zxSkStockTransItemReceiving.setInPriceNoTax(zxSkInvoiceItem.getUnitPriceNoTax());
                //???????????????
                zxSkStockTransItemReceiving.setResAllFeeNoTax(zxSkInvoiceItem.getStockAmtNoTax());
                //??????
                zxSkStockTransItemReceiving.setResAllFeeTax(zxSkInvoiceItem.getStockAmtTax());
                //????????????
                zxSkStockTransItemReceiving.setInPrice(zxSkInvoiceItem.getUnitPrice());
                //????????????
                zxSkStockTransItemReceiving.setResAllFee(zxSkInvoiceItem.getStockAmt());
                //???????????????
                zxSkStockTransItemReceiving.setInFee(zxSkInvoiceItem.getOtherExpense()!=null?zxSkInvoiceItem.getOtherExpense():new BigDecimal("0"));
                //???????????????
                zxSkStockTransItemReceiving.setInFeeTotal(zxSkInvoiceItem.getOtherExpenseTotal()!=null?zxSkInvoiceItem.getOtherExpenseTotal():new BigDecimal("0"));
                //???????????????
                zxSkStockTransItemReceiving.setYsFee(zxSkInvoiceItem.getYsFee()!=null?zxSkInvoiceItem.getYsFee():new BigDecimal("0"));
                //???????????????
                zxSkStockTransItemReceiving.setYsFeeTotal(zxSkInvoiceItem.getYsFeeTotal()!=null?zxSkInvoiceItem.getYsFeeTotal():new BigDecimal("0"));
                //??????
                zxSkStockTransItemReceiving.setInAmtAll(zxSkInvoiceItem.getAmtTotal());
                //????????????
                zxSkStockTransItemReceiving.setInAmt(zxSkInvoiceItem.getAmt());
                //????????????
                zxSkStockTransItemReceiving.setPrecollecte("0");
                //??????
                zxSkStockTransItemReceiving.setRemark(zxSkInvoiceItem.getRemarks());
                zxSkStockTransItemReceivingList.add(zxSkStockTransItemReceiving);
            }
            zxSkStockTransferReceiving.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivingList);
            //??????
            ZxSkStockTransferReceiving zxSkStockTransferReceiving1 = new ZxSkStockTransferReceiving();
            zxSkStockTransferReceiving1.setOrgID(zxSkInvoice1.getMakeoutOrgID());
            zxSkStockTransferReceiving1.setBusDate(zxSkInvoice1.getMakeoutDate());
            ResponseEntity zxSkStockTransferReceivingNo = zxSkStockTransferReceivingService.getZxSkStockTransferReceivingNo(zxSkStockTransferReceiving1);
            zxSkStockTransferReceiving.setBillNo(zxSkStockTransferReceivingNo.getData().toString());
            zxSkStockTransferReceivingService.saveZxSkStockTransferReceiving(zxSkStockTransferReceiving);
            return responseEntity;
        }else {
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity getZxSkInvoiceOutOrg(ZxSkInvoice zxSkInvoice) {
        if(StrUtil.isEmpty(zxSkInvoice.getMakeoutOrgID())){
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // ????????????
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkInvoiceMapper.getZxSkInvoiceOutOrg(zxSkInvoice);
        // ??????????????????
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkInvoiceResource(ZxSkInvoice zxSkInvoice) {
        if(StrUtil.isEmpty(zxSkInvoice.getCompanyID())||StrUtil.isEmpty(zxSkInvoice.getMakeoutOrgID())){
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // ????????????
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkInvoiceMapper.getZxSkInvoiceResource(zxSkInvoice);

        // ??????????????????
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }





}
