package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxCtWorkAlterSingle extends BasePojo {
	// 主键ID
	private String id;

	// 清单ID
	private String workID;

	// 清单编号
	private String workNo;

	// 清单名称
	private String workName;

	// 计量单位
	private String unit;

	// 变更后数量
	private BigDecimal quantity;

	// 变更后单价
	private BigDecimal price;

	// 变更人
	private String alterPerson;

	// 变更时间
	private Date alterDate;

	// 变更令
	private String alterSign;

	// 说明
	private String information;

	// 类型
	private String alterType;

	// 原来数量
	private BigDecimal originalQuantity;

	// 原来单价
	private BigDecimal originalPrice;

	// 编辑时间
	private Date editTime;

	// 是否禁用
	private String replyType;

	// 是否叶子节点
	private int isLeaf = 0;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getWorkID() {
		return workID == null ? "" : workID.trim();
	}

	public void setWorkID(String workID) {
		this.workID = workID == null ? null : workID.trim();
	}

	public String getWorkNo() {
		return workNo == null ? "" : workNo.trim();
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo == null ? null : workNo.trim();
	}

	public String getWorkName() {
		return workName == null ? "" : workName.trim();
	}

	public void setWorkName(String workName) {
		this.workName = workName == null ? null : workName.trim();
	}

	public String getUnit() {
		return unit == null ? "" : unit.trim();
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAlterPerson() {
		return alterPerson == null ? "" : alterPerson.trim();
	}

	public void setAlterPerson(String alterPerson) {
		this.alterPerson = alterPerson == null ? null : alterPerson.trim();
	}

	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	public String getAlterSign() {
		return alterSign == null ? "" : alterSign.trim();
	}

	public void setAlterSign(String alterSign) {
		this.alterSign = alterSign == null ? null : alterSign.trim();
	}

	public String getInformation() {
		return information == null ? "" : information.trim();
	}

	public void setInformation(String information) {
		this.information = information == null ? null : information.trim();
	}

	public String getAlterType() {
		return alterType == null ? "" : alterType.trim();
	}

	public void setAlterType(String alterType) {
		this.alterType = alterType == null ? null : alterType.trim();
	}

	public BigDecimal getOriginalQuantity() {
		return originalQuantity;
	}

	public void setOriginalQuantity(BigDecimal originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getReplyType() {
		return replyType == null ? "" : replyType.trim();
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType == null ? null : replyType.trim();
	}

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
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
