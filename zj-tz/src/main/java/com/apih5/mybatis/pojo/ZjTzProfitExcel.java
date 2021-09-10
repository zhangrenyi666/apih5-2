package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzProfitExcel extends BasePojo {
    private String profitExcelId;

    private String profitId;

    private String ext11;

    private String ext12;

    private String ext13;

    private String ext14;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> fileList;

    private String workId;
    
    public String getProfitExcelId() {
        return profitExcelId == null ? "" : profitExcelId.trim();
    }

    public void setProfitExcelId(String profitExcelId) {
        this.profitExcelId = profitExcelId == null ? null : profitExcelId.trim();
    }

    public String getProfitId() {
        return profitId == null ? "" : profitId.trim();
    }

    public void setProfitId(String profitId) {
        this.profitId = profitId == null ? null : profitId.trim();
    }

    public String getExt11() {
        return ext11 == null ? "" : ext11.trim();
    }

    public void setExt11(String ext11) {
        this.ext11 = ext11 == null ? null : ext11.trim();
    }

    public String getExt12() {
        return ext12 == null ? "" : ext12.trim();
    }

    public void setExt12(String ext12) {
        this.ext12 = ext12 == null ? null : ext12.trim();
    }

    public String getExt13() {
        return ext13 == null ? "" : ext13.trim();
    }

    public void setExt13(String ext13) {
        this.ext13 = ext13 == null ? null : ext13.trim();
    }

    public String getExt14() {
        return ext14 == null ? "" : ext14.trim();
    }

    public void setExt14(String ext14) {
        this.ext14 = ext14 == null ? null : ext14.trim();
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

    public List<ZjTzFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjTzFile> fileList) {
        this.fileList = fileList;
    }
    
    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

}

