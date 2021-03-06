package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxSkTurnOverFeeShareItemMapper;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShareItem;
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkTurnOverFeeShareMapper;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShare;
import com.apih5.service.ZxSkTurnOverFeeShareService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnOverFeeShareService")
public class ZxSkTurnOverFeeShareServiceImpl implements ZxSkTurnOverFeeShareService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverFeeShareMapper zxSkTurnOverFeeShareMapper;

    @Autowired(required = true)
    private ZxSkTurnOverFeeShareItemMapper zxSkTurnOverFeeShareItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;



    @Override
    public ResponseEntity getZxSkTurnOverFeeShareListByCondition(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        if (zxSkTurnOverFeeShare == null) {
            zxSkTurnOverFeeShare = new ZxSkTurnOverFeeShare();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnOverFeeShare.setCompanyId("");
            zxSkTurnOverFeeShare.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkTurnOverFeeShare.setCompanyId(zxSkTurnOverFeeShare.getOrgID());
            zxSkTurnOverFeeShare.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkTurnOverFeeShare.setOrgID(zxSkTurnOverFeeShare.getOrgID());
        }

        // ????????????
        PageHelper.startPage(zxSkTurnOverFeeShare.getPage(),zxSkTurnOverFeeShare.getLimit());
        // ????????????
        List<ZxSkTurnOverFeeShare> zxSkTurnOverFeeShareList = zxSkTurnOverFeeShareMapper.selectByZxSkTurnOverFeeShareList(zxSkTurnOverFeeShare);
        //????????????
        for (ZxSkTurnOverFeeShare zxSkTurnOverFeeShare1 : zxSkTurnOverFeeShareList) {
            ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
            zxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShare1.getId());
            List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
            zxSkTurnOverFeeShare1.setZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItems);
        }
        // ??????????????????
        PageInfo<ZxSkTurnOverFeeShare> p = new PageInfo<>(zxSkTurnOverFeeShareList);

        return repEntity.okList(zxSkTurnOverFeeShareList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverFeeShareDetail(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        if (zxSkTurnOverFeeShare == null) {
            zxSkTurnOverFeeShare = new ZxSkTurnOverFeeShare();
        }
        // ????????????
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        // ????????????
        if (dbZxSkTurnOverFeeShare != null) {
            ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
            zxSkTurnOverFeeShareItem.setBillID(dbZxSkTurnOverFeeShare.getId());
            List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
            dbZxSkTurnOverFeeShare.setZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItems);
            return repEntity.ok(dbZxSkTurnOverFeeShare);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverFeeShare.setId(UuidUtil.generate());
        zxSkTurnOverFeeShare.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkTurnOverFeeShare.setBillStatus("0");
        //????????????
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShare.getZxSkTurnOverFeeShareItemList();
        if(zxSkTurnOverFeeShareItemList != null && zxSkTurnOverFeeShareItemList.size()>0) {
            for (ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem : zxSkTurnOverFeeShareItemList) {
                if(zxSkTurnOverFeeShareItem.getShareAmt()==null){
                    zxSkTurnOverFeeShareItem.setShareAmt(new BigDecimal(0));
                }
                zxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShare.getId());
                zxSkTurnOverFeeShareItem.setId((UuidUtil.generate()));
                zxSkTurnOverFeeShareItem.setCreateUserInfo(userKey, realName);
                zxSkTurnOverFeeShareItemMapper.insert(zxSkTurnOverFeeShareItem);
            }
        }
        int flag = zxSkTurnOverFeeShareMapper.insert(zxSkTurnOverFeeShare);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverFeeShare);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        if (dbZxSkTurnOverFeeShare != null && StrUtil.isNotEmpty(dbZxSkTurnOverFeeShare.getId())) {
           // ????????????ID
           dbZxSkTurnOverFeeShare.setOrgID(zxSkTurnOverFeeShare.getOrgID());
           // ????????????
           dbZxSkTurnOverFeeShare.setOrgName(zxSkTurnOverFeeShare.getOrgName());
           // ????????????
           dbZxSkTurnOverFeeShare.setBillNo(zxSkTurnOverFeeShare.getBillNo());
           // ??????
           dbZxSkTurnOverFeeShare.setBusDate(zxSkTurnOverFeeShare.getBusDate());
           // ?????????
           dbZxSkTurnOverFeeShare.setConsignee(zxSkTurnOverFeeShare.getConsignee());
           // ????????????
           dbZxSkTurnOverFeeShare.setBillStatus(zxSkTurnOverFeeShare.getBillStatus());
           // ??????id
           dbZxSkTurnOverFeeShare.setCompanyId(zxSkTurnOverFeeShare.getCompanyId());
           // ????????????
           dbZxSkTurnOverFeeShare.setCompanyName(zxSkTurnOverFeeShare.getCompanyName());
           // ??????
           dbZxSkTurnOverFeeShare.setRemarks(zxSkTurnOverFeeShare.getRemarks());
           // ??????
           dbZxSkTurnOverFeeShare.setSort(zxSkTurnOverFeeShare.getSort());
           // ??????
           dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey, realName);
            //???????????????
            ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
            zxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShare.getId());
            List<ZxSkTurnOverFeeShareItem> zxSkTurnoverInItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
            if(zxSkTurnoverInItems != null && zxSkTurnoverInItems.size()>0) {
                zxSkTurnOverFeeShareItem.setModifyUserInfo(userKey, realName);
                zxSkTurnOverFeeShareItemMapper.batchDeleteUpdateZxSkTurnOverFeeShareItem(zxSkTurnoverInItems, zxSkTurnOverFeeShareItem);
            }
            //??????list
            List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShare.getZxSkTurnOverFeeShareItemList();
            if(zxSkTurnOverFeeShareItemList != null && zxSkTurnOverFeeShareItemList.size()>0) {
                for(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem1 : zxSkTurnOverFeeShareItemList) {
                    if(zxSkTurnOverFeeShareItem1.getShareAmt()==null){
                        zxSkTurnOverFeeShareItem1.setShareAmt(new BigDecimal(0));
                    }
                    zxSkTurnOverFeeShareItem1.setId(UuidUtil.generate());
                    zxSkTurnOverFeeShareItem1.setBillID(zxSkTurnOverFeeShare.getId());
                    zxSkTurnOverFeeShareItem1.setCreateUserInfo(userKey, realName);
                    zxSkTurnOverFeeShareItemMapper.insert(zxSkTurnOverFeeShareItem1);
                }
            }
           flag = zxSkTurnOverFeeShareMapper.updateByPrimaryKey(dbZxSkTurnOverFeeShare);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnOverFeeShare);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverFeeShare(List<ZxSkTurnOverFeeShare> zxSkTurnOverFeeShareList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnOverFeeShareList != null && zxSkTurnOverFeeShareList.size() > 0) {
            for (ZxSkTurnOverFeeShare zxSkTurnOverFeeShare : zxSkTurnOverFeeShareList) {
                // ????????????
                ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
                zxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShare.getId());
                List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
                if(zxSkTurnOverFeeShareItems != null && zxSkTurnOverFeeShareItems.size()>0) {
                    zxSkTurnOverFeeShareItem.setModifyUserInfo(userKey, realName);
                    zxSkTurnOverFeeShareItemMapper.batchDeleteUpdateZxSkTurnOverFeeShareItem(zxSkTurnOverFeeShareItems, zxSkTurnOverFeeShareItem);
                }
            }
           ZxSkTurnOverFeeShare zxSkTurnOverFeeShare = new ZxSkTurnOverFeeShare();
           zxSkTurnOverFeeShare.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverFeeShareMapper.batchDeleteUpdateZxSkTurnOverFeeShare(zxSkTurnOverFeeShareList, zxSkTurnOverFeeShare);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverFeeShareList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    //?????????????????????+?????????????????????+?????????-??????-????????????+??????
    @Override
    public ResponseEntity getZxSkTurnOverFeeShareNo(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        if(StrUtil.isEmpty(zxSkTurnOverFeeShare.getOrgID())|| zxSkTurnOverFeeShare.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkTurnOverFeeShare.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkTurnOverFeeShare.getBusDate());
        int month = DateUtil.month(zxSkTurnOverFeeShare.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkTurnOverFeeShare.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkTurnOverFeeShareMapper.getZxSkTurnOverFeeShareCount(result,orgID));
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
    public ResponseEntity getZxSkTurnOverFeeShareResourceList(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        if (StrUtil.isEmpty(zxSkTurnOverFeeShare.getOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkTurnOverFeeShare.getPage(),zxSkTurnOverFeeShare.getLimit());
        // ????????????(????????????)
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareMapper.getZxSkTurnOverFeeShareResourceList(zxSkTurnOverFeeShare);
        // ??????????????????
        PageInfo<ZxSkTurnOverFeeShareItem> p = new PageInfo<>(zxSkTurnOverFeeShareItems);
        return repEntity.okList(zxSkTurnOverFeeShareItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        //?????????????????? (????????????????????????)
        //?????? (???????????????)
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        //??????????????????
        if(StrUtil.equals(dbZxSkTurnOverFeeShare.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "?????????????????????????????????");
        }
        //????????????
        ZxSkTurnOverFeeShareItem dbzxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
        dbzxSkTurnOverFeeShareItem.setBillID(dbZxSkTurnOverFeeShare.getId());
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(dbzxSkTurnOverFeeShareItem);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //????????????
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemListNew =
        zxSkTurnOverFeeShareItemList
            .stream()
            .filter(a -> CalcUtils.compareToZero(a.getShareAmt()) != 0)
            .collect(Collectors.toList());
//                        .forEach((o->o.setModifyUserInfo(userKey,realName)));
//                        .collect(Collectors.toMap(ZxSkTurnOverFeeShareItem::getBatchNo, a -> a, (k1, k2) -> k1));
        zxSkTurnOverFeeShareItemListNew.stream().forEach((o->o.setModifyUserInfo(userKey,realName)));
        if(CollectionUtil.isEmpty(zxSkTurnOverFeeShareItemListNew)){
            //??????
            dbZxSkTurnOverFeeShare.setBillStatus("1");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"????????????");
        }
        flag = zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShareUpdateNum(zxSkTurnOverFeeShareItemListNew);
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //??????
            dbZxSkTurnOverFeeShare.setBillStatus("1");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"????????????");
        }
    }

    @Override
    public synchronized ResponseEntity counterCheckZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        //??????????????????
        //?????????????????? (????????????????????????)
        //?????? (???????????????)
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        //??????????????????
        if(StrUtil.equals(dbZxSkTurnOverFeeShare.getBillStatus(), "0")) {
            return repEntity.layerMessage("no", "?????????????????????????????????");
        }
        ZxSkTurnOverFeeShareItem dbzxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
        dbzxSkTurnOverFeeShareItem.setBillID(dbZxSkTurnOverFeeShare.getId());
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(dbzxSkTurnOverFeeShareItem);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //????????????
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemListNew =
        zxSkTurnOverFeeShareItemList
                .stream()
                .filter(a -> CalcUtils.compareToZero(a.getShareAmt()) != 0)
                .collect(Collectors.toList());
//                        .forEach((o->o.setModifyUserInfo(userKey,realName)));
//                        .collect(Collectors.toMap(ZxSkTurnOverFeeShareItem::getBatchNo, a -> a, (k1, k2) -> k1));
        zxSkTurnOverFeeShareItemListNew.stream().forEach((o->o.setModifyUserInfo(userKey,realName)));
        if(CollectionUtil.isEmpty(zxSkTurnOverFeeShareItemListNew)){
            //??????
            dbZxSkTurnOverFeeShare.setBillStatus("0");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"???????????????");
        }
        flag = zxSkTurnOverFeeShareMapper.counterCheckZxSkTurnOverFeeShare(zxSkTurnOverFeeShareItemListNew);
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //??????
            dbZxSkTurnOverFeeShare.setBillStatus("0");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"???????????????");
        }
    }
}
