package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfHazardRoomAtt extends BasePojo {
    // 主键
    private String zxSfHazardRoomAttId;

    // 机构ID
    private String orgID;

    // 类别
    private String type;

    // 编辑时间
    private Date editTime;

    // 明细ID
    private String itemID;

    // 父ID
    private String parentID;

    // 名称
    private String attName;

    // 作业条件危险性评价（D）
    private BigDecimal dee;

    // 作业条件危险性评价（C）
    private BigDecimal cee;

    // 作业条件危险性评价（B）
    private BigDecimal bee;

    // 作业条件危险性评价（L）
    private BigDecimal lee;

    // 风险等级
    private String riskLevel;

    // 是否下达
    private String isGroup;

    // 过程（区域）
    private String proArea;

    // 行为（活动）或设备=环境
    private String doing;

    // 危险因素
    private String riskFactors;

    // 可能导致的伤害（事故）
    private String accident;

    // 编制人
    private String preparedby;

    // 机构名称
    private String orgName;

    // 所属公司名称
    private String companyName;

    // 所属公司ID
    private String companyId;

    // 备注
    private String remarks;
    
    // 附件
    private List<ZxErpFile> fileList;

	// 排序
    private int sort=0;

    private int xuhao;

    private String titleRiskLevel;

    public String getTitleRiskLevel() {
        return titleRiskLevel;
    }

    public void setTitleRiskLevel(String titleRiskLevel) {
        this.titleRiskLevel = titleRiskLevel;
    }

    public int getXuhao() {
        return xuhao;
    }

    public void setXuhao(int xuhao) {
        this.xuhao = xuhao;
    }

    public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

    public String getZxSfHazardRoomAttId() {
        return zxSfHazardRoomAttId == null ? "" : zxSfHazardRoomAttId.trim();
    }

    public void setZxSfHazardRoomAttId(String zxSfHazardRoomAttId) {
        this.zxSfHazardRoomAttId = zxSfHazardRoomAttId == null ? null : zxSfHazardRoomAttId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getItemID() {
        return itemID == null ? "" : itemID.trim();
    }

    public void setItemID(String itemID) {
        this.itemID = itemID == null ? null : itemID.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getAttName() {
        return attName == null ? "" : attName.trim();
    }

    public void setAttName(String attName) {
        this.attName = attName == null ? null : attName.trim();
    }

    public BigDecimal getDee() {
        return dee;
    }

    public void setDee(BigDecimal dee) {
        this.dee = dee;
    }

    public BigDecimal getCee() {
        return cee;
    }

    public void setCee(BigDecimal cee) {
        this.cee = cee;
    }

    public BigDecimal getBee() {
        return bee;
    }

    public void setBee(BigDecimal bee) {
        this.bee = bee;
    }

    public BigDecimal getLee() {
        return lee;
    }

    public void setLee(BigDecimal lee) {
        this.lee = lee;
    }

    public String getRiskLevel() {
        return riskLevel == null ? "" : riskLevel.trim();
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
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

    public String getPreparedby() {
        return preparedby == null ? "" : preparedby.trim();
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby == null ? null : preparedby.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
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
