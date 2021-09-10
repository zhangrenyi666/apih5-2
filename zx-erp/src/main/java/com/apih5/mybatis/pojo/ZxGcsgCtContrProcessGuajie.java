package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCtContrProcessGuajie extends BasePojo {
	// 主键
	private String ctContrProcessGuajieId;

	// 合同清单ID(workID)
	private String ccWorksId;

	// 合同评审清单ID、合同补充协议清单ID(applyWorkID)
	private String applyAlterWorksId;

	// 合同评审ID补充协议ID(contrApplyID)
	private String ctContrApplyId;

	// 合同管理ID(contractID)
	private String ctContractId;

	// 工序ID
	private String processID;

	// 挂接工序编号
	private String processNo;

	// 最后编辑时间
	private Date editTime;

	// 工序名称
	private String processName;

	// 计量单位
	private String processUnit;

	// 工序库类型
	private String baseType;

	// 是否叶子节点
	private Integer isLeaf;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getCtContrProcessGuajieId() {
		return ctContrProcessGuajieId == null ? "" : ctContrProcessGuajieId.trim();
	}

	public void setCtContrProcessGuajieId(String ctContrProcessGuajieId) {
		this.ctContrProcessGuajieId = ctContrProcessGuajieId == null ? null : ctContrProcessGuajieId.trim();
	}

	public String getCcWorksId() {
		return ccWorksId == null ? "" : ccWorksId.trim();
	}

	public void setCcWorksId(String ccWorksId) {
		this.ccWorksId = ccWorksId == null ? null : ccWorksId.trim();
	}

	public String getApplyAlterWorksId() {
		return applyAlterWorksId == null ? "" : applyAlterWorksId.trim();
	}

	public void setApplyAlterWorksId(String applyAlterWorksId) {
		this.applyAlterWorksId = applyAlterWorksId == null ? null : applyAlterWorksId.trim();
	}

	public String getCtContrApplyId() {
		return ctContrApplyId == null ? "" : ctContrApplyId.trim();
	}

	public void setCtContrApplyId(String ctContrApplyId) {
		this.ctContrApplyId = ctContrApplyId == null ? null : ctContrApplyId.trim();
	}

	public String getCtContractId() {
		return ctContractId == null ? "" : ctContractId.trim();
	}

	public void setCtContractId(String ctContractId) {
		this.ctContractId = ctContractId == null ? null : ctContractId.trim();
	}

	public String getProcessID() {
		return processID == null ? "" : processID.trim();
	}

	public void setProcessID(String processID) {
		this.processID = processID == null ? null : processID.trim();
	}

	public String getProcessNo() {
		return processNo == null ? "" : processNo.trim();
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo == null ? null : processNo.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
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

	public String getBaseType() {
		return baseType == null ? "" : baseType.trim();
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType == null ? null : baseType.trim();
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
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
