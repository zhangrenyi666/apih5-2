package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSkStockFeeItemMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSkMmReqPlanItem;
import com.apih5.mybatis.pojo.ZxSkStockFeeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockFeeMapper;
import com.apih5.mybatis.pojo.ZxSkStockFee;
import com.apih5.service.ZxSkStockFeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockFeeService")
public class ZxSkStockFeeServiceImpl implements ZxSkStockFeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockFeeMapper zxSkStockFeeMapper;

    @Autowired(required = true)
    private ZxSkStockFeeItemMapper zxSkStockFeeItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSkStockFeeListByCondition(ZxSkStockFee zxSkStockFee) {
        if (zxSkStockFee == null) {
            zxSkStockFee = new ZxSkStockFee();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockFee.getPage(),zxSkStockFee.getLimit());
        // 获取数据
        List<ZxSkStockFee> zxSkStockFeeList = zxSkStockFeeMapper.selectByZxSkStockFeeList(zxSkStockFee);
        //查询明细
        for (ZxSkStockFee zxSkStockFee1 : zxSkStockFeeList) {
            ZxSkStockFeeItem zxSkStockFeeItem = new ZxSkStockFeeItem();
            zxSkStockFeeItem.setMainID(zxSkStockFee.getZxSkStockFeeId());
            List<ZxSkStockFeeItem> zxSkStockFeeItems = zxSkStockFeeItemMapper.selectByZxSkStockFeeItemList(zxSkStockFeeItem);
            zxSkStockFee1.setReviewDetailList(zxSkStockFeeItems);
        }
        //查询附件
        for (ZxSkStockFee zxSkStockFee1 : zxSkStockFeeList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkStockFee1.getZxSkStockFeeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkStockFee1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkStockFee> p = new PageInfo<>(zxSkStockFeeList);

        return repEntity.okList(zxSkStockFeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockFeeDetail(ZxSkStockFee zxSkStockFee) {
        if (zxSkStockFee == null) {
            zxSkStockFee = new ZxSkStockFee();
        }
        // 获取数据
        ZxSkStockFee dbZxSkStockFee = zxSkStockFeeMapper.selectByPrimaryKey(zxSkStockFee.getZxSkStockFeeId());
        // 数据存在
        if (dbZxSkStockFee != null) {
            ZxSkStockFeeItem zxSkStockFeeItem = new ZxSkStockFeeItem();
            zxSkStockFeeItem.setMainID(dbZxSkStockFee.getZxSkStockFeeId());
            List<ZxSkStockFeeItem> zxSkStockFeeItems = zxSkStockFeeItemMapper.selectByZxSkStockFeeItemList(zxSkStockFeeItem);
            dbZxSkStockFee.setReviewDetailList(zxSkStockFeeItems);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkStockFee.getZxSkStockFeeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkStockFee.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkStockFee);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockFee(ZxSkStockFee zxSkStockFee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockFee.setZxSkStockFeeId(UuidUtil.generate());
        zxSkStockFee.setCreateUserInfo(userKey, realName);
        //创建明细
        List<ZxSkStockFeeItem> zxSkStockFeeItemList = zxSkStockFee.getReviewDetailList();
        if(zxSkStockFeeItemList!=null&&zxSkStockFeeItemList.size()>0){
            for (ZxSkStockFeeItem zxSkStockFeeItem : zxSkStockFeeItemList) {
                zxSkStockFeeItem.setMainID(zxSkStockFee.getZxSkStockFeeId());
                zxSkStockFeeItem.setZxSkStockFeeItemId((UuidUtil.generate()));
                zxSkStockFeeItem.setCreateUserInfo(userKey, realName);
                zxSkStockFeeItemMapper.insert(zxSkStockFeeItem);
            }
        }

        //添加附件
        List<ZxErpFile> fileList = zxSkStockFee.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkStockFee.getZxSkStockFeeId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSkStockFeeMapper.insert(zxSkStockFee);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStockFee);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockFee(ZxSkStockFee zxSkStockFee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockFee dbZxSkStockFee = zxSkStockFeeMapper.selectByPrimaryKey(zxSkStockFee.getZxSkStockFeeId());
        if (dbZxSkStockFee != null && StrUtil.isNotEmpty(dbZxSkStockFee.getZxSkStockFeeId())) {
           // 附属合同编号
           dbZxSkStockFee.setContractNo(zxSkStockFee.getContractNo());
           // 附属合同名称
           dbZxSkStockFee.setContractName(zxSkStockFee.getContractName());
           // 合同类型
           dbZxSkStockFee.setContractType(zxSkStockFee.getContractType());
           // 
           dbZxSkStockFee.setIsOfficial(zxSkStockFee.getIsOfficial());
           // 
           dbZxSkStockFee.setContractViewType(zxSkStockFee.getContractViewType());
           // 
           dbZxSkStockFee.setContractSort(zxSkStockFee.getContractSort());
           // 摘要
           dbZxSkStockFee.setSubject(zxSkStockFee.getSubject());
           // 
           dbZxSkStockFee.setContent(zxSkStockFee.getContent());
           // 
           dbZxSkStockFee.setOrgID(zxSkStockFee.getOrgID());
           // 甲方ID
           dbZxSkStockFee.setFirstId(zxSkStockFee.getFirstId());
           // 合同甲方
           dbZxSkStockFee.setFirstName(zxSkStockFee.getFirstName());
           // 项目经理姓名
           dbZxSkStockFee.setFirstPrincipal(zxSkStockFee.getFirstPrincipal());
           // 甲方代表身份证
           dbZxSkStockFee.setFirstPrincipalIDCard(zxSkStockFee.getFirstPrincipalIDCard());
           // 甲方联系电话
           dbZxSkStockFee.setFirstUnitTel(zxSkStockFee.getFirstUnitTel());
           // 乙方ID
           dbZxSkStockFee.setSecondID(zxSkStockFee.getSecondID());
           // 乙方名称
           dbZxSkStockFee.setSecondName(zxSkStockFee.getSecondName());
           // 乙方代表
           dbZxSkStockFee.setSecondPrincipal(zxSkStockFee.getSecondPrincipal());
           // 乙方代表身份证
           dbZxSkStockFee.setSecondPrincipalIDCard(zxSkStockFee.getSecondPrincipalIDCard());
           // 乙方(固话)
           dbZxSkStockFee.setSecondUnitTel(zxSkStockFee.getSecondUnitTel());
           // 丙方
           dbZxSkStockFee.setThirdName(zxSkStockFee.getThirdName());
           // 收付类型
           dbZxSkStockFee.setPayType(zxSkStockFee.getPayType());
           // 
           dbZxSkStockFee.setLocationInDepr(zxSkStockFee.getLocationInDepr());
           // 所属处部ID
           dbZxSkStockFee.setLocationInDeprId(zxSkStockFee.getLocationInDeprId());
           // 登记日期
           dbZxSkStockFee.setRegisterDate(zxSkStockFee.getRegisterDate());
           // 中标日期
           dbZxSkStockFee.setBidDate(zxSkStockFee.getBidDate());
           // 签订时间
           dbZxSkStockFee.setSignatureDate(zxSkStockFee.getSignatureDate());
           // 总金额
           dbZxSkStockFee.setContractCost(zxSkStockFee.getContractCost());
           // 合同工期(天)
           dbZxSkStockFee.setCsTimeLimit(zxSkStockFee.getCsTimeLimit());
           // 合同开始时间
           dbZxSkStockFee.setPlanStartDate(zxSkStockFee.getPlanStartDate());
           // 实际开始时间
           dbZxSkStockFee.setActualStartDate(zxSkStockFee.getActualStartDate());
           // 合同结束时间
           dbZxSkStockFee.setPlanEndDate(zxSkStockFee.getPlanEndDate());
           // 实际结束时间
           dbZxSkStockFee.setActualEndDate(zxSkStockFee.getActualEndDate());
           // 
           dbZxSkStockFee.setProjectSize(zxSkStockFee.getProjectSize());
           // 监理单位
           dbZxSkStockFee.setConsultative(zxSkStockFee.getConsultative());
           // 监理单位电话
           dbZxSkStockFee.setConsultativeTel(zxSkStockFee.getConsultativeTel());
           // 业主联系电话
           dbZxSkStockFee.setOwnerLinkTel(zxSkStockFee.getOwnerLinkTel());
           // 项目经理
           dbZxSkStockFee.setProjectManager(zxSkStockFee.getProjectManager());
           // 项目经理电话
           dbZxSkStockFee.setProjectManagerTel(zxSkStockFee.getProjectManagerTel());
           // 项目总工姓名
           dbZxSkStockFee.setProjectChiefName(zxSkStockFee.getProjectChiefName());
           // 项目总工电话
           dbZxSkStockFee.setProjectChiefTel(zxSkStockFee.getProjectChiefTel());
           // 
           dbZxSkStockFee.setHasDetail(zxSkStockFee.getHasDetail());
           // 合同状态
           dbZxSkStockFee.setContractStatus(zxSkStockFee.getContractStatus());
           // 项目编号
           dbZxSkStockFee.setProjectNo(zxSkStockFee.getProjectNo());
           // 项目全称
           dbZxSkStockFee.setProjectName(zxSkStockFee.getProjectName());
           // 项目简称
           dbZxSkStockFee.setShortName(zxSkStockFee.getShortName());
           // 风险抵押金
           dbZxSkStockFee.setVentureGuaranty(zxSkStockFee.getVentureGuaranty());
           // 内部承包总价
           dbZxSkStockFee.setInnerContractSum(zxSkStockFee.getInnerContractSum());
           // 税率
           dbZxSkStockFee.setFaxRate(zxSkStockFee.getFaxRate());
           // 管理费率
           dbZxSkStockFee.setManageRate(zxSkStockFee.getManageRate());
           // 质保费率
           dbZxSkStockFee.setQuanlityFeeRate(zxSkStockFee.getQuanlityFeeRate());
           // 供货总额
           dbZxSkStockFee.setGoodsSum(zxSkStockFee.getGoodsSum());
           // 付款约定
           dbZxSkStockFee.setPaymentAssumpsit(zxSkStockFee.getPaymentAssumpsit());
           // 装运方式约定
           dbZxSkStockFee.setTransferAssumpsit(zxSkStockFee.getTransferAssumpsit());
           // 其他条款
           dbZxSkStockFee.setOtherAssumpsit(zxSkStockFee.getOtherAssumpsit());
           // 乙方性质
           dbZxSkStockFee.setAgent(zxSkStockFee.getAgent());
           // 经营指标
           dbZxSkStockFee.setManageIndex(zxSkStockFee.getManageIndex());
           // 施工单位
           dbZxSkStockFee.setImplementUnit(zxSkStockFee.getImplementUnit());
           // 设计单位
           dbZxSkStockFee.setDesignUnit(zxSkStockFee.getDesignUnit());
           // 
           dbZxSkStockFee.setTransactor(zxSkStockFee.getTransactor());
           // null
           dbZxSkStockFee.setNominated(zxSkStockFee.getNominated());
           // null
           dbZxSkStockFee.setAssignContract(zxSkStockFee.getAssignContract());
           // 乙方(手机)
           dbZxSkStockFee.setPp1(zxSkStockFee.getPp1());
           // 乙方(传真)
           dbZxSkStockFee.setPp2(zxSkStockFee.getPp2());
           // 合同类型
           dbZxSkStockFee.setPp3(zxSkStockFee.getPp3());
           // 累计结算金额
           dbZxSkStockFee.setPp4(zxSkStockFee.getPp4());
           // 累计支付金额
           dbZxSkStockFee.setPp5(zxSkStockFee.getPp5());
           // 
           dbZxSkStockFee.setPp6(zxSkStockFee.getPp6());
           // 
           dbZxSkStockFee.setPp7(zxSkStockFee.getPp7());
           // 
           dbZxSkStockFee.setPp8(zxSkStockFee.getPp8());
           // pp9
           dbZxSkStockFee.setPp9(zxSkStockFee.getPp9());
           // 资金流向
           dbZxSkStockFee.setPp10(zxSkStockFee.getPp10());
           // 
           dbZxSkStockFee.setAuditStatus(zxSkStockFee.getAuditStatus());
           // 
           dbZxSkStockFee.setEditTime(zxSkStockFee.getEditTime());
           // 
           dbZxSkStockFee.setFirstAddr(zxSkStockFee.getFirstAddr());
           // 
           dbZxSkStockFee.setDesignPhone(zxSkStockFee.getDesignPhone());
           // null
           dbZxSkStockFee.setContractMoney(zxSkStockFee.getContractMoney());
           // 
           dbZxSkStockFee.setDesignAddr(zxSkStockFee.getDesignAddr());
           // 
           dbZxSkStockFee.setConsultativeAddr(zxSkStockFee.getConsultativeAddr());
           // 业主合同功能码
           dbZxSkStockFee.setCode(zxSkStockFee.getCode());
           // 机构编码
           dbZxSkStockFee.setCode1(zxSkStockFee.getCode1());
           // 承建单位简称
           dbZxSkStockFee.setCode2(zxSkStockFee.getCode2());
           // 中标单位简称
           dbZxSkStockFee.setCode3(zxSkStockFee.getCode3());
           // 项目所在省份简称
           dbZxSkStockFee.setCode4(zxSkStockFee.getCode4());
           // 项目简称
           dbZxSkStockFee.setCode5(zxSkStockFee.getCode5());
           // 标段号
           dbZxSkStockFee.setCode6(zxSkStockFee.getCode6());
           // 合同类别
           dbZxSkStockFee.setCode7(zxSkStockFee.getCode7());
           // 合同序号
           dbZxSkStockFee.setCode8(zxSkStockFee.getCode8());
           // 合同评审ID
           dbZxSkStockFee.setFromApplyID(zxSkStockFee.getFromApplyID());
           // 业主合同功能码
           dbZxSkStockFee.setCodeP1(zxSkStockFee.getCodeP1());
           // 同一公司
           dbZxSkStockFee.setCode2T3(zxSkStockFee.getCode2T3());
           // 
           dbZxSkStockFee.setFlowBeginDate(zxSkStockFee.getFlowBeginDate());
           // 
           dbZxSkStockFee.setFlowEndDate(zxSkStockFee.getFlowEndDate());
           // 
           dbZxSkStockFee.setQualityBailRate(zxSkStockFee.getQualityBailRate());
           // 
           dbZxSkStockFee.setProdSafeBailRate(zxSkStockFee.getProdSafeBailRate());
           // 
           dbZxSkStockFee.setPayBailRate(zxSkStockFee.getPayBailRate());
           // 
           dbZxSkStockFee.setRealBeginDate(zxSkStockFee.getRealBeginDate());
           // 
           dbZxSkStockFee.setRealEndDate(zxSkStockFee.getRealEndDate());
           // 
           dbZxSkStockFee.setIsJuProj(zxSkStockFee.getIsJuProj());
           // 
           dbZxSkStockFee.setJuProjID(zxSkStockFee.getJuProjID());
           // 
           dbZxSkStockFee.setJuProjName(zxSkStockFee.getJuProjName());
           // 
           dbZxSkStockFee.setStockAudit(zxSkStockFee.getStockAudit());
           // 
           dbZxSkStockFee.setRentType(zxSkStockFee.getRentType());
           // 变更后含税合同金额（万元）
           dbZxSkStockFee.setAlterContractSum(zxSkStockFee.getAlterContractSum());
           // 结算情况
           dbZxSkStockFee.setSettleType(zxSkStockFee.getSettleType());
           // 
           dbZxSkStockFee.setMaterialSource(zxSkStockFee.getMaterialSource());
           // 
           dbZxSkStockFee.setContrStatus(zxSkStockFee.getContrStatus());
           // 
           dbZxSkStockFee.setSubArea(zxSkStockFee.getSubArea());
           // 税率(%)
           dbZxSkStockFee.setTaxRate(zxSkStockFee.getTaxRate());
           // 是否抵扣
           dbZxSkStockFee.setIsDeduct(zxSkStockFee.getIsDeduct());
           // 入账金额
           dbZxSkStockFee.setContractCostNoTax(zxSkStockFee.getContractCostNoTax());
           // 合同税额
           dbZxSkStockFee.setContractCostTax(zxSkStockFee.getContractCostTax());
           // 变更后不含税合同金额(万元)
           dbZxSkStockFee.setAlterContractSumNoTax(zxSkStockFee.getAlterContractSumNoTax());
           // 变更后合同税额(万元)
           dbZxSkStockFee.setAlterContractSumTax(zxSkStockFee.getAlterContractSumTax());
           // 合同录入类型
           dbZxSkStockFee.setAudit(zxSkStockFee.getAudit());
           // 备注
           dbZxSkStockFee.setRemarks(zxSkStockFee.getRemarks());
           // 排序
           dbZxSkStockFee.setSort(zxSkStockFee.getSort());
           // 共通
           dbZxSkStockFee.setModifyUserInfo(userKey, realName);
           flag = zxSkStockFeeMapper.updateByPrimaryKey(dbZxSkStockFee);

            //删除在新增
            ZxSkStockFeeItem zxSkStockFeeItem = new ZxSkStockFeeItem();
            zxSkStockFeeItem.setMainID(zxSkStockFee.getZxSkStockFeeId());
            List<ZxSkStockFeeItem> zxSkStockFeeItems = zxSkStockFeeItemMapper.selectByZxSkStockFeeItemList(zxSkStockFeeItem);
            if(zxSkStockFeeItems != null && zxSkStockFeeItems.size()>0) {
                zxSkStockFeeItem.setModifyUserInfo(userKey, realName);
                zxSkStockFeeItemMapper.batchDeleteUpdateZxSkStockFeeItem(zxSkStockFeeItems, zxSkStockFeeItem);
            }
            //明细list
            List<ZxSkStockFeeItem> zxSkStockFeeItemList = zxSkStockFee.getReviewDetailList();
            if(zxSkStockFeeItemList != null && zxSkStockFeeItemList.size()>0) {
                for(ZxSkStockFeeItem zxSkStockFeeItem1 : zxSkStockFeeItemList) {
                    zxSkStockFeeItem1.setZxSkStockFeeItemId(UuidUtil.generate());
                    zxSkStockFeeItem1.setMainID(zxSkStockFee.getZxSkStockFeeId());
                    zxSkStockFeeItem1.setCreateUserInfo(userKey, realName);
                    zxSkStockFeeItemMapper.insert(zxSkStockFeeItem1);
                }
            }
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkStockFee.getZxSkStockFeeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkStockFee.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkStockFee.getZxSkStockFeeId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkStockFee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockFee(List<ZxSkStockFee> zxSkStockFeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockFeeList != null && zxSkStockFeeList.size() > 0) {
            for (ZxSkStockFee zxSkStockFee : zxSkStockFeeList) {
                // 删除明细
                ZxSkStockFeeItem zxSkStockFeeItem = new ZxSkStockFeeItem();
                zxSkStockFeeItem.setMainID(zxSkStockFee.getZxSkStockFeeId());
                List<ZxSkStockFeeItem> zxSkStockFeeItems = zxSkStockFeeItemMapper.selectByZxSkStockFeeItemList(zxSkStockFeeItem);
                if(zxSkStockFeeItems != null && zxSkStockFeeItems.size()>0) {
                    zxSkStockFeeItem.setModifyUserInfo(userKey, realName);
                    zxSkStockFeeItemMapper.batchDeleteUpdateZxSkStockFeeItem(zxSkStockFeeItems, zxSkStockFeeItem);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkStockFee.getZxSkStockFeeId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkStockFee zxSkStockFee = new ZxSkStockFee();
           zxSkStockFee.setModifyUserInfo(userKey, realName);
           flag = zxSkStockFeeMapper.batchDeleteUpdateZxSkStockFee(zxSkStockFeeList, zxSkStockFee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkStockFeeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓



}
