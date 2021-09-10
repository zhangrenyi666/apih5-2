package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxCtSZProcess extends BasePojo {
	private String id;

	private String parentID;

	private String treeNode;

	private String oldTreeNode;

	private String processNo;

	private String processName;

	private String processUnit;

	private String content;

	private String priceType;

	private String remark;

	private String deleted;

	private String isLeaf;

	private String isGroup;

	private String editUserID;

	private String editUserName;

	private String comID;

	private String comName;

	private Date editTime;

	private String baseType;

	private String isProcess;

	private String oldProcessNo;

	private String isCustom;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	// 分包合同中引用字段
	private String ctContrProcessGuajieId;

	private String applyAlterWorksId;

	private String ctContrApplyId;

	private String ctContractId;

	private String ccWorksId;

	public String getCtContrProcessGuajieId() {
		return ctContrProcessGuajieId;
	}

	public void setCtContrProcessGuajieId(String ctContrProcessGuajieId) {
		this.ctContrProcessGuajieId = ctContrProcessGuajieId;
	}

	public String getApplyAlterWorksId() {
		return applyAlterWorksId;
	}

	public void setApplyAlterWorksId(String applyAlterWorksId) {
		this.applyAlterWorksId = applyAlterWorksId;
	}

	public String getCtContrApplyId() {
		return ctContrApplyId;
	}

	public void setCtContrApplyId(String ctContrApplyId) {
		this.ctContrApplyId = ctContrApplyId;
	}

	public String getCtContractId() {
		return ctContractId;
	}

	public void setCtContractId(String ctContractId) {
		this.ctContractId = ctContractId;
	}

	public String getCcWorksId() {
		return ccWorksId;
	}

	public void setCcWorksId(String ccWorksId) {
		this.ccWorksId = ccWorksId;
	}

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getParentID() {
		return parentID == null ? "" : parentID.trim();
	}

	public void setParentID(String parentID) {
		this.parentID = parentID == null ? null : parentID.trim();
	}

	public String getTreeNode() {
		return treeNode == null ? "" : treeNode.trim();
	}

	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode == null ? null : treeNode.trim();
	}

	public String getOldTreeNode() {
		return oldTreeNode == null ? "" : oldTreeNode.trim();
	}

	public void setOldTreeNode(String oldTreeNode) {
		this.oldTreeNode = oldTreeNode == null ? null : oldTreeNode.trim();
	}

	public String getProcessNo() {
		return processNo == null ? "" : processNo.trim();
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo == null ? null : processNo.trim();
	}

	public String getProcessName() {
		return processName == null ? "" : processName.trim();
	}

	public void setProcessName(String processName) {
		this.processName = processName == null ? null : processName.trim();
	}

	public String getProcessUnit() {
		return processUnit == null ? "" : processUnit.trim();
	}

	public void setProcessUnit(String processUnit) {
		this.processUnit = processUnit == null ? null : processUnit.trim();
	}

	public String getContent() {
		return content == null ? "" : content.trim();
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getPriceType() {
		return priceType == null ? "" : priceType.trim();
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType == null ? null : priceType.trim();
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getDeleted() {
		return deleted == null ? "" : deleted.trim();
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted == null ? null : deleted.trim();
	}

	public String getIsLeaf() {
		return isLeaf == null ? "" : isLeaf.trim();
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf == null ? null : isLeaf.trim();
	}

	public String getIsGroup() {
		return isGroup == null ? "" : isGroup.trim();
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup == null ? null : isGroup.trim();
	}

	public String getEditUserID() {
		return editUserID == null ? "" : editUserID.trim();
	}

	public void setEditUserID(String editUserID) {
		this.editUserID = editUserID == null ? null : editUserID.trim();
	}

	public String getEditUserName() {
		return editUserName == null ? "" : editUserName.trim();
	}

	public void setEditUserName(String editUserName) {
		this.editUserName = editUserName == null ? null : editUserName.trim();
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

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getBaseType() {
		return baseType == null ? "" : baseType.trim();
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType == null ? null : baseType.trim();
	}

	public String getIsProcess() {
		return isProcess == null ? "" : isProcess.trim();
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess == null ? null : isProcess.trim();
	}

	public String getOldProcessNo() {
		return oldProcessNo == null ? "" : oldProcessNo.trim();
	}

	public void setOldProcessNo(String oldProcessNo) {
		this.oldProcessNo = oldProcessNo == null ? null : oldProcessNo.trim();
	}

	public String getIsCustom() {
		return isCustom == null ? "" : isCustom.trim();
	}

	public void setIsCustom(String isCustom) {
		this.isCustom = isCustom == null ? null : isCustom.trim();
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
