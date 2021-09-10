package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjLzehTeamInformation extends BasePojo {

    private String teamInformationId;

    private Integer orderFlag;

    private String teamName;

    private Integer aSectionPeopleNumber;

    private List<ZjLzehTeam> aTeamList;

    private Integer bSectionPeopleNumber;

    private List<ZjLzehTeam> bTeamList;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getTeamInformationId() {
        return teamInformationId;
    }

    public void setTeamInformationId(String teamInformationId) {
        this.teamInformationId = teamInformationId;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getaSectionPeopleNumber() {
        return aSectionPeopleNumber;
    }

    public void setaSectionPeopleNumber(Integer aSectionPeopleNumber) {
        this.aSectionPeopleNumber = aSectionPeopleNumber;
    }

    public List<ZjLzehTeam> getaTeamList() {
        return aTeamList;
    }

    public void setaTeamList(List<ZjLzehTeam> aTeamList) {
        this.aTeamList = aTeamList;
    }

    public Integer getbSectionPeopleNumber() {
        return bSectionPeopleNumber;
    }

    public void setbSectionPeopleNumber(Integer bSectionPeopleNumber) {
        this.bSectionPeopleNumber = bSectionPeopleNumber;
    }

    public List<ZjLzehTeam> getbTeamList() {
        return bTeamList;
    }

    public void setbTeamList(List<ZjLzehTeam> bTeamList) {
        this.bTeamList = bTeamList;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String getCreateUserName() {
        return createUserName;
    }

    @Override
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public Date getModifyTime() {
        return modifyTime;
    }

    @Override
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String getModifyUser() {
        return modifyUser;
    }

    @Override
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public String getModifyUserName() {
        return modifyUserName;
    }

    @Override
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }
}

