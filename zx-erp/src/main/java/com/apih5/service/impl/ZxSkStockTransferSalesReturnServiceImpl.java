package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkStockTransferSalesReturnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferSalesReturnService")
public class ZxSkStockTransferSalesReturnServiceImpl implements ZxSkStockTransferSalesReturnService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferSalesReturnMapper zxSkStockTransferSalesReturnMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemSalesReturnMapper zxSkStockTransItemSalesReturnMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSkStockMapper zxSkStockMapper;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    //预收单明细Mapper
    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptMapper zxSkStockTransItemInitialReceiptMapper;

    //收料单明细Mapper
    @Autowired(required = true)
    private ZxSkStockTransItemReceivingMapper zxSkStockTransItemReceivingMapper;

    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnListByCondition(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (zxSkStockTransferSalesReturn == null) {
            zxSkStockTransferSalesReturn = new ZxSkStockTransferSalesReturn();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferSalesReturn.setCompanyId("");
            zxSkStockTransferSalesReturn.setInOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferSalesReturn.setCompanyId(zxSkStockTransferSalesReturn.getInOrgID());
            zxSkStockTransferSalesReturn.setInOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferSalesReturn.setInOrgID(zxSkStockTransferSalesReturn.getInOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // 获取数据
        List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList = zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnList(zxSkStockTransferSalesReturn);
        //查询明细
        for (ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn1 : zxSkStockTransferSalesReturnList) {
            ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
            zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferSalesReturn1.getId());
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnListMinNumber(zxSkStockTransItemSalesReturn);
            zxSkStockTransferSalesReturn1.setZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturns);
        }
        //附件
        for (ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn1 : zxSkStockTransferSalesReturnList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferSalesReturn1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferSalesReturn1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkStockTransferSalesReturn> p = new PageInfo<>(zxSkStockTransferSalesReturnList);
        return repEntity.okList(zxSkStockTransferSalesReturnList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnDetails(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (zxSkStockTransferSalesReturn == null) {
            zxSkStockTransferSalesReturn = new ZxSkStockTransferSalesReturn();
        }
        // 获取数据
        ZxSkStockTransferSalesReturn dbZxSkStockTransferSalesReturn = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferSalesReturn.getId());
        // 数据存在
        if (dbZxSkStockTransferSalesReturn != null) {
            ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
            zxSkStockTransItemSalesReturn.setBillID(dbZxSkStockTransferSalesReturn.getId());
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
            for (ZxSkStockTransItemSalesReturn skStockTransItemSalesReturn : zxSkStockTransItemSalesReturns) {
                skStockTransItemSalesReturn.setId(skStockTransItemSalesReturn.getResID()+","+skStockTransItemSalesReturn.getInPrice());
            }
            dbZxSkStockTransferSalesReturn.setZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturns);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferSalesReturn.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferSalesReturn.setFileList(zxErpFiles);

            return repEntity.ok(dbZxSkStockTransferSalesReturn);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferSalesReturn.setId(UuidUtil.generate());
        zxSkStockTransferSalesReturn.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkStockTransferSalesReturn.setBillStatus("0");
        //totalAmt
        //单据金额
        BigDecimal total = new BigDecimal(0);//inAmt
        //创建明细
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransferSalesReturn.getZxSkStockTransItemSalesReturnList();
        if(zxSkStockTransItemSalesReturnList != null && zxSkStockTransItemSalesReturnList.size()>0) {
            for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnList) {
                if(CalcUtils.compareToZero(zxSkStockTransItemSalesReturn.getInAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemSalesReturn.getInAmt());
                }
                //resAllFee ->inAmtAll
                //含税金额 -> 总价
                if(CalcUtils.compareToZero(zxSkStockTransItemSalesReturn.getResAllFee())!=0){
                    zxSkStockTransItemSalesReturn.setInAmtAll(zxSkStockTransItemSalesReturn.getResAllFee());
                }else {
                    zxSkStockTransItemSalesReturn.setInAmtAll(new BigDecimal(0));
                }
                zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferSalesReturn.getId());

                //是否结算
                zxSkStockTransItemSalesReturn.setSettlementStatus("0");
//                明细id通过前端上传(改了)
                zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId(zxSkStockTransItemSalesReturn.getId());
                zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId((UuidUtil.generate()));
                zxSkStockTransItemSalesReturn.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemSalesReturnMapper.insert(zxSkStockTransItemSalesReturn);
            }
        }
        //添加附件
        List<ZxErpFile> fileList = zxSkStockTransferSalesReturn.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferSalesReturn.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferSalesReturn.setTotalAmt(total);
        int flag = zxSkStockTransferSalesReturnMapper.insert(zxSkStockTransferSalesReturn);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferSalesReturn);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferSalesReturn dbzxSkStockTransferSalesReturn = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferSalesReturn.getId());
        if (dbzxSkStockTransferSalesReturn != null && StrUtil.isNotEmpty(dbzxSkStockTransferSalesReturn.getId())) {
           // 仓库(1)
           dbzxSkStockTransferSalesReturn.setOrgID(zxSkStockTransferSalesReturn.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferSalesReturn.setWhOrgID(zxSkStockTransferSalesReturn.getWhOrgID());
           // 退货单位ID
           dbzxSkStockTransferSalesReturn.setOutOrgID(zxSkStockTransferSalesReturn.getOutOrgID());
           // 收料单位ID
           dbzxSkStockTransferSalesReturn.setInOrgID(zxSkStockTransferSalesReturn.getInOrgID());
           // 业务类型
           dbzxSkStockTransferSalesReturn.setBizType(zxSkStockTransferSalesReturn.getBizType());
           // 单据编号
           dbzxSkStockTransferSalesReturn.setBillNo(zxSkStockTransferSalesReturn.getBillNo());
           // 物资来源
           dbzxSkStockTransferSalesReturn.setMaterialSource(zxSkStockTransferSalesReturn.getMaterialSource());
           // 用途去向
           dbzxSkStockTransferSalesReturn.setPurpose(zxSkStockTransferSalesReturn.getPurpose());
           // 业务日期
           dbzxSkStockTransferSalesReturn.setBusDate(zxSkStockTransferSalesReturn.getBusDate());
           // 退货单位
           dbzxSkStockTransferSalesReturn.setOutOrgName(zxSkStockTransferSalesReturn.getOutOrgName());
           // 收料单位
           dbzxSkStockTransferSalesReturn.setInOrgName(zxSkStockTransferSalesReturn.getInOrgName());
           // 单据金额
           dbzxSkStockTransferSalesReturn.setTotalAmt(zxSkStockTransferSalesReturn.getTotalAmt());
           // 经办人(1)
           dbzxSkStockTransferSalesReturn.setOuttransactor(zxSkStockTransferSalesReturn.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferSalesReturn.setIntransactor(zxSkStockTransferSalesReturn.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferSalesReturn.setWaretransactor(zxSkStockTransferSalesReturn.getWaretransactor());
           // 采购人
           dbzxSkStockTransferSalesReturn.setBuyer(zxSkStockTransferSalesReturn.getBuyer());
           // 收料人
           dbzxSkStockTransferSalesReturn.setConsignee(zxSkStockTransferSalesReturn.getConsignee());
           // 审核人
           dbzxSkStockTransferSalesReturn.setAuditor(zxSkStockTransferSalesReturn.getAuditor());
           // 凭证号
           dbzxSkStockTransferSalesReturn.setVoucherNo(zxSkStockTransferSalesReturn.getVoucherNo());
           // 合同号
           dbzxSkStockTransferSalesReturn.setContractNo(zxSkStockTransferSalesReturn.getContractNo());
           // 发票号
           dbzxSkStockTransferSalesReturn.setInvoiceNo(zxSkStockTransferSalesReturn.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferSalesReturn.setBillType(zxSkStockTransferSalesReturn.getBillType());
           // 单据标志
           dbzxSkStockTransferSalesReturn.setBillFlag(zxSkStockTransferSalesReturn.getBillFlag());
           // 单据状态
           dbzxSkStockTransferSalesReturn.setBillStatus(zxSkStockTransferSalesReturn.getBillStatus());
           // 制单人
           dbzxSkStockTransferSalesReturn.setReporter(zxSkStockTransferSalesReturn.getReporter());
           // 扣款类型
           dbzxSkStockTransferSalesReturn.setDeductAmtType(zxSkStockTransferSalesReturn.getDeductAmtType());
           // 备注
           dbzxSkStockTransferSalesReturn.setRemark(zxSkStockTransferSalesReturn.getRemark());
           // 物资类别ID
           dbzxSkStockTransferSalesReturn.setResourceID(zxSkStockTransferSalesReturn.getResourceID());
           // 物资类别
           dbzxSkStockTransferSalesReturn.setResourceName(zxSkStockTransferSalesReturn.getResourceName());
           // 税率(%)
           dbzxSkStockTransferSalesReturn.setTaxRate(zxSkStockTransferSalesReturn.getTaxRate());
           // 是否抵扣
           dbzxSkStockTransferSalesReturn.setIsDeduct(zxSkStockTransferSalesReturn.getIsDeduct());
           // 仓库(3)
           dbzxSkStockTransferSalesReturn.setWarehouseName(zxSkStockTransferSalesReturn.getWarehouseName());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferSalesReturn.setInvoiceNum(zxSkStockTransferSalesReturn.getInvoiceNum());
           // 公司id
           dbzxSkStockTransferSalesReturn.setCompanyId(zxSkStockTransferSalesReturn.getCompanyId());
           // 公司名称
           dbzxSkStockTransferSalesReturn.setCompanyName(zxSkStockTransferSalesReturn.getCompanyName());
           // 共通
           dbzxSkStockTransferSalesReturn.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
            zxSkStockTransItemSalesReturn.setBillID(dbzxSkStockTransferSalesReturn.getId());
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
            if(zxSkStockTransItemSalesReturns != null && zxSkStockTransItemSalesReturns.size()>0) {
                zxSkStockTransItemSalesReturn.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemSalesReturnMapper.batchDeleteUpdateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturns, zxSkStockTransItemSalesReturn);
            }
            //明细list
            List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransferSalesReturn.getZxSkStockTransItemSalesReturnList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemSalesReturnList != null && zxSkStockTransItemSalesReturnList.size()>0) {
                for(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn1 : zxSkStockTransItemSalesReturnList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemSalesReturn1.getInAmt());
                    zxSkStockTransItemSalesReturn.setZxSkStockTransItemSalesReturnId(zxSkStockTransItemSalesReturn.getId());
                    zxSkStockTransItemSalesReturn1.setZxSkStockTransItemSalesReturnId(UuidUtil.generate());
                    zxSkStockTransItemSalesReturn1.setBillID(dbzxSkStockTransferSalesReturn.getId());
                    zxSkStockTransItemSalesReturn1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemSalesReturnMapper.insert(zxSkStockTransItemSalesReturn1);
                }
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferSalesReturn.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkStockTransferSalesReturn.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferSalesReturn.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferSalesReturn.setTotalAmt(total);
            flag = zxSkStockTransferSalesReturnMapper.updateByPrimaryKey(dbzxSkStockTransferSalesReturn);

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferSalesReturn);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferSalesReturn(List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferSalesReturnList != null && zxSkStockTransferSalesReturnList.size() > 0) {
            for (ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn : zxSkStockTransferSalesReturnList) {
                // 删除明细
                ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn = new ZxSkStockTransItemSalesReturn();
                zxSkStockTransItemSalesReturn.setBillID(zxSkStockTransferSalesReturn.getId());
                List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnList(zxSkStockTransItemSalesReturn);
                if(zxSkStockTransItemSalesReturns != null && zxSkStockTransItemSalesReturns.size()>0) {
                    zxSkStockTransItemSalesReturn.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemSalesReturnMapper.batchDeleteUpdateZxSkStockTransItemSalesReturn(zxSkStockTransItemSalesReturns, zxSkStockTransItemSalesReturn);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferSalesReturn.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn = new ZxSkStockTransferSalesReturn();
           zxSkStockTransferSalesReturn.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferSalesReturnMapper.batchDeleteUpdateZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturnList, zxSkStockTransferSalesReturn);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferSalesReturnList);
        }
    }

    //审核退货
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        //审核数据
        ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn1 = zxSkStockTransferSalesReturnMapper.selectByPrimaryKey(zxSkStockTransferSalesReturn.getId());
        if(StrUtil.equals(zxSkStockTransferSalesReturn1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //获取明细
        ZxSkStockTransItemSalesReturn dbzxSkStockTransItemSalesReturn1 = new ZxSkStockTransItemSalesReturn();
        dbzxSkStockTransItemSalesReturn1.setBillID(zxSkStockTransferSalesReturn1.getId());
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList = zxSkStockTransItemSalesReturnMapper.selectByZxSkStockTransItemSalesReturnListMinNumber(dbzxSkStockTransItemSalesReturn1);
        if(CollUtil.isEmpty(zxSkStockTransItemSalesReturnList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //查询可以使用的数量
        //提供数据
        //(根据仓库id(whOrgID),项目id(收料单位inOrgID),退货部门id(供货商outOrgID),分类id(resourceID),物资编码ID List)
        Map<String,ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnMap = zxSkStockTransferSalesReturnMapper.getZxSkStockTransferSalesReturnResName(zxSkStockTransItemSalesReturnList);

        //退货单可退数量
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnListIsOutNumber = new ArrayList<>();

        //错误List (审核的数量大于库存数量)
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns = new ArrayList<>();
        for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnList) {
            ZxSkStockTransItemSalesReturn dbzxSkStockTransItemSalesReturn = zxSkStockTransItemSalesReturnMap.get(zxSkStockTransItemSalesReturn.getMainIdAndItemId());
            if(CalcUtils.compareTo(dbzxSkStockTransItemSalesReturn.getIsOutNumber(),zxSkStockTransItemSalesReturn.getInQty())<0){
                zxSkStockTransItemSalesReturns.add(zxSkStockTransItemSalesReturn);
            }else {
                String str = zxSkStockTransItemSalesReturn.getMainIdAndItemId();
                ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn1 = new ZxSkStockTransItemSalesReturn();
                zxSkStockTransItemSalesReturn1.setId(StrUtil.sub(str,str.indexOf("-")+1,str.length()));
                zxSkStockTransItemSalesReturn1.setIsOutNumber(CalcUtils.calcSubtract(dbzxSkStockTransItemSalesReturn.getIsOutNumber(),zxSkStockTransItemSalesReturn.getInQty()));
                zxSkStockTransItemSalesReturn1.setModifyUserInfo(userKey,realName);
                zxSkStockTransItemSalesReturnListIsOutNumber.add(zxSkStockTransItemSalesReturn1);

            }
        }
        if(CollUtil.isNotEmpty(zxSkStockTransItemSalesReturns)){
            return repEntity.layerMessage(false,zxSkStockTransItemSalesReturns, "退货单的数量不能大于收料的数量");
        }
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferSalesReturn.getCompanyId());
            //项目ID(收料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferSalesReturn.getInOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferSalesReturn.getWhOrgID());
            //物资大类ID
            zxSkStock.setResourceId(zxSkStockTransferSalesReturn.getResourceID());
            //物资大类编码
            zxSkStock.setResourceName(zxSkStockTransferSalesReturn.getResourceName());
            //物资Id
            zxSkStock.setResID(zxSkStockTransItemSalesReturn.getResID());
            //资源编号
            zxSkStock.setResCode(zxSkStockTransItemSalesReturn.getResCode());
            //资源名称
            zxSkStock.setResName(zxSkStockTransItemSalesReturn.getResName());
            //规格型号spec
            zxSkStock.setSpec(zxSkStockTransItemSalesReturn.getSpec());
            //单位unit
            zxSkStock.setUnit(zxSkStockTransItemSalesReturn.getResUnit());
            //退货数量
            zxSkStock.setStockQty(zxSkStockTransItemSalesReturn.getInQty());
            //退货金额
            zxSkStock.setStockAmt(zxSkStockTransItemSalesReturn.getInAmt());
            zxSkStockList.add(zxSkStock);
        }
        //减库存
        ResponseEntity responseEntity = ZxSkStockServiceImpl.reduceZxSkStockPriceChange(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferSalesReturn.setBillStatus("1");
            zxSkStockTransferSalesReturn.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferSalesReturnMapper.checkZxSkStockTransferSalesReturn(zxSkStockTransferSalesReturn);
            //并且对预收单或者收料单中的可退库数量进行扣减
            int flag2 = 0;
            for (ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn : zxSkStockTransItemSalesReturnListIsOutNumber) {
                flag2 = zxSkStockTransItemInitialReceiptMapper.updateZxSkStockTransItemReceivingMapperIsOutNumber(zxSkStockTransItemSalesReturn);
                if(NumberUtil.equals(new BigDecimal(flag2),new BigDecimal("0"))){
                    zxSkStockTransItemReceivingMapper.updateZxSkStockTransItemReceivingMapperIsOutNumber(zxSkStockTransItemSalesReturn);
                }
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

    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnNo(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if(StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID()) || zxSkStockTransferSalesReturn.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferSalesReturn.getInOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferSalesReturn.getBusDate());
        int month = DateUtil.month(zxSkStockTransferSalesReturn.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferSalesReturn.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferSalesReturnMapper.getZxSkStockTransferSalesReturnCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 退货字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    //收料单位ID:inOrgID
    //仓库id:whOrgID
    //根据仓库id,项目id
    //获取供应商
    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnOutOrgName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getWhOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // 获取数据
        List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList =
                zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnOutOrgNameList(zxSkStockTransferSalesReturn);
        // 得到分页信息
        PageInfo<ZxSkStockTransferSalesReturn> p = new PageInfo<>(zxSkStockTransferSalesReturnList);
        return repEntity.okList(zxSkStockTransferSalesReturnList, p.getTotal());
    }

    //根据仓库id,项目id,退货单位id        outOrgID,outOrgName
    //查询的 预收初始单/收料单 的   物资大类        resourceID  resourceName
    //获取物资大类
    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnResourceName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getOutOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // 获取数据
        List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList =
                zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnResourceNameList(zxSkStockTransferSalesReturn);
        // 得到分页信息
        PageInfo<ZxSkStockTransferSalesReturn> p = new PageInfo<>(zxSkStockTransferSalesReturnList);
        return repEntity.okList(zxSkStockTransferSalesReturnList, p.getTotal());
    }

    //todo: 最大值为0要删除该条数据
    @Override
    public ResponseEntity getZxSkStockTransferSalesReturnResName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn) {
        if (StrUtil.isEmpty(zxSkStockTransferSalesReturn.getWhOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getOutOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getInOrgID())
                || StrUtil.isEmpty(zxSkStockTransferSalesReturn.getResourceID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferSalesReturn.getPage(),zxSkStockTransferSalesReturn.getLimit());
        // 获取数据
        List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturns =
                zxSkStockTransferSalesReturnMapper.selectByZxSkStockTransferSalesReturnResNameList(zxSkStockTransferSalesReturn);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemSalesReturn> p = new PageInfo<>(zxSkStockTransItemSalesReturns);
        return repEntity.okList(zxSkStockTransItemSalesReturns, p.getTotal());
    }




}
