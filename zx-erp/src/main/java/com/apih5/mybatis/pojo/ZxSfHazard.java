package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfHazard extends BasePojo {
    // 主键
    private String zxSfHazardId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    // 过程(区域)
    private String proArea;

    // 行为(活动)或设备
    private String doing;

    // 危险因素
    private String riskFactors;

    // 可能导致的伤害（事故）
    private String accident;

    // 作业条件危险性评价(L)
    private String lee;

    // 作业条件危险性评价(e)
    private String bee;

    // 作业条件危险性评价(c)
    private String cee;

    // 作业条件危险性评价(d)
    private String dee;

    // 风险等级
    private String riskLevel;

    // 预防措施
    private String safeguard;

    // 主表ID
    private String mainID;

    // 编辑时间
    private Date editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getZxSfHazardId() {
        return zxSfHazardId == null ? "" : zxSfHazardId.trim();
    }

    public void setZxSfHazardId(String zxSfHazardId) {
        this.zxSfHazardId = zxSfHazardId == null ? null : zxSfHazardId.trim();
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

    public String getProArea() {
        return proArea == null ? "" : proArea.trim();
    }

    public void setProArea(String proArea) {
        this.proArea = proArea == null ? null : proArea.trim();
    }

    public String getDoing() {
        return doing == null ? "" : doing.trim();
    }

    public void setDoing(String doing) {
        this.doing = doing == null ? null : doing.trim();
    }

    public String getRiskFactors() {
        return riskFactors == null ? "" : riskFactors.trim();
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors == null ? null : riskFactors.trim();
    }

    public String getAccident() {
        return accident == null ? "" : accident.trim();
    }

    public void setAccident(String accident) {
        this.accident = accident == null ? null : accident.trim();
    }

    public String getLee() {
        return lee == null ? "" : lee.trim();
    }

    public void setLee(String lee) {
        this.lee = lee == null ? null : lee.trim();
    }

    public String getBee() {
        return bee == null ? "" : bee.trim();
    }

    public void setBee(String bee) {
        this.bee = bee == null ? null : bee.trim();
    }

    public String getCee() {
        return cee == null ? "" : cee.trim();
    }

    public void setCee(String cee) {
        this.cee = cee == null ? null : cee.trim();
    }

    public String getDee() {
        return dee == null ? "" : dee.trim();
    }

    public void setDee(String dee) {
        this.dee = dee == null ? null : dee.trim();
    }

    public String getRiskLevel() {
        return riskLevel == null ? "" : riskLevel.trim();
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    public String getSafeguard() {
        return safeguard == null ? "" : safeguard.trim();
    }

    public void setSafeguard(String safeguard) {
        this.safeguard = safeguard == null ? null : safeguard.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
