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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnoverOut.setCompanyId("");
            zxSkTurnoverOut.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverOut.getOrgID2())){
                zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTurnoverOut.setCompanyId(zxSkTurnoverOut.getOrgID());
            zxSkTurnoverOut.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverOut.getOrgID2())){
                zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID());
            if(StrUtil.isNotEmpty(zxSkTurnoverOut.getOrgID2())){
                zxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverOut.getPage(),zxSkTurnoverOut.getLimit());
        // 获取数据
        List<ZxSkTurnoverOut> zxSkTurnoverOutList = zxSkTurnoverOutMapper.selectByZxSkTurnoverOutList(zxSkTurnoverOut);
        //查询明细
        for (ZxSkTurnoverOut zxSkTurnoverOut1 : zxSkTurnoverOutList) {
            ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
            zxSkTurnoverOutItem.setBillID(zxSkTurnoverOut1.getId());
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
            //把归还的值放到一个新值上
            for (ZxSkTurnoverOutItem skTurnoverOutItem : zxSkTurnoverOutItems) {
                if(skTurnoverOutItem.getHasReturnQty()==null){
                    skTurnoverOutItem.setOldhasReturnQty(new BigDecimal(0));
                }else {
                    skTurnoverOutItem.setOldhasReturnQty(skTurnoverOutItem.getHasReturnQty());
                }
            }

            zxSkTurnoverOut1.setZxSkTurnoverOutItemList(zxSkTurnoverOutItems);
        }
        // 得到分页信息
        PageInfo<ZxSkTurnoverOut> p = new PageInfo<>(zxSkTurnoverOutList);

        return repEntity.okList(zxSkTurnoverOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverOutDetail(ZxSkTurnoverOut zxSkTurnoverOut) {
        if (zxSkTurnoverOut == null) {
            zxSkTurnoverOut = new ZxSkTurnoverOut();
        }
        // 获取数据
        ZxSkTurnoverOut dbZxSkTurnoverOut = zxSkTurnoverOutMapper.selectByPrimaryKey(zxSkTurnoverOut.getId());
        // 数据存在
        if (dbZxSkTurnoverOut != null) {
            ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
            zxSkTurnoverOutItem.setBillID(dbZxSkTurnoverOut.getId());
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
            //把归还的值放到一个新值上
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
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverOut.setId(UuidUtil.generate());
        zxSkTurnoverOut.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkTurnoverOut.setStatus("0");
        //创建明细
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
           // 发料单位ID
           dbZxSkTurnoverOut.setOrgID(zxSkTurnoverOut.getOrgID());
           // 发料单位
           dbZxSkTurnoverOut.setOrgName(zxSkTurnoverOut.getOrgName());
           // 单据编号
           dbZxSkTurnoverOut.setBillNo(zxSkTurnoverOut.getBillNo());
           // 出库日期
           dbZxSkTurnoverOut.setBusDate(zxSkTurnoverOut.getBusDate());
           // 领料单位id
           dbZxSkTurnoverOut.setInOrgID(zxSkTurnoverOut.getInOrgID());
           // 领料单位
           dbZxSkTurnoverOut.setInOrgName(zxSkTurnoverOut.getInOrgName());
           // 物资员
           dbZxSkTurnoverOut.setConsignee(zxSkTurnoverOut.getConsignee());
           // 单据状态
           dbZxSkTurnoverOut.setBillStatus(zxSkTurnoverOut.getBillStatus());
           // 审核状态
           dbZxSkTurnoverOut.setStatus(zxSkTurnoverOut.getStatus());
           // 公司id
           dbZxSkTurnoverOut.setCompanyId(zxSkTurnoverOut.getCompanyId());
           // 公司名称
           dbZxSkTurnoverOut.setCompanyName(zxSkTurnoverOut.getCompanyName());
           // 备注
           dbZxSkTurnoverOut.setRemarks(zxSkTurnoverOut.getRemarks());
           // 排序
           dbZxSkTurnoverOut.setSort(zxSkTurnoverOut.getSort());
           // 共通
           dbZxSkTurnoverOut.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkTurnoverOutItem zxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
            zxSkTurnoverOutItem.setBillID(zxSkTurnoverOut.getId());
            List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(zxSkTurnoverOutItem);
            if(zxSkTurnoverOutItems != null && zxSkTurnoverOutItems.size()>0) {
                zxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
                zxSkTurnoverOutItemMapper.batchDeleteUpdateZxSkTurnoverOutItem(zxSkTurnoverOutItems, zxSkTurnoverOutItem);
            }
            //明细list
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
        // 失败
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
                // 删除明细
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverOutList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //业主“合同编号”+“周材领字第”+“年份-月份-001号”
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
        String no = contractNo + " 周材领字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    //orgID
    //项目id
    @Override
    public ResponseEntity getZxSkTurnoverOutResourceList(ZxSkTurnoverOut zxSkTurnoverOut) {
        if (StrUtil.isEmpty(zxSkTurnoverOut.getOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverOut.getPage(),zxSkTurnoverOut.getLimit());
        // 获取数据(通过库存)
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItems = zxSkTurnoverOutMapper.getZxSkTurnoverOutResourceList(zxSkTurnoverOut);
        // 得到分页信息
        PageInfo<ZxSkTurnoverOutItem> p = new PageInfo<>(zxSkTurnoverOutItems);
        return repEntity.okList(zxSkTurnoverOutItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        //审核数据
        ZxSkTurnoverOut dbZxSkTurnoverOut = zxSkTurnoverOutMapper.selectByPrimaryKey(zxSkTurnoverOut.getId());
        if(StrUtil.equals(dbZxSkTurnoverOut.getStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkTurnoverOutItem dbzxSkTurnoverOutItem = new ZxSkTurnoverOutItem();
        dbzxSkTurnoverOutItem.setBillID(dbZxSkTurnoverOut.getId());
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOutItemMapper.selectByZxSkTurnoverOutItemList(dbzxSkTurnoverOutItem);
        //把归还的值放到一个新值上
        for (ZxSkTurnoverOutItem skTurnoverOutItem : zxSkTurnoverOutItemList) {
            if(skTurnoverOutItem.getHasReturnQty()==null){
                skTurnoverOutItem.setOldhasReturnQty(new BigDecimal(0));
            }else {
                skTurnoverOutItem.setOldhasReturnQty(skTurnoverOutItem.getHasReturnQty());
            }
        }
        if(CollUtil.isEmpty(zxSkTurnoverOutItemList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //聚合
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemListNew = new ArrayList<>();
        zxSkTurnoverOutItemList.parallelStream()
                //collect就是一个归约操作，就像reduce一样可以接受各种做法作为参数，将流中的元素累积成一个汇总结果
                .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
                (id,transfer) -> {
                    transfer.stream().reduce((a,b) -> new ZxSkTurnoverOutItem(
                            //批次
                            a.getBatchNo(),
                            //数量
                            CalcUtils.calcAdd(a.getOutQty(),b.getOutQty())
                    )).ifPresent(zxSkTurnoverOutItemListNew::add);
                }
        );
        //库存List
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnoverOutItem item : zxSkTurnoverOutItemListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //公司ID
//            zxSkTurnOverStock.setCompanyID(dbZxSkTurnoverOut.getCompanyID());
            //项目ID(发料单位Id)
            zxSkTurnOverStock.setOrgID(dbZxSkTurnoverOut.getOrgID());
            //项目名称
            zxSkTurnOverStock.setOrgName(dbZxSkTurnoverOut.getOrgName());
            //物资Id
            //资源编号
            //资源名称
            //规格型号spec
            //单位unit
            //批次(明细id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //入库数量/数量
            zxSkTurnOverStock.setStockQty(item.getOutQty());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //先减少库存
        ResponseEntity responseEntity = zxSkTurnOverStockService.reduceZxSkStock(zxSkTurnOverStockList);
        if(responseEntity.isSuccess()){
            dbZxSkTurnoverOut.setStatus("1");
            dbZxSkTurnoverOut.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverOutMapper.checkZxSkTurnoverOut(dbZxSkTurnoverOut);
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
        }else {
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity returnZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //然后审核数据
        if(!StrUtil.equals(zxSkTurnoverOut.getStatus(), "1")) {
            return repEntity.layerMessage("no", "非审核的单据，请重新选择！");
        }
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList = zxSkTurnoverOut.getZxSkTurnoverOutItemList();
        if(CollUtil.isEmpty(zxSkTurnoverOutItemList)){
            return repEntity.layerMessage("no", "该数据无明细，请重新选择！");
        }
        for (ZxSkTurnoverOutItem zxSkTurnoverOutItem : zxSkTurnoverOutItemList) {
            //当前归还数量跟减去上一次归还数量
            zxSkTurnoverOutItem.setOldhasReturnQty(
                    CalcUtils.calcSubtract(
                            zxSkTurnoverOutItem.getHasReturnQty(),zxSkTurnoverOutItem.getOldhasReturnQty()));
        }

        int flag = 0;
        //聚合
        List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemListNew = new ArrayList<>();
        zxSkTurnoverOutItemList.parallelStream()
            //collect就是一个归约操作，就像reduce一样可以接受各种做法作为参数，将流中的元素累积成一个汇总结果
            .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
            (id,transfer) -> {
                transfer.stream().reduce((a,b) -> new ZxSkTurnoverOutItem(
                        //批次
                        a.getBatchNo(),
                        //resID,
                        a.getResID(),
                        //归还数量
                        CalcUtils.calcAdd(a.getOldhasReturnQty(),b.getOldhasReturnQty())
                )).ifPresent(zxSkTurnoverOutItemListNew::add);
            }
        );
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnoverOutItem item : zxSkTurnoverOutItemListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //公司ID
//            zxSkTurnOverStock.setCompanyID(zxSkTurnoverOut.getCompanyID());
            //项目ID(发料单位Id)
            zxSkTurnOverStock.setOrgID(zxSkTurnoverOut.getOrgID());
            //项目名称
            zxSkTurnOverStock.setOrgName(zxSkTurnoverOut.getOrgName());
            //批次(明细id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //归还数量
            zxSkTurnOverStock.setStockQty(item.getOldhasReturnQty());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //先添加库存
        ResponseEntity responseEntity = zxSkTurnOverStockService.returnZxSkStock(zxSkTurnOverStockList);
        if(responseEntity.isSuccess()){
            zxSkTurnoverOut.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverOutMapper.updateByPrimaryKey(zxSkTurnoverOut);
            for (ZxSkTurnoverOutItem zxSkTurnoverOutItem : zxSkTurnoverOutItemList) {
                zxSkTurnoverOutItem.setModifyUserInfo(userKey, realName);
                flag = zxSkTurnoverOutItemMapper.updateByPrimaryKey(zxSkTurnoverOutItem);
            }
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
        }else {
            return responseEntity;
        }
    }



}
