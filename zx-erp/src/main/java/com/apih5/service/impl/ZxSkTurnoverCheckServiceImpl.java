package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkTurnoverCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkTurnoverCheckService")
public class ZxSkTurnoverCheckServiceImpl implements ZxSkTurnoverCheckService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverCheckMapper zxSkTurnoverCheckMapper;

    @Autowired(required = true)
    private ZxSkTurnoverCheckItemMapper zxSkTurnoverCheckItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    //入库
    @Autowired(required = true)
    private ZxSkTurnoverInItemMapper zxSkTurnoverInItemMapper;

    //库存
    @Autowired(required = true)
    private ZxSkTurnOverStockMapper zxSkTurnOverStockMapper;

    @Override
    public ResponseEntity getZxSkTurnoverCheckListByCondition(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        if (zxSkTurnoverCheck == null) {
            zxSkTurnoverCheck = new ZxSkTurnoverCheck();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnoverCheck.setCompanyId("");
            zxSkTurnoverCheck.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTurnoverCheck.setCompanyId(zxSkTurnoverCheck.getOrgID());
            zxSkTurnoverCheck.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTurnoverCheck.setOrgID(zxSkTurnoverCheck.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverCheck.getPage(),zxSkTurnoverCheck.getLimit());
        // 获取数据
        List<ZxSkTurnoverCheck> zxSkTurnoverCheckList = zxSkTurnoverCheckMapper.selectByZxSkTurnoverCheckList(zxSkTurnoverCheck);
        //查询明细
        for (ZxSkTurnoverCheck zxSkTurnoverCheck1 : zxSkTurnoverCheckList) {
            ZxSkTurnoverCheckItem zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
            zxSkTurnoverCheckItem.setBillID(zxSkTurnoverCheck1.getId());
            List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItems = zxSkTurnoverCheckItemMapper.selectByZxSkTurnoverCheckItemList(zxSkTurnoverCheckItem);
            for (ZxSkTurnoverCheckItem skTurnoverCheckItem : zxSkTurnoverCheckItems) {
                ZxSkTurnoverInItem zxSkTurnoverInItem = zxSkTurnoverCheckItemMapper.getZxSkTurnoverCheckReceiveInQty(skTurnoverCheckItem.getBatchNo());
                //能冲账的数量
                skTurnoverCheckItem.setOldQty(zxSkTurnoverInItem.getOldQty());
                //预收的摊销
                skTurnoverCheckItem.setOldFeeSum(zxSkTurnoverInItem.getOldFeeSum());
            }
            zxSkTurnoverCheck1.setZxSkTurnoverCheckItemList(zxSkTurnoverCheckItems);
            //明细中要把 冲账数量
        }
        // 得到分页信息
        PageInfo<ZxSkTurnoverCheck> p = new PageInfo<>(zxSkTurnoverCheckList);

        return repEntity.okList(zxSkTurnoverCheckList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverCheckDetail(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        if (zxSkTurnoverCheck == null) {
            zxSkTurnoverCheck = new ZxSkTurnoverCheck();
        }
        // 获取数据
        ZxSkTurnoverCheck dbZxSkTurnoverCheck = zxSkTurnoverCheckMapper.selectByPrimaryKey(zxSkTurnoverCheck.getId());
        // 数据存在
        if (dbZxSkTurnoverCheck != null) {
            ZxSkTurnoverCheckItem zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
            zxSkTurnoverCheckItem.setBillID(dbZxSkTurnoverCheck.getId());
            List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItems = zxSkTurnoverCheckItemMapper.selectByZxSkTurnoverCheckItemList(zxSkTurnoverCheckItem);
            dbZxSkTurnoverCheck.setZxSkTurnoverCheckItemList(zxSkTurnoverCheckItems);
            return repEntity.ok(dbZxSkTurnoverCheck);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverCheck.setId(UuidUtil.generate());
        zxSkTurnoverCheck.setCreateUserInfo(userKey, realName);

        //默认审核状态: 0:未审核
        zxSkTurnoverCheck.setBillStatus("0");

        //创建明细
        List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList = zxSkTurnoverCheck.getZxSkTurnoverCheckItemList();
        if(zxSkTurnoverCheckItemList != null && zxSkTurnoverCheckItemList.size()>0) {
            for (ZxSkTurnoverCheckItem zxSkTurnoverCheckItem : zxSkTurnoverCheckItemList) {
                zxSkTurnoverCheckItem.setBillID(zxSkTurnoverCheck.getId());
                zxSkTurnoverCheckItem.setId((UuidUtil.generate()));
                zxSkTurnoverCheckItem.setCreateUserInfo(userKey, realName);
                zxSkTurnoverCheckItemMapper.insert(zxSkTurnoverCheckItem);
            }
        }
        int flag = zxSkTurnoverCheckMapper.insert(zxSkTurnoverCheck);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverCheck);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverCheck dbZxSkTurnoverCheck = zxSkTurnoverCheckMapper.selectByPrimaryKey(zxSkTurnoverCheck.getId());
        if (dbZxSkTurnoverCheck != null && StrUtil.isNotEmpty(dbZxSkTurnoverCheck.getId())) {
           // 收料单位ID
           dbZxSkTurnoverCheck.setOrgID(zxSkTurnoverCheck.getOrgID());
           // 收料单位
           dbZxSkTurnoverCheck.setOrgName(zxSkTurnoverCheck.getOrgName());
           // 单据编号
           dbZxSkTurnoverCheck.setBillNo(zxSkTurnoverCheck.getBillNo());
           // 发票号
           dbZxSkTurnoverCheck.setInvoiceNo(zxSkTurnoverCheck.getInvoiceNo());
           // 冲账日期
           dbZxSkTurnoverCheck.setBusDate(zxSkTurnoverCheck.getBusDate());
           // 物资来源
           dbZxSkTurnoverCheck.setMaterialSource(zxSkTurnoverCheck.getMaterialSource());
           // 预收单编号
           dbZxSkTurnoverCheck.setYsdNo(zxSkTurnoverCheck.getYsdNo());
           // 预收单ID
           dbZxSkTurnoverCheck.setYsdID(zxSkTurnoverCheck.getYsdID());
           // 金额
           dbZxSkTurnoverCheck.setAmt(zxSkTurnoverCheck.getAmt());
           // 物资员
           dbZxSkTurnoverCheck.setConsignee(zxSkTurnoverCheck.getConsignee());
           // 单据状态
           dbZxSkTurnoverCheck.setBillStatus(zxSkTurnoverCheck.getBillStatus());
           // 采购类别
           dbZxSkTurnoverCheck.setPurchType(zxSkTurnoverCheck.getPurchType());
           // 合同ID
           dbZxSkTurnoverCheck.setContractID(zxSkTurnoverCheck.getContractID());
           // 合同名称
           dbZxSkTurnoverCheck.setContractName(zxSkTurnoverCheck.getContractName());
           // 供货单位ID
           dbZxSkTurnoverCheck.setOutOrgID(zxSkTurnoverCheck.getOutOrgID());
           // 供货单位
           dbZxSkTurnoverCheck.setOutOrgName(zxSkTurnoverCheck.getOutOrgName());
           // 税率
           dbZxSkTurnoverCheck.setTaxRate(zxSkTurnoverCheck.getTaxRate());
           // 是否预收
           dbZxSkTurnoverCheck.setIsDeduct(zxSkTurnoverCheck.getIsDeduct());
           // 公司id
           dbZxSkTurnoverCheck.setCompanyId(zxSkTurnoverCheck.getCompanyId());
           // 公司名称
           dbZxSkTurnoverCheck.setCompanyName(zxSkTurnoverCheck.getCompanyName());
           // 备注
           dbZxSkTurnoverCheck.setRemarks(zxSkTurnoverCheck.getRemarks());
           // 排序
           dbZxSkTurnoverCheck.setSort(zxSkTurnoverCheck.getSort());
           // 共通
           dbZxSkTurnoverCheck.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkTurnoverCheckItem zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
            zxSkTurnoverCheckItem.setBillID(zxSkTurnoverCheck.getId());
            List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItems = zxSkTurnoverCheckItemMapper.selectByZxSkTurnoverCheckItemList(zxSkTurnoverCheckItem);
            if(zxSkTurnoverCheckItems != null && zxSkTurnoverCheckItems.size()>0) {
                zxSkTurnoverCheckItem.setModifyUserInfo(userKey, realName);
                zxSkTurnoverCheckItemMapper.batchDeleteUpdateZxSkTurnoverCheckItem(zxSkTurnoverCheckItems, zxSkTurnoverCheckItem);
            }
            //明细list
            List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList = zxSkTurnoverCheck.getZxSkTurnoverCheckItemList();
            if(zxSkTurnoverCheckItemList != null && zxSkTurnoverCheckItemList.size()>0) {
                for(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem1 : zxSkTurnoverCheckItemList) {
                    zxSkTurnoverCheckItem1.setId(UuidUtil.generate());
                    zxSkTurnoverCheckItem1.setBillID(zxSkTurnoverCheck.getId());
                    zxSkTurnoverCheckItem1.setCreateUserInfo(userKey, realName);
                    zxSkTurnoverCheckItemMapper.insert(zxSkTurnoverCheckItem1);
                }
            }
           flag = zxSkTurnoverCheckMapper.updateByPrimaryKey(dbZxSkTurnoverCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverCheck);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverCheck(List<ZxSkTurnoverCheck> zxSkTurnoverCheckList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverCheckList != null && zxSkTurnoverCheckList.size() > 0) {
            for (ZxSkTurnoverCheck zxSkTurnoverCheck : zxSkTurnoverCheckList) {
                // 删除明细
                ZxSkTurnoverCheckItem zxSkTurnoverCheckItem = new ZxSkTurnoverCheckItem();
                zxSkTurnoverCheckItem.setBillID(zxSkTurnoverCheck.getId());
                List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItems = zxSkTurnoverCheckItemMapper.selectByZxSkTurnoverCheckItemList(zxSkTurnoverCheckItem);
                if(zxSkTurnoverCheckItems != null && zxSkTurnoverCheckItems.size()>0) {
                    zxSkTurnoverCheckItem.setModifyUserInfo(userKey, realName);
                    zxSkTurnoverCheckItemMapper.batchDeleteUpdateZxSkTurnoverCheckItem(zxSkTurnoverCheckItems, zxSkTurnoverCheckItem);
                }
            }
           ZxSkTurnoverCheck zxSkTurnoverCheck = new ZxSkTurnoverCheck();
           zxSkTurnoverCheck.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverCheckMapper.batchDeleteUpdateZxSkTurnoverCheck(zxSkTurnoverCheckList, zxSkTurnoverCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverCheckList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //“业主合同编号”+“周材冲字第”+“年份-月份-顺序号”+“号”
    @Override
    public ResponseEntity getZxSkTurnoverCheckNo(ZxSkTurnoverCheck zxSkTurnoverCheck){
        if(StrUtil.isEmpty(zxSkTurnoverCheck.getOrgID())){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(zxSkTurnoverCheck.getOrgID());
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        String result = new SimpleDateFormat("yyyy-MM").format(new Date());
        String str = String.valueOf(zxSkTurnoverCheckMapper.getZxSkTurnoverCheckCount(result));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 周材冲字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkTurnoverCheckReceive(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        if(zxSkTurnoverCheck == null){
            zxSkTurnoverCheck = new ZxSkTurnoverCheck();
        }
        if(StrUtil.isEmpty(zxSkTurnoverCheck.getOrgID())){
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverCheck.getPage(),zxSkTurnoverCheck.getLimit());
        // 获取数据
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverCheckMapper.getZxSkTurnoverCheckReceive(zxSkTurnoverCheck);

            Iterator<ZxSkTurnoverIn> iterator = zxSkTurnoverInList.iterator();
            while (iterator.hasNext()){
                ZxSkTurnoverIn zxSkTurnoverIn = iterator.next();
                if(zxSkTurnoverIn != null){
                    List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverIn.getZxSkTurnoverInItemList();
                    Iterator<ZxSkTurnoverInItem> zxSkTurnoverInItemIterator = zxSkTurnoverInItemList.iterator();
                    while (zxSkTurnoverInItemIterator.hasNext()){
                        ZxSkTurnoverInItem zxSkTurnoverInItem = zxSkTurnoverInItemIterator.next();
                        if(zxSkTurnoverInItem!=null){
                            if(CalcUtils.compareToZero(zxSkTurnoverInItem.getInQty())==0){
                                zxSkTurnoverInItemIterator.remove();
                            }else {
                                //计算一下入账金额
                            }
                        }
                    }
                    if(zxSkTurnoverInItemList.size()==0){
                        iterator.remove();
                    }
                }else {
                    iterator.remove();
                }
            }

        // 得到分页信息
        PageInfo<ZxSkTurnoverIn> p = new PageInfo<>(zxSkTurnoverInList);
        return repEntity.okList(zxSkTurnoverInList, p.getTotal());
    }

    //本次冲账 数量*本次购入单价合计 + 历史 数量*历史购入单价合计 + 库存数量 * 库存内的 单价  = 净值
    //算出来的净值 需要减去 数据库的 入库数量 * 净值   =  纯净值
    //在跟数据库的净值  做加减
    @Override
    public ResponseEntity checkZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //然后审核数据
        if(StrUtil.equals(zxSkTurnoverCheck.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //先判断数量是否可以审核
        List<ZxSkTurnoverIn> dbzxSkTurnoverCheckReceive = zxSkTurnoverCheckMapper.getZxSkTurnoverCheckReceive(zxSkTurnoverCheck);
        Iterator<ZxSkTurnoverIn> iterator = dbzxSkTurnoverCheckReceive.iterator();
        Map<String,ZxSkTurnoverInItem> dbzxSkTurnoverCheckReceiveMap = new HashMap<>();
        while (iterator.hasNext()){
            ZxSkTurnoverIn zxSkTurnoverIn = iterator.next();
            List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverIn.getZxSkTurnoverInItemList();
            Iterator<ZxSkTurnoverInItem> zxSkTurnoverInItemIterator = zxSkTurnoverInItemList.iterator();
            while (zxSkTurnoverInItemIterator.hasNext()){
                ZxSkTurnoverInItem zxSkTurnoverInItem = zxSkTurnoverInItemIterator.next();
                    //添加到新List
                dbzxSkTurnoverCheckReceiveMap.put(zxSkTurnoverInItem.getBatchNo(),zxSkTurnoverInItem);

            }
        }
        //本次 remainAmt
        List<ZxSkTurnoverCheckItem> zxSkTurnoverInItemList = zxSkTurnoverCheck.getZxSkTurnoverCheckItemList();
        for (ZxSkTurnoverCheckItem zxSkTurnoverCheckItem : zxSkTurnoverInItemList) {
            ZxSkTurnoverInItem zxSkTurnoverInItem = dbzxSkTurnoverCheckReceiveMap.get(zxSkTurnoverCheckItem.getBatchNo());
            //允许的数量:inQty   前端传过来的:inQty
            if(!(CalcUtils.compareTo(zxSkTurnoverInItem.getInQty(),zxSkTurnoverCheckItem.getInQty())>=0)){
                return repEntity.layerMessage("no", "冲账的数量不可以大于预收的数量！");
            }
        }
        //业务修改(温建伟)
        int flag = 0;
        for (ZxSkTurnoverCheckItem zxSkTurnoverCheckItem : zxSkTurnoverInItemList) {
            //1.从数据库获取入库单据的  原值/数量 = 原单价
            //获取入库单据明细
            ZxSkTurnoverInItem zxSkTurnoverInItem = zxSkTurnoverInItemMapper.selectByPrimaryKey(zxSkTurnoverCheckItem.getBatchNo());
            //求出原单价
            BigDecimal oldPrice = CalcUtils.calcDivide(zxSkTurnoverInItem.getInAmt(), zxSkTurnoverInItem.getInQty(),6);
            //2.前端传过来 原值
            BigDecimal inAmt = zxSkTurnoverCheckItem.getInAmt();
            //3.原值 - 原单价 * 冲账数量 = 冲账金额
            BigDecimal checkAmt = CalcUtils.calcSubtract(inAmt, CalcUtils.calcMultiply(oldPrice, zxSkTurnoverCheckItem.getInQty()));
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            zxSkTurnOverStock.setBatchNo(zxSkTurnoverCheckItem.getBatchNo());
            //根据batchNo
            List<ZxSkTurnOverStock> zxSkTurnOverStocks = zxSkTurnOverStockMapper.selectByZxSkTurnOverStockList(zxSkTurnOverStock);
            if(zxSkTurnOverStocks != null && zxSkTurnOverStocks.size()>0){
                ZxSkTurnOverStock zxSkTurnOverStock1 = zxSkTurnOverStocks.get(0);
                //4.库存原值or净值 + 冲账金额
                zxSkTurnOverStock1.setOriginalAmt(CalcUtils.calcAdd(zxSkTurnOverStock1.getOriginalAmt(),checkAmt));
                zxSkTurnOverStock1.setRemainAmt(CalcUtils.calcAdd(zxSkTurnOverStock1.getRemainAmt(),checkAmt));
                zxSkTurnOverStock1.setModifyUserInfo(userKey,realName);
                //更新库存
                flag = zxSkTurnOverStockMapper.updateZxSkTurnOverStockOriginalAmtAndRemainAmt(zxSkTurnOverStock1);
            }else {
                try {
                    throw new Exception("审核失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.ok("审核失败",zxSkTurnoverCheck);
            }
        }
//        //数据库值和历史值的净值+本次的
        List<ZxSkTurnoverCheckItem> oldRemainAmt = zxSkTurnoverCheckMapper.getOldRemainAmt(zxSkTurnoverCheck, zxSkTurnoverInItemList);
//        //直接修改库的值
//        for (ZxSkTurnoverCheckItem zxSkTurnoverCheckItem : oldRemainAmt) {
//            zxSkTurnoverCheckItem.setModifyUserInfo(userKey,realName);
//        }
//        flag = zxSkTurnoverCheckMapper.updateStockRemainAmt(oldRemainAmt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //修改状态
            zxSkTurnoverCheck.setBillStatus("1");
            zxSkTurnoverCheck.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverCheckMapper.checkZxSkTurnoverCheck(zxSkTurnoverCheck);
            if (flag == 0) {
                try {
                    throw new Exception("修改审核失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return repEntity.errorUpdate();
            }
            return repEntity.ok(zxSkTurnoverCheck);
        }
    }

    @Override
    public List<ZxSkTurnoverCheck> ureportZxSkTurnoverCheckList(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        if (zxSkTurnoverCheck == null) {
            zxSkTurnoverCheck = new ZxSkTurnoverCheck();
        }
        // 获取数据
        List<ZxSkTurnoverCheck> zxSkTurnoverCheckList = zxSkTurnoverCheckMapper.getZxSkTurnoverCheckListForReport(zxSkTurnoverCheck);
        return zxSkTurnoverCheckList;
    }

    @Override
    public ResponseEntity getUreportZxSkTurnoverCheckList(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        if (zxSkTurnoverCheck == null) {
            zxSkTurnoverCheck = new ZxSkTurnoverCheck();
        }
        // 获取数据
        List<ZxSkTurnoverCheck> zxSkTurnoverCheckList = zxSkTurnoverCheckMapper.getZxSkTurnoverCheckListForReport(zxSkTurnoverCheck);
        return repEntity.ok(zxSkTurnoverCheckList);
    }

    @Override
    public ResponseEntity getZxSkTurnoverCheckSupplierList(ZxSkTurnoverCheck zxSkTurnoverCheck) {
        //从该项目在收料单中出现的收料单位中进行选择
        if (zxSkTurnoverCheck == null) {
            zxSkTurnoverCheck = new ZxSkTurnoverCheck();
        }
        if(StrUtil.isEmpty(zxSkTurnoverCheck.getOrgID())){
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverCheck.getPage(),zxSkTurnoverCheck.getLimit());
        // 获取数据
        List<ZxCrCustomerNew> zxCrCustomerNewList = zxSkTurnoverCheckMapper.getZxSkTurnoverCheckSupplierList(zxSkTurnoverCheck);
        // 得到分页信息
        PageInfo<ZxCrCustomerNew> p = new PageInfo<>(zxCrCustomerNewList);

        return repEntity.okList(zxCrCustomerNewList, p.getTotal());
    }
}
