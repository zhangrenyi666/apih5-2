package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import cn.hutool.json.JSONObject;
import com.apih5.framework.entity.BasePojo;

public class ZjTzComprehensiveSup extends BasePojo {
    private String comprehensiveSupId;

    private String projectId;

    private String projectName;

    private String title;

    private Date checkDate;

    private String trusteeCompanyId;

    private String trusteeCompanyName;

    private String inspectDeptAndPerson;

    private Date registerDate;

    private String registerPerson;

    private String correctiveId;

    private String correctiveName;

    private String contentDesc;

    private String correctiveUserKey;

    private String correctiveUserName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzSupContent> zjTzSupContentList;

    private List<JSONObject> personList;
    
    private List<ZjTzFile> replyFileList;
    
    private String projectIdSql;
    
    private String unExt1SeeFlag;

    public String getComprehensiveSupId() {
        return comprehensiveSupId == null ? "" : comprehensiveSupId.trim();
    }

    public void setComprehensiveSupId(String comprehensiveSupId) {
        this.comprehensiveSupId = comprehensiveSupId == null ? null : comprehensiveSupId.trim();
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

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getTrusteeCompanyId() {
        return trusteeCompanyId == null ? "" : trusteeCompanyId.trim();
    }

    public void setTrusteeCompanyId(String trusteeCompanyId) {
        this.trusteeCompanyId = trusteeCompanyId == null ? null : trusteeCompanyId.trim();
    }

    public String getTrusteeCompanyName() {
        return trusteeCompanyName == null ? "" : trusteeCompanyName.trim();
    }

    public void setTrusteeCompanyName(String trusteeCompanyName) {
        this.trusteeCompanyName = trusteeCompanyName == null ? null : trusteeCompanyName.trim();
    }

    public String getInspectDeptAndPerson() {
        return inspectDeptAndPerson == null ? "" : inspectDeptAndPerson.trim();
    }

    public void setInspectDeptAndPerson(String inspectDeptAndPerson) {
        this.inspectDeptAndPerson = inspectDeptAndPerson == null ? null : inspectDeptAndPerson.trim();
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

    public String getCorrectiveId() {
        return correctiveId == null ? "" : correctiveId.trim();
    }

    public void setCorrectiveId(String correctiveId) {
        this.correctiveId = correctiveId == null ? null : correctiveId.trim();
    }

    public String getCorrectiveName() {
        return correctiveName == null ? "" : correctiveName.trim();
    }

    public void setCorrectiveName(String correctiveName) {
        this.correctiveName = correctiveName == null ? null : correctiveName.trim();
    }

    public String getContentDesc() {
        return contentDesc == null ? "" : contentDesc.trim();
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc == null ? null : contentDesc.trim();
    }

    public String getCorrectiveUserKey() {
        return correctiveUserKey == null ? "" : correctiveUserKey.trim();
    }

    public void setCorrectiveUserKey(String correctiveUserKey) {
        this.correctiveUserKey = correctiveUserKey == null ? null : correctiveUserKey.trim();
    }

    public String getCorrectiveUserName() {
        return correctiveUserName == null ? "" : correctiveUserName.trim();
    }

    public void setCorrectiveUserName(String correctiveUserName) {
        this.correctiveUserName = correctiveUserName == null ? null : correctiveUserName.trim();
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

    public List<ZjTzSupContent> getZjTzSupContentList() {
        return zjTzSupContentList;
    }

    public void setZjTzSupContentList(List<ZjTzSupContent> zjTzSupContentList) {
        this.zjTzSupContentList = zjTzSupContentList;
    }

    public List<JSONObject> getPersonList() {
        return personList;
    }

    public void setPersonList(List<JSONObject> personList) {
        this.personList = personList;
    }

	public List<ZjTzFile> getReplyFileList() {
		return replyFileList;
	}

	public void setReplyFileList(List<ZjTzFile> replyFileList) {
		this.replyFileList = replyFileList;
	}
	
	public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public String getUnExt1SeeFlag() {
		return unExt1SeeFlag == null ? "" : unExt1SeeFlag.trim();
	}

	public void setUnExt1SeeFlag(String unExt1SeeFlag) {
		this.unExt1SeeFlag = unExt1SeeFlag == null ? null : unExt1SeeFlag.trim();
	}

}

