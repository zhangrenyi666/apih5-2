package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzThreeSupervisorBill extends BasePojo {
    private String threeSupervisorBillId;

    private String threeSupervisorId;

    private String billName;

    private String resultId;

    private String resultName;

    private String otherRequire;

    private String specificDesc;

    private String completeId;

    private String completeName;

    private String remarks;

    private String sortNumber;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String workId;
    
    private String groupByFlag;
    private String companyName;
    private String projectName;
    private BigDecimal amount1;
    private String company3;
    private String company2;
    private String company1;
    private BigDecimal company7;
    
    private String meetNumberNameSha;
    private String meetVoteNameSha;
    private String meetDateSha;
    private String meetPlaceSha;
    private String resultNameSha;
    private String billNameSha;
    private String originalNameSha;
    private String otherRequireSha;
    private String specificDescSha;
    private String remarksSha;
    //
    private String meetNumberNameDir;
    private String meetVoteNameDir;
    private String meetDateDir;
    private String meetPlaceDir;
    private String resultNameDir;
    private String billNameDir;
    private String originalNameDir;
    private String otherRequireDir;
    private String specificDescDir;
    private String remarksDir;
    //
    private String meetNumberNameSup;
    private String meetVoteNameSup;
    private String meetDateSup;
    private String meetPlaceSup;
    private String resultNameSup;
    private String billNameSup;
    private String originalNameSup;
    private String otherRequireSup;
    private String specificDescSup;
    private String remarksSup;
    //
    private String projectIdSql;
    private String projectId;

    private String sort1;
    
    private String proNo;
    
    public String getThreeSupervisorBillId() {
        return threeSupervisorBillId == null ? "" : threeSupervisorBillId.trim();
    }

    public void setThreeSupervisorBillId(String threeSupervisorBillId) {
        this.threeSupervisorBillId = threeSupervisorBillId == null ? null : threeSupervisorBillId.trim();
    }

    public String getThreeSupervisorId() {
        return threeSupervisorId == null ? "" : threeSupervisorId.trim();
    }

    public void setThreeSupervisorId(String threeSupervisorId) {
        this.threeSupervisorId = threeSupervisorId == null ? null : threeSupervisorId.trim();
    }

    public String getBillName() {
        return billName == null ? "" : billName.trim();
    }

    public void setBillName(String billName) {
        this.billName = billName == null ? null : billName.trim();
    }

    public String getResultId() {
        return resultId == null ? "" : resultId.trim();
    }

    public void setResultId(String resultId) {
        this.resultId = resultId == null ? null : resultId.trim();
    }

    public String getResultName() {
        return resultName == null ? "" : resultName.trim();
    }

    public void setResultName(String resultName) {
        this.resultName = resultName == null ? null : resultName.trim();
    }

    public String getOtherRequire() {
        return otherRequire == null ? "" : otherRequire.trim();
    }

    public void setOtherRequire(String otherRequire) {
        this.otherRequire = otherRequire == null ? null : otherRequire.trim();
    }

    public String getSpecificDesc() {
        return specificDesc == null ? "" : specificDesc.trim();
    }

    public void setSpecificDesc(String specificDesc) {
        this.specificDesc = specificDesc == null ? null : specificDesc.trim();
    }

    public String getCompleteId() {
        return completeId == null ? "" : completeId.trim();
    }

    public void setCompleteId(String completeId) {
        this.completeId = completeId == null ? null : completeId.trim();
    }

    public String getCompleteName() {
        return completeName == null ? "" : completeName.trim();
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName == null ? null : completeName.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getSortNumber() {
        return sortNumber == null ? "" : sortNumber.trim();
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber == null ? null : sortNumber.trim();
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
    
    public String getWorkId() {
		return workId == null ? "" : workId.trim();
	}

	public void setWorkId(String workId) {
		this.workId = workId == null ? null : workId.trim();
	}

	public String getGroupByFlag() {
		return groupByFlag;
	}

	public void setGroupByFlag(String groupByFlag) {
		this.groupByFlag = groupByFlag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public String getCompany3() {
		return company3;
	}

	public void setCompany3(String company3) {
		this.company3 = company3;
	}

	public String getCompany2() {
		return company2;
	}

	public void setCompany2(String company2) {
		this.company2 = company2;
	}

	public String getCompany1() {
		return company1;
	}

	public void setCompany1(String company1) {
		this.company1 = company1;
	}

	public BigDecimal getCompany7() {
		return company7;
	}

	public void setCompany7(BigDecimal company7) {
		this.company7 = company7;
	}

	public String getMeetNumberNameSha() {
		return meetNumberNameSha;
	}

	public void setMeetNumberNameSha(String meetNumberNameSha) {
		this.meetNumberNameSha = meetNumberNameSha;
	}

	public String getMeetDateSha() {
		return meetDateSha;
	}

	public void setMeetDateSha(String meetDateSha) {
		this.meetDateSha = meetDateSha;
	}

	public String getMeetPlaceSha() {
		return meetPlaceSha;
	}

	public void setMeetPlaceSha(String meetPlaceSha) {
		this.meetPlaceSha = meetPlaceSha;
	}

	public String getResultNameSha() {
		return resultNameSha;
	}

	public void setResultNameSha(String resultNameSha) {
		this.resultNameSha = resultNameSha;
	}

	public String getBillNameSha() {
		return billNameSha;
	}

	public void setBillNameSha(String billNameSha) {
		this.billNameSha = billNameSha;
	}

	public String getMeetNumberNameDir() {
		return meetNumberNameDir;
	}

	public void setMeetNumberNameDir(String meetNumberNameDir) {
		this.meetNumberNameDir = meetNumberNameDir;
	}

	public String getMeetDateDir() {
		return meetDateDir;
	}

	public void setMeetDateDir(String meetDateDir) {
		this.meetDateDir = meetDateDir;
	}

	public String getMeetPlaceDir() {
		return meetPlaceDir;
	}

	public void setMeetPlaceDir(String meetPlaceDir) {
		this.meetPlaceDir = meetPlaceDir;
	}

	public String getResultNameDir() {
		return resultNameDir;
	}

	public void setResultNameDir(String resultNameDir) {
		this.resultNameDir = resultNameDir;
	}

	public String getBillNameDir() {
		return billNameDir;
	}

	public void setBillNameDir(String billNameDir) {
		this.billNameDir = billNameDir;
	}

	public String getMeetNumberNameSup() {
		return meetNumberNameSup;
	}

	public void setMeetNumberNameSup(String meetNumberNameSup) {
		this.meetNumberNameSup = meetNumberNameSup;
	}

	public String getMeetDateSup() {
		return meetDateSup;
	}

	public void setMeetDateSup(String meetDateSup) {
		this.meetDateSup = meetDateSup;
	}

	public String getMeetPlaceSup() {
		return meetPlaceSup;
	}

	public void setMeetPlaceSup(String meetPlaceSup) {
		this.meetPlaceSup = meetPlaceSup;
	}

	public String getResultNameSup() {
		return resultNameSup;
	}

	public void setResultNameSup(String resultNameSup) {
		this.resultNameSup = resultNameSup;
	}

	public String getBillNameSup() {
		return billNameSup;
	}

	public void setBillNameSup(String billNameSup) {
		this.billNameSup = billNameSup;
	}

	public String getProjectIdSql() {
		return projectIdSql;
	}

	public void setProjectIdSql(String projectIdSql) {
		this.projectIdSql = projectIdSql;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMeetVoteNameSha() {
		return meetVoteNameSha;
	}

	public void setMeetVoteNameSha(String meetVoteNameSha) {
		this.meetVoteNameSha = meetVoteNameSha;
	}

	public String getOriginalNameSha() {
		return originalNameSha;
	}

	public void setOriginalNameSha(String originalNameSha) {
		this.originalNameSha = originalNameSha;
	}

	public String getOtherRequireSha() {
		return otherRequireSha;
	}

	public void setOtherRequireSha(String otherRequireSha) {
		this.otherRequireSha = otherRequireSha;
	}

	public String getSpecificDescSha() {
		return specificDescSha;
	}

	public void setSpecificDescSha(String specificDescSha) {
		this.specificDescSha = specificDescSha;
	}

	public String getRemarksSha() {
		return remarksSha;
	}

	public void setRemarksSha(String remarksSha) {
		this.remarksSha = remarksSha;
	}

	public String getMeetVoteNameDir() {
		return meetVoteNameDir;
	}

	public void setMeetVoteNameDir(String meetVoteNameDir) {
		this.meetVoteNameDir = meetVoteNameDir;
	}

	public String getOriginalNameDir() {
		return originalNameDir;
	}

	public void setOriginalNameDir(String originalNameDir) {
		this.originalNameDir = originalNameDir;
	}

	public String getOtherRequireDir() {
		return otherRequireDir;
	}

	public void setOtherRequireDir(String otherRequireDir) {
		this.otherRequireDir = otherRequireDir;
	}

	public String getSpecificDescDir() {
		return specificDescDir;
	}

	public void setSpecificDescDir(String specificDescDir) {
		this.specificDescDir = specificDescDir;
	}

	public String getRemarksDir() {
		return remarksDir;
	}

	public void setRemarksDir(String remarksDir) {
		this.remarksDir = remarksDir;
	}

	public String getMeetVoteNameSup() {
		return meetVoteNameSup;
	}

	public void setMeetVoteNameSup(String meetVoteNameSup) {
		this.meetVoteNameSup = meetVoteNameSup;
	}

	public String getOriginalNameSup() {
		return originalNameSup;
	}

	public void setOriginalNameSup(String originalNameSup) {
		this.originalNameSup = originalNameSup;
	}

	public String getOtherRequireSup() {
		return otherRequireSup;
	}

	public void setOtherRequireSup(String otherRequireSup) {
		this.otherRequireSup = otherRequireSup;
	}

	public String getSpecificDescSup() {
		return specificDescSup;
	}

	public void setSpecificDescSup(String specificDescSup) {
		this.specificDescSup = specificDescSup;
	}

	public String getRemarksSup() {
		return remarksSup;
	}

	public void setRemarksSup(String remarksSup) {
		this.remarksSup = remarksSup;
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