package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfEduItem extends BasePojo {
    // 主键
    private String zxSfEduItemId;

    // 主表ID
    private String eduID;

    // 姓名
    private String name;

    // 工种
    private String empType;

    // 身份证号
    private String idCard;

    // 教育类型
    private String tranContext;

    // 编辑时间
    private Date editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String orgID;

    private String orgName;

    private String xuhao;

    public String getXuhao() {
        return xuhao;
    }

    public void setXuhao(String xuhao) {
        this.xuhao = xuhao;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getZxSfEduItemId() {
        return zxSfEduItemId == null ? "" : zxSfEduItemId.trim();
    }

    public void setZxSfEduItemId(String zxSfEduItemId) {
        this.zxSfEduItemId = zxSfEduItemId == null ? null : zxSfEduItemId.trim();
    }

    public String getEduID() {
        return eduID == null ? "" : eduID.trim();
    }

    public void setEduID(String eduID) {
        this.eduID = eduID == null ? null : eduID.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmpType() {
        return empType == null ? "" : empType.trim();
    }

    public void setEmpType(String empType) {
        this.empType = empType == null ? null : empType.trim();
    }

    public String getIdCard() {
        return idCard == null ? "" : idCard.trim();
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getTranContext() {
        return tranContext == null ? "" : tranContext.trim();
    }

    public void setTranContext(String tranContext) {
        this.tranContext = tranContext == null ? null : tranContext.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
