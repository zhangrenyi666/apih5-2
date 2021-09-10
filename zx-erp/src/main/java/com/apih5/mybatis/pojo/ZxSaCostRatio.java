package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 结算管理-数据填报-累计结算成本比指标填报（原表iesa_CostRatio）
 *
 * @author jobob
 * @since 2021-03-17
 */
public class ZxSaCostRatio extends BasePojo {

	private static final long serialVersionUID = 1L;

	private String costRatioId;

	@ApiModelProperty(value = "项目ID")
	private String orgID;

	@ApiModelProperty(value = "项目名称")
	private String orgName;

	@ApiModelProperty(value = "期次")
	private String period;

	@ApiModelProperty(value = "期次时间蹉")
	private Date periodTime;

	@ApiModelProperty(value = "填报人")
	private String reporter;

	@ApiModelProperty(value = "填报时间")
	private Date reporterTime;

	@ApiModelProperty(value = "开累结算计入成本金额（万元）")
	private BigDecimal totalAmt;

	@ApiModelProperty(value = "库存金额（万元）")
	private BigDecimal stockAmt;

	@ApiModelProperty(value = "财务账面成本（万元）")
	private BigDecimal bookAmt;

	@ApiModelProperty(value = "已发生未入账的成本（万元）")
	private BigDecimal unrecordAmt;

	@ApiModelProperty(value = "研发支出费用（万元）")
	private BigDecimal rdAmt;

	@ApiModelProperty(value = "审核状态")
	private String auditStatus;

