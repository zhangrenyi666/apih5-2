package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverOut extends BasePojo {
    // 主键
    private String id;

    // 发料单位ID
    private String orgID;

    // 发料单位
    private String orgName;

    // 单据编号
    private String billNo;

    // 出库日期
    private Date busDate;

    // 领料单位id
    private String inOrgID;

    // 领料单位
    private String inOrgName;

    // 物资员
    private String consignee;

    // 单据状态
    private String billStatus;

    // 审核状态
    private String status;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList;

    private List<Date> timeList;

    //筛选
    private String resCode;

    private String resName;

    private String resUnit;

    private String spec;

    private String orgID2;

    public String getOrgID2() {
        return orgID2;
    }

    public void setOrgID2(String orgID2) {
        this.orgID2 = orgID2;
    }

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

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    public List<ZxSkTurnoverOutItem> getZxSkTurnoverOutItemList() {
        return zxSkTurnoverOutItemList;
    }

    public void setZxSkTurnoverOutItemList(List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList) {
        this.zxSkTurnoverOutItemList = zxSkTurnoverOutItemList;
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

    public String getInOrgID() {
        return inOrgID == null ? "" : inOrgID.trim();
    }

    public void setInOrgID(String inOrgID) {
        this.inOrgID = inOrgID == null ? null : inOrgID.trim();
    }

    public String getInOrgName() {
        return inOrgName == null ? "" : inOrgName.trim();
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName == null ? null : inOrgName.trim();
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
