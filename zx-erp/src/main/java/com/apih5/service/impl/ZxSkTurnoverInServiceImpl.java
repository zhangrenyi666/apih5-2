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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnoverIn.setCompanyId("");
            zxSkTurnoverIn.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverIn.getOrgID2())){
                zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTurnoverIn.setCompanyId(zxSkTurnoverIn.getOrgID());
            zxSkTurnoverIn.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkTurnoverIn.getOrgID2())){
                zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID());
            if(StrUtil.isNotEmpty(zxSkTurnoverIn.getOrgID2())){
                zxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverIn.getPage(),zxSkTurnoverIn.getLimit());
        // 获取数据
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverInMapper.selectByZxSkTurnoverInList(zxSkTurnoverIn);
        //查询明细
        for (ZxSkTurnoverIn zxSkTurnoverIn1 : zxSkTurnoverInList) {
            ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
            zxSkTurnoverInItem.setBillID(zxSkTurnoverIn1.getId());
            List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
            zxSkTurnoverIn1.setZxSkTurnoverInItemList(zxSkTurnoverInItems);
        }
        // 得到分页信息
        PageInfo<ZxSkTurnoverIn> p = new PageInfo<>(zxSkTurnoverInList);

        return repEntity.okList(zxSkTurnoverInList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverInDetail(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // 获取数据
        ZxSkTurnoverIn dbZxSkTurnoverIn = zxSkTurnoverInMapper.selectByPrimaryKey(zxSkTurnoverIn.getId());
        // 数据存在
        if (dbZxSkTurnoverIn != null) {
            ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
            zxSkTurnoverInItem.setBillID(dbZxSkTurnoverIn.getId());
            List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
            dbZxSkTurnoverIn.setZxSkTurnoverInItemList(zxSkTurnoverInItems);
            return repEntity.ok(dbZxSkTurnoverIn);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverIn.setId(UuidUtil.generate());
        zxSkTurnoverIn.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkTurnoverIn.setStatus("0");
        //创建明细
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverIn.getZxSkTurnoverInItemList();
        if(CollUtil.isNotEmpty(zxSkTurnoverInItemList)) {
            for (ZxSkTurnoverInItem zxSkTurnoverInItem : zxSkTurnoverInItemList) {
                if(zxSkTurnoverInItem.getFeeSum()==null){
                    zxSkTurnoverInItem.setFeeSum(new BigDecimal(0));
                }
                //是否结算  0: 否,1:是
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
           // 收料单位ID
           dbZxSkTurnoverIn.setOrgID(zxSkTurnoverIn.getOrgID());
           // 收料单位
           dbZxSkTurnoverIn.setOrgName(zxSkTurnoverIn.getOrgName());
           // 物资来源
           dbZxSkTurnoverIn.setMaterialSource(zxSkTurnoverIn.getMaterialSource());
           // 单据编号
           dbZxSkTurnoverIn.setBillNo(zxSkTurnoverIn.getBillNo());
           // 入库日期
           dbZxSkTurnoverIn.setBusDate(zxSkTurnoverIn.getBusDate());
           // 物资员
           dbZxSkTurnoverIn.setConsignee(zxSkTurnoverIn.getConsignee());
           // 单据状态
           dbZxSkTurnoverIn.setBillStatus(zxSkTurnoverIn.getBillStatus());
           // 审核状态
           dbZxSkTurnoverIn.setStatus(zxSkTurnoverIn.getStatus());
           // 供货单位ID
           dbZxSkTurnoverIn.setOutOrgID(zxSkTurnoverIn.getOutOrgID());
           // 供货单位
           dbZxSkTurnoverIn.setOutOrgName(zxSkTurnoverIn.getOutOrgName());
           // 物资合同ID
           dbZxSkTurnoverIn.setContractID(zxSkTurnoverIn.getContractID());
           // 物资合同
           dbZxSkTurnoverIn.setContractName(zxSkTurnoverIn.getContractName());
           // 采购类别
           dbZxSkTurnoverIn.setPurchType(zxSkTurnoverIn.getPurchType());
           // 收料人员
           dbZxSkTurnoverIn.setRecePerson(zxSkTurnoverIn.getRecePerson());
           // 采购人员
           dbZxSkTurnoverIn.setPurcPerson(zxSkTurnoverIn.getPurcPerson());
           // 是否抵扣
           dbZxSkTurnoverIn.setIsDeduct(zxSkTurnoverIn.getIsDeduct());
           // 税率(%)
           dbZxSkTurnoverIn.setTaxRate(zxSkTurnoverIn.getTaxRate());
           // fromBillID
           dbZxSkTurnoverIn.setFromBillID(zxSkTurnoverIn.getFromBillID());
           // 是否预收
           dbZxSkTurnoverIn.setOrNotPrecollecte(zxSkTurnoverIn.getOrNotPrecollecte());
           // 公司id
           dbZxSkTurnoverIn.setCompanyId(zxSkTurnoverIn.getCompanyId());
           // 公司名称
           dbZxSkTurnoverIn.setCompanyName(zxSkTurnoverIn.getCompanyName());
           // 备注
           dbZxSkTurnoverIn.setRemarks(zxSkTurnoverIn.getRemarks());
           // 排序
           dbZxSkTurnoverIn.setSort(zxSkTurnoverIn.getSort());
           // 共通
           dbZxSkTurnoverIn.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkTurnoverInItem zxSkTurnoverInItem = new ZxSkTurnoverInItem();
            zxSkTurnoverInItem.setBillID(zxSkTurnoverIn.getId());
            List<ZxSkTurnoverInItem> zxSkTurnoverInItems = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(zxSkTurnoverInItem);
            if(zxSkTurnoverInItems != null && zxSkTurnoverInItems.size()>0) {
                zxSkTurnoverInItem.setModifyUserInfo(userKey, realName);
                zxSkTurnoverInItemMapper.batchDeleteUpdateZxSkTurnoverInItem(zxSkTurnoverInItems, zxSkTurnoverInItem);
            }
            //明细list
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
        // 失败
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
                // 删除明细
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverInList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public synchronized ResponseEntity checkZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn) {
        //然后审核数据
        ZxSkTurnoverIn dbZxSkTurnoverIn = zxSkTurnoverInMapper.selectByPrimaryKey(zxSkTurnoverIn.getId());
        if(StrUtil.equals(dbZxSkTurnoverIn.getStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkTurnoverInItem dbzxSkTurnoverInItem = new ZxSkTurnoverInItem();
        dbzxSkTurnoverInItem.setBillID(dbZxSkTurnoverIn.getId());
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverInItemMapper.selectByZxSkTurnoverInItemList(dbzxSkTurnoverInItem);
        if(CollUtil.isEmpty(zxSkTurnoverInItemList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //购入单价合计
//        inPrice
        //购入单价(购入不含税单价)
//        inPriceNoTax
        //库存List
        //入库时都是使用含税单价合计   选择是否抵扣时.(是)抵扣使用的是不含税单价做计算,(否)抵扣使用的是含税单价合计做计算
        //0: 否, 1:是
        if(StrUtil.equals(dbZxSkTurnoverIn.getIsDeduct(),"1")){
            for (ZxSkTurnoverInItem zxSkTurnoverInItem : zxSkTurnoverInItemList) {
                zxSkTurnoverInItem.setInPrice(zxSkTurnoverInItem.getInPriceNoTax());
            }
        }
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = new ArrayList<>();
        for (ZxSkTurnoverInItem item : zxSkTurnoverInItemList) {
            ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
            //公司ID
            zxSkTurnOverStock.setCompanyID(dbZxSkTurnoverIn.getCompanyID());
            //项目ID(发料单位Id)
            zxSkTurnOverStock.setOrgID(dbZxSkTurnoverIn.getOrgID());
            //项目名称
            zxSkTurnOverStock.setOrgName(dbZxSkTurnoverIn.getOrgName());
            //物资Id
            zxSkTurnOverStock.setResID(item.getResID());
            //资源编号
            zxSkTurnOverStock.setResCode(item.getResCode());
            //资源名称
            zxSkTurnOverStock.setResName(item.getResName());
            //规格型号spec
            zxSkTurnOverStock.setSpec(item.getSpec());
            //单位unit
            zxSkTurnOverStock.setResUnit(item.getResUnit());
            //批次(明细id)
            zxSkTurnOverStock.setBatchNo(item.getId());
            //入库数量/数量
            zxSkTurnOverStock.setStockQty(item.getInQty());
            //入库单价/购入单价合计(含税单价)
            zxSkTurnOverStock.setStockPrice(item.getInPrice());
            //入库金额/总值
            zxSkTurnOverStock.setStockAmt(CalcUtils.calcDivide(item.getInPrice(),item.getInQty(),2));
            //原数量/数量
            zxSkTurnOverStock.setOriginalQty(item.getInQty());
            //原值
            zxSkTurnOverStock.setOriginalAmt(item.getInAmt());
            //净值
            zxSkTurnOverStock.setRemainAmt(item.getRemainAmt());
            zxSkTurnOverStockList.add(zxSkTurnOverStock);
        }
        //先添加库存
        ResponseEntity responseEntity = zxSkTurnOverStockService.addZxSkStock(zxSkTurnOverStockList);
        if(responseEntity.isSuccess()){
            dbZxSkTurnoverIn.setStatus("1");
            dbZxSkTurnoverIn.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnoverInMapper.checkZxSkTurnoverIn(dbZxSkTurnoverIn);
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
    public ResponseEntity getZxSkTurnoverInResourceList(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverIn.getPage(),zxSkTurnoverIn.getLimit());
        // 获取数据
        List<ZxSkTurnoverInItem> zxSkTurnoverInItemList = zxSkTurnoverInMapper.getZxSkTurnoverInResourceList(zxSkTurnoverIn);
        for (ZxSkTurnoverInItem zxSkTurnoverInItem : zxSkTurnoverInItemList) {
            zxSkTurnoverInItem.setPricingManner("移动平均");
            zxSkTurnoverInItem.setPlanningAuthorities("局机关");
        }
        // 得到分页信息
        PageInfo<ZxSkTurnoverInItem> p = new PageInfo<>(zxSkTurnoverInItemList);
        return repEntity.okList(zxSkTurnoverInItemList,p.getTotal());
    }

    //合同编号”+“周材入字第”+“年份-月份-顺序号”+“号”
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
        String no = contractNo + " 周材入字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public List<ZxSkTurnoverIn> ureportZxSkTurnoverInList(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // 获取数据
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverInMapper.getZxSkTurnoverInListForReport(zxSkTurnoverIn);
        return zxSkTurnoverInList;
    }

    @Override
    public ResponseEntity getUreportZxSkTurnoverInList(ZxSkTurnoverIn zxSkTurnoverIn) {
        if (zxSkTurnoverIn == null) {
            zxSkTurnoverIn = new ZxSkTurnoverIn();
        }
        // 获取数据
        List<ZxSkTurnoverIn> zxSkTurnoverInList = zxSkTurnoverInMapper.getZxSkTurnoverInListForReport(zxSkTurnoverIn);
        return repEntity.ok(zxSkTurnoverInList);
    }

}
