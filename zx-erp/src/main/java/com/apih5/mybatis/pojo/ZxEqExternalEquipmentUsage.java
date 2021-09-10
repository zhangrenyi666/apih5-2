package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqExternalEquipmentUsage extends BasePojo {
    // 主键
    private String zxEqExternalEquipmentUsageId;

    // 组织名称
    private String orgName;

    // 项目名称
    private String projectName;

    // 设备名称
    private String equipName;

    // 设备型号
    private String model;

    // 设备规格
    private String spec;

    // 设备功率
    private String power;

    // 生产厂家
    private String outfactory;

    // 出厂日期
    private Date outfactoryDate;

    // 原值（美元）
    private BigDecimal orginalValue;

    // 使用地点
    private String place;

    // 租赁期限
    private Date leaseLimit;

    // 机主姓名
    private String master;

    // 租赁价格（美元）
    private BigDecimal leaseprice;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private Date beginPeriod;

	private Date endPeriod;
    
    private String orgID;
    
	public Date getBeginPeriod() {
		return beginPeriod;
	}

	public void setBeginPeriod(Date beginPeriod) {
		this.beginPeriod = beginPeriod;
	}

	public Date getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

    public String getZxEqExternalEquipmentUsageId() {
        return zxEqExternalEquipmentUsageId == null ? "" : zxEqExternalEquipmentUsageId.trim();
    }

    public void setZxEqExternalEquipmentUsageId(String zxEqExternalEquipmentUsageId) {
        this.zxEqExternalEquipmentUsageId = zxEqExternalEquipmentUsageId == null ? null : zxEqExternalEquipmentUsageId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getPower() {
        return power == null ? "" : power.trim();
    }

    public void setPower(String power) {
        this.power = power == null ? null : power.trim();
    }

    public String getOutfactory() {
        return outfactory == null ? "" : outfactory.trim();
    }

    public void setOutfactory(String outfactory) {
        this.outfactory = outfactory == null ? null : outfactory.trim();
    }

    public Date getOutfactoryDate() {
        return outfactoryDate;
    }

    public void setOutfactoryDate(Date outfactoryDate) {
        this.outfactoryDate = outfactoryDate;
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public String getPlace() {
        return place == null ? "" : place.trim();
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getLeaseLimit() {
        return leaseLimit;
    }

    public void setLeaseLimit(Date leaseLimit) {
        this.leaseLimit = leaseLimit;
    }

    public String getMaster() {
        return master == null ? "" : master.trim();
    }

    public void setMaster(String master) {
        this.master = master == null ? null : master.trim();
    }

    public BigDecimal getLeaseprice() {
        return leaseprice;
    }

    public void setLeaseprice(BigDecimal leaseprice) {
        this.leaseprice = leaseprice;
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
