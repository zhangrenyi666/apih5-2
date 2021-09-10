package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzRunQuarterly extends BasePojo {
    private String runQuarterlyId;

    private Date yearDate;

    private String yearStr;

    private String quarter;

    private String runDesc;

    private Date registerDate;

    private String registerPerson;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String sequenceFlag;
    private String quarterFlag;
    
    public String getRunQuarterlyId() {
        return runQuarterlyId == null ? "" : runQuarterlyId.trim();
    }

    public void setRunQuarterlyId(String runQuarterlyId) {
        this.runQuarterlyId = runQuarterlyId == null ? null : runQuarterlyId.trim();
    }

    public Date getYearDate() {
        return yearDate;
    }

    public void setYearDate(Date yearDate) {
        this.yearDate = yearDate;
    }

    public String getYearStr() {
        return yearStr == null ? "" : yearStr.trim();
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr == null ? null : yearStr.trim();
    }

    public String getQuarter() {
        return quarter == null ? "" : quarter.trim();
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    public String getRunDesc() {
        return runDesc == null ? "" : runDesc.trim();
    }

    public void setRunDesc(String runDesc) {
        this.runDesc = runDesc == null ? null : runDesc.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterPerson() {
        return registerPerson == null ? "" : registerPerson.trim();
    }

    public void setRegisterPerson(String registerPerson) {
        this.registerPerson = registerPerson == null ? null : registerPerson.trim();
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

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

	public String getSequenceFlag() {
		return sequenceFlag;
	}

	public void setSequenceFlag(String sequenceFlag) {
		this.sequenceFlag = sequenceFlag;
	}

	public String getQuarterFlag() {
		return quarterFlag;
	}

	public void setQuarterFlag(String quarterFlag) {
		this.quarterFlag = quarterFlag;
	}
	
	

}