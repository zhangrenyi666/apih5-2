package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqMoveUseOrg extends BasePojo {
    private String id;

    private String outOrgID;

    private String acceptOrgID;

    private String outOrgName;

    private String acceptOrgName;

    private Date movedate;

    private String deliveryLocation;

    private BigDecimal moveExes;

    private String exesPayOrg;

    private String outOpinion;

    private String outTransactor;

    private String acceptOpinion;

    private String acceptTransactor;

    private String adminOrgOpinion;

    private String approveStatus;

    private String remark;

    private String transferNo;

    private String reason;

    private String careinfo;

    private String adminLeader;

    private String equipAdmin;

    private String finance;

    private String lister;

    private String serialNo;

    private String inEquipManageDpt;

    private String acceptFinance;

    private String locality;

    private String comID;

    private String comName;

    private String outComID;

    private String outComName;

    private String acceptComID;

    private String acceptComName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxEqMoveUseOrgItem> itemList;

    private List<ZxErpFile> fileList;

    private String equipName;
    
    private String seeFlagForCom;
    
    private String seeFlagForPro;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public String getAcceptOrgID() {
        return acceptOrgID == null ? "" : acceptOrgID.trim();
    }

    public void setAcceptOrgID(String acceptOrgID) {
        this.acceptOrgID = acceptOrgID == null ? null : acceptOrgID.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public String getAcceptOrgName() {
        return acceptOrgName == null ? "" : acceptOrgName.trim();
    }

    public void setAcceptOrgName(String acceptOrgName) {
        this.acceptOrgName = acceptOrgName == null ? null : acceptOrgName.trim();
    }

    public Date getMovedate() {
        return movedate;
    }

    public void setMovedate(Date movedate) {
        this.movedate = movedate;
    }

    public String getDeliveryLocation() {
        return deliveryLocation == null ? "" : deliveryLocation.trim();
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation == null ? null : deliveryLocation.trim();
    }

    public BigDecimal getMoveExes() {
        return moveExes;
    }

    public void setMoveExes(BigDecimal moveExes) {
        this.moveExes = moveExes;
    }

    public String getExesPayOrg() {
        return exesPayOrg == null ? "" : exesPayOrg.trim();
    }

    public void setExesPayOrg(String exesPayOrg) {
        this.exesPayOrg = exesPayOrg == null ? null : exesPayOrg.trim();
    }

    public String getOutOpinion() {
        return outOpinion == null ? "" : outOpinion.trim();
    }

    public void setOutOpinion(String outOpinion) {
        this.outOpinion = outOpinion == null ? null : outOpinion.trim();
    }

    public String getOutTransactor() {
        return outTransactor == null ? "" : outTransactor.trim();
    }

    public void setOutTransactor(String outTransactor) {
        this.outTransactor = outTransactor == null ? null : outTransactor.trim();
    }

    public String getAcceptOpinion() {
        return acceptOpinion == null ? "" : acceptOpinion.trim();
    }

    public void setAcceptOpinion(String acceptOpinion) {
        this.acceptOpinion = acceptOpinion == null ? null : acceptOpinion.trim();
    }

    public String getAcceptTransactor() {
        return acceptTransactor == null ? "" : acceptTransactor.trim();
    }

    public void setAcceptTransactor(String acceptTransactor) {
        this.acceptTransactor = acceptTransactor == null ? null : acceptTransactor.trim();
    }

    public String getAdminOrgOpinion() {
        return adminOrgOpinion == null ? "" : adminOrgOpinion.trim();
    }

    public void setAdminOrgOpinion(String adminOrgOpinion) {
        this.adminOrgOpinion = adminOrgOpinion == null ? null : adminOrgOpinion.trim();
    }

    public String getApproveStatus() {
        return approveStatus == null ? "" : approveStatus.trim();
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTransferNo() {
        return transferNo == null ? "" : transferNo.trim();
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo == null ? null : transferNo.trim();
    }

    public String getReason() {
        return reason == null ? "" : reason.trim();
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getCareinfo() {
        return careinfo == null ? "" : careinfo.trim();
    }

    public void setCareinfo(String careinfo) {
        this.careinfo = careinfo == null ? null : careinfo.trim();
    }

    public String getAdminLeader() {
        return adminLeader == null ? "" : adminLeader.trim();
    }

    public void setAdminLeader(String adminLeader) {
        this.adminLeader = adminLeader == null ? null : adminLeader.trim();
    }

    public String getEquipAdmin() {
        return equipAdmin == null ? "" : equipAdmin.trim();
    }

    public void setEquipAdmin(String equipAdmin) {
        this.equipAdmin = equipAdmin == null ? null : equipAdmin.trim();
    }

    public String getFinance() {
        return finance == null ? "" : finance.trim();
    }

    public void setFinance(String finance) {
        this.finance = finance == null ? null : finance.trim();
    }

    public String getLister() {
        return lister == null ? "" : lister.trim();
    }

    public void setLister(String lister) {
        this.lister = lister == null ? null : lister.trim();
    }

    public String getSerialNo() {
        return serialNo == null ? "" : serialNo.trim();
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getInEquipManageDpt() {
        return inEquipManageDpt == null ? "" : inEquipManageDpt.trim();
    }

    public void setInEquipManageDpt(String inEquipManageDpt) {
        this.inEquipManageDpt = inEquipManageDpt == null ? null : inEquipManageDpt.trim();
    }

    public String getAcceptFinance() {
        return acceptFinance == null ? "" : acceptFinance.trim();
    }

    public void setAcceptFinance(String acceptFinance) {
        this.acceptFinance = acceptFinance == null ? null : acceptFinance.trim();
    }

    public String getLocality() {
        return locality == null ? "" : locality.trim();
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
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

    public String getOutComID() {
        return outComID == null ? "" : outComID.trim();
    }

    public void setOutComID(String outComID) {
        this.outComID = outComID == null ? null : outComID.trim();
    }

    public String getOutComName() {
        return outComName == null ? "" : outComName.trim();
    }

    public void setOutComName(String outComName) {
        this.outComName = outComName == null ? null : outComName.trim();
    }

    public String getAcceptComID() {
        return acceptComID == null ? "" : acceptComID.trim();
    }

    public void setAcceptComID(String acceptComID) {
        this.acceptComID = acceptComID == null ? null : acceptComID.trim();
    }

    public String getAcceptComName() {
        return acceptComName == null ? "" : acceptComName.trim();
    }

    public void setAcceptComName(String acceptComName) {
        this.acceptComName = acceptComName == null ? null : acceptComName.trim();
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

    public List<ZxEqMoveUseOrgItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxEqMoveUseOrgItem> itemList) {
        this.itemList = itemList;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

	public String getSeeFlagForCom() {
		return seeFlagForCom;
	}

	public void setSeeFlagForCom(String seeFlagForCom) {
		this.seeFlagForCom = seeFlagForCom;
	}

	public String getSeeFlagForPro() {
		return seeFlagForPro;
	}

	public void setSeeFlagForPro(String seeFlagForPro) {
		this.seeFlagForPro = seeFlagForPro;
	}

}