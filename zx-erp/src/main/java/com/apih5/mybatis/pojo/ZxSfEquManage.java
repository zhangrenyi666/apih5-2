package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfEquManage extends BasePojo {
    // 主键
    private String zxSfEquManageId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    private String companyId;

    // 设备所在地
    private String equipAddress;

    // 联系人
    private String relationMan;

    // 联系电话
    private String relationTel;

    // 设备安全主管领导
    private String equipManager;

    // 主管部门负责人
    private String deManager;

    // 填报人
    private String creator;

    // 填报日期
    private Date createDate;

    // 编辑日期
    private Date editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 附件
    private List<ZxErpFile> fileList;
    
    private String colorflag;

    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    // 明细
    private List<ZxSfEquManageItem> zxSfEquManageItemList;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getColorflag() {
		return colorflag;
	}

	public void setColorflag(String colorflag) {
		this.colorflag = colorflag;
	}

    public List<ZxSfEquManageItem> getZxSfEquManageItemList() {
		return zxSfEquManageItemList;
	}

	public void setZxSfEquManageItemList(List<ZxSfEquManageItem> zxSfEquManageItemList) {
		this.zxSfEquManageItemList = zxSfEquManageItemList;
	}

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfEquManageId() {
        return zxSfEquManageId == null ? "" : zxSfEquManageId.trim();
    }

    public void setZxSfEquManageId(String zxSfEquManageId) {
        this.zxSfEquManageId = zxSfEquManageId == null ? null : zxSfEquManageId.trim();
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

    public String getEquipAddress() {
        return equipAddress == null ? "" : equipAddress.trim();
    }

    public void setEquipAddress(String equipAddress) {
        this.equipAddress = equipAddress == null ? null : equipAddress.trim();
    }

    public String getRelationMan() {
        return relationMan == null ? "" : relationMan.trim();
    }

    public void setRelationMan(String relationMan) {
        this.relationMan = relationMan == null ? null : relationMan.trim();
    }

    public String getRelationTel() {
        return relationTel == null ? "" : relationTel.trim();
    }

    public void setRelationTel(String relationTel) {
        this.relationTel = relationTel == null ? null : relationTel.trim();
    }

    public String getEquipManager() {
        return equipManager == null ? "" : equipManager.trim();
    }

    public void setEquipManager(String equipManager) {
        this.equipManager = equipManager == null ? null : equipManager.trim();
    }

    public String getDeManager() {
        return deManager == null ? "" : deManager.trim();
    }

    public void setDeManager(String deManager) {
        this.deManager = deManager == null ? null : deManager.trim();
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
