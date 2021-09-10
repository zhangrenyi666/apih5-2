package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojoFlow;

public class ZjTzLifeCycleChange extends BasePojoFlow {
    private String lifeCycleChangeId;

    private String projectId;

    private String projectName;

    private String remark;

    private Date registerDate;

    private String registerPerson;

    private String statusId;

    private String statusName;

    private String workId;

    private String apih5FlowStatus;

    private String opinionField1;

    private String opinionField2;

    private String opinionField3;

    private String opinionField4;

    private String opinionField5;

    private String opinionField6;

    private String opinionField7;

    private String opinionField8;

    private String opinionField9;

    private String opinionField10;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzFile> zjTzFileOpinionList;
    
    private String projectIdSql;
    
    private List<String> apih5ToObjectList;
    
    private String flowId;
    
    private String opinionDeptName;
    
    private String remindFlag;
    
    private Date remindTime;
    
    public String getLifeCycleChangeId() {
        return lifeCycleChangeId == null ? "" : lifeCycleChangeId.trim();
    }

    public void setLifeCycleChangeId(String lifeCycleChangeId) {
        this.lifeCycleChangeId = lifeCycleChangeId == null ? null : lifeCycleChangeId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getStatusId() {
        return statusId == null ? "" : statusId.trim();
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName.trim();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getApih5FlowStatus() {
        return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
    }

    public void setApih5FlowStatus(String apih5FlowStatus) {
        this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
    }

    public String getOpinionField1() {
        return opinionField1 == null ? "" : opinionField1.trim();
    }

    public void setOpinionField1(String opinionField1) {
        this.opinionField1 = opinionField1 == null ? null : opinionField1.trim();
    }

    public String getOpinionField2() {
        return opinionField2 == null ? "" : opinionField2.trim();
    }

    public void setOpinionField2(String opinionField2) {
        this.opinionField2 = opinionField2 == null ? null : opinionField2.trim();
    }

    public String getOpinionField3() {
        return opinionField3 == null ? "" : opinionField3.trim();
    }

    public void setOpinionField3(String opinionField3) {
        this.opinionField3 = opinionField3 == null ? null : opinionField3.trim();
    }

    public String getOpinionField4() {
        return opinionField4 == null ? "" : opinionField4.trim();
    }

    public void setOpinionField4(String opinionField4) {
        this.opinionField4 = opinionField4 == null ? null : opinionField4.trim();
    }

    public String getOpinionField5() {
        return opinionField5 == null ? "" : opinionField5.trim();
    }

    public void setOpinionField5(String opinionField5) {
        this.opinionField5 = opinionField5 == null ? null : opinionField5.trim();
    }

    public String getOpinionField6() {
        return opinionField6 == null ? "" : opinionField6.trim();
    }

    public void setOpinionField6(String opinionField6) {
        this.opinionField6 = opinionField6 == null ? null : opinionField6.trim();
    }

    public String getOpinionField7() {
        return opinionField7 == null ? "" : opinionField7.trim();
    }

    public void setOpinionField7(String opinionField7) {
        this.opinionField7 = opinionField7 == null ? null : opinionField7.trim();
    }

    public String getOpinionField8() {
        return opinionField8 == null ? "" : opinionField8.trim();
    }

    public void setOpinionField8(String opinionField8) {
        this.opinionField8 = opinionField8 == null ? null : opinionField8.trim();
    }

    public String getOpinionField9() {
        return opinionField9 == null ? "" : opinionField9.trim();
    }

    public void setOpinionField9(String opinionField9) {
        this.opinionField9 = opinionField9 == null ? null : opinionField9.trim();
    }

    public String getOpinionField10() {
        return opinionField10 == null ? "" : opinionField10.trim();
    }

    public void setOpinionField10(String opinionField10) {
        this.opinionField10 = opinionField10 == null ? null : opinionField10.trim();
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

    public List<ZjTzFile> getZjTzFileOpinionList() {
        return zjTzFileOpinionList;
    }

    public void setZjTzFileOpinionList(List<ZjTzFile> zjTzFileOpinionList) {
        this.zjTzFileOpinionList = zjTzFileOpinionList;
    }
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public List<String> getApih5ToObjectList() {
		return apih5ToObjectList;
	}

	public void setApih5ToObjectList(List<String> apih5ToObjectList) {
		this.apih5ToObjectList = apih5ToObjectList;
	}

	public String getFlowId() {
		return flowId == null ? "" : flowId.trim();
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId == null ? null : flowId.trim();
	}

	public String getOpinionDeptName() {
		return opinionDeptName == null ? "" : opinionDeptName.trim();
	}

	public void setOpinionDeptName(String opinionDeptName) {
		this.opinionDeptName = opinionDeptName == null ? null : opinionDeptName.trim();
	}

	public String getRemindFlag() {
		return remindFlag == null ? "" : remindFlag.trim();
	}

	public void setRemindFlag(String remindFlag) {
		this.remindFlag = remindFlag == null ? null : remindFlag.trim();
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
	
	
    
}