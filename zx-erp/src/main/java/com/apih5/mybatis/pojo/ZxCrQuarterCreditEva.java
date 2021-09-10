package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrQuarterCreditEva extends BasePojo {
    // 主键
    private String zxCrQuarterCreditEvaId;

    // 机构ID
    private String orgID;

    // 机构名称
    private String orgName;

    // 评价期次
    private String period;

    // 评价日期
    private Date dateTime;

    // 评审状态
    private String statusName;

    // 评审标识
    private String statusFlag;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 发起人
    private String beginPer;

    // 进度ID
    private String instProcessId;

    // 审批进度ID
    private String workitemID;

    // 评价编号
    private String evaluationNo;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCrQuarterCreditEvaId() {
        return zxCrQuarterCreditEvaId == null ? "" : zxCrQuarterCreditEvaId.trim();
    }

    public void setZxCrQuarterCreditEvaId(String zxCrQuarterCreditEvaId) {
        this.zxCrQuarterCreditEvaId = zxCrQuarterCreditEvaId == null ? null : zxCrQuarterCreditEvaId.trim();
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

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName.trim();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getStatusFlag() {
        return statusFlag == null ? "" : statusFlag.trim();
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag == null ? null : statusFlag.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getBeginPer() {
        return beginPer == null ? "" : beginPer.trim();
    }

    public void setBeginPer(String beginPer) {
        this.beginPer = beginPer == null ? null : beginPer.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public String getWorkitemID() {
        return workitemID == null ? "" : workitemID.trim();
    }

    public void setWorkitemID(String workitemID) {
        this.workitemID = workitemID == null ? null : workitemID.trim();
    }

    public String getEvaluationNo() {
        return evaluationNo == null ? "" : evaluationNo.trim();
    }

    public void setEvaluationNo(String evaluationNo) {
        this.evaluationNo = evaluationNo == null ? null : evaluationNo.trim();
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
