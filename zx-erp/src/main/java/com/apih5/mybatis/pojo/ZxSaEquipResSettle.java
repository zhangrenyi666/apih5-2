package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxSaEquipResSettle extends BasePojo {
    // 主键
    private String zxSaEquipResSettleId;

    // 结算单主键id
    private String zxSaEquipSettleAuditId;

    // 项目id
    private String orgID;

    // 项目名称
    private String orgName;

    // 合同id
    private String contractID;

    // 自动编号
    private String autoNum;

    // 结算单编号
    private String billNo;

    // 结算期次
    private String period;

    // 签认单编号
    private String signedNos;

    // 含税合同金额(元)
    private BigDecimal contractAmt;

    // 变更后含税合同金额(元)
    private BigDecimal changeAmt;

    // 本期清单结算含税金额(元)
    private BigDecimal thisAmt;

    // 本期清单结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期清单结算税额(元)
    private BigDecimal thisAmtTax;

    // 累计清单结算含税金额(元)
    private BigDecimal totalAmt;

    // 累计清单结算不含税金额(元)
    private BigDecimal totalAmtNoTax;

    // 上期末清单结算金额(元)
    private BigDecimal upAmt;

    // 所属公司id
    private String comID;

    // 所属公司名称
    private String comName;

    // 0
    private String contrType;

    // 清单结算明细list
    private List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList;

    public String getZxSaEquipResSettleId() {
        return zxSaEquipResSettleId == null ? "" : zxSaEquipResSettleId.trim();
    }

    public void setZxSaEquipResSettleId(String zxSaEquipResSettleId) {
        this.zxSaEquipResSettleId = zxSaEquipResSettleId == null ? null : zxSaEquipResSettleId.trim();
    }

    public String getZxSaEquipSettleAuditId() {
        return zxSaEquipSettleAuditId == null ? "" : zxSaEquipSettleAuditId.trim();
    }

    public void setZxSaEquipSettleAuditId(String zxSaEquipSettleAuditId) {
        this.zxSaEquipSettleAuditId = zxSaEquipSettleAuditId == null ? null : zxSaEquipSettleAuditId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getAutoNum() {
        return autoNum == null ? "" : autoNum.trim();
    }

    public void setAutoNum(String autoNum) {
        this.autoNum = autoNum == null ? null : autoNum.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(BigDecimal changeAmt) {
        this.changeAmt = changeAmt;
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

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getTotalAmtNoTax() {
        return totalAmtNoTax;
    }

    public void setTotalAmtNoTax(BigDecimal totalAmtNoTax) {
        this.totalAmtNoTax = totalAmtNoTax;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

	public List<ZxSaEquipResSettleItem> getZxSaEquipResSettleItemList() {
		return zxSaEquipResSettleItemList;
	}

	public void setZxSaEquipResSettleItemList(List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList) {
		this.zxSaEquipResSettleItemList = zxSaEquipResSettleItemList;
	}

}
