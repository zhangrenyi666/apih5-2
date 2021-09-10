package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesShopResSettle extends BasePojo {
    // 主键
    private String zxCtSuppliesShopResSettleId;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 上期末结算金额
    private BigDecimal upAmt;

    // 物资结算表ID
    private String billID;

    // 累计物资细目含税结算金额(元)
    private BigDecimal totalAmt;

    // 所属公司排序
    private String comOrders;

    // 本期物资细目不含税结算金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期物资细目含税结算税额(元)
    private BigDecimal thisAmtTax;

    // 项目ID
    private String orgID;

    // 所属公司名称
    private String comName;

    // 项目名称
    private String orgName;

    // 变更后含税合同金额(万元)
    private BigDecimal changeAmt;

    // 所属公司ID
    private String comID;

    // 签认单编号
    private String signedNos;

    // 本期物资细目含税结算金额(元)
    private BigDecimal thisAmt;

    // 合同ID
    private String contractID;

    // 最后编辑时间
    private Date editTime;
    
    // 最后编辑时间
    private Date periodTime;

    // 含税合同金额(万元)
    private BigDecimal contractAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public Date getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(Date periodTime) {
		this.periodTime = periodTime;
	}

	public String getZxCtSuppliesShopResSettleId() {
        return zxCtSuppliesShopResSettleId == null ? "" : zxCtSuppliesShopResSettleId.trim();
    }

    public void setZxCtSuppliesShopResSettleId(String zxCtSuppliesShopResSettleId) {
        this.zxCtSuppliesShopResSettleId = zxCtSuppliesShopResSettleId == null ? null : zxCtSuppliesShopResSettleId.trim();
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

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
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

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public BigDecimal getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(BigDecimal changeAmt) {
        this.changeAmt = changeAmt;
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
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
