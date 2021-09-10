package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CheckSfCheck extends BasePojo {
    // 主键
    private String checkSfCheckId;

    // 项目/机构名称
    private String orgName;

    // 项目/机构ID
    private String orgID;

    // 项目总数
    private int orgNum=0;

    // 已检查项目数
    private int checkNum=0;

    // 未检查项目数
    private int noCheckNum=0;

    // 检查覆盖率
    private BigDecimal checkFGL;

    // 开始日期
    private Date startDate;

    // 结束日期
    private Date endDate;

    // 公司ID
    private String companyId;

    // 公司名称
    private String companyName;

    public String getCheckSfCheckId() {
        return checkSfCheckId == null ? "" : checkSfCheckId.trim();
    }

    public void setCheckSfCheckId(String checkSfCheckId) {
        this.checkSfCheckId = checkSfCheckId == null ? null : checkSfCheckId.trim();
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

    public int getOrgNum() {
        return orgNum;
    }

    public void setOrgNum(int orgNum) {
        this.orgNum = orgNum;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public int getNoCheckNum() {
        return noCheckNum;
    }

    public void setNoCheckNum(int noCheckNum) {
        this.noCheckNum = noCheckNum;
    }

    public BigDecimal getCheckFGL() {
        return checkFGL;
    }

    public void setCheckFGL(BigDecimal checkFGL) {
        this.checkFGL = checkFGL;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

}
