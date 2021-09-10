package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqVehicleUsageProject extends BasePojo {
    // 主键
    private String zxEqVehicleUsageProjectId;

    // 分类
    private String catName;

    // 管理编号
    private String equipNo;

    // 机械名称
    private String equipName;

    // 技术规格
    private String techrequire;

    // 日历台日
    private BigDecimal calendarNumDay;

    // 完美台日
    private BigDecimal wellDays;

    // 完好率
    private BigDecimal wellPercen;

    // 运转台日
    private BigDecimal workDays;

    // 运转台时
    private BigDecimal actualQty;

    // 利用率
    private BigDecimal runDayPercen;

    // 行驶里程
    private BigDecimal usefulMileage;

    // 耗油情况汽油
    private BigDecimal oilamount;

    // 耗油情况柴油
    private BigDecimal caioidamout;

    // 电消耗
    private BigDecimal consumption;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;
    
	private Date beginDate;
    
    private Date endDate;
    
    public String getOrgID() {
 		return orgID;
 	}

 	public void setOrgID(String orgID) {
 		this.orgID = orgID;
 	}

 	public Date getBeginDate() {
 		return beginDate;
 	}

 	public void setBeginDate(Date beginDate) {
 		this.beginDate = beginDate;
 	}

 	public Date getEndDate() {
 		return endDate;
 	}

 	public void setEndDate(Date endDate) {
 		this.endDate = endDate;
 	}


    public String getZxEqVehicleUsageProjectId() {
        return zxEqVehicleUsageProjectId == null ? "" : zxEqVehicleUsageProjectId.trim();
    }

    public void setZxEqVehicleUsageProjectId(String zxEqVehicleUsageProjectId) {
        this.zxEqVehicleUsageProjectId = zxEqVehicleUsageProjectId == null ? null : zxEqVehicleUsageProjectId.trim();
    }

    public String getCatName() {
        return catName == null ? "" : catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getTechrequire() {
        return techrequire == null ? "" : techrequire.trim();
    }

    public void setTechrequire(String techrequire) {
        this.techrequire = techrequire == null ? null : techrequire.trim();
    }

    public BigDecimal getCalendarNumDay() {
        return calendarNumDay;
    }

    public void setCalendarNumDay(BigDecimal calendarNumDay) {
        this.calendarNumDay = calendarNumDay;
    }

    public BigDecimal getWellDays() {
        return wellDays;
    }

    public void setWellDays(BigDecimal wellDays) {
        this.wellDays = wellDays;
    }

    public BigDecimal getWellPercen() {
        return wellPercen;
    }

    public void setWellPercen(BigDecimal wellPercen) {
        this.wellPercen = wellPercen;
    }

    public BigDecimal getWorkDays() {
        return workDays;
    }

    public void setWorkDays(BigDecimal workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getRunDayPercen() {
        return runDayPercen;
    }

    public void setRunDayPercen(BigDecimal runDayPercen) {
        this.runDayPercen = runDayPercen;
    }

    public BigDecimal getUsefulMileage() {
        return usefulMileage;
    }

    public void setUsefulMileage(BigDecimal usefulMileage) {
        this.usefulMileage = usefulMileage;
    }

    public BigDecimal getOilamount() {
        return oilamount;
    }

    public void setOilamount(BigDecimal oilamount) {
        this.oilamount = oilamount;
    }

    public BigDecimal getCaioidamout() {
        return caioidamout;
    }

    public void setCaioidamout(BigDecimal caioidamout) {
        this.caioidamout = caioidamout;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
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
