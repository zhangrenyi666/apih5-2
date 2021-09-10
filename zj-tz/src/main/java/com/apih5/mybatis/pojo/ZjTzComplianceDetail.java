package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzComplianceDetail extends BasePojo {
    private String complianceDetailId;

    private String complianceDealId;

    private String num;

    private String complianceBanseName;

    private String dealFlag;

    private Date shouldFinishDate;

    private Date approvalDate;

    private String dealSituation;

    private String lockFlag;

    private String addFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String colourFlag;

    private String groupByFlagTypeId;

    private String number;

    private List<ZjTzComplianceDetail> children;

    private String mainFlag;

    private List<ZjTzComplianceDetail> zjTzComplianceDetailList;
    
    
    private String e1;
    
    private String e2;
    
    private String e3;
    
    private String e4;
    
    private String e5;
    
    private String e6;
    
    private String e7;
    
    private String e8;
    
    private String companyName;
    
    private String proTypeName;
    
    private String company2;
    
    private String projectName;
    
    private String subprojectName;

    private String projectId;
    
    private String projectIdSql;
    
    private String userId;
    
    private String ext1;
    
    private String sort1;
    
    private String proNo1;
    //策划批复应办理完结日期 
    private Date approvalShouldFinishDate;
    //备注
    private String bz;
    //策划时限预警
    private String colourFlag1;
    //项目简称
    private String projectShortName;
    
    public String getComplianceDetailId() {
        return complianceDetailId == null ? "" : complianceDetailId.trim();
    }

    public void setComplianceDetailId(String complianceDetailId) {
        this.complianceDetailId = complianceDetailId == null ? null : complianceDetailId.trim();
    }

    public String getComplianceDealId() {
        return complianceDealId == null ? "" : complianceDealId.trim();
    }

    public void setComplianceDealId(String complianceDealId) {
        this.complianceDealId = complianceDealId == null ? null : complianceDealId.trim();
    }

    public String getNum() {
        return num == null ? "" : num.trim();
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getComplianceBanseName() {
        return complianceBanseName == null ? "" : complianceBanseName.trim();
    }

    public void setComplianceBanseName(String complianceBanseName) {
        this.complianceBanseName = complianceBanseName == null ? null : complianceBanseName.trim();
    }

    public String getDealFlag() {
        return dealFlag == null ? "" : dealFlag.trim();
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag == null ? null : dealFlag.trim();
    }

    public Date getShouldFinishDate() {
        return shouldFinishDate;
    }

    public void setShouldFinishDate(Date shouldFinishDate) {
        this.shouldFinishDate = shouldFinishDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getDealSituation() {
        return dealSituation == null ? "" : dealSituation.trim();
    }

    public void setDealSituation(String dealSituation) {
        this.dealSituation = dealSituation == null ? null : dealSituation.trim();
    }

    public String getLockFlag() {
        return lockFlag == null ? "" : lockFlag.trim();
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag == null ? null : lockFlag.trim();
    }

    public String getAddFlag() {
        return addFlag == null ? "" : addFlag.trim();
    }

    public void setAddFlag(String addFlag) {
        this.addFlag = addFlag == null ? null : addFlag.trim();
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

    public String getColourFlag() {
        return colourFlag == null ? "" : colourFlag.trim();
    }

    public void setColourFlag(String colourFlag) {
        this.colourFlag = colourFlag == null ? null : colourFlag.trim();
    }

    public String getGroupByFlagTypeId() {
        return groupByFlagTypeId == null ? "" : groupByFlagTypeId.trim();
    }

    public void setGroupByFlagTypeId(String groupByFlagTypeId) {
        this.groupByFlagTypeId = groupByFlagTypeId == null ? null : groupByFlagTypeId.trim();
    }

    public String getNumber() {
        return number == null ? "" : number.trim();
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public List<ZjTzComplianceDetail> getChildren() {
        return children;
    }

    public void setChildren(List<ZjTzComplianceDetail> children) {
        this.children = children;
    }

    public String getMainFlag() {
        return mainFlag == null ? "" : mainFlag.trim();
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag == null ? null : mainFlag.trim();
    }

    public List<ZjTzComplianceDetail> getZjTzComplianceDetailList() {
        return zjTzComplianceDetailList;
    }

    public void setZjTzComplianceDetailList(List<ZjTzComplianceDetail> zjTzComplianceDetailList) {
        this.zjTzComplianceDetailList = zjTzComplianceDetailList;
    }

	public String getE1() {
		return e1;
	}

	public void setE1(String e1) {
		this.e1 = e1;
	}

	public String getE2() {
		return e2;
	}

	public void setE2(String e2) {
		this.e2 = e2;
	}

	public String getE3() {
		return e3;
	}

	public void setE3(String e3) {
		this.e3 = e3;
	}

	public String getE4() {
		return e4;
	}

	public void setE4(String e4) {
		this.e4 = e4;
	}

	public String getE5() {
		return e5;
	}

	public void setE5(String e5) {
		this.e5 = e5;
	}

	public String getE6() {
		return e6;
	}

	public void setE6(String e6) {
		this.e6 = e6;
	}
	
	public String getE7() {
		return e7;
	}

	public void setE7(String e7) {
		this.e7 = e7;
	}

	public String getE8() {
		return e8;
	}

	public void setE8(String e8) {
		this.e8 = e8;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProTypeName() {
		return proTypeName;
	}

	public void setProTypeName(String proTypeName) {
		this.proTypeName = proTypeName;
	}

	public String getCompany2() {
		return company2;
	}

	public void setCompany2(String company2) {
		this.company2 = company2;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSubprojectName() {
		return subprojectName;
	}

	public void setSubprojectName(String subprojectName) {
		this.subprojectName = subprojectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectIdSql() {
		return projectIdSql;
	}

	public void setProjectIdSql(String projectIdSql) {
		this.projectIdSql = projectIdSql;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getSort1() {
		return sort1;
	}

	public void setSort1(String sort1) {
		this.sort1 = sort1;
	}

	public String getProNo1() {
		return proNo1;
	}

	public void setProNo1(String proNo1) {
		this.proNo1 = proNo1;
	}

	public Date getApprovalShouldFinishDate() {
		return approvalShouldFinishDate;
	}

	public void setApprovalShouldFinishDate(Date approvalShouldFinishDate) {
		this.approvalShouldFinishDate = approvalShouldFinishDate;
	}

	public String getBz() {
		return bz == null ? "" : bz.trim();
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public String getColourFlag1() {
		return colourFlag1 == null ? "" : colourFlag1.trim();
	}

	public void setColourFlag1(String colourFlag1) {
		this.colourFlag1 = colourFlag1 == null ? null : colourFlag1.trim();
	}

	public String getProjectShortName() {
		return projectShortName == null ? "" : projectShortName.trim();
	}

	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName == null ? null : projectShortName.trim();
	}
	
}