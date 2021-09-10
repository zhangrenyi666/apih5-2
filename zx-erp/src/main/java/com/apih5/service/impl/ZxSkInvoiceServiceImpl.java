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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkInvoice.setCompanyId("");
            zxSkInvoice.setMakeoutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkInvoice.setCompanyId(zxSkInvoice.getMakeoutOrgID());
            zxSkInvoice.setMakeoutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkInvoice.setMakeoutOrgID(zxSkInvoice.getMakeoutOrgID());
        }

        // 分页查询
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // 获取数据
        List<ZxSkInvoice> zxSkInvoiceList = zxSkInvoiceMapper.selectByZxSkInvoiceList(zxSkInvoice);
        //查询明细
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
        // 得到分页信息
        PageInfo<ZxSkInvoice> p = new PageInfo<>(zxSkInvoiceList);

        return repEntity.okList(zxSkInvoiceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkInvoiceDetail(ZxSkInvoice zxSkInvoice) {
        if (zxSkInvoice == null) {
            zxSkInvoice = new ZxSkInvoice();
        }
        // 获取数据
        ZxSkInvoice dbZxSkInvoice = zxSkInvoiceMapper.selectByPrimaryKey(zxSkInvoice.getId());
        // 数据存在
        if (dbZxSkInvoice != null) {
            ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
            zxSkInvoiceItem.setInvoiceID(dbZxSkInvoice.getId());
            List<ZxSkInvoiceItem> zxSkInvoiceItems = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
            dbZxSkInvoice.setZxSkInvoiceItemList(zxSkInvoiceItems);
            return repEntity.ok(dbZxSkInvoice);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkInvoice(ZxSkInvoice zxSkInvoice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkInvoice.setId(UuidUtil.generate());
        zxSkInvoice.setCreateUserInfo(userKey, realName);

        //默认审核状态: 0:未审核
        zxSkInvoice.setBillStatus("0");
        //创建明细
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
           // 登记单位
           dbZxSkInvoice.setOrgID(zxSkInvoice.getOrgID());
           // 开票单位
           dbZxSkInvoice.setMakeoutOrgID(zxSkInvoice.getMakeoutOrgID());
           // 发票号
           dbZxSkInvoice.setInvoiceNo(zxSkInvoice.getInvoiceNo());
           // 冲账日期
           dbZxSkInvoice.setMakeoutDate(zxSkInvoice.getMakeoutDate());
           // 顾客
           dbZxSkInvoice.setCustomer(zxSkInvoice.getCustomer());
           // 入账金额
           dbZxSkInvoice.setAmt(zxSkInvoice.getAmt());
           // 仓库ID
           dbZxSkInvoice.setWhOrgID(zxSkInvoice.getWhOrgID());
           // 仓库
           dbZxSkInvoice.setWarehouseName(zxSkInvoice.getWarehouseName());
           // 供方名称
           dbZxSkInvoice.setOutOrgName(zxSkInvoice.getOutOrgName());
           // 供方ID
           dbZxSkInvoice.setOutOrgID(zxSkInvoice.getOutOrgID());
           // 单据状态
           dbZxSkInvoice.setBillStatus(zxSkInvoice.getBillStatus());
           // 预收单
           dbZxSkInvoice.setYsdID(zxSkInvoice.getYsdID());
           // 预收单编号
           dbZxSkInvoice.setYsdNo(zxSkInvoice.getYsdNo());
           // 单据编号
           dbZxSkInvoice.setReceNo(zxSkInvoice.getReceNo());
           // 物资类别ID
           dbZxSkInvoice.setResourceID(zxSkInvoice.getResourceID());
           // 物资类别
           dbZxSkInvoice.setResourceName(zxSkInvoice.getResourceName());
           // 物资来源
           dbZxSkInvoice.setMaterialSource(zxSkInvoice.getMaterialSource());
           // 采购类别
           dbZxSkInvoice.setPurchType(zxSkInvoice.getPurchType());
           // 合同ID号
           dbZxSkInvoice.setContractID(zxSkInvoice.getContractID());
           // 合同名称
           dbZxSkInvoice.setContractName(zxSkInvoice.getContractName());
           // 总金额
           dbZxSkInvoice.setAmtTotal(zxSkInvoice.getAmtTotal());
           // 项目名称
           dbZxSkInvoice.setMakeoutOrgName(zxSkInvoice.getMakeoutOrgName());
           // 公司id
           dbZxSkInvoice.setCompanyId(zxSkInvoice.getCompanyId());
           // 公司名称
           dbZxSkInvoice.setCompanyName(zxSkInvoice.getCompanyName());
           // 备注
           dbZxSkInvoice.setRemarks(zxSkInvoice.getRemarks());
           // 排序
           dbZxSkInvoice.setSort(zxSkInvoice.getSort());
           // 共通
           dbZxSkInvoice.setModifyUserInfo(userKey, realName);

           //删除在新增
            ZxSkInvoiceItem zxSkInvoiceItem = new ZxSkInvoiceItem();
            zxSkInvoiceItem.setInvoiceID(zxSkInvoice.getId());
            List<ZxSkInvoiceItem> zxSkInvoiceItems = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(zxSkInvoiceItem);
            if(zxSkInvoiceItems!=null&&zxSkInvoiceItems.size()>0){
                zxSkInvoiceItem.setModifyUserInfo(userKey,realName);
                zxSkInvoiceItemMapper.batchDeleteUpdateZxSkInvoiceItem(zxSkInvoiceItems,zxSkInvoiceItem);
            }
            //明细List
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
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkInvoiceList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    //合同编号”+“冲字第”+“年份-月份-顺序号”+“号
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
        String no = contractNo + " 冲字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkInvoiceReceivableOrder(ZxSkInvoice zxSkInvoice) {
        if(StrUtil.isEmpty(zxSkInvoice.getCompanyID())
            ||StrUtil.isEmpty(zxSkInvoice.getWhOrgID())
            ||StrUtil.isEmpty(zxSkInvoice.getOutOrgID())
            ||StrUtil.isEmpty(zxSkInvoice.getResourceID())){
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // 获取数据
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
                    //计算一下入账金额
                }
            }
            if(zxSkStockTransItemReceivingList.size()==0){
                iterator.remove();
            }
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkInvoice(ZxSkInvoice zxSkInvoice) {
        //然后审核数据
        ZxSkInvoice zxSkInvoice1 = zxSkInvoiceMapper.selectByPrimaryKey(zxSkInvoice.getId());
        if(StrUtil.equals(zxSkInvoice1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkInvoiceItem dbzxSkInvoiceItem = new ZxSkInvoiceItem();
        dbzxSkInvoiceItem.setInvoiceID(zxSkInvoice1.getId());
        List<ZxSkInvoiceItem> zxSkInvoiceItemList = zxSkInvoiceItemMapper.selectByZxSkInvoiceItemList(dbzxSkInvoiceItem);
        if(CollUtil.isEmpty(zxSkInvoiceItemList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
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
        //查询可以使用的数量
        //提供数据
        //(根据仓库id(whOrgID),退货部门id(供货商outOrgID),分类id(resourceID),       预收单ID(stockTransID)List)
        //todo:用税额
        List<ZxSkStockTransItemReceiving> zxSkInvoiceQty = zxSkInvoiceMapper.getZxSkInvoiceQty(zxSkInvoiceItemList, zxSkInvoice1);
        //错误List (审核的数量大于库存数量)
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
            return repEntity.layerMessage(false,zxSkStockTransItemReceivings, "冲账的数量不能大于收料的数量");
        }
        //现在的入账金额amt    -    原来的入账金额oldAmt 除以 总数量tempQty  *  冲账数量qty
        for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
            BigDecimal pri = CalcUtils.calcDivide(zxSkInvoiceItem.getoldAmt(), zxSkInvoiceItem.getTempQty());
            BigDecimal oldamt = CalcUtils.calcMultiply(pri, zxSkInvoiceItem.getQty());
            zxSkInvoiceItem.setMoney(CalcUtils.calcSubtract(zxSkInvoiceItem.getAmt(),oldamt));
        }
//        库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkInvoiceItem Item : zxSkInvoiceItemList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkInvoice1.getCompanyId());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkInvoice1.getWhOrgID());
            //物资大类ID
            zxSkStock.setResourceId(zxSkInvoice1.getResourceID());
            //物资Id
            zxSkStock.setResID(Item.getResID());
            //资源编号
            zxSkStock.setResCode(Item.getResCode());
            //资源名称
            zxSkStock.setResName(Item.getResName());
            //规格型号spec
            zxSkStock.setSpec(Item.getResSpec());
            //单位unit
            zxSkStock.setUnit(Item.getResUnit());
            //数量
            zxSkStock.setStockQty(Item.getQty());
            //金额
            zxSkStock.setStockAmt(Item.getMoney());
            zxSkStockList.add(zxSkStock);
        }
        //修改库存金额
        ResponseEntity responseEntity = zxSkStockService.changeZxSkStockTotal(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkInvoice1.setBillStatus("1");
            zxSkInvoice1.setModifyUserInfo(userKey, realName);
            flag = zxSkInvoiceMapper.checkZxSkInvoice(zxSkInvoice1);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.errorUpdate();
            }
            //成功审核 创建两条收料单单据
            //预收单冲账后，负单据编号为[充00]+“收料单据编号”，
            //冲多条  //使用聚合
            List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = new ArrayList<>();
            List<ZxSkInvoiceItem> zxSkInvoiceItemBillNoList = new ArrayList<>();
            zxSkInvoiceItemList.parallelStream().collect(Collectors.groupingBy((o->o.getStockTransBillNo()),Collectors.toList())).forEach(
                    (id,transfer) -> {transfer.stream().reduce((a,b)-> new ZxSkInvoiceItem(
                            a.getStockTransBillNo()
                    )).ifPresent(zxSkInvoiceItemBillNoList::add);
                    }
            );
            //根据单据去查询数据//并放到list中
            for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemBillNoList) {
                ZxSkStockTransferReceiving zxSkStockTransferReceiving = new ZxSkStockTransferReceiving();
                zxSkStockTransferReceiving.setBillNo(zxSkInvoiceItem.getStockTransBillNo());
                List<ZxSkStockTransferReceiving> zxSkStockTransferReceivings = zxSkStockTransferReceivingMapper.selectByZxSkStockTransferReceivingListByPrimaryNo(zxSkStockTransferReceiving);
                for (ZxSkStockTransferReceiving skStockTransferReceiving : zxSkStockTransferReceivings) {
                    //不用明细,编号,状态
                    //在这做收料单(主表)数据
                    //修改业务日期
//                    skStockTransferReceiving.setBusDate(zxSkInvoice1.getMakeoutDate());
                    //经办人
//                    skStockTransferReceiving.setOuttransactor("");
                    //仓库经办人 置空
//                    skStockTransferReceiving.setIntransactor("");
                    //使用冲账单发票号
//                    skStockTransferReceiving.setInvoiceNo(zxSkInvoice1.getInvoiceNo());
                    //制单人
//                    skStockTransferReceiving.setReporter(realName);
                    //凭证号 置空
//                    skStockTransferReceiving.setVoucherNo("");
                    //合同id
//                    skStockTransferReceiving.setPurchaseContractID(zxSkInvoice1.getContractID());
                    //合同名称
//                    skStockTransferReceiving.setPurchaseContract(zxSkInvoice1.getContractName());
                    //采购类别
//                    skStockTransferReceiving.setPurchType(zxSkInvoice1.getPurchType());
                    //todo:税率和是否抵扣待确认
                    //税率
                    //是否抵扣
                    //单据状态
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
                //查询明细数据 (收料单)
                ZxSkStockTransItemReceiving receivingItem = zxSkStockTransItemReceivingMapper.selectByPrimaryKey(zxSkInvoiceItem.getStockTransItemID());

                ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
                //物资ID
                zxSkStockTransItemReceiving.setResID(receivingItem.getResID());
                //物资编码
                zxSkStockTransItemReceiving.setResCode(receivingItem.getResCode());
                //物资名称
                zxSkStockTransItemReceiving.setResName(receivingItem.getResName());
                //物资规格
                zxSkStockTransItemReceiving.setSpec(receivingItem.getSpec());
                //计量单位
                zxSkStockTransItemReceiving.setResUnit(receivingItem.getResUnit());
                //实收数量 -
                zxSkStockTransItemReceiving.setInQty(zxSkInvoiceItem.getQty().negate());
                //不含税单价
                zxSkStockTransItemReceiving.setInPriceNoTax(receivingItem.getInPriceNoTax());
                //不含税金额 -    //实收数量乘以 预收的不含税单价
                BigDecimal resAllFeeNoTax = NumberUtil.round(CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPriceNoTax()),2);
                zxSkStockTransItemReceiving.setResAllFeeNoTax(resAllFeeNoTax.negate());
                //税额 -  //含税单价-不含税单价
                BigDecimal resAllFeeTax = NumberUtil.round(CalcUtils.calcSubtract(CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPrice()),CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPriceNoTax())),2);
                zxSkStockTransItemReceiving.setResAllFeeTax(resAllFeeTax.negate());
                //含税单价
                zxSkStockTransItemReceiving.setInPrice(receivingItem.getInPrice());
                //含税金额 -   //实收数量乘以  预收的含税单价
                BigDecimal resAllFee = NumberUtil.round(CalcUtils.calcMultiply(zxSkInvoiceItem.getQty(),receivingItem.getInPrice()),2);
                zxSkStockTransItemReceiving.setResAllFee(resAllFee.negate());
                //入账其他费 -
                zxSkStockTransItemReceiving.setInFee(zxSkInvoiceItem.getOtherExpense()!=null?zxSkInvoiceItem.getOtherExpense().negate():new BigDecimal("0"));
                //其他费合计 -
                BigDecimal inFeeTotal = zxSkInvoiceItem.getOtherExpenseTotal();
                zxSkStockTransItemReceiving.setInFeeTotal(inFeeTotal!=null?zxSkInvoiceItem.getOtherExpenseTotal().negate():new BigDecimal("0"));
                //入账运输费 -
                zxSkStockTransItemReceiving.setYsFee(zxSkInvoiceItem.getYsFee()!=null?zxSkInvoiceItem.getYsFee().negate():new BigDecimal("0"));
                //运输费合计 -
                BigDecimal ysFeeTotal = zxSkInvoiceItem.getYsFeeTotal();
                zxSkStockTransItemReceiving.setYsFeeTotal(ysFeeTotal!=null?zxSkInvoiceItem.getYsFeeTotal().negate():new BigDecimal("0"));
                //总价 -   运输费合计,其它费合计,含税金额,
                zxSkStockTransItemReceiving.setInAmtAll(CalcUtils.calcAdd(resAllFee,CalcUtils.calcAdd(inFeeTotal,ysFeeTotal)).negate());
                //根据是否抵扣 isDeduct 判断使用含税还是非含税   1:是 0:否
                //入账金额 -
                if(StrUtil.equals(zxSkInvoiceItem.getIsDeduct(),"1")){
                    //使用非含税 + 运输费合计 + 其它费合计
                    zxSkStockTransItemReceiving.setInAmt(CalcUtils.calcAdd(resAllFeeNoTax,CalcUtils.calcAdd(inFeeTotal,ysFeeTotal)).negate());
                }else {
                    //使用含税 + 运输费合计 + 其它费合计
                    zxSkStockTransItemReceiving.setInAmt(CalcUtils.calcAdd(resAllFee,CalcUtils.calcAdd(inFeeTotal,ysFeeTotal)).negate());
                }
                //备注
                zxSkStockTransItemReceiving.setRemark(zxSkInvoiceItem.getRemarks());
                zxSkStockTransItemReceivingsList.add(zxSkStockTransItemReceiving);
                zxSkStockTransItemReceivingMap.put(zxSkInvoiceItem.getStockTransBillNo(),zxSkStockTransItemReceivingsList);
            }
            int i = 0;
            for (ZxSkStockTransferReceiving zxSkStockTransferReceiving : zxSkStockTransferReceivingList) {
                //根据单据编号添加明细数据
                List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivings1 = zxSkStockTransItemReceivingMap.get(zxSkStockTransferReceiving.getBillNo());
                zxSkStockTransferReceiving.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivings1);
                //修改单据编号
                zxSkStockTransferReceiving.setBillNo("[冲0"+i+"]"+zxSkStockTransferReceiving.getBillNo());
                i++;
                zxSkStockTransferReceivingService.saveZxSkStockTransferReceiving(zxSkStockTransferReceiving);
            }

            //收1条
            //正单据编号为“业主合同编号”+“收字第”+“年份-月份-顺序号”
            //就取第一条
            ZxSkStockTransferReceiving zxSkStockTransferReceiving = zxSkStockTransferReceivingList.get(0);
            List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList = new ArrayList<>();
            for (ZxSkInvoiceItem zxSkInvoiceItem : zxSkInvoiceItemList) {
                ZxSkStockTransItemReceiving zxSkStockTransItemReceiving = new ZxSkStockTransItemReceiving();
                //物资ID
                zxSkStockTransItemReceiving.setResID(zxSkInvoiceItem.getResID());
                //物资编码
                zxSkStockTransItemReceiving.setResCode(zxSkInvoiceItem.getResCode());
                //物资名称
                zxSkStockTransItemReceiving.setResName(zxSkInvoiceItem.getResName());
                //物资规格
                zxSkStockTransItemReceiving.setSpec(zxSkInvoiceItem.getResSpec());
                //计量单位
                zxSkStockTransItemReceiving.setResUnit(zxSkInvoiceItem.getResUnit());
                //实收数量
                zxSkStockTransItemReceiving.setInQty(zxSkInvoiceItem.getQty());
                //不含税单价
                zxSkStockTransItemReceiving.setInPriceNoTax(zxSkInvoiceItem.getUnitPriceNoTax());
                //不含税金额
                zxSkStockTransItemReceiving.setResAllFeeNoTax(zxSkInvoiceItem.getStockAmtNoTax());
                //税额
                zxSkStockTransItemReceiving.setResAllFeeTax(zxSkInvoiceItem.getStockAmtTax());
                //含税单价
                zxSkStockTransItemReceiving.setInPrice(zxSkInvoiceItem.getUnitPrice());
                //含税金额
                zxSkStockTransItemReceiving.setResAllFee(zxSkInvoiceItem.getStockAmt());
                //入账其他费
                zxSkStockTransItemReceiving.setInFee(zxSkInvoiceItem.getOtherExpense()!=null?zxSkInvoiceItem.getOtherExpense():new BigDecimal("0"));
                //其他费合计
                zxSkStockTransItemReceiving.setInFeeTotal(zxSkInvoiceItem.getOtherExpenseTotal()!=null?zxSkInvoiceItem.getOtherExpenseTotal():new BigDecimal("0"));
                //入账运输费
                zxSkStockTransItemReceiving.setYsFee(zxSkInvoiceItem.getYsFee()!=null?zxSkInvoiceItem.getYsFee():new BigDecimal("0"));
                //运输费合计
                zxSkStockTransItemReceiving.setYsFeeTotal(zxSkInvoiceItem.getYsFeeTotal()!=null?zxSkInvoiceItem.getYsFeeTotal():new BigDecimal("0"));
                //总价
                zxSkStockTransItemReceiving.setInAmtAll(zxSkInvoiceItem.getAmtTotal());
                //入账金额
                zxSkStockTransItemReceiving.setInAmt(zxSkInvoiceItem.getAmt());
                //是否预收
                zxSkStockTransItemReceiving.setPrecollecte("0");
                //备注
                zxSkStockTransItemReceiving.setRemark(zxSkInvoiceItem.getRemarks());
                zxSkStockTransItemReceivingList.add(zxSkStockTransItemReceiving);
            }
            zxSkStockTransferReceiving.setZxSkStockTransItemReceivingList(zxSkStockTransItemReceivingList);
            //编号
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
//            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // 获取数据
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkInvoiceMapper.getZxSkInvoiceOutOrg(zxSkInvoice);
        // 得到分页信息
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkInvoiceResource(ZxSkInvoice zxSkInvoice) {
        if(StrUtil.isEmpty(zxSkInvoice.getCompanyID())||StrUtil.isEmpty(zxSkInvoice.getMakeoutOrgID())){
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkInvoice.getPage(),zxSkInvoice.getLimit());
        // 获取数据
        List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList = zxSkInvoiceMapper.getZxSkInvoiceResource(zxSkInvoice);

        // 得到分页信息
        PageInfo<ZxSkStockTransferReceiving> p = new PageInfo<>(zxSkStockTransferReceivingList);
        return repEntity.okList(zxSkStockTransferReceivingList, p.getTotal());
    }





}
