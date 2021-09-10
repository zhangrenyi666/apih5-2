package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectDisciplineAssessment extends BasePojo {
    private String disciplineId;

    private String managerId;
    
    private String assistantLeaderApprovalId;
    
    private String assessmentType;

    private String disciplineState;

    private String disciplineTitle;
    
    private String deptHeadId;
    
    private String deptHeadOption;
    
    private String executiveLeaderId;
    
    private String executiveLeaderOption;

    private Date disciplineYears;

    private String disciplineQuarter;

    private String disciplineRemarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String ApprovalFlag;
    
    private String leaderOption;
    
    private String assessmentDept;
    
    private String disciplineDeptId;

	public String getDisciplineDeptId() {
		return disciplineDeptId;
	}

	public void setDisciplineDeptId(String disciplineDeptId) {
		this.disciplineDeptId = disciplineDeptId;
	}

	public String getAssessmentDept() {
		return assessmentDept;
	}

	public void setAssessmentDept(String assessmentDept) {
		this.assessmentDept = assessmentDept;
	}

	public String getLeaderOption() {
		return leaderOption;
	}

	public void setLeaderOption(String leaderOption) {
		this.leaderOption = leaderOption;
	}

	private List<ZjXmCqjxProjectDisciplineAssessment> disciplineList;
    
    public List<ZjXmCqjxProjectDisciplineAssessment> getDisciplineList() {
		return disciplineList;
	}

	public void setDisciplineList(List<ZjXmCqjxProjectDisciplineAssessment> disciplineList) {
		this.disciplineList = disciplineList;
	}

	public String getAssistantLeaderApprovalId() {
		return assistantLeaderApprovalId;
	}

	public void setAssistantLeaderApprovalId(String assistantLeaderApprovalId) {
		this.assistantLeaderApprovalId = assistantLeaderApprovalId;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getApprovalFlag() {
		return ApprovalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		ApprovalFlag = approvalFlag;
	}

	public String getDisciplineId() {
        return disciplineId == null ? "" : disciplineId.trim();
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId == null ? null : disciplineId.trim();
    }

    public String getManagerId() {
        return managerId == null ? "" : managerId.trim();
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public String getDisciplineState() {
        return disciplineState == null ? "" : disciplineState.trim();
    }

    public void setDisciplineState(String disciplineState) {
        this.disciplineState = disciplineState == null ? null : disciplineState.trim();
    }

    public String getDisciplineTitle() {
        return disciplineTitle == null ? "" : disciplineTitle.trim();
    }

    public void setDisciplineTitle(String disciplineTitle) {
        this.disciplineTitle = disciplineTitle == null ? null : disciplineTitle.trim();
    }

    public Date getDisciplineYears() {
        return disciplineYears;
    }

    public void setDisciplineYears(Date disciplineYears) {
        this.disciplineYears = disciplineYears;
    }

    public String getDisciplineQuarter() {
        return disciplineQuarter == null ? "" : disciplineQuarter.trim();
    }

    public void setDisciplineQuarter(String disciplineQuarter) {
        this.disciplineQuarter = disciplineQuarter == null ? null : disciplineQuarter.trim();
    }

    public String getDeptHeadId() {
        return deptHeadId == null ? "" : deptHeadId.trim();
    }

    public void setDeptHeadId(String deptHeadId) {
        this.deptHeadId = deptHeadId == null ? null : deptHeadId.trim();
    }

    public String getDeptHeadOption() {
        return deptHeadOption == null ? "" : deptHeadOption.trim();
    }

    public void setDeptHeadOption(String deptHeadOption) {
        this.deptHeadOption = deptHeadOption == null ? null : deptHeadOption.trim();
    }

    public String getExecutiveLeaderId() {
        return executiveLeaderId == null ? "" : executiveLeaderId.trim();
    }

    public void setExecutiveLeaderId(String executiveLeaderId) {
        this.executiveLeaderId = executiveLeaderId == null ? null : executiveLeaderId.trim();
    }

    public String getExecutiveLeaderOption() {
        return executiveLeaderOption == null ? "" : executiveLeaderOption.trim();
    }

    public void setExecutiveLeaderOption(String executiveLeaderOption) {
        this.executiveLeaderOption = executiveLeaderOption == null ? null : executiveLeaderOption.trim();
    }

    public String getDisciplineRemarks() {
        return disciplineRemarks == null ? "" : disciplineRemarks.trim();
    }

    public void setDisciplineRemarks(String disciplineRemarks) {
        this.disciplineRemarks = disciplineRemarks == null ? null : disciplineRemarks.trim();
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

}

