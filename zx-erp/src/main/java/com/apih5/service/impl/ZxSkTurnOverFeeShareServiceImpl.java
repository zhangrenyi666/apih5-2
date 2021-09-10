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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnOverFeeShare.setCompanyId("");
            zxSkTurnOverFeeShare.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTurnOverFeeShare.setCompanyId(zxSkTurnOverFeeShare.getOrgID());
            zxSkTurnOverFeeShare.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTurnOverFeeShare.setOrgID(zxSkTurnOverFeeShare.getOrgID());
        }

        // 分页查询
        PageHelper.startPage(zxSkTurnOverFeeShare.getPage(),zxSkTurnOverFeeShare.getLimit());
        // 获取数据
        List<ZxSkTurnOverFeeShare> zxSkTurnOverFeeShareList = zxSkTurnOverFeeShareMapper.selectByZxSkTurnOverFeeShareList(zxSkTurnOverFeeShare);
        //查询明细
        for (ZxSkTurnOverFeeShare zxSkTurnOverFeeShare1 : zxSkTurnOverFeeShareList) {
            ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
            zxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShare1.getId());
            List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
            zxSkTurnOverFeeShare1.setZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItems);
        }
        // 得到分页信息
        PageInfo<ZxSkTurnOverFeeShare> p = new PageInfo<>(zxSkTurnOverFeeShareList);

        return repEntity.okList(zxSkTurnOverFeeShareList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverFeeShareDetail(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        if (zxSkTurnOverFeeShare == null) {
            zxSkTurnOverFeeShare = new ZxSkTurnOverFeeShare();
        }
        // 获取数据
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        // 数据存在
        if (dbZxSkTurnOverFeeShare != null) {
            ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
            zxSkTurnOverFeeShareItem.setBillID(dbZxSkTurnOverFeeShare.getId());
            List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
            dbZxSkTurnOverFeeShare.setZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItems);
            return repEntity.ok(dbZxSkTurnOverFeeShare);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverFeeShare.setId(UuidUtil.generate());
        zxSkTurnOverFeeShare.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkTurnOverFeeShare.setBillStatus("0");
        //创建明细
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
           // 摊销单位ID
           dbZxSkTurnOverFeeShare.setOrgID(zxSkTurnOverFeeShare.getOrgID());
           // 摊销单位
           dbZxSkTurnOverFeeShare.setOrgName(zxSkTurnOverFeeShare.getOrgName());
           // 单据编号
           dbZxSkTurnOverFeeShare.setBillNo(zxSkTurnOverFeeShare.getBillNo());
           // 日期
           dbZxSkTurnOverFeeShare.setBusDate(zxSkTurnOverFeeShare.getBusDate());
           // 物资员
           dbZxSkTurnOverFeeShare.setConsignee(zxSkTurnOverFeeShare.getConsignee());
           // 单据状态
           dbZxSkTurnOverFeeShare.setBillStatus(zxSkTurnOverFeeShare.getBillStatus());
           // 公司id
           dbZxSkTurnOverFeeShare.setCompanyId(zxSkTurnOverFeeShare.getCompanyId());
           // 公司名称
           dbZxSkTurnOverFeeShare.setCompanyName(zxSkTurnOverFeeShare.getCompanyName());
           // 备注
           dbZxSkTurnOverFeeShare.setRemarks(zxSkTurnOverFeeShare.getRemarks());
           // 排序
           dbZxSkTurnOverFeeShare.setSort(zxSkTurnOverFeeShare.getSort());
           // 共通
           dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey, realName);
            //删除在新增
            ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
            zxSkTurnOverFeeShareItem.setBillID(zxSkTurnOverFeeShare.getId());
            List<ZxSkTurnOverFeeShareItem> zxSkTurnoverInItems = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(zxSkTurnOverFeeShareItem);
            if(zxSkTurnoverInItems != null && zxSkTurnoverInItems.size()>0) {
                zxSkTurnOverFeeShareItem.setModifyUserInfo(userKey, realName);
                zxSkTurnOverFeeShareItemMapper.batchDeleteUpdateZxSkTurnOverFeeShareItem(zxSkTurnoverInItems, zxSkTurnOverFeeShareItem);
            }
            //明细list
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
        // 失败
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
                // 删除明细
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverFeeShareList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //业主合同编号”+“周材摊字第”+“年份-月份-顺序号”+“号
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
        String no = contractNo + " 周材摊字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkTurnOverFeeShareResourceList(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        if (StrUtil.isEmpty(zxSkTurnOverFeeShare.getOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverFeeShare.getPage(),zxSkTurnOverFeeShare.getLimit());
        // 获取数据(通过库存)
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItems = zxSkTurnOverFeeShareMapper.getZxSkTurnOverFeeShareResourceList(zxSkTurnOverFeeShare);
        // 得到分页信息
        PageInfo<ZxSkTurnOverFeeShareItem> p = new PageInfo<>(zxSkTurnOverFeeShareItems);
        return repEntity.okList(zxSkTurnOverFeeShareItems, p.getTotal());
    }

    @Override
    public synchronized ResponseEntity checkZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        //修改累计摊销 (入库单的累计摊销)
        //净值 (库存的净值)
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        //然后审核数据
        if(StrUtil.equals(dbZxSkTurnOverFeeShare.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已审核的，请重新选择！");
        }
        //查询明细
        ZxSkTurnOverFeeShareItem dbzxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
        dbzxSkTurnOverFeeShareItem.setBillID(dbZxSkTurnOverFeeShare.getId());
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(dbzxSkTurnOverFeeShareItem);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //根据批次
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemListNew =
        zxSkTurnOverFeeShareItemList
            .stream()
            .filter(a -> CalcUtils.compareToZero(a.getShareAmt()) != 0)
            .collect(Collectors.toList());
//                        .forEach((o->o.setModifyUserInfo(userKey,realName)));
//                        .collect(Collectors.toMap(ZxSkTurnOverFeeShareItem::getBatchNo, a -> a, (k1, k2) -> k1));
        zxSkTurnOverFeeShareItemListNew.stream().forEach((o->o.setModifyUserInfo(userKey,realName)));
        if(CollectionUtil.isEmpty(zxSkTurnOverFeeShareItemListNew)){
            //审核
            dbZxSkTurnOverFeeShare.setBillStatus("1");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"审核成功");
        }
        flag = zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShareUpdateNum(zxSkTurnOverFeeShareItemListNew);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //审核
            dbZxSkTurnOverFeeShare.setBillStatus("1");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"审核成功");
        }
    }

    @Override
    public synchronized ResponseEntity counterCheckZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare) {
        //全部修改回去
        //修改累计摊销 (入库单的累计摊销)
        //净值 (库存的净值)
        ZxSkTurnOverFeeShare dbZxSkTurnOverFeeShare = zxSkTurnOverFeeShareMapper.selectByPrimaryKey(zxSkTurnOverFeeShare.getId());
        //然后审核数据
        if(StrUtil.equals(dbZxSkTurnOverFeeShare.getBillStatus(), "0")) {
            return repEntity.layerMessage("no", "未审核的，请重新选择！");
        }
        ZxSkTurnOverFeeShareItem dbzxSkTurnOverFeeShareItem = new ZxSkTurnOverFeeShareItem();
        dbzxSkTurnOverFeeShareItem.setBillID(dbZxSkTurnOverFeeShare.getId());
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList = zxSkTurnOverFeeShareItemMapper.selectByZxSkTurnOverFeeShareItemList(dbzxSkTurnOverFeeShareItem);
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //根据批次
        List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemListNew =
        zxSkTurnOverFeeShareItemList
                .stream()
                .filter(a -> CalcUtils.compareToZero(a.getShareAmt()) != 0)
                .collect(Collectors.toList());
//                        .forEach((o->o.setModifyUserInfo(userKey,realName)));
//                        .collect(Collectors.toMap(ZxSkTurnOverFeeShareItem::getBatchNo, a -> a, (k1, k2) -> k1));
        zxSkTurnOverFeeShareItemListNew.stream().forEach((o->o.setModifyUserInfo(userKey,realName)));
        if(CollectionUtil.isEmpty(zxSkTurnOverFeeShareItemListNew)){
            //审核
            dbZxSkTurnOverFeeShare.setBillStatus("0");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"反审核成功");
        }
        flag = zxSkTurnOverFeeShareMapper.counterCheckZxSkTurnOverFeeShare(zxSkTurnOverFeeShareItemListNew);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //审核
            dbZxSkTurnOverFeeShare.setBillStatus("0");
            dbZxSkTurnOverFeeShare.setModifyUserInfo(userKey,realName);
            zxSkTurnOverFeeShareMapper.checkZxSkTurnOverFeeShare(dbZxSkTurnOverFeeShare);
            return repEntity.layerMessage(true,zxSkTurnOverFeeShareItemListNew,"反审核成功");
        }
    }
}
