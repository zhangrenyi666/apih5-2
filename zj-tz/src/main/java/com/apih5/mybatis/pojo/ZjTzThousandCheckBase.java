package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzThousandCheckBase extends BasePojo {
    private String thousandCheckBaseId;

    private String codePid;

    private String typeId;

    private String evalPro;

    private String evalIndex;

    private String evalContent;

    private BigDecimal score;

    private BigDecimal scoreSubtotal;

    private BigDecimal scoreTotal;

    private String pidAll;

    private String pidNameAll;

    private Integer orderFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String interfaceFlag;

    private String evalProName;

    private List<ZjTzFile> fileList;

    public String getThousandCheckBaseId() {
        return thousandCheckBaseId == null ? "" : thousandCheckBaseId.trim();
    }

    public void setThousandCheckBaseId(String thousandCheckBaseId) {
        this.thousandCheckBaseId = thousandCheckBaseId == null ? null : thousandCheckBaseId.trim();
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

    public String getEvalPro() {
        return evalPro == null ? "" : evalPro.trim();
    }

    public void setEvalPro(String evalPro) {
        this.evalPro = evalPro == null ? null : evalPro.trim();
    }

    public String getEvalIndex() {
        return evalIndex == null ? "" : evalIndex.trim();
    }

    public void setEvalIndex(String evalIndex) {
        this.evalIndex = evalIndex == null ? null : evalIndex.trim();
    }

    public String getEvalContent() {
        return evalContent == null ? "" : evalContent.trim();
    }

    public void setEvalContent(String evalContent) {
        this.evalContent = evalContent == null ? null : evalContent.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScoreSubtotal() {
        return scoreSubtotal;
    }

    public void setScoreSubtotal(BigDecimal scoreSubtotal) {
        this.scoreSubtotal = scoreSubtotal;
    }

    public BigDecimal getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(BigDecimal scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getPidAll() {
        return pidAll == null ? "" : pidAll.trim();
    }

    public void setPidAll(String pidAll) {
        this.pidAll = pidAll == null ? null : pidAll.trim();
    }

    public String getPidNameAll() {
        return pidNameAll == null ? "" : pidNameAll.trim();
    }

    public void setPidNameAll(String pidNameAll) {
        this.pidNameAll = pidNameAll == null ? null : pidNameAll.trim();
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

    public String getEvalProName() {
        return evalProName == null ? "" : evalProName.trim();
    }

    public void setEvalProName(String evalProName) {
        this.evalProName = evalProName == null ? null : evalProName.trim();
    }

    public List<ZjTzFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjTzFile> fileList) {
        this.fileList = fileList;
    }

}

