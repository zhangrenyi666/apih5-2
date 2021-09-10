package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxSkTurnOverTransferItemMapper;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxSkTurnOverStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkTurnOverTransferMapper;
import com.apih5.service.ZxSkTurnOverTransferService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("zxSkTurnOverTransferService")
public class ZxSkTurnOverTransferServiceImpl implements ZxSkTurnOverTransferService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverTransferMapper zxSkTurnOverTransferMapper;

    @Autowired(required = true)
    private ZxSkTurnOverTransferItemMapper zxSkTurnOverTransferItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkTurnOverStockService zxSkTurnOverStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkTurnOverTransferListByCondition(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        if (zxSkTurnOverTransfer == null) {
            zxSkTurnOverTransfer = new ZxSkTurnOverTransfer();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnOverTransfer.setCompanyId("");
            zxSkTurnOverTransfer.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTurnOverTransfer.setCompanyId(zxSkTurnOverTransfer.getOrgID());
            zxSkTurnOverTransfer.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTurnOverTransfer.setOrgID(zxSkTurnOverTransfer.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverTransfer.getPage(), zxSkTurnOverTransfer.getLimit());
        // 获取数据
        List<ZxSkTurnOverTransfer> zxSkTurnOverTransferList = zxSkTurnOverTransferMapper.selectByZxSkTurnOverTransferList(zxSkTurnOverTransfer);
        //查询明细
        for (ZxSkTurnOverTransfer zxSkTurnOverTransfer1 : zxSkTurnOverTransferList) {
            ZxSkTurnOverTransferItem zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
            zxSkTurnOverTransferItem.setBillID(zxSkTurnOverTransfer1.getId());
            List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItems = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(zxSkTurnOverTransferItem);
            zxSkTurnOverTransfer1.setZxSkTurnOverTransferItemsList(zxSkTurnOverTransferItems);
        }
        // 得到分页信息
        PageInfo<ZxSkTurnOverTransfer> p = new PageInfo<>(zxSkTurnOverTransferList);

        return repEntity.okList(zxSkTurnOverTransferList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverTransferDetail(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        if (zxSkTurnOverTransfer == null) {
            zxSkTurnOverTransfer = new ZxSkTurnOverTransfer();
        }
        // 获取数据
        ZxSkTurnOverTransfer dbZxSkTurnOverTransfer = zxSkTurnOverTransferMapper.selectByPrimaryKey(zxSkTurnOverTransfer.getId());
        // 数据存在
        if (dbZxSkTurnOverTransfer != null) {
            ZxSkTurnOverTransferItem zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
            zxSkTurnOverTransferItem.setBillID(dbZxSkTurnOverTransfer.getId());
            List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItems = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(zxSkTurnOverTransferItem);
            dbZxSkTurnOverTransfer.setZxSkTurnOverTransferItemsList(zxSkTurnOverTransferItems);

            return repEntity.ok(dbZxSkTurnOverTransfer);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverTransfer.setId(UuidUtil.generate());
        zxSkTurnOverTransfer.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkTurnOverTransfer.setBillStatus("0");
        //创建明细
        List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemsList = zxSkTurnOverTransfer.getZxSkTurnOverTransferItemsList();
        if (zxSkTurnOverTransferItemsList != null && zxSkTurnOverTransferItemsList.size() > 0) {
            for (ZxSkTurnOverTransferItem zxSkTurnOverTransferItem : zxSkTurnOverTransferItemsList) {
                zxSkTurnOverTransferItem.setBillID(zxSkTurnOverTransfer.getId());
                zxSkTurnOverTransferItem.setId((UuidUtil.generate()));
                zxSkTurnOverTransferItem.setCreateUserInfo(userKey, realName);
                zxSkTurnOverTransferItemMapper.insert(zxSkTurnOverTransferItem);
            }
        }
        int flag = zxSkTurnOverTransferMapper.insert(zxSkTurnOverTransfer);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverTransfer);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverTransfer dbZxSkTurnOverTransfer = zxSkTurnOverTransferMapper.selectByPrimaryKey(zxSkTurnOverTransfer.getId());
        if (dbZxSkTurnOverTransfer != null && StrUtil.isNotEmpty(dbZxSkTurnOverTransfer.getId())) {
            // 调出单位ID
            dbZxSkTurnOverTransfer.setOrgID(zxSkTurnOverTransfer.getOrgID());
            // 调出单位
            dbZxSkTurnOverTransfer.setOrgName(zxSkTurnOverTransfer.getOrgName());
            // 接受单位
            dbZxSkTurnOverTransfer.setAcceptOrgName(zxSkTurnOverTransfer.getAcceptOrgName());
            // 单据编号
            dbZxSkTurnOverTransfer.setBillNo(zxSkTurnOverTransfer.getBillNo());
            // 日期
            dbZxSkTurnOverTransfer.setBusDate(zxSkTurnOverTransfer.getBusDate());
            // 物资员
            dbZxSkTurnOverTransfer.setConsignee(zxSkTurnOverTransfer.getConsignee());
            // 单据状态
            dbZxSkTurnOverTransfer.setBillStatus(zxSkTurnOverTransfer.getBillStatus());
            // 备注
            dbZxSkTurnOverTransfer.setRemarks(zxSkTurnOverTransfer.getRemarks());
            // 排序
            dbZxSkTurnOverTransfer.setSort(zxSkTurnOverTransfer.getSort());
            // 共通
            dbZxSkTurnOverTransfer.setModifyUserInfo(userKey, realName);
            // 公司id
            dbZxSkTurnOverTransfer.setCompanyId(zxSkTurnOverTransfer.getCompanyId());
            // 公司名称
            dbZxSkTurnOverTransfer.setCompanyName(zxSkTurnOverTransfer.getCompanyName());
            //删除在新增
            ZxSkTurnOverTransferItem zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
            zxSkTurnOverTransferItem.setBillID(zxSkTurnOverTransfer.getId());
            List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItems = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(zxSkTurnOverTransferItem);
            if (zxSkTurnOverTransferItems != null && zxSkTurnOverTransferItems.size() > 0) {
                zxSkTurnOverTransferItem.setModifyUserInfo(userKey, realName);
                zxSkTurnOverTransferItemMapper.batchDeleteUpdateZxSkTurnOverTransferItem(zxSkTurnOverTransferItems, zxSkTurnOverTransferItem);
            }
            //明细list
            List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemsList = zxSkTurnOverTransfer.getZxSkTurnOverTransferItemsList();
            if (zxSkTurnOverTransferItemsList != null && zxSkTurnOverTransferItemsList.size() > 0) {
                for (ZxSkTurnOverTransferItem zxSkTurnOverTransferItem1 : zxSkTurnOverTransferItemsList) {
                    zxSkTurnOverTransferItem1.setId(UuidUtil.generate());
                    zxSkTurnOverTransferItem1.setBillID(zxSkTurnOverTransfer.getId());
                    zxSkTurnOverTransferItem1.setCreateUserInfo(userKey, realName);
                    zxSkTurnOverTransferItemMapper.insert(zxSkTurnOverTransferItem1);
                }
            }
            flag = zxSkTurnOverTransferMapper.updateByPrimaryKey(dbZxSkTurnOverTransfer);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkTurnOverTransfer);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverTransfer(List<ZxSkTurnOverTransfer> zxSkTurnOverTransferList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnOverTransferList != null && zxSkTurnOverTransferList.size() > 0) {
            for (ZxSkTurnOverTransfer zxSkTurnOverTransfer : zxSkTurnOverTransferList) {
                // 删除明细
                ZxSkTurnOverTransferItem zxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
                zxSkTurnOverTransferItem.setBillID(zxSkTurnOverTransfer.getId());
                List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItems = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(zxSkTurnOverTransferItem);
                if (zxSkTurnOverTransferItems != null && zxSkTurnOverTransferItems.size() > 0) {
                    zxSkTurnOverTransferItem.setModifyUserInfo(userKey, realName);
                    zxSkTurnOverTransferItemMapper.batchDeleteUpdateZxSkTurnOverTransferItem(zxSkTurnOverTransferItems, zxSkTurnOverTransferItem);
                }
            }
            ZxSkTurnOverTransfer zxSkTurnOverTransfer = new ZxSkTurnOverTransfer();
            zxSkTurnOverTransfer.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnOverTransferMapper.batchDeleteUpdateZxSkTurnOverTransfer(zxSkTurnOverTransferList, zxSkTurnOverTransfer);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSkTurnOverTransferList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //“合同编号”+“周材调字第”+“年份-月份-顺序号”+“号”
    @Override
    public ResponseEntity getZxSkTurnOverTransferNo(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        if(StrUtil.isEmpty(zxSkTurnOverTransfer.getOrgID()) || zxSkTurnOverTransfer.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkTurnOverTransfer.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkTurnOverTransfer.getBusDate());
        int month = DateUtil.month(zxSkTurnOverTransfer.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkTurnOverTransfer.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkTurnOverTransferMapper.getZxSkTurnOverTransferCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 周材调字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkTurnOverTransferResourceList(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        if (StrUtil.isEmpty(zxSkTurnOverTransfer.getOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverTransfer.getPage(), zxSkTurnOverTransfer.getLimit());
        // 获取数据(通过库存)
        List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItems = zxSkTurnOverTransferMapper.getZxSkTurnOverTransferResourceList(zxSkTurnOverTransfer);
        // 得到分页信息
        PageInfo<ZxSkTurnOverTransferItem> p = new PageInfo<>(zxSkTurnOverTransferItems);
        return repEntity.okList(zxSkTurnOverTransferItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        //然后审核数据
        ZxSkTurnOverTransfer dbZxSkTurnOverTransfer = zxSkTurnOverTransferMapper.selectByPrimaryKey(zxSkTurnOverTransfer.getId());
        if (StrUtil.equals(dbZxSkTurnOverTransfer.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }

        ZxSkTurnOverTransferItem dbzxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
        dbzxSkTurnOverTransferItem.setBillID(dbZxSkTurnOverTransfer.getId());
        List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemsList = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(dbzxSkTurnOverTransferItem);
        if (CollUtil.isEmpty(zxSkTurnOverTransferItemsList)) {
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //聚合前查库存 的库存单价  根据 batchNo
        Map<String, ZxSkTurnOverTransferItem> stockPriceMap = zxSkTurnOverTransferMapper.selectStockPrice(zxSkTurnOverTransferItemsList);
        for (ZxSkTurnOverTransferItem zxSkTurnOverTransferItem : zxSkTurnOverTransferItemsList) {
            zxSkTurnOverTransferItem.setStockPrice(stockPriceMap.get(zxSkTurnOverTransferItem.getBatchNo()).getStockPrice());
            if (zxSkTurnOverTransferItem.getStockPrice() == null) {
                return repEntity.layerMessage("no", "仓库没有这个数据！");
            }
        }
        //聚合 数量
        List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemsListNew = new ArrayList<>();
        zxSkTurnOverTransferItemsList.parallelStream()
                //collect就是一个归约操作，就像reduce一样可以接受各种做法作为参数，将流中的元素累积成一个汇总结果
                .collect(Collectors.groupingBy(o -> (o.getBatchNo()), Collectors.toList())).forEach(
                (id, transfer) -> {
                    transfer.stream().reduce((a, b) -> new ZxSkTurnOverTransferItem(
                            //批次
                            a.getBatchNo(),
                            //数量
                            CalcUtils.calcAdd(a.getOutQty(), b.getOutQty())
                    )).ifPresent(zxSkTurnOverTransferItemsListNew::add);
                }
        );
        for (ZxSkTurnOverTransferItem zxSkTurnOverTransferItem : zxSkTurnOverTransferItemsListNew) {
            //根据  库存单价 *  调拨数量 = 调拨原值
            zxSkTurnOverTransferItem.setThisOriginalAmt(CalcUtils.calcMultiply(
                    zxSkTurnOverTransferItem.getStockPrice(),
                    zxSkTurnOverTransferItem.getOutQty()
            ));
            //调拨原值 / 库存原值 * 库存净值 = 调拨净值
            zxSkTurnOverTransferItem.setThisRemainAmt
                    (CalcUtils.calcMultiply
                            (CalcUtils.calcDivide(
                                    zxSkTurnOverTransferItem.getThisOriginalAmt(),
                                    zxSkTurnOverTransferItem.getBuyAmt()
                            ), zxSkTurnOverTransferItem.getCurrentAmt()));
        }
        //库存List
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnOverTransferItem item : zxSkTurnOverTransferItemsListNew) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //公司ID
//            zxSkTurnOverStock.setCompanyID(zxSkTurnoverOut.getCompanyID());
            //项目ID(发料单位Id)
            zxSkTurnOverStock.setOrgID(dbZxSkTurnOverTransfer.getOrgID());
            //项目名称
            zxSkTurnOverStock.setOrgName(dbZxSkTurnOverTransfer.getOrgName());
            //物资Id
            //资源编号
            //资源名称
            //规格型号spec
            //单位unit
            //批次(明细id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //入库数量/数量
            zxSkTurnOverStock.setStockQty(item.getOutQty());
            //调拨原值
            zxSkTurnOverStock.setOriginalAmt(item.getThisOriginalAmt());
            //调拨净值
            zxSkTurnOverStock.setRemainAmt(item.getThisRemainAmt());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //先减少库存
        ResponseEntity responseEntity = zxSkTurnOverStockService.reduceZxSkStock(zxSkTurnOverStockList);
        if (responseEntity.isSuccess()) {
            dbZxSkTurnOverTransfer.setBillStatus("1");
            dbZxSkTurnOverTransfer.setModifyUserInfo(userKey, realName);
            //这里顺便把净值原值也修改了.
            for (ZxSkTurnOverTransferItem zxSkTurnOverTransferItem : zxSkTurnOverTransferItemsList) {
                //根据  库存单价 *  调拨数量 = 调拨原值
                zxSkTurnOverTransferItem.setThisOriginalAmt(CalcUtils.calcMultiply(
                        zxSkTurnOverTransferItem.getStockPrice(),
                        zxSkTurnOverTransferItem.getOutQty()
                ));
                //调拨原值 / 库存原值 * 库存净值 = 调拨净值
                zxSkTurnOverTransferItem.setThisRemainAmt
                        (CalcUtils.calcMultiply
                                (CalcUtils.calcDivide(
                                        zxSkTurnOverTransferItem.getThisOriginalAmt(),
                                        zxSkTurnOverTransferItem.getBuyAmt()
                                ), zxSkTurnOverTransferItem.getCurrentAmt()));
                zxSkTurnOverTransferItem.setModifyUserInfo(userKey, realName);
                flag = zxSkTurnOverTransferItemMapper.updateByPrimaryKey(zxSkTurnOverTransferItem);
            }
            flag = zxSkTurnOverTransferMapper.checkZxSkTurnOverTransfer(dbZxSkTurnOverTransfer);
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

    @Override
    public synchronized ResponseEntity counterCheckZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer) {
        //然后审核数据
        ZxSkTurnOverTransfer dbZxSkTurnOverTransfer = zxSkTurnOverTransferMapper.selectByPrimaryKey(zxSkTurnOverTransfer.getId());
        if (StrUtil.equals(dbZxSkTurnOverTransfer.getBillStatus(), "0")) {
            return repEntity.layerMessage("no", "未审核的，请重新选择！");
        }
        ZxSkTurnOverTransferItem dbzxSkTurnOverTransferItem = new ZxSkTurnOverTransferItem();
        dbzxSkTurnOverTransferItem.setBillID(dbZxSkTurnOverTransfer.getId());
        List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemsList = zxSkTurnOverTransferItemMapper.selectByZxSkTurnOverTransferItemList(dbzxSkTurnOverTransferItem);

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //库存List
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnOverTransferItem item : zxSkTurnOverTransferItemsList) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //公司ID
//            zxSkTurnOverStock.setCompanyID(zxSkTurnoverOut.getCompanyID());
            //项目ID(发料单位Id)
            zxSkTurnOverStock.setOrgID(dbZxSkTurnOverTransfer.getOrgID());
            //项目名称
//            zxSkTurnOverStock.setOrgName(dbZxSkTurnOverTransfer.getOrgName());
            //物资Id
            //资源编号
            //资源名称
            //规格型号spec
            //单位unit
            //批次(明细id)
            zxSkTurnOverStock.setBatchNo(item.getBatchNo());
            //入库数量/数量
            zxSkTurnOverStock.setStockQty(item.getOutQty());
            //调拨原值
            zxSkTurnOverStock.setOriginalAmt(item.getThisOriginalAmt());
            //调拨净值
            zxSkTurnOverStock.setRemainAmt(item.getThisRemainAmt());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        ResponseEntity responseEntity = zxSkTurnOverStockService.returnZxSkStock(zxSkTurnOverStockList);
        if (responseEntity.isSuccess()) {
            for (ZxSkTurnOverTransferItem zxSkTurnOverTransferItem : zxSkTurnOverTransferItemsList) {
                zxSkTurnOverTransferItem.setThisOriginalAmt(new BigDecimal(0));
                zxSkTurnOverTransferItem.setThisRemainAmt(new BigDecimal(0));
                zxSkTurnOverTransferItem.setModifyUserInfo(userKey, realName);
                flag = zxSkTurnOverTransferItemMapper.updateByPrimaryKey(zxSkTurnOverTransferItem);
            }
            dbZxSkTurnOverTransfer.setBillStatus("0");
            dbZxSkTurnOverTransfer.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnOverTransferMapper.checkZxSkTurnOverTransfer(dbZxSkTurnOverTransfer);
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
