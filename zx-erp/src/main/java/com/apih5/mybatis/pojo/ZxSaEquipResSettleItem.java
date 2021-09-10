package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxSaEquipResSettleItem extends BasePojo {
    // 主键
    private String zxSaEquipResSettleItemId;

    // 清单结算id
    private String zxSaEquipResSettleId;

    // 合同id
    private String contractID;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 签认单编号
    private String signedNos;

    // 合同明细id
    private String contractItemID;

    // 设备编号
    private String equipCode;

    // 设备名称
    private String equipName;

    // 型号
    private String spec;

    // 单位
    private String unit;

    // 起租日期
    private Date startDate;

    // 单价(元)
    private BigDecimal contractPrice;
    
    //不含税单价
    private BigDecimal contractPriceNoTax;

    // 合同数量
    private BigDecimal contractQty;

    // 合同金额(元)
    private BigDecimal contractAmt;
    
    //原合同不含税金额
    private BigDecimal contractAmtNoTax;
    
    //原合同税额
    private BigDecimal contractAmtTax;
    
    // 变更后数量
    private BigDecimal changedQty;

    // 变更后金额(元)
    private BigDecimal changedAmt;
    
    //变更后不含税金额
    private BigDecimal changedAmtNoTax;
    
    //变更后税额
    private BigDecimal changedAmtTax;
    
    // 税率(%)
    private String taxRate;

    // 本期结算数量
    private BigDecimal thisQty;

    // 本期结算单价(元)
    private BigDecimal thisPrice;

    // 本期结算含税金额(元)
    private BigDecimal thisAmt;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 至上期末累计结算数量
    private BigDecimal upQty;

    // 至上期末累计结算金额(元)
    private BigDecimal upAmt;
    
    // 至上期末累计不含税金额(元)
    private BigDecimal upAmtNoTax;

    // 至上期末累计税额(元)
    private BigDecimal upAmtTax;

    // 至本期末累计结算数量
    private BigDecimal totalQty;

    // 至本期末累计结算金额(元)
    private BigDecimal totalAmt;
    
    // 至本期末累计不含税金额(元)
    private BigDecimal totalAmtNoTax;

    // 至本期末累计税额(元)
    private BigDecimal totalAmtTax;

    // 本期运转台时(小时)
    private BigDecimal runHour;

    // 本期油耗(L)
    private BigDecimal useOil;

    // 备注
    private String remark;

    // 所属公司id
    private String comID;

    // 所属公司名称
    private String comName;
    
    //是否抵扣
    private String isDeduct;
    
    private String zxSaEquipSettleAuditId;
    
    //项目名称=甲方
    private String orgName;
    //乙方
    private String secondName;
    //合同号
    private String contractNo;
    
    private String zxSaEquipResSettleItemIdNotFlag;

    public String getZxSaEquipResSettleItemId() {
        return zxSaEquipResSettleItemId == null ? "" : zxSaEquipResSettleItemId.trim();
    }

    public void setZxSaEquipResSettleItemId(String zxSaEquipResSettleItemId) {
        this.zxSaEquipResSettleItemId = zxSaEquipResSettleItemId == null ? null : zxSaEquipResSettleItemId.trim();
    }

    public String getZxSaEquipResSettleId() {
        return zxSaEquipResSettleId == null ? "" : zxSaEquipResSettleId.trim();
    }

    public void setZxSaEquipResSettleId(String zxSaEquipResSettleId) {
        this.zxSaEquipResSettleId = zxSaEquipResSettleId == null ? null : zxSaEquipResSettleId.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
    }

    public String getContractItemID() {
        return contractItemID == null ? "" : contractItemID.trim();
    }

    public void setContractItemID(String contractItemID) {
        this.contractItemID = contractItemID == null ? null : contractItemID.trim();
    }

    public String getEquipCode() {
        return equipCode == null ? "" : equipCode.trim();
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode == null ? null : equipCode.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getContractQty() {
        return contractQty;
    }

    public void setContractQty(BigDecimal contractQty) {
        this.contractQty = contractQty;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getChangedQty() {
        return changedQty;
    }

    public void setChangedQty(BigDecimal changedQty) {
        this.changedQty = changedQty;
    }

    public BigDecimal getChangedAmt() {
        return changedAmt;
    }

    public void setChangedAmt(BigDecimal changedAmt) {
        this.changedAmt = changedAmt;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
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

    public BigDecimal getUpQty() {
        return upQty;
    }

    public void setUpQty(BigDecimal upQty) {
        this.upQty = upQty;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getRunHour() {
        return runHour;
    }

    public void setRunHour(BigDecimal runHour) {
        this.runHour = runHour;
    }

    public BigDecimal getUseOil() {
        return useOil;
    }

    public void setUseOil(BigDecimal useOil) {
        this.useOil = useOil;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

	public BigDecimal getUpAmtNoTax() {
		return upAmtNoTax;
	}

	public void setUpAmtNoTax(BigDecimal upAmtNoTax) {
		this.upAmtNoTax = upAmtNoTax;
	}

	public BigDecimal getUpAmtTax() {
		return upAmtTax;
	}

	public void setUpAmtTax(BigDecimal upAmtTax) {
		this.upAmtTax = upAmtTax;
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

	public String getIsDeduct() {
		return isDeduct;
	}

	public void setIsDeduct(String isDeduct) {
		this.isDeduct = isDeduct;
	}

	public BigDecimal getChangedAmtNoTax() {
		return changedAmtNoTax;
	}

	public void setChangedAmtNoTax(BigDecimal changedAmtNoTax) {
		this.changedAmtNoTax = changedAmtNoTax;
	}

	public BigDecimal getChangedAmtTax() {
		return changedAmtTax;
	}

	public void setChangedAmtTax(BigDecimal changedAmtTax) {
		this.changedAmtTax = changedAmtTax;
	}

	public BigDecimal getContractAmtNoTax() {
		return contractAmtNoTax;
	}

	public void setContractAmtNoTax(BigDecimal contractAmtNoTax) {
		this.contractAmtNoTax = contractAmtNoTax;
	}

	public BigDecimal getContractAmtTax() {
		return contractAmtTax;
	}

	public void setContractAmtTax(BigDecimal contractAmtTax) {
		this.contractAmtTax = contractAmtTax;
	}

	public BigDecimal getContractPriceNoTax() {
		return contractPriceNoTax;
	}

	public void setContractPriceNoTax(BigDecimal contractPriceNoTax) {
		this.contractPriceNoTax = contractPriceNoTax;
	}
	
    public String getZxSaEquipSettleAuditId() {
        return zxSaEquipSettleAuditId == null ? "" : zxSaEquipSettleAuditId.trim();
    }

    public void setZxSaEquipSettleAuditId(String zxSaEquipSettleAuditId) {
        this.zxSaEquipSettleAuditId = zxSaEquipSettleAuditId == null ? null : zxSaEquipSettleAuditId.trim();
    }

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getZxSaEquipResSettleItemIdNotFlag() {
		return zxSaEquipResSettleItemIdNotFlag;
	}

	public void setZxSaEquipResSettleItemIdNotFlag(String zxSaEquipResSettleItemIdNotFlag) {
		this.zxSaEquipResSettleItemIdNotFlag = zxSaEquipResSettleItemIdNotFlag;
	}
    
}
