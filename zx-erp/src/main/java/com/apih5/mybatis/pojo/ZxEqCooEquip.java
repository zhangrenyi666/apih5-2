package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqCooEquip extends BasePojo {
    private String id;

    private String orgID;

    private String subUnitID;

    private String editMan;

    private String remark;

    private String state;

    private String contractID;

    private String contractNo;

    private String contractName;

    private String comID;

    private String comName;

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

    private List<ZxEqCooEquipItem> itemList;
    
    private List<ZxErpFile> fileList;

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

    public String getSubUnitID() {
        return subUnitID == null ? "" : subUnitID.trim();
    }

    public void setSubUnitID(String subUnitID) {
        this.subUnitID = subUnitID == null ? null : subUnitID.trim();
    }

    public String getEditMan() {
        return editMan == null ? "" : editMan.trim();
    }

    public void setEditMan(String editMan) {
        this.editMan = editMan == null ? null : editMan.trim();
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

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
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

    public List<ZxEqCooEquipItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxEqCooEquipItem> itemList) {
        this.itemList = itemList;
    }

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}
    
    

}

