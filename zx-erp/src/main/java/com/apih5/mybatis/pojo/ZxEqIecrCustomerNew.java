package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxEqIecrCustomerNew extends BasePojo {
    private String customerNewID;

    private String customerName;

    private String customerNo;

    private String corparation;

    private String orgCertificate;

    private String province;

    private String district;

    private String address;

    private String telephone;

    private String fax;

    private String email;

    private String homePage;

    private String supplierType;

    private String services;

    private Date editTime;

    private Date regDate;

    private BigDecimal regMoney;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getCustomerNewID() {
        return customerNewID == null ? "" : customerNewID.trim();
    }

    public void setCustomerNewID(String customerNewID) {
        this.customerNewID = customerNewID == null ? null : customerNewID.trim();
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

    public String getSupplierType() {
        return supplierType == null ? "" : supplierType.trim();
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType == null ? null : supplierType.trim();
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

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

}

