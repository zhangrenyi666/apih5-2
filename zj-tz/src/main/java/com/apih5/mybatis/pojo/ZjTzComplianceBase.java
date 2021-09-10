package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzComplianceBase extends BasePojo {
    private String complianceBaseId;

    private String num;

    private String complianceBanseName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String groupByFlagTypeId;

    private String number;

    private List<ZjTzComplianceBase> children;

    private String mainFlag;

    public String getComplianceBaseId() {
        return complianceBaseId == null ? "" : complianceBaseId.trim();
    }

    public void setComplianceBaseId(String complianceBaseId) {
        this.complianceBaseId = complianceBaseId == null ? null : complianceBaseId.trim();
    }

    public String getNum() {
        return num == null ? "" : num.trim();
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getComplianceBanseName() {
        return complianceBanseName == null ? "" : complianceBanseName.trim();
    }

    public void setComplianceBanseName(String complianceBanseName) {
        this.complianceBanseName = complianceBanseName == null ? null : complianceBanseName.trim();
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

    public String getGroupByFlagTypeId() {
        return groupByFlagTypeId == null ? "" : groupByFlagTypeId.trim();
    }

    public void setGroupByFlagTypeId(String groupByFlagTypeId) {
        this.groupByFlagTypeId = groupByFlagTypeId == null ? null : groupByFlagTypeId.trim();
    }

    public String getNumber() {
        return number == null ? "" : number.trim();
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public List<ZjTzComplianceBase> getChildren() {
        return children;
    }

    public void setChildren(List<ZjTzComplianceBase> children) {
        this.children = children;
    }

    public String getMainFlag() {
        return mainFlag == null ? "" : mainFlag.trim();
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag == null ? null : mainFlag.trim();
    }

}

