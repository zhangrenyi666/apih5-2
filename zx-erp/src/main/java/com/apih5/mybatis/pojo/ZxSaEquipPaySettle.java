package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import com.apih5.framework.entity.BasePojo;

public class ZxSaEquipPaySettle extends BasePojo {
    // 主键
    private String zxSaEquipPaySettleId;

    // 结算单id
    private String zxSaEquipSettleAuditId;

    // 项目id
    private String orgID;

    // 项目名称
    private String orgName;

    // 合同id
    private String contractID;

    // 合同类型
    private String contrType;

    // 自动编号
    private String autoNum;

    // 结算单编号
    private String billNo;

    // 结算期次
    private String period;

    // 本期支付项结算金额(元)
    private BigDecimal thisAmt;

    // 本期支付项结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期支付项结算税额(元)
    private BigDecimal thisAmtTax;

    // 累计支付项结算金额(元)
    private BigDecimal totalAmt;

    // 累计支付项结算不含税金额(
    private BigDecimal totalAmtNoTax;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmt;

    // 本期进退场费
    private BigDecimal inOutAmt;

    // 上期末进退场费
    private BigDecimal upInOutAmt;

    // 本期奖罚
    private BigDecimal fineAmt;

    // 上期末奖罚
    private BigDecimal upFineAmt;

    // 本期伙食费
    private BigDecimal foodAmt;

    // 上期末伙食费
    private BigDecimal upFoodAmt;

    // 本期其他款项
    private BigDecimal otherAmt;

    // 上期末其他款项
    private BigDecimal upOtherAmt;

    // 所属公司id
    private String comID;

    // 所属公司名称
    private String comName;

    public String getZxSaEquipPaySettleId() {
        return zxSaEquipPaySettleId == null ? "" : zxSaEquipPaySettleId.trim();
    }

    public void setZxSaEquipPaySettleId(String zxSaEquipPaySettleId) {
        this.zxSaEquipPaySettleId = zxSaEquipPaySettleId == null ? null : zxSaEquipPaySettleId.trim();
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
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

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
    }

    public BigDecimal getUpInOutAmt() {
        return upInOutAmt;
    }

    public void setUpInOutAmt(BigDecimal upInOutAmt) {
        this.upInOutAmt = upInOutAmt;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public BigDecimal getUpFineAmt() {
        return upFineAmt;
    }

    public void setUpFineAmt(BigDecimal upFineAmt) {
        this.upFineAmt = upFineAmt;
    }

    public BigDecimal getFoodAmt() {
        return foodAmt;
    }

    public void setFoodAmt(BigDecimal foodAmt) {
        this.foodAmt = foodAmt;
    }

    public BigDecimal getUpFoodAmt() {
        return upFoodAmt;
    }

    public void setUpFoodAmt(BigDecimal upFoodAmt) {
        this.upFoodAmt = upFoodAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getUpOtherAmt() {
        return upOtherAmt;
    }

    public void setUpOtherAmt(BigDecimal upOtherAmt) {
        this.upOtherAmt = upOtherAmt;
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

}
