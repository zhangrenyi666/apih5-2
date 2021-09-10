package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.apih5.service.ZxSkStockTransferWithdrawalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockTransferWithdrawalService")
public class ZxSkStockTransferWithdrawalServiceImpl implements ZxSkStockTransferWithdrawalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferWithdrawalMapper zxSkStockTransferWithdrawalMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemWithdrawalMapper zxSkStockTransItemWithdrawalMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxSkStockServiceImpl ZxSkStockServiceImpl;

    @Autowired(required = true)
    private ZxSkStockTransferMaterialRequisitionMapper zxSkStockTransferMaterialRequisitionMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxSkStockTransItemMaterialRequisitionMapper zxSkStockTransItemMaterialRequisitionMapper;

    @Override
    public ResponseEntity getZxSkStockTransferWithdrawalListByCondition(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        if (zxSkStockTransferWithdrawal == null) {
            zxSkStockTransferWithdrawal = new ZxSkStockTransferWithdrawal();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockTransferWithdrawal.setCompanyId("");
            zxSkStockTransferWithdrawal.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferWithdrawal.getOrgID2())){
                zxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockTransferWithdrawal.setCompanyId(zxSkStockTransferWithdrawal.getOutOrgID());
            zxSkStockTransferWithdrawal.setOutOrgID("");
            if(StrUtil.isNotEmpty(zxSkStockTransferWithdrawal.getOrgID2())){
                zxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockTransferWithdrawal.setOutOrgID(zxSkStockTransferWithdrawal.getOutOrgID());
            if(StrUtil.isNotEmpty(zxSkStockTransferWithdrawal.getOrgID2())){
                zxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferWithdrawal.getPage(),zxSkStockTransferWithdrawal.getLimit());
        // 获取数据
        List<ZxSkStockTransferWithdrawal> zxSkStockTransferWithdrawalList = zxSkStockTransferWithdrawalMapper.selectByZxSkStockTransferWithdrawalList(zxSkStockTransferWithdrawal);
        //查询明细
        for (ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal1 : zxSkStockTransferWithdrawalList) {
            ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
            zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal1.getId());
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
            if(CollUtil.isNotEmpty(zxSkStockTransItemWithdrawals)){
                //在这查询可退数量
                for (ZxSkStockTransItemWithdrawal skStockTransItemWithdrawal : zxSkStockTransItemWithdrawals) {
                    String str = skStockTransItemWithdrawal.getMainIdAndItemId();
                    ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = zxSkStockTransItemMaterialRequisitionMapper.selectByPrimaryKey(StrUtil.sub(str, str.indexOf("-") + 1, str.length()));
                    if(ObjectUtil.isNotEmpty(zxSkStockTransItemMaterialRequisition)){
                        skStockTransItemWithdrawal.setIsOutNumber(zxSkStockTransItemMaterialRequisition.getIsOutNumber());
                    }else {
                        skStockTransItemWithdrawal.setIsOutNumber(new BigDecimal("0"));
                    }
                }
            }
            zxSkStockTransferWithdrawal1.setZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawals);
        }
        //查询附件
        for (ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal1 : zxSkStockTransferWithdrawalList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockTransferWithdrawal1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockTransferWithdrawal1.setFileList(zxErpFiles);
        }

        // 得到分页信息
        PageInfo<ZxSkStockTransferWithdrawal> p = new PageInfo<>(zxSkStockTransferWithdrawalList);

        return repEntity.okList(zxSkStockTransferWithdrawalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferWithdrawalDetails(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        if (zxSkStockTransferWithdrawal == null) {
            zxSkStockTransferWithdrawal = new ZxSkStockTransferWithdrawal();
        }
        // 获取数据
        ZxSkStockTransferWithdrawal dbZxSkStockTransferWithdrawal = zxSkStockTransferWithdrawalMapper.selectByPrimaryKey(zxSkStockTransferWithdrawal.getId());
        // 数据存在
        if (dbZxSkStockTransferWithdrawal != null) {
            ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
            zxSkStockTransItemWithdrawal.setBillID(dbZxSkStockTransferWithdrawal.getId());
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
            dbZxSkStockTransferWithdrawal.setZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawals);
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockTransferWithdrawal.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockTransferWithdrawal.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockTransferWithdrawal);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferWithdrawal.setId(UuidUtil.generate());
        zxSkStockTransferWithdrawal.setCreateUserInfo(userKey, realName);

        //默认审核状态: 0:未审核
        zxSkStockTransferWithdrawal.setBillStatus("0");
        //totalAmt
        //单据金额
        BigDecimal total = new BigDecimal(0);
        //创建明细
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransferWithdrawal.getZxSkStockTransItemWithdrawalList();
        if(zxSkStockTransItemWithdrawalList != null && zxSkStockTransItemWithdrawalList.size()>0) {
            for (ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal : zxSkStockTransItemWithdrawalList) {
                //单据总额
                if(CalcUtils.compareToZero(zxSkStockTransItemWithdrawal.getOutAmt())!=0){
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemWithdrawal.getOutAmt());
                }
                //stdPrice -> outPrice
                //标准单价 -> 单价
                if(CalcUtils.compareToZero(zxSkStockTransItemWithdrawal.getStdPrice())!=0){
                    zxSkStockTransItemWithdrawal.setOutPrice(zxSkStockTransItemWithdrawal.getStdPrice());
                }else {
                    zxSkStockTransItemWithdrawal.setOutPrice(new BigDecimal(0));
                }
                zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal.getId());
                //这里不创建id,通过前端传 (改了)
                zxSkStockTransItemWithdrawal.setZxSkStockTransItemWithdrawalId((UuidUtil.generate()));
                zxSkStockTransItemWithdrawal.setCreateUserInfo(userKey, realName);
                zxSkStockTransItemWithdrawalMapper.insert(zxSkStockTransItemWithdrawal);
            }
        }
        //添加附件
        List<ZxErpFile> fileList = zxSkStockTransferWithdrawal.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockTransferWithdrawal.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSkStockTransferWithdrawal.setTotalAmt(total);
        int flag = zxSkStockTransferWithdrawalMapper.insert(zxSkStockTransferWithdrawal);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferWithdrawal);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferWithdrawal dbzxSkStockTransferWithdrawal = zxSkStockTransferWithdrawalMapper.selectByPrimaryKey(zxSkStockTransferWithdrawal.getId());
        if (dbzxSkStockTransferWithdrawal != null && StrUtil.isNotEmpty(dbzxSkStockTransferWithdrawal.getId())) {
           // 仓库(1)
           dbzxSkStockTransferWithdrawal.setOrgID(zxSkStockTransferWithdrawal.getOrgID());
           // 仓库(2)
           dbzxSkStockTransferWithdrawal.setWhOrgID(zxSkStockTransferWithdrawal.getWhOrgID());
           // 发料单位ID
           dbzxSkStockTransferWithdrawal.setOutOrgID(zxSkStockTransferWithdrawal.getOutOrgID());
           // 退料部门ID
           dbzxSkStockTransferWithdrawal.setInOrgID(zxSkStockTransferWithdrawal.getInOrgID());
           // 业务类型
           dbzxSkStockTransferWithdrawal.setBizType(zxSkStockTransferWithdrawal.getBizType());
           // 单据编号
           dbzxSkStockTransferWithdrawal.setBillNo(zxSkStockTransferWithdrawal.getBillNo());
           // 发料单位
           dbzxSkStockTransferWithdrawal.setMaterialSource(zxSkStockTransferWithdrawal.getMaterialSource());
           // 领料
           dbzxSkStockTransferWithdrawal.setPurpose(zxSkStockTransferWithdrawal.getPurpose());
           // 业务日期
           dbzxSkStockTransferWithdrawal.setBusDate(zxSkStockTransferWithdrawal.getBusDate());
           // 供货单位
           dbzxSkStockTransferWithdrawal.setOutOrgName(zxSkStockTransferWithdrawal.getOutOrgName());
           // 退料部门
           dbzxSkStockTransferWithdrawal.setInOrgName(zxSkStockTransferWithdrawal.getInOrgName());
           // 单据金额
           dbzxSkStockTransferWithdrawal.setTotalAmt(zxSkStockTransferWithdrawal.getTotalAmt());
           // 经办人(1)
           dbzxSkStockTransferWithdrawal.setOuttransactor(zxSkStockTransferWithdrawal.getOuttransactor());
           // 经办人(2)
           dbzxSkStockTransferWithdrawal.setIntransactor(zxSkStockTransferWithdrawal.getIntransactor());
           // 仓库经办人
           dbzxSkStockTransferWithdrawal.setWaretransactor(zxSkStockTransferWithdrawal.getWaretransactor());
           // 发料
           dbzxSkStockTransferWithdrawal.setBuyer(zxSkStockTransferWithdrawal.getBuyer());
           // 料账
           dbzxSkStockTransferWithdrawal.setConsignee(zxSkStockTransferWithdrawal.getConsignee());
           // 操作员
           dbzxSkStockTransferWithdrawal.setAuditor(zxSkStockTransferWithdrawal.getAuditor());
           // 凭证号
           dbzxSkStockTransferWithdrawal.setVoucherNo(zxSkStockTransferWithdrawal.getVoucherNo());
           // 合同号
           dbzxSkStockTransferWithdrawal.setContractNo(zxSkStockTransferWithdrawal.getContractNo());
           // 领料单位
           dbzxSkStockTransferWithdrawal.setInvoiceNo(zxSkStockTransferWithdrawal.getInvoiceNo());
           // 单据类型
           dbzxSkStockTransferWithdrawal.setBillType(zxSkStockTransferWithdrawal.getBillType());
           // 单据标志
           dbzxSkStockTransferWithdrawal.setBillFlag(zxSkStockTransferWithdrawal.getBillFlag());
           // 单据状态
           dbzxSkStockTransferWithdrawal.setBillStatus(zxSkStockTransferWithdrawal.getBillStatus());
           // 制单人
           dbzxSkStockTransferWithdrawal.setReporter(zxSkStockTransferWithdrawal.getReporter());
           // 扣款类型
           dbzxSkStockTransferWithdrawal.setDeductAmtType(zxSkStockTransferWithdrawal.getDeductAmtType());
           // 备注
           dbzxSkStockTransferWithdrawal.setRemark(zxSkStockTransferWithdrawal.getRemark());
           // 明细
           dbzxSkStockTransferWithdrawal.setCombProp(zxSkStockTransferWithdrawal.getCombProp());
           // 物资类别ID
           dbzxSkStockTransferWithdrawal.setResourceID(zxSkStockTransferWithdrawal.getResourceID());
           // 物资类别
           dbzxSkStockTransferWithdrawal.setResourceName(zxSkStockTransferWithdrawal.getResourceName());
           // 分部分项ID
           dbzxSkStockTransferWithdrawal.setCbsID(zxSkStockTransferWithdrawal.getCbsID());
           // 分部分项
           dbzxSkStockTransferWithdrawal.setCbsName(zxSkStockTransferWithdrawal.getCbsName());
           // 仓库(3)
           dbzxSkStockTransferWithdrawal.setWarehouseName(zxSkStockTransferWithdrawal.getWarehouseName());
           // 用于生成冲字收料单编号
           dbzxSkStockTransferWithdrawal.setInvoiceNum(zxSkStockTransferWithdrawal.getInvoiceNum());
           // 公司id
           dbzxSkStockTransferWithdrawal.setCompanyId(zxSkStockTransferWithdrawal.getCompanyId());
           // 公司名称
           dbzxSkStockTransferWithdrawal.setCompanyName(zxSkStockTransferWithdrawal.getCompanyName());
           // 共通
           dbzxSkStockTransferWithdrawal.setModifyUserInfo(userKey, realName);


            //删除在新增
            ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
            zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal.getId());
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
            if(zxSkStockTransItemWithdrawals != null && zxSkStockTransItemWithdrawals.size()>0) {
                zxSkStockTransItemWithdrawal.setModifyUserInfo(userKey, realName);
                zxSkStockTransItemWithdrawalMapper.batchDeleteUpdateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawals, zxSkStockTransItemWithdrawal);
            }
            //明细list
            List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransferWithdrawal.getZxSkStockTransItemWithdrawalList();
            BigDecimal total = new BigDecimal(0);
            if(zxSkStockTransItemWithdrawalList != null && zxSkStockTransItemWithdrawalList.size()>0) {
                for(ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal1 : zxSkStockTransItemWithdrawalList) {
                    total = CalcUtils.calcAdd(total,zxSkStockTransItemWithdrawal1.getOutAmt());
                    zxSkStockTransItemWithdrawal1.setZxSkStockTransItemWithdrawalId(UuidUtil.generate());
                    zxSkStockTransItemWithdrawal1.setBillID(zxSkStockTransferWithdrawal.getId());
                    zxSkStockTransItemWithdrawal1.setCreateUserInfo(userKey, realName);
                    zxSkStockTransItemWithdrawalMapper.insert(zxSkStockTransItemWithdrawal1);
                }
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockTransferWithdrawal.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkStockTransferWithdrawal.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockTransferWithdrawal.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            dbzxSkStockTransferWithdrawal.setTotalAmt(total);
            flag = zxSkStockTransferWithdrawalMapper.updateByPrimaryKey(dbzxSkStockTransferWithdrawal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransferWithdrawal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferWithdrawal(List<ZxSkStockTransferWithdrawal> zxSkStockTransferWithdrawalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferWithdrawalList != null && zxSkStockTransferWithdrawalList.size() > 0) {
            for (ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal : zxSkStockTransferWithdrawalList) {
                // 删除明细
                ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
                zxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal.getId());
                List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(zxSkStockTransItemWithdrawal);
                if(zxSkStockTransItemWithdrawals != null && zxSkStockTransItemWithdrawals.size()>0) {
                    zxSkStockTransItemWithdrawal.setModifyUserInfo(userKey, realName);
                    zxSkStockTransItemWithdrawalMapper.batchDeleteUpdateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawals, zxSkStockTransItemWithdrawal);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockTransferWithdrawal.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal = new ZxSkStockTransferWithdrawal();
           zxSkStockTransferWithdrawal.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferWithdrawalMapper.batchDeleteUpdateZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawalList, zxSkStockTransferWithdrawal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferWithdrawalList);
        }
    }

    //审核退库
    @Override
    public synchronized ResponseEntity checkZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        //审核数据
        ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal1 = zxSkStockTransferWithdrawalMapper.selectByPrimaryKey(zxSkStockTransferWithdrawal.getId());
        if(StrUtil.equals(zxSkStockTransferWithdrawal1.getBillStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        //获取明细
        ZxSkStockTransItemWithdrawal dbzxSkStockTransItemWithdrawal = new ZxSkStockTransItemWithdrawal();
        dbzxSkStockTransItemWithdrawal.setBillID(zxSkStockTransferWithdrawal1.getId());
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList = zxSkStockTransItemWithdrawalMapper.selectByZxSkStockTransItemWithdrawalList(dbzxSkStockTransItemWithdrawal);
        if(CollUtil.isEmpty(zxSkStockTransItemWithdrawalList)){
            return repEntity.layerMessage("no", "单据中无明细数据,请确认！");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //查询可以使用的数量
        //提供数据
        ZxSkStockTransferMaterialRequisition zxSkStockTransferMaterialRequisition = new ZxSkStockTransferMaterialRequisition();
        //根据仓库id
        zxSkStockTransferMaterialRequisition.setWhOrgID(zxSkStockTransferWithdrawal1.getWhOrgID());
        //项目id
        zxSkStockTransferMaterialRequisition.setOutOrgID(zxSkStockTransferWithdrawal1.getOutOrgID());
        //退料部门id
        zxSkStockTransferMaterialRequisition.setInOrgID(zxSkStockTransferWithdrawal1.getInOrgID());
        //分类id
        zxSkStockTransferMaterialRequisition.setResourceID(zxSkStockTransferWithdrawal1.getResourceID());
        //whOrgID,outOrgID ,inOrgID,resourceID, resId
        //(根据仓库id,项目id,退料部门id,分类id,物资编码ID List)
        Map<String,ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionMap = zxSkStockTransferMaterialRequisitionMapper.getZxSkStockTransferMaterialRequisitionResName(zxSkStockTransItemWithdrawalList,zxSkStockTransferMaterialRequisition);

        //领料单明细可退数量
        List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionIsOutNumberList = new ArrayList<>();
        //错误List (审核的数量大于库存数量)
        List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawals = new ArrayList<>();
        for (ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal : zxSkStockTransItemWithdrawalList) {
            ZxSkStockTransItemMaterialRequisition dbzxSkStockTransItemMaterialRequisition = zxSkStockTransItemMaterialRequisitionMap.get(zxSkStockTransItemWithdrawal.getMainIdAndItemId());
            if(CalcUtils.compareTo(dbzxSkStockTransItemMaterialRequisition.getIsOutNumber(),zxSkStockTransItemWithdrawal.getOutQty())<0){
                zxSkStockTransItemWithdrawals.add(zxSkStockTransItemWithdrawal);
            }else {
                String str = zxSkStockTransItemWithdrawal.getMainIdAndItemId();
                ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition = new ZxSkStockTransItemMaterialRequisition();
                zxSkStockTransItemMaterialRequisition.setId(StrUtil.sub(str,str.indexOf("-")+1,str.length()));
                zxSkStockTransItemMaterialRequisition.setIsOutNumber(CalcUtils.calcSubtract(dbzxSkStockTransItemMaterialRequisition.getIsOutNumber(),zxSkStockTransItemWithdrawal.getOutQty()));
                zxSkStockTransItemMaterialRequisition.setModifyUserInfo(userKey,realName);
                zxSkStockTransItemMaterialRequisitionIsOutNumberList.add(zxSkStockTransItemMaterialRequisition);
            }
        }
        if(CollUtil.isNotEmpty(zxSkStockTransItemWithdrawals)){
            return repEntity.layerMessage(false,zxSkStockTransItemWithdrawals, "退库单的数量不能大于领料单的数量");
        }
        //库存List
        List<ZxSkStock> zxSkStockList = new ArrayList<>();
        for (ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal : zxSkStockTransItemWithdrawalList) {
            ZxSkStock zxSkStock = new ZxSkStock();
            //公司ID
            zxSkStock.setCompanyID(zxSkStockTransferWithdrawal1.getCompanyId());
            //项目ID(收料单位Id)
            zxSkStock.setOrgID(zxSkStockTransferWithdrawal1.getOutOrgID());
            //仓库ID
            zxSkStock.setWhOrgID(zxSkStockTransferWithdrawal1.getWhOrgID());
            //物资大类ID
            zxSkStock.setResourceId(zxSkStockTransferWithdrawal1.getResourceID());
            //物资大类编码
            zxSkStock.setResourceName(zxSkStockTransferWithdrawal1.getResourceName());
            //物资Id
            zxSkStock.setResID(zxSkStockTransItemWithdrawal.getResID());
            //资源编号
            zxSkStock.setResCode(zxSkStockTransItemWithdrawal.getResCode());
            //资源名称
            zxSkStock.setResName(zxSkStockTransItemWithdrawal.getResName());
            //规格型号spec
            zxSkStock.setSpec(zxSkStockTransItemWithdrawal.getSpec());
            //单位unit
            zxSkStock.setUnit(zxSkStockTransItemWithdrawal.getResUnit());
            //退库数量
            zxSkStock.setStockQty(zxSkStockTransItemWithdrawal.getOutQty());
            //退库金额
            zxSkStock.setStockAmt(zxSkStockTransItemWithdrawal.getOutAmt());

            zxSkStockList.add(zxSkStock);
        }
        //加库存
        ResponseEntity responseEntity = ZxSkStockServiceImpl.addZxSkStock(zxSkStockList);
        if(responseEntity.isSuccess()){
            zxSkStockTransferWithdrawal1.setBillStatus("1");
            zxSkStockTransferWithdrawal1.setModifyUserInfo(userKey, realName);
            flag = zxSkStockTransferWithdrawalMapper.checkZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawal1);
            //并且对领料单中的可退库数量进行扣减
            for (ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition : zxSkStockTransItemMaterialRequisitionIsOutNumberList) {
                zxSkStockTransItemMaterialRequisitionMapper.updateZxSkStockTransItemMaterialRequisitionIsOutNumber(zxSkStockTransItemMaterialRequisition);
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

    //合同编号”+“退字第”+“年份-月份-顺序号”+“号
    @Override
    public ResponseEntity getZxSkStockTransferWithdrawalNo(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        if(StrUtil.isEmpty(zxSkStockTransferWithdrawal.getOutOrgID()) || zxSkStockTransferWithdrawal.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkStockTransferWithdrawal.getOutOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkStockTransferWithdrawal.getBusDate());
        int month = DateUtil.month(zxSkStockTransferWithdrawal.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkStockTransferWithdrawal.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkStockTransferWithdrawalMapper.getZxSkStockTransferWithdrawalCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 退字第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }





}
