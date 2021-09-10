package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfHazardmainDetailJu extends BasePojo {
    // 主键
    private String zxSfHazardmainDetailJuId;

    // 主表id
    private String mainId;

    // 所属机构
    private String orgId;

    // 所属机构名称
    private String orgName;

    // 编制人
    private String preparer;

    // 过程
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

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSfHazardmainDetailJuId() {
        return zxSfHazardmainDetailJuId == null ? "" : zxSfHazardmainDetailJuId.trim();
    }

    public void setZxSfHazardmainDetailJuId(String zxSfHazardmainDetailJuId) {
        this.zxSfHazardmainDetailJuId = zxSfHazardmainDetailJuId == null ? null : zxSfHazardmainDetailJuId.trim();
    }

    public String getMainId() {
        return mainId == null ? "" : mainId.trim();
    }

    public void setMainId(String mainId) {
        this.mainId = mainId == null ? null : mainId.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getPreparer() {
        return preparer == null ? "" : preparer.trim();
    }

    public void setPreparer(String preparer) {
        this.preparer = preparer == null ? null : preparer.trim();
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
