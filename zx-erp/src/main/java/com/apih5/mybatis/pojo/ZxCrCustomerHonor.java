package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrCustomerHonor extends BasePojo {
    // 主键
    private String zxCrCustomerHonorId;

    // 协作单位id
    private String bisID;

    // 业绩和荣誉
    private String honor;

    // 附件id
    private String datumID;

    // 编辑时间
    private Date editTime;

    // comID
    private String comID;

    // comName
    private String comName;

    // comOrders
    private String comOrders;

    // 序号
    private String orderStr;

    // 编辑日期
    private Date prepareDate;

    // 已建工程项目名称
    private String projectName;

    // 合同开始时间
    private Date contrDateS;

    // 合同结束时间
    private Date contrDateE;

    // 工程类别
    private String projectType;

    // 合同金额
    private BigDecimal contrAmount;

    // 工程数量
    private BigDecimal workNum;

    // 单位
    private String workUnit;

    // 录入时间
    private Date inputDate;

    // 来源
    private String dataFrom;

    // 工程类别ID
    private String projectTypeID;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String zxCrCustomerInfoId;

    public String getZxCrCustomerInfoId() {
		return zxCrCustomerInfoId;
	}

	public void setZxCrCustomerInfoId(String zxCrCustomerInfoId) {
		this.zxCrCustomerInfoId = zxCrCustomerInfoId;
	}

	public String getZxCrCustomerHonorId() {
        return zxCrCustomerHonorId == null ? "" : zxCrCustomerHonorId.trim();
    }

    public void setZxCrCustomerHonorId(String zxCrCustomerHonorId) {
        this.zxCrCustomerHonorId = zxCrCustomerHonorId == null ? null : zxCrCustomerHonorId.trim();
    }

    public String getBisID() {
        return bisID == null ? "" : bisID.trim();
    }

    public void setBisID(String bisID) {
        this.bisID = bisID == null ? null : bisID.trim();
    }

    public String getHonor() {
        return honor == null ? "" : honor.trim();
    }

    public void setHonor(String honor) {
        this.honor = honor == null ? null : honor.trim();
    }

    public String getDatumID() {
        return datumID == null ? "" : datumID.trim();
    }

    public void setDatumID(String datumID) {
        this.datumID = datumID == null ? null : datumID.trim();
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

    public String getOrderStr() {
        return orderStr == null ? "" : orderStr.trim();
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr == null ? null : orderStr.trim();
    }

    public Date getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(Date prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Date getContrDateS() {
        return contrDateS;
    }

    public void setContrDateS(Date contrDateS) {
        this.contrDateS = contrDateS;
    }

    public Date getContrDateE() {
        return contrDateE;
    }

    public void setContrDateE(Date contrDateE) {
        this.contrDateE = contrDateE;
    }

    public String getProjectType() {
        return projectType == null ? "" : projectType.trim();
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public BigDecimal getContrAmount() {
        return contrAmount;
    }

    public void setContrAmount(BigDecimal contrAmount) {
        this.contrAmount = contrAmount;
    }

    public BigDecimal getWorkNum() {
        return workNum;
    }

    public void setWorkNum(BigDecimal workNum) {
        this.workNum = workNum;
    }

    public String getWorkUnit() {
        return workUnit == null ? "" : workUnit.trim();
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getDataFrom() {
        return dataFrom == null ? "" : dataFrom.trim();
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom == null ? null : dataFrom.trim();
    }

    public String getProjectTypeID() {
        return projectTypeID == null ? "" : projectTypeID.trim();
    }

    public void setProjectTypeID(String projectTypeID) {
        this.projectTypeID = projectTypeID == null ? null : projectTypeID.trim();
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
