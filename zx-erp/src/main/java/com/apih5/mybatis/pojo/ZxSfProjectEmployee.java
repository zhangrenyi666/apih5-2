package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfProjectEmployee extends BasePojo {
    // 主键
    private String zxSfProjectEmployeeId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    // 姓名
    private String name;

    // 性别
    private String sex;

    // 年龄
    private int age=0;

    // 学历
    private String eduLevel;

    // 职称
    private String title;

    // 注安师证号
    private String cardNo;

    // 从事安全工作累计时间(年)
    private BigDecimal workAge;

    // 交通部安全证书编号
    private String safeCardNo;

    // 发证日期
    private Date sendCardDate;

    // 到期日期
    private Date useEndDate;

    // 正在从事安全工作
    private String isWorking;

    // 编辑时间
    private Date editTime;

    // 是否为注安师
    private String isExpert;

    // 主表ID
    private String mainID;

    // 建设部安全证书编号
    private String buildCardNo;

    // 发证日期（建）
    private Date buildSendDate;

    // 到期日期（建）
    private Date buildEndDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String xuhao;

    private String safeDate;

    private String buildDate;

    private String companyId;

    private String companyName;

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

    public String getXuhao() {
        return xuhao;
    }

    public void setXuhao(String xuhao) {
        this.xuhao = xuhao;
    }

    public String getSafeDate() {
        return safeDate;
    }

    public void setSafeDate(String safeDate) {
        this.safeDate = safeDate;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getZxSfProjectEmployeeId() {
        return zxSfProjectEmployeeId == null ? "" : zxSfProjectEmployeeId.trim();
    }

    public void setZxSfProjectEmployeeId(String zxSfProjectEmployeeId) {
        this.zxSfProjectEmployeeId = zxSfProjectEmployeeId == null ? null : zxSfProjectEmployeeId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex == null ? "" : sex.trim();
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEduLevel() {
        return eduLevel == null ? "" : eduLevel.trim();
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel == null ? null : eduLevel.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCardNo() {
        return cardNo == null ? "" : cardNo.trim();
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public BigDecimal getWorkAge() {
        return workAge;
    }

    public void setWorkAge(BigDecimal workAge) {
        this.workAge = workAge;
    }

    public String getSafeCardNo() {
        return safeCardNo == null ? "" : safeCardNo.trim();
    }

    public void setSafeCardNo(String safeCardNo) {
        this.safeCardNo = safeCardNo == null ? null : safeCardNo.trim();
    }

    public Date getSendCardDate() {
        return sendCardDate;
    }

    public void setSendCardDate(Date sendCardDate) {
        this.sendCardDate = sendCardDate;
    }

    public Date getUseEndDate() {
        return useEndDate;
    }

    public void setUseEndDate(Date useEndDate) {
        this.useEndDate = useEndDate;
    }

    public String getIsWorking() {
        return isWorking == null ? "" : isWorking.trim();
    }

    public void setIsWorking(String isWorking) {
        this.isWorking = isWorking == null ? null : isWorking.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getIsExpert() {
        return isExpert == null ? "" : isExpert.trim();
    }

    public void setIsExpert(String isExpert) {
        this.isExpert = isExpert == null ? null : isExpert.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getBuildCardNo() {
        return buildCardNo == null ? "" : buildCardNo.trim();
    }

    public void setBuildCardNo(String buildCardNo) {
        this.buildCardNo = buildCardNo == null ? null : buildCardNo.trim();
    }

    public Date getBuildSendDate() {
        return buildSendDate;
    }

    public void setBuildSendDate(Date buildSendDate) {
        this.buildSendDate = buildSendDate;
    }

    public Date getBuildEndDate() {
        return buildEndDate;
    }

    public void setBuildEndDate(Date buildEndDate) {
        this.buildEndDate = buildEndDate;
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
