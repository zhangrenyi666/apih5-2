package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverIn extends BasePojo {
    // 主键
    private String id;

    // 收料单位ID
    private String orgID;

    // 收料单位
    private String orgName;

    // 物资来源
    private String materialSource;

    // 单据编号
    private String billNo;

    // 入库日期
    private Date busDate;

    // 物资员
    private String consignee;

    // 单据状态
    private String billStatus;

    // 审核状态
    private String status;

    // 供货单位ID
    private String outOrgID;

    // 供货单位
    private String outOrgName;

    // 物资合同ID
    private String contractID;

    // 物资合同
    private String contractName;

    // 采购类别
    private String purchType;

    // 收料人员
    private String recePerson;

    // 采购人员
    private String purcPerson;

    // 是否抵扣
    private String isDeduct;

    // 税率(%)
    private String taxRate;

    // fromBillID
    private String fromBillID;

    // 是否预收
    private String orNotPrecollecte;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String companyID;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String resUnit;

    // 数量
    private BigDecimal inQty;

    // 购入单价合计
    private BigDecimal inPrice;

    // 总值 含税采购金额（万元）
    private BigDecimal inAmtTotal;

    // 购入不含税单价
    private BigDecimal inPriceNoTax;

    // 不含税采购金额（万元）
    private BigDecimal inAmtNoTax;

    // 是否预收
    private String precollecte;

    // 开始时间
    private String beginDate;

    // 结束时间
    private String endDate;

    //明细
    private List<ZxSkTurnoverInItem> zxSkTurnoverInItemList;

    private List<Date> timeList;

    private String orgID2;

    public String getOrgID2() {
        return orgID2;
    }

    public void setOrgID2(String orgID2) {
        this.orgID2 = orgID2;
    }

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public List<ZxSkTurnoverInItem> getZxSkTurnoverInItemList() {
        return zxSkTurnoverInItemList;
    }

    public void setZxSkTurnoverInItemList(List<ZxSkTurnoverInItem> zxSkTurnoverInItemList) {
        this.zxSkTurnoverInItemList = zxSkTurnoverInItemList;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public Date getBusDate() {
        return busDate;
    }

    public void setBusDate(Date busDate) {
        this.busDate = busDate;
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee.trim();
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getBillStatus() {
        return billStatus == null ? "" : billStatus.trim();
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getPurchType() {
        return purchType == null ? "" : purchType.trim();
    }

    public void setPurchType(String purchType) {
        this.purchType = purchType == null ? null : purchType.trim();
    }

    public String getRecePerson() {
        return recePerson == null ? "" : recePerson.trim();
    }

    public void setRecePerson(String recePerson) {
        this.recePerson = recePerson == null ? null : recePerson.trim();
    }

    public String getPurcPerson() {
        return purcPerson == null ? "" : purcPerson.trim();
    }

    public void setPurcPerson(String purcPerson) {
        this.purcPerson = purcPerson == null ? null : purcPerson.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getFromBillID() {
        return fromBillID == null ? "" : fromBillID.trim();
    }

    public void setFromBillID(String fromBillID) {
        this.fromBillID = fromBillID == null ? null : fromBillID.trim();
    }

    public String getOrNotPrecollecte() {
        return orNotPrecollecte == null ? "" : orNotPrecollecte.trim();
    }

    public void setOrNotPrecollecte(String orNotPrecollecte) {
        this.orNotPrecollecte = orNotPrecollecte == null ? null : orNotPrecollecte.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public BigDecimal getInQty() {
        return inPrice;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }
    public BigDecimal getInPriceNoTax() {
        return inPriceNoTax;
    }

    public void setInPriceNoTax(BigDecimal inPriceNoTax) {
        this.inPriceNoTax = inPriceNoTax;
    }

    public BigDecimal getInAmtTotal() {
        return inAmtTotal;
    }

    public void setInAmtTotal(BigDecimal inAmtTotal) {
        this.inAmtTotal = inAmtTotal;
    }

    public BigDecimal getInAmtNoTax() {
        return inAmtNoTax;
    }

    public void setInAmtNoTax(BigDecimal inAmtNoTax) {
        this.inAmtNoTax = inAmtNoTax;
    }

    public String getPrecollecte() {
        return precollecte == null ? "" : precollecte.trim();
    }

    public void setPrecollecte(String precollecte) {
        this.precollecte = precollecte == null ? null : precollecte.trim();
    }

    public String getBeginDate() {
        return beginDate == null ? "" : beginDate.trim();
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getEndDate() {
        return endDate == null ? "" : endDate.trim();
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }
}
