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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkReturns.setCompanyId("");
            zxSkReturns.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkReturns.setCompanyId(zxSkReturns.getOrgID());
            zxSkReturns.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkReturns.setOrgID(zxSkReturns.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSkReturns.getPage(),zxSkReturns.getLimit());
        // 获取数据
        List<ZxSkReturns> zxSkReturnsList = zxSkReturnsMapper.selectByZxSkReturnsList(zxSkReturns);
        //查询明细
        for (ZxSkReturns zxSkReturns1 : zxSkReturnsList) {
            ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
            zxSkReturnsItem.setBillID(zxSkReturns1.getId());
            List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
            zxSkReturns1.setZxSkReturnsItemList(zxSkReturnsItems);
        }
        // 得到分页信息
        PageInfo<ZxSkReturns> p = new PageInfo<>(zxSkReturnsList);

        return repEntity.okList(zxSkReturnsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkReturnsDetail(ZxSkReturns zxSkReturns) {
        if (zxSkReturns == null) {
            zxSkReturns = new ZxSkReturns();
        }
        // 获取数据
        ZxSkReturns dbZxSkReturns = zxSkReturnsMapper.selectByPrimaryKey(zxSkReturns.getId());
        // 数据存在
        if (dbZxSkReturns != null) {
            ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
            zxSkReturnsItem.setBillID(dbZxSkReturns.getId());
            List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
            dbZxSkReturns.setZxSkReturnsItemList(zxSkReturnsItems);
            return repEntity.ok(dbZxSkReturns);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkReturns(ZxSkReturns zxSkReturns) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkReturns.setId(UuidUtil.generate());
        zxSkReturns.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkReturns.setStatus("0");
        //创建明细
        List<ZxSkReturnsItem> zxSkReturnsItemList = zxSkReturns.getZxSkReturnsItemList();
        if(CollUtil.isNotEmpty(zxSkReturnsItemList)) {
            for (ZxSkReturnsItem zxSkReturnsItem : zxSkReturnsItemList) {
                //是否结算 0:否.1:是
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
           // 退货单位ID
           dbZxSkReturns.setOrgID(zxSkReturns.getOrgID());
           // 退货单位
           dbZxSkReturns.setOrgName(zxSkReturns.getOrgName());
           // 单据编号
           dbZxSkReturns.setBillNo(zxSkReturns.getBillNo());
           // 退货日期
           dbZxSkReturns.setBusDate(zxSkReturns.getBusDate());
           // 审核状态
           dbZxSkReturns.setStatus(zxSkReturns.getStatus());
           // 供货单位ID
           dbZxSkReturns.setOutOrgID(zxSkReturns.getOutOrgID());
           // 供货单位
           dbZxSkReturns.setOutOrgName(zxSkReturns.getOutOrgName());
           // 税率(%)
           dbZxSkReturns.setTaxRate(zxSkReturns.getTaxRate());
           // 是否抵扣
           dbZxSkReturns.setIsDeduct(zxSkReturns.getIsDeduct());
           // 公司id
           dbZxSkReturns.setCompanyId(zxSkReturns.getCompanyId());
           // 公司名称
           dbZxSkReturns.setCompanyName(zxSkReturns.getCompanyName());
           // 备注
           dbZxSkReturns.setRemarks(zxSkReturns.getRemarks());
           // 排序
           dbZxSkReturns.setSort(zxSkReturns.getSort());
           // 共通
           dbZxSkReturns.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
            zxSkReturnsItem.setBillID(zxSkReturns.getId());
            List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
            if(zxSkReturnsItems != null && zxSkReturnsItems.size()>0) {
                zxSkReturnsItem.setModifyUserInfo(userKey, realName);
                zxSkReturnsItemMapper.batchDeleteUpdateZxSkReturnsItem(zxSkReturnsItems, zxSkReturnsItem);
            }
            //明细list
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
        // 失败
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
                // 删除明细
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkReturnsList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //“业主合同编号”+“周材退字第”+“年份-月份-顺序号”+“号”，
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
        String no = contractNo + " 周材退字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkReturnsResourceList(ZxSkReturns zxSkReturns) {
        //退货单位orgID,供货单位outOrgID
        if (StrUtil.isEmpty(zxSkReturns.getOrgID()) ||
                StrUtil.isEmpty(zxSkReturns.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkReturns.getPage(),zxSkReturns.getLimit());
        // 获取数据(通过库存)
        List<ZxSkReturnsItem> zxSkReturnsItems = zxSkReturnsMapper.getZxSkReturnsResourceList(zxSkReturns);
        // 得到分页信息
        PageInfo<ZxSkReturnsItem> p = new PageInfo<>(zxSkReturnsItems);
        return repEntity.okList(zxSkReturnsItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkReturns(ZxSkReturns zxSkReturns) {
        //然后审核数据
        ZxSkReturns dbZxSkReturns = zxSkReturnsMapper.selectByPrimaryKey(zxSkReturns.getId());
        if (StrUtil.equals(dbZxSkReturns.getStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkReturnsItem zxSkReturnsItem = new ZxSkReturnsItem();
        zxSkReturnsItem.setBillID(dbZxSkReturns.getId());
        List<ZxSkReturnsItem> zxSkReturnsItemList = zxSkReturnsItemMapper.selectByZxSkReturnsItemList(zxSkReturnsItem);
        if (CollUtil.isEmpty(zxSkReturnsItemList)) {
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //聚合 数量
        List<ZxSkReturnsItem> zxSkReturnsItemListNew = new ArrayList<>();
        zxSkReturnsItemList.parallelStream()
                //collect就是一个归约操作，就像reduce一样可以接受各种做法作为参数，将流中的元素累积成一个汇总结果
                .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
                (id, transfer) -> {
                    transfer.stream().reduce((a, b) -> new ZxSkReturnsItem(
                            //批次
                            a.getBatchNo(),
                            //退货数量
                            CalcUtils.calcAdd(a.getReturnQty(), b.getReturnQty()),
                            //originAmt 退货原值
                            CalcUtils.calcAdd(a.getOriginAmt(), b.getOriginAmt()),
                            //remainAmt	退货净值
                            CalcUtils.calcAdd(a.getRemainAmt(), b.getRemainAmt())
                    )).ifPresent(zxSkReturnsItemListNew::add);
                }
        );
        //库存List
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkReturnsItem item : zxSkReturnsItemListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //公司ID
//            zxSkTurnOverStock.setCompanyID(zxSkTurnoverOut.getCompanyID());
            //项目ID(发料单位Id)
            zxSkTurnOverStock.setOrgID(dbZxSkReturns.getOrgID());
            //项目名称
            zxSkTurnOverStock.setOrgName(dbZxSkReturns.getOrgName());
            //物资Id
            //资源编号
            //资源名称
            //规格型号spec
            //单位unit
            //批次(明细id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //入库数量/数量
            zxSkTurnOverStock.setStockQty(item.getReturnQty());
            //调拨原值
            zxSkTurnOverStock.setOriginalAmt(item.getOriginAmt());
            //调拨净值
            zxSkTurnOverStock.setRemainAmt(item.getRemainAmt());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //退货
        //先减少库存
        ResponseEntity responseEntity = zxSkTurnOverStockService.reduceZxSkStock(zxSkTurnOverStockList);
        if (responseEntity.isSuccess()) {
            dbZxSkReturns.setStatus("1");
            dbZxSkReturns.setModifyUserInfo(userKey, realName);
            //这里顺便把净值原值也修改了.
            flag = zxSkReturnsMapper.checkZxSkReturns(dbZxSkReturns);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核失败");
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
