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
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxSkStockTransItemInitialReceiptMapper;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxSkStock;
import com.apih5.mybatis.pojo.ZxSkStockTransItemInitialReceipt;
import com.apih5.service.ZxSkStockService;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockTransferInitialReceiptMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import com.apih5.service.ZxSkStockTransferInitialReceiptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferInitialReceiptService")
public class ZxSkStockTransferInitialReceiptServiceImpl implements ZxSkStockTransferInitialReceiptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferInitialReceiptMapper zxSkStockTransferInitialReceiptMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptMapper zxSkStockTransItemInitialReceiptMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxSkStockTransferInitialReceiptListByCondition(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        if (zxSkStockTransferInitialReceipt == null) {
            zxSkStockTransferInitialReceipt = new ZxSkStockTransferInitialReceipt();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferInitialReceipt.setCompanyId("");
            zxSkStockTransferInitialReceipt.setInOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferInitialReceipt.getOrgID2())){
                zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferInitialReceipt.setCompanyId(zxSkStockTransferInitialReceipt.getInOrgID());
            zxSkStockTransferInitialReceipt.setInOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferInitialReceipt.getOrgID2())){
                zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getInOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferInitialReceipt.getOrgID2())){
                zxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferInitialReceipt.getPage(),zxSkStockTransferInitialReceipt.getLimit());
        // 获取数据
        List<ZxSkStockTransferInitialReceipt> zxSkStockTransferInitialReceiptList = zxSkStockTransferInitialReceiptMapper.selectByZxSkStockTransferInitialReceiptList(zxSkStockTransferInitialReceipt);
        //查询明细
        for (ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt1 : zxSkStockTransferInitialReceiptList) {
            //单据金额
            zxSkStockTransferInitialReceipt1.setTotalAmt(NumberUtil.round(zxSkStockTransferInitialReceipt1.getTotalAmt(),2));
            ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
            zxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt1.getId());
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
            zxSkStockTransferInitialReceipt1.setZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipts);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferInitialReceipt> p = new PageInfo<>(zxSkStockTransferInitialReceiptList);
        return repEntity.okList(zxSkStockTransferInitialReceiptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferInitialReceiptDetails(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        if (zxSkStockTransferInitialReceipt == null) {
            zxSkStockTransferInitialReceipt = new ZxSkStockTransferInitialReceipt();
        }
        // 获取数据
        ZxSkStockTransferInitialReceipt dbZxSkStockTransferInitialReceipt = zxSkStockTransferInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransferInitialReceipt.getId());
        // 数据存在
        if (dbZxSkStockTransferInitialReceipt != null) {
            ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
            zxSkStockTransItemInitialReceipt.setBillID(dbZxSkStockTransferInitialReceipt.getId());
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
            dbZxSkStockTransferInitialReceipt.setZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipts);
            return repEntity.ok(dbZxSkStockTransferInitialReceipt);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferInitialReceipt.setId(UuidUtil.generate());
        zxSkStockTransferInitialReceipt.setCreateUserInfo(userKey, realName);

        //默认审核状态: 0:未审核
        zxSkStockTransferInitialReceipt.setBillStatus("0");
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransferInitialReceipt.getZxSkStockTransItemInitialReceiptList();
        if(zxSkStockTransItemInitialReceiptList != null && zxSkStockTransItemInitialReceiptList.size()>0) {
            for (ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt : zxSkStockTransItemInitialReceiptList) {
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getInAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemInitialReceipt.getInAmt());
                }
                //含税单价 ->   采购单价
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getInPrice())!=0){
                    zxSkStockTransItemInitialReceipt.setStdPrice(zxSkStockTransItemInitialReceipt.getInPrice());
                }else {
                    zxSkStockTransItemInitialReceipt.setStdPrice(new BigDecimal(0));
                }
                //不含税金额 -> 不含税总价
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getResAllFeeNoTax())!=0){
                    zxSkStockTransItemInitialReceipt.setInAmtNoTax(zxSkStockTransItemInitialReceipt.getResAllFeeNoTax());
                }else {
                    zxSkStockTransItemInitialReceipt.setInAmtNoTax(new BigDecimal(0));
                }
                //初始明细数量 -> 可退数量
                if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt.getInQty())!=0){
                    zxSkStockTransItemInitialReceipt.setIsOutNumber(zxSkStockTransItemInitialReceipt.getInQty());
                }else {
                    zxSkStockTransItemInitialReceipt.setIsOutNumber(new BigDecimal(0));
                }
                zxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt.getId());
                zxSkStockTransItemInitialReceipt.setId((UuidUtil.generate()));
                zxSkStockTransItemInitialReceipt.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemInitialReceiptMapper.insert(zxSkStockTransItemInitialReceipt);
            }
        }
        zxSkStockTransferInitialReceipt.setTotalAmt(total);
        int flag = zxSkStockTransferInitialReceiptMapper.insert(zxSkStockTransferInitialReceipt);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferInitialReceipt);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferInitialReceipt dbzxSkStockTransferInitialReceipt = zxSkStockTransferInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransferInitialReceipt.getId());
        if (dbzxSkStockTransferInitialReceipt != null && StrUtil.isNotEmpty(dbzxSkStockTransferInitialReceipt.getId())) {
           // 仓库(1)所属机构id
           dbzxSkStockTransferInitialReceipt.setOrgID(zxSkStockTransferInitialReceipt.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferInitialReceipt.setWhOrgID(zxSkStockTransferInitialReceipt.getWhOrgID());
           // 仓库(3)
           dbzxSkStockTransferInitialReceipt.setWarehouseName(zxSkStockTransferInitialReceipt.getWarehouseName());
           // 供货单位ID
           dbzxSkStockTransferInitialReceipt.setOutOrgID(zxSkStockTransferInitialReceipt.getOutOrgID());
           // 收料单位ID
           dbzxSkStockTransferInitialReceipt.setInOrgID(zxSkStockTransferInitialReceipt.getInOrgID());
           // 业务类型
           dbzxSkStockTransferInitialReceipt.setBizType(zxSkStockTransferInitialReceipt.getBizType());
           // 单据编号
           dbzxSkStockTransferInitialReceipt.setBillNo(zxSkStockTransferInitialReceipt.getBillNo());
           // 物资来源
           dbzxSkStockTransferInitialReceipt.setMaterialSource(zxSkStockTransferInitialReceipt.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferInitialReceipt.setPurpose(zxSkStockTransferInitialReceipt.getPurpose());
           // 业务日期
           dbzxSkStockTransferInitialReceipt.setBusDate(zxSkStockTransferInitialReceipt.getBusDate());
           // 供货单位
           dbzxSkStockTransferInitialReceipt.setOutOrgName(zxSkStockTransferInitialReceipt.getOutOrgName());
           // 收料单位
           dbzxSkStockTransferInitialReceipt.setInOrgName(zxSkStockTransferInitialReceipt.getInOrgName());
           // 单据金额

           // 经办人(1)
           dbzxSkStockTransferInitialReceipt.setOuttransactor(zxSkStockTransferInitialReceipt.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferInitialReceipt.setIntransactor(zxSkStockTransferInitialReceipt.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferInitialReceipt.setWaretransactor(zxSkStockTransferInitialReceipt.getWaretransactor());
           // 发票号
           dbzxSkStockTransferInitialReceipt.setBuyer(zxSkStockTransferInitialReceipt.getBuyer());
           // 收料人
           dbzxSkStockTransferInitialReceipt.setConsignee(zxSkStockTransferInitialReceipt.getConsignee());
           // 审核人
           dbzxSkStockTransferInitialReceipt.setAuditor(zxSkStockTransferInitialReceipt.getAuditor());
           // 凭证号
           dbzxSkStockTransferInitialReceipt.setVoucherNo(zxSkStockTransferInitialReceipt.getVoucherNo());
           // 合同号
           dbzxSkStockTransferInitialReceipt.setContractNo(zxSkStockTransferInitialReceipt.getContractNo());
           // 发票号
           dbzxSkStockTransferInitialReceipt.setInvoiceNo(zxSkStockTransferInitialReceipt.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferInitialReceipt.setBillType(zxSkStockTransferInitialReceipt.getBillType());
           // 单据标志
           dbzxSkStockTransferInitialReceipt.setBillFlag(zxSkStockTransferInitialReceipt.getBillFlag());
           // 单据状态
           dbzxSkStockTransferInitialReceipt.setBillStatus(zxSkStockTransferInitialReceipt.getBillStatus());
           // 制单人
           dbzxSkStockTransferInitialReceipt.setReporter(zxSkStockTransferInitialReceipt.getReporter());
           // 扣款类型
           dbzxSkStockTransferInitialReceipt.setDeductAmtType(zxSkStockTransferInitialReceipt.getDeductAmtType());
           // 备注
           dbzxSkStockTransferInitialReceipt.setRemark(zxSkStockTransferInitialReceipt.getRemark());
           // 明细
           dbzxSkStockTransferInitialReceipt.setCombProp(zxSkStockTransferInitialReceipt.getCombProp());
           // 截止日期
           dbzxSkStockTransferInitialReceipt.setExpirationDate(zxSkStockTransferInitialReceipt.getExpirationDate());
           // 物资类别ID
           dbzxSkStockTransferInitialReceipt.setResourceID(zxSkStockTransferInitialReceipt.getResourceID());
           // 物资类别
           dbzxSkStockTransferInitialReceipt.setResourceName(zxSkStockTransferInitialReceipt.getResourceName());
           // cbsID
           dbzxSkStockTransferInitialReceipt.setCbsID(zxSkStockTransferInitialReceipt.getCbsID());
           // cbs名称
           dbzxSkStockTransferInitialReceipt.setCbsName(zxSkStockTransferInitialReceipt.getCbsName());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferInitialReceipt.setInvoiceNum(zxSkStockTransferInitialReceipt.getInvoiceNum());
           // 税率(%)
           dbzxSkStockTransferInitialReceipt.setTaxRate(zxSkStockTransferInitialReceipt.getTaxRate());
           // 是否抵扣
           dbzxSkStockTransferInitialReceipt.setIsDeduct(zxSkStockTransferInitialReceipt.getIsDeduct());
           // 是否预收
           dbzxSkStockTransferInitialReceipt.setOrNotPrecollecte(zxSkStockTransferInitialReceipt.getOrNotPrecollecte());
           // 公司id
           dbzxSkStockTransferInitialReceipt.setCompanyId(zxSkStockTransferInitialReceipt.getCompanyId());
           // 公司名称
           dbzxSkStockTransferInitialReceipt.setCompanyName(zxSkStockTransferInitialReceipt.getCompanyName());
           // 共通
           dbzxSkStockTransferInitialReceipt.setModifyUserInfo(userKey, realName);
            //删除在新增
            ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
            zxSkStockTransItemInitialReceipt.setBillID(dbzxSkStockTransferInitialReceipt.getId());
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
            if(zxSkStockTransItemInitialReceipts != null && zxSkStockTransItemInitialReceipts.size()>0) {
                zxSkStockTransItemInitialReceipt.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemInitialReceiptMapper.batchDeleteUpdateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceipts, zxSkStockTransItemInitialReceipt);
            }
            //明细list
            List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransferInitialReceipt.getZxSkStockTransItemInitialReceiptList();
            //单据金额
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemInitialReceiptList != null && zxSkStockTransItemInitialReceiptList.size()>0) {
                for(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt1 : zxSkStockTransItemInitialReceiptList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemInitialReceipt1.getInAmt());

                    //含税单价 ->   采购单价
                    if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt1.getInPrice())!=0){
                        zxSkStockTransItemInitialReceipt1.setStdPrice(zxSkStockTransItemInitialReceipt1.getInPrice());
                    }else {
                        zxSkStockTransItemInitialReceipt1.setStdPrice(new BigDecimal(0));
                    }
                    //不含税金额 -> 不含税总价
                    if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt1.getResAllFeeNoTax())!=0){
                        zxSkStockTransItemInitialReceipt1.setInAmtNoTax(zxSkStockTransItemInitialReceipt1.getResAllFeeNoTax());
                    }else {
                        zxSkStockTransItemInitialReceipt1.setInAmtNoTax(new BigDecimal(0));
                    }
                    //初始明细数量 -> 可退数量
                    if(CalcUtils.compareToZero(zxSkStockTransItemInitialReceipt1.getInQty())!=0){
                        zxSkStockTransItemInitialReceipt1.setIsOutNumber(zxSkStockTransItemInitialReceipt1.getInQty());
                    }else {
                        zxSkStockTransItemInitialReceipt1.setIsOutNumber(new BigDecimal(0));
                    }
                    zxSkStockTransItemInitialReceipt1.setId(UuidUtil.generate());
                    zxSkStockTransItemInitialReceipt1.setBillID(zxSkStockTransferInitialReceipt.getId());
                    zxSkStockTransItemInitialReceipt1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemInitialReceiptMapper.insert(zxSkStockTransItemInitialReceipt1);
                }
            }

            dbzxSkStockTransferInitialReceipt.setTotalAmt(total);
            flag = zxSkStockTransferInitialReceiptMapper.updateByPrimaryKey(dbzxSkStockTransferInitialReceipt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferInitialReceipt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferInitialReceipt(List<ZxSkStockTransferInitialReceipt> zxSkStockTransferInitialReceiptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferInitialReceiptList != null && zxSkStockTransferInitialReceiptList.size() > 0) {
            for (ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt : zxSkStockTransferInitialReceiptList) {
                // 删除明细
                ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
                zxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt.getId());
                List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceipts = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(zxSkStockTransItemInitialReceipt);
                if(zxSkStockTransItemInitialReceipts != null && zxSkStockTransItemInitialReceipts.size()>0) {
                    zxSkStockTransItemInitialReceipt.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemInitialReceiptMapper.batchDeleteUpdateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceipts, zxSkStockTransItemInitialReceipt);
                }
            }
           ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt = new ZxSkStockTransferInitialReceipt();
           zxSkStockTransferInitialReceipt.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferInitialReceiptMapper.batchDeleteUpdateZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceiptList, zxSkStockTransferInitialReceipt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferInitialReceiptList);
        }
    }

    @Override
    public ResponseEntity getZxSkStockTransferInitialReceiptNo(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        if(StrUtil.isEmpty(zxSkStockTransferInitialReceipt.getInOrgID()) || zxSkStockTransferInitialReceipt.getBusDate() == null){
            return repEntity.ok(null);
        }
        String orgID = zxSkStockTransferInitialReceipt.getInOrgID();
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferInitialReceipt.getBusDate());
        int month = DateUtil.month(zxSkStockTransferInitialReceipt.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferInitialReceipt.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" + (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferInitialReceiptMapper.getZxSkStockTransferInitialReceiptCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + "预收字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public synchronized ResponseEntity checkZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        //查询审核数据
        ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt1 = zxSkStockTransferInitialReceiptMapper.selectByPrimaryKey(zxSkStockTransferInitialReceipt.getId());
        if(StrUtil.equals(zxSkStockTransferInitialReceipt1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //查询明细
        ZxSkStockTransItemInitialReceipt dbzxSkStockTransItemInitialReceipt = new ZxSkStockTransItemInitialReceipt();
        dbzxSkStockTransItemInitialReceipt.setBillID(zxSkStockTransferInitialReceipt1.getId());
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList = zxSkStockTransItemInitialReceiptMapper.selectByZxSkStockTransItemInitialReceiptList(dbzxSkStockTransItemInitialReceipt);
        if(CollUtil.isEmpty(zxSkStockTransItemInitialReceiptList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //先添加库存
        List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptListNew = new ArrayList<>();
        zxSkStockTransItemInitialReceiptList
                //并行流parallelStream
                .parallelStream()
                //collect就是一个归约操作，就像reduce一样可以接受各种做法作为参数，将流中的元素累积成一个汇总结果
                .collect(Collectors.groupingBy(o -> (o.getResID()+o.getInPrice()), Collectors.toList())).forEach(
                (id,transfer) -> {
                    transfer.stream().reduce((a,b) -> new ZxSkStockTransItemInitialReceipt(
                            //resID
                            a.getResID(),
                            //code
                            a.getResCode(),
                            //Name
                            a.getResName(),
                            //spec
                            a.getSpec(),
                            //unit
                            a.getResUnit(),
                            //数量聚合
                            CalcUtils.calcAdd(a.getInQty(),b.getInQty()),
                            //总金额聚合
                            CalcUtils.calcAdd(a.getInAmt(),b.getInAmt())
                    )).ifPresent(zxSkStockTransItemInitialReceiptListNew::add);
                }
        );
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt : zxSkStockTransItemInitialReceiptListNew) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferInitialReceipt1.getCompanyId());
            //项目ID(收料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferInitialReceipt1.getInOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferInitialReceipt1.getWhOrgID());
            //物资大类ID
            zxSkStock.setResourceId(zxSkStockTransferInitialReceipt1.getResourceID());
            //物资大类编码
            zxSkStock.setResourceName(zxSkStockTransferInitialReceipt1.getResourceName());
            //物资Id
            zxSkStock.setResID(zxSkStockTransItemInitialReceipt.getResID());
            //资源编号
            zxSkStock.setResCode(zxSkStockTransItemInitialReceipt.getResCode());
            //资源名称
            zxSkStock.setResName(zxSkStockTransItemInitialReceipt.getResName());
            //规格型号spec
            zxSkStock.setSpec(zxSkStockTransItemInitialReceipt.getSpec());
            //单位unit
            zxSkStock.setUnit(zxSkStockTransItemInitialReceipt.getResUnit());
            //库存数量/实收数量
            zxSkStock.setStockQty(zxSkStockTransItemInitialReceipt.getInQty());
            //库存金额(不含税金额)//改了(入账金额)
            zxSkStock.setStockAmt(zxSkStockTransItemInitialReceipt.getInAmt());
            zxSkStockList.add(zxSkStock);
        }
        //加库存
        ResponseEntity responseEntity = zxSkStockService.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferInitialReceipt1.setBillStatus("1");
            zxSkStockTransferInitialReceipt1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferInitialReceiptMapper.checkZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceipt1);
            // 失败
            if (flag == 0) {
                try {
                    throw new Exception("修改审核异常");
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
