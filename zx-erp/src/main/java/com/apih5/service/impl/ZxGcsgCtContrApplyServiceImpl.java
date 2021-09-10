package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrCustomerExtAttrMapper;
import com.apih5.mybatis.dao.ZxCtCloudEcoMapper;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxGcsgCcCoAlterMapper;
import com.apih5.mybatis.dao.ZxGcsgCcCoAlterWorkMapper;
import com.apih5.mybatis.dao.ZxGcsgCcWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCommonAttachmentMapper;
import com.apih5.mybatis.dao.ZxGcsgCtCoContractAmtRateMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrApplyMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrApplyWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrProcessGuajieMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContractMapper;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysItemMapper;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxCtCloudEco;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlter;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApplyWorks;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSysItem;
import com.apih5.service.ZxGcsgCtContrApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zxGcsgCtContrApplyService")
public class ZxGcsgCtContrApplyServiceImpl implements ZxGcsgCtContrApplyService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCtContrApplyMapper zxGcsgCtContrApplyMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrApplyWorksMapper zxGcsgCtContrApplyWorksMapper;
	@Autowired(required = true)
	private ZxGcsgCtPriceSysMapper zxGcsgCtPriceSysMapper;
	@Autowired(required = true)
	private ZxGcsgCtContractMapper zxGcsgCtContractMapper;
	@Autowired(required = true)
	private ZxGcsgCcWorksMapper zxGcsgCcWorksMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrProcessGuajieMapper zxGcsgCtContrProcessGuajieMapper;
	@Autowired(required = true)
	private ZxGcsgCtPriceSysItemMapper zxGcsgCtPriceSysItemMapper;
	@Autowired(required = true)
	private ZxGcsgCcCoAlterMapper zxGcsgCcCoAlterMapper;
	@Autowired(required = true)
	private ZxGcsgCcCoAlterWorkMapper zxGcsgCcCoAlterWorkMapper;
	@Autowired(required = true)
	private ZxGcsgCommonAttachmentMapper zxGcsgCommonAttachmentMapper;
	@Autowired(required = true)
	private ZxCtContractMapper zxCtContractMapper;
	@Autowired(required = true)
	private ZxCrCustomerExtAttrMapper zxCrCustomerExtAttrMapper;
	@Autowired(required = true)
	private ZxCtCloudEcoMapper zxCtCloudEcoMapper;
	@Autowired(required = true)
	private ZxGcsgCtCoContractAmtRateMapper zxGcsgCtCoContractAmtRateMapper;

	@Override
	public ResponseEntity getZxGcsgCtContrApplyListByCondition(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		// 分页查询
		PageHelper.startPage(zxGcsgCtContrApply.getPage(), zxGcsgCtContrApply.getLimit());
		// 获取数据
		List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList = zxGcsgCtContrApplyMapper
				.selectByZxGcsgCtContrApplyList(zxGcsgCtContrApply);
		// 得到分页信息
		PageInfo<ZxGcsgCtContrApply> p = new PageInfo<>(zxGcsgCtContrApplyList);

		return repEntity.okList(zxGcsgCtContrApplyList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyDetail(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		// 获取数据
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		// 数据存在
		if (dbZxGcsgCtContrApply != null) {
			return repEntity.ok(dbZxGcsgCtContrApply);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCtContrApply.setCtContrApplyId(UuidUtil.generate());
		zxGcsgCtContrApply.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCtContrApplyMapper.insert(zxGcsgCtContrApply);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApply);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null && StrUtil.isNotEmpty(dbZxGcsgCtContrApply.getCtContrApplyId())) {
			// 合同编号
			dbZxGcsgCtContrApply.setContractNo(zxGcsgCtContrApply.getContractNo());
			// 合同名称
			dbZxGcsgCtContrApply.setContractName(zxGcsgCtContrApply.getContractName());
			// 合同类型
			dbZxGcsgCtContrApply.setContractType(zxGcsgCtContrApply.getContractType());
			// 含税合同金额(万元)
			dbZxGcsgCtContrApply.setContractCost(zxGcsgCtContrApply.getContractCost());
			// 甲方ID
			dbZxGcsgCtContrApply.setFirstID(zxGcsgCtContrApply.getFirstID());
			// 甲方名称
			dbZxGcsgCtContrApply.setFirstName(zxGcsgCtContrApply.getFirstName());
			// 乙方ID
			dbZxGcsgCtContrApply.setSecondID(zxGcsgCtContrApply.getSecondID());
			// 乙方名称
			dbZxGcsgCtContrApply.setSecondName(zxGcsgCtContrApply.getSecondName());
			// 开工日期
			dbZxGcsgCtContrApply.setStartDate(zxGcsgCtContrApply.getStartDate());
			// 竣工日期
			dbZxGcsgCtContrApply.setEndDate(zxGcsgCtContrApply.getEndDate());
			// 合同工期
			dbZxGcsgCtContrApply.setCsTimeLimit(zxGcsgCtContrApply.getCsTimeLimit());
			// 合同签定人
			dbZxGcsgCtContrApply.setAgent(zxGcsgCtContrApply.getAgent());
			// 合同内容
			dbZxGcsgCtContrApply.setContent(zxGcsgCtContrApply.getContent());
			// 发起人
			dbZxGcsgCtContrApply.setBeginPer(zxGcsgCtContrApply.getBeginPer());
			// 评审状态
			dbZxGcsgCtContrApply.setStatus(zxGcsgCtContrApply.getStatus());
			// 废弃字段
			dbZxGcsgCtContrApply.setCombProp(zxGcsgCtContrApply.getCombProp());
			// pp1
			dbZxGcsgCtContrApply.setPp1(zxGcsgCtContrApply.getPp1());
			// pp2
			dbZxGcsgCtContrApply.setPp2(zxGcsgCtContrApply.getPp2());
			// 补充协议名称
			dbZxGcsgCtContrApply.setPp3(zxGcsgCtContrApply.getPp3());
			// 补充协议本期含税增减金额(万元)
			dbZxGcsgCtContrApply.setPp4(zxGcsgCtContrApply.getPp4());
			// 补充协议清单ID CoAlterBO
			dbZxGcsgCtContrApply.setPp5(zxGcsgCtContrApply.getPp5());
			// 补充协议合同管理ID
			dbZxGcsgCtContrApply.setPp6(zxGcsgCtContrApply.getPp6());
			// pp7
			dbZxGcsgCtContrApply.setPp7(zxGcsgCtContrApply.getPp7());
			// 清单
			dbZxGcsgCtContrApply.setPp8(zxGcsgCtContrApply.getPp8());
			// pp9
			dbZxGcsgCtContrApply.setPp9(zxGcsgCtContrApply.getPp9());
			// pp10
			dbZxGcsgCtContrApply.setPp10(zxGcsgCtContrApply.getPp10());
			// 补充协议名称(pp3)
			dbZxGcsgCtContrApply.setAgreementName(zxGcsgCtContrApply.getAgreementName());
			// 补充协议本期含税增减金额(万元)(pp4)
			dbZxGcsgCtContrApply.setCurrentTaxAmount(zxGcsgCtContrApply.getCurrentTaxAmount());
			// 补充协议清单ID CoAlterBO(pp5)
			dbZxGcsgCtContrApply.setCcCoAlterId(zxGcsgCtContrApply.getCcCoAlterId());
			// 补充协议合同管理ID(pp6)
			dbZxGcsgCtContrApply.setCtContractId(zxGcsgCtContrApply.getCtContractId());
			// 清单(pp8)
			dbZxGcsgCtContrApply.setWorksId(zxGcsgCtContrApply.getWorksId());
			// 最后编辑时间
			dbZxGcsgCtContrApply.setEditTime(zxGcsgCtContrApply.getEditTime());
			// 流程实例ID
			dbZxGcsgCtContrApply.setInstProcessId(zxGcsgCtContrApply.getInstProcessId());
			// 公文任务ID
			dbZxGcsgCtContrApply.setWorkitemID(zxGcsgCtContrApply.getWorkitemID());
			// name
			dbZxGcsgCtContrApply.setCode(zxGcsgCtContrApply.getCode());
			// 机构编码
			dbZxGcsgCtContrApply.setCode1(zxGcsgCtContrApply.getCode1());
			// 承建单位简称
			dbZxGcsgCtContrApply.setCode2(zxGcsgCtContrApply.getCode2());
			// 中标单位简称
			dbZxGcsgCtContrApply.setCode3(zxGcsgCtContrApply.getCode3());
			// 项目所在省份简称
			dbZxGcsgCtContrApply.setCode4(zxGcsgCtContrApply.getCode4());
			// 项目简称
			dbZxGcsgCtContrApply.setCode5(zxGcsgCtContrApply.getCode5());
			// 标段号
			dbZxGcsgCtContrApply.setCode6(zxGcsgCtContrApply.getCode6());
			// 合同类别
			dbZxGcsgCtContrApply.setCode7(zxGcsgCtContrApply.getCode7());
			// 合同序号
			dbZxGcsgCtContrApply.setCode8(zxGcsgCtContrApply.getCode8());
			// 业主合同编码
			dbZxGcsgCtContrApply.setCodeP1(zxGcsgCtContrApply.getCodeP1());
			// 同一公司
			dbZxGcsgCtContrApply.setCode2T3(zxGcsgCtContrApply.getCode2T3());
			// 评审开始时间
			dbZxGcsgCtContrApply.setFlowBeginDate(zxGcsgCtContrApply.getFlowBeginDate());
			// 评审结束时间
			dbZxGcsgCtContrApply.setFlowEndDate(zxGcsgCtContrApply.getFlowEndDate());
			// 变更后含税金额
			dbZxGcsgCtContrApply.setAlterContractSum(zxGcsgCtContrApply.getAlterContractSum());
			// 是否局审批
			dbZxGcsgCtContrApply.setIsFlag(zxGcsgCtContrApply.getIsFlag());
			// 是否发送到局机关审批
			dbZxGcsgCtContrApply.setIsReport(zxGcsgCtContrApply.getIsReport());
			// 局历史流程ID
			dbZxGcsgCtContrApply.setAppInsHistID(zxGcsgCtContrApply.getAppInsHistID());
			// 发送局判断ID
			dbZxGcsgCtContrApply.setSendToJuID(zxGcsgCtContrApply.getSendToJuID());
			// 物资来源
			dbZxGcsgCtContrApply.setMaterialSource(zxGcsgCtContrApply.getMaterialSource());
			// 上期末变更后金额
			dbZxGcsgCtContrApply.setUpAlterContractSum(zxGcsgCtContrApply.getUpAlterContractSum());
			// 现场负责人
			dbZxGcsgCtContrApply.setLegalPerson(zxGcsgCtContrApply.getLegalPerson());
			// 委托代理人
			dbZxGcsgCtContrApply.setAgentPerson(zxGcsgCtContrApply.getAgentPerson());
			// 法定代表人
			dbZxGcsgCtContrApply.setChargePerson(zxGcsgCtContrApply.getChargePerson());
			// 是否局指审批
			dbZxGcsgCtContrApply.setIsFlagZhb(zxGcsgCtContrApply.getIsFlagZhb());
			// 是否发送到局机关审批
			dbZxGcsgCtContrApply.setIsReportZhb(zxGcsgCtContrApply.getIsReportZhb());
			// 发送局指判断ID
			dbZxGcsgCtContrApply.setSendToZhbID(zxGcsgCtContrApply.getSendToZhbID());
			// 局指挥部历史流程ID
			dbZxGcsgCtContrApply.setAppInsHistIDZhb(zxGcsgCtContrApply.getAppInsHistIDZhb());
			// 不含税合同金额(万元)
			dbZxGcsgCtContrApply.setContractCostNoTax(zxGcsgCtContrApply.getContractCostNoTax());
			// 税率(%)
			dbZxGcsgCtContrApply.setTaxRate(zxGcsgCtContrApply.getTaxRate());
			// 变更后不含税金额
			dbZxGcsgCtContrApply.setAlterContractSumNoTax(zxGcsgCtContrApply.getAlterContractSumNoTax());
			// 变更后税额
			dbZxGcsgCtContrApply.setAlterContractSumTax(zxGcsgCtContrApply.getAlterContractSumTax());
			// 本期不含税增减金额(万元)
			dbZxGcsgCtContrApply.setPp4NoTax(zxGcsgCtContrApply.getPp4NoTax());
			// 本期增减税额(万元)
			dbZxGcsgCtContrApply.setPp4Tax(zxGcsgCtContrApply.getPp4Tax());
			// 是否抵扣
			dbZxGcsgCtContrApply.setIsDeduct(zxGcsgCtContrApply.getIsDeduct());
			// 原合同ID--附属合同应用
			dbZxGcsgCtContrApply.setFromContractID(zxGcsgCtContrApply.getFromContractID());
			// 原合同编号--附属合同应用
			dbZxGcsgCtContrApply.setFromContractNo(zxGcsgCtContrApply.getFromContractNo());
			// 原合同名称--附属合同应用
			dbZxGcsgCtContrApply.setFromContractName(zxGcsgCtContrApply.getFromContractName());
			// 合同税额(万元)
			dbZxGcsgCtContrApply.setContractCostTax(zxGcsgCtContrApply.getContractCostTax());
			// 授权委托人姓名
			dbZxGcsgCtContrApply.setWtrName(zxGcsgCtContrApply.getWtrName());
			// 授权委托人身份证号
			dbZxGcsgCtContrApply.setWtrPhone(zxGcsgCtContrApply.getWtrPhone());
			// 推荐人姓名
			dbZxGcsgCtContrApply.setReferenceName(zxGcsgCtContrApply.getReferenceName());
			// 推荐人职务
			dbZxGcsgCtContrApply.setReferencePost(zxGcsgCtContrApply.getReferencePost());
			// 推荐人电话
			dbZxGcsgCtContrApply.setReferencePhone(zxGcsgCtContrApply.getReferencePhone());
			// 上期末变更后金额不含税
			dbZxGcsgCtContrApply.setUpAlterContractSumNoTax(zxGcsgCtContrApply.getUpAlterContractSumNoTax());
			// comID
			dbZxGcsgCtContrApply.setComID(zxGcsgCtContrApply.getComID());
			// 上期末变更后税额
			dbZxGcsgCtContrApply.setUpAlterContractSumTax(zxGcsgCtContrApply.getUpAlterContractSumTax());
			// 财务系统ID
			dbZxGcsgCtContrApply.setFiId(zxGcsgCtContrApply.getFiId());
			// 核算部门名称
			dbZxGcsgCtContrApply.setActDepartment(zxGcsgCtContrApply.getActDepartment());
			// 核算部门名称编码
			dbZxGcsgCtContrApply.setActDepartmentBM(zxGcsgCtContrApply.getActDepartmentBM());
			// 核算部门名称ID
			dbZxGcsgCtContrApply.setActDepartmentID(zxGcsgCtContrApply.getActDepartmentID());
			// 名义投标单位ID
			dbZxGcsgCtContrApply.setBiddersId(zxGcsgCtContrApply.getBiddersId());
			// 名义投标单位编号
			dbZxGcsgCtContrApply.setBiddersCode(zxGcsgCtContrApply.getBiddersCode());
			// 名义投标单位
			dbZxGcsgCtContrApply.setBiddersName(zxGcsgCtContrApply.getBiddersName());
			// 核算单位ID
			dbZxGcsgCtContrApply.setAccountUnitId(zxGcsgCtContrApply.getAccountUnitId());
			// 核算单位编号
			dbZxGcsgCtContrApply.setAccountUnitCode(zxGcsgCtContrApply.getAccountUnitCode());
			// 核算单位
			dbZxGcsgCtContrApply.setAccountUnitName(zxGcsgCtContrApply.getAccountUnitName());
			// 核算项目Id
			dbZxGcsgCtContrApply.setAccountProjId(zxGcsgCtContrApply.getAccountProjId());
			// 核算项目编号
			dbZxGcsgCtContrApply.setAccountProjCode(zxGcsgCtContrApply.getAccountProjCode());
			// 核算项目名称
			dbZxGcsgCtContrApply.setAccountProjName(zxGcsgCtContrApply.getAccountProjName());
			// 项目属地
			dbZxGcsgCtContrApply.setProjSite(zxGcsgCtContrApply.getProjSite());
			// 业务板块
			dbZxGcsgCtContrApply.setBusiSegments(zxGcsgCtContrApply.getBusiSegments());
			// 项目资金来源
			dbZxGcsgCtContrApply.setProjFundsSource(zxGcsgCtContrApply.getProjFundsSource());
			// 事业部
			dbZxGcsgCtContrApply.setDivision(zxGcsgCtContrApply.getDivision());
			// 税务备案号(名义投标单位/中标单位)
			dbZxGcsgCtContrApply.setTaxFilingCode(zxGcsgCtContrApply.getTaxFilingCode());
			// 计税方法
			dbZxGcsgCtContrApply.setTaxCountWay(zxGcsgCtContrApply.getTaxCountWay());
			// 增值税预征率(%)
			dbZxGcsgCtContrApply.setTaxAdvanceRate(zxGcsgCtContrApply.getTaxAdvanceRate());
			// 增值税使用税率(%)
			dbZxGcsgCtContrApply.setTaxUseWay(zxGcsgCtContrApply.getTaxUseWay());
			// 是否属地预缴
			dbZxGcsgCtContrApply.setPrepaidLand(zxGcsgCtContrApply.getPrepaidLand());
			// 预缴国税机关
			dbZxGcsgCtContrApply.setNationalTax(zxGcsgCtContrApply.getNationalTax());
			// 预缴国税机关联系方式
			dbZxGcsgCtContrApply.setNationalTaxTel(zxGcsgCtContrApply.getNationalTaxTel());
			// 预交国税机关地址
			dbZxGcsgCtContrApply.setNationalTaxAdds(zxGcsgCtContrApply.getNationalTaxAdds());
			// 项目部通讯地址
			dbZxGcsgCtContrApply.setProjDepAdds(zxGcsgCtContrApply.getProjDepAdds());
			// 项目部邮编
			dbZxGcsgCtContrApply.setProjDepZipCode(zxGcsgCtContrApply.getProjDepZipCode());
			// 项目部电话
			dbZxGcsgCtContrApply.setProjDepTel(zxGcsgCtContrApply.getProjDepTel());
			// 项目部传真
			dbZxGcsgCtContrApply.setProjDepFax(zxGcsgCtContrApply.getProjDepFax());
			// 项目阶段
			dbZxGcsgCtContrApply.setProjStage(zxGcsgCtContrApply.getProjStage());
			// 项目经理固话
			dbZxGcsgCtContrApply.setPmFixedLine(zxGcsgCtContrApply.getPmFixedLine());
			// 项目经理邮箱
			dbZxGcsgCtContrApply.setPmMail(zxGcsgCtContrApply.getPmMail());
			// 财务负责人
			dbZxGcsgCtContrApply.setFiCharge(zxGcsgCtContrApply.getFiCharge());
			// 财务负责人手机
			dbZxGcsgCtContrApply.setFiTel(zxGcsgCtContrApply.getFiTel());
			// 财务负责人固话
			dbZxGcsgCtContrApply.setFiFixedLine(zxGcsgCtContrApply.getFiFixedLine());
			// 财务负责人邮箱
			dbZxGcsgCtContrApply.setFiMail(zxGcsgCtContrApply.getFiMail());
			// 合约负责人
			dbZxGcsgCtContrApply.setCtrCharge(zxGcsgCtContrApply.getCtrCharge());
			// 合约负责人手机
			dbZxGcsgCtContrApply.setCtrTel(zxGcsgCtContrApply.getCtrTel());
			// 合约负责人固话
			dbZxGcsgCtContrApply.setCtrFixedLine(zxGcsgCtContrApply.getCtrFixedLine());
			// 合约负责人邮箱
			dbZxGcsgCtContrApply.setCtrMail(zxGcsgCtContrApply.getCtrMail());
			// 债权清收负责人
			dbZxGcsgCtContrApply.setDcLeader(zxGcsgCtContrApply.getDcLeader());
			// 债权清收负责人手机
			dbZxGcsgCtContrApply.setDcTel(zxGcsgCtContrApply.getDcTel());
			// 债权清收负责人固话
			dbZxGcsgCtContrApply.setDcFixedLine(zxGcsgCtContrApply.getDcFixedLine());
			// 债权清收负责人邮箱
			dbZxGcsgCtContrApply.setDcMail(zxGcsgCtContrApply.getDcMail());
			// 复核日期
			dbZxGcsgCtContrApply.setDoubleCheckDate(zxGcsgCtContrApply.getDoubleCheckDate());
			// 录入日期
			dbZxGcsgCtContrApply.setWriteDate(zxGcsgCtContrApply.getWriteDate());
			// 核算部门id
			dbZxGcsgCtContrApply.setAccountDepId(zxGcsgCtContrApply.getAccountDepId());
			// 核算部门编号
			dbZxGcsgCtContrApply.setAccountDepCode(zxGcsgCtContrApply.getAccountDepCode());
			// 核算部门
			dbZxGcsgCtContrApply.setAccountDepName(zxGcsgCtContrApply.getAccountDepName());
			// 往来单位编号
			dbZxGcsgCtContrApply.setSecondIDCode(zxGcsgCtContrApply.getSecondIDCode());
			// 录入人
			dbZxGcsgCtContrApply.setWriter(zxGcsgCtContrApply.getWriter());
			// 系统编号
			dbZxGcsgCtContrApply.setSystemNo(zxGcsgCtContrApply.getSystemNo());
			// 币种编号
			dbZxGcsgCtContrApply.setCurrencyCode(zxGcsgCtContrApply.getCurrencyCode());
			// 合同性质
			dbZxGcsgCtContrApply.setCtrNature(zxGcsgCtContrApply.getCtrNature());
			// 合同更新状态
			dbZxGcsgCtContrApply.setCtrUpdateState(zxGcsgCtContrApply.getCtrUpdateState());
			// 财务合同状态
			dbZxGcsgCtContrApply.setFiCtrState(zxGcsgCtContrApply.getFiCtrState());
			// 收支方向
			dbZxGcsgCtContrApply.setRevenueDir(zxGcsgCtContrApply.getRevenueDir());
			// 发票类型
			dbZxGcsgCtContrApply.setInvoiceType(zxGcsgCtContrApply.getInvoiceType());
			// 制式合同
			dbZxGcsgCtContrApply.setStaCtr(zxGcsgCtContrApply.getStaCtr());
			// 重点合同
			dbZxGcsgCtContrApply.setKeyCtr(zxGcsgCtContrApply.getKeyCtr());
			// orgID
			dbZxGcsgCtContrApply.setOrgID(zxGcsgCtContrApply.getOrgID());
			// notDisplay
			dbZxGcsgCtContrApply.setNotDisplay(zxGcsgCtContrApply.getNotDisplay());
			// 复核人
			dbZxGcsgCtContrApply.setDoubleCheckPerson(zxGcsgCtContrApply.getDoubleCheckPerson());
			// 往来单位编号
			dbZxGcsgCtContrApply.setSecondIDCodeBh(zxGcsgCtContrApply.getSecondIDCodeBh());
			// auditWorkitemID
			dbZxGcsgCtContrApply.setAuditWorkitemID(zxGcsgCtContrApply.getAuditWorkitemID());
			// auditSys
			dbZxGcsgCtContrApply.setAuditSys(zxGcsgCtContrApply.getAuditSys());
			// 往来单位名称
			dbZxGcsgCtContrApply.setSecondIDCodeName(zxGcsgCtContrApply.getSecondIDCodeName());
			// 管理机构
			dbZxGcsgCtContrApply.setOrgName(zxGcsgCtContrApply.getOrgName());
			// 协作单位类型
			dbZxGcsgCtContrApply.setSecondOrgType(zxGcsgCtContrApply.getSecondOrgType());
			// firstID对应OA机构ID
			dbZxGcsgCtContrApply.setFirstOAorgID(zxGcsgCtContrApply.getFirstOAorgID());
			// 是否通过云电商进行招标
			dbZxGcsgCtContrApply.setIsBiddedOnCloud(zxGcsgCtContrApply.getIsBiddedOnCloud());
			// 招标方式
			dbZxGcsgCtContrApply.setBidType(zxGcsgCtContrApply.getBidType());
			// 云电商招标方案编号
			dbZxGcsgCtContrApply.setCloudBidNo(zxGcsgCtContrApply.getCloudBidNo());
			// 组织招标主体
			dbZxGcsgCtContrApply.setBidOrgType(zxGcsgCtContrApply.getBidOrgType());
			// 各单位主管部门是否参与
			dbZxGcsgCtContrApply.setIsAllDepartJoin(zxGcsgCtContrApply.getIsAllDepartJoin());
			// 参与方式
			dbZxGcsgCtContrApply.setJoinType(zxGcsgCtContrApply.getJoinType());
			// 各单位主管部门是否参与评标
			dbZxGcsgCtContrApply.setIsDepartJoinBid(zxGcsgCtContrApply.getIsDepartJoinBid());
			// 合同所属事业部
			dbZxGcsgCtContrApply.setContractDepart(zxGcsgCtContrApply.getContractDepart());
			// 包件编号
			dbZxGcsgCtContrApply.setPackageNo(zxGcsgCtContrApply.getPackageNo());
			// 包件ID
			dbZxGcsgCtContrApply.setCloudEcoID(zxGcsgCtContrApply.getCloudEcoID());
			// 投资合同编号
			dbZxGcsgCtContrApply.setInvestContractNo(zxGcsgCtContrApply.getInvestContractNo());
			// 物资类别ID
			dbZxGcsgCtContrApply.setResCategoryID(zxGcsgCtContrApply.getResCategoryID());
			// 物资类别
			dbZxGcsgCtContrApply.setResCategoryName(zxGcsgCtContrApply.getResCategoryName());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField1(zxGcsgCtContrApply.getOpinionField1());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField2(zxGcsgCtContrApply.getOpinionField2());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField3(zxGcsgCtContrApply.getOpinionField3());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField4(zxGcsgCtContrApply.getOpinionField4());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField5(zxGcsgCtContrApply.getOpinionField5());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField6(zxGcsgCtContrApply.getOpinionField6());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField7(zxGcsgCtContrApply.getOpinionField7());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField8(zxGcsgCtContrApply.getOpinionField8());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField9(zxGcsgCtContrApply.getOpinionField9());
			// 备注
			dbZxGcsgCtContrApply.setOpinionField10(zxGcsgCtContrApply.getOpinionField10());
			// 流程id
			dbZxGcsgCtContrApply.setApih5FlowId(zxGcsgCtContrApply.getApih5FlowId());
			// work_id
			dbZxGcsgCtContrApply.setWorkId(zxGcsgCtContrApply.getWorkId());
			// 工序审核状态
			dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
			// 流程状态
			dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
			// 备注
			dbZxGcsgCtContrApply.setRemarks(zxGcsgCtContrApply.getRemarks());
			// 排序
			dbZxGcsgCtContrApply.setSort(zxGcsgCtContrApply.getSort());
			// 共通
			dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApply);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCtContrApply(List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String token = TokenUtils.getToken(request);
		int flag = 0;
		JSONArray deleteFlowJson = JSONUtil.createArray();
		if (zxGcsgCtContrApplyList != null && zxGcsgCtContrApplyList.size() > 0) {
			// check流程123状态不可以删除
			for (ZxGcsgCtContrApply check : zxGcsgCtContrApplyList) {
				if (StrUtil.equals("1", check.getApih5FlowStatus()) || StrUtil.equals("2", check.getApih5FlowStatus())
						|| StrUtil.equals("3", check.getApih5FlowStatus())) {
					return repEntity.layerMessage("no", "该数据不允许删除,请检查数据当前审核状态。");
				}
			}
			ZxGcsgCtContrApply zxGcsgCtContrApply = new ZxGcsgCtContrApply();
			zxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyMapper.batchDeleteUpdateZxGcsgCtContrApply(zxGcsgCtContrApplyList,
					zxGcsgCtContrApply);
			if (flag != 0) {
				zxGcsgCtContrApplyList.stream().filter(f -> StrUtil.equals("0", f.getApih5FlowStatus()))
						.forEach(delete -> {
							deleteFlowJson.add(delete.getWorkId());
						});
			}
		}
		// 待提交状态的数据需要删除流程后台数据
		if (deleteFlowJson.size() > 0) {
			HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", deleteFlowJson.toString(), token);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCtContrApplyList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity psAddZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// check
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getFirstID())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApply.getContractNo())) {
			int count = zxGcsgCtContrApplyMapper.checkZxGcsgCtContrApplyRepetitiveNo(zxGcsgCtContrApply);
			if (count > 0) {
				return repEntity.layerMessage("no", "该合同评审的编号已存在,请检查后重试。");
			}
		}
		zxGcsgCtContrApply.setCtContrApplyId(UuidUtil.generate());
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getFirstID())) {
			ZxCtContract zxCtContract = new ZxCtContract();
			zxCtContract.setOrgID(zxGcsgCtContrApply.getFirstID());
			List<ZxCtContract> list = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
			if (list.size() > 0) {
				zxGcsgCtContrApply.setFirstName(list.get(0).getOrgName());
				// 甲方信息获取权限字段
				zxGcsgCtContrApply.setProjectId(list.get(0).getOrgID());
				zxGcsgCtContrApply.setProjectName(list.get(0).getOrgName());
				zxGcsgCtContrApply.setCompanyId(list.get(0).getCompanyId());
				zxGcsgCtContrApply.setCompanyName(list.get(0).getCompanyName());
			}
		}
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getSecondID())) {
			ZxCrCustomerExtAttr zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
			zxCrCustomerExtAttr.setZxCrCustomerExtAttrId(zxGcsgCtContrApply.getSecondID());
			List<ZxCrCustomerExtAttr> list = zxCrCustomerExtAttrMapper
					.selectByZxCrCustomerExtAttrEngineeringList(zxCrCustomerExtAttr);
			if (list.size() > 0) {
				zxGcsgCtContrApply.setSecondName(list.get(0).getCustomerName());
			}
		}
		zxGcsgCtContrApply.setSecondOrgType("施工协作单位");
		zxGcsgCtContrApply.setApih5FlowStatus("-1");
		if (StrUtil.equals("1", zxGcsgCtContrApply.getIsBiddedOnCloud())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApply.getCloudEcoID())) {
			ZxCtCloudEco dbZxCtCloudEco = zxCtCloudEcoMapper
					.getZxCtCloudEcoByPrimaryKey(zxGcsgCtContrApply.getCloudEcoID());
			if (dbZxCtCloudEco != null) {
				// 是否匹配已返回给前端,由前端check
				// check该包件编号未完全匹配!请在oa系统中将该包件编号对应的投标单位全部进行入库
				if (!StrUtil.equals("1", dbZxCtCloudEco.getIsRela())) {
					return repEntity.layerMessage("no", "该包件编号未完全匹配!请在oa系统中将该包件编号对应的投标单位全部进行入库。");
				}
				zxGcsgCtContrApply.setPackageNo(dbZxCtCloudEco.getPackageNo());
				zxGcsgCtContrApply.setCloudBidNo(dbZxCtCloudEco.getSchemeNo());
			}
		}
		zxGcsgCtContrApply.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCtContrApplyMapper.insert(zxGcsgCtContrApply);
		if (flag != 0) {
			// 一、生成合同评审清单表顶级父节点
			ZxGcsgCtContrApplyWorks applyWorksRootNode = new ZxGcsgCtContrApplyWorks();
			// 顶级节点清单ID前缀 D,前缀+清单书ID
			// public final static String TOPNODEPREFIX = "work";
			applyWorksRootNode.setCtContrApplyWorksId(UuidUtil.generate());
			applyWorksRootNode.setParentID("-1");
			applyWorksRootNode.setTreeNode("1000");
			// 责任中心取自合同甲方ID
			applyWorksRootNode.setOrgID(zxGcsgCtContrApply.getFirstID());
			applyWorksRootNode.setCtContrApplyId(zxGcsgCtContrApply.getCtContrApplyId());
			// { "10", "合同清单" },{ "11", "管理费清单" }, { "20", "拆分清单" } ;
			applyWorksRootNode.setWorkType(10);
			applyWorksRootNode.setWorkNo("-");
			applyWorksRootNode.setWorkName(zxGcsgCtContrApply.getContractName());
			applyWorksRootNode.setContractPrice(BigDecimal.ZERO);
			applyWorksRootNode.setContractPriceNoTax(BigDecimal.ZERO);
			applyWorksRootNode.setContractQty(BigDecimal.ZERO);
			applyWorksRootNode.setContractAmt(BigDecimal.ZERO);
			applyWorksRootNode.setQuantity(BigDecimal.ZERO);
			applyWorksRootNode.setPrice(BigDecimal.ZERO);
			applyWorksRootNode.setPriceNoTax(BigDecimal.ZERO);
			applyWorksRootNode.setDeleted(0);
			// 0:非叶子节点
			applyWorksRootNode.setIsLeaf(0);
			// 0正常 1 新增 2 修改 3 删除 老系统此时为传0
			applyWorksRootNode.setExsitStatus(1);
			applyWorksRootNode.setIsAssignable(0);
			applyWorksRootNode.setEditTime(DateUtil.date());
			applyWorksRootNode.setCheckQty(BigDecimal.ZERO);
			applyWorksRootNode.setExpectChangeQty(BigDecimal.ZERO);
			applyWorksRootNode.setExpectChangePrice(BigDecimal.ZERO);
			applyWorksRootNode.setQty(BigDecimal.ZERO);
			applyWorksRootNode.setUpdateFlag("N");
			applyWorksRootNode.setCreateUserInfo(userKey, realName);
			// 新增合同评审清单根节点
			flag = zxGcsgCtContrApplyWorksMapper.insert(applyWorksRootNode);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApply);
		}
	}

	@Override
	public ResponseEntity psCheckZxGcsgCtContrApplyBeforeFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null) {
			// 规则一：当合同含税金额超过400万此项内容必须选择‘是’，否则无法发起合同评审
			// （发起合同评审时，系统需提醒客户合同含税金额大于400万，需要通过云电商进行招标）
			if (dbZxGcsgCtContrApply.getContractCost().compareTo(new BigDecimal(400)) > 0
					&& StrUtil.equals("0", dbZxGcsgCtContrApply.getIsBiddedOnCloud())) {
				return repEntity.layerMessage("no", "合同含税金额大于400万，需要通过云电商进行招标。");
			}
			// 规则二：注意当“是否通过云电商分包招标”字段选择为‘是’时，该项为必填项。
			// 不填写不准发起合同评审（发起合同评审时，系统需提醒客户填写这个字段）；
			if (StrUtil.equals("1", dbZxGcsgCtContrApply.getIsBiddedOnCloud())
					&& StrUtil.isEmpty(dbZxGcsgCtContrApply.getPackageNo())) {
				return repEntity.layerMessage("no", "当通过云电商分包招标时,包件编号为必填项");
			}
			// 规则三：如果（合同单价分析）页面存在‘不含税单价’为0 时，该工程合同是无法发起评审的，
			// 系统应提示用户“请完善合同单价分析数据”。
			ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
			zxGcsgCtPriceSys.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
			int count = zxGcsgCtPriceSysMapper.checkZxGcsgCtPriceSysBeforeFlow(zxGcsgCtPriceSys);
			if (count > 0) {
				return repEntity.layerMessage("no", "请完善合同单价分析数据。");
			}
			// 规则四：不能重复发起评审
			if (StrUtil.isNotEmpty(dbZxGcsgCtContrApply.getApih5FlowStatus())
					&& !StrUtil.equals("-1", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
				return repEntity.layerMessage("no", "已发起评审的数据不能再次发起评审。");
			}
		}
		return repEntity.ok(true);
	}

	@Override
	public ResponseEntity psGetZxGcsgCtContrApplyContractNo(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		// 获取数据
		String contractNo = zxGcsgCtContrApply.getContractNo() + "-FB-001";
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getFirstID())) {
			ZxGcsgCtContrApply contrApply = new ZxGcsgCtContrApply();
			contrApply.setFirstID(zxGcsgCtContrApply.getFirstID());
			ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.psGetZxGcsgCtContrApplyContractNo(contrApply);
			if (dbZxGcsgCtContrApply != null) {
				// String prefix = dbZxGcsgCtContrApply.getContractNo().substring(0,
				// dbZxGcsgCtContrApply.getContractNo().lastIndexOf("-") + 1);
				String prefix = zxGcsgCtContrApply.getContractNo() + "-FB-";
				String suffix = dbZxGcsgCtContrApply.getContractNo()
						.substring(dbZxGcsgCtContrApply.getContractNo().lastIndexOf("-") + 1);
				String number = String.valueOf(Integer.parseInt(suffix.substring(suffix.lastIndexOf("0") + 1)) + 1);
				switch (number.length()) {
				case 1:
					contractNo = prefix + "00" + number;
					break;
				case 2:
					contractNo = prefix + "0" + number;
					break;
				default:
					contractNo = prefix + number;
					break;
				}
			}
		}
		// 数据存在
		return repEntity.ok(contractNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity psUpdateZxGcsgCtContrApplyFirstByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 流程创建
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = null;
		if (StrUtil.equals("0", zxGcsgCtContrApply.getApih5FlowStatus())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
			if (dbZxGcsgCtContrApply != null) {
				dbZxGcsgCtContrApply.setBeginPer(realName);
				dbZxGcsgCtContrApply.setApih5FlowId(zxGcsgCtContrApply.getApih5FlowId());
				dbZxGcsgCtContrApply.setWorkId(zxGcsgCtContrApply.getWorkId());
				dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
				dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
				dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
		}
		// 流程提交
		else if (StrUtil.equals("1", zxGcsgCtContrApply.getApih5FlowStatus())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
			if (dbZxGcsgCtContrApply != null) {
				// 上报时间
				dbZxGcsgCtContrApply.setReportTime(DateUtil.date());
				// dbZxGcsgCtContrApply.setBeginPer(realName);
				dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
				dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
				dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
		}
		if (flag != 0) {
			// 流程中公文正文附件
			ZxGcsgCommonAttachment delete = new ZxGcsgCommonAttachment();
			delete.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
			delete.setFileType("2");
			zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(delete);
			if (zxGcsgCtContrApply.getDocAttachmentList() != null
					&& zxGcsgCtContrApply.getDocAttachmentList().size() > 0) {
				zxGcsgCtContrApply.getDocAttachmentList().stream().forEach(attachment -> {
					attachment.setUid(UuidUtil.generate());
					attachment.setFileType("2");
					attachment.setOtherId(zxGcsgCtContrApply.getCtContrApplyId());
					attachment.setCreateUserInfo(userKey, realName);
				});
				flag = zxGcsgCommonAttachmentMapper
						.batchInsertZxGcsgCommonAttachment(zxGcsgCtContrApply.getDocAttachmentList());
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApply);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity psUpdateZxGcsgCtContrApplySecondByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = null;
		if (StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContrApplyId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		} else if (StrUtil.isNotEmpty(zxGcsgCtContrApply.getWorkId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.getZxGcsgCtContrApplyByWorkId(zxGcsgCtContrApply.getWorkId());
		}
		// 更新状态或意见
		if (dbZxGcsgCtContrApply != null) {
			dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
			dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
			dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
			// 领导一意见
			if (StrUtil.equals("opinionField1", zxGcsgCtContrApply.getOpinionField(), true)) {
				dbZxGcsgCtContrApply.setOpinionField1(
						zxGcsgCtContrApply.getOpinionContent(realName, dbZxGcsgCtContrApply.getOpinionField1()));
			}
			// 领导二意见
			if (StrUtil.equals("opinionField2", zxGcsgCtContrApply.getOpinionField(), true)) {
				dbZxGcsgCtContrApply.setOpinionField2(
						zxGcsgCtContrApply.getOpinionContent(realName, dbZxGcsgCtContrApply.getOpinionField2()));
			}
			// 最后一次审批时间
			dbZxGcsgCtContrApply.setApprovalTime(DateUtil.date());
			flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			if (flag != 0) {
				// 流程中公文正文附件
				ZxGcsgCommonAttachment delete = new ZxGcsgCommonAttachment();
				delete.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
				delete.setFileType("2");
				zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(delete);
				if (zxGcsgCtContrApply.getDocAttachmentList() != null
						&& zxGcsgCtContrApply.getDocAttachmentList().size() > 0) {
					zxGcsgCtContrApply.getDocAttachmentList().stream().forEach(attachment -> {
						attachment.setUid(UuidUtil.generate());
						attachment.setFileType("2");
						attachment.setOtherId(zxGcsgCtContrApply.getCtContrApplyId());
						attachment.setCreateUserInfo(userKey, realName);
					});
					flag = zxGcsgCommonAttachmentMapper
							.batchInsertZxGcsgCommonAttachment(zxGcsgCtContrApply.getDocAttachmentList());
				}
				if (StrUtil.equals("2", zxGcsgCtContrApply.getApih5FlowStatus())) {
					// 评审通过时,生成合同管理基础数据
					ZxGcsgCtContract ctContract = new ZxGcsgCtContract();
					ctContract.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
					ctContract.setCtContractId(UuidUtil.generate());
					ctContract.setContractNo(dbZxGcsgCtContrApply.getContractNo());
					ctContract.setContractName(dbZxGcsgCtContrApply.getContractName());
					ctContract.setContractType(dbZxGcsgCtContrApply.getContractType());
					ctContract.setContent(dbZxGcsgCtContrApply.getContent());
					ctContract.setFirstId(dbZxGcsgCtContrApply.getFirstID());
					// 合同甲方
					ctContract.setFirstName(dbZxGcsgCtContrApply.getFirstName());
					ctContract.setFirstUnitTel("");
					ctContract.setSecondID(dbZxGcsgCtContrApply.getSecondID());
					// 乙方名称
					ctContract.setSecondName(dbZxGcsgCtContrApply.getSecondName());
					// “乙方代表”进行默认赋值为合同评审里面的“合同签订人”
					ctContract.setSecondPrincipal(dbZxGcsgCtContrApply.getAgent());
					ctContract.setSecondUnitTel("");
					// 签订日期”进行赋值为评审通过的当天日期
					ctContract.setSignatureDate(DateUtil.date());
					ctContract.setPlanStartDate(dbZxGcsgCtContrApply.getStartDate());
					ctContract.setPlanEndDate(dbZxGcsgCtContrApply.getEndDate());
					ctContract.setProjectManager("");
					// 1：执行中 3:终止,默认1
					ctContract.setContractStatus("1");
					// 0:个人 1:单位 默认无
					ctContract.setAgent("");
					ctContract.setSecondPartyMobile("");
					ctContract.setSecondPartyFax("");
					// 评审状态(评审通过)
					// ctContract.setAuditStatus(dbZxGcsgCtContrApply.getApih5FlowStatus());
					ctContract.setAuditStatus("auditPassed");
					ctContract.setSettleType("实际无结算");
					ctContract.setContractCost(dbZxGcsgCtContrApply.getContractCost());
					ctContract.setContractCostNoTax(dbZxGcsgCtContrApply.getContractCostNoTax());
					ctContract.setContractCostTax(dbZxGcsgCtContrApply.getContractCostTax());
					ctContract.setAlterContractSum(dbZxGcsgCtContrApply.getContractCost());
					ctContract.setAlterContractSumNoTax(dbZxGcsgCtContrApply.getContractCostNoTax());
					ctContract.setAlterContractSumTax(dbZxGcsgCtContrApply.getContractCostTax());
					ctContract.setIsDeduct(dbZxGcsgCtContrApply.getIsDeduct());
					ctContract.setWtrName("");
					ctContract.setWtrPhone("");
					ctContract.setReferenceName("");
					ctContract.setReferencePost("");
					ctContract.setReferencePhone("");
					// 甲方名称
					ctContract.setFirstIdFormula(dbZxGcsgCtContrApply.getFirstName());
					// 合同乙方
					ctContract.setSecondIDFormula(dbZxGcsgCtContrApply.getSecondName());
					ctContract.setCompanyId(dbZxGcsgCtContrApply.getCompanyId());
					ctContract.setCompanyName(dbZxGcsgCtContrApply.getCompanyName());
					ctContract.setProjectId(dbZxGcsgCtContrApply.getProjectId());
					ctContract.setProjectName(dbZxGcsgCtContrApply.getProjectName());
					ctContract.setCreateUserInfo(userKey, realName);
					flag = zxGcsgCtContractMapper.insert(ctContract);
					if (flag != 0) {
						// 默认新增三条保证金
						List<ZxGcsgCtCoContractAmtRate> amtRateList = Lists.newArrayList();
						ZxGcsgCtCoContractAmtRate amtRate1 = new ZxGcsgCtCoContractAmtRate();
						amtRate1.setCtCoContractAmtRateId(UuidUtil.generate());
						amtRate1.setOrgID(ctContract.getProjectId());
						amtRate1.setCtContractId(ctContract.getCtContractId());
						amtRate1.setStatisticsNo("100400");
						amtRate1.setStatisticsName("1、质量保证金");
						amtRate1.setStatisticsRate(BigDecimal.ZERO);
						amtRate1.setBackCondition("");
						amtRate1.setTimeLimit("");
						amtRate1.setComID(ctContract.getCompanyId());
						amtRate1.setComName(ctContract.getCompanyName());
						amtRate1.setCreateUserInfo(userKey, realName);
						amtRateList.add(amtRate1);
						ZxGcsgCtCoContractAmtRate amtRate2 = new ZxGcsgCtCoContractAmtRate();
						BeanUtil.copyProperties(amtRate1, amtRate2);
						amtRate2.setCtCoContractAmtRateId(UuidUtil.generate());
						amtRate2.setStatisticsNo("100500");
						amtRate2.setStatisticsName("2、安全生产保证金");
						amtRateList.add(amtRate2);
						ZxGcsgCtCoContractAmtRate amtRate3 = new ZxGcsgCtCoContractAmtRate();
						BeanUtil.copyProperties(amtRate1, amtRate3);
						amtRate3.setCtCoContractAmtRateId(UuidUtil.generate());
						amtRate3.setStatisticsNo("100600");
						amtRate3.setStatisticsName("3、农民工工资偿付保证金");
						amtRateList.add(amtRate3);
						flag = zxGcsgCtCoContractAmtRateMapper.batchInsertZxGcsgCtCoContractAmtRate(amtRateList);
						// 新增合同管理清单
						ZxGcsgCtContrApplyWorks applyWorks = new ZxGcsgCtContrApplyWorks();
						applyWorks.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						List<ZxGcsgCtContrApplyWorks> applyWorksList = zxGcsgCtContrApplyWorksMapper
								.selectByZxGcsgCtContrApplyWorksList(applyWorks);
						if (applyWorksList.size() > 0) {
							List<ZxGcsgCcWorks> ccworksList = Lists.newArrayList();
							// 根节点id重置
							String rootNodeId = UuidUtil.generate();
							applyWorksList.stream().forEach(dbApplyWorks -> {
								ZxGcsgCcWorks zxGcsgCcWorks = new ZxGcsgCcWorks();
								BeanUtil.copyProperties(dbApplyWorks, zxGcsgCcWorks);
								if (StrUtil.equals("-1", dbApplyWorks.getParentID())) {
									zxGcsgCcWorks.setCcWorksId(rootNodeId);
								} else {
									zxGcsgCcWorks.setCcWorksId(dbApplyWorks.getCtContrApplyWorksId());
								}
								if (dbApplyWorks.getTreeNode().length() == 8) {
									zxGcsgCcWorks.setParentID(rootNodeId);
								} else {
									zxGcsgCcWorks.setParentID(dbApplyWorks.getParentID());
								}
								zxGcsgCcWorks.setCtContractId(ctContract.getCtContractId());
								// 0正常 1 新增 2 修改 3 删除
								zxGcsgCcWorks.setExsitStatus(1);
								/*
								 * zxGcsgCcWorks.setTreeNode(dbApplyWorks.getTreeNode());
								 * zxGcsgCcWorks.setOrgID(dbApplyWorks.getOrgID());
								 * zxGcsgCcWorks.setWorkType(dbApplyWorks.getWorkType());
								 * zxGcsgCcWorks.setWorkNo(dbApplyWorks.getWorkNo());
								 * zxGcsgCcWorks.setWorkName(dbApplyWorks.getWorkName());
								 * zxGcsgCcWorks.setUnit(dbApplyWorks.getUnit());
								 * zxGcsgCcWorks.setContractPrice(dbApplyWorks.getContractPrice());
								 * zxGcsgCcWorks.setContractPriceNoTax(dbApplyWorks.getContractPriceNoTax());
								 * zxGcsgCcWorks.setContractQty(dbApplyWorks.getContractQty());
								 * zxGcsgCcWorks.setContractAmt(dbApplyWorks.getContractAmt());
								 * zxGcsgCcWorks.setContractAmtNoTax(dbApplyWorks.getAmtNoTax());
								 * zxGcsgCcWorks.setQuantity(dbApplyWorks.getQuantity());
								 * zxGcsgCcWorks.setPrice(dbApplyWorks.getPrice());
								 * zxGcsgCcWorks.setPriceNoTax(dbApplyWorks.getPriceNoTax());
								 * zxGcsgCcWorks.setAmtNoTax(dbApplyWorks.getAmtNoTax());
								 * zxGcsgCcWorks.setDeleted(dbApplyWorks.getDeleted());
								 * zxGcsgCcWorks.setIsLeaf(dbApplyWorks.getIsLeaf());
								 * zxGcsgCcWorks.setExsitStatus(dbApplyWorks.getExsitStatus());
								 * zxGcsgCcWorks.setIsAssignable(dbApplyWorks.getIsAssignable());
								 * zxGcsgCcWorks.setUpdateFlag(dbApplyWorks.getUpdateFlag());
								 * zxGcsgCcWorks.setEditTime(dbApplyWorks.getEditTime());
								 * zxGcsgCcWorks.setCheckQty(dbApplyWorks.getCheckQty());
								 * zxGcsgCcWorks.setExpectChangeQty(dbApplyWorks.getExpectChangeQty());
								 * zxGcsgCcWorks.setExpectChangePrice(dbApplyWorks.getExpectChangePrice());
								 * zxGcsgCcWorks.setInputWorkType(dbApplyWorks.getInputWorkType());
								 * zxGcsgCcWorks.setIsReCountAmt(dbApplyWorks.getIsReCountAmt());
								 * zxGcsgCcWorks.setQty(dbApplyWorks.getQty());
								 * zxGcsgCcWorks.setIsGroup(dbApplyWorks.getIsGroup());
								 * zxGcsgCcWorks.setTaxRate(dbApplyWorks.getTaxRate());
								 * zxGcsgCcWorks.setRuleID(dbApplyWorks.getRuleID());
								 * zxGcsgCcWorks.setRuleName(dbApplyWorks.getRuleName());
								 */
								zxGcsgCcWorks.setCreateUserInfo(userKey, realName);
								ccworksList.add(zxGcsgCcWorks);
							});
							flag = zxGcsgCcWorksMapper.batchInsertZxGcsgCcWorks(ccworksList);
						}
						// 合同单价分析
						// ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
						// zxGcsgCtPriceSys.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						// zxGcsgCtPriceSys.setCtContractId(ctContract.getCtContractId());
						// flag =
						// zxGcsgCtPriceSysMapper.updateZxGcsgCtPriceSysByCtContrApplyId(zxGcsgCtPriceSys);
						// 获取合同评审所有单价分析表和明细表
						ZxGcsgCtPriceSys search = new ZxGcsgCtPriceSys();
						search.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						List<ZxGcsgCtPriceSys> applyPriceSysList = zxGcsgCtPriceSysMapper
								.selectByZxGcsgCtPriceSysList(search);
						if (applyPriceSysList.size() > 0) {
							List<ZxGcsgCtPriceSys> insertPriceList = Lists.newArrayList();
							List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
							applyPriceSysList.stream().forEach(applyPriceSys -> {
								ZxGcsgCtPriceSys insertPrice = new ZxGcsgCtPriceSys();
								BeanUtil.copyProperties(applyPriceSys, insertPrice);
								insertPrice.setCtPriceSysId(UuidUtil.generate());
								insertPrice.setCtContrApplyId("");
								insertPrice.setCtContractId(ctContract.getCtContractId());
								insertPrice.setApplyAlterWorksId("");
								insertPrice.setCcWorksId(applyPriceSys.getApplyAlterWorksId());
								insertPrice.setCreateUserInfo(userKey, realName);
								insertPriceList.add(insertPrice);
								ZxGcsgCtPriceSysItem priceSysItem = new ZxGcsgCtPriceSysItem();
								priceSysItem.setCtPriceSysId(applyPriceSys.getCtPriceSysId());
								List<ZxGcsgCtPriceSysItem> itemList = zxGcsgCtPriceSysItemMapper
										.selectByZxGcsgCtPriceSysItemList(priceSysItem);
								if (itemList.size() > 0) {
									itemList.stream().forEach(item -> {
										ZxGcsgCtPriceSysItem insertData = new ZxGcsgCtPriceSysItem();
										BeanUtil.copyProperties(item, insertData);
										insertData.setCtPriceSysItemId(UuidUtil.generate());
										insertData.setCtPriceSysId(insertPrice.getCtPriceSysId());
										insertData.setCreateUserInfo(userKey, realName);
										insertItemList.add(insertData);
									});
								}
							});
							if (insertPriceList.size() > 0) {
								flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(insertPriceList);
							}
							if (insertItemList.size() > 0) {
								flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
							}
						}
						// 工序挂接
						// ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie = new
						// ZxGcsgCtContrProcessGuajie();
						// zxGcsgCtContrProcessGuajie.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						// zxGcsgCtContrProcessGuajie.setCtContractId(ctContract.getCtContractId());
						// flag = zxGcsgCtContrProcessGuajieMapper
						// .updateZxGcsgCtContrProcessGuajieByCtContrApplyId(zxGcsgCtContrProcessGuajie);
						ZxGcsgCtContrProcessGuajie search2 = new ZxGcsgCtContrProcessGuajie();
						search2.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						List<ZxGcsgCtContrProcessGuajie> applyGuajieList = zxGcsgCtContrProcessGuajieMapper
								.selectByZxGcsgCtContrProcessGuajieList(search2);
						if (applyGuajieList.size() > 0) {
							List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
							applyGuajieList.stream().forEach(applyGuajie -> {
								ZxGcsgCtContrProcessGuajie insertGuajie = new ZxGcsgCtContrProcessGuajie();
								BeanUtil.copyProperties(applyGuajie, insertGuajie);
								insertGuajie.setCtContrProcessGuajieId(UuidUtil.generate());
								insertGuajie.setApplyAlterWorksId("");
								insertGuajie.setCtContrApplyId("");
								insertGuajie.setCcWorksId(applyGuajie.getApplyAlterWorksId());
								insertGuajie.setCtContractId(ctContract.getCtContractId());
								insertGuajie.setCreateUserInfo(userKey, realName);
								insertList.add(insertGuajie);
							});
							if (insertList.size() > 0) {
								zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
							}
						}
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApply);
		}
	}

	@Override
	public ResponseEntity psGetZxGcsgCtContrApplyDetailsByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = null;
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getWorkId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.getZxGcsgCtContrApplyByWorkId(zxGcsgCtContrApply.getWorkId());
		} else if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContrApplyId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		}
		if (dbZxGcsgCtContrApply != null) {
			// 获取公文正文附件
			ZxGcsgCommonAttachment attachment = new ZxGcsgCommonAttachment();
			attachment.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
			attachment.setFileType("2");
			List<ZxGcsgCommonAttachment> docAttachmentList = zxGcsgCommonAttachmentMapper
					.selectByZxGcsgCommonAttachmentList(attachment);
			dbZxGcsgCtContrApply.setDocAttachmentList(docAttachmentList);
		}
		return repEntity.ok(dbZxGcsgCtContrApply);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity bxAddZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// check
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContractId())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApply.getContractNo())) {
			ZxGcsgCtContrApply check = new ZxGcsgCtContrApply();
			check.setCtContractId(zxGcsgCtContrApply.getCtContractId());
			check.setContractNo(zxGcsgCtContrApply.getContractNo());
			int count = zxGcsgCtContrApplyMapper.checkZxGcsgCtContrApplyRepetitiveNo(check);
			if (count > 0) {
				return repEntity.layerMessage("no", "该补充协议的编号已存在,请检查后重试。");
			}
			// check该合同是否存在未通过的补充协议
			check.setContractNo("");
			int count2 = zxGcsgCtContrApplyMapper.bxCheckZxGcsgCtContrApplyPreCondition(check);
			if (count2 > 0) {
				return repEntity.layerMessage("no", "该合同存在未评审通过的补充协议,不能创建新的补充协议。");
			}
		}
		zxGcsgCtContrApply.setCtContrApplyId(UuidUtil.generate());
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContractId())) {
			ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper
					.selectByPrimaryKey(zxGcsgCtContrApply.getCtContractId());
			if (dbZxGcsgCtContract != null) {
				zxGcsgCtContrApply.setContractName(dbZxGcsgCtContract.getContractName());
				zxGcsgCtContrApply.setContractType("P2C");
				zxGcsgCtContrApply.setFirstID(dbZxGcsgCtContract.getFirstId());
				zxGcsgCtContrApply.setFirstName(dbZxGcsgCtContract.getFirstName());
				zxGcsgCtContrApply.setSecondID(dbZxGcsgCtContract.getSecondID());
				zxGcsgCtContrApply.setSecondName(dbZxGcsgCtContract.getSecondName());
				zxGcsgCtContrApply.setContractCost(dbZxGcsgCtContract.getContractCost());
				zxGcsgCtContrApply.setContractCostNoTax(dbZxGcsgCtContract.getContractCostNoTax());
				zxGcsgCtContrApply.setContractCostTax(dbZxGcsgCtContract.getContractCostTax());
				zxGcsgCtContrApply.setCurrentTaxAmount(BigDecimal.ZERO);
				zxGcsgCtContrApply.setPp4NoTax(BigDecimal.ZERO);
				zxGcsgCtContrApply.setPp4Tax(BigDecimal.ZERO);
				zxGcsgCtContrApply.setAlterContractSum(dbZxGcsgCtContract.getAlterContractSum());
				zxGcsgCtContrApply.setAlterContractSumNoTax(dbZxGcsgCtContract.getAlterContractSumNoTax());
				zxGcsgCtContrApply.setAlterContractSumTax(dbZxGcsgCtContract.getAlterContractSumTax());
				zxGcsgCtContrApply.setIsDeduct(dbZxGcsgCtContract.getIsDeduct());
				// zxGcsgCtContrApply.setCsTimeLimit("0");
				zxGcsgCtContrApply.setUpAlterContractSum(dbZxGcsgCtContract.getAlterContractSum());
				zxGcsgCtContrApply.setUpAlterContractSumNoTax(dbZxGcsgCtContract.getAlterContractSumNoTax());
				zxGcsgCtContrApply.setUpAlterContractSumTax(dbZxGcsgCtContract.getAlterContractSumTax());
				// 合同管理信息获取权限字段
				zxGcsgCtContrApply.setProjectId(dbZxGcsgCtContract.getProjectId());
				zxGcsgCtContrApply.setProjectName(dbZxGcsgCtContract.getProjectName());
				zxGcsgCtContrApply.setCompanyId(dbZxGcsgCtContract.getCompanyId());
				zxGcsgCtContrApply.setCompanyName(dbZxGcsgCtContract.getCompanyName());
			}
		}
		zxGcsgCtContrApply.setCreateUserInfo(userKey, realName);
		// 一、生成合同补充协议清单
		ZxGcsgCcCoAlter zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
		zxGcsgCcCoAlter.setCcCoAlterId(UuidUtil.generate());
		zxGcsgCcCoAlter.setProposer(zxGcsgCtContrApply.getAgreementName());
		zxGcsgCcCoAlter.setApplyAmount(zxGcsgCtContrApply.getCurrentTaxAmount());
		zxGcsgCcCoAlter.setReplyAmount(zxGcsgCtContrApply.getAlterContractSum());
		zxGcsgCcCoAlter.setReplyNo(zxGcsgCtContrApply.getContractNo());
		zxGcsgCcCoAlter.setOriginalTaxAmount(zxGcsgCtContrApply.getContractCost());
		zxGcsgCcCoAlter.setCtContrApplyId(zxGcsgCtContrApply.getCtContrApplyId());
		zxGcsgCcCoAlter.setCtContractId(zxGcsgCtContrApply.getCtContractId());
		zxGcsgCcCoAlter.setApplyAmountNoTax(zxGcsgCtContrApply.getPp4NoTax());
		zxGcsgCcCoAlter.setApplyAmountTax(zxGcsgCtContrApply.getPp4Tax());
		zxGcsgCcCoAlter.setReplyAmountNoTax(zxGcsgCtContrApply.getAlterContractSumNoTax());
		zxGcsgCcCoAlter.setReplyAmountTax(zxGcsgCtContrApply.getAlterContractSumTax());
		zxGcsgCcCoAlter.setPp2NoTax(zxGcsgCtContrApply.getContractCostNoTax());
		zxGcsgCcCoAlter.setPp2Tax(zxGcsgCtContrApply.getContractCostTax());
		zxGcsgCcCoAlter.setContractName(zxGcsgCtContrApply.getContractName());
		// zxGcsgCcCoAlter.setReplyDate(replyDate);
		zxGcsgCcCoAlter.setCreateUserInfo(userKey, realName);
		// id互相写入
		zxGcsgCtContrApply.setCcCoAlterId(zxGcsgCcCoAlter.getCcCoAlterId());
		zxGcsgCtContrApply.setApih5FlowStatus("-1");
		int flag = zxGcsgCtContrApplyMapper.insert(zxGcsgCtContrApply);
		if (flag != 0) {
			flag = zxGcsgCcCoAlterMapper.insert(zxGcsgCcCoAlter);
			// 附件
			if (zxGcsgCtContrApply.getAttachmentList() != null && zxGcsgCtContrApply.getAttachmentList().size() > 0) {
				zxGcsgCtContrApply.getAttachmentList().stream().forEach(attachment -> {
					attachment.setUid(UuidUtil.generate());
					attachment.setFileType("1");
					attachment.setOtherId(zxGcsgCtContrApply.getCtContrApplyId());
					attachment.setCreateUserInfo(userKey, realName);
				});
				flag = zxGcsgCommonAttachmentMapper
						.batchInsertZxGcsgCommonAttachment(zxGcsgCtContrApply.getAttachmentList());
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApply);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity bxUpdateZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null) {
			// check审核中和通过审核的不允许修改
			if (StrUtil.equals("1", dbZxGcsgCtContrApply.getApih5FlowStatus())
					|| StrUtil.equals("2", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
				return repEntity.layerMessage("no", "该补充协议正在审核中或已通过审核,不允许修改。");
			}
			dbZxGcsgCtContrApply.setAgreementName(zxGcsgCtContrApply.getAgreementName());
			dbZxGcsgCtContrApply.setAgent(zxGcsgCtContrApply.getAgent());
			dbZxGcsgCtContrApply.setStartDate(zxGcsgCtContrApply.getStartDate());
			dbZxGcsgCtContrApply.setEndDate(zxGcsgCtContrApply.getEndDate());
			dbZxGcsgCtContrApply.setContent(zxGcsgCtContrApply.getContent());
			dbZxGcsgCtContrApply.setRemarks(zxGcsgCtContrApply.getRemarks());
			dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			if (flag != 0) {
				// 一、修改合同补充协议清单
				ZxGcsgCcCoAlter dbZxGcsgCcCoAlter = zxGcsgCcCoAlterMapper
						.selectByPrimaryKey(dbZxGcsgCtContrApply.getCcCoAlterId());
				if (dbZxGcsgCcCoAlter != null) {
					dbZxGcsgCcCoAlter.setProposer(dbZxGcsgCtContrApply.getAgreementName());
					dbZxGcsgCcCoAlter.setModifyUserInfo(userKey, realName);
					flag = zxGcsgCcCoAlterMapper.updateByPrimaryKey(dbZxGcsgCcCoAlter);
				}
				// 附件
				ZxGcsgCommonAttachment delete = new ZxGcsgCommonAttachment();
				delete.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
				delete.setFileType("1");
				zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(delete);
				if (zxGcsgCtContrApply.getAttachmentList() != null
						&& zxGcsgCtContrApply.getAttachmentList().size() > 0) {
					zxGcsgCtContrApply.getAttachmentList().stream().forEach(attachment -> {
						attachment.setUid(UuidUtil.generate());
						attachment.setFileType("1");
						attachment.setOtherId(zxGcsgCtContrApply.getCtContrApplyId());
						attachment.setCreateUserInfo(userKey, realName);
					});
					flag = zxGcsgCommonAttachmentMapper
							.batchInsertZxGcsgCommonAttachment(zxGcsgCtContrApply.getAttachmentList());
				}
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApply);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity bxUpdateZxGcsgCtContrApplyFirstByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 流程创建
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = null;
		if (StrUtil.equals("0", zxGcsgCtContrApply.getApih5FlowStatus())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
			if (dbZxGcsgCtContrApply != null) {
				dbZxGcsgCtContrApply.setBeginPer(realName);
				dbZxGcsgCtContrApply.setApih5FlowId(zxGcsgCtContrApply.getApih5FlowId());
				dbZxGcsgCtContrApply.setWorkId(zxGcsgCtContrApply.getWorkId());
				dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
				dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
				dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
		}
		// 流程提交
		else if (StrUtil.equals("1", zxGcsgCtContrApply.getApih5FlowStatus())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
			if (dbZxGcsgCtContrApply != null) {
				dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
				dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
				dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
		}
		if (flag != 0) {
			// 流程中公文正文附件
			ZxGcsgCommonAttachment delete = new ZxGcsgCommonAttachment();
			delete.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
			delete.setFileType("2");
			zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(delete);
			if (zxGcsgCtContrApply.getDocAttachmentList() != null
					&& zxGcsgCtContrApply.getDocAttachmentList().size() > 0) {
				zxGcsgCtContrApply.getDocAttachmentList().stream().forEach(attachment -> {
					attachment.setUid(UuidUtil.generate());
					attachment.setFileType("2");
					attachment.setOtherId(zxGcsgCtContrApply.getCtContrApplyId());
					attachment.setCreateUserInfo(userKey, realName);
				});
				flag = zxGcsgCommonAttachmentMapper
						.batchInsertZxGcsgCommonAttachment(zxGcsgCtContrApply.getDocAttachmentList());
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApply);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity bxUpdateZxGcsgCtContrApplySecondByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = null;
		if (StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContrApplyId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		} else if (StrUtil.isNotEmpty(zxGcsgCtContrApply.getWorkId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.getZxGcsgCtContrApplyByWorkId(zxGcsgCtContrApply.getWorkId());
		}
		// 更新状态或意见
		if (dbZxGcsgCtContrApply != null) {
			dbZxGcsgCtContrApply.setApih5FlowStatus(zxGcsgCtContrApply.getApih5FlowStatus());
			dbZxGcsgCtContrApply.setApih5FlowNodeStatus(zxGcsgCtContrApply.getApih5FlowNodeStatus());
			dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
			// 领导一意见
			if (StrUtil.equals("opinionField1", zxGcsgCtContrApply.getOpinionField(), true)) {
				dbZxGcsgCtContrApply.setOpinionField1(
						zxGcsgCtContrApply.getOpinionContent(realName, dbZxGcsgCtContrApply.getOpinionField1()));
			}
			// 领导二意见
			if (StrUtil.equals("opinionField2", zxGcsgCtContrApply.getOpinionField(), true)) {
				dbZxGcsgCtContrApply.setOpinionField2(
						zxGcsgCtContrApply.getOpinionContent(realName, dbZxGcsgCtContrApply.getOpinionField2()));
			}
			flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			if (flag != 0) {
				// 流程中公文正文附件
				ZxGcsgCommonAttachment delete = new ZxGcsgCommonAttachment();
				delete.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
				delete.setFileType("2");
				zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(delete);
				if (zxGcsgCtContrApply.getDocAttachmentList() != null
						&& zxGcsgCtContrApply.getDocAttachmentList().size() > 0) {
					zxGcsgCtContrApply.getDocAttachmentList().stream().forEach(attachment -> {
						attachment.setUid(UuidUtil.generate());
						attachment.setFileType("2");
						attachment.setOtherId(zxGcsgCtContrApply.getCtContrApplyId());
						attachment.setCreateUserInfo(userKey, realName);
					});
					flag = zxGcsgCommonAttachmentMapper
							.batchInsertZxGcsgCommonAttachment(zxGcsgCtContrApply.getDocAttachmentList());
				}
				if (StrUtil.equals("2", zxGcsgCtContrApply.getApih5FlowStatus())) {
					// 补充协议审核通过时,修改合同管理表(变更后含税金额、变更后不含税金额、变更后税额)
					ZxGcsgCtContract dbContract = zxGcsgCtContractMapper
							.selectByPrimaryKey(dbZxGcsgCtContrApply.getCtContractId());
					if (dbContract != null) {
						dbContract.setAlterContractSum(dbZxGcsgCtContrApply.getAlterContractSum());
						dbContract.setAlterContractSumNoTax(dbZxGcsgCtContrApply.getAlterContractSumNoTax());
						dbContract.setAlterContractSumTax(dbZxGcsgCtContrApply.getAlterContractSumTax());
						dbContract.setModifyUserInfo(userKey, realName);
						flag = zxGcsgCtContractMapper.updateByPrimaryKey(dbContract);
					}
					// 修改合同管理清单表
					if (flag != 0) {
						ZxGcsgCcCoAlterWork ccCoAlterWork = new ZxGcsgCcCoAlterWork();
						ccCoAlterWork.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						List<ZxGcsgCcCoAlterWork> alterWorkList = zxGcsgCcCoAlterWorkMapper
								.selectByZxGcsgCcCoAlterWorkList(ccCoAlterWork);
						if (alterWorkList.size() > 0) {
							List<ZxGcsgCcWorks> insertCcworksList = Lists.newArrayList();
							List<ZxGcsgCcWorks> updateCcworksList = Lists.newArrayList();
							alterWorkList.stream().forEach(dbAlterWork -> {
								ZxGcsgCcWorks zxGcsgCcWorks = new ZxGcsgCcWorks();
								// 修改类型单价不变只变数量
								BigDecimal priceNoTax = BigDecimal.ZERO;
								if (dbAlterWork.getTaxRate() != null && NumberUtil.isNumber(dbAlterWork.getTaxRate())) {
									priceNoTax = CalcUtils.calcDivide(dbAlterWork.getAfterChangePrice(),
											CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils.calcDivide(
													new BigDecimal(dbAlterWork.getTaxRate()), new BigDecimal(100))),
											6);
								}
								// 新增类型
								if (StrUtil.equals("1", dbAlterWork.getAlterType())) {
									zxGcsgCcWorks.setCcWorksId(dbAlterWork.getCcCoAlterWorkId());
									zxGcsgCcWorks.setParentID(dbAlterWork.getCcWorksParentId());
									zxGcsgCcWorks.setCtContractId(dbContract.getCtContractId());
									zxGcsgCcWorks.setTreeNode(dbAlterWork.getTreeNode());
									zxGcsgCcWorks.setOrgID(dbContract.getFirstId());
									// { "10", "合同清单" },{ "11", "管理费清单" }, { "20", "拆分清单" } ;
									zxGcsgCcWorks.setWorkType(10);
									zxGcsgCcWorks.setWorkNo(dbAlterWork.getCcWorksNo());
									zxGcsgCcWorks.setWorkName(dbAlterWork.getCcWorksName());
									zxGcsgCcWorks.setUnit(dbAlterWork.getCcWorksUnit());
									// zxGcsgCcWorks.setContractPrice(dbAlterWork.getOriginPrice());
									// zxGcsgCcWorks.setContractPriceNoTax(dbAlterWork.getOriginPriceNoTax());
									// zxGcsgCcWorks.setContractQty(dbAlterWork.getOriginQty());
									zxGcsgCcWorks.setContractPrice(null);
									zxGcsgCcWorks.setContractPriceNoTax(null);
									zxGcsgCcWorks.setContractQty(null);
									// zxGcsgCcWorks.setContractAmt(dbAlterWork.getContractPrice());
									// zxGcsgCcWorks.setContractAmtNoTax(dbAlterWork.getContractCostNoTax());
									zxGcsgCcWorks.setQuantity(dbAlterWork.getAfterChangeQty());
									zxGcsgCcWorks.setPrice(dbAlterWork.getAfterChangePrice());
									zxGcsgCcWorks.setPriceNoTax(priceNoTax);
									// zxGcsgCcWorks.setAmt(dbAlterWork.getAfterAmt());
									// zxGcsgCcWorks.setAmtNoTax(dbAlterWork.getAfterAmtNoTax());
									zxGcsgCcWorks.setDeleted(0);
									zxGcsgCcWorks.setIsLeaf(dbAlterWork.getIsLeaf());
									// 0正常 1 新增 2 修改 3 删除
									zxGcsgCcWorks.setExsitStatus(2);
									zxGcsgCcWorks.setIsAssignable(
											dbAlterWork.getIsLeaf() != null ? dbAlterWork.getIsLeaf() : 0);
									zxGcsgCcWorks.setUpdateFlag("0");
									zxGcsgCcWorks.setEditTime(dbAlterWork.getEditTime());
									// zxGcsgCcWorks.setCheckQty(dbAlterWork.getCheckQty());
									zxGcsgCcWorks.setExpectChangeQty(dbAlterWork.getAfterChangeQty());
									zxGcsgCcWorks.setExpectChangePrice(dbAlterWork.getAfterChangePrice());
									// 根据父节点inputWorkType获取
									ZxGcsgCcWorks dbParent = zxGcsgCcWorksMapper
											.selectByPrimaryKey(dbAlterWork.getCcWorksParentId());
									if (dbParent != null) {
										zxGcsgCcWorks.setInputWorkType(dbParent.getInputWorkType());
									}
									// zxGcsgCcWorks.setIsReCountAmt(dbAlterWork.getIsReCountAmt());
									// zxGcsgCcWorks.setQty(dbAlterWork.getQty());
									// zxGcsgCcWorks.setIsGroup(dbAlterWork.getIsGroup());
									zxGcsgCcWorks.setTaxRate(dbAlterWork.getTaxRate());
									zxGcsgCcWorks.setRuleID(dbAlterWork.getRuleID());
									zxGcsgCcWorks.setRuleName(dbAlterWork.getRuleName());
									zxGcsgCcWorks.setCreateUserInfo(userKey, realName);
									insertCcworksList.add(zxGcsgCcWorks);
								}
								// 修改类型(只修改变更后数量和状态)
								else if (StrUtil.equals("2", dbAlterWork.getAlterType())) {
									ZxGcsgCcWorks search = new ZxGcsgCcWorks();
									search.setCcWorksId(dbAlterWork.getCcWorksId());
									ZxGcsgCcWorks dbZxGcsgCcWorks = zxGcsgCcWorksMapper
											.getZxGcsgCcWorksByCondition(search);
									dbZxGcsgCcWorks.setQuantity(dbAlterWork.getAfterChangeQty());
									// 变更状态0正常 1 新增 2 修改 3 删除
									dbZxGcsgCcWorks.setExsitStatus(2);
									// dbZxGcsgCcWorks.setPrice(dbAlterWork.getAfterChangePrice());
									// dbZxGcsgCcWorks.setPriceNoTax(priceNoTax);
									// dbZxGcsgCcWorks.setAmt(dbAlterWork.getAfterAmt());
									// dbZxGcsgCcWorks.setAmtNoTax(dbAlterWork.getAfterAmtNoTax());
									// dbZxGcsgCcWorks.setModifyUserInfo(userKey, realName);
									updateCcworksList.add(dbZxGcsgCcWorks);
								}
							});
							if (insertCcworksList.size() > 0) {
								flag = zxGcsgCcWorksMapper.batchInsertZxGcsgCcWorks(insertCcworksList);
							}
							if (updateCcworksList.size() > 0) {
								zxGcsgCcWorksMapper.batchUpdateZxGcsgCcWorks(updateCcworksList);
							}
						}
						// 合同单价分析
						// ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
						// zxGcsgCtPriceSys.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						// zxGcsgCtPriceSys.setCtContractId(ctContract.getCtContractId());
						// flag =
						// zxGcsgCtPriceSysMapper.updateZxGcsgCtPriceSysByCtContrApplyId(zxGcsgCtPriceSys);
						// 获取补充协议所有单价分析表和明细表
						ZxGcsgCtPriceSys search = new ZxGcsgCtPriceSys();
						search.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						List<ZxGcsgCtPriceSys> applyPriceSysList = zxGcsgCtPriceSysMapper
								.selectByZxGcsgCtPriceSysList(search);
						if (applyPriceSysList.size() > 0) {
							List<ZxGcsgCtPriceSys> insertPriceList = Lists.newArrayList();
							List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
							applyPriceSysList.stream().forEach(applyPriceSys -> {
								ZxGcsgCtPriceSys insertPrice = new ZxGcsgCtPriceSys();
								BeanUtil.copyProperties(applyPriceSys, insertPrice);
								insertPrice.setCtPriceSysId(UuidUtil.generate());
								insertPrice.setCtContrApplyId("");
								insertPrice.setCtContractId(dbContract.getCtContractId());
								insertPrice.setApplyAlterWorksId("");
								insertPrice.setCcWorksId(applyPriceSys.getApplyAlterWorksId());
								insertPrice.setCreateUserInfo(userKey, realName);
								insertPriceList.add(insertPrice);
								ZxGcsgCtPriceSysItem priceSysItem = new ZxGcsgCtPriceSysItem();
								priceSysItem.setCtPriceSysId(applyPriceSys.getCtPriceSysId());
								List<ZxGcsgCtPriceSysItem> itemList = zxGcsgCtPriceSysItemMapper
										.selectByZxGcsgCtPriceSysItemList(priceSysItem);
								if (itemList.size() > 0) {
									itemList.stream().forEach(item -> {
										ZxGcsgCtPriceSysItem insertData = new ZxGcsgCtPriceSysItem();
										BeanUtil.copyProperties(item, insertData);
										insertData.setCtPriceSysItemId(UuidUtil.generate());
										insertData.setCtPriceSysId(insertPrice.getCtPriceSysId());
										insertData.setCreateUserInfo(userKey, realName);
										insertItemList.add(insertData);
									});
								}
							});
							if (insertPriceList.size() > 0) {
								flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(insertPriceList);
							}
							if (insertItemList.size() > 0) {
								flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
							}
						}
						// 工序挂接
						// ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie = new
						// ZxGcsgCtContrProcessGuajie();
						// zxGcsgCtContrProcessGuajie.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						// zxGcsgCtContrProcessGuajie.setCtContractId(ctContract.getCtContractId());
						// flag = zxGcsgCtContrProcessGuajieMapper
						// .updateZxGcsgCtContrProcessGuajieByCtContrApplyId(zxGcsgCtContrProcessGuajie);
						ZxGcsgCtContrProcessGuajie search2 = new ZxGcsgCtContrProcessGuajie();
						search2.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
						List<ZxGcsgCtContrProcessGuajie> applyGuajieList = zxGcsgCtContrProcessGuajieMapper
								.selectByZxGcsgCtContrProcessGuajieList(search2);
						if (applyGuajieList.size() > 0) {
							List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
							applyGuajieList.stream().forEach(applyGuajie -> {
								ZxGcsgCtContrProcessGuajie insertGuajie = new ZxGcsgCtContrProcessGuajie();
								BeanUtil.copyProperties(applyGuajie, insertGuajie);
								insertGuajie.setCtContrProcessGuajieId(UuidUtil.generate());
								insertGuajie.setApplyAlterWorksId("");
								insertGuajie.setCtContrApplyId("");
								insertGuajie.setCcWorksId(applyGuajie.getApplyAlterWorksId());
								insertGuajie.setCtContractId(dbContract.getCtContractId());
								insertGuajie.setCreateUserInfo(userKey, realName);
								insertList.add(insertGuajie);
							});
							if (insertList.size() > 0) {
								zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
							}
						}
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApply);
		}
	}

	@Override
	public ResponseEntity bxGetZxGcsgCtContrApplyDetailsByFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = null;
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getWorkId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.getZxGcsgCtContrApplyByWorkId(zxGcsgCtContrApply.getWorkId());
		} else if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContrApplyId())) {
			dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		}
		if (dbZxGcsgCtContrApply != null) {
			// 查询普通附件和公文正文附件
			ZxGcsgCommonAttachment attachment = new ZxGcsgCommonAttachment();
			attachment.setOtherId(dbZxGcsgCtContrApply.getCtContrApplyId());
			// attachment.setFileType("2");
			List<ZxGcsgCommonAttachment> attachmentList = zxGcsgCommonAttachmentMapper
					.selectByZxGcsgCommonAttachmentList(attachment);
			List<ZxGcsgCommonAttachment> list1 = Lists.newArrayList();
			List<ZxGcsgCommonAttachment> list2 = Lists.newArrayList();
			if (attachmentList.size() > 0) {
				attachmentList.stream().forEach(file -> {
					if (StrUtil.equals("1", file.getFileType())) {
						list1.add(file);
					} else if (StrUtil.equals("2", file.getFileType())) {
						list2.add(file);
					}
				});
			}
			dbZxGcsgCtContrApply.setAttachmentList(list1);
			dbZxGcsgCtContrApply.setDocAttachmentList(list2);
		}
		return repEntity.ok(dbZxGcsgCtContrApply);
	}

	@Override
	public ResponseEntity bxCheckZxGcsgCtContrApplyBeforeFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null) {
			// 规则一：（1）在点击发起补充协议合同评审时候进行限制 超过限制条件的数据弹出 提示：
			// 【补充协议 累计含税合同金额 超过原主合同含税金额的15% 或
			// 此主合同累计建立补充协议份数超过5份，不准发起评审。如有问题请咨询局合同管理与结算中心】
			BigDecimal fifPercent = CalcUtils.calcMultiply(dbZxGcsgCtContrApply.getContractCost(),
					new BigDecimal(0.15));
			BigDecimal difference = CalcUtils.calcSubtract(dbZxGcsgCtContrApply.getAlterContractSum(),
					dbZxGcsgCtContrApply.getContractCost());
			if (difference.compareTo(fifPercent) > 0) {
				return repEntity.layerMessage("no", "补充协议累计含税合同金额超过原主合同含税金额的15%。");
			}
			// 规则二：
			ZxGcsgCtContrApply ctContrApply = new ZxGcsgCtContrApply();
			ctContrApply.setCtContractId(dbZxGcsgCtContrApply.getCtContractId());
			List<ZxGcsgCtContrApply> countList = zxGcsgCtContrApplyMapper.selectByZxGcsgCtContrApplyList(ctContrApply);
			if (countList.size() > 5) {
				return repEntity.layerMessage("no", "此主合同累计建立补充协议份数超过5份,不准发起评审,如有问题请咨询局合同管理与结算中心。");
			}
			// 规则三：不能重复发起评审
			if (StrUtil.isNotEmpty(dbZxGcsgCtContrApply.getApih5FlowStatus())
					&& !StrUtil.equals("-1", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
				return repEntity.layerMessage("no", "已发起评审的数据不能再次发起评审。");
			}
		}
		return repEntity.ok(true);
	}

	@Override
	public ResponseEntity bxGetZxGcsgCtContrApplyContractNo(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		// 获取数据
		String contractNo = zxGcsgCtContrApply.getContractNo() + "-补01";
		if (zxGcsgCtContrApply != null && StrUtil.isNotEmpty(zxGcsgCtContrApply.getCtContractId())) {
			ZxGcsgCtContrApply contrApply = new ZxGcsgCtContrApply();
			contrApply.setCtContractId(zxGcsgCtContrApply.getCtContractId());
			ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.bxGetZxGcsgCtContrApplyContractNo(contrApply);
			if (dbZxGcsgCtContrApply != null) {
				String prefix = dbZxGcsgCtContrApply.getContractNo().substring(0,
						dbZxGcsgCtContrApply.getContractNo().lastIndexOf("补") + 1);
				String suffix = dbZxGcsgCtContrApply.getContractNo()
						.substring(dbZxGcsgCtContrApply.getContractNo().lastIndexOf("补") + 1);
				String number = String.valueOf(Integer.parseInt(suffix.substring(suffix.lastIndexOf("0") + 1)) + 1);
				switch (number.length()) {
				case 1:
					contractNo = prefix + "0" + number;
					break;
				default:
					contractNo = prefix + number;
					break;
				}
			}
		}
		// 数据存在
		return repEntity.ok(contractNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity psUpdateZxGcsgCtContrApply(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null) {
			// check审核中的不允许修改
			if (StrUtil.equals("1", dbZxGcsgCtContrApply.getApih5FlowStatus())
					|| StrUtil.equals("2", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
				return repEntity.layerMessage("no", "该合同评审正在审核中或已通过审核,不允许修改。");
			}
			// dbZxGcsgCtContrApply.setSecondOrgType("施工协作单位");
			dbZxGcsgCtContrApply.setContractName(zxGcsgCtContrApply.getContractName());
			dbZxGcsgCtContrApply.setAgent(zxGcsgCtContrApply.getAgent());
			dbZxGcsgCtContrApply.setIsDeduct(zxGcsgCtContrApply.getIsDeduct());
			dbZxGcsgCtContrApply.setStartDate(zxGcsgCtContrApply.getStartDate());
			dbZxGcsgCtContrApply.setEndDate(zxGcsgCtContrApply.getEndDate());
			dbZxGcsgCtContrApply.setLegalPerson(zxGcsgCtContrApply.getLegalPerson());
			dbZxGcsgCtContrApply.setAgentPerson(zxGcsgCtContrApply.getAgentPerson());
			dbZxGcsgCtContrApply.setChargePerson(zxGcsgCtContrApply.getChargePerson());
			dbZxGcsgCtContrApply.setIsBiddedOnCloud(zxGcsgCtContrApply.getIsBiddedOnCloud());
			dbZxGcsgCtContrApply.setBidType(zxGcsgCtContrApply.getBidType());
			dbZxGcsgCtContrApply.setBidOrgType(zxGcsgCtContrApply.getBidOrgType());
			dbZxGcsgCtContrApply.setIsAllDepartJoin(zxGcsgCtContrApply.getIsAllDepartJoin());
			dbZxGcsgCtContrApply.setJoinType(zxGcsgCtContrApply.getJoinType());
			dbZxGcsgCtContrApply.setIsDepartJoinBid(zxGcsgCtContrApply.getIsDepartJoinBid());
			dbZxGcsgCtContrApply.setContractDepart(zxGcsgCtContrApply.getContractDepart());
			dbZxGcsgCtContrApply.setContent(zxGcsgCtContrApply.getContent());
			String packageNo = "";
			String schemeNo = "";
			if (StrUtil.equals("1", zxGcsgCtContrApply.getIsBiddedOnCloud())
					&& StrUtil.isNotEmpty(zxGcsgCtContrApply.getCloudEcoID())) {
				ZxCtCloudEco dbZxCtCloudEco = zxCtCloudEcoMapper
						.getZxCtCloudEcoByPrimaryKey(zxGcsgCtContrApply.getCloudEcoID());
				if (dbZxCtCloudEco != null) {
					// 是否匹配已返回给前端,由前端check
					// check该包件编号未完全匹配!请在oa系统中将该包件编号对应的投标单位全部进行入库
					if (!StrUtil.equals("1", dbZxCtCloudEco.getIsRela())) {
						return repEntity.layerMessage("no", "该包件编号未完全匹配!请在oa系统中将该包件编号对应的投标单位全部进行入库。");
					}
					packageNo = dbZxCtCloudEco.getPackageNo();
					schemeNo = dbZxCtCloudEco.getSchemeNo();
				}
			}
			dbZxGcsgCtContrApply.setPackageNo(packageNo);
			dbZxGcsgCtContrApply.setCloudBidNo(schemeNo);
			dbZxGcsgCtContrApply.setRemarks(zxGcsgCtContrApply.getRemarks());
			dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			if (flag != 0) {
				// 一、修改合同评审清单
				ZxGcsgCtContrApplyWorks search = new ZxGcsgCtContrApplyWorks();
				search.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
				search.setParentID("-1");
				List<ZxGcsgCtContrApplyWorks> dbApplyWorks = zxGcsgCtContrApplyWorksMapper
						.selectByZxGcsgCtContrApplyWorksList(search);
				if (dbApplyWorks != null && dbApplyWorks.size() == 1) {
					dbApplyWorks.get(0).setWorkName(zxGcsgCtContrApply.getContractName());
					dbApplyWorks.get(0).setModifyUserInfo(userKey, realName);
					flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbApplyWorks.get(0));
				}
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApply);
		}
	}

	@Override
	public ResponseEntity psGetZxGcsgCtContrApplyList(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		String userId = TokenUtils.getUserId(request);
		// 分页查询
		PageHelper.startPage(zxGcsgCtContrApply.getPage(), zxGcsgCtContrApply.getLimit());
		// 集团全部可见
		if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxGcsgCtContrApply.setCompanyId(zxGcsgCtContrApply.getCompanyId());
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId(zxGcsgCtContrApply.getProjectId());
		}
		// 获取数据
		List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList = zxGcsgCtContrApplyMapper
				.psGetZxGcsgCtContrApplyList(zxGcsgCtContrApply);
		// 得到分页信息
		PageInfo<ZxGcsgCtContrApply> p = new PageInfo<>(zxGcsgCtContrApplyList);

		return repEntity.okList(zxGcsgCtContrApplyList, p.getTotal());
	}

	@Override
	public ResponseEntity bxGetZxGcsgCtContrApplyList(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		String userId = TokenUtils.getUserId(request);
		// 分页查询
		PageHelper.startPage(zxGcsgCtContrApply.getPage(), zxGcsgCtContrApply.getLimit());
		// 集团全部可见
		if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxGcsgCtContrApply.setCompanyId(zxGcsgCtContrApply.getCompanyId());
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId(zxGcsgCtContrApply.getProjectId());
		}
		// 分页查询
		PageHelper.startPage(zxGcsgCtContrApply.getPage(), zxGcsgCtContrApply.getLimit());
		// 获取数据
		List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList = zxGcsgCtContrApplyMapper
				.bxGetZxGcsgCtContrApplyList(zxGcsgCtContrApply);
		if (zxGcsgCtContrApplyList.size() > 0) {
			zxGcsgCtContrApplyList.stream().forEach(apply -> {
				ZxGcsgCommonAttachment attachment = new ZxGcsgCommonAttachment();
				attachment.setOtherId(apply.getCtContrApplyId());
				attachment.setFileType("1");
				List<ZxGcsgCommonAttachment> attachmentList = zxGcsgCommonAttachmentMapper
						.selectByZxGcsgCommonAttachmentList(attachment);
				apply.setAttachmentList(attachmentList);
			});
		}
		// 得到分页信息
		PageInfo<ZxGcsgCtContrApply> p = new PageInfo<>(zxGcsgCtContrApplyList);
		return repEntity.okList(zxGcsgCtContrApplyList, p.getTotal());
	}

	@Override
	public void psExportZxGcsgCtContrApplyExcel(ZxGcsgCtContrApply zxGcsgCtContrApply, HttpServletResponse response) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		String userId = TokenUtils.getUserId(request);
		// 集团全部可见
		if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxGcsgCtContrApply.setCompanyId(zxGcsgCtContrApply.getCompanyId());
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId(zxGcsgCtContrApply.getProjectId());
		}
		// 获取数据
		List<ZxGcsgCtContrApply> dataList = zxGcsgCtContrApplyMapper.psGetZxGcsgCtContrApplyList(zxGcsgCtContrApply);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("合同编号", "合同名称", "合同类型", "甲方名称", "乙方名称", "协作单位类型", "合同签订人", "含税合同金额(万元)",
				"是否抵扣", "开工日期", "竣工日期", "现场负责人", "委托代理人", "法定代表人", "合同所属事业部", "发起人", "评审状态");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZxGcsgCtContrApply dbData : dataList) {
				String contractType = dbData.getContractType();
				switch (contractType) {
				case "P2":
					contractType = "工程施工类";
					break;
				default:
					break;
				}
				String isDeduct = StrUtil.equals("1", dbData.getIsDeduct()) ? "是" : "否";
				String contractDepart = dbData.getContractDepart();
				switch (contractDepart) {
				case "glsz":
					contractDepart = "公路市政事业部";
					break;
				case "tlgd":
					contractDepart = "铁路轨道事业部";
					break;
				case "csfj":
					contractDepart = "城市房建事业部";
					break;
				default:
					break;
				}
				String apih5FlowStatus = dbData.getApih5FlowStatus();
				switch (apih5FlowStatus) {
				case "-1":
					apih5FlowStatus = "未评审";
					break;
				case "2":
					apih5FlowStatus = "评审通过";
					break;
				default:
					apih5FlowStatus = "正在评审";
					break;
				}
				rowsList.add(CollUtil.newArrayList(dbData.getContractNo(), dbData.getContractName(), contractType,
						dbData.getFirstName(), dbData.getSecondName(), dbData.getSecondOrgType(), dbData.getAgent(),
						dbData.getContractCost(), isDeduct, DateUtil.format(dbData.getStartDate(), "yyyy-MM-dd"),
						DateUtil.format(dbData.getEndDate(), "yyyy-MM-dd"), dbData.getLegalPerson(),
						dbData.getAgentPerson(), dbData.getChargePerson(), contractDepart, dbData.getBeginPer(),
						apih5FlowStatus));
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLUtil.encode("工程施工合同评审列表.xlsx", "UTF-8"));
		// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out为OutputStream，需要写出到的目标流
		ServletOutputStream out = null;
		try {
			// response.setHeader("Content-Disposition",
			// "attachment; filename=\"" + new String("工程施工合同评审列表".getBytes("utf-8"),
			// "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, "工程施工合同评审列表");
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

	@Override
	public void bxExportZxGcsgCtContrApplyExcel(ZxGcsgCtContrApply zxGcsgCtContrApply, HttpServletResponse response) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		String userId = TokenUtils.getUserId(request);
		// 集团全部可见
		if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxGcsgCtContrApply.setCompanyId(zxGcsgCtContrApply.getCompanyId());
			zxGcsgCtContrApply.setProjectId("");
		} else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxGcsgCtContrApply.setCompanyId("");
			zxGcsgCtContrApply.setProjectId(zxGcsgCtContrApply.getProjectId());
		}
		// 获取数据
		List<ZxGcsgCtContrApply> dataList = zxGcsgCtContrApplyMapper.bxGetZxGcsgCtContrApplyList(zxGcsgCtContrApply);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("补充协议编号", "补充协议名称", "合同名称", "合同类型", "甲方名称", "乙方名称", "合同签订人", "含税合同金额(万元)",
				"本期含税增减金额(万元)", "变更后含税金额", "是否抵扣", "开工日期", "竣工日期", "发起人", "评审状态");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZxGcsgCtContrApply dbData : dataList) {
				String contractType = dbData.getContractType();
				switch (contractType) {
				case "P2C":
					contractType = "工程施工类补充协议";
					break;
				default:
					break;
				}
				String isDeduct = StrUtil.equals("1", dbData.getIsDeduct()) ? "是" : "否";
				String apih5FlowStatus = dbData.getApih5FlowStatus();
				switch (apih5FlowStatus) {
				case "-1":
					apih5FlowStatus = "未评审";
					break;
				case "2":
					apih5FlowStatus = "评审通过";
					break;
				default:
					apih5FlowStatus = "正在评审";
					break;
				}
				rowsList.add(CollUtil.newArrayList(dbData.getContractNo(), dbData.getAgreementName(),
						dbData.getContractName(), contractType, dbData.getFirstName(), dbData.getSecondName(),
						dbData.getAgent(), dbData.getContractCost(), dbData.getCurrentTaxAmount(),
						dbData.getAlterContractSum(), isDeduct, DateUtil.format(dbData.getStartDate(), "yyyy-MM-dd"),
						DateUtil.format(dbData.getEndDate(), "yyyy-MM-dd"), dbData.getBeginPer(), apih5FlowStatus));
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
		response.setHeader("Content-Disposition",
				"attachment; filename=" + URLUtil.encode("工程施工合同补充协议列表.xlsx", "UTF-8"));
		// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out为OutputStream，需要写出到的目标流
		ServletOutputStream out = null;
		try {
			// response.setHeader("Content-Disposition",
			// "attachment; filename=\"" + new String("工程施工合同补充协议列表".getBytes("utf-8"),
			// "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, "工程施工合同补充协议列表");
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

	@Override
	public ResponseEntity psCheckZxGcsgCtContrApplyInFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null) {
			// 规则一：当合同含税金额超过400万此项内容必须选择‘是’，否则无法发起合同评审
			// （发起合同评审时，系统需提醒客户合同含税金额大于400万，需要通过云电商进行招标）
			if (dbZxGcsgCtContrApply.getContractCost().compareTo(new BigDecimal(400)) > 0
					&& StrUtil.equals("0", dbZxGcsgCtContrApply.getIsBiddedOnCloud())) {
				return repEntity.layerMessage("no", "合同含税金额大于400万，需要通过云电商进行招标。");
			}
			// 规则二：注意当“是否通过云电商分包招标”字段选择为‘是’时，该项为必填项。
			// 不填写不准发起合同评审（发起合同评审时，系统需提醒客户填写这个字段）；
			if (StrUtil.equals("1", dbZxGcsgCtContrApply.getIsBiddedOnCloud())
					&& StrUtil.isEmpty(dbZxGcsgCtContrApply.getPackageNo())) {
				return repEntity.layerMessage("no", "当通过云电商分包招标时,包件编号为必填项");
			}
			// 规则三：如果（合同单价分析）页面存在‘不含税单价’为0 时，该工程合同是无法发起评审的，
			// 系统应提示用户“请完善合同单价分析数据”。
			ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
			zxGcsgCtPriceSys.setCtContrApplyId(dbZxGcsgCtContrApply.getCtContrApplyId());
			int count = zxGcsgCtPriceSysMapper.checkZxGcsgCtPriceSysBeforeFlow(zxGcsgCtPriceSys);
			if (count > 0) {
				return repEntity.layerMessage("no", "请完善合同单价分析数据。");
			}
			// 规则四：不能重复发起评审
			// if (StrUtil.isNotEmpty(dbZxGcsgCtContrApply.getApih5FlowStatus())
			// && !StrUtil.equals("-1", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
			// return repEntity.layerMessage("no", "已发起评审的数据不能再次发起评审。");
			// }
		}
		return repEntity.ok(true);
	}

	@Override
	public ResponseEntity bxCheckZxGcsgCtContrApplyInFlow(ZxGcsgCtContrApply zxGcsgCtContrApply) {
		if (zxGcsgCtContrApply == null) {
			zxGcsgCtContrApply = new ZxGcsgCtContrApply();
		}
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
				.selectByPrimaryKey(zxGcsgCtContrApply.getCtContrApplyId());
		if (dbZxGcsgCtContrApply != null) {
			// 规则一：（1）在点击发起补充协议合同评审时候进行限制 超过限制条件的数据弹出 提示：
			// 【补充协议 累计含税合同金额 超过原主合同含税金额的15% 或
			// 此主合同累计建立补充协议份数超过5份，不准发起评审。如有问题请咨询局合同管理与结算中心】
			BigDecimal fifPercent = CalcUtils.calcMultiply(dbZxGcsgCtContrApply.getContractCost(),
					new BigDecimal(0.15));
			BigDecimal difference = CalcUtils.calcSubtract(dbZxGcsgCtContrApply.getAlterContractSum(),
					dbZxGcsgCtContrApply.getContractCost());
			if (difference.compareTo(fifPercent) > 0) {
				return repEntity.layerMessage("no", "补充协议累计含税合同金额超过原主合同含税金额的15%。");
			}
			// 规则二：
			ZxGcsgCtContrApply ctContrApply = new ZxGcsgCtContrApply();
			ctContrApply.setCtContractId(dbZxGcsgCtContrApply.getCtContractId());
			List<ZxGcsgCtContrApply> countList = zxGcsgCtContrApplyMapper.selectByZxGcsgCtContrApplyList(ctContrApply);
			if (countList.size() > 5) {
				return repEntity.layerMessage("no", "此主合同累计建立补充协议份数超过5份,不准发起评审,如有问题请咨询局合同管理与结算中心。");
			}
			// 规则三：不能重复发起评审
			// if (StrUtil.isNotEmpty(dbZxGcsgCtContrApply.getApih5FlowStatus())
			// && !StrUtil.equals("-1", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
			// return repEntity.layerMessage("no", "已发起评审的数据不能再次发起评审。");
			// }
		}
		return repEntity.ok(true);
	}

}
