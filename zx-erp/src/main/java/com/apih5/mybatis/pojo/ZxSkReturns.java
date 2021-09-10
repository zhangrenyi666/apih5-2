package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkReturns extends BasePojo {
    // 主键
    private String id;

    // 退货单位ID
    private String orgID;

    // 退货单位
    private String orgName;

    // 单据编号
    private String billNo;

    // 退货日期
    private Date busDate;

    // 审核状态
    private String status;

    // 供货单位ID
    private String outOrgID;

    // 供货单位
    private String outOrgName;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //明细
    List<ZxSkReturnsItem> zxSkReturnsItemList;

    //筛选
    private String resCode;

    private String resName;

    private String resUnit;

    private String spec;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResUnit() {
        return resUnit;
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public List<ZxSkReturnsItem> getZxSkReturnsItemList() {
        return zxSkReturnsItemList;
    }

    public void setZxSkReturnsItemList(List<ZxSkReturnsItem> zxSkReturnsItemList) {
        this.zxSkReturnsItemList = zxSkReturnsItemList;
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
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

}
