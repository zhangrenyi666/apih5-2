package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzThousandCheck extends BasePojo {
    private String thousandCheckId;

    private String projectId;

    private String projectName;

    private Date checkDate;

    private String checkPerson;

    private BigDecimal proScore;

    private String voteDownId;

    private String voteDownName;

    private String scoreTotal;

    private Date registerDate;

    private String registerPerson;

    private String releaseId;

    private String releaseName;

    private BigDecimal addMarks;

    private String addMarksReason;

    private BigDecimal reduceMarks;

    private String reduceMarksReason;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzThousandDeduct> zjTzThousandDeductList;
    
    private String projectIdSql;
    
    private String unExt1SeeFlag;
    
    private BigDecimal detailMarks;

    public String getThousandCheckId() {
        return thousandCheckId == null ? "" : thousandCheckId.trim();
    }

    public void setThousandCheckId(String thousandCheckId) {
        this.thousandCheckId = thousandCheckId == null ? null : thousandCheckId.trim();
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

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckPerson() {
        return checkPerson == null ? "" : checkPerson.trim();
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson == null ? null : checkPerson.trim();
    }

    public BigDecimal getProScore() {
        return proScore;
    }

    public void setProScore(BigDecimal proScore) {
        this.proScore = proScore;
    }

    public String getVoteDownId() {
        return voteDownId == null ? "" : voteDownId.trim();
    }

    public void setVoteDownId(String voteDownId) {
        this.voteDownId = voteDownId == null ? null : voteDownId.trim();
    }

    public String getVoteDownName() {
        return voteDownName == null ? "" : voteDownName.trim();
    }

    public void setVoteDownName(String voteDownName) {
        this.voteDownName = voteDownName == null ? null : voteDownName.trim();
    }

    public String getScoreTotal() {
        return scoreTotal == null ? "" : scoreTotal.trim();
    }

    public void setScoreTotal(String scoreTotal) {
        this.scoreTotal = scoreTotal == null ? null : scoreTotal.trim();
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

    public String getReleaseId() {
        return releaseId == null ? "" : releaseId.trim();
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId == null ? null : releaseId.trim();
    }

    public String getReleaseName() {
        return releaseName == null ? "" : releaseName.trim();
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName == null ? null : releaseName.trim();
    }

    public BigDecimal getAddMarks() {
        return addMarks;
    }

    public void setAddMarks(BigDecimal addMarks) {
        this.addMarks = addMarks;
    }

    public String getAddMarksReason() {
        return addMarksReason == null ? "" : addMarksReason.trim();
    }

    public void setAddMarksReason(String addMarksReason) {
        this.addMarksReason = addMarksReason == null ? null : addMarksReason.trim();
    }

    public BigDecimal getReduceMarks() {
        return reduceMarks;
    }

    public void setReduceMarks(BigDecimal reduceMarks) {
        this.reduceMarks = reduceMarks;
    }

    public String getReduceMarksReason() {
        return reduceMarksReason == null ? "" : reduceMarksReason.trim();
    }

    public void setReduceMarksReason(String reduceMarksReason) {
        this.reduceMarksReason = reduceMarksReason == null ? null : reduceMarksReason.trim();
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

    public List<ZjTzThousandDeduct> getZjTzThousandDeductList() {
        return zjTzThousandDeductList;
    }

    public void setZjTzThousandDeductList(List<ZjTzThousandDeduct> zjTzThousandDeductList) {
        this.zjTzThousandDeductList = zjTzThousandDeductList;
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

	public BigDecimal getDetailMarks() {
		return detailMarks;
	}

	public void setDetailMarks(BigDecimal detailMarks) {
		this.detailMarks = detailMarks;
	}

}

