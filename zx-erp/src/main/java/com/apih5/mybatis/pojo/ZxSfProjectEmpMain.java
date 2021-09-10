package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfProjectEmpMain extends BasePojo {
    // 主键
    private String zxSfProjectEmpMainId;

    // 机构名称
    private String orgID;

    private String orgID2;

    // 编制人
    private String reporter;

    // 编辑时间
    private String editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 颜色控制
    private String colorflag;
    
	// 附件
    private List<ZxErpFile> fileList;
    
    // 明细
    private List<ZxSfProjectEmployee> zxSfProjectEmployeeList;

    private String daxue;

    private String zhongzhuan;

    private String gaozhong;

    private String chuzhong;

    private String wu;

    private String orgName;
    private String companyId;
    private String projectId;

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
    
    public List<ZxSfProjectEmployee> getZxSfProjectEmployeeList() {
		return zxSfProjectEmployeeList;
	}


    public String getDaxue() {
        return daxue;
    }

    public void setDaxue(String daxue) {
        this.daxue = daxue;
    }

    public String getZhongzhuan() {
        return zhongzhuan;
    }

    public void setZhongzhuan(String zhongzhuan) {
        this.zhongzhuan = zhongzhuan;
    }

    public String getGaozhong() {
        return gaozhong;
    }

    public void setGaozhong(String gaozhong) {
        this.gaozhong = gaozhong;
    }

    public String getChuzhong() {
        return chuzhong;
    }

    public void setChuzhong(String chuzhong) {
        this.chuzhong = chuzhong;
    }

    public String getWu() {
        return wu;
    }

    public void setWu(String wu) {
        this.wu = wu;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setZxSfProjectEmployeeList(List<ZxSfProjectEmployee> zxSfProjectEmployeeList) {
		this.zxSfProjectEmployeeList = zxSfProjectEmployeeList;
	}

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfProjectEmpMainId() {
        return zxSfProjectEmpMainId == null ? "" : zxSfProjectEmpMainId.trim();
    }

    public void setZxSfProjectEmpMainId(String zxSfProjectEmpMainId) {
        this.zxSfProjectEmpMainId = zxSfProjectEmpMainId == null ? null : zxSfProjectEmpMainId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getReporter() {
        return reporter == null ? "" : reporter.trim();
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public String getEditTime() {
        return editTime == null ? "" : editTime.trim();
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime == null ? null : editTime.trim();
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
