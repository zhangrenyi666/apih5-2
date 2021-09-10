package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfSpecialEmpItem extends BasePojo {
    // 主键
    private String zxSfSpecialEmpItemId;

    // 主表ID
    private String seID;

    // 姓名
    private String name;

    // 性别
    private String sex;

    // 准操作项目
    private String opProjName;

    // 发证机关
    private String sendName;

    // 发证日期
    private Date sendDate;

    // 证件号码
    private String cardNo;

    // 复审日期
    private Date checkDate;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 编辑时间
    private Date editTime;

    // 退场时间
    private Date outDate;

    // 备注
    private String remarks;

    private Date endDate;

    private String xuHao;

    private String tlOpProjName;

    private String companyId;

    private String companyName;
    // 排序
    private int sort=0;

    private String zxSfSpecialEmpId;

    public String getZxSfSpecialEmpId() {
        return zxSfSpecialEmpId;
    }

    public void setZxSfSpecialEmpId(String zxSfSpecialEmpId) {
        this.zxSfSpecialEmpId = zxSfSpecialEmpId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getXuHao() {
        return xuHao;
    }

    public void setXuHao(String xuHao) {
        this.xuHao = xuHao;
    }

    public String getTlOpProjName() {
        return tlOpProjName;
    }

    public void setTlOpProjName(String tlOpProjName) {
        this.tlOpProjName = tlOpProjName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getZxSfSpecialEmpItemId() {
        return zxSfSpecialEmpItemId == null ? "" : zxSfSpecialEmpItemId.trim();
    }

    public void setZxSfSpecialEmpItemId(String zxSfSpecialEmpItemId) {
        this.zxSfSpecialEmpItemId = zxSfSpecialEmpItemId == null ? null : zxSfSpecialEmpItemId.trim();
    }

    public String getSeID() {
        return seID == null ? "" : seID.trim();
    }

    public void setSeID(String seID) {
        this.seID = seID == null ? null : seID.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex == null ? "" : sex.trim();
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getOpProjName() {
        return opProjName == null ? "" : opProjName.trim();
    }

    public void setOpProjName(String opProjName) {
        this.opProjName = opProjName == null ? null : opProjName.trim();
    }

    public String getSendName() {
        return sendName == null ? "" : sendName.trim();
    }

    public void setSendName(String sendName) {
        this.sendName = sendName == null ? null : sendName.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getCardNo() {
        return cardNo == null ? "" : cardNo.trim();
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
