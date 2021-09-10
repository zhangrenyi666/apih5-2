package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxQuarterlyWeightDetails extends BasePojo {
    // 主键ID
    private String detailsId;

    // 季度考核id
    private String assessmentId;

    // 项目类型id
    private String projectType;

    // 项目类型名称
    private String projectTypeName;

    // 路桥事业部权重
    private int roadBridgeWeight=0;

    // 城市房建事业部权重
    private int housingWeight=0;

    // 铁路轨道事业部权重
    private int trackWeight=0;

    // 技术质量部权重
    private int technicalWeight=0;

    // 安全监督部权重
    private int safetyWeight=0;

    // 经营考核部权重
    private int businessWeight=0;

    // 财务部权重
    private int financeWeight=0;

    // 物资设备部权重
    private int materialsWeight=0;

    // 人力资源部权重
    private int humanResourcesWeight=0;

    // 法律部权重
    private int legalWeight=0;

    // 办公室权重
    private int officeWeight=0;

    // 经营考核部供应链管理部权重
    private int supplyChainWeight=0;

    // 经营考核部收尾中心权重
    private int closingCenterWeight=0;

    // 是否是收尾项目
    private String isClosed;

    // 确认状态
    private String confirmStatus;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getDetailsId() {
        return detailsId == null ? "" : detailsId.trim();
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId == null ? null : detailsId.trim();
    }

    public String getAssessmentId() {
        return assessmentId == null ? "" : assessmentId.trim();
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId == null ? null : assessmentId.trim();
    }

    public String getProjectType() {
        return projectType == null ? "" : projectType.trim();
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getProjectTypeName() {
        return projectTypeName == null ? "" : projectTypeName.trim();
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
    }

    public int getRoadBridgeWeight() {
        return roadBridgeWeight;
    }

    public void setRoadBridgeWeight(int roadBridgeWeight) {
        this.roadBridgeWeight = roadBridgeWeight;
    }

    public int getHousingWeight() {
        return housingWeight;
    }

    public void setHousingWeight(int housingWeight) {
        this.housingWeight = housingWeight;
    }

    public int getTrackWeight() {
        return trackWeight;
    }

    public void setTrackWeight(int trackWeight) {
        this.trackWeight = trackWeight;
    }

    public int getTechnicalWeight() {
        return technicalWeight;
    }

    public void setTechnicalWeight(int technicalWeight) {
        this.technicalWeight = technicalWeight;
    }

    public int getSafetyWeight() {
        return safetyWeight;
    }

    public void setSafetyWeight(int safetyWeight) {
        this.safetyWeight = safetyWeight;
    }

    public int getBusinessWeight() {
        return businessWeight;
    }

    public void setBusinessWeight(int businessWeight) {
        this.businessWeight = businessWeight;
    }

    public int getFinanceWeight() {
        return financeWeight;
    }

    public void setFinanceWeight(int financeWeight) {
        this.financeWeight = financeWeight;
    }

    public int getMaterialsWeight() {
        return materialsWeight;
    }

    public void setMaterialsWeight(int materialsWeight) {
        this.materialsWeight = materialsWeight;
    }

    public int getHumanResourcesWeight() {
        return humanResourcesWeight;
    }

    public void setHumanResourcesWeight(int humanResourcesWeight) {
        this.humanResourcesWeight = humanResourcesWeight;
    }

    public int getLegalWeight() {
        return legalWeight;
    }

    public void setLegalWeight(int legalWeight) {
        this.legalWeight = legalWeight;
    }

    public int getOfficeWeight() {
        return officeWeight;
    }

    public void setOfficeWeight(int officeWeight) {
        this.officeWeight = officeWeight;
    }

    public int getSupplyChainWeight() {
        return supplyChainWeight;
    }

    public void setSupplyChainWeight(int supplyChainWeight) {
        this.supplyChainWeight = supplyChainWeight;
    }

    public int getClosingCenterWeight() {
        return closingCenterWeight;
    }

    public void setClosingCenterWeight(int closingCenterWeight) {
        this.closingCenterWeight = closingCenterWeight;
    }

    public String getIsClosed() {
        return isClosed == null ? "" : isClosed.trim();
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed == null ? null : isClosed.trim();
    }

    public String getConfirmStatus() {
        return confirmStatus == null ? "" : confirmStatus.trim();
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus == null ? null : confirmStatus.trim();
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
