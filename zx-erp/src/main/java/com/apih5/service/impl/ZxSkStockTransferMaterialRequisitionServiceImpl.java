package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemMaterialRequisitionMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemWithdrawalMapper;
import com.apih5.mybatis.pojo.*;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockTransferMaterialRequisitionMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransferMaterialRequisition;
import com.apih5.service.ZxSkStockTransferMaterialRequisitionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferMaterialRequisitionService")
public class ZxSkStockTransferMaterialRequisitionServiceImpl implements ZxSkStockTransferMaterialRequisitionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferMaterialRequisitionMapper zxSkStockTransferMaterialRequisitionMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemMaterialRequisitionMapper zxSkStockTransItemMaterialRequisitionMapper;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private SequenceService sequenceService;

//    @Autowired(required = true)
//    private ZxSkResCategoryMaterialsMapper zxSkResCategoryMaterialsMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemWithdrawalMapper zxSkStockTransItemWithdrawalMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionListByCondition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (zxSkStockTransferMaterialRequisition == null) {
            zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferMaterialRequisition.setCompanyId("");
            zxSkStockTransferMaterialRequisition.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferMaterialRequisition.getOrgID2())){
                zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkStockTransferMaterialRequisition.setCompanyId(zxSkStockTransferMaterialRequisition.getOutOrgID());
            zxSkStockTransferMaterialRequisition.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferMaterialRequisition.getOrgID2())){
                zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOutOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferMaterialRequisition.getOrgID2())){
                zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // ????????????
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList = zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionList(zxSkStockTransferMaterialRequisition);

        //????????????
        for (ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition1 : zxSkStockTransferMaterialRequisitionList) {
            ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
            zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition1.getId());
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitions = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
            zxSkStockTransferMaterialRequisition1.setZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisitions);
        }
        // ??????????????????
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);

        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionDetails(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (zxSkStockTransferMaterialRequisition == null) {
            zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
        }
        // ????????????
        ZxSkStockTransferMaterialRequisition dbZxSkStockTransferMaterialRequisition = zxSkStockTransferMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransferMaterialRequisition.getId());
        // ????????????
        if (dbZxSkStockTransferMaterialRequisition != null) {
            ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
            zxSkStockTransItemMaterialRequisition.setBillID(dbZxSkStockTransferMaterialRequisition.getId());
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitions = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
            dbZxSkStockTransferMaterialRequisition.setZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisitions);
            return repEntity.ok(dbZxSkStockTransferMaterialRequisition);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferMaterialRequisition.setId(UuidUtil.generate());
        zxSkStockTransferMaterialRequisition.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkStockTransferMaterialRequisition.setBillStatus("0");
        //????????????
        BigDecimal total = new BigDecimal(0);
        //????????????
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList = zxSkStockTransferMaterialRequisition.getZxSkStockTransItemMaterialRequisitionList();
        if(zxSkStockTransItemMaterialRequisitionList != null && zxSkStockTransItemMaterialRequisitionList.size()>0) {
            for (ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition : zxSkStockTransItemMaterialRequisitionList) {
                //????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemMaterialRequisition.getOutAmt());
                }
                //???????????? ->???????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getStdPrice())!=0){
                    zxSkStockTransItemMaterialRequisition.setOutCostPrice(zxSkStockTransItemMaterialRequisition.getStdPrice());
                }else {
                    zxSkStockTransItemMaterialRequisition.setOutCostPrice(new BigDecimal(0));
                }

                //?????? -> ??????????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getOutAmt())!=0){
                    zxSkStockTransItemMaterialRequisition.setOutCostAmt(zxSkStockTransItemMaterialRequisition.getOutAmt());
                }else {
                    zxSkStockTransItemMaterialRequisition.setOutCostAmt(new BigDecimal(0));
                }

                //???????????? -> ????????????
                if(CalcUtils.compareToZero(zxSkStockTransItemMaterialRequisition.getOutQty())!=0){
                    zxSkStockTransItemMaterialRequisition.setIsOutNumber(zxSkStockTransItemMaterialRequisition.getOutQty());
                }else {
                    zxSkStockTransItemMaterialRequisition.setIsOutNumber(new BigDecimal(0));
                }
                zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition.getId());
                zxSkStockTransItemMaterialRequisition.setId((UuidUtil.generate()));
                zxSkStockTransItemMaterialRequisition.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemMaterialRequisitionMapper.insert(zxSkStockTransItemMaterialRequisition);
            }
        }
        zxSkStockTransferMaterialRequisition.setTotalAmt(total);
        int flag = zxSkStockTransferMaterialRequisitionMapper.insert(zxSkStockTransferMaterialRequisition);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferMaterialRequisition);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferMaterialRequisition dbzxSkStockTransferMaterialRequisition = zxSkStockTransferMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransferMaterialRequisition.getId());
        if (dbzxSkStockTransferMaterialRequisition != null && StrUtil.isNotEmpty(dbzxSkStockTransferMaterialRequisition.getId())) {
           // ??????(1)
           dbzxSkStockTransferMaterialRequisition.setOrgID(zxSkStockTransferMaterialRequisition.getOrgID());
           // ??????(2)
           dbzxSkStockTransferMaterialRequisition.setWhOrgID(zxSkStockTransferMaterialRequisition.getWhOrgID());
           // ????????????ID
           dbzxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferMaterialRequisition.getOutOrgID());
           // ????????????ID
           dbzxSkStockTransferMaterialRequisition.setInOrgID(zxSkStockTransferMaterialRequisition.getInOrgID());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setBizType(zxSkStockTransferMaterialRequisition.getBizType());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setBillNo(zxSkStockTransferMaterialRequisition.getBillNo());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setMaterialSource(zxSkStockTransferMaterialRequisition.getMaterialSource());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setPurpose(zxSkStockTransferMaterialRequisition.getPurpose());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setBusDate(zxSkStockTransferMaterialRequisition.getBusDate());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setOutOrgName(zxSkStockTransferMaterialRequisition.getOutOrgName());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setInOrgName(zxSkStockTransferMaterialRequisition.getInOrgName());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setTotalAmt(zxSkStockTransferMaterialRequisition.getTotalAmt());
           // ?????????(1)
           dbzxSkStockTransferMaterialRequisition.setOuttransactor(zxSkStockTransferMaterialRequisition.getOuttransactor());
           // ?????????(2)
           dbzxSkStockTransferMaterialRequisition.setIntransactor(zxSkStockTransferMaterialRequisition.getIntransactor());
           // ???????????????
           dbzxSkStockTransferMaterialRequisition.setWaretransactor(zxSkStockTransferMaterialRequisition.getWaretransactor());
           // ??????
           dbzxSkStockTransferMaterialRequisition.setBuyer(zxSkStockTransferMaterialRequisition.getBuyer());
           // ??????
           dbzxSkStockTransferMaterialRequisition.setConsignee(zxSkStockTransferMaterialRequisition.getConsignee());
           // ?????????
           dbzxSkStockTransferMaterialRequisition.setAuditor(zxSkStockTransferMaterialRequisition.getAuditor());
           // ?????????
           dbzxSkStockTransferMaterialRequisition.setVoucherNo(zxSkStockTransferMaterialRequisition.getVoucherNo());
           // ?????????
           dbzxSkStockTransferMaterialRequisition.setContractNo(zxSkStockTransferMaterialRequisition.getContractNo());
           // ?????????
           dbzxSkStockTransferMaterialRequisition.setInvoiceNo(zxSkStockTransferMaterialRequisition.getInvoiceNo());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setBillType(zxSkStockTransferMaterialRequisition.getBillType());
           // ??????
           dbzxSkStockTransferMaterialRequisition.setBillFlag(zxSkStockTransferMaterialRequisition.getBillFlag());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setBillStatus(zxSkStockTransferMaterialRequisition.getBillStatus());
           // ?????????
           dbzxSkStockTransferMaterialRequisition.setReporter(zxSkStockTransferMaterialRequisition.getReporter());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setDeductAmtType(zxSkStockTransferMaterialRequisition.getDeductAmtType());
           // ??????
           dbzxSkStockTransferMaterialRequisition.setRemark(zxSkStockTransferMaterialRequisition.getRemark());
           // ?????????????????????????????????
           dbzxSkStockTransferMaterialRequisition.setInvoiceNum(zxSkStockTransferMaterialRequisition.getInvoiceNum());
           // ????????????ID
           dbzxSkStockTransferMaterialRequisition.setResourceID(zxSkStockTransferMaterialRequisition.getResourceID());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setResourceName(zxSkStockTransferMaterialRequisition.getResourceName());
           // ????????????ID
           dbzxSkStockTransferMaterialRequisition.setCbsID(zxSkStockTransferMaterialRequisition.getCbsID());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setCbsName(zxSkStockTransferMaterialRequisition.getCbsName());
           // ??????(3)
           dbzxSkStockTransferMaterialRequisition.setWarehouseName(zxSkStockTransferMaterialRequisition.getWarehouseName());
           // ??????id
           dbzxSkStockTransferMaterialRequisition.setCompanyId(zxSkStockTransferMaterialRequisition.getCompanyId());
           // ????????????
           dbzxSkStockTransferMaterialRequisition.setCompanyName(zxSkStockTransferMaterialRequisition.getCompanyName());
           // ??????
           dbzxSkStockTransferMaterialRequisition.setModifyUserInfo(userKey, realName);


            //???????????????
            ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
            zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition.getId());
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitions = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
            if(zxSkStockTransItemMaterialRequisitions != null && zxSkStockTransItemMaterialRequisitions.size()>0) {
                zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransItemMaterialRequisition(zxSkStockTransItemMaterialRequisitions, zxSkStockTransItemMaterialRequisition);
            }
            //??????list
            List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList = zxSkStockTransferMaterialRequisition.getZxSkStockTransItemMaterialRequisitionList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemMaterialRequisitionList != null && zxSkStockTransItemMaterialRequisitionList.size()>0) {
                for(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition1 : zxSkStockTransItemMaterialRequisitionList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemMaterialRequisition1.getOutAmt());
                    zxSkStockTransItemMaterialRequisition1.setId(UuidUtil.generate());
                    zxSkStockTransItemMaterialRequisition1.setBillID(zxSkStockTransferMaterialRequisition.getId());
                    zxSkStockTransItemMaterialRequisition1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemMaterialRequisitionMapper.insert(zxSkStockTransItemMaterialRequisition1);
                }
            }
            dbzxSkStockTransferMaterialRequisition.setTotalAmt(total);
            flag = zxSkStockTransferMaterialRequisitionMapper.updateByPrimaryKey(dbzxSkStockTransferMaterialRequisition);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferMaterialRequisition);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferMaterialRequisition(List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferMaterialRequisitionList != null && zxSkStockTransferMaterialRequisitionList.size() > 0) {
            for (ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition : zxSkStockTransferMaterialRequisitionList) {
                // ????????????
                ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
                zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition.getId());
                List<ZxSkStockTransItemMaterialRequisition> deleteZxSkStockTransItems = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
                if(deleteZxSkStockTransItems != null && deleteZxSkStockTransItems.size()>0) {
                    zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransItemMaterialRequisition(deleteZxSkStockTransItems, zxSkStockTransItemMaterialRequisition);
                }
            }
           ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
           zxSkStockTransferMaterialRequisition.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferMaterialRequisitionMapper.batchDeleteUpdateZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisitionList, zxSkStockTransferMaterialRequisition);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferMaterialRequisitionList);
        }
    }

    //??????
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferMaterialRequisition(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        //????????????
        ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition1 = zxSkStockTransferMaterialRequisitionMapper.selectByPrimaryKey(zxSkStockTransferMaterialRequisition.getId());
        if(StrUtil.equals(zxSkStockTransferMaterialRequisition1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        //????????????
        ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
        zxSkStockTransItemMaterialRequisition.setBillID(zxSkStockTransferMaterialRequisition1.getId());
        List<ZxSkStockTransItemMaterialRequisition> itemList = zxSkStockTransItemMaterialRequisitionMapper.selectByZxSkStockTransItemMaterialRequisitionList(zxSkStockTransItemMaterialRequisition);
        if(CollUtil.isEmpty(itemList)){
            return repEntity.layerMessage("no", "????????????????????????,????????????");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //???????????????
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionListNew = new ArrayList<>();
        itemList.parallelStream()
                .collect(Collectors.groupingBy(o ->
                        (o.getResID()+o.getStdPrice()), Collectors.toList())).forEach(
                (id,transfer) ->{
                    transfer.stream().reduce((a,b)-> new ZxSkStockTransItemMaterialRequisition(
                            //resId
                            a.getResID(),
                            a.getResCode(),
                            a.getResName(),
                            a.getSpec(),
                            a.getResUnit(),
                            a.getStdPrice(),
                            CalcUtils.calcAdd(a.getOutQty(),b.getOutQty()),
                            CalcUtils.calcAdd(a.getOutAmt(),b.getOutAmt())
                    )).ifPresent(zxSkStockTransItemMaterialRequisitionListNew::add);
                }
        );
        //??????List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemMaterialRequisition item : zxSkStockTransItemMaterialRequisitionListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //??????ID
            zxSkStock.setCompanyID(zxSkStockTransferMaterialRequisition1.getCompanyId());
            //??????ID(????????????Id)
            zxSkStock.setOrgID(zxSkStockTransferMaterialRequisition1.getOutOrgID());
            //??????ID
            zxSkStock.setWhOrgID(zxSkStockTransferMaterialRequisition1.getWhOrgID());
            //????????????resourceId
            zxSkStock.setResourceId(zxSkStockTransferMaterialRequisition1.getResourceID());
            //??????????????????resourceName
            zxSkStock.setResourceName(zxSkStockTransferMaterialRequisition1.getResourceName());
            //??????Id
            zxSkStock.setResID(item.getResID());
            //????????????
            zxSkStock.setResCode(item.getResCode());
            //????????????
            zxSkStock.setResName(item.getResName());
            //????????????spec
            zxSkStock.setSpec(item.getSpec());
            //??????unit
            zxSkStock.setUnit(item.getResUnit());
//          batchNo
            //????????????
            zxSkStock.setStockQty(item.getOutQty());
            //????????????
            zxSkStock.setStockPrice(item.getStdPrice());
            //????????????
            zxSkStock.setStockAmt(item.getOutAmt());
            zxSkStockList.add(zxSkStock);
        }
        //??????????????????????????????.(???????????????????????????????????????,???????????????,?????????????????????????????????????????????)
        ResponseEntity responseEntity = ZxSkStockServiceImpl.reduceZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferMaterialRequisition1.setBillStatus("1");
            zxSkStockTransferMaterialRequisition1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferMaterialRequisitionMapper.checkZxSkStockTransferMaterialRequisition(zxSkStockTransferMaterialRequisition1);
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

    //???????????????+???????????????+?????????-??????-????????????+??????
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionNo(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if(StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID()) || zxSkStockTransferMaterialRequisition.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferMaterialRequisition.getOutOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferMaterialRequisition.getBusDate());
        int month = DateUtil.month(zxSkStockTransferMaterialRequisition.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferMaterialRequisition.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferMaterialRequisitionMapper.getZxSkStockTransferMaterialRequisitionCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " ????????? " + result + "-" + str + " ???";
        return repEntity.ok(no);
    }

    //whOrgID,outOrgID
    //(????????????id,??????id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionInOrgName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if ( StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // ????????????
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionInOrgNameList(zxSkStockTransferMaterialRequisition);
        // ??????????????????
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    //whOrgID,outOrgID
    //(????????????id,??????id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionCbsName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // ????????????
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionCbsNameList(zxSkStockTransferMaterialRequisition);
        // ??????????????????
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    //whOrgID,outOrgID ,inOrgID
    //(????????????id,??????id,????????????id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionResourceName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getInOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "????????????");
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // ????????????
        List<ZxSkStockTransferMaterialRequisition> zxSkStockTransferMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionResourceNameList(zxSkStockTransferMaterialRequisition);
        // ??????????????????
        PageInfo<ZxSkStockTransferMaterialRequisition> p = new PageInfo<>(zxSkStockTransferMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransferMaterialRequisitionList, p.getTotal());
    }

    //whOrgID,outOrgID ,inOrgID,resourceID
    //(????????????id,??????id,????????????id,??????id)
    @Override
    public ResponseEntity getZxSkStockTransferMaterialRequisitionResName(ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition) {
        if (StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferMaterialRequisition.getResourceID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // ????????????
        PageHelper.startPage(zxSkStockTransferMaterialRequisition.getPage(),zxSkStockTransferMaterialRequisition.getLimit());
        // ????????????
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList =
                zxSkStockTransferMaterialRequisitionMapper.selectByZxSkStockTransferMaterialRequisitionResNameList(zxSkStockTransferMaterialRequisition);
        // ??????????????????
        PageInfo<ZxSkStockTransItemMaterialRequisition> p = new PageInfo<>(zxSkStockTransItemMaterialRequisitionList);
        return repEntity.okList(zxSkStockTransItemMaterialRequisitionList, p.getTotal());
    }




}
