package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxCtProduceAmtCalMapper;
import com.apih5.mybatis.dao.ZxCtWorkBookMapper;
import com.apih5.mybatis.dao.ZxSaProjectFinishMapper;
import com.apih5.mybatis.pojo.ZxCtContrDetail;
import com.apih5.mybatis.pojo.ZxCtContrDetailAttr;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxCtProduceAmtCal;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.mybatis.pojo.ZxCtWorks;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSaProjectFinish;
import com.apih5.mybatis.pojo.ZxSaProjectFinishItem;
import com.apih5.service.ZxCtContrDetailService;
import com.apih5.service.ZxCtContractService;
import com.apih5.service.ZxSaProjectFinishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("zxCtContractService")
public class ZxCtContractServiceImpl implements ZxCtContractService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;
    
    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;
    
    @Autowired(required = true)
    private ZxCtContrDetailAttrServiceImpl zxCtContrDetailAttrServiceImpl;

    @Autowired(required = true)
    private ZxCtWorkBookServiceImpl zxCtWorkBookServiceImpl;
    
    @Autowired(required = true)
    private ZxCtWorkBookMapper zxCtWorkBookMapper;
    
    @Autowired(required = true)
    private ZxCtWorksServiceImpl zxCtWorksServiceImpl;
    
    @Autowired(required = true)
    private ZxCtContrDetailService zxCtContrDetailService;
    
    @Autowired(required = true)
    private ZxSaProjectFinishService zxSaProjectFinishService;
    
    @Autowired(required = true)
    private ZxSaProjectFinishMapper zxSaProjectFinishMapper;
    
    @Autowired(required = true)
    private ZxCtProduceAmtCalMapper zxCtProduceAmtCalMapper;
    
    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtContractListByCondition(ZxCtContract zxCtContract) {
        if (zxCtContract == null) {
            zxCtContract = new ZxCtContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtContract.setCompanyId("");
            zxCtContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtContract.setCompanyId(zxCtContract.getOrgID());
            zxCtContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtContract.setOrgID(zxCtContract.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCtContract.getPage(),zxCtContract.getLimit());
        // 获取数据
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        // 得到分页信息
        PageInfo<ZxCtContract> p = new PageInfo<>(zxCtContractList);

        for (ZxCtContract ctContract : zxCtContractList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctContract.getId());
        	zxErpFile.setOtherType("1"); // 1:合同附件
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctContract.setAttachment(zxErpFileList);
        	
        	// 新增合同条款主表(保证金)
        	ZxCtContrDetail zxCtContrDetail = new ZxCtContrDetail();
        	zxCtContrDetail.setContractID(ctContract.getId());
        	List<ZxCtContrDetail> zxCtContrDetaillist = (List<ZxCtContrDetail>) zxCtContrDetailService.getZxCtContrDetailListByCondition(zxCtContrDetail).getData();
        	if (zxCtContrDetaillist != null && zxCtContrDetaillist.size() > 0) {
        		ctContract.setBondList(zxCtContrDetaillist.get(0).getZxCtContrDetailAttrList());
			}
        }
        
        return repEntity.okList(zxCtContractList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity getZxCtContractDetail(ZxCtContract zxCtContract) {
        if (zxCtContract == null) {
            zxCtContract = new ZxCtContract();
        }
        // 获取数据
        ZxCtContract dbZxCtContract = zxCtContractMapper.selectByPrimaryKey(zxCtContract.getId());
        // 数据存在
        if (dbZxCtContract != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtContract.getId());
        	zxErpFile.setOtherType("1"); // 1:合同附件
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtContract.setAttachment(zxErpFileList);
        	
        	// 保证金
        	ZxCtContrDetailAttr zxCtContrDetailAttr = new ZxCtContrDetailAttr();
        	zxCtContrDetailAttr.setContrDetailID(dbZxCtContract.getId());
			List<ZxCtContrDetailAttr> zxCtContrDetailAttrList = (List<ZxCtContrDetailAttr>) zxCtContrDetailAttrServiceImpl.getZxCtContrDetailAttrListByCondition(zxCtContrDetailAttr).getData();
        	dbZxCtContract.setBondList(zxCtContrDetailAttrList);
            return repEntity.ok(dbZxCtContract);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContract(ZxCtContract zxCtContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContract.setId(UuidUtil.generate());
        zxCtContract.setCreateUserInfo(userKey, realName);
        
        if (StrUtil.isNotEmpty(zxCtContract.getLotNo()) && zxCtContract.getLotNo().length() > 4) {
			return repEntity.layerMessage("no", "标段号长度不能大于四位!");
		}
        
        // 重复项目添加check
        ZxCtContract checkZxCtContract = new ZxCtContract();
        checkZxCtContract.setOrgID(zxCtContract.getOrgID());
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(checkZxCtContract);
        if (zxCtContractList != null && zxCtContractList.size() > 0) {
			return repEntity.layerMessage("no", "该项目已存在，请勿重复添加！");
		}
        
        int flag = zxCtContractMapper.insert(zxCtContract);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 新增清单书关联表信息
        	ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
        	zxCtWorkBook.setOrgID(zxCtContract.getOrgID());
        	zxCtWorkBook.setWorkBookName(zxCtContract.getOrgName());
        	zxCtWorkBook.setStatus("0");
        	zxCtWorkBook.setPp1("0");
        	zxCtWorkBook.setSystemType("10");
        	zxCtWorkBook.setEditTime(new Date());
        	zxCtWorkBookServiceImpl.saveZxCtWorkBook(zxCtWorkBook);
        	
        	// 新增合同条款主表(保证金)
        	ZxCtContrDetail zxCtContrDetail = new ZxCtContrDetail();
        	BeanUtil.copyProperties(zxCtContract, zxCtContrDetail, true);
        	zxCtContrDetail.setZxCtContrDetailAttrList(zxCtContract.getBondList());
        	zxCtContrDetail.setContractID(zxCtContract.getId());
        	zxCtContrDetailService.saveZxCtContrDetail(zxCtContrDetail);
        	
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtContract.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(zxCtContract.getId());
		        	zxErpFile.setOtherType("1"); // 1:合同附件
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
            return repEntity.ok("sys.data.sava", zxCtContract);
        }
    }

    @Override
    public ResponseEntity updateZxCtContract(ZxCtContract zxCtContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContract dbZxCtContract = zxCtContractMapper.selectByPrimaryKey(zxCtContract.getId());
        if (dbZxCtContract != null && StrUtil.isNotEmpty(dbZxCtContract.getId())) {
           // 业主合同编号
           dbZxCtContract.setContractNo(zxCtContract.getContractNo());
           // 合同名称
           dbZxCtContract.setContractName(zxCtContract.getContractName());
           // 合同类型
           dbZxCtContract.setContractType(zxCtContract.getContractType());
           // 中标资质方是否局资质
           dbZxCtContract.setIsOfficial(zxCtContract.getIsOfficial());
           // 合同显示类型
           dbZxCtContract.setContractViewType(zxCtContract.getContractViewType());
           // 合同种类
           dbZxCtContract.setContractSort(zxCtContract.getContractSort());
           // 摘要
           dbZxCtContract.setSubject(zxCtContract.getSubject());
           // 合同内容
           dbZxCtContract.setContent(zxCtContract.getContent());
           // 所属机构
           dbZxCtContract.setOrgID(zxCtContract.getOrgID());
           // 项目特征
           dbZxCtContract.setProjFea(zxCtContract.getProjFea());
           // 甲方ID
           dbZxCtContract.setFirstId(zxCtContract.getFirstId());
           // 甲方名称
           dbZxCtContract.setFirstName(zxCtContract.getFirstName());
           // 甲方代表
           dbZxCtContract.setFirstPrincipal(zxCtContract.getFirstPrincipal());
           // 甲方代表身份证
           dbZxCtContract.setFirstPrincipalIDCard(zxCtContract.getFirstPrincipalIDCard());
           // 甲方联系电话
           dbZxCtContract.setFirstUnitTel(zxCtContract.getFirstUnitTel());
           // 乙方ID
           dbZxCtContract.setSecondID(zxCtContract.getSecondID());
           // 乙方名称
           dbZxCtContract.setSecondName(zxCtContract.getSecondName());
           // 乙方代表
           dbZxCtContract.setSecondPrincipal(zxCtContract.getSecondPrincipal());
           // 乙方代表身份证
           dbZxCtContract.setSecondPrincipalIDCard(zxCtContract.getSecondPrincipalIDCard());
           // 乙方联系电话
           dbZxCtContract.setSecondUnitTel(zxCtContract.getSecondUnitTel());
           // 丙方
           dbZxCtContract.setThirdName(zxCtContract.getThirdName());
           // 收付类型
           dbZxCtContract.setPayType(zxCtContract.getPayType());
           // 所属处部
           dbZxCtContract.setLocationInDepr(zxCtContract.getLocationInDepr());
           // 所属处部ID
           dbZxCtContract.setLocationInDeprId(zxCtContract.getLocationInDeprId());
           // 登记日期
           dbZxCtContract.setRegisterDate(zxCtContract.getRegisterDate());
           // 中标日期
           dbZxCtContract.setBidDate(zxCtContract.getBidDate());
           // 签订日期
           dbZxCtContract.setSignatureDate(zxCtContract.getSignatureDate());
           // 合同总价
           dbZxCtContract.setContractCost(zxCtContract.getContractCost());
           // 合同工期
           dbZxCtContract.setCsTimeLimit(zxCtContract.getCsTimeLimit());
           // 合同开始时间
           dbZxCtContract.setPlanStartDate(zxCtContract.getPlanStartDate());
           // 实际开始时间
           dbZxCtContract.setActualStartDate(zxCtContract.getActualStartDate());
           // 合同结束时间
           dbZxCtContract.setPlanEndDate(zxCtContract.getPlanEndDate());
           // 实际结束时间
           dbZxCtContract.setActualEndDate(zxCtContract.getActualEndDate());
           // 主体完工日期
           dbZxCtContract.setMainEndDate(zxCtContract.getMainEndDate());
           // 归档日期
           dbZxCtContract.setDocDate(zxCtContract.getDocDate());
           // 项目交工日期
           dbZxCtContract.setDeliveryDate(zxCtContract.getDeliveryDate());
           // 工程规模
           dbZxCtContract.setProjectSize(zxCtContract.getProjectSize());
           // 监理单位
           dbZxCtContract.setConsultative(zxCtContract.getConsultative());
           // 监理单位电话
           dbZxCtContract.setConsultativeTel(zxCtContract.getConsultativeTel());
           // 业主联系电话
           dbZxCtContract.setOwnerLinkTel(zxCtContract.getOwnerLinkTel());
           // 项目经理姓名
           dbZxCtContract.setProjectManager(zxCtContract.getProjectManager());
           // 项目经理电话
           dbZxCtContract.setProjectManagerTel(zxCtContract.getProjectManagerTel());
           // 项目总工姓名
           dbZxCtContract.setProjectChiefName(zxCtContract.getProjectChiefName());
           // 项目总工电话
           dbZxCtContract.setProjectChiefTel(zxCtContract.getProjectChiefTel());
           // 是否有标的
           dbZxCtContract.setHasDetail(zxCtContract.getHasDetail());
           // 合同状态
           dbZxCtContract.setContractStatus(zxCtContract.getContractStatus());
           // 项目编号
           dbZxCtContract.setProjectNo(zxCtContract.getProjectNo());
           // 项目全称
           dbZxCtContract.setProjectName(zxCtContract.getProjectName());
           // 项目简称
           dbZxCtContract.setShortName(zxCtContract.getShortName());
           // 风险抵押金
           dbZxCtContract.setVentureGuaranty(zxCtContract.getVentureGuaranty());
           // 内部承包总价
           dbZxCtContract.setInnerContractSum(zxCtContract.getInnerContractSum());
           // 税率
           dbZxCtContract.setFaxRate(zxCtContract.getFaxRate());
           // 管理费率
           dbZxCtContract.setManageRate(zxCtContract.getManageRate());
           // 质保费率
           dbZxCtContract.setQuanlityFeeRate(zxCtContract.getQuanlityFeeRate());
           // 供货总额
           dbZxCtContract.setGoodsSum(zxCtContract.getGoodsSum());
           // 付款约定
           dbZxCtContract.setPaymentAssumpsit(zxCtContract.getPaymentAssumpsit());
           // 装运方式约定
           dbZxCtContract.setTransferAssumpsit(zxCtContract.getTransferAssumpsit());
           // 其他条款
           dbZxCtContract.setOtherAssumpsit(zxCtContract.getOtherAssumpsit());
           // 代理人
           dbZxCtContract.setAgent(zxCtContract.getAgent());
           // 经营指标
           dbZxCtContract.setManageIndex(zxCtContract.getManageIndex());
           // 施工单位
           dbZxCtContract.setImplementUnit(zxCtContract.getImplementUnit());
           // 设计单位
           dbZxCtContract.setDesignUnit(zxCtContract.getDesignUnit());
           // 经办人
           dbZxCtContract.setTransactor(zxCtContract.getTransactor());
           // 指定的
           dbZxCtContract.setNominated(zxCtContract.getNominated());
           // 转让合同
           dbZxCtContract.setAssignContract(zxCtContract.getAssignContract());
           // 备注
           dbZxCtContract.setRemarks(zxCtContract.getRemarks());
           // pp1
           dbZxCtContract.setPp1(zxCtContract.getPp1());
           // pp2
           dbZxCtContract.setPp2(zxCtContract.getPp2());
           // pp3
           dbZxCtContract.setPp3(zxCtContract.getPp3());
           // pp4
           dbZxCtContract.setPp4(zxCtContract.getPp4());
           // 项目所在地
           dbZxCtContract.setProjectLocation(zxCtContract.getProjectLocation());
           // 审核状态
           dbZxCtContract.setAuditStatus(zxCtContract.getAuditStatus());
           // 编辑时间
           dbZxCtContract.setEditTime(zxCtContract.getEditTime());
           // 甲方联系地址
           dbZxCtContract.setFirstAddr(zxCtContract.getFirstAddr());
           // 设计单位电话
           dbZxCtContract.setDesignPhone(zxCtContract.getDesignPhone());
           // 当前合同总造价（万元）
           dbZxCtContract.setContractMoney(zxCtContract.getContractMoney());
           // 设计单位位置
           dbZxCtContract.setDesignAddr(zxCtContract.getDesignAddr());
           // 监理单位地址
           dbZxCtContract.setConsultativeAddr(zxCtContract.getConsultativeAddr());
           // 合同编码
           dbZxCtContract.setCode(zxCtContract.getCode());
           // 机构编码
           dbZxCtContract.setCode1(zxCtContract.getCode1());
           // 成建单位简称
           dbZxCtContract.setContractor(zxCtContract.getContractor());
           // 中标资质
           dbZxCtContract.setBiddingQualification(zxCtContract.getBiddingQualification());
           // 项目所在省份简称
           dbZxCtContract.setProvinceAbbreviation(zxCtContract.getProvinceAbbreviation());
           // 项目简称
           dbZxCtContract.setCode5(zxCtContract.getCode5());
           // 标段号
           dbZxCtContract.setLotNo(zxCtContract.getLotNo());
           // 合同管理类别
           dbZxCtContract.setCode7(zxCtContract.getCode7());
           // 合同序号
           dbZxCtContract.setCode8(zxCtContract.getCode8());
           // 合同审批ID
           dbZxCtContract.setFromApplyID(zxCtContract.getFromApplyID());
           // 业主合同功能码（原字段codeP1）
           dbZxCtContract.setOwnerContractFunctionCode(zxCtContract.getOwnerContractFunctionCode());
           // 同一公司
           dbZxCtContract.setCode2T3(zxCtContract.getCode2T3());
           // 流程开始时间
           dbZxCtContract.setFlowBeginDate(zxCtContract.getFlowBeginDate());
           // 流程结束时间
           dbZxCtContract.setFlowEndDate(zxCtContract.getFlowEndDate());
           // 质量保证金扣除比例（%）
           dbZxCtContract.setQualityBailRate(zxCtContract.getQualityBailRate());
           // 安全生产保证金扣除比例（%）
           dbZxCtContract.setProdSafeBailRate(zxCtContract.getProdSafeBailRate());
           // 农民工工资偿付保证金扣除比例（%）
           dbZxCtContract.setPayBailRate(zxCtContract.getPayBailRate());
           // 材料口回款比例（%）
           dbZxCtContract.setClkhkblPercent(zxCtContract.getClkhkblPercent());
           // 时间开始日期
           dbZxCtContract.setRealBeginDate(zxCtContract.getRealBeginDate());
           // 实际结束时间
           dbZxCtContract.setRealEndDate(zxCtContract.getRealEndDate());
           // 是否局直属
           dbZxCtContract.setIsJuProj(zxCtContract.getIsJuProj());
           // 局直属项目名称ID
           dbZxCtContract.setJuProjID(zxCtContract.getJuProjID());
           // 局直属项目名称
           dbZxCtContract.setJuProjName(zxCtContract.getJuProjName());
           // 物资审查
           dbZxCtContract.setStockAudit(zxCtContract.getStockAudit());
           // 租金类型
           dbZxCtContract.setRentType(zxCtContract.getRentType());
           // 变更后含税合同金额（万元）
           dbZxCtContract.setAlterContractSum(zxCtContract.getAlterContractSum());
           // 结算情况
           dbZxCtContract.setSettleType(zxCtContract.getSettleType());
           // 材料来源
           dbZxCtContract.setMaterialSource(zxCtContract.getMaterialSource());
           // 基本信息审核按钮
           dbZxCtContract.setContrStatus(zxCtContract.getContrStatus());
           // 所属区域
           dbZxCtContract.setSubArea(zxCtContract.getSubArea());
           // 税率（%）
           dbZxCtContract.setTaxRate(zxCtContract.getTaxRate());
           // 是否抵扣
           dbZxCtContract.setIsDeduct(zxCtContract.getIsDeduct());
           // 不含税合同总价（万元）
           dbZxCtContract.setContractCostNoTax(zxCtContract.getContractCostNoTax());
           // 合同税额
           dbZxCtContract.setContractCostTax(zxCtContract.getContractCostTax());
           // 变更后不含税合同总价（万元）
           dbZxCtContract.setAlterContractSumNoTax(zxCtContract.getAlterContractSumNoTax());
           // 变更后合同税额
           dbZxCtContract.setAlterContractSumTax(zxCtContract.getAlterContractSumTax());
           // 是否简易计税
           dbZxCtContract.setIsEasyTax(zxCtContract.getIsEasyTax());
           // 项目类型
           dbZxCtContract.setProjectProperty(zxCtContract.getProjectProperty());
           // 授权委托人姓名
           dbZxCtContract.setWtrName(zxCtContract.getWtrName());
           // 授权委托人身份证号
           dbZxCtContract.setWtrPhone(zxCtContract.getWtrPhone());
           // 推荐人姓名
           dbZxCtContract.setReferenceName(zxCtContract.getReferenceName());
           // 推荐人职务
           dbZxCtContract.setReferencePost(zxCtContract.getReferencePost());
           // 推荐人电话
           dbZxCtContract.setReferencePhone(zxCtContract.getReferencePhone());
           // comID
           dbZxCtContract.setComID(zxCtContract.getComID());
           // 市名称
           dbZxCtContract.setCityName(zxCtContract.getCityName());
           // notDisplay
           dbZxCtContract.setNotDisplay(zxCtContract.getNotDisplay());
           // 财务系统ID
           dbZxCtContract.setFiId(zxCtContract.getFiId());
           // 名义投标单位ID
           dbZxCtContract.setBiddersId(zxCtContract.getBiddersId());
           // 名义投标单位编号
           dbZxCtContract.setBiddersCode(zxCtContract.getBiddersCode());
           // 名义投标单位
           dbZxCtContract.setBiddersName(zxCtContract.getBiddersName());
           // 核算单位ID
           dbZxCtContract.setAccountUnitId(zxCtContract.getAccountUnitId());
           // 核算单位编号
           dbZxCtContract.setAccountUnitCode(zxCtContract.getAccountUnitCode());
           // 核算单位
           dbZxCtContract.setAccountUnitName(zxCtContract.getAccountUnitName());
           // 核算项目ID
           dbZxCtContract.setAccountProjId(zxCtContract.getAccountProjId());
           // 核算项目编号
           dbZxCtContract.setAccountProjCode(zxCtContract.getAccountProjCode());
           // 核算项目
           dbZxCtContract.setAccountProjName(zxCtContract.getAccountProjName());
           // 项目属地
           dbZxCtContract.setProjSite(zxCtContract.getProjSite());
           // 业务板块
           dbZxCtContract.setBusiSegments(zxCtContract.getBusiSegments());
           // 项目资金来源
           dbZxCtContract.setProjFundsSource(zxCtContract.getProjFundsSource());
           // 事业部
           dbZxCtContract.setDivision(zxCtContract.getDivision());
           // 税务备案号（名义投标单位/中标单位）
           dbZxCtContract.setTaxFilingCode(zxCtContract.getTaxFilingCode());
           // 增值税计税方法
           dbZxCtContract.setTaxCountWay(zxCtContract.getTaxCountWay());
           // 增值税预征率（%）
           dbZxCtContract.setTaxAdvanceRate(zxCtContract.getTaxAdvanceRate());
           // 增值税使用税率（%）
           dbZxCtContract.setTaxUseWay(zxCtContract.getTaxUseWay());
           // 是否属地预缴
           dbZxCtContract.setPrepaidLand(zxCtContract.getPrepaidLand());
           // 预缴国税机关
           dbZxCtContract.setNationalTax(zxCtContract.getNationalTax());
           // 预缴国税机关联系方式
           dbZxCtContract.setNationalTaxTel(zxCtContract.getNationalTaxTel());
           // 预缴国税机关地址
           dbZxCtContract.setNationalTaxAdds(zxCtContract.getNationalTaxAdds());
           // 项目部通讯地址
           dbZxCtContract.setProjDepAdds(zxCtContract.getProjDepAdds());
           // 项目部邮编
           dbZxCtContract.setProjDepZipCode(zxCtContract.getProjDepZipCode());
           // 项目部电话
           dbZxCtContract.setProjDepTel(zxCtContract.getProjDepTel());
           // 项目部传真
           dbZxCtContract.setProjDepFax(zxCtContract.getProjDepFax());
           // 项目阶段
           dbZxCtContract.setProjStage(zxCtContract.getProjStage());
           // 项目经理固话
           dbZxCtContract.setPmFixedLine(zxCtContract.getPmFixedLine());
           // 项目经理邮箱
           dbZxCtContract.setPmMail(zxCtContract.getPmMail());
           // 财务负责人
           dbZxCtContract.setFiCharge(zxCtContract.getFiCharge());
           // 财务负责人手机
           dbZxCtContract.setFiTel(zxCtContract.getFiTel());
           // 财务负责人固话
           dbZxCtContract.setFiFixedLine(zxCtContract.getFiFixedLine());
           // 财务负责人邮箱
           dbZxCtContract.setFiMail(zxCtContract.getFiMail());
           // 合约负责人
           dbZxCtContract.setCtrCharge(zxCtContract.getCtrCharge());
           // 合约负责人手机
           dbZxCtContract.setCtrTel(zxCtContract.getCtrTel());
           // 合约负责人固话
           dbZxCtContract.setCtrFixedLine(zxCtContract.getCtrFixedLine());
           // 合约负责人邮箱
           dbZxCtContract.setCtrMail(zxCtContract.getCtrMail());
           // 债权清收负责人
           dbZxCtContract.setDcLeader(zxCtContract.getDcLeader());
           // 债权清收负责人手机
           dbZxCtContract.setDcTel(zxCtContract.getDcTel());
           // 债权清收负责人固话
           dbZxCtContract.setDcFixedLine(zxCtContract.getDcFixedLine());
           // 债权清收负责人邮箱
           dbZxCtContract.setDcMail(zxCtContract.getDcMail());
           // 核算部门id财务
           dbZxCtContract.setAccountDepId(zxCtContract.getAccountDepId());
           // 核算部门编号
           dbZxCtContract.setAccountDepCode(zxCtContract.getAccountDepCode());
           // 核算部门
           dbZxCtContract.setAccountDepName(zxCtContract.getAccountDepName());
           // 往来单位编号
           dbZxCtContract.setSecondIDCode(zxCtContract.getSecondIDCode());
           // 录入人
           dbZxCtContract.setWriter(zxCtContract.getWriter());
           // 录入日期
           dbZxCtContract.setWriteDate(zxCtContract.getWriteDate());
           // 财务合同状态
           dbZxCtContract.setFiCtrState(zxCtContract.getFiCtrState());
           // 系统编号
           dbZxCtContract.setSystemNo(zxCtContract.getSystemNo());
           // 币种编号
           dbZxCtContract.setCurrencyCode(zxCtContract.getCurrencyCode());
           // 合同性质
           dbZxCtContract.setCtrNature(zxCtContract.getCtrNature());
           // 复核人
           dbZxCtContract.setDoubleCheckPerson(zxCtContract.getDoubleCheckPerson());
           // 复核日期
           dbZxCtContract.setDoubleCheckDate(zxCtContract.getDoubleCheckDate());
           // 收支方向
           dbZxCtContract.setRevenueDir(zxCtContract.getRevenueDir());
           // 发票类型
           dbZxCtContract.setInvoiceType(zxCtContract.getInvoiceType());
           // 制式合同
           dbZxCtContract.setStaCtr(zxCtContract.getStaCtr());
           // 重点合同
           dbZxCtContract.setKeyCtr(zxCtContract.getKeyCtr());
           // 合同更新状态
           dbZxCtContract.setCtrUpdateState(zxCtContract.getCtrUpdateState());
           // 竣工决算日期
           dbZxCtContract.setFinalDate(zxCtContract.getFinalDate());
           // 投标状态
           dbZxCtContract.setBidStatus(zxCtContract.getBidStatus());
           // 质保金到期日期
           dbZxCtContract.setPremiumExpDate(zxCtContract.getPremiumExpDate());
           // 垫付款到期日期
           dbZxCtContract.setCushionExpDate(zxCtContract.getCushionExpDate());
           // 预付款比例(%)
           dbZxCtContract.setAdvanceRate(zxCtContract.getAdvanceRate());
           // 进度款比例(%)
           dbZxCtContract.setProgressRate(zxCtContract.getProgressRate());
           // 竣工款比例(%)
           dbZxCtContract.setCompletionRate(zxCtContract.getCompletionRate());
           // 往来单位财务编号
           dbZxCtContract.setFirstIdBh(zxCtContract.getFirstIdBh());
           // 往来单位主库编号
           dbZxCtContract.setFirstIdSh(zxCtContract.getFirstIdSh());
           // 往来单位编号
           dbZxCtContract.setSecondIDCodeBh(zxCtContract.getSecondIDCodeBh());
           // 结算资产额
           dbZxCtContract.setSettleTotalAmt(zxCtContract.getSettleTotalAmt());
           // 财务审核状态
           dbZxCtContract.setCwStatus(zxCtContract.getCwStatus());
           // 财务合同ID
           dbZxCtContract.setZjxmhtbNm(zxCtContract.getZjxmhtbNm());
           // 财务合同编号
           dbZxCtContract.setZjxmhtbBh(zxCtContract.getZjxmhtbBh());
           // 工程类别
           dbZxCtContract.setProjType(zxCtContract.getProjType());
           // 所属项目
           dbZxCtContract.setOrgName(zxCtContract.getOrgName());
           // 甲方
           dbZxCtContract.setFirstIdFormula(zxCtContract.getFirstIdFormula());
           // 统一社会信用代码
           dbZxCtContract.setOrgCertificate(zxCtContract.getOrgCertificate());
           // 国家
           dbZxCtContract.setCountry(zxCtContract.getCountry());
           // 机械及其他结算引用
           dbZxCtContract.setEquipSettleUseCount(zxCtContract.getEquipSettleUseCount());
           // 资产公司机械及其他结算引用
           dbZxCtContract.setZcgsEquipSettleUseCount(zxCtContract.getZcgsEquipSettleUseCount());
           // 工程结算引用数
           dbZxCtContract.setProjectSettleUseCount(zxCtContract.getProjectSettleUseCount());
           // 乙方
           dbZxCtContract.setSecondIDFormula(zxCtContract.getSecondIDFormula());
           // 施工单位
           dbZxCtContract.setLocationInDeprFormula(zxCtContract.getLocationInDeprFormula());
           // 地区
           dbZxCtContract.setArea(zxCtContract.getArea());
           // 清单是否审核
           dbZxCtContract.setWorkBookStatusP1(zxCtContract.getWorkBookStatusP1());
           // 清单是否审核
           dbZxCtContract.setWorkBookStatusP2(zxCtContract.getWorkBookStatusP2());
           // 变更审核次数
           dbZxCtContract.setAuditContrAlterCount(zxCtContract.getAuditContrAlterCount());
           // 编码引用
           dbZxCtContract.setCodeUsing(zxCtContract.getCodeUsing());
           // 从合同ID
           dbZxCtContract.setFromContractID(zxCtContract.getFromContractID());
           // 所属事业部
           dbZxCtContract.setBizDep(zxCtContract.getBizDep());
           // 是否参与结算指标考核
           dbZxCtContract.setIsSettle(zxCtContract.getIsSettle());
           // 投资合同编号
           dbZxCtContract.setInvestContractNo(zxCtContract.getInvestContractNo());
           // 资源类别ID
           dbZxCtContract.setResCategoryID(zxCtContract.getResCategoryID());
           // 资源类别
           dbZxCtContract.setResCategoryName(zxCtContract.getResCategoryName());
           // 排序
           dbZxCtContract.setSort(zxCtContract.getSort());
           // 附件
           dbZxCtContract.setAttachment(zxCtContract.getAttachment());
           // 项目性质
           dbZxCtContract.setProjQuality(zxCtContract.getProjQuality());
           // 动员预付款(元)
           dbZxCtContract.setDyyfkMoney(zxCtContract.getDyyfkMoney());
           // 动员预付款起扣点(元)
           dbZxCtContract.setDyyfkqkdMoney(zxCtContract.getDyyfkqkdMoney());
           // 扣回动员预付款比例(%)
           dbZxCtContract.setKhdyyfkPercent(zxCtContract.getKhdyyfkPercent());
           // 动员预付款全额扣回起点(元)
           dbZxCtContract.setDyyfkqekhdMoney(zxCtContract.getDyyfkqekhdMoney());
           // 材料预付款比例(%)
           dbZxCtContract.setClyfkPercent(zxCtContract.getClyfkPercent());
           // 材料预付款限额(元)
           dbZxCtContract.setClyfkxeMoney(zxCtContract.getClyfkxeMoney());
           // 迟扣款利息(%)
           dbZxCtContract.setCkklxPercent(zxCtContract.getCkklxPercent());
           // 迟扣款利息限额(元)
           dbZxCtContract.setCkklxxeMoney(zxCtContract.getCkklxxeMoney());
           // 违约金限额(元)
           dbZxCtContract.setWyjxeMoney(zxCtContract.getWyjxeMoney());
           // 索赔金限额(元)
           dbZxCtContract.setCpjexeMoney(zxCtContract.getCpjexeMoney());
           // 保留金扣款比例(%)
           dbZxCtContract.setBljblPercent(zxCtContract.getBljblPercent());
           // 保留金起扣点(元)
           dbZxCtContract.setBljqkdMoney(zxCtContract.getBljqkdMoney());
           // 累计保留金限额(元)
           dbZxCtContract.setLjkbljxeMoney(zxCtContract.getLjkbljxeMoney());
           // 主要工程项目及工程数量
           dbZxCtContract.setProjDetail(zxCtContract.getProjDetail());
           // 业主节点工期
           dbZxCtContract.setOwnerNodeDeys(zxCtContract.getOwnerNodeDeys());
           // 业主调差、变更定价原则等主要专用条款
           dbZxCtContract.setOwnerSpecialClause(zxCtContract.getOwnerSpecialClause());
           // 合同内容概述
           dbZxCtContract.setSummaryOfContractContents(zxCtContract.getSummaryOfContractContents());
           // 保证金列表
           dbZxCtContract.setBondList(zxCtContract.getBondList());
           // 清单金额
           dbZxCtContract.setQdMoney(zxCtContract.getQdMoney());
           // 共通
           dbZxCtContract.setModifyUserInfo(userKey, realName);
           flag = zxCtContractMapper.updateByPrimaryKey(dbZxCtContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	ZxErpFile delErpFile = new ZxErpFile();
        	delErpFile.setOtherId(dbZxCtContract.getId());
        	delErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtContract.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(dbZxCtContract.getId());
		        	zxErpFile.setOtherType("1"); // 1:合同附件
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 删除合同条款主表(保证金) 连带删除子表
        	ZxCtContrDetail delZxCtContrDetail = new ZxCtContrDetail();
        	delZxCtContrDetail.setContractID(dbZxCtContract.getId());
        	@SuppressWarnings("unchecked")
			List<ZxCtContrDetail> zxCtContrDetailList = (List<ZxCtContrDetail>) zxCtContrDetailService.getZxCtContrDetailListByCondition(delZxCtContrDetail).getData();
        	zxCtContrDetailService.batchDeleteUpdateZxCtContrDetail(zxCtContrDetailList);
        	
        	ZxCtContrDetail zxCtContrDetail = new ZxCtContrDetail();
        	BeanUtil.copyProperties(dbZxCtContract, zxCtContrDetail, true);
        	zxCtContrDetail.setZxCtContrDetailAttrList(zxCtContract.getBondList());
        	zxCtContrDetail.setContractID(dbZxCtContract.getId());
        	zxCtContrDetailService.saveZxCtContrDetail(zxCtContrDetail);
        	
            return repEntity.ok("sys.data.update",zxCtContract);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContract(List<ZxCtContract> zxCtContractList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContractList != null && zxCtContractList.size() > 0) {
           ZxCtContract zxCtContract = new ZxCtContract();
           zxCtContract.setModifyUserInfo(userKey, realName);
           
           // 查询业主合同审核状态及清单审核状态
           List<ZxCtContract> checkContractList = zxCtContractMapper.getContractAuditStatus(zxCtContractList);
			if (checkContractList != null && checkContractList.size() > 0) {
				for (ZxCtContract contract : checkContractList) {
					if (StrUtil.equals("1", contract.getContrStatus()) || StrUtil.equals("1", contract.getWorksAuditFlag())) {
						return repEntity.layerMessage("no", "业主合同信息、清单已审核，无法删除合同。");
					}
				}
			}
           
           flag = zxCtContractMapper.batchDeleteUpdateZxCtContract(zxCtContractList, zxCtContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtContract zxCtContract : zxCtContractList) {
        		// 附件
            	ZxErpFile delErpFile = new ZxErpFile();
            	delErpFile.setOtherId(zxCtContract.getId());
            	delErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
            	
            	// 删除合同条款主表(保证金) 连带删除子表
            	ZxCtContrDetail delZxCtContrDetail = new ZxCtContrDetail();
            	delZxCtContrDetail.setContractID(zxCtContract.getId());
            	@SuppressWarnings("unchecked")
    			List<ZxCtContrDetail> zxCtContrDetailList = (List<ZxCtContrDetail>) zxCtContrDetailService.getZxCtContrDetailListByCondition(delZxCtContrDetail).getData();
            	zxCtContrDetailService.batchDeleteUpdateZxCtContrDetail(zxCtContrDetailList);
            	
            	// 删除清单书关联表信息
            	ZxCtWorkBook delZxCtWorkBook = new ZxCtWorkBook();
            	delZxCtWorkBook.setOrgID(zxCtContract.getOrgID());
            	zxCtWorkBookServiceImpl.deleteAllZxCtWorkBook(delZxCtWorkBook);
            	
            	// 清单
            	ZxCtWorks delZxCtWorks = new ZxCtWorks();
            	delZxCtWorks.setOrgID(zxCtContract.getOrgID());
            	zxCtWorksServiceImpl.delZxCtWorksByOrgIDWorkBookID(delZxCtWorks);
			}
        	
            return repEntity.ok("sys.data.delete",zxCtContractList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity updateZxCtContractContrStatus(ZxCtContract zxCtContract) {
		if (zxCtContract == null || StrUtil.isEmpty(zxCtContract.getId())) {
			return repEntity.layerMessage("no", "主键ID不能为空！");
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
		
        int flag = 0;
        
        ZxCtContract dbZxCtContract = zxCtContractMapper.selectByPrimaryKey(zxCtContract.getId());
        if (dbZxCtContract != null && StrUtil.isNotEmpty(dbZxCtContract.getId())) {
        	// 基本信息审核按钮
            dbZxCtContract.setContrStatus(zxCtContract.getContrStatus());
            // 修改变更审核次数
            int auditContrAlterCount = dbZxCtContract.getAuditContrAlterCount() + 1;
            dbZxCtContract.setAuditContrAlterCount(auditContrAlterCount);
            // 共通
            dbZxCtContract.setModifyUserInfo(userKey, realName);
            flag = zxCtContractMapper.updateByPrimaryKey(dbZxCtContract);
        }
        
        if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			if (StrUtil.equals("1", zxCtContract.getContrStatus())) {
				// 查询完工审核信息
	    		ZxSaProjectFinish selectZxSaProjectFinish = new ZxSaProjectFinish();
	    		selectZxSaProjectFinish.setContractID(dbZxCtContract.getId());
	    		List<ZxSaProjectFinish> zxSaProjectFinishList = (List<ZxSaProjectFinish>) zxSaProjectFinishService.getZxSaProjectFinishListByCondition(selectZxSaProjectFinish).getData();
	    		
	    		if (zxSaProjectFinishList != null && zxSaProjectFinishList.size() > 0) {
					// 修改
	    			ZxSaProjectFinish upadteProjectFinish = zxSaProjectFinishList.get(0);
	    			upadteProjectFinish.setContractNo(dbZxCtContract.getContractNo()); // 合同编号
	    			upadteProjectFinish.setContractName(dbZxCtContract.getContractName()); // 合同名称
	    			upadteProjectFinish.setOrgID(dbZxCtContract.getOrgID()); // 项目id
	    			upadteProjectFinish.setOrgName(dbZxCtContract.getOrgName()); // 项目名称
	    			upadteProjectFinish.setContractCost(dbZxCtContract.getContractCost()); // 合同总造价（万元）
	    			upadteProjectFinish.setContractMoney(dbZxCtContract.getContractMoney()); // 当前合同总造价（万元）
	    			upadteProjectFinish.setProjectManager(dbZxCtContract.getProjectChiefName()); // 项目经理
	    			upadteProjectFinish.setActualStartDate(dbZxCtContract.getActualStartDate()); // 合同开工日期
	    			upadteProjectFinish.setActualEndDate(dbZxCtContract.getActualEndDate()); // 合同竣工日期
	    			upadteProjectFinish.setCsTimeLimit(dbZxCtContract.getCsTimeLimit()); // 合同工期（天）
	    			upadteProjectFinish.setRealBeginDate(dbZxCtContract.getRealBeginDate()); // 实际开工日期
	    			upadteProjectFinish.setRealEndDate(dbZxCtContract.getRealEndDate());// 实际竣工日期
//	    			upadteProjectFinish.setRealSettleEndDate(dbZxCtContract.getRealEndDate()); // 实际完工日期
	    			if (upadteProjectFinish.getRealSettleEndDate() != null && DateUtil.compare(new Date(), upadteProjectFinish.getRealSettleEndDate()) > 0) {
	    				upadteProjectFinish.setFinishStatus("1");
					} else {
						upadteProjectFinish.setFinishStatus("0");
					}
	    			upadteProjectFinish.setPp1(dbZxCtContract.getSubArea()); // 项目所在区域
//	        		if(dbZxCtContract.getRealEndDate() != null) {
//	        			upadteProjectFinish.setPlanSettleCloseDate(DateUtil.offsetMonth(dbZxCtContract.getRealEndDate(), 3)); // 计划结算关闭日期
//	        			upadteProjectFinish.setRealSettleCloseDate(DateUtil.offsetMonth(dbZxCtContract.getRealEndDate(), 3)); // 实际结算关闭日期
//	        		}
	        		upadteProjectFinish.setPp7(dbZxCtContract.getProjectLocation()); // 项目所在地
	        		upadteProjectFinish.setModifyUserInfo(userKey, realName);
	        		zxSaProjectFinishMapper.updateByPrimaryKey(upadteProjectFinish);
				} else {
					// 新增
					ZxSaProjectFinish zxSaProjectFinish = new ZxSaProjectFinish();
	        		zxSaProjectFinish.setContractID(dbZxCtContract.getId()); // 合同id
	        		zxSaProjectFinish.setContractNo(dbZxCtContract.getContractNo()); // 合同编号
	        		zxSaProjectFinish.setContractName(dbZxCtContract.getContractName()); // 合同名称
	        		zxSaProjectFinish.setOrgID(dbZxCtContract.getOrgID()); // 项目id
	        		zxSaProjectFinish.setOrgName(dbZxCtContract.getOrgName()); // 项目名称
	        		zxSaProjectFinish.setContractCost(dbZxCtContract.getContractCost()); // 合同总造价（万元）
	        		zxSaProjectFinish.setContractMoney(dbZxCtContract.getContractMoney()); // 当前合同总造价（万元）
	        		zxSaProjectFinish.setProjectManager(dbZxCtContract.getProjectChiefName()); // 项目经理
	        		zxSaProjectFinish.setActualStartDate(dbZxCtContract.getActualStartDate()); // 合同开工日期
	        		zxSaProjectFinish.setActualEndDate(dbZxCtContract.getActualEndDate()); // 合同竣工日期
	        		zxSaProjectFinish.setCsTimeLimit(dbZxCtContract.getCsTimeLimit()); // 合同工期（天）
	        		zxSaProjectFinish.setRealBeginDate(dbZxCtContract.getRealBeginDate()); // 实际开工日期
	        		zxSaProjectFinish.setRealEndDate(dbZxCtContract.getRealEndDate());// 实际竣工日期
//	        		zxSaProjectFinish.setRealSettleEndDate(dbZxCtContract.getRealEndDate()); // 实际完工日期
	        		if (zxSaProjectFinish.getRealSettleEndDate() != null && DateUtil.compare(new Date(), zxSaProjectFinish.getRealSettleEndDate()) > 0) {
	        			zxSaProjectFinish.setFinishStatus("1");
					} else {
						zxSaProjectFinish.setFinishStatus("0");
					}
	        		zxSaProjectFinish.setPp1(dbZxCtContract.getSubArea()); // 项目所在区域
//	        		if(dbZxCtContract.getRealEndDate() != null) {
//	        			zxSaProjectFinish.setPlanSettleCloseDate(DateUtil.offsetMonth(dbZxCtContract.getRealEndDate(), 3)); // 计划结算关闭日期
//	        			zxSaProjectFinish.setRealSettleCloseDate(DateUtil.offsetMonth(dbZxCtContract.getRealEndDate(), 3)); // 实际结算关闭日期
//	        		}
	        		zxSaProjectFinish.setReportDate(new Date()); // 填报日期
	        		zxSaProjectFinish.setReportPerson(realName); // 填报人
	        		zxSaProjectFinish.setAuditStatus("0"); // 状态
	        		zxSaProjectFinish.setComID(dbZxCtContract.getCompanyId()); // 所属公司id
	        		zxSaProjectFinish.setComName(dbZxCtContract.getCompanyName()); // 所属公司name
	        		zxSaProjectFinish.setPp7(dbZxCtContract.getProjectLocation()); // 项目所在地

	        		List<ZxSaProjectFinishItem> itemList = new ArrayList<>();
	        		ZxSaProjectFinishItem gcjsItem = new ZxSaProjectFinishItem();
	        		gcjsItem.setContractID(dbZxCtContract.getId());
	        		gcjsItem.setOrderNum("1");
	        		gcjsItem.setSettleTypeCode("P2");
	        		gcjsItem.setSettleType("工程结算");
//	        		gcjsItem.setPlanSettleCloseDate(zxSaProjectFinish.getPlanSettleCloseDate()); // 计划结算关闭日期
//	        		gcjsItem.setRealSettleCloseDate(zxSaProjectFinish.getRealSettleCloseDate()); // 实际结算关闭日期
	        		gcjsItem.setReportDate(new Date()); // 填报日期
	        		gcjsItem.setReportPerson(realName); // 填报人
	        		gcjsItem.setComID(dbZxCtContract.getCompanyId()); // 所属公司id
	        		gcjsItem.setComName(dbZxCtContract.getCompanyName()); // 所属公司name
	        		itemList.add(gcjsItem);
	        		
	        		ZxSaProjectFinishItem cljsItem = new ZxSaProjectFinishItem();
	        		cljsItem.setContractID(dbZxCtContract.getId());
	        		cljsItem.setOrderNum("2");
	        		cljsItem.setSettleTypeCode("P5");
	        		cljsItem.setSettleType("材料结算");
//	        		cljsItem.setPlanSettleCloseDate(zxSaProjectFinish.getPlanSettleCloseDate()); // 计划结算关闭日期
//	        		cljsItem.setRealSettleCloseDate(zxSaProjectFinish.getRealSettleCloseDate()); // 实际结算关闭日期
	        		cljsItem.setReportDate(new Date()); // 填报日期
	        		cljsItem.setReportPerson(realName); // 填报人
	        		cljsItem.setComID(dbZxCtContract.getCompanyId()); // 所属公司id
	        		cljsItem.setComName(dbZxCtContract.getCompanyName()); // 所属公司name
	        		itemList.add(cljsItem);
	        		
	        		ZxSaProjectFinishItem jxjsItem = new ZxSaProjectFinishItem();
	        		jxjsItem.setContractID(dbZxCtContract.getId());
	        		jxjsItem.setOrderNum("3");
	        		jxjsItem.setSettleTypeCode("P6");
	        		jxjsItem.setSettleType("机械结算");
//	        		jxjsItem.setPlanSettleCloseDate(zxSaProjectFinish.getPlanSettleCloseDate()); // 计划结算关闭日期
//	        		jxjsItem.setRealSettleCloseDate(zxSaProjectFinish.getRealSettleCloseDate()); // 实际结算关闭日期
	        		jxjsItem.setReportDate(new Date()); // 填报日期
	        		jxjsItem.setReportPerson(realName); // 填报人
	        		jxjsItem.setComID(dbZxCtContract.getCompanyId()); // 所属公司id
	        		jxjsItem.setComName(dbZxCtContract.getCompanyName()); // 所属公司name
	        		itemList.add(jxjsItem);
	        		
	        		ZxSaProjectFinishItem qtjsItem = new ZxSaProjectFinishItem();
	        		qtjsItem.setContractID(dbZxCtContract.getId());
	        		qtjsItem.setOrderNum("4");
	        		qtjsItem.setSettleTypeCode("21");
	        		qtjsItem.setSettleType("其他结算");
//	        		qtjsItem.setPlanSettleCloseDate(zxSaProjectFinish.getPlanSettleCloseDate()); // 计划结算关闭日期
//	        		qtjsItem.setRealSettleCloseDate(zxSaProjectFinish.getRealSettleCloseDate()); // 实际结算关闭日期
	        		qtjsItem.setReportDate(new Date()); // 填报日期
	        		qtjsItem.setReportPerson(realName); // 填报人
	        		qtjsItem.setComID(dbZxCtContract.getCompanyId()); // 所属公司id
	        		qtjsItem.setComName(dbZxCtContract.getCompanyName()); // 所属公司name
	        		itemList.add(qtjsItem);

	        		zxSaProjectFinish.setItemList(itemList);
	        		
	        		zxSaProjectFinishService.saveZxSaProjectFinish(zxSaProjectFinish);
				}
	    		// 修改项目立项中项目开工日期、项目竣工日期
	    		String url = Apih5Properties.getWebUrl() + "updateSysProjectByContract";
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("departmentId", dbZxCtContract.getOrgID());
                jsonObject.set("actualStartDate", dbZxCtContract.getRealBeginDate());
                jsonObject.set("actualEndDate", dbZxCtContract.getRealEndDate());
                HttpUtil.sendPostToken(url, jsonObject.toString(), token);
			} else {
				// 修改清单书审核状态
				ZxCtWorkBook updateZxCtWorkBook = new ZxCtWorkBook();
				updateZxCtWorkBook.setOrgID(dbZxCtContract.getOrgID());
				// 状态
				updateZxCtWorkBook.setPp1("0");
				updateZxCtWorkBook.setStatus("0");
				// 共通
				updateZxCtWorkBook.setModifyUserInfo(userKey, realName);
				zxCtWorkBookMapper.updateStatusByOrgId(updateZxCtWorkBook);
			}
			return repEntity.ok("sys.data.update", dbZxCtContract);
		}
	}

	@Override
	public ResponseEntity getZxCtContractListByStatus(ZxCtContract zxCtContract) {
		if (zxCtContract == null) {
            zxCtContract = new ZxCtContract();
        }
		zxCtContract.setContractStatus("1");
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
            zxCtContract.setCompanyId("");
            zxCtContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtContract.setCompanyId(zxCtContract.getOrgID());
            zxCtContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtContract.setOrgID(zxCtContract.getOrgID());
        }
        
        // 期次处理
        List<String> sensonList = new ArrayList<>();
        if (StrUtil.isNotEmpty(zxCtContract.getSeason())) {
			String sensonYear = zxCtContract.getSeason().substring(0, 4);
			String senson = zxCtContract.getSeason().substring(4);
			if (StrUtil.equals("01", senson)) {
				sensonList.add(sensonYear + "01");
				sensonList.add(sensonYear + "02");
				sensonList.add(sensonYear + "03");
			} else if(StrUtil.equals("02", senson)) {
				sensonList.add(sensonYear + "04");
				sensonList.add(sensonYear + "05");
				sensonList.add(sensonYear + "06");
			} else if(StrUtil.equals("03", senson)) {
				sensonList.add(sensonYear + "07");
				sensonList.add(sensonYear + "08");
				sensonList.add(sensonYear + "09");
			} else if(StrUtil.equals("04", senson)) {
				sensonList.add(sensonYear + "10");
				sensonList.add(sensonYear + "11");
				sensonList.add(sensonYear + "12");
			}
		}
        
        // 分页查询
        PageHelper.startPage(zxCtContract.getPage(),zxCtContract.getLimit());
        // 获取数据
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.getZxCtContractListByStatus(zxCtContract);
        // 得到分页信息
        PageInfo<ZxCtContract> p = new PageInfo<>(zxCtContractList);
        
        if (StrUtil.isNotEmpty(zxCtContract.getSeason())) {
        	for (ZxCtContract ctContract : zxCtContractList) {
        		// 查询
        		ZxCtProduceAmtCal selectProduceAmtCal = new ZxCtProduceAmtCal();
        		selectProduceAmtCal.setOrgID(ctContract.getOrgID());
        		ZxCtProduceAmtCal zxCtProduceAmtCal = zxCtProduceAmtCalMapper.selectTotalProduceAmt(selectProduceAmtCal, sensonList);
        		if (zxCtProduceAmtCal == null || zxCtProduceAmtCal.getProduceAmt() == null) {
        			ctContract.setTotalProduceAmt(new BigDecimal(0));
        		} else {
        			ctContract.setTotalProduceAmt(zxCtProduceAmtCal.getProduceAmt());
        		}
        	}
		}
		
		return repEntity.okList(zxCtContractList, p.getTotal());
	}
	
	@Override
	public ResponseEntity updateRealEndDate(ZxCtContract zxCtContract) {
		if (zxCtContract == null || StrUtil.isEmpty(zxCtContract.getId())) {
			return repEntity.layerMessage("no", "主键ID不能为空！");
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
		
		ZxCtContract dbZxCtContract = zxCtContractMapper.selectByPrimaryKey(zxCtContract.getId());
		if (dbZxCtContract != null && StrUtil.isNotEmpty(dbZxCtContract.getId())) {
			dbZxCtContract.setRealEndDate(zxCtContract.getRealEndDate());
			dbZxCtContract.setModifyUserInfo(userKey, realName);
			zxCtContractMapper.updateByPrimaryKey(dbZxCtContract);
			return repEntity.ok("修改成功！");
		} else {
			return repEntity.layerMessage("no", "修改失败!");
		}
	}

	@Override
	public ResponseEntity updateZxCtContractFinishInfo(ZxCtContract zxCtContract) {
		if (zxCtContract == null || StrUtil.isEmpty(zxCtContract.getOrgID())) {
			return repEntity.layerMessage("no", "orgId不能为空！");
		}
		
		Date actualStartDate = zxCtContract.getActualStartDate(); // 项目开工日期
		Date actualEndDate = zxCtContract.getActualEndDate(); // 项目竣工日期
		Date mainEndDate = zxCtContract.getMainEndDate(); // 主体完工日期
		Date docDate = zxCtContract.getDocDate(); // 归档日期
		
		if (actualStartDate != null || actualEndDate != null) {
			// 修改业主合同台账信息
			ZxCtContract selectContract = new ZxCtContract();
			selectContract.setOrgID(zxCtContract.getOrgID());
			List<ZxCtContract> contractList = zxCtContractMapper.selectByZxCtContractList(selectContract);
			if (contractList != null && contractList.size() > 0) {
				ZxCtContract dbZxCtContract = contractList.get(0);
				if (actualStartDate != null) {
					dbZxCtContract.setRealBeginDate(actualStartDate);
				}
				if (actualEndDate != null) {
					dbZxCtContract.setRealEndDate(actualEndDate);
				}
				zxCtContractMapper.updateByPrimaryKey(dbZxCtContract);
			}
		}
		
		if (actualStartDate != null || mainEndDate != null || docDate != null) {
			// 修改完工审核信息
			ZxSaProjectFinish selectProjectFinish = new ZxSaProjectFinish();
			selectProjectFinish.setOrgID(zxCtContract.getOrgID());
			List<ZxSaProjectFinish> projectFinishList = zxSaProjectFinishMapper.selectByZxSaProjectFinishList(selectProjectFinish);
			if (projectFinishList != null && projectFinishList.size() > 0) {
				ZxSaProjectFinish dbZxSaProjectFinish = projectFinishList.get(0);
				if (actualStartDate != null) {
					dbZxSaProjectFinish.setRealBeginDate(actualStartDate);
				}
				if (mainEndDate != null) {
					dbZxSaProjectFinish.setRealSettleEndDate(mainEndDate);
				}
				if (docDate != null) {
					dbZxSaProjectFinish.setGuidangDate(docDate);
					dbZxSaProjectFinish.setDetermineFilingDate(docDate);
				}
				zxSaProjectFinishMapper.updateByPrimaryKey(dbZxSaProjectFinish);
			}
		}
		return repEntity.ok("修改成功！");
	}

	@Override
	public ResponseEntity getZxCtContractListByOrgId(ZxCtContract zxCtContract) {
        // 获取数据
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        if (zxCtContractList == null || zxCtContractList.size()==0) {
        	return repEntity.ok(new ArrayList<>());
		} else {
			return repEntity.ok(zxCtContractList);
		}
	}

	/*
	 * 当这个业主合同台账建立任意一条对下的合同（工程、物资、设备、其他、附属）产生结算时（判断依据可以根据【对外经营合同管理】模块里这些类型的合同结算状态为： 
	 * 结算开始执行来进行判定），这个项目在次月月初（1号那天）是否参与指标考核 显示为：是（就是被勾选）
	 */
	@Override
	public ResponseEntity jobSyncZxCtContractIsSettle(ZxCtContract zxCtContract) throws Exception {
		// 查询并修改
		zxCtContractMapper.syncZxCtContractIsSettle(zxCtContract);
		return repEntity.ok("定时任务执行成功!");
	}


    @Override
    public ResponseEntity getZxCtContractListByCompanyId(ZxCtContract zxCtContract) {
        // 获取数据
        zxCtContract.setContractStatus("1");
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.getZxCtContractListByStatus(zxCtContract);
        if (zxCtContractList == null || zxCtContractList.size()==0) {
            return repEntity.ok(new ArrayList<>());
        } else {
            return repEntity.ok(zxCtContractList);
        }
    }
}
