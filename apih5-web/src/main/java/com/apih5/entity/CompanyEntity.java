package com.apih5.entity;

/**
 * 用户entity用于外部展示
 */
public class CompanyEntity {
	
	private String userKey;

	private String companyId;

    private String companyName;

    private int companySelectFlag = 0;

	 
    public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanySelectFlag() {
		return companySelectFlag;
	}

	public void setCompanySelectFlag(int companySelectFlag) {
		this.companySelectFlag = companySelectFlag;
	}

}
