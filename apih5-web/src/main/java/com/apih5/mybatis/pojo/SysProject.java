package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SysProject extends BasePojo {
    // 主键
    private String departmentId;

    // 项目简称
    private String departmentName;

    // 项目全称
    private String departmentFullName;

    // 项目父ID
    private String departmentParentId;

    // 项目路径
    private String departmentPath;

    // 项目路径名称
    private String departmentPathName;

    // 项目标识（机构类型）
    private String departmentFlag;

    // 项目标识（机构类型）
    private String departmentFlagName;

    // 所属公司
    private String companyId;

    // 所属公司
    private String companyName;

    // 公司项目表
    private String companyConf;

    // 所属项目
    private String projectId;

    // 所属项目
    private String projectName;

    // 总部ID
    private String headquartersId;

    // 总部名称
    private String headquartersName;

    // 总部项目表
    private String headquartersConf;

    // 托管ID
    private String delegateId;

    // 托管名称
    private String delegateName;

    // 托管项目表
    private String delegateConf;

    // 项目表文件up
    private String confUp;

    // 项目表文件down
    private String confDown;

    // 是否显示下一层
    private String confDownShowFlag;

    // 其他id（如微信等）
    private String otherId;

    // 项目拼音
    private String projectPinyin;

    // 承建项目类型
    private String contractorType;

    // 是否局直属
    private String juFlag;

    // 局直属项目名称
    private String juName;

    // 项目施工单位
    private String locationInDeprFormula;

    // 承建单位简称
    private String contractor;

    // 中标资质
    private String biddingQualification;

    // 所属事业部
    private String bizDep;

    // 所属区域
    private String subArea;

    // 项目所在地
    private String projectLocation;

    // 项目所在省份简称
    private String provinceAbbreviation;

    // 市级
    private String cityName;

    // 经纬度
    private String mapXY;

    // 标段号
    private String lotNo;

    // 工程类别
    private String projType;

    // 项目性质
    private String projectProperty;

    // 项目特征
    private String proDescribe;

    // 主要工程项目及工程数量
    private String detail;

    // 开工日期
    private Date actualStartDate;

    // 主体完工日期
    private Date mainEndDate;

    // 交工日期
    private Date deliveryDate;

    // 实际竣工日期
    private Date actualEndDate;

    // 归档日期
    private Date docDate;

    // 项目经理姓名
    private String projectChiefName;

    // 项目承包模式
    private String contractingType;

    // 项目经理电话
    private String projectChiefTel;

    // 公司类型
    private String companyType;

    // 项目级别
    private String proLevel;

    // 合同金额
    private BigDecimal contractAmount;

    // 平均产值
    private BigDecimal outputValue;

    // 工程造价
    private BigDecimal proCost;

    // 计量金额
    private BigDecimal meteringAmount;

    // 合同工期
    private String schedule;

    // 项目人员限定数量
    private Integer departmentNum;

    // 项目表up数组
    private JSONArray confUpJSONArray;

    // 项目表down数组
    private JSONArray confDownJSONArray;

    // 所属公司
    private JSONArray companyJSONArray;

    // 总部JSONArray
    private JSONArray headquartersJSONArray;

    // 托管JSONArray
    private JSONArray delegateJSONArray;

    // userKey
    private String userKey;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentFullName() {
        return departmentFullName == null ? "" : departmentFullName.trim();
    }

    public void setDepartmentFullName(String departmentFullName) {
        this.departmentFullName = departmentFullName == null ? null : departmentFullName.trim();
    }

    public String getDepartmentParentId() {
        return departmentParentId == null ? "" : departmentParentId.trim();
    }

    public void setDepartmentParentId(String departmentParentId) {
        this.departmentParentId = departmentParentId == null ? null : departmentParentId.trim();
    }

    public String getDepartmentPath() {
        return departmentPath == null ? "" : departmentPath.trim();
    }

    public void setDepartmentPath(String departmentPath) {
        this.departmentPath = departmentPath == null ? null : departmentPath.trim();
    }

    public String getDepartmentPathName() {
        return departmentPathName == null ? "" : departmentPathName.trim();
    }

    public void setDepartmentPathName(String departmentPathName) {
        this.departmentPathName = departmentPathName == null ? null : departmentPathName.trim();
    }

    public String getDepartmentFlag() {
        return departmentFlag == null ? "" : departmentFlag.trim();
    }

    public void setDepartmentFlag(String departmentFlag) {
        this.departmentFlag = departmentFlag == null ? null : departmentFlag.trim();
    }

    public String getDepartmentFlagName() {
        return departmentFlagName == null ? "" : departmentFlagName.trim();
    }

    public void setDepartmentFlagName(String departmentFlagName) {
        this.departmentFlagName = departmentFlagName == null ? null : departmentFlagName.trim();
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

    public String getCompanyConf() {
        return companyConf == null ? "" : companyConf.trim();
    }

    public void setCompanyConf(String companyConf) {
        this.companyConf = companyConf == null ? null : companyConf.trim();
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

    public String getHeadquartersId() {
        return headquartersId == null ? "" : headquartersId.trim();
    }

    public void setHeadquartersId(String headquartersId) {
        this.headquartersId = headquartersId == null ? null : headquartersId.trim();
    }

    public String getHeadquartersName() {
        return headquartersName == null ? "" : headquartersName.trim();
    }

    public void setHeadquartersName(String headquartersName) {
        this.headquartersName = headquartersName == null ? null : headquartersName.trim();
    }

    public String getHeadquartersConf() {
        return headquartersConf == null ? "" : headquartersConf.trim();
    }

    public void setHeadquartersConf(String headquartersConf) {
        this.headquartersConf = headquartersConf == null ? null : headquartersConf.trim();
    }

    public String getDelegateId() {
        return delegateId == null ? "" : delegateId.trim();
    }

    public void setDelegateId(String delegateId) {
        this.delegateId = delegateId == null ? null : delegateId.trim();
    }

    public String getDelegateName() {
        return delegateName == null ? "" : delegateName.trim();
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName == null ? null : delegateName.trim();
    }

    public String getDelegateConf() {
        return delegateConf == null ? "" : delegateConf.trim();
    }

    public void setDelegateConf(String delegateConf) {
        this.delegateConf = delegateConf == null ? null : delegateConf.trim();
    }

    public String getConfUp() {
        return confUp == null ? "" : confUp.trim();
    }

    public void setConfUp(String confUp) {
        this.confUp = confUp == null ? null : confUp.trim();
    }

    public String getConfDown() {
        return confDown == null ? "" : confDown.trim();
    }

    public void setConfDown(String confDown) {
        this.confDown = confDown == null ? null : confDown.trim();
    }

    public String getConfDownShowFlag() {
        return confDownShowFlag == null ? "" : confDownShowFlag.trim();
    }

    public void setConfDownShowFlag(String confDownShowFlag) {
        this.confDownShowFlag = confDownShowFlag == null ? null : confDownShowFlag.trim();
    }

    public String getOtherId() {
        return otherId == null ? "" : otherId.trim();
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId == null ? null : otherId.trim();
    }

    public String getProjectPinyin() {
        return projectPinyin == null ? "" : projectPinyin.trim();
    }

    public void setProjectPinyin(String projectPinyin) {
        this.projectPinyin = projectPinyin == null ? null : projectPinyin.trim();
    }

    public String getContractorType() {
        return contractorType == null ? "" : contractorType.trim();
    }

    public void setContractorType(String contractorType) {
        this.contractorType = contractorType == null ? null : contractorType.trim();
    }

    public String getJuFlag() {
        return juFlag == null ? "" : juFlag.trim();
    }

    public void setJuFlag(String juFlag) {
        this.juFlag = juFlag == null ? null : juFlag.trim();
    }

    public String getJuName() {
        return juName == null ? "" : juName.trim();
    }

    public void setJuName(String juName) {
        this.juName = juName == null ? null : juName.trim();
    }

    public String getLocationInDeprFormula() {
        return locationInDeprFormula == null ? "" : locationInDeprFormula.trim();
    }

    public void setLocationInDeprFormula(String locationInDeprFormula) {
        this.locationInDeprFormula = locationInDeprFormula == null ? null : locationInDeprFormula.trim();
    }

    public String getContractor() {
        return contractor == null ? "" : contractor.trim();
    }

    public void setContractor(String contractor) {
        this.contractor = contractor == null ? null : contractor.trim();
    }

    public String getBiddingQualification() {
        return biddingQualification == null ? "" : biddingQualification.trim();
    }

    public void setBiddingQualification(String biddingQualification) {
        this.biddingQualification = biddingQualification == null ? null : biddingQualification.trim();
    }

    public String getBizDep() {
        return bizDep == null ? "" : bizDep.trim();
    }

    public void setBizDep(String bizDep) {
        this.bizDep = bizDep == null ? null : bizDep.trim();
    }

    public String getSubArea() {
        return subArea == null ? "" : subArea.trim();
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea == null ? null : subArea.trim();
    }

    public String getProjectLocation() {
        return projectLocation == null ? "" : projectLocation.trim();
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation == null ? null : projectLocation.trim();
    }

    public String getProvinceAbbreviation() {
        return provinceAbbreviation == null ? "" : provinceAbbreviation.trim();
    }

    public void setProvinceAbbreviation(String provinceAbbreviation) {
        this.provinceAbbreviation = provinceAbbreviation == null ? null : provinceAbbreviation.trim();
    }

    public String getCityName() {
        return cityName == null ? "" : cityName.trim();
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getMapXY() {
        return mapXY == null ? "" : mapXY.trim();
    }

    public void setMapXY(String mapXY) {
        this.mapXY = mapXY == null ? null : mapXY.trim();
    }

    public String getLotNo() {
        return lotNo == null ? "" : lotNo.trim();
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo == null ? null : lotNo.trim();
    }

    public String getProjType() {
        return projType == null ? "" : projType.trim();
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

    public String getProjectProperty() {
        return projectProperty == null ? "" : projectProperty.trim();
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty = projectProperty == null ? null : projectProperty.trim();
    }

    public String getProDescribe() {
        return proDescribe == null ? "" : proDescribe.trim();
    }

    public void setProDescribe(String proDescribe) {
        this.proDescribe = proDescribe == null ? null : proDescribe.trim();
    }

    public String getDetail() {
        return detail == null ? "" : detail.trim();
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getMainEndDate() {
        return mainEndDate;
    }

    public void setMainEndDate(Date mainEndDate) {
        this.mainEndDate = mainEndDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getProjectChiefName() {
        return projectChiefName == null ? "" : projectChiefName.trim();
    }

    public void setProjectChiefName(String projectChiefName) {
        this.projectChiefName = projectChiefName == null ? null : projectChiefName.trim();
    }

    public String getContractingType() {
        return contractingType == null ? "" : contractingType.trim();
    }

    public void setContractingType(String contractingType) {
        this.contractingType = contractingType == null ? null : contractingType.trim();
    }

    public String getProjectChiefTel() {
        return projectChiefTel == null ? "" : projectChiefTel.trim();
    }

    public void setProjectChiefTel(String projectChiefTel) {
        this.projectChiefTel = projectChiefTel == null ? null : projectChiefTel.trim();
    }

    public String getCompanyType() {
        return companyType == null ? "" : companyType.trim();
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getProLevel() {
        return proLevel == null ? "" : proLevel.trim();
    }

    public void setProLevel(String proLevel) {
        this.proLevel = proLevel == null ? null : proLevel.trim();
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(BigDecimal outputValue) {
        this.outputValue = outputValue;
    }

    public BigDecimal getProCost() {
        return proCost;
    }

    public void setProCost(BigDecimal proCost) {
        this.proCost = proCost;
    }

    public BigDecimal getMeteringAmount() {
        return meteringAmount;
    }

    public void setMeteringAmount(BigDecimal meteringAmount) {
        this.meteringAmount = meteringAmount;
    }

    public String getSchedule() {
        return schedule == null ? "" : schedule.trim();
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule == null ? null : schedule.trim();
    }

    public Integer getDepartmentNum() {
        return departmentNum;
    }

    public void setDepartmentNum(Integer departmentNum) {
        this.departmentNum = departmentNum;
    }

    public JSONArray getConfUpJSONArray() {
        return confUpJSONArray == null ? new JSONArray() : confUpJSONArray;
    }

    public void setConfUpJSONArray(JSONArray confUpJSONArray) {
        this.confUpJSONArray = confUpJSONArray == null ? null : confUpJSONArray;
    }

    public JSONArray getConfDownJSONArray() {
        return confDownJSONArray == null ? new JSONArray() : confDownJSONArray;
    }

    public void setConfDownJSONArray(JSONArray confDownJSONArray) {
        this.confDownJSONArray = confDownJSONArray == null ? null : confDownJSONArray;
    }

    public JSONArray getCompanyJSONArray() {
        return companyJSONArray == null ? new JSONArray() : companyJSONArray;
    }

    public void setCompanyJSONArray(JSONArray companyJSONArray) {
        this.companyJSONArray = companyJSONArray == null ? null : companyJSONArray;
    }

    public JSONArray getHeadquartersJSONArray() {
        return headquartersJSONArray == null ? new JSONArray() : headquartersJSONArray;
    }

    public void setHeadquartersJSONArray(JSONArray headquartersJSONArray) {
        this.headquartersJSONArray = headquartersJSONArray == null ? null : headquartersJSONArray;
    }

    public JSONArray getDelegateJSONArray() {
        return delegateJSONArray == null ? new JSONArray() : delegateJSONArray;
    }

    public void setDelegateJSONArray(JSONArray delegateJSONArray) {
        this.delegateJSONArray = delegateJSONArray == null ? null : delegateJSONArray;
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
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
