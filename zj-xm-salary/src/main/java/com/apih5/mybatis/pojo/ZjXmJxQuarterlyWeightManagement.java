package com.apih5.mybatis.pojo;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxQuarterlyWeightManagement extends BasePojo {
	// 主键
	private String managementId;

	// 项目类型id
	private String projectType;

	// 项目类型名称
	private String projectTypeName;

	// 路桥事业部权重
	private Integer roadBridgeWeight;

	// 城市房建事业部权重
	private Integer housingWeight;

	// 铁路轨道事业部权重
	private Integer trackWeight;

	// 技术质量部权重
	private Integer technicalWeight;

	// 安全监督部权重
	private Integer safetyWeight;

	// 经营考核部权重
	private Integer businessWeight;

	// 财务部权重
	private Integer financeWeight;

	// 物资设备部权重
	private Integer materialsWeight;

	// 人力资源部权重
	private Integer humanResourcesWeight;

	// 法律部权重
	private Integer legalWeight;

	// 办公室权重
	private Integer officeWeight;

	// 经营考核部供应链管理部权重
	private Integer supplyChainWeight;

	// 经营考核部收尾中心权重
	private Integer closingCenterWeight;

	// 是否是收尾项目
	private String isClosed;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getManagementId() {
		return managementId == null ? "" : managementId.trim();
	}

	public void setManagementId(String managementId) {
		this.managementId = managementId == null ? null : managementId.trim();
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

	public Integer getRoadBridgeWeight() {
		return roadBridgeWeight;
	}

	public void setRoadBridgeWeight(Integer roadBridgeWeight) {
		this.roadBridgeWeight = roadBridgeWeight;
	}

	public Integer getHousingWeight() {
		return housingWeight;
	}

	public void setHousingWeight(Integer housingWeight) {
		this.housingWeight = housingWeight;
	}

	public Integer getTrackWeight() {
		return trackWeight;
	}

	public void setTrackWeight(Integer trackWeight) {
		this.trackWeight = trackWeight;
	}

	public Integer getTechnicalWeight() {
		return technicalWeight;
	}

	public void setTechnicalWeight(Integer technicalWeight) {
		this.technicalWeight = technicalWeight;
	}

	public Integer getSafetyWeight() {
		return safetyWeight;
	}

	public void setSafetyWeight(Integer safetyWeight) {
		this.safetyWeight = safetyWeight;
	}

	public Integer getBusinessWeight() {
		return businessWeight;
	}

	public void setBusinessWeight(Integer businessWeight) {
		this.businessWeight = businessWeight;
	}

	public Integer getFinanceWeight() {
		return financeWeight;
	}

	public void setFinanceWeight(Integer financeWeight) {
		this.financeWeight = financeWeight;
	}

	public Integer getMaterialsWeight() {
		return materialsWeight;
	}

	public void setMaterialsWeight(Integer materialsWeight) {
		this.materialsWeight = materialsWeight;
	}

	public Integer getHumanResourcesWeight() {
		return humanResourcesWeight;
	}

	public void setHumanResourcesWeight(Integer humanResourcesWeight) {
		this.humanResourcesWeight = humanResourcesWeight;
	}

	public Integer getLegalWeight() {
		return legalWeight;
	}

	public void setLegalWeight(Integer legalWeight) {
		this.legalWeight = legalWeight;
	}

	public Integer getOfficeWeight() {
		return officeWeight;
	}

	public void setOfficeWeight(Integer officeWeight) {
		this.officeWeight = officeWeight;
	}

	public Integer getSupplyChainWeight() {
		return supplyChainWeight;
	}

	public void setSupplyChainWeight(Integer supplyChainWeight) {
		this.supplyChainWeight = supplyChainWeight;
	}

	public Integer getClosingCenterWeight() {
		return closingCenterWeight;
	}

	public void setClosingCenterWeight(Integer closingCenterWeight) {
		this.closingCenterWeight = closingCenterWeight;
	}

	public String getIsClosed() {
		return isClosed == null ? "" : isClosed.trim();
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed == null ? null : isClosed.trim();
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
