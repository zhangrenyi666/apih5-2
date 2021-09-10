package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzLifeCycleChangeOpinion extends BasePojo {
    private String lifeCycleChangeOpinionId;

    private String lifeCycleChangeId;

    private Integer reviewNumber;

    private String reviewFlag;

    private String reviewerKey;

    private String reviewer;

    private String reviewerDept;

    private String reviewerDeptName;

    private String opinion;

    private Date reviewStartTime;

    private Date reviewEndTime;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileOpinionList;

    private String groupByFlagReviewNumber;

    private String count;

    private String lifeCycleId;

    public String getLifeCycleChangeOpinionId() {
        return lifeCycleChangeOpinionId == null ? "" : lifeCycleChangeOpinionId.trim();
    }

    public void setLifeCycleChangeOpinionId(String lifeCycleChangeOpinionId) {
        this.lifeCycleChangeOpinionId = lifeCycleChangeOpinionId == null ? null : lifeCycleChangeOpinionId.trim();
    }

    public String getLifeCycleChangeId() {
        return lifeCycleChangeId == null ? "" : lifeCycleChangeId.trim();
    }

    public void setLifeCycleChangeId(String lifeCycleChangeId) {
        this.lifeCycleChangeId = lifeCycleChangeId == null ? null : lifeCycleChangeId.trim();
    }

    public Integer getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(Integer reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    public String getReviewFlag() {
        return reviewFlag == null ? "" : reviewFlag.trim();
    }

    public void setReviewFlag(String reviewFlag) {
        this.reviewFlag = reviewFlag == null ? null : reviewFlag.trim();
    }

    public String getReviewerKey() {
        return reviewerKey == null ? "" : reviewerKey.trim();
    }

    public void setReviewerKey(String reviewerKey) {
        this.reviewerKey = reviewerKey == null ? null : reviewerKey.trim();
    }

    public String getReviewer() {
        return reviewer == null ? "" : reviewer.trim();
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer == null ? null : reviewer.trim();
    }

    public String getReviewerDept() {
        return reviewerDept == null ? "" : reviewerDept.trim();
    }

    public void setReviewerDept(String reviewerDept) {
        this.reviewerDept = reviewerDept == null ? null : reviewerDept.trim();
    }

    public String getReviewerDeptName() {
        return reviewerDeptName == null ? "" : reviewerDeptName.trim();
    }

    public void setReviewerDeptName(String reviewerDeptName) {
        this.reviewerDeptName = reviewerDeptName == null ? null : reviewerDeptName.trim();
    }

    public String getOpinion() {
        return opinion == null ? "" : opinion.trim();
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public Date getReviewStartTime() {
        return reviewStartTime;
    }

    public void setReviewStartTime(Date reviewStartTime) {
        this.reviewStartTime = reviewStartTime;
    }

    public Date getReviewEndTime() {
        return reviewEndTime;
    }

    public void setReviewEndTime(Date reviewEndTime) {
        this.reviewEndTime = reviewEndTime;
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

    public List<ZjTzFile> getZjTzFileOpinionList() {
        return zjTzFileOpinionList;
    }

    public void setZjTzFileOpinionList(List<ZjTzFile> zjTzFileOpinionList) {
        this.zjTzFileOpinionList = zjTzFileOpinionList;
    }

    public String getGroupByFlagReviewNumber() {
        return groupByFlagReviewNumber == null ? "" : groupByFlagReviewNumber.trim();
    }

    public void setGroupByFlagReviewNumber(String groupByFlagReviewNumber) {
        this.groupByFlagReviewNumber = groupByFlagReviewNumber == null ? null : groupByFlagReviewNumber.trim();
    }

    public String getCount() {
        return count == null ? "" : count.trim();
    }

    public void setCount(String count) {
        this.count = count == null ? null : count.trim();
    }

    public String getLifeCycleId() {
        return lifeCycleId == null ? "" : lifeCycleId.trim();
    }

    public void setLifeCycleId(String lifeCycleId) {
        this.lifeCycleId = lifeCycleId == null ? null : lifeCycleId.trim();
    }

}

