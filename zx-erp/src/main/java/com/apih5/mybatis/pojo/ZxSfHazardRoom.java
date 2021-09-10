package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfHazardRoom extends BasePojo {
    // 主键
    private String zxSfHazardRoomId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    // 编辑时间
    private Date editTime;

    // 名称
    private String roomName;

    // 填报日期
    private Date createDate;

    // 填报人
    private String creator;

    // 备注
    private String notes;

    // 所属机构（项目ID）
    private String projectId;

    // 项目名称
    private String projectName;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // (项目)List
    private List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList;

	public List<ZxSfHazardRoomAtt> getZxSfHazardRoomAttList() {
		return zxSfHazardRoomAttList;
	}

	public void setZxSfHazardRoomAttList(List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList) {
		this.zxSfHazardRoomAttList = zxSfHazardRoomAttList;
	}

	public String getZxSfHazardRoomId() {
        return zxSfHazardRoomId == null ? "" : zxSfHazardRoomId.trim();
    }

    public void setZxSfHazardRoomId(String zxSfHazardRoomId) {
        this.zxSfHazardRoomId = zxSfHazardRoomId == null ? null : zxSfHazardRoomId.trim();
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

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getRoomName() {
        return roomName == null ? "" : roomName.trim();
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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
