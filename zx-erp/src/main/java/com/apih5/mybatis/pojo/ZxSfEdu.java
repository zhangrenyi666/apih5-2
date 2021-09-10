package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfEdu extends BasePojo {
    // 主键
    private String zxSfEduId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    private String orgID2;

    // 培训名称
    private String tranName;

    // 主办单位
    private String mainUnit;

    // 办班地点
    private String address;

    // 采用教材
    private String materials;

    // 教员
    private String teacher;

    // 日期
    private Date bizDate;

    // 学时
    private String tranTime;

    // 培训内容
    private String tranContext;

    // 编辑时间
    private Date editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 所属公司ID
    private String companyId;
    
	// 所属公司名称
    private String companyName;
    
    // 附件
    private List<ZxErpFile> fileList;
    
    // 明细
    private List<ZxSfEduItem> zxSfEduItemList;

    private String projectId;

    private String projType;

    private String startDate;

    private String endDate;

    private String three;

    private String fugong;

    private String huangang;

    private String sanlei;

    private String zhuanxiang;

    private String qita;

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

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getFugong() {
        return fugong;
    }

    public void setFugong(String fugong) {
        this.fugong = fugong;
    }

    public String getHuangang() {
        return huangang;
    }

    public void setHuangang(String huangang) {
        this.huangang = huangang;
    }

    public String getSanlei() {
        return sanlei;
    }

    public void setSanlei(String sanlei) {
        this.sanlei = sanlei;
    }

    public String getZhuanxiang() {
        return zhuanxiang;
    }

    public void setZhuanxiang(String zhuanxiang) {
        this.zhuanxiang = zhuanxiang;
    }

    public String getQita() {
        return qita;
    }

    public void setQita(String qita) {
        this.qita = qita;
    }

    public List<ZxSfEduItem> getZxSfEduItemList() {
		return zxSfEduItemList;
	}

	public void setZxSfEduItemList(List<ZxSfEduItem> zxSfEduItemList) {
		this.zxSfEduItemList = zxSfEduItemList;
	}

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfEduId() {
        return zxSfEduId == null ? "" : zxSfEduId.trim();
    }

    public void setZxSfEduId(String zxSfEduId) {
        this.zxSfEduId = zxSfEduId == null ? null : zxSfEduId.trim();
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

    public String getTranName() {
        return tranName == null ? "" : tranName.trim();
    }

    public void setTranName(String tranName) {
        this.tranName = tranName == null ? null : tranName.trim();
    }

    public String getMainUnit() {
        return mainUnit == null ? "" : mainUnit.trim();
    }

    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit == null ? null : mainUnit.trim();
    }

    public String getAddress() {
        return address == null ? "" : address.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMaterials() {
        return materials == null ? "" : materials.trim();
    }

    public void setMaterials(String materials) {
        this.materials = materials == null ? null : materials.trim();
    }

    public String getTeacher() {
        return teacher == null ? "" : teacher.trim();
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getTranTime() {
        return tranTime == null ? "" : tranTime.trim();
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime == null ? null : tranTime.trim();
    }

    public String getTranContext() {
        return tranContext == null ? "" : tranContext.trim();
    }

    public void setTranContext(String tranContext) {
        this.tranContext = tranContext == null ? null : tranContext.trim();
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOrgID2() {
        return orgID2;
    }

    public void setOrgID2(String orgID2) {
        this.orgID2 = orgID2;
    }
}
