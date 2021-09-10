package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfHazardMain extends BasePojo {
    // 主键
    private String zxSfHazardMainId;

    // 机构ID
    private String orgID;

    // 机构ID
    private String orgID2;

    // 机构名称
    private String orgName;

    // 编制人
    private String creator;

    // 类型
    private String type;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    // 所属项目
    private String projectId;
    
    // 附件
    private List<ZxErpFile> fileList;
    
    private List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList;
    
    private List<ZxSfHazard> zxSfHazardList;

    public List<ZxSfHazard> getZxSfHazardList() {
		return zxSfHazardList;
	}

	public void setZxSfHazardList(List<ZxSfHazard> zxSfHazardList) {
		this.zxSfHazardList = zxSfHazardList;
	}

	public List<ZxSfHazardRoomAtt> getZxSfHazardRoomAttList() {
		return zxSfHazardRoomAttList;
	}

	public void setZxSfHazardRoomAttList(List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList) {
		this.zxSfHazardRoomAttList = zxSfHazardRoomAttList;
	}

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfHazardMainId() {
        return zxSfHazardMainId == null ? "" : zxSfHazardMainId.trim();
    }

    public void setZxSfHazardMainId(String zxSfHazardMainId) {
        this.zxSfHazardMainId = zxSfHazardMainId == null ? null : zxSfHazardMainId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public String getOrgID2() {
        return orgID2;
    }

    public void setOrgID2(String orgID2) {
        this.orgID2 = orgID2;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
