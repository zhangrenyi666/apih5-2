package com.apih5.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishAgreementMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementListMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishAgreement;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtSuppliesContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContractService")
public class ZxCtSuppliesContractServiceImpl implements ZxCtSuppliesContractService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementListMapper zxCtSuppliesLeaseSettlementListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementListMapper zxCtSuppliesShopSettlementListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishAgreementMapper zxCtSuppliesContrReplenishAgreementMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContractListByCondition(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContract.setComID("");
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtSuppliesContract.setComID(zxCtSuppliesContract.getOrgID());
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
        }        
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
        // 获取数据
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(zxCtSuppliesContract);
        zxCtSuppliesContractList.parallelStream().forEach((contract)->{
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(contract.getZxCtSuppliesContractId());
        	contract.setSuppliesContractFileList(zxErpFileMapper.selectByZxErpFileList(file));
        	contract.setApih5FlowStatus("2");
        });
        // 得到分页信息
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContractDetail(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        // 获取数据
        ZxCtSuppliesContract dbZxCtSuppliesContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContract.getZxCtSuppliesContractId());
        // 数据存在
        if (dbZxCtSuppliesContract != null) {
            return repEntity.ok(dbZxCtSuppliesContract);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContract(ZxCtSuppliesContract zxCtSuppliesContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContract.setZxCtSuppliesContractId(UuidUtil.generate());
        zxCtSuppliesContract.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContractMapper.insert(zxCtSuppliesContract);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContract);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContract(ZxCtSuppliesContract zxCtSuppliesContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContract dbZxCtSuppliesContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContract.getZxCtSuppliesContractId());
        if (dbZxCtSuppliesContract != null && StrUtil.isNotEmpty(dbZxCtSuppliesContract.getZxCtSuppliesContractId())) {
           // 装运方式约定
           dbZxCtSuppliesContract.setTransferAssumpsit(zxCtSuppliesContract.getTransferAssumpsit());
           // 中标日期
           dbZxCtSuppliesContract.setBidDate(zxCtSuppliesContract.getBidDate());
           // 中标单位简称
           dbZxCtSuppliesContract.setCode3(zxCtSuppliesContract.getCode3());
           // 质保费率
           dbZxCtSuppliesContract.setQuanlityFeeRate(zxCtSuppliesContract.getQuanlityFeeRate());
           // 摘要
           dbZxCtSuppliesContract.setSubject(zxCtSuppliesContract.getSubject());
           // 乙方性质
           dbZxCtSuppliesContract.setAgent(zxCtSuppliesContract.getAgent());
           // 乙方名称
           dbZxCtSuppliesContract.setSecondName(zxCtSuppliesContract.getSecondName());
           // 乙方代表身份证
           dbZxCtSuppliesContract.setSecondPrincipalIDCard(zxCtSuppliesContract.getSecondPrincipalIDCard());
           // 乙方代表
           dbZxCtSuppliesContract.setSecondPrincipal(zxCtSuppliesContract.getSecondPrincipal());
           // 乙方ID
           dbZxCtSuppliesContract.setSecondID(zxCtSuppliesContract.getSecondID());
           // 乙方(手机)
           dbZxCtSuppliesContract.setPp1(zxCtSuppliesContract.getPp1());
           // 乙方(固话)
           dbZxCtSuppliesContract.setSecondUnitTel(zxCtSuppliesContract.getSecondUnitTel());
           // 乙方(传真)
           dbZxCtSuppliesContract.setPp2(zxCtSuppliesContract.getPp2());
           // 业主合同功能码
           dbZxCtSuppliesContract.setCodeP1(zxCtSuppliesContract.getCodeP1());
           // 协作单位名称
           dbZxCtSuppliesContract.setThirdName(zxCtSuppliesContract.getThirdName());
           // 项目总工姓名
           dbZxCtSuppliesContract.setProjectChiefName(zxCtSuppliesContract.getProjectChiefName());
           // 项目总工电话
           dbZxCtSuppliesContract.setProjectChiefTel(zxCtSuppliesContract.getProjectChiefTel());
           // 项目所在省份简称
           dbZxCtSuppliesContract.setCode4(zxCtSuppliesContract.getCode4());
           // 项目全称
           dbZxCtSuppliesContract.setProjectName(zxCtSuppliesContract.getProjectName());
           // 项目经理电话
           dbZxCtSuppliesContract.setProjectManagerTel(zxCtSuppliesContract.getProjectManagerTel());
           // 项目经理
           dbZxCtSuppliesContract.setProjectManager(zxCtSuppliesContract.getProjectManager());
           // 项目经理
           dbZxCtSuppliesContract.setFirstPrincipal(zxCtSuppliesContract.getFirstPrincipal());
           // 项目简称
           dbZxCtSuppliesContract.setCode5(zxCtSuppliesContract.getCode5());
           // 项目简称
           dbZxCtSuppliesContract.setShortName(zxCtSuppliesContract.getShortName());
           // 项目编号
           dbZxCtSuppliesContract.setProjectNo(zxCtSuppliesContract.getProjectNo());
           // 物资来源
           dbZxCtSuppliesContract.setMaterialSource(zxCtSuppliesContract.getMaterialSource());
           // 物资结算引用数
           dbZxCtSuppliesContract.setStockSettleUseCount(zxCtSuppliesContract.getStockSettleUseCount());
           // 物资合同审核状态
           dbZxCtSuppliesContract.setStockAudit(zxCtSuppliesContract.getStockAudit());
           // code2T3
           dbZxCtSuppliesContract.setCode2T3(zxCtSuppliesContract.getCode2T3());
           // 所属项目ID
           dbZxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
           // 所属项目
           dbZxCtSuppliesContract.setOrgName(zxCtSuppliesContract.getOrgName());
           // 所属处部ID
           dbZxCtSuppliesContract.setLocationInDeprId(zxCtSuppliesContract.getLocationInDeprId());
           // 税率(%)
           dbZxCtSuppliesContract.setTaxRate(zxCtSuppliesContract.getTaxRate());
           // 税率
           dbZxCtSuppliesContract.setFaxRate(zxCtSuppliesContract.getFaxRate());
           // 收付类型
           dbZxCtSuppliesContract.setPayType(zxCtSuppliesContract.getPayType());
           // 是否抵扣
           dbZxCtSuppliesContract.setIsDeduct(zxCtSuppliesContract.getIsDeduct());
           // 实际开始时间
           dbZxCtSuppliesContract.setActualStartDate(zxCtSuppliesContract.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContract.setActualEndDate(zxCtSuppliesContract.getActualEndDate());
           // 设备及其他结算引用数
           dbZxCtSuppliesContract.setEquipSettleUseCount(zxCtSuppliesContract.getEquipSettleUseCount());
           // 签订时间
           dbZxCtSuppliesContract.setSignatureDate(zxCtSuppliesContract.getSignatureDate());
           // 其他条款
           dbZxCtSuppliesContract.setOtherAssumpsit(zxCtSuppliesContract.getOtherAssumpsit());
           // 内部承包总价
           dbZxCtSuppliesContract.setInnerContractSum(zxCtSuppliesContract.getInnerContractSum());
           // 累计支付金额
           dbZxCtSuppliesContract.setPp5(zxCtSuppliesContract.getPp5());
           // 累计结算金额
           dbZxCtSuppliesContract.setPp4(zxCtSuppliesContract.getPp4());
           // 经营指标
           dbZxCtSuppliesContract.setManageIndex(zxCtSuppliesContract.getManageIndex());
           // 监理单位
           dbZxCtSuppliesContract.setConsultative(zxCtSuppliesContract.getConsultative());
           // 甲方联系电话
           dbZxCtSuppliesContract.setFirstUnitTel(zxCtSuppliesContract.getFirstUnitTel());
           // 甲方代表身份证
           dbZxCtSuppliesContract.setFirstPrincipalIDCard(zxCtSuppliesContract.getFirstPrincipalIDCard());
           // 甲方ID
           dbZxCtSuppliesContract.setFirstId(zxCtSuppliesContract.getFirstId());
           // 甲方
           dbZxCtSuppliesContract.setFirstIdFormula(zxCtSuppliesContract.getFirstIdFormula());
           // 机构编码
           dbZxCtSuppliesContract.setCode1(zxCtSuppliesContract.getCode1());
           // 合同状态
           dbZxCtSuppliesContract.setContractStatus(zxCtSuppliesContract.getContractStatus());
           // 合同乙方
           dbZxCtSuppliesContract.setSecondIDFormula(zxCtSuppliesContract.getSecondIDFormula());
           // 合同序号
           dbZxCtSuppliesContract.setCode8(zxCtSuppliesContract.getCode8());
           // 合同税额(万元)
           dbZxCtSuppliesContract.setContractCostTax(zxCtSuppliesContract.getContractCostTax());
           // 合同评审ID
           dbZxCtSuppliesContract.setFromApplyID(zxCtSuppliesContract.getFromApplyID());
           // 合同内容
           dbZxCtSuppliesContract.setContent(zxCtSuppliesContract.getContent());
           // 合同名称
           dbZxCtSuppliesContract.setContractName(zxCtSuppliesContract.getContractName());
           // 合同录入类型
           dbZxCtSuppliesContract.setAudit(zxCtSuppliesContract.getAudit());
           // 合同类型
           dbZxCtSuppliesContract.setContractType(zxCtSuppliesContract.getContractType());
           // 合同类型
           dbZxCtSuppliesContract.setPp3(zxCtSuppliesContract.getPp3());
           // 合同类别
           dbZxCtSuppliesContract.setCode7(zxCtSuppliesContract.getCode7());
           // 合同开始日期
           dbZxCtSuppliesContract.setPlanStartDate(zxCtSuppliesContract.getPlanStartDate());
           // 合同结束日期
           dbZxCtSuppliesContract.setPlanEndDate(zxCtSuppliesContract.getPlanEndDate());
           // 合同甲方
           dbZxCtSuppliesContract.setFirstName(zxCtSuppliesContract.getFirstName());
           // 合同工期(天)
           dbZxCtSuppliesContract.setCsTimeLimit(zxCtSuppliesContract.getCsTimeLimit());
           // 合同编码
           dbZxCtSuppliesContract.setCode(zxCtSuppliesContract.getCode());
           // 合同编号
           dbZxCtSuppliesContract.setContractNo(zxCtSuppliesContract.getContractNo());
           // 含税合同总价(万元)
           dbZxCtSuppliesContract.setContractCost(zxCtSuppliesContract.getContractCost());
           // 管理费率
           dbZxCtSuppliesContract.setManageRate(zxCtSuppliesContract.getManageRate());
           // 供货总额
           dbZxCtSuppliesContract.setGoodsSum(zxCtSuppliesContract.getGoodsSum());
           // 工程结算引用数
           dbZxCtSuppliesContract.setProjectSettleUseCount(zxCtSuppliesContract.getProjectSettleUseCount());
           // 付款约定
           dbZxCtSuppliesContract.setPaymentAssumpsit(zxCtSuppliesContract.getPaymentAssumpsit());
           // 风险抵押金
           dbZxCtSuppliesContract.setVentureGuaranty(zxCtSuppliesContract.getVentureGuaranty());
           // 登记日期
           dbZxCtSuppliesContract.setRegisterDate(zxCtSuppliesContract.getRegisterDate());
           // 承建单位简称
           dbZxCtSuppliesContract.setCode2(zxCtSuppliesContract.getCode2());
           // 不含税合同总价(万元)
           dbZxCtSuppliesContract.setContractCostNoTax(zxCtSuppliesContract.getContractCostNoTax());
           // 标段号
           dbZxCtSuppliesContract.setCode6(zxCtSuppliesContract.getCode6());
           // 变更审核次数
           dbZxCtSuppliesContract.setAuditContrAlterCount(zxCtSuppliesContract.getAuditContrAlterCount());
           // 变更后合同税额(万元)
           dbZxCtSuppliesContract.setAlterContractSumTax(zxCtSuppliesContract.getAlterContractSumTax());
           // 变更后含税合同金额(万元)
           dbZxCtSuppliesContract.setAlterContractSum(zxCtSuppliesContract.getAlterContractSum());
           // 变更后不含税合同金额(万元)
           dbZxCtSuppliesContract.setAlterContractSumNoTax(zxCtSuppliesContract.getAlterContractSumNoTax());
           // pp9
           dbZxCtSuppliesContract.setPp9(zxCtSuppliesContract.getPp9());
           // pp10
           dbZxCtSuppliesContract.setPp10(zxCtSuppliesContract.getPp10());
           // comID
           dbZxCtSuppliesContract.setComID(zxCtSuppliesContract.getComID());
           // 结算情况
           dbZxCtSuppliesContract.setSettleType(zxCtSuppliesContract.getSettleType());
           // 备注
           dbZxCtSuppliesContract.setRemarks(zxCtSuppliesContract.getRemarks());
           // 排序
           dbZxCtSuppliesContract.setSort(zxCtSuppliesContract.getSort());
           // 共通
           dbZxCtSuppliesContract.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractMapper.updateByPrimaryKey(dbZxCtSuppliesContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContract);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContract(List<ZxCtSuppliesContract> zxCtSuppliesContractList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContractList != null && zxCtSuppliesContractList.size() > 0) {
           ZxCtSuppliesContract zxCtSuppliesContract = new ZxCtSuppliesContract();
           zxCtSuppliesContract.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractMapper.batchDeleteUpdateZxCtSuppliesContract(zxCtSuppliesContractList, zxCtSuppliesContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContractList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity getZxCtSuppliesContractListByOrgId(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);    
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
        // 获取数据
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractListAddSettlement(zxCtSuppliesContract);
        for(ZxCtSuppliesContract contract : zxCtSuppliesContractList) {
        	if(StrUtil.equals(zxCtSuppliesContract.getCode7(), "WZ")) {
        		ZxCtSuppliesShopSettlementList settlement = new ZxCtSuppliesShopSettlementList();
        		settlement.setContractID(contract.getZxCtSuppliesContractId());
        		List<ZxCtSuppliesShopSettlementList> settlementList = zxCtSuppliesShopSettlementListMapper.selectByZxCtSuppliesShopSettlementListList(settlement);
        		if(settlementList.size()>0) {
        			if(!StrUtil.equals(settlementList.get(0).getApih5FlowStatus(), "2")) {
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
            			contract.setIsUsed("0");
        			}else {
        				contract.setIsUsed("1");
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
        			}
        		}else {
        			contract.setInitSerialNumber(0);	
        		}
        	}else if(StrUtil.equals(zxCtSuppliesContract.getCode7(), "WL")){
        		ZxCtSuppliesLeaseSettlementList settlement = new ZxCtSuppliesLeaseSettlementList();
        		settlement.setContractID(contract.getZxCtSuppliesContractId());
        		List<ZxCtSuppliesLeaseSettlementList> settlementList = zxCtSuppliesLeaseSettlementListMapper.selectByZxCtSuppliesLeaseSettlementListList(settlement);
        		if(settlementList.size()>0) {
        			if(!StrUtil.equals(settlementList.get(0).getApih5FlowStatus(), "2")) {
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
            			contract.setIsUsed("0");
        			}else {
        				contract.setIsUsed("1");
            			contract.setInitSerialNumber((Integer.parseInt(settlementList.get(0).getInitSerialNumber())));
        			}
        		}else {
        			contract.setInitSerialNumber(0);
        		}        		
        	}
        }
        // 得到分页信息
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
    }

	@Override
	public ResponseEntity updateZxCtSuppliesContractByContId(ZxCtSuppliesContract zxCtSuppliesContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContract dbZxCtSuppliesContract = zxCtSuppliesContractMapper.selectByPrimaryKey(zxCtSuppliesContract.getZxCtSuppliesContractId());
        if (dbZxCtSuppliesContract != null && StrUtil.isNotEmpty(dbZxCtSuppliesContract.getZxCtSuppliesContractId())) {
           // 装运方式约定
           dbZxCtSuppliesContract.setTransferAssumpsit(zxCtSuppliesContract.getTransferAssumpsit());
           // 中标日期
           dbZxCtSuppliesContract.setBidDate(zxCtSuppliesContract.getBidDate());
           // 中标单位简称
           dbZxCtSuppliesContract.setCode3(zxCtSuppliesContract.getCode3());
           // 质保费率
           dbZxCtSuppliesContract.setQuanlityFeeRate(zxCtSuppliesContract.getQuanlityFeeRate());
           // 摘要
           dbZxCtSuppliesContract.setSubject(zxCtSuppliesContract.getSubject());
           // 乙方性质
           dbZxCtSuppliesContract.setAgent(zxCtSuppliesContract.getAgent());
           // 乙方名称
           dbZxCtSuppliesContract.setSecondName(zxCtSuppliesContract.getSecondName());
           // 乙方代表身份证
           dbZxCtSuppliesContract.setSecondPrincipalIDCard(zxCtSuppliesContract.getSecondPrincipalIDCard());
           // 乙方代表
           dbZxCtSuppliesContract.setSecondPrincipal(zxCtSuppliesContract.getSecondPrincipal());
           // 乙方ID
//           dbZxCtSuppliesContract.setSecondID(zxCtSuppliesContract.getSecondID());
           // 乙方(手机)
           dbZxCtSuppliesContract.setPp1(zxCtSuppliesContract.getPp1());
           // 乙方(固话)
           dbZxCtSuppliesContract.setSecondUnitTel(zxCtSuppliesContract.getSecondUnitTel());
           // 乙方(传真)
           dbZxCtSuppliesContract.setPp2(zxCtSuppliesContract.getPp2());
           // 业主合同功能码
           dbZxCtSuppliesContract.setCodeP1(zxCtSuppliesContract.getCodeP1());
           // 协作单位名称
           dbZxCtSuppliesContract.setThirdName(zxCtSuppliesContract.getThirdName());
           // 项目总工姓名
           dbZxCtSuppliesContract.setProjectChiefName(zxCtSuppliesContract.getProjectChiefName());
           // 项目总工电话
           dbZxCtSuppliesContract.setProjectChiefTel(zxCtSuppliesContract.getProjectChiefTel());
           // 项目所在省份简称
           dbZxCtSuppliesContract.setCode4(zxCtSuppliesContract.getCode4());
           // 项目全称
           dbZxCtSuppliesContract.setProjectName(zxCtSuppliesContract.getProjectName());
           // 项目经理电话
           dbZxCtSuppliesContract.setProjectManagerTel(zxCtSuppliesContract.getProjectManagerTel());
           // 项目经理
           dbZxCtSuppliesContract.setProjectManager(zxCtSuppliesContract.getProjectManager());
           // 项目经理
           dbZxCtSuppliesContract.setFirstPrincipal(zxCtSuppliesContract.getFirstPrincipal());
           // 项目简称
           dbZxCtSuppliesContract.setCode5(zxCtSuppliesContract.getCode5());
           // 项目简称
           dbZxCtSuppliesContract.setShortName(zxCtSuppliesContract.getShortName());
           // 项目编号
           dbZxCtSuppliesContract.setProjectNo(zxCtSuppliesContract.getProjectNo());
           // 物资来源
           dbZxCtSuppliesContract.setMaterialSource(zxCtSuppliesContract.getMaterialSource());
           // 物资结算引用数
           dbZxCtSuppliesContract.setStockSettleUseCount(zxCtSuppliesContract.getStockSettleUseCount());
           // 物资合同审核状态
           dbZxCtSuppliesContract.setStockAudit(zxCtSuppliesContract.getStockAudit());
           // code2T3
           dbZxCtSuppliesContract.setCode2T3(zxCtSuppliesContract.getCode2T3());
           // 所属项目ID
           dbZxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
           // 所属项目
           dbZxCtSuppliesContract.setOrgName(zxCtSuppliesContract.getOrgName());
           // 所属处部ID
           dbZxCtSuppliesContract.setLocationInDeprId(zxCtSuppliesContract.getLocationInDeprId());
           // 税率(%)
           dbZxCtSuppliesContract.setTaxRate(zxCtSuppliesContract.getTaxRate());
           // 税率
           dbZxCtSuppliesContract.setFaxRate(zxCtSuppliesContract.getFaxRate());
           // 收付类型
           dbZxCtSuppliesContract.setPayType(zxCtSuppliesContract.getPayType());
           // 是否抵扣
           dbZxCtSuppliesContract.setIsDeduct(zxCtSuppliesContract.getIsDeduct());
           // 实际开始时间
           dbZxCtSuppliesContract.setActualStartDate(zxCtSuppliesContract.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContract.setActualEndDate(zxCtSuppliesContract.getActualEndDate());
           // 设备及其他结算引用数
           dbZxCtSuppliesContract.setEquipSettleUseCount(zxCtSuppliesContract.getEquipSettleUseCount());
           // 签订时间
           dbZxCtSuppliesContract.setSignatureDate(zxCtSuppliesContract.getSignatureDate());
           // 其他条款
           dbZxCtSuppliesContract.setOtherAssumpsit(zxCtSuppliesContract.getOtherAssumpsit());
           // 内部承包总价
           dbZxCtSuppliesContract.setInnerContractSum(zxCtSuppliesContract.getInnerContractSum());
           // 累计支付金额
           dbZxCtSuppliesContract.setPp5(zxCtSuppliesContract.getPp5());
           // 累计结算金额
           dbZxCtSuppliesContract.setPp4(zxCtSuppliesContract.getPp4());
           // 经营指标
           dbZxCtSuppliesContract.setManageIndex(zxCtSuppliesContract.getManageIndex());
           // 监理单位
           dbZxCtSuppliesContract.setConsultative(zxCtSuppliesContract.getConsultative());
           // 甲方联系电话
           dbZxCtSuppliesContract.setFirstUnitTel(zxCtSuppliesContract.getFirstUnitTel());
           // 甲方代表身份证
           dbZxCtSuppliesContract.setFirstPrincipalIDCard(zxCtSuppliesContract.getFirstPrincipalIDCard());
           // 甲方ID
//           dbZxCtSuppliesContract.setFirstId(zxCtSuppliesContract.getFirstId());
           // 甲方
           dbZxCtSuppliesContract.setFirstIdFormula(zxCtSuppliesContract.getFirstIdFormula());
           // 机构编码
           dbZxCtSuppliesContract.setCode1(zxCtSuppliesContract.getCode1());
           // 合同状态
           dbZxCtSuppliesContract.setContractStatus(zxCtSuppliesContract.getContractStatus());
           // 合同乙方
           dbZxCtSuppliesContract.setSecondIDFormula(zxCtSuppliesContract.getSecondIDFormula());
           // 合同序号
           dbZxCtSuppliesContract.setCode8(zxCtSuppliesContract.getCode8());
           // 合同税额(万元)
           dbZxCtSuppliesContract.setContractCostTax(zxCtSuppliesContract.getContractCostTax());
           // 合同评审ID
//           dbZxCtSuppliesContract.setFromApplyID(zxCtSuppliesContract.getFromApplyID());
           // 合同内容
           dbZxCtSuppliesContract.setContent(zxCtSuppliesContract.getContent());
           // 合同名称
           dbZxCtSuppliesContract.setContractName(zxCtSuppliesContract.getContractName());
           // 合同录入类型
           dbZxCtSuppliesContract.setAudit(zxCtSuppliesContract.getAudit());
           // 合同类型
           dbZxCtSuppliesContract.setContractType(zxCtSuppliesContract.getContractType());
           // 合同类型
           dbZxCtSuppliesContract.setPp3(zxCtSuppliesContract.getPp3());
           // 合同类别
           dbZxCtSuppliesContract.setCode7(zxCtSuppliesContract.getCode7());
           // 合同开始日期
           dbZxCtSuppliesContract.setPlanStartDate(zxCtSuppliesContract.getPlanStartDate());
           // 合同结束日期
           dbZxCtSuppliesContract.setPlanEndDate(zxCtSuppliesContract.getPlanEndDate());
           // 合同甲方
           dbZxCtSuppliesContract.setFirstName(zxCtSuppliesContract.getFirstName());
           // 合同工期(天)
           dbZxCtSuppliesContract.setCsTimeLimit(zxCtSuppliesContract.getCsTimeLimit());
           // 合同编码
           dbZxCtSuppliesContract.setCode(zxCtSuppliesContract.getCode());
           // 合同编号
           dbZxCtSuppliesContract.setContractNo(zxCtSuppliesContract.getContractNo());
           // 含税合同总价(万元)
           dbZxCtSuppliesContract.setContractCost(zxCtSuppliesContract.getContractCost());
           // 管理费率
           dbZxCtSuppliesContract.setManageRate(zxCtSuppliesContract.getManageRate());
           // 供货总额
           dbZxCtSuppliesContract.setGoodsSum(zxCtSuppliesContract.getGoodsSum());
           // 工程结算引用数
           dbZxCtSuppliesContract.setProjectSettleUseCount(zxCtSuppliesContract.getProjectSettleUseCount());
           // 付款约定
           dbZxCtSuppliesContract.setPaymentAssumpsit(zxCtSuppliesContract.getPaymentAssumpsit());
           // 风险抵押金
           dbZxCtSuppliesContract.setVentureGuaranty(zxCtSuppliesContract.getVentureGuaranty());
           // 登记日期
           dbZxCtSuppliesContract.setRegisterDate(zxCtSuppliesContract.getRegisterDate());
           // 承建单位简称
           dbZxCtSuppliesContract.setCode2(zxCtSuppliesContract.getCode2());
           // 不含税合同总价(万元)
           dbZxCtSuppliesContract.setContractCostNoTax(zxCtSuppliesContract.getContractCostNoTax());
           // 标段号
           dbZxCtSuppliesContract.setCode6(zxCtSuppliesContract.getCode6());
           // 变更审核次数
           dbZxCtSuppliesContract.setAuditContrAlterCount(zxCtSuppliesContract.getAuditContrAlterCount());
           // 变更后合同税额(万元)
           dbZxCtSuppliesContract.setAlterContractSumTax(zxCtSuppliesContract.getAlterContractSumTax());
           // 变更后含税合同金额(万元)
           dbZxCtSuppliesContract.setAlterContractSum(zxCtSuppliesContract.getAlterContractSum());
           // 变更后不含税合同金额(万元)
           dbZxCtSuppliesContract.setAlterContractSumNoTax(zxCtSuppliesContract.getAlterContractSumNoTax());
           // pp9
           dbZxCtSuppliesContract.setPp9(zxCtSuppliesContract.getPp9());
           // pp10
           dbZxCtSuppliesContract.setPp10(zxCtSuppliesContract.getPp10());
           // comID
           dbZxCtSuppliesContract.setComID(zxCtSuppliesContract.getComID());
           // 结算情况
           dbZxCtSuppliesContract.setSettleType(zxCtSuppliesContract.getSettleType());
           // 备注
           dbZxCtSuppliesContract.setRemarks(zxCtSuppliesContract.getRemarks());
           // 排序
           dbZxCtSuppliesContract.setSort(zxCtSuppliesContract.getSort());
           // 共通
           dbZxCtSuppliesContract.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractMapper.updateByPrimaryKey(dbZxCtSuppliesContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
    	    //新增外层补充协议
    	    if(zxCtSuppliesContract.getSuppliesContractFileList() != null && zxCtSuppliesContract.getSuppliesContractFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(zxCtSuppliesContract.getZxCtSuppliesContractId());
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, realName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesContract.getSuppliesContractFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtSuppliesContract.getZxCtSuppliesContractId());                
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);  
         	   }
            }        	
            return repEntity.ok("sys.data.update",zxCtSuppliesContract);
        }
	}

	@Override
	public ResponseEntity getZxCtSuppliesContractListAddAgreementNum(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContract.setComID("");
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtSuppliesContract.setComID(zxCtSuppliesContract.getOrgID());
        	zxCtSuppliesContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtSuppliesContract.setOrgID(zxCtSuppliesContract.getOrgID());
        }        
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
        // 获取数据
        zxCtSuppliesContract.setAgreementFlag("1");//添加是否补充协议字段，来过滤已最终结算的合同
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(zxCtSuppliesContract);
        zxCtSuppliesContractList.parallelStream().forEach((contract)->{
        	ZxCtSuppliesContrReplenishAgreement agreement = new ZxCtSuppliesContrReplenishAgreement();
        	agreement.setPp6(contract.getZxCtSuppliesContractId());
        	String code = zxCtSuppliesContrReplenishAgreementMapper.getZxCtReplenishAgreementNoByCode(agreement);
    		if(StrUtil.isNotEmpty(code)) {
    			DecimalFormat decimalFormat=new DecimalFormat("00");		
    			String headCode = code.substring(0, code.lastIndexOf("补")+1);
    			 String[] numArr = code.split("-补", code.lastIndexOf("-"));
    		        String num =decimalFormat.format(Integer.parseInt(numArr[numArr.length-1])+1);
    		        code =  headCode + num;
    		}else {
    			code = contract.getContractNo()+"-补01";
    		}  
    		contract.setAgreementNum(code);
        });
        // 得到分页信息
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtSuppliesContractListByFirstId(ZxCtSuppliesContract zxCtSuppliesContract) {
        if (zxCtSuppliesContract == null) {
            zxCtSuppliesContract = new ZxCtSuppliesContract();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContract.getPage(),zxCtSuppliesContract.getLimit());
//        //是否周转材；0：是；1：不是
//        if(StrUtil.equals(zxCtSuppliesContract.getIsTurnover(), "0")) {
//        	
//        }else if(StrUtil.equals(zxCtSuppliesContract.getIsTurnover(), "1")){
//        	
//        }
        if(StrUtil.isEmpty(zxCtSuppliesContract.getFirstId())) {
        	return repEntity.layerMessage("NO", "请选择收料单位");
        }
        if(StrUtil.isEmpty(zxCtSuppliesContract.getSecondID())) {
        	return repEntity.layerMessage("NO", "请选择供货单位");
        }        
        // 获取数据
        List<ZxCtSuppliesContract> zxCtSuppliesContractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractListByFirstId(zxCtSuppliesContract);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContract> p = new PageInfo<>(zxCtSuppliesContractList);

        return repEntity.okList(zxCtSuppliesContractList, p.getTotal());
	}
}
