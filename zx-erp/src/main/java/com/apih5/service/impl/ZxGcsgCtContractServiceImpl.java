package com.apih5.service.impl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrCustomerExtAttrMapper;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxGcsgCommonAttachmentMapper;
import com.apih5.mybatis.dao.ZxGcsgCtCoContractAmtRateMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContractMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;
import com.apih5.service.ZxGcsgCtContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zxGcsgCtContractService")
public class ZxGcsgCtContractServiceImpl implements ZxGcsgCtContractService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCtContractMapper zxGcsgCtContractMapper;
	@Autowired(required = true)
	private ZxGcsgCtCoContractAmtRateMapper zxGcsgCtCoContractAmtRateMapper;
	@Autowired(required = true)
	private ZxGcsgCommonAttachmentMapper zxGcsgCommonAttachmentMapper;
	@Autowired(required = true)
	private ZxCtContractMapper zxCtContractMapper;
	@Autowired(required = true)
	private ZxCrCustomerExtAttrMapper zxCrCustomerExtAttrMapper;

	@Override
	public ResponseEntity getZxGcsgCtContractListByCondition(ZxGcsgCtContract zxGcsgCtContract) {
		if (zxGcsgCtContract == null) {
			zxGcsgCtContract = new ZxGcsgCtContract();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		String userId = TokenUtils.getUserId(request);
		// 分页查询
		PageHelper.startPage(zxGcsgCtContract.getPage(), zxGcsgCtContract.getLimit());
		// 集团全部可见
		if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
			zxGcsgCtContract.setCompanyId("");
			zxGcsgCtContract.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxGcsgCtContract.setCompanyId(zxGcsgCtContract.getCompanyId());
			zxGcsgCtContract.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxGcsgCtContract.setCompanyId("");
			zxGcsgCtContract.setProjectId(zxGcsgCtContract.getProjectId());
		}
		// 获取数据
		List<ZxGcsgCtContract> zxGcsgCtContractList = zxGcsgCtContractMapper
				.selectByZxGcsgCtContractList(zxGcsgCtContract);
		// 得到分页信息
		PageInfo<ZxGcsgCtContract> p = new PageInfo<>(zxGcsgCtContractList);

		return repEntity.okList(zxGcsgCtContractList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCtContractDetail(ZxGcsgCtContract zxGcsgCtContract) {
		if (zxGcsgCtContract == null) {
			zxGcsgCtContract = new ZxGcsgCtContract();
		}
		// 获取数据
		ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper
				.selectByPrimaryKey(zxGcsgCtContract.getCtContractId());
		// 数据存在
		if (dbZxGcsgCtContract != null) {
			return repEntity.ok(dbZxGcsgCtContract);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCtContract(ZxGcsgCtContract zxGcsgCtContract) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCtContract.setCtContractId(UuidUtil.generate());
		zxGcsgCtContract.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCtContractMapper.insert(zxGcsgCtContract);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContract);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCtContract(ZxGcsgCtContract zxGcsgCtContract) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper
				.selectByPrimaryKey(zxGcsgCtContract.getCtContractId());
		if (dbZxGcsgCtContract != null && StrUtil.isNotEmpty(dbZxGcsgCtContract.getCtContractId())) {
			// 合同编号
			dbZxGcsgCtContract.setContractNo(zxGcsgCtContract.getContractNo());
			// 合同名称
			dbZxGcsgCtContract.setContractName(zxGcsgCtContract.getContractName());
			// 合同类型
			dbZxGcsgCtContract.setContractType(zxGcsgCtContract.getContractType());
			// 摘要
			dbZxGcsgCtContract.setIsOfficial(zxGcsgCtContract.getIsOfficial());
			// 合同显示类型
			dbZxGcsgCtContract.setContractViewType(zxGcsgCtContract.getContractViewType());
			// 合同种类:合同种类(总价合同或单价合同)
			dbZxGcsgCtContract.setContractSort(zxGcsgCtContract.getContractSort());
			// 摘要
			dbZxGcsgCtContract.setSubject(zxGcsgCtContract.getSubject());
			// 合同内容
			dbZxGcsgCtContract.setContent(zxGcsgCtContract.getContent());
			// 所属项目ID
			dbZxGcsgCtContract.setOrgID(zxGcsgCtContract.getOrgID());
			// 甲方ID
			dbZxGcsgCtContract.setFirstId(zxGcsgCtContract.getFirstId());
			// 合同甲方
			dbZxGcsgCtContract.setFirstName(zxGcsgCtContract.getFirstName());
			// 项目经理
			dbZxGcsgCtContract.setFirstPrincipal(zxGcsgCtContract.getFirstPrincipal());
			// 甲方代表身份证
			dbZxGcsgCtContract.setFirstPrincipalIDCard(zxGcsgCtContract.getFirstPrincipalIDCard());
			// 甲方联系电话
			dbZxGcsgCtContract.setFirstUnitTel(zxGcsgCtContract.getFirstUnitTel());
			// 乙方ID
			dbZxGcsgCtContract.setSecondID(zxGcsgCtContract.getSecondID());
			// 乙方名称
			dbZxGcsgCtContract.setSecondName(zxGcsgCtContract.getSecondName());
			// 乙方代表
			dbZxGcsgCtContract.setSecondPrincipal(zxGcsgCtContract.getSecondPrincipal());
			// 乙方代表身份证
			dbZxGcsgCtContract.setSecondPrincipalIDCard(zxGcsgCtContract.getSecondPrincipalIDCard());
			// 乙方(固话)
			dbZxGcsgCtContract.setSecondUnitTel(zxGcsgCtContract.getSecondUnitTel());
			// 丙方
			dbZxGcsgCtContract.setThirdName(zxGcsgCtContract.getThirdName());
			// 收付类型
			dbZxGcsgCtContract.setPayType(zxGcsgCtContract.getPayType());
			// 所属处部
			dbZxGcsgCtContract.setLocationInDepr(zxGcsgCtContract.getLocationInDepr());
			// 所属处部ID
			dbZxGcsgCtContract.setLocationInDeprId(zxGcsgCtContract.getLocationInDeprId());
			// 登记日期
			dbZxGcsgCtContract.setRegisterDate(zxGcsgCtContract.getRegisterDate());
			// 中标日期
			dbZxGcsgCtContract.setBidDate(zxGcsgCtContract.getBidDate());
			// 签订日期
			dbZxGcsgCtContract.setSignatureDate(zxGcsgCtContract.getSignatureDate());
			// 含税合同总价(万元)
			dbZxGcsgCtContract.setContractCost(zxGcsgCtContract.getContractCost());
			// 合同工期(天)
			dbZxGcsgCtContract.setCsTimeLimit(zxGcsgCtContract.getCsTimeLimit());
			// 合同开工日期
			dbZxGcsgCtContract.setPlanStartDate(zxGcsgCtContract.getPlanStartDate());
			// 实际开始时间
			dbZxGcsgCtContract.setActualStartDate(zxGcsgCtContract.getActualStartDate());
			// 合同竣工日期
			dbZxGcsgCtContract.setPlanEndDate(zxGcsgCtContract.getPlanEndDate());
			// 实际结束时间
			dbZxGcsgCtContract.setActualEndDate(zxGcsgCtContract.getActualEndDate());
			// 工程规模
			dbZxGcsgCtContract.setProjectSize(zxGcsgCtContract.getProjectSize());
			// 监理单位
			dbZxGcsgCtContract.setConsultative(zxGcsgCtContract.getConsultative());
			// 监理单位电话
			dbZxGcsgCtContract.setConsultativeTel(zxGcsgCtContract.getConsultativeTel());
			// 业主联系电话
			dbZxGcsgCtContract.setOwnerLinkTel(zxGcsgCtContract.getOwnerLinkTel());
			// 项目经理
			dbZxGcsgCtContract.setProjectManager(zxGcsgCtContract.getProjectManager());
			// 项目经理电话
			dbZxGcsgCtContract.setProjectManagerTel(zxGcsgCtContract.getProjectManagerTel());
			// 项目总工姓名
			dbZxGcsgCtContract.setProjectChiefName(zxGcsgCtContract.getProjectChiefName());
			// 项目总工电话
			dbZxGcsgCtContract.setProjectChiefTel(zxGcsgCtContract.getProjectChiefTel());
			// 是否有标的
			dbZxGcsgCtContract.setHasDetail(zxGcsgCtContract.getHasDetail());
			// 合同状态
			dbZxGcsgCtContract.setContractStatus(zxGcsgCtContract.getContractStatus());
			// 项目编号
			dbZxGcsgCtContract.setProjectNo(zxGcsgCtContract.getProjectNo());
			// 项目全称
			dbZxGcsgCtContract.setProjectName(zxGcsgCtContract.getProjectName());
			// 项目简称
			dbZxGcsgCtContract.setShortName(zxGcsgCtContract.getShortName());
			// 风险抵押金
			dbZxGcsgCtContract.setVentureGuaranty(zxGcsgCtContract.getVentureGuaranty());
			// 内部承包总价(万元)
			dbZxGcsgCtContract.setInnerContractSum(zxGcsgCtContract.getInnerContractSum());
			// 税率
			dbZxGcsgCtContract.setFaxRate(zxGcsgCtContract.getFaxRate());
			// 管理费率
			dbZxGcsgCtContract.setManageRate(zxGcsgCtContract.getManageRate());
			// 质保费率
			dbZxGcsgCtContract.setQuanlityFeeRate(zxGcsgCtContract.getQuanlityFeeRate());
			// 供货总额
			dbZxGcsgCtContract.setGoodsSum(zxGcsgCtContract.getGoodsSum());
			// 付款约定
			dbZxGcsgCtContract.setPaymentAssumpsit(zxGcsgCtContract.getPaymentAssumpsit());
			// 装运方式约定
			dbZxGcsgCtContract.setTransferAssumpsit(zxGcsgCtContract.getTransferAssumpsit());
			// 其他条款
			dbZxGcsgCtContract.setOtherAssumpsit(zxGcsgCtContract.getOtherAssumpsit());
			// 乙方性质
			dbZxGcsgCtContract.setAgent(zxGcsgCtContract.getAgent());
			// 经营指标
			dbZxGcsgCtContract.setManageIndex(zxGcsgCtContract.getManageIndex());
			// 施工单位
			dbZxGcsgCtContract.setImplementUnit(zxGcsgCtContract.getImplementUnit());
			// 设计单位
			dbZxGcsgCtContract.setDesignUnit(zxGcsgCtContract.getDesignUnit());
			// 经办人
			dbZxGcsgCtContract.setTransactor(zxGcsgCtContract.getTransactor());
			// 指定的
			dbZxGcsgCtContract.setNominated(zxGcsgCtContract.getNominated());
			// 是否指定合同
			dbZxGcsgCtContract.setAssignContract(zxGcsgCtContract.getAssignContract());
			// 乙方(手机)
			dbZxGcsgCtContract.setPp1(zxGcsgCtContract.getPp1());
			// 乙方(传真)
			dbZxGcsgCtContract.setPp2(zxGcsgCtContract.getPp2());
			// 合同类型
			dbZxGcsgCtContract.setPp3(zxGcsgCtContract.getPp3());
			// 累计结算金额
			dbZxGcsgCtContract.setPp4(zxGcsgCtContract.getPp4());
			// 累计支付金额
			dbZxGcsgCtContract.setPp5(zxGcsgCtContract.getPp5());
			// pp6
			dbZxGcsgCtContract.setPp6(zxGcsgCtContract.getPp6());
			// pp7
			dbZxGcsgCtContract.setPp7(zxGcsgCtContract.getPp7());
			// 清单
			dbZxGcsgCtContract.setPp8(zxGcsgCtContract.getPp8());
			// pp9
			dbZxGcsgCtContract.setPp9(zxGcsgCtContract.getPp9());
			// pp10
			dbZxGcsgCtContract.setPp10(zxGcsgCtContract.getPp10());
			// 乙方(手机)(pp1)
			dbZxGcsgCtContract.setSecondPartyMobile(zxGcsgCtContract.getSecondPartyMobile());
			// 乙方(传真)(pp2)
			dbZxGcsgCtContract.setSecondPartyFax(zxGcsgCtContract.getSecondPartyFax());
			// 合同类型(pp3)
			dbZxGcsgCtContract.setContractManageType(zxGcsgCtContract.getContractManageType());
			// 累计结算金额(pp4)
			dbZxGcsgCtContract.setAccSettlementAmount(zxGcsgCtContract.getAccSettlementAmount());
			// 累计支付金额(pp5)
			dbZxGcsgCtContract.setAccPaymentAmount(zxGcsgCtContract.getAccPaymentAmount());
			// 清单(pp8)
			dbZxGcsgCtContract.setWorksId(zxGcsgCtContract.getWorksId());
			// 评审状态
			dbZxGcsgCtContract.setAuditStatus(zxGcsgCtContract.getAuditStatus());
			// 最后编辑时间
			dbZxGcsgCtContract.setEditTime(zxGcsgCtContract.getEditTime());
			// 甲方地址
			dbZxGcsgCtContract.setFirstAddr(zxGcsgCtContract.getFirstAddr());
			// 设计单位电话
			dbZxGcsgCtContract.setDesignPhone(zxGcsgCtContract.getDesignPhone());
			// 当前合同总造价（万元）
			dbZxGcsgCtContract.setContractMoney(zxGcsgCtContract.getContractMoney());
			// 设计单位地址
			dbZxGcsgCtContract.setDesignAddr(zxGcsgCtContract.getDesignAddr());
			// 监理单位电话:监理单位地址
			dbZxGcsgCtContract.setConsultativeAddr(zxGcsgCtContract.getConsultativeAddr());
			// name
			dbZxGcsgCtContract.setCode(zxGcsgCtContract.getCode());
			// 机构编码
			dbZxGcsgCtContract.setCode1(zxGcsgCtContract.getCode1());
			// 承建单位简称
			dbZxGcsgCtContract.setCode2(zxGcsgCtContract.getCode2());
			// 中标单位简称
			dbZxGcsgCtContract.setCode3(zxGcsgCtContract.getCode3());
			// 项目所在省份简称
			dbZxGcsgCtContract.setCode4(zxGcsgCtContract.getCode4());
			// 项目简称
			dbZxGcsgCtContract.setCode5(zxGcsgCtContract.getCode5());
			// 标段号
			dbZxGcsgCtContract.setCode6(zxGcsgCtContract.getCode6());
			// 合同类别
			dbZxGcsgCtContract.setCode7(zxGcsgCtContract.getCode7());
			// 合同序号
			dbZxGcsgCtContract.setCode8(zxGcsgCtContract.getCode8());
			// 合同评审ID(fromApplyID)
			dbZxGcsgCtContract.setCtContrApplyId(zxGcsgCtContract.getCtContrApplyId());
			// 业主合同功能码
			dbZxGcsgCtContract.setCodeP1(zxGcsgCtContract.getCodeP1());
			// 同一公司
			dbZxGcsgCtContract.setCode2T3(zxGcsgCtContract.getCode2T3());
			// 评审开始时间
			dbZxGcsgCtContract.setFlowBeginDate(zxGcsgCtContract.getFlowBeginDate());
			// 评审结束时间
			dbZxGcsgCtContract.setFlowEndDate(zxGcsgCtContract.getFlowEndDate());
			// 质量保证金扣除比例（%）
			dbZxGcsgCtContract.setQualityBailRate(zxGcsgCtContract.getQualityBailRate());
			// 安全生产保证金扣除比例（%）
			dbZxGcsgCtContract.setProdSafeBailRate(zxGcsgCtContract.getProdSafeBailRate());
			// 农民工工资偿付保证金扣除比例（%）
			dbZxGcsgCtContract.setPayBailRate(zxGcsgCtContract.getPayBailRate());
			// 实际开工日期
			dbZxGcsgCtContract.setRealBeginDate(zxGcsgCtContract.getRealBeginDate());
			// 实际结束日期
			dbZxGcsgCtContract.setRealEndDate(zxGcsgCtContract.getRealEndDate());
			// 是否局属项目
			dbZxGcsgCtContract.setIsJuProj(zxGcsgCtContract.getIsJuProj());
			// 局直属项目ID
			dbZxGcsgCtContract.setJuProjID(zxGcsgCtContract.getJuProjID());
			// 局直属项目名称
			dbZxGcsgCtContract.setJuProjName(zxGcsgCtContract.getJuProjName());
			// 用于物资审核
			dbZxGcsgCtContract.setStockAudit(zxGcsgCtContract.getStockAudit());
			// 合同管理中的租赁类型
			dbZxGcsgCtContract.setRentType(zxGcsgCtContract.getRentType());
			// 变更后含税合同金额(万元)
			dbZxGcsgCtContract.setAlterContractSum(zxGcsgCtContract.getAlterContractSum());
			// 结算情况
			dbZxGcsgCtContract.setSettleType(zxGcsgCtContract.getSettleType());
			// 物资来源
			dbZxGcsgCtContract.setMaterialSource(zxGcsgCtContract.getMaterialSource());
			// 基本信息审核按钮
			dbZxGcsgCtContract.setContrStatus(zxGcsgCtContract.getContrStatus());
			// 所属区域
			dbZxGcsgCtContract.setSubArea(zxGcsgCtContract.getSubArea());
			// 不含税合同总价(万元)
			dbZxGcsgCtContract.setContractCostNoTax(zxGcsgCtContract.getContractCostNoTax());
			// 合同税额(万元)
			dbZxGcsgCtContract.setContractCostTax(zxGcsgCtContract.getContractCostTax());
			// 变更后不含税合同金额(万元)
			dbZxGcsgCtContract.setAlterContractSumNoTax(zxGcsgCtContract.getAlterContractSumNoTax());
			// 变更后合同税额(万元)
			dbZxGcsgCtContract.setAlterContractSumTax(zxGcsgCtContract.getAlterContractSumTax());
			// 税率(%)
			dbZxGcsgCtContract.setTaxRate(zxGcsgCtContract.getTaxRate());
			// 是否抵扣
			dbZxGcsgCtContract.setIsDeduct(zxGcsgCtContract.getIsDeduct());
			// 是否简易计税
			dbZxGcsgCtContract.setIsEasyTax(zxGcsgCtContract.getIsEasyTax());
			// 【业主合同台账】_项目性质
			dbZxGcsgCtContract.setProjectProperty(zxGcsgCtContract.getProjectProperty());
			// 授权委托人姓名
			dbZxGcsgCtContract.setWtrName(zxGcsgCtContract.getWtrName());
			// 授权委托人身份证号
			dbZxGcsgCtContract.setWtrPhone(zxGcsgCtContract.getWtrPhone());
			// 推荐人姓名
			dbZxGcsgCtContract.setReferenceName(zxGcsgCtContract.getReferenceName());
			// 推荐人职务
			dbZxGcsgCtContract.setReferencePost(zxGcsgCtContract.getReferencePost());
			// 推荐人电话
			dbZxGcsgCtContract.setReferencePhone(zxGcsgCtContract.getReferencePhone());
			// comID
			dbZxGcsgCtContract.setComID(zxGcsgCtContract.getComID());
			// 财务系统ID
			dbZxGcsgCtContract.setFiId(zxGcsgCtContract.getFiId());
			// 名义投标单位ID
			dbZxGcsgCtContract.setBiddersId(zxGcsgCtContract.getBiddersId());
			// 名义投标单位编号
			dbZxGcsgCtContract.setBiddersCode(zxGcsgCtContract.getBiddersCode());
			// 名义投标单位
			dbZxGcsgCtContract.setBiddersName(zxGcsgCtContract.getBiddersName());
			// 核算单位id
			dbZxGcsgCtContract.setAccountUnitId(zxGcsgCtContract.getAccountUnitId());
			// 核算单位编号
			dbZxGcsgCtContract.setAccountUnitCode(zxGcsgCtContract.getAccountUnitCode());
			// 核算单位
			dbZxGcsgCtContract.setAccountUnitName(zxGcsgCtContract.getAccountUnitName());
			// 核算项目Id
			dbZxGcsgCtContract.setAccountProjId(zxGcsgCtContract.getAccountProjId());
			// 核算项目编号
			dbZxGcsgCtContract.setAccountProjCode(zxGcsgCtContract.getAccountProjCode());
			// 核算项目名称
			dbZxGcsgCtContract.setAccountProjName(zxGcsgCtContract.getAccountProjName());
			// 项目属地
			dbZxGcsgCtContract.setProjSite(zxGcsgCtContract.getProjSite());
			// 业务板块
			dbZxGcsgCtContract.setBusiSegments(zxGcsgCtContract.getBusiSegments());
			// 项目资金来源
			dbZxGcsgCtContract.setProjFundsSource(zxGcsgCtContract.getProjFundsSource());
			// 事业部
			dbZxGcsgCtContract.setDivision(zxGcsgCtContract.getDivision());
			// 税务备案号
			dbZxGcsgCtContract.setTaxFilingCode(zxGcsgCtContract.getTaxFilingCode());
			// 增值税计税方法
			dbZxGcsgCtContract.setTaxCountWay(zxGcsgCtContract.getTaxCountWay());
			// 增值税预征率
			dbZxGcsgCtContract.setTaxAdvanceRate(zxGcsgCtContract.getTaxAdvanceRate());
			// 增值税使用税率
			dbZxGcsgCtContract.setTaxUseWay(zxGcsgCtContract.getTaxUseWay());
			// 是否属地预缴
			dbZxGcsgCtContract.setPrepaidLand(zxGcsgCtContract.getPrepaidLand());
			// 预缴国税机关
			dbZxGcsgCtContract.setNationalTax(zxGcsgCtContract.getNationalTax());
			// 预缴国税机关联系方式
			dbZxGcsgCtContract.setNationalTaxTel(zxGcsgCtContract.getNationalTaxTel());
			// 预交国税机关地址
			dbZxGcsgCtContract.setNationalTaxAdds(zxGcsgCtContract.getNationalTaxAdds());
			// 项目部通讯地址
			dbZxGcsgCtContract.setProjDepAdds(zxGcsgCtContract.getProjDepAdds());
			// 邮编
			dbZxGcsgCtContract.setProjDepZipCode(zxGcsgCtContract.getProjDepZipCode());
			// 电话
			dbZxGcsgCtContract.setProjDepTel(zxGcsgCtContract.getProjDepTel());
			// 传真
			dbZxGcsgCtContract.setProjDepFax(zxGcsgCtContract.getProjDepFax());
			// 项目阶段
			dbZxGcsgCtContract.setProjStage(zxGcsgCtContract.getProjStage());
			// 固话
			dbZxGcsgCtContract.setPmFixedLine(zxGcsgCtContract.getPmFixedLine());
			// 邮箱
			dbZxGcsgCtContract.setPmMail(zxGcsgCtContract.getPmMail());
			// 财务负责人
			dbZxGcsgCtContract.setFiCharge(zxGcsgCtContract.getFiCharge());
			// 手机
			dbZxGcsgCtContract.setFiTel(zxGcsgCtContract.getFiTel());
			// 固话
			dbZxGcsgCtContract.setFiFixedLine(zxGcsgCtContract.getFiFixedLine());
			// 邮箱
			dbZxGcsgCtContract.setFiMail(zxGcsgCtContract.getFiMail());
			// 合约负责人
			dbZxGcsgCtContract.setCtrCharge(zxGcsgCtContract.getCtrCharge());
			// 手机
			dbZxGcsgCtContract.setCtrTel(zxGcsgCtContract.getCtrTel());
			// 固话
			dbZxGcsgCtContract.setCtrFixedLine(zxGcsgCtContract.getCtrFixedLine());
			// 邮箱
			dbZxGcsgCtContract.setCtrMail(zxGcsgCtContract.getCtrMail());
			// 债权清收负责人
			dbZxGcsgCtContract.setDcLeader(zxGcsgCtContract.getDcLeader());
			// 手机
			dbZxGcsgCtContract.setDcTel(zxGcsgCtContract.getDcTel());
			// 固话
			dbZxGcsgCtContract.setDcFixedLine(zxGcsgCtContract.getDcFixedLine());
			// 邮箱
			dbZxGcsgCtContract.setDcMail(zxGcsgCtContract.getDcMail());
			// 复核日期
			dbZxGcsgCtContract.setDoubleCheckDate(zxGcsgCtContract.getDoubleCheckDate());
			// 录入日期
			dbZxGcsgCtContract.setWriteDate(zxGcsgCtContract.getWriteDate());
			// 核算部门id财务
			dbZxGcsgCtContract.setAccountDepId(zxGcsgCtContract.getAccountDepId());
			// 核算部门编号财务
			dbZxGcsgCtContract.setAccountDepCode(zxGcsgCtContract.getAccountDepCode());
			// 核算部门名称财务
			dbZxGcsgCtContract.setAccountDepName(zxGcsgCtContract.getAccountDepName());
			// 往来单位编号
			dbZxGcsgCtContract.setSecondIDCode(zxGcsgCtContract.getSecondIDCode());
			// 录入人
			dbZxGcsgCtContract.setWriter(zxGcsgCtContract.getWriter());
			// 系统编号
			dbZxGcsgCtContract.setSystemNo(zxGcsgCtContract.getSystemNo());
			// 币种编号
			dbZxGcsgCtContract.setCurrencyCode(zxGcsgCtContract.getCurrencyCode());
			// 合同性质
			dbZxGcsgCtContract.setCtrNature(zxGcsgCtContract.getCtrNature());
			// 合同更新状态
			dbZxGcsgCtContract.setCtrUpdateState(zxGcsgCtContract.getCtrUpdateState());
			// 财务合同状态
			dbZxGcsgCtContract.setFiCtrState(zxGcsgCtContract.getFiCtrState());
			// 收支方向
			dbZxGcsgCtContract.setRevenueDir(zxGcsgCtContract.getRevenueDir());
			// 发票类型
			dbZxGcsgCtContract.setInvoiceType(zxGcsgCtContract.getInvoiceType());
			// 制式合同
			dbZxGcsgCtContract.setStaCtr(zxGcsgCtContract.getStaCtr());
			// 重点合同
			dbZxGcsgCtContract.setKeyCtr(zxGcsgCtContract.getKeyCtr());
			// 市级
			dbZxGcsgCtContract.setCityName(zxGcsgCtContract.getCityName());
			// notDisplay
			dbZxGcsgCtContract.setNotDisplay(zxGcsgCtContract.getNotDisplay());
			// 复核人
			dbZxGcsgCtContract.setDoubleCheckPerson(zxGcsgCtContract.getDoubleCheckPerson());
			// 竣工决算日期
			dbZxGcsgCtContract.setFinalDate(zxGcsgCtContract.getFinalDate());
			// 投标状态
			dbZxGcsgCtContract.setBidStatus(zxGcsgCtContract.getBidStatus());
			// 质保金到期日期
			dbZxGcsgCtContract.setPremiumExpDate(zxGcsgCtContract.getPremiumExpDate());
			// 垫付款到期日期
			dbZxGcsgCtContract.setCushionExpDate(zxGcsgCtContract.getCushionExpDate());
			// 预付款比例
			dbZxGcsgCtContract.setAdvanceRate(zxGcsgCtContract.getAdvanceRate());
			// 进度款比例
			dbZxGcsgCtContract.setProgressRate(zxGcsgCtContract.getProgressRate());
			// 竣工款比例
			dbZxGcsgCtContract.setCompletionRate(zxGcsgCtContract.getCompletionRate());
			// 往来单位编号LSWLDW_WLDWBH
			dbZxGcsgCtContract.setFirstIdBh(zxGcsgCtContract.getFirstIdBh());
			// 往来单位编号lswldw_sh
			dbZxGcsgCtContract.setFirstIdSh(zxGcsgCtContract.getFirstIdSh());
			// 往来单位编号
			dbZxGcsgCtContract.setSecondIDCodeBh(zxGcsgCtContract.getSecondIDCodeBh());
			// 累计结算金额
			dbZxGcsgCtContract.setSettleTotalAmt(zxGcsgCtContract.getSettleTotalAmt());
			// 财务审核状态
			dbZxGcsgCtContract.setCwStatus(zxGcsgCtContract.getCwStatus());
			// 备注
			dbZxGcsgCtContract.setRemarks(zxGcsgCtContract.getRemarks());
			// 财务合同ID
			dbZxGcsgCtContract.setZjxmhtbNm(zxGcsgCtContract.getZjxmhtbNm());
			// 排序
			dbZxGcsgCtContract.setSort(zxGcsgCtContract.getSort());
			// 财务合同编号
			dbZxGcsgCtContract.setZjxmhtbBh(zxGcsgCtContract.getZjxmhtbBh());
			// 往来单位名称
			dbZxGcsgCtContract.setSecondIDCodeName(zxGcsgCtContract.getSecondIDCodeName());
			// 项目内码
			dbZxGcsgCtContract.setZJGCXMNM(zxGcsgCtContract.getZJGCXMNM());
			// 项目编号
			dbZxGcsgCtContract.setZJGCXMXMBH(zxGcsgCtContract.getZJGCXMXMBH());
			// 项目名称
			dbZxGcsgCtContract.setZJGCXMXMMC(zxGcsgCtContract.getZJGCXMXMMC());
			// 财务合同名称
			dbZxGcsgCtContract.setZjxmhtbMc(zxGcsgCtContract.getZjxmhtbMc());
			// 是否关联
			dbZxGcsgCtContract.setIsRela(zxGcsgCtContract.getIsRela());
			// 合同码ID
			dbZxGcsgCtContract.setContractCodeID(zxGcsgCtContract.getContractCodeID());
			// 所属项目
			dbZxGcsgCtContract.setOrgName(zxGcsgCtContract.getOrgName());
			// 甲方
			dbZxGcsgCtContract.setFirstIdFormula(zxGcsgCtContract.getFirstIdFormula());
			// 合同乙方
			dbZxGcsgCtContract.setSecondIDFormula(zxGcsgCtContract.getSecondIDFormula());
			// 统一社会信用代码
			dbZxGcsgCtContract.setOrgCertificate(zxGcsgCtContract.getOrgCertificate());
			// 所属处部
			dbZxGcsgCtContract.setLocationInDeprFormula(zxGcsgCtContract.getLocationInDeprFormula());
			// 区域(北京市广东省..)
			dbZxGcsgCtContract.setArea(zxGcsgCtContract.getArea());
			// 国家
			dbZxGcsgCtContract.setCountry(zxGcsgCtContract.getCountry());
			// 类型(公路铁路市政其他)
			dbZxGcsgCtContract.setProjType(zxGcsgCtContract.getProjType());
			// 项目特征
			dbZxGcsgCtContract.setProjFea(zxGcsgCtContract.getProjFea());
			// 承包合同中清单是否审核
			dbZxGcsgCtContract.setWorkBookStatusP1(zxGcsgCtContract.getWorkBookStatusP1());
			// 清单是否审核
			dbZxGcsgCtContract.setWorkBookStatusP2(zxGcsgCtContract.getWorkBookStatusP2());
			// 物资变更是否有审核单据
			dbZxGcsgCtContract.setAuditContrAlterCount(zxGcsgCtContract.getAuditContrAlterCount());
			// 用于判断编号是否已被引用
			dbZxGcsgCtContract.setCodeUsing(zxGcsgCtContract.getCodeUsing());
			// 原合同编号ID
			dbZxGcsgCtContract.setFromContractID(zxGcsgCtContract.getFromContractID());
			// 物资结算引用数
			dbZxGcsgCtContract.setStockSettleUseCount(zxGcsgCtContract.getStockSettleUseCount());
			// 机械及其他结算引用
			dbZxGcsgCtContract.setEquipSettleUseCount(zxGcsgCtContract.getEquipSettleUseCount());
			// 资产公司结算引用用于明细是否可以编辑处理
			dbZxGcsgCtContract.setZcgsEquipSettleUseCount(zxGcsgCtContract.getZcgsEquipSettleUseCount());
			// 工程结算引用数
			dbZxGcsgCtContract.setProjectSettleUseCount(zxGcsgCtContract.getProjectSettleUseCount());
			// 所属事业部
			dbZxGcsgCtContract.setBizDep(zxGcsgCtContract.getBizDep());
			// 是否参与结算指标考核
			dbZxGcsgCtContract.setIsSettle(zxGcsgCtContract.getIsSettle());
			// 是否局发送
			dbZxGcsgCtContract.setIsJuSend(zxGcsgCtContract.getIsJuSend());
			// 投资合同编号
			dbZxGcsgCtContract.setInvestContractNo(zxGcsgCtContract.getInvestContractNo());
			// 物资类别ID
			dbZxGcsgCtContract.setResCategoryID(zxGcsgCtContract.getResCategoryID());
			// 物资类别
			dbZxGcsgCtContract.setResCategoryName(zxGcsgCtContract.getResCategoryName());
			// 备注
			dbZxGcsgCtContract.setOpinionField1(zxGcsgCtContract.getOpinionField1());
			// 备注
			dbZxGcsgCtContract.setOpinionField2(zxGcsgCtContract.getOpinionField2());
			// 备注
			dbZxGcsgCtContract.setOpinionField3(zxGcsgCtContract.getOpinionField3());
			// 备注
			dbZxGcsgCtContract.setOpinionField4(zxGcsgCtContract.getOpinionField4());
			// 备注
			dbZxGcsgCtContract.setOpinionField5(zxGcsgCtContract.getOpinionField5());
			// 备注
			dbZxGcsgCtContract.setOpinionField6(zxGcsgCtContract.getOpinionField6());
			// 备注
			dbZxGcsgCtContract.setOpinionField7(zxGcsgCtContract.getOpinionField7());
			// 备注
			dbZxGcsgCtContract.setOpinionField8(zxGcsgCtContract.getOpinionField8());
			// 备注
			dbZxGcsgCtContract.setOpinionField9(zxGcsgCtContract.getOpinionField9());
			// 备注
			dbZxGcsgCtContract.setOpinionField10(zxGcsgCtContract.getOpinionField10());
			// 流程id
			dbZxGcsgCtContract.setApih5FlowId(zxGcsgCtContract.getApih5FlowId());
			// work_id
			dbZxGcsgCtContract.setWorkId(zxGcsgCtContract.getWorkId());
			// 工序审核状态
			dbZxGcsgCtContract.setApih5FlowStatus(zxGcsgCtContract.getApih5FlowStatus());
			// 流程状态
			dbZxGcsgCtContract.setApih5FlowNodeStatus(zxGcsgCtContract.getApih5FlowNodeStatus());
			// 共通
			dbZxGcsgCtContract.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContractMapper.updateByPrimaryKey(dbZxGcsgCtContract);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContract);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCtContract(List<ZxGcsgCtContract> zxGcsgCtContractList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgCtContractList != null && zxGcsgCtContractList.size() > 0) {
			List<ZxGcsgCtContract> checkList = zxGcsgCtContractMapper
					.getZxGcsgCtContractListByIdList(zxGcsgCtContractList);
			if (checkList.size() > 0) {
				boolean boo = checkList.stream().anyMatch(checkData -> StrUtil.equals("2", checkData.getAuditStatus())
						&& StrUtil.isNotEmpty(checkData.getCtContrApplyId()));
				if (boo) {
					return repEntity.layerMessage("no", "该合同来源于合同评审,不能删除。");
				}
			}
			ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
			zxGcsgCtContract.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContractMapper.batchDeleteUpdateZxGcsgCtContract(zxGcsgCtContractList, zxGcsgCtContract);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCtContractList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxGcsgCtContractDetails(ZxGcsgCtContract zxGcsgCtContract) {
		if (zxGcsgCtContract == null) {
			zxGcsgCtContract = new ZxGcsgCtContract();
		}
		// 获取数据
		ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper
				.selectByPrimaryKey(zxGcsgCtContract.getCtContractId());
		if (dbZxGcsgCtContract != null) {
			// 合同保证金
			ZxGcsgCtCoContractAmtRate contractAmtRate = new ZxGcsgCtCoContractAmtRate();
			contractAmtRate.setCtContractId(dbZxGcsgCtContract.getCtContractId());
			List<ZxGcsgCtCoContractAmtRate> list = zxGcsgCtCoContractAmtRateMapper
					.selectByZxGcsgCtCoContractAmtRateList(contractAmtRate);
			dbZxGcsgCtContract.setCoContractAmtRateList(list);
			// 附件
			ZxGcsgCommonAttachment commonAttachment = new ZxGcsgCommonAttachment();
			commonAttachment.setOtherId(dbZxGcsgCtContract.getCtContractId());
			List<ZxGcsgCommonAttachment> list2 = zxGcsgCommonAttachmentMapper
					.selectByZxGcsgCommonAttachmentList(commonAttachment);
			dbZxGcsgCtContract.setCommonAttachmentList(list2);
		}
		// 数据存在
		if (dbZxGcsgCtContract != null) {
			return repEntity.ok(dbZxGcsgCtContract);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCtContractInfo(ZxGcsgCtContract zxGcsgCtContract) {
		if (zxGcsgCtContract == null) {
			zxGcsgCtContract = new ZxGcsgCtContract();
		}
		int flag = 0;
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// 获取数据
		ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper
				.selectByPrimaryKey(zxGcsgCtContract.getCtContractId());
		if (dbZxGcsgCtContract != null) {
			// dbZxGcsgCtContract.setContractNo(zxGcsgCtContract.getContractNo());
			dbZxGcsgCtContract.setContractName(zxGcsgCtContract.getContractName());
			// 合同类型 0:内部分包 1:专业分包 2:劳务分包 3:总体分包
			dbZxGcsgCtContract.setContractManageType(zxGcsgCtContract.getContractManageType());
			dbZxGcsgCtContract.setFirstId(zxGcsgCtContract.getFirstId());
			// dbZxGcsgCtContract.setFirstName(zxGcsgCtContract.getFirstName());
			dbZxGcsgCtContract.setSecondID(zxGcsgCtContract.getSecondID());
			// dbZxGcsgCtContract.setSecondName(zxGcsgCtContract.getSecondName());
			if (zxGcsgCtContract != null && StrUtil.isNotEmpty(zxGcsgCtContract.getFirstId())) {
				ZxCtContract zxCtContract = new ZxCtContract();
				zxCtContract.setOrgID(zxGcsgCtContract.getFirstId());
				List<ZxCtContract> list = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
				if (list.size() > 0) {
					// 合同甲方好像一直不变
					dbZxGcsgCtContract.setFirstName(list.get(0).getOrgName());
					// 甲方名称
					// dbZxGcsgCtContract.setFirstIdFormula(list.get(0).getOrgName());
				}
			}
			if (zxGcsgCtContract != null && StrUtil.isNotEmpty(zxGcsgCtContract.getSecondID())) {
				ZxCrCustomerExtAttr zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
				zxCrCustomerExtAttr.setZxCrCustomerExtAttrId(zxGcsgCtContract.getSecondID());
				List<ZxCrCustomerExtAttr> list = zxCrCustomerExtAttrMapper
						.selectByZxCrCustomerExtAttrEngineeringList(zxCrCustomerExtAttr);
				if (list.size() > 0) {
					// dbZxGcsgCtContract.setSecondName(list.get(0).getCustomerName());
					// 合同乙方(乙方名称secondName不变,合同乙方更新)
					dbZxGcsgCtContract.setSecondIDFormula(list.get(0).getCustomerName());
				}
			}
			// dbZxGcsgCtContract.setContractCost(zxGcsgCtContract.getContractCost());
			// dbZxGcsgCtContract.setContractCostNoTax(zxGcsgCtContract.getContractCostNoTax());
			// dbZxGcsgCtContract.setContractCostTax(zxGcsgCtContract.getContractCostTax());
			// dbZxGcsgCtContract.setAlterContractSum(zxGcsgCtContract.getAlterContractSum());
			// dbZxGcsgCtContract.setAlterContractSumNoTax(zxGcsgCtContract.getAlterContractSumNoTax());
			// dbZxGcsgCtContract.setAlterContractSumTax(zxGcsgCtContract.getAlterContractSumTax());
			// dbZxGcsgCtContract.setIsDeduct(zxGcsgCtContract.getIsDeduct());
			dbZxGcsgCtContract.setSignatureDate(zxGcsgCtContract.getSignatureDate());
			dbZxGcsgCtContract.setProjectManager(zxGcsgCtContract.getProjectManager());
			dbZxGcsgCtContract.setSecondPrincipal(zxGcsgCtContract.getSecondPrincipal());
			dbZxGcsgCtContract.setFirstUnitTel(zxGcsgCtContract.getFirstUnitTel());
			dbZxGcsgCtContract.setSecondUnitTel(zxGcsgCtContract.getSecondUnitTel());
			dbZxGcsgCtContract.setSecondPartyMobile(zxGcsgCtContract.getSecondPartyMobile());
			dbZxGcsgCtContract.setSecondPartyFax(zxGcsgCtContract.getSecondPartyFax());
			// 0:个人 1:单位 默认空
			dbZxGcsgCtContract.setAgent(zxGcsgCtContract.getAgent());
			dbZxGcsgCtContract.setPlanStartDate(zxGcsgCtContract.getPlanStartDate());
			dbZxGcsgCtContract.setPlanEndDate(zxGcsgCtContract.getPlanEndDate());
			// dbZxGcsgCtContract.setAuditStatus(zxGcsgCtContract.getAuditStatus());
			// 1:执行中 3:终止 默认1
			dbZxGcsgCtContract.setContractStatus(zxGcsgCtContract.getContractStatus());
			// dbZxGcsgCtContract.setSettleType(zxGcsgCtContract.getSettleType());
			dbZxGcsgCtContract.setContent(zxGcsgCtContract.getContent());
			dbZxGcsgCtContract.setRemarks(zxGcsgCtContract.getRemarks());
			dbZxGcsgCtContract.setWtrName(zxGcsgCtContract.getWtrName());
			dbZxGcsgCtContract.setWtrPhone(zxGcsgCtContract.getWtrPhone());
			dbZxGcsgCtContract.setReferenceName(zxGcsgCtContract.getReferenceName());
			dbZxGcsgCtContract.setReferencePhone(zxGcsgCtContract.getReferencePhone());
			dbZxGcsgCtContract.setReferencePost(zxGcsgCtContract.getReferencePost());
			dbZxGcsgCtContract.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContractMapper.updateByPrimaryKey(dbZxGcsgCtContract);
			if (flag != 0) {
				// 合同保证金
				ZxGcsgCtCoContractAmtRate contractAmtRate = new ZxGcsgCtCoContractAmtRate();
				contractAmtRate.setCtContractId(dbZxGcsgCtContract.getCtContractId());
				zxGcsgCtCoContractAmtRateMapper.deleteZxGcsgCtCoContractAmtRateByCondition(contractAmtRate);
				if (zxGcsgCtContract.getCoContractAmtRateList() != null
						&& zxGcsgCtContract.getCoContractAmtRateList().size() > 0) {
					List<ZxGcsgCtCoContractAmtRate> amtRateList = Lists.newArrayList();
					zxGcsgCtContract.getCoContractAmtRateList().stream().forEach(amtRate -> {
						amtRate.setCtCoContractAmtRateId(UuidUtil.generate());
						amtRate.setOrgID(dbZxGcsgCtContract.getProjectId());
						amtRate.setCtContractId(dbZxGcsgCtContract.getCtContractId());
						amtRate.setCreateUserInfo(userKey, realName);
						if (StrUtil.equals("1、质量保证金", amtRate.getStatisticsName())) {
							amtRate.setStatisticsNo("100400");
						} else if (StrUtil.equals("2、安全生产保证金", amtRate.getStatisticsName())) {
							amtRate.setStatisticsNo("100500");
						} else if (StrUtil.equals("3、农民工工资偿付保证金", amtRate.getStatisticsName())) {
							amtRate.setStatisticsNo("100600");
						} else if (StrUtil.equals("4、履约保证金", amtRate.getStatisticsName())) {
							amtRate.setStatisticsNo("100610");
						}
						amtRate.setComID(dbZxGcsgCtContract.getCompanyId());
						amtRate.setComName(dbZxGcsgCtContract.getCompanyName());
						amtRateList.add(amtRate);
					});
					flag = zxGcsgCtCoContractAmtRateMapper.batchInsertZxGcsgCtCoContractAmtRate(amtRateList);
				}
				// 附件
				ZxGcsgCommonAttachment commonAttachment = new ZxGcsgCommonAttachment();
				commonAttachment.setOtherId(dbZxGcsgCtContract.getCtContractId());
				zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(commonAttachment);
				if (zxGcsgCtContract.getCommonAttachmentList() != null
						&& zxGcsgCtContract.getCommonAttachmentList().size() > 0) {
					List<ZxGcsgCommonAttachment> attachmentList = Lists.newArrayList();
					zxGcsgCtContract.getCommonAttachmentList().stream().forEach(attachment -> {
						attachment.setUid(UuidUtil.generate());
						attachment.setOtherId(dbZxGcsgCtContract.getCtContractId());
						attachment.setFileType("0");
						attachment.setCreateUserInfo(userKey, realName);
						attachmentList.add(attachment);
					});
					flag = zxGcsgCommonAttachmentMapper.batchInsertZxGcsgCommonAttachment(attachmentList);
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContract);
		}
	}

	@Override
	public ResponseEntity resumeZxGcsgCtContractStatus(ZxGcsgCtContract zxGcsgCtContract) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 规则：点击此按钮后，合同状态变为“执行中”，执行中的合同可以在做结算中选择到该合同编号；
		// 合同状态为“终止”时，无法选择到此合同编号。
		// 注意：合同建立后该项值默认为“执行中”，当【完工审核】模块中，此合同所在项目的项目状态为完工时，
		// 该项值变为“终止”，也就无法再建立结算数据。
		ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper
				.selectByPrimaryKey(zxGcsgCtContract.getCtContractId());
		if (dbZxGcsgCtContract != null) {
			// 合同状态 1:执行中 3:终止 默认1
			// dbZxGcsgCtContract.setContractStatus("1");
			// 共通
			dbZxGcsgCtContract.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContractMapper.updateByPrimaryKey(dbZxGcsgCtContract);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContract);
		}
	}

	@Override
	public void glExportZxGcsgCtContractExcel(ZxGcsgCtContract zxGcsgCtContract, HttpServletResponse response) {
		if (zxGcsgCtContract == null) {
			zxGcsgCtContract = new ZxGcsgCtContract();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		String userId = TokenUtils.getUserId(request);
		// 集团全部可见
		if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
			zxGcsgCtContract.setCompanyId("");
			zxGcsgCtContract.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxGcsgCtContract.setCompanyId(zxGcsgCtContract.getCompanyId());
			zxGcsgCtContract.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxGcsgCtContract.setCompanyId("");
			zxGcsgCtContract.setProjectId(zxGcsgCtContract.getProjectId());
		}
		// 获取数据
		List<ZxGcsgCtContract> dataList = zxGcsgCtContractMapper.selectByZxGcsgCtContractList(zxGcsgCtContract);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("合同序号", "合同编号", "合同名称", "合同类型", "合同乙方", "含税合同总价(万元)", "变更后含税合同金额(万元)",
				"是否抵扣", "签订日期", "乙方代表", "结算情况", "乙方名称");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				ZxGcsgCtContract dbData = dataList.get(i);
				// 合同类型 0:内部分包 1:专业分包 2:劳务分包 3:总体分包
				String contractManageType = dbData.getContractManageType();
				switch (contractManageType) {
				case "0":
					contractManageType = "内部分包";
					break;
				case "1":
					contractManageType = "专业分包";
					break;
				case "2":
					contractManageType = "劳务分包";
					break;
				case "3":
					contractManageType = "总体分包";
					break;
				default:
					break;
				}
				// 合同甲方--firstName 合同乙方--secondIDFormula
				// 甲方名称--firstIdFormula 乙方名称--secondName
				String isDeduct = StrUtil.equals("1", dbData.getIsDeduct()) ? "是" : "否";
				rowsList.add(CollUtil.newArrayList(i + 1, dbData.getContractNo(), dbData.getContractName(),
						contractManageType, dbData.getSecondIDFormula(), dbData.getContractCost(),
						dbData.getAlterContractSum(), isDeduct,
						DateUtil.format(dbData.getSignatureDate(), "yyyy-MM-dd"), dbData.getSecondPrincipal(),
						dbData.getSettleType(), dbData.getSecondName()));
			}
		}
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		// 通过工具类创建writer，创建xlsx格式
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// response为HttpServletResponse对象
		// 设置response下载弹窗
		// response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLUtil.encode("工程施工合同管理列表.xlsx", "UTF-8"));
		// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out为OutputStream，需要写出到的目标流
		ServletOutputStream out = null;
		try {
			// response.setHeader("Content-Disposition",
			// "attachment; filename=\"" + new String("工程施工合同管理列表".getBytes("utf-8"),
			// "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, "工程施工合同管理列表");
			// 一次性写出内容，使用默认样式，强制输出标题
			writer.write(rowsList, true);
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭writer，释放内存
			if (writer != null) {
				writer.close();
			}
			// 此处记得关闭输出Servlet流
			if (out != null) {
				IoUtil.close(out);
			}
		}
	}

}
