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
import com.apih5.mybatis.dao.ZxSkTurnoverInItemMapper;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkTurnOverStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkTurnoverInMapper;
import com.apih5.service.ZxSkTurnoverInService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverInService")
public class ZxSkTurnoverInServiceImpl implements ZxSkTurnoverInService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverInMapper zxSkTurnoverInMapper;

    @Autowired(required = true)
    private ZxSkTurnoverInItemMapper zxSkTurnoverInItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkTurnOverStockService zxSkTurnOverStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkTurnoverInListByCondition(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnoverIn.setCompanyId("");
            zxSkTurnoverIn.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverIn.getOrgID2())){
                zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkTurnoverIn.setCompanyId(zxSkTurnoverIn.getOrgID());
            zxSkTurnoverIn.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverIn.getOrgID2())){
                zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID());
            if(StrUtil.isNotEmpty(zxSkTurnoverIn.getOrgID2())){
                zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkTurnoverIn.getPage(),zxSkTurnoverIn.getLimit());
        // ????????????
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverInMapper.selectByZxSkTurnoverInList(zxSkTurnoverIn);
        //????????????
        for (ZxSkTurnoverIn zxSkTurnoverIn1 : zxSkTurnoverInList) {
            ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
            zxSkTurnoverInItem.setBillID(zxSkTurnoverIn1.getId());
            List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
            zxSkTurnoverIn1.setZxSkTurnoverInItemList(zxSkTurnoverInItems);
        }
        // ??????????????????
        PageInfo<ZxSkTurnoverIn> p = new PageInfo<>(zxSkTurnoverInList);

        return repEntity.okList(zxSkTurnoverInList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverInDetail(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // ????????????
        ZxSkTurnoverIn dbZxSkTurnoverIn = zxSkTurnoverInMapper.selectByPrimaryKey(zxSkTurnoverIn.getId());
        // ????????????
        if (dbZxSkTurnoverIn != null) {
            ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
            zxSkTurnoverInItem.setBillID(dbZxSkTurnoverIn.getId());
            List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
            dbZxSkTurnoverIn.setZxSkTurnoverInItemList(zxSkTurnoverInItems);
            return repEntity.ok(dbZxSkTurnoverIn);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverIn.setId(UuidUtil.generate());
        zxSkTurnoverIn.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkTurnoverIn.setStatus("0");
        //????????????
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverIn.getZxSkTurnoverInItemList();
        if(CollUtil.isNotEmpty(zxSkTurnoverInItemList)) {
            for (ZxSkTurnoverInItem zxSkTurnoverInItem : zxSkTurnoverInItemList) {
                if(zxSkTurnoverInItem.getFeeSum()==null){
                    zxSkTurnoverInItem.setFeeSum(new BigDecimal(0));
                }
                //????????????  0: ???,1:???
                zxSkTurnoverInItem.setSettlementStatus("0");
                zxSkTurnoverInItem.setBillID(zxSkTurnoverIn.getId());
                zxSkTurnoverInItem.setId((UuidUtil.generate()));
                zxSkTurnoverInItem.setCreateUserInfo(userKey, realName);
                zxSkTurnoverInItemMapper.insert(zxSkTurnoverInItem);
            }
        }
        int flag = zxSkTurnoverInMapper.insert(zxSkTurnoverIn);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverIn);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverIn dbZxSkTurnoverIn = zxSkTurnoverInMapper.selectByPrimaryKey(zxSkTurnoverIn.getId());
        if (dbZxSkTurnoverIn != null && StrUtil.isNotEmpty(dbZxSkTurnoverIn.getId())) {
           // ????????????ID
           dbZxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID());
           // ????????????
           dbZxSkTurnoverIn.setOrgName(zxSkTurnoverIn.getOrgName());
           // ????????????
           dbZxSkTurnoverIn.setMaterialSource(zxSkTurnoverIn.getMaterialSource());
           // ????????????
           dbZxSkTurnoverIn.setBillNo(zxSkTurnoverIn.getBillNo());
           // ????????????
           dbZxSkTurnoverIn.setBusDate(zxSkTurnoverIn.getBusDate());
           // ?????????
           dbZxSkTurnoverIn.setConsignee(zxSkTurnoverIn.getConsignee());
           // ????????????
           dbZxSkTurnoverIn.setBillStatus(zxSkTurnoverIn.getBillStatus());
           // ????????????
           dbZxSkTurnoverIn.setStatus(zxSkTurnoverIn.getStatus());
           // ????????????ID
           dbZxSkTurnoverIn.setOutOrgID(zxSkTurnoverIn.getOutOrgID());
           // ????????????
           dbZxSkTurnoverIn.setOutOrgName(zxSkTurnoverIn.getOutOrgName());
           // ????????????ID
           dbZxSkTurnoverIn.setContractID(zxSkTurnoverIn.getContractID());
           // ????????????
           dbZxSkTurnoverIn.setContractName(zxSkTurnoverIn.getContractName());
           // ????????????
           dbZxSkTurnoverIn.setPurchType(zxSkTurnoverIn.getPurchType());
           // ????????????
           dbZxSkTurnoverIn.setRecePerson(zxSkTurnoverIn.getRecePerson());
           // ????????????
           dbZxSkTurnoverIn.setPurcPerson(zxSkTurnoverIn.getPurcPerson());
           // ????????????
           dbZxSkTurnoverIn.setIsDeduct(zxSkTurnoverIn.getIsDeduct());
           // ??????(%)
           dbZxSkTurnoverIn.setTaxRate(zxSkTurnoverIn.getTaxRate());
           // fromBillID
           dbZxSkTurnoverIn.setFromBillID(zxSkTurnoverIn.getFromBillID());
           // ????????????
           dbZxSkTurnoverIn.setOrNotPrecollecte(zxSkTurnoverIn.getOrNotPrecollecte());
           // ??????id
           dbZxSkTurnoverIn.setCompanyId(zxSkTurnoverIn.getCompanyId());
           // ????????????
           dbZxSkTurnoverIn.setCompanyName(zxSkTurnoverIn.getCompanyName());
           // ??????
           dbZxSkTurnoverIn.setRemarks(zxSkTurnoverIn.getRemarks());
           // ??????
           dbZxSkTurnoverIn.setSort(zxSkTurnoverIn.getSort());
           // ??????
           dbZxSkTurnoverIn.setModifyUserInfo(userKey, realName);

            //???????????????
            ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
            zxSkTurnoverInItem.setBillID(zxSkTurnoverIn.getId());
            List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
            if(zxSkTurnoverInItems != null && zxSkTurnoverInItems.size()>0) {
                zxSkTurnoverInItem.setModifyUserInfo(userKey, realName);
                zxSkTurnoverInItemMapper.batchDeleteUpdateZxSkTurnoverInItem(zxSkTurnoverInItems, zxSkTurnoverInItem);
            }
            //??????list
            List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverIn.getZxSkTurnoverInItemList();
            if(zxSkTurnoverInItemList != null && zxSkTurnoverInItemList.size()>0) {
                for(ZxSkTurnoverInItem zxSkTurnoverInItem1 : zxSkTurnoverInItemList) {
                    zxSkTurnoverInItem1.setId(UuidUtil.generate());
                    zxSkTurnoverInItem1.setBillID(zxSkTurnoverIn.getId());
                    zxSkTurnoverInItem1.setCreateUserInfo(userKey, realName);
                    zxSkTurnoverInItemMapper.insert(zxSkTurnoverInItem1);
                }
            }
           flag = zxSkTurnoverInMapper.updateByPrimaryKey(dbZxSkTurnoverIn);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverIn);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverIn(List<ZxSkTurnoverIn> zxSkTurnoverInList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverInList != null && zxSkTurnoverInList.size() > 0) {
            for (ZxSkTurnoverIn zxSkTurnoverIn : zxSkTurnoverInList) {
                // ????????????
                ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
                zxSkTurnoverInItem.setBillID(zxSkTurnoverIn.getId());
                List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
                if(zxSkTurnoverInItems != null && zxSkTurnoverInItems.size()>0) {
                    zxSkTurnoverInItem.setModifyUserInfo(userKey, realName);
                    zxSkTurnoverInItemMapper.batchDeleteUpdateZxSkTurnoverInItem(zxSkTurnoverInItems, zxSkTurnoverInItem);
                }
            }
           ZxSkTurnoverIn zxSkTurnoverIn = new ZxSkTurnoverIn();
           zxSkTurnoverIn.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverInMapper.batchDeleteUpdateZxSkTurnoverIn(zxSkTurnoverInList, zxSkTurnoverIn);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverInList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    @Override
    public synchronized ResponseEntity checkZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn) {
        //??????????????????
        ZxSkTurnoverIn dbZxSkTurnoverIn = zxSkTurnoverInMapper.selectByPrimaryKey(zxSkTurnoverIn.getId());
        if(StrUtil.equals(dbZxSkTurnoverIn.getStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkTurnoverInItem dbzxSkTurnoverInItem = new ZxSkTurnoverInItem();
        dbzxSkTurnoverInItem.setBillID(dbZxSkTurnoverIn.getId());
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(dbzxSkTurnoverInItem);
        if(CollUtil.isEmpty(zxSkTurnoverInItemList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //??????????????????
//        inPrice
        //????????????(?????????????????????)
//        inPriceNoTax
        //??????List
        //???????????????????????????????????????   ?????????????????????.(???)??????????????????????????????????????????,(???)?????????????????????????????????????????????
        //0: ???, 1:???
        if(StrUtil.equals(dbZxSkTurnoverIn.getIsDeduct(),"1")){
            for (ZxSkTurnoverInItem zxSkTurnoverInItem : zxSkTurnoverInItemList) {
                zxSkTurnoverInItem.setInPrice(zxSkTurnoverInItem.getInPriceNoTax());
            }
        }
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnoverInItem item : zxSkTurnoverInItemList) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //??????ID
            zxSkTurnOverStock.setCompanyID(dbZxSkTurnoverIn.getCompanyID());
            //??????ID(????????????Id)
            zxSkTurnOverStock.setOrgID(dbZxSkTurnoverIn.getOrgID());
            //????????????
            zxSkTurnOverStock.setOrgName(dbZxSkTurnoverIn.getOrgName());
            //??????Id
            zxSkTurnOverStock.setResID(item.getResID());
            //????????????
            zxSkTurnOverStock.setResCode(item.getResCode());
            //????????????
            zxSkTurnOverStock.setResName(item.getResName());
            //????????????spec
            zxSkTurnOverStock.setSpec(item.getSpec());
            //??????unit
            zxSkTurnOverStock.setResUnit(item.getResUnit());
            //??????(??????id)
            zxSkTurnOverStock.setBatchNo(item.getId());
            //????????????/??????
            zxSkTurnOverStock.setStockQty(item.getInQty());
            //????????????/??????????????????(????????????)
            zxSkTurnOverStock.setStockPrice(item.getInPrice());
            //????????????/??????
            zxSkTurnOverStock.setStockAmt(CalcUtils.calcDivide(item.getInPrice(),item.getInQty(),2));
            //?????????/??????
            zxSkTurnOverStock.setOriginalQty(item.getInQty());
            //??????
            zxSkTurnOverStock.setOriginalAmt(item.getInAmt());
            //??????
            zxSkTurnOverStock.setRemainAmt(item.getRemainAmt());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //???????????????
        ResponseEntity responseEntity = zxSkTurnOverStockService.addZxSkStock(zxSkTurnOverStockList);
        if(responseEntity.isSuccess()){
            dbZxSkTurnoverIn.setStatus("1");
            dbZxSkTurnoverIn.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverInMapper.checkZxSkTurnoverIn(dbZxSkTurnoverIn);
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
    public ResponseEntity getZxSkTurnoverInResourceList(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // ????????????
        PageHelper.startPage(zxSkTurnoverIn.getPage(),zxSkTurnoverIn.getLimit());
        // ????????????
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverInMapper.getZxSkTurnoverInResourceList(zxSkTurnoverIn);
        for (ZxSkTurnoverInItem zxSkTurnoverInItem : zxSkTurnoverInItemList) {
            zxSkTurnoverInItem.setPricingManner("????????????");
            zxSkTurnoverInItem.setPlanningAuthorities("?????????");
        }
        // ??????????????????
        PageInfo<ZxSkTurnoverInItem> p = new PageInfo<>(zxSkTurnoverInItemList);
        return repEntity.okList(zxSkTurnoverInItemList,p.getTotal());
    }

    //???????????????+?????????????????????+?????????-??????-????????????+?????????
    @Override
    public ResponseEntity getZxSkTurnoverInNo(ZxSkTurnoverIn zxSkTurnoverIn) {
        if(StrUtil.isEmpty(zxSkTurnoverIn.getOrgID()) || zxSkTurnoverIn.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkTurnoverIn.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkTurnoverIn.getBusDate());
        int month = DateUtil.month(zxSkTurnoverIn.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkTurnoverIn.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkTurnoverInMapper.getZxSkTurnoverInCount(result,orgID));
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
    public List<ZxSkTurnoverIn> ureportZxSkTurnoverInList(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // ????????????
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverInMapper.getZxSkTurnoverInListForReport(zxSkTurnoverIn);
        return zxSkTurnoverInList;
    }

    @Override
    public ResponseEntity getUreportZxSkTurnoverInList(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // ????????????
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverInMapper.getZxSkTurnoverInListForReport(zxSkTurnoverIn);
        return repEntity.ok(zxSkTurnoverInList);
    }

}
