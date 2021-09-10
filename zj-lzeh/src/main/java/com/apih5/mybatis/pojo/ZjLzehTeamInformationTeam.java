package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjLzehTeamInformationTeam extends BasePojo {
    private String teamInformationTeamId;

    private String teamInformationId;

    private String teamId;

    private String section;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjLzehTeam> TeamList;

    public String getTeamInformationTeamId() {
        return teamInformationTeamId == null ? "" : teamInformationTeamId.trim();
    }

    public void setTeamInformationTeamId(String teamInformationTeamId) {
        this.teamInformationTeamId = teamInformationTeamId == null ? null : teamInformationTeamId.trim();
    }

    public String getTeamInformationId() {
        return teamInformationId == null ? "" : teamInformationId.trim();
    }

    public void setTeamInformationId(String teamInformationId) {
        this.teamInformationId = teamInformationId == null ? null : teamInformationId.trim();
    }

    public String getTeamId() {
        return teamId == null ? "" : teamId.trim();
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public String getSection() {
        return section == null ? "" : section.trim();
    }

    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
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

    public List<ZjLzehTeam> getTeamList() {
        return TeamList;
    }

    public void setTeamList(List<ZjLzehTeam> teamList) {
        TeamList = teamList;
    }
}

