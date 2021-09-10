package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPppTermBase extends BasePojo {
    private String pppTermBaseId;

    private String codePid;

    private String typeId;

    private String analysisKey;

    private String keyTerm;

    private String keyAnalysisContent;

    private Integer orderFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String interfaceFlag;//0外层  1里层

    private String analysisKeyName;

    private List<ZjTzFile> fileList;

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

    public String getTypeId() {
        return typeId == null ? "" : typeId.trim();
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
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

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
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

    public String getInterfaceFlag() {
        return interfaceFlag == null ? "" : interfaceFlag.trim();
    }

    public void setInterfaceFlag(String interfaceFlag) {
        this.interfaceFlag = interfaceFlag == null ? null : interfaceFlag.trim();
    }

    public String getAnalysisKeyName() {
        return analysisKeyName == null ? "" : analysisKeyName.trim();
    }

    public void setAnalysisKeyName(String analysisKeyName) {
        this.analysisKeyName = analysisKeyName == null ? null : analysisKeyName.trim();
    }

    public List<ZjTzFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjTzFile> fileList) {
        this.fileList = fileList;
    }

}

