package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzExecutivePersonnel extends BasePojo {
    private String executivePersonnelId;

    private String executiveMeetId;

    private String shareholderName;

    private String proportion;

    private String directorz;

    private String directorzOld;

    private String director;

    private String directorOld;

    private String supervisorz;

    private String supervisorzOld;

    private String supervisor;

    private String supervisorOld;

    private String manager;

    private String managerOld;

    private String remarks;

    private Integer orderNum;

    private String chief1;

    private String chief2;

    private String chief3;

    private String shareTypeId;

    private String shareType;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String companyName;

    private String projectName;

    private String company1;

    private BigDecimal company4;

    private String company3;

    private String projectId;

    private String projectIdSql;

    private String groupByFlag;

    private String num;

    private String userId;

    private String ext1;
    
    private String legal;
    
    private String sort1;
    
    private String proNo;

    public String getExecutivePersonnelId() {
        return executivePersonnelId == null ? "" : executivePersonnelId.trim();
    }

    public void setExecutivePersonnelId(String executivePersonnelId) {
        this.executivePersonnelId = executivePersonnelId == null ? null : executivePersonnelId.trim();
    }

    public String getExecutiveMeetId() {
        return executiveMeetId == null ? "" : executiveMeetId.trim();
    }

    public void setExecutiveMeetId(String executiveMeetId) {
        this.executiveMeetId = executiveMeetId == null ? null : executiveMeetId.trim();
    }

    public String getShareholderName() {
        return shareholderName == null ? "" : shareholderName.trim();
    }

    public void setShareholderName(String shareholderName) {
        this.shareholderName = shareholderName == null ? null : shareholderName.trim();
    }

    public String getProportion() {
        return proportion == null ? "" : proportion.trim();
    }

    public void setProportion(String proportion) {
        this.proportion = proportion == null ? null : proportion.trim();
    }

    public String getDirectorz() {
        return directorz == null ? "" : directorz.trim();
    }

    public void setDirectorz(String directorz) {
        this.directorz = directorz == null ? null : directorz.trim();
    }

    public String getDirectorzOld() {
        return directorzOld == null ? "" : directorzOld.trim();
    }

    public void setDirectorzOld(String directorzOld) {
        this.directorzOld = directorzOld == null ? null : directorzOld.trim();
    }

    public String getDirector() {
        return director == null ? "" : director.trim();
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getDirectorOld() {
        return directorOld == null ? "" : directorOld.trim();
    }

    public void setDirectorOld(String directorOld) {
        this.directorOld = directorOld == null ? null : directorOld.trim();
    }

    public String getSupervisorz() {
        return supervisorz == null ? "" : supervisorz.trim();
    }

    public void setSupervisorz(String supervisorz) {
        this.supervisorz = supervisorz == null ? null : supervisorz.trim();
    }

    public String getSupervisorzOld() {
        return supervisorzOld == null ? "" : supervisorzOld.trim();
    }

    public void setSupervisorzOld(String supervisorzOld) {
        this.supervisorzOld = supervisorzOld == null ? null : supervisorzOld.trim();
    }

    public String getSupervisor() {
        return supervisor == null ? "" : supervisor.trim();
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor == null ? null : supervisor.trim();
    }

    public String getSupervisorOld() {
        return supervisorOld == null ? "" : supervisorOld.trim();
    }

    public void setSupervisorOld(String supervisorOld) {
        this.supervisorOld = supervisorOld == null ? null : supervisorOld.trim();
    }

    public String getManager() {
        return manager == null ? "" : manager.trim();
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getManagerOld() {
        return managerOld == null ? "" : managerOld.trim();
    }

    public void setManagerOld(String managerOld) {
        this.managerOld = managerOld == null ? null : managerOld.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getChief1() {
        return chief1 == null ? "" : chief1.trim();
    }

    public void setChief1(String chief1) {
        this.chief1 = chief1 == null ? null : chief1.trim();
    }

    public String getChief2() {
        return chief2 == null ? "" : chief2.trim();
    }

    public void setChief2(String chief2) {
        this.chief2 = chief2 == null ? null : chief2.trim();
    }

    public String getChief3() {
        return chief3 == null ? "" : chief3.trim();
    }

    public void setChief3(String chief3) {
        this.chief3 = chief3 == null ? null : chief3.trim();
    }

    public String getShareTypeId() {
        return shareTypeId == null ? "" : shareTypeId.trim();
    }

    public void setShareTypeId(String shareTypeId) {
        this.shareTypeId = shareTypeId == null ? null : shareTypeId.trim();
    }

    public String getShareType() {
        return shareType == null ? "" : shareType.trim();
    }

    public void setShareType(String shareType) {
        this.shareType = shareType == null ? null : shareType.trim();
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

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getCompany1() {
        return company1 == null ? "" : company1.trim();
    }

    public void setCompany1(String company1) {
        this.company1 = company1 == null ? null : company1.trim();
    }

    public BigDecimal getCompany4() {
		return company4;
	}

	public void setCompany4(BigDecimal company4) {
		this.company4 = company4;
	}

	public String getCompany3() {
        return company3 == null ? "" : company3.trim();
    }

    public void setCompany3(String company3) {
        this.company3 = company3 == null ? null : company3.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

    public String getGroupByFlag() {
        return groupByFlag == null ? "" : groupByFlag.trim();
    }

    public void setGroupByFlag(String groupByFlag) {
        this.groupByFlag = groupByFlag == null ? null : groupByFlag.trim();
    }

    public String getNum() {
        return num == null ? "" : num.trim();
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getUserId() {
        return userId == null ? "" : userId.trim();
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getExt1() {
        return ext1 == null ? "" : ext1.trim();
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getSort1() {
		return sort1;
	}

	public void setSort1(String sort1) {
		this.sort1 = sort1;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
    
	

}

