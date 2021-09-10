package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfEquManageItem extends BasePojo {
    // 主键
    private String zxSfEquManageItemId;

    // 主表ID
    private String emID;

    // 设备ID
    private String resID;

    // 机械管理编号
    private String resCode;

    // 设备名称
    private String resName;

    // 型号
    private String resSpec;

    // 合格证书编号
    private String cardNo;

    // 发证单位
    private String sendCardUnit;

    // 姓名
    private String empName;

    // 证书编号
    private String empCardNo;

    // 有效期
    private String empValidDate;

    // 设备管理人员
    private String equipManager;

    // 安全管理人员
    private String safeManager;

    // 编辑时间
    private String editTime;

    // 设备来源
    private String source;

    // 有效期：起
    private Date effStart;

    // 有效期：止
    private Date effEnd;

    // 共计天数
    private int totalDay=0;

    // 退场时间
    private Date outDate;

    // 备注
    private String remarks;

    //结束时间
    private Date endDate;

    //公司ID
    private String companyId;

    //公司名称
    private String companyName;

    private String orgID;

    private String orgName;

    private String equipAddress;

    // 排序
    private int sort=0;

    public String getEquipAddress() {
        return equipAddress;
    }

    public void setEquipAddress(String equipAddress) {
        this.equipAddress = equipAddress;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getZxSfEquManageItemId() {
        return zxSfEquManageItemId == null ? "" : zxSfEquManageItemId.trim();
    }

    public void setZxSfEquManageItemId(String zxSfEquManageItemId) {
        this.zxSfEquManageItemId = zxSfEquManageItemId == null ? null : zxSfEquManageItemId.trim();
    }

    public String getEmID() {
        return emID == null ? "" : emID.trim();
    }

    public void setEmID(String emID) {
        this.emID = emID == null ? null : emID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
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

    public String getResSpec() {
        return resSpec == null ? "" : resSpec.trim();
    }

    public void setResSpec(String resSpec) {
        this.resSpec = resSpec == null ? null : resSpec.trim();
    }

    public String getCardNo() {
        return cardNo == null ? "" : cardNo.trim();
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getSendCardUnit() {
        return sendCardUnit == null ? "" : sendCardUnit.trim();
    }

    public void setSendCardUnit(String sendCardUnit) {
        this.sendCardUnit = sendCardUnit == null ? null : sendCardUnit.trim();
    }

    public String getEmpName() {
        return empName == null ? "" : empName.trim();
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getEmpCardNo() {
        return empCardNo == null ? "" : empCardNo.trim();
    }

    public void setEmpCardNo(String empCardNo) {
        this.empCardNo = empCardNo == null ? null : empCardNo.trim();
    }

    public String getEmpValidDate() {
        return empValidDate == null ? "" : empValidDate.trim();
    }

    public void setEmpValidDate(String empValidDate) {
        this.empValidDate = empValidDate == null ? null : empValidDate.trim();
    }

    public String getEquipManager() {
        return equipManager == null ? "" : equipManager.trim();
    }

    public void setEquipManager(String equipManager) {
        this.equipManager = equipManager == null ? null : equipManager.trim();
    }

    public String getSafeManager() {
        return safeManager == null ? "" : safeManager.trim();
    }

    public void setSafeManager(String safeManager) {
        this.safeManager = safeManager == null ? null : safeManager.trim();
    }

    public String getEditTime() {
        return editTime == null ? "" : editTime.trim();
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime == null ? null : editTime.trim();
    }

    public String getSource() {
        return source == null ? "" : source.trim();
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getEffStart() {
        return effStart;
    }

    public void setEffStart(Date effStart) {
        this.effStart = effStart;
    }

    public Date getEffEnd() {
        return effEnd;
    }

    public void setEffEnd(Date effEnd) {
        this.effEnd = effEnd;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
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
