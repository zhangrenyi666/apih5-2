package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesLeaseResSettleItem extends BasePojo {
    // 主键
    private String zxCtSuppliesLeaseSettlementItemId;

    // 最后编辑时间
    private Date editTime;

    // 租期单位
    private String rentUnit;

    // 主表ID
    private String mainID;

    // 至上期末累计结算数量*租期
    private BigDecimal upQty;

    // 至上期末累计结算金额(元)
    private BigDecimal upAmt;

    // 至本期末累计结算数量*租期
    private BigDecimal totalQty;

    // 至本期末累计结算金额(元)
    private BigDecimal totalAmt;

    // 序号
    private int orderNum=0;

    // 物资名称
    private String equipName;

    // 物资编码
    private String equipCode;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 税率(%)
    private String taxRate;

    // 签认单明细ID
    private String signedOrderItemID;

    // 签认单编号
    private String signedNo;

    // 起租日期
    private Date startDate;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 计量单位
    private String unit;

    // 合同租期
    private BigDecimal contrTrrm;

    // 合同数量
    private BigDecimal contractQty;

    // 合同明细ID
    private String contractItemID;

    // 合同金额(元)
    private BigDecimal contractAmt;

    // 合同ID
    private String contractID;

    // 规格型号
    private String spec;

    // 单价(元)
    private BigDecimal contractPrice;

    // 变更后租期
    private BigDecimal alterTrrm;

    // 变更后数量
    private BigDecimal changedQty;

    // 变更后金额(元)
    private BigDecimal changedAmt;

    // 变更后单价(元)
    private BigDecimal alterPrice;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 本期结算数量*租期
    private BigDecimal thisQty;

    // 本期结算金额(元)
    private BigDecimal thisAmt;

    // 本期结算单价(元)
    private BigDecimal thisPrice;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;
    
    // 本期结算租期（签认单）
    private BigDecimal signContrTrrm;
    
    // 本期结算数量（签认单）
    private BigDecimal signQty;
    
    // 本期结算数量*租期（签认单）
    private BigDecimal signThisAmt;
    
    // 至上期末累计不含税金额(元)
    private BigDecimal upAmtNoTax;
    
    // 至上期末累计税额(元)
    private BigDecimal upAmtTax;
    
    // 本期租赁起始日期（签认单）
    private Date signRentStartDate;
    
    // 本期租赁截止日期（签认单）
    private Date signRentEndDate;

    // 备注
    private String remarks;
    
    private String detailListId;

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

	public String getDetailListId() {
		return detailListId;
	}

	public void setDetailListId(String detailListId) {
		this.detailListId = detailListId;
	}

	// 排序
    private int sort=0;

    public BigDecimal getSignContrTrrm() {
		return signContrTrrm;
	}

	public void setSignContrTrrm(BigDecimal signContrTrrm) {
		this.signContrTrrm = signContrTrrm;
	}

	public BigDecimal getSignQty() {
		return signQty;
	}

	public void setSignQty(BigDecimal signQty) {
		this.signQty = signQty;
	}

	public BigDecimal getSignThisAmt() {
		return signThisAmt;
	}

	public void setSignThisAmt(BigDecimal signThisAmt) {
		this.signThisAmt = signThisAmt;
	}

	public Date getSignRentStartDate() {
		return signRentStartDate;
	}

	public void setSignRentStartDate(Date signRentStartDate) {
		this.signRentStartDate = signRentStartDate;
	}

	public Date getSignRentEndDate() {
		return signRentEndDate;
	}

	public void setSignRentEndDate(Date signRentEndDate) {
		this.signRentEndDate = signRentEndDate;
	}

	public String getZxCtSuppliesLeaseSettlementItemId() {
        return zxCtSuppliesLeaseSettlementItemId == null ? "" : zxCtSuppliesLeaseSettlementItemId.trim();
    }

    public void setZxCtSuppliesLeaseSettlementItemId(String zxCtSuppliesLeaseSettlementItemId) {
        this.zxCtSuppliesLeaseSettlementItemId = zxCtSuppliesLeaseSettlementItemId == null ? null : zxCtSuppliesLeaseSettlementItemId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getRentUnit() {
        return rentUnit == null ? "" : rentUnit.trim();
    }

    public void setRentUnit(String rentUnit) {
        this.rentUnit = rentUnit == null ? null : rentUnit.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
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

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getEquipCode() {
        return equipCode == null ? "" : equipCode.trim();
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode == null ? null : equipCode.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getSignedOrderItemID() {
        return signedOrderItemID == null ? "" : signedOrderItemID.trim();
    }

    public void setSignedOrderItemID(String signedOrderItemID) {
        this.signedOrderItemID = signedOrderItemID == null ? null : signedOrderItemID.trim();
    }

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getContrTrrm() {
        return contrTrrm;
    }

    public void setContrTrrm(BigDecimal contrTrrm) {
        this.contrTrrm = contrTrrm;
    }

    public BigDecimal getContractQty() {
        return contractQty;
    }

    public void setContractQty(BigDecimal contractQty) {
        this.contractQty = contractQty;
    }

    public String getContractItemID() {
        return contractItemID == null ? "" : contractItemID.trim();
    }

    public void setContractItemID(String contractItemID) {
        this.contractItemID = contractItemID == null ? null : contractItemID.trim();
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getAlterTrrm() {
        return alterTrrm;
    }

    public void setAlterTrrm(BigDecimal alterTrrm) {
        this.alterTrrm = alterTrrm;
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

    public BigDecimal getAlterPrice() {
        return alterPrice;
    }

    public void setAlterPrice(BigDecimal alterPrice) {
        this.alterPrice = alterPrice;
    }

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
    }

    public BigDecimal getThisAmtNoTax() {
        return thisAmtNoTax;
    }

    public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
        this.thisAmtNoTax = thisAmtNoTax;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
