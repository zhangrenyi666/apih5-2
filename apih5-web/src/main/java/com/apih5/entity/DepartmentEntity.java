package com.apih5.entity;

/**
 * 用户entity用于外部展示
 */
public class DepartmentEntity {

    private String departmentId;

	private String departmentName;
	
    public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
