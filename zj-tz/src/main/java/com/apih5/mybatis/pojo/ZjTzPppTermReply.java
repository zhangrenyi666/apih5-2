package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPppTermReply extends BasePojo {
    private String pppTermReplyId;

    private String pppTermId;

    private String pppTermBaseId;

    private String codePid;

    private String analysisKey;

    private String keyTerm;

    private String keyAnalysisContent;

    private String numberContent;

    private String term;

    private String riskFlag;

    private String measure;

    private String negotiateFlag;

    private String deptAndLeader;

    private String implement;

    private Integer orderFlag;

    private String changeFlag1;

    private String changeFlag2;

    private String changeFlag3;

    private String changeFlag4;

    private String changeFlag5;

    private String changeFlag6;

    private String changeFlag7;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String groupByFlagCodePid;

    private String count;

    private String updateFlag;

    private List<ZjTzPppTermReply> zjTzPppTermReplyList;
    
    private String workId;
    
    private String workIdFlag;
    
    private String projectNameAndSub;
    
    private String issueName;
    
    public String getPppTermReplyId() {
        return pppTermReplyId == null ? "" : pppTermReplyId.trim();
    }

    public void setPppTermReplyId(String pppTermReplyId) {
        this.pppTermReplyId = pppTermReplyId == null ? null : pppTermReplyId.trim();
    }

    public String getPppTermId() {
        return pppTermId == null ? "" : pppTermId.trim();
    }

    public void setPppTermId(String pppTermId) {
        this.pppTermId = pppTermId == null ? null : pppTermId.trim();
    }

    public String getPppTermBaseId() {
        return pppTermBaseId == null ? "" : pppTermBaseId.trim();
    }

    public void setPppTermBaseId(String pppTermBaseId) {
        this.pppTermBaseId = pppTermBaseId == null ? null : pppTermBaseId.trim();
    }

    public String getCodePid() {
        return codePid == null ? "" : codePid.trim();
    }

    public void setCodePid(String codePid) {
        this.codePid = codePid == null ? null : codePid.trim();
    }

    public String getAnalysisKey() {
        return analysisKey == null ? "" : analysisKey.trim();
    }

    public void setAnalysisKey(String analysisKey) {
        this.analysisKey = analysisKey == null ? null : analysisKey.trim();
    }

    public String getKeyTerm() {
        return keyTerm == null ? "" : keyTerm.trim();
    }

    public void setKeyTerm(String keyTerm) {
        this.keyTerm = keyTerm == null ? null : keyTerm.trim();
    }

    public String getKeyAnalysisContent() {
        return keyAnalysisContent == null ? "" : keyAnalysisContent.trim();
    }

    public void setKeyAnalysisContent(String keyAnalysisContent) {
        this.keyAnalysisContent = keyAnalysisContent == null ? null : keyAnalysisContent.trim();
    }

    public String getNumberContent() {
        return numberContent == null ? "" : numberContent.trim();
    }

    public void setNumberContent(String numberContent) {
        this.numberContent = numberContent == null ? null : numberContent.trim();
    }

    public String getTerm() {
        return term == null ? "" : term.trim();
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getRiskFlag() {
        return riskFlag == null ? "" : riskFlag.trim();
    }

    public void setRiskFlag(String riskFlag) {
        this.riskFlag = riskFlag == null ? null : riskFlag.trim();
    }

    public String getMeasure() {
        return measure == null ? "" : measure.trim();
    }

    public void setMeasure(String measure) {
        this.measure = measure == null ? null : measure.trim();
    }

    public String getNegotiateFlag() {
        return negotiateFlag == null ? "" : negotiateFlag.trim();
    }

    public void setNegotiateFlag(String negotiateFlag) {
        this.negotiateFlag = negotiateFlag == null ? null : negotiateFlag.trim();
    }

    public String getDeptAndLeader() {
        return deptAndLeader == null ? "" : deptAndLeader.trim();
    }

    public void setDeptAndLeader(String deptAndLeader) {
        this.deptAndLeader = deptAndLeader == null ? null : deptAndLeader.trim();
    }

    public String getImplement() {
        return implement == null ? "" : implement.trim();
    }

    public void setImplement(String implement) {
        this.implement = implement == null ? null : implement.trim();
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getChangeFlag1() {
        return changeFlag1 == null ? "" : changeFlag1.trim();
    }

    public void setChangeFlag1(String changeFlag1) {
        this.changeFlag1 = changeFlag1 == null ? null : changeFlag1.trim();
    }

    public String getChangeFlag2() {
        return changeFlag2 == null ? "" : changeFlag2.trim();
    }

    public void setChangeFlag2(String changeFlag2) {
        this.changeFlag2 = changeFlag2 == null ? null : changeFlag2.trim();
    }

    public String getChangeFlag3() {
        return changeFlag3 == null ? "" : changeFlag3.trim();
    }

    public void setChangeFlag3(String changeFlag3) {
        this.changeFlag3 = changeFlag3 == null ? null : changeFlag3.trim();
    }

    public String getChangeFlag4() {
        return changeFlag4 == null ? "" : changeFlag4.trim();
    }

    public void setChangeFlag4(String changeFlag4) {
        this.changeFlag4 = changeFlag4 == null ? null : changeFlag4.trim();
    }

    public String getChangeFlag5() {
        return changeFlag5 == null ? "" : changeFlag5.trim();
    }

    public void setChangeFlag5(String changeFlag5) {
        this.changeFlag5 = changeFlag5 == null ? null : changeFlag5.trim();
    }

    public String getChangeFlag6() {
        return changeFlag6 == null ? "" : changeFlag6.trim();
    }

    public void setChangeFlag6(String changeFlag6) {
        this.changeFlag6 = changeFlag6 == null ? null : changeFlag6.trim();
    }

    public String getChangeFlag7() {
        return changeFlag7 == null ? "" : changeFlag7.trim();
    }

    public void setChangeFlag7(String changeFlag7) {
        this.changeFlag7 = changeFlag7 == null ? null : changeFlag7.trim();
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

    public String getGroupByFlagCodePid() {
        return groupByFlagCodePid == null ? "" : groupByFlagCodePid.trim();
    }

    public void setGroupByFlagCodePid(String groupByFlagCodePid) {
        this.groupByFlagCodePid = groupByFlagCodePid == null ? null : groupByFlagCodePid.trim();
    }

    public String getCount() {
        return count == null ? "" : count.trim();
    }

    public void setCount(String count) {
        this.count = count == null ? null : count.trim();
    }

    public String getUpdateFlag() {
        return updateFlag == null ? "" : updateFlag.trim();
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag == null ? null : updateFlag.trim();
    }

    public List<ZjTzPppTermReply> getZjTzPppTermReplyList() {
        return zjTzPppTermReplyList;
    }

    public void setZjTzPppTermReplyList(List<ZjTzPppTermReply> zjTzPppTermReplyList) {
        this.zjTzPppTermReplyList = zjTzPppTermReplyList;
    }
    
    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }
    
    public String getWorkIdFlag() {
        return workIdFlag == null ? "" : workIdFlag.trim();
    }

    public void setWorkIdFlag(String workIdFlag) {
        this.workIdFlag = workIdFlag == null ? null : workIdFlag.trim();
    }

	public String getProjectNameAndSub() {
		return projectNameAndSub == null ? "" : projectNameAndSub.trim();
	}

	public void setProjectNameAndSub(String projectNameAndSub) {
		this.projectNameAndSub = projectNameAndSub == null ? null : projectNameAndSub.trim();
	}
	
	public String getIssueName() {
		return issueName == null ? "" : issueName.trim();
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName == null ? null : issueName.trim();
	}

}