package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class BaseAccount extends BasePojo {
    private String accountId;

    private String accountName;

    private String accountCorpId;

    private String accountAppType;

    private String agentId;

    private String corpId;

    private String secret;

    private String token;

    private String accessToken;

    private String scope;

    private String otherAccountId;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private Date modifyTime;

    private String modifyUser;

    private String domianUrl;

    private String state;

    private String jssdkUrl;

    public String getAccountId() {
        return accountId == null ? "" : accountId.trim();
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getAccountName() {
        return accountName == null ? "" : accountName.trim();
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountCorpId() {
        return accountCorpId == null ? "" : accountCorpId.trim();
    }

    public void setAccountCorpId(String accountCorpId) {
        this.accountCorpId = accountCorpId == null ? null : accountCorpId.trim();
    }

    public String getAccountAppType() {
        return accountAppType == null ? "" : accountAppType.trim();
    }

    public void setAccountAppType(String accountAppType) {
        this.accountAppType = accountAppType == null ? null : accountAppType.trim();
    }

    public String getAgentId() {
        return agentId == null ? "" : agentId.trim();
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getCorpId() {
        return corpId == null ? "" : corpId.trim();
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId == null ? null : corpId.trim();
    }

    public String getSecret() {
        return secret == null ? "" : secret.trim();
    }

    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

    public String getToken() {
        return token == null ? "" : token.trim();
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getAccessToken() {
        return accessToken == null ? "" : accessToken.trim();
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public String getScope() {
        return scope == null ? "" : scope.trim();
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getOtherAccountId() {
        return otherAccountId == null ? "" : otherAccountId.trim();
    }

    public void setOtherAccountId(String otherAccountId) {
        this.otherAccountId = otherAccountId == null ? null : otherAccountId.trim();
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

    public String getDomianUrl() {
        return domianUrl == null ? "" : domianUrl.trim();
    }

    public void setDomianUrl(String domianUrl) {
        this.domianUrl = domianUrl == null ? null : domianUrl.trim();
    }

    public String getState() {
        return state == null ? "" : state.trim();
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getJssdkUrl() {
        return jssdkUrl == null ? "" : jssdkUrl.trim();
    }

    public void setJssdkUrl(String jssdkUrl) {
        this.jssdkUrl = jssdkUrl == null ? null : jssdkUrl.trim();
    }

}

