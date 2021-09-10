package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.xml.crypto.Data;

public class ZxSkColInven extends BasePojo {
    // 主键
    private String id;

    // 项目名称ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 协作队伍名称ID
    private String customerOrgID;

    // 协作队伍名称
    private String customerOrgName;

    // 登记日期
    private Date makeInvDate;

    // 经办人
    private String intransactor;

    // 协作队伍负责人
    private String reporter;

    // 状态
    private String status;

    // 审核员
    private String auditor;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 盘点期次
    private String makeInvPeriod;

    //前端使用盘点期次字段
    private Date makeInvPeriodDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //筛选
    private String resCode;

    private String resName;

    private String spec;

    private String unit;

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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private List<Date> timeList;

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    private List<ZxSkColInvenItem> zxSkColInvenItemList;

    public List<ZxSkColInvenItem> getZxSkColInvenItemList() {
        return zxSkColInvenItemList;
    }

    public Date getMakeInvPeriodDate() {
        return makeInvPeriodDate;
    }

    public void setMakeInvPeriodDate(Date makeInvPeriodDate) {
        this.makeInvPeriodDate = makeInvPeriodDate;
    }

    public void setZxSkColInvenItemList(List<ZxSkColInvenItem> zxSkColInvenItemList) {
        this.zxSkColInvenItemList = zxSkColInvenItemList;
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

    public String getCustomerOrgID() {
        return customerOrgID == null ? "" : customerOrgID.trim();
    }

    public void setCustomerOrgID(String customerOrgID) {
        this.customerOrgID = customerOrgID == null ? null : customerOrgID.trim();
    }

    public String getCustomerOrgName() {
        return customerOrgName == null ? "" : customerOrgName.trim();
    }

    public void setCustomerOrgName(String customerOrgName) {
        this.customerOrgName = customerOrgName == null ? null : customerOrgName.trim();
    }

    public Date getMakeInvDate() {
        return makeInvDate;
    }

    public void setMakeInvDate(Date makeInvDate) {
        this.makeInvDate = makeInvDate;
    }

    public String getIntransactor() {
        return intransactor == null ? "" : intransactor.trim();
    }

    public void setIntransactor(String intransactor) {
        this.intransactor = intransactor == null ? null : intransactor.trim();
    }

    public String getReporter() {
        return reporter == null ? "" : reporter.trim();
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAuditor() {
        return auditor == null ? "" : auditor.trim();
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
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

    public String getMakeInvPeriod() {
        return makeInvPeriod == null ? "" : makeInvPeriod.trim();
    }

    public void setMakeInvPeriod(String makeInvPeriod) {
        this.makeInvPeriod = makeInvPeriod == null ? null : makeInvPeriod.trim();
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
