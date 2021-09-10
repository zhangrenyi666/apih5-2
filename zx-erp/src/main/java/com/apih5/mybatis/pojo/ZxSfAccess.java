package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfAccess extends BasePojo {
    // 主键
    private String zxSfAccessId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    // 机构ID
    private String orgID2;

    // 协作队伍名称
    private String crmName;

    // 协作队伍ID
    private String crmID;

    // 营业执照号
    private String cardNo;

    // 资质等级
    private String levs;

    // 安全许可证
    private String safeCardNo;

    // 合同编号
    private String contractNo;

    // 开始时间
    private Date inDate;

    // 结束时间
    private Date outDate;

    // 编辑时间
    private Date editTime;

    // 级别
    private String levels;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 所属项目
    private String projectId;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private List<ZxErpFile> fileList;

	private List<ZxSfAccessItem> zxSfAccessItemList;
	
	public List<ZxErpFile> getFileList() {
			return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
			this.fileList = fileList;
	}

	public List<ZxSfAccessItem> getZxSfAccessItemList() {
			return zxSfAccessItemList;
	}

	public void setZxSfAccessItemList(List<ZxSfAccessItem> zxSfAccessItemList) {
			this.zxSfAccessItemList = zxSfAccessItemList;
	}

    public String getZxSfAccessId() {
        return zxSfAccessId == null ? "" : zxSfAccessId.trim();
    }

    public void setZxSfAccessId(String zxSfAccessId) {
        this.zxSfAccessId = zxSfAccessId == null ? null : zxSfAccessId.trim();
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

    public String getCrmName() {
        return crmName == null ? "" : crmName.trim();
    }

    public void setCrmName(String crmName) {
        this.crmName = crmName == null ? null : crmName.trim();
    }

    public String getCrmID() {
        return crmID == null ? "" : crmID.trim();
    }

    public void setCrmID(String crmID) {
        this.crmID = crmID == null ? null : crmID.trim();
    }

    public String getCardNo() {
        return cardNo == null ? "" : cardNo.trim();
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getLevs() {
        return levs == null ? "" : levs.trim();
    }

    public void setLevs(String levs) {
        this.levs = levs == null ? null : levs.trim();
    }

    public String getSafeCardNo() {
        return safeCardNo == null ? "" : safeCardNo.trim();
    }

    public void setSafeCardNo(String safeCardNo) {
        this.safeCardNo = safeCardNo == null ? null : safeCardNo.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getLevels() {
        return levels == null ? "" : levels.trim();
    }

    public void setLevels(String levels) {
        this.levels = levels == null ? null : levels.trim();
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