	@ApiModelProperty(value = "公司id")
	private String companyId;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "项目id")
	private String projectId;

	@ApiModelProperty(value = "项目开工日期")
	private Date actualStartDate;

	@ApiModelProperty(value = "主体完工日期")
	private Date mainEndDate;

	@ApiModelProperty(value = "项目交工日期")
	private Date deliveryDate;

	@ApiModelProperty(value = "项目竣工日期")
	private Date actualEndDate;

	@ApiModelProperty(value = "项目归档日期")
	private Date docDate;

	@ApiModelProperty(value = "部门id")
	private String departmentId;

	@ApiModelProperty(value = "部门名称")
	private String departmentName;

	@ApiModelProperty(value = "部门全称")
	private String departmentFullName;

	@ApiModelProperty(value = "部门路径")
	private String departmentPath;

	@ApiModelProperty(value = "本月应执行合同份数(有做过建立首张结算单数据后且未做【评审通过最终结算】的合同份数)")
	private BigDecimal thisMonthContractNum;

	@ApiModelProperty(value = "本月结算合同份数(上面结果中本期+通过)")
	private BigDecimal thisMonthSettleContractNum;

	@ApiModelProperty(value = "工程类合同月度执行率指标值")
	private BigDecimal thisMonthIndexValue;

	@ApiModelProperty(value = "工程类合同月度执行率指标是否合格 0:否 1:是")
	private String thisMonthIndexValueQualifiedFlag;

	@ApiModelProperty(value = "本月执行率合格在建、主体完工项目数量")
	private BigDecimal thisMonthQualifiedConstructionProjectNum;

	@ApiModelProperty(value = "本月在建、主体完工项目总数量")
	private BigDecimal thisMonthConstructionAllProjectNum;

	@ApiModelProperty(value = "工程类合同月度执行率项目合格率指标值")
	private BigDecimal thisMonthQualifiedIndexValue;

	@ApiModelProperty(value = "本月仍有应执行合同的归档项目数量")
	private BigDecimal thisMonthHaveFileProjectNum;

	@ApiModelProperty(value = "本月在建、主体完工项目、交工、归档总数量")
	private BigDecimal thisMonthConstructionFileAllNum;

	@ApiModelProperty(value = "归档项目个数比指标值%")
	private BigDecimal ratioOfArchiveItemsIndexValue;

	@ApiModelProperty(value = "累计结算成本比指标值")
	private BigDecimal totalSettleIndexValue;

	@ApiModelProperty(value = "累计结算成本比指标值是否合格 0:否 1:是")
	private String totalSettleQualifiedFlag;

	@ApiModelProperty(value = "本月合格的在建、主体完工、交工、归档项目数量")
	private BigDecimal thisMonthQualifiedConstructionFileAllNum;

	@ApiModelProperty(value = "本月在建、主体完工、交工、归档总数量")
	private BigDecimal thisMonthConstructionFileTotalNum;

	@ApiModelProperty(value = "累计结算成本比合格率")
	private BigDecimal totalSettleQualifiedValue;

	@ApiModelProperty(value = "父ID")
	private String pid;

	@ApiModelProperty(value = "所属事业部")
	private String bizDep;

	@ApiModelProperty(value = "分子公司、事业部Flag")
	private String businessDivisionFlag;

	@ApiModelProperty(value = "实际开工日期")
	private Date realBeginDate;

	@ApiModelProperty(value = "完工审核的实际结算结束日期")
	private Date realSettleEndDate;

	@ApiModelProperty(value = "完工审核主键ID")
	private String finishId;

	@ApiModelProperty(value = "工程类合同本期上报份数")
	private BigDecimal gcThisReportNum;

	@ApiModelProperty(value = "工程类合同开累上报份数")
	private BigDecimal gcTotalReportNum;

	@ApiModelProperty(value = "工程类合同本期退回份数")
	private BigDecimal gcThisBackNum;

	@ApiModelProperty(value = "工程类合同开累退回份数")
	private BigDecimal gcTotalBackNum;

	@ApiModelProperty(value = "物资类合同本期上报份数")
	private BigDecimal wzThisReportNum;

	@ApiModelProperty(value = "物资类合同开累上报份数")
	private BigDecimal wzTotalReportNum;

	@ApiModelProperty(value = "物资类合同本期退回份数")
	private BigDecimal wzThisBackNum;

	@ApiModelProperty(value = "物资类合同开累退回份数")
	private BigDecimal wzTotalBackNum;

	@ApiModelProperty(value = "机械类合同本期上报份数")
	private BigDecimal jxThisReportNum;

	@ApiModelProperty(value = "机械类合同开累上报份数")
	private BigDecimal jxTotalReportNum;

	@ApiModelProperty(value = "机械类合同本期退回份数")
	private BigDecimal jxThisBackNum;

	@ApiModelProperty(value = "机械类合同开累退回份数")
	private BigDecimal jxTotalBackNum;

	@ApiModelProperty(value = "其他类合同本期上报份数")
	private BigDecimal qtThisReportNum;

	@ApiModelProperty(value = "其他类合同开累上报份数")
	private BigDecimal qtTotalReportNum;

	@ApiModelProperty(value = "其他类合同本期退回份数")
	private BigDecimal qtThisBackNum;

	@ApiModelProperty(value = "其他类合同开累退回份数")
	private BigDecimal qtTotalBackNum;

	@ApiModelProperty(value = "排序字段")
	private String treeNode;

	@ApiModelProperty(value = "deptFlag")
	private String deptFlag;

	@ApiModelProperty(value = "合同编号")
	private String contractNo;

	@ApiModelProperty(value = "变更后含税合同金额(元)")
	private BigDecimal alterContractSum;

	@ApiModelProperty(value = "年初累计(元)")
	private BigDecimal earlyYearTotalAmt;

	@ApiModelProperty(value = "一月结算金额(元)")
	private BigDecimal januaryTotalAmt;

	@ApiModelProperty(value = "二月结算金额(元)")
	private BigDecimal februaryTotalAmt;

	@ApiModelProperty(value = "三月结算金额(元)")
	private BigDecimal marchTotalAmt;

	@ApiModelProperty(value = "四月结算金额(元)")
	private BigDecimal aprilTotalAmt;

	@ApiModelProperty(value = "五月结算金额(元)")
	private BigDecimal mayTotalAmt;

	@ApiModelProperty(value = "六月结算金额(元)")
	private BigDecimal juneTotalAmt;

	@ApiModelProperty(value = "七月结算金额(元)")
	private BigDecimal julyTotalAmt;

	@ApiModelProperty(value = "八月结算金额(元)")
	private BigDecimal augustTotalAmt;

	@ApiModelProperty(value = "九月结算金额(元)")
	private BigDecimal septemberTotalAmt;

	@ApiModelProperty(value = "十月结算金额(元)")
	private BigDecimal octoberTotalAmt;

	@ApiModelProperty(value = "十一月结算金额(元)")
	private BigDecimal novemberTotalAmt;

	@ApiModelProperty(value = "十二月结算金额(元)")
	private BigDecimal decemberTotalAmt;

	@ApiModelProperty(value = "最后结算期次")
	private String lastPeriod;

	@ApiModelProperty(value = "查询期次上年末")
	private String upPeriod;

	@ApiModelProperty(value = "期次所属年份")
	private String periodYear;

	@ApiModelProperty(value = "合同金额（元）")
	private BigDecimal contractCost;

	@ApiModelProperty(value = "本月不含税金额")
	private BigDecimal thisAmtNoTax;

	@ApiModelProperty(value = "本月税额")
	private BigDecimal thisAmtTax;

	@ApiModelProperty(value = "本月含税金额")
	private BigDecimal thisAmt;

	@ApiModelProperty(value = "累计不含税金额")
	private BigDecimal totalAmtNoTax;

	@ApiModelProperty(value = "累计税额")
	private BigDecimal totalAmtTax;

	@ApiModelProperty(value = "本月扣除保证金")
	private BigDecimal thisKcAmt;

	@ApiModelProperty(value = "累计扣除保证金")
	private BigDecimal totalKcAmt;

	@ApiModelProperty(value = "本月返还保证金")
	private BigDecimal thisFhAmt;

	@ApiModelProperty(value = "累计返还保证金")
	private BigDecimal totalFhAmt;

	@ApiModelProperty(value = "本月应付款")
	private BigDecimal thisYfgckAmt;

	@ApiModelProperty(value = "累计应付款")
	private BigDecimal totalYfgckAmt;

	@ApiModelProperty(value = "合同类别")
	private String contractType;

	@ApiModelProperty(value = "乙方名称")
	private String secondName;

	@ApiModelProperty(value = "乙方负责人")
	private String secondPrincipal;

	@ApiModelProperty(value = "合同内容")
	private String content;

	@ApiModelProperty(value = "结算类型")
	private String billType;

	@ApiModelProperty(value = "结算单编号")
	private String billNo;

	@ApiModelProperty(value = "上期末结算单编号")
	private String upBillNo;

	@ApiModelProperty(value = "本月扣除质量保证金")
	private BigDecimal thisZlbzjKcAmt;

	@ApiModelProperty(value = "开累扣除质量保证金")
	private BigDecimal totalZlbzjKcAmt;

	@ApiModelProperty(value = "本月扣除安全生产保证金")
	private BigDecimal thisAqbzjKcAmt;

	@ApiModelProperty(value = "开累扣除安全生产保证金")
	private BigDecimal totalAqbzjKcAmt;

	@ApiModelProperty(value = "本月扣除农名工工资保证金")
	private BigDecimal thisGzbzjKcAmt;

	@ApiModelProperty(value = "开累扣除农名工工资保证金")
	private BigDecimal totalGzbzjKcAmt;

	@ApiModelProperty(value = "本月扣除其他保证金")
	private BigDecimal thisQtbzjKcAmt;

	@ApiModelProperty(value = "开累扣除其他保证金")
	private BigDecimal totalQtbzjKcAmt;

	@ApiModelProperty(value = "本月返还质量保证金")
	private BigDecimal thisZlbzjFhAmt;

	@ApiModelProperty(value = "开累返还质量保证金")
	private BigDecimal totalZlbzjFhAmt;

	@ApiModelProperty(value = "本月返还安全生产保证金")
	private BigDecimal thisAqbzjFhAmt;

	@ApiModelProperty(value = "开累返还安全生产保证金")
	private BigDecimal totalAqbzjFhAmt;

	@ApiModelProperty(value = "本月返还农名工工资保证金")
	private BigDecimal thisGzbzjFhAmt;

	@ApiModelProperty(value = "开累返还农名工工资保证金")
	private BigDecimal totalGzbzjFhAmt;

	@ApiModelProperty(value = "本月返还其他保证金")
	private BigDecimal thisQtbzjFhAmt;

	@ApiModelProperty(value = "开累返还其他保证金")
	private BigDecimal totalQtbzjFhAmt;

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondPrincipal() {
		return secondPrincipal;
	}

	public void setSecondPrincipal(String secondPrincipal) {
		this.secondPrincipal = secondPrincipal;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getUpBillNo() {
		return upBillNo;
	}

	public void setUpBillNo(String upBillNo) {
		this.upBillNo = upBillNo;
	}

	public BigDecimal getThisZlbzjKcAmt() {
		return thisZlbzjKcAmt;
	}

	public void setThisZlbzjKcAmt(BigDecimal thisZlbzjKcAmt) {
		this.thisZlbzjKcAmt = thisZlbzjKcAmt;
	}

	public BigDecimal getTotalZlbzjKcAmt() {
		return totalZlbzjKcAmt;
	}

	public void setTotalZlbzjKcAmt(BigDecimal totalZlbzjKcAmt) {
		this.totalZlbzjKcAmt = totalZlbzjKcAmt;
	}

	public BigDecimal getThisAqbzjKcAmt() {
		return thisAqbzjKcAmt;
	}

	public void setThisAqbzjKcAmt(BigDecimal thisAqbzjKcAmt) {
		this.thisAqbzjKcAmt = thisAqbzjKcAmt;
	}

	public BigDecimal getTotalAqbzjKcAmt() {
		return totalAqbzjKcAmt;
	}

	public void setTotalAqbzjKcAmt(BigDecimal totalAqbzjKcAmt) {
		this.totalAqbzjKcAmt = totalAqbzjKcAmt;
	}

	public BigDecimal getThisGzbzjKcAmt() {
		return thisGzbzjKcAmt;
	}

	public void setThisGzbzjKcAmt(BigDecimal thisGzbzjKcAmt) {
		this.thisGzbzjKcAmt = thisGzbzjKcAmt;
	}

	public BigDecimal getTotalGzbzjKcAmt() {
		return totalGzbzjKcAmt;
	}

	public void setTotalGzbzjKcAmt(BigDecimal totalGzbzjKcAmt) {
		this.totalGzbzjKcAmt = totalGzbzjKcAmt;
	}

	public BigDecimal getThisQtbzjKcAmt() {
		return thisQtbzjKcAmt;
	}

	public void setThisQtbzjKcAmt(BigDecimal thisQtbzjKcAmt) {
		this.thisQtbzjKcAmt = thisQtbzjKcAmt;
	}

	public BigDecimal getTotalQtbzjKcAmt() {
		return totalQtbzjKcAmt;
	}

	public void setTotalQtbzjKcAmt(BigDecimal totalQtbzjKcAmt) {
		this.totalQtbzjKcAmt = totalQtbzjKcAmt;
	}

	public BigDecimal getThisZlbzjFhAmt() {
		return thisZlbzjFhAmt;
	}

	public void setThisZlbzjFhAmt(BigDecimal thisZlbzjFhAmt) {
		this.thisZlbzjFhAmt = thisZlbzjFhAmt;
	}

	public BigDecimal getTotalZlbzjFhAmt() {
		return totalZlbzjFhAmt;
	}

	public void setTotalZlbzjFhAmt(BigDecimal totalZlbzjFhAmt) {
		this.totalZlbzjFhAmt = totalZlbzjFhAmt;
	}

	public BigDecimal getThisAqbzjFhAmt() {
		return thisAqbzjFhAmt;
	}

	public void setThisAqbzjFhAmt(BigDecimal thisAqbzjFhAmt) {
		this.thisAqbzjFhAmt = thisAqbzjFhAmt;
	}

	public BigDecimal getTotalAqbzjFhAmt() {
		return totalAqbzjFhAmt;
	}

	public void setTotalAqbzjFhAmt(BigDecimal totalAqbzjFhAmt) {
		this.totalAqbzjFhAmt = totalAqbzjFhAmt;
	}

	public BigDecimal getThisGzbzjFhAmt() {
		return thisGzbzjFhAmt;
	}

	public void setThisGzbzjFhAmt(BigDecimal thisGzbzjFhAmt) {
		this.thisGzbzjFhAmt = thisGzbzjFhAmt;
	}

	public BigDecimal getTotalGzbzjFhAmt() {
		return totalGzbzjFhAmt;
	}

	public void setTotalGzbzjFhAmt(BigDecimal totalGzbzjFhAmt) {
		this.totalGzbzjFhAmt = totalGzbzjFhAmt;
	}

	public BigDecimal getThisQtbzjFhAmt() {
		return thisQtbzjFhAmt;
	}

	public void setThisQtbzjFhAmt(BigDecimal thisQtbzjFhAmt) {
		this.thisQtbzjFhAmt = thisQtbzjFhAmt;
	}

	public BigDecimal getTotalQtbzjFhAmt() {
		return totalQtbzjFhAmt;
	}

	public void setTotalQtbzjFhAmt(BigDecimal totalQtbzjFhAmt) {
		this.totalQtbzjFhAmt = totalQtbzjFhAmt;
	}

	public BigDecimal getContractCost() {
		return contractCost;
	}

	public void setContractCost(BigDecimal contractCost) {
		this.contractCost = contractCost;
	}

	public BigDecimal getThisAmtNoTax() {
		return thisAmtNoTax;
	}

	public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
		this.thisAmtNoTax = thisAmtNoTax;
	}

	public BigDecimal getThisAmtTax() {
		return thisAmtTax;
	}

	public void setThisAmtTax(BigDecimal thisAmtTax) {
		this.thisAmtTax = thisAmtTax;
	}

	public BigDecimal getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(BigDecimal thisAmt) {
		this.thisAmt = thisAmt;
	}

	public BigDecimal getTotalAmtNoTax() {
		return totalAmtNoTax;
	}

	public void setTotalAmtNoTax(BigDecimal totalAmtNoTax) {
		this.totalAmtNoTax = totalAmtNoTax;
	}

	public BigDecimal getTotalAmtTax() {
		return totalAmtTax;
	}

	public void setTotalAmtTax(BigDecimal totalAmtTax) {
		this.totalAmtTax = totalAmtTax;
	}

	public BigDecimal getThisKcAmt() {
		return thisKcAmt;
	}

	public void setThisKcAmt(BigDecimal thisKcAmt) {
		this.thisKcAmt = thisKcAmt;
	}

	public BigDecimal getTotalKcAmt() {
		return totalKcAmt;
	}

	public void setTotalKcAmt(BigDecimal totalKcAmt) {
		this.totalKcAmt = totalKcAmt;
	}

	public BigDecimal getThisFhAmt() {
		return thisFhAmt;
	}

	public void setThisFhAmt(BigDecimal thisFhAmt) {
		this.thisFhAmt = thisFhAmt;
	}

	public BigDecimal getTotalFhAmt() {
		return totalFhAmt;
	}

	public void setTotalFhAmt(BigDecimal totalFhAmt) {
		this.totalFhAmt = totalFhAmt;
	}

	public BigDecimal getThisYfgckAmt() {
		return thisYfgckAmt;
	}

	public void setThisYfgckAmt(BigDecimal thisYfgckAmt) {
		this.thisYfgckAmt = thisYfgckAmt;
	}

	public BigDecimal getTotalYfgckAmt() {
		return totalYfgckAmt;
	}

	public void setTotalYfgckAmt(BigDecimal totalYfgckAmt) {
		this.totalYfgckAmt = totalYfgckAmt;
	}

	public String getCostRatioId() {
		return costRatioId;
	}

	public void setCostRatioId(String costRatioId) {
		this.costRatioId = costRatioId;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getAlterContractSum() {
		return alterContractSum;
	}

	public void setAlterContractSum(BigDecimal alterContractSum) {
		this.alterContractSum = alterContractSum;
	}

	public BigDecimal getEarlyYearTotalAmt() {
		return earlyYearTotalAmt;
	}

	public void setEarlyYearTotalAmt(BigDecimal earlyYearTotalAmt) {
		this.earlyYearTotalAmt = earlyYearTotalAmt;
	}

	public BigDecimal getJanuaryTotalAmt() {
		return januaryTotalAmt;
	}

	public void setJanuaryTotalAmt(BigDecimal januaryTotalAmt) {
		this.januaryTotalAmt = januaryTotalAmt;
	}

	public BigDecimal getFebruaryTotalAmt() {
		return februaryTotalAmt;
	}

	public void setFebruaryTotalAmt(BigDecimal februaryTotalAmt) {
		this.februaryTotalAmt = februaryTotalAmt;
	}

	public BigDecimal getMarchTotalAmt() {
		return marchTotalAmt;
	}

	public void setMarchTotalAmt(BigDecimal marchTotalAmt) {
		this.marchTotalAmt = marchTotalAmt;
	}

	public BigDecimal getAprilTotalAmt() {
		return aprilTotalAmt;
	}

	public void setAprilTotalAmt(BigDecimal aprilTotalAmt) {
		this.aprilTotalAmt = aprilTotalAmt;
	}

	public BigDecimal getMayTotalAmt() {
		return mayTotalAmt;
	}

	public void setMayTotalAmt(BigDecimal mayTotalAmt) {
		this.mayTotalAmt = mayTotalAmt;
	}

	public BigDecimal getJuneTotalAmt() {
		return juneTotalAmt;
	}

	public void setJuneTotalAmt(BigDecimal juneTotalAmt) {
		this.juneTotalAmt = juneTotalAmt;
	}

	public BigDecimal getJulyTotalAmt() {
		return julyTotalAmt;
	}

	public void setJulyTotalAmt(BigDecimal julyTotalAmt) {
		this.julyTotalAmt = julyTotalAmt;
	}

	public BigDecimal getAugustTotalAmt() {
		return augustTotalAmt;
	}

	public void setAugustTotalAmt(BigDecimal augustTotalAmt) {
		this.augustTotalAmt = augustTotalAmt;
	}

	public BigDecimal getSeptemberTotalAmt() {
		return septemberTotalAmt;
	}

	public void setSeptemberTotalAmt(BigDecimal septemberTotalAmt) {
		this.septemberTotalAmt = septemberTotalAmt;
	}

	public BigDecimal getOctoberTotalAmt() {
		return octoberTotalAmt;
	}

	public void setOctoberTotalAmt(BigDecimal octoberTotalAmt) {
		this.octoberTotalAmt = octoberTotalAmt;
	}

	public BigDecimal getNovemberTotalAmt() {
		return novemberTotalAmt;
	}

	public void setNovemberTotalAmt(BigDecimal novemberTotalAmt) {
		this.novemberTotalAmt = novemberTotalAmt;
	}

	public BigDecimal getDecemberTotalAmt() {
		return decemberTotalAmt;
	}

	public void setDecemberTotalAmt(BigDecimal decemberTotalAmt) {
		this.decemberTotalAmt = decemberTotalAmt;
	}

	public String getLastPeriod() {
		return lastPeriod;
	}

	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}

	public String getUpPeriod() {
		return upPeriod;
	}

	public void setUpPeriod(String upPeriod) {
		this.upPeriod = upPeriod;
	}

	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(Date periodTime) {
		this.periodTime = periodTime;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public Date getReporterTime() {
		return reporterTime;
	}

	public void setReporterTime(Date reporterTime) {
		this.reporterTime = reporterTime;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(BigDecimal stockAmt) {
		this.stockAmt = stockAmt;
	}

	public BigDecimal getBookAmt() {
		return bookAmt;
	}

	public void setBookAmt(BigDecimal bookAmt) {
		this.bookAmt = bookAmt;
	}

	public BigDecimal getUnrecordAmt() {
		return unrecordAmt;
	}

	public void setUnrecordAmt(BigDecimal unrecordAmt) {
		this.unrecordAmt = unrecordAmt;
	}

	public BigDecimal getRdAmt() {
		return rdAmt;
	}

	public void setRdAmt(BigDecimal rdAmt) {
		this.rdAmt = rdAmt;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getMainEndDate() {
		return mainEndDate;
	}

	public void setMainEndDate(Date mainEndDate) {
		this.mainEndDate = mainEndDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentFullName() {
		return departmentFullName;
	}

	public void setDepartmentFullName(String departmentFullName) {
		this.departmentFullName = departmentFullName;
	}

	public String getDepartmentPath() {
		return departmentPath;
	}

	public void setDepartmentPath(String departmentPath) {
		this.departmentPath = departmentPath;
	}

	public BigDecimal getThisMonthContractNum() {
		return thisMonthContractNum;
	}

	public void setThisMonthContractNum(BigDecimal thisMonthContractNum) {
		this.thisMonthContractNum = thisMonthContractNum;
	}

	public BigDecimal getThisMonthSettleContractNum() {
		return thisMonthSettleContractNum;
	}

	public void setThisMonthSettleContractNum(BigDecimal thisMonthSettleContractNum) {
		this.thisMonthSettleContractNum = thisMonthSettleContractNum;
	}

	public BigDecimal getThisMonthIndexValue() {
		return thisMonthIndexValue;
	}

	public void setThisMonthIndexValue(BigDecimal thisMonthIndexValue) {
		this.thisMonthIndexValue = thisMonthIndexValue;
	}

	public String getThisMonthIndexValueQualifiedFlag() {
		return thisMonthIndexValueQualifiedFlag;
	}

	public void setThisMonthIndexValueQualifiedFlag(String thisMonthIndexValueQualifiedFlag) {
		this.thisMonthIndexValueQualifiedFlag = thisMonthIndexValueQualifiedFlag;
	}

	public BigDecimal getThisMonthQualifiedConstructionProjectNum() {
		return thisMonthQualifiedConstructionProjectNum;
	}

	public void setThisMonthQualifiedConstructionProjectNum(BigDecimal thisMonthQualifiedConstructionProjectNum) {
		this.thisMonthQualifiedConstructionProjectNum = thisMonthQualifiedConstructionProjectNum;
	}

	public BigDecimal getThisMonthConstructionAllProjectNum() {
		return thisMonthConstructionAllProjectNum;
	}

	public void setThisMonthConstructionAllProjectNum(BigDecimal thisMonthConstructionAllProjectNum) {
		this.thisMonthConstructionAllProjectNum = thisMonthConstructionAllProjectNum;
	}

	public BigDecimal getThisMonthQualifiedIndexValue() {
		return thisMonthQualifiedIndexValue;
	}

	public void setThisMonthQualifiedIndexValue(BigDecimal thisMonthQualifiedIndexValue) {
		this.thisMonthQualifiedIndexValue = thisMonthQualifiedIndexValue;
	}

	public BigDecimal getThisMonthHaveFileProjectNum() {
		return thisMonthHaveFileProjectNum;
	}

	public void setThisMonthHaveFileProjectNum(BigDecimal thisMonthHaveFileProjectNum) {
		this.thisMonthHaveFileProjectNum = thisMonthHaveFileProjectNum;
	}

	public BigDecimal getThisMonthConstructionFileAllNum() {
		return thisMonthConstructionFileAllNum;
	}

	public void setThisMonthConstructionFileAllNum(BigDecimal thisMonthConstructionFileAllNum) {
		this.thisMonthConstructionFileAllNum = thisMonthConstructionFileAllNum;
	}

	public BigDecimal getRatioOfArchiveItemsIndexValue() {
		return ratioOfArchiveItemsIndexValue;
	}

	public void setRatioOfArchiveItemsIndexValue(BigDecimal ratioOfArchiveItemsIndexValue) {
		this.ratioOfArchiveItemsIndexValue = ratioOfArchiveItemsIndexValue;
	}

	public BigDecimal getTotalSettleIndexValue() {
		return totalSettleIndexValue;
	}

	public void setTotalSettleIndexValue(BigDecimal totalSettleIndexValue) {
		this.totalSettleIndexValue = totalSettleIndexValue;
	}

	public String getTotalSettleQualifiedFlag() {
		return totalSettleQualifiedFlag;
	}

	public void setTotalSettleQualifiedFlag(String totalSettleQualifiedFlag) {
		this.totalSettleQualifiedFlag = totalSettleQualifiedFlag;
	}

	public BigDecimal getThisMonthQualifiedConstructionFileAllNum() {
		return thisMonthQualifiedConstructionFileAllNum;
	}

	public void setThisMonthQualifiedConstructionFileAllNum(BigDecimal thisMonthQualifiedConstructionFileAllNum) {
		this.thisMonthQualifiedConstructionFileAllNum = thisMonthQualifiedConstructionFileAllNum;
	}

	public BigDecimal getThisMonthConstructionFileTotalNum() {
		return thisMonthConstructionFileTotalNum;
	}

	public void setThisMonthConstructionFileTotalNum(BigDecimal thisMonthConstructionFileTotalNum) {
		this.thisMonthConstructionFileTotalNum = thisMonthConstructionFileTotalNum;
	}

	public BigDecimal getTotalSettleQualifiedValue() {
		return totalSettleQualifiedValue;
	}

	public void setTotalSettleQualifiedValue(BigDecimal totalSettleQualifiedValue) {
		this.totalSettleQualifiedValue = totalSettleQualifiedValue;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBizDep() {
		return bizDep;
	}

	public void setBizDep(String bizDep) {
		this.bizDep = bizDep;
	}

	public String getBusinessDivisionFlag() {
		return businessDivisionFlag;
	}

	public void setBusinessDivisionFlag(String businessDivisionFlag) {
		this.businessDivisionFlag = businessDivisionFlag;
	}

	public Date getRealBeginDate() {
		return realBeginDate;
	}

	public void setRealBeginDate(Date realBeginDate) {
		this.realBeginDate = realBeginDate;
	}

	public Date getRealSettleEndDate() {
		return realSettleEndDate;
	}

	public void setRealSettleEndDate(Date realSettleEndDate) {
		this.realSettleEndDate = realSettleEndDate;
	}

	public String getFinishId() {
		return finishId;
	}

	public void setFinishId(String finishId) {
		this.finishId = finishId;
	}

	public BigDecimal getGcThisReportNum() {
		return gcThisReportNum;
	}

	public void setGcThisReportNum(BigDecimal gcThisReportNum) {
		this.gcThisReportNum = gcThisReportNum;
	}

	public BigDecimal getGcTotalReportNum() {
		return gcTotalReportNum;
	}

	public void setGcTotalReportNum(BigDecimal gcTotalReportNum) {
		this.gcTotalReportNum = gcTotalReportNum;
	}

	public BigDecimal getGcThisBackNum() {
		return gcThisBackNum;
	}

	public void setGcThisBackNum(BigDecimal gcThisBackNum) {
		this.gcThisBackNum = gcThisBackNum;
	}

	public BigDecimal getGcTotalBackNum() {
		return gcTotalBackNum;
	}

	public void setGcTotalBackNum(BigDecimal gcTotalBackNum) {
		this.gcTotalBackNum = gcTotalBackNum;
	}

	public BigDecimal getWzThisReportNum() {
		return wzThisReportNum;
	}

	public void setWzThisReportNum(BigDecimal wzThisReportNum) {
		this.wzThisReportNum = wzThisReportNum;
	}

	public BigDecimal getWzTotalReportNum() {
		return wzTotalReportNum;
	}

	public void setWzTotalReportNum(BigDecimal wzTotalReportNum) {
		this.wzTotalReportNum = wzTotalReportNum;
	}

	public BigDecimal getWzThisBackNum() {
		return wzThisBackNum;
	}

	public void setWzThisBackNum(BigDecimal wzThisBackNum) {
		this.wzThisBackNum = wzThisBackNum;
	}

	public BigDecimal getWzTotalBackNum() {
		return wzTotalBackNum;
	}

	public void setWzTotalBackNum(BigDecimal wzTotalBackNum) {
		this.wzTotalBackNum = wzTotalBackNum;
	}

	public BigDecimal getJxThisReportNum() {
		return jxThisReportNum;
	}

	public void setJxThisReportNum(BigDecimal jxThisReportNum) {
		this.jxThisReportNum = jxThisReportNum;
	}

	public BigDecimal getJxTotalReportNum() {
		return jxTotalReportNum;
	}

	public void setJxTotalReportNum(BigDecimal jxTotalReportNum) {
		this.jxTotalReportNum = jxTotalReportNum;
	}

	public BigDecimal getJxThisBackNum() {
		return jxThisBackNum;
	}

	public void setJxThisBackNum(BigDecimal jxThisBackNum) {
		this.jxThisBackNum = jxThisBackNum;
	}

	public BigDecimal getJxTotalBackNum() {
		return jxTotalBackNum;
	}

	public void setJxTotalBackNum(BigDecimal jxTotalBackNum) {
		this.jxTotalBackNum = jxTotalBackNum;
	}

	public BigDecimal getQtThisReportNum() {
		return qtThisReportNum;
	}

	public void setQtThisReportNum(BigDecimal qtThisReportNum) {
		this.qtThisReportNum = qtThisReportNum;
	}

	public BigDecimal getQtTotalReportNum() {
		return qtTotalReportNum;
	}

	public void setQtTotalReportNum(BigDecimal qtTotalReportNum) {
		this.qtTotalReportNum = qtTotalReportNum;
	}

	public BigDecimal getQtThisBackNum() {
		return qtThisBackNum;
	}

	public void setQtThisBackNum(BigDecimal qtThisBackNum) {
		this.qtThisBackNum = qtThisBackNum;
	}

	public BigDecimal getQtTotalBackNum() {
		return qtTotalBackNum;
	}

	public void setQtTotalBackNum(BigDecimal qtTotalBackNum) {
		this.qtTotalBackNum = qtTotalBackNum;
	}

	public String getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode;
	}

	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
