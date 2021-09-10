package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrCustomerNew extends BasePojo {
    // 主键
    private String zxCrCustomerNewId;

    // 供应商名称
    private String customerName;

    // 供应商编号
    private String customerNo;

    // 法人
    private String corparation;

    // 组织机构代码证
    private String orgCertificate;

    // 省份
    private String province;

    // 市
    private String district;

    // 地址
    private String address;

    // 电话
    private String telephone;

    // 传真
    private String fax;

    // 邮箱
    private String email;

    // 主页
    private String homePage;

    // 经营范围
    private String services;

    // 更新时间
    private Date editTime;

    // 注册日期
    private Date regDate;

    // 注册资金
    private BigDecimal regMoney;

    // 所属公司名称
    private String companyName;

    // 所属公司ID
    private String companyId;

    // 项目名称ID
    private String projectId;

    // 项目名称
    private String projectName;

    // 供应商类型
    private String type;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String search;

    private String inOrgName;
    
    public String getInOrgName() {
		return inOrgName;
	}

	public void setInOrgName(String inOrgName) {
		this.inOrgName = inOrgName;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getZxCrCustomerNewId() {
        return zxCrCustomerNewId == null ? "" : zxCrCustomerNewId.trim();
    }

    public void setZxCrCustomerNewId(String zxCrCustomerNewId) {
        this.zxCrCustomerNewId = zxCrCustomerNewId == null ? null : zxCrCustomerNewId.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerNo() {
        return customerNo == null ? "" : customerNo.trim();
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getCorparation() {
        return corparation == null ? "" : corparation.trim();
    }

    public void setCorparation(String corparation) {
        this.corparation = corparation == null ? null : corparation.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getProvince() {
        return province == null ? "" : province.trim();
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getDistrict() {
        return district == null ? "" : district.trim();
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAddress() {
        return address == null ? "" : address.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTelephone() {
        return telephone == null ? "" : telephone.trim();
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getFax() {
        return fax == null ? "" : fax.trim();
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email == null ? "" : email.trim();
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHomePage() {
        return homePage == null ? "" : homePage.trim();
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage == null ? null : homePage.trim();
    }

    public String getServices() {
        return services == null ? "" : services.trim();
    }

    public void setServices(String services) {
        this.services = services == null ? null : services.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public BigDecimal getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(BigDecimal regMoney) {
        this.regMoney = regMoney;
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

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
