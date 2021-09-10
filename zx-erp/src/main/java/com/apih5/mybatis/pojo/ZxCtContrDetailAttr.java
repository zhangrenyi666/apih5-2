package com.apih5.mybatis.pojo;

import java.math.BigDecimal;

import com.apih5.framework.entity.BasePojo;

public class ZxCtContrDetailAttr extends BasePojo {
	// 主键
	private String id;

	// 合同条款ID
	private String contrDetailID;

	// 保证金
	private String pledgeMoney;

	// 类型
	private String type;

	// 金额
	private BigDecimal money;

	// 返还条件
	private String backCondition;

	// 期限
	private String timeLimit;

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

	public String getContrDetailID() {
		return contrDetailID == null ? "" : contrDetailID.trim();
	}

	public void setContrDetailID(String contrDetailID) {
		this.contrDetailID = contrDetailID == null ? null : contrDetailID.trim();
	}

	public String getPledgeMoney() {
		return pledgeMoney == null ? "" : pledgeMoney.trim();
	}

	public void setPledgeMoney(String pledgeMoney) {
		this.pledgeMoney = pledgeMoney == null ? null : pledgeMoney.trim();
	}

	public String getType() {
		return type == null ? "" : type.trim();
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getBackCondition() {
		return backCondition == null ? "" : backCondition.trim();
	}

	public void setBackCondition(String backCondition) {
		this.backCondition = backCondition == null ? null : backCondition.trim();
	}

	public String getTimeLimit() {
		return timeLimit == null ? "" : timeLimit.trim();
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit == null ? null : timeLimit.trim();
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
