package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxEqEWorkItem extends BasePojo {
    private String id;

    private String mainID;

    private String equipID;

    private Date workDate;

    private String workAddress;

    private String workContext;

    private BigDecimal todayMiles;

    private BigDecimal totalMiles;

    private BigDecimal loadMiles;

    private BigDecimal nullMiles;

    private BigDecimal gasoline;

    private BigDecimal diesel;

    private BigDecimal engineOil;

    private String remark;

    private String opertaor;

    private BigDecimal runHour;

    private BigDecimal stopHour;

    private BigDecimal calendarNumDay;

    private BigDecimal runDay;

    private BigDecimal wellDays;

    private BigDecimal consumption;

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

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getWorkAddress() {
        return workAddress == null ? "" : workAddress.trim();
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress == null ? null : workAddress.trim();
    }

    public String getWorkContext() {
        return workContext == null ? "" : workContext.trim();
    }

    public void setWorkContext(String workContext) {
        this.workContext = workContext == null ? null : workContext.trim();
    }

    public BigDecimal getTodayMiles() {
        return todayMiles;
    }

    public void setTodayMiles(BigDecimal todayMiles) {
        this.todayMiles = todayMiles;
    }

    public BigDecimal getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(BigDecimal totalMiles) {
        this.totalMiles = totalMiles;
    }

    public BigDecimal getLoadMiles() {
        return loadMiles;
    }

    public void setLoadMiles(BigDecimal loadMiles) {
        this.loadMiles = loadMiles;
    }

    public BigDecimal getNullMiles() {
        return nullMiles;
    }

    public void setNullMiles(BigDecimal nullMiles) {
        this.nullMiles = nullMiles;
    }

    public BigDecimal getGasoline() {
        return gasoline;
    }

    public void setGasoline(BigDecimal gasoline) {
        this.gasoline = gasoline;
    }

    public BigDecimal getDiesel() {
        return diesel;
    }

    public void setDiesel(BigDecimal diesel) {
        this.diesel = diesel;
    }

    public BigDecimal getEngineOil() {
        return engineOil;
    }

    public void setEngineOil(BigDecimal engineOil) {
        this.engineOil = engineOil;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOpertaor() {
        return opertaor == null ? "" : opertaor.trim();
    }

    public void setOpertaor(String opertaor) {
        this.opertaor = opertaor == null ? null : opertaor.trim();
    }

    public BigDecimal getRunHour() {
        return runHour;
    }

    public void setRunHour(BigDecimal runHour) {
        this.runHour = runHour;
    }

    public BigDecimal getStopHour() {
        return stopHour;
    }

    public void setStopHour(BigDecimal stopHour) {
        this.stopHour = stopHour;
    }

    public BigDecimal getCalendarNumDay() {
        return calendarNumDay;
    }

    public void setCalendarNumDay(BigDecimal calendarNumDay) {
        this.calendarNumDay = calendarNumDay;
    }

    public BigDecimal getRunDay() {
        return runDay;
    }

    public void setRunDay(BigDecimal runDay) {
        this.runDay = runDay;
    }

    public BigDecimal getWellDays() {
        return wellDays;
    }

    public void setWellDays(BigDecimal wellDays) {
        this.wellDays = wellDays;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
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

