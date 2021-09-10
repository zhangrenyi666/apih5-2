package com.apih5.entity;

import java.util.List;

import com.apih5.framework.entity.TreeNodeEntity;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

/**
 * 用户entity用于外部展示
 */
public class SysUserWebEntity {

	private String userKey;

	private String userId;

	private String realName;

	private String mobile;

	private String imageUrl;

	private List<CompanyEntity> companyList;

	private List<DepartmentEntity> departmentList;

	private String gender;

	private String ext1;
	
	private String ext2;

    private String jobType;

	private JSONObject menuTree;
	
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getImageUrl() {
		return imageUrl == null ? "" : imageUrl.trim();
	}

	public void setImageUrl(String imageUrl) {
		 this.imageUrl = imageUrl == null ? "" : imageUrl.trim();
	}

	public List<CompanyEntity> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyEntity> companyList) {
		this.companyList = companyList;
	}
	
	public List<DepartmentEntity> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentEntity> departmentList) {
		this.departmentList = departmentList;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public JSONObject getMenuTree() {
		return menuTree == null ? new JSONObject() : menuTree;
	}

	public void setMenuTree(JSONObject menuTree) {
		this.menuTree = menuTree == null ? new JSONObject() : menuTree;
	}
}
