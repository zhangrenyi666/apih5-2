package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqOuterEquip extends BasePojo {
    private String id;

    private String orgID;

    private String orgName;

    private String parentID;

    private String parentName;

    private String equipID ;

    private String equipName;

    private String spec;

    private String model;

    private String power;

    private String outfactory;

    private Date outfactoryDate;

    private BigDecimal orginalValue;

    private BigDecimal leftValue;

    private String place;

    private String placeName;

    private String leaseLimit;

    private String leaseprice;

    private String supplierID;

    private String supplierMaster;

    private String operator;

    private Date inDate;

    private Date outDate;

    private String remark;

    private String state;

    private String contrID;

    private String contrItem;

    private String equipNo;

    private String contrItemID;

    private String passNo;

    private Date startEndDate;

    private String comID;

    private String comName;

    private String acceptance;

    private String isSepcial;

    private String inspReport;

    private String inspCert;

    private String opCert;

    private String isOut;

    private String qrcodeName;

    private String qrcodeUrl;

    private String qrcodeContent;

    private String qrcodeDownUrl;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxErpFile> fileList;

    private String projectName;

    private String useOrgID;

    private String beginDate;

    private String endDate;

    private String secondID;

    private String secondName;

    private String outFactoryDateStr;

    private String supOperator;

    private String startEndDateStr;

    private String inDateStr;

    private String outDateStr;

    private String ureportFlag;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getParentName() {
        return parentName == null ? "" : parentName.trim();
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    public String getEquipID () {
        return equipID  == null ? "" : equipID .trim();
    }

    public void setEquipID (String equipID ) {
        this.equipID  = equipID  == null ? null : equipID .trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getPower() {
        return power == null ? "" : power.trim();
    }

    public void setPower(String power) {
        this.power = power == null ? null : power.trim();
    }

    public String getOutfactory() {
        return outfactory == null ? "" : outfactory.trim();
    }

    public void setOutfactory(String outfactory) {
        this.outfactory = outfactory == null ? null : outfactory.trim();
    }

    public Date getOutfactoryDate() {
        return outfactoryDate;
    }

    public void setOutfactoryDate(Date outfactoryDate) {
        this.outfactoryDate = outfactoryDate;
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public BigDecimal getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(BigDecimal leftValue) {
        this.leftValue = leftValue;
    }

    public String getPlace() {
        return place == null ? "" : place.trim();
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getPlaceName() {
        return placeName == null ? "" : placeName.trim();
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName == null ? null : placeName.trim();
    }

    public String getLeaseLimit() {
        return leaseLimit == null ? "" : leaseLimit.trim();
    }

    public void setLeaseLimit(String leaseLimit) {
        this.leaseLimit = leaseLimit == null ? null : leaseLimit.trim();
    }

    public String getLeaseprice() {
        return leaseprice == null ? "" : leaseprice.trim();
    }

    public void setLeaseprice(String leaseprice) {
        this.leaseprice = leaseprice == null ? null : leaseprice.trim();
    }

    public String getSupplierID() {
        return supplierID == null ? "" : supplierID.trim();
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID == null ? null : supplierID.trim();
    }

    public String getSupplierMaster() {
        return supplierMaster == null ? "" : supplierMaster.trim();
    }

    public void setSupplierMaster(String supplierMaster) {
        this.supplierMaster = supplierMaster == null ? null : supplierMaster.trim();
    }

    public String getOperator() {
        return operator == null ? "" : operator.trim();
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getState() {
        return state == null ? "" : state.trim();
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getContrID() {
        return contrID == null ? "" : contrID.trim();
    }

    public void setContrID(String contrID) {
        this.contrID = contrID == null ? null : contrID.trim();
    }

    public String getContrItem() {
        return contrItem == null ? "" : contrItem.trim();
    }

    public void setContrItem(String contrItem) {
        this.contrItem = contrItem == null ? null : contrItem.trim();
    }

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getContrItemID() {
        return contrItemID == null ? "" : contrItemID.trim();
    }

    public void setContrItemID(String contrItemID) {
        this.contrItemID = contrItemID == null ? null : contrItemID.trim();
    }

    public String getPassNo() {
        return passNo == null ? "" : passNo.trim();
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo == null ? null : passNo.trim();
    }

    public Date getStartEndDate() {
        return startEndDate;
    }

    public void setStartEndDate(Date startEndDate) {
        this.startEndDate = startEndDate;
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getAcceptance() {
        return acceptance == null ? "" : acceptance.trim();
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance == null ? null : acceptance.trim();
    }

    public String getIsSepcial() {
        return isSepcial == null ? "" : isSepcial.trim();
    }

    public void setIsSepcial(String isSepcial) {
        this.isSepcial = isSepcial == null ? null : isSepcial.trim();
    }

    public String getInspReport() {
        return inspReport == null ? "" : inspReport.trim();
    }

    public void setInspReport(String inspReport) {
        this.inspReport = inspReport == null ? null : inspReport.trim();
    }

    public String getInspCert() {
        return inspCert == null ? "" : inspCert.trim();
    }

    public void setInspCert(String inspCert) {
        this.inspCert = inspCert == null ? null : inspCert.trim();
    }

    public String getOpCert() {
        return opCert == null ? "" : opCert.trim();
    }

    public void setOpCert(String opCert) {
        this.opCert = opCert == null ? null : opCert.trim();
    }

    public String getIsOut() {
        return isOut == null ? "" : isOut.trim();
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut == null ? null : isOut.trim();
    }

    public String getQrcodeName() {
        return qrcodeName == null ? "" : qrcodeName.trim();
    }

    public void setQrcodeName(String qrcodeName) {
        this.qrcodeName = qrcodeName == null ? null : qrcodeName.trim();
    }

    public String getQrcodeUrl() {
        return qrcodeUrl == null ? "" : qrcodeUrl.trim();
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
    }

    public String getQrcodeContent() {
        return qrcodeContent == null ? "" : qrcodeContent.trim();
    }

    public void setQrcodeContent(String qrcodeContent) {
        this.qrcodeContent = qrcodeContent == null ? null : qrcodeContent.trim();
    }

    public String getQrcodeDownUrl() {
        return qrcodeDownUrl == null ? "" : qrcodeDownUrl.trim();
    }

    public void setQrcodeDownUrl(String qrcodeDownUrl) {
        this.qrcodeDownUrl = qrcodeDownUrl == null ? null : qrcodeDownUrl.trim();
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

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getUseOrgID() {
        return useOrgID == null ? "" : useOrgID.trim();
    }

    public void setUseOrgID(String useOrgID) {
        this.useOrgID = useOrgID == null ? null : useOrgID.trim();
    }

    public String getBeginDate() {
        return beginDate == null ? "" : beginDate.trim();
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getEndDate() {
        return endDate == null ? "" : endDate.trim();
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getSecondID() {
        return secondID == null ? "" : secondID.trim();
    }

    public void setSecondID(String secondID) {
        this.secondID = secondID == null ? null : secondID.trim();
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public String getOutFactoryDateStr() {
        return outFactoryDateStr == null ? "" : outFactoryDateStr.trim();
    }

    public void setOutFactoryDateStr(String outFactoryDateStr) {
        this.outFactoryDateStr = outFactoryDateStr == null ? null : outFactoryDateStr.trim();
    }

    public String getSupOperator() {
        return supOperator == null ? "" : supOperator.trim();
    }

    public void setSupOperator(String supOperator) {
        this.supOperator = supOperator == null ? null : supOperator.trim();
    }

    public String getStartEndDateStr() {
        return startEndDateStr == null ? "" : startEndDateStr.trim();
    }

    public void setStartEndDateStr(String startEndDateStr) {
        this.startEndDateStr = startEndDateStr == null ? null : startEndDateStr.trim();
    }

    public String getInDateStr() {
        return inDateStr == null ? "" : inDateStr.trim();
    }

    public void setInDateStr(String inDateStr) {
        this.inDateStr = inDateStr == null ? null : inDateStr.trim();
    }

    public String getOutDateStr() {
        return outDateStr == null ? "" : outDateStr.trim();
    }

    public void setOutDateStr(String outDateStr) {
        this.outDateStr = outDateStr == null ? null : outDateStr.trim();
    }

    public String getUreportFlag() {
        return ureportFlag == null ? "" : ureportFlag.trim();
    }

    public void setUreportFlag(String ureportFlag) {
        this.ureportFlag = ureportFlag == null ? null : ureportFlag.trim();
    }

}

