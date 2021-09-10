package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxEqRentOutEqRecordItem extends BasePojo {
    private String id;

    private String billID;

    private Date useDate;

    private String content;

    private String place;

    private String unit;

    private BigDecimal qty;

    private BigDecimal mile;

    private BigDecimal actualQty;

    private BigDecimal waitQty;

    private BigDecimal weatherQty;

    private BigDecimal problemQty;

    private BigDecimal gasQty;

    private BigDecimal dervQty;

    private BigDecimal coalQty;

    private Date editTime;

    private String driverName;

    private BigDecimal consumption;

    private BigDecimal calendarNumDay;

    private BigDecimal wellDays;

    private BigDecimal runDay;

    private String projectName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPlace() {
        return place == null ? "" : place.trim();
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getMile() {
        return mile;
    }

    public void setMile(BigDecimal mile) {
        this.mile = mile;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getWaitQty() {
        return waitQty;
    }

    public void setWaitQty(BigDecimal waitQty) {
        this.waitQty = waitQty;
    }

    public BigDecimal getWeatherQty() {
        return weatherQty;
    }

    public void setWeatherQty(BigDecimal weatherQty) {
        this.weatherQty = weatherQty;
    }

    public BigDecimal getProblemQty() {
        return problemQty;
    }

    public void setProblemQty(BigDecimal problemQty) {
        this.problemQty = problemQty;
    }

    public BigDecimal getGasQty() {
        return gasQty;
    }

    public void setGasQty(BigDecimal gasQty) {
        this.gasQty = gasQty;
    }

    public BigDecimal getDervQty() {
        return dervQty;
    }

    public void setDervQty(BigDecimal dervQty) {
        this.dervQty = dervQty;
    }

    public BigDecimal getCoalQty() {
        return coalQty;
    }

    public void setCoalQty(BigDecimal coalQty) {
        this.coalQty = coalQty;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getDriverName() {
        return driverName == null ? "" : driverName.trim();
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
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

    public BigDecimal getRunDay() {
        return runDay;
    }

    public void setRunDay(BigDecimal runDay) {
        this.runDay = runDay;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

}

