package com.apih5.mybatis.pojo;

import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZjLzehManageTaskPlan extends BasePojo {
	// 主键
	private String zjLzehManageTaskPlanId;

	// 月份
	private Date month;

	// 任务数
	private Integer taskQty;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	// 任务接收数量
	private String taskReceiveNum;

	// 任务创建数量
	private String taskCreateNum;

	public String getZjLzehManageTaskPlanId() {
		return zjLzehManageTaskPlanId == null ? "" : zjLzehManageTaskPlanId.trim();
	}

	public void setZjLzehManageTaskPlanId(String zjLzehManageTaskPlanId) {
		this.zjLzehManageTaskPlanId = zjLzehManageTaskPlanId == null ? null : zjLzehManageTaskPlanId.trim();
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Integer getTaskQty() {
		return taskQty;
	}

	public void setTaskQty(Integer taskQty) {
		this.taskQty = taskQty;
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

	public String getTaskReceiveNum() {
		return taskReceiveNum;
	}

	public void setTaskReceiveNum(String taskReceiveNum) {
		this.taskReceiveNum = taskReceiveNum;
	}

	public String getTaskCreateNum() {
		return taskCreateNum;
	}

	public void setTaskCreateNum(String taskCreateNum) {
		this.taskCreateNum = taskCreateNum;
	}

}
